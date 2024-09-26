
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
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
* @author Maria Alejandra Colorado
* @version 1.0
* @Class PaisBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Parametrica Pais
* Fecha: 06/05/2019
*/
@Named
@ViewScoped
@ManagedBean
public class PaisBean extends BaseBean implements Serializable{
	private static final long serialVersionUID = -1981489735745035228L;
	
	private boolean blnHabilitaFormulario;
	private boolean blnCrearPais;
	private boolean blnModificarPais;
	private boolean blnConsultarPais;
	private boolean blnEliminarPais;
	private boolean blnCancelarFormulario;
	private boolean blnFlgActivo;
	
	private String  strAccionRealizada;
	private String 	strMensaje;
	private String 	strBusquedaPais;
	
	private List<Pais> lstPaises;
	
	private Pais infoPais;
	private String nombrePaisAnt;

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
		lstPaises =  new ArrayList<>();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		inicializarVariables();
		inicializarVariablesAuditoria();
		validarRolPermitido();
		filtrarPais();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	/*Metodo que inicializa las variables de la vista*/
	public void  inicializarVariables() {
		blnHabilitaFormulario=false;
		blnCrearPais=false;
		blnModificarPais=false;
		blnConsultarPais=false;
		blnEliminarPais=false;
		blnFlgActivo = false;
		strAccionRealizada ="";
		strMensaje="";
		strBusquedaPais = "";
	}
	
	/*Metodo que inicializa las variables de auditoria de la aplicacion*/
	public void inicializarVariablesAuditoria() {
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		codAudCodRol = (int) this.getRolAuditoria().getId();
		codAudusuario = (int) usuarioSesion.getId();
	}
	
	/*Metodo que valida si el usuario que intenta ingresar a la opcion si tiene los permisos para acceder.*/
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
	
