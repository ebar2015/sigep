
package co.gov.dafp.sigep2.mbean.parametrica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.InstitucionEducativaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
* @author Andrés Felipe Jaramillo Arenas
* @version 1.0
* @Class InstitucionBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Parametricas
* @Date 06/05/2019
*/
@Named
@ViewScoped
@ManagedBean
public class InstitucionBean extends BaseBean implements Serializable{
	private static final long serialVersionUID = 4406130899850330333L;
	
	private boolean blnHabilitaFormulario;
	private boolean blnCrearInstitucionEducativa;
	private boolean blnModificarInstitucionEducativa;
	private boolean blnConsultarInstitucionEducativa;
	private boolean blnEliminarInstitucionEducativa;
	private boolean blnCancelarFormulario;
	private boolean blnFlgActivo;
	private boolean blnFlgExtranjera;
	private boolean blnBloquearCodigoInstitucion;
	
	private String  strAccionRealizada;
	private String 	strMensaje;
	private String 	strBusquedaInstitucionEducativa;
	
	private List<InstitucionEducativaExt> lstInstitucionEducativa;
	
	private InstitucionEducativa infoInstitucionEducativa;
	private Long 				 codInstitucionEducativaAnt ;
	private String				 nombreInstitucionAnt;

	/*Variables de auditoria*/
	private Integer codAudCodRol;
	private Integer codAudusuario; 
	private Integer codAudAccionInsert;
	private Integer codAudAccionUpdate;
	private Integer codAudAccionDelete;
	
	/*Variables de sesión*/
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
	
	@PostConstruct
	public void init() {
		lstInstitucionEducativa =  new ArrayList<>();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		inicializarVariables();
		inicializarVariablesAuditoria();
		validarRolPermitido();
		filtrarInstitucionEducativa();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	/* Metodo que inicializa las variables de la vista*/
	public void  inicializarVariables() {
		blnHabilitaFormulario				= false;
		blnCrearInstitucionEducativa		= false;
		blnModificarInstitucionEducativa	= false;
		blnConsultarInstitucionEducativa	= false;
		blnEliminarInstitucionEducativa		= false;
		blnFlgActivo 						= false;
		blnFlgExtranjera 					= false;
		blnBloquearCodigoInstitucion 		= false;
		strAccionRealizada 					= "";
		strMensaje							= "";
		strBusquedaInstitucionEducativa 	= null;
		nombreInstitucionAnt				= "";
		
	}
	
	/* Metodo que inicializa las variables de auditoria de la aplicacion*/
	public void inicializarVariablesAuditoria() {
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		codAudCodRol = (int) this.getRolAuditoria().getId();
		codAudusuario = (int) usuarioSesion.getId();
	}
	
	/**
	 * Metodo que valida si el usuario que intenta ingresar a la opcion si tiene los permisos para acceder.
	 */
	public void validarRolPermitido() {
		try {
			Boolean validRold = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_TECNICO );
			if (!validRold) {
				mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
						TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
						"window.location.assign('../persona/informacionPersonal.xhtml?faces-redirect=true')"); 
	    		return;
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignadoSinJerarquia", e);
		}
	}
	
