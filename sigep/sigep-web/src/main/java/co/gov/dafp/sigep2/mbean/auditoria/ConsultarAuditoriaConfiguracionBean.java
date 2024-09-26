package co.gov.dafp.sigep2.mbean.auditoria;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import com.google.gson.Gson;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.AuditoriaConfiguracion;
import co.gov.dafp.sigep2.entity.seguridad.AuditoriaConfiguracionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.AccionAuditoriaDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ViewScoped
@ManagedBean
public class ConsultarAuditoriaConfiguracionBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -1926074688756375441L;
	private boolean guardarButton = false;
	private List<AuditoriaConfiguracion> listaConfiguracionAuditoria;
	private AuditoriaConfiguracionDTO auditoria;
	private List<AuditoriaConfiguracion> listas;
	private AuditoriaConfiguracion auditoriaConf;
	private List<AuditoriaConfiguracion> lstAuditoriaConf;
	private AuditoriaConfiguracion auditoriasporClave = new AuditoriaConfiguracion();
	private boolean habilitarPanelVerDetalle = false;
	private AccionAuditoriaDTO TipoAccion;
	private String dato 					= "";
	private Boolean flgValidRolPermission 	= false;
	private boolean flgValidModificarCheck 	= true;
	private boolean verBtnGurdar 			= false;
	private  AuditoriaConfiguracion auditoriaConfiguracion;

	public AuditoriaConfiguracion getAuditoriasporClave() {
		return auditoriasporClave;
	}

	public void setAuditoriasporClave(AuditoriaConfiguracion auditoriasporClave) {
		this.auditoriasporClave = auditoriasporClave;
	}

	public AccionAuditoriaDTO getTipoAccion() {
		return TipoAccion;
	}

	public void setTipoAccion(AccionAuditoriaDTO tipoAccion) {
		TipoAccion = tipoAccion;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public Boolean getFlgValidRolPermission() {
		return flgValidRolPermission;
	}

	public void setFlgValidRolPermission(Boolean flgValidRolPermission) {
		this.flgValidRolPermission = flgValidRolPermission;
	}

	public boolean isHabilitarPanelVerDetalle() {
		return habilitarPanelVerDetalle;
	}

	public void setHabilitarPanelVerDetalle(boolean habilitarPanelVerDetalle) {
		this.habilitarPanelVerDetalle = habilitarPanelVerDetalle;
	}

	public List<AuditoriaConfiguracion> getListas() {
		return listas;
	}

	public void setListas(List<AuditoriaConfiguracion> listas) {
		this.listas = listas;
	}

	public boolean isGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(boolean guardarButton) {
		this.guardarButton = guardarButton;
	}

	public AuditoriaConfiguracion getAuditoriaConf() {
		return auditoriaConf;
	}

	public void setAuditoriaConf(AuditoriaConfiguracion auditoriaConf) {
		this.auditoriaConf = auditoriaConf;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
	}

	public List<AuditoriaConfiguracion> getListaConfiguracionAuditoria() {
		return listaConfiguracionAuditoria;
	}

	public void setListaConfiguracionAuditoria(List<AuditoriaConfiguracion> listaConfiguracionAuditoria) {
		this.listaConfiguracionAuditoria = listaConfiguracionAuditoria;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct

	/*
	 * Metodo que retorna a la vista la lista de tablas del sistema y el estado de
	 * permiso en sus acciones CRUD
	 */
	public void init() {
		try {
			auditoriaConfiguracion = new AuditoriaConfiguracion();
			lstAuditoriaConf = new ArrayList<>();
			initialization();
			listaConfiguracionAuditoria = ComunicacionServiciosSis.getAuditoriaConfiguracion(null, 0, 100);
			String jsonToPrint = new Gson().toJson(listaConfiguracionAuditoria);
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}

	/*
	 * Metodo que valida el Rol Usuario del sistema y los permisos de acceso a datos
	 * a la aplicacion.
	 */
	public void initialization() {
		if (getUsuarioSesion() != null) {
			try {
				flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR,
						RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_TECNICO);
				
				if (flgValidRolPermission == false) {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					return;
				}
				boolean b=this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_TECNICO);
				setFlgValidModificarCheck(!b);
				
			} catch (SIGEP2SistemaException e) {
				logger.error("void init() usuarioTieneRolAsignado", e);
			} catch (IOException e) {
				logger.error("void init() finalizarConversacion", e);
			}

		}

	}
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya 11 ene. 2019
	 */
	public void buscar() {
		listaConfiguracionAuditoria = ComunicacionServiciosSis.getAuditoriaConfiguracionFiltro(auditoriaConfiguracion);
		auditoriaConfiguracion = new AuditoriaConfiguracion();
	}

	/**
	 * Metodo 
	 */
	public List<String> consultarNombreFuncional(String entidadPrivQuery) {
		List<String> filtroNombreFuncional = new ArrayList<>();
		List<AuditoriaConfiguracion> listaNombresFuncionales = ComunicacionServiciosSis.getAuditoriaConfiguracionFiltro(auditoriaConfiguracion);
		if (!listaNombresFuncionales.isEmpty()) {
			for (AuditoriaConfiguracion param : listaNombresFuncionales) {
				if(param.getNombreFuncional() !=null && !param.getNombreFuncional().equals("")) {
					if (param.getNombreFuncional().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
						filtroNombreFuncional.add(param.getNombreFuncional());
					}
				}
			}
		}
		return filtroNombreFuncional;

	}
	
	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String update() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// TODO Auto-generated method stub
	}

	
	/*
	 * Metodo que permite cambiar el estado de activo o inactivo los permisos de
	 * Seleccionar sobre la tabla
	 */
	public void cambioCheck(boolean flgOption, short codTablaAuditoria, int index) {		
		try
		{
			verBtnGurdar = true;
			switch (index) {
			case 1:
				auditoriaConf = ComunicacionServiciosSis.getAuditoriaConfiguracion(codTablaAuditoria);
				auditoriaConf.setFlgSelect(flgOption);
				break;
			case 2:
				auditoriaConf = ComunicacionServiciosSis.getAuditoriaConfiguracion(codTablaAuditoria);
				auditoriaConf.setFlgInsert(flgOption);
				break;
			case 3:
				auditoriaConf = ComunicacionServiciosSis.getAuditoriaConfiguracion(codTablaAuditoria);
				auditoriaConf.setFlgUpdate(flgOption);
				break;
			case 4:
				auditoriaConf = ComunicacionServiciosSis.getAuditoriaConfiguracion(codTablaAuditoria);
				auditoriaConf.setFlgDelete(flgOption);
				break;

			}
			auditoriaConf.setAudFechaActualizacion(DateUtils.getFechaSistema());
			auditoriaConf.setAudCodRol((int)this.getRolAuditoria().getId());
			auditoriaConf.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			auditoriaConf.setAudAccion(TipoParametro.AUDITORIA_UPDATE.getValue());
			lstAuditoriaConf.add(auditoriaConf);
			 RequestContext rc = RequestContext.getCurrentInstance();
		        rc.update("frmPrincipal:paneBtnGuardar");
		}catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya 11 ene. 2019
	 */
	public void guardarModificacion() {
		if(!lstAuditoriaConf.isEmpty()) {
			List<String> tablas =  new ArrayList<>();
			for (AuditoriaConfiguracion auditoriaConfiguracion : listaConfiguracionAuditoria) {
				boolean res = ComunicacionServiciosSis.setAuditoriaconfiguracion(auditoriaConfiguracion);
				if(!res) {
					tablas.add(auditoriaConfiguracion.getNombreTabla());
				}
			}
			lstAuditoriaConf = new ArrayList<>();
			verBtnGurdar = false;
			 RequestContext rc = RequestContext.getCurrentInstance();
		        rc.update("frmPrincipal:paneBtnGuardar");
		        if(tablas.isEmpty()) {
		        	mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_AUDITORIA_MODIFICACION_EXITOSA);
		        }else {
		        	String msg = MessagesBundleConstants.MSG_AUDITORIA_MODIFICACION_ERROR;
		        	for (String string : tablas) {
		        		msg +=string+", ";
					}
		        	msg = msg.substring(0, msg.length() - 2);
		        	msg +=".";
		        	mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, msg);
		        }
			
		}
	}

	
	/*
	 * Metodo que permite cambiar el estado de activo o inactivo los permisos de
	 * Insertar sobre la tabla
	 */
	public void cambioCheck1(boolean flgInsert, short codTablaAuditoria) {
		try 
		{
			auditoriaConf = ComunicacionServiciosSis.getAuditoriaConfiguracion(codTablaAuditoria);
			auditoriaConf.setFlgInsert(flgInsert);
			boolean valid = ComunicacionServiciosSis.setAuditoriaconfiguracion(auditoriaConf);
			if (valid == true) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "El cambio se realizó con éxito");
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "No fué posible realizar el cambio");
			}

		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}

	}

	
	/*
	 * Metodo que permite cambiar el estado de activo o inactivo los permisos de
	 * Actualizar sobre la tabla
	 */
	public void cambioCheck2(boolean flgUpdate, short codTablaAuditoria) {
		try {
			auditoriaConf = ComunicacionServiciosSis.getAuditoriaConfiguracion(codTablaAuditoria);
			auditoriaConf.setFlgUpdate(flgUpdate);
			boolean valid = ComunicacionServiciosSis.setAuditoriaconfiguracion(auditoriaConf);
			if (valid == true) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "El cambio se realizó con éxito");
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "No fué posible realizar el cambio");
			}

		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}

	}

	/*
	 * Metodo que permite cambiar el estado de activo o inactivo los permisos de
	 * Eliminar sobre la tabla
	 */
	public void cambioCheck3(boolean flgDelete, short codTablaAuditoria) {
		try {
			auditoriaConf = ComunicacionServiciosSis.getAuditoriaConfiguracion(codTablaAuditoria);
			auditoriaConf.setFlgDelete(flgDelete);
			boolean valid = ComunicacionServiciosSis.setAuditoriaconfiguracion(auditoriaConf);
			if (valid == true) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "El cambio se realizó con éxito");
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "No fué posible realizar el cambio");
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}

	}

	/*
	 * Metodo que permite habilitar en linea el Formulario de buscar detalle de
	 * Configuracion Auditoria
	 */
	public void habilitarFormulariobusqueda() {

		habilitarPanelVerDetalle = true;
	}

	/*
	 * Metodo para retornar a la vista un objeto lleno de tipo
	 * AuditoriaConfiguracion segun sea el id de la tabla seleccionada en la Grilla
	 * Configuracion Auditoria
	 */
	
	public void DetalleAutoria(short codTablaAuditoria) {
		try
		{
			auditoriasporClave = ComunicacionServiciosSis.getAuditoriaConfiguracion(codTablaAuditoria);
			boolean valid=ComunicacionServiciosSis.setAuditoriaconfiguracion(auditoriasporClave);
			if(valid==false) {
				String jsonToPrint = new Gson().toJson(auditoriasporClave);
			}
			else
			{	
				System.out.println("No fue posible obtener la información.");
			}	
		}catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
		
	}
	
	/**
	 * Metodo que identifica si existen o no detalle de datos para la tabla seleccionado
	 * @param tablaAuditar
	 */
	public void consultarDetalleAuditoria(AuditoriaConfiguracion tablaAuditar) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("consultarDetalleAuditoriaConf.xhtml?id=" + tablaAuditar.getCodTablaAuditoria());
		} catch (IOException e) {
			logger.error("El metodo no redirecciono - consultarDetalleAuditoria()", e);
		}	
	}
	
	/*Metodo que retorna a la vista ConsultarDetalle*/
	public String action() {
		return "consultarDetalleAuditoriaConf.xhtml";
	}

	public boolean getFlgValidModificarCheck() {
		return flgValidModificarCheck;
	}

	public void setFlgValidModificarCheck(boolean flgValidModificarCheck) {
		this.flgValidModificarCheck = flgValidModificarCheck;
	}

	/**
	 * @return the auditoriaConfiguracion
	 */
	public AuditoriaConfiguracion getAuditoriaConfiguracion() {
		return auditoriaConfiguracion;
	}

	/**
	 * @param auditoriaConfiguracion the auditoriaConfiguracion to set
	 */
	public void setAuditoriaConfiguracion(AuditoriaConfiguracion auditoriaConfiguracion) {
		this.auditoriaConfiguracion = auditoriaConfiguracion;
	}

	/**
	 * @return the verBtnGurdar
	 */
	public boolean isVerBtnGurdar() {
		return verBtnGurdar;
	}

	/**
	 * @param verBtnGurdar the verBtnGurdar to set
	 */
	public void setVerBtnGurdar(boolean verBtnGurdar) {
		this.verBtnGurdar = verBtnGurdar;
	}

}