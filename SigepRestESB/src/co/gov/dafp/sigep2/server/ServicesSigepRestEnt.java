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
import com.sun.jersey.core.util.Base64;

import co.gov.dafp.sigep2.bean.AsignacionSalarial;
import co.gov.dafp.sigep2.bean.Autonomia;
import co.gov.dafp.sigep2.bean.Denominacion;
import co.gov.dafp.sigep2.bean.DependenciaEntidad;
import co.gov.dafp.sigep2.bean.Entidad;
import co.gov.dafp.sigep2.bean.EntidadEscision;
import co.gov.dafp.sigep2.bean.EntidadFusion;
import co.gov.dafp.sigep2.bean.EntidadHistoricoEstados;
import co.gov.dafp.sigep2.bean.EntidadPoliticaPublica;
import co.gov.dafp.sigep2.bean.EntidadSistemaRegimen;
import co.gov.dafp.sigep2.bean.EscalaSalarial;
import co.gov.dafp.sigep2.bean.Estructura;
import co.gov.dafp.sigep2.bean.GrupoDependencia;
import co.gov.dafp.sigep2.bean.IncrementoSalarial;
import co.gov.dafp.sigep2.bean.Nomenclatura;
import co.gov.dafp.sigep2.bean.NomenclaturaDenominacion;
import co.gov.dafp.sigep2.bean.NomenclaturaEntidad;
import co.gov.dafp.sigep2.bean.Norma;
import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.PepCuestionario;
import co.gov.dafp.sigep2.bean.PepPreguntasCuestionario;
import co.gov.dafp.sigep2.bean.PepPreguntasCuestionarioDetalle;
import co.gov.dafp.sigep2.bean.PepRespuestaCuestionario;
import co.gov.dafp.sigep2.bean.PepRespuestaCuestionarioDetalle;
import co.gov.dafp.sigep2.bean.PepRespuestasPreguntaCuestionario;
import co.gov.dafp.sigep2.bean.PlantaPersonaUtlUan;
import co.gov.dafp.sigep2.bean.PoliticaPublica;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.UsuarioRolDependencia;
import co.gov.dafp.sigep2.bean.UsuarioRolEntidad;
import co.gov.dafp.sigep2.bean.ext.AsignacionSalarialExt;
import co.gov.dafp.sigep2.bean.ext.AutonomiaExt;
import co.gov.dafp.sigep2.bean.ext.DenominacionExt;
import co.gov.dafp.sigep2.bean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.bean.ext.EntidadExt;
import co.gov.dafp.sigep2.bean.ext.EntidadHistoricoEstadosExt;
import co.gov.dafp.sigep2.bean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.bean.ext.EntidadPoliticaPublicaExt;
import co.gov.dafp.sigep2.bean.ext.EntidadSistemaRegimenExt;
import co.gov.dafp.sigep2.bean.ext.GrupoDependenciaExt;
import co.gov.dafp.sigep2.bean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.bean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.bean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.bean.ext.PepCuestionarioExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.PlantaPersonaUtlUanExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioExt;
import co.gov.dafp.sigep2.constante.AuditoriaConstantes;
import co.gov.dafp.sigep2.services.AsignacionSalarialService;
import co.gov.dafp.sigep2.services.AuditoriaService;
import co.gov.dafp.sigep2.services.AutonomiaService;
import co.gov.dafp.sigep2.services.DenominacionService;
import co.gov.dafp.sigep2.services.DependenciaEntidadService;
import co.gov.dafp.sigep2.services.EntidadEscisionService;
import co.gov.dafp.sigep2.services.EntidadFusionService;
import co.gov.dafp.sigep2.services.EntidadHistoricoEstadosService;
import co.gov.dafp.sigep2.services.EntidadPlantaDetalleService;
import co.gov.dafp.sigep2.services.EntidadPoliticaPublicaService;
import co.gov.dafp.sigep2.services.EntidadService;
import co.gov.dafp.sigep2.services.EntidadSistemaRegimenService;
import co.gov.dafp.sigep2.services.EscalaSalarialService;
import co.gov.dafp.sigep2.services.EstructuraService;
import co.gov.dafp.sigep2.services.GrupoDependenciaService;
import co.gov.dafp.sigep2.services.HojaVidaService;
import co.gov.dafp.sigep2.services.IncrementoSalarialService;
import co.gov.dafp.sigep2.services.NomenclaturaDenominacionService;
import co.gov.dafp.sigep2.services.NomenclaturaEntidadService;
import co.gov.dafp.sigep2.services.NomenclaturaService;
import co.gov.dafp.sigep2.services.NormaService;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.PepCuestionarioService;
import co.gov.dafp.sigep2.services.PepPreguntasCuestionarioDetalleService;
import co.gov.dafp.sigep2.services.PepPreguntasCuestionarioService;
import co.gov.dafp.sigep2.services.PepRespuestaCuestionarioDetalleService;
import co.gov.dafp.sigep2.services.PepRespuestaCuestionarioService;
import co.gov.dafp.sigep2.services.PepRespuestasPreguntaCuestionarioService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.services.PlantaPersonaUtlUanService;
import co.gov.dafp.sigep2.services.PoliticaPublicaService;
import co.gov.dafp.sigep2.services.UsuarioRolDependenciaService;
import co.gov.dafp.sigep2.services.UsuarioRolEntidadService;
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
@Path("/sigepent")
public class ServicesSigepRestEnt implements Serializable {
	
    private static final long serialVersionUID = -6503436010566070869L;
    public Gson gson = new Gson();
    
    public ServicesSigepRestEnt() {
    	LoggerSigep.getInstance().configureAppender();
	}

   /**
    * 
    * @author: Jose Viscaya
    * @param msg
    * @return
    */
    @GET()
    @Path("testAlive/{msg}")
    @Produces("text/plain")
    public Response cliente(@PathParam("msg") String msg) {
        return Response.status(201).entity("Hello: Services Context sigepent.. " + msg).build();
    }

    /**
     * @author: Jose Viscaya
     *
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones
     * por Codigo de Declaracion.
     */
    