	/**
	 * Metodo que guarda la modificacion o creación de un Institucion Educativa
	 */
	public void guardarInstitucionEducativa() {
		infoInstitucionEducativa.setAudCodRol(codAudCodRol);
		infoInstitucionEducativa.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoInstitucionEducativa.setAudFechaActualizacion(new Date());
		if(blnModificarInstitucionEducativa)
			infoInstitucionEducativa.setAudAccion(codAudAccionUpdate);
		else
			infoInstitucionEducativa.setAudAccion(codAudAccionInsert);
		
		if(blnFlgActivo)
			infoInstitucionEducativa.setFlgActivo((short)1);
		else
			infoInstitucionEducativa.setFlgActivo((short)0);
		
		if(blnFlgExtranjera)
			infoInstitucionEducativa.setFlgInstExtranjera((short)1);
		else
			infoInstitucionEducativa.setFlgInstExtranjera((short)0);
	
		boolean insitucionDuplicado = validarInstitucionEducativaDuplicado();
		if(insitucionDuplicado) 
			return;
		InstitucionEducativa institucionGuardado = ComunicacionServiciosAdmin.guardarInstitucionEducativa(infoInstitucionEducativa);
		if(!institucionGuardado.isError()) {
			if(blnModificarInstitucionEducativa)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_SITUACION_PARTICULAR_MODIFICADO_EXITO);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
			inicializarVariables();
			filtrarInstitucionEducativa();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRO_NOGUARDADO);
		}
	}
	
	
	/** Metodo que valida si existen instituciones con el mismo codigo
	 * que el ingresado al momento de crear o de modificar.
	 * @return blnExisteInstitucion
	 */
	public boolean validarInstitucionEducativaDuplicado() {
		boolean existeInstitucion = false;
		if(infoInstitucionEducativa.getCodInstitucionEducativa()==null ||
				(infoInstitucionEducativa.getCodInstitucionEducativa() != null && codInstitucionEducativaAnt != null
					&&(!infoInstitucionEducativa.getCodInstitucionMen().equals(codInstitucionEducativaAnt)))) {
			if(infoInstitucionEducativa.getCodInstitucionMen()!= null) {
				InstitucionEducativa fil = new InstitucionEducativa();
				fil.setCodInstitucionMen(this.infoInstitucionEducativa.getCodInstitucionMen());
				List<InstitucionEducativaExt> instituciones = ComunicacionServiciosAdmin.obtenerInstitucionEducativaFiltro(fil);
				if(instituciones != null && !instituciones.isEmpty()) {
					this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_CODIGO_INSTITUCION_DUPLICADO);
					existeInstitucion = true;
				}
			}	
		}
		
		if(blnFlgExtranjera && 
				(!infoInstitucionEducativa.getNombreInstitucion().equals(nombreInstitucionAnt) 
				|| infoInstitucionEducativa.getCodInstitucionEducativa() == null)) {
				InstitucionEducativa fil = new InstitucionEducativa();
				fil.setNombreInstitucion(infoInstitucionEducativa.getNombreInstitucion());
				fil.setFlgInstExtranjera((short) 1);
				List<InstitucionEducativaExt> instituciones = ComunicacionServiciosAdmin.obtenerInstitucionEducativaFiltro(fil);
				if(instituciones != null && !instituciones.isEmpty()) {
					this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_INSTITUCION_EXTRANJERA_DUPLICADO);
					existeInstitucion = true;
				}
			}
		
		
		return existeInstitucion;
	}
	
	/* Metodo que habilita formulario para la creacion de Institucion Educativa*/
	public void crearInstitucionEducativa() {
		inicializarVariables();
		infoInstitucionEducativa = new InstitucionEducativa();
		blnHabilitaFormulario = true;
		blnCrearInstitucionEducativa=true;
		blnFlgActivo = true;
		strAccionRealizada = TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_ADD_NEW, getLocale()) 
				+ " " + TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_INSTITUCION, getLocale());
	}
	
	/*Metodo que habilita formulario para la modificacion de Institucion Educativa*/
	public void modificarInstitucionEducativa(InstitucionEducativa dataInstitucionEducativa) {
		inicializarVariables();
		infoInstitucionEducativa 	= dataInstitucionEducativa;
		codInstitucionEducativaAnt 	= dataInstitucionEducativa.getCodInstitucionMen();
		nombreInstitucionAnt		= dataInstitucionEducativa.getNombreInstitucion();
		validarFlgActivo();
		validarFlgInstExtranjera();
		validarInstitucionExtanjera();
		blnHabilitaFormulario = true;
		blnModificarInstitucionEducativa=true;
		strAccionRealizada = TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_EDIT, getLocale()) 
				+ " "+ TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_INSTITUCION, getLocale());
	}
	
	
	/* Metodo que habilita formulario para la consultar de Institucion Educativa*/
	public void consultarInstitucionEducativa(InstitucionEducativa dataInstitucionEducativa) {
		infoInstitucionEducativa = dataInstitucionEducativa;
		inicializarVariables();
		validarFlgActivo();
		validarFlgInstExtranjera();
		validarInstitucionExtanjera();
		blnHabilitaFormulario = true;
		blnConsultarInstitucionEducativa = true;
		strAccionRealizada = TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.BTN_TABLA_VER, getLocale()) 
				+ " "+TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_INSTITUCION, getLocale());
	}

	/*Metodo que habilita formulario para la consultar de Institucion Educativa*/
	public void eliminarInstitucionEducativa() {
		inicializarVariables();
		blnEliminarInstitucionEducativa=true;
		strAccionRealizada = "Eliminar Institución Educativa";
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_PAIS_ELIMINAR_CONFIRMAR, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarInstitucionEducativa').show();");
	}
	
	/*Metodo que cancela la accion realizada*/
	public void cancelarFormulario() {
		blnCancelarFormulario = true;
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DESCARTAR_CAMBIOS, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarInstitucionEducativa').show();");
	}
	
	/*Metodo que valida el estado de FLG_ACTIVO*/
	public void validarFlgActivo() {
		if(infoInstitucionEducativa.getFlgActivo() != null) {
			if(infoInstitucionEducativa.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	
	/*Metodo que valida el estado de FLG_INST_EXTRANJERA */
	public void validarFlgInstExtranjera() {
		if(infoInstitucionEducativa.getFlgInstExtranjera() != null) {
			if(infoInstitucionEducativa.getFlgInstExtranjera().intValue() ==  1)
				blnFlgExtranjera = true;
			else
				blnFlgExtranjera = false;
		}
	}
	
	/**
	 * Metodo que filtra programas academicos según lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return List<String> filtroInstitucion
	 */
	public List<String> listarInstitucionEducativaFiltro(String entidadPrivQuery){
		List<String> filtroInstitucion = new ArrayList<>();
		for(InstitucionEducativa institucion : lstInstitucionEducativa ) {
			if(institucion.getNombreInstitucion().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroInstitucion.add(institucion.getNombreInstitucion());
			}
		}
		return filtroInstitucion;
	}
	
	/*Metodo que precesa respuesta dependiendo de la acción realizada*/
	public void procesarRespuestaConfirmacion() {
		if(blnEliminarInstitucionEducativa){
			eliminarInstitucionEducativaConfirmado();
		}else if(blnCancelarFormulario||blnConsultarInstitucionEducativa){
			cancelarFormularioConfirmado();
		}
	}
	
	/**
	 * Metodo que elimina un pais donde:
	 * 1. Si la parametrica a eliminar no tiene referencia en el sistema, el metodo realiza un eliminado fisico del parametro. 
	 * Sino, el metodo realiza un eliminado logico y desactiva el Institucion Educativa, con la opcion de volverlo a activar despues.
	 * @param detalleParam
	 */
	public void eliminarInstitucionEducativaConfirmado() {
		eliminarLogicamenteInstitucionEducativa();
		inicializarVariables();
		filtrarInstitucionEducativa();
	}

	/**
	 * Metodo que elimina un pais de manera logica. 
	 * FLG_ACTIVO = 0
	 */
	public void eliminarLogicamenteInstitucionEducativa() {
		infoInstitucionEducativa.setAudCodRol(codAudCodRol);
		infoInstitucionEducativa.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoInstitucionEducativa.setAudAccion(codAudAccionDelete);
		infoInstitucionEducativa.setFlgActivo((short)0);
		infoInstitucionEducativa.setAudFechaActualizacion(new Date());	
		InstitucionEducativa insitucionEducativa = ComunicacionServiciosAdmin.guardarInstitucionEducativa(infoInstitucionEducativa);
		if(!insitucionEducativa.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
			inicializarVariables();
			filtrarInstitucionEducativa();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.BR_MSG_ELIMINACION_FALLIDA);
		}
	}
	
	/*Metodo que se ejecuta una vez el usuario ya haya confirmado la cancelacion del formulario*/
	public void cancelarFormularioConfirmado() {
		inicializarVariables();
		filtrarInstitucionEducativa();
	}
	
	/*Metodo que filtra los Institucion Educativa*/
	public void filtrarInstitucionEducativa() {
		InstitucionEducativa filtroInsitucionEducativa = new InstitucionEducativa();
		filtroInsitucionEducativa.setNombreInstitucion(strBusquedaInstitucionEducativa);
		lstInstitucionEducativa =ComunicacionServiciosAdmin.obtenerInstitucionEducativaFiltro(filtroInsitucionEducativa);
	}
	
	/*Metodo que valida si la institucion a crear o modificar es extranjera, de ser asi, bloquea el campo codigo de institucion dado por el ministerio*/
	public void validarInstitucionExtanjera() {
		if(blnFlgExtranjera) {
			blnBloquearCodigoInstitucion = true;
			infoInstitucionEducativa.setCodInstitucionMen(null);
		}else {
			blnBloquearCodigoInstitucion = false;
		}
	}

	/* Get y Set de Variables*/

	/**
	 * @return the blnHabilitaFormuario
	 */
	public boolean isBlnHabilitaFormulario() {
		return blnHabilitaFormulario;
	}
	
	/**
	 * @param blnHabilitaFormulario the blnHabilitaFormulario to set
	 */
	public void setBlnHabilitaFormulario(boolean blnHabilitaFormulario) {
		this.blnHabilitaFormulario = blnHabilitaFormulario;
	}

	/**
	 * @return the blnCrearInstitucionEducativa
	 */
	public boolean isBlnCrearInstitucionEducativa() {
		return blnCrearInstitucionEducativa;
	}

	/**
	 * @param blnCrearInstitucionEducativa the blnCrearInstitucionEducativa to set
	 */
	public void setBlnCrearInstitucionEducativa(boolean blnCrearInstitucionEducativa) {
		this.blnCrearInstitucionEducativa = blnCrearInstitucionEducativa;
	}

	/**
	 * @return the blnModificarInstitucionEducativa
	 */
	public boolean isBlnModificarInstitucionEducativa() {
		return blnModificarInstitucionEducativa;
	}

	/**
	 * @param blnModificarInstitucionEducativa the blnModificarInstitucionEducativa to set
	 */
	public void setBlnModificarInstitucionEducativa(boolean blnModificarInstitucionEducativa) {
		this.blnModificarInstitucionEducativa = blnModificarInstitucionEducativa;
	}

	/**
	 * @return the blnConsultarInstitucionEducativa
	 */
	public boolean isBlnConsultarInstitucionEducativa() {
		return blnConsultarInstitucionEducativa;
	}

	/**
	 * @param blnConsultarInstitucionEducativa the blnConsultarInstitucionEducativa to set
	 */
	public void setBlnConsultarInstitucionEducativa(boolean blnConsultarInstitucionEducativa) {
		this.blnConsultarInstitucionEducativa = blnConsultarInstitucionEducativa;
	}

	/**
	 * @return the blnEliminarInstitucionEducativa
	 */
	public boolean isBlnEliminarInstitucionEducativa() {
		return blnEliminarInstitucionEducativa;
	}

	/**
	 * @param blnEliminarInstitucionEducativa the blnEliminarInstitucionEducativa to set
	 */
	public void setBlnEliminarInstitucionEducativa(boolean blnEliminarInstitucionEducativa) {
		this.blnEliminarInstitucionEducativa = blnEliminarInstitucionEducativa;
	}

	/**
	 * @return the blnCancelarFormulario
	 */
	public boolean isBlnCancelarFormulario() {
		return blnCancelarFormulario;
	}

	/**
	 * @param blnCancelarFormulario the blnCancelarFormulario to set
	 */
	public void setBlnCancelarFormulario(boolean blnCancelarFormulario) {
		this.blnCancelarFormulario = blnCancelarFormulario;
	}

	/**
	 * @return the blnFlgActivo
	 */
	public boolean isBlnFlgActivo() {
		return blnFlgActivo;
	}

	/**
	 * @param blnFlgActivo the blnFlgActivo to set
	 */
	public void setBlnFlgActivo(boolean blnFlgActivo) {
		this.blnFlgActivo = blnFlgActivo;
	}

	/**
	 * @return the strAccionRealizada
	 */
	public String getStrAccionRealizada() {
		return strAccionRealizada;
	}

	/**
	 * @param strAccionRealizada the strAccionRealizada to set
	 */
	public void setStrAccionRealizada(String strAccionRealizada) {
		this.strAccionRealizada = strAccionRealizada;
	}

	/**
	 * @return the strMensaje
	 */
	public String getStrMensaje() {
		return strMensaje;
	}

	/**
	 * @param strMensaje the strMensaje to set
	 */
	public void setStrMensaje(String strMensaje) {
		this.strMensaje = strMensaje;
	}

	/**
	 * @return the strBusquedaInstitucionEducativa
	 */
	public String getStrBusquedaInstitucionEducativa() {
		return strBusquedaInstitucionEducativa;
	}

	/**
	 * @param strBusquedaInstitucionEducativa the strBusquedaInstitucionEducativa to set
	 */
	public void setStrBusquedaInstitucionEducativa(String strBusquedaInstitucionEducativa) {
		this.strBusquedaInstitucionEducativa = strBusquedaInstitucionEducativa;
	}
	
	public List<InstitucionEducativaExt> getLstInstitucionEducativa() {
		return lstInstitucionEducativa;
	}

	public void setLstInstitucionEducativa(List<InstitucionEducativaExt> lstInstitucionEducativa) {
		this.lstInstitucionEducativa = lstInstitucionEducativa;
	}

	/**
	 * @return the infoInstitucionEducativa
	 */
	public InstitucionEducativa getInfoInstitucionEducativa() {
		return infoInstitucionEducativa;
	}

	/**
	 * @param infoInstitucionEducativa the infoInstitucionEducativa to set
	 */
	public void setInfoInstitucionEducativa(InstitucionEducativa infoInstitucionEducativa) {
		this.infoInstitucionEducativa = infoInstitucionEducativa;
	}
	
	/**
	 * @return the codInstitucionEducativaAnt
	 */
	public Long getCodInstitucionEducativaAnt() {
		return codInstitucionEducativaAnt;
	}

	/**
	 * @param codInstitucionEducativaAnt the codInstitucionEducativaAnt to set
	 */
	public void setCodInstitucionEducativaAnt(Long codInstitucionEducativaAnt) {
		this.codInstitucionEducativaAnt = codInstitucionEducativaAnt;
	}
	

	/**
	 * @return the blnFlgExtranjera
	 */
	public boolean isBlnFlgExtranjera() {
		return blnFlgExtranjera;
	}

	/**
	 * @param blnFlgExtranjera the blnFlgExtranjera to set
	 */
	public void setBlnFlgExtranjera(boolean blnFlgExtranjera) {
		this.blnFlgExtranjera = blnFlgExtranjera;
	}
	
	/**
	 * @return the blnBloquearCodigoInstitucion
	 */
	public boolean isBlnBloquearCodigoInstitucion() {
		return blnBloquearCodigoInstitucion;
	}

	/**
	 * @param blnBloquearCodigoInstitucion the blnBloquearCodigoInstitucion to set
	 */
	public void setBlnBloquearCodigoInstitucion(boolean blnBloquearCodigoInstitucion) {
		this.blnBloquearCodigoInstitucion = blnBloquearCodigoInstitucion;
	}
	
	/**
	 * @return the nombreInstitucionAnt
	 */
	public String getNombreInstitucionAnt() {
		return nombreInstitucionAnt;
	}

	/**
	 * @param nombreInstitucionAnt the nombreInstitucionAnt to set
	 */
	public void setNombreInstitucionAnt(String nombreInstitucionAnt) {
		this.nombreInstitucionAnt = nombreInstitucionAnt;
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

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
		
}
