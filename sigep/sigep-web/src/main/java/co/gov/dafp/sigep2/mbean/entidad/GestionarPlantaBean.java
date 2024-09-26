package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.converter.ExportacionDocumentoConverter;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Nomenclatura;
import co.gov.dafp.sigep2.entities.NomenclaturaDenominacion;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.PlantaPersonaUtlUan;
import co.gov.dafp.sigep2.entities.SequenciasSigep;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PlantaPersonaUtlUanExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Named
@ViewScoped
public class GestionarPlantaBean extends BaseBean implements Serializable {

	private static final String MSG_ERROR_RE = "Error al intentar redireccionar, informacion del error: ";
	private static final String TIT_ERROR_RE = "Error";
	private static final String MSG_NO_DATO = "Seleccione";
	private static final Logger logger = Logger.getInstance(GestionarPlantaBean.class);
	private String numeroNorma;
	private static final long serialVersionUID = 1L;

	private Long 	codPlantaProceso;
	private Long 	idPlanta;
	private Long 	codEntidadProceso;
	private Long 	varSalariosMinimos;
	private BigDecimal	varMontoMaximo;
	private boolean estadoFiltros;
	private boolean estadoCreacion;
	private boolean estadoSecCreacion;
	private boolean estadoInfoEntidad;
	private boolean soloLectura;
	private boolean estTablaPla;
	private Boolean flgValidaRolPermiso;
	private Boolean flgisRolSoloConsulta;
	private Boolean required;
	private Boolean requiredClasificacion;
	private Boolean requiredTemporal;
	private Boolean blnEliminarPlantaConVinc;
	private Boolean blnPlantaUtl;
	private Boolean blnModificarPlanta;

	private Entidad entProceso;
	private Boolean blnFechaFinObligatoria;
	private Norma 	norma;
	private Norma 	normaSeleccionada;
	
	private List<SelectItem> lstDenominacionesEntidad;
	private List<SelectItem> lstNiveles;
	private List<SelectItem> lstGrados;
	private List<SelectItem> lstCodigos;
	
	private EntidadPlantaExt 	entiPlantPer;
	private EntidadPlantaExt 	entidadPlantaPersonal;
	private EntidadPlantaExt	plantaSeleccionada;
	private EntidadPlantaDetalleExt cargoSeleccionado;
	
	private List<VinculacionExt> listPersVinc;
	private List<VinculacionExt> listPersVincCargo;
	
	private List<EntidadPlantaDetalleExt> 	resultadosCargos;
	private List<NomenclaturaDenominacion> 	listaNomenclaturaDenominacion;
	private List<EntidadPlantaDetalleExt> 	resultadosPlantas;
	private List<EntidadPlantaExt> 			resultadosPlanta;
	private List<NomenclaturaExt> 			lstNomenclaturaEntidad;
	private List<Norma> 					lstNormas;
	private Map<String, Long>	 			listaDependencias;
	private List<PlantaPersonaUtlUanExt>	listaResponsablesUTL;
	private List<HojaVidaExt>				listaPersonasVinculadasUTL;
	private List<PlantaPersonaUtlUanExt>	listaUTLExcedenCargos;
	private List<PlantaPersonaUtlUanExt> 	lstVinculacionUTL;
	private List<SelectItem>				lstDependencias;
	List<SelectItem> 						lstNivel;
	
	private VinculacionExt					ultimaVinculacionCargo;
	
	
	/* Vbles de auditoria */
	private Integer codAudAccionInsert = 3;
	
	private boolean bloqueoNomenclatura;
	private boolean estadoJustifi;
	private boolean estadoTemporalidad;
	private boolean estadoEdicion;
	private String 	tituloBotonCrearPlanta;
	private String 	mensajeGuardadoPlanta;
	private Long 	cargoPublicoSel;
	private String	lblMensajeCargosVinculacion;
	private String strTargetExportar;
	
