package co.gov.dafp.sigep2.mbean.parametrica;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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

import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;

/**
* @author Andrés Felipe Jaramillo Arenas
* @version 1.0
* @Class NormaBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Normas
* @Date 06/05/2019
*/
@Named
@ViewScoped
@ManagedBean
public class NormaBean extends BaseBean implements Serializable{
	private static final long serialVersionUID = -1981489735745035228L;
	
	private boolean blnHabilitaFormulario;
	private boolean blnCrearNorma;
	private boolean blnModificarNorma;
	private boolean blnConsultarNorma;
	private boolean blnEliminarNorma;
	private boolean blnCancelarFormulario;
	private boolean blnFlgActivo;
	
	private String  strAccionRealizada;
	private String 	strMensaje;
	private String 	strBusquedaNorma;
	
	private List<Norma> lstNorma;
	
	private Norma infoNorma;
	
	/*Variables para identificar si la norma fue modificada*/
	private Integer tipoNomaAnt;
	private Date fechaNormaAnt;
	private String numeroNormaAnt;

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
		lstNorma =  new ArrayList<>();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		inicializarVariables();
		inicializarVariablesAuditoria();
		validarRolPermitido();
		filtrarNorma();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	/*Metodo que inicializa las variables de la vista*/
	public void  inicializarVariables() {
		blnHabilitaFormulario=false;
		blnCrearNorma=false;
		blnModificarNorma=false;
		blnConsultarNorma=false;
		blnEliminarNorma=false;
		blnFlgActivo = false;
		strAccionRealizada ="";
		strMensaje="";
		strBusquedaNorma = "";
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
	
	/*Metodo que guarda la modificacion o creación de una norma*/
	public void guardarNorma() {
		infoNorma.setAudCodRol(codAudCodRol);
		infoNorma.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoNorma.setAudFechaActualizacion(new Date());
		if(blnModificarNorma)
			infoNorma.setAudAccion(codAudAccionUpdate);
		else
			infoNorma.setAudAccion(codAudAccionInsert);		
		if(blnFlgActivo)
			infoNorma.setFlgActivo((short)1);
		else
			infoNorma.setFlgActivo((short)0);
		boolean normaDuplicado = validarNormaDuplicado();
		if(normaDuplicado) 
			return;
		Norma normaGuardado = ComunicacionServiciosEnt.setNorma(infoNorma);
		if(!normaGuardado.isError()) {
			if(blnModificarNorma)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDICION_CORRECTA);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_GUARDAR);
			inicializarVariables();
			filtrarNorma();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ERROR_GUARDAR);
		}
	}
	
	/*Metodo que valida si una norma se encuentra duplicada*/
	public boolean validarNormaDuplicado() {
		boolean normaDuplicado = false;
		if(infoNorma.getCodNorma()==null ||
				(infoNorma.getCodNorma()!=null 
				&& (!infoNorma.getCodTipoNorma().equals(tipoNomaAnt)
					|| !infoNorma.getFechaNorma().equals(fechaNormaAnt)
					|| !infoNorma.getNumeroNorma().equals(numeroNormaAnt)))) {
			Norma fil = new Norma();
			fil.setCodTipoNorma(infoNorma.getCodTipoNorma());
			fil.setFechaNorma(infoNorma.getFechaNorma());
			fil.setNumeroNorma(infoNorma.getNumeroNorma());
			List<Norma> norma = ComunicacionServiciosEnt.getFiltroNorma(fil);
			if(norma != null && !norma.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_NORMA_DUPLICADO);
				normaDuplicado = true;
			}
		}
		return normaDuplicado;
	}
	
	/*Metodo que habilita formulario para la creacion de la norma*/
	public void crearNorma() {
		inicializarVariables();
		infoNorma = new Norma();
		blnHabilitaFormulario = true;
		blnCrearNorma=true;
		blnFlgActivo = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_NORMA_CREAR, getLocale());
	}
	
	/*Metodo que habilita formulario para la modificacion de la norma*/
	public void modificarNorma(Norma dataNorma) {
		infoNorma = dataNorma;
		tipoNomaAnt = dataNorma.getCodTipoNorma();
		numeroNormaAnt = dataNorma.getNumeroNorma();
		fechaNormaAnt = dataNorma.getFechaNorma();
		inicializarVariables();
		if(infoNorma.getFlgActivo() != null) {
			if(infoNorma.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		blnHabilitaFormulario = true;
		blnModificarNorma=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_NORMA_MODIFICAR, getLocale());
	}
	
	/* Metodo que habilita formulario para la consultar de la norma*/
	public void consultarNorma(Norma dataNorma) {
		infoNorma = dataNorma;
		inicializarVariables();
		if(infoNorma.getFlgActivo() != null) {
			if(infoNorma.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		blnHabilitaFormulario = true;
		blnConsultarNorma = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_NORMA_CONSULTAR, getLocale());
	}

	/*Metodo que habilita formulario para la consultar de la norma*/
	public void eliminarNorma() {
		inicializarVariables();
		blnEliminarNorma=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_PAIS_ELIMINAR, getLocale());
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PREGUNTA_ELIMINAR_REGISTRO, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarNorma').show();");
	}
	
	/*Metodo que cancela la accion realizada*/
	public void cancelarFormulario() {
		blnCancelarFormulario = true;
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DESCARTAR_CAMBIOS, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarNorma').show();");
	}
	
	/**
	 * Metodo que filtra norma segun lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return filtroNorma
	 */
	public List<String> listarNormaFiltro(String entidadPrivQuery){
		List<String> filtroNorma = new ArrayList<>();
		for(Norma norma : lstNorma ) {
			if(norma.getNumeroNorma().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroNorma.add(norma.getNumeroNorma());
			}
		}
		return filtroNorma;
	}
	
	/* Metodo que precesa respuesta dependiendo de la acción realizada*/
	public void procesarRespuestaConfirmacion() {
		if(blnEliminarNorma){
			eliminarNormaConfirmado();
		}else if(blnCancelarFormulario || blnConsultarNorma){
			cancelarFormularioConfirmado();
		}
	}
	
	/**
	 * Metodo que elimina una norma donde:
	 * El metodo realiza un eliminado logico y desactiva la norma, con la opcion de volverlo a activar despues.
	 * @param detalleParam
	 */
	public void eliminarNormaConfirmado() {
		eliminarLogicamenteNorma();
		inicializarVariables();
		filtrarNorma();
	}
	
	/**
	 * Metodo que elimina una norma de manera logica. 
	 * FLG_ACTIVO = 0
	 */
	public void eliminarLogicamenteNorma() {
		infoNorma.setAudCodRol(codAudCodRol);
		infoNorma.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoNorma.setAudAccion(codAudAccionDelete);
		infoNorma.setFlgActivo((short)0);
		infoNorma.setAudFechaActualizacion(new Date());	
		Norma normaGuardado = ComunicacionServiciosEnt.setNorma(infoNorma);
		if(!normaGuardado.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_HV_GP_ELIMINADO_EXITO);
			inicializarVariables();
			filtrarNorma();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ERROR_GUARDAR);
		}
	}
	
	
	/* Metodo que se ejecuta una vez el usuario ya haya confirmado la cancelacion del formulario*/
	public void cancelarFormularioConfirmado() {
		inicializarVariables();
		filtrarNorma();
	}
	
	/* Metodo que filtra las norma*/
	public void filtrarNorma() {
		Norma filtroNorma = new Norma();
		if(strBusquedaNorma != null)
			filtroNorma.setNumeroNorma(strBusquedaNorma.equals("")? null:strBusquedaNorma);
		
		lstNorma = ComunicacionServiciosEnt.getFiltroNorma(filtroNorma);
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
	 * @return the blnCrearNorma
	 */
	public boolean isBlnCrearNorma() {
		return blnCrearNorma;
	}

	/**
	 * @param blnCrearNorma the blnCrearNorma to set
	 */
	public void setBlnCrearNorma(boolean blnCrearNorma) {
		this.blnCrearNorma = blnCrearNorma;
	}

	/**
	 * @return the blnModificarNorma
	 */
	public boolean isBlnModificarNorma() {
		return blnModificarNorma;
	}

	/**
	 * @param blnModificarNorma the blnModificarNorma to set
	 */
	public void setBlnModificarNorma(boolean blnModificarNorma) {
		this.blnModificarNorma = blnModificarNorma;
	}

	/**
	 * @return the blnConsultarNorma
	 */
	public boolean isBlnConsultarNorma() {
		return blnConsultarNorma;
	}

	/**
	 * @param blnConsultarNorma the blnConsultarNorma to set
	 */
	public void setBlnConsultarNorma(boolean blnConsultarNorma) {
		this.blnConsultarNorma = blnConsultarNorma;
	}

	/**
	 * @return the blnEliminarPais
	 */
	public boolean isBlnEliminarPais() {
		return blnEliminarNorma;
	}

	/**
	 * @param blnEliminarPais the blnEliminarPais to set
	 */
	public void setBlnEliminarPais(boolean blnEliminarPais) {
		this.blnEliminarNorma = blnEliminarPais;
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
	 * @return the strBusquedaNorma
	 */
	public String getStrBusquedaNorma() {
		return strBusquedaNorma;
	}

	/**
	 * @param strBusquedaNorma the strBusquedaNorma to set
	 */
	public void setStrBusquedaNorma(String strBusquedaNorma) {
		this.strBusquedaNorma = strBusquedaNorma;
	}

	/**
	 * @return the lstNorma
	 */
	public List<Norma> getLstNorma() {
		return lstNorma;
	}

	/**
	 * @param lstNorma the lstNorma to set
	 */
	public void setLstNorma(List<Norma> lstNorma) {
		this.lstNorma = lstNorma;
	}

	/**
	 * @return the infoNorma
	 */
	public Norma getInfoNorma() {
		return infoNorma;
	}

	/**
	 * @param infoNorma the infoNorma to set
	 */
	public void setInfoNorma(Norma infoNorma) {
		this.infoNorma = infoNorma;
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
	 * @return the tipoNomaAnt
	 */
	public Integer getTipoNomaAnt() {
		return tipoNomaAnt;
	}

	/**
	 * @param tipoNomaAnt the tipoNomaAnt to set
	 */
	public void setTipoNomaAnt(Integer tipoNomaAnt) {
		this.tipoNomaAnt = tipoNomaAnt;
	}

	/**
	 * @return the fechaNormaAnt
	 */
	public Date getFechaNormaAnt() {
		return fechaNormaAnt;
	}

	/**
	 * @param fechaNormaAnt the fechaNormaAnt to set
	 */
	public void setFechaNormaAnt(Date fechaNormaAnt) {
		this.fechaNormaAnt = fechaNormaAnt;
	}


	/**
	 * @return the numeroNormaAnt
	 */
	public String getNumeroNormaAnt() {
		return numeroNormaAnt;
	}

	/**
	 * @param numeroNormaAnt the numeroNormaAnt to set
	 */
	public void setNumeroNormaAnt(String numeroNormaAnt) {
		this.numeroNormaAnt = numeroNormaAnt;
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
