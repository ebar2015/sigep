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

import co.gov.dafp.sigep2.bean.DatoAdicional;
import co.gov.dafp.sigep2.bean.DatoContacto;
import co.gov.dafp.sigep2.bean.DocumentosAdicionalesHv;
import co.gov.dafp.sigep2.bean.EducacionFormal;
import co.gov.dafp.sigep2.bean.Entidad;
import co.gov.dafp.sigep2.bean.EvaluacionDesempeno;
import co.gov.dafp.sigep2.bean.ExperienciaDocente;
import co.gov.dafp.sigep2.bean.ExperienciaProfesional;
import co.gov.dafp.sigep2.bean.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.bean.HojaVida;
import co.gov.dafp.sigep2.bean.LogroRecurso;
import co.gov.dafp.sigep2.bean.NacionalidadPerfil;
import co.gov.dafp.sigep2.bean.OtroConocimiento;
import co.gov.dafp.sigep2.bean.ParticipacionInstitucion;
import co.gov.dafp.sigep2.bean.ParticipacionProyecto;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.PreguntaOpinion;
import co.gov.dafp.sigep2.bean.Publicacion;
import co.gov.dafp.sigep2.bean.Reconocimiento;
import co.gov.dafp.sigep2.bean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.bean.ext.DocumentosAdicionalesHvExt;
import co.gov.dafp.sigep2.bean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.bean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.bean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.bean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.bean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.bean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.bean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.PublicacionExt;
import co.gov.dafp.sigep2.bean.ext.ReconocimientoExt;
import co.gov.dafp.sigep2.bean.ext.VinculacionExt;
import co.gov.dafp.sigep2.constante.AuditoriaConstantes;
import co.gov.dafp.sigep2.services.AuditoriaService;
import co.gov.dafp.sigep2.services.DatoAdicionalService;
import co.gov.dafp.sigep2.services.DatoContactoService;
import co.gov.dafp.sigep2.services.DocumentosAdicionalesHvService;
import co.gov.dafp.sigep2.services.EducacionFormalService;
import co.gov.dafp.sigep2.services.EntidadService;
import co.gov.dafp.sigep2.services.EvaluacionDesempenoService;
import co.gov.dafp.sigep2.services.ExperienciaDocenteService;
import co.gov.dafp.sigep2.services.ExperienciaProfesionalService;
import co.gov.dafp.sigep2.services.HistoricoModificacionHojaVidaService;
import co.gov.dafp.sigep2.services.HojaVidaService;
import co.gov.dafp.sigep2.services.LogroRecursoService;
import co.gov.dafp.sigep2.services.NacionalidadPerfilService;
import co.gov.dafp.sigep2.services.OtroConocimientoService;
import co.gov.dafp.sigep2.services.ParticipacionInstitucionService;
import co.gov.dafp.sigep2.services.ParticipacionProyectoService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.services.PreguntaOpinionService;
import co.gov.dafp.sigep2.services.PublicacionService;
import co.gov.dafp.sigep2.services.ReconocimientoService;
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
@Path("/sigephv")
public class ServicesSigepRestHV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2599571539343766265L;
	private Gson gson;

	public ServicesSigepRestHV() {
		LoggerSigep.getInstance().configureAppender();
	}
	private static AuditoriaSeguridad auditoriaSeguridad;

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
		return Response.status(201).entity("Hello: Services Context sigephv... " + msg).build();
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
	@Path("/evaldesempusent")
	@Consumes("text/plain")
	public Response evaldesempusent(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EvaluacionDesempeno user = gson.fromJson(json, EvaluacionDesempeno.class);
			EvaluacionDesempenoService service = new EvaluacionDesempenoService();
			try {
				List<EvaluacionDesempeno> d = service.evaluacionDesempenoPorUSEN(user.getCodPersona(),
						user.getCodEntidad());
				if (d.size() > 0) {

					for (int i = 0; i < d.size(); i++) {
						d.get(i).setError(false);
					}
				}
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				EvaluacionDesempeno us = new EvaluacionDesempeno();
				List<EvaluacionDesempeno> nl = new ArrayList<EvaluacionDesempeno>();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				nl.add(us);
				return Response.status(201).entity(gson.toJson(nl)).build();
			}
		} catch (JsonParseException e) {
			EvaluacionDesempeno us = new EvaluacionDesempeno();
			List<EvaluacionDesempeno> nl = new ArrayList<EvaluacionDesempeno>();
			us.setError(true);
			us.setMensaje("Error Al comunicarse a la Base de Datos");
			us.setMensajeTecnico(e.getMessage());
			nl.add(us);
			return Response.status(201).entity(gson.toJson(nl)).build();
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
	@Path("/evaldesempusentfe")
	@Consumes("text/plain")
	public Response evaluacionDesempenoPorUSENfe(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			EvaluacionDesempeno user = gson.fromJson(json, EvaluacionDesempeno.class);
			EvaluacionDesempenoService service = new EvaluacionDesempenoService();
			try {
				user = service.evaluacionDesempenoPorUSENfe(user.getCodPersona(), user.getCodEntidad());
				user.setError(false);
				String out = gson.toJson(user);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				EvaluacionDesempeno us = new EvaluacionDesempeno();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			EvaluacionDesempeno us = new EvaluacionDesempeno();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Mensaje Json");
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
	@Path("/expprofesionalporid")
	@Consumes("text/plain")
	public Response experienciaProfesionalPorId(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			ExperienciaProfesionalExt user = gson.fromJson(json, ExperienciaProfesionalExt.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			try {
				user = service.experienciaProfesionalById(user.getCodExperienciaProfesional());
				user.setError(false);
				String out = gson.toJson(user);
				return Response.status(201).entity(out).build();
			} catch (Exception e) {
				ExperienciaProfesional us = new ExperienciaProfesional();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}

		} catch (JsonParseException e) {
			ExperienciaProfesional us = new ExperienciaProfesional();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Json");
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
	@Path("/setexpprofesional")
	@Consumes("text/plain")
	public Response setexperienciaProfesional(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		ExperienciaProfesional res = new ExperienciaProfesional();
		try {
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			user.setAudFechaActualizacion(new Date());
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodExperienciaProfesional() > 0) {
					res = service.updateExperienciaProfesional(user);
				} else {
					if (user.getCodPersona().longValue() > 0) {
						user.setFlgActivo((short) 1);
						res = service.insertExperienciaProfesional(user);
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					}
				}
				return Response.status(201).entity(gson.toJson(res)).build();
			} catch (NullPointerException e) {
				try {
					if (user.getCodPersona().longValue() > 0) {
						user.setFlgActivo((short) 1);
						res = service.insertExperienciaProfesional(user);
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					}
					return Response.status(201).entity(gson.toJson(res)).build();
				} catch (Exception e2) {
					res.setError(true);
					res.setMensaje(UtilsConstantes.MSG_EXEPCION);
					res.setMensajeTecnico(e2.getMessage());
					return Response.status(201).entity(gson.toJson(res)).build();
				}
			}
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
	@Path("/experienciadoc001")
	@Consumes("text/plain")
	public Response getExperianciaDocente(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaDocente user = gson.fromJson(json, ExperienciaDocente.class);
			if (user.getCodExperienciaDocente() > 0) {
				ExperienciaDocenteService service = new ExperienciaDocenteService();
				try {
					user = service.getExpericiado(user.getCodExperienciaDocente());
					user.setError(false);
					String out = gson.toJson(user);
					out = out.replace("\"flgActivo\":1,", "\"flgActivo\":true,");
					out = out.replace("\"flgActivo\":0,", "\"flgActivo\":false,");
					out = out.replace("\"flgEstado\":1,", "\"flgEstado\":true,");
					out = out.replace("\"flgEstado\":0,", "\"flgEstado\":false,");
					out = out.replace("\"flgActualmente\":1,", "\"flgActualmente\":true,");
					out = out.replace("\"flgActualmente\":0,", "\"flgActualmente\":false,");
					out = out.replace("\"flgVerificado\":1,", "\"flgVerificado\":true,");
					out = out.replace("\"flgVerificado\":0,", "\"flgVerificado\":false,");
					out = out.replace("\"flgEntidadPublica\":1,", "\"flgEntidadPublica\":true,");
					out = out.replace("\"flgEntidadPublica\":0,", "\"flgEntidadPublica\":false,");
					return Response.status(201).entity(out).build();
				} catch (NullPointerException e) {
					ExperienciaDocente us = new ExperienciaDocente();
					us.setError(true);
					us.setMensaje("Faltan Datos Esperados");
					us.setMensajeTecnico(e.getMessage());
					return Response.status(201).entity(gson.toJson(us)).build();
				}
			} else {
				ExperienciaDocente us = new ExperienciaDocente();
				us.setError(true);
				us.setMensaje("Faltan CodExperianciaDocente");
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			ExperienciaDocente us = new ExperienciaDocente();
			us.setError(true);
			us.setMensaje("Error al Serialiar el Json");
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
	@Path("/educacionformal001")
	@Consumes("text/plain")
	public Response getExperianciaFormal001(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		json = json.replace("flgActivo\":true", "flgActivo\":1");
		json = json.replace("flgActivo\":false", "flgActivo\":0");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EducacionFormal user = gson.fromJson(json, EducacionFormal.class);
			try {
				if (user.getCodPersona().intValue() > 0) {
					EducacionFormalService service = new EducacionFormalService();
					try {
						List<EducacionFormalExt> d = service.selectByCodPersona001(user);
						if (d.size() > 0) {
							for (int i = 0; i < d.size(); i++) {
								d.get(i).setError(false);
							}
						}
						String out = gson.toJson(d);
						out = out.replace("flgFinalizado\":0", "flgFinalizado\":false");
						out = out.replace("flgFinalizado\":1", "flgFinalizado\":true");
						out = out.replace("flgEducacionExterior\":1", "flgEducacionExterior\":true");
						out = out.replace("flgEducacionExterior\":0", "flgEducacionExterior\":false");
						out = out.replace("estudioConvalidado\":1", "estudioConvalidado\":true");
						out = out.replace("estudioConvalidado\":0", "estudioConvalidado\":false");
						out = out.replace("flgValidadoRrhh\":1", "flgValidadoRrhh\":true");
						out = out.replace("flgValidadoRrhh\":0", "flgValidadoRrhh\":false");
						out = out.replace("flgActivo\":1", "flgActivo\":true");
						out = out.replace("flgActivo\":0", "flgActivo\":false");
						out = out.replace("flgValidadoTarjetaProfesional\":1", "flgValidadoTarjetaProfesional\":true");
						out = out.replace("flgValidadoTarjetaProfesional\":0", "flgValidadoTarjetaProfesional\":false");						
						return Response.status(201).entity(out).build();
					} catch (NullPointerException e) {
						EducacionFormalExt us = new EducacionFormalExt();
						List<EducacionFormalExt> nl = new ArrayList<EducacionFormalExt>();
						us.setError(true);
						us.setMensaje("Faltan Datos Esperados");
						us.setMensajeTecnico(e.getMessage());
						nl.add(us);
						return Response.status(201).entity(gson.toJson(nl)).build();
					}
				} else {
					EducacionFormalExt us = new EducacionFormalExt();
					List<EducacionFormalExt> nl = new ArrayList<EducacionFormalExt>();
					us.setError(true);
					us.setMensaje("Faltan CodPersona");
					nl.add(us);
					return Response.status(201).entity(gson.toJson(nl)).build();
				}
			} catch (NullPointerException e) {
				EducacionFormalExt us = new EducacionFormalExt();
				List<EducacionFormalExt> nl = new ArrayList<EducacionFormalExt>();
				us.setError(true);
				us.setMensaje("Faltan CodPersona");
				nl.add(us);
				return Response.status(201).entity(gson.toJson(nl)).build();
			}

		} catch (JsonParseException e) {
			EducacionFormalExt us = new EducacionFormalExt();
			List<EducacionFormalExt> nl = new ArrayList<EducacionFormalExt>();
			us.setError(true);
			us.setMensaje("Error Al Serialiar el Json");
			us.setMensajeTecnico(e.getMessage());
			nl.add(us);
			return Response.status(201).entity(gson.toJson(nl)).build();
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
	@Path("/expeproporcodpersona")
	@Consumes("text/plain")
	public Response expeproporcodpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			if (user.getCodPersona().intValue() > 0) {
				ExperienciaProfesionalService service = new ExperienciaProfesionalService();
				try {
					List<ExperienciaProfesional> d = service.expProfesionalPorcodigoPer(user);
					if (d.size() > 0) {
						for (int i = 0; i < d.size(); i++) {
							d.get(i).setError(false);
						}
					}
					String out = gson.toJson(d);
					return Response.status(201).entity(out).build();
				} catch (NullPointerException e) {
					ExperienciaProfesional us = new ExperienciaProfesional();
					List<ExperienciaProfesional> nl = new ArrayList<ExperienciaProfesional>();
					us.setError(true);
					us.setMensaje("Faltan Datos Esperados");
					us.setMensajeTecnico(e.getMessage());
					nl.add(us);
					return Response.status(201).entity(gson.toJson(nl)).build();
				}
			} else {
				ExperienciaProfesional us = new ExperienciaProfesional();
				List<ExperienciaProfesional> nl = new ArrayList<ExperienciaProfesional>();
				us.setError(true);
				us.setMensaje("Falta CodPersona");
				nl.add(us);
				return Response.status(201).entity(gson.toJson(nl)).build();
			}
		} catch (JsonParseException e) {
			ExperienciaProfesional us = new ExperienciaProfesional();
			List<ExperienciaProfesional> nl = new ArrayList<ExperienciaProfesional>();
			us.setError(true);
			us.setMensaje("Error Al Serializar El Json");
			us.setMensajeTecnico(e.getMessage());
			nl.add(us);
			return Response.status(201).entity(gson.toJson(nl)).build();
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
			DatoContacto user = gson.fromJson(json, DatoContacto.class);
			if (user.getCodPersona().intValue() > 0) {
				DatoContactoService service = new DatoContactoService();
				user = service.getDatoContactoPorIdPersona(user.getCodPersona().longValue());
				user.setError(false);
				String out = gson.toJson(user);
				return Response.status(201).entity(out).build();
			} else {
				DatoContacto us = new DatoContacto();
				us.setError(true);
				us.setMensaje("Faltan CodPersona");
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
		DatoContacto us = new DatoContacto();
		try {
			DatoContacto user = gson.fromJson(json, DatoContacto.class);
			user.setAudFechaActualizacion(new Date());
			DatoContactoService service = new DatoContactoService();
			if(auditJson != null) {
				auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
				if(auditoriaSeguridad.getCodUsuario() != null) {
					user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
				}
				if(auditoriaSeguridad.getCodUsuarioRol() != null) {
					user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
				}
			}
			if (user.getCodDatosContacto() != null) {
				us = service.updateDatoContacto(user);
			} else {
				if (user.getCodPersona() != null) {
					us = service.insertDatoContacto(user);
				} else {
					us.setError(true);
					us.setMensaje("Falta CodPersona");
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
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getdatocontactoadiporpersona")
	@Consumes("text/plain")
	public Response getDatoContactoAdiPorIdPersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DatoAdicionalExt us = new DatoAdicionalExt();
		try {
			DatoContacto user = gson.fromJson(json, DatoContacto.class);
			if (user.getCodPersona() != null) {
				DatoAdicionalService service = new DatoAdicionalService();
				us = service.getatoAdicionalByCodPersona(user.getCodPersona().longValue());
			} else {
				us.setError(true);
				us.setMensaje("Error Falta CodPersona");
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
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setdatocontactoadiporpersona")
	@Consumes("text/plain")
	public Response setDatocontactoadiporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		json = json.replace(":0,", ":null,");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DatoAdicional us = new DatoAdicional();
		try {
			DatoAdicional user = gson.fromJson(json, DatoAdicional.class);
			user.setAudFechaActualizacion(new Date());
			DatoAdicionalService service = new DatoAdicionalService();
			if(auditJson != null) {
				auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
				if(auditoriaSeguridad.getCodUsuario() != null) {
					user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
				}
				if(auditoriaSeguridad.getCodUsuarioRol() != null) {
					user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
				}
			}
			if (user.getCodDatoAdicional() != null) {
				us = service.updateDatoAdicional(user);
			} else if (user.getCodPersona() != null) {
				us = service.insertDatoAdicional(user);
			} else {
				us.setError(true);
				us.setMensaje("Falta el Parametro CodPersona()");
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
	 * 
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setexperienciadoc")
	@Consumes("text/plain")
	public Response setExperienciaDoc(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		json = json.replace(":false", ":0");
		json = json.replace(":true", ":1");
		json = json.replace("\"error\":0", "\"error\":false");
		json = json.replace("\"error\":1", "\"error\":true");
		ExperienciaDocente res = new ExperienciaDocente();
		try {
			ExperienciaDocente user = gson.fromJson(json, ExperienciaDocente.class);
			user.setAudFechaActualizacion(new Date());
			ExperienciaDocenteService service = new ExperienciaDocenteService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodExperienciaDocente() > 0) {
					res = service.updateExperienciaDocente(user);
				} else {
					if (user.getCodPersona().longValue() > 0) {
						res = service.insertExperienciaDocente(user);
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					}
				}
				return Response.status(201).entity(gson.toJson(res)).build();
			} catch (NullPointerException e) {
				try {
					if (user.getCodPersona().longValue() > 0) {
						res = service.insertExperienciaDocente(user);
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					}
				} catch (Exception e2) {
					res.setError(true);
					res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					res.setMensajeTecnico(e2.getMessage());
					return Response.status(201).entity(gson.toJson(res)).build();
				}
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
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
	@Path("/getlogrorecursoporpersona")
	@Consumes("text/plain")
	public Response getlogrorecursoporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			LogroRecurso user = gson.fromJson(json, LogroRecurso.class);
			if (user.getCodPersona().intValue() > 0) {
				LogroRecursoService service = new LogroRecursoService();
				user = service.getLogroRecursoByPersona(user.getCodPersona());
				String out = gson.toJson(user);
				return Response.status(201).entity(out).build();
			} else {
				return Response.status(201).entity("{}").build();
			}
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
	@Path("/setlogrorecursoporpersona")
	@Consumes("text/plain")
	public Response setlogrorecursoporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		LogroRecurso res = new LogroRecurso();
		try {
			LogroRecurso user = gson.fromJson(json, LogroRecurso.class);
			user.setAudFechaActualizacion(new Date());
			LogroRecursoService service = new LogroRecursoService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodLogroRecurso() > 0) {
					res = service.updateLogroRecurso(user);
				} else {
					if (user.getCodPersona().longValue() > 0) {
						res = service.insertLogroRecurso(user);
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					}
				}
				return Response.status(201).entity(gson.toJson(res)).build();
			} catch (NullPointerException e) {
				try {
					if (user.getCodPersona().longValue() > 0) {
						res = service.insertLogroRecurso(user);
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					}
				} catch (Exception e2) {
					res.setError(true);
					res.setMensaje(UtilsConstantes.MSG_EXEPCION);
					res.setMensajeTecnico(e2.getMessage());
					return Response.status(201).entity(gson.toJson(res)).build();
				}
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
	@Path("/evaldesempporpersona")
	@Consumes("text/plain")
	public Response evaldesempporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			EvaluacionDesempenoExt user = gson.fromJson(json, EvaluacionDesempenoExt.class);
			EvaluacionDesempenoService service = new EvaluacionDesempenoService();
			user = service.evaluacionDesempenoPorpersona(user);
			String out = gson.toJson(user);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/setevaluaciondesempeno")
	@Consumes("text/plain")
	public Response setevaluaciondesempeno(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		EvaluacionDesempeno res = new EvaluacionDesempeno();
		try {
			EvaluacionDesempeno user = gson.fromJson(json, EvaluacionDesempeno.class);
			user.setAudFechaActualizacion(new Date());
			EvaluacionDesempenoService service = new EvaluacionDesempenoService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodEvaluacionDesempeno() > 0) {
					res = service.updateEvaluacionDesempeno(user);
					return Response.status(201).entity(gson.toJson(res)).build();
				} else {
					if (user.getCodPersona().longValue() > 0) {
						res = service.insertEvaluacionDesempeno(user);
						return Response.status(201).entity(gson.toJson(res)).build();
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
						return Response.status(201).entity(gson.toJson(res)).build();
					}
				}
			} catch (NullPointerException e) {
				try {
					if (user.getCodPersona().longValue() > 0) {
						res = service.insertEvaluacionDesempeno(user);
						return Response.status(201).entity(gson.toJson(res)).build();
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
						return Response.status(201).entity(gson.toJson(res)).build();
					}
				} catch (Exception e2) {
					res.setError(true);
					res.setMensaje(UtilsConstantes.MSG_EXEPCION);
					res.setMensajeTecnico(e2.getMessage());
					return Response.status(201).entity(gson.toJson(res)).build();
				}
			}
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
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
	@Path("/getreconocimientoporpersona")
	@Consumes("text/plain")
	public Response getreconocimientoporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ReconocimientoExt user = gson.fromJson(json, ReconocimientoExt.class);
			ReconocimientoService service = new ReconocimientoService();
			user = service.getReconocimientoByPersona(user.getCodPersona());
			String out = gson.toJson(user);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/setreconocimientoporpersona")
	@Consumes("text/plain")
	public Response setreconocimientoporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Reconocimiento user = gson.fromJson(json, Reconocimiento.class);
			user.setAudFechaActualizacion(new Date());
			ReconocimientoService service = new ReconocimientoService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodReconocimiento() > 0) {
					boolean d = service.updateReconocimiento(user);
					return Response.status(201).entity(d).build();
				} else {
					if (user.getCodPersona().longValue() > 0) {
						boolean d = service.insertReconocimiento(user);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}

				}
			} catch (NullPointerException e) {
				try {
					if (user.getCodPersona().longValue() > 0) {
						boolean d = service.insertReconocimiento(user);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}
				} catch (Exception e2) {
					return Response.status(201).entity(false).build();
				}
			}
		} catch (JsonParseException e) {
			return Response.status(201).entity(false).build();
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
	@Path("/getpublicacionoporpersona")
	@Consumes("text/plain")
	public Response getPublicacionoporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			PublicacionExt user = gson.fromJson(json, PublicacionExt.class);
			PublicacionService service = new PublicacionService();
			user = service.getPublicacionByPersona(user.getCodPersona());
			String out = gson.toJson(user);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/setpublicacionoporpersona")
	@Consumes("text/plain")
	public Response setpublicacionoporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Publicacion user = gson.fromJson(json, Publicacion.class);
			user.setAudFechaActualizacion(new Date());
			PublicacionService service = new PublicacionService();
			if(auditJson != null) {
				auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
				if(auditoriaSeguridad.getCodUsuario() != null) {
					user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
				}
				if(auditoriaSeguridad.getCodUsuarioRol() != null) {
					user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
				}
			}
			if (user.getCodPublicacion() != null && user.getCodPublicacion() > 0) {
				boolean d = service.updatePublicacion(user);
				return Response.status(201).entity(d).build();
			} else if (user.getCodPersona() != null && user.getCodPersona().longValue() > 0) {
				boolean d = service.insertPublicacion(user);
				return Response.status(201).entity(d).build();
			} else {
				return Response.status(201).entity(false).build();
			}
		} catch (JsonParseException e) {
			return Response.status(201).entity(false).build();
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
	@Path("/getparticipacionInstitucionporpersona")
	@Consumes("text/plain")
	public Response getParticipacionInstitucionporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			ParticipacionInstitucion user = gson.fromJson(json, ParticipacionInstitucion.class);
			ParticipacionInstitucionService service = new ParticipacionInstitucionService();
			user = service.getParticipacionInstitucionByPersona(user.getCodPersona());
			String out = gson.toJson(user);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/setparticipacionInstitucionporpersona")
	@Consumes("text/plain")
	public Response setparticipacionInstitucionporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ParticipacionInstitucion user = gson.fromJson(json, ParticipacionInstitucion.class);
			user.setAudFechaActualizacion(new Date());
			ParticipacionInstitucionService service = new ParticipacionInstitucionService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodParticipacionInstitucion() > 0) {
					boolean d = service.updateParticipacionInstitucion(user);
					return Response.status(201).entity(d).build();
				} else {
					if (user.getCodPersona().longValue() > 0) {
						boolean d = service.insertParticipacionInstitucion(user);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}
				}
			} catch (NullPointerException e) {
				try {
					if (user.getCodPersona().longValue() > 0) {
						boolean d = service.insertParticipacionInstitucion(user);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}
				} catch (Exception e2) {
					return Response.status(201).entity(false).build();
				}

			}

		} catch (JsonParseException e) {
			return Response.status(201).entity(false).build();
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
	@Path("/getparticipacionProyeporpersona")
	@Consumes("text/plain")
	public Response getParticipacionProyeporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			ParticipacionProyectoExt user = gson.fromJson(json, ParticipacionProyectoExt.class);
			ParticipacionProyectoService service = new ParticipacionProyectoService();
			try {
				user = service.getParticipacionProyectoByPersona(user.getCodPersona());
				String out = gson.toJson(user);
				return Response.status(201).entity(out).build();
			} catch (Exception e) {
				return Response.status(201).entity("{}").build();
			}

		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/setparticipacionProyeporpersona")
	@Consumes("text/plain")
	public Response setparticipacionProyeporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ParticipacionProyecto user = gson.fromJson(json, ParticipacionProyecto.class);
			user.setAudFechaActualizacion(new Date());
			ParticipacionProyectoService service = new ParticipacionProyectoService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodParticipacionProyecto() > 0) {
					boolean d = service.updateParticipacionProyecto(user);
					return Response.status(201).entity(d).build();
				} else {
					if (user.getCodPersona().longValue() > 0) {
						boolean d = service.insertParticipacionProyecto(user);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}
				}
			} catch (NullPointerException e) {
				try {
					if (user.getCodPersona().longValue() > 0) {
						boolean d = service.insertParticipacionProyecto(user);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}
				} catch (Exception e2) {
					return Response.status(201).entity(false).build();
				}
			}
		} catch (JsonParseException e) {
			return Response.status(201).entity(false).build();
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
	@Path("/eliminarhojadevida")
	@Consumes("text/plain")
	public Response eliminarHojaVida(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			HojaVidaExt user = gson.fromJson(json, HojaVidaExt.class);
			HojaVidaService service = new HojaVidaService();
			boolean d = service.eliminarHojaDeVida(user);
			return Response.status(201).entity(d).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(false).build();
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
	@Path("/geexperienciadocentepocodper")
	@Consumes("text/plain")
	public Response geExperienciaDocenteBycodPer(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		json = json.replace("\"flgActivo\":true", "\"flgActivo\":1");
		json = json.replace("\"flgActivo\":false", "\"flgActivo\":0");
		try {
			ExperienciaDocente user = gson.fromJson(json, ExperienciaDocente.class);
			ExperienciaDocenteService service = new ExperienciaDocenteService();
			try {
				List<ExperienciaDocenteExt> d = service.geExperienciaDocenteBycodPer(user);
				String out = gson.toJson(d);
				out = out.replace("\"flgActivo\":1", "\"flgActivo\":true");
				out = out.replace("\"flgActivo\":0", "\"flgActivo\":false");
				out = out.replace("\"flgEstado\":1", "\"flgEstado\":true");
				out = out.replace("\"flgEstado\":0", "\"flgEstado\":false");
				out = out.replace("\"flgActualmente\":1", "\"flgActualmente\":true");
				out = out.replace("\"flgActualmente\":0", "\"flgActualmente\":false");
				out = out.replace("\"flgVerificado\":1", "\"flgVerificado\":true");
				out = out.replace("\"flgVerificado\":0", "\"flgVerificado\":false");
				out = out.replace("\"flgEntidadPublica\":1", "\"flgEntidadPublica\":true");
				out = out.replace("\"flgEntidadPublica\":0", "\"flgEntidadPublica\":false");
				return Response.status(201).entity(out).build();
			} catch (Exception e) {
				return Response.status(201).entity("{}").build();
			}
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getdifdias")
	@Consumes("text/plain")
	public Response getdifDias(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaDocente user = gson.fromJson(json, ExperienciaDocente.class);
			ExperienciaDocenteService service = new ExperienciaDocenteService();
			int d = service.getDifdias(user.getCodPersona());
			return Response.status(201).entity(d).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getexperenciaprotipoentidad")
	@Consumes("text/plain")
	public Response getExperenciaProPortipoEntidad(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesional> d = service.getExperienciaTipoentidad(user.getCodTipoEntidad());
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getentidadporcodtipoentidad")
	@Consumes("text/plain")
	public Response getEntidadporCodTipoEntidad(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Entidad user = gson.fromJson(json, Entidad.class);
			EntidadService service = new EntidadService();
			List<Entidad> d = service.getEntidadporCodTipoEntidad(user.getCodTipoEntidad());
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/setotroconocimiento")
	@Consumes("text/plain")
	public Response setOtroConocimiento(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		json = json.replace(":0,", ":null,");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			OtroConocimiento user = gson.fromJson(json, OtroConocimiento.class);
			user.setAudFechaActualizacion(new Date());
			OtroConocimientoService service = new OtroConocimientoService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodOtroConocimiento().longValue() > 0) {
					boolean d = service.updateOtroConocimiento(user);
					return Response.status(201).entity(d).build();
				} else {
					if (user.getCodPersona().longValue() > 0) {
						boolean d = service.insertOtroConocimiento(user);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}
				}
			} catch (NullPointerException e) {
				if (user.getCodPersona().longValue() > 0) {
					boolean d = service.insertOtroConocimiento(user);
					return Response.status(201).entity(d).build();
				} else {
					return Response.status(201).entity(false).build();
				}
			}
		} catch (JsonParseException e) {
			return Response.status(201).entity(false).build();
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
	@Path("/seteducacionformal")
	@Consumes("text/plain")
	public Response seteducacionformal(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		json = json.replace(":false", ":0");
		json = json.replace(":true", ":1");
		json = json.replace("error\":0", "error\":false");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		EducacionFormal res = new EducacionFormal();
		try {
			EducacionFormal user = gson.fromJson(json, EducacionFormal.class);
			user.setAudFechaActualizacion(new Date());
			EducacionFormalService service = new EducacionFormalService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodEducacionFormal() > 0) {
					res = service.updateEducacionFormal(user);
					return Response.status(201).entity(gson.toJson(res)).build();
				} else {
					if (user.getCodPersona().longValue() > 0) {
						user.setFlgActivo((short) 1);
						user.setFlgValidadoRrhh((short) 0);
						res = service.insertEducacionFormal(user);
						return Response.status(201).entity(gson.toJson(res)).build();
					} else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
						return Response.status(201).entity(gson.toJson(res)).build();
					}
				}
			} catch (NullPointerException e) {
				if (user.getCodPersona().longValue() > 0) {
					user.setFlgActivo((short) 1);
					user.setFlgValidadoRrhh((short) 0);
					res = service.insertEducacionFormal(user);
					return Response.status(201).entity(gson.toJson(res)).build();
				} else {
					res.setError(true);
					res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					return Response.status(201).entity(gson.toJson(res)).build();
				}
			}
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
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
	@Path("/getotroconocimientoporpersona")
	@Consumes("text/plain")
	public Response getotroconocimientoporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			json = json.replace("\"flgActivo\":true", "\"flgActivo\":1");
			json = json.replace("\"flgActivo\":false", "\"flgActivo\":0");
			OtroConocimiento user = gson.fromJson(json, OtroConocimiento.class);
			OtroConocimientoService service = new OtroConocimientoService();
			List<OtroConocimientoExt> d = service.getOtroConocimientoPorPersona(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getpreguntaopoinionfechaini")
	@Consumes("text/plain")
	public Response getpreguntaopoinionfechaini(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getPreguntaOpinionPorFechaIni(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getpreguntaopoinionfechafin")
	@Consumes("text/plain")
	public Response getpreguntaopoinionfechafin(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getPreguntaOpinionPorFechaFin(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getpreguntaopoinionentrefechaini")
	@Consumes("text/plain")
	public Response getpreguntaopoinionentrefechaini(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getPreguntaOpinionEntreFechaIni(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getpreguntaopoinionentrefechafin")
	@Consumes("text/plain")
	public Response getpreguntaopoinionentrefechafin(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getPreguntaOpinionEntreFechaFin(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getpreguntaopoinionentrefechafinini")
	@Consumes("text/plain")
	public Response getpreguntaopoinionentrefechafinini(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getPreguntaOpinionEntreFechaIniFin(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getdifdiasporpersona")
	@Consumes("text/plain")
	public Response getdifdiasporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			ExperienciaProfesionalExt d = service.getDifDias(user.getCodPersona());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getcargosexperienciapro")
	@Consumes("text/plain")
	public Response getcargosexperienciapro(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		ExperienciaProfesional experienciaProfesional = gson.fromJson(json, ExperienciaProfesional.class);
		try {
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.getCargos(experienciaProfesional);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("[]").build();
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
	@Path("/getexperienciaprofesionalporpersona")
	@Consumes("text/plain")
	public Response getexperienciaprofesionalporpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.getexpProfesionalPorcodigoPer(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getcargosexperienciapublic")
	@Consumes("text/plain")
	public Response getcargosexperienciapublic(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesional> d = service.getCargosPublicos();
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("[]").build();
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
	@Path("/getcargoentidadpersona")
	@Consumes("text/plain")
	public Response getCargoEntidadPersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			json = json.replace("\"flgActivo\":true", "\"flgActivo\":1");
			json = json.replace("\"flgActivo\":false", "\"flgActivo\":0");
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.getCargoEntidadPersona(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getexperienciacalculo")
	@Consumes("text/plain")
	public Response getExperienCiacalculo(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.getExperienCiacalculo(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * @author: Franklin Zapata
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getexperienciadocentecalculo")
	@Consumes("text/plain")
	public Response getExperienCiaDocenteCalculo(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaDocente user = gson.fromJson(json, ExperienciaDocente.class);
			ExperienciaDocenteService service = new ExperienciaDocenteService();
			List<ExperienciaDocenteExt> d = service.getExperienCiaDocenteCalculo(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getmodificacionhojavidapersona")
	@Consumes("text/plain")
	public Response getModificacionHojaVidaPersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			HistoricoModificacionHojaVida hmHv = gson.fromJson(json, HistoricoModificacionHojaVida.class);
			HistoricoModificacionHojaVidaService service = new HistoricoModificacionHojaVidaService();
			List<HistoricoModificacionHojaVida> d = service.getHistoricoModificacionHojaVidaPersona(hmHv.getCodHojaVida());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/setmodificacionhohadevida")
	@Consumes("text/plain")
	public Response setmodificacionhohadevida(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			HistoricoModificacionHojaVida user = gson.fromJson(json, HistoricoModificacionHojaVida.class);
			user.setAudFechaModificacion(new Date());
			HistoricoModificacionHojaVidaService service = new HistoricoModificacionHojaVidaService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						user.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						user.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (user.getCodModificacion() > 0) {
					boolean d = service.updateHistoricoModificacionHojaVida(user);
					return Response.status(201).entity(d).build();
				} else {
					if (user.getCodHojaVida().longValue() > 0) {
						boolean d = service.insertHistoricoModificacionHojaVida(user);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}
				}
			} catch (NullPointerException e) {
				if (user.getCodHojaVida().longValue() > 0) {
					boolean d = service.insertHistoricoModificacionHojaVida(user);
					return Response.status(201).entity(d).build();
				} else {
					return Response.status(201).entity(false).build();
				}
			}
		} catch (JsonParseException e) {
			return Response.status(201).entity(false).build();
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
	@Path("/getotroconocimientoporid")
	@Consumes("text/plain")
	public Response getotroconocimientoporid(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			OtroConocimiento user = gson.fromJson(json, OtroConocimiento.class);
			OtroConocimientoService service = new OtroConocimientoService();
			OtroConocimientoExt d = service.getOtroConocimientoPorId(user.getCodOtroConocimiento());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.printStackTrace();
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/setpreguntaopinion")
	@Consumes("text/plain")
	public Response setpersonaparentescopersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		PreguntaOpinion res = new PreguntaOpinion();
		res.setError(true);
		res.setMensaje(
				"No se Encontro CodPreguntaOpinion ni Pregunta  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			PreguntaOpinion preguntaOpinion = gson.fromJson(json, PreguntaOpinion.class);
			preguntaOpinion.setAudFechaActualizacion(new Date());
			PreguntaOpinionService service = new PreguntaOpinionService();
			boolean r = false;
			try {
				if (preguntaOpinion.getCodPreguntaOpinion() != null
						&& preguntaOpinion.getCodPreguntaOpinion().longValue() > 0) {
					preguntaOpinion.setAudFechaActualizacion(new Date());
					r = service.updatePreguntaOpinion(preguntaOpinion);
					res.setError((r) ? false : true);
					res.setMensaje((r) ? "Actualizacion Exitosa" : "Fallo la Actualizacion");
				} else if (preguntaOpinion.getPregunta() != null && !preguntaOpinion.getPregunta().equals("null")
						&& !preguntaOpinion.getPregunta().isEmpty()) {
					r = service.insertPreguntaOpinion(preguntaOpinion);
					res.setError((r) ? false : true);
					res.setMensaje((r) ? "Insercion Exitosa" : "Fallo la Insercion");
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensajeTecnico(e.getMessage());
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensajeTecnico(e.getMessage());
			res.setMensaje("Error Al comunicarse a la Base de Datos");
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
	@Path("/getpreguntaopinion")
	@Consumes("text/plain")
	public Response getpreguntaopinion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
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
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getPreguntaOpinionTodos(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.PREGUNTA_OPINION, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getpreguntaopinionid")
	@Consumes("text/plain")
	public Response getpreguntaopinionid(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			PreguntaOpinion d = service.getPreguntaOpinionById(user.getCodPreguntaOpinion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getcargoexprofepersona")
	@Consumes("text/plain")
	public Response getcargoexprofepersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.getCargoExprofePersona(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
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
	@Path("/getevaluaciondesempenopersona")
	@Consumes("text/plain")
	public Response evaluacionDesempenoCodPersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			EvaluacionDesempeno user = gson.fromJson(json, EvaluacionDesempeno.class);
			EvaluacionDesempenoService service = new EvaluacionDesempenoService();
			try {
				List<EvaluacionDesempeno> d = service.getEvaluacionDesempenoPersona(user.getCodPersona());
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				EvaluacionDesempeno us = new EvaluacionDesempeno();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			EvaluacionDesempeno us = new EvaluacionDesempeno();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Mensaje Json");
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
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
	@Path("/gethojavidafiltro")
	@Consumes("text/plain")
	public Response getHojaVidafiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		HojaVida us = new HojaVida();
		try {
			HojaVida user = gson.fromJson(json, HojaVida.class);
			HojaVidaService service = new HojaVidaService();
			try {
				List<HojaVidaExt> d = service.getHojaVidafiltro(user);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				us = new HojaVida();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			us = new HojaVida();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Mensaje Json");
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
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
	@Path("/getevaluaciondesempenobypersona")
	@Consumes("text/plain")
	public Response getevaluaciondesempenobypersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			EvaluacionDesempeno user = gson.fromJson(json, EvaluacionDesempeno.class);
			EvaluacionDesempenoService service = new EvaluacionDesempenoService();
			try {
				List<EvaluacionDesempeno> d = service.getEvaluacionDesempenoPersona(user.getCodPersona());
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				EvaluacionDesempeno us = new EvaluacionDesempeno();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			EvaluacionDesempeno us = new EvaluacionDesempeno();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Mensaje Json");
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
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
	@Path("/sethojavida")
	@Consumes("text/plain")
	public Response sethojavida(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		HojaVida res = new HojaVida();
		res.setError(true);
		res.setMensaje(
				"No se Encontro CodHojaVida ni CodPersona  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			HojaVida hojaVida = gson.fromJson(json, HojaVida.class);
			hojaVida.setAudFechaActualizacion(new Date());
			HojaVidaService service = new HojaVidaService();
			try {
				if(auditJson != null) {
					auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
					if(auditoriaSeguridad.getCodUsuario() != null) {
						hojaVida.setAudCodUsuario(auditoriaSeguridad.getCodUsuario());
					}
					if(auditoriaSeguridad.getCodUsuarioRol() != null) {
						hojaVida.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
					}
				}
				if (hojaVida.getCodHojaVida() != null && hojaVida.getCodHojaVida().longValue() > 0) {
					hojaVida.setAudFechaActualizacion(new Date());
					res = service.updateHojaVida(hojaVida);
				} else if (hojaVida.getCodPersona() != null && hojaVida.getCodPersona().longValue() > 0) {
					res = service.insertHojaVida(hojaVida);
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensajeTecnico(e.getMessage());
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensajeTecnico(e.getMessage());
			res.setMensaje("Error Al comunicarse a la Base de Datos");
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
	@Path("/updatemasivohojavida")
	@Consumes("text/plain")
	public Response updatemasivohojavida(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			ExperienciaProfesional user = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			try {
				ExperienciaProfesional d = service.updatemasivo(user);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				ExperienciaProfesional us = new ExperienciaProfesional();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			EvaluacionDesempeno us = new EvaluacionDesempeno();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Mensaje Json");
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
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
	@Path("/getpersonacargosfecha")
	@Consumes("text/plain")
	public Response getPersonaCargosFecha(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			try {
				List<PersonaExt> d = service.getPersonaCargosFecha(user);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				EvaluacionDesempeno us = new EvaluacionDesempeno();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			EvaluacionDesempeno us = new EvaluacionDesempeno();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Mensaje Json");
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
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
	@Path("/getpersonacargosposesion")
	@Consumes("text/plain")
	public Response getPersonaCargosPosesion(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			Persona user = gson.fromJson(json, Persona.class);
			PersonaService service = new PersonaService();
			try {
				List<VinculacionExt> d = service.getPersonaCargosPosesion(user);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				EvaluacionDesempeno us = new EvaluacionDesempeno();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			EvaluacionDesempeno us = new EvaluacionDesempeno();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Mensaje Json");
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
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
	@Path("/getpersonacontrolinterno")
	@Consumes("text/plain")
	public Response getPersonaControlInterno(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
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
			try {
				List<PersonaExt> d = service.getPersonaControlInterno(user);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (NullPointerException e) {
				EvaluacionDesempeno us = new EvaluacionDesempeno();
				us.setError(true);
				us.setMensaje("Error Al comunicarse a la Base de Datos");
				us.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			EvaluacionDesempeno us = new EvaluacionDesempeno();
			us.setError(true);
			us.setMensaje("Error Al Serializar el Mensaje Json");
			us.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(us)).build();
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
	@Path("/geteducacionformalid")
	@Consumes("text/plain")
	public Response getEducacionFormalId(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EducacionFormal user = gson.fromJson(json, EducacionFormal.class);
			EducacionFormalService service = new EducacionFormalService();
			EducacionFormalExt d = service.getEducacionFormalById(user);
			String out = gson.toJson(d);
			out = out.replace("flgFinalizado\":0", "flgFinalizado\":false");
			out = out.replace("flgFinalizado\":1", "flgFinalizado\":true");
			out = out.replace("flgEducacionExterior\":1", "flgEducacionExterior\":true");
			out = out.replace("flgEducacionExterior\":0", "flgEducacionExterior\":false");
			out = out.replace("estudioConvalidado\":1", "estudioConvalidado\":true");
			out = out.replace("estudioConvalidado\":0", "estudioConvalidado\":false");
			out = out.replace("flgValidadoRrhh\":1", "flgValidadoRrhh\":true");
			out = out.replace("flgValidadoRrhh\":0", "flgValidadoRrhh\":false");
			out = out.replace("flgActivo\":1", "flgActivo\":true");
			out = out.replace("flgActivo\":0", "flgActivo\":false");
			out = out.replace("flgValidadoTarjetaProfesional\":1", "flgValidadoTarjetaProfesional\":true");
			out = out.replace("flgValidadoTarjetaProfesional\":0", "flgValidadoTarjetaProfesional\":false");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			EducacionFormalExt us = new EducacionFormalExt();
			List<EducacionFormalExt> nl = new ArrayList<EducacionFormalExt>();
			us.setError(true);
			us.setMensaje("Error Al Serialiar el Json");
			us.setMensajeTecnico(e.getMessage());
			nl.add(us);
			return Response.status(201).entity(gson.toJson(nl)).build();
		}
	}

	/**
	 * @author jesu_
	 * @param request
	 * @param json
	 * @param token
	 * @param timeout
	 * @return {@link ExperienciaProfesionalExt}
	 */
	@POST
	@Path("/getempleoactual")
	@Consumes("text/plain")
	public Response obtenerTrabajoActual(@Context HttpServletRequest request, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		json = json.replace("flgActivo\":true", "flgActivo\":1");
		json = json.replace("flgActivo\":false", "flgActivo\":0");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesionalExt exp = gson.fromJson(json, ExperienciaProfesionalExt.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.obtenerEmpleoActual(exp);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (Exception e) {
			ExperienciaProfesionalExt expProfesional = new ExperienciaProfesionalExt();
			expProfesional.setError(true);
			expProfesional.setMensaje("Error ");
			expProfesional.setMensajeTecnico(e.getMessage());
			List<ExperienciaProfesionalExt> d = new ArrayList<>();
			d.add(expProfesional);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestHV.class);
			return Response.status(201).entity(gson.toJson(expProfesional)).build();
		}
	}
	
	/**
	 * 
	 * @author jesu_
	 * @param request
	 * @param json
	 * @param token
	 * @param timeout
	 * @return {@link ExperienciaProfesionalExt}
	 */
	@POST
	@Path("/obtenerempleosanteriores")
	@Consumes("text/plain")
	public Response obtenerEmpleosAnteriores(@Context HttpServletRequest request, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		json = json.replace("flgActivo\":true", "flgActivo\":1");
		json = json.replace("flgActivo\":false", "flgActivo\":0");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesional exp = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.obtenerEmpleosAnteriores(exp);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (Exception e) {
			ExperienciaProfesionalExt expProfesional = new ExperienciaProfesionalExt();
			expProfesional.setError(true);
			expProfesional.setMensaje("Error ");
			expProfesional.setMensajeTecnico(e.getMessage());
			List<ExperienciaProfesionalExt> d = new ArrayList<>();
			d.add(expProfesional);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestHV.class);
			return Response.status(201).entity(gson.toJson(expProfesional)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo que retorna la cantidad de modificaciones que se realizaron en la hoja de vida
	 * para que desde gestionar hv se apruebe nuevamente.
	 */
	@POST
	@Path ( "/gettotalaprobado" ) 
	@Consumes("text/plain")
	public Response getTotalAprobado(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
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
			HojaVidaExt list = service.getTotalAprobados(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			HojaVidaExt user = new HojaVidaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestHV.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 * @fecha Dec 27, 2018
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getevaluacionFiltro" ) 
	@Consumes("text/plain")
	public Response getevaluacionFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EvaluacionDesempeno objJson = gson.fromJson(json, EvaluacionDesempeno.class);
			EvaluacionDesempenoService service = new EvaluacionDesempenoService();
			List<EvaluacionDesempenoExt> list = service.getevaluacionFiltro(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EvaluacionDesempenoExt user = new EvaluacionDesempenoExt();
			List<EvaluacionDesempenoExt> list = new ArrayList<>();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestHV.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @fecha Dec 27, 2018
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getLogroRecursoFiltro" ) 
	@Consumes("text/plain")
	public Response getLogroRecursoFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			LogroRecurso objJson = gson.fromJson(json, LogroRecurso.class);
			LogroRecursoService service = new LogroRecursoService();
			List<LogroRecurso> list = service.getLogroRecursoFiltro(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			LogroRecurso user = new LogroRecurso();
			List<LogroRecurso> list = new ArrayList<>();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestHV.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @param auditJson
	 * @return
	 */
	@POST
	@Path ( "/getLogroRecursoFiltroGerencia" ) 
	@Consumes("text/plain")
	public Response getLogroRecursoFiltroGerencia(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			LogroRecurso objJson = gson.fromJson(json, LogroRecurso.class);
			LogroRecursoService service = new LogroRecursoService();
			List<LogroRecurso> list = service.getLogroRecursoFiltroGerencia(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			LogroRecurso user = new LogroRecurso();
			List<LogroRecurso> list = new ArrayList<>();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestHV.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya
	 * @fechaDec 27, 2018
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getPublicacionFiltro" ) 
	@Consumes("text/plain")
	public Response getPublicacionFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Publicacion objJson = gson.fromJson(json, Publicacion.class);
			PublicacionService service = new PublicacionService();
			List<PublicacionExt> list = service.getPublicacionFiltro(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			PublicacionExt user = new PublicacionExt();
			List<PublicacionExt> list = new ArrayList<>();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestHV.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 28 dic. 2018
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getParticipacionInstitucionByFiltro")
	@Consumes("text/plain")
	public Response getParticipacionInstitucionByFiltro(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();

		try {
			ParticipacionInstitucion user = gson.fromJson(json, ParticipacionInstitucion.class);
			ParticipacionInstitucionService service = new ParticipacionInstitucionService();
			List<ParticipacionInstitucion> asoc = new ArrayList<>();
			asoc = service.getParticipacionInstitucionByFiltro(user);
			String out = gson.toJson(asoc);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 28 dic. 2018
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getParticipacionProyectoByFiltro")
	@Consumes("text/plain")
	public Response getParticipacionProyectoByFiltro(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		
		try {
			ParticipacionProyecto user = gson.fromJson(json, ParticipacionProyecto.class);
			ParticipacionProyectoService service = new ParticipacionProyectoService();
			List<ParticipacionProyectoExt> asoc = new ArrayList<>();
			asoc = service.getParticipacionProyectoByFiltro(user);
			String out = gson.toJson(asoc);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 28 dic. 2018
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getReconocimientoByFiltro")
	@Consumes("text/plain")
	public Response getReconocimientoByFiltro(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Reconocimiento user = gson.fromJson(json, Reconocimiento.class);
			ReconocimientoService service = new ReconocimientoService();
			List<ReconocimientoExt> asoc = new ArrayList<>();
			asoc = service.getReconocimientoByFiltro(user);
			String out = gson.toJson(asoc);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * 
	 * Elaborado por:
	 * Maria Alejandra Colorado
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/gethvpersonaconUTL")
	@Consumes("text/plain")
	public Response getHVPersonaConUTL(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			HojaVidaExt user = gson.fromJson(json, HojaVidaExt.class);
			HojaVidaService service = new HojaVidaService();
			List<HojaVidaExt> asoc = new ArrayList<>();
			asoc = service.getHVPersonaConUTL(user);
			String out = gson.toJson(asoc);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 * @fecha 14 feb. 2019
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getExperienciaByFiltro")
	@Consumes("text/plain")
	public Response getExperienciaByFiltro(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesional experienciaProfesional = gson.fromJson(json, ExperienciaProfesional.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.getExperienciaProfesionalFiltro(experienciaProfesional);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * @author Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
		@POST
		@Path("/getEntidadPrivadaExpProfesional")
		@Consumes("text/plain")
		public Response getEntidadPrivadas(@Context HttpServletRequest req, String json,
				@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				ExperienciaProfesionalExt user = gson.fromJson(json, ExperienciaProfesionalExt.class);
				ExperienciaProfesionalService service = new ExperienciaProfesionalService();
				List<ExperienciaProfesionalExt> d = service.getEntidadesPrivadas(user);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (JsonParseException e) {
				return Response.status(201).entity(e.getMessage()).build();
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
		 * @Fecha :Mar 12, 2019
		 */
		@POST
		@Path ( "/setDocumentosAdicionalesHv" ) 
		@Consumes("text/plain")
		public Response setDocumentosAdicionalesHv(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	        }
	    	json = new String(Base64.decode(json.getBytes()));
	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	        gson = gsonBuilder.create();
	        DocumentosAdicionalesHvService service = new DocumentosAdicionalesHvService();
	        DocumentosAdicionalesHv res = new DocumentosAdicionalesHv();
	        try {
	        	DocumentosAdicionalesHv documentosAdicionalesHv = gson.fromJson(json, DocumentosAdicionalesHv.class);
	            try {
	            	if(auditJson != null) {
						auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
						if(auditoriaSeguridad.getCodUsuario() != null) {
							documentosAdicionalesHv.setAudCodUsuario(auditoriaSeguridad.getCodUsuario().longValue());
						}
						if(auditoriaSeguridad.getCodUsuarioRol() != null) {
							documentosAdicionalesHv.setAudCodRol(auditoriaSeguridad.getCodUsuarioRol().intValue());
						}
					}
	                if (documentosAdicionalesHv.getCodDocumentoAdicional() != null) {
	                    res = service.updateDocumentosAdicionalesHv(documentosAdicionalesHv);
	                } else {
	                    res = service.insertDocumentosAdicionalesHv(documentosAdicionalesHv);
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
		 * @param req
		 * @param json
		 * @param token
		 * @param timeout
		 * @return
		 * @Fecha :Mar 12, 2019
		 */
		@POST
		@Path("/getDocumentosAdicionalesHvFiltro")
		@Consumes("text/plain")
		public Response getDocumentosAdicionalesHvFiltro(@Context HttpServletRequest req, String json,
				@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				DocumentosAdicionalesHv documentosAdicionalesHv = gson.fromJson(json, DocumentosAdicionalesHv.class);
				DocumentosAdicionalesHvService service = new DocumentosAdicionalesHvService();
				List<DocumentosAdicionalesHvExt> d = service.getDocumentosAdicionalesHvFiltro(documentosAdicionalesHv);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (JsonParseException e) {
				return Response.status(201).entity(e.getMessage()).build();
			}
		}
		/**
		 * @author: Jose Viscaya
		 * @param req
		 * @param json
		 * @param token
		 * @param timeout
		 * @return
		 * @Fecha :Mar 12, 2019
		 */
		@POST
		@Path("/getDocumentosAdicionalesHvId")
		@Consumes("text/plain")
		public Response getDocumentosAdicionalesHvId(@Context HttpServletRequest req, String json,
				@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				DocumentosAdicionalesHv documentosAdicionalesHv = gson.fromJson(json, DocumentosAdicionalesHv.class);
				DocumentosAdicionalesHvService service = new DocumentosAdicionalesHvService();
				DocumentosAdicionalesHvExt d = service.getDocumentosAdicionalesHvById(documentosAdicionalesHv.getCodTipoDocumentoAdicional().longValue());
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (JsonParseException e) {
				return Response.status(201).entity(e.getMessage()).build();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @Date:   Mar 29, 2019, 10:34:14 AM
		 * @File:   ServicesSigepRestHV.java
		 * @param req
		 * @param json
		 * @param token
		 * @param timeout
		 * @return
		 */
		@POST
		@Path("/delDocumentosAdicionalesHvId")
		@Consumes("text/plain")
		public Response delDocumentosAdicionalesHvId(@Context HttpServletRequest req, String json,
				@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				DocumentosAdicionalesHv documentosAdicionalesHv = gson.fromJson(json, DocumentosAdicionalesHv.class);
				DocumentosAdicionalesHvService service = new DocumentosAdicionalesHvService();
				DocumentosAdicionalesHvExt d = service.delDocumentosAdicionalesHvById(documentosAdicionalesHv.getCodDocumentoAdicional().longValue());
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (JsonParseException e) {
				return Response.status(201).entity(e.getMessage()).build();
			}
		}
		
		
		/**
		 * @author Maria Alejandra C
		 * @param req
		 * @param json
		 * @param token
		 * @param timeout
		 * @return
		 */
			@POST
			@Path("/getdependenciasfilterlike")
			@Consumes("text/plain")
			public Response getDependenciasFilterLike(@Context HttpServletRequest req, String json,
					@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
				if (!UtilsConstantes.tokenIsValid(token, timeout)) {
					return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
				}
				json = new String(Base64.decode(json.getBytes()));
				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
				gson = gsonBuilder.create();
				try {
					ExperienciaProfesionalExt user = gson.fromJson(json, ExperienciaProfesionalExt.class);
					ExperienciaProfesionalService service = new ExperienciaProfesionalService();
					List<ExperienciaProfesionalExt> d = service.getDependenciasFilterLike(user);
					String out = gson.toJson(d);
					return Response.status(201).entity(out).build();
				} catch (JsonParseException e) {
					return Response.status(201).entity(e.getMessage()).build();
				}
			}
			/**
			 * @author Maria Alejandra C
			 * @param req
			 * @param json
			 * @param token
			 * @param timeout
			 * @return
			 */
			@POST
			@Path("/getcargosfilterlikentidadpublica")
			@Consumes("text/plain")
			public Response getCargosFilterLikeEntidadPublica(@Context HttpServletRequest req, String json,
					@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
				if (!UtilsConstantes.tokenIsValid(token, timeout)) {
					return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
				}
				json = new String(Base64.decode(json.getBytes()));
				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
				gson = gsonBuilder.create();
				try {
					ExperienciaProfesionalExt user = gson.fromJson(json, ExperienciaProfesionalExt.class);
					ExperienciaProfesionalService service = new ExperienciaProfesionalService();
					List<ExperienciaProfesionalExt> d = service.getCargosFilterLikeEntidadPublica(user);
					String out = gson.toJson(d);
					return Response.status(201).entity(out).build();
				} catch (JsonParseException e) {
					return Response.status(201).entity(e.getMessage()).build();
				}
			}
				
	/**
	 * @author Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getcargosfilterlikentidadprivada")
	@Consumes("text/plain")
	public Response getCargosFilterLikeEntidadPrivada(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesionalExt user = gson.fromJson(json, ExperienciaProfesionalExt.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.getCargosFilterLikeEntidadPrivada(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	 /**
     * @author: Maria Alejandra 
     * @fecha 26 Agosto 2020
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una getHistoricoModificacionHojaVidaByFiltro
     */
    @POST
    @Path("/getHistoricoModificacionHojaVidaByFiltro")
    @Consumes("text/plain")
    public Response getHistoricoModificacionHojaVidaByFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<HistoricoModificacionHojaVida> err =  new ArrayList<>();
        try {
        	HistoricoModificacionHojaVida historicoModificacionHojaVida = gson.fromJson(json, HistoricoModificacionHojaVida.class);
        	HistoricoModificacionHojaVidaService service = new HistoricoModificacionHojaVidaService();
          	err = service.getHistoricoModificacionHojaVidaByFiltro(historicoModificacionHojaVida);
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
        	    err = new ArrayList<>();
        	    HistoricoModificacionHojaVida er = new HistoricoModificacionHojaVida();
        	    er.setError(true);
        	    er.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    er.setMensaje(e.getMessage());
        	    err.add(er);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
	 * @author Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return List<ExperienciaProfesionalExt>
	 * Servicio que retorna lista de experiencias profesionales y docentes de acuerdo al filtro enviado.
	 * La lista devuelve la siguiente información: cod_jornada_laboral, cod_tipo_entidad, fecha_ingreso, fecha_retiro y flg_contratista. 
	 */
	@POST
	@Path("/getExperienciaProfesionalYDocente")
	@Consumes("text/plain")
	public Response getExperienciaProfesionalYDocente(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ExperienciaProfesionalExt user = gson.fromJson(json, ExperienciaProfesionalExt.class);
			ExperienciaProfesionalService service = new ExperienciaProfesionalService();
			List<ExperienciaProfesionalExt> d = service.getExperienciaProfesionalYDocente(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * 
	 * Elaborado por:
	 * Maria Alejandra Colorado
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getHVPersona")
	@Consumes("text/plain")
	public Response getHVPersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VinculacionExt user = gson.fromJson(json, VinculacionExt.class);
			HojaVidaService service = new HojaVidaService();
			List<HojaVidaExt> asoc = new ArrayList<>();
			asoc = service.getHVPersona(user);
			String out = gson.toJson(asoc);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	

	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para retornar los datos de la Nacionalidad de una persona
	 */
	@POST
	@Path ( "/getNacionalidadPerfilUnico" ) 
	@Consumes("text/plain")
	public Response getNacionalidadPerfilUnico(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			NacionalidadPerfil user = gson.fromJson(json, NacionalidadPerfil.class);
			NacionalidadPerfilService service = new NacionalidadPerfilService();
			NacionalidadPerfil d = service.getNacionalidadPerfilUnico(user);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			NacionalidadPerfil user = new NacionalidadPerfil();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), NacionalidadPerfilService.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}

	/**
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para retornar los datos de Hoja de Vida
	 */
	@POST
	@Path ( "/getHojaVidaPersona" ) 
	@Consumes("text/plain")
	public Response getHojaVidaPersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			HojaVidaService service = new HojaVidaService();
			PersonaExt d = service.getHojaVidaPersona(user);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			PersonaExt user = new PersonaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), NacionalidadPerfilService.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}

}