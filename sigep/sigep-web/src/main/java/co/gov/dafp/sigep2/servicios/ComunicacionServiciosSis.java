/**
 * 
 */
package co.gov.dafp.sigep2.servicios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entities.AuditoriaConfiguracion;
import co.gov.dafp.sigep2.entities.Cargo;
import co.gov.dafp.sigep2.entities.CargoHojaVida;
import co.gov.dafp.sigep2.entities.CmActivarUsuarios;
import co.gov.dafp.sigep2.entities.CmConfiguracion;
import co.gov.dafp.sigep2.entities.CmContratos;
import co.gov.dafp.sigep2.entities.CmCrearEntidad;
import co.gov.dafp.sigep2.entities.CmCrearEstructura;
import co.gov.dafp.sigep2.entities.CmCrearGrupo;
import co.gov.dafp.sigep2.entities.CmCrearNomenclaturaSalarial;
import co.gov.dafp.sigep2.entities.CmCrearPlanta;
import co.gov.dafp.sigep2.entities.CmCrearUsuarios;
import co.gov.dafp.sigep2.entities.CmHvEducacionDesaHumano;
import co.gov.dafp.sigep2.entities.CmHvEducacionFormal;
import co.gov.dafp.sigep2.entities.CmHvEducacionIdiomas;
import co.gov.dafp.sigep2.entities.CmHvEducacionOtros;
import co.gov.dafp.sigep2.entities.CmHvEvaluacionDesempeno;
import co.gov.dafp.sigep2.entities.CmHvExperienciaLaboral;
import co.gov.dafp.sigep2.entities.CmHvExperienciaLaboralDocen;
import co.gov.dafp.sigep2.entities.CmHvInformacionBasica;
import co.gov.dafp.sigep2.entities.CmRoles;
import co.gov.dafp.sigep2.entities.CmSituacionesAdministrativas;
import co.gov.dafp.sigep2.entities.CmVinculaciones;
import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.DependenciaEntidad;
import co.gov.dafp.sigep2.entities.DependenciaHojaVida;
import co.gov.dafp.sigep2.entities.DependenciaHojaVidaExt;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadPlanta;
import co.gov.dafp.sigep2.entities.Festivo;
import co.gov.dafp.sigep2.entities.Idioma;
import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.ProcesosCargaMasiva;
import co.gov.dafp.sigep2.entities.ProgramaAcademico;
import co.gov.dafp.sigep2.entities.RangoEdad;
import co.gov.dafp.sigep2.entities.Recurso;
import co.gov.dafp.sigep2.entities.RecursoAccion;
import co.gov.dafp.sigep2.entities.Rol;
import co.gov.dafp.sigep2.entities.SequenciasSigep;
import co.gov.dafp.sigep2.entities.SituacionAdministrativa;
import co.gov.dafp.sigep2.entities.Token;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entities.UsuarioRolEntidad;
import co.gov.dafp.sigep2.entities.UsuarioSession;
import co.gov.dafp.sigep2.entities.VAccionAuditoria;
import co.gov.dafp.sigep2.entity.RecursoDTO;
import co.gov.dafp.sigep2.entity.seguridad.RecursoAccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.mbean.PermisoRol;
import co.gov.dafp.sigep2.mbean.PermisoRolAccion;
import co.gov.dafp.sigep2.mbean.ext.AuditoriaExt;
import co.gov.dafp.sigep2.mbean.ext.CargoHojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.MunicipioExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.ProcesosCargaMasivaExt;
import co.gov.dafp.sigep2.mbean.ext.RecursoAccionExt;
import co.gov.dafp.sigep2.mbean.ext.RecursoExt;
import co.gov.dafp.sigep2.mbean.ext.ResultadoPreguntaExt;
import co.gov.dafp.sigep2.mbean.ext.RolExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.mbean.preguntaopinion.PreguntaOpinion;
import co.gov.dafp.sigep2.mbean.preguntaopinion.ResultadoPregunta;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.rest.ErrorMensajes;
import co.gov.dafp.sigep2.rest.SerivesRestURL;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * @author joseviscaya
 *
 */
public class ComunicacionServiciosSis implements Serializable {
	private static Gson gson;
	private static String token;
	private static long TIME_OUT = ConfigurationBundleConstants.getTimeOutConversation();
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final long serialVersionUID = 9143774434283059779L;
	private static AuditoriaSeguridad auditoriaSeguridad;

	/**
	 * @return the auditoriaSeguridad
	 */
	public static AuditoriaSeguridad getAuditoriaSeguridad() {
		return auditoriaSeguridad;
	}

	/**
	 * @param auditoriaSeguridad the auditoriaSeguridad to set
	 */
	public static void setAuditoriaSeguridad(AuditoriaSeguridad auditoriaSeguridad) {
		ComunicacionServiciosSis.auditoriaSeguridad = auditoriaSeguridad;
	}