	@Override
	@PostConstruct
	public void init() {
		initialization();
		cerrarSessionFuncion(AMBITO_POLITICAS);
		if (!validarPermisoRolGestionarPlanta()) {
			mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,TitlesBundleConstants.getStringMessagesBundle(
							TitlesBundleConstants.TTL_ACEPTAR, getLocale()), "window.location.assign('entidad/../../gestionarEntidad.xhtml?faces-redirect=true')");
			return;
		}
		if (flgisRolSoloConsulta)
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_RECURSO_SOLO_CONSULTA);
	}

	public void initialization() {
		this.entProceso = (Entidad) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codEntidadProceso");
		if (entProceso != null)
			this.codEntidadProceso = entProceso.getCodEntidad().longValue();
		inicializarVariables();
		this.mostrarTablaPlantasPersonal();
		this.cargarListasGlobales();
	}
	
	/**
	 * Metodo que se encarga de cargar de forma global las listas a ser utilizadas en el aplicativo
	 * Carga las dependencias de la entidad.
	 * Carga los niveles jerarquicos que están asociados a la nomenclatura de la entidad 
	 */
	public void cargarListasGlobales() {
		getDependencias();
		getNivelNomenclaturaEntidad();
	}
	
	
	/**Metodo que se encarga de cargas dependencias de la entidad*/
	public void getDependencias() {
		try {
			DependenciaEntidadExt filtro = new DependenciaEntidadExt();
			filtro.setCodEntidad(this.codEntidadProceso);
			filtro.setFlgActivo((short) 1);
			List<DependenciaEntidadExt> lista = ComunicacionServiciosVin.getDependenciaEntidadFilter(filtro);
			
			if (!lista.isEmpty()) {
				for (DependenciaEntidadExt iter : lista)
					lstDependencias.add(new SelectItem(iter.getCodDependenciaEntidad(), iter.getNombreDependencia()));
			}
		}catch (NullPointerException ex) {
			logger.error("public List<SelectItem> getDependencias(Long codTipoPlanta) GestionarPlantaBean.java Error: " + ex.getMessage());
		}
	}
	
	/**
	 * Metodo para traer los niveles pertenecientes a una nomenclatura
	 * Para el componente de Gestionar Planta
	 * @param codNomenclatura
	 * @return List<SelectItem>
	 */	
	public void getNivelNomenclaturaEntidad() {
		lstNivel = new ArrayList<>();
		if(plantaSeleccionada.getCodNomenclatura() != null) {
			NomenclaturaDenominacion buscador = new NomenclaturaDenominacion();
			buscador.setFlgActivo((short) 1);
			buscador.setCodNomenclatura(BigDecimal.valueOf(plantaSeleccionada.getCodNomenclatura()));
			List<NomenclaturaDenominacionExt> listP = ComunicacionServiciosEnt.getNivelJerarquicoPorNomenclatura(buscador);
			try {
				if (!listP.isEmpty()) {
					for (NomenclaturaDenominacionExt aux : listP) {
						lstNivel.add(new SelectItem(aux.getCodNivelJerarquico(), aux.getNivelCargo().toUpperCase()));
					}
				}
			} catch (NullPointerException e) {
				e.getStackTrace();
			}
		}
	}
	
	/**Metodo que inicializa las variables a ser utilizadas por el aplicativo*/
	public void inicializarVariables() {
		this.entiPlantPer 			= new EntidadPlantaExt();
		lstNomenclaturaEntidad 		= new ArrayList<>();
		entidadPlantaPersonal 		= new EntidadPlantaExt();
		norma 						= new Norma();
		plantaSeleccionada			= new EntidadPlantaExt();
		listaResponsablesUTL		= new ArrayList<>();
		lstDependencias				= new ArrayList<>();
		lstNivel					= new ArrayList<>();
		flgValidaRolPermiso			= false;
		flgisRolSoloConsulta 		= false;
		required 					= false;
		requiredClasificacion 		= false;
		requiredTemporal 			= false;
		soloLectura 				= false;
		estadoTemporalidad 			= true;
		tituloBotonCrearPlanta 		= "";
		cargoPublicoSel 			= null;
		estTablaPla					= true;
		blnFechaFinObligatoria		= false;
		blnEliminarPlantaConVinc 	= false;
		blnPlantaUtl				= false;
		codAudAccionInsert 			= Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		varSalariosMinimos			= 0L;
		varMontoMaximo				= new BigDecimal(0);
		lblMensajeCargosVinculacion = "";
		strTargetExportar			= "";
	}

	/**
	 * Metodo que validad si el rol en sesion tiene permisos para gestionar plantas.
	 */
	public boolean validarPermisoRolGestionarPlanta() {
		if (getUsuarioSesion() != null) {
			try {
				this.flgValidaRolPermiso = this.usuarioTieneRolAsignadoSinJerarquia(
						RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.JEFE_TALENTO_HUMANO);
				if (!this.flgValidaRolPermiso) {
					this.flgisRolSoloConsulta = this.usuarioTieneRolAsignadoSinJerarquia(
							RolDTO.SUPER_ADMINISTRADOR, RolDTO.AUDITOR, RolDTO.ADMINISTRADOR_FUNCIONAL, 
							RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS, 
							RolDTO.OPERADOR_ENTIDADES, RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_CONTROL_INTERNO, 
							RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS, RolDTO.OPERADOR_TALENTO_HUMANO);
					return this.flgisRolSoloConsulta;
				} 
				else
					return true;

			} catch (SIGEP2SistemaException e) {
				GestionarPlantaBean.logger.error("void init() usuarioTieneRolAsignado", e);
			}
		} 
		else
			return false;
		return true;
	}

	/**
	 * Metodo muestra las plantas de personal que existen para una entidad
	 * especifica.
	 */
	public void mostrarTablaPlantasPersonal() {		
		this.resultadosPlanta 	= new ArrayList<>();
		if(entidadPlantaPersonal.getNumeroNorma()!= null && entidadPlantaPersonal.getNumeroNorma().equals("")) {
			entidadPlantaPersonal.setNumeroNorma(null);
		}
		this.entidadPlantaPersonal.setFlgActivo((short) 1);
		this.entidadPlantaPersonal.setCodEntidad(codEntidadProceso);
		this.entidadPlantaPersonal.setLimitInit(0);
		this.entidadPlantaPersonal.setLimitEnd(100);
		this.resultadosPlanta = ComunicacionServiciosVin.getEntidadPlantaFilter(entidadPlantaPersonal);
	}

	/**
	 * Metodo que permite crear una planta de personal
	 */
	public void crearPlanta() {
		this.cargarNomenclaturasEntidad();
		if(this.lstNomenclaturaEntidad.isEmpty()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_PLANTA_SIN_NOMENCLATURA_CREAR);
			return;
		}
		reiniciarCamposNorma();
		inicializarVariablesBoolean();
		this.plantaSeleccionada 	= new EntidadPlantaExt();
		this.resultadosPlantas 		= new ArrayList<>();
		this.resultadosCargos 		= new ArrayList<>();
		this.listaResponsablesUTL	= new ArrayList<>();
		this.estadoSecCreacion 		= true;
		this.tituloBotonCrearPlanta = TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_PLANTA_BOTON_CREAR, this.getLocale());
	}
	
	/**
	 * Metodo que permite modificar una planta de personal existente
	 * @param pla
	 */
	public void modificarPlanta(EntidadPlantaExt pla) {
		try {
			this.resultadosCargos 		= new ArrayList<>();
			this.plantaSeleccionada 	= new EntidadPlantaExt();
			this.resultadosPlantas 		= new ArrayList<>();
			this.listaResponsablesUTL	= new ArrayList<>();
			inicializarVariablesBoolean();
			reiniciarCamposNorma();
			this.plantaSeleccionada = pla;
			getNivelNomenclaturaEntidad();
			this.estadoEdicion 		= true;
			this.estadoSecCreacion 	= true;
			blnModificarPlanta		= true;
			buscarNormaPlanta();
			validarFechaFin();
			
			/*Cuando la planta posee una nomenclatura y no es utl*/
			if(this.plantaSeleccionada.getFlgPlantaUTL()!= null && this.plantaSeleccionada.getFlgPlantaUTL() == 0 ) {
				cargarPlantaCargos();
			}else {
				validarPlantaUTL();
				calcularMontoMaximoUTL();
			}
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage());
			logger.error("public void modificarPlanta(EntidadPlantaExt pla) GestionarPlantaBean " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}
	
	/**
	 * Metodo que permite consultar la planta de personal seleccionada desde la
	 * vista.
	 * @param pla
	 */
	public void consultarPlanta(EntidadPlantaExt pla) {
		try {
			this.resultadosCargos 		= new ArrayList<>();
			this.listaResponsablesUTL	= new ArrayList<>();
			inicializarVariablesBoolean();
			reiniciarCamposNorma();
			this.plantaSeleccionada 	= pla;
			this.bloqueoNomenclatura	= true;
			this.estadoSecCreacion 		= true;
			this.soloLectura 			= true;
			this.estadoTemporalidad 	= true;
			
			buscarNormaPlanta();
			validarFechaFin();
			validarPlantaUTL();
			calcularMontoMaximoUTL();
			if(this.plantaSeleccionada.getFlgPlantaUTL()!= null && this.plantaSeleccionada.getFlgPlantaUTL() == 0 ) {
				cargarPlantaCargos();
				strTargetExportar = "tablaImpresionCargos";
			}else {	
				strTargetExportar		= "tablaImpresionCargosUtl";
				llenarTablaResponsablesUTL();
			}
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage());
			logger.error("public void consultarPlanta(EntidadPlantaExt pla) GestionarPlantaBean "+ ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}
	
	/**
	 * Metodo que carga todas la informacion necesaria de la planta tipo cargo seleccionada para su modificacion o consulta
	 */
	public void cargarPlantaCargos() {
		cargarListaCargos();
		this.cargarDenominacionDetalle();
		this.configurarValorCargoInicial();
		this.configurarFlags();
	}
	/**
	 * Metodo que inicializa todas las variables booleanas del sistema
	 */
	public void inicializarVariablesBoolean() {
		blnFechaFinObligatoria		= false;
		this.soloLectura 			= false;
		this.bloqueoNomenclatura 	= false;
		this.estadoEdicion		 	= false;
		this.estadoSecCreacion 		= false;
		this.estadoJustifi 			= false;
		blnPlantaUtl				= false;
	}

	/**
	 * Metodo que valida si existen nomenclaturas asocidas a la entidad
	 */
	public void cargarNomenclaturasEntidad() {
		NomenclaturaExt filtroNomenclatura = new NomenclaturaExt();
		filtroNomenclatura.setFlgActivo((short) 1);
		filtroNomenclatura.setFlgDefinitivo((short)1);
		filtroNomenclatura.setFlgGenerica((short) 0);
		filtroNomenclatura.setCodEntidad(new BigDecimal(codEntidadProceso));
		this.lstNomenclaturaEntidad = ComunicacionServiciosEnt.getNomenclaturaFiltro(filtroNomenclatura);
	}

	/**
	 * Metodo que carga la lista de cargos de la planta
	 */
	public void cargarListaCargos() {
		EntidadPlantaDetalleExt consulta = new EntidadPlantaDetalleExt();
		consulta.setCodEntidadPlanta(new BigDecimal(plantaSeleccionada.getCodEntidadPlanta()));
		consulta.setLimitInit(0);
		consulta.setLimitEnd(2000);
		consulta.setFlgActivo((short) 1);
		this.resultadosCargos = ComunicacionServiciosEnt.getPlantaPersonalEntidadCargos(consulta);
		this.bloqueoNomenclatura = !this.resultadosCargos.isEmpty();
	}

	/**
	 * Metodo que permite eliminar una planta seleccionada. Se llama desde el boton
	 * "Eliminar", este abre un dialogo de confirmacion de eliminar.
	 * @param pla
	 */
	public void eliminarPlanta(EntidadPlantaExt pla) {
		this.estadoSecCreacion = false;
		this.plantaSeleccionada = pla;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("$('#dialogConfirmElimTipoPlanta').modal('show')");
	}

	/**
	 * Metodo que verifica si la planta seleccionada para eliminar tiene
	 * vinculaciones asociadas cargos o no. Se llama desde el panel
	 * "dialogConfirmElimTipoPlanta".
	 * Asi mismo, verifica si la planta a eliminar es una planta UTL
	 * @throws IOException
	 */
	public void verificaPlantaEliminar() throws IOException {
		if(plantaSeleccionada!= null && plantaSeleccionada.getFlgPlantaUTL() == 0) {
			validarEliminacionPlantaConCargos();
		}else {
			validarEliminacionPlantaUtl();
		}
	}
	
	/**
	 * Metodo que valida si la planta a eliminar posee vinculaciones activas
	 */
	public void validarEliminacionPlantaConCargos() {
		this.resultadosCargos = new ArrayList<>();
		this.listPersVinc = new ArrayList<>();
		VinculacionExt objetVinculacion = new VinculacionExt();
		objetVinculacion.setCodEntidad(plantaSeleccionada.getCodEntidad());
		objetVinculacion.setCodPlantaPersonal(plantaSeleccionada.getCodEntidadPlanta());
		listPersVinc = ComunicacionServiciosVin.getVinculacionesPlantaPersonal(objetVinculacion);
		if (listPersVinc == null || listPersVinc.isEmpty()) {
			blnEliminarPlantaConVinc = false;
			EntidadPlantaDetalleExt consulta = new EntidadPlantaDetalleExt();
			consulta.setCodEntidadPlanta(new BigDecimal(plantaSeleccionada.getCodEntidadPlanta()));
			consulta.setLimitInit(0);
			consulta.setLimitEnd(100);
			consulta.setFlgActivo((short) 1);
			resultadosCargos = ComunicacionServiciosEnt.getPlantaPersonalEntidadCargos(consulta);
			if (!resultadosCargos.isEmpty())
				eliminarCargosPlanta();
			else
				eliminarPlantaTotal();
		} 
		else {
			blnEliminarPlantaConVinc = true;
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("$('#dialogConfirmElimTipoPlanta').modal('hide')");
			context.execute("$('#dialogElimEntidadCargoP').modal('show')");
		}
	}
	
	/**
	 * Metodo que permite validar si la planta UTL a eliminar posee vinculaciones
	 * desde el componente de HV
	 */
	public void validarEliminacionPlantaUtl(){
		PlantaPersonaUtlUanExt objPlanta = new PlantaPersonaUtlUanExt();
		objPlanta.setCodEntidadPlanta(plantaSeleccionada.getCodEntidadPlanta());
		 lstVinculacionUTL = ComunicacionServiciosEnt.getPersonasVinculadasUTL(objPlanta);
		 if(!lstVinculacionUTL.isEmpty()) {
			 RequestContext context = RequestContext.getCurrentInstance();
			 context.execute("$('#dialogEliminarPlantaUTL').modal('show')");
		 }else {
			 llenarTablaResponsablesUTL();
			 eliminarResponsablesTotalPlanta();
			 eliminarPlantaTotal(); 
		 }
	}

	/**
	 * Metodo que elimina los responsables asociados a la planta a eliminar
	 */
	public void eliminarResponsablesTotalPlanta() {
		 if(listaResponsablesUTL!= null) {
			 for (PlantaPersonaUtlUanExt ecp : this.listaResponsablesUTL) {
					ecp.setCodAudRol( (short)this.getUsuarioSesion().getCodRol());
					ecp.setCodAudUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
					ecp.setAudFechaActualizacion(new Date());
					ecp.setCodEntidadPlanta(this.estadoEdicion ? this.plantaSeleccionada.getCodEntidadPlanta() : this.entiPlantPer.getCodEntidadPlanta());
					ecp.setFlgActivo((short) 0);
					ecp.setCodAudAccion(new BigDecimal(TipoParametro.AUDITORIA_DELETE.getValue()));
					PlantaPersonaUtlUan  planta = ComunicacionServiciosEnt.guardarPlantaPersonaUtl(ecp);
				} 
		 }
	}
	
	/**
	 * Metodo que permite eliminar la planta
	 */
	public void eliminarPlantaTotal() {
		
		EntidadPlantaExt plantaGuardar = new EntidadPlantaExt();
		plantaGuardar = plantaSeleccionada;
		plantaGuardar.setAudFechaActualizacion(DateUtils.getFechaSistema());
		plantaGuardar.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		plantaGuardar.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
		plantaGuardar.setAudCodRol((int) getUsuarioSesion().getCodRol());
		plantaGuardar.setFlgActivo((short) 0);
		EntidadPlantaExt entiPlantPerResult = ComunicacionServiciosEnt.setEntidadPlanta(plantaGuardar);
		boolean resultado = !entiPlantPerResult.isError();
		if (resultado) {
			mostrarTablaPlantasPersonal();
			this.plantaSeleccionada = new EntidadPlantaExt();
			if (!blnEliminarPlantaConVinc)
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_PLANTA_ELIMINACION_PLANTA);
			else
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_PLANTA_ELIMINACION_PLANTA_VINC);
		} 
		else
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO + " " + entiPlantPerResult.getMensajeTecnico());
		blnEliminarPlantaConVinc = false;
	}

	/**
	 * Metodo para hacer desvinculación automática y eliminar la
	 * entidad_cargo_planta
	 * 
	 * @param entidadCargoPlanta
	 * @return boolean
	 */
	public void desvinAutomaticaEntCargoP() throws IOException {
		try {
			blnEliminarPlantaConVinc = true;
			/**
			 * Se realiza la desvinculacion de todas las vinculaciones que tenga asociada la
			 * planta
			 */
			if (listPersVinc != null && plantaSeleccionada != null) {
				VinculacionExt desvAutomatica = new VinculacionExt();
				desvAutomatica.setCodCausalDesvinculacion(new BigDecimal(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue()));
				desvAutomatica.setFlgActivo((short) 0);
				desvAutomatica.setFechaFinalizacion(DateUtils.getFechaSistema());
				desvAutomatica.setAudAccion((int) TipoAccionEnum.DELETE.getIdAccion());
				desvAutomatica.setAudFechaActualizacion(DateUtils.getFechaSistema());
				desvAutomatica.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
				desvAutomatica.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
				desvAutomatica.setCodPlantaPersonal(plantaSeleccionada.getCodEntidadPlanta());
				boolean desvinculacion = ComunicacionServiciosVin.setDesvinculacionAutomaticaPorPlanta(desvAutomatica);
				if (desvinculacion) {
					/*Se realiza desvinculacion automatica de las plantas asociadas a la planta de personal*/
					String asunto = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_ASUNTO_DESVINCULACION, getLocale());
					String mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_MENSAJE_DESVINCULACION_PLANTA, getLocale()).replace("%nombreEntidad%", entProceso.getNombreEntidad()).replace("%planta%", plantaSeleccionada.getNombreClasePlanta());
					List<PersonaExt> listPersonas = ComunicacionServiciosVin.getEmailPersonasDesvincular(Long.valueOf(entProceso.getCodEntidad().toString()));
					for (PersonaExt personaExt : listPersonas) {
						try {
							if (personaExt.getCorreoElectronico() != null && !"".equals(personaExt.getCorreoElectronico()))
								HojaDeVidaDelegate.enviarEmailPersonasDesvincular(asunto, mensaje, personaExt.getCorreoElectronico());
						} catch (SIGEP2SistemaException e) {
							logger.error("El metodo no envio correo - HojaDeVidaDelegate.enviarEmailPersonasDesvincular(asunto, mensaje, personaExt.getCorreoElectronico())", e);
						}
					}
					this.eliminarCargosPlanta();
				}
			}
		} catch (Exception ex) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, GestionarPlantaBean.TIT_ERROR_RE + " " + ex.getMessage() + ex.getCause() + ex.getStackTrace());
		}
	}

	/**
	 * Metodo que permite eliminar los cargos que tiene asociados una planta.
	 */
	public void eliminarCargosPlanta() {
		EntidadPlantaDetalleExt eliminacionCargo = new EntidadPlantaDetalleExt();
		eliminacionCargo.setFlgActivo((short) 0);
		eliminacionCargo.setAudAccion((int) TipoAccionEnum.DELETE.getIdAccion());
		eliminacionCargo.setAudFechaActualizacion(DateUtils.getFechaSistema());
		eliminacionCargo.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		eliminacionCargo.setAudCodRol((int) this.getRolAuditoria().getId());
		eliminacionCargo.setCodEntidadPlanta(new BigDecimal(plantaSeleccionada.getCodEntidadPlanta()));
		boolean eliminacionCargos = ComunicacionServiciosEnt.setEliminacionAutomaticaCargos(eliminacionCargo);
		if (eliminacionCargos)
			eliminarPlantaTotal();
	}

	/**
	 * Metodo que permite regresar al menu de Gestionar Entidad
	 */
	public void regresarMenu() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
		} catch (IOException ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, TIT_ERROR_RE, MSG_ERROR_RE + ex.getMessage());
		}
	}

	/**
	 * Metodo que permite regresar al menu principal de gestionar Entidad
	 */
	public void regresarPaso() {
		this.plantaSeleccionada = new EntidadPlantaExt();
		this.norma = new Norma();
		this.estadoSecCreacion = false;
		mostrarTablaPlantasPersonal();
	}




	/**
	 * Metodo que obtiene el nombre de las dependencias
	 * 
	 * @param codDependencia
	 * @return
	 */
	public String obtenerNombreDependencia(Long codDependencia) {
		if (codDependencia != null && !lstDependencias.isEmpty()) {
			for (SelectItem iter : lstDependencias) {
				Long idDep = (Long)iter.getValue();
				if (Long.compare(idDep, codDependencia) == 0)
					return !iter.getLabel().equals("") ? iter.getLabel().toUpperCase() : "";
			}
		}
		return "";
	}

	/**
	 * Metodo que permite guardar la información de los compontenes de una planta de personal
	 * guarda los datos de la planta, realiza las repectivas validaciones y guarda los cargos
	 * creados para la planta
	 * @param tipoGuardado
	 */
	public void guardarPlanta(String tipoGuardado) {
		boolean error = false;
		
		if(validarPlantaVencida()) {
			error = true;
			return;
		}
		
		if(tipoGuardado.equalsIgnoreCase("1"))
			this.mensajeGuardadoPlanta = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PLANTA_GUARDADO_PARCIAL_EXITO, this.getLocale());
		else if(this.plantaSeleccionada!=null && this.plantaSeleccionada.getCodEntidadPlanta()!=null)
				this.mensajeGuardadoPlanta = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PLANTA_MODIFICACION_EXITO, this.getLocale());
		else
			this.mensajeGuardadoPlanta = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PLANTA_CREACION_EXITO, this.getLocale());
		
		if(validarPlantaCargos()) {
			error = true;
			return;
		}
		
		try {	
			this.validarNormaPlanta();
			
			if(this.guardarDatosPlanta(tipoGuardado)) {
				if(plantaSeleccionada.getFlgPlantaUTL() ==0) {
					List<EntidadPlantaDetalleExt> lista = this.guardarCargos();
					if (!lista.isEmpty())
						this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, mensajeGuardadoPlanta + (error ? MessagesBundleConstants.MSG_PLANTA_ERROR_GUARDADO_POR_NOMENCLATURA : ""));
					else
						this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, mensajeGuardadoPlanta);	
				}else {
					
					guardarUTL();
						this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, mensajeGuardadoPlanta + (error ? MessagesBundleConstants.MSG_PLANTA_ERROR_GUARDADO_POR_NOMENCLATURA : ""));
						this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, mensajeGuardadoPlanta);
				}
				this.mostrarTablaPlantasPersonal();
				this.regresarPaso();
			}
			
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage());
		}
	}
	
	/**
	 * Metodo que valida si la planta creada ya esta vencida
	 */
	public boolean validarPlantaVencida() {
		boolean blnPlantaVencida = false;
		if(plantaSeleccionada != null && plantaSeleccionada.getFechaFin()!= null) {
			LocalDate fechaFin = plantaSeleccionada.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate fechaSistema =DateUtils.getFechaSistema().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (fechaFin.isBefore(fechaSistema)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_PLANTA_VENCIDA_PLANTA_FINAL	);
				blnPlantaVencida = true;
			}
		}
		return blnPlantaVencida;
	}

	/***
	 * Metodo que guarda toda la lista de UTLs/UAN con su respectivo responsable
	 */
	public void guardarUTL() {
		if(!listaResponsablesUTL.isEmpty()) {
			for (PlantaPersonaUtlUanExt ecp : this.listaResponsablesUTL) {
				ecp.setCodAudRol( (short)this.getUsuarioSesion().getCodRol());
				ecp.setCodAudUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
				ecp.setAudFechaActualizacion(new Date());
				ecp.setCodEntidadPlanta(this.estadoEdicion ? this.plantaSeleccionada.getCodEntidadPlanta() : this.entiPlantPer.getCodEntidadPlanta());
				ecp.setFlgActivo((short) 1);
				ecp.setCodAudAccion(new BigDecimal(TipoParametro.AUDITORIA_INSERT.getValue()));
				PlantaPersonaUtlUan  planta = ComunicacionServiciosEnt.guardarPlantaPersonaUtl(ecp);
			}
		}
	}
	
	/**
	 * Metodo que valida que no existan dos bolsas UTL con el mismo responsable
	 * @param responsableUTL
	 */
	public void validarDuplicidadResponsable(PlantaPersonaUtlUanExt responsableUTL){
		if(!this.listaResponsablesUTL.isEmpty()) {
			for(PlantaPersonaUtlUanExt utl : this.listaResponsablesUTL){
				try {
					if(utl.getCodPersona()!= null && utl != responsableUTL && utl.getCodPersona().equals(responsableUTL.getCodPersona())) {
						this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ULT_RESPONSABLE_EXISTENTE);
						responsableUTL.setCodPersona(null);
						return ;
					}
				} catch (NullPointerException e) {
					return ;
				}
			}	
		}
		return ;
	}
	
	/**
	 * Metodo que valida si la planta para los cargos si fue diligenciada de manera correcta
	 * @return plantaIncorrecta
	 */
	public boolean validarPlantaCargos() {
		
		boolean plantaIncorrecta = false;
		if(plantaSeleccionada != null && plantaSeleccionada.getFlgPlantaUTL()==0) {
			for (EntidadPlantaDetalleExt nd : this.resultadosCargos) {
				if(!buscarRelacionarCargoSeleccionado(nd)) {
					return true;
				}
			}
			if (!this.verificarDiligenciamientoCargos())
				plantaIncorrecta = true;
		}
		return plantaIncorrecta;
	}
	
	/**
	 * Método que verifica que todos los campos de una denominación creada
	 * se hayan diligenciado.
	 * 
	 */
	public boolean verificarDiligenciamientoCargos() {
		for (EntidadPlantaDetalleExt ecp : this.resultadosCargos) {
			if (ecp.getCodNivelCargo() == null || ecp.getCodDenominacion() == null || ecp.getCodGrado() == null || ecp.getCodNaturalezaEmpleo() == null || ecp.getCantidadCargo() == null || ecp.getCantidadCargo() <= 0 || ecp.getCodTipoPlanta() == null) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ERROR_CAMPOS_SIN_DILIGENCIAR);
				return false;
			}
			if (ecp.getCantidadCargoApropiacionPresupuestal() != null && (ecp.getCantidadCargo().longValue() < ecp.getCantidadCargoApropiacionPresupuestal().longValue())) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ERROR_CARGOS_APROPIACION_INCOHERENTE);
				return false;
			}
			if (ecp.getCodTipoPlanta().intValue() == TipoParametro.PLANTA_ESTRUCTURAL.getValue() && ecp.getCodDependenciaEntidad() == null) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ERROR_DEPE_ESTRU);
				return false;
			}
			if(ecp.getCantidadCargoApropiacionPresupuestal() == null)
				ecp.setCantidadCargoApropiacionPresupuestal(ecp.getCantidadCargo());
				
		}
		return true;
	}
	
	/**
	 * Método que valida que el valor ingresado en el campo cantidadCargoApropiacionPresupuestal
	 * No sea mayor al valor diligenciado en el campo catidad cargo
	 * @param epd
	 */
	public void validarAprobacionPresupuestal(EntidadPlantaDetalleExt epd) {
		if (epd.getCantidadCargo() != null && epd.getCantidadCargoApropiacionPresupuestal() != null && epd.getCantidadCargoApropiacionPresupuestal().longValue() > epd.getCantidadCargo().longValue()) {
			epd.setCantidadCargoApropiacionPresupuestal(epd.getCantidadCargo());
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ERROR_CARGOS_APROP_PRESUPUES);
		}
	}
	
	/**
	 * método que se ocupa únicamente de guardar la información respectiva a los cargos
	 * que se desean crear para la planta en proceso, guarda cada uno de los cargos ingresados.
	 * @return
	 */
	public List<EntidadPlantaDetalleExt> guardarCargos() {
		List<EntidadPlantaDetalleExt> listaPlantas = new ArrayList<>();
		try {
			if (!resultadosCargos.isEmpty()) {
				for (EntidadPlantaDetalleExt ecp : this.resultadosCargos) {
					ecp.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
					ecp.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
					ecp.setAudFechaActualizacion(new Date());
					ecp.setCodEntidadPlanta(new BigDecimal(this.estadoEdicion ? this.plantaSeleccionada.getCodEntidadPlanta() : this.entiPlantPer.getCodEntidadPlanta()));
					ecp.setFlgActivo((short) 1);
					if (ecp.getCodEntidadPlantaDetalle() == null || ecp.getCodEntidadPlantaDetalle() < -1) {
						ecp.setCantidadVacantes(ecp.getCantidadCargoApropiacionPresupuestal().intValue());
						ecp.setAudAccion(785);
					}else {
						ecp.setAudAccion(63);
						VinculacionExt vinFilter = new VinculacionExt();
						vinFilter.setCodEntidadPlantaDetalle(new BigDecimal(ecp.getCodEntidadPlantaDetalle()));
						vinFilter.setFlgActivo((short) 1);
						List<VinculacionExt> listaVin = ComunicacionServiciosVin.getVinculacionFilter(vinFilter);
						if((listaVin != null && !listaVin.isEmpty()) && ecp.getCantCargo() > ecp.getCantidadCargo() || ecp.getApropiacionPresu() > ecp.getCantidadCargoApropiacionPresupuestal()) {
							if(listaVin.size() > ecp.getCantidadCargoApropiacionPresupuestal()) {
								this.envioCorreos(ecp);
								Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(307));
								Integer dias = (parametrica.getValorParametro() != null && !parametrica.getValorParametro().isEmpty()) ? Integer.parseInt(parametrica.getValorParametro()) : 30;
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(new Date());
								calendar.add(Calendar.DATE, dias);
								Date fechaFinalizacion = calendar.getTime();
								VinculacionExt vi;
								for(long ite = 0; ite < (listaVin.size() - ecp.getCantidadCargoApropiacionPresupuestal()); ite++) {
									vi = new VinculacionExt();
									vi.setFechaFinalizacion(fechaFinalizacion);
									vi.setAudAccion(63);
									vi.setAudCodRol(new BigDecimal(this.getUsuarioSesion().getCodRol()));
									vi.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
									vi.setAudFechaActualizacion(new Date());
									ComunicacionServiciosVin.setVinculacion(vi);
								}
							}
						}
					}
					EntidadPlantaDetalleExt lista = ComunicacionServiciosVin.guardarEntidadPlantaDetalle(ecp);
					if (lista.isError()) {
						listaPlantas.add(lista);
						break;
					}
					if(ecp!=null && ecp.getCodEntidadPlantaDetalle() != null && ecp.getFlgRepresentanteLegal()!=null && ecp.getFlgRepresentanteLegal() == 1  ) {
						guardarRepresentanteLegal(ecp);
					}
				}
			}
		}
		catch(Exception ex) {
			logger.error("Error public List<EntidadPlantaDetalleExt> guardarCargos() GestionarPlantaBean: " + ex.getMessage() , ex);
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES , MessagesBundleConstants.MSG_PLANTA_ERROR);
		}
		return listaPlantas;
	}
	
	
	
	
	/**
	 * Metodo que almacena el representante legal en el componente de entidad.
	 * Para esto, es necesario que el cargo tenga el flg_representante_legal = 1 y adicional, que exista una vinculación para este cargo
	 */
	public void guardarRepresentanteLegal(EntidadPlantaDetalleExt cargo) {
		long usuarioSesion = this.getUsuarioSesion().getId();
        long rol = this.getRolAuditoria().getId();
        long tipoVin = TipoParametro.TIPO_VINCULACION_SERV_PUBLICO.getValue();
		if(validarSiExisteVinculacion(cargo)){
			String strNombrePersona = ultimaVinculacionCargo.getNombreUsuario();
			Entidad entidadN = new Entidad();
			entidadN.setCodEntidad(new BigDecimal(ultimaVinculacionCargo.getCodEntidad()));
	        entidadN.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
	        entidadN.setAudCodUsuario(BigDecimal.valueOf(getUsuarioSesion().getId()));
	        entidadN.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
	        entidadN.setAudFechaActualizacion(Calendar.getInstance().getTime());
	        entidadN.setCodRepresentanteLegal(strNombrePersona);
	        Entidad ent = ComunicacionServiciosEnt.setEntidadExt(entidadN,tipoVin,usuarioSesion,rol);
		}		
	}
	
	/**
	 * Metodo que se encarga de validar si existe una vinculación asociada al cargo de representante legal
	 * @param cargo
	 * @return
	 */
	public boolean validarSiExisteVinculacion(EntidadPlantaDetalleExt cargo) {
		boolean blnExisteVinculacion = false;
		VinculacionExt obVinculacion = new VinculacionExt();
		obVinculacion.setCodEntidadPlantaDetalle(new BigDecimal(cargo.getCodEntidadPlantaDetalle()));
		obVinculacion.setFlgActivo((short)1);
		ultimaVinculacionCargo = new VinculacionExt();
		ultimaVinculacionCargo = ComunicacionServiciosVin.getUltimaVinculacionByCargo(obVinculacion);
		if(ultimaVinculacionCargo != null && ultimaVinculacionCargo.getCodVinculacion() != null) {
			blnExisteVinculacion = true;
		}
		
		return blnExisteVinculacion;
	}

	/**
	 * Metodo que valida si la norma ya exite.
	 * 1. Si la norma ya existe, coge el codNorma y la almacena el objeto estructuraExt
	 * 2. Si no existe la norma, la crea.
	 */
	public void validarNormaPlanta() {
		if(norma.getNumeroNorma()!=null && norma.getFechaNorma()!=null && norma.getCodTipoNorma()!=null) {
			//Valida si la norma existenorma
			if(lstNormas != null && !lstNormas.isEmpty()) {
				plantaSeleccionada.setCodNorma(lstNormas.get(0).getCodNorma().longValue());
			}else {
			//Crea la norma cuando este no existe
				Norma norma = new Norma();
				norma.setCodTipoNorma(this.norma.getCodTipoNorma());
				norma.setFechaNorma(this.norma.getFechaNorma());
				norma.setNumeroNorma(this.norma.getNumeroNorma());
				norma.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				norma.setAudCodRol((int) getUsuarioSesion().getCodRol());
				norma.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				norma.setAudFechaActualizacion(new Date());
				norma = ComunicacionServiciosEnt.setNorma(norma);
				if(!norma.isError()) {
					plantaSeleccionada.setCodNorma(norma.getCodNorma());
				}else{
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, norma.getMensaje());
					return;
				}
			}
		}
	}
	
	/**
	 * método que crea la norma ingresada cuando esta no existe
	 * 
	 * @return
	 */
	public boolean crearNorma() {
		SequenciasSigep secuenciaNorma = ComunicacionServiciosSis.getSequenciasSigep("NORMA");
		if (secuenciaNorma != null) {
			this.norma.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
			this.norma.setAudFechaActualizacion(new Date());
			this.norma.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
			this.norma.setCodNorma(secuenciaNorma.getSecuencia());
			this.norma.setAudAccion(codAudAccionInsert);
			this.norma.setFechaNorma(new Date());
			Norma nuevaNorma = ComunicacionServiciosEnt.setNorma(norma);
			if (nuevaNorma != null && nuevaNorma.getCodNorma() != null)
				this.plantaSeleccionada.setCodNorma(nuevaNorma.getCodNorma());
			else {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ERROR_NORMA_JUSTI_GUARDADO);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Método que guarda unicamente la información de la planta como lo son los datos norma, 
	 * fecha de validez de la planta, nombre de la planta la clase y clasificación y la nomenclatura asociada.
	 * 
	 * @param tipoGuardado
	 * @return
	 */
	public boolean guardarDatosPlanta(String tipoGuardado) {
		if(plantaSeleccionada.getFlgPlantaUTL() ==1 && validarUTL()) {
			return false;
		}
		if (this.plantaSeleccionada.getJustificacion() != null && this.plantaSeleccionada.getJustificacion().isEmpty())
			this.plantaSeleccionada.setJustificacion(null);
		this.plantaSeleccionada.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
		this.plantaSeleccionada.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
		this.plantaSeleccionada.setAudFechaActualizacion(new Date());
		this.plantaSeleccionada.setFlgActivo((short) 1);
		this.plantaSeleccionada.setCodEntidad(codEntidadProceso);
		this.plantaSeleccionada.setFlgGuardadoParcial(Short.parseShort(tipoGuardado));
		if (this.plantaSeleccionada.getCodEntidadPlanta() == null || this.plantaSeleccionada.getCodEntidadPlanta() < -1)
			this.plantaSeleccionada.setAudAccion(785);
		else
			this.plantaSeleccionada.setAudAccion(63);
		this.entiPlantPer = ComunicacionServiciosEnt.setEntidadPlanta(this.plantaSeleccionada);
		if (entiPlantPer.isError()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + entiPlantPer.getMensajeTecnico());
			return false;
		}
		return true;
	}

	/**
	 * Metodo que agrega un nuevo cargo a lista creada para la planta.
	 */
	public void agregarCargoTabla() {
		if (this.plantaSeleccionada.getCodNomenclatura() != null) {
			EntidadPlantaDetalleExt fila = new EntidadPlantaDetalleExt();
			if (!this.resultadosCargos.isEmpty()) {
				if (!this.verificarDiligenciamientoCargos())
					return;
				if (!this.buscarRelacionarCargoSeleccionado(this.resultadosCargos.get(0)))
					return;
			}
			this.resultadosCargos.add(0, fila);
		} 
		else
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_AVISO_NO_SELECCION_NOMENCLATURA);
	}

	/**
	 * Metodo que permite eliminar un cargo de la plannta seleccionada.
	 * 
	 * @param cargo
	 */
	public void eliminarCargoPlanta(EntidadPlantaDetalleExt cargo) {
		this.cargoSeleccionado = cargo;
		this.cargarPersonDesv();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("$('#dialogConfirmElimCargoPlanta').modal('show')");
	}

	/**
	 * Carga las personas a las cuales le va a realizar la eliminacion de un cargo
	 */
	public void cargarPersonDesv() {
		try {
			if(cargoSeleccionado.getCodEntidadPlantaDetalle() != null) {
				VinculacionExt vinculacion = new VinculacionExt();
				vinculacion.setFlgActivo((short) 1);
				vinculacion.setCodEntidadPlantaDetalle(new BigDecimal(cargoSeleccionado.getCodEntidadPlantaDetalle()));
				this.listPersVincCargo = ComunicacionServiciosVin.getVinculacionFilter(vinculacion);
			}
		} catch (Exception ex) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, GestionarPlantaBean.TIT_ERROR_RE + " " + ex.getMessage() + ex.getCause() + ex.getStackTrace());
			listPersVincCargo = new ArrayList<>();
		}
	}

	/**
	 * Metodo que verifica si el cargo a eliminar tiene vinculaciones.
	 */
	public void verificaCargoEliminar() {
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			if (this.cargoSeleccionado.getCodEntidadPlantaDetalle() != null) {
				VinculacionExt vinculacion = new VinculacionExt();
				vinculacion.setFlgActivo((short) 1);
				vinculacion.setCodEntidadPlantaDetalle(new BigDecimal(cargoSeleccionado.getCodEntidadPlantaDetalle()));
				List<VinculacionExt> vinculacionesCargo = ComunicacionServiciosVin.getVinculacionFilter(vinculacion);
				if (vinculacionesCargo == null || vinculacionesCargo.isEmpty()) {
					this.cargoSeleccionado.setFlgActivo((short) 0);
					this.cargoSeleccionado.setAudFechaActualizacion(new Date());
					boolean resultado = ComunicacionServiciosVin.setEntidadPlantaDetalle(this.cargoSeleccionado);
					if (resultado) {
						this.resultadosCargos.remove(this.cargoSeleccionado);
						VinculacionExt vinFilter = new VinculacionExt();
						vinFilter.setCodEntidadPlantaDetalle(new BigDecimal(this.cargoSeleccionado.getCodEntidadPlantaDetalle()));
						vinFilter.setFlgActivo((short) 1);
						List<VinculacionExt> listaVin = ComunicacionServiciosVin.getVinculacionFilter(vinFilter);
						if(listaVin != null && !listaVin.isEmpty()) {
							this.envioCorreos(this.cargoSeleccionado);
							Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(307));
							Integer dias = parametrica.getValorParametro() != null ? Integer.parseInt(parametrica.getValorParametro()) : 30;
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(new Date());
							calendar.add(Calendar.DATE, dias);
							Date fechaFinalizacion = calendar.getTime();
							for(VinculacionExt vi : listaVin) {
								vi.setFechaFinalizacion(fechaFinalizacion);
								vi.setAudAccion(63);
								vi.setAudCodRol(new BigDecimal(this.getUsuarioSesion().getCodRol()));
								vi.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
								vi.setAudFechaActualizacion(new Date());
								ComunicacionServiciosVin.setVinculacion(vi);
							}
						}
						this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO, MessagesBundleConstants.DLG_BR_CONFIRMA_ELIMINA);
					} 
					else
						this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_PAGE_EXCEPTION_ERROR, MessagesBundleConstants.BR_MSG_ELIMINACION_FALLIDA);
				} 
				else {
					context.execute("$('#dialogConfirmElimCargoPlanta').modal('hide')");
					context.execute("$('#dialogElimCargoPlanta').modal('show')");
				}
			} 
			else {
				this.resultadosCargos.remove(cargoSeleccionado);
				context.execute("$('#dialogConfirmElimCargoPlanta').modal('hide')");
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_PROCESO_EXITOSO, MessagesBundleConstants.DLG_BR_CONFIRMA_ELIMINA);
			}
		}
		catch(Exception ex) {
			logger.error("Error public void verificaCargoEliminar() GestionarPlantaBean.java", ex);
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error eliminando el cargo: " + ex.getMessage());
		}
	}

	/**
	 * Metodo que realiza la desvinculacion automatica de la planta.
	 */
	public void desvinAutomaticaCargoPlanta() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			BigDecimal lECodRol = new BigDecimal(getUsuarioSesion().getCodRol());
			BigDecimal IEUsuarioId = new BigDecimal(getUsuarioSesion().getId());
			if (cargoSeleccionado != null) {
				BigDecimal codCausalVinculacion = new BigDecimal("2015");
				VinculacionExt vinculacion = new VinculacionExt();
				vinculacion.setFlgActivo((short) 1);
				vinculacion.setCodEntidadPlantaDetalle(new BigDecimal(cargoSeleccionado.getCodEntidadPlantaDetalle()));
				List<VinculacionExt> vinculacionesCargo = ComunicacionServiciosVin.getVinculacionFilter(vinculacion);
				if (vinculacionesCargo != null) {
					for (VinculacionExt listaVinc : vinculacionesCargo) {
						listaVinc.setCodCausalDesvinculacion(codCausalVinculacion);
						listaVinc.setFlgActivo((short) 0);
					
						listaVinc.setFechaFinalizacion(DateUtils.getFechaSistema());
						listaVinc.setStrFechaDesvinculacion(format.format(DateUtils.getFechaSistema()));
						
						listaVinc.setFlgMedicoDocente((short) 0);
						listaVinc.setAudAccion((int) TipoAccionEnum.UPDATE.getIdAccion());
						listaVinc.setAudCodRol(lECodRol);
						listaVinc.setAudCodUsuario(IEUsuarioId);
						listaVinc.setAudFechaActualizacion(DateUtils.getFechaSistema());
						/* boolean desvinculacion = */ ComunicacionServiciosVin.setVinculacion(listaVinc);
						String asunto = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_ASUNTO_DESVINCULACION, getLocale());
						String mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_MENSAJE_DESVINCULACION_CARGO, getLocale()).replace("%nombreEntidad%", entProceso.getNombreEntidad()).replace("%cargo%", cargoSeleccionado.getNombreCargo());
						List<PersonaExt> listPersonas = ComunicacionServiciosVin.getEmailPersonasDesvincular(Long.valueOf(entProceso.getCodEntidad().toString()));
						for (PersonaExt personaExt : listPersonas) {
							try {
								if (personaExt.getCorreoElectronico() != null && !"".equals(personaExt.getCorreoElectronico()))
									HojaDeVidaDelegate.enviarEmailPersonasDesvincular(asunto, mensaje, personaExt.getCorreoElectronico());
							} catch (SIGEP2SistemaException e) {
								logger.error("El metodo no envio correo - HojaDeVidaDelegate.enviarEmailPersonasDesvincular(asunto, mensaje, personaExt.getCorreoElectronico())", e);
							}
						}
					}
					cargoSeleccionado.setFlgActivo((short) 0);
					cargoSeleccionado.setAudFechaActualizacion(DateUtils.getFechaSistema());
					cargoSeleccionado.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
					cargoSeleccionado.setAudCodRol(lECodRol.intValue());
					cargoSeleccionado.setAudCodUsuario(IEUsuarioId);
					boolean resultado = ComunicacionServiciosVin.setEntidadPlantaDetalle(cargoSeleccionado);
					if (resultado) {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("$('#dialogElimCargoPlanta').modal('hide')");
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ELIMINACION_EXITO_DET + vinculacionesCargo.size() + " persona(s)");
					} 
					else
						mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ELIMINACION_CARGO_FALLIDA);
				}
			}
		} catch (Exception ex) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, GestionarPlantaBean.TIT_ERROR_RE + " " + ex.getMessage() + ex.getCause() + ex.getStackTrace());
		}
	}

	/**
	 * Metodo que obtiene las parametricas
	 * 
	 * @param idParam
	 * @return
	 */
	public String obtenerParametrica(Long idParam) {
		try {
			if (idParam == null)
				return MSG_NO_DATO;
			Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(idParam));
			if (param.getNombreParametro() == null || param.getNombreParametro() == "")
				return MSG_NO_DATO;
			return param.getValorParametro().toUpperCase();
		} catch (Exception ex) {
			return MSG_NO_DATO;
		}
	}
	
	/**
	 * Método que obtiene los valores de la parametricas usadas en la aplicación
	 * @param idParam
	 * @return
	 */
	public String obtenerValorParametrica(Long idParam) {
		try {
			if (idParam == null)
				return MSG_NO_DATO;
			Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(idParam));
			if (param.getValorParametro() == null || param.getValorParametro() == "")
				return MSG_NO_DATO;
			return param.getValorParametro().replaceAll("_", " ").toUpperCase();
		} catch (Exception ex) {
			return MSG_NO_DATO;
		}
	}

	/**
	 * Metodo utilizada para buscar plantas,
	 */
	public void buscarPlantas() {
		this.estTablaPla = true;
	}

	/**
	 * Método usado para convertir y formatear el valor de los campos
	 * flgRepresentanteLegal y flgGerenciaPublica para visualizarlos en su respectivo control en el 
	 * formulario
	 * 
	 * @param enti
	 */
	public void setValorObjeto(EntidadPlantaDetalleExt enti) {
		try {
			enti.setFlgEsGerenciaPublica(enti.isFlgGerenciaPublica() ? (short) 1 : (short) 0);
			enti.setFlgRepresentanteLegal(enti.isFlgRepresentanteEmpleo() ? (short) 1 : (short) 0);
			enti.setFlgCargoExpuestoPublicamente(enti.isFlgIsExpuestoPoliticamente() ? (short) 1 : (short) 0);
			if (!lstDenominacionesEntidad.isEmpty() && enti.getFlgRepresentanteLegal() == 1 && enti.getCodDenominacion() == null)
				enti.setCodDenominacion(Long.valueOf(lstDenominacionesEntidad.get(0).getValue().toString()));
		} catch (Exception ex){
			logger.error("Error setValorObjeto() GestionarPlantaBean()", ex);
		}
	}

	/**
	 * metodo utilizado para obtener los numeros de norma de una entidad.
	 * 
	 * @param tipoNorma
	 * @param fechaNorma
	 * @return
	 */
	public List<SelectItem> getNumerosNorma(Long tipoNorma, Date fechaNorma) {
		List<SelectItem> list = new ArrayList<>();
		Norma norma = new Norma();
		if (tipoNorma != null)
			norma.setCodTipoNorma(tipoNorma.intValue());
		norma.setFechaNorma(fechaNorma);
		List<Norma> listNorma = ComunicacionServiciosEnt.getFiltroNorma(norma);
		try {
			if (!listNorma.isEmpty()) {
				for (Norma aux : listNorma)
					list.add(new SelectItem(aux.getNumeroNorma(), aux.getNumeroNorma()));
			}
		} catch (NullPointerException ex) {
			GestionarPlantaBean.logger.error("public List<SelectItem> getNumerosNorma(Long tipoNorma, Date fechaNorma) GestionPlantaBean.java error: " + ex.getMessage(), ex);
			return new ArrayList<>();
		}
		return list;
	}
	/**
	 * 
	 * Método usado para obtener el nombre de una denominación teniendo el id de esa denominación
	 * 
	 * @param nombreDenominacion
	 * @return
	 */
	public String obtenerDenominacion(Long nombreDenominacion) {
		try {
			if (nombreDenominacion != null) {
				Denominacion denom = new Denominacion();
				denom.setCodDenominacion(new BigDecimal(nombreDenominacion));
				List<Denominacion> lista = ComunicacionServiciosEnt.getDenomincacionesFiltro(denom);
				if (lista != null && !lista.isEmpty())
					return !lista.get(0).getNombreDenominacion().equals("")?  lista.get(0).getNombreDenominacion().toUpperCase(): "";
			}
			return MSG_NO_DATO;
		} catch (Exception ex) {
			logger.error("Error Public String obtenerDenominacion() GestionarPlantaBean " + ex.getMessage(), ex);
			return "Error recuperando denominacion";
		}
	}

	/**
	 * metodo utilizado para obtener los numeros de norma de una entidad.
	 * 
	 * @param tipoNorma
	 * @param fechaNorma
	 * @return
	 */
	public List<SelectItem> getNormas() {
		List<SelectItem> list = new ArrayList<>();
		List<Norma> listNorma = ComunicacionServiciosEnt.getTodasNormas();
		try {
			if (!listNorma.isEmpty()) {
				for (Norma aux : listNorma) {
					list.add(new SelectItem(aux.getCodNorma(), aux.getNumeroNorma()));
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}
		return list;
	}

	public String obtenerDatosNorma(Long idNorma, byte datoRetornar) {
		return null;
	}


	/**
	 * Método que obtiene los registros nomenclaturaDenominacion, esto es la nomenclatura
	 * completa con su detalle teniendo el código de la denominación por un código de nomenclatura
	 */
	public void cargarDenominacionDetalle() {
		getNivelNomenclaturaEntidad();
		NomenclaturaDenominacion filtroNomDen = new NomenclaturaDenominacion();
		filtroNomDen.setCodNomenclatura(new BigDecimal(this.plantaSeleccionada.getCodNomenclatura()));
		filtroNomDen.setFlgActivo((short) 1);
		if(validarNomenclaturaGeneral())
			filtroNomDen.setFlgActivoParticular((short)1);
		this.listaNomenclaturaDenominacion = ComunicacionServiciosEnt.obtenerNomenclaturaDenominacion(filtroNomDen);
		if(this.listaNomenclaturaDenominacion.isEmpty())
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES , MessagesBundleConstants.MSG_PLANTA_NOMENCLATURA_SIN_DENOMINACION);
		if(this.plantaSeleccionada == null)
			this.resultadosCargos = new ArrayList<>();
	}
	
	/**
	 * Metodo que valida si la nomenclatura seleccionada viene de una general
	 * @return boolean
	 */
	public boolean validarNomenclaturaGeneral() {
		boolean nomenclaturaGeneral = false;
		Nomenclatura nomenclatura = ComunicacionServiciosEnt.getNomenclaturaPorId(this.plantaSeleccionada.getCodNomenclatura());
			if(nomenclatura.getCodNomenclaturaAsociada()!=null)
				nomenclaturaGeneral = true;
		return nomenclaturaGeneral;
	}
	
	/**
	 * Método que obtiene la lista de grados de una nivel y denominación especificos usados en una denominación
	 * 
	 * @param codNivel
	 * @param codDenominacion
	 * @return
	 */
	public List<SelectItem> configurarListaGrados(Long codNivel, Long codDenominacion, Long codCodigo) {
		try {
			this.lstGrados = new ArrayList<>();
			if (codNivel != null && codDenominacion != null && codCodigo != null && this.plantaSeleccionada!=null && this.plantaSeleccionada.getCodNomenclatura()!=null) {
				NomenclaturaDenominacion buscador = new NomenclaturaDenominacion();
				buscador.setFlgActivo((short) 1);
				buscador.setCodNomenclatura(BigDecimal.valueOf(this.plantaSeleccionada.getCodNomenclatura()));
				buscador.setCodNivelJerarquico(codNivel);
				buscador.setCodDenominacion(codDenominacion);
				buscador.setCodCodigo(codCodigo.toString());
				List<NomenclaturaDenominacionExt> lstGrados = ComunicacionServiciosEnt.getGradoPorDenominacionNivelCodigo(buscador);
				for(NomenclaturaDenominacionExt nd:lstGrados){
					this.lstGrados.add(new SelectItem(nd.getCodGrado(), nd.getGradoCargo()));
				}
				return this.lstGrados;
			} else{
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Método que obtiene la lista de codigos de un cargo dependiendo de la denominacion y el nivel seleccionado.
	 * 
	 * @param codNivel
	 * @param codDenominacion
	 * @return
	 */
	public List<SelectItem> configurarListaCodigos(Long codNivel, Long codDenominacion) {
		try {
			this.lstCodigos = new ArrayList<>();
			if (codNivel != null && codDenominacion != null && this.plantaSeleccionada!=null && this.plantaSeleccionada.getCodNomenclatura()!=null) {
				
				NomenclaturaDenominacion buscador = new NomenclaturaDenominacion();
				buscador.setFlgActivo((short) 1);
				buscador.setCodNomenclatura(BigDecimal.valueOf(this.plantaSeleccionada.getCodNomenclatura()));
				buscador.setCodNivelJerarquico(codNivel);
				buscador.setCodDenominacion(codDenominacion);
				List<NomenclaturaDenominacionExt> lstCodigos = ComunicacionServiciosEnt.getCodigoPorDenominacionNivel(buscador);
				for(NomenclaturaDenominacionExt nd : lstCodigos){
					this.lstCodigos.add(new SelectItem(nd.getCodCodigo(), nd.getCodigoCargo()));
				}
				if(!this.lstCodigos.isEmpty() && this.lstCodigos.size() ==1) {
					configurarListaGrados(codNivel, codDenominacion, Long.parseLong(lstCodigos.get(0).getCodCodigo()));
				}
				
				return this.lstCodigos;
			} else{
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage() + " " + ex.getStackTrace() + " " + ex.getCause(), ex);
			return new ArrayList<>();
		}
	}
	
	/**
	 * Método usado para verificar si un elemento existe en una lista.
	 * 
	 * @param items
	 * @param elementVerifi
	 * @return
	 */
	public boolean verificarExistenciaElemento(List<SelectItem> items, Long elementVerifi){
		if(elementVerifi != null) {
			for (SelectItem SelIte : items) {
				if ((long) SelIte.getValue() == elementVerifi)
					return true;
			}
			return false;
		} 
		else
			return true;
	}
	
	/**
	 * Método que verifica si una cargo configurado en la planta es valido y existe en la nomenclatura seleccionada
	 * @param detalleSeleccionado
	 * @return
	 */
	public boolean buscarRelacionarCargoSeleccionado(EntidadPlantaDetalleExt detalleSeleccionado) {
		NomenclaturaDenominacion nomenclatura = new NomenclaturaDenominacion();
		nomenclatura.setCodNomenclatura(new BigDecimal(this.plantaSeleccionada.getCodNomenclatura()));
		nomenclatura.setCodGrado(detalleSeleccionado.getCodGrado());
		nomenclatura.setCodCodigo(detalleSeleccionado.getCodCodigo().toString());
		nomenclatura.setCodNivelJerarquico(detalleSeleccionado.getCodNivel());
		nomenclatura.setCodDenominacion(detalleSeleccionado.getCodDenominacion());
		nomenclatura.setFlgActivo((short)1);
		List<NomenclaturaDenominacion> nomeclats = ComunicacionServiciosEnt.getDenomincacionesNomenclaturaFiltro(nomenclatura);
		if (!nomeclats.isEmpty()) {
			detalleSeleccionado.setCodNomenclaturaDenominacion(nomeclats.get(0).getCodNomenclaturaDenominacion());
			return true;
		} else {
			String strDenominacion = detalleSeleccionado.getNombreDenominacion() + " " + detalleSeleccionado.getNombreCodigo()+ " " + detalleSeleccionado.getNombreGrado();
			String msgNoExisteDenNom = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_NO_EXISTE_DENOMINACION_NOMENCLATURA, getLocale())
					.replace("%denominacion%", strDenominacion);
			detalleSeleccionado.setCodGrado(null);
			detalleSeleccionado.setCodNivel(null);
			detalleSeleccionado.setCodDenominacion(null);
			detalleSeleccionado.setCodNaturalezaEmpleo(null);
			detalleSeleccionado.setCodTipoPlanta(null);
			detalleSeleccionado.setCodNomenclaturaDenominacion(null);
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, msgNoExisteDenNom );
			return false;
		}
	}


	public void actualizarCargoPresupuesto(EntidadPlantaDetalleExt cargo) {
		int posicion = resultadosCargos.lastIndexOf(cargo);
		cargo.setCantidadCargoApropiacionPresupuestal(cargo.getCantidadCargo());
		this.resultadosCargos.set(posicion, cargo);
	}
	

	public void configurarTemporalidad() {
		this.estadoTemporalidad = !(this.plantaSeleccionada.getCodClasePlanta() != null && this.plantaSeleccionada.getCodClasePlanta().longValue() == TipoParametro.PLANTA_TEMPORAL.getValue());
	}

	// Se debe deshabilitar el campo de crear registro en la tabla.
	public void construirTablaDenominaciones() {
		try {
			for (NomenclaturaDenominacion denom : this.listaNomenclaturaDenominacion) {
				EntidadPlantaDetalleExt enPla = new EntidadPlantaDetalleExt();
				enPla.setCodNivel(denom.getCodNivelJerarquico());
				enPla.setCodGrado(denom.getCodGrado());
				enPla.setCodDenominacion(denom.getCodDenominacion());
				enPla.setFlgRepresentanteLegal((short) 0);
				enPla.setFlgEsGerenciaPublica((short) 0);
				enPla.setFlgCargoExpuestoPublicamente((short) 0);
				enPla.setFlgActivo((short) 0);
				enPla.setCodEntidadPlanta(null);
				this.resultadosCargos.add(enPla);
			}
		} catch (Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ERROR_CARGA_TABLA_CARGOS);
			this.resultadosCargos = null;
		}
	}
	
	public void desactivarJustificacion() {
		this.estadoJustifi = !(this.norma.getNumeroNorma().isEmpty() || this.norma.getNumeroNorma() == null || this.norma.getNumeroNorma().equalsIgnoreCase(""));

	}
	
	public void configurarValorCargoInicial() {
		for(EntidadPlantaDetalleExt en : this.resultadosCargos) {
			en.setCantCargo(en.getCantidadCargo().intValue());
			en.setApropiacionPresu(en.getCantidadCargoApropiacionPresupuestal().intValue());
		}
	}
	
	public void envioCorreos(EntidadPlantaDetalleExt epd){
		try {
			PersonaExt personaRoles = new PersonaExt();
			Integer[] roles = new Integer[2];
			roles[0] = 16;
		    roles[1] = 18;
		    personaRoles.setCodEntidad(new BigDecimal(this.codEntidadProceso));
		    personaRoles.setCodRolList(roles);
			List<PersonaExt> listPersonas = ComunicacionServiciosEnt.getPersonasPorRoles(personaRoles);
			if(!listPersonas.isEmpty()) {
				String asunto = "Se ha modificado una planta de personal de la entidad";
				String mensaje = "Se ha modificado la planta de personal de la entidad la planta tiene vinculaciones por realice las respectivas desvinculaciones";
				for (PersonaExt personaExt : listPersonas) {
					personaExt.getCorreoElectronico();
					ConnectionHttp.sendEmail(personaExt.getCorreoElectronico(), mensaje, asunto);
				}
			}
		}
		catch(Exception ex) {
			GestionarPlantaBean.logger.error(TIT_ERROR_RE + " GestionarPlantaBean.java public void envioCorreos(EntidadPlantaDetalleExt epd)" + ex.getMessage(), ex);
		}
	}
	
	public void configurarFlags() {
		for(EntidadPlantaDetalleExt en : this.resultadosCargos) {
			en.setFlgGerenciaPublica(en.getFlgEsGerenciaPublica() != null && (en.getFlgEsGerenciaPublica() == 1));
			en.setFlgRepresentanteEmpleo(en.getFlgRepresentanteLegal() != null && (en.getFlgRepresentanteLegal() == 1));
			en.setFlgIsExpuestoPoliticamente(en.getFlgCargoExpuestoPublicamente() != null && (en.getFlgCargoExpuestoPublicamente() == 1));
		}
	}
	
	public Long obtenerCodigosDenominacion(Long codDen){
		List<Parametrica> lisParam = ComunicacionServiciosSis.getParametricaPorIdPadre(2253L);
		if(codDen == null)
			return 0L;
		for(Parametrica p : lisParam) {
			try {
				if(p != null && p.getValorParametro() != null && Long.parseLong(p.getValorParametro()) == codDen)
					return p.getCodTablaParametrica().longValue();
			}
			catch(NumberFormatException ex) {
				GestionarPlantaBean.logger.error("public Long obtenerCodigosDenominacion(Long codDen) Error el valor del parametro procesado no es un número, codigo del parametro: " + codDen + " " + ex.getMessage(), ex);
			}
		}
		return 0L;
	}
	
	public void restablecerSelecciones(EntidadPlantaDetalleExt cargo, int caso){
		switch (caso) { 
			//Caso 1 en caso tal de que se haya modificado en nivel jerarquico
			case 1:
				cargo.setCodDenominacion(null);
				cargo.setCodCodigo(null);
				cargo.setCodGrado(null);
				break;
			// Caso 2 en caso de que se haya modificado la denominacion
			case 2:
				cargo.setCodCodigo(null);
				cargo.setCodGrado(null);
				break;
			// Caso 3 en caso de que se haya modificado el codigo
			case 3:
				cargo.setCodGrado(null);
				break;
		}
	}

	 /**
	  * Metodo que valida si la fecha fin es obligatoria, esto para solo cuando la clase es temporal
	  */
	 public void validarFechaFin() {
		 blnFechaFinObligatoria = plantaSeleccionada.getCodClasePlanta() != null 
				 				&& this.plantaSeleccionada.getCodClasePlanta().longValue() == TipoParametro.PLANTA_TEMPORAL.getValue() ? true : false;
	 }
	 
	/**
	 * Metodo que reinicia los campos de la norma
	 */
	public void reiniciarCamposNorma() {
		norma 				= new Norma();
		normaSeleccionada 	= new Norma();
		lstNormas	 		= new ArrayList<>();
	}
	
	/**
	 * Metodo que busca si existen normas segun la fecha de la norma
	 * y el tipo de la norma seleccionada y las retorna
	 * @param numeroNorma
	 * @return
	 */
	 public List<Norma> completeMethodNorma(String  numeroNorma) {
		 Norma norm = new Norma();
		 norm.setNumeroNorma(numeroNorma);
		 norm.setFechaNorma(norma.getFechaNorma());
		 if(norma.getCodTipoNorma()!=null)
			 norm.setCodTipoNorma(norma.getCodTipoNorma().intValue());
		 lstNormas =  ComunicacionServiciosEnt.getFiltroNormaLike(norm);
	     if(lstNormas == null && lstNormas.isEmpty()) {
	    	 return new ArrayList<>();
	     }
	     return lstNormas;
	 }
	 
	 /**
	  * Metodo que obtiene el numero de la norma
	  * @param estructuraExt
	  */
	 public void vclNorma(ValueChangeEvent  norma) {
		Norma estr = (Norma) norma.getNewValue();
        if (estr!= null && estr.getNumeroNorma() != null) {
        	this.norma.setNumeroNorma(estr.getNumeroNorma());
        }
	 }
	 
	/**
	 * Metodo que busca las normas de las nomenclaturas.
	 */
	public void buscarNormaPlanta() {
		if(plantaSeleccionada != null && plantaSeleccionada.getCodNorma() != null) {
			Norma norma= ComunicacionServiciosEnt.getNormaPorId(plantaSeleccionada.getCodNorma().longValue());
			if(norma != null) {
				this.norma = norma;
				normaSeleccionada = norma;
			}
		}
	}
	
	/**
	 * Metodo que valida si la clasificacion seleccionada es una planta UTL
	 */
	public void validarPlantaUTL() {
		if( plantaSeleccionada!= null && plantaSeleccionada.getCodClasificacionPlanta() != null 
				&& plantaSeleccionada.getCodClasificacionPlanta() == TipoParametro.CLASIFICACION_PLANTA_UTL.getValue()) {
			if(validarExistenciaPlantaUTL()) {
				plantaSeleccionada.setCodClasificacionPlanta(null);
				return;
			}
			Parametrica parSalariosMensuales = ComunicacionServiciosVin.getParametricaporId(new BigDecimal(TipoParametro.SALARIOS_MENSUALES_MINIMOS.getValue()));
			varSalariosMinimos = parSalariosMensuales.getValorParametro() != null ? Long.parseLong(parSalariosMensuales.getValorParametro()) : 0;
			blnPlantaUtl = true;
			plantaSeleccionada.setFlgPlantaUTL((short)1);
			llenarTablaResponsablesUTL();
		}else {
			blnPlantaUtl = false;
			plantaSeleccionada.setFlgPlantaUTL((short)0);
		}
	}
	
	/**
	 * Mrtodo que consulta si ya existe una planta UTL para la entidad seleccionada y si es asi, no deja crear mas plantas
	 * @return blnExistePlantaUTl
	 */
	public boolean validarExistenciaPlantaUTL() {
		if(plantaSeleccionada.getCodEntidadPlanta() == null) {
			List<EntidadPlantaExt> resultadoPlantaUTL  = new ArrayList<>();
			EntidadPlantaExt 		objEntidadPlantaUTL = new EntidadPlantaExt(); 
			objEntidadPlantaUTL.setCodEntidad(codEntidadProceso);
			objEntidadPlantaUTL.setCodClasificacionPlanta(Long.valueOf(TipoParametro.CLASIFICACION_PLANTA_UTL.getValue()));
			objEntidadPlantaUTL.setFlgActivo((short) 1);
			objEntidadPlantaUTL.setLimitInit(0);
			objEntidadPlantaUTL.setLimitEnd(100);
			resultadoPlantaUTL = ComunicacionServiciosVin.getEntidadPlantaFilter(objEntidadPlantaUTL);
			if(!resultadoPlantaUTL.isEmpty()) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_VALIDAR_EXISTENCIA_PLANTA_UTL);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Metodo que obtiene los responsables de la tabla utl
	 */
	public void llenarTablaResponsablesUTL() {
		if(plantaSeleccionada!=null && plantaSeleccionada.getCodEntidadPlanta() != null) {
			PlantaPersonaUtlUanExt obPlanta = new PlantaPersonaUtlUanExt();
			obPlanta.setFlgActivo((short) 1);
			obPlanta.setCodEntidadPlanta(plantaSeleccionada.getCodEntidadPlanta());
			listaResponsablesUTL = ComunicacionServiciosEnt.obtenerPlantaPersonaUTLFiltro(obPlanta);
		}
	}
	
	
	/**
	 * Metodo que calcula el monto maximo disponible para la UTL
	 * se obtiene de multplicar SMMLV* numero de SMMLV
	 */
	public void calcularMontoMaximoUTL() { 
		varMontoMaximo = new BigDecimal(0);
		if(plantaSeleccionada!= null && plantaSeleccionada.getCantidadSalariosUTL()!=null
			&& varSalariosMinimos != null) {
			varMontoMaximo = plantaSeleccionada.getCantidadSalariosUTL().multiply(new BigDecimal(varSalariosMinimos));
		}
	}
	
	
	/**
	 * Metodo que calcula el total de cargos disponibles
	 */
	public int calcularCargosDisponibles(int cargosOcupados) {
		int totalCargosDisponibles = 0;
			if(plantaSeleccionada != null && plantaSeleccionada.getCantidadColaboradoresUTL()!= null) {
				totalCargosDisponibles = plantaSeleccionada.getCantidadColaboradoresUTL().intValue() - cargosOcupados;
			}
		
		return totalCargosDisponibles;
	}
	
	/**
	 * Metodo que calcula el monto que cada UTL tiene disponible
	 * @return totalMontoDisponible
	 */
	public BigDecimal calcularMontoDisponible(BigDecimal montoGastado) {
		BigDecimal totalMontoDisponible = new BigDecimal(0);
		if(varMontoMaximo != null) {
			montoGastado = montoGastado!= null ? montoGastado: new BigDecimal(0);
			totalMontoDisponible = varMontoMaximo.subtract(montoGastado);
		}
		return totalMontoDisponible;
	}
	
	/**
	 * Metodo que crea dinamicamente las bolsas diligenciadas por el usuario
	 */
	public boolean validarUTL() {
		if(plantaSeleccionada!=null && plantaSeleccionada.getCantidadBolsasUTL()!= null) {
			if(validarBolsaMayorUno()) 
				return true;
			if(validarColaboradoresMayorUno()) 
				return true;
			if(validaBolsaMenorIngresado())
				return true;
			if(validarVinculacionVsMontoCargo())
				return true;
			if(validarSalariosVinculadosVsMontoMaximo()) 
				return true;
			
			
			generarRegistrosUTL();
		}
		return false;
	}
	
	/**
	 * Metodo que valida si el numero de bolsas UTL/UAN se diminuyo desde el label y no desde los registros de la tabla
	 * @return
	 */
	public boolean validaBolsaMenorIngresado() {
		if(!listaResponsablesUTL.isEmpty()&&plantaSeleccionada.getCantidadBolsasUTL()!=null) {
			if(listaResponsablesUTL.size() > plantaSeleccionada.getCantidadBolsasUTL() ) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
						MessagesBundleConstants.MSG_PLANTA_UTL_BOLSAS_MENOR_GENERADO);
				plantaSeleccionada.setCantidadBolsasUTL(Long.valueOf(listaResponsablesUTL.size()));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo que valida si el numero de bolsas UTL/UAN se diminuyo desde el label y no desde los registros de la tabla
	 * @return boolean
	 */
	public boolean validarColaboradoresMayorUno() {
		if(plantaSeleccionada.getCantidadColaboradoresUTL() != null && plantaSeleccionada.getCantidadColaboradoresUTL() <= 0) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_PLANTA_ERROR_NUMERO_COLABORADORES_UTL);
				plantaSeleccionada.setCantidadColaboradoresUTL(null);
				return true;
		}
		return false;
	}
	
	/**
	 * Metodo que valida si la cantidad de bolsas es menor a 1
	 * @return boolean
	 */
	public boolean validarBolsaMayorUno() {
		if(plantaSeleccionada.getCantidadBolsasUTL()!= null && plantaSeleccionada.getCantidadBolsasUTL() <= 0) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_PLANTA_ERROR_NUMERO_BOLSAS_UTL);
				plantaSeleccionada.setCantidadBolsasUTL(!listaResponsablesUTL.isEmpty() ? Long.valueOf(listaResponsablesUTL.size()) : null );
				return true;
		}
		return false;
	}
	
	/**
	 * Metodo que valida si la cantidad de cargos dado es menor a la cantidad de vinculaciones que tienen las UTL
	 * @return boolean
	 */
	public boolean validarVinculacionVsMontoCargo() {
		if(plantaSeleccionada.getCodEntidadPlanta()!= null) {
			PlantaPersonaUtlUanExt objPlanta = new PlantaPersonaUtlUanExt();
			listaUTLExcedenCargos = new ArrayList<>();
			objPlanta.setCodEntidadPlanta(plantaSeleccionada.getCodEntidadPlanta());
			objPlanta.setCantidadColaboradoresUTL(plantaSeleccionada.getCantidadColaboradoresUTL().intValue());
			listaUTLExcedenCargos = ComunicacionServiciosEnt.getExcedenteBolsaUTL(objPlanta);
			if(!listaUTLExcedenCargos.isEmpty()) {
				this.setLblMensajeCargosVinculacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PLANTA_UTL_VINCULACION_MAYOR_BOLSA, getLocale()));
				RequestContext.getCurrentInstance().execute("PF('wdgtUtlMontoMaximo').show();");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo que valida si la cantidad de salario es mayor a la registrada en el monto maximo
	 * @return boolean
	 */
	public boolean validarSalariosVinculadosVsMontoMaximo() {
		if(plantaSeleccionada.getCodEntidadPlanta()!= null) {
			PlantaPersonaUtlUanExt objPlanta = new PlantaPersonaUtlUanExt();
			listaUTLExcedenCargos = new ArrayList<>();
			objPlanta.setCodEntidadPlanta(plantaSeleccionada.getCodEntidadPlanta());
			objPlanta.setMontoMaximo(varMontoMaximo);
			listaUTLExcedenCargos = ComunicacionServiciosEnt.getExcedenteBolsaUTL(objPlanta);
			if(!listaUTLExcedenCargos.isEmpty()) {
				this.setLblMensajeCargosVinculacion(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PLANTA_UTL_MONTO_MENOR_GASTADO, getLocale()));
				RequestContext.getCurrentInstance().execute("PF('wdgtUtlMontoMaximo').show();");
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Metodo que genera los registros en la tabla teniendo en cuenta los previamente creados
	 */
	public void generarRegistrosUTL(){
		if (this.listaResponsablesUTL == null)
			this.listaResponsablesUTL = new ArrayList<>();
		PlantaPersonaUtlUanExt fila;
		if(listaResponsablesUTL.size() <  plantaSeleccionada.getCantidadBolsasUTL()) {
			int diferencia = (int) (long) plantaSeleccionada.getCantidadBolsasUTL() -listaResponsablesUTL.size();
			for(int i = 0; i < diferencia; i++ ) {
				 fila = new PlantaPersonaUtlUanExt();
				 fila.setFlgActivo((short)1);
				listaResponsablesUTL.add(i, fila);
			}
		}
	}
	
	/**
	 * Metodo que carga todas las personas que estan vinculadas 
	 * en la UTL del responsable Seleccionada
	 */
	public void cargaPersonasVinculadasUTL(PlantaPersonaUtlUanExt plantaSeleccionada ) {
		HojaVidaExt objHV = new HojaVidaExt();
		if(plantaSeleccionada != null && plantaSeleccionada.getCodPlantaPersonaUtlUan() != null) {
			objHV.setCodUtlUan(plantaSeleccionada.getCodPlantaPersonaUtlUan());
			objHV.setFlgActivo((short) 1);
			listaPersonasVinculadasUTL = ComunicacionServiciosEnt.getHVPersonaAsociadaUTLXResponsable(objHV);
			if(listaPersonasVinculadasUTL.isEmpty()) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_UTL_SIN_VINCULACION);
				return;
			}
			RequestContext.getCurrentInstance().execute("PF('wdgtVerDetalleVinculados').show();");
		}
	}
	
	/**
	 * Metodo que verifica si existen personas vinculadas a una planta del responsable,
	 * si es asi, saca mensaje informativo, de lo contrario, elimina la planta del responsable
	 */
	public void eliminarPlantaResponsableUTL(PlantaPersonaUtlUanExt ultResponsableSelect) {
		HojaVidaExt objHV = new HojaVidaExt();
		if(ultResponsableSelect != null && ultResponsableSelect.getCodPlantaPersonaUtlUan() != null) {
			objHV.setCodUtlUan(ultResponsableSelect.getCodPlantaPersonaUtlUan());
			objHV.setFlgActivo((short) 1);
			listaPersonasVinculadasUTL = ComunicacionServiciosEnt.getHVPersonaAsociadaUTLXResponsable(objHV);
			if(!listaPersonasVinculadasUTL.isEmpty()) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_ELIMINACION_UTL);
				return;
			}
		}
		
		if(ultResponsableSelect.getCodPlantaPersonaUtlUan() == null) {
			ultResponsableSelect.setCodAudRol( (short)this.getUsuarioSesion().getCodRol());
			ultResponsableSelect.setCodAudUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
			ultResponsableSelect.setAudFechaActualizacion(new Date());
			ultResponsableSelect.setFlgActivo((short) 0);
			ultResponsableSelect.setCodAudAccion(new BigDecimal(TipoParametro.AUDITORIA_DELETE.getValue()));
			listaResponsablesUTL.remove(ultResponsableSelect);
			actualizarNumeroBolsas();
		}else {
			ultResponsableSelect.setCodAudRol( (short)this.getUsuarioSesion().getCodRol());
			ultResponsableSelect.setCodAudUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
			ultResponsableSelect.setAudFechaActualizacion(new Date());
			ultResponsableSelect.setCodEntidadPlanta(this.estadoEdicion ? this.plantaSeleccionada.getCodEntidadPlanta() : this.entiPlantPer.getCodEntidadPlanta());
			ultResponsableSelect.setFlgActivo((short) 0);
			ultResponsableSelect.setCodAudAccion(new BigDecimal(TipoParametro.AUDITORIA_DELETE.getValue()));
			PlantaPersonaUtlUan  planta = ComunicacionServiciosEnt.guardarPlantaPersonaUtl(ultResponsableSelect);
			if(!planta.isError()) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_ELIMINACION_EXITOSA);
				llenarTablaResponsablesUTL();
				actualizarNumeroBolsas();
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_ELIMINACION_FALLIDA);
			}
		}
		
		
	}
	
	/**
	 * Metodo que actualiza el numero de bolsas de la utl cuando esta se elimina
	 */
	public void actualizarNumeroBolsas() {
		EntidadPlantaExt plantaGuardar = new EntidadPlantaExt();
		plantaGuardar = plantaSeleccionada;
		plantaGuardar.setCantidadBolsasUTL(Long.valueOf(listaResponsablesUTL.size()));
		plantaGuardar.setAudFechaActualizacion(DateUtils.getFechaSistema());
		plantaGuardar.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		plantaGuardar.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		plantaGuardar.setAudCodRol((int) getUsuarioSesion().getCodRol());
		EntidadPlantaExt entiPlantPerResult = ComunicacionServiciosEnt.setEntidadPlanta(plantaGuardar);
		if(entiPlantPerResult.isError()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PLANTA_UTL_ACTUALIZACION_BOLSA_FALLIDA);
		}
	}
	
	 public void celdaEditada(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
	 }
	 
	public void postProcessXLSX(Object document) {
		int indexRow = 6; // columna desde donde se realizaría el filtro al momento de exportar
		
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessExcel(document, indexRow, TitlesBundleConstants.getStringMessagesBundle("TTL_GI_BREADCRUMB_SEGUIMIENTO"));
	}

	
	/** Get y set de variables */

	public List<EntidadPlantaDetalleExt> getResultadosPlantas() {
		return resultadosPlantas;
	}

	public void setResultadosPlantas(List<EntidadPlantaDetalleExt> resultadosPlantas) {
		this.resultadosPlantas = resultadosPlantas;
	}

	public List<EntidadPlantaExt> getResultadosPlanta() {
		return resultadosPlanta;
	}

	public void setResultadosPlanta(List<EntidadPlantaExt> resultadosPlanta) {
		this.resultadosPlanta = resultadosPlanta;
	}

	public boolean isEstadoCreacion() {
		return estadoCreacion;
	}

	public void setEstadoCreacion(boolean estadoCreacion) {
		this.estadoCreacion = estadoCreacion;
	}

	public boolean isEstTablaPla() {
		return estTablaPla;
	}

	public void setEstTablaPla(boolean estTablaPla) {
		this.estTablaPla = estTablaPla;
	}

	public Norma getNorma() {
		return norma;
	}

	public void setNorma(Norma norma) {
		this.norma = norma;
	}

	/**
	 * @return the entidadPlantaPersonal
	 */
	public EntidadPlantaExt getEntidadPlantaPersonal() {
		return entidadPlantaPersonal;
	}

	/**
	 * @param entidadPlantaPersonal
	 *            the entidadPlantaPersonal to set
	 */
	public void setEntidadPlantaPersonal(EntidadPlantaExt entidadPlantaPersonal) {
		this.entidadPlantaPersonal = entidadPlantaPersonal;
	}

	public boolean isSoloLectura() {
		return soloLectura;
	}

	public void setSoloLectura(boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public EntidadPlantaDetalleExt getCargoSeleccionado() {
		return cargoSeleccionado;
	}

	public void setCargoSeleccionado(EntidadPlantaDetalleExt cargoSeleccionado) {
		this.cargoSeleccionado = cargoSeleccionado;
	}

	public List<VinculacionExt> getListPersVincCargo() {
		return listPersVincCargo;
	}

	public void setListPersVincCargo(List<VinculacionExt> listPersVincCargo) {
		this.listPersVincCargo = listPersVincCargo;
	}

	public List<SelectItem> getLstDenominacionesEntidad() {
		return lstDenominacionesEntidad;
	}

	public void setLstDenominacionesEntidad(List<SelectItem> lstDenominacionesEntidad) {
		this.lstDenominacionesEntidad = lstDenominacionesEntidad;
	}

	public Boolean getFlgValidaRolPermiso() {
		return flgValidaRolPermiso;
	}

	public void setFlgValidaRolPermiso(Boolean flgValidaRolPermiso) {
		this.flgValidaRolPermiso = flgValidaRolPermiso;
	}

	public boolean isEstadoFiltros() {
		return estadoFiltros;
	}

	public Map<String, Long> getListaDependencias() {
		return listaDependencias;
	}

	public void setListaDependencias(Map<String, Long> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public Long getIdPlanta() {
		return idPlanta;
	}

	public void setIdPlanta(Long idPlanta) {
		this.idPlanta = idPlanta;
	}

	public void setEstadoFiltros(boolean estadoFiltros) {
		this.estadoFiltros = estadoFiltros;
	}

	/**
	 * @return the listPersVinc
	 */
	public List<VinculacionExt> getListPersVinc() {
		return listPersVinc;
	}

	/**
	 * @param listPersVinc
	 */
	public void setListPersVinc(List<VinculacionExt> listPersVinc) {
		this.listPersVinc = listPersVinc;
	}

	public Long getCodPlantaProceso() {
		return codPlantaProceso;
	}

	public void setCodPlantaProceso(Long codPlantaProceso) {
		this.codPlantaProceso = codPlantaProceso;
	}

	public boolean isEstadoSecCreacion() {
		return estadoSecCreacion;
	}

	public void setEstadoSecCreacion(boolean estadoSecCreacion) {
		this.estadoSecCreacion = estadoSecCreacion;
	}

	public boolean isEstadoInfoEntidad() {
		return estadoInfoEntidad;
	}

	public void setEstadoInfoEntidad(boolean estadoInfoEntidad) {
		this.estadoInfoEntidad = estadoInfoEntidad;
	}

	public Entidad getEntProceso() {
		return entProceso;
	}

	public void setEntProceso(Entidad entProceso) {
		this.entProceso = entProceso;
	}

	public EntidadPlantaExt getPlantaSeleccionada() {
		return plantaSeleccionada;
	}

	public void setPlantaSeleccionada(EntidadPlantaExt plantaSeleccionada) {
		this.plantaSeleccionada = plantaSeleccionada;
	}

	public List<EntidadPlantaDetalleExt> getResultadosCargos() {
		return resultadosCargos;
	}

	public void setResultadosCargos(List<EntidadPlantaDetalleExt> resultadosCargos) {
		this.resultadosCargos = resultadosCargos;
	}

	public Long getCodEntidadProceso() {
		return codEntidadProceso;
	}

	public void setCodEntidadProceso(Long codEntidadProceso) {
		this.codEntidadProceso = codEntidadProceso;
	}

	public List<SelectItem> getLstNiveles() {
		return lstNiveles;
	}

	public void setLstNiveles(List<SelectItem> lstNiveles) {
		this.lstNiveles = lstNiveles;
	}

	public List<SelectItem> getLstGrados() {
		return lstGrados;
	}

	public void setLstGrados(List<SelectItem> lstGrados) {
		this.lstGrados = lstGrados;
	}

	public boolean isBloqueoNomenclatura() {
		return bloqueoNomenclatura;
	}

	public void setBloqueoNomenclatura(boolean bloqueoNomenclatura) {
		this.bloqueoNomenclatura = bloqueoNomenclatura;
	}

	public boolean isEstadoJustifi() {
		return estadoJustifi;
	}

	public void setEstadoJustifi(boolean estadoJustifi) {
		this.estadoJustifi = estadoJustifi;
	}

	public boolean isEstadoTemporalidad() {
		return estadoTemporalidad;
	}

	public void setEstadoTemporalidad(boolean estadoTemporalidad) {
		this.estadoTemporalidad = estadoTemporalidad;
	}
	
	public Long getCargoPublicoSel() {
		return cargoPublicoSel;
	}

	public void setCargoPublicoSel(Long cargoPublicoSel) {
		this.cargoPublicoSel = cargoPublicoSel;
	}

	/**
	 * @return the lstNomenclaturaEntidad
	 */
	public List<NomenclaturaExt> getLstNomenclaturaEntidad() {
		return lstNomenclaturaEntidad;
	}

	/**
	 * @param lstNomenclaturaEntidad the lstNomenclaturaEntidad to set
	 */
	public void setLstNomenclaturaEntidad(List<NomenclaturaExt> lstNomenclaturaEntidad) {
		this.lstNomenclaturaEntidad = lstNomenclaturaEntidad;
	}

	/**
	 * Variable para validar roles del usuario en sesion
	 */
	
	public String getTituloBotonCrearPlanta() {
		return tituloBotonCrearPlanta;
	}

	public void setTituloBotonCrearPlanta(String tituloBotonCrearPlanta) {
		this.tituloBotonCrearPlanta = tituloBotonCrearPlanta;
	}

	public boolean isEstadoEdicion() {
		return estadoEdicion;
	}

	public void setEstadoEdicion(boolean estadoEdicion) {
		this.estadoEdicion = estadoEdicion;
	}

	public String getNumeroNorma() {
		return numeroNorma;
	}

	public void setNumeroNorma(String numeroNorma) {
		this.numeroNorma = numeroNorma;
	}

	/**
	 * @return the blnEliminarPlantaConVinc
	 */
	public Boolean getBlnEliminarPlantaConVinc() {
		return blnEliminarPlantaConVinc;
	}

	/**
	 * @param blnEliminarPlantaConVinc
	 *            the blnEliminarPlantaConVinc to set
	 */
	public void setBlnEliminarPlantaConVinc(Boolean blnEliminarPlantaConVinc) {
		this.blnEliminarPlantaConVinc = blnEliminarPlantaConVinc;
	}

	/**
	 * @return el required
	 */
	public Boolean getRequired() {
		return required;
	}

	/**
	 * @param required
	 *            el required a establecer
	 */
	public void setRequired(Boolean required) {
		this.required = required;
	}

	/**
	 * @return el requiredClasificacion
	 */
	public Boolean getRequiredClasificacion() {
		return requiredClasificacion;
	}

	/**
	 * @param requiredClasificacion
	 *            el requiredClasificacion a establecer
	 */
	public void setRequiredClasificacion(Boolean requiredClasificacion) {
		this.requiredClasificacion = requiredClasificacion;
	}

	/**
	 * @return el requiredTemporal
	 */
	public Boolean getRequiredTemporal() {
		return requiredTemporal;
	}

	/**
	 * @param requiredTemporal
	 *            el requiredTemporal a establecer
	 */
	public void setRequiredTemporal(Boolean requiredTemporal) {
		this.requiredTemporal = requiredTemporal;
	}

	public void setFlgisRolSoloConsulta(Boolean flgisRolSoloConsulta) {
		this.flgisRolSoloConsulta = flgisRolSoloConsulta;
	}

	public Boolean getFlgisRolSoloConsulta() {
		return flgisRolSoloConsulta;
	}
	/**
	 * @return the blnFechaFinObligatoria
	 */
	public Boolean getBlnFechaFinObligatoria() {
		return blnFechaFinObligatoria;
	}

	/**
	 * @param blnFechaFinObligatoria the blnFechaFinObligatoria to set
	 */
	public void setBlnFechaFinObligatoria(Boolean blnFechaFinObligatoria) {
		this.blnFechaFinObligatoria = blnFechaFinObligatoria;
	}

	/**
	 * @return the normaSeleccionada
	 */
	public Norma getNormaSeleccionada() {
		return normaSeleccionada;
	}

	/**
	 * @param normaSeleccionada the normaSeleccionada to set
	 */
	public void setNormaSeleccionada(Norma normaSeleccionada) {
		this.normaSeleccionada = normaSeleccionada;
	}

	/**
	 * @return the lstNormas
	 */
	public List<Norma> getLstNormas() {
		return lstNormas;
	}

	/**
	 * @param lstNormas the lstNormas to set
	 */
	public void setLstNormas(List<Norma> lstNormas) {
		this.lstNormas = lstNormas;
	}
	
	/**
	 * @return the blnPlantaUtl
	 */
	public Boolean getBlnPlantaUtl() {
		return blnPlantaUtl;
	}

	/**
	 * @param blnPlantaUtl the blnPlantaUtl to set
	 */
	public void setBlnPlantaUtl(Boolean blnPlantaUtl) {
		this.blnPlantaUtl = blnPlantaUtl;
	}


	/**
	 * @return the varSalariosMinimos
	 */
	public Long getVarSalariosMinimos() {
		return varSalariosMinimos;
	}

	/**
	 * @param varSalariosMinimos the varSalariosMinimos to set
	 */
	public void setVarSalariosMinimos(Long varSalariosMinimos) {
		this.varSalariosMinimos = varSalariosMinimos;
	}


	/**
	 * @return the varMontoMaximo
	 */
	public BigDecimal getVarMontoMaximo() {
		return varMontoMaximo;
	}

	/**
	 * @param varMontoMaximo the varMontoMaximo to set
	 */
	public void setVarMontoMaximo(BigDecimal varMontoMaximo) {
		this.varMontoMaximo = varMontoMaximo;
	}
	
	/**
	 * @return the listaResponsablesUTL
	 */
	public List<PlantaPersonaUtlUanExt> getListaResponsablesUTL() {
		return listaResponsablesUTL;
	}

	/**
	 * @param listaResponsablesUTL the listaResponsablesUTL to set
	 */
	public void setListaResponsablesUTL(List<PlantaPersonaUtlUanExt> listaResponsablesUTL) {
		this.listaResponsablesUTL = listaResponsablesUTL;
	}
	
	/**
	 * @return the listaPersonasVinculadasUTL
	 */
	public List<HojaVidaExt> getListaPersonasVinculadasUTL() {
		return listaPersonasVinculadasUTL;
	}

	/**
	 * @param listaPersonasVinculadasUTL the listaPersonasVinculadasUTL to set
	 */
	public void setListaPersonasVinculadasUTL(List<HojaVidaExt> listaPersonasVinculadasUTL) {
		this.listaPersonasVinculadasUTL = listaPersonasVinculadasUTL;
	}

	/**
	 * @return the listaUTLExcedenCargos
	 */
	public List<PlantaPersonaUtlUanExt> getListaUTLExcedenCargos() {
		return listaUTLExcedenCargos;
	}

	/**
	 * @param listaUTLExcedenCargos the listaUTLExcedenCargos to set
	 */
	public void setListaUTLExcedenCargos(List<PlantaPersonaUtlUanExt> listaUTLExcedenCargos) {
		this.listaUTLExcedenCargos = listaUTLExcedenCargos;
	}
	
	

	/**
	 * @return the lblMensajeCargosVinculacion
	 */
	public String getLblMensajeCargosVinculacion() {
		return lblMensajeCargosVinculacion;
	}

	/**
	 * @param lblMensajeCargosVinculacion the lblMensajeCargosVinculacion to set
	 */
	public void setLblMensajeCargosVinculacion(String lblMensajeCargosVinculacion) {
		this.lblMensajeCargosVinculacion = lblMensajeCargosVinculacion;
	}

	/**
	 * @return the lstVinculacionUTL
	 */
	public List<PlantaPersonaUtlUanExt> getLstVinculacionUTL() {
		return lstVinculacionUTL;
	}

	/**
	 * @param lstVinculacionUTL the lstVinculacionUTL to set
	 */
	public void setLstVinculacionUTL(List<PlantaPersonaUtlUanExt> lstVinculacionUTL) {
		this.lstVinculacionUTL = lstVinculacionUTL;
	}

	/**
	 * @return the blnModificarPlanta
	 */
	public Boolean getBlnModificarPlanta() {
		return blnModificarPlanta;
	}

	/**
	 * @param blnModificarPlanta the blnModificarPlanta to set
	 */
	public void setBlnModificarPlanta(Boolean blnModificarPlanta) {
		this.blnModificarPlanta = blnModificarPlanta;
	}
	
	

	/**
	 * @return the ultimaVinculacionCargo
	 */
	public VinculacionExt getUltimaVinculacionCargo() {
		return ultimaVinculacionCargo;
	}

	/**
	 * @param ultimaVinculacionCargo the ultimaVinculacionCargo to set
	 */
	public void setUltimaVinculacionCargo(VinculacionExt ultimaVinculacionCargo) {
		this.ultimaVinculacionCargo = ultimaVinculacionCargo;
	}

	public String getStrTargetExportar() {
		return strTargetExportar;
	}

	public void setStrTargetExportar(String strTargetExportar) {
		this.strTargetExportar = strTargetExportar;
	}

	public List<SelectItem> getLstDependencias() {
		return lstDependencias;
	}

	public void setLstDependencias(List<SelectItem> lstDependencias) {
		this.lstDependencias = lstDependencias;
	}
	
	public List<SelectItem> getLstNivel() {
		return lstNivel;
	}

	public void setLstNivel(List<SelectItem> lstNivel) {
		this.lstNivel = lstNivel;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
	}

	@Override
	public String persist() throws NotSupportedException {
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		this.consultarPlanta(null);
	}

	@Override
	public String update() throws NotSupportedException {
		this.modificarPlanta(null);
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		this.eliminarPlanta(null);
	}
}
