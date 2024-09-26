/**
 * 
 */
package co.gov.dafp.sigep2.servicios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entities.Cargo;
import co.gov.dafp.sigep2.entities.EntidadPlantaDetalle;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.SituacionAdminVinculacion;
import co.gov.dafp.sigep2.entities.UsuarioEntidad;
import co.gov.dafp.sigep2.entities.Vinculacion;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadCargoExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.rest.ErrorMensajes;
import co.gov.dafp.sigep2.rest.SerivesRestURL;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * @author Milciades Vargas Liz
 * @version 5.0.4
 * @Class clase para manejar los servicios del modulo de vinculacion
 * @Proyect DAFP
 * @Company ADA S.A
 * @Module CU030201 VINCULAR SERVIDOR PÚBLICO Fecha: 03/07/2018
 */
public class ComunicacionServiciosVin implements Serializable {

	private static Gson gson;
	private static String token;
	private static AuditoriaSeguridad auditoriaSeguridad;

	private static final long serialVersionUID = 9143774434283059779L;
	private static final long TIME_OUT = ConfigurationBundleConstants.getTimeOutConversation();
	private static final Logger logger = Logger.getInstance(ComunicacionServiciosVin.class);
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss.s";

	/**
	 * @return the auditoriaSeguridad
	 */
	public static AuditoriaSeguridad getAuditoriaSeguridad() {
		return auditoriaSeguridad;
	}

	/**
	 * @param auditoriaSeguridad
	 *            the auditoriaSeguridad to set
	 */
	public static void setAuditoriaSeguridad(AuditoriaSeguridad auditoriaSeguridad) {
		ComunicacionServiciosVin.auditoriaSeguridad = auditoriaSeguridad;
	}

