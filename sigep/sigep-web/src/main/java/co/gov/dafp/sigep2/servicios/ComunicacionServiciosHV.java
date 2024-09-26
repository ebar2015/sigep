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
import co.gov.dafp.sigep2.entities.DependenciaEntidad;
import co.gov.dafp.sigep2.entities.DocumentosAdicionalesHv;
import co.gov.dafp.sigep2.entities.EducacionFormal;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EvaluacionDesempeno;
import co.gov.dafp.sigep2.entities.ExperienciaDocente;
import co.gov.dafp.sigep2.entities.ExperienciaProfesional;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.IdiomaPersona;
import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.entities.LogroRecurso;
import co.gov.dafp.sigep2.entities.NacionalidadPerfil;
import co.gov.dafp.sigep2.entities.OtroConocimiento;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.ParticipacionInstitucion;
import co.gov.dafp.sigep2.entities.ParticipacionProyecto;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entities.ProgramaAcademico;
import co.gov.dafp.sigep2.entities.Publicacion;
import co.gov.dafp.sigep2.entities.Reconocimiento;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entities.UsuarioEntidad;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.CargoExt;
import co.gov.dafp.sigep2.mbean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.mbean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.mbean.ext.DocumentosAdicionalesHvExt;
import co.gov.dafp.sigep2.mbean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.mbean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.IdiomaExt;
import co.gov.dafp.sigep2.mbean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.mbean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PublicacionExt;
import co.gov.dafp.sigep2.mbean.ext.ReconocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.mbean.preguntaopinion.PreguntaOpinion;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.rest.ErrorMensajes;
import co.gov.dafp.sigep2.rest.SerivesRestURL;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * 
 * @author joseviscaya
 *
 */
public class ComunicacionServiciosHV implements Serializable {
	private static Gson gson;
	private static String token;
	private static long TIME_OUT = ConfigurationBundleConstants.getTimeOutConversation();
	private static final Logger logger = Logger.getInstance(ComunicacionServiciosHV.class);
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static AuditoriaSeguridad auditoriaSeguridad;


	public static AuditoriaSeguridad getAuditoriaSeguridad() {
		return auditoriaSeguridad;
	}

	public static void setAuditoriaSeguridad(AuditoriaSeguridad auditoriaSeguridad) {
		auditoriaSeguridad = auditoriaSeguridad;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5594210247464090093L;

	/**
	 * SERWEB000001
	 * 
	 * @param persona
	 * @return Este Servicio recibe un objeto Perona con los valores tipo de
	 *         identificacion y numero de identificacion y regresa un Obgejto con
	 *         todos campos que estan almacenados en BD
	 */
	public static PersonaDTO persontipIdnumId(PersonaDTO persona) {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		getToken();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_POR_TIPOID_NUMID,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			return gson.fromJson(out, PersonaDTO.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PersonaDTO();
		}
	}

	/**
	 * SERWEB000001-B
	 * 
	 * @param persona
	 * @return Este Servicio recibe un objeto Perona con los valores tipo de
	 *         identificacion y numero de identificacion y regresa un Obgejto con
	 *         todos campos que estan almacenados en BD
	 */
	public static PersonaExt persontipIdnumId(Persona persona) {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		getToken();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_POR_TIPOID_NUMID,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			return gson.fromJson(out, PersonaExt.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PersonaExt();
		}
	}

	/**
	 * SERWEB000001-C
	 * 
	 * @param persona
	 * @return Este Servicio recibe un objeto Perona que trabaja como filtro con los
	 *         siguientes campos: codGenero codEstadoCivil codMunicipioNacimiento
	 *         codDepartamentoNacimiento codPaisNacimiento codFactorRh
	 *         codTipoIdentificacion numeroIdentificacion primerNombre segundoNombre
	 *         primerApellido segundoApellid numeroLibretaMilitar fechaNacimiento
	 *         correoElectronico debe enviarse obligatoriamente los limites de la
	 *         consulta
	 */
	public static List<PersonaExt> getpersonaFiltro(Persona persona) {
		if(persona.getNumeroIdentificacion() != null)
			persona.setNumeroIdentificacion(persona.getNumeroIdentificacion().trim());
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		getToken();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PersonaExt>();
		}
	}

	/**
	 * SERWEB000002
	 * 
	 * @param persona
	 * @return Este Servicio recibe un objeto Perona con los valores tipo de
	 *         identificacion y numero de identificacion, codigo entidad y regresa
	 *         un Obgejto con todos campos que estan almacenados en BD
	 */
	public static PersonaDTO persontipIdnumIdEntId(PersonaDTO persona) {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		getToken();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_POR_TIPOID_NUMID_CODENTIDAD, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			return gson.fromJson(out, PersonaDTO.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PersonaDTO();
		}
	}

