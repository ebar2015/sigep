package co.gov.dafp.sigep2.sistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.codec.binary.Base64;

import co.gov.dafp.sigep2.entities.Cargo;
import co.gov.dafp.sigep2.entities.CargoHojaVida;
import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.DependenciaEntidad;
import co.gov.dafp.sigep2.entities.DependenciaHojaVidaExt;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadPlanta;
import co.gov.dafp.sigep2.entities.ExperienciaProfesional;
import co.gov.dafp.sigep2.entities.Idioma;
import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.ProgramaAcademico;
import co.gov.dafp.sigep2.entities.Rol;
import co.gov.dafp.sigep2.entities.VAccionAuditoria;
import co.gov.dafp.sigep2.mbean.ext.CargoHojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.DenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.InstitucionEducativaExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PlantaPersonaUtlUanExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.util.logger.Logger;

@ManagedBean(name = "webUtils")
@SessionScoped
public class WebUtils implements Serializable {
	
	private static final Logger logger = Logger.getInstance(WebUtils.class);
	private static final long serialVersionUID = 8539942703632046225L;
	private static WebUtils instance;
	private static String HADOOP_ON = "1";

	public static synchronized WebUtils getInstance() {
		if (instance == null) {
			instance = new WebUtils();
		}
		return instance;
	}
	private static final String CONFIG_PATH = "CONFIG_PATH";

	private List<SelectItem> iEducativa;
	private List<SelectItem> iEducativaTodas;
	private List<SelectItem> idiomas;
	private List<SelectItem> nivelEducativo;
	private List<SelectItem> areaConocimiento;
	private List<SelectItem> jornadaLaboral;
	private List<SelectItem> motivoRetiro;
	private List<SelectItem> tipoDiscapacidad;
	private List<SelectItem> tipoVia;
	private List<SelectItem> tipoOrientacion;
	private List<SelectItem> tipoLetra;
	private List<SelectItem> tiposDeIdentificacion;
	private List<SelectItem> factorRh;
	private List<SelectItem> genero;
	private List<SelectItem> estadoCivil;
	private List<SelectItem> tiposAsociacion;
	private List<SelectItem> tiposEntidad;
	private List<SelectItem> poblacionEtnica;
	private List<SelectItem> reporte;
	private List<SelectItem> naturalezaEmpleo;
	private List<SelectItem> regimenSalarial;
	private List<SelectItem> claseLibreta_mil;
	private List<SelectItem> tipoArticulo;
	private List<SelectItem> nivelIdioma;
	private List<SelectItem> orientacionSexual;
	private List<SelectItem> tituloOtorgado;
	private List<SelectItem> pais;
	private List<SelectItem> tipoZona;
	private List<SelectItem> cabezaFamilia;
	private List<SelectItem> entidades;
	private List<SelectItem> tipoInstitucion;
	private List<SelectItem> nivelJerarquico;
	private List<SelectItem> codNivelCumplimiento;
	private List<SelectItem> codTipoEvaluacion;
	private List<SelectItem> dependenciaentidad;
	private List<SelectItem> institucionesporidtipo;
	private List<SelectItem> victimaDesplazamiento;
	private List<SelectItem> demasTiposProduccionBiblografic;
	private List<SelectItem> libroResultadoInvestigacio;
	private List<SelectItem> entidadesportipo;
	private List<SelectItem> medioCApacitacion;
	private List<SelectItem> modalidadEstudio;
	private List<SelectItem> formaParticipacion;
	private List<SelectItem> tipoBien;
	private List<SelectItem> tipoIngreso;
	private List<SelectItem> tipoIngresoBR;
	private List<SelectItem> tipoDeclaracion;
	private List<SelectItem> accionesAuditoria;
	private List<SelectItem> tipoMonedas;
	private List<SelectItem> tipoFuentesFinanciacion;
	private List<SelectItem> lstMotivosSuspensionContrat;
	private List<SelectItem> parentesco;
	private List<SelectItem> tipoCuenta;
	private List<SelectItem> tipoVinculacionJunta;
	private List<SelectItem> calidadMiembroBr;
	private List<SelectItem> calidadSocioBr;
	private List<SelectItem> tipoModalidad;
	private List<SelectItem> dataExample;
	private List<SelectItem> lstTiposModificacionContrato;
	private List<SelectItem> demasTiposProduccionBiblografica;
	private List<SelectItem> libroResultadoInvestigacion;
	private List<SelectItem> lstMotivosSuspensionContrato;
	private List<SelectItem> lstTiposModificacionContratos;
	private List<SelectItem> lstTiposProcesosCarguesMasivos;
	private List<SelectItem> lstEstadosCargueMasivo;
	private List<SelectItem> descripcionActividadBR;
	private List<SelectItem> formaBeneficiosBR;
	private List<SelectItem> tiposNormaEntidad;
	private List<SelectItem> tipoNaturalezaJuridica;
	private List<SelectItem> tipoOrdenTerritorial;
	private List<SelectItem> tipoSubordenEntidad;
	private List<SelectItem> tipoClasificacionOrganica;
	private List<SelectItem> tipoSectorAdministrativo;
	private List<SelectItem> tipoNivelAdministrativo;
	private List<SelectItem> tipoCategoriaEntidad;
	private List<SelectItem> tipoAdscripcion;
	private List<SelectItem> tipoSistemaCarrera;
	private List<SelectItem> tipoPoliticaPublica;
	private List<SelectItem> tipoNorma;
	private List<SelectItem> tipoPlanta;
	private List<SelectItem> entidadPostConflicto;
	private List<SelectItem> personeriaJuridica;
	private List<SelectItem> lstAdmonRecursosContratos;
	private List<SelectItem> lstMaestroEstadosEntidad;
	private List<SelectItem> clasificacionPlantaByEntidad;
	private List<SelectItem> causalDesvinculacion;
	private List<SelectItem> lstCargos;
	private List<SelectItem> lstAdscVinculada;
	private List<SelectItem> tiposAutonomia;
	private List<SelectItem> lstRolBase;
	private List<SelectItem> lstSubEstadoentidad;
	private List<SelectItem> lstCodigoDenominacion;
	private List<SelectItem> tipoNombramiento;
	private List<SelectItem> lstDocumentosAdicionales;
	private List<SelectItem> clasePlanta;
	private List<SelectItem> clasificacionPlanta;
	
	public static String CNS_RUTA_DOCUMENTO;           
	public static String CNS_RUTA_LIBRETA_MILITAR;
	public static String CNS_RUTA_DOCUMENTO_DISCAPACIDAD;
	public static String CNS_RUTA_FOTO_USUARIO;      
	public static String CNS_RUTA_DOC_EXP_DOCENTE;       
	public static String CNS_RUTA_TARJETA_PROFESIONAL;   
	public static String CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE; 
	public static String CNS_RUTA_IDIOMA_SOPORTE;
	public static String CNS_RUTA_EDUCACION_FORMAL;
	public static String CNS_RUTA_DOC_EXP_LABORAL;     
	public static String CNS_RUTA_FILE_FOTO_HOJA_VIDA;
	public static String CNS_RUTA_LOGS_CM;
	public static String CNS_RUTA_DOCUMENTOS_ADICIONALES;
	public static String CNS_RUTA_REPORTES_OFF_LINE;
	
	public static String WS_MULTIMEDIA_UPLOAD;
	public static String WS_MULTIMEDIA_UPLOAD_R;
	public static String WS_MULTIMEDIA_DOWNLOAD;   
	public static String WS_MULTIMEDIA_DOWNLOAD_R;
	public static String WS_MULTIMEDIA_SHOW;
	public static String WS_MULTIMEDIA_PATH;
	public static String WS_UPLOAD_HADOOP_ONLINE;
	
	public static String TP_DOCUMENTO_IDENTIFICACION;         
	public static String TP_LIBRETA_MILITAR;
	public static String TP_DOCUMENTO_DISCAPACIDAD;  
	public static String TP_FOTO_USUARIO;     
	public static String TP_DOC_EXP_DOCENTE;       
	public static String TP_TARJETA_PROFESIONAL;  
	public static String TP_CONOCIMIENTO_SOPORTE;
	public static String TP_IDIOMA_SOPORTE;
	public static String TP_EDUCACION_FORMAL;
	public static String TP_DOC_EXP_LABORAL;    
	public static String TP_FILE_FOTO_HOJA_VIDA;
	public static String TP_LOGS_CM;
	public static String TP_DOCUMENTOS_ADICIONALES;
	public static String TP_FOTO_HOJA_VIDA;
	public static String TP_NORMA;
	public static String WS_UPLOAD_HADOOP_ONLINE_OFF;
	
	private static final String ORIGINAL     												= "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT  												= "AaEeIiOoUuNnUu";
    public static final String NOMBRE_CIFRA_1  = "Debe reportar Acuerdos de Gestión (Fuente: Función Pública)";
    public static final String NOMBRE_CIFRA_2  = "Acuerdos de Gestión - Reporte 2015";
    public static final String NOMBRE_CIFRA_3  = "Número de Gerentes públicos (Fuente: FURAG 2015 - Pregunta 212)";
    public static final String NOMBRE_CIFRA_4  = "Número de Gerentes públicos (Fuente: FURAG 2015 - Pregunta 212)";

	

