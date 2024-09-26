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
import co.gov.dafp.sigep2.entities.AcreenciaObligacion;
import co.gov.dafp.sigep2.entities.ActividadPrivada;
import co.gov.dafp.sigep2.entities.BienesPatrimonio;
import co.gov.dafp.sigep2.entities.CuentasDeclaracion;
import co.gov.dafp.sigep2.entities.Declaracion;
import co.gov.dafp.sigep2.entities.HistoricoEntidadesDeclaracion;
import co.gov.dafp.sigep2.entities.IngresosDeclaracion;
import co.gov.dafp.sigep2.entities.OtrosIngresosDeclaracion;
import co.gov.dafp.sigep2.entities.ParticipacionJunta;
import co.gov.dafp.sigep2.entities.PersonaParentesco;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.ActividadPrivadaExt;
import co.gov.dafp.sigep2.mbean.ext.BienesPatrimonioExt;
import co.gov.dafp.sigep2.mbean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.mbean.ext.DeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.IngresosDeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.OtrosIngresosDeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionJuntaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaParentescoExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.rest.ErrorMensajes;
import co.gov.dafp.sigep2.rest.SerivesRestURL;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * @author joseviscaya
 *
 */
public class ComunicacionServiciosBR implements Serializable {
	private static Gson gson;
	private static String token;
	private static AuditoriaSeguridad auditoriaSeguridad;
	

