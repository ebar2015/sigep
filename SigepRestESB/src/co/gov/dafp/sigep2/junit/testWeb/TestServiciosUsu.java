package co.gov.dafp.sigep2.junit.testWeb;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.mockito.Mockito;

import co.gov.dafp.sigep2.server.ServicesSigepRestUsuarios;
import co.gov.dafp.sigep2.utils.LoggerSigep;

public class TestServiciosUsu extends Mockito{
    private ServicesSigepRestUsuarios restService = new ServicesSigepRestUsuarios();
    private final String  TOKEN = "12333-45567-777778-882235g";
    private HttpServletRequest req = mock(HttpServletRequest.class); 
    
	@Test
	public void testPersontipIdnumId() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testPersontipIdnumId() Start JunitTest", TestServiciosUsu.class);
		 String json = "{\"codTipoIdentificacion\":38,\"numeroIdentificacion\":\"112345678914\"}"; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.persontipIdnumId(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testPersontipIdnumIdEntId() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testPersontipIdnumIdEntId() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.persontipIdnumIdEntId(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testUsuarioEntidadPorUsuarioId() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testUsuarioEntidadPorUsuarioId() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.usuarioEntidadPorUsuarioId(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testUsuariosPorEntidad() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testUsuariosPorEntidad() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.usuariosPorEntidad(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testUsuarioPassword() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testUsuarioPassword() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.usuarioPassword(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testUsuarioEntidadPorcodUsCodent() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testUsuarioEntidadPorcodUsCodent() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.usuarioEntidadPorcodUsCodent(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testPersonEntidadPorPersonaEnt() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testPersonEntidadPorPersonaEnt() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.personEntidadPorPersonaEnt(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testPersonaporId() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testPersonaporId() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.personaporId(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testPersonaporfiltro() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testPersonaporfiltro() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.personaporfiltro(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testPersonaporidext() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testPersonaporidext() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.personaporidext(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testUpdatePersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testUpdatePersona() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.updatePersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testUpdatePersonatarjeta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testUpdatePersonatarjeta() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.updatePersonatarjeta(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetNacionalidadPerfil() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testSetNacionalidadPerfil() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setNacionalidadPerfil(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetIdiomaComplPersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testGetIdiomaComplPersona() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getIdiomaComplPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetIdiomaPersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testSetIdiomaPersona() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setIdiomaPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetIdiomaPersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testGetIdiomaPersona() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getIdiomaPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetPersonaContacto() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testGetPersonaContacto() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getPersonaContacto(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSeltUsuarioExt() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testSeltUsuarioExt() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.seltUsuarioExt(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetidiomapersonaporpersonaporid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("testGetidiomapersonaporpersonaporid() Start JunitTest", TestServiciosUsu.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosUsu.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getidiomapersonaporpersonaporid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosUsu.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosUsu.class);
	     assertTrue(out.getStatus() == 201);
	}

}
