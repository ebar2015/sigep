package co.gov.dafp.sigep2.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Persona;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioPassword;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.ProcesoBackgroundFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioPasswordFactoria;
import co.gov.dafp.sigep2.interfaces.ICambioContraseniaRemote;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.StringEncrypt;
import co.gov.dafp.sigep2.util.StringPassword;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class CambioContraseniaEJB implements ICambioContraseniaRemote {
	private static final long serialVersionUID = 1352471373248439462L;
	transient Logger logger = Logger.getInstance(CambioContraseniaEJB.class);

	private Usuario usuarioSesion;

	@EJB
	private UsuarioFactoria usuarioFactoria;

	@EJB
	private UsuarioPasswordFactoria usuarioPasswordFactoria;

	@EJB
	private ProcesoBackgroundFactoria procesoBackgroundFactoria;

	@EJB
	private IEnvioCorreoLocal mailService;

	@EJB
	private PersonaFactoria personaFactoria;

	private String enviarMailResPassword(Long tipoDocumento, String name, String asunto, String cuerpoParam,
			int diasVigencia, int horasVigencia, Locale locale,String strNotaPrivacidad) throws Exception {
		UsuarioPasswordDTO usuarioSesionPassword = this.usuarioPasswordFactoria.getUsuarioByLoginOrMail(name,
				tipoDocumento);
		if (usuarioSesionPassword != null) {
			if(!usuarioSesionPassword.getFlgEstado()){
				return MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_USUARIO_INACTIVO,locale);
			}
			if ( (usuarioSesionPassword.getCorreoElectronico() != null && !usuarioSesionPassword.getCorreoElectronico().isEmpty())
					||( usuarioSesionPassword.getCorreoAlternativo()!=null && !usuarioSesionPassword.getCorreoAlternativo().isEmpty()) ) {
				long millis = Calendar.getInstance().getTimeInMillis();
				String ticket = StringEncrypt.encrypt(ConfigurationBundleConstants.key(),
						ConfigurationBundleConstants.iv(), String.valueOf(millis));
				UsuarioPassword u = usuarioPasswordFactoria.find(usuarioSesionPassword.getId());
				u.setTicket(ticket);
				u = usuarioPasswordFactoria.merge(u, this.usuarioSesion);

				// String cuerpo =
				// ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_RESTABLECER_PASSWORD_CUERPO)
				String cuerpo = MessagesBundleConstants.getStringMessagesBundle(cuerpoParam.replace("PASS_USUARIO_RESTABLECE", usuarioSesionPassword.getNombrePersona())
						.replace(ConfigurationBundleConstants.PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS,
								ConfigurationBundleConstants.getString(ConfigurationBundleConstants.PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS))
						.replace(ConfigurationBundleConstants.PASS_VALIDATE_MAXIMO,
								ConfigurationBundleConstants.getString(ConfigurationBundleConstants.PASS_VALIDATE_MAXIMO))
						.replace(ConfigurationBundleConstants.PASS_VALIDATE_MINIMO, 
								ConfigurationBundleConstants.getString(ConfigurationBundleConstants.PASS_VALIDATE_MINIMO)), locale);

				String link = this.getUrlRestablecerPassword() + "?s=false&ticket=" + u.getTicket();

				asunto = asunto.replace("ticket".toUpperCase(), u.getTicket());
				cuerpo = cuerpo.replace("ticket".toUpperCase(), u.getTicket());
				cuerpo = cuerpo.replace("link".toUpperCase(), link);

				Calendar horaTicket = Calendar.getInstance();
				horaTicket.setTimeInMillis(millis);

				horaTicket.setTime(DateUtils.sumarDias(horaTicket.getTime(), diasVigencia));
				horaTicket.setTime(DateUtils.sumarHoras(horaTicket.getTime(), horasVigencia));

				cuerpo = cuerpo.replace("vence".toUpperCase(),
						DateUtils.formatearACadena(horaTicket.getTime(), DateUtils.FECHA_HORA_FORMATO));

				List<String> correosA = new LinkedList<>();
				if(usuarioSesionPassword.getCorreoElectronico()!=null && !usuarioSesionPassword.getCorreoElectronico().isEmpty())
					correosA.add(usuarioSesionPassword.getCorreoElectronico());
				if(usuarioSesionPassword.getCorreoAlternativo() != null && !usuarioSesionPassword.getCorreoAlternativo().isEmpty()){
					correosA.add(usuarioSesionPassword.getCorreoAlternativo());
				}
				this.mailService.enviarMail(asunto, cuerpo, ConfigurationBundleConstants.adminCuentaCorreo(), correosA,
						null,usuarioSesionPassword.getId(),false,strNotaPrivacidad);
				logger.info("Link generado para " + usuarioSesionPassword.getNombreUsuario() + " : " + link);

				String confirmacion = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_CONFIRMAR_CAMBIAR_CONTRASENIA_SMS, locale);

				String cuentaNotifica=    usuarioSesionPassword.getCorreoElectronico()!=null 
						               && !usuarioSesionPassword.getCorreoElectronico().isEmpty()?usuarioSesionPassword.getCorreoElectronico()
						            		   :usuarioSesionPassword.getCorreoAlternativo();
				return confirmacion.replace("CUENTA_CORREO",
						StringEncrypt.enmascararCorreoElectronico(cuentaNotifica));
			} else {
				this.mailService.enviarMail(asunto, "", ConfigurationBundleConstants.adminCuentaCorreo(), null,
						null,usuarioSesionPassword.getId(),true,strNotaPrivacidad);				
				
				return MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_USUARIO_SIN_CORREO_SMS,
						locale);
			}
		}
		return MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE,
				locale);
	}

	private String getUrlRestablecerPassword() {
		return ConfigurationBundleConstants.getPrefijoApp()
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.PAGINA_RESTABLECER_PASSWORD);
	}

	@Override
	public String solicitarRestablecerPassword(Long tipoDocumento, String login, String asunto, String cuerpo,
			int diasVigencia, int horasVigencia, Locale locale,String strNotaPrivacidad) throws SIGEP2SistemaException {
		try {
			return this.enviarMailResPassword(tipoDocumento, login, asunto, cuerpo, diasVigencia, horasVigencia,
					locale,strNotaPrivacidad);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	@Override
	public void restablecerPassword(Long usuarioId, String password, String ticket, String asunto, String cuerpoParam,
			int diasVigenciaClave, int diasVigenciaTicket, int horaVigenciaTicket, Locale locale, String strNotaPrivacidad)
			throws SIGEP2SistemaException {
		try {
			UsuarioPassword usuarioAppTemp = usuarioPasswordFactoria.find(usuarioId);
			validarTicket(usuarioId, ticket, diasVigenciaTicket, horaVigenciaTicket, locale);
			List<String> validates = StringPassword.validarPassword(password, password);
			if (!validates.isEmpty()) {
				String mensajes = HTMLUtil.abreListaNoOrdenada;
				for (String validate : validates) {
					mensajes = mensajes + HTMLUtil.abreItem;
					mensajes = mensajes + validate;
					mensajes = mensajes + HTMLUtil.cierraItem;
				}
				mensajes = mensajes + HTMLUtil.cierraListaNoOrdenada;
				lanzarException(mensajes);
			} else {
				Usuario sistema = usuarioFactoria.getUsuarioSistema();
				UsuarioPassword usuarioApp = this.usuarioPasswordFactoria.find(usuarioAppTemp.getId());
				usuarioApp.setPasswd(password);
				usuarioApp.setBloqueado(Boolean.FALSE);
				Date diasVence = null;
				if (diasVigenciaClave > 0) {
					diasVence = DateUtils.sumarDias(DateUtils.getFechaSistema(), diasVigenciaClave);
				}
				usuarioApp.setFechaVence(diasVence);
				usuarioApp.setTicket(null);
				this.usuarioPasswordFactoria.merge(usuarioApp, sistema);
				this.usuarioFactoria.desbloquearUsuario(usuarioId);
				String restablecerLink = ConfigurationBundleConstants.getPrefijoApp() + "index.xhtml?login="
						+ usuarioApp.getNombreUsuario();
				restablecerLink = "<a href='" + restablecerLink + "'>" + TitlesBundleConstants.getStringMessagesBundle(
						TitlesBundleConstants.TTL_LOGIN) + "</a>";
				String cuerpo = cuerpoParam.replace("CUENTA_USUARIO", usuarioApp.getNombreUsuario())
						.replace("CUENTA_ADMON", ConfigurationBundleConstants.adminCuentaCorreo())
						.replace("LINK_RESTABLECER", restablecerLink).replace("LINK_OLVIDO_CONTRASENA", TitlesBundleConstants.getStringMessagesBundle(
								TitlesBundleConstants.TTL_FORGET_PASSWORD))	;
				List<String> correosA = new LinkedList<>();
				if(usuarioApp.getCorreoElectronico()!=null && !usuarioApp.getCorreoElectronico().isEmpty())
					correosA.add(usuarioApp.getCorreoElectronico());
				if(usuarioApp.getCorreoAlternativo() != null && !usuarioApp.getCorreoAlternativo().isEmpty()){
					correosA.add(usuarioApp.getCorreoAlternativo());
				}
				mailService.enviarMail(asunto, cuerpo,
						ConfigurationBundleConstants.getString(ConfigurationBundleConstants.adminCuentaCorreo()),
						correosA, null, strNotaPrivacidad);
			}
		} catch (Exception e) {
			lanzarException(e.getMessage());
		}
	}

	@Override
	public void validarTicket(Long usuarioId, String ticket, int diasVigencia, int horasVigencia, Locale locale)
			throws SIGEP2SistemaException {
		try {
			if (ticket != null) {
				long millis = Long.valueOf(ticket);
				Date horaTicket = new Date(millis);

				horaTicket = DateUtils.sumarDias(horaTicket, diasVigencia);
				horaTicket = DateUtils.sumarHoras(horaTicket, horasVigencia);

				Date ahora = DateUtils.getFechaSistema();
				UsuarioPassword usuarioAppTemp = usuarioPasswordFactoria.find(usuarioId);

				if (ahora.after(horaTicket) || usuarioAppTemp.getTicket() == null) {
					String mensajeControl = MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO_NUEVA_SOLICITUD, locale) + " "
							+ DateUtils.formatearACadena(horaTicket, DateUtils.FECHA_HORA_FORMATO);
					UsuarioPassword usuarioApp = this.usuarioPasswordFactoria.find(usuarioAppTemp.getId());
					usuarioApp.setTicket(null);
					Usuario sistema = usuarioFactoria.getUsuarioSistema();
					this.usuarioPasswordFactoria.merge(usuarioApp, sistema);

					lanzarException(mensajeControl);
				}
			}
		} catch (Exception e) {
			lanzarException(e.getMessage());
		}
	}

	@Override
	public UsuarioPasswordDTO getUsuarioTicket(String ticket) throws SIGEP2SistemaException {
		return this.usuarioPasswordFactoria.getUsuarioByTicket(ticket);
	}

	@Override
	public UsuarioDTO getUsuario(long id) throws SIGEP2SistemaException {
		Usuario usuario = this.usuarioFactoria.find(id);
		if (usuario != null) {
			UsuarioDTO usuarioDTO = (UsuarioDTO) usuario.getDTO();
			Persona persona = personaFactoria.find(usuarioDTO.getCodPersona());
			usuarioDTO.setCodTipoIdentificacion(persona.getCodTipoIdentificacion());
			return usuarioDTO;
		} else {
			return null;
		}
	}

	private void lanzarException(String message) throws SIGEP2SistemaException {
		throw new SIGEP2SistemaException(message);
	}

	@Override
	public UsuarioPasswordDTO getUsuarioByLogin(Long tipoDocumento, String login) throws SIGEP2SistemaException {
		return this.usuarioPasswordFactoria.getUsuarioByLoginOrMail(login, tipoDocumento);
	}

	@Override
	public void anularTicket(UsuarioPasswordDTO usuario, UsuarioDTO usuarioSesion) throws SIGEP2SistemaException {
		Usuario usuarioAud = this.usuarioFactoria.find(usuarioSesion.getId());
		Usuario usuarioAnular = this.usuarioFactoria.find(usuario.getId());
		if (usuarioAnular != null) {
			usuarioAnular.setTicket(null);
			usuarioFactoria.merge(usuarioAnular, usuarioAud);
		}
	}

}