	private static long TIME_OUT = ConfigurationBundleConstants.getTimeOutConversation();
	protected static Logger logger = Logger.getInstance(ComunicacionServiciosBR.class);
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1211106855000046961L;
	
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
		ComunicacionServiciosBR.auditoriaSeguridad = auditoriaSeguridad;
	}

	/**
	 * 
	 * @param codTipoInstitucion
	 * @return Metodo que consume el servicio de trae una lista de personas
	 *         parentesco del declarante por medio de codPersna La lista es de
	 *         Objetos Persona.
	 */
	public static List<PersonaParentescoExt> getPersonaParentescoPersona(PersonaParentesco personaParentesco) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(personaParentesco);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARENTESCO_PERSONA, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PersonaParentescoExt>>() {}.getType();
			List<PersonaParentescoExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<PersonaParentescoExt>();
		}
	}

	/***
	 * 
	 * @param datoContacto
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codParentescoPersona para hacer una actualizacion por ID de no
	 *         ir este parametro el sistema valida que se envie el CodPersona
	 *         para insertar un registro, si no detecta ninguno de los dos
	 *         atributos se generara un error que el servicio reprtara en el
	 *         objeto ErrorMensages.
	 */
	public static boolean setPersonaParentescoPersona(PersonaParentesco personaParentesco){
		if(personaParentesco.getPrimerNombre() != null) 
			personaParentesco.setPrimerNombre(personaParentesco.getPrimerNombre().trim());
		if(personaParentesco.getSegundoNombre() != null)
			personaParentesco.setSegundoNombre(personaParentesco.getSegundoNombre().trim());
		if(personaParentesco.getPrimerApellido() != null)
			personaParentesco.setPrimerApellido(personaParentesco.getPrimerApellido().trim());
		if(personaParentesco.getSegundoApellido() != null) 
			personaParentesco.setSegundoApellido(personaParentesco.getSegundoApellido().trim());
		if(personaParentesco.getNumIdentificacion() != null)
			personaParentesco.setNumIdentificacion(personaParentesco.getNumIdentificacion().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(personaParentesco);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PARENTESCO_PERSONA, json, token,TIME_OUT,auditoriaSeguridad);
			PersonaParentesco per = gson.fromJson(out, PersonaParentesco.class);
			if (per.isError()) {
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param codTipoInstitucion
	 * @return Metodo que consume el servicio de trae una lista de personas
	 *         parentesco del declarante por medio de codPersna La lista es de
	 *         Objetos Persona.
	 */
	public static List<BienesPatrimonioExt> getBienesPatrimonio(BienesPatrimonio bienesPatrimonio) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(bienesPatrimonio);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_BIENES_PATRIMONIO_POR_DECLARACION,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<BienesPatrimonioExt>>() {}.getType();
			List<BienesPatrimonioExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<>();
		}
	}

	/***
	 * 
	 * @param datoContacto
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codBienPatrimonio para hacer una actualizacion por ID de no ir
	 *         este parametro el sistema valida que se envie el codDeclaracion
	 *         para insertar un registro, si no detecta ninguno de los dos
	 *         atributos se generara un error que el servicio reprtara en el
	 *         objeto ErrorMensages.
	 */
	public static boolean setBienesPatrimonio(BienesPatrimonio bienesPatrimonio) {
		if(bienesPatrimonio.getIdentificacion() != null)
			bienesPatrimonio.setIdentificacion(bienesPatrimonio.getIdentificacion().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(bienesPatrimonio);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_BIENES_PATRIMONIO, json, token,TIME_OUT,auditoriaSeguridad);
			BienesPatrimonio per = gson.fromJson(out, BienesPatrimonio.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae una lista de Actividad
	 *         Privada del declarante por medio de codDeclaracion La lista es de
	 *         Objetos ActividadPrivada.
	 */
	public static List<ActividadPrivadaExt> getactividadprivada(long codDeclaracion, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion + ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ACTIVIDAD_PRIVADA_POR_DECLARACION, json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ActividadPrivadaExt>>() {}.getType();
			List<ActividadPrivadaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<ActividadPrivadaExt>();
		}
	}

	/***
	 * 
	 * @param ActividadPrivada
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codBienPatrimonio para hacer una actualizacion por ID de no ir
	 *         este parametro el sistema valida que se envie el codDeclaracion
	 *         para insertar un registro, si no detecta ninguno de los dos
	 *         atributos se generara un error que el servicio reprtara en el
	 *         objeto ErrorMensages.
	 */
	public static boolean setactividadprivada(ActividadPrivada actividadPrivada) {
		actividadPrivada.setDetalle(actividadPrivada.getDetalle().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(actividadPrivada);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ACTIVIDAD_PRIVADA, json, token,TIME_OUT,auditoriaSeguridad);
			ActividadPrivada per = gson.fromJson(out, ActividadPrivada.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param declaracion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         Decalsraciones por medio de codPersona La lista es de Objetos
	 *         Declaracion. Este servicio Valida la Existencia de los Siguientes
	 *         Parametros para hacer la consulta: CodPersona Confirmacion
	 *         CodTipoDeclaracion AnnoDeclaracion los grupos son los siguientes:
	 *         grupo 1 -> CodPersona ,Confirmacion grupo 2 -> CodPersona
	 *         ,Confirmacion, CodTipoDeclaracion grupo 3 -> CodPersona
	 *         ,Confirmacion, CodTipoDeclaracion, AnnoDeclaracion
	 */
	public static List<Declaracion> getdeclaracionpersona(Declaracion declaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(declaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACION_PERSONA, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Declaracion>>() {}.getType();
			List<Declaracion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<Declaracion>();
		}
	}

	/***
	 * 
	 * @param Declaracion
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codDeclaracion para hacer una actualizacion por ID de no ir
	 *         este parametro el sistema valida que se envie codPersona para
	 *         insertar un registro, si no detecta ninguno de los dos atributos
	 *         se generara un error que el servicio reprtara en el objeto
	 *         ErrorMensages.
	 */
	public static String setDeclaracion(Declaracion declaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA_HORA).create();
		if(declaracion.getCodDeclaracion() == null || declaracion.getCodDeclaracion().longValue() == 0) {
			try {
				String json = gson.toJson(declaracion);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DECLARACION_PERSONA,json,token, TIME_OUT, auditoriaSeguridad);
				Declaracion per = gson.fromJson(out, Declaracion.class);
				if (per.isError()) {
					logger.error("Error Transaccion", per.getMensaje());
					return "0";
				} else {
					return per.getMensajeTecnico();
				}
			} catch (JsonSyntaxException e) {
				logger.error("JsonSyntaxException", e.getMessage());
				return "0";
			}
		}else {
			return "0";
		}
		
	}
	
	/***
	 * 
	 * @param Declaracion
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codDeclaracion para hacer una actualizacion por ID de no ir
	 *         este parametro el sistema valida que se envie codPersona para
	 *         insertar un registro, si no detecta ninguno de los dos atributos
	 *         se generara un error que el servicio reprtara en el objeto
	 *         ErrorMensages.
	 */
	public static String setDeclaracionUp(Declaracion declaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA_HORA).create();
		try {
			if(declaracion.getCodDeclaracion() != null && declaracion.getCodDeclaracion().longValue() > 0) {
				declaracion.setAudAccion(63);
				try {
					String json = gson.toJson(declaracion);
					String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DECLARACION_PERSONA,json,token, TIME_OUT, auditoriaSeguridad);
					Declaracion per = gson.fromJson(out, Declaracion.class);
					if (per.isError()) {
						logger.error("Error Transaccion", per.getMensaje());
						return "0";
					} else {
						return per.getMensajeTecnico();
					}
				} catch (JsonSyntaxException e) {
					logger.error("JsonSyntaxException", e.getMessage());
					return "0";
				}
			}else {
				return "0";
			}
		} catch (Exception e) {
			logger.error("Exception", e.getMessage());
			return "0";
		}
		
	}
	
	/***
	 * 
	 * @param Declaracion
	 * @return Metodo que consume el servicio que actualiza o inserta una registro
	 *         en la base de datos, este servicio valida la existencia de
	 *         codDeclaracion para hacer una actualizacion por ID de no ir este
	 *         parametro el sistema valida que se envie codPersona para insertar un
	 *         registro, si no detecta ninguno de los dos atributos se generara un
	 *         error que el servicio reprtara en el objeto ErrorMensages.
	 */
	public static boolean setDeclaracionINI(Declaracion declaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(declaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DECLARACION_PERSONA, json, token,TIME_OUT,auditoriaSeguridad);
			Declaracion per = gson.fromJson(out, Declaracion.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/***
	 * 
	 * @param Declaracion
	 * @return Metodo que consume el servicio que elimina fisicamente un
	 *         registro de la base de datos por medio de su Id
	 *         codBienPatrimonio. si no detecta el atributo de eliminacion se
	 *         generara un error que el servicio reprtara en el objeto
	 *         ErrorMensages.
	 */
	public static boolean delBienespatrimonio(Long codBienPatrimonio) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codBienPatrimonio\":" + codBienPatrimonio + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.DEL_BIENES_PATRIMONIO, json, token,TIME_OUT,auditoriaSeguridad);
			Declaracion per = gson.fromJson(out, Declaracion.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param codPersona
	 * @return Metodo que consume el servicio de trae una lista de
	 *         OtrosIngresosDeclaracion por medio de codDeclaracion La lista es
	 *         de Objetos Declaracion.
	 */
	public static List<OtrosIngresosDeclaracionExt> getotrosingresosdeclaracion(
			long codDeclaracion, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion+ ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_OTROS_INGRESOS_DECLARACION, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<OtrosIngresosDeclaracionExt>>() {}.getType();
			List<OtrosIngresosDeclaracionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<OtrosIngresosDeclaracionExt>();
		}
	}

	/***
	 * 
	 * @param Declaracion
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codDeclaracion para hacer una actualizacion por ID de no ir
	 *         este parametro el sistema valida que se envie codPersona para
	 *         insertar un registro, si no detecta ninguno de los dos atributos
	 *         se generara un error que el servicio reprtara en el objeto
	 *         ErrorMensages.
	 */
	public static boolean setOtrosIngresosDeclaracion(OtrosIngresosDeclaracion declaracion) {
		if(declaracion.getConceptoIngreso() != null)
			declaracion.setConceptoIngreso(declaracion.getConceptoIngreso().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(declaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_OTROS_INGRESOS_DECLARACION, json, token,TIME_OUT,auditoriaSeguridad);
			OtrosIngresosDeclaracion per = gson.fromJson(out,OtrosIngresosDeclaracion.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param codPersona
	 * @return Metodo que consume el servicio de trae una lista de
	 *         IngresosDeclaracion por medio de codDeclaracion La lista es de
	 *         Objetos Declaracion.
	 */
	public static List<IngresosDeclaracionExt> getingresosdeclaracion(
			IngresosDeclaracion ingresosDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(ingresosDeclaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_INGRESO_DECLARACION, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<IngresosDeclaracionExt>>() {}.getType();
			List<IngresosDeclaracionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<>();
		}
	}
	/**
	 * 
	 * Creado Por: Jose viscaya
	 * @param ingresosDeclaracion
	 * @return
	 * 21 oct. 2019 14:40:45
	 * ComunicacionServiciosBR.java
	 */
	public static IngresosDeclaracion getTotalIngresos(IngresosDeclaracion ingresosDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(ingresosDeclaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_INGRESO_TOTAL_DECLARACION, json, token,TIME_OUT,auditoriaSeguridad);
			IngresosDeclaracion list = gson.fromJson(out, IngresosDeclaracion.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new IngresosDeclaracion();
		}
	}
	/***
	 * 
	 * @param Declaracion
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codDIngresosDeclaracion para hacer una actualizacion por ID de
	 *         no ir este parametro el sistema valida que se envie
	 *         codDeclaracion para insertar un registro, si no detecta ninguno
	 *         de los dos atributos se generara un error que el servicio
	 *         reprtara en el objeto ErrorMensages.
	 */
	public static boolean setingresosdeclaracion(
			IngresosDeclaracion ingresosDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(ingresosDeclaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_INGRESO_DECLARACION, json, token,TIME_OUT,auditoriaSeguridad);
			IngresosDeclaracion per = gson.fromJson(out,IngresosDeclaracion.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         CuentasDeclaracion por medio de codDeclaracion
	 */
	public static List<CuentasDeclaracion> getcuentasdeclaracion(
			long codDeclaracion, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion+ ", \"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CUENTAS_DECLARACION, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<CuentasDeclaracion>>() {}.getType();
			List<CuentasDeclaracion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<CuentasDeclaracion>();
		}
	}

	/***
	 * 
	 * @param CuentasDeclaracion
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codCuentasDeclaracion para hacer una actualizacion por ID de
	 *         no ir este parametro el sistema valida que se envie
	 *         codDeclaracion para insertar un registro, si no detecta ninguno
	 *         de los dos atributos se generara un error que el servicio
	 *         reprtara en el objeto ErrorMensages.
	 */
	public static boolean setcuentasdeclaracion(CuentasDeclaracion cuentasDeclaracion) {
		if(cuentasDeclaracion.getNumeroCuenta() != null)
			cuentasDeclaracion.setNumeroCuenta(cuentasDeclaracion.getNumeroCuenta().trim());
		if(cuentasDeclaracion.getSedeCuenta() != null)
			cuentasDeclaracion.setSedeCuenta(cuentasDeclaracion.getSedeCuenta().trim());
		if(cuentasDeclaracion.getEntidad() != null)
			cuentasDeclaracion.setEntidad(cuentasDeclaracion.getEntidad().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cuentasDeclaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CUENTAS_DECLARACION, json, token,TIME_OUT,auditoriaSeguridad);
			CuentasDeclaracion per = gson.fromJson(out,CuentasDeclaracion.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         CuentasAcreenciaObligacionDeclaracion por medio de codDeclaracion
	 */
	public static List<AcreenciaObligacion> getacreenciaobligacion(
			long codDeclaracion, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion+ ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ACREDITACION_OBLIGACION_POR_DECLARACION,json, token, TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<AcreenciaObligacion>>() {
			}.getType();
			List<AcreenciaObligacion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<AcreenciaObligacion>();
		}
	}

	/***
	 * 
	 * @param AcreenciaObligacion
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codAcreenciaObligacion para hacer una actualizacion por ID de
	 *         no ir este parametro el sistema valida que se envie
	 *         codDeclaracion para insertar un registro, si no detecta ninguno
	 *         de los dos atributos se generara un error que el servicio
	 *         reprtara en el objeto ErrorMensages.
	 */
	public static boolean setacreenciaobligacion(AcreenciaObligacion acreenciaObligacion) {
		acreenciaObligacion.setConceptoAcreencia(acreenciaObligacion.getConceptoAcreencia().trim());
		acreenciaObligacion.setEntidadPersona(acreenciaObligacion.getEntidadPersona().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(acreenciaObligacion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ACREDITACION_OBLIGACION, json, token,TIME_OUT,auditoriaSeguridad);
			AcreenciaObligacion per = gson.fromJson(out,AcreenciaObligacion.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         ParticipacionJunta por medio de codDeclaracion
	 */
	public static List<ParticipacionJuntaExt> getparticipacionjunta(long codDeclaracion, int flgActivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion+ ",\"flgActivo\":" + flgActivo + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARTICIPACION_JUNTA, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<ParticipacionJuntaExt>>() {
			}.getType();
			List<ParticipacionJuntaExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<ParticipacionJuntaExt>();
		}
	}

	/***
	 * 
	 * @param ParticipacionJunta
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de codParticipacionJunta para hacer una actualizacion por ID de
	 *         no ir este parametro el sistema valida que se envie
	 *         codDeclaracion para insertar un registro, si no detecta ninguno
	 *         de los dos atributos se generara un error que el servicio
	 *         reportara en el objeto ErrorMensages.
	 */
	public static boolean setparticipacionjunta(ParticipacionJunta participacionJunta) {
		participacionJunta.setEntidad(participacionJunta.getEntidad().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(participacionJunta);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_PARTICIPACION_JUNTA, json, token,TIME_OUT,auditoriaSeguridad);
			ParticipacionJunta per = gson.fromJson(out,ParticipacionJunta.class);
			if (per.isError()) {
				logger.error("Error Transaccion", per.getMensaje());
				return false;
			} else {
				return true;
			}
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param codPersona,
	 *            codTipoDeclaracion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         Decalsraciones por medio de codPersona y codTipoDeclaracion La
	 *         lista es de Objetos Declaracion.
	 */
	public static List<Declaracion> getdeclaracionpersonatipo(long codPersona,
			long codTipoDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona+ ",\"codTipoDeclaracion\":" + codTipoDeclaracion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACION_PERSONA_TIPOD, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Declaracion>>() {}.getType();
			List<Declaracion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<Declaracion>();
		}
	}

	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae una Decalsraciones por
	 *         medio de codDeclaracion La lista es de Objetos Declaracion.
	 */
	public static DeclaracionExt getdeclaracionid(long codDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACION_ID,json,token, TIME_OUT, auditoriaSeguridad);
			DeclaracionExt list = gson.fromJson(out, DeclaracionExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new DeclaracionExt();
		}
	}

	/**
	 * 
	 * @param codIngresoDeclaracion
	 * @return Metodo que consume el servicio de trae un objeto
	 *         IngresosDeclaracion por medio de codIngresoDeclaracion La lista
	 *         es de Objetos Declaracion.
	 */
	public static IngresosDeclaracionExt getingresosdeclaracionid(long codIngresoDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codIngresoDeclaracion\":" + codIngresoDeclaracion+ "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_INGRESO_DECLARACIN_ID, json, token,TIME_OUT,auditoriaSeguridad);
			IngresosDeclaracionExt list = gson.fromJson(out,IngresosDeclaracionExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new IngresosDeclaracionExt();
		}
	}

	/**
	 * 
	 * @param codPersona
	 * @return Metodo que consume el servicio de trae un objeto Declaracion por
	 *         medio de codPersona que devuelve la ultima declaracion del
	 *         usuario CodPersona
	 */
	public static Declaracion getdeclaracionUltima(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACION_ULTIMA, json, token,TIME_OUT,auditoriaSeguridad);
			Declaracion list = gson.fromJson(out, Declaracion.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new Declaracion();
		}
	}

	/**
	 * 
	 * @param codPersona
	 * @return Metodo que consume el servicio de trae un una lista de
	 *         DeclaracionExt por medio de filtro nombres, apellidos,
	 *         tipoidentificaacion nombre identificacion fehca de realizacion
	 *         CodPersona
	 */
	public static List<DeclaracionExt> getDeclaracionFiltro(
			DeclaracionExt declaracionExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(declaracionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACION_FILTRO, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<DeclaracionExt>>() {}.getType();
			List<DeclaracionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<DeclaracionExt>();
		}
	}

	/**
	 * 
	 * @param codPersona
	 * @return Metodo que consume el servicio de trae un una lista de
	 *         DeclaracionExt por medio de filtro nombres, apellidos,
	 *         tipoidentificaacion nombre identificacion fehca de realizacion
	 *         CodPersona
	 */
	public static List<DeclaracionExt> getDeclaracionFiltro(DeclaracionExt declaracionExt, int ini, int fin) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			if (declaracionExt == null) {
				return new ArrayList<DeclaracionExt>();
			}
			declaracionExt.setLimitIni(ini);
			declaracionExt.setLimitFin(fin);
			if(declaracionExt.getFlgModificado()!= null) {
				if(declaracionExt.getFlgModificado() == 0) {
					declaracionExt.setFlgModificado(null);
				}
			}
			String json = gson.toJson(declaracionExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACION_FILTRO, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<DeclaracionExt>>() {}.getType();
			List<DeclaracionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<DeclaracionExt>();
		}
	}

	/**
	 * 
	 * @param declaracion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         Decalsraciones por medio filtro, codtipodeclaracion, anno
	 *         delcaracion y confirmacion
	 */
	public static List<Declaracion> getdeclaracioncreacionn(
			Declaracion declaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(declaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACION_CREACION, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Declaracion>>() {}.getType();
			List<Declaracion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<Declaracion>();
		}
	}
	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae Decalsracion por medio
	 *         codDeclaracion
	 */
	public static DeclaracionExt getTotalDeclaacion(long codDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_TOTAL_DECLARACION, json, token,TIME_OUT,auditoriaSeguridad);
			DeclaracionExt list = gson.fromJson(out, DeclaracionExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new DeclaracionExt();
		}
	}

	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         IngresosDeclaracionExt por medio de codDeclaracion
	 */
	public static IngresosDeclaracionExt getSumaIngresos(long codDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion+ "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SUMA_INGRESOS,json,token, TIME_OUT, auditoriaSeguridad);
			IngresosDeclaracionExt list = gson.fromJson(out,IngresosDeclaracionExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new IngresosDeclaracionExt();
		}
	}
	
	/***
	 * 
	 * @param Declaracion
	 * @return Metodo que consume el servicio que actualiza una registro
	 *         en la base de datos, este servicio valida la existencia de
	 *         codDeclaracion para hacer una actualizacion por ID
	 *         servicio reprtara en el objeto ErrorMensages.
	 */
	public static boolean updateDeclaracion(Declaracion declaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		if(declaracion.getCodDeclaracion() != null || declaracion.getCodDeclaracion().longValue() != 0) {
			try {
				String json = gson.toJson(declaracion);
				String out = ConnectionHttp.sendPost(SerivesRestURL.UPDATE_DECLARACION_SELECTIVE,json,token, TIME_OUT, auditoriaSeguridad);
				Declaracion per = gson.fromJson(out, Declaracion.class);
				if (per.isError()) {
					logger.error("Error Transaccion", per.getMensaje());
					return false;
				} else {
					return true;
				}
			} catch (JsonSyntaxException e) {
				logger.error("JsonSyntaxException", e.getMessage());
				return false;
			}
		}else {
			return false;
		}
		
	}
	
	/**
	 * 
	 * @param codPersona
	 * @return Metodo que consume el servicio de trae un objeto Declaracion por
	 *         medio de codPersona que devuelve la ultima declaracion del
	 *         usuario CodPersona
	 */
	public static Declaracion getdeclaracionCarga(long codPersona) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + codPersona + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACION_CARGA, json, token,TIME_OUT,auditoriaSeguridad);
			Declaracion list = gson.fromJson(out, Declaracion.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new Declaracion();
		}
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosBR.java
	 * @param declaracion
	 * @return
	 */
	public static List<DeclaracionExt> getListaModificarFiltro(DeclaracionExt declaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(declaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DECLARACIONES_MODIFICAR	, json, token,TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<DeclaracionExt>>() {}.getType();
			List<DeclaracionExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new ArrayList<>();
		}
	}
	
	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae una lista de
	 *         IngresosDeclaracionExt por medio de codDeclaracion
	 */
	public static OtrosIngresosDeclaracion getSumaOtrosIngresos(long codDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codDeclaracion\":" + codDeclaracion+ "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SUMA_OTROS_INGRESOS,json,token, TIME_OUT, auditoriaSeguridad);
			OtrosIngresosDeclaracion list = gson.fromJson(out,OtrosIngresosDeclaracion.class);
			invalidToken(out);
			return list;
		}catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new OtrosIngresosDeclaracion();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosBR.java
	 * @param id
	 * @param codDeclaracion
	 * @return
	 */
	public static DatoContactoExt getDatoContacto(long id, long codDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codPersona\":" + id + ",\"codDeclaracion\":" + codDeclaracion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DATO_CONTACOTO_PERSONA_BR,json,token, TIME_OUT, auditoriaSeguridad);
			DatoContactoExt list = gson.fromJson(out, DatoContactoExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new DatoContactoExt();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosBR.java
	 * @param datoContacto
	 * @return
	 */
	public static boolean setDatoContacto(DatoContactoExt datoContacto) {
		if(datoContacto.getTelefonoResidencia()!=null)
			datoContacto.setTelefonoResidencia(datoContacto.getTelefonoResidencia().trim());
		if(datoContacto.getNumCelular()!=null)
			datoContacto.setNumCelular(datoContacto.getNumCelular().trim());
		if(datoContacto.getDireccionResidencia()!=null)
			datoContacto.setDireccionResidencia(datoContacto.getDireccionResidencia().trim());
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(datoContacto);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DATO_CONTACOTO_PERSONA_BR,json,token, TIME_OUT, auditoriaSeguridad);
			ErrorMensajes error = gson.fromJson(out, ErrorMensajes.class);
			invalidToken(out);
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
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosBR.java
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
	 * @File:   ComunicacionServiciosBR.java
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
	
	/**
	 * @Author: Maria Alejandra
	 * @Date: 08 Septiembre 2020
	 * @File: ComunicacionServiciosBR.java
	 * @param historicoEntidadesDeclaracion
	 * @return HistoricoEntidadesDeclaracion
	 * Metodo que inserta o modifica un registro de la tabla HISTORICO_ENTIDADES_DECLARACION
	 */
	public static HistoricoEntidadesDeclaracion setHistoricoEntidadesDeclaracion(HistoricoEntidadesDeclaracion historicoEntidadesDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(historicoEntidadesDeclaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_HISTORICO_ENTIDADES_DECLARACION, json, token, TIME_OUT, auditoriaSeguridad);
			HistoricoEntidadesDeclaracion result = gson.fromJson(out, HistoricoEntidadesDeclaracion.class);
			invalidToken(out);
			return result;
		} catch (Exception ex) {
			return new HistoricoEntidadesDeclaracion();
		}
	}

	/**
	 * @Author: Maria Alejandra
	 * @Date: 08 Septiembre 2020
	 * @File: ComunicacionServiciosBR.java
	 * @param historicoEntidadesDeclaracion
	 * @return List<HistoricoEntidadesDeclaracion>
	 * Metodo que retorna lista de HistoricoEntidadesDeclaracion  que cumplen con el filtro enviado
	 */
	public static List<HistoricoEntidadesDeclaracion> geHistoricoEntidadesDeclaracionFiltro(HistoricoEntidadesDeclaracion historicoEntidadesDeclaracion) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		getToken();
		String json = gson.toJson(historicoEntidadesDeclaracion);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_HISTORICO_ENTIDADES_DECLARACION_BY_FILTRO, json, token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<HistoricoEntidadesDeclaracion>>() {
			}.getType();
			List<HistoricoEntidadesDeclaracion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	 * 
	 * @param codDeclaracion
	 * @return Metodo que consume el servicio de trae una Decalsraciones por
	 *         medio de codDeclaracion La lista es de Objetos Declaracion.
	 */
	public static DatoContactoExt getdDatoContactoExt(DatoContactoExt obDeclaracion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(obDeclaracion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DATO_CONTACTO_EXT,json,token, TIME_OUT, auditoriaSeguridad);
			DatoContactoExt list = gson.fromJson(out, DatoContactoExt.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException", e.getMessage());
			return new DatoContactoExt();
		}
	}
}
