package co.gov.dafp.sigep2.mbean;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.mbean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.Direccion;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

@Named
@ViewScoped
@ManagedBean
public class DatosPersonalesBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 3695472896249884567L;

	private static final Logger logger = Logger.getInstance(DatosPersonalesBean.class);

	private PersonaExt persona;// recibe el objeto PersonaExt
	private DatoContactoExt datosContacto;// recibe el objeto DatoContactoExt
	private DatoAdicionalExt datosAdicionales;// recibe el objeto DatoAdicionalExt
	private List<PaisDTO> nacionalidad;// recibe una lista de objetos de PaisDTO
	private EditarDireccionDTO editarDireccion;// recibe el objeto EditarDireccionDTO
	private HistoricoModificacionHojaVida modificacionHojaVida;// recibe el objeto HistoricoModificacionHojaVida
	private Pais paisNacimiento;// recibe el objeto Pais
	private Pais paisNacimientoAnterior;// recibe el objeto Pais

	private int changedTab, tabGeneralHojaVida, tabEducacion;
	private boolean mostratDatosPorPaisResidencia;
	private boolean mostratDatosPorPaisNacionalidad;
	private boolean mostratEditarDireccion;
	private boolean requiredExt 	= false;
	private boolean editado 		= false;
	private UploadedFile cargarDodumento 				= null;
	private UploadedFile cargarlibretaMilitar 			= null;
	private UploadedFile cargarDocumentoDiscapacidad 	= null;
	private Long codPersona 		= null;
	private Integer codFormulario 	= null;
	private Boolean flgValidadoDocumento 	= Boolean.FALSE;
	private Boolean flgValidadoLibreta 		= Boolean.FALSE;
	private Boolean flgDireccionRequerida 	= Boolean.FALSE;
	private Boolean flgValidadoDiscapacidad	= Boolean.FALSE;

	private String nombreArchivoDocumento;
	private String nombreArchivoLibreta;
	private String nombreArchivoDiscapacidad;

	private String banderaPais = "/resources/images/banderas/default.png";
	private boolean estadoBoton;
	private boolean estadoText;
	private boolean estadoBotonTextGen;
	private boolean expuestoPublicamente;
	private String rutaArchivo = null;
	private BigDecimal maxExt = new BigDecimal(2169);
	private BigDecimal maxNumOf = new BigDecimal(2168);
	private BigDecimal minNumOf = new BigDecimal(2201);
	
	private BigDecimal maxExtF;
	private BigDecimal maxNumOfF;
	private BigDecimal minNumOfF;
	
	/*Cambio entre tabs*/
	private Integer tabIndexModDatosPersonales, tabIndexModHVGeneral,tabIndexModEducacion;
	private static String DATO_MODIFICADO="1";
	UIOutput uiDatosModificados;
	private boolean lbISGestionarHV;
	/*Cambio entre tabs Gerencia Pública*/
	private int tabGerenciaPublica, tabIndexModGerenciaPublica;
	/*Otros datos adicionales*/
	private boolean flgOtraOrientacion;
	/*Datos adicionales oculta/muestra "usted es"*/
	private boolean flgOcultaOrientacionSexual;
	Long codParametricaOcultaOrientacionSexual;
	/*Datos adicionales que muestra redees sociales*/
	private boolean blnMostrarRedesSociales;
	
	/*Oculta/ muestra TAB de datos adicionales*/
	private boolean blnMostrarTabDatosAdicionales;
	
	
	/*Documenetos Adicionales*/
	private int tabDocumentosAdicionales, tabIndexDocumentosAdicionales;
	
	private int progresoHojadeVida;
	
	/*Se fusiona componente de consulta de hoja de vida*/
	private boolean lbISGestionarSoloConsultaHV;
	private boolean lblModificarDatosPersonales;
	
	static final long DIAS_ANIO 	= 360; // variable para obtener los días de un año
	static final long DIAS_MES 		= 30; // variable para obtener los días de un mes
	static final long MESES_ANIO 	= 12; 
	public final static String  FORMATO_FECHA 	= "dd/MM/yyyy";
	public final static Double HORAS_LABORAL_DIA = 9.0d;
	public final static Double HORAS_MEDIO_TIEMPO = 4.5d;
	private static final int HORAS_LIMITE = 188;
	private static Integer tiempoMaximoMensual; 
	
	private String strPageSubtitle;
	private final String TTL_SUB_MI_HOJA_VIDA ="TTL_SUB_MI_HOJA_VIDA";
	
	static final long COD_JEFE_CONTROL_INTERNO = 15; // variable para envio de correos desde modificar HV
	
	private HojaVidaExt hojaVida = new HojaVidaExt();
	
	public static String HADOOP_OPCION_CARGA 	="0";
	private static String HADOOP_USER_REPO 		="N";
	private static String HADOOP_URL_BY_SO 		="N";
	

	@Inject
    protected DireccionBean direccionBean;
	/**
	 * @return el flgDireccionRequerida
	 */
	public Boolean getFlgDireccionRequerida() {
		return flgDireccionRequerida;
	}

	/**
	 * @param flgDireccionRequerida
	 *            el flgDireccionRequerida a establecer
	 */
	public void setFlgDireccionRequerida(Boolean flgDireccionRequerida) {
		this.flgDireccionRequerida = flgDireccionRequerida;
	}

	public int getTabEducacion() {
		return tabEducacion;
	}

	public void setTabEducacion(int tabEducacion) {
		this.tabEducacion = tabEducacion;
	}

	public int getTabGeneralHojaVida() {
		return tabGeneralHojaVida;
	}

	public void setTabGeneralHojaVida(int tabGeneralHojaVida) {
		this.tabGeneralHojaVida = tabGeneralHojaVida;
	}

	/**
	 * @return the persona
	 */
	public PersonaExt getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	/**
	 * @return the datosContacto
	 */
	public DatoContactoExt getDatosContacto() {
		return datosContacto;
	}

	/**
	 * @param datosContacto
	 *            the datosContacto to set
	 */
	public void setDatosContacto(DatoContactoExt datosContacto) {
		this.datosContacto = datosContacto;
	}

	/**
	 * @return the datosAdicionales
	 */
	public DatoAdicionalExt getDatosAdicionales() {
		return datosAdicionales;
	}

	/**
	 * @param datosAdicionales
	 *            the datosAdicionales to set
	 */
	public void setDatosAdicionales(DatoAdicionalExt datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}

	/**
	 * @return the nacionalidad
	 */
	public List<PaisDTO> getNacionalidad() {
		return nacionalidad;
	}

	/**
	 * @param nacionalidad
	 *            the nacionalidad to set
	 */
	public void setNacionalidad(List<PaisDTO> nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	/**
	 * @return the editarDireccion
	 */
	public EditarDireccionDTO getEditarDireccion() {
		return editarDireccion;
	}

	/**
	 * @param editarDireccion
	 *            the editarDireccion to set
	 */
	public void setEditarDireccion(EditarDireccionDTO editarDireccion) {
		this.editarDireccion = editarDireccion;
	}

	/**
	 * @return the modificacionHojaVida
	 */
	public HistoricoModificacionHojaVida getModificacionHojaVida() {
		return modificacionHojaVida;
	}

	/**
	 * @param modificacionHojaVida
	 *            the modificacionHojaVida to set
	 */
	public void setModificacionHojaVida(HistoricoModificacionHojaVida modificacionHojaVida) {
		this.modificacionHojaVida = modificacionHojaVida;
	}

	/**
	 * @return the paisNacimiento
	 */
	public Pais getPaisNacimiento() {
		return paisNacimiento;
	}

	/**
	 * @param paisNacimiento
	 *            the paisNacimiento to set
	 */
	public void setPaisNacimiento(Pais paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
	}

	/**
	 * @return the paisNacimientoAnterior
	 */
	public Pais getPaisNacimientoAnterior() {
		return paisNacimientoAnterior;
	}

	/**
	 * @param paisNacimientoAnterior
	 *            the paisNacimientoAnterior to set
	 */
	public void setPaisNacimientoAnterior(Pais paisNacimientoAnterior) {
		this.paisNacimientoAnterior = paisNacimientoAnterior;
	}

	/**
	 * @return the changedTab
	 */
	public int getChangedTab() {
		return changedTab;
	}

	/**
	 * @param changedTab
	 *            the changedTab to set
	 */
	public void setChangedTab(int changedTab) {
		this.changedTab = changedTab;
	}

	/**
	 * @return the mostratDatosPorPaisResidencia
	 */
	public boolean isMostratDatosPorPaisResidencia() {
		return mostratDatosPorPaisResidencia;
	}

	/**
	 * @param mostratDatosPorPaisResidencia
	 *            the mostratDatosPorPaisResidencia to set
	 */
	public void setMostratDatosPorPaisResidencia(boolean mostratDatosPorPaisResidencia) {
		this.mostratDatosPorPaisResidencia = mostratDatosPorPaisResidencia;
	}

	/**
	 * @return the mostratDatosPorPaisNacionalidad
	 */
	public boolean isMostratDatosPorPaisNacionalidad() {
		return mostratDatosPorPaisNacionalidad;
	}

	/**
	 * @param mostratDatosPorPaisNacionalidad
	 *            the mostratDatosPorPaisNacionalidad to set
	 */
	public void setMostratDatosPorPaisNacionalidad(boolean mostratDatosPorPaisNacionalidad) {
		this.mostratDatosPorPaisNacionalidad = mostratDatosPorPaisNacionalidad;
	}

	/**
	 * @return the mostratEditarDireccion
	 */
	public boolean isMostratEditarDireccion() {
		return mostratEditarDireccion;
	}

	/**
	 * @param mostratEditarDireccion
	 *            the mostratEditarDireccion to set
	 */
	public void setMostratEditarDireccion(boolean mostratEditarDireccion) {
		this.mostratEditarDireccion = mostratEditarDireccion;
	}

	/**
	 * @return the requiredExt
	 */
	public boolean isRequiredExt() {
		return requiredExt;
	}

	/**
	 * @param requiredExt
	 *            the requiredExt to set
	 */
	public void setRequiredExt(boolean requiredExt) {
		this.requiredExt = requiredExt;
	}

	/**
	 * @return the editado
	 */
	public boolean isEditado() {
		return editado;
	}

	/**
	 * @param editado
	 *            the editado to set
	 */
	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	/**
	 * @return the cargarDodumento
	 */
	public UploadedFile getCargarDodumento() {
		return cargarDodumento;
	}

	/**
	 * @param cargarDodumento
	 *            the cargarDodumento to set
	 */
	public void setCargarDodumento(UploadedFile cargarDodumento) {
		this.cargarDodumento = cargarDodumento;
	}

	/**
	 * @return the cargarlibretaMilitar
	 */
	public UploadedFile getCargarlibretaMilitar() {
		return cargarlibretaMilitar;
	}

	/**
	 * @param cargarlibretaMilitar
	 *            the cargarlibretaMilitar to set
	 */
	public void setCargarlibretaMilitar(UploadedFile cargarlibretaMilitar) {
		this.cargarlibretaMilitar = cargarlibretaMilitar;
	}

	/**
	 * @return the codPersona
	 */
	public Long getCodPersona() {
		return codPersona;
	}

	/**
	 * @param codPersona
	 *            the codPersona to set
	 */
	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * @return the codFormulario
	 */
	public Integer getCodFormulario() {
		return codFormulario;
	}

	/**
	 * @param codFormulario
	 *            the codFormulario to set
	 */
	public void setCodFormulario(Integer codFormulario) {
		this.codFormulario = codFormulario;
	}

	/**
	 * @return the flgValidadoDocumento
	 */
	public Boolean getFlgValidadoDocumento() {
		return flgValidadoDocumento;
	}

	/**
	 * @param flgValidadoDocumento
	 *            the flgValidadoDocumento to set
	 */
	public void setFlgValidadoDocumento(Boolean flgValidadoDocumento) {
		this.flgValidadoDocumento = flgValidadoDocumento;
	}

	/**
	 * @return the flgValidadoLibreta
	 */
	public Boolean getFlgValidadoLibreta() {
		return flgValidadoLibreta;
	}

	/**
	 * @param flgValidadoLibreta
	 *            the flgValidadoLibreta to set
	 */
	public void setFlgValidadoLibreta(Boolean flgValidadoLibreta) {
		this.flgValidadoLibreta = flgValidadoLibreta;
	}

	/**
	 * @return the nombreArchivoDocumento
	 */
	public String getNombreArchivoDocumento() {
		return nombreArchivoDocumento;
	}

	/**
	 * @param nombreArchivoDocumento
	 *            the nombreArchivoDocumento to set
	 */
	public void setNombreArchivoDocumento(String nombreArchivoDocumento) {
		this.nombreArchivoDocumento = nombreArchivoDocumento;
	}

	/**
	 * @return the nombreArchivoLibreta
	 */
	public String getNombreArchivoLibreta() {
		return nombreArchivoLibreta;
	}

	/**
	 * @param nombreArchivoLibreta
	 *            the nombreArchivoLibreta to set
	 */
	public void setNombreArchivoLibreta(String nombreArchivoLibreta) {
		this.nombreArchivoLibreta = nombreArchivoLibreta;
	}

	/**
	 * @return the banderaPais
	 */
	public String getBanderaPais() {
		return banderaPais;
	}

	/**
	 * @param banderaPais
	 *            the banderaPais to set
	 */
	public void setBanderaPais(String banderaPais) {
		this.banderaPais = banderaPais;
	}

	/**
	 * @return the expuestoPublicamente
	 */
	public boolean isExpuestoPublicamente() {
		return expuestoPublicamente;
	}

	/**
	 * @param expuestoPublicamente
	 *            the expuestoPublicamente to set
	 */
	public void setExpuestoPublicamente(boolean expuestoPublicamente) {
		this.expuestoPublicamente = expuestoPublicamente;
	}

	/**
	 * @return the lblModificarDatosPersonales
	 */
	public boolean isLblModificarDatosPersonales() {
		return lblModificarDatosPersonales;
	}

	/**
	 * @param lblModificarDatosPersonales the lblModificarDatosPersonales to set
	 */
	public void setLblModificarDatosPersonales(boolean lblModificarDatosPersonales) {
		this.lblModificarDatosPersonales = lblModificarDatosPersonales;
	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String recursoId = paramMap.get("recursoId");
		
		maxExtF = ComunicacionServiciosSis.getParametricaporId(maxExt).getValorParametro() != null ? new BigDecimal(ComunicacionServiciosSis.getParametricaporId(maxExt).getValorParametro()) : new BigDecimal(0);
		setMaxNumOfF(ComunicacionServiciosSis.getParametricaporId(maxNumOf).getValorParametro()!= null ? new BigDecimal(ComunicacionServiciosSis.getParametricaporId(maxNumOf).getValorParametro()) :  new BigDecimal(0));
		minNumOfF = ComunicacionServiciosSis.getParametricaporId(minNumOf).getValorParametro() != null ? new BigDecimal(ComunicacionServiciosSis.getParametricaporId(minNumOf).getValorParametro()) :  new BigDecimal(0);

		lbISGestionarHV = false;
		String strIsLinkMenu = paramMap.get("isMenu");
        if(strIsLinkMenu != null && !"".equals(strIsLinkMenu) && "0".equals(strIsLinkMenu)){
        	lbISGestionarHV = true;
        }
        
        lbISGestionarSoloConsultaHV= false;
        String strISGestionarSoloConsultaHV = paramMap.get("isGestionSoloConsulta");
        if(strISGestionarSoloConsultaHV != null && !"".equals(strISGestionarSoloConsultaHV) && "1".equals(strISGestionarSoloConsultaHV)){
        	lbISGestionarSoloConsultaHV = true;
        }
        
        lblModificarDatosPersonales = false;
        String strGestionConsulta = paramMap.get("isGestionSoloConsulta");
        if(strGestionConsulta != null && !"".equals(strGestionConsulta) && "0".equals(strGestionConsulta)){
        	lblModificarDatosPersonales = true;
        }
		
		this.validaPermisosAcciones(recursoId);
		this.initialization();

		changedTab = 0;
		tabGeneralHojaVida = 0;
		
	
		String recursoIdGerencia = paramMap.get("opcionGerenciaPublica");	
		if(recursoIdGerencia!=null && !"".equals(recursoIdGerencia) && "2".equals(recursoIdGerencia)){
			tabGeneralHojaVida=5;/*Acceso desde el formulario de gerencia pública*/
		}
		
		tabEducacion = 0;
		try {
			persona 		= ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
			datosContacto 	= ComunicacionServiciosHV.getDatoContacto(codPersona);
			if(datosContacto!=null && datosContacto.getDireccionResidencia()!=null)
				setStrDireccionResidencia(datosContacto.getDireccionResidencia());
			datosAdicionales	 	= ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
			editarDireccion 		= new EditarDireccionDTO();
			modificacionHojaVida 	= new HistoricoModificacionHojaVida();
		} catch (NullPointerException e) {
			persona = new PersonaExt();
			datosContacto = new DatoContactoExt();
			datosAdicionales = new DatoAdicionalExt();
		}

		List<PaisDTO> nacionalidadDTO = null;

		if (this.getUsuarioSesion() != null) {
			try {
				nacionalidadDTO = HojaDeVidaDelegate.findNacionalidadByCodPersonaId(codPersona);
			} catch (SIGEP2SistemaException e) {
				logger.log().warn("void init() - datosAdicionalesDTO", e);
			}
		}

		try {
			if (datosContacto.getCodDatosContacto() == null) {
				datosContacto.setCodPersona(BigDecimal.valueOf(codPersona));
			}

			if (datosAdicionales.getCodDatoAdicional() == null) {
				datosAdicionales.setCodPersona(BigDecimal.valueOf(codPersona));
			}
		} catch (NullPointerException e) {
			return;
		}

		if (nacionalidadDTO != null) {
			this.setNacionalidad(nacionalidadDTO);
		}

		if (persona != null && persona.getExpuestoPoliticamente()) {
			expuestoPublicamente = true;
		}

		this.cambiarEstadoTextoBotonInit();
		this.mostratPanelDatosPorPaisResidencia(false);
		this.mostratPanelDatosPorPaisNacionalidad(false);
		this.agregarIndicativoDepartamento(false);
		if (persona != null && persona.getDocumentoIdentificacionUrl() != null
				&& !"".equals(persona.getDocumentoIdentificacionUrl()))
			this.setNombreArchivoDocumento("Identificacion.pdf");
		if (persona != null && persona.getLibretaMilitarUrl() != null && !"".equals(persona.getLibretaMilitarUrl()))
			this.setNombreArchivoLibreta("Libreta.pdf");
		
		if (persona != null && persona.getDocumentoDiscapacidadUrl() != null && !"".equals(persona.getDocumentoDiscapacidadUrl()))
			this.setNombreArchivoDiscapacidad("Discapacidad.pdf");
		
		this.validRequiredExt();
		this.mostrarOtraOrientacion();
		this.mostrarOcultarUstedEs();
		this.mostrarOcultarRedesSociales();
		this.mostrarOcultarTabDatosAdicionales();
		
		tabIndexModDatosPersonales 		= changedTab;
		tabIndexModHVGeneral 			= tabGeneralHojaVida;
		tabIndexModEducacion 			= tabEducacion;
		tabIndexModGerenciaPublica 		= tabGerenciaPublica;
		tabIndexDocumentosAdicionales 	= tabDocumentosAdicionales;
		
		refrescarProgresoHojaVida();
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_HOJAVIDA);
		
		/*se inicializa variable para carga de docuentos haddop*/
		Long codParametro = (long) TipoParametro.HADOOP_BY_SO.getValue();
		if(codParametro!=null && codParametro >0){
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(codParametro));
			if(parametrica!=null && parametrica.getValorParametro()!=null ){
				HADOOP_OPCION_CARGA = parametrica.getValorParametro();
			}
		}
		codParametro=null;
		codParametro = (long) TipoParametro.HADOOP_URL_BY_SO.getValue();
		if(codParametro!=null && codParametro >0){
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(codParametro));
			if(parametrica!=null && parametrica.getValorParametro()!=null ){
				HADOOP_URL_BY_SO = parametrica.getValorParametro();
			}
		}
		codParametro=null;
		codParametro = (long) TipoParametro.HADOOP_FOLDER_RAIZ.getValue();
		if(codParametro!=null && codParametro >0){
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(codParametro));
			if(parametrica!=null && parametrica.getValorParametro()!=null ){
				HADOOP_USER_REPO = parametrica.getValorParametro();
			}
		}	
		
		/*Inicializamos el encabezado de hoja de vida*/
		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
		hojaVidaFilter.setCodPersona( BigDecimal.valueOf(codPersona));
		hojaVidaFilter.setCodEntidad(getEntidadUsuario().getId());
		hojaVidaFilter.setFlgActivo(true);
		hojaVidaFilter.setLimitEnd(1);
		List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);
		if(listHojaVida!=null && listHojaVida.size()>=1)
			hojaVida = listHojaVida.get(0);
		
		try {
			Parametrica parTiempoMaximoMensual = ComunicacionServiciosVin.getParametricaporId(new BigDecimal(TipoParametro.TIEMPO_MES_LIMITE_LABORAL.getValue()));
			tiempoMaximoMensual = parTiempoMaximoMensual.getValorParametro() != null ? Integer.parseInt(parTiempoMaximoMensual.getValorParametro()) : HORAS_LIMITE;
		} catch (Exception e) {
			tiempoMaximoMensual = HORAS_LIMITE;
		}
	}

	/**
	 * @return the maxExtF
	 */
	public BigDecimal getMaxExtF() {
		return maxExtF;
	}

	/**
	 * @param maxExtF the maxExtF to set
	 */
	public void setMaxExtF(BigDecimal maxExtF) {
		this.maxExtF = maxExtF;
	}

	public void abrirCancelarJustificacion() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogDatosPersonales').hide();");
		context.execute("PF('ConfirmarCancelar').show();");
	}

	public void cancelarCancelarJustificacion() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar').hide();");
		context.execute("PF('dialogDatosPersonales').show();");
	}

	/**
	 * metodo para validar los roles de usuario e inicializar algunas variables
	 */
	public void initialization() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String idPersona = request.getParameter("id");
		if (idPersona != null) {
			codPersona = Long.valueOf(idPersona);

			if (getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != codPersona) {
				try {
					boolean rolPermisoHV;
					rolPermisoHV = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTROL_INTERNO, RolDTO.AUDITOR,
							RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_CONTRATOS,
							RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS, RolDTO.OPERADOR_TALENTO_HUMANO);
					if (!rolPermisoHV) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
					this.flgValidadoDocumento 		= true;
					this.flgValidadoLibreta 		= true;
					this.flgValidadoDiscapacidad 	= true;
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			}
		} else {
			if (getUsuarioSesion() != null) {
				codPersona = getUsuarioSesion().getCodPersona();
				try {
					Boolean flgValidPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA,
							RolDTO.SERVIDOR_PUBLICO);
					if (!flgValidPermission) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
					this.flgValidadoDocumento 		= false;
					this.flgValidadoLibreta 		= false;
					this.flgValidadoDiscapacidad 	= false;
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			} else {
				try {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle resourcesTitles = context.getApplication().getResourceBundle(context, "titles");
		try {
			strPageSubtitle = resourcesTitles.getString(TTL_SUB_MI_HOJA_VIDA);
			if(lbISGestionarHV)
				strPageSubtitle = resourcesTitles.getString("TTL_HOJA_CONSULT");
			if (strPageSubtitle == null || "".equals(strPageSubtitle))
				strPageSubtitle = "Mi Hoja de Vida";
		} catch (Exception z) {
			strPageSubtitle = "Mi Hoja de Vida";
		}			
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		// it isn't necessary
	}

	/**
	 * @return null metodo para guardar los datos personales del usuario
	 */
	public String persist() {

		if (cargarDodumento == null && persona.getDocumentoIdentificacionUrl() == null) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_SOPORTE_DOCUMENTO, "");
			return null;
		}

		if (persona != null  && persona.getClaseLibretaMilitar() != null && 
			!persona.getNumeroLibretaMilitar().equals("") && !persona.getDistritoMilitar().equals("") &&
			cargarlibretaMilitar == null && persona.getLibretaMilitarUrl() == null) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.MSG_SOPORTE_LIBRETA_MILITAR, "");
			return null;
		}

		persona.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		persona.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		persona.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		persona.setAudFechaActualizacion(DateUtils.getFechaSistema());

		boolean valid = ComunicacionServiciosHV.updatePersona(persona);

		if (valid) {
			persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			cargarDodumento = null;
			cargarlibretaMilitar = null;
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			TarjetaPersonaBean tarjetaPersonaBean = (TarjetaPersonaBean) elContext.getELResolver().getValue(elContext,null, "tarjetaPersonaBean");
			tarjetaPersonaBean.init();
			actualizaModificacionHv();
			
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_PAGE_EXCEPTION_ERROR,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}

		this.editado = false;
		return null;
	}

	/**
	 * Método para advertir que el correo ha cambiado.
	 */
	public void mostrarMensajeCorreo() {
		PersonaExt personaAnterior = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);

		if (datosContacto.getDireccionResidencia() == null) {
			this.flgDireccionRequerida = true;
			return;
		}

		if (persona.getCorreoElectronico() != null
				&& !persona.getCorreoElectronico().equalsIgnoreCase(personaAnterior.getCorreoElectronico())) {
			RequestContext.getCurrentInstance()
					.execute("$('#dlgEditarCorreoPersonal').modal({backdrop: 'static', keyboard: false});");
		} else {
			persistDatoContacto();
		}
	}

	/**
	 * metodo para guardar los datos de contacto del usuario
	 */
	public void persistDatoContacto() {
		datosContacto.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		datosContacto.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		datosContacto.setAudCodRol((int) this.getRolAuditoria().getId());
		datosContacto.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (datosContacto.getCodDatosContacto() == null || datosContacto.getCodDatosContacto().equals(0l)) {
			datosContacto.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}

		if (datosContacto.getIndicativoString() != null && datosContacto.getIndicativoString() != "") {
			datosContacto.setIndicativo(Integer.valueOf(datosContacto.getIndicativoString().replace("+", "")));
		}
		if(datosContacto.getIndicativoOficinaString()!=null && !"".equals(datosContacto.getIndicativoOficinaString()))
			datosContacto.setIndicativoOficina(Integer.valueOf(datosContacto.getIndicativoOficinaString()));
		
		if(getStrDireccionResidencia()!=null)
			datosContacto.setDireccionResidencia(getStrDireccionResidencia());
		
		if(estadoBoton) {
			editarDireccion.setDireccionGenerada(null);
			editarDireccion.setDireccionGenerada(editarDireccion.getComplemento());
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			String jsonDireccion = gson.toJson(editarDireccion);
			datosContacto.setDireccionResidencia(jsonDireccion);
			setStrDireccionResidencia(jsonDireccion);
		}
		
		boolean valid = ComunicacionServiciosHV.setDatoContacto(datosContacto);
		ComunicacionServiciosHV.updatePersona(persona);
		
		
		
		if (valid) {
			datosContacto = ComunicacionServiciosHV.getDatoContacto(codPersona);
			this.mostratPanelDatosPorPaisResidencia(false);
			this.agregarIndicativoDepartamento(false);
			actualizaModificacionHv();
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_PAGE_EXCEPTION_ERROR,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
		this.editado = false;
	}

	public void guardarDiscapacidad() {
		datosAdicionales.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		datosAdicionales.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		datosAdicionales.setAudCodRol((int) this.getRolAuditoria().getId());
		datosAdicionales.setAudFechaActualizacion(DateUtils.getFechaSistema());
		if (datosAdicionales.getCodDatoAdicional() == null || datosAdicionales.getCodDatoAdicional().equals(0l)) {
			datosAdicionales.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}
		ComunicacionServiciosHV.setDatoContactoAdi(datosAdicionales);
	}
	
	/**
	 * metodo para guardar los datos adicionales del usuario
	 */
	public void persistDatoAdicional() {
		datosAdicionales.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		datosAdicionales.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		datosAdicionales.setAudCodRol((int) this.getRolAuditoria().getId());
		datosAdicionales.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (datosAdicionales.getCodDatoAdicional() == null || datosAdicionales.getCodDatoAdicional().equals(0l)) {
			datosAdicionales.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setDatoContactoAdi(datosAdicionales);

		if (valid) {
			datosAdicionales = ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
			actualizaModificacionHv();
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_PAGE_EXCEPTION_ERROR,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
		this.editado = false;
	}

	/**
	 * @return null metodo para guardar los datos demograficos del usuario
	 */
	public String persistDatosDemograficos() {

		persona.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		persona.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		persona.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		persona.setAudFechaActualizacion(DateUtils.getFechaSistema());
		
		try {
			HojaDeVidaDelegate.actualizarDatosDemograficos(codPersona, nacionalidad, this.getUsuarioSesion(),
					this.getRolAuditoria().getId());
		} catch (SIGEP2SistemaException e) {
			logger.error("String persistDatosDemograficos() - guardar nacionalidad", e);
		}

		boolean valid = ComunicacionServiciosHV.updatePersona(persona);
		
		if (valid) {
			guardarDiscapacidad();
			actualizaModificacionHv();
			persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_PAGE_EXCEPTION_ERROR,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
		this.editado=false;
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			if (this.conversation.isTransient()) {
				this.conversation.begin();
				this.conversation.setTimeout(timeOut);
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_URL_INVALID);
		}
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// it isn't necessary
	}
	
	/**
	 * @param e
	 * @throws IOException
	 *             metodo para cargar el soporte del documento de identidad
	 */
	public void documentoUpload(FileUploadEvent e) throws IOException {
		cargarDodumento = e.getFile();
		String response = "";
		byte[] bytes = null;
		try {
			if (null != cargarDodumento) {
				bytes = cargarDodumento.getContents();
				String ext = FilenameUtils.getExtension(cargarDodumento.getFileName());
				String filename = persona.getNumeroIdentificacion() + "." + ext;
				String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_DOCUMENTO)+ filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();	
				}catch (IOException err) {
					logger.log().error("Error al trata de escribir en DatosPer el archivo en la ruta:" + ruta, err.getMessage());
				}
				ErrorMensajes resp=null;
				
				if (HADOOP_OPCION_CARGA.equals("0")){/*Operacion Normal*/
					response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
                            ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_DOCUMENTO, 
                            WebUtils.TP_DOCUMENTO_IDENTIFICACION, persona.getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);						

				}else if (HADOOP_OPCION_CARGA.equals("1")||HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
					resp=uploadHaddopApi(WebUtils.CNS_RUTA_DOCUMENTO, WebUtils.TP_DOCUMENTO_IDENTIFICACION, filePath, persona.getNumeroIdentificacion());			
				}
				if(!resp.isError()) {
					persona.setDocumentoIdentificacionUrl(resp.getUrlArchivo());
					this.setNombreArchivoDocumento(resp.getNombreArchivo());
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale())
									.replace("%nombrearchivo%", resp.getNombreArchivo()));					
				}else {
					persona.setDocumentoIdentificacionUrl(null);
					this.setNombreArchivoDocumento(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_FALLIDA, getLocale()));	
					logger.log().error("********Error documentoUpload()*****", resp.getMensaje() + " "+resp.getMensajeTecnico());
				}
			}
			
		} catch (Exception e2) {
			logger.log().error("documentoUpload()", e2);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
		
	}

	/**
	 * @param e
	 * @throws IOException
	 *             metodo para cargar el soporte de la libreta militar
	 */
	public void libretaMilitarUpload(FileUploadEvent e) throws IOException {

		cargarlibretaMilitar = e.getFile();

		byte[] bytes = null;
		String response = "";
		try {
			if (null != cargarlibretaMilitar) {
				bytes = cargarlibretaMilitar.getContents();
				String ext = FilenameUtils.getExtension(cargarlibretaMilitar.getFileName());
				String filename = persona.getNumeroIdentificacion() + "." + ext;
				String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_LIBRETA_MILITAR)
						+ filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();
				}catch (IOException err) {
					logger.log().error("Error al trata de escribir en Dato Personal el archivo en la ruta:" + ruta, err.getMessage());
				}
				ErrorMensajes resp = null;
				
				if (HADOOP_OPCION_CARGA.equals("0")){/*Operacion Normal*/
					response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
													ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_LIBRETA_MILITAR, 
													WebUtils.TP_LIBRETA_MILITAR, persona.getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);
				}else if (HADOOP_OPCION_CARGA.equals("1")||HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
					resp=uploadHaddopApi(WebUtils.CNS_RUTA_LIBRETA_MILITAR, WebUtils.TP_LIBRETA_MILITAR, filePath, persona.getNumeroIdentificacion());			
				}					
				if(!resp.isError()) {
					persona.setLibretaMilitarUrl(resp.getUrlArchivo());
					this.setNombreArchivoLibreta(resp.getNombreArchivo());
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale())
									.replace("%nombrearchivo%", resp.getNombreArchivo()));					
				}else {
					persona.setLibretaMilitarUrl(null);
					this.setNombreArchivoLibreta(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));		
					logger.log().error("********Error libretaMilitarUpload()*****", resp.getMensaje() + " "+resp.getMensajeTecnico());
				}
			}
		} catch (Exception e2) {
			logger.log().error("libretaMilitarUpload()", e2);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
		
	}
	
	/**
	 * Metodo para cargar el documento de Discapacidad
	 * @param e
	 * @throws IOException
	 */
	public void uploadDocumentoDiscapacidad(FileUploadEvent e) throws IOException {
		cargarDocumentoDiscapacidad = e.getFile();
		byte[] bytes 				= null;
		String response 			= "";
		try {
			if (null != cargarDocumentoDiscapacidad) {
				bytes 			= cargarDocumentoDiscapacidad.getContents();
				String ext 		= FilenameUtils.getExtension(cargarDocumentoDiscapacidad.getFileName());
				String filename = persona.getNumeroIdentificacion() + "." + ext;
				String ruta 	= ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_DOCUMENTO_DISCAPACIDAD) + filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();
				}catch (IOException err) {
					logger.log().error("Error al trata de escribir en Dato Personal el archivo en la ruta:" + ruta, err.getMessage());
				}
				ErrorMensajes resp = null;
				
				if (HADOOP_OPCION_CARGA.equals("0")){/*Operacion Normal*/
					response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
													ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_DOCUMENTO_DISCAPACIDAD, 
													WebUtils.TP_DOCUMENTO_DISCAPACIDAD, persona.getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);
				}else if (HADOOP_OPCION_CARGA.equals("1")||HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
					resp=uploadHaddopApi(WebUtils.CNS_RUTA_DOCUMENTO_DISCAPACIDAD, WebUtils.TP_DOCUMENTO_DISCAPACIDAD, filePath, persona.getNumeroIdentificacion());			
				}					
				if(!resp.isError()) {
					persona.setDocumentoDiscapacidadUrl(resp.getUrlArchivo());
					this.setNombreArchivoDiscapacidad(resp.getNombreArchivo());
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale())
									.replace("%nombrearchivo%", resp.getNombreArchivo()));					
				}else {
					persona.setDocumentoDiscapacidadUrl(null);
					this.setNombreArchivoDiscapacidad(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));		
					logger.log().error("********Error uploadDocumentoDiscapacidad()*****", resp.getMensaje() + " "+resp.getMensajeTecnico());
				}
				
				 File file = new File(filePath);
				 if (file.exists()) {
					file.delete();
				 }
			}
		} catch (Exception e2) {
			logger.log().error("uploadDocumentoDiscapacidad()", e2);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
	}

	/**
	 * metodo para mostrar el panel de las discapacidades
	 */
	public void mostratPanelTipoDiscapacidad() {
		if (!datosAdicionales.getFlgDiscapacidad()) {
			datosAdicionales.setCodTipoDiscapacidad(null);
		}
	}

	/**
	 * @param inicializacion
	 *            metodo para mostrar el panel donde esta municipio y ciudad
	 */
	public void mostratPanelDatosPorPaisResidencia(boolean inicializacion) {

		Pais pais = ComunicacionServiciosSis.getpisporid(169);

		if (datosContacto!=null && datosContacto.getCodPaisResidencia() != null) {
			datosContacto.setIndicativoString(null);
			datosContacto.setIndicativoOficinaString(null);
			pais = ComunicacionServiciosSis.getpisporid(datosContacto.getCodPaisResidencia());
			if (pais.getNombrePais().equalsIgnoreCase("COLOMBIA")) {
				mostratDatosPorPaisResidencia = true;
			} else {
				mostratDatosPorPaisResidencia = false;
				datosContacto.setCodDepartamentoResidencia(null);
				datosContacto.setCodMunicipioResidencia(null);
			}

			if (pais.getIndicativoPais() != null) {
				datosContacto.setIndicativoString("+" + pais.getIndicativoPais());
				if(inicializacion){
					datosContacto.setIndicativoOficina(pais.getIndicativoPais());
					datosContacto.setIndicativoOficinaString(pais.getIndicativoPais().toString());
					//RequestContext.getCurrentInstance().update("frmPrincipal:tabHojaDeVida:tabView:indicativoTelOficina_input");
				}
			}
		} else {
			datosContacto.setCodPaisResidencia(169);
			mostratDatosPorPaisResidencia = true;

			if (pais!=null && pais.getIndicativoPais() != null) {
				datosContacto.setIndicativoString("+" + pais.getIndicativoPais());
			}
		}

		if (pais.getBanderaUrl() != null) {
			this.setBanderaPais(pais.getBanderaUrl().toLowerCase());
		} else {
			this.setBanderaPais("/resources/images/banderas/default.png");
		}

		this.editado = inicializacion;
		if (inicializacion) {
			this.changedTab = 2;
		}
		
		
	}

	/**
	 * @param inicializacion
	 *            metodo para agregar los indicativos por departamento
	 */
	public void agregarIndicativoDepartamento(boolean inicializacion) {
		if (datosContacto.getCodDepartamentoResidencia() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(datosContacto.getCodPaisResidencia());
			Departamento departamento = ComunicacionServiciosSis
					.getdeptoporid(datosContacto.getCodDepartamentoResidencia());
			String indicativo = "+" + pais.getIndicativoPais() + departamento.getIndicativo();
			datosContacto.setIndicativoString(indicativo);
			datosContacto.setIndicativoOficinaString(indicativo);
			this.editado = inicializacion;
			if (inicializacion) {
				this.changedTab = 2;
			}
		}
		
	}

	/**
	 * @param inicializacion
	 *            metodo para mostrar el panel donde esta municipio y ciudad
	 */
	public void mostratPanelDatosPorPaisNacionalidad(boolean inicializacion) {
		if (persona.getCodPaisNacimiento() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(persona.getCodPaisNacimiento());
			if (pais.getNombrePais()!=null &&   pais.getNombrePais().equalsIgnoreCase("COLOMBIA")) {
				mostratDatosPorPaisNacionalidad = true;
			} else {
				mostratDatosPorPaisNacionalidad = false;
				persona.setCodDepartamentoNacimiento(null);
				persona.setCodMunicipioNacimiento(null);
			}

			this.editado = inicializacion;
			if (inicializacion) {
				this.changedTab = 1;
			}
		}
	}

	/**
	 * @param value
	 *            metodo para mostar el panel de generar direccion
	 */
	public void mostrarPanelEditarDireccion(boolean value) {		
		cargarDireccionPanelEditarDireccion();
		mostratEditarDireccion = value;
	}
	
	public void cargarDireccionPanelEditarDireccion() {
		if (this.datosContacto.getDireccionResidencia() != null && !this.datosContacto.getDireccionResidencia().isEmpty()) {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(this.datosContacto.getDireccionResidencia(), EditarDireccionDTO.class);
				if (direccion != null) {
					this.editarDireccion = direccion;
				}
			} catch (JsonSyntaxException e) {

			}
		}
	}

	/**
	 * Metodo para generar la direccion
	 */
	public void generarDireccion() {
		String primerDirecion = "";
		String segundaDirecion = "";

		if (editarDireccion.getTipoVia() != null) {
			primerDirecion = primerDirecion + " " + editarDireccion.getTipoVia().getSigla();
		}

		if (editarDireccion.getSegundoNumero() != null && !editarDireccion.getSegundoNumero().isEmpty()) {
			segundaDirecion = segundaDirecion + " " + editarDireccion.getSegundoNumero();
		}

		if (editarDireccion.getNumero() != null && !editarDireccion.getNumero().isEmpty()) {
			primerDirecion = primerDirecion + " " + editarDireccion.getNumero();
		}

		if (editarDireccion.getTercerLetra() != null) {
			segundaDirecion = segundaDirecion + editarDireccion.getTercerLetra().getSigla();
		}

		if (editarDireccion.getPrimerLetra() != null) {
			primerDirecion = primerDirecion + editarDireccion.getPrimerLetra().getSigla();
		}

		if (editarDireccion.isBis()) {
			primerDirecion = primerDirecion + " BIS";
		}

		if (editarDireccion.getSegundaLetra() != null) {
			primerDirecion = primerDirecion + " " + editarDireccion.getSegundaLetra().getSigla();
		}

		if (editarDireccion.getOrientacion() != null) {
			primerDirecion = primerDirecion + " " + editarDireccion.getOrientacion().getSigla();
		}

		if (editarDireccion.getTercerNumero() != null && !editarDireccion.getTercerNumero().isEmpty()) {
			segundaDirecion = segundaDirecion + " " + editarDireccion.getTercerNumero();
		}

		if (editarDireccion.getSegundaOrientacion() != null) {
			segundaDirecion = segundaDirecion + " " + editarDireccion.getSegundaOrientacion().getSigla();
		}

		if (editarDireccion.getComplemento() != null && !editarDireccion.getComplemento().isEmpty()) {
			segundaDirecion = segundaDirecion + " " + editarDireccion.getComplemento();
		}

		segundaDirecion = segundaDirecion == "" ? "" : " -" + segundaDirecion;
		editarDireccion.setDireccionGenerada(primerDirecion + segundaDirecion);
	}

	/**
	 * metodo para agregar la direccion generada al formulario de datos del usuario
	 */
	public void agergarDireccion() {
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String jsonDireccion = gson.toJson(editarDireccion);
		
		datosContacto.setDireccionResidencia(jsonDireccion);
		setStrDireccionResidencia(jsonDireccion);
		mostratEditarDireccion = false;
	}
	
	/**
	 * Metodo para convertir la direccion y mostrarla*/
	public String mostrarDireccionGenerada() {
		String direccion = "";
		try {	
			if (datosContacto.getDireccionResidencia() != null && !datosContacto.getDireccionResidencia().isEmpty()) {
				Direccion dir = new Direccion();
				dir.llenarDatosDesdeJson(datosContacto.getDireccionResidencia());
				direccionBean.setDireccionSeleccionada(dir);
	            direccionBean.mostrarDireccion();
	            direccion = direccionBean.getDireccionGenerada();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return direccion;
	}
	
	/**
	 * metodo para cancelar los cambios realizados en datos basicos
	 */
	public void cancelDatosBasicos() {
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		cargarDodumento = null;
		cargarlibretaMilitar = null;
		this.setEditado(false);
	}

	/**
	 * metodo para cancelar los cambios realizados en datos demograficos
	 */
	public void cancelFormDatosDemograficos() {
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		this.mostratPanelDatosPorPaisNacionalidad(false);
		List<PaisDTO> nacionalidadDTO = null;

		try {
			nacionalidadDTO = HojaDeVidaDelegate.findNacionalidadByCodPersonaId(codPersona);
			if (nacionalidadDTO != null) {
				this.setNacionalidad(nacionalidadDTO);
			}
			this.setEditado(false);
		} catch (SIGEP2SistemaException e) {
			logger.log().warn("void init() - datosAdicionalesDTO", e);
		}
	}

	/**
	 * metodo para cancelar los cambios realizados en datos de contato
	 */
	public void cancelFormDatosContacto() {
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		datosContacto = ComunicacionServiciosHV.getDatoContacto(codPersona);
		this.mostratPanelDatosPorPaisResidencia(false);
		this.agregarIndicativoDepartamento(false);
		this.mostrarPanelEditarDireccion(false);
		editarDireccion = new EditarDireccionDTO();
		this.setEditado(false);
	}

	/**
	 * metodo para cancelar los cambios realizados en datos adicionales
	 */
	public void cancelDatosAdicional() {
		datosAdicionales = ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
		this.setEditado(false);
	}

	/**
	 * metodo para validar si se requiere la extension del telefono
	 */
	public void validRequiredExt() {
		if (datosContacto.getExtencionTelefonoOficina() != null) {
			requiredExt = true;
		} else {
			requiredExt = false;
		}
	}

	/**
	 * @param changedTab
	 *            metodo para cambiar el valor de los tabs
	 */
	public void formChange(int changedTab) {
		this.flgDireccionRequerida = false;
		this.setEditado(true);
		this.changedTab = changedTab;
		this.cambiarEstadoTextoBotonInit();
	}

	/**
	 * @param formulario
	 * @return null metodo para mostrar el formulario de confirmacion antes de
	 *         guardar los datos.
	 */
	public String confirmarDatosGuardar(int formulario) {
		if (formulario == 1) {
			Persona personaFound = new Persona();
			personaFound.setCodTipoIdentificacion(persona.getCodTipoIdentificacion());
			personaFound.setNumeroIdentificacion(persona.getNumeroIdentificacion());
			PersonaExt personaIdent = ComunicacionServiciosHV.persontipIdnumId(personaFound);

			if (personaIdent.getCodPersona() != null && !personaIdent.getCodPersona().equals(persona.getCodPersona())) {
				String msn = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_VALIDAR_CAMBIO_DATOS, getLocale())
						.replace("%tipo%", personaIdent.getNombreTipoDocuento())
						.replace("%numero%", personaIdent.getNumeroIdentificacion())
						.replace("%nombreusuario%", personaIdent.getNombreUsuario());
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, msn, "");
				return null;
			}
			String mensajes = "";
			boolean doc, lib;
			doc = lib = true;
			if (cargarDodumento == null && persona.getDocumentoIdentificacionUrl() == null) {
				mensajes +=  MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_SOPORTE_DOCUMENTO, this.getLocale()) + ", ";
				doc = false;
			}
			if (persona.getClaseLibretaMilitar() != null && 
				!persona.getNumeroLibretaMilitar().equals("") && !persona.getDistritoMilitar().equals("") && 
				cargarlibretaMilitar == null  && persona.getLibretaMilitarUrl() == null) {
				mensajes +=  MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_SOPORTE_LIBRETA_MILITAR, this.getLocale());
				lib = false;
			}
			if(!doc || !lib) {
				this.mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES , mensajes);
				return null;
			}
		}

		codFormulario = formulario;
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		
		if(lbISGestionarHV){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogDatosPersonales').show()");
		}else{
			guardarConfirmacionDatos();
		}
		return null;
	}

	/**
	 * metodo para guardar la confirmacion de los cambios realizados y guardar los
	 * cambios
	 */
	public void guardarConfirmacionDatos() {
		PersonaExt personaAnterior 					= ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		DatoContactoExt datosContactoAnterior 		= ComunicacionServiciosHV.getDatoContacto(codPersona);
		DatoAdicionalExt datosAdicionalesAnterior 	= ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
		List<PaisDTO> nacionalidadAnterior			= null;
		List<String> camposEditados 				= new ArrayList<>();
		if(lbISGestionarHV){
			try {
				nacionalidadAnterior = HojaDeVidaDelegate.findNacionalidadByCodPersonaId(codPersona);
			} catch (SIGEP2SistemaException e) {
				logger.error("SIGEP2SistemaException", e);
			}
			
			/*Datos basicos*/
			if (persona.getPrimerNombre() != null
					&& !persona.getPrimerNombre().equalsIgnoreCase(personaAnterior.getPrimerNombre())) {
				camposEditados.add("Primer nombre: " + persona.getPrimerNombre());
			}
	
			if (persona.getSegundoNombre() != null && persona.getSegundoNombre().length() > 0
					&& !persona.getSegundoNombre().equalsIgnoreCase(personaAnterior.getSegundoNombre())) {
				camposEditados.add("Segundo nombre: " + persona.getSegundoNombre());
			}
	
			if (persona.getPrimerApellido() != null
					&& !persona.getPrimerApellido().equalsIgnoreCase(personaAnterior.getPrimerApellido())) {
				camposEditados.add("Primer apellido: " + persona.getPrimerApellido());
			}
	
			if (persona.getSegundoApellido() != null && persona.getSegundoApellido().length() > 0
					&& !persona.getSegundoApellido().equalsIgnoreCase(personaAnterior.getSegundoApellido())) {
				camposEditados.add("Segundo apellido: " + persona.getSegundoApellido());
			}
	
			if (persona.getCodTipoIdentificacion() != null
					&& !persona.getCodTipoIdentificacion().equals(personaAnterior.getCodTipoIdentificacion())) {
				camposEditados.add("Tipo de identificación: " + persona.getNombreTipoDocuento());
			}
	
			if (!persona.getNumeroIdentificacion().equalsIgnoreCase(personaAnterior.getNumeroIdentificacion())) {
				camposEditados.add("Número de identificación: " + persona.getNumeroIdentificacion());
			}
	
			if (!persona.getFechaNacimientoString().equalsIgnoreCase(personaAnterior.getFechaNacimientoString())) {
				camposEditados.add("Fecha de nacimiento: " + persona.getFechaNacimientoString());
			}
	
			if (persona.getCorreoElectronico() != null
					&& !persona.getCorreoElectronico().equalsIgnoreCase(personaAnterior.getCorreoElectronico())) {
				camposEditados.add("Correo electrónico: " + persona.getCorreoElectronico());
			}
	
			if (persona.getCodGenero() != null && !persona.getCodGenero().equals(personaAnterior.getCodGenero())) {
				Parametrica genero = ComunicacionServiciosSis
						.getParametricaporId(BigDecimal.valueOf(persona.getCodGenero()));
				camposEditados.add("Género: " + genero.getNombreParametro());
			}
	
			if (persona.getCodEstadoCivil() != null
					&& !persona.getCodEstadoCivil().equals(personaAnterior.getCodEstadoCivil())) {
				Parametrica estadoCivil = ComunicacionServiciosSis
						.getParametricaporId(BigDecimal.valueOf(persona.getCodEstadoCivil()));
				camposEditados.add("Estado civil: " + estadoCivil.getNombreParametro());
			}
			if(persona.getNumeroLibretaMilitar()!=null && personaAnterior!=null && personaAnterior.getNumeroLibretaMilitar()!=null
					&& !persona.getNumeroLibretaMilitar().equals(personaAnterior.getNumeroLibretaMilitar())){
				camposEditados.add("Número Libreta Militar: " +persona.getNumeroLibretaMilitar());
			}
			if(persona.getDistritoMilitar()!=null && personaAnterior!=null && personaAnterior.getDistritoMilitar()!=null
					&& !persona.getDistritoMilitar().equals(personaAnterior.getDistritoMilitar())){
				camposEditados.add("Distrito Militar: " +persona.getDistritoMilitar());
			}
			if(persona.getClaseLibretaMilitar()!=null && personaAnterior!=null && personaAnterior.getClaseLibretaMilitar()!=null
					&& !persona.getClaseLibretaMilitar().equals(personaAnterior.getClaseLibretaMilitar())){
				Parametrica claseLibreta = ComunicacionServiciosSis
						.getParametricaporId(BigDecimal.valueOf(Long.parseLong(persona.getClaseLibretaMilitar())));
				camposEditados.add("Clase Libreta Militar: " +claseLibreta.getNombreParametro());
			}						
			
			/*Fin datos básicos*/
	
			if (persona.getCodPertenenciaEtnica() != null
					&& !persona.getCodPertenenciaEtnica().equals(personaAnterior.getCodPertenenciaEtnica())) {
				Parametrica pertenenciaEtnica = ComunicacionServiciosSis
						.getParametricaporId(BigDecimal.valueOf(persona.getCodPertenenciaEtnica()));
				camposEditados.add("Pertenencia Ëtnica: " + pertenenciaEtnica.getNombreParametro());
			}
	
			if (persona.getCodPaisNacimiento() != null
					&& !persona.getCodPaisNacimiento().equals(personaAnterior.getCodPaisNacimiento())
					&& persona.getCodPaisNacimiento() != 0) {
				paisNacimiento = ComunicacionServiciosSis.getpisporid(persona.getCodPaisNacimiento());
				camposEditados.add("País de nacimiento: " + paisNacimiento.getNombrePais());
			}
	
			if (nacionalidad != null && nacionalidadAnterior != null) {
				for (int i = 0; i < nacionalidad.size(); i++) {
					Boolean flgIgual = false;
	
					for (int j = 0; j < nacionalidadAnterior.size(); j++) {
						if (nacionalidad.get(i).getId() == nacionalidadAnterior.get(j).getId()) {
							flgIgual = true;
						}
					}
	
					if (!flgIgual) {
						camposEditados.add("Nacionalidad: " + nacionalidad.get(i).getNombrePais());
					}
				}
			}
	
			if (datosContacto.getCodDatosContacto()!=null && datosContacto.getCodPaisResidencia() != null
					&& !datosContacto.getCodPaisResidencia().equals(datosContactoAnterior.getCodPaisResidencia())) {
				Pais paisResidencia = ComunicacionServiciosSis.getpisporid(datosContacto.getCodPaisResidencia());
				camposEditados.add("País de residencia: " + paisResidencia.getNombrePais());
			}
			if (datosContacto.getCodDatosContacto()!=null && datosContacto.getCodDepartamentoResidencia() != null
					&& !datosContacto.getCodDepartamentoResidencia().equals(datosContactoAnterior.getCodDepartamentoResidencia())) {
				Departamento depto = ComunicacionServiciosSis.getdeptoporid(datosContacto.getCodDepartamentoResidencia());
				camposEditados.add("Departamento de residencia: " + depto.getNombreDepartamento());
			}	
			if (datosContacto.getCodDatosContacto()!=null && datosContacto.getCodMunicipioResidencia() != null
					&& !datosContacto.getCodMunicipioResidencia().equals(datosContactoAnterior.getCodMunicipioResidencia())) {
				Municipio mun = ComunicacionServiciosSis.getMunicipiosid(datosContacto.getCodMunicipioResidencia());
				camposEditados.add("Municipio de residencia: " + mun.getNombreMunicipio());
			}				
	
			if (datosContacto.getTelefonoResidencia() != null && datosContacto.getTelefonoResidencia().length() > 0
					&& !datosContacto.getTelefonoResidencia().equals(datosContactoAnterior.getTelefonoResidencia())) {
				camposEditados.add("Teléfono de residencia: " + datosContacto.getTelefonoResidencia());
			}
	
			if (datosContacto.getNumCelular() != null && datosContacto.getNumCelular().length() > 0
					&& !datosContacto.getNumCelular().equals(datosContactoAnterior.getNumCelular())) {
				camposEditados.add("Teléfono móvil: " + datosContacto.getNumCelular());
			}
	
			if (datosContacto.getNumTelefonoOficina() != null && datosContacto.getNumTelefonoOficina().length() > 0
					&& !datosContacto.getNumTelefonoOficina().equals(datosContactoAnterior.getNumTelefonoOficina())) {
				camposEditados.add("Teléfono oficina: " + datosContacto.getNumTelefonoOficina());
			}
	
			if (datosContacto.getExtencionTelefonoOficina() != null && !datosContacto.getExtencionTelefonoOficina()
					.equals(datosContactoAnterior.getExtencionTelefonoOficina())) {
				camposEditados.add("Extensión oficina: " + datosContacto.getExtencionTelefonoOficina());
			}
	
			if (persona.getCorreoAlternativo() != null && persona.getCorreoAlternativo().length() > 0
					&& !persona.getCorreoAlternativo().equals(personaAnterior.getCorreoAlternativo())) {
				camposEditados.add("Correo electrónico oficina: " + persona.getCorreoAlternativo());
			}
	
			if (datosContacto.getCodTipoZona() != null
					&& !datosContacto.getCodTipoZona().equals(datosContactoAnterior.getCodTipoZona())) {
				Parametrica tipoZona = ComunicacionServiciosSis
						.getParametricaporId(BigDecimal.valueOf(datosContacto.getCodTipoZona()));
				camposEditados.add("Tipo Zona: " + tipoZona.getNombreParametro());
			}
	
			if (datosContacto.getDireccionResidencia() != null && datosContacto.getDireccionResidencia().length() > 0
					&& !datosContacto.getDireccionResidencia().equals(datosContactoAnterior.getDireccionResidencia())) {
				camposEditados.add("Dirección residencia: " + datosContacto.getDireccionResidencia());
			}
	
			if (datosAdicionales.getCodCabezaHogar() != null
					&& !datosAdicionales.getCodCabezaHogar().equals(datosAdicionalesAnterior.getCodCabezaHogar())) {
				Parametrica codCabezaHogar = ComunicacionServiciosSis
						.getParametricaporId(BigDecimal.valueOf(datosAdicionales.getCodCabezaHogar()));
				camposEditados.add("Cabeza de hogar: " + codCabezaHogar.getNombreParametro());
			}
	
			if (datosAdicionales.getCodTipoDiscapacidad() != null && !datosAdicionales.getCodTipoDiscapacidad()
					.equals(datosAdicionalesAnterior.getCodTipoDiscapacidad())) {
				Parametrica discapacidad = ComunicacionServiciosSis
						.getParametricaporId(BigDecimal.valueOf(datosAdicionales.getCodTipoDiscapacidad()));
				camposEditados.add("Discapacidad: " + discapacidad.getNombreParametro());
				if (!datosAdicionales.getFlgDiscapacidad()) {
					camposEditados.remove("Discapacidad: " + discapacidad.getNombreParametro());
				}
			}
	
			if (datosAdicionalesAnterior.getFlgPrepensionado() != datosAdicionales.getFlgPrepensionado()) {
				String flgPensionado = "No";
				if (datosAdicionales.getFlgPrepensionado()) {
					flgPensionado = "Si";
				}
				camposEditados.add("Está pre-pensionado: " + flgPensionado);
			}
	
			if (datosAdicionalesAnterior.getFlgVictimaConflicto() != datosAdicionales.getFlgVictimaConflicto()) {
				String flgVictima = "No";
				if (datosAdicionales.getFlgVictimaConflicto()) {
					flgVictima = "Si";
				}
				camposEditados.add("Está pre-pensionado: " + flgVictima);
			}
	
			if (datosAdicionales.getUrlFacebook() != null && datosAdicionales.getUrlFacebook().length() > 0
					&& !datosAdicionales.getUrlFacebook().equalsIgnoreCase(datosAdicionalesAnterior.getUrlFacebook())) {
				camposEditados.add("Url Facebook: " + datosAdicionales.getUrlFacebook());
			}
	
			if (datosAdicionales.getUrlTwitter() != null && datosAdicionales.getUrlTwitter().length() > 0
					&& !datosAdicionales.getUrlTwitter().equalsIgnoreCase(datosAdicionalesAnterior.getUrlTwitter())) {
				camposEditados.add("Url Twitter: " + datosAdicionales.getUrlTwitter());
			}
	
			if (datosAdicionales.getUrlLinkedin() != null && datosAdicionales.getUrlLinkedin().length() > 0
					&& !datosAdicionales.getUrlLinkedin().equalsIgnoreCase(datosAdicionalesAnterior.getUrlLinkedin())) {
				camposEditados.add("Url LinkedIn: " + datosAdicionales.getUrlLinkedin());
			}
	
			if (datosAdicionales.getUrlInstagram() != null && datosAdicionales.getUrlInstagram().length() > 0
					&& !datosAdicionales.getUrlInstagram().equalsIgnoreCase(datosAdicionalesAnterior.getUrlInstagram())) {
				camposEditados.add("Url Instagram: " + datosAdicionales.getUrlInstagram());
			}
	
			camposEditados.add("Modificado Por : " + this.getUsuarioSesion().getNumeroIdentificacion() + " - "
					+ this.getUsuarioSesion().getNombrePersona());
			camposEditados.add("Entidad que Modifica : " + this.getEntidadUsuario().getNombreEntidad());
			if(modificacionHojaVida!=null && modificacionHojaVida.getJustificacionModificacion()!=null
					&& !"".equals(modificacionHojaVida.getJustificacionModificacion())){
				camposEditados.add("Justificación : " + modificacionHojaVida.getJustificacionModificacion());
			}
		}

		switch (codFormulario) {
		case 1:
			this.persist();
			break;
		case 2:
			this.persistDatosDemograficos();
			break;
		case 3:
			this.persistDatoContacto();
			break;
		case 4:
			this.persistDatoAdicional();
			break;
		default:
			break;
		}
		if(lbISGestionarHV){
			HojaVidaExt hojaVidaFilter = new HojaVidaExt();
			hojaVidaFilter.setCodPersona(persona.getCodPersona());
			hojaVidaFilter.setFlgActivo(true);
			hojaVidaFilter.setLimitEnd(1);
			List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);
			if(listHojaVida!=null && listHojaVida.size()>0){
				modificacionHojaVida.setCodHojaVida(BigDecimal.valueOf(listHojaVida.get(0).getCodHojaVida()));
				modificacionHojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
				modificacionHojaVida.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				modificacionHojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
				modificacionHojaVida.setAudFechaModificacion(DateUtils.getFechaSistema());
				ComunicacionServiciosHV.setmodificacionhohadevida(modificacionHojaVida);
			}
		}			
		
		codFormulario = null;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogDatosPersonales').hide();");
		if(lbISGestionarHV){
			PersonaExt personaControlInterno = new PersonaExt();
			personaControlInterno.setCodRol(new BigDecimal(COD_JEFE_CONTROL_INTERNO));
			personaControlInterno.setCodEntidad(new BigDecimal(getEntidadUsuario().getId()));
			List<PersonaExt> listaPersonalControlInterno = ComunicacionServiciosHV.getPersonaControlInterno(personaControlInterno);
			List<String> email = new ArrayList<>();
			List<String> ccEmail = new ArrayList<>();
	
			try {
				for (PersonaExt personaExt : listaPersonalControlInterno) {
					ccEmail.add(personaExt.getCorreoElectronico());
				}
				email.add(persona.getCorreoElectronico());
				email.add(persona.getCorreoAlternativo());
				HojaDeVidaDelegate.emailActualizacionDatosPersonaDC(persona.getNombreUsuario(),
						modificacionHojaVida.getAudFechaModificacion(), personaControlInterno.getNombreUsuario(), email,
						ccEmail, camposEditados);
			} catch (Exception e) {
				logger.error("void guardarConfirmacionDatos() - enviar correo", e);
			}
		}
		if(modificacionHojaVida!=null)
			modificacionHojaVida.setJustificacionModificacion(null);
		refrescarProgresoHojaVida();
	}

	/**
	 * metodo para cancelar los cambios que realizamos en el formulario
	 */
	public void cancelarConfirmacionDatos() {
		switch (codFormulario) {
		case 1:
			this.cancelDatosBasicos();
			break;
		case 2:
			this.cancelFormDatosDemograficos();
			break;
		case 3:
			this.cancelFormDatosContacto();
			break;
		case 4:
			this.cancelDatosAdicional();
			break;
		default:
			break;
		}

		codFormulario = null;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar').hide();");
	}

	/**
	 * Metodo para limpiar todos los formulario de datos personales
	 */
	public void limpiarTodoFormulario() {
		if(validaCambioPestanna()){
			this.cancelDatosBasicos();
			this.cancelFormDatosDemograficos();
			this.cancelFormDatosContacto();
			this.cancelDatosAdicional();
		}
	}

	public boolean isEstadoBoton() {
		return estadoBoton;
	}

	public void setEstadoBoton(boolean estadoBoton) {
		this.estadoBoton = estadoBoton;
	}

	public boolean isEstadoText() {
		return estadoText;
	}

	public void setEstadoText(boolean estadoText) {
		this.estadoText = estadoText;
	}
	/*
	 * Habilita la direccion
	 */
	public void cambiarEstadoTextoBotonInit() {
		if (datosContacto.getCodTipoZona() == null) {
			this.estadoText = true;
			this.estadoBoton = true;
			return;
		}
		if (datosContacto.getCodTipoZona() == 222) {
			this.estadoText = false;
			this.estadoBoton = true;
			cargarDireccionPanelEditarDireccion();
		} else {
			this.estadoText = true;
			this.estadoBoton = false;
		}
	}
	
	public void cambiarEstadoTextoBoton() {
		if (datosContacto.getCodTipoZona() == null) {
			this.estadoText = true;
			this.estadoBoton = true;
			return;
		}
		if (datosContacto.getCodTipoZona() == 222) {
			this.estadoText = false;
			this.estadoBoton = true;
			cargarDireccionPanelEditarDireccion();
		} else {
			this.estadoText = true;
			this.estadoBoton = false;
			datosContacto.setDireccionResidencia(null);
			setStrDireccionResidencia(null);
			RequestContext.getCurrentInstance().update("frmPrincipal:tabHojaDeVida:tabView:direccion");
		}
	}	
	

	public boolean isEstadoBotonTextGen() {
		return estadoBotonTextGen;
	}

	public void setEstadoBotonTextGen(boolean estadoBotonTextGen) {
		this.estadoBotonTextGen = estadoBotonTextGen;
	}

	/**
	 * Pablo Quintana - 26/09/2018 Método que obtiene la url del anexo
	 * 
	 * @param tipoDocVisualiza
	 *            1. Hoja de vida datos basicos - documento identidad 2. Hoja de
	 *            vida datos basicos - Libreta Militar
	 */
	public void visualizarArchivoDatosPersonales(String tipoDocVisualiza) {
		String ruta = "";
		String tipo = "";
		switch (tipoDocVisualiza) {
			case "1":
				tipo = WebUtils.TP_DOCUMENTO_IDENTIFICACION;
				ruta = (persona != null && persona.getDocumentoIdentificacionUrl() != null) ? persona.getDocumentoIdentificacionUrl() : "";
	            break;
			case "2":
				tipo = WebUtils.TP_LIBRETA_MILITAR;
	            ruta = (persona != null && persona.getLibretaMilitarUrl() != null) ? persona.getLibretaMilitarUrl() : "";
	            break;
			case "3":
				tipo = WebUtils.TP_DOCUMENTO_DISCAPACIDAD;
	            ruta = (persona != null && persona.getDocumentoDiscapacidadUrl() != null) ? persona.getDocumentoDiscapacidadUrl() : "";
	            break;
			default:
	            throw new IllegalArgumentException("Tipo de documento no válido");
		}
		rutaArchivo = (ruta != null && !ruta.isEmpty()) ? WebUtils.getUrlFile(ruta) : null;
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	public void confirmarCancelar() {

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar2').show();");
	}

	public void cancelarCancelar() {

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar2').hide();");
	}

	@Override
	/**
	 * Metodo para hacer back hacia la página <b>index.xhtml</b>
	 */
	public void cancelar() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar2').hide();");
		try {
			if(lbISGestionarHV){
				FacesContext.getCurrentInstance().getExternalContext().redirect("gestionarHojaDeVida.xhtml?");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("informacionPersonal.xhtml?");	
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 
	 */
	public void onTabChange(){
		validaCambioPestanna();
	}
	
	public boolean validaCambioPestanna(){
		if( (changedTab !=tabIndexModDatosPersonales) || (tabGeneralHojaVida != tabIndexModHVGeneral)
				||(tabIndexModEducacion!=tabEducacion)  ||( tabGerenciaPublica!= tabIndexModGerenciaPublica)
				||(tabIndexDocumentosAdicionales!=tabDocumentosAdicionales)){
			if(uiDatosModificados!=null && uiDatosModificados.getValue()!=null
					&& DATO_MODIFICADO.equals(uiDatosModificados.getValue())){
				RequestContext.getCurrentInstance().execute("$('#dlgValidSaveForm').modal('show');");
				changedTab = tabIndexModDatosPersonales;
				tabGeneralHojaVida = tabIndexModHVGeneral;
				tabEducacion = tabIndexModEducacion;
				tabGerenciaPublica = tabIndexModGerenciaPublica;
				tabDocumentosAdicionales=tabIndexDocumentosAdicionales;
				return false;
			}else{
				tabIndexModDatosPersonales = changedTab;
				tabIndexModHVGeneral = tabGeneralHojaVida;
				tabIndexModEducacion=tabEducacion;
				tabIndexModGerenciaPublica = tabGerenciaPublica;
				tabIndexDocumentosAdicionales=tabDocumentosAdicionales;
				if(tabIndexModHVGeneral==5){
					ELContext elContext = FacesContext.getCurrentInstance().getELContext();
					GerenciaPublicaHVBean mBGerenciaPublicaHVBean = (GerenciaPublicaHVBean) elContext.getELResolver().getValue(elContext,null, "gerenciaPublicaHVBean");
					mBGerenciaPublicaHVBean.cargarListas();
				}
				return true;
			}
		}
		return true;
	}
	
	public UIOutput getUiDatosModificados() {
		return uiDatosModificados;
	}
	public void setUiDatosModificados(UIOutput uiDatosModificados) {
		this.uiDatosModificados = uiDatosModificados;
	}
	/*
	 * setEditado sobra
	 * */
	/*Bug no refresca dirección*/
	private String strDireccionResidencia;
	public String getStrDireccionResidencia() {
		return strDireccionResidencia;
	}
	public void setStrDireccionResidencia(String strDireccionResidencia) {
		this.strDireccionResidencia = strDireccionResidencia;
	}

	public boolean isLbISGestionarHV() {
		return lbISGestionarHV;
	}

	public void setLbISGestionarHV(boolean lbISGestionarHV) {
		this.lbISGestionarHV = lbISGestionarHV;
	}
	
	
	public boolean isFlgOtraOrientacion() {
		return flgOtraOrientacion;
	}

	public void setFlgOtraOrientacion(boolean flgOtraOrientacion) {
		this.flgOtraOrientacion = flgOtraOrientacion;
	}

	/*Bug no refresca indicativooficina*/
	public void actualizaIndicativoOficina(){
		if(datosContacto.getIndicativoOficina()!=null)
			datosContacto.setIndicativoOficinaString(datosContacto.getIndicativoOficina().toString());
	}

	public int getTabGerenciaPublica() {
		return tabGerenciaPublica;
	}
	public void setTabGerenciaPublica(int tabGerenciaPublica) {
		this.tabGerenciaPublica = tabGerenciaPublica;
	}
	
	/*Datos Adiconales*/
	public void mostrarOtraOrientacion() {
		if (datosAdicionales.getCodOrientacionSexual() != null && datosAdicionales.getCodOrientacionSexual().equals(381)) {
			flgOtraOrientacion = true;
		} else {
			flgOtraOrientacion = false;
		}
	}
	private void mostrarOcultarUstedEs(){
		try{
			codParametricaOcultaOrientacionSexual = (long) TipoParametro.OCULTA_ORIENTACION_SEXUAL_DATOS_ADICIONALES.getValue();
			if(codParametricaOcultaOrientacionSexual!=null && codParametricaOcultaOrientacionSexual >0){
				Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(codParametricaOcultaOrientacionSexual));
				if(parametrica!=null && parametrica.getValorParametro()!=null && "S".equals(parametrica.getValorParametro())){
					flgOcultaOrientacionSexual = true;
				}else{
					flgOcultaOrientacionSexual = false;
				}
			}else{
				flgOcultaOrientacionSexual = false;
			}
		}catch(Exception z){
			flgOcultaOrientacionSexual = false;
			logger.error(z.getMessage(), z.getMessage());
		}
		
	}
	
	/**
	 * Metodo que controla el panel redes sociales.
	 */
	public void mostrarOcultarRedesSociales() {
		try{
			Long parametricaRedes = (long) TipoParametro.DATOS_ADICIONALES_MOSTRAR_REDES_SOCIALES.getValue();
			if(parametricaRedes!=null && parametricaRedes >0){
				Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(parametricaRedes));
				if(parametrica!=null && parametrica.getValorParametro()!=null && "S".equals(parametrica.getValorParametro())){
					blnMostrarRedesSociales = true;
				}else{
					blnMostrarRedesSociales = false;
				}
			}else{
				blnMostrarRedesSociales = false;
			}
		}catch(Exception z){
			blnMostrarRedesSociales = false;
			logger.error(z.getMessage(), z.getMessage());
		}
	}
	
	/**
	 * Metodo que controla el tab 'Datos adicionales'
	 */
	public void mostrarOcultarTabDatosAdicionales() {
		try{
			Long parametricaMostrarTab = (long) TipoParametro.DATOS_ADICIONALES_MOSTRAR_PESTANA.getValue();
			if(parametricaMostrarTab!=null && parametricaMostrarTab >0){
				Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(parametricaMostrarTab));
				if(parametrica!=null && parametrica.getValorParametro()!=null && "S".equals(parametrica.getValorParametro()) && 
						parametrica.getFlgActivo() !=null && parametrica.getFlgEstado() == 1){
					blnMostrarTabDatosAdicionales = true;
				}else{
					blnMostrarTabDatosAdicionales = false;
				}
			}else{
				blnMostrarTabDatosAdicionales = false;
			}
		}catch(Exception z){
			blnMostrarTabDatosAdicionales = false;
			logger.error(z.getMessage(), z.getMessage());
		}
		
	}
	
	
	public void refrescarProgresoHojaVida(){
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		InformacionPersonalBean mBGerenciaPublicaHVBean = (InformacionPersonalBean) elContext.getELResolver().getValue(elContext,null, "informacionPersonalBean");
		mBGerenciaPublicaHVBean.progresoHojadeVida();
		progresoHojadeVida = mBGerenciaPublicaHVBean.getProgresoHojadeVida();
		persona.setPorcentajeHV (BigDecimal.valueOf(progresoHojadeVida));
		boolean valid = ComunicacionServiciosHV.updatePersona(persona);
		if(!valid)
			logger.error("Error","Error refrescando porcentaje de diligenciamiento de hoja de vida");
	}

	public int getProgresoHojadeVida() {
		return progresoHojadeVida;
	}

	public void setProgresoHojadeVida(int progresoHojadeVida) {
		this.progresoHojadeVida = progresoHojadeVida;
	}

	public boolean isLbISGestionarSoloConsultaHV() {
		return lbISGestionarSoloConsultaHV;
	}

	public void setLbISGestionarSoloConsultaHV(boolean lbISGestionarSoloConsultaHV) {
		this.lbISGestionarSoloConsultaHV = lbISGestionarSoloConsultaHV;
	}
	
	/**
	 * @return the maxNumOf
	 */
	public BigDecimal getMaxNumOf() {
		return maxNumOf;
	}

	/**
	 * @param maxNumOf the maxNumOf to set
	 */
	public void setMaxNumOf(BigDecimal maxNumOf) {
		this.maxNumOf = maxNumOf;
	}

	/**
	 * @return the blnMostrarRedesSociales
	 */
	public boolean isBlnMostrarRedesSociales() {
		return blnMostrarRedesSociales;
	}

	/**
	 * @param blnMostrarRedesSociales the blnMostrarRedesSociales to set
	 */
	public void setBlnMostrarRedesSociales(boolean blnMostrarRedesSociales) {
		this.blnMostrarRedesSociales = blnMostrarRedesSociales;
	}
	
	public boolean isFlgOcultaOrientacionSexual() {
		return flgOcultaOrientacionSexual;
	}
	public void setFlgOcultaOrientacionSexual(boolean flgOcultaOrientacionSexual) {
		this.flgOcultaOrientacionSexual = flgOcultaOrientacionSexual;
	}

	/**
	 * @return the blnMostrarTabDatosAdicionales
	 */
	public boolean isBlnMostrarTabDatosAdicionales() {
		return blnMostrarTabDatosAdicionales;
	}

	/**
	 * @param blnMostrarTabDatosAdicionales the blnMostrarTabDatosAdicionales to set
	 */
	public void setBlnMostrarTabDatosAdicionales(boolean blnMostrarTabDatosAdicionales) {
		this.blnMostrarTabDatosAdicionales = blnMostrarTabDatosAdicionales;
	}

	/**
	 * @return the maxNumOfF
	 */
	public BigDecimal getMaxNumOfF() {
		return maxNumOfF;
	}

	/**
	 * @param maxNumOfF the maxNumOfF to set
	 */
	public void setMaxNumOfF(BigDecimal maxNumOfF) {
		this.maxNumOfF = maxNumOfF;
	}

	/**
	 * @return the minNumOfF
	 */
	public BigDecimal getMinNumOfF() {
		return minNumOfF;
	}

	/**
	 * @param minNumOfF the minNumOfF to set
	 */
	public void setMinNumOfF(BigDecimal minNumOfF) {
		this.minNumOfF = minNumOfF;
	}
	
	/**
	 * 
	 * @param expeiencias
	 * @return arreglo con el tiempo de las experiencias
	 * [0] total años publicos
	 * [1] total meses publicos
	 * [2] total dias publicos
	 * [3] total años privados
	 * [4] total meses privados
	 * [5] total dias privados
	 * [6] total años totales
	 * [7] total mess totales
	 * [8] total dias totales
	 * 
	 * Para la opción de Imprimir Hoja de vida se realizó un Control de cambios expuesto en el Issue 0006010. Para lo cual se añadieron las siguientes variables:
	 * [9]  total años publicos sin experiencias contratistas
	 * [10] total meses publicos sin experiencias publicas
	 * [11] total dias publicos de experiencias no contratistas
	 * [12] total de años privados
	 * [13] total de meses privados
	 * [14] total de dias privados
	 * [15] total de años independientes (Experiencias con cargo 'Contratista')
	 * [16] total de mes independiente (Experiencias con cargo 'Contratista')
	 * [17] total de dias independiente (Experiencias con cargo 'Contratista')
	 * [18] Total de anños
	 * [19] Total de meses
	 * [20] Total de Días
	 */
	public static Long[] calculartiempoExperienciaprofesional(List<ExperienciaProfesionalExt> expeiencias){
		Long[] tiempos= new Long[21];
    	long aniopublico	= 0; // variable para el valor año
    	long mespublico 	= 0;// variable para el valor mes
    	long diapublico 	= 0;// variable para el valor dia
    	long anioprivado 	= 0;// variable para el valor año
    	long mesprivado 	= 0;// variable para el valor mes
    	long diaprivado 	= 0; // variable para el valor dia    
    	long aniototal 		= 0;// variable para el valor año
    	long mestotal 		= 0;// variable para el valor mes
    	long diatotal 		= 0; // variable para el valor dia
    	
    	/*Para la opción de Imprimir Hoja de Vida*/
    	long aniopublicoImprimir 		= 0; // variable para el valor año publico (Sin cargos contratistas)
    	long mespublicoImprimir 		= 0;// variable para el valor mes publico (Sin cargos contratistas)
    	long diapublicoImprimir 		= 0;// variable para el valor dia publico (Sin cargos contratistas)
    	
    	long anioprivadoImprimir 		= 0;// variable para el valor año privado (Sin cargos contratistas)
    	long mesprivadoImprimir 		= 0;// variable para el valor mes año privado (Sin cargos contratistas)
    	long diaprivadoImprimir	 		= 0;// variable para el valor dia año privado (Sin cargos contratistas)
    	
    	long anioIndependienteImprimir 	= 0;// variable para el valor año (Solo cargos Contratistas)
    	long mesIndependienteImprimir 	= 0;// variable para el valor mes (Solo cargos Contratistas)
    	long diaIndependienteImprimir	= 0;// variable para el valor dia (Solo cargos Contratistas)
    	
    	long aniototalImprimir 			= 0;// variable para el valor año
    	long mestotalImprimir 			= 0;// variable para el valor mes
    	long diatotalImprimir 			= 0; // variable para el valor dia
    		
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);		
		Calendar calendar;
		Date fechaFinFinal;
		
		if(!expeiencias.isEmpty() || expeiencias != null) {
			for (ExperienciaProfesionalExt experienciaProfesional : expeiencias) {
				
				Date fechaInicialF = experienciaProfesional.getFechaIngreso();
				Date fechaFinF = experienciaProfesional.getFechaRetiro() != null ? experienciaProfesional.getFechaRetiro() : new Date();
				
				if(experienciaProfesional.getCodJornadaLaboral()!=null) {	
					calendar = Calendar.getInstance();
					calendar.setTime(fechaFinF); 
					calendar.add(Calendar.DAY_OF_YEAR, 1);  
					fechaFinFinal = calendar.getTime();
					
					ChronoLocalDate from = ChronoLocalDate.from(formatter.parse(sdf.format(fechaInicialF)));
					ChronoLocalDate to = ChronoLocalDate.from(formatter.parse(sdf.format(fechaFinFinal)));
					ChronoPeriod period = ChronoPeriod.between(from, to);	
					int dias, meses, annos;
					dias = (int) period.get(DAYS);
					meses = (int) period.get(MONTHS);
					annos =  (int) period.get(YEARS);	
					        
					if(experienciaProfesional.getCodJornadaLaboral().equals(TipoParametro.MEDIO_TIEMPO.getValue())) {
						
					    int diasTotalMedioTiempo = (int) (dias +(annos * 360 ) + (meses * DIAS_MES));
					    dias = diasTotalMedioTiempo / 2;
					    meses = annos = 0; 
					}else if(experienciaProfesional.getCodJornadaLaboral().equals(TipoParametro.TIEMPO_PARCIAL.getValue())){ 
						
						
						/*long horasB = 192;*/
						long horasMes =0;
							
						if(experienciaProfesional.getHorasPromedioMes()!=null) {
							if(experienciaProfesional.getHorasPromedioMes() > HORAS_LIMITE ) {
								horasMes = HORAS_LIMITE;
							}else{
								horasMes  = experienciaProfesional.getHorasPromedioMes();
							}
						}
							
						if(horasMes > 0) {
							
							int diasTotalParcialTiempo = (int) (dias +(annos * 360 ) + (meses * DIAS_MES));
							double porcparcial = (horasMes * 100 )/192;
							diasTotalParcialTiempo = (int) (diasTotalParcialTiempo * (porcparcial/100));
						    dias = diasTotalParcialTiempo;
						    meses = annos = 0; 							
							
						}
					 }
					/*Para la opción de formulario de Experiencia Profesional*/
					if (experienciaProfesional.getCodTipoEntidad()!=null && experienciaProfesional.getCodTipoEntidad().equals(TipoParametro.ENTIDAD_PUBLICA.getValue())){
						aniopublico =aniopublico+annos;
						mespublico =mespublico+meses;
						diapublico=diapublico+dias;
					}else{
						anioprivado =anioprivado+annos;
						mesprivado =mesprivado+meses;
						diaprivado=diaprivado+dias;
					}
					/*Para la opción de Imprimir Hoja de Vida*/
					if(experienciaProfesional.getCodTipoEntidad() !=null && experienciaProfesional.getCodTipoEntidad().equals(TipoParametro.ENTIDAD_PUBLICA.getValue())) {
						/*Experiencias publicas que no sean por contrato*/
						if(experienciaProfesional.getFlgContratista() != null && experienciaProfesional.getFlgContratista() == 0 ) {
							aniopublicoImprimir +=annos;
							mespublicoImprimir  +=meses;
							diapublicoImprimir  +=dias;
						}else {
							/*Experiencias publicas que sean por contrato*/
							anioIndependienteImprimir 	+= annos;
					    	mesIndependienteImprimir 	+= meses;
					    	diaIndependienteImprimir	+= dias;
						}
					}
					
					/*Experiencias privadas, independiente si es contratista*/
					if(experienciaProfesional.getCodTipoEntidad() !=null && (experienciaProfesional.getCodTipoEntidad().equals(TipoParametro.ENTIDAD_PRIVADA.getValue())||
							experienciaProfesional.getCodTipoEntidad().equals(TipoParametro.ENTIDAD_PRIV_FUNC_PUBLICAS.getValue()))) {
						anioprivadoImprimir +=annos;
						mesprivadoImprimir  +=meses;
						diaprivadoImprimir  +=dias;
					}			
				}

			}
			
			/**AjustarFechas formulario experiencias*/
			while (diaprivado >= DIAS_MES ){
				diaprivado = diaprivado - DIAS_MES;
				mesprivado++;
			}
			while (mesprivado >= MESES_ANIO ){
				mesprivado = mesprivado - MESES_ANIO;
				anioprivado++;
			}	
			while (diapublico >= DIAS_MES ){
				diapublico = diapublico - DIAS_MES;
				mespublico++;
			}
			while (mespublico >= MESES_ANIO ){
				mespublico = mespublico - MESES_ANIO;
				aniopublico++;
			}				
			
			aniototal=aniototal+aniopublico+anioprivado;
			mestotal=mestotal+mespublico+mesprivado;
			diatotal=diatotal+diapublico+diaprivado;
			
			while (diatotal >= DIAS_MES ){
				diatotal = diatotal - DIAS_MES;
				mestotal++;
			}
			while (mestotal >= MESES_ANIO ){
				mestotal = mestotal - MESES_ANIO;
				aniototal++;
			}
			
			/** AjustarFechas opción hoja de Vida*/
			while (diaprivadoImprimir >= DIAS_MES ){
				diaprivadoImprimir -= DIAS_MES;
				mesprivadoImprimir ++;
			}
			while (mesprivadoImprimir >= MESES_ANIO ){
				mesprivadoImprimir -=  MESES_ANIO;
				anioprivadoImprimir++;
			}	
			
			while (diapublicoImprimir >= DIAS_MES ){
				diapublicoImprimir -= DIAS_MES;
				mespublicoImprimir++;
			}
			while (mespublicoImprimir >= MESES_ANIO ){
				mespublicoImprimir -= MESES_ANIO;
				aniopublicoImprimir++;
			}
			
			while (diaIndependienteImprimir >= DIAS_MES ){
				diaIndependienteImprimir -= DIAS_MES;
				mesIndependienteImprimir++;
			}
			while (mesIndependienteImprimir >= MESES_ANIO ){
				mesIndependienteImprimir -= MESES_ANIO;
				anioIndependienteImprimir++;
			}	
			
			aniototalImprimir	= aniototalImprimir + aniopublicoImprimir + anioprivadoImprimir + anioIndependienteImprimir;
			mestotalImprimir	= mestotalImprimir  + mespublicoImprimir  + mesprivadoImprimir  + mesIndependienteImprimir;
			diatotalImprimir	= diatotalImprimir  + diapublicoImprimir  + diaprivadoImprimir  + diaIndependienteImprimir;
			
			while (diatotalImprimir >= DIAS_MES ){
				diatotalImprimir = diatotalImprimir - DIAS_MES;
				mestotalImprimir++;
			}
			while (mestotalImprimir >= MESES_ANIO ){
				mestotalImprimir = mestotalImprimir - MESES_ANIO;
				aniototalImprimir++;
			}
					
			tiempos[0]=aniopublico;
			tiempos[1]=mespublico;
			tiempos[2]=diapublico;
			tiempos[3]=anioprivado;
			tiempos[4]=mesprivado;
			tiempos[5]=diaprivado;
			tiempos[6]=aniototal;
			tiempos[7]=mestotal;
			tiempos[8]=diatotal;
			
			tiempos[9]=aniopublicoImprimir;
			tiempos[10]=mespublicoImprimir;
			tiempos[11]=diapublicoImprimir;
			tiempos[12]=anioprivadoImprimir;
			tiempos[13]=mesprivadoImprimir;
			tiempos[14]=diaprivadoImprimir;
			tiempos[15]=anioIndependienteImprimir;
			tiempos[16]=mesIndependienteImprimir;
			tiempos[17]=diaIndependienteImprimir;
			tiempos[18]=aniototalImprimir;
			tiempos[19]=mestotalImprimir;
			tiempos[20]=diatotalImprimir;
		
		}else{
			tiempos[0]=0l;
			tiempos[1]=0l;
			tiempos[2]=0l;
			tiempos[3]=0l;
			tiempos[4]=0l;
			tiempos[5]=0l;
			tiempos[6]=0l;
			tiempos[7]=0l;
			tiempos[8]=0l;
			tiempos[9]=0l;
			tiempos[10]=0l;
			tiempos[11]=0l;
			tiempos[12]=0l;
			tiempos[13]=0l;
			tiempos[14]=0l;
			tiempos[15]=0l;
			tiempos[16]=0l;
			tiempos[17]=0l;
			tiempos[18]=0l;
			tiempos[19]=0l;
			tiempos[20]=0l;
		}
		return tiempos;
	}

	public static int calculartiempoExperienciaProfesionalTotal(List<ExperienciaProfesionalExt> experiences){

		int totalDiasTiempoCompleto 			= 0;
		double totalDiasTrabajadosMedioTiempo	= 0.0d; 
		double totalHorasTrabajadasParcial 		= 0.0d;
		double horasTrabajadasDia 				= 0.0d;	
		int codJornadaLaboral;
		double totalHorasTrabajadasParcialPorMes = 0.0d;		
		boolean esPrimeraIteracionParcial = Boolean.TRUE;
		Map<LocalDate, Integer> mapFechasCompletoMedio 	= new HashMap();
		Map<LocalDate, Integer> mapFechaMedioParcial 	= new HashMap();
		Map<LocalDate, Integer> mapFechaParcial 		= new HashMap();
		
		//Ciclo que almacena fechas de tiempo completo y medio y realiza sumatoria de dias en tiempo completo 
				for(ExperienciaProfesionalExt experience:experiences) {
					
					 LocalDate startDate 	= experience.getFechaIngreso()!=null ? experience.getFechaIngreso().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():  LocalDate.now();
		             LocalDate endDate 		= experience.getFechaRetiro() !=null ? experience.getFechaRetiro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():  LocalDate.now();
		             codJornadaLaboral 		= experience.getCodJornadaLaboral()!= null ? experience.getCodJornadaLaboral(): 0;
		             
					for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
						//System.out.println("date:" + date.getDayOfMonth() );				
						if(!mapFechasCompletoMedio.containsKey(date) && (codJornadaLaboral == TipoParametro.MEDIO_TIEMPO.getValue() || codJornadaLaboral == 257)) { //259 medio 257 completo
							mapFechasCompletoMedio.put(date, codJornadaLaboral);
							if (codJornadaLaboral == 257) { // completo 
								totalDiasTiempoCompleto++;
							}
						}
					}			
				}
				
				//Ciclo que almacena fechas que coincidan en tiempo completo y medio realiza sumatoria de horas tiempo completo y medio 
				//Realiza sumatoria de horas tiempo parcial cuyas fechas no coincidan con tiempo completo y medio
				for(ExperienciaProfesionalExt experience:experiences) {
					LocalDate startDate 	= experience.getFechaIngreso()!=null ? experience.getFechaIngreso().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():  LocalDate.now();
		            LocalDate endDate 		= experience.getFechaRetiro() !=null ? experience.getFechaRetiro().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():  LocalDate.now();
		            codJornadaLaboral 		= experience.getCodJornadaLaboral()!= null ? experience.getCodJornadaLaboral(): 0;
		            
		            if(codJornadaLaboral == TipoParametro.TIEMPO_PARCIAL.getValue()) { //parcial
		            	
		            	int horasPromedioMensual = experience.getHorasPromedioMes();            	
		            	
		        		double horasPorDia = Double.parseDouble(String.valueOf(horasPromedioMensual))/30d;        			
		        		int nroMes = 0;        		
		        		esPrimeraIteracionParcial = true;
		        		totalHorasTrabajadasParcialPorMes = 0.0d;        		        		
		        		
		            	for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {              		           		
		            		
		            		if(mapFechasCompletoMedio.containsKey(date) && !mapFechaMedioParcial.containsKey(date)) {
		            			codJornadaLaboral = mapFechasCompletoMedio.get(date);
		            			if (codJornadaLaboral == TipoParametro.MEDIO_TIEMPO.getValue()) { //medio tiempo 4.5 
		            				mapFechaMedioParcial.put(date, codJornadaLaboral);
		        					horasTrabajadasDia = horasPorDia + HORAS_MEDIO_TIEMPO;
		        					if(horasTrabajadasDia > HORAS_LABORAL_DIA) {
		        						totalHorasTrabajadasParcial += HORAS_LABORAL_DIA;        						
		        						totalHorasTrabajadasParcialPorMes += HORAS_LABORAL_DIA; 
		        					}else {
		        						totalHorasTrabajadasParcial += horasTrabajadasDia;
		        						totalHorasTrabajadasParcialPorMes += horasTrabajadasDia; 
		        					}
		                    		  
		            			}            			
		            		}else {
		            			if(!mapFechaParcial.containsKey(date)) {
		            				mapFechaParcial.put(date, codJornadaLaboral);
		            				totalHorasTrabajadasParcial += horasPorDia;
		                			totalHorasTrabajadasParcialPorMes += horasPorDia;
		            			}            			
		            		} 
		            		
		            		if(nroMes != date.getMonthValue() || date.isEqual(endDate) ) {
		            			
		            			nroMes = date.getMonthValue();            			
		            			
		            			if(totalHorasTrabajadasParcialPorMes > tiempoMaximoMensual ) {  
		            				double auxtotalHorasTrabajadasParcialPorMes = totalHorasTrabajadasParcialPorMes - tiempoMaximoMensual;
		            				totalHorasTrabajadasParcial = totalHorasTrabajadasParcial - auxtotalHorasTrabajadasParcialPorMes;     			
		            			}            			
		            			if(!esPrimeraIteracionParcial) {
		            				totalHorasTrabajadasParcialPorMes = 0.0d;     
		            			}else {
		            				esPrimeraIteracionParcial = false;
		            			}
		            			      			
		            		}   
		            		     		      		
		    			}		
		            }					
				}
				
				//Ciclo que realiza sumatoria de dias de fechas de medio tiempo que no coincidan con las de completo, (medio + parcial)
				for (Map.Entry<LocalDate, Integer> entry : mapFechasCompletoMedio.entrySet()) {
					LocalDate fecha = (LocalDate)entry.getKey();
					codJornadaLaboral = (Integer)entry.getValue(); 
					if(!mapFechaMedioParcial.containsKey(fecha) && codJornadaLaboral == TipoParametro.MEDIO_TIEMPO.getValue()) {
						totalDiasTrabajadosMedioTiempo +=0.5;
					}
				}
				
				double diasTiempoParcial  = totalHorasTrabajadasParcial/ HORAS_LABORAL_DIA;	
				double totalidadDias = totalDiasTiempoCompleto + totalDiasTrabajadosMedioTiempo + diasTiempoParcial;
				
				return (int)Math.ceil(totalidadDias) ;
	}
	
	
	public static int calculartiempoExperienciaDocente(List<ExperienciaDocenteExt> experiences){

		int totalDiasTiempoCompleto = 0;
		double totalDiasTrabajadosMedioTiempo= 0.0d; 
		double totalHorasTrabajadasParcial = 0.0d;
		double horasTrabajadasDia = 0.0d;	
		int codJornadaLaboral;
		double totalHorasTrabajadasParcialPorMes = 0.0d;		
		boolean esPrimeraIteracionParcial = Boolean.TRUE;
		Map<LocalDate, Integer> mapFechasCompletoMedio = new HashMap();
		Map<LocalDate, Integer> mapFechaMedioParcial = new HashMap();
		Map<LocalDate, Integer> mapFechaParcial = new HashMap();
		
		//Ciclo que almacena fechas de tiempo completo y medio y realiza sumatoria de dias en tiempo completo 
				for(ExperienciaDocenteExt experience:experiences) {
					LocalDate startDate 	= experience.getFechaIngreso()!=null ? experience.getFechaIngreso().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():  LocalDate.now();
		            LocalDate endDate 		= experience.getFechaFinalizacion() !=null ? experience.getFechaFinalizacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():  LocalDate.now();
		            codJornadaLaboral 		= experience.getCodJornadaLaboral()!= null ? experience.getCodJornadaLaboral(): 0;
		             
					for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
						//System.out.println("date:" + date.getDayOfMonth() );				
						if(!mapFechasCompletoMedio.containsKey(date) && (codJornadaLaboral == TipoParametro.MEDIO_TIEMPO.getValue() || codJornadaLaboral == 257)) { //259 medio 257 completo
							mapFechasCompletoMedio.put(date, codJornadaLaboral);
							if (codJornadaLaboral == 257) { // completo 
								totalDiasTiempoCompleto++;
							}
						}
					}			
				}
				
				//Ciclo que almacena fechas que coincidan en tiempo completo y medio realiza sumatoria de horas tiempo completo y medio 
				//Realiza sumatoria de horas tiempo parcial cuyas fechas no coincidan con tiempo completo y medio
				for(ExperienciaDocenteExt experience:experiences) {
				LocalDate startDate 	= experience.getFechaIngreso()!=null ? experience.getFechaIngreso().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():  LocalDate.now();
	            LocalDate endDate 		= experience.getFechaFinalizacion() !=null ? experience.getFechaFinalizacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():  LocalDate.now();
	            codJornadaLaboral 		= experience.getCodJornadaLaboral()!= null ? experience.getCodJornadaLaboral(): 0;
		            
		            if(codJornadaLaboral == TipoParametro.TIEMPO_PARCIAL.getValue()) { //parcial
		            	
		            	int horasPromedioMensual = experience.getHorasPromedioMes();            	
		            	
		        		double horasPorDia = Double.parseDouble(String.valueOf(horasPromedioMensual))/30d;        			
		        		int nroMes = 0;        		
		        		esPrimeraIteracionParcial = true;
		        		totalHorasTrabajadasParcialPorMes = 0.0d;        		        		
		        		
		            	for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {              		           		
		            		
		            		if(mapFechasCompletoMedio.containsKey(date) && !mapFechaMedioParcial.containsKey(date)) {
		            			codJornadaLaboral = mapFechasCompletoMedio.get(date);
		            			if (codJornadaLaboral == TipoParametro.MEDIO_TIEMPO.getValue()) { //medio tiempo 4.5 
		            				mapFechaMedioParcial.put(date, codJornadaLaboral);
		        					horasTrabajadasDia = horasPorDia + HORAS_MEDIO_TIEMPO;
		        					if(horasTrabajadasDia > HORAS_LABORAL_DIA) {
		        						totalHorasTrabajadasParcial += HORAS_LABORAL_DIA;        						
		        						totalHorasTrabajadasParcialPorMes += HORAS_LABORAL_DIA; 
		        					}else {
		        						totalHorasTrabajadasParcial += horasTrabajadasDia;
		        						totalHorasTrabajadasParcialPorMes += horasTrabajadasDia; 
		        					}
		                    		  
		            			}            			
		            		}else {
		            			if(!mapFechaParcial.containsKey(date)) {
		            				mapFechaParcial.put(date, codJornadaLaboral);
		            				totalHorasTrabajadasParcial += horasPorDia;
		                			totalHorasTrabajadasParcialPorMes += horasPorDia;
		            			}            			
		            		} 
		            		
		            		if(nroMes != date.getMonthValue() || date.isEqual(endDate) ) {
		            			
		            			nroMes = date.getMonthValue();            			
		            			
		            			if(totalHorasTrabajadasParcialPorMes > HORAS_LIMITE ) {  
		            				double auxtotalHorasTrabajadasParcialPorMes = totalHorasTrabajadasParcialPorMes - HORAS_LIMITE;
		            				totalHorasTrabajadasParcial = totalHorasTrabajadasParcial - auxtotalHorasTrabajadasParcialPorMes;     			
		            			}            			
		            			if(!esPrimeraIteracionParcial) {
		            				totalHorasTrabajadasParcialPorMes = 0.0d;     
		            			}else {
		            				esPrimeraIteracionParcial = false;
		            			}
		            			      			
		            		}   
		            		     		      		
		    			}		
		            }					
				}
				
				//Ciclo que realiza sumatoria de dias de fechas de medio tiempo que no coincidan con las de completo, (medio + parcial)
				for (Map.Entry<LocalDate, Integer> entry : mapFechasCompletoMedio.entrySet()) {
					LocalDate fecha = (LocalDate)entry.getKey();
					codJornadaLaboral = (Integer)entry.getValue(); 
					if(!mapFechaMedioParcial.containsKey(fecha) && codJornadaLaboral == TipoParametro.MEDIO_TIEMPO.getValue()) {
						totalDiasTrabajadosMedioTiempo +=0.5;
					}
				}
				
				double diasTiempoParcial  = totalHorasTrabajadasParcial/ HORAS_LABORAL_DIA;	
				double totalidadDias = totalDiasTiempoCompleto + totalDiasTrabajadosMedioTiempo + diasTiempoParcial;
				
				return (int)Math.ceil(totalidadDias) ;
	}
	
	
	public String getStrPageSubtitle() {
		return strPageSubtitle;
	}

	public void setStrPageSubtitle(String strPageSubtitle) {
		this.strPageSubtitle = strPageSubtitle;
	}
	
	
	/**
	 * Upload Hadoop Sisteme Operativo
	 */
	public static ErrorMensajes uploadHaddopApi(String carpeta, String tipologia, String nombreArchivo, String numeroIdentificacion) {
        final ErrorMensajes errorM = new ErrorMensajes();
        String urlArchivo = "/getShowFile/";
        try {
        	carpeta=carpeta.replace("/", "")+"/";
            String nombreArchivoFinal = construirNombre(nombreArchivo, tipologia, numeroIdentificacion);
            InputStream inputStream = new FileInputStream(new File(nombreArchivo));
        	if(!cargueArchivosHadoop(nombreArchivo,inputStream, carpeta, tipologia, nombreArchivoFinal, numeroIdentificacion)) {
        		errorM.setError(Boolean.valueOf(true));
                errorM.setMensaje("Error al Guardar Archivo");
                errorM.setCodigoEstado(500);
                return errorM;
        	}
        	errorM.setError(Boolean.valueOf(false));
            errorM.setMensaje("Archivo Almacenado Correctamente");
            errorM.setNombreArchivo(nombreArchivoFinal);
            urlArchivo = String.valueOf(urlArchivo) + carpeta + nombreArchivoFinal;
            errorM.setUrlArchivo(urlArchivo);
            errorM.setCodigoEstado(200);
            return errorM;
        } catch (Exception e) {
            errorM.setError(Boolean.valueOf(true));
            errorM.setMensaje("Error al Guardar Archivo");
            errorM.setCodigoEstado(500);
			e.printStackTrace();
            return errorM;
        }
    }
	public static String construirNombre(String path, String tipologia, String numeroIdentificacion) {
		String nombreArchivo = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
		LocalDateTime now = LocalDateTime.now();
		String[] pathArray = path.split("\\.");
		String extension = pathArray[pathArray.length-1];
		extension = extension.toLowerCase();
		String autonumerico = dtf.format(now);
		nombreArchivo=tipologia+autonumerico+numeroIdentificacion+"."+extension;
		return nombreArchivo;
	}
    
	public static boolean uploadLinux(String path, String carpeta, String tipologia, String nombreArchivo, String numeroIdentificacion) {
		String finalUrl="";
		logger.log().error("******uploadLinux:******* ", path);
		try {
	    	String configPath        = System.getProperty("CONFIG_PATH");
	    	System.out.println("configPath: "+configPath);
	    	finalUrl = "bash "+configPath+"co/gov/dafp/sigep2/hadoopso/upload_file.sh "+path+" "+nombreArchivo+" "+carpeta.replace("/","")+" "+HADOOP_USER_REPO.replace("/", "");
	    	logger.log().error("finalUrl: ", finalUrl);
			Process process = Runtime.getRuntime().exec(finalUrl);
			return true;
		} catch (Exception e) {
			logger.log().error("finalUrl()", finalUrl);
			logger.log().error("Exception uploadLinux()", e.getMessage());
			return false;
		}
	}
	
	public static boolean cargueArchivosHadoop(String path, InputStream in, String carpeta, String tipologia, String nombreArchivo, String numeroIdentificacion) {
		String finalUrl="";
		HttpURLConnection connection;
    	OutputStream output;
    	int codeResponse = 0;
		try {
			byte[] bFile  = IOUtils.toByteArray(in);
			String repositorio =HADOOP_USER_REPO;  
			String hadoopUrl = HADOOP_URL_BY_SO+repositorio+carpeta+nombreArchivo+"?op=CREATE&user.name=hdfs&namenoderpcaddress=cluster-hdfs&createflag=&createparent=true&overwrite=false";
		
	    	URL url = new URL(hadoopUrl);
	    	connection = (HttpURLConnection) url.openConnection();
	    	connection.setRequestMethod("PUT");
	    	connection.setDoOutput( true );
	    	output = connection.getOutputStream(); 
	    	IOUtils.write( bFile, output );
	    	output.close();
	    	codeResponse = connection.getResponseCode() ; 
	    	if(codeResponse == 200 || codeResponse == 201 ) {
	    		return true;
	    	}
			return false;
		} catch (Exception e) {
			logger.log().error("*****finalUrl()****", finalUrl);
			logger.log().error("Exception uploadWindows()", e.getMessage());
			return false;
		}
	}
	
	
	public static boolean uploadWindows(String path, InputStream in, String carpeta, String tipologia, String nombreArchivo, String numeroIdentificacion) {
		String finalUrl="";
		logger.log().error("********uploadWindows()******", path);
		try {
	    	String configPath        = System.getProperty("CONFIG_PATH");
			String curlUrl = configPath+"co/gov/dafp/sigep2/hadoopso/curl/curl-7.69.1-win64-mingw/bin/curl";
			logger.log().error("curlUrl: ", curlUrl);
			String options = " -i -X PUT -T ";
			String repositorio =HADOOP_USER_REPO; 
			String hadoopUrl = "\"" +HADOOP_URL_BY_SO+repositorio+carpeta+nombreArchivo+"?op=CREATE&user.name=hdfs&namenoderpcaddress=cluster-hdfs&createflag=&createparent=true&overwrite=false\"";
			finalUrl = curlUrl + options + path + " " + hadoopUrl;
			Process process = Runtime.getRuntime().exec(finalUrl);
			return true;
		} catch (Exception e) {
			logger.log().error("*****finalUrl()****", finalUrl);
			logger.log().error("Exception uploadWindows()", e.getMessage());
			return false;
		}
	}	
	
	
	
	


	public void actualizaModificacionHv(){
		try{
			getRolAuditoria().getId();
			hojaVida.setAudFechaActualizacion(DateUtils.getFechaSistema());
			hojaVida.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
			hojaVida.setAudCodRol((int) getRolAuditoria().getId());
			hojaVida.setAudCodUsuario(BigDecimal.valueOf( getUsuarioSesion().getId()));
			
			boolean valid = true;
			if(hojaVida.getCodHojaVida()!=null)
				valid = ComunicacionServiciosHV.setHojaVida(hojaVida);
			if(!valid){
				logger.error("Error", "Error actualizando encabezado de hoja de vida");
			}
		}catch(Exception z){
			logger.error("Error actualizando encabezado de hoja de vida", z.getMessage());
		}

	}

	public HojaVidaExt getHojaVida() {
		return hojaVida;
	}

	public void setHojaVida(HojaVidaExt hojaVida) {
		this.hojaVida = hojaVida;
	}
	
	public UploadedFile getCargarDocumentoDiscapacidad() {
		return cargarDocumentoDiscapacidad;
	}

	public void setCargarDocumentoDiscapacidad(UploadedFile cargarDocumentoDiscapacidad) {
		this.cargarDocumentoDiscapacidad = cargarDocumentoDiscapacidad;
	}
	public String getNombreArchivoDiscapacidad() {
		return nombreArchivoDiscapacidad;
	}

	public void setNombreArchivoDiscapacidad(String nombreArchivoDiscapacidad) {
		this.nombreArchivoDiscapacidad = nombreArchivoDiscapacidad;
	}

	public Boolean getFlgValidadoDiscapacidad() {
		return flgValidadoDiscapacidad;
	}

	public void setFlgValidadoDiscapacidad(Boolean flgValidadoDiscapacidad) {
		this.flgValidadoDiscapacidad = flgValidadoDiscapacidad;
	}
	
}