	/**
	 * 
	 * @param codigoVentana
	 * @return
	 */
	public static RecursoActivoPerfilUsuarioDTO recursoPorCod(String codigoVentana) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"codigoVentana\":\"" + codigoVentana + "\"}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RECURSO_POR_CODIGO_VENTANA, json, token, TIME_OUT,
					auditoriaSeguridad);
			return gson.fromJson(out, RecursoActivoPerfilUsuarioDTO.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new RecursoActivoPerfilUsuarioDTO();
		}
	}

	/**
	 * SERWEB000003 - Consulta un recurso por codigo de ventana
	 * 
	 * @param codigoVentana
	 * @return {@link RecursoDTO}
	 */
	public static Recurso recursoPorCodVentana(String codigoVentana) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"codigoVentana\":\"" + codigoVentana + "\"}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RECURSO_POR_CODIGO_VENTANA, json, token, TIME_OUT,
					auditoriaSeguridad);
			return gson.fromJson(out, Recurso.class);
		} catch (JsonSyntaxException e) {
			return new Recurso();
		}
	}

	/**
	 * SERWEB000004
	 * 
	 * @return
	 */
	public static List<RecursoActivoPerfilUsuarioDTO> recursoActivoPerfilUsuario() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RECURSO_ACTIVO_PERFIL_USUARIO, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RecursoActivoPerfilUsuarioDTO>>() {
			}.getType();
			List<RecursoActivoPerfilUsuarioDTO> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<RecursoActivoPerfilUsuarioDTO>();
		}
	}

	/**
	 * SERWEB000014
	 * 
	 * @param
	 * @return List<Idioma>
	 */
	public static List<Idioma> idiomaList() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_IDIOMAS, "", token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Idioma>>() {
			}.getType();
			List<Idioma> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Idioma>();
		}
	}

	/**
	 * SERWEB000019
	 * 
	 * @param name
	 * @return
	 */
	public static List<Parametrica> getParametrica(String name) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"nombreParametro\":\"" + name + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETROS_POR_CODIGO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Parametrica>>() {
			}.getType();
			List<Parametrica> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Parametrica>();
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Parametrica getParametricaIntetos(String name) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"nombreParametro\":\"" + name + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRICA_INTENTOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			Parametrica list = gson.fromJson(out, Parametrica.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SERWEB000085
	 * 
	 * @param ProgramaAcademico
	 * @return
	 */
	public static List<ProgramaAcademico> getprogramaacademicoporint(long codInstitucion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codInstitucion\":" + codInstitucion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PROGRAMA_ACADEMICO_POR_INT, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ProgramaAcademico>>() {
			}.getType();
			List<ProgramaAcademico> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ProgramaAcademico>();
		}
	}

	/**
	 * SERWEB000023
	 * 
	 * @param
	 * @return
	 */
	public static List<Pais> getPaises() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PAISES, "", token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Pais>>() {
			}.getType();
			List<Pais> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Pais>();
		}
	}

	/**
	 * SERWEB000024
	 * 
	 * @param
	 * @return
	 */
	public static List<Departamento> getDepartamentos(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPais\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPTOS, json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Departamento>>() {
			}.getType();
			List<Departamento> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Departamento>();
		}
	}

	/**
	 * SERWEB000025
	 * 
	 * @param
	 * @return
	 */
	public static List<Municipio> getMunicipios(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDepartamento\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MUNICIPIOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Municipio>>() {
			}.getType();
			List<Municipio> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Municipio>();
		}
	}

	/**
	 * SERWEB000036
	 * 
	 * @param
	 * @return
	 */
	public static RolDTO getRolPorId(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codRol\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROL_POR_IDROL, json, token, TIME_OUT,
					auditoriaSeguridad);
			out = out.replace("\"codRol\"", "\"id\"").replace("\"flgEstado\":1", "\"flgEstado\":true")
					.replace("\"flgEstado\":0", "\"flgEstado\":false").replace("\"flgActivo\":1", "\"flgActivo\":true")
					.replace("\"flgActivo\":0", "\"flgActivo\":false");
			RolDTO list = gson.fromJson(out, RolDTO.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new RolDTO();
		}
	}

	/**
	 * SERWEB000041
	 * 
	 * @param codPersona
	 * @param codEntidad
	 * @param codRol
	 * @return
	 */
	public static String registrarToken(long codPersona, long codEntidad, long codRol) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codUsuario\":" + codPersona + ",\"codEntidad\":" + codEntidad + ",\"audCodRol\":" + codRol
					+ "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.REGISTRAR_TOKEN, json, "", TIME_OUT,
					auditoriaSeguridad);
			Token t = gson.fromJson(out, Token.class);

			return t.getToken();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * SERWEB000056
	 * 
	 * @return
	 */
	public static boolean expiracionToken(String token, long codUsuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"token\":\"" + token + "\",\"codUsuario\":" + codUsuario + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.EXPIRAR_TOKEN, json, token, TIME_OUT,
					auditoriaSeguridad);
			ErrorMensajes error = gson.fromJson(out, ErrorMensajes.class);
			if (error.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000059
	 * 
	 * @param id
	 * @return
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
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SERWEB000060
	 * 
	 * @param id
	 * @return
	 */
	public static InstitucionEducativa getInstitucionEducaporId(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codInstitucionEducativa\":\"" + id + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IEDUCATIVAS_POR_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			InstitucionEducativa list = gson.fromJson(out, InstitucionEducativa.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SERWEB000062
	 * 
	 * @param id
	 * @return
	 */
	public static List<Parametrica> getParametricaPorIdPadre(long codPadreParametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPadreParametrica\":" + codPadreParametrica + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRO_POR_ID_PADRE, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Parametrica>>() {
			}.getType();
			List<Parametrica> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Parametrica>();
		}
	}

	/**
	 * SERWEB000068
	 * 
	 * @param codRolPadre
	 * @return
	 */
	public static List<RolDTO> getRolesPorPadreId(long codRolPadre) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codRolPadre\":" + codRolPadre + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROLES_POR_PADRE_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RolDTO>>() {
			}.getType();
			List<RolDTO> list = gson.fromJson(out.replace("\"codRol\"", "\"id\""), type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<RolDTO>();
		}
	}

	/**
	 * SERWEB000069
	 * 
	 * @param rol
	 * @return
	 */
	public static boolean setanduprol(RolDTO rol) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(rol);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_UPDATE_ROL, json, token, TIME_OUT,
					auditoriaSeguridad);
			if (out.equals("true")) {
				return true;
			} else {
				return false;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB0000100
	 * 
	 * @param rol
	 * @return
	 */
	public static boolean setanduprol(Rol rol) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(rol);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ROL, json, token, TIME_OUT, auditoriaSeguridad);
			Rol r = gson.fromJson(out, Rol.class);
			if (r.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000070
	 * 
	 * @param
	 * @return
	 */
	public static Pais getpisporid(long codPais) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"codPais\":" + codPais + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PAIS_POR_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			Pais list = gson.fromJson(out, Pais.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Pais();
		}
	}

	/**
	 * SERWEB000071
	 * 
	 * @param
	 * @return
	 */
	public static Departamento getdeptoporid(long codDepartamento) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"codDepartamento\":" + codDepartamento + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPARTAMENTO_POR_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			Departamento list = gson.fromJson(out, Departamento.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Departamento();
		}
	}

	/**
	 * SERWEB000097
	 * 
	 * @param name,
	 *            limitIni, limitEnd, flgEstado
	 * @return
	 */
	public static List<Parametrica> getParametricaPorNombre(String name, int limitIni, int limitEnd, int flgEstado) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"nombreParametro\":\"" + name + "\",\"limitInit\":\"" + limitIni + "\",\"limitEnd\":\""
					+ limitEnd + "\",\"flgEstado\":" + flgEstado + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRICA_POR_NOMBRE_LIKE, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Parametrica>>() {
			}.getType();
			List<Parametrica> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Parametrica>();
		}
	}

	/**
	 * SERWEB000098
	 * 
	 * @param exp
	 * @return
	 */
	public static Parametrica setparametrica(Parametrica parametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		if (parametrica.getNombreParametro() != null)
			parametrica.setNombreParametro(parametrica.getNombreParametro().trim());
		if (parametrica.getValorParametro() != null)
			parametrica.setValorParametro(parametrica.getValorParametro().trim());
		if (parametrica.getDescripcion() != null)
			parametrica.setDescripcion(parametrica.getDescripcion().trim());
		try {
			String json = gson.toJson(parametrica);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PARAMETRICA, json, token, TIME_OUT,
					auditoriaSeguridad);
			Parametrica user = gson.fromJson(out, Parametrica.class);
			if (user.isError()) {
				return user;
			} else {
				return user;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return parametrica;
		}
	}

	/**
	 * SERWEB000043
	 * 
	 * @return
	 */
	public static List<RolDTO> getRolesSistema(String name, int limitIni, int limitFin, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"nombre\":\"" + name + "\",\"limitIni\":" + limitIni + "," + "\"limitFin\":" + limitFin
				+ ",\"flgActivo\":" + flgActivo + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROLES_SISTEMA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RolDTO>>() {
			}.getType();
			out = out.replace("\"codRol\":", "\"id\":");
			List<RolDTO> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<RolDTO>();
		}
	}
	
	/**
	 * SERWEB000043
	 * 
	 * @return
	 */
	public static List<RolDTO> getRolesSistemaXObjeto(RolExt rol) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(rol);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROLES_SISTEMA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RolDTO>>() {
			}.getType();
			out = out.replace("\"codRol\":", "\"id\":");
			List<RolDTO> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<RolDTO>();
		}
	}
	
	/**
	 * SERWEB000099 param persona
	 * 
	 * @return Este Servicion recibe un objeto persona en el cual se pueden
	 *         enviar los siguientes atributos de consulta codTipoIdentificacion
	 *         numeroIdentificacion primerNombre primerApellido segundoNombre
	 *         segundoApellido codEntidad codUsuario flgActivo y se deben enviar
	 *         los limites de consulta en la respueta se obtendra un atributo
	 *         total que informara el la cantidad de registros que se
	 *         encontraron con los criterios de busqueda este servicio recibe
	 *         cualquiera de estos valores para la consulta. la consulta no es
	 *         like
	 */
	public static List<RolExt> getRolesSistemaPorPersona(PersonaExt persona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DETALLE_ROL_PERSONA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RolExt>>() {
			}.getType();
			List<RolExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<RolExt>();
		}
	}

	/**
	 * SERWEB000101
	 * 
	 * @param codUsuarioRolEntidad
	 * @return Metodo que consume el servicio de desactivar un rol de un usuario
	 *         asociado a una entidad este metodo recibe el id de la tabla
	 *         UsuarioRolEntidad y pone su flag de activo y estado a 0 si el
	 *         ServicioRest no encuntra un valor en codUsuarioRolEntidad
	 *         generara una respuesta de error
	 * 
	 */
	public static boolean desactivarrolusuario(long codUsuarioRolEntidad, long audCodUsuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codUsuarioRolEntidad\":" + codUsuarioRolEntidad + ",\"audCodUsuario\":" + audCodUsuario
					+ "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.DESACTIVAR_ROL_USUARIO_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			UsuarioRolEntidad user = gson.fromJson(out, UsuarioRolEntidad.class);
			if (user.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000118
	 * 
	 * @param name
	 * @param limitInit
	 * @param limitEnd
	 * @return Metodo que consume el servicio de trae una lista de
	 *         AuditoriaConfiguracion el atributo Name es para buscar por
	 *         nombreTabla usando un Like el servicio valida que si esta
	 *         variable se envia null no use el like, dentro de la respuesta
	 *         llega un atributo total que le indica el total de registros
	 *         disponibles por la consulta, ya se que se use el like o no.
	 */
	public static List<AuditoriaConfiguracion> getAuditoriaConfiguracion(String name, int limitInit, int limitEnd) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"nombreTabla\":\"" + name + "\",\"limitInit\":" + limitInit + "," + "\"limitEnd\":"
					+ limitEnd + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUDITORIA_CONFIGURACION, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<AuditoriaConfiguracion>>() {
			}.getType();
			List<AuditoriaConfiguracion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<AuditoriaConfiguracion>();
		}
	}

	/***
	 * SERWEB000119
	 * 
	 * @param auditoriaConfiguracion
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codTablaAuditoria para hacer una actualizacion por ID de no ir
	 *         este parametro el sistema valida que se envie el nombreTabla para
	 *         insertar un registro, si no detecta ninguno de los dos atributos
	 *         se generara un error que el servicio reportara en el objeto
	 *         ErrorMensages.
	 */
	public static boolean setAuditoriaconfiguracion(AuditoriaConfiguracion auditoriaConfiguracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(auditoriaConfiguracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_AUDITORIA_CONFIGURACION, json, token, TIME_OUT,
					auditoriaSeguridad);
			AuditoriaConfiguracion per = gson.fromJson(out, AuditoriaConfiguracion.class);
			if (per.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000120
	 * 
	 * @param name
	 * @param limitInit
	 * @param limitEnd
	 * @return Metodo que consume el servicio de trae una una
	 *         AuditoriaConfiguracion por medio del atributo codTablaAuditoria
	 *         que es el id de la tabla.
	 */
	public static AuditoriaConfiguracion getAuditoriaConfiguracion(int codTablaAuditoria) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codTablaAuditoria\":\"" + codTablaAuditoria + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUDITORIA_CONFIGURACION_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			AuditoriaConfiguracion list = gson.fromJson(out, AuditoriaConfiguracion.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new AuditoriaConfiguracion();
		}
	}

	/**
	 * SERWEB000121
	 * 
	 * @param preguntaOpinion
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codPreguntaOpinion para hacer una actualizacion por ID de no
	 *         ir este parametro el sistema valida que se envie pregunta para
	 *         insertar un registro, si no detecta ninguno de los dos atributos
	 *         se generara un error que el servicio reportara en el objeto
	 *         ErrorMensages.
	 */
	public static boolean setpreguntaopinion(PreguntaOpinion preguntaOpinion) {
		getToken();
		if (preguntaOpinion.getPregunta() != null)
			preguntaOpinion.setPregunta(preguntaOpinion.getPregunta().trim());
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(preguntaOpinion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PREGUNTA_OPINION, json, token, TIME_OUT,
					auditoriaSeguridad);
			PreguntaOpinion pre = gson.fromJson(out, PreguntaOpinion.class);
			if (pre.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000122
	 * 
	 * @param PreguntaOpinion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         PreguntaOpinion por medio del atributo flgActivo y Limitinit y
	 *         limitEnd de la clase PreguntaOpinion, deben enviar como filtro
	 *         nombre de la pregunta o fecha ini o fecha fin
	 */
	public static List<PreguntaOpinion> getPreguntaOpinion(PreguntaOpinion PreguntaOpinion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(PreguntaOpinion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PREGUNTA_OPINION, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PreguntaOpinion>>() {
			}.getType();
			List<PreguntaOpinion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PreguntaOpinion>();
		}
	}

	/**
	 * SERWEB000124
	 * 
	 * @param parametrica
	 * @return Metodo que implementa el servicios de retornar la lista de todas
	 *         las parametricas padre, el objeto parametrica se debe setear con
	 *         los rangos limitini y limitfin para la paginacion el objeto
	 *         retorna el todal de registros que contiene la consulta, tambien
	 *         se debe enviar el flgActivo para filtar por activos e incativos
	 */
	public static List<Parametrica> getParametricaPadreTodo(Parametrica parametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(parametrica);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRICA_TODOS_PADRE, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Parametrica>>() {
			}.getType();
			List<Parametrica> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Parametrica>();
		}
	}

	/**
	 * SERWEB000128
	 * 
	 * @param resultadoPregunta
	 * @return Metodo que implementa el servicios de retornar la lista de
	 *         resultadoPregunta, por medio del objeto resultadoPregunta se debe
	 *         setear con los rangos limitini y limitfin para la paginacion el
	 *         objeto retorna el todal de registros que contiene la consulta
	 *         para tema de paginacion
	 */
	public static List<ResultadoPregunta> getRespuestasPregunta(ResultadoPregunta resultadoPregunta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(resultadoPregunta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RESPUESTAS_POR_PREGUNTA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ResultadoPregunta>>() {
			}.getType();
			List<ResultadoPregunta> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ResultadoPregunta>();
		}
	}

	/**
	 * SERWEB000129
	 * 
	 * @param codPreguntaOpinion
	 * @return Metodo que consume el servicio de trae el valor del numero de
	 *         respuestas por pregunta mediate el codPreguntaOpinion.
	 */
	public static int getTotalRespuestasPregunta(long codPreguntaOpinion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPreguntaOpinion\":\"" + codPreguntaOpinion + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_TOTAL_RESPUESTAS_POR_PREGUNTA, json, token,
					TIME_OUT, auditoriaSeguridad);
			ResultadoPregunta list = gson.fromJson(out, ResultadoPregunta.class);
			return list.getTotalRespuesta();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * SERWEB000130
	 * 
	 * @param codPreguntaOpinion
	 * @return Metodo que consume el servicio de trae el valor del numero de
	 *         respuestas por pregunta mediate el codPreguntaOpinion.
	 */
	public static int getRespuestasPuntajePregunta(long codPreguntaOpinion, int puntaje) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPreguntaOpinion\":" + codPreguntaOpinion + ",\"puntaje\":" + puntaje + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PUNTAJE_RESPUESTAS_POR_PREGUNTA, json, token,
					TIME_OUT, auditoriaSeguridad);
			ResultadoPregunta list = gson.fromJson(out, ResultadoPregunta.class);
			return list.getTotalRespuesta();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * SERWEB000136
	 * 
	 * @param codTablaParametrica
	 * @return Metodo que consume el servicio elimina fisicamente un registro de
	 *         la tabla parametrica El metodo retorna un String con true, de lo
	 *         contrario el servicio retornara el error ocurrido
	 */
	public static String delparametrica(long codTablaParametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codTablaParametrica\":" + codTablaParametrica + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.DEL_PARAMETRICA, json, token, TIME_OUT,
					auditoriaSeguridad);
			return out;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "false";
		}
	}

	/**
	 * SERWEB000137
	 * 
	 * @param CodPreguntaOpinion
	 * @return Metodo que consume el servicio elimina fisicamente un registro de
	 *         la tabla pregunta opinion El metodo retorna un String con true,
	 *         de lo contrario el servicio retornara el error ocurrido
	 */
	public static String delpreguntaopinion(long codPreguntaOpinion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPreguntaOpinion\":" + codPreguntaOpinion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.DEL_PREGUNTA_OPINION, json, token, TIME_OUT,
					auditoriaSeguridad);
			return out;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "false";
		}
	}

	/**
	 * SERWEB000139
	 * 
	 * @param ResultadoPregunta
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de CodResultadoPregunta para hacer una actualizacion por ID de no
	 *         ir este parametro el sistema valida que se envie pregunta para
	 *         insertar un registro, si no detecta ninguno de los dos atributos
	 *         se generara un error que el servicio reportara en el objeto
	 *         ErrorMensages.
	 */
	public static boolean setpreguntaopinion(ResultadoPregunta resultadoPregunta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(resultadoPregunta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_RESPUESTA_PREGUNTA, json, token, TIME_OUT,
					auditoriaSeguridad);
			PreguntaOpinion pre = gson.fromJson(out, PreguntaOpinion.class);
			if (pre.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000140
	 * 
	 * @param resultadoPregunta
	 * @return Metodo que implementa el servicios de retornar una
	 *         PreguntaOpinion de forma aleatoria,
	 */
	public static PreguntaOpinion getPreguntaOpinionRDN() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PREGUNTA_OPINION_RDN, json, token, TIME_OUT,
					auditoriaSeguridad);
			PreguntaOpinion list = gson.fromJson(out, PreguntaOpinion.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PreguntaOpinion();
		}
	}

	/**
	 * SERWEB000141
	 * 
	 * @param codPreguntaOpinion
	 * @return Metodo que consume el servicio de trae los valores para frafico
	 *         de ResultadoPregunta por pregunta mediate el codPreguntaOpinion.
	 */
	public static List<ResultadoPregunta> getRespuestasGrafico(long codPreguntaOpinion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPreguntaOpinion\":\"" + codPreguntaOpinion + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RESPUESTA_GRAFICO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ResultadoPregunta>>() {
			}.getType();
			List<ResultadoPregunta> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ResultadoPregunta>();
		}
	}

	/**
	 * SERWEB000142
	 * 
	 * @param codPersona
	 * @return Metodo que implementa el servicios de retornar la lista de
	 *         EntidadExt, por medio del parametro codPersona
	 */
	public static List<EntidadExt> getEntidadPorPersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":\"" + codPersona + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDADES_POR_PERSONA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadExt>>() {
			}.getType();
			List<EntidadExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EntidadExt>();
		}
	}

	/**
	 * SERWEB000144
	 * 
	 * @param codPreguntaOpinion
	 * @return Metodo que consume el servicio de trae los valores para frafico
	 *         de ResultadoPregunta por pregunta mediate el codPreguntaOpinion.
	 */
	public static List<ResultadoPreguntaExt> getRespuestaPreguntaPersona(long codPreguntaOpinion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPreguntaOpinion\":\"" + codPreguntaOpinion + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RESPUESTA_PREGUNTA_PERSONA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ResultadoPreguntaExt>>() {
			}.getType();
			List<ResultadoPreguntaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ResultadoPreguntaExt>();
		}
	}

	/**
	 * SERWEB000145
	 * 
	 * @param sigla
	 * @return Metodo que consume el servicio de trae los valores para frafico
	 *         de VAccionAuditoria por pregunta mediate el sigla.
	 */
	public static Integer getAuditoriaSigla(String sigla) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"sigla\":\"" + sigla + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUDITORIA_ACCION_SIGLA, json, token, TIME_OUT,
					auditoriaSeguridad);
			VAccionAuditoria list = gson.fromJson(out, VAccionAuditoria.class);
			return list.getAccionAuditoriaId().intValue();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * SERWEB000146
	 * 
	 * @param sigla
	 * @return Metodo que consume el servicio de trae los valores para frafico
	 *         de VAccionAuditoria por pregunta mediate el sigla.
	 */
	public static List<VAccionAuditoria> getVaccionAuditoria() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUDITORIA_ACCION_V, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<VAccionAuditoria>>() {
			}.getType();
			List<VAccionAuditoria> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<VAccionAuditoria>();
		}
	}

	/**
	 * SERWEB000148
	 * 
	 * @param auditoriaExt
	 * @return Metodo que consume el servicio de trae una lista de AuditoriaExt
	 *         recibe como parametro el objeto AuditoriaExt el cual debe
	 *         setearse obligatoriamente el codTablaParametrica limitInit
	 *         limitEnd
	 */
	public static List<AuditoriaExt> getAuditoriaParametrica(AuditoriaExt auditoriaExt) {
		if (auditoriaExt.getCodTablaParametrica() < 1) {
			List<AuditoriaExt> error = new ArrayList<AuditoriaExt>();
			AuditoriaExt er = new AuditoriaExt();
			er.setError(true);
			er.setMensaje("Falta codTablaParametrica");
			error.add(er);
			return error;
		}
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(auditoriaExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUDITORIA_PARAMETRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<AuditoriaExt>>() {
			}.getType();
			List<AuditoriaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<AuditoriaExt>();
		}
	}

	/**
	 * SERWEB000152
	 * 
	 * @param auditoriaExt
	 * @return Metodo que consume el servicio de trae una lista de AuditoriaExt
	 *         recibe como parametro el objeto AuditoriaExt el cual debe
	 *         setearse obligatoriamente el FechaIni, FechaFin
	 *         codTablaParametrica nombreTabla y tambien limitInit limitEnd
	 */
	public static List<AuditoriaExt> getAuditoriaFechas(AuditoriaExt auditoriaExt) {

		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = gson.toJson(auditoriaExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUDITORIAS_FECHAS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<AuditoriaExt>>() {
			}.getType();
			List<AuditoriaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<AuditoriaExt>();
		}
	}

	/**
	 * SERWEB000154
	 * 
	 * @param resultadoPregunta
	 * @return Metodo que consume el servicio de trae estadisticas de
	 *         ResultadoPregunta mediate el codPreguntaOpinion y puntaje, ademas
	 *         es indispensable enviar los limites de la consulta.
	 */
	public static List<ResultadoPreguntaExt> getRespuestaPreguntaEstadistica(ResultadoPregunta resultadoPregunta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(resultadoPregunta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RESPUESTA_PREGUNTA_ESTADISTICA, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ResultadoPreguntaExt>>() {
			}.getType();
			List<ResultadoPreguntaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ResultadoPreguntaExt>();
		}
	}

	/**
	 * SERWEB000160
	 * 
	 * @param codPreguntaOpinion
	 * @param puntaje
	 * @return Metodo que consume el servicio de trae los valores para frafico
	 *         Edades de ResultadoPregunta por pregunta mediate el
	 *         codPreguntaOpinion y puntaje.
	 */
	public static List<ResultadoPreguntaExt> getRespuestasGraficoEdades(long codPreguntaOpinion, int puntaje) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPreguntaOpinion\":" + codPreguntaOpinion + ",\"puntaje\":" + puntaje + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RESPUESTA_GRAFICO_EDADES, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ResultadoPreguntaExt>>() {
			}.getType();
			List<ResultadoPreguntaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ResultadoPreguntaExt>();
		}
	}

	/**
	 * SERWEB000163
	 * 
	 * @param codPreguntaOpinion
	 * @param puntaje
	 * @return Metodo que consume el servicio de trae una lista cargos entidades
	 */
	public static List<Cargo> getCargosEntidad(long codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codEntidad\":" + codEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Cargo>>() {
			}.getType();
			List<Cargo> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Cargo>();
		}
	}

	/**
	 * SERWEB000163
	 * 
	 * @param codPreguntaOpinion
	 * @param puntaje
	 * @return Metodo que consume el servicio de trae una lista cargos entidades
	 */
	public static Cargo getCargo(long codCargo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codCargo\":" + codCargo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO, json, token, TIME_OUT, auditoriaSeguridad);
			Cargo list = gson.fromJson(out, Cargo.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Cargo();
		}
	}

	/**
	 * SERWEB000164
	 * 
	 * @param PreguntaOpinion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         PreguntaOpinion por medio del atributo fechaInio FechaFin
	 *         Pregunta y Limitinit y limitEnd de la clase PreguntaOpinion,
	 *         deben enviar como filtro nombre de la pregunta o fecha ini o
	 *         fecha fin
	 */
	public static List<PreguntaOpinion> getPreguntaOpinionFechaPregunta(PreguntaOpinion preguntaOpinion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			if (preguntaOpinion.getLimitEnd() == 0) {
				List<PreguntaOpinion> er = new ArrayList<PreguntaOpinion>();
				PreguntaOpinion err = new PreguntaOpinion();
				err.setError(true);
				err.setMensaje("Hace Falta el atriburo LimitEnd");
				er.add(err);
				return er;
			}
			String json = gson.toJson(preguntaOpinion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PREGUNTA_OPINION_FECHA_PREGUNTA, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PreguntaOpinion>>() {
			}.getType();
			List<PreguntaOpinion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PreguntaOpinion>();
		}
	}

	/**
	 * SERWEB000166
	 * 
	 * @param PreguntaOpinion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         PreguntaOpinion por medio del atributo fechaInio FechaFin y
	 *         Limitinit y limitEnd de la clase PreguntaOpinion, deben enviar
	 *         como filtro nombre de la pregunta o fecha ini o fecha fin
	 */
	public static List<PreguntaOpinion> getPreguntaOpinionFecha(PreguntaOpinion preguntaOpinion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			if (preguntaOpinion.getLimitEnd() == 0) {
				List<PreguntaOpinion> er = new ArrayList<PreguntaOpinion>();
				PreguntaOpinion err = new PreguntaOpinion();
				err.setError(true);
				err.setMensaje("Hace Falta el atriburo LimitEnd");
				er.add(err);
				return er;
			}
			String json = gson.toJson(preguntaOpinion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PREGUNTA_OPINION_FECHA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PreguntaOpinion>>() {
			}.getType();
			List<PreguntaOpinion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PreguntaOpinion>();
		}
	}

	/**
	 * SERWEB000170
	 * 
	 * @param EntidadPlanta
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de CodEntidadPlanta para hacer una actualizacion por ID de no ir
	 *         este parametro el sistema valida que se envie CodClasePlanta para
	 *         insertar un registro, si no detecta ninguno de los dos atributos
	 *         se generara un error que el servicio reportara en el objeto
	 *         ErrorMensages.
	 */
	public static boolean setEntidadPlanta(EntidadPlanta EntidadPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(EntidadPlanta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_PERSONAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			EntidadPlanta pre = gson.fromJson(out, EntidadPlanta.class);
			if (pre.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000171
	 * 
	 * @param EntidadPlanta
	 * @return Metodo que consume el servicio de trae una lista de EntidadPlanta
	 *         por medio del atributo codEntidad y Limitinit y limitEnd de la
	 *         clase EntidadPlanta.
	 */
	public static List<EntidadPlanta> getEntidadPlantaEntidad(EntidadPlantaExt EntidadPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			if (EntidadPlanta.getLimitEnd() == 0) {
				List<EntidadPlanta> er = new ArrayList<EntidadPlanta>();
				EntidadPlanta err = new EntidadPlanta();
				err.setError(true);
				err.setMensaje("Hace Falta el atriburo LimitEnd");
				er.add(err);
				return er;
			}
			String json = gson.toJson(EntidadPlanta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PERSONAL_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlanta>>() {
			}.getType();
			List<EntidadPlanta> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EntidadPlanta>();
		}
	}

	/**
	 * SERWEB000173
	 * 
	 * @param EntidadPlanta
	 * @return Metodo que consume el servicio de trae una lista de EntidadPlanta
	 *         por medio del atributo codEntidad y Limitinit y limitEnd de la
	 *         clase EntidadPlanta.
	 */
	public static EntidadPlanta getEntidadPlantaId(long codEntidadPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codEntidadPlanta\":" + codEntidadPlanta + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PERSONAL_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			EntidadPlanta list = gson.fromJson(out, EntidadPlanta.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new EntidadPlanta();
		}
	}

	/**
	 * SERWEB000142
	 * 
	 * @param codPersona
	 * @return Metodo que implementa el servicios de retornar la lista de
	 *         EntidadExt, por medio del parametro codPersona
	 */
	public static List<Entidad> getEntidadCodPersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":\"" + codPersona + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDADES_COD_PERSONA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Entidad>>() {
			}.getType();
			List<Entidad> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<Entidad>();
		}
	}

	/**
	 * SERWEB000187
	 * 
	 * @param codPersona,codEntidad
	 * @return Metodo que implementa el servicios de retornar la lista de
	 *         DependenciaEntidad, por medio del parametro codPersona,
	 *         codEntidad
	 */
	public static List<DependenciaEntidad> getDependenciaEntidadPersona(long codPersona, long codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona + ",\"codEntidad\":" + codEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPENDINCIA_ENTIDAD_PERSONA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<DependenciaEntidad>>() {
			}.getType();
			List<DependenciaEntidad> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<DependenciaEntidad>();
		}
	}

	/**
	 * SERWEB000189
	 * 
	 * @param codRolPadre,flgEstado
	 * @return
	 */
	public static List<RolDTO> getRolesHijos(long codRolPadre, int flgEstado) {
		getToken();
		String out;
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codRolPadre\":" + codRolPadre + ",\"flgEstado\":" + flgEstado + "}";
			if (codRolPadre == TipoParametro.JEFETH.getValue())
				out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROL_HIJOS_JEFE_TH, json, token, TIME_OUT,
						auditoriaSeguridad);
			else
				out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROL_HIJOS, json, token, TIME_OUT, auditoriaSeguridad);
			out = out.replace("\"codRol\"", "\"id\"").replace("\"flgEstado\":1", "\"flgEstado\":true")
					.replace("\"flgEstado\":0", "\"flgEstado\":false").replace("\"flgActivo\":1", "\"flgActivo\":true")
					.replace("\"flgActivo\":0", "\"flgActivo\":false");
			java.lang.reflect.Type type = new TypeToken<List<RolDTO>>() {
			}.getType();
			List<RolDTO> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<RolDTO>();
		}
	}
	
	/**
	 * SERWEB000189
	 * 
	 * @param codRolPadre,flgEstado
	 * @return
	 */
	public static List<RolDTO> getRolesHijosJefeTHYContratos(RolExt rolExt) {
		String out;
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();

		try {
			String json = gson.toJson(rolExt);
			out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROL_HIJOS_JEFE_TH_CONTRATOS, json, token, TIME_OUT,
						auditoriaSeguridad);
			out = out.replace("\"codRol\"", "\"id\"").replace("\"flgEstado\":1", "\"flgEstado\":true")
					.replace("\"flgEstado\":0", "\"flgEstado\":false").replace("\"flgActivo\":1", "\"flgActivo\":true")
					.replace("\"flgActivo\":0", "\"flgActivo\":false");
			java.lang.reflect.Type type = new TypeToken<List<RolDTO>>() {
			}.getType();
			List<RolDTO> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<RolDTO>();
		}
	}


	/**
	 * SERWEB000195
	 * 
	 * @param
	 * @return
	 */
	public static Municipio getMunicipiosid(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codMunicipio\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MUNICIPIO_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			Municipio list = gson.fromJson(out, Municipio.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Municipio();
		}
	}

	/**
	 * SERWEB000196
	 * 
	 * @param
	 * @return
	 */
	public static Idioma getIdiomaid(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codIdioma\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IDIOMA_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			Idioma list = gson.fromJson(out, Idioma.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Idioma();
		}
	}

	/**
	 * SERWEB000198
	 * 
	 * @param
	 * @return
	 */
	public static CmActivarUsuarios setCmActivarUsuarios(CmActivarUsuarios cmActivarUsuarios) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmActivarUsuarios);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CMACTIVACION_USUARIOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmActivarUsuarios list = gson.fromJson(out, CmActivarUsuarios.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmActivarUsuarios err = new CmActivarUsuarios();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000199
	 * 
	 * @param
	 * @return
	 */
	public static CmActivarUsuarios getCmActivarUsuarios(long codCmActivarUsuarios) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codCmActivarUsuarios\":" + codCmActivarUsuarios + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CMACTIVACION_USUARIOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmActivarUsuarios list = gson.fromJson(out, CmActivarUsuarios.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmActivarUsuarios err = new CmActivarUsuarios();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000200
	 * 
	 * @param
	 * @return
	 */
	public static ProcesosCargaMasiva setProcesosCargaMasiva(ProcesosCargaMasiva procesosCargaMasiva) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = gson.toJson(procesosCargaMasiva);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PROCEOS_MARIVOS, json, token, TIME_OUT,	auditoriaSeguridad);
			ProcesosCargaMasiva list = gson.fromJson(out, ProcesosCargaMasiva.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			ProcesosCargaMasiva err = new ProcesosCargaMasiva();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000201
	 * 
	 * @param
	 * @return
	 */
	public static ProcesosCargaMasivaExt getProcesosCargaMasiva(long codProcesoCargaMasiva) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProcesoCargaMasiva + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PROCEOS_MARIVOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			ProcesosCargaMasivaExt list = gson.fromJson(out, ProcesosCargaMasivaExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			ProcesosCargaMasivaExt err = new ProcesosCargaMasivaExt();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000202
	 * 
	 * @param
	 * @return
	 */
	public static SequenciasSigep getSequenciasSigep(String tablaNombre) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"tablaNombre\":\"" + tablaNombre + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SEQUENCIA_NOMBRE, json, token, TIME_OUT,
					auditoriaSeguridad);
			SequenciasSigep list = gson.fromJson(out, SequenciasSigep.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			SequenciasSigep err = new SequenciasSigep();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	
	/**
	 * Servicio que elimina el codigo sigep creado de la tabla sequencias_sigep y que no será utilizado
	 */
	public static SequenciasSigep setSequenciasSigepCodigoSIGEP(String tablaNombre) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"tablaNombre\":\"" + tablaNombre + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_SECUENCIA_CODIGO_SIGEP_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			SequenciasSigep list = gson.fromJson(out, SequenciasSigep.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			SequenciasSigep err = new SequenciasSigep();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}
	
	/**
	 * SERWEB000203
	 * 
	 * @param
	 * @return
	 */
	public static SequenciasSigep setSequenciasSigep(SequenciasSigep sequenciasSigep) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(sequenciasSigep);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_SEQUENCIA_NOMBRE, json, token, TIME_OUT,
					auditoriaSeguridad);
			SequenciasSigep list = gson.fromJson(out, SequenciasSigep.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			SequenciasSigep err = new SequenciasSigep();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000206
	 * 
	 * @param
	 * @return
	 */
	public static CmHvInformacionBasica setCmHvInformacionBasica(CmHvInformacionBasica CmHvInformacionBasica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(CmHvInformacionBasica);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_HV_INFORMACION_BASICA, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmHvInformacionBasica list = gson.fromJson(out, CmHvInformacionBasica.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvInformacionBasica err = new CmHvInformacionBasica();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000206
	 * 
	 * @param
	 * @return
	 */
	public static CmHvInformacionBasica setCM_HV_INFORMACION_BASICA(String CmHvInformacionBasicaJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_HV_INFORMACION_BASICA, CmHvInformacionBasicaJSON,
					token, TIME_OUT, auditoriaSeguridad);
			CmHvInformacionBasica list = gson.fromJson(out, CmHvInformacionBasica.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvInformacionBasica err = new CmHvInformacionBasica();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000207
	 * 
	 * @param
	 * @return
	 */
	public static CmConfiguracion setCmConfiguracion(CmConfiguracion cmConfiguracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmConfiguracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CONFIGURACION, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmConfiguracion list = gson.fromJson(out, CmConfiguracion.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmConfiguracion err = new CmConfiguracion();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000208
	 * 
	 * @param cmConfiguracion
	 * @return Metodo que implementa el servicios de retornar la lista de
	 *         CmConfiguracion, por medio del parametros opcionales
	 *         nombreCampo,descripcionCampo,flgActivo
	 */
	public static List<CmConfiguracion> getCmConfiguracion(CmConfiguracion cmConfiguracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmConfiguracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_CONFIGURACION, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmConfiguracion>>() {
			}.getType();
			List<CmConfiguracion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<CmConfiguracion>();
		}
	}

	/**
	 * SERWEB000209
	 * 
	 * @param
	 * @return
	 */
	public static CmCrearUsuarios setCmCrearUsuarios(CmCrearUsuarios cmCrearUsuarios) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmCrearUsuarios);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_USUARIOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmCrearUsuarios list = gson.fromJson(out, CmCrearUsuarios.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearUsuarios err = new CmCrearUsuarios();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB0002010
	 * 
	 * @param
	 * @return
	 */
	public static CmHvEducacionDesaHumano setCmHvEducacionDesaHumano(CmHvEducacionDesaHumano cmHvEducacionDesaHumano) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmHvEducacionDesaHumano);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_EDUCACION_DES_HUMANO, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmHvEducacionDesaHumano list = gson.fromJson(out, CmHvEducacionDesaHumano.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionDesaHumano err = new CmHvEducacionDesaHumano();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB0002011
	 * 
	 * @param
	 * @return
	 */
	public static CmHvEducacionFormal setCmHvEducacionFormal(CmHvEducacionFormal cmHvEducacionFormal) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmHvEducacionFormal);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_EDUCACION_FORMAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmHvEducacionFormal list = gson.fromJson(out, CmHvEducacionFormal.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionFormal err = new CmHvEducacionFormal();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB002012
	 * 
	 * @param procesosCargaMasivaExt
	 * @param limitEnd
	 * @param limitInit
	 * @return
	 */
	public static List<ProcesosCargaMasivaExt> getProcesosCargaMasivaCompleto(ProcesosCargaMasivaExt procesosCargaMasivaExt, int limitInit, int limitEnd) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm.ss.s").create();
		try {
			procesosCargaMasivaExt.setLimitInit(limitInit);
			procesosCargaMasivaExt.setLimitEnd(limitEnd);
			String json = gson.toJson(procesosCargaMasivaExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PROCESOS_MASIVOS_FULL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ProcesosCargaMasivaExt>>() {
			}.getType();
			List<ProcesosCargaMasivaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<ProcesosCargaMasivaExt>();
		}
	}

	/**
	 * SERWEB0002013
	 * 
	 * @param
	 * @return
	 */
	public static List<CmCrearUsuarios> getCmCrearUsuarios(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_CREAR_USUARIOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmCrearUsuarios>>() {
			}.getType();
			List<CmCrearUsuarios> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearUsuarios err = new CmCrearUsuarios();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmCrearUsuarios>();
		}
	}

	/**
	 * SERWEB0002014
	 * 
	 * @param
	 * @return
	 */
	public static CmHvEvaluacionDesempeno setCmHvEvaluacionDesempeno(CmHvEvaluacionDesempeno CmHvEvaluacionDesempeno) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(CmHvEvaluacionDesempeno);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_EVALUACION_DESEMPENO, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmHvEvaluacionDesempeno list = gson.fromJson(out, CmHvEvaluacionDesempeno.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEvaluacionDesempeno err = new CmHvEvaluacionDesempeno();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB0002014
	 * 
	 * @param
	 * @return
	 */
	public static List<CmActivarUsuarios> getCmActivarUsuarios(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_ACTIVAR_USUARIOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmActivarUsuarios>>() {
			}.getType();
			List<CmActivarUsuarios> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmActivarUsuarios err = new CmActivarUsuarios();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmActivarUsuarios>();
		}
	}

	/**
	 * SERWEB0002015
	 * 
	 * @param
	 * @return
	 */
	public static List<CmHvEvaluacionDesempeno> getCmEvaluacionDesempeno(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_EVALUACION_DESEMPENO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmHvEvaluacionDesempeno>>() {
			}.getType();
			List<CmHvEvaluacionDesempeno> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEvaluacionDesempeno err = new CmHvEvaluacionDesempeno();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmHvEvaluacionDesempeno>();
		}
	}

	/**
	 * 
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
	 * Servicios para cargas masivas
	 * 
	 * @param cmCrearUsuariosJSON
	 * @return
	 */

	public static CmCrearUsuarios setCM_CREAR_USUARIOS(String cmCrearUsuariosJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_USUARIOS, cmCrearUsuariosJSON, token,
					TIME_OUT, auditoriaSeguridad);
			CmCrearUsuarios list = gson.fromJson(out, CmCrearUsuarios.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearUsuarios err = new CmCrearUsuarios();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * 
	 * @param cmActivarUsuariosJSON
	 * @return
	 */
	public static CmActivarUsuarios setCM_ACTIVAR_USUARIOS(String cmActivarUsuariosJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CMACTIVACION_USUARIOS, cmActivarUsuariosJSON, token,
					TIME_OUT, auditoriaSeguridad);
			CmActivarUsuarios list = gson.fromJson(out, CmActivarUsuarios.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmActivarUsuarios err = new CmActivarUsuarios();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000216 Obtiene la lista de recursos por permiso rol
	 * 
	 * 
	 * @param rol
	 *            valor del rol a consultar
	 * @param limitIni
	 *            valor inicial del tama?o de la paginacion del data lazy
	 * @param limitFin
	 *            valor final del tama?o de la paginacion del data lazy
	 * 
	 * @return {@link List} de {@link RecursoAccionDTO}
	 */
	public static List<RecursoAccionDTO> getRecursosAccion(String rol, String recurso, int limitIni, int limitFin) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"nombreRol\":\"" + rol + "\",\"recurso\":\"" + recurso + "\",\"limitIni\":" + limitIni + ","
				+ "\"limitFin\":" + limitFin + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RECURSOS_ACCION, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RecursoAccionDTO>>() {
			}.getType();
			out = out.replace("\"codRecurso\":", "\"id\":");
			List<RecursoAccionDTO> list = new LinkedList<>();
			try {
				list = gson.fromJson(out, type);
			} catch (JsonSyntaxException e) {
				RecursoAccionDTO recursoResponse = gson.fromJson(out, RecursoAccionDTO.class);
				list.add(recursoResponse);
			}
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * SERWEB000217 Gestiona el CRUD de un {@link PermisoRolAccion}
	 * 
	 * 
	 * @param rol
	 *            valor del rol a consultar
	 * 
	 * @return {@link List} de {@link RecursoAccionDTO}
	 */
	public static CmHvEducacionDesaHumano setCM_HV_EDUCACION_DESA_HUMANO(String cmHvEducacionDesaHumanoJson) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_EDUCACION_DES_HUMANO,
					cmHvEducacionDesaHumanoJson, token, TIME_OUT, auditoriaSeguridad);
			CmHvEducacionDesaHumano list = gson.fromJson(out, CmHvEducacionDesaHumano.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionDesaHumano err = new CmHvEducacionDesaHumano();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmHvEducacionFormal setCM_HV_EDUCACION_FORMAL(String cmHvEducacionFormalJson) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_EDUCACION_FORMAL, cmHvEducacionFormalJson, token,
					TIME_OUT, auditoriaSeguridad);
			CmHvEducacionFormal list = gson.fromJson(out, CmHvEducacionFormal.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionFormal err = new CmHvEducacionFormal();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmHvEvaluacionDesempeno setCM_HV_EVALUACION_DESEMPENO(String CmHvEvaluacionDesempenoJson) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_EVALUACION_DESEMPENO,
					CmHvEvaluacionDesempenoJson, token, TIME_OUT, auditoriaSeguridad);
			CmHvEvaluacionDesempeno list = gson.fromJson(out, CmHvEvaluacionDesempeno.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEvaluacionDesempeno err = new CmHvEvaluacionDesempeno();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000219
	 * 
	 * @param CmHvEducacionOtros
	 * @return
	 */
	public static List<CmHvEducacionOtros> getCmHvEducacionOtros(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_EDUCACION_OTROS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmHvEducacionOtros>>() {
			}.getType();
			List<CmHvEducacionOtros> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEvaluacionDesempeno err = new CmHvEvaluacionDesempeno();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmHvEducacionOtros>();
		}
	}

	/**
	 * 
	 * SERWEB000219
	 * 
	 * @param CmHvEducacionOtros
	 * @return
	 */
	public static CmHvEducacionOtros getCM_HV_EDUCACION_OTROS(long codProcesoCargaMasiva) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProcesoCargaMasiva + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_EDUCACION_OTROS, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmHvEducacionOtros list = gson.fromJson(out, CmHvEducacionOtros.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionOtros err = new CmHvEducacionOtros();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000218
	 * 
	 * @param
	 * @return
	 */

	public static CmHvEducacionOtros setCM_HV_EDUCACION_OTROS(String CmHvEducacionOtrosJson) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_HV_EDUCACION_OTROS, CmHvEducacionOtrosJson,
					token, TIME_OUT, auditoriaSeguridad);
			CmHvEducacionOtros list = gson.fromJson(out, CmHvEducacionOtros.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionOtros err = new CmHvEducacionOtros();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000220
	 * 
	 * @param
	 * @return
	 */

	public static CmHvEducacionIdiomas setCM_HV_EDUCACION_IDIOMAS(String CmHvEducacionIdiomasJson) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_HV_EDUCACION_IDIOMAS, CmHvEducacionIdiomasJson,
					token, TIME_OUT, auditoriaSeguridad);
			CmHvEducacionIdiomas list = gson.fromJson(out, CmHvEducacionIdiomas.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionIdiomas err = new CmHvEducacionIdiomas();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000220
	 * 
	 * @param
	 * @return
	 */

	public static CmHvEducacionIdiomas set_CM_HV_EDUCACION_IDIOMAS(String CmHvEducacionIdiomasJson) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_HV_EDUCACION_OTROS, CmHvEducacionIdiomasJson,
					token, TIME_OUT, auditoriaSeguridad);
			CmHvEducacionIdiomas list = gson.fromJson(out, CmHvEducacionIdiomas.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionIdiomas err = new CmHvEducacionIdiomas();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000219
	 * 
	 * @param CmHvEducacionIdiomas
	 * @return
	 */

	public static List<CmHvEducacionIdiomas> getCmHvEducacionIdiomas(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_EDUCACION_IDIOMAS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmHvEducacionIdiomas>>() {
			}.getType();
			List<CmHvEducacionIdiomas> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionIdiomas err = new CmHvEducacionIdiomas();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmHvEducacionIdiomas>();
		}
	}

	/**
	 * SERWEB000219
	 * 
	 * @param CmHvEducacionIdiomas
	 * @return
	 */
	public static CmHvEducacionIdiomas getCM_HV_EDUCACION_IDIOMAS(long codProcesoCargaMasiva) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProcesoCargaMasiva + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_EDUCACION_OTROS, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmHvEducacionIdiomas list = gson.fromJson(out, CmHvEducacionIdiomas.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionIdiomas err = new CmHvEducacionIdiomas();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000222
	 * 
	 * @param
	 * @return
	 */

	public static CmHvExperienciaLaboral setCM_HV_EXPERIENCIA_LABORAL(String CmHvExperienciaLaboralJson) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_HV_EXPERIENCIA_LABORAL,
					CmHvExperienciaLaboralJson, token, TIME_OUT, auditoriaSeguridad);
			CmHvExperienciaLaboral list = gson.fromJson(out, CmHvExperienciaLaboral.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvExperienciaLaboral err = new CmHvExperienciaLaboral();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000221
	 * 
	 * @param CmHvExperienciaLaboral
	 * @return
	 */

	public static List<CmHvExperienciaLaboral> getCmHvExperienciaLaboral(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_EXPERIENCIA_LABORAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmHvExperienciaLaboral>>() {
			}.getType();
			List<CmHvExperienciaLaboral> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvExperienciaLaboral err = new CmHvExperienciaLaboral();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmHvExperienciaLaboral>();
		}
	}

	/**
	 * SERWEB000221
	 * 
	 * @param CmHvExperienciaLaboral
	 * @return
	 */
	public static CmHvExperienciaLaboral getCM_HV_EXPERIENCIA_LABORAL(long codProcesoCargaMasiva) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProcesoCargaMasiva + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_EXPERIENCIA_LABORAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmHvExperienciaLaboral list = gson.fromJson(out, CmHvExperienciaLaboral.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvExperienciaLaboral err = new CmHvExperienciaLaboral();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000224
	 * 
	 * @param
	 * @return
	 */

	public static CmHvExperienciaLaboralDocen setCM_HV_EXPERIENCIA_LABORAL_DOCEN(
			String CmHvExperienciaLaboralDocenJson) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_HV_EXPERIENCIA_LABORAL_DOCEN,
					CmHvExperienciaLaboralDocenJson, token, TIME_OUT, auditoriaSeguridad);
			CmHvExperienciaLaboralDocen list = gson.fromJson(out, CmHvExperienciaLaboralDocen.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvExperienciaLaboralDocen err = new CmHvExperienciaLaboralDocen();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000223
	 * 
	 * @param codProcesoCargaMasiva
	 * @return
	 */

	public static List<CmHvExperienciaLaboralDocen> getCmHvExperienciaLaboralDocen(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_EXPERIENCIA_LABORAL_DOCEN, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmHvExperienciaLaboralDocen>>() {
			}.getType();
			List<CmHvExperienciaLaboralDocen> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvExperienciaLaboralDocen err = new CmHvExperienciaLaboralDocen();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmHvExperienciaLaboralDocen>();
		}
	}

	/**
	 * SERWEB000223
	 * 
	 * @param codProcesoCargaMasiva
	 * @return
	 */
	public static CmHvExperienciaLaboralDocen getCM_HV_EXPERIENCIA_LABORAL_DOCEN(long codProcesoCargaMasiva) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProcesoCargaMasiva + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_EXPERIENCIA_LABORAL_DOCEN, json, token,
					TIME_OUT, auditoriaSeguridad);
			CmHvExperienciaLaboralDocen list = gson.fromJson(out, CmHvExperienciaLaboralDocen.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvExperienciaLaboralDocen err = new CmHvExperienciaLaboralDocen();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000225
	 * 
	 * @param codProceso
	 * @return
	 */

	public static List<CmHvInformacionBasica> getCmHvInformacionBasica(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_HV_INFORMACION_BASICA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmHvInformacionBasica>>() {
			}.getType();
			List<CmHvInformacionBasica> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvInformacionBasica err = new CmHvInformacionBasica();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmHvInformacionBasica>();
		}
	}

	/**
	 * SERWEB000226
	 * 
	 * @param codProceso
	 * @return
	 */

	public static List<CmHvEducacionFormal> getCmHvEducacionFormal(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EDUCACION_FORMAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmHvEducacionFormal>>() {
			}.getType();
			List<CmHvEducacionFormal> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionFormal err = new CmHvEducacionFormal();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmHvEducacionFormal>();
		}
	}

	/**
	 * SERWEB000229
	 * 
	 * @param
	 * @return
	 */
	public static List<CmSituacionesAdministrativas> getCmSituacionesAdministrativas(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_SITUACIONES_ADMINISTRATIVAS, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmSituacionesAdministrativas>>() {
			}.getType();
			List<CmSituacionesAdministrativas> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmSituacionesAdministrativas err = new CmSituacionesAdministrativas();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmSituacionesAdministrativas>();
		}
	}

	public static CmSituacionesAdministrativas setCM_SITUACIONES_ADMINISTRATIVAS(
			String CmSituacionesAdministrativasJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_SITUACIONES_ADMINISTRATIVAS,
					CmSituacionesAdministrativasJSON, token, TIME_OUT, auditoriaSeguridad);
			CmSituacionesAdministrativas list = gson.fromJson(out, CmSituacionesAdministrativas.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmSituacionesAdministrativas err = new CmSituacionesAdministrativas();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000227
	 * 
	 * @param codProceso
	 * @return
	 */

	public static List<CmHvEducacionDesaHumano> getCmHvEducacionDesaHumano(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_EDUCACION_DES_HUMANO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmHvEducacionDesaHumano>>() {
			}.getType();
			List<CmHvEducacionDesaHumano> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmHvEducacionDesaHumano err = new CmHvEducacionDesaHumano();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmHvEducacionDesaHumano>();
		}
	}

	/**
	 * SERWEB000231
	 * 
	 * @param
	 * @return
	 */
	public static List<CmContratos> getCmContratos(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_CONTRATOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmContratos>>() {
			}.getType();
			List<CmContratos> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmContratos err = new CmContratos();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmContratos>();
		}
	}

	/**
	 * SERWEB000230
	 * 
	 * @param
	 * @return
	 */
	public static CmContratos setCmContratos(CmContratos CmContratos) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(CmContratos);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CONTRATOS, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmContratos list = gson.fromJson(out, CmContratos.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmContratos err = new CmContratos();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * SERWEB000231
	 * 
	 * @param
	 * @return
	 */
	public static List<CmVinculaciones> getCmVinculaciones(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_VINCULACIONES, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmVinculaciones>>() {
			}.getType();
			List<CmVinculaciones> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmVinculaciones err = new CmVinculaciones();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmVinculaciones>();
		}
	}

	/**
	 * SERWEB000232
	 * 
	 * @param
	 * @return
	 */
	public static CmVinculaciones setCmVinculaciones(CmVinculaciones CmVinculaciones) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(CmVinculaciones);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_VINCULACIONES, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmVinculaciones list = gson.fromJson(out, CmVinculaciones.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmVinculaciones err = new CmVinculaciones();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmContratos setCM_CONTRATOS(String CmContratosJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CONTRATOS, CmContratosJSON, token, TIME_OUT,
					auditoriaSeguridad);
			CmContratos list = gson.fromJson(out, CmContratos.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmContratos err = new CmContratos();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmVinculaciones setCM_VINCULACIONES(String CmVinculacionesJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_VINCULACIONES, CmVinculacionesJSON, token,
					TIME_OUT, auditoriaSeguridad);
			CmVinculaciones list = gson.fromJson(out, CmVinculaciones.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmVinculaciones err = new CmVinculaciones();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * @param cmRoles
	 * @return
	 */
	public static CmRoles setCmRoles(CmRoles cmRoles) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmRoles);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_ROLES, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmRoles list = gson.fromJson(out, CmRoles.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmRoles err = new CmRoles();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmRoles setCM_ROLES(String cmRolesJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_ROLES, cmRolesJSON, token, TIME_OUT,
					auditoriaSeguridad);
			CmRoles list = gson.fromJson(out, CmRoles.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmRoles err = new CmRoles();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * 
	 * @param codProceso
	 * @return
	 */
	public static List<CmRoles> getCmRoles(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_ROLES, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmRoles>>() {
			}.getType();
			List<CmRoles> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmRoles err = new CmRoles();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmRoles>();
		}
	}

	/**
	 * @param cmCrearEntidad
	 * @return
	 */
	public static CmCrearEntidad setCmCrearEntidad(CmCrearEntidad cmCrearEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmCrearEntidad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmCrearEntidad list = gson.fromJson(out, CmCrearEntidad.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearEntidad err = new CmCrearEntidad();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmCrearEntidad setCM_CREAR_ENTIDAD(String cmCrearEntidadJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_ENTIDAD, cmCrearEntidadJSON, token,
					TIME_OUT, auditoriaSeguridad);
			CmCrearEntidad list = gson.fromJson(out, CmCrearEntidad.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearEntidad err = new CmCrearEntidad();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * 
	 * @param codProceso
	 * @return
	 */
	public static List<CmCrearEntidad> getCmCrearEntidad(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_CREAR_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmCrearEntidad>>() {
			}.getType();
			List<CmCrearEntidad> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearEntidad err = new CmCrearEntidad();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmCrearEntidad>();
		}
	}

	/**
	 * @param CmCrearEstructura
	 * @return
	 */
	public static CmCrearEstructura setCmCrearEstructura(CmCrearEstructura cmCrearEstructura) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmCrearEstructura);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_ESTRUCTURA, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmCrearEstructura list = gson.fromJson(out, CmCrearEstructura.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearEstructura err = new CmCrearEstructura();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmCrearEstructura setCM_CREAR_ESTRUCTURA(String cmCrearEstructuraJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_ESTRUCTURA, cmCrearEstructuraJSON, token,
					TIME_OUT, auditoriaSeguridad);
			CmCrearEstructura list = gson.fromJson(out, CmCrearEstructura.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearEstructura err = new CmCrearEstructura();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * 
	 * @param codProceso
	 * @return
	 */
	public static List<CmCrearEstructura> getCmCrearEstructura(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_CREAR_ESTRUCTURA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmCrearEstructura>>() {
			}.getType();
			List<CmCrearEstructura> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearEstructura err = new CmCrearEstructura();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmCrearEstructura>();
		}
	}

	/**
	 * @param CmCrearGrupo
	 * @return
	 */
	public static CmCrearGrupo setCmCrearGrupo(CmCrearGrupo cmCrearGrupo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmCrearGrupo);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_GRUPO, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmCrearGrupo list = gson.fromJson(out, CmCrearGrupo.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearGrupo err = new CmCrearGrupo();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmCrearGrupo setCM_CREAR_GRUPO(String cmCrearGrupoJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_GRUPO, cmCrearGrupoJSON, token, TIME_OUT,
					auditoriaSeguridad);
			CmCrearGrupo list = gson.fromJson(out, CmCrearGrupo.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearGrupo err = new CmCrearGrupo();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * 
	 * @param codProceso
	 * @return
	 */
	public static List<CmCrearGrupo> getCmCrearGrupo(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_CREAR_GRUPO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmCrearGrupo>>() {
			}.getType();
			List<CmCrearGrupo> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearGrupo err = new CmCrearGrupo();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmCrearGrupo>();
		}
	}

	/**
	 * @param CmCrearPlanta
	 * @return
	 */
	public static CmCrearPlanta setCmCrearPlanta(CmCrearPlanta cmCrearPlanta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmCrearPlanta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_PLANTA, json, token, TIME_OUT,
					auditoriaSeguridad);
			CmCrearPlanta list = gson.fromJson(out, CmCrearPlanta.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearPlanta err = new CmCrearPlanta();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmCrearPlanta setCM_CREAR_PLANTA(String cmCrearPlantaJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_PLANTA, cmCrearPlantaJSON, token, TIME_OUT,
					auditoriaSeguridad);
			CmCrearPlanta list = gson.fromJson(out, CmCrearPlanta.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearPlanta err = new CmCrearPlanta();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * 
	 * @param codProceso
	 * @return
	 */
	public static List<CmCrearPlanta> getCmCrearPlanta(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_CREAR_PLANTA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmCrearPlanta>>() {
			}.getType();
			List<CmCrearPlanta> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearPlanta err = new CmCrearPlanta();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmCrearPlanta>();
		}
	}

	/**
	 * @param cmCrearNomenclaturaSalarial
	 * @return
	 */
	public static CmCrearNomenclaturaSalarial setCmCrearNomenclaturaSalarial(
			CmCrearNomenclaturaSalarial cmCrearNomenclaturaSalarial) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cmCrearNomenclaturaSalarial);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_NOMENCLATURA_SALARIAL, json, token,
					TIME_OUT, auditoriaSeguridad);
			CmCrearNomenclaturaSalarial list = gson.fromJson(out, CmCrearNomenclaturaSalarial.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearNomenclaturaSalarial err = new CmCrearNomenclaturaSalarial();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	public static CmCrearNomenclaturaSalarial setCM_CREAR_NOMENCLATURA_SALARIAL(
			String cmCrearNomenclaturaSalarialJSON) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CM_CREAR_NOMENCLATURA_SALARIAL,
					cmCrearNomenclaturaSalarialJSON, token, TIME_OUT, auditoriaSeguridad);
			CmCrearNomenclaturaSalarial list = gson.fromJson(out, CmCrearNomenclaturaSalarial.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearNomenclaturaSalarial err = new CmCrearNomenclaturaSalarial();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return err;
		}
	}

	/**
	 * 
	 * @param codProceso
	 * @return
	 */
	public static List<CmCrearNomenclaturaSalarial> getCmCrearNomenclaturaSalarial(Long codProceso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codProcesoCargaMasiva\":" + codProceso + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CM_CREAR_NOMENCLATURA_SALARIAL, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CmCrearNomenclaturaSalarial>>() {
			}.getType();
			List<CmCrearNomenclaturaSalarial> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			CmCrearNomenclaturaSalarial err = new CmCrearNomenclaturaSalarial();
			err.setError(true);
			err.setMensajeTecnico(e.getMessage());
			return new ArrayList<CmCrearNomenclaturaSalarial>();
		}
	}

	/**
	 * SERWEB000158
	 * 
	 * @param recursoExt
	 * @return Metodo que consume el servicio de trae una lista de Objetos
	 *         RecursoExt, mediante un filtro de codigoVentana, y un arreglo de
	 *         roles Este metodo devuelve una lista con los recursos a los que
	 *         tiene una persona segun sus roles en sesion
	 */
	public static List<RecursoExt> getRecursoList(RecursoExt recurso) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(recurso);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_RECURSO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RecursoExt>>() {
			}.getType();
			List<RecursoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<RecursoExt>();
		}
	}

	/**
	 * 
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
		}
	}

	/**
	 * SERWEB000217 Gestiona el CRUD de un {@link PermisoRolAccion}
	 * 
	 * 
	 * @param rol
	 *            valor del rol a consultar
	 * 
	 * @return {@link List} de {@link RecursoAccionDTO}
	 */
	public static ErrorMensajes procesarPermiso(ErrorMensajes permisoRolAccion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		ErrorMensajes permiso = permisoRolAccion;
		try {
			String json = gson.toJson(permiso);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PERMISOS_ROL_ACCION, json, token, TIME_OUT,
					auditoriaSeguridad);

			try {
				if (permisoRolAccion instanceof PermisoRolAccion) {
					permiso = gson.fromJson(out, PermisoRolAccion.class);
				} else if (permisoRolAccion instanceof PermisoRol) {
					permiso = gson.fromJson(out, PermisoRol.class);
				} else {
					throw new JsonSyntaxException("" + permisoRolAccion.getClass() + " no soportada");
				}
			} catch (JsonSyntaxException e) {
				throw new JsonSyntaxException(e);
			}
			invalidToken(out);
			return permiso;
		} catch (JsonSyntaxException e) {
			permiso.setError(true);
			permiso.setMensajeTecnico(e.getMessage());
		}
		return permiso;
	}

	/**
	 * 
	 * Elaborado por: Jose Viscaya 8 ene. 2019
	 * 
	 * @param codRolPadre
	 * @return
	 */
	public static List<Rol> getRolBase() {
		getToken();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROLES_BASE, "", token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Rol>>() {
			}.getType();
			List<Rol> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Rol>();
		}
	}

	/**
	 * 
	 * Elaborado por: Jose Viscaya 9 ene. 2019
	 * 
	 * @param rolExt
	 * @return
	 */
	public static List<RolExt> getRolPorPersonaEntidad(RolExt rolExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();

		try {
			String json = gson.toJson(rolExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROL_POR_PERSONA_ENTIDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RolExt>>() {
			}.getType();
			List<RolExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<RolExt>();
		}
	}

	/**
	 * 
	 * @param codTablaParametrica
	 * @return Metodo que consume el servicio elimina fisicamente un registro de
	 *         la tabla parametrico El metodo retorna un String con true, de lo
	 *         contrario el servicio retornara el error ocurrido
	 */
	public static String eliminarParametrica(long codTablaParametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codTablaParametrica\":" + codTablaParametrica + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.ELIMINAR_PARAMETRICA, json, token, TIME_OUT,
					auditoriaSeguridad);
			return out;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "false";
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Usuario getUsuarioPorId(BigDecimal id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codUsuario\":\"" + id + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_POR_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			Usuario list = gson.fromJson(out, Usuario.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SERWEB000154
	 * 
	 * @param resultadoPregunta
	 * @return Metodo que trae estadisticas del nivel Jerarquico de las personas
	 *         que respondieron a la pregunta Nota: es necesario que se envie el
	 *         codPreguntaOpinion, puntaje y limites de la consulta.
	 */
	public static List<ResultadoPreguntaExt> getGraficoNivelJerarquico(ResultadoPregunta resultadoPregunta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(resultadoPregunta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RESPUESTA_NIVEL_JERARQUICO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ResultadoPreguntaExt>>() {
			}.getType();
			List<ResultadoPreguntaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ResultadoPreguntaExt>();
		}
	}

	/**
	 * 
	 * @param resultadoPregunta
	 * @return Metodo que trae informacion de las respuestas dadas a una
	 *         pregunta en especifico Nota: es necesario que se envie el
	 *         codPreguntaOpinion
	 */
	public static List<ResultadoPreguntaExt> getInformacionRespuestas(ResultadoPregunta resultadoPregunta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(resultadoPregunta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_INFORMACION_RESPUESTA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ResultadoPreguntaExt>>() {
			}.getType();
			List<ResultadoPreguntaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ResultadoPreguntaExt>();
		}
	}

	/**
	 * SERWEB000154
	 * 
	 * @param resultadoPregunta
	 * @return Metodo que trae estadisticas del Reten social de las personas que
	 *         respondieron a la pregunta Nota: es necesario que se envie el
	 *         codPreguntaOpinion, puntaje y limites de la consulta.
	 */
	public static List<ResultadoPreguntaExt> getGraficoRetenSocial(ResultadoPregunta resultadoPregunta) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(resultadoPregunta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RESPUESTA_RETEN_SOCIAL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ResultadoPreguntaExt>>() {
			}.getType();
			List<ResultadoPreguntaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ResultadoPreguntaExt>();
		}
	}

	/**
	 * 
	 * Elaborado por: Jose Viscaya 11 ene. 2019
	 * 
	 * @param auditoriaConfiguracion
	 * @return
	 */
	public static List<AuditoriaConfiguracion> getAuditoriaConfiguracionFiltro(
			AuditoriaConfiguracion auditoriaConfiguracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(auditoriaConfiguracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUDITORIA_CONFIGURACION_FILTRO, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<AuditoriaConfiguracion>>() {
			}.getType();
			List<AuditoriaConfiguracion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * Elaborado por: Jose Viscaya Jan 14, 2019
	 * 
	 * @param usuarioSession
	 * @return
	 */
	public static List<UsuarioSession> getUsuarioSession(UsuarioSession usuarioSession) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(usuarioSession);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_SESSION, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<UsuarioSession>>() {
			}.getType();
			List<UsuarioSession> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * Elaborado Por: Jose Viscaya 11 feb. 2019 ComunicacionServiciosSis.java
	 * 
	 * @param codPersona
	 * @return
	 */
	public static List<EntidadExt> getEntidadesPersonaRol(Long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PERSONA_ROL, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadExt>>() {
			}.getType();
			List<EntidadExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * Elaborado por: Jose Viscaya Jan 14, 2019
	 * 
	 * @param usuarioSession
	 * @return
	 */
	public static UsuarioSession setUsuarioSession(UsuarioSession usuarioSession) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = gson.toJson(usuarioSession);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_USUARIO_SESSION, json, token, TIME_OUT,
					auditoriaSeguridad);
			UsuarioSession list = gson.fromJson(out, UsuarioSession.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new UsuarioSession();
		}
	}

	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @param usuarioSession
	 * @return
	 * @Fecha :26 feb. 2019 ComunicacionServiciosSis.java
	 */
	public static List<UsuarioExt> getUsuarioRolesValidarOP(Long codUsuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = "{\"codUsuario\":" + codUsuario + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROLES_VALIDAR_OP, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<UsuarioExt>>() {
			}.getType();
			List<UsuarioExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @param codUsuario
	 * @return
	 * @Fecha :26 feb. 2019 ComunicacionServiciosSis.java
	 */
	public static boolean updateUsuarioOP(Long codUsuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = "{\"codUsuario\":" + codUsuario + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.UPDATE_USUARIO_OP, json, token, TIME_OUT,
					auditoriaSeguridad);
			Usuario list = gson.fromJson(out, Usuario.class);
			invalidToken(out);
			return list.isError();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: 8 may. 2019, 10:04:14
	 * @File: ComunicacionServiciosSis.java
	 * @param rangoEdad
	 * @return
	 */
	public static List<RangoEdad> getRangoedadFiltro(RangoEdad rangoEdad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(rangoEdad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RANGO_EDAD_FILTRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RangoEdad>>() {
			}.getType();
			List<RangoEdad> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: 8 may. 2019, 10:05:36
	 * @File: ComunicacionServiciosSis.java
	 * @param rangoEdad
	 * @return
	 */
	public static RangoEdad setRangoEdad(RangoEdad rangoEdad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(rangoEdad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_RANGO_EDAD, json, token, TIME_OUT,
					auditoriaSeguridad);
			RangoEdad list = gson.fromJson(out, RangoEdad.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new RangoEdad();
		}
	}

	/**
	 * SERWEB000124
	 * 
	 * @param parametrica
	 * @return Metodo que retorna los paises que cumplen con el filtro enviado
	 */
	public static List<Pais> getPaisFiltro(Pais pais) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(pais);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PAIS_FILTRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Pais>>() {
			}.getType();
			List<Pais> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Obtener Departamento Filtro
	 * 
	 * @param
	 * @return
	 */
	public static List<Departamento> getDepartamentoFiltro(Departamento departamento) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(departamento);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPARTAMENTO_FILTRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Departamento>>() {
			}.getType();
			List<Departamento> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @Author: Maria Alejandra
	 * @Date: 08 May. 2019, 10:59:48
	 * @File: ComunicacionServiciosSis.java
	 * @param Pais
	 * @return
	 */
	public static Pais setPais(Pais pais) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(pais);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PAIS, json, token, TIME_OUT, auditoriaSeguridad);
			Pais nom = gson.fromJson(out, Pais.class);
			invalidToken(out);
			return nom;
		} catch (Exception ex) {
			return new Pais();
		}
	}

	/**
	 * 
	 * @Author: Maria Alejandra
	 * @Date: 08 May. 2019, 10:59:48
	 * @File: ComunicacionServiciosSis.java
	 * @param Departamento
	 * @return
	 */
	public static Departamento setDepartamento(Departamento departamento) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(departamento);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DEPARTAMENTO, json, token, TIME_OUT,
					auditoriaSeguridad);
			Departamento nom = gson.fromJson(out, Departamento.class);
			invalidToken(out);
			return nom;
		} catch (Exception ex) {
			return new Departamento();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: 8 may. 2019, 13:35:24
	 * @File: ComunicacionServiciosSis.java
	 * @param festivo
	 * @return
	 */
	public static List<Festivo> getFestivoFiltro(Festivo festivo) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(festivo);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_FESTIVO_FILTRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Festivo>>() {
			}.getType();
			List<Festivo> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @Author: Maria Alejandra
	 * @Date: 08 May. 2019, 10:59:48
	 * @File: ComunicacionServiciosSis.java
	 * @param Municipio
	 * @return Municipio
	 */
	public static Municipio setMunicipio(Municipio municipio) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(municipio);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_MUNICIPIO, json, token, TIME_OUT,
					auditoriaSeguridad);
			Municipio nom = gson.fromJson(out, Municipio.class);
			invalidToken(out);
			return nom;
		} catch (Exception ex) {
			return new Municipio();
		}
	}

	/**
	 * Obtener Municipio Filtro
	 * 
	 * @param
	 * @return
	 */
	public static List<Municipio> getMunicipioFiltro(Municipio municipio) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(municipio);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MUNICIPIO_FILTRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Municipio>>() {
			}.getType();
			List<Municipio> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: 8 may. 2019, 13:36:34
	 * @File: ComunicacionServiciosSis.java
	 * @param rangoEdad
	 * @return
	 */
	public static Festivo setFestivo(Festivo festivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(festivo);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_FESTIVO, json, token, TIME_OUT, auditoriaSeguridad);
			Festivo list = gson.fromJson(out, Festivo.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Festivo();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 9, 2019, 7:50:18 AM
	 * @File: ComunicacionServiciosSis.java
	 * @param idioma
	 * @return
	 */
	public static List<Idioma> getIdiomaFiltro(Idioma idioma) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(idioma);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IDIOMA_FILTRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Idioma>>() {
			}.getType();
			List<Idioma> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: May 9, 2019, 7:50:59 AM
	 * @File: ComunicacionServiciosSis.java
	 * @param idioma
	 * @return
	 */
	public static Idioma setIdioma(Idioma idioma) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(idioma);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_IDIOMA, json, token, TIME_OUT, auditoriaSeguridad);
			Idioma list = gson.fromJson(out, Idioma.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Idioma();
		}
	}

	/**
	 * @param codPais
	 * @return Metodo que consume el servicio elimina fisicamente un registro de
	 *         la tabla maestro pais El metodo retorna un String con true, de lo
	 *         contrario el servicio retornara el error ocurrido
	 */
	public static String eliminarPais(BigDecimal codPais) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPais\":" + codPais + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.ELIMINAR_PAIS, json, token, TIME_OUT,
					auditoriaSeguridad);
			return out;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "false";
		}
	}

	/**
	 * Elaborado por: Maria Alejandra C 20-05- 2019
	 * 
	 * @param codPais
	 * @return Pais
	 */
	public static Pais getPaisId(BigDecimal codPais) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = "{\"codPais\":" + codPais + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PAIS_ID, json, token, TIME_OUT, auditoriaSeguridad);
			Pais res = gson.fromJson(out, Pais.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Pais();
		}
	}

	/**
	 * Elaborado por: Maria Alejandra C 20-05- 2019
	 * 
	 * @param codDepartamento
	 * @return Departamento
	 */
	public static Departamento getDepartamentoId(BigDecimal codDepartamento) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = "{\"codDepartamento\":" + codDepartamento + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPARTAMENTO_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			Departamento res = gson.fromJson(out, Departamento.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Departamento();
		}
	}

	/**
	 * Pablo Quintana Retorna un rol dado su nombre parameter name (nombre de
	 * rol)
	 */
	public static List<Rol> getRolByNombre(String name) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = "{\"nombre\":" + "'"+ name + "'" +"}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ROL_POR_NOMBRE, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Rol>>() {
			}.getType();
			List<Rol> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	/**
	 * Elaborado por: Maria Alejandra C 20-05- 2019
	 * 
	 * @param codInstitucionEducativa
	 * @return InstitucionEducativa
	 */
	public static InstitucionEducativa getInstitucionEducativaId(BigDecimal codInstitucionEducativa) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = "{\"codInstitucionEducativa\":" + codInstitucionEducativa + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_INSTITUCION_EDUCATIVA_ID, json, token, TIME_OUT,
					auditoriaSeguridad);
			InstitucionEducativa res = gson.fromJson(out, InstitucionEducativa.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new InstitucionEducativa();
		}
	}

	/**
	 * 
	 * @param auditoriaExt
	 * @return Metodo que consume el servicio de trae una lista de AuditoriaExt
	 *         recibe como parametro el objeto AuditoriaExt
	 */
	public static List<AuditoriaExt> getAuditoriaByFilter(AuditoriaExt auditoriaExt) {

		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(auditoriaExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUDITORIA_BY_FILTER, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<AuditoriaExt>>() {
			}.getType();
			List<AuditoriaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<AuditoriaExt>();
		}
	}

	/**
	 * SERWEB000124
	 * 
	 * @param parametrica
	 * @return Metodo que retorna los paises que cumplen con el filtro enviado
	 */
	public static List<Pais> getPaisDuplicado(Pais pais) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(pais);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PAIS_DUPLICADO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Pais>>() {
			}.getType();
			List<Pais> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * SERWEB000124
	 * 
	 * @param parametrica
	 * @return Metodo que retorna los departamentos que cumplen con el filtro
	 *         enviado
	 */
	public static List<Departamento> getDepartamentoDuplicado(Departamento departamento) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(departamento);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPARTAMENTO_DUPLICADO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Departamento>>() {
			}.getType();
			List<Departamento> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * SERWEB000124
	 * 
	 * @param parametrica
	 * @return Metodo que retorna los idiomas que cumplen con el filtro enviado
	 */
	public static List<Idioma> getIdiomaDuplicado(Idioma idioma) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(idioma);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IDIOMA_DUPLICADO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Idioma>>() {
			}.getType();
			List<Idioma> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * @param parametrica
	 * @return Metodo que retorna los Municipios que cumplen con el filtro
	 *         enviado
	 */
	public static List<Municipio> getMunicipioDuplicado(Municipio municipio) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(municipio);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MUNICIPIO_DUPLICADO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Departamento>>() {
			}.getType();
			List<Municipio> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * SERWEB000124
	 * 
	 * @param parametrica
	 * @return Metodo que retorna los paises que cumplen con el filtro enviado
	 */
	public static List<Denominacion> getDenominacionDuplicado(Denominacion denominacion) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(denominacion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DENOMINACION_DUPLICADO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Denominacion>>() {
			}.getType();
			List<Denominacion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * @param parametrica
	 * @return Metodo que retorna los situaciones que cumplen con el filtro
	 *         enviado
	 */
	public static List<SituacionAdministrativa> getSituacionesDuplicado(SituacionAdministrativa situacionAdm) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(situacionAdm);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACION_ADMIN_DUPLICADO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdministrativa>>() {
			}.getType();
			List<SituacionAdministrativa> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * @param parametrica
	 * @return Metodo que retorna los rangos de edad que cumplen con el filtro
	 *         enviado
	 */
	public static List<RangoEdad> getRangoEdadDuplicado(RangoEdad rangoEdad) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(rangoEdad);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RANGO_EDAD_DUPLICADO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RangoEdad>>() {
			}.getType();
			List<RangoEdad> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	
	/**
	 * @param parametrica
	 * @return Metodo que retorna las instituciones dependiendo del filtro FLG_INS_EXTRANJERA
	 */
	public static List<InstitucionEducativa> getInstitucionByPais(InstitucionEducativa rangoEdad) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(rangoEdad);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_INSTITUCION_EDUCATIVA_BY_PAIS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<InstitucionEducativa>>() {
			}.getType();
			List<InstitucionEducativa> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 *  Metodo que retorna los cargos de HV que cumplen con el filtro enviado
	 * @param cargoHV
	 * @return List<CargoHojaVida>
	 */
	public static List<CargoHojaVidaExt> getCargoHVFiltro(CargoHojaVidaExt cargoHV) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(cargoHV);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_HV_FILTRO, json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CargoHojaVidaExt>>() {
			}.getType();
			List<CargoHojaVidaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * @Author: Maria Alejandra
	 * @Date: 29 Abril 2020, 10:59:48
	 * @File: ComunicacionServiciosSis.java
	 * @param cargoHojaVida
	 * @return CargoHojaVida
	 * Metodo que inserta o modifica un registro de la tabla cargo_hoja_vida
	 */
	public static CargoHojaVida setCargoHojaVida(CargoHojaVida cargoHojaVida) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cargoHojaVida);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CARGOS_HV, json, token, TIME_OUT, auditoriaSeguridad);
			CargoHojaVida result = gson.fromJson(out, CargoHojaVida.class);
			invalidToken(out);
			return result;
		} catch (Exception ex) {
			return new CargoHojaVida();
		}
	}
	

	/**
	 * Metodo que verifica si el cargo Hoja de vida ya existe en el maestro cargo_hoja_vida
	 * @param parametrica
	 * @return 
	 */
	public static List<CargoHojaVida> getCargoHVDuplicado(CargoHojaVida cargoHojaVida) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(cargoHojaVida);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_HV_DUPLICADO, json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CargoHojaVida>>() {
			}.getType();
			List<CargoHojaVida> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	 * Metodo que consume el servicio elimina fisicamente un registro de la tabla maestro cargo_hoja_vida.
	 * @param codCargoHojaVida
	 * @return Retorna un String con true, de lo contrario el servicio retornara el error ocurrido
	 */
	public static String eliminarCargoHojaVidaFisico(BigDecimal codCargoHV) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codCargoHojaVida\":" + codCargoHV + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.ELIMINAR_CARGO_HV_FISICO, json, token, TIME_OUT, auditoriaSeguridad);
			return out;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	/**
	 * @Author: Maria Alejandra
	 * @Date: 30 Abril 2020, 02:45:48
	 * @File: ComunicacionServiciosSis.java
	 * @param dependenciaHojaVida
	 * @return DependenciaHojaVida
	 * Metodo que inserta o modifica un registro de la tabla dependencia_hoja_vida
	 */
	public static DependenciaHojaVida setDependenciaHojaVida(DependenciaHojaVida dependenciaHojaVida) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(dependenciaHojaVida);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DEPENDENCIAS_HV, json, token, TIME_OUT, auditoriaSeguridad);
			DependenciaHojaVida result = gson.fromJson(out, DependenciaHojaVida.class);
			invalidToken(out);
			return result;
		} catch (Exception ex) {
			return new DependenciaHojaVida();
		}
	}
	/**
	 * Metodo que verifica si la dependencia de Hoja de vida ya existe en el maestro dependencia_hoja_vida
	 * @param parametrica
	 * @return 
	 */
	public static List<DependenciaHojaVida> getDependenciaHVDuplicado(DependenciaHojaVida dependenciaHojaVida) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(dependenciaHojaVida);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPENDENCIA_HV_DUPLICADO, json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<DependenciaHojaVida>>() {
			}.getType();
			List<DependenciaHojaVida> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo que consume el servicio elimina fisicamente un registro de la tabla maestro dependencia_hoja_vida.
	 * @param codCargoHojaVida
	 * @return Retorna un String con true, de lo contrario el servicio retornara el error ocurrido
	 */
	public static String eliminarDependenciaHojaVidaFisico(BigDecimal codDependenciaHV) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDependenciaHojaVida\":" + codDependenciaHV + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.ELIMINAR_DEPENDENCIA_HV_FISICO, json, token, TIME_OUT, auditoriaSeguridad);
			return out;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	/**
	 *  Metodo que retorna las dependencias de HV que cumplen con el filtro enviado
	 * @param DependenciaHojaVida
	 * @return List<DependenciaHojaVidaExt>
	 */
	public static List<DependenciaHojaVidaExt> getDependenciaHVFiltro(DependenciaHojaVidaExt dependenciaHojaVida) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(dependenciaHojaVida);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPENDENCIA_HV_FILTRO, json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<DependenciaHojaVidaExt>>() {
			}.getType();
			List<DependenciaHojaVidaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	
	/**
	 * @Author: Maria Alejandra
	 * @Date: 04 Mayo 2020, 10:50:00
	 * @File: ComunicacionServiciosSis.java
	 * @param dependenciaHojaVida
	 * @return DependenciaHojaVida
	 * Metodo que inserta o modifica un registro de la tabla dependencia_hoja_vida desde el componente de Estructura
	 * Este metodo llama inicialmete un servicio que consulta si la dependencia ya existe en el maestro. Si existe, no inserta.
	 * Si no existe, inserta en maestro dependencia_hoja_vida um nuevo registro
	 */
	public static DependenciaHojaVida setDependenciaHojaVidaDesdeEstructura(DependenciaHojaVida dependenciaHojaVida) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(dependenciaHojaVida);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DEPENDENCIAS_HV_DESDE_ESTRUCTURA, json, token, TIME_OUT, auditoriaSeguridad);
			DependenciaHojaVida result = gson.fromJson(out, DependenciaHojaVida.class);
			invalidToken(out);
			return result;
		} catch (Exception ex) {
			return new DependenciaHojaVida();
		}
	}
	
	/**
	 * Metodo para obtener una dependencia del maestro dependencia_hoja_vida por el ID
	 * @param DependenciaHojaVida con cod_depedendencia_hoja_vida
	 * @return DependenciaHojaVida
	 */
	public static DependenciaHojaVida getDependenciaHojaVidaById(DependenciaHojaVida dependenciaHojaVida) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(dependenciaHojaVida);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPENDENCIA_HV_POR_ID, json, token, TIME_OUT, auditoriaSeguridad);
			DependenciaHojaVida list = gson.fromJson(out, DependenciaHojaVida.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new DependenciaHojaVida();
		}
	}
	
	/**
	 * Metodo para obtener un cargo del maestro cargo_hoja_vida por el ID
	 * @param cargoHojaVida cod_cargo_hoja_vida
	 * @return cargoHojaVida
	 */
	public static CargoHojaVida getCargoHojaVidaById(CargoHojaVida cargoHojaVida) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(cargoHojaVida);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_HV_POR_ID, json, token, TIME_OUT, auditoriaSeguridad);
			CargoHojaVida list = gson.fromJson(out, CargoHojaVida.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new CargoHojaVida();
		}
	}

	/**
	 * Metodo para obtener liata de registros de la vista v_recurso_accion segun el filtro enviado
	 * @param recursoAccion 
	 * @return List<RecursoAccion>
	 */
	public static List<RecursoAccion> getVistaRecursoAccionFiltro(RecursoAccion recursoAccion) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(recursoAccion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VISTA_RECURSO_ACCION_BY_FILTRO, json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RecursoAccion>>() {
			}.getType();
			List<RecursoAccion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	 * Metodo para obtener liata de registros de la vista v_recurso_accion segun el filtro enviado
	 * @param recursoAccion 
	 * @return List<RecursoAccion>
	 */
	public static List<RecursoAccion> getVistaRecursoUsuarioAccionFiltro(RecursoAccionExt recursoAccion) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(recursoAccion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VISTA_RECURSO_USUARIO_ACCION_BY_FILTRO, json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<RecursoAccion>>() {
			}.getType();
			List<RecursoAccion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	

	
	
	/**
	 * Metodo para obtener liata de registros de la vista v_recurso_accion segun el filtro enviado
	 * @param recursoAccion 
	 * @return List<RecursoAccion>
	 */
	public static UsuarioRolEntidadExt eliminarUsuarioEntidad(UsuarioRolEntidadExt usuarioRolEntidadExt) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(usuarioRolEntidadExt);
		try {
			
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ELIMINAR_USUARIO_ROL_ENTIDAD, json, token, TIME_OUT, auditoriaSeguridad);
			UsuarioRolEntidadExt list = gson.fromJson(out, UsuarioRolEntidadExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new UsuarioRolEntidadExt();
		}
	}	
	
	public static Parametrica getParametricaByname(int codPadre, String nombreParametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			Parametrica parametrica = new Parametrica();
			parametrica.setCodPadreParametrica(BigDecimal.valueOf(codPadre));
			parametrica.setNombreParametro(nombreParametrica);
			String json = gson.toJson(parametrica);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRICA_BY_NOMBRE, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<Parametrica>() {
			}.getType();
			Parametrica objParametrica = gson.fromJson(out, type);
			invalidToken(out);
			return objParametrica;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Parametrica();
		}
	}

	public static List<MunicipioExt> getMunByDep(Municipio municipio){
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(municipio);

		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MUN_BY_DEP, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<MunicipioExt>>() {
			}.getType();
			List<MunicipioExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	 * SERWEB000062
	 * 
	 * @param id
	 * @return
	 */
	public static List<Parametrica> getParametricaActivaPorIdPadre(long codPadreParametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPadreParametrica\":" + codPadreParametrica + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRO_ACTIVO_POR_ID_PADRE, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Parametrica>>() {
			}.getType();
			List<Parametrica> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Parametrica>();
		}
	}
}