	/*Metodo que guarda la modificacion o creación de un pais*/
	public void guardarPais() {
		infoPais.setAudCodRol(new BigDecimal(codAudCodRol));
		infoPais.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoPais.setAudFechaActualizacion(new Date());
		if(blnModificarPais)
			infoPais.setAudAccion(codAudAccionUpdate);
		else
			infoPais.setAudAccion(codAudAccionInsert);		
		if(blnFlgActivo)
			infoPais.setFlgActivo(new BigDecimal(1));
		else
			infoPais.setFlgActivo(new BigDecimal(0));
		
		boolean paisDuplicado = validarPaisDuplicado();
		if(paisDuplicado) 
			return;
		Pais paisGuardado = ComunicacionServiciosSis.setPais(infoPais);
		if(!paisGuardado.isError()) {
			if(blnModificarPais)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_PAIS_MODIFICAR_EXITOSO);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_PAIS_GUARDAR_EXITOSO);
			inicializarVariables();
			filtrarPais();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_PAIS_GUARDAR_FALLIDO);
		}
	}
	
	/**
	 * Metodo que valdia si un pais esta duplicado
	 * @return paisDuplicado
	 */
	public boolean validarPaisDuplicado() {
		boolean paisDuplicado = false;
		if(infoPais.getCodPais()==null ||
				(infoPais.getCodPais()!=null && !(infoPais.getNombrePais().equals(nombrePaisAnt)))) {
			Pais fil = new Pais();
			fil.setNombrePais(infoPais.getNombrePais());
			List<Pais> pais = ComunicacionServiciosSis.getPaisDuplicado(fil);
			if(pais != null && !pais.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_NOMBRE_PAIS_DUPLICADO);
				paisDuplicado = true;
			}
		}
		return paisDuplicado;
	}
	
	/*Metodo que habilita formulario para la creacion de Pais*/
	public void crearPais() {
		inicializarVariables();
		infoPais = new Pais();
		blnHabilitaFormulario = true;
		blnCrearPais=true;
		blnFlgActivo = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_PAIS_CREAR, getLocale());
	}
	
	/*Metodo que habilita formulario para la modificacion de Pais*/
	public void modificarPais(Pais dataPais) {
		infoPais = dataPais;
		nombrePaisAnt = dataPais.getNombrePais();
		inicializarVariables();
		if(infoPais.getFlgActivo() != null) {
			if(infoPais.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		blnHabilitaFormulario = true;
		blnModificarPais=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_PAIS_MODIFICAR, getLocale());
	}
	
	/* Metodo que habilita formulario para la consultar de Pais*/
	public void consultarPais(Pais dataPais) {
		infoPais = dataPais;
		inicializarVariables();
		if(infoPais.getFlgActivo() != null) {
			if(infoPais.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		blnHabilitaFormulario = true;
		blnConsultarPais = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_PAIS_CONSULTAR, getLocale());
	}

	/*Metodo que habilita formulario para la consultar de Pais*/
	public void eliminarPais() {
		inicializarVariables();
		blnEliminarPais=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_PAIS_ELIMINAR, getLocale());
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_PAIS_ELIMINAR_CONFIRMAR, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarPais').show();");
	}
	
	/*Metodo que cancela la accion realizada*/
	public void cancelarFormulario() {
		blnCancelarFormulario = true;
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DESCARTAR_CAMBIOS, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarPais').show();");
	}
	
	/**
	 * Metodo que filtra pais segun lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return filtroPais
	 */
	public List<String> listarPaisesFiltro(String entidadPrivQuery){
		List<String> filtroPais = new ArrayList<>();
		for(Pais pais : lstPaises ) {
			if(pais.getNombrePais().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroPais.add(pais.getNombrePais());
			}
		}
		return filtroPais;
	}
	
	/* Metodo que precesa respuesta dependiendo de la acción realizada*/
	public void procesarRespuestaConfirmacion() {
		if(blnEliminarPais){
			eliminarPaisConfirmado();
		}else if(blnCancelarFormulario || blnConsultarPais){
			cancelarFormularioConfirmado();
		}
	}
	
	/**
	 * Metodo que elimina un pais donde:
	 * 1. Si la parametrica a eliminar no tiene referencia en el sistema, el metodo realiza un eliminado fisico del parametro. 
	 * Sino, el metodo realiza un eliminado logico y desactiva el pais, con la opcion de volverlo a activar despues.
	 * @param detalleParam
	 */
	public void eliminarPaisConfirmado() {
		eliminarLogicamentePais();
		inicializarVariables();
		filtrarPais();
	}
	
	/*Metodo que elimina un pais creado solo cuando este no tiene ninguna referencia.*/
	public void eliminarFisicoPais() {
		if(infoPais.getCodPais() != null) {
			String eliminado = ComunicacionServiciosSis.eliminarPais(infoPais.getCodPais());
			if (eliminado.equals("\"true\"")) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
						MessagesBundleConstants.MSG_PARAMETRICA_PAIS_ELIMINADO_EXITOSO);
			}else {
				eliminarLogicamentePais();
			}
		}
	}
	
	/**
	 * Metodo que elimina un pais de manera logica. 
	 * FLG_ACTIVO = 0
	 */
	public void eliminarLogicamentePais() {
		infoPais.setAudCodRol(new BigDecimal(codAudCodRol));
		infoPais.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoPais.setAudAccion(codAudAccionDelete);
		infoPais.setFlgActivo(new BigDecimal(0));
		infoPais.setAudFechaActualizacion(new Date());	
		Pais paisGuardado = ComunicacionServiciosSis.setPais(infoPais);
		if(!paisGuardado.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_PAIS_ELIMINADO_EXITOSO);
			inicializarVariables();
			filtrarPais();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error al eliminar el pais");
		}
	}
	
	
	/* Metodo que se ejecuta una vez el usuario ya haya confirmado la cancelacion del formulario*/
	public void cancelarFormularioConfirmado() {
		inicializarVariables();
		filtrarPais();
	}
	
	/* Metodo que filtra los paises*/
	public void filtrarPais() {
		Pais filtroPais = new Pais();
		filtroPais.setNombrePais(strBusquedaPais);
		lstPaises = ComunicacionServiciosSis.getPaisFiltro(filtroPais);
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
	 * @return the blnCrearPais
	 */
	public boolean isBlnCrearPais() {
		return blnCrearPais;
	}

	/**
	 * @param blnCrearPais the blnCrearPais to set
	 */
	public void setBlnCrearPais(boolean blnCrearPais) {
		this.blnCrearPais = blnCrearPais;
	}

	/**
	 * @return the blnModificarPais
	 */
	public boolean isBlnModificarPais() {
		return blnModificarPais;
	}

	/**
	 * @param blnModificarPais the blnModificarPais to set
	 */
	public void setBlnModificarPais(boolean blnModificarPais) {
		this.blnModificarPais = blnModificarPais;
	}

	/**
	 * @return the blnConsultarPais
	 */
	public boolean isBlnConsultarPais() {
		return blnConsultarPais;
	}

	/**
	 * @param blnConsultarPais the blnConsultarPais to set
	 */
	public void setBlnConsultarPais(boolean blnConsultarPais) {
		this.blnConsultarPais = blnConsultarPais;
	}

	/**
	 * @return the blnEliminarPais
	 */
	public boolean isBlnEliminarPais() {
		return blnEliminarPais;
	}

	/**
	 * @param blnEliminarPais the blnEliminarPais to set
	 */
	public void setBlnEliminarPais(boolean blnEliminarPais) {
		this.blnEliminarPais = blnEliminarPais;
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

	/**
	 * @return the strBusquedaPais
	 */
	public String getStrBusquedaPais() {
		return strBusquedaPais;
	}

	/**
	 * @param strBusquedaPais the strBusquedaPais to set
	 */
	public void setStrBusquedaPais(String strBusquedaPais) {
		this.strBusquedaPais = strBusquedaPais;
	}

	/**
	 * @return the lstPaises
	 */
	public List<Pais> getLstPaises() {
		return lstPaises;
	}

	/**
	 * @param lstPaises the lstPaises to set
	 */
	public void setLstPaises(List<Pais> lstPaises) {
		this.lstPaises = lstPaises;
	}
	/**
	 * @return the infoPais
	 */
	public Pais getInfoPais() {
		return infoPais;
	}

	/**
	 * @param infoPais the infoPais to set
	 */
	public void setInfoPais(Pais infoPais) {
		this.infoPais = infoPais;
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
	 * @return the nombrePaisAnt
	 */
	public String getNombrePaisAnt() {
		return nombrePaisAnt;
	}

	/**
	 * @param nombrePaisAnt the nombrePaisAnt to set
	 */
	public void setNombrePaisAnt(String nombrePaisAnt) {
		this.nombrePaisAnt = nombrePaisAnt;
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
