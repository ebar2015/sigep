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

import co.gov.dafp.sigep2.server.ServicesSigepRestCont;
import co.gov.dafp.sigep2.utils.LoggerSigep;

/**
 * @author joseviscaya
 *
 */
public class TestServicioCont extends Mockito{
	private final String  TOKEN = "12333-45567-777778-882235g";
	private ServicesSigepRestCont restService = new ServicesSigepRestCont();
	private HttpServletRequest req = mock(HttpServletRequest.class); 
	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#setpersonaparentescopersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetpersonaparentescopersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testSetpersonaparentescopersona() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.setpersonaparentescopersona(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#getusuariosporentidadfiltro(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetusuariosporentidadfiltro() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testGetusuariosporentidadfiltro() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getusuariosporentidadfiltro(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#getusuariosporentidadfiltroB(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetusuariosporentidadfiltroB() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testGetusuariosporentidadfiltroB() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getusuariosporentidadfiltroB(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#setmodificacioncontrato(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetmodificacioncontrato() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testSetmodificacioncontrato() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.setmodificacioncontrato(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#getmodificacioncontrato(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetmodificacioncontrato() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testGetmodificacioncontrato() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 
		 /*Response out = restService.getmodificacioncontrato(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#getmodificacioncontratoportipomod(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetmodificacioncontratoportipomod() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testGetmodificacioncontratoportipomod() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 /*json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getmodificacioncontratoportipomod(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#getContratoPersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetContratoPersona() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testGetContratoPersona() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getContratoPersona(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#getContratoFiltro(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetContratoFiltro() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testGetContratoFiltro() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 /*json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getContratoFiltro(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#getContratoPersonaFecha(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetContratoPersonaFecha() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testGetContratoPersonaFecha() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getContratoPersonaFecha(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestCont#getContratoEntreDias(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetContratoEntreDias() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);
		 LoggerSigep.getInstance().info("testGetContratoEntreDias() Start JunitTest", TestServicioCont.class);
		  String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioCont.class);
		 /*json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getContratoEntreDias(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioCont.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioCont.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

}
