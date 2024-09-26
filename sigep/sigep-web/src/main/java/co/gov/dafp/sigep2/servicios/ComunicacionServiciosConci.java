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
import co.gov.dafp.sigep2.entities.CifrasConciliacion;
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
 * 
 * @author Jose Viscaya
 *
 */
public class ComunicacionServiciosConci implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8379402066051338978L;
	private static Gson gson;
	private static String token;
	private static AuditoriaSeguridad auditoriaSeguridad;
	
	private static final long TIME_OUT = ConfigurationBundleConstants.getTimeOutConversation();
	private static final Logger logger = Logger.getInstance(ComunicacionServiciosConci.class);
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss.s";
	
	
	
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
		ComunicacionServiciosConci.auditoriaSeguridad = auditoriaSeguridad;
	}

	/**
	 * Metodo para retornar los datos de CifrasConciliacion por medio del id
	 * @param cifrasConciliacion
	 * @return CifrasConciliacion
	 */
    public static List<CifrasConciliacion> getCifrasConciliacion(CifrasConciliacion cifrasConciliacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
		    String json = gson.toJson(cifrasConciliacion);
		    String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CIFRAS_CONCILIACION, json,token, TIME_OUT, auditoriaSeguridad);
		    java.lang.reflect.Type type = new TypeToken<List<CifrasConciliacion>>() {
		    }.getType();
		    List<CifrasConciliacion> list = gson.fromJson(out, type);
	        invalidToken(out);
	        return list;
		} catch (JsonSyntaxException e) {
		    logger.info("El servicio no encontro datos - getEntidadCargoId(long codCargoEntidad)", e);
		    return new ArrayList<>();
		}
    }
    
	
    
    /**
     * Metodo para registrar en la tabla CifrasConciliacion
     * @param cifrasConciliacion
     * @return boolean
     */
	public static boolean setCifrasConciliacion(CifrasConciliacion cifrasConciliacion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA_HORA).create();
		try {
			String json = gson.toJson(cifrasConciliacion);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CIFRAS_CONCILIACION, json,token, TIME_OUT, auditoriaSeguridad);
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
	 * 
	 * @Author: Jose Viscaya
	 * @param out
	 * @Date: 13 nov. 2021
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
			System.out.println(e.getMessage());
		}
	}   
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: 13 nov. 2021
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
}