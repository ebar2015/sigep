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
import co.gov.dafp.sigep2.entities.AsignacionSalarial;
import co.gov.dafp.sigep2.entities.Autonomia;
import co.gov.dafp.sigep2.entities.Cargo;
import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entities.DependenciaEntidad;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadEscision;
import co.gov.dafp.sigep2.entities.EntidadFusion;
import co.gov.dafp.sigep2.entities.EntidadHistoricoEstados;
import co.gov.dafp.sigep2.entities.EntidadPoliticaPublica;
import co.gov.dafp.sigep2.entities.EntidadSistemaRegimen;
import co.gov.dafp.sigep2.entities.EscalaSalarial;
import co.gov.dafp.sigep2.entities.Estructura;
import co.gov.dafp.sigep2.entities.IncrementoSalarial;
import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.entities.Nomenclatura;
import co.gov.dafp.sigep2.entities.NomenclaturaDenominacion;
import co.gov.dafp.sigep2.entities.NomenclaturaEntidad;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.PepCuestionario;
import co.gov.dafp.sigep2.entities.PepPreguntasCuestionario;
import co.gov.dafp.sigep2.entities.PepPreguntasCuestionarioDetalle;
import co.gov.dafp.sigep2.entities.PepRespuestaCuestionario;
import co.gov.dafp.sigep2.entities.PepRespuestaCuestionarioDetalle;
import co.gov.dafp.sigep2.entities.PepRespuestasPreguntaCuestionario;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entities.PlantaPersonaUtlUan;
import co.gov.dafp.sigep2.entities.PoliticaPublica;
import co.gov.dafp.sigep2.entities.ProgramaAcademico;
import co.gov.dafp.sigep2.entities.UsuarioEntidad;
import co.gov.dafp.sigep2.entities.UsuarioRolDependencia;
import co.gov.dafp.sigep2.entities.UsuarioRolEntidad;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.AsignacionSalarialExt;
import co.gov.dafp.sigep2.mbean.ext.AutonomiaExt;
import co.gov.dafp.sigep2.mbean.ext.CargoExt;
import co.gov.dafp.sigep2.mbean.ext.DenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadHistoricoEstadosExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPoliticaPublicaExt;
import co.gov.dafp.sigep2.mbean.ext.EstructuraExt;
import co.gov.dafp.sigep2.mbean.ext.GrupoDependenciaExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.mbean.ext.PepCuestionarioExt;
import co.gov.dafp.sigep2.mbean.ext.PepPreguntasCuestionarioDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.PepPreguntasCuestionarioExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PlantaPersonaUtlUanExt;
import co.gov.dafp.sigep2.mbean.ext.PoliticaPublicaExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.rest.ErrorMensajes;
import co.gov.dafp.sigep2.rest.SerivesRestURL;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * @author joseviscaya
 *
 */
public class ComunicacionServiciosEnt implements Serializable {
	private static Gson gson;
	private static String token;
	private static AuditoriaSeguridad auditoriaSeguridad;
	private static long TIME_OUT = ConfigurationBundleConstants.getTimeOutConversation();
	private static final long serialVersionUID = -7290999141241541658L;
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final Logger logger = Logger.getInstance(ComunicacionServiciosEnt.class);
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param id
	 * @return
	 */
	public static InstitucionEducativa getInstitucionEducaporId(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codInstitucionEducativa\":\"" + id + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_IEDUCATIVAS_POR_ID,json,token, TIME_OUT, auditoriaSeguridad);
			InstitucionEducativa list = gson.fromJson(out, InstitucionEducativa.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @return
	 */
	public static List<EntidadDTO> entidadesTotal() {
		getToken();
		String json = "";
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_ENTIDADES,json,token, TIME_OUT, auditoriaSeguridad);
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
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @return
	 */
	public static List<InstitucionEducativa> listInstitucionEducativa() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_IEDUCATIVAS, "", token, TIME_OUT,auditoriaSeguridad);
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
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param id
	 * @return
	 */
	
