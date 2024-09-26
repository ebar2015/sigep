
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
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;

import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
* @author Maria Alejandra Colorado
* @version 1.0
* @Class MunicipioBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Parametrica Municipio
* Fecha: 06/05/2019
*/
@Named
@ViewScoped
@ManagedBean
public class MunicipioBean extends BaseBean implements Serializable{
	private static final long serialVersionUID = -1981489735745035228L;
	
	private boolean blnHabilitaFormulario;
	private boolean blnCrearMunicipio;
	private boolean blnModificarMunicipio;
	private boolean blnConsultarMunicipio;
	private boolean blnEliminarMunicipio;
	private boolean blnCancelarFormulario;
	private boolean blnFlgActivo;
	
	private String  strAccionRealizada;
	private String 	strMensaje;
	private String 	strBusquedaMunicipio;
	
	private List<Municipio> lstMunicipio;
	
	private Municipio infoMunicipio;

	/*Varibles para guardar datos anteriores*/
	private String  strNombreMunicipioAnt;
	private Integer	codDeparamentoAnt;
	private Integer codMunicipioDANEAnt;
	
	
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
		lstMunicipio =  new ArrayList<>();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		inicializarVariables();
		inicializarVariablesAuditoria();
		validarRolPermitido();
		filtrarMunicipio();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	/*Metodo que inicializa las variables de la vista*/
	public void  inicializarVariables() {
		blnHabilitaFormulario=false;
		blnCrearMunicipio=false;
		blnModificarMunicipio=false;
		blnConsultarMunicipio=false;
		blnEliminarMunicipio=false;
		blnFlgActivo = false;
		strAccionRealizada ="";
		strMensaje="";
		strBusquedaMunicipio = "";
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
	
	/* Metodo que guarda la modificacion o creación de un Municipio*/
	public void guardarMunicipio() {
		infoMunicipio.setAudCodRol(codAudCodRol.shortValue());
		infoMunicipio.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoMunicipio.setAudFechaActualizacion(new Date());	
		if(blnModificarMunicipio)
			infoMunicipio.setAudAccion(codAudAccionUpdate);
		else
			infoMunicipio.setAudAccion(codAudAccionInsert);
		if(blnFlgActivo)
			infoMunicipio.setFlgActivo((short)1);
		else
			infoMunicipio.setFlgActivo((short)0);
		
		boolean municipioDuplicado = validarMunicipioDuplicado();
		if(municipioDuplicado) 
			return;
		Municipio municipioGuardado = ComunicacionServiciosSis.setMunicipio(infoMunicipio);
		if(!municipioGuardado.isError()) {
			if(blnModificarMunicipio)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_MODIFICAR_EXITOSO);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_GUARDAR_EXITOSO);	
			inicializarVariables();
			filtrarMunicipio();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_GUARDAR_FALLIDO);
		}
	}
	
	/**
	 * Metodo que valida si un Municipio esta duplicado
	 * @return municipioDuplicado
	 */
	public boolean validarMunicipioDuplicado() {
		boolean municipioDuplicado = false;
		/*Valida si el municipio existe con el mismo Departamento y nombre del Municipio*/
		
		if(infoMunicipio.getCodMunicipio()==null ||
				(infoMunicipio.getCodMunicipio()!=null 
					&& ((infoMunicipio.getCodDepartamento()!= codDeparamentoAnt.intValue())
					|| !(infoMunicipio.getNombreMunicipio().equals(strNombreMunicipioAnt))))) {
			Municipio fil = new Municipio();
			fil.setNombreMunicipio(infoMunicipio.getNombreMunicipio());
			fil.setCodDepartamento(infoMunicipio.getCodDepartamento());
			List<Municipio> municipio = ComunicacionServiciosSis.getMunicipioDuplicado(fil);
			if(municipio != null && !municipio.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_DUPLICADO_NOMBRE);
				municipioDuplicado = true;
			}
		}
		
		if(infoMunicipio.getCodMunicipio()==null || 
				(infoMunicipio.getCodMunicipio()!=null && infoMunicipio.getCodMunicipioDane() != codMunicipioDANEAnt )) {
			Municipio fil = new Municipio();
			fil.setCodMunicipioDane(infoMunicipio.getCodMunicipioDane());
			List<Municipio> municipio = ComunicacionServiciosSis.getMunicipioDuplicado(fil);
			if(municipio != null && !municipio.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_DUPLICADO_CODIGO);
				municipioDuplicado = true;
			}
		}
		return municipioDuplicado;
	}
	
	/* Metodo que habilita formulario para la creacion de Municipio*/
	public void crearMunicipio() {
		inicializarVariables();
		infoMunicipio = new Municipio();
		blnHabilitaFormulario = true;
		blnCrearMunicipio=true;
		blnFlgActivo = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_CREAR, getLocale());
	}
	
	/*Metodo que habilita formulario para la modificacion de Municipio*/
	public void modificarMunicipio(Municipio dataMunicipio) {
		infoMunicipio = dataMunicipio;
		guardarRegistrosAnteriores(dataMunicipio);
		inicializarVariables();
		if(infoMunicipio.getFlgActivo() != null) {
			if(infoMunicipio.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		blnHabilitaFormulario = true;
		blnModificarMunicipio=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_MODIFICAR, getLocale());
	}
	
	/*Metodo que guarda los registros del municipio que estaban antes de realizar una modificacion*/
	public void guardarRegistrosAnteriores(Municipio dataMunicipio) {
		strNombreMunicipioAnt = dataMunicipio.getNombreMunicipio().equals("") ? null : dataMunicipio.getNombreMunicipio();
		codDeparamentoAnt = dataMunicipio.getCodDepartamento()!= null ? dataMunicipio.getCodDepartamento().intValue() : null;
		codMunicipioDANEAnt = dataMunicipio.getCodMunicipioDane() != null ? dataMunicipio.getCodMunicipioDane() : null;
	}
	
	/*Metodo que habilita formulario para la consultar de Municipio*/
	public void consultarMunicipio(Municipio dataMunicipio) {
		infoMunicipio = dataMunicipio;
		inicializarVariables();
		if(infoMunicipio.getFlgActivo() != null) {
			if(infoMunicipio.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		blnHabilitaFormulario = true;
		blnConsultarMunicipio = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_CONSULTAR, getLocale());
	}

	/* Metodo que habilita formulario para la consultar de Municipio*/
	public void eliminarMunicipio() {
		inicializarVariables();
		blnEliminarMunicipio=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_ELIMINAR, getLocale());
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_MUNICIPIO_ELIMINAR_CONFIRMAR, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarMunicipio').show();");
	}
	
	/* Metodo que cancela la accion realizada*/
	public void cancelarFormulario() {
		blnCancelarFormulario = true;
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DESCARTAR_CAMBIOS, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarMunicipio').show();");
	}
	
	/**
	 * Metodo que filtra pais segun lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return
	 */
	public List<String> listarMunicipioFiltro(String entidadPrivQuery){
		List<String> filtroMunicipio = new ArrayList<>();
		for(Municipio municipio : lstMunicipio ) {
			if(municipio.getNombreMunicipio().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
				filtroMunicipio.add(municipio.getNombreMunicipio());
			}
		}
		return filtroMunicipio;
	}
	
	/* Metodo que precesa respuesta dependiendo de la acción realizada*/
	public void procesarRespuestaConfirmacion() {
		if(blnEliminarMunicipio){
			eliminarPaisConfirmado();
		}else if(blnCancelarFormulario||blnConsultarMunicipio){
			cancelarFormularioConfirmado();
		}
	}
	
	/* Metodo que se ejecuta una vez el usuario ya haya confirmado la eliminacion del Municipio seleccionado*/
	public void eliminarPaisConfirmado() {
		eliminarLogicamenteMunicipio();
		inicializarVariables();
		filtrarMunicipio();
	}
	
	/**
	 * Metodo que elimina un Municipio de manera logica. 
	 * FLG_ACTIVO = 0
	 */
	public void eliminarLogicamenteMunicipio() {
		infoMunicipio.setAudFechaActualizacion(new Date());	
		infoMunicipio.setAudCodRol(codAudCodRol.shortValue());
		infoMunicipio.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoMunicipio.setAudAccion(codAudAccionDelete);
		infoMunicipio.setFlgActivo((short)0);	
		Municipio municipioGuardado = ComunicacionServiciosSis.setMunicipio(infoMunicipio);
		if(!municipioGuardado.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Municipio eliminado logicamente exitosamente");
			inicializarVariables();
			filtrarMunicipio();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Error al eliminar el Municipio logicamente");
		}
	}
	
	/*Metodo que se ejecuta una vez el usuario ya haya confirmado la cancelacion del formulario*/
	public void cancelarFormularioConfirmado() {
		inicializarVariables();
		filtrarMunicipio();
	}
	
	/*Metodo que filtra los Municipio*/
	public void filtrarMunicipio() {
		Municipio filtroMunicipio = new Municipio();
		filtroMunicipio.setNombreMunicipio(strBusquedaMunicipio);
		lstMunicipio = ComunicacionServiciosSis.getMunicipioFiltro(filtroMunicipio);
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
	 * @return the blnCrearMunicipio
	 */
	public boolean isBlnCrearMunicipio() {
		return blnCrearMunicipio;
	}

	/**
	 * @param blnCrearMunicipio the blnCrearMunicipio to set
	 */
	public void setBlnCrearMunicipio(boolean blnCrearMunicipio) {
		this.blnCrearMunicipio = blnCrearMunicipio;
	}

	/**
	 * @return the blnModificarMunicipio
	 */
	public boolean isBlnModificarMunicipio() {
		return blnModificarMunicipio;
	}

	/**
	 * @param blnModificarMunicipio the blnModificarMunicipio to set
	 */
	public void setBlnModificarMunicipio(boolean blnModificarMunicipio) {
		this.blnModificarMunicipio = blnModificarMunicipio;
	}

	/**
	 * @return the blnConsultarMunicipio
	 */
	public boolean isBlnConsultarMunicipio() {
		return blnConsultarMunicipio;
	}

	/**
	 * @param blnConsultarMunicipio the blnConsultarMunicipio to set
	 */
	public void setBlnConsultarMunicipio(boolean blnConsultarMunicipio) {
		this.blnConsultarMunicipio = blnConsultarMunicipio;
	}

	/**
	 * @return the blnEliminarMunicipio
	 */
	public boolean isBlnEliminarMunicipio() {
		return blnEliminarMunicipio;
	}

	/**
	 * @param blnEliminarMunicipio the blnEliminarMunicipio to set
	 */
	public void setBlnEliminarMunicipio(boolean blnEliminarMunicipio) {
		this.blnEliminarMunicipio = blnEliminarMunicipio;
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
	 * @return the strBusquedaMunicipio
	 */
	public String getStrBusquedaMunicipio() {
		return strBusquedaMunicipio;
	}

	/**
	 * @param strBusquedaMunicipio the strBusquedaMunicipio to set
	 */
	public void setStrBusquedaMunicipio(String strBusquedaMunicipio) {
		this.strBusquedaMunicipio = strBusquedaMunicipio;
	}

	/**
	 * @return the lstMunicipio
	 */
	public List<Municipio> getLstMunicipio() {
		return lstMunicipio;
	}

	/**
	 * @param lstMunicipio the lstMunicipio to set
	 */
	public void setLstMunicipio(List<Municipio> lstMunicipio) {
		this.lstMunicipio = lstMunicipio;
	}

	/**
	 * @return the infoMunicipio
	 */
	public Municipio getInfoMunicipio() {
		return infoMunicipio;
	}

	/**
	 * @param infoMunicipio the infoMunicipio to set
	 */
	public void setInfoMunicipio(Municipio infoMunicipio) {
		this.infoMunicipio = infoMunicipio;
	}
	
	/**
	 * @return the strNombreMunicipioAnt
	 */
	public String getStrNombreMunicipioAnt() {
		return strNombreMunicipioAnt;
	}

	/**
	 * @param strNombreMunicipioAnt the strNombreMunicipioAnt to set
	 */
	public void setStrNombreMunicipioAnt(String strNombreMunicipioAnt) {
		this.strNombreMunicipioAnt = strNombreMunicipioAnt;
	}

	/**
	 * @return the codDeparamentoAnt
	 */
	public Integer getCodDeparamentoAnt() {
		return codDeparamentoAnt;
	}

	/**
	 * @param codDeparamentoAnt the codDeparamentoAnt to set
	 */
	public void setCodDeparamentoAnt(Integer codDeparamentoAnt) {
		this.codDeparamentoAnt = codDeparamentoAnt;
	}
	
	/**
	 * @return the codMunicipioDANEAnt
	 */
	public Integer getCodMunicipioDANEAnt() {
		return codMunicipioDANEAnt;
	}

	/**
	 * @param codMunicipioDANEAnt the codMunicipioDANEAnt to set
	 */
	public void setCodMunicipioDANEAnt(Integer codMunicipioDANEAnt) {
		this.codMunicipioDANEAnt = codMunicipioDANEAnt;
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