	public WebUtils() {
		this.CNS_RUTA_DOCUMENTO             = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.RUTA_DOCUMENTO.getValue())).getValorParametro();
	    this.CNS_RUTA_LIBRETA_MILITAR       =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_LIBRETA_MILITAR.getValue())).getValorParametro();
	    this.CNS_RUTA_DOCUMENTO_DISCAPACIDAD      =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_DOCUMENTO_DISCAPACIDAD.getValue())).getValorParametro();
	    this.CNS_RUTA_FOTO_USUARIO          =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_FOTO_USUARIO.getValue())).getValorParametro();
	    this.CNS_RUTA_DOC_EXP_DOCENTE       =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_DOC_EXP_DOCENTE.getValue())).getValorParametro();
	    this.CNS_RUTA_TARJETA_PROFESIONAL   =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_TARJETA_PROFESIONAL.getValue())).getValorParametro();
	    this.CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_OTRO_CONOCIMIENTO_SOPORTE.getValue())).getValorParametro();
	    this.CNS_RUTA_IDIOMA_SOPORTE        =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_IDIOMA_SOPORTE.getValue())).getValorParametro();
	    this.CNS_RUTA_DOC_EXP_LABORAL       =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_DOC_EXP_LABORAL.getValue())).getValorParametro();
	    this.CNS_RUTA_FILE_FOTO_HOJA_VIDA   =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_FILE_FOTO_HOJA_VIDA.getValue())).getValorParametro();
	    this.CNS_RUTA_LOGS_CM = ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_FILE_LOGS_CM.getValue())).getValorParametro();
	    this.WS_MULTIMEDIA_UPLOAD   =   ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.TH_WS_MULTIMEDIA_UPLOAD.getValue())).getValorParametro()+"/uploadFile/";
	    this.WS_MULTIMEDIA_UPLOAD_R   =   ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.TH_WS_MULTIMEDIA_UPLOAD.getValue())).getValorParametro()+"/uploadReportes";
	    this.WS_MULTIMEDIA_DOWNLOAD   = ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.TH_WS_MULTIMEDIA.getValue())).getValorParametro()+"/getfile/";
	    this.WS_MULTIMEDIA_DOWNLOAD_R   = ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.TH_WS_MULTIMEDIA.getValue())).getValorParametro();
	    this.WS_MULTIMEDIA_SHOW   =    ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.TH_WS_MULTIMEDIA.getValue())).getValorParametro();
	    
	    this.WS_MULTIMEDIA_PATH =    ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.WS_MULTIMEDIA_PATH.getValue())).getValorParametro();
	    
	    this.CNS_RUTA_EDUCACION_FORMAL       =ComunicacionServiciosSis.
	    	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_EDUCACION_FORMAL.getValue())).getValorParametro();
	    this.CNS_RUTA_DOCUMENTOS_ADICIONALES       =ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.RUTA_DOCUMENTOS_ADICIONALES_HV.getValue())).getValorParametro();	    
	    this.CNS_RUTA_REPORTES_OFF_LINE = "reportesoffline";
	    this.TP_DOCUMENTO_IDENTIFICACION = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_DOCUMENTO_IDENTIFICACION.getValue())).getValorParametro();         
		this.TP_LIBRETA_MILITAR  = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_LIBRETA_MILITAR.getValue())).getValorParametro();
		this.TP_DOCUMENTO_DISCAPACIDAD  = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_DOCUMENTO_DISCAPACIDAD.getValue())).getValorParametro();  
		this.TP_FOTO_USUARIO  = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_FOTO_USUARIO.getValue())).getValorParametro();    
		this.TP_DOC_EXP_DOCENTE = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_DOC_EXP_DOCENTE.getValue())).getValorParametro();     
		this.TP_TARJETA_PROFESIONAL = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_TARJETA_PROFESIONAL.getValue())).getValorParametro();  
		this.TP_CONOCIMIENTO_SOPORTE = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_OTRO_CONOCIMIENTO_SOPORTE.getValue())).getValorParametro(); 
		this.TP_IDIOMA_SOPORTE = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_IDIOMA_SOPORTE.getValue())).getValorParametro();
		this.TP_EDUCACION_FORMAL = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_EDUCACION_FORMAL.getValue())).getValorParametro();
		this.TP_DOC_EXP_LABORAL = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_DOC_EXP_LABORAL.getValue())).getValorParametro();    
		this.TP_FILE_FOTO_HOJA_VIDA = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_FILE_FOTO_HOJA_VIDA.getValue())).getValorParametro(); 
		this.TP_LOGS_CM = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_LOGS_CM.getValue())).getValorParametro(); 
		this.TP_DOCUMENTOS_ADICIONALES = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_DOCUMENTOS_ADICIONALES.getValue())).getValorParametro(); 
		this.TP_FOTO_HOJA_VIDA = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_FOTO_HOJA_VIDA.getValue())).getValorParametro(); 
		this.TP_NORMA = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.TP_NORMA.getValue())).getValorParametro();
		this.WS_UPLOAD_HADOOP_ONLINE = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.HADOOP_ONLINE.getValue())).getValorParametro();
		this.WS_UPLOAD_HADOOP_ONLINE_OFF = ComunicacionServiciosSis.
				getParametricaporId(new BigDecimal(TipoParametro.HADOOP_ONLINE_OFF.getValue())).getValorParametro();
	}

	
	
	/**
	 * @return el lstCargos
	 */
	public List<SelectItem> getLstCargos() {
		return lstCargos;
	}



	/**
	 * @param lstCargos el lstCargos a establecer
	 */
	public void setLstCargos(List<SelectItem> lstCargos) {
		this.lstCargos = lstCargos;
	}



	/**
	 * @return the iEducativa
	 */
	public List<SelectItem> getiEducativa() {
		return getIEducativa();
	}

	/**
	 * @param iEducativa
	 *            the iEducativa to set
	 */
	public void setiEducativa(List<SelectItem> iEducativa) {
		this.iEducativa = iEducativa;
	}
	
	

	/**
	 * @return the iEducativaTodas
	 */
	public List<SelectItem> getiEducativaTodas() {
		return getIEducativaTodas();
	}
	
	/**
	 * @param iEducativaTodas the iEducativaTodas to set
	 */
	public void setiEducativaTodas(List<SelectItem> iEducativaTodas) {
		this.iEducativaTodas = iEducativaTodas;
	}



	/**
	 * @return
	 */
	private List<SelectItem> getIEducativa() {
		List<SelectItem> list = new ArrayList<>();
		List<InstitucionEducativa> listP = ComunicacionServiciosEnt.listInstitucionEducativa();
		try {
			if (!listP.isEmpty()) {
				for (InstitucionEducativa aux : listP) {
					list.add(new SelectItem(aux.getCodInstitucionEducativa(), aux.getNombreInstitucion()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	/**
	 * @return
	 */
	private List<SelectItem> getIEducativaTodas() {
		List<SelectItem> list = new ArrayList<>();
		InstitucionEducativa filtroInsitucionEducativa = new InstitucionEducativa();
		filtroInsitucionEducativa.setFlgActivo((short)1);
		List<InstitucionEducativaExt> listP = ComunicacionServiciosAdmin.obtenerInstitucionEducativaFiltro(filtroInsitucionEducativa);
		try {
			if (!listP.isEmpty()) {
				for (InstitucionEducativa aux : listP) {
					list.add(new SelectItem(aux.getCodInstitucionEducativa(), aux.getNombreInstitucion()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}


	public List<SelectItem> getDemasTiposProduccionBiblografic() {
		return demasTiposProduccionBiblografic;
	}

	public void setDemasTiposProduccionBiblografic(List<SelectItem> demasTiposProduccionBiblografic) {
		this.demasTiposProduccionBiblografic = demasTiposProduccionBiblografic;
	}

	public List<SelectItem> getLibroResultadoInvestigacio() {
		return libroResultadoInvestigacio;
	}

	public void setLibroResultadoInvestigacio(List<SelectItem> libroResultadoInvestigacio) {
		this.libroResultadoInvestigacio = libroResultadoInvestigacio;
	}

	public List<SelectItem> getLstMotivosSuspensionContrat() {
		return lstMotivosSuspensionContrat;
	}

	public void setLstMotivosSuspensionContrat(List<SelectItem> lstMotivosSuspensionContrat) {
		this.lstMotivosSuspensionContrat = lstMotivosSuspensionContrat;
	}

	public List<SelectItem> getLstTiposModificacionContrato() {
		return lstTiposModificacionContrato;
	}

	public void setLstTiposModificacionContrato(List<SelectItem> lstTiposModificacionContrato) {
		this.lstTiposModificacionContrato = lstTiposModificacionContrato;
	}

	public List<SelectItem> getTipoNaturalezaJuridica() {
		return getIParametricas(TipoParametro.TIPO_NATURALEZA_JURIDICA);
	}

	public void setTipoNaturalezaJuridica(List<SelectItem> tipoNaturalezaJuridica) {
		this.tipoNaturalezaJuridica = tipoNaturalezaJuridica;
	}

	/**
	 * 
	 * @return
	 */
	private List<SelectItem> getPaises() {
		List<SelectItem> list = new ArrayList<>();
		List<Pais> listP = ComunicacionServiciosSis.getPaises();
		try {
			if (!listP.isEmpty()) {
				for (Pais aux : listP) {
					list.add(new SelectItem(aux.getCodPais(), aux.getNombrePais()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @return
	 */
	private List<SelectItem> getAccionesAuditoriaServicio() {
		List<SelectItem> list = new ArrayList<>();
		List<VAccionAuditoria> listP = ComunicacionServiciosSis.getVaccionAuditoria();
		try {
			if (!listP.isEmpty()) {
				for (VAccionAuditoria aux : listP) {
					list.add(new SelectItem(aux.getAccionAuditoriaId(), aux.getAccion()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @return
	 */
	private List<SelectItem> getEntidadesBen() {
		List<SelectItem> list = new ArrayList<>();
		List<Entidad> listP = ComunicacionServiciosEnt.entidadesTotalbean();
		try {
			if (!listP.isEmpty()) {
				for (Entidad aux : listP) {
					list.add(new SelectItem(aux.getCodEntidad(), aux.getNombreEntidad()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<SelectItem> getProgramaAcademico(long id) {
		List<SelectItem> list = new ArrayList<>();
		List<ProgramaAcademico> listP = ComunicacionServiciosSis.getprogramaacademicoporint(id);
		try {
			if (!listP.isEmpty()) {
				for (ProgramaAcademico aux : listP) {
					list.add(new SelectItem(aux.getCodTituloAcademico(), aux.getNombreProgramaAcademico().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	public List<SelectItem> getProgramaAcademicoWPrograma(long id) {
		List<SelectItem> list = new ArrayList<>();
		List<ProgramaAcademico> listP = ComunicacionServiciosSis.getprogramaacademicoporint(id);
		try {
			if (!listP.isEmpty()) {
				for (ProgramaAcademico aux : listP) {
					list.add(new SelectItem(aux.getCodTituloAcademico(), aux.getNombreProgramaAcademico().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<SelectItem> getTituloAcademico(long id) {
		List<SelectItem> list = new ArrayList<>();
		ProgramaAcademico listP = ComunicacionServiciosEnt.getprogramaacademicoporid(id);
		try {
			list.add(new SelectItem(listP.getCodTituloAcademico(), listP.getNombreTituloOtorgado().toUpperCase()));
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	public List<SelectItem> getTituloAcademicoByCodPrograma(long id) {
		List<SelectItem> list = new ArrayList<>();
		ProgramaAcademico listP = ComunicacionServiciosEnt.getProgramaAcademicoPorCodigoPrograma(id);
		try {
			list.add(new SelectItem(listP.getCodTituloAcademico(), listP.getNombreTituloOtorgado().toUpperCase()));
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}	


	
	
	
	public List<SelectItem> getTituloAcademicoWTitulo(long id) {
		List<SelectItem> list = new ArrayList<>();
		ProgramaAcademico listP = ComunicacionServiciosEnt.getprogramaacademicoporid(id);
		try {
			list.add(new SelectItem(listP.getCodTituloAcademico(), listP.getNombreTituloOtorgado().toUpperCase()));
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}	

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<SelectItem> getInstitucionPorIdTipo(int id) {
		List<SelectItem> list = new ArrayList<>();
		List<InstitucionEducativa> listP = ComunicacionServiciosEnt.getInstitucionTipo(id);
		try {
			if (!listP.isEmpty()) {
				for (InstitucionEducativa aux : listP) {
					list.add(new SelectItem(aux.getCodInstitucionEducativa(), aux.getNombreInstitucion().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<SelectItem> getDeptos(long id) {
		List<SelectItem> list = new ArrayList<>();
		List<Departamento> listP = ComunicacionServiciosSis.getDepartamentos(id);
		try {
			if (!listP.isEmpty()) {
				for (Departamento aux : listP) {
					list.add(new SelectItem(aux.getCodDepartamento(), aux.getNombreDepartamento().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<SelectItem> getMunicipios(long id) {
		List<SelectItem> list = new ArrayList<>();
		List<Municipio> listP = ComunicacionServiciosSis.getMunicipios(id);
		try {
			if (!listP.isEmpty()) {
				for (Municipio aux : listP) {
					list.add(new SelectItem(aux.getCodMunicipio(), aux.getNombreMunicipio().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<SelectItem> getEntidadPorTipo(long id) {
		List<SelectItem> list = new ArrayList<>();
		List<Entidad> listP = ComunicacionServiciosHV.getEntidadporCodTipoEntidad(id);
		try {
			if (!listP.isEmpty()) {
				for (Entidad aux : listP) {
					if (aux.getNombreEntidad() != null) 
						list.add(new SelectItem(aux.getCodEntidad(), aux.getNombreEntidad().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * Metodo que lista los cargos del maestro cargo_hoja_vida dependenciendo del tipo de entidad enviada.
	 * Este metodo es utilizado en el componente de hoja de vida para la vista experiencia profesional
	 * @param id
	 * @return
	 */
	public List<SelectItem> getCargosHV(long codTipoEntidad) {
		List<SelectItem> list = new ArrayList<>();
		CargoHojaVidaExt filtroCargoHV = new CargoHojaVidaExt();
		filtroCargoHV.setFlgActivo((short) 1);
		if(codTipoEntidad > 0 && codTipoEntidad == TipoParametro.ENTIDAD_PUBLICA.getValue()) {
			filtroCargoHV.setFlgPublico((short) 1);
		}else if(codTipoEntidad > 0 ) {
			filtroCargoHV.setFlgPublico((short) 0);
		}else {
			return new ArrayList<>();
		}
		
		List<CargoHojaVidaExt> lstCargoHV = ComunicacionServiciosSis.getCargoHVFiltro(filtroCargoHV);
		try {
			if (!lstCargoHV.isEmpty()) {
				for (CargoHojaVida aux : lstCargoHV) {
					list.add(new SelectItem(aux.getCodCargoHojaVida(), aux.getNombreCargo().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		return list;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	private List<SelectItem> getProgramaAcademico(int index) {
		List<SelectItem> list = new ArrayList<>();
		List<ProgramaAcademico> listP = ComunicacionServiciosHV.getProgramaacademico();
		try {
			if (!listP.isEmpty()) {
				if (index == 1) {
					for (ProgramaAcademico aux : listP) {
						list.add(new SelectItem(aux.getCodProgramaAcademico(), aux.getNombreProgramaAcademico().toUpperCase()));
					}
				} else {
					for (ProgramaAcademico aux : listP) {
						list.add(new SelectItem(aux.getCodTituloAcademico(), aux.getNombreTituloOtorgado().toUpperCase()));
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	private List<SelectItem> getIParametricasCarrera(TipoParametro type) {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosHV.getParametrica(type.getValue());
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if(aux.getFlgEstado() == 1) {
						if(aux.getCodTablaParametrica().intValue() != 1459 && aux.getCodTablaParametrica().intValue() !=552 && aux.getCodTablaParametrica().intValue() != 10033) {
							list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getValorParametro().toUpperCase()));
						}
					}
					
				}
			}
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}

		return list;
	}
	
	private List<SelectItem> getIParametricas(TipoParametro type) {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosHV.getParametrica(type.getValue());
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if(aux.getFlgEstado() == 1) {
						if(aux.getCodTablaParametrica().intValue() != 1459) {
							list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getValorParametro().toUpperCase()));
						}
					}
				}
			}
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}

		return list;
	}

	
	
	private List<SelectItem> getIParametricaOnlyNumero(TipoParametro type) {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosHV.getParametricaOnlyNumero(type.getValue());
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if(aux.getFlgEstado() == 1) {
						if(aux.getCodTablaParametrica().intValue() != 1459) {
							list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getValorParametro().toUpperCase()));
						}
					}
				}
			}
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @param codPadreParametrica
	 * @return
	 */
	public List<SelectItem> getParametricaPorIdPadre(long codPadreParametrica) {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosSis.getParametricaPorIdPadre(codPadreParametrica);
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if(aux.getFlgEstado() == 1) {
						list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getValorParametro().toUpperCase()));
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	public List<SelectItem> getParametricasByOrden(long codPadreParametrica) {
		List<SelectItem> list = new ArrayList<>();
		Parametrica parametrica = new Parametrica();
		parametrica.setCodPadreParametrica(new BigDecimal(codPadreParametrica));
		List<Parametrica> listP = ComunicacionServiciosAdmin.getParametricaByOrden(parametrica);
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					 list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getValorParametro().toUpperCase()));
					
				}
			}
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}

		return list;
	}

	
	
	public List<SelectItem> getInstitucionByPais(Integer codPais) {
		List<SelectItem> list = new ArrayList<>();
		InstitucionEducativa institucionEducativa = new InstitucionEducativa();
		if(codPais !=null && codPais.equals(TipoParametro.PAIS_COLOMBIA.getValue())) {
			institucionEducativa.setFlgInstExtranjera((short) 0);
		}else {
			institucionEducativa.setFlgInstExtranjera((short) 1);
		}

		List<InstitucionEducativa> listP = ComunicacionServiciosSis.getInstitucionByPais(institucionEducativa);
		try {
			if (!listP.isEmpty()) {
				for (InstitucionEducativa aux : listP) {
					 list.add(new SelectItem(aux.getCodInstitucionEducativa(), aux.getNombreInstitucion().toUpperCase()));
					
				}
			}
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @param codPadreParametrica
	 * @return
	 */
	public List<SelectItem> getCargosPorEntidad(long codTipoEntidad) {
		List<SelectItem> list = new ArrayList<>();
		List<ExperienciaProfesional> listP = ComunicacionServiciosHV.getExperenciaProPortipoEntidad(codTipoEntidad);
		try {
			if (!listP.isEmpty()) {
				for (ExperienciaProfesional aux : listP) {
					list.add(new SelectItem(aux.getCodExperienciaProfesional(), aux.getCargo().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	public List<SelectItem> getIdiomas() {
		return getListaIdioma();
	}

	public void setIdiomas(List<SelectItem> listaIdiomas) {
		this.idiomas = listaIdiomas;
	}

	private List<SelectItem> getListaIdioma() {
		List<SelectItem> list = new ArrayList<>();
		List<Idioma> listP = ComunicacionServiciosSis.idiomaList();
		try {
			if (!listP.isEmpty()) {
				for (Idioma aux : listP) {
					list.add(new SelectItem(aux.getCodIdioma(), aux.getNombreIdioma().toUpperCase()));
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @param codTipoInstitucion
	 * @return
	 */
	public List<SelectItem> getInstitucionTipo(int codTipoInstitucion) {
		List<SelectItem> list = new ArrayList<>();
		List<InstitucionEducativa> listP = ComunicacionServiciosEnt.getInstitucionTipo(codTipoInstitucion);
		try {
			if (!listP.isEmpty()) {
				for (InstitucionEducativa aux : listP) {
					list.add(new SelectItem(aux.getCodInstitucionEducativa(), aux.getNombreInstitucion().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @return
	 */
	public List<SelectItem> getDependenciaEntidad() {
		List<SelectItem> list = new ArrayList<>();
		List<DependenciaEntidad> listP = ComunicacionServiciosEnt.getDependenciaentidad();
		try {
			if (!listP.isEmpty()) {
				for (DependenciaEntidad aux : listP) {
					list.add(new SelectItem(aux.getCodDependenciaEntidad(), aux.getNombreDependencia().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * @return the nivelEducativo
	 */
	public List<SelectItem> getNivelEducativo() {
		return getParametricasByOrden(TipoParametro.NIVEL_EDUCATIVO.getValue());
	}

	/**
	 * @param nivelEducativo
	 *            the nivelEducativo to set
	 */
	public void setNivelEducativo(List<SelectItem> nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	/**
	 * @return the areaConocimiento
	 */
	public List<SelectItem> getAreaConocimiento() {
		return getIParametricas(TipoParametro.AREA_CONOCIMIENTO);
	}

	/**
	 * @param areaConocimiento
	 *            the areaConocimiento to set
	 */
	public void setAreaConocimiento(List<SelectItem> areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	/**
	 * @return the jornadaLaboral
	 */
	public List<SelectItem> getJornadaLaboral() {
		return getIParametricas(TipoParametro.JORNADA_LABORAL);
	}

	/**
	 * @param jornadaLaboral
	 *            the jornadaLaboral to set
	 */
	public void setJornadaLaboral(List<SelectItem> jornadaLaboral) {
		this.jornadaLaboral = jornadaLaboral;
	}

	/**
	 * @return the motivoRetiro
	 */
	public List<SelectItem> getMotivoRetiro() {
		return getIParametricas(TipoParametro.MOTIVO_RETIRO);
	}

	/**
	 * @param motivoRetiro
	 *            the motivoRetiro to set
	 */
	public void setMotivoRetiro(List<SelectItem> motivoRetiro) {
		this.motivoRetiro = motivoRetiro;
	}

	/**
	 * @return the tipoDiscapacidad
	 */
	public List<SelectItem> getTipoDiscapacidad() {
		return getIParametricas(TipoParametro.TIPO_DISCAPACIDAD);
	}

	/**
	 * @param tipoDiscapacidad
	 *            the tipoDiscapacidad to set
	 */
	public void setTipoDiscapacidad(List<SelectItem> tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}

	/**
	 * @return the tipoVia
	 */
	public List<SelectItem> getTipoVia() {
		return getIParametricas(TipoParametro.TIPO_VIA);
	}

	/**
	 * @param tipoVia
	 *            the tipoVia to set
	 */
	public void setTipoVia(List<SelectItem> tipoVia) {
		this.tipoVia = tipoVia;
	}

	/**
	 * @return the tipoOrientacion
	 */
	public List<SelectItem> getTipoOrientacion() {
		return getIParametricas(TipoParametro.TIPO_ORIENTACION);
	}

	/**
	 * @param tipoOrientacion
	 *            the tipoOrientacion to set
	 */
	public void setTipoOrientacion(List<SelectItem> tipoOrientacion) {
		this.tipoOrientacion = tipoOrientacion;
	}

	/**
	 * @return the tipoLetra
	 */
	public List<SelectItem> getTipoLetra() {
		return getIParametricas(TipoParametro.TIPO_LETRA);
	}

	/**
	 * @param tipoLetra
	 *            the tipoLetra to set
	 */
	public void setTipoLetra(List<SelectItem> tipoLetra) {
		this.tipoLetra = tipoLetra;
	}

	/**
	 * @return the tiposDeIdentificacion
	 */
	public List<SelectItem> getTiposDeIdentificacion() {
		return getIParametricas(TipoParametro.TIPOS_DE_IDENTIFICACION);
	}

	/**
	 * @param tiposDeIdentificacion
	 *            the tiposDeIdentificacion to set
	 */
	public void setTiposDeIdentificacion(List<SelectItem> tiposDeIdentificacion) {
		this.tiposDeIdentificacion = tiposDeIdentificacion;
	}

	/**
	 * @return the factorRh
	 */
	public List<SelectItem> getFactorRh() {
		return getIParametricas(TipoParametro.FACTOR_RH);
	}

	/**
	 * @param factorRh
	 *            the factorRh to set
	 */
	public void setFactorRh(List<SelectItem> factorRh) {
		this.factorRh = factorRh;
	}

	/**
	 * @return the genero
	 */
	public List<SelectItem> getGenero() {
		return getIParametricas(TipoParametro.GENERO);
	}

	/**
	 * @param genero
	 *            the genero to set
	 */
	public void setGenero(List<SelectItem> genero) {
		this.genero = genero;
	}

	/**
	 * @return the estadoCivil
	 */
	public List<SelectItem> getEstadoCivil() {
		return getIParametricas(TipoParametro.ESTADO_CIVIL);
	}

	/**
	 * @param estadoCivil
	 *            the estadoCivil to set
	 */
	public void setEstadoCivil(List<SelectItem> estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**
	 * @return the tiposAsociacion
	 */
	public List<SelectItem> getTiposAsociacion() {
		return getIParametricas(TipoParametro.TIPOS_ASOCIACION);
	}

	/**
	 * @param tiposAsociacion
	 *            the tiposAsociacion to set
	 */
	public void setTiposAsociacion(List<SelectItem> tiposAsociacion) {
		this.tiposAsociacion = tiposAsociacion;
	}

	/**
	 * @return the tiposEntidad
	 */
	public List<SelectItem> getTiposEntidad() {
		return getIParametricas(TipoParametro.TIPOS_ENTIDAD);
	}

	/**
	 * @param tiposEntidad
	 *            the tiposEntidad to set
	 */
	public void setTiposEntidad(List<SelectItem> tiposEntidad) {
		this.tiposEntidad = tiposEntidad;
	}

	/**
	 * @return the poblacionEtnica
	 */
	public List<SelectItem> getPoblacionEtnica() {
		return getIParametricas(TipoParametro.POBLACION_ETNICA);
	}

	/**
	 * @param poblacionEtnica
	 *            the poblacionEtnica to set
	 */
	public void setPoblacionEtnica(List<SelectItem> poblacionEtnica) {
		this.poblacionEtnica = poblacionEtnica;
	}

	/**
	 * @return the reporte
	 */
	public List<SelectItem> getReporte() {
		return getIParametricas(TipoParametro.REPORTE);
	}

	/**
	 * @param reporte
	 *            the reporte to set
	 */
	public void setReporte(List<SelectItem> reporte) {
		this.reporte = reporte;
	}

	/**
	 * @return the naturalezaEmpleo
	 */
	public List<SelectItem> getNaturalezaEmpleo() {
		return getIParametricas(TipoParametro.NATURALEZA_EMPLEO);
	}

	/**
	 * @param naturalezaEmpleo
	 *            the naturalezaEmpleo to set
	 */
	public void setNaturalezaEmpleo(List<SelectItem> naturalezaEmpleo) {
		this.naturalezaEmpleo = naturalezaEmpleo;
	}

	/**
	 * @return the regimenSalarial
	 */
	public List<SelectItem> getRegimenSalarial() {
		return getIParametricas(TipoParametro.REGIMEN_SALARIAL);
	}

	/**
	 * @param regimenSalarial
	 *            the regimenSalarial to set
	 */
	public void setRegimenSalarial(List<SelectItem> regimenSalarial) {
		this.regimenSalarial = regimenSalarial;
	}

	/**
	 * @return the claseLibreta_mil
	 */
	public List<SelectItem> getClaseLibreta_mil() {
		return getIParametricas(TipoParametro.CLASE_LIBRETA_MIL);
	}

	/**
	 * @param claseLibreta_mil
	 *            the claseLibreta_mil to set
	 */
	public void setClaseLibreta_mil(List<SelectItem> claseLibreta_mil) {
		this.claseLibreta_mil = claseLibreta_mil;
	}

	/**
	 * @return the tipoArticulo
	 */
	public List<SelectItem> getTipoArticulo() {
		return getIParametricas(TipoParametro.TIPO_ARTICULO);
	}

	/**
	 * @param tipoArticulo
	 *            the tipoArticulo to set
	 */
	public void setTipoArticulo(List<SelectItem> tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}

	/**
	 * @return the nivelIdioma
	 */
	public List<SelectItem> getNivelIdioma() {
		return getIParametricas(TipoParametro.NIVEL_IDIOMA);
	}

	/**
	 * @param nivelIdioma
	 *            the nivelIdioma to set
	 */
	public void setNivelIdioma(List<SelectItem> nivelIdioma) {
		this.nivelIdioma = nivelIdioma;
	}

	/**
	 * @return the orientacionSexual
	 */
	public List<SelectItem> getOrientacionSexual() {
		return getIParametricas(TipoParametro.ORIENTACION_SEXUAL);
	}

	/**
	 * @param orientacionSexual
	 *            the orientacionSexual to set
	 */
	public void setOrientacionSexual(List<SelectItem> orientacionSexual) {
		this.orientacionSexual = orientacionSexual;
	}

	/**
	 * @param programaAcademico
	 *            the programaAcademico to set
	 */
	public void setProgramaAcademico(List<SelectItem> programaAcademico) {
	}

	/**
	 * @return the tituloOtorgado
	 */
	public List<SelectItem> getTituloOtorgado() {
		return tituloOtorgado;
	}

	/**
	 * @param tituloOtorgado
	 *            the tituloOtorgado to set
	 */
	public void setTituloOtorgado(List<SelectItem> tituloOtorgado) {
		this.tituloOtorgado = tituloOtorgado;
	}

	/**
	 * @return the pais
	 */
	public List<SelectItem> getPais() {
		return getPaises();
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(List<SelectItem> pais) {
		this.pais = pais;
	}

	/**
	 * @return the tipoZona
	 */
	public List<SelectItem> getTipoZona() {
		return getIParametricas(TipoParametro.TIPO_ZONA);
	}

	/**
	 * @param tipoZona
	 *            the tipoZona to set
	 */
	public void setTipoZona(List<SelectItem> tipoZona) {
		this.tipoZona = tipoZona;
	}

	public List<SelectItem> getCabezaFamilia() {
		return getIParametricas(TipoParametro.CABEZA_DE_FAMILIA);
	}

	public void setCabezaFamilia(List<SelectItem> cabezaFamilia) {
		this.cabezaFamilia = cabezaFamilia;
	}

	/**
	 * @return the entidades
	 */
	public List<SelectItem> getEntidades() {
		return getEntidadesBen();
	}

	/**
	 * @param entidades
	 *            the entidades to set
	 */
	public void setEntidades(List<SelectItem> entidades) {
		this.entidades = entidades;
	}

	/**
	 * @return the tipoInstitucion
	 */
	public List<SelectItem> getTipoInstitucion() {
		return getIParametricas(TipoParametro.TIPO_INSTITUCION);
	}

	/**
	 * @param tipoInstitucion
	 *            the tipoInstitucion to set
	 */
	public void setTipoInstitucion(List<SelectItem> tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}

	/**
	 * @return the nivelJerarquico
	 */
	public List<SelectItem> getNivelJerarquico() {
		return getIParametricas(TipoParametro.NIVEL_JERARQUICO);
	}

	/**
	 * @param nivelJerarquico
	 *            the nivelJerarquico to set
	 */
	public void setNivelJerarquico(List<SelectItem> nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}

	/**
	 * @return the codNivelCumplimiento
	 */
	public List<SelectItem> getCodNivelCumplimiento() {
		return getIParametricas(TipoParametro.COD_NIVEL_CUMPLIMIENTO);
	}

	/**
	 * @param codNivelCumplimiento
	 *            the codNivelCumplimiento to set
	 */
	public void setCodNivelCumplimiento(List<SelectItem> codNivelCumplimiento) {
		this.codNivelCumplimiento = codNivelCumplimiento;
	}

	/**
	 * @return the codTipoEvaluacion
	 */
	public List<SelectItem> getCodTipoEvaluacion() {
		return getIParametricas(TipoParametro.COD_TIPO_EVALUACION);
	}

	/**
	 * @param codTipoEvaluacion
	 *            the codTipoEvaluacion to set
	 */
	public void setCodTipoEvaluacion(List<SelectItem> codTipoEvaluacion) {
		this.codTipoEvaluacion = codTipoEvaluacion;
	}

	/**
	 * @return the dependenciaentidad
	 */
	public List<SelectItem> getDependenciaentidad() {
		return getDependenciaEntidad();
	}

	/**
	 * @param dependenciaentidad
	 *            the dependenciaentidad to set
	 */
	public void setDependenciaentidad(List<SelectItem> dependenciaentidad) {
		this.dependenciaentidad = dependenciaentidad;
	}

	/**
	 * @return the institucionesporidtipo
	 */
	public List<SelectItem> getInstitucionesporidtipo() {
		return institucionesporidtipo;
	}

	/**
	 * @param institucionesporidtipo
	 *            the institucionesporidtipo to set
	 */
	public void setInstitucionesporidtipo(List<SelectItem> institucionesporidtipo) {
		this.institucionesporidtipo = institucionesporidtipo;
	}

	/**
	 * @return the victimaDesplazamiento
	 */
	public List<SelectItem> getVictimaDesplazamiento() {
		return getIParametricas(TipoParametro.VICTIMA_DESPLAZAMIENTO);
	}

	/**
	 * @param victimaDesplazamiento
	 *            the victimaDesplazamiento to set
	 */
	public void setVictimaDesplazamiento(List<SelectItem> victimaDesplazamiento) {
		this.victimaDesplazamiento = victimaDesplazamiento;
	}

	/**
	 * @return the demasTiposProduccionBiblografica
	 */
	public List<SelectItem> getDemasTiposProduccionBiblografica() {
		return getIParametricas(TipoParametro.DEMAS_TIPOS_PRODUCCION_BIBLIOGRAFICA);
	}

	/**
	 * @param demasTiposProduccionBiblografica
	 *            the demasTiposProduccionBiblografica to set
	 */
	public void setDemasTiposProduccionBiblografica(List<SelectItem> demasTiposProduccionBiblografica) {
		this.demasTiposProduccionBiblografica = demasTiposProduccionBiblografica;
	}

	/**
	 * @return the libroResultadoInvestigacion
	 */
	public List<SelectItem> getLibroResultadoInvestigacion() {
		return getIParametricas(TipoParametro.LIBRO_RESULTADO_INVESTIGACION);
	}

	/**
	 * @param libroResultadoInvestigacion
	 *            the libroResultadoInvestigacion to set
	 */
	public void setLibroResultadoInvestigacion(List<SelectItem> libroResultadoInvestigacion) {
		this.libroResultadoInvestigacion = libroResultadoInvestigacion;
	}

	/**
	 * @return the entidadesportipo
	 */
	public List<SelectItem> getEntidadesportipo() {
		return entidadesportipo;
	}

	/**
	 * @param entidadesportipo
	 *            the entidadesportipo to set
	 */
	public void setEntidadesportipo(List<SelectItem> entidadesportipo) {
		this.entidadesportipo = entidadesportipo;
	}

	public List<SelectItem> getMedioCApacitacion() {
		return getIParametricas(TipoParametro.MEDIO_DE_CAPACITACION);
	}

	public void setMedioCApacitacion(List<SelectItem> medioCApacitacion) {
		this.medioCApacitacion = medioCApacitacion;
	}

	public List<SelectItem> getModalidadEstudio() {
		return getIParametricas(TipoParametro.MODALIDAD_DE_ESTUDIO);
	}

	public void setModalidadEstudio(List<SelectItem> modalidadEstudio) {
		this.modalidadEstudio = modalidadEstudio;
	}

	/**
	 * @return the formaParticipacion
	 */
	public List<SelectItem> getFormaParticipacion() {
		return getIParametricas(TipoParametro.FORMA_DE_PARTICIPACION);
	}

	/**
	 * @param formaParticipacion
	 *            the formaParticipacion to set
	 */
	public void setFormaParticipacion(List<SelectItem> formaParticipacion) {
		this.formaParticipacion = formaParticipacion;
	}

	/**
	 * @return the tipoBien
	 */
	public List<SelectItem> getTipoBien() {
		return getIParametricas(TipoParametro.TIPO_BIEN);
	}

	/**
	 * @param tipoBien
	 *            the tipoBien to set
	 */
	public void setTipoBien(List<SelectItem> tipoBien) {
		this.tipoBien = tipoBien;
	}

	/**
	 * @return the tipoIngreso
	 */
	public List<SelectItem> getTipoIngreso() {
		return getIParametricas(TipoParametro.OTRAS_ASIGNACIONES_DENOMINACION);
	}

	/**
	 * @param tipoIngreso
	 *            the tipoIngreso to set
	 */
	public void setTipoIngreso(List<SelectItem> tipoIngreso) {
		this.tipoIngreso = tipoIngreso;
	}

	/**
	 * @return the tipoDeclaracion
	 */
	public List<SelectItem> getTipoDeclaracion() {
		return getIParametricas(TipoParametro.TIPO_DECLARACION);
	}

	/**
	 * @param tipoDeclaracion
	 *            the tipoDeclaracion to set
	 */
	public void setTipoDeclaracion(List<SelectItem> tipoDeclaracion) {
		this.tipoDeclaracion = tipoDeclaracion;
	}

	/**
	 * @return the accionesAuditoria
	 */
	public List<SelectItem> getAccionesAuditoria() {
		return getAccionesAuditoriaServicio();
	}

	/**
	 * @param accionesAuditoria
	 *            the accionesAuditoria to set
	 */
	public void setAccionesAuditoria(List<SelectItem> accionesAuditoria) {
		this.accionesAuditoria = accionesAuditoria;
	}

	public List<SelectItem> getTipoMonedas() {
		return getIParametricas(TipoParametro.TIPO_MONEDA);
	}

	public void setTipoMonedas(List<SelectItem> tipoMonedas) {
		this.tipoMonedas = tipoMonedas;
	}

	public List<SelectItem> getTipoFuentesFinanciacion() {
		return getIParametricas(TipoParametro.TIPO_FUENTE_FINANCIACION);
	}

	public void setTipoFuentesFinanciacion(List<SelectItem> tipoFuentesFinanciacion) {
		this.tipoFuentesFinanciacion = tipoFuentesFinanciacion;
	}

	public List<SelectItem> getLstMotivosSuspensionContrato() {
		return getIParametricas(TipoParametro.MOTIVOS_SUSPENSION_CONTRATO);
	}

	public void setLstMotivosSuspensionContrato(List<SelectItem> lstMotivosSuspensionContrato) {
		this.lstMotivosSuspensionContrato = lstMotivosSuspensionContrato;
	}

	public List<SelectItem> getParentesco() {
		return getIParametricas(TipoParametro.PARENTESCO);
	}

	public void setParentesco(List<SelectItem> parentesco) {
		this.parentesco = parentesco;
	}

	public List<SelectItem> getTipoCuenta() {
		return getIParametricas(TipoParametro.TIPO_CUENTA);
	}

	public void setTipoCuenta(List<SelectItem> tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public List<SelectItem> getTipoVinculacionJunta() {
		return getIParametricas(TipoParametro.TIPO_VINCULACION_JUNTA);
	}

	public void setTipoVinculacionJunta(List<SelectItem> tipoVinculacionJunta) {
		this.tipoVinculacionJunta = tipoVinculacionJunta;
	}

	public List<SelectItem> getCalidadMiembroBr() {
		return getIParametricas(TipoParametro.CALIDAD_MIEMBRO_BR);
	}

	public void setCalidadMiembroBr(List<SelectItem> calidadMiembroBr) {
		this.calidadMiembroBr = calidadMiembroBr;
	}

	public List<SelectItem> getCalidadSocioBr() {
		return getIParametricas(TipoParametro.CALIDAD_SOCIO_BR);
	}

	public void setCalidadSocioBr(List<SelectItem> calidadSocioBr) {
		this.calidadSocioBr = calidadSocioBr;
	}

	public List<SelectItem> getTipoModalidad() {
		return getIParametricas(TipoParametro.TIPO_MODALIDAD);
	}

	public void setTipoModalidad(List<SelectItem> tipoModalidad) {
		this.tipoModalidad = tipoModalidad;
	}

	public List<SelectItem> getLstTiposModificacionContratos() {
		return getIParametricas(TipoParametro.TIPOS_MODIFICACION_CONTRATOS);
	}

	public void setLstTiposModificacionContratos(List<SelectItem> lstTiposModificacionContratos) {
		this.lstTiposModificacionContratos = lstTiposModificacionContratos;
	}

	public List<SelectItem> getDataExample() {
		return getIParametricas(TipoParametro.DATA_EXAMPLE);
	}

	public void setDataExample(List<SelectItem> dataExample) {
		this.dataExample = dataExample;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<SelectItem> getCargoEntidad(long id) {
		List<SelectItem> list = new ArrayList<>();
		List<Cargo> listP = ComunicacionServiciosSis.getCargosEntidad(id);
		try {
			if (!listP.isEmpty()) {
				for (Cargo aux : listP) {
					list.add(new SelectItem(aux.getCodCargo(), aux.getNombreCargo().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<SelectItem> getUtlUanEntidad(long id) {
		List<SelectItem> list = new ArrayList<>();
		EntidadPlantaExt epp = new EntidadPlantaExt();
		epp.setCodEntidad(id);
		epp.setLimitEnd(100000);
		List<EntidadPlanta> listP = ComunicacionServiciosSis.getEntidadPlantaEntidad(epp);

		try {
			if (!listP.isEmpty()) {
				for (EntidadPlanta aux : listP) {
					// list.add(new SelectItem(aux.getCodEntidadPlanta(),
					// aux.getNombrePlantaPersonal()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	public List<SelectItem> getLstEstadosCargueMasivo() {
		Parametrica padreProcesosCarguesMasivos = ComunicacionServiciosSis
				.getParametricaIntetos("ESTADO PROCESO DE CARGA MASIVA");
		lstEstadosCargueMasivo = getParametricaPorIdPadre(
				padreProcesosCarguesMasivos.getCodTablaParametrica().longValue());
		return lstEstadosCargueMasivo;
	}

	public void setLstEstadosCargueMasivo(List<SelectItem> lstEstadosCargueMasivo) {
		this.lstEstadosCargueMasivo = lstEstadosCargueMasivo;
	}

	public List<SelectItem> getLstTiposProcesosCarguesMasivos() {
		Parametrica padreProcesosCarguesMasivos = ComunicacionServiciosSis
				.getParametricaIntetos("PROCESOS DE CARGUES MASIVOS");
		lstTiposProcesosCarguesMasivos = getParametricaPorIdPadre(
				padreProcesosCarguesMasivos.getCodTablaParametrica().longValue());
		return lstTiposProcesosCarguesMasivos;
	}

	public void setLstTiposProcesosCarguesMasivos(List<SelectItem> lstTiposProcesosCarguesMasivos) {
		this.lstTiposProcesosCarguesMasivos = lstTiposProcesosCarguesMasivos;
	}

	/**
	 * @return the descripcionActividadBR
	 */
	public List<SelectItem> getDescripcionActividadBR() {
		return getIParametricas(TipoParametro.DESCRIPCION_ACTIVIDAD_BR);
	}

	/**
	 * @param descripcionActividadBR
	 *            the descripcionActividadBR to set
	 */
	public void setDescripcionActividadBR(List<SelectItem> descripcionActividadBR) {
		this.descripcionActividadBR = descripcionActividadBR;
	}

	/**
	 * @return the formaBeneficiosBR
	 */
	public List<SelectItem> getFormaBeneficiosBR() {
		return getIParametricas(TipoParametro.FORMA_BENEFICIO_BR);
	}

	/**
	 * @param formaBeneficiosBR
	 *            the formaBeneficiosBR to set
	 */
	public void setFormaBeneficiosBR(List<SelectItem> formaBeneficiosBR) {
		this.formaBeneficiosBR = formaBeneficiosBR;
	}

	/**
	 * @return the tiposNormaEntidad
	 */
	public List<SelectItem> getTiposNormaEntidad() {
		return getIParametricas(TipoParametro.TIPO_NORMA);
	}

	/**
	 * @param tiposNormaEntidad
	 *            the tiposNormaEntidad to set
	 */
	public void setTiposNormaEntidad(List<SelectItem> tiposNormaEntidad) {
		this.tiposNormaEntidad = tiposNormaEntidad;
	}

	public List<SelectItem> getTipoOrdenTerritorial() {
		return getIParametricas(TipoParametro.TIPO_ORDEN_TERRITORIAL);
	}

	public void setTipoOrdenTerritorial(List<SelectItem> tipoOrdenTerritorial) {
		this.tipoOrdenTerritorial = tipoOrdenTerritorial;
	}

	public List<SelectItem> getTipoSubordenEntidad() {
		return getIParametricas(TipoParametro.TIPO_SUBORDEN_ENTIDAD);
	}

	public void setTipoSubordenEntidad(List<SelectItem> tipoSubordenEntidad) {
		this.tipoSubordenEntidad = tipoSubordenEntidad;
	}

	public List<SelectItem> getTipoClasificacionOrganica() {
		return getIParametricas(TipoParametro.TIPO_CLASIFICACION_ORGANICA);
	}

	public void setTipoClasificacionOrganica(List<SelectItem> tipoClasificacionOrganica) {
		this.tipoClasificacionOrganica = tipoClasificacionOrganica;
	}

	public List<SelectItem> getTipoSectorAdministrativo() {
		return getIParametricas(TipoParametro.TIPO_SECTOR_ADMINISTRATIVO);
	}

	public void setTipoSectorAdministrativo(List<SelectItem> tipoSectorAdministrativo) {
		this.tipoSectorAdministrativo = tipoSectorAdministrativo;
	}

	public List<SelectItem> getTipoNivelAdministrativo() {
		return getIParametricas(TipoParametro.TIPO_NIVEL_ADMINISTRATIVO);
	}

	public void setTipoNivelAdministrativo(List<SelectItem> tipoNivelAdministrativo) {
		this.tipoNivelAdministrativo = tipoNivelAdministrativo;
	}

	public List<SelectItem> getTipoCategoriaEntidad() {
		return getIParametricasValor(TipoParametro.TIPO_CATEGORIA_ENTIDAD);
	}

	public void setTipoCategoriaEntidad(List<SelectItem> tipoCategoriaEntidad) {
		this.tipoCategoriaEntidad = tipoCategoriaEntidad;
	}

	public List<SelectItem> getTipoAdscripcion() {
		return getIParametricas(TipoParametro.TIPO_ADSCRIPCION_ENTIDAD);
	}

	public void setTipoAdscripcion(List<SelectItem> tipoAdscripcion) {
		this.tipoAdscripcion = tipoAdscripcion;
	}

	public List<SelectItem> getTipoSistemaCarrera() {
		return getIParametricasCarrera(TipoParametro.TIPO_SISTEMA_CARRERA);
	}

	public void setTipoSistemaCarrera(List<SelectItem> tipoSistemaCarrera) {
		this.tipoSistemaCarrera = tipoSistemaCarrera;
	}

	public List<SelectItem> getEntidadPostConflicto() {
		return getIParametricas(TipoParametro.ENTIDAD_POST_CONFLICTO);
	}

	public void setEntidadPostConflicto(List<SelectItem> entidadPostConflicto) {
		this.entidadPostConflicto = entidadPostConflicto;
	}

	public List<SelectItem> getPersoneriaJuridica() {
		return getIParametricas(TipoParametro.PERSONERIA_JURIDICA);
	}

	public void setPersoneriaJuridica(List<SelectItem> personeriaJuridica) {
		this.personeriaJuridica = personeriaJuridica;
	}

	public List<SelectItem> getLstAdmonRecursosContratos() {
		return getIParametricas(TipoParametro.TIPO_ADMON_RECUROS_CONTRATO);
	}

	public void setLstAdmonRecursosContratos(List<SelectItem> lstAdmonRecursosContratos) {
		this.lstAdmonRecursosContratos = lstAdmonRecursosContratos;
	}

	public List<SelectItem> getLstMaestroEstadosEntidad() {
		return getIParametricas(TipoParametro.MAESTRO_ESTADOS_ENTIDAD);
	}

	public void setLstMaestroEstadosEntidad(List<SelectItem> lstMaestroEstadosEntidad) {
		this.lstMaestroEstadosEntidad = lstMaestroEstadosEntidad;
	}

	/**
	 * Metodo para traer las dependencias de una entidad
	 * 
	 * @param codEntidad
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getDependenciaEntidadFilter(long codEntidad) {
		List<SelectItem> list = new ArrayList<>();
		DependenciaEntidadExt ent = new DependenciaEntidadExt();
		ent.setCodEntidad(codEntidad);
		ent.setFlgActivo((short) 1);
		List<DependenciaEntidadExt> listP = ComunicacionServiciosVin.getDependenciaEntidadFilter(ent);

		try {
			if (!listP.isEmpty()) {
				for (DependenciaEntidadExt aux : listP) {
					list.add(new SelectItem(aux.getCodDependenciaEntidad(), aux.getNombreDependencia()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * 1 feb. 2019
	 * WebUtils.java
	 * @param codEntidad
	 * @return
	 */
	public List<SelectItem> getDependenciaEntidadEspecial(long codEntidad) {
		List<SelectItem> list = new ArrayList<>();
		DependenciaEntidadExt ent = new DependenciaEntidadExt();
		ent.setCodEntidad(codEntidad);
		ent.setFlgActivo((short) 1);
		List<DependenciaEntidadExt> listP = ComunicacionServiciosVin.getDependenciaEntidadFilter(ent);
		try {
			if (!listP.isEmpty()) {
				for (DependenciaEntidadExt aux : listP) {
					if(aux.getFlgEspecial() == 1) {
						list.add(new SelectItem(aux.getCodDependenciaEntidad(), aux.getNombreDependencia().toUpperCase()));
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	public List<SelectItem> getTiposNombramiento(Long idPadre) {
		if (idPadre == null) {
			return new ArrayList<>();
		}
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosSis.getParametricaPorIdPadre(idPadre);

		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getNombreParametro().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	public List<SelectItem> getClasificacionPlantaByEntidad() {
		return clasificacionPlantaByEntidad;
	}

	public void setClasificacionPlantaByEntidad(List<SelectItem> clasificacionPlantaByEntidad) {
		this.clasificacionPlantaByEntidad = clasificacionPlantaByEntidad;
	}

	/**
	 * Metodo para traer las clasificaciones de planta por medio de la entidad y
	 * mostrar en un select
	 * 
	 * @param id
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getClasificacionPlantaByEntidad(long codEntidad) {
		
		List<SelectItem> list = new ArrayList<>();
		EntidadPlantaExt ent = new EntidadPlantaExt();
		ent.setCodEntidad(codEntidad);
		ent.setFlgActivo((short) 1);
		List<EntidadPlantaExt> listP = ComunicacionServiciosVin.getClasificacionPlantaByEntidad(ent);

		try {
			if (!listP.isEmpty()) {
				for (EntidadPlantaExt aux : listP) {
					if(aux.getCodClasificacionPlanta() != TipoParametro.CLASIFICACION_PLANTA_UTL.getValue() ) {
						list.add(new SelectItem(aux.getCodClasificacionPlanta(), aux.getNombreClasificacionPlanta().toUpperCase()));
					}
				}
			}
		} catch (NullPointerException ex) {
			WebUtils.logger.error("Error public List<SelectItem> getClasificacionPlantaByEntidad(long codEntidad) WebUtils.java", ex.getMessage() + " " + ex.getStackTrace());
			return new ArrayList<>();
		}
		return list;
	}

    public List<SelectItem> getCategoriaDNP() {
		return getIParametricas(TipoParametro.CATEGORIA_DNP);
	}
    
    public List<SelectItem> getCategoriaMunicipio(){
    	List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosHV.getParametrica(TipoParametro.CATEGORIA_MUNICIPIO.getValue());
		Collections.sort(listP, new Comparator<Parametrica>(){
		     public int compare(Parametrica o1, Parametrica o2){
		         if(o1.getCodTablaParametrica() == o2.getCodTablaParametrica())
		             return 0;
		         return o1.getCodTablaParametrica().intValue() < o2.getCodTablaParametrica().intValue() ? -1 : 1;
		     }
		});
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if(aux.getFlgEstado() == 1) {
						if(aux.getCodTablaParametrica().intValue() != 1459) {
							list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getNombreParametro().toUpperCase()));
						}
					}
					
				}
			}
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}
    	return list;
    }
    
    
	/**
	 * Metodo para traer las clasificaciones de planta por medio de la entidad y
	 * mostrar en un select
	 * 
	 * @param id
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getClasePlantaByEntidad(long codEntidad, Integer codClasificacionPlanta) {
		List<SelectItem> list = new ArrayList<SelectItem>();
		EntidadPlantaExt ent = new EntidadPlantaExt();
		if(codClasificacionPlanta!=null) {
			ent.setCodClasificacionPlanta(codClasificacionPlanta.longValue());
		}
		ent.setCodEntidad(codEntidad);
		ent.setFlgActivo((short) 1);
		List<EntidadPlantaExt> listP = ComunicacionServiciosVin.getClasePlantaByEntidad(ent);

		try {
			if (!listP.isEmpty()) {
				for (EntidadPlantaExt aux : listP) {
					if(aux.getCodClasePlanta() != TipoParametro.CLASE_PLANTA_UTL.getValue()) {
						list.add(new SelectItem(aux.getCodClasePlanta(), aux.getNombreClasePlanta().toUpperCase()));
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * Metodo para traer los cargos de una entidad segï¿½n la planta
	 * 
	 * @param codEntidad
	 * @param codClasificacionPlanta
	 * @param codClasePlanta
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getCargoPlanta(long codEntidad, long codClasificacionPlanta, long codClasePlanta) {
		List<SelectItem> list = new ArrayList<>();
		EntidadPlantaDetalleExt ent = new EntidadPlantaDetalleExt();
		ent.setCodClasificacionPlanta(codClasificacionPlanta);
		ent.setCodEntidad(codEntidad);
		ent.setCodClasePlanta(codClasePlanta);
		ent.setFlgActivo((short) 1);
		List<EntidadPlantaDetalleExt> listP = ComunicacionServiciosVin.getCargoPlanta(ent);

		try {
			if (!listP.isEmpty()) {
				for (EntidadPlantaDetalleExt aux : listP) {
					list.add(new SelectItem(aux.getCodEntidadPlantaDetalle(), aux.getNombreCargo()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * Metodo para traer los cargos de una entidad segï¿½n la planta
	 * 
	 * @param codEntidad
	 * @param codClasificacionPlanta
	 * @param codClasePlanta
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getCargosFuncionario(long codPersona) {
		List<SelectItem> list = new ArrayList<>();
		List<EntidadPlantaDetalleExt> listP = ComunicacionServiciosVin.getcargosfuncionario(codPersona, 0);

		try {
			if (!listP.isEmpty()) {
				for (EntidadPlantaDetalleExt aux : listP) {
					list.add(new SelectItem(aux.getCodVinculacion(), aux.getNombreCargo().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}

	/**
	 * @return the causalDesvinculacion
	 */
	public List<SelectItem> getCausalDesvinculacion() {
		return getIParametricasValor(TipoParametro.CAUSAL_DESVINCULACION);
	}

	/**
	 * @param causalDesvinculacion
	 *            the causalDesvinculacion to set
	 */
	public void setCausalDesvinculacion(List<SelectItem> causalDesvinculacion) {
		this.causalDesvinculacion = causalDesvinculacion;
	}

	public List<SelectItem> getListasParametricas(Long idPadre) {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosSis.getParametricaPorIdPadre(idPadre);
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) 
					list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getNombreParametro().toUpperCase()));
			}
		}catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	private List<SelectItem> getIParametricasValor(TipoParametro type) {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosHV.getParametrica(type.getValue());
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) 
					list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getValorParametro().toUpperCase()));
			}
		} catch (NullPointerException e) {
			return new ArrayList<>();
		}

		return list;
	}

	public List<SelectItem> getNorma(Long codTipoNorma){
    	List<SelectItem> list = new ArrayList<>();
		
    	if(codTipoNorma != null) {
    	
	    	List<Norma> listN = ComunicacionServiciosEnt.getNorma(codTipoNorma);
			try {	
			    if (!listN.isEmpty()) {
					for (Norma aux : listN) {
					    if(!aux.isError()) {
							list.add(new SelectItem(aux.getCodTipoNorma(), aux.getNumeroNorma()));
					    }
					}
			    }
			} catch (NullPointerException e) {
			    e.getStackTrace();
			    return new ArrayList<>();
			}
    	}
	
		return list;
    }
	
	public List<SelectItem> getTiposDeclaracion() {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosSis.getParametricaPorIdPadre(740);
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if(aux.getCodTablaParametrica().longValue() != 742L)
						list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getNombreParametro().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * 
	 * @param codEntidadPublica
	 * @return
	 */
	public List<SelectItem> getCargosEntidad(long codEntidad) {
		List<SelectItem> list = new ArrayList<>();
		List<Cargo> listP = ComunicacionServiciosSis.getCargosEntidad(codEntidad);
		try {
			if (!listP.isEmpty()) {
				for (Cargo aux : listP) {
					list.add(new SelectItem(aux.getCodCargo(), aux.getNombreCargo().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	public List<SelectItem> getDenominacionesEntidad() {
		List<SelectItem> list = new ArrayList<>();
		Denominacion buscador = new Denominacion();
		List<Denominacion> listP = ComunicacionServiciosEnt.getDenomincacionesFiltro(buscador);
		try {
			if (!listP.isEmpty()) {
				for (Denominacion aux : listP) {
					list.add(new SelectItem(aux.getCodDenominacion(), aux.getNombreDenominacion().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	

	
	public List<SelectItem> getRolBase() {
		List<SelectItem> list = new ArrayList<>();
		List<Rol> listP = ComunicacionServiciosSis.getRolBase();
		try {
			if (!listP.isEmpty()) {
				for (Rol aux : listP) {
					list.add(new SelectItem(aux.getCodRol(), aux.getNombre()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		
		return list;
	}

	/**
	 * RETORNA LOS TIPOS DE NOMBRAMIENTO SEGUN LA NATURALEZA DE LOS EMPLEOS.
	 * @param codPadreParametrica
	 * @return
	 */
	public List<SelectItem> getTipoNombramiento(long codPadreParametrica) {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosSis.getParametricaPorIdPadre(codPadreParametrica);
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if(aux.getFlgEstado() == 1) {
						list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getNombreParametro().toUpperCase()));
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		return list;
	}
	
	

	/**
	 * Metodo que obtiene lista de dependencias hoja de Vida de la tabla maestro dependencia_hoja_vida
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getDependenciasHV() {
		List<SelectItem> list = new ArrayList<>();
		DependenciaHojaVidaExt filtroDependenciaHV = new DependenciaHojaVidaExt();
		filtroDependenciaHV.setFlgActivo((short)1);
		List<DependenciaHojaVidaExt> lstDependenciaHV = ComunicacionServiciosSis.getDependenciaHVFiltro(filtroDependenciaHV);
		try {
			if (!lstDependenciaHV.isEmpty()) {
				for (DependenciaHojaVidaExt aux : lstDependenciaHV) {
					list.add(new SelectItem(aux.getCodDependenciaHojaVida(), aux.getNombreDependencia().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	/**
	 * @return the tipoPoliticaPublica
	 */
	public List<SelectItem> getTipoPoliticaPublica() {
		return getIParametricasValor(TipoParametro.ENTIDAD_TIPO_POLITICA);
	}

	/**
	 * @param tipoPoliticaPublica the tipoPoliticaPublica to set
	 */
	public void setTipoPoliticaPublica(List<SelectItem> tipoPoliticaPublica) {
		this.tipoPoliticaPublica = tipoPoliticaPublica;
	}

	/**
	 * @return the tipoNorma
	 */
	public List<SelectItem> getTipoNorma() {
		return getIParametricasValor(TipoParametro.TIPO_NORMA);
	}

	/**
	 * @param tipoNorma the tipoNorma to set
	 */
	public void setTipoNorma(List<SelectItem> tipoNorma) {
		this.tipoNorma = tipoNorma;
	}

	/**
	 * @return the tipoPlanta
	 */
	public List<SelectItem> getTipoPlanta() {
		return getIParametricasValor(TipoParametro.TIPO_PLANTA);
	}

	/**
	 * @param tipoPlanta the tipoPlanta to set
	 */
	public void setTipoPlanta(List<SelectItem> tipoPlanta) {
		this.tipoPlanta = tipoPlanta;
	}

	/**
	 * @return the lstAdscVinculada
	 */
	public List<SelectItem> getLstAdscVinculada() {
		return getIParametricas(TipoParametro.ADSCRITA_VINCULADA);
	}

	/**
	 * @param lstAdscVinculada the lstAdscVinculada to set
	 */
	public void setLstAdscVinculada(List<SelectItem> lstAdscVinculada) {
		this.lstAdscVinculada = lstAdscVinculada;
	}
	
	public List<SelectItem> getTiposDependenciaEspecial(){
		return getIParametricas(TipoParametro.ENTIDAD_TIPOS_DEPENDENCIAS);
	}
	
	public List<SelectItem> getEntidadGradosNomenclatura(){
		return getIParametricas(TipoParametro.ENTIDAD_NOMENCLATURA_GRADOS);
	}

	
	public List<SelectItem> getGradosClasificacionOrganica(boolean organoAutonomo){
		if(organoAutonomo) {
			return getIParametricas(TipoParametro.ENTIDAD_NOMENCLATURA_GRADOS);
		}
		
		return getIParametricaOnlyNumero(TipoParametro.ENTIDAD_NOMENCLATURA_GRADOS);
	}
	
	/**
	 * @return the tiposAutonomia
	 */
	public List<SelectItem> getTiposAutonomia() {
		return getIParametricas(TipoParametro.AUTONOMIA);
	}

	/**
	 * @param tiposAutonomia the tiposAutonomia to set
	 */
	public void setTiposAutonomia(List<SelectItem> tiposAutonomia) {
		this.tiposAutonomia = tiposAutonomia;
	}

	/**
	 * @return the lstRolBase
	 */
	public List<SelectItem> getLstRolBase() {
		return getRolBase();
	}

	/**
	 * @param lstRolBase the lstRolBase to set
	 */
	public void setLstRolBase(List<SelectItem> lstRolBase) {
		this.lstRolBase = lstRolBase;
	}

	/**
	 * @return the lstSubEstadoentidad
	 */
	public List<SelectItem> getLstSubEstadoentidad() {
		return getIParametricas(TipoParametro.SUB_ESTADO_ENTIDAD);
	}

	/**
	 * @param lstSubEstadoentidad the lstSubEstadoentidad to set
	 */
	public void setLstSubEstadoentidad(List<SelectItem> lstSubEstadoentidad) {
		this.lstSubEstadoentidad = lstSubEstadoentidad;
	}



	/**
	 * @return the lstCodigoDenominacion
	 */
	public final List<SelectItem> getLstCodigoDenominacion() {
		return getIParametricas(TipoParametro.CODIGO_DENOMINACION);
	}



	/**
	 * @param lstCodigoDenominacion the lstCodigoDenominacion to set
	 */
	public final void setLstCodigoDenominacion(List<SelectItem> lstCodigoDenominacion) {
		this.lstCodigoDenominacion = lstCodigoDenominacion;
	}



	/**
	 * @return the tipoNombramiento
	 */
	public List<SelectItem> getTipoNombramiento() {
		return getIParametricas(TipoParametro.TIPO_NOMBRAMIENTO);
	}



	/**
	 * @param tipoNombramiento the tipoNombramiento to set
	 */
	public void setTipoNombramiento(List<SelectItem> tipoNombramiento) {
		this.tipoNombramiento = tipoNombramiento;
	}
	
	/**
	 * Metodo para traer los cargos de la entidad
	 * Para el componente de Hoja de vida
	 * @param codEntidad
	 * @param codClasificacionPlanta
	 * @param codClasePlanta
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getCargoAprobar(long codEntidad) {
		List<SelectItem> list = new ArrayList<>();
		NomenclaturaDenominacionExt ent = new NomenclaturaDenominacionExt();
		ent.setCodEntidad(codEntidad);
		ent.setFlgActivo((short) 1);
		List<NomenclaturaDenominacionExt> listP = ComunicacionServiciosEnt.getCargoAprobarHojaVida(ent);

		try {
			if (!listP.isEmpty()) {
				for (NomenclaturaDenominacionExt aux : listP) {
					list.add(new SelectItem(aux.getCodNomenclaturaDenominacion(), aux.getNombreCargo().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	
	/**
	 * Metodo que obtiene de la planta de UTLs la bolsa de UTLs/UAN con la lista de responsables de cada uno
	 * 
	 * */
	public static List<SelectItem> getUtlUan(BigDecimal codEntidad) {
		if(codEntidad == null)
			return new ArrayList<>();
		List<SelectItem> list = new ArrayList<>();
		PlantaPersonaUtlUanExt objPlanta = new PlantaPersonaUtlUanExt();
		objPlanta.setFlgActivo((short)1);
		objPlanta.setCodEntidad(codEntidad);
		objPlanta.setFlgGuardadoParcialPlanta((short)0);
		List<PlantaPersonaUtlUanExt> plantas= ComunicacionServiciosEnt.obtenerPlantaPersonaUTLFiltro(objPlanta);
		try {
			if (plantas != null && !plantas.isEmpty()) {
				for (PlantaPersonaUtlUanExt aux : plantas){
					if(aux.getCodPersona()!=null && aux.getNombreResponsable()!=null) {
						list.add(new SelectItem(aux.getCodPlantaPersonaUtlUan(), aux.getNombreResponsable().toUpperCase()));
					}
				}
			}
		}catch (NullPointerException ex) {
			logger.error("public List<SelectItem> getUtlUan() error: " + ex.getMessage(), ex);
			return new ArrayList<>();
		}
		catch(Exception ex) {
			logger.error("public List<SelectItem> getUtlUan() WebUtils.java error: " + ex.getMessage(), ex);
			return new ArrayList<>();
		}
		return list;
	}


	/**
	 * Metodo para traer los cargos de la entidad
	 * Para el componente de Hoja de vida
	 * @param codEntidad
	 * @param codClasificacionPlanta
	 * @param codClasePlanta
	 * @return List<SelectItem>
	 */
	public List<SelectItem> getCargoAsociadosNomenclatura() {
		List<SelectItem> list = new ArrayList<>();
		NomenclaturaDenominacionExt ent = new NomenclaturaDenominacionExt();
		ent.setFlgActivo((short) 1);
		List<NomenclaturaDenominacionExt> listP = ComunicacionServiciosEnt.getCargoAprobarHojaVida(ent);

		try {
			if (!listP.isEmpty()) {
				for (NomenclaturaDenominacionExt aux : listP) {
					list.add(new SelectItem(aux.getCodNomenclaturaDenominacion(), aux.getNombreCargo().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	/**
	 *  Metodo lista las denominaciones con un nivel especifico usadas en una nomenclatura 
	 * @param codNomenclatura
	 * @return List<SelectItem>
	 */	
	public List<SelectItem> getDenominacionesNomenclaturaEntidad(Long codNivel, Long codNomenclatura) {
		List<SelectItem> list = new ArrayList<>();
		if(codNivel != null && codNomenclatura != null ) {
			DenominacionExt buscador = new DenominacionExt(); 
			buscador.setFlgActivo((short) 1);
			buscador.setCodNomenclatura(BigDecimal.valueOf(codNomenclatura));
			buscador.setCodNivelJerarquico(codNivel);
			List<DenominacionExt> listP = ComunicacionServiciosEnt.getDenominacionPorNivelNomenclatura(buscador);
			try {
				if (!listP.isEmpty()) {
					for (DenominacionExt aux : listP) {
						list.add(new SelectItem(aux.getCodDenominacion(), aux.getNombreDenominacion()));
					}
				}
			} catch (NullPointerException e) {
				e.getStackTrace();
				return new ArrayList<>();
			}

		}	
		return list;
	}
	/**
	 * Metodo para traer las nomenclaturas de la entidad
	 * Para el componente de gestionar planta
	 * @param codEntidad
	 * @return List<SelectItem>
	 */	
	public List<SelectItem> getNomenclaturaEntidad(Long codEntidad) {
		List<SelectItem> list = new ArrayList<>();
		if(codEntidad != null) {
			NomenclaturaExt nomenclatura = new NomenclaturaExt();
			nomenclatura.setCodEntidad(new BigDecimal(codEntidad));
			nomenclatura.setFlgActivo((short) 1);
			nomenclatura.setFlgDefinitivo((short) 1);
			List<NomenclaturaExt> listP = ComunicacionServiciosEnt.getNomenclaturaFiltro(nomenclatura);
			try {
				if (!listP.isEmpty()) {
					for (NomenclaturaExt aux : listP) {
						list.add(new SelectItem(aux.getCodNomenclatura(), aux.getNombreNomenclatura().toUpperCase()));
					}
				}
			} catch (NullPointerException e) {
				e.getStackTrace();
				return new ArrayList<>();
			}
		}
		return list;
	}
	
	public List<SelectItem> getCargoAsociadosEntidadNomenclaturaNomenclatura(Long codEntidad) {
		List<SelectItem> list = new ArrayList<>();
		NomenclaturaDenominacionExt ent = new NomenclaturaDenominacionExt();
		ent.setFlgActivo((short) 1);
		if(codEntidad!=null)
			ent.setCodEntidad(codEntidad);
		else
			ent.setCodEntidad((long) 0);
		List<NomenclaturaDenominacionExt> listP = ComunicacionServiciosEnt.getCargoAprobarHojaVida(ent);
		try {
			if (!listP.isEmpty()) {
				for (NomenclaturaDenominacionExt aux : listP) {
					list.add(new SelectItem(aux.getCodNomenclaturaDenominacion(), aux.getNombreCargo().toUpperCase()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}	

	/**
	 * Metodo que retorna las personas que estan vinculadas a una entidad
	 * @return
	 */
	public List<SelectItem> getPersonasVinculadasEntidad(Long codEntidad) {
		List<SelectItem> list = new ArrayList<>();
		PersonaExt objPersona = new PersonaExt();
		if(codEntidad != null)
			objPersona.setCodEntidad(new BigDecimal(codEntidad));
		objPersona.setFlgActivo((short)1);
		objPersona.setFlgEstado((short)1);
		List<PersonaExt> listP = ComunicacionServiciosVin.getPersonasVinculadas(objPersona);
		try {
			if (!listP.isEmpty()) {
				for (PersonaExt aux : listP) {
					list.add(new SelectItem(aux.getCodPersona(), aux.getNombrePersonaCompleto()));
				} 
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return list;
	}
	
	
	
	/**
	 * @return the tipoIngresoBR
	 */
	public List<SelectItem> getTipoIngresoBR() {
		 return getIParametricas(TipoParametro.TIPO_INGRESO_BR);
	}



	/**
	 * @param tipoIngresoBR the tipoIngresoBR to set
	 */
	public void setTipoIngresoBR(List<SelectItem> tipoIngresoBR) {
		this.tipoIngresoBR = tipoIngresoBR;
	}



	public List<SelectItem> getLstDocumentosAdicionales() {
		return getIParametricas(TipoParametro.TIPO_DOCUMENTO_ADICIONAL_HV);
	}



	public void setLstDocumentosAdicionales(List<SelectItem> lstDocumentosAdicionales) {
		this.lstDocumentosAdicionales = lstDocumentosAdicionales;
	}
	
	public List<SelectItem> getLstMaestroEstadosGestionEntidad() {
		return getIParametricas(TipoParametro.ESTADOS_GESTION_ENTIDAD);
	}
	
	/**
	 * @return the clasePlanta
	 */
	public List<SelectItem> getClasePlanta() {
		return getIParametricas(TipoParametro.CLASE_PLANTA);
	}



	/**
	 * @param clasePlanta the clasePlanta to set
	 */
	public void setClasePlanta(List<SelectItem> clasePlanta) {
		this.clasePlanta = clasePlanta;
	}
	
	
	
	/**
	 * @return the clasificacionPlanta
	 */
	public List<SelectItem> getClasificacionPlanta() {
		return getIParametricas(TipoParametro.CLASIFICACION_PLANTA);
	}



	/**
	 * @param clasificacionPlanta the clasificacionPlanta to set
	 */
	public void setClasificacionPlanta(List<SelectItem> clasificacionPlanta) {
		this.clasificacionPlanta = clasificacionPlanta;
	}



	public void cargarParametros() {
		getLstDocumentosAdicionales();
		getTipoIngresoBR();
		getTipoNombramiento();
		getLstCodigoDenominacion();
		getLstSubEstadoentidad();
		getTiposAutonomia();
		getEntidadGradosNomenclatura();
		getTiposDependenciaEspecial();
		getLstAdscVinculada();
		getTipoPlanta();
		getTipoNorma();
		getTipoPoliticaPublica();
		getDenominacionesEntidad();
		getTiposDeclaracion();
		getCausalDesvinculacion();
		getCategoriaMunicipio();
		getCategoriaDNP();
		getLstMaestroEstadosEntidad();
		getLstAdmonRecursosContratos();
		getPersoneriaJuridica();
		getEntidadPostConflicto();
		getTipoSistemaCarrera();
		getTipoAdscripcion();
		getTipoCategoriaEntidad();
		getTipoNivelAdministrativo();
		getTipoSectorAdministrativo();
		getTipoClasificacionOrganica();
		getTipoSubordenEntidad();
		getTipoOrdenTerritorial();
		getTiposNormaEntidad();
		getFormaBeneficiosBR();
		getDescripcionActividadBR();
		getLstTiposProcesosCarguesMasivos();
		getLstEstadosCargueMasivo();
		getDataExample();
		getLstTiposModificacionContratos();
		getTipoModalidad();
		getCalidadSocioBr();
		getCalidadMiembroBr();
		getTipoVinculacionJunta();
		getTipoCuenta();
		getParentesco();
		getLstMotivosSuspensionContrato();
		getTipoFuentesFinanciacion();
		getTipoMonedas();
		getTipoDeclaracion();
		getTipoIngreso();
		getTipoBien();
		getFormaParticipacion();
		getModalidadEstudio();
		getMedioCApacitacion();
		getLibroResultadoInvestigacion();
		getDemasTiposProduccionBiblografica();
		getVictimaDesplazamiento();
		getCodTipoEvaluacion();
		getCodNivelCumplimiento();
		getNivelJerarquico();
		getTipoInstitucion();
		getCabezaFamilia();
		getTipoZona();
		getOrientacionSexual();
		getNivelIdioma();
		getTipoArticulo();
		getClaseLibreta_mil();
		getRegimenSalarial();
		getNaturalezaEmpleo();
		getReporte();
		getPoblacionEtnica();
		getTiposEntidad();
		getTiposAsociacion();
		getEstadoCivil();
		getGenero();
		getFactorRh();
		getTiposDeIdentificacion();
		getTipoDiscapacidad();
		getMotivoRetiro();
		getJornadaLaboral();
		getAreaConocimiento();
		getNivelEducativo();
		getTipoNaturalezaJuridica();
		getClasePlanta();
		getClasificacionPlanta();
	}
	
	
	public static String getUrlFile(String ruta) {
		try {
			if(ruta != null) {
				String url = "";
				String base = "/getShowFile";
				System.out.println(ruta.indexOf(base));
				if(ruta.indexOf(base) < 0) {
					ruta = new String(Base64.encodeBase64(ruta.getBytes()));
					return WS_MULTIMEDIA_SHOW+"/loadFile/"+ruta;
				}else {
					return WS_MULTIMEDIA_SHOW+""+ruta;
				}
			}else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * Creado Por: Jose viscaya
	 * @param ruta
	 * @return
	 * Nov 28, 2019 2:23:42 PM
	 * WebUtils.java
	 */
	@Deprecated
	public static String getUrlFileHadoo(String ruta, String docIdentidad, String codigoRegistro, String tipo) {
		if(ruta != null) {
			String url = "";
			String base = "/getShowFile";
			System.out.println(ruta.indexOf(base));
			if(ruta.indexOf(base) < 0) {
				if(WS_UPLOAD_HADOOP_ONLINE_OFF.equals(HADOOP_ON)) {
					if(docIdentidad == null) {
						return null;
					}
					if(codigoRegistro == null) {
						return null;
					}
					if(tipo == null) {
						return null;
					}
					url = ConnectionHttp.sendGetHadoop(WS_UPLOAD_HADOOP_ONLINE, ruta, docIdentidad, codigoRegistro, tipo);
					if(url == null) {
						return null;
					}
					return WS_MULTIMEDIA_SHOW+""+url;
				}else {
					return null;
				}
			}else {
				return WS_MULTIMEDIA_SHOW+""+ruta;
			}
		}else {
			return null;
		}
		
	}
	/**
	 * 
	 * Creado Por: Jose viscaya
	 * @param ruta
	 * @return
	 * Nov 28, 2019 4:24:31 PM
	 * WebUtils.java
	 */
	public static String validarUrl(String ruta, String docIdentidad, String codigoRegistro, String tipo) {
		if(WebUtils.getUrlFile(ruta) == null) {
			return null;
		}
//		if(WebUtils.getUrlFileHadoop(ruta, docIdentidad, codigoRegistro, tipo) == null) {
//			return null;
//		}
		return ruta;
	}
	
	public static String stripAccents(String str) {
		if (str == null) {
		    return null;
		}
		char[] array = str.toCharArray();
		for (int index = 0; index < array.length; index++) {
		    int pos = ORIGINAL.indexOf(array[index]);
		    if (pos > -1) {
		        array[index] = REPLACEMENT.charAt(pos);
		    }
		}
		return new String(array);
    }
	
}