	public static Entidad getEntidadPorId(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codEntidad\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_POR_ID,json,token, TIME_OUT, auditoriaSeguridad);
			Entidad list = gson.fromJson(out, Entidad.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Entidad();
		}
	}
    /**
     * 
     * @Author: Jose Viscaya
     * @File:   ComunicacionServiciosEnt.java
     * @param codTipoInstitucion
     * @return
     */
	public static List<InstitucionEducativa> getInstitucionTipo(int codTipoInstitucion) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codTipoInstitucion\":" + codTipoInstitucion + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_INTITUCION_POR_TIPO,json,token, TIME_OUT, auditoriaSeguridad);
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
	 *
	 * @return
	 */
	public static List<DependenciaEntidad> getDependenciaentidad() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DEPENDENCIA_ENTIDAD, "", token, TIME_OUT,auditoriaSeguridad);
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
	 *
	 * @param
	 * @return
	 */
	public static List<CargoExt> getcargoporcodtipoentidad(long codTipoEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codTipoEntidad\":" + codTipoEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_POR_COD_TIPO_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
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
	 *
	 * @param rol
	 * @return
	 */
	public static List<CargoExt> getcargostodos(int limitInit, int limitEnd) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"limitInit\":" + limitInit + ",\"limitEnd\":" + limitEnd + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOD_TODOS,json,token, TIME_OUT, auditoriaSeguridad);
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
	 *
	 * @param ProgramaAcademico
	 * @return
	 */
	public static ProgramaAcademico getprogramaacademicoporid(long codTituloAcademico) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codTituloAcademico\":" + codTituloAcademico + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PROGRAMA_ACADEMICO_POR_ID,json,token, TIME_OUT, auditoriaSeguridad);
			ProgramaAcademico list = gson.fromJson(out, ProgramaAcademico.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ProgramaAcademico();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   7 may. 2019, 17:23:44
	 * @File:   ComunicacionServiciosEnt.java
	 * @return
	 */
	public static List<Entidad> entidadesTotalbean() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_ENTIDADES, json, "", TIME_OUT,auditoriaSeguridad);
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
	 *  Methoso que implementa el servicios de traer Entdades por
	 * cualquier campo o conjunto de campos que se envien ene le objeto es
	 * obligatrio enviar los limites si no se envian por defecto traera los primeros
	 * 10 registros
	 *
	 * @return List<Entidad>
	 */
	public static List<Entidad> getEntidadesFiltro(Entidad entidad) {
		getToken();
		if(entidad.getLimitEnd() != null) {
			if (entidad.getLimitEnd() < 1) {
				entidad.setLimitEnd(500);
			}
		}else {
			entidad.setLimitEnd(100);
			entidad.setLimitInit(0);
		}
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		String json = gson.toJson(entidad);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
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
	 * 
	 * @param entidad
	 * @return
	 */
	public static Entidad getEntidadDepMun(Entidad entidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(entidad);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_DEP_MUN,json,token, TIME_OUT, auditoriaSeguridad);
			Entidad list = gson.fromJson(out, Entidad.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Entidad();
		}
	}

	/**
	 * @param entidad
	 * @return Metodo que inserta y actualiza dependiendo de los datos enviados,
	 *         evalua el ID principal.
	 */
	public static boolean setDatosEntidad(Entidad entidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(entidad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
			Entidad res = gson.fromJson(out, Entidad.class);
			invalidToken(out);
			return !res.isError();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;

		}
	}
	
	/**
	 * @param entidad
	 * @return Metodo que inserta y actualiza dependiendo de los datos enviados,
	 *         evalua el ID principal.
	 */
	public static Entidad setEntidade(Entidad entidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		if(entidad.getJustificacion() != null)
			entidad.setJustificacion(entidad.getJustificacion().trim());
		if(entidad.getNit() != null)
			entidad.setNit(entidad.getNit().trim());
		if(entidad.getEmailEntidad() != null)
			entidad.setEmailEntidad(entidad.getEmailEntidad().trim());
		if(entidad.getPaginaWeb() != null )
			entidad.setPaginaWeb(entidad.getPaginaWeb().trim());
		if(entidad.getDireccion() != null)
			entidad.setDireccion(entidad.getDireccion().trim());
		if(entidad.getTelefonoEntidad() != null)
			entidad.setTelefonoEntidad(entidad.getTelefonoEntidad().trim());
		if(entidad.getFax() != null)
			entidad.setFax(entidad.getFax().trim());
		if(entidad.getCodRepresentanteLegal() != null)
			entidad.setCodRepresentanteLegal(entidad.getCodRepresentanteLegal().trim());
		try {
			String json = gson.toJson(entidad);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
			Entidad res = gson.fromJson(out, Entidad.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new Entidad();

		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param nombreEntidad
	 * @return
	 */
	public static List<Entidad> getEntidadesPorLike(String nombreEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "";
		try {
			json = "{\"nombreEntidad\":\"" + nombreEntidad + "\"}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_LIKE,json,token, TIME_OUT, auditoriaSeguridad);
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
	 * @param entidadSistemaRegimen
	 * @return Metodo que inserta y actualiza dependiendo de los datos enviados,
	 *         evalua el ID principal.
	 */
	public static EntidadSistemaRegimen setEntidadSistemaRegimen(EntidadSistemaRegimen entidadSistemaRegimen) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(entidadSistemaRegimen);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_SISTEMA_REGIMEN,json,token, TIME_OUT, auditoriaSeguridad);
			EntidadSistemaRegimen res = gson.fromJson(out, EntidadSistemaRegimen.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new EntidadSistemaRegimen();

		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codEntidad
	 * @return
	 */
	public static List<EntidadSistemaRegimen> getEntidadesSistemaRegimenPorIdEntidad(BigDecimal codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "";
		try {
			json = "{\"codEntidad\":" + codEntidad + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_SISTEMA_REGIMEN_POR_ID_ENTIDAD, json, token,
					TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadSistemaRegimen>>() {
			}.getType();
			List<EntidadSistemaRegimen> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EntidadSistemaRegimen>();
		}
	}

	/**
	 * @param getEntidadHistoricoEstados
	 * @return
	 */
	public static List<EntidadHistoricoEstadosExt> getEntidadHistoricoEstados(Object entidadHistoricoEstados) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		String json = gson.toJson(entidadHistoricoEstados);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_ENTIDADES_HISTORICO_ESTADOS, json, token,
					TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadHistoricoEstadosExt>>() {
			}.getType();
			List<EntidadHistoricoEstadosExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EntidadHistoricoEstadosExt>();
		}
	}

	/**
	 * @param entidadHistoricoEstados
	 * @return
	 */
    public static EntidadHistoricoEstados setEntidadHistoricoEstado(EntidadHistoricoEstados entidadHistoricoEstados) {
        getToken();
        gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
        try {
            String json = gson.toJson(entidadHistoricoEstados);
            String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_HISTORICO_ESTADO,json,token, TIME_OUT, auditoriaSeguridad);
            EntidadHistoricoEstados res = gson.fromJson(out, EntidadHistoricoEstados.class);
            invalidToken(out);
            return res;
        } catch (JsonSyntaxException e) {
            return new EntidadHistoricoEstados();

        }
    }

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
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
	 * @File:   ComunicacionServiciosEnt.java
	 * @param out
	 */
	private static void invalidToken(String out) {
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			ErrorMensajes err = gson.fromJson(out, ErrorMensajes.class);
			if(err != null) {
				if (err.getCodigo() == 101) {
					FacesContext context = FacesContext.getCurrentInstance();
					ExternalContext externalContext = context.getExternalContext();
					externalContext.getSessionMap().put("sesionValida", false);
					externalContext.getSessionMap().remove("token");
					Object session = externalContext.getSession(false);
					HttpSession httpSession = (HttpSession) session;
					httpSession.invalidate();
				}
			}
			
		} catch (JsonSyntaxException e) {
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param entidadEscision
	 * @return
	 */
	public static List<EntidadEscision> getEntidadEscision(EntidadEscision entidadEscision) {
		getToken();
		String json = "";
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_ESCISION,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadEscision>>() {
			}.getType();
			List<EntidadEscision> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<EntidadEscision>();
		}
	}

	public static EntidadEscision setEntidadEscision(EntidadEscision entidadEscision) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(entidadEscision);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_ESCISION,json,token, TIME_OUT, auditoriaSeguridad);
			EntidadEscision res = gson.fromJson(out, EntidadEscision.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new EntidadEscision();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param entidadPlantaDetalle
	 * @return
	 */
	public static List<EntidadPlantaDetalleExt> getPlantaPersonalEntidadCargos(EntidadPlantaDetalleExt entidadPlantaDetalle) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(entidadPlantaDetalle);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PLANTA_PERSONAL_ENTIDAD_CARGOS,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
			}.getType();
			List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;

		} catch (JsonSyntaxException ex) {
			logger.error("public static List<EntidadPlantaDetalleExt> getPlantaPersonalEntidadCargos(EntidadPlantaDetalleExt entidadPlantaDetalle) ComuniacionServiciosEnt.java error: " + ex.getMessage() , ex);;
			return new ArrayList<>();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param cargo
	 * @return
	 */
	public static List<CargoExt> getCargosFiltro(Cargo cargo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(cargo);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
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
	 * @param VinculacionExt
	 * @return Metodo inserta una registro en la base de datos de una nueva
	 *         vinculacion
	 */
	public static EntidadPlantaExt setEntidadPlanta(EntidadPlantaExt plantaPersonal) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(plantaPersonal);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_PLANTA,json,token, TIME_OUT, auditoriaSeguridad);
			EntidadPlantaExt pre = gson.fromJson(out, EntidadPlantaExt.class);
			return pre;
		}catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new EntidadPlantaExt();
		}
	}

	/**
	 * @param VinculacionExt
	 * @return Metodo inserta una registro en la base de datos de una nueva
	 *         vinculacion
	 */
	public static boolean setCargo(Cargo cargo) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(cargo);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_CARGO,json,token, TIME_OUT, auditoriaSeguridad);
			Cargo pre = gson.fromJson(out, Cargo.class);
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
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param respuestaCuestionario
	 * @return
	 */
	public static PepRespuestaCuestionario setRespuestaCuentionario(PepRespuestaCuestionario respuestaCuestionario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = gson.toJson(respuestaCuestionario);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_RESPUESTA_CUESTIONARIO,json,token, TIME_OUT, auditoriaSeguridad);
			PepRespuestaCuestionario res = gson.fromJson(out, PepRespuestaCuestionario.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new PepRespuestaCuestionario();

		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param respuestaCuestionario
	 * @return
	 */
	public static PepRespuestasPreguntaCuestionario setPepRespuestasPreguntaCuestionario(
			PepRespuestasPreguntaCuestionario respuestaCuestionario) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = gson.toJson(respuestaCuestionario);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_RESPUESTA_PREGUNTAS_CUESTIONARIO, json, token,
					TIME_OUT,auditoriaSeguridad);
			PepRespuestasPreguntaCuestionario res = gson.fromJson(out, PepRespuestasPreguntaCuestionario.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new PepRespuestasPreguntaCuestionario();

		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param detalle
	 * @return
	 */
	public static PepRespuestaCuestionarioDetalle setPepRespuestaCuestionarioDetalle(
			PepRespuestaCuestionarioDetalle detalle) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = gson.toJson(detalle);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_RESPUESTA_CUESTIONARIO_DETALLE, json, token,
					TIME_OUT,auditoriaSeguridad);
			PepRespuestaCuestionarioDetalle res = gson.fromJson(out, PepRespuestaCuestionarioDetalle.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new PepRespuestaCuestionarioDetalle();

		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param preguntaCuestionario
	 * @return
	 */
	public static List<PepPreguntasCuestionarioExt> getPreguntasCuestionarioFiltro(
			PepPreguntasCuestionario preguntaCuestionario) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(preguntaCuestionario);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_LIST_PREGUNTAS_CUESTIONARIO_FILTRO, json, token,
					TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PepPreguntasCuestionarioExt>>() {
			}.getType();
			List<PepPreguntasCuestionarioExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PepPreguntasCuestionarioExt>();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param preguntaCuestionarioDet
	 * @return
	 */
	public static List<PepPreguntasCuestionarioDetalleExt> getPreguntasCuestionarioDetFiltro(
			PepPreguntasCuestionarioDetalle preguntaCuestionarioDet) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(preguntaCuestionarioDet);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PREGUNTA_CUESTIONARIO_DETALLE, json, token,
					TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PepPreguntasCuestionarioDetalleExt>>() {
			}.getType();
			List<PepPreguntasCuestionarioDetalleExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PepPreguntasCuestionarioDetalleExt>();
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @return
	 */
	public static List<PepCuestionarioExt> getCuestionarios() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CUESTIONARIOS, "", token, TIME_OUT,auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PepCuestionarioExt>>() {
			}.getType();
			List<PepCuestionarioExt> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PepCuestionarioExt>();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static List<EntidadExt> getEntidadesUsuario(Integer codUsuario) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"codUsuario\":" + codUsuario + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PERSONA,json,token, TIME_OUT, auditoriaSeguridad);
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
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static Nomenclatura getNomenclaturaPorId(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codNomenclatura\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NOMENCLATURA_ID,json,token, TIME_OUT, auditoriaSeguridad);
			Nomenclatura list = gson.fromJson(out, Nomenclatura.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Nomenclatura();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static List<NomenclaturaExt> getTodasNomenclaturas() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_TODAS_NOMENCLATURAS,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<NomenclaturaExt>>() {
			}.getType();
			List<NomenclaturaExt> list = gson.fromJson(out, type);
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
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static boolean setNomenclatura(Nomenclatura nomenclatura) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(nomenclatura);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NOMENCLATURA,json,token, TIME_OUT, auditoriaSeguridad);
			Nomenclatura res = gson.fromJson(out, Nomenclatura.class);
			invalidToken(out);
			if (res.isError()) {
				logger.info("El servicio no inserto el dato en setNomenclatura", res.getMensaje());
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
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static NomenclaturaEntidad getNomenclaturaEntidadPorId(long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codNomenclaturaEntid\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NOMENCLATURA_ENTIDAD_ID,json,token, TIME_OUT, auditoriaSeguridad);
			NomenclaturaEntidad list = gson.fromJson(out, NomenclaturaEntidad.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new NomenclaturaEntidad();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static List<NomenclaturaEntidad> getTodasNomenclaturasEntidad() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_TODAS_NOMENCLATURAS_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<NomenclaturaEntidad>>() {
			}.getType();
			List<NomenclaturaEntidad> list = gson.fromJson(out, type);
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
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static boolean setNomenclaturaEntidad(NomenclaturaEntidad nomenclatura) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = gson.toJson(nomenclatura);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NOMENCLATURA_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
			NomenclaturaEntidad res = gson.fromJson(out, NomenclaturaEntidad.class);
			invalidToken(out);
			return res.isError();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;

		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static List<Norma> getTodasNormas() {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_TODAS_NORMAS,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Norma>>() {
			}.getType();
			List<Norma> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<Norma>();
		}
	}

	/**
	 * Metodo para mostrar los numeros de norma de un tipo de norma
	 * 
	 * @param codTipoNorma
	 * @return
	 */
	public static List<Norma> getNorma(Long codTipoNorma) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"codTipoNorma\":" + codTipoNorma + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_MOSTRAR_NORMAS,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Norma>>() {
			}.getType();
			List<Norma> list = gson.fromJson(out, type);
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
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static Norma getNormaPorId(Long id) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = "{\"codNorma\":" + id + "}";
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NORMA_ID,json,token, TIME_OUT, auditoriaSeguridad);
			Norma list = gson.fromJson(out, Norma.class);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException ex) {
			logger.error(ex.getStackTrace().toString(), ex);
			return new Norma();
		}
	}
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static Norma setNorma(Norma norma) {
		getToken();
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		try {
			String json = gson.toJson(norma);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NORMA,json,token, TIME_OUT, auditoriaSeguridad);
			Norma res = gson.fromJson(out, Norma.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new Norma();

		}
	}

	/**
	 * @param Festivo
	 * @return Metodo que consume el servicio que trae las personas activas por
	 *         entidad
	 */
	public static List<PersonaExt> getPersonasActivasEntidad(PersonaExt PersonaExt) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(PersonaExt);
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONAS_ACTIVAS_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
			invalidToken(out);
			java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
			}.getType();
			List<PersonaExt> list = gson.fromJson(out, type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<PersonaExt>();
		}
	}

	/**
	 *
	 * @param entidadPolitica
	 * @return Metodo que inserta y actualiza dependiendo de los datos enviados,
	 *         evalua el ID principal.
	 */
	public static EntidadPoliticaPublica setEntidadPoliticaPublica(EntidadPoliticaPublica entidadPolitica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(entidadPolitica);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_POLITICAS,json,token, TIME_OUT, auditoriaSeguridad);
			EntidadPoliticaPublica res = gson.fromJson(out, EntidadPoliticaPublica.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new EntidadPoliticaPublica();
		}
	}

	/**
	 *
	 * @param politica
	 * @return Metodo que inserta y actualiza dependiendo de los datos enviados,
	 *         evalua el ID principal.
	 */
	public static PoliticaPublica setPoliticaPublica(PoliticaPublica politica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(politica);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_POLITICAS,json,token, TIME_OUT, auditoriaSeguridad);
			PoliticaPublica res = gson.fromJson(out, PoliticaPublica.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new PoliticaPublica();
		}
	}

	/**
	 * @param codEntidad
	 * @return
	 */
	public static List<EntidadDTO> desactivarEntidad(Long codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"codEntidad\":\"" + codEntidad + "\"}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.DESACTIVAR_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
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
	 * Metodo para mostrar los numeros de norma de un tipo de norma
	 * 
	 * @param codEntidad
	 * @return
	 */
	public static List<Cargo> getCargosEntidad(Long codEntidad) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = "{\"codEntidad\":" + codEntidad + "}";
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGOS_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
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
	 * Metodo para mostrar los registros de la tabla POLITICA_PUBLICA
	 * 
	 * @param politica
	 * @return
	 */
	public static List<PoliticaPublica> getPoliticasPublicas(PoliticaPublica politica) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(politica);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_POLITICAS_PUBLICAS,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<PoliticaPublica>>() {
			}.getType();
			List<PoliticaPublica> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<PoliticaPublica>();
		}
	}

	/**
	 * Metodo para mostrar los registros de la tabla ENTIDAD_POLITICA_PUBLICA
	 * 
	 * @param entPol
	 *            el filtro por el que se desean listar los registros
	 * @return
	 */
	public static List<EntidadPoliticaPublica> getEntidadPoliticasPublicas(EntidadPoliticaPublica entPol) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(entPol);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_POLITICAS,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadPoliticaPublica>>() {
			}.getType();
			List<EntidadPoliticaPublica> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	 * Metodo para mostrar los registros de la tabla ENTIDAD_FUSION
	 * 
	 * @param entPol
	 *            el filtro por el que se desean listar los registros
	 * @return
	 */
	public static List<EntidadFusion> getEntidadFusion(EntidadFusion entPol) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(entPol);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_FUSION,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<EntidadFusion>>() {
			}.getType();
			List<EntidadFusion> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	/**
	 *
	 * @param politica
	 * @return Metodo que inserta y actualiza dependiendo de los datos enviados,
	 *         evalua el ID principal.
	 */
	public static EntidadFusion setEntidadFusion(EntidadFusion entidadFus) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(entidadFus);
			String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD_FUSION,json,token, TIME_OUT, auditoriaSeguridad);
			EntidadFusion res = gson.fromJson(out, EntidadFusion.class);
			invalidToken(out);
			return res;
		} catch (JsonSyntaxException e) {
			return new EntidadFusion();
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static List<Norma> getFiltroNorma(Norma norma){
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(norma);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_FILTRO_NORMA,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Norma>>() {
			}.getType();
			List<Norma> list = gson.fromJson(out, type);
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
	 * @File:   ComunicacionServiciosEnt.java
	 * @param codUsuario
	 * @return
	 */
	public static List<Norma> getFiltroNormaLike(Norma norma){
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		String json = gson.toJson(norma);
		try {
			String out = ConnectionHttp.sendPost(SerivesRestURL.GET_FILTRO_NORMA_LIKE,json,token, TIME_OUT, auditoriaSeguridad);
			java.lang.reflect.Type type = new TypeToken<List<Norma>>() {
			}.getType();
			List<Norma> list = gson.fromJson(out, type);
			invalidToken(out);
			return list;
		} catch (JsonSyntaxException e) {
			return new ArrayList<>();
		}
	}	
	
	/**
	 * Metodo para obtener los correos de las personas con rol de administrador de politicas.
	 * Este metodo se utiliza para enviar correo de confirmacion de creacion de entidad.
	 * @return  List<PersonaExt>
	 */
	public static List<PersonaExt> getPersonasPorRoles(PersonaExt roles) {
		getToken();
		gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
		try {
			String json = gson.toJson(roles);
		    String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONAS_POR_ROLES, json,token, TIME_OUT, auditoriaSeguridad );
		    java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
		    }.getType();
		    List<PersonaExt> list = gson.fromJson(out, type);
	        invalidToken(out);
	        return list;
		} catch (JsonSyntaxException e) {
		    return new ArrayList<>();
		}
    }
	
	/**
	 * Metodo que permite obtener todas las politicas publicas que tiene una entidad
	 * @param EntidadPoliticaPublicaExt
	 * @return List<EntidadPoliticaPublicaExt>
	 */
		public static List<EntidadPoliticaPublicaExt> getPoliticasPublicasEntidad(EntidadPoliticaPublicaExt entidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(entidad);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_POLITICAS_PUBLICAS_ENTIDAD, json,token, TIME_OUT,  auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<EntidadPoliticaPublicaExt>>() {
				}.getType();
				List<EntidadPoliticaPublicaExt> list = gson.fromJson(out, type);
	            invalidToken(out);
	            return list;
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<EntidadPoliticaPublicaExt>();
			}
		}
		
		/**
		 * Metodo que permite ingresar las politicas publicas
		 * @param politicaPublicaExt
		 * @return boolean
		 */
		public static boolean setNuevaPoliticaPublica(PoliticaPublicaExt politicaPublicaExt) {	
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(politicaPublicaExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_POLITICAS_PUBLICAS, json,token, TIME_OUT, auditoriaSeguridad  );
				PoliticaPublicaExt pre = gson.fromJson(out, PoliticaPublicaExt.class);
				invalidToken(out);
				if (pre.isError()) {
					logger.info("El servicio no inserto el dato en setNuevaPoliticaPublica", pre.getMensaje());
					return false;
				} else {
					return true;
				}
			} catch (JsonSyntaxException e) {
				logger.error("El servicio no inserto los datos - setNuevaPoliticaPublica(PoliticaPublicaExt politicaPublicaExt)", e);
				return false;
			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static boolean setEscalaSalarial(EscalaSalarial escala) {
			getToken();
			gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
			try {
				String json = gson.toJson(escala);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ESCALA_SALARIAL,json,token, TIME_OUT, auditoriaSeguridad);
				EscalaSalarial res = gson.fromJson(out, EscalaSalarial.class);
				invalidToken(out);
				if (res.isError()) {
					logger.info("No se pudo guardar el registro en la tabla escala salarial - public static boolean setEscalaSalarial(EscalaSalarial escala)", res.getMensajeTecnico() + " " + res.getMensaje());
					return false;
				} else
					return true;
			} catch (JsonSyntaxException ex) {
				logger.info("No se pudo guardar el registro en la tabla escala salarial - public static boolean setEscalaSalarial(EscalaSalarial escala) ComunicacionServiciosEnt", ex);
				return false;

			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<EscalaSalarial> getEscalasSalariales() {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = "";
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ESCALAS_SALARIALES,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<EscalaSalarial>>() {
				}.getType();
				List<EscalaSalarial> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<EscalaSalarial> getEscalasSalariales() ComunicacionServiciosEnt", ex);
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static EstructuraExt setEstructura(Estructura estructura) {
			EstructuraExt res;
			getToken();
			gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
			try {
				String json = gson.toJson(estructura);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ESTRUCTURA_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				res = gson.fromJson(out, EstructuraExt.class);
				invalidToken(out);
				if (res.isError()) {
					logger.info("No se pudo guardar el registro en la tabla Estructura - public static boolean setEscalaSalarial(estructura)", res.getMensajeTecnico() + " " + res.getMensaje());
					return res;
				} 
				else
					return res;
			} catch (JsonSyntaxException ex) {
				logger.info("No se pudo guardar el registro en la tabla Estructura - public static boolean setEscalaSalarial(estructura) ComunicacionServiciosEnt", ex);
				res = new EstructuraExt();
				res.setError(true);
				return res;

			}
		}
		
		/**
		 * Obtiene una lista con dependencias.
		 * @param estructura
		 * @return <code>List<DependenciaEntidadExt></code>
		 */
		public static List<DependenciaEntidadExt> obtenerDependenciasEstructura(DependenciaEntidadExt estructura) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			List<DependenciaEntidadExt> lista;
			try {
				String json = gson.toJson(estructura);
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_DEPENDENCIA_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<DependenciaEntidadExt>>() {
				}.getType();
				lista = gson.fromJson(out, type);
				invalidToken(out);
				return lista;
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static DependenciaEntidadExt obtenerDependenciaEstructuraCodPadre(DependenciaEntidadExt estructuraEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			DependenciaEntidadExt result;
			try {
				String json = gson.toJson(estructuraEntidad);
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_DEPENDENCIA_ENTIDAD_PADRE,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<DependenciaEntidadExt>() {
				}.getType();
				result = gson.fromJson(out, type);
				invalidToken(out);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return new DependenciaEntidadExt();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static DependenciaEntidadExt obtenerDependenciaEstructuraJerarquia(DependenciaEntidadExt estructuraEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			DependenciaEntidadExt result;
			try {
				String json = gson.toJson(estructuraEntidad);
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_DEPENDENCIA_ENTIDAD_JERARQUIA,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<DependenciaEntidadExt>() {
				}.getType();
				result = gson.fromJson(out, type);
				invalidToken(out);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return new DependenciaEntidadExt();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<DependenciaEntidadExt> obtenerDependenciaEstructuraHijos(DependenciaEntidadExt estructuraEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			List<DependenciaEntidadExt> result;
			try {
				String json = gson.toJson(estructuraEntidad);
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_DEPENDENCIA_ENTIDAD_HIJO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<DependenciaEntidadExt>>() {
				}.getType();
				result = gson.fromJson(out, type);
				invalidToken(out);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static DependenciaEntidadExt obtenerDependenciaPadreByCodPredecesor(DependenciaEntidadExt dependenciaEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			DependenciaEntidadExt result;
			try {
				String json = gson.toJson(dependenciaEntidad);
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_DEPENDENCIA_PADRE_COD_PREDECESOR,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<DependenciaEntidadExt>() {
				}.getType();
				result = gson.fromJson(out, type);
				invalidToken(out);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return new DependenciaEntidadExt();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<NomenclaturaExt> getNomenclaturaFiltro(NomenclaturaExt nomenclatura){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(nomenclatura);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NOMENCLATURA_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaExt>>(){}.getType();
				List<NomenclaturaExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<EscalaSalarial> getEscalasSalariales() ComunicacionServiciosEnt", ex);
				return new ArrayList<>();
			}
			
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static DependenciaEntidad setDependenciaEntidad(DependenciaEntidad dependencia) {
			getToken();
			DependenciaEntidad res;
			gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
			try {
				String json = gson.toJson(dependencia);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DEPENDENCIA_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				res = gson.fromJson(out, DependenciaEntidad.class);
				invalidToken(out);
				if (res.isError()) {
					logger.info("No se pudo guardar el registro en la tabla Estructura - public static boolean setEscalaSalarial(estructura)", res.getMensajeTecnico() + " " + res.getMensaje());
					return res;
				} else
					return res;
			} catch (JsonSyntaxException ex) {
				logger.info("No se pudo guardar el registro en la tabla Estructura - public static boolean setEscalaSalarial(estructura) ComunicacionServiciosEnt", ex);
				res = new DependenciaEntidad();
				res.setError(true);
				return res;

			}
		}	
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static EstructuraExt getEstructuraByEntidad(BigDecimal codEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = "{\"codEntidad\":\"" + codEntidad + "\"}";
			EstructuraExt result;
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ESTRUCTURABYENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<EstructuraExt>() {}.getType();
				result = gson.fromJson(out, type);
				invalidToken(out);
				return result;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<EscalaSalarial> getEstructuraByEntidad() ComunicacionServiciosEnt", ex);
				return null;
			}
		}		
		
		
		
		/**
		 * @autor Maria Alejandra Colorado Rios
		 * Metodo que trae todas las asignaciones salariales
		 * filtra por cualquier valor de la tabla ASIGNACION_SALARIAL
		 * @param filtBusqueda
		 * @return List<AsignacionSalarialExt>
		 */
		public static List<AsignacionSalarialExt> getASignacionSalarialFitro(AsignacionSalarialExt filtBusqueda) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(filtBusqueda);
			    String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ASIGNACION_SALARIAL_FILTRO, json,token, TIME_OUT, auditoriaSeguridad );
			    java.lang.reflect.Type type = new TypeToken<List<AsignacionSalarialExt>>() {
			    }.getType();
			    List<AsignacionSalarialExt> list = gson.fromJson(out, type);
		        invalidToken(out);
		        return list;
			} catch (JsonSyntaxException e) {
			    logger.info("El servicio no encontro datos - getASignacionSalarialFitro(AsignacionSalarialExt filtBusqueda)", e);
			    return new ArrayList<>();
			}
	    }
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static AsignacionSalarial setAsignacionSalarial(AsignacionSalarial asignacion) {
			getToken();
			AsignacionSalarial res;
			gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
			try {
				String json = gson.toJson(asignacion);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ASIGNACION_SALARIAL,json,token, TIME_OUT, auditoriaSeguridad);
				res = gson.fromJson(out, AsignacionSalarial.class);
				invalidToken(out);
				if (res.isError())
					logger.info("No se pudo guardar el registro en la tabla Estructura - public static boolean setEscalaSalarial(estructura)", res.getMensajeTecnico() + " " + res.getMensaje());
				return res;
			} catch (JsonSyntaxException ex) {
				logger.info("No se pudo guardar el registro en la tabla asignacion_salarial - public static AsignacionSalarial setAsignacionSalarial(AsignacionSalarial asignacion) ComunicacionServiciosEnt", ex);
				res = new AsignacionSalarial();
				res.setError(true);
				return res;

			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<GrupoDependenciaExt> getTodosGrupoDependencias() {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = "";
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_TODOS_GRUPOS_DEPENDENCIA,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<GrupoDependenciaExt>>() {
				}.getType();
				List<GrupoDependenciaExt> list = gson.fromJson(out, type);
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
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<GrupoDependenciaExt> getGrupoDependenciasFiltroEntidad(GrupoDependenciaExt grupo) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(grupo);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_GRUPO_DEPENDENCIA_FILTRO_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<GrupoDependenciaExt>>() {
				}.getType();
				List<GrupoDependenciaExt> list = gson.fromJson(out, type);
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
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static GrupoDependenciaExt getGrupoDependenciaPorId(long id) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = "{\"codGrupoDependencia\":" + id + "}";
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_GRUPO_DEPENDENCIA_POR_ID,json,token, TIME_OUT, auditoriaSeguridad);
				GrupoDependenciaExt list = gson.fromJson(out, GrupoDependenciaExt.class);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new GrupoDependenciaExt();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static boolean setGrupoDependencia(GrupoDependenciaExt grupo) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(grupo);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_GRUPO_DEPENDENCIA,json,token, TIME_OUT, auditoriaSeguridad);
				GrupoDependenciaExt pre = gson.fromJson(out, GrupoDependenciaExt.class);
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
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<NomenclaturaEntidad> getNomenculaturaEntidadFiltro(NomenclaturaEntidad nomenclaturaEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(nomenclaturaEntidad);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NOMENCLATURA_ENTIDAD_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaEntidad>>() {
				}.getType();
				List<NomenclaturaEntidad> list = gson.fromJson(out, type);
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
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<Parametrica> getDenomincacionEntidad(BigDecimal codEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = "{\"codEntidad\":" + codEntidad + "}";
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DENOMINACION_BY_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<Parametrica>>() {
				}.getType();
				List<Parametrica> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}		
		
		/**
		 * @author Maria Alejandra Colorado Rios
		 * @param EntidadPlantaDetalleExt
		 * @return
		 * Metodo que obtiene los datos a setear en la tabla EntidadPlantaDetalle.
		 * Este tiene como fin, realizar un eliminado logico de todas los cargos que tiene una planta de personal
		 */
		public static boolean setEliminacionAutomaticaCargos(EntidadPlantaDetalleExt EntidadPlantaDetalleExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(EntidadPlantaDetalleExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ELIMINACION_CARGOS_AUTOMATICA, json,token, TIME_OUT, auditoriaSeguridad );
				EntidadPlantaDetalleExt pre = gson.fromJson(out, EntidadPlantaDetalleExt.class);
				if (pre.isError()) {
					logger.info("El servicio no inserto los datos", pre.getMensaje());
					return false;
				} else {
					return true;
				}
			} catch (JsonSyntaxException e) {
				logger.error("El servicio no inserto los datos - setDesvinculacionAutomaticaPorPlanta(VinculacionExt vinculacionExt)", e);
				return false;
			}
		}
		
		/**
		 * @author Maria Alejandra Colorado
		 * @param personaExt
		 * @return List<PersonaExt>
		 *  Este Servicio recibe un objeeto PersonaExt con las siguientes entradas:
		 *         cod_entidad cod_rol flg_activo flg_estado, ordenTerritorial, ordenNacional
		 *  Metodo utilizado para el componente de asignar responsable entidad.
		 * Trae una consulta con las personas con rol gestionEntidad (Responsables) y 
		 * sus el total de asociaciones que tiene para las entidades en general, por 
		 * orden territorial y por orrden nacional
		 */
		public static List<PersonaExt> getPersonaResponsableEntidad(Persona persona) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(persona);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONA_RESPONSABLE_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
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
		 * @author Maria Alejandra Colorado Ríos
		 * 23/11/2018
		 * @param EntidadExt
		 * @return
		 */
		public static List<EntidadExt> getEntidadesPersonaFiltro(EntidadExt entidadExt) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(entidadExt);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDADES_PERSONA_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
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
		 * 
		 * @param entidadPoliticaPublica
		 * @return
		 */
		public static List<EntidadPoliticaPublica> getEntidadPoliticaPublicaFiltro(EntidadPoliticaPublica entidadPoliticaPublica) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(entidadPoliticaPublica);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDADES_POLITICAS_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<EntidadPoliticaPublica>>() {
				}.getType();
				List<EntidadPoliticaPublica> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * @param codEntidadPoliticaPublica
		 * @return
		 */
		public static EntidadPoliticaPublica delEntidadPoliticaPublica(Long codEntidadPoliticaPublica) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = "{\"codEntidadPoliticaPublica\":"+codEntidadPoliticaPublica+"}";
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.DEL_ENTIDADES_POLITICAS,json,token, TIME_OUT, auditoriaSeguridad);
				EntidadPoliticaPublica list = gson.fromJson(out, EntidadPoliticaPublica.class);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new EntidadPoliticaPublica();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static Entidad setEntidadExt(Entidad entidad, Long codTipoVinculacion, Long codUsuario, Long codRol ) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			if(entidad.getJustificacion() != null)
				entidad.setJustificacion(entidad.getJustificacion().trim());
			if(entidad.getNit() != null)
				entidad.setNit(entidad.getNit().trim());
			if(entidad.getEmailEntidad() != null)
				entidad.setEmailEntidad(entidad.getEmailEntidad().trim());
			if(entidad.getPaginaWeb() != null )
				entidad.setPaginaWeb(entidad.getPaginaWeb().trim());
			if(entidad.getDireccion() != null)
				entidad.setDireccion(entidad.getDireccion().trim());
			if(entidad.getTelefonoEntidad() != null)
				entidad.setTelefonoEntidad(entidad.getTelefonoEntidad().trim());
			if(entidad.getFax() != null)
				entidad.setFax(entidad.getFax().trim());
			if(entidad.getCodRepresentanteLegal() != null)
				entidad.setCodRepresentanteLegal(entidad.getCodRepresentanteLegal().trim());
			try {
				
				String entidadExt = gson.toJson(entidad);
				EntidadExt entidExt = gson.fromJson(entidadExt, EntidadExt.class);
				entidExt.setCodTipoVinculacion(codTipoVinculacion);
				entidExt.setCodUsuario(codUsuario);
				entidExt.setCodRol(codRol);
				String json = gson.toJson(entidExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				Entidad res = gson.fromJson(out, Entidad.class);
				invalidToken(out);
				return res;
			} catch (JsonSyntaxException e) {
				return new Entidad();

			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static UsuarioEntidad setUsuarioEntidad(UsuarioEntidad usuarioEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				
				String usuarioEntidadJson = gson.toJson(usuarioEntidad);
				UsuarioEntidad uEntidad = gson.fromJson(usuarioEntidadJson, UsuarioEntidad.class);
				String json = gson.toJson(uEntidad);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_USUARIO_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				UsuarioEntidad res = gson.fromJson(out, UsuarioEntidad.class);
				invalidToken(out);
				return res;
			} catch (JsonSyntaxException e) {
				return new UsuarioEntidad();

			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static IncrementoSalarial guardarIncremento(IncrementoSalarial incremento) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String jsonIncre = gson.toJson(incremento);
				IncrementoSalarial incrementoSalarial = gson.fromJson(jsonIncre, IncrementoSalarial.class);
				String json = gson.toJson(incrementoSalarial);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GUARDAR_INCREMENTO_SALARIAL,json,token, TIME_OUT, auditoriaSeguridad);
				IncrementoSalarial res = gson.fromJson(out, IncrementoSalarial.class);
				invalidToken(out);
				return res;
			} catch (JsonSyntaxException e) {
				return null;

			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static Nomenclatura setNomenclaturaRespuesta(Nomenclatura nomenclatura) {
			Nomenclatura res;
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclatura);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NOMENCLATURA,json,token, TIME_OUT, auditoriaSeguridad);
				res = gson.fromJson(out, Nomenclatura.class);
				invalidToken(out);
				if (res.isError()) {
					logger.info("El servicio no inserto el dato en setNomenclatura", res.getMensaje());
					return res;
				} else {
					return res;
				}
			} catch (JsonSyntaxException e) {
				res = new Nomenclatura();
				res.setMensajeTecnico(e.getMessage());
				return res;
			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<Denominacion> getDenomincacionesFiltro(Denominacion denomincacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(denomincacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DENOMINACIONES_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
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
		 * 
		 * Elaborado por:
		 * Jose Viscaya Dec 19, 2018
		 * @param codDenomincacion
		 * @return
		 */
		public static Denominacion getDenomincacionId(BigDecimal codDenomincacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = "{\"codDenominacion\":"+codDenomincacion+"}";
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DENOMINACION_ID,json,token, TIME_OUT, auditoriaSeguridad);
				Denominacion res = gson.fromJson(out, Denominacion.class);
				invalidToken(out);
				return res;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new Denominacion();
			}
		}	
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static NomenclaturaDenominacion setNomenclaturaDenominacion(NomenclaturaDenominacion nomenclaturaDenomincacion) {
			NomenclaturaDenominacion res;
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclaturaDenomincacion);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NOMENCLATURA_DENOMINACION,json,token, TIME_OUT, auditoriaSeguridad);
				res = gson.fromJson(out, NomenclaturaDenominacion.class);
				invalidToken(out);
				if (res.isError()) {
					logger.info("El servicio no inserto el dato en setNomenclatura", res.getMensaje());
					return res;
				} else {
					return res;
				}
			} catch (JsonSyntaxException e) {
				res = new NomenclaturaDenominacion();
				res.setMensajeTecnico(e.getMessage());
				return res;
			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<NomenclaturaDenominacion> getDenomincacionesNomenclaturaFiltro(NomenclaturaDenominacion nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NOMENCLATURA_DENOMINACIONES_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacion>>() {
				}.getType();
				List<NomenclaturaDenominacion> list = gson.fromJson(out, type);
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
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<NomenclaturaDenominacionExt> getDenomincacionesNomenclaturaExtFiltro(NomenclaturaDenominacion nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NOMENCLATURA_DENOMINACIONES_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacionExt>>() {
				}.getType();
				List<NomenclaturaDenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}			
		
		/**
		 * 
		 * @param entidadPoliticaPublica
		 * @return
		 */
		public static List<NomenclaturaDenominacion> obtenerNomenclaturaDenominacion(NomenclaturaDenominacion nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_NOMENCLATURA_DENOMINACION_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacion>>() {
				}.getType();
				List<NomenclaturaDenominacion> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static PlantaPersonaUtlUan guardarPlantaPersonaUtl(PlantaPersonaUtlUanExt plantaUtl) {
			PlantaPersonaUtlUan res;
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(plantaUtl);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GUARDAR_PLANTA_PERSONA_UTL,json,token, TIME_OUT, auditoriaSeguridad);
				res = gson.fromJson(out, PlantaPersonaUtlUan.class);
				invalidToken(out);
				if (res.isError()) {
					logger.info("El servicio no inserto el dato en setplantapersonautluan", res.getMensaje());
					return res;
				} else {
					return res;
				}
			} catch (JsonSyntaxException e) {
				res = new PlantaPersonaUtlUan();
				res.setMensajeTecnico(e.getMessage());
				return res;
			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<NomenclaturaExt> getNomenclaturaBase(NomenclaturaExt nomenclaturaExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				 if(nomenclaturaExt.getNombreEntidad().equals("")) {
					 nomenclaturaExt.setNombreEntidad(null);
				 }
				 if(nomenclaturaExt.getCodSigep().equals("")) {
					nomenclaturaExt.setCodSigep(null); 
				 }
				String json = gson.toJson(nomenclaturaExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NOMENCLATURA_ENTIDAD_BASE, json,token, TIME_OUT, auditoriaSeguridad );
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaExt>>() {
				}.getType();
				List<NomenclaturaExt> nom = gson.fromJson(out, type);
	            invalidToken(out);
	            return nom;
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<PersonaExt> obtenerDatosVinculacion(PersonaExt persona){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(persona);
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_DATOS_VINCULACION, json,token, TIME_OUT, auditoriaSeguridad  );
				java.lang.reflect.Type type = new TypeToken<List<PersonaExt>>() {
				}.getType();
				List<PersonaExt> nom = gson.fromJson(out, type);
	            invalidToken(out);
	            return nom;
			} catch (Exception ex) {
				logger.error("Error public List<PersonaExt> obtenerDatosVinculacion() ", ex);
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * Elaborado por:
		 * Jose Viscaya 4 ene. 2019
		 * @param autonomia
		 * @return
		 */
		public static Autonomia setAutonomia(Autonomia autonomia){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(autonomia);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_AUTONIMIA_ENTIDAD, json,token, TIME_OUT, auditoriaSeguridad);
				Autonomia nom = gson.fromJson(out, Autonomia.class);
	            invalidToken(out);
	            return nom;
			} catch (Exception ex) {
				logger.error("Error public Autonomia setAutonomia() ", ex);
				return new Autonomia();
			}
		}
		/**
		 * 
		 * Elaborado por:
		 * Jose Viscaya 4 ene. 2019
		 * @param autonomia
		 * @return
		 */
		public static List<AutonomiaExt> getAutonomia(Autonomia autonomia){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(autonomia);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_AUTONIMIA_ENTIDAD, json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<AutonomiaExt>>() {
				}.getType();
				List<AutonomiaExt> nom = gson.fromJson(out, type);
	            invalidToken(out);
	            return nom;
			} catch (Exception ex) {
				logger.error("Error public List<AutonomiaExt> getAutonomia() ", ex);
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * Elaborado por:
		 * Jose Viscaya 9 ene. 2019
		 * @param usuarioRolDependencia
		 * @return
		 */
		public static List<UsuarioRolDependencia> getUsuarioRolDependencia(UsuarioRolDependencia usuarioRolDependencia){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(usuarioRolDependencia);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_ROL_DEPENDENCIA, json,token, TIME_OUT, auditoriaSeguridad  );
				java.lang.reflect.Type type = new TypeToken<List<UsuarioRolDependencia>>() {
				}.getType();
				List<UsuarioRolDependencia> nom = gson.fromJson(out, type);
	            invalidToken(out);
	            return nom;
			} catch (Exception ex) {
				logger.error("Error public List<AutonomiaExt> getAutonomia() ", ex);
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * Elaborado por:
		 * Jose Viscaya 9 ene. 2019
		 * @param usuarioRolDependencia
		 * @return
		 */
		public static UsuarioRolDependencia setUsuarioRolDependencia(UsuarioRolDependencia usuarioRolDependencia){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(usuarioRolDependencia);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_USUARIO_ROL_DEPENDENCIA, json,token, TIME_OUT, auditoriaSeguridad);
				UsuarioRolDependencia usDep = gson.fromJson(out, UsuarioRolDependencia.class);
	            invalidToken(out);
	            return usDep;
			} catch (Exception ex) {
				logger.error("Error public List<AutonomiaExt> getAutonomia() ", ex);
				return new UsuarioRolDependencia();
			}
		}
		/**
		 * 
		 * Elaborado por:
		 * Jose Viscaya 10 ene. 2019
		 * @param codUsuarioRolDependencia
		 * @return
		 */
		public static UsuarioRolDependencia getUsuarioRolDependenciaId(long codUsuarioRolDependencia){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = "{\"codUsuarioRolDependencia\":"+codUsuarioRolDependencia+"}";
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_ROL_DEPENDENCIA_ID, json,token, TIME_OUT,  auditoriaSeguridad);
				UsuarioRolDependencia usDep = gson.fromJson(out, UsuarioRolDependencia.class);
				invalidToken(out);
				return usDep;
			} catch (Exception ex) {
				logger.error("Error public UsuarioRolDependencia getUsuarioRolDependenciaId() ", ex);
				return new UsuarioRolDependencia();
			}
		}
		/**
		 * 
		 * Elaborado por:
		 * Jose Viscaya 9 ene. 2019
		 * @param codUsuario
		 * @param codRol
		 * @return
		 */
		public static UsuarioRolEntidad getUsuarioRolEntidadByRol(long codUsuario, long codRol){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = "{\"codUsuario\":"+codUsuario+",\"codRol\":"+codRol+"}";
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_USUARIO_ROL_ENTIDAD_BY_USROL, json,token, TIME_OUT,  auditoriaSeguridad);
				UsuarioRolEntidad nom = gson.fromJson(out, UsuarioRolEntidad.class);
	            invalidToken(out);
	            return nom;
			} catch (Exception ex) {
				logger.error("Error public UsuarioRolEntidad getUsuarioRolEntidadByRol() ", ex);
				return new UsuarioRolEntidad();
			}
		}
		

		/**
		 * @author Maria Alejandra Colorado Rios
		 * @param EntidadPlantaDetalleExt
		 * @return
		 * Metodo que cambia la denominacion de una planta, por otra ya existente.
		 * Se realiza para el componente de Equivalencia de nomenclatura.
		 */
		public static boolean setDenominacionDestino(EntidadPlantaDetalleExt EntidadPlantaDetalleExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(EntidadPlantaDetalleExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DENOMINACION_DESTINO, json,token, TIME_OUT,  auditoriaSeguridad);
				EntidadPlantaDetalleExt pre = gson.fromJson(out, EntidadPlantaDetalleExt.class);
				if (pre.isError()) {
					logger.info("El servicio no inserto los datos", pre.getMensaje());
					return false;
				} 
				else
					return true;
			} catch (JsonSyntaxException e) {
				logger.error("El servicio no inserto los datos - setDenominacionDestino(EntidadPlantaDetalleExt EntidadPlantaDetalleExt)", e);
				return false;
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<EstructuraExt> obtenerEstructuraFiltro(EstructuraExt estruc){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(estruc);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ESTRUCTURABYFILER, json,token, TIME_OUT, auditoriaSeguridad );
				java.lang.reflect.Type type = new TypeToken<List<EstructuraExt>>(){}.getType();
				List<EstructuraExt> estru = gson.fromJson(out, type);
	            invalidToken(out);
	            return estru;
			} catch (Exception ex) {
				logger.error("Error public List<PersonaExt> obtenerDatosVinculacion() ", ex);
				return new ArrayList<>();
			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static EntidadPlantaDetalleExt getTotalCargosDirectivos(EntidadPlantaDetalleExt entidadEscision) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(entidadEscision);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_TOTAL_CARGOS_DIRECTIVOS_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<EntidadPlantaDetalleExt>() {
				}.getType();
				EntidadPlantaDetalleExt list = gson.fromJson(out, type);
				invalidToken(out);
				return list;

			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new EntidadPlantaDetalleExt();
			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<PlantaPersonaUtlUanExt> obtenerPlantaPersonaUTLFiltro(PlantaPersonaUtlUanExt planta) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(planta);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_PLANTA_UTL,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<PlantaPersonaUtlUanExt>>(){}.getType();
				List<PlantaPersonaUtlUanExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;

			} catch (JsonSyntaxException ex) {
				logger.error("Error en public static List<PlantaPersonaUtlUan> getPlantaPersonaUTLFiltro(PlantaPersonaUtlUan planta) ComunicacionServiciosEnt " + ex.getMessage(), ex);
				return new ArrayList<>();
			}
		}
		/**
		 * 
		 * Elaborado por:
		 * Jose Viscaya Jan 22, 2019
		 * @param planta
		 * @return
		 */
		public static Entidad getEntidadBogota(int codEntidad) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = "{\"codEntidad\":"+codEntidad+"}";
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_BOGOTA,json,token, TIME_OUT, auditoriaSeguridad);
				Entidad list = gson.fromJson(out, Entidad.class);
				invalidToken(out);
				return list;

			} catch (JsonSyntaxException ex) {
				logger.error("Error en public static Entidad getEntidadBogota ComunicacionServiciosEnt " + ex.getMessage(), ex);
				return new Entidad();
			}
		}
		
		public static List<Entidad> getEntidadesBogotaUsuario(int codUsuario) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = "{\"codUsuario\":"+codUsuario+"}";
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDADES_BOGOTA_USUARIO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<Entidad>>(){}.getType();
				List<Entidad> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;

			} catch (JsonSyntaxException ex) {
				logger.error("Error en public static Entidad getEntidadBogota ComunicacionServiciosEnt " + ex.getMessage(), ex);
				return new ArrayList<>();
			}
		}		
		
		/**
		 * @param Nomencalruta Ext
		 * @author Maria Alejandra colorado
		 */
		public static NomenclaturaExt setNomenclaturasHeredadas(NomenclaturaExt nomenclaturaExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclaturaExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NOMENCLATURA_HEREDADA,json,token, TIME_OUT, auditoriaSeguridad);
				NomenclaturaExt per = gson.fromJson(out, NomenclaturaExt.class);
				return per;
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new NomenclaturaExt();
			}
		}
		/**
		 * @param Nomencalruta Ext
		 * @author Maria Alejandra colorado
		 */
		public static NomenclaturaExt setNomenclaturaEquivalencia(NomenclaturaExt nomenclaturaExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclaturaExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NOMENCLATURA_EQUIVALENCIA,json,token, TIME_OUT, auditoriaSeguridad);
				NomenclaturaExt per = gson.fromJson(out, NomenclaturaExt.class);
				return per;
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new NomenclaturaExt();
			}
		}
		
		/**
		 * Metodo para retornar una lista de datos de EntidadPlantaDetalle 
		 * @param EntidadPlantaDetalleExt
		 * @return List<EntidadPlantaDetalleExt>
		 */
	    public static List<NomenclaturaDenominacionExt> getCargoAprobarHojaVida(NomenclaturaDenominacionExt NomenclaturaDenominacionExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(NomenclaturaDenominacionExt);
			    String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARGO_APROBAR_HV, json,token, TIME_OUT, auditoriaSeguridad );
			    java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacionExt>>() {
			    }.getType();
			    List<NomenclaturaDenominacionExt> list = gson.fromJson(out, type);
		        invalidToken(out);
		        return list;
			} catch (JsonSyntaxException e) {
			    logger.info("El servicio no encontro datos - getCargoPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalleFilter)", e);
			    return new ArrayList<>();
			}
	    }
	    
	    /**
	     * 
	     * Elaborado Por: Jose Viscaya
	     * 11 feb. 2019
	     * ComunicacionServiciosEnt.java
	     * @param entidadExt
	     * @return
	     */
	    public static List<EntidadExt> getEntidadesPersonaRol(EntidadExt entidadExt) {
	    	getToken();
	    	gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
	    	try {
	    		String json = gson.toJson(entidadExt);
	    		String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDAD_PERSONA_ROL_LIST, json,token, TIME_OUT,  auditoriaSeguridad);
	    		java.lang.reflect.Type type = new TypeToken<List<EntidadExt>>() {
	    		}.getType();
	    		List<EntidadExt> list = gson.fromJson(out, type);
	    		invalidToken(out);
	    		return list;
	    	} catch (JsonSyntaxException e) {
	    		logger.info("El servicio no encontro datos - getEntidadesPersonaRol(EntidadExt entidadExt)", e);
	    		return new ArrayList<>();
	    	}
	    }
	    
	    /**
		 * @param Nomencalruta Ext
		 * @author Maria Alejandra colorado
		 */
		public static NomenclaturaDenominacionExt getCaracteristicasDenominacion(NomenclaturaDenominacionExt nomenclaturaDenominacionExt) {
			getToken();
			NomenclaturaDenominacionExt per;
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclaturaDenominacionExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CARACTERISTICAS_DENOMINACION,json,token, TIME_OUT, auditoriaSeguridad);
				 per = gson.fromJson(out, NomenclaturaDenominacionExt.class);
				 return per;
			} catch (JsonSyntaxException e) {
				per = new NomenclaturaDenominacionExt();
				per.setMensajeTecnico(e.getMessage());
				return per;				
			}
		}
		
		/**
	     * Metodo retorna las plantas asociadas a una nomenclatura general
	     * @param EntidadPlanta
	     * @return List<EntidadPlantaExt>
	     */
		public static List<NomenclaturaExt> getPlantaAsociadaNomGeneral(NomenclaturaExt nomenclaturaExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclaturaExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PLANTA_ASOCIADA_NOM_GENERAL, json,token, TIME_OUT,  auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaExt>>() {
				}.getType();
				List<NomenclaturaExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				logger.info("El servicio no encontro datos -  getPlantaAsociadaNomGeneral(EntidadPlantaExt EntidadPlanta)", e);
			    return new ArrayList<>();
			}
		}
		
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static PepCuestionario getCuestionarioById(PepCuestionario pepCuestionario) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(pepCuestionario);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PEP_CUESTIONARIO_BY_ID,json,token, TIME_OUT, auditoriaSeguridad);
				PepCuestionario list = gson.fromJson(out, PepCuestionario.class);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new PepCuestionario();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<Parametrica> getPoliticasParametrica(Parametrica parametrica) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(parametrica);
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PARAMETRO_POR_ID_PADRE, json,token, TIME_OUT,  auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<Parametrica>>() {
				}.getType();
				List<Parametrica> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				logger.info("El servicio no encontro datos -  getPoliticasParametrica(Parametrica Parametrica)", e);
			    return new ArrayList<>();
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @File:   ComunicacionServiciosEnt.java
		 * @param codUsuario
		 * @return
		 */
		public static List<EntidadSistemaRegimen> getEntidadesSistemaRegimenPorId(EntidadSistemaRegimen entSis) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = "";
			try {
				json = gson.toJson(entSis);
				String out = ConnectionHttp.sendPost(SerivesRestURL.OBTENER_ENTIDAD_SISTEMA_REGIMEN_FILTRO,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<EntidadSistemaRegimen>>() {}.getType();
				List<EntidadSistemaRegimen> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				ComunicacionServiciosEnt.logger.error("public static List<EntidadSistemaRegimen> getEntidadesSistemaRegimenPorId(EntidadSistemaRegimen entSis) ComunicacionServiciosEnt.java, error: " + ex.getMessage(), ex);
				return new ArrayList<>();
			}
		}
		
		/**
		 * @author Maria Alejandra C
		 * @param entidadPlantaDetalle
		 * @return List<EntidadPlantaDetalleExt>
		 * Metodo que trae las vinculaciones y plantas pertenecientes a una denominacion especifica
		 */
		public static List<EntidadPlantaDetalleExt> getVinculacionPlanta(EntidadPlantaDetalleExt entidadPlantaDetalle) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(entidadPlantaDetalle);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_PLANTA,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
				}.getType();
				List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<EntidadPlantaDetalleExt> getVinculacionPlanta(EntidadPlantaDetalleExt entidadPlantaDetalle) ComuniacionServiciosEnt.java error: " + ex.getMessage() , ex);;
				return new ArrayList<>();
			}
		}
		
		/**
		 * @author Maria Alejandra C
		 * @param entidadPlantaDetalle
		 * @return List<EntidadPlantaDetalleExt>
		 * Metodo que trae las vinculaciones asociadas a una denominacion general
		 */
		public static List<EntidadPlantaDetalleExt> getVinculacionDenominacionGeneral(EntidadPlantaDetalleExt entidadPlantaDetalle) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(entidadPlantaDetalle);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_VINCULACION_DENOMINACION_GENERAL,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
				}.getType();
				List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<EntidadPlantaDetalleExt>  getVinculacionDenominacionGeneral(EntidadPlantaDetalleExt entidadPlantaDetalle) ComuniacionServiciosEnt.java error: " + ex.getMessage() , ex);;
				return new ArrayList<>();
			}
		}
		
		/**
		 * @author Maria Alejandra C
		 * @param entidadPlantaDetalle
		 * @return List<EntidadPlantaDetalleExt>
		 * Metodo que trae las PLANTAS asociadas a una denominacion general
		 */
		public static List<EntidadPlantaDetalleExt> getPlantaDenominacionGeneral(EntidadPlantaDetalleExt entidadPlantaDetalle) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(entidadPlantaDetalle);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PLANTA_DENOMINACION_GENERAL,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<EntidadPlantaDetalleExt>>() {
				}.getType();
				List<EntidadPlantaDetalleExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<EntidadPlantaDetalleExt>   getPlantaDenominacionGeneral(EntidadPlantaDetalleExt entidadPlantaDetalle) ComuniacionServiciosEnt.java error: " + ex.getMessage() , ex);;
				return new ArrayList<>();
			}
		}
		
		/**
		 * Update a denominaciones asociadas
		 * @param nomenclaturaDenomincacion
		 * @return
		 */
		public static NomenclaturaDenominacion setNomenclaturaDenominacionAsociadas(NomenclaturaDenominacion nomenclaturaDenomincacion) {
			NomenclaturaDenominacion res;
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclaturaDenomincacion);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NOMENCLATURA_DENOMINACION_ASOCIADA,json,token, TIME_OUT, auditoriaSeguridad);
				res = gson.fromJson(out, NomenclaturaDenominacion.class);
				invalidToken(out);
				if (res.isError()) {
					logger.info("El servicio no inserto el dato en setNomenclaturaDenominacionAsociadas", res.getMensaje());
					return res;
				} else {
					return res;
				}
			} catch (JsonSyntaxException e) {
				res = new NomenclaturaDenominacion();
				res.setMensajeTecnico(e.getMessage());
				return res;
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @Date:   30 abr. 2019, 10:59:48
		 * @File:   ComunicacionServiciosEnt.java
		 * @param denominacion
		 * @return
		 */
		public static Denominacion setDenominacion(Denominacion denominacion){
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(denominacion);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_DENOMINACION, json,token, TIME_OUT, auditoriaSeguridad );
				Denominacion nom = gson.fromJson(out, Denominacion.class);
	            invalidToken(out);
	            return nom;
			} catch (Exception ex) {
				logger.error("Error public Denominacion setDenominacion() ", ex);
				return new Denominacion();
			}
		}
		
		/**
		 * Realiza proceso de equivalencias para nomenclatura general
		 * @param Nomencalruta Ext
		 * @author Maria Alejandra colorado
		 */
		public static NomenclaturaExt setNomenclaturaEquivalenciaGeneral(NomenclaturaExt nomenclaturaExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclaturaExt);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_NOMENCLATURA_EQUIVALENCIA_GENERAL,json,token, TIME_OUT, auditoriaSeguridad);
				NomenclaturaExt per = gson.fromJson(out, NomenclaturaExt.class);
				return per;
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new NomenclaturaExt();
			}
		}
		/**
		 *  Methoso que implementa el servicios de traer Entdades por codigo sigep
		 *
		 * @return List<Entidad>
		 */
		public static List<Entidad> getEntidadesByCodigoSigep(Entidad entidad) {
			getToken();
			if(entidad.getLimitEnd() != null) {
				if (entidad.getLimitEnd() < 1) {
					entidad.setLimitEnd(500);
				}
			}else {
				entidad.setLimitEnd(100);
				entidad.setLimitInit(0);
			}
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(entidad);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_ENTIDADES_BY_CODIGO_SIGEP,json,token, TIME_OUT, auditoriaSeguridad);
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
		 * @author Maria Alejandra C
		 * @param denominacioonExt
		 * @return List<DenominacionExt>
		 * Metodo que trae las denominaciones asociada a una nomenclatura y nivel
		 */
		public static List<DenominacionExt> getDenominacionPorNivelNomenclatura(DenominacionExt denominacionExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(denominacionExt);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DENOMINACION_BY_NIVEL_NOMENCLATURA,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<DenominacionExt>>() {
				}.getType();
				List<DenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<DenominacionExt>   getDenominacionPorNivelNomenclatura(DenominacionExt DenominacionExt) ComuniacionServiciosEnt.java error: " + ex.getMessage() , ex);;
				return new ArrayList<>();
			}
		}
		
		/**
		 * Metodo que trae los niveles jerarquicos asociados a una nomenclatura 
		 * @param nomenclaturaDenominacion
		 * @return
		 */
		public static List<NomenclaturaDenominacionExt> getNivelJerarquicoPorNomenclatura(NomenclaturaDenominacion nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NIVEL_JERARQUICO_BY_NIVEL_NOMENCLATURA,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacionExt>>() {
				}.getType();
				List<NomenclaturaDenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		
		/**
		 * Metodo que trae los grados de una nomenclatura de acuerdo a un nivel y denominacion espeifica
		 * @param nomenclaturaDenominacion
		 * @return
		 */
		
		public static List<NomenclaturaDenominacionExt> getGradoPorDenominacionNivelCodigo(NomenclaturaDenominacion nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_GRADO_BY_NIVEL_DENOMINACION,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacionExt>>() {
				}.getType();
				List<NomenclaturaDenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		
		/**
		 * Metodo que trae los Codigos de una cargo de acuerdo a un nivel y denominacion especifica
		 * @param nomenclaturaDenominacion
		 * @return
		 */
		public static List<NomenclaturaDenominacionExt> getCodigoPorDenominacionNivel(NomenclaturaDenominacion nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CODIGO_BY_NIVEL_DENOMINACION,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacionExt>>() {
				}.getType();
				List<NomenclaturaDenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		
		/**
		 *
		 * @param ProgramaAcademico
		 * @return
		 */
		public static ProgramaAcademico getProgramaAcademicoPorCodigoPrograma(long codProgramaAcademico) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = "{\"codProgramaAcademico\":" + codProgramaAcademico + "}";
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PROGRAMA_ACADEMICO_POR_CODIGO_PROGRAMA,json,token, TIME_OUT, auditoriaSeguridad);
				ProgramaAcademico list = gson.fromJson(out, ProgramaAcademico.class);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ProgramaAcademico();
			}
		}
		
		 /**
		 * Metodo para retornar la hoja de vida de las personas que estan asociadas a una UTL de una responsable especifico
		 * @param HojaVidaExt
		 * @return List<HojaVidaExt>
		 */
		public static List<HojaVidaExt> getHVPersonaAsociadaUTLXResponsable(HojaVidaExt hojaVida) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(hojaVida);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_HV_PERSONAS_ASOCIADAS_UTL_X_RESPONSABLE,json,token, TIME_OUT, auditoriaSeguridad);
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
		 * Metodo para retornar las lista de responsables que superan el tope maximo de bolsas debido a que cuentann con mas vinculaciones
		 * de las permitidas
		 * @param HojaVidaExt
		 * @return List<HojaVidaExt>
		 */
		public static List<PlantaPersonaUtlUanExt> getExcedenteBolsaUTL(PlantaPersonaUtlUanExt planta) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(planta);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_EXCEDENTE_BOLSA_UTL,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<PlantaPersonaUtlUanExt>>(){}.getType();
				List<PlantaPersonaUtlUanExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;

			} catch (JsonSyntaxException ex) {
				logger.error("Error en public static List<PlantaPersonaUtlUan> getPlantaPersonaUTLFiltro(PlantaPersonaUtlUan planta) ComunicacionServiciosEnt " + ex.getMessage(), ex);
				return new ArrayList<>();
			}
		}
		
		

		 /**
		 * Metodo que retorna las personas que se encuentran vinculadas un conjunto de plantas UTL por entidad
		 * de las permitidas
		 * @param HojaVidaExt
		 * @return List<HojaVidaExt>
		 */
		public static List<PlantaPersonaUtlUanExt> getPersonasVinculadasUTL(PlantaPersonaUtlUanExt planta) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(planta);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_PERSONAS_ASOCIADAS_PLANTA_UTL,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<PlantaPersonaUtlUanExt>>(){}.getType();
				List<PlantaPersonaUtlUanExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;

			} catch (JsonSyntaxException ex) {
				logger.error("Error en public static List<PlantaPersonaUtlUan> getPlantaPersonaUTLFiltro(PlantaPersonaUtlUan planta) ComunicacionServiciosEnt " + ex.getMessage(), ex);
				return new ArrayList<>();
			}
		}
		
		/**
		 * Realiza proceso de asociacion de una nomenclatura general a una entidad
		 * Este metodo llama a la funcion en BD F_ASOCIAR_NOMENCLATURA_ENTIDAD, la cual se encarga de 
		 * crear la nomenclatura heredada en la entidad con las respectivas denominaciones que tiene asociada la generica
		 * @param Nomenclatura
		 * @author Maria Alejandra C
		 */
		public static Nomenclatura setAsociarNomenclaturAEntidad(NomenclaturaExt nomenclatura) {
			Nomenclatura res;
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			try {
				String json = gson.toJson(nomenclatura);
				String out = ConnectionHttp.sendPost(SerivesRestURL.SET_ASOCIAR_NOMENCLATURA_GENERAL_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				res = gson.fromJson(out, Nomenclatura.class);
				invalidToken(out);
				if (res.isError()) {
					logger.info("El servicio no inserto el dato en setAsociarNomenclaturaentidad", res.getMensaje());
					return res;
				} else {
					return res;
				}
			} catch (JsonSyntaxException e) {
				res = new Nomenclatura();
				res.setMensajeTecnico(e.getMessage());
				return res;
			}
		}
		
		/**
		 * Metodo que trae los niveles jerarquicos asociados a una entidad 
		 * @param nomenclaturaDenominacion
		 * @return
		 */
		public static List<NomenclaturaDenominacionExt> getNivelJerarquicoXEntidad(NomenclaturaDenominacionExt nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_NIVEL_JERARQUICO_BY_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacionExt>>() {
				}.getType();
				List<NomenclaturaDenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		
		
		/**
		 * @author Maria Alejandra C
		 * @param denominacioonExt
		 * @return List<DenominacionExt>
		 * Metodo que trae las denominaciones teniendo como filtro de entrada la entidad
		 */
		public static List<DenominacionExt> getDenominacionXEntidad(DenominacionExt denominacionExt) {
			getToken();
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			String json = gson.toJson(denominacionExt);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_DENOMINACION_BY_NIVEL_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<DenominacionExt>>() {
				}.getType();
				List<DenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException ex) {
				logger.error("public static List<DenominacionExt>   getDenominacionPorNivelNomenclatura(DenominacionExt DenominacionExt) ComuniacionServiciosEnt.java error: " + ex.getMessage() , ex);;
				return new ArrayList<>();
			}
		}
		
		/**
		 * Metodo que trae los Codigos de una cargo de acuerdo a su entidad
		 * @param nomenclaturaDenominacion
		 * @return
		 */
		public static List<NomenclaturaDenominacionExt> getCodigoDenominacionXEntidad(NomenclaturaDenominacionExt nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_CODIGO_DENOMINACION_BY_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacionExt>>() {
				}.getType();
				List<NomenclaturaDenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		
		/**
		 * Metodo que trae los grados de una cargo de acuerdo a su entidad
		 * @param nomenclaturaDenominacion
		 * @return
		 */
		public static List<NomenclaturaDenominacionExt> getGradoDenominacionXEntidad(NomenclaturaDenominacion nomenclaturaDenominacion) {
			gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			getToken();
			String json = gson.toJson(nomenclaturaDenominacion);
			try {
				String out = ConnectionHttp.sendPost(SerivesRestURL.GET_GRADO_DENOMINACION_BY_ENTIDAD,json,token, TIME_OUT, auditoriaSeguridad);
				java.lang.reflect.Type type = new TypeToken<List<NomenclaturaDenominacionExt>>() {
				}.getType();
				List<NomenclaturaDenominacionExt> list = gson.fromJson(out, type);
				invalidToken(out);
				return list;
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return new ArrayList<>();
			}
		}
		
}