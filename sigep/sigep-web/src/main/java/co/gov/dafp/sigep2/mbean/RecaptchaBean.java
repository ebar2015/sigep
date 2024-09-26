package co.gov.dafp.sigep2.mbean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;
import javax.transaction.NotSupportedException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ViewScoped
@ManagedBean
public class RecaptchaBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -6238658086067734323L;
	
	/*Metodo que valida el captcha*/
	public boolean verificarRecaptcha(String recaptchaToken ) {
		
			FacesContext context 			= FacesContext.getCurrentInstance();
		    ExternalContext externalContext = context.getExternalContext();
		    String secretKey 				= externalContext.getInitParameter("primefaces.PRIVATE_CAPTCHA_KEY");
		    
	
			Integer codScoreCaptcha = TipoParametro.RECAPTCHA_SCORE.getValue();
			Parametrica paramAsunto = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(codScoreCaptcha));
			Double valorCaptcha 	= (paramAsunto.getValorParametro() != null && !paramAsunto.getValorParametro().isEmpty()) ? Double.parseDouble(paramAsunto.getValorParametro()) : 0.5;
		    try {
		    	if(!recaptchaToken.equals("")) {
		        // Realiza la solicitud de verificación a la API de reCAPTCHA
		        String url 	= "https://www.google.com/recaptcha/api/siteverify";
		        String data = "secret=" + URLEncoder.encode(secretKey, "UTF-8") +
		                "&response=" + URLEncoder.encode(recaptchaToken, "UTF-8");
	
		        URL apiURL = new URL(url);
		        HttpsURLConnection connection = (HttpsURLConnection) apiURL.openConnection();
		        connection.setRequestMethod("POST");
		        connection.setDoOutput(true);
		        connection.getOutputStream().write(data.getBytes("UTF-8"));
	
		        // Lee la respuesta de la API
		        BufferedReader reader 			= new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        StringBuilder responseBuilder 	= new StringBuilder();
		        String line;
		        while ((line = reader.readLine()) != null) {
		            responseBuilder.append(line);
		        }
		        reader.close();
	
		        // Parsea la respuesta JSON
		        Gson gson 				= new Gson();
		        JsonObject responseJson = gson.fromJson(responseBuilder.toString(), JsonObject.class);
	
		        // Verifica el resultado de la verificación
		        boolean success = responseJson.get("success").getAsBoolean();
		        double score 	= responseJson.get("score").getAsDouble();
		        String hostname = responseJson.get("hostname").getAsString();
	
		        if (success && score >= valorCaptcha) {
		            // El reCAPTCHA se ha verificado correctamente
		        	return true;
		        } else {
		        	// El reCAPTCHA no es válido o la puntuación es muy baja
		        	String errorMessage = "El reCAPTCHA no se validó correctamente para la ip: " + hostname +  " Puntaje: " + score + ", Éxito: " + success;
		        	logger.error("Recaptcha Error: ", errorMessage);
		        	return false;
		        }
		    }
		    	return true;
		    } catch (Exception e) {
		    	logger.error("Recaptcha Error Exepción: ", e.getMessage());
		    	return false;
		    }
		
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws NotSupportedException, SIGEP2SistemaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
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
}
