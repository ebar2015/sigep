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

import co.gov.dafp.sigep2.server.ServicesSigepRestHV;
import co.gov.dafp.sigep2.utils.LoggerSigep;

/**
 * @author joseviscaya
 *
 */
public class TestServiciosHV extends Mockito{
	private final String  TOKEN = "12333-45567-777778-882235g";
	private ServicesSigepRestHV restService = new ServicesSigepRestHV();
	private HttpServletRequest req = mock(HttpServletRequest.class); 
	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#evaldesempusent(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */

	@Test
	public void testEvaldesempusent() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testEvaldesempusent() Start JunitTest", TestServiciosHV.class);
		 String json = "{\"codPersona\":655,\"codEntidad\":1}";
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.evaldesempusent(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}


	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#evaluacionDesempenoPorUSENfe(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testEvaluacionDesempenoPorUSENfe() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testEvaluacionDesempenoPorUSENfe() Start JunitTest", TestServiciosHV.class);
		 String json = "{\"codPersona\":655,\"codEntidad\":1}";
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.evaluacionDesempenoPorUSENfe(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}


	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#experienciaProfesionalPorId(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testExperienciaProfesionalPorId() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testExperienciaProfesionalPorId() Start JunitTest", TestServiciosHV.class);
		 String json = "{\"codExperienciaProfesional\":183}";
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.experienciaProfesionalPorId(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}


	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setexperienciaProfesional(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetexperienciaProfesional() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetexperienciaProfesional() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setexperienciaProfesional(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}


	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getExperianciaDocente(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetExperianciaDocente() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetExperianciaDocente() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getExperianciaDocente(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}



	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getExperianciaFormal001()}.
	 */
	@Test
	public void testGetExperianciaFormal001() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetExperianciaFormal001() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getExperianciaFormal001(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}


	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#expeproporcodpersona()}.
	 */
	@Test
	public void testExpeproporcodpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testExpeproporcodpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.expeproporcodpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}


	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getDatoContactoPorIdPersona()}.
	 */
	@Test
	public void testGetDatoContactoPorIdPersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetDatoContactoPorIdPersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getDatoContactoPorIdPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}


	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setDatoContactoPorIdPersona()}.
	 */
	@Test
	public void testSetDatoContactoPorIdPersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetDatoContactoPorIdPersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setDatoContactoPorIdPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getDatoContactoAdiPorIdPersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetDatoContactoAdiPorIdPersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetDatoContactoAdiPorIdPersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getDatoContactoAdiPorIdPersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setDatocontactoadiporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetDatocontactoadiporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetDatocontactoadiporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setDatocontactoadiporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setExperienciaDoc(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetExperienciaDoc() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetExperienciaDoc() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setExperienciaDoc(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getlogrorecursoporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetlogrorecursoporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetlogrorecursoporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getlogrorecursoporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setlogrorecursoporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetlogrorecursoporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetlogrorecursoporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setlogrorecursoporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#evaldesempporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testEvaldesempporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testEvaldesempporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.evaldesempporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setevaluaciondesempeno(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetevaluaciondesempeno() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetevaluaciondesempeno() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setevaluaciondesempeno(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getreconocimientoporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetreconocimientoporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetreconocimientoporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getreconocimientoporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setreconocimientoporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetreconocimientoporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetreconocimientoporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setreconocimientoporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getPublicacionoporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetPublicacionoporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetPublicacionoporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getPublicacionoporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setpublicacionoporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetpublicacionoporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetpublicacionoporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setpublicacionoporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getParticipacionInstitucionporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetParticipacionInstitucionporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetParticipacionInstitucionporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParticipacionInstitucionporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setparticipacionInstitucionporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetparticipacionInstitucionporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetparticipacionInstitucionporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParticipacionInstitucionporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getParticipacionProyeporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetParticipacionProyeporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetParticipacionProyeporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getParticipacionProyeporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#setparticipacionProyeporpersona(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSetparticipacionProyeporpersona() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testSetparticipacionProyeporpersona() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.setparticipacionProyeporpersona(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#eliminarHojaVida(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testEliminarHojaVida() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testEliminarHojaVida() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.eliminarHojaVida(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#geExperienciaDocenteBycodPer(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGeExperienciaDocenteBycodPer() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGeExperienciaDocenteBycodPer() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.geExperienciaDocenteBycodPer(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getdifDias(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetdifDias() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetdifDias() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getdifDias(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getExperenciaProPortipoEntidad(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetExperenciaProPortipoEntidad() {
		  LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetExperenciaProPortipoEntidad() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getExperenciaProPortipoEntidad(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

	/**
	 * Test method for {@link co.gov.dafp.sigep2.server.ServicesSigepRestHV#getEntidadporCodTipoEntidad(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetEntidadporCodTipoEntidad() {
		 LoggerSigep.getInstance().configureAppender();
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
		 LoggerSigep.getInstance().info("testGetEntidadporCodTipoEntidad() Start JunitTest", TestServiciosHV.class);
		 String json = ""; //-> imtroducir aqui el json a probar
		 LoggerSigep.getInstance().info("Parametros Json Enviados:-> "+json, TestServiciosHV.class);
		 json = new String(Base64.encodeBase64(json.getBytes()));
		 Response out = restService.getEntidadporCodTipoEntidad(req, json, TOKEN, "30","3");
		 LoggerSigep.getInstance().info("Respuesta Del Servicio:-> "+out.getEntity().toString(), TestServiciosHV.class);
		 LoggerSigep.getInstance().info("*******************************************************************************************************", TestServiciosHV.class);
	     assertTrue(out.getStatus() == 201);
	}

}
