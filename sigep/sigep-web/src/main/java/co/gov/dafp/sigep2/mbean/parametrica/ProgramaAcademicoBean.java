
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
import co.gov.dafp.sigep2.entities.ProgramaAcademico;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.InstitucionEducativaExt;
import co.gov.dafp.sigep2.mbean.ext.ProgramaAcademicoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
* @version 1.0
* @Class ProgramaAcademicoBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Programas academicos
* @Date 06/05/2019
*/
@Named
@ViewScoped
@ManagedBean
public class ProgramaAcademicoBean extends BaseBean implements Serializable{
	private static final long serialVersionUID = 4406130899850330333L;
	
	private boolean blnHabilitaFormulario;
	private boolean blnCrearProgramaAcademico;
	private boolean blnModificarProgramaAcademico;
	private boolean blnConsultarProgramaAcademico;
	private boolean blnEliminarProgramaAcademico;
	private boolean blnCancelarFormulario;
	private boolean blnFlgActivo;
	private boolean blnInsExtranjera;
	
	private String  strAccionRealizada;
	private String 	strMensaje;
	private String 	strBusquedaProgramaAcademico;
	
	private List<ProgramaAcademicoExt> lstProgramaAcademico;
	
	private ProgramaAcademico 			infoProgramaAcademico;
	private Long 			  			codProgramaAcademicoAnt;
	private String			  			strProgramaAcademicoAnt;
	private InstitucionEducativa 		institucionEducativa;
	private List<InstitucionEducativaExt> 	lstInstitucionEducativa;

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
		lstProgramaAcademico =  new ArrayList<>();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		inicializarVariables();
		inicializarVariablesAuditoria();
		validarRolPermitido();
		filtrarProgramaAcademico();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);
		blnInsExtranjera 		= false;
		institucionEducativa	= new InstitucionEducativa();
	}
	
	/*Metodo que inicializa las variables de la vista*/
	public void  inicializarVariables() {
		blnHabilitaFormulario			=false;
		blnCrearProgramaAcademico		=false;
		blnModificarProgramaAcademico	=false;
		blnConsultarProgramaAcademico	=false;
		blnEliminarProgramaAcademico	=false;
		blnFlgActivo 					=false;
		strAccionRealizada 				="";
		strMensaje						="";
		strBusquedaProgramaAcademico 	= "";
		strProgramaAcademicoAnt			= "";
	}
	
	/*Metodo que inicializa las variables de auditoria de la aplicacion*/
	public void inicializarVariablesAuditoria() {
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		codAudCodRol = (int) this.getRolAuditoria().getId();
		codAudusuario = (int) usuarioSesion.getId();
	}
	
	/* Metodo que valida si el usuario que intenta ingresar a la opcion si tiene los permisos para acceder.*/
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
			logger.error("void init() usuarioTieneRolAsignado", e);
		}
	}
	
	/* Metodo que guarda la modificacion o creación de un programa academico */
	public void guardarProgramaAcademico() {
		infoProgramaAcademico.setAudCodRol(codAudCodRol);
		infoProgramaAcademico.setAudCodUsuario(codAudusuario.longValue());
		if(blnModificarProgramaAcademico)
			infoProgramaAcademico.setAudAccion(codAudAccionUpdate);
		else
			infoProgramaAcademico.setAudAccion(codAudAccionInsert);
		if(blnFlgActivo)
			infoProgramaAcademico.setFlgActivo((short)1);
		else
			infoProgramaAcademico.setFlgActivo((short)0);
		infoProgramaAcademico.setAudFechaActualizacion(new Date());
		infoProgramaAcademico.setCodInstitucion(institucionEducativa.getCodInstitucionEducativa()!= null ? institucionEducativa.getCodInstitucionEducativa().longValue() : null );
		boolean programaDuplicado = validarProgramaAcademicoDuplicado();
		if(programaDuplicado) 
			return;
		
		ProgramaAcademico programaGuardado = ComunicacionServiciosAdmin.guardarProgramaAcademico(infoProgramaAcademico);
		if(!programaGuardado.isError()) {
			if(blnModificarProgramaAcademico)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_SITUACION_PARTICULAR_MODIFICADO_EXITO);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
			inicializarVariables();
			filtrarProgramaAcademico();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRO_NOGUARDADO);
		}
		blnInsExtranjera = false;
	}
	
	/** Metodo que valida si existen programas academicos con el mismo codigo
	 * que el ingresado al momento de crear o de modificar.
	 * @return blnExistePrograma
	 */
	public boolean validarProgramaAcademicoDuplicado() {
		boolean blnExistePrograma = false;
		if((infoProgramaAcademico.getCodTituloAcademico()==null 
				&& infoProgramaAcademico.getCodProgramaAcademico()!= null) 
			||(infoProgramaAcademico.getCodTituloAcademico()!=null 
				&& (infoProgramaAcademico.getCodProgramaAcademico()!= null &&
					!infoProgramaAcademico.getCodProgramaAcademico().equals(codProgramaAcademicoAnt)))) {
			ProgramaAcademico fil = new ProgramaAcademico();
			fil.setCodProgramaAcademico(this.infoProgramaAcademico.getCodProgramaAcademico());
			List<ProgramaAcademico> programas = ComunicacionServiciosAdmin.obtenerProgramaAcademicoDuplicado(fil);
			if(programas != null && !programas.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_CODIGO_PROGRAMA_DUPLICADO );
				blnExistePrograma = true;
			}
		}
		if(blnInsExtranjera && 
			(infoProgramaAcademico.getCodTituloAcademico()==null 
				|| !(infoProgramaAcademico.getNombreProgramaAcademico().equals(strProgramaAcademicoAnt)))) {
			ProgramaAcademico fil = new ProgramaAcademico();
			fil.setNombreProgramaAcademico(infoProgramaAcademico.getNombreProgramaAcademico());
			fil.setCodInstitucion(infoProgramaAcademico.getCodInstitucion());
			List<ProgramaAcademico> programas = ComunicacionServiciosAdmin.obtenerProgramaAcademicoDuplicado(fil);
			if(programas != null && !programas.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_PROGRAMA_ACADEMICO_DUPLICADO);
				blnExistePrograma = true;
			}
		}
		return blnExistePrograma;
	}
	
	/* Metodo que habilita formulario para la creacion de programa academico*/
	public void crearProgramaAcademico() {
		inicializarVariables();
		infoProgramaAcademico 		= new ProgramaAcademico();
		blnHabilitaFormulario 		= true;
		blnCrearProgramaAcademico	= true;
		institucionEducativa	 	= new InstitucionEducativa();
		blnFlgActivo 				= true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_CREAR_PROGRAMA_ACADEMICO, getLocale());
	}
	
	/* Metodo que habilita formulario para la modificacion de programa academico*/
	public void modificarProgramaAcademico(ProgramaAcademico dataProgramaAcademico) {
		inicializarVariables();
		infoProgramaAcademico 	= dataProgramaAcademico;
		codProgramaAcademicoAnt = dataProgramaAcademico.getCodProgramaAcademico();
		strProgramaAcademicoAnt	= dataProgramaAcademico.getNombreProgramaAcademico();
		if(infoProgramaAcademico.getFlgActivo() != null) {
			if(infoProgramaAcademico.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		if(infoProgramaAcademico != null && infoProgramaAcademico.getCodInstitucion() != null) {
			obtenerInstitucionEducativaFiltroPorID(infoProgramaAcademico.getCodInstitucion());
		}
		validarInstitucionExtanjera();
		blnHabilitaFormulario = true;
		blnModificarProgramaAcademico=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_MODIFICAR_PROGRAMA_ACADEMICO, getLocale());
	}
	/* Metodo que habilita formulario para la consultar de programa academico*/
	public void consultarProgramaAcademico(ProgramaAcademico dataProgramaAcademico) {
		infoProgramaAcademico = dataProgramaAcademico;
		inicializarVariables();
		if(infoProgramaAcademico.getFlgActivo() != null) {
			if(infoProgramaAcademico.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		if(infoProgramaAcademico != null && infoProgramaAcademico.getCodInstitucion() != null) {
			obtenerInstitucionEducativaFiltroPorID(infoProgramaAcademico.getCodInstitucion());
		}
		validarInstitucionExtanjera();
		blnHabilitaFormulario = true;
		blnConsultarProgramaAcademico = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_CONSULTAR_PROGRAMA_ACADEMICO, getLocale());
	}

	/*Metodo que habilita formulario para la consultar de programa academico*/
	public void eliminarProgramaAcademico() {
		inicializarVariables();
		blnEliminarProgramaAcademico=true;
		strAccionRealizada = "Eliminar Programa Académico";
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_PAIS_ELIMINAR_CONFIRMAR, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarProgramaAcademico').show();");
	}
	
	/*Metodo que cancela la accion realizada*/
	public void cancelarFormulario() {
		blnCancelarFormulario = true;
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DESCARTAR_CAMBIOS, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarProgramaAcademico').show();");
	}
	
	/**
	 * Metodo que filtra programas academicos según lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return
	 */
	public List<String> listarProgramaAcademicoFiltro(String entidadPrivQuery){
		List<String> filtroPrograma = new ArrayList<>();
		for(ProgramaAcademico programa : lstProgramaAcademico ) {
			if(programa.getNombreProgramaAcademico().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroPrograma.add(programa.getNombreProgramaAcademico());
			}
		}
		return filtroPrograma;	
	}
	
	/* Metodo que precesa respuesta dependiendo de la acción realizada*/
	public void procesarRespuestaConfirmacion() {
		if(blnEliminarProgramaAcademico){
			eliminarProgramaAcademicoConfirmado();
		}else if(blnCancelarFormulario||blnConsultarProgramaAcademico){
			cancelarFormularioConfirmado();
		}
	}
	
	/**
	 * Metodo que elimina un programa academico donde:
	 * 1. Realiza un eliminado logico y desactiva el programa academico, con la opcion de volverlo a activar despues.
	 * @param detalleParam
	 */
	public void eliminarProgramaAcademicoConfirmado() {
		eliminarLogicamenteProgramaAcademico();
		inicializarVariables();
		filtrarProgramaAcademico();
	}

	/**
	 * Metodo que elimina un programa academico de manera logica. 
	 * FLG_ACTIVO = 0
	 */
	public void eliminarLogicamenteProgramaAcademico() {
		infoProgramaAcademico.setAudCodRol(codAudCodRol);
		infoProgramaAcademico.setAudCodUsuario(codAudusuario.longValue());
		infoProgramaAcademico.setAudAccion(codAudAccionDelete);
		infoProgramaAcademico.setFlgActivo((short)0);
		infoProgramaAcademico.setAudFechaActualizacion(new Date());	
		ProgramaAcademico programaAcademico = ComunicacionServiciosAdmin.guardarProgramaAcademico(infoProgramaAcademico);
		if(!programaAcademico.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
			inicializarVariables();
			filtrarProgramaAcademico();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.BR_MSG_ELIMINACION_FALLIDA);
		}
	}
	
	/*Metodo que se ejecuta una vez el usuario ya haya confirmado la cancelacion del formulario*/
	public void cancelarFormularioConfirmado() {
		inicializarVariables();
		filtrarProgramaAcademico();
	}
	
	/*Metodo que filtra los programa academico*/
	public void filtrarProgramaAcademico() {
		ProgramaAcademico filtroPrograma = new ProgramaAcademico();
		filtroPrograma.setNombreProgramaAcademico(strBusquedaProgramaAcademico);
		lstProgramaAcademico =ComunicacionServiciosAdmin.obtenerProgramaAcademicoFiltro(filtroPrograma);	
	}

	
//	/*Metodo que valida si la institucion educativa es extranjera*/
	public void validarInstitucionExtanjera(){
		if(institucionEducativa.getCodInstitucionEducativa() != null) {
			InstitucionEducativa institucion = ComunicacionServiciosSis.getInstitucionEducativaId(new BigDecimal(institucionEducativa.getCodInstitucionEducativa()));
			if(institucion.getFlgInstExtranjera() != null && institucion.getFlgInstExtranjera() == 1) {
				blnInsExtranjera = true;
			}else {
				blnInsExtranjera = false;
			}
		}
	}
	

	/**
	 * Metodo que filtra la Institucion Educativa segun lo ingresado por el usuario
	 * @param institucionPrivQuery
	 * @return filtroInsitucionEducativa
	 */
	public List<InstitucionEducativaExt> listarInsitucionEducativaFiltro(String institucionPrivQuery){
		InstitucionEducativa filtroInstitucionEducativa = new InstitucionEducativa();
		if(!institucionPrivQuery.equals("")) {
			filtroInstitucionEducativa.setNombreInstitucion(institucionPrivQuery);	
		}
		filtroInstitucionEducativa.setFlgActivo((short)1);
		lstInstitucionEducativa = ComunicacionServiciosAdmin.obtenerInstitucionEducativaFiltro(filtroInstitucionEducativa);
		if(lstInstitucionEducativa.isEmpty()) {
	    	 return new ArrayList<>();
	     }
		return lstInstitucionEducativa;
	}
	
	/**
	 * Metodo que filtra la insitucion educativa por id
	 * @param entidadPrivQuery
	 * @return filtroDependenciaHV
	 */
	public void obtenerInstitucionEducativaFiltroPorID(long codInstitucionEducativa){
		institucionEducativa = ComunicacionServiciosSis.getInstitucionEducaporId(codInstitucionEducativa);
	}
	
	/* Get y Set de Variables*/
	
	/**
	 * @return the blnHabilitaFormulario
	 */
	public boolean isBlnHabilitaFormulario() {
		return blnHabilitaFormulario;
	}

	/**
	 * @return the blnCrearProgramaAcademico
	 */
	public boolean isBlnCrearProgramaAcademico() {
		return blnCrearProgramaAcademico;
	}

	/**
	 * @param blnCrearProgramaAcademico the blnCrearProgramaAcademico to set
	 */
	public void setBlnCrearProgramaAcademico(boolean blnCrearProgramaAcademico) {
		this.blnCrearProgramaAcademico = blnCrearProgramaAcademico;
	}

	/**
	 * @return the blnModificarProgramaAcademico
	 */
	public boolean isBlnModificarProgramaAcademico() {
		return blnModificarProgramaAcademico;
	}

	/**
	 * @param blnModificarProgramaAcademico the blnModificarProgramaAcademico to set
	 */
	public void setBlnModificarProgramaAcademico(boolean blnModificarProgramaAcademico) {
		this.blnModificarProgramaAcademico = blnModificarProgramaAcademico;
	}

	/**
	 * @return the blnConsultarProgramaAcademico
	 */
	public boolean isBlnConsultarProgramaAcademico() {
		return blnConsultarProgramaAcademico;
	}

	/**
	 * @param blnConsultarProgramaAcademico the blnConsultarProgramaAcademico to set
	 */
	public void setBlnConsultarProgramaAcademico(boolean blnConsultarProgramaAcademico) {
		this.blnConsultarProgramaAcademico = blnConsultarProgramaAcademico;
	}

	/**
	 * @return the blnEliminarProgramaAcademico
	 */
	public boolean isBlnEliminarProgramaAcademico() {
		return blnEliminarProgramaAcademico;
	}

	/**
	 * @param blnEliminarProgramaAcademico the blnEliminarProgramaAcademico to set
	 */
	public void setBlnEliminarProgramaAcademico(boolean blnEliminarProgramaAcademico) {
		this.blnEliminarProgramaAcademico = blnEliminarProgramaAcademico;
	}

	/**
	 * @return the strBusquedaProgramaAcademico
	 */
	public String getStrBusquedaProgramaAcademico() {
		return strBusquedaProgramaAcademico;
	}

	/**
	 * @param strBusquedaProgramaAcademico the strBusquedaProgramaAcademico to set
	 */
	public void setStrBusquedaProgramaAcademico(String strBusquedaProgramaAcademico) {
		this.strBusquedaProgramaAcademico = strBusquedaProgramaAcademico;
	}

	/**
	 * @param blnHabilitaFormulario the blnHabilitaFormulario to set
	 */
	public void setBlnHabilitaFormulario(boolean blnHabilitaFormulario) {
		this.blnHabilitaFormulario = blnHabilitaFormulario;
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
	
	public List<ProgramaAcademicoExt> getLstProgramaAcademico() {
		return lstProgramaAcademico;
	}

	public void setLstProgramaAcademico(List<ProgramaAcademicoExt> lstProgramaAcademico) {
		this.lstProgramaAcademico = lstProgramaAcademico;
	}

	/**
	 * @return the infoProgramaAcademico
	 */
	public ProgramaAcademico getInfoProgramaAcademico() {
		return infoProgramaAcademico;
	}

	/**
	 * @param infoProgramaAcademico the infoProgramaAcademico to set
	 */
	public void setInfoProgramaAcademico(ProgramaAcademico infoProgramaAcademico) {
		this.infoProgramaAcademico = infoProgramaAcademico;
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
	 * @return the codProgramaAcademicoAnt
	 */
	public Long getCodProgramaAcademicoAnt() {
		return codProgramaAcademicoAnt;
	}

	/**
	 * @param codProgramaAcademicoAnt the codProgramaAcademicoAnt to set
	 */
	public void setCodProgramaAcademicoAnt(Long codProgramaAcademicoAnt) {
		this.codProgramaAcademicoAnt = codProgramaAcademicoAnt;
	}
	

	/**
	 * @return the blnInsExtranjera
	 */
	public boolean isBlnInsExtranjera() {
		return blnInsExtranjera;
	}

	/**
	 * @param blnInsExtranjera the blnInsExtranjera to set
	 */
	public void setBlnInsExtranjera(boolean blnInsExtranjera) {
		this.blnInsExtranjera = blnInsExtranjera;
	}	

	/**
	 * @return the strProgramaAcademicoAnt
	 */
	public String getStrProgramaAcademicoAnt() {
		return strProgramaAcademicoAnt;
	}

	/**
	 * @param strProgramaAcademicoAnt the strProgramaAcademicoAnt to set
	 */
	public void setStrProgramaAcademicoAnt(String strProgramaAcademicoAnt) {
		this.strProgramaAcademicoAnt = strProgramaAcademicoAnt;
	}

	/**
	 * @return the institucionEducativa
	 */
	public InstitucionEducativa getInstitucionEducativa() {
		return institucionEducativa;
	}

	/**
	 * @param institucionEducativa the institucionEducativa to set
	 */
	public void setInstitucionEducativa(InstitucionEducativa institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}

	public List<InstitucionEducativaExt> getLstInstitucionEducativa() {
		return lstInstitucionEducativa;
	}

	public void setLstInstitucionEducativa(List<InstitucionEducativaExt> lstInstitucionEducativa) {
		this.lstInstitucionEducativa = lstInstitucionEducativa;
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

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}
		
}
