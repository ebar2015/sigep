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

import co.gov.dafp.sigep2.bean.Cargo;
import co.gov.dafp.sigep2.bean.Entidad;
import co.gov.dafp.sigep2.bean.EntidadCargo;
import co.gov.dafp.sigep2.bean.EntidadPlanta;
import co.gov.dafp.sigep2.bean.EntidadPlantaDetalle;
import co.gov.dafp.sigep2.bean.SituacionAdminVinculacion;
import co.gov.dafp.sigep2.bean.UsuarioRolEntidad;
import co.gov.dafp.sigep2.bean.Vinculacion;
import co.gov.dafp.sigep2.bean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.bean.ext.EntidadCargoExt;
import co.gov.dafp.sigep2.bean.ext.EntidadExt;
import co.gov.dafp.sigep2.bean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.bean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.bean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.bean.ext.VinculacionExt;
import co.gov.dafp.sigep2.constante.AuditoriaConstantes;
import co.gov.dafp.sigep2.services.AuditoriaService;
import co.gov.dafp.sigep2.services.CargoService;
import co.gov.dafp.sigep2.services.DependenciaEntidadService;
import co.gov.dafp.sigep2.services.EntidadCargoService;
import co.gov.dafp.sigep2.services.EntidadPlantaDetalleService;
import co.gov.dafp.sigep2.services.EntidadPlantaService;
import co.gov.dafp.sigep2.services.HojaVidaService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.services.SituacionAdminVinculacionService;
import co.gov.dafp.sigep2.services.UsuarioRolEntidadService;
import co.gov.dafp.sigep2.services.VinculacionService;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.TipoParametro;
import co.gov.dafp.sigep2.utils.UtilsConstantes;


/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de exponer micro servicios de Vinculaciones
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Martes 3 de Julio de 2018
*/
@Path("/sigepvin")
public class ServicesSigepRestVin implements Serializable{


	private static final long serialVersionUID = -6503436010566070869L;
	public Gson gson = new Gson();
	
