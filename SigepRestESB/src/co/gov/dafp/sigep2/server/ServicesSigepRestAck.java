/**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import co.gov.dafp.sigep2.services.TokenService;


/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar servicios rest para el la aplicacon web
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
@Path("/sigepack")
public class ServicesSigepRestAck implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6503436010566070869L;
	
	
	/**
	 * @author: Jose Viscaya
	 * @param msg
	 * @return
	 */
	@GET()
    @Path("ack/{msg}")
	@Produces("text/plain")
	public Response cliente(@PathParam("msg") String msg) {   
		TokenService service = new TokenService();
		boolean tok = service.updateTokenAck(msg);
		return Response.status(201).entity(tok).build();
	}
	
	
}
