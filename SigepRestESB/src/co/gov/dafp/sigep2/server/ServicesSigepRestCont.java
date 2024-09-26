/**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.sun.jersey.core.util.Base64;

import co.gov.dafp.sigep2.bean.Contrato;
import co.gov.dafp.sigep2.bean.ModificacionContrato;
import co.gov.dafp.sigep2.bean.ext.ContratoExt;
import co.gov.dafp.sigep2.bean.ext.ModificacionContratoExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.constante.AuditoriaConstantes;
import co.gov.dafp.sigep2.services.AuditoriaService;
import co.gov.dafp.sigep2.services.ContratoService;
import co.gov.dafp.sigep2.services.ModificacionContratoService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya.
 * @version 1.0
 * @Class Clase que se encarga de gestionar servicios rest para el la aplicacon
 *        web
 * @Proyect SIGEPII
 * @Company ADA S.A
 * @Module exposicion de servicios Rest
 */
@Path("/sigepcont")
public class ServicesSigepRestCont implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 248002675592552668L;
	private Gson gson;

	public ServicesSigepRestCont() {
		LoggerSigep.getInstance().configureAppender();
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param msg
	 * @return
	 */
	@GET()
	@Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@Context HttpServletRequest req, @PathParam("msg") String msg) {
		return Response.status(201).entity("Hello: Services Context sigepcont... " + msg).build();
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setcontrato")
	@Consumes("text/plain")
	public Response setpersonaparentescopersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Contrato res = new Contrato();
		res.setError(true);
		res.setMensaje(
				"No se Encontro CodContrato() ni CodPersona()  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			Contrato contrato = gson.fromJson(json, Contrato.class);
			contrato.setAudFechaActualizacion(new Date());
			ContratoService service = new ContratoService();
			contrato.setAudFechaActualizacion(new Date());
			if (contrato.getCodContrato() != null) {
				res = service.updateContrato(contrato);
			} else if (contrato.getCodPersona() != null) {
				res = service.insertContrato(contrato);
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensajeTecnico(e.getMessage());
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getpersonaporentidadfiltro")
	@Consumes("text/plain")
	public Response getusuariosporentidadfiltro(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> d = service.getPersonaEntidadPorCodPeCodEntFiltro(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.CONTRATO, auditoriaSeguridad.getCodUsuario(),
					auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("{}").build();
		}

	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getpersonaporentidadfiltrof")
	@Consumes("text/plain")
	public Response getusuariosporentidadfiltroB(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> d = service.getPersonaEntidadPorCodPeCodEntFiltrof(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("{}").build();
		}

	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setmodificacioncontrato")
	@Consumes("text/plain")
	public Response setmodificacioncontrato(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		ModificacionContrato res = new ModificacionContrato();
		try {
			ModificacionContrato modificacionContrato = gson.fromJson(json, ModificacionContrato.class);
			modificacionContrato.setAudFechaActualizacion(new Date());
			ModificacionContratoService service = new ModificacionContratoService();
			try {
				if(modificacionContrato.getCodModificacionContrato() !=null && modificacionContrato.getCodModificacionContrato().longValue() > 0) {
					modificacionContrato.setAudFechaActualizacion(new Date());
					res = service.updateModificacionContrato(modificacionContrato);
				}else if(modificacionContrato.getCodContrato() != null && modificacionContrato.getCodContrato() > 0) {
					res = service.insertModificacionContrato(modificacionContrato);
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensaje(UtilsConstantes.MSG_EXEPCION);
				res.setMensajeTecnico(e.getMessage());
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	
	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/selectMontoContratoInicial")
	@Consumes("text/plain")
	public Response selectMontoContratoInicial(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}	
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		//ModificacionContrato res = new ModificacionContrato();
		try {
			ModificacionContrato modificacionContrato = gson.fromJson(json, ModificacionContrato.class);
			ModificacionContratoService service = new ModificacionContratoService();
			List<ModificacionContratoExt>  res = service.selectMontoContratoInicial(modificacionContrato.getCodNuevoContratista());	
			return Response.status(201).entity(gson.toJson(res)).build();			
		} catch (JsonParseException e) {
			return Response.status(201).entity("{}").build();
		}
	}
	
	
	
	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getmodificacioncontrato")
	@Consumes("text/plain")
	public Response getmodificacioncontrato(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			ModificacionContrato user = gson.fromJson(json, ModificacionContrato.class);
			ModificacionContratoService service = new ModificacionContratoService();
			List<ModificacionContratoExt> d = service.getModificacionContrato(user.getCodContrato());
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.MODIFICACION_CONTRATO,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("{}").build();
		}

	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getmodificacioncontratoportipomod")
	@Consumes("text/plain")
	public Response getmodificacioncontratoportipomod(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			ModificacionContrato user = gson.fromJson(json, ModificacionContrato.class);
			ModificacionContratoService service = new ModificacionContratoService();
			List<ModificacionContratoExt> d = service.getModificacionContratoPorTipoMod(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.MODIFICACION_CONTRATO,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("{}").build();
		}

	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getcontratopersona")
	@Consumes("text/plain")
	public Response getContratoPersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			Contrato user = gson.fromJson(json, Contrato.class);
			ContratoService service = new ContratoService();
			List<ContratoExt> d = service.getContratoPersona(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.CONTRATO, auditoriaSeguridad.getCodUsuario(),
					auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("[]").build();
		}

	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getcontratofiltro")
	@Consumes("text/plain")
	public Response getContratoFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Contrato user = gson.fromJson(json, Contrato.class);
			ContratoService service = new ContratoService();
			List<ContratoExt> d = service.getContratoFiltro(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("[]").build();
		}

	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getcontratofechamenorfin")
	@Consumes("text/plain")
	public Response getContratoPersonaFecha(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Contrato user = gson.fromJson(json, Contrato.class);
			ContratoService service = new ContratoService();
			List<ContratoExt> d = service.getContratoPersonaFecha(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("[]").build();
		}

	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getcontratoentredias")
	@Consumes("text/plain")
	public Response getContratoEntreDias(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ContratoExt user = gson.fromJson(json, ContratoExt.class);
			ContratoService service = new ContratoService();
			List<ContratoExt> d = service.getContratoEntreDias(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("[]").build();
		}

	}

}
