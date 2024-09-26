package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;

@Named
@ConversationScoped
public class CambiarPasswordBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -1464926281294420360L;

	private String login;

	private String password;
	private String password2;

	private String passwordAnterior;

	private Boolean usuarioAutenticado = Boolean.FALSE;

	private Boolean mostrarContrasenia = Boolean.FALSE;
	private Boolean mostrarContrasenia2 = Boolean.FALSE;
	private Boolean mostrarContrasenia3 = Boolean.FALSE;
	private Boolean habilitarMostrarContrasenia = Boolean.FALSE;

	@Inject
	private SesionBean sesionBean;

	public String getNombreUsuario() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPasswordAnterior() {
		return passwordAnterior;
	}

	public String getPasswordAnteriorClaro() {
		return passwordAnterior;
	}

	public void setPasswordAnterior(String passwordAnterior) {
		this.passwordAnterior = passwordAnterior;
	}

	public Boolean getUsuarioAutenticado() {
		return usuarioAutenticado;
	}

	public void setUsuarioAutenticado(Boolean usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}

	public Boolean getMostrarContrasenia() {
		return mostrarContrasenia;
	}

	public void setMostrarContrasenia(Boolean mostrarContrasenia) {
		if (mostrarContrasenia == null) {
			this.mostrarContrasenia = Boolean.FALSE;
			return;
		}
		this.mostrarContrasenia = mostrarContrasenia;
	}

	public Boolean getMostrarContrasenia2() {
		return mostrarContrasenia2;
	}

	public void setMostrarContrasenia2(Boolean mostrarContrasenia2) {
		if (mostrarContrasenia2 == null) {
			this.mostrarContrasenia2 = Boolean.FALSE;
			return;
		}
		this.mostrarContrasenia2 = mostrarContrasenia2;
	}

	public Boolean getMostrarContrasenia3() {
		return mostrarContrasenia3;
	}

	public void setMostrarContrasenia3(Boolean mostrarContrasenia3) {
		if (mostrarContrasenia3 == null) {
			this.mostrarContrasenia3 = Boolean.FALSE;
			return;
		}
		this.mostrarContrasenia3 = mostrarContrasenia3;
	}

	public Boolean getHabilitarMostrarContrasenia() {
		return habilitarMostrarContrasenia;
	}

	public void setHabilitarMostrarContrasenia(Boolean habilitarMostrarContrasenia) {
		this.habilitarMostrarContrasenia = habilitarMostrarContrasenia;
	}

	public void cambiarMostrarContrasenia() {
		this.mostrarContrasenia = !mostrarContrasenia;
	}

	public void cambiarMostrarContrasenia2() {
		this.mostrarContrasenia2 = !mostrarContrasenia2;
	}

	public void cambiarMostrarContrasenia3() {
		this.mostrarContrasenia3 = !mostrarContrasenia3;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		// throw new NotSupportedException();
	}

	@Override
	public void init() throws NotSupportedException {
		this.validaPermisosAcciones(recursoId);
		this.passwordAnterior = null;
		this.password = null;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
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
		this.init();
	}

	@Override
	public String update() throws NotSupportedException {
		try {
			if (this.passwordAnterior != null && !this.passwordAnterior.equals(getUsuarioSesion().getContrasena())) {
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				TipoDocumentoDTO tipoDocumento = (TipoDocumentoDTO) contexto.getSessionMap().get("tipoDocumento");
				UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
				IngresoSistemaDelegate.login(tipoDocumento.getId(), usuarioSesion.getNombreUsuario(), passwordAnterior,
						null, null);
			}

			IngresoSistemaDelegate.restablecerPassword(getUsuarioSesion().getId(), this.password, null, getLocale());

			this.setForzarRedireccionamientoIndex(true);

			if (sesionBean.getEntidadUsuario() != null) {
				this.finalizarConversacion("/persona/informacionPersonal.xhtml",
						MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_CAMBIO_EXITOSO, "recursoId=HojaDeVidaSubMenu");
			} else {
				this.finalizarConversacion(true, MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_CAMBIO_EXITOSO, null);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (e.getMessage().contains(MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA)) {
				try {
					this.sesionBean.setHabilitarRestablecerContrasenia(true);
					this.sesionBean.setSesionValida(false);
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA, null);
				} catch (IOException e1) {
					logger.error("", e);
				}
			}
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		}
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

}
