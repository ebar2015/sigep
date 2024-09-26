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
import co.gov.dafp.sigep2.entities.Contrato;
import co.gov.dafp.sigep2.entities.ModificacionContrato;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.ContratoExt;
import co.gov.dafp.sigep2.mbean.ext.ModificacionContratoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.rest.ErrorMensajes;
import co.gov.dafp.sigep2.rest.SerivesRestURL;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * @author joseviscaya
 *
 */
public class ComunicacionServiciosCO implements Serializable {

	private static Gson gson;
	private static String token;
	private static long TIME_OUT = ConfigurationBundleConstants.getTimeOutConversation();
	private static AuditoriaSeguridad auditoriaSeguridad;
	private static final String strErrorExcepcion = "Ocurrió Una Excepción En La Operación";
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
	private static final long serialVersionUID = -8190685084269019180L;

	/**
	 * @param contrato
	 * @return Metodo que consume el servicio que actualiza o inserta una registro
	 *         en la base de datos, este servicio valida la existencia de
	 *         codContrato para hacer una actualizacion por ID de no ir este
	 *         parametro el sistema valida que se envie codPersona para insertar un
	 *         registro, si no detecta ninguno de los dos atributos se generara un
	 *         error que el servicio reportara en el objeto ErrorMensages.
	 */
	public static Contrato setContrato(Contrato contrato) {
		if (contrato.getNumeroContrato() != null)
			contrato.setNumeroContrato(contrato.getNumeroContrato().trim());
		if (contrato.getObjetoContrato() != null)
			contrato.setObjetoContrato(contrato.getObjetoContrato().trim());
		if (contrato.getAlcanceContrato() != null)
			contrato.setAlcanceContrato(contrato.getAlcanceContrato().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA_HORA).create();
		Contrato pre;
		try {
			String json = gson.toJson(contrato);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CONTRATO, json, token, TIME_OUT,
					auditoriaSeguridad);
			pre = gson.fromJson(out, Contrato.class);
			return pre;
		} catch (Exception e) {
			e.printStackTrace();
			pre = new Contrato();
			pre.setError(true);
			pre.setMensaje(strErrorExcepcion);
			pre.setMensajeTecnico(e.getMessage());
			return pre;
		}
	}

	/**
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
	public static List<PersonaExt> getPersonaporEntidadFiltro(PersonaExt personaExt) {
		getToken();
		if (personaExt.getLimitInit() == null) {
			List<PersonaExt> er = new ArrayList<PersonaExt>();
			PersonaExt err = new PersonaExt();
			err.setError(true);
			err.setMensaje("Hace Falta el atriburo LimitInit");
			er.add(err);
			return er;
		} else if (personaExt.getLimitEnd() == null || personaExt.getLimitEnd() == 0) {
			List<PersonaExt> er = new ArrayList<PersonaExt>();
			PersonaExt err = new PersonaExt();
			err.setError(true);
			err.setMensaje("Hace Falta el atriburo LimitEnd o valor invalido");
			er.add(err);
			return er;
		}
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(personaExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONAS_USUARIO_ACTIVO_FILTRO, json, token,
					TIME_OUT, auditoriaSeguridad);
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
	public static List<PersonaExt> getPersonaporEntidadFiltroF(PersonaExt personaExt) {
		getToken();
		if (personaExt.getLimitInit() == null) {
			List<PersonaExt> er = new ArrayList<PersonaExt>();
			PersonaExt err = new PersonaExt();
			err.setError(true);
			err.setMensaje("Hace Falta el atriburo LimitInit");
			er.add(err);
			return er;
		} else if (personaExt.getLimitEnd() == null || personaExt.getLimitEnd() == 0) {
			List<PersonaExt> er = new ArrayList<PersonaExt>();
			PersonaExt err = new PersonaExt();
			err.setError(true);
			err.setMensaje("Hace Falta el atriburo LimitEnd o valor invalido");
			er.add(err);
			return er;
		}
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(personaExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONAS_USUARIO_ACTIVO_FILTROF, json, token,
					TIME_OUT, auditoriaSeguridad);
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
	 * 
	 * @param codContrato
	 * @return Metodo que consume el servicio de trae una lista de
	 *         ModificacionContrato por medio de codDeclaracion
	 */
	public static List<ModificacionContrato> getModificacionContrato(long codContrato) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codContrato\":" + codContrato + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MODIFICACION_CONTRATO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ModificacionContrato>>() {
			}.getType();
			List<ModificacionContrato> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ModificacionContrato>();
		}
	}

	/**
	 * SERWEB000064
	 * 
	 * @param id
	 * @return
	 */
	public static List<ModificacionContratoExt> selectMontoContratoInicial(long codNuevoContratista) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		try {
			String json = "{\"codNuevoContratista\":" + codNuevoContratista + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MONTO_INICIAL_CONTRATO, json, token, TIME_OUT,
					auditoriaSeguridad);			
			java.lang.reflect.Type type = new TypeToken<List<ModificacionContratoExt>>() {
			}.getType();
			List<ModificacionContratoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 
	 * @param ModificacionContrato
	 * @return Metodo que consume el servicio que actualiza o inserta una registro
	 *         en la base de datos, este servicio valida la existencia de
	 *         codModificacionContrato para hacer una actualizacion por ID de no ir
	 *         este parametro el sistema valida que se envie codDeclaracion para
	 *         insertar un registro, si no detecta ninguno de los dos atributos se
	 *         generara un error que el servicio reprtara en el objeto
	 *         ErrorMensages.
	 */
	public static boolean setModificacionContrato(ModificacionContrato modificacionContrato) {
		if(modificacionContrato.getJustificacionModificacion() != null)
			modificacionContrato.setJustificacionModificacion(modificacionContrato.getJustificacionModificacion().trim());
		if(modificacionContrato.getNuevoAlcanceContrato() != null)
			modificacionContrato.setNuevoAlcanceContrato(modificacionContrato.getNuevoAlcanceContrato().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(modificacionContrato);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_MODIFICACION_CONTRATO, json,token, TIME_OUT, auditoriaSeguridad);
			ModificacionContrato per = gson.fromJson(out, ModificacionContrato.class);
			if (per.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * 
	 * @param codTipoModificacion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         ModificacionContrato por medio de codTipoModificacion y codContrato
	 *         siendo obligatorio enviar codContrato y opcional codTipoModificacion
	 */
	public static List<ModificacionContratoExt> getModificacionContratoPorTipoMod(long codTipoModificacionContrato,
			long codContrato, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codTipoModificacionContrato\":" + codTipoModificacionContrato + ",\"codContrato\":"
					+ codContrato + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MODIFICACION_CONTRATO_POR_TIPO_MOD, json, token,
					TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ModificacionContratoExt>>() {
			}.getType();
			List<ModificacionContratoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ModificacionContratoExt>();
		}
	}

	/**
	 * 
	 * @param codPersona
	 * @return Metodo que consume el servicio de trae una lista de ContratoExt por
	 *         medio de codPersona
	 */
	public static List<ContratoExt> getContratoPersona(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CONTRATO_PERSONA, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ContratoExt>>() {
			}.getType();
			List<ContratoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ContratoExt>();
		}
	}

	/**
	 * 
	 * @param codPersona
	 * @return Metodo que consume el servicio de trae una lista de ContratoExt
	 *         filtro a los siguientes atributos de la clase el servicio evalua la
	 *         existencia del atributo y lo incluye en la consulta tener encuenta
	 *         que si se envia un valor vacio el servicio lo tomaria como un dato
	 *         valido para la busqueda: codPersona codEntidad codDependenciaEntidad
	 *         codGrupoDependencia codDepartamento codMunicipio fechaFirma
	 *         fechaInicio fechaFin numeroContrato duracionContrato objetoContrato
	 *         alcanceContrato codMonedaMonto monto honorarios codFuenteFinanciacion
	 *         codAdministracionRecurso derechoExclusividad codSupervisor
	 *         fechaInicioSupervision dependenciaSupervision liquidado flgActivo
	 *         fechaLiquidacion Los atributos LinitIni y Limitend son obligatorios
	 *         para todas las consultas
	 */
	public static List<ContratoExt> getContratoFiltro(Contrato contrato) {
		if (contrato.getLimitEnd() < 1) {
			List<ContratoExt> err = new ArrayList<ContratoExt>();
			ContratoExt er = new ContratoExt();
			er.setError(true);
			er.setMensaje("El atributo limitEnd debe ser mayor a 0");
			err.add(er);
			return err;
		}
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(contrato);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CONTRATO_FILTRO, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ContratoExt>>() {
			}.getType();
			List<ContratoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ContratoExt>();
		}
	}

	/**
	 * 
	 * @param Contrato
	 * @return Metodo que consume el servicio de trae una lista de
	 *         ContratoFechaFinMenor filtro a los siguientes atributos de la clase
	 *         el servicio evalua la existencia del atributo y lo incluye en la
	 *         consulta tener encuenta que si se envia un valor vacio el servicio lo
	 *         tomaria como un dato valido para la busqueda: codPersona flgActivo
	 *         fechaFin es dato obligatorio para la consulta Los atributos LinitIni
	 *         y Limitend son obligatorios para todas las consultas
	 */
	public static List<ContratoExt> getContratoFechaFinMenor(Contrato contrato) {
		if (contrato.getLimitEnd() < 1) {
			List<ContratoExt> err = new ArrayList<ContratoExt>();
			ContratoExt er = new ContratoExt();
			er.setError(true);
			er.setMensaje("El atributo limitEnd debe ser mayor a 0");
			err.add(er);
			return err;
		}
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(contrato);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CONRATO_FECHA_FIN_MENOR, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ContratoExt>>() {
			}.getType();
			List<ContratoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ContratoExt>();
		}
	}

	/**
	 * 
	 * @param ContratoExt
	 * @return Metodo que consume el servicio de trae una lista de ContratoEntre
	 *         dias Vencidos filtro a los siguientes atributos de la clase el
	 *         servicio evalua la existencia del atributo y lo incluye en la
	 *         consulta tener encuenta que si se envia un valor vacio el servicio lo
	 *         tomaria como un dato valido para la busqueda: codPersona flgActivo
	 *         diasIni Y diasFin es dato obligatorio para la consulta Los atributos
	 *         LinitIni y Limitend son obligatorios para todas las consultas
	 */
	public static List<ContratoExt> getContratoEntreDias(ContratoExt contrato) {
		if (contrato.getLimitEnd() < 1) {
			List<ContratoExt> err = new ArrayList<ContratoExt>();
			ContratoExt er = new ContratoExt();
			er.setError(true);
			er.setMensaje("El atributo limitEnd debe ser mayor a 0");
			err.add(er);
			return err;
		}
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(contrato);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CONRATO_ENTRE_DIAS, json, token, TIME_OUT,
					auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ContratoExt>>() {
			}.getType();
			List<ContratoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ContratoExt>();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File: ComunicacionServiciosCO.java
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
	 * 
	 * @Author: Jose Viscaya
	 * @File: ComunicacionServiciosCO.java
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
		}
	}

}