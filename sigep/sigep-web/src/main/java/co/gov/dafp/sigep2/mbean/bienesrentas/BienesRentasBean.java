package co.gov.dafp.sigep2.mbean.bienesrentas;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.AcreenciaObligacion;
import co.gov.dafp.sigep2.entities.BienesPatrimonio;
import co.gov.dafp.sigep2.entities.CuentasDeclaracion;
import co.gov.dafp.sigep2.entities.Declaracion;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.IngresosDeclaracion;
import co.gov.dafp.sigep2.entities.OtrosIngresosDeclaracion;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entities.PersonaParentesco;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.DireccionBean;
import co.gov.dafp.sigep2.mbean.ext.ActividadPrivadaExt;
import co.gov.dafp.sigep2.mbean.ext.BienesPatrimonioExt;
import co.gov.dafp.sigep2.mbean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.mbean.ext.IngresosDeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.OtrosIngresosDeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionJuntaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaParentescoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosBR;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.Direccion;
import co.gov.dafp.sigep2.sistema.FuncionExtraBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.UtilidadesFaces;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 * @author {Jose viscaya, David Torres, Andrés Jaramillo}
 * @version {3.0.3}
 * @Class {Esta clase contiene todos los métodos y variables necesarios para el
 *        funcionamiento de la sección de creación de declaraciones, todos los
 *        formularios de la sección Declaracion e historico de bienes y rentas
 *        se conectan con este bean
 * @Proyect {SIGEP}
 * @Company {ADA S.A}
 * @Module {Bienes y rentas}
 */

@ManagedBean(name = "bienesRentasBean")
@ViewScoped
@Named
public class BienesRentasBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1307010051869443311L;
	
	/**
     * Validacion seguridad de los botones
     */
	private boolean btnGuardadoDefinitivoDeshabilitado;
	private boolean btnSaveAcreenciaDeshabilitado;
	private boolean btnGuardarActividadDeshabilitado;
	private boolean btnGuardarPatrimonioDeshabilitado;
	private boolean btnGuardarCuentasDeshabilitado;
	private boolean btnGuardarDomicilioDeshabilitado;
	private boolean btnGuardarIngresoRentaDeshabilitado;
	private boolean btnGuardarOtroIngresoDeshabilitado;
	private boolean btnGuardarOtroIngreso2Deshabilitado;
	private boolean btnGuardarParientesDeshabilitado;
	private boolean btnGuardarParticipacionJuntasDeshabilitado;
	private Boolean blnPasaporte = false; 

	/**
     * Mensajes de Validacion seguridad de roles
     */
	private String btnGuardadoDefinitivoMensaje;
	private String btnSaveAcreenciaMensaje;
	private String btnGuardarActividadMensaje;
	private String btnGuardarPatrimonioMensaje;
	private String btnGuardarCuentasMensaje;
	private String btnGuardarDomicilioMensaje;
	private String btnGuardarIngresoRentaMensaje;
	private String btnGuardarOtroIngresoMensaje;
	private String btnGuardarOtroIngreso2Mensaje;
	private String btnGuardarParientesMensaje;
	private String btnGuardarParticipacionJuntasMensaje;

	private String pageCancel = "../persona/informacionPersonal.xhtml?recursoId=HojaDeVidaSubMenu";
	private SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
	private long codDeclaracion;
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	private int indexTab = 0;
	private int indexTabPos;
	private ExternalContext declarationSesion;
	private String idUltimaDecla = "";
	private String cumpleFlujo = "";
	private List<ParticipacionJuntaExt> participaciones;
	private ParticipacionJuntaExt participacionSeleccionada;
	private CuentasDeclaracion cuentasConfig;
	private CuentasDeclaracion cuentaSeleccionada;
	private List<CuentasDeclaracion> cuentas;
	private AcreenciaObligacion acreenciaOblicacion 				= new AcreenciaObligacion();
	private List<AcreenciaObligacion> listaAcreObligaciones 		= new ArrayList<>();
	private ActividadPrivadaExt actividadPrivada 					= new ActividadPrivadaExt();
	private List<ActividadPrivadaExt> listaActividadPrivada			= new ArrayList<>();
	private OtrosIngresosDeclaracionExt otrosIngresos 				= new OtrosIngresosDeclaracionExt();
	private List<OtrosIngresosDeclaracionExt> listaOtrosIngresos 	= new ArrayList<>();
	private BigDecimal totalOtrosIngresos = new BigDecimal(0);
	private DatoContactoExt domicilio;
	private BienesPatrimonioExt bienConfig;
	private BienesPatrimonioExt patrimonio;
	private BienesPatrimonioExt patrimonioSeleccionado = new BienesPatrimonioExt();
	private List<BienesPatrimonioExt> bienes;
	private BienesPatrimonioExt patrimonioSel;
	private PersonaParentescoExt parienteSeleccionado;
	private List<PersonaParentescoExt> parientes;
	private Declaracion declaracionRenta 							= new Declaracion();
	private List<Declaracion> listaDeclaracion 						= new ArrayList<>();
	private IngresosDeclaracionExt ingresosDeclaracion 				= new IngresosDeclaracionExt();
	private List<IngresosDeclaracionExt> listaIngresosDeclaracion 	= new ArrayList<>();
	private DeclaracionRentaBeanPrint declaracionParaImprimir;
	private List<DeclaracionRentaBeanPrint> listaDeclaracionParaImprimir;
	private boolean guardadoDefinitivoHabilitado;
	private boolean formParticipacionHabilitado;
	private boolean renderBienes;
	private boolean formBienesHabilitado;
	private boolean panelBienesHabilitado;
	private boolean habilitadoFormularioIngresoRenta	= false;
	private boolean habilitadoMensajeConfirmacion 		= false;
	private boolean sinOtrosIngresos;
	private boolean sinActividadPrivada;
	private boolean sinAcreencias;
	private boolean sinBienes = false;
	private boolean sinParticipacion;
	private boolean sinCuentas;
	private boolean estadoCalidadMiembro;
	private boolean estadoCalidadSocio;
	private boolean habilitadoDatosDomicilio;
	private boolean habilitadoConsanguinidadConjugues;
	private boolean habilitadoTabIngresoLaboral;
	private boolean habilitadoTabOtrosIngresos;
	private boolean habilitadoCuentas;
	private boolean habilitadoBienePatrimoniales;
	private boolean habilitadoTabActividadEconomica;
	private boolean habilitadoTabAcreenciasObligaciones;
	private boolean habilitadoParticipacionJuntas;
	private boolean estadoPanelParientes;
	private boolean sinParientes = false;
	private boolean estadoTabParientes;
	private boolean estadoRadioPariente;
	private boolean sinIngresosRentas;
	private boolean estadoControlesTablaCuentas;
	private boolean estadoControlesTablaBienes;
	private boolean estadoControlesTablaAcreencias;
	private boolean formAcreenciasHabilitado;
	private boolean formActividadHabilitado;
	private boolean estadoControlesTablaActividad;
	private boolean estadoControlesTablaParticipacion;
	private boolean modificacion 	= false;
	private boolean nuevaDec 		= false;
	private boolean estadoBtnOtroIn;
	private boolean estadoPanelDireccion;
	private String fechaInicioDeclaracion;
	private String fechaFinDeclaracion;
	private String btnGuardarIngresoRenta 	= TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_BTN_ADICIONAR, getLocale());
	private String btnGuardarOtrosIngresos 	= TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_BTN_ADICIONAR, getLocale());
	private String btnActividadEconomica 	= TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_BTN_ADICIONAR, getLocale());
	private String btnAcreencia 			= TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_BTN_ADICIONAR, getLocale());
	private String messageEncabezadoGuadarDefinitivo;
	private String tituloGuardaExito;
	private String textoGuardaExito;
	private String tituloGuardaFallido;
	private String textoGuardaFallido;
	private String tituloEliminaExito;
	private String textoEliminaExito;
	private String tituloEliminaError;
	private String textoEliminaError;
	private String textoGuardaParcial;
	private String textoGuardaDefini;
	private String textoGuardaParcialPar;
	private String paraCarga;
	private String idPersona = "";
	private String valorTotal = "0";
	private String sDeclaration = "";
	private String tipoDeclaracion = "";
	private String nombreTipoDeclaracionAct;
	private String mensajeParametrico = "";
	private String mensage;
	protected String rutaPdf;
	private final String MENSAJE_ERROR = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_ELIMINACION_FALLIDA, getLocale());
	private static final String INSTRUCCION_MOSTRAR_POPUP = "$('#dialogRespu').modal('show')";
	private static final String SEGURIDAD_ROLES_MSG = "Deshabilitado por seguridad Roles";
	private static final String NOMBRE_ELEMENTO = "tabViewBienesRentas";
	private static final String NOMBRE_ELEMENTO_TAB = "panelTabView";
	private Declaracion ultimaDecla;
	protected static Logger logger = Logger.getInstance(BienesRentasBean.class);
	private EditarDireccionDTO editarDireccion;
	private static final String CONST_TEXT_GUARDAR_CAMBIOS = "Guardar Cambios";
	private String valorTotalIngresos = "0";
	private static final String CONST_INDEX = "../index.xhtml";
	private Integer codTipoIdentificacionAnt;
	private String numIdentificacionAnt;
	private PersonaExt personaDeclaracion;
	private String nombreUsuario,nombreTipoDocumento, numeroIdentificacion;
	
	private static AuditoriaSeguridad auditoriaSeguridad;
		
	public static final Integer COD_TIPO_DECLARACION_INGRESO 		= 822;
	public static final Integer COD_TIPO_DECLARACION_RETIRO 		= 743;
	public static final Integer COD_TIPO_DECLARACION_MODIFICACION 	= 742;
	public static final Integer COD_TIPO_DECLARACION_PERIODICA 		= 741;
	public static final Integer COD_TIPO_MODIFICACION_A_INGRESO 	= 1;
	public static final Integer COD_TIPO_MODIFICACION_A_RETIRO		= 2;
	public static final Integer COD_TIPO_MODIFICACION_A_PERIODICA 	= 3;
	
	private boolean lbnuevoregistrogrilla;
	private String strToolTipBtnLimpiar,strToolTipBtnLimpiarNuevo, strToolTipBtnLimpiarEditar;
	
	@Inject
	protected DireccionBean direccionBean;
	
	
	public String getValorTotalIngresos() {
		return valorTotalIngresos;
	}

	public void setValorTotalIngresos(String valorTotalIngresos) {
		this.valorTotalIngresos = valorTotalIngresos;
	}

	/**
	 * @return the blnPasaporte
	 */
	public Boolean getBlnPasaporte() {
		return blnPasaporte;
	}

	/**
	 * @param blnPasaporte the blnPasaporte to set
	 */
	public void setBlnPasaporte(Boolean blnPasaporte) {
		this.blnPasaporte = blnPasaporte;
	}

	public String getBtnGuardarIngresoRenta() {
		return btnGuardarIngresoRenta;
	}

	public void setBtnGuardarIngresoRenta(String btnGuardarIngresoRenta) {
		this.btnGuardarIngresoRenta = btnGuardarIngresoRenta;
	}

	public String getBtnGuardarOtrosIngresos() {
		return btnGuardarOtrosIngresos;
	}

	public void setBtnGuardarOtrosIngresos(String btnGuardarOtrosIngresos) {
		this.btnGuardarOtrosIngresos = btnGuardarOtrosIngresos;
	}

	public String getBtnActividadEconomica() {
		return btnActividadEconomica;
	}

	public void setBtnActividadEconomica(String btnActividadEconomica) {
		this.btnActividadEconomica = btnActividadEconomica;
	}

	public String getBtnAcreencia() {
		return btnAcreencia;
	}

	public void setBtnAcreencia(String btnAcreencia) {
		this.btnAcreencia = btnAcreencia;
	}
	
	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getFechaInicioDeclaracion() {
		return fechaInicioDeclaracion;
	}

	public void setFechaInicioDeclaracion(String fechaInicioDeclaracion) {
		this.fechaInicioDeclaracion = fechaInicioDeclaracion;
	}

	public String getFechaFinDeclaracion() {
		return fechaFinDeclaracion;
	}

	public void setFechaFinDeclaracion(String fechaFinDeclaracion) {
		this.fechaFinDeclaracion = fechaFinDeclaracion;
	}

	public String getNombreTipoDeclaracionAct() {
		return nombreTipoDeclaracionAct;
	}

	public void setNombreTipoDeclaracionAct(String nombreTipoDeclaracionAct) {
		this.nombreTipoDeclaracionAct = nombreTipoDeclaracionAct;
	}

	public DeclaracionRentaBeanPrint getDeclaracionParaImprimir() {
		return declaracionParaImprimir;
	}

	public void setDeclaracionParaImprimir(DeclaracionRentaBeanPrint declaracionParaImprimir) {
		this.declaracionParaImprimir = declaracionParaImprimir;
	}

	public List<DeclaracionRentaBeanPrint> getListaDeclaracionParaImprimir() {
		return listaDeclaracionParaImprimir;
	}

	public void setListaDeclaracionParaImprimir(List<DeclaracionRentaBeanPrint> listaDeclaracionParaImprimir) {
		this.listaDeclaracionParaImprimir = listaDeclaracionParaImprimir;
	}

	public boolean isSinActividadPrivada() {
		return sinActividadPrivada;
	}

	public void setSinActividadPrivada(boolean sinActividadPrivada) {
		this.sinActividadPrivada = sinActividadPrivada;
	}

	public boolean isSinOtrosIngresos() {
		return sinOtrosIngresos;
	}

	public void setSinOtrosIngresos(boolean sinOtrosIngresos) {
		this.sinOtrosIngresos = sinOtrosIngresos;
	}

	public String getCumpleFlujo() {
		return cumpleFlujo;
	}

	public void setCumpleFlujo(String cumpleFlujo) {
		this.cumpleFlujo = cumpleFlujo;
	}

	public String getIdUltimaDecla() {
		return idUltimaDecla;
	}

	public void setIdUltimaDecla(String idUltimaDecla) {
		this.idUltimaDecla = idUltimaDecla;
	}

	public ExternalContext getDeclarationSesion() {
		return declarationSesion;
	}

	public void setDeclarationSesion(ExternalContext declarationSesion) {
		this.declarationSesion = declarationSesion;
	}

	public String getsDeclaration() {
		return sDeclaration;
	}

	public void setsDeclaration(String sDeclaration) {
		this.sDeclaration = sDeclaration;
	}

	public String getTipoDeclaracion() {
		return tipoDeclaracion;
	}

	public void setTipoDeclaracion(String tipoDeclaracion) {
		this.tipoDeclaracion = tipoDeclaracion;
	}

	public BienesPatrimonioExt getBienConfig() {
		return bienConfig;
	}

	public void setBienConfig(BienesPatrimonioExt bienConfig) {
		this.bienConfig = bienConfig;
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	public boolean isHabilitadoTabIngresoLaboral() {
		return habilitadoTabIngresoLaboral;
	}

	public void setHabilitadoTabIngresoLaboral(boolean habilitadoTabIngresoLaboral) {
		this.habilitadoTabIngresoLaboral = habilitadoTabIngresoLaboral;
	}

	public boolean isHabilitadoTabOtrosIngresos() {
		return habilitadoTabOtrosIngresos;
	}

	public void setHabilitadoTabOtrosIngresos(boolean habilitadoTabOtrosIngresos) {
		this.habilitadoTabOtrosIngresos = habilitadoTabOtrosIngresos;
	}

	public boolean isHabilitadoTabActividadEconomica() {
		return habilitadoTabActividadEconomica;
	}

	public void setHabilitadoTabActividadEconomica(boolean habilitadoTabActividadEconomica) {
		this.habilitadoTabActividadEconomica = habilitadoTabActividadEconomica;
	}

	public boolean isHabilitadoTabAcreenciasObligaciones() {
		return habilitadoTabAcreenciasObligaciones;
	}

	public void setHabilitadoTabAcreenciasObligaciones(boolean habilitadoTabAcreenciasObligaciones) {
		this.habilitadoTabAcreenciasObligaciones = habilitadoTabAcreenciasObligaciones;
	}

	public boolean isHabilitadoDatosDomicilio() {
		return habilitadoDatosDomicilio;
	}

	public void setHabilitadoDatosDomicilio(boolean habilitadoDatosDomicilio) {
		this.habilitadoDatosDomicilio = habilitadoDatosDomicilio;
	}

	public boolean isHabilitadoConsanguinidadConjugues() {
		return habilitadoConsanguinidadConjugues;
	}

	public void setHabilitadoConsanguinidadConjugues(boolean habilitadoConsanguinidadConjugues) {
		this.habilitadoConsanguinidadConjugues = habilitadoConsanguinidadConjugues;
	}

	public boolean isHabilitadoCuentas() {
		return habilitadoCuentas;
	}

	public void setHabilitadoCuentas(boolean habilitadoCuentas) {
		this.habilitadoCuentas = habilitadoCuentas;
	}

	public boolean isHabilitadoBienePatrimoniales() {
		return habilitadoBienePatrimoniales;
	}

	public void setHabilitadoBienePatrimoniales(boolean habilitadoBienePatrimoniales) {
		this.habilitadoBienePatrimoniales = habilitadoBienePatrimoniales;
	}

	public boolean isHabilitadoParticipacionJuntas() {
		return habilitadoParticipacionJuntas;
	}

	public void setHabilitadoParticipacionJuntas(boolean habilitadoParticipacionJuntas) {
		this.habilitadoParticipacionJuntas = habilitadoParticipacionJuntas;
	}

	public BienesPatrimonioExt getPatrimonioSel() {
		return patrimonioSel;
	}

	public void setPatrimonioSel(BienesPatrimonioExt patrimonioSel) {
		this.patrimonioSel = patrimonioSel;
	}

	public BienesPatrimonio getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(BienesPatrimonioExt patrimonio) {
		this.patrimonio = patrimonio;
	}

	public BienesPatrimonio getPatrimonioSeleccionado() {
		return patrimonioSeleccionado;
	}

	public void setPatrimonioSeleccionado(BienesPatrimonioExt patrimonioSeleccionado) {
		this.patrimonioSeleccionado = patrimonioSeleccionado;
	}

	public long getCodDeclaracion() {
		return codDeclaracion;
	}

	public void setCodDeclaracion(long codDeclaracion) {
		this.codDeclaracion = codDeclaracion;
	}

	public List<BienesPatrimonioExt> getBienes() {
		return bienes;
	}

	public void setBienes(List<BienesPatrimonioExt> bienes) {
		this.bienes = bienes;
	}

	public boolean isFormBienesHabilitado() {
		return formBienesHabilitado;
	}

	public void setFormBienesHabilitado(boolean formBienesHabilitado) {
		this.formBienesHabilitado = formBienesHabilitado;
	}

	public void confirmarEliminadoBien(BienesPatrimonioExt bienPatrimonio) {
		this.patrimonioSeleccionado = bienPatrimonio;
	}

	/**
	 * @return the panelBienesHabilitado
	 */
	public boolean isPanelBienesHabilitado() {
		return panelBienesHabilitado;
	}

	/**
	 * @param panelBienesHabilitado
	 *            the panelBienesHabilitado to set
	 */
	public void setPanelBienesHabilitado(boolean panelBienesHabilitado) {
		this.panelBienesHabilitado = panelBienesHabilitado;
	}

	public boolean isEstadoTabParientes() {
		return estadoTabParientes;
	}

	public void setEstadoTabParientes(boolean estadoTabParientes) {
		this.estadoTabParientes = estadoTabParientes;
	}

	public boolean isEstadoPanelParientes() {

		return estadoPanelParientes;
	}

	public void setEstadoPanelParientes(boolean estadoPanelParientes) {
		this.estadoPanelParientes = estadoPanelParientes;
	}

	public boolean isSinParientes() {
		return sinParientes;
	}

	public void setSinParientes(boolean sinParientes) {
		this.sinParientes = sinParientes;
	}

	public PersonaParentesco getParienteSeleccionado() {
		return parienteSeleccionado;
	}

	public void setParienteSeleccionado(PersonaParentescoExt parienteSeleccionado) {
		this.parienteSeleccionado = parienteSeleccionado;
	}

	public List<PersonaParentescoExt> getParientes() {
		return parientes;
	}

	public void setParientes(List<PersonaParentescoExt> parientes) {
		this.parientes = parientes;
	}

	public Boolean getHabilitadoFormularioIngresoRenta() {
		return habilitadoFormularioIngresoRenta;
	}

	public void setHabilitadoFormularioIngresoRenta(Boolean habilitadoFormularioIngresoRenta) {
		this.habilitadoFormularioIngresoRenta = habilitadoFormularioIngresoRenta;
	}

	public Boolean getHabilitadoMensajeConfirmacion() {
		return habilitadoMensajeConfirmacion;
	}

	public void setHabilitadoMensajeConfirmacion(Boolean habilitadoMensajeConfirmacion) {
		this.habilitadoMensajeConfirmacion = habilitadoMensajeConfirmacion;
	}

	public Declaracion getDeclaracionRenta() {
		return declaracionRenta;
	}

	public void setDeclaracionRenta(Declaracion declaracionRenta) {
		this.declaracionRenta = declaracionRenta;
	}

	public List<Declaracion> getListaDeclaracion() {
		return listaDeclaracion;
	}

	public void setListaDeclaracion(List<Declaracion> listaDeclaracion) {
		this.listaDeclaracion = listaDeclaracion;
	}

	public IngresosDeclaracionExt getIngresosDeclaracion() {
		return ingresosDeclaracion;
	}

	public void setIngresosDeclaracion(IngresosDeclaracionExt ingresosDeclaracion) {
		this.ingresosDeclaracion = ingresosDeclaracion;
	}

	public List<IngresosDeclaracionExt> getListaIngresosDeclaracion() {
		return listaIngresosDeclaracion;
	}

	public void setListaIngresosDeclaracion(List<IngresosDeclaracionExt> listaIngresosDeclaracion) {
		this.listaIngresosDeclaracion = listaIngresosDeclaracion;
	}

	public DatoContactoExt getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DatoContactoExt domicilio) {
		this.domicilio = domicilio;
	}

	public UsuarioDTO getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(UsuarioDTO usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

	public String getMensajeParametrico() {
		return mensajeParametrico;
	}

	public void setMensajeParametrico(String mensajeParametrico) {
		this.mensajeParametrico = mensajeParametrico;
	}

	public BigDecimal getTotalOtrosIngresos() {
		return totalOtrosIngresos;
	}

	public void setTotalOtrosIngresos(BigDecimal totalOtrosIngresos) {
		this.totalOtrosIngresos = totalOtrosIngresos;
	}

	public OtrosIngresosDeclaracion getOtrosIngresos() {
		return otrosIngresos;
	}

	public void setOtrosIngresos(OtrosIngresosDeclaracionExt otrosIngresos) {
		this.otrosIngresos = otrosIngresos;
	}

	public List<OtrosIngresosDeclaracionExt> getListaOtrosIngresos() {
		return listaOtrosIngresos;
	}

	public void setListaOtrosIngresos(List<OtrosIngresosDeclaracionExt> listaOtrosIngresos) {
		this.listaOtrosIngresos = listaOtrosIngresos;
	}

	public ActividadPrivadaExt getActividadPrivada() {
		return actividadPrivada;
	}

	public void setActividadPrivada(ActividadPrivadaExt actividadPrivada) {
		this.actividadPrivada = actividadPrivada;
	}

	public List<ActividadPrivadaExt> getListaActividadPrivada() {
		return listaActividadPrivada;
	}

	public void setListaActividadPrivada(List<ActividadPrivadaExt> listaActividadPrivada) {
		this.listaActividadPrivada = listaActividadPrivada;
	}

	public AcreenciaObligacion getAcreenciaOblicacion() {
		return acreenciaOblicacion;
	}

	public void setAcreenciaOblicacion(AcreenciaObligacion acreenciaOblicacion) {
		this.acreenciaOblicacion = acreenciaOblicacion;
	}

	public List<AcreenciaObligacion> getListaAcreObligaciones() {
		return listaAcreObligaciones;
	}

	public void setListaAcreObligaciones(List<AcreenciaObligacion> listaAcreObligaciones) {
		this.listaAcreObligaciones = listaAcreObligaciones;
	}

	public boolean isRenderBienes() {
		return renderBienes;
	}

	public void setRenderBienes(boolean renderBienes) {
		this.renderBienes = renderBienes;
	}

	public List<CuentasDeclaracion> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<CuentasDeclaracion> cuentas) {
		this.cuentas = cuentas;
	}

	public CuentasDeclaracion getCuentaSeleccionada() {
		return cuentaSeleccionada;
	}

	public void setCuentaSeleccionada(CuentasDeclaracion cuentaSeleccionada) {
		this.cuentaSeleccionada = cuentaSeleccionada;
	}

	public CuentasDeclaracion getCuentasConfig() {
		return cuentasConfig;
	}

	public void setCuentasConfig(CuentasDeclaracion cuentasConfig) {
		this.cuentasConfig = cuentasConfig;
	}

	public boolean isFormParticipacionHabilitado() {
		return formParticipacionHabilitado;
	}

	public void setFormParticipacionHabilitado(boolean formParticipacionHabilitado) {
		this.formParticipacionHabilitado = formParticipacionHabilitado;
	}

	public ParticipacionJuntaExt getParticipacionSeleccionada() {
		return participacionSeleccionada;
	}

	public void setParticipacionSeleccionada(ParticipacionJuntaExt participacionSeleccionada) {
		this.participacionSeleccionada = participacionSeleccionada;
	}

	public List<ParticipacionJuntaExt> getParticipaciones() {
		return participaciones;
	}

	public void setParticipaciones(List<ParticipacionJuntaExt> participaciones) {
		this.participaciones = participaciones;
	}

	public boolean isGuardadoDefinitivoHabilitado() {
		return guardadoDefinitivoHabilitado;
	}

	public void setGuardadoDefinitivoHabilitado(boolean guardadoDefinitivoHabilitado) {
		this.guardadoDefinitivoHabilitado = guardadoDefinitivoHabilitado;
	}

	public boolean isEstadoCalidadMiembro() {
		return estadoCalidadMiembro;
	}

	public void setEstadoCalidadMiembro(boolean estadoCalidadMiembro) {
		this.estadoCalidadMiembro = estadoCalidadMiembro;
	}

	public boolean isEstadoCalidadSocio() {
		return estadoCalidadSocio;
	}

	public void setEstadoCalidadSocio(boolean estadoCalidadSocio) {
		this.estadoCalidadSocio = estadoCalidadSocio;
	}

	public boolean isSinBienes() {
		return sinBienes;
	}

	public void setSinBienes(boolean sinBienes) {
		this.sinBienes = sinBienes;
	}

	public boolean isSinParticipacion() {
		return sinParticipacion;
	}

	public void setSinParticipacion(boolean sinParticipacion) {
		this.sinParticipacion = sinParticipacion;
	}

	public boolean isSinCuentas() {
		return sinCuentas;
	}

	public void setSinCuentas(boolean sinCuentas) {
		this.sinCuentas = sinCuentas;
	}

	public boolean isEstadoControlesTablaBienes() {
		return this.estadoControlesTablaBienes;
	}

	public void setEstadoControlesTablaBienes(boolean estadoControlesTablaBienes) {
		this.estadoControlesTablaBienes = estadoControlesTablaBienes;
	}

	public boolean isEstadoControlesTablaAcreencias() {
		return this.estadoControlesTablaAcreencias;
	}

	public void setEstadoControlesTablaAcreencias(boolean estadoControlesTablaAcreencias) {
		this.estadoControlesTablaAcreencias = estadoControlesTablaAcreencias;
	}

	public boolean isFormAcreenciasHabilitado() {
		return formAcreenciasHabilitado;
	}

	public void setFormAcreenciasHabilitado(boolean formAcreenciasHabilitado) {
		this.formAcreenciasHabilitado = formAcreenciasHabilitado;
	}

	public boolean isFormActividadHabilitado() {
		return formActividadHabilitado;
	}

	public void setFormActividadHabilitado(boolean formActividadHabilitado) {
		this.formActividadHabilitado = formActividadHabilitado;
	}

	public boolean isEstadoControlesTablaActividad() {
		return estadoControlesTablaActividad;
	}

	public void setEstadoControlesTablaActividad(boolean estadoControlesTablaActividad) {
		this.estadoControlesTablaActividad = estadoControlesTablaActividad;
	}

	public boolean isEstadoControlesTablaParticipacion() {
		return estadoControlesTablaParticipacion;
	}

	public void setEstadoControlesTablaParticipacion(boolean estadoControlesTablaParticipacion) {
		this.estadoControlesTablaParticipacion = estadoControlesTablaParticipacion;
	}

	public boolean isEstadoRadioPariente() {
		return estadoRadioPariente;
	}

	public void setEstadoRadioPariente(boolean estadoRadioPariente) {
		this.estadoRadioPariente = estadoRadioPariente;
	}

	public boolean isSinIngresosRentas() {
		return sinIngresosRentas;
	}

	public void setSinIngresosRentas(boolean sinIngresosRentas) {
		this.sinIngresosRentas = sinIngresosRentas;
	}

	public boolean isBtnGuardadoDefinitivoDeshabilitado() {
		return btnGuardadoDefinitivoDeshabilitado;
	}

	public void setBtnGuardadoDefinitivoDeshabilitado(boolean btnGuardadoDefinitivoDeshabilitado) {
		this.btnGuardadoDefinitivoDeshabilitado = btnGuardadoDefinitivoDeshabilitado;
	}

	public boolean isBtnSaveAcreenciaDeshabilitado() {
		return btnSaveAcreenciaDeshabilitado;
	}

	public void setBtnSaveAcreenciaDeshabilitado(boolean btnSaveAcreenciaDeshabilitado) {
		this.btnSaveAcreenciaDeshabilitado = btnSaveAcreenciaDeshabilitado;
	}

	public boolean isBtnGuardarActividadDeshabilitado() {
		return btnGuardarActividadDeshabilitado;
	}

	public void setBtnGuardarActividadDeshabilitado(boolean btnGuardarActividadDeshabilitado) {
		this.btnGuardarActividadDeshabilitado = btnGuardarActividadDeshabilitado;
	}

	public boolean isBtnGuardarPatrimonioDeshabilitado() {
		return btnGuardarPatrimonioDeshabilitado;
	}

	public void setBtnGuardarPatrimonioDeshabilitado(boolean btnGuardarPatrimonioDeshabilitado) {
		this.btnGuardarPatrimonioDeshabilitado = btnGuardarPatrimonioDeshabilitado;
	}

	public boolean isBtnGuardarCuentasDeshabilitado() {
		return btnGuardarCuentasDeshabilitado;
	}

	public void setBtnGuardarCuentasDeshabilitado(boolean btnGuardarCuentasDeshabilitado) {
		this.btnGuardarCuentasDeshabilitado = btnGuardarCuentasDeshabilitado;
	}

	public boolean isBtnGuardarDomicilioDeshabilitado() {
		return btnGuardarDomicilioDeshabilitado;
	}

	public void setBtnGuardarDomicilioDeshabilitado(boolean btnGuardarDomicilioDeshabilitado) {
		this.btnGuardarDomicilioDeshabilitado = btnGuardarDomicilioDeshabilitado;
	}

	public boolean isBtnGuardarIngresoRentaDeshabilitado() {
		return btnGuardarIngresoRentaDeshabilitado;
	}

	public void setBtnGuardarIngresoRentaDeshabilitado(boolean btnGuardarIngresoRentaDeshabilitado) {
		this.btnGuardarIngresoRentaDeshabilitado = btnGuardarIngresoRentaDeshabilitado;
	}

	public boolean isBtnGuardarOtroIngresoDeshabilitado() {
		return btnGuardarOtroIngresoDeshabilitado;
	}

	public void setBtnGuardarOtroIngresoDeshabilitado(boolean btnGuardarOtroIngresoDeshabilitado) {
		this.btnGuardarOtroIngresoDeshabilitado = btnGuardarOtroIngresoDeshabilitado;
	}

	public boolean isBtnGuardarOtroIngreso2Deshabilitado() {
		return btnGuardarOtroIngreso2Deshabilitado;
	}

	public void setBtnGuardarOtroIngreso2Deshabilitado(boolean btnGuardarOtroIngreso2Deshabilitado) {
		this.btnGuardarOtroIngreso2Deshabilitado = btnGuardarOtroIngreso2Deshabilitado;
	}

	public boolean isBtnGuardarParientesDeshabilitado() {
		return btnGuardarParientesDeshabilitado;
	}

	public void setBtnGuardarParientesDeshabilitado(boolean btnGuardarParientesDeshabilitado) {
		this.btnGuardarParientesDeshabilitado = btnGuardarParientesDeshabilitado;
	}

	public String getBtnGuardadoDefinitivoMensaje() {
		return btnGuardadoDefinitivoMensaje;
	}

	public void setBtnGuardadoDefinitivoMensaje(String btnGuardadoDefinitivoMensaje) {
		this.btnGuardadoDefinitivoMensaje = btnGuardadoDefinitivoMensaje;
	}

	public String getBtnSaveAcreenciaMensaje() {
		return btnSaveAcreenciaMensaje;
	}

	public void setBtnSaveAcreenciaMensaje(String btnSaveAcreenciaMensaje) {
		this.btnSaveAcreenciaMensaje = btnSaveAcreenciaMensaje;
	}

	public String getBtnGuardarActividadMensaje() {
		return btnGuardarActividadMensaje;
	}

	public void setBtnGuardarActividadMensaje(String btnGuardarActividadMensaje) {
		this.btnGuardarActividadMensaje = btnGuardarActividadMensaje;
	}

	public String getBtnGuardarPatrimonioMensaje() {
		return btnGuardarPatrimonioMensaje;
	}

	public void setBtnGuardarPatrimonioMensaje(String btnGuardarPatrimonioMensaje) {
		this.btnGuardarPatrimonioMensaje = btnGuardarPatrimonioMensaje;
	}

	public String getBtnGuardarCuentasMensaje() {
		return btnGuardarCuentasMensaje;
	}

	public void setBtnGuardarCuentasMensaje(String btnGuardarCuentasMensaje) {
		this.btnGuardarCuentasMensaje = btnGuardarCuentasMensaje;
	}

	public String getBtnGuardarDomicilioMensaje() {
		return btnGuardarDomicilioMensaje;
	}

	public void setBtnGuardarDomicilioMensaje(String btnGuardarDomicilioMensaje) {
		this.btnGuardarDomicilioMensaje = btnGuardarDomicilioMensaje;
	}

	public String getBtnGuardarIngresoRentaMensaje() {
		return btnGuardarIngresoRentaMensaje;
	}

	public void setBtnGuardarIngresoRentaMensaje(String btnGuardarIngresoRentaMensaje) {
		this.btnGuardarIngresoRentaMensaje = btnGuardarIngresoRentaMensaje;
	}

	public String getBtnGuardarOtroIngresoMensaje() {
		return btnGuardarOtroIngresoMensaje;
	}

	public void setBtnGuardarOtroIngresoMensaje(String btnGuardarOtroIngresoMensaje) {
		this.btnGuardarOtroIngresoMensaje = btnGuardarOtroIngresoMensaje;
	}

	public String getBtnGuardarOtroIngreso2Mensaje() {
		return btnGuardarOtroIngreso2Mensaje;
	}

	public void setBtnGuardarOtroIngreso2Mensaje(String btnGuardarOtroIngreso2Mensaje) {
		this.btnGuardarOtroIngreso2Mensaje = btnGuardarOtroIngreso2Mensaje;
	}

	public String getBtnGuardarParientesMensaje() {
		return btnGuardarParientesMensaje;
	}

	public void setBtnGuardarParientesMensaje(String btnGuardarParientesMensaje) {
		this.btnGuardarParientesMensaje = btnGuardarParientesMensaje;
	}

	public boolean isBtnGuardarParticipacionJuntasDeshabilitado() {
		return btnGuardarParticipacionJuntasDeshabilitado;
	}

	public void setBtnGuardarParticipacionJuntasDeshabilitado(boolean btnGuardarParticipacionJuntasDeshabilitado) {
		this.btnGuardarParticipacionJuntasDeshabilitado = btnGuardarParticipacionJuntasDeshabilitado;
	}

	public String getBtnGuardarParticipacionJuntasMensaje() {
		return btnGuardarParticipacionJuntasMensaje;
	}

	public void setBtnGuardarParticipacionJuntasMensaje(String btnGuardarParticipacionJuntasMensaje) {
		this.btnGuardarParticipacionJuntasMensaje = btnGuardarParticipacionJuntasMensaje;
	}
	
	public boolean isEstadoBtnOtroIn() {
		return estadoBtnOtroIn;
	}

	public void setEstadoBtnOtroIn(boolean estadoBtnOtroIn) {
		this.estadoBtnOtroIn = estadoBtnOtroIn;
	}

	/**
	 * @return the mensage
	 */
	public String getMensage() {
		return mensage;
	}

	/**
	 * @param mensage
	 *            the mensage to set
	 */
	public void setMensage(String mensage) {
		this.mensage = mensage;
	}

	/**
	 * @return the pageCancel
	 */
	public String getPageCancel() {
		return pageCancel;
	}

	/**
	 * @param pageCancel
	 *            the pageCancel to set
	 */
	public void setPageCancel(String pageCancel) {
		this.pageCancel = pageCancel;
	}

	/**
	 * @return the codTipoIdentificacionAnt
	 */
	public Integer getCodTipoIdentificacionAnt() {
		return codTipoIdentificacionAnt;
	}

	/**
	 * @param codTipoIdentificacionAnt the codTipoIdentificacionAnt to set
	 */
	public void setCodTipoIdentificacionAnt(Integer codTipoIdentificacionAnt) {
		this.codTipoIdentificacionAnt = codTipoIdentificacionAnt;
	}
	
	/**
	 * @return the numIdentificacionAnt
	 */
	public String getNumIdentificacionAnt() {
		return numIdentificacionAnt;
	}

	/**
	 * @param numIdentificacionAnt the numIdentificacionAnt to set
	 */
	public void setNumIdentificacionAnt(String numIdentificacionAnt) {
		this.numIdentificacionAnt = numIdentificacionAnt;
	}

	/**
	 * Método para setear el tab activo.
	 */
	public void onChangeTabVisible(TabChangeEvent event) {
		TabView tv = (TabView) event.getComponent();
		this.indexTab = tv.getChildren().indexOf(event.getTab());
		this.cargarDatos();
		lbMostrarFormulario=false;
	}
	
	/**
	 * Constructor de la clase
	 * Este método es el encargado de verificar permisos de acceso y verificar si el usuario ingreso a la página
	 * a tráves de una opción valida desde el menú o el index.
	 * @return void
	 * @param none
	 */
	public BienesRentasBean() {
		auditoriaSeguridad = new AuditoriaSeguridad();
		auditoriaSeguridad.setCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		auditoriaSeguridad.setCodUsuarioRol(new BigDecimal(getUsuarioSesion().getCodRol()));
		ComunicacionServiciosBR.setAuditoriaSeguridad(auditoriaSeguridad);
		this.setBtnGuardadoDefinitivoDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardadoDefinitivo"));
		this.setBtnGuardadoDefinitivoMensaje(isBtnGuardadoDefinitivoDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnGuardarDomicilioDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardarDomicilio"));
		this.setBtnGuardarDomicilioMensaje(isBtnGuardarDomicilioDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnGuardarParientesDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardarParientes"));
		this.setBtnGuardarParientesMensaje(isBtnGuardarParientesDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnGuardarIngresoRentaDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardarIngresoRenta"));
		this.setBtnGuardarIngresoRentaMensaje(isBtnGuardarIngresoRentaDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnGuardarOtroIngresoDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardarOtroIngreso"));
		this.setBtnGuardarOtroIngresoMensaje(isBtnGuardarOtroIngresoDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnGuardarCuentasDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardarCuentas"));
		this.setBtnGuardarCuentasMensaje(isBtnGuardarCuentasDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnGuardarPatrimonioDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardarPatrimonio"));
		this.setBtnGuardarPatrimonioMensaje(isBtnGuardarPatrimonioDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnGuardarActividadDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardarActividad"));
		this.setBtnGuardarActividadMensaje(isBtnGuardarActividadDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnSaveAcreenciaDeshabilitado(this.validarFuncionalidadDeshabilitada("btnSaveAcreencia"));
		this.setBtnSaveAcreenciaMensaje(isBtnSaveAcreenciaDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.setBtnGuardarParticipacionJuntasDeshabilitado(this.validarFuncionalidadDeshabilitada("btnGuardarParticipacionJuntas"));
		this.setBtnGuardarParticipacionJuntasMensaje(isBtnGuardarParticipacionJuntasDeshabilitado() ? SEGURIDAD_ROLES_MSG : "");
		this.declarationSesion = FacesContext.getCurrentInstance().getExternalContext();
		try {
			String data = (String) this.declarationSesion.getSessionMap().get("modificacion");
			String nuevaDec = (String) this.declarationSesion.getSessionMap().get("nuevaDec");
			if(data.equals("true")) 
				this.modificacion = true;
			if(nuevaDec.equals("true")) 
				this.nuevaDec = true;
			this.declarationSesion.getSessionMap().put("nuevaDec","false");
		}catch (NullPointerException e) {
			this.modificacion = false;
		}
		this.guardadoDefinitivoHabilitado = true;
		this.usuarioSesion = (UsuarioDTO) this.declarationSesion.getSessionMap().get("usuarioSesion");
		try {
			this.mensage = (String) this.declarationSesion.getSessionMap().get("msg");
		} catch (Exception e) {
			this.mensage = "";
		}
		this.cumpleFlujo = (String) declarationSesion.getSessionMap().get("cumpleFlujo");
		if (this.cumpleFlujo == null || this.cumpleFlujo.equals("")) {
			try {
				this.declarationSesion.redirect("../persona/informacionPersonal.xhtml");
			} catch (Exception ex) {
				BienesRentasBean.logger.log().error("Error en constructor public BienesRentasBean(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
			}
		} 
		else {
			this.sDeclaration = (String) this.declarationSesion.getSessionMap().get("codDeclaracion");
			this.tipoDeclaracion = (String) this.declarationSesion.getSessionMap().get("tipoDeclaracion");
			if(this.modificacion) {
				this.tipoDeclaracion = "742";
			}
			this.paraCarga = (String) this.declarationSesion.getSessionMap().get("paracarga");
			this.idUltimaDecla = (String) this.declarationSesion.getSessionMap().get("codDeclaracionCarga");
			this.idPersona = "" + getUsuarioSesion().getCodPersona();
			this.inicializarDeclaracion();
		}
		if(this.modificacion) 
			this.establecerEstadoTabs();
		if(this.nuevaDec) {
			this.establecerEstadoTabs();
			this.nuevaDec = false;
		}
		this.establecerEstadoTabs();
		strToolTipBtnLimpiarNuevo = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_TOOLTIP_BTN_LIMPIAR_NUEVO, getLocale());
		strToolTipBtnLimpiarEditar = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_TOOLTIP_BTN_LIMPIAR_EDICION, getLocale());
	}
	
	/**
	 *Este método es el encargado de recuperar el id de la declaración en proceso, inicialzar cada los datos de cada tab y establecer si existen datos de una declaración anterior para cargar en la que se
	 *esta diligenciando.
	 *
	 * @return void
	 * @param none
	 */
	public void inicializarDeclaracion() {
		this.declarationSesion.getSessionMap().remove("cumpleFlujo");
		this.habilitadoDatosDomicilio 				= true;
		this.habilitadoConsanguinidadConjugues 		= true;
		this.habilitadoTabIngresoLaboral 			= true;
		this.habilitadoTabOtrosIngresos 			= true;
		this.habilitadoCuentas 						= true;
		this.habilitadoBienePatrimoniales 			= true;
		this.habilitadoTabActividadEconomica 		= true;
		this.habilitadoTabAcreenciasObligaciones 	= true;
		this.habilitadoParticipacionJuntas 			= true;
		if (paraCarga.equals("true") && idUltimaDecla!=null &&  sDeclaration!=null) {
			this.ultimaDecla = ComunicacionServiciosBR.getdeclaracionid(Long.parseLong(idUltimaDecla));
			this.declaracionRenta = ComunicacionServiciosBR.getdeclaracionid(Long.parseLong(sDeclaration));
			if(this.modificacion) {
				this.declaracionRenta.setCodTipoDeclaracion(742);
				if(this.declaracionRenta !=null && this.declaracionRenta.getCodTipoDeclaracion().equals(COD_TIPO_DECLARACION_MODIFICACION)){
					this.declaracionRenta.setFlgExtemporanea((short) 0);
				}
				this.declaracionRenta.setCodDeclaracionAnt(new BigDecimal(idUltimaDecla));
				Declaracion ultimaDec = ComunicacionServiciosBR.getdeclaracionid(Long.parseLong(idUltimaDecla));
				if(ultimaDec!=null && ultimaDec.getCodTipoDeclaracion()!=null   ) {
					if(ultimaDec.getCodTipoDeclaracion().equals(COD_TIPO_DECLARACION_INGRESO))
						this.declaracionRenta.setTipoModificacion(new BigDecimal(COD_TIPO_MODIFICACION_A_INGRESO));
					else if(ultimaDec.getCodTipoDeclaracion().equals(COD_TIPO_DECLARACION_RETIRO))
						this.declaracionRenta.setTipoModificacion(new BigDecimal(COD_TIPO_MODIFICACION_A_RETIRO));
					else if(ultimaDec.getCodTipoDeclaracion().equals(COD_TIPO_DECLARACION_PERIODICA))
						this.declaracionRenta.setTipoModificacion(new BigDecimal(COD_TIPO_MODIFICACION_A_PERIODICA));					
				}
				ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			}
			this.migrarIngresosDeclaracion();
			this.migrarOtrosIngresos();
			this.migrarActividadPrivada();
			this.migrarAcreencias();
			this.inicializarRecursosTexto();
			this.indexTabPos = Integer.parseInt("" + declaracionRenta.getTabIndex());
			this.indexTab = this.indexTabPos % 10;
		} 
		else {
			if(sDeclaration!=null) {
				this.declaracionRenta = ComunicacionServiciosBR.getdeclaracionid(Long.parseLong(sDeclaration));
			}
			if (this.declaracionRenta.getCodDeclaracion() != null) {
				this.mostrarIngresosLaborales();
				this.mostrarOtrosIngresos();
				this.mostrarActividadesPrivadas();
				this.mostrarAcreenciasObligaciones();
			} 
			else
				if(usuarioSesion!=null && usuarioSesion.getCodPersona()!=null) {
					this.declaracionRenta = ComunicacionServiciosBR.getdeclaracionUltima(usuarioSesion.getCodPersona());
				}
				
			if(this.declaracionRenta.getTabIndex() == null)
				this.indexTabPos = 0;
			else
				this.indexTabPos = Integer.parseInt("" + declaracionRenta.getTabIndex());
			this.indexTab = this.indexTabPos % 10;
			RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO);
			this.activarCheckOtrosIngresos();
			this.activarCheckActividadPrivada();
			this.activarCheckIngresos();
		}
		this.inicializaPersona();
		this.inicializarDatosDomicilio();
		this.setPanelBienesHabilitado(false);
		this.inicializarRecursosTexto();
		this.inicializarBienesPatrimoniales();
		this.inicializarParientes();
		this.inicializarCuentas();
		this.inicializarParticipacion();
		declaracionRenta.setFechaPresentacion(DateUtils.getFechaSistema());
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		this.nombreTipoDeclaracionAct = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(declaracionRenta.getCodTipoDeclaracion().intValue())).getNombreParametro();
		if (nombreTipoDeclaracionAct != null || nombreTipoDeclaracionAct != "")
			nombreTipoDeclaracionAct = nombreTipoDeclaracionAct.substring(0, 1).toUpperCase() + nombreTipoDeclaracionAct.substring(1).toLowerCase();
		fechaInicioDeclaracion = formateador.format(declaracionRenta.getFechaInicio());
		fechaFinDeclaracion = formateador.format(declaracionRenta.getFechaFin());
		RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO);
	}
	
	public void establecerEstadoTabs() {
		if(this.indexTabPos / 10 >= 0)
			this.habilitadoDatosDomicilio = false;
		if(this.indexTabPos / 10 >= 1)
			this.habilitadoConsanguinidadConjugues = false;
		if(this.indexTabPos / 10 >= 2)
			this.habilitadoTabIngresoLaboral = false;
		if(this.indexTabPos / 10 >= 3)
			this.habilitadoTabOtrosIngresos = false;
		if(this.indexTabPos / 10 >= 4)
			this.habilitadoCuentas = false;
		if(this.indexTabPos / 10 >= 5)
			this.habilitadoBienePatrimoniales = false;
		if(this.indexTabPos / 10 >= 6)
			this.habilitadoTabActividadEconomica = false;
		if(this.indexTabPos / 10 >= 7)
			this.habilitadoTabAcreenciasObligaciones = false;
		if(this.indexTabPos / 10 >= 8)
			this.habilitadoParticipacionJuntas = false;
	}
	
	/**
	 * Este método verifica si existen datos de ingresos y rentas laborales de periodos anteriores para cargar en la declaración en proceso,
	 * En caso de ser así los relaciona en la declaración actual
	 *
	 * @return void
	 * @param none
	 */
	public void migrarIngresosDeclaracion() {
		if(declaracionRenta!=null && declaracionRenta.getCodDeclaracion()!=null) {
			this.ingresosDeclaracion = new IngresosDeclaracionExt();
			this.ingresosDeclaracion.setFlgActivo(1);
			this.ingresosDeclaracion.setCodDeclaracion(declaracionRenta.getCodDeclaracion().longValue());
			List<IngresosDeclaracionExt> listaParaMigrar = ComunicacionServiciosBR.getingresosdeclaracion(ingresosDeclaracion);
			if (listaParaMigrar.isEmpty()) {
				this.ingresosDeclaracion.setFlgActivo(1);
				this.ingresosDeclaracion.setCodDeclaracion(Long.parseLong(idUltimaDecla));
				listaParaMigrar = ComunicacionServiciosBR.getingresosdeclaracion(ingresosDeclaracion);
				if (!listaParaMigrar.isEmpty()) {
					for (IngresosDeclaracionExt ingresosDeclaracionM : listaParaMigrar) {
						ingresosDeclaracionM.setCodIngresoDeclaracion(null);
						ingresosDeclaracionM.setCodDeclaracion(declaracionRenta.getCodDeclaracion().longValue());
						ComunicacionServiciosBR.setingresosdeclaracion(ingresosDeclaracionM);
					}
				}
				else {
					this.sinIngresosRentas = this.ultimaDecla.getFlgTengoIngresosLaborales() != null && this.ultimaDecla.getFlgTengoIngresosLaborales() == 1;
					this.declaracionRenta.setFlgTengoIngresosLaborales(this.ultimaDecla.getFlgTengoIngresosLaborales() != null ? this.ultimaDecla.getFlgTengoIngresosLaborales() : 0);
				}
			}
			else 
				this.sinIngresosRentas = this.declaracionRenta.getFlgTengoIngresosLaborales() == 1;
			this.habilitadoTabIngresoLaboral = true;
			this.ingresosDeclaracion = new IngresosDeclaracionExt();
			this.ingresosDeclaracion.setCodDeclaracion(declaracionRenta.getCodDeclaracion().longValue());
			this.ingresosDeclaracion.setFlgActivo(1);
			this.listaIngresosDeclaracion = ComunicacionServiciosBR.getingresosdeclaracion(ingresosDeclaracion);
		}
	}

	/**
	 * Este método verifica si existen datos de ingresos no laborales de periodos anteriores para cargar en la declaración en proceso,
	 * En caso de ser así los relaciona en la declaración actual
	 *
	 * @return void
	 * @param none
	 */
	public void migrarOtrosIngresos() {
		if(declaracionRenta!=null && declaracionRenta.getCodDeclaracion()!=null) {
			this.listaOtrosIngresos = ComunicacionServiciosBR.getotrosingresosdeclaracion(this.declaracionRenta.getCodDeclaracion().longValue(), 1);
			List<OtrosIngresosDeclaracionExt> listaParaMigrar;
			if (listaOtrosIngresos.isEmpty()) {
				listaParaMigrar = ComunicacionServiciosBR.getotrosingresosdeclaracion(Long.parseLong(this.idUltimaDecla), 1);
				if (!listaParaMigrar.isEmpty()) {
					for (OtrosIngresosDeclaracion otrosIngresosDeclaracionM : listaParaMigrar) {
						otrosIngresosDeclaracionM.setCodOtrosIngresosDeclaracion(null);
						otrosIngresosDeclaracionM.setCodDeclaracion(this.declaracionRenta.getCodDeclaracion());
						ComunicacionServiciosBR.setOtrosIngresosDeclaracion(otrosIngresosDeclaracionM);
					}
				}
				else {
					this.sinOtrosIngresos = this.ultimaDecla.getFlgTengoOtrosIngresos() != null && this.ultimaDecla.getFlgTengoOtrosIngresos() == 1;
					this.declaracionRenta.setFlgTengoOtrosIngresos(this.ultimaDecla.getFlgTengoOtrosIngresos() != null ? this.ultimaDecla.getFlgTengoOtrosIngresos() : 0);
				}
			}
			else
				this.sinOtrosIngresos = this.declaracionRenta.getFlgTengoOtrosIngresos() == 1;
			this.estadoBtnOtroIn = this.sinOtrosIngresos;
			this.listaOtrosIngresos = ComunicacionServiciosBR.getotrosingresosdeclaracion(this.declaracionRenta.getCodDeclaracion().longValue(), 1);	
		}
	}
	
	/**
	 * Este método verifica si existen registros de actividad privada de periodos anteriores para cargar en la declaración en proceso,
	 * En caso de ser así los relaciona en la declaración actual
	 *
	 * @return void
	 * @param none
	 */
	public void migrarActividadPrivada() {
		if(declaracionRenta!=null && declaracionRenta.getCodDeclaracion()!=null && idUltimaDecla!=null) {
			listaActividadPrivada = ComunicacionServiciosBR.getactividadprivada(declaracionRenta.getCodDeclaracion().longValue(), 1);
			List<ActividadPrivadaExt> listaParaMigrar;
			if (listaActividadPrivada.isEmpty()) {
				listaParaMigrar = ComunicacionServiciosBR.getactividadprivada(Long.parseLong(idUltimaDecla), 1);
				if (!listaParaMigrar.isEmpty()) {
					for (ActividadPrivadaExt actividadPrivadaM : listaParaMigrar) {
						actividadPrivadaM.setCodActividadPrivada(null);
						actividadPrivadaM.setCodDeclaracion(declaracionRenta.getCodDeclaracion());
						ComunicacionServiciosBR.setactividadprivada(actividadPrivadaM);
					}
				}
				else {
					this.sinActividadPrivada = this.ultimaDecla.getFlgActividadEconomicaPrivada() != null && this.ultimaDecla.getFlgActividadEconomicaPrivada() == 1;
					this.declaracionRenta.setFlgActividadEconomicaPrivada(this.ultimaDecla.getFlgActividadEconomicaPrivada() != null ? this.ultimaDecla.getFlgActividadEconomicaPrivada() : 0 );
				}
			}
			else
				this.sinActividadPrivada = this.declaracionRenta.getFlgActividadEconomicaPrivada() == 1;
			this.listaActividadPrivada = ComunicacionServiciosBR.getactividadprivada(declaracionRenta.getCodDeclaracion().longValue(), 1);	
		}
	}

	/**
	 * Este método verifica si existen registros de acreencias y obligaciones de periodos anteriores para cargar en la declaración en proceso,
	 * En caso de ser así los relaciona en la declaración actual
	 *
	 * @return void
	 * @param none
	 */
	public void migrarAcreencias() {
		if(declaracionRenta!=null && declaracionRenta.getCodDeclaracion()!=null && idUltimaDecla!=null) {
			this.listaAcreObligaciones = ComunicacionServiciosBR.getacreenciaobligacion(declaracionRenta.getCodDeclaracion().longValue(), 1);
			List<AcreenciaObligacion> listaParaMigrar;
			if (listaAcreObligaciones.isEmpty()) {
				listaParaMigrar = ComunicacionServiciosBR.getacreenciaobligacion(Long.parseLong(idUltimaDecla), 1);
				if (!listaParaMigrar.isEmpty()) {
					for (AcreenciaObligacion acreenciaObligacionM : listaParaMigrar) {
						acreenciaObligacionM.setCodAcreenciaObligacion(null);
						acreenciaObligacionM.setCodDeclaracion(declaracionRenta.getCodDeclaracion());
						ComunicacionServiciosBR.setacreenciaobligacion(acreenciaObligacionM);
					}
				}
				else {
					this.sinAcreencias = this.ultimaDecla.getFlgAcreenciaObligacion() != null && this.ultimaDecla.getFlgAcreenciaObligacion() == 1;
					this.declaracionRenta.setFlgAcreenciaObligacion(this.ultimaDecla.getFlgAcreenciaObligacion() != null ? this.ultimaDecla.getFlgAcreenciaObligacion() : 0);
				}
			}
			else
				this.sinAcreencias = this.declaracionRenta.getFlgAcreenciaObligacion() == 1;
			this.listaAcreObligaciones = ComunicacionServiciosBR.getacreenciaobligacion(declaracionRenta.getCodDeclaracion().longValue(), 1);
		}
	}

	public void agregarNuevoBien() {
		this.formBienesHabilitado 	= true;
		this.patrimonioSeleccionado = new BienesPatrimonioExt();
		lbnuevoregistrogrilla 		= true;
		strToolTipBtnLimpiar 		= strToolTipBtnLimpiarNuevo;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:formularioBienPatrimonial:panelFormRegistroBien");	
	}

	public void mostrarDialogoEliminarBienPatrimonial() {
		RequestContext.getCurrentInstance().execute("$('#dialogEliminarBienPatrimonial').modal({backdrop: 'static', keyboard: false});");
	}
	
	/**
	 * método que realiza el eliminado lógico del bien patrimonial seleccionado por el usuario, informa dle resultado de la eliminación mediante un mensaje modal.
	 * 
	 * @return void
	 * @param none
	 */
	public void eliminarBienPatrimonial() {
		patrimonioSeleccionado.setFlgActivo((short) 0);
		patrimonioSeleccionado.setAudAccion(62);
		try {
			if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
				this.patrimonioSeleccionado.setAudCodRol(4);
			else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.patrimonioSeleccionado.setAudCodRol(3);
			else
				this.patrimonioSeleccionado.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}			
		Boolean eliminadoBienPatrimonial = ComunicacionServiciosBR.setBienesPatrimonio(patrimonioSeleccionado);
		if (eliminadoBienPatrimonial) {
			RequestContext.getCurrentInstance().execute("$('#dialogEliminarBienPatrimonial').modal('hide');");
			this.patrimonioSeleccionado = new BienesPatrimonioExt();
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
		}
		if (!eliminadoBienPatrimonial) {
			declaracionRenta.setTabIndex((short) 2);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		}
		this.cargarBienesPatrimoniales();
	}

	public void actualizarBien() {
		this.formBienesHabilitado = true;
	}

	public void activarFormularioRegistroBienes() {
		this.formBienesHabilitado = true;
	}

	public void cancelarFormularioBienesPatrimonial() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(CONST_INDEX);
		} catch (Exception ex) {
			logger.log().error("Error en public void cancelarFormularioBienesPatrimonial(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}
	
	public void configurarIndexTabActual(byte tab) {
		if(this.indexTabPos / 10 > this.indexTab)
			this.indexTabPos = this.indexTabPos - (this.indexTabPos % 10) + this.indexTab;
		else
			this.indexTabPos = this.indexTab * 10 + this.indexTab;
		this.indexTabPos =8;
		this.declaracionRenta.setTabIndex((short) this.indexTabPos); 
	}
	
	public void configurarIndexTab(byte tab) {
		this.indexTab = tab + 1;
		if(this.indexTabPos / 10 > this.indexTab)
			this.indexTabPos = this.indexTabPos - (this.indexTabPos % 10) + this.indexTab;
		else
			this.indexTabPos = this.indexTab * 10 + this.indexTab;
		this.declaracionRenta.setTabIndex((short) this.indexTabPos); 
	}

	public void guardarContinuarBienPatrimonial() {
		try {
			if(!this.sinBienes && this.bienes.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
				return;
			}
			this.configurarIndexTab((byte) 5);
			this.habilitadoBienePatrimoniales = false;
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			lbMostrarFormulario=false;
			lbEditarFormulario=false;			
			RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO_TAB);
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, tituloGuardaFallido + " error: " + ex.getMessage());
		}
	}
	
	public void guardarContinuarIngresoLaboral() {
		try {
			if(!this.sinIngresosRentas && this.listaIngresosDeclaracion.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
				return;
			}
			this.configurarIndexTab((byte) 2);
			this.habilitadoTabIngresoLaboral = false;
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			lbMostrarFormulario=false;
			lbEditarFormulario=false;
			RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO_TAB);
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, tituloGuardaFallido + " error: " + ex.getMessage());
		}
	}
	
	public void guardarContinuarOtrosIngresos() {
		try {
			if(!this.sinOtrosIngresos && this.listaOtrosIngresos.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
				return;
			}
			this.configurarIndexTab((byte) 3);
			this.habilitadoTabOtrosIngresos = false;
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			lbMostrarFormulario=false;
			lbEditarFormulario=false;			
			BigDecimal total = new BigDecimal(0);
			if (!listaOtrosIngresos.isEmpty()) {
				for (int i = 0; i < listaOtrosIngresos.size(); i++)
					total.add(listaOtrosIngresos.get(i).getValor());
			}
			this.valorTotal = String.valueOf(total);
			RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO_TAB);
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, tituloGuardaFallido + " error: " + ex.getMessage());
		}
	}
	
	public void guardarContinuarActividadPrivada() {
		try {
			if(!this.sinActividadPrivada && this.listaActividadPrivada.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
				return;
			}
			this.configurarIndexTab((byte) 6);
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			lbMostrarFormulario=false;
			lbEditarFormulario=false;			
			this.habilitadoTabActividadEconomica = false;
			RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO_TAB);
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, tituloGuardaFallido + " error: " + ex.getMessage());
		}
	}
	
	public void guardarContinuarAcreenciasObligaciones() {
		try {
		if(!this.sinAcreencias && this.listaAcreObligaciones.isEmpty()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
			return;
		}
		this.configurarIndexTab((byte) 7);
		ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
		lbMostrarFormulario=false;
		lbEditarFormulario=false;		
		this.habilitadoTabAcreenciasObligaciones = false;
		RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO_TAB);
		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, tituloGuardaFallido + " error: " + ex.getMessage());
		}
	}
	
	public void guardarParticipacionContinuar() {
		guardadoDefinitivoHabilitado = false;
		RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO);
	}
	
	public void guardadoParcialParientes() {
		if(!this.sinParientes && this.parientes.isEmpty()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
			return;
		}
		try {
			this.configurarIndexTab((byte) 1);
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			this.habilitadoConsanguinidadConjugues = false;
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, textoGuardaParcialPar);
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, tituloGuardaFallido + " error: " + ex.getMessage());
		}
	}
	
	public void guardadoParcialCuentas() {
		if(!this.sinCuentas && this.cuentas.isEmpty()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
			return;
		}
		try {
			this.configurarIndexTab((byte) 4);
			this.habilitadoCuentas = false;
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			lbMostrarFormulario=false;
			lbEditarFormulario=false;
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, textoGuardaParcial);
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloGuardaFallido, textoGuardaFallido + " error: " + ex.getMessage());
		}
	}
	
	public void guardadoParcialParticipacion() {
		if(!this.sinParticipacion && this.participaciones.isEmpty()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
			return;
		}
		try {
			this.configurarIndexTab((byte) 8);
			this.habilitadoParticipacionJuntas = false;
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloGuardaExito, textoGuardaDefini);
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloGuardaFallido, tituloGuardaFallido + " error: " + ex.getMessage());
		}
	}
	
	public void guardarUltimotab() {
		try {
			String mensge = MessagesBundleConstants.getStringMessagesBundle("MSG_DECLARACION_SEC_FINAL", getLocale());
			this.guardarTabIndex((short) 9);
			this.habilitadoParticipacionJuntas = false;
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, mensge);
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloGuardaFallido, textoGuardaFallido);
		}
	}
	
	public void guardadoParcialBienes() {
		if(!this.sinBienes && this.bienes.isEmpty()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_MSG_MARCAR_CHECK);
			return;
		}
		try {
			this.configurarIndexTab((byte) 5);
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			this.habilitadoBienePatrimoniales = false;
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Proceso completado", textoGuardaParcial);
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloEliminaError, textoEliminaError + " error: " + ex.getMessage());
		}
	}
	
	public boolean verificarRegistroExistente() {
		for(BienesPatrimonioExt b : this.bienes) {
			if(this.patrimonioSeleccionado.getCodTipoBien().longValue() == b.getCodTipoBien().longValue() && this.patrimonioSeleccionado.getIdentificacion().equalsIgnoreCase(b.getIdentificacion()) && b != this.patrimonioSeleccionado){
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_REGISTRO_EXISTENTE);
				this.patrimonioSeleccionado.setCodTipoBien(null);
				this.patrimonioSeleccionado.setIdentificacion(null);
				return false;
			}
		}
		return true;
	}
	
	public void establecerRol() throws SIGEP2SistemaException{
		if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
			this.patrimonioSeleccionado.setAudCodRol(4);
		else { 
			if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.patrimonioSeleccionado.setAudCodRol(3);
		}
	}
	
	public void guardarBienPatrimonial() {
		if(!this.verificarRegistroExistente())
			return;
		if (this.patrimonioSeleccionado.getCodBienPatrimonio() != null)
			this.patrimonioSeleccionado.setAudAccion(63);
		else { 
			if(this.patrimonioSeleccionado.getCodBienPatrimonio() == null)
				this.patrimonioSeleccionado.setAudAccion(785);
		}
		try {
			if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
				this.patrimonioSeleccionado.setAudCodRol(4);
			else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.patrimonioSeleccionado.setAudCodRol(3);
			else
				this.patrimonioSeleccionado.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}		
		
		
		this.patrimonioSeleccionado.setCodDeclaracion(new BigDecimal(declaracionRenta.getCodDeclaracion().longValue()));
		this.patrimonioSeleccionado.setFlgActivo((short) 1);
		this.patrimonioSeleccionado.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		Boolean guardadoBien = ComunicacionServiciosBR.setBienesPatrimonio(patrimonioSeleccionado);
		if (guardadoBien) {
			if (this.patrimonioSeleccionado.getCodBienPatrimonio() == null) {
				this.inicializarBienesPatrimoniales();
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
			} 
			else { 
				if (this.patrimonioSeleccionado.getCodBienPatrimonio() != null) {
					this.inicializarBienesPatrimoniales();
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDICION_CORRECTA);
				}
			}
			this.patrimonioSeleccionado = new BienesPatrimonioExt();
			RequestContext.getCurrentInstance().execute("$('#divFormularioBienPatrimonial').attr('style','display:none');");
		} 
		else
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MessagesBundleConstants.BR_MSG_GUARDADO_FALLIDO);
	}

	public void mostrarDialogoEditarBienPatrimonial() {
		RequestContext.getCurrentInstance().execute("$('#dialogEditarBienPatrimonial').modal({backdrop: 'static', keyboard: false});");
		lbnuevoregistrogrilla = false;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;
	}

	public void guardarDatosDomicilio() {
		try {
			if(this.domicilio.getDireccionResidencia()==null || this.domicilio.getDireccionResidencia().isEmpty()){
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_DATO_DIRECCION);
				return;
			}			
			this.domicilio.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			this.domicilio.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
			if(this.domicilio.getCodDatosContacto() == null)
				this.domicilio.setAudAccion(785);
			else
				this.domicilio.setAudAccion(63);
			this.domicilio.setCodPersona(new BigDecimal(getUsuarioSesion().getCodPersona()));
			this.domicilio.setFlgActivo((short) 1);
			this.domicilio.setDefinitivo((short) 0);
			if (ComunicacionServiciosBR.setDatoContacto(domicilio)) {
				this.configurarIndexTab((byte) 0);
				this.habilitadoConsanguinidadConjugues = false;
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, textoGuardaExito);
			} 
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloGuardaFallido, textoGuardaFallido);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
			lbMostrarFormulario = false;
			lbEditarFormulario=false;
		}catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error en proceso", ComunicacionServiciosSis.getParametricaIntetos("BR_MSG_ERROR_SISTEMA").getValorParametro() + ex.getMessage());
		}
	}

	
	
	public void guardarAcreenciaObligacion() throws SIGEP2SistemaException {
		if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
			this.acreenciaOblicacion.setAudCodRol(4);
		else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
			this.acreenciaOblicacion.setAudCodRol(3);
		else
			this.acreenciaOblicacion.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		this.acreenciaOblicacion.setCodDeclaracion(declaracionRenta.getCodDeclaracion());
		if (this.acreenciaOblicacion.getCodAcreenciaObligacion() != null)
			this.acreenciaOblicacion.setAudAccion(63);
		else if (this.acreenciaOblicacion.getCodAcreenciaObligacion() == null)
			this.acreenciaOblicacion.setAudAccion(785);
		this.acreenciaOblicacion.setAudCodUsuario(getUsuarioSesion().getId());
		this.acreenciaOblicacion.setFlgActivo(Short.parseShort("1"));
		Boolean guardadoAcreenciaObligacion = ComunicacionServiciosBR.setacreenciaobligacion(acreenciaOblicacion);
		if (guardadoAcreenciaObligacion) {
			if (this.acreenciaOblicacion.getCodAcreenciaObligacion() == null) {
				this.mostrarAcreenciasObligaciones();
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
			} else if (this.acreenciaOblicacion.getCodAcreenciaObligacion() != null) {
				this.setBtnAcreencia("Guardar Acreencia obligacion");
				this.mostrarAcreenciasObligaciones();
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDICION_CORRECTA);
			}
			this.acreenciaOblicacion = new AcreenciaObligacion();
			RequestContext.getCurrentInstance().execute("$('#divFormObligaciones').attr('style','display:none');");
		} 
		else
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MessagesBundleConstants.BR_MSG_GUARDADO_FALLIDO);
	}
	
	public void guardarActividadPrivada() throws SIGEP2SistemaException {
		if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
			this.actividadPrivada.setAudCodRol(4);
		else if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.actividadPrivada.setAudCodRol(3);
		else
			this.actividadPrivada.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		this.actividadPrivada.setCodDeclaracion(declaracionRenta.getCodDeclaracion());
		if (this.actividadPrivada.getCodActividadPrivada() != null)
			this.actividadPrivada.setAudAccion(63);
		else {
			if (this.actividadPrivada.getCodActividadPrivada() == null) 
				this.actividadPrivada.setAudAccion(785);
		}
		this.actividadPrivada.setFlgActivo(1);
		this.actividadPrivada.setAudCodUsuario(getUsuarioSesion().getId());
		Boolean guardadoActividadPrivada = ComunicacionServiciosBR.setactividadprivada(actividadPrivada);
		if (guardadoActividadPrivada) {
			if (this.actividadPrivada.getCodActividadPrivada() == null) {
				this.mostrarActividadesPrivadas();
				this.habilitadoTabActividadEconomica = true;
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
			} 
			else if (this.actividadPrivada.getCodActividadPrivada() != null) {
				this.setBtnActividadEconomica("Guardar Actividad Económica");
				this.mostrarActividadesPrivadas();
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDICION_CORRECTA);
			}
			this.actividadPrivada = new ActividadPrivadaExt();
			RequestContext.getCurrentInstance().execute("$('#divFormActividadPrivada').attr('style','display:none');");
		} 
		else
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MessagesBundleConstants.BR_MSG_GUARDADO_FALLIDO);
		if (!guardadoActividadPrivada)
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		
	}

	public boolean verificarCuentaExistente() {
		if(this.cuentas != null) {
			for(CuentasDeclaracion cuentasDeclaracion : this.cuentas) {
				if(this.cuentaSeleccionada.getCodTipoCuenta().equals(cuentasDeclaracion.getCodTipoCuenta()) && this.cuentaSeleccionada.getEntidad().equals(cuentasDeclaracion.getEntidad()) && this.cuentaSeleccionada.getNumeroCuenta().equalsIgnoreCase(cuentasDeclaracion.getNumeroCuenta()) &&  this.cuentaSeleccionada != cuentasDeclaracion )
					return  true;
			}
		}
		return false;
	}
	
	public void guardadoDefinitivo() {
		IngresosDeclaracion ing = new IngresosDeclaracion();
		ing.setCodDeclaracion(this.declaracionRenta.getCodDeclaracion().longValue());
		ing = ComunicacionServiciosBR.getTotalIngresos(ing);
		this.declaracionRenta.setTotalIngresos(ing.getValor());
		String mensge = MessagesBundleConstants.getStringMessagesBundle("MSG_DECLARACION_GUARDADO_FINAL",getLocale());
		String opc = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(declaracionRenta.getCodTipoDeclaracion())).getNombreParametro();
		mensge = mensge.replace("#tipo", opc);
		try {
			this.guardarTabIndex((short) 9);
			this.mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, "", mensge, "Aceptar", "location.href = '../persona/informacionPersonal.xhtml?recursoId=HojaDeVidaSubMenu'");
		} catch (Exception ex) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, tituloGuardaFallido, textoGuardaFallido));
		}
	}
	
	public void guardarParticipacion() {
		try {
			if(this.participacionSeleccionada.getEntidad() != null && this.verificarParticipacionDuplicada())
				return;
			if (this.participacionSeleccionada.getCodParticipacionJunta() != null)
				this.participacionSeleccionada.setAudAccion(63);
			else
				this.participacionSeleccionada.setAudAccion(785);
			if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
				this.participacionSeleccionada.setAudCodRol(4);
			else if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.participacionSeleccionada.setAudCodRol(3);
			else
				this.participacionSeleccionada.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			this.formParticipacionHabilitado = false;
			if (this.participacionSeleccionada.getCodParticipacionJunta() == null)
				this.participacionSeleccionada.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
			this.participacionSeleccionada.setFlgActivo((short) 1);
			this.participacionSeleccionada.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			this.participacionSeleccionada.setAudFechaActualizacion(new Date());
			if (ComunicacionServiciosBR.setparticipacionjunta(this.participacionSeleccionada))
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloGuardaExito, textoGuardaExito);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloGuardaFallido, textoGuardaFallido);
			this.inicializarParticipacion();
		} catch (Exception ex) {
			logger.log().error("Error en public void guardarParticipacion() BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloGuardaFallido, textoGuardaFallido + " Error: " + ex.getMessage());
		}
	}
	
	private void guardarTabIndex(Short index) {
		Declaracion declaracion;
		declaracion = ComunicacionServiciosBR.getdeclaracionid(this.codDeclaracion);
		declaracion.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
		this.indexTab = (index == 9) ? 8 : index;
		declaracion.setTabIndex((short) 88);
		declaracion.setConfirmacion((index == 9) ? (short) 1 : (short) 0);
		declaracion.setFechaPresentacion(DateUtils.getFechaSistema());
		if(index==9) {
			if(declaracion.getCodTipoDeclaracion().equals(COD_TIPO_DECLARACION_PERIODICA)){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date dtFechaLimitePresentacion;
				try {
					dtFechaLimitePresentacion = sdf.parse((String)this.declarationSesion.getSessionMap().get("fechaLimite"));
					if(validaFechaAfter (dtFechaLimitePresentacion,new Date()))
						declaracion.setFlgExtemporanea((short) 1);
					else
						declaracion.setFlgExtemporanea((short) 0);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				declaracion.setFlgExtemporanea((short) 0);
			}
		}
		ComunicacionServiciosBR.setDeclaracionUp(declaracion);
		if(declaracion.getConfirmacion() == 1){ 
			DatoContactoExt datoContacto = ComunicacionServiciosBR.getDatoContacto(declaracion.getCodPersona().longValue(), declaracion.getCodDeclaracion().longValue());
			datoContacto.setDefinitivo((short) 1);
			ComunicacionServiciosBR.setDatoContacto(datoContacto);
		}
	}
	
	public void declararSinParientes() {
		this.parientes = new ArrayList<>();
	}
	
	private void inicializaPersona() {
		if(usuarioSesion!=null && usuarioSesion.getCodPersona()!=null) {
			personaDeclaracion 	= ComunicacionServiciosHV.getPersonaporIdExt(usuarioSesion.getCodPersona());
		}
		
		nombreUsuario 		= personaDeclaracion.getNombreUsuario()!=null ? personaDeclaracion.getNombreUsuario().toLowerCase():"";
		nombreTipoDocumento = personaDeclaracion.getNombreTipoDocuento()!=null ? personaDeclaracion.getNombreTipoDocuento().toLowerCase():"";
		numeroIdentificacion = personaDeclaracion.getNumeroIdentificacion()!=null ?personaDeclaracion.getNumeroIdentificacion().toLowerCase():"";
	}

	public void inicializarDatosDomicilio() {
		try {
			ExternalContext declaration = FacesContext.getCurrentInstance().getExternalContext();
			UsuarioDTO user = (UsuarioDTO) declaration.getSessionMap().get("usuarioSesion");
			this.domicilio = ComunicacionServiciosBR.getDatoContacto(user.getCodPersona(), declaracionRenta.getCodDeclaracion().longValue());
			this.editarDireccion = new EditarDireccionDTO();
			if (this.domicilio == null)
				this.domicilio = new DatoContactoExt();
			if (this.domicilio.getCodPaisResidencia() != null) {
				Pais pais = ComunicacionServiciosSis.getpisporid(domicilio.getCodPaisResidencia());
				if (pais.getNombrePais().trim().equalsIgnoreCase("COLOMBIA")) {
					RequestContext.getCurrentInstance().execute("$('#col').attr('style','display:block');");
					RequestContext.getCurrentInstance().execute("$('#otro').attr('style','display:none');");
					this.localidadHabilitada = false;
				} else {
					RequestContext.getCurrentInstance().execute("$('#col').attr('style','display:none');");
					RequestContext.getCurrentInstance().execute("$('#otro').attr('style','display:block');");
					this.localidadHabilitada = true;
				}
			}
			this.cambiarBanderaPais();
			this.agregarIndicativoDepartamento();
			
		} catch (Exception ex) {
			logger.log().error("Error en public void inicializarDatosDomicilio(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}
	
	/**
	 * Metodo para convertir la direccion y mostrarla*/
	public String mostrarDireccionGenerada() {
		String direccion = "";
		try {	
			if (domicilio.getDireccionResidencia() != null && !domicilio.getDireccionResidencia().isEmpty()) {
				Direccion dir = new Direccion();
				dir.llenarDatosDesdeJson(domicilio.getDireccionResidencia());
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
	 * 
	 * Método que verifica si existen registros de periodos anteriores para cargar en el tab bienes patrimoniales de la declaracion
	 * en proceso, en caso de ser así los relaciona a estas. 
	 * 
	 * @return void
	 * @param none
	 */
	public void inicializarBienesPatrimoniales() {
		try {
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			this.bienConfig = new BienesPatrimonioExt();
			this.formBienesHabilitado = false;
			this.patrimonioSeleccionado = new BienesPatrimonioExt();
			this.bienConfig.setFlgActivo((short) 1);
			this.bienConfig.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
			this.bienes = ComunicacionServiciosBR.getBienesPatrimonio(bienConfig);
			if (this.bienes.isEmpty()) {
				if (Boolean.parseBoolean(this.paraCarga) && declaracionRenta.getFlgSinBienesPatrimoniales() == 0) {
					this.bienConfig.setCodDeclaracion(new BigDecimal(idUltimaDecla));
					this.bienes = ComunicacionServiciosBR.getBienesPatrimonio(bienConfig);
					if(!this.bienes.isEmpty()) {
						for (BienesPatrimonioExt b : this.bienes) {
							b.setCodBienPatrimonio(null);
							b.setCodDeclaracion(new BigDecimal(codDeclaracion));
							ComunicacionServiciosBR.setBienesPatrimonio(b);
						}
					}
					else 
						this.declaracionRenta.setFlgSinBienesPatrimoniales(this.ultimaDecla.getFlgSinBienesPatrimoniales() != null ? this.ultimaDecla.getFlgSinBienesPatrimoniales() : 0);
				}
			}
			this.sinBienes = this.declaracionRenta.getFlgSinBienesPatrimoniales() !=null ? this.declaracionRenta.getFlgSinBienesPatrimoniales() == 1: false;
			this.bienConfig.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
			this.bienes = ComunicacionServiciosBR.getBienesPatrimonio(bienConfig);			
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error en public void inicializarBienesPatrimoniales(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}
	
	public void inicializarParientes() {
		try {
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			PersonaParentescoExt parienteConfig = new PersonaParentescoExt();
			this.parienteSeleccionado = new PersonaParentescoExt();
			parienteConfig.setCodPersona(new BigDecimal(idPersona));
			parienteConfig.setFlgActivo((short) 1);
			parienteConfig.setCodDeclaracion(this.codDeclaracion);
			this.parientes = ComunicacionServiciosBR.getPersonaParentescoPersona(parienteConfig);
			if (this.parientes.isEmpty()) {
				if (Boolean.parseBoolean(this.paraCarga)) {
					parienteConfig.setCodDeclaracion(Long.parseLong(this.idUltimaDecla));
					this.parientes = ComunicacionServiciosBR.getPersonaParentescoPersona(parienteConfig);
					if(!this.parientes.isEmpty()) {
						for (PersonaParentescoExt p : parientes) {
							p.setCodDeclaracion(codDeclaracion);
							p.setCodPersonaParentesco(null);
							ComunicacionServiciosBR.setPersonaParentescoPersona(p);
						}
					}
					else 
						this.declaracionRenta.setFlgSinParientesConyugues(this.ultimaDecla.getFlgSinParientesConyugues() != null ? this.ultimaDecla.getFlgSinParientesConyugues() : 0);
				}
			}
			this.sinParientes = declaracionRenta.getFlgSinParientesConyugues()!=null ? declaracionRenta.getFlgSinParientesConyugues() == 1: false;
			lbMostrarFormulario = false;
			parienteConfig.setCodDeclaracion(this.codDeclaracion);
			this.parientes = ComunicacionServiciosBR.getPersonaParentescoPersona(parienteConfig);
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error en public void inicializarParientes(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}
	
	public void inicializarCuentas() {
		try {
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			this.cuentas = ComunicacionServiciosBR.getcuentasdeclaracion(this.codDeclaracion, 1);
			if (this.cuentas.isEmpty()) {
				if (Boolean.parseBoolean(this.paraCarga) && declaracionRenta.getFlgSinCuentasAhorro() == 0) {
					this.cuentas = ComunicacionServiciosBR.getcuentasdeclaracion(Long.parseLong(this.idUltimaDecla), 1);
					if(!this.cuentas.isEmpty()) {
						for (CuentasDeclaracion c : this.cuentas) {
							c.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
							c.setCodCuentaDeclaracion(null);
							this.cuentaSeleccionada = c;
							this.cuentaSeleccionada.setAudAccion(63);
							this.cuentaSeleccionada.setAudCodRol((int) usuarioSesion.getCodRol());
							this.cuentaSeleccionada.setAudCodUsuario(new BigDecimal(usuarioSesion.getId()));
							this.cuentaSeleccionada.setAudFechaActualizacion(new Date());
							this.cuentaSeleccionada.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
							ComunicacionServiciosBR.setcuentasdeclaracion(cuentaSeleccionada);
						}
					}
					else 
						this.declaracionRenta.setFlgSinCuentasAhorro(this.ultimaDecla.getFlgSinCuentasAhorro() != null ? this.ultimaDecla.getFlgSinCuentasAhorro() : 0);
					
				}
				this.nombreBotonGuardarCuenta = "Guardar cuenta bancaria";
			}
			this.sinCuentas = this.declaracionRenta.getFlgSinCuentasAhorro()!=null ? this.declaracionRenta.getFlgSinCuentasAhorro() == 1: false;
			this.cuentas = ComunicacionServiciosBR.getcuentasdeclaracion(this.codDeclaracion, 1);
			this.cuentaSeleccionada = new CuentasDeclaracion();			
		} catch (Exception ex) {
			logger.log().error("Error en public void inicializarCuentas(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}
	
	public void inicializarParticipacion() {
		try {
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			this.participaciones = ComunicacionServiciosBR.getparticipacionjunta(this.codDeclaracion, 1);
			if (this.participaciones.isEmpty()) {
				if (Boolean.parseBoolean(this.paraCarga) && this.declaracionRenta.getFlgSinParticipacionJuntas() == 0) {
					this.participaciones = ComunicacionServiciosBR.getparticipacionjunta(Long.parseLong(idUltimaDecla), 1);
					if(this.participaciones.isEmpty()) {
						this.sinParticipacion = this.ultimaDecla.getFlgSinParticipacionJuntas() != null && this.ultimaDecla.getFlgSinParticipacionJuntas() == 1;
						this.declaracionRenta.setFlgSinParticipacionJuntas(this.ultimaDecla.getFlgSinParticipacionJuntas() != null ? this.ultimaDecla.getFlgSinParticipacionJuntas() : 0);
					}
					else {
						for (ParticipacionJuntaExt p : this.participaciones) {
							p.setCodParticipacionJunta(null);
							p.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
							ComunicacionServiciosBR.setparticipacionjunta(p);
						}
					}
				}
			}
			this.sinParticipacion =  this.declaracionRenta.getFlgSinParticipacionJuntas()!=null ? this.declaracionRenta.getFlgSinParticipacionJuntas() == 1: false;
			this.participaciones = ComunicacionServiciosBR.getparticipacionjunta(this.codDeclaracion, 1);
			this.participacionSeleccionada = new ParticipacionJuntaExt();
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error en public void inicializarParticipacion() BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}
	
	/**
	 * @param ningún
	 *            valor
	 * @return ningún valor Método que carga una lista del tipo
	 *         IngresosDeclaracionExt con todos los registro existentes en la base
	 *         de datos con un codigo de declaración.
	 */
	public void mostrarIngresosLaborales() {
		if(declaracionRenta!=null && declaracionRenta.getCodDeclaracion()!=null) {
			this.ingresosDeclaracion = new IngresosDeclaracionExt();
			this.ingresosDeclaracion.setCodDeclaracion(Long.parseLong(declaracionRenta.getCodDeclaracion().toString()));
			this.ingresosDeclaracion.setFlgActivo(1);
			this.listaIngresosDeclaracion = ComunicacionServiciosBR.getingresosdeclaracion(ingresosDeclaracion);
			this.sinIngresosRentas = declaracionRenta.getFlgTengoIngresosLaborales() !=null ? declaracionRenta.getFlgTengoIngresosLaborales() == 1: false;
			this.totalizarIngresosRentasLaborales();
		}
	}
	
	public void mostrarOtrosIngresos() {
		setTotalOtrosIngresos(new BigDecimal(0));
		DecimalFormat formato = new DecimalFormat("#,###.00");
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		this.listaOtrosIngresos = ComunicacionServiciosBR.getotrosingresosdeclaracion(declaracionRenta.getCodDeclaracion().longValue(), 1);
		for(OtrosIngresosDeclaracionExt otrosIngresosT : listaOtrosIngresos)
			this.totalOtrosIngresos = otrosIngresosT.getValor().add(totalOtrosIngresos);
		this.valorTotal = formato.format(totalOtrosIngresos);
		this.sinOtrosIngresos =  this.declaracionRenta.getFlgTengoOtrosIngresos()!=null ? this.declaracionRenta.getFlgTengoOtrosIngresos() == 1: false;
		this.estadoBtnOtroIn = this.sinOtrosIngresos;
		this.valorTotal .replace(",", "a");
		this.valorTotal .replace(".", ",");
		this.valorTotal .replace("a", ".");		
		RequestContext.getCurrentInstance().update("tablaOtrosIngresos");
	}
	
	public void mostrarAcreenciasObligaciones() {
		this.listaAcreObligaciones = ComunicacionServiciosBR.getacreenciaobligacion(declaracionRenta.getCodDeclaracion().longValue(), 1);
		this.sinAcreencias = this.declaracionRenta.getFlgAcreenciaObligacion() !=null ? this.declaracionRenta.getFlgAcreenciaObligacion() == 1: false;
	}
	
	public void mostrarActividadesPrivadas() {
		this.listaActividadPrivada = ComunicacionServiciosBR.getactividadprivada(declaracionRenta.getCodDeclaracion().longValue(), 1);
		this.sinActividadPrivada = this.declaracionRenta.getFlgActividadEconomicaPrivada()!=null ? this.declaracionRenta.getFlgActividadEconomicaPrivada() == 1: false;
	}

	public String obtenerMensajeParametrico(String nombreMensaje) {
		setMensajeParametrico(ComunicacionServiciosSis.getParametricaIntetos(nombreMensaje).getValorParametro());
		return mensajeParametrico;
	}

	/**
	 * Método que habilita el formulario de Ingresos Laborales y Rentas.
	 */
	public void habilitarFormularioIngresoRenta() {
		this.habilitadoFormularioIngresoRenta = true;
		RequestContext.getCurrentInstance().update("tabViewBienesRentas:formularioIngresos");
	}

	public void eliminarIngresoLaboralRentas() {
		this.ingresosDeclaracion.setFlgActivo(0);
		this.ingresosDeclaracion.setAudAccion(62);
		try {
			if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
				this.ingresosDeclaracion.setAudCodRol(4);
			else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.ingresosDeclaracion.setAudCodRol(3);
			else
				this.ingresosDeclaracion.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}				
		Boolean eliminadoIngresoLaboral = ComunicacionServiciosBR.setingresosdeclaracion(ingresosDeclaracion);
		if (eliminadoIngresoLaboral) {
			this.mostrarIngresosLaborales();
			RequestContext.getCurrentInstance().execute("$('#dialogEliminarIngresosRenta').modal('hide');");
			this.ingresosDeclaracion = new IngresosDeclaracionExt();
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
		}
		if (!eliminadoIngresoLaboral) {
			this.declaracionRenta.setTabIndex((short) 2);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		}
	}

	public void cancelarFormularioIngresoLaboral() {
		this.limpiarFormularioIngresoLaboral();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(CONST_INDEX);
		} catch (Exception ex) {
			logger.log().error("Error en public void cancelarFormularioIngresoLaboral(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}

	public void limpiarFormularioIngresoLaboral() {
		this.declaracionRenta = new Declaracion();
		this.ingresosDeclaracion = new IngresosDeclaracionExt();
		this.habilitadoMensajeConfirmacion = false;
		this.habilitadoFormularioIngresoRenta = false;
	}



	

	public void eliminarActividadPrivada() {
		this.actividadPrivada.setFlgActivo(0);
		this.actividadPrivada.setAudAccion(62);
		try {
			if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
				this.actividadPrivada.setAudCodRol(4);
			else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.actividadPrivada.setAudCodRol(3);
			else
				this.actividadPrivada.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}				
		Boolean guardadoActividadPrivada = ComunicacionServiciosBR.setactividadprivada(actividadPrivada);
		if (guardadoActividadPrivada) {
			this.mostrarActividadesPrivadas();
			RequestContext.getCurrentInstance().execute("$('#dialogEliminarActividadEconomica').modal('hide');");
			this.actividadPrivada = new ActividadPrivadaExt();
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
		}
		if (!guardadoActividadPrivada) {
			this.declaracionRenta.setTabIndex((short) 6);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		}
	}



	public void cancelarActividadPrivada() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(CONST_INDEX);
		} catch (Exception ex) {
			logger.log().error("Error en public void cancelarActividadPrivada(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}

	public void cancelarAcreenciasObligaciones() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(CONST_INDEX);
		} catch (Exception ex) {
			logger.log().error("Error en public void cancelarAcreenciasObligaciones(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}

	

	public void redireccionarIndex() {
		String mensge = ComunicacionServiciosSis.getParametricaIntetos("BR_DIALOGO_CANCELAR").getValorParametro();
		this.mostrarMensajeSiNo(FacesMessage.SEVERITY_INFO, "", mensge, "location.href = '../persona/informacionPersonal.xhtml?recursoId=HojaDeVidaSubMenu';", "", "Si", "No");
	}

	public void confirmarActualizadoBien(BienesPatrimonioExt bienPatrimonio) {
		this.patrimonioSeleccionado = bienPatrimonio;
	}
	private boolean lbMostrarFormulario=false, lbEditarFormulario;
	
	public boolean isLbMostrarFormulario() {
		return lbMostrarFormulario;
	}

	public void setLbMostrarFormulario(boolean lbMostrarFormulario) {
		this.lbMostrarFormulario = lbMostrarFormulario;
	}

	public boolean isLbEditarFormulario() {
		return lbEditarFormulario;
	}

	public void setLbEditarFormulario(boolean lbEditarFormulario) {
		this.lbEditarFormulario = lbEditarFormulario;
	}

	/*Acciones Parientes Cónyugue*/
	/*Agregar pariente*/
	public void agregarNuevoPariente() {
		this.parienteSeleccionado = new PersonaParentescoExt();
		lbMostrarFormulario = true;
		lbEditarFormulario = true;
		lbnuevoregistrogrilla = true;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarNuevo;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:frmParientesConyugu:frmRegistroParientes");		
	}
	/*Ver pariente*/
	public void verDetallesPariente(PersonaParentescoExt persona) {
		this.parienteSeleccionado = persona;
		lbMostrarFormulario = true;
		lbEditarFormulario = false;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:frmParientesConyugu:frmRegistroParientes");
	}	
	/*confirmar editar pariente*/
	public void confirmarActualizadoPariente(PersonaParentescoExt persona) {
		this.parienteSeleccionado = persona;
		RequestContext.getCurrentInstance().execute("$('#dlgModifi').modal({backdrop: 'static', keyboard: false});");
	}
	/*editar pariente*/
	public void actualizarPariente() {
		this.configurarRadio();
		lbnuevoregistrogrilla = false;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;
		codTipoIdentificacionAnt = parienteSeleccionado.getCodTipoIdentificacion();
		numIdentificacionAnt = parienteSeleccionado.getNumIdentificacion();
		lbMostrarFormulario = true;
		lbEditarFormulario = true;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:frmParientesConyugu:frmRegistroParientes");		
	}
	public void confirmaEliminarariente(PersonaParentescoExt persona) {
		this.parienteSeleccionado = persona;
		lbMostrarFormulario = false;
	}	
	/*Eliminar pariente*/
	public void eliminarPariente() {
		try {
			this.parienteSeleccionado.setFlgActivo((short) (0));
			this.parienteSeleccionado.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			try {
				if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
					this.parienteSeleccionado.setAudCodRol(4);
				else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
					this.parienteSeleccionado.setAudCodRol(3);
				else
					this.parienteSeleccionado.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}	
			this.parienteSeleccionado.setAudAccion(62);
			if(ComunicacionServiciosBR.setPersonaParentescoPersona(parienteSeleccionado))
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha eliminado exitosamente el registro");
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloEliminaError, textoEliminaError);
			this.cargarParientes();
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloEliminaError, textoEliminaError + " error: " + ex.getMessage());
		}
	}	
	/*Guardar parient*/
	public void guardarPariente() {
		if(validarNumeroIdentificacionUnico()){
			return;
		}
		try {
			if (this.parienteSeleccionado.getCodTipoParentesco() < 0) {
				FacesContext.getCurrentInstance().addMessage("msgCmbParent", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se requiere un valor", ""));
				RequestContext.getCurrentInstance().update("msgCmbParent");
				return;
			}
			if (this.parienteSeleccionado.getCodPersonaParentesco() == null) {
				this.parienteSeleccionado.setAudAccion(785);
				this.parienteSeleccionado.setCodPersona(new BigDecimal(idPersona));
			} 
			else
				this.parienteSeleccionado.setAudAccion(63);
			int codParentesco = this.parienteSeleccionado.getCodTipoParentesco().intValue();
			if((codParentesco == TipoParametro.PADRE.getValue() || codParentesco == TipoParametro.MADRE.getValue()) && this.parienteSeleccionado.getAudAccion() == 785) {
				for(PersonaParentescoExt p : parientes) {
					if(p.getCodTipoParentesco() == codParentesco && p != this.parienteSeleccionado) {
						this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_PARIENTE_EXISTE);
						return;
					}
				}
			}
			LocalDate hoy = LocalDate.now();   
			LocalDate nacimiento = this.parienteSeleccionado.getFechaNacimiento().toInstant().
			           atZone(ZoneId.systemDefault()).toLocalDate(); 
			 long edad = ChronoUnit.YEARS.between(nacimiento, hoy); 
			if(codParentesco == TipoParametro.COMPANERO_PERMANENTE.getValue() || codParentesco == TipoParametro.CONYUGUE.getValue()) {
				 if(edad < 14) {
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_CONJUGUE_MENOR);
					return;
				  }
			}
			if(codParentesco == TipoParametro.PADRE.getValue() || codParentesco == TipoParametro.MADRE.getValue()) {
				 if(edad < 18) {
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_PADRES_MENOR);
					return;
				  }
			}
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			this.estadoRadioPariente = false;
			this.parienteSeleccionado.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			this.parienteSeleccionado.setFlgSociedadConyugalActiva(parienteSeleccionado.getFlgSociedadConyugalActiva() == null 
					|| parienteSeleccionado.getFlgSociedadConyugalActiva() == 0 ? (short) 0 : (short) 1);
			try {
				if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
					this.parienteSeleccionado.setAudCodRol(4);
				else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
					this.parienteSeleccionado.setAudCodRol(3);
				else
					this.parienteSeleccionado.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}	
			this.parienteSeleccionado.setFlgActivo((short) 1);
			this.parienteSeleccionado.setCodDeclaracion(this.codDeclaracion);
			boolean repetido = false;
			if(this.parientes != null) {
				for (PersonaParentescoExt personaParentescoExt : this.parientes) {
					if(this.parienteSeleccionado != personaParentescoExt && this.parienteSeleccionado.getCodTipoIdentificacion() == personaParentescoExt.getCodTipoIdentificacion() && this.parienteSeleccionado.getNumIdentificacion().equals(personaParentescoExt.getNumIdentificacion()) && (this.parienteSeleccionado.getCodPersonaParentesco() != null) && this.parienteSeleccionado.getAudAccion() == 785) {
						this.parienteSeleccionado.setNumIdentificacion(null);
						repetido = true;
					}
				}
			}
			if(!repetido) {
				if (ComunicacionServiciosBR.setPersonaParentescoPersona(parienteSeleccionado)) {
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloGuardaExito, textoGuardaExito);
					this.declaracionRenta.setFlgSinParientesConyugues((short) 0);
					ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
				} 
				else
					this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloGuardaFallido, textoGuardaFallido);
			}
			else 
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,"Tipo de Documento y Numero Ya Ingresados");
			
			this.inicializarParientes();
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error en public void guardarPariente(); BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
		parienteSeleccionado = new PersonaParentescoExt();
	}	
	/*Limpiar*/
	public void limpiarPariente() {
		if(lbnuevoregistrogrilla) {
			parienteSeleccionado.setCodTipoIdentificacion(null);
			parienteSeleccionado.setCodTipoParentesco(null);
			parienteSeleccionado.setFechaNacimiento(null);
		}else {
			this.inicializarParientes();
		}
	}
	/*FIN ACCIONES PARIENTES*/
	
	/*ACCIONES INGRESOS INGRESOS Y RENTAS LABORALES*/
	public void crearIngreso() {
		this.ingresosDeclaracion = new IngresosDeclaracionExt();
		lbnuevoregistrogrilla = true;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarNuevo;
		lbMostrarFormulario = true;
		lbEditarFormulario = true;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:formularioIngresosRenta:panelFormularioIngresoLaboral_content");		
	}	
	
	public void verIngresolaboral(){
		lbMostrarFormulario = true;
		lbEditarFormulario = false;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:formularioIngresosRenta:panelFormularioIngresoLaboral_content");
	}
	
	
	public void mostrarDialogoEditarIngresoLaboral() {
		this.setBtnGuardarIngresoRenta(CONST_TEXT_GUARDAR_CAMBIOS);
		lbnuevoregistrogrilla = false;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;
		lbMostrarFormulario = true;
		lbEditarFormulario = true;
		RequestContext.getCurrentInstance().execute("$('#dialogEditarIngresosRenta').modal({backdrop: 'static', keyboard: false});");

	}

	public void mostrarDialogoEliminarIngresoLaboral() {
		lbMostrarFormulario = false;
		lbEditarFormulario = false;
		RequestContext.getCurrentInstance().execute("$('#dialogEliminarIngresosRenta').modal({backdrop: 'static', keyboard: false});");
	}	
	public void guardarIngresoLaboralRentas() throws SIGEP2SistemaException {
		if (ingresosDeclaracion.getValor().longValue() > 9999999999999999L) {
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "El valor máximo permitido es 9999999999999999.");
			return;
		}
		if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
			this.ingresosDeclaracion.setAudCodRol(4);
		else if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
			this.ingresosDeclaracion.setAudCodRol(3);
		else
			this.ingresosDeclaracion.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		if (this.ingresosDeclaracion.getCodIngresoDeclaracion() != null)
			this.ingresosDeclaracion.setAudAccion(63);
		else if (ingresosDeclaracion.getCodIngresoDeclaracion() == null)
			this.ingresosDeclaracion.setAudAccion(785);
		this.ingresosDeclaracion.setCodDeclaracion(declaracionRenta.getCodDeclaracion().longValue());
		this.ingresosDeclaracion.setFlgActivo(1);
		this.ingresosDeclaracion.setAudCodUsuario(getUsuarioSesion().getId());
		Boolean guardadoIngresoLaboral = ComunicacionServiciosBR.setingresosdeclaracion(ingresosDeclaracion);
		if (guardadoIngresoLaboral) {
			if (this.ingresosDeclaracion.getCodIngresoDeclaracion() == null) {
				this.mostrarIngresosLaborales();
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
			} 
			else if (this.ingresosDeclaracion.getCodIngresoDeclaracion() != null) {
				this.setBtnGuardarIngresoRenta("Guardar y Continuar");
				this.mostrarIngresosLaborales();
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_EDICION_CORRECTA);
			}
			this.ingresosDeclaracion = new IngresosDeclaracionExt();
			RequestContext.getCurrentInstance().execute("$('#divFormularioIngresoLaboral').attr('style','display:none');");
			RequestContext.getCurrentInstance().update("formularioIngresosRenta");
		} 
		else
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MessagesBundleConstants.BR_MSG_GUARDADO_FALLIDO);
		this.totalizarIngresosRentasLaborales();
		lbMostrarFormulario = false;
		lbEditarFormulario = false;		
	}	
	public void limpiarIngresosRentas() {
		if(!lbnuevoregistrogrilla) {
			this.mostrarIngresosLaborales();
			this.totalizarIngresosRentasLaborales();
			this.ingresosDeclaracion = new IngresosDeclaracionExt();
			RequestContext.getCurrentInstance().execute("$('#divFormularioIngresoLaboral').attr('style','display:none');");
			RequestContext.getCurrentInstance().update("formularioIngresosRenta");	
			lbMostrarFormulario = false;
		}
	}	
	/*fin ACCIONES INGRESOS INGRESOS Y RENTAS LABORALES*/
	
	/*ACCIONES OTROS INGRESOS*/
	public void crearIngresoNoLaboral() {
		lbnuevoregistrogrilla = true;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarNuevo;
		otrosIngresos = new OtrosIngresosDeclaracionExt();
		lbMostrarFormulario = true;
		lbEditarFormulario = true;
	}
	public void verDetalleOtroIngreso() {
		lbMostrarFormulario = true;
		lbEditarFormulario = false;	
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:formOtrosIngresos:panelFormOtrosIngresos_content");
	}	
	public void mostrarDialogoEditarOtrosIngresos() {
		setBtnGuardarOtrosIngresos(CONST_TEXT_GUARDAR_CAMBIOS);
		this.lbnuevoregistrogrilla = false;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;		
		RequestContext.getCurrentInstance().execute("$('#dialogEditarOtrosIngresos').modal({backdrop: 'static', keyboard: false});");
	}	
	public void editarIngresoNoLaboral() {
		lbMostrarFormulario=true;
		lbEditarFormulario=true;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:formOtrosIngresos:panelFormOtrosIngresos_content");
	}	




	public void mostrarDialogoEliminarOtrosIngresos() {
		RequestContext.getCurrentInstance().execute("$('#dialogEliminarOtrosIngresos').modal({backdrop: 'static', keyboard: false});");
	}

	public void eliminarOtroIngreso() {
		otrosIngresos.setFlgActivo(Short.parseShort("0"));
		otrosIngresos.setAudAccion(62);
		Boolean guardadoOtrosIngresos = ComunicacionServiciosBR.setOtrosIngresosDeclaracion(otrosIngresos);
		if (guardadoOtrosIngresos) {
			mostrarOtrosIngresos();
			RequestContext.getCurrentInstance().execute("$('#dialogEliminarOtrosIngresos').modal('hide');");
			this.otrosIngresos = new OtrosIngresosDeclaracionExt();
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
		}
		if (!guardadoOtrosIngresos) {
			declaracionRenta.setTabIndex((short) 3);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		}
	}	
	

	public void guardarOtrosIngresos() throws SIGEP2SistemaException {
		if (this.otrosIngresos.getValor().longValue() > 9999999999999999L) {
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "El valor máximo permitido es 9999999999999999.");
			return;
		}
		if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
			this.otrosIngresos.setAudCodRol(4);
		else if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.otrosIngresos.setAudCodRol(3);
		else
			this.otrosIngresos.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		this.otrosIngresos.setCodDeclaracion(new BigDecimal(declaracionRenta.getCodDeclaracion().longValue()));
		this.otrosIngresos.setCodDeclaracion(declaracionRenta.getCodDeclaracion());
		if (this.otrosIngresos.getCodOtrosIngresosDeclaracion() != null)
			this.otrosIngresos.setAudAccion(63);
		else { 
			if (this.otrosIngresos.getCodOtrosIngresosDeclaracion() == null)
				this.otrosIngresos.setAudAccion(785);
		}
		this.otrosIngresos.setFlgActivo(Short.parseShort("1"));
		this.otrosIngresos.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		Boolean guardadoOtrosIngresos = ComunicacionServiciosBR.setOtrosIngresosDeclaracion(this.otrosIngresos);
		if (guardadoOtrosIngresos) {
			if (this.otrosIngresos.getCodOtrosIngresosDeclaracion() == null)
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
			else {
				if (this.otrosIngresos.getCodOtrosIngresosDeclaracion() != null) {
					this.setBtnGuardarOtrosIngresos("Guardar Otros Ingresos y rentas no laborales");
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_EDICION_CORRECTA);
				}
			}	
			this.otrosIngresos = new OtrosIngresosDeclaracionExt();
		} 
		else
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MessagesBundleConstants.BR_MSG_GUARDADO_FALLIDO);
		if (!guardadoOtrosIngresos) {
			this.declaracionRenta.setTabIndex((short) 3);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		}
		this.mostrarOtrosIngresos();
		lbMostrarFormulario = false;
	}
	public void limpiarOtrosIngrsos() {
		if(!lbnuevoregistrogrilla) {
			this.mostrarOtrosIngresos();
			lbMostrarFormulario = false;
		}
	}	
	public void cancelarOtrosIngresos() {
		RequestContext.getCurrentInstance().execute("$('#formularioOtrosIngresos').attr('style', 'display:none');");
	}	
	
	/*ACCIONES CUENTAS*/
	public void agregarNuevaCuenta() {
		lbMostrarFormulario = true;
		lbEditarFormulario = true;
		this.cuentaSeleccionada = new CuentasDeclaracion();
		this.nombreBotonGuardarCuenta = TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_BTN_ADICIONAR, this.getLocale());
		lbnuevoregistrogrilla = true;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarNuevo;
	}
	
	public void verCuenta(CuentasDeclaracion cuenta) {
		this.cuentaSeleccionada = cuenta;
		lbMostrarFormulario = true;
		lbEditarFormulario = false;	
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:frmCuentasAhorros:frmRegistroCuenta");		
	}
	public void confirmarEditarCuenta(CuentasDeclaracion cuenta) {
		this.cuentaSeleccionada = cuenta;
		lbnuevoregistrogrilla = false;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;
		lbMostrarFormulario = true;
		lbEditarFormulario = true;				
	}	
	public void actualizarCuenta() {
		this.nombreBotonGuardarCuenta = TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GUARDAR_CAMBIOS, this.getLocale());
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:frmCuentasAhorros:frmRegistroCuenta");
	}	
	public void confirmarEliminadoCuenta(CuentasDeclaracion cuenta) {
		this.cuentaSeleccionada = cuenta;
		lbnuevoregistrogrilla = false;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;
		lbMostrarFormulario = true;
		lbEditarFormulario = true;				
	}	

	public void eliminarCuenta() {
		try {
			this.cuentaSeleccionada.setAudAccion(63);
			this.cuentaSeleccionada.setFlgActivo((short) (0));
			try {
				if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
					this.cuentaSeleccionada.setAudCodRol(4);
				else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
					this.cuentaSeleccionada.setAudCodRol(3);
				else
					this.cuentaSeleccionada.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}		
			this.cuentaSeleccionada.setAudCodUsuario(new BigDecimal(usuarioSesion.getId()));
			if (ComunicacionServiciosBR.setcuentasdeclaracion(cuentaSeleccionada))
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha eliminado exitosamente el registro");
			else
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, tituloEliminaError, textoEliminaError));
			this.cargarCuentas();
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, tituloEliminaError, textoEliminaError + " error: " + ex.getMessage()));
		}
	}
	public void guardarCuenta() {
		try {
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			if (this.cuentaSeleccionada.getCodCuentaDeclaracion() == null) {
				this.cuentaSeleccionada.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
				this.cuentaSeleccionada.setAudAccion(785);
			} 
			else
				this.cuentaSeleccionada.setAudAccion(63);
			this.cuentaSeleccionada.setFlgActivo((short) 1);
			this.cuentaSeleccionada.setAudFechaActualizacion(new Date());
			try {
				if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
					this.cuentaSeleccionada.setAudCodRol(4);
				else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
					this.cuentaSeleccionada.setAudCodRol(3);
				else
					this.cuentaSeleccionada.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}		
			this.cuentaSeleccionada.setAudCodUsuario(new BigDecimal(usuarioSesion.getId()));
			boolean repetido = this.verificarCuentaExistente();
			if(!repetido) {
				if (ComunicacionServiciosBR.setcuentasdeclaracion(this.cuentaSeleccionada)) {
					if (this.cuentaSeleccionada.getCodCuentaDeclaracion() == null)
						this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_GUARDADO_CORRECTO);
					else
						this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha modificado o editado exitosamente el registro");
				} 
				else
					this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloGuardaFallido, textoGuardaFallido);
			}
			else 
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Cuenta Bancaria Repetida");
			this.inicializarCuentas();
			lbMostrarFormulario= false;
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloGuardaFallido, textoGuardaFallido + " error: " + ex.getMessage());
		}
	}	


	
	public void limpiarCuentas() {
		if(!lbnuevoregistrogrilla) {
			this.inicializarCuentas();
			lbMostrarFormulario=false;
		}
	}
	/*FIN ACCIONES CUENTAS*/	
	
	/*ACCIONES ACTIVIDAD ECONOMICA*/
	public void crearActividad() {
		actividadPrivada = new ActividadPrivadaExt();
		lbnuevoregistrogrilla = true;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarNuevo;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:fomularioActividadPrivada:panelFormActividad_content");	
		lbMostrarFormulario=true;
		lbEditarFormulario=true;
	}	
	public void mostrarDialogoEditarActividadPrivada() {
		this.setBtnActividadEconomica(CONST_TEXT_GUARDAR_CAMBIOS);
		lbnuevoregistrogrilla = false;
		lbMostrarFormulario=true;
				lbEditarFormulario=true;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;
		RequestContext.getCurrentInstance().execute("$('#dialogEditarActividadEconomica').modal({backdrop: 'static', keyboard: false});");
		
		
	}
	public void editarActividadPrivada() {

		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:fomularioActividadPrivada:panelFormActividad_content");
	}
	public void mostrarDialogoEliminarActividadPrivada() {
		RequestContext.getCurrentInstance().execute("$('#dialogEliminarActividadEconomica').modal({backdrop: 'static', keyboard: false});");
	}		
	
	/*ACCIONES ACREENCIAS*/
	public void crearAcreencia() {
		this.acreenciaOblicacion = new AcreenciaObligacion();
		lbnuevoregistrogrilla = true;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarNuevo;
		lbMostrarFormulario=true;
		lbEditarFormulario=true;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:formularioAcreenciasObligaciones:panelFormAcreencia_content");
	}	
	public void editarAcreencia() {
		lbMostrarFormulario=true;
		lbEditarFormulario=true;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:formularioAcreenciasObligaciones:panelFormAcreencia_content");
	}
	public void mostrarDialogoEditarAcreenciasObligaciones() {
		this.btnAcreencia = CONST_TEXT_GUARDAR_CAMBIOS;
		lbnuevoregistrogrilla = false;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;
		RequestContext.getCurrentInstance().execute("$('#dialogEditarAcreenciasObligaciones').modal({backdrop: 'static', keyboard: false});");
	}

	public void mostrarDialogoEliminarAcreenciasObligaciones() {
		lbMostrarFormulario=false;
		lbEditarFormulario=false;
		RequestContext.getCurrentInstance().execute("$('#dialogEliminarAcreenciasObligaciones').modal({backdrop: 'static', keyboard: false});");
	}

	public void eliminarAcreenciasObligaciones() {
		this.acreenciaOblicacion.setFlgActivo((short) 0);
		this.acreenciaOblicacion.setAudAccion(62);
		try {
			if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
				this.acreenciaOblicacion.setAudCodRol(4);
			else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
				this.acreenciaOblicacion.setAudCodRol(3);
			else
				this.acreenciaOblicacion.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}			
		Boolean guardadoAcreencias = ComunicacionServiciosBR.setacreenciaobligacion(acreenciaOblicacion);
		if (guardadoAcreencias) {
			this.mostrarAcreenciasObligaciones();
			RequestContext.getCurrentInstance().execute("$('#dialogEliminarAcreenciasObligaciones').modal('hide');");
			this.acreenciaOblicacion = new AcreenciaObligacion();
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PROCESO_ELIMINADO);
		}
		if (!guardadoAcreencias) {
			this.declaracionRenta.setTabIndex((short) 3);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		}
	}
	public void guardarSinAcreencias() {
		declaracionRenta.setFlgAcreenciaObligacion((this.sinAcreencias) ? (short) 1 : (short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.sinAcreencias)
			context.execute("$('#dialogRespuAcreencias').modal('show')");
		else 
			this.estadoControlesTablaAcreencias = false;
		lbMostrarFormulario = false; 
	}	


	/**
	 * Metodo que verifica si el documento ingresado es valido
	 * adicional verifica la edad teniendo en cuenta el documento
	 */
	public void compararDocumento() {
		verificarDocumentoPasaporte();
		verificarEdadDocumento();
	}
	
	/**
	 * Metodo que realiza la validacion en la vista, si es pasaporte permite letras, de lo contrario no
	 */
	public void verificarDocumentoPasaporte() {
		if (parienteSeleccionado.getCodTipoIdentificacion() != null) {
			blnPasaporte = false;
			if (parienteSeleccionado.getCodTipoIdentificacion() == TipoParametro.TIPO_DOCUMENTO_PASAPORTE.getValue()) {
				blnPasaporte = true;
			}
		}
	}




	public String obtenerNombreParametrica(long idParametrica) {
		return ComunicacionServiciosSis.getParametricaporId(new BigDecimal(idParametrica)).getNombreParametro();
	}

	public String obtenerValorParametrica(long idParametrica) {
		try {
			return ComunicacionServiciosSis.getParametricaporId(new BigDecimal(idParametrica)).getValorParametro();
		}
		catch(Exception ex) {
			return "Error";
		}
	}

	public void cancelarDatosDomicilio() {
		String mensge = MessagesBundleConstants.getStringMessagesBundle("MSG_DECLARACION_BTN_CANCEL",getLocale());
		String accionSI = "location.href='../persona/informacionPersonal.xhtml?recursoId=HojaDeVidaSubMenu'";
		this.mostrarMensajeSiNo(FacesMessage.SEVERITY_INFO, "", mensge, accionSI, "", "Si", "No");
	}

	public void agregarNuevoParticipacion() {
		this.formParticipacionHabilitado = true;
		this.estadoCalidadMiembro = estadoCalidadSocio = true;
		this.participacionSeleccionada = new ParticipacionJuntaExt();
		lbnuevoregistrogrilla = true;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarNuevo;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:frmParticipacion:frmRegisJuntas");	
	}

	public void seleccionarParticipacion(ParticipacionJuntaExt parti) {
		this.participacionSeleccionada = parti;
		lbnuevoregistrogrilla = false;
		strToolTipBtnLimpiar = strToolTipBtnLimpiarEditar;
	}

	public void eliminarParticipacion() {
		try {
			this.participacionSeleccionada.setAudAccion(62);
			this.participacionSeleccionada.setFlgActivo((short) (0));
			try {
				if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO))
					this.participacionSeleccionada.setAudCodRol(4);
				else if (this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA))
					this.participacionSeleccionada.setAudCodRol(3);
				else
					this.participacionSeleccionada.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}					
			if (ComunicacionServiciosBR.setparticipacionjunta(participacionSeleccionada))
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_REGISTRO_ELIMINADO_CORRECTAMENTE);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloEliminaError, textoEliminaError);
			this.cargarParticipacion();
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloEliminaError, textoEliminaError + " error: " + ex.getMessage());
		}
	}

	public void actualizarParticipacion() {
		this.formParticipacionHabilitado = true;
		this.alternarMiembroSocio();
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:frmParticipacion:frmRegisJuntas");		
	}

	private void cargarDatos() {
		switch (this.getIndexTab()) {
			case 0:
				this.inicializarDatosDomicilio();
				break;
			case 1:
				this.cargarParientes();
				break;
			case 2:
				this.mostrarIngresosLaborales();
				break;
			case 3:
				this.mostrarOtrosIngresos();
				break;
			case 4:
				this.cargarCuentas();
				break;
			case 5:
				this.cargarBienesPatrimoniales();
				break;
			case 6:
				this.mostrarActividadesPrivadas();
				break;
			case 7:
				this.mostrarAcreenciasObligaciones();
				break;
			default:
		}
		this.cargarParticipacion();
	}

	public String obtenerValorParametrica(String nombre) {
		return ComunicacionServiciosSis.getParametricaIntetos(nombre).getValorParametro();
	}

	private void inicializarRecursosTexto() {
		
		textoGuardaExito = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_GUARDADO_CORRECTO, getLocale());
		tituloGuardaExito = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale());
		tituloGuardaFallido = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale());
		textoGuardaFallido =  MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_MSG_GUARDADO_FALLIDO, getLocale());
		tituloEliminaExito = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale());
		textoEliminaExito = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_MSG_ELIMINACION_EXITOSA, getLocale());
		tituloEliminaError = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale());
		textoEliminaError = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_MSG_ELIMINACION_FALLIDA, getLocale());
		textoGuardaParcialPar = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_GUARDADO_PARCIAL_PARIENTES, getLocale());
		textoGuardaDefini = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_GUARDADO_DEFINITIVO, getLocale());
		textoGuardaParcial = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_GUARDADO_PARCIAL_DATOS, getLocale());
	}

	public void alternarMiembroSocio() {
		try {
			String opc = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(participacionSeleccionada.getCodTipoVinculacion())).getValorParametro().toLowerCase();
			if (opc.replace('_', ' ').equals("miembro de la junta")) {
				estadoCalidadSocio = true;
				estadoCalidadMiembro = false;
			} 
			else if (opc.replace('_', ' ').equals("socio")) {
				estadoCalidadSocio = false;
				estadoCalidadMiembro = true;
			} 
			else
				estadoCalidadMiembro = estadoCalidadSocio = false;

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "Error: " + ex.getMessage()));
		}
	}

	public void configurarElementosFaltantes(Boolean b, int[] elements, String elementos, int indice, String texto) {
		b = true;
		elements[indice] = indice + 1;
		elementos += texto;
	}

	public void verificarGuardadoDefinitivo() {
		try {
			String elementosFaltantes = "";
			int[] tabRedireccion = { 0, 0, 0, 0, 0, 0, 0, 0 };
			Boolean val = false;
			if(this.parientes != null && this.parientes.isEmpty() && this.declaracionRenta.getFlgSinParientesConyugues() == 0) {
				elementosFaltantes += TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_TAP_CONSANGUINIDAD_CONYUGUES, getLocale());
				val = true;
				tabRedireccion[0] = 1;
			}
			if(this.listaIngresosDeclaracion.isEmpty() && this.declaracionRenta.getFlgTengoIngresosLaborales() == 0) {
				elementosFaltantes += TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_TAP_INGRESOS_RENTAS, getLocale()) + ", ";
				val = true;
				tabRedireccion[1] = 2;
			}
			if(this.listaOtrosIngresos.isEmpty() && this.declaracionRenta.getFlgTengoOtrosIngresos() == 0) {
				elementosFaltantes += TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_TAP_OTROS_INGRESOS_RENTAS, getLocale()) + ", ";
				val = true;
				tabRedireccion[2] = 3;
			}
			if (this.cuentas.isEmpty() && this.declaracionRenta.getFlgSinCuentasAhorro() == 0) {
				elementosFaltantes += TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_TAP_CUENTAS, getLocale())+ ", ";
				val = true;
				tabRedireccion[3] = 4;
			}
			if (this.bienes.isEmpty() && this.declaracionRenta.getFlgSinBienesPatrimoniales() == 0) {
				elementosFaltantes += TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_TAP_BIENES_PATRIMONIALES, getLocale())+ ", ";
				val = true;
				tabRedireccion[4] = 5;
			}
			if (this.listaActividadPrivada.isEmpty() && this.declaracionRenta.getFlgActividadEconomicaPrivada() == 0) {
				elementosFaltantes += TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_TAP_ACTIVIDAD_ECONOMICA_PRIV, getLocale())+ ", ";
				val = true;
				tabRedireccion[5] = 6;
			}
			if (this.listaAcreObligaciones.isEmpty() && this.declaracionRenta.getFlgAcreenciaObligacion() == 0) {
				elementosFaltantes += TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_TAP_ACREECNCIAS_OBLIGACIONES, getLocale())+ ", ";
				val = true;
				tabRedireccion[6] = 7;
			}
			if (this.participaciones.isEmpty() && this.declaracionRenta.getFlgSinParticipacionJuntas() == 0) {
				elementosFaltantes += TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_BR_TAP_PARTICIPACION_JUNTAS, getLocale())+ ", ";
				val = true;
				tabRedireccion[7] = 8;
			}
			if (val) {
				String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_GUARDADO_DEFINITIVO_ERROR, getLocale());
				msg = msg.replace("$", this.usuarioSesion.getNombrePersona());
				msg += " " + elementosFaltantes;
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, tituloGuardaFallido, msg);
				for (int i = 0; i < tabRedireccion.length; i++) {
					if (tabRedireccion[i] > 0) {
						int index = (byte) tabRedireccion[i];
						this.configurarIndexTab((byte) (index-1));
						break;
					}
				}
				RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO);
			} 
			else {
				this.construirModeloJasper();
				this.rutaPdf = FuncionExtraBean.mostrarJasperPDF(this.declarationSesion, this.listaDeclaracionParaImprimir, false);
				RequestContext.getCurrentInstance().update("panelDiaVisualizar");
				RequestContext.getCurrentInstance().execute("$('#dlgConfirmDef').modal('show');");				
			}

		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error en public void verificarGuardadoDefinitivo() BienesRentasBean error: " + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}

	}

	public Date getMaxAge() {
		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DAY_OF_MONTH, -2);
		return currentDate.getTime();
	}

	/**
	 * Inicializa el valor del check de Bienes Patrimoniales.
	 * 
	 * @return none
	 * @param none
	 */
	/*private void activarCheckBienPatrimonial() {
		try {
			this.sinBienes = declaracionRenta.getFlgSinBienesPatrimoniales() == 1;
		} catch (Exception e) {
			sinBienes = false;
		}
		bloquarRegistrarBienPatrimonial();
	}*

	/**
	 * No permite registrar nuevos Actividad Económica Privada. Vacía la tabla.
	 * 
	 * @return none
	 * @param none
	 */
	public void bloquarRegistrarBienPatrimonial() {
		if (sinBienes) {
			declaracionRenta.setFlgSinBienesPatrimoniales((short) 1);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		} 
		else {
			declaracionRenta.setFlgSinBienesPatrimoniales((short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		}
	}

	/**
	 * Inicializa el valor del check de Otros Ingresos.
	 * 
	 * @return none
	 * @param none
	 */
	private void activarCheckOtrosIngresos() {
		try {
			this.sinOtrosIngresos = declaracionRenta.getFlgTengoOtrosIngresos() == 1;
		} catch (Exception e) {
			this.sinOtrosIngresos = false;
		}
		this.bloquearRegistrarOtrosIngresos();
	}

	/**
	 * No permite registrar nuevos Ingresos no laborales. Vacía la tabla.
	 * 
	 * @return none
	 * @param none
	 */
	public void bloquearRegistrarOtrosIngresos() {
		if (this.sinOtrosIngresos) {
			for (OtrosIngresosDeclaracionExt otrosIngreso : this.listaOtrosIngresos) {
				otrosIngreso.setFlgActivo((short) 0);
				otrosIngreso.setAudAccion(62);
				ComunicacionServiciosBR.setOtrosIngresosDeclaracion(otrosIngreso);
			}
			this.declaracionRenta.setFlgTengoOtrosIngresos((short) 1);
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			this.mostrarOtrosIngresos();
		} 
		else {
			RequestContext.getCurrentInstance().execute("$('#btnNuevoOtroIngreso').attr('style', 'display:display');");
			this.declaracionRenta.setFlgTengoOtrosIngresos((short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
		}
	}

	/**
	 * Inicializa el valor del check de Actividad Económica Privada.
	 * 
	 * @return none
	 * @param none
	 */
	public void activarCheckActividadPrivada() {
		try {
			if (this.declaracionRenta.getFlgActividadEconomicaPrivada() == 1)
				this.sinActividadPrivada = true;
			else
				this.sinActividadPrivada = false;
		} catch (Exception e) {
			this.sinActividadPrivada = false;
		}
	}
	
	public void activarCheckIngresos() {
		this.sinIngresosRentas = this.declaracionRenta.getFlgTengoIngresosLaborales() != null ? this.declaracionRenta.getFlgTengoIngresosLaborales() == 1 : false;
		this.sinIngresosRentas = this.declaracionRenta.getFlgTengoIngresosLaborales() != null && this.declaracionRenta.getFlgTengoIngresosLaborales() == 1;
	}

	public void configurarRadio() {
		if(this.parienteSeleccionado.getCodTipoParentesco()!=null  &&
			(this.parienteSeleccionado.getCodTipoParentesco() == TipoParametro.CONYUGUE.getValue()||
			this.parienteSeleccionado.getCodTipoParentesco() == TipoParametro.COMPANERO_PERMANENTE.getValue()))
			this.estadoRadioPariente = true;
		else{
			this.estadoRadioPariente = false;
			this.parienteSeleccionado.setFlgSociedadConyugalActiva(null);
		}
		
	}

	private void construirModeloJasper() {
		try {
			this.declarationSesion = FacesContext.getCurrentInstance().getExternalContext();
			this.declaracionParaImprimir = new DeclaracionRentaBeanPrint();
			this.listaDeclaracionParaImprimir = new ArrayList<>();
			PersonaParentescoExt parentesco = new PersonaParentescoExt();
			IngresosDeclaracionExt ingresoLaboral = new IngresosDeclaracionExt();
			BienesPatrimonioExt bienPatrimonio = new BienesPatrimonioExt();
			this.declaracionParaImprimir.setConyugue(new PersonaParentescoExt());
			this.declaracionParaImprimir.getDetalleDeclaracion().setNombreTipoDeclaracion(UtilidadesFaces.eliminarCaracteresEspeciales(ComunicacionServiciosSis.getParametricaporId(new BigDecimal(declaracionRenta.getCodTipoDeclaracion())).getNombreParametro()));
			PersonaExt datos = ComunicacionServiciosHV.getpersonacontacto(usuarioSesion.getCodPersona());
			DatoContactoExt datoExt = ComunicacionServiciosBR.getDatoContacto(this.getUsuarioSesion().getCodPersona(), declaracionRenta.getCodDeclaracion().longValue());
			datos.setTelefonoResidencia(datoExt.getTelefonoResidencia() + " " + datoExt.getNumCelular());
			datos.setDireccionResidencia(getDireccionResidenciaGenerada(datos));
			datos.setNumCelular(datoExt.getNumCelular() + " " + datoExt.getTelefonoResidencia());
			this.declaracionParaImprimir.setDatosPersonales(datos);
			this.declaracionParaImprimir.setDetalleDeclaracion(ComunicacionServiciosBR.getdeclaracionid(declaracionRenta.getCodDeclaracion().longValue()));
			parentesco.setFlgActivo(Short.parseShort("1"));
			parentesco.setCodDeclaracion(declaracionRenta.getCodDeclaracion().longValue());
			parentesco.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
			parentesco.setCodTipoParentesco(TipoParametro.HIJO.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
				this.declaracionParaImprimir.getListaParentesco().add(pariente);
			parentesco.setCodTipoParentesco(TipoParametro.MADRE.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
				this.declaracionParaImprimir.getListaParentesco().add(pariente);
			parentesco.setCodTipoParentesco(TipoParametro.HERMANO.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
				this.declaracionParaImprimir.getListaParentesco().add(pariente);
			parentesco.setCodTipoParentesco(TipoParametro.PADRE.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
				this.declaracionParaImprimir.getListaParentesco().add(pariente);
			parentesco.setCodTipoParentesco(TipoParametro.COMPANERO_PERMANENTE.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
				declaracionParaImprimir.getListaParentesco().add(pariente);				
			parentesco.setCodTipoParentesco(TipoParametro.CONYUGUE.getValue());
			for (PersonaParentescoExt conyugue : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
				this.declaracionParaImprimir.setConyugue(conyugue);
			if(this.declaracionParaImprimir.getConyugue() == null || this.declaracionParaImprimir.getConyugue().getCodPersona() == null) {
				parentesco.setCodTipoParentesco(TipoParametro.COMPANERO_PERMANENTE.getValue());
				for (PersonaParentescoExt conyugue : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
					this.declaracionParaImprimir.setConyugue(conyugue);
			}
			ingresoLaboral.setCodDeclaracion(declaracionRenta.getCodDeclaracion().longValue());
			ingresoLaboral.setFlgActivo(1);
			this.declaracionParaImprimir.setListaIngresosLaborales(ComunicacionServiciosBR.getingresosdeclaracion(ingresoLaboral));
			this.declaracionParaImprimir.setListaOtrosIngresos(ComunicacionServiciosBR.getotrosingresosdeclaracion(declaracionRenta.getCodDeclaracion().longValue(), 1));
			this.declaracionParaImprimir.setListaCuentasDeclaracion(ComunicacionServiciosBR.getcuentasdeclaracion(declaracionRenta.getCodDeclaracion().longValue(), 1));
			BigDecimal totalIngresos = ComunicacionServiciosBR.getSumaIngresos(declaracionRenta.getCodDeclaracion().longValue()).getValor();
			this.declaracionParaImprimir.setTotalIngresoLaboral(totalIngresos == null ? new BigDecimal(0) : totalIngresos);
			BigDecimal totalOtros = ComunicacionServiciosBR.getSumaOtrosIngresos(declaracionRenta.getCodDeclaracion().longValue()).getValor();
			this.declaracionParaImprimir.setTotalOtrosIngresos(totalOtros);
			BigDecimal totalCesantias = new BigDecimal(0);//ComunicacionServiciosBR.getSumaIngresos(declaracionRenta.getCodDeclaracion().longValue(), COD_PARAM_CESANTIAS).getValor();
			this.declaracionParaImprimir.setTotalCesantias(totalCesantias == null ? new BigDecimal(0) : totalCesantias);
			BigDecimal totalRepresentacion = new BigDecimal(0);//ComunicacionServiciosBR.getSumaIngresos(declaracionRenta.getCodDeclaracion().longValue(), COD_PARAM_GASTOS_REPRESENTACION).getValor();
			this.declaracionParaImprimir.setTotalGastosRepresentacion(totalRepresentacion == null ? new BigDecimal(0) : totalRepresentacion);
			BigDecimal sumaIngresos =new BigDecimal(0);// ComunicacionServiciosBR.getSumaIngresos(declaracionRenta.getCodDeclaracion().longValue(), COD_PARAM_ARRIENDOS).getValor();
			this.declaracionParaImprimir.setTotalArriendos(sumaIngresos == null ? new BigDecimal(0) : sumaIngresos);
			BigDecimal totalHonorarios = new BigDecimal(0);//ComunicacionServiciosBR.getSumaIngresos(declaracionRenta.getCodDeclaracion().longValue(), COD_PARAM_HONORARIOS).getValor();
			this.declaracionParaImprimir.setTotalHonorarios(totalHonorarios == null ? new BigDecimal(0) : totalHonorarios );
			this.declaracionParaImprimir.sumarTotales();
			this.declaracionParaImprimir.setListaCuentasDeclaracion(ComunicacionServiciosBR.getcuentasdeclaracion(declaracionRenta.getCodDeclaracion().longValue(), 1));
			this.declaracionParaImprimir.setListaAcreenciasObligaciones(ComunicacionServiciosBR.getacreenciaobligacion(declaracionRenta.getCodDeclaracion().longValue(), 1));
			bienPatrimonio.setFlgActivo((short) 1);
			bienPatrimonio.setCodDeclaracion(new BigDecimal(declaracionRenta.getCodDeclaracion().longValue()));
			this.declaracionParaImprimir.setListaBienesPatrimoniales(ComunicacionServiciosBR.getBienesPatrimonio(bienPatrimonio));
			this.declaracionParaImprimir.setListaActividadesEconomicas(ComunicacionServiciosBR.getactividadprivada(declaracionRenta.getCodDeclaracion().longValue(), 1));
			this.declaracionParaImprimir.setListaParticipacionesJuntas(ComunicacionServiciosBR.getparticipacionjunta(declaracionRenta.getCodDeclaracion().longValue(), 1));
			this.declaracionParaImprimir.setListaParticipacionesSocio(ComunicacionServiciosBR.getparticipacionjunta(declaracionRenta.getCodDeclaracion().longValue(), 1));
			this.listaDeclaracionParaImprimir.add(declaracionParaImprimir);
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error en public void construirModeloJasper() BienesRentasBean" + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	/**Dirección de residencia generada*/
	private String getDireccionResidenciaGenerada(PersonaExt datosPersonales) {
		String direccionGenerada = "";
		if(datosPersonales.getDireccionResidencia() != null) {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(datosPersonales.getDireccionResidencia(), EditarDireccionDTO.class);
				if (direccion != null) {
					direccionGenerada = direccion.getDireccionGenerada();
				}
			} catch (JsonSyntaxException e) {
	
			}
		}
		return direccionGenerada;
	}
	
	public void visualizarEimprimir() {
		this.construirModeloJasper();
		/*Boolean valido = */FuncionExtraBean.verJasperPDF("bienesrentas/FormatoBienesRentas.jasper", declarationSesion, listaDeclaracionParaImprimir, false, "DeclaracionBienesRentas");
	}

	public String mostrarPDF() {
		return rutaPdf;
	}

	public String getBanderaPais() {
		return banderaPais;
	}

	public void setBanderaPais(String banderaPais) {
		this.banderaPais = banderaPais;
	}

	private String banderaPais = "/resources/images/banderas/co.png";

	
	/**
	 * Metodo que cambia la bandera del indicativo telefonico dependiendo del pais
	 */
	public void cambiarBanderaPais() {

		Pais pais = null;
		if (this.domicilio.getCodPaisResidencia() != null) 
			pais = ComunicacionServiciosSis.getpisporid(domicilio.getCodPaisResidencia());
		else {
			pais = ComunicacionServiciosSis.getpisporid(169);
			this.domicilio.setCodPaisResidencia(169);	
		}
		if (!pais.getNombrePais().equalsIgnoreCase("COLOMBIA")) {
			this.domicilio.setCodDepartamentoResidencia(null);
			this.domicilio.setCodMunicipioResidencia(null);
			this.localidadHabilitada = true;
		} 
		else 
			this.localidadHabilitada = false;
		if (pais.getIndicativoPais() != null)
			this.domicilio.setIndicativo(pais.getIndicativoPais());
		if (pais.getBanderaUrl() != null) 
			this.setBanderaPais(pais.getBanderaUrl().toLowerCase());
		else 
			this.setBanderaPais("/resources/images/banderas/default.png");
		
	}

	
	/**
	 * Metodo que agrega el indicativo del departamento cuando se selecciona un departamento de colombia
	 */
	public void agregarIndicativoDepartamento() {
		if(this.domicilio.getCodDepartamentoResidencia()!=null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(domicilio.getCodPaisResidencia());
			Departamento departamento = ComunicacionServiciosSis.getdeptoporid(domicilio.getCodDepartamentoResidencia());
			String indicativo = pais.getIndicativoPais() +  departamento.getIndicativo();
			domicilio.setIndicativo(Integer.parseInt(indicativo));
		}
	}
	
	
	@Override
	public void validateForm(ComponentSystemEvent event) {
		/*
		 * Este metodo se encuentra vacio porque la signatura de este se encuentra en
		 * ingles y por politicas la codificación debe haerse en su mayor parte en
		 * español
		 */
	}

	@Override
	public void init() {
		/*
		 * Este metodo se encuentra vacio porque la signatura de este se encuentra en
		 * ingles y por politicas la codificación debe haerse en su mayor parte en
		 * español
		 */
	}

	@Override
	public String persist() {
		return null;
	}

	@Override
	public void retrieve() {
		/*
		 * Este metodo se encuentra vacio porque la signatura de este se encuentra en
		 * ingles y por politicas la codificación debe haerse en su mayor parte en
		 * español no se elimina para evitar inconvenientes puesto que este es heredado
		 */
	}

	@Override
	public String update() {
		return null;
	}

	@Override
	public void delete() {
		/*
		 * Este metodo se encuentra vacio porque la signatura de este se encuentra en
		 * ingles y por politicas la codificación debe haerse en su mayor parte en
		 * español no se elimina para evitar inconvenientes puesto que este es heredado
		 */
	}

	private String nombreBotonGuardarCuenta;

	public void setNombreBotonGuardarCuenta(String nombreBotonGuardarCuenta) {
		this.nombreBotonGuardarCuenta = nombreBotonGuardarCuenta;
	}

	public String getNombreBotonGuardarCuenta() {
		return nombreBotonGuardarCuenta;
	}

	/**
	 * @return the mensageExt
	 */
	public String getMessageEncabezadoGuadarDefinitivo() {
		String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_GUARDADO_DEFINITIVO_CONFIRMACION, getLocale());
		String msgExt = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_GUARDADO_DEFINITIVO_CONFIRMACION_EXT, getLocale());
		String fechaLimite = this.tipoDeclaracion = (String) this.declarationSesion.getSessionMap().get("fechaLimite");
		messageEncabezadoGuadarDefinitivo = msg;
		if(this.getDeclaracionRenta()!=null && this.getDeclaracionRenta().getCodTipoDeclaracion().equals(COD_TIPO_DECLARACION_PERIODICA)) {
			Calendar cal = Calendar.getInstance();
			Date date;
			try {
				date = formateador.parse(fechaLimite);
				cal.setTime(date);	
				if(validaFechaAfter(date, new Date())){
					Integer month = cal.get(Calendar.MONTH) + 1;
					Integer day = cal.get(Calendar.DAY_OF_MONTH);
					msgExt = msgExt.replace("[dd]", day.toString());
					msgExt = msgExt.replace("[mm]", month.toString());
					messageEncabezadoGuadarDefinitivo = msgExt;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return messageEncabezadoGuadarDefinitivo;
	}
	public void setMessageEncabezadoGuadarDefinitivo(String messageEncabezadoGuadarDefinitivo) {
		this.messageEncabezadoGuadarDefinitivo = messageEncabezadoGuadarDefinitivo;
	}

	public void cancelarDef() {
		this.configurarIndexTab((byte) -1);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		this.declarationSesion.getSessionMap().put("cumpleFlujo", "true");
		try {
			this.declarationSesion.redirect("../bienesrentas/bienesrentas.xhtml");
		} catch (IOException e) {
			BienesRentasBean.logger.log().error("Error en public void cancelarDef() BienesRentasBean" + e.getMessage() + " " + e.getStackTrace(), e);
		}
	}

	public void cancelarExt() {
		this.configurarIndexTab((byte) -1);
		ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
		RequestContext.getCurrentInstance().update(NOMBRE_ELEMENTO_TAB);
	}

	/**
	 * @return the sinAcreencias
	 */
	public boolean isSinAcreencias() {
		return sinAcreencias;
	}

	/**
	 * @param sinAcreencias
	 *            the sinAcreencias to set
	 */
	public void setSinAcreencias(boolean sinAcreencias) {
		this.sinAcreencias = sinAcreencias;
	}

	private boolean localidadHabilitada;

	public boolean isLocalidadHabilitada() {
		return localidadHabilitada;
	}

	public void setLocalidadHabilitada(boolean localidadHabilitada) {
		this.localidadHabilitada = localidadHabilitada;
	}

	/**
	 * Método usado para actualizar la tabla del formulario participación en juntas
	 * después de que se elimina o guarda un registro. e ejecuta al pulsar el botón
	 * eliminar de un registro en la tabla o el botón guardar en el fomrulario de
	 * registro de parientes
	 */
	public void cargarParticipacion() {
		try {
			this.codDeclaracion = this.declaracionRenta.getCodDeclaracion().longValue();
			this.participaciones = ComunicacionServiciosBR.getparticipacionjunta(this.codDeclaracion, 1);
			this.participacionSeleccionada = new ParticipacionJuntaExt();
			this.sinParticipacion = this.declaracionRenta.getFlgSinParticipacionJuntas() == 1;
		} catch (Exception ex) {
			logger.log().error("Error al obtener la lista de participacion para cargar en la vista. public void cargarParticipacion(); " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	/**
	 * @param ningún
	 *            valor
	 * @return ningún valor Método usado para actualizar la tabla del formulario
	 *         parientes en primer grado de consanguinidad y/o conyugues después de
	 *         que se elimina o guarda un registro
	 */
	public void cargarParientes() {
		try {
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			PersonaParentescoExt parienteConfig = new PersonaParentescoExt();
			this.parienteSeleccionado = new PersonaParentescoExt();
			parienteConfig.setCodPersona(new BigDecimal(idPersona));
			parienteConfig.setFlgActivo((short) 1);
			parienteConfig.setCodDeclaracion(this.codDeclaracion);
			this.parientes = ComunicacionServiciosBR.getPersonaParentescoPersona(parienteConfig);
			this.sinParientes = declaracionRenta.getFlgSinParientesConyugues() == 1;
		} catch (Exception ex) {
			logger.log().error("Error al obtener la lista de parientes para cargar en la vista. public void cargarParientes();" + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	/**
	 * @param ningún
	 *            valor
	 * @return ningún valor Método usado para actualizar la tabla del formulario
	 *         cuentas corrientes y de ahorros despu´s de que se elimina o guardar
	 *         un registro
	 */
	public void cargarCuentas() {
		try {
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			this.cuentas = ComunicacionServiciosBR.getcuentasdeclaracion(this.codDeclaracion, 1);
			this.sinCuentas = this.declaracionRenta.getFlgSinCuentasAhorro() == 1;
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error al obtener la lista de cuentas para cargar en la vista. " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	/**
	 * @param ningún
	 *            valor
	 * @return ningún valor Método que carga una lista de tipo <BienesPatrimonioExt>
	 *         con todos los registro existentes en la base de datos con un codigo
	 *         de declaración, este código de declaración es proporcionado por el
	 *         objeto bienConfig de tipo BienesPatrimonioExt, el objeto es
	 *         proporcionado al método getBienesPatrimonio, método estatico de la
	 *         clase ComunicacionServiciosBR.
	 */
	public void cargarBienesPatrimoniales() {
		try {
			this.codDeclaracion = declaracionRenta.getCodDeclaracion().longValue();
			this.bienConfig = new BienesPatrimonioExt();
			this.formBienesHabilitado = false;
			this.patrimonioSeleccionado = new BienesPatrimonioExt();
			this.bienConfig.setFlgActivo((short) 1);
			this.bienConfig.setCodDeclaracion(new BigDecimal(this.codDeclaracion));
			this.bienes = ComunicacionServiciosBR.getBienesPatrimonio(bienConfig);
			this.sinBienes = this.declaracionRenta.getFlgSinBienesPatrimoniales() == 1;
		} catch (Exception ex) {
			logger.log().error("Error al obtener la lista de bienes patrimoniales para cargar en la vista."+ ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	/**
	 * @param ningún
	 *            valor
	 * @return ningún valor Método usado para redireccionar al usuario a la página
	 *         /bienesrentas/informacionPersona.xhtml
	 */
	public void reiniciarDeclaracion() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../bienesrentas/informacionPersona.xhtml");
	}

	public boolean isEstadoControlesTablaCuentas() {
		return estadoControlesTablaCuentas;
	}

	public void setEstadoControlesTablaCuentas(boolean estadoControlesTablaCuentas) {
		this.estadoControlesTablaCuentas = estadoControlesTablaCuentas;
	}

	public void limpiarFormIngresosRenta() {
		boolean sw = true;
		boolean errorProceso = true;
		try {
			if (this.listaIngresosDeclaracion.isEmpty()) 
				sw = false;
			this.ingresosDeclaracion = new IngresosDeclaracionExt();
			this.declaracionRenta.setFlgTengoIngresosLaborales((this.sinIngresosRentas) ? (short) 1 : (short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
			if (!sw)
				return;
			for (IngresosDeclaracionExt ingreso : this.listaIngresosDeclaracion) {
				ingreso.setFlgActivo(0);
				ingreso.setAudCodUsuario(getUsuarioSesion().getId());
				ingreso.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
				ingreso.setAudAccion(62);
				errorProceso = ComunicacionServiciosBR.setingresosdeclaracion(ingreso);
			}
			this.listaIngresosDeclaracion = null;
			if (errorProceso)
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloEliminaExito, textoEliminaExito);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MENSAJE_ERROR);
			this.mostrarIngresosLaborales();
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error al eliminar los registros de la lista. limpiarFormIngresosRenta(); " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	public void limpiarFormCuentas() {
		boolean sw = true;
		boolean errorProceso = true;
		try {
			if (this.cuentas.isEmpty())
				sw = false;
			this.cuentaSeleccionada = new CuentasDeclaracion();
			this.declaracionRenta.setFlgTengoOtrosIngresos((this.sinCuentas) ? (short) 1 : (short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
			this.estadoControlesTablaCuentas = true;
			if (!sw)
				return;
			for (CuentasDeclaracion cuenta : this.cuentas) {
				cuenta.setFlgActivo((short) 0);
				cuenta.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				cuenta.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
				cuenta.setAudAccion(62);
				errorProceso = ComunicacionServiciosBR.setcuentasdeclaracion(cuenta);
			}
			this.cuentas = null;
			if (errorProceso)
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, this.tituloEliminaExito, this.textoEliminaExito);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MENSAJE_ERROR);
			this.cargarCuentas();
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error al eliminar los registros de la lista. public void limpiarFormCuentas() " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	public void limpiarFormBienes() {
		boolean sw = true;
		boolean errorProceso = true;
		try {
			if(this.bienes.isEmpty())
				sw = false;
			this.patrimonioSeleccionado = new BienesPatrimonioExt();
			this.declaracionRenta.setFlgTengoOtrosIngresos((this.sinBienes) ? (short) 1 : (short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
			this.estadoControlesTablaBienes = true;
			if (!sw)
				return;
			for (BienesPatrimonioExt bien : this.bienes) {
				bien.setFlgActivo((short) 0);
				bien.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				bien.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
				bien.setAudAccion(62);
				errorProceso = ComunicacionServiciosBR.setBienesPatrimonio(bien);
			}
			this.bienes = null;
			if (errorProceso)
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloEliminaExito, textoEliminaExito);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MENSAJE_ERROR);
			this.cargarBienesPatrimoniales();
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error al eliminar los registros de la lista. public void limpiarFormBienes(); " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	public void limpiarFormAcreencias() {
		boolean sw = true;
		boolean errorProceso = true;
		try {
			if (this.listaAcreObligaciones.isEmpty()) 
				sw = false;
			this.acreenciaOblicacion = null;
			this.declaracionRenta.setFlgTengoOtrosIngresos((this.sinAcreencias) ? (short) 1 : (short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			this.setEstadoControlesTablaAcreencias(true);
			lbMostrarFormulario = false;
			if (!sw)
				return;
			for(AcreenciaObligacion acreencia : this.listaAcreObligaciones) {
				acreencia.setFlgActivo((short) 0);
				acreencia.setAudCodUsuario(getUsuarioSesion().getId());
				acreencia.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
				acreencia.setAudAccion(62);
				errorProceso = ComunicacionServiciosBR.setacreenciaobligacion(acreencia);
			}
			this.listaAcreObligaciones = null;
			if (errorProceso)
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloEliminaExito, textoEliminaExito);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MENSAJE_ERROR);
			this.mostrarAcreenciasObligaciones();
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error al eliminar los registros de la lista. public void limpiarFormAcreencias() " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	public void limpiarFormParientesOcultar() {
		try {
			lbMostrarFormulario=false;
			this.declaracionRenta.setFlgSinParientesConyugues((this.sinParientes) ? (short) 1 : (short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(this.declaracionRenta);
			if (this.parientes.isEmpty())
				return;
			boolean errorProceso = false;
			for (PersonaParentescoExt persona : this.parientes) {
				persona.setFlgActivo((short) (0));
				persona.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				persona.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
				persona.setAudAccion(62);
				errorProceso = ComunicacionServiciosBR.setPersonaParentescoPersona(persona);
			}
			if (errorProceso)
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloEliminaExito, textoEliminaExito);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, tituloEliminaError, textoEliminaError);
			this.cargarParientes();
			
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error en public void limpiarFormParientesOcultar BienesRentasBean.java" + ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
		}
	}

	public void limpiarFormActividad() {
		boolean sw = true;
		boolean errorProceso = true;
		try {
			if (this.listaActividadPrivada.isEmpty()) 
				sw = false;
			this.actividadPrivada = new ActividadPrivadaExt();
			this.declaracionRenta.setFlgActividadEconomicaPrivada(this.sinActividadPrivada ? (short) 1 : (short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
			this.setEstadoControlesTablaActividad(true);
			if (!sw)
				return;
			for (ActividadPrivadaExt actividad : this.listaActividadPrivada) {
				actividad.setFlgActivo(0);
				actividad.setAudCodUsuario(getUsuarioSesion().getId());
				actividad.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
				actividad.setAudAccion(62);
				errorProceso = ComunicacionServiciosBR.setactividadprivada(actividad);
			}
			this.listaActividadPrivada = null;
			if (errorProceso)
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloEliminaExito, textoEliminaExito);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MENSAJE_ERROR);
			this.mostrarActividadesPrivadas();
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error al eliminar los registros de la lista. public void limpiarFormActividad() " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	public void limpiarFormParticipacion() {
		boolean sw = true;
		boolean errorProceso = true;
		try {
			if (this.participaciones.isEmpty()) 
				sw = false;
			this.participacionSeleccionada = new ParticipacionJuntaExt();
			this.declaracionRenta.setFlgTengoOtrosIngresos((this.sinParticipacion) ? (short) 1 : (short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
			this.setEstadoControlesTablaParticipacion(true);
			if (!sw)
				return;
			for (ParticipacionJuntaExt parti : participaciones) {
				parti.setFlgActivo((short) 0);
				parti.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				parti.setAudCodRol((int) (getUsuarioSesion().getCodRol()));
				parti.setAudAccion(62);
				errorProceso = ComunicacionServiciosBR.setparticipacionjunta(parti);
			}
			this.participaciones = null;
			if (errorProceso)
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, tituloEliminaExito, textoEliminaExito);
			else
				this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", MENSAJE_ERROR);
			this.cargarParticipacion();
		} catch (Exception ex) {
			BienesRentasBean.logger.log().error("Error al eliminar los registros de la lista. public void limpiarFormParticipacion() " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}

	/**
	 * @param ningún
	 *            valor
	 * @return ningún valor
	 * 
	 *         Método que guarda el estado del checkbox no tengo ingresos ni rentas
	 *         laborales cuando se selecciona el checkbox
	 */
	public void guardarVerificacionIngresos() {
		this.declaracionRenta.setFlgTengoIngresosLaborales((this.sinIngresosRentas) ? (short) 1 : (short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.sinIngresosRentas)
			context.execute("$('#dialogRespuIngresos').modal('show')");
	}

	public void guardarValoresVerificacion() {
		this.declaracionRenta.setFlgSinParientesConyugues((this.sinParientes) ? (short) 1 : (short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.sinParientes)
			context.execute(INSTRUCCION_MOSTRAR_POPUP);
		
	}

	public void guardarSinCuentas() {
		this.declaracionRenta.setFlgSinCuentasAhorro((this.sinCuentas) ? (short) 1 : (short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.sinCuentas)
			context.execute("$('#dialogRespuCuentas').modal('show')");
		else {
			this.estadoControlesTablaCuentas = false;
		}
	}

	public void guardarSinBienes() {
		declaracionRenta.setFlgSinBienesPatrimoniales(this.sinBienes ? (short) 1 : (short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.sinBienes)
			context.execute("$('#dialogRespuBienes').modal('show')");
		else {
			this.estadoControlesTablaBienes = false;
			this.formBienesHabilitado = false;
		}
	}

	public void guardarCheckOtrosIngresos() {
		this.declaracionRenta.setFlgTengoOtrosIngresos((this.sinOtrosIngresos) ? (short) 1 : (short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.sinOtrosIngresos)
			context.execute("$('#dialogEliminarTodosOtrosIngresos').modal('show');");
		else 
			this.estadoBtnOtroIn = this.sinOtrosIngresos;
		
	}

	

	public void bloquarRegistrarActividadPrivada() {
		if (sinActividadPrivada) {
			this.declaracionRenta.setFlgActividadEconomicaPrivada((short) 1);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		} 
		else {
			this.declaracionRenta.setFlgActividadEconomicaPrivada((short) 0);
			ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		}
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.sinActividadPrivada)
			context.execute("$('#dialogRespuActividad').modal('show')");
		else {
			this.setEstadoControlesTablaActividad(false);
			this.setFormActividadHabilitado(false);
		}
	}

	public void guardarSinParticipacion() {
		declaracionRenta.setFlgSinParticipacionJuntas((this.sinParticipacion) ? (short) 1 : (short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		RequestContext context = RequestContext.getCurrentInstance();
		if (this.sinParticipacion)
			context.execute("$('#dialogRespuParticipacion').modal('show')");
		else {
			this.estadoControlesTablaParticipacion = false;
			this.formParticipacionHabilitado = false;
		}
	}

	public void cancelarEliminacionTablaCuentas() {
		this.sinCuentas = false;
		declaracionRenta.setFlgSinCuentasAhorro((short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		this.estadoControlesTablaCuentas = false;
	}

	public void cancelarEliminacionTablaBienes() {
		this.sinBienes = false;
		declaracionRenta.setFlgSinBienesPatrimoniales((short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		this.estadoControlesTablaBienes = false;
		this.formBienesHabilitado = false;
	}

	public void cancelarEliminacionTablaAcreencias() {
		this.sinAcreencias = false;
		declaracionRenta.setFlgAcreenciaObligacion((short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		this.estadoControlesTablaAcreencias = false;
	}

	public void cancelarEliminacionTablaIngresos() {
		this.sinIngresosRentas = false;
		declaracionRenta.setFlgTengoIngresosLaborales((short) 0);
		this.sinIngresosRentas = false;
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
	}

	public void cancelarEliminacionTablaActividad() {
		this.sinActividadPrivada = false;
		declaracionRenta.setFlgActividadEconomicaPrivada((short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		this.estadoControlesTablaActividad = false;
		this.formActividadHabilitado = false;
	}

	public void cancelarEliminacionTabla() {
		this.sinParientes = false;
		declaracionRenta.setFlgSinParientesConyugues((short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
	}

	public void cancelarEliminacionTablaParticipacion() {
		this.sinParticipacion = false;
		declaracionRenta.setFlgSinParticipacionJuntas((short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
		this.estadoControlesTablaParticipacion = false;
		this.formParticipacionHabilitado = false;
	}

	public void cancelarEliminacionOtrosIngresos() {
		this.sinOtrosIngresos = false;
		declaracionRenta.setFlgTengoOtrosIngresos((short) 0);
		ComunicacionServiciosBR.setDeclaracionUp(declaracionRenta);
	}
	
	/***
	 * Método para la validación de la edad del tipo de pariente seleccionado
	 * validacion 0 desde lista de tipos parentesco
	 *            1 desde radio button
	 */
	public void verificarEdadPariente(String validacion) {
		this.validarConyugue(validacion);
		if(this.parienteSeleccionado.getCodTipoParentesco() == null || this.parienteSeleccionado.getCodTipoParentesco() == 0 || this.parienteSeleccionado.getCodTipoIdentificacion() == null || this.parienteSeleccionado.getCodTipoIdentificacion() == 0)
			return;
		Persona per = ComunicacionServiciosHV.getPersonaPorId(getUsuarioSesion().getCodPersona());
		if(per == null) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_ERROR_RECUPERANDO_PERSONA);
			BienesRentasBean.logger.error("Información del error: No se puedo recuperar la información de la persona en sesión verificarEdadPariente() BienesRentasBean()", new Object());
			return;
		}
		if(this.parienteSeleccionado.getFechaNacimiento() == null || per.getFechaNacimiento()==null)
			return;
		if(this.parienteSeleccionado.getCodTipoParentesco() == TipoParametro.HIJO.getValue() && per.getFechaNacimiento().compareTo(this.parienteSeleccionado.getFechaNacimiento()) >= 0 ){
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_EDAD_PARIENTE_ERRADA);
			this.parienteSeleccionado.setCodTipoParentesco(null);
			return;
		}
		int diasPar = (int) ((new Date().getTime() - this.parienteSeleccionado.getFechaNacimiento().getTime()) /86400000);
		int diasUsu = (int) ((new Date().getTime() - per.getFechaNacimiento().getTime()) /86400000);
		if(this.parienteSeleccionado.getCodTipoParentesco() == 1196 && (diasUsu / 365) - (diasPar / 365 ) < 11) {
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "La fecha de nacimiento para el tipo de pariente seleccionado no es permitida.");
			this.parienteSeleccionado.setCodTipoParentesco(null);
		}
		
	}
	
	/***
	 * Método para la validación de la verificación de la edad para un tipo de documento seleccionado
	 */
	public void verificarEdadDocumento() {
		this.verificarEdadPariente("0");
		if(this.parienteSeleccionado.getCodTipoIdentificacion() == null || this.parienteSeleccionado.getCodTipoIdentificacion() == 0 || this.parienteSeleccionado.getFechaNacimiento() == null )
			return;
		if((this.parienteSeleccionado.getCodTipoIdentificacion() == 40 || this.parienteSeleccionado.getCodTipoIdentificacion() == 41) && this.verificarDiferenciaDias()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_EDAD_NO_APTA_DOCUMENTO_2);
			this.parienteSeleccionado.setCodTipoIdentificacion(null);
			this.parienteSeleccionado.setFechaNacimiento(null);
			return;
		}
		if((this.parienteSeleccionado.getCodTipoIdentificacion() != 40 && this.parienteSeleccionado.getCodTipoIdentificacion() != 41) && !this.verificarDiferenciaDias()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_EDAD_NO_APTA_DOCUMENTO_1);
			this.parienteSeleccionado.setCodTipoIdentificacion(null);
			this.parienteSeleccionado.setFechaNacimiento(null);
		}
	}
	
	public boolean verificarDiferenciaDias() {
		int dias = (int) ((new Date().getTime() - this.parienteSeleccionado.getFechaNacimiento().getTime()) /86400000);
		return (dias / 365 >= 18);
	}
	
	public void agergarDireccion() {
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String jsonDireccion = gson.toJson(editarDireccion);
		
		this.domicilio.setDireccionResidencia(jsonDireccion);
		this.estadoPanelDireccion = false;
	}

	public EditarDireccionDTO getEditarDireccion() {
		return this.editarDireccion;
	}

	public void setEditarDireccion(EditarDireccionDTO editarDireccion) {
		this.editarDireccion = editarDireccion;
	}
	
	public void generarDireccion() {
		String primerDirecion = "";
		String segundaDirecion = "";
		if (this.editarDireccion.getTipoVia() != null) 
			primerDirecion = primerDirecion + " " + editarDireccion.getTipoVia().getSigla();
		if (this.editarDireccion.getSegundoNumero() != null && !editarDireccion.getSegundoNumero().isEmpty()) 
			segundaDirecion = segundaDirecion + " " + editarDireccion.getSegundoNumero();
		if (this.editarDireccion.getNumero() != null && !editarDireccion.getNumero().isEmpty()) 
			primerDirecion = primerDirecion + " " + editarDireccion.getNumero();
		if (this.editarDireccion.getTercerLetra() != null) 
			segundaDirecion = segundaDirecion + editarDireccion.getTercerLetra().getSigla();
		if (this.editarDireccion.getPrimerLetra() != null) 
			primerDirecion = primerDirecion + editarDireccion.getPrimerLetra().getSigla();
		if (this.editarDireccion.isBis()) 
			primerDirecion = primerDirecion + " BIS";
		if (this.editarDireccion.getSegundaLetra() != null) 
			primerDirecion = primerDirecion + " " + editarDireccion.getSegundaLetra().getSigla();
		if (this.editarDireccion.getOrientacion() != null) 
			primerDirecion = primerDirecion + " " + editarDireccion.getOrientacion().getSigla();
		if (this.editarDireccion.getTercerNumero() != null && !editarDireccion.getTercerNumero().isEmpty()) 
			segundaDirecion = segundaDirecion + " " + editarDireccion.getTercerNumero();
		if (this.editarDireccion.getSegundaOrientacion() != null) 
			segundaDirecion = segundaDirecion + " " + editarDireccion.getSegundaOrientacion().getSigla();
		if (this.editarDireccion.getComplemento() != null && !editarDireccion.getComplemento().isEmpty()) 
			segundaDirecion = segundaDirecion + " " + editarDireccion.getComplemento();
		segundaDirecion = segundaDirecion == "" ? "" : " " + segundaDirecion;
		this.editarDireccion.setDireccionGenerada(primerDirecion + segundaDirecion);
	}
	
	public void mostrarPanelEditarDireccion(boolean value) {
		cargarDireccionPanelEditarDireccion();
		this.estadoPanelDireccion = value;
	}

	public boolean isEstadoPanelDireccion() {
		return estadoPanelDireccion;
	}

	public void setEstadoPanelDireccion(boolean estadoPanelDireccion) {
		this.estadoPanelDireccion = estadoPanelDireccion;
	}
	
	public void cargarDireccionPanelEditarDireccion() {
		if (this.domicilio.getDireccionResidencia() != null && !this.domicilio.getDireccionResidencia().isEmpty()) {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(this.domicilio.getDireccionResidencia(), EditarDireccionDTO.class);
				if (direccion != null) {
					this.editarDireccion = direccion;
				}
			} catch (JsonSyntaxException e) {

			}
		}
	} 
	
	
	public List<SelectItem> obtenerTiposDocumento() {
		List<SelectItem> list = new ArrayList<>();
		List<Parametrica> listP = ComunicacionServiciosHV.getParametrica(23);
		try {
			if (!listP.isEmpty()) {
				for (Parametrica aux : listP) {
					if(aux.getFlgEstado() == 1 && aux.getCodTablaParametrica().intValue() != 396 && aux.getCodTablaParametrica().intValue() != 37 ) 
						list.add(new SelectItem(aux.getCodTablaParametrica(), aux.getNombreParametro().toUpperCase()));
				}
			}
		}catch (NullPointerException e) {
			return new ArrayList<>();
		}
		return list;
	}
	
	private void totalizarIngresosRentasLaborales() {
		BigDecimal totalIngresos	=new BigDecimal(0);
		DecimalFormat formato 		= new DecimalFormat("#,###.00");
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
		for (IngresosDeclaracionExt IngresosL : this.listaIngresosDeclaracion)
			totalIngresos = IngresosL.getValor().add(totalIngresos);
		this.valorTotalIngresos = formato.format(totalIngresos);
		this.valorTotalIngresos .replace(",", "a");
		this.valorTotalIngresos .replace(".", ",");
		this.valorTotalIngresos .replace("a", ".");
		RequestContext.getCurrentInstance().update("tablaIngresosRenta");
	}
	
	public boolean verificarParticipacionDuplicada(){
		for(ParticipacionJuntaExt p : this.participaciones) {
			if(p.getEntidad() != null) {
				if(participacionSeleccionada.getCodTipoVinculacion() == p.getCodTipoVinculacion() && participacionSeleccionada.getCodCalidadMiembro() == p.getCodCalidadMiembro() && participacionSeleccionada.getCodCalidadSocio() == p.getCodCalidadSocio() && participacionSeleccionada.getEntidad().equalsIgnoreCase(p.getEntidad())){
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_REGISTRO_EXISTENTE );
					return true;
				}
			}
			else {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_BR_REGISTRO_EXISTENTE );
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Metodo que valida que el numero de identificacion del pariente a registrar sea unico
	 */
	public boolean validarNumeroIdentificacionUnico() {
		boolean blnExistePariente = false;
		if(parienteSeleccionado.getCodPersonaParentesco() == null 
		   || (parienteSeleccionado.getCodPersonaParentesco() != null && !(parienteSeleccionado.getNumIdentificacion().equals(""))
			   &&  (parienteSeleccionado.getCodTipoIdentificacion() != codTipoIdentificacionAnt 
			   		|| !parienteSeleccionado.getNumIdentificacion().equals(numIdentificacionAnt)))) {
				PersonaParentescoExt parienteConfig = new PersonaParentescoExt();
				parienteConfig.setCodPersona(new BigDecimal(idPersona));
				parienteConfig.setFlgActivo((short) 1);
				parienteConfig.setCodDeclaracion(this.codDeclaracion);
				parienteConfig.setCodTipoIdentificacion(parienteSeleccionado.getCodTipoIdentificacion());
				parienteConfig.setNumIdentificacion(parienteSeleccionado.getNumIdentificacion());
				List<PersonaParentescoExt> resultParientes =  ComunicacionServiciosBR.getPersonaParentescoPersona(parienteConfig);
				if(resultParientes.size() > 0) {
					  this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							   MessagesBundleConstants.MSG_BR_UN_DOC_EXIST);
					  blnExistePariente = true;
				}
		}
		return blnExistePariente;
	}
	
	private void showValidationResult(String val, String msg)
	{
		if(val.equals("0"))
			this.parienteSeleccionado.setCodTipoParentesco(null);
		if(val.equals("1"))
			this.parienteSeleccionado.setFlgSociedadConyugalActiva(null);
		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, msg);
		this.configurarRadio();		
	}
	
	public void validarConyugue(String validacion) 
	{
		int conyuge = TipoParametro.CONYUGUE.getValue();
		int permanente = TipoParametro.COMPANERO_PERMANENTE.getValue();
		int contador = 0;
		PersonaParentescoExt parienteConfig = new PersonaParentescoExt();
		parienteConfig.setCodPersona(new BigDecimal(idPersona));
		parienteConfig.setFlgActivo((short) 1);
		parienteConfig.setCodDeclaracion(this.codDeclaracion);
		this.parientes = ComunicacionServiciosBR.getPersonaParentescoPersona(parienteConfig);
		
		for (PersonaParentescoExt personaParentescoExt  : this.parientes) {
			
			if(parienteSeleccionado.getCodTipoParentesco()==null || personaParentescoExt.getCodTipoParentesco() == null) continue;			

			// se verifica identificacion para poder editar
			if(parienteSeleccionado.getNumIdentificacion() != null && parienteSeleccionado.getNumIdentificacion().equals(personaParentescoExt.getNumIdentificacion())) continue;
			
			if(parienteSeleccionado.getCodTipoParentesco()==conyuge && personaParentescoExt.getCodTipoParentesco() == conyuge)
			{
				showValidationResult(validacion, MessagesBundleConstants.MSG_BR_UN_CONYUGUE);				
				return;	
			}
			
			if(parienteSeleccionado.getCodTipoParentesco() == permanente && personaParentescoExt.getCodTipoParentesco() == permanente  ){
				showValidationResult(validacion, MessagesBundleConstants.MSG_BR_UN_COMPANERO);				
				return;	
		//	if(personaParentescoExt.getFlgSociedadConyugalActiva() == 1) {
//					showValidationResult(validacion, MessagesBundleConstants.MSG_BR_UN_COMPANERO);				
//					return;	
//				}
			}
			
			if((parienteSeleccionado.getCodTipoParentesco()==permanente && personaParentescoExt.getCodTipoParentesco() == conyuge) || (parienteSeleccionado.getCodTipoParentesco()==conyuge && personaParentescoExt.getCodTipoParentesco() == permanente)){
				showValidationResult(validacion, MessagesBundleConstants.MSG_BR_UN_COMPANERO_CONYUGUE);				
				return;	
				
				//				if(personaParentescoExt.getFlgSociedadConyugalActiva() == 1) {
//					showValidationResult(validacion, MessagesBundleConstants.MSG_BR_UN_COMPANERO_CONYUGUE);				
//					return;	
//				}
			}		
		}
		
		this.configurarRadio();
	}
	
	public void tabsvisibles(boolean b) {
		this.habilitadoDatosDomicilio = false;
		this.habilitadoConsanguinidadConjugues = false;
		this.habilitadoTabIngresoLaboral = false;
		this.habilitadoTabOtrosIngresos = false;
		this.habilitadoCuentas = false;
		this.habilitadoBienePatrimoniales = false;
		this.habilitadoTabActividadEconomica = false;
		this.habilitadoTabAcreenciasObligaciones = false;
		this.habilitadoParticipacionJuntas = false;
	}
	
	public void editarBien() {
		this.formBienesHabilitado = true;
		RequestContext.getCurrentInstance().scrollTo("tabViewBienesRentas:formularioBienPatrimonial:panelFormRegistroBien");
	}

	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}

	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	

	

	

	
	public void limpiarBienPatrimonial() {
		//patrimonioSeleccionado
		if(!lbnuevoregistrogrilla) {
			this.inicializarBienesPatrimoniales();
		}
	}
	
	public void limpiarActividadPrivada() {
		if(!lbnuevoregistrogrilla) {
			this.mostrarActividadesPrivadas();
			RequestContext.getCurrentInstance().execute("$('#divFormActividadPrivada').attr('style','display:none');");
		}
	}
	
	public void limpiarAcreenciaObligacion() {
		//acreenciaOblicacion
		if(!lbnuevoregistrogrilla) {
			this.mostrarAcreenciasObligaciones();
			RequestContext.getCurrentInstance().execute("$('#divFormObligaciones').attr('style','display:none');");
		}
	}
	public void limpiarParticipacion() {
		
		if(!lbnuevoregistrogrilla) {
			this.inicializarParticipacion();
			this.formParticipacionHabilitado = false;
		}else {
			participacionSeleccionada.setCodCalidadMiembro(null);
			participacionSeleccionada.setCodTipoVinculacion(null);
		}
	}

	public boolean isLbnuevoregistrogrilla() {
		return lbnuevoregistrogrilla;
	}

	public void setLbnuevoregistrogrilla(boolean lbnuevoregistrogrilla) {
		this.lbnuevoregistrogrilla = lbnuevoregistrogrilla;
	}

	public String getStrToolTipBtnLimpiar() {
		return strToolTipBtnLimpiar;
	}

	public void setStrToolTipBtnLimpiar(String strToolTipBtnLimpiar) {
		this.strToolTipBtnLimpiar = strToolTipBtnLimpiar;
	}
	
	/**
	 * 
	 * @param fechaIni
	 * @param fechaFin
	 * @return true si fechafin > fechaini
	 *         false si fechafin<= fecchaini
	 */
	private boolean validaFechaAfter(Date fechaIni, Date fechaFin) {
		try {
			/* se parsean las fechas para quitar las horas */
			fechaIni = formateador.parse(formateador.format(fechaIni));
			fechaFin = formateador.parse(formateador.format(fechaFin));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (fechaIni == fechaFin) {
			return false;
		} else if (fechaFin.after(fechaIni))
			return true;
		return false;
	}
}
