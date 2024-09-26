/**
 * 
 */
package co.gov.dafp.sigep2.servicios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entities.ArchivoCalidadDatos;
import co.gov.dafp.sigep2.entities.Enfermedad;
import co.gov.dafp.sigep2.entities.Festivo;
import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.entities.ModificacionSituacionAdministrativa;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.ProgramaAcademico;
import co.gov.dafp.sigep2.entities.SituacionAdminVinculacion;
import co.gov.dafp.sigep2.entities.SituacionAdministrativa;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entities.UsuarioRolEntidad;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.HistoricoSituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.mbean.ext.InstitucionEducativaExt;
import co.gov.dafp.sigep2.mbean.ext.ModificacionSituacionAdministrativaExt;
import co.gov.dafp.sigep2.mbean.ext.ProgramaAcademicoExt;
import co.gov.dafp.sigep2.mbean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.mbean.ext.SituacionAdministrativaExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.mbean.preguntaopinion.PreguntaOpinion;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.rest.ErrorMensajes;
import co.gov.dafp.sigep2.rest.SerivesRestURL;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * @author joseviscaya
 *
 */
public class ComunicacionServiciosAdmin implements Serializable {
	
	private static Gson gson;
	private static String token;
	private static long TIME_OUT = ConfigurationBundleConstants.getTimeOutConversation();
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static AuditoriaSeguridad auditoriaSeguridad;
	private static final Logger logger = Logger.getInstance(ComunicacionServiciosEnt.class);
	private static final long serialVersionUID = -8190685084269019180L;
	
	
	
