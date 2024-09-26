
package co.gov.dafp.sigep2.mbean.parametrica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.primefaces.model.LazyDataModel;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.datamodel.DependenciaHojaVidaLazyDataModel;
import co.gov.dafp.sigep2.entities.DependenciaHojaVida;
import co.gov.dafp.sigep2.entities.DependenciaHojaVidaExt;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;

/**
* @author Maria Alejandra Colorado
* @version 1.0
* @Class DependenciaHojaVidaBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Parametrica Dependencia hoja de Vida
* Fecha: 30/04/2020
*/
@Named
@ViewScoped
@ManagedBean
public class DependenciaHojaVidaBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 7058778322378462484L;
	
	private boolean blnHabilitaFormulario;
	private boolean blnCrearDependenciaHV;
	private boolean blnModificarDependenciaHV;
	private boolean blnConsultarDependenciaHV;
	private boolean blnEliminarDependenciaHV;
	private boolean blnCancelarFormulario;
	private boolean blnFlgActivo;
	
	private String  strAccionRealizada;
	private String 	strMensaje;
	private String 	strBusquedaDependenciaHV;
	
	private LazyDataModel<DependenciaHojaVidaExt> lstDependenciaHV;
	
	private DependenciaHojaVidaExt infoDependenciaHV;
	private String 		  		nombreDependenciaHVAnt;

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
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		validarSeguridadRoles();
		inicializarVariables();
		inicializarVariablesAuditoria();
		validarRolPermitido();
		filtrarDepedenciaHV();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	/*Metodo que inicializa las variables de la vista*/
	public void  inicializarVariables() {
		blnHabilitaFormulario		=false;
		blnCrearDependenciaHV		=false;
		blnModificarDependenciaHV	=false;
		blnConsultarDependenciaHV	=false;
		blnEliminarDependenciaHV	=false;
		blnFlgActivo 				=false;
		strAccionRealizada 			="";
		strMensaje					="";
		strBusquedaDependenciaHV 	= null;
	}
	
	/*Metodo que inicializa las variables de auditoria de la aplicacion*/
	public void inicializarVariablesAuditoria() {
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		codAudCodRol 	   = (int) this.getRolAuditoria().getId();
		codAudusuario      = (int) usuarioSesion.getId();
	}
	
	/*Metodo que se encarga de validar la seguridad de roles */
	public void validarSeguridadRoles() {
		FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);
	}
	
	/*Metodo que valida si el usuario que intenta ingresar a la opcion si tiene los permisos para acceder.*/
	public void validarRolPermitido() {
		boolean validRol= validarRecursoxusuario();
		if (!validRol) {
			mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"window.location.assign('../persona/informacionPersonal.xhtml?faces-redirect=true')"); 
    		return;
		}				
	}
	
	/*Metodo que guarda la modificacion o creación de una dependencia*/
	public void guardarDependenciaHV() {
		infoDependenciaHV.setAudCodRol(new BigDecimal(codAudCodRol));
		infoDependenciaHV.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoDependenciaHV.setAudFechaActualizacion(new Date());
		if(blnModificarDependenciaHV)
			infoDependenciaHV.setAudAccion(new BigDecimal(codAudAccionUpdate));
		else
			infoDependenciaHV.setAudAccion(new BigDecimal(codAudAccionInsert));		
		if(blnFlgActivo)
			infoDependenciaHV.setFlgActivo((short)1);
		else
			infoDependenciaHV.setFlgActivo((short)0);
		
		boolean dependenciaHVDuplicado = validarDependenciaHVDuplicado();
		if(dependenciaHVDuplicado) 
			return;
		DependenciaHojaVida DependenciaHVGuardado = ComunicacionServiciosSis.setDependenciaHojaVida(infoDependenciaHV);
		if(!DependenciaHVGuardado.isError()) {
			if(blnModificarDependenciaHV)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDICION_CORRECTA);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_CREAR_REGISTRO);
			inicializarVariables();
			filtrarDepedenciaHV();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FALLA_GUARDADO);
		}
	}
	
	/**
	 * Metodo que valdia si una dependencia esta duplicada
	 * @return dependenciaHVDuplicado
	 */
	public boolean validarDependenciaHVDuplicado() {
		boolean dependenciaHVDuplicado = false;
		String depedendenciaLimpio = "";
		depedendenciaLimpio = cleanString(infoDependenciaHV.getNombreDependencia().toUpperCase());
		if(infoDependenciaHV.getCodDependenciaHojaVida()==null ||
				(infoDependenciaHV.getCodDependenciaHojaVida()!=null && 
				!(depedendenciaLimpio.equals(nombreDependenciaHVAnt)))) {
			DependenciaHojaVida fil = new DependenciaHojaVida();
			fil.setNombreDependencia(infoDependenciaHV.getNombreDependencia());
			List<DependenciaHojaVida> dependencia = ComunicacionServiciosSis.getDependenciaHVDuplicado(fil);
			if(dependencia != null && !dependencia.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_NOMBRE_DEPENDENCIA_HV_DUPLICADO);
				dependenciaHVDuplicado = true;
			}
		}
		return dependenciaHVDuplicado;
	}
	
	/*Metodo que valida los flgs de activo*/
	public void validarFlgs() {
		if(infoDependenciaHV.getFlgActivo() != null) {
			if(infoDependenciaHV.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
	}
	
	/*Metodo que habilita formulario para la creacion de Dependencia Hoja de vida*/
	public void crearDependenciaHV() {
		inicializarVariables();
		infoDependenciaHV = new DependenciaHojaVidaExt();
		blnHabilitaFormulario = true;
		blnCrearDependenciaHV=true;
		blnFlgActivo = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPENDENCIA_HV_CREAR, getLocale());
	}
	
	/*Metodo que habilita formulario para la modificacion de dependencia Hoja de Vida*/
	public void modificarDependenciaHV(DependenciaHojaVidaExt dependenciaHojaVida) {
		infoDependenciaHV = dependenciaHojaVida;
		nombreDependenciaHVAnt = dependenciaHojaVida.getNombreDependencia();
		inicializarVariables();
		validarFlgs();
		blnHabilitaFormulario = true;
		blnModificarDependenciaHV=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPENDENCIA_HV_MODIFICAR, getLocale());
	}
	
	/* Metodo que habilita formulario para la consultar la dependencia Hoja de vida*/
	public void consultarDependenciaHV(DependenciaHojaVidaExt dependenciaHojaVida) {
		infoDependenciaHV = dependenciaHojaVida;
		inicializarVariables();
		validarFlgs();
		blnHabilitaFormulario = true;
		blnConsultarDependenciaHV = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPENDENCIA_HV_CONSULTAR, getLocale());
	}

	/*Metodo que habilita formulario para la consultar la dependencia de la Hoja de Vida*/
	public void eliminarDependenciaHV() {
		inicializarVariables();
		blnEliminarDependenciaHV=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPENDENCIA_HV_ELIMINAR, getLocale());
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_SEGURO_ELIMINAR_REGISTRO, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarDependenciaHV').show();");
	}
	
	/*Metodo que cancela la accion realizada*/
	public void cancelarFormulario() {
		blnCancelarFormulario = true;
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DESCARTAR_CAMBIOS, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarDependenciaHV').show();");
	}
	
	/**
	 * Metodo que filtra la dependencia segun lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return filtroDependenciaHV
	 */
	public List<String> listarDependenciaHVFiltro(String entidadPrivQuery){
		List<String> filtroDependenciaHV = new ArrayList<>();
		DependenciaHojaVidaExt filtroDependenciaHVS = new DependenciaHojaVidaExt();
		filtroDependenciaHVS.setNombreDependencia(entidadPrivQuery);
		List<DependenciaHojaVidaExt> lstDependencia = ComunicacionServiciosSis.getDependenciaHVFiltro(filtroDependenciaHVS);
		for(DependenciaHojaVida dependenciaHV : lstDependencia ) {
			filtroDependenciaHV.add(dependenciaHV.getNombreDependencia());
		}
		return filtroDependenciaHV;
	}
	
	/* Metodo que precesa respuesta dependiendo de la acción realizada*/
	public void procesarRespuestaConfirmacion() {
		if(blnEliminarDependenciaHV){
			eliminarDependenciaHVConfirmado();
		}else if(blnCancelarFormulario || blnConsultarDependenciaHV){
			cancelarFormularioConfirmado();
		}
	}
	
	/**
	 * Metodo que elimina una dependencia donde:
	 * 1. Si la parametrica a eliminar no tiene referencia en el sistema, el metodo realiza un eliminado fisico del parametro. 
	 * Sino, el metodo realiza un eliminado logico y desactiva la dependencia, con la opcion de volverlo a activar despues.
	 * @param detalleParam
	 */
	public void eliminarDependenciaHVConfirmado() {
		eliminarFisicoDependenciaHV();
		inicializarVariables();
		filtrarDepedenciaHV();
	}
	
	/*Metodo que elimina una dependencia creada solo cuando este no tiene ninguna referencia.*/
	public void eliminarFisicoDependenciaHV() {
		if(infoDependenciaHV.getCodDependenciaHojaVida() != null) {
			String eliminado = ComunicacionServiciosSis.eliminarDependenciaHojaVidaFisico(infoDependenciaHV.getCodDependenciaHojaVida());
			if (eliminado.equals("\"true\"")) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
			}else {
				eliminarLogicamenteDependenciaHV();
			}
		}
	}
	
	/**
	 * Metodo que elimina una dependencia del maestro dependencia_hoja_vida de manera logica. 
	 * FLG_ACTIVO = 0
	 */
	public void eliminarLogicamenteDependenciaHV() {
		infoDependenciaHV.setAudCodRol(new BigDecimal(codAudCodRol));
		infoDependenciaHV.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoDependenciaHV.setAudAccion(new BigDecimal(codAudAccionDelete));
		infoDependenciaHV.setFlgActivo((short)0);
		infoDependenciaHV.setAudFechaActualizacion(new Date());	
		DependenciaHojaVida resultGuardado = ComunicacionServiciosSis.setDependenciaHojaVida(infoDependenciaHV);
		if(!resultGuardado.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_REGISTRO_ELIMINADO_LOGICAMENTE);
			inicializarVariables();
			filtrarDepedenciaHV();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error al eliminar la dependencia");
		}
	}
	
	
	/* Metodo que se ejecuta una vez el usuario ya haya confirmado la cancelacion del formulario*/
	public void cancelarFormularioConfirmado() {
		inicializarVariables();
		filtrarDepedenciaHV();
	}
	
	/* Metodo que filtra las dependencias de Hoja de vida*/
	public void filtrarDepedenciaHV() {
		DependenciaHojaVidaExt filtroDependenciaHV = new DependenciaHojaVidaExt();
		filtroDependenciaHV.setNombreDependencia(strBusquedaDependenciaHV);
		lstDependenciaHV = new DependenciaHojaVidaLazyDataModel(filtroDependenciaHV);
	}
	
	/**
	 * Metodo que limpia las tildes
	 * @param texto
	 * @return texto sin tildes
	 */
	 public static String cleanString(String texto) {
	        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
	        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	        return texto;
	  }

	/**
	 * Get y Set de Variables
	 */

	/**
	 * @return the blnHabilitaFormulario
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
	 * @return the blnCrearDependenciaHV
	 */
	public boolean isBlnCrearDependenciaHV() {
		return blnCrearDependenciaHV;
	}

	/**
	 * @param blnCrearDependenciaHV the blnCrearDependenciaHV to set
	 */
	public void setBlnCrearDependenciaHV(boolean blnCrearDependenciaHV) {
		this.blnCrearDependenciaHV = blnCrearDependenciaHV;
	}

	/**
	 * @return the blnModificarDependenciaHV
	 */
	public boolean isBlnModificarDependenciaHV() {
		return blnModificarDependenciaHV;
	}

	/**
	 * @param blnModificarDependenciaHV the blnModificarDependenciaHV to set
	 */
	public void setBlnModificarDependenciaHV(boolean blnModificarDependenciaHV) {
		this.blnModificarDependenciaHV = blnModificarDependenciaHV;
	}

	/**
	 * @return the blnConsultarDependenciaHV
	 */
	public boolean isBlnConsultarDependenciaHV() {
		return blnConsultarDependenciaHV;
	}

	/**
	 * @param blnConsultarDependenciaHV the blnConsultarDependenciaHV to set
	 */
	public void setBlnConsultarDependenciaHV(boolean blnConsultarDependenciaHV) {
		this.blnConsultarDependenciaHV = blnConsultarDependenciaHV;
	}

	/**
	 * @return the blnEliminarDependenciaHV
	 */
	public boolean isBlnEliminarDependenciaHV() {
		return blnEliminarDependenciaHV;
	}

	/**
	 * @param blnEliminarDependenciaHV the blnEliminarDependenciaHV to set
	 */
	public void setBlnEliminarDependenciaHV(boolean blnEliminarDependenciaHV) {
		this.blnEliminarDependenciaHV = blnEliminarDependenciaHV;
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
	 * @return the strBusquedaDependenciaHV
	 */
	public String getStrBusquedaDependenciaHV() {
		return strBusquedaDependenciaHV;
	}

	/**
	 * @param strBusquedaDependenciaHV the strBusquedaDependenciaHV to set
	 */
	public void setStrBusquedaDependenciaHV(String strBusquedaDependenciaHV) {
		this.strBusquedaDependenciaHV = strBusquedaDependenciaHV;
	}
	


	/**
	 * @return the nombreDependenciaHVAnt
	 */
	public String getNombreDependenciaHVAnt() {
		return nombreDependenciaHVAnt;
	}

	/**
	 * @param nombreDependenciaHVAnt the nombreDependenciaHVAnt to set
	 */
	public void setNombreDependenciaHVAnt(String nombreDependenciaHVAnt) {
		this.nombreDependenciaHVAnt = nombreDependenciaHVAnt;
	}

	/**
	 * @return the usuarioSesion
	 */
	public UsuarioDTO getUsuarioSesion() {
		return usuarioSesion;
	}

	/**
	 * @param usuarioSesion the usuarioSesion to set
	 */
	public void setUsuarioSesion(UsuarioDTO usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
	
	public LazyDataModel<DependenciaHojaVidaExt> getLstDependenciaHV() {
		return lstDependenciaHV;
	}

	public void setLstDependenciaHV(LazyDataModel<DependenciaHojaVidaExt> lstDependenciaHV) {
		this.lstDependenciaHV = lstDependenciaHV;
	}

	public DependenciaHojaVidaExt getInfoDependenciaHV() {
		return infoDependenciaHV;
	}

	public void setInfoDependenciaHV(DependenciaHojaVidaExt infoDependenciaHV) {
		this.infoDependenciaHV = infoDependenciaHV;
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
