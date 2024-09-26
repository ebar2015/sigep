package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entities.Contrato;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioEntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.mbean.ext.ContratoExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosCO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
public class DesasociarUsuarioEntidadBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -1199368366370427715L;

	private PersonaDTO persona;
	private boolean disableButton = true;
	private boolean entidadSeleccionada = false;
	private String strMensajeDesasociar = "";

	private TipoAsociacionDTO tipoAsociacion;

	private String numeroDocumento;
	
	private String mensaje;

	public boolean isDisableButton() {
		return disableButton;
	}

	public void setDisableButton(boolean disable) {
		this.disableButton = disable;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public boolean isEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void setEntidadSeleccionada(boolean entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}
	
	/**
	 * @return the strMensajeDesasociar
	 */
	public String getStrMensajeDesasociar() {
		return strMensajeDesasociar;
	}

	/**
	 * @param strMensajeDesasociar the strMensajeDesasociar to set
	 */
	public void setStrMensajeDesasociar(String strMensajeDesasociar) {
		this.strMensajeDesasociar = strMensajeDesasociar;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		throw new NotSupportedException();
	}

	@PostConstruct
	public void init() {
		this.validaPermisosAcciones(recursoId);
		persona = new PersonaDTO();

		if (getEntidadUsuario() != null) {
			entidadSeleccionada = true;
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_SIN_ENTIDAD_ASOCIADA);
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		

		strMensajeDesasociar = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.DLG_DESASOCIAR_USUARIO, getLocale())
				.replace("%ENTIDAD%", this.getEntidadUsuario().getNombreEntidad());
	}

	@Override
	public String persist() throws NotSupportedException {
		boolean desasociado = false;
		if (persona != null) {
			try {
				boolean permiteDesasociarUsuario = false;
				boolean usuarioSesionEsJefeTH = false;
				boolean usuarioSesionEsJefeCONTRATOS = false;
				List<RolDTO> rolesDesasociarUsuario = AdministracionDelegate.obtenerRolesPorDescripcion(
						RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTRATOS, RolDTO.OPERADOR_TALENTO_HUMANO,
						RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.OPERADOR_CONTRATOS, RolDTO.ADMINISTRADOR_FUNCIONAL);
				for (RolDTO rolUsuario : getRolesUsuarioSesion()) {
					if (rolesDesasociarUsuario.contains(rolUsuario)) {
						permiteDesasociarUsuario = true;
						if (RolDTO.JEFE_TALENTO_HUMANO.equalsIgnoreCase(rolUsuario.getNombre())) {
							usuarioSesionEsJefeTH = true;
						}
						if (RolDTO.JEFE_CONTRATOS.equalsIgnoreCase(rolUsuario.getNombre())) {
							usuarioSesionEsJefeCONTRATOS = true;
						}
					}
				}
				if (permiteDesasociarUsuario) {
					boolean tieneJTHyContratos = false;
					if (usuarioSesionEsJefeTH && usuarioSesionEsJefeCONTRATOS ) {
						tieneJTHyContratos = true;
					}
					if ((usuarioSesionEsJefeTH || usuarioSesionEsJefeCONTRATOS) && !tieneJTHyContratos) {
						UsuarioPasswordDTO usuarioADesasociar = IngresoSistemaDelegate.getUsuarioByLogin(
								this.persona.getTipoIdentificacionId().getId(), this.persona.getNumeroIdentificacion());
						UsuarioEntidadDTO usuarioEntidad = IngresoSistemaDelegate
								.consultarUsuarioEntidad(usuarioADesasociar.getId(), getEntidadUsuario().getId());
						if (usuarioEntidad != null) {
							this.tipoAsociacion = AdministracionDelegate
									.findTipoAsociacion(usuarioEntidad.getTipoAsociacion());

							if (this.tipoAsociacion != null) {
								
								mensaje =  MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_DESASOCIACION_FALLIDA_CONTRATISTA,getLocale())
										.replace("%Tipo_Vinculacion%", this.tipoAsociacion.getDescripcion());
								
								if (usuarioSesionEsJefeTH && TipoAsociacionDTO.CONTRATISTA
										.equalsIgnoreCase(this.tipoAsociacion.getDescripcion())) {
									mostrarMensaje(FacesMessage.SEVERITY_WARN,
											MessagesBundleConstants.DLG_HEADER_MENSAJES,
											mensaje);
									return null;
								} else if (usuarioSesionEsJefeCONTRATOS && TipoAsociacionDTO.SERVICIO_PUBLICO_ID
								          == this.tipoAsociacion.getId()) {
									mostrarMensaje(FacesMessage.SEVERITY_WARN,
											MessagesBundleConstants.DLG_HEADER_MENSAJES,
											mensaje);
									return null;
								}
							} else {
								mostrarMensaje(FacesMessage.SEVERITY_WARN,
										MessagesBundleConstants.DLG_HEADER_MENSAJES,
										MessagesBundleConstants.DLG_DESASOCIACION_FALLIDA);
								return null;
							}
						} else {
							mostrarMensaje(FacesMessage.SEVERITY_WARN,
									MessagesBundleConstants.DLG_HEADER_MENSAJES,
									MessagesBundleConstants.DLG_PERSONA_DESASOCIADA);
							return null;
						}
					}
					/*Antes de ser desasociar a la persona, se debe de validar que esta no presente una vinculación a un cargo, a una UTL, o a un contrato activo para la entidad*/
					if(validarVinculacionCargoActivo(persona.getId(), getEntidadUsuario().getId())) return null;
					if (validarVinculacionUTL(persona.getId(), getEntidadUsuario().getId())) return null;
					if(validarContratoActivo(persona.getId(), getEntidadUsuario().getId())) return null;
					desasociado = IngresoSistemaDelegate.desasociarUsuarioEntidad(persona.getId(),
							getEntidadUsuario().getId(), persona.getTipoIdentificacionId().getId(),
							persona.getNumeroIdentificacion(), getUsuarioSesion());
					if (desasociado) {
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_DESASOCIACION_EXITOSA);
						persona = new PersonaDTO();
						this.setDisableButton(true);
					} else {
						mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_DESASOCIACION_FALLIDA);
						this.setDisableButton(true);
					}
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				}
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_DESASOCIACION_FALLIDA);
				this.setDisableButton(true);
			}
		}
		return null;
	}
	
	/**
	 * Metodo que valida, si la persona cuenta con un cargo activo para la entidad a la cual se va a desasociar*/
	public boolean validarVinculacionCargoActivo(Long codPersona, Long codEntidad){
		boolean blnVinculacionActiva = false;
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodEntidad(codEntidad);
		objFilter.setFlgActivo((short)1);
		objFilter.setCodPersona(BigDecimal.valueOf(codPersona));
		List<VinculacionExt> list = ComunicacionServiciosVin.getvinculacionactual(objFilter);
		if (!list.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_MENSAJE_VINCULACION_A_ENTIDAD_SESION);
			blnVinculacionActiva = true;
		}
		return blnVinculacionActiva;
	}
	
	/**
	 * Metodo para validar si una persona cuenta con una vinculación actual en una UTL*/
	public boolean validarVinculacionUTL(Long codPersona, Long codEntidad) {
		boolean blnVinculacionActivaUTL = false;
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(BigDecimal.valueOf(codPersona));
		objFilter.setFlgActivo((short) 1);
		objFilter.setCodEntidad(codEntidad);
		List<HojaVidaExt> list = ComunicacionServiciosHV.getHVPersonaAsociadaUTL(objFilter);
		if (!list.isEmpty()) {
			String strMensajeVinculacionUTL = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_VINCULACION_PERSONA_UTL, getLocale())
					.replace("%nombrePlanta%", list.get(0).getNombreUtlUan());
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					strMensajeVinculacionUTL);
			blnVinculacionActivaUTL = true;
		}
		
		return blnVinculacionActivaUTL;
	}
	
	/**Metodo que valida si la persona cuenta con un contrato activo para la entidad a la cual se va a desasociar*/
	public boolean validarContratoActivo(Long codPersona, Long codEntidad) {
		boolean blnContratoActivo = false;
		Contrato objFilter 	= new Contrato();
		objFilter.setCodPersona(BigDecimal.valueOf(codPersona));
		objFilter.setCodEntidad(codEntidad);
		objFilter.setFlgActivo((short) 1);
		objFilter.setLimitEnd(0);
		objFilter.setLimitEnd(1000);
		List<ContratoExt> list = ComunicacionServiciosCO.getContratoFiltro(objFilter);
		if (!list.isEmpty()) {
			Entidad objEntidad = ComunicacionServiciosEnt.getEntidadPorId(codEntidad);
			String contenido = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_USUARIO_CONTRATO_CON_ENTIDAD, getLocale())
					.replace("%nombreEntidad%", objEntidad.getNombreEntidad());

			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, contenido);
			blnContratoActivo = true;
		}
		return blnContratoActivo;
	}
	
	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			if (this.conversation.isTransient()) {
				this.conversation.begin();
				this.conversation.setTimeout(timeOut);
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_URL_INVALID);
			return;
		}
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void buscarPersona() {
		PersonaDTO personaDTO = null;
		try {
			personaDTO = IngresoSistemaDelegate.findByTipoDocumentoAndNumeroIdentificacion(
					persona.getTipoIdentificacionId().getId(), persona.getNumeroIdentificacion(),
					getEntidadUsuario().getId());
		} catch (SIGEP2SistemaException e) {
			personaDTO = null;
		}

		if (personaDTO != null) {
			personaDTO.setTipoIdentificacionId(persona.getTipoIdentificacionId());
			persona = personaDTO;
			this.setDisableButton(false);
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_PERSONA_NO_ENCONTRADA);
			this.setDisableButton(true);
			persona = new PersonaDTO();
		}
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void limpiarCampos() {
		persona = new PersonaDTO();
	}

	public void limpiarDoc() {
		this.numeroDocumento = null;
	}

	public void listener() throws NotSupportedException {
		update();
	}

	public boolean validarExisteyActivo() {
		if (getEntidadUsuario() != null) {
			return IngresoSistemaDelegate.usuarioExisteyActivo(persona, getEntidadUsuario().getId());
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,  MessagesBundleConstants.MSG_USUARIO_SIN_ENTIDAD_ASOCIADA);
			return false;
		}
	}

	public boolean validarExisteyAsociado() {
		if (getEntidadUsuario() != null) {
			return IngresoSistemaDelegate.usuarioExisteyActivo(persona, getEntidadUsuario().getId(),
					tipoAsociacion.getId());
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_USUARIO_SIN_ENTIDAD_ASOCIADA);
			return false;
		}
	}

}