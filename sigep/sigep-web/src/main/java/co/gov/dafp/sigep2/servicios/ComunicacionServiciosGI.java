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
import co.gov.dafp.sigep2.entities.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.entities.ColumnaTablaGestionInformacion;
import co.gov.dafp.sigep2.entities.TablaGestionInformacion;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.rest.ErrorMensajes;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * @author JDavila
 *
 */
public class ComunicacionServiciosGI implements  Serializable {
	private static final long serialVersionUID = 5238163537397889985L;
	private static Gson gson;
	private static String token;
	private static long timeOUT = ConfigurationBundleConstants.getTimeOutConversation();
	private static AuditoriaSeguridad auditoriaSeguridad;

	/**
	 * Devuelve el tojen de sesion
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
	 * Invalida el token de sesion en caso de haberse superado el tiempo maximo
	 * de inactividad
	 */
	private static void invalidToken(String out) {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
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
		}
	}

	/**
	 * Devuelve el listado de registros de cifras de empleo publico de acuerdo a
	 * los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros
	 * 
	 * @return {@link List} de {@link CifrasEmpleoPublico} Registros
	 *         seleccionados
	 */
	public static List<CifrasEmpleoPublico> getCifrasEmpleoPublico(CifrasEmpleoPublico parametros) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(parametros);
		try {
			String path = ConfigurationBundleConstants.getPathServerRest() + "sigepgi/tablero/cep";
			String out = ConnectionHttp.sendPost(path, json, token, timeOUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CifrasEmpleoPublico>>() {
			}.getType();
			List<CifrasEmpleoPublico> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<>();
		}
	}

	/**
	 * Devuelve el totalizado de los registros de cifras de empleo publico de
	 * acuerdo a los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros para el totalizado
	 * 
	 * @return {@link CifrasEmpleoPublico} Totalizado de los registros
	 *         seleccionados
	 */
	public static CifrasEmpleoPublico getTotalCifrasEmpleoPublico(CifrasEmpleoPublico parametros) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(parametros);
		try {
			String path = ConfigurationBundleConstants.getPathServerRest() + "sigepgi/tablero/totalcep";
			String out = ConnectionHttp.sendPost(path, json, token, timeOUT,auditoriaSeguridad);
			CifrasEmpleoPublico list = gson.fromJson(out, CifrasEmpleoPublico.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new CifrasEmpleoPublico();
		}
	}

	/**
	 * Devuelve las tablas y vistas del sistema para configuracion de los
	 * reportes en gestion de la informacion
	 * 
	 * @return {@link List} de {@link TablaGestionInformacion} Tablas y vistas
	 *         del sistema
	 */
	public static List<TablaGestionInformacion> tablasGestionInformacion() {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(new Object());
		try {
			String path = ConfigurationBundleConstants.getPathServerRest() + "sigepgi/tablero/tablasgi";
			String out = ConnectionHttp.sendPost(path, json, token, timeOUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<TablaGestionInformacion>>() {
			}.getType();
			List<TablaGestionInformacion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<>();
		}
	}

	/**
	 * Devuelve el listado de columnas de la tabla filtrada
	 * 
	 * @return {@link List} de {@link ColumnaTablaGestionInformacion} Registros
	 *         seleccionados para la tabla seleccionada
	 */
	public static List<ColumnaTablaGestionInformacion> columnasTablasGestionInformacion(
			TablaGestionInformacion tablaGestionInformacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(tablaGestionInformacion);
		try {
			String path = ConfigurationBundleConstants.getPathServerRest() + "sigepgi/tablero/columnastablasgi";
			String out = ConnectionHttp.sendPost(path, json, token, timeOUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ColumnaTablaGestionInformacion>>() {
			}.getType();
			List<ColumnaTablaGestionInformacion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<>();
		}
	}

	/**
	 * Devuelve el listado de registros de tabla de resultados para cifras de
	 * empleo publico de acuerdo a los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros
	 * 
	 * @return {@link List} de {@link CifrasEmpleoPublico} Registros
	 *         seleccionados
	 */
	public static List<CifrasEmpleoPublico> getTablaResultadosCifrasEmpleoPublico(CifrasEmpleoPublico parametros) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(parametros);
		try {
			String path = ConfigurationBundleConstants.getPathServerRest() + "sigepgi/tablero/tablaresultados";
			String out = ConnectionHttp.sendPost(path, json, token, timeOUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CifrasEmpleoPublico>>() {
			}.getType();
			List<CifrasEmpleoPublico> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<>();
		}
	}

}