package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.model.StreamedContent;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.datamodel.DetalleReporteLazyDataModel;
import co.gov.dafp.sigep2.deledago.GestionInformacionDelegate;
import co.gov.dafp.sigep2.entity.ReporteDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.Columna;
import co.gov.dafp.sigep2.util.xml.reporte.config.Registro;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoPlantilla;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoRegistro;

@Named
@ConversationScoped
@ManagedBean
public class DetalleReporteBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -4572667738156756794L;

	private String sqlQuery;
	private DetalleReporteLazyDataModel resultados;
	private List<ColumnModel> columns;
	private ReporteDTO detalleReporte;
	private List<Parametro> totales;
	private XmlReporte xml;
	private Registro tipoRegistro;
	private String parametrosDesdePadre;
	private List<Parametro> parametros;
	private boolean tieneTotales = false;
	private List<co.gov.dafp.sigep2.util.Registro> datasource;

	private StreamedContent fileDownload;

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public DetalleReporteLazyDataModel getResultados() {
		return resultados;
	}

	public void setResultados(DetalleReporteLazyDataModel resultados) {
		this.resultados = resultados;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public ReporteDTO getDetalleReporte() {
		return detalleReporte;
	}

	public void setDetalleReporte(ReporteDTO detalleReporte) {
		this.detalleReporte = detalleReporte;
	}

	public List<Parametro> getTotales() {
		return totales;
	}

	public void setTotales(List<Parametro> totales) {
		this.totales = totales;
	}

	public XmlReporte getXml() {
		return xml;
	}

	public void setXml(XmlReporte xml) {
		this.xml = xml;
	}

	public Registro getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(Registro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public String getParametrosDesdePadre() {
		return parametrosDesdePadre;
	}

	public void setParametrosDesdePadre(String parametrosDesdePadre) {
		this.parametrosDesdePadre = parametrosDesdePadre;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	@Override
	public StreamedContent getFileDownload() {
		ReporteBean bean = (ReporteBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("reporteBeanS");
		this.generarArchivos(columns, bean.getXml(), datasource);
		return fileDownload;
	}

	public List<co.gov.dafp.sigep2.util.Registro> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<co.gov.dafp.sigep2.util.Registro> datasource) {
		this.datasource = datasource;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	@PostConstruct
	public void init() {
		this.parametros = new LinkedList<Parametro>();
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		if (this.id != null) {
			try {
				detalleReporte = GestionInformacionDelegate.getReporte(this.id);
				xml = XmlReporte.getEstructura(
						XmlReporte.getXml(this.detalleReporte.getPlantillaXML(), ConfigurationBundleConstants
								.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CATALOGO)));

				if (xml.getTipoPlantilla().equals(TipoPlantilla.REPORTE)) {
					boolean columnasCreadas = false;
					for (Registro registro : xml.getRegistro()) {
						if (TipoRegistro.DETALLE.equals(registro.getTipoRegistro())
								&& registro.getDescripcion().equalsIgnoreCase(parametrosDesdePadre.split(";")[0])) {
							for (co.gov.dafp.sigep2.util.xml.reporte.config.Parametro parametro : registro.getSQL()
									.getParametro()) {
								Parametro parametroVista = new Parametro(parametro.getNombre(), null);
								parametroVista.setType(parametro.getTipoDato().value());
								parametroVista.setLabel(parametro.getEtiquetaColumna());
								parametroVista.setSource(parametro.getSentencia());
								parametroVista.setRequired(parametro.isRequerido());
								if (!parametros.contains(parametroVista)) {
									parametros.add(parametroVista);
								}
							}
							if (!columnasCreadas) {
								createDynamicColumns(registro.getColumna());
								columnasCreadas = true;
							}
							this.tipoRegistro = registro;
							break;
						}
					}
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_CARGAR_PLANTILLA_REPORTE);
				}
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detalleReporteBeanS", this);
				this.resultados = new DetalleReporteLazyDataModel(null);
			} catch (Exception e) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			}
		}
		this.generarDetalleReporte();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void generarDetalleReporte() {
		this.resultados = new DetalleReporteLazyDataModel(null);
	}

	private void createDynamicColumns(List<Columna> columns) {
		this.columns = new LinkedList<ColumnModel>();

		int index = 0;
		for (Columna columnKey : columns) {
			this.columns.add(new ColumnModel(index++, columnKey.getEtiquetaColumna(), columnKey.getDescripcion(),
					columnKey.getDescripcion(), columnKey.getEtiquetaColumna(), columnKey.getTipoDato().value(),
					columnKey.getMascara(), columnKey.isTotalizado()));
			if (!tieneTotales && columnKey.isTotalizado()) {
				tieneTotales = true;
			}
		}
	}

}
