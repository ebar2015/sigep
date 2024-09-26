package co.gov.dafp.sigep2.mbean;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.converter.ExportacionDocumentoConverter;
import co.gov.dafp.sigep2.datamodel.CifrasLazyDataModel;
import co.gov.dafp.sigep2.entities.CifrasConciliacion;
import co.gov.dafp.sigep2.entities.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosConci;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;

@Named
@ConversationScoped
@ManagedBean
public class CifrasEmpleoPublicoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -461788711703559084L;
	private static final double CNS_SEMAFORO_ROJO = 0.69;
	private static final double CNS_SEMAFORO_AMARILLO = 0.89;
	private static final double CNS_SEMAFORO_VERDE = 1;

	private CifrasEmpleoPublico cifraSeleccionada;
	private CifrasEmpleoPublico filtro;
	private CifrasLazyDataModel listaCifras;
	
	public CifrasEmpleoPublicoBean(){
		debeReportarAGCL = new CifrasConciliacion();
		debeReportarAGCL.setValorCifraConcilia("");
		reportoAGCL = new CifrasConciliacion();
		reportoAGCL.setValorCifraConcilia("");
		numeroGerentesPublicoCL = new CifrasConciliacion();
		numeroGerentesPublicoCL.setValorCifraConcilia("");
		observacionAGCL = new CifrasConciliacion();
		observacionAGCL.setValorCifraConcilia("");
	}

	private static final Long SECTOR = 1695l;
	private static final Long ORDEN = 1362l;
	private static final Long CLASIFICACION_ORGANICA = 1370l;
	private static final Long NATURALEZA = 1707l;
	private static final Long CATEGORIA = 1437l;
	

	private UIInput todosUIInput = null;

	private boolean habilitarConsulta = false;

	private boolean todos = false;
	private boolean verButotn = false;
	
	private List<CifrasConciliacion> listCifrasconciliacion;
	
	private CifrasConciliacion debeReportarAGCL;
	/**
	 * @return the debeReportarAGCL
	 */
	public CifrasConciliacion getDebeReportarAGCL() {
		return debeReportarAGCL;
	}



	/**
	 * @param debeReportarAGCL the debeReportarAGCL to set
	 */
	public void setDebeReportarAGCL(CifrasConciliacion debeReportarAGCL) {
		this.debeReportarAGCL = debeReportarAGCL;
	}



	/**
	 * @return the reportoAGCL
	 */
	public CifrasConciliacion getReportoAGCL() {
		return reportoAGCL;
	}



	/**
	 * @param reportoAGCL the reportoAGCL to set
	 */
	public void setReportoAGCL(CifrasConciliacion reportoAGCL) {
		this.reportoAGCL = reportoAGCL;
	}



	/**
	 * @return the numeroGerentesPublicoCL
	 */
	public CifrasConciliacion getNumeroGerentesPublicoCL() {
		return numeroGerentesPublicoCL;
	}



	/**
	 * @param numeroGerentesPublicoCL the numeroGerentesPublicoCL to set
	 */
	public void setNumeroGerentesPublicoCL(CifrasConciliacion numeroGerentesPublicoCL) {
		this.numeroGerentesPublicoCL = numeroGerentesPublicoCL;
	}



	/**
	 * @return the observacionAGCL
	 */
	public CifrasConciliacion getObservacionAGCL() {
		return observacionAGCL;
	}



	/**
	 * @param observacionAGCL the observacionAGCL to set
	 */
	public void setObservacionAGCL(CifrasConciliacion observacionAGCL) {
		this.observacionAGCL = observacionAGCL;
	}

	private CifrasConciliacion reportoAGCL;
	private CifrasConciliacion numeroGerentesPublicoCL;
	private CifrasConciliacion observacionAGCL;
	

	public boolean isVerButotn() {
		return verButotn;
	}



	public void setVerButotn(boolean verButotn) {
		this.verButotn = verButotn;
	}

	private Long[] sectoresSeleccionados;

	public CifrasEmpleoPublico getFiltro() {
		return filtro;
	}

	
	
	public void setFiltro(CifrasEmpleoPublico filtro) {
		this.filtro = filtro;
	}

	public CifrasEmpleoPublico getCifraSeleccionada() {
		return cifraSeleccionada;
	}

	public void setCifraSeleccionada(CifrasEmpleoPublico cifraSeleccionada) {
		this.cifraSeleccionada = cifraSeleccionada;
	}

	public CifrasLazyDataModel getListaCifras() {
		return listaCifras;
	}

	public void setListaCifras(CifrasLazyDataModel listaCifras) {
		this.listaCifras = listaCifras;
	}

	public Long[] getSectoresSeleccionados() {
		return sectoresSeleccionados;
	}

	public void setSectoresSeleccionados(Long[] sectoresSeleccionados) {
		this.sectoresSeleccionados = sectoresSeleccionados;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public boolean isHabilitarConsulta() {
		return habilitarConsulta;
	}

	public void setHabilitarConsulta(boolean habilitarConsulta) {
		this.habilitarConsulta = habilitarConsulta;
	}

	/**
	 * Inicializa el buscador de cifras de empleo teniendo en cuenta las
	 * restricciones de seguridad por rol para la funcionalidad
	 */
	public void montarDatos() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}

		// Se valida que al menos se haya seleccionado alguna de las tematicas
		if (filtro != null && !filtro.isAcuerdosGestion() && !filtro.isBienestarIncentivos() && !filtro.isBilinguismo()
				&& !filtro.isCapacitacion() && !filtro.isEmpleos() && !filtro.isHorariosFlexibles()
				&& !filtro.isLeyCuotas() && !filtro.isPlanAnualVacantes() && !filtro.isSeguimiento()
				&& !filtro.isTeletrabajo() && !filtro.isNaturaleza() && !todos) {
			habilitarConsulta = false;
			this.listaCifras.setListaCifras(new LinkedList<>());
			FacesContext fc = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_VALOR_REQUERIDO) + ". "
							+ TitlesBundleConstants
									.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_RESUMEN_CARGOS_PUBLICOS));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todosUIInput.getClientId(), msg);
			fc.renderResponse();
		} else {
			if (todos) {
				marcarTodos();
			}
			this.habilitarConsulta = true;
			listaCifras = new CifrasLazyDataModel(filtro);
		}
		
		if(filtro.isAcuerdosGestion()){
			CifrasConciliacion conci = new CifrasConciliacion();
			String cod = filtro.getCodEntidad().replace("*", "");
			cod = cod.replace("[", "");
			cod = cod.replace("]", "");
			cod = cod.replace("^", "");
			cod = cod.replace("(", "");
			cod = cod.replace(")", "");
			cod = cod.replace("$", "");
			cod = cod.substring(1,cod.length());
			conci.setCodigoSigep(cod);
			conci.setNombreReporte("AcuerdosGestion");
			listCifrasconciliacion = ComunicacionServiciosConci.getCifrasConciliacion(conci);
			if(!listCifrasconciliacion.isEmpty()){
				for (CifrasConciliacion cifrasConciliacion : listCifrasconciliacion) {
					if(cifrasConciliacion.getNombreCifra().equals("debeReportarAGCL")){
						debeReportarAGCL = cifrasConciliacion;
					}else if(cifrasConciliacion.getNombreCifra().equals("reportoAGCL")){
						reportoAGCL = cifrasConciliacion;
					}else if(cifrasConciliacion.getNombreCifra().equals("numeroGerentesPublicoCL")){
						numeroGerentesPublicoCL = cifrasConciliacion;
					}else if(cifrasConciliacion.getNombreCifra().equals("observacionAGCL")){
						observacionAGCL = cifrasConciliacion;
					}
				}
				
			}else{
				debeReportarAGCL = new CifrasConciliacion();
				debeReportarAGCL.setValorCifraConcilia("");
				reportoAGCL = new CifrasConciliacion();
				reportoAGCL.setValorCifraConcilia("");
				numeroGerentesPublicoCL = new CifrasConciliacion();
				numeroGerentesPublicoCL.setValorCifraConcilia("");
				observacionAGCL = new CifrasConciliacion();
				observacionAGCL.setValorCifraConcilia("");
			}
		}
	}
	

	public void conciliacionParcial(){
		Date fechac = new Date();
		String cod = filtro.getCodEntidad().replace("*", "");
		cod = cod.replace("[", "");
		cod = cod.replace("]", "");
		cod = cod.replace("^", "");
		cod = cod.replace("(", "");
		cod = cod.replace(")", "");
		cod = cod.replace("$", "");
		cod = cod.substring(1,cod.length());
		observacionAGCL.setCodigoSigep(cod);
		observacionAGCL.setNombreReporte("AcuerdosGestion");
		if(observacionAGCL.getBorrador() == null){
			observacionAGCL.setBorrador((short) 0);
		}
		observacionAGCL.setFechaGeneracion(fechac);
		observacionAGCL.setNombreCifra("debeReportarAGCL");
		observacionAGCL.setValorCifra(listaCifras.getListaCifras().get(0).getDebeReportarAG().replace("TTL_", ""));
		ComunicacionServiciosConci.setCifrasConciliacion(observacionAGCL);
		
		reportoAGCL.setCodigoSigep(cod);
		reportoAGCL.setNombreReporte("AcuerdosGestion");
		if(reportoAGCL.getBorrador() == null){
			reportoAGCL.setBorrador((short) 0);
		}
		reportoAGCL.setFechaGeneracion(fechac);
		reportoAGCL.setNombreCifra("reportoAGCL");
		reportoAGCL.setValorCifra(listaCifras.getListaCifras().get(0).getReportoAG().replace("TTL_", ""));
		ComunicacionServiciosConci.setCifrasConciliacion(reportoAGCL);
		
		numeroGerentesPublicoCL.setCodigoSigep(cod);
		numeroGerentesPublicoCL.setNombreReporte("AcuerdosGestion");
		if(numeroGerentesPublicoCL.getBorrador() == null){
			numeroGerentesPublicoCL.setBorrador((short) 0);
		}
		numeroGerentesPublicoCL.setFechaGeneracion(fechac);
		numeroGerentesPublicoCL.setNombreCifra("reportoAGCL");
		numeroGerentesPublicoCL.setValorCifra(listaCifras.getListaCifras().get(0).getNumeroGerentesPublicos().toString());
		ComunicacionServiciosConci.setCifrasConciliacion(numeroGerentesPublicoCL);
		
		observacionAGCL.setCodigoSigep(cod);
		observacionAGCL.setNombreReporte("AcuerdosGestion");
		if(observacionAGCL.getBorrador() == null){
			observacionAGCL.setBorrador((short) 0);
		}
		observacionAGCL.setFechaGeneracion(fechac);
		observacionAGCL.setNombreCifra("observacionAGCL");
		observacionAGCL.setValorCifra(listaCifras.getListaCifras().get(0).getObservacionAG());
		ComunicacionServiciosConci.setCifrasConciliacion(observacionAGCL);
		
	}
	
	public void conciliacionFinal(){
		Date fechac = new Date();
		String cod = filtro.getCodEntidad().replace("*", "");
		cod = cod.replace("[", "");
		cod = cod.replace("]", "");
		cod = cod.replace("^", "");
		cod = cod.replace("(", "");
		cod = cod.replace(")", "");
		cod = cod.replace("$", "");
		cod = cod.substring(1,cod.length());
		observacionAGCL.setCodigoSigep(cod);
		observacionAGCL.setBorrador((short) 1);
		observacionAGCL.setFechaGeneracion(fechac);
		observacionAGCL.setNombreCifra("debeReportarAGCL");
		observacionAGCL.setValorCifra(listaCifras.getListaCifras().get(0).getDebeReportarAG().replace("TTL_", ""));
		ComunicacionServiciosConci.setCifrasConciliacion(observacionAGCL);
		
		reportoAGCL.setCodigoSigep(cod);
		reportoAGCL.setBorrador((short) 1);
		reportoAGCL.setFechaGeneracion(fechac);
		reportoAGCL.setNombreCifra("reportoAGCL");
		reportoAGCL.setValorCifra(listaCifras.getListaCifras().get(0).getReportoAG().replace("TTL_", ""));
		ComunicacionServiciosConci.setCifrasConciliacion(reportoAGCL);
		
		numeroGerentesPublicoCL.setCodigoSigep(cod);
		numeroGerentesPublicoCL.setBorrador((short) 1);
		numeroGerentesPublicoCL.setFechaGeneracion(fechac);
		numeroGerentesPublicoCL.setNombreCifra("reportoAGCL");
		numeroGerentesPublicoCL.setValorCifra(listaCifras.getListaCifras().get(0).getNumeroGerentesPublicos().toString());
		ComunicacionServiciosConci.setCifrasConciliacion(numeroGerentesPublicoCL);
		
		observacionAGCL.setCodigoSigep(cod);
		observacionAGCL.setBorrador((short) 1);
		observacionAGCL.setFechaGeneracion(fechac);
		observacionAGCL.setNombreCifra("observacionAGCL");
		observacionAGCL.setValorCifra(listaCifras.getListaCifras().get(0).getObservacionAG());
		ComunicacionServiciosConci.setCifrasConciliacion(observacionAGCL);
		
	}

	/**
	 * Validador del formulario principal. Es invocado posterior a las
	 * validaciones de tipo definidas en cada componenete del mismo
	 * 
	 * @param event
	 *            Contiene los componentes procesados en el formulario una vez
	 *            se genera el submit
	 */
	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		UIComponent components = event.getComponent();
		todosUIInput = (UIInput) components.findComponent("todos");
	}

	/**
	 * Inicializa los valores necesarios para el tablero en el formulario
	 */
	@PostConstruct
	public void init() {
		habilitarConsulta = true;
		listaCifras = new CifrasLazyDataModel(filtro);
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_GESTIONINFORMACION);		
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * Construye la conversación entre la vista y el controlador
	 */
	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			if (this.conversation.isTransient()) {
				this.conversation.begin();
				this.conversation.setTimeout(timeOut);
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_URL_INVALID);
		}
		filtro = new CifrasEmpleoPublico();

		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
		this.init();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * Valida formato de las celdas de xlsx posterior al exportado de la tabla
	 * en componentes de exportación de Primefaces a través de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document
	 *            Documento Excel en formato xlsx. Es importante que el atributo
	 *            <code>type</code> del componente de exportación contenga
	 *            exlusivamente uno de esos dos valores
	 */
	public void postProcessXLSX(Object document) {
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessExcel(document, 1,
				TitlesBundleConstants.getStringMessagesBundle("TTL_GI_BREADCRUMB_CIFRAS_EMPLEO_PUBLICO"));

		int indexFirstColumn = 1;
		int indexLastColumn = 9;

		final int indexRow1 = 2;
		final int indexRow2 = 3;

		// Cifras de empleo
		Color awt = new Color(255, 255, 255, 0);
		XSSFColor color = new XSSFColor(awt);
		formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexLastColumn);

		// Plan Anual de Vacantes
		if (filtro.isPlanAnualVacantes()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 27;

			awt = new Color(252, 213, 180, 0);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Ley de Cuotas
		if (filtro.isLeyCuotas()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 2;

			awt = new Color(177, 160, 199);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Maximos
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 7;

			awt = new Color(228, 223, 236);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Otros
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 7;

			awt = new Color(204, 192, 218);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Observaciones
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 1;

			awt = new Color(177, 160, 199);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Empleos
		if (filtro.isEmpleos()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 12;

			awt = new Color(216, 228, 188);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			/*// Planta
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 9;

			awt = new Color(146, 208, 80);
			color = new XSSFColor(awt, null);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Empleos
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 8;

			awt = new Color(216, 228, 188);
			color = new XSSFColor(awt, null);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);*/
		}

		// Naturaleza
		if (filtro.isNaturaleza()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 8;

			awt = new Color(54, 228, 188);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

		}

		// Seguimiento
		if (filtro.isSeguimiento()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 20;

			awt = new Color(146, 205, 220);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}

		// Acuerdos de Gestion
		if (filtro.isAcuerdosGestion()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 4;

			awt = new Color(220, 230, 241);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color,	indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Capacitacion
		if (filtro.isCapacitacion()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 13;

			awt = new Color(184, 204, 228);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Bienestar
		if (filtro.isBienestarIncentivos()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 5;

			awt = new Color(149, 179, 215);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Horarios Flexibles
		if (filtro.isHorariosFlexibles()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 4;

			awt = new Color(54, 96, 146);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Teletrabajo
		if (filtro.isTeletrabajo()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 4;

			awt = new Color(22, 54, 92);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Bilinguismo
		if (filtro.isBilinguismo()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 2;

			awt = new Color(15, 36, 62);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(54, 96, 146);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(15, 36, 62);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(54, 96, 146);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(22, 54, 92);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 1;

			awt = new Color(15, 36, 62);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
	}

	/**
	 * Valida formato de las celdas de xls posterior al exportado de la tabla en
	 * componentes de exportación de Primefaces a través de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document
	 *            Documento Excel en formato xls. Es importante que el atributo
	 *            <code>type</code> del componente de exportación contenga
	 *            exlusivamente uno de esos dos valores
	 */
	public void postProcessXLS(Object document) {
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessExcel(document, 1,
				TitlesBundleConstants.getStringMessagesBundle("TTL_GI_BREADCRUMB_CIFRAS_EMPLEO_PUBLICO"));
	}

	public void formatearCabecerasXLSX(Object document, XSSFColor color, final int indexFirstRow,
			final int indexFirstColumn, final int indexLastColumn) {
		int indexRowTemp = indexFirstRow - 1;
		int indexFirstColumnTemp = indexFirstColumn - 1;
		int indexLastColumnTemp = indexLastColumn;

		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);

		XSSFRow row = sheet.getRow(indexRowTemp);
		for (int i = indexFirstColumnTemp; i < indexLastColumnTemp; i++) {
			XSSFCell cell = row.getCell(i);
			XSSFCellStyle cellStyle = wb.createCellStyle();
			if (cell != null) {
				if (cell.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
					cellStyle.setFillForegroundColor(color);
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyle.setWrapText(true);
					cellStyle.setLocked(true);
					cell.setCellStyle(cellStyle);
				}
				cellStyle.setBorderBottom(BorderStyle.HAIR);
				cellStyle.setBorderLeft(BorderStyle.HAIR);
				cellStyle.setBorderRight(BorderStyle.HAIR);
				cellStyle.setBorderTop(BorderStyle.HAIR);
			}
		}
	}

	/**
	 * Marca en el valor {@link Boolean} de <code>todos</code> todas las
	 * tematicas de formulario de busqueda
	 */
	public void marcarTodos() {
		this.filtro.setPlanAnualVacantes(todos);
		this.filtro.setLeyCuotas(todos);
		this.filtro.setEmpleos(todos);
		this.filtro.setNaturaleza(todos);
		this.filtro.setSeguimiento(todos);
		this.filtro.setAcuerdosGestion(todos);
		this.filtro.setCapacitacion(todos);
		this.filtro.setBienestarIncentivos(todos);
		this.filtro.setHorariosFlexibles(todos);
		this.filtro.setTeletrabajo(todos);
		this.filtro.setBilinguismo(todos);
	}

	/**
	 * Devuelve el color del semaforo de acuerdo a <code>valor</code> teniendo
	 * en cuenta la escala de valores definidas
	 * 
	 * @param valor
	 *            Valor a evaluar
	 * @return {@link String} color del semaforo de acuerdo al rango al que
	 *         aplique <code>valor</code>
	 */
	public static String getSemaforo(BigDecimal valor) {
		if (valor == null) {
			return "";
		}

		if (valor.doubleValue() <= CNS_SEMAFORO_ROJO) {
			return "rojo";
		} else if (valor.doubleValue() <= CNS_SEMAFORO_AMARILLO) {
			return "amarillo";
		} else if (valor.doubleValue() <= CNS_SEMAFORO_VERDE) {
			return "verde";
		} else {
			return "";
		}
	}

	/**
	 * Determina si <code>valor</code> se encuentra contiene un valor que se
	 * considera un error de calculo. El texto devuelto se utiliza para la
	 * marcacion de los estilos css de la celda en la que se contine el dato a
	 * evaluar
	 * 
	 * @param valor
	 *            Valor a evaluar
	 * @return {@link String} nombre de la clase css que marca el error en la
	 *         celda
	 */
	public String getError(BigDecimal valor) {
		if (valor != null) {
			Double noVinculado = valor.doubleValue() * 100;
			if (noVinculado.intValue() > 100) {
				return " seguimiento-error";
			}
		} else {
			return "";
		}
		return "";
	}

	/**
	 * Determina si <code>valor</code> se encuentra contiene un valor que se
	 * considera un error de calculo si este es mayor a <code>100%</code>. El
	 * texto devuelto se utiliza para la marcacion de los estilos css de la
	 * celda en la que se contine el dato a evaluar
	 * 
	 * @param valor
	 *            Valor a evaluar
	 * @return {@link String} nombre de la clase css que marca el error en la
	 *         celda
	 */
	public String getNoVinculado(BigDecimal valor) {
		if (valor != null) {
			Double noVinculado = valor.doubleValue() * 100;
			if (noVinculado.intValue() == -1) {
				return " seguimiento-no-vinculado";
			}
		} else {
			return "";
		}
		return "";
	}

	/**
	 * Devuelve la lista de Sectores Administrativos
	 * 
	 * @return {@link List} de {@link Parametrica} de Sectores Administrativos
	 */
	public List<Parametrica> getListaSectores() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(SECTOR);
	}

	/**
	 * Devuelve la lista de Orden de Entidad
	 * 
	 * @return {@link List} de {@link Parametrica} de Orden de Entidad
	 */
	public List<Parametrica> getListaOrden() {
		List<Parametrica> orden = ComunicacionServiciosSis.getParametricaPorIdPadre(ORDEN);
		Parametrica todos = new Parametrica();
		todos.setNombreParametro("");
		todos.setCodTablaParametrica(BigDecimal.ZERO);

		for (int i = 0; i < orden.size(); i++) {
			Parametrica item = orden.get(i);
			if (i == 0) {
				todos.setNombreParametro(item.getNombreParametro());
			} else if (i < orden.size() - 1) {
				todos.setNombreParametro(todos.getNombreParametro() + ", " + item.getNombreParametro());
			} else {
				todos.setNombreParametro(todos.getNombreParametro() + " y " + item.getNombreParametro());
			}
		}
		todos.setNombreParametro(todos.getNombreParametro().replace("  ", " "));
		orden.add(todos);
		return orden;
	}

	/**
	 * Devuelve la lista de CLasificacion Organica
	 * 
	 * @return {@link List} de {@link Parametrica} de CLasificacion Organica
	 */
	public List<Parametrica> getListaClasificacionOrganica() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(getClasificacionOrganica());
	}

	/**
	 * Devuelve la lista de Naturaleza
	 * 
	 * @return {@link List} de {@link Parametrica} de Naturaleza
	 */
	public List<Parametrica> getListaNaturalezaJuridica() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(NATURALEZA);
	}


	/**
	 * Devuelve la lista de Naturaleza
	 * 
	 * @return {@link List} de {@link Parametrica} de Categoria de la Entidad
	 */
	public List<Parametrica> getListaCategoria() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(CATEGORIA);
	}


	public static Long getClasificacionOrganica() {
		return CLASIFICACION_ORGANICA;
	}
	
	public void btnconciliar(){
		System.out.println("[BtnConciliar");
		System.out.println(filtro.isAcuerdosGestion());
		if(this.verButotn){
			this.verButotn = false;
		}else{
			this.verButotn = true;
		}	

	}


	/**
	 * @return the listCifrasconciliacion
	 */
	public List<CifrasConciliacion> getListCifrasconciliacion() {
		return listCifrasconciliacion;
	}



	/**
	 * @param listCifrasconciliacion the listCifrasconciliacion to set
	 */
	public void setListCifrasconciliacion(List<CifrasConciliacion> listCifrasconciliacion) {
		this.listCifrasconciliacion = listCifrasconciliacion;
	}
}
