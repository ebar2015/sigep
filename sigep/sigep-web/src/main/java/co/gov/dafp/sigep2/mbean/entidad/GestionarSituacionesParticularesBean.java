package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.SituacionAdministrativaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.UtilidadesFaces;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 * Administra los casos de uso relacionados con Gestionar Situaciones
 * Administrativas.
 *
 * @author jesus.torres
 * @Proyect SIGEPII
 * @Company ADA SA
 * @Module Entidades
 * @Fecha 11-07-2018
 */
@Named
@ManagedBean
@ViewScoped
public class GestionarSituacionesParticularesBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 5131929950282880884L;

	private SituacionAdministrativaExt nuevaSituacionParticular; // Instancia del objeto para guardar el registro.
	private String strTituloFormulario; // String para mostrar el título del formulario.
	private String STR_TITULO_NUEVA_SITUACION = TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_REGISTRAR_SA, getLocale()); // Estas dos
																									// constantes me
																									// permiten
																									// gestionar el
																									// titulo del
																									// formulario.
	private String STR_TITULO_EDITAR_SITUACION =TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_MODIFICAR_SA, getLocale()); // Es el mismo
																									// formulario.
	private String STR_TITULO_CONSULTAR_SITUACION = TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_CONSULTAR_SA, getLocale()); // Titulo para el
																										// detalle de la
																										// ssituación.
	private Boolean boolHabilitadoFormulario; // Variable para mostrar o esconder el formulario.
	private Boolean boolHabilitadoDetalle; // Variable para mostrar o esconder el detalle.
	private Boolean boolVerBotonesForm; // Variable para controlar visibilidad de botones en el detalle.
	private Boolean boolVerBotonExportar; // Variable para controlar visibilidad de boton exportar.
	private List<SituacionAdministrativaExt> listaSituaciones; // Lista para mostrar la data almacenada en la tabla.
	private List<SituacionAdministrativaExt> listaSituacionAsignada; // Lista para mostrar las situaciones asignadas.
	private Entidad entidadActual;
	private static final int INT_ROL_VINCULADO = 4;
	private boolean flgisRolSoloConsulta;
	private boolean situacionExiste = false;
	private String nombresituacion;

	// Métodos apuntadores

	public SituacionAdministrativaExt getNuevaSituacionParticular() {
		return nuevaSituacionParticular;
	}

	public Boolean getBoolVerBotonExportar() {
		return boolVerBotonExportar;
	}

	public void setBoolVerBotonExportar(Boolean boolVerBotonExportar) {
		this.boolVerBotonExportar = boolVerBotonExportar;
	}

	public void setNuevaSituacionParticular(SituacionAdministrativaExt nuevaSituacionParticular) {
		this.nuevaSituacionParticular = nuevaSituacionParticular;
	}

	public String getStrTituloFormulario() {
		return strTituloFormulario;
	}

	public void setStrTituloFormulario(String strTituloFormulario) {
		this.strTituloFormulario = strTituloFormulario;
	}

	public Boolean getBoolHabilitadoFormulario() {
		return boolHabilitadoFormulario;
	}

	public void setBoolHabilitadoFormulario(Boolean boolHabilitadoFormulario) {
		this.boolHabilitadoFormulario = boolHabilitadoFormulario;
	}

	public Boolean getBoolHabilitadoDetalle() {
		return boolHabilitadoDetalle;
	}

	public void setBoolHabilitadoDetalle(Boolean boolHabilitadoDetalle) {
		this.boolHabilitadoDetalle = boolHabilitadoDetalle;
	}

	public Boolean getBoolVerBotonesForm() {
		return boolVerBotonesForm;
	}

	public void setBoolVerBotonesForm(Boolean boolVerBotonesForm) {
		this.boolVerBotonesForm = boolVerBotonesForm;
	}

	public List<SituacionAdministrativaExt> getListaSituaciones() {
		return listaSituaciones;
	}

	public void setListaSituaciones(List<SituacionAdministrativaExt> listaSituaciones) {
		this.listaSituaciones = listaSituaciones;
	}

	public List<SituacionAdministrativaExt> getListaSituacionAsignada() {
		return listaSituacionAsignada;
	}

	public void setListaSituacionAsignada(List<SituacionAdministrativaExt> listaSituacionAsignada) {
		this.listaSituacionAsignada = listaSituacionAsignada;
	}

	public  String getStrTituloNuevaSituacion() {
		return STR_TITULO_NUEVA_SITUACION;
	}

	public  String getStrTituloEditarSituacion() {
		return STR_TITULO_EDITAR_SITUACION;
	}

	public  String getStrTituloConsultarSituacion() {
		return STR_TITULO_CONSULTAR_SITUACION;
	}

	// Fin métodos apuntadores

	/**
	 * Método constructor. Inicializa las variables cuando la clase es invocada y
	 * ejecuta el metodo init().
	 */
	public GestionarSituacionesParticularesBean() {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		nuevaSituacionParticular = new SituacionAdministrativaExt();
		entidadActual = new Entidad();
		strTituloFormulario = "";
		boolHabilitadoFormulario = false;
		boolHabilitadoDetalle = false;
		boolVerBotonesForm = false;
		boolVerBotonExportar = false;
		listaSituaciones = new ArrayList<>();
		listaSituacionAsignada = new ArrayList<>();
		init();
	}

	/**
	 * Hace el llamado al cliente de servicio
	 * getSituacionParticularesPorEntidad(SituacionAdministrativaExt
	 * situacionAdministrativa) para cargar las situaciones administrativas
	 * particulares.
	 */
	private void cargarSituaciones() {
		nuevaSituacionParticular = new SituacionAdministrativaExt();
		nuevaSituacionParticular.setFlgEsgenerica((short) 0);
		nuevaSituacionParticular.setFlgActivo((short) 1);
		nuevaSituacionParticular.setCodEntidad(entidadActual.getCodEntidad().intValue());
		listaSituaciones = ComunicacionServiciosAdmin.getSituacionParticularesPorEntidad(nuevaSituacionParticular);
		if (!listaSituaciones.isEmpty()) {
			boolVerBotonExportar = true;
		}
	}

	/**
	 * Carga el detalle de una situacion particular especifica. Trae el nombre de
	 * los servidores publicos que la tienen asignada.
	 * 
	 * @param situacionParticular
	 */
	private void cargarSituacionDetalle(SituacionAdministrativaExt situacionParticular) {
		nuevaSituacionParticular = new SituacionAdministrativaExt();
		nuevaSituacionParticular.setNombreSituacion(situacionParticular.getNombreSituacion());
		nuevaSituacionParticular.setDescripcion(situacionParticular.getDescripcion());
		nuevaSituacionParticular.setCodSituacionAdministrativa(situacionParticular.getCodSituacionAdministrativa());
		nuevaSituacionParticular.setCodRolVinculado(INT_ROL_VINCULADO);
		nuevaSituacionParticular.setFlgEsgenerica((short) 0);
		nuevaSituacionParticular.setFlgActivo((short) 1);
		nuevaSituacionParticular.setCodEntidad(entidadActual.getCodEntidad().intValue());
		listaSituacionAsignada = ComunicacionServiciosAdmin.getSituacionAsignada(nuevaSituacionParticular);
	}

	/**
	 * Metodo llamado en el constructor para recuperar la entidad y si no la puede
	 * recuperar redirecciona a Gestionar Entidad.
	 */
	public void init() {
		if(!validarPermisoRolGestionarSitAdParticuales()){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"window.location.assign('entidad/../../../gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;
		}
		/*Solo consulta*/
		if(flgisRolSoloConsulta){
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_FATAL_RECURSO_SOLO_CONSULTA);
		}
		
	
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		entidadActual = (Entidad) externalContext.getSessionMap().get("entidadParticular");
		if (entidadActual == null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SITUACION_PARTICULAR_ERROR_SIN_ENTIDAD);
			UtilidadesFaces.redirect("../../gestionarEntidad.xhtml");
		} else {
			cargarSituaciones();
			externalContext.getSessionMap().remove("entidadParticular");
		}
	}


	/**
	 * Metodo para que ejecuta la persistencia de los valores para hacer un insert o
	 * un update dependiendo del caso.
	 * 
	 * @return
	 */
	public String persist() {
		if(acListenerExisteNombre()) {
			return null;
		}
		nuevaSituacionParticular.setCodEntidad(entidadActual.getCodEntidad().intValue());
		nuevaSituacionParticular.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		nuevaSituacionParticular.setAudCodRol((int) getUsuarioSesion().getCodRol());
		
		if (nuevaSituacionParticular.getCodSituacionAdministrativa() == null) {
				nuevaSituacionParticular.setAudAccion(TipoParametro.AUDITORIA_INSERT.getValue());
				Boolean hecho = ComunicacionServiciosAdmin.setSituacionParticular(nuevaSituacionParticular);
				if (hecho) {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_CREACION_SITUACION_PARTICULAR_EXITO);
					cargarSituaciones();
					limpiarFormulario();
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FALLA_GUARDADO);
				}
		} else {
			update();
		}
		return null;
	}

	/**
	 * Metodo llamado en el persist() si es para actualizar un valor.
	 * 
	 * @return string
	 */
	public String update() {
		nuevaSituacionParticular.setAudAccion(TipoParametro.AUDITORIA_UPDATE.getValue());
		Boolean hecho = ComunicacionServiciosAdmin.setSituacionParticular(nuevaSituacionParticular);
		if (hecho) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SITUACION_PARTICULAR_MODIFICADO_EXITO);
			cargarSituaciones();
			limpiarFormulario();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FALLA_GUARDADO);
		}
		return "Actualizado con éxito.";
	}

	/**
	 * Muestra un mensaje bloqueante para decidir si se modifica o no un registro.
	 */
	public void mostrarDialogoModificar() {
		limpiarFormulario();
		RequestContext.getCurrentInstance()
				.execute("$('#dlgEditarSituacion').modal({backdrop: 'static', keyboard: false});");
	}

	/**
	 * Muestra un mensaje bloqueante para decidir si se elimina o no un registro.
	 */
	public void mostrarDialogoEliminar() {
		setBoolHabilitadoDetalle(false);
		setBoolHabilitadoFormulario(false);
		RequestContext.getCurrentInstance()
				.execute("$('#dlgEliminarSituacion').modal({backdrop: 'static', keyboard: false});");
	}

	/**
	 * Habilita el apartado de detalle en la pantalla.
	 * 
	 * @param situacionParticular
	 */
	public void mostrarDetalle(SituacionAdministrativaExt situacionParticular) {
		strTituloFormulario = getStrTituloConsultarSituacion();
		setBoolHabilitadoDetalle(true);
		setBoolHabilitadoFormulario(true);
		setBoolVerBotonesForm(false);
		cargarSituacionDetalle(situacionParticular);
	}

	/**
	 * Metodo para eliminar un registro seleccionado.
	 */
	public void delete() {
		RequestContext.getCurrentInstance().execute("$('#dlgEliminarSituacion').modal('hide');");
		nuevaSituacionParticular.setFlgActivo((short) 1);
		setListaSituacionAsignada(ComunicacionServiciosAdmin.getSituacionParticularAsignada(nuevaSituacionParticular));
		if (!listaSituacionAsignada.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SITUACION_PARTICULAR_NO_ELIMINADO);
		} else {
			nuevaSituacionParticular.setAudAccion(TipoParametro.AUDITORIA_DELETE.getValue());
			nuevaSituacionParticular.setAudCodUsuario(BigDecimal.valueOf(getUsuarioSesion().getId()));
			nuevaSituacionParticular.setAudFechaActualizacion(new Date());
			nuevaSituacionParticular.setCodEntidad(entidadActual.getCodEntidad().intValue());
			nuevaSituacionParticular.setAudCodRol((int) getUsuarioSesion().getCodRol());
			nuevaSituacionParticular.setCodRolVinculado(INT_ROL_VINCULADO);
			nuevaSituacionParticular.setFlgActivo((short) 0);
			Boolean hecho = ComunicacionServiciosAdmin.setSituacionParticular(nuevaSituacionParticular);
			if (hecho) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_SITUACION_ELIMINADA);
				limpiarFormulario();
				cargarSituaciones();
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_ERROR_ELIMINAR_SITUACION_PARTICULAR);
			}
		}
	}

	/**
	 * Metodo que habilita el formulario para crear un nuevo registro.
	 */
	public void activarFormulario() {
		if (boolHabilitadoFormulario && boolHabilitadoDetalle) {
			setBoolHabilitadoDetalle(false);
			setBoolVerBotonesForm(true);
			strTituloFormulario = getStrTituloNuevaSituacion();
			nuevaSituacionParticular = new SituacionAdministrativaExt();
		} else if (boolHabilitadoFormulario && !boolHabilitadoDetalle) {
			nuevaSituacionParticular = new SituacionAdministrativaExt();
			setBoolHabilitadoDetalle(false);
			setBoolHabilitadoFormulario(false);
		} else {
			boolHabilitadoFormulario = !boolHabilitadoFormulario;
			setBoolHabilitadoDetalle(false);
			setBoolVerBotonesForm(true);
			strTituloFormulario = getStrTituloNuevaSituacion();
			nuevaSituacionParticular = new SituacionAdministrativaExt();
			situacionExiste = false;
		}
	}

	/**
	 * Metodo que habilita el formulario para editar registro.
	 */
	public void activarFormularioEditar() {
		setBoolHabilitadoDetalle(false);
		setBoolVerBotonesForm(true);
		RequestContext.getCurrentInstance().execute("$('#dlgEditarSituacion').modal('hide');");
		setBoolHabilitadoFormulario(true);
		nombresituacion = nuevaSituacionParticular.getNombreSituacion();
		strTituloFormulario = getStrTituloEditarSituacion();
		situacionExiste = true;
		RequestContext.getCurrentInstance().update("formularioGestionarSituaciones");
		
	}

	/**
	 * Muestra una pantalla bloqueante preguntando si desea cancelar los cambios.
	 */
	public void mostrarDialogoCancelar() {
//		setBoolHabilitadoFormulario(false);
		RequestContext.getCurrentInstance()
				.execute("$('#dlgCancelarRegistro').modal({backdrop: 'static', keyboard: false});");
	}

	/**
	 * Metodo que ejecuta la accion de cancelar, redirecciona al index.
	 */
	public void cancelarGestionSituaciones() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../../../index.xhtml");
		} catch (IOException e) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ERROR_REDIRECCION);
		}
	}

	/**
	 * Metodo para limpiar el formulario.
	 */
	public void limpiarFormulario() {
		setBoolHabilitadoFormulario(false);
		nuevaSituacionParticular = new SituacionAdministrativaExt();
	}

	private ExcelOptions excelOpt;

	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}

	public void setExcelOpt(ExcelOptions excelOpt) {
		this.excelOpt = excelOpt;
	}

	public void postProcessXLS(Object document) {		
		HSSFWorkbook wb = (HSSFWorkbook) document;
		
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFFont cellFont = wb.createFont();
		
		header.setHeightInPoints(14);
		
		cellFont.setFontName("Arial");
		cellFont.setFontHeightInPoints((short) 12);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFont(cellFont);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);
			cell.setCellStyle(cellStyle);
		}
	}

	public void retrieve() {
	}

	@Override
	public void validateForm(ComponentSystemEvent event) {
	}
	
    private boolean validarPermisoRolGestionarSitAdParticuales(){
    	try {
			if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)){
				return true;
			}else{
				/*validar rol consulta*/
				flgisRolSoloConsulta = usuarioTieneRolAsignadoSinJerarquia( RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS, RolDTO.OPERADOR_ENTIDADES,
						 										RolDTO.JEFE_CONTROL_INTERNO,
						 										RolDTO.JEFE_TALENTO_HUMANO,  RolDTO.OPERADOR_TALENTO_HUMANO
															   );
				if(flgisRolSoloConsulta)
					return true;
				return false;
			}
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
    	return false;
    }

	public boolean isFlgisRolSoloConsulta() {
		return flgisRolSoloConsulta;
	}

	public void setFlgisRolSoloConsulta(boolean flgisRolSoloConsulta) {
		this.flgisRolSoloConsulta = flgisRolSoloConsulta;
	}
	/**
	 * 
	 */
	public boolean acListenerExisteNombre() {
		boolean blnExiste = false;
		SituacionAdministrativaExt sAdmin = new SituacionAdministrativaExt();
		sAdmin.setNombreSituacion(nuevaSituacionParticular.getNombreSituacion());
		sAdmin.setCodEntidad(entidadActual.getCodEntidad().intValue());
		if(nuevaSituacionParticular.getCodSituacionAdministrativa()!=null)
			sAdmin.setCodSituacionAdministrativa(nuevaSituacionParticular.getCodSituacionAdministrativa());
		 situacionExiste = ComunicacionServiciosAdmin.getNombreExisteSituParticular(sAdmin);
	     if(situacionExiste) {
	    	nuevaSituacionParticular.setNombreSituacion(nombresituacion); 
	    	mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
	    			MessagesBundleConstants.MSG_SITUACION_PARTICULAR_EXISTENTE,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),""); 
	    	blnExiste = true;
	    }
	     return blnExiste;
	}

	/**
	 * @return the situacionExiste
	 */
	public boolean isSituacionExiste() {
		return situacionExiste;
	}

	/**
	 * @param situacionExiste the situacionExiste to set
	 */
	public void setSituacionExiste(boolean situacionExiste) {
		this.situacionExiste = situacionExiste;
	}
	
	
	public void cancel() {
		setBoolHabilitadoDetalle(false);
		setBoolHabilitadoFormulario(false);
		RequestContext.getCurrentInstance().update("formularioGestionarSituaciones");
	}
	
    
    
}