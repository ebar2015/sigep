/**
 * 
 */
package co.gov.dafp.sigep2.junit.testWeb;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.mockito.Mockito;

import co.gov.dafp.sigep2.server.ServicesSigepRestLogin;
import co.gov.dafp.sigep2.utils.LoggerSigep;

/**
 * @author joseviscaya
 *
 */
public class TestServicioLogin extends Mockito{
	private ServicesSigepRestLogin restService = new ServicesSigepRestLogin();
	private HttpServletRequest req = mock(HttpServletRequest.class); 

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestLogin#transaccion(java.lang.String)}.
	 */
	@Test
	public void testTransaccion() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testTransaccion() Start JunitTest", TestServicioLogin.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioLogin.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.transaccion(json);
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioLogin.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestLogin#tiposdocumentos()}.
	 */
	@Test
	public void testTiposdocumentos() {
		LoggerSigep.getInstance().configureAppender();
		LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
		LoggerSigep.getInstance().info("testTiposdocumentos() Start JunitTest", TestServicioLogin.class);
		Response out = restService.tiposdocumentos();
		LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioLogin.class);
		LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
		assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestLogin#inserPersona(javax.servlet.http.HttpServletRequest, java.lang.String)}.
	 */
	@Test
	public void testInserPersona() {
		LoggerSigep.getInstance().configureAppender();
		LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
		LoggerSigep.getInstance().info("testInserPersona() Start JunitTest", TestServicioLogin.class);
		String json = ""; //-> imtroducir aqui el json a probar
		LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioLogin.class);
		json = new String(Base64.encodeBase64(json.getBytes()));
		Response out = restService.inserPersona(req, json);
		LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioLogin.class);
		LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
	    assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestLogin#getPersona(javax.servlet.http.HttpServletRequest, java.lang.String)}.
	 */
	@Test
	public void testGetPersona() {
		LoggerSigep.getInstance().configureAppender();
		LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
		LoggerSigep.getInstance().info("testGetPersona() Start JunitTest", TestServicioLogin.class);
		String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioLogin.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getPersona(req, json);
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioLogin.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestLogin#loginMobile(java.lang.String)}.
	 */
	@Test
	public void testLoginMobile() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
		 LoggerSigep.getInstance().info("testLoginMobile() Start JunitTest", TestServicioLogin.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioLogin.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.loginMobile(json);
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioLogin.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioLogin.class);
	     assertTrue(out.getStatus() == 201);
	}

}
