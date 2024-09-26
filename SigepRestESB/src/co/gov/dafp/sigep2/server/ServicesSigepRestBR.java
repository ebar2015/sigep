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

import co.gov.dafp.sigep2.bean.AcreenciaObligacion;
import co.gov.dafp.sigep2.bean.ActividadPrivada;
import co.gov.dafp.sigep2.bean.BienesPatrimonio;
import co.gov.dafp.sigep2.bean.CuentasDeclaracion;
import co.gov.dafp.sigep2.bean.DatoContacto;
import co.gov.dafp.sigep2.bean.DatoContactoBr;
import co.gov.dafp.sigep2.bean.Declaracion;
import co.gov.dafp.sigep2.bean.HistoricoEntidadesDeclaracion;
import co.gov.dafp.sigep2.bean.IngresosDeclaracion;
import co.gov.dafp.sigep2.bean.OtrosIngresosDeclaracion;
import co.gov.dafp.sigep2.bean.ParticipacionJunta;
import co.gov.dafp.sigep2.bean.PersonaParentesco;
import co.gov.dafp.sigep2.bean.ext.ActividadPrivadaExt;
import co.gov.dafp.sigep2.bean.ext.BienesPatrimonioExt;
import co.gov.dafp.sigep2.bean.ext.CuentasDeclaracionExt;
import co.gov.dafp.sigep2.bean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.bean.ext.DeclaracionExt;
import co.gov.dafp.sigep2.bean.ext.IngresosDeclaracionExt;
import co.gov.dafp.sigep2.bean.ext.OtrosIngresosDeclaracionExt;
import co.gov.dafp.sigep2.bean.ext.ParticipacionJuntaExt;
import co.gov.dafp.sigep2.bean.ext.PersonaParentescoExt;
import co.gov.dafp.sigep2.constante.AuditoriaConstantes;
import co.gov.dafp.sigep2.services.AcreenciaObligacionService;
import co.gov.dafp.sigep2.services.ActividadPrivadaService;
import co.gov.dafp.sigep2.services.AuditoriaService;
import co.gov.dafp.sigep2.services.BienesPatrimonioService;
import co.gov.dafp.sigep2.services.CuentasDeclaracionService;
import co.gov.dafp.sigep2.services.DatoContactoService;
import co.gov.dafp.sigep2.services.DatosContactoBrService;
import co.gov.dafp.sigep2.services.DeclaracionService;
import co.gov.dafp.sigep2.services.HistoricoEntidadesDeclaracionService;
import co.gov.dafp.sigep2.services.IngresosDeclaracionService;
import co.gov.dafp.sigep2.services.OtrosIngresosDeclaracionService;
import co.gov.dafp.sigep2.services.ParticipacionJuntaService;
import co.gov.dafp.sigep2.services.PersonaParentescoService;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.TipoParametro;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar servicios rest para el la aplicacon web
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
@Path("/sigepbr")
public class ServicesSigepRestBR implements Serializable{
	private Gson gson;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4679946001937423708L;
	
