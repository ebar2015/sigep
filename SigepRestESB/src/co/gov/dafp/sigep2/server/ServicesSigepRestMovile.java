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

import co.gov.dafp.sigep2.bean.DatoAdicional;
import co.gov.dafp.sigep2.bean.DatoContacto;
import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.Token;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.ext.UsuarioExt;
import co.gov.dafp.sigep2.movile.DatosAdicionales;
import co.gov.dafp.sigep2.movile.MovileService;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.TokenService;
import co.gov.dafp.sigep2.services.UsuarioService;
import co.gov.dafp.sigep2.utils.ErrorMensajes;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.StringEncrypt;
import co.gov.dafp.sigep2.utils.UtilsConstantes;


/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de transportar servicios al fuse
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Martes 24 de Junio de 2018
*/
@Path("/sigepmov")
public class ServicesSigepRestMovile implements Serializable{

	
	private static final long serialVersionUID = -6503436010566070869L;
	private Gson gson = new Gson();
	protected static final String CODTIMEOUT = "1559";
	private static final BigDecimal MAXIMO_INTENTOS_FALLIDOS_LOGIN = new BigDecimal(687);
	
	public ServicesSigepRestMovile() {
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
		return Response.status(201).entity("Hello: Services Context sigepmov.. "+msg).build();	
	}
	
	
	/**
     * @author: Jose Viscaya 
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @return Este Servicio cambia la contrasena;
     */
	@POST
	@Path ("/cambiarconreasena") 
	@Produces("text/plain")
	public Response cambiarconreasena(@Context HttpServletRequest req, String json, @HeaderParam("token") String token) {  
		ParametricaService serviceP = new ParametricaService();
		if(!UtilsConstantes.tokenIsValid(token,serviceP.getPrametricaById(new BigDecimal(CODTIMEOUT)).getValorParametro())) {
			return Response.status(201).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		UsuarioExt usu = gson.fromJson(json, UsuarioExt.class);
		Usuario error = new Usuario();
		try {
			if(usu.getContrasena() != null) {
				if(!usu.getContrasena().isEmpty()) {
					usu.setContrasena(StringEncrypt.encrypt(usu.getContrasena()));
					Usuario res = service.getusuarioId(usu.getCodUsuario());
					if(res.getContrasena().equals(usu.getContrasena())){
						if(!usu.getContrasenaNew().isEmpty() && usu.getContrasena() != null) {
							if(usu.getCodUsuario() != null || usu.getCodUsuario().longValue() > 0) {
								if(!usu.getContrasenaNew().equals(res.getContrasena())) {
									usu.setContrasena(StringEncrypt.encrypt(usu.getContrasenaNew()));
									error = service.updateUsuarioSelective(usu);
								}else {
									error.setError(true);
									error.setMensaje(UtilsConstantes.MSG_CONTRASENA_INVALIDA);
									return Response.status(201).entity(gson.toJson(error)).build();
								}
							}else {
								error.setError(true);
								error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodUsuario()");
								return Response.status(201).entity(gson.toJson(error)).build();
							}
						}else {
							error.setError(true);
							error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " ContrasenaNew()");
							return Response.status(201).entity(gson.toJson(error)).build();
						}
					}else {
						error.setError(true);
						error.setMensaje(serviceP.getParametricaIntentos(UtilsConstantes.MSG_ES_CONTRASENA_NO_COINCIDE).get(0).getValorParametro());
						int intMax = Integer.parseInt(serviceP.getPrametricaById(MAXIMO_INTENTOS_FALLIDOS_LOGIN).getValorParametro());
						int inlog = res.getNumeroReintentos().intValue();
						if(inlog == intMax) {
							res.setFlgBloqueado((short) 1);
							res.setFechaVence(new Date());
							res.setAudFechaActualizacion(new Date());
							service.updateUsuarioSelective(res);
							error.setError(true);
							error.setFlgBloqueado((short) 1);
							error.setNumeroReintentos(new BigDecimal(inlog));
							error.setMensaje(serviceP.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_BLOQUEADO).get(0).getValorParametro());
							TokenService serviceT = new TokenService();
							Token tok = new Token();
							tok.setToken(token);
							tok.setAudFechaActualizacion(new Date());
							tok.setCodUsuario(usu.getCodUsuario());
							serviceT.updateTokenuser(tok);
						}else {
							inlog++;
							res.setFlgBloqueado((short) 0);
							res.setNumeroReintentos(new BigDecimal(0));
							res.setNumeroReintentos(new BigDecimal(inlog));
							res.setAudFechaActualizacion(new Date());
							service.updateUsuarioSelective(res);
							error.setError(true);
							error.setNumeroReintentos(new BigDecimal(inlog));
							error.setFlgBloqueado((short) 0);
						}
						return Response.status(201).entity(gson.toJson(error)).build();
					}
			  }else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " Contrasena()");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(serviceP.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_CONTRASENA).get(0).getValorParametro());
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	
	/**
     * @author: Jose Viscaya 
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @return Este Servicio cambia el ahabeas data a true
     */
	@POST
	@Path ("/aceptarhabeasdata") 
	@Produces("text/plain")
	public Response cambiarhabeasdata(@Context HttpServletRequest req, String json, @HeaderParam("token") String token) {  
		ParametricaService serviceP = new ParametricaService();
		if(!UtilsConstantes.tokenIsValid(token,serviceP.getPrametricaById(new BigDecimal(CODTIMEOUT)).getValorParametro())) {
			return Response.status(201).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		Usuario usu = gson.fromJson(json, Usuario.class);
		Usuario error = new Usuario();
		try {
			if(usu.getCodUsuario() != null) {
				if(usu.getCodUsuario().longValue() > 0) {
					usu.setFlgAceptoHabeasData((short) 1);
					usu.setFechaAceptoHabeas(new Date());
					error = service.updateUsuarioSelective(usu);
				}else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodUsuario()");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodUsuario()");
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	
	/**
     * @author: Jose Viscaya 
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @return Este Servicio consulta datos basicos de la persona
     */
	@POST
	@Path ("/datosbasicos") 
	@Produces("text/plain")
	public Response datosBasicos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token) {  
		ParametricaService serviceP = new ParametricaService();
		if(!UtilsConstantes.tokenIsValid(token,serviceP.getPrametricaById(new BigDecimal(CODTIMEOUT)).getValorParametro())) {
			return Response.status(201).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		Persona usu = gson.fromJson(json, Persona.class);
		Persona error = new Persona();
		try {
			if(usu.getCodPersona() != null) {
				if(usu.getCodPersona().longValue() > 0) {
					error = service.getPersonaPorCodPersona(usu.getCodPersona());
				}else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona() Valor no valido");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona()");
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 8/08/2018 8:41:45 a.m. 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 */
	@POST
	@Path ("/setdatosbasicos") 
	@Produces("text/plain")
	public Response setdatosBasicos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token) {  
		ParametricaService serviceP = new ParametricaService();
		if(!UtilsConstantes.tokenIsValid(token,serviceP.getPrametricaById(new BigDecimal(CODTIMEOUT)).getValorParametro())) {
			return Response.status(201).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		Persona usu = gson.fromJson(json, Persona.class);
		ErrorMensajes error = new ErrorMensajes();
		try {
			if(usu.getCodPersona() != null) {
				if(usu.getCodPersona().longValue() > 0) {
					error = service.setPersonaSelective(usu);
				}else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona() Valor no valido");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona()");
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	
	/**
     * @author: Jose Viscaya 
     * @fecha 25 Juli 2018
     * @param req
     * @param json
     * @return Este Servicio efectua la recuperacion de la contrasena
     */
	@POST
	@Path ("/solicitarrecuperacion") 
	@Produces("text/plain")
	public Response solicitarRecuperacion(@Context HttpServletRequest req, String json) {  
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		Persona usu = gson.fromJson(json, Persona.class);
		Persona error = new Persona();
		try {
			if(usu.getCodPersona() != null) {
				if(usu.getCodPersona().longValue() > 0) {
					error = service.getPersonaPorCodPersona(usu.getCodPersona());
				}else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona()");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona()");
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	
	/**
     * @author: Jose Viscaya 
     * @fecha 25 Juli 2018
     * @param req
     * @param json
     * @return Este Servicio efectua la recuperacion de la contrasena
     */
	@POST
	@Path ("/datoscontacto") 
	@Produces("text/plain")
	public Response datosContacto(@Context HttpServletRequest req, String json, @HeaderParam("token") String token) {  
		ParametricaService serviceP = new ParametricaService();
		if(!UtilsConstantes.tokenIsValid(token,serviceP.getPrametricaById(new BigDecimal(CODTIMEOUT)).getValorParametro())) {
			return Response.status(201).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		Persona usu = gson.fromJson(json, Persona.class);
		DatosAdicionales error = new DatosAdicionales();
		try {
			if(usu.getCodPersona() != null) {
				if(usu.getCodPersona().longValue() > 0) {
					error.setDatoContacto(service.getDatoContactoPorIdPersona(usu.getCodPersona().longValue()));
					error.setDatosDemograficos(service.getatoAdicionalByCodPersona(usu.getCodPersona().longValue()));
				}else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona()");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona()");
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	
	/**
     * @author: Jose Viscaya 
     * @fecha 25 Juli 2018
     * @param req
     * @param json
     * @return Este Servicio efectua la recuperacion de la contrasena
     */
	@POST
	@Path ("/getparametrossistema") 
	@Produces("text/plain")
	public Response getParametrosSistema(@Context HttpServletRequest req, String json) {  
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		Parametrica usu = gson.fromJson(json, Parametrica.class);
		Parametrica error = new Parametrica();
		List<Parametrica> param = new ArrayList<>();
		try {
			if(usu.getCodPadreParametrica() != null) {
				if(usu.getCodPadreParametrica().longValue() > 0) {
					param = service.getPrametricaByPadreiId(usu.getCodPadreParametrica());
				}else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPadreParametrica()");
					param.add(error);
					return Response.status(201).entity(gson.toJson(param)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPadreParametrica()");
				param.add(error);
				return Response.status(201).entity(gson.toJson(param)).build();
			}
			return Response.status(201).entity(gson.toJson(param)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			param.add(error);
			return Response.status(201).entity(gson.toJson(param)).build();
		}
	}		
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 8/08/2018 8:41:45 a.m. 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 */
	@POST
	@Path ("/setdatocontacto") 
	@Produces("text/plain")
	public Response setdatoContacto(@Context HttpServletRequest req, String json, @HeaderParam("token") String token) {  
		ParametricaService serviceP = new ParametricaService();
		if(!UtilsConstantes.tokenIsValid(token,serviceP.getPrametricaById(new BigDecimal(CODTIMEOUT)).getValorParametro())) {
			return Response.status(201).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		DatoContacto usu = gson.fromJson(json, DatoContacto.class);
		ErrorMensajes error = new ErrorMensajes();
		try {
			if(usu.getCodPersona() != null) {
				if(usu.getCodPersona().longValue() > 0) {
					error = service.setDatoContactoSelective(usu);
				}else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona() Valor no valido");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona()");
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 */
	@POST
	@Path ("/setdatosdemograficos") 
	@Produces("text/plain")
	public Response setdatoAdicional(@Context HttpServletRequest req, String json, @HeaderParam("token") String token) {  
		ParametricaService serviceP = new ParametricaService();
		if(!UtilsConstantes.tokenIsValid(token,serviceP.getPrametricaById(new BigDecimal(CODTIMEOUT)).getValorParametro())) {
			return Response.status(201).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		MovileService service = new MovileService();
		DatoAdicional usu = gson.fromJson(json, DatoAdicional.class);
		ErrorMensajes error = new ErrorMensajes();
		try {
			if(usu.getCodPersona() != null) {
				if(usu.getCodPersona().longValue() > 0) {
					error = service.setDatoAdicional(usu);
				}else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona() Valor no valido");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " CodPersona()");
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	/**
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 10:54:53 a.m.
	* @param req
	* @param json
	* @param token
	* @return
	 */
	@POST
	@Path ("/recuperarconreasena") 
	@Produces("text/plain")
	public Response recuperarconreasena(@Context HttpServletRequest req, String json, @HeaderParam("token") String token) {  
		ParametricaService serviceP = new ParametricaService();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		UsuarioService serviceUs = new UsuarioService();
		UsuarioExt usu = gson.fromJson(json, UsuarioExt.class);
		Usuario error = new Usuario();
		try {
			if(usu.getContrasena() != null) {
				if(!usu.getContrasena().isEmpty()) {
					usu.setContrasena(StringEncrypt.encrypt(usu.getContrasena()));
					error = serviceUs.validarPing(usu);
					if(error.isError()){
						return Response.status(201).entity(gson.toJson(error)).build();
					}else{
						usu.setAudFechaActualizacion(new Date());
						error = serviceUs.recuperarContrasena(usu);
						if(!error.isError()) {
							SendMail sendmail = new SendMail();
							String msg = "<h1>Estimado usuario se ha cambiado con Exito su Contrasena Desde la Aplicacion Movil</h1>";
							String subject = "No Repli Dafpt";
							sendmail.sendmailHtml(error.getMensajeTecnico(), msg, subject);
						}
					}
			  }else {
					error.setError(true);
					error.setMensaje(UtilsConstantes.MSG_VALOR_VACIO + " Contrasena()");
					return Response.status(201).entity(gson.toJson(error)).build();
				}
			}else {
				error.setError(true);
				error.setMensaje(serviceP.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_CONTRASENA).get(0).getValorParametro());
				return Response.status(201).entity(gson.toJson(error)).build();
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	/**
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 11:35:04 a.m.
	* @param req
	* @param json
	* @param token
	* @return
	 */
	@POST
	@Path ("/generarping") 
	@Produces("text/plain")
	public Response generarPing(@Context HttpServletRequest req, String json) {  
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Persona persona = gson.fromJson(json, Persona.class);
		Usuario error = new Usuario();
		UsuarioService service = new UsuarioService();
		ErrorMensajes rep = new ErrorMensajes();
		try {
			error = service.generarPing(persona);
			if(!error.isError()) {
				SendMail sendmail = new SendMail();
				String msg = "<h1>Estimado usuario este es su Ping de Seguridad:</h1><br/><h3>Ping:<b>"+error.getTicket()+"</b>";
				String subject = "No Repli Dafpt";
				if(error.getMensajeTecnico() !=null) {
					if(sendmail.sendmailHtml(error.getMensajeTecnico(), msg, subject).equals("true")) {
						rep.setError(false);
						rep.setMensaje("Asignacion Con Exito");
						return Response.status(201).entity(gson.toJson(rep)).build();
					}else {
						rep.setError(true);
						rep.setMensaje("Email Invalido.");
						return Response.status(201).entity(gson.toJson(rep)).build();
					}
				}else {
					rep.setError(true);
					rep.setMensaje("No tiene Email el Usuario.");
					return Response.status(201).entity(gson.toJson(rep)).build();
				}
			}
			return Response.status(201).entity(gson.toJson(error)).build();
		} catch (Exception e) {
			error.setError(true);
			error.setMensaje(UtilsConstantes.MSG_EXEPCION);
			error.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(error)).build();
		}
	}
	
}