	/**
	 * 
	 * @param SituacionAdministrativa
	 * @return
	 * Metodo que consume el servicio de trae una lista de Objetos SituacionAdministrativa, mediante un filtro .
	 */
	public static List<SituacionAdministrativaExt> getSituacionAdministrativaFiltro(SituacionAdministrativaExt situacionAdministrativa) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdministrativa);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACIONES_ADMIN, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdministrativaExt>>() {
			}.getType();
			List<SituacionAdministrativaExt> list = gson.fromJson(out, type);
            return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SituacionAdministrativaExt>();
		}
	}
	/**
	 * 
	 * @param codSituacionAdministrativa
	 * @return
	 * Metodo que consume el servicio de trae un Objetos SituacionAdministrativa, mediante codSituacionAdministrativa .
	 */
	public static SituacionAdministrativaExt getSituacionPadre(long codSituacionAdministrativa) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codSituacionAdministrativa\":"+codSituacionAdministrativa+"}";
			
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACION_ADMIN_PADRE, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			SituacionAdministrativaExt list = gson.fromJson(out, SituacionAdministrativaExt.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new SituacionAdministrativaExt();
		}
	}
	
	/***
	 * 
	 * @param datoContacto
	 * @return Metodo que consume el servicio que actualiza o inserta una
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de CodSituacionAdministrativa para hacer una actualizacion por ID de no
	 *         ir este parametro el sistema valida que se envie el CodVinculacion
	 *         para insertar un registro, si no detecta ninguno de los dos
	 *         atributos se generara un error que el servicio reprtara en el
	 *         objeto ErrorMensages.
	 */
	public static SituacionAdminVinculacion setSituacionAdminVinculacion(SituacionAdminVinculacion situacionAdminVinculacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdminVinculacion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_SITUACION_ADMIN_VINCULACION, json, token,TIME_OUT,auditoriaSeguridad);
			SituacionAdminVinculacion per = gson.fromJson(out, SituacionAdminVinculacion.class);
			return per;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new SituacionAdminVinculacion();
		}
	}

	
	/***
	 * 
	 * @param situacionAdminVinculacion
	 * @return Metodo que consume el servicio que actualiza 
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de CodSituacionAdministrativa para hacer una actualizacion por ID
	 */
	public static SituacionAdminVinculacion updateSituacionAdminVinculacion(SituacionAdminVinculacion situacionAdminVinculacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdminVinculacion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.UPDATE_SITUACION_ADMIN_VINCULACION, json, token,TIME_OUT,auditoriaSeguridad);
			SituacionAdminVinculacion per = gson.fromJson(out, SituacionAdminVinculacion.class);
			return per;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new SituacionAdminVinculacion();
		}
	}
	
	
	/***
	 * 
	 * @param datoContacto
	 * @return Metodo que consume el servicio que actualiza o inserta un
	 *         registro en la base de datos, este servicio valida la existencia
	 *         de COD_HISTORICO_SITUACION_ADMIN para hacer una actualizacion por ID de no
	 *         ir este parametro el sistema valida que se envie el COD_HISTORICO_SITUACION_ADMIN
	 *         para insertar un registro, si no detecta ninguno de los 
	 *         atributos se generara un error que el servicio reprtara en el
	 *         objeto ErrorMensages.
	 */
	public static boolean setHistoricoAdministracion(HistoricoSituacionAdminVinculacionExt historicoSituacionAdminVinculacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(historicoSituacionAdminVinculacion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_HISTORICO_ADMINISTRACION, json, token,TIME_OUT,auditoriaSeguridad);
			HistoricoSituacionAdminVinculacionExt per = gson.fromJson(out, HistoricoSituacionAdminVinculacionExt.class);
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
	 * 
	 * @param SituacionAdministrativa
	 * @return
	 * Metodo que consume el servicio de trae una lista de Objetos SituacionAdministrativaExt, codVncilacion y el flg .
	 * activo
	 */
	public static List<SituacionAdminVinculacionExt> getSituacionVinculacion(SituacionAdminVinculacionExt situacionAdministrativa) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdministrativa);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACION_ADMIN_VINCULACION, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdminVinculacionExt>>() {
			}.getType();
			List<SituacionAdminVinculacionExt> list = gson.fromJson(out, type);
            return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SituacionAdminVinculacionExt>();
		}
	}
	/**
	 * 
	 * @param Enfermedad
	 * @return
	 * Metodo que consume el servicio de trae una lista de Objetos Enfermedad, nombre enfermedad y limites .
	 * activo
	 */
	public static List<Enfermedad> getEnfemedadLike(Enfermedad enfermedad) {
		getToken();
		if(enfermedad.getLimitFin() < 1) {
			List<Enfermedad> enfer  = new ArrayList<Enfermedad>();
			enfermedad.setError(true);
			enfermedad.setMensaje("Hace falta definir los limites de la consulta");
			enfer.add(enfermedad);
			return enfer;
		}
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(enfermedad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENFERMEDADES_NOMBRE_LIKE, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<Enfermedad>>() {
			}.getType();
			List<Enfermedad> list = gson.fromJson(out, type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Enfermedad>();
		}
	}
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosAdmin.java
	 * @param modificacionsituacionadministrativa
	 * @return
	 */
	public static boolean setmodificacionsituacionadministrativa(ModificacionSituacionAdministrativa modificacionsituacionadministrativa) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(modificacionsituacionadministrativa);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_MODIFICACION_SITUACION_ADMINISTRATIVA, json, token,TIME_OUT,auditoriaSeguridad);
			ModificacionSituacionAdministrativa per = gson.fromJson(out, ModificacionSituacionAdministrativa.class);
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
	 * 
	 * @param codCIE
	 * @return
	 */
	public static Enfermedad getEnfemedadCIE(String codCIE) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codCie10\":\""+codCIE+"\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENFERMEDAD_CIE, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			Enfermedad  res = gson.fromJson(out, Enfermedad.class);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return new Enfermedad();
		}
	}
	
	/**
	 * 
	 * @param Festivo
	 * @return
	 * Metodo que consume el servicio de trae una lista de Objetos SituacionAdministrativa, mediante un filtro .
	 */
	public static int getcantidaddiashabiles(Festivo festivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(festivo);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CANTIDAD_DIAS_HABILES, json,token, TIME_OUT, auditoriaSeguridad);
			return Integer.parseInt(out);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * @param Festivo
	 * @return
	 * Metodo que consume el servicio de trae una lista de Objetos de Festivo, mediante un filtro.
	 */
	public static List<Festivo> getDiasHabiles(Festivo festivo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(festivo);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_FECHA_FERIADO, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<Festivo>>() {
			}.getType();
			List<Festivo> list = gson.fromJson(out, type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Festivo>();
		}
	}
	
	/**
	 * @param codSAV
	 * @return List<ModificacionSituacionAdministrativa>
	 * @author Nestor.Riasco
	 */
	public static List<ModificacionSituacionAdministrativaExt> getModificacionSituacionAdministrativa(long codSAV) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codSituacionAdministrativaVinculacion\":\"" + codSAV + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MODIFICACION_SITUACION_ADMINISTRATIVA, json, token, TIME_OUT,auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<ModificacionSituacionAdministrativaExt>>() {
			}.getType();
			List<ModificacionSituacionAdministrativaExt> list = gson.fromJson(out, type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ModificacionSituacionAdministrativaExt>();
		}
	}
	
	/**
	 * @param situacionParticularExt
	 * @author jesus.torres
	 */
	public static boolean setSituacionParticular(SituacionAdministrativaExt situacionParticularExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionParticularExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_SITUACION_PARTICULAR, json,token, TIME_OUT, auditoriaSeguridad);
			SituacionAdministrativaExt per = gson.fromJson(out, SituacionAdministrativaExt.class);
			if(per.isError() != null) {
				if (per.isError()) {
					return false;
				} else {
					return true;
				}
			}else {
				return true;
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 
	 * @param nombreSituacion
	 * @param codEntidad
	 * @return
	 */
	public static boolean getNombreExisteSituParticular(SituacionAdministrativaExt situacionParticular) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionParticular);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACION_PARTICULAR_EXITE, json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdministrativa>>() {
			}.getType();
			List<SituacionAdministrativa> list = gson.fromJson(out, type);
			if (list.isEmpty()) {
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
	 * @param situacionParticularExt
	 * @author jesus.torres
	 */
	public static List<SituacionAdministrativaExt> getSituacionParticularesPorEntidad(SituacionAdministrativaExt situacionParticularExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionParticularExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACIONES_PARTICULARES_ENTIDAD, json,token, TIME_OUT, auditoriaSeguridad);			
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdministrativaExt>>() {
			}.getType();
			List<SituacionAdministrativaExt> list = gson.fromJson(out, type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SituacionAdministrativaExt>();
		}
	}
	
	/**
	 * @param situacionParticularExt
	 * @author jesus.torres
	 */
	public static List<SituacionAdministrativaExt> getSituacionParticularAsignada(SituacionAdministrativaExt situacionParticularExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionParticularExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACIONES_PARTICULARES_ASIGNADAS, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdministrativaExt>>() {
			}.getType();
			List<SituacionAdministrativaExt> list = gson.fromJson(out, type);
			if(!list.isEmpty()) {
				if(!list.get(0).isError()) {
					return list;
				}else {
					return new ArrayList<>();
				}
			}else {
				return new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		    return new ArrayList<>();
		}
	}
	
	/**
	 * @param situacionParticularExt
	 * @author jesus.torres
	 */
	public static List<SituacionAdministrativaExt> getSituacionAsignada(SituacionAdministrativaExt situacionParticularExt ) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionParticularExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACIONES_ASIGNADAS, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdministrativaExt>>() {
			}.getType();
			List<SituacionAdministrativaExt> list = gson.fromJson(out, type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SituacionAdministrativaExt>();
		}
	}
	
	/**
	 * Retorna parametricas por campo orden
	 * @param Parametrica
	 * @return List<Parametrica>
	 */
    public static List<Parametrica> getParametricaByOrden(Parametrica parametrica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(parametrica);
		    String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRICA_BY_ORDEN, json,token, TIME_OUT, auditoriaSeguridad);
		    java.lang.reflect.Type type = new TypeToken<List<Parametrica>>() {
		    }.getType();
		    List<Parametrica> list = gson.fromJson(out, type);
	        invalidToken(out);
	        return list;
		} catch (JsonSyntaxException e) {
		    return new ArrayList<>();
		}
    }
    /**
     * 
     * @Author: Jose Viscaya
     * @Date:   May 8, 2019, 8:10:37 AM
     * @File:   ComunicacionServiciosAdmin.java
     * @param enfermedad
     * @return
     */
    public static List<Enfermedad> getEnfermedadFiltro(Enfermedad enfermedad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(enfermedad);
		    String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENFERMEDADES_FILTRO, json,token, TIME_OUT, auditoriaSeguridad);
		    java.lang.reflect.Type type = new TypeToken<List<Enfermedad>>() {
		    }.getType();
		    List<Enfermedad> list = gson.fromJson(out, type);
	        invalidToken(out);
	        return list;
		} catch (JsonSyntaxException e) {
		    return new ArrayList<>();
		}
    }
    /**
     * 
     * @Author: Jose Viscaya
     * @Date:   May 8, 2019, 8:29:59 AM
     * @File:   ComunicacionServiciosAdmin.java
     * @param enfermedad
     * @return
     */
    public static Enfermedad setEnfermedad(Enfermedad enfermedad) {
    	getToken();
    	gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
    	try {
    		String json = gson.toJson(enfermedad);
    		String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENFERMEDAD, json,token, TIME_OUT, auditoriaSeguridad);
    		Enfermedad list = gson.fromJson(out, Enfermedad.class);
    		invalidToken(out);
    		return list;
    	} catch (JsonSyntaxException e) {
    		return new Enfermedad();
    	}
    }
    /**
     * 
     * @Author: Jose Viscaya
     * @Date:   9 may. 2019, 8:48:43
     * @File:   ComunicacionServiciosAdmin.java
     * @param situacionAdministrativa
     * @return
     */
    public static SituacionAdministrativa setSituacionAdministrativa(SituacionAdministrativa situacionAdministrativa) {
    	getToken();
    	gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
    	try {
    		String json = gson.toJson(situacionAdministrativa);
    		String out = ConnectionHttp.sendPost(SerivesRestURL.SET_SITUACIONADMINISTRATIVA, json,token, TIME_OUT, auditoriaSeguridad);
    		SituacionAdministrativa list = gson.fromJson(out, SituacionAdministrativa.class);
    		invalidToken(out);
    		return list;
    	} catch (JsonSyntaxException e) {
    		return new SituacionAdministrativa();
    	}
    }
    /**
     * 
     * @Author: Jose Viscaya
     * @Date:   9 may. 2019, 9:22:02
     * @File:   ComunicacionServiciosAdmin.java
     * @param situacionAdministrativa
     * @return
     */
    public static List<SituacionAdministrativa> getSituacionAdministrativaFiltro(SituacionAdministrativa situacionAdministrativa) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(situacionAdministrativa);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_SITUACIONES_ADMIN, json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<SituacionAdministrativa>>() {
			}.getType();
			List<SituacionAdministrativa> list = gson.fromJson(out, type);
            return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SituacionAdministrativa>();
		}
	}
    
    
    /**
     * 
     * @Author: Jhon De Avila Mercado
     * @File:   ComunicacionServiciosAdmin.java
     * @param token
     * @return Estado de la sesion <code>true</code> para sesion activa o <code>false</code> para sesion inactiva
     */
    public static boolean getEstadoSesionValida(String token) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendGet(SerivesRestURL.GET_ESTADO_SESION + "/" + token, token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			return !Boolean.valueOf(out);
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosAdmin.java
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
	 * @File:   ComunicacionServiciosAdmin.java
	 * @param out
	 */
	 private static void invalidToken(String out) {
 		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
 		try {
 			ErrorMensajes err = gson.fromJson(out, ErrorMensajes.class);
 			if(err.getCodigo() == 101) {
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
	 
	 public static InstitucionEducativa guardarInstitucionEducativa(InstitucionEducativa institucion) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(institucion);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GUARDAR_INSTITUCION_EDUCATIVA, json,token, TIME_OUT, auditoriaSeguridad);
				InstitucionEducativa inst = gson.fromJson(out, InstitucionEducativa.class);
				return inst;
				
			} catch (JsonSyntaxException ex) {
				logger.error("Error public static InstitucionEducativa guardarInstitucionEducativa(InstitucionEducativa institucion) ComunicacionServiciosAdmin.java: " + ex.getMessage(), ex);
				return new InstitucionEducativa();
			}
		}
		
		public static List<InstitucionEducativaExt> obtenerInstitucionEducativaFiltro(InstitucionEducativa institucion) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(institucion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_INSTITUCION_EDUCATIVA, json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<InstitucionEducativaExt>>() {
				}.getType();
				List<InstitucionEducativaExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<InstitucionEducativa> obtenerInstitucionEducativaFiltro(InstitucionEducativa institucion) ComunicacionServiciosAdmin.java error: " + ex.getMessage() , ex);
				return new ArrayList<>();
			}
		}
		
		public static List<ProgramaAcademicoExt> obtenerProgramaAcademicoFiltro(ProgramaAcademico programa) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(programa);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_PROGRAMA_ACADEMICO	, json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<ProgramaAcademicoExt>>() {
				}.getType();
				List<ProgramaAcademicoExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error(" public static List<ProgramaAcademico> obtenerProgramaAcademicoFiltro(ProgramaAcademico programa) ComunicacionServiciosAdmin.java error: " + ex.getMessage() , ex);;
				return new ArrayList<>();
			}
		}
		
		
		/**Metodo utilizado para validar si un programa academico se encuentra Duplicado*/
		public static List<ProgramaAcademico> obtenerProgramaAcademicoDuplicado(ProgramaAcademico programa) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(programa);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_PROGRAMA_ACADEMICO_DUPLICADO, json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<ProgramaAcademico>>() {
				}.getType();
				List<ProgramaAcademico> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error(" public static List<ProgramaAcademico> obtenerProgramaAcademicoDuplicado(ProgramaAcademico programa) ComunicacionServiciosAdmin.java error: " + ex.getMessage() , ex);;
				return new ArrayList<>();
			}
		}
		
		
		public static ProgramaAcademico guardarProgramaAcademico(ProgramaAcademico programa) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(programa);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GUARDAR_PROGRAMA_ACADEMICO , json,token, TIME_OUT, auditoriaSeguridad);
				ProgramaAcademico inst = gson.fromJson(out, ProgramaAcademico.class);
				return inst;
				
			} catch (JsonSyntaxException ex) {
				logger.error("public static ProgramaAcademico guardarProgramaAcademico(ProgramaAcademico programa) ComunicacionServiciosAdmin.java: " + ex.getMessage(), ex);
				return new ProgramaAcademico();
			}
		}
		

		/**
		 * Maria Alejandra Colorado
		 * Retorna una pregunta dado su nombre
		 * parameter name (nombre de pregunta)
		 */
		public static List<PreguntaOpinion> preguntaOpinionByNombre(PreguntaOpinion preguntaOpinion) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(preguntaOpinion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PREGUNTA_POR_NOMBRE	, json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<PreguntaOpinion>>() {
				}.getType();
				List<PreguntaOpinion> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error(" public static preguntaOpinionByNombre(PreguntaOpinion preguntaOpinion) ComunicacionServiciosAdmin.java error: " + ex.getMessage() , ex);;
				return new ArrayList<>();
			}
		}
		
		/**
		 * Realiza proceso de asociacion del rol funcional a todas las entidades del sistema
		 * @param UsuarioRolEntidad
		 * @author Maria Alejandra colorado
		 */
		public static UsuarioRolEntidad asociarRolFuncionalTodasEntidades(UsuarioRolEntidad usuarioRolEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(usuarioRolEntidad);
				String out = ConnectionHttp.sendPost(SerivesRestURL.ASOCIAR_ROL_AF_TODAS_ENTIDADES,json,token, TIME_OUT, auditoriaSeguridad);
				UsuarioRolEntidad per = gson.fromJson(out, UsuarioRolEntidad.class);
				return per;
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new UsuarioRolEntidad();
			}
		}
		
		/**
		 * Consulta un usuario por numero y tipo de identificacion de la persona
		 * @param UsuarioExt
		 * @return Usuario
		 * @author Maria Alejandra colorado
		 */
		public static Usuario getUsuarioByPersona(UsuarioExt usuarioExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(usuarioExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_BY_PERSONA,json,token, TIME_OUT, auditoriaSeguridad);
				Usuario per = gson.fromJson(out, Usuario.class);
				return per;
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new Usuario();
			}
		}
			
		/**
		 * Realiza proceso de asociacion de una persona a un rol
		 * Este metodo llama un procedimiento almacenado que se encarga de realizar las validaciones de:
		 * 	- Verifica si la persona ya esta asociado a la entidad, sino crea el registro en UsarioEntidad para la entidad enviada.
		 *  - Verifica si la persona ya tiene el rol enviado asociado a esa entidad, sino, tambien lo crea en la tabla UsuarioRolEntidad
		 * @param UsuarioRolEntidad
		 * @author Maria Alejandra colorado
		 */
		public static UsuarioRolEntidad asociarRolAUsuario(UsuarioRolEntidadExt usuarioRolEntidadExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(usuarioRolEntidadExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.ASOCIAR_ROL_A_USUARIO,json,token, TIME_OUT, auditoriaSeguridad);
				UsuarioRolEntidad per = gson.fromJson(out, UsuarioRolEntidad.class);
				return per;
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new UsuarioRolEntidad();
			}
		}
		
		/**
		 * Metodo que se retorna información de la tabla usuarioRolEntidad. 
		 * Este servicio debe de recibir codRol y codUsuario de manera obligatoria y  codEntidad opcional
		 * @param UsuarioRolEntidadExt
		 * @return UsuarioRolEntidad
		 * @author Maria Alejandra colorado
		 */
		public static UsuarioRolEntidad getUsuarioRolEntidad(UsuarioRolEntidadExt usuarioRolEntidadExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(usuarioRolEntidadExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_ROL_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				UsuarioRolEntidad per = gson.fromJson(out, UsuarioRolEntidad.class);
				return per;
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new UsuarioRolEntidad();
			}
		}
		
		public static boolean setArchivoCalidadDatos(String nombreArchivo, String tipoArchivo, byte[] archivo){
			try{
				byte[] bbyte = Base64.getEncoder().encode(archivo);
				String strArchivo = new String(bbyte, StandardCharsets.UTF_8);
				ArchivoCalidadDatos ar = new ArchivoCalidadDatos();
				ar.setNombreArchivo(nombreArchivo);
				ar.setArchivo(strArchivo);			
				String json = gson.toJson(ar);
				String out = ConnectionHttp.sendPostNoBase64(SerivesRestURL.SET_EXCEL_CALIDAD_DATOS, json, token,TIME_OUT,auditoriaSeguridad);
			}catch(Exception z){
				return false;
			}
			return true;
		}
		
		public static boolean aplicarCalidadDatos(String emailNotifica){
			try{
				getToken();
				String json = "{\"correoNotificacion\":\"" + emailNotifica + "\"}";
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CALIDAD_DATOS, json, token,TIME_OUT,auditoriaSeguridad);
			}catch(Exception z){
				return false;
			}
			return true;
		}		

		/**
		 * Metodo que retorna true o false en caso tal de que el usuario cuente 
		 * con alguno de los siguientes roles activos 18, 14, 16, 6, 15, 17, 10, 8
		 * @param UsuarioExt
		 * @return Usuario
		 * @author Maria Alejandra colorado
		 */
		public static boolean getUsuariosAdmin(UsuarioExt usuarioExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(usuarioExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_ADMIN,json,token, TIME_OUT, auditoriaSeguridad);
				UsuarioExt per = gson.fromJson(out, UsuarioExt.class);
				if (per!=null && per.getCantidadRoles() >0) {
					return true;
				}
				return false;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return false;
			}
		}
}