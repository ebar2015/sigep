package co.gov.dafp.sigep2.sistema;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.factoria.MailFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.elemento.CuentaCorreo;

public class HMail extends Thread {
	private Logger logger = Logger.getInstance(HMail.class);

	private Session mailSession;
	private UsuarioFactoria usuarioFactoria;
	private MailFactoria mailDao;
	private List<String> adjuntos;
	private String asunto;
	private Object desde;
	private String cuerpo;
	private List<?> aList;
	private List<?> ccList;
	private Date fechaEnvio;
	private static Long llCodUsuario; 
	private boolean lbRecuperaDosfases;
	private boolean lbEnviarSoloSMS;
	private String lsNotaPrivacidadEmail;

	public HMail(UsuarioFactoria usuarioDao, MailFactoria mailDao, Session mailSession, List<String> adjuntos,
			String asunto, Object desde, String cuerpo, List<?> aList, List<?> ccList) {
		this.usuarioFactoria = usuarioDao;
		this.mailDao = mailDao;
		this.mailSession = mailSession;
		this.adjuntos = adjuntos;
		this.asunto = asunto;
		this.desde = desde;
		this.cuerpo = cuerpo;
		this.aList = aList;
		this.ccList = ccList;
		this.fechaEnvio = DateUtils.getFechaSistema();
		lbRecuperaDosfases = false;
	}
	
	
	public HMail(UsuarioFactoria usuarioDao, MailFactoria mailDao, Session mailSession, List<String> adjuntos,
			String asunto, Object desde, String cuerpo, List<?> aList, List<?> ccList, String strNotaPrivacidad) {
		this.usuarioFactoria = usuarioDao;
		this.mailDao = mailDao;
		this.mailSession = mailSession;
		this.adjuntos = adjuntos;
		this.asunto = asunto;
		this.desde = desde;
		this.cuerpo = cuerpo;
		this.aList = aList;
		this.ccList = ccList;
		this.fechaEnvio = DateUtils.getFechaSistema();
		lbRecuperaDosfases = false;
		this.lsNotaPrivacidadEmail=strNotaPrivacidad;
	}

	public HMail(UsuarioFactoria usuarioDao, MailFactoria mailDao, Session mailSession, List<String> adjuntos,
			String asunto, Object desde, String cuerpo, List<?> aList, List<?> ccList, Date fechaEnvio) {
		this.usuarioFactoria = usuarioDao;
		this.mailDao = mailDao;
		this.mailSession = mailSession;
		this.adjuntos = adjuntos;
		this.asunto = asunto;
		this.desde = desde;
		this.cuerpo = cuerpo;
		this.aList = aList;
		this.ccList = ccList;
		this.fechaEnvio = fechaEnvio;
		lbRecuperaDosfases=false;
	}
	
	/**
	 * Recuperación dos fases
	 * @param usuarioDao
	 * @param mailDao
	 * @param mailSession
	 * @param adjuntos
	 * @param asunto
	 * @param desde
	 * @param cuerpo
	 * @param aList
	 * @param ccList
	 * @param codUsuario
	 * @param soloSMS
	 */
	public HMail(UsuarioFactoria usuarioDao, MailFactoria mailDao, Session mailSession, List<String> adjuntos,
			String asunto, Object desde, String cuerpo, List<?> aList, List<?> ccList, Long codUsuario, boolean soloSMS,String strNotaPrivacidad) {
		this.usuarioFactoria = usuarioDao;
		this.mailDao = mailDao;
		this.mailSession = mailSession;
		this.adjuntos = adjuntos;
		this.asunto = asunto;
		this.desde = desde;
		this.cuerpo = cuerpo;
		this.aList = aList;
		this.ccList = ccList;
		this.fechaEnvio = DateUtils.getFechaSistema();
		lbRecuperaDosfases = true;
		this.llCodUsuario = codUsuario;
		this.lbEnviarSoloSMS = soloSMS;
		this.lsNotaPrivacidadEmail=strNotaPrivacidad;
	}	


	@Override
	public void run() {
		String lsSistemaNotaPrivacidad, lsPrintmsgcuerpoConHTML;
		if(lsNotaPrivacidadEmail!=null &&!"".equals(lsNotaPrivacidadEmail)){
			lsSistemaNotaPrivacidad=lsNotaPrivacidadEmail;
		}else{
			lsSistemaNotaPrivacidad=ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_NOTA_PRIVACIDAD);	
		}
		
		
		String cuerpoConHTML = this.cuerpo;
		try {
			MimeMessage message = new MimeMessage(mailSession);

			String correoElectronico = null;
			if (desde != null) {
				List<String> cuentasCorreoFrom = cuentasCorreo(desde);
				for (String correoElectronicoFrom : cuentasCorreoFrom) {
					correoElectronico = correoElectronicoFrom;
					message.setFrom(new InternetAddress(correoElectronicoFrom));
					break;
				}
			}

			if (correoElectronico == null || correoElectronico.isEmpty()) {
				correoElectronico = ConfigurationBundleConstants.mailSystem();
				message.setFrom(new InternetAddress(correoElectronico));
			}

			for (Object a : aList) {
				List<String> cuentasCorreoA = cuentasCorreo(a);
				for (String correoElectronicoA : cuentasCorreoA) {
					message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(correoElectronicoA));
				}
			}

