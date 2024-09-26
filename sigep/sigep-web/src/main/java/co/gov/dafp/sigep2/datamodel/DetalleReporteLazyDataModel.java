package co.gov.dafp.sigep2.datamodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.GestionInformacionDelegate;
import co.gov.dafp.sigep2.mbean.DetalleReporteBean;
import co.gov.dafp.sigep2.mbean.ReporteBean;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.NumeroUtil;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.Registro;
import co.gov.dafp.sigep2.util.xml.reporte.config.Columna;

public class DetalleReporteLazyDataModel extends LazyDataModel<Registro> {
	private static final long serialVersionUID = 9020784816789104644L;

	List<Registro> datasource;
	private ReporteBean reporteBean;

	public DetalleReporteLazyDataModel() {

	}

	public DetalleReporteLazyDataModel(ReporteBean reporteBean) {
		this.datasource = new LinkedList<Registro>();
		reporteBean = (ReporteBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("reporteBeanS");
		DetalleReporteBean detalleReporteBean = (DetalleReporteBean) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("detalleReporteBeanS");
		reporteBean.setDetalleReporteBean(detalleReporteBean);
		this.reporteBean = reporteBean;
	}

	@Override
	public Object getRowKey(Registro object) {
		return object.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Registro getRowData(String rowKey) {
		List<Registro> Registros = (List<Registro>) getWrappedData();
		for (Registro ar : Registros) {
			if (String.valueOf(ar.getId()).equals(rowKey))
				return ar;
		}
		return null;
	}

	@Override
	public List<Registro> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		try {
			boolean parametrosDiligenciados = false;
			reporteBean.getDetalleReporteBean().setTotales(new LinkedList<Parametro>());
			List<Columna> columnas = reporteBean.getDetalleReporteBean().getTipoRegistro().getColumna();
			for (Columna col : columnas) {
				Parametro total = new Parametro(col.getEtiquetaColumna(), 0);
				reporteBean.getDetalleReporteBean().getTotales().add(total);
			}
			String[] parametrosDesdePadre = this.reporteBean.getDetalleReporteBean().getParametrosDesdePadre()
					.split(";");
			int index = 1;
			for (int i = 0; i < reporteBean.getDetalleReporteBean().getParametros().size(); i++) {
				parametrosDiligenciados = true;
				reporteBean.getDetalleReporteBean().getParametros().get(i).setValue(parametrosDesdePadre[index]);
			}
			if (parametrosDiligenciados) {
				reporteBean.getDetalleReporteBean().setTotales(new LinkedList<Parametro>());
				datasource = GestionInformacionDelegate.exec(reporteBean.getDetalleReporteBean().getTipoRegistro(),
						reporteBean.getDetalleReporteBean().getXml(),
						reporteBean.getDetalleReporteBean().getUsuarioSesion(),
						reporteBean.getDetalleReporteBean().getParametros(), DateUtils.getFechaSistema(),
						ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_ARCHIVO_REPORTE), first,
						pageSize, false);
				setRowCount(GestionInformacionDelegate.getRowsCount());
				boolean hayTotales = false;
				for (Columna col : columnas) {
					Object sumatoria = "";
					if (col.isTotalizado()) {
						if (!hayTotales) {
							hayTotales = true;
						}
						sumatoria = 0;
						for (Registro registro : datasource) {
							sumatoria = NumeroUtil.sumatoria(sumatoria,
									registro.getItem().get(columnas.indexOf(col)).getValue());
						}
					}
					Parametro total = new Parametro(col.getEtiquetaColumna(), sumatoria);
					total.setType(col.getTipoDato().value());
					reporteBean.getDetalleReporteBean().getTotales().add(total);
				}
				if (!hayTotales) {
					reporteBean.setTotales(new LinkedList<Parametro>());
				}
			} else {
				datasource = new LinkedList<Registro>();
			}
		} catch (Exception e) {
			datasource = new LinkedList<Registro>();
			setRowCount(0);
			reporteBean.getDetalleReporteBean().mostrarMensaje(FacesMessage.SEVERITY_WARN,
					MessagesBundleConstants.DLG_VALOR_REQUERIDO, e.getMessage());
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reporteBeanS", reporteBean);
		reporteBean.getDetalleReporteBean().setDatasource(datasource);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detalleReporteBeanS",
				reporteBean.getDetalleReporteBean());
		return datasource;
	}

	@Override
	public void setRowCount(int rowCount) {
		super.setRowCount(rowCount);
	}
}
