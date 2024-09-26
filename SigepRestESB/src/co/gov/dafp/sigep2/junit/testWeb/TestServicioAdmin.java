/**
 * 
 */
package co.gov.dafp.sigep2.junit.testWeb;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.mockito.Mockito;

import org.apache.commons.codec.binary.Base64;

import co.gov.dafp.sigep2.server.ServicesSigepRestAdmin;
import co.gov.dafp.sigep2.utils.LoggerSigep;

/**
 * @author joseviscaya
 *
 */
public class TestServicioAdmin extends Mockito{
	private final String  TOKEN = "12333-45567-777778-882235g";
	private ServicesSigepRestAdmin restService = new ServicesSigepRestAdmin();
	private HttpServletRequest req = mock(HttpServletRequest.class); 
	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestAdmin#getSituacionAdministrativaFiltro(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetSituacionAdministrativaFiltro() {
         LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("testGetSituacionAdministrativaFiltro() Start JunitTest", TestServicioAdmin.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioAdmin.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 json = new String(Base64.encodeBase64(json.getBytes()));
		Response out = restService.getSituacionAdministrativaFiltro(req, json, TOKEN, "30","Test");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);;
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestAdmin#getSituacionPadre(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetSituacionPadre() {
         LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("testGetSituacionPadre() Start JunitTest", TestServicioAdmin.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioAdmin.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getSituacionPadre(req, json, TOKEN, "30","Test");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);;
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestAdmin#setSituacionAdminVinculacion(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetSituacionAdminVinculacion() {
 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("testSetSituacionAdminVinculacion() Start JunitTest", TestServicioAdmin.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioAdmin.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setSituacionAdminVinculacion(req, json, TOKEN, "30","Test");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);;
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestAdmin#getSituacionVinculacion(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetSituacionVinculacion() {
		LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("testGetSituacionVinculacion() Start JunitTest", TestServicioAdmin.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioAdmin.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 
		 Response out = restService.getSituacionVinculacion(req, json, TOKEN, "30","Test");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);;
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestAdmin#getEnfemedadLike(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetEnfemedadLike() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("testGetEnfemedadLike() Start JunitTest", TestServicioAdmin.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServicioAdmin.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getEnfemedadLike(req, json, TOKEN, "30","Test");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServicioAdmin.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServicioAdmin.class);;
	     assertTrue(out.getStatus() == 201);
	}

}
