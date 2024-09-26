/**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.services.DatoContactoService;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.services.UsuarioService;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.StringEncrypt;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author joseviscaya
 * 2019, 7:20:44 AMO
 * SendSMS.java
 */
@Path("/SigepSMS")
public class SendSMS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000440030910892192L;
	protected  String URL;
	protected  String CLIENTE;
	protected  String KEY;
	protected  String MENSAJE_ENVIO;
	protected  String ENVIO_ACTIVO;
	protected  final String ERROR_URL 		= "{\"msg\":\"Error No URL(SigepII)\",\"error\":\"1001\"}";
	protected  final String ERROR_CLIENTE 	= "{\"msg\":\"Error No CLIENTE(SigepII)\",\"error\":\"1002\"}";
	protected  final String ERROR_KEY 		= "{\"msg\":\"Error No KEY(SigepII)\",\"error\":\"1003\"}";
	
	
	public SendSMS() {
		ParametricaService serviceP = new ParametricaService();
		LoggerSigep.getInstance().configureAppender();
		LoggerSigep.getInstance().info("*************************************************************************************************", SendSMS.class);
		LoggerSigep.getInstance().info("SigepSMS: [inicio]", SendSMS.class);
		LoggerSigep.getInstance().info("*************************************************************************************************", SendSMS.class);
		try {
			this.URL = serviceP.getPrametricaById(new BigDecimal(2283)).getValorParametro();
		} catch (Exception e) {
			this.URL = "";
		}
		try {
			this.CLIENTE = serviceP.getPrametricaById(new BigDecimal(2284)).getValorParametro();
		} catch (Exception e) {
			this.CLIENTE = "";
		}
		try {
			this.KEY = serviceP.getPrametricaById(new BigDecimal(2285)).getValorParametro();
		} catch (Exception e) {
			this.KEY = "";
		}
		try {
			this.MENSAJE_ENVIO = serviceP.getPrametricaById(new BigDecimal(2286)).getValorParametro();
		} catch (Exception e) {
			this.MENSAJE_ENVIO = "Recuperacion contrasenna, ingrese con codigo: ";
		}
		
		try {
			this.ENVIO_ACTIVO = serviceP.getPrametricaById(new BigDecimal(2287)).getValorParametro();
		} catch (Exception e) {
			this.ENVIO_ACTIVO = "N";
		}				
		
	}
	/**
	 * 
	 * @author: Jose Viscaya
	 * @param numCel
	 * @param mensaje
	 * @return
	 * @Fecha :Mar 6, 2019
	 * SendSMS.java
	 */
	@GET()
    @Path("/{mensaje}/{codUsuario}")
	@Produces("text/plain")
	public Response sendMailMessage(@PathParam("mensaje") String mensaje, @PathParam("codUsuario") Integer codUsuario) { 
		LoggerSigep.getInstance().info("*************************************************************************************************", SendSMS.class);
		LoggerSigep.getInstance().info("SigepSMS: [Envio de Mensaje sms]", SendSMS.class);
		LoggerSigep.getInstance().info("SigepSMS: [mensaje] "+mensaje, SendSMS.class);
		LoggerSigep.getInstance().info("SigepSMS: [codUsuario] "+codUsuario, SendSMS.class);
		String  salida="";
		try {
			ParametricaService serviceP = new ParametricaService();
			this.ENVIO_ACTIVO = serviceP.getPrametricaById(new BigDecimal(2287)).getValorParametro();
		} catch (Exception e) {
			this.ENVIO_ACTIVO = "N";
		}		
		if ("S".equals(ENVIO_ACTIVO)){
			salida = procesaSMS(mensaje, codUsuario);	
		}
		LoggerSigep.getInstance().info("SigepSMS: [salida] "+salida, SendSMS.class);
		LoggerSigep.getInstance().info("*************************************************************************************************", SendSMS.class);
		return Response.status(200).entity(salida).build();
	}
	
	/**
	 * 
	 * @param numCel
	 * @param mensaje
	 * @param codUsuario
	 * @return
	 */
	@GET()
    @Path("send/{numCel}/{mensaje}")
	@Produces("text/plain")
	public Response sendsmsMessage(@PathParam("numCel") String numCel, @PathParam("mensaje") String mensaje) { 
		LoggerSigep.getInstance().info("*************************************************************************************************", SendSMS.class);
		LoggerSigep.getInstance().info("SigepSMS: [Envio de Mensaje sms]", SendSMS.class);
		LoggerSigep.getInstance().info("SigepSMS: [mensaje] "+mensaje, SendSMS.class);
		LoggerSigep.getInstance().info("SigepSMS: [numCel] "+numCel, SendSMS.class);
		String  salida="";
		try {
			ParametricaService serviceP = new ParametricaService();
			this.ENVIO_ACTIVO = serviceP.getPrametricaById(new BigDecimal(2287)).getValorParametro();
		} catch (Exception e) {
			this.ENVIO_ACTIVO = "N";
		}		
		if ("S".equals(ENVIO_ACTIVO)){
			salida = procesaSMS(numCel, mensaje);	
		}
		LoggerSigep.getInstance().info("SigepSMS: [salida] "+salida, SendSMS.class);
		LoggerSigep.getInstance().info("*************************************************************************************************", SendSMS.class);
		return Response.status(200).entity(salida).build();
	}
	/**
	 * 
	 * @author: Jose Viscaya
	 * @param numCel
	 * @param mensaje
	 * @return
	 * @Fecha :Mar 6, 2019
	 * SendSMS.java
	 */
	protected String procesaSMS(String mensaje, Integer codUsuario) {
		LoggerSigep.getInstance().info("SigepSMS: [PROCESO]", SendSMS.class);
		String numCel = "";
		if(URL != null) {
			if(URL.isEmpty()) {
				return ERROR_URL;
			}
		}else {
			return ERROR_URL;
		}
		if(CLIENTE != null) {
			if(CLIENTE.isEmpty()) {
				return ERROR_CLIENTE;
			}
		}else {
			return ERROR_CLIENTE;
		}

		if(KEY != null) {
			if(KEY.isEmpty()) {
				return ERROR_KEY;
			}
		}else {
			return ERROR_KEY;
		}

		if(codUsuario!=null) {
			UsuarioService serviceUS = new UsuarioService();
			Usuario us = serviceUS.getUsuario(new BigDecimal(codUsuario));
			Persona persona;
			DatoContactoExt datoContacto;
			if(us!=null){
				PersonaService serviceP = new PersonaService();
				persona = serviceP.personaByCodUsuario(us);
				if(persona!=null && persona.getCodPersona()!=null){
					DatoContactoService serviceDC = new DatoContactoService();
					datoContacto = serviceDC.getDatoContactoPorIdPersona(persona.getCodPersona().longValue());
					if(datoContacto!=null && datoContacto.getNumCelular()!=null){
						numCel = datoContacto.getNumCelular();
						String pin = UtilsConstantes.generarPingSms();
						pin = pin.concat("*");
						String pinEncrypt  = StringEncrypt.encrypt(pin);
						mensaje = MENSAJE_ENVIO.concat(" ").concat(pin);
						us.setContrasena(pinEncrypt);
						us.setAudFechaActualizacion(new Date());
						us.setFechaVence(null);
						us.setNumeroReintentos(new BigDecimal(0));
						us.setFlgBloqueado((short) 0);
						serviceUS.updateUsuarioSelective(us);	
						String salida="";
						try {
							URL url = new URL(URL);
							Map<String, Object> params = new LinkedHashMap<>();
							String json ="[{\"destino\":\""+numCel+"\",\"mensaje\":\""+mensaje+"\"}]";
							json = String.valueOf(json);
							String view="getEnvioSMS";
							params.put("json", json);
							params.put("login", CLIENTE);
							params.put("clave", KEY);
							params.put("view", view);
							LoggerSigep.getInstance().info("SigepSMS: [json]" +json, SendSMS.class);
							StringBuilder postData = new StringBuilder();
							for (Map.Entry<String, Object> param : params.entrySet()) {
								if (postData.length() != 0)postData.append('&');
								postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
								postData.append('=');
								postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
							}
							byte[] postDataBytes = postData.toString().getBytes("UTF-8");
							HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
							conn.setRequestMethod("POST"); 
							conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
							conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length)); 
							conn.setDoOutput(true); conn.getOutputStream().write(postDataBytes); 
							Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); 
							for (int c; (c = in.read()) >= 0;) {
								salida += ((char) c);
							}
							salida = salida.toString();
							return salida;
						} catch (IOException e) {
							LoggerSigep.getInstance().info("SigepSMS: [ERROR]" +e.getMessage(), SendSMS.class);
							return e.getMessage();
						}catch (Exception e) {
							LoggerSigep.getInstance().info("SigepSMS: [ERROR]" +e.getMessage(), SendSMS.class);
							return e.getMessage();
						}					
					}
					LoggerSigep.getInstance().info("SigepSMS: [INFO] La persona no cuenta con un número de celular registrado", SendSMS.class);
				}
			}
		}
		return "";
	}
	/**
	 * 
	 * @param numCel
	 * @param mensaje
	 * @return
	 * @throws IOException
	 */
	protected String procesaSMS(String numCel, String mensaje){
		LoggerSigep.getInstance().info("SigepSMS: [PROCESO]", SendSMS.class);
		if(URL != null) {
			if(URL.isEmpty()) {
				return ERROR_URL;
			}
		}else {
			return ERROR_URL;
		}
		if(CLIENTE != null) {
			if(CLIENTE.isEmpty()) {
				return ERROR_CLIENTE;
			}
		}else {
			return ERROR_CLIENTE;
		}
		
		if(KEY != null) {
			if(KEY.isEmpty()) {
				return ERROR_KEY;
			}
		}else {
			return ERROR_KEY;
		}
        
        String view="getEnvioSMS";  
        String salida="";

        URL url;
		try {
			url = new URL(URL);
			Map<String, Object> params = new LinkedHashMap<>();
	        String json ="[{\"destino\":\""+numCel+"\",\"mensaje\":\""+mensaje+"\"}]";
	        json = String.valueOf(json);
	        params.put("json", json);
	        params.put("login", CLIENTE);
	        params.put("clave", KEY);
	        params.put("view", view);
	        LoggerSigep.getInstance().info("SigepSMS: [json]" +json, SendSMS.class);
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String, Object> param : params.entrySet()) {
	        	if (postData.length() != 0)postData.append('&');
	            try {
					postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
	        conn.setRequestMethod("POST"); 
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length)); 
	        conn.setDoOutput(true); conn.getOutputStream().write(postDataBytes); 
	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); 
	          for (int c; (c = in.read()) >= 0;) {
	               salida += ((char) c);
	       }
	       salida = salida.toString();
	       return salida;
		} catch (IOException e) {
			LoggerSigep.getInstance().info("SigepSMS: [ERROR]" +e.getMessage(), SendSMS.class);
			return e.getMessage();
		}
	}

	
}
