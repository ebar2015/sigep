
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
import co.gov.dafp.sigep2.datamodel.CargoHojaVidaLazyDataModel;
import co.gov.dafp.sigep2.entities.CargoHojaVida;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.CargoHojaVidaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;

/**
* @author Maria Alejandra Colorado
* @version 1.0
* @Class CargoHojaVidaBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Parametrica Cargos hoja de Vida
* Fecha: 28/04/2020
*/
@Named
@ViewScoped
@ManagedBean
public class CargoHojaVidaBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = -7259466678876217657L;
	private boolean blnHabilitaFormulario;
	private boolean blnCrearCargoHV;
	private boolean blnModificarCargoHV;
	private boolean blnConsultarCargoHV;
	private boolean blnEliminarCargoHV;
	private boolean blnCancelarFormulario;
	private boolean blnFlgActivo;
	private boolean blnFlgPublico;
	
	private String  strAccionRealizada;
	private String 	strMensaje;
	private String 	strBusquedaCargoHV;
	
	private LazyDataModel<CargoHojaVidaExt> lstCargoHV;
	
	private CargoHojaVidaExt infoCargoHV;
	private CargoHojaVidaExt filterCargoHV;
	private String 		  nombreCargoHVAnt;
	private boolean       blnPublicoAnt;

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
		filterCargoHV 	= new CargoHojaVidaExt();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		validarSeguridadRoles();
		inicializarVariables();
		inicializarVariablesAuditoria();
		validarRolPermitido();
		filtrarCargoHV();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);
	}
	
	/*Metodo que inicializa las variables de la vista*/
	public void  inicializarVariables() {
		blnHabilitaFormulario	=false;
		blnCrearCargoHV			=false;
		blnModificarCargoHV		=false;
		blnConsultarCargoHV		=false;
		blnEliminarCargoHV		=false;
		blnFlgActivo 			=false;
		blnFlgPublico			=false;
		strAccionRealizada 		="";
		strMensaje				="";
		strBusquedaCargoHV 		= "";
	}
	
	/*Metodo que inicializa las variables de auditoria de la aplicacion*/
	public void inicializarVariablesAuditoria() {
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		codAudCodRol 	   = (int) this.getRolAuditoria().getId();
		codAudusuario      = (int) usuarioSesion.getId();
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
	/*Metodo que se encarga de validar la seguridad de roles */
	public void validarSeguridadRoles() {
		FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);
	}
	
	/*Metodo que guarda la modificacion o creación de un Cargo*/
	public void guardarCargoHV() {
		infoCargoHV.setAudCodRol(new BigDecimal(codAudCodRol));
		infoCargoHV.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoCargoHV.setAudFechaActualizacion(new Date());
		if(blnModificarCargoHV)
			infoCargoHV.setAudAccion(new BigDecimal(codAudAccionUpdate));
		else
			infoCargoHV.setAudAccion(new BigDecimal(codAudAccionInsert));		
		if(blnFlgActivo)
			infoCargoHV.setFlgActivo((short)1);
		else
			infoCargoHV.setFlgActivo((short)0);
		if(blnFlgPublico)
			infoCargoHV.setFlgPublico((short)1);
		else
			infoCargoHV.setFlgPublico((short)0);
		
		boolean cargoHVDuplicado = validarCargoHVDuplicado();
		if(cargoHVDuplicado) 
			return;
		CargoHojaVida cargoHVGuardado = ComunicacionServiciosSis.setCargoHojaVida(infoCargoHV);
		if(!cargoHVGuardado.isError()) {
			if(blnModificarCargoHV)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDICION_CORRECTA);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_CREAR_REGISTRO);
			inicializarVariables();
			filtrarCargoHV();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FALLA_GUARDADO);
		}
	}
	
	/**
	 * Metodo que valdia si un cargo esta duplicado
	 * @return cargoHVDuplicado
	 */
	public boolean validarCargoHVDuplicado() {
		boolean cargoHVDuplicado = false;
		String cargoLimpio = "";
		cargoLimpio = cleanString(infoCargoHV.getNombreCargo().toUpperCase());
		if(infoCargoHV.getCodCargoHojaVida()==null ||
				(infoCargoHV.getCodCargoHojaVida()!=null && 
				(!(cargoLimpio.equals(nombreCargoHVAnt))|| blnFlgPublico != blnPublicoAnt))) {
			CargoHojaVida fil = new CargoHojaVida();
			fil.setNombreCargo(infoCargoHV.getNombreCargo());
			if(blnFlgPublico) {
				fil.setFlgPublico((short) 1);
			}else {
				fil.setFlgPublico((short) 0);
			}
			List<CargoHojaVida> cargo = ComunicacionServiciosSis.getCargoHVDuplicado(fil);
			if(cargo != null && !cargo.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_NOMBRE_CARGO_HV_DUPLICADO);
				cargoHVDuplicado = true;
			}
		}
		return cargoHVDuplicado;
	}
	
	/*Metodo que valida los flgs de activo y publico*/
	public void validarFlgs() {
		if(infoCargoHV.getFlgActivo() != null) {
			if(infoCargoHV.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		if(infoCargoHV.getFlgPublico() != null) {
			if(infoCargoHV.getFlgPublico().intValue() ==  1)
				blnFlgPublico = true;
			else
				blnFlgPublico = false;
		}
	}
	
	/*Metodo que habilita formulario para la creacion de Cargo Hoja de vida*/
	public void crearCargoHV() {
		inicializarVariables();
		infoCargoHV = new CargoHojaVidaExt();
		blnHabilitaFormulario = true;
		blnCrearCargoHV=true;
		blnFlgActivo = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_CARGO_HV_CREAR, getLocale());
	}
	
	/*Metodo que habilita formulario para la modificacion de Cargo Hoja de Vida*/
	public void modificarCargoHV(CargoHojaVidaExt cargoHojaVida) {
		infoCargoHV = cargoHojaVida;
		nombreCargoHVAnt = cargoHojaVida.getNombreCargo();
		inicializarVariables();
		validarFlgs();
		blnPublicoAnt = blnFlgPublico;
		blnHabilitaFormulario = true;
		blnModificarCargoHV=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_CARGO_HV_MODIFICAR, getLocale());
	}
	
	/* Metodo que habilita formulario para la consultar de Cargo Hoja de vida*/
	public void consultarCargoHV(CargoHojaVidaExt cargoHojaVida) {
		infoCargoHV = cargoHojaVida;
		inicializarVariables();
		validarFlgs();
		blnHabilitaFormulario = true;
		blnConsultarCargoHV = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_CARGO_HV_CONSULTAR, getLocale());
	}

	/*Metodo que habilita formulario para la consultar de Cargo Hoja de Vida*/
	public void eliminarCargoHV() {
		inicializarVariables();
		blnEliminarCargoHV=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_CARGO_HV_ELIMINAR, getLocale());
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_SEGURO_ELIMINAR_REGISTRO, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarCargoHV').show();");
	}
	
	/*Metodo que cancela la accion realizada*/
	public void cancelarFormulario() {
		blnCancelarFormulario = true;
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DESCARTAR_CAMBIOS, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarCargoHV').show();");
	}
	
	/**
	 * Metodo que filtra Cago segun lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return filtroCargoHV
	 */
	public List<String> listarCargoHVFiltro(String entidadPrivQuery){
		List<String> filtroCargoHV = new ArrayList<>();
		CargoHojaVidaExt filtroCargoHVS = new CargoHojaVidaExt();
		filtroCargoHVS.setNombreCargo(entidadPrivQuery);
		List<CargoHojaVidaExt> lstCargoHVS = ComunicacionServiciosSis.getCargoHVFiltro(filtroCargoHVS);
		for(CargoHojaVida cargoHV : lstCargoHVS ) {
			filtroCargoHV.add(cargoHV.getNombreCargo());
		}
		return filtroCargoHV;
	}
	
	/* Metodo que precesa respuesta dependiendo de la acción realizada*/
	public void procesarRespuestaConfirmacion() {
		if(blnEliminarCargoHV){
			eliminarCargoHVConfirmado();
		}else if(blnCancelarFormulario || blnConsultarCargoHV){
			cancelarFormularioConfirmado();
		}
	}
	
	/**
	 * Metodo que elimina un pais donde:
	 * 1. Si la parametrica a eliminar no tiene referencia en el sistema, el metodo realiza un eliminado fisico del parametro. 
	 * Sino, el metodo realiza un eliminado logico y desactiva el pais, con la opcion de volverlo a activar despues.
	 * @param detalleParam
	 */
	public void eliminarCargoHVConfirmado() {
		eliminarFisicoCargoHV();
		inicializarVariables();
		filtrarCargoHV();
	}
	
	/*Metodo que elimina un pais creado solo cuando este no tiene ninguna referencia.*/
	public void eliminarFisicoCargoHV() {
		if(infoCargoHV.getCodCargoHojaVida() != null) {
			String eliminado = ComunicacionServiciosSis.eliminarCargoHojaVidaFisico(infoCargoHV.getCodCargoHojaVida());
			if (eliminado.equals("\"true\"")) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
			}else {
				eliminarLogicamenteCargoHV();
			}
		}
	}
	
	/**
	 * Metodo que elimina un Cargo del maestro cargo_hoja_vida de manera logica. 
	 * FLG_ACTIVO = 0
	 */
	public void eliminarLogicamenteCargoHV() {
		infoCargoHV.setAudCodRol(new BigDecimal(codAudCodRol));
		infoCargoHV.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoCargoHV.setAudAccion(new BigDecimal(codAudAccionDelete));
		infoCargoHV.setFlgActivo((short)0);
		infoCargoHV.setAudFechaActualizacion(new Date());	
		CargoHojaVida resultGuardado = ComunicacionServiciosSis.setCargoHojaVida(infoCargoHV);
		if(!resultGuardado.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_REGISTRO_ELIMINADO_LOGICAMENTE);
			inicializarVariables();
			filtrarCargoHV();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error al eliminar el Cargo");
		}
	}
	
	
	/* Metodo que se ejecuta una vez el usuario ya haya confirmado la cancelacion del formulario*/
	public void cancelarFormularioConfirmado() {
		inicializarVariables();
		filtrarCargoHV();
	}
	
	/* Metodo que filtra los cargos Hoja de vida*/
	public void filtrarCargoHV() {
		CargoHojaVidaExt filtroCargoHV = new CargoHojaVidaExt();
		filtroCargoHV.setNombreCargo(strBusquedaCargoHV);
		lstCargoHV = new CargoHojaVidaLazyDataModel(filtroCargoHV);
	}
	
	
	/* Metodo que filtra los cargos Hoja de vida desde la opción consultar*/
	public void consultarCargos() {
		lstCargoHV = new CargoHojaVidaLazyDataModel(filterCargoHV);
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
	 * @return the blnCrearCargoHV
	 */
	public boolean isBlnCrearCargoHV() {
		return blnCrearCargoHV;
	}

	/**
	 * @param blnCrearCargoHV the blnCrearCargoHV to set
	 */
	public void setBlnCrearCargoHV(boolean blnCrearCargoHV) {
		this.blnCrearCargoHV = blnCrearCargoHV;
	}

	/**
	 * @return the blnModificarCargoHV
	 */
	public boolean isBlnModificarCargoHV() {
		return blnModificarCargoHV;
	}

	/**
	 * @param blnModificarCargoHV the blnModificarCargoHV to set
	 */
	public void setBlnModificarCargoHV(boolean blnModificarCargoHV) {
		this.blnModificarCargoHV = blnModificarCargoHV;
	}

	/**
	 * @return the blnConsultarCargoHV
	 */
	public boolean isBlnConsultarCargoHV() {
		return blnConsultarCargoHV;
	}

	/**
	 * @param blnConsultarCargoHV the blnConsultarCargoHV to set
	 */
	public void setBlnConsultarCargoHV(boolean blnConsultarCargoHV) {
		this.blnConsultarCargoHV = blnConsultarCargoHV;
	}

	/**
	 * @return the blnEliminarCargoHV
	 */
	public boolean isBlnEliminarCargoHV() {
		return blnEliminarCargoHV;
	}

	/**
	 * @param blnEliminarCargoHV the blnEliminarCargoHV to set
	 */
	public void setBlnEliminarCargoHV(boolean blnEliminarCargoHV) {
		this.blnEliminarCargoHV = blnEliminarCargoHV;
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
	 * @return the strBusquedaCargoHV
	 */
	public String getStrBusquedaCargoHV() {
		return strBusquedaCargoHV;
	}

	/**
	 * @param strBusquedaCargoHV the strBusquedaCargoHV to set
	 */
	public void setStrBusquedaCargoHV(String strBusquedaCargoHV) {
		this.strBusquedaCargoHV = strBusquedaCargoHV;
	}
	
	/**
	 * @return the lstCargoHV
	 */
	public LazyDataModel<CargoHojaVidaExt> getLstCargoHV() {
		return lstCargoHV;
	}

	/**
	 * @param lstCargoHV the lstCargoHV to set
	 */
	public void setLstCargoHV(LazyDataModel<CargoHojaVidaExt> lstCargoHV) {
		this.lstCargoHV = lstCargoHV;
	}


	/**
	 * @return the nombreCargoHVAnt
	 */
	public String getNombreCargoHVAnt() {
		return nombreCargoHVAnt;
	}

	/**
	 * @param nombreCargoHVAnt the nombreCargoHVAnt to set
	 */
	public void setNombreCargoHVAnt(String nombreCargoHVAnt) {
		this.nombreCargoHVAnt = nombreCargoHVAnt;
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

	/**
	 * @return the contexto
	 */
	public ExternalContext getContexto() {
		return contexto;
	}

	/**
	 * @param contexto the contexto to set
	 */
	public void setContexto(ExternalContext contexto) {
		this.contexto = contexto;
	}
	
	/**
	 * @return the blnFlgPublico
	 */
	public boolean isBlnFlgPublico() {
		return blnFlgPublico;
	}

	/**
	 * @param blnFlgPublico the blnFlgPublico to set
	 */
	public void setBlnFlgPublico(boolean blnFlgPublico) {
		this.blnFlgPublico = blnFlgPublico;
	}

	/**
	 * @return the blnPublicoAnt
	 */
	public boolean isBlnPublicoAnt() {
		return blnPublicoAnt;
	}

	/**
	 * @param blnPublicoAnt the blnPublicoAnt to set
	 */
	public void setBlnPublicoAnt(boolean blnPublicoAnt) {
		this.blnPublicoAnt = blnPublicoAnt;
	}
	

	/**
	 * @return the infoCargoHV
	 */
	public CargoHojaVidaExt getInfoCargoHV() {
		return infoCargoHV;
	}

	/**
	 * @param infoCargoHV the infoCargoHV to set
	 */
	public void setInfoCargoHV(CargoHojaVidaExt infoCargoHV) {
		this.infoCargoHV = infoCargoHV;
	}

	/**
	 * @return the filterCargoHV
	 */
	public CargoHojaVidaExt getFilterCargoHV() {
		return filterCargoHV;
	}

	/**
	 * @param filterCargoHV the filterCargoHV to set
	 */
	public void setFilterCargoHV(CargoHojaVidaExt filterCargoHV) {
		this.filterCargoHV = filterCargoHV;
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
