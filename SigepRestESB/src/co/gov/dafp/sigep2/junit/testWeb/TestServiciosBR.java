package co.gov.dafp.sigep2.junit.testWeb;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.mockito.Mockito;

import co.gov.dafp.sigep2.server.ServicesSigepRestBR;
import co.gov.dafp.sigep2.utils.LoggerSigep;

public class TestServiciosBR extends Mockito{
	private final String  TOKEN = "12333-45567-777778-882235g";
	private ServicesSigepRestBR restService = new ServicesSigepRestBR();
	private HttpServletRequest req = mock(HttpServletRequest.class); 

	@Test
	public void testSetAcreenciaObligacion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetAcreenciaObligacion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.setAcreenciaObligacion(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testGetacreenciaobligacion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetacreenciaobligacion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getacreenciaobligacion(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("**+*****************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testGetActividadPrivada() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetActividadPrivada() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getActividadPrivada(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testSetActividadPrivada() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetActividadPrivada() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.setActividadPrivada(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testGetBienesPatrimonio() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetBienesPatrimonio() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getBienesPatrimonio(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testSetBienesPatrimonio() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetBienesPatrimonio() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.setBienesPatrimonio(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testGetCuentasDeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetCuentasDeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getCuentasDeclaracion(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testSetCuentasDeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetCuentasDeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.setCuentasDeclaracion(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testGetDeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetDeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getDeclaracion(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testGetDeclaracionId() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetDeclaracionId() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.getDeclaracionId(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testSetDeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetDeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 /*Response out = restService.setDeclaracion(req, json, TOKEN, "30");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);*/
	}

	@Test
	public void testGetOtrosIngresosDeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetOtrosIngresosDeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getOtrosIngresosDeclaracion(req, json, TOKEN, "30", "4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetOtrosIngresosDeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetOtrosIngresosDeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setOtrosIngresosDeclaracion(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetParticipacionJunta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetParticipacionJunta() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParticipacionJunta(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetParticipacionJunta() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetParticipacionJunta() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setParticipacionJunta(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetpersonaparentescopersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetpersonaparentescopersona() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getpersonaparentescopersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetpersonaparentescopersona() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetpersonaparentescopersona() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setpersonaparentescopersona(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testDelBienesPatrimonio() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testDelBienesPatrimonio() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.delBienesPatrimonio(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetingresosdeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetingresosdeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getingresosdeclaracion(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testSetingresosdeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testSetingresosdeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setingresosdeclaracion(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetDeclaracionPorTipo() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetDeclaracionPorTipo() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getDeclaracionPorTipo(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetingresosdeclaracionid() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetingresosdeclaracionid() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getingresosdeclaracionid(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetDeclaracionUnica() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetDeclaracionUnica() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getDeclaracionUnica(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetDeclaracionfiltro() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetDeclaracionfiltro() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getDeclaracionfiltro(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetDeclaracionCreacionn() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetDeclaracionCreacionn() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getDeclaracionCreacionn(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetTotalDeclaracion() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetTotalDeclaracion() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getTotalDeclaracion(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testGetsumaingresos() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testGetsumaingresos() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getsumaingresos(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

	@Test
	public void testUpdatedeclaracionfiltro() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);
		 LoggerSigep.getInstance().info("testUpdatedeclaracionfiltro() Start JunitTest", TestServiciosBR.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosBR.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.updatedeclaracionfiltro(req, json, TOKEN, "30","4");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosBR.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosBR.class);;
	     assertTrue(out.getStatus() == 201);
	}

}
