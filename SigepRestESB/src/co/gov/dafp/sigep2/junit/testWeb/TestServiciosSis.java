package co.gov.dafp.sigep2.junit.testWeb;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.mockito.Mockito;

import co.gov.dafp.sigep2.server.ServicesSigepRestSis;
import co.gov.dafp.sigep2.utils.LoggerSigep;

public class TestServiciosSis extends Mockito{
	private final String  TOKEN = "12333-45567-777778-882235g";
	private ServicesSigepRestSis restService = new ServicesSigepRestSis();
	private HttpServletRequest req = mock(HttpServletRequest.class); 
	
	@Test
	public void testRecursoPorCodVentana() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testRecursoPorCodVentana() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.recursoPorCodVentana(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testRecursoActivoPerfilUsuario() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testRecursoActivoPerfilUsuario() Start JunitTest", TestServiciosSis.class);
		 Response out = restService.recursoActivoPerfilUsuario(req, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testEntidadesTotal() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testEntidadesTotal() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.entidadesTotal(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testDesactivarEntidad() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testDesactivarEntidad() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.desactivarEntidad(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testListInstitucionEducativa() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testListInstitucionEducativa() Start JunitTest", TestServiciosSis.class);
		 Response out = restService.listInstitucionEducativa(req, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testListInstitucionEducativatipo() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testListInstitucionEducativatipo() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.listInstitucionEducativatipo(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testIdiomasList() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testIdiomasList() Start JunitTest", TestServiciosSis.class);
		 Response out = restService.idiomasList(req, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetParametrica() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetParametrica() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParametrica(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testParametricaintentos() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testParametricaintentos() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParametrica(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetProgramaAcademicoByAll() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetProgramaAcademicoByAll() Start JunitTest", TestServiciosSis.class);
		 Response out = restService.getProgramaAcademicoByAll(req, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetPaises() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetPaises() Start JunitTest", TestServiciosSis.class);
		 Response out = restService.getPaises(req, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetDeptos() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetDeptos() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getDeptos(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetMunicipios() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetMunicipios() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getMunicipios(req, json, TOKEN, "30","3");
		  LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetentidadporid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetentidadporid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getentidadporid(req, json, TOKEN, "30","3");
		  LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetrolporid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetrolporid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getrolporid(req, json, TOKEN, "30","3");
		  LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testRegistrartoken() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testRegistrartoken() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.registrartoken(req, json);
		  LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetrolesistema() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetrolesistema() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getrolesistema(req, json, TOKEN, "30","3");
		  LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testExpirartoken() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testExpirartoken() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.expirartoken(req, json, TOKEN, "30","3");
		  LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetdependenciaentidad() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetdependenciaentidad() Start JunitTest", TestServiciosSis.class);
		 Response out = restService.getdependenciaentidad(req, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetParametricaporid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetParametricaporid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParametricaporid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testListInstitucionEducativaporid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testListInstitucionEducativaporid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.listInstitucionEducativaporid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetParametricaporpadre() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetParametricaporpadre() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParametricaporpadre(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetrolporpadreid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetrolporpadreid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getrolporpadreid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetanduprol() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testSetanduprol() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setanduprol(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetPaisesporid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetPaisesporid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getPaisesporid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetdeptoporid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetdeptoporid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getdeptoporid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetCargoBycodEntidad() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetCargoBycodEntidad() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getCargoBycodEntidad(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGeentidadesporcodtipoentidad() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGeentidadesporcodtipoentidad() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.geentidadesporcodtipoentidad(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetcargostodos() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetcargostodos() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getcargostodos(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetprogramaacademicoporint() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetprogramaacademicoporint() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getprogramaacademicoporint(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetprogramaacademicoporid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetprogramaacademicoporid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getprogramaacademicoporid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetFile() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetFile() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getFile(req, json);
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetParametricapornamelike() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetParametricapornamelike() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParametricapornamelike(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetDatoContactoPorIdPersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testSetDatoContactoPorIdPersona() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setDatoContactoPorIdPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetRolByPerosna() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetRolByPerosna() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getRolByPerosna(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetRol() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testSetRol() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setRol(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetUsuarioRolEntidad() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testSetUsuarioRolEntidad() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setUsuarioRolEntidad(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetpersonaparentescopersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetpersonaparentescopersona() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getpersonaparentescopersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetpersonaparentescopersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testSetpersonaparentescopersona() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setpersonaparentescopersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetauditoriaconfiguracionid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetauditoriaconfiguracionid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getauditoriaconfiguracionid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetParametricaporpadrenull() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetParametricaporpadrenull() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParametricaporpadrenull(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetRespuestasPregunta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetRespuestasPregunta() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getRespuestasPregunta(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetTotalRespuestasPregunta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetTotalRespuestasPregunta() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getTotalRespuestasPregunta(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetRespuestasPuntagePregunta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetRespuestasPuntagePregunta() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getRespuestasPuntagePregunta(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testDelparametrica() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testDelparametrica() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.delparametrica(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testDelpreguntaopinion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testDelpreguntaopinion() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.delpreguntaopinion(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetresultadopregunta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testSetresultadopregunta() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setresultadopregunta(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetpreguntaopinionrdn() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetpreguntaopinionrdn() Start JunitTest", TestServiciosSis.class);
		 Response out = restService.getpreguntaopinionrdn(req,TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetRespuestasGrafico() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetRespuestasGrafico() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getRespuestasGrafico(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetEntidadPorPersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetEntidadPorPersona() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getEntidadPorPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetRespuestaPreguntaPersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetRespuestaPreguntaPersona() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getRespuestaPreguntaPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetVAccionAuditoriaSigla() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetVAccionAuditoriaSigla() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getVAccionAuditoriaSigla(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetVAccionAuditoria() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetVAccionAuditoria() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getVAccionAuditoria(req, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetauditoriaparametrica() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetauditoriaparametrica() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getauditoriaparametrica(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetauditoriafechas() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetauditoriafechas() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getauditoriafechas(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetRespuestasPreguntaesta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetRespuestasPreguntaesta() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getRespuestasPreguntaesta(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetRespuestasGraficoEdades() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetRespuestasGraficoEdades() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getRespuestasGraficoEdades(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetCargosEntidad() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetCargosEntidad() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getCargosEntidad(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetpreguntaopinionfechapregunta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetpreguntaopinionfechapregunta() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getpreguntaopinionfechapregunta(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetpreguntaopinionfecha() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetpreguntaopinionfecha() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getpreguntaopinionfecha(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetAcreenciaObligacion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testSetAcreenciaObligacion() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setAcreenciaObligacion(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetacreenciaobligacion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetacreenciaobligacion() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getacreenciaobligacion(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetaEntidadPlantaid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetaEntidadPlantaid() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getaEntidadPlantaid(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testUpdateabeasdata() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testUpdateabeasdata() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.updateabeasdata(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetentidadcodpersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
		 LoggerSigep.getInstance().info("testGetentidadcodpersona() Start JunitTest", TestServiciosSis.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosSis.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getentidadcodpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosSis.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosSis.class);
	     assertTrue(out.getStatus() == 201);
	}

}
