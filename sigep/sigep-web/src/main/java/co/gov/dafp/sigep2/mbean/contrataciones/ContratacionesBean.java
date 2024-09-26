package co.gov.dafp.sigep2.mbean.contrataciones;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.NotSupportedException;

import org.apache.poi.util.SystemOutLogger;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.primefaces.context.RequestContext;

import com.sun.mail.handlers.message_rfc822;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Contrato;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.ModificacionContrato;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.ContratoExt;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.ModificacionContratoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.RolExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosCO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.enums.TipoDocumentoIdentidadEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Named
@ViewScoped
@ManagedBean

public class ContratacionesBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Variables para la búsqueda
	 */
	private String strCodTipoIdentificacionBuscar;
	private String strNumeroIdentificacionBuscar;
	private String strPrimerNombreBuscar;
	private String strPrimerApellidoBuscar;
	private String strSegundoNombreBuscar;
	private String strSegundoApellidoBuscar;
	private List<PersonaExt> lstBusquedaPersonasExt;
	private List<ContratoExt> lstBuscarContratosPersona;
	private List<ModificacionContratoExt> lstModificacionesContrato;
	private List<ModificacionContratoExt> lstModificacionesSuspensiones;
	private List<ModificacionContratoExt> lstModificacionesContratoReversa;
	/* Variable entidad Contrato */
	private Contrato contrato;
	private ModificacionContrato modificacionContrato;
	/*
	 * Variables para manejo de tabs 0 Principal - Búsqueda 1 Registro
	 * (Modificación) 2 Adición 3 Prorroga 4 Alcance 5 Suspension 6 Terminacion 7
	 * Prorroga, Adición, Alcance 8 Cesión 9 Consulta
	 */
	private int intIndextab;
	private int intIndexOPContinuar;
	private String strMsgContinuar;
	private boolean bltabBuscarActivo;
	/* Variables registro Contrato */
	private String strEntidadUsuario;
	private String strAreaEntidad;
	private PersonaExt personaSelected;

	/* Variables de sesión */
	/*
	 * private List<SelectItem> lisEntida = new ArrayList<SelectItem>(); private
	 * List<EntidadExt> entidades; private boolean lbIsEntidadesTodas;
	 */
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	private EntidadDTO entidadUsuario = new EntidadDTO();
	Entidad entidadContrato;
	/* Roles */
	private boolean lbIsRolAdminFUNC, lbIsRolValidoContratos, lbIsRolValidoConsulta, lbRolValido;

	/* Datos de vinculación */
	private VinculacionExt vinculacionSupervisor;
	private String strNombreSupervisor, strNombreGradoVinculacionSupervisor;

	/* Datos de Cesion */
	private PersonaExt personaCesionContrato;
	private String strNombrePersonaCesion;

	/* Datos de suspension */
	/* True si es suspensión, falsesi es quitar suspensión */
	private boolean lbSuspension = true;
	private String strNombreBotonSuspension;

	/* Variables Tipos Modificacion Cntratos */
	private final String strContratoNuevo = "879";
	private final String strContratoModificacion = "880";
	private final String strContratoAdicion = "881";
	private final String strContratoProrroga = "882";
	private final String strContratoAlcance = "883";
	private final String strContratoSuspension = "884";
	private final String strContratoTerminacion = "885";
	private final String strContratoAdProMod = "886";
	private final String strContratoCesion = "887";
	private final String strContratoQuitarSuspension = "1001";
	private final String strContratoTerminacionManual = "1158";
	private String strTipoModificacion;

	/**/
	private String stsNombreTabContrato = "Contrato";
	private String strNombreBotonContratos;
	private String strNombreBotonContratoVolver;

	private boolean lbContratoDeshabilitaMonto;
	private boolean lbContratoDeshabilitaFecIni;
	private boolean lbContratoDeshabilitaFecFin;
	private boolean lbContratoDeshabilitaJustificacion;
	private AuditoriaSeguridad auditoriaSeguridad;

	/* Mensajes */
	private String strMsgGuardarExitoContrato, strMsgGuardarErrorContrato, strMsgMensajesvalidacionesProrroga,
			strMsgTieneDerechosExclusividad, strMsgModificarContratoExito, strMsgGuardarAdicionExito,
			strMsgGuardarProrrogaExito, strMsgGuardarAlcanceExito, strMsgGuardarSuspensionExito,
			strMsgGuardarTerminacionExito, strMsgGuardarCesionExito, strMsgGuardarTerminacionManualExito,
			strMsgReversaModificacionMsg, strMsgReversaModificacionUnica, strMsgReversaModificacionVarias,
			strLabelFechaFinSuspension, strLabelFechaFinQuitarSuspension, strLabelFechaDefFrmSuspension,
			strMsgBusquedaExitosa, strMsgVigenciaAnterior, strMsgIngresarCriterioBusqueda, strMsgBusquedaNoResultados,
			strMsgNoContratosRegistrados, strMsgUsuarioNoAutorizadoAccion, strMsgContratoNoActivo,
			strMsgEntidadDiferenteUsuEntidad, strMsgNoRegistrarExclusividad, strMensajeRegistrarContrato,
			strMensajeCorregirContrato;
	/* Mensajes de los diálogos */
	private String strMsgAccionesContrato;

	/* Consultar y reversar Contrato */
	private List<ModificacionContratoExt> lstConsultarCoModificaciones;
	private ModificacionContrato modificacionConsultaReversaSelected;
	private boolean lbconsultarReversar;
	private Usuario usuarioConsultaReversa;
	private PersonaExt personaUsuarioConsultaContrato;
	private boolean lbRolValidoReversa = false, lbUltimaModificacion, lbEliminaModPosteriores;

	/* campos de auditoria */
	Integer codAudCodRol, codAudAccionInsert = 3, codAudAccionUpdate = 2, codAudAccionDelete = 1;

	/* Pintar - Ocultar Tabs */
	private boolean lbIsTabBuscar;
	private boolean lbIsTabContrato;
	private boolean lbIsTabAdicion;
	private boolean lbIsTabProrroga;
	private boolean lbIsTabAlcance;
	private boolean lbIsTabSuspension;
	private boolean lbIsTabTerminacion;
	private boolean lbIsTabAdPrAl;
	private boolean lbIsTabCesion;
	private boolean lbIsTabConsultar;

	/**/
	private boolean lbIsmostrarListaContratosPersona;
	private String strMsgContratosRegistradosPersona;
	private Long tiempoMesesAprobacion;
	private String strValidaHV;
	private Long llDiasLimiteProrrogaContrato;

	/* Suspende/levantarContrato */
	private String strLabelFormularioSuspension;
	private String strLabelFormularioAgregarSuspension;
	private String strLabelFormularioQuitarSuspension;
	/* Parametricas */
	Integer liParTipoVincluacionContratista;
	Integer liTipoFuenteFinanciacionNacion;
	Integer liTipoFuenteFinanciacionPropio;
	Integer liAdmonRecursosNoAplica;
	Integer liMonedaPesos, liMonedaDolar, liMonedaEuros, liTipoDocPasaporte;
	private String TIPO_DOC_PASAPORTE = TipoDocumentoIdentidadEnum.PA.getDescripcion();

	/* Area de la entidad = dependencias */
	private List<SelectItem> lstDependenciasEntidadSelect = new ArrayList<SelectItem>();

	/* No editar contratos termiandos */
	private boolean lbIsRolFuncional;

	private Long llMontoActualContratoMod;
	
	private boolean lbEliminarContratoRol;
	

	public ContratacionesBean() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
		lbIsRolAdminFUNC = lbIsRolValidoContratos = lbIsRolValidoConsulta = lbRolValido = false;
		try {
			lbIsRolAdminFUNC = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
			lbIsRolAdminFUNC = false;
		}
		try {
			lbIsRolValidoContratos = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTRATOS,
					RolDTO.OPERADOR_CONTRATOS, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.JEFE_CONTROL_INTERNO);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
			lbIsRolValidoContratos = false;
		}
		try {
			lbIsRolValidoConsulta = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTRATOS,
					RolDTO.OPERADOR_CONTRATOS, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.JEFE_CONTROL_INTERNO);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
			lbIsRolValidoConsulta = false;
		}
		try {
			lbEliminarContratoRol = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTRATOS,
					RolDTO.OPERADOR_CONTRATOS, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.ADMINISTRADOR_FUNCIONAL,
				    RolDTO.SUPER_ADMINISTRADOR);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
			lbIsRolValidoConsulta = false;
		}
		if (lbIsRolValidoContratos || lbIsRolValidoConsulta)
			lbRolValido = true;

		codAudCodRol = (int) usuarioSesion.getCodRol();
		codAudCodRol = (int) this.getRolAuditoria().getId();
		inicializarMensajesProperties();
		/* valido si el rol puede reversar modificaciones */
		lbRolValidoReversa = rolvalidoReversaModificacion();

		if (!lbIsRolValidoContratos && lbIsRolValidoConsulta) {
			this.setStrMsgAccionesContrato(MessagesBundleConstants.getStringMessagesBundle(
					MessagesBundleConstants.DLG_CT_MSG_USUARIO_CONSULTA_CONTRATO, getLocale()));

			RequestContext.getCurrentInstance().execute("PF('dlgAccionesContrato').show();");
		}

	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
	}

	@PostConstruct
	public void init() {
		vinculacionSupervisor = new VinculacionExt();
		intIndextab = 0;
		lbIsmostrarListaContratosPersona = false;
		if (!lbRolValido) {
			mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()),
					"window.location.assign('../index.xhtml?faces-redirect=true')");
			return;
		}
		auditoriaSeguridad = new AuditoriaSeguridad();
		auditoriaSeguridad.setCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		auditoriaSeguridad.setCodUsuarioRol(new BigDecimal(getUsuarioSesion().getCodRol()));
		ComunicacionServiciosHV.setAuditoriaSeguridad(auditoriaSeguridad);
		ComunicacionServiciosSis.setAuditoriaSeguridad(auditoriaSeguridad);
		ComunicacionServiciosVin.setAuditoriaSeguridad(auditoriaSeguridad);
		ocultarTabs(0);
		String recursoId = getRecursoId();
		this.validaPermisosAcciones(recursoId);
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,
				ConfigurationBundleConstants.OPT_VIDEO_CONTRATOS);
	}

	@Override
	public String persist() throws NotSupportedException {
		boolean lbResultadoModificacion;
		Contrato contratoRes = null;
		RequestContext requestContext = RequestContext.getCurrentInstance();
		Date ldate = new Date();
		/* Registro de contrato */
		if (this.strTipoModificacion.equals(this.strContratoNuevo)) {
			if (contrato.getCodContrato() == null)
				contrato.setCodEntidad(Long.valueOf(entidadUsuario.getId()));
			/**
			 * validar datos de supervisor
			 */
			if (vinculacionSupervisor != null) {
				if ((vinculacionSupervisor.getCodTipoIdentificacion() != null
						&& (vinculacionSupervisor.getNumeroIdentificacion() == null
								|| "".equals(vinculacionSupervisor.getNumeroIdentificacion())))
						|| ((vinculacionSupervisor.getNumeroIdentificacion() != null
								&& !"".equals(vinculacionSupervisor.getNumeroIdentificacion()))
								&& vinculacionSupervisor.getCodTipoIdentificacion() == null)) {
					this.strMsgAccionesContrato = MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_CT_MSG_SUPERVISOR_FALTAN_PARAMETROS_BUSCAR, getLocale());
					RequestContext.getCurrentInstance().execute("PF('dlgRegistroContrato').show();");
					return null;
				}
			}
			/**
			 * @pjqr 20180322 CU0301–FA09
			 */
			if (contrato.getFechaInicio().before(contrato.getFechaFirma())) {
				this.strMsgAccionesContrato = MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CT_MSG_FECHA_INI_MENOR_FECHA_FIRMA, getLocale());
				requestContext.execute("PF('dlgRegistroContrato').show();");
				this.intIndextab = 1;
				return null;
			}
			if (!validarContratoEntidad()) {
				String msgEntidad = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_EN_ENTIDAD, getLocale());
				String msgExisteContrato = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_YA_EXISTE_CONTRATO, getLocale());
				this.setStrMsgAccionesContrato(
						msgEntidad + " " + (this.strEntidadUsuario != null ? this.strEntidadUsuario : "") + " "
								+ msgExisteContrato + " " + this.contrato.getNumeroContrato());
				requestContext.execute("PF('dlgRegistroContrato').show();");
				this.intIndextab = 1;
				return null;
			}

			/* Si el contrato es de exclusividad no puede tener más contratos activos */
			if (contrato.getDerechoExclusividad() != null && contrato.getDerechoExclusividad() == 1) {
				if (personaTieneContratosActivos(personaSelected)) {
					this.setStrMsgAccionesContrato(strMsgNoRegistrarExclusividad);
					requestContext.execute("PF('dlgRegistroContrato').show();");
					this.intIndextab = 1;
					return null;
				}
			}

			contrato.setCodPersona(personaSelected.getCodPersona());
			contrato.setAudFechaActualizacion(new Date());
			contrato.setAudCodRol(codAudCodRol);
			contrato.setAudAccion(codAudAccionInsert);
			contrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			if (contrato.getDerechoExclusividad() == null)
				contrato.setDerechoExclusividad((short) 0);
			contrato.setFlgActivo((short) 1);

			if (!validaFechaAfter(ldate, contrato.getFechaFin()))
				contrato.setFlgActivo((short) 0);

			if (vinculacionSupervisor != null && vinculacionSupervisor.getCodPersona() != null)
				contrato.setCodSupervisor(vinculacionSupervisor.getCodPersona());
			contratoRes = ComunicacionServiciosCO.setContrato(contrato);
			if (!contratoRes.isError()) {
				this.setStrMsgAccionesContrato(strMsgGuardarExitoContrato);
				requestContext.execute("PF('dlgRegistroContratoGuardar').show();");
				this.intIndextab = 1;
				return null;
			} else {
				this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato + " - " + contratoRes.toString());
				requestContext.execute("PF('dlgRegistroContratoGuardar').show();");
				this.intIndextab = 1;
				return null;
			}
		} else if (this.strTipoModificacion.equals(this.strContratoModificacion)) {
			if (vinculacionSupervisor != null && vinculacionSupervisor.getCodPersona() != null)
				contrato.setCodSupervisor(vinculacionSupervisor.getCodPersona());
			contrato.setAudFechaActualizacion(new Date());
			contrato.setAudCodRol(codAudCodRol);
			contrato.setAudAccion(codAudAccionUpdate);
			contrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			/**
			 * @pjqr 20180322 CU0301–FA09
			 */
			if (contrato.getFechaInicio().before(contrato.getFechaFirma())) {
				this.strMsgAccionesContrato = MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CT_MSG_FECHA_INI_MENOR_FECHA_FIRMA, getLocale());
				requestContext.execute("PF('dlgRegistroContrato').show();");
				this.intIndextab = 1;
				return null;
			}

			if (!validaFechaAfter(ldate, contrato.getFechaFin()))
				contrato.setFlgActivo((short) 0);
			contratoRes = ComunicacionServiciosCO.setContrato(contrato);
			if (!contratoRes.isError()) {
				this.setStrMsgAccionesContrato(strMsgModificarContratoExito);
				requestContext.execute("PF('dlgRegistroContratoGuardar').show();");
				this.intIndextab = 1;
				return null;
			} else {
				FacesContext.getCurrentInstance().addMessage("idMsgConsultaPersonas",
						new FacesMessage(strMsgGuardarErrorContrato + " - " + contratoRes.toString()));
				return null;
			}

		} else if (this.strTipoModificacion.equals(this.strContratoAdicion)) {
						
			if(validarValorAdicional()) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATO_VALOR_ADICIONAR, getLocale()));
				requestContext.execute("PF('dlgAdContrato_02').show();");
				return null;		
			}		
			
			/*
			 * Validar tamaño Justificacion
			 */
			if (modificacionContrato != null && modificacionContrato.getJustificacionModificacion() != null) {
				try {
					if (!validaTammanoJustificacionII(modificacionContrato.getJustificacionModificacion().toString())) {
						this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.DLG_CT_MSG_TAM_MAX_JUSTIFICACION_NO_PERMITIDO, getLocale()));
						requestContext.execute("PF('dlgAdContrato_02').show();");
						return null;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			/*
			 * CU0303–FA01:2
			 */
			if (modificacionContrato.getValorAdicion()!=null && validarValorAdicional()) {
				requestContext.execute("PF('dlgAdContrato_01').show();");
				this.intIndextab = 2;
			} else {
				obtenervalorActualContrato();
				modificacionContrato.setCodTipoModificacionContrato(Integer.valueOf(this.strContratoAdicion));
				modificacionContrato.setCodContrato(contrato.getCodContrato());
				modificacionContrato.setAudFechaActualizacion(new Date());
				modificacionContrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
				modificacionContrato.setAudCodRol(codAudCodRol);
				modificacionContrato.setAudAccion(codAudAccionInsert);
				modificacionContrato.setFlgActivo((short) 1);
				modificacionContrato.setNuevoMontoContrato(
						modificacionContrato.getValorAdicion().add(BigDecimal.valueOf(llMontoActualContratoMod)));
				lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(modificacionContrato);
				if (lbResultadoModificacion) {
					this.setStrMsgAccionesContrato(strMsgGuardarAdicionExito);
					requestContext.execute("PF('dlgRegistroAdicionGuardar').show();");
					this.intIndextab = 1;
					return null;
				} else {
					this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato);
					requestContext.execute("PF('dlgRegistroAdicionGuardar').show();");
					this.intIndextab = 1;
					return null;
				}
			}
		} else if (this.strTipoModificacion.equals(this.strContratoProrroga)) {
			/* PRORROGA */
			
			
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(5940));
			if(parametrica != null) {
				int diasProrroga = Integer.parseInt(parametrica.getValorParametro());
				Date fechaFin = this.contrato.getFechaFin();
				Calendar calendar = Calendar.getInstance();				
				calendar.setTime(fechaFin); // Configuramos la fecha que se recibe				 
				calendar.add(Calendar.DAY_OF_YEAR, diasProrroga);  // numero de días a añadir, o restar en caso de días<0				 
				Date fechaSumada = calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos				
				Date fechFinProrroga = modificacionContrato.getFechaFinModificacion();
				if(fechFinProrroga.after(fechaSumada)  ) {
					this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_DIAS_PRORROGA_FINALIZAR, getLocale()));
					requestContext.execute("PF('dlgPrContrato_01').show();");
					return null;
				}				
			}
			
			
			try {
				if (!validaTammanoJustificacionII(modificacionContrato.getJustificacionModificacion().toString())) {
					this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_CT_MSG_TAM_MAX_JUSTIFICACION_NO_PERMITIDO, getLocale()));
					requestContext.execute("PF('dlgPrContrato_01').show();");
					return null;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			/* validar fechas prorroga mayores a fecha de contrato */

			/**
			 * @pjqr 20180322 CU0304–FA01:3
			 */
			if (modificacionContrato.getFechaInicioModificacion().before(this.contrato.getFechaFin())
					|| modificacionContrato.getFechaFinModificacion().before(this.contrato.getFechaFin())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_PRORROGA_MENOR_INICIAL, getLocale()));
				requestContext.execute("PF('dlgPrContrato_01').show();");
				this.intIndextab = 3;
			}
			/**
			 * @pjqr 20180322 CU0304–FA01:4
			 */
			if (!validarTiempoProrroga(modificacionContrato)) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_PRORROGA_MENOR_ESTIPULADO, getLocale()));
				requestContext.execute("PF('dlgPrContrato_01').show();");
				this.intIndextab = 3;
				return null;
			}
			/**
			 * @pjqr 20180322 CU0304–FA01:5
			 */
			Date dateprorroga = obtenerMaximaFechaProrroga((ContratoExt) this.contrato);
			if (dateprorroga != null && modificacionContrato.getFechaInicioModificacion().before(dateprorroga)
					|| modificacionContrato.getFechaInicioModificacion().before(this.contrato.getFechaFin())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_INCICIO_ANTERIOR_FIN, getLocale()));
				requestContext.execute("PF('dlgPrContrato_01').show();");
				this.intIndextab = 3;
				return null;
			}

			/**
			 * @pjqr 20180322 CU0304–FA01:7
			 */

			if (modificacionContrato.getFechaFinModificacion()
					.before(modificacionContrato.getFechaInicioModificacion())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_FINAL_ANTERIOR_INICIAL, getLocale()));
				requestContext.execute("PF('dlgPrContrato_01').show();");
				this.intIndextab = 3;
				return null;
			}

			/**
			 * Issue 2298
			 */
			if (!validaFechaAfter(modificacionContrato.getFechaAutorizacionModificacion(), contrato.getFechaFin())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_AUTORIZACION_CONTRATO_FIN, getLocale()));
				requestContext.execute("PF('dlgPrContrato_01').show();");
				this.intIndextab = 3;
				return null;
			}

			modificacionContrato.setCodTipoModificacionContrato(Integer.valueOf(this.strContratoProrroga));
			modificacionContrato.setCodContrato(contrato.getCodContrato());
			modificacionContrato.setAudFechaActualizacion(new Date());
			modificacionContrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			modificacionContrato.setAudCodRol(codAudCodRol);
			modificacionContrato.setAudAccion(codAudAccionInsert);
			modificacionContrato.setFlgActivo((short) 1);
			modificacionContrato.setFechaFinRegistroContrato(contrato.getFechaFin());
			lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(modificacionContrato);
			/**
			 * @pjqr 20180322 CU0304–FA02:2
			 */

			if (lbResultadoModificacion) {
				contrato.setFlgActivo((short) 1);
				contrato.setAudFechaActualizacion(new Date());
				ComunicacionServiciosCO.setContrato(contrato);
				this.setStrMsgAccionesContrato(strMsgGuardarProrrogaExito);
				requestContext.execute("PF('dlgRegistroProrrogaGuardar').show();");
				this.intIndextab = 1;
				return null;
			} else {
				this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato);
				requestContext.execute("PF('dlgRegistroProrrogaGuardar').show();");
				this.intIndextab = 1;
				return null;
			}
		} else if (this.strTipoModificacion.equals(this.strContratoAlcance)) {
			/**
			 * @pjqr 20180322 CU0305–FA01:2
			 */
			if (modificacionContrato.getFechaAutorizacionModificacion().before(contrato.getFechaInicio())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CT_MSG_FECHA_AUT_MENOR_FECHA_INI, getLocale()));
				requestContext.execute("PF('dlgAlcanceContrato_01').show();");
				this.intIndextab = 4;
				return null;
			}
			/*
			 * Validar tamaño Justificacion
			 */
			if (modificacionContrato != null && modificacionContrato.getJustificacionModificacion() != null) {
				try {
					if (!validaTammanoJustificacionII(modificacionContrato.getJustificacionModificacion().toString())) {
						this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.DLG_CT_MSG_TAM_MAX_JUSTIFICACION_NO_PERMITIDO, getLocale()));
						requestContext.execute("PF('dlgAlcanceContrato_01').show();");
						return null;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			modificacionContrato.setCodTipoModificacionContrato(Integer.valueOf(this.strContratoAlcance));
			modificacionContrato.setCodContrato(contrato.getCodContrato());
			modificacionContrato.setAudFechaActualizacion(new Date());
			modificacionContrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			modificacionContrato.setAudCodRol(codAudCodRol);
			modificacionContrato.setAudAccion(codAudAccionInsert);
			modificacionContrato.setFlgActivo((short) 1);
			lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(modificacionContrato);
			if (lbResultadoModificacion) {
				this.setStrMsgAccionesContrato(strMsgGuardarAlcanceExito);
				requestContext.execute("PF('dlgRegistroAlcanceGuardar').show();");
				this.intIndextab = 1;
				return null;
			} else {
				this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato);
				requestContext.execute("PF('dlgRegistroAlcanceGuardar').show();");
				this.intIndextab = 1;
				return null;
			}

		} else if (this.strTipoModificacion.equals(this.strContratoSuspension)
				|| this.strTipoModificacion.equals(this.strContratoQuitarSuspension)) {
			ModificacionContratoExt ultimaSuspension = null;
			List<ModificacionContratoExt> lstModificacionesSuspensiones = ComunicacionServiciosCO
					.getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoSuspension),
							contrato.getCodContrato(), 1);
			if (lstModificacionesSuspensiones != null && lstModificacionesSuspensiones.size() > 0)
				ultimaSuspension = lstModificacionesSuspensiones.get(0);

			/**
			 * @pjqr 20180322 CU0306–FA01:2
			 */
			if (this.strTipoModificacion.equals(this.strContratoSuspension)) {
				if (modificacionContrato.getFechaInicioModificacion()
						.after(modificacionContrato.getFechaFinModificacion())) {
					this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_CT_MSG_FECHA_FIN_SUSP_MENOR_FECHA_FIN_SUSP, getLocale()));
					requestContext.execute("PF('dlgSuspensionContrato_01').show();");
					this.intIndextab = 5;
					return null;
				}
				/**
				 * @pjqr 20180322 CU0306–FA01:3
				 */
				if (modificacionContrato.getFechaInicioModificacion().before(this.contrato.getFechaInicio())
						|| modificacionContrato.getFechaFinModificacion().before(this.contrato.getFechaInicio())) {
					this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_CT_MSG_FECHA_SUSP_MENOR_FECHA_INI_CONTRATO, getLocale()));
					requestContext.execute("PF('dlgSuspensionContrato_01').show();");
					this.intIndextab = 5;
					return null;
				}

				/**
				 * @pjqr 20181010 Se valida la fecha de prorroga
				 */
				Date dtMaxProrroga = obtenerMaximaFechaProrroga((ContratoExt) this.contrato);
				if (dtMaxProrroga != null && modificacionContrato.getFechaInicioModificacion() != null
						&& (modificacionContrato.getFechaInicioModificacion().after(dtMaxProrroga))
						&& contrato.getFlgActivo().equals(0)) {
					this.setStrMsgMensajesvalidacionesProrroga(strMsgVigenciaAnterior);
					requestContext.execute("PF('dlgSuspensionContrato_01').show();");
					this.intIndextab = 5;
					return null;

				}

			}
			modificacionContrato.setCodTipoModificacionContrato(Integer.valueOf(this.strTipoModificacion));
			modificacionContrato.setCodContrato(contrato.getCodContrato());
			modificacionContrato.setAudFechaActualizacion(new Date());
			modificacionContrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			modificacionContrato.setAudCodRol(codAudCodRol);
			modificacionContrato.setAudAccion(codAudAccionInsert);
			modificacionContrato.setFlgActivo((short) 1);
			modificacionContrato.setFechaFinRegistroContrato(contrato.getFechaFin());
			lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(modificacionContrato);

			if (this.strTipoModificacion.equals(this.strContratoSuspension)) {
				/*
				 * el contrato se inactiva si la fecha final de modificacion de suspension es
				 * mayor a la fecha de contrato
				 */
				if (!validaFechaAfter(modificacionContrato.getFechaFinModificacion(), contrato.getFechaFin()))
					contrato.setFlgActivo((short) 0);
			} else if (this.strTipoModificacion.equals(this.strContratoQuitarSuspension)) {
				contrato.setFlgActivo((short) 1);
			}
			if (this.strTipoModificacion.equals(this.strContratoQuitarSuspension)) {
				Date nuevaFecha;
				Integer difDias = null;
				Calendar calendar;
				calendar = Calendar.getInstance();
				if (ultimaSuspension != null) {
					if (!validaFechaAfter(contrato.getFechaFin(), ultimaSuspension.getFechaFinModificacion())) {
						difDias = obtenerDiferenciaDiasFechas(ultimaSuspension.getFechaInicioModificacion(),
								ultimaSuspension.getFechaFinModificacion());
						calendar.setTime(ultimaSuspension.getFechaFinModificacion());
					} else {
						if (ultimaSuspension.getFechaInicioModificacion().before(contrato.getFechaFin())) {
							difDias = obtenerDiferenciaDiasFechas(ultimaSuspension.getFechaInicioModificacion(),
									contrato.getFechaFin());
							calendar.setTime(ultimaSuspension.getFechaFinModificacion());
						} else {
							difDias = obtenerDiferenciaDiasFechas(contrato.getFechaFin(),
									ultimaSuspension.getFechaInicioModificacion());
							calendar.setTime(ultimaSuspension.getFechaFinModificacion());
						}

					}
					calendar.add(Calendar.DAY_OF_YEAR, difDias);
					nuevaFecha = calendar.getTime();
				} else {
					nuevaFecha = modificacionContrato.getFechaFinModificacion();
				}

				contrato.setFechaFin(nuevaFecha);
			}
			contratoRes = ComunicacionServiciosCO.setContrato(contrato);
			if (lbResultadoModificacion && !contratoRes.isError()) {
				this.setStrMsgAccionesContrato(strMsgGuardarSuspensionExito);
				requestContext.execute("PF('dlgRegistroSuspensionGuardar').show();");
				this.intIndextab = 1;
				return null;
			} else {
				this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato + " - " + contratoRes.toString());
				requestContext.execute("PF('dlgRegistroSuspensionGuardar').show();");
				this.intIndextab = 1;
				return null;
			}
		} else if (this.strTipoModificacion.equals(this.strContratoTerminacion)) {
			/**
			 * @pjqr 20180322 CU0307–FA01:2
			 */
			if (modificacionContrato.getFechaInicioModificacion().before(contrato.getFechaInicio())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_ANTICIPADA_MENOR_INICIAL, getLocale()));
				requestContext.execute("PF('dlgTerminacionContrato_01').show();");
				this.intIndextab = 6;
				return null;
			}
			/**
			 * @pjqr 20180322 CU0307–FA01:3
			 */
			if (modificacionContrato.getFechaInicioModificacion().after(contrato.getFechaFin())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_ANTICIPADA_MAYOR_FINAL, getLocale()));
				requestContext.execute("PF('dlgTerminacionContrato_01').show();");
				this.intIndextab = 6;
				return null;
			}
			modificacionContrato.setCodTipoModificacionContrato(Integer.valueOf(this.strContratoTerminacion));
			modificacionContrato.setCodContrato(contrato.getCodContrato());
			modificacionContrato.setAudFechaActualizacion(new Date());
			modificacionContrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			modificacionContrato.setAudCodRol(codAudCodRol);
			modificacionContrato.setAudAccion(codAudAccionInsert);
			modificacionContrato.setFlgActivo((short) 1);
			modificacionContrato.setFechaFinRegistroContrato(contrato.getFechaFin());
			lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(modificacionContrato);
			if (!validaFechaAfter(ldate, modificacionContrato.getFechaInicioModificacion()))
				contrato.setFlgActivo((short) 0);
			contrato.setFechaFin(modificacionContrato.getFechaInicioModificacion());
			contrato.setAudAccion(codAudAccionUpdate);
			contrato.setAudCodRol(codAudCodRol);
			contrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			contrato.setAudFechaActualizacion(new Date());
			contratoRes = ComunicacionServiciosCO.setContrato(contrato);
			if (lbResultadoModificacion && !contratoRes.isError()) {
				this.setStrMsgAccionesContrato(strMsgGuardarTerminacionExito);
				requestContext.execute("PF('dlgRegistroTerminacionGuardar').show();");
				this.intIndextab = 1;
				return null;
			} else {
				this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato + " - " + contratoRes.toString());
				requestContext.execute("PF('dlgRegistroTerminacionGuardar').show();");
				this.intIndextab = 1;
				return null;
			}
		} else if (this.strTipoModificacion.equals(this.strContratoAdProMod)) {
						
			Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(5940));
			if(parametrica != null) {
				int diasProrroga = Integer.parseInt(parametrica.getValorParametro());
				Date fechaFin = this.contrato.getFechaFin();
				Calendar calendar = Calendar.getInstance();				
				calendar.setTime(fechaFin); // Configuramos la fecha que se recibe				 
				calendar.add(Calendar.DAY_OF_YEAR, diasProrroga);  // numero de días a añadir, o restar en caso de días<0				 
				Date fechaSumada = calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos				
				Date fechFinProrroga = modificacionContrato.getFechaFinModificacion();
				if(fechFinProrroga.after(fechaSumada)  ) {
					this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_DIAS_PRORROGA_FINALIZAR, getLocale()));
					requestContext.execute("PF('dlgPrAdAlContrato_01').show();");
					return null;					
				}				
			}
			
			if(validarValorAdicional()) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATO_VALOR_ADICIONAR, getLocale()));
				requestContext.execute("PF('dlgPrAdAlContrato_01').show();");
				return null;		
			}		
			
			/*
			 * Validar tamaño Justificacion
			 */
			if (modificacionContrato != null && modificacionContrato.getJustificacionModificacion() != null) {
				try {
					if (!validaTammanoJustificacionII(modificacionContrato.getJustificacionModificacion().toString())) {
						this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.DLG_CONTRATOS_TAMANIO_MAXIMO, getLocale()));
						requestContext.execute("PF('dlgPrAdAlContrato_01').show();");
						return null;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			/**
			 * @pjqr 20180322 CU0308–FA01:2
			 */
			if (modificacionContrato != null && modificacionContrato.getValorAdicion() != null
					&& modificacionContrato.getValorAdicion().longValue() > contrato.getMonto().longValue() / 2) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.DLG_CONTRATOS_VALOR_EDICION, getLocale()));
				requestContext.execute("PF('dlgPrAdAlContrato_01').show();");
				this.intIndextab = 7;
				return null;
			}
			if (modificacionContrato.getFechaFinModificacion() != null) {
				modificacionContrato.setFechaInicioModificacion(obtenerMaximaFechaProrroga());
			}
			/**
			 * @pjqr 20180322 CU0308–FA01:3
			 */
			if (modificacionContrato != null && modificacionContrato.getFechaFinModificacion() != null
					&& modificacionContrato.getFechaInicioModificacion() != null && modificacionContrato
							.getFechaFinModificacion().before(modificacionContrato.getFechaInicioModificacion())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHAS_PRORROGA_ERRONEA, getLocale()));
				requestContext.execute("PF('dlgPrAdAlContrato_01').show();");
				this.intIndextab = 7;
				return null;
			}
			/**
			 * @pjqr 20180322 CU0308–FA01:4
			 */
			if (modificacionContrato != null && modificacionContrato.getFechaInicioModificacion() != null
					&& modificacionContrato.getFechaInicioModificacion().before(contrato.getFechaInicio())
					|| (modificacionContrato != null && modificacionContrato.getFechaFinModificacion() != null
							&& modificacionContrato.getFechaFinModificacion().before(contrato.getFechaInicio()))) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_PRORROGA_MENOR_INICIAL, getLocale()));
				requestContext.execute("PF('dlgPrAdAlContrato_01').show();");
				this.intIndextab = 7;
				return null;
			}
			/**
			 * @pjqr 20180322 CU0308–FA01:5
			 */
			if (!validarTiempoProrroga(modificacionContrato)) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_PRORROGA_MENOR_ESTIPULADO, getLocale()));
				requestContext.execute("PF('dlgPrAdAlContrato_01').show();");
				this.intIndextab = 7;
				return null;
			}
			/**
			 * @pjqr 20180322 CU0308–FA01:6
			 */
			if (modificacionContrato.getFechaAutorizacionModificacion().before(contrato.getFechaInicio())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_AUTORIZACION, getLocale()));
				requestContext.execute("PF('dlgPrAdAlContrato_01').show();");
				this.intIndextab = 7;
				return null;
			}
			obtenervalorActualContrato();
			modificacionContrato.setCodTipoModificacionContrato(Integer.valueOf(this.strContratoAdProMod));
			modificacionContrato.setCodContrato(contrato.getCodContrato());
			modificacionContrato.setAudFechaActualizacion(new Date());
			modificacionContrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			modificacionContrato.setAudCodRol(codAudCodRol);
			modificacionContrato.setAudAccion(codAudAccionInsert);
			modificacionContrato.setFlgActivo((short) 1);
			if (modificacionContrato.getValorAdicion() != null)
				modificacionContrato.setNuevoMontoContrato(
						modificacionContrato.getValorAdicion().add(BigDecimal.valueOf(llMontoActualContratoMod)));
			lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(modificacionContrato);
			/**
			 * @pjqr 20180322 CU0308–FA02:2
			 */
			if (lbResultadoModificacion) {
				if (modificacionContrato.getFechaFinModificacion() != null) {
					contrato.setFlgActivo((short) 1);
					contrato.setFechaFin(modificacionContrato.getFechaFinModificacion());
					ComunicacionServiciosCO.setContrato(contrato);
				}
				this.setStrMsgAccionesContrato(strMsgGuardarExitoContrato);
				requestContext.execute("PF('dlgRegistroAdPrAlGuardar').show();");
				this.intIndextab = 1;
				return null;
			} else {
				this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato);
				requestContext.execute("PF('dlgRegistroAdPrAlGuardar').show();");
				this.intIndextab = 1;
				return null;
			}
		} else if (this.strTipoModificacion.equals(this.strContratoCesion)) {
			/* Validar tamaño justificacion */
			try {
				if (!validaTammanoJustificacionII(modificacionContrato.getJustificacionModificacion().toString())) {
					this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_CONTRATOS_TAMANIO_MAXIMO, getLocale()));
					requestContext.execute("PF('dlgCesionContrato_01').show();");
					return null;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			/**
			 * @pjqr 20180322 CU0309–FA01:2
			 */
			if (modificacionContrato.getFechaInicioModificacion().before(contrato.getFechaInicio())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_CESION_MENOR_INCICIAL, getLocale()));
				requestContext.execute("PF('dlgCesionContrato_01').show();");
				this.intIndextab = 8;
				return null;
			}
			/**
			 * @pjqr 20180322 CU0309–FA01:3
			 */
			if (modificacionContrato.getFechaInicioModificacion().after(contrato.getFechaFin())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_FECHA_SESION_MAYOR_FINAL, getLocale()));
				requestContext.execute("PF('dlgCesionContrato_01').show();");
				this.intIndextab = 8;
				return null;
			}
			/**
			 * @pjqr 20180322 CU0309–FA01:4
			 */
			boolean lbexiteDerExc = false;
			List<ContratoExt> lstContratos = ComunicacionServiciosCO
					.getContratoPersona(personaCesionContrato.getCodPersona().longValue());
			for (ContratoExt cont : lstContratos) {
				if (cont.getDerechoExclusividad() == 1) {
					lbexiteDerExc = true;
					break;
				}
			}
			if (lbexiteDerExc) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_PERSONA_CON_CLAUSULA, getLocale()));
				requestContext.execute("PF('dlgCesionContrato_01').show();");
				this.intIndextab = 8;
				return null;
			}
			modificacionContrato.setCodTipoModificacionContrato(Integer.valueOf(this.strContratoCesion));
			modificacionContrato.setCodContrato(contrato.getCodContrato());
			modificacionContrato.setAudFechaActualizacion(new Date());
			modificacionContrato.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			modificacionContrato.setAudCodRol(codAudCodRol);
			modificacionContrato.setAudAccion(codAudAccionInsert);
			modificacionContrato.setFlgActivo((short) 1);
			if (contrato.getMonto() != null)
				modificacionContrato.setMontoRegistroContrato(contrato.getMonto().intValue());
			if (personaCesionContrato != null && personaCesionContrato.getCodPersona() != null)
				modificacionContrato.setCodNuevoContratista(personaCesionContrato.getCodPersona().longValue());
			if (contrato != null && contrato.getCodPersona() != null)
				modificacionContrato.setCodContratistaInicial(contrato.getCodPersona().longValue());
			lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(modificacionContrato);
			
			
			Short tmpFlgActivo = contrato.getFlgActivo();
			Date tmpFechaFin = contrato.getFechaFin();
			
			Date fechInicioCesion = modificacionContrato.getFechaInicioModificacion();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechInicioCesion);
			calendar.add(Calendar.DATE, -1); 
			contrato.setFlgActivo((short) 0); //0 estado finalizado
			contrato.setFechaFin(calendar.getTime());  //fecha fin = fecha inicio cesion - un dia			
			/**
			 * CU0309–FA02:2
			 */
			if (lbResultadoModificacion) {
				//usuario 1 //actualiza fecha fin y estado a finalizado	
				obtenerDuracionContratos(this.contrato.getFechaInicio(), this.contrato.getFechaFin());
				Contrato contratos = ComunicacionServiciosCO.setContrato(contrato); //ACTUALIZA				
				contrato.setCodContrato(null);
				contrato.setFechaFin(tmpFechaFin);				
				contrato.setFlgActivo(tmpFlgActivo);
				if (modificacionContrato.getNuevoMontoContrato() != null)
					contrato.setMonto(modificacionContrato.getNuevoMontoContrato());
				if (personaCesionContrato != null && personaCesionContrato.getCodPersona() != null)
					contrato.setCodPersona(BigDecimal.valueOf(personaCesionContrato.getCodPersona().longValue()));
				if (modificacionContrato.getFechaInicioModificacion() != null)
					contrato.setFechaInicio(modificacionContrato.getFechaInicioModificacion());
				if (modificacionContrato.getFechaAutorizacionModificacion() != null)
					contrato.setFechaFirma(modificacionContrato.getFechaAutorizacionModificacion());
				
				obtenerDuracionContratos(this.contrato.getFechaInicio(), this.contrato.getFechaFin());
				contratoRes = ComunicacionServiciosCO.setContrato(contrato); //INSERTA
				if (lbResultadoModificacion && !contratoRes.isError()) {
					this.setStrMsgAccionesContrato(strMsgGuardarCesionExito);
					requestContext.execute("PF('dlgRegistroCesionGuardar').show();");
					this.intIndextab = 1;
					return null;
				} else {
					this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato + " - " + contratoRes.toString());
					requestContext.execute("PF('dlgRegistroCesionGuardar').show();");
					this.intIndextab = 1;
					return null;
				}
			} else {
				this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato);
				requestContext.execute("PF('dlgRegistroCesionGuardar').show();");
				this.intIndextab = 1;
				return null;
			}
		}
		return null;
	}
	
	
	public boolean validarValorAdicional(){
		
		List<ModificacionContratoExt> listModContratoCesion = ComunicacionServiciosCO.selectMontoContratoInicial(this.contrato.getCodPersona().longValue());
		
		if(listModContratoCesion != null && listModContratoCesion.size() > 0){					
			
			long sumaValorAdicion = modificacionContrato.getValorAdicion().longValue();
			//Consulta las adiciones que ha realizado el contrato actual
			List<ModificacionContratoExt> lstModificacionesAdicion = ComunicacionServiciosCO
					.getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoAdicion),
							contrato.getCodContrato(), 1);		
			List<ModificacionContratoExt> lstModificacionesAdProMod = ComunicacionServiciosCO
					.getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoAdProMod),
							contrato.getCodContrato(), 1);		
			
			List<ModificacionContratoExt> listAdicionesContrato = new ArrayList<ModificacionContratoExt>();
			if(lstModificacionesAdicion != null && lstModificacionesAdicion.size() > 0) {
				listAdicionesContrato.addAll(lstModificacionesAdicion);
			}
			if(lstModificacionesAdProMod != null && lstModificacionesAdProMod.size() > 0) {
				listAdicionesContrato.addAll(lstModificacionesAdProMod);
			}
			
			//Sumatoria de valores de adiciones que ha realizado el contrato actual
			for(ModificacionContratoExt modExt:listAdicionesContrato){
				sumaValorAdicion += modExt.getValorAdicion().longValue();						
			}	
			
			int montoContratoInicial = ((ModificacionContratoExt)listModContratoCesion.get(0)).getMontoRegistroContrato();
			BigDecimal b1 = new BigDecimal(String.valueOf(montoContratoInicial));					
		    BigDecimal b2 = new BigDecimal("50");
			BigDecimal porcentajeContratoIni = b1.multiply(b2).divide(new BigDecimal("100"), 0);
			
			for(ModificacionContratoExt modificacionContratoCesion: listModContratoCesion){										
				//Consulta las adiciones que ha realizado el contrato cedido
				lstModificacionesAdicion = ComunicacionServiciosCO
						.getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoAdicion),
								modificacionContratoCesion.getCodContrato(), 1);	
				
				lstModificacionesAdProMod = ComunicacionServiciosCO
						.getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoAdProMod),
								contrato.getCodContrato(), 1);		
				
				listAdicionesContrato = new ArrayList<ModificacionContratoExt>();
				if(lstModificacionesAdicion != null && lstModificacionesAdicion.size() > 0) {
					listAdicionesContrato.addAll(lstModificacionesAdicion);
				}
				if(lstModificacionesAdProMod != null && lstModificacionesAdProMod.size() > 0) {
					listAdicionesContrato.addAll(lstModificacionesAdProMod);
				}
				
				//Sumatoria de valores de adiciones que ha realizado el contrato
				for(ModificacionContratoExt modExt:listAdicionesContrato){
					sumaValorAdicion += modExt.getValorAdicion().longValue();						
				}							
			}
			BigDecimal sumValorAdicion = new BigDecimal(String.valueOf(sumaValorAdicion));	
			if(sumValorAdicion.longValue() > porcentajeContratoIni.longValue()){				
				return true;				
			}			
		}
		return false;		
	}
	
	
	/**
	 * Valida que el tamaño de la justificacións sea el máximo permitido
	 * 
	 * @param tamanno
	 * @return true si el tamño de la justificación es permitido false en caso
	 *         contrario
	 */
	private boolean validaTammanoJustificacionII(String dato) throws UnsupportedEncodingException {
		System.out.println("file.encoding = " + System.getProperty("file.encoding"));
		int total = 0;
		for (int i = 0; i < dato.length(); i++) {
			char a = dato.charAt(i);
			String s = new String(new char[] { a });
			byte[] bytes = s.getBytes("UTF-8");
			int bit = bytes.length;
			if (bit > 1) {
				total += bit + 1;
			} else {
				total += bit;
			}
		}
		if (total > 999)
			return false;
		return true;
	}

	public String getMensajeNumContratos() {
		String titleTxt = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_ENTIDAD_YA_EXISTE_CONTRATO, getLocale());
		return titleTxt + ' ' + contrato.getNumeroContrato();
	}

	private boolean validarTiempoProrroga(ModificacionContrato modificacion) {
		if (this.contrato.getFechaInicio() != null && this.contrato.getFechaFin() != null
				&& modificacion.getFechaFinModificacion() != null
				&& modificacion.getFechaInicioModificacion() != null) {
			long diferenciaEn_msContrato = this.contrato.getFechaFin().getTime()
					- this.contrato.getFechaInicio().getTime();
			long diferenciaEn_msProrroga = modificacion.getFechaFinModificacion().getTime()
					- modificacion.getFechaInicioModificacion().getTime();
			if (diferenciaEn_msProrroga > diferenciaEn_msContrato / 2)
				return false;
		}
		return true;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		// TODO Auto-generated method stub
	}

	@Override
	public String update() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// TODO Auto-generated method stub

	}

	public String getStrCodTipoIdentificacionBuscar() {
		return strCodTipoIdentificacionBuscar;
	}

	public void setStrCodTipoIdentificacionBuscar(String strCodTipoIdentificacionBuscar) {
		this.strCodTipoIdentificacionBuscar = strCodTipoIdentificacionBuscar;
	}

	public String getStrNumeroIdentificacionBuscar() {
		return strNumeroIdentificacionBuscar;
	}

	public void setStrNumeroIdentificacionBuscar(String strNumeroIdentificacionBuscar) {
		this.strNumeroIdentificacionBuscar = strNumeroIdentificacionBuscar;
	}

	public String getStrPrimerNombreBuscar() {
		return strPrimerNombreBuscar;
	}

	public void setStrPrimerNombreBuscar(String strPrimerNombreBuscar) {
		this.strPrimerNombreBuscar = strPrimerNombreBuscar;
	}

	public String getStrPrimerApellidoBuscar() {
		return strPrimerApellidoBuscar;
	}

	public void setStrPrimerApellidoBuscar(String strPrimerApellidoBuscar) {
		this.strPrimerApellidoBuscar = strPrimerApellidoBuscar;
	}

	public String getStrSegundoNombreBuscar() {
		return strSegundoNombreBuscar;
	}

	public void setStrSegundoNombreBuscar(String strSegundoNombreBuscar) {
		this.strSegundoNombreBuscar = strSegundoNombreBuscar;
	}

	public String getStrSegundoApellidoBuscar() {
		return strSegundoApellidoBuscar;
	}

	public void setStrSegundoApellidoBuscar(String strSegundoApellidoBuscar) {
		this.strSegundoApellidoBuscar = strSegundoApellidoBuscar;
	}

	public List<PersonaExt> getLstBusquedaPersonasExt() {
		return lstBusquedaPersonasExt;
	}

	public void setLstBusquedaPersonasExt(List<PersonaExt> lstBusquedaPersonasExt) {
		this.lstBusquedaPersonasExt = lstBusquedaPersonasExt;
	}

	public List<ContratoExt> getLstBuscarContratosPersona() {
		return lstBuscarContratosPersona;
	}

	public void setLstBuscarContratosPersona(List<ContratoExt> lstBuscarContratosPersona) {
		this.lstBuscarContratosPersona = lstBuscarContratosPersona;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public int getIntIndextab() {
		return intIndextab;
	}

	public void setIntIndextab(int intIndextab) {
		this.intIndextab = intIndextab;
	}

	public int getIntIndexOPContinuar() {
		return intIndexOPContinuar;
	}

	public void setIntIndexOPContinuar(int intIndexOPContinuar) {
		this.intIndexOPContinuar = intIndexOPContinuar;
	}

	public String getStrMsgContinuar() {
		return strMsgContinuar;
	}

	public void setStrMsgContinuar(String strMsgContinuar) {
		this.strMsgContinuar = strMsgContinuar;
	}

	public boolean isBltabBuscarActivo() {
		return bltabBuscarActivo;
	}

	public void setBltabBuscarActivo(boolean bltabBuscarActivo) {
		this.bltabBuscarActivo = bltabBuscarActivo;
	}

	public String getStrEntidadUsuario() {
		return strEntidadUsuario;
	}

	public void setStrEntidadUsuario(String strEntidadUsuario) {
		this.strEntidadUsuario = strEntidadUsuario;
	}

	public String getStrAreaEntidad() {
		return strAreaEntidad;
	}

	public void setStrAreaEntidad(String strAreaEntidad) {
		this.strAreaEntidad = strAreaEntidad;
	}

	public PersonaExt getPersonaSelected() {
		return personaSelected;
	}

	public void setPersonaSelected(PersonaExt personaSelected) {
		this.personaSelected = personaSelected;
	}

	public ModificacionContrato getModificacionContrato() {
		return modificacionContrato;
	}

	public void setModificacionContrato(ModificacionContrato modificacionContrato) {
		this.modificacionContrato = modificacionContrato;
	}

	public PersonaExt getPersonaCesionContrato() {
		return personaCesionContrato;
	}

	public void setPersonaCesionContrato(PersonaExt personaCesionContrato) {
		this.personaCesionContrato = personaCesionContrato;
	}

	public String getStrTipoModificacion() {
		return strTipoModificacion;
	}

	public void setStrTipoModificacion(String strTipoModificacion) {
		this.strTipoModificacion = strTipoModificacion;
	}

	public String getStsNombreTabContrato() {
		return stsNombreTabContrato;
	}

	public void setStsNombreTabContrato(String stsNombreTabContrato) {
		this.stsNombreTabContrato = stsNombreTabContrato;
	}

	public List<ModificacionContratoExt> getLstModificacionesContrato() {
		return lstModificacionesContrato;
	}

	public void setLstModificacionesContrato(List<ModificacionContratoExt> lstModificacionesContrato) {
		this.lstModificacionesContrato = lstModificacionesContrato;
	}

	public boolean isLbIsRolAdminFUNC() {
		return lbIsRolAdminFUNC;
	}

	public void setLbIsRolAdminFUNC(boolean lbIsRolAdminFUNC) {
		this.lbIsRolAdminFUNC = lbIsRolAdminFUNC;
	}

	public String getStrNombreBotonContratos() {
		return strNombreBotonContratos;
	}

	public void setStrNombreBotonContratos(String strNombreBotonContratos) {
		this.strNombreBotonContratos = strNombreBotonContratos;
	}

	public boolean isLbContratoDeshabilitaMonto() {
		return lbContratoDeshabilitaMonto;
	}

	public void setLbContratoDeshabilitaMonto(boolean lbContratoDeshabilitaMonto) {
		this.lbContratoDeshabilitaMonto = lbContratoDeshabilitaMonto;
	}

	public boolean isLbContratoDeshabilitaFecIni() {
		return lbContratoDeshabilitaFecIni;
	}

	public void setLbContratoDeshabilitaFecIni(boolean lbContratoDeshabilitaFecIni) {
		this.lbContratoDeshabilitaFecIni = lbContratoDeshabilitaFecIni;
	}

	public boolean isLbContratoDeshabilitaFecFin() {
		return lbContratoDeshabilitaFecFin;
	}

	public void setLbContratoDeshabilitaFecFin(boolean lbContratoDeshabilitaFecFin) {
		this.lbContratoDeshabilitaFecFin = lbContratoDeshabilitaFecFin;
	}

	public boolean isLbContratoDeshabilitaJustificacion() {
		return lbContratoDeshabilitaJustificacion;
	}

	public void setLbContratoDeshabilitaJustificacion(boolean lbContratoDeshabilitaJustificacion) {
		this.lbContratoDeshabilitaJustificacion = lbContratoDeshabilitaJustificacion;
	}

	public String getStrMsgGuardarExitoContrato() {
		return strMsgGuardarExitoContrato;
	}

	public void setStrMsgGuardarExitoContrato(String strMsgGuardarExitoContrato) {
		this.strMsgGuardarExitoContrato = strMsgGuardarExitoContrato;
	}

	public String getStrMsgMensajesvalidacionesProrroga() {
		return strMsgMensajesvalidacionesProrroga;
	}

	public void setStrMsgMensajesvalidacionesProrroga(String strMsgMensajesvalidacionesProrroga) {
		this.strMsgMensajesvalidacionesProrroga = strMsgMensajesvalidacionesProrroga;
	}

	public String getStrMsgTieneDerechosExclusividad() {
		return strMsgTieneDerechosExclusividad;
	}

	public void setStrMsgTieneDerechosExclusividad(String strMsgTieneDerechosExclusividad) {
		this.strMsgTieneDerechosExclusividad = strMsgTieneDerechosExclusividad;
	}

	public String getStrMsgGuardarErrorContrato() {
		return strMsgGuardarErrorContrato;
	}

	public void setStrMsgGuardarErrorContrato(String strMsgGuardarErrorContrato) {
		this.strMsgGuardarErrorContrato = strMsgGuardarErrorContrato;
	}

	public String getStrMsgAccionesContrato() {
		return strMsgAccionesContrato;
	}

	public void setStrMsgAccionesContrato(String strMsgAccionesContrato) {
		this.strMsgAccionesContrato = strMsgAccionesContrato;
	}

	public String getStrMsgModificarContratoExito() {
		return strMsgModificarContratoExito;
	}

	public void setStrMsgModificarContratoExito(String strMsgModificarContratoExito) {
		this.strMsgModificarContratoExito = strMsgModificarContratoExito;
	}

	public String getStrMsgGuardarAdicionExito() {
		return strMsgGuardarAdicionExito;
	}

	public void setStrMsgGuardarAdicionExito(String strMsgGuardarAdicionExito) {
		this.strMsgGuardarAdicionExito = strMsgGuardarAdicionExito;
	}

	public String getStrMsgGuardarProrrogaExito() {
		return strMsgGuardarProrrogaExito;
	}

	public void setStrMsgGuardarProrrogaExito(String strMsgGuardarProrrogaExito) {
		this.strMsgGuardarProrrogaExito = strMsgGuardarProrrogaExito;
	}

	public String getStrMsgGuardarAlcanceExito() {
		return strMsgGuardarAlcanceExito;
	}

	public void setStrMsgGuardarAlcanceExito(String strMsgGuardarAlcanceExito) {
		this.strMsgGuardarAlcanceExito = strMsgGuardarAlcanceExito;
	}

	public String getStrMsgGuardarSuspensionExito() {
		return strMsgGuardarSuspensionExito;
	}

	public void setStrMsgGuardarSuspensionExito(String strMsgGuardarSuspensionExito) {
		this.strMsgGuardarSuspensionExito = strMsgGuardarSuspensionExito;
	}

	public String getStrMsgGuardarTerminacionExito() {
		return strMsgGuardarTerminacionExito;
	}

	public void setStrMsgGuardarTerminacionExito(String strMsgGuardarTerminacionExito) {
		this.strMsgGuardarTerminacionExito = strMsgGuardarTerminacionExito;
	}

	public String getStrMsgGuardarCesionExito() {
		return strMsgGuardarCesionExito;
	}

	public void setStrMsgGuardarCesionExito(String strMsgGuardarCesionExito) {
		this.strMsgGuardarCesionExito = strMsgGuardarCesionExito;
	}

	public String getStrMsgGuardarTerminacionManualExito() {
		return strMsgGuardarTerminacionManualExito;
	}

	public void setStrMsgGuardarTerminacionManualExito(String strMsgGuardarTerminacionManualExito) {
		this.strMsgGuardarTerminacionManualExito = strMsgGuardarTerminacionManualExito;
	}

	public boolean isLbSuspension() {
		return lbSuspension;
	}

	public void setLbSuspension(boolean lbSuspension) {
		this.lbSuspension = lbSuspension;
	}

	public String getStrNombreBotonSuspension() {
		return strNombreBotonSuspension;
	}

	public void setStrNombreBotonSuspension(String strNombreBotonSuspension) {
		this.strNombreBotonSuspension = strNombreBotonSuspension;
	}

	public List<ModificacionContratoExt> getLstConsultarCoModificaciones() {
		return lstConsultarCoModificaciones;
	}

	public void setLstConsultarCoModificaciones(List<ModificacionContratoExt> lstConsultarCoModificaciones) {
		this.lstConsultarCoModificaciones = lstConsultarCoModificaciones;
	}

	public ModificacionContrato getModificacionConsultaReversaSelected() {
		return modificacionConsultaReversaSelected;
	}

	public void setModificacionConsultaReversaSelected(ModificacionContrato modificacionConsultaReversaSelected) {
		this.modificacionConsultaReversaSelected = modificacionConsultaReversaSelected;
	}

	public boolean isLbconsultarReversar() {
		return lbconsultarReversar;
	}

	public void setLbconsultarReversar(boolean lbconsultarReversar) {
		this.lbconsultarReversar = lbconsultarReversar;
	}

	public Usuario getUsuarioConsultaReversa() {
		return usuarioConsultaReversa;
	}

	public void setUsuarioConsultaReversa(Usuario usuarioConsultaReversa) {
		this.usuarioConsultaReversa = usuarioConsultaReversa;
	}

	public PersonaExt getPersonaUsuarioConsultaContrato() {
		return personaUsuarioConsultaContrato;
	}

	public void setPersonaUsuarioConsultaContrato(PersonaExt personaUsuarioConsultaContrato) {
		this.personaUsuarioConsultaContrato = personaUsuarioConsultaContrato;
	}

	public boolean isLbIsTabBuscar() {
		return lbIsTabBuscar;
	}

	public void setLbIsTabBuscar(boolean lbIsTabBuscar) {
		this.lbIsTabBuscar = lbIsTabBuscar;
	}

	public boolean isLbIsTabContrato() {
		return lbIsTabContrato;
	}

	public void setLbIsTabContrato(boolean lbIsTabContrato) {
		this.lbIsTabContrato = lbIsTabContrato;
	}

	public boolean isLbIsTabAdicion() {
		return lbIsTabAdicion;
	}

	public void setLbIsTabAdicion(boolean lbIsTabAdicion) {
		this.lbIsTabAdicion = lbIsTabAdicion;
	}

	public boolean isLbIsTabProrroga() {
		return lbIsTabProrroga;
	}

	public void setLbIsTabProrroga(boolean lbIsTabProrroga) {
		this.lbIsTabProrroga = lbIsTabProrroga;
	}

	public boolean isLbIsTabAlcance() {
		return lbIsTabAlcance;
	}

	public void setLbIsTabAlcance(boolean lbIsTabAlcance) {
		this.lbIsTabAlcance = lbIsTabAlcance;
	}

	public boolean isLbIsTabSuspension() {
		return lbIsTabSuspension;
	}

	public void setLbIsTabSuspension(boolean lbIsTabSuspension) {
		this.lbIsTabSuspension = lbIsTabSuspension;
	}

	public boolean isLbIsTabTerminacion() {
		return lbIsTabTerminacion;
	}

	public void setLbIsTabTerminacion(boolean lbIsTabTerminacion) {
		this.lbIsTabTerminacion = lbIsTabTerminacion;
	}

	public boolean isLbIsTabAdPrAl() {
		return lbIsTabAdPrAl;
	}

	public void setLbIsTabAdPrAl(boolean lbIsTabAdPrAl) {
		this.lbIsTabAdPrAl = lbIsTabAdPrAl;
	}

	public boolean isLbIsTabCesion() {
		return lbIsTabCesion;
	}

	public void setLbIsTabCesion(boolean lbIsTabCesion) {
		this.lbIsTabCesion = lbIsTabCesion;
	}

	public boolean isLbIsTabConsultar() {
		return lbIsTabConsultar;
	}

	public void setLbIsTabConsultar(boolean lbIsTabConsultar) {
		this.lbIsTabConsultar = lbIsTabConsultar;
	}

	public boolean isLbIsmostrarListaContratosPersona() {
		return lbIsmostrarListaContratosPersona;
	}

	public void setLbIsmostrarListaContratosPersona(boolean lbIsmostrarListaContratosPersona) {
		this.lbIsmostrarListaContratosPersona = lbIsmostrarListaContratosPersona;
	}

	public String getStrMsgContratosRegistradosPersona() {
		return strMsgContratosRegistradosPersona;
	}

	public void setStrMsgContratosRegistradosPersona(String strMsgContratosRegistradosPersona) {
		this.strMsgContratosRegistradosPersona = strMsgContratosRegistradosPersona;
	}

	public String getStrValidaHV() {
		return strValidaHV;
	}

	public void setStrValidaHV(String strValidaHV) {
		this.strValidaHV = strValidaHV;
	}

	public Long getLlDiasLimiteProrrogaContrato() {
		return llDiasLimiteProrrogaContrato;
	}

	public void setLlDiasLimiteProrrogaContrato(Long llDiasLimiteProrrogaContrato) {
		this.llDiasLimiteProrrogaContrato = llDiasLimiteProrrogaContrato;
	}

	public String getStrMsgReversaModificacionMsg() {
		return strMsgReversaModificacionMsg;
	}

	public void setStrMsgReversaModificacionMsg(String strMsgReversaModificacionMsg) {
		this.strMsgReversaModificacionMsg = strMsgReversaModificacionMsg;
	}

	public String getStrMsgReversaModificacionUnica() {
		return strMsgReversaModificacionUnica;
	}

	public void setStrMsgReversaModificacionUnica(String strMsgReversaModificacionUnica) {
		this.strMsgReversaModificacionUnica = strMsgReversaModificacionUnica;
	}

	public String getStrMsgReversaModificacionVarias() {
		return strMsgReversaModificacionVarias;
	}

	public void setStrMsgReversaModificacionVarias(String strMsgReversaModificacionVarias) {
		this.strMsgReversaModificacionVarias = strMsgReversaModificacionVarias;
	}

	public String getStrNombreBotonContratoVolver() {
		return strNombreBotonContratoVolver;
	}

	public void setStrNombreBotonContratoVolver(String strNombreBotonContratoVolver) {
		this.strNombreBotonContratoVolver = strNombreBotonContratoVolver;
	}

	public String getStrNombreSupervisor() {
		return strNombreSupervisor;
	}

	public void setStrNombreSupervisor(String strNombreSupervisor) {
		this.strNombreSupervisor = strNombreSupervisor;
	}

	public String getStrNombreGradoVinculacionSupervisor() {
		return strNombreGradoVinculacionSupervisor;
	}

	public void setStrNombreGradoVinculacionSupervisor(String strNombreGradoVinculacionSupervisor) {
		this.strNombreGradoVinculacionSupervisor = strNombreGradoVinculacionSupervisor;
	}

	public String getStrNombrePersonaCesion() {
		return strNombrePersonaCesion;
	}

	public void setStrNombrePersonaCesion(String strNombrePersonaCesion) {
		this.strNombrePersonaCesion = strNombrePersonaCesion;
	}

	public List<ModificacionContratoExt> getLstModificacionesContratoReversa() {
		return lstModificacionesContratoReversa;
	}

	public void setLstModificacionesContratoReversa(List<ModificacionContratoExt> lstModificacionesContratoReversa) {
		this.lstModificacionesContratoReversa = lstModificacionesContratoReversa;
	}

	public boolean isLbRolValidoReversa() {
		return lbRolValidoReversa;
	}

	public void setLbRolValidoReversa(boolean lbRolValidoReversa) {
		this.lbRolValidoReversa = lbRolValidoReversa;
	}

	public boolean isLbUltimaModificacion() {
		return lbUltimaModificacion;
	}

	public void setLbUltimaModificacion(boolean lbUltimaModificacion) {
		this.lbUltimaModificacion = lbUltimaModificacion;
	}

	public boolean isLbEliminaModPosteriores() {
		return lbEliminaModPosteriores;
	}

	public void setLbEliminaModPosteriores(boolean lbEliminaModPosteriores) {
		this.lbEliminaModPosteriores = lbEliminaModPosteriores;
	}

	public String getStrLabelFormularioSuspension() {
		return strLabelFormularioSuspension;
	}

	public void setStrLabelFormularioSuspension(String strLabelFormularioSuspension) {
		this.strLabelFormularioSuspension = strLabelFormularioSuspension;
	}

	public String getStrLabelFormularioAgregarSuspension() {
		return strLabelFormularioAgregarSuspension;
	}

	public void setStrLabelFormularioAgregarSuspension(String strLabelFormularioAgregarSuspension) {
		this.strLabelFormularioAgregarSuspension = strLabelFormularioAgregarSuspension;
	}

	public String getStrLabelFormularioQuitarSuspension() {
		return strLabelFormularioQuitarSuspension;
	}

	public void setStrLabelFormularioQuitarSuspension(String strLabelFormularioQuitarSuspension) {
		this.strLabelFormularioQuitarSuspension = strLabelFormularioQuitarSuspension;
	}

	/**
	 * @pjqr 20180322 NCU: 0301:6
	 */
	public void consultarPersonas() {
		RequestContext context = RequestContext.getCurrentInstance();
		strMsgContratosRegistradosPersona = "";
		limpiarmensajes();
		if (this.strCodTipoIdentificacionBuscar != null && !this.strCodTipoIdentificacionBuscar.equals("")
				|| this.strNumeroIdentificacionBuscar != null && !this.strNumeroIdentificacionBuscar.equals("")
				|| this.strPrimerApellidoBuscar != null && !this.strPrimerApellidoBuscar.equals("")
				|| this.strPrimerNombreBuscar != null && !this.strPrimerNombreBuscar.equals("")
				|| this.strSegundoApellidoBuscar != null && !this.strSegundoApellidoBuscar.equals("")
				|| this.strSegundoNombreBuscar != null && !this.strSegundoNombreBuscar.equals("")) {
			PersonaExt personaBuscar = new PersonaExt();
			personaBuscar.setCodTipoIdentificacion(
					this.strCodTipoIdentificacionBuscar != null && !("").equals(this.strCodTipoIdentificacionBuscar)
							? this.strCodTipoIdentificacionBuscar
							: null);
			personaBuscar.setNumeroIdentificacion(
					this.strNumeroIdentificacionBuscar != null && !("").equals(this.strNumeroIdentificacionBuscar)
							? this.strNumeroIdentificacionBuscar
							: null);
			personaBuscar.setPrimerApellido(
					this.strPrimerApellidoBuscar != null && !("").equals(this.strPrimerApellidoBuscar)
							? this.strPrimerApellidoBuscar
							: null);
			personaBuscar.setSegundoApellido(
					this.strSegundoApellidoBuscar != null && !("").equals(this.strSegundoApellidoBuscar)
							? this.strSegundoApellidoBuscar
							: null);
			personaBuscar.setPrimerNombre(this.strPrimerNombreBuscar != null && !("").equals(this.strPrimerNombreBuscar)
					? this.strPrimerNombreBuscar
					: null);
			personaBuscar
					.setSegundoNombre(this.strSegundoNombreBuscar != null && !("").equals(this.strSegundoNombreBuscar)
							? this.strSegundoNombreBuscar
							: null);
			personaBuscar.setCodEntidad(BigDecimal.valueOf(entidadUsuario.getId()));
			personaBuscar.setLimitInit(0);
			personaBuscar.setLimitEnd(300);
			personaBuscar.setFlgEstado((short) 1);
			personaBuscar.setCodTipoVinculacion(BigDecimal.valueOf(liParTipoVincluacionContratista));
			if (lbIsRolAdminFUNC) {
				lstBusquedaPersonasExt = ComunicacionServiciosCO.getPersonaporEntidadFiltro(personaBuscar);
			} else {
				lstBusquedaPersonasExt = ComunicacionServiciosCO.getPersonaporEntidadFiltro(personaBuscar);
			}
			if (lstBusquedaPersonasExt == null || lstBusquedaPersonasExt.size() == 0) {
				limpiarmensajes();
				this.setStrMsgAccionesContrato(strMsgBusquedaNoResultados);
				context.execute("PF('dlgAccionesContrato').show();");
			} else {
				limpiarmensajes();
				this.setStrMsgAccionesContrato(strMsgBusquedaExitosa);
				context.execute("PF('dlgAccionesContrato').show();");
			}
		} else {
			limpiarmensajes();
			this.setStrMsgAccionesContrato(strMsgIngresarCriterioBusqueda);
			context.execute("PF('dlgAccionesContrato').show();");
			lstBusquedaPersonasExt = null;
		}
		lstBuscarContratosPersona = null;
		lbIsmostrarListaContratosPersona = false;
	}

	/* Método para buscar los contratos de un contratista */
	public void verContratosPorContratista(PersonaExt persona) {
		String strNombre;
		RequestContext context = RequestContext.getCurrentInstance();
		if (persona == null)/* Se consulta desde mensaje que tiee dechos de exclusividad */
			persona = personaSelected;

		strMsgContratosRegistradosPersona = "";
		personaSelected = persona;
		lstBuscarContratosPersona = ComunicacionServiciosCO
				.getContratoPersona(personaSelected.getCodPersona().longValue());
		if (lstBuscarContratosPersona == null || lstBuscarContratosPersona.size() == 0) {
			this.setStrMsgAccionesContrato(strMsgNoContratosRegistrados);
			context.execute("PF('dlgAccionesContrato').show();");
			lbIsmostrarListaContratosPersona = false;
		} else {
			lbIsmostrarListaContratosPersona = true;
			strNombre = (personaSelected.getPrimerNombre() != null ? ucFirst(personaSelected.getPrimerNombre())
					: "" + "") + " "
					+ (personaSelected.getSegundoNombre() != null ? ucFirst(personaSelected.getSegundoNombre()) : "")
					+ " "
					+ (personaSelected.getPrimerApellido() != null ? ucFirst(personaSelected.getPrimerApellido()) : "")
					+ " "
					+ (personaSelected.getSegundoApellido() != null ? ucFirst(personaSelected.getSegundoApellido())
							: "");
			strMsgContratosRegistradosPersona = TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_CONTRATOS_REGISTRADOS_SIGEPII, getLocale())
					+ ": " + strNombre + " "
					+ TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_CON, getLocale())
							.toLowerCase()
					+ " " + personaSelected.getNombreTipoDocuento().toLowerCase() + " " + TitlesBundleConstants
							.getStringMessagesBundle(TitlesBundleConstants.TTL_NUMERO, getLocale()).toLowerCase()
					+ " " + personaSelected.getNumeroIdentificacion();
		}
	}

	public static String ucFirst(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		} else {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
	}

	/**
	 * Registrar Contrato
	 * 
	 * @param index
	 */
	public void registrarContrato(Integer index) {
		RequestContext context = RequestContext.getCurrentInstance();
		vinculacionSupervisor = new VinculacionExt();
		/*
		 * CU0301-FA02:2
		 */
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		boolean lbexiteDerExc = false;
		List<ContratoExt> lstContratos = ComunicacionServiciosCO
				.getContratoPersona(personaSelected.getCodPersona().longValue());
		for (ContratoExt cont : lstContratos) {
			if (cont.getDerechoExclusividad() == 1) {
				lbexiteDerExc = true;
				break;
			}
		}
		if (lbexiteDerExc) {
			intIndextab = 0;
			this.strMsgAccionesContrato = strMsgTieneDerechosExclusividad;
			context.execute("PF('dlgAccionesContrato').show();");
		} else {
			boolean hojaVidaValida = validarHojaVidaAprobada();
			/*
			 * CU0301-FA08
			 */
			if (!hojaVidaValida) {
				intIndextab = 0;
				context.execute("PF('dlgAccionesContrato').show();");
				return;
			}

			intIndextab = index;
			inicializarContrato();
			if (entidadUsuario != null) {
				this.strEntidadUsuario = entidadUsuario.getNombreEntidad();
			}
			this.setStsNombreTabContrato(
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_REGISTRAR_CONTRATO_SIGEP));
			this.setStrTipoModificacion(this.strContratoNuevo);
			this.strNombreBotonContratos = TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_REGISTRARCONTRATO);
			this.strNombreBotonContratoVolver = TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.LBL_REGRESAR);
			this.lbContratoDeshabilitaJustificacion = true;
			obtenerDuracionContratos(this.contrato.getFechaInicio(), this.contrato.getFechaFin());
			ocultarTabs(1);
		}
	}

	/**
	 * Modificar Contrato
	 * 
	 * @param index
	 */
	public void modificarContrato(Integer index, ContratoExt contratoSel) {
		vinculacionSupervisor = new VinculacionExt();
		RequestContext context = RequestContext.getCurrentInstance();
		boolean blMensaje = false;
		lbContratoDeshabilitaMonto = false;
		lbContratoDeshabilitaFecIni = false;
		lbContratoDeshabilitaFecFin = false;
		lbContratoDeshabilitaJustificacion = false;
		if (isContratoInactivo(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgContratoNoActivo);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!validarVigencia(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.strMsgAccionesContrato = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MENSAJE_VIGENCIA_ANTERIOR, getLocale());
			context.execute("PF('dlgAccionesContrato').show();");
		} else {
			/*
			 * CU0302–FA02:1 CU0302–FA02:2 CU0302–FA02:3 CU0302–FA02:4
			 */
			List<ModificacionContratoExt> lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoProrroga), contratoSel.getCodContrato(), 1);
			if (lstModificaciones != null && lstModificaciones.size() > 0) {
				lbContratoDeshabilitaFecIni = true;
				lbContratoDeshabilitaFecFin = true;
			}
			lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoAdicion), contratoSel.getCodContrato(), 1);
			if (lstModificaciones != null && lstModificaciones.size() > 0)
				lbContratoDeshabilitaMonto = true;
			lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoCesion), contratoSel.getCodContrato(), 1);
			if (lstModificaciones != null && lstModificaciones.size() > 0) {
				lbContratoDeshabilitaMonto = true;
				lbContratoDeshabilitaFecIni = true;
				lbContratoDeshabilitaFecFin = true;
			}

			inicializarContrato();
			this.setContrato(contratoSel);
			this.setStsNombreTabContrato(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_CORREGIR_CONTRATO, getLocale()));
			this.setStrTipoModificacion(this.strContratoModificacion);
			this.strNombreBotonContratos = TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_CORREGIR_CONTRATO, getLocale());
			this.strNombreBotonContratoVolver = TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.BTN_CT_VOLVER, getLocale());
			strMensajeRegistrarContrato = strMensajeCorregirContrato;
			if (lbContratoDeshabilitaFecIni || lbContratoDeshabilitaMonto) {
				blMensaje = true;
				this.strMsgAccionesContrato = MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.MSG_CT_CONTRATO_REGISTRA_MODIFICACIONES, getLocale());
				context.execute("PF('dlgRegistroContrato').show();");
			}
			obtenerDuracionContratos(this.contrato.getFechaInicio(), this.contrato.getFechaFin());
			if (contrato.getCodSupervisor() != null && !contrato.getCodSupervisor().equals(0)) {

				consultarSupervisorByCodigoPersona(contrato.getCodSupervisor(), true, false);
			}
			if (entidadUsuario != null) {
				this.strEntidadUsuario = entidadUsuario.getNombreEntidad();
			}
			if (contrato != null && contrato.getCodEntidad() != null && contrato.getCodEntidad() > 0) {
				entidadContrato = ComunicacionServiciosEnt.getEntidadPorId(contrato.getCodEntidad());
				if (entidadContrato != null) {
					this.strEntidadUsuario = entidadContrato.getNombreEntidad();
				}
			}
			this.setStrMsgContinuar(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_CORREGIR_CONTRATO, getLocale()));
			if (!blMensaje) {
				context.execute("PF('dlgContinuar').show();");
				intIndexOPContinuar = index;
			} else {
				ocultarTabs(1);
				intIndextab = index;

			}

		}
	}

	private boolean validarVigencia(Contrato contratoSel) {
		Date maxfecha = contratoSel.getFechaFin();
		Date today = new Date();

		List<ModificacionContratoExt> lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
				Long.valueOf(this.strContratoProrroga), contratoSel.getCodContrato(), 1);
		if (lstModificaciones != null && lstModificaciones.size() > 0) {
			for (ModificacionContratoExt modificacion : lstModificaciones) {
				if (modificacion.getFechaFinModificacion() != null
						&& modificacion.getFechaFinModificacion().after(maxfecha))
					maxfecha = modificacion.getFechaFinModificacion();
			}
		}
		/*
		 * las modificaciones strContratoAdProMod(886) son Adición y/o Prorroga y/o
		 * Alcance: si las mismas tienen fecha inicio modificacion y fecha fin
		 * modificacion son prorrogas
		 */
		lstModificaciones = null;
		lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
				Long.valueOf(this.strContratoAdProMod), contratoSel.getCodContrato(), 1);
		if (lstModificaciones != null && lstModificaciones.size() > 0) {
			for (ModificacionContratoExt modificacion : lstModificaciones) {
				if ((modificacion.getFechaInicioModificacion() != null
						&& modificacion.getFechaFinModificacion() != null)
						&& (modificacion.getFechaFinModificacion().after(maxfecha))) {
					maxfecha = modificacion.getFechaFinModificacion();
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			today = sdf.parse(sdf.format(new Date()));
			maxfecha = sdf.parse(sdf.format(maxfecha));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (today.after(maxfecha))
			return false;
		return true;
	}

	private boolean validarVigencia_1(Contrato contratoSel) {
		Date maxfecha = contratoSel.getFechaFin();
		Date today = new Date();

		List<ModificacionContratoExt> lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
				Long.valueOf(this.strContratoProrroga), contratoSel.getCodContrato(), 1);
		if (lstModificaciones != null && lstModificaciones.size() > 0) {
			for (ModificacionContratoExt modificacion : lstModificaciones) {
				if (modificacion.getFechaFinModificacion() != null
						&& modificacion.getFechaFinModificacion().after(maxfecha))
					maxfecha = modificacion.getFechaFinModificacion();
			}
		}
		/*
		 * las modificaciones strContratoAdProMod(886) son Adición y/o Prorroga y/o
		 * Alcance: si las mismas tienen fecha inicio modificacion y fecha fin
		 * modificacion son prorrogas
		 */
		lstModificaciones = null;
		lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
				Long.valueOf(this.strContratoAdProMod), contratoSel.getCodContrato(), 1);
		if (lstModificaciones != null && lstModificaciones.size() > 0) {
			for (ModificacionContratoExt modificacion : lstModificaciones) {
				if ((modificacion.getFechaInicioModificacion() != null
						&& modificacion.getFechaFinModificacion() != null)
						&& (modificacion.getFechaFinModificacion().after(maxfecha))) {
					maxfecha = modificacion.getFechaFinModificacion();
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		try {
			today = sdf.parse(sdf.format(new Date()));
			maxfecha = sdf.parse(sdf.format(maxfecha));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int an01 = Integer.parseInt(sdf.format(new Date()));
			int an02 = Integer.parseInt(sdf.format(maxfecha));
			if (an01 > an02)
				return false;
		} catch (NumberFormatException e) {
		}

		return true;
	}

	private boolean validaMaximaFechaProrroga(ContratoExt contratoSel) {
		Date maxfecha = obtenerMaximaFechaProrroga(contratoSel);
		if (maxfecha == null || maxfecha.before(contratoSel.getFechaFin()))
			maxfecha = contratoSel.getFechaFin();
		int difDias = obtenerDiferenciaDiasFechas(maxfecha, new Date());
		if (difDias > llDiasLimiteProrrogaContrato)
			return false;
		return true;
	}

	/**
	 * Registar Adición Contrato
	 * 
	 * @param index
	 */
	public void registrarAdicionContrato(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		/**
		 * @pjqr 20180322 CU0303–FA03
		 */
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (isContratoInactivo(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgContratoNoActivo);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}
		if (!validarVigencia(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_CONTRATOS_REGISTRADOS_SIGEPII, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
		} else {
			modificacionContrato = new ModificacionContrato();
			this.setStrTipoModificacion(this.strContratoAdicion);
			this.setContrato(contratoSel);
			lstModificacionesContrato = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoAdicion), contratoSel.getCodContrato(), 1);
			context.execute("PF('dlgContinuar').show();");
			intIndexOPContinuar = index;
			this.setStrMsgContinuar(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_ADICIONAR_CONTRATO, getLocale()));
		}
	}

	/**
	 * Registrar Prórroga Contrato
	 * 
	 * @param index
	 */
	public void registrarProrrogaContrato(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		Date ldFechaIni;
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}
		/**
		 * @pjqr 20180322 CU0304–FA03
		 */
		if (!validaMaximaFechaProrroga(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_NO_PRORROGABLE, getLocale()) + " "
					+ llDiasLimiteProrrogaContrato + " " + MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_DIAS_PRORROGA, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
		} else {
			modificacionContrato = new ModificacionContrato();
			this.setStrTipoModificacion(this.strContratoProrroga);
			this.setContrato(contratoSel);
			lstModificacionesContrato = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoProrroga), contratoSel.getCodContrato(), 1);
			context.execute("PF('dlgContinuar').show();");
			intIndexOPContinuar = index;
			this.setStrMsgContinuar(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_PRORROGAR_CONTRATO, getLocale()));
			ldFechaIni = obtenerMaximaFechaProrroga();
			modificacionContrato.setFechaInicioModificacion(ldFechaIni);
		}
	}

	public void registrarAlcanceContrato(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (isContratoInactivo(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgContratoNoActivo);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}

		/**
		 * @pjqr 20180322 CU0305–FA03
		 */
		if (!validarVigencia(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MENSAJE_VIGENCIA_ANTERIOR, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
		} else {

			modificacionContrato = new ModificacionContrato();
			this.setStrTipoModificacion(this.strContratoAlcance);
			this.setContrato(contratoSel);
			lstModificacionesContrato = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoAlcance), contratoSel.getCodContrato(), 1);

			context.execute("PF('dlgContinuar').show();");
			intIndexOPContinuar = index;
			this.setStrMsgContinuar(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_MODIFICAR_ALCANCE_CONTRATO, getLocale()));
		}

	}

	public void registrarSuspensionContrato(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		/**
		 * @pjqr 20180322 CU0306–FA04
		 */
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (isContratoInactivo(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgContratoNoActivo);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}
		lbSuspension = !validaContratoSuspendido(contratoSel);
		if (lbSuspension) {
			this.setStrNombreBotonSuspension(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_SUSPENDERCONTRATO, getLocale()));
			strMsgGuardarSuspensionExito = TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_SUSPENDERCONTRATO, getLocale());
			this.setStrLabelFormularioSuspension(strLabelFormularioAgregarSuspension);
			this.setStrTipoModificacion(this.strContratoSuspension);
			strLabelFechaDefFrmSuspension = strLabelFechaFinSuspension;

		} else {
			this.setStrNombreBotonSuspension(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_QUITAR_SUSPENSION, getLocale()));
			this.setStrLabelFormularioSuspension(strLabelFormularioQuitarSuspension);
			strMsgGuardarSuspensionExito = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_LEVANTADA_SUSPENSION, getLocale());
			this.setStrTipoModificacion(this.strContratoQuitarSuspension);
			strLabelFechaDefFrmSuspension = strLabelFechaFinQuitarSuspension;
		}
		if (!validarVigencia(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MENSAJE_VIGENCIA_ANTERIOR, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
		} else {
			modificacionContrato = new ModificacionContrato();
			if (!lbSuspension) {
				modificacionContrato.setFechaFinModificacion(new Date());
			}
			this.setContrato(contratoSel);
			lstModificacionesContrato = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoSuspension), contratoSel.getCodContrato(), 1);
			lstModificacionesSuspensiones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoQuitarSuspension), contratoSel.getCodContrato(), 1);
			lstModificacionesContrato.addAll(lstModificacionesSuspensiones);
			Collections.sort(lstModificacionesContrato);
			context.execute("PF('dlgContinuar').show();");
			intIndexOPContinuar = index;
			this.setStrMsgContinuar(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_QUITAR_SUSPENDER_CONTRATO, getLocale()));
		}
	}

	public void continuarAccion() {
		ocultarTabs(intIndexOPContinuar);
		intIndextab = intIndexOPContinuar;

	}

	/**
	 * valida si un contrato se encuentra suspendido
	 * 
	 * @param contrato
	 * @return true si contrtao está suspendido
	 */
	private boolean validaContratoSuspendido(ContratoExt pContratoSuspendido) {
		/*
		 * if (pContrato.getFlgActivo() != null && pContrato.getFlgActivo() == 1) return
		 * false; ModificacionContratoExt ultimaSuspension = null;
		 * List<ModificacionContratoExt> lstModificacionesSuspensiones =
		 * ComunicacionServiciosCO
		 * .getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoSuspension),
		 * pContrato.getCodContrato(), 1); if (lstModificacionesSuspensiones != null &&
		 * lstModificacionesSuspensiones.size() > 0) ultimaSuspension =
		 * lstModificacionesSuspensiones.get(0); if(ultimaSuspension!=null){
		 * if(validaFechaAfter(pContrato.getFechaFin(),ultimaSuspension.
		 * getFechaFinModificacion())) return true; }
		 */
		if (pContratoSuspendido.getFlgEstadoSuspendido() != null && pContratoSuspendido.getFlgEstadoSuspendido() == 2)
			return true;
		return false;
	}

	public void registrarTerminacionContrato(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();

		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (isContratoInactivo(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgContratoNoActivo);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}

		/**
		 * @pjqr 20180322 CU0307–FA03
		 */
		if (!validarVigencia(contratoSel) && !lbIsRolAdminFUNC) {/**/
			intIndextab = 0;
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MENSAJE_VIGENCIA_ANTERIOR, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
		} else {
			modificacionContrato = new ModificacionContrato();
			this.setStrTipoModificacion(this.strContratoTerminacion);
			this.setContrato(contratoSel);
			lstModificacionesContrato = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoTerminacion), contratoSel.getCodContrato(), 1);
			context.execute("PF('dlgContinuar').show();");
			intIndexOPContinuar = index;
			this.setStrMsgContinuar(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_TAB_CT_REGISTRAR_TERMINACION, getLocale()));
		}
	}

	public void registrarAdPrAlContrato(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}

		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}
		/**
		 * @pjqr 20180322 CU0308–FA03
		 */
		if (!validarVigencia(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MENSAJE_VIGENCIA_ANTERIOR, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
		} else {

			modificacionContrato = new ModificacionContrato();
			this.setStrTipoModificacion(this.strContratoAdProMod);
			this.setContrato(contratoSel);
			lstModificacionesContrato = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoAdProMod), contratoSel.getCodContrato(), 1);
			context.execute("PF('dlgContinuar').show();");
			intIndexOPContinuar = index;
			this.setStrMsgContinuar(TitlesBundleConstants.getStringMessagesBundle(
					TitlesBundleConstants.TTL_CT_ADICION_PRORROGA_ALCANCE_CONTRATO, getLocale()));
		}
	}

	/**
	 * 
	 * @param index
	 * @param contratoSel
	 */
	public void registrarAdPrAlContrato_1(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		Contrato cto = new Contrato();
		cto.setCodContrato(contratoSel.getCodContrato());
		List<ContratoExt> contra = ComunicacionServiciosCO.getContratoFiltro(cto);
		if (!contra.isEmpty()) {
			cto = contra.get(0);
		}
		contratoSel.setFlgActivo(cto.getFlgActivo());
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (isContratoInactivo(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgContratoNoActivo);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}

		if (!validaMaximaFechaProrroga(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_NO_PRORROGABLE, getLocale()) + " "
					+ llDiasLimiteProrrogaContrato + " " + MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_DIAS_PRORROGA, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}

		/**
		 * @pjqr 20180322 CU0308–FA03
		 */
		if (!validarVigencia_1(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MENSAJE_VIGENCIA_ANTERIOR, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
		} else {

			modificacionContrato = new ModificacionContrato();
			this.setStrTipoModificacion(this.strContratoAdProMod);
			this.setContrato(contratoSel);
			lstModificacionesContrato = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoAdProMod), contratoSel.getCodContrato(), 1);
			context.execute("PF('dlgContinuar').show();");
			intIndexOPContinuar = index;
			this.setStrMsgContinuar(TitlesBundleConstants.getStringMessagesBundle(
					TitlesBundleConstants.TTL_CT_ADICION_PRORROGA_ALCANCE_CONTRATO, getLocale()));
		}
	}

	public void registrarCesionContrato(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (isContratoInactivo(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgContratoNoActivo);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}
		/**
		 * @pjqr 20180322 CU0309:4
		 */
		if (!validarVigencia(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MENSAJE_VIGENCIA_ANTERIOR, getLocale()));
			context.execute("PF('dlgAccionesContrato').show();");
		} else {

			modificacionContrato = new ModificacionContrato();
			this.setStrNombrePersonaCesion("");
			personaCesionContrato = new PersonaExt();
			this.setStrTipoModificacion(this.strContratoCesion);
			this.setContrato(contratoSel);
			lstModificacionesContrato = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
					Long.valueOf(this.strContratoCesion), contratoSel.getCodContrato(), 1);

			context.execute("PF('dlgContinuar').show();");
			intIndexOPContinuar = index;
			this.setStrMsgContinuar(TitlesBundleConstants
					.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_CESION_CONTRATO, getLocale()));

			if (contrato.getCodSupervisor() != null && !contrato.getCodSupervisor().equals(0)) {
				consultarSupervisorByCodigoPersona(contrato.getCodSupervisor(), true, false);
			}
		}

	}

	public void consultarContrato(Integer index, ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		vinculacionSupervisor = new VinculacionExt();
		if (!lbIsRolValidoConsulta) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		inicializarContrato();
		this.setContrato(contratoSel);

		/* Consultar Modificaciones al contrato */
		lstConsultarCoModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(0,
				this.contrato.getCodContrato(), 1);
		if (entidadUsuario != null) {

			if (this.contrato.getCodDependenciaEntidad() != null) {
				DependenciaEntidadExt depBuscar = new DependenciaEntidadExt();
				depBuscar.setCodEntidad(Long.valueOf(entidadUsuario.getId()));
				depBuscar.setFlgActivo((short) 1);
				depBuscar.setLimitInit(0);
				depBuscar.setLimitEnd(1);
				depBuscar.setCodDependenciaEntidad(this.contrato.getCodDependenciaEntidad());
				List<DependenciaEntidadExt> lista = ComunicacionServiciosVin.getDependenciaEntidadFilter(depBuscar);
				if (lista != null && lista.size() > 0)
					strAreaEntidad = lista.get(0).getNombreDependencia();
			}
			this.strEntidadUsuario = entidadUsuario.getNombreEntidad();
		}
		if (contrato.getCodSupervisor() != null && !contrato.getCodSupervisor().equals(0)) {
			consultarSupervisorByCodigoPersona(contrato.getCodSupervisor(), true, false);
		}
		context.execute("PF('dlgContinuar').show();");
		intIndexOPContinuar = index;
		this.setStrMsgContinuar(TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.TTL_CT_CONSULTAR_CONTRATO, getLocale()));
	}

	public void registrarTerminacionManualContrato(ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (isContratoInactivo(contratoSel) && !lbIsRolAdminFUNC) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgContratoNoActivo);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
		if (!modificaContratoEntidadUsuarioEntidad(contratoSel)) {
			intIndextab = 0;
			return;
		}

		this.setContrato(contratoSel);
		if (personaCesionContrato == null)
			personaCesionContrato = new PersonaExt();
		context.execute("PF('dlgTerminacionManualContrato').show();");
	}

	public void terminacionManualContrato() {
		RequestContext context = RequestContext.getCurrentInstance();
		Contrato contratoRes;
		boolean lbResultadoModificacion;
		ModificacionContrato modificacion = new ModificacionContrato();
		modificacion.setCodTipoModificacionContrato(Integer.valueOf(this.strContratoTerminacionManual));
		modificacion.setCodContrato(contrato.getCodContrato());
		modificacion.setAudFechaActualizacion(new Date());
		modificacion.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
		modificacion.setAudCodRol(codAudCodRol);
		modificacion.setAudAccion(codAudAccionInsert);
		modificacion.setJustificacionModificacion("Cumplimiento del objeto del Contrato");
		lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(modificacion);
		if (lbResultadoModificacion) {
			contrato.setFlgActivo((short) 0);
			contratoRes = ComunicacionServiciosCO.setContrato(contrato);
			if (!contratoRes.isError()) {
				this.setStrMsgAccionesContrato(strMsgGuardarTerminacionManualExito);
				context.execute("PF('dlgAccionesContrato').show();");
				return;
			} else {
				this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato);
				context.execute("PF('dlgAccionesContrato').show();");
				return;
			}
		} else {
			this.setStrMsgAccionesContrato(strMsgGuardarErrorContrato);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}
	}

	public void MostrarDialogoEliminarContrato(ContratoExt contratoSel) {
		RequestContext context = RequestContext.getCurrentInstance();
		this.setContrato(contratoSel);
		this.setStrMsgAccionesContrato(MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MENSAJE_ELIMINAR, getLocale())
				.replace("%numeroContrato%", contratoSel.getNumeroContrato() + ""));
		context.execute("PF('dlgAccionContratoEliminar').show();");
	}

	public void confirmarEliminarContrato() {
		Contrato contratoRes;
		this.contrato.setFlgEliminado((short) 1);
		contratoRes = ComunicacionServiciosCO.setContrato(contrato);
		if (!contratoRes.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_ELIMINACION_EXITOSA);
			lstBuscarContratosPersona = ComunicacionServiciosCO.getContratoPersona(personaSelected.getCodPersona().longValue());
			return;
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
	}

	private void inicializarContrato() {
		contrato = new Contrato();
		this.setStrNombrePersonaCesion("");
		lstDependenciasEntidadSelect = new ArrayList<>();
		if (entidadUsuario != null) {
			DependenciaEntidadExt depEntidad = new DependenciaEntidadExt();
			depEntidad.setFlgActivo((short) 1);
			depEntidad.setCodEntidad(entidadUsuario.getId());
			List<DependenciaEntidadExt> lstDependenciasEntidad = ComunicacionServiciosVin
					.getDependenciaEntidadFilter(depEntidad);
			if (lstDependenciasEntidad != null && lstDependenciasEntidad.size() > 0) {
				for (DependenciaEntidadExt depEntidadSelect : lstDependenciasEntidad) {
					lstDependenciasEntidadSelect.add(new SelectItem(depEntidadSelect.getCodDependenciaEntidad(),
							depEntidadSelect.getNombreDependencia()));
				}
			}
		}
	}

	private void anularvariablesContrato() {
		contrato = null;
		lstModificacionesContrato = null;
		lbContratoDeshabilitaMonto = false;
		lbContratoDeshabilitaFecIni = false;
		lbContratoDeshabilitaFecFin = false;
		this.setStrNombreSupervisor("");
	}

	public void regresarBusqueda() {
		/*
		 * FacesContext context = FacesContext.getCurrentInstance(); Application
		 * application = context.getApplication(); ViewHandler viewHandler =
		 * application.getViewHandler(); UIViewRoot viewRoot =
		 * viewHandler.createView(context, context.getViewRoot().getViewId());
		 * context.setViewRoot(viewRoot); context.renderResponse();
		 */
		intIndextab = 0;
		anularvariablesContrato();
		this.setStsNombreTabContrato("Contrato");
		ocultarTabs(99);
		RequestContext context1 = RequestContext.getCurrentInstance();
		context1.reset("frContrataciones:viewTabContrataciones:idpnContratacionesTbRegistrarContrato");
		context1.reset("frContrataciones:viewTabContrataciones:idpnContratacionesTbRegistrarAdCto");
		context1.reset("frContrataciones:viewTabContrataciones:idpnContratacionesTbRegistrarProrrogaCto");
		context1.reset("frContrataciones:viewTabContrataciones:idpnContratacionesTbRegistrarAlcanceCto");
		context1.reset("frContrataciones:viewTabContrataciones:idpnContratacionesTbRegistrarSuspensionCto");
		context1.reset("frContrataciones:viewTabContrataciones:idpnContratacionesTbRegistrarTerminacionCto");
		context1.reset("frContrataciones:viewTabContrataciones:idpnContratacionesTbRegistrarAdPrAl");
		context1.reset("frContrataciones:viewTabContrataciones:idpnContratacionesTbRegistrarCesionCto");
		if (personaSelected != null)
			verContratosPorContratista(personaSelected);
	}

	public void obtenerDuracionContratos(Date fechaini, Date fechaFin) {
		if (fechaini != null && fechaFin != null) {
			Date fechaFinFinal;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaFin);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			fechaFinFinal = calendar.getTime();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			ChronoLocalDate from = ChronoLocalDate.from(formatter.parse(sdf.format(fechaini)));
			ChronoLocalDate to = ChronoLocalDate.from(formatter.parse(sdf.format(fechaFinFinal)));
			ChronoPeriod period = ChronoPeriod.between(from, to);
			int dias, meses, annos;
			dias = (int) period.get(DAYS);
			meses = (int) period.get(MONTHS);
			annos = (int) period.get(YEARS);
			meses = meses + (annos * 12);
			this.contrato.setDuracionContrato(dias);
			this.contrato.setDuracionContratoMeses(meses);
		} else {
			this.contrato.setDuracionContrato(0);
			this.contrato.setDuracionContratoMeses(0);
		}
	}

	/**
	 * @pjqr 20180322 CU0301–FA06
	 */
	public boolean validarContratoEntidad() {
		Contrato contratoBuscar = new Contrato();
		contratoBuscar.setLimitInit(0);
		contratoBuscar.setLimitEnd(10);
		contratoBuscar.setCodEntidad(this.contrato.getCodEntidad());
		contratoBuscar.setNumeroContrato(this.contrato.getNumeroContrato());
		List<ContratoExt> lstContrato = ComunicacionServiciosCO.getContratoFiltro(contratoBuscar);
		if (lstContrato != null && lstContrato.size() > 0)
			return false;
		return true;
	}

	public void selecionarPersona(PersonaExt persona) {
		RequestContext context = RequestContext.getCurrentInstance();
		if (!lbIsRolValidoContratos) {
			intIndextab = 0;
			this.setStrMsgAccionesContrato(strMsgUsuarioNoAutorizadoAccion);
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		}

		personaSelected = persona;
		boolean hojaVidaValida = validarHojaVidaAprobada();
		boolean lbtieneDerechos = contratistaTieneDerechosExclusividad();
		/*
		 * CU0301-FA08
		 */
		if (!hojaVidaValida) {
			context.execute("PF('dlgAccionesContrato').show();");
			return;
		} else if (lbtieneDerechos) {
			this.strMsgAccionesContrato = strMsgTieneDerechosExclusividad;
			context.execute("PF('dlgTieneDerechosExclusividad').show();");
			return;

		} else {
			context.execute("PF('dlg1').show();");
		}
	}

	public void seleccionarContrato(Contrato cont) {
		this.setContrato(cont);
	}

	private Date obtenerMaximaFechaProrroga(ContratoExt contratoSel) {
		List<ModificacionContratoExt> lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(0,
				contratoSel.getCodContrato(), 1);
		Date maxfecha = null;
		int indice = 0;
		if (lstModificaciones != null && lstModificaciones.size() > 0) {
			for (ModificacionContratoExt modificacion : lstModificaciones) {
				if (indice == 0)
					maxfecha = modificacion.getFechaFinModificacion();
				if (modificacion.getFechaFinModificacion() != null
						&& modificacion.getFechaFinModificacion().after(maxfecha))
					maxfecha = modificacion.getFechaFinModificacion();
			}
		}
		/*
		 * las modificaciones strContratoAdProMod(886) son Adición y/o Prorroga y/o
		 * Alcance: si las mismas tienen fecha inicio modificacion y fecha fin
		 * modificacion son prorrogas
		 */
		lstModificaciones = null;
		lstModificaciones = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
				Long.valueOf(this.strContratoAdProMod), contratoSel.getCodContrato(), 1);
		if (lstModificaciones != null && lstModificaciones.size() > 0) {
			for (ModificacionContratoExt modificacion : lstModificaciones) {
				try {
					if ((modificacion.getFechaInicioModificacion() != null
							&& modificacion.getFechaFinModificacion() != null)
							&& (modificacion.getFechaFinModificacion().after(maxfecha))) {
						maxfecha = modificacion.getFechaFinModificacion();
					}
				} catch (Exception e) {
				}

			}
		}
		return maxfecha;
	}

	private Date obtenerMaximaFechaProrroga() {
		Calendar calendar = Calendar.getInstance();
		List<ModificacionContratoExt> lstModificacionesPr = ComunicacionServiciosCO.getModificacionContratoPorTipoMod(
				Long.valueOf(this.strContratoProrroga), this.contrato.getCodContrato(), 1);
		List<ModificacionContratoExt> lstModificacionesPrAlAd = ComunicacionServiciosCO
				.getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoAdProMod),
						this.contrato.getCodContrato(), 1);
		if (lstModificacionesPrAlAd != null && lstModificacionesPrAlAd.size() > 0)
			lstModificacionesPr.addAll(lstModificacionesPrAlAd);
		Collections.sort(lstModificacionesPrAlAd);
		Date maxfecha = null;
		if (lstModificacionesPr != null && lstModificacionesPr.size() > 0) {
			for (ModificacionContratoExt modificacion : lstModificacionesPr) {
				if (modificacion.getFechaFinModificacion() != null) {
					maxfecha = modificacion.getFechaFinModificacion();
					break;
				}
			}
		}
		if (maxfecha == null)
			maxfecha = this.contrato.getFechaFin();
		calendar.setTime(maxfecha);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		maxfecha = calendar.getTime();
		return maxfecha;
	}

	private void obtenervalorActualContrato() {
		List<ModificacionContratoExt> lstModificacionesAdicion = ComunicacionServiciosCO
				.getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoAdicion),
						this.contrato.getCodContrato(), 1);
		List<ModificacionContratoExt> lstModificacionesAdPrAl = ComunicacionServiciosCO
				.getModificacionContratoPorTipoMod(Long.valueOf(this.strContratoAdProMod),
						this.contrato.getCodContrato(), 1);
		if (lstModificacionesAdPrAl != null && lstModificacionesAdPrAl.size() > 0)
			lstModificacionesAdicion.addAll(lstModificacionesAdPrAl);
		Collections.sort(lstModificacionesAdicion);
		if (lstModificacionesAdicion != null && lstModificacionesAdicion.size() > 0) {
			for (ModificacionContratoExt modificacion : lstModificacionesAdicion) {
				if (modificacion.getNuevoMontoContrato() != null) {
					llMontoActualContratoMod = modificacion.getNuevoMontoContrato().longValue();
					break;
				}
			}
		}
		if (llMontoActualContratoMod == null || llMontoActualContratoMod == 0 && contrato.getMonto() != null)
			llMontoActualContratoMod = contrato.getMonto().longValue();
	}

	public void consultarSupervisorByTipoNumeroIdentificacion(String pcodTipoIdentificacion,
			String pnumeroIdentificacion) {
		if (pcodTipoIdentificacion == null || "".equals(pcodTipoIdentificacion) || pnumeroIdentificacion == null
				|| "".equals(pnumeroIdentificacion)) {
			this.strMsgAccionesContrato = MessagesBundleConstants.getStringMessagesBundle(
					MessagesBundleConstants.DLG_CT_MSG_SUPERVISOR_FALTAN_PARAMETROS_BUSCAR, getLocale());
			RequestContext.getCurrentInstance().execute("PF('dlgRegistroContrato').show();");
			return;
		}

		PersonaExt personaBuscar = new PersonaExt();
		personaBuscar.setCodTipoIdentificacion(pcodTipoIdentificacion);
		personaBuscar.setNumeroIdentificacion(pnumeroIdentificacion);
		personaBuscar.setLimitInit(0);
		personaBuscar.setLimitEnd(10);
		PersonaExt persona = ComunicacionServiciosHV.persontipIdnumId(personaBuscar);
		if (persona != null && persona.getCodPersona() != null) {

			strNombreSupervisor = (persona.getPrimerNombre() != null ? persona.getPrimerNombre() : "") + " "
					+ (persona.getSegundoNombre() != null ? persona.getSegundoNombre() : "") + " "
					+ (persona.getPrimerApellido() != null ? persona.getPrimerApellido() : "") + " "
					+ (persona.getSegundoApellido() != null ? persona.getSegundoApellido() : "");
			consultarSupervisorByCodigoPersona(persona.getCodPersona(), false, true);
			vinculacionSupervisor.setCodTipoIdentificacion(Long.parseLong(pcodTipoIdentificacion));

		} else {
			strNombreSupervisor = "";
			this.strMsgAccionesContrato = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MSG_SUPERVISOR_NO_ENCONTRADO, getLocale());
			vinculacionSupervisor.setNombreCargo(null);
			vinculacionSupervisor.setCodGrado(null);
			vinculacionSupervisor.setNombreDependencia(null);
			vinculacionSupervisor.setCodTipoIdentificacion(Long.parseLong(pcodTipoIdentificacion));
			RequestContext.getCurrentInstance().execute("PF('dlgRegistroContrato').show();");
		}
	}

	private void consultarSupervisorByCodigoPersona(BigDecimal codigoPersona, boolean consultarPersonaVinculacion,
			boolean isBuscar) {
		VinculacionExt vinculacionBuscar = new VinculacionExt();
		Parametrica pGrado;
		PersonaExt personaV;
		vinculacionBuscar.setCodPersona(codigoPersona);
		vinculacionBuscar.setFlgActivo((short) 1);
		vinculacionBuscar.setCodEntidad(entidadUsuario.getId());
		List<VinculacionExt> lstvinculacionSupervisor = ComunicacionServiciosVin
				.getvinculacionactual(vinculacionBuscar);
		if (lstvinculacionSupervisor != null && lstvinculacionSupervisor.size() > 0) {
			vinculacionSupervisor = lstvinculacionSupervisor.get(0);
			if (vinculacionSupervisor.getCodGrado() != null) {
				pGrado = ComunicacionServiciosSis
						.getParametricaporId(BigDecimal.valueOf(vinculacionSupervisor.getCodGrado()));
				strNombreGradoVinculacionSupervisor = pGrado.getValorParametro();
			}
			if (consultarPersonaVinculacion) {
				personaV = ComunicacionServiciosHV.getPersonaporIdExt(codigoPersona.longValue());
				vinculacionSupervisor.setCodTipoIdentificacion(Long.parseLong(personaV.getCodTipoIdentificacion()));
				vinculacionSupervisor.setNumeroIdentificacion(personaV.getNumeroIdentificacion());
				vinculacionSupervisor.setNombreDocumento(personaV.getNombreTipoDocuento());
				strNombreSupervisor = (personaV.getPrimerNombre() != null ? personaV.getPrimerNombre() : "") + " "
						+ (personaV.getSegundoNombre() != null ? personaV.getSegundoNombre() : "") + " "
						+ (personaV.getPrimerApellido() != null ? personaV.getPrimerApellido() : "") + " "
						+ (personaV.getSegundoApellido() != null ? personaV.getSegundoApellido() : "");
			}
		} else {
			strNombreSupervisor = "";
			this.strMsgAccionesContrato = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MSG_SUPERVISOR_NO_ENCONTRADO, getLocale());
			vinculacionSupervisor.setNombreCargo(null);
			vinculacionSupervisor.setCodGrado(null);
			vinculacionSupervisor.setNombreDependencia(null);
			if (isBuscar)
				RequestContext.getCurrentInstance().execute("PF('dlgRegistroContrato').show();");
		}
	}

	public void counsultaPersonaCesionByTipoNumeroIdentificacion(String pcodTipoIdentificacion,
			String pnumeroIdentificacion) {
		String h = personaCesionContrato.getCodTipoIdentificacion();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		int tienePunto = pnumeroIdentificacion.indexOf(".");
		Long esNumero = null;
		try {
			esNumero = Long.parseLong(pnumeroIdentificacion);
		} catch (NumberFormatException e) {
			esNumero = null;
		}
		if (esNumero == null && !h.equals("42")) {
			this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_NO_CARACTERES, getLocale()));
			personaCesionContrato.setNumeroIdentificacion("");
			requestContext.update("idpnContratacionesTbRegistrarCesionCto:frmCesionContratoid:NumDocCesion");
			requestContext.execute("PF('dlgCesionContrato_01').show();");
			return;
		}
		if (tienePunto != -1 && h.equals("42")) {
			this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_CARACTER_NO_PERMITIDO, getLocale()));
			personaCesionContrato.setNumeroIdentificacion("");
			requestContext.update("idpnContratacionesTbRegistrarCesionCto:frmCesionContratoid:NumDocCesion");
			requestContext.execute("PF('dlgCesionContrato_01').show();");
			return;
		}
		PersonaExt personaBuscar = new PersonaExt();
		personaBuscar.setCodTipoIdentificacion(pcodTipoIdentificacion);
		personaBuscar.setNumeroIdentificacion(pnumeroIdentificacion);
		personaBuscar.setLimitInit(0);
		personaBuscar.setLimitEnd(10);
		List<PersonaExt> lstpersonasBuscar = ComunicacionServiciosHV.getpersonaFiltro(personaBuscar);
		if (lstpersonasBuscar != null && lstpersonasBuscar.size() > 0) {
			personaBuscar = lstpersonasBuscar.get(0);
			if (personaBuscar != null && personaBuscar.getCodPersona() != null
					&& personaBuscar.getCodPersona().equals(contrato.getCodPersona())) {
				this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_NO_CEDER_A_SI_MISMO, getLocale()));
				requestContext.execute("PF('dlgCesionContrato_01').show();");
				strNombrePersonaCesion = "";
				personaCesionContrato.setCodUsuario(null);
				personaCesionContrato.setNumeroIdentificacion(null);
				return;
			}
			strNombrePersonaCesion = (personaBuscar.getPrimerNombre() != null ? personaBuscar.getPrimerNombre() : "")
					+ " " + (personaBuscar.getSegundoNombre() != null ? personaBuscar.getSegundoNombre() : "") + " "
					+ (personaBuscar.getPrimerApellido() != null ? personaBuscar.getPrimerApellido() : "") + " "
					+ (personaBuscar.getSegundoApellido() != null ? personaBuscar.getSegundoApellido() : "");
			personaCesionContrato.setNombreUsuario(personaBuscar.getNombreUsuario());
			personaCesionContrato.setCodPersona(personaBuscar.getCodPersona());
			personaCesionContrato.setCodUsuario(personaBuscar.getCodUsuario());
			if (personaCesionContrato.getCodUsuario() == null) {
				Usuario usuarioFind = new Usuario();
				Usuario usuarioEncontrado;
				BigDecimal codPersona = personaCesionContrato.getCodPersona();
				usuarioFind.setCodPersona(codPersona);
				usuarioEncontrado = ComunicacionServiciosHV.getusuarioid(usuarioFind);
				if (usuarioEncontrado != null && usuarioEncontrado.getCodUsuario() != null) {
					personaCesionContrato.setCodUsuario(usuarioEncontrado.getCodUsuario());
					this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_BUSQUEDA_EXITOSA, getLocale()));
					requestContext.execute("PF('dlgCesionContrato_01').show();");
				}
			}
		} else {
			this.setStrMsgMensajesvalidacionesProrroga(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_NO_RESULTADOS, getLocale()));
			requestContext.execute("PF('dlgCesionContrato_01').show();");
			strNombrePersonaCesion = "";
			personaCesionContrato.setCodUsuario(null);
			personaCesionContrato.setNumeroIdentificacion(null);
		}
	}

	/*
	 * valiada si el contratista tiene hoja de vida aprobada por Jefe de Contratos u
	 * operador de contratos de la entidad
	 */
	private boolean validarHojaVidaAprobada() {
		if (strValidaHV != null && !"".equals(strValidaHV) && "N".equals(strValidaHV)) {
			System.out.println("NO validarHojaVidaAprobada:");
			return true;
		}
		Date fechaAprobacionHV;
		/*
		 * El contratista debe tener usuario. con el codigo de la persona consulta el
		 * usuario
		 */
		/*
		 * CU01 PRE2 El contratista debe tener usuario activo vinculado a la entidad y
		 * tener en el sistema su Hoja de vida.
		 */
		UsuarioExt usuarioContratista = ComunicacionServiciosHV.getusuarioext(
				personaSelected.getCodPersona().longValue(), personaSelected.getCodEntidad().longValue());
		if (usuarioContratista == null || usuarioContratista.getCodPersona() == null) {
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CONTRATOS_USUARIO_INACTIVO, getLocale()));
			return false;
		}
		/* El contratista debe tener hoja de vida aprobada */
		HojaVidaExt hoja = new HojaVidaExt();
		hoja.setCodPersona(personaSelected.getCodPersona());
		hoja.setCodEntidad(personaSelected.getCodEntidad().longValue());
		hoja.setLimitInit(0);
		hoja.setLimitEnd(10);
		List<HojaVidaExt> lstHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hoja);

		if (lstHojaVida != null && lstHojaVida.size() > 0) {
			hoja = lstHojaVida.get(0);
			if (hoja != null && hoja.getFlgAprobado()) {
				/*
				 * si la hoja de vida está aprobada con el codigo del aprobador busco los roles
				 */
				PersonaExt persona = new PersonaExt();
				persona.setCodUsuario(hoja.getCodUsuarioAprueba());
				persona.setCodEntidad(personaSelected.getCodEntidad());
				List<RolExt> lstRoles = ComunicacionServiciosSis.getRolesSistemaPorPersona(persona);
				if (lstRoles != null && lstRoles.size() > 0) {
					boolean rolValido = false;
					for (RolExt rol : lstRoles) {
						if (rol.getNombre().equals(RolDTO.JEFE_CONTRATOS)
								|| rol.getNombre().equals(RolDTO.OPERADOR_CONTRATOS)) {
							rolValido = true;
							break;
						}
					}
					if (!rolValido) {
						this.setStrMsgAccionesContrato(MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.DLG_CONTRATOS_HOJA_SIN_APROBACION, getLocale()));
						return false;
					}
					/* Se valida aprbación no menor a tres meses */
					if (hoja.getFechaAprobacion() != null) {
						fechaAprobacionHV = hoja.getFechaAprobacion();
					} else {
						fechaAprobacionHV = new Date();
					}
					Calendar inicio = new GregorianCalendar();
					Calendar fin = new GregorianCalendar();
					inicio.setTime(fechaAprobacionHV);
					fin.setTime(new Date());
					int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
					int difM = (difA * 12 + fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH)) + 1;
					if (difM > tiempoMesesAprobacion) {
						this.setStrMsgAccionesContrato(MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.DLG_CONTRATOS_TIEMPO_SIN_APROBACION,
										getLocale())
								.replace("%TIEMPO%", tiempoMesesAprobacion + ""));
						return false;
					}
				} else {
					this.setStrMsgAccionesContrato(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.DLG_CONTRATOS_USUARIO_SIN_ROLES, getLocale()));
					this.setStrMsgAccionesContrato(
							MessagesBundleConstants
									.getStringMessagesBundle(
											MessagesBundleConstants.DLG_CONTRATOS_TIEMPO_SIN_APROBACION, getLocale())
									.replace("%TIEMPO%", tiempoMesesAprobacion + ""));
					return false;
				}
			} else {
				this.setStrMsgAccionesContrato(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.DLG_CONTRATOS_HOJA_SIN_APROBACION, getLocale()));
				this.setStrMsgAccionesContrato(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.DLG_CONTRATOS_TIEMPO_SIN_APROBACION,
								getLocale())
						.replace("%TIEMPO%", tiempoMesesAprobacion + ""));
				return false;
			}
		} else {
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CONTRATOS_SIN_HOJA_VIDA, getLocale()));
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CONTRATOS_TIEMPO_SIN_APROBACION, getLocale())
					.replace("%TIEMPO%", tiempoMesesAprobacion + ""));
			return false;
		}
		return true;
	}

	/*
	 * Valida si el contratis tiene derechos de exclusividad: Devuelve true si tiene
	 * derechos, false en caso contrario
	 */
	private boolean contratistaTieneDerechosExclusividad() {
		List<ContratoExt> lstContratos = ComunicacionServiciosCO
				.getContratoPersona(personaSelected.getCodPersona().longValue());
		for (ContratoExt cont : lstContratos) {
			if (cont.getDerechoExclusividad() == 1) {
				return true;
			}
		}
		return false;
	}

	public void imprimir() {

		InitialContext initialContext;
		DataSource dataSource = null;
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:/SIGEP2DS");
			String jasperPath = "/resources/jasper/contratos/reporteContratos.jrxml";
			String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
			String subReportDir = "/resources/jasper/contratos/";
			String imageDir = "/resources/images/";
			subReportDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath(subReportDir);
			imageDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath(imageDir);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("P_CODIGO_CONTRATO", String.valueOf((this.contrato.getCodContrato())));
			parameters.put("P_SUBREPORT_DIR", subReportDir + "/");
			parameters.put("P_IMAGE_FOLDER", imageDir + "/");

			JasperReport report = JasperCompileManager.compileReport(relativePath);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline; filename=\"fileName.pdf\"");
			ServletOutputStream stream;
			try {
				stream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(print, stream);
				stream.flush();
				stream.close();
				FacesContext.getCurrentInstance().responseComplete();
				dataSource.getConnection().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void consultarReversarModificacion(ModificacionContrato mod, String proceso) {
		/*
		 * proceso D = Detalle R = Reversa
		 */
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (proceso.equals("R")) {
			this.setLbconsultarReversar(true);
		} else {
			this.setLbconsultarReversar(false);
		}
		usuarioConsultaReversa = null;
		this.modificacionConsultaReversaSelected = mod;
		lbEliminaModPosteriores = false;
		strMsgReversaModificacionMsg = strMsgReversaModificacionUnica;
		lstModificacionesContratoReversa = new ArrayList<>();
		if (proceso.equals("R")) {
			lbUltimaModificacion = validaIsUltimaModificion(modificacionConsultaReversaSelected);
			if (!lbUltimaModificacion) {/* si no es la ultima modificacion */
				if (lbRolValidoReversa) {/* verifico si el rol si puede eliminar y continua */
					lbEliminaModPosteriores = true;
					strMsgReversaModificacionMsg = strMsgReversaModificacionVarias;
					/* lISTA DE REVERSAS POSTERIORRES */
					List<ModificacionContratoExt> lstModificacionesReversar = ComunicacionServiciosCO
							.getModificacionContratoPorTipoMod(0, modificacionConsultaReversaSelected.getCodContrato(),
									1);
					for (ModificacionContratoExt modifica : lstModificacionesReversar) {
						if (modificacionConsultaReversaSelected.getCodModificacionContrato() <= modifica
								.getCodModificacionContrato()) {
							lstModificacionesContratoReversa.add(modifica);
						}
					}
				} else {/* si no es rol valido saca mensajes */
					this.setStrMsgAccionesContrato(MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_CT_NO_PUEDE_BORRAR, getLocale()));
					requestContext.execute("PF('dlgConsultaContrato').show();");
					return;
				}
			}
		}

		personaCesionContrato = new PersonaExt();
		if (usuarioConsultaReversa == null) {
			Usuario usuarioBuscardto = new Usuario();
			usuarioBuscardto.setCodUsuario((mod.getAudCodUsuario()));
			usuarioConsultaReversa = ComunicacionServiciosHV.getusuarioid(usuarioBuscardto);
			if (usuarioConsultaReversa != null) {
				PersonaExt personaBuscar = new PersonaExt();
				personaBuscar.setCodPersona(usuarioConsultaReversa.getCodPersona());
				personaBuscar.setLimitInit(0);
				personaBuscar.setLimitEnd(10);
				List<PersonaExt> lstpersonasBuscar = ComunicacionServiciosHV.getpersonaFiltro(personaBuscar);
				if (lstpersonasBuscar != null && lstpersonasBuscar.size() > 0) {
					personaUsuarioConsultaContrato = lstpersonasBuscar.get(0);
				}
			}
		}
		if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
				.equals(this.strContratoAdicion)) {
			requestContext.execute("PF('dlgConsultaReversaAdicion').show();");
		} else if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
				.equals(this.strContratoProrroga)) {
			requestContext.execute("PF('dlgConsultaReversaProrroga').show();");
		} else if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
				.equals(this.strContratoAlcance)) {
			requestContext.execute("PF('dlgConsultaReversaAlcance').show();");
		} else if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
				.equals(this.strContratoSuspension)) {
			requestContext.execute("PF('dlgConsultaReversaSuspension').show();");
		} else if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
				.equals(this.strContratoTerminacion)) {
			requestContext.execute("PF('dlgConsultaReversaTerminacion').show();");
		} else if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
				.equals(this.strContratoCesion)) {
			requestContext.execute("PF('dlgConsultaReversaCesion').show();");
		} else if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
				.equals(this.strContratoAdProMod)) {
			requestContext.execute("PF('dlgConsultaReversaAdPrAl').show();");
		} else {
			this.setStrMsgAccionesContrato(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_ACCION_NO_PERMITIDA, getLocale()));
			requestContext.execute("PF('dlgConsultaContrato').show();");
		}
	}

	public void eliminarModificacionContrato() {
		boolean lbResultadoModificacion;
		Integer codAudCodRol = 3, codAudAccion = 1;
		Contrato contratoRes;
		if (!lbEliminaModPosteriores) {/* si es la ultima modificacion la elimina */
			modificacionConsultaReversaSelected.setFlgActivo((short) 0);
			modificacionConsultaReversaSelected.setAudFechaActualizacion(new Date());
			modificacionConsultaReversaSelected.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
			modificacionConsultaReversaSelected.setAudCodRol(codAudCodRol);
			modificacionConsultaReversaSelected.setAudAccion(codAudAccion);
			/* Reversa sesion */
			if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
					.equals(this.strContratoCesion)) {
				if (modificacionConsultaReversaSelected.getMontoRegistroContrato() != null) {
					contrato.setMonto(
							BigDecimal.valueOf(modificacionConsultaReversaSelected.getMontoRegistroContrato()));
				}
				if (modificacionConsultaReversaSelected.getCodContratistaInicial() != null) {
					contrato.setCodPersona(
							BigDecimal.valueOf(modificacionConsultaReversaSelected.getCodContratistaInicial()));
				}
			}
			/* reversa prorroga- reversa suspension - reversa terminacion */
			if (modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
					.equals(this.strContratoProrroga)
					|| modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
							.equals(this.strContratoSuspension)
					|| modificacionConsultaReversaSelected.getCodTipoModificacionContrato().toString()
							.equals(this.strContratoTerminacion)) {
				contrato.setFechaFin(modificacionConsultaReversaSelected.getFechaFinRegistroContrato());
			}

			lbResultadoModificacion = ComunicacionServiciosCO
					.setModificacionContrato(modificacionConsultaReversaSelected);
			ComunicacionServiciosCO.setContrato(contrato);
			if (lbResultadoModificacion) {
				contratoRes = ComunicacionServiciosCO.setContrato(contrato);
				if (!contratoRes.isError()) {
					FacesContext.getCurrentInstance().addMessage("idMsgConsultaPersonas",
							new FacesMessage("Modificacion Reversa exitosa"));
					regresarBusqueda();
				}
			}
		} else {/* si no es laúltima modificación se eliminan las posterires */
			lbResultadoModificacion = true;
			List<ModificacionContratoExt> lstModificacionesReversar = ComunicacionServiciosCO
					.getModificacionContratoPorTipoMod(0, modificacionConsultaReversaSelected.getCodContrato(), 1);
			for (ModificacionContratoExt mod : lstModificacionesReversar) {
				if (modificacionConsultaReversaSelected.getCodModificacionContrato() <= mod
						.getCodModificacionContrato()) {
					mod.setFlgActivo((short) 0);
					mod.setAudFechaActualizacion(new Date());
					mod.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
					mod.setAudCodRol(codAudCodRol);
					mod.setAudAccion(codAudAccionDelete);
					/* Reversa sesion */
					if (mod.getCodTipoModificacionContrato().toString().equals(this.strContratoCesion)) {
						if (mod.getMontoRegistroContrato() != null) {
							contrato.setMonto(BigDecimal.valueOf(mod.getMontoRegistroContrato()));
						}
						if (mod.getCodContratistaInicial() != null) {
							contrato.setCodPersona(BigDecimal.valueOf(mod.getCodContratistaInicial()));
						}
					}
					/* reversa prorroga- reversa suspension - reversa terminacion */
					if (mod.getCodTipoModificacionContrato().toString().equals(this.strContratoProrroga)
							|| mod.getCodTipoModificacionContrato().toString().equals(this.strContratoSuspension)
							|| mod.getCodTipoModificacionContrato().toString().equals(this.strContratoTerminacion)) {
						contrato.setFechaFin(mod.getFechaFinRegistroContrato());
					}
					lbResultadoModificacion = ComunicacionServiciosCO.setModificacionContrato(mod);
					contratoRes = ComunicacionServiciosCO.setContrato(contrato);
				}
			}
			if (lbResultadoModificacion) {
				FacesContext.getCurrentInstance().addMessage("idMsgConsultaPersonas",
						new FacesMessage("Modificaciones reversadas con éxito"));
				regresarBusqueda();
			}

		}
	}

	/* valida si l modificacion es la ultima para el contrato */
	private boolean validaIsUltimaModificion(ModificacionContrato modificacion) {

		List<ModificacionContratoExt> lstModificacionesReversar = ComunicacionServiciosCO
				.getModificacionContratoPorTipoMod(0, modificacionConsultaReversaSelected.getCodContrato(), 1);
		for (ModificacionContratoExt mod : lstModificacionesReversar) {
			if (modificacion.getCodModificacionContrato() < mod.getCodModificacionContrato())
				return false;
		}
		return true;

	}

	/* mira si el rol que estpa eliminando la modificación es válido */
	private boolean rolvalidoReversaModificacion() {
		List<Parametrica> lstRolesReversaModificaciones;
		try {
			if (lbIsRolAdminFUNC)
				return true;
			lstRolesReversaModificaciones = ComunicacionServiciosSis
					.getParametricaPorIdPadre(TipoParametro.ROL_REVERSA_MODIFICACIONES.getValue());
			for (Parametrica parametro : lstRolesReversaModificaciones) {
				if (codAudCodRol == Integer.valueOf(parametro.getValorParametro())) {
					return true;
				}
			}
		} catch (Exception z) {
			return false;
		}
		return false;
	}

	/* FUNCIONES UTILIDADES */

	/**
	 * Valida si la fechaIni 1 es mayor a la fechaFin, si es asi retorna true,
	 * retorna false si es igual o menor
	 * 
	 * @param fechaIni
	 * @param fechaFin
	 * @return
	 */
	private boolean validaFechaAfter(Date fechaIni, Date fechaFin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			/* se parsean as fechas para quitar las horas */
			fechaIni = sdf.parse(sdf.format(fechaIni));
			fechaFin = sdf.parse(sdf.format(fechaFin));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (fechaIni == fechaFin) {
			return true;
		} else if (fechaIni.after(fechaFin))
			return false;
		return true;
	}

	private Integer obtenerDiferenciaDiasFechas(Date feIni, Date feFin) {
		Integer linDias = 0;
		linDias = (int) ((feFin.getTime() - feIni.getTime()) / 86400000);
		return linDias + 1;
	}

	private void ocultarTabs(int index) {
		/* Buscar */
		if (index == 0) {
			lbIsTabBuscar = true;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
			/* Registrar-Modificar */
		} else if (index == 1) {
			lbIsTabBuscar = false;
			lbIsTabContrato = true;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
			/* Adicion */
		} else if (index == 2) {
			lbIsTabBuscar = false;
			lbIsTabContrato = false;
			lbIsTabAdicion = true;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
		}
		/* Prorroga */
		else if (index == 3) {
			lbIsTabBuscar = false;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = true;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
		}
		/* Alcance */
		else if (index == 4) {
			lbIsTabBuscar = false;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = true;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
		}
		/* Suspension */
		else if (index == 5) {
			lbIsTabBuscar = false;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = true;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
		}
		/* Terminacion */
		else if (index == 6) {
			lbIsTabBuscar = false;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = true;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
		}
		/* Mod pro Alcance */
		else if (index == 7) {
			lbIsTabBuscar = false;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = true;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
		}
		/* Cesion */
		else if (index == 8) {
			lbIsTabBuscar = false;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = true;
			lbIsTabConsultar = false;
		}
		/* Consulta */
		else if (index == 9) {
			lbIsTabBuscar = false;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = true;
		}
		/* Volver */
		else if (index == 99) {
			lbIsTabBuscar = true;
			lbIsTabContrato = false;
			lbIsTabAdicion = false;
			lbIsTabProrroga = false;
			lbIsTabAlcance = false;
			lbIsTabSuspension = false;
			lbIsTabTerminacion = false;
			lbIsTabAdPrAl = false;
			lbIsTabCesion = false;
			lbIsTabConsultar = false;
		}
	}

	private void limpiarmensajes() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> it = context.getMessages();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
	}

	private void inicializarMensajesProperties() {
		Parametrica parametrica;
		strMsgGuardarExitoContrato = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_GUARDAR_CONTRATO_EXITO, getLocale());
		strMsgIngresarCriterioBusqueda = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_CRITERIO_BUSQUEDA, getLocale());
		strMsgGuardarErrorContrato = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_GUARDAR_CONTRATO_ERROR, getLocale());
		strMsgTieneDerechosExclusividad = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_TIENE_DERECHO_EXCLUSIVIDAD, getLocale());
		strMsgModificarContratoExito = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_MODIFICAR_CONTRATO_EXITO, getLocale());
		strMsgGuardarAdicionExito = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_GUARDAR_ADICION_EXITO, getLocale());
		strMsgGuardarProrrogaExito = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_GUARDAR_PRORROGA_EXITO, getLocale());
		strMsgGuardarAlcanceExito = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_GUARDAR_ALCANCE_EXITO, getLocale());
		strMsgGuardarSuspensionExito = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_GUARDAR_SUSPENSION_EXITO, getLocale());
		strMsgGuardarTerminacionExito = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_GUARDAR_TERMINACION_EXITO, getLocale());
		strMsgGuardarCesionExito = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_MSG_GUARDAR_CESION_EXITO, getLocale());
		strMsgGuardarTerminacionManualExito = TitlesBundleConstants.getStringMessagesBundle(
				TitlesBundleConstants.LBL_CT_MSG_GUARDAR_TERMINACION_MANUAL_EXITO, getLocale());
		strMsgReversaModificacionUnica = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_REVERSAR_CONTRATO, getLocale());
		strMsgReversaModificacionVarias = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_REVERSAR_MODIFICACIONES, getLocale());
		strLabelFormularioAgregarSuspension = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_FRM_SUSPENSION, getLocale());
		strLabelFormularioQuitarSuspension = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_FRM_LEVANTAR_SUSPENSION, getLocale());
		strLabelFechaFinSuspension = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_FECHA_FIN_SUSPENSION, getLocale());
		strLabelFechaFinQuitarSuspension = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_FECHA_FIN_SUSPENSION_3, getLocale());

		strMsgVigenciaAnterior = MessagesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.DLG_CT_MENSAJE_VIGENCIA_ANTERIOR, getLocale());
		strMsgBusquedaNoResultados = MessagesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.DLG_CT_MSG_BUSQUEDA_NO_RESULTADOS, getLocale());
		strMsgNoContratosRegistrados = MessagesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.DLG_CT_MSG_NO_CONTRATOS_SIGEPII, getLocale());
		strMsgUsuarioNoAutorizadoAccion = MessagesBundleConstants.getStringMessagesBundle(
				TitlesBundleConstants.DLG_CT_MSG_USUARIO_NO_AUTORIZADO_ACCION_SIGEPII, getLocale());
		strMsgContratoNoActivo = MessagesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.DLG_CT_MSG_CONTRATO_NO_ACTIVO, getLocale());
		strMsgEntidadDiferenteUsuEntidad = MessagesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.DLG_CT_MSG_CONTRATO_DIF_USUENTIDAD, getLocale());
		strMsgNoRegistrarExclusividad = MessagesBundleConstants.getStringMessagesBundle(
				TitlesBundleConstants.DLG_CT_MSG_CONTRATO_NO_REGISTRO_EXCLUSIVIDAD, getLocale());
		strMensajeCorregirContrato = TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_CT_TITULO_MODIFICAR, getLocale());
		strMensajeRegistrarContrato = TitlesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.LBL_CT_TITULO_REGISTRO, getLocale());
		strMsgBusquedaExitosa = MessagesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.DLG_CT_BUSQUEDA_EXITOSA, getLocale());

		try {
			parametrica = ComunicacionServiciosSis.getParametricaIntetos("P_CT_TIEMPO_MESES_APRUEBA_HV");
			if (parametrica != null && parametrica.getValorParametro() != null
					&& !"".equals(parametrica.getValorParametro())) {
				tiempoMesesAprobacion = Long.valueOf(parametrica.getValorParametro());
			} else
				tiempoMesesAprobacion = 2L;

		} catch (Exception z) {
			tiempoMesesAprobacion = 2L;
		}
		try {
			parametrica = ComunicacionServiciosSis.getParametricaIntetos("P_CT_VALIDA_HV");
			if (parametrica != null && parametrica.getValorParametro() != null
					&& !"".equals(parametrica.getValorParametro()))
				strValidaHV = parametrica.getValorParametro();
			if (strValidaHV == null || "".equals(strValidaHV))
				strValidaHV = "S";
		} catch (Exception z) {
			strValidaHV = "S";
		}
		try {
			parametrica = ComunicacionServiciosSis.getParametricaIntetos("P_CT_DIAS_LIMITE_PRORROGA_CONTRATO");
			if (parametrica != null && parametrica.getValorParametro() != null
					&& !"".equals(parametrica.getValorParametro())) {
				llDiasLimiteProrrogaContrato = Long.valueOf(parametrica.getValorParametro());
			} else
				llDiasLimiteProrrogaContrato = 30L;

		} catch (Exception z) {
			llDiasLimiteProrrogaContrato = 30L;
		}

		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		liParTipoVincluacionContratista = Integer.valueOf(TipoParametro.TIPO_VINCULACION_CONTRATISTA.getValue());
		liTipoFuenteFinanciacionNacion = Integer.valueOf(TipoParametro.TIPO_FUENTE_FINANCIACION_NACION.getValue());
		liTipoFuenteFinanciacionPropio = Integer.valueOf(TipoParametro.TIPO_FUENTE_FINANCIACION_PROPIOS.getValue());
		liAdmonRecursosNoAplica = Integer.valueOf(TipoParametro.TIPO_ADMON_RECURSOS_CONTRATO_NO_APLICA.getValue());

		try {
			parametrica = ComunicacionServiciosSis
					.getParametricaporId(BigDecimal.valueOf(TipoParametro.CT_COD_MONEDA_PESOS.getValue()));
			if (parametrica != null && parametrica.getValorParametro() != null
					&& !"".equals(parametrica.getValorParametro()))
				liMonedaPesos = Integer.valueOf(parametrica.getValorParametro());
			else
				liMonedaPesos = 0;
		} catch (Exception z) {
			liMonedaPesos = 0;
		}
		try {
			parametrica = ComunicacionServiciosSis
					.getParametricaporId(BigDecimal.valueOf(TipoParametro.CT_COD_MONEDA_EURO.getValue()));
			if (parametrica != null && parametrica.getValorParametro() != null
					&& !"".equals(parametrica.getValorParametro()))
				liMonedaEuros = Integer.valueOf(parametrica.getValorParametro());
			else
				liMonedaEuros = 0;
		} catch (Exception z) {
			liMonedaEuros = 0;
		}
		try {
			parametrica = ComunicacionServiciosSis
					.getParametricaporId(BigDecimal.valueOf(TipoParametro.CT_COD_MONEDA_DOLAR.getValue()));
			if (parametrica != null && parametrica.getValorParametro() != null
					&& !"".equals(parametrica.getValorParametro()))
				liMonedaDolar = Integer.valueOf(parametrica.getValorParametro());
			else
				liMonedaDolar = 0;
		} catch (Exception z) {
			liMonedaDolar = 0;
		}

		liTipoDocPasaporte = Integer.valueOf(TipoParametro.TIPO_DOCUMENTO_PASAPORTE.getValue());
		;
	}

	protected void finalizarConversacion(boolean redireccionarAIndex, String mensajeConfirmacion,
			String parametrosAdicionales) throws IOException {
		try {
			conversation.end();
		} catch (Exception e) {
		}
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String path = "/index.xhtml";
		if (!redireccionarAIndex) {
			path = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
		}
		String mensaje = "";
		if (mensajeConfirmacion != null && !mensajeConfirmacion.isEmpty()) {
			mensaje = "&dialog=" + mensajeConfirmacion;
		}
		String parametros = "?faces-redirect=true";
		if (parametrosAdicionales != null && !parametrosAdicionales.isEmpty()) {
			if (!parametrosAdicionales.startsWith("&")) {
				parametros = parametros + "&";
			}
			parametros = parametros + parametrosAdicionales;
		}
		context.redirect(context.getRequestContextPath() + "/" + ConfigurationBundleConstants.aliasSitio() + path
				+ parametros + mensaje);
	}

	public void volverBusqueda() {
		lstBuscarContratosPersona = null;
		strMsgContratosRegistradosPersona = "";
		RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
	}

	public String consultarPersona(long id) {
		Persona persona = ComunicacionServiciosHV.getPersonaPorId(id);
		return persona.getPrimerNombre() + " " + (persona.getSegundoNombre() == null ? "" : persona.getSegundoNombre())
				+ " " + persona.getPrimerApellido() + " "
				+ (persona.getSegundoApellido() == null ? "" : persona.getSegundoApellido());
	}

	/**
	 * Método que valida si la fuente de financiación es 1 o 2 no aplica
	 * administración de recursos
	 */
	public void cambioFuenteFinanciacion() {

		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (contrato.getCodFuenteFinanciacion() != null) {
			if (contrato.getCodFuenteFinanciacion().equals(liTipoFuenteFinanciacionNacion)
					|| contrato.getCodFuenteFinanciacion().equals(liTipoFuenteFinanciacionPropio)) {
				this.contrato.setCodAdministracionRecurso(liAdmonRecursosNoAplica.longValue());
				this.strMsgAccionesContrato = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.DLG_CT_MSG_FUENTE_FINANCIACION, getLocale());
				this.adminRecursos = false;
				requestContext.execute("PF('dlgRegistroContrato').show();");
			} else
				this.adminRecursos = true;
		}

	}

	private boolean adminRecursos = true;

	public boolean getAdminRecursos() {
		return adminRecursos;
	}

	public void setAdminRecursos(boolean adminRecursos) {
		this.adminRecursos = adminRecursos;
	}

	public String getStrLabelFechaDefFrmSuspension() {
		return strLabelFechaDefFrmSuspension;
	}

	public void setStrLabelFechaDefFrmSuspension(String strLabelFechaDefFrmSuspension) {
		this.strLabelFechaDefFrmSuspension = strLabelFechaDefFrmSuspension;
	}

	public Integer getLiMonedaPesos() {
		return liMonedaPesos;
	}

	public void setLiMonedaPesos(Integer liMonedaPesos) {
		this.liMonedaPesos = liMonedaPesos;
	}

	public Integer getLiMonedaDolar() {
		return liMonedaDolar;
	}

	public void setLiMonedaDolar(Integer liMonedaDolar) {
		this.liMonedaDolar = liMonedaDolar;
	}

	public Integer getLiMonedaEuros() {
		return liMonedaEuros;
	}

	public void setLiMonedaEuros(Integer liMonedaEuros) {
		this.liMonedaEuros = liMonedaEuros;
	}

	public List<SelectItem> getLstDependenciasEntidadSelect() {
		return lstDependenciasEntidadSelect;
	}

	public void setLstDependenciasEntidadSelect(List<SelectItem> lstDependenciasEntidadSelect) {
		this.lstDependenciasEntidadSelect = lstDependenciasEntidadSelect;
	}

	public Integer getLiTipoDocPasaporte() {
		return liTipoDocPasaporte;
	}

	public void setLiTipoDocPasaporte(Integer liTipoDocPasaporte) {
		this.liTipoDocPasaporte = liTipoDocPasaporte;
	}

	/**
	 * Metodo la validacion en la vista de pasaporte, donde pasaporte si permite
	 * letras y numeros, mientras que los otros no.
	 */
	boolean blnPasaporte;

	public boolean isBlnPasaporte() {
		return blnPasaporte;
	}

	public void setBlnPasaporte(boolean blnPasaporte) {
		this.blnPasaporte = blnPasaporte;
	}

	public String getStrMensajeRegistrarContrato() {
		return strMensajeRegistrarContrato;
	}

	public void setStrMensajeRegistrarContrato(String strMensajeRegistrarContrato) {
		this.strMensajeRegistrarContrato = strMensajeRegistrarContrato;
	}

	public String getStrMensajeCorregirContrato() {
		return strMensajeCorregirContrato;
	}

	public void setStrMensajeCorregirContrato(String strMensajeCorregirContrato) {
		this.strMensajeCorregirContrato = strMensajeCorregirContrato;
	}

	public VinculacionExt getVinculacionSupervisor() {
		return vinculacionSupervisor;
	}

	public void setVinculacionSupervisor(VinculacionExt vinculacionSupervisor) {
		this.vinculacionSupervisor = vinculacionSupervisor;
	}
	
	public boolean isLbEliminarContratoRol() {
		return lbEliminarContratoRol;
	}

	public void setLbEliminarContratoRol(boolean lbEliminarContratoRol) {
		this.lbEliminarContratoRol = lbEliminarContratoRol;
	}

	public void compararDocumento() {
		if (strCodTipoIdentificacionBuscar != null) {
			blnPasaporte = false;
			Parametrica tipoDocumento = ComunicacionServiciosVin
					.getParametricaporId(BigDecimal.valueOf(Long.parseLong(strCodTipoIdentificacionBuscar)));
			if (tipoDocumento != null
					&& tipoDocumento.getNombreParametro().toUpperCase().contains(TIPO_DOC_PASAPORTE.toUpperCase())) {
				blnPasaporte = true;
			}
		}
	}

	/* No editar contratos termiandos */
	public boolean isLbIsRolFuncional() {
		return lbIsRolFuncional;
	}

	public void setLbIsRolFuncional(boolean lbIsRolFuncional) {
		this.lbIsRolFuncional = lbIsRolFuncional;
	}

	private boolean isContratoInactivo(ContratoExt contratoSel) {
		/* Verifica que el contrato este activo */
		if (contratoSel != null && contratoSel.getFlgActivo() != null && contratoSel.getFlgActivo() == 1) {
			return false;
		}

		/* Si no está activo, verifica que no tenga prorrogas */
		if (contratoSel != null && contratoSel.getFlgActivo() != null && contratoSel.getFlgEstadoProrroga() != null
				&& contratoSel.getFlgEstadoProrroga() == 0) {
			return true;
		}

		return false;
	}
	/**/

	/**
	 * Compara la entidad del usuario en sesión con la entidad del contrato
	 * 
	 * @param contratosel
	 * @return true si el contrato tiene la misma entidad del usuario en seión
	 */
	private boolean modificaContratoEntidadUsuarioEntidad(ContratoExt contratosel) {
		Long codEntidadUsuario = null, codEntidadContrato = null;
		if (entidadUsuario != null)
			codEntidadUsuario = entidadUsuario.getId();
		if (contratosel != null)
			codEntidadContrato = contratosel.getCodEntidad();
		if (codEntidadUsuario != null && codEntidadContrato != null && !codEntidadUsuario.equals(codEntidadContrato)) {
			RequestContext context = RequestContext.getCurrentInstance();
			this.setStrMsgAccionesContrato(strMsgEntidadDiferenteUsuEntidad);
			context.execute("PF('dlgAccionesContrato').show();");
			return false;
		}
		return true;
	}

	private boolean personaTieneContratosActivos(PersonaExt persona) {
		List<ContratoExt> lstContratos = ComunicacionServiciosCO
				.getContratoPersona(persona.getCodPersona().longValue());
		for (ContratoExt cont : lstContratos) {
			if (cont.getFlgActivo() == 1) {
				return true;
			}
		}
		return false;
	}

	public String obtenerNombreCargo() {
		try {
			if (modificacionConsultaReversaSelected != null) {
				Usuario usu = new Usuario();
				usu.setCodUsuario(this.modificacionConsultaReversaSelected.getAudCodUsuario());
				usu = ComunicacionServiciosHV.getusuarioid(usu);
				co.gov.dafp.sigep2.mbean.ext.VinculacionExt vin = new co.gov.dafp.sigep2.mbean.ext.VinculacionExt();
				vin.setCodPersona(usu.getCodPersona());
				List<co.gov.dafp.sigep2.mbean.ext.VinculacionExt> vins = ComunicacionServiciosVin
						.getMostrarVinculaciones(vin);
				if (vins != null) {
					if (vins.isEmpty())
						return MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.MSG_CONTRATOS_SIN_CARGO, this.getLocale());
					return vins.get(0).getNombreCargo();
				} else
					return MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_CONTRATOS_SIN_CARGO, this.getLocale());
			}
			return "";
		} catch (Exception ex) {
			logger.error("Error public String obtenerNombreCargo() ContratacionesBean.java " + ex.getMessage(), ex);
			return "Error";
		}
	}
}
