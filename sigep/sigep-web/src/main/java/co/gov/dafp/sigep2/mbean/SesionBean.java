package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.NonexistentConversationException;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.produces.EntidadProduces;
import co.gov.dafp.sigep2.sistema.validator.WebSiteValidator;
import co.gov.dafp.sigep2.util.StringEncrypt;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@SessionScoped
public class SesionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 3695472896249884567L;

	private TipoDocumentoDTO tipoDocumento;
	private String name;
	private String pass;
	private String ipAddress;
	private String mac;
	private String mailRememberPass;
	private String videoTutorial;

	static int inthabilitarRestablecerContrasenia = 0;

	private Boolean mostrarContrasenia 	= Boolean.FALSE;
	private Boolean redirectIndex 		= Boolean.FALSE;
	private Boolean habilitarRestablecerContrasenia = Boolean.FALSE;
	private EntidadDTO entidadSeleccion;
	private String token = "11111-11111-11111-0000"; // token no existe es solo
														// para validar que el
														// servicio de ack
														// cuando vea este token
														// no realice la
														// consulta de session
														// porque aun no ha
														// enviado crerenciales
	private String urlAck = ConfigurationBundleConstants.getPathServerRest();

	private String strTxtHabeasData;
	@Inject
	private RecaptchaBean recaptchaBean;
	
	/*Variables creadas para la validación del aplicativo de pico y cédula*/
	private String strPicoCedula;
	private Boolean blnHabilitaPicoCedula;
	
	@Inject
	private EntidadProduces entidadProduces;
	
	
	UIOutput uiCaptcha;
	
	public UIOutput getUiCaptcha() {
		return uiCaptcha;
	}

	public void setUiCaptcha(UIOutput uiCaptcha) {
		this.uiCaptcha = uiCaptcha;
	}

	public static int getInthabilitarRestablecerContrasenia() {
		return inthabilitarRestablecerContrasenia;
	}

	public static void setInthabilitarRestablecerContrasenia(int inthabilitarRestablecerContrasenia) {
		SesionBean.inthabilitarRestablecerContrasenia = inthabilitarRestablecerContrasenia;
	}

	public TipoDocumentoDTO getTipoDocumento() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		return (TipoDocumentoDTO) contexto.getSessionMap().get("tipoDocumento");
	}

	public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("tipoDocumento", tipoDocumento);
		this.tipoDocumento = tipoDocumento;
	}

	public Boolean getRedirectIndex() {
		return redirectIndex;
	}

	public void setRedirectIndex(Boolean redirectIndex) {
		this.redirectIndex = redirectIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toUpperCase();
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass.trim();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getMailRememberPass() {
		return mailRememberPass;
	}

	public void setMailRememberPass(String mailRememberPass) {
		this.mailRememberPass = mailRememberPass;
	}

	public String getTicket() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		return (String) contexto.getSessionMap().get("ticket");
	}

	public void setTicket(String ticket) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("ticket", ticket);
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

	public Boolean getHabilitarRestablecerContrasenia() {
		return habilitarRestablecerContrasenia;
	}

	public void setHabilitarRestablecerContrasenia(Boolean habilitarRestablecerContrasenia) {
		this.habilitarRestablecerContrasenia = habilitarRestablecerContrasenia;
	}

	public void cambiarMostrarContrasenia() {
		this.mostrarContrasenia = !mostrarContrasenia;
	}

	/**
	 * Valida la autenticidad del ticket generado para acceso a SIGEP II desde
	 * portal Liferay
	 */
	public void loginExterno() {
		UsuarioPasswordDTO usuario = null;
		logger.info("", StringEncrypt.encrypt(String.valueOf(Calendar.getInstance().getTimeInMillis())));
		try {
			if (this.getSesionValida()) {
				this.setForzarRedireccionamientoIndex(true);
				finalizarConversacion(true, null, null);
				return;
			}
			if (this.getTicket() == null || this.getTicket().isEmpty()) {
				String mensajeValidacion = null;
				if (getTicket() != null && !this.getTicket().isEmpty()) {
					mensajeValidacion = MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO_LOGIN_EXTERNO;
				} else {
					this.logout();
				}
				finalizarConversacion(true, mensajeValidacion, null);
				return;
			}
			if (!getTicket().isEmpty()) {
				String ticketTemp = this.getTicket();
				ticketTemp = ticketTemp.replace(" ", "+");
				usuario = IngresoSistemaDelegate.getUsuarioTicket(ticketTemp);
				if (usuario != null) {
					this.logout();
					IngresoSistemaDelegate.validarTicket(usuario.getId(), StringEncrypt.decrypt(ticketTemp),
							getLocale());
					UsuarioDTO usuarioAutenticado = IngresoSistemaDelegate.getUsuario(usuario.getId());
					this.tipoDocumento = AdministracionDelegate
							.findTipoDocumentoId(usuarioAutenticado.getCodTipoIdentificacion());
					this.name = usuario.getNombreUsuario();
					this.pass = StringEncrypt.decrypt(usuario.getContrasena());
					login();
				} else {
					throw new SIGEP2SistemaException(MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO_LOGIN_EXTERNO);
				}
			} else {
				throw new SIGEP2SistemaException(MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO_LOGIN_EXTERNO);
			}
			this.setForzarRedireccionamientoIndex(true);
			finalizarConversacion(true, null, null);
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		} finally {
			this.habilitarRestablecerContrasenia = false;
			this.setTicket(null);
			if (usuario != null) {
				usuario.setTicket(null);
				try {
					IngresoSistemaDelegate.anularTicket(usuario, getUsuarioSesion());
				} catch (SIGEP2SistemaException e) {
					logger.error("void loginExterno()", e);
				}
			}
		}
	}
	
	/**
	 * Metodo que valida si la persona se encuentra dentro de la lista de administradores con permiso 
	 * al sistema sin restricción al pico y cedula., si existe, retorna true para dar a entender de que tiene pico y cedula
	 * */
	public boolean validarPicoCedula(Long tipoDocumento, String cedula) {
		boolean blnExiste = false;
		/*Valida que la cedula exista */
		UsuarioExt objUsuario = new UsuarioExt();
		objUsuario.setCodTipoIdentificacionPersona(new BigDecimal(tipoDocumento));
		objUsuario.setNumeroIdentificacionPersona(cedula);
		blnExiste = ComunicacionServiciosAdmin.getUsuariosAdmin(objUsuario);	
		/*Si la cédula no existe, entonces debe de validar por el digito*/
		if(!blnExiste) {
			LocalDate date = LocalDate.now();
			int dia = date.getDayOfMonth(); /**el día será la posición de la matriz creada para la validación, por tanto, es necesario realizar la resta (-1)*/
			int  digito = Integer.parseInt(cedula.substring(cedula.length() - 1));    
			if(validarPicoCedulaDigitos(dia, digito )) {
				blnExiste = true;
			}
		}
		return blnExiste;
	}
	
	/**
	 * Metodo que se encarga de realizar la validación del pico y cédula; para esto, utiliza 2 variables:
	 * (1) variable 'día' -> esta variable corresponde a día de la semana actual
	 * (2) variable 'digito' -> esta variable corresponde al último digito de la cédula de la persona
	 * De esta manera, tanto el día como el digito deben de ser pares para que permita el ingreso a la plataforma.
	 * */
	public boolean validarPicoCedulaDigitos(int dia, int digito) {
		boolean blnPermiso = false;
		if((dia % 2 == 0 && digito % 2 == 0) || (dia % 2 != 0 && digito % 2 != 0)) {
			blnPermiso = true;
		}
		return blnPermiso;
	}

	public String login() {
		String captureCaptcha="";
		if(uiCaptcha.getValue()!=null) 
			captureCaptcha = uiCaptcha.getValue().toString();
		if(!recaptchaBean.verificarRecaptcha(captureCaptcha)) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ERROR_VERIFICACION_CAPTCHA);
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			return "";
		}
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest httpRequest = (HttpServletRequest) externalContext.getRequest();
		
		/**
		 * Se procede con la invalidacion de cualquier sesion activa y se
		 * procede con la construccion de una nueva de ser necesario
		 */
		try {
			httpRequest.getSession().invalidate();
		} catch (Exception e) {
			logger.error("String login()", e);
		} finally {
			httpRequest.getSession(true);
		}
		this.sesionValida 			= false;
		this.mostrarContrasenia 	= false;
		EntidadDTO entidadUsuario	= null;
		UsuarioDTO usuarioSesion 	= new UsuarioDTO();
		inicializarVariablesPicoCedula();
		
		if (blnHabilitaPicoCedula) {
			if(!validarPicoCedula(tipoDocumento.getId(), name.toUpperCase())) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PICO_CEDULA);
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				return "";
			}
		}
		
		try {
			this.mac 		= httpRequest.getRemoteHost();
			this.ipAddress 	= httpRequest.getRemoteAddr();
			usuarioSesion 	= IngresoSistemaDelegate.login(tipoDocumento.getId(), name.toUpperCase(), pass.trim(), mac,ipAddress);
			externalContext.getSessionMap().put("sesionValida", usuarioSesion == null ? false : true);
			externalContext.getSessionMap().put("inicioSesionLoggin","1");
			this.setUsuarioSesion(usuarioSesion);
			List<EntidadDTO> entidadesUsuario = entidadProduces.getEntidadesUsuario();
			if (entidadesUsuario.size() == 1) {
				entidadUsuario = entidadesUsuario.get(0);
				this.setEntidadUsuario(entidadUsuario);
				token = ComunicacionServiciosSis.registrarToken(usuarioSesion.getId(), entidadesUsuario.get(0).getId(),usuarioSesion.getCodRol());
				if (token == null || token.isEmpty()) {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_PAGE_EXCEPTION_ERROR_SUBJECT1);
					FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
					return "error";
				}
			} else if (entidadesUsuario.size() > 1) {
				token = ComunicacionServiciosSis.registrarToken(usuarioSesion.getId(), entidadesUsuario.get(0).getId(),usuarioSesion.getCodRol());
				if (token == null || token.isEmpty()) {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_PAGE_EXCEPTION_ERROR_SUBJECT1);
					FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
					return "error";
				}
				externalContext.getSessionMap().put("entidadesUsuario",entidadesUsuario);				
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_ENTIDAD_NO_ROL);
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			}

			externalContext.getSessionMap().put("token", token);
			if (usuarioSesion != null) {
				if (usuarioSesion.isFlgAceptoHabeasData()) {
					return "index";
				}
			}

		} catch (Exception e) {
			if (e.getMessage().contains(MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA)) {
				this.habilitarRestablecerContrasenia = true;
				this.pass = null;
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						e.getMessage());
				return "index";
			}
			this.logout();
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		}
		return "index";
	}
	
	
	
	public String generarHabeasData() {
		/**
		 * Texto Habeas Data
		 */
		try {
			Parametrica paraHabeas = ComunicacionServiciosSis
					.getParametricaporId(BigDecimal.valueOf(TipoParametro.HABEAS_DATA_TXT.getValue()));
			if (paraHabeas != null && paraHabeas.getValorParametro() != null)
				strTxtHabeasData = paraHabeas.getValorParametro();
		} catch (Exception z) {
			strTxtHabeasData = "<h2 style='text-align:center;color:#235379;font-size: 28px;margin-top: 10px;'>ESTIMADO USUARIO</h2><br><p style='text-align:justify;"
					+ "color:#235379;font-size: 10.5px;'>Se solicita su autorizaci\u00f3n para que de manera libre, previa y expresa, permita a la entidad p\u00fablica "
					+ "respectiva, el recaudo, almacenamiento y disposici\u00f3n de los datos personales incorporados en el Sistema de Informaci\u00f3n y Gesti\u00f3n "
					+ "del Empleo P\u00fablico \u2013 SIGEPII, en cualquier medio, para el debido cumplimiento de los fines y prop\u00f3sitos de la aplicaci\u00f3n de "
					+ "las normas del servidor p\u00fablico.</br></br>Debe tenerse en cuenta la obligaci\u00f3n que le asiste a los servidores p\u00fablicos y "
					+ "contratistas de ingresar la informaci\u00f3n de su hoja de vida en el momento de su incorporaci\u00f3n al servicio p\u00fablico o "
					+ "celebraci\u00f3n de contrato de prestaci\u00f3n de servicios, y para todos los servidores p\u00fablicos adem\u00e1s, el diligenciamiento del "
					+ "formulario de declaraci\u00f3n de bienes y rentas seg\u00fan lo establecido en las normas vigentes<sup>1</sup>.</br></br>La incorporaci\u00f3n "
					+ "y actualizaci\u00f3n permanentemente de la informaci\u00f3n en el SIGEP, es fundamental para el cumplimiento de las obligaciones contenidas en "
					+ "la Ley 1712 de 2014, Ley de Transparencia y del Derecho de Acceso a la Informaci\u00f3n P\u00fablica Nacional, mediante la cual se exige a las "
					+ "entidades p\u00fablicas divulgar la informaci\u00f3n institucional del personal al servicio del Estado.</br></br>Los datos personales que "
					+ "usted suministrar\u00e1 al sistema, ser\u00e1n administrados por las entidades p\u00fablicas respectivas, con el direccionamiento t\u00e9cnico "
					+ "y operativo del DAFP, y su confidencialidad y seguridad estar\u00e1n garantizadas de conformidad con las disposiciones legales<sup>2</sup> "
					+ "que regulan la protecci\u00f3n de datos personales y con la pol\u00edtica de privacidad para el tratamiento de dichos datos establecida "
					+ "en el DAFP, la cual podr\u00e1 ser consultada en <a href='http://portal.dafp.gov.co' target='_blank'>http://portal.dafp.gov.co</a> o en "
					+ "<a href='http://www.sigep.gov.co' target='_blank'>http://www.sigep.gov.co</a>.</br></br>Finalmente se recuerda al servidor p\u00fablico y "
					+ "contratista que el usuario y contrase\u00f1a es personal, y por tanto su uso ser\u00e1 de su exclusiva responsabilidad; en consecuencia, "
					+ "se presume que el contenido de la informaci\u00f3n y archivos ingresados es veraz, oportuno y completo, de acuerdo con los principios que rigen "
					+ "la funci\u00f3n p\u00fablica.<br /><br /><br /></p><p style='text-align:center;font-weight:bold;color:#235379;font-size: 10.5px;'>"
					+ "PARA TODOS LOS EFECTOS LEGALES, CERTIFICO QUE LOS DATOS POR MI ANOTADOS EN EL PRESENTE FORMATO \u00daNICO DE HOJA DE VIDA, SON VERACES, "
					+ "(ART\u00cdCULO 5\u00b0 DE LA LEY 190 DE 1995).</p><br /><br /><p style='text-align:center;color:#235379'><sup>1</sup>Decreto Ley 019 de 2012 y "
					+ "el Decreto 2842 de 2010, Art\u00edculo 122 de la Constituci\u00f3n Pol\u00edtica, la Ley 190 de 1995 y los Decretos 2204 y 736 de 1996. "
					+ "</br><sup>2</sup>Ley 1581 de 2012 y Decreto Reglamentario 1377 de 2013.</p>";
		}
		return strTxtHabeasData;
	}

	public void restablecerPassword() {
		try {
			String msg = IngresoSistemaDelegate.solicitarRestablecerPassword(this.tipoDocumento.getId(),
					this.name.toUpperCase(), getLocale());
			String urlPortal = getUrlPortal();
			if (urlPortal != null && !urlPortal.isEmpty()) {
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				contexto.getSessionMap().put("solicitarRestablecerPassword", msg);
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, msg);
			}
			this.retrieve();
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		}
	}

	public void validarEstadoSesionActive() {
		logout(true);
	}

	public void validarEstadoSesionIdle() {
		logout(false);
	}

	/**
	 * Apoya la validación de cierre de sesion automática por timeout de request
	 * constante desde el cliente.
	 * 
	 * @param redireccionar
	 */
	private void logout(boolean redireccionar) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		Long validarEstadoSesion = (Long) contexto.getSessionMap().get("validarEstadoSesion");
		if (validarEstadoSesion == null) {
			validarEstadoSesion = 0l;
		} else {
			Long validarEstadoSesionTemp = validarEstadoSesion;
			validarEstadoSesion = validarEstadoSesion + 2;
			if ((validarEstadoSesionTemp - 2) != validarEstadoSesion) {
				if (redireccionar) {
					this.logout();
				} else {
					FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				}
			}
		}
		contexto.getSessionMap().put("validarEstadoSesion", validarEstadoSesion);
	}

	public boolean logout() {
		if (!this.getSesionValida()) {
			try {
				if (getTicket() == null || getTicket().isEmpty()) {
					finalizarConversacion(true, null, null);
				}
			} catch (Exception e) {
				return false;
			}
			return false;
		}
		String msg = "boolean logout()";
		Cookie cookie = getCookie("isPageUnloadHandled");
		boolean continuarCierreSesion = true;
		if (cookie != null) {
			// continuarCierreSesion =
			// Boolean.TRUE.toString().equals(cookie.getValue());
		}
		if (continuarCierreSesion || cookie == null) {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			String localeCode = (String) contexto.getSessionMap().get("localeCode");

			if (localeCode == null) {
				Cookie localeCodecookie = getCookie("localeCode");
				if (localeCodecookie != null) {
					localeCode = localeCodecookie.getValue();
				}
			}

			if (localeCode == null || localeCode.isEmpty() || "null".equals(localeCode)) {
				localeCode = Locale.ROOT.getLanguage();
			}

			this.name = null;
			this.pass = null;
			this.tipoDocumento = null;
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().remove("sesionBean");
			try {
				String token = (String) externalContext.getSessionMap().get("token");
				ComunicacionServiciosSis.expiracionToken(token, getUsuarioSesion().getId());
			} catch (Exception e) {
				logger.error(msg, e);
				return false;
			}
			try {
				externalContext.getSessionMap().remove("token");
			} catch (Exception e) {
				logger.error(msg, e);
				return false;
			}

			Object session = externalContext.getSession(false);
			HttpSession httpSession = (HttpSession) session;
			try {
				Enumeration<String> atributos = ((HttpServletRequest) externalContext.getRequest()).getAttributeNames();
				while (atributos.hasMoreElements()) {
					Object atributo = atributos.nextElement();
					if (atributo instanceof Conversation) {
						logger.info(msg, ((Conversation) atributo).getId());
					}
				}
				httpSession.invalidate();
				if (cookie != null) {
					cookie.setValue(Boolean.FALSE.toString());
					cookie.setMaxAge(getVigenciaCookie(1, 0, 0, 0));
					refrescarCookie(cookie);
				}
			} catch (Exception e) {
				logger.error(msg, e);
				return false;
			}
			try {
				context.getExternalContext().invalidateSession();
			} catch (NonexistentConversationException e) {
				logger.error(msg, e);
			}
			externalContext.getSession(true);
			try {
				this.sesionValida = false;
				this.setSesionValida(false);
				this.finalizarConversacion(true, null, "l=" + localeCode);
			} catch (IOException e) {
				logger.error(msg, e);
				return false;
			}
			contexto.getSessionMap().put("localeCode", localeCode);
		}
		return this.sesionValida;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void setEntidad() {
		if (this.entidadSeleccion != null) {
			this.setEntidadUsuario(this.getEntidadSeleccion());
		}
	}

	public void valueChangeEntidad(ValueChangeEvent e) {
		if (e != null) {
			this.setEntidadUsuario((EntidadDTO) e.getNewValue());
		}
	}

	public void aceptarHabeasData() throws SIGEP2SistemaException {
		if (!getUsuarioSesion().isFlgAceptoHabeasData()) {
			if (this.getEntidadUsuario() == null)
				this.setEntidadUsuario(null);
			this.getUsuarioSesion().setFlgAceptoHabeasData(true);
			this.getUsuarioSesion().setFechaAceptoHabeas(new Date());
			IngresoSistemaDelegate.aceptaHabeasData(getUsuarioSesion());
		}

	}

	public void rechazarHabeasData() {
		if (this.getEntidadUsuario() == null)
			this.setEntidadUsuario(null);
		getUsuarioSesion().setFlgAceptoHabeasData(true);
	}

	public void habilitarRestablecerContrasenia() {
		this.mostrarContrasenia = false;
		this.pass = null;
		this.habilitarRestablecerContrasenia = !this.habilitarRestablecerContrasenia;
	}

	@Override
	public void init() {
		this.sesionValida = false;
		this.mostrarContrasenia = false;
		this.name = null;
		this.pass = null;
		this.habilitarRestablecerContrasenia = false;
	}
	
	
	/**
	 * Inicialización y variables de pico y cédula
	 * Si en el archvivo de configuración existe la variable
	 * HABILITAR_PICO_CEDULA =S, entonces se debe de habilitar la opción de pico y cédula
	 * */
	public void inicializarVariablesPicoCedula() {
		this.strPicoCedula =ConfigurationBundleConstants.getString(ConfigurationBundleConstants.HABILITAR_PICO_CEDULA);
		this.blnHabilitaPicoCedula = false;
		if(strPicoCedula!= null && !strPicoCedula.equals("")){
			if("S".equals(strPicoCedula)) {
				this.blnHabilitaPicoCedula = true;
			}
		}
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		this.name = null;
		this.pass = null;
		this.sesionValida = false;
		this.setTipoDocumento(null);
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Named
	@Produces
	public Long getTimeOutIdle() {
		return timeOutIdle;
	}

	@Named
	@Produces
	public static boolean getRecaptcha() {
		return ConfigurationBundleConstants.getRecaptcha();
	}

	@Named
	@Produces
	public static boolean isRenderMenu() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		if (usuarioSesion != null) {
			return ConfigurationBundleConstants.renderMenu()
					|| UsuarioDTO.USUARIO_SUPER_ADMINISTRADOR.equals(usuarioSesion.getNombreUsuario());
		} else {
			return ConfigurationBundleConstants.renderMenu();
		}
	}

	@Named
	@Produces
	public static long getTimeout() {
		return ConfigurationBundleConstants.getTimeOutConversation();
	}

	@Named
	@Produces
	public static String getPaginatorsSize() {
		return ConfigurationBundleConstants.getpaginatorsSize();
	}

	@Named
	@Produces
	public static int getPaginatorSize() {
		return ConfigurationBundleConstants.getpaginatorSize();
	}

	public EntidadDTO getEntidadSeleccion() {
		return entidadSeleccion;
	}

	public void setEntidadSeleccion(EntidadDTO entidadSeleccion) {
		this.entidadSeleccion = entidadSeleccion;
	}

	public void limpiarDoc() throws IOException {
		this.name = null;
		this.pass = null;
		if (SesionBean.inthabilitarRestablecerContrasenia == 0) {
			this.redireccionarIndex();
		}
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the urlAck
	 */
	public String getUrlAck() {
		return urlAck;
	}

	/**
	 * @param urlAck
	 *            the urlAck to set
	 */
	public void setUrlAck(String urlAck) {
		this.urlAck = urlAck;
	}

	public String getStrTxtHabeasData() {
		return strTxtHabeasData;
	}

	public void setStrTxtHabeasData(String strTxtHabeasData) {
		this.strTxtHabeasData = strTxtHabeasData;
	}
	
	public void setCambioEntidadSideBar() {
		try {
			redireccionarIndex();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * Devuelve la URL del portal de sigep ii (liferay)
	 * 
	 * @return String
	 */
	@Named
	@Produces
	public String getUrlPortal() {
		if (getRedireccionarUrlPortal()) {
			Parametrica urlPortal = getParametroSesion(ConfigurationBundleConstants.CNS_URL_PORTAL);
			if (urlPortal != null && urlPortal.getValorParametro() != null
					&& !urlPortal.getValorParametro().isEmpty()) {
				try {
					WebSiteValidator validadorWebURL = new WebSiteValidator();
					validadorWebURL.validate(FacesContext.getCurrentInstance(), null, urlPortal.getValorParametro());
				} catch (ValidatorException e) {
					logger.error("String getUrlPortal()",
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_URL_INVALID, getLocale())
									+ ": '" + urlPortal.getValorParametro() + "'");
				}
				return urlPortal.getValorParametro();
			}
		}
		return null;
	}

	/**
	 * Indica si en caso de que la sesión esté vencida, se redireccione al
	 * portal liferay
	 */
	@Named
	@Produces
	public Boolean getRedireccionarUrlPortal() {
		Parametrica redireccionarUrlPortal = getParametroSesion(
				ConfigurationBundleConstants.CNS_SESION_REDIRECCIONAR_URL_PORTAL);
		if (redireccionarUrlPortal != null && redireccionarUrlPortal.getValorParametro() != null
				&& "1".equals(redireccionarUrlPortal.getValorParametro())) {
			return true;
		}
		return false;
	}

	/*
	 * Metodo para redireccionar al index de la aplicacion Se crea este metodo
	 * porque el que esta en el BaseBean no aplicaba para el caso especifico de
	 * reestablecer contrasena
	 */

	public void redireccionarIndex() throws IOException {
		try {
			conversation.end();
		} catch (Exception e) {
		}
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String path = "/index.xhtml";
		String pathInfo = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
		String mensaje = "";
		String parametros = "?faces-redirect=true";
		boolean redireccionar = false;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		String pathInfoAnterior = (String) contexto.getSessionMap().get("pathInfoAnterior");
		if (!pathInfo.equals(pathInfoAnterior)) {
			redireccionar = true;
			if (pathInfoAnterior != null) {
				contexto.getSessionMap().put("pathInfoAnterior", pathInfoAnterior);
			} else {
				contexto.getSessionMap().put("pathInfoAnterior", pathInfo);
			}
		}
		if (redireccionar) {
			context.redirect(context.getRequestContextPath() + "/" + ConfigurationBundleConstants.aliasSitio() + path
					+ parametros + mensaje);
		}
	}

	public void abrirVideoTutorial(){
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		String recursoVideo = (String) contexto.getSessionMap().get(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION);
		Parametrica parametrica=null;
		if (recursoVideo==null || "".equals(recursoVideo))/*las opciones sin inicializar como loggin se asumen genericas*/
				recursoVideo=ConfigurationBundleConstants.OPT_VIDEO_INGRESOSISTEMA;
		if(recursoVideo!=null && !"".equals(recursoVideo)){
			switch(recursoVideo) {
				case ConfigurationBundleConstants.OPT_VIDEO_INGRESOSISTEMA:
					parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_HOJA_VIDA.getValue()));
				    videoTutorial = parametrica.getValorParametro();
				    break;
				case ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION:
					parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_ADMINSITRACION.getValue()));
					videoTutorial = parametrica.getValorParametro();
					break;
				case ConfigurationBundleConstants.OPT_VIDEO_HOJAVIDA:
					parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_HOJA_VIDA.getValue()));
					videoTutorial = parametrica.getValorParametro();
					break;
				case ConfigurationBundleConstants.OPT_VIDEO_BYR:
					parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_BIENESYRENTAS.getValue()));
					videoTutorial = parametrica.getValorParametro();
					break;					
				case ConfigurationBundleConstants.OPT_VIDEO_CONTRATOS:
					parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_CONTRATOS.getValue()));
					videoTutorial = parametrica.getValorParametro();
					break;
				case ConfigurationBundleConstants.OPT_VIDEO_VINCULACIONES:
					parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_VINCULACIONES.getValue()));
					videoTutorial = parametrica.getValorParametro();
					break;
				case ConfigurationBundleConstants.OPT_VIDEO_SITUACIONESADMINISTRATIVAS:
					parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_SITUACIONESADMINISTRTAIVAS.getValue()));
					videoTutorial = parametrica.getValorParametro();
					break;
				case ConfigurationBundleConstants.OPT_VIDEO_CARGUESMASIVOS:
					parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_CARGUESMASIVOS.getValue()));
					videoTutorial = parametrica.getValorParametro();
					break;	
			    case ConfigurationBundleConstants.OPT_VIDEO_GESTIONINFORMACION:
			    	parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_GESTIONINFORMACION.getValue()));
				    videoTutorial = parametrica.getValorParametro();
				    break;					
			    case ConfigurationBundleConstants.OPT_VIDEO_ENTIDADES:
			    	parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.VIDEO_TUTORIAL_CREARENTIDAD.getValue()));
			    	videoTutorial = parametrica.getValorParametro();
			    	break;	
			}
		}
	}
	
	public String getVideoTutorial() {
		return videoTutorial;
	}

	public void setVideoTutorial(String videoTutorial) {
		this.videoTutorial = videoTutorial;
	}

	public String getStrPicoCedula() {
		return strPicoCedula;
	}

	public void setStrPicoCedula(String strPicoCedula) {
		this.strPicoCedula = strPicoCedula;
	}

	public Boolean getBlnHabilitaPicoCedula() {
		return blnHabilitaPicoCedula;
	}

	public void setBlnHabilitaPicoCedula(Boolean blnHabilitaPicoCedula) {
		this.blnHabilitaPicoCedula = blnHabilitaPicoCedula;
	}
}
