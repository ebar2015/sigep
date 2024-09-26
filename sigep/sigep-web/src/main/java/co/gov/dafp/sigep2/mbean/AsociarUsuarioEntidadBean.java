package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
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
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.entity.view.GeneroDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.enums.TipoDocumentoIdentidadEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
public class AsociarUsuarioEntidadBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 3695472896249884567L;
	private String TIPO_DOC_PASAPORTE = TipoDocumentoIdentidadEnum.PA.getDescripcion();

	private String tipoId;
	private String numeroId;

	private PersonaDTO persona;
	private TipoAsociacionDTO tipoAsociacion;

	private Boolean disponibleEdicion = Boolean.FALSE;
	private Boolean habilitarFormulario = Boolean.FALSE;

	private String pathActivarUsuario = "/usuario/asociarUsuarioEntidad.xhtml";
	private String recursoIdActivarUsuario = "recursoId=ActivarUsuarioTag";

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public Boolean getDisponibleEdicion() {
		return disponibleEdicion;
	}

	public void setDisponibleEdicion(Boolean disponibleEdicion) {
		this.disponibleEdicion = disponibleEdicion;
	}

	public Boolean getHabilitarFormulario() {
		return habilitarFormulario;
	}

	public void setHabilitarFormulario(Boolean habilitarFormulario) {
		this.habilitarFormulario = habilitarFormulario;
	}

	@PostConstruct
	public void init() {
		this.validaPermisosAcciones(recursoId);
		this.disponibleEdicion = Boolean.TRUE;
		this.habilitarFormulario = Boolean.FALSE;
		this.persona = new PersonaDTO();
		this.tipoAsociacion = null;
		if (tipoId != null) {
			TipoDocumentoDTO tipoDocumento = null;
			try {
				tipoDocumento = AdministracionDelegate.findTipoDocumentoId(Long.parseLong(tipoId));
				persona.setTipoIdentificacionId(tipoDocumento);
				persona.setNumeroIdentificacion(numeroId);
			} catch (NumberFormatException | SIGEP2SistemaException e) {
				e.printStackTrace();
			}

		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);

	}

	public TipoAsociacionDTO getTipoAsociacion() {
		return tipoAsociacion;
	}

	public void setTipoAsociacion(TipoAsociacionDTO tipoAsociacion) {
		this.tipoAsociacion = tipoAsociacion;
	}

	public String getTIPO_DOC_PASAPORTE() {
		return TIPO_DOC_PASAPORTE;
	}

	public void setTIPO_DOC_PASAPORTE(String tIPO_DOC_PASAPORTE) {
		TIPO_DOC_PASAPORTE = tIPO_DOC_PASAPORTE;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public String persist() throws NotSupportedException {
		try {
			Long tipoIdentificacion = this.persona.getCodTipoIdentificacion() != null
					? this.persona.getCodTipoIdentificacion() : this.persona.getTipoIdentificacionId().getId();
			UsuarioPasswordDTO usuario = IngresoSistemaDelegate.getUsuarioByLogin(tipoIdentificacion,
					this.persona.getNumeroIdentificacion());
			if (usuario != null) {
				List<UsuarioDTO> usuariosAsociados = IngresoSistemaDelegate
						.obtenerEntidadesUsuarioAsociadas(usuario.getId(), getEntidadUsuario().getId());
				
				//Verificar que tenga una hoja de vida activa asociada
				boolean validarHVActiva= validarHVActivaPersona(persona.getTipoIdentificacionId().getId(),this.persona.getNumeroIdentificacion(),  getEntidadUsuario().getId());
				if (!usuariosAsociados.isEmpty() && validarHVActiva) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_USUARIO_ASOCIADO_EXISTE);
					return null;
				}
			}
			persona.setCodTipoIdentificacion(tipoIdentificacion);
			IngresoSistemaDelegate.crearUsuario(persona, tipoAsociacion, getUsuarioSesion(), getEntidadUsuario());
			IngresoSistemaDelegate.asociarEntidad(persona, tipoAsociacion, getUsuarioSesion(), getEntidadUsuario());
			IngresoSistemaDelegate.solicitarRestablecerPassword(persona.getTipoIdentificacionId().getId(),
					persona.getNumeroIdentificacion(), getLocale());
			this.finalizarConversacion(pathActivarUsuario, MessagesBundleConstants.MSG_USUARIO_ACTIVACION_EXITOSA,
					recursoIdActivarUsuario);
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
		return null;
	}

	/**Metodo que valida si la persona cuenta con una HV activa para la entidad a asociar*/
	public boolean validarHVActivaPersona(Long tipoDocumento, String cedula, Long codEntidad) {
		boolean blnHVActiva = false;
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodTipoIentificacion(tipoDocumento.intValue());;
		objFilter.setCodEntidad(codEntidad);
		objFilter.setNumeroIdentificacion(cedula);
		List<HojaVidaExt> list = ComunicacionServiciosHV.getHVPersonaAsociada(objFilter);
		if (!list.isEmpty()) {
			blnHVActiva = true;
		}
		return blnHVActiva;
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
		}
		this.init();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void buscarPersona(Boolean redirect) {
		PersonaDTO personaDTO = null;
		try {
			personaDTO = IngresoSistemaDelegate.findByTipoDocumentoAndNumeroIdentificacion(
					persona.getTipoIdentificacionId().getId(), persona.getNumeroIdentificacion());

			if (personaDTO != null) {
				try {
					GeneroDTO generoDTO = AdministracionDelegate.findGeneroId(personaDTO.getCodGenero());
					if (generoDTO != null) {
						personaDTO.setGenero(generoDTO);
					}
				} catch (Exception e) {
					logger.error("String update()", e);
				}
				this.disponibleEdicion = Boolean.TRUE;
			} else {
				this.disponibleEdicion = Boolean.TRUE;
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("String update()", e);
		}

		if (personaDTO != null) {
			personaDTO.setTipoIdentificacionId(persona.getTipoIdentificacionId());
			persona = personaDTO;
		} else {
			personaDTO = new PersonaDTO();
			personaDTO.setTipoIdentificacionId(persona.getTipoIdentificacionId());
			personaDTO.setNumeroIdentificacion(persona.getNumeroIdentificacion());
			persona = personaDTO;
		}
		//Verificar que tenga una hoja de vida activa asociada
		boolean validarHVActiva= validarHVActivaPersona(persona.getTipoIdentificacionId().getId(),this.persona.getNumeroIdentificacion(),  getEntidadUsuario().getId());
		if (validarExisteyActivo() && redirect && validarHVActiva ) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_USUARIO_ASOCIADO_EXISTE);
			return;
		}
		this.habilitarFormulario = Boolean.TRUE;
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public boolean validarExisteyActivo() {
		if (getEntidadUsuario() != null) {
			return IngresoSistemaDelegate.usuarioExisteyActivo(persona, getEntidadUsuario().getId());
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"El usuario no tiene entidad asociada");
			return false;
		}
	}

	public boolean validarExisteyAsociado() {
		if (getEntidadUsuario() != null) {
			return IngresoSistemaDelegate.usuarioExisteyActivo(persona, getEntidadUsuario().getId(),
					tipoAsociacion.getId());
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"El usuario no tiene entidad asociada");
			return false;
		}
	}

	/**
	 * Se encarga de crear la persona <b>persona</b> en caso de no existir, a su
	 * vez, realiza la asociación del usuario a la entidad en sesión
	 * 
	 */
	public void guardarPersona() {
		//Verificar que tenga una hoja de vida activa asociada
		boolean validarHVActiva= validarHVActivaPersona(persona.getTipoIdentificacionId().getId(),this.persona.getNumeroIdentificacion(),  getEntidadUsuario().getId());
		if (validarExisteyActivo() && validarHVActiva) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_USUARIO_ASOCIADO_EXISTE);
		} else {
			try {
				
				if (persona.getId() == 0 || !verificarUsuario() ) {
					IngresoSistemaDelegate.crearUsuario(persona, tipoAsociacion, getUsuarioSesion(),
							getEntidadUsuario());
					buscarPersona(false);
				} else {
					IngresoSistemaDelegate.asociarEntidad(persona, tipoAsociacion, getUsuarioSesion(),
							getEntidadUsuario());
				}
				if (validarExisteyAsociado()) {
					try {
						this.finalizarConversacion(pathActivarUsuario,
								MessagesBundleConstants.MSG_USUARIO_ACTIVACION_EXITOSA, recursoIdActivarUsuario);
						IngresoSistemaDelegate.solicitarRestablecerPassword(persona.getTipoIdentificacionId().getId(),
								persona.getNumeroIdentificacion(), getLocale());
					} catch (Exception e) {
						logger.warn("void guardarPersona()", MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA);
					}
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_PROCESO_FALLIDO);
				}
			} catch (Exception e) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			}
		}
	}

	/**
	 * Metodo que verifica si la persona ya cuenta con un usuario
	 * Retorna false si no cuenta con usuario
	 * @return
	 */
	public boolean verificarUsuario() {
		boolean blUsuarioActivo = false;
		UsuarioExt objUsuario = new UsuarioExt();
		try {
			if(persona.getId() != 0) {
				objUsuario.setCodTipoIdentificacionPersona(new BigDecimal(persona.getTipoIdentificacionId().getId()));
				objUsuario.setNumeroIdentificacionPersona(persona.getNumeroIdentificacion());
				Usuario usuario = ComunicacionServiciosAdmin.getUsuarioByPersona(objUsuario);
				if(usuario.getCodUsuario() != null) {
					blUsuarioActivo = true;
				}
			}
				return blUsuarioActivo;
		} catch (Exception e) {
			return blUsuarioActivo;
		}
	}
	
	
	
	public void listener() {
		buscarPersona(true);
	}

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public String getNumeroId() {
		return numeroId;
	}

	public void setNumeroId(String numeroId) {
		this.numeroId = numeroId;
	}

	/**
	 * Se encarga de cancelar la accion y de regresar al formulario inicial para
	 * la asociacion de usuarios
	 * 
	 */
	public void cancelarAtras() {
		try {
			this.finalizarConversacion(pathActivarUsuario, null, recursoIdActivarUsuario);
		} catch (IOException e) {
			return;
		}
	}

}