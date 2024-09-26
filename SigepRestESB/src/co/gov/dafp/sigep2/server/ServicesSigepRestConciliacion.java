/**
 * @ Author Jose Viscaya
 * 13 nov. 2021
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

import co.gov.dafp.sigep2.bean.CifrasConciliacion;
import co.gov.dafp.sigep2.services.CifrasConciliacionService;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya
 *
 */

@Path("/sigepConci")
public class ServicesSigepRestConciliacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5724571862332346736L;
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @param req
	 * @param msg
	 * @return
	 * @Date: 13 nov. 2021
	 */
	@GET()
    @Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@Context HttpServletRequest req, @PathParam("msg") String msg) {    	
		return Response.status(201).entity("Hello: Services Context sigepConci... "+msg).build();	
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @param auditJson
	 * @return
	 * @Date: 13 nov. 2021
	 */
	@POST 
	@Path ( "/setCifrasConciliacion" ) 
	@Consumes("text/plain")
	public Response setCifrasConciliacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CifrasConciliacion res = new CifrasConciliacion();
		try {
			CifrasConciliacion cifrasConciliacion = gson.fromJson(json, CifrasConciliacion.class);
			cifrasConciliacion.setFechaGeneracion(new Date());
			CifrasConciliacionService service = new CifrasConciliacionService();
			try {
				if(cifrasConciliacion.getCodCifrasConciliacion() !=null && cifrasConciliacion.getCodCifrasConciliacion().longValue() > 0) {
					res = service.updateCifrasConciliacion(cifrasConciliacion);
				}else{
					res = service.insertCifrasConciliacion(cifrasConciliacion);
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
	 * @Author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @param auditJson
	 * @return
	 * @Date: 13 nov. 2021
	 */
	@POST 
	@Path ( "/getCifrasConciliacion" ) 
	@Consumes("text/plain")
	public Response getCifrasConciliacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		List<CifrasConciliacion> res = new ArrayList<>();
		try {
			CifrasConciliacion cifrasConciliacion = gson.fromJson(json, CifrasConciliacion.class);
			cifrasConciliacion.setFechaGeneracion(new Date());
			CifrasConciliacionService service = new CifrasConciliacionService();
			try {
				res = service.getCifrasConciliacionFiltro(cifrasConciliacion);
			} catch (NullPointerException e) {
				res = new ArrayList<>();
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res = new ArrayList<>();
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	

}