	public ServicesSigepRestVin() {
		LoggerSigep.getInstance().configureAppender();
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param msg
	 * @return
	 */
	@GET()
    @Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@PathParam("msg") String msg) {    	
		return Response.status(201).entity("Hello: Services Context sigepvin.. "+msg).build();	
	}
	
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio inserta y actualiza una EntidadCargo.
	 */
	@POST
	@Path ( "/setentidadcargo" ) 
	@Consumes("text/plain")
	public Response setentidadcargo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		EntidadCargoService service = new EntidadCargoService();
		EntidadCargo res = new EntidadCargo();
		try {
			EntidadCargo cargoEntidad = gson.fromJson(json, EntidadCargo.class);
			try {
				if(cargoEntidad.getCodEntidadCargo() !=null && cargoEntidad.getCodEntidadCargo() > 0) {
					cargoEntidad.setAudFechaActualizacion(new Date());
					res = service.updateEntidadCargo(cargoEntidad);
				}else if(cargoEntidad.getCodEntidadCargo() == null || cargoEntidad.getCodEntidadCargo() == 0 ) {
					res = service.insertEntidadCargoSelective(cargoEntidad);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de EntidadCargo 
	 */
	@POST
	@Path ( "/getentidadcargo" ) 
	@Consumes("text/plain")
	public Response getentidadcargo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			EntidadCargoService service = new EntidadCargoService();
			List<EntidadCargo> d = service.getEntidadCargoByAll();
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			EntidadCargo user = new EntidadCargo();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadCargo> d = new ArrayList<>();
			d.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una EntidadCargo por id
	 */
	@POST
	@Path ( "/getentidadcargoid" ) 
	@Consumes("text/plain")
	public Response getentidadcargoid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			EntidadCargo user = gson.fromJson(json, EntidadCargo.class);
			EntidadCargoService service = new EntidadCargoService();
			if(user.getCodEntidadCargo()!= null) {
				EntidadCargo d = service.getEntidadCargoporById(user.getCodEntidadCargo());
				return Response.status(201).entity(gson.toJson(d)).build();
			}else {
				user = new EntidadCargo();
				user.setError(true);
				user.setMensaje("No se envio CodEntidadCargo");
				return Response.status(201).entity(gson.toJson(user)).build();
			}
		} catch (JsonParseException e) {
			EntidadCargo user = new EntidadCargo();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio inserta y actualiza una EntidadCargo.
	 */
	@POST
	@Path ( "/setEntidadPlantaDetalle" ) 
	@Consumes("text/plain")
	public Response setEntidadPlantaDetalle(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
		EntidadPlantaDetalle res = new EntidadPlantaDetalle();
		try {
			EntidadPlantaDetalle EntidadPlantaDetalle = gson.fromJson(json, EntidadPlantaDetalle.class);
			try {
				if(EntidadPlantaDetalle.getCodEntidadPlantaDetalle() !=null && EntidadPlantaDetalle.getCodEntidadPlantaDetalle() > 0) {
					EntidadPlantaDetalle.setAudFechaActualizacion(new Date());
					res = service.updateEntidadPlantaDetalle(EntidadPlantaDetalle);
				}else if(EntidadPlantaDetalle.getCodEntidadPlantaDetalle() == null || EntidadPlantaDetalle.getCodEntidadPlantaDetalle() == 0 ) {
					res = service.insertEntidadPlantaDetalleSelective(EntidadPlantaDetalle);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de EntidadCargo 
	 */
	@POST
	@Path ( "/getEntidadPlantaDetalle" ) 
	@Consumes("text/plain")
	public Response getEntidadPlantaDetalle(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			List<EntidadPlantaDetalle> d = service.getEntidadPlantaDetalleByAll();
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			EntidadPlantaDetalle user = new EntidadPlantaDetalle();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaDetalle> d = new ArrayList<>();
			d.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una EntidadCargo por id
	 */
	@POST
	@Path ( "/getEntidadPlantaDetalleid" ) 
	@Consumes("text/plain")
	public Response getEntidadPlantaDetalleid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaDetalleExt user = gson.fromJson(json, EntidadPlantaDetalleExt.class);
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			EntidadPlantaDetalleExt d = service.getEntidadPlantaDetalleporById(user.getCodEntidadPlantaDetalle());
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			EntidadPlantaDetalleExt user = new EntidadPlantaDetalleExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de EntidadCargo 
	 */
	@POST
	@Path ( "/getentidadcargofilter" ) 
	@Consumes("text/plain")
	public Response getEntidadCargoFilter(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			EntidadCargoExt objJson = gson.fromJson(json, EntidadCargoExt.class);
			EntidadCargoService service = new EntidadCargoService();
			List<EntidadCargoExt> list = service.getEntidadCargoByFilter(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EntidadCargo user = new EntidadCargo();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadCargo> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de EntidadPlantaDetalle
	 */
	@POST
	@Path ( "/getEntidadPlantaDetallefilter" ) 
	@Consumes("text/plain")
	public Response getEntidadPlantaDetalleFilter(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaDetalleExt objJson = gson.fromJson(json, EntidadPlantaDetalleExt.class);
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			List<EntidadPlantaDetalleExt> list = service.getEntidadPlantaDetalleByFilter(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EntidadPlantaDetalle user = new EntidadPlantaDetalle();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaDetalle> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metido para retornar una lista de las clasificaciones de planta por entidad desde EntidadPlanta
	 */
	@POST
	@Path ( "/getclasificacionplantabyentidad" ) 
	@Consumes("text/plain")
	public Response getClasificacionPlantaByEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaExt objJson = gson.fromJson(json, EntidadPlantaExt.class);
			EntidadPlantaService service = new EntidadPlantaService();
			List<EntidadPlantaExt> list = service.getClasificacionPlantaByEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EntidadPlantaExt user = new EntidadPlantaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metido para retornar una lista de las clases de planta por entidad desde EntidadPlanta
	 */
	@POST
	@Path ( "/getclaseplantabyentidad" ) 
	@Consumes("text/plain")
	public Response getClasePlantaByEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaExt objJson = gson.fromJson(json, EntidadPlantaExt.class);
			EntidadPlantaService service = new EntidadPlantaService();
			List<EntidadPlantaExt> list = service.getClasePlantaByEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EntidadPlantaExt user = new EntidadPlantaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de EntidadPlantaDetalle
	 */
	@POST
	@Path ( "/getcargoplanta" ) 
	@Consumes("text/plain")
	public Response getCargoPlanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaDetalleExt objJson = gson.fromJson(json, EntidadPlantaDetalleExt.class);
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			List<EntidadPlantaDetalleExt> list = service.getCargoPlanta(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (Exception e) {
			EntidadPlantaDetalle user = new EntidadPlantaDetalle();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaDetalle> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	
	
	/**
	 * @author: Robinson Correa
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de Cargos por Entidad
	 */
	@POST
	@Path ( "/getcargosplanta" ) 
	@Consumes("text/plain")
	public Response getCargosPlanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaDetalleExt objJson = gson.fromJson(json, EntidadPlantaDetalleExt.class);
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			List<EntidadPlantaDetalleExt> list = service.getCargosPlanta(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (Exception e) {
			EntidadPlantaDetalle user = new EntidadPlantaDetalle();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaDetalle> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	
	/**
	 * @author: Robinson Correa
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de Codigos Cargo por Entidad
	 */
	@POST
	@Path ( "/getcodigoscargos" ) 
	@Consumes("text/plain")
	public Response getCodigosCargos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaDetalleExt objJson = gson.fromJson(json, EntidadPlantaDetalleExt.class);
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			List<EntidadPlantaDetalleExt> list = service.getCodigosCargos(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (Exception e) {
			EntidadPlantaDetalle user = new EntidadPlantaDetalle();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaDetalle> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Robinson Correa
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de 
	 * naturaleza empleo por Entidad
	 */
	@POST
	@Path ( "/getnaturalezaempleo" ) 
	@Consumes("text/plain")
	public Response getNaturalezaEmpleo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaDetalleExt objJson = gson.fromJson(json, EntidadPlantaDetalleExt.class);
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			List<EntidadPlantaDetalleExt> list= service.getNaturalezaEmpleobyEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (Exception e) {
			EntidadPlantaDetalle user = new EntidadPlantaDetalle();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaDetalle> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de EntidadPlantaDetalle
	 */
	@POST
	@Path ( "/getdependenciaentidadfilter" ) 
	@Consumes("text/plain")
	public Response getDependenciaEntidadFilter(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			DependenciaEntidadExt objJson = gson.fromJson(json, DependenciaEntidadExt.class);
			DependenciaEntidadService service = new DependenciaEntidadService();
			List<DependenciaEntidadExt> list = service.getDependenciaEntidadFilter(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			DependenciaEntidadExt user = new DependenciaEntidadExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<DependenciaEntidadExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio inserta y actualiza una Vinculacion.
	 */
	@POST
	@Path ( "/setvinculacion" ) 
	@Consumes("text/plain")
	public Response setvinculacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		VinculacionService service = new VinculacionService();
		Vinculacion res = new Vinculacion();
		try {
			Vinculacion vinculacion = gson.fromJson(json, Vinculacion.class);
			try {
				if(vinculacion.getCodVinculacion() !=null && vinculacion.getCodVinculacion().longValue() > 0) {
					vinculacion.setAudFechaActualizacion(new Date());
					res = service.updateVinculacion(vinculacion);
				}else if(vinculacion.getCodPersona() != null || vinculacion.getCodPersona().longValue() > 0 ) {
					res = service.insertVinculacion(vinculacion);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metido para retornar una lista de Vinculaciones por persona
	 */
	@POST
	@Path ( "/getvinculacion" ) 
	@Consumes("text/plain")
	public Response getvinculacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Vinculacion vinculacion = gson.fromJson(json, Vinculacion.class);
			VinculacionService service = new VinculacionService();
			List<Vinculacion> list = service.getVinculacionPersona(vinculacion);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			Vinculacion user = new Vinculacion();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<Vinculacion> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para retornar una lista de datos de Vinculacion por filtro
	 */
	@POST
	@Path ( "/getvinculacionfilter" ) 
	@Consumes("text/plain")
	public Response getVinculacionFilter(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt vinculacion = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionFilter(vinculacion);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una lista de EntidadCargo 
	 */
	@POST
	@Path ( "/getEntidadPlantafilter" ) 
	@Consumes("text/plain")
	public Response getEntidadPlantaFilter(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaExt objJson = gson.fromJson(json, EntidadPlantaExt.class);
			EntidadPlantaService service = new EntidadPlantaService();
			List<EntidadPlantaExt> list = service.getEntidadPlantaFilter(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EntidadPlantaExt user = new EntidadPlantaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metido para retornar una lista de Vinculaciones por persona
	 */
	@POST
	@Path ( "/getvinculaciondiferenteentidad" ) 
	@Consumes("text/plain")
	public Response getVinculacionDiferenteEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionDiferenteEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para validar si la persona tiene un rol activo en la entidad
	 */
	@POST
	@Path ( "/getpersonarolactivoentidad" ) 
	@Consumes("text/plain")
	public Response getPersonaRolActivoEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaExt objJson = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> list = service.getPersonaRolActivoEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			PersonaExt user = new PersonaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<PersonaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para validar si la persona tiene aprobada la hoja de vida y no se pasa del tiempo parametrisable
	 */
	@POST
	@Path ( "/getpersonahojavidaaprobada" ) 
	@Consumes("text/plain")
	public Response getPersonaHojaVidaAprobada(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			HojaVidaExt objJson = gson.fromJson(json, HojaVidaExt.class);
			HojaVidaService service = new HojaVidaService();
			List<HojaVidaExt> list = service.getPersonaHojaVidaAprobada(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			HojaVidaExt user = new HojaVidaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<HojaVidaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metido para retornar una lista de Vinculaciones por filtro
	 */
	@POST
	@Path ( "/vinculacionpersonafiltro" ) 
	@Consumes("text/plain")
	public Response vinculacionPersonafiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.vinculacionPersonafiltro(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metido para retornar una lista de Vinculaciones por filtro
	 */
	@POST
	@Path ( "/getmostrarvinculaciones" ) 
	@Consumes("text/plain")
	public Response getMostrarVinculaciones(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getMostrarVinculaciones(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para mostrar las vinculaciones que tengan una situacion administrativa con comision/encargo
	 */
	@POST
	@Path ( "/getvinculacionsituacionadmin" ) 
	@Consumes("text/plain")
	public Response getVinculacionSituacionAdmin(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionSituacionAdmin(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Franklin Zapata
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para mostrar las vinculaciones que tengan una situacion administrativa con periodo de prueba
	 */
	@POST
	@Path ( "/getvinculacionsituacionadminperiodoprueba" ) 
	@Consumes("text/plain")
	public Response getVinculacionSituacionAdminPeriodoPrueba(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionSituacionAdminPeriodoPrueba(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Franklin Zapata
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para mostrar las vinculaciones en periodo de prueba
	 */
	@POST
	@Path ( "/getvinculacionperiododeprueba" ) 
	@Consumes("text/plain")
	public Response getVinculacionPeriodoDePrueba(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionPeriodoDePrueba(objJson);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.VINCULACION, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para retornar los datos de Vinculacion por medio del id
	 */
	@POST
	@Path ( "/getvinculacionid" ) 
	@Consumes("text/plain")
	public Response getVinculacionById(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Vinculacion user = gson.fromJson(json, Vinculacion.class);
			VinculacionService service = new VinculacionService();
			Vinculacion d = service.getVinculacionById(user.getCodVinculacion());
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			Vinculacion user = new Vinculacion();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para obtener los correos de las personas para enviar email de desvincular
	 */
	@POST
	@Path ( "/getemailpersonasdesvincular" ) 
	@Consumes("text/plain")
	public Response getEmailPersonasDesvincular(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaExt objJson = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> list = service.getEmailPersonasDesvincular(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			PersonaExt user = new PersonaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<PersonaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}

	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para mostrar las vinculaciones actuales que tenga una persona
	 */
	@POST
	@Path ( "/getvinculacionactual" ) 
	@Consumes("text/plain")
	public Response getVinculacionActual(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionActual(objJson);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.VINCULACION, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	 /**
     * @author: Jose Viscaya 
     * @fecha 25 Juli 2018
     * @param req
     * @param json
     * @param token
     * @retdo para insertar y actualizar en ala tabla EntidadPlanta
	 */
	@POST
	@Path ( "/getultimavinculaciondesvinculacion" ) 
	@Consumes("text/plain")
	public Response getUltimaVinculacionDesvinculacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getUltimaVinculacionDesvinculacion(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 */
	@POST
	@Path ( "/quitarrol" ) 
	@Consumes("text/plain")
	public Response desactivarrolescontratista(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		UsuarioRolEntidadService service = new UsuarioRolEntidadService();
		UsuarioRolEntidad res = new UsuarioRolEntidad();
		try {
			UsuarioRolEntidadExt usuario = gson.fromJson(json, UsuarioRolEntidadExt.class);
			res = service.updateQuitarRol(usuario);
		    return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 */
	@POST
	@Path ( "/desactivarrol" ) 
	@Consumes("text/plain")
	public Response updateByDesactivar(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		UsuarioRolEntidadService service = new UsuarioRolEntidadService();
		UsuarioRolEntidad res = new UsuarioRolEntidad();
		try {
			UsuarioRolEntidadExt usuario = gson.fromJson(json, UsuarioRolEntidadExt.class);
			res = service.updateByDesactivar(usuario);
		    return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio inserta y actualiza una EntidadCargo.
	 */
	@POST
	@Path ( "/setEntidadPlanta" ) 
	@Consumes("text/plain")
	public Response setEntidadPlanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	    json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        EntidadPlantaService service = new EntidadPlantaService();
        EntidadPlanta res = new EntidadPlanta();
        try {
        	EntidadPlanta EntidadPlanta = gson.fromJson(json, EntidadPlanta.class);
            try {
                if (EntidadPlanta.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
                		|| EntidadPlanta.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	EntidadPlanta.setAudFechaActualizacion(new Date());
                    res = service.updateEntidadPlanta(EntidadPlanta);
                } else if (EntidadPlanta.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertEntidadPlanta(EntidadPlanta);
                }
            } catch (NullPointerException e) {
                res.setError(true);
                res.setMensaje(UtilsConstantes.MSG_OBLIGATORIOS + " AudAccion()");
            }
            return Response.status(201).entity(gson.toJson(res)).build();
        } catch (JsonParseException e) {
            res.setError(true);
            res.setMensaje(UtilsConstantes.MSG_EXEPCION);
            res.setMensajeTecnico(e.getMessage());
            LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
            return Response.status(201).entity(gson.toJson(res)).build();
        }
	}
	/**
	 * @author: Jose Viscaya 
	 * @fecha 23 ago. 2018 10:44:16
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getvacantes" ) 
	@Consumes("text/plain")
	public Response getvacantes(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVacantes(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * Elaborado por Nestor Riasco 
	 * @fecha 10 sep. 2018 10:44:16
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getcargosfuncionario" ) 
	@Consumes("text/plain")
	public Response getcargosfuncionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getcargosfuncionario(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 */
	@POST
	@Path ( "/getcargosentidadvin" ) 
	@Consumes("text/plain")
	public Response getcargosentidadvin(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Entidad objJson = gson.fromJson(json, Entidad.class);
			CargoService service = new CargoService();
			List<Cargo> list = service.getcargosEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			Cargo user = new Cargo();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<Cargo> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * 
	 * Elaborado por Nestor Riasco 
	 * @fecha 03 sep. 2018 10:44:16
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getvacantestempglobal" ) 
	@Consumes("text/plain")
	public Response getVacantesTempGlobal(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVacantesTempGlobal(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * 
	 * Elaborado por Nestor Riasco 
	 * @fecha 04 sep. 2018 10:44:16
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getvacantestemppermglobal" ) 
	@Consumes("text/plain")
	public Response getVacantesTemyPermGlobal(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVacantesTemyPermGlobal(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * 
	 * Elaborado por Nestor Riasco 
	 * @fecha 04 sep. 2018 10:44:16
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getvacantesglobal" ) 
	@Consumes("text/plain")
	public Response getVacantesGlobal(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVacantesGlobal(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * 
	 * Elaborado por Nestor Riasco 
	 * @fecha 02 oct. 2018 9:45016
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getcargoEntidadPlantaDetalle" ) 
	@Consumes("text/plain")
	public Response getcargoEntidadPlantaDetalle(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaDetalle objJson = gson.fromJson(json, EntidadPlantaDetalle.class);
			CargoService service = new CargoService();
			Cargo cargo = service.getcargoEntidadPlantaDetalle(objJson);
			return Response.status(201).entity(gson.toJson(cargo)).build();
		} catch (JsonParseException e) {
			Cargo cargo = new Cargo();
			cargo.setError(true);
			cargo.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cargo.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(cargo)).build();
		}
	}
	
	
	 /**
     * Elaborado por Maria Alejandra C
     * @fecha 26-10-2018
     * @param req
     * @param json
     * @param token
     * @retdo metodo que obtiene las vinculaciones de una entidad por planta de personal
	 */
	@POST
	@Path ( "/getvinculacionporplanta" ) 
	@Consumes("text/plain")
	public Response getvinculacionporplanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionPorPlanta(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio actualiza todas las vinculaciones que se tengan para una planta en especifico,
	 * y realiza un eliminado logico
	 */
	@POST
	@Path ( "/setdesvinculacionautomaticaporplanta" ) 
	@Consumes("text/plain")
	public Response setdesvinculacionautomaticaporplanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		VinculacionService service = new VinculacionService();
		VinculacionExt res = new VinculacionExt();
		try {
			VinculacionExt vinculacion = gson.fromJson(json, VinculacionExt.class);
			try {
				if(vinculacion.getCodPlantaPersonal() !=null && vinculacion.getCodPlantaPersonal().longValue() > 0) {
					res = service.updateDesvinculacionAutomaticaPorPlanta(vinculacion);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metido para retornar una lista de Vinculaciones para la entidad seleccionada
	 */
	@POST
	@Path ( "/getvinculacionmismaentidad" ) 
	@Consumes("text/plain")
	public Response getVinculacionMismaEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionMismaEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo que retorna la lista de vinculaciones que tienen un codigo de cargo especifico
	 */
	@POST
	@Path ( "/getvinculacionsenadorescongresistas" ) 
	@Consumes("text/plain")
	public Response getVinculacionSenadoresCongresistas(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadExt objJson = gson.fromJson(json, EntidadExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionSenadoresCongresistas(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna lista de plantas existentes, excepto las que son de tipo UTL/UAN
	 */
	@POST
	@Path ( "/getselectentidadplantaexceptoUTL" ) 
	@Consumes("text/plain")
	public Response getSelectEntidadPlantaExceptoUTL(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPlantaExt objJson = gson.fromJson(json, EntidadPlantaExt.class);
			EntidadPlantaService service = new EntidadPlantaService();
			List<EntidadPlantaExt> list = service.getselectEntidadPlantaExceptoUTL(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EntidadPlantaExt user = new EntidadPlantaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPlantaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	 /**
     * Elaborado por Maria Alejandra Colorado Rios
     * Servicio que llama a la funciï¿½n f_obtener_vacancias_cargos
     * @fecha 11 de Junio 2019
     * @param req
     * @param json
     * @param token
     */
	@POST
	@Path("/obtenervacanciascargos")
	@Consumes("text/plain")
	public Response obtenerVacanciaCargos(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		EntidadPlantaDetalleExt res = new EntidadPlantaDetalleExt();
		try {
			EntidadPlantaDetalleExt objEntidadPlantaDetalle = gson.fromJson(json, EntidadPlantaDetalleExt.class);
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			res = service.obtenerVacanciaCargos(objEntidadPlantaDetalle);
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	
	/**
	 * @author: Maria Alejandra Colorado
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio actualiza todas las situaciones administrativas asociadas a una vinculacion
	 */
	@POST
	@Path ( "/setsituaciporvinculacion" ) 
	@Consumes("text/plain")
	public Response setSituaciPorVinculacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		SituacionAdminVinculacionService service = new SituacionAdminVinculacionService();
		SituacionAdminVinculacionExt res = new SituacionAdminVinculacionExt();
		try {
			SituacionAdminVinculacionExt sa = gson.fromJson(json, SituacionAdminVinculacionExt.class);
			try {
				if(sa.getCodVinculacion()!=null && sa.getCodVinculacion().longValue() > 0) {
					res = service.updateSituacionPorVinculacion(sa);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	
	/**
	 * 
	 * Elaborado por Maria Alejandra C
	 * @fecha 08 sep. 2019 10:44:16
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getvacantestemppermglobalestructural" ) 
	@Consumes("text/plain")
	public Response getVacantesTemyPermGlobalyEstructural(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt objJson = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVacantesTemyPermGlobalyEstructural(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Maria C
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo que retorna todas las personas que estan vinculadas
	 */
	@POST
	@Path ( "/getpersonasvinculadas" ) 
	@Consumes("text/plain")
	public Response getPersonasVinculadas(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaExt objJson = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> list = service.getPersonasVinculadasPorEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			PersonaExt user = new PersonaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<PersonaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para retornar una lista de datos de Vinculacion que no tienen situaciones administrativas asociadas
	 * o que las situaciones administrativas posean fecha de inicio mayor a la actual
	 * Se utiliza desde el componente de Vinculaciones, especificamente en la opción de verificar vacancia cargos.
	 */
	@POST
	@Path ( "/getVinculacionSinSAXProcesar" ) 
	@Consumes("text/plain")
	public Response getVinculacionSinSAXProcesar(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt vinculacion = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			List<VinculacionExt> list = service.getVinculacionSinSAXProcesar(vinculacion);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<VinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}

	
	/**
	 * Servicio que retorna la cantidad de encargos activos en los que se encuentra un cargo.
	 * Elaborado por Maria Alejandra C
	 * @fecha 02/07/2020 10:44:16
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getCargoEnEncargo" ) 
	@Consumes("text/plain")
	public Response getCargoEnEncargo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SituacionAdminVinculacionExt objJson = gson.fromJson(json, SituacionAdminVinculacionExt.class);
			SituacionAdminVinculacionService service = new SituacionAdminVinculacionService();
			List<SituacionAdminVinculacionExt> list = service.getCargoEnEncargo(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			SituacionAdminVinculacionExt user = new SituacionAdminVinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<SituacionAdminVinculacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una situacion_admin_vinculacion por id
	 */
	@POST
	@Path ( "/getSituacionAdminVincPorid" ) 
	@Consumes("text/plain")
	public Response getSituacionAdminVincPorid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		SituacionAdminVinculacion st = new SituacionAdminVinculacion();
		try {
			st = gson.fromJson(json, SituacionAdminVinculacion.class);
			SituacionAdminVinculacionService service = new SituacionAdminVinculacionService();
			SituacionAdminVinculacion res = service.getSituacionAdminVinculacionById(st.getCodSituacionAdminRelacionLaboral());
			String out = gson.toJson(res);
			return Response.status(201).entity(out).build();
		} catch (Exception e) { 
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			st.setError(true);
			st.setMensaje(UtilsConstantes.MSG_EXEPCION);
			st.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(st)).build();
		}	
	}
	
	
	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna una situacion_admin_vinculacion por cod_sa_ocupa_temporal
	 */
	@POST
	@Path ( "/getSituacionAdminVincPorCodSAOcupaTemporal" ) 
	@Consumes("text/plain")
	public Response getSituacionAdminVincPorCodSAOcupaTemporal(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		SituacionAdminVinculacion st = new SituacionAdminVinculacion();
		try {
			st = gson.fromJson(json, SituacionAdminVinculacion.class);
			SituacionAdminVinculacionService service = new SituacionAdminVinculacionService();
			SituacionAdminVinculacion res = service.getSituacionAdminVinculacionByCodSAOcupaTemporal(st);
			String out = gson.toJson(res);
			return Response.status(201).entity(out).build();
		} catch (Exception e) { 
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestAdmin.class);
			st.setError(true);
			st.setMensaje(UtilsConstantes.MSG_EXEPCION);
			st.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(st)).build();
		}	
	}

	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para retornar los datos de la última vinculación asignada a un cargo.
	 */
	@POST
	@Path ( "/getultimaVinculacionByCargo" ) 
	@Consumes("text/plain")
	public Response getultimaVinculacionByCargo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt user = gson.fromJson(json, VinculacionExt.class);
			VinculacionService service = new VinculacionService();
			VinculacionExt d = service.getultimaVinculacionByCargo(user);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			VinculacionExt user = new VinculacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	
}
