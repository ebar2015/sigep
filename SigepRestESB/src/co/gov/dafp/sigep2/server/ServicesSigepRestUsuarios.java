/**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.core.util.Base64;

import co.gov.dafp.sigep2.bean.Idioma;
import co.gov.dafp.sigep2.bean.IdiomaPersona;
import co.gov.dafp.sigep2.bean.NacionalidadPerfil;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.UsuarioEntidad;
import co.gov.dafp.sigep2.bean.ext.IdiomaExt;
import co.gov.dafp.sigep2.bean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioExt;
import co.gov.dafp.sigep2.constante.AuditoriaConstantes;
import co.gov.dafp.sigep2.services.AuditoriaService;
import co.gov.dafp.sigep2.services.IdiomaPersonaService;
import co.gov.dafp.sigep2.services.IdiomaService;
import co.gov.dafp.sigep2.services.NacionalidadPerfilService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.services.UsuarioEntidadService;
import co.gov.dafp.sigep2.services.UsuarioService;
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
@Path("/sigepus")
public class ServicesSigepRestUsuarios implements Serializable {

	
	private static final long serialVersionUID = -72460055357354490L;
	private Gson gson;
	
	public ServicesSigepRestUsuarios() {
		LoggerSigep.getInstance().configureAppender();
	}
	/**
	 * 
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param msg
	 * @return
	 */
	@GET()
    @Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@Context HttpServletRequest req, @PathParam("msg") String msg) {   
		return Response.status(201).entity("Hello: Services Context sigepus... "+msg).build();	
	}
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/persontipIdnumId" ) 
	@Consumes("text/plain")
	public Response persontipIdnumId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			PersonaExt d = service.personaByTipoIdNumId(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			//LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getpersonafiltro" ) 
	@Consumes("text/plain")
	public Response getPersonaFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> d = service.getPersonaFiltro(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
		
	}
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/persontipIdnumIdEntId" ) 
	@Consumes("text/plain")
	public Response persontipIdnumIdEntId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			Persona d = service.personaByTipoIdNumIdCodEntidad(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/entidadesPorUsuarioId" ) 
	@Consumes("text/plain")
	public Response usuarioEntidadPorUsuarioId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
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
			List<UsuarioEntidad> d  = new ArrayList<UsuarioEntidad>();
			try {
				d = service.usuarioEntidadPorUsuarioId(user.getCodUsuario());
			} catch (NullPointerException e) {
				 d = service.usuarioEntidadPorUsuarioId(new BigDecimal(0));
			}
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/usuariosPorEntidad" ) 
	@Consumes("text/plain")
	public Response usuariosPorEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			UsuarioEntidad user = gson.fromJson(json, UsuarioEntidad.class);
			UsuarioService service = new UsuarioService();
			List<Usuario> d = service.getUsuariosPorEntidad(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
     * @author: Jose Viscaya
     *
     * @param req
     * @param json
     * @param token
     * @return Este Servicio actualiza el UsuarioEntidad
     * por Codigo de Declaracion.
     */
    @POST
    @Path("/setusuarioentidad")
    @Consumes("text/plain")
    public Response setUsuarioEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        UsuarioEntidadService service = new UsuarioEntidadService();
        UsuarioEntidad res = new UsuarioEntidad();
        try {
            UsuarioEntidad entidad = gson.fromJson(json, UsuarioEntidad.class);
            try {
                if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		||entidad.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                    entidad.setAudFechaActualizacion(new Date());
                    res = service.updateUsuarioEntidadSelective(entidad);
                } else if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertUsuarioEntidadSelective(entidad);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
	
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/usuarioPassword" ) 
	@Consumes("text/plain")
	public Response usuarioPassword(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			Usuario user = gson.fromJson(json, Usuario.class);
			UsuarioService service = new UsuarioService();
			Usuario d = service.usuarioPassword(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/usenticoduscodent" ) 
	@Consumes("text/plain")
	public Response usuarioEntidadPorcodUsCodent(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
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
			UsuarioEntidad d = service.getUsuarioEntidadPorcodUsCodent(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/perentiporperent" ) 
	@Consumes("text/plain")
	public Response personEntidadPorPersonaEnt(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			PersonaExt d = service.getPersonaEntidadPorCodPeCodEnt(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/personaporid" ) 
	@Consumes("text/plain")
	public Response personaporId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			Persona d = service.personaById(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/personaporfiltro" ) 
	@Consumes("text/plain")
	public Response personaporfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			Persona d = service.personaById(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/personaporidext" ) 
	@Consumes("text/plain")
	public Response personaporidext(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			PersonaExt d = service.personaByIdext(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/updatepersona" ) 
	@Consumes("text/plain")
	public Response updatePersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			if(user.getCodPersona() != null) {
				user.setFotoUrl(null);
				boolean d = service.updatePersona(user);
				return Response.status(201).entity(d).build();
			}else {
				return Response.status(201).entity(false).build();
			}
			
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/updatepersonatarjeta" ) 
	@Consumes("text/plain")
	public Response updatePersonatarjeta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			if(user.getCodPersona().longValue() > 0) {
				boolean d = service.updatePersona(user);
				return Response.status(201).entity(d).build();
			}else {
				return Response.status(201).entity(false).build();
			}
			
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/setnacionalidadperfil" ) 
	@Consumes("text/plain")
	public Response setNacionalidadPerfil(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		int t1 = 0;
		int t2 = 0;
		try {
			java.lang.reflect.Type type = new TypeToken<List<NacionalidadPerfil>>(){}.getType();
			List<NacionalidadPerfil>  list =  gson.fromJson(json, type );
			NacionalidadPerfilService service = new NacionalidadPerfilService();
			if(list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getCodNacionalidadPerfil() != null) {
						list.get(i).setAudFechaActualizacion(new Date());
						if(service.updateNacionalidadPerfil(list.get(i))) {
							t1++;
						}else {
							t2++;
						}
					}else if(list.get(i).getCodPersona().longValue() > 0){
						list.get(i).setAudFechaActualizacion(new Date());
						if(service.insertNacionalidadPerfil(list.get(i))) {
							t1++;
						}else {
							t2++;
						}
							
					}
				}
			}
			String out = "{\"procesados\":"+t1+",\"noProcesado\":"+t2+"}";
			return Response.status(201).entity(out).build();
		} catch (Exception e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{\"error\":true,\"mensaje\":\""+e.getMessage()+"\"}").build();
		}
		
	}
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getnacionalidadperfil" ) 
	@Consumes("text/plain")
	public Response getnacionalidadperfil(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			NacionalidadPerfilService service = new NacionalidadPerfilService();
			if(user.getCodPersona() != null) {
				if(user.getFlgActivo() != null) {
					List<NacionalidadPerfil> d = service.getNacionalidadPerfilPersona(user);
					String out = gson.toJson(d);
					return Response.status(201).entity(out).build();
				}else {
					NacionalidadPerfil d = new NacionalidadPerfil();
					d.setError(true);
					d.setMensaje("El valor flgActivo no encontrado");
					return Response.status(201).entity(gson.toJson(d)).build();
				}
			}else {
				NacionalidadPerfil d = new NacionalidadPerfil();
				d.setError(true);
				d.setMensaje("El valor Codigo Persona no encontrado");
				return Response.status(201).entity(gson.toJson(d)).build();
			}
			
		} catch (JsonParseException e) {
			NacionalidadPerfil d = new NacionalidadPerfil();
			d.setError(true);
			d.setMensaje("Ha Ocurrido Un Error");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getidiomacomplpersona" ) 
	@Consumes("text/plain")
	public Response getIdiomaComplPersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			IdiomaService service = new IdiomaService();
			if(user.getCodPersona().longValue() > 0) {
				IdiomaExt d = service.getIdiomaComplPersona(user.getCodPersona().longValue());
				d.setError(false);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			}else {
				Idioma d = new Idioma();
				d.setError(true);
				d.setMensaje("El valor Codigo Persona no encontrado");
				return Response.status(201).entity(gson.toJson(d)).build();
			}
			
		} catch (JsonParseException e) {
			Idioma d = new Idioma();
			d.setError(true);
			d.setMensaje("Ha Ocurrido Un Error");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/setidiomapersona" ) 
	@Consumes("text/plain")
	public Response setIdiomaPersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		IdiomaPersona idio = new IdiomaPersona();
		try {
			json = json.replace(":0,", ":null,");
			json = json.replace(":false,", ":0,");
			json = json.replace(":true,", ":1,");
			json = json.replace(":0}", ":null}");
			json = json.replace(":false}", ":0}");
			json = json.replace(":true}", ":1}");
			json = json.replace("\"error\":0", "\"error\":false");
			IdiomaPersona user = gson.fromJson(json, IdiomaPersona.class);
			user.setAudFechaActualizacion(new Date());
			IdiomaPersonaService service = new IdiomaPersonaService();
			try {
				if(user.getCodIdiomaPersona().longValue() > 0) {
					idio  = service.updateIdiomaPersona(user);
				}else {
					if(user.getCodPersona().longValue()> 0) {
						idio  = service.insertIdiomaPersona(user);
					}else {
						idio.setError(false);
						idio.setMensaje("Falta Enviar CodPersona");
					}
				}
				return Response.status(201).entity(gson.toJson(idio)).build();
			} catch (NullPointerException e) {
				try {
					if(user.getCodPersona().longValue()> 0) {
						idio = service.insertIdiomaPersona(user);
					}else {
						idio.setError(true);
						idio.setMensaje("Falta Enviar CodPersona");
					}
					return Response.status(201).entity(gson.toJson(idio)).build();
				} catch (Exception e2) {
					idio.setError(true);
					idio.setMensaje("Falta Enviar CodPersona");
					idio.setMensajeTecnico(e2.getMessage());
					return Response.status(201).entity(gson.toJson(idio)).build();
				}
			}
		} catch (JsonParseException e) {
			idio.setError(true);
			idio.setMensaje(UtilsConstantes.MSG_EXEPCION);
			idio.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity(gson.toJson(idio)).build();
		}
		
	}
			
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getidiomapersonaporpersona" ) 
	@Consumes("text/plain")
	public Response getIdiomaPersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		json = json.replace("\"flgActivo\":true", "\"flgActivo\":1");
		json = json.replace("\"flgActivo\":false", "\"flgActivo\":0");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			IdiomaPersona user = gson.fromJson(json, IdiomaPersona.class);
			IdiomaPersonaService service = new IdiomaPersonaService();
			if(user.getCodPersona().longValue() > 0) {
				List<IdiomaPersonaExt> d = service.getIdiomaPersonaByPersona(user);
				if(d.size() > 0) {
					for (int i = 0; i < d.size(); i++) {
						d.get(i).setError(false);
					}
				}
				
				String out = gson.toJson(d);
				out = out.replace("\"flgIdiomaNativo\":0", "\"flgIdiomaNativo\":false");
				out = out.replace("\"flgIdiomaNativo\":1", "\"flgIdiomaNativo\":true");
				out = out.replace("\"flgActivo\":1", "\"flgActivo\":true");
				out = out.replace("\"flgActivo\":0", "\"flgActivo\":false");
				return Response.status(201).entity(out).build();
			}else {
				IdiomaPersonaExt d = new IdiomaPersonaExt();
				d.setError(true);
				d.setMensaje("El valor Codigo Persona no encontrado");
				List<IdiomaPersonaExt> d1 = new ArrayList<IdiomaPersonaExt>();
				d1.add(d);
				return Response.status(201).entity(gson.toJson(d1)).build();
			}
			
		} catch (JsonParseException e) {
			IdiomaPersonaExt d = new IdiomaPersonaExt();
			d.setError(true);
			d.setMensaje("Ha Ocurrido un Error");
			d.setMensajeTecnico(e.getMessage());
			List<IdiomaPersonaExt> d1 = new ArrayList<IdiomaPersonaExt>();
			d1.add(d);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity(gson.toJson(d1)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getpersonacontacto" ) 
	@Consumes("text/plain")
	public Response getPersonaContacto(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			PersonaExt d = service.getPersonaContacto(user.getCodPersona());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getusuarioext" ) 
	@Consumes("text/plain")
	public Response seltUsuarioExt(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			UsuarioEntidad user = gson.fromJson(json, UsuarioEntidad.class);
			UsuarioService service = new UsuarioService();
			UsuarioExt d = service.selectUsuarioExt(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getidiomapersonaporpersonaporid" ) 
	@Consumes("text/plain")
	public Response getidiomapersonaporpersonaporid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		IdiomaPersonaExt d = new IdiomaPersonaExt();
		try {
			IdiomaPersona user = gson.fromJson(json, IdiomaPersona.class);
			IdiomaPersonaService service = new IdiomaPersonaService();
			if(user.getCodIdiomaPersona().longValue() > 0) {
				IdiomaPersonaExt dr = service.getIdiomaPersonaById(user.getCodIdiomaPersona());
				String out = gson.toJson(dr);
				out = out.replace("\"flgIdiomaNativo\":0,", "\"flgIdiomaNativo\":false,");
				out = out.replace("\"flgIdiomaNativo\":1,", "\"flgIdiomaNativo\":true,");
				out = out.replace("\"flgActivo\":1,", "\"flgActivo\":true,");
				out = out.replace("\"flgActivo\":0,", "\"flgActivo\":false,");
				return Response.status(201).entity(out).build();
			}else {
				d.setError(true);
				d.setMensaje("El valor Codigo Persona no encontrado");
				List<IdiomaPersonaExt> d1 = new ArrayList<IdiomaPersonaExt>();
				d1.add(d);
				return Response.status(201).entity(gson.toJson(d1)).build();
			}
			
		} catch (JsonParseException e) {
			d.setError(true);
			d.setMensaje("Ha Ocurrido un Error");
			d.setMensajeTecnico(e.getMessage());
			List<IdiomaPersonaExt> d1 = new ArrayList<IdiomaPersonaExt>();
			d1.add(d);
			return Response.status(201).entity(gson.toJson(d1)).build();
		} catch (NullPointerException e) {
			d.setError(true);
			d.setMensaje("Ha Ocurrido un Error");
			d.setMensajeTecnico(e.getMessage());
			List<IdiomaPersonaExt> d1 = new ArrayList<IdiomaPersonaExt>();
			d1.add(d);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity(gson.toJson(d1)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getpersonahvfiltro" ) 
	@Consumes("text/plain")
	public Response getusuariosporentidadfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> d = service.getPersonaHVFiltro(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getusuarioid" ) 
	@Consumes("text/plain")
	public Response getUsuarioId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			json = json.replace("false", "0");
			json = json.replace("true", "1");
			json = json.replace("error\":0", "error\":false");
			json = json.replace("error\":1", "error\":true");
			
		    GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			Usuario user = gson.fromJson(json, Usuario.class);
			UsuarioService service = new UsuarioService();
			Usuario d = service.usuariobyPersona(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		    return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/bloqueointentosmov" ) 
	@Consumes("text/plain")
	public Response usuarioupdateselective(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
		    GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			Usuario user = new Usuario();
		try {
			user = gson.fromJson(json, Usuario.class);
			user.setFlgBloqueado((short) 1);
			user.setFlgEstado((short) 1);
			user.setFechaVence(new Date());
			UsuarioService service = new UsuarioService();
			Usuario d = service.updateUsuarioSelective(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
		
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getpersonarolentidad" ) 
	@Consumes("text/plain")
	public Response getPersonaRolEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
		    GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> d = service.getPersonaRolEntidad(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getpersonavinculacionFiltro" ) 
	@Consumes("text/plain")
	public Response getpersonavinculacionFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> d = service.getpersonavinculacionfiltro(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.VINCULACION, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 23 ago. 2018 14:07:15
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getpersonasactivas" ) 
	@Consumes("text/plain")
	public Response getpersonasActivas(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> d = service.getpersonaActivas(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	
	/**
	 * @author Maria Alejandra Colorado R�os
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getpersonaresponsableentidad" ) 
	@Consumes("text/plain")
	public Response getPersonaResponsableEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
		    GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			PersonaService service = new PersonaService();
			List<PersonaExt> d = service.getPersonaResponsableEntidad(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
		return Response.status(201).entity("{}").build();
		}
		
	}
			
	/**
	 * @author Jhon Garcia
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/usuarioporid" ) 
	@Consumes("text/plain")
	public Response usuarioporId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
		try {
			Usuario user = gson.fromJson(json, Usuario.class);
			UsuarioService service = new UsuarioService();
			Usuario d = service.getUsuario(user.getCodUsuario());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestUsuarios.class);
			return Response.status(201).entity("{}").build();
		}
		
	}
	
	
	/**
     * @author Desarolladora Junior
     * @param req
     * @param json
     * @param token
     * @return Este Servicio actualiza el UsuarioEntidad por usuario
     */
    @POST
    @Path("/setusuarioentidadporUsuario")
    @Consumes("text/plain")
    public Response setusuarioentidadporUsuario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        UsuarioEntidadService service = new UsuarioEntidadService();
        UsuarioEntidad res = new UsuarioEntidad();
        try {
            UsuarioEntidad entidad = gson.fromJson(json, UsuarioEntidad.class);
            try {
                if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
                		||entidad.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                    entidad.setAudFechaActualizacion(new Date());
                    res = service.updatePorUsuario(entidad);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return Usuario
	 * Metodo para retornar los datos de usuario por medio de numero y tipo de indentificacion de la persona
	 */
	@POST
	@Path ( "/getUsuarioByPersona" ) 
	@Consumes("text/plain")
	public Response getUsuarioByPersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			UsuarioExt user = gson.fromJson(json, UsuarioExt.class);
			UsuarioService service = new UsuarioService();
			Usuario d = service.getUsuarioByPersona(user);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			Usuario user = new Usuario();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), UsuarioService.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}

	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return Usuario
	 * Metodo para retornar si el usuario cuenta con alguno de los roles de administracion
	 */
	@POST
	@Path ( "/getUsuarioAdmin" ) 
	@Consumes("text/plain")
	public Response getUsuarioAdmin(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			UsuarioExt user = gson.fromJson(json, UsuarioExt.class);
			UsuarioService service = new UsuarioService();
			UsuarioExt d = service.getUsuarioAdmin(user);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			UsuarioExt user = new UsuarioExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), UsuarioService.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
}

