
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
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;

import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
* @author Maria Alejandra Colorado
* @version 1.0
* @Class DepartamentoBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Parametrica Departamento
* Fecha: 06/05/2019
*/
@Named
@ViewScoped
@ManagedBean
public class DepartamentoBean extends BaseBean implements Serializable{
	private static final long serialVersionUID = -1981489735745035228L;
	
	private boolean blnHabilitaFormulario;
	private boolean blnCrearDepartamento;
	private boolean blnModificarDepartamento;
	private boolean blnConsultarDepartamento;
	private boolean blnEliminarDepartamento;
	private boolean blnCancelarFormulario;
	private boolean blnFlgActivo;
	
	private String  strAccionRealizada;
	private String 	strMensaje;
	private String 	strBusquedaDepartamento;
	
	private List<Departamento> lstDepartamento;
	
	private Departamento infoDepartamento;
	private String nombreDepartamentoAnt;

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
		lstDepartamento =  new ArrayList<>();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		inicializarVariables();
		inicializarVariablesAuditoria();
		validarRolPermitido();
		filtrarDepartamento();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}
	
	/* Metodo que inicializa las variables de la vista */
	public void  inicializarVariables() {
		blnHabilitaFormulario=false;
		blnCrearDepartamento=false;
		blnModificarDepartamento=false;
		blnConsultarDepartamento=false;
		blnEliminarDepartamento=false;
		blnFlgActivo = false;
		strAccionRealizada ="";
		strMensaje="";
		strBusquedaDepartamento = "";	
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
	
	/*Metodo que guarda la modificacion o creación de un Departamento*/
	public void guardarDepartamento() {
		infoDepartamento.setAudCodRol(new BigDecimal(codAudCodRol));
		infoDepartamento.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoDepartamento.setAudFechaActualizacion(new Date());
		if(blnModificarDepartamento)
			infoDepartamento.setAudAccion(codAudAccionUpdate);
		else
			infoDepartamento.setAudAccion(codAudAccionInsert);
		if(blnFlgActivo)
			infoDepartamento.setFlgActivo(new BigDecimal(1));
		else
			infoDepartamento.setFlgActivo(new BigDecimal(0));
		
		boolean departamentoDuplicado = validarDepartamentoDuplicado();
		if(departamentoDuplicado) 
			return;
		Departamento departamentoGuardado = ComunicacionServiciosSis.setDepartamento(infoDepartamento);
		if(!departamentoGuardado.isError()) {
			if(blnModificarDepartamento)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_DEPARTAMENTO_MODIFICAR_EXITOSO);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_DEPARTAMENTO_GUARDAR_EXITOSO);
			inicializarVariables();
			filtrarDepartamento();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PARAMETRICA_DEPARTAMENTO_GUARDAR_FALLIDO);
		}
	}
	
	/**
	 * Metodo que valida si un departamento esta duplicado
	 * @return departamentoDuplicado
	 */
	public boolean validarDepartamentoDuplicado() {
		boolean departamentoDuplicado = false;
		if(infoDepartamento.getCodDepartamento()==null ||
				(infoDepartamento.getCodDepartamento()!=null && !(infoDepartamento.getNombreDepartamento().equals(nombreDepartamentoAnt)))) {
			Departamento fil = new Departamento();
			fil.setNombreDepartamento(infoDepartamento.getNombreDepartamento());
			List<Departamento> departamento = ComunicacionServiciosSis.getDepartamentoDuplicado(fil);
			if(departamento != null && !departamento.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_NOMBRE_DEPARTAMENTO_DUPLICADO);
				departamentoDuplicado = true;
			}
		}
		return departamentoDuplicado;
	}
	
	/*Metodo que habilita formulario para la creacion de Departamento*/
	public void crearDepartamento() {
		inicializarVariables();
		infoDepartamento = new Departamento();
		infoDepartamento.setCodPais(new BigDecimal(TipoParametro.PAIS_COLOMBIA.getValue()));
		blnHabilitaFormulario = true;
		blnCrearDepartamento=true;
		blnFlgActivo = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPARTAMENTO_CREAR, getLocale());
	}
	
	/*Metodo que habilita formulario para la modificacion de Departamento*/
	public void modificarDepartamento(Departamento dataDepartamento) {
		infoDepartamento = dataDepartamento;
		nombreDepartamentoAnt = dataDepartamento.getNombreDepartamento();
		inicializarVariables();
		if(infoDepartamento.getFlgActivo() != null) {
			if(infoDepartamento.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		blnHabilitaFormulario = true;
		blnModificarDepartamento=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPARTAMENTO_MODIFICAR, getLocale());
	}
	
	/* Metodo que habilita formulario para la consultar de Departamento*/
	public void consultarDepartamento(Departamento dataDepartamento) {
		infoDepartamento = dataDepartamento;
		inicializarVariables();
		if(infoDepartamento.getFlgActivo() != null) {
			if(infoDepartamento.getFlgActivo().intValue() ==  1)
				blnFlgActivo = true;
			else
				blnFlgActivo = false;
		}
		blnHabilitaFormulario = true;
		blnConsultarDepartamento = true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPARTAMENTO_CONSULTAR, getLocale());
	}

	/*Metodo que habilita formulario para la consultar de Departamento*/
	public void eliminarDepartamento() {
		inicializarVariables();
		blnEliminarDepartamento=true;
		strAccionRealizada = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPARTAMENTO_ELIMINAR, getLocale());
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DEPARTAMENTO_ELIMINAR_CONFIRMAR, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarDepartamento').show();");
	}
	
	/* Metodo que cancela la accion Departamento*/
	public void cancelarFormulario() {
		blnCancelarFormulario = true;
		strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PARAMETRICA_DESCARTAR_CAMBIOS, getLocale());
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarDepartamento').show();");
	}
	
	/**
	 * Metodo que filtra pais segun lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return filtroDepartamento
	 */
	public List<String> listarDepartamentoFiltro(String entidadPrivQuery){
		List<String> filtroDepartamento = new ArrayList<>();
		if(!lstDepartamento.isEmpty()) {
			for(Departamento departamento : lstDepartamento ) {
				if(departamento.getNombreDepartamento().toLowerCase().contains(entidadPrivQuery.toLowerCase())) {
					filtroDepartamento.add(departamento.getNombreDepartamento());
				}
			}
		}
		return filtroDepartamento;
	}
	
	/*Metodo que precesa respuesta dependiendo de la acción realizada*/
	public void procesarRespuestaConfirmacion() {
		if(blnEliminarDepartamento){
			eliminarDepartamentoConfirmado();
		}else if(blnCancelarFormulario||blnConsultarDepartamento){
			cancelarFormularioConfirmado();
		}
	}
	
	/*Metodo que se ejecuta una vez el usuario ya haya confirmado la eliminacion del Departamento seleccionado*/
	public void eliminarDepartamentoConfirmado() {
		eliminarLogicamenteDepartamento();
		inicializarVariables();
		filtrarDepartamento();
	}
	
	/**
	 * Metodo que elimina un Departamento de manera logica. 
	 * FLG_ACTIVO = 0
	 */
	public void eliminarLogicamenteDepartamento() {
		infoDepartamento.setAudCodRol(new BigDecimal(codAudCodRol));
		infoDepartamento.setAudCodUsuario(new BigDecimal(codAudusuario));
		infoDepartamento.setAudAccion(codAudAccionDelete);
		infoDepartamento.setFlgActivo(new BigDecimal(0));
		infoDepartamento.setAudFechaActualizacion(new Date());
		Departamento paisDepartamento = ComunicacionServiciosSis.setDepartamento(infoDepartamento);
		if(!paisDepartamento.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_REGISTRO_ELIMINADO_CORRECTAMENTE);
			inicializarVariables();
			filtrarDepartamento();
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_ELIMINACION_FALLIDA);
		}
	}
	
	/*Metodo que se ejecuta una vez el usuario ya haya confirmado la cancelacion del formulario*/
	public void cancelarFormularioConfirmado() {
		inicializarVariables();
		filtrarDepartamento();
	}
	
	/* Metodo que filtra los Departamento*/
	public void filtrarDepartamento() {
		Departamento filtroDepartamento = new Departamento();
		filtroDepartamento.setNombreDepartamento(strBusquedaDepartamento);
		lstDepartamento = ComunicacionServiciosSis.getDepartamentoFiltro(filtroDepartamento);
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
	 * @return the blnCrearDepartamento
	 */
	public boolean isBlnCrearDepartamento() {
		return blnCrearDepartamento;
	}

	/**
	 * @param blnCrearDepartamento the blnCrearDepartamento to set
	 */
	public void setBlnCrearDepartamento(boolean blnCrearDepartamento) {
		this.blnCrearDepartamento = blnCrearDepartamento;
	}

	/**
	 * @return the blnModificarDepartamento
	 */
	public boolean isBlnModificarDepartamento() {
		return blnModificarDepartamento;
	}

	/**
	 * @param blnModificarDepartamento the blnModificarDepartamento to set
	 */
	public void setBlnModificarDepartamento(boolean blnModificarDepartamento) {
		this.blnModificarDepartamento = blnModificarDepartamento;
	}

	/**
	 * @return the blnConsultarDepartamento
	 */
	public boolean isBlnConsultarDepartamento() {
		return blnConsultarDepartamento;
	}

	/**
	 * @param blnConsultarDepartamento the blnConsultarDepartamento to set
	 */
	public void setBlnConsultarDepartamento(boolean blnConsultarDepartamento) {
		this.blnConsultarDepartamento = blnConsultarDepartamento;
	}

	/**
	 * @return the blnEliminarDepartamento
	 */
	public boolean isBlnEliminarDepartamento() {
		return blnEliminarDepartamento;
	}

	/**
	 * @param blnEliminarDepartamento the blnEliminarDepartamento to set
	 */
	public void setBlnEliminarDepartamento(boolean blnEliminarDepartamento) {
		this.blnEliminarDepartamento = blnEliminarDepartamento;
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
	 * @return the strBusquedaDepartamento
	 */
	public String getStrBusquedaDepartamento() {
		return strBusquedaDepartamento;
	}

	/**
	 * @param strBusquedaDepartamento the strBusquedaDepartamento to set
	 */
	public void setStrBusquedaDepartamento(String strBusquedaDepartamento) {
		this.strBusquedaDepartamento = strBusquedaDepartamento;
	}

	/**
	 * @return the lstDepartamento
	 */
	public List<Departamento> getLstDepartamento() {
		return lstDepartamento;
	}

	/**
	 * @param lstDepartamento the lstDepartamento to set
	 */
	public void setLstDepartamento(List<Departamento> lstDepartamento) {
		this.lstDepartamento = lstDepartamento;
	}

	/**
	 * @return the infoDepartamento
	 */
	public Departamento getInfoDepartamento() {
		return infoDepartamento;
	}

	/**
	 * @param infoDepartamento the infoDepartamento to set
	 */
	public void setInfoDepartamento(Departamento infoDepartamento) {
		this.infoDepartamento = infoDepartamento;
	}

	/**
	 * @return the nombreDepartamentoAnt
	 */
	public String getNombreDepartamentoAnt() {
		return nombreDepartamentoAnt;
	}

	/**
	 * @param nombreDepartamentoAnt the nombreDepartamentoAnt to set
	 */
	public void setNombreDepartamentoAnt(String nombreDepartamentoAnt) {
		this.nombreDepartamentoAnt = nombreDepartamentoAnt;
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