    @POST
    @Path("/setentidad")
    @Consumes("text/plain")
    public Response setEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        EntidadService service = new EntidadService();
        Entidad res = new Entidad();
        try {
            EntidadExt entidad = gson.fromJson(json, EntidadExt.class);
            try {
                if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		||entidad.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                    entidad.setAudFechaActualizacion(new Date());
                    res = service.updateEntidadSelective(entidad);
                } else if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertEntidadSelective(entidad);
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
     * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones
     * por Codigo de Declaracion.
     */
    @POST
    @Path("/getentidadfiltro")
    @Consumes("text/plain")
    public Response getEntidadFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
        gson = gsonBuilder.create();
        try {
        	AuditoriaSeguridad auditoriaSeguridad = gson.fromJson(auditJson, AuditoriaSeguridad.class);
            Entidad user = gson.fromJson(json, Entidad.class);
            EntidadService service = new EntidadService();
            List<Entidad> d = service.getFiltro(user);
            AuditoriaService.insertarAuditoriaConsulta(AuditoriaConstantes.ENTIDAD, auditoriaSeguridad.getCodUsuario(), auditoriaSeguridad.getCodUsuarioRol());
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
    @Path("/getentidaddepmun")
    @Consumes("text/plain")
    public Response getEntidadDepMun(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
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
    		Entidad d = service.getEntidadDepMun(user);
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
     * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones
     * por Codigo de Declaracion.
     */
    @GET()
    @Path("getentidadnit/{msg}")
    @Produces("text/plain")
    public Response getEntidadNit(@PathParam("msg") String msg, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
        try {
            String json = "{\"nit\":\"" + msg + "\"}";
            Entidad user = gson.fromJson(json, Entidad.class);
            EntidadService service = new EntidadService();
            boolean d = service.getEntidadNitExist(user.getNit());
            return Response.status(201).entity(d).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity(false).build();
        }
    }
    /**
     * 
     * @author: Jose Viscaya
     * @param nombre
     * @param depto
     * @param mun
     * @return
     */
    @GET()
    @Path("getentidadnombre/{nombre}/{depto}/{mun}")
    @Produces("text/plain")
    public Response getEntidadNombreExist(@PathParam("nombre") String nombre, @PathParam("depto") String depto, @PathParam("mun") String mun, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	try {
            String json = "{\"nombreEntidad\":\"" + nombre + "\",\"codDepartamento\":\"" + depto + "\",\"codMunicipio\":\"" + mun + "\"}";
            Entidad user = gson.fromJson(json, Entidad.class);
            EntidadService service = new EntidadService();
            boolean d = service.getEntidadNombreExist(user);
            return Response.status(201).entity(d).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity(false).build();
        }
    }

    /**
     * @author: Jose Viscaya
     *
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una lista de entidades por contenido del
     * nombre
     */
    @POST
    @Path("/getentidadlike")
    @Consumes("text/plain")
    public Response getEntidadLike(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
            Entidad entidad = gson.fromJson(json, Entidad.class);
            EntidadService service = new EntidadService();
            List<Entidad> d = service.getFiltroLike(entidad);
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
     * @return Este Servicio inserta un registro en la tabla
     * EntidadSistemaRegimen
     */
    @POST
    @Path("/setentidadsistemaregimen")
    @Consumes("text/plain")
    public Response setEntidadSistemaRegimen(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        EntidadSistemaRegimenService service = new EntidadSistemaRegimenService();
        EntidadSistemaRegimen res = new EntidadSistemaRegimen();
        try {
            EntidadSistemaRegimen entidadSistemaRegimen = gson.fromJson(json, EntidadSistemaRegimen.class);
            try {
                if (entidadSistemaRegimen.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) ||
                		entidadSistemaRegimen.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                    entidadSistemaRegimen.setAudFechaActualizacion(new Date());
                    res = service.updateEntidadSelective(entidadSistemaRegimen);
                } else if (entidadSistemaRegimen.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertEntidadSelective(entidadSistemaRegimen);
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
     * @param req
     * @param json
     * @param token
     * @return Este Servicio ibserta una entidadEscision o actualiza
     */
    @POST
    @Path("/setentidadescision")
    @Consumes("text/plain")
    public Response setEntidadEscision(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        EntidadEscisionService service = new EntidadEscisionService();
        EntidadEscision res = new EntidadEscision();
        try {
            EntidadEscision entidadEscision = gson.fromJson(json, EntidadEscision.class);
            try {
                if (entidadEscision.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) ||
                		entidadEscision.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                    entidadEscision.setAudFechaActualizacion(new Date());
                    res = service.updateEntidadEscision(entidadEscision);
                } else if (entidadEscision.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertEntidadEscision(entidadEscision);
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
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una lista de entidades por contenido del
     * nombre
     */
    @POST
    @Path("/getentidadescisionfiltro")
    @Consumes("text/plain")
    public Response getentidadescisionfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
            EntidadEscision entidadEscision = gson.fromJson(json, EntidadEscision.class);
            EntidadEscisionService service = new EntidadEscisionService();
            List<EntidadEscision> d = service.getEntidadEscisionFiltro(entidadEscision);
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
     * @return Este Servicio retorna una lista de Acreditaciones u Obligaciones
     * por Codigo de Declaracion.
     */
    @POST
    @Path("/setentidadeshistoricoestados")
    @Consumes("text/plain")
    public Response setEntidadHistoricoEstados(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
        gson = gsonBuilder.create();
        EntidadHistoricoEstadosService service = new EntidadHistoricoEstadosService();
        EntidadHistoricoEstados res = new EntidadHistoricoEstados();
        try {
            EntidadHistoricoEstados entidad = gson.fromJson(json, EntidadHistoricoEstados.class);
            try {
                if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) ||
                		entidad.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                    entidad.setAudFechaActualizacion(new Date());
                    res = service.updateEntidadHistoricoEstados(entidad);
                } else if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertEntidadHistoricoEstados(entidad);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }

    /**
     * @author: Jose Viscaya
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una lista de EntidadHistoricoEstados por
     * contenido del nombre
     */
    @POST
    @Path("/getentidadeshistoricoestadosfiltro")
    @Consumes("text/plain")
    public Response getEntidadHistoricoEstadosFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
        gson = gsonBuilder.create();
        try {
            EntidadHistoricoEstados entidad = gson.fromJson(json, EntidadHistoricoEstados.class);
            EntidadHistoricoEstadosService service = new EntidadHistoricoEstadosService();
            List<EntidadHistoricoEstadosExt> d = service.getEntidadHistoricoEstadosFiltro(entidad);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            List<EntidadHistoricoEstados> d = new ArrayList<>();
            EntidadHistoricoEstados entidad = new EntidadHistoricoEstados();
            entidad.setError(true);
            entidad.setMensaje(UtilsConstantes.MSG_EXEPCION);
            entidad.setMensajeTecnico(e.getMessage());
            d.add(entidad);
            return Response.status(201).entity(gson.toJson(d)).build();
        }
    }

    /**
     * @author: Jose Viscaya
     *
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una lista de Entidad u Obligaciones por
     * Codigo de Declaracion.
     */
    @POST
    @Path("/getentidadsistemaregimenporentidad")
    @Consumes("text/plain")
    public Response getEntidadesSistemaRegimenPorIdEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
            EntidadSistemaRegimen entidadSistemaRegimen = gson.fromJson(json, EntidadSistemaRegimen.class);
            EntidadSistemaRegimenService service = new EntidadSistemaRegimenService();
            List<EntidadSistemaRegimenExt> d = service.encontrarPorIdEntidad(entidadSistemaRegimen.getCodEntidad());
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
    @Path("/getentidadsistemaregimenFiltro")
    @Consumes("text/plain")
    public Response getEntidadesSistemaRegimenFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		EntidadSistemaRegimen entidadSistemaRegimen = gson.fromJson(json, EntidadSistemaRegimen.class);
    		EntidadSistemaRegimenService service = new EntidadSistemaRegimenService();
    		List<EntidadSistemaRegimenExt> d = service.getEntidadSistemaRegimenFiltro(entidadSistemaRegimen);
    		String out = gson.toJson(d);
    		return Response.status(201).entity(out).build();
    	} catch (JsonParseException e) {
    		e.getStackTrace();
    		return Response.status(201).entity("[]").build();
    	}
    }
    
    /**
     * @author: Jose Viscaya 
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepCuestionario
     */
    @POST
    @Path("/getpcuestionarioid")
    @Consumes("text/plain")
    public Response getPepCuestionarioId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepCuestionario res = new PepCuestionario();
        try {
          	PepCuestionario pepCuestionario = gson.fromJson(json, PepCuestionario.class);
          	PepCuestionarioService service = new PepCuestionarioService();
          	PepCuestionario d = service.getPepCuestionarioById(pepCuestionario.getIdCuestionario());
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
        		res.setError(true);
            res.setMensaje(UtilsConstantes.MSG_EXEPCION);
            res.setMensajeTecnico(e.getMessage());
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepCuestionario
     */
    @POST
    @Path("/getpcuestionarioall")
    @Consumes("text/plain")
    public Response gepcuestionarioall(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
          	PepCuestionario pepCuestionario = gson.fromJson(json, PepCuestionario.class);
          	PepCuestionarioService service = new PepCuestionarioService();
          	List<PepCuestionario> d = service.getPepCuestionarioAll(null);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserra PepCuestionario
     */
    @POST
    @Path("/setpepcuestionario")
    @Consumes("text/plain")
    public Response setPepCuestionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepCuestionarioService service = new PepCuestionarioService();
        PepCuestionario res = new PepCuestionario();
        try {
        	PepCuestionario pepCuestionario = gson.fromJson(json, PepCuestionario.class);
            try {
                if (pepCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		||pepCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	pepCuestionario.setAudFechaActualizacion(new Date());
                    res = service.updatePepCuestionario(pepCuestionario);
                } else if (pepCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertPepCuestionario(pepCuestionario);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepPreguntasCuestionario
     */
    @POST
    @Path("/getpeppreguntascuestionariod")
    @Consumes("text/plain")
    public Response getPepPreguntasCuestionarioId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepPreguntasCuestionario res  = new PepPreguntasCuestionario();
        try {
        		PepPreguntasCuestionario pepPreguntasCuestionario = gson.fromJson(json, PepPreguntasCuestionario.class);
        		PepPreguntasCuestionarioService service = new PepPreguntasCuestionarioService();
        		PepPreguntasCuestionario d = service.getPepPreguntasCuestionarioById(pepPreguntasCuestionario.getIdPreguntaCuestionario());
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
         	res.setError(true);
            res.setMensaje(UtilsConstantes.MSG_EXEPCION);
            res.setMensajeTecnico(e.getMessage());
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepPreguntasCuestionario
     */
    @POST
    @Path("/getpeppreguntascuestionarioall")
    @Consumes("text/plain")
    public Response getPepPreguntasCuestionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        		PepPreguntasCuestionarioService service = new PepPreguntasCuestionarioService();
          	List<PepPreguntasCuestionario> d = service.getPepPreguntasCuestionarioAll(null);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserra PepPreguntasCuestionario
     */
    @POST
    @Path("/setpeppreguntascuestionario")
    @Consumes("text/plain")
    public Response setPepPreguntasCuestionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepPreguntasCuestionarioService service = new PepPreguntasCuestionarioService();
        PepPreguntasCuestionario res = new PepPreguntasCuestionario();
        try {
        	PepPreguntasCuestionario pepPreguntasCuestionario = gson.fromJson(json, PepPreguntasCuestionario.class);
            try {
                if (pepPreguntasCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		||pepPreguntasCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	pepPreguntasCuestionario.setAudFechaActualizacion(new Date());
                    res = service.updatePepPreguntasCuestionario(pepPreguntasCuestionario);
                } else if (pepPreguntasCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertPepPreguntasCuestionario(pepPreguntasCuestionario);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepPreguntasCuestionarioDetalle
     */
    @POST
    @Path("/getpeppreguntascuestionarioDetalleid")
    @Consumes("text/plain")
    public Response getPepPreguntasCuestionarioDetalleId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepPreguntasCuestionarioDetalle res = new PepPreguntasCuestionarioDetalle();
        try {
	        	PepPreguntasCuestionarioDetalle pepPreguntasCuestionarioDetalle = gson.fromJson(json, PepPreguntasCuestionarioDetalle.class);
	        	PepPreguntasCuestionarioDetalleService service = new PepPreguntasCuestionarioDetalleService();
	        	PepPreguntasCuestionarioDetalle d = service.getPepPreguntasCuestionarioDetalleById(pepPreguntasCuestionarioDetalle.getIdPreguntaCuestionarioDetalle());
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
           	res.setError(true);
            res.setMensaje(UtilsConstantes.MSG_EXEPCION);
            res.setMensajeTecnico(e.getMessage());
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepPreguntasCuestionarioDetalle
     */
    @POST
    @Path("/getpeppreguntascuestionariodetalleall")
    @Consumes("text/plain")
    public Response getPepPreguntasCuestionarioDetalle(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        	PepPreguntasCuestionarioDetalleService service = new PepPreguntasCuestionarioDetalleService();
          	List<PepPreguntasCuestionarioDetalle> d = service.getPepPreguntasCuestionarioDetalleAll(null);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserra PepPreguntasCuestionarioDetalle
     */
    @POST
    @Path("/setpeppreguntascuestionariodeteall")
    @Consumes("text/plain")
    public Response setPepPreguntasCuestionarioDetalle(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepPreguntasCuestionarioDetalleService service = new PepPreguntasCuestionarioDetalleService();
        PepPreguntasCuestionarioDetalle res = new PepPreguntasCuestionarioDetalle();
        try {
        	PepPreguntasCuestionarioDetalle pepPreguntasCuestionarioDetalle = gson.fromJson(json, PepPreguntasCuestionarioDetalle.class);
            try {
                if (pepPreguntasCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		|| pepPreguntasCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	pepPreguntasCuestionarioDetalle.setAudFechaActualizacion(new Date());
                    res = service.updatePepPreguntasCuestionarioDetalle(pepPreguntasCuestionarioDetalle);
                } else if (pepPreguntasCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertPepPreguntasCuestionarioDetalle(pepPreguntasCuestionarioDetalle);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepRespuestaCuestionario
     */
    @POST
    @Path("/getpeprespuestacuestionarioid")
    @Consumes("text/plain")
    public Response getPepRespuestaCuestionarioId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepRespuestaCuestionario res = new PepRespuestaCuestionario();
        try {
        		PepRespuestaCuestionario pepRespuestaCuestionario = gson.fromJson(json, PepRespuestaCuestionario.class);
        		PepRespuestaCuestionarioService service = new PepRespuestaCuestionarioService();
        		PepRespuestaCuestionario d = service.getPepRespuestaCuestionarioById(pepRespuestaCuestionario.getIdRespuestaCuestionario());
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
           	res.setError(true);
            res.setMensaje(UtilsConstantes.MSG_EXEPCION);
            res.setMensajeTecnico(e.getMessage());
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepRespuestaCuestionario
     */
    @POST
    @Path("/getpeppespuestacuestionarioall")
    @Consumes("text/plain")
    public Response getPepRespuestaCuestionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        		PepRespuestaCuestionarioService service = new PepRespuestaCuestionarioService();
          	List<PepRespuestaCuestionario> d = service.getPepRespuestaCuestionarioAll(null);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserra PepRespuestaCuestionario
     */
    @POST
    @Path("/setpeprespuestacuestionario")
    @Consumes("text/plain")
    public Response setPepRespuestaCuestionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepRespuestaCuestionarioService service = new PepRespuestaCuestionarioService();
        PepRespuestaCuestionario res = new PepRespuestaCuestionario();
        try {
        	PepRespuestaCuestionario pepRespuestaCuestionario = gson.fromJson(json, PepRespuestaCuestionario.class);
            try {
                if (pepRespuestaCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
                		|| pepRespuestaCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	pepRespuestaCuestionario.setAudFechaActualizacion(new Date());
                    res = service.updatePepRespuestaCuestionario(pepRespuestaCuestionario);
                } else if (pepRespuestaCuestionario.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                	PepCuestionarioService servceC = new PepCuestionarioService();
                	BigDecimal idCuestionario = servceC.getPepCuestionarioById(pepRespuestaCuestionario.getIdTipoCuestionario()).getIdParametricaCuestionario();
                	pepRespuestaCuestionario.setIdTipoCuestionario(idCuestionario.longValue());
                    res = service.insertPepRespuestaCuestionario(pepRespuestaCuestionario);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepRespuestaCuestionarioDetalle
     */
    @POST
    @Path("/getpeprespuestacuestionariodetalleid")
    @Consumes("text/plain")
    public Response getPepRespuestaCuestionarioDetalleId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepRespuestaCuestionarioDetalle res = new PepRespuestaCuestionarioDetalle();
        try {
	        	PepRespuestaCuestionarioDetalle pepRespuestaCuestionarioDetalle = gson.fromJson(json, PepRespuestaCuestionarioDetalle.class);
	        	PepRespuestaCuestionarioDetalleService service = new PepRespuestaCuestionarioDetalleService();
	        	PepRespuestaCuestionarioDetalle d = service.getPepRespuestaCuestionarioDetalleById(pepRespuestaCuestionarioDetalle.getIdRespuestaCuestionarioDetalle());
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
           	res.setError(true);
            res.setMensaje(UtilsConstantes.MSG_EXEPCION);
            res.setMensajeTecnico(e.getMessage());
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepRespuestaCuestionarioDetalle
     */
    @POST
    @Path("/getpeprespuestacuestionariodetalleall")
    @Consumes("text/plain")
    public Response getPepRespuestaCuestionarioDetalle(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        	PepRespuestaCuestionarioDetalleService service = new PepRespuestaCuestionarioDetalleService();
          	List<PepRespuestaCuestionarioDetalle> d = service.getPepRespuestaCuestionarioDetalleAll(null);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserra PepRespuestaCuestionarioDetalle
     */
    @POST
    @Path("/setpeprespuestacuestionariodeteall")
    @Consumes("text/plain")
    public Response setPepRespuestaCuestionarioDetalle(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepRespuestaCuestionarioDetalleService service = new PepRespuestaCuestionarioDetalleService();
        PepRespuestaCuestionarioDetalle res = new PepRespuestaCuestionarioDetalle();
        try {
        	PepRespuestaCuestionarioDetalle pepRespuestaCuestionarioDetalle = gson.fromJson(json, PepRespuestaCuestionarioDetalle.class);
            try {
                if (pepRespuestaCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		||pepRespuestaCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	pepRespuestaCuestionarioDetalle.setAudFechaActualizacion(new Date());
                    res = service.updatePepRespuestaCuestionarioDetalle(pepRespuestaCuestionarioDetalle);
                } else if (pepRespuestaCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertPepRespuestaCuestionarioDetalle(pepRespuestaCuestionarioDetalle);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepRespuestasPreguntaCuestionario
     */
    @POST
    @Path("/getpeprespuestaspreguntacuestionarioid")
    @Consumes("text/plain")
    public Response getPepRespuestasPreguntaCuestionarioId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepRespuestasPreguntaCuestionario res = new PepRespuestasPreguntaCuestionario();
        try {
        	PepRespuestasPreguntaCuestionario pepRespuestasPreguntaCuestionario = gson.fromJson(json, PepRespuestasPreguntaCuestionario.class);
        	PepRespuestasPreguntaCuestionarioService service = new PepRespuestasPreguntaCuestionarioService();
        	PepRespuestasPreguntaCuestionario d = service.getPepRespuestasPreguntaCuestionarioById(pepRespuestasPreguntaCuestionario.getIdRespuestaPreguntaCuestionario());
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
           	res.setError(true);
            res.setMensaje(UtilsConstantes.MSG_EXEPCION);
            res.setMensajeTecnico(e.getMessage());
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un PepRespuestasPreguntaCuestionario
     */
    @POST
    @Path("/getpeprespuestaspreguntacuestionarioall")
    @Consumes("text/plain")
    public Response getPepRespuestasPreguntaCuestionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        	PepRespuestasPreguntaCuestionarioService service = new PepRespuestasPreguntaCuestionarioService();
          	List<PepRespuestasPreguntaCuestionario> d = service.getPepRespuestasPreguntaCuestionarioAll(null);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserra PepRespuestasPreguntaCuestionario
     */
    @POST
    @Path("/setPepRespuestasPreguntaCuestionario")
    @Consumes("text/plain")
    public Response setPepRespuestasPreguntaCuestionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	    json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PepRespuestasPreguntaCuestionarioService service = new PepRespuestasPreguntaCuestionarioService();
        PepRespuestasPreguntaCuestionario res = new PepRespuestasPreguntaCuestionario();
        try {
        	PepRespuestasPreguntaCuestionario pepRespuestaCuestionarioDetalle = gson.fromJson(json, PepRespuestasPreguntaCuestionario.class);
            try {
                if (pepRespuestaCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
                		||pepRespuestaCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	pepRespuestaCuestionarioDetalle.setAudFechaActualizacion(new Date());
                    res = service.updatePepRespuestasPreguntaCuestionario(pepRespuestaCuestionarioDetalle);
                } else if (pepRespuestaCuestionarioDetalle.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertPepRespuestasPreguntaCuestionario(pepRespuestaCuestionarioDetalle);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 25 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de EntidadPlantaDetalleExt
     */
    @POST
    @Path("/getEntidadPlantaDetalle")
    @Consumes("text/plain")
    public Response getEntiddCargoPlanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<EntidadPlantaDetalleExt> err =  new ArrayList<>();
        try {
        	EntidadPlantaDetalleExt EntidadPlantaDetalleExt = gson.fromJson(json, EntidadPlantaDetalleExt.class);
        	EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
          	err = service.getEntiddCargoPlanta(EntidadPlantaDetalleExt);
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
           	EntidadPlantaDetalleExt error = new EntidadPlantaDetalleExt();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 26 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserta y actualiza la tabla Norma
     */
    @POST
    @Path("/setnorma")
    @Consumes("text/plain")
    public Response setNormaCuestionario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA_HORA);
        gson = gsonBuilder.create();
        NormaService service = new NormaService();
        Norma res = new Norma();
        try {
        	Norma norma = gson.fromJson(json, Norma.class);
            try {
                if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
                		||norma.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	norma.setAudFechaActualizacion(new Date());
                    res = service.updateNorma(norma);
                } else if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertNorma(norma);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 26 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una Norma
     */
    @POST
    @Path("/getnormaid")
    @Consumes("text/plain")
    public Response getNorma(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        Norma err =  new Norma();
        try {
        		Norma norma = gson.fromJson(json, Norma.class);
        		NormaService service = new NormaService();
          	err = service.getNormaById(norma.getCodNorma());
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
        	    err = new Norma();
        	    err.setError(true);
        	    err.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    err.setMensaje(e.getMessage());
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 26 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de Norma
     */
    @POST
    @Path("/getnormaall")
    @Consumes("text/plain")
    public Response getnormaall(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<Norma> err =  new ArrayList<>();
        json = new String(Base64.decode(json.getBytes()));
        try {
            NormaService service = new NormaService();
          	err = service.getNormaAll();
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
          	err =  new ArrayList<>();
         	Norma error = new Norma();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
    * @author: Jose Viscaya
    * @fecha 17/08/2018 9:37:16 a.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    *
     */
    @POST
    @Path("/getnormatiponorma")
    @Consumes("text/plain")
    public Response getnormacodTipoNorma(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	List<Norma> err =  new ArrayList<>();
    	try {
    		Norma norma = gson.fromJson(json, Norma.class);
    		NormaService service = new NormaService();
          	err = service.getNormaTipoNorma(norma.getCodTipoNorma());
    		return Response.status(201).entity(gson.toJson(err)).build();
    	} catch (JsonParseException e) {
    		err =  new ArrayList<>();
    		Norma error = new Norma();
    		error.setError(true);
    		error.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		error.setMensaje(e.getMessage());
    		err.add(error);
    		return Response.status(201).entity(gson.toJson(err)).build();
    	}
    }
    
    
    /**
     * @author: Jose Viscaya 
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserta y actualiza la tabla Nomenclatura
     */
    @POST
    @Path("/setnomenclatura")
    @Consumes("text/plain")
    public Response setNomenclatura(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        NomenclaturaService service = new NomenclaturaService();
        Nomenclatura res = new Nomenclatura();
        try {
        	Nomenclatura nomenclatura = gson.fromJson(json, Nomenclatura.class);
            try {
                if (nomenclatura.getCodNomenclatura() !=null && nomenclatura.getCodNomenclatura() >0 ) {
                	nomenclatura.setAudFechaActualizacion(new Date());
                    res = service.updateNomenclatura(nomenclatura);
                } else if (nomenclatura.getCodNomenclatura() ==null) {
                    res = service.insertNomenclatura(nomenclatura);
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
     * @author: Jose Viscaya
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una Nomenclatura
     */
    @POST
    @Path("/getnomenclaturaid")
    @Consumes("text/plain")
    public Response getNomenclaturaId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        Nomenclatura err =  new Nomenclatura();
        try {
         	Nomenclatura norma = gson.fromJson(json, Nomenclatura.class);
         	NomenclaturaService service = new NomenclaturaService();
          	err = service.getNomenclaturaById(norma.getCodNomenclatura());
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
        	    err = new Nomenclatura();
        	    err.setError(true);
        	    err.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    err.setMensaje(e.getMessage());
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de Nomenclatura
     */
    @POST
    @Path("/getnomenclaturaall")
    @Consumes("text/plain")
    public Response getNomenclaturaall(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<Nomenclatura> err =  new ArrayList<>();
        try {
        		NomenclaturaService service = new NomenclaturaService();
          	err = service.getNomenclaturaAll();
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
          	err =  new ArrayList<>();
          	Nomenclatura error = new Nomenclatura();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    
    /**
     * @author: Jose Viscaya
     * @fecha 24 Octubre 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de Nomenclatura
     */
    @POST
    @Path("/getnomenclaturafiltro")
    @Consumes("text/plain")
    public Response getnomenclaturafiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
       
        List<NomenclaturaExt> err =  new ArrayList<>();
        try {
        	 Nomenclatura norma = gson.fromJson(json, Nomenclatura.class);
             NomenclaturaService service = new NomenclaturaService();
          	 err = service.getNomenclaturaFiltro(norma);
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
          	err =  new ArrayList<>();
          	NomenclaturaExt error = new NomenclaturaExt();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserta y actualiza la tabla NomenclaturaEntidad
     */
    @POST
    @Path("/setnomenclaturaEntidad")
    @Consumes("text/plain")
    public Response setNomenclaturaEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	    json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        NomenclaturaEntidadService service = new NomenclaturaEntidadService();
        NomenclaturaEntidad res = new NomenclaturaEntidad();
        try {
        		NomenclaturaEntidad norma = gson.fromJson(json, NomenclaturaEntidad.class);
            try {
                if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		||norma.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	norma.setAudFechaActualizacion(new Date());
                    res = service.updateNomenclaturaEntidad(norma);
                } else if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertNomenclaturaEntidad(norma);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una Nomenclatura
     */
    @POST
    @Path("/getnomenclaturaentidadid")
    @Consumes("text/plain")
    public Response getNomenclaturaEntidadId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        NomenclaturaEntidad err =  new NomenclaturaEntidad();
        try {
	        	NomenclaturaEntidad norma = gson.fromJson(json, NomenclaturaEntidad.class);
	        	NomenclaturaEntidadService service = new NomenclaturaEntidadService();
          	err = service.getNomenclaturaEntidadById(norma.getCodNomenclaturaEntid());
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
        	    err = new NomenclaturaEntidad();
        	    err.setError(true);
        	    err.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    err.setMensaje(e.getMessage());
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de Nomenclatura
     */
    @POST
    @Path("/getnomenclaturaentidadall")
    @Consumes("text/plain")
    public Response getNomenclaturaEntidadall(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<NomenclaturaEntidad> err =  new ArrayList<>();
        try {
        	NomenclaturaEntidadService service = new NomenclaturaEntidadService();
          	err = service.getNomenclaturaEntidadall();
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
          	err =  new ArrayList<>();
          	NomenclaturaEntidad error = new NomenclaturaEntidad();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
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
    @Path("/getnomenclaturaentidadfilter")
    @Consumes("text/plain")
    public Response getNomenclaturaEntidadfilter(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	json = new String(Base64.decode(json.getBytes()));
    	List<NomenclaturaEntidad> err =  new ArrayList<>();
    	try {
    		NomenclaturaEntidadService service = new NomenclaturaEntidadService();
    		NomenclaturaEntidad norma = gson.fromJson(json, NomenclaturaEntidad.class);
    		err = service.getNomenclaturaEntidadFiltro(norma);
    		return Response.status(201).entity(gson.toJson(err)).build();
    	} catch (JsonParseException e) {
    		err =  new ArrayList<>();
    		NomenclaturaEntidad error = new NomenclaturaEntidad();
    		error.setError(true);
    		error.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		error.setMensaje(e.getMessage());
    		err.add(error);
    		return Response.status(201).entity(gson.toJson(err)).build();
    	}
    }
    
    
    /**
     * @author: Jose Viscaya
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio inserta y actualiza la tabla AsignacionSalarial
     */
    @POST
    @Path("/setasignacionsalarial")
    @Consumes("text/plain")
    public Response setAsignacionSalarial(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	    json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        AsignacionSalarialService service = new AsignacionSalarialService();
        AsignacionSalarial res = new AsignacionSalarial();
        try {
        		AsignacionSalarial norma = gson.fromJson(json, AsignacionSalarial.class);
            try {
                if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		||norma.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	norma.setAudFechaActualizacion(new Date());
                    res = service.updateAsignacionSalarial(norma);
                } else if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertAsignacionSalarial(norma);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una AsignacionSalarial
     */
    @POST
    @Path("/getasignacionsalarialid")
    @Consumes("text/plain")
    public Response getAsignacionSalarialId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        AsignacionSalarial err =  new AsignacionSalarial();
        try {
         	AsignacionSalarial norma = gson.fromJson(json, AsignacionSalarial.class);
         	AsignacionSalarialService service = new AsignacionSalarialService();
          	err = service.getAsignacionSalarialById(norma.getCodAsignacionSalarial());
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
        	    err = new AsignacionSalarial();
        	    err.setError(true);
        	    err.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    err.setMensaje(e.getMessage());
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
     * @author: Jose Viscaya
     * @fecha 27 Juli 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de AsignacionSalarial
     */
    @POST
    @Path("/getasignacionsalarialall")
    @Consumes("text/plain")
    public Response getAsignacionSalarialall(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<AsignacionSalarial> err =  new ArrayList<>();
        try {
        		AsignacionSalarialService service = new AsignacionSalarialService();
          	err = service.getAsignacionSalarialall();
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
          	err =  new ArrayList<>();
          	AsignacionSalarial error = new AsignacionSalarial();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    /**
     * 
     * @author: Jose Viscaya
     * @fecha 1/08/2018 3:31:12 p. m.
     * @param req
     * @param json
     * @param token
     * @param timeout
     * @return
     */
    @POST
    @Path("/getpeppreguntascuestionariofiltro")
    @Consumes("text/plain")
    public Response getPepPreguntasCuestionariofiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        		PepPreguntasCuestionario pepCuestionario = gson.fromJson(json, PepPreguntasCuestionario.class);
        		PepPreguntasCuestionarioService service = new PepPreguntasCuestionarioService();
          	List<PepPreguntasCuestionario> d = service.getPepPreguntasCuestionarioFiltro(pepCuestionario);
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
     * @fecha 1/08/2018 3:40:15 p. m.
     * @param req
     * @param json
     * @param token
     * @param timeout
     * @return
     */
    @POST
    @Path("/getpeppreguntascuestionariodetallefiltro")
    @Consumes("text/plain")
    public Response getPepPreguntasCuestionarioDetalleFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        		PepPreguntasCuestionarioDetalle pepCuestionario = gson.fromJson(json, PepPreguntasCuestionarioDetalle.class);
        		PepPreguntasCuestionarioDetalleService service = new PepPreguntasCuestionarioDetalleService();
          	List<PepPreguntasCuestionarioDetalle> d = service.getPepPreguntasCuestionarioDetalleFiltro(pepCuestionario);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    /**
    * @author: Jose Viscaya
    * @fecha 14/08/2018 11:07:29 a.m.
    * @param req
    * @param token
    * @param timeout
    * @return
    *
     */
    @POST
    @Path("/getcuestionarionombre")
    @Consumes("text/plain")
    public Response getCuestionarioNombre(@Context HttpServletRequest req, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		PepCuestionarioService service = new PepCuestionarioService();
    		List<PepCuestionarioExt> d = service.getPepCuestionarioExt();
    		String out = gson.toJson(d);
    		return Response.status(201).entity(out).build();
    	} catch (JsonParseException e) {
    		e.getStackTrace();
    		return Response.status(201).entity("[]").build();
    	}
    }
    /**
    * @author: Jose Viscaya
    * @fecha 14/08/2018 12:01:42 p.m.
    * @param req
    * @param json
    * @param token
    * @return
    *
    */
    @POST
    @Path("/getentidadpersona")
    @Consumes("text/plain")
    public Response getEntidadPersona(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
		json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
            Usuario persona = gson.fromJson(json, Usuario.class);
            EntidadService service = new EntidadService();
            List<Entidad> d = service.getEntidadPorPersonaE(persona.getCodUsuario().intValue());
            return Response.status(201).entity(gson.toJson(d)).build();
        } catch (JsonParseException e) {
        	    List<Entidad> d = new ArrayList<>();
        	    Entidad en = new Entidad();
        	    en.setError(true);
        	    en.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    en.setMensajeTecnico(e.getMessage());
        	    d.add(en);
            return Response.status(201).entity(gson.toJson(d)).build();
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
    @Path("/getnormafitro")
    @Consumes("text/plain")
    public Response getNormaFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		Norma norma = gson.fromJson(json, Norma.class);
    		NormaService service = new NormaService();
    		List<Norma> d = service.getNormaFiltro(norma);
    		return Response.status(201).entity(gson.toJson(d)).build();
    	} catch (JsonParseException e) {
    		List<Norma> d = new ArrayList<>();
    		Norma en = new Norma();
    		en.setError(true); 
    		en.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		en.setMensajeTecnico(e.getMessage());
    		d.add(en);
    		return Response.status(201).entity(gson.toJson(d)).build();
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
    @Path("/getnormalike")
    @Consumes("text/plain")
    public Response getNormaFiltrolike(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		Norma norma = gson.fromJson(json, Norma.class);
    		NormaService service = new NormaService();
    		List<Norma> d = service.getNormaLike(norma);
    		return Response.status(201).entity(gson.toJson(d)).build();
    	} catch (JsonParseException e) {
    		List<Norma> d = new ArrayList<>();
    		Norma en = new Norma();
    		en.setError(true); 
    		en.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		en.setMensajeTecnico(e.getMessage());
    		d.add(en);
    		return Response.status(201).entity(gson.toJson(d)).build();
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
    @Path("/getpersonasactivasentidad")
    @Consumes("text/plain")
    public Response getPersonasActivasEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		PersonaExt person = gson.fromJson(json, PersonaExt.class);
    		PersonaService service = new PersonaService();
    		List<PersonaExt> d = service.getpersonaActivasEntidad(person);
    		return Response.status(201).entity(gson.toJson(d)).build();
    	} catch (JsonParseException e) {
    		List<PersonaExt> d = new ArrayList<>();
    		PersonaExt en = new PersonaExt();
    		en.setError(true);
    		en.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		en.setMensajeTecnico(e.getMessage());
    		d.add(en);
    		return Response.status(201).entity(gson.toJson(d)).build();
    	}
    }
    /**
    * @author: Jose Viscaya 
    * @fecha 29/08/2018 7:46:36 a.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    *
     */
    @POST
    @Path("/getescalasalarial")
    @Consumes("text/plain")
    public Response getEscalaSalarial(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		EscalaSalarialService service = new EscalaSalarialService();
    		List<EscalaSalarial> d = service.getEscalaSalarialAll();
    		return Response.status(201).entity(gson.toJson(d)).build();
    	} catch (JsonParseException e) {
    		List<EscalaSalarial> d = new ArrayList<>();
    		EscalaSalarial en = new EscalaSalarial();
    		en.setError(true);
    		en.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		en.setMensajeTecnico(e.getMessage());
    		d.add(en);
    		return Response.status(201).entity(gson.toJson(d)).build();
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
    @Path("/getescalasalarialid")
    @Consumes("text/plain")
    public Response getEscalaSalarialid(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		EscalaSalarial escalaSalarial = gson.fromJson(json, EscalaSalarial.class);
    		EscalaSalarialService service = new EscalaSalarialService();
    		EscalaSalarial d = service.getEscalaSalarialById(escalaSalarial.getCodEscalaSalarial());
    		return Response.status(201).entity(gson.toJson(d)).build();
    	} catch (JsonParseException e) {
    		EscalaSalarial en = new EscalaSalarial();
    		en.setError(true);
    		en.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		en.setMensajeTecnico(e.getMessage());
    		return Response.status(201).entity(gson.toJson(en)).build();
    	}
    }
    /**
    * @author: Jose Viscaya
    * @fecha 29/08/2018 7:49:04 a.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    */
    @POST
    @Path("/setescalasalarial")
    @Consumes("text/plain")
    public Response setEscalasalarial(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	    json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        EscalaSalarialService service = new EscalaSalarialService();
        EscalaSalarial res = new EscalaSalarial();
        try {
        	EscalaSalarial norma = gson.fromJson(json, EscalaSalarial.class);
            try {
                if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())||
                		norma.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	norma.setAudFechaActualizacion(new Date());
                    res = service.updateEscalaSalarial(norma);
                } else if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertEscalaSalarial(norma);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    /**
     * 
    * @author: Jose Viscaya
    * @fecha 5/09/2018 11:34:44 a.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    *
     */
    @POST
    @Path("/getescalasalarialfiltro")
    @Consumes("text/plain")
    public Response getEscalaSalarialfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		EscalaSalarial norma = gson.fromJson(json, EscalaSalarial.class);
    		EscalaSalarialService service = new EscalaSalarialService();
    		List<EscalaSalarial> d = service.getEscalaSalarialFiltro(norma);
    		return Response.status(201).entity(gson.toJson(d)).build();
    	} catch (JsonParseException e) {
    		List<EscalaSalarial> d = new ArrayList<>();
    		EscalaSalarial en = new EscalaSalarial();
    		en.setError(true);
    		en.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		en.setMensajeTecnico(e.getMessage());
    		d.add(en);
    		return Response.status(201).entity(gson.toJson(d)).build();
    	}
    }
    
    /**
     * 
    * @author: Jose Viscaya 
    * @fecha 5/09/2018 5:04:11 p.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    *
     */
    @POST
    @Path("/setincrementosalarial")
    @Consumes("text/plain")
    public Response setIncrementoSalarial(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
    	    json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        IncrementoSalarialService service = new IncrementoSalarialService();
        IncrementoSalarial res = new IncrementoSalarial();
        try {
        	IncrementoSalarial norma = gson.fromJson(json, IncrementoSalarial.class);
            try {
                if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
                		||norma.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                	norma.setAudFechaActualizacion(new Date());
                    res = service.updateIncrementoSalarial(norma);
                } else if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertIncrementoSalarial(norma);
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
            return Response.status(201).entity(gson.toJson(res)).build();
        }
    }
    
    /**
    * @author: Jose Viscaya
    * @fecha 5/09/2018 5:04:02 p.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    *
    */
    @POST
    @Path("/getincrementosalarialid")
    @Consumes("text/plain")
    public Response getIncrementoSalarialId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        IncrementoSalarial err =  new IncrementoSalarial();
        try {
        	IncrementoSalarial norma = gson.fromJson(json, IncrementoSalarial.class);
        	IncrementoSalarialService service = new IncrementoSalarialService();
          	err = service.getIncrementoSalarialById(norma.getCodIncrementoSalarial());
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
        	    err = new IncrementoSalarial();
        	    err.setError(true);
        	    err.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    err.setMensaje(e.getMessage());
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
    * @author: Jose Viscaya 
    * @fecha 5/09/2018 5:03:53 p.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    */
    @POST
    @Path("/getincrementosalarialall")
    @Consumes("text/plain")
    public Response getincrementosalarialall(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<IncrementoSalarial> err =  new ArrayList<>();
        try {
        	IncrementoSalarialService service = new IncrementoSalarialService();
          	err = service.getIncrementoSalarialall();
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
          	err =  new ArrayList<>();
          	IncrementoSalarial error = new IncrementoSalarial();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    /**
     * 
    * @author: Jose Viscaya 
    * @fecha 5/09/2018 5:03:46 p.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    *
     */
    @POST
    @Path("/getincrementoalarialfiltro")
    @Consumes("text/plain")
    public Response getIncrementoSalarialfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        	IncrementoSalarial pepCuestionario = gson.fromJson(json, IncrementoSalarial.class);
        	IncrementoSalarialService service = new IncrementoSalarialService();
          	List<IncrementoSalarial> d = service.getIncrementoSalarialFilter(pepCuestionario);
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
    * @fecha 10/09/2018 3:37:22 p.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    *
     */
    @POST
    @Path("/setgrupodependencia")
    @Consumes("text/plain")
    public Response setGrupoDependencia(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	GrupoDependenciaService service = new GrupoDependenciaService();
    	GrupoDependencia res = new GrupoDependencia();
    	try {
    		GrupoDependencia norma = gson.fromJson(json, GrupoDependencia.class);
    		try {
    			if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
    					||norma.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
    				norma.setAudFechaActualizacion(new Date());
    				res = service.updateGrupoDependencia(norma);
    			} else if (norma.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
    				res = service.insertGrupoDependencia(norma);
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
    		return Response.status(201).entity(gson.toJson(res)).build();
    	}
    }
    
    /**
     * 
    * @author: Jose Viscaya 
    * @fecha 10/09/2018 3:37:17 p.m.
    * @param req
    * @param json
    * @param token
    * @param timeout
    * @return
    *
     */
    @POST
    @Path("/getgrupodependenciaid")
    @Consumes("text/plain")
    public Response getGrupoDependenciaId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	GrupoDependencia err =  new GrupoDependencia();
    	try {
    		GrupoDependencia norma = gson.fromJson(json, GrupoDependencia.class);
    		GrupoDependenciaService service = new GrupoDependenciaService();
    		err = service.getIncrementoSalarialById(norma.getCodGrupoDependencia());
    		return Response.status(201).entity(gson.toJson(err)).build();
    	} catch (JsonParseException e) {
    		err = new GrupoDependencia();
    		err.setError(true);
    		err.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		err.setMensaje(e.getMessage());
    		return Response.status(201).entity(gson.toJson(err)).build();
    	}
    }
    
   /**
    * 
   * @author: Jose Viscaya 
   * @fecha 10/09/2018 3:38:51 p.m.
   * @param req
   * @param json
   * @param token
   * @param timeout
   * @return
   *
    */
    @POST
    @Path("/getgrupodependenciaall")
    @Consumes("text/plain")
    public Response getGrupoDependenciall(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	List<GrupoDependencia> err =  new ArrayList<>();
    	try {
    		GrupoDependenciaService service = new GrupoDependenciaService();
    		err = service.getGrupoDependenciaByall();
    		return Response.status(201).entity(gson.toJson(err)).build();
    	} catch (JsonParseException e) {
    		err =  new ArrayList<>();
    		GrupoDependencia error = new GrupoDependencia();
    		error.setError(true);
    		error.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		error.setMensaje(e.getMessage());
    		err.add(error);
    		return Response.status(201).entity(gson.toJson(err)).build();
    	}
    }
   /**
    * 
   * @author: Jose Viscaya 
   * @fecha 10/09/2018 3:40:45 p.m.
   * @param req
   * @param json
   * @param token
   * @param timeout
   * @return
   *
    */
    @POST
    @Path("/getgrupodependenciafiltro")
    @Consumes("text/plain")
    public Response getGrupoDependenciailtro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		GrupoDependencia pepCuestionario = gson.fromJson(json, GrupoDependencia.class);
    		GrupoDependenciaService service = new GrupoDependenciaService();
    		List<GrupoDependencia> d = service.getGrupoDependenciaByFiltro(pepCuestionario);
    		String out = gson.toJson(d);
    		return Response.status(201).entity(out).build();
    	} catch (JsonParseException e) {
    		e.getStackTrace();
    		return Response.status(201).entity("[]").build();
    	}
    }
    
    @POST
    @Path("/getgrupodependenciafiltroentidad")
    @Consumes("text/plain")
    public Response getGrupoDependenciailtroEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		GrupoDependenciaExt pepCuestionario = gson.fromJson(json, GrupoDependenciaExt.class);
    		GrupoDependenciaService service = new GrupoDependenciaService();
    		List<GrupoDependencia> d = service.getGrupoDependenciaByFiltroEntidad(pepCuestionario);
    		String out = gson.toJson(d);
    		return Response.status(201).entity(out).build();
    	} catch (JsonParseException e) {
    		e.getStackTrace();
    		return Response.status(201).entity("[]").build();
    	}
    }
    
    /**
     * Elaborado por Sergio Mart�nez 
     * @fecha 11/09/2018
     *
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una lista de pol�ticas p�blicas seg�n el
     * filtro que se env�e
     */
    @POST
    @Path("/getpoliticas")
    @Consumes("text/plain")
    public Response getPoliticasPublicas(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
            PoliticaPublica politica = gson.fromJson(json, PoliticaPublica.class);
            PoliticaPublicaService service = new PoliticaPublicaService();
            List<PoliticaPublica> d = service.getPoliticaByAll(politica);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    
    /**
     * Elaborado por Sergio Mart�nez 
     * @fecha 11/09/2018
     *
     * @param req
     * @param json
     * @param token
     * @return Este servicio crea o actualiza un registro en la tabla 
     * <i>POLITICAS_PUBLICAS</i>
     */
    @POST
    @Path("/setpoliticapublica")
    @Consumes("text/plain")
    public Response setPoliticaPublica(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        PoliticaPublicaService service = new PoliticaPublicaService();
        PoliticaPublica res = new PoliticaPublica();
        try {
            PoliticaPublica politica = gson.fromJson(json, PoliticaPublica.class);
            try {
                if (politica.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
                		||politica.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
                    politica.setAudFechaActualizacion(new Date());
                    res = service.updatePoliticaPublica(politica);
                } else if (politica.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertPoliticaPublica(politica);
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
     * Elaborado por Sergio Mart�nez 
     * @fecha 11/09/2018
     *
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una lista de pol�ticas p�blicas asociadas 
     * a entidad seg�n el filtro que se env�e
     */
    @POST
    @Path("/getentidadpoliticas")
    @Consumes("text/plain")
    public Response getEntidadPoliticasPublicas(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
            EntidadPoliticaPublica entidadPolitica = gson.fromJson(json, EntidadPoliticaPublica.class);
            EntidadPoliticaPublicaService service = new EntidadPoliticaPublicaService();
            List<EntidadPoliticaPublica> d = service.getEntidadPoliticaByFilter(entidadPolitica);
            String out = gson.toJson(d);
            return Response.status(201).entity(out).build();
        } catch (JsonParseException e) {
            e.getStackTrace();
            return Response.status(201).entity("[]").build();
        }
    }
    
    /**
     * Elaborado por Sergio Mart�nez 
     * @fecha 11/09/2018
     *
     * @param req
     * @param json
     * @param token
     * @return Este servicio crea o actualiza un registro en la tabla 
     * <i>POLITICAS_PUBLICAS</i>
     */
    @POST
    @Path("/setentidadpoliticapublica")
    @Consumes("text/plain")
    public Response setEntidadPoliticaPublica(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        EntidadPoliticaPublicaService service = new EntidadPoliticaPublicaService();
        EntidadPoliticaPublica res = new EntidadPoliticaPublica();
        try {
            EntidadPoliticaPublica entidadPolitica = gson.fromJson(json, EntidadPoliticaPublica.class);
            try {
                if (entidadPolitica.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
                		||entidadPolitica.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) ) {
                    entidadPolitica.setAudFechaActualizacion(new Date());
                    res = service.updateEntidadPoliticaPublica(entidadPolitica);
                } else if (entidadPolitica.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
                    res = service.insertEntidadPoliticaPublica(entidadPolitica);
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
     * 
     * @author: Jose Viscaya 
     * @fecha 10/10/2018 - 6:00:32 p. m.
     * @param req
     * @param json
     * @param token
     * @param timeout
     * @return
     */
    @POST
    @Path("/getentidadfusion")
    @Consumes("text/plain")
    public Response getEntidadFusion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	try {
    		EntidadFusion entidadPolitica = gson.fromJson(json, EntidadFusion.class);
    		EntidadFusionService service = new EntidadFusionService();
    		List<EntidadFusion> d = service.getEntidadFusion(entidadPolitica);
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
     * @fecha 10/10/2018 - 6:03:05 p. m.
     * @param req
     * @param json
     * @param token
     * @param timeout
     * @return
     */
    @POST
    @Path("/setentidadfusion")
    @Consumes("text/plain")
    public Response setEntidadFusion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	EntidadFusionService service = new EntidadFusionService();
    	EntidadFusion res = new EntidadFusion();
    	try {
    		EntidadFusion entidadPolitica = gson.fromJson(json, EntidadFusion.class);
    		try {
    			if (entidadPolitica.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
    					||entidadPolitica.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
    				entidadPolitica.setAudFechaActualizacion(new Date());
    				res = service.updateEntidadFusion(entidadPolitica);
    			} else if (entidadPolitica.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
    				res = service.insertEntidadFusion(entidadPolitica);
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
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo para obtener Personas que tienen asociado un rol enviando una lista de Roles.
	 */
	@POST
	@Path ( "/getPersonasPorRoles" ) 
	@Consumes("text/plain")
	public Response getPersonasPorRoles(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
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
			List<PersonaExt> list = service.getPersonasPorRoles(objJson);
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
     * 
     * Elaborado por Maria Alejandra C
     * @fecha 18/10/2018
     * @param req
     * @param json
     * @param token
     * @param timeout
     * @return
     */
	@POST
	@Path ( "/getpoliticaspublicasentidad" ) 
	@Consumes("text/plain")
	public Response getPoliticasPublicasEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadPoliticaPublicaExt objJson = gson.fromJson(json, EntidadPoliticaPublicaExt.class);
			EntidadPoliticaPublicaService service = new EntidadPoliticaPublicaService();
			List<EntidadPoliticaPublicaExt> list = service.getPoliticasPublicasEntidad(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EntidadPoliticaPublicaExt user = new EntidadPoliticaPublicaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadPoliticaPublicaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * 
	 * @author jesu_
	 * @fecha 10/10/2018 - 6:03:05 p. m.
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return {@link List<DependenciaEntidadExt>}
	 */
	@POST
	@Path("/obtenerdependenciasestructura")
	@Consumes("text/plain")
	public Response obtenerDependenciasEstructura(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		List<DependenciaEntidadExt> d;
		DependenciaEntidadExt objeto;
		try {
			objeto = gson.fromJson(json, DependenciaEntidadExt.class);
			DependenciaEntidadService service = new DependenciaEntidadService();
			d = service.obtenerDependenciasEstructura(objeto);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (Exception e) {
			d = new ArrayList<>();
			objeto = new DependenciaEntidadExt();
			objeto.setError(true);
			objeto.setMensaje(e.getMessage());
			d.add(objeto);
			return Response.status(201).entity(gson.toJson(d)).build();
		}
	}
	
	/**
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return {@link DependenciaEntidadExt}
	 */
	@POST
	@Path("/obtenerdependenciaestructuracodpadre")
	@Consumes("text/plain")
	public Response obtenerDependenciaEstructuraCodPadre(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DependenciaEntidadExt objeto;
		try {
			objeto = gson.fromJson(json, DependenciaEntidadExt.class);
			DependenciaEntidadService service = new DependenciaEntidadService();
			DependenciaEntidadExt d = service.obtenerDependenciaEstructuraCodPadre(objeto);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (Exception e) {
			objeto = new DependenciaEntidadExt();
			objeto.setError(true);
			objeto.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(objeto)).build();
		}
	}
	
	/**
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return {@link DependenciaEntidadExt}
	 */
	@POST
	@Path("/obtenerdependenciaestructurajerarquia")
	@Consumes("text/plain")
	public Response obtenerDependenciaEstructuraJerarquia(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DependenciaEntidadExt objeto;
		try {
			objeto = gson.fromJson(json, DependenciaEntidadExt.class);
			DependenciaEntidadService service = new DependenciaEntidadService();
			DependenciaEntidadExt d = service.obtenerDependenciaEstructuraJerarquia(objeto);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (Exception e) {
			objeto = new DependenciaEntidadExt();
			objeto.setError(true);
			objeto.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(objeto)).build();
		}
	}
	
	/**
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return {@link DependenciaEntidadExt}
	 */
	@POST
	@Path("/obtenerdependenciaestructurahijo")
	@Consumes("text/plain")
	public Response obtenerDependenciaEstructuraHijos(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DependenciaEntidadExt objeto;
		try {
			objeto = gson.fromJson(json, DependenciaEntidadExt.class);
			DependenciaEntidadService service = new DependenciaEntidadService();
			List<DependenciaEntidadExt> d = service.obtenerDependenciaEstructuraHijos(objeto);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (Exception e) {
			DependenciaEntidadExt objetoError = new DependenciaEntidadExt();
			List<DependenciaEntidadExt> asoc = new ArrayList<>();
			objetoError = new DependenciaEntidadExt();
			objetoError.setError(true);
			objetoError.setMensaje("Error en la consulta");
			objetoError.setMensajeTecnico(e.getMessage());
			asoc.add(objetoError);
			return Response.status(201).entity(gson.toJson(asoc)).build();
		}
	}
	
	/**
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return {@link DependenciaEntidadExt}
	 */
	@POST
	@Path("/obtenerDependenciaPadreByCodPredecesor")
	@Consumes("text/plain")
	public Response obtenerDependenciaPadreByCodPredecesor(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		DependenciaEntidadExt objeto;
		try {
			objeto = gson.fromJson(json, DependenciaEntidadExt.class);
			DependenciaEntidadService service = new DependenciaEntidadService();
			DependenciaEntidadExt d = service.obtenerDependenciaPadreByCodPredecesor(objeto);
			return Response.status(201).entity(gson.toJson(d)).build();
		} catch (Exception e) {
			objeto = new DependenciaEntidadExt();
			objeto.setError(true);
			objeto.setMensaje(e.getMessage());
			return Response.status(201).entity(gson.toJson(objeto)).build();
		}
	}  
	
	
    /**
     * 
     * @author: Jose Viscaya 
     * @fecha 10/10/2018 - 6:03:05 p. m.
     * @param req
     * @param json
     * @param token
     * @param timeout
     * @return
     */
    @POST
    @Path("/setestructuraentidad")
    @Consumes("text/plain")
    public Response setEstructuraEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	EstructuraService service = new EstructuraService();
    	Estructura res = new Estructura();
    	try {
    		Estructura entidad = gson.fromJson(json, Estructura.class);
    		try {
    			if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
    					||entidad.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
    				entidad.setAudFechaActualizacion(new Date());
    				res = service.updateEstructura(entidad);
    			} else if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
    				res = service.insertEstructura(entidad);
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
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
    @POST
	@Path ( "/getestructurabyentidad" ) 
	@Consumes("text/plain")
	public Response getEstructuraByEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Estructura objJson = gson.fromJson(json, Estructura.class);
			EstructuraService service = new EstructuraService();
			Estructura list = service.getEstructuraByEntidad(objJson.getCodEntidad());
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			Estructura user = new Estructura();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
    /**
     * 
     * @param req
     * @param json
     * @param token
     * @param timeout
     * @return
     */
    @POST
	@Path ( "/getestructurabyid" ) 
	@Consumes("text/plain")
	public Response getEstructuraById(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Estructura objJson = gson.fromJson(json, Estructura.class);
			EstructuraService service = new EstructuraService();
			Estructura list = service.getEstructuraById(objJson.getCodEstructura());
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			Estructura user = new Estructura();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	/**
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
    @POST
	@Path ( "/getestructurabyfiltro" ) 
	@Consumes("text/plain")
	public Response getEstructuraByFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Estructura objJson = gson.fromJson(json, Estructura.class);
			EstructuraService service = new EstructuraService();
			List<Estructura> list = service.getEstructuraByFiltro(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			Estructura user = new Estructura();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
			List<Estructura> asoc = new ArrayList<>();
			asoc.add(user);
			return Response.status(201).entity(gson.toJson(asoc)).build();
		}
	}
    
    /**
     * 
     * @author: Jose Viscaya 
     * @fecha 10/10/2018 - 6:03:05 p. m.
     * @param req
     * @param json
     * @param token
     * @param timeout
     * @return
     */
    @POST
    @Path("/setdependenciaentidad")
    @Consumes("text/plain")
    public Response setDependenciaEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
    	json = new String(Base64.decode(json.getBytes()));
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
    	gson = gsonBuilder.create();
    	DependenciaEntidadService service = new DependenciaEntidadService();
    	DependenciaEntidad res = new DependenciaEntidad();
    	try {
    		DependenciaEntidad entidad = gson.fromJson(json, DependenciaEntidad.class);
    		try {
    			if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue())
    					|| entidad.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
    				entidad.setAudFechaActualizacion(new Date());
    				res = service.updateDependenciaEntidad(entidad);
    			} else if (entidad.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
    				res = service.insertDependenciaEntidad(entidad);
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
    * Elaborado por Maria Alejandra C
    * @fecha 26-10-2018
    * @param req
    * @param json
    * @param token
    * @retdo metodo que obtiene las asignaciones salariales por filtro
	 */
	@POST
	@Path ( "/getasignacionsalarialbyfiltro" ) 
	@Consumes("text/plain")
	public Response getasignacionsalarialbyfiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			AsignacionSalarialExt objJson = gson.fromJson(json, AsignacionSalarialExt.class);
			AsignacionSalarialService service = new AsignacionSalarialService();
			List<AsignacionSalarialExt> list = service.getVinculacionPorPlanta(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			AsignacionSalarialExt user = new AsignacionSalarialExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<AsignacionSalarialExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * 
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getdenomincacionentidad" ) 
	@Consumes("text/plain")
	public Response getDenomincacionEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			Entidad objJson = gson.fromJson(json, Entidad.class);
			ParametricaService service = new ParametricaService();
			List<Parametrica> list = service.getDenomincacionEntidad(objJson.getCodEntidad());
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			Parametrica user = new Parametrica();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<Parametrica> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio actualiza todas los car que se tengan para una planta en especifico,
	 * y realiza un eliminado logico
	 */
	@POST
	@Path ( "/setdesactivacioncargosautomatico" ) 
	@Consumes("text/plain")
	public Response setdesactivacioncargosautomatico(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
		EntidadPlantaDetalleExt res = new EntidadPlantaDetalleExt();
		try {
			EntidadPlantaDetalleExt EntidadPlantaDetalle = gson.fromJson(json, EntidadPlantaDetalleExt.class);
			try {
				if(EntidadPlantaDetalle.getCodEntidadPlanta() !=null && EntidadPlantaDetalle.getCodEntidadPlanta().longValue() > 0) {
					res = service.updateCargosDePlantaAutomatico(EntidadPlantaDetalle);
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
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Metodo que retorna la lista de entidades a las que se encuentra asociada una persona
	 */
	@POST
	@Path ( "/getentidadesasociadaspersonafiltro" ) 
	@Consumes("text/plain")
	public Response getEntidadesAsociadasPersonaFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadExt objJson = gson.fromJson(json, EntidadExt.class);
			EntidadService service = new EntidadService();
			List<EntidadExt> list = service.getEntidadesAsociadasPersonaFiltro(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			EntidadExt user = new EntidadExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	@POST
    @Path("/getentidadpoliticafiltro")
    @Consumes("text/plain")
    public Response getentidadpoliticafiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
		json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        	EntidadPoliticaPublica entidadPolitica = gson.fromJson(json, EntidadPoliticaPublica.class);
        	EntidadPoliticaPublicaService service = new EntidadPoliticaPublicaService();
            List<EntidadPoliticaPublica> d = service.getPoliticasPublicasEntidadFiltro(entidadPolitica);
            return Response.status(201).entity(gson.toJson(d)).build();
        } catch (JsonParseException e) {
        	    List<Entidad> d = new ArrayList<>();
        	    Entidad en = new Entidad();
        	    en.setError(true);
        	    en.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    en.setMensajeTecnico(e.getMessage());
        	    d.add(en);
            return Response.status(201).entity(gson.toJson(d)).build();
        }
    }
	
	@POST
    @Path("/delentidadpolitica")
    @Consumes("text/plain")
    public Response delentidadpolitica(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
		json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        	EntidadPoliticaPublica entidadPolitica = gson.fromJson(json, EntidadPoliticaPublica.class);
        	EntidadPoliticaPublicaService service = new EntidadPoliticaPublicaService();
            EntidadPoliticaPublica d = service.deleteEntidadPoliticaPublica(entidadPolitica.getCodEntidadPoliticaPublica());
            return Response.status(201).entity(gson.toJson(d)).build();
        } catch (JsonParseException e) {
        	EntidadPoliticaPublica en = new EntidadPoliticaPublica();
    	    en.setError(true);
    	    en.setMensaje(UtilsConstantes.MSG_EXEPCION);
    	    en.setMensajeTecnico(e.getMessage());
            return Response.status(201).entity(gson.toJson(en)).build();
        }
    }
	
	@POST
    @Path("/getentidadbogota")
    @Consumes("text/plain")
    public Response getentidadbogota(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
		json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        	Entidad entidad = gson.fromJson(json, Entidad.class);
        	EntidadService service = new EntidadService();
        	Entidad d = service.entidadByBogota(entidad);
            return Response.status(201).entity(gson.toJson(d)).build();
        } catch (JsonParseException e) {
        	    Entidad en = new Entidad();
        	    en.setError(true);
        	    en.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    en.setMensajeTecnico(e.getMessage());
            return Response.status(201).entity(gson.toJson(en)).build();
        }
    }
	@POST
    @Path("/getentidadesbogotausuario")
    @Consumes("text/plain")
    public Response getEntidadesBogotaUsuario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
    	}
		json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        try {
        	Usuario usuario = gson.fromJson(json, Usuario.class);
        	EntidadService service = new EntidadService();
            List<Entidad> d = service.getEntidadesBogotaUsuario(usuario.getCodUsuario().intValue());
            return Response.status(201).entity(gson.toJson(d)).build();
        } catch (JsonParseException e) {
        	    List<Entidad> d = new ArrayList<>();
        	    Entidad en = new Entidad();
        	    en.setError(true);
        	    en.setMensaje(UtilsConstantes.MSG_EXEPCION);
        	    en.setMensajeTecnico(e.getMessage());
        	    d.add(en);
            return Response.status(201).entity(gson.toJson(d)).build();
        }
    }	
	
	/**
     * @author: Jose Viscaya 
     * @fecha 10 noviembre 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna una setnomenclaturadenominacion
     */
	 	@POST
	    @Path("/setnomenclaturadenominacion")
	    @Consumes("text/plain")
	    public Response setNomenclaturaDenominacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	        }
	    	json = new String(Base64.decode(json.getBytes()));
	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	        gson = gsonBuilder.create();
	        NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
	        NomenclaturaDenominacion res = new NomenclaturaDenominacion();
	        try {
	        	NomenclaturaDenominacion nomenclatura = gson.fromJson(json, NomenclaturaDenominacion.class);
	            try {
	                if (nomenclatura.getCodNomenclaturaDenominacion()==null ) {
	                	nomenclatura.setAudFechaActualizacion(new Date());
	                    res = service.insertNomenclaturaDenominacion(nomenclatura);
	                } else {
	                    res = service.updateNomenclaturaDenominacion(nomenclatura);
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
	     * @author: Jose Viscaya 
	     * @fecha 10 noviembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una getnomenclaturadenominacionfiltro
	     */
	    @POST
	    @Path("/getnomenclaturadenominacionfiltro")
	    @Consumes("text/plain")
	    public Response getNomenclaturaDenominacionFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	        }
	        json = new String(Base64.decode(json.getBytes()));
	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	        gson = gsonBuilder.create();
	        List<NomenclaturaDenominacionExt> err =  new ArrayList<>();
	        try {
	        	NomenclaturaDenominacion nomenclaturaDenominacion = gson.fromJson(json, NomenclaturaDenominacion.class);
	        	NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
	          	err = service.getNomenclaturaDenominacionByFiltro(nomenclaturaDenominacion);
	            return Response.status(201).entity(gson.toJson(err)).build();
	        } catch (JsonParseException e) {
	        	    err = new ArrayList<>();
	        	    NomenclaturaDenominacionExt er = new NomenclaturaDenominacionExt();
	        	    er.setError(true);
	        	    er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	        	    er.setMensaje(e.getMessage());
	        	    err.add(er);
	            return Response.status(201).entity(gson.toJson(err)).build();
	        }
	    }
	    /**
	     * @author: Jose Viscaya 
	     * @fecha 10 noviembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una setdenominacion
	     */
	    @POST
	    @Path("/setdenominacion")
	    @Consumes("text/plain")
	    public Response setDenominacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	        }
	    	json = new String(Base64.decode(json.getBytes()));
	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	        gson = gsonBuilder.create();
	        DenominacionService service = new DenominacionService();
	        Denominacion res = new Denominacion();
	        try {
	        	Denominacion denominacion = gson.fromJson(json, Denominacion.class);
	            try {
	                if (denominacion.getAudAccion().equals(TipoParametro.AUDITORIA_INSERT.getValue())) {
	                	denominacion.setAudFechaActualizacion(new Date());
	                    res = service.insertDenominacion(denominacion);
	                } else if (denominacion.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
	                		||denominacion.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
	                    res = service.updateDenominacion(denominacion);
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
	     * @author: Jose Viscaya 
	     * @fecha 10 noviembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una getdenominacionfiltro
	     */
	    @POST
	    @Path("/getdenominacionfiltro")
	    @Consumes("text/plain")
	    public Response getDenominacionFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	        }
	        json = new String(Base64.decode(json.getBytes()));
	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	        gson = gsonBuilder.create();
	        List<Denominacion> err =  new ArrayList<>();
	        try {
	        	Denominacion denominacion = gson.fromJson(json, Denominacion.class);
	        	DenominacionService service = new DenominacionService();
	          	err = service.getDenominacionFiltro(denominacion);
	            return Response.status(201).entity(gson.toJson(err)).build();
	        } catch (JsonParseException e) {
	        	    err = new ArrayList<>();
	        	    Denominacion er = new Denominacion();
	        	    er.setError(true);
	        	    er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	        	    er.setMensaje(e.getMessage());
	        	    err.add(er);
	            return Response.status(201).entity(gson.toJson(err)).build();
	        }
	    }
	    
	    /**
	     * @author: Jose Viscaya 
	     * @fecha 10 noviembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una getdenominacionfiltro
	     */
	    @POST
	    @Path("/getdenominacionid")
	    @Consumes("text/plain")
	    public Response getDenominacionId(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	        }
	        json = new String(Base64.decode(json.getBytes()));
	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	        gson = gsonBuilder.create();
	        Denominacion err =  new Denominacion();
	        try {
	        	Denominacion denominacion = gson.fromJson(json, Denominacion.class);
	        	DenominacionService service = new DenominacionService();
	          	err = service.getDenominacionById(denominacion.getCodDenominacion());
	            return Response.status(201).entity(gson.toJson(err)).build();
	        } catch (JsonParseException e) {
	        	    Denominacion er = new Denominacion();
	        	    er.setError(true);
	        	    er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	        	    er.setMensaje(e.getMessage());
	            return Response.status(201).entity(gson.toJson(er)).build();
	        }
	    }
	    /**
	     * @author: Jose Viscaya 
	     * @fecha 12 Diciembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una setdenominacion
	     */
	    @POST
	    @Path("/setplantapersonautluan")
	    @Consumes("text/plain")
	    public Response setPlantaPersonaUtlUan(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	PlantaPersonaUtlUanService service = new PlantaPersonaUtlUanService();
	    	PlantaPersonaUtlUan res = new PlantaPersonaUtlUan();
	    	try {
	    		PlantaPersonaUtlUan plantaPersonaUtlUan = gson.fromJson(json, PlantaPersonaUtlUan.class);
	    		try {
	    			if(plantaPersonaUtlUan.getCodPlantaPersonaUtlUan() != null) {
	    				res = service.updatePlantaPersonaUtlUan(plantaPersonaUtlUan);
	    			}else {
	    				res = service.insertPlantaPersonaUtlUan(plantaPersonaUtlUan);
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
	     * @author: Jose Viscaya 
	     * @fecha 12 Diciembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una getdenominacionfiltro
	     */
	    @POST
	    @Path("/getplantapersonautluanfiltro")
	    @Consumes("text/plain")
	    public Response getPlantaPersonaUtlUanFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	List<PlantaPersonaUtlUanExt> err =  new ArrayList<>();
	    	try {
	    		PlantaPersonaUtlUanExt denominacion = gson.fromJson(json, PlantaPersonaUtlUanExt.class);
	    		PlantaPersonaUtlUanService service = new PlantaPersonaUtlUanService();
	    		err = service.getPlantaPersonaUtlUanFiltro(denominacion);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	} catch (JsonParseException e) {
	    		err = new ArrayList<>();
	    		PlantaPersonaUtlUanExt er = new PlantaPersonaUtlUanExt();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		err.add(er);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	}
	    }
	    
	    
	    /**
	     * @author: Jose Viscaya 
	     * @fecha 12 Diciembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una setdenominacion
	     */
	    @POST
	    @Path("/updatenoenclaturadenominacion")
	    @Consumes("text/plain")
	    public Response updateNoenclatura(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
	    	NomenclaturaDenominacion res = new NomenclaturaDenominacion();
	    	try {
	    		NomenclaturaDenominacion nomenclaturaDenominacion = gson.fromJson(json, NomenclaturaDenominacion.class);
	    		try {
	    			if (nomenclaturaDenominacion.getAudAccion().equals(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()))
	    					||nomenclaturaDenominacion.getAudAccion().equals(Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue()))) {
	    				nomenclaturaDenominacion.setAudFechaActualizacion(new Date());
	    				res = service.updateNoenclatura(nomenclaturaDenominacion);
	    			}else {
	    				res.setError(true);
		    			res.setMensaje(UtilsConstantes.MSG_NO_SOPORTADO + " " +nomenclaturaDenominacion.getAudAccion());
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
	     * @author: Jose Viscaya 
	     * @fecha 13 Diciembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una getdenominacionfiltro
	     */
	    @POST
	    @Path("/getnomenclaturaentidad")
	    @Consumes("text/plain")
	    public Response getNomenclaturaEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	List<Nomenclatura> err =  new ArrayList<>();
	    	try {
	    		NomenclaturaExt nomenclatura = gson.fromJson(json, NomenclaturaExt.class);
	    		NomenclaturaService service = new NomenclaturaService();
	    		err = service.getNomenclaturaByEntidad(nomenclatura);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	} catch (JsonParseException e) {
	    		err = new ArrayList<>();
	    		Nomenclatura er = new Nomenclatura();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		err.add(er);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	}
	    }
	    
	    /**
	     * @author: Jose Viscaya 
	     * @fecha 13 Diciembre 2018
	     * @param req
	     * @param json
	     * @param token
	     * @return Este Servicio retorna una getdenominacionfiltro
	     */
	    @POST
	    @Path("/getpersonaentidadplanta")
	    @Consumes("text/plain")
	    public Response getPersonaEntidadPlanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	List<PersonaExt> err =  new ArrayList<>();
	    	try {
	    		PersonaExt persona = gson.fromJson(json, PersonaExt.class);
	    		PersonaService service = new PersonaService();
	    		err = service.getPersonaEntidadPlanta(persona);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	} catch (JsonParseException e) {
	    		err = new ArrayList<>();
	    		PersonaExt er = new PersonaExt();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		err.add(er);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	}
	    }
	    
	    
	    /**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Cambia el codigo de la denominacion, por la denominacion destino (Equivalencia)
		 */
		@POST
		@Path ( "/setdenominaciondestino" ) 
		@Consumes("text/plain")
		public Response setdenominaciondestino(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
			EntidadPlantaDetalleExt res = new EntidadPlantaDetalleExt();
			try {
				EntidadPlantaDetalleExt EntidadPlantaDetalle = gson.fromJson(json, EntidadPlantaDetalleExt.class);
				try {
					res = service.updateDenominacionDestino(EntidadPlantaDetalle);
					
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
		
		@POST
	    @Path("/getAutonomiaEntidad")
	    @Consumes("text/plain")
	    public Response getAutonomiaEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	List<AutonomiaExt> err =  new ArrayList<>();
	    	try {
	    		Autonomia autonomia = gson.fromJson(json, Autonomia.class);
	    		AutonomiaService service = new AutonomiaService();
	    		err = service.getAutonomiaCodEntidadFlg(autonomia);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	} catch (JsonParseException e) {
	    		err = new ArrayList<>();
	    		AutonomiaExt er = new AutonomiaExt();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		err.add(er);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	}
	    }
	    
	    
	    /**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Cambia el codigo de la denominacion, por la denominacion destino (Equivalencia)
		 */
		@POST
		@Path ("/setAutonomiaEntidad") 
		@Consumes("text/plain")
		public Response setAutonomia(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			AutonomiaService service = new AutonomiaService();
			Autonomia res = new Autonomia();
			AutonomiaExt cosAutonomia;
			try {
				Autonomia autonomia = gson.fromJson(json, Autonomia.class);
				try {
					if(autonomia.getCodTipoAutonomia()!=null) {
						if(autonomia.getCodEntidad()!=null) {
							if(autonomia.getFlgActivo() !=null) {
								res.setCodEntidad(autonomia.getCodEntidad());
								res.setCodTipoAutonomia(autonomia.getCodTipoAutonomia());
								cosAutonomia = service.getAutonomiaCodEntidad(res);
								if(cosAutonomia != null) {
									autonomia.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
									res = service.updateAutonomia(autonomia);
								}else {
									autonomia.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue()));
									res = service.insertAutonomia(autonomia);
								}
							}else {
								res.setError(true);
								res.setMensaje(UtilsConstantes.MSG_OBLIGATORIOS);
							}
						}else {
							res.setError(true);
							res.setMensaje(UtilsConstantes.MSG_OBLIGATORIOS);
						}
					}else {
						res.setError(true);
						res.setMensaje(UtilsConstantes.MSG_OBLIGATORIOS);
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
		 * Elaborado por:
		 * Jose Viscaya 9 ene. 2019
		 * @param req
		 * @param json
		 * @param token
		 * @param timeout
		 * @return
		 */
	    @POST
	    @Path("/getUsuarioRolDependenciaFiltro")
	    @Consumes("text/plain")
	    public Response getUsuarioRolDependenciaFiltro(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	List<UsuarioRolDependencia> err =  new ArrayList<>();
	    	try {
	    		UsuarioRolDependencia usuarioRolDependencia = gson.fromJson(json, UsuarioRolDependencia.class);
	    		UsuarioRolDependenciaService service = new UsuarioRolDependenciaService();
	    		err = service.getUsuarioRolDependenciaFiltro(usuarioRolDependencia);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	} catch (JsonParseException e) {
	    		err = new ArrayList<>();
	    		UsuarioRolDependencia er = new UsuarioRolDependencia();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		err.add(er);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	}
	    }
	    /**
	     * 
	     * Elaborado por:
	     * Jose Viscaya 10 ene. 2019
	     * @param req
	     * @param json
	     * @param token
	     * @param timeout
	     * @return
	     */
	    @POST
	    @Path("/getUsuarioRolDependenciaId")
	    @Consumes("text/plain")
	    public Response getUsuarioRolDependenciaById(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	UsuarioRolDependencia err =  new UsuarioRolDependencia();
	    	try {
	    		UsuarioRolDependencia usuarioRolDependencia = gson.fromJson(json, UsuarioRolDependencia.class);
	    		UsuarioRolDependenciaService service = new UsuarioRolDependenciaService();
	    		err = service.getUsuarioRolDependenciaById(usuarioRolDependencia.getCodUsuarioRolDependencia());
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	} catch (JsonParseException e) {
	    		err = new UsuarioRolDependencia();
	    		UsuarioRolDependencia er = new UsuarioRolDependencia();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		return Response.status(201).entity(gson.toJson(er)).build();
	    	}
	    }
	    
	    
	    /**
	     * 
	     * Elaborado por:
	     * Jose Viscaya 9 ene. 2019
	     * @param req
	     * @param json
	     * @param token
	     * @param timeout
	     * @return
	     */
		@POST
		@Path ( "/setUsuarioRolDependencia" ) 
		@Consumes("text/plain")
		public Response setUsuarioRolDependencia(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			UsuarioRolDependenciaService service = new UsuarioRolDependenciaService();
			UsuarioRolDependencia res = new UsuarioRolDependencia();
			try {
				UsuarioRolDependencia usuarioRolDependencia = gson.fromJson(json, UsuarioRolDependencia.class);
				try {
					if (usuarioRolDependencia.getCodUsuarioRolDependencia() != null) {
						usuarioRolDependencia.setAudFechaActualizacion(new Date());
						res = service.updateUsuarioRolDependencia(usuarioRolDependencia);
						
	    			} else {
	    				res = service.insertUsuarioRolDependencia(usuarioRolDependencia);
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
		 * Elaborado por:
		 * Jose Viscaya 10 ene. 2019
		 * @param req
		 * @param json
		 * @param token
		 * @param timeout
		 * @return
		 */
		@POST
	    @Path("/getUsuarioRolEntidadByUsuario")
	    @Consumes("text/plain")
	    public Response getUsuarioRolEntidadByUsuario(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	UsuarioRolEntidad er =  new UsuarioRolEntidad();
	    	try {
	    		UsuarioExt usuarioRolDependencia = gson.fromJson(json, UsuarioExt.class);
	    		UsuarioRolEntidadService service = new UsuarioRolEntidadService();
	    		er = service.getUsuarioRol(usuarioRolDependencia);
	    		return Response.status(201).entity(gson.toJson(er)).build();
	    	} catch (JsonParseException e) {
	    		er = new UsuarioRolEntidad();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		return Response.status(201).entity(gson.toJson(er)).build();
	    	}
	    }
		
		
	/**
     * Elaborado por Mar�a Alejandra Colorado R�os
     * @fecha 14 de enero 2018
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna un objeto EntidadPlantaDetalleExt con el total de cargos directivos de una entidad 
     */
    @POST
    @Path("/getcantidadcargosdirectivos")
    @Consumes("text/plain")
    public Response getCantidadCargosDirectivosEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        EntidadPlantaDetalleExt err =  new EntidadPlantaDetalleExt();
        try {
        	EntidadPlantaDetalleExt EntidadPlantaDetalleExt = gson.fromJson(json, EntidadPlantaDetalleExt.class);
        	EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
          	err = service.getTotalCargosDirectivoEntidad(EntidadPlantaDetalleExt);
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
           	EntidadPlantaDetalleExt error = new EntidadPlantaDetalleExt();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
            return Response.status(201).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * Elaborado por Mar�a Alejandra Colorado R�os
     * Servicio que llama al procedimiento SP_NOMENCLATURA_UPDATE_ASOCIADAS una vez se realice
     * modificaciones en nomenclatura general
     * @fecha 05 de febrer0 2019
     * @param req
     * @param json
     * @param token
     */
	@POST
	@Path("/setnomenclaturasheredadas")
	@Consumes("text/plain")
	public Response setNomenclaturasHeredadas(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		NomenclaturaExt res = new NomenclaturaExt();
		try {
			NomenclaturaExt nomenclatura = gson.fromJson(json, NomenclaturaExt.class);
			NomenclaturaService service = new NomenclaturaService();
			nomenclatura.setAudFechaActualizacion(new Date());
			if (nomenclatura.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
					||nomenclatura.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue())) {
				res = service.updateNomenclaturasHeredadas(nomenclatura);
			} 
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(res)).build();
		}
	}
    
	
	 /**
     * Elaborado por Maria Alejandra Colorado Rios
     * Servicio que llama al procedimiento SP_NOMENCLATURA_EQUIVALENCIAS. Modulo de Nomenclatura
     * @fecha 05 de febrer0 2019
     * @param req
     * @param json
     * @param token
     */
	@POST
	@Path("/setnomenclaturaequivalencia")
	@Consumes("text/plain")
	public Response setNomenclaturaEquivalencia(@Context HttpServletRequest req, String json,
			@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		NomenclaturaExt res = new NomenclaturaExt();
		try {
			NomenclaturaExt nomenclatura = gson.fromJson(json, NomenclaturaExt.class);
			NomenclaturaService service = new NomenclaturaService();
			nomenclatura.setAudFechaActualizacion(new Date());
			res = service.updateNomenclaturaEquivalencia(nomenclatura);
			return Response.status(201).entity(gson.toJson(res)).build();
		} catch (JsonParseException e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
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
	 * @return
	 * Este Servicio retorna una lista de EntidadPlantaDetalle
	 */
	@POST
	@Path ( "/getcargoaprobarhojavida" ) 
	@Consumes("text/plain")
	public Response getCargoAprobarHojaVida(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			NomenclaturaDenominacionExt objJson = gson.fromJson(json, NomenclaturaDenominacionExt.class);
			NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
			List<NomenclaturaDenominacionExt> list = service.getCargoAprobarHojaVida(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (Exception e) {
			NomenclaturaDenominacionExt user = new NomenclaturaDenominacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<NomenclaturaDenominacionExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * 11 feb. 2019
	 * ServicesSigepRestEnt.java
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getEntidadesPersonaRol" ) 
	@Consumes("text/plain")
	public Response getEntidadesPersonaRol(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			EntidadExt objJson = gson.fromJson(json, EntidadExt.class);
			EntidadService service = new EntidadService();
			List<EntidadExt> list = service.getEntidadesPersonaRol(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (Exception e) {
			EntidadExt user = new EntidadExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<EntidadExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna las plantas a las cuales esta asociada una nomenclatura general
	 */
	@POST
	@Path ( "/getplantaasociadanomGeneral" ) 
	@Consumes("text/plain")
	public Response getPlantaAsociadaNomGeneral(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			NomenclaturaExt objJson = gson.fromJson(json, NomenclaturaExt.class);
			NomenclaturaService service = new NomenclaturaService();
			List<NomenclaturaExt> list = service.getPlantasAsociadasNomenclatura(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			NomenclaturaExt user = new NomenclaturaExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			List<NomenclaturaExt> list = new ArrayList<>();
			list.add(user);
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(list)).build();
		}
	}
	/**
	 * @param req
	 * @param json
	 * @param token
	 * @return
	 * Este Servicio retorna el nombre del nivel y grado de una denominacion seleccionada
	 */
	@POST
	@Path ( "/getcaracteristicasdenominacion" ) 
	@Consumes("text/plain")
	public Response getCaracteristicaDenominacion(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
		if(!UtilsConstantes.tokenIsValid(token,timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		try {
			NomenclaturaDenominacionExt objJson = gson.fromJson(json, NomenclaturaDenominacionExt.class);
			NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
			NomenclaturaDenominacionExt list = service.getCaracteristicaDenominacion(objJson);
			return Response.status(201).entity(gson.toJson(list)).build();
		} catch (JsonParseException e) {
			NomenclaturaDenominacionExt user = new NomenclaturaDenominacionExt();
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(e.getMessage());
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestVin.class);
			return Response.status(201).entity(gson.toJson(user)).build();
		}
	}
	
	
	 /**
     * @author: Maria Alejandra C
     * @fecha 02 Abril 2019
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de Vinculaciones
     */
    @POST
    @Path("/getvinculacionplanta")
    @Consumes("text/plain")
    public Response getVinculacionPlanta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<EntidadPlantaDetalleExt> err =  new ArrayList<>();
        try {
        	EntidadPlantaDetalleExt EntidadPlantaDetalleExt = gson.fromJson(json, EntidadPlantaDetalleExt.class);
        	EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
          	err = service.getVinculacion(EntidadPlantaDetalleExt);
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
           	EntidadPlantaDetalleExt error = new EntidadPlantaDetalleExt();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    
    /**
     * @author: Maria Alejandra C
     * @fecha 02 Abril 2019
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de Vinculaciones que estan asociadas a una denominacion general
     */
    @POST
    @Path("/getvinculaciondenominaciongeneral")
    @Consumes("text/plain")
    public Response getVinculacionDenominacionGeneral(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<EntidadPlantaDetalleExt> err =  new ArrayList<>();
        try {
        	EntidadPlantaDetalleExt EntidadPlantaDetalleExt = gson.fromJson(json, EntidadPlantaDetalleExt.class);
        	EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
          	err = service.getVinculacionDenominacionGeneral(EntidadPlantaDetalleExt);
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
           	EntidadPlantaDetalleExt error = new EntidadPlantaDetalleExt();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
     * @author: Maria Alejandra C
     * @fecha 02 Abril 2019
     * @param req
     * @param json
     * @param token
     * @return Este Servicio retorna lista de Vinculaciones que estan asociadas a una denominacion general
     */
    @POST
    @Path("/getplantadenominaciongeneral")
    @Consumes("text/plain")
    public Response getPlantaDenominacionGeneral(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
        if (!UtilsConstantes.tokenIsValid(token, timeout)) {
            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
        }
        json = new String(Base64.decode(json.getBytes()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
        gson = gsonBuilder.create();
        List<EntidadPlantaDetalleExt> err =  new ArrayList<>();
        try {
        	EntidadPlantaDetalleExt EntidadPlantaDetalleExt = gson.fromJson(json, EntidadPlantaDetalleExt.class);
        	EntidadPlantaDetalleService service = new EntidadPlantaDetalleService();
          	err = service.getPlantasDenominacionGeneral(EntidadPlantaDetalleExt);
            return Response.status(201).entity(gson.toJson(err)).build();
        } catch (JsonParseException e) {
           	EntidadPlantaDetalleExt error = new EntidadPlantaDetalleExt();
           	error.setError(true);
           	error.setMensaje(UtilsConstantes.MSG_EXEPCION);
           	error.setMensaje(e.getMessage());
           	err.add(error);
            return Response.status(201).entity(gson.toJson(err)).build();
        }
    }
    
    /**
     * @author: Maria C
     * @fecha 03 abril 2019
     * @param req
     * @param json
     * @param token
     */
	 	@POST
	    @Path("/setnomenclaturadenominacionasociada")
	    @Consumes("text/plain")
	    public Response setNomenclaturaDenominacionAsociada(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	       	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	            return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	        }
	    	json = new String(Base64.decode(json.getBytes()));
	        GsonBuilder gsonBuilder = new GsonBuilder();
	        gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	        gson = gsonBuilder.create();
	        NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
	        NomenclaturaDenominacion res = new NomenclaturaDenominacion();
	        try {
	        	NomenclaturaDenominacion nomenclatura = gson.fromJson(json, NomenclaturaDenominacion.class);
	            try {
	               if (nomenclatura.getAudAccion().equals(TipoParametro.AUDITORIA_UPDATE.getValue()) 
	            		   || nomenclatura.getAudAccion().equals(TipoParametro.AUDITORIA_DELETE.getValue()) ) {
	                    res = service.updateNomenclaturaDenominacionAsociada(nomenclatura);
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
	     * Elaborado por Maria Alejandra Colorado Rios
	     * Servicio que llama al procedimiento SP_NOMENCLATURA_UPDATE_EQUIVALENCIAS_GENERAL. Modulo de Nomenclatura general
	     * @fecha 09 de abril 2019
	     * @param req
	     * @param json
	     * @param token
	     */
		@POST
		@Path("/setnomenclaturaequivalenciageneral")
		@Consumes("text/plain")
		public Response setNomenclaturaEquivalenciaGeneral(@Context HttpServletRequest req, String json,
				@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			NomenclaturaExt res = new NomenclaturaExt();
			try {
				NomenclaturaExt nomenclatura = gson.fromJson(json, NomenclaturaExt.class);
				NomenclaturaService service = new NomenclaturaService();
				nomenclatura.setAudFechaActualizacion(new Date());
				res = service.updateNomenclaturaEquivalenciaGeneral(nomenclatura);
				return Response.status(201).entity(gson.toJson(res)).build();
			} catch (JsonParseException e) {
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
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
		 * @param timeout
		 * @return
		 */
		@POST
		@Path("/getentidadbysigep")
		@Consumes("text/plain")
		public Response getEntidadByCodigoSigep(@Context HttpServletRequest req, String json,
				@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				Entidad entidad = gson.fromJson(json, Entidad.class);
				EntidadService service = new EntidadService();
				List<Entidad> d = service.getEntidadByCodigoSigep(entidad.getCodigoSigep());
				String out = gson.toJson(d);
				return Response.status(201).entity(out).build();
			} catch (JsonParseException e) {
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestSis.class);
				return Response.status(201).entity(e.getMessage()).build();
			}
		}
		
		/**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Este Servicio retorna una lista de denominaciones de acuerdo a la nomenclatura y nivel enviado
		 */
		@POST
		@Path ( "/getdenominacionpornivelnomenclatura" ) 
		@Consumes("text/plain")
		public Response getDenominacionPorNivelNomenclatura(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				DenominacionExt objJson = gson.fromJson(json, DenominacionExt.class);
				DenominacionService service = new DenominacionService();
				List<DenominacionExt> list = service.getDenominacionPorNivelNomenclatura(objJson);
				return Response.status(201).entity(gson.toJson(list)).build();
			} catch (Exception e) {
				DenominacionExt user = new DenominacionExt();
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_EXEPCION);
				user.setMensajeTecnico(e.getMessage());
				List<DenominacionExt> list = new ArrayList<>();
				list.add(user);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
				return Response.status(201).entity(gson.toJson(list)).build();
			}
		}
		
		/**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Este Servicio retorna una lista de nomenclatura
		 */
		@POST
		@Path ( "/getniveljerarquicopornomenclatura" ) 
		@Consumes("text/plain")
		public Response getNivelJerarquicoPorNomenclatura(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				NomenclaturaDenominacionExt objJson = gson.fromJson(json, NomenclaturaDenominacionExt.class);
				NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
				List<NomenclaturaDenominacionExt> list = service.getNivelJerarquicoPorNomenclatura(objJson);
				return Response.status(201).entity(gson.toJson(list)).build();
			} catch (Exception e) {
				NomenclaturaDenominacionExt user = new NomenclaturaDenominacionExt();
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_EXEPCION);
				user.setMensajeTecnico(e.getMessage());
				List<NomenclaturaDenominacionExt> list = new ArrayList<>();
				list.add(user);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
				return Response.status(201).entity(gson.toJson(list)).build();
			}
		}
		/**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Este Servicio retorna una lista de nomenclatura
		 */
		@POST
		@Path ( "/getgradopordenominacionnivel" ) 
		@Consumes("text/plain")
		public Response getGradoPorDenominacionNivel(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				NomenclaturaDenominacionExt objJson = gson.fromJson(json, NomenclaturaDenominacionExt.class);
				NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
				List<NomenclaturaDenominacionExt> list = service.getGradoPorDenominacionNivel(objJson);
				return Response.status(201).entity(gson.toJson(list)).build();
			} catch (Exception e) {
				NomenclaturaDenominacionExt user = new NomenclaturaDenominacionExt();
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_EXEPCION);
				user.setMensajeTecnico(e.getMessage());
				List<NomenclaturaDenominacionExt> list = new ArrayList<>();
				list.add(user);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
				return Response.status(201).entity(gson.toJson(list)).build();
			}
		}
		
		/**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Este Servicio retorna una lista de nomenclatura
		 */
		@POST
		@Path ( "/getCodigoPorDenominacionNivel" ) 
		@Consumes("text/plain")
		public Response getCodigoPorDenominacionNivel(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				NomenclaturaDenominacionExt objJson = gson.fromJson(json, NomenclaturaDenominacionExt.class);
				NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
				List<NomenclaturaDenominacionExt> list = service.getCodigoPorDenominacionNivel(objJson);
				return Response.status(201).entity(gson.toJson(list)).build();
			} catch (Exception e) {
				NomenclaturaDenominacionExt user = new NomenclaturaDenominacionExt();
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_EXEPCION);
				user.setMensajeTecnico(e.getMessage());
				List<NomenclaturaDenominacionExt> list = new ArrayList<>();
				list.add(user);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
				return Response.status(201).entity(gson.toJson(list)).build();
			}
		}
		
		/**
		 * Elaborado por:
		 * Maria Alejandra Colorado
		 * @param req
		 * @param json
		 * @param token
		 * @param timeout
		 * @return List<PlantaPersonaUtlUanExt> 
		 * Servicio que retorna las personas que se encuentran vinculadas a una UTL especifica
		 */
		@POST
		@Path("/getHVpersonaasociadaUTL")
		@Consumes("text/plain")
		public Response getHVPersonaAsociadaUTL(@Context HttpServletRequest req, String json,
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
				asoc = service.getHVPersonaAsociadaUTL(user);
				String out = gson.toJson(asoc);
				return Response.status(201).entity(out).build();
			} catch (JsonParseException e) {
				return Response.status(201).entity(e.getMessage()).build();
			}
		}
		
		
	    /**
	     * @author Desarolladora Junior
	     * @fecha	10/01/2020
	     * @param req
	     * @param json
	     * @param token
	     * @return List<PlantaPersonaUtlUanExt> 
	     * Este servicio retorna las UTL a las cuales el tope maximo a cambiar afecta la cantidad de cargos disponibles
	     * debido a que este es mucho mayor.
	     */
	    @POST
	    @Path("/getexcedentebolsaUTL")
	    @Consumes("text/plain")
	    public Response getExcedenteBolsaUTL(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	List<PlantaPersonaUtlUanExt> err =  new ArrayList<>();
	    	try {
	    		PlantaPersonaUtlUanExt denominacion = gson.fromJson(json, PlantaPersonaUtlUanExt.class);
	    		PlantaPersonaUtlUanService service = new PlantaPersonaUtlUanService();
	    		err = service.getExcedenteBolsaUTL(denominacion);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	} catch (JsonParseException e) {
	    		err = new ArrayList<>();
	    		PlantaPersonaUtlUanExt er = new PlantaPersonaUtlUanExt();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		err.add(er);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	}
	    }
	    
	    
	    /**
	     * @author Desarolladora Junior
	     * @fecha	10/01/2020
	     * @param req
	     * @param json
	     * @param token
	     * @return List<PlantaPersonaUtlUanExt> 
	     * Este servicio retorna las UTL a las cuales el tope maximo a cambiar afecta la cantidad de cargos disponibles
	     * debido a que este es mucho mayor.
	     */
	    @POST
	    @Path("/getpersonasvinculadasUTL")
	    @Consumes("text/plain")
	    public Response getPersonasVinculadasUTL(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
	    	if (!UtilsConstantes.tokenIsValid(token, timeout)) {
	    		return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
	    	}
	    	json = new String(Base64.decode(json.getBytes()));
	    	GsonBuilder gsonBuilder = new GsonBuilder();
	    	gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
	    	gson = gsonBuilder.create();
	    	List<PlantaPersonaUtlUanExt> err =  new ArrayList<>();
	    	try {
	    		PlantaPersonaUtlUanExt denominacion = gson.fromJson(json, PlantaPersonaUtlUanExt.class);
	    		PlantaPersonaUtlUanService service = new PlantaPersonaUtlUanService();
	    		err = service.getPersonasVinculadasUTL(denominacion);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	} catch (JsonParseException e) {
	    		err = new ArrayList<>();
	    		PlantaPersonaUtlUanExt er = new PlantaPersonaUtlUanExt();
	    		er.setError(true);
	    		er.setMensaje(UtilsConstantes.MSG_EXEPCION);
	    		er.setMensaje(e.getMessage());
	    		err.add(er);
	    		return Response.status(201).entity(gson.toJson(err)).build();
	    	}
	    }
	    
	    
	    /**
	     * Elaborado por Maria Alejandra Colorado Rios
	     * Servicio que llama al procedimiento  F_ASOCIAR_NOMENCLATURA_ENTIDAD.
	     * Este procedimiento se encarga de asociar una nomenclatura general a una entidad
	     * @fecha 04/03/2021
	     * @param req
	     * @param json
	     * @param token
	     */
		@POST
		@Path("/setAsociarNomenclaturaGeneralAEntidad")
		@Consumes("text/plain")
		public Response setAsociarNomenclaturaGeneralAEntidad(@Context HttpServletRequest req, String json,
				@HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {
			if (!UtilsConstantes.tokenIsValid(token, timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			Nomenclatura res = new Nomenclatura();
			try {
				NomenclaturaExt nomenclatura = gson.fromJson(json, NomenclaturaExt.class);
				NomenclaturaService service = new NomenclaturaService();
				res = service.asociarNomenclaturaGeneralAEntidad(nomenclatura);
				return Response.status(201).entity(gson.toJson(res)).build();
			} catch (JsonParseException e) {
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
		 * @return
		 * Este Servicio retorna una lista de niveles jerarquicos de una nomenclatura por entidad
		 */
		@POST
		@Path ( "/getNivelJerarquicoXEntidad" ) 
		@Consumes("text/plain")
		public Response getNivelJerarquicoXEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				NomenclaturaDenominacionExt objJson = gson.fromJson(json, NomenclaturaDenominacionExt.class);
				NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
				List<NomenclaturaDenominacionExt> list = service.getNivelJerarquicoXEntidad(objJson);
				return Response.status(201).entity(gson.toJson(list)).build();
			} catch (Exception e) {
				NomenclaturaDenominacionExt user = new NomenclaturaDenominacionExt();
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_EXEPCION);
				user.setMensajeTecnico(e.getMessage());
				List<NomenclaturaDenominacionExt> list = new ArrayList<>();
				list.add(user);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
				return Response.status(201).entity(gson.toJson(list)).build();
			}
		}
		
		/**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Este Servicio retorna una lista de denominaciones de acuerdo a la entidad
		 */
		@POST
		@Path ( "/getDenominacionXEntidad" ) 
		@Consumes("text/plain")
		public Response getDenominacionXEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				DenominacionExt objJson 	= gson.fromJson(json, DenominacionExt.class);
				DenominacionService service = new DenominacionService();
				List<DenominacionExt> list 	= service.getDenominacionXEntidad(objJson);
				return Response.status(201).entity(gson.toJson(list)).build();
			} catch (Exception e) {
				DenominacionExt user = new DenominacionExt();
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_EXEPCION);
				user.setMensajeTecnico(e.getMessage());
				List<DenominacionExt> list = new ArrayList<>();
				list.add(user);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
				return Response.status(201).entity(gson.toJson(list)).build();
			}
		}
		
		/**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Este Servicio retorna una lista de codigos Denominaciones
		 */
		@POST
		@Path ( "/getCodigoDenominacionXEntidad" ) 
		@Consumes("text/plain")
		public Response getCodigoDenominacionXEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				NomenclaturaDenominacionExt objJson 	= gson.fromJson(json, NomenclaturaDenominacionExt.class);
				NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
				List<NomenclaturaDenominacionExt> list 	= service.getCodigoDenominacionXEntidad(objJson);
				return Response.status(201).entity(gson.toJson(list)).build();
			} catch (Exception e) {
				NomenclaturaDenominacionExt user = new NomenclaturaDenominacionExt();
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_EXEPCION);
				user.setMensajeTecnico(e.getMessage());
				List<NomenclaturaDenominacionExt> list = new ArrayList<>();
				list.add(user);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
				return Response.status(201).entity(gson.toJson(list)).build();
			}
		}
		
		/**
		 * @param req
		 * @param json
		 * @param token
		 * @return
		 * Este Servicio retorna una lista de grados
		 */
		@POST
		@Path ( "/getGradoDenominacionXEntidad" ) 
		@Consumes("text/plain")
		public Response getGradoDenominacionXEntidad(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
			if(!UtilsConstantes.tokenIsValid(token,timeout)) {
				return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
			}
			json = new String(Base64.decode(json.getBytes()));
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
			gson = gsonBuilder.create();
			try {
				NomenclaturaDenominacionExt objJson 	= gson.fromJson(json, NomenclaturaDenominacionExt.class);
				NomenclaturaDenominacionService service = new NomenclaturaDenominacionService();
				List<NomenclaturaDenominacionExt> list 	= service.getGradoDenominacionXEntidad(objJson);
				return Response.status(201).entity(gson.toJson(list)).build();
			} catch (Exception e) {
				NomenclaturaDenominacionExt user = new NomenclaturaDenominacionExt();
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_EXEPCION);
				user.setMensajeTecnico(e.getMessage());
				List<NomenclaturaDenominacionExt> list = new ArrayList<>();
				list.add(user);
				LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestEnt.class);
				return Response.status(201).entity(gson.toJson(list)).build();
			}
		}
		
}
