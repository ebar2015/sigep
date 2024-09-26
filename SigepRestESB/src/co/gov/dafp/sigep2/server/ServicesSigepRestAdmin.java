/**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.Serializable;
import java.util.ArrayList;
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

import co.gov.dafp.sigep2.bean.Enfermedad;
import co.gov.dafp.sigep2.bean.EntidadSituacionAdministrativa;
import co.gov.dafp.sigep2.bean.Festivo;
import co.gov.dafp.sigep2.bean.HistoricoSituacionAdminVinculacion;
import co.gov.dafp.sigep2.bean.ModificacionSituacionAdministrativa;
import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.SituacionAdminVinculacion;
import co.gov.dafp.sigep2.bean.SituacionAdministrativa;
import co.gov.dafp.sigep2.bean.UsuarioEntidad;
import co.gov.dafp.sigep2.bean.UsuarioRolEntidad;
import co.gov.dafp.sigep2.bean.ext.EntidadSituacionAdministrativaExt;
import co.gov.dafp.sigep2.bean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.bean.ext.SituacionAdministrativaExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.constante.AuditoriaConstantes;
import co.gov.dafp.sigep2.services.AuditoriaService;
import co.gov.dafp.sigep2.services.EnfermedadService;
import co.gov.dafp.sigep2.services.EntidadSituacionAdministrativaService;
import co.gov.dafp.sigep2.services.FestivoService;
import co.gov.dafp.sigep2.services.HistoricoSituacionAdminVinculacionService;
import co.gov.dafp.sigep2.services.ModificacionSituacionAdministrativaService;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.SituacionAdminVinculacionService;
import co.gov.dafp.sigep2.services.SituacionAdministrativaService;
import co.gov.dafp.sigep2.services.UsuarioEntidadService;
import co.gov.dafp.sigep2.services.UsuarioRolEntidadService;
import co.gov.dafp.sigep2.services.UsuarioService;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.TipoParametro;
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
@Path("/sigepadmin")
public class ServicesSigepRestAdmin implements Serializable {

	private static final long serialVersionUID = 7466675903790073750L;
	private Gson gson;
	
	public ServicesSigepRestAdmin() {
		LoggerSigep.getInstance().configureAppender();
	}

	/**
	 * @param msg
	 * @return
	 */
	@GET()
	@Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@PathParam("msg") String msg) {
		return Response.status(201).entity("Hello: Services Context sigepadmin.. " + msg).build();
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
	@Path("/getsituacionadministrativafiltro")
	@Consumes("text/plain")
	public Response getSituacionAdministrativaFiltro(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		SituacionAdministrativa st = new SituacionAdministrativa();
		try {
			st = gson.fromJson(json, SituacionAdministrativa.class);
			SituacionAdministrativaService service = new SituacionAdministrativaService();
			List<SituacionAdministrativa> res = service.getSituacionAdministrativaFiltro(st);
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (Exception e) { LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			st.setError(true);
			st.setMensaje(UtilsConstantes.MSG_EXEPCION);
			st.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			return Response.status(201).entity("[" + gson.toJson(st) + "]").build();
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
	@Path("/getsituacionpadre")
	@Consumes("text/plain")
	public Response getSituacionPadre(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		SituacionAdministrativa st = new SituacionAdministrativa();
		try {
			st = gson.fromJson(json, SituacionAdministrativa.class);
			SituacionAdministrativaService service = new SituacionAdministrativaService();
			SituacionAdministrativa res = service.getSituacionAdministrativaPadre(st.getCodSituacionAdministrativa());
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (Exception e) { 
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			st.setError(true);
			st.setMensaje(UtilsConstantes.MSG_EXEPCION);
			st.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(st)).build();
		}
	}

	/**
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una SituacionAdminVinculacion y
	 *         tambien la actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setituacionadminvinculacion")
	@Consumes("text/plain")
	public Response setSituacionAdminVinculacion(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		SituacionAdminVinculacion res = new SituacionAdminVinculacion();
		try {
			SituacionAdminVinculacion situacionAdminVinculacion = gson.fromJson(json, SituacionAdminVinculacion.class);
			situacionAdminVinculacion.setAudFechaActualizacion(new Date());
			SituacionAdminVinculacionService service = new SituacionAdminVinculacionService();
			if (situacionAdminVinculacion.getCodSituacionAdminRelacionLaboral() != null
					&& situacionAdminVinculacion.getCodSituacionAdminRelacionLaboral() > 0) {
				situacionAdminVinculacion.setAudFechaActualizacion(new Date());
				res = service.updateSituacionAdminVinculacion(situacionAdminVinculacion);
			} else if (situacionAdminVinculacion.getCodVinculacion() != null
					&& situacionAdminVinculacion.getCodVinculacion().longValue() > 0) {
				res = service.insertSituacionAdminVinculacion(situacionAdminVinculacion);
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException | NullPointerException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	
	/**
	 * Metodo que actualiza
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una SituacionAdminVinculacion y
	 *         tambien la actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/updateSituacionAdminVinculacion")
	@Consumes("text/plain")
	public Response updateSituacionAdminVinculacion(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		SituacionAdminVinculacion res = new SituacionAdminVinculacion();
		try {
			SituacionAdminVinculacion situacionAdminVinculacion = gson.fromJson(json, SituacionAdminVinculacion.class);
			situacionAdminVinculacion.setAudFechaActualizacion(new Date());
			SituacionAdminVinculacionService service = new SituacionAdminVinculacionService();
			if (situacionAdminVinculacion.getCodSituacionAdminRelacionLaboral() != null
					&& situacionAdminVinculacion.getCodSituacionAdminRelacionLaboral() > 0) {
				situacionAdminVinculacion.setAudFechaActualizacion(new Date());
				res = service.updateSituacionAdminVinculacion(situacionAdminVinculacion);
			} 
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException | NullPointerException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	
	

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones por
	 *         Codigo de Declaracion.
	 */
	@POST
	@Path("/getsituacionvinculacion")
	@Consumes("text/plain")
	public Response getSituacionVinculacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SituacionAdminVinculacionExt user = gson.fromJson(json, SituacionAdminVinculacionExt.class);
			SituacionAdminVinculacionService service = new SituacionAdminVinculacionService();
			List<SituacionAdminVinculacionExt> d = service.getSituacionAdminVinculacion(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones por
	 *         Codigo de Declaracion.
	 */
	@POST
	@Path("/getenfemedadlike")
	@Consumes("text/plain")
	public Response getEnfemedadLike(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Enfermedad user = gson.fromJson(json, Enfermedad.class);
			EnfermedadService service = new EnfermedadService();
			List<Enfermedad> d = service.getEnfemedadLike(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
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
	@Path("/setmodificacionSituacionAdministrativa")
	@Consumes("text/plain")
	public Response setmodificacionSituacionAdministrativa(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		ModificacionSituacionAdministrativa res = new ModificacionSituacionAdministrativa();
		try {
			ModificacionSituacionAdministrativa situacionIn = gson.fromJson(json,
					ModificacionSituacionAdministrativa.class);
			ModificacionSituacionAdministrativaService service = new ModificacionSituacionAdministrativaService();
			if (situacionIn.getCodModificacion() != null && situacionIn.getCodModificacion() > 0) {
				res = service.updateModificacionSituacionAdministrativa(situacionIn);
			} else if (situacionIn.getCodSituacionAdministrativaVinculacion() != null
					&& situacionIn.getCodSituacionAdministrativaVinculacion().longValue() > 0) {
				res = service.insertModificacionSituacionAdministrativa(situacionIn);
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException | NullPointerException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones por
	 *         Codigo de Declaracion.
	 */
	@POST
	@Path("/getcantidaddiashabiles")
	@Consumes("text/plain")
	public Response getcantidaddiashabiles(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Festivo user = gson.fromJson(json, Festivo.class);
			FestivoService service = new FestivoService();
			int d = service.getDifFestivo(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones por
	 *         Codigo de Declaracion.
	 */
	@POST
	@Path("/getdiasferiados")
	@Consumes("text/plain")
	public Response getDiasFeriados(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Festivo user = gson.fromJson(json, Festivo.class);
			FestivoService service = new FestivoService();
			List<Festivo> d = service.getDiasFestivo(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones por
	 *         Codigo de Declaracion.
	 */
	@POST
	@Path("/getenfemedadcie")
	@Consumes("text/plain")
	public Response getEnfemedadcie(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Enfermedad user = gson.fromJson(json, Enfermedad.class);
			EnfermedadService service = new EnfermedadService();
			Enfermedad d = service.getEnfemedadcodCIE(user.getCodCie10());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una lista de
	 *         ModificacionSituacionAdministrativa por
	 *         CodSituacionAdministrativaVinculacion
	 */
	@POST
	@Path("/getmodificacionsituacionesadmin")
	@Consumes("text/plain")
	public Response getmodificacionsituacionesadmin(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ModificacionSituacionAdministrativa user = gson.fromJson(json, ModificacionSituacionAdministrativa.class);
			ModificacionSituacionAdministrativaService service = new ModificacionSituacionAdministrativaService();
			List<ModificacionSituacionAdministrativa> d = service
					.getModificacionesPorSA(user.getCodSituacionAdministrativaVinculacion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			ModificacionSituacionAdministrativa user = new ModificacionSituacionAdministrativa();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity("[" + gson.toJson(user) + "]").build();
		}
	}

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */

	@POST
	@Path("/setsituacionadministrativaparticular")
	@Consumes("text/plain")
	public Response setsituacionadministrativapartcular(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		SituacionAdministrativa res = new SituacionAdministrativa();
		try {
			SituacionAdministrativaExt situacionIn = gson.fromJson(json, SituacionAdministrativaExt.class);
			SituacionAdministrativaService service = new SituacionAdministrativaService();
			situacionIn.setAudFechaActualizacion(new Date());
			if (situacionIn.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				res = service.insertSituacionAdministrativaParticular(situacionIn);
			} else if (situacionIn.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) ||
					situacionIn.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())){
				res = service.updateSituacionAdministrativa(situacionIn);
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/sethistoricosituacionsdminsinculacion")
	@Consumes("text/plain")
	public Response setHistoricoSituacionAdminVinculacion(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		HistoricoSituacionAdminVinculacion res = new HistoricoSituacionAdminVinculacion();
		try {
			HistoricoSituacionAdminVinculacion situacionIn = gson.fromJson(json,
					HistoricoSituacionAdminVinculacion.class);
			HistoricoSituacionAdminVinculacionService service = new HistoricoSituacionAdminVinculacionService();
			if (situacionIn.getAudAccion() == 785) {
				res = service.insertHistoricoSituacionAdminVinculacion(situacionIn);
			} else if (situacionIn.getAudAccion() == 63) {
				res = service.updateHistoricoSituacionAdminVinculacion(situacionIn);
			} else {
				res.setError(true);
				res.setMensaje(UtilsConstantes.MSG_ERROR_FALTA_DATO_OBIGATORIO + " AudAccion()");
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una lista de HistoricoSituacionAdminVinculacion
	 */
	@POST
	@Path("/gethistoricosituacionsdminsinculacion")
	@Consumes("text/plain")
	public Response getHistoricoSituacionAdminVinculacion(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			HistoricoSituacionAdminVinculacion user = gson.fromJson(json, HistoricoSituacionAdminVinculacion.class);
			HistoricoSituacionAdminVinculacionService service = new HistoricoSituacionAdminVinculacionService();
			List<HistoricoSituacionAdminVinculacion> d = service.getHistoricoSituacionAdminVinculacionFiltro(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			HistoricoSituacionAdminVinculacion user = new HistoricoSituacionAdminVinculacion();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity("[" + gson.toJson(user) + "]").build();
		}
	}
	
	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de SituacionesAdministrativas por CodEntidad
	 */
	@POST
	@Path ( "/getsituacionparticularesporentidad" ) 
	@Consumes("text/plain")
	public Response selectsituacionparticularesporentidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SituacionAdministrativaExt user = gson.fromJson(json, SituacionAdministrativaExt.class);
			SituacionAdministrativaService service = new SituacionAdministrativaService();
			List<SituacionAdministrativaExt> d = service.getSituacionParticularesPorEntidad(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			SituacionAdministrativaExt user = new SituacionAdministrativaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity("["+gson.toJson(user)+"]").build();
		}
	}
	
	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de SituacionesAdministrativas Asignadas a un servidor
	 */
	@POST
	@Path ( "/getsituacionparticularasignada" )
	@Consumes("text/plain")
	public Response gettsituacionparticularesporentidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SituacionAdministrativaExt user = gson.fromJson(json, SituacionAdministrativaExt.class);
			SituacionAdministrativaService service = new SituacionAdministrativaService();
			List<SituacionAdministrativaExt> d = service.getSituacionParticularAsignada(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			SituacionAdministrativaExt user = new SituacionAdministrativaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity("["+gson.toJson(user)+"]").build();
		}
	}
	/**
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getnombreexistesituparticular")
	@Consumes("text/plain")
	public Response getnombreexistesituparticular(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SituacionAdministrativaExt user = gson.fromJson(json, SituacionAdministrativaExt.class);
			SituacionAdministrativaService service = new SituacionAdministrativaService();
			List<SituacionAdministrativa> d = service.getNombreSituacion(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			EntidadSituacionAdministrativa user = new EntidadSituacionAdministrativa();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getsituacionesaplicadas")
	@Consumes("text/plain")
	public Response getSituacionesAplicadas(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadSituacionAdministrativaExt user = gson.fromJson(json, EntidadSituacionAdministrativaExt.class);
			EntidadSituacionAdministrativaService service = new EntidadSituacionAdministrativaService();
			List<EntidadSituacionAdministrativaExt> d = service.getSituacionesAplicadas(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			EntidadSituacionAdministrativa user = new EntidadSituacionAdministrativa();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	
	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metido para retornar las parametricas por orden
	 */
	@POST
	@Path ( "/getparametricabyorden" ) 
	@Consumes("text/plain")
	public Response getParametricaByOrden(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Parametrica parametrica = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			List<Parametrica> list = service.getParametricaByOrden(parametrica);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			Parametrica user = new Parametrica();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<Parametrica> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   6 may. 2019, 17:17:22
	 * @File:   ServicesSigepRestAdmin.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setEnfermedad")
	@Consumes("text/plain")
	public Response setEnfermedad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Enfermedad enfermedad = gson.fromJson(json, Enfermedad.class);
			enfermedad.setAudFechaActualizacion(new Date());
			EnfermedadService service = new EnfermedadService();
			if(enfermedad.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				enfermedad = service.insertEnfermedad(enfermedad);
			}else if(enfermedad.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) ||
					enfermedad.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				enfermedad = service.updateEnfermedad(enfermedad);
			}else {
				enfermedad = new Enfermedad();
				enfermedad.setError(true);
				enfermedad.setMensaje(UtilsConstantes.MSG_OBLIGATORIOS);
			}
			String out = gson.toJson(enfermedad);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			return Response.status(201).entity("[]").build();
		}
	}
	/***
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   6 may. 2019, 17:22:51
	 * @File:   ServicesSigepRestAdmin.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getEnfermedadfiltro" ) 
	@Consumes("text/plain")
	public Response getEnfermedadfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			Enfermedad enfermedad = gson.fromJson(json, Enfermedad.class);
			EnfermedadService service = new EnfermedadService();
			List<Enfermedad> d = service.getEnfemedad(enfermedad);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.ENFERMEDAD, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			Enfermedad user = new Enfermedad();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<Enfermedad> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 8:15:46 AM
	 * @File:   ServicesSigepRestAdmin.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setsituacionadministra")
	@Consumes("text/plain")
	public Response setsituacionadministra(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SituacionAdministrativa situacionIn = gson.fromJson(json, SituacionAdministrativa.class);
			SituacionAdministrativaService service = new SituacionAdministrativaService();
			situacionIn.setAudFechaActualizacion(new Date());
			if (situacionIn.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				situacionIn = service.insertSituacionAdministrativa(situacionIn);
			} else if (situacionIn.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
					||situacionIn.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())){
				situacionIn = service.updateSituacionAdministrativa(situacionIn);
			}
			return Response.status(201).entity(gson.toJson(situacionIn)).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			SituacionAdministrativa res = new SituacionAdministrativa();
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	 /**
     * Elaborado por Maria Alejandra Colorado Rios
     * Servicio que llama al procedimiento SP_USUARIO_ROL_ENTIDAD_ASOCIAR_AF para la asociacion de un usuario funcional
     * a todas las entidades.
     * @fecha 09 de abril 2019
     * @param req
     * @param json
     * @param token
     */
	@POST
	@Path("/asociarrolAFaentidades")
	@Consumes("text/plain")
	public Response asociarRolAFTodasEntidades(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		UsuarioRolEntidad res = new UsuarioRolEntidad();
		try {
			UsuarioRolEntidad usuarioRolEntidad = gson.fromJson(json, UsuarioRolEntidad.class);
			UsuarioRolEntidadService service = new UsuarioRolEntidadService();
			res = service.asociarTodasEntidadAF(usuarioRolEntidad);
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return UsuarioEntidad
	 * Metodo para retornar los datos de usuarioEntidad por medio de codUsuario y codEntidad
	 */
	@POST
	@Path ( "/getUsuarioEntidad" ) 
	@Consumes("text/plain")
	public Response getUsuarioEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			UsuarioEntidad user = gson.fromJson(json, UsuarioEntidad.class);
			UsuarioEntidadService service = new UsuarioEntidadService();
			UsuarioEntidad d = service.getUsuarioEntidad(user);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			UsuarioEntidad user = new UsuarioEntidad();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), UsuarioService.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}

	 /**
     * Elaborado por Maria Alejandra Colorado Rios
     * Servicio que llama al procedimiento  SP_ASOCIAR_ROL_USUARIO . Este procedimiento se encarga de asociar un
     * usuario a una entidad dependiendo del rol enviado.
     * @fecha 26 de abril 2020
     * @param req
     * @param json
     * @param token
     */
	@POST
	@Path("/asociarRolAUsuario")
	@Consumes("text/plain")
	public Response asociarRolAUsuario(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		UsuarioRolEntidad res = new UsuarioRolEntidad();
		try {
			UsuarioRolEntidadExt usuarioRolEntidad = gson.fromJson(json, UsuarioRolEntidadExt.class);
			UsuarioRolEntidadService service = new UsuarioRolEntidadService();
			res = service.asociarRolAUsuario(usuarioRolEntidad);
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	

	 /**
    * Elaborado por Maria Alejandra Colorado Rios
    * Servicio que devuelve un objeto UsuarioRolEntidad de acuerdo al usuario, codigo de la entidad y rol enviado
    * @fecha 26 de abril 2020
    * @param req
    * @param json
    * @param token
    */
	@POST
    @Path("/getByUsuarioRolEntidad")
    @Consumes("text/plain")
    public Response getByUsuarioRolEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	UsuarioRolEntidad er =  new UsuarioRolEntidad();
    	try {
    		UsuarioRolEntidadExt usuarioRolEntidad = gson.fromJson(json, UsuarioRolEntidadExt.class);
    		UsuarioRolEntidadService service = new UsuarioRolEntidadService();
    		er = service.getByUsuarioRolEntidad(usuarioRolEntidad);
    		return Response.status(201).entity(gson.toJson(er)).build();
    	} catch (JsonParseException e) {
    		er = new UsuarioRolEntidad();
    		er.setError(true);
    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		er.setMensaje(e.getMessage());
    		return Response.status(201).entity(gson.toJson(er)).build();
    	}
    }
}