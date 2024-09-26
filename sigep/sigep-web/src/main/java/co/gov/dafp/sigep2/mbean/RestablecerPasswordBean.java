package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.StringEncrypt;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Named
@ConversationScoped
public class RestablecerPasswordBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -1199368366370427715L;
	protected static Logger logger = Logger.getInstance(RestablecerPasswordBean.class);

	private String tipoDocumento;

	private String login;

	private String ticket;

	private boolean ticketValido = false;

	private String password;

	private Boolean passwordActualizado = Boolean.FALSE;

	@Inject
	private SesionBean sesionBean;

	public String getNombreUsuario() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTicket() {
		return ticket;
	}

	public String getTicketCifrado() {
		try {
			return StringEncrypt.decrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(), ticket);
		} catch (Exception e) {
			logger.error(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO,
							getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO,
							getLocale()));

			return null;
		}
	}

	public void setTicket(String ticketP) {
		try {
			String ticket = ticketP.replace(" ", "+");
			this.ticket = StringEncrypt.decrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(),
					ticket);
		} catch (Exception e) {
			logger.error(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO,
							getLocale()),
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO,
							getLocale()));

			this.ticket = null;
		}
	}

	public boolean isTicketValido() {
		try {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			this.ticketValido = (Boolean) contexto.getSessionMap().get("ticketValido");
		} catch (Exception e) {
			this.ticketValido = false;
		}
		return ticketValido;
	}

	public void setTicketValido(boolean ticketValido) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("ticketValido", ticketValido);
		this.ticketValido = ticketValido;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getPasswordActualizado() {
		return passwordActualizado;
	}

	public void setPasswordActualizado(Boolean passwordActualizado) {
		this.passwordActualizado = passwordActualizado;
	}

	@Override
	public void init() throws NotSupportedException {
		String mensajeControl = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE, getLocale());
		this.sesionBean.setEntidadUsuario(new EntidadDTO());
		this.sesionBean.setUsuarioSesion(new UsuarioDTO());
		this.sesionBean.getUsuarioSesion().setFlgAceptoHabeasData(true);

		if (this.ticket == null) {

			mensajeControl = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO, getLocale());
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, mensajeControl);

			return;
		}

		try {
			String ticket = StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(),
					this.ticket);
			UsuarioPasswordDTO usuario = IngresoSistemaDelegate.getUsuarioTicket(ticket);
			if (usuario != null) {
				try {
					IngresoSistemaDelegate.validarTicket(usuario.getId(), this.ticket, getLocale());
					UsuarioDTO usuarioSesion = IngresoSistemaDelegate.getUsuario(usuario.getId());
					this.setUsuarioSesion(usuarioSesion);
					this.sesionBean.getUsuarioSesion().setFlgAceptoHabeasData(true);
					this.login = usuario.getNombreUsuario();
					String ticketTemp = StringEncrypt.decrypt(ConfigurationBundleConstants.key(),
							ConfigurationBundleConstants.iv(), usuario.getTicket());
					passwordActualizado = Boolean.FALSE;
					if (!ticketTemp.equals(this.ticket)) {

						mensajeControl = MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO, getLocale());
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								mensajeControl);

						return;
					}
					this.setTicketValido(true);

					ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
					contexto.getSessionMap().put("sesionValida", true);
				} catch (Exception e) {

					logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
					mensajeControl = MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO;
					if (usuario.getTicket() == null) {
						mensajeControl = MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_AUTORIZADA_CAMBIO_PASS;
					}

					throw new SIGEP2SistemaException(mensajeControl);
				}
			} else {
				this.sesionBean.setHabilitarRestablecerContrasenia(false);
				this.sesionBean.setSesionValida(false);
				this.setTicketValido(false);
				this.sesionBean.logout();
				this.sesionBean.setRedirectIndex(true);

				finalizarConversacion("/index.xhtml", MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO, "r=true");

			}
		} catch (Exception e) {
			logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
			this.sesionBean.setSesionValida(false);
			this.sesionBean.setHabilitarRestablecerContrasenia(true);
			this.sesionBean.setEntidadUsuario(null);
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
			try {
				this.finalizarConversacion(true, e.getMessage(), null);
			} catch (IOException e1) {
				logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e1);
			}
		}
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		String mensajeControl = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE, getLocale());

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		this.sesionValida = this.getSesionValida();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		if (!this.sesionValida) {
			if (this.ticket == null) {
				try {
					this.ticket = StringEncrypt.decrypt(ConfigurationBundleConstants.key(),
							ConfigurationBundleConstants.iv(), (String) contexto.getSessionMap().get("ticket"));
				} catch (SIGEP2SistemaException e) {
					this.ticket = (String) contexto.getSessionMap().get("ticket");
				}
			}
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			Object session = externalContext.getSession(false);
			HttpSession httpSession = (HttpSession) session;
			try {
				httpSession.invalidate();
			} catch (Exception e) {
				logger.error("String logout(String recurso, String recursoId)", e);
			}
			contexto.getSessionMap().put("ticket", StringEncrypt.encrypt(ConfigurationBundleConstants.key(),
					ConfigurationBundleConstants.iv(), (String) contexto.getSessionMap().get("ticket")));
		}

		try {
			if (this.ticket == null) {
				try {
					this.ticket = StringEncrypt.decrypt(ConfigurationBundleConstants.key(),
							ConfigurationBundleConstants.iv(), (String) contexto.getSessionMap().get("ticket"));
				} catch (SIGEP2SistemaException e) {
					this.ticket = (String) contexto.getSessionMap().get("ticket");
				}
			}
			String ticket = StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(),
					this.ticket);
			UsuarioPasswordDTO usuario = IngresoSistemaDelegate.getUsuarioTicket(ticket);
			if (usuario != null) {
				try {
					IngresoSistemaDelegate.validarTicket(usuario.getId(), this.ticket, getLocale());
					UsuarioDTO usuarioSesion = IngresoSistemaDelegate.getUsuario(usuario.getId());
					this.setUsuarioSesion(usuarioSesion);
					this.sesionBean.getUsuarioSesion().setFlgAceptoHabeasData(true);
					this.login = usuario.getNombreUsuario();
					String ticketTemp = StringEncrypt.decrypt(ConfigurationBundleConstants.key(),
							ConfigurationBundleConstants.iv(), usuario.getTicket());
					passwordActualizado = Boolean.FALSE;
					if (!ticketTemp.equals(this.ticket)) {

						mensajeControl = MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO, getLocale());
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								mensajeControl);

						return;
					}
					this.setTicketValido(true);

					contexto.getSessionMap().put("sesionValida", true);
					contexto.getSessionMap().put("ticket", ticket);
				} catch (Exception e) {

					logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
					mensajeControl = MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO;
					if (usuario.getTicket() == null) {
						mensajeControl = MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_AUTORIZADA_CAMBIO_PASS;
					}

					throw new SIGEP2SistemaException(mensajeControl);
				}
			} else {

				this.sesionBean.setHabilitarRestablecerContrasenia(false);
				throw new SIGEP2SistemaException(MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO);

			}
		} catch (Exception e) {
			logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
			this.sesionBean.setSesionValida(false);
			this.sesionBean.setHabilitarRestablecerContrasenia(true);
			this.sesionBean.setEntidadUsuario(null);
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
			try {
				this.finalizarConversacion(true, e.getMessage(), null);
			} catch (IOException e1) {
				logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e1);
			}
		}

		if (this.sesionValida) {
			try {
				finalizarConversacion(true, MessagesBundleConstants.MSG_CUENTA_USUARIO_SESION_ACTIVA_CAMBIO_PASS, null);
			} catch (IOException e) {
				logger.error("void retrieve()", e);
			}
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

		passwordActualizado = Boolean.TRUE;
		this.sesionBean.setSesionValida(Boolean.TRUE);

		this.init();
	}

	@Override
	public String update() {
		try {
			String msg = "update()";
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			if (this.ticket == null) {
				try {
					this.ticket = StringEncrypt.decrypt(ConfigurationBundleConstants.key(),
							ConfigurationBundleConstants.iv(), (String) contexto.getSessionMap().get("ticket"));
				} catch (SIGEP2SistemaException e) {
					this.ticket = (String) contexto.getSessionMap().get("ticket");
				}
			}
			logger.log().info(msg, "PRUEBA DAFP: Usuario sesion: " + getUsuarioSesion().getId());
			IngresoSistemaDelegate.restablecerPassword(getUsuarioSesion().getId(), this.password, this.ticket,
					getLocale());
			this.setTicketValido(false);

			TipoDocumentoDTO tipoDoc = AdministracionDelegate
					.findTipoDocumentoId(getUsuarioSesion().getCodTipoIdentificacion());
			this.sesionBean.setName(getUsuarioSesion().getNombreUsuario());
			this.sesionBean.setTipoDocumento(tipoDoc);
			this.sesionBean.setPass(this.password);
			this.sesionBean.login();
			
			this.setForzarRedireccionamientoIndex(true);
			
			if (sesionBean.getEntidadUsuario() != null) {
				this.finalizarConversacion("/persona/informacionPersonal.xhtml",
						MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_CAMBIO_EXITOSO, "recursoId=HojaDeVidaSubMenu");
			} else {
				this.finalizarConversacion(true, MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_CAMBIO_EXITOSO, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		}
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {

	}

	public void limpiarCampos() {
		this.password = "";
	}

	/**
	 * Metodo para hacer back hacia la pÃ¡gina <b>index.xhtml</b>
	 * @throws IOException 
	 */
	
	public void cancelarReestablecer() throws IOException {
		this.sesionBean.redireccionarIndex();
	}

}
