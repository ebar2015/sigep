package co.gov.dafp.sigep2.server;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
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

import co.gov.dafp.sigep2.bean.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.bean.ColumnaTablaGestionInformacion;
import co.gov.dafp.sigep2.bean.Recurso;
import co.gov.dafp.sigep2.bean.SIGEPEnCifras;
import co.gov.dafp.sigep2.bean.TablaGestionInformacion;
import co.gov.dafp.sigep2.bean.ext.VinculacionExt;
import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.services.CifrasEmpleoPublicoServices;
import co.gov.dafp.sigep2.services.RecursoService;
import co.gov.dafp.sigep2.services.VinculacionService;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormaConsulta;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

@Path("/sigepgi")
public class ServicesSigepRestGI implements Serializable {
	private static final long serialVersionUID = 1461649534333319589L;
	public Gson gson = new Gson();
	/**
	 * WS 000 Servicio para test de disponibilidad del servicio
	 * 
	 * @param httpServletRequest
	 *            solicitud de servicio
	 * @param msg
	 *            texto de prueba
	 * 
	 * @return {@link Response}
	 */
	
	
	@GET()
	@Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@Context HttpServletRequest httpServletRequest, @PathParam("msg") String msg) {
		return Response.status(201).entity("Hello: Services Context sigepGI... " + msg).build();
	}

	/**
	 * WS 001 Devuelve el listado de registros de cifras de empleo publico de
	 * acuerdo a los filtros definidos
	 * 
	 * @param httpServletRequest
	 *            solicitud de servicio
	 * @param json
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros *
	 * @return {@link Response} Contiene {@link List} de
	 *         {@link CifrasEmpleoPublico} Registros seleccionados
	 */
	@POST
	@Path("/tablero/cep")
	@Produces("text/plain")
	public Response getCifrasEmpleoPublico(@Context HttpServletRequest httpServletRequest, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		List<CifrasEmpleoPublico> listado = new LinkedList<>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		Gson gson = gsonBuilder.create();

		String jsonTemp = new String(Base64.decode(json.getBytes()));

		try {
			CifrasEmpleoPublico parametros = gson.fromJson(jsonTemp, CifrasEmpleoPublico.class);
			CifrasEmpleoPublicoServices service = new CifrasEmpleoPublicoServices();
			listado = service.cifrasEmpleoPublico(parametros);
		} catch (JsonParseException e) {
			CifrasEmpleoPublico cifrasEmpleoPublicoResponse = new CifrasEmpleoPublico();
			cifrasEmpleoPublicoResponse.setCodigoEstado(500);
			listado.add(cifrasEmpleoPublicoResponse);
		}

		String out = gson.toJson(listado);
		return Response.status(201).entity(out).build();

	}

	/**
	 * WS 002 Devuelve el totalizado de los registros de cifras de empleo
	 * publico de acuerdo a los filtros definidos
	 * 
	 * @param httpServletRequest
	 *            solicitud de servicio
	 * @param json
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros para el totalizado
	 * 
	 * @return {@link Response} Contiene {@link CifrasEmpleoPublico} Totalizado
	 *         de los registros seleccionados
	 */
	@POST
	@Path("/tablero/totalcep")
	@Produces("text/plain")
	public Response getTotalCifrasEmpleoPublico(@Context HttpServletRequest httpServletRequest, String json,  @HeaderParam("token") String token, @HeaderParam("timeout") String timeout) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		CifrasEmpleoPublico cifrasEmpleoPublicoResponse = new CifrasEmpleoPublico();

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		Gson gson = gsonBuilder.create();

		String jsonTemp = new String(Base64.decode(json.getBytes()));

		try {
			CifrasEmpleoPublico parametros = gson.fromJson(jsonTemp, CifrasEmpleoPublico.class);
			CifrasEmpleoPublicoServices service = new CifrasEmpleoPublicoServices();
			cifrasEmpleoPublicoResponse = service.getTotalCifrasEmpleoPublico(parametros);
		} catch (JsonParseException e) {
			cifrasEmpleoPublicoResponse.setCodigoEstado(500);
		}

		String out = gson.toJson(cifrasEmpleoPublicoResponse);
		return Response.status(201).entity(out).build();

	}

	/**
	 * WS 003 Devuelve las tablas y vistas del sistema para configuracion de los
	 * reportes en gestion de la informacion
	 * 
	 * @param httpServletRequest
	 *            solicitud de servicio
	 * @param json
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros para el totalizado
	 * 
	 * @return {@link Response} Contiene {@link TablaGestionInformacion} Tablas
	 *         del sistema
	 */
	@POST
	@Path("/tablero/tablasgi")
	@Produces("text/plain")
	public Response tablasGestionInformacion(@Context HttpServletRequest httpServletRequest,  @HeaderParam("token") String token, @HeaderParam("timeout") String timeout) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		List<TablaGestionInformacion> listado = new LinkedList<>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		Gson gson = gsonBuilder.create();

		try {
			CifrasEmpleoPublicoServices service = new CifrasEmpleoPublicoServices();
			listado = service.tablasGestionInformacion();
		} catch (JsonParseException e) {
			TablaGestionInformacion cifrasEmpleoPublicoResponse = new TablaGestionInformacion();
			cifrasEmpleoPublicoResponse.setCodigoEstado(500);
			listado.add(cifrasEmpleoPublicoResponse);
		}

		String out = gson.toJson(listado);
		return Response.status(201).entity(out).build();
	}

	/**
	 * WS 004 Devuelve el listado de columnas de la tabla filtrada
	 * 
	 * @param httpServletRequest
	 *            solicitud de servicio
	 * @param json
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            columnas de la tabla en filtro
	 * 
	 * @return {@link Response} Contiene {@link ColumnaTablaGestionInformacion}
	 *         Registros seleccionados
	 */
	@POST
	@Path("/tablero/columnastablasgi")
	@Produces("text/plain")
	public Response columnasTablasGestionInformacion(@Context HttpServletRequest httpServletRequest, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout ) {
		if (!UtilsConstantes.tokenIsValid(token, timeout)) {
			return Response.status(401).entity(UtilsConstantes.MSG_ERROR_TOKEN).build();
		}
		List<ColumnaTablaGestionInformacion> listado = new LinkedList<>();

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		Gson gson = gsonBuilder.create();

		String jsonTemp = new String(Base64.decode(json.getBytes()));

		try {
			TablaGestionInformacion tablaGestionInformacion = gson.fromJson(jsonTemp, TablaGestionInformacion.class);
			CifrasEmpleoPublicoServices service = new CifrasEmpleoPublicoServices();
			listado = service.columnasTablasGestionInformacion(tablaGestionInformacion);
		} catch (JsonParseException e) {
			ColumnaTablaGestionInformacion cifrasEmpleoPublicoResponse = new ColumnaTablaGestionInformacion();
			cifrasEmpleoPublicoResponse.setCodigoEstado(500);
			listado.add(cifrasEmpleoPublicoResponse);
		}

		String out = gson.toJson(listado);
		return Response.status(201).entity(out).build();
	}

	/**
	 * WS 005 Devuelve el listado de reportes publicados en el portal. Este
	 * servicio sera consumido de forma exclusiva por el portal web para la
	 * implementacion del menu de reportes publicados para dicho sitio web
	 * 
	 * @param httpServletRequest
	 *            solicitud de servicio
	 * 
	 * @return {@link Response} Contiene {@link SIGEPEnCifras} Registros
	 *         seleccionados
	 */
	@POST
	@Path("/sigepencifras")
	@Produces("text/plain")
	public Response getReportesPublicadosPortal(@Context HttpServletRequest httpServletRequest) {
		List<SIGEPEnCifras> listado = new LinkedList<>();

		 gson = new GsonBuilder().setDateFormat(UtilsConstantes.FORMATO_FECHA).create();

		try {
			RecursoService recursoService = new RecursoService();
			List<XmlReporte> plantillas = new LinkedList<>();
			String ruta = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
					+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_PORTAL);
			String rutaReporte = ConfigurationBundleConstants.getPrefijoApp();

			if (rutaReporte.endsWith("/")) {
				rutaReporte = rutaReporte.substring(0, rutaReporte.lastIndexOf("/"));
			}

			Recurso recursoReporte = recursoService.getRecursoPorCodVentana("ReporteCatalogoTag");
			rutaReporte = rutaReporte + recursoReporte.getPagina().replace("consult.xhtml", "sigepencifras.xhtml")
					+ "?recursoId=ReporteSIGEPCifrasTag&id=ID_REPORTE#no-back-button";

			File carpetaPlantillas = new File(ruta);
			if (carpetaPlantillas.exists()) {
				for (File nombrePlantilla : carpetaPlantillas.listFiles()) {
					if (!nombrePlantilla.isDirectory() && nombrePlantilla.getPath().endsWith(FileUtil.XML)) {
						try {
							XmlReporte plantilla = getPlantilla(nombrePlantilla.getName(), ConfigurationBundleConstants
									.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_PORTAL));
							if (!plantilla.isEliminado() && FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_PORTAL
									.equals(plantilla.getFormaConsulta())) {
								SIGEPEnCifras sigepEnCifras = new SIGEPEnCifras();
								sigepEnCifras.setId(plantilla.getId());
								sigepEnCifras.setNombre(plantilla.getNombre());
								sigepEnCifras.setAcceso(rutaReporte.replace("ID_REPORTE", plantilla.getId().toString()));
								listado.add(sigepEnCifras);
							}
						} catch (Exception e) {
						}
					}
				}
			}

		} catch (Exception e) {
			SIGEPEnCifras sigepEnCifras = new SIGEPEnCifras();
			sigepEnCifras.setCodigoEstado(500);
			listado.add(sigepEnCifras);
		}
		
		String out = listado.toString();
		return Response.status(201).entity(out).build();
	}

	/**
	 * WS 005 Devuelve el listado de registros de tabla de resultados para
	 * cifras de empleo publico de acuerdo a los filtros definidos
	 * 
	 * @param httpServletRequest
	 *            solicitud de servicio
	 * @param json
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros *
	 * @return {@link Response} Contiene {@link List} de
	 *         {@link CifrasEmpleoPublico} Registros seleccionados
	 */
	@POST
	@Path("/tablero/tablaresultados")
	@Produces("text/plain")
	public Response getTablaResultadosCifrasEmpleoPublico(@Context HttpServletRequest httpServletRequest, String json) {
		List<CifrasEmpleoPublico> listado = new LinkedList<>();

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		Gson gson = gsonBuilder.create();

		String jsonTemp = new String(Base64.decode(json.getBytes()));

		try {
			CifrasEmpleoPublico parametros = gson.fromJson(jsonTemp, CifrasEmpleoPublico.class);
			CifrasEmpleoPublicoServices service = new CifrasEmpleoPublicoServices();
			listado = service.tablaResultadoCifrasEmpleoPublico(parametros);
		} catch (JsonParseException e) {
			CifrasEmpleoPublico cifrasEmpleoPublicoResponse = new CifrasEmpleoPublico();
			cifrasEmpleoPublicoResponse.setCodigoEstado(500);
			listado.add(cifrasEmpleoPublicoResponse);
		}

		String out = gson.toJson(listado);
		return Response.status(201).entity(out).build();
	}

	public static XmlReporte getPlantilla(String nombrePlantilla, String subcarpeta) throws Exception {
		return XmlReporte.getEstructura(XmlReporte.getXml(nombrePlantilla, subcarpeta));
		
	}
	
	
	/**
	 * 
	 * Elaborado por Maria Alejandra C
	 * @fecha 10/01/2020
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getvinculacionalerta" ) 
	@Consumes("text/plain")
	public Response getVinculacionAlerta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
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
			List<VinculacionExt> list = service.getVinculacionAlerta(objJson);
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
	 * Elaborado por Maria Alejandra C
	 * @fecha 10/01/2020
	 * @param req
	 * @param json
	 * @param token
	 * @param timeout
	 * @return
	 */
	@POST
	@Path ( "/getsituacionesadminalerta" ) 
	@Consumes("text/plain")
	public Response getSituacionesAdminAlerta(@Context HttpServletRequest req, String json, @HeaderParam("token") String token, @HeaderParam("timeout") String timeout, @HeaderParam("auditJson") String auditJson) {  
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
			List<VinculacionExt> list = service.getSituacionesAdminAlerta(objJson);
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
	
	
}
