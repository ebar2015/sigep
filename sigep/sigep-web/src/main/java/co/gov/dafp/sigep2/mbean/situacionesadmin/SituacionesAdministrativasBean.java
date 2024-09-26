package co.gov.dafp.sigep2.mbean.situacionesadmin;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Enfermedad;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Festivo;
import co.gov.dafp.sigep2.entities.ModificacionSituacionAdministrativa;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.SituacionAdminVinculacion;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.HistoricoSituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.ModificacionSituacionAdministrativaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.mbean.ext.SituacionAdministrativaExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosCO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 * @author Nestor Riasco ADA
 */
@Named
@ViewScoped
@ManagedBean
public class SituacionesAdministrativasBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -4946388058501390640L;
	
	/**Variables staticas de las situaciones administrativas tabla SITUACION_ADMINISTRATIVA*/
	private static final int EN_EJERCICIO_EMPLEO_POR_ENCARGO							= 7;
	private static final int COMISION													= 8;
	private static final int SUSPENSION_PROVISIONAL										= 9;
	private static final int PRESTACION_SERVICIOS_MILITAR								= 10;
	private static final int VACACIONES 												= 11;
	private static final int DESCANSO_COMPENSADO										= 12;
	private static final int PROVISIONAL_A_PROVISIONAL									= 13;
	private static final int LIBRE_NOMBR_REMOCION_A_LIBRE_NOMBR							= 14;
	private static final int PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD						= 15;
	private static final int PERIODO_PRUEBA_ASCENSO_OTRA_ENTIDAD						= 16;
	private static final int LICENCIA_ORDINARIA 										= 18;
	private static final int LICENCIA_NO_REMUNERADA_ESTUDIOS 							= 19;
	private static final int LICENCIA_POR_LUTO 											= 20;
	private static final int LICENCIA_MATERNINAD_PATERNIDAD 							= 21;
	private static final int LICENCIA_POR_ACCIDENTE_TRABAJO								= 22;
	private static final int LICENCIA_POR_ENFERMEDAD									= 23;
	private static final int LICENCIA_ACTIVIDADES_DEPORTIVAS 							= 24;
	private static final int EN_PERMISO						 							= 6;
	private static final int PERMISO_ACADEMICO_COMPENSADO	 							= 26;
	private static final int PERMISO_EJERCER_DOCENCIA_UNIVERSITARIA						= 27;
	private static final int PERMISO_SINDICAL											= 28;
	private static final int LICENCIA_POR_ENFERMEDAD_PROFESIONAL						= 29;
	private static final int PERMISO_REMUNERADO											= 25;
	private static final int ENCARGO_EN_EMPLEOS_DE_CARRERA								= 36;
	private static final int ENCARGO_EN_EMPLEOS_DE_CARRERA_X_VACANCIA_TEMP				= 37;
	private static final int ENCARGO_EN_EMPLEOS_LIBRE_NOMBRAMIENTO_REMOSION				= 38;
	private static final int ENCARGO_INTERINSTITUCIONAL									= 39;
	private static final int COMISION_DE_SERVICIO										= 45;
	private static final int COMISION_DE_ESTUDIOS										= 46;
	private static final int COMISION_DESEMPENIO_CARGO_LIBRE_NOMBRAMIENTO_OTRA_ENTIDAD	= 47;
	private static final int CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD					= 48;
	private static final int COMISION_ATENDER_INVITACIONES_GOBIERNOS_EXTRANJEROS 		= 49;
	private static final int REGISTRAR_VACACIONES										= 50;
	private static final int CORRIMIENTO_DE_VACACIONES									= 51;
	private static final int INTERRUPCION_DE_VACACIONES									= 52;
	private static final int APLAZAMIENTO_DE_VACACIONES									= 53;
	private static final int OTRAS_SITUACIONES 											= 54;
	
	
	private Boolean flgValidaRolPermiso 			= false;
	private Boolean flgModificar 					= false;
	private Boolean flghabilitarClasificacionClase 	= false;
	private Boolean flgVisualizarCancelar 			= true;
	private String 	nombreCargoEncargo;
	private String 	codTipoIdentificacionBuscar;
	private String 	numeroIdentificacionBuscar;
	private String	primerNombreBuscar;
	private String 	primerApellidoBuscar;
	private String	segundoNombreBuscar;
	private String 	segundoApellidoBuscar;
	private String accion;
	
	private PersonaExt persona;
	private List<PersonaExt> listaFuncionario;
	private boolean habilitarFormulario = false;
	private boolean flgInformacionSA 	= false;
	private Long idFunc;
	private Long lngValorEntidad;
	private Integer duracionComisionTotal;

	/**Variable para Lista historico de situaciones administrativas*/
	private SituacionAdministrativaExt situacionAdmin;
	private List<SituacionAdminVinculacionExt> listaSitAdminFuncionario;
	private List<SituacionAdminVinculacionExt> listaSitAdminFuncionarioCargo;
	private EntidadPlantaDetalleExt cargoSeleccionado = new EntidadPlantaDetalleExt();

	private VinculacionExt vinculacionExt;
	private List<VinculacionExt> vinculacionActiva;
	VinculacionExt ultimaVinculacionActiva;
	private Boolean mostrarIconoProrroga = false;

	/**Variables para dropdowns de tipos de situaciones administrativas*/
	private List<SelectItem> listSitAdminP;
	private List<SelectItem> listSitAdminDependiente;
	private long codPersona;
	private Long sitAdminPadre;
	private Long sitAdminDep;
	private Boolean habilitarSADEP = true;

	/**Variables para habilitar formulario de acuerdo a la SA*/
	private Boolean habilitarFormLic 				= false;
	private Boolean habilitarFormEnf 				= false;
	private Boolean habilitarFormSA 				= false;
	private Boolean habilitarFormCargo 				= false;
	private Boolean habilitarFormComision 			= false;
	private Boolean habilitarFormLibreta 			= false;
	private Boolean habilitarFormOtro 				= false;
	private Boolean habilitarFormProvisional 		= false;
	private Boolean habilitarFormPruebaOtraEntidad 	= false;
	private Boolean mostrarFechaFinRequerida 		= false;

	/**
	 * Variables para activas, mostrar, desactivar u ocultar campos de cada uno de
	 * los formularios de registro de S.A.
	 */
	private SituacionAdminVinculacionExt situacionAdminPersona;
	private String sitAdminPadreForm;
	private String sitAdminDepForm;
	private boolean accionModificar 			= false;
	private Boolean deshabilitarfechafin 		= false;
	private Boolean boolMostrarVolverEncargar 	= false;
	private Boolean mostrarVolverEncargar 		= true;
	private Boolean mostrarDescEntidad			= false;
	private Boolean mostrarSitAminDep 			= false;
	private Boolean mostrarSitAminDepSuspension = false;
	private Boolean mostrarFechaFin 			= false;
	private Boolean mostrarfechaFinSuspension 	= false;
	private Boolean mostrarTotalDuracion 		= false;
	private List<SelectItem> listaTipoActoAdmin;
	private VinculacionExt vinculacion;
	private List<EntidadPlantaDetalleExt> listCargos;
	private List<ModificacionSituacionAdministrativaExt> listModificacionSA;

	private EntidadPlantaDetalleExt cargo;
	private EntidadPlantaDetalleExt cargoTemporalAOcupar; 
	private Boolean flgTipoPlanta = true; 
	private Boolean valorEncargar = false;
	
	/* Acciones sobre una situación Administrativa */
	private boolean lbProrrogar;
	private boolean lbConsultar;
	private boolean lbEliminar;
	private boolean lbLog = true;
	private SituacionAdminVinculacionExt situacionAdminVinculacionExtSelected;
	ModificacionSituacionAdministrativa prorrogaSituacion;
	/* Mensajes */
	private String strMensajeModificacionSitAdmin;
	RequestContext requestContext;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);
		this.initialization();
		this.vinculacion = new VinculacionExt();
		this.listCargos = null;
		lngValorEntidad = getEntidadUsuario().getId();
		situacionAdmin = new SituacionAdministrativaExt();
		situacionAdminPersona = new SituacionAdminVinculacionExt();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_SITUACIONESADMINISTRATIVAS);
	}

	/** Metodo para validar roles */
	public void initialization() {
		if (getUsuarioSesion() != null) {
			try {
				flgValidaRolPermiso = this.usuarioTieneRolAsignadoSinJerarquia(
										RolDTO.JEFE_TALENTO_HUMANO,
										RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_ENTIDADES,
										RolDTO.JEFE_CONTROL_INTERNO);
				if (flgValidaRolPermiso == false) {
					mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, TitlesBundleConstants
							.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()),
							"window.location.assign('../index.xhtml?faces-redirect=true')");
					return;
				}
			} catch (SIGEP2SistemaException e) {
				logger.error("void init() usuarioTieneRolAsignado", e);
			}
		} else {
			mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()),
					"window.location.assign('../index.xhtml?faces-redirect=true')");
			return;
		}

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String idPersona = request.getParameter("id");
		if (idPersona != null) {
			Long lgPersona = Long.valueOf(idPersona);
			persona = ComunicacionServiciosHV.getPersonaporIdExt(lgPersona);
			if (persona.getCodVinculacion() != null)
				this.mostrarHistoricoSA();
		}
	}

	/**
	 * valida que hayan datos para realizar la consulta consulta con los parametros
	 * recibidos muesta u oculta tabla con resultados de busqueda
	 */
	public void mostrarTablaFuncionario() {
		if(validarCamposFiltroBusquedaPersona()) {
			return;
		}

		PersonaExt personaBuscar = new PersonaExt();
		personaBuscar.setCodTipoIdentificacion( this.codTipoIdentificacionBuscar != null && !this.codTipoIdentificacionBuscar.equals("") 
												? this.codTipoIdentificacionBuscar : null);
		personaBuscar.setNumeroIdentificacion(this.numeroIdentificacionBuscar != null && !this.numeroIdentificacionBuscar.equals("") 
												? this.numeroIdentificacionBuscar : null);
		personaBuscar.setPrimerNombre(this.primerNombreBuscar != null && !this.primerNombreBuscar.equals("") 
										? this.primerNombreBuscar: null);
		personaBuscar.setSegundoNombre(this.segundoNombreBuscar != null && !this.segundoNombreBuscar.equals("") 
										? this.segundoNombreBuscar : null);
		personaBuscar.setPrimerApellido(this.primerApellidoBuscar != null && !this.primerApellidoBuscar.equals("")
										? this.primerApellidoBuscar : null);
		personaBuscar.setSegundoApellido(this.segundoApellidoBuscar != null && !this.segundoApellidoBuscar.equals("")
										? this.segundoApellidoBuscar : null);
		personaBuscar.setCodTipoVinculacion(BigDecimal.valueOf(TipoParametro.TIPO_VINCULACION_SERV_PUBLICO.getValue()));
		personaBuscar.setCodEntidad(BigDecimal.valueOf(getEntidadUsuario().getId()));
		personaBuscar.setFlgActivoVinculacion((short) 1);
		personaBuscar.setFlgActivo((short) 1);
		personaBuscar.setLimitInit(0);
		personaBuscar.setLimitEnd(2000);
	
		listaFuncionario = ComunicacionServiciosCO.getPersonaporEntidadFiltroF(personaBuscar);
		habilitarFormulario = true;
		if (listaFuncionario == null || listaFuncionario.size() == 0) {
			habilitarFormulario = false;
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SIN_RESULTADO);
		}
	}

	/**
	 * Metodo que valida que los campos del filtro inicial para la busqueda de las personas a registrar una situacion administrativa
	 * esten diligenciados segun lo especificado en el CU
	 * 1. Si se selecciona el tipo de identificacion, se debe de llenar tambien el numero de identificacion
	 * 2. Si esta vacio el filtro, el sistema debe de validar que el usuario diligencie al menos un campo
	 * @return true cuando faltan campos por diligenciar, segun las dos reglas anteriores.
	 */
	public boolean validarCamposFiltroBusquedaPersona() {
		if (this.codTipoIdentificacionBuscar != null && !this.codTipoIdentificacionBuscar.equals("")
				|| this.numeroIdentificacionBuscar != null && !this.numeroIdentificacionBuscar.equals("")
				|| this.primerNombreBuscar != null && !this.primerNombreBuscar.equals("")
				|| this.segundoNombreBuscar != null && !this.segundoNombreBuscar.equals("")
				|| this.primerApellidoBuscar != null && !this.primerApellidoBuscar.equals("")
				|| this.segundoApellidoBuscar != null && !this.segundoApellidoBuscar.equals("")) {

			if ((this.codTipoIdentificacionBuscar != null && !this.codTipoIdentificacionBuscar.equals(""))
					&& (this.numeroIdentificacionBuscar == null || this.numeroIdentificacionBuscar.equals(""))) {
				habilitarFormulario = false;
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_INGRESAR_NUMERO_DOC);
				return true;
			}
			if ((this.codTipoIdentificacionBuscar == null || this.codTipoIdentificacionBuscar.equals(""))
					&& (this.numeroIdentificacionBuscar != null && !this.numeroIdentificacionBuscar.equals(""))) {
				habilitarFormulario = false;
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_INGRESAR_TIPO_DOC);
				return true;
			}
		} else {
			habilitarFormulario = false;
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_VALIDAR_DATOS_CONSULTAR);
			return true;
		}
		return false;
	}
	
	/** Cancelacion de la busqueda de funcionarios publicos */
	public void cancelarBusquedaFuncP() {
		reiniciarCamposFiltros();
	}
	
	/** Cancelacion de la busqueda de funcionarios publicos */
	public void cancelarGestionBusqueda() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("situacionesAdministrativas.xhtml?recursoId=SituacionesAdministrativasTag");
		} catch (IOException e) {
			e.printStackTrace();
		}
		reiniciarCamposFiltros();
	}
	
	/**Metodo que reinicia los campos utilizados para los filtros de busqueda*/
	public void reiniciarCamposFiltros() {
		this.habilitarFormulario 			= false;
		this.codTipoIdentificacionBuscar 	= null;
		this.numeroIdentificacionBuscar 	= null;
		this.primerNombreBuscar 			= null;
		this.segundoNombreBuscar 			= null;
		this.primerApellidoBuscar 			= null;
		this.segundoApellidoBuscar 			= null;
	}

	/**
	 * Metodo para redireccionar de situaciones administrativa a gestionar
	 * situaciones administrativas con el id de la persona elegida
	 */
	public void verAccion(long id) {
		if (id == 0) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_SELECCIONAR_FUNCIONARIO);
			return;
		}
		this.idFunc = id;
		persona = ComunicacionServiciosHV.getPersonaporIdExt(this.idFunc);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('accionRealizarDialog').show();");
	}

	/**
	 * Metodo que realiza las validaciones de dependencia y seleccion del cargo
	 * @return
	 */
	public void seleccionCargo(EntidadPlantaDetalleExt cargoSeleccionado) {
		cargoTemporalAOcupar = new EntidadPlantaDetalleExt();
		cargoTemporalAOcupar = cargoSeleccionado;
		cargo = ComunicacionServiciosVin.getEntidadPlantaDetalleId(cargoSeleccionado.getCodEntidadPlantaDetalle());
		validarDependencia();
		this.nombreCargoEncargo = cargoSeleccionado.getNombreCargo() + "-" + cargoSeleccionado.getNombreCodigo() + "-" + cargoSeleccionado.getNombreGrado();
		this.situacionAdminPersona.setCodEntidadPlantaDetalleEncargo(cargoSeleccionado.getCodEntidadPlantaDetalle());
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogCargo').hide();");
	}

	public void cancelarAccion() {
		this.accion = null;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('accionRealizarDialog').hide();");
	}

	public void mostrarFormAccion() {
		if (accion.equals("gestionsarSA")) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("gestionarSituacionesAdministrativas.xhtml?id=" + idFunc
								+ "&recursoId=SituacionesAdministrativasTag");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(
						"consultarSituaciones.xhtml?id=" + idFunc + "&recursoId=SituacionesAdministrativasTag");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('accionRealizarDialog').hide();");
	}

	/** Metodo que valida el tipo de planta al que pertenece el cargo*/
	public void validarDependencia() {
		flgTipoPlanta = false;
		if (cargo.getCodTipoPlanta() != null && cargo.getCodTipoPlanta().longValue() == TipoParametro.PLANTA_ESTRUCTURAL.getValue()) {
			flgTipoPlanta = true;
		}
		DependenciaEntidadExt ent = new DependenciaEntidadExt();
		ent.setCodEntidad(lngValorEntidad);
		ent.setCodDependenciaEntidad(cargo.getCodDependenciaEntidad());
		situacionAdminPersona.setCodDependenciaEntidad(cargo.getCodDependenciaEntidad());
		ent.setFlgActivo((short) 1);
		List<DependenciaEntidadExt> listP = ComunicacionServiciosVin.getDependenciaEntidadFilter(ent);

		if (!listP.isEmpty()) {
			vinculacion.setStrNombreDependencia(listP.get(0).getNombreDependencia());
			situacionAdminPersona.setCodDependenciaEntidad(listP.get(0).getCodDependenciaEntidad());
			vinculacion.setCodDependenciaEntidad(BigDecimal.valueOf(listP.get(0).getCodDependenciaEntidad()));
		}
	}

	/** Metodo para listar el historico de SA de la persona elegida */
	public void mostrarHistoricoSA() {
		vinculacionExt = new VinculacionExt();
		VinculacionExt personaVinculacion = new VinculacionExt();
		vinculacionExt.setCodPersona(persona.getCodPersona());
		vinculacionExt.setCodEntidad(getEntidadUsuario().getId());
		vinculacionExt.setFlgActivo((short) 1);
		vinculacionExt.setLimitInit(0);
		vinculacionExt.setLimitEnd(1);

		List<VinculacionExt> listPersonaVinculacion = ComunicacionServiciosVin.getvinculacionactual(vinculacionExt);
		if (!listPersonaVinculacion.isEmpty()) {
			personaVinculacion = listPersonaVinculacion.get(0);
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_VINCULACION);
			return;
		}
		
		SituacionAdminVinculacionExt filtroHistoricoSA = new SituacionAdminVinculacionExt();
		filtroHistoricoSA.setCodVinculacion(personaVinculacion.getCodVinculacion().longValue());
		filtroHistoricoSA.setFlgActivo((short) 1);
		listaSitAdminFuncionario = ComunicacionServiciosAdmin.getSituacionVinculacion(filtroHistoricoSA);

		for (SituacionAdminVinculacionExt situacion : listaSitAdminFuncionario) {
			if (situacion.getFechaFin() == null) {
				int duracion;
				if (situacion.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA 
						|| situacion.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
						|| situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD
						|| situacion.getCodSituacionAdministrativa() == PERMISO_REMUNERADO) {
					duracion = this.calcularDuracionGenerica(situacion, true);
				} else {
					duracion = this.calcularDuracionGenerica(situacion, false);
				}
				situacion.setDuracion(duracion);
			}
		}
	}

	
	/**
	 * Metodo para validar si la hoja de vida de la persona a probar
	 * cuenta con datos modificados y que no estan aprobados.
	 * @return boolean
	 */
	private boolean validacionCambiosHv() {	
		boolean valid = false;
		HojaVidaExt objFilter = new HojaVidaExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short)1);
		objFilter.setFlgAprobado((short)0);
		HojaVidaExt hojaVidaSinAprobacion = ComunicacionServiciosHV.getHojaVidaAprobacion(objFilter);
		if(hojaVidaSinAprobacion != null) {
			if(hojaVidaSinAprobacion.getTotalSinVerificar()!=null && hojaVidaSinAprobacion.getTotalSinVerificar()>0) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_INFO_HOJA_SIN_APROBAR);
				valid = true;
			}
		}
		return valid;
	}
	
	/**
	 * Metodo para visualizacion de formulario de eleccion de situacion administrativa
	 */
	public void mostrarFormulario(boolean valid) {
		this.flgModificar 		= false;
		this.sitAdminPadre 		= null;
		this.sitAdminDep 		= null;
		this.vinculacion 		= new VinculacionExt();
		situacionAdmin 			= new SituacionAdministrativaExt();
		situacionAdminPersona 	= new SituacionAdminVinculacionExt();
		if (valid == false) {
			this.flghabilitarClasificacionClase = false;
			this.flgVisualizarCancelar = true;
		} else {
			try {
				flgValidaRolPermiso = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO,
						RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_ENTIDADES);
				if (flgValidaRolPermiso == false) {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
					return;
				}
			} catch (SIGEP2SistemaException e) {
				logger.error("void init() usuarioTieneRolAsignado", e);
			}
			this.flgVisualizarCancelar = false;
			this.listaSitAdministrativas();
			this.listaSitAdminDependiente(sitAdminPadre);
			reiniciarVisualizacionFormulario();
		}
		this.habilitarFormSA = valid;
	}

	/**Dropdown de Situaciones administrativas */
	public void listaSitAdministrativas() {
		situacionAdmin = new SituacionAdministrativaExt();
		situacionAdmin.setFlgActivo((short) 1);
		situacionAdmin.setCodSituacionadminPadre(null);
		listSitAdminP = new ArrayList<SelectItem>();
		List<SituacionAdministrativaExt> listSitAdmin = ComunicacionServiciosAdmin.getSituacionAdministrativaFiltro(situacionAdmin);
		if (!listSitAdmin.isEmpty()) {
			for (SituacionAdministrativaExt aux : listSitAdmin) {
				if (aux.getCodSituacionadminPadre() == null && aux.getCodSituacionAdministrativa() != OTRAS_SITUACIONES) {
					listSitAdminP.add(new SelectItem(aux.getCodSituacionAdministrativa(), aux.getNombreSituacion()));
				} else if (aux.getCodSituacionAdministrativa() == OTRAS_SITUACIONES) {
					SituacionAdministrativaExt nuevaSituacionParticular = new SituacionAdministrativaExt();
					nuevaSituacionParticular.setFlgEsgenerica((short) 0);
					nuevaSituacionParticular.setFlgActivo((short) 1);
					nuevaSituacionParticular.setCodEntidad((int) getEntidadUsuario().getId());
					List<SituacionAdministrativaExt> listOtrasAsig = ComunicacionServiciosAdmin.getSituacionParticularesPorEntidad(nuevaSituacionParticular);
					listSitAdminDependiente = new ArrayList<SelectItem>();
					if (!listOtrasAsig.isEmpty()) 
						listSitAdminP.add(new SelectItem(aux.getCodSituacionAdministrativa(), aux.getNombreSituacion()));
				}
			}
		}
	}

	/**Dropdown de Situaciones administrativas Dependientes */
	public void listaSitAdminDependiente(Long CodSitAdmin) {
		this.mostrarSitAminDepSuspension = false;
		this.flghabilitarClasificacionClase = false;
		SituacionAdministrativaExt sitAdminDep = new SituacionAdministrativaExt();
		sitAdminDep.setCodSituacionadminPadre(CodSitAdmin);
		sitAdminDep.setFlgActivo((short) 1);
		if (CodSitAdmin != null) {
			if ((CodSitAdmin == PROVISIONAL_A_PROVISIONAL) 
					|| (CodSitAdmin == LIBRE_NOMBR_REMOCION_A_LIBRE_NOMBR) 
					|| (CodSitAdmin == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD)) {
				flghabilitarClasificacionClase = true;
			} else {
				flghabilitarClasificacionClase = false;
			}
		}
		this.sitAdminDep = null;
		listSitAdminDependiente = new ArrayList<SelectItem>();
		List<SituacionAdministrativaExt> listSitAdminDep = new ArrayList<>();
		
		/** Si la situacion administrativa es "Otras situaciones administrativas"*/
		if (CodSitAdmin != null) {
			if (CodSitAdmin == OTRAS_SITUACIONES) {
				SituacionAdministrativaExt nuevaSituacionParticular = new SituacionAdministrativaExt();
				nuevaSituacionParticular.setFlgEsgenerica((short) 0);
				nuevaSituacionParticular.setFlgActivo((short) 1);
				nuevaSituacionParticular.setCodEntidad((int) getEntidadUsuario().getId());
				listSitAdminDep = ComunicacionServiciosAdmin.getSituacionParticularesPorEntidad(nuevaSituacionParticular);
				if (!listSitAdminDep.isEmpty()) {
					this.habilitarSADEP = true;
					for (SituacionAdministrativaExt aux : listSitAdminDep) {
						listSitAdminDependiente.add(new SelectItem(aux.getCodSituacionAdministrativa(), aux.getNombreSituacion()));
					}
				}else {
					if (CodSitAdmin == SUSPENSION_PROVISIONAL || CodSitAdmin == OTRAS_SITUACIONES) {
						this.mostrarSitAminDepSuspension = true;
					}
				}
			} else {
				listSitAdminDep = ComunicacionServiciosAdmin.getSituacionAdministrativaFiltro(sitAdminDep);
				if (!listSitAdminDep.isEmpty()) {
					this.habilitarSADEP = true;
					for (SituacionAdministrativaExt aux : listSitAdminDep) {
						if (aux.getCodSituacionadminPadre() != null && !aux.getCodSituacionadminPadre().equals(null)
								&& aux.getCodSituacionadminPadre().equals(CodSitAdmin)) {
							listSitAdminDependiente.add(new SelectItem(aux.getCodSituacionAdministrativa(), aux.getNombreSituacion()));
						}
					}
				} else {
					if (CodSitAdmin == SUSPENSION_PROVISIONAL || CodSitAdmin == OTRAS_SITUACIONES) {
						this.mostrarSitAminDepSuspension = true;
					}
				}
			}
			if (listSitAdminDep.size() <= 0) {
				this.habilitarSADEP = false;
			}
		}
	}

	public void listaTipoActoAdmin() {
		listaTipoActoAdmin = new ArrayList<SelectItem>();
		List<Parametrica> listaActoAdmin = ComunicacionServiciosSis.getParametricaPorIdPadre(TipoParametro.TIPO_ACTO_ADMINISTRATIVO.getValue());
		if (!listaActoAdmin.isEmpty()) {
			for (Parametrica aux : listaActoAdmin) {
				listaTipoActoAdmin.add(new SelectItem(aux.getCodTablaParametrica(), aux.getNombreParametro()));
			}
		}
	}

	/**Metodo que reinicia la visualizacion de formulario*/
	public void reiniciarVisualizacionFormulario() {
		this.habilitarFormLibreta 			= false;
		this.habilitarFormSA 				= false;
		this.habilitarFormLic 				= false;
		this.habilitarFormEnf 				= false;
		this.habilitarFormCargo 			= false;
		this.habilitarFormComision 			= false;
		this.habilitarFormOtro 				= false;
		this.habilitarFormProvisional 		= false;
		this.habilitarFormPruebaOtraEntidad = false;
	}
	
	/**
	 * Metodo que se encarga de validar si la persona cuenta con tipo de nombramiento Carrera Administrativa
	 * */
	public boolean validarTipoNombramientoCarrera() {
		Integer[] tipoNombramientoCarreraAdministrativa = {
				  TipoParametro.NOMBRAMIENTO_CARRERA_CARRERA_ADMIN.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_CARRERA_DIPLOMATICA.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_DOCENTES.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_INSTRUCTORES.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_RAMA_JUDICIAL.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_MILITAR.getValue(),
				  TipoParametro.NOMBRAMIENTO_PERIODO_PRUEBA.getValue()
				 };
		
		if (ultimaVinculacionActiva!= null && ultimaVinculacionActiva.getCodTipoNombramiento() != null && 
				!Arrays.asList(tipoNombramientoCarreraAdministrativa).contains(ultimaVinculacionActiva.getCodTipoNombramiento())) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SA_TIPO_NOMBRAMIENTO_NO_VALIDO);
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo que se encarga de validar si la persona cuenta con tipo de nombramiento Carrera Administrativa o tipo de nombramiento y remoción
	 * */
	public boolean validarTipoNombramientoCarreraConLNR() {
		Integer[] tipoNombramientoCarreraAdministrativaLNR = {
				  TipoParametro.NOMBRAMIENTO_LNR.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_CARRERA_ADMIN.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_CARRERA_DIPLOMATICA.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_DOCENTES.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_INSTRUCTORES.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_RAMA_JUDICIAL.getValue(),
				  TipoParametro.NOMBRAMIENTO_CARRERA_MILITAR.getValue()
				 };
		
		if (ultimaVinculacionActiva.getCodTipoNombramiento() != null && 
				!Arrays.asList(tipoNombramientoCarreraAdministrativaLNR).contains(ultimaVinculacionActiva.getCodTipoNombramiento())) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SA_TIPO_NOMBRAMIENTO_NO_VALIDO);
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo que se encarga de validar si la persona cuenta con tipo de nombramiento LNR
	 * */
	public boolean validarTipoNombramientoLNR() {
		Integer[] tipoNombramientoLNR = {TipoParametro.NOMBRAMIENTO_LNR.getValue()};
		if (ultimaVinculacionActiva.getCodTipoNombramiento() != null && 
				!Arrays.asList(tipoNombramientoLNR).contains(ultimaVinculacionActiva.getCodTipoNombramiento())) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SA_TIPO_NOMBRAMIENTO_NO_VALIDO);
			return true;
		}
		return false;
	}
	
	
	/** Mostrar formularios de registro de situaciones adminsitrativas */
	public void mostrarFormularioRegistoSA() {
		if (sitAdminDep == null) {
			if (sitAdminPadre == PRESTACION_SERVICIOS_MILITAR || sitAdminPadre == DESCANSO_COMPENSADO) {
				if (sitAdminPadre == PRESTACION_SERVICIOS_MILITAR && persona.getCodGenero() != TipoParametro.GENERO_MASCULINO.getValue()) {
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							"Género de funcionario no válido para prestación de servicio militar");
					return;
				}
				if (sitAdminPadre == PRESTACION_SERVICIOS_MILITAR) {
					this.mostrarFechaFinRequerida = false;
				} else {
					this.mostrarFechaFinRequerida = true;
				}
				reiniciarVisualizacionFormulario();
				this.habilitarFormLibreta = true;
			} else if ((sitAdminPadre == PROVISIONAL_A_PROVISIONAL || sitAdminPadre == LIBRE_NOMBR_REMOCION_A_LIBRE_NOMBR 
						|| sitAdminPadre == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD) && this.flgModificar) {
				if (situacionAdminPersona.getCodDependenciaEntidad() != null) {
					this.vinculacion.setCodDependenciaEntidad(
							BigDecimal.valueOf(situacionAdminPersona.getCodDependenciaEntidad()));
				}
				flgTipoPlanta = false;
				if(situacionAdminPersona.getCodEntidadPlantaDetalleEncargo() != null) {
					cargo = ComunicacionServiciosVin.getEntidadPlantaDetalleId(situacionAdminPersona.getCodEntidadPlantaDetalleEncargo());
				}
				
				if (cargo != null)
					this.nombreCargoEncargo = cargo.getNombreDenominacion() + "-" + cargo.getNombreCodigo() + "-" + cargo.getNombreGrado();
				reiniciarVisualizacionFormulario();
				this.habilitarFormProvisional = true;
			} else if ((sitAdminPadre == PROVISIONAL_A_PROVISIONAL || sitAdminPadre == LIBRE_NOMBR_REMOCION_A_LIBRE_NOMBR 
					|| sitAdminPadre == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD) && !this.flgModificar) {
				obtenerVinculacionActual();
				if(sitAdminPadre == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD) {
					if(validarTipoNombramientoCarrera()) {
						return;
					}
				}
				
				if(sitAdminPadre == LIBRE_NOMBR_REMOCION_A_LIBRE_NOMBR) {
					if(validarTipoNombramientoLNR()) {
						return;
					}
				}
				
				if(this.validacionCambiosHv()) {
					return;
				}
				this.mostrarTablaCargos();
				reiniciarVisualizacionFormulario();
				this.habilitarFormProvisional = true;
			} else if (sitAdminPadre == PERIODO_PRUEBA_ASCENSO_OTRA_ENTIDAD) {
				obtenerVinculacionActual();
				if(validarTipoNombramientoCarrera()) {
					return;
				}
				
				reiniciarVisualizacionFormulario();
				this.habilitarFormPruebaOtraEntidad = true;
			}
		} else if (sitAdminPadre == SUSPENSION_PROVISIONAL || sitAdminPadre == VACACIONES ) {
			if (sitAdminPadre == SUSPENSION_PROVISIONAL) {
				this.mostrarSitAminDepSuspension 	= true;
				this.mostrarfechaFinSuspension 		= true;
			} else {
				this.mostrarSitAminDepSuspension 	= false;
				this.mostrarfechaFinSuspension	 	= false;
				this.mostrarFechaFin 				= true;
			}
			this.habilitarFormLic 				= true;
			this.habilitarFormSA 				= false;
			this.habilitarFormEnf 				= false;
			this.habilitarFormCargo 			= false;
			this.habilitarFormComision 			= false;
			this.habilitarFormLibreta 			= false;
			this.habilitarFormOtro 				= false;
			this.habilitarFormPruebaOtraEntidad = false;
			this.setMostrarTotalDuracion(false);
		} else if (sitAdminPadre == OTRAS_SITUACIONES) {
			reiniciarVisualizacionFormulario();
			this.habilitarFormOtro = true;
			this.mostrarSitAminDepSuspension = true;
			this.setMostrarTotalDuracion(false);
		} else if (sitAdminDep == LICENCIA_ORDINARIA || sitAdminDep == LICENCIA_NO_REMUNERADA_ESTUDIOS 
				|| sitAdminDep == LICENCIA_POR_LUTO || sitAdminDep == LICENCIA_ACTIVIDADES_DEPORTIVAS
				|| sitAdminPadre == EN_PERMISO || sitAdminPadre == VACACIONES ) {
			if (sitAdminPadre == VACACIONES  && sitAdminDep != REGISTRAR_VACACIONES) {
				this.mostrarfechaFinSuspension = true;
			} else {
				this.mostrarFechaFin = true;
			}
			reiniciarVisualizacionFormulario();
			this.mostrarSitAminDep = true;
			this.habilitarFormLic = true;
			this.setMostrarTotalDuracion(false);
		} else if ((sitAdminPadre == EN_EJERCICIO_EMPLEO_POR_ENCARGO || (sitAdminPadre == COMISION 
				&& sitAdminDep == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD)) && this.flgModificar) {
			if (sitAdminDep != ENCARGO_INTERINSTITUCIONAL && sitAdminDep != CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD) {
				this.mostrarVolverEncargar = true;
			} else {
				this.mostrarVolverEncargar = false;
			}
			
			if(situacionAdminPersona.getCodDependenciaEntidad() != null) {
				this.vinculacion.setCodDependenciaEntidad(BigDecimal.valueOf(situacionAdminPersona.getCodDependenciaEntidad()));
			}
			
			flgTipoPlanta = false;
			if(situacionAdminPersona.getCodEntidadPlantaDetalleEncargo() != null) {
				cargo = ComunicacionServiciosVin.getEntidadPlantaDetalleId(situacionAdminPersona.getCodEntidadPlantaDetalleEncargo());
			}
			

			if (cargo != null)
				this.nombreCargoEncargo = cargo.getNombreDenominacion() + "-" + cargo.getNombreCodigo() + "-"
						+ cargo.getNombreGrado();

			reiniciarVisualizacionFormulario();
			this.habilitarFormCargo = true;
			this.mostrarSitAminDep = true;
			this.mostrarfechaFinSuspension = true;
			this.setMostrarTotalDuracion(false);
		} else if ((sitAdminPadre == EN_EJERCICIO_EMPLEO_POR_ENCARGO || (sitAdminPadre == COMISION && sitAdminDep == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD)) 
					&& !this.flgModificar) {
			obtenerVinculacionActual();
			if(sitAdminPadre == EN_EJERCICIO_EMPLEO_POR_ENCARGO ) {
				if(sitAdminDep == ENCARGO_EN_EMPLEOS_DE_CARRERA 
				   || sitAdminDep == ENCARGO_EN_EMPLEOS_DE_CARRERA_X_VACANCIA_TEMP ) {
					if(validarTipoNombramientoCarrera()) {
						return;
					}
				}
				
				if(sitAdminDep == ENCARGO_EN_EMPLEOS_LIBRE_NOMBRAMIENTO_REMOSION || sitAdminDep == ENCARGO_INTERINSTITUCIONAL) {
					validarTipoNombramientoCarreraConLNR();
				}				
			}
			
			if(sitAdminDep == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD) {
				if(validarTipoNombramientoCarrera()) {
					return;
				}
			}
			
			if (sitAdminDep != ENCARGO_INTERINSTITUCIONAL && sitAdminDep != CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD) {
				this.mostrarVolverEncargar = true;
			} else {
				this.mostrarVolverEncargar = false;
			}
			if(this.validacionCambiosHv()) {
				return ;
			}
			
			this.mostrarTablaCargos();
			reiniciarVisualizacionFormulario();
			this.habilitarFormCargo = true;
			this.mostrarSitAminDep = true;
			this.mostrarfechaFinSuspension = true;
			this.setMostrarTotalDuracion(false);
		} else if (sitAdminPadre == COMISION && sitAdminDep != CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD) {
			obtenerVinculacionActual();
			if(sitAdminDep == COMISION_ATENDER_INVITACIONES_GOBIERNOS_EXTRANJEROS || sitAdminDep == COMISION_DE_ESTUDIOS) {
				validarTipoNombramientoCarreraConLNR();
			}
			if(sitAdminDep ==  COMISION_DE_SERVICIO || sitAdminDep ==  COMISION_DESEMPENIO_CARGO_LIBRE_NOMBRAMIENTO_OTRA_ENTIDAD) {
				if(validarTipoNombramientoCarrera()) {
					return;
				}
			}
			
			if (sitAdminDep == COMISION_DE_ESTUDIOS) {
				this.mostrarDescEntidad = false;
			} else {
				this.mostrarDescEntidad = true;
			}

			reiniciarVisualizacionFormulario();
			this.habilitarFormComision = true;
			if (sitAdminDep == COMISION_DESEMPENIO_CARGO_LIBRE_NOMBRAMIENTO_OTRA_ENTIDAD) {
				this.duracionComisionTotal = calcularDuracionTotalComisionPorEmpleado();
				this.setMostrarTotalDuracion(true);
			}
		} else {
			reiniciarVisualizacionFormulario();
			this.mostrarSitAminDep = true;
			this.mostrarFechaFin = true;
			this.habilitarFormEnf = true;
			this.setMostrarTotalDuracion(false);
		}

		if (sitAdminDep != null) {
			situacionAdmin = ComunicacionServiciosAdmin.getSituacionPadre(sitAdminDep);
			setSitAdminPadreForm(situacionAdmin.getNombrePadre());
			setSitAdminDepForm(situacionAdmin.getNombreSituacion());
		} else {
			situacionAdmin = ComunicacionServiciosAdmin.getSituacionPadre(sitAdminPadre);
			setSitAdminPadreForm(situacionAdmin.getNombreSituacion());
			setSitAdminDepForm(situacionAdmin.getNombreSituacion());
		}
		situacionAdminPersona.setCodSituacionAdministrativa(situacionAdmin.getCodSituacionAdministrativa());
		this.listaTipoActoAdmin();
	}
	
	/**
	 * Metodo que obtiene informacion del cargo actual de la persona
	 */
	public void obtenerVinculacionActual() {
		VinculacionExt objFilter = new VinculacionExt();
		objFilter.setCodPersona(persona.getCodPersona());
		objFilter.setFlgActivo((short)1);
		objFilter.setCodEntidad(getEntidadUsuario().getId());
		vinculacionActiva = ComunicacionServiciosVin.getvinculacionactual(objFilter);
		if(!vinculacionActiva.isEmpty()) {
			ultimaVinculacionActiva = vinculacionActiva.get(0);
		}
	}
	
	private Integer calcularDuracionTotalComisionPorEmpleado() {
		int total = 0;
		vinculacionExt = new VinculacionExt();
		vinculacionExt.setCodPersona(persona.getCodPersona());
		vinculacionExt.setCodEntidad(getEntidadUsuario().getId());
		vinculacionExt.setFlgActivo((short) 1);
		vinculacionExt.setLimitInit(0);
		vinculacionExt.setLimitEnd(1);

		VinculacionExt personaVinculacion = ComunicacionServiciosVin.getvinculacionactual(vinculacionExt).get(0);

		SituacionAdminVinculacionExt filtroHistoricoSA = new SituacionAdminVinculacionExt();
		filtroHistoricoSA.setCodVinculacion(Long.parseLong("" + personaVinculacion.getCodVinculacion()));
		filtroHistoricoSA.setFlgActivo((short) 1);
		listaSitAdminFuncionario = ComunicacionServiciosAdmin.getSituacionVinculacion(filtroHistoricoSA);

		for (SituacionAdminVinculacionExt situacion : listaSitAdminFuncionario) {
			if (situacion.getCodSituacionAdministrativa() == COMISION_DESEMPENIO_CARGO_LIBRE_NOMBRAMIENTO_OTRA_ENTIDAD) {
				total = total + situacion.getDuracion();
			}
		}
		return total;
	}

	public void validarEncargoAnterior() {
		SituacionAdministrativaExt filtro;
		if (valorEncargar) {
			Boolean permiteVolverEncargar = false;
			VinculacionExt vinculacionActual = new VinculacionExt();
			vinculacionActual.setCodPersona(persona.getCodPersona());
			vinculacionActual.setCodEntidad(getEntidadUsuario().getId());
			vinculacionActual.setFlgActivo((short) 1);
			vinculacionActual.setLimitInit(0);
			vinculacionActual.setLimitEnd(1);
			vinculacionActual = ComunicacionServiciosVin.getvinculacionactual(vinculacionActual).get(0);

			SituacionAdminVinculacionExt sitVin = new SituacionAdminVinculacionExt();
			sitVin.setCodVinculacion(vinculacionActual.getCodVinculacion().longValue());
			List<SituacionAdminVinculacionExt> lista = ComunicacionServiciosAdmin.getSituacionVinculacion(sitVin);
			for (SituacionAdminVinculacionExt situacionAdminVinculacion : lista) {
				filtro = ComunicacionServiciosAdmin
						.getSituacionPadre(situacionAdminVinculacion.getCodSituacionAdministrativa().longValue());
				if (filtro.getCodSituacionadminPadre() != null) {
					if (filtro.getCodSituacionadminPadre().longValue() == EN_EJERCICIO_EMPLEO_POR_ENCARGO) {
						permiteVolverEncargar = true;
						break;
					} else {
						permiteVolverEncargar = false;
					}
				} else if (filtro.getCodSituacionAdministrativa().longValue() == EN_EJERCICIO_EMPLEO_POR_ENCARGO) {
					permiteVolverEncargar = true;
					break;
				} else {
					permiteVolverEncargar = false;
				}
			}
			if (!permiteVolverEncargar) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, "",
						"No existen situaciones de encargo anteriores, por favor verificar antes de activar el check.");
			}
		}
	}

	/**
	 * @see guardarSituacionAdministrativa
	 * @param sitAdmin
	 *            Calcular cantidad de dias entre la fecha inicio y fin de las SA,
	 *            se ejecuta al momento de guardar la SA
	 */
	public void calcularDuracion(SituacionAdminVinculacionExt sitAdmin) {
		int duracion = 0;
		long fechaFin;
		long fechaInicio = sitAdmin.getFechaInicio().getTime();
		if (sitAdmin.getFechaFin() != null) {
			fechaFin = sitAdmin.getFechaFin().getTime();
		} else {
			fechaFin = new Date().getTime();
		}

		if (fechaFin < fechaInicio) {
			duracion = (int) ((fechaInicio - fechaFin) / (1000 * 60 * 60 * 24));
			duracion = duracion + 1;
			duracion = duracion * -1;
		} else {
			duracion = (int) ((fechaFin - fechaInicio) / (1000 * 60 * 60 * 24));
			duracion = duracion + 1;
		}

		situacionAdminPersona.setDuracion(duracion);
	}

	public int calcularDuracionGenerica(SituacionAdminVinculacionExt sitAdmin, Boolean habil) {
		int diasHabiles = 0;
		int diasFestivos = 0;
		int duracion = 0;
		long fechaFin;
		Calendar fechaInicial = Calendar.getInstance();
		Calendar fechaFinal = Calendar.getInstance();

		if (habil) {
			fechaInicial.setTime(sitAdmin.getFechaInicio());
			if (sitAdmin.getFechaFin() != null) {
				fechaFinal.setTime(sitAdmin.getFechaFin());
			} else {
				fechaFinal.setTime(new Date());
			}

			while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {
				if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					diasHabiles++;
				}
				fechaInicial.add(Calendar.DATE, 1);
			}

			Festivo festivo = new Festivo();
			festivo.setFechaFeriado1(sitAdmin.getFechaInicio());
			if (sitAdmin.getFechaFin() != null) {
				festivo.setFechaFeriado2(sitAdmin.getFechaFin());
			} else {
				festivo.setFechaFeriado2(new Date());
			}

			diasFestivos = ComunicacionServiciosAdmin.getcantidaddiashabiles(festivo);
			duracion = diasHabiles - diasFestivos;
		} else {

			long fechaInicio = sitAdmin.getFechaInicio().getTime();

			if (sitAdmin.getFechaFin() != null) {
				fechaFin = sitAdmin.getFechaFin().getTime();
			} else {
				fechaFin = new Date().getTime();
			}

			if (fechaFin < fechaInicio) {
				duracion = (int) ((fechaInicio - fechaFin) / (1000 * 60 * 60 * 24));
				duracion = duracion + 1;
				duracion = duracion * -1;
			} else {
				duracion = (int) ((fechaFin - fechaInicio) / (1000 * 60 * 60 * 24));
				duracion = duracion + 1;
			}
		}
		return duracion;
	}

	/** Calcular dias habiles entre la fecha inicio y fin de las SA */
	public void calcularDuracionHabil(SituacionAdminVinculacionExt sitAdminVincunacion) {
		int diasHabiles = 0;
		int diasFestivos = 0;
		int duracion = 0;
		Calendar fechaInicial = Calendar.getInstance();
		Calendar fechaFinal = Calendar.getInstance();

		fechaInicial.setTime(sitAdminVincunacion.getFechaInicio());

		if (sitAdminVincunacion.getFechaFin() != null) {
			fechaFinal.setTime(sitAdminVincunacion.getFechaFin());
		} else {
			fechaFinal.setTime(new Date());
		}

		while (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)) {
			if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				diasHabiles++;
			}
			fechaInicial.add(Calendar.DATE, 1);
		}

		Festivo festivo = new Festivo();
		festivo.setFechaFeriado1(sitAdminVincunacion.getFechaInicio());
		if (sitAdminVincunacion.getFechaFin() != null) {
			festivo.setFechaFeriado2(sitAdminVincunacion.getFechaFin());
		} else {
			festivo.setFechaFeriado2(new Date());
		}

		diasFestivos = ComunicacionServiciosAdmin.getcantidaddiashabiles(festivo);
		duracion = diasHabiles - diasFestivos;
		situacionAdminPersona.setDuracion(duracion);
	}

	/** Mostrar Tabla de cargos disponibles */
	public void mostrarTablaCargos() {

		if (sitAdminPadre == PROVISIONAL_A_PROVISIONAL
			||sitAdminPadre == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD) {
			this.getListaCargos(this.lngValorEntidad, this.vinculacion.getCodClasificacionPlanta(),
					this.vinculacion.getCodClasePlanta(), TipoParametro.CARRERA_ADMINISTRATIVA.getValue(), "GET_VACANTES_TEMP_PERM_GLOBAL");
			
		} else if (sitAdminPadre == LIBRE_NOMBR_REMOCION_A_LIBRE_NOMBR 
				|| (sitAdminPadre == EN_EJERCICIO_EMPLEO_POR_ENCARGO && sitAdminDep == ENCARGO_EN_EMPLEOS_LIBRE_NOMBRAMIENTO_REMOSION)
				|| (sitAdminPadre == COMISION && sitAdminDep == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD)) {
			this.getListaCargos(this.lngValorEntidad, this.vinculacion.getCodClasificacionPlanta(),
					this.vinculacion.getCodClasePlanta(),  TipoParametro.LIBRE_NOMBRAMIENTO_Y_REMOCION.getValue(), "GET_VACANTES_TEMP_PERM_GLOBAL_ESTRUCTURAL");

		} else if (sitAdminPadre == EN_EJERCICIO_EMPLEO_POR_ENCARGO && sitAdminDep == ENCARGO_EN_EMPLEOS_DE_CARRERA_X_VACANCIA_TEMP) {
			this.getListaCargos(this.lngValorEntidad, this.vinculacion.getCodClasificacionPlanta(),
					this.vinculacion.getCodClasePlanta(), TipoParametro.CARRERA_ADMINISTRATIVA.getValue(), "GET_VACANTES_TEMP_GLOBAL");

		} else if (sitAdminPadre == EN_EJERCICIO_EMPLEO_POR_ENCARGO && 
				(sitAdminDep == ENCARGO_EN_EMPLEOS_DE_CARRERA || sitAdminDep == ENCARGO_INTERINSTITUCIONAL)) {
			this.getListaCargos(this.lngValorEntidad, this.vinculacion.getCodClasificacionPlanta(),
					this.vinculacion.getCodClasePlanta(), TipoParametro.CARRERA_ADMINISTRATIVA.getValue(), "GET_VACANTES_TEMP_PERM_GLOBAL_ESTRUCTURAL");
		}

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogCargo').show();");
	}

	/** Cancelar formulario de registro de situaciones administrativas */
	public void cancelarRegistro() {
		situacionAdmin = new SituacionAdministrativaExt();
		situacionAdminPersona = new SituacionAdminVinculacionExt();
		this.mostrarHistoricoSA();
		this.mostrarFormulario(false);
		this.setMostrarTotalDuracion(false);
		this.flgVisualizarCancelar = true;
		this.deshabilitarfechafin = false;
		this.mostrarSitAminDep = false;
		this.mostrarSitAminDepSuspension = false;
		this.mostrarFechaFin = false;
		this.mostrarfechaFinSuspension = false;
		reiniciarVisualizacionFormulario();
	}

	/** Guardar Situacion Administrativa */
	public void guardarSituacionAdministrativa() {
		this.mostrarHistoricoSA();
		if(validarSituacionMenorVinculacion()) {
			return;
		}
		if(validarSAEnMismoPeriodo()) {
			return;
		}
		if(validarFechasSituacionVacancia()) {
			return;
		}
	
		vinculacionExt = new VinculacionExt();
		VinculacionExt personaVinculacion = new VinculacionExt();

		vinculacionExt.setCodPersona(persona.getCodPersona());
		vinculacionExt.setCodEntidad(getEntidadUsuario().getId());
		vinculacionExt.setFlgActivo((short) 1);
		vinculacionExt.setLimitInit(0);
		vinculacionExt.setLimitEnd(1);

		List<VinculacionExt> listPersonaVinculacion = ComunicacionServiciosVin.getvinculacionactual(vinculacionExt);

		if (!listPersonaVinculacion.isEmpty()) {
			personaVinculacion = listPersonaVinculacion.get(0);
		}

		if (personaVinculacion.getCodPersona() != null) {

			if (situacionAdminPersona.getCodSituacionAdministrativa() == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD
							|| situacionAdminPersona.getCodSituacionAdministrativa() == PERIODO_PRUEBA_ASCENSO_OTRA_ENTIDAD
							|| situacionAdminPersona.getCodSituacionAdministrativa() == ENCARGO_EN_EMPLEOS_DE_CARRERA_X_VACANCIA_TEMP
							|| situacionAdminPersona.getCodSituacionAdministrativa() == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD){
				validarTipoNombramientoCarrera();
			}
			/*Se cambia el tipo de nombramiento a periodo de prueba si cumple con la condicion */
			/*if (situacionAdminPersona.getCodSituacionAdministrativa() == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD
				|| situacionAdminPersona.getCodSituacionAdministrativa() == PERIODO_PRUEBA_ASCENSO_OTRA_ENTIDAD) {
				personaVinculacion.setCodTipoNombramiento(TipoParametro.PERIODO_PRUEBA_SA.getValue());
				boolean valido = ComunicacionServiciosVin.setVinculacion(personaVinculacion);
				if (!valido) {
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
					return;
				}
			}*/
			
			if(situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_DESEMPENIO_CARGO_LIBRE_NOMBRAMIENTO_OTRA_ENTIDAD
			   ||situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_ATENDER_INVITACIONES_GOBIERNOS_EXTRANJEROS
			   ||situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_DE_SERVICIO
			   ||situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_DE_ESTUDIOS) {
				if (situacionAdminPersona.getCodEntidadComision() == null && situacionAdminPersona.getDescripcionEntidad()==null) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_ENTIDAD_INEXISTENTE_SA);
					return;
				}
			}

			situacionAdminPersona.setAudFechaActualizacion(DateUtils.getFechaSistema());
			situacionAdminPersona.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			situacionAdminPersona.setAudCodRol((int) this.getRolAuditoria().getId());
			situacionAdminPersona.setFlgActivo((short) 1);
			if (situacionAdminPersona.getCodSituacionAdminRelacionLaboral() == null
					|| situacionAdminPersona.getCodSituacionAdminRelacionLaboral() == 0) {
				situacionAdminPersona.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
			} else {
				situacionAdminPersona.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
			}
			situacionAdminPersona.setCodVinculacion(Long.parseLong("" + personaVinculacion.getCodVinculacion()));
			if (situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_DE_ESTUDIOS) {
				situacionAdminPersona.setDescripcionEntidad(null);
			}
			if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA
					|| situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
					|| situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD
					|| situacionAdminPersona.getCodSituacionAdministrativa() == PERMISO_REMUNERADO || sitAdminPadre == VACACIONES ) {
				if (persona.getCodGenero() == TipoParametro.GENERO_FEMENINO.getValue() && situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD) {
					this.calcularDuracion(situacionAdminPersona);
				} else {
					this.calcularDuracionHabil(situacionAdminPersona);
					Parametrica diasPermisoRemunerado = new Parametrica();
					diasPermisoRemunerado = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.DIAS_PERMISO_REMUNERADO.getValue()));
					int iDiasPermiso;
					String sDiasPermisoRemunerado;
					try {
						iDiasPermiso = Integer.valueOf(diasPermisoRemunerado.getValorParametro());
						sDiasPermisoRemunerado = diasPermisoRemunerado.getValorParametro();
					} catch (NumberFormatException e) {
						iDiasPermiso = 0;
						sDiasPermisoRemunerado = "0";
					}

					if (situacionAdminPersona.getCodSituacionAdministrativa() == PERMISO_REMUNERADO
							&& situacionAdminPersona.getDuracion() > iDiasPermiso) {

						mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.getStringMessagesBundle(
												MessagesBundleConstants.MSG_PERMISO_REMUNERADO_NO_PERMITIDO, getLocale())
										.replace("%dias%", sDiasPermisoRemunerado));
						return;
					}

				}
			} else {
				this.calcularDuracion(situacionAdminPersona);

			}

			if (sitAdminPadre == SUSPENSION_PROVISIONAL) {
				situacionAdminPersona.setCodTipoSuspencion(
						Integer.valueOf(situacionAdminPersona.getCodSituacionAdministrativa().toString()));
			}

			/* Validacion para que no permita guardar comisiones para desempeñar cagos de libre nombremiento en otra entidad
			 * No puede ser mayor a 6 años*/

			if (situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_DESEMPENIO_CARGO_LIBRE_NOMBRAMIENTO_OTRA_ENTIDAD) {
				int totalDiasComisiones = calcularDuracionTotalComisionPorEmpleado();
				totalDiasComisiones = totalDiasComisiones + situacionAdminPersona.getDuracion();
				Parametrica paramTotalDiasComisiones = ComunicacionServiciosSis
						.getParametricaIntetos("TOTAL_DIAS_COMISION_LIBRE_NOMBRAMIENTO_OTRA_ENT");
				int paramTotal = Integer.parseInt(paramTotalDiasComisiones.getValorParametro());

				if (totalDiasComisiones > paramTotal) {
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_SUPERA_DIAS_COMISION_LIBRE_NOMBRAMIENTO_OTRA_ENT);
					return;
				}
			}

			SituacionAdminVinculacion valid = ComunicacionServiciosAdmin.setSituacionAdminVinculacion(situacionAdminPersona);

			if (!valid.isError()) {
				ocuparVacanciaTemporal(valid);
				
				for (SituacionAdminVinculacionExt situacion : this.listaSitAdminFuncionario) {
					if (situacion.getFechaFin() != null && situacionAdminPersona.getFechaFin() != null) {
						int numeroDiasASumar = 0;
						SituacionAdminVinculacionExt temp = new SituacionAdminVinculacionExt();

						if ((situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
								|| situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_ENFERMEDAD)
								&& (situacion.getCodSituacionAdministrativa() == REGISTRAR_VACACIONES
										|| situacion.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA
										|| situacion.getCodSituacionAdministrativa() == LICENCIA_NO_REMUNERADA_ESTUDIOS)) {
							// Se consulta el padre de la licencia
							SituacionAdministrativaExt situacionAdmin = ComunicacionServiciosAdmin.getSituacionPadre(situacion.getCodSituacionAdministrativa());
							Long sitAdminPadre = situacionAdmin.getCodSituacionadminPadre();

							if (situacion.getCodSituacionAdministrativa() == REGISTRAR_VACACIONES) {
								temp.setCodSituacionAdministrativa(situacionAdminPersona.getCodSituacionAdministrativa());
							} else {
								temp.setCodSituacionAdministrativa(situacion.getCodSituacionAdministrativa());
							}
							// Fechas nuevas están entre las dos fechas de la situacion existente
 							if ((situacionAdminPersona.getFechaInicio().compareTo(situacion.getFechaInicio()) >= 0)
									&& (situacionAdminPersona.getFechaFin().compareTo(situacion.getFechaFin()) <= 0)) {
								temp.setFechaInicio(situacion.getFechaInicio());
								temp.setFechaFin(situacionAdminPersona.getFechaInicio());
								numeroDiasASumar = situacionAdminPersona.getDuracion();
								// Se actualiza la fecha fin de la situación
								situacion.setFechaFin(sumarDiasFecha(situacion.getCodSituacionAdministrativa(),
										sitAdminPadre, situacion.getFechaFin(), numeroDiasASumar, false));

								// Fechas nuevas se solapan por la izquierda
							} else if ((situacionAdminPersona.getFechaFin().compareTo(situacion.getFechaInicio()) >= 0)
									&& (situacionAdminPersona.getFechaInicio()
											.compareTo(situacion.getFechaInicio()) < 0)) {

								temp.setFechaInicio(situacionAdminPersona.getFechaInicio());
								temp.setFechaFin(situacion.getFechaInicio());
								/*Se suma toda la duraciónporque aun no han comenzado las vacaciones*/
								numeroDiasASumar = (situacionAdminPersona.getDuracion()	
													- obtenerNumeroDias(temp, sitAdminPadre)) + 2; 

								// Se actualiza la fecha fin de la situación
								situacion.setFechaFin(sumarDiasFecha(situacion.getCodSituacionAdministrativa(),sitAdminPadre, situacion.getFechaFin(), numeroDiasASumar,false));

								// Fechas nuevas se solapan por la derecha
							} else if ((situacionAdminPersona.getFechaInicio().compareTo(situacion.getFechaFin()) <= 0)
									&& (situacionAdminPersona.getFechaFin().compareTo(situacion.getFechaFin()) > 0)) {
								temp.setFechaInicio(situacionAdminPersona.getFechaInicio());
								temp.setFechaFin(situacion.getFechaFin());

								numeroDiasASumar = obtenerNumeroDias(temp, sitAdminPadre) + 1;
								// numeroDiasASumar = obtenerNumeroDias(temp, sitAdminPadre);

								// Se actualiza la fecha fin de la situación
								situacion.setFechaFin(sumarDiasFecha(situacion.getCodSituacionAdministrativa(),
										sitAdminPadre, situacionAdminPersona.getFechaFin(), numeroDiasASumar));
							}

							situacion.setAudFechaActualizacion(DateUtils.getFechaSistema());
							situacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
							situacion.setAudCodRol((int) this.getRolAuditoria().getId());
							situacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());

							if (situacion.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA
									|| situacion.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
									|| situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD
									|| situacion.getCodSituacionAdministrativa() == PERMISO_REMUNERADO || sitAdminPadre == VACACIONES ) {
								if (persona.getCodGenero() == TipoParametro.GENERO_FEMENINO.getValue() && situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD) {
									this.calcularDuracion(situacion);
								} else {
									this.calcularDuracionHabil(situacion);
								}
							} else {
								this.calcularDuracion(situacion);

							}

							situacion.setDuracion(situacionAdminPersona.getDuracion());

							valid = ComunicacionServiciosAdmin.setSituacionAdminVinculacion(situacion);
							if (!valid.isError()) {
								mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
										MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
							}/*else call procedure*/
						}
					}
				}

				if ((sitAdminPadre == EN_EJERCICIO_EMPLEO_POR_ENCARGO) || (sitAdminPadre == COMISION && sitAdminDep == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD)) {
					HistoricoSituacionAdminVinculacionExt historicoSituacionAdminVinculacion = new HistoricoSituacionAdminVinculacionExt();
					historicoSituacionAdminVinculacion.setCodPersona(personaVinculacion.getCodPersona());
					historicoSituacionAdminVinculacion
							.setCodEntidadPlantaDetalleEncargo(situacionAdminPersona.getCodEntidadPlantaDetalleEncargo());
					historicoSituacionAdminVinculacion.setFechaInicio(situacionAdminPersona.getFechaInicio());
					historicoSituacionAdminVinculacion.setFechaFin(situacionAdminPersona.getFechaFin());
					historicoSituacionAdminVinculacion.setAudFechaActualizacion(DateUtils.getFechaSistema());
					historicoSituacionAdminVinculacion
							.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
					historicoSituacionAdminVinculacion.setAudCodRol((int) this.getRolAuditoria().getId());
					historicoSituacionAdminVinculacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());

					boolean validHistorico = ComunicacionServiciosAdmin.setHistoricoAdministracion(historicoSituacionAdminVinculacion);

					if (!validHistorico) {
						mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
					}
				}

				situacionAdmin = new SituacionAdministrativaExt();
				if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_EXITOSA_LIC_ORDINARIA);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_NO_REMUNERADA_ESTUDIOS) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_NO_REMUNERADA);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_LUTO);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_MATERNIDAD);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_ACCIDENTE_TRABAJO) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_ACCIDENTE_LABORAL);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_ENFERMEDAD) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_ENFERMEDAD);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_ENFERMEDAD_PROFESIONAL) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_ENFERMEDAD_PRO);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_ACTIVIDADES_DEPORTIVAS) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_DEPORTIVA);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == PERMISO_REMUNERADO) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_PERMISO_REMUNERADO);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == PERMISO_ACADEMICO_COMPENSADO) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_PERMISO_ACADEMICO_COMPENSADO);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == PERMISO_EJERCER_DOCENCIA_UNIVERSITARIA) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_PERMISO_DOCENTE);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == PERMISO_SINDICAL) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_PERMISO_SINDICAL);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == ENCARGO_EN_EMPLEOS_DE_CARRERA) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_EMPLEO_CARRERA);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == ENCARGO_EN_EMPLEOS_DE_CARRERA_X_VACANCIA_TEMP) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_EMPLEO_CARRERA_VACANCIA_TEMPORAL);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == ENCARGO_EN_EMPLEOS_LIBRE_NOMBRAMIENTO_REMOSION) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_EMPLEO_LIBRE_NOMBRAMIENTO);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == ENCARGO_INTERINSTITUCIONAL) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_EMPLEO_INTERINSTITUCIONAL);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_DE_SERVICIO) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_COMISION_SERVICIO);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_DE_ESTUDIOS) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_COMISION_ESTUDIO);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_DESEMPENIO_CARGO_LIBRE_NOMBRAMIENTO_OTRA_ENTIDAD
						|| situacionAdminPersona.getCodSituacionAdministrativa() == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_COMISION_CARGO_ENTIDAD);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == COMISION_ATENDER_INVITACIONES_GOBIERNOS_EXTRANJEROS) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDAD_SA_COMISION_INVITACIONES);
				} else if (sitAdminPadre == SUSPENSION_PROVISIONAL) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_SUSPENSION);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == PRESTACION_SERVICIOS_MILITAR) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_SERVICIO_MILITAR);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == REGISTRAR_VACACIONES) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_REGISTRAR_VACACIONES);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == CORRIMIENTO_DE_VACACIONES) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_CORRIMIENTO_VACACIONES);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == INTERRUPCION_DE_VACACIONES) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_INTERRUPCION_VACACIONES);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == APLAZAMIENTO_DE_VACACIONES) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_APLAZAMIENTO_VACACIONES);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == DESCANSO_COMPENSADO) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_DESCANSO);
				} else if (sitAdminPadre == OTRAS_SITUACIONES) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_OTRA);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == PROVISIONAL_A_PROVISIONAL) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_PROVISIONAL);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LIBRE_NOMBR_REMOCION_A_LIBRE_NOMBR) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_LIBRE_NOMBRAMIENTO);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_ASCENSO_MISMA_ENTIDAD);
				} else if (situacionAdminPersona.getCodSituacionAdministrativa() == PERIODO_PRUEBA_ASCENSO_OTRA_ENTIDAD) {
					situacionAdminPersona = new SituacionAdminVinculacionExt();
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_GUARDADA_SA_ASCENSO_OTRA_ENTIDAD);
				}

			} else {
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
				situacionAdminPersona = new SituacionAdminVinculacionExt();
			}
			this.cancelarRegistro();
		}
		cargoTemporalAOcupar = new EntidadPlantaDetalleExt();
	}
	
	/**
	 * Metodo que se encarga de almacenar el codigo de la situacion admininastriva que va a ocupar la vacancia temporal.
	 * */
	public void ocuparVacanciaTemporal(SituacionAdminVinculacion saGuardada) {
		if(cargoTemporalAOcupar != null && cargoTemporalAOcupar.getCodSituacionAdminRelacionLaboral()!= null) {
			SituacionAdminVinculacion sa = new SituacionAdminVinculacion();
			sa.setCodSituacionAdminRelacionLaboral( cargoTemporalAOcupar.getCodSituacionAdminRelacionLaboral().longValue());
			SituacionAdminVinculacion result = ComunicacionServiciosVin.getSituacionAdminVincPorId(sa);
			if(result != null && result.getCodSituacionAdminRelacionLaboral()!= null) {
				result.setCodSAOcupaTemporal(saGuardada.getCodSituacionAdminRelacionLaboral()!= null ? new BigDecimal(saGuardada.getCodSituacionAdminRelacionLaboral()): null);
				SituacionAdminVinculacion valid = ComunicacionServiciosAdmin.updateSituacionAdminVinculacion(result);
				if(!valid.isError()) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_CREAR_REGISTRO);
				}
			}
		}
	}
	
	/**
	 * Metodo que valida que la situacion sea mayor a la vinculación actual
	 * @return blnFechaIncorrecta
	 * Si es true es porque la situacion administrativa a registrar si es menor a la fecha de posesion de la vinculacion asociada.
	 */
	public boolean validarSituacionMenorVinculacion() {
		boolean blnFechaIncorrecta = false;
		vinculacionExt = new VinculacionExt();
		VinculacionExt personaVinculacion = new VinculacionExt();
		vinculacionExt.setCodPersona(persona.getCodPersona());
		vinculacionExt.setCodEntidad(getEntidadUsuario().getId());
		vinculacionExt.setFlgActivo((short) 1);
		vinculacionExt.setLimitInit(0);
		vinculacionExt.setLimitEnd(1);
		List<VinculacionExt> listPersonaVinculacion = ComunicacionServiciosVin.getvinculacionactual(vinculacionExt);
		if (!listPersonaVinculacion.isEmpty()) {
			personaVinculacion = listPersonaVinculacion.get(0);
		}
		if (situacionAdminPersona != null && personaVinculacion != null && situacionAdminPersona.getFechaInicio() != null && 
			personaVinculacion.getFechaPosesion()!= null) {
			LocalDate fechaInicialSA 	= situacionAdminPersona.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate FechaVinculacion 	= personaVinculacion.getFechaPosesion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (fechaInicialSA.isBefore(FechaVinculacion)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_SA_FECHA_INICIO_MENOR_FECHA_VINCULACION);
				blnFechaIncorrecta = true;
			}
		}
		return blnFechaIncorrecta;
	}
	
	/**
	 * Metodo que se encarga de realizar las validaciones de fechas para las situaciones administrativas que van en encargo o comisión.
	 * 1. Valida que la fecha de inicio de la SA no sea menor a la fecha de Inicio de la SA de la persona que va a encargar.
	 * 2. Valida que la fecha Final de la SA no sea mayor a la fecha Fina de la SA de la persona que va a encargar.
	 * @return
	 */
	public boolean validarFechasSituacionVacancia() {
		if(cargoTemporalAOcupar != null &&  situacionAdminPersona != null) {
			/*Valida fechas iniciales*/
			if(cargoTemporalAOcupar.getFechaInicioSA() != null && situacionAdminPersona.getFechaInicio() != null) {
				LocalDate fechaInicialSA 		= situacionAdminPersona.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate fechaInicialSAEncargo = cargoTemporalAOcupar.getFechaInicioSA().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if (fechaInicialSA.isBefore(fechaInicialSAEncargo)) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_SA_FECHA_INICIO_MENOR_FECHA_INICIO_ENCARGO);
					return true;
				}
			}
			/*Valida fechas finales*/
			if(cargoTemporalAOcupar.getFechaFinSA()!= null) {
				if(situacionAdminPersona.getFechaFin() == null) {
					return false;
				}
			
				LocalDate fechaFinalSA 		= situacionAdminPersona.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() ;
				LocalDate fechaFinalSAEncargo = cargoTemporalAOcupar.getFechaFinSA().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if (fechaFinalSAEncargo.isBefore(fechaFinalSA)) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_SA_FECHA_FINAL_MAYOR_FECHA_FINAL_ENCARGO);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo que valida que no se realicen SA repetidas en el mismo periodo de tiempo
	 * 1. El primer if realiza la validacion siempre y cuando la SA no implique cambio de cargo, es por este motivo que
	 * 		se valida por situacionAdminPersona.getCodEntidadPlantaDetalleEncargo() == null
	 * 2. Para el else, Si la sa implica un cambio de cargo, se valida que exista otra vigente para modificar
		  su fecha fin = a la fecha inicial -1 de la nueva SA
	 * @return true si existe ya una situacion administrativa para ese mismo periodo de tiempo.
	 */
	public boolean validarSAEnMismoPeriodo() {
		int contIgual = 0;	
		for (SituacionAdminVinculacionExt situacion : this.listaSitAdminFuncionario) {
			if (situacion.getFechaFin() != null && situacionAdminPersona.getFechaFin() != null){
				if(situacionAdminPersona.getCodSituacionAdminRelacionLaboral() == null ||
					(situacionAdminPersona.getCodSituacionAdminRelacionLaboral() != null
						&& situacionAdminPersona.getCodSituacionAdminRelacionLaboral() != situacion.getCodSituacionAdminRelacionLaboral() )	) {
					if ((!situacion.getCodSituacionAdministrativa().equals(situacionAdminPersona.getCodSituacionAdministrativa()))
							&& (((situacionAdminPersona.getFechaInicio().compareTo(situacion.getFechaInicio()) >= 0)
									&& (situacionAdminPersona.getFechaInicio().compareTo(situacion.getFechaFin()) <= 0))
									|| ((situacionAdminPersona.getFechaFin().compareTo(situacion.getFechaInicio()) >= 0)
											&& (situacionAdminPersona.getFechaFin().compareTo(situacion.getFechaFin()) <= 0)))) {
						contIgual++;
					}
				}
			} else {
				Date fechaFin;
				if (situacion.getFechaFin() == null)
					fechaFin = DateUtils.getFechaSistema();
				else
					fechaFin = situacion.getFechaFin();

				if (situacion.getCodEntidadPlantaDetalleEncargo() != null
						&& fechaFin.compareTo(DateUtils.getFechaSistema()) >= 0
						&& situacionAdminPersona.getFechaInicio().compareTo(fechaFin) <= 0
						&& situacionAdminPersona.getCodEntidadPlantaDetalleEncargo() != null) {
					Calendar fechaIni = Calendar.getInstance();
					fechaIni.setTime(situacionAdminPersona.getFechaInicio());
					fechaIni.add(Calendar.DAY_OF_YEAR, -1);
					Date fechafinal = fechaIni.getTime();
					situacion.setFechaFin(fechafinal);
					situacion.setAudFechaActualizacion(DateUtils.getFechaSistema());
					situacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
					situacion.setAudCodRol((int) this.getRolAuditoria().getId());
					situacion.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());

					if (situacion.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA
							|| situacion.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
							|| situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD
							|| situacion.getCodSituacionAdministrativa() == PERMISO_REMUNERADO || sitAdminPadre == VACACIONES ) {
						if (persona.getCodGenero() == TipoParametro.GENERO_FEMENINO.getValue() && situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD) {
							this.calcularDuracion(situacion);
						} else {
							this.calcularDuracionHabil(situacion);
						}
					} else {
						this.calcularDuracion(situacion);
					}

					situacion.setDuracion(situacionAdminPersona.getDuracion());
					SituacionAdminVinculacion valid = ComunicacionServiciosAdmin.setSituacionAdminVinculacion(situacion);
					if (!valid.isError()) {
						return true;
					}

				}
			}
		}
		if (contIgual > 0) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_REGISTRO_REPETIDO);
			return true;
		}
		return false;
	}
	
	/**
	 * @author Jhon Garcia 
	 * Calcular el número de días que existen entre dos fechas
	 */
	public int obtenerNumeroDias(SituacionAdminVinculacionExt situacion, long codPadreSituacionAdmin) {
		if (situacion.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA || situacion.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
				|| situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD || situacion.getCodSituacionAdministrativa() == PERMISO_REMUNERADO
				|| codPadreSituacionAdmin == VACACIONES ) {
			if (persona.getCodGenero() == TipoParametro.GENERO_FEMENINO.getValue() && situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD) {
				this.calcularDuracion(situacion);
			} else {
				this.calcularDuracionHabil(situacion);
			}
		} else {
			this.calcularDuracion(situacion);
		}
		// return situacionAdminPersona.getDuracion() - 1;
		return situacionAdminPersona.getDuracion();
	}
	public Date sumarDiasFecha(Long codSituacionAdministrativa, Long codPadreSituacionAdmin, Date fecha, int dias){
		return sumarDiasFecha(codSituacionAdministrativa, codPadreSituacionAdmin, fecha, dias, true);
	}

	/**
	 * @author Jhon Garcia Sumar o restar dias a una fecha
	 */
	public Date sumarDiasFecha(Long codSituacionAdministrativa, Long codPadreSituacionAdmin, Date fecha, int dias, boolean asignarFIsituacionAdminPersona) {
		Calendar fechaInicial = Calendar.getInstance();

		fechaInicial.setTime(fecha);
		if(asignarFIsituacionAdminPersona)
			situacionAdminPersona.setFechaInicio(fecha);
		int diasHabilesAdicional = 0;

		if (codSituacionAdministrativa == LICENCIA_ORDINARIA || codSituacionAdministrativa == LICENCIA_POR_LUTO 
				|| codSituacionAdministrativa == LICENCIA_MATERNINAD_PATERNIDAD
				|| codSituacionAdministrativa == PERMISO_REMUNERADO || codPadreSituacionAdmin == VACACIONES ) {
			if (persona.getCodGenero() == TipoParametro.GENERO_FEMENINO.getValue() && codSituacionAdministrativa == LICENCIA_MATERNINAD_PATERNIDAD) {
				diasHabilesAdicional = dias;
			} else {
				diasHabilesAdicional = this.calcularFechaFinHabiles("" + dias);

			}
		} else {
			diasHabilesAdicional = dias;

		}
		fechaInicial.add(Calendar.DAY_OF_YEAR, diasHabilesAdicional);
		Date fechafinal = fechaInicial.getTime();
		return fechafinal;
	}

	/**
	 * Calculos de fecha fin para licencia por luto Fecha Junio 21 del 2018
	 */
	public void calcularfechaFin() {

		if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
				|| situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD) {
			this.deshabilitarfechafin = true;
			Parametrica licencia = new Parametrica();
			if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO) {
				licencia = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.DIAS_LICENCIA_LUTO.getValue()));
			} else if (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD) {
				if (persona.getCodGenero() == TipoParametro.GENERO_FEMENINO.getValue()) {
					licencia = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.DIAS_LICENCIA_LUTO.getValue()));
				} else {
					licencia = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.DIAS_LICENCIA_PATERNIDAD.getValue()));
				}
			}

			String diasParametro = licencia.getValorParametro();

			Calendar fechaInicial = Calendar.getInstance();

			fechaInicial.setTime(situacionAdminPersona.getFechaInicio());
			int diasHabilesAdicional;
			if ((situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO)
					|| (situacionAdminPersona.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD && persona.getCodGenero() != TipoParametro.GENERO_FEMENINO.getValue())) {
				diasHabilesAdicional = this.calcularFechaFinHabiles(diasParametro);
			} else {
				diasHabilesAdicional = Integer.parseInt(diasParametro);
			}

			fechaInicial.add(Calendar.DAY_OF_YEAR, diasHabilesAdicional - 1);
			Date fechafinal = fechaInicial.getTime();
			situacionAdminPersona.setFechaFin(fechafinal);

		} else {
			return;
		}
	}

	/**
	 * @param diasAdicionar
	 * @return diasAdicionales
	 * @See calcularfechaFin Metodo que calcula cantidad de dias habiles para halla
	 *      fecha fin de acuerdo a una fecha inicial fecha: 31 de Agosto del 2018
	 * @author Nestor Riasco ADA
	 */
	public int calcularFechaFinHabiles(String diasAdicionar) {

		int diasAdicionales = Integer.parseInt(diasAdicionar);
		int diasTranscurridos = 1;
		int contador = 1;
		Calendar fechaInicial = Calendar.getInstance();
		Calendar fechaFestivo = Calendar.getInstance();
		Festivo festivo = new Festivo();
		festivo.setFechaFeriado1(situacionAdminPersona.getFechaInicio());
		boolean aumentacontador=false;

		List<Festivo> listaDiasFestivos = new ArrayList<>();
		listaDiasFestivos = ComunicacionServiciosAdmin.getDiasHabiles(festivo);

		if (listaDiasFestivos.size() <= 0) {
			while (contador < diasAdicionales) {
				fechaInicial.setTime(situacionAdminPersona.getFechaInicio());
				fechaInicial.add(Calendar.DATE, diasTranscurridos);
				if (fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
						&& fechaInicial.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					contador++;
				}
				diasTranscurridos++;
			}
		} else {
			contador = diasTranscurridos = 0;
			while (contador < diasAdicionales) {
				fechaInicial.setTime(situacionAdminPersona.getFechaInicio());
				if(diasTranscurridos > 0)
					fechaInicial.add(Calendar.DATE, diasTranscurridos);
				diasTranscurridos++;
				aumentacontador = true;
				if (fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| fechaInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					aumentacontador=false;
				} else{
					for (Festivo aux : listaDiasFestivos) {
						fechaFestivo.setTime(aux.getFechaFeriado());
						if (fechaFestivo.getTime().equals(fechaInicial.getTime())) {
							aumentacontador = false;
							break;
						} 
					}
				}
				if(aumentacontador)
					contador++;
			}
		}
		return diasTranscurridos;

	}

	
	/* Metodo para dropdown de enfermedades - autocomplete */
	public List<String> listaEnfermedades(String enfermedadQuery) {
		Enfermedad enfermedad = new Enfermedad();
		enfermedad.setCodCie10(enfermedadQuery);
		enfermedad.setFlgActivo((short) 1);
		enfermedad.setLimitIni(0);
		enfermedad.setLimitFin(1000);
		List<Enfermedad> listaEnfermedades = ComunicacionServiciosAdmin.getEnfemedadLike(enfermedad);
		
		if (!listaEnfermedades.isEmpty()) {
			List<String> listaEnfermedad = new ArrayList<>();
			for (Enfermedad aux : listaEnfermedades) {
				listaEnfermedad.add(aux.getCodCie10());
			}
			return listaEnfermedad;
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SIN_RESULTADO);
			return null;
		}
		
	}

	/* Metodo para dropdown de entidades - autocomplete */
	public List<String> listaEntidades() {
		List<Entidad> listaEntidades = ComunicacionServiciosEnt.entidadesTotalbean();
		if (!listaEntidades.isEmpty()) {
			List<String> listaEntidad = new ArrayList<>();
			for (Entidad aux : listaEntidades) {
				listaEntidad.add(aux.getCodEntidad().toString());
			}
			return listaEntidad;
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SIN_RESULTADO);
			return null;
		}
	}

	/* Metodo para la descripcion de la enfermedad de acuerdo al codigo ingresado */
	public void descripcionEnfermedad(String codCie10) {
		Enfermedad enfermedad = new Enfermedad();
		enfermedad = ComunicacionServiciosAdmin.getEnfemedadCIE(codCie10);
		situacionAdminPersona.setDescripcionEnfermedad(enfermedad.getNombreEnfermedad());
	}

	/* Metodo para la descripcion de la entidad de acuerdo al codigo ingresado */
	public void descripcionEntidad(String id) {
		Entidad entidad = new Entidad();
		entidad.setCodigoSigep(String.valueOf(id));
		entidad.setFlgActivo((short)1);
		List<Entidad> entidades = ComunicacionServiciosEnt.getEntidadesByCodigoSigep(entidad);
		if(!entidades.isEmpty()) {
			entidad = entidades.get(0);
			situacionAdminPersona.setDescripcionEntidad(entidad.getNombreEntidad());
			situacionAdminPersona.setCodEntidadComision(entidad.getCodEntidad().longValue());
		}
		if (entidades.isEmpty() || entidad.getNombreEntidad() == null) {
			situacionAdminPersona.setCodEntidadComision(null);
			situacionAdminPersona.setDescripcionEntidad(null);
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ENTIDAD_INEXISTENTE);
			return;
		}
	}

	public void prorrogarSituacionAdministrativa(SituacionAdminVinculacionExt sitPadre) {
		if (lbLog)
			ocultarFormularios();

		situacionAdminVinculacionExtSelected = sitPadre;
		prorrogaSituacion = new ModificacionSituacionAdministrativa();
		situacionAdmin = ComunicacionServiciosAdmin
				.getSituacionPadre(situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa());
		setSitAdminPadreForm(situacionAdmin.getNombrePadre());
		setSitAdminDepForm(situacionAdmin.getNombreSituacion());
		sitAdminDep = situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa();
		sitAdminPadre = situacionAdmin.getCodSituacionadminPadre();
		this.listaSitAdminDependiente(situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa());
		lbProrrogar = true;
		lbConsultar = false;
		lbEliminar = false;

		historicoProrroga(situacionAdminVinculacionExtSelected.getCodSituacionAdminRelacionLaboral());
	}

	public void historicoProrroga(long codSAVinculacion) {
		this.listModificacionSA = ComunicacionServiciosAdmin.getModificacionSituacionAdministrativa(codSAVinculacion);
	}

	/**
	 * Metodo que busca informacion de la entidad en comision
	 * @param id
	 */
	public void buscarEntidadComision(long id) {
		Entidad entidad = new Entidad();
		entidad.setCodEntidad(new BigDecimal(id));
		entidad.setFlgActivo((short)1);
		List<Entidad> entidades = ComunicacionServiciosEnt.getEntidadesFiltro(entidad);
		if(!entidades.isEmpty()) {
			entidad = entidades.get(0);
			situacionAdminPersona.setDescripcionEntidad(entidad.getNombreEntidad());
			situacionAdminPersona.setCodCodigoSigepEntidad(entidad.getCodigoSigep());
		}
	}
	
	/**
	 * Metodo que se encarga de obtener la información de la situacion administrativa del encargo que esta ocupando la persona
	 */
	public void obtenerInfoSACargoTemporalOcupado(SituacionAdminVinculacionExt sitActual) {
		cargoTemporalAOcupar = new EntidadPlantaDetalleExt();
		
		if(sitActual != null && sitActual.getCodSituacionAdminRelacionLaboral() != null) {
			SituacionAdminVinculacion sa = new SituacionAdminVinculacion();
			sa.setCodSAOcupaTemporal(new BigDecimal (sitActual.getCodSituacionAdminRelacionLaboral()));
			SituacionAdminVinculacion result = ComunicacionServiciosVin.getSituacionAdminVincPorCodSAOcupaTemporal(sa);
			if(result.getCodSituacionAdminRelacionLaboral() != null) {
				cargoTemporalAOcupar.setCodSituacionAdminRelacionLaboral(result.getCodSituacionAdminRelacionLaboral() != null ? result.getCodSituacionAdminRelacionLaboral().intValue() : null );
				cargoTemporalAOcupar.setFechaInicioSA(result.getFechaInicio());
				cargoTemporalAOcupar.setFechaFinSA(result.getFechaFin());
			}
		}
	}
	
	
	public void modificarSituacionAdministrativa(SituacionAdminVinculacionExt sitPadre) {
		obtenerInfoSACargoTemporalOcupado(sitPadre);
		try {
			flgValidaRolPermiso = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO,
					RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_ENTIDADES);
			if (flgValidaRolPermiso == false) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignado", e);
		}

		this.flgModificar = true;

		situacionAdminVinculacionExtSelected = sitPadre;
		situacionAdminPersona = sitPadre;
		if(sitPadre.getCodEntidadComision()!=null) {
			buscarEntidadComision(sitPadre.getCodEntidadComision());
			
		}
		situacionAdmin = ComunicacionServiciosAdmin.getSituacionPadre(situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa());
		if (situacionAdmin.getCodSituacionadminPadre() == null) {
			setSitAdminPadreForm(situacionAdmin.getNombreSituacion());
			setSitAdminDepForm(situacionAdmin.getNombreSituacion());
			sitAdminPadre = situacionAdmin.getCodSituacionAdministrativa();
			sitAdminDep = null;
			this.situacionAdminPersona.setCodTipoSuspencion(situacionAdminVinculacionExtSelected.getCodTipoSuspencion());
		} else {
			setSitAdminPadreForm(situacionAdmin.getNombrePadre());
			setSitAdminDepForm(situacionAdmin.getNombreSituacion());
			sitAdminDep = situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa();
			sitAdminPadre = situacionAdmin.getCodSituacionadminPadre();
			this.situacionAdminPersona.setCodTipoSuspencion(situacionAdminVinculacionExtSelected.getCodTipoSuspencion());
		}
		ocultarFormularios();
		mostrarFormularioRegistoSA();
		lbProrrogar = false;
		lbConsultar = false;
		lbEliminar = false;
	}

	public void consultarSituacionAdministrativaCargo(SituacionAdminVinculacionExt sitPadre) {
		if (lbLog)
			situacionAdminVinculacionExtSelected = sitPadre;
		situacionAdminPersona = sitPadre;
		situacionAdmin = ComunicacionServiciosAdmin.getSituacionPadre(situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa());
		if (situacionAdmin.getCodSituacionadminPadre() == null) {
			setSitAdminPadreForm(situacionAdmin.getNombreSituacion());
			setSitAdminDepForm(null);
		} else {
			setSitAdminPadreForm(situacionAdmin.getNombrePadre());
			setSitAdminDepForm(situacionAdmin.getNombreSituacion());
		}

		this.listaTipoActoAdmin();
		this.habilitarFormEnf = false;
		this.habilitarFormCargo = false;
		this.habilitarFormComision = false;

		if ((situacionAdmin.getCodSituacionAdministrativa()!= null && (situacionAdmin.getCodSituacionAdministrativa() == PROVISIONAL_A_PROVISIONAL 
				|| situacionAdmin.getCodSituacionAdministrativa() == LIBRE_NOMBR_REMOCION_A_LIBRE_NOMBR
				|| situacionAdmin.getCodSituacionAdministrativa() == PERIODO_PRUEBA_ASCENSO_MISMA_ENTIDAD))
				|| ((situacionAdmin.getCodSituacionadminPadre()!=null && situacionAdmin.getCodSituacionadminPadre() == EN_EJERCICIO_EMPLEO_POR_ENCARGO) 
					|| ((situacionAdmin.getCodSituacionadminPadre()!=null && situacionAdmin.getCodSituacionadminPadre() == COMISION)
						&& (situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa()!= null &&
							situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD)))
			) {

			this.habilitarFormEnf = false;
			this.habilitarFormComision = false;
			this.habilitarFormCargo = true;
			if(situacionAdminPersona.getCodEntidadPlantaDetalleEncargo() != null) {
				cargo = ComunicacionServiciosVin.getEntidadPlantaDetalleId(situacionAdminPersona.getCodEntidadPlantaDetalleEncargo());
			}
			
			if (cargo != null)
				this.nombreCargoEncargo = cargo.getNombreDenominacion() + "-" + cargo.getNombreCodigo() + "-"
						+ cargo.getNombreGrado();

		} else if ((situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD
				|| situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == LICENCIA_POR_ACCIDENTE_TRABAJO
				|| situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == LICENCIA_POR_ENFERMEDAD
				|| situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == LICENCIA_POR_ENFERMEDAD_PROFESIONAL)) {
			this.habilitarFormEnf = true;
			this.habilitarFormCargo = false;
			this.habilitarFormComision = false;

		} else if ((situacionAdmin.getCodSituacionadminPadre() != null && situacionAdmin.getCodSituacionadminPadre() == COMISION
				&& situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() != CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD)) {
			this.habilitarFormEnf = false;
			this.habilitarFormCargo = false;
			this.habilitarFormComision = true;
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogSituacionCargo').show();");
	}

	public void consultarSituacionAdministrativa(SituacionAdminVinculacionExt sitPadre) {
		if (lbLog)
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../situacionesadmin/consultarSituaciones.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		lbProrrogar = false;
		lbConsultar = true;
		lbEliminar = false;
	}

	public void eliminarSituacionAdministrativa(SituacionAdminVinculacionExt sitPadre) {

		if (lbLog)
			System.out.println("eliminarSituacionAdministrativa");

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('eliminarSA2').hide();");

		lbProrrogar = false;
		lbConsultar = false;
		lbEliminar = true;
		situacionAdminVinculacionExtSelected.setFlgActivo((short) 0);
		situacionAdminVinculacionExtSelected.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
		situacionAdminVinculacionExtSelected.setAudCodRol((int) (long) this.getRolAuditoria().getId());
		situacionAdminVinculacionExtSelected.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		situacionAdminVinculacionExtSelected.setAudFechaActualizacion(DateUtils.getFechaSistema());
		SituacionAdminVinculacion valid = ComunicacionServiciosAdmin.setSituacionAdminVinculacion(situacionAdminVinculacionExtSelected);
		if (!valid.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					"Se ha borrado el registro");
			situacionAdminVinculacionExtSelected = new SituacionAdminVinculacionExt();
			mostrarHistoricoSA();
		}

	}

	/**
	 * NCU: 0302 Registrar Prórroga Situación Administrativa
	 */
	public void registrarProrroga() {
		boolean lbGuardar;
		SituacionAdminVinculacion lbActualizar;

		try {
			flgValidaRolPermiso = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO,
					RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_ENTIDADES);
			if (flgValidaRolPermiso == false) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignado", e);
		}

		if (lbLog)
			requestContext = RequestContext.getCurrentInstance();
		prorrogaSituacion.setCodSituacionAdministrativaVinculacion(situacionAdminVinculacionExtSelected.getCodSituacionAdminRelacionLaboral());
		prorrogaSituacion.setAudFechaModificacion(DateUtils.getFechaSistema());
		prorrogaSituacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		prorrogaSituacion.setUsuarioProrroga(this.getUsuarioSesion().getId());
		prorrogaSituacion.setAudCodRol((int) this.getRolAuditoria().getId());
		prorrogaSituacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		prorrogaSituacion.setFechaIngresoProrroga(DateUtils.getFechaSistema());

		lbGuardar = ComunicacionServiciosAdmin.setmodificacionsituacionadministrativa(prorrogaSituacion);
		if (lbGuardar) {
			SituacionAdminVinculacionExt sitAdminVinculacion = new SituacionAdminVinculacionExt();
			sitAdminVinculacion.setCodSituacionAdminRelacionLaboral(
					situacionAdminVinculacionExtSelected.getCodSituacionAdminRelacionLaboral());
			sitAdminVinculacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			sitAdminVinculacion.setAudCodRol((int) this.getRolAuditoria().getId());
			sitAdminVinculacion.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
			sitAdminVinculacion.setFechaInicio(situacionAdminVinculacionExtSelected.getFechaInicio());
			sitAdminVinculacion.setFechaFin(prorrogaSituacion.getFechaFinProrroga());

			if (situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA
					|| situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
					|| situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD
					|| situacionAdminVinculacionExtSelected.getCodSituacionAdministrativa() == PERMISO_REMUNERADO) {
				this.calcularDuracionHabil(sitAdminVinculacion);
			} else {
				this.calcularDuracion(sitAdminVinculacion);
			}

			sitAdminVinculacion.setDuracion(situacionAdminPersona.getDuracion());

			lbActualizar = ComunicacionServiciosAdmin.setSituacionAdminVinculacion(sitAdminVinculacion);
			if (!lbActualizar.isError()) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_PRORROGA_SA_EXITOSA);
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_SIN_RESULTADO);
			}
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_SIN_RESULTADO);
		}

		this.cancelarRegistro();
		this.cerrarModificacionesSitAd();

		lbProrrogar = false;
	}

	public void cerrarModificacionesSitAd() {
		if (lbLog)
			lbProrrogar = false;
		lbConsultar = false;
		lbEliminar = false;
	}

	/**
	 * Valida si la fechaIni es menor a la fechaFin, si es asi retorna true, retorna
	 * false si es igual o mayor
	 * @param fechaIni
	 * @param fechaFin
	 * @return
	 */
	private void ocultarFormularios() {
		this.habilitarFormLic = false;
		this.habilitarFormSA = false;
		this.habilitarFormEnf = false;
		this.habilitarFormCargo = false;
		this.habilitarFormComision = false;
	}

	/**
	 * Metodo para mostrar los campos Clasificacion y Clase
	 */
	public void mostrarClasificacionClase() {
		if ((sitAdminPadre == EN_EJERCICIO_EMPLEO_POR_ENCARGO) 
				|| (sitAdminPadre == COMISION && sitAdminDep == CARGOS_LIBRE_NOMBR_REMOCION_MISMA_ENTIDAD)) {
			flghabilitarClasificacionClase = true;
		} else {
			flghabilitarClasificacionClase = false;
		}
	}

	public void cancelarCargosDisponibles() {
		this.cancelarRegistro();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogCargo').hide();");
	}

	public void abrirEliminarSA() {
		try {
			flgValidaRolPermiso = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO,
					RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_ENTIDADES);
			if (flgValidaRolPermiso == false) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignado", e);
		}
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('eliminarSA').show();");
	}

	public void abrirConfirmacionEliminarSA() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('eliminarSA').hide();");
		context.execute("PF('eliminarSA2').show();");
	}

	public void cancelarEliminarSA() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('eliminarSA').hide();");
	}

	public void cancelarEliminarSA2() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('eliminarSA2').hide();");
	}

	/**
	 * Metodo para traer los cargos de una entidad según la planta
	 * @param codEntidad
	 * @param codClasificacionPlanta
	 * @param codClasePlanta
	 * @param codNaturalezaEmpleo
	 * @return List<EntidadPlantaDetalleExt>
	 * @author Nestor Riasco
	 */
	public void getListaCargos(long codEntidad, long codClasificacionPlanta, long codClasePlanta,
			long codNaturalezaEmpleo, String tipoServicio) {
		switch (tipoServicio) {
		case "GET_VACANTES_TEMP_GLOBAL":
			this.listCargos = ComunicacionServiciosVin.getvacantesTempGlobal(codEntidad, codClasificacionPlanta,
					codClasePlanta, codNaturalezaEmpleo);
			break;
		case "GET_VACANTES_TEMP_PERM_GLOBAL":
			this.listCargos = ComunicacionServiciosVin.getvacantesTempPermGlobal(codEntidad, codClasificacionPlanta,
					codClasePlanta, codNaturalezaEmpleo);
			break;
		case "GET_VACANTES_TEMP_PERM_GLOBAL_ESTRUCTURAL":
			this.listCargos = ComunicacionServiciosVin.getvacantesTemporalPermanteGlobalEstructural(codEntidad, codClasificacionPlanta,
					codClasePlanta, codNaturalezaEmpleo);
			break;	
			
		default:
			this.listCargos = ComunicacionServiciosVin.getvacantesGlobal(codEntidad, codClasificacionPlanta,
					codClasePlanta, codNaturalezaEmpleo);
			break;
		}

	}

	/*
	 * Metodo para listar el historico de SA de la persona elegida y el Cargo
	 * elegido
	 */
	public void mostrarHistoricoSACargo(long codVinculacion) {

		List<EntidadPlantaDetalleExt> listP = ComunicacionServiciosVin.getcargosfuncionario(Long.parseLong("" + persona.getCodPersona()), codVinculacion);
		if (!listP.isEmpty()) {
			cargoSeleccionado = listP.get(0);
			this.flgInformacionSA = true;
		}

		SituacionAdminVinculacionExt filtroHistoricoSA = new SituacionAdminVinculacionExt();
		filtroHistoricoSA.setCodVinculacion(codVinculacion);
		listaSitAdminFuncionarioCargo = ComunicacionServiciosAdmin.getSituacionVinculacion(filtroHistoricoSA);

		for (SituacionAdminVinculacionExt situacion : listaSitAdminFuncionario) {
			if (situacion.getFechaFin() == null) {
				int duracion;
				if (situacion.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA 
						|| situacion.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
						|| situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD
						|| situacion.getCodSituacionAdministrativa() == PERMISO_REMUNERADO) {
					duracion = this.calcularDuracionGenerica(situacion, true);
				} else {
					duracion = this.calcularDuracionGenerica(situacion, false);
				}
				situacion.setDuracion(duracion);
			}
		}
		
		for (SituacionAdminVinculacionExt situacion : listaSitAdminFuncionarioCargo) {
			if (situacion.getFechaFin() == null) {
				int duracion;
				if (situacion.getCodSituacionAdministrativa() == LICENCIA_ORDINARIA 
						|| situacion.getCodSituacionAdministrativa() == LICENCIA_POR_LUTO
						|| situacion.getCodSituacionAdministrativa() == LICENCIA_MATERNINAD_PATERNIDAD
						|| situacion.getCodSituacionAdministrativa() == PERMISO_REMUNERADO) {
					duracion = this.calcularDuracionGenerica(situacion, true);
				} else {
					duracion = this.calcularDuracionGenerica(situacion, false);
				}
				situacion.setDuracion(duracion);
			}
		}
	}

	/*Get y set*/
	
	public Boolean getMostrarTotalDuracion() {
		return mostrarTotalDuracion;
	}

	public void setMostrarTotalDuracion(Boolean mostrarTotalDuracion) {
		this.mostrarTotalDuracion = mostrarTotalDuracion;
	}

	/**
	 * @return el flgVisualizarCancelar
	 */
	public Boolean getFlgVisualizarCancelar() {
		return flgVisualizarCancelar;
	}

	/**
	 * @param flgVisualizarCancelar el flgVisualizarCancelar a establecer
	 */
	public void setFlgVisualizarCancelar(Boolean flgVisualizarCancelar) {
		this.flgVisualizarCancelar = flgVisualizarCancelar;
	}

	/**
	 * @return el cargoSeleccionado
	 */
	public EntidadPlantaDetalleExt getCargoSeleccionado() {
		return cargoSeleccionado;
	}

	/**
	 * @param cargoSeleccionado
	 *            el cargoSeleccionado a establecer
	 */
	public void setCargoSeleccionado(EntidadPlantaDetalleExt cargoSeleccionado) {
		this.cargoSeleccionado = cargoSeleccionado;
	}

	/**
	 * @return el flgInformacionSA
	 */
	public boolean isFlgInformacionSA() {
		return flgInformacionSA;
	}

	/**
	 * @param flgInformacionSA
	 *            el flgInformacionSA a establecer
	 */
	public void setFlgInformacionSA(boolean flgInformacionSA) {
		this.flgInformacionSA = flgInformacionSA;
	}

	/**
	 * @return el vinculacionExt
	 */
	public VinculacionExt getVinculacionExt() {
		return vinculacionExt;
	}

	/**
	 * @param vinculacionExt
	 *            el vinculacionExt a establecer
	 */
	public void setVinculacionExt(VinculacionExt vinculacionExt) {
		this.vinculacionExt = vinculacionExt;
	}

	/**
	 * @return el listaSitAdminFuncionarioCargo
	 */
	public List<SituacionAdminVinculacionExt> getListaSitAdminFuncionarioCargo() {
		return listaSitAdminFuncionarioCargo;
	}

	/**
	 * @param listaSitAdminFuncionarioCargo
	 *            el listaSitAdminFuncionarioCargo a establecer
	 */
	public void setListaSitAdminFuncionarioCargo(List<SituacionAdminVinculacionExt> listaSitAdminFuncionarioCargo) {
		this.listaSitAdminFuncionarioCargo = listaSitAdminFuncionarioCargo;
	}

	public Integer getDuracionComisionTotal() {
		return duracionComisionTotal;
	}

	public void setDuracionComisionTotal(Integer duracionComisionTotal) {
		this.duracionComisionTotal = duracionComisionTotal;
	}
	
	/**
	 * @return the vinculacionActiva
	 */
	public List<VinculacionExt> getVinculacionActiva() {
		return vinculacionActiva;
	}

	/**
	 * @param vinculacionActiva the vinculacionActiva to set
	 */
	public void setVinculacionActiva(List<VinculacionExt> vinculacionActiva) {
		this.vinculacionActiva = vinculacionActiva;
	}
	
	/**
	 * @return the ultimaVinculacionActiva
	 */
	public VinculacionExt getUltimaVinculacionActiva() {
		return ultimaVinculacionActiva;
	}

	/**
	 * @param ultimaVinculacionActiva the ultimaVinculacionActiva to set
	 */
	public void setUltimaVinculacionActiva(VinculacionExt ultimaVinculacionActiva) {
		this.ultimaVinculacionActiva = ultimaVinculacionActiva;
	}

	public Boolean getBoolMostrarVolverEncargar() {
		return boolMostrarVolverEncargar;
	}

	public void setBoolMostrarVolverEncargar(Boolean boolMostrarVolverEncargar) {
		this.boolMostrarVolverEncargar = boolMostrarVolverEncargar;
	}

	/**
	 * @return el mostrarFechaFinRequerida
	 */
	public Boolean getMostrarFechaFinRequerida() {
		return mostrarFechaFinRequerida;
	}

	/**
	 * @param mostrarFechaFinRequerida
	 *            el mostrarFechaFinRequerida a establecer
	 */
	public void setMostrarFechaFinRequerida(Boolean mostrarFechaFinRequerida) {
		this.mostrarFechaFinRequerida = mostrarFechaFinRequerida;
	}

	/**
	 * @return el flgTipoPlanta
	 */
	public Boolean getFlgTipoPlanta() {
		return flgTipoPlanta;
	}

	/**
	 * @param flgTipoPlanta
	 *            el flgTipoPlanta a establecer
	 */
	public void setFlgTipoPlanta(Boolean flgTipoPlanta) {
		this.flgTipoPlanta = flgTipoPlanta;
	}

	/**
	 * @return el listModificacionSA
	 */
	public List<ModificacionSituacionAdministrativaExt> getListModificacionSA() {
		return listModificacionSA;
	}

	/**
	 * @param listModificacionSA
	 *            el listModificacionSA a establecer
	 */
	public void setListModificacionSA(List<ModificacionSituacionAdministrativaExt> listModificacionSA) {
		this.listModificacionSA = listModificacionSA;
	}

	/**
	 * @return el cargo
	 */
	public EntidadPlantaDetalleExt getCargo() {
		return cargo;
	}

	/**
	 * @param cargo
	 *            el cargo a establecer
	 */
	public void setCargo(EntidadPlantaDetalleExt cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return el nombreCargoEncargo
	 */
	public String getNombreCargoEncargo() {
		return nombreCargoEncargo;
	}

	/**
	 * @param nombreCargoEncargo
	 *            el nombreCargoEncargo a establecer
	 */
	public void setNombreCargoEncargo(String nombreCargoEncargo) {
		this.nombreCargoEncargo = nombreCargoEncargo;
	}

	/**
	 * @return el listCargos
	 */
	public List<EntidadPlantaDetalleExt> getListCargos() {
		return listCargos;
	}

	/**
	 * @param listCargos
	 *            el listCargos a establecer
	 */
	public void setListCargos(List<EntidadPlantaDetalleExt> listCargos) {
		this.listCargos = listCargos;
	}

	/**
	 * @return el lngValorEntidad
	 */
	public Long getLngValorEntidad() {
		return lngValorEntidad;
	}

	/**
	 * @param lngValorEntidad
	 *            el lngValorEntidad a establecer
	 */
	public void setLngValorEntidad(Long lngValorEntidad) {
		this.lngValorEntidad = lngValorEntidad;
	}

	/**
	 * @return el flghabilitarClasificacionClase
	 */
	public Boolean getFlghabilitarClasificacionClase() {
		return flghabilitarClasificacionClase;
	}

	/**
	 * @param flghabilitarClasificacionClase
	 *            el flghabilitarClasificacionClase a establecer
	 */
	public void setFlghabilitarClasificacionClase(Boolean flghabilitarClasificacionClase) {
		this.flghabilitarClasificacionClase = flghabilitarClasificacionClase;
	}

	/**
	 * @return the flgValidaRolPermiso
	 */
	public Boolean getFlgValidaRolPermiso() {
		return flgValidaRolPermiso;
	}

	/**
	 * @return el vinculacion
	 */
	public VinculacionExt getVinculacion() {
		return vinculacion;
	}

	/**
	 * @param vinculacion
	 *            el vinculacion a establecer
	 */
	public void setVinculacion(VinculacionExt vinculacion) {
		this.vinculacion = vinculacion;
	}

	/**
	 * @param flgValidaRolPermiso
	 *            the flgValidaRolPermiso to set
	 */
	public void setFlgValidaRolPermiso(Boolean flgValidaRolPermiso) {
		this.flgValidaRolPermiso = flgValidaRolPermiso;
	}

	/**
	 * @return the codTipoIdentificacionBuscar
	 */
	public String getCodTipoIdentificacionBuscar() {
		return codTipoIdentificacionBuscar;
	}

	/**
	 * @param codTipoIdentificacionBuscar
	 *            the codTipoIdentificacionBuscar to set
	 */
	public void setCodTipoIdentificacionBuscar(String codTipoIdentificacionBuscar) {
		this.codTipoIdentificacionBuscar = codTipoIdentificacionBuscar;
	}

	/**
	 * @return the numeroIdentificacionBuscar
	 */
	public String getNumeroIdentificacionBuscar() {
		return numeroIdentificacionBuscar;
	}

	/**
	 * @param numeroIdentificacionBuscar
	 *            the numeroIdentificacionBuscar to set
	 */
	public void setNumeroIdentificacionBuscar(String numeroIdentificacionBuscar) {
		this.numeroIdentificacionBuscar = numeroIdentificacionBuscar != null ? numeroIdentificacionBuscar.toUpperCase()
				: numeroIdentificacionBuscar;
	}

	/**
	 * @return the primerNombreBuscar
	 */
	public String getPrimerNombreBuscar() {
		return primerNombreBuscar;
	}

	/**
	 * @param primerNombreBuscar
	 *            the primerNombreBuscar to set
	 */
	public void setPrimerNombreBuscar(String primerNombreBuscar) {
		this.primerNombreBuscar = primerNombreBuscar;
	}

	/**
	 * @return the primerApellidoBuscar
	 */
	public String getPrimerApellidoBuscar() {
		return primerApellidoBuscar;
	}

	/**
	 * @param primerApellidoBuscar
	 *            the primerApellidoBuscar to set
	 */
	public void setPrimerApellidoBuscar(String primerApellidoBuscar) {
		this.primerApellidoBuscar = primerApellidoBuscar;
	}

	/**
	 * @return the segundoNombreBuscar
	 */
	public String getSegundoNombreBuscar() {
		return segundoNombreBuscar;
	}

	/**
	 * @param segundoNombreBuscar
	 *            the segundoNombreBuscar to set
	 */
	public void setSegundoNombreBuscar(String segundoNombreBuscar) {
		this.segundoNombreBuscar = segundoNombreBuscar;
	}

	/**
	 * @return the segundoApellidoBuscar
	 */
	public String getSegundoApellidoBuscar() {
		return segundoApellidoBuscar;
	}

	/**
	 * @param segundoApellidoBuscar
	 *            the segundoApellidoBuscar to set
	 */
	public void setSegundoApellidoBuscar(String segundoApellidoBuscar) {
		this.segundoApellidoBuscar = segundoApellidoBuscar;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion
	 *            the accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
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
	 * @return the listaFuncionario
	 */
	public List<PersonaExt> getListaFuncionario() {
		return listaFuncionario;
	}

	/**
	 * @param listaFuncionario
	 *            the listaFuncionario to set
	 */
	public void setListaFuncionario(List<PersonaExt> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}

	/**
	 * @return the habilitarFormulario
	 */
	public boolean isHabilitarFormulario() {
		return habilitarFormulario;
	}

	/**
	 * @param habilitarFormulario
	 *            the habilitarFormulario to set
	 */
	public void setHabilitarFormulario(boolean habilitarFormulario) {
		this.habilitarFormulario = habilitarFormulario;
	}

	/**
	 * @return the idFunc
	 */
	public Long getIdFunc() {
		return idFunc;
	}

	/**
	 * @param idFunc
	 *            the idFunc to set
	 */
	public void setIdFunc(Long idFunc) {
		this.idFunc = idFunc;
	}

	/**
	 * @return the situacionAdmin
	 */
	public SituacionAdministrativaExt getSituacionAdmin() {
		return situacionAdmin;
	}

	/**
	 * @param situacionAdmin
	 *            the situacionAdmin to set
	 */
	public void setSituacionAdmin(SituacionAdministrativaExt situacionAdmin) {
		this.situacionAdmin = situacionAdmin;
	}

	/**
	 * @return the listaSitAdminFuncionario
	 */
	public List<SituacionAdminVinculacionExt> getListaSitAdminFuncionario() {
		return listaSitAdminFuncionario;
	}

	/**
	 * @param listaSitAdminFuncionario
	 *            the listaSitAdminFuncionario to set
	 */
	public void setListaSitAdminFuncionario(List<SituacionAdminVinculacionExt> listaSitAdminFuncionario) {
		this.listaSitAdminFuncionario = listaSitAdminFuncionario;
	}

	/**
	 * @return the mostrarIconoProrroga
	 */
	public Boolean getMostrarIconoProrroga() {
		return mostrarIconoProrroga;
	}

	/**
	 * @param mostrarIconoProrroga
	 *            the mostrarIconoProrroga to set
	 */
	public void setMostrarIconoProrroga(Boolean mostrarIconoProrroga) {
		this.mostrarIconoProrroga = mostrarIconoProrroga;
	}

	/**
	 * @return the listSitAdminP
	 */
	public List<SelectItem> getListSitAdminP() {
		return listSitAdminP;
	}

	/**
	 * @param listSitAdminP
	 *            the listSitAdminP to set
	 */
	public void setListSitAdminP(List<SelectItem> listSitAdminP) {
		this.listSitAdminP = listSitAdminP;
	}

	/**
	 * @return the listSitAdminDependiente
	 */
	public List<SelectItem> getListSitAdminDependiente() {
		return listSitAdminDependiente;
	}

	/**
	 * @param listSitAdminDependiente
	 *            the listSitAdminDependiente to set
	 */
	public void setListSitAdminDependiente(List<SelectItem> listSitAdminDependiente) {
		this.listSitAdminDependiente = listSitAdminDependiente;
	}

	/**
	 * @return the codPersona
	 */
	public long getCodPersona() {
		return codPersona;
	}

	/**
	 * @param codPersona
	 *            the codPersona to set
	 */
	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * @return the sitAdminPadre
	 */
	public Long getSitAdminPadre() {
		return sitAdminPadre;
	}

	/**
	 * @param sitAdminPadre
	 *            the sitAdminPadre to set
	 */
	public void setSitAdminPadre(Long sitAdminPadre) {
		this.sitAdminPadre = sitAdminPadre;
	}

	/**
	 * @return the sitAdminDep
	 */
	public Long getSitAdminDep() {
		return sitAdminDep;
	}

	/**
	 * @param sitAdminDep
	 *            the sitAdminDep to set
	 */
	public void setSitAdminDep(Long sitAdminDep) {
		this.sitAdminDep = sitAdminDep;
	}

	/**
	 * @return the habilitarSADEP
	 */
	public Boolean getHabilitarSADEP() {
		return habilitarSADEP;
	}

	/**
	 * @param habilitarSADEP
	 *            the habilitarSADEP to set
	 */
	public void setHabilitarSADEP(Boolean habilitarSADEP) {
		this.habilitarSADEP = habilitarSADEP;
	}

	/**
	 * @return the habilitarFormLic
	 */
	public Boolean getHabilitarFormLic() {
		return habilitarFormLic;
	}

	/**
	 * @param habilitarFormLic
	 *            the habilitarFormLic to set
	 */
	public void setHabilitarFormLic(Boolean habilitarFormLic) {
		this.habilitarFormLic = habilitarFormLic;
	}

	/**
	 * @return the habilitarFormEnf
	 */
	public Boolean getHabilitarFormEnf() {
		return habilitarFormEnf;
	}

	/**
	 * @param habilitarFormEnf
	 *            the habilitarFormEnf to set
	 */
	public void setHabilitarFormEnf(Boolean habilitarFormEnf) {
		this.habilitarFormEnf = habilitarFormEnf;
	}

	/**
	 * @return the habilitarFormSA
	 */
	public Boolean getHabilitarFormSA() {
		return habilitarFormSA;
	}

	/**
	 * @param habilitarFormSA
	 *            the habilitarFormSA to set
	 */
	public void setHabilitarFormSA(Boolean habilitarFormSA) {
		this.habilitarFormSA = habilitarFormSA;
	}

	/**
	 * @return the habilitarFormCargo
	 */
	public Boolean getHabilitarFormCargo() {
		return habilitarFormCargo;
	}

	/**
	 * @param habilitarFormCargo
	 *            the habilitarFormCargo to set
	 */
	public void setHabilitarFormCargo(Boolean habilitarFormCargo) {
		this.habilitarFormCargo = habilitarFormCargo;
	}

	/**
	 * @return the habilitarFormComision
	 */
	public Boolean getHabilitarFormComision() {
		return habilitarFormComision;
	}

	/**
	 * @param habilitarFormComision
	 *            the habilitarFormComision to set
	 */
	public void setHabilitarFormComision(Boolean habilitarFormComision) {
		this.habilitarFormComision = habilitarFormComision;
	}

	/**
	 * @return the habilitarFormLibreta
	 */
	public Boolean getHabilitarFormLibreta() {
		return habilitarFormLibreta;
	}

	/**
	 * @param habilitarFormLibreta
	 *            the habilitarFormLibreta to set
	 */
	public void setHabilitarFormLibreta(Boolean habilitarFormLibreta) {
		this.habilitarFormLibreta = habilitarFormLibreta;
	}

	/**
	 * @return the habilitarFormOtro
	 */
	public Boolean getHabilitarFormOtro() {
		return habilitarFormOtro;
	}

	/**
	 * @param habilitarFormOtro
	 *            the habilitarFormOtro to set
	 */
	public void setHabilitarFormOtro(Boolean habilitarFormOtro) {
		this.habilitarFormOtro = habilitarFormOtro;
	}

	/**
	 * @return the habilitarFormProvisional
	 */
	public Boolean getHabilitarFormProvisional() {
		return habilitarFormProvisional;
	}

	/**
	 * @param habilitarFormProvisional
	 *            the habilitarFormProvisional to set
	 */
	public void setHabilitarFormProvisional(Boolean habilitarFormProvisional) {
		this.habilitarFormProvisional = habilitarFormProvisional;
	}

	/**
	 * @return the habilitarFormPruebaOtraEntidad
	 */
	public Boolean getHabilitarFormPruebaOtraEntidad() {
		return habilitarFormPruebaOtraEntidad;
	}

	/**
	 * @param habilitarFormPruebaOtraEntidad
	 *            the habilitarFormPruebaOtraEntidad to set
	 */
	public void setHabilitarFormPruebaOtraEntidad(Boolean habilitarFormPruebaOtraEntidad) {
		this.habilitarFormPruebaOtraEntidad = habilitarFormPruebaOtraEntidad;
	}

	/**
	 * @return the situacionAdminPersona
	 */
	public SituacionAdminVinculacionExt getSituacionAdminPersona() {
		return situacionAdminPersona;
	}

	/**
	 * @param situacionAdminPersona
	 *            the situacionAdminPersona to set
	 */
	public void setSituacionAdminPersona(SituacionAdminVinculacionExt situacionAdminPersona) {
		this.situacionAdminPersona = situacionAdminPersona;
	}

	/**
	 * @return the sitAdminPadreForm
	 */
	public String getSitAdminPadreForm() {
		return sitAdminPadreForm;
	}

	/**
	 * @param sitAdminPadreForm
	 *            the sitAdminPadreForm to set
	 */
	public void setSitAdminPadreForm(String sitAdminPadreForm) {
		this.sitAdminPadreForm = sitAdminPadreForm;
	}

	/**
	 * @return the sitAdminDepForm
	 */
	public String getSitAdminDepForm() {
		return sitAdminDepForm;
	}

	/**
	 * @param sitAdminDepForm
	 *            the sitAdminDepForm to set
	 */
	public void setSitAdminDepForm(String sitAdminDepForm) {
		this.sitAdminDepForm = sitAdminDepForm;
	}

	/**
	 * @return the accionModificar
	 */
	public boolean isAccionModificar() {
		return accionModificar;
	}

	/**
	 * @param accionModificar
	 *            the accionModificar to set
	 */
	public void setAccionModificar(boolean accionModificar) {
		this.accionModificar = accionModificar;
	}

	/**
	 * @return the listaTipoActoAdmin
	 */
	public List<SelectItem> getListaTipoActoAdmin() {
		return listaTipoActoAdmin;
	}

	/**
	 * @param listaTipoActoAdmin
	 *            the listaTipoActoAdmin to set
	 */
	public void setListaTipoActoAdmin(List<SelectItem> listaTipoActoAdmin) {
		this.listaTipoActoAdmin = listaTipoActoAdmin;
	}

	/**
	 * @return the deshabilitarfechafin
	 */
	public Boolean getDeshabilitarfechafin() {
		return deshabilitarfechafin;
	}

	/**
	 * @param deshabilitarfechafin
	 *            the deshabilitarfechafin to set
	 */
	public void setDeshabilitarfechafin(Boolean deshabilitarfechafin) {
		this.deshabilitarfechafin = deshabilitarfechafin;
	}

	/**
	 * @return the mostrarVolverEncargar
	 */
	public Boolean getMostrarVolverEncargar() {
		return mostrarVolverEncargar;
	}

	/**
	 * @param mostrarVolverEncargar
	 *            the mostrarVolverEncargar to set
	 */
	public void setMostrarVolverEncargar(Boolean mostrarVolverEncargar) {
		this.mostrarVolverEncargar = mostrarVolverEncargar;
	}

	/**
	 * @return the mostrarDescEntidad
	 */
	public Boolean getMostrarDescEntidad() {
		return mostrarDescEntidad;
	}

	/**
	 * @param mostrarDescEntidad
	 *            the mostrarDescEntidad to set
	 */
	public void setMostrarDescEntidad(Boolean mostrarDescEntidad) {
		this.mostrarDescEntidad = mostrarDescEntidad;
	}

	/**
	 * @return the mostrarSitAminDep
	 */
	public Boolean getMostrarSitAminDep() {
		return mostrarSitAminDep;
	}

	/**
	 * @param mostrarSitAminDep
	 *            the mostrarSitAminDep to set
	 */
	public void setMostrarSitAminDep(Boolean mostrarSitAminDep) {
		this.mostrarSitAminDep = mostrarSitAminDep;
	}

	/**
	 * @return the mostrarSitAminDepSuspension
	 */
	public Boolean getMostrarSitAminDepSuspension() {
		return mostrarSitAminDepSuspension;
	}

	/**
	 * @param mostrarSitAminDepSuspension
	 *            the mostrarSitAminDepSuspension to set
	 */
	public void setMostrarSitAminDepSuspension(Boolean mostrarSitAminDepSuspension) {
		this.mostrarSitAminDepSuspension = mostrarSitAminDepSuspension;
	}

	/**
	 * @return the mostrarFechaFin
	 */
	public Boolean getMostrarFechaFin() {
		return mostrarFechaFin;
	}

	/**
	 * @param mostrarFechaFin
	 *            the mostrarFechaFin to set
	 */
	public void setMostrarFechaFin(Boolean mostrarFechaFin) {
		this.mostrarFechaFin = mostrarFechaFin;
	}

	/**
	 * @return the mostrarfechaFinSuspension
	 */
	public Boolean getMostrarfechaFinSuspension() {
		return mostrarfechaFinSuspension;
	}

	/**
	 * @param mostrarfechaFinSuspension
	 *            the mostrarfechaFinSuspension to set
	 */
	public void setMostrarfechaFinSuspension(Boolean mostrarfechaFinSuspension) {
		this.mostrarfechaFinSuspension = mostrarfechaFinSuspension;
	}

	/**
	 * @return the lbLog
	 */
	public boolean isLbLog() {
		return lbLog;
	}

	/**
	 * @param lbLog
	 *            the lbLog to set
	 */
	public void setLbLog(boolean lbLog) {
		this.lbLog = lbLog;
	}

	/**
	 * @return the requestContext
	 */
	public RequestContext getRequestContext() {
		return requestContext;
	}

	/**
	 * @param requestContext
	 *            the requestContext to set
	 */
	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}

	/**
	 * @return el flgModificar
	 */
	public Boolean getFlgModificar() {
		return flgModificar;
	}

	/**
	 * @param flgModificar
	 *            el flgModificar a establecer
	 */
	public void setFlgModificar(Boolean flgModificar) {
		this.flgModificar = flgModificar;
	}
	
	public Boolean getValorEncargar() {
		return valorEncargar;
	}

	public void setValorEncargar(Boolean valorEncargar) {
		this.valorEncargar = valorEncargar;
	}
	
	public boolean isLbProrrogar() {
		return lbProrrogar;
	}

	public void setLbProrrogar(boolean lbProrrogar) {
		this.lbProrrogar = lbProrrogar;
	}

	public boolean isLbConsultar() {
		return lbConsultar;
	}

	public void setLbConsultar(boolean lbConsultar) {
		this.lbConsultar = lbConsultar;
	}

	public boolean isLbEliminar() {
		return lbEliminar;
	}

	public void setLbEliminar(boolean lbEliminar) {
		this.lbEliminar = lbEliminar;
	}

	public String getStrMensajeModificacionSitAdmin() {
		return strMensajeModificacionSitAdmin;
	}

	public void setStrMensajeModificacionSitAdmin(String strMensajeModificacionSitAdmin) {
		this.strMensajeModificacionSitAdmin = strMensajeModificacionSitAdmin;
	}

	public SituacionAdminVinculacionExt getSituacionAdminVinculacionExtSelected() {
		return situacionAdminVinculacionExtSelected;
	}

	public void setSituacionAdminVinculacionExtSelected(
			SituacionAdminVinculacionExt situacionAdminVinculacionExtSelected) {
		this.situacionAdminVinculacionExtSelected = situacionAdminVinculacionExtSelected;
	}

	public ModificacionSituacionAdministrativa getProrrogaSituacion() {
		return prorrogaSituacion;
	}

	public void setProrrogaSituacion(ModificacionSituacionAdministrativa prorrogaSituacion) {
		this.prorrogaSituacion = prorrogaSituacion;
	}
	
	/**
	 * @return the cargoTemporalAOcupar
	 */
	public EntidadPlantaDetalleExt getCargoTemporalAOcupar() {
		return cargoTemporalAOcupar;
	}

	/**
	 * @param cargoTemporalAOcupar the cargoTemporalAOcupar to set
	 */
	public void setCargoTemporalAOcupar(EntidadPlantaDetalleExt cargoTemporalAOcupar) {
		this.cargoTemporalAOcupar = cargoTemporalAOcupar;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
	}

	@Override
	public String persist() throws NotSupportedException {
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
	}

	

}