	/**
	 * SERWEB000003
	 * 
	 * @param codigoVentana
	 * @return Este Servicion retorna Recursos Avtivos de un prfil por codigo de
	 *         Ventana
	 */
	public static RecursoActivoPerfilUsuarioDTO recursoPorCod333(String codigoVentana) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codigoVentana\":\"" + codigoVentana + "\"}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RECURSO_POR_CODIGO_VENTANA,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			return gson.fromJson(out, RecursoActivoPerfilUsuarioDTO.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new RecursoActivoPerfilUsuarioDTO();
		}
	}

	/**
	 * SERWEB000005
	 * 
	 * @param usuario
	 * @return Este servicio permite hacer login al sistema enviando por medio de la
	 *         firma tipo de Identificaicon, nombre de usuario y contrasena.
	 */
	public static UsuarioDTO login(UsuarioDTO usuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(usuario);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.LOGIN,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			return gson.fromJson(out, UsuarioDTO.class);

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new UsuarioDTO();
		}
	}

	/**
	 * SERWEB000006
	 * 
	 * @param usuario
	 * @return Este servicio devuelve una lista de entidades por usuario
	 */
	public static List<EntidadDTO> entidadesPorUsuarioId(UsuarioDTO usuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(usuario);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDADES_POR_USUARIO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<EntidadDTO>>() {
			}.getType();
			List<EntidadDTO> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EntidadDTO>();
		}
	}

	/**
	 * SERWEB000007
	 * 
	 * @param usuario
	 * @return Este Metodo revuelve una lista de usuarios por entidad
	 */
	public static List<UsuarioDTO> usuariosPorEntidad(UsuarioDTO usuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(usuario);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIOS_ENTIDADES,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<UsuarioDTO>>() {
			}.getType();
			List<UsuarioDTO> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<UsuarioDTO>();
		}
	}

	/**
	 * SERWEB000008-B
	 * 
	 * @return
	 */
	public static List<Entidad> entidadesTotalbean333() {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_ENTIDADES, json, "", TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<Entidad>>() {
			}.getType();
			List<Entidad> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Entidad>();
		}
	}

	/**
	 * SERWEB000009
	 * 
	 * @param codEntidad
	 * @return
	 */
	public static List<EntidadDTO> desactivarEntidad(Long codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codEntidad\":\"" + codEntidad + "\"}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.DESACTIVAR_ENTIDADED,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<EntidadDTO>>() {
			}.getType();
			List<EntidadDTO> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EntidadDTO>();
		}
	}

	/**
	 * SERWEB000010
	 * 
	 * @param usuario
	 * @return
	 */
	public static UsuarioPasswordDTO usuarioPassword(UsuarioDTO usuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(usuario);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_POR_LOGIN,json,token, TIME_OUT, getAuditoriaSeguridad());
			UsuarioPasswordDTO us = gson.fromJson(out, UsuarioPasswordDTO.class);
			invalidToken(out);
			if (us.getAudCodUsuario().longValue() > 0) {
				return us;
			} else {
				return null;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SERWEB000013
	 * 
	 * @param
	 * @return List<InstitucionEducativa>
	 */
	public static List<InstitucionEducativa> listInstitucionEducativa333() {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_IEDUCATIVAS, "", token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<InstitucionEducativa>>() {
			}.getType();
			List<InstitucionEducativa> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<InstitucionEducativa>();
		}
	}

	/**
	 * SERWEB000015
	 * 
	 * @param eva
	 * @return
	 */
	public static List<EvaluacionDesempeno> evaldesempusent(EvaluacionDesempeno eva) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(eva);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EVALUACION_DES_US_EN,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<EvaluacionDesempeno>>() {
			}.getType();
			List<EvaluacionDesempeno> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EvaluacionDesempeno>();
		}
	}

	/**
	 * SERWEB000016
	 * 
	 * @param eva
	 * @return
	 */
	public static EvaluacionDesempeno evaldesempusentfe(EvaluacionDesempeno eva) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(eva);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EVALUACION_DES_US_EN_FE,json,token, TIME_OUT, getAuditoriaSeguridad());
			EvaluacionDesempeno list = gson.fromJson(out, EvaluacionDesempeno.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new EvaluacionDesempeno();
		}
	}

	/**
	 * SERWEB000017
	 * 
	 * @param usent
	 * @return
	 */
	public static UsuarioEntidad usenticoduscodent(UsuarioEntidad usent) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(usent);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_ENT_ROL_POR_USENT,json,token, TIME_OUT, getAuditoriaSeguridad());
			UsuarioEntidad list = gson.fromJson(out, UsuarioEntidad.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new UsuarioEntidad();
		}
	}

	/**
	 * SERWEB000018
	 * 
	 * @param id
	 * @return
	 */
	public static ExperienciaProfesionalExt expProfesionalPorId(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codExperienciaProfesional\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXP_PROFESIONAL_POR_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			ExperienciaProfesionalExt list = gson.fromJson(out, ExperienciaProfesionalExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ExperienciaProfesionalExt();
		}
	}

	/**
	 * SERWEB000019
	 * 
	 * @param name
	 * @return
	 */
	public static List<Parametrica> getParametrica(int name) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPadreParametrica\":\"" + name + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRO_POR_ID_PADRE,json,token, TIME_OUT, getAuditoriaSeguridad());
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
	 * SERWEB000019
	 * 
	 * @param name
	 * @return
	 */
	public static List<Parametrica> getParametricaOnlyNumero(int codPadreParametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPadreParametrica\":\"" + codPadreParametrica + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRO_POR_ID_PADRE_ONLY_NUMERO,json,token, TIME_OUT, getAuditoriaSeguridad());
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
	 * SERWEB000020
	 * 
	 * @param exp
	 * @return
	 */
	public static boolean setexpProfesional(ExperienciaProfesional exp) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(exp.getDireccionEntidad() != null)
			exp.setDireccionEntidad(exp.getDireccionEntidad());
		if(exp.getTelefono() != null)
			exp.setTelefono(exp.getTelefono().trim());	
		try {
			String json = gson.toJson(exp);
			json = json.replace("codTipoEntidad\":0", "codTipoEntidad\":null");
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_EXP_PROFESIONAL,json,token, TIME_OUT, getAuditoriaSeguridad());
			ExperienciaProfesional user = gson.fromJson(out, ExperienciaProfesional.class);
			invalidToken(out);
			if (user.isError()) {
				System.out.println(user.getMensaje());
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
	 * SERWEB000021
	 * 
	 * @param exp
	 * @return
	 */
	public static PersonaExt perentiporperent(PersonaExt per) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(per);
			String out = ConnectionHttp.sendPost(SerivesRestURL.PERSONA_ENTIDAD_POR_PER_ENT,json,token, TIME_OUT, getAuditoriaSeguridad());
			PersonaExt d = gson.fromJson(out, PersonaExt.class);
			invalidToken(out);
			return d;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PersonaExt();
		}
	}

	/**
	 * SERWEB000022
	 * 
	 * @param
	 * @return
	 */
	public static List<ProgramaAcademico> getProgramaacademico() {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PROGRAMA_ACADEMICO, "", token, TIME_OUT, getAuditoriaSeguridad());
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
	 * SERWEB000026
	 * 
	 * @param
	 * @return
	 */
	public static ExperienciaDocenteExt getExperianciaDoc001(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codExperienciaDocente\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIANCIA_DOC_001,json,token, TIME_OUT, getAuditoriaSeguridad());
			ExperienciaDocenteExt list = gson.fromJson(out, ExperienciaDocenteExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ExperienciaDocenteExt();
		}
	}

	/**
	 * SERWEB000027
	 * 
	 * @param
	 * @return
	 */
	public static List<EducacionFormalExt> getEducacionFormal001(long id, boolean flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + id + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EDUCACION_FORMAL_001,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<EducacionFormalExt>>() {
			}.getType();
			List<EducacionFormalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EducacionFormalExt>();
		}
	}

	/**
	 * SERWEB000028
	 * 
	 * @param
	 * @return
	 */
	public static List<ExperienciaProfesional> getexpeproporcodpersona(long id, int flgActivo, int flgActivoEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + id + ",\"flgActivoEntidad\":" + flgActivoEntidad + ",\"flgActivo\":"
					+ flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIANCIA_PRO_PORPERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesional>>() {
			}.getType();
			List<ExperienciaProfesional> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesional>();
		}
	}

	/**
	 * SERWEB000028B
	 * 
	 * @param
	 * @return
	 */
	public static List<ExperienciaProfesional> getexpeproporcodpersona(long id, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + id + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIANCIA_PRO_PORPERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesional>>() {
			}.getType();
			List<ExperienciaProfesional> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesional>();
		}
	}

	/**
	 * SERWEB000029
	 * 
	 * @param
	 * @return
	 */
	public static DatoContactoExt getDatoContacto(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DATO_CONTACOTO_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			DatoContactoExt list = gson.fromJson(out, DatoContactoExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new DatoContactoExt();
		}
	}

	/**
	 * SERWEB000030
	 * 
	 * @param
	 * @return
	 */
	public static boolean setDatoContacto(DatoContactoExt datoContacto) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(datoContacto.getDireccionResidencia() != null)
			datoContacto.setDireccionResidencia(datoContacto.getDireccionResidencia().trim());
		if(datoContacto.getTelefonoResidencia() != null)
			datoContacto.setTelefonoResidencia(datoContacto.getTelefonoResidencia().trim());
		if(datoContacto.getNumTelefonoOficina() != null)
			datoContacto.setNumTelefonoOficina(datoContacto.getNumTelefonoOficina().trim());
		if(datoContacto.getNumCelular() != null)
			datoContacto.setNumCelular(datoContacto.getNumCelular().trim());
		try {
			String json = gson.toJson(datoContacto);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DATO_CONTACOTO_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			ErrorMensajes error = gson.fromJson(out, ErrorMensajes.class);
			invalidToken(out);
			if (!error.isError()) {
				return true;
			} else {
				System.out.println(error.getMensaje());
				return false;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000031
	 * 
	 * @param
	 * @return
	 */
	public static Persona getPersonaPorId(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_POR_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			Persona list = gson.fromJson(out, Persona.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Persona();
		}
	}

	/**
	 * SERWEB000032
	 * 
	 * @param
	 * @return
	 */
	public static Persona getPersonaporfiltro(Persona persona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(persona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_POR_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			Persona list = gson.fromJson(out, Persona.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Persona();
		}
	}

	/**
	 * SERWEB000033
	 * 
	 * @param
	 * @return
	 */
	public static PersonaExt getPersonaporIdExt(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_POR_ID_EXT,json,token, TIME_OUT, getAuditoriaSeguridad());
			PersonaExt list = gson.fromJson(out, PersonaExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PersonaExt();
		}
	}

	/**
	 * SERWEB000034
	 * 
	 * @param
	 * @return
	 */
	public static Entidad getEntidadPorId333(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codEntidad\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_POR_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			Entidad list = gson.fromJson(out, Entidad.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Entidad();
		}
	}

	/**
	 * SERWEB000035
	 * 
	 * @param
	 * @return
	 */
	public static boolean updatePersona(Persona persona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(persona.getPrimerNombre() != null)
			persona.setPrimerNombre(persona.getPrimerNombre().trim());
		if(persona.getSegundoNombre() != null)
			persona.setSegundoNombre(persona.getSegundoNombre().trim());
		if(persona.getPrimerApellido() != null)
			persona.setPrimerApellido(persona.getPrimerApellido().trim());
		if(persona.getSegundoApellido() != null)
			persona.setSegundoApellido(persona.getSegundoApellido().trim());
		if(persona.getNumeroIdentificacion() != null)
			persona.setNumeroIdentificacion(persona.getNumeroIdentificacion().trim());
		if(persona.getCorreoElectronico() != null)
			persona.setCorreoElectronico(persona.getCorreoElectronico().trim());
		if(persona.getCorreoAlternativo() != null)
			persona.setCorreoAlternativo(persona.getCorreoAlternativo().trim());
		try {
			String json = gson.toJson(persona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.UPDATE_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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

	public static boolean updatePersonaTarjeta(Persona persona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(persona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.UPDATE_PERSONA_TARJETA,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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
	 * SERWEB000037
	 * 
	 * @param id
	 * @return
	 */
	public static DatoAdicionalExt getDatoContactoAdi(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DATO_CONTACTO_AD_POR_COD_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			DatoAdicionalExt list = gson.fromJson(out, DatoAdicionalExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new DatoAdicionalExt();
		}
	}

	/***
	 * SERWEB000038
	 * 
	 * @param datoContacto
	 * @return
	 */
	public static boolean setDatoContactoAdi(DatoAdicionalExt datoContacto) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(datoContacto.getUrlFacebook() != null)
			datoContacto.setUrlFacebook(datoContacto.getUrlFacebook().trim());
		if(datoContacto.getUrlInstagram() != null)
			datoContacto.setUrlInstagram(datoContacto.getUrlInstagram().trim());
		if(datoContacto.getUrlTwitter() != null)
			datoContacto.setUrlFacebook(datoContacto.getUrlFacebook().trim());
		if(datoContacto.getUrlLinkedin() != null)
			datoContacto.setUrlLinkedin(datoContacto.getUrlLinkedin().trim());
		try {
			String json = gson.toJson(datoContacto);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DATO_CONTACTO_AD_POR_COD_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			ErrorMensajes errr = gson.fromJson(json, ErrorMensajes.class);
			if (errr.isError()) {
				System.out.println(errr.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/***
	 * SERWEB000039
	 * 
	 * @param datoContacto
	 * @return
	 */
	public static boolean setExperiancaDoc(ExperienciaDocente experiencaDoc) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(experiencaDoc.getDireccion() != null)
			experiencaDoc.setDireccion(experiencaDoc.getDireccion().trim());
		if(experiencaDoc.getTelefono() != null)
			experiencaDoc.setTelefono(experiencaDoc.getTelefono().trim());
		if(experiencaDoc.getMateriaImpartida() != null)
			experiencaDoc.setMateriaImpartida(experiencaDoc.getMateriaImpartida().trim());
		try {
			String json = gson.toJson(experiencaDoc);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_EXPERIENCIA_DOCENTE,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			ErrorMensajes error = gson.fromJson(out, ErrorMensajes.class);
			if (!error.isError()) {
				return true;
			} else {
				System.out.println(error.getMensaje());
				return false;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000040
	 * 
	 * @param codTipoInstitucion
	 * @return
	 */
	public static List<InstitucionEducativa> getInstitucionTipo333(int codTipoInstitucion) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codTipoInstitucion\":" + codTipoInstitucion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_INTITUCION_POR_TIPO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<InstitucionEducativa>>() {
			}.getType();
			List<InstitucionEducativa> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<InstitucionEducativa>();
		}
	}

	/***
	 * SERWEB000042
	 * 
	 * @param list
	 * @return
	 */
	public static String setExperiancaDoc(List<NacionalidadPerfil> list) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(list);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NCIONALIDAD_PERFIL,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			return out;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * SERWEB000044
	 * 
	 * @return
	 */
	public static LogroRecurso getLogroRecursoPorPersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codPersona\":" + codPersona + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LOGRO_RECURSO_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			LogroRecurso list = gson.fromJson(out, LogroRecurso.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new LogroRecurso();
		}
	}

	/**
	 * SERWEB000045
	 * 
	 * @return
	 */
	public static boolean setLogroRecursoPorPersona(LogroRecurso logroRecurso) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(logroRecurso.getLogroSobresaliente() != null)
			logroRecurso.setLogroSobresaliente(logroRecurso.getLogroSobresaliente().trim());
		String json = gson.toJson(logroRecurso);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_LOGRO_RECURSO_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			ErrorMensajes error = gson.fromJson(out, ErrorMensajes.class);
			if (!error.isError()) {
				return true;
			} else {
				System.out.println(error.getMensaje());
				return false;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000046
	 * 
	 * @return
	 */
	public static EvaluacionDesempenoExt getEvaluacionDesempenoPorPersona(long codPersona, Long codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codPersona\":" + codPersona + ",\"codEntidad\":" + codEntidad + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EVALUACION_DESEM_POR_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			EvaluacionDesempenoExt list = gson.fromJson(out, EvaluacionDesempenoExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new EvaluacionDesempenoExt();
		}
	}

	/**
	 * SERWEB000047
	 * 
	 * @return
	 */
	public static boolean setEvaluacionDesempeno(EvaluacionDesempeno evaluacionDesempeno) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(evaluacionDesempeno.getEscalaEvaluacion() != null)
			evaluacionDesempeno.setEscalaEvaluacion(evaluacionDesempeno.getEscalaEvaluacion().trim());
		String json = gson.toJson(evaluacionDesempeno);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_EVALUACION_DESEMPENO,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			ErrorMensajes error = gson.fromJson(out, ErrorMensajes.class);
			if (!error.isError()) {
				return true;
			} else {
				System.out.println(error.getMensaje());
				return false;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000048
	 * 
	 * @return
	 */
	public static ReconocimientoExt getreconocimientoporpersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codPersona\":" + codPersona + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_RECONOCIMIENTO_POR_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			ReconocimientoExt list = gson.fromJson(out, ReconocimientoExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ReconocimientoExt();
		}
	}

	/**
	 * SERWEB000049
	 * 
	 * @return
	 */
	public static boolean setreconocimientoporpersona(Reconocimiento reconocimiento) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(reconocimiento);
		if(reconocimiento.getNombreEntidad() != null)
			reconocimiento.setNombreEntidad(reconocimiento.getNombreEntidad().trim());
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_RECONOCIMIENTO_POR_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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
	 * SERWEB000050
	 * 
	 * @return
	 */
	public static PublicacionExt getpublicacionoporpersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codPersona\":" + codPersona + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PUBLICACION_POR_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			PublicacionExt list = gson.fromJson(out, PublicacionExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PublicacionExt();
		}
	}

	/**
	 * SERWEB000051
	 * 
	 * @return
	 */
	public static boolean setpublicacionoporpersona(Publicacion publicacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(publicacion.getNombreArticulo() != null)
			publicacion.setNombreArticulo(publicacion.getNombreArticulo().trim());
		if(publicacion.getNombreLibro() != null)
			publicacion.setNombreLibro(publicacion.getNombreLibro().trim());
		if(publicacion.getNombrePublicacion() != null)
			publicacion.setNombrePublicacion(publicacion.getNombrePublicacion().trim());
		String json = gson.toJson(publicacion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PUBLICACION_POR_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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
	 * SERWEB000052
	 * 
	 * @return
	 */
	public static ParticipacionInstitucion getparticipacionInstitucionporpersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codPersona\":" + codPersona + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARTICIPACION_INT_POR_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			ParticipacionInstitucion list = gson.fromJson(out, ParticipacionInstitucion.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ParticipacionInstitucion();
		}
	}

	/**
	 * SERWEB000053
	 * 
	 * @return
	 */
	public static boolean setparticipacionInstitucionporpersona(ParticipacionInstitucion participacionInstitucion) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(participacionInstitucion.getNombreEntidadOrganizacion() != null)
			participacionInstitucion.setNombreEntidadOrganizacion(participacionInstitucion.getNombreEntidadOrganizacion().trim());
		if(participacionInstitucion.getNombreInstitucion() != null)
			participacionInstitucion.setNombreInstitucion(participacionInstitucion.getNombreInstitucion().trim());
		if(participacionInstitucion.getNombreRazonSocialInstitucion() != null)
			participacionInstitucion.setNombreRazonSocialInstitucion(participacionInstitucion.getNombreRazonSocialInstitucion().trim());
		String json = gson.toJson(participacionInstitucion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PARTICIPACION_INT_POR_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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
	 * SERWEB000054
	 * 
	 * @return
	 */
	public static ParticipacionProyectoExt getparticipacionProyeporpersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codPersona\":" + codPersona + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARTICIPACION_PROYEC_POR_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			ParticipacionProyectoExt list = gson.fromJson(out, ParticipacionProyectoExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ParticipacionProyectoExt();
		}
	}

	/**
	 * SERWEB000055
	 * 
	 * @return
	 */
	public static boolean setparticipacionProyeporpersona(ParticipacionProyecto participacionProyecto) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(participacionProyecto);
		if(participacionProyecto.getNombreProyecto().trim() != null)
			participacionProyecto.setNombreProyecto(participacionProyecto.getNombreProyecto().trim());
		if(participacionProyecto.getRolLaborado() != null)
			participacionProyecto.setRolLaborado(participacionProyecto.getRolLaborado().trim());
		if(participacionProyecto.getNombreEntidad() != null)
			participacionProyecto.setNombreEntidad(participacionProyecto.getNombreEntidad().trim());
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PARTICIPACION_PROYEC_POR_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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
	 * SERWEB000057
	 * 
	 * @return
	 */
	public static List<DependenciaEntidad> getDependenciaentidad333() {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPENDENCIA_ENTIDAD, "", token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<DependenciaEntidad>>() {
			}.getType();
			List<DependenciaEntidad> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<DependenciaEntidad>();
		}
	}

	/**
	 * SERWEB000058
	 * 
	 * @return
	 */
	public static boolean eliminarHojaVida(long codPersona, long audCodUsuario, long codEntidad,
			String justificacionEliminacion, long codHojaVida) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + ",\"audCodUsuario\":" + audCodUsuario + ",\"codEntidad\":"
					+ codEntidad + ",\"justificacionEliminacion\":\" " + justificacionEliminacion
					+ "\",\"codHojaVida\":" + codHojaVida + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.ELIMINAR_HOJA_VIDA,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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
	 * SERWEB000060
	 * 
	 * @param id
	 * @return
	 */
	public static InstitucionEducativa getInstitucionEducaporId333(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codInstitucionEducativa\":\"" + id + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IEDUCATIVAS_POR_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			InstitucionEducativa list = gson.fromJson(out, InstitucionEducativa.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SERWEB000061
	 * 
	 * @param id
	 * @return
	 */
	public static List<ExperienciaDocenteExt> geExperienciaDocenteBycodPer(long codPersona, boolean flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIENCIA_DOC_POR_COD_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaDocenteExt>>() {
			}.getType();
			List<ExperienciaDocenteExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaDocenteExt>();
		}
	}

	/**
	 * SERWEB000063
	 * 
	 * @param id
	 * @return
	 */
	public static IdiomaExt getidiomacomplementario(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IDIOMA_COMPLEMENTARIO_POR_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			IdiomaExt list = gson.fromJson(out, IdiomaExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new IdiomaExt();
		}
	}

	/**
	 * SERWEB000064
	 * 
	 * @param id
	 * @return
	 */
	public static int getDifDias(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DIFERENCIA_DIAS,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
			return Integer.parseInt(out);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * SERWEB000065
	 * 
	 * @param id
	 * @return
	 */
	public static List<ExperienciaProfesional> getExperenciaProPortipoEntidad(long codTipoEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codTipoEntidad\":" + codTipoEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIENCIA_POR_TIPO_ENTIDAD, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesional>>() {
			}.getType();
			List<ExperienciaProfesional> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesional>();
		}
	}

	/**
	 * SERWEB000066
	 * 
	 * @param id
	 * @return
	 */
	public static List<Entidad> getEntidadporCodTipoEntidad(long codTipoEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codTipoEntidad\":" + codTipoEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDADES_POR_TIPO_ENTIDAD,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<Entidad>>() {
			}.getType();
			List<Entidad> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Entidad>();
		}
	}

	/**
	 * SERWEB000067
	 * 
	 * @param id
	 * @return
	 */
	public static boolean setIdiomaPersona(IdiomaPersona idiomaPersona) {
		getToken();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("dd/MM/yyyy");
		gson = gsonBuilder.create();
		try {
			String json = gson.toJson(idiomaPersona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_IDIOMA_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			ErrorMensajes error = gson.fromJson(out, ErrorMensajes.class);
			if (!error.isError()) {
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
	 * SERWEB000072
	 * 
	 * @param
	 * @return
	 */
	public static List<IdiomaPersonaExt> getidiomapersonaporpersona(long codPersona, boolean flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IDIOMAPERSONA_POR_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<IdiomaPersonaExt>>() {
			}.getType();
			List<IdiomaPersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<IdiomaPersonaExt>();
		}
	}

	/**
	 * SERWEB000073
	 * 
	 * @param
	 * @return
	 */
	public static List<CargoExt> getcargoporcodtipoentidad333(long codTipoEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codTipoEntidad\":" + codTipoEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_POR_COD_TIPO_ENTIDAD,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<CargoExt>>() {
			}.getType();
			List<CargoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<CargoExt>();
		}
	}

	/**
	 * SERWEB000074
	 * 
	 * @param rol
	 * @return
	 */
	public static boolean setotroconocimiento(OtroConocimiento otroConocimiento) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(otroConocimiento.getNombreCurso() != null)
			otroConocimiento.setNombreCurso(otroConocimiento.getNombreCurso().trim());
		if(otroConocimiento.getInstitucionFormacion() != null)
			otroConocimiento.setInstitucionFormacion(otroConocimiento.getInstitucionFormacion().trim());
		try {
			String json = gson.toJson(otroConocimiento);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_OTRO_CONOCIMIENTO,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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
	 * SERWEB000075
	 * 
	 * @param rol
	 * @return
	 */
	public static boolean seteducacionformal(EducacionFormal educacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		if(educacion.getNumTarjetaProfesional() != null)
			educacion.setNumTarjetaProfesional(educacion.getNumTarjetaProfesional().trim());
		try {
			String json = gson.toJson(educacion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_EDUCACION_FORMAL,json,token, TIME_OUT, getAuditoriaSeguridad());
			ErrorMensajes error = gson.fromJson(out, ErrorMensajes.class);
			if (!error.isError()) {
				return true;
			} else {
				System.out.println(error.getMensaje());
				return false;
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * SERWEB000076
	 * 
	 * @param rol
	 * @return
	 */
	public static List<OtroConocimientoExt> getotroconocimientoporpersona(long codPersona, boolean flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_OTRO_CONOCIMIENTO_POR_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<OtroConocimientoExt>>() {
			}.getType();
			List<OtroConocimientoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<OtroConocimientoExt>();
		}
	}

	/**
	 * SERWEB000082
	 * 
	 * @param rol
	 * @return
	 */
	public static List<CargoExt> getcargostodos333(int limitInit, int limitEnd) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"limitInit\":" + limitInit + ",\"limitEnd\":" + limitEnd + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOD_TODOS,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<CargoExt>>() {
			}.getType();
			List<CargoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<CargoExt>();
		}
	}

	/**
	 * SERWEB000083
	 * 
	 * @param rol
	 * @return
	 */
	public static Integer getdifdiasporpersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DIF_DIAS_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			ExperienciaProfesionalExt list = gson.fromJson(out, ExperienciaProfesionalExt.class);
			invalidToken(out);
			return list.getDiferenciaDias();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * SERWEB000084
	 * 
	 * @param rol
	 * @return Metodo que rtorna una lista de Experiencias Profecionale por medio
	 *         del Objeto ExperienciaProfesional el cual debe llevar los atributos
	 *         FlgActivo y limites como obligatorios.
	 */
	public static List<ExperienciaProfesionalExt> getcargosexperienciapro(
			ExperienciaProfesional experienciaProfesional) {
		getToken();
		if (experienciaProfesional.getLimitEnd() == 0) {
			return null;
		}
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(experienciaProfesional);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_EXPERIENCIA,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}

	/**
	 * SERWEB000086
	 * 
	 * @param ProgramaAcademico
	 * @return
	 */
	public static ProgramaAcademico getprogramaacademicoporid333(long codTituloAcademico) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codTituloAcademico\":" + codTituloAcademico + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PROGRAMA_ACADEMICO_POR_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			ProgramaAcademico list = gson.fromJson(out, ProgramaAcademico.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ProgramaAcademico();
		}
	}

	/**
	 * SERWEB000087
	 * 
	 * @param ProgramaAcademico
	 * @return
	 */
	public static List<ExperienciaProfesionalExt> getexperienciaprofesionalporpersona(
			ExperienciaProfesional experienciaProfesional) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(experienciaProfesional);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIENCIA_PROFESIONAL_POR_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}

	/**
	 * SERWEB000088
	 * 
	 * @param rol
	 * @return
	 */
	public static List<ExperienciaProfesional> getcargosexperienciapublic() {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_EXPERIENCIA_PUBLICO,json,token, TIME_OUT, getAuditoriaSeguridad());

			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesional>>() {
			}.getType();
			List<ExperienciaProfesional> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesional>();
		}
	}

	/**
	 * SERWEB000090
	 * @param rol
	 * @return
	 */
	public static List<ExperienciaProfesionalExt> getcargoentidadpersona(long codPersona, boolean flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_ENTIDAD_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}

	/**
	 * SERWEB000089
	 * 
	 * @param rol
	 * @return
	 */
	public static PersonaExt getpersonacontacto(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_DATO_CONTACTO,json,token, TIME_OUT, getAuditoriaSeguridad());
			PersonaExt list = gson.fromJson(out, PersonaExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PersonaExt();
		}
	}

	/**
	 * SERWEB000091
	 * 
	 * @param rol
	 * @return
	 */
	public static List<HistoricoModificacionHojaVida> getModificacionHojaVidaPersona(BigDecimal codHojaVida) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codHojaVida\":" + codHojaVida + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MODIFICACION_HOJA_VIDA_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<HistoricoModificacionHojaVida>>() {
			}.getType();
			List<HistoricoModificacionHojaVida> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<HistoricoModificacionHojaVida>();
		}
	}

	/**
	 * SERWEB000092
	 * 
	 * @param rol
	 * @return
	 */
	public static boolean setmodificacionhohadevida(HistoricoModificacionHojaVida modificacionHojaVida) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(modificacionHojaVida);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_MODIFICACION_HOJA_VIDA,json,token, TIME_OUT, getAuditoriaSeguridad());
			invalidToken(out);
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
	 * SERWEB000093
	 * 
	 * @param rol
	 * @return
	 */
	public static UsuarioExt getusuarioext(long codPersona, long codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codUsuario\":" + codPersona + ",\"codEntidad\":" + codEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_EXT,json,token, TIME_OUT, getAuditoriaSeguridad());
			UsuarioExt d = gson.fromJson(out, UsuarioExt.class);
			invalidToken(out);
			return d;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new UsuarioExt();
		}
	}

	/**
	 * SERWEB000070B
	 * 
	 * @param
	 * @return
	 */
	public static String getpaisBandera(long codPais) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = "{\"codPais\":" + codPais + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PAIS_POR_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			Pais list = gson.fromJson(out, Pais.class);
			invalidToken(out);
			return list.getBanderaUrl().toLowerCase();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * SERWEB000095
	 * 
	 * @param
	 * @return
	 */
	public static IdiomaPersonaExt getidiomapersonaporpersonaporid(long codIdiomaPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codIdiomaPersona\":" + codIdiomaPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IDIOMA_PERSONA_POR_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			IdiomaPersonaExt list = gson.fromJson(out, IdiomaPersonaExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new IdiomaPersonaExt();
		}
	}

	/**
	 * SERWEB000096
	 * 
	 * @param rol
	 * @return
	 */
	public static OtroConocimientoExt getotroconocimientoporid(long codOtroConocimiento) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codOtroConocimiento\":" + codOtroConocimiento + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_OTRO_CONOCIMIENTO_POR_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			OtroConocimientoExt list = gson.fromJson(out, OtroConocimientoExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new OtroConocimientoExt();
		}
	}

	/**
	 * SERWEB000125
	 * 
	 * @param codPreguntaOpinion
	 * @return Metodo que implementa el web Service que retorna un Objeto Pregunta
	 *         Opinion consultado por Id de la tabla.
	 */
	public static PreguntaOpinion getpreguntaopinionid(long codPreguntaOpinion) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPreguntaOpinion\":" + codPreguntaOpinion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PREGUNTA_OPINION_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			PreguntaOpinion list = gson.fromJson(out, PreguntaOpinion.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PreguntaOpinion();
		}
	}

	/**
	 * SERWEB000127
	 * 
	 * @param rol
	 * @return Metodo que recibe como parametros un objeto experienciaProfesional
	 *         con los atributos CodPersona, FlgActivo, los limites para controlar
	 *         el numero de registros que retorna la consulta
	 */
	public static List<ExperienciaProfesionalExt> getCargoExp(ExperienciaProfesional experienciaProfesional) {
		getToken();
		if (experienciaProfesional.getLimitEnd() == 0) {
			return null;
		}
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(experienciaProfesional);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_PERSONA_EXP,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}

	/**
	 * SERWEB000155
	 * 
	 * @param codPersona
	 * @return
	 */
	public static List<EvaluacionDesempeno> getEvaluacionDesemenoPerosna(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EVALUACION_DESEMPENO_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<EvaluacionDesempeno>>() {
			}.getType();
			List<EvaluacionDesempeno> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EvaluacionDesempeno>();
		}
	}

	/**
	 * SERWEB000156
	 * 
	 * @param codPersona
	 * @return Metodo que implementa el web Service que retorna un Objeto Evaluacion
	 *         Desempenio consultado por Id de la persona.
	 */
	public static EvaluacionDesempenoExt getevaluaciondesempenobypersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EVALUACION_DESEMPENO_BY_PERSONA, json, token,
					TIME_OUT, getAuditoriaSeguridad());
			EvaluacionDesempenoExt list = gson.fromJson(out, EvaluacionDesempenoExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new EvaluacionDesempenoExt();
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
	public static List<PersonaExt> getPersonaporHVFiltro(PersonaExt persona) {
		getToken();
		if (persona.getLimitInit() == null) {
			List<PersonaExt> er = new ArrayList<PersonaExt>();
			PersonaExt err = new PersonaExt();
			err.setError(true);
			err.setMensaje("Hace Falta el atriburo LimitInit");
			er.add(err);
			return er;
		} else if (persona.getLimitEnd() == null || persona.getLimitEnd() == 0) {
			List<PersonaExt> er = new ArrayList<PersonaExt>();
			PersonaExt err = new PersonaExt();
			err.setError(true);
			err.setMensaje("Hace Falta el atriburo LimitEnd o valor invalido");
			er.add(err);
			return er;
		}
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(persona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_HV_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
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

	/**
	 * SERWEB000159
	 * 
	 * @param hojaVida
	 * @return Metodo que consume el servicio de trae una lista de Objetos HojaVida,
	 *         mediante un filtro codPersona; codEntidad; codModalidadContrato;
	 *         codUtlUan; codCargo; detallesContrato; flgAprobado;
	 *         codUsuarioAprueba; fechaAprobacion; flgActivo, el servicio recibe
	 *         cualquiera de estos atributos se pueden enviar todos o ninguno, solo
	 *         hay dos atributos obligatorios que son los limites de registros que
	 *         se consultaran.
	 */
	public static List<HojaVidaExt> getHojaVidafiltro(HojaVidaExt hojaVida) {
		getToken();
		if (hojaVida.getLimitEnd() == 0) {
			List<HojaVidaExt> er = new ArrayList<HojaVidaExt>();
			HojaVidaExt err = new HojaVidaExt();
			err.setError(true);
			err.setMensaje("Hace Falta el atriburo LimitInit");
			er.add(err);
			return er;
		}
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(hojaVida);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_HOJAVIDA_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<HojaVidaExt>>() {
			}.getType();
			List<HojaVidaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<HojaVidaExt>();
		}
	}

	/**
	 * SERWEB000167
	 * 
	 * @param HojaVida
	 * @return Servicio que inserta o actualiza un registro en al base de datos
	 *         validando si envia el Id de la tabla COD_HOJA_VIDA para actualizacion
	 *         y null o 0 (COD_HOJA_VIDA) y valido el COD_PERSONA
	 */
	public static boolean setHojaVida(HojaVidaExt HojaVida) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(HojaVida);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_HOJA_VIDA,json,token, TIME_OUT, getAuditoriaSeguridad());
			ExperienciaProfesional user = gson.fromJson(out, ExperienciaProfesional.class);
			invalidToken(out);
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
	 * SERWEB000168
	 * 
	 * @param codPersona
	 * @return Metodo que implementa el web Service que retorna un Objeto Usuario
	 *         Opinion consultado por Id de la tabla.
	 */
	public static Usuario getusuarioid(Usuario usuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(usuario);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			Usuario list = gson.fromJson(out, Usuario.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Usuario();
		}
	}

	/**
	 * SERWEB000171
	 * 
	 * @param ExperienciaProfesional
	 * @return Servicio que actualiza estadode de la hoja de vida
	 */
	public static boolean updateMasivoHojaVida(ExperienciaProfesional experienciaProfesional) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(experienciaProfesional);
			String out = ConnectionHttp.sendPost(SerivesRestURL.UPDATE_MASIVO_HOJA_VIDA,json,token, TIME_OUT, getAuditoriaSeguridad());
			ExperienciaProfesional user = gson.fromJson(out, ExperienciaProfesional.class);
			invalidToken(out);
			if (user.isError()) {
				System.out.println(user.getMensaje());
				System.out.println(user.getMensajeTecnico());
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
	 * SERWEB000179
	 * 
	 * @param persona
	 * @return Este Servicio recibe un objeto Perona con el numero de identificacion
	 */
	public static List<PersonaExt> getPersonaCargosFecha(Persona persona) {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		getToken();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_CARGO_FECHA,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PersonaExt>();
		}
	}

	/**
	 * SERWEB000180
	 * 
	 * @param persona
	 * @return Este Servicio recibe un objeto Perona con el numero de identificacion
	 */
	public static List<Persona> getPersonaCargosPosesion(Persona persona) {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		getToken();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_CARGO_POSESION,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<Persona>>() {
			}.getType();
			List<Persona> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Persona>();
		}
	}

	/**
	 * SERWEB000181
	 * 
	 * @param personaExt
	 * @return Este Servicio recibe una lista de objetos PeronaExt entradas:
	 *         cod_entidad cod_rol flg_activo flg_estado
	 */
	public static List<PersonaExt> getPersonaRolEntidad(Persona persona) {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		getToken();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_ROL_ENTIDAD,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PersonaExt>();
		}
	}

	/**
	 * SERWEB000184
	 * 
	 * @param PersonaExt
	 * @return Este Servicio recibe un objeto PersonaExt que trabaja con los
	 *         siguientes campos: codEntidad codRol
	 */
	public static List<PersonaExt> getPersonaControlInterno(PersonaExt persona) {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		getToken();
		String json = gson.toJson(persona);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_CONTROL_INTERNO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PersonaExt>();
		}
	}

	/**
	 * SERWEB000194
	 * 
	 * @param
	 * @return
	 */
	public static EducacionFormalExt getEducacionFormalId(long codEducacionFormal, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codEducacionFormal\":" + codEducacionFormal + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EDUCACION_FORMAL_ID,json,token, TIME_OUT, getAuditoriaSeguridad());
			EducacionFormalExt list = gson.fromJson(out, EducacionFormalExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new EducacionFormalExt();
		}
	}

	public static List<ExperienciaProfesionalExt> obtenerEmpleoActual(ExperienciaProfesionalExt experienciaProfesional) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(experienciaProfesional);
		List<ExperienciaProfesionalExt> result;
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EMPLEO_ACTUAL,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {}.getType();
			result = gson.fromJson(out, type);
			invalidToken(out);
			return result;
		} catch (Exception e) {
			logger.info("El servicio no encontro datos - getEmpleoActual(long codPersona, boolean flgActivo)", e);
			return new ArrayList<>();
		}
	}
	
	public static List<ExperienciaProfesionalExt> obtenerEmpleosAnteriores(ExperienciaProfesionalExt experienciaProfesional) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String json = gson.toJson(experienciaProfesional);
		List<ExperienciaProfesionalExt> result;
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EMPLEOS_ANTERIORES,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {}.getType();
			result = gson.fromJson(out, type);
			invalidToken(out);
			return result;
		} catch (Exception e) {
			logger.info("El servicio no encontro datos - getEmpleoActual(long codPersona, boolean flgActivo)", e);
			System.out.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Metodo que devuelve el total de datos de HV que tienen cambios para su aprobación.
	 * @param hojaVidaExt
	 * @return
	 */
	public static HojaVidaExt getHojaVidaAprobacion(HojaVidaExt hojaVidaExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(hojaVidaExt);
		    String out = ConnectionHttp.sendPost(SerivesRestURL.GET_HOJA_VIDA_VERIFICADO, json,token, TIME_OUT, getAuditoriaSeguridad());
		    java.lang.reflect.Type type = new TypeToken<HojaVidaExt>() {
		    }.getType();
		    HojaVidaExt list = gson.fromJson(out, type);
	        invalidToken(out);
	        return list;
		} catch (JsonSyntaxException e) {
		    logger.info("El servicio no encontro datos - getHojaVidaAprobacion(HojaVidaExt hojaVidaExt)", e);
		    return new HojaVidaExt();
		}
    }
	
	/* GERENCIA PUBLIC HV**/
	public static List<LogroRecurso> getLogrosRecursosFiltro(LogroRecurso logro) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(logro);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LOGROS_RECURSOS_HV_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<LogroRecurso>>() {
			}.getType();
			List<LogroRecurso> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	/**
	 * 
	 * @param logro
	 * @return
	 */
	public static List<LogroRecurso> getLogrosRecursosFiltroGerencia(LogroRecurso logro) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(logro);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LOGROS_RECURSOS_HV_FILTRO_G,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<LogroRecurso>>() {
			}.getType();
			List<LogroRecurso> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	
	
	public static List<PublicacionExt> getPublicacionesFiltro(PublicacionExt publicacion) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(publicacion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PUBLICACIONES_HV_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<PublicacionExt>>() {
			}.getType();
			List<PublicacionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	public static List<EvaluacionDesempenoExt> getEvDesempenno(EvaluacionDesempenoExt evaluacion) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(evaluacion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EVDESEMPENNO_HV_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<EvaluacionDesempenoExt>>() {
			}.getType();
			List<EvaluacionDesempenoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	
	public static List<ReconocimientoExt> getPremiosReconocimientosHV(ReconocimientoExt premio) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(premio);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PREMIOS_HV_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ReconocimientoExt>>() {
			}.getType();
			List<ReconocimientoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	public static List<ParticipacionProyectoExt> getParticipacionProyectosHV(ParticipacionProyectoExt participacion) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(participacion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARTICIPACION_PROYECTOS_HV_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ParticipacionProyectoExt>>() {
			}.getType();
			List<ParticipacionProyectoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	
	public static List<ParticipacionInstitucion> getParticipacionInstitucionHV(ParticipacionInstitucion participacion) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(participacion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARTICIPACION_INSTITUCIONES_HV_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ParticipacionInstitucion>>() {
			}.getType();
			List<ParticipacionInstitucion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}		
	
	 /**
		 * Metodo para retornar la hoja de vida de las personas que estan asociadas a una UTL
		 * @param VinculacionExt
		 * @return List<VinculacionExt>
		 */
	public static List<HojaVidaExt> getHVPersonaAsociadaUTL(HojaVidaExt hojaVida) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(hojaVida);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_HV_PERSONAS_ASOCIADA_UTL,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<HojaVidaExt>>() {
			}.getType();
			List<HojaVidaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	
	
	 /**
		 * Metodo para retornar las experienciasProfesionals
		 * @param ExperienciaProfesional
		 * @return List<ExperienciaProfesional>
		 */
	public static List<ExperienciaProfesional> getExperienciaProfesionalFilter(ExperienciaProfesional experiencia) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(experiencia);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIENCIA_PROFESIONAL_FILTER,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesional>>() {
			}.getType();
			List<ExperienciaProfesional> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	
	
	
	/**
	 * @return Metodo que recibe como parametros un objeto experienciaProfesional ext
	 */
	public static List<ExperienciaProfesionalExt> getEntidadesPriv(ExperienciaProfesionalExt experienciaProfesionalExt) {
		getToken();
		if (experienciaProfesionalExt.getLimitEnd() == 0) {
			return null;
		}
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(experienciaProfesionalExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PRIVADA_EXPERIENCIA_PROFESIONAL,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Mar 29, 2019, 10:33:53 AM
	 * @File:   ComunicacionServiciosHV.java
	 * @param buscador
	 * @return
	 */
	public static List<DocumentosAdicionalesHvExt> getDocumentosAdicionalesHVFiltro(DocumentosAdicionalesHv buscador) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(buscador);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DOCUMENTOS_ADICIONALES_HV_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<DocumentosAdicionalesHvExt>>() {
			}.getType();
			List<DocumentosAdicionalesHvExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<DocumentosAdicionalesHvExt>();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Mar 29, 2019, 10:33:45 AM
	 * @File:   ComunicacionServiciosHV.java
	 * @param buscador
	 * @return
	 */
	public static DocumentosAdicionalesHvExt getDocumentosAdicionalesHVById(DocumentosAdicionalesHv buscador) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(buscador);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DOCUMENTOS_ADICIONALES_HV_BYID,json,token, TIME_OUT, getAuditoriaSeguridad());
			DocumentosAdicionalesHvExt list = gson.fromJson(out, DocumentosAdicionalesHvExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new DocumentosAdicionalesHvExt();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Mar 29, 2019, 10:34:03 AM
	 * @File:   ComunicacionServiciosHV.java
	 * @param buscador
	 * @return
	 */
	public static DocumentosAdicionalesHvExt delDocumentosAdicionalesHVById(DocumentosAdicionalesHv buscador) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = gson.toJson(buscador);
			String out = ConnectionHttp.sendPost(SerivesRestURL.DEL_DOCUMENTOS_ADICIONALES,json,token, TIME_OUT, getAuditoriaSeguridad());
			DocumentosAdicionalesHvExt list = gson.fromJson(out, DocumentosAdicionalesHvExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new DocumentosAdicionalesHvExt();
		}
	}
	
	public static DocumentosAdicionalesHv setDocumentosAdicionalesHv(DocumentosAdicionalesHv objeto) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(objeto);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DOCUMENTO_ADICIONAL_HV,json,token, TIME_OUT, getAuditoriaSeguridad());
			DocumentosAdicionalesHv per = gson.fromJson(out, DocumentosAdicionalesHv.class);
			return per;
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new DocumentosAdicionalesHv();
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
	
	public static String getTokenService() {
		try {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			boolean stado = (boolean) contexto.getSessionMap().get("sesionValida");
			if (stado) {
				try {
					return (String) contexto.getSessionMap().get("token");
				} catch (NullPointerException e) {
					return "";
				}
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
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
	 * @return Metodo que recibe como parametros un objeto experienciaProfesional ext
	 */
	public static List<ExperienciaProfesionalExt> getDependenciasLikeExperienciaProfesional(ExperienciaProfesionalExt experienciaProfesionalExt) {
		getToken();
		if (experienciaProfesionalExt.getLimitEnd() == 0) {
			return null;
		}
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(experienciaProfesionalExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPENDENCIA_EXPERIENCIA_PROFESIONAL,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}
	
	/**
	 * @return Metodo que recibe como parametros un objeto experienciaProfesional ext
	 */
	public static List<ExperienciaProfesionalExt> getCargosFilterLikeEntidadPublica(ExperienciaProfesionalExt experienciaProfesionalExt) {
		getToken();
		if (experienciaProfesionalExt.getLimitEnd() == 0) {
			return null;
		}
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(experienciaProfesionalExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_PUBLICOS_EXPERIENCIA_PROFESIONAL,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}
	
	/**
	 * @return Metodo que recibe como parametros un objeto experienciaProfesional ext
	 */
	public static List<ExperienciaProfesionalExt> getCargosFilterLikeEntidadPrivada(ExperienciaProfesionalExt experienciaProfesionalExt) {
		getToken();
		if (experienciaProfesionalExt.getLimitEnd() == 0) {
			return null;
		}
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(experienciaProfesionalExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_PRIVADOS_EXPERIENCIA_PROFESIONAL,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}
	
	/**
	 * @return Metodo que devuelve una lista de HistoricoModificacionHojaVida dependiendo del filtro enviado
	 */
	public static List<HistoricoModificacionHojaVida> getHistoricoModificacionHojaVidaByFiltro(HistoricoModificacionHojaVida historicoModificacionHojaVida) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(historicoModificacionHojaVida);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GEY_HISTORICO_MODIFICACION_HOJA_VIDA_BY_FILTRO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<HistoricoModificacionHojaVida>>() {
			}.getType();
			List<HistoricoModificacionHojaVida> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<HistoricoModificacionHojaVida>();
		}
	}
		
	/**
	 * Metodo que retorna una lista con las experiencias profesionales y docentes de la persona enviada. 
	 * Retorna la siguiente información: cod_jornada_laboral, cod_tipo_entidad, fecha_ingreso, fecha_retiro y flg_contratista.
	 * @param experienciaProfesionalExt con los parametros: flgActivo y codPersona
	 * @return List<ExperienciaProfesionalExt>
	 */
	public static List<ExperienciaProfesionalExt> getExperienciaProfesionalYDocente(ExperienciaProfesionalExt experienciaProfesionalExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(experienciaProfesionalExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIENCIA_PROFESIONAL_Y_DOCENTE,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}
	
	
	 /**
		 * Metodo que retorna la hoja de vida activa de una persona
		 * @param VinculacionExt
		 * @return List<VinculacionExt>
		 */
	public static List<HojaVidaExt> getHVPersonaAsociada(VinculacionExt hojaVida) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(hojaVida);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_HV_ASOCIADA_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<HojaVidaExt>>() {
			}.getType();
			List<HojaVidaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	
	
	/**
	 * Metodo para retornar la nacionalidad de una persona
	 * @param id
	 * @return VinculacionExt
	 */
    public static NacionalidadPerfil getNacionalidadPersona(NacionalidadPerfil nacionalidadPerfil) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(nacionalidadPerfil);
		    String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NACIONALIDAD_PERFIL, json,token, TIME_OUT, auditoriaSeguridad);
		    NacionalidadPerfil objNacionalidad = gson.fromJson(out, NacionalidadPerfil.class);
	        invalidToken(out);
	        return objNacionalidad;
		} catch (JsonSyntaxException e) {
		    logger.info("El servicio no encontro datos - getNacionalidadPersona(NacionalidadPerfil nacionalidadPerfil)", e);
		    return new NacionalidadPerfil();
		}
    }
	
    
	/**
	 * TraeInformacion de Hoja de Vida de una persona
	 * 
	 * @param
	 * @return
	 */
	public static PersonaExt getHojaVida(PersonaExt persona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(persona);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_HV_PERSONA,json,token, TIME_OUT, getAuditoriaSeguridad());
			PersonaExt list = gson.fromJson(out, PersonaExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new PersonaExt();
		}
	}

	public static List<ExperienciaProfesionalExt> getDateExperiencias(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIENCIAS_CALCULO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaProfesionalExt>>() {
			}.getType();
			List<ExperienciaProfesionalExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaProfesionalExt>();
		}
	}
	
	public static List<ExperienciaDocenteExt> getDateExperienciasDocente(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXPERIENCIAS_DOCENTE_CALCULO,json,token, TIME_OUT, getAuditoriaSeguridad());
			java.lang.reflect.Type type = new TypeToken<List<ExperienciaDocenteExt>>() {
			}.getType();
			List<ExperienciaDocenteExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<ExperienciaDocenteExt>();
		}
	}
}