	public ServicesSigepRestBR() {
		LoggerSigep.getInstance().configureAppender();
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param msg
	 * @return
	 * Metodo para hacer prueba de confirmacion que el servidor se encuentra en linea y respondiendo,
	 * solo hace un echo al mensage que recibe por Get
	 */
	@GET()
    @Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@Context HttpServletRequest req, @PathParam("msg") String msg) {    	
		return Response.status(201).entity("Hello: Services Context sigepbr... "+msg).build();	
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que crea en base de datos una acreditacion y tambien la actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST 
	@Path ( "/setacreenciaobligacion" ) 
	@Consumes("text/plain")
	public Response setAcreenciaObligacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		AcreenciaObligacion res = new AcreenciaObligacion();
		try {
			AcreenciaObligacion acreenciaObligacion = gson.fromJson(json, AcreenciaObligacion.class);
			acreenciaObligacion.setAudFechaActualizacion(new Date());
			AcreenciaObligacionService service = new AcreenciaObligacionService();
			try {
				if(acreenciaObligacion.getCodAcreenciaObligacion() !=null && acreenciaObligacion.getCodAcreenciaObligacion() > 0) {
					acreenciaObligacion.setAudFechaActualizacion(new Date());
					res = service.updateAcreenciaObligacion(acreenciaObligacion);
				}else if(acreenciaObligacion.getCodDeclaracion() != null && acreenciaObligacion.getCodDeclaracion().longValue() > 0) {
					res = service.insertAcreenciaObligacion(acreenciaObligacion);
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
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de Acreditaciones u Obligaciones por Codigo de Declaracion.
	 */
	@POST
	@Path ( "/getacreenciaobligacion" ) 
	@Consumes("text/plain")
	public Response getacreenciaobligacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AcreenciaObligacion user = gson.fromJson(json, AcreenciaObligacion.class);
			AcreenciaObligacionService service = new AcreenciaObligacionService();
			List<AcreenciaObligacion> d = service.getAcreenciaObligacionCodDeclaracion(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicios que retorna un alista de Actividades Privadas por Codigo de Declaracion
	 */
	@POST
	@Path ( "/getactividadprivada" ) 
	@Consumes("text/plain")
	public Response getActividadPrivada(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ActividadPrivadaExt user = gson.fromJson(json, ActividadPrivadaExt.class);
			ActividadPrivadaService service = new ActividadPrivadaService();
			List<ActividadPrivadaExt> d = service.getActividadPrivadaCodDeclaracion(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla actividad privada dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/setactividadprivada" ) 
	@Consumes("text/plain")
	public Response setActividadPrivada(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		ActividadPrivada res = new ActividadPrivada();
		res.setError(true);
		res.setMensaje("No se Encontro CodActividadPrivada() ni CodDeclaracion  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			ActividadPrivada acreenciaObligacion = gson.fromJson(json, ActividadPrivada.class);
			acreenciaObligacion.setAudFechaActualizacion(new Date());
			ActividadPrivadaService service = new ActividadPrivadaService();
			try {
				if(acreenciaObligacion.getCodActividadPrivada() !=null && acreenciaObligacion.getCodActividadPrivada() > 0) {
					acreenciaObligacion.setAudFechaActualizacion(new Date());
					res = service.updateActividadPrivada(acreenciaObligacion);
				}else if(acreenciaObligacion.getCodDeclaracion() != null && acreenciaObligacion.getCodDeclaracion().longValue() > 0) {
					res = service.insertActividadPrivada(acreenciaObligacion);
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensaje(UtilsConstantes.MSG_EXEPCION);
				res.setMensajeTecnico(e.getMessage());
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
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de bienes patrimoniales por codigo de declaracion
	 */
	@POST
	@Path ( "/getbienespatrimonio" ) 
	@Consumes("text/plain")
	public Response getBienesPatrimonio(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			BienesPatrimonio user = gson.fromJson(json, BienesPatrimonio.class);
			BienesPatrimonioService service = new BienesPatrimonioService();
			List<BienesPatrimonioExt> d = service.getBienesPatrimonioCodDeclaracion(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla bienes patrimonio dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/setbienespatrimonio" ) 
	@Consumes("text/plain")
	public Response setBienesPatrimonio(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		BienesPatrimonio res = new BienesPatrimonio();
		try {
			BienesPatrimonio bienesPatrimonio = gson.fromJson(json, BienesPatrimonio.class);
			bienesPatrimonio.setAudFechaActualizacion(new Date());
			BienesPatrimonioService service = new BienesPatrimonioService();
			if(bienesPatrimonio.getCodBienPatrimonio() !=null) {
				bienesPatrimonio.setAudFechaActualizacion(new Date());
				res = service.updateBienesPatrimonio(bienesPatrimonio);
			}else if(bienesPatrimonio.getCodDeclaracion() != null) {
				res = service.insertBienesPatrimonio(bienesPatrimonio);
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
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de cuentas declaracion por codigo de declaracion
	 */
	@POST
	@Path ( "/getcuentasdeclaracion" ) 
	@Consumes("text/plain")
	public Response getCuentasDeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CuentasDeclaracion user = gson.fromJson(json, CuentasDeclaracion.class);
			CuentasDeclaracionService service = new CuentasDeclaracionService();
			List<CuentasDeclaracionExt> d = service.getCuentasDeclaracionCodDeclaracion(user );
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla cuentas declaracion dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/setcuentasdeclaracion" ) 
	@Consumes("text/plain")
	public Response setCuentasDeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CuentasDeclaracion res = new CuentasDeclaracion();
		res.setError(true);
		res.setMensaje("No se Encontro CodBienPatrimonio() ni CodDeclaracion  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			CuentasDeclaracion cuentasDeclaracion = gson.fromJson(json, CuentasDeclaracion.class);
			cuentasDeclaracion.setAudFechaActualizacion(new Date());
			CuentasDeclaracionService service = new CuentasDeclaracionService();
			try {
				if(cuentasDeclaracion.getCodCuentaDeclaracion() !=null && cuentasDeclaracion.getCodCuentaDeclaracion() > 0) {
					cuentasDeclaracion.setAudFechaActualizacion(new Date());
					res = service.updateCuentasDeclaracion(cuentasDeclaracion);
				}else if(cuentasDeclaracion.getCodDeclaracion() != null && cuentasDeclaracion.getCodDeclaracion().longValue() > 0) {
					res = service.insertCuentasDeclaracion(cuentasDeclaracion);
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
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getdeclaracionpersona" ) 
	@Consumes("text/plain")
	public Response getDeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Declaracion user = gson.fromJson(json, Declaracion.class);
			DeclaracionService service = new DeclaracionService();
			List<Declaracion> d = service.getDeclaracionPersona(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getdeclaracionid" ) 
	@Consumes("text/plain")
	public Response getDeclaracionId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Declaracion user = gson.fromJson(json, Declaracion.class);
			DeclaracionService service = new DeclaracionService();
		    DeclaracionExt d = service.getDeclaracionById(user.getCodDeclaracion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla declaracion dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/setdeclaracion" ) 
	@Consumes("text/plain")
	public Response setDeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		Declaracion res = new Declaracion();
		res.setError(true);
		res.setMensaje("No se Encontro CodDeclaracion ni CodPersona  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			Declaracion declaracion = gson.fromJson(json, Declaracion.class);
			declaracion.setAudFechaActualizacion(new Date());
			DeclaracionService service = new DeclaracionService();
			try {
				if(declaracion.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) ||
						declaracion.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					declaracion.setAudFechaActualizacion(new Date());
					res = service.updateDeclaracion(declaracion);
				}else if(declaracion.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertDeclaracion(declaracion);
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensaje(UtilsConstantes.MSG_EXEPCION);
				res.setMensajeTecnico(e.getMessage());
			}
			gson = new Gson();
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de otros ingresos por codigo de la declaracion
	 */
	@POST
	@Path ( "/getotrosingresosdeclaracion" ) 
	@Consumes("text/plain")
	public Response getOtrosIngresosDeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			OtrosIngresosDeclaracion user = gson.fromJson(json, OtrosIngresosDeclaracion.class);
			OtrosIngresosDeclaracionService service = new OtrosIngresosDeclaracionService();
			List<OtrosIngresosDeclaracionExt> d = service.getOtrosIngresosDeclaracion(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla otros ingresos dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/setotrosingresosdeclaracion" ) 
	@Consumes("text/plain")
	public Response setOtrosIngresosDeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		OtrosIngresosDeclaracion res = new OtrosIngresosDeclaracion();
		res.setError(true);
		res.setMensaje("No se Encontro CodOtrosIngresosDeclaracion ni CodDeclaracion  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			OtrosIngresosDeclaracion otrosIngresosDeclaracion = gson.fromJson(json, OtrosIngresosDeclaracion.class);
			otrosIngresosDeclaracion.setAudFechaActualizacion(new Date());
			OtrosIngresosDeclaracionService service = new OtrosIngresosDeclaracionService();
			boolean r = false;
			try {
				if(otrosIngresosDeclaracion.getCodOtrosIngresosDeclaracion() !=null && otrosIngresosDeclaracion.getCodOtrosIngresosDeclaracion().longValue() > 0) {
					otrosIngresosDeclaracion.setAudFechaActualizacion(new Date());
					r = service.updateOtrosIngresosDeclaracion(otrosIngresosDeclaracion);
					res.setError((r)?false:true);
					res.setMensaje((r)?"Actualizacion Exitosa":"Fallo la Actualizacion");
				}else if(otrosIngresosDeclaracion.getCodDeclaracion() != null && otrosIngresosDeclaracion.getCodDeclaracion().longValue() > 0) {
					r = service.insertOtrosIngresosDeclaracion(otrosIngresosDeclaracion);
					res.setError((r)?false:true);
					res.setMensaje((r)?"Insercion Exitosa":"Fallo la Insercion");
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensajeTecnico(e.getMessage());
			}
			gson = new Gson();
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensajeTecnico(e.getMessage());
			res.setMensaje("Error Al comunicarse a la Base de Datos");
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de participacion junta por codigo de la declaracion
	 */
	@POST
	@Path ( "/getparticipacionjunta" ) 
	@Consumes("text/plain")
	public Response getParticipacionJunta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ParticipacionJunta user = gson.fromJson(json, ParticipacionJunta.class);
			ParticipacionJuntaService service = new ParticipacionJuntaService();
			List<ParticipacionJuntaExt> d = service.getParticipacionJuntaCodDeclaracion(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla participacion junta dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/setparticipacionjunta" ) 
	@Consumes("text/plain")
	public Response setParticipacionJunta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		ParticipacionJunta res = new ParticipacionJunta();
		res.setError(true);
		res.setMensaje("No se Encontro CodParticipacionJunta ni CodDeclaracion  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			ParticipacionJunta participacionJunta = gson.fromJson(json, ParticipacionJunta.class);
			participacionJunta.setAudFechaActualizacion(new Date());
			ParticipacionJuntaService service = new ParticipacionJuntaService();
			boolean r = false;
			try {
				if(participacionJunta.getCodParticipacionJunta() !=null && participacionJunta.getCodParticipacionJunta().longValue() > 0) {
					participacionJunta.setAudFechaActualizacion(new Date());
					r = service.updateParticipacionJunta(participacionJunta);
					res.setError((r)?false:true);
					res.setMensaje((r)?"Actualizacion Exitosa":"Fallo la Actualizacion");
				}else if(participacionJunta.getCodDeclaracion() != null && participacionJunta.getCodDeclaracion().longValue() > 0) {
					r = service.insertParticipacionJunta(participacionJunta);
					res.setError((r)?false:true);
					res.setMensaje((r)?"Insercion Exitosa":"Fallo la Insercion");
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensajeTecnico(e.getMessage());
			}
			gson = new Gson();
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensajeTecnico(e.getMessage());
			res.setMensaje("Error Al comunicarse a la Base de Datos");
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de personaparentesco por codigo de la declaracion
	 */
	@POST
	@Path ( "/getpersonaparentescopersona" ) 
	@Consumes("text/plain")
	public Response getpersonaparentescopersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaParentesco user = gson.fromJson(json, PersonaParentesco.class);
			PersonaParentescoService service = new PersonaParentescoService();
			List<PersonaParentescoExt> d = service.getPersonaParentescoPersona(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla persona parentesco dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/setpersonaparentescopersona" ) 
	@Consumes("text/plain")
	public Response setpersonaparentescopersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		PersonaParentesco res = new PersonaParentesco();
		res.setError(true);
		res.setMensaje("No se Encontro CodPersonaParentesco ni CodPersona  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			PersonaParentesco personaParentesco = gson.fromJson(json, PersonaParentesco.class);
			personaParentesco.setAudFechaActualizacion(new Date());
			PersonaParentescoService service = new PersonaParentescoService();
			boolean r = false;
			try {
				if(personaParentesco.getCodPersonaParentesco() !=null && personaParentesco.getCodPersonaParentesco().longValue() > 0) {
					personaParentesco.setAudFechaActualizacion(new Date());
					r = service.updatePersonaParentesco(personaParentesco);
					res.setError((r)?false:true);
					res.setMensaje((r)?"Actualizacion Exitosa":"Fallo la Actualizacion");
				}else if(personaParentesco.getCodPersona() != null && personaParentesco.getCodPersona().longValue() > 0) {
					r = service.insertPersonaParentesco(personaParentesco);
					res.setError((r)?false:true);
					res.setMensaje((r)?"Insercion Exitosa":"Fallo la Insercion");
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensajeTecnico(e.getMessage());
			}
			gson = new Gson();
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensajeTecnico(e.getMessage());
			res.setMensaje("Error Al comunicarse a la Base de Datos");
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla bienes patrimonio dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/delbienespatrimonio" ) 
	@Consumes("text/plain")
	public Response delBienesPatrimonio(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		BienesPatrimonio res = new BienesPatrimonio();
		try {
			BienesPatrimonio acreenciaObligacion = gson.fromJson(json, BienesPatrimonio.class);
			acreenciaObligacion.setAudFechaActualizacion(new Date());
			BienesPatrimonioService service = new BienesPatrimonioService();
			if(acreenciaObligacion.getCodBienPatrimonio() !=null) {
				res = service.delBienesPatrimonio(acreenciaObligacion.getCodBienPatrimonio());
			}else {
				res.setError(true);
				res.setMensaje("No se Encuantra el atributo CodBienPatrimonio()");
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
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de IngresosDeclaracion por codigo de la declaracion
	 */
	@POST
	@Path ( "/getingresosdeclaracion" ) 
	@Consumes("text/plain")
	public Response getingresosdeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			IngresosDeclaracion user = gson.fromJson(json, IngresosDeclaracion.class);
			IngresosDeclaracionService service = new IngresosDeclaracionService();
			List<IngresosDeclaracionExt> d = service.getIngresosDeclaracionCodDeclaracion(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	/**
	 * 
	 * Creado Por: Jose viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @param auditJson
	 * @return
	 * 21 oct. 2019 14:38:26
	 * ServicesSigepRestBR.java
	 */
	@POST
	@Path ( "/getTotalIngresos" ) 
	@Consumes("text/plain")
	public Response gettotalIngresos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			IngresosDeclaracion user = gson.fromJson(json, IngresosDeclaracion.class);
			IngresosDeclaracionService service = new IngresosDeclaracionService();
			IngresosDeclaracion d = service.gettotalIngresos(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla IngresosDeclaracion  dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/setingresosdeclaracion" ) 
	@Consumes("text/plain")
	public Response setingresosdeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		IngresosDeclaracion res = new IngresosDeclaracion();
		try {
			IngresosDeclaracion ingresosDeclaracion = gson.fromJson(json, IngresosDeclaracion.class);
			ingresosDeclaracion.setAudFechaActualizacion(new Date());
			IngresosDeclaracionService service = new IngresosDeclaracionService();
				if(ingresosDeclaracion.getCodIngresoDeclaracion() !=null) {
					ingresosDeclaracion.setAudFechaActualizacion(new Date());
					res = service.updateIngresosDeclaracion(ingresosDeclaracion);
				}else if(ingresosDeclaracion.getCodDeclaracion() != null) {
					res = service.insertIngresosDeclaracion(ingresosDeclaracion);
				}else {
					res.setError(true);
					res.setMensaje("Falta el Atributo CodIngresoDeclaracion");
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
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getdeclaracionpersonatipo" ) 
	@Consumes("text/plain")
	public Response getDeclaracionPorTipo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Declaracion user = gson.fromJson(json, Declaracion.class);
			DeclaracionService service = new DeclaracionService();
			List<Declaracion> d = service.getDeclaracionPersonaTipo(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de IngresosDeclaracion por codigo de la declaracion
	 */
	@POST
	@Path ( "/getingresosdeclaracionid" ) 
	@Consumes("text/plain")
	public Response getingresosdeclaracionid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			IngresosDeclaracion user = gson.fromJson(json, IngresosDeclaracion.class);
			IngresosDeclaracionService service = new IngresosDeclaracionService();
			IngresosDeclaracionExt d = service.getIngresosDeclaracionById(user.getCodIngresoDeclaracion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getdeclaracionunica" ) 
	@Consumes("text/plain")
	public Response getDeclaracionUnica(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			Declaracion user = gson.fromJson(json, Declaracion.class);
			DeclaracionService service = new DeclaracionService();
			Declaracion d = service.getDeclaracionUltima(user.getCodPersona());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getdeclaracionfiltro") 
	@Consumes("text/plain")
	public Response getDeclaracionfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			DeclaracionExt user = gson.fromJson(json, DeclaracionExt.class);
			DeclaracionService service = new DeclaracionService();
			List<DeclaracionExt> d = service.getDeclaracionfiltro(user);
			String out = gson.toJson(d);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.DECLARACION, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getdeclaracioncreacionn" ) 
	@Consumes("text/plain")
	public Response getDeclaracionCreacionn(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Declaracion user = gson.fromJson(json, Declaracion.class);
			DeclaracionService service = new DeclaracionService();
			List<Declaracion> d = service.getDeclaracionCreacionn(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una total declaracion por codigoDeclaracion
	 */
	@POST
	@Path ( "/gettotaldeclaracion" ) 
	@Consumes("text/plain")
	public Response getTotalDeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Declaracion user = gson.fromJson(json, Declaracion.class);
			DeclaracionService service = new DeclaracionService();
			DeclaracionExt d = service.getTotalDeclaracion(user.getCodDeclaracion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("{}").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getsumaingresos" ) 
	@Consumes("text/plain")
	public Response getsumaingresos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			IngresosDeclaracion user = gson.fromJson(json, IngresosDeclaracion.class);
			IngresosDeclaracionService service = new IngresosDeclaracionService();
			IngresosDeclaracionExt d = service.getSumaIngresos(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<IngresosDeclaracionExt> d = new ArrayList<>();
			IngresosDeclaracionExt user = new IngresosDeclaracionExt();
			user.setError(true);
			user.setMensajeTecnico(e.getMessage());
			d.add(user);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que inserta o actualiza un registro en la tabla declaracion dependoento de los Id que envie el cliente Rest
	 */
	@POST
	@Path ( "/updatedeclaracionfiltro" ) 
	@Consumes("text/plain")
	public Response updatedeclaracionfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Declaracion res = new Declaracion();
		res.setError(true);
		res.setMensaje("No se Encontro CodDeclaracion servicio no puede realizar Update verifique Informacion");
		try {
			Declaracion declaracion = gson.fromJson(json, Declaracion.class);
			declaracion.setAudFechaActualizacion(new Date());
			DeclaracionService service = new DeclaracionService();
			try {
				if(declaracion.getCodDeclaracion() !=null && declaracion.getCodDeclaracion().longValue() > 0) {
					declaracion.setAudFechaActualizacion(new Date());
					res = service.updateDeclaracionSelective(declaracion);
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensaje(UtilsConstantes.MSG_EXEPCION);
				res.setMensajeTecnico(e.getMessage());
			}
			gson = new Gson();
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getdeclaracioncarga" ) 
	@Consumes("text/plain")
	public Response getDeclaracionCarga(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Declaracion user = gson.fromJson(json, Declaracion.class);
			DeclaracionService service = new DeclaracionService();
			Declaracion d = service.getDeclaracionCarga(user.getCodPersona());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getsumaotrosingresos" ) 
	@Consumes("text/plain")
	public Response getsumaotrosingresos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			OtrosIngresosDeclaracion user = gson.fromJson(json, OtrosIngresosDeclaracion.class);
			OtrosIngresosDeclaracionService service = new OtrosIngresosDeclaracionService();
			OtrosIngresosDeclaracion d = service.getSumaOtrosIngresos(user.getCodDeclaracion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<IngresosDeclaracionExt> d = new ArrayList<>();
			IngresosDeclaracionExt user = new IngresosDeclaracionExt();
			user.setError(true);
			user.setMensajeTecnico(e.getMessage());
			d.add(user);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}
	
	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicios que retorna un alista de Actividades Privadas por Codigo de Declaracion
	 */
	@POST
	@Path ( "/getdeclaracionfinalizada" ) 
	@Consumes("text/plain")
	public Response getdeclaracionfinalizada(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Declaracion user = gson.fromJson(json, Declaracion.class);
			DeclaracionService service = new DeclaracionService();
			List<DeclaracionExt> d = service.getDeclaracionesParaExt(user.getCodPersona());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
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
	@Path("/getdatocontactoporpersona")
	@Consumes("text/plain")
	public Response getDatoContactoPorIdPersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			DatoContactoExt user = gson.fromJson(json, DatoContactoExt.class);
			DatoContactoExt userR = new DatoContactoExt();
			DatosContactoBrService serviceBr = new DatosContactoBrService();
			if (user.getCodPersona() != null) {
				if(user.getCodDeclaracion() != null) {
					userR = serviceBr.getDatosContactoBrPorIdPersona(user);
					userR.setCodDeclaracion(user.getCodDeclaracion());
					if(userR.getCodPersona() != null) {
						userR.setError(false);
						String out = gson.toJson(userR);
						return Response.status(201).entity(out).build();
					}else {
						DatoContactoService service = new DatoContactoService();
						userR = service.getDatoContactoPorIdPersona(user.getCodPersona().longValue());
						if(userR.getCodPersona() != null) {
							DatoContactoBr data = new DatoContactoBr();
							userR.setCodDeclaracion(user.getCodDeclaracion());
							userR.setFlgActivo((short) 1);
							userR.setDefinitivo((short) 0);
							data = serviceBr.insertDatosContactoBr(userR);
							if(!data.isError()) {
								userR = serviceBr.getDatosContactoBrPorIdPersona(userR);
							}
						}
						userR.setCodDeclaracion(user.getCodDeclaracion());
						userR.setError(false);
						String out = gson.toJson(userR);
						return Response.status(201).entity(out).build();
					}
				}else {
					DatoContacto us = new DatoContacto();
					us.setError(true);
					us.setMensaje("Falta CodDeclaracion");
					return Response.status(201).entity(gson.toJson(us)).build();
				}
			} else {
				DatoContacto us = new DatoContacto();
				us.setError(true);
				us.setMensaje("Falta CodPersona");
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			DatoContacto us = new DatoContacto();
			us.setError(true);
			us.setMensaje("Error Al Serializar Json");
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
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
	@Path("/setdatocontactoporpersona")
	@Consumes("text/plain")
	public Response setDatoContactoPorIdPersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DatoContactoBr us = new DatoContactoBr();
		try {
			DatoContactoExt user = gson.fromJson(json, DatoContactoExt.class);
			user.setAudFechaActualizacion(new Date());
			DatosContactoBrService service = new DatosContactoBrService();
			us = service.updateDatosContactoBr(user);
			if(!us.isError() && user.getDefinitivo() != null) {
				if(user.getDefinitivo() == 1) {
					DatoContactoService serviceD = new DatoContactoService();
					DatoContactoExt data = serviceD.getDatoContactoPorIdPersona(user.getCodPersona().longValue());
					if(data.getCodDatosContacto()!=null) {
						user.setCodDatosContacto(data.getCodDatosContacto());
						serviceD.updateDatoContactoSelective(user);
					}else {
						serviceD.insertDatoContacto(user);
					}
				}
			}
			return Response.status(201).entity(gson.toJson(us)).build();
		} catch (JsonParseException e) {
			us.setError(true);
			us.setMensaje(UtilsConstantes.MSG_EXEPCION);
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
		}
	}
	
	/**
	 * Servicio que almacena un nuevo registro o modifica uno existente en el maestro HISTORICO_ENTIDADES_DECLARACION
	 * @File: ServicesSigepRestBR.java
	 * @Date: Septiembre 08, 2020
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return HistoricoEntidadesDeclaracion
	 */
	@POST
	@Path("/setHistoricoEntidadesDeclaracion")
	@Consumes("text/plain")
	public Response setHistoricoEntidadesDeclaracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		HistoricoEntidadesDeclaracionService service = new HistoricoEntidadesDeclaracionService();
		HistoricoEntidadesDeclaracion res = new HistoricoEntidadesDeclaracion();
		try {
			HistoricoEntidadesDeclaracion historicoEntidadesDeclaracion = gson.fromJson(json, HistoricoEntidadesDeclaracion.class);
			try {
				if(historicoEntidadesDeclaracion.getCodDeclaracionEntidad() == null) {
					res = service.insertHistoricoEntidadesDeclaracion(historicoEntidadesDeclaracion);
				}else {
					res = service.updateHistoricoEntidadesDeclaracion(historicoEntidadesDeclaracion);
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensaje(UtilsConstantes.MSG_OBLIGATORIOS);
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestBR.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * Servicio que retorna una lista de registros pertenecientes al maestro HISTORICO_ENTIDADES_DECLARACION, dependiendo del filtro enviado
	 * @Author: Maria Alejandra
	 * @Date: Septiembre 08, 2020
	 * @File: ServicesSigepRestBR.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return List<HistoricoEntidadesDeclaracion>
	 */
	@POST
	@Path("/geHistoricoEntidadesDeclaracionFiltro")
	@Consumes("text/plain")
	public Response geHistoricoEntidadesDeclaracionFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
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
			HistoricoEntidadesDeclaracion historicoDeclaracion = gson.fromJson(json, HistoricoEntidadesDeclaracion.class);
			HistoricoEntidadesDeclaracionService service = new HistoricoEntidadesDeclaracionService();
			List<HistoricoEntidadesDeclaracion> d = service.geHistoricoEntidadesDeclaracionFiltro(historicoDeclaracion);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.HISTORICO_ENTIDADES_DECLARACION, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			out = out.replace("src", "");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestBR.class);
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * Servicio que retorna un registro pertenecientes al maestro HISTORICO_ENTIDADES_DECLARACION, dependiendo de la llave primaria enviada
	 * @Author: Maria Alejandra
	 * @Date: Septiembre 08, 2020
	 * @File: ServicesSigepRestBR.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return HistoricoEntidadesDeclaracion
	 */
	@POST
	@Path("/getHistoricoEntidadesDeclaracionById")
	@Consumes("text/plain")
	public Response getHistoricoEntidadesDeclaracionById(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		HistoricoEntidadesDeclaracion err = new HistoricoEntidadesDeclaracion();
		try {
			HistoricoEntidadesDeclaracion historico = gson.fromJson(json, HistoricoEntidadesDeclaracion.class);
			HistoricoEntidadesDeclaracionService service = new HistoricoEntidadesDeclaracionService();
			err = service.HistoricoEntidadesDeclaracionById(historico);
			return Response.status(201).entity(gson.toJson(err)).build();
		} catch (JsonParseException e) {
			HistoricoEntidadesDeclaracion er = new HistoricoEntidadesDeclaracion();
			er.setError(true);
			er.setMensaje(UtilsConstantes.MSG_EXEPCION);
			er.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(er)).build();
		}
	}
	

	/**
	 * @author Jose Viscaya.
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Servicio que retorna una lista de declaracion por codigo de la persona
	 */
	@POST
	@Path ( "/getDatoContactoExt" ) 
	@Consumes("text/plain")
	public Response getDatoContactoExt(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			DatoContactoExt objDatoContacto = gson.fromJson(json, DatoContactoExt.class);
			DatosContactoBrService service = new DatosContactoBrService();
			DatoContactoExt d = service.getDatosContactoBrByDeclaracion(objDatoContacto);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}
}
