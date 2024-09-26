  /**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioExt;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.services.UsuarioService;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.UtilsConstantes;


/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar servicios rest para el la aplicacon web
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
@Path("/sigeplg")
public class ServicesSigepRestLogin implements Serializable{

	
	private static final long serialVersionUID = -6503436010566070869L;
	public Gson gson = new Gson();
	
	public ServicesSigepRestLogin() {
		LoggerSigep.getInstance().configureAppender();
	}
	
	
	/**
	 * 
	 * @author: Jose Viscaya
	 *
	 * @param msg
	 * @return
	 */
	@GET()
    @Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@PathParam("msg") String msg) {    	
		return Response.status(201).entity("Hello: Services Context sigeplg... "+msg).build();	
	}
	
	
	/**
	 * 
	 * @author: Jose Viscaya
	 *
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/login" ) 
	@Consumes("text/plain")
	public Response transaccion(String json) {  
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			UsuarioService serviceL = new UsuarioService();
			UsuarioExt user = gson.fromJson(json, UsuarioExt.class);
			UsuarioExt usere = serviceL.usuarioLogin(user);
			String out = gson.toJson(usere);
			out = out.replace("\\u003d", "=");
			return Response.status(201).entity(out).build();
		} catch (Exception e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestLogin.class);
			return Response.status(201).entity("{}").build();
		}
	}
	
	/**
	 * 
	 * @author: Jose Viscaya
	 *
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/loginweb" ) 
	@Consumes("text/plain")
	public Response loginweb(String json) {  
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			UsuarioService serviceL = new UsuarioService();
			UsuarioExt user = gson.fromJson(json, UsuarioExt.class);
			UsuarioExt usere = serviceL.loginWeb(user);
			String out = gson.toJson(usere);
			out = out.replace("\\u003d", "=");
			out = out.replace("codUsuario", "id");
			out = out.replace("flgAceptoHabeasData:1", "flgAceptoHabeasData:true");
			out = out.replace("flgAceptoHabeasData:0", "flgAceptoHabeasData:false");
			out = out.replace("flgBloqueado:1", "flgBloqueado:true");
			out = out.replace("flgBloqueado:0", "flgBloqueado:false");
			out = out.replace("flgEsInterno:1", "flgEsInterno:true");
			out = out.replace("flgEsInterno:0", "flgEsInterno:false");
			out = out.replace("flgEstado:1", "flgEstado:true");
			out = out.replace("flgEstado:0", "flgEstado:false");
			
			return Response.status(201).entity(out).build();
		} catch (Exception e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestLogin.class);
			return Response.status(201).entity("{}").build();
		}
	}
	
	/**
	 * 
	 * @author: Jose Viscaya
	 *
	 * @return
	 */
	@POST
	@Path ( "/tiposdocumentos" ) 
	@Consumes("text/plain")
	public Response tiposdocumentos() {  
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
		ParametricaService service = new ParametricaService();
			List<Parametrica> d = service.getPrametricaByPadreMovil(new BigDecimal(23));
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestLogin.class);
			return Response.status(201).entity("{}").build();
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/insert-persona" ) 
	@Consumes("text/plain")
	public Response inserPersona(@Context HttpServletRequest req, String json) {  
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		PersonaExt user = gson.fromJson(json, PersonaExt.class);
		PersonaService service = new PersonaService();
		boolean res = service.insertPersona(user);
		return Response.status(201).entity("{\"Transaccion\",\""+res+"\"}").build();
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/get-persona" ) 
	@Consumes("text/plain")
	public Response getPersona(@Context HttpServletRequest req, String json) {  
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Persona user = gson.fromJson(json, Persona.class);
		PersonaService service = new PersonaService();
		user = service.personaById(user);
		String out = gson.toJson(user);
		return Response.status(201).entity(out).build();
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/loginmov" ) 
	@Consumes("text/plain")
	public Response loginMobile(String json) {  
		LoggerSigep.getInstance().info(json, ServicesSigepRestLogin.class);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Usuario user = new Usuario();
		try {
			UsuarioService serviceL = new UsuarioService();
			user = gson.fromJson(json, Usuario.class);
			UsuarioExt usere = serviceL.usuarioLoginMobile(user);
			String out = gson.toJson(usere);
			return Response.status(201).entity(out).build();
		} catch (Exception e) {
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestLogin.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	
}