	/**
	 * Metodo para retornar los datos de EntidadCargo por medio del id
	 * 
	 * @param id
	 * @return EntidadCargoExt
	 */
	public static EntidadCargoExt getEntidadCargoId(long codCargoEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codEntidadCargo\":" + codCargoEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_CARGO_BY_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			EntidadCargoExt entidadCargoExt = gson.fromJson(out, EntidadCargoExt.class);
			invalidToken(out);
			return entidadCargoExt;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getEntidadCargoId(long codCargoEntidad)", e);
			return new EntidadCargoExt();
		}
	}

	/**
	 * Metodo para retornar una lista de datos de EntidadCargo por filtro
	 * 
	 * @param EntidadCargoExt
	 * @return List<EntidadCargoExt>
	 */
	public static List<EntidadCargoExt> getEntidadCargoFilter(EntidadCargoExt entidadCargoFilter) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA_HORA).create();
		try {
			String json = gson.toJson(entidadCargoFilter);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_CARGO_BY_FILTER, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadCargoExt>>() {
			}.getType();
			List<EntidadCargoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getEntidadCargo(EntidadCargoExt entidadCargoFilter)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para registrar en la tabla EntidadCargo
	 * 
	 * @param entidadCargo
	 * @return boolean
	 */
	public static boolean setEntidadCargo(EntidadCargoExt entidadCargo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA_HORA).create();
		try {
			String json = gson.toJson(entidadCargo);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_CARGO, json, token, TIME_OUT,
					auditoriaSeguridad);
			EntidadCargoExt pre = gson.fromJson(out, EntidadCargoExt.class);
			if (pre.isError()) {
				logger.info("El servicio no inserto el dato en EntidadCargo", pre.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("El servicio no inserto los datos - setEntidadCargo(EntidadCargoExt entidadCargo)", e);
			return false;
		}
	}

	/**
	 * Metodo para retornar los datos de EntidadPlantaDetalle por medio del id
	 * 
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 */
	public static EntidadPlantaDetalleExt getEntidadPlantaDetalleId(long codEntidadPlantaDetalle) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codEntidadPlantaDetalle\":" + codEntidadPlantaDetalle + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PLANTA_DETALLE_BY_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			EntidadPlantaDetalleExt EntidadPlantaDetalleExt = gson.fromJson(out, EntidadPlantaDetalleExt.class);
			invalidToken(out);
			return EntidadPlantaDetalleExt;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getEntidadPlantaDetalleId(long codEntidadPlantaDetalle)", e);
			return new EntidadPlantaDetalleExt();
		}
	}

	
	
	
	
	
	/**
	 * Metodo para retornar los cargos vacantes
	 * 
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 * @author Nestor Riasco
	 */
	public static List<EntidadPlantaDetalleExt> getvacantes(long codEntidad, long codClasificacionPlanta,
			long codClasePlanta, long codNaturalezaEmpleo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codClasificacionPlanta\":" + codClasificacionPlanta + ",\"codClasePlanta\":"
					+ codClasePlanta + ",\"codEntidad\":" + codEntidad + ",\"codNaturalezaEmpleo\":"
					+ codNaturalezaEmpleo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VACANTES, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getvacantes(long codEntidad, long codClasificacionPlanta, long codClasePlanta, long codNaturalezaEmpleo)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar los cargos de vacancia temporal, tipo planta global
	 * 
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 * @author Nestor Riasco
	 */
	public static List<EntidadPlantaDetalleExt> getvacantesTempGlobal(long codEntidad, long codClasificacionPlanta,
			long codClasePlanta, long codNaturalezaEmpleo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codClasificacionPlanta\":" + codClasificacionPlanta + ",\"codClasePlanta\":"
					+ codClasePlanta + ",\"codEntidad\":" + codEntidad + ",\"codNaturalezaEmpleo\":"
					+ codNaturalezaEmpleo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VACANTES_TEMP_GLOBAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getvacantesTempGlobal(long codEntidad, long codClasificacionPlanta, long codClasePlanta, long codNaturalezaEmpleo)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar los cargos de vacancia temporal y Permanetes, tipo
	 * planta global
	 * 
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 * @author Nestor Riasco
	 */
	public static List<EntidadPlantaDetalleExt> getvacantesTempPermGlobal(long codEntidad, long codClasificacionPlanta,
			long codClasePlanta, long codNaturalezaEmpleo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codClasificacionPlanta\":" + codClasificacionPlanta + ",\"codClasePlanta\":"
					+ codClasePlanta + ",\"codEntidad\":" + codEntidad + ",\"codNaturalezaEmpleo\":"
					+ codNaturalezaEmpleo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VACANTES_TEMP_PERM_GLOBAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getvacantesTempPermGlobal(long codEntidad, long codClasificacionPlanta, long codClasePlanta, long codNaturalezaEmpleo)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar los cargos de vacancia temporal y Permanetes, tipo
	 * planta global
	 * 
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 * @author Nestor Riasco
	 */
	public static List<EntidadPlantaDetalleExt> getcargosfuncionario(long codPersona, long codVinculacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona + ",\"codVinculacion\":" + codVinculacion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_FUNCIONARIO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getcargosfuncionario(long codPersona)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar los cargos Permanetes, tipo planta global
	 * 
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 * @author Nestor Riasco
	 */
	public static List<EntidadPlantaDetalleExt> getvacantesGlobal(long codEntidad, long codClasificacionPlanta,
			long codClasePlanta, long codNaturalezaEmpleo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codClasificacionPlanta\":" + codClasificacionPlanta + ",\"codClasePlanta\":"
					+ codClasePlanta + ",\"codEntidad\":" + codEntidad + ",\"codNaturalezaEmpleo\":"
					+ codNaturalezaEmpleo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VACANTES_GLOBAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getvacantesGlobal(long codEntidad, long codClasificacionPlanta, long codClasePlanta, long codNaturalezaEmpleo)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para registrar en la tabla EntidadPlantaDetalle
	 * 
	 * @param EntidadPlantaDetalle
	 * @return boolean
	 */
	public static boolean setEntidadPlantaDetalle(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlantaDetalle);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_PLANTA_DETALLE, json, token, TIME_OUT,
					auditoriaSeguridad);
			EntidadPlantaDetalleExt pre = gson.fromJson(out, EntidadPlantaDetalleExt.class);
			if (pre.isError()) {
				logger.info("El servicio no inserto el dato en EntidadPlantaDetalle", pre.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error(
					"El servicio no inserto los datos - setEntidadPlantaDetalle(EntidadPlantaDetalleExt EntidadPlantaDetalle)",
					e);
			return false;
		}
	}

	/**
	 * Metodo para retornar una lista de datos de EntidadPlantaDetalle por filtro
	 * 
	 * @param EntidadPlantaDetalleExt
	 * @return List<EntidadPlantaDetalleExt>
	 */
	public static List<EntidadPlantaDetalleExt> getEntidadPlantaDetalleFilter(
			EntidadPlantaDetalleExt EntidadPlantaDetalleFilter) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlantaDetalleFilter);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PLANTA_DETALLE_BY_FILTER, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getEntidadPlantaDetalle(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar una lista de las clasificaciones de planta por entidad
	 * desde EntidadPlanta
	 * 
	 * @param EntidadPlantaExt
	 * @return List<EntidadPlantaExt>
	 */
	public static List<EntidadPlantaExt> getClasificacionPlantaByEntidad(EntidadPlantaExt EntidadPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlanta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CLASIFICACION_PLANTA_BY_ENTIDAD, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaExt>>() {
			}.getType();
			List<EntidadPlantaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getClasificacionPlantaByEntidad(EntidadPlantaExt EntidadPlanta)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar una lista de las clasificaciones de planta por entidad
	 * desde EntidadPlanta
	 * 
	 * @param EntidadPlantaExt
	 * @return List<EntidadPlantaExt>
	 */
	public static List<EntidadPlantaExt> getClasePlantaByEntidad(EntidadPlantaExt EntidadPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlanta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CLASE_PLANTA_BY_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaExt>>() {
			}.getType();
			List<EntidadPlantaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getClasePlantaByEntidad(EntidadPlantaExt EntidadPlanta)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar una lista de datos de EntidadPlantaDetalle por filtro
	 * 
	 * @param EntidadPlantaDetalleExt
	 * @return List<EntidadPlantaDetalleExt>
	 */
	public static List<EntidadPlantaDetalleExt> getCargoPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlantaDetalleFilter);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_PLANTA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getCargoPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter)",
					e);
			return new ArrayList<>();
		}
	}

	
	/**
	 * Metodo para retornar una lista de datos de EntidadPlantaDetalle por filtro
	 * 
	 * @param EntidadPlantaDetalleExt
	 * @return List<EntidadPlantaDetalleExt>
	 */
	public static List<EntidadPlantaDetalleExt> getCargosPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlantaDetalleFilter);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_PLANTA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getCargosPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter)",
					e);
			return new ArrayList<>();
		}
	}
	
	
	
	/**
	 * Metodo para Retornar La Naturaleza Empleo por Entidad
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 */
	public static List<EntidadPlantaDetalleExt>  getNaturalezaEmpleo(EntidadPlantaDetalleExt Entidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(Entidad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NATURALEZA_EMPLEO_BY_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getNaturalezaEnmpleo(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter)",
					e);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Metodo para retornar una lista de datos de EntidadPlantaDetalle por filtro
	 * 
	 * @param EntidadPlantaDetalleExt
	 * @return List<EntidadPlantaDetalleExt>
	 */
	public static List<EntidadPlantaDetalleExt> getCodigosCargos(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlantaDetalleFilter);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CODIGOS_CARGO_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getCodigosCargo(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter)",
					e);
			return new ArrayList<>();
		}
	}
	/**
	 * Metodo para retornar una lista de datos de DependenciaEntidad por filtro
	 * 
	 * @param DependenciaEntidadExt
	 * @return List<DependenciaEntidadExt>
	 */
	public static List<DependenciaEntidadExt> getDependenciaEntidadFilter(DependenciaEntidadExt dependenciaEntidadExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(dependenciaEntidadExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPENDENCIA_ENTIDAD_FILTER, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<DependenciaEntidadExt>>() {
			}.getType();
			List<DependenciaEntidadExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getDependenciaEntidadFilter(DependenciaEntidadExt dependenciaEntidadExt)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar una lista de datos de Vinculacion por filtro
	 * 
	 * @param VinculacionExt
	 * @return List<VinculacionExt>
	 */
	public static List<VinculacionExt> getVinculacionFilter(VinculacionExt vinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_BY_FILTER, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getVinculacionFilter(VinculacionExt vinculacionExt)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar una lista de datos de EntidadPlanta por filtro
	 * 
	 * @param EntidadPlanta
	 * @return List<EntidadPlantaExt>
	 */
	public static List<EntidadPlantaExt> getEntidadPlantaFilter(EntidadPlantaExt EntidadPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlanta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PLANTA_BY_FILTER, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaExt>>() {
			}.getType();
			List<EntidadPlantaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getEntidadPlantaFilter(EntidadPlantaExt EntidadPlanta)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar la vinculacion de un usuario que ya este vinculado
	 * 
	 * @param VinculacionExt
	 * @return List<VinculacionExt>
	 */
	public static List<VinculacionExt> getVinculacionDiferenteEntidad(VinculacionExt vinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_DIFERENTE_ENTIDAD, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getVinculacionDiferenteEntidad(VinculacionExt vinculacionExt)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * @param VinculacionExt
	 * @return Metodo inserta una registro en la base de datos de una nueva
	 *         vinculacion
	 */
	public static boolean setVinculacion(VinculacionExt vinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_VINCULACION, json, token, TIME_OUT,
					auditoriaSeguridad);
			Vinculacion pre = gson.fromJson(out, Vinculacion.class);
			if (pre.isError()) {
				logger.error("El servicio no guardo datos - setVinculacion(VinculacionExt vinculacionExt)",
						pre.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("El servicio no guardo datos - setVinculacion(VinculacionExt vinculacionExt)", e);
			return false;
		}
	}

	/**
	 * Metodo para validar si la persona tiene un rol activo en la entidad
	 * 
	 * @param persona
	 * @return
	 */
	public static List<PersonaExt> getPersonaRolActivoEntidad(PersonaExt persona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(persona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_ROL_ACTIVO_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			logger.info("El servicio no encontro datos - getPersonaRolActivoEntidad(PersonaExt persona)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para validar si la persona tiene aprobada la hoja de vida y no se pasa
	 * del tiempo parametrisable
	 * 
	 * @param persona
	 * @return
	 */
	public static List<HojaVidaExt> getPersonaHojaVidaAprobada(HojaVidaExt hojaVidaExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(hojaVidaExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_HOJAVIDA_APROBADA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<HojaVidaExt>>() {
			}.getType();
			List<HojaVidaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			logger.info("El servicio no encontro datos - getPersonaHojaVidaAprobada(HojaVidaExt hojaVidaExt)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para generar un id token
	 */
	private static void getToken() {
		token = "";
		auditoriaSeguridad = new AuditoriaSeguridad();
		try {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			boolean stado = (boolean) contexto.getSessionMap().get("sesionValida");
			UsuarioDTO usuarioDTO = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
			if (stado) {
				token = (String) contexto.getSessionMap().get("token");
			} else {
				token = "";
			}
			auditoriaSeguridad.setCodUsuario(new BigDecimal(usuarioDTO.getId()));
			auditoriaSeguridad.setCodUsuarioRol(new BigDecimal(usuarioDTO.getCodRol()));
		} catch (NullPointerException e) {
			token = "";
			auditoriaSeguridad.setCodUsuario(new BigDecimal(0));
			auditoriaSeguridad.setCodUsuarioRol(new BigDecimal(0));
		}
	}

	/**
	 * Metodo para invalidar el token
	 * 
	 * @param out
	 */
	private static void invalidToken(String out) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			ErrorMensajes err = gson.fromJson(out, ErrorMensajes.class);
			if (err.getCodigo() == 101) {
				FacesContext context = FacesContext.getCurrentInstance();
				ExternalContext externalContext = context.getExternalContext();
				externalContext.getSessionMap().put("sesionValida", false);
				externalContext.getSessionMap().remove("token");
				Object session = externalContext.getSession(false);
				HttpSession httpSession = (HttpSession) session;
				httpSession.invalidate();
			}
		} catch (JsonSyntaxException e) {
			// logger.info("invalidToken(String out)", e);
		}
	}

	/**
	 * Metodo para consultar Parametrica por el id
	 * 
	 * @param Parametrica
	 */
	public static Parametrica getParametricaporId(BigDecimal id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codTablaParametrica\":\"" + id + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRICA_POR_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			Parametrica list = gson.fromJson(out, Parametrica.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getParametricaporId(BigDecimal id)", e);
			return null;
		}
	}

	/**
	 * Metodo para traer los datos de una vinculacion a exportar
	 * 
	 * @param filtroBusqueda
	 * @return List<VinculacionExt>
	 */
	public static List<VinculacionExt> getVinculacionExportacion(VinculacionExt filtroBusqueda) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(filtroBusqueda);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_EXP, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getVinculacionExportacion(VinculacionExt filtroBusqueda)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para mostrar las vinculaciones de un usuario
	 * 
	 * @param filtroBusqueda
	 * @return
	 */
	public static List<VinculacionExt> getMostrarVinculaciones(VinculacionExt filtroBusqueda) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(filtroBusqueda);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MOSTRAR_VINCULACIONES, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getMostrarVinculaciones(VinculacionExt filtroBusqueda)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para mostrar las vinculaciones que tengan una situacion administrativa
	 * con comision/encargo
	 * 
	 * @param filtroBusqueda
	 * @return
	 */
	public static List<VinculacionExt> getVinculacionSituacionAdmin(VinculacionExt filtroBusqueda) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(filtroBusqueda);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_SITUACION_ADMIN, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getVinculacionSituacionAdmin(VinculacionExt filtroBusqueda)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para mostrar las vinculaciones que tengan una situacion administrativa
	 * con Periodo de Prueba
	 * 
	 * @param filtroBusqueda
	 * @return
	 */
	public static List<VinculacionExt> getVinculacionSituacionAdminPeriodoPrueba(VinculacionExt filtroBusqueda) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(filtroBusqueda);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_SITUACION_ADMIN_PERIODO_PRUEBA, json,
					token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getVinculacionSituacionAdminPeriodoPrueba(VinculacionExt filtroBusqueda)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para obtener la vinculacion con tipo de nombramiento periodo de prueba
	 * persona
	 * 
	 * @param vinculacionPeriodoDeprueba
	 * @return
	 */
	public static List<VinculacionExt> getVinculacionPeriodoDePrueba(VinculacionExt vinculacionActual) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionActual);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_PERIODO_PRUEBA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getvinculacionperiododeprueba(VinculacionExt vinculacionActual)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar los datos de Vinculacion por medio del id
	 * 
	 * @param id
	 * @return VinculacionExt
	 */
	public static VinculacionExt getVinculacionById(BigDecimal codVinculacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codVinculacion\":" + codVinculacion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_BY_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			VinculacionExt objEntidad = gson.fromJson(out, VinculacionExt.class);
			invalidToken(out);
			return objEntidad;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getVinculacionId(long codVinculacion)", e);
			return new VinculacionExt();
		}
	}

	/**
	 * Metodo para obtener los correos de las personas para enviar email de
	 * desvincular
	 * 
	 * @param filtroBusqueda
	 * @return
	 */
	public static List<PersonaExt> getEmailPersonasDesvincular(long codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codEntidad\":" + codEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EMAIL_PERSONAS_DESVINCULAR, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getEmailPersonasDesvincular(PersonaExt personaExt)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para obtener la vinculacion actual de una persona
	 * 
	 * @param vinculacionActual
	 * @return
	 */
	public static List<VinculacionExt> getvinculacionactual(VinculacionExt vinculacionActual) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionActual);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_ACTUAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getvinculacionactual(VinculacionExt vinculacionActual)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo que trae todas las vinculaciones o desvinculaciones de una persona
	 * ordenadas por la ultima modificacion hecha. Recibe el codPersona y codEntidad
	 * 
	 * @param filtBusqueda
	 * @return List<VinculacionExt>
	 */
	public static List<VinculacionExt> getUltimaVinculacionDesv(VinculacionExt filtBusqueda) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(filtBusqueda);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ULTIMA_VINCULACION_DESVINCULACION, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getvinculacionactual(VinculacionExt vinculacionActual)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo que asigna el rol de servidor publico a una persona que antes era
	 * contratista
	 * 
	 * @param usuarioRolEntidad
	 * @return boolean
	 */
	public static boolean desactivarRolContratista(UsuarioRolEntidadExt usuarioRolEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(usuarioRolEntidad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DESACTIVAR_ROLES_CONTRATISTA, json, token, TIME_OUT,
					auditoriaSeguridad);
			UsuarioRolEntidadExt pre = gson.fromJson(out, UsuarioRolEntidadExt.class);
			if (pre.isError()) {
				logger.error(
						"El servicio no guardo datos - desactivarRolContratista(UsuarioRolEntidadExt usuarioRolEntidad)",
						pre.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error(
					"El servicio no guardo datos - desactivarRolContratista(UsuarioRolEntidadExt usuarioRolEntidad)",
					e);
			return false;
		}
	}

	/**
	 * SERWEB000158
	 * 
	 * @param personaExt
	 * @return Metodo que consume el servicio de trae una lista de Objetos
	 *         PersonaExt, mediante un filtro de tipoIdentificacion, numero
	 *         Identificacion primer nombre, segundo nombre, primer apellido,
	 *         segundo apellido, codigo entidad, el servicio recibe cualquiera de
	 *         estos atributos se pueden enviar todos o ninguno, solo hay dos
	 *         atributos obligatorios que son los limites de registros que se
	 *         consultaran.
	 */
	public static List<PersonaExt> getPersonaporFiltroVinculacion(PersonaExt persona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(persona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_VINCULACION_FILTRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<PersonaExt>();
		}
	}

	public static List<Cargo> getCargosPorEntidad(Cargo cargo) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(cargo);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_POR_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Cargo>>() {
			}.getType();
			List<Cargo> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static Cargo getcargoEntidadPlantaDetalle(EntidadPlantaDetalle EntidadPlantaDetalle) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(EntidadPlantaDetalle);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_ENTIDAD_PLANTA_DETALLE, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<Cargo>() {
			}.getType();
			Cargo cargo = gson.fromJson(out, type);
			invalidToken(out);
			return cargo;
		} catch (Exception e) {
			e.printStackTrace();
			return new Cargo();
		}
	}

	/**
	 * Metodo que trae todas las vinculaciones que estan activas para una planta
	 * personal especifica de una entidad Recibe el codEntidad, codEntidadPlanta en
	 * un objeto VinculacionExt
	 * 
	 * @param filtBusqueda
	 * @return List<VinculacionExt>
	 */
	public static List<VinculacionExt> getVinculacionesPlantaPersonal(VinculacionExt filtBusqueda) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(filtBusqueda);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACIONES_POR_PLANTA_PERSONAL, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getVinculacionesPlantaPersonal(VinculacionExt vinculacionActual)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * @author Maria Alejandra Colorado Rios
	 * @param vinculacionExt
	 * @return Metodo que obtiene los datos a setear en la tabla Vinculacion. Este
	 *         tiene como fin, realizar un eliminado logico de todas las
	 *         vinculaciones que estan asociadas a la planta enviada.
	 */
	public static boolean setDesvinculacionAutomaticaPorPlanta(VinculacionExt vinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DESVINCULACION_AUTOMATICA_POR_PLANTA, json, token,
					TIME_OUT, auditoriaSeguridad);
			VinculacionExt pre = gson.fromJson(out, VinculacionExt.class);
			if (pre.isError()) {
				logger.info("El servicio no inserto los datos", pre.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error(
					"El servicio no inserto los datos - setDesvinculacionAutomaticaPorPlanta(VinculacionExt vinculacionExt)",
					e);
			return false;
		}
	}

	/**
	 * Metodo para retornar la vinculacion de un usuario que ya este vinculado para
	 * la misma entidad
	 * 
	 * @param VinculacionExt
	 * @return List<VinculacionExt>
	 */
	public static List<VinculacionExt> getVinculacionMismaEntidad(VinculacionExt vinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_MISMA_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getVinculacionMismaEntidad(VinculacionExt vinculacionExt)", e);
			return new ArrayList<>();
		}
	}

	public static EntidadPlantaDetalleExt guardarEntidadPlantaDetalle(EntidadPlantaDetalleExt entidadCargoPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(entidadCargoPlanta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_PLANTA_DETALLE, json, token, TIME_OUT,
					auditoriaSeguridad);
			EntidadPlantaDetalleExt pre = gson.fromJson(out, EntidadPlantaDetalleExt.class);
			return pre;
		} catch (JsonSyntaxException e) {
			logger.error(
					"El servicio no inserto los datos - setEntidadCargoPlanta(EntidadCargoPlantaExt entidadCargoPlanta)",
					e);
			return new EntidadPlantaDetalleExt();
		}
	}

	/**
	 * Metodo para retornar la vinculacion de un usuario con un codigo de
	 * denominacion especifico
	 * 
	 * @param VinculacionExt
	 * @return List<VinculacionExt>
	 */
	public static List<VinculacionExt> getVinculacionSenadoresCongresistas(BigDecimal codEntidad, Long idDenominacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codEntidad\":" + codEntidad + ",\"idDenominacion\":" + idDenominacion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_SENADOR_CONGRESISTA, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getVinculacionSenadoresCongresistas(VinculacionExt vinculacionExt)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo retorna las plantas activas de una entidad, exceptuando aquellas que
	 * son de tipo utl
	 * 
	 * @param EntidadPlanta
	 * @return List<EntidadPlantaExt>
	 */
	public static List<EntidadPlantaExt> getSelectEntidadPlantaExceptoUTL(EntidadPlantaExt EntidadPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlanta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PLANTA_EXCEPTO_UTL_UAN, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaExt>>() {
			}.getType();
			List<EntidadPlantaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos -  getSelectEntidadPlantaExceptoUTL(EntidadPlantaExt EntidadPlanta)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo que asigna el rol de servidor publico a una persona que antes era
	 * contratista
	 * 
	 * @param usuarioRolEntidad
	 * @return boolean
	 */
	public static boolean setUsuarioEntidadPorUsuario(UsuarioEntidad usuarioEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(usuarioEntidad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_UPDATE_USUARIO_ENTIDAD_POR_USUARIO, json, token,
					TIME_OUT, auditoriaSeguridad);
			UsuarioEntidad pre = gson.fromJson(out, UsuarioEntidad.class);
			if (pre.isError()) {
				logger.error("El servicio no guardo datos - setUsuarioEntidadPorUsuario(UsuarioEntidad usuarioEntidad)",
						pre.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("El servicio no guardo datos - setUsuarioEntidadPorUsuario(UsuarioEntidad usuarioEntidad)", e);
			return false;
		}
	}

	/**
	 * Metodo retorna la cantidad de vacantes de un cargo especifico
	 * 
	 * @param EntidadPlantaDetalleExt
	 * @return List<EntidadPlantaExt>
	 */
	public static EntidadPlantaDetalleExt getVacanciaPorCargo(EntidadPlantaDetalleExt entidadPlantaDetalleExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(entidadPlantaDetalleExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VACANCIA_CARGOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			EntidadPlantaDetalleExt per = gson.fromJson(out, EntidadPlantaDetalleExt.class);
			return per;

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new EntidadPlantaDetalleExt();
		}
	}

	/**
	 * @author Maria Alejandra Colorado Rios
	 * @param situacionAdminVinculacionExt
	 * @return Eliminado de todas las situaciones administrativas asociadas a la
	 *         vinculacion eliminada
	 */
	public static boolean setSituacionPorVinculacion(SituacionAdminVinculacionExt situacionAdminVinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdminVinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_SA_POR_VINCULACION, json, token, TIME_OUT,
					auditoriaSeguridad);
			SituacionAdminVinculacionExt pre = gson.fromJson(out, SituacionAdminVinculacionExt.class);
			if (pre.isError()) {
				logger.info("El servicio no inserto los datos", pre.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error(
					"El servicio no inserto los datos - setSituacionPorVinculacion(SituacionAdminVinculacionExt situacionAdminVinculacionExt)",
					e);
			return false;
		}
	}

	/**
	 * Metodo para retornar los cargos vacantes tanto por vacacia temporal como po
	 * global
	 * 
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 * @author Maria Alejandra C
	 */
	public static List<EntidadPlantaDetalleExt> getvacantesTemporalPermanteGlobalEstructural(long codEntidad,
			long codClasificacionPlanta, long codClasePlanta, long codNaturalezaEmpleo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codClasificacionPlanta\":" + codClasificacionPlanta + ",\"codClasePlanta\":"
					+ codClasePlanta + ",\"codEntidad\":" + codEntidad + ",\"codNaturalezaEmpleo\":"
					+ codNaturalezaEmpleo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VACANTES_TEMP_PERM_GLOBAL_ESTRUCTURAL, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro datos - getvacantes(long codEntidad, long codClasificacionPlanta, long codClasePlanta, long codNaturalezaEmpleo)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo obtener las personas vinculadas
	 * 
	 * @param persona
	 * @return
	 */
	public static List<PersonaExt> getPersonasVinculadas(PersonaExt persona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(persona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONAS_VINCULADAS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			logger.info("El servicio no encontro datos - getPersonaRolActivoEntidad(PersonaExt persona)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo que retorna lista de vinculaciones que NO tienen Situaciones
	 * Administrtivas asociadas o que tienen Situaciones Administrativas con fecha
	 * de inicio mayor a la fecha actual o a la fecha de posesion de la vinculacion
	 * (Esta fecha depende del parametro enviado) Este metodo es utilizado para el
	 * conteo de las vacantes por cargo.
	 * 
	 * @param VinculacionExt
	 * @return List<VinculacionExt>
	 */
	public static List<VinculacionExt> getVinculacionSinSAXProcesar(VinculacionExt vinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_SIN_SA_POR_PROCESAR, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VinculacionExt>>() {
			}.getType();
			List<VinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getVinculacionFilter(VinculacionExt vinculacionExt)", e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar situaciones administrativas que tienen el campo enviado
	 * en encargo.
	 * 
	 * @param id
	 * @return EntidadPlantaDetalleExt
	 * @author Maria Alejandra C
	 */
	public static List<SituacionAdminVinculacionExt> getCargoEnEncargo(
			SituacionAdminVinculacionExt situacionAdminVinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdminVinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_EN_ENCARGO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdminVinculacionExt>>() {
			}.getType();
			List<SituacionAdminVinculacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.info(
					"El servicio no encontro dgetCargoEnEncargo(SituacionAdminVinculacionExt situacionAdminVinculacionExt)",
					e);
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo para retornar la situacion_admin_vinculacion por id
	 * 
	 * @param SituacionAdminVinculacion
	 * @return SituacionAdminVinculacion
	 */
	public static SituacionAdminVinculacion getSituacionAdminVincPorId(SituacionAdminVinculacion situacionAdminVinc) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdminVinc);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACION_ADMIN_VINC_POR_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<SituacionAdminVinculacion>() {
			}.getType();
			SituacionAdminVinculacion sa = gson.fromJson(out, type);
			invalidToken(out);
			return sa;
		} catch (Exception e) {
			e.printStackTrace();
			return new SituacionAdminVinculacion();
		}
	}

	/**
	 * Metodo para retornar la situacion_admin_vinculacion del cargo temporal
	 * ocupado
	 * 
	 * @param SituacionAdminVinculacion
	 * @return SituacionAdminVinculacion
	 */
	public static SituacionAdminVinculacion getSituacionAdminVincPorCodSAOcupaTemporal(
			SituacionAdminVinculacion situacionAdminVinc) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdminVinc);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACION_ADMIN_VINC_POR_COD_SA_OCUPA_TEMPORAL,
					json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<SituacionAdminVinculacion>() {
			}.getType();
			SituacionAdminVinculacion sa = gson.fromJson(out, type);
			invalidToken(out);
			return sa;
		} catch (Exception e) {
			e.printStackTrace();
			return new SituacionAdminVinculacion();
		}
	}

	/**
	 * Metodo para retornar los datos de la ultima vinculación asignado a un cargo
	 * de entidadPlantaDetalle
	 * 
	 * @param id
	 * @return VinculacionExt
	 */
	public static VinculacionExt getUltimaVinculacionByCargo(VinculacionExt vinculacionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(vinculacionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ULTIMA_VINCULACION_BY_CARGO, json, token, TIME_OUT,
					auditoriaSeguridad);
			VinculacionExt objEntidad = gson.fromJson(out, VinculacionExt.class);
			invalidToken(out);
			return objEntidad;
		} catch (JsonSyntaxException e) {
			logger.info("El servicio no encontro datos - getUltimaVinculacionByCargo(VinculacionExt vinculacionExt)",
					e);
			return new VinculacionExt();
		}
	}

}