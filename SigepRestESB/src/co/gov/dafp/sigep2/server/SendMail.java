package co.gov.dafp.sigep2.server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.jersey.core.util.Base64;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.utils.ErrorMensajes;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.UtilsConstantes;



/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar el envio de emails
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Date 28/09/2018
 */
@Path("/SigepMail")
public class SendMail implements Serializable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2342379841477188216L;
	private String MAIL_SUBJECT;
    private Properties props;
    private Session session;
    private String MAIL_HOST;
    private String MAIL_SENDER;
    private String MAIL_USUARIO;
    private String MAIL_CONTRASENA;
    private String MAIL_PORT;
  
    /**
     * 
     * @author: Jose Viscaya 
     * @fecha 28/09/2018 - 8:00:47 a. m.
     * @return
     */
    @GET()
	@Produces("text/plain")
	public String helloNMC() {
		return "!Hello Sigep Send Email";
	}
    
    /**
     * 
     * @author: Jose Viscaya
     * @Fecha 28/09/2018 - 8:00:36 a. m.
     */
    public SendMail(){
    	ParametricaService service 	= new ParametricaService();
    	this.MAIL_HOST 			= service.getPrametricaById(new BigDecimal(2148)) !=null ? service.getPrametricaById(new BigDecimal(2148)).getValorParametro():null;
    	this.MAIL_SENDER 		= service.getPrametricaById(new BigDecimal(2121)) !=null  ? service.getPrametricaById(new BigDecimal(2121)).getValorParametro():null;
    	this.MAIL_USUARIO 		= service.getPrametricaById(new BigDecimal(5937)) !=null  ? service.getPrametricaById(new BigDecimal(5937)).getValorParametro():null;
    	this.MAIL_CONTRASENA 	= service.getPrametricaById(new BigDecimal(2146)) !=null  ? service.getPrametricaById(new BigDecimal(2146)).getValorParametro():null;
    	this.MAIL_PORT 			= service.getPrametricaById(new BigDecimal(2147)) !=null  ? service.getPrametricaById(new BigDecimal(2147)).getValorParametro():null;
    	this.MAIL_SUBJECT 		= service.getPrametricaById(new BigDecimal(2147)) !=null  ? service.getPrametricaById(new BigDecimal(2151)).getValorParametro():null;
		if(MAIL_HOST!=null && MAIL_SENDER !=null && MAIL_USUARIO !=null && MAIL_CONTRASENA!=null && MAIL_PORT!=null && MAIL_SUBJECT!=null) {
			props = new Properties(); 
	    	LoadProperties();
			session = Session.getDefaultInstance(props);
			LoggerSigep.getInstance().configureAppender();
		}
	}
    
    
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 28/09/2018 - 7:59:31 a. m.
	 * @param email
	 * @param msgBody
	 * @return
	 */
    @GET()
    @Path("sendMailMessage/{email}/{body}")
	@Produces("text/plain")
	public String sendMailMessage(@PathParam("email") String email, @PathParam("body") String msgBody) { 
    	if(isEmail(email)){
	        return sendmail(email,msgBody,MAIL_SUBJECT);
    	}else{
    		return "false";
    	}
	}
    
    /**
     * 
     * @author: Jose Viscaya 
     * @fecha 28/09/2018 - 7:59:26 a. m.
     */
    private void LoadProperties() { 
        props.put("mail.smtp.host", this.MAIL_HOST);
        props.put("mail.smtp.port", this.MAIL_PORT);
        props.put("mail.smtp.mail.sender", this.MAIL_SENDER);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.mail.user", this.MAIL_USUARIO);
        props.put("mail.smtp.mail.password", this.MAIL_CONTRASENA);
        props.put("mail.smtp.ssl.trust", this.MAIL_HOST);

    }
    /**
     * 
     * @author: Jose Viscaya 
     * @fecha 28/09/2018 - 7:59:22 a. m.
     * @param correo
     * @return
     */
   
    public static boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        String pattern = UtilsConstantes.REGEX_EMAIL;
        pattern ="^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";
        pat = Pattern.compile(pattern);
        mat = pat.matcher(correo.trim());
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * @author: Jose Viscaya 
     * @fecha 28/09/2018 - 7:59:17 a. m.
     * @param email
     * @param msg
     * @param subject
     * @return
     */
    @GET()
    @Path("sendMail/{email}/{msg}/{subject}")
	@Produces("text/plain")
    public  String sendmail(@PathParam("email") String email, @PathParam("msg") String msg, @PathParam("subject") String subject){
    	if(isEmail(email)){
    		try {
            	MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress((String) props.get("mail.smtp.mail.sender")));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(URLDecoder.decode(subject));
                message.setText(URLDecoder.decode(msg));
                Transport t = session.getTransport("smtp");
                t.connect((String) props.get("mail.smtp.mail.user"), (String) props.get("mail.smtp.mail.password"));
                t.sendMessage(message, message.getAllRecipients());
                t.close();
                return "true";
            } catch (MessagingException e) {
            	LoggerSigep.getInstance().error(e.getMessage(), SendMail.class);
            	return "false";
            }
    	}else {
    		return "false";
    	}
    	
    }
    /**
     * 
     * @author: Jose Viscaya 
     * @fecha 28/09/2018 - 7:59:00 a. m.
     * @param email
     * @param msg
     * @param subject
     * @return
     */
    
    @GET()
    @Path("sendMailHtml/{email}/{msg}/{subject}")
	@Produces("text/plain")
    public  String sendmailHtml(@PathParam("email") String email, @PathParam("msg") String msg, @PathParam("subject") String subject){
    	if(isEmail(email)){
	    	try {
	        	MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress((String) props.get("mail.smtp.mail.sender")));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	            message.setSubject(URLDecoder.decode(subject));
	            message.setContent(msg, "text/html; charset=" + ConfigurationBundleConstants.charset());
	            Transport t = session.getTransport("smtp");
	            t.connect((String) props.get("mail.smtp.mail.user"), (String) props.get("mail.smtp.mail.password"));
	            t.sendMessage(message, message.getAllRecipients());
	            t.close();
	            return "true";
	        } catch (MessagingException e) {
	        	LoggerSigep.getInstance().error(e.getMessage(), SendMail.class);
	        	return "false";
			}
    	}else {
    		return "false";
    	}
    }
    
    @GET()
    @Path("sendMailHtml64/{email}/{msg}/{subject}")
	@Produces("text/plain")
    public  String sendmailHtml64(@PathParam("email") String email, @PathParam("msg") String msg, @PathParam("subject") String subject){
    	if(isEmail(email)){
	    	try {
	    		msg = new String(Base64.decode(msg.getBytes()));
	        	MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress((String) props.get("mail.smtp.mail.sender")));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	            message.setSubject(URLDecoder.decode(subject));
	            message.setContent(msg, "text/html; charset=" + ConfigurationBundleConstants.charset());
	            Transport t = session.getTransport("smtp");
	            t.connect((String) props.get("mail.smtp.mail.user"), (String) props.get("mail.smtp.mail.password"));
	            t.sendMessage(message, message.getAllRecipients());
	            t.close();
	            return "true";
	        } catch (MessagingException e) {
	        	LoggerSigep.getInstance().error(e.getMessage(), SendMail.class);
	        	return "false";
			}
    	}else {
    		return "false";
    	}
    }
    public  String sendmailHtmlWithAttachment( List<String>lstMails, String subject, MimeMultipart mimeMultiPart){
	    	try {
	        	MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress((String) props.get("mail.smtp.mail.sender")));
	            for(String correoTo: lstMails)
	            	message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(correoTo));
	            message.setSubject(URLDecoder.decode(subject));
	            message.setContent(mimeMultiPart);
	            Transport t = session.getTransport("smtp");
	            t.connect((String) props.get("mail.smtp.mail.user"), (String) props.get("mail.smtp.mail.password"));
	            t.sendMessage(message, message.getAllRecipients());
	            t.close();
	            return "true";
	        } catch (MessagingException e) {
	        	LoggerSigep.getInstance().error(e.getMessage(), SendMail.class);
	        	return "false";
			}
    }    
    
    @GET()
    @Path("sendMailHtmlMovile/{email}/{msg}/{subject}")
	@Produces(MediaType.APPLICATION_JSON)
    public  String sendMailHtmlMovile(@PathParam("email") String email, @PathParam("msg") String msg, @PathParam("subject") String subject){
    	ErrorMensajes error = new ErrorMensajes();
    	Gson gson = new Gson();
    	if(isEmail(email)){
	    	try {
	        	MimeMessage message = new MimeMessage(session);
	            message.setFrom(new InternetAddress((String) props.get("mail.smtp.mail.sender")));
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	            message.setSubject(URLDecoder.decode(subject));
	            message.setContent(URLDecoder.decode(msg), "text/html; charset=utf-8");
	            Transport t = session.getTransport("smtp");
	            t.connect((String) props.get("mail.smtp.mail.user"), (String) props.get("mail.smtp.mail.password"));
	            t.sendMessage(message, message.getAllRecipients());
	            t.close();
	            error.setError(false);
	            error.setMensaje("Mensaje Enviado con Exito");
	            return gson.toJson(error);
	        } catch (MessagingException e) {
	        	LoggerSigep.getInstance().error(e.getMessage(), SendMail.class);
	        	error.setError(true);
		        error.setMensaje("Mensaje No Enviado");
		        error.setMensajeTecnico(e.getMessage());
		        return gson.toJson(error);
			}
    	}else {
    		error.setError(true);
	        error.setMensaje("Mensaje No Enviado");
	        error.setMensajeTecnico("Correo Invalido");
	        return gson.toJson(error);
    	}
    }
 }
