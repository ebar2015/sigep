package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadHistoricoEstados;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
//import co.gov.dafp.sigep2.entities.EntidadHistoricoEstados;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.EntidadHistoricoEstadosExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ViewScoped
@ManagedBean

public class GestionarEstadoEntidadBean extends BaseBean implements Serializable{
	
	private Entidad entidadSelected;
	private EntidadHistoricoEstados entidadHistorico;
	private List<EntidadHistoricoEstadosExt> lstHistoricoEntidad;
	
	private String strMensaje;
	Integer codAudCodRol, codAudAccionInsert = 3, codAudAccionUpdate = 2, codAudAccionDelete = 1;
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	ExternalContext externalContext;
	RequestContext requestContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	public GestionarEstadoEntidadBean() {
		super();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		cerrarSessionFuncion(AMBITO_POLITICAS);
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		codAudCodRol = (int) usuarioSesion.getCodRol();
		codAudCodRol = (int) this.getRolAuditoria().getId();		
	}
	
	@PostConstruct
	public void init() {
		
    	if(!validarPermisoRol()){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"window.location.assign('entidad/../../gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;

    	}		
		
		externalContext= FacesContext.getCurrentInstance().getExternalContext();
		entidadSelected = (Entidad) externalContext.getSessionMap().get("entidadGestionEstado");
		entidadHistorico = new EntidadHistoricoEstados();
		obtenerHistoricoEntidad();
	}

	@Override
	public String persist() throws NotSupportedException {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		EntidadHistoricoEstados historicoRespuesta;
		Entidad entidadRespuesta;
		entidadHistorico.setAudAccion(codAudAccionInsert);
		entidadHistorico.setAudCodRol(codAudCodRol);
		entidadHistorico.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
		entidadHistorico.setAudFechaActualizacion(new Date());
		entidadHistorico.setCodEntidad(entidadSelected.getCodEntidad());
		if(entidadSelected.getCodEstadoGestion()!=null)
			entidadHistorico.setIdEstadoAnterior(BigDecimal.valueOf(entidadSelected.getCodEstadoGestion()));
		historicoRespuesta = ComunicacionServiciosEnt.setEntidadHistoricoEstado(entidadHistorico);
		if (!historicoRespuesta.isError()){
			entidadSelected.setCodEstadoGestion(entidadHistorico.getIdEstadoNuevo().intValue());
			entidadSelected.setAudAccion(codAudAccionUpdate);
			entidadSelected.setAudFechaActualizacion(new Date());
			entidadSelected.setAudCodRol(BigDecimal.valueOf(codAudCodRol));
			entidadSelected.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			entidadRespuesta = ComunicacionServiciosEnt.setEntidade(entidadSelected);
			if(!entidadRespuesta.isError())
				this.setStrMensaje(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_PROCESO_EXITOSO, getLocale()));	
			else
				this.setStrMensaje(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO,
								getLocale()) +entidadRespuesta.toString());
		}else{
			this.setStrMensaje(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO,
							getLocale()) + historicoRespuesta.toString());
			}
		requestContext.execute("PF('dlgGestionarEstadoEntidad').show();");
		entidadHistorico = new EntidadHistoricoEstados();
		obtenerHistoricoEntidad();
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
	
	public void gestionarEstadoEntidad(Entidad entidad){
		entidad = new Entidad();
		entidadSelected = entidad;
	}
	
	public void cancelar(){
		externalContext= FacesContext.getCurrentInstance().getExternalContext();
		requestContext = RequestContext.getCurrentInstance();
		entidadHistorico = new EntidadHistoricoEstados();
		try {
			externalContext.redirect("../../entidad/gestionarEntidad.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void guardar(){
		try {
			persist();
		} catch (NotSupportedException e) {
			e.printStackTrace();
		}
	}

	public Entidad getEntidadSelected() {
		return entidadSelected;
	}

	public void setEntidadSelected(Entidad entidadSelected) {
		this.entidadSelected = entidadSelected;
	}

	public EntidadHistoricoEstados getEntidadHistorico() {
		return entidadHistorico;
	}

	public void setEntidadHistorico(EntidadHistoricoEstados entidadHistorico) {
		this.entidadHistorico = entidadHistorico;
	}

	public String getStrMensaje() {
		return strMensaje;
	}

	public void setStrMensaje(String strMensaje) {
		this.strMensaje = strMensaje;
	}
	
	public List<EntidadHistoricoEstadosExt> getLstHistoricoEntidad() {
		return lstHistoricoEntidad;
	}

	public void setLstHistoricoEntidad(List<EntidadHistoricoEstadosExt> lstHistoricoEntidad) {
		this.lstHistoricoEntidad = lstHistoricoEntidad;
	}

	private void obtenerHistoricoEntidad(){
		EntidadHistoricoEstados historicoBuscar = new EntidadHistoricoEstados();
		historicoBuscar.setCodEntidad(entidadSelected.getCodEntidad());
		lstHistoricoEntidad = ComunicacionServiciosEnt.getEntidadHistoricoEstados(historicoBuscar);
		if(lstHistoricoEntidad!=null && lstHistoricoEntidad.size()>0 && entidadHistorico!=null){
			EntidadHistoricoEstadosExt estadoActual =lstHistoricoEntidad.get(0); 
			BigDecimal estado = estadoActual.getIdEstadoNuevo();
			entidadHistorico.setIdEstadoNuevo(estado);
		}
	}
	
	private boolean validarPermisoRol(){
		try {
			if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SISTEMA, RolDTO.CRUD,RolDTO.ADMINISTRADOR_FUNCIONAL))
				return true;
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		return true;
	}		
	
	

}