			if (ccList != null) {
				for (Object c : ccList) {
					List<String> cuentasCorreoCC = cuentasCorreo(c);
					for (String correoElectronicoCC : cuentasCorreoCC) {
						message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(correoElectronicoCC));
					}
				}
			}

			MimeBodyPart messageBodyPart = new MimeBodyPart();

			message.setSubject(asunto, ConfigurationBundleConstants.charset());

			cuerpoConHTML = HTMLUtil.abreParrafo + DateUtils.getSaludo() + "," + HTMLUtil.retornoCarro
					+ HTMLUtil.retornoCarro + cuerpoConHTML + HTMLUtil.cierraParrafo;

			cuerpoConHTML = ConfigurationBundleConstants
					.getString(ConfigurationBundleConstants.MAIL_SISTEMA_BASE_CUERPO_ENCABEZADO)
					.replace("CNS_LOGO",
							ConfigurationBundleConstants
									.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LOGO_CORREO))
					.replace("CNS_LEMA_GOBIERNO",
							ConfigurationBundleConstants
									.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LEMA_GOBIERNO))
					+ cuerpoConHTML
					+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_FIRMA)
							.replace("CNS_LOGO",
									ConfigurationBundleConstants
											.getRutaArchivoApp(ConfigurationBundleConstants.CNS_LOGO_CORREO))
							.replace("CNS_ADMIN_NOMBRE", ConfigurationBundleConstants.adminNombre())
							.replace("CNS_ADMIN_CARGO", ConfigurationBundleConstants.adminCargo())
							.replace("CNS_CUENTA_CORREO_ADMIN", ConfigurationBundleConstants.adminCuentaCorreo())
							.replace("CNS_ADMIN_TELEFONO", ConfigurationBundleConstants.adminTelefono())
					+ lsSistemaNotaPrivacidad
					+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_SISTEMA_BASE_CUERPO_PIE);

			if (this.adjuntos != null && !this.adjuntos.isEmpty()) {
				List<String> adjuntos = new LinkedList<>();

				for (String adjunto : this.adjuntos) {
					if (!adjuntos.contains(adjunto)) {
						adjuntos.add(adjunto);
					}
				}

				this.adjuntos = adjuntos;

				boolean adjuntoArchivos = false;
				Multipart multipart = new MimeMultipart();

				messageBodyPart.setContent(cuerpoConHTML,
						"text/html; charset=" + ConfigurationBundleConstants.charset());
				multipart.addBodyPart(messageBodyPart);

				for (String rutaAdjunto : adjuntos) {
					MimeBodyPart messageAttachment = new MimeBodyPart();
					File f = new File(rutaAdjunto);
					if (f.exists()) {
						if (!adjuntoArchivos) {
							adjuntoArchivos = true;
						}
						DataSource source = new FileDataSource(rutaAdjunto);
						messageAttachment.setDataHandler(new DataHandler(source));
						messageAttachment.setFileName(f.getName());
						multipart.addBodyPart(messageAttachment);
					}
				}
				if (adjuntoArchivos) {
					message.setContent(multipart);
				}
			} else {
				message.setContent(cuerpoConHTML, "text/html; charset=" + ConfigurationBundleConstants.charset());
			}
			
			try{
				lsPrintmsgcuerpoConHTML="N";
				lsPrintmsgcuerpoConHTML=ConfigurationBundleConstants.getString("PRINTMSGCUERPOCONHTML");
				if (lsPrintmsgcuerpoConHTML==null)
					lsPrintmsgcuerpoConHTML="N";
			}catch(Exception z){
				lsPrintmsgcuerpoConHTML="N";	
			}
			if ("S".equals(lsPrintmsgcuerpoConHTML))
				logger.error("cuerpoConHTML", cuerpoConHTML);
			
			
			message.setSentDate(this.fechaEnvio);
			Transport.send(message);
		} catch (Exception e) {
			logger.error("Error al enviar el correo: ", e);
			if(lbRecuperaDosfases)
				sendMSG();
			this.interrupt();
		} finally {
			if (adjuntos != null) {
				for (String rutaAdjunto : adjuntos) {
					FileUtil.eliminarArchivo(rutaAdjunto);
				}
			}
		}
	}

	private List<String> cuentasCorreo(Object a) {
		List<String> correos = new LinkedList<>();
		if (a instanceof UsuarioDTO) {
			String correoElectronico = ((UsuarioDTO) a).getCorreoElectronico();
			if (correoElectronico != null && !correoElectronico.isEmpty()) {
				correos.add(correoElectronico);
			}
		} else if (a instanceof String) {
			String correoElectronico = String.valueOf(a);
			if (correoElectronico != null && !correoElectronico.isEmpty()) {
				correos.add(correoElectronico);
			}
		} else if (a instanceof CuentaCorreo) {
			CuentaCorreo c = ((CuentaCorreo) a);
			if (c.getPorPerfil() != null && !c.getPorPerfil().isEmpty()) {
				String perfil = c.getPorPerfil();
				List<UsuarioDTO> usuarios = usuarioFactoria.obtenerUsuariosPorRol(perfil);
				for (UsuarioDTO usuario : usuarios) {
					if (usuario.getCorreoElectronico() != null && !usuario.getCorreoElectronico().isEmpty()) {
						correos.add(usuario.getCorreoElectronico());
					}
				}
			} else {
				correos.add(c.getNombreCuenta());
			}
		}
		return correos;
	}

    public static String sendMSG() {
        String out = "";
        try {
            URL server = new URL(ConfigurationBundleConstants.getPathServerRest().trim()+"SigepSMS"
            																			+"/"+URLEncoder.encode("message",  java.nio.charset.StandardCharsets.UTF_8.toString()) 
            																	        +"/"+llCodUsuario);
            URLConnection connection = server.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = "";
            while ((inputLine = in.readLine()) != null) {
                out += inputLine;
            }
            in.close();
        } 
        catch(Exception z){
        	return out;
        }
        return out;
    }	
	
}
