/**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.File;
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
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.sun.jersey.core.util.Base64;

import co.gov.dafp.sigep2.bean.AuditoriaConfiguracion;
import co.gov.dafp.sigep2.bean.Cargo;
import co.gov.dafp.sigep2.bean.CargoHojaVida;
import co.gov.dafp.sigep2.bean.CmActivarUsuarios;
import co.gov.dafp.sigep2.bean.CmConfiguracion;
import co.gov.dafp.sigep2.bean.CmContratos;
import co.gov.dafp.sigep2.bean.CmCrearEntidad;
import co.gov.dafp.sigep2.bean.CmCrearEstructura;
import co.gov.dafp.sigep2.bean.CmCrearGrupo;
import co.gov.dafp.sigep2.bean.CmCrearNomenclaturaSalarial;
import co.gov.dafp.sigep2.bean.CmCrearPlanta;
import co.gov.dafp.sigep2.bean.CmCrearUsuarios;
import co.gov.dafp.sigep2.bean.CmHvEducacionDesaHumano;
import co.gov.dafp.sigep2.bean.CmHvEducacionFormal;
import co.gov.dafp.sigep2.bean.CmHvEducacionIdiomas;
import co.gov.dafp.sigep2.bean.CmHvEducacionOtros;
import co.gov.dafp.sigep2.bean.CmHvEvaluacionDesempeno;
import co.gov.dafp.sigep2.bean.CmHvExperienciaLaboral;
import co.gov.dafp.sigep2.bean.CmHvExperienciaLaboralDocen;
import co.gov.dafp.sigep2.bean.CmHvInformacionBasica;
import co.gov.dafp.sigep2.bean.CmRoles;
import co.gov.dafp.sigep2.bean.CmSituacionesAdministrativas;
import co.gov.dafp.sigep2.bean.CmVinculaciones;
import co.gov.dafp.sigep2.bean.Denominacion;
import co.gov.dafp.sigep2.bean.Departamento;
import co.gov.dafp.sigep2.bean.DependenciaEntidad;
import co.gov.dafp.sigep2.bean.DependenciaHojaVida;
import co.gov.dafp.sigep2.bean.Entidad;
import co.gov.dafp.sigep2.bean.EntidadPlanta;
import co.gov.dafp.sigep2.bean.Festivo;
import co.gov.dafp.sigep2.bean.Idioma;
import co.gov.dafp.sigep2.bean.InstitucionEducativa;
import co.gov.dafp.sigep2.bean.Municipio;
import co.gov.dafp.sigep2.bean.Pais;
import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.PermisoRol;
import co.gov.dafp.sigep2.bean.PermisoRolAccion;
import co.gov.dafp.sigep2.bean.PreguntaOpinion;
import co.gov.dafp.sigep2.bean.ProcesosCargaMasiva;
import co.gov.dafp.sigep2.bean.ProgramaAcademico;
import co.gov.dafp.sigep2.bean.RangoEdad;
import co.gov.dafp.sigep2.bean.Recurso;
import co.gov.dafp.sigep2.bean.RecursoAccion;
import co.gov.dafp.sigep2.bean.ResultadoPregunta;
import co.gov.dafp.sigep2.bean.Rol;
import co.gov.dafp.sigep2.bean.SequenciasSigep;
import co.gov.dafp.sigep2.bean.SituacionAdministrativa;
import co.gov.dafp.sigep2.bean.Token;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.UsuarioRolEntidad;
import co.gov.dafp.sigep2.bean.UsuarioSession;
import co.gov.dafp.sigep2.bean.VAccionAuditoria;
import co.gov.dafp.sigep2.bean.VRecursoActivoPerfilUsuario;
import co.gov.dafp.sigep2.bean.ext.AuditoriaExt;
import co.gov.dafp.sigep2.bean.ext.CargoExt;
import co.gov.dafp.sigep2.bean.ext.CargoHojaVidaExt;
import co.gov.dafp.sigep2.bean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.bean.ext.DependenciaHojaVidaExt;
import co.gov.dafp.sigep2.bean.ext.EntidadExt;
import co.gov.dafp.sigep2.bean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.bean.ext.InstitucionEducativaExt;
import co.gov.dafp.sigep2.bean.ext.MunicipioExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.ProcesosCargaMasivaExt;
import co.gov.dafp.sigep2.bean.ext.ProgramaAcademicoExt;
import co.gov.dafp.sigep2.bean.ext.RecursoAccionExt;
import co.gov.dafp.sigep2.bean.ext.RecursoExt;
import co.gov.dafp.sigep2.bean.ext.ResultadoPreguntaExt;
import co.gov.dafp.sigep2.bean.ext.RolExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.constante.AuditoriaConstantes;
import co.gov.dafp.sigep2.services.AuditoriaConfiguracionService;
import co.gov.dafp.sigep2.services.AuditoriaService;
import co.gov.dafp.sigep2.services.CargoHojaVidaService;
import co.gov.dafp.sigep2.services.CargoService;
import co.gov.dafp.sigep2.services.CmActivarUsuariosService;
import co.gov.dafp.sigep2.services.CmConfiguracionService;
import co.gov.dafp.sigep2.services.CmContratosService;
import co.gov.dafp.sigep2.services.CmCrearEntidadService;
import co.gov.dafp.sigep2.services.CmCrearEstructuraService;
import co.gov.dafp.sigep2.services.CmCrearGrupoService;
import co.gov.dafp.sigep2.services.CmCrearNomenclaturaSalarialService;
import co.gov.dafp.sigep2.services.CmCrearPlantaService;
import co.gov.dafp.sigep2.services.CmCrearUsuariosService;
import co.gov.dafp.sigep2.services.CmHvEducacionDesaHumanoService;
import co.gov.dafp.sigep2.services.CmHvEducacionFormalService;
import co.gov.dafp.sigep2.services.CmHvEducacionIdiomasService;
import co.gov.dafp.sigep2.services.CmHvEducacionOtrosService;
import co.gov.dafp.sigep2.services.CmHvEvaluacionDesempenoService;
import co.gov.dafp.sigep2.services.CmHvExperienciaLaboralDocenService;
import co.gov.dafp.sigep2.services.CmHvExperienciaLaboralService;
import co.gov.dafp.sigep2.services.CmHvInformacionBasicaService;
import co.gov.dafp.sigep2.services.CmRolesService;
import co.gov.dafp.sigep2.services.CmSituacionesAdministrativasService;
import co.gov.dafp.sigep2.services.CmVinculacionesService;
import co.gov.dafp.sigep2.services.DenominacionService;
import co.gov.dafp.sigep2.services.DepartamentoService;
import co.gov.dafp.sigep2.services.DependenciaEntidadService;
import co.gov.dafp.sigep2.services.DependenciaHojaVidaService;
import co.gov.dafp.sigep2.services.EntidadPlantaService;
import co.gov.dafp.sigep2.services.EntidadService;
import co.gov.dafp.sigep2.services.FestivoService;
import co.gov.dafp.sigep2.services.IdiomaService;
import co.gov.dafp.sigep2.services.InstitucionEducativaService;
import co.gov.dafp.sigep2.services.MunicipioService;
import co.gov.dafp.sigep2.services.PaisService;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.PreguntaOpinionService;
import co.gov.dafp.sigep2.services.ProcesosCargaMasivaService;
import co.gov.dafp.sigep2.services.ProgramaAcademicoServicio;
import co.gov.dafp.sigep2.services.RangoEdadService;
import co.gov.dafp.sigep2.services.RecursoAccionService;
import co.gov.dafp.sigep2.services.RecursoService;
import co.gov.dafp.sigep2.services.ResultadoPreguntaService;
import co.gov.dafp.sigep2.services.RolService;
import co.gov.dafp.sigep2.services.SequenciasSigepService;
import co.gov.dafp.sigep2.services.SituacionAdministrativaService;
import co.gov.dafp.sigep2.services.TokenService;
import co.gov.dafp.sigep2.services.UsuarioRolEntidadService;
import co.gov.dafp.sigep2.services.UsuarioService;
import co.gov.dafp.sigep2.services.UsuarioSessionService;
import co.gov.dafp.sigep2.services.VAccionAuditoriaService;
import co.gov.dafp.sigep2.services.VRecursoActivoPerfilUsuarioService;
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
@Path("/sigepsis")
public class ServicesSigepRestSis implements Serializable {

	private static final long serialVersionUID = 248002675592552668L;
	private Gson gson;

	public ServicesSigepRestSis() {
		LoggerSigep.getInstance().configureAppender();
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param msg
	 * @param token
	 * @param timeout
	 * @return
	 */
	@GET()
	@Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@Context HttpServletRequest req, @PathParam("msg") String msg,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		return Response.status(201).entity("Hello: Services Context sigepsis... " + msg).build();
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
	@Path("/recursoPorCodVentana")
	@Consumes("text/plain")
	public Response recursoPorCodVentana(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Recurso user = gson.fromJson(json, Recurso.class);
			RecursoService service = new RecursoService();
			Recurso d = service.getRecursoPorCodVentana(user.getCodigoVentana());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/recursolist")
	@Consumes("text/plain")
	public Response recursolist(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			RecursoExt user = gson.fromJson(json, RecursoExt.class);
			RecursoService service = new RecursoService();
			List<RecursoExt> d = service.getRecursoList(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("{}").build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/recursoActivoPerfilUsuario")
	@Consumes("text/plain")
	public Response recursoActivoPerfilUsuario(@Context HttpServletRequest req, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VRecursoActivoPerfilUsuarioService service = new VRecursoActivoPerfilUsuarioService();
			List<VRecursoActivoPerfilUsuario> d = service.getVRecursoActivoPerfilUsuario(0, 0);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/entidadesTotal")
	@Consumes("text/plain")
	public Response entidadesTotal(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {

		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Entidad user = gson.fromJson(json, Entidad.class);
			EntidadService service = new EntidadService();
			List<Entidad> d = service.getEntidadByAll(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/desactivarEntidad")
	@Consumes("text/plain")
	public Response desactivarEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
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
			boolean d = service.desactivarEntidad(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/listInstitucionEducativa")
	@Consumes("text/plain")
	public Response listInstitucionEducativa(@Context HttpServletRequest req, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			InstitucionEducativaService service = new InstitucionEducativaService();
			List<InstitucionEducativa> d = service.getInstitucionEducativaByAll();
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<InstitucionEducativa> asoc = new ArrayList<>();
			InstitucionEducativa d = new InstitucionEducativa();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			asoc.add(d);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(asoc)).build();
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
	@Path("/listInstitucionEducativaportipo")
	@Consumes("text/plain")
	public Response listInstitucionEducativatipo(@Context HttpServletRequest req, String json,
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
			InstitucionEducativa user = gson.fromJson(json, InstitucionEducativa.class);
			InstitucionEducativaService service = new InstitucionEducativaService();
			List<InstitucionEducativa> d = service.getInstitucionEducativaByTipo(user.getCodTipoInstitucion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<InstitucionEducativa> asoc = new ArrayList<>();
			InstitucionEducativa d = new InstitucionEducativa();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			asoc.add(d);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(asoc)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/idiomasList")
	@Consumes("text/plain")
	public Response idiomasList(@Context HttpServletRequest req, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			IdiomaService service = new IdiomaService();
			List<Idioma> d = service.getIdiomaByAll();
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<Idioma> asoc = new ArrayList<>();
			Idioma d = new Idioma();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			asoc.add(d);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(asoc)).build();
		}
	}

	// SERWEB000019
	@POST
	@Path("/parametricasporcodigo")
	@Consumes("text/plain")
	public Response getParametrica(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			try {
				List<Parametrica> d = service.getParametricaPorPadrel(user.getNombreParametro());
				return Response.status(201).entity(gson.toJson(d)).build();
			} catch (NullPointerException e) {
				List<Parametrica> d = new ArrayList<>();
				Parametrica p = new Parametrica();
				p.setError(true);
				p.setMensaje("nombreParametro no puede ser Nullo");
				p.setMensajeTecnico(e.getMessage());
				d.add(p);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
				return Response.status(201).entity(gson.toJson(d)).build();
			}

		} catch (JsonParseException e) {
			List<Parametrica> d = new ArrayList<>();
			Parametrica p = new Parametrica();
			p.setError(true);
			p.setMensaje("Formato Json No Valido");
			p.setMensajeTecnico(e.getMessage());
			d.add(p);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/parametricaintentos")
	@Consumes("text/plain")
	public Response parametricaintentos(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			try {
				List<Parametrica> d = service.getParametricaIntentos(user.getNombreParametro());
				if (d.size() > 0) {
					return Response.status(201).entity(gson.toJson(d.get(0))).build();
				} else {
					Parametrica p = new Parametrica();
					p.setError(true);
					p.setMensaje("No se encontro registro");
					return Response.status(201).entity(gson.toJson(p)).build();
				}
			} catch (NullPointerException e) {
				Parametrica p = new Parametrica();
				p.setError(true);
				p.setMensaje("nombreParametro no puede ser Nullo");
				p.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(p)).build();
			}

		} catch (JsonParseException e) {
			Parametrica p = new Parametrica();
			p.setError(true);
			p.setMensaje("Formato Json No valido");
			p.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(p)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getprogramaacademico")
	@Consumes("text/plain")
	public Response getProgramaAcademicoByAll(@Context HttpServletRequest req, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			ProgramaAcademicoServicio service = new ProgramaAcademicoServicio();
			List<ProgramaAcademico> d = service.getProgramaAcademicoByAll();
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getpises")
	@Consumes("text/plain")
	public Response getPaises(@Context HttpServletRequest req, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PaisService service = new PaisService();
			List<Pais> d = service.getPaisByAll();
			String out = gson.toJson(d);
			out = out.replace("src", "");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getdeptos")
	@Consumes("text/plain")
	public Response getDeptos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Departamento dep = gson.fromJson(json, Departamento.class);
			DepartamentoService service = new DepartamentoService();
			List<Departamento> d = service.getDepartamentoByIdPais(dep.getCodPais());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getmunicipios")
	@Consumes("text/plain")
	public Response getMunicipios(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Municipio mun = gson.fromJson(json, Municipio.class);
			MunicipioService service = new MunicipioService();
			List<Municipio> d = service.getMunicipioByIdDpto(mun.getCodDepartamento());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getentidadporid")
	@Consumes("text/plain")
	public Response getentidadporid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Entidad ent = gson.fromJson(json, Entidad.class);
			EntidadService service = new EntidadService();
			Entidad d = service.entidadById(ent.getCodEntidad());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getrolporid")
	@Consumes("text/plain")
	public Response getrolporid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Rol ro = gson.fromJson(json, Rol.class);
			RolService service = new RolService();
			Rol d = service.getRolById(ro.getCodRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/registrartoken")
	@Consumes("text/plain")
	public Response registrartoken(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Token tok = new Token();
		try {
			tok = gson.fromJson(json, Token.class);
			tok.setFechaGeneracion(new Date());
			tok.setAudFechaActualizacion(new Date());
			tok.setFlgActivo((short) 1);
			tok.setAudCodUsuario(tok.getCodUsuario().longValue());
			tok.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue()));
			tok.setOrigen((short) 1);
			try {
				if (tok.getCodEntidad() == 0) {
					tok.setCodEntidad(null);
				}
			} catch (NullPointerException e) {
				tok.setCodEntidad(null);
			}
			TokenService service = new TokenService();
			tok.setToken(service.getToken());
			tok = service.insertToken(tok);
			String out = gson.toJson(tok);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			tok.setError(true);
			tok.setMensaje(UtilsConstantes.MSG_EXEPCION);
			tok.setMensajeTecnico(e.getMessage());
			String out = gson.toJson(tok);
			return Response.status(201).entity(out).build();
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
	@Path("/getrolesistema")
	@Consumes("text/plain")
	public Response getrolesistema(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		json = new String(Base64.decode(json.getBytes()));
		try {
			List<Rol> d;
			RolExt rol = gson.fromJson(json, RolExt.class);
			try {
				if (rol.getNombre().equalsIgnoreCase("NULL") || rol.getNombre().length() < 1) {
					rol.setNombre("");
				} else {
					rol.setNombre(rol.getNombre().toUpperCase());
				}
				
				if (rol !=null && (rol.getFieldName()!= "" || rol.getFieldName().equalsIgnoreCase("NULL") || rol.getFieldName().length() < 1)) {
					rol.setFieldName(null);
				} 
				
			} catch (NullPointerException e) {
				rol.setNombre("");
			}
			RolService service = new RolService();
			if (rol.getNombre().length() > 0) {
				d = service.getRolByLike(rol);
			} else {
				d = service.getRolByAll(rol);
			}
			String out = gson.toJson(d);
			out = out.replace("\"flgActivo\":1,", "\"flgActivo\":true,");
			out = out.replace("\"flgActivo\":0,", "\"flgActivo\":false,");
			out = out.replace("\"flgEstado\":1,", "\"flgEstado\":true,");
			out = out.replace("\"flgEstado\":0,", "\"flgEstado\":false,");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/expirartoken")
	@Consumes("text/plain")
	public Response expirartoken(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		TokenService service = new TokenService();
		try {
			Token tok = gson.fromJson(json, Token.class);
			tok = service.getTokenBytokenName(tok.getToken());
			tok.setFlgActivo((short) 0);
			tok.setAudFechaActualizacion(new Date());
			Token d = service.updateTokenuser(tok);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getdependenciaentidad")
	@Consumes("text/plain")
	public Response getdependenciaentidad(@Context HttpServletRequest req, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			DependenciaEntidadService service = new DependenciaEntidadService();
			List<DependenciaEntidad> d = service.getDependenciaEntidadByunion();
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/parametricasporid")
	@Consumes("text/plain")
	public Response getParametricaporid(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			Parametrica d = service.getPrametricaById(user.getCodTablaParametrica());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
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
	@Path("/institucionEducativaporid")
	@Consumes("text/plain")
	public Response listInstitucionEducativaporid(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		InstitucionEducativa user = gson.fromJson(json, InstitucionEducativa.class);
		try {
			InstitucionEducativaService service = new InstitucionEducativaService();
			InstitucionEducativa d = service.institucionEducativaById(user.getCodInstitucionEducativa());
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
	@Path("/parametricasporpadreid")
	@Consumes("text/plain")
	public Response getParametricaporpadre(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			List<Parametrica> d = service.getPrametricaByPadreiId(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.PARAMETRICA,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
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
	@Path("/parametricasporpadreidonlynumero")
	@Consumes("text/plain")
	public Response parametricasporpadreidonlynumero(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			List<Parametrica> d = service.getParametricaByPadreiIdOnlyNumero(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.PARAMETRICA,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
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
	@Path("/getrolporpadreid")
	@Consumes("text/plain")
	public Response getrolporpadreid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Rol ro = gson.fromJson(json, Rol.class);
			RolService service = new RolService();
			List<Rol> d = service.getRolByPadre(ro.getCodRolPadre());
			String out = gson.toJson(d).replace("\"flgEstado\":1", "\"flgEstado\":true")
					.replace("\"flgEstado\":0", "\"flgEstado\":false").replace("\"flgActivo\":1", "\"flgActivo\":true")
					.replace("\"flgActivo\":0", "\"flgActivo\":false");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/setanduprol")
	@Consumes("text/plain")
	public Response setanduprol(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		json = json.replace("true", "1");
		json = json.replace("false", "0");
		json = json.replace("id", "codRol");
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Rol ro = gson.fromJson(json, Rol.class);
			ro.setAudFechaActualizacion(new Date());
			RolService service = new RolService();
			try {
				if (ro.getCodRol().longValue() > 0) {
					boolean d = service.updateRol(ro);
					return Response.status(201).entity(d).build();
				} else {
					if (ro.getNombre().length() > 0 && ro.getAudCodUsuario().longValue() > 0) {
						boolean d = service.insertRol(ro);
						return Response.status(201).entity(d).build();
					} else {
						return Response.status(201).entity(false).build();
					}

				}
			} catch (NullPointerException e) {
				try {
					if (ro.getNombre().length() > 0 && ro.getAudCodUsuario().longValue() > 0) {
						boolean d = service.insertRol(ro);
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
	@Path("/getpisporid")
	@Consumes("text/plain")
	public Response getPaisesporid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Pais ro = gson.fromJson(json, Pais.class);
		PaisService service = new PaisService();
		try {
			Pais d = service.getPaisbyId(ro.getCodPais());
			String out = gson.toJson(d);
			out = out.replace("src", "");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
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
	@Path("/getdeptoporid")
	@Consumes("text/plain")
	public Response getdeptoporid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Departamento dep = gson.fromJson(json, Departamento.class);
			DepartamentoService service = new DepartamentoService();
			Departamento d = service.getDepartamentoById(dep.getCodDepartamento());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getcargoporcodtipoentidad")
	@Consumes("text/plain")
	public Response getCargoBycodEntidad(@Context HttpServletRequest req, String json,
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
			Entidad dep = gson.fromJson(json, Entidad.class);
			CargoService service = new CargoService();
			List<CargoExt> d = service.getCargoBycodEntidad(dep.getCodTipoEntidad());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/geentidadesporcodtipoentidad")
	@Consumes("text/plain")
	public Response geentidadesporcodtipoentidad(@Context HttpServletRequest req, String json,
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
			Entidad user = gson.fromJson(json, Entidad.class);
			EntidadService service = new EntidadService();
			List<Entidad> d = service.getEntidadByAll(user);
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
	@Path("/getcargostodos")
	@Consumes("text/plain")
	public Response getcargostodos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Cargo user = gson.fromJson(json, Cargo.class);
			CargoService service = new CargoService();
			List<CargoExt> d = service.getCargoTodos(user);
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
	@Path("/getprogramaacademicoporint")
	@Consumes("text/plain")
	public Response getprogramaacademicoporint(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		json = new String(Base64.decode(json.getBytes()));
		try {
			ProgramaAcademico pr = gson.fromJson(json, ProgramaAcademico.class);
			ProgramaAcademicoServicio service = new ProgramaAcademicoServicio();
			List<ProgramaAcademico> d = service.getProgramaAcademicoByIdInt(pr.getCodInstitucion());
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
	@Path("/getprogramaacademicoporid")
	@Consumes("text/plain")
	public Response getprogramaacademicoporid(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		json = new String(Base64.decode(json.getBytes()));
		try {
			ProgramaAcademico pr = gson.fromJson(json, ProgramaAcademico.class);
			ProgramaAcademicoServicio service = new ProgramaAcademicoServicio();
			ProgramaAcademico d = service.getProgramaAcademicoById(pr.getCodTituloAcademico());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("{}").build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @return
	 */
	@GET
	@Path("/getpdffile/{path}")
	@Produces("application/pdf")
	public Response getFile(@Context HttpServletRequest req, @PathParam("path") String json) {
		try {
			json = new String(Base64.decode(json.getBytes()));
			File file = new File(json);
			if (file.exists()) {
				ResponseBuilder response = Response.ok((Object) file);
				return response.build();
			} else {
				return Response.status(404).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(404).build();
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
	@Path("/parametricaspornombre")
	@Consumes("text/plain")
	public Response getParametricapornamelike(@Context HttpServletRequest req, String json,
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
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			List<Parametrica> d = service.getParametricatByNameLike(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
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
	@Path("/setparametrica")
	@Consumes("text/plain")
	public Response setDatoContactoPorIdPersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Parametrica us = new Parametrica();
		try {
			Parametrica user = gson.fromJson(json, Parametrica.class);
			user.setAudFechaActualizacion(new Date());
			ParametricaService service = new ParametricaService();
			if (user.getCodTablaParametrica() != null) {
				if (user.getCodTablaParametrica().longValue() > 0) {
					us = service.updateParametrica(user);
					return Response.status(201).entity(gson.toJson(us)).build();
				} else {
					us = new Parametrica();
					us.setError(true);
					us.setMensaje("Codigo De actualizaion Invalido " + user.getCodTablaParametrica());
					return Response.status(201).entity(gson.toJson(us)).build();
				}
			} else if (user.getNombreParametro() != null) {
				if (user.getNombreParametro().length() > 0) {
					us = service.insertParametrica(user);
					return Response.status(201).entity(gson.toJson(us)).build();
				} else {
					us = new Parametrica();
					us.setError(true);
					us.setMensaje("No se encontro nombre Parametro ");
					return Response.status(201).entity(gson.toJson(us)).build();
				}
			} else {
				us = new Parametrica();
				us.setError(true);
				us.setMensaje("Nombre Parametro no puede ser Nulo");
				return Response.status(201).entity(gson.toJson(us)).build();
			}
		} catch (JsonParseException e) {
			us = new Parametrica();
			us.setError(true);
			us.setMensaje("Error Al Serializar Json");
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
	@Path("/getrolesporpersona")
	@Consumes("text/plain")
	public Response getRolByPerosna(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaExt user = gson.fromJson(json, PersonaExt.class);
			RolService service = new RolService();
			List<RolExt> d = service.getRolByPerosna(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
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
	@Path("/setrol")
	@Consumes("text/plain")
	public Response setRol(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		Rol us = new Rol();
		try {
			Rol user = gson.fromJson(json, Rol.class);
			user.setFechaModificacion(new Date());
			if (user.getAudFechaActualizacion() == null) {
				user.setAudFechaActualizacion(new Date());
			}
			RolService service = new RolService();
			try {
				if (user.getCodRol().longValue() > 0) {
					boolean d = service.updateRol(user);
					us.setError((d) ? false : true);
					us.setMensaje((d) ? "Actualizacion Exitosa" : "Fallo la Actualizacion");
				} else {
					if (user.getDescripcionRol() != null) {
						if (user.getDescripcionRol().length() > 2 && !user.getDescripcionRol().equals("null")) {
							boolean d = service.insertRol(user);
							us.setError((d) ? false : true);
							us.setMensaje((d) ? "Insercion Exitosa" : "Fallo la Insercion");
						} else {
							us.setError(true);
							us.setMensaje("Falta Descripcion Rol");
						}
					} else {
						us.setError(true);
						us.setMensaje("Descripcion Rol");
					}
				}
				return Response.status(201).entity(gson.toJson(us)).build();
			} catch (NullPointerException e) {
				try {
					if (user.getDescripcionRol() != null) {
						if (user.getDescripcionRol().length() > 2 && !user.getDescripcionRol().equals("null")) {
							boolean d = service.insertRol(user);
							us.setError((d) ? false : true);
							us.setMensaje((d) ? "Actualizacion Exitosa" : "Fallo la Actualizacion");
							return Response.status(201).entity(gson.toJson(us)).build();
						} else {
							us.setError(true);
							us.setMensaje("Falta Descripcion Rol");
							return Response.status(201).entity(gson.toJson(us)).build();
						}
					} else {
						us.setError(true);
						us.setMensaje("Falta Descripcion Rol");
						return Response.status(201).entity(gson.toJson(us)).build();
					}
				} catch (Exception e2) {
					us.setError(true);
					us.setMensaje("Error Datos Enviados");
					return Response.status(201).entity(gson.toJson(us)).build();
				}
			}
		} catch (JsonParseException e) {
			us.setError(true);
			us.setMensaje("Error Al Serializar Json");
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
	@Path("/desactivarrolusuario")
	@Consumes("text/plain")
	public Response setUsuarioRolEntidad(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		UsuarioRolEntidad us = new UsuarioRolEntidad();
		try {
			UsuarioRolEntidad user = gson.fromJson(json, UsuarioRolEntidad.class);
			UsuarioRolEntidadService service = new UsuarioRolEntidadService();
			try {
				if (user.getCodUsuarioRolEntidad() != null) {
					if (user.getCodUsuarioRolEntidad().longValue() > 0) {
						user.setFlgActivo((short) 0);
						user.setFlgEstado(new BigDecimal(0));
						user.setAudFechaActualizacion(new Date());
						boolean d = service.desactivarRolUsuario(user);
						us.setError((d) ? false : true);
						us.setMensaje((d) ? "Actualizacion Exitosa" : "Fallo la Actualizacion");
					} else {
						us.setError(true);
						us.setMensaje("Falta CodUsuarioRolEntidad");
					}
				} else {
					us.setError(true);
					us.setMensaje("Falta CodUsuarioRolEntidad");
				}
			} catch (NullPointerException e) {
				us.setError(true);
				us.setMensaje("Falta CodUsuarioRolEntidad");
				us.setMensajeTecnico(e.getMessage());
			}
			return Response.status(201).entity(gson.toJson(us)).build();
		} catch (JsonParseException e) {
			us.setError(true);
			us.setMensaje("Error Al Serializar Json");
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
	@Path("/getauditoriaconfiguracion")
	@Consumes("text/plain")
	public Response getpersonaparentescopersona(@Context HttpServletRequest req, String json,
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
			List<AuditoriaConfiguracion> d = new ArrayList<AuditoriaConfiguracion>();
			AuditoriaConfiguracion user = gson.fromJson(json, AuditoriaConfiguracion.class);
			AuditoriaConfiguracionService service = new AuditoriaConfiguracionService();
			if (user.getNombreTabla() != null && !user.getNombreTabla().equals("null")
					&& !user.getNombreTabla().isEmpty()) {
				user.setNombreTabla(user.getNombreTabla().toUpperCase());
				d = service.getAuditoriaConfiguracionByName(user);
			} else {
				d = service.getAuditoriaConfiguracionByAll(user);
			}
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
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
	@Path("/setauditoriaconficuracion")
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
		AuditoriaConfiguracion res = new AuditoriaConfiguracion();
		res.setError(true);
		res.setMensaje(
				"No se Encontro CodTablaAuditoria ni NombreTabla  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			AuditoriaConfiguracion auditoriaConfiguracion = gson.fromJson(json, AuditoriaConfiguracion.class);
			auditoriaConfiguracion.setAudFechaActualizacion(new Date());
			AuditoriaConfiguracionService service = new AuditoriaConfiguracionService();
			try {
				if (auditoriaConfiguracion.getCodTablaAuditoria() != null
						&& auditoriaConfiguracion.getCodTablaAuditoria().longValue() > 0) {
					auditoriaConfiguracion.setAudFechaActualizacion(new Date());
					res = service.updateAuditoriaConfiguracion(auditoriaConfiguracion);
				} else if (auditoriaConfiguracion.getNombreTabla() != null
						&& !auditoriaConfiguracion.getNombreTabla().equals("null")) {
					res = service.insertAuditoriaConfiguracion(auditoriaConfiguracion);
				}
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensajeTecnico(e.getMessage());
				res.setMensaje(UtilsConstantes.MSG_EXEPCION);
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
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getauditoriaconfiguracionid")
	@Consumes("text/plain")
	public Response getauditoriaconfiguracionid(@Context HttpServletRequest req, String json,
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
			AuditoriaConfiguracion user = gson.fromJson(json, AuditoriaConfiguracion.class);
			AuditoriaConfiguracionService service = new AuditoriaConfiguracionService();
			AuditoriaConfiguracion d = service.getAuditoriaConfiguracionById(user.getCodTablaAuditoria());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
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
	@Path("/getparametricaspadretodo")
	@Consumes("text/plain")
	public Response getParametricaporpadrenull(@Context HttpServletRequest req, String json,
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
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			List<Parametrica> d = service.getPrametricaByPadre(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.PARAMETRICA,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
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
	@Path("/getrespuestaspregunta")
	@Consumes("text/plain")
	public Response getRespuestasPregunta(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			List<ResultadoPregunta> d = service.getRespuestasPregunta(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/gettotalrespuestaspregunta")
	@Consumes("text/plain")
	public Response getTotalRespuestasPregunta(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			ResultadoPregunta d = service.getTotalRespuestas(user.getCodPreguntaOpinion().longValue());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getrespuestaspuntajepregunta")
	@Consumes("text/plain")
	public Response getRespuestasPuntagePregunta(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			ResultadoPregunta d = service.getRespuestasPuntagePregunta(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/delparametrica")
	@Consumes("text/plain")
	public Response delparametrica(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			String d = service.deleteParametrica(user.getCodTablaParametrica());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/delpreguntaopinion")
	@Consumes("text/plain")
	public Response delpreguntaopinion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
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
			String d = service.deletePreguntaOpinion(user.getCodPreguntaOpinion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/setresultadopregunta")
	@Consumes("text/plain")
	public Response setresultadopregunta(@Context HttpServletRequest req, String json,
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
		ResultadoPregunta res = new ResultadoPregunta();
		res.setError(true);
		res.setMensaje(
				"No se Encontro CodTablaAuditoria ni NombreTabla  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			ResultadoPregunta resultadoPregunta = gson.fromJson(json, ResultadoPregunta.class);
			resultadoPregunta.setAudFechaActualizacion(new Date());
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			boolean r = false;
			try {
				if (resultadoPregunta.getCodResultadoPregunta() != null
						&& resultadoPregunta.getCodResultadoPregunta().longValue() > 0) {
					resultadoPregunta.setAudFechaActualizacion(new Date());
					r = service.updateResultadoPregunta(resultadoPregunta);
					res.setError((r) ? false : true);
					res.setMensaje((r) ? "Actualizacion Exitosa" : "Fallo la Actualizacion");
				} else if (resultadoPregunta.getCodPersona() != null
						&& resultadoPregunta.getCodPersona().longValue() > 0) {
					r = service.insertResultadoPregunta(resultadoPregunta);
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
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getpreguntaopinionrdn")
	@Consumes("text/plain")
	public Response getpreguntaopinionrdn(@Context HttpServletRequest req, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PreguntaOpinionService service = new PreguntaOpinionService();
			PreguntaOpinion d = service.getPreguntaOpinionRDN();
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getrespuestagrafico")
	@Consumes("text/plain")
	public Response getRespuestasGrafico(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			List<ResultadoPregunta> d = service.getRespuestasGrafico(user.getCodPreguntaOpinion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getentidadporpersona")
	@Consumes("text/plain")
	public Response getEntidadPorPersona(@Context HttpServletRequest req, String json,
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
			EntidadExt user = gson.fromJson(json, EntidadExt.class);
			EntidadService service = new EntidadService();
			List<EntidadExt> d = service.getEntidadPorPersona(user.getCodPersona());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getrespuestapreguntapersona")
	@Consumes("text/plain")
	public Response getRespuestaPreguntaPersona(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			List<ResultadoPreguntaExt> d = service.getRespuestaPreguntaPersona(user.getCodPreguntaOpinion());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getauditoriasigla")
	@Consumes("text/plain")
	public Response getVAccionAuditoriaSigla(@Context HttpServletRequest req, String json,
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
			VAccionAuditoria user = gson.fromJson(json, VAccionAuditoria.class);
			VAccionAuditoriaService service = new VAccionAuditoriaService();
			VAccionAuditoria d = service.getVAccionAuditoriaSigla(user.getAccionAuditoriaId());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getvaccionauditoria")
	@Consumes("text/plain")
	public Response getVAccionAuditoria(@Context HttpServletRequest req, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			VAccionAuditoriaService service = new VAccionAuditoriaService();
			List<VAccionAuditoria> d = service.getVAccionAuditoria();
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getauditoriaparametrica")
	@Consumes("text/plain")
	public Response getauditoriaparametrica(@Context HttpServletRequest req, String json,
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
			AuditoriaExt user = gson.fromJson(json, AuditoriaExt.class);
			AuditoriaService service = new AuditoriaService();
			List<AuditoriaExt> d = service.getAuditoriaParametro(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getauditoriafechas")
	@Consumes("text/plain")
	public Response getauditoriafechas(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			AuditoriaExt user = gson.fromJson(json, AuditoriaExt.class);
			List<AuditoriaExt> d = new ArrayList<AuditoriaExt>();
			AuditoriaExt e = new AuditoriaExt();
			if (user.getFechaIni() == null) {
				e.setError(true);
				e.setMensaje("Debe enviar Fecha Inicial");
				d.add(e);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} else if (user.getFechaFin() == null) {
				e.setError(true);
				e.setMensaje("Debe enviar Fecha Final");
				d.add(e);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} else if (user.getNombreTabla() == null || user.getNombreTabla().isEmpty()) {
				e.setError(true);
				e.setMensaje("Debe enviar Nombre de la Tabla");
				d.add(e);
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			}
			AuditoriaService service = new AuditoriaService();
			d = service.getAuditoriaFechas(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getrespuestaspreguestadistica")
	@Consumes("text/plain")
	public Response getRespuestasPreguntaesta(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			List<ResultadoPreguntaExt> d = service.getRespuestasEstadistica(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getrespuestagraficoedades")
	@Consumes("text/plain")
	public Response getRespuestasGraficoEdades(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			List<ResultadoPreguntaExt> d = service.getRespuestasGraficoEdades(user);
			String out = gson.toJson(d);
			out = out.replace("\\u003c\\u003d", "<=");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getcargo")
	@Consumes("text/plain")
	public Response getCargo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Cargo dep = gson.fromJson(json, Cargo.class);
			CargoService service = new CargoService();
			Cargo d = service.getCargo(dep.getCodCargo().longValue());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getcargoentidad")
	@Consumes("text/plain")
	public Response getCargosEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Entidad dep = gson.fromJson(json, Entidad.class);
			CargoService service = new CargoService();
			List<Cargo> d = service.getCargosEntidad(dep.getCodEntidad().longValue());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getpreguntaopinionfechapregunta")
	@Consumes("text/plain")
	public Response getpreguntaopinionfechapregunta(@Context HttpServletRequest req, String json,
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
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getpreguntaOpinionFechapregunta(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getpreguntaopinionfecha")
	@Consumes("text/plain")
	public Response getpreguntaopinionfecha(@Context HttpServletRequest req, String json,
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
			PreguntaOpinion user = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getpreguntaOpinionFechapregunta(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una acreditacion y tambien la
	 *         actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setEntidadPlanta")
	@Consumes("text/plain")
	public Response setAcreenciaObligacion(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		EntidadPlanta res = new EntidadPlanta();
		res.setError(true);
		res.setMensaje(
				"No se Encontro CodEntidadPlanta  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			EntidadPlanta EntidadPlanta = gson.fromJson(json, EntidadPlanta.class);
			EntidadPlanta.setAudFechaActualizacion(new Date());
			EntidadPlantaService service = new EntidadPlantaService();
			try {
				if (EntidadPlanta.getCodEntidadPlanta() != null && EntidadPlanta.getCodEntidadPlanta() > 0) {
					EntidadPlanta.setAudFechaActualizacion(new Date());
					res = service.updateEntidadPlanta(EntidadPlanta);
				} else if (EntidadPlanta.getCodClasePlanta() != null
						&& EntidadPlanta.getCodClasePlanta().longValue() > 0) {
					res = service.insertEntidadPlanta(EntidadPlanta);
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
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una lista de EntidadPlanta.
	 */
	@POST
	@Path("/getaEntidadPlantaentidad")
	@Consumes("text/plain")
	public Response getacreenciaobligacion(@Context HttpServletRequest req, String json,
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
			EntidadPlantaExt user = gson.fromJson(json, EntidadPlantaExt.class);
			EntidadPlantaService service = new EntidadPlantaService();
			List<EntidadPlanta> d = service.getEntidadPlantaentidad(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna una EntidadPlanta.
	 */
	@POST
	@Path("/getaEntidadPlantaid")
	@Consumes("text/plain")
	public Response getaEntidadPlantaid(@Context HttpServletRequest req, String json,
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
			EntidadPlanta user = gson.fromJson(json, EntidadPlanta.class);
			EntidadPlantaService service = new EntidadPlantaService();
			EntidadPlanta d = service.getEntidadPlantaById(user.getCodEntidadPlanta());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio Actualiza elabeasdata.
	 */
	@POST
	@Path("/updateabeasdata")
	@Consumes("text/plain")
	public Response updateabeasdata(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Usuario user = gson.fromJson(json, Usuario.class);
			user.setFechaAceptoHabeas(new Date());
			user.setFlgAceptoHabeasData((short) 1);
			user.setAudFechaActualizacion(new Date());
			user.setFlgEstado((short) 1);
			UsuarioService service = new UsuarioService();
			Usuario d = service.updateUsuarioSelective(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
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
	@Path("/getentidadcodpersona")
	@Consumes("text/plain")
	public Response getentidadcodpersona(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			Usuario user = gson.fromJson(json, Usuario.class);
			EntidadService service = new EntidadService();
			List<Entidad> d = service.getEntidadporCodUsuario(user.getCodPersona());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getdependenciaentidadpersona")
	@Consumes("text/plain")
	public Response getdependenciaentidadpersona(@Context HttpServletRequest req, String json,
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
			DependenciaEntidadExt dep = gson.fromJson(json, DependenciaEntidadExt.class);
			DependenciaEntidadService service = new DependenciaEntidadService();
			List<DependenciaEntidad> d = service.getDependenciaEntidadPersona(dep);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getrolhijos")
	@Consumes("text/plain")
	public Response getRolHijos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Rol ro = gson.fromJson(json, Rol.class);
			RolService service = new RolService();
			List<Rol> d = service.gettHijosRol(ro);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getrolespersona")
	@Consumes("text/plain")
	public Response getrolespersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			PersonaExt ro = gson.fromJson(json, PersonaExt.class);
			RolService service = new RolService();
			List<Rol> d = service.rolesPersona(ro);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getmunicipiosid")
	@Consumes("text/plain")
	public Response getMunicipiosid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Municipio mun = gson.fromJson(json, Municipio.class);
			MunicipioService service = new MunicipioService();
			Municipio d = service.getMunicipioById(mun.getCodMunicipio());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getidiomasid")
	@Consumes("text/plain")
	public Response idiomasid(@Context HttpServletRequest req, @HeaderParam("token") String token, String json,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Idioma idioma = gson.fromJson(json, Idioma.class);
			IdiomaService service = new IdiomaService();
			Idioma d = service.getIdiomaById(idioma.getCodIdioma());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			Idioma d = new Idioma();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una acreditacion y tambien la
	 *         actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setcmactivarusuarios")
	@Consumes("text/plain")
	public Response setCmActivarUsuarios(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmActivarUsuarios res = new CmActivarUsuarios();
		try {
			CmActivarUsuarios cmActivarUsuarios = gson.fromJson(json, CmActivarUsuarios.class);
			cmActivarUsuarios.setAudFechaActualizacion(new Date());
			CmActivarUsuariosService service = new CmActivarUsuariosService();
			if (cmActivarUsuarios.getCodCmActivarUsuarios() != null
					&& cmActivarUsuarios.getCodCmActivarUsuarios() > 0) {
				cmActivarUsuarios.setAudFechaActualizacion(new Date());
				res = service.updateCmActivarUsuarios(cmActivarUsuarios);
			} else {
				cmActivarUsuarios.setAudFechaActualizacion(new Date());
				res = service.insertCmActivarUsuarios(cmActivarUsuarios);
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
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getcmactivarusuarios")
	@Consumes("text/plain")
	public Response getCmActivarUsuarios(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmActivarUsuarios cmActivarUsuarios = gson.fromJson(json, CmActivarUsuarios.class);
			CmActivarUsuariosService service = new CmActivarUsuariosService();
			CmActivarUsuarios d = service.getCmActivarUsuariosById(cmActivarUsuarios.getCodCmActivarUsuarios());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmActivarUsuarios d = new CmActivarUsuarios();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una acreditacion y tambien la
	 *         actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setprocesoscargamasiva")
	@Consumes("text/plain")
	public Response setProcesosCargaMasiva(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {

		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		ProcesosCargaMasiva res = new ProcesosCargaMasiva();
		res.setError(true);
		res.setMensaje(
				"No se Encontro getCodCmActivarUsuarios  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			ProcesosCargaMasiva procesosCargaMasiva = gson.fromJson(json, ProcesosCargaMasiva.class);
			procesosCargaMasiva.setAudFechaActualizacion(new Date());
			ProcesosCargaMasivaService service = new ProcesosCargaMasivaService();
			try {
				if (procesosCargaMasiva.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					procesosCargaMasiva.setAudFechaActualizacion(new Date());
					res = service.insertProcesosCargaMasiva(procesosCargaMasiva);
				} else if (procesosCargaMasiva.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| procesosCargaMasiva.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					procesosCargaMasiva.setAudFechaActualizacion(new Date());
					res = service.updateProcesosCargaMasiva(procesosCargaMasiva);
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
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getprocesoscargamasiva")
	@Consumes("text/plain")
	public Response getProcesosCargaMasiva(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		/*gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);*/
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			ProcesosCargaMasiva cmActivarUsuarios = gson.fromJson(json, ProcesosCargaMasiva.class);
			ProcesosCargaMasivaService service = new ProcesosCargaMasivaService();
			ProcesosCargaMasivaExt d = service.getProcesosCargaMasivaById(cmActivarUsuarios.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmActivarUsuarios d = new CmActivarUsuarios();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getprocesoscargamasivafiltro")
	@Consumes("text/plain")
	public Response getprocesoscargamasivafiltro(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA + " HH:mm.ss.s");
		gson = gsonBuilder.create();
		try {
			PersonaExt cmActivarUsuarios = gson.fromJson(json, PersonaExt.class);
			ProcesosCargaMasivaService service = new ProcesosCargaMasivaService();
			List<ProcesosCargaMasivaExt> d = service.cargaMasivafiltro(cmActivarUsuarios);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<ProcesosCargaMasivaExt> p = new ArrayList<>();
			ProcesosCargaMasivaExt d = new ProcesosCargaMasivaExt();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			p.add(d);
			return Response.status(201).entity(gson.toJson(p)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/getsequencia")
	@Consumes("text/plain")
	public Response getsequencia(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SequenciasSigep sequenciasSigep = gson.fromJson(json, SequenciasSigep.class);
			SequenciasSigepService service = new SequenciasSigepService();
			SequenciasSigep d = service.getSequenciasSigep(sequenciasSigep.getTablaNombre());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			SequenciasSigep d = new SequenciasSigep();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/setSecuenciaCodigoSigepEntidad")
	@Consumes("text/plain")
	public Response setSecuenciaCodigoSigepEntidad(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SequenciasSigep sequenciasSigep = gson.fromJson(json, SequenciasSigep.class);
			SequenciasSigepService service = new SequenciasSigepService();
			SequenciasSigep d = service.setSequenciasSigepCodigoEntidad(sequenciasSigep.getTablaNombre());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			SequenciasSigep d = new SequenciasSigep();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
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
	@Path("/setsequencia")
	@Consumes("text/plain")
	public Response setsequencia(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			SequenciasSigep d = new SequenciasSigep();
			SequenciasSigep sequenciasSigep = gson.fromJson(json, SequenciasSigep.class);
			SequenciasSigepService service = new SequenciasSigepService();
			if (sequenciasSigep.getTablaNombre() != null && sequenciasSigep.getTablaNombre().length() > 5) {
				if (sequenciasSigep.getSecuencia() != null) {
					if (sequenciasSigep.getTipoRetorno() != null) {
						if (sequenciasSigep.getDescripcion() != null && sequenciasSigep.getTablaNombre().length() > 5) {
							sequenciasSigep.setFechaSq(new Date());
							d = service.insertSequenciasSigep(sequenciasSigep);
							String out = gson.toJson(d);
							return Response.status(201).entity(out).build();
						} else {
							d.setMensaje("No Se ha enviado Descripcion");
						}
					} else {
						d.setMensaje("No Se ha enviado Tipo Retorno");
					}
				} else {
					d.setMensaje("No Se ha enviado Sequencia Inicial");
				}
			} else {
				d.setMensaje("No Se ha enviado Nombre de la Tabla Secuencia");
			}
			d.setError(true);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (JsonParseException e) {
			SequenciasSigep d = new SequenciasSigep();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una acreditacion y tambien la
	 *         actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setcmhvinformacionbasica")
	@Consumes("text/plain")
	public Response setCmHvInformacionBasica(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmHvInformacionBasica res = new CmHvInformacionBasica();
		res.setError(true);
		res.setMensaje("No se Encontro AudAccion  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			CmHvInformacionBasica cmHvInformacionBasica = gson.fromJson(json, CmHvInformacionBasica.class);
			cmHvInformacionBasica.setAudFechaActualizacion(new Date());
			CmHvInformacionBasicaService service = new CmHvInformacionBasicaService();
			try {
				if (cmHvInformacionBasica.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmHvInformacionBasica.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmHvInformacionBasica.setAudFechaActualizacion(new Date());
					res = service.updateCmHvInformacionBasica(cmHvInformacionBasica);
				} else if (cmHvInformacionBasica.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmHvInformacionBasica(cmHvInformacionBasica);
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
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una acreditacion y tambien la
	 *         actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setcmconfiguracion")
	@Consumes("text/plain")
	public Response setCmConfiguracion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmConfiguracion res = new CmConfiguracion();
		res.setError(true);
		res.setMensaje(
				"No se Encontro getCodCmActivarUsuarios  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			CmConfiguracion cmConfiguracion = gson.fromJson(json, CmConfiguracion.class);
			cmConfiguracion.setAudFechaActualizacion(new Date());
			CmConfiguracionService service = new CmConfiguracionService();
			try {
				if (cmConfiguracion.getCodCmConfiguracion() != null && cmConfiguracion.getCodCmConfiguracion() > 0) {
					cmConfiguracion.setAudFechaActualizacion(new Date());
					res = service.updateCmConfiguracion(cmConfiguracion);
				} else if (cmConfiguracion.getCodProcesoCargue() != null
						&& cmConfiguracion.getCodProcesoCargue().longValue() > 0) {
					res = service.insertCmConfiguracion(cmConfiguracion);
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
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/getcmconfiguracion")
	@Consumes("text/plain")
	public Response getCmConfiguracion(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmConfiguracion cmConfiguracion = gson.fromJson(json, CmConfiguracion.class);
			CmConfiguracionService service = new CmConfiguracionService();
			List<CmConfiguracion> d = service.getCmConfiguracionFiltro(cmConfiguracion);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			SequenciasSigep d = new SequenciasSigep();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * SERWEB000209
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una acreditacion y tambien la
	 *         actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setcmcrearusuarios")
	@Consumes("text/plain")
	public Response setCmCrearUsuarios(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmCrearUsuarios res = new CmCrearUsuarios();
		res.setError(true);
		res.setMensaje(
				"No se Encontro getCodCmActivarUsuarios  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			CmCrearUsuarios cmCrearUsuarios = gson.fromJson(json, CmCrearUsuarios.class);
			cmCrearUsuarios.setAudFechaActualizacion(new Date());
			CmCrearUsuariosService service = new CmCrearUsuariosService();
			try {
				if (cmCrearUsuarios.getCodCmCrearUsuarios() != null && cmCrearUsuarios.getCodCmCrearUsuarios() > 0) {
					cmCrearUsuarios.setAudFechaActualizacion(new Date());
					res = service.updateCmCrearUsuarios(cmCrearUsuarios);
				} else if (cmCrearUsuarios.getCodProcesoCargaMasiva() != null) {
					res = service.insertCmCrearUsuarios(cmCrearUsuarios);
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
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una acreditacion y tambien la
	 *         actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setcmhvEducaciondesaHumano")
	@Consumes("text/plain")
	public Response setCmHvEducacionDesaHumano(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmHvEducacionDesaHumano res = new CmHvEducacionDesaHumano();
		res.setError(true);
		res.setMensaje(
				"No se Encontro getCodCmActivarUsuarios  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			CmHvEducacionDesaHumano cmHvEducacionDesaHumano = gson.fromJson(json, CmHvEducacionDesaHumano.class);
			cmHvEducacionDesaHumano.setAudFechaActualizacion(new Date());
			CmHvEducacionDesaHumanoService service = new CmHvEducacionDesaHumanoService();
			try {
				if (cmHvEducacionDesaHumano.getCodCmHvEducacionDesaHumano() != null
						&& cmHvEducacionDesaHumano.getCodCmHvEducacionDesaHumano() > 0) {
					cmHvEducacionDesaHumano.setAudFechaActualizacion(new Date());
					res = service.updateCmHvEducacionDesaHumano(cmHvEducacionDesaHumano);
				} else if (cmHvEducacionDesaHumano.getCodProcesoCargaMasiva() != null) {
					res = service.insertCmHvEducacionDesaHumano(cmHvEducacionDesaHumano);
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
	 * @author: Jose Viscaya
	 * @param req
	 * @param json
	 * @param token
	 * @return Servicio que crea en base de datos una acreditacion y tambien la
	 *         actualiza dependiendo de los Id que envie el cliente REst
	 */
	@POST
	@Path("/setcmhveducacionformal")
	@Consumes("text/plain")
	public Response setCmHvEducacionFormal(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		Gson gson;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmHvEducacionFormal res = new CmHvEducacionFormal();
		res.setError(true);
		res.setMensaje(
				"No se Encontro getCodCmActivarUsuarios  el servicio no puede realizar Insert o Update verifique Informacion");
		try {
			CmHvEducacionFormal cmHvEducacionFormal = gson.fromJson(json, CmHvEducacionFormal.class);
			cmHvEducacionFormal.setAudFechaActualizacion(new Date());
			CmHvEducacionFormalService service = new CmHvEducacionFormalService();
			try {
				if (cmHvEducacionFormal.getCodCmHvEducacionFormal() != null
						&& cmHvEducacionFormal.getCodCmHvEducacionFormal() > 0) {
					cmHvEducacionFormal.setAudFechaActualizacion(new Date());
					res = service.updateCmHvEducacionFormal(cmHvEducacionFormal);
				} else if (cmHvEducacionFormal.getCodProcesoCargaMasiva() != null) {
					res = service.insertCmHvEducacionFormal(cmHvEducacionFormal);
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
	 * SERWEB000212 Devuelve los recursos asociados a los permisos por roles
	 * 
	 * @param req
	 *            Peticion del servicio
	 * @param token
	 *            Cadena con token de sesion, estara vigente mientras no se haya
	 *            superado el tiempo maximo de vigencia o se mantega la sesion
	 *            activa del usuario
	 * @param json
	 *            Cadena en formato json con los datos a procesar
	 * @param timeout
	 *            Tiempo maximo de vigencia de la sesion mientras haya actividad
	 *            en el consumo de servicios
	 */
	@POST
	@Path("/recursosaccion")
	@Consumes("text/plain")
	public Response getRecursoAccionByLikeNombreRol(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd/MM/yyyy");
		gson = gsonBuilder.create();
		try {
			RecursoAccion recursoAccion = gson.fromJson(json, RecursoAccion.class);
			RecursoAccionService service = new RecursoAccionService();
			List<RecursoAccion> result = service.getRecursoAccionByLikeNombreRol(recursoAccion);
			String out = gson.toJson(result);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			RecursoAccion d = new RecursoAccion();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * SERWEB000213 Gestiona el CRUD de un {@link PermisoRolAccion} de acuerdo a
	 * {@link RecursoAccionService#procesarPermiso(PermisoRolAccion)}
	 * 
	 * @param req
	 *            Peticion del servicio
	 * @param token
	 *            Cadena con token de sesion, estara vigente mientras no se haya
	 *            superado el tiempo maximo de vigencia o se mantega la sesion
	 *            activa del usuario
	 * @param json
	 *            Cadena en formato json con los datos a procesar
	 * @param timeout
	 *            Tiempo maximo de vigencia de la sesion mientras haya actividad
	 *            en el consumo de servicios
	 */
	@POST
	@Path("/procesarpermiso")
	@Consumes("text/plain")
	public Response procesarPermiso(@Context HttpServletRequest req, @HeaderParam("token") String token, String json,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd/MM/yyyy");
		gson = gsonBuilder.create();
		try {
			String out = null;
			if (json.contains("codPermisoRolAcciones")) {
				PermisoRolAccion permisoRolAccion = gson.fromJson(json, PermisoRolAccion.class);
				RecursoAccionService service = new RecursoAccionService();
				PermisoRolAccion result = service.procesarPermiso(permisoRolAccion);
				out = gson.toJson(result);
			} else {
				PermisoRol permisoRol = gson.fromJson(json, PermisoRol.class);
				if (permisoRol.getCodPermisoRol() == null) {
					permisoRol.setFlgEstado(false);
				}
				RecursoAccionService service = new RecursoAccionService();
				PermisoRol result = service.procesarPermiso(permisoRol);
				out = gson.toJson(result);
			}
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			PermisoRolAccion d = new PermisoRolAccion();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(d)).build();
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
	@Path("/getcmcrearcsuarioscodproceso")
	@Consumes("text/plain")
	public Response getcmcrearcsuarios(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmCrearUsuarios cmCrearUsuarios = gson.fromJson(json, CmCrearUsuarios.class);
			CmCrearUsuariosService service = new CmCrearUsuariosService();
			List<CmCrearUsuarios> d = service.getCmCrearUsuariosCodProceso(cmCrearUsuarios.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<CmCrearUsuarios> ds = new ArrayList<>();
			CmCrearUsuarios d = new CmCrearUsuarios();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			ds.add(d);
			return Response.status(201).entity(gson.toJson(ds)).build();
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
	@Path("/getcargopofiltro")
	@Consumes("text/plain")
	public Response getcargopofiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Cargo ca = gson.fromJson(json, Cargo.class);
			CargoService service = new CargoService();
			List<CargoExt> d = service.getCargoFilter(ca);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getcmactivarusuariosporcargamasiva")
	@Consumes("text/plain")
	public Response getcmactivarusuariosporcargamasiva(@Context HttpServletRequest req,
			@HeaderParam("token") String token, String json, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmActivarUsuarios cmActivarUsuarios = gson.fromJson(json, CmActivarUsuarios.class);
			CmActivarUsuariosService service = new CmActivarUsuariosService();
			List<CmActivarUsuarios> d = service
					.getCmActivarUsuariosCodProceso(cmActivarUsuarios.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmActivarUsuarios d = new CmActivarUsuarios();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getcmhvevaluaciondesempeno")
	@Consumes("text/plain")
	public Response getCmHvEvaluacionDesempeno(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmHvEvaluacionDesempeno cmHvEvaluacionDesempeno = gson.fromJson(json, CmHvEvaluacionDesempeno.class);
			CmHvEvaluacionDesempenoService service = new CmHvEvaluacionDesempenoService();
			List<CmHvEvaluacionDesempeno> d = service
					.getCmHvEvaluacionDesempenoProcarga(cmHvEvaluacionDesempeno.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmActivarUsuarios d = new CmActivarUsuarios();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/setcmhvevaluaciondesempeno")
	@Consumes("text/plain")
	public Response getcmhvevaluaciondesempeno(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmHvEvaluacionDesempeno res = new CmHvEvaluacionDesempeno();
		try {
			CmHvEvaluacionDesempeno cmHvEvaluacionDesempeno = gson.fromJson(json, CmHvEvaluacionDesempeno.class);
			cmHvEvaluacionDesempeno.setAudFechaActualizacion(new Date());
			CmHvEvaluacionDesempenoService service = new CmHvEvaluacionDesempenoService();
			try {
				if (cmHvEvaluacionDesempeno.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmHvEvaluacionDesempeno.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmHvEvaluacionDesempeno.setAudFechaActualizacion(new Date());
					res = service.updateCmHvEvaluacionDesempeno(cmHvEvaluacionDesempeno);
				} else if (cmHvEvaluacionDesempeno.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmHvEvaluacionDesempeno(cmHvEvaluacionDesempeno);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27 Juli 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna lista de CmHvEducacionOtros
	 */
	@POST
	@Path("/getcmHvEducacionotros")
	@Consumes("text/plain")
	public Response getCmHvEducacionOtros(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmHvEducacionOtros cmHvEducacionOtros = gson.fromJson(json, CmHvEducacionOtros.class);
			CmHvEducacionOtrosService service = new CmHvEducacionOtrosService();
			List<CmHvEducacionOtros> d = service
					.getCmHvEducacionOtrosProceso(cmHvEducacionOtros.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmHvEducacionOtros d = new CmHvEducacionOtros();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27 Juli 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio inserta y actualiza en la tabla CmHvEducacionOtros
	 */
	@POST
	@Path("/setcmHvEducacionotros")
	@Consumes("text/plain")
	public Response setCmHvEducacionOtros(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmHvEducacionOtros res = new CmHvEducacionOtros();
		try {
			CmHvEducacionOtros cmHvEducacionOtros = gson.fromJson(json, CmHvEducacionOtros.class);
			cmHvEducacionOtros.setAudFechaActualizacion(new Date());
			CmHvEducacionOtrosService service = new CmHvEducacionOtrosService();
			try {
				if (cmHvEducacionOtros.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmHvEducacionOtros.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmHvEducacionOtros.setAudFechaActualizacion(new Date());
					res = service.updateCmHvEducacionOtros(cmHvEducacionOtros);
				} else if (cmHvEducacionOtros.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmHvEducacionOtros(cmHvEducacionOtros);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27 Juli 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna lista de CmHvEducacionIdiomas
	 */
	@POST
	@Path("/getcmHvEducacionidiomas")
	@Consumes("text/plain")
	public Response getCmHvEducacionIdiomas(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmHvEducacionIdiomas cmHvEducacionIdiomas = gson.fromJson(json, CmHvEducacionIdiomas.class);
			CmHvEducacionIdiomasService service = new CmHvEducacionIdiomasService();
			List<CmHvEducacionIdiomas> d = service
					.getCmHvEducacionIdiomasProceso(cmHvEducacionIdiomas.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmHvEducacionIdiomas d = new CmHvEducacionIdiomas();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27 Juli 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio inserta y actualiza en la tabla
	 *         CmHvEducacionIdiomas
	 */
	@POST
	@Path("/setcmHvEducacionidiomas")
	@Consumes("text/plain")
	public Response setCmHvEducacionIdiomas(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmHvEducacionIdiomas res = new CmHvEducacionIdiomas();
		try {
			CmHvEducacionIdiomas cmHvEducacionIdiomas = gson.fromJson(json, CmHvEducacionIdiomas.class);
			cmHvEducacionIdiomas.setAudFechaActualizacion(new Date());
			CmHvEducacionIdiomasService service = new CmHvEducacionIdiomasService();
			try {
				if (cmHvEducacionIdiomas.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmHvEducacionIdiomas.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmHvEducacionIdiomas.setAudFechaActualizacion(new Date());
					res = service.updateCmHvEducacionIdiomas(cmHvEducacionIdiomas);
				} else if (cmHvEducacionIdiomas.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmHvEducacionIdiomas(cmHvEducacionIdiomas);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27 Juli 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna lista de CmHvExperienciaLaboral
	 */
	@POST
	@Path("/getcmHvExperiencialaboral")
	@Consumes("text/plain")
	public Response getCmHvExperienciaLaboral(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmHvExperienciaLaboral cmHvExperienciaLaboral = gson.fromJson(json, CmHvExperienciaLaboral.class);
			CmHvExperienciaLaboralService service = new CmHvExperienciaLaboralService();
			List<CmHvExperienciaLaboral> d = service
					.getCmHvExperienciaLaboralProceso(cmHvExperienciaLaboral.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmHvExperienciaLaboral d = new CmHvExperienciaLaboral();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27 Juli 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio inserta y actualiza en la tabla
	 *         CmHvExperienciaLaboral
	 */
	@POST
	@Path("/setCmHvExperienciaLaboral")
	@Consumes("text/plain")
	public Response setCmHvExperienciaLaboral(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmHvExperienciaLaboral res = new CmHvExperienciaLaboral();
		try {
			CmHvExperienciaLaboral cmHvExperienciaLaboral = gson.fromJson(json, CmHvExperienciaLaboral.class);
			cmHvExperienciaLaboral.setAudFechaActualizacion(new Date());
			CmHvExperienciaLaboralService service = new CmHvExperienciaLaboralService();
			try {
				if (cmHvExperienciaLaboral.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmHvExperienciaLaboral.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmHvExperienciaLaboral.setAudFechaActualizacion(new Date());
					res = service.updateCmHvExperienciaLaboral(cmHvExperienciaLaboral);
				} else if (cmHvExperienciaLaboral.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmHvExperienciaLaboral(cmHvExperienciaLaboral);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27 Juli 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio retorna lista de CmHvExperienciaLaboralDocen
	 */
	@POST
	@Path("/getcmHvexperiencialaboraldocen")
	@Consumes("text/plain")
	public Response getCmHvExperienciaLaboralDocen(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmHvExperienciaLaboralDocen cmHvExperienciaLaboralDocen = gson.fromJson(json,
					CmHvExperienciaLaboralDocen.class);
			CmHvExperienciaLaboralDocenService service = new CmHvExperienciaLaboralDocenService();
			List<CmHvExperienciaLaboralDocen> d = service
					.getCmHvExperienciaLaboralDocenProceso(cmHvExperienciaLaboralDocen.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmHvExperienciaLaboralDocen d = new CmHvExperienciaLaboralDocen();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27 Juli 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio inserta y actualiza en la tabla
	 *         CmHvExperienciaLaboralDocen
	 */
	@POST
	@Path("/setcmHvExperiencialaboraldocen")
	@Consumes("text/plain")
	public Response setCmHvExperienciaLaboralDocen(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmHvExperienciaLaboralDocen res = new CmHvExperienciaLaboralDocen();
		try {
			CmHvExperienciaLaboralDocen cmHvExperienciaLaboralDocen = gson.fromJson(json,
					CmHvExperienciaLaboralDocen.class);
			cmHvExperienciaLaboralDocen.setAudFechaActualizacion(new Date());
			CmHvExperienciaLaboralDocenService service = new CmHvExperienciaLaboralDocenService();
			try {
				if (cmHvExperienciaLaboralDocen.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmHvExperienciaLaboralDocen.getAudAccion()
								.equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmHvExperienciaLaboralDocen.setAudFechaActualizacion(new Date());
					res = service.updateCmHvExperienciaLaboralDocen(cmHvExperienciaLaboralDocen);
				} else if (cmHvExperienciaLaboralDocen.getAudAccion()
						.equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmHvExperienciaLaboralDocen(cmHvExperienciaLaboralDocen);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 30 Julio 2018
	 * @param req
	 * @param json
	 * @param token
	 * @return Este Servicio inserta y actualiza en la tabla Cargo
	 */
	@POST
	@Path("/setcargo")
	@Consumes("text/plain")
	public Response setentidadcargo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CargoService service = new CargoService();
		Cargo res = new Cargo();
		try {
			Cargo cargo = gson.fromJson(json, Cargo.class);
			try {
				if (cargo.getCodCargo() != null && cargo.getCodCargo() > 0) {
					cargo.setAudFechaActualizacion(new Date());
					res = service.updateCargo(cargo);
				} else if (cargo.getCodCargo() == null || cargo.getCodCargo() == 0) {
					res = service.insertCargo(cargo);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 1/08/2018 2:30:38 p. m.
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/getcmHvInformacionBasica")
	@Consumes("text/plain")
	public Response getCmHvInformacionBasica(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmHvInformacionBasica cmHvInformacionBasica = gson.fromJson(json, CmHvInformacionBasica.class);
			CmHvInformacionBasicaService service = new CmHvInformacionBasicaService();
			List<CmHvInformacionBasica> d = service.getCmHvInformacionBasicaByProceso(cmHvInformacionBasica);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmHvEducacionFormal d = new CmHvEducacionFormal();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 1/08/2018 2:37:22 p. m.
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/getcmHvEducacionFormal")
	@Consumes("text/plain")
	public Response getCmHvEducacionFormal(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmHvEducacionFormal cmHvEducacionFormal = gson.fromJson(json, CmHvEducacionFormal.class);
			CmHvEducacionFormalService service = new CmHvEducacionFormalService();
			List<CmHvEducacionFormal> d = service.getCmHvEducacionFormalByProceso(cmHvEducacionFormal);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmHvEducacionFormal d = new CmHvEducacionFormal();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 1/08/2018 2:42:06 p. m.
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/getcmHvEducacionDesaHumano")
	@Consumes("text/plain")
	public Response getCmHvEducacionDesaHumano(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmHvEducacionDesaHumano cmHvEducacionDesaHumano = gson.fromJson(json, CmHvEducacionDesaHumano.class);
			CmHvEducacionDesaHumanoService service = new CmHvEducacionDesaHumanoService();
			List<CmHvEducacionDesaHumano> d = service.getCmHvEducacionDesaHumanoByProceso(cmHvEducacionDesaHumano);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmHvEducacionDesaHumano d = new CmHvEducacionDesaHumano();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 9 ago. 2018 7:40:39
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setcmsituacionesadministrativas")
	@Consumes("text/plain")
	public Response setCmSituacionesAdministrativas(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		LoggerSigep.getInstance().info(json, ServicesSigepRestSis.class);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmSituacionesAdministrativas res = new CmSituacionesAdministrativas();
		try {
			CmSituacionesAdministrativas cmSituacionesAdministrativas = gson.fromJson(json,
					CmSituacionesAdministrativas.class);
			cmSituacionesAdministrativas.setAudFechaActualizacion(new Date());
			CmSituacionesAdministrativasService service = new CmSituacionesAdministrativasService();
			LoggerSigep.getInstance().info(cmSituacionesAdministrativas.getNumActoAdministrativo(),
					ServicesSigepRestSis.class);
			try {
				if (cmSituacionesAdministrativas.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmSituacionesAdministrativas.getAudAccion()
								.equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmSituacionesAdministrativas.setAudFechaActualizacion(new Date());
					res = service.updateCmSituacionesAdministrativas(cmSituacionesAdministrativas);
				} else if (cmSituacionesAdministrativas.getAudAccion()
						.equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmSituacionesAdministrativas(cmSituacionesAdministrativas);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 9 ago. 2018 7:43:23
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/getcmsituacionesadministrativas")
	@Consumes("text/plain")
	public Response getCmsituacionesadministrativas(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmSituacionesAdministrativas cmSituacionesAdministrativas = gson.fromJson(json,
					CmSituacionesAdministrativas.class);
			CmSituacionesAdministrativasService service = new CmSituacionesAdministrativasService();
			List<CmSituacionesAdministrativas> d = service
					.getCmSituacionesAdministrativasByProceso(cmSituacionesAdministrativas);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmSituacionesAdministrativas d = new CmSituacionesAdministrativas();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 10/08/2018 8:22:42 a. m.
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return Response
	 */
	@POST
	@Path("/setcmcontratos")
	@Consumes("text/plain")
	public Response setCmContratos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmContratos res = new CmContratos();
		try {
			CmContratos cmContratos = gson.fromJson(json, CmContratos.class);
			cmContratos.setAudFechaActualizacion(new Date());
			CmContratosService service = new CmContratosService();
			try {
				if (cmContratos.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmContratos.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmContratos.setAudFechaActualizacion(new Date());
					res = service.updateCmContratos(cmContratos);
				} else if (cmContratos.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmContratos(cmContratos);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 10/08/2018 8:22:27 a. m.
	 * @param req
	 * @param json
	 * @return Response
	 */
	@POST
	@Path("/getcmcontratos")
	@Consumes("text/plain")
	public Response getCmContratos(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmContratos cmContratos = gson.fromJson(json, CmContratos.class);
			CmContratosService service = new CmContratosService();
			List<CmContratos> d = service.getCmContratosCodProceso(cmContratos.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmContratos d = new CmContratos();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 10/09/2018 3:09:53 p.m.
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 *
	 */
	@POST
	@Path("/setcmvinculaciones")
	@Consumes("text/plain")
	public Response setCmVinculaciones(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmVinculaciones res = new CmVinculaciones();
		try {
			CmVinculaciones cmVinculaciones = gson.fromJson(json, CmVinculaciones.class);
			cmVinculaciones.setAudFechaActualizacion(new Date());
			CmVinculacionesService service = new CmVinculacionesService();
			try {
				if (cmVinculaciones.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmVinculaciones.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmVinculaciones.setAudFechaActualizacion(new Date());
					res = service.updateCmVinculaciones(cmVinculaciones);
				} else if (cmVinculaciones.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmVinculaciones(cmVinculaciones);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 10/08/2018 8:22:27 a. m.
	 * @param req
	 * @param json
	 * @return Response‰
	 */
	@POST
	@Path("/getcmvinculaciones")
	@Consumes("text/plain")
	public Response getCmVinculaciones(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmVinculaciones cmVinculaciones = gson.fromJson(json, CmVinculaciones.class);
			CmVinculacionesService service = new CmVinculacionesService();
			List<CmVinculaciones> d = service.getCmVinculacionesByProceso(cmVinculaciones);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmContratos d = new CmContratos();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 12/09/2018 9:29:26 a.m.
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 *
	 */
	@POST
	@Path("/setcmroles")
	@Consumes("text/plain")
	public Response setCmRoles(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmRoles res = new CmRoles();
		try {
			CmRoles cmRoles = gson.fromJson(json, CmRoles.class);
			cmRoles.setAudFechaActualizacion(new Date());
			CmRolesService service = new CmRolesService();
			try {
				if (cmRoles.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmRoles.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmRoles.setAudFechaActualizacion(new Date());
					res = service.updateCmRoles(cmRoles);
				} else if (cmRoles.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmRoles(cmRoles);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 12/09/2018 9:29:34 a.m.
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/getcmroles")
	@Consumes("text/plain")
	public Response getCmRoles(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmRoles cmVinculaciones = gson.fromJson(json, CmRoles.class);
			CmRolesService service = new CmRolesService();
			List<CmRoles> d = service.getCmRolesByProceso(cmVinculaciones);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmRoles d = new CmRoles();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 3/10/2018 - 10:20:55 a. m.
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setcmcrearentidad")
	@Consumes("text/plain")
	public Response setCmentidades(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmCrearEntidad res = new CmCrearEntidad();
		try {
			CmCrearEntidad cmCrearEntidad = gson.fromJson(json, CmCrearEntidad.class);
			cmCrearEntidad.setAudFechaActualizacion(new Date());
			CmCrearEntidadService service = new CmCrearEntidadService();
			try {
				if (cmCrearEntidad.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmCrearEntidad.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmCrearEntidad.setAudFechaActualizacion(new Date());
					res = service.updateCmCrearEntidad(cmCrearEntidad);
				} else if (cmCrearEntidad.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmCrearEntidad(cmCrearEntidad);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 10/08/2018 8:22:27 a. m.
	 * @param req
	 * @param json
	 * @return Response
	 */
	@POST
	@Path("/getcmcrearcentidad")
	@Consumes("text/plain")
	public Response getCmCrearEntidad(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmCrearEntidad cmCrearEntidad = gson.fromJson(json, CmCrearEntidad.class);
			CmCrearEntidadService service = new CmCrearEntidadService();
			List<CmCrearEntidad> d = service.getCmCrearEntidadCodProceso(cmCrearEntidad.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmCrearEntidad d = new CmCrearEntidad();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 9/10/2018 - 2:43:06 p. m.
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setcmcrearnomenclaturasalarial")
	@Consumes("text/plain")
	public Response getCmCrearNomenclaturaSalarial(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmCrearNomenclaturaSalarial res = new CmCrearNomenclaturaSalarial();
		try {
			CmCrearNomenclaturaSalarial cmCrearNomenclaturaSalarial = gson.fromJson(json,
					CmCrearNomenclaturaSalarial.class);
			cmCrearNomenclaturaSalarial.setAudFechaActualizacion(new Date());
			CmCrearNomenclaturaSalarialService service = new CmCrearNomenclaturaSalarialService();
			try {
				if (cmCrearNomenclaturaSalarial.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmCrearNomenclaturaSalarial.getAudAccion()
								.equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmCrearNomenclaturaSalarial.setAudFechaActualizacion(new Date());
					res = service.updateCmCrearNomenclaturaSalarial(cmCrearNomenclaturaSalarial);
				} else if (cmCrearNomenclaturaSalarial.getAudAccion()
						.equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmCrearNomenclaturaSalarial(cmCrearNomenclaturaSalarial);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 9/10/2018 - 2:49:50 p. m.
	 * @param req
	 * @param json
	 * @return
	 */
	@POST
	@Path("/getcmcrearnomenclaturasalarial")
	@Consumes("text/plain")
	public Response getCmCrearNomenclaturaSalarial(@Context HttpServletRequest req, String json) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CmCrearNomenclaturaSalarial cmCrearNomenclaturaSalarial = gson.fromJson(json,
					CmCrearNomenclaturaSalarial.class);
			CmCrearNomenclaturaSalarialService service = new CmCrearNomenclaturaSalarialService();
			List<CmCrearNomenclaturaSalarial> d = service
					.getCmCrearNomenclaturaSalarialCodProceso(cmCrearNomenclaturaSalarial.getCodProcesoCargaMasiva());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmCrearNomenclaturaSalarial d = new CmCrearNomenclaturaSalarial();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author Maria Alejandra Colorado
	 * @fecha 08/01/2019
	 * @param req
	 * @param json
	 * @return Retorna el nivel jerarquico de las personas que
	 */
	@POST
	@Path("/getrespuestasniveljerarquico")
	@Consumes("text/plain")
	public Response getRespuestaNivelJerarquico(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			List<ResultadoPreguntaExt> d = service.getRespuestasNivelJerarquico(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 *
	 * @param req
	 * @return
	 */
	@POST
	@Path("/getrolesbase")
	@Consumes("text/plain")
	public Response getRoleBase(@Context HttpServletRequest req) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			RolService service = new RolService();
			List<Rol> d = service.getRolBase();
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmCrearNomenclaturaSalarial d = new CmCrearNomenclaturaSalarial();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 9 ene. 2019
	 * @param req
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getCmCrearEstructura")
	@Consumes("text/plain")
	public Response getCmCrearEstructura(@Context HttpServletRequest req, @HeaderParam("token") String token,
			String json, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmCrearEstructura cmCrearEstructura = gson.fromJson(json, CmCrearEstructura.class);
			CmCrearEstructuraService service = new CmCrearEstructuraService();
			List<CmCrearEstructura> d = service.getCmCrearEstructuraFiltro(cmCrearEstructura);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmCrearEstructura d = new CmCrearEstructura();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 9 ene. 2019
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setCmCrearEstructura")
	@Consumes("text/plain")
	public Response setCmCrearEstructura(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmCrearEstructura res = new CmCrearEstructura();
		try {
			CmCrearEstructura cmCrearEstructura = gson.fromJson(json, CmCrearEstructura.class);
			cmCrearEstructura.setAudFechaActualizacion(new Date());
			CmCrearEstructuraService service = new CmCrearEstructuraService();
			try {
				if (cmCrearEstructura.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmCrearEstructura.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmCrearEstructura.setAudFechaActualizacion(new Date());
					res = service.updateCmCrearEstructura(cmCrearEstructura);
				} else if (cmCrearEstructura.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmCrearEstructura(cmCrearEstructura);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 9 ene. 2019
	 * @param req
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getCmCrearGrupo")
	@Consumes("text/plain")
	public Response getCmCrearGrupo(@Context HttpServletRequest req, @HeaderParam("token") String token, String json,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmCrearGrupo cmCrearGrupo = gson.fromJson(json, CmCrearGrupo.class);
			CmCrearGrupoService service = new CmCrearGrupoService();
			List<CmCrearGrupo> d = service.getCmCrearGrupoFiltro(cmCrearGrupo);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmCrearGrupo d = new CmCrearGrupo();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 9 ene. 2019
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setCmCrearGrupo")
	@Consumes("text/plain")
	public Response setCmCrearGrupo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmCrearGrupo res = new CmCrearGrupo();
		try {
			CmCrearGrupo cmCrearGrupo = gson.fromJson(json, CmCrearGrupo.class);
			cmCrearGrupo.setAudFechaActualizacion(new Date());
			CmCrearGrupoService service = new CmCrearGrupoService();
			try {
				if (cmCrearGrupo.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmCrearGrupo.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmCrearGrupo.setAudFechaActualizacion(new Date());
					res = service.updateCmCrearGrupo(cmCrearGrupo);
				} else if (cmCrearGrupo.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmCrearGrupo(cmCrearGrupo);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 9 ene. 2019
	 * @param req
	 * @param token
	 * @param json
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getCmCrearPlanta")
	@Consumes("text/plain")
	public Response getCmCrearPlanta(@Context HttpServletRequest req, @HeaderParam("token") String token, String json,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		try {
			CmCrearPlanta cmCrearPlanta = gson.fromJson(json, CmCrearPlanta.class);
			CmCrearPlantaService service = new CmCrearPlantaService();
			List<CmCrearPlanta> d = service.getCmCrearPlantaFiltro(cmCrearPlanta);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			CmCrearPlanta d = new CmCrearPlanta();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 9 ene. 2019
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setCmCrearPlanta")
	@Consumes("text/plain")
	public Response setCmCrearPlanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CmCrearPlanta res = new CmCrearPlanta();
		try {
			CmCrearPlanta cmCrearPlanta = gson.fromJson(json, CmCrearPlanta.class);
			cmCrearPlanta.setAudFechaActualizacion(new Date());
			CmCrearPlantaService service = new CmCrearPlantaService();
			try {
				if (cmCrearPlanta.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| cmCrearPlanta.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					cmCrearPlanta.setAudFechaActualizacion(new Date());
					res = service.updateCmCrearPlanta(cmCrearPlanta);
				} else if (cmCrearPlanta.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertCmCrearPlanta(cmCrearPlanta);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * @author Maria Alejandra Colorado
	 * @fecha 08/01/2019
	 * @param req
	 * @param json
	 * @return
	 * @modulo Gestion de preguntas de opinion Retorna informacion de las
	 *         respuestas de una pregunta especifica
	 */
	@POST
	@Path("/getinformacionrespuestapregunta")
	@Consumes("text/plain")
	public Response getInformacionRespuestapregunta(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			List<ResultadoPreguntaExt> d = service.getInformacionRespuestasExportar(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.RESULTADO_PREGUNTA,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 9 ene. 2019
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getRolesByPerosnaEntidad")
	@Consumes("text/plain")
	public Response getRolesByPerosnaEntidad(@Context HttpServletRequest req, String json,
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
			RolExt rolExt = gson.fromJson(json, RolExt.class);
			RolService service = new RolService();
			List<RolExt> d = service.getRolesByPerosnaEntidad(rolExt);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author Maria Alejandra Colorado
	 * @fecha 08/01/2019
	 * @param req
	 * @param json
	 * @return Retorna el reten social de las personas que respondieron a una
	 *         pregunta especifica
	 */
	@POST
	@Path("/getrespuestasretensocial")
	@Consumes("text/plain")
	public Response getRespuestaRetenSocial(@Context HttpServletRequest req, String json,
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
			ResultadoPregunta user = gson.fromJson(json, ResultadoPregunta.class);
			ResultadoPreguntaService service = new ResultadoPreguntaService();
			List<ResultadoPreguntaExt> d = service.getRespuestasRetenSocial(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 11 ene. 2019
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getauditoriaconfiguracionFiltro")
	@Consumes("text/plain")
	public Response getauditoriaconfiguracionFiltro(@Context HttpServletRequest req, String json,
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
			List<AuditoriaConfiguracion> d = new ArrayList<AuditoriaConfiguracion>();
			AuditoriaConfiguracion audi = gson.fromJson(json, AuditoriaConfiguracion.class);
			AuditoriaConfiguracionService service = new AuditoriaConfiguracionService();
			if (audi.getNombreTabla() != null) {
				if (audi.getNombreTabla().isEmpty()) {
					audi.setNombreTabla(null);
				}
			}
			if (audi.getNombreFuncional() != null) {
				if (audi.getNombreFuncional().isEmpty()) {
					audi.setNombreFuncional(null);
				}
			}
			if (audi.getDescripcionTabla() != null) {
				if (audi.getDescripcionTabla().isEmpty()) {
					audi.setDescripcionTabla(null);
				}
			}
			d = service.getAuditoriaConfiguracionByFiltro(audi);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha Jan 14, 2019
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getUsuarioSession")
	@Consumes("text/plain")
	public Response getUsuarioSessionFuncion(@Context HttpServletRequest req, String json,
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
			List<UsuarioSession> d = new ArrayList<UsuarioSession>();
			UsuarioSession usuarioSession = gson.fromJson(json, UsuarioSession.class);
			UsuarioSessionService service = new UsuarioSessionService();
			d = service.getUsuarioSession(usuarioSession);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha Jan 14, 2019
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setUsuarioSession")
	@Consumes("text/plain")
	public Response setUsuarioSession(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
		gson = gsonBuilder.create();
		UsuarioSession res = new UsuarioSession();
		try {
			UsuarioSession usuarioSession = gson.fromJson(json, UsuarioSession.class);
			usuarioSession.setAudFechaActualizacion(new Date());
			UsuarioSessionService service = new UsuarioSessionService();
			try {
				if (usuarioSession.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| usuarioSession.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					usuarioSession.setAudFechaActualizacion(new Date());
					res = service.updateUsuarioSession(usuarioSession);
				} else if (usuarioSession.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					res = service.insertUsuarioSession(usuarioSession);
				} else {
					res.setError(true);
					res.setMensaje("No se envio la accion a realizar AudAccion()");
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * 
	 * @author: Jose Viscaya
	 * @fecha 11 feb. 2019
	 * @file ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getEntidadesPersonaRol")
	@Consumes("text/plain")
	public Response getEntidadesPersonaRol(@Context HttpServletRequest req, String json,
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
			List<UsuarioRolEntidadExt> d = new ArrayList<>();
			UsuarioRolEntidadExt usuarioSession = gson.fromJson(json, UsuarioRolEntidadExt.class);
			UsuarioRolEntidadService service = new UsuarioRolEntidadService();
			d = service.getEntidadesRolPersona(usuarioSession.getCodPersona());
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
	 * @Fecha :26 feb. 2019 ServicesSigepRestSis.java
	 */
	@POST
	@Path("/getRolesUsuarioOP")
	@Consumes("text/plain")
	public Response getRolesUsuarioOP(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			List<UsuarioExt> d = new ArrayList<>();
			Usuario usuario = gson.fromJson(json, Usuario.class);
			UsuarioService service = new UsuarioService();
			d = service.getUsuariosPorEntidad(usuario);
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
	 * @Fecha :26 feb. 2019 ServicesSigepRestSis.java
	 */
	@POST
	@Path("/updateUsuarioOP")
	@Consumes("text/plain")
	public Response updateUsuarioOP(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Usuario usuario = gson.fromJson(json, Usuario.class);
			UsuarioService service = new UsuarioService();
			usuario = service.updateUsuarioOP(usuario.getCodUsuario());
			String out = gson.toJson(usuario);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			e.getStackTrace();
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getrolhijosjefeth")
	@Consumes("text/plain")
	public Response getRolHijosJefeTH(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Rol ro = gson.fromJson(json, Rol.class);
			RolService service = new RolService();
			List<Rol> d = service.gettHijosRolJefeTH(ro);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getRolHijosJefethYContratos")
	@Consumes("text/plain")
	public Response getRolHijosJefethYContratos(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Rol ro = gson.fromJson(json, Rol.class);
			RolService service = new RolService();
			List<Rol> d = service.getHijosRolJefeTHyContratos(ro);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: 6 may. 2019, 17:41:14
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setProgramaacademico")
	@Consumes("text/plain")
	public Response setProgramaacademico(@Context HttpServletRequest req, String json,
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
			ProgramaAcademico programaAcademico = gson.fromJson(json, ProgramaAcademico.class);
			ProgramaAcademicoServicio service = new ProgramaAcademicoServicio();
			programaAcademico.setAudFechaActualizacion(new Date());
			if (programaAcademico.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				programaAcademico.setFlgActivo((short) 1);
				programaAcademico = service.insertProgramaAcademico(programaAcademico);
			} else if (programaAcademico.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
					|| programaAcademico.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				programaAcademico = service.updateProgramaAcademico(programaAcademico);
			} else {
				programaAcademico = new ProgramaAcademico();
				programaAcademico.setError(true);
				programaAcademico.setMensaje(UtilsConstantes.MSG_OBLIGATORIOS);
			}
			String out = gson.toJson(programaAcademico);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("{}").build();
		}

	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 7:08:21 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setmunicipios")
	@Consumes("text/plain")
	public Response setmunicipios(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Municipio municipio = gson.fromJson(json, Municipio.class);
			municipio.setAudFechaActualizacion(new Date());
			MunicipioService service = new MunicipioService();
			if (municipio.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				municipio.setFlgActivo((short) 1);
				municipio = service.insertMunicipio(municipio);
			} else if (municipio.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
					|| municipio.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				municipio = service.updateMunicipio(municipio);
			}
			String out = gson.toJson(municipio);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 7:09:21 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getmunicipiosFiltro")
	@Consumes("text/plain")
	public Response getmunicipiosFiltro(@Context HttpServletRequest req, String json,
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
			Municipio mun = gson.fromJson(json, Municipio.class);
			MunicipioService service = new MunicipioService();
			List<Municipio> d = service.getMunicipioByFiltro(mun);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.MUNICIPIO,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 7:17:30 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setInstitucionEducativa")
	@Consumes("text/plain")
	public Response setInstitucionEducativa(@Context HttpServletRequest req, String json,
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
			InstitucionEducativa institucionEducativa = gson.fromJson(json, InstitucionEducativa.class);
			InstitucionEducativaService service = new InstitucionEducativaService();
			if (institucionEducativa.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				institucionEducativa = service.insertInstitucionEducativa(institucionEducativa);
			} else if (institucionEducativa.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
					|| institucionEducativa.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				institucionEducativa = service.updateInstitucionEducativa(institucionEducativa);
			}
			String out = gson.toJson(institucionEducativa);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			InstitucionEducativa d = new InstitucionEducativa();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 7:22:51 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getInstitucionEducativaFiltro")
	@Consumes("text/plain")
	public Response getInstitucionEducativaFiltro(@Context HttpServletRequest req, String json,
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
			InstitucionEducativa institucionEducativa = gson.fromJson(json, InstitucionEducativa.class);
			InstitucionEducativaService service = new InstitucionEducativaService();
			List<InstitucionEducativaExt> d = service.getInstitucionEducativaByFiltro(institucionEducativa);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.INSTITUCION_EDUCATIVA,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<InstitucionEducativaExt> asoc = new ArrayList<>();
			InstitucionEducativaExt d = new InstitucionEducativaExt();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			asoc.add(d);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(asoc)).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 7:36:52 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setpais")
	@Consumes("text/plain")
	public Response setpais(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		PaisService service = new PaisService();
		Pais res = new Pais();
		try {
			Pais pais = gson.fromJson(json, Pais.class);
			try {
				if (pais.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					pais.setAudFechaActualizacion(new Date());
					res = service.insertPais(pais);
				} else if (pais.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| pais.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					res = service.updatePais(pais);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 7:45:18 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getpaisFiltro")
	@Consumes("text/plain")
	public Response getpaisFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
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
			Pais pais = gson.fromJson(json, Pais.class);
			PaisService service = new PaisService();
			List<Pais> d = service.getPaisByFiltro(pais);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.PAIS, auditoriaSeguridad.getCodUsuario(),
					auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			out = out.replace("src", "");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 7:56:36 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getprogramaacademicoFiltro")
	@Consumes("text/plain")
	public Response getprogramaacademicoFiltro(@Context HttpServletRequest req, String json,
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
			ProgramaAcademico programaAcademico = gson.fromJson(json, ProgramaAcademico.class);
			ProgramaAcademicoServicio service = new ProgramaAcademicoServicio();
			List<ProgramaAcademicoExt> d = service.getProgramaAcademicoByFiltro(programaAcademico);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.PROGRAMA_ACADEMICO,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("[]").build();
		}
	}

	
	/**
	 * 
	 * @Author: Maria Alejandra
	 * @Date: May 7, 2022, 7:56:36 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getProgramaacademicoDuplicado")
	@Consumes("text/plain")
	public Response getProgramaacademicoDuplicado(@Context HttpServletRequest req, String json,
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
			ProgramaAcademico programaAcademico = gson.fromJson(json, ProgramaAcademico.class);
			ProgramaAcademicoServicio service = new ProgramaAcademicoServicio();
			List<ProgramaAcademico> d = service.getProgramaAcademicoDuplicado(programaAcademico);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.PROGRAMA_ACADEMICO,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("[]").build();
		}
	}
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 8:00:52 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setidioma")
	@Consumes("text/plain")
	public Response setidioma(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Idioma idioma = gson.fromJson(json, Idioma.class);
			IdiomaService service = new IdiomaService();
			idioma.setAudFechaActualizacion(new Date());
			if (idioma.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				idioma = service.insertIdioma(idioma);
			} else if (idioma.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
					|| idioma.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				idioma = service.updateIdioma(idioma);
			}
			String out = gson.toJson(idioma);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			Idioma d = new Idioma();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 8:06:48 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getidiomaFiltro")
	@Consumes("text/plain")
	public Response getidiomasFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
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
			Idioma idioma = gson.fromJson(json, Idioma.class);
			IdiomaService service = new IdiomaService();
			List<Idioma> d = service.getIdiomaByfiltro(idioma);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.VINCULACION,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<Idioma> asoc = new ArrayList<>();
			Idioma d = new Idioma();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			asoc.add(d);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(asoc)).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 8:21:08 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setDepartamento")
	@Consumes("text/plain")
	public Response setDepartamento(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DepartamentoService service = new DepartamentoService();
		Departamento res = new Departamento();
		try {
			Departamento departamento = gson.fromJson(json, Departamento.class);
			try {
				if (departamento.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
					departamento.setAudFechaActualizacion(new Date());
					res = service.insertDepartamento(departamento);
				} else if (departamento.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
						|| departamento.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
					res = service.updateDepartamento(departamento);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 8:50:48 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getDepartamentoFiltro")
	@Consumes("text/plain")
	public Response getDepartamentoFiltro(@Context HttpServletRequest req, String json,
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
			Departamento dep = gson.fromJson(json, Departamento.class);
			DepartamentoService service = new DepartamentoService();
			List<Departamento> d = service.getDepartamentoByFiltro(dep);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.DEPARTAMENTO,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 3:40:26 PM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setFestivo")
	@Consumes("text/plain")
	public Response setFestivo(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Festivo festivo = gson.fromJson(json, Festivo.class);
			FestivoService service = new FestivoService();
			festivo.setAudFechaActualizacion(new Date());
			if (festivo.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				festivo = service.insertFestivo(festivo);
			} else if (festivo.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
					|| festivo.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				festivo = service.updateFestivo(festivo);
			} else if (festivo.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				festivo = service.updateFestivo(festivo);
			}
			String out = gson.toJson(festivo);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 3:40:32 PM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getFestivoFiltro")
	@Consumes("text/plain")
	public Response getFestivoFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
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
			Festivo festivo = gson.fromJson(json, Festivo.class);
			FestivoService service = new FestivoService();
			List<Festivo> d = service.getFestivoByFiltro(festivo);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.FESTIVO, auditoriaSeguridad.getCodUsuario(),
					auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 3:45:49 PM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getRangoEdadId")
	@Consumes("text/plain")
	public Response getRangoEdadId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
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
			RangoEdad festivo = gson.fromJson(json, RangoEdad.class);
			RangoEdadService service = new RangoEdadService();
			RangoEdad d = service.getRangoEdadByID(festivo);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.RANGO_EDAD,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 4:06:10 PM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/setRangoEdad")
	@Consumes("text/plain")
	public Response setRangoEdad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			RangoEdad festivo = gson.fromJson(json, RangoEdad.class);
			RangoEdadService service = new RangoEdadService();
			festivo.setAudFechaActualizacion(new Date());
			if (festivo.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
				festivo = service.insertRangoEdad(festivo);
			} else if (festivo.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
					|| festivo.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				festivo = service.updateRangoEdad(festivo);
			}
			String out = gson.toJson(festivo);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 4:06:14 PM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getRangoEdadFiltro")
	@Consumes("text/plain")
	public Response getRangoEdadFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			RangoEdad rangoEdad = gson.fromJson(json, RangoEdad.class);
			RangoEdadService service = new RangoEdadService();
			List<RangoEdad> d = service.getRangoEdadByFiltro(rangoEdad);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 7, 2019, 4:06:29 PM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getFestivoId")
	@Consumes("text/plain")
	public Response getFestivoId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Festivo festivo = gson.fromJson(json, Festivo.class);
			FestivoService service = new FestivoService();
			Festivo d = service.getFestivoByID(festivo);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/deletepais")
	@Consumes("text/plain")
	public Response deletepais(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Pais user = gson.fromJson(json, Pais.class);
			PaisService service = new PaisService();
			String d = service.deletePais(user.getCodPais());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @fecha 20 Mayo 2019
	 * @param req
	 * @param json
	 * @param token
	 */
	@POST
	@Path("/getpaisid")
	@Consumes("text/plain")
	public Response getPaisId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd/MM/yyyy");
		gson = gsonBuilder.create();
		Pais err = new Pais();
		try {
			Pais pais = gson.fromJson(json, Pais.class);
			PaisService service = new PaisService();
			err = service.getPaisbyId(pais.getCodPais());
			return Response.status(201).entity(gson.toJson(err)).build();
		} catch (JsonParseException e) {
			Pais er = new Pais();
			er.setError(true);
			er.setMensaje(UtilsConstantes.MSG_EXEPCION);
			er.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(er)).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @fecha 20 Mayo 2019
	 * @param req
	 * @param json
	 * @param token
	 */
	@POST
	@Path("/getdepartamentoid")
	@Consumes("text/plain")
	public Response getDepartamentoId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd/MM/yyyy");
		gson = gsonBuilder.create();
		Departamento err = new Departamento();
		try {
			Departamento departamento = gson.fromJson(json, Departamento.class);
			DepartamentoService service = new DepartamentoService();
			err = service.getDepartamentoById(departamento.getCodDepartamento());
			return Response.status(201).entity(gson.toJson(err)).build();
		} catch (JsonParseException e) {
			Departamento er = new Departamento();
			er.setError(true);
			er.setMensaje(UtilsConstantes.MSG_EXEPCION);
			er.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(er)).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 *
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getrolpornombre")
	@Consumes("text/plain")
	public Response getRolPorNombre(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Rol ro = gson.fromJson(json, Rol.class);
			RolService service = new RolService();
			List<Rol> d = service.getRolPorNombre(ro);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @fecha 20 Mayo 2019
	 * @param req
	 * @param json
	 * @param token
	 */
	@POST
	@Path("/getinstitucioneducativaid")
	@Consumes("text/plain")
	public Response getInstitucionEducativaId(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd/MM/yyyy");
		gson = gsonBuilder.create();
		InstitucionEducativa err = new InstitucionEducativa();
		try {
			InstitucionEducativa institucionEducativa = gson.fromJson(json, InstitucionEducativa.class);
			InstitucionEducativaService service = new InstitucionEducativaService();
			err = service.institucionEducativaById(institucionEducativa.getCodInstitucionEducativa());
			return Response.status(201).entity(gson.toJson(err)).build();
		} catch (JsonParseException e) {
			InstitucionEducativa er = new InstitucionEducativa();
			er.setError(true);
			er.setMensaje(UtilsConstantes.MSG_EXEPCION);
			er.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(er)).build();
		}
	}

	/**
	 * Servicio que consulta por cualquier campo de la tabla AUDITORIA
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getauditoriabyfilter")
	@Consumes("text/plain")
	public Response getAuditoriaByFilter(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd/MM/yyyy");
		gson = gsonBuilder.create();
		try {
			AuditoriaExt user = gson.fromJson(json, AuditoriaExt.class);
			List<AuditoriaExt> d = new ArrayList<AuditoriaExt>();
			AuditoriaService service = new AuditoriaService();
			d = service.getAuditoriaByFilter(user);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getpreguntapornombre")
	@Consumes("text/plain")
	public Response getPreguntaPorNombre(@Context HttpServletRequest req, String json,
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
			PreguntaOpinion po = gson.fromJson(json, PreguntaOpinion.class);
			PreguntaOpinionService service = new PreguntaOpinionService();
			List<PreguntaOpinion> d = service.getPreguntaPorNombre(po);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getpaisduplicado")
	@Consumes("text/plain")
	public Response getPaisDuplicado(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Pais pais = gson.fromJson(json, Pais.class);
			PaisService service = new PaisService();
			List<Pais> d = service.getPaisDuplicado(pais);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getdepartamentoduplicado")
	@Consumes("text/plain")
	public Response getDepartamentoDuplicado(@Context HttpServletRequest req, String json,
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
			Departamento departamento = gson.fromJson(json, Departamento.class);
			DepartamentoService service = new DepartamentoService();
			List<Departamento> d = service.getDepartamentoDuplicado(departamento);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getidiomaduplicado")
	@Consumes("text/plain")
	public Response getIdiomaDuplicado(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Idioma idioma = gson.fromJson(json, Idioma.class);
			IdiomaService service = new IdiomaService();
			List<Idioma> d = service.getIdiomaDuplicado(idioma);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getmunicipioduplicado")
	@Consumes("text/plain")
	public Response getMunicipioDuplicado(@Context HttpServletRequest req, String json,
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
			Municipio municipio = gson.fromJson(json, Municipio.class);
			MunicipioService service = new MunicipioService();
			List<Municipio> d = service.getMunicipioDuplicado(municipio);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getdenominacionduplicado")
	@Consumes("text/plain")
	public Response getDenominacionDuplicado(@Context HttpServletRequest req, String json,
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
			Denominacion denominacion = gson.fromJson(json, Denominacion.class);
			DenominacionService service = new DenominacionService();
			List<Denominacion> d = service.getDenominacionDuplicado(denominacion);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getsituacionduplicado")
	@Consumes("text/plain")
	public Response getSituacionDuplicado(@Context HttpServletRequest req, String json,
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
			SituacionAdministrativa situacion = gson.fromJson(json, SituacionAdministrativa.class);
			SituacionAdministrativaService service = new SituacionAdministrativaService();
			List<SituacionAdministrativa> d = service.getNombreSituacionUnq(situacion);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}

	/**
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getrangoedadduplicado")
	@Consumes("text/plain")
	public Response getRangoEdadDuplicado(@Context HttpServletRequest req, String json,
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
			RangoEdad rangoEdad = gson.fromJson(json, RangoEdad.class);
			RangoEdadService service = new RangoEdadService();
			List<RangoEdad> d = service.getRangoEdadDuplicado(rangoEdad);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/getprogramaacademicoporcodigoprograma")
	@Consumes("text/plain")
	public Response getProgramaAcademicoPorCodigoPrograma(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		json = new String(Base64.decode(json.getBytes()));
		try {
			ProgramaAcademico pr = gson.fromJson(json, ProgramaAcademico.class);
			ProgramaAcademicoServicio service = new ProgramaAcademicoServicio();
			ProgramaAcademico d = service.getProgramaAcademicoByCodigoPrograma(pr.getCodProgramaAcademico().intValue());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("{}").build();
		}
	}

	/**
	 * @Author: Maria Alejandra
	 * @Date: Oct 21, 2019, 7:22:51 AM
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getinstitucioneducativabypais")
	@Consumes("text/plain")
	public Response getInstitucionEducativaByPais(@Context HttpServletRequest req, String json,
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
			InstitucionEducativa institucionEducativa = gson.fromJson(json, InstitucionEducativa.class);
			InstitucionEducativaService service = new InstitucionEducativaService();
			List<InstitucionEducativa> d = service.getInstitucionByPais(institucionEducativa);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.INSTITUCION_EDUCATIVA,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			List<InstitucionEducativa> asoc = new ArrayList<>();
			InstitucionEducativa d = new InstitucionEducativa();
			d.setError(true);
			d.setMensaje("Error De Json Format");
			d.setMensajeTecnico(e.getMessage());
			asoc.add(d);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(asoc)).build();
		}
	}

	/**
	 * Servicio que retorna una lista de cargos pertenecientes al maestro cargo_hoja_vida, dependiendo del filtro enviado
	 * @Author: Maria Alejandra
	 * @Date: Abril 29, 2020
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getCargoHVFiltro")
	@Consumes("text/plain")
	public Response getCargoHVFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
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
			CargoHojaVidaExt cargoHV = gson.fromJson(json, CargoHojaVidaExt.class);
			CargoHojaVidaService service = new CargoHojaVidaService();
			List<CargoHojaVidaExt> d = service.getCargoHVFiltro(cargoHV);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.CARGO_HOJA_VIDA, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			out = out.replace("src", "");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("[]").build();
		}
	}

	/**
	 * Servicio que almacena un nuevo registro o modifica uno existente en el maestro cargo_hoja_vida
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return CargoHojaVida
	 */
	@POST
	@Path("/setCargoHojaVida")
	@Consumes("text/plain")
	public Response setCargoHojaVida(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CargoHojaVidaService service = new CargoHojaVidaService();
		CargoHojaVida res = new CargoHojaVida();
		try {
			CargoHojaVida cargoHojaVida = gson.fromJson(json, CargoHojaVida.class);
			try {
				if(cargoHojaVida.getCodCargoHojaVida() == null) {
					res = service.insertCargoHV(cargoHojaVida);
				}else {
					res = service.updateCargoHojaVida(cargoHojaVida);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	/**
	 * Servicio que verifica si el nombre del cargo ya existe en el maestro cargo_hoja_vida
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getCargoHVDuplicado")
	@Consumes("text/plain")
	public Response getCargoHVDuplicado(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CargoHojaVida cargoHV = gson.fromJson(json, CargoHojaVida.class);
			CargoHojaVidaService service = new CargoHojaVidaService();
			List<CargoHojaVida> d = service.getCargoHVDuplicado(cargoHV);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Servicio que elimina un cargo del maestro cargo_hoja_vida de manera fisica.
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/deleteCargoHojaVidaFisico")
	@Consumes("text/plain")
	public Response deleteCargoHojaVidaFisico(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			CargoHojaVida object = gson.fromJson(json, CargoHojaVida.class);
			CargoHojaVidaService service = new CargoHojaVidaService();
			String d = service.deleteCargoHojaVidaFisico(object.getCodCargoHojaVida());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Servicio que almacena un nuevo registro o modifica uno existente en el maestro dependencia_hoja_vida
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return DependenciaHojaVida
	 */
	@POST
	@Path("/setDependenciaHojaVida")
	@Consumes("text/plain")
	public Response setDependenciaHojaVida(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DependenciaHojaVidaService service = new DependenciaHojaVidaService();
		DependenciaHojaVida res = new DependenciaHojaVida();
		try {
			DependenciaHojaVida dependenciaHV = gson.fromJson(json, DependenciaHojaVida.class);
			try {
				if(dependenciaHV.getCodDependenciaHojaVida() == null) {
					res = service.insertDependenciaHV(dependenciaHV);
				}else {
					res = service.updateDependenciaHojaVida(dependenciaHV);
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
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	/**
	 * Servicio que verifica el nombre de la dependencia ya existe en el maestro dependencia_hoja_vida
	 * @author: Maria Alejandra C
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getDependenciaHVDuplicado")
	@Consumes("text/plain")
	public Response getDependenciaHVDuplicado(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			DependenciaHojaVida cargoHV = gson.fromJson(json, DependenciaHojaVida.class);
			DependenciaHojaVidaService service = new DependenciaHojaVidaService();
			List<DependenciaHojaVida> d = service.getDependenciaHVDuplicado(cargoHV);
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Servicio que elimina una dependencia del maestro dependencia_hoja_vida de manera fisica.
	 * @author: Maria Alejandra
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/deleteDependenciaHojaVidaFisico")
	@Consumes("text/plain")
	public Response deleteDependenciaHojaVidaFisico(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			DependenciaHojaVida object = gson.fromJson(json, DependenciaHojaVida.class);
			DependenciaHojaVidaService service = new DependenciaHojaVidaService();
			String d = service.deleteDependenciaHojaVidaFisico(object.getCodDependenciaHojaVida());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Servicio que retorna una lista de dependencias pertenecientes al maestro dependencia_hoja_vida, dependiendo del filtro enviado
	 * @Author: Maria Alejandra
	 * @Date: Abril 29, 2020
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getDependenciaHVFiltro")
	@Consumes("text/plain")
	public Response getDependenciaHVFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
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
			DependenciaHojaVidaExt dependenciaHV = gson.fromJson(json, DependenciaHojaVidaExt.class);
			DependenciaHojaVidaService service = new DependenciaHojaVidaService();
			List<DependenciaHojaVidaExt> d = service.getDependenciaVFiltro(dependenciaHV);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.DEPENDENCIA_HOJA_VIDA, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			out = out.replace("src", "");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * Servicio que almacena un nuevo registro si este no existe en la tabla maestro dependencia_hoja_vida desde
	 * el componente de estructura.
	 * Este servicio valida que la dependencia enviada no exista en el maestro, y de ser cierto almacena un nuevo registro.
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return DependenciaHojaVida
	 */
	@POST
	@Path("/setDependenciaHojaVidaDesdeEstructura")
	@Consumes("text/plain")
	public Response setDependenciaHojaVidaDesdeEstructura(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DependenciaHojaVidaService service = new DependenciaHojaVidaService();
		DependenciaHojaVida res = new DependenciaHojaVida();
		try {
			DependenciaHojaVida dependenciaHV = gson.fromJson(json, DependenciaHojaVida.class);
			try {
				res = service.insertDependenciaHVDesdeEstructura(dependenciaHV);
			} catch (NullPointerException e) {
				res.setError(true);
				res.setMensaje(UtilsConstantes.MSG_OBLIGATORIOS);
			}
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
	
	
	/**
	 * @author: Maria Alejandra C
	 * @fecha 15 Mayo 2020
	 * @param req
	 * @param json
	 * @param token
	 */
	@POST
	@Path("/getDependenciaHVById")
	@Consumes("text/plain")
	public Response getDependenciaHVById(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DependenciaHojaVida err = new DependenciaHojaVida();
		try {
			DependenciaHojaVida dependencia = gson.fromJson(json, DependenciaHojaVida.class);
			DependenciaHojaVidaService service = new DependenciaHojaVidaService();
			err = service.dependenciaHVById(dependencia);
			return Response.status(201).entity(gson.toJson(err)).build();
		} catch (JsonParseException e) {
			DependenciaHojaVida er = new DependenciaHojaVida();
			er.setError(true);
			er.setMensaje(UtilsConstantes.MSG_EXEPCION);
			er.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(er)).build();
		}
	}
	
	/**
	 * @author: Maria Alejandra C
	 * @fecha 15 Mayo 2020
	 * @param req
	 * @param json
	 * @param token
	 */
	@POST
	@Path("/getCargoHVById")
	@Consumes("text/plain")
	public Response getCargoHVById(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		CargoHojaVida err = new CargoHojaVida();
		try {
			CargoHojaVida cargo = gson.fromJson(json, CargoHojaVida.class);
			CargoHojaVidaService service = new CargoHojaVidaService();
			err = service.cargoHVById(cargo);
			return Response.status(201).entity(gson.toJson(err)).build();
		} catch (JsonParseException e) {
			CargoHojaVida er = new CargoHojaVida();
			er.setError(true);
			er.setMensaje(UtilsConstantes.MSG_EXEPCION);
			er.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(er)).build();
		}
	}
	/**
	 * Servicio que retorna una lista de datos pertenecientes a la vista v_recurso_accion, dependiendo del filtro enviado
	 * @Author: Maria Alejandra
	 * @Date: 18/09/2020
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getVistaRecursoAccionFiltro")
	@Consumes("text/plain")
	public Response getVistaRecursoAccionFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {

		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			RecursoAccion recursoAccion = gson.fromJson(json, RecursoAccion.class);
			RecursoAccionService service = new RecursoAccionService();
			List<RecursoAccion> d = service.getVistaRecursoAccionFiltro(recursoAccion);
			String out = gson.toJson(d);
			out = out.replace("src", "");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("[]").build();
		}
	}
	
	/**
	 * Servicio que retorna una lista de datos pertenecientes a la vista v_recurso_accion, dependiendo del filtro enviado
	 * @Author: Maria Alejandra
	 * @Date: 18/09/2020
	 * @File: ServicesSigepRestSis.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path("/getVistaRecursoUsuarioAccionFiltro")
	@Consumes("text/plain")
	public Response getVistaRecursoUsuarioAccionFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {

		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			RecursoAccionExt recursoAccion = gson.fromJson(json, RecursoAccionExt.class);
			RecursoAccionService service = new RecursoAccionService();
			List<RecursoAccion> d = service.getVistaRecursoUsuarioAccionFiltro(recursoAccion);
			String out = gson.toJson(d);
			out = out.replace("src", "");
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity("[]").build();
		}
	}	
	
	
	/**
	 * @author: Maria Alejandra C
	 * @fecha 27 Septiembre 2022
	 * @param req
	 * @param json
	 * @param token
	 */
	@POST
	@Path("/setEliminarUsuarioEntidad")
	@Consumes("text/plain")
	public Response setEliminarUsuarioEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token,
			@HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			UsuarioRolEntidadExt cargo = gson.fromJson(json, UsuarioRolEntidadExt.class);
			UsuarioRolEntidadService service = new UsuarioRolEntidadService();
			UsuarioRolEntidadExt err = service.eliminarUsuarioEntidad(cargo);
			return Response.status(201).entity(gson.toJson(err)).build();
		} catch (JsonParseException e) {
			UsuarioRolEntidadExt er = new UsuarioRolEntidadExt();
			er.setError(true);
			er.setMensaje(UtilsConstantes.MSG_EXEPCION);
			er.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(er)).build();
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
	@Path("/getParametricaByname")
	@Consumes("text/plain")
	public Response getParametricaByname(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Parametrica parametrica = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			try {
				Parametrica d = service.getParametricaByname(parametrica);
				if (d !=null) {
					return Response.status(201).entity(gson.toJson(d)).build();
				} else {
					Parametrica p = new Parametrica();
					p.setError(true);
					p.setMensaje("No se encontro registro");
					return Response.status(201).entity(gson.toJson(p)).build();
				}
			} catch (NullPointerException e) {
				Parametrica p = new Parametrica();
				p.setError(true);
				p.setMensaje("nombreParametro no puede ser Nulo");
				p.setMensajeTecnico(e.getMessage());
				return Response.status(201).entity(gson.toJson(p)).build();
			}

		} catch (JsonParseException e) {
			Parametrica p = new Parametrica();
			p.setError(true);
			p.setMensaje("Formato Json No valido");
			p.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
			return Response.status(201).entity(gson.toJson(p)).build();
		}
	}
	
	/**
	 * @author jgonzalez
	 * @return List<MunicipioExt> retorna una lista con el nombre de los municipios y su respectivo departamento
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * 
	 */
	
	@POST
	@Path("/getMunByDep")
	@Consumes("text/plain")
	
	public Response getMunByDep(@Context HttpServletRequest req, String json,
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
			Municipio municipio = gson.fromJson(json, Municipio.class);
			MunicipioService service = new MunicipioService();
			List<MunicipioExt> municipios = service.getMunicipioPorDepartamento(municipio);
			return Response.status(201).entity(gson.toJson(municipios)).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
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
	@Path("/parametricaActivaXidPadre")
	@Consumes("text/plain")
	public Response parametricaActivaXidPadre(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout,
			@HeaderParam("auditJson") String auditJson) {
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
			Parametrica user = gson.fromJson(json, Parametrica.class);
			ParametricaService service = new ParametricaService();
			List<Parametrica> d = service.getPrametricaActivaByPadreiId(user);
			AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.PARAMETRICA,
					auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
			String out = gson.toJson(d);
			return Response.status(201).entity(out).build();
		} catch (JsonParseException e) {
			return Response.status(201).entity("{}").build();
		}
	}
	
}
