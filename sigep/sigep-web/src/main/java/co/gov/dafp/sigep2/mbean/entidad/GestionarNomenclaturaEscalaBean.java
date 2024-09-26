
package co.gov.dafp.sigep2.mbean.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.AsignacionSalarial;
import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.IncrementoSalarial;
import co.gov.dafp.sigep2.entities.Nomenclatura;
import co.gov.dafp.sigep2.entities.NomenclaturaDenominacion;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.AsignacionSalarialExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
* @author  Maria Alejandra Colorado
* @version 1.0
* @Class GestionarNomenclaturaEscalaBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Entidades
* Fecha: 10/12/2018
*/
@Named
@ViewScoped
@ManagedBean
public class GestionarNomenclaturaEscalaBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = 4286974099510164295L;

	private Long lngValorEntidad; //valor de la entidad seleccionada o de session.
	private Long lngValorOtraAsignacion; //Valor de la otra Asignacion 
	BigDecimal lbgValorIncremento; //Valor a incrementar en la escala salarial
	
	private String txtJustificacion;
	
	private String strOpcionIncremento 			= "0"; //Almacena el valor de la opción seleccionada (valor o %) incremental
	private String strOpcionOtraAsignacion 		= "0"; //Almacena el valor de la opcion seleccionada para la otra Asignacion
	private String strOpcionNomenclaturaBase 	= "0";
	private String strMensajeNomenclaturaBase 	= "";
	
	private List<NomenclaturaExt> lstNomenclaturaEntidad; // Lista que contiene las nomenclaturas asociadas a la entidad seleccionada
	private List<NomenclaturaDenominacionExt> lstDenominacionesNomenclatura; //Lista que contiene las denominaciones de una entidad
	private List<NomenclaturaDenominacionExt> lstDenominacionesNomenclaturaBase; //Lista que contiene las denominaciones de la nomenclatura base seleccionada
	private List<NomenclaturaExt> lstNomenclaturasBase; //Lista que contiene todas las nomenclaturas base
	private List<EntidadPlantaExt> listaPlantas; //Lista que contiene las plantas que estan asociadas a la nomenclatura a eliminar
	private List<VinculacionExt> lstVinculacionDenominacion; //Lista que contiene todas las vinculaciones a las cuales esta asociada una denominacion
	private List<AsignacionSalarialExt> lstOtrasAsignacionesSalariales; //Lista las otras asignaciones salariales de la nomenclatura.
	private List<EntidadPlantaDetalleExt> lstPlantaDetalleDenominacion; // Lista que trae las plantas a las cuales esta asociada una denominacion
	private List<Norma> lstNormas; //Trae las listas por filtro de Tipo de norma, fecha norma y numero de norma
	private List<SelectItem> lstGradosDenominaciones;
	
	private Boolean blnVisibleFormularioNomenclatura; //Determina si se visualiza el formulario nomenclatura
	private Boolean blnVisibleFormularioNomenclaturaBase; //Determina si se muestra el formulario de nomenclatura base
	private Boolean blnVisibleMensajeCrear; // Determina si se muestra el mensaje de crear.
	private Boolean blnVisibleMensajeModificar; // Determina si se muestra el mensaje de modificar.
	private Boolean blnVisibleMensajeConsultar; // Determina si se muestra el mensaje de consultar
	private Boolean blnAccionConsultar; // Determina si se modifican o no los campos del formulario de crear nomenclatura
	private Boolean blnAccionConsultarDenominacion ; //Determina si se modifican o no los campos del formulario modificar denominacion
	private Boolean blnAccionIncrementoSalarial; //Determina si se muestra o no el panel de Incremento y escala Salarial
	private Boolean blnAccionOtraAsignacion; //Determina si se muestra o no el panel de Otras asignaciones
	private Boolean blnAccionConsultarOtraAsignacion; //Determina si se permite la modificacion o no del panel de Otras asignaciones
	private Boolean blnAccionPersonalizarGeneral; // Determina si se muestra o no el panel para personalizar una nomenclatura general
	private Boolean blnIncrementoSalarial; //Determina que se ingreso un valor o % para incrementar en las denominaciones
	private Boolean blnNomenclaturaBase; //Determina si la nomenclatura a guardar viene de una existente(Base)
	private Boolean blnAccionEquivalencia; //Determina si se muestra las opciones de equivalencia en la tabla denominaciones
	private Boolean blnPermisoNomenclatura; // Si es falso, el OpertTH y JefeTH no tiene permisos para crearNomenclatura
	private Boolean blnPermisoPersonalizacion; // Valida si la persona tiene permisos de personalizar la nomenclatura general
	private Boolean blnOtraAsignacionSalarial;
	private Boolean blnPersonalizarTodo; //Valida si se presiono el boton personalizar todo
	private Boolean blnOrganoAutonomo; //Valida si se presiono el boton personalizar todo
	
	private NomenclaturaExt nomenclaturaExt; //Almacena los datos de la nomenclatura
	private NomenclaturaExt nomenclaturaBaseSelect; // Almacena la nomenclatura base seleccionada
	private NomenclaturaDenominacionExt denominacion; //Almacena la denominacion de una nomenclatura
	private Nomenclatura nomenclaturaRespuesta; //Almacena la nomenclatura creada para ser utilizada al momento de guardar las denominaciones
	private NomenclaturaDenominacionExt nomenclaturaDenominacionDestino; //Nomenclatura destino componente equivalencias
	private NomenclaturaExt nomenclaturaBase; //Almacena los datos de la nomenclatura base
	
	private Norma normaEscalaSalarial; //Almacena la norma de la escala Salarial
	private Norma normaEscalaSalarialSeleccionada;
	
	private Norma normaNomenclatura;//Almacena la nomenclatura diligenciada o modificada
	private Norma normaNomenclaturaSeleccionada; //Almacena el valor del numero de la norma seleccionada
	
	private IncrementoSalarial incrementoSalarial; //Almacena la informacion de los datos a incrementar de la denominacion
	private AsignacionSalarialExt asignacionSalarialProcesar; //Almacena la informacion de la otra asignacion salarial a crear 
	
	private boolean blDenominacionNueva, blDenominacionModificar;
	
	/*Variables para validacion de denominacionExistente*/
	private Long 	codNivelAnt;
	private Long 	codDenAnt;
	private String 	codCodAnt;
	private Long 	codGradoAnt;
	
	@PostConstruct
	public void init() {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		nomenclaturaExt 		= new NomenclaturaExt();
		nomenclaturaBaseSelect 	= new NomenclaturaExt();
		denominacion 			= new NomenclaturaDenominacionExt();
		lstNomenclaturaEntidad 	= new ArrayList<>();
		lstDenominacionesNomenclatura 	= new ArrayList<>();
		asignacionSalarialProcesar 		= new AsignacionSalarialExt();
		lstNomenclaturasBase 			= new ArrayList<>();
		lstOtrasAsignacionesSalariales 	= new ArrayList<>();
		normaNomenclatura 				= new Norma();
		blnPermisoNomenclatura 		= false;
		blnPermisoPersonalizacion 	= false;
		blnPersonalizarTodo 		= false;
		blDenominacionNueva			= false;
		blDenominacionModificar		= false;
		blnOrganoAutonomo 			= false;
		reiniciarCampos();
		validarPermisosRoles();
		if(!validarPermisoRol()){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"window.location.assign('../../gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;
    	}
		
		validarEntidad();
		cargarGradosDenominaciones();
		cargarNomenclaturasEntidad();
	}
	
	public void obtenerInformacionEntidad() {
	
		Entidad objEntidad = ComunicacionServiciosEnt.getEntidadPorId(lngValorEntidad);
		if(objEntidad !=null && objEntidad.getCodClasificacionOrganica()!= null 
				&& objEntidad.getCodClasificacionOrganica().equals(BigDecimal.valueOf(TipoParametro.ORGANO_AUTONOMO.getValue()))){
			blnOrganoAutonomo = true;
		}		
	}
	
	public void cargarGradosDenominaciones() {
		WebUtils webUtils = new WebUtils();
		lstGradosDenominaciones = webUtils.getGradosClasificacionOrganica(blnOrganoAutonomo);
	}
	
	/**
	 * Metodo que valida los permisos de las acciones desde el componente:
	 * Administracion del sistema- Administrar Permiso Rol
	 */
	public void validarPermisosRoles() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);
	}

	/**
	 * Solo ingresan a la Nomenclatura Especifica
	 * AdminFunc, AdminEnt, caracterizadorEntidad, OperTH, JefeTH
	 * @return boolean
	 */
	private boolean validarPermisoRol(){
		try {
			if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, 
					RolDTO.CARACTERIZADOR_ENTIDAD, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.JEFE_TALENTO_HUMANO )) {
				validarPermisoNomenclatura();
				return true;
			}		
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Valida si el usuario ingresado tiene permiso para realizar acciones correspondientes a la nomenclatura
	 * 1. AdminFunc, AdminEnt, caracterizadorEntidad solo permisos para nomenclatura y escala Salarial
	 * 2. OperTH, JefeTH solo permisos para Escala Salarial
	 */
	public void validarPermisoNomenclatura() {
		try {
			if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, 
					RolDTO.CARACTERIZADOR_ENTIDAD,RolDTO.SUPER_ADMINISTRADOR)) {
				blnPermisoNomenclatura= true;
			}else if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.JEFE_TALENTO_HUMANO)){
				blnPermisoNomenclatura = false;
			}else if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, 
					RolDTO.JEFE_TALENTO_HUMANO,RolDTO.SUPER_ADMINISTRADOR,RolDTO.USUARIO_SISTEMA)) {
				blnPermisoPersonalizacion = true;
			}		
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que captura la entidad seleccionada
	 */
	public void validarEntidad() {
		Entidad entidadProceso = (Entidad) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("entidadProceso");
		if (entidadProceso != null) {
			lngValorEntidad = entidadProceso.getCodEntidad().longValue();
		}else {
			lngValorEntidad = getEntidadUsuario().getId();
		}
		
		if(lngValorEntidad != null) {
			obtenerInformacionEntidad();
		}
	}
	
	/**
	 * Metodo que carga las nomenclaturas de la entidad que estan activas. 
	 */
	public void cargarNomenclaturasEntidad() {
		validarPermisoNomenclatura();
		NomenclaturaExt filtroNomenclatura = new NomenclaturaExt();
		filtroNomenclatura.setFlgActivo((short) 1);
		filtroNomenclatura.setFlgGenerica((short) 0);
		filtroNomenclatura.setCodEntidad(new BigDecimal(lngValorEntidad));
		lstNomenclaturaEntidad = ComunicacionServiciosEnt.getNomenclaturaFiltro(filtroNomenclatura);
	}
	
	/**
	 * Metodo que trae las denominaciones correspondientes a
	 * la nomenclatura base seleccionada
	 */
	private void cargarDenominacionesNomenclaturaBase(){
		if(nomenclaturaBase.getCodNomenclatura()!= null) {
			NomenclaturaDenominacionExt denominacionesBusqueda= new NomenclaturaDenominacionExt();
			denominacionesBusqueda.setFlgActivo((short) 1);
			denominacionesBusqueda.setCodNomenclatura(new BigDecimal(nomenclaturaBase.getCodNomenclatura()));
			lstDenominacionesNomenclaturaBase = ComunicacionServiciosEnt.getDenomincacionesNomenclaturaExtFiltro(denominacionesBusqueda);
		}
	}
	
	/**
	 * Metodo que abre panel para crear una nueva nomenclatura
	 */
	public void abrirPanelCrearNomenclatura() {
		nomenclaturaExt 	= new NomenclaturaExt();
		denominacion 		= new NomenclaturaDenominacionExt();
		lstDenominacionesNomenclatura 	= new ArrayList<>();
		lstOtrasAsignacionesSalariales 	= new ArrayList<>();
		reiniciarCampos();
		reiniciarCamposNorma();
		blnVisibleFormularioNomenclatura 	= true;
		blnVisibleMensajeCrear 				= true;
	}
	
	/**
	 * Metodo que cierra el formulario de creacion o edicion de nomenclatura
	 * y limpia los campos 
	 */
	public void cancelarEdicionNomenclatura() {
		reiniciarCampos();
		nomenclaturaExt = new NomenclaturaExt();
		denominacion 	= new NomenclaturaDenominacionExt();
	}
	
	/**
	 * Metodo que carga los valores a modificar de la nomenclatura seleccionada 
	 */
	public void modificarNomenclaturaEntidad(NomenclaturaExt nomenclaturasel) {
		reiniciarCampos();
		reiniciarCamposNorma();
		blnVisibleFormularioNomenclatura 	= true;
		blnVisibleMensajeModificar 			= true;
		nomenclaturaExt = nomenclaturasel;
		buscarNormaNomenclatura();
		cargarDenominacionesNomenclatura();
	}
	
	/**
	 * Metodo que busca las normas de las nomenclaturas.
	 */
	public void buscarNormaNomenclatura() {
		if(nomenclaturaExt != null && nomenclaturaExt.getCodNorma()!=null) {

			Norma norma= ComunicacionServiciosEnt.getNormaPorId(nomenclaturaExt.getCodNorma().longValue());
			if(norma != null) {
				normaNomenclatura = norma;
				normaNomenclaturaSeleccionada = norma;
			}
		}
	}
	
	/**
	 * Metodo que reinicia los campos del formulario
	 */
	public void reiniciarCampos() {
		blnVisibleFormularioNomenclatura 		= false;
		blnVisibleFormularioNomenclaturaBase 	= false;
		blnVisibleMensajeModificar 				= false;
		blnVisibleMensajeCrear 					= false;
		blnVisibleMensajeConsultar 				= false;
		blnAccionConsultar 						= false;
		blnAccionOtraAsignacion	 				= false;
		blnAccionIncrementoSalarial 			= false;
		blnNomenclaturaBase 					= false;
		blnIncrementoSalarial 					= false;
		blnAccionEquivalencia					= false;
		blnOtraAsignacionSalarial 				= false;
		blnAccionPersonalizarGeneral 			= false;
	}
	
	/**
	 * Metodo que permite visualizar la nomenclatura y sus denominaciones
	 */
	public void verNomenclaturaEntidad(NomenclaturaExt nomenclaturasel) {
		reiniciarCampos();
		reiniciarCamposNorma();
		blnAccionConsultar 			= true;
		blnVisibleMensajeConsultar 	= true;
		blnVisibleFormularioNomenclatura = true;
		nomenclaturaExt = nomenclaturasel;
		cargarDenominacionesNomenclatura();
		buscarNormaNomenclatura();
	}
	
	/**
	 * Metodo que permite Personalizar una nomenclatura que viene desde la escala general
	 */
	public void personalizarNomenclaturaGeneral(NomenclaturaExt nomenclaturasel) {
		reiniciarCampos();
		reiniciarCamposNorma();
		blnAccionPersonalizarGeneral 	 = true;
		blnVisibleFormularioNomenclatura = true;
		nomenclaturaExt = nomenclaturasel;
		cargarDenominacionesPersonalizar();
		buscarNormaNomenclatura();	
	}
	
	/**
	 * Metodo que carga inicialmente, las denominaciones que posee la nomenclatura, tanto activas como inactivas
	 */
	public void cargarDenominacionesPersonalizar() {
		if (nomenclaturaExt.getCodNomenclatura() != null) {
			NomenclaturaDenominacionExt denominacionesBusqueda = new NomenclaturaDenominacionExt();
			denominacionesBusqueda.setFlgActivo((short)1);
			denominacionesBusqueda.setCodNomenclatura(BigDecimal.valueOf(nomenclaturaExt.getCodNomenclatura()));
			lstDenominacionesNomenclatura = ComunicacionServiciosEnt.getDenomincacionesNomenclaturaExtFiltro(denominacionesBusqueda);
		}
	}
	
	/**
	 * Componente de Personalizar nomenclatura y escala salarial
	 * Metodo que selecciona todas las denominaciones con las que cuenta la nomenclatura
	 */
	public void seleccionarTodasDenominaciones() {
		if(blnPersonalizarTodo) {
			for(int i = 0; i<lstDenominacionesNomenclatura.size(); i++) {
				lstDenominacionesNomenclatura.get(i).setFlgActivoParticular((short)1);
			}
		}else {
			for(int i = 0; i<lstDenominacionesNomenclatura.size(); i++) {
				lstDenominacionesNomenclatura.get(i).setFlgActivoParticular((short)0);
			}
		}
	}
	
	/**
	 * Componente de Personalizar nomenclatura y escala salarial
	 * Metodo que valida que las nomenclaturas seleccionadas, no cuenten con
	 * 1. Planta
	 * 2. Vinculaciones
	 */
	public void validarDenominacionPersonalizar(NomenclaturaDenominacionExt denominacion) {
		boolean valid = false; 
		if(denominacion!=null && denominacion.getFlgActivoParticular() == 0) {
			valid = this.validarVinculaciones(denominacion);
			if(valid) 
				return;
			valid = this.validarPlantaDemoninacionPersonalizar(denominacion);
			if(valid) 
				return;
		mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_PROCESO_EXITOSO);
		}
	}
	
	/**
	 * Metodo que valida si la denominacion seleccionada presenta plantas de personal asociadas
	 */
	public boolean validarPlantaDemoninacionPersonalizar(NomenclaturaDenominacionExt denominacion) {
		boolean blnPlanta = false;
		EntidadPlantaDetalleExt entidadPlantaDetalle = new EntidadPlantaDetalleExt();
		entidadPlantaDetalle.setCodNomenclaturaDenominacion(denominacion.getCodNomenclaturaDenominacion());
		entidadPlantaDetalle.setFlgActivo((short)1);
		entidadPlantaDetalle.setCodEntidad(lngValorEntidad);
		entidadPlantaDetalle.setLimitInit(0);
		entidadPlantaDetalle.setLimitEnd(100);
		lstPlantaDetalleDenominacion  = ComunicacionServiciosEnt.getPlantaPersonalEntidadCargos(entidadPlantaDetalle);
		 if(!lstPlantaDetalleDenominacion.isEmpty()) {
			 blnPlanta = true;
			 denominacion.setFlgActivoParticular((short)1); 
			 RequestContext.getCurrentInstance().execute("PF('wdgtDetallePlantasAsociadasPersonalizacion').show();");
		 }
		return blnPlanta;
	}
	
	/**
	 * Metodo que valida si una denominacion cuenta con vinculaciones activas
	 * @return
	 */
	public boolean validarVinculaciones(NomenclaturaDenominacionExt denominacion) {
		boolean blnVinculacion = false;
		EntidadPlantaDetalleExt entidadPlantaDetalle = new EntidadPlantaDetalleExt();
		entidadPlantaDetalle.setCodNomenclaturaDenominacion(denominacion.getCodNomenclaturaDenominacion());
		entidadPlantaDetalle.setFlgActivo((short)1);
		entidadPlantaDetalle.setLimitInit(0);
		entidadPlantaDetalle.setCodEntidad(lngValorEntidad);;
		entidadPlantaDetalle.setLimitEnd(100);
		lstPlantaDetalleDenominacion  = ComunicacionServiciosEnt.getVinculacionPlanta(entidadPlantaDetalle);
		 if(!lstPlantaDetalleDenominacion.isEmpty()) {
			 blnVinculacion = true;
			 denominacion.setFlgActivoParticular((short)1); 
			 RequestContext.getCurrentInstance().execute("PF('wdgtDetallePlantasAsociadasPersonalizacion').show();");
		 }
		return blnVinculacion;
	}
	
	/**
	 * Cancela las acciones de modificacion o consulta de una denominacion
	 */
	public void cancelarDenominacion() {
		blnAccionConsultarDenominacion = false; 
	}
	
	/**
	 * Metodo que permite visualizar la denominacion seleccionada
	 */
	public void verDenominacionNomenclatura(NomenclaturaDenominacionExt denominacionsele) {
		blnAccionConsultarDenominacion = true;
		denominacion = denominacionsele;
		RequestContext.getCurrentInstance().execute("PF('wdgtNuevaDenominacion').show();");
	}
	
	/**
	 * Metodo que trae las denominaciones correspondientes a
	 * la nomenclatura seleccionada
	 */
	private void cargarDenominacionesNomenclatura(){
		if(nomenclaturaExt.getCodNomenclatura()!= null) {
			NomenclaturaDenominacionExt denominacionesBusqueda= new NomenclaturaDenominacionExt();
			if(nomenclaturaExt.getCodNomenclaturaAsociada()!=null 
					&& nomenclaturaExt.getCodNomenclaturaAsociada() != TipoParametro.COD_GENERAL_TERRITORIAL.getValue())
				denominacionesBusqueda.setFlgActivoParticular((short)1);
			denominacionesBusqueda.setFlgActivo((short) 1);
			denominacionesBusqueda.setCodNomenclatura(BigDecimal.valueOf(nomenclaturaExt.getCodNomenclatura()));
			lstDenominacionesNomenclatura = ComunicacionServiciosEnt.getDenomincacionesNomenclaturaExtFiltro(denominacionesBusqueda);
		}	
	}
	
	/**
	 * Metodo que carga la denominacion a modificar.
	 */
	public void modificarDenominacionNomenclatura(NomenclaturaDenominacionExt denominacionNomenclatura) {
		blDenominacionNueva=false;
		blDenominacionModificar=true;
		this.denominacion = denominacionNomenclatura;
		codNivelAnt = denominacion.getCodNivelJerarquico();
		codDenAnt 	= denominacion.getCodDenominacion();
		codCodAnt 	= denominacion.getCodCodigo();
		codGradoAnt = denominacion.getCodGrado();
		RequestContext.getCurrentInstance().execute("PF('wdgtNuevaDenominacion').show();");
	}
	
	/**
	 * Metodo que permite eliminar una nomenclatura
	 */
	public void eliminarNomenclatura(){
		validarPlantasAsociadas();
		if(listaPlantas.isEmpty()) {
			nomenclaturaExt.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
			nomenclaturaExt.setAudCodRol((int)this.getRolAuditoria().getId());
			nomenclaturaExt.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			nomenclaturaExt.setAudFechaActualizacion(DateUtils.getFechaSistema());
			nomenclaturaExt.setFlgActivo((short) 0);
			
			Nomenclatura nomenclaturaRespuesta;
			nomenclaturaRespuesta = ComunicacionServiciosEnt.setNomenclaturaRespuesta(nomenclaturaExt);
			if(nomenclaturaRespuesta.isError()){
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_ELIMINAR_ERROR);	
			}else{
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_ELIMINAR_EXITO);
			}
			cargarNomenclaturasEntidad();		
		}else {
			 RequestContext.getCurrentInstance().execute("PF('wdgtDetallePlantasAsociadasNomenclatura').show();");
		}
	
	}
	
	/**
	  * Metodo que permite validar si una nomenclatura ya esta asociada a una planta
	  */
	 public void validarPlantasAsociadas() {
		 EntidadPlantaExt entFiltro = new EntidadPlantaExt();
		 entFiltro.setCodNomenclatura(nomenclaturaExt.getCodNomenclatura());
		 entFiltro.setFlgActivo((short)1);
		 listaPlantas = ComunicacionServiciosVin.getEntidadPlantaFilter(entFiltro);
	 }
	 
	/**
	 * Metodo que elimina la denominacion seleccionada
	 */
	public void eliminarDenominacion() {
		boolean valid = false;
		valid = verificarVinculacionEnDenominacion();
		if(valid) 
			return;
		valid = verificarDenominacionAsociadaPlanta();
		if(valid) 
			return;
		
		denominacion.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
		denominacion.setAudCodRol((int)this.getRolAuditoria().getId());
		denominacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		denominacion.setAudFechaActualizacion(DateUtils.getFechaSistema());
		denominacion.setFlgActivo((short) 0);
		NomenclaturaDenominacion nomenclaturaDenominacionRespuesta = ComunicacionServiciosEnt.setNomenclaturaDenominacion(denominacion);
			if(nomenclaturaDenominacionRespuesta.isError()){
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_ELIMINAR_ERROR);	
			}else{
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_ELIMINAR_EXITO);
			}
			cargarDenominacionesNomenclatura();
	}
	
	/**
	 * Metodo que verifica si la denominacion seleccionada esta asociada a una planta de personal activa
	 * @return plantaDetalle
	 */
	public boolean verificarDenominacionAsociadaPlanta(){
		boolean plantaDetalle = false;
		if(denominacion.getCodNomenclaturaDenominacion()!=null) {
			EntidadPlantaDetalleExt entidadPlantaDetalle = new EntidadPlantaDetalleExt();
			entidadPlantaDetalle.setCodNomenclaturaDenominacion(denominacion.getCodNomenclaturaDenominacion());
			entidadPlantaDetalle.setCodEntidad(lngValorEntidad);
			entidadPlantaDetalle.setFlgActivo((short)1);
			entidadPlantaDetalle.setLimitInit(0);
			entidadPlantaDetalle.setLimitEnd(100);
			lstPlantaDetalleDenominacion  = ComunicacionServiciosEnt.getPlantaPersonalEntidadCargos(entidadPlantaDetalle);
			 if(!lstPlantaDetalleDenominacion.isEmpty()) {
				 plantaDetalle = true;
				 RequestContext.getCurrentInstance().execute("PF('wdgtDetallePlantasAsociadasDenominacion').show();");
			 }
		}
		
		 return plantaDetalle;
	}
	
	/**
	 * Metodo que verifica si una denominacion ya cuenta con una 
	 */
	
	public boolean verificarVinculacionEnDenominacion() {
		boolean blnVinculacion = false;
		if(denominacion.getCodNomenclaturaDenominacion()!=null) {
			EntidadPlantaDetalleExt entidadPlantaDetalle = new EntidadPlantaDetalleExt();
			entidadPlantaDetalle.setCodNomenclaturaDenominacion(denominacion.getCodNomenclaturaDenominacion());
			entidadPlantaDetalle.setFlgActivo((short)1);
			entidadPlantaDetalle.setLimitInit(0);
			entidadPlantaDetalle.setLimitEnd(100);
			entidadPlantaDetalle.setCodEntidad(lngValorEntidad);
			lstPlantaDetalleDenominacion  = ComunicacionServiciosEnt.getVinculacionPlanta(entidadPlantaDetalle);
			 if(!lstPlantaDetalleDenominacion.isEmpty()) {
				 blnVinculacion = true;
				 denominacion.setFlgActivo((short)1); 
				 RequestContext.getCurrentInstance().execute("PF('wdgtDetallePlantasAsociadasDenominacion').show();");
			 }
		}
		return blnVinculacion;
	}
	
	/**
	 * Guarda la nomenclatura
	 * @param strTipoGuardado 0:Parcial 1:Definitivo
	 */
	public void guardarNomenclatura(String strTipoGuardado) {
		//Valida que se ingrese la justficacion en caso de que no se haya ingresado ningun valor en norma
		if( (nomenclaturaExt.getJustificacion()==null || "".equals(nomenclaturaExt.getJustificacion())) && 
				(normaNomenclatura.getNumeroNorma()==null||normaNomenclatura.getFechaNorma()==null || normaNomenclatura.getCodTipoNorma()==null)){
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INGRESAR_NORMA_JUSTIFICACION);
			return;
		}
		
		validarNormaNomenclatura();
		
		if(blnNomenclaturaBase) {
			nomenclaturaExt.setCodNomenclatura(null);
		}
		if(nomenclaturaExt.getCodNomenclatura() !=null) {
			nomenclaturaExt.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}else {
			nomenclaturaExt.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}
		nomenclaturaExt.setAudCodRol((int)this.getRolAuditoria().getId());
		nomenclaturaExt.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		nomenclaturaExt.setAudFechaActualizacion(new Date());
		nomenclaturaExt.setFlgActivo((short) 1);
		nomenclaturaExt.setCodEntidad(new BigDecimal(lngValorEntidad));
		nomenclaturaExt.setFlgGenerica((short)0);
		nomenclaturaExt.setFlgDefinitivo(Short.valueOf(strTipoGuardado));
		nomenclaturaRespuesta = ComunicacionServiciosEnt.setNomenclaturaRespuesta(nomenclaturaExt);
		if(nomenclaturaRespuesta.isError()){
			if("1".equals(strTipoGuardado)) {
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_PLANTA_GUARDADO_DEFINITIVO_ERROR);
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_GUARDAR_PARCIAL_ERROR);
			}
		}else{
			if(guardarDenominaciones() && guardarOtrasAsignaciones()) {
				if("1".equals(strTipoGuardado)) {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_PLANTA_GUARDADO_DEFINITIVO_EXITO);
				}else {
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_GUARDAR_PARCIAL_EXITO);
				}
			} 
		}
		cargarNomenclaturasEntidad();
		reiniciarCampos();
		validarPermisoNomenclatura();
		reiniciarCamposNorma();
	}
	
	/**
	 * Metodo que reinicia los campos de la norma
	 */
	public void reiniciarCamposNorma() {
		normaNomenclatura = new Norma();
		normaNomenclaturaSeleccionada = new Norma();
		normaEscalaSalarial = new Norma();
		normaEscalaSalarialSeleccionada = new Norma();
		lstNormas = new ArrayList<>();
	}
	
	/**
	 * Metodo que guarda las escalas salariales correspondientes a otras asignaciones
	 * @return
	 */
	private boolean guardarOtrasAsignaciones(){
		boolean guardadoAsignaciones = true;
		if(lstOtrasAsignacionesSalariales!=null && lstOtrasAsignacionesSalariales.size()>0){
			AsignacionSalarial respuesta;
			for(AsignacionSalarial asignacion:lstOtrasAsignacionesSalariales){
				if(asignacion.getCodAsignacionSalarial()==null) {
					asignacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				}else {
					asignacion.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				}
				respuesta = ComunicacionServiciosEnt.setAsignacionSalarial(asignacion);
				if(respuesta.isError()){
					guardadoAsignaciones = false;
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_ERROR_GUARDAR);
					break;
				}
			}
		}
		return guardadoAsignaciones;
	}
	
	
	/**
	 * Guarda el incremente salarial cuando la acción haya sido confirmada
	 */
	public void guardarIncrementoSalarial(){
		if(lstDenominacionesNomenclatura!=null && lstDenominacionesNomenclatura.size()>0){
			for(NomenclaturaDenominacionExt n:lstDenominacionesNomenclatura){
				if(n.getCodNomenclaturaDenominacion()!=null)
					n.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
				n.setAudCodRol((int)this.getRolAuditoria().getId());
				n.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
				n.setAudFechaActualizacion(new Date());
				n.setCodNomenclatura(BigDecimal.valueOf(nomenclaturaExt.getCodNomenclatura()));
				if(n.getNuevaAsignacionSalarial()!=null)
					n.setAsignacionSalarial(n.getNuevaAsignacionSalarial());
				n.setIncFecha(new Date());
				NomenclaturaDenominacion res  =  ComunicacionServiciosEnt.setNomenclaturaDenominacion(n);
				if(res.isError()){
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_ERROR);
					return;
				}
			}
			incrementoSalarial = new IncrementoSalarial();
			incrementoSalarial.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
			incrementoSalarial.setAudCodRol((int)this.getRolAuditoria().getId());
			incrementoSalarial.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			incrementoSalarial.setAudFechaActualizacion(new Date());
			validarNormaEscalaSalarial();
			incrementoSalarial.setCodNomenclatura(nomenclaturaExt.getCodNomenclatura());
			 
			 IncrementoSalarial res = ComunicacionServiciosEnt.guardarIncremento(incrementoSalarial);
			 if(res.isError()){
				 mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_ERROR);
				 blnAccionIncrementoSalarial = false;
				 blnIncrementoSalarial = false;
				 blnVisibleFormularioNomenclatura = false;
				 lbgValorIncremento = null;
				 txtJustificacion = "";
				 return;
			 }else{
				 mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_EXITO);
				 blnAccionIncrementoSalarial = false;
				 blnIncrementoSalarial = false;
				 blnVisibleFormularioNomenclatura = false;
				 lbgValorIncremento = null;
				 txtJustificacion = "";
				 return; 
			 }
		}else{
			 mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_ERROR);
			 blnAccionIncrementoSalarial = false;
			 blnIncrementoSalarial = false;
			 blnVisibleFormularioNomenclatura = false;
			 lbgValorIncremento = null;
			 txtJustificacion = "";
			 return;			
		}
		
		
	}
	
	/**
	 * Guardar las denomincaciones
	 * Es llamado si guardarNomenclatura se ejecuta correctamente
	 * @return
	 */
	private boolean guardarDenominaciones(){
		if(lstDenominacionesNomenclatura!=null && lstDenominacionesNomenclatura.size()>0){
			if(nomenclaturaExt.getCodNomenclatura()==null)
				nomenclaturaExt.setCodNomenclatura(nomenclaturaRespuesta.getCodNomenclatura());
			for(NomenclaturaDenominacion n:lstDenominacionesNomenclatura){
					if(blnNomenclaturaBase) 
						n.setCodNomenclaturaDenominacion(null);
					if(n.getCodNomenclaturaDenominacion()!=null)
						n.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
					n.setCodNomenclatura(BigDecimal.valueOf(nomenclaturaExt.getCodNomenclatura()));
					NomenclaturaDenominacion nomenclaturaDenominacionRespuesta = ComunicacionServiciosEnt.setNomenclaturaDenominacion(n);
					if(nomenclaturaDenominacionRespuesta.isError()){
						mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_GUARDAR_ERROR);
						return false;
					}
			}
		}
		return true;
	}
		
	/**
	 * Metodo que guarda una denominacion nueva o editada de forma parcial en la lista de denominaciones.
	 * El guardar se efectuta en guardarNomenclatura
	 */
	public void guardarDenominacionesArreglo() {
		denominacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		denominacion.setAudCodRol((int)this.getRolAuditoria().getId());
		denominacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		denominacion.setAudFechaActualizacion(new Date());
		denominacion.setFlgActivo((short) 1);
		
		if(validarLimiteSalarial()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ASIGNACION_SALARIAL_MAYOR);
		}
		
		if(lstDenominacionesNomenclatura==null) {
			lstDenominacionesNomenclatura = new ArrayList<NomenclaturaDenominacionExt>();
		}
			
		if(validarDuplicidadDenominacion())
			return;
		
		if(lstDenominacionesNomenclatura.contains(denominacion)){
			lstDenominacionesNomenclatura.remove(denominacion);
			lstDenominacionesNomenclatura.add(denominacion);
		}else{
			lstDenominacionesNomenclatura.add(0, denominacion);	
		}
		 RequestContext.getCurrentInstance().execute("PF('wdgtNuevaDenominacion').hide();");
	}
	
	public boolean validarLimiteSalarial() {
		try {
			Parametrica nivelJerarquico = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(denominacion.getCodNivelJerarquico()));
			Parametrica limiteSalarial = ComunicacionServiciosSis.getParametricaByname(TipoParametro.COD_LIMITES_SALARIALES.getValue(), nivelJerarquico.getValorParametro());
			Double limiteSalarialValor = Double.parseDouble(limiteSalarial.getValorParametro());
			Double asignacionSalarial = denominacion.getAsignacionSalarial().doubleValue();
			if (asignacionSalarial > limiteSalarialValor) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/*Metodo que valida si la nomenclatura ya cuenta con esta denominacion*/
	public boolean validarDuplicidadDenominacion() {
		if(lstDenominacionesNomenclatura!=null && lstDenominacionesNomenclatura.size()>0) {
			if(blDenominacionNueva) {
				for(NomenclaturaDenominacionExt den:lstDenominacionesNomenclatura) {
					if(denominacion.getCodNivelJerarquico().equals(den.getCodNivelJerarquico())
							&& denominacion.getCodDenominacion().equals(den.getCodDenominacion())
							&& denominacion.getCodCodigo().equals(den.getCodCodigo())
							&& denominacion.getCodGrado().equals(den.getCodGrado())) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_REPETIDA);
						return true;
					}
				}
			}else {
				if(!denominacion.getCodNivelJerarquico().equals(codNivelAnt)
					|| !denominacion.getCodDenominacion().equals(codDenAnt)
					|| !denominacion.getCodCodigo().equals(codCodAnt)
					|| !denominacion.getCodGrado().equals(codGradoAnt)) {
					lstDenominacionesNomenclatura.remove(denominacion);
					for(NomenclaturaDenominacionExt den : lstDenominacionesNomenclatura  ) {
						if(denominacion.getCodNivelJerarquico().equals(den.getCodNivelJerarquico())
								&& denominacion.getCodDenominacion().equals(den.getCodDenominacion())
								&& denominacion.getCodCodigo().equals(den.getCodCodigo())
								&& denominacion.getCodGrado().equals(den.getCodGrado())) {
							mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_REPETIDA);
							if(blDenominacionModificar) {
								denominacion.setCodNivelJerarquico(codNivelAnt);
								denominacion.setCodDenominacion(codDenAnt);
								denominacion.setCodCodigo(codCodAnt);
								denominacion.setCodGrado(codGradoAnt);
								lstDenominacionesNomenclatura.add(denominacion);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo que valida si la norma ya exite.
	 * 1. Si la norma ya existe, coge el codNorma y la almacena el objeto estructuraExt
	 * 2. Si no existe la norma, la crea.
	 */
	public void validarNormaEscalaSalarial() {
		if(normaEscalaSalarial.getNumeroNorma()!=null && normaEscalaSalarial.getFechaNorma()!=null && normaEscalaSalarial.getCodTipoNorma()!=null) {
			//Valida si la norma existe
			if(lstNormas != null && !lstNormas.isEmpty()) {
				incrementoSalarial.setCodNorma(lstNormas.get(0).getCodNorma().longValue());
			}else {
			//Crea la norma cuando este no existe
				Norma norma = new Norma();
				norma.setCodTipoNorma(normaEscalaSalarial.getCodTipoNorma());
				norma.setFechaNorma(normaEscalaSalarial.getFechaNorma());
				norma.setNumeroNorma(normaEscalaSalarial.getNumeroNorma());
				norma.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				norma.setAudCodRol((int) getUsuarioSesion().getCodRol());
				norma.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				norma.setAudFechaActualizacion(new Date());
				norma = ComunicacionServiciosEnt.setNorma(norma);
				if(!norma.isError()) {
					incrementoSalarial.setCodNorma(norma.getCodNorma());
				}else{
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, norma.getMensaje());
					return;
				}
			}
		}
	}
	
	/**
	 * Metodo que valida si la norma ya exite.
	 * 1. Si la norma ya existe, coge el codNorma y la almacena el objeto estructuraExt
	 * 2. Si no existe la norma, la crea.
	 */
	public void validarNormaNomenclatura() {
		if(normaNomenclatura.getNumeroNorma()!=null && normaNomenclatura.getFechaNorma()!=null && normaNomenclatura.getCodTipoNorma()!=null) {
			//Valida si la norma existe
			if(lstNormas != null && !lstNormas.isEmpty()) {
				nomenclaturaExt.setCodNorma(lstNormas.get(0).getCodNorma().longValue());
			}else {
			//Crea la norma cuando este no existe
				Norma norma = new Norma();
				norma.setCodTipoNorma(normaNomenclatura.getCodTipoNorma());
				norma.setFechaNorma(normaNomenclatura.getFechaNorma());
				norma.setNumeroNorma(normaNomenclatura.getNumeroNorma());
				norma.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				norma.setAudCodRol((int) getUsuarioSesion().getCodRol());
				norma.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
				norma.setAudFechaActualizacion(new Date());
				norma = ComunicacionServiciosEnt.setNorma(norma);
				if(!norma.isError()) {
					nomenclaturaExt.setCodNorma(norma.getCodNorma());
				}else{
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, norma.getMensaje());
					return;
				}
			}
		}
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
		 norm.setFechaNorma(normaNomenclatura.getFechaNorma());
		 if(normaNomenclatura.getCodTipoNorma()!=null)
			 norm.setCodTipoNorma(normaNomenclatura.getCodTipoNorma().intValue());
		 lstNormas =  ComunicacionServiciosEnt.getFiltroNormaLike(norm);
	     if(lstNormas == null && lstNormas.isEmpty()) {
	    	 return new ArrayList<>();
	     }
	     return lstNormas;
	 }
	 
	 /**
	  * Metodo que obtiene todas las denominaciones existentes
	  * para la entidad.
	  * @return
	  */
	 public List<SelectItem> getListaDenominaciones(){
			List<SelectItem> lstSelec= null;
			Denominacion buscadorDenominaciones = new Denominacion();
			List<Denominacion>lstDenomincaciones = ComunicacionServiciosEnt.getDenomincacionesFiltro(buscadorDenominaciones);
			if(lstDenomincaciones!=null && lstDenomincaciones.size()>0){
				lstSelec = new ArrayList<SelectItem>();
				for(Denominacion d:lstDenomincaciones){
					lstSelec.add(new SelectItem(d.getCodDenominacion(),d.getNombreDenominacion().toUpperCase()));
				}
			}
			return lstSelec;
			
		}
	 
	 /***
	  * Método que retorna los grados de empleos dependiendo de la clasificación orgánica
	  */
	 public List<SelectItem> getGradosClasificacionOrganica(){
		 //List<SelectItem> grados = new ArrayList<>();
		 if(denominacion.getCodDenominacion() != null &&(
				 	denominacion.getCodDenominacion().equals(Long.valueOf(TipoParametro.COD_DENOMINACION_ALCALDE.getValue())) 
				 || denominacion.getCodDenominacion().equals(Long.valueOf(TipoParametro.COD_DENOMINACION_GOBERNADOR.getValue())))) {
			 String codNoAplica = String.valueOf(TipoParametro.COD_PARAMETRO_NO_APLICA.getValue());
			 for(SelectItem grado : lstGradosDenominaciones) {
				 String codGrado = grado.getValue().toString();
				 if (codGrado.equals(codNoAplica)) {
					 List<SelectItem> returnList = new ArrayList<>();
					 returnList.add(grado);
					 return returnList;
				 }
			 }
			 //grados.add(new SelectItem(BigDecimal.valueOf(TipoParametro.COD_PARAMETRO_NO_APLICA.getValue()), "NO APLICA"));
		 } //else {
			 //WebUtils webUtils = new WebUtils();
			 //grados = webUtils.getGradosClasificacionOrganica(blnOrganoAutonomo);
		 //}
		 return lstGradosDenominaciones;
	 }
	 
	 /**
	  * Metodo que obtiene el numero de la norma
	  * @param estructuraExt
	  */
	 public void vclNorma(ValueChangeEvent  norma) {
		 Norma estr = (Norma) norma.getNewValue();
		 if (estr!= null && estr.getNumeroNorma() != null) {
        	normaNomenclatura.setNumeroNorma(estr.getNumeroNorma());
		 }
	 }
	 
	 /**
	  * Metodo que abre el formulario para crear una nueva denominacion
	  */
	 public void abrirFormularioCrearDenominacion() {
		 blDenominacionNueva=true;
		 blDenominacionModificar=false;
		 denominacion = new NomenclaturaDenominacionExt();
		 RequestContext.getCurrentInstance().execute("PF('wdgtNuevaDenominacion').show();");
	 }
	 
	 /**
	  * Metodo que abre el formulario de nomenclatura base
	  */
	 public void abrirFormularioNomenclaturaBase() {
		 nomenclaturaExt = new NomenclaturaExt();
		 nomenclaturaBaseSelect = new NomenclaturaExt();
		 lstNomenclaturasBase = new ArrayList<>();
		 RequestContext.getCurrentInstance().execute("PF('wdgtNomenclaturaBase').show();");
	 }
	 
	 /**
	  * Metodo que abre el detalle de la nomenclatura seleccionada (Denominaciones)
	  */
	 public void abrirFormularioDetalleNomenclaturaBase(NomenclaturaExt nomenclaturaBase) {
		 this.nomenclaturaBase = nomenclaturaBase;
		 cargarDenominacionesNomenclaturaBase();
		 RequestContext.getCurrentInstance().execute("PF('wdgtDetalleNomenclatura').show();");
	 }
	 
	 
	 /**
	  * Metodo que valida que se hayan diligenciado todos los datos obligatorios
	  * y abre el mensaje de confirmacion
	  */
	 public void abrirDialogoConfirmacionGuardadoDefinitivo() {
		 RequestContext.getCurrentInstance().execute("PF('dlgConfirmarGuardoDefinivo').show();");
	 }
	 
	/**
	 * Abre el formulario de incremento de escala Salarial
	 * Se llama de la accion de "Incremento y escala Salarial" de la tabla nomenclatura
	 * 
	 * @param nomenclaturasel
	 */
	public void verFormularioIncrementoEscala(NomenclaturaExt nomenclaturasel) {
		reiniciarCamposNorma();
		nomenclaturaExt = nomenclaturasel;
		cargarDenominacionesNomenclatura();
		buscarNormaNomenclatura();
		if(lstDenominacionesNomenclatura.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATUA_WARN_DENOMINACIONES_ASOCIADAS);
			reiniciarCampos();
			reiniciarCamposNorma();
			return;
		}else {
			normaEscalaSalarial = new Norma();
			reiniciarCampos();
			blnAccionIncrementoSalarial = true;
			blnIncrementoSalarial = true;
			blnVisibleFormularioNomenclatura = true;
			blnAccionConsultar = true;
		}
	}
	
	/**
	 * Metodo que valida las opciones de nomenclatura.
	 */
	public void validarAccionIncrementarEscala(){
		if(strOpcionIncremento==null){
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_SELECCIONAR_OP_INCREMENTO);
			return;
		}
		if( ("0".equals(strOpcionIncremento)||"1".equals(strOpcionIncremento)) && lbgValorIncremento==null){
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_SELECCIONAR_PORC_VALIDO);
			return;
		}
		if("0".equals(strOpcionIncremento) && lbgValorIncremento.compareTo(new BigDecimal(99))==1){
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_SELECCIONAR_PORC_VALIDO);
			return;
		}
		blnIncrementoSalarial = true;
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarIncrementoSalarial').show();");		
	}
	
	/**
	 * Metodo que realiza incremento salarial de la denominacion desde la tabla editable.
	 * @param denomincaciionsel
	 * @param strOpcion
	 */
	public void aplicarIncrementoDenominacionIndividual( NomenclaturaDenominacionExt denominacionSelect, String strOpcion){
		if(denominacionSelect !=null) {
			denominacion = denominacionSelect;
	
			if("0".equals(strOpcion) && denominacionSelect.getIncPorcentaje()!=null){
				if(denominacionSelect.getIncValor()!=null) {
					denominacionSelect.setIncPorcentaje(null);
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INFO_INCREMENTO);
					return;
				}
				if(denominacion.getAsignacionSalarial() != null) {
					denominacion.setNuevaAsignacionSalarial(
							denominacion.getAsignacionSalarial().add( 
							(denominacion.getAsignacionSalarial().multiply(denominacionSelect.getIncPorcentaje())).divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_CEILING)));
				}
				denominacion.setIncValor(null);
				
			}else if("1".equals(strOpcion) && denominacionSelect.getIncValor()!=null){
				if(denominacionSelect.getIncPorcentaje()!=null) {
					denominacionSelect.setIncValor(null);
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INFO_INCREMENTO);
					return;
				}
				if(denominacion.getAsignacionSalarial() != null) {
					denominacion.setNuevaAsignacionSalarial(denominacion.getAsignacionSalarial().add(denominacionSelect.getIncValor()).setScale(0, BigDecimal.ROUND_CEILING));
				}
				denominacion.setIncPorcentaje(null);
			}else if(denominacionSelect.getIncValor()==null && denominacionSelect.getIncPorcentaje()==null ) {
				denominacion.setNuevaAsignacionSalarial(denominacion.getAsignacionSalarial());
			}
		}else {
			denominacion.setNuevaAsignacionSalarial(denominacion.getAsignacionSalarial());
		}
		
	}
	
	/**
	 * Aplica la opción de incremente salarial a las denominaciones:
	 * 0:Porcentaje
	 * 1:Valor fijo
	 */
	public void realizarIncrementoEscalaSalarial(){
		if(lstDenominacionesNomenclatura!=null && lstDenominacionesNomenclatura.size()>0){
			if("0".equals(strOpcionIncremento)){
				for(NomenclaturaDenominacionExt n:lstDenominacionesNomenclatura){
					n.setIncPorcentaje(lbgValorIncremento);
					if(n.getAsignacionSalarial() != null) {
						n.setNuevaAsignacionSalarial(n.getAsignacionSalarial().add((n.getAsignacionSalarial().multiply(lbgValorIncremento)).divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING));
					}
					n.setIncValor(null);
				}
			}else if("1".equals(strOpcionIncremento)){
				for(NomenclaturaDenominacionExt n:lstDenominacionesNomenclatura){
					n.setIncValor(lbgValorIncremento);
					n.setNuevaAsignacionSalarial(n.getAsignacionSalarial()!= null ? n.getAsignacionSalarial().add(lbgValorIncremento.setScale(0, BigDecimal.ROUND_CEILING)): lbgValorIncremento);
					n.setIncPorcentaje(null);
				}	
			}	
		}
		
	}
	
	/**
	 * Metodo que permita cancela el incremento de una escala salarial
	 */
	public void cancelarEscalaSalarial() {
		reiniciarCampos();
	}
	
	/**
	 * Metodo que busca si existen normas segun la fecha de la norma
	 * y el tipo de la norma seleccionada en la opcion de Incremento de escala salarial y las retorna
	 * @param numeroNorma
	 * @return
	 */
	public List<Norma> completeMethodNormaEscalaSalarial(String  numeroNorma) {
		 Norma norm = new Norma();
		 norm.setNumeroNorma(numeroNorma);
		 norm.setFechaNorma(normaEscalaSalarial.getFechaNorma());
		 if(normaEscalaSalarial.getCodTipoNorma()!=null)
			 norm.setCodTipoNorma(normaEscalaSalarial.getCodTipoNorma().intValue());
		 lstNormas =  ComunicacionServiciosEnt.getFiltroNormaLike(norm);
	     if(lstNormas == null) {
	    	 return new ArrayList<>();
	     }
	     return lstNormas;
	 }	
	
	/**
	 * Metodo que limpia la vista una vez se seleccione la opcion eliminar
	 */
	public void limpiarDatosVistaOpcionEliminar(){
		blnVisibleFormularioNomenclatura = false;
		blnVisibleFormularioNomenclaturaBase = false;
		blnVisibleMensajeModificar = false;
		blnVisibleMensajeCrear = false;
		blnVisibleMensajeConsultar = false;
		blnAccionConsultar = false;
		blnAccionIncrementoSalarial = false;
		blnAccionPersonalizarGeneral = false;
	}
	
	
	/**
	  * Metodo que obtiene el numero de la norma de la escala salarial
	  * @param estructuraExt
	  */
	 public void vclNormaEscalaSalarial(ValueChangeEvent  normaExt) {
		Norma estr = (Norma) normaExt.getNewValue();
		    if (estr != null && estr.getNumeroNorma() != null) {
		       	normaEscalaSalarial.setNumeroNorma(estr.getNumeroNorma());
		}
		
	 }
	 
	 /**
	  * Metodo que busca las nomenclaturas base de la entidad
	  */
	 public void buscarNomenclaturaBase() {
		 nomenclaturaExt.setFlgActivo((short)1);
		 lstNomenclaturasBase = ComunicacionServiciosEnt.getNomenclaturaBase(nomenclaturaExt);
	 }
	 
	 /**
	  * Dependiendo de las acciones: "Aceptar" o "Cancelar" seleccionados realiza la accion correspondiente
	  */
	 public void abrirConfirmacionNB(String accion) {
		 strOpcionNomenclaturaBase =  accion;
		 
		 if("0".equals(accion)) {
			 this.setStrMensajeNomenclaturaBase(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_ACEPTAR_NOMENC_BASE, getLocale()));
		 }else {
			 this.setStrMensajeNomenclaturaBase(MessagesBundleConstants.getStringMessagesBundle(
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_CANCELAR_NOMENC_BASE, getLocale()));
		 }
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionNomenclaturaBase').show();");	
	 }
	 
	 /**
	  * Dependiendo de la accion seleccionada, cancela o llena la nueva nomenclatura con la Nbase seleccionada
	  */
	 public void redireccionarAccionNB() {
		 if("0".equals(strOpcionNomenclaturaBase)) {
			 aplicarNomenclaturaBase();
		 }else {
			 RequestContext.getCurrentInstance().execute("PF('wdgtNomenclaturaBase').hide();");	
		 }
	 }
	 
	 
	 /**
	  * Metodo que aplica la nomenclatura seleccionada como base para la creacion de una nueva
	  */
	 public void aplicarNomenclaturaBase() {
		 if(nomenclaturaBaseSelect != null && nomenclaturaBaseSelect.getCodNomenclatura() !=null ) {
			 nomenclaturaBaseSelect.setFlgDefinitivo((short)0);
			 nomenclaturaBaseSelect.setCodNomenclaturaAsociada(null);
			 nomenclaturaBaseSelect.setFlgGenerica((short)0);
			 nomenclaturaExt.setCodNomenclatura(nomenclaturaBaseSelect.getCodNomenclatura());
			 buscarNormaNomenclatura();
			 blnNomenclaturaBase = true;
			 cargarDenominacionesNomenclatura();
			 RequestContext.getCurrentInstance().execute("PF('wdgtNomenclaturaBase').hide();");	
		 }else {
			 mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_SELECCIONAR_NOMENCLATURA);
		 }
		 
	 }
	 
	 /**
	  * Metodo que abre panel de edicion de otras asignaciones
	  */
	 public void verOtraAsignacionDenominacion(NomenclaturaDenominacionExt denominacionSelec) {
		 if(blnOtraAsignacionSalarial) {
			 mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EN_PROCESO);
		 }else {
			 denominacion =  denominacionSelec;
			 blnAccionOtraAsignacion = true;
			 buscarOtrasAsignacionesSalariales();
		 }
	 }
	 
	 /**
	  * Metodo que retorna una lista de las otras asignaciones salariales que tiene una denominacion
	  */
	 public void buscarOtrasAsignacionesSalariales() {
		AsignacionSalarialExt buscadorAsignacion = new AsignacionSalarialExt();
		buscadorAsignacion.setFlgActivo((short) 1);
		buscadorAsignacion.setCodNomenclaturaDenominacion(denominacion.getCodNomenclaturaDenominacion());
		lstOtrasAsignacionesSalariales = ComunicacionServiciosEnt.getASignacionSalarialFitro(buscadorAsignacion); 
	 }
	 /**
	  * Metodo que abre el dialogo para la creacion de una nueva asignacion salarial
	  */
	 public void abrirDialogCrearOtraAsignacionSalarial() {
		 asignacionSalarialProcesar = new AsignacionSalarialExt();
		 lngValorOtraAsignacion = null;
		 RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacionSalarial').show();");	
	 }
	
	 /**
	  * Metodo que calcula la otra asignacion salarial de la nomenclatura
	  */
	 public void calcularOtraAsignacionSalarial() {
		 if(lngValorOtraAsignacion==null){
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_OPCION_INVALIDA);
			}else{
				if("0".equals(strOpcionOtraAsignacion)){
					asignacionSalarialProcesar.setValorAsignacion((denominacion.getAsignacionSalarial().multiply(new BigDecimal(lngValorOtraAsignacion))).divide(new BigDecimal(100))  .longValue());
					asignacionSalarialProcesar.setPorcentajeAsignacion(lngValorOtraAsignacion);
				}else if("1".equals(strOpcionOtraAsignacion)){
					asignacionSalarialProcesar.setValorAsignacion(lngValorOtraAsignacion);
				}
			}
	 }
	 
	/** Agrega otra asignacion a la lista OtrasAsignaciones*/
	public void agregarOtraAsignacionLista(){
		asignacionSalarialProcesar.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		asignacionSalarialProcesar.setAudCodRol((int)this.getRolAuditoria().getId());
		asignacionSalarialProcesar.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		asignacionSalarialProcesar.setAudFechaActualizacion(new Date());
		asignacionSalarialProcesar.setFlgActivo((short) 1);
		asignacionSalarialProcesar.setCodNomenclaturaDenominacion(denominacion.getCodNomenclaturaDenominacion());
		if(lstOtrasAsignacionesSalariales==null)
			lstOtrasAsignacionesSalariales = new ArrayList<AsignacionSalarialExt>();
		if(lstOtrasAsignacionesSalariales.contains(asignacionSalarialProcesar)){
			lstOtrasAsignacionesSalariales.remove(asignacionSalarialProcesar);
			if("0".equals(strOpcionOtraAsignacion))
				asignacionSalarialProcesar.setPorcentajeAsignacion(lngValorOtraAsignacion);
			else
				asignacionSalarialProcesar.setPorcentajeAsignacion(null);
			lstOtrasAsignacionesSalariales.add(asignacionSalarialProcesar);
		}else{
			blnOtraAsignacionSalarial = true;
			lstOtrasAsignacionesSalariales.add(0, asignacionSalarialProcesar);	
		}
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacionSalarial').hide();");
	}
	 
	/** Seleccionar una OtraAsignacion para su edición*/
	public void editarOtraAsignacionSalarial(AsignacionSalarialExt otraAsignacion){
		asignacionSalarialProcesar = otraAsignacion;
		blnAccionConsultarOtraAsignacion = false;
		lngValorOtraAsignacion = asignacionSalarialProcesar.getPorcentajeAsignacion()!=null?asignacionSalarialProcesar.getPorcentajeAsignacion():
			asignacionSalarialProcesar.getValorAsignacion();
		if(asignacionSalarialProcesar.getPorcentajeAsignacion()!=null)
			strOpcionOtraAsignacion="0";
		else
			strOpcionOtraAsignacion="1";		
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacionSalarial').show();");
	}
	
	/**
	 * Seleccionar una OtraAsignacion para su visualizacion*/
	public void consultarOtraAsignacionSalarial(AsignacionSalarialExt otraAsignacion){
		asignacionSalarialProcesar =  otraAsignacion;
		blnAccionConsultarOtraAsignacion = true;
		lngValorOtraAsignacion = asignacionSalarialProcesar.getPorcentajeAsignacion()!=null?asignacionSalarialProcesar.getPorcentajeAsignacion():
			asignacionSalarialProcesar.getValorAsignacion();
		if(asignacionSalarialProcesar.getPorcentajeAsignacion()!=null)
			strOpcionOtraAsignacion="0";
		else
			strOpcionOtraAsignacion="1";			
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacionSalarial').show();");
	}
	
	/**
	 * Solicita confirmacion para elimianr OtraAsignacion*/
	public void solicitaConfirmacionEliminarOtraAsignacion(AsignacionSalarialExt otraAsignacion){
		asignacionSalarialProcesar = otraAsignacion;
	RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccionEliminacionOtraAsignacion').show();");	
	}

	
	/**
	 * Elimina OtraAsignacionDenominacion una vez confirmada la acción*/
	public void eliminarOtraAsignacionSalarial(){
		if(asignacionSalarialProcesar.getCodAsignacionSalarial()==null){
			lstOtrasAsignacionesSalariales.remove(asignacionSalarialProcesar);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_ELIMINAR_EXITO);			
		}else{
			asignacionSalarialProcesar.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
			asignacionSalarialProcesar.setAudCodRol((int)this.getRolAuditoria().getId());
			asignacionSalarialProcesar.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			asignacionSalarialProcesar.setAudFechaActualizacion(new Date());
			asignacionSalarialProcesar.setFlgActivo((short) 0);
			AsignacionSalarial respuesta = ComunicacionServiciosEnt.setAsignacionSalarial(asignacionSalarialProcesar);
			if(respuesta.isError()){
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_ELIMINAR_ERROR);
				
			}else{
				lstOtrasAsignacionesSalariales.remove(asignacionSalarialProcesar);
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_ELIMINAR_EXITO);
			}
		}
	}
	
	 
	 /**Metodo que cancela la creacion de la asignacion */
	 public void cancelarCreacionOtraAsignacion() {
		 RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacionSalarial').hide();");
	 }
	
	 /**
	  * Solo se habilita el boton de otras asignaciones cuando la denominacion seleccionada
	  * ya esta guardada parcial o definitivamente.
	  * @param denominacion
	  * @return boolean
	  */
	 public boolean habilitarBotonOtrasAsignaciones(NomenclaturaDenominacion denominacion){
			if(denominacion.getCodNomenclaturaDenominacion()==null)
				return false;
			return true;
	 }
	 
	 /**
	  * Abre la tabla de denominaciones con las opciones de origen y destino
	  */
	 public void verFormularioEquivalencia(NomenclaturaExt nomenclaturasel) {
		reiniciarCamposNorma();
		nomenclaturaExt = nomenclaturasel;
		cargarDenominacionesNomenclatura();
		buscarNormaNomenclatura();
		if(lstDenominacionesNomenclatura.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATUA_WARN_DENOMINACIONES_ASOCIADAS);
			reiniciarCampos();
			reiniciarCamposNorma();
			return;
		}else {
			reiniciarCampos();
			blnAccionConsultar = true;
			blnAccionEquivalencia = true;
			blnVisibleFormularioNomenclatura = true;
		}
	 }
	 
	 /**
	  * valida:
	  * 1. Que se tenga un destino
	  * 2. Que el destino sea diferente al de origen
	  */
	 public void validarDenominacionesEquivalencia(){
			boolean existeorigen=false;
			/*valida que exista destino*/
			if(nomenclaturaDenominacionDestino == null){
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_SEL_DESTINO);
				return;
			}
			
			/*valida que exista como mínimo un origen y sean diferentes al destino*/
			for(NomenclaturaDenominacionExt dennomenclatura:lstDenominacionesNomenclatura){
				if(dennomenclatura.isLbOrigenEquivalencia()){
					existeorigen = true;
					/*Origen diferente al destino*/
					if(dennomenclatura.getCodDenominacion().equals(nomenclaturaDenominacionDestino.getCodDenominacion())
							&& dennomenclatura.getCodGrado().equals(nomenclaturaDenominacionDestino.getCodGrado())
							&& dennomenclatura.getCodNivelJerarquico().equals(nomenclaturaDenominacionDestino.getCodNivelJerarquico()) ){
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
								MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_DESTINO_IGUAL_ORIGEN);
						return;
					}
					/*salario*/
					if(dennomenclatura.getAsignacionSalarial().compareTo(nomenclaturaDenominacionDestino.getAsignacionSalarial())==1){
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
								MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_SALARIO_INVALIDO);					
						return;
					}
				}
			}
			if(!existeorigen){
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_SEL_ORIGEN);
				return;
			}
			
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarEquivalencia').show();");			

	}

	 /** Metodo que inhabilita las denominaciones origen seleccionadas para el proceso de equivalencias*/
	public boolean fusionarOrigenesADestino(String origenes, long destino, long lngValorEntidad) {
		boolean error = false;
		NomenclaturaExt objNomenclatura = new NomenclaturaExt();
		if(origenes != null)
			objNomenclatura.setOrigenes(origenes.substring(0, origenes.length()-1));
		objNomenclatura.setDenDestino(destino);
		objNomenclatura.setCodEntidad(new BigDecimal(lngValorEntidad));
		NomenclaturaExt res = ComunicacionServiciosEnt.setNomenclaturaEquivalencia(objNomenclatura);
		if (res.isError())
			error = true;
		return error;
	}
	 
	 /**
	  * Metodo que valida:
	  * 1. Si las denominaciones de origen no tienen asociadas plantas ni vinculaciones----->las inhabilita.
	  * 2. Si las denominaciones de origen tiene asociadas planta:
	  * 	2.1. inhabilita los cargos que tienen esas denominaciones
	  *     2.2. inhabilita las denominaciones origen, dejando solo activa la de destino
	  * 3. Si las denominaciones de origen tienen asociadas plantas y vinculaciones activas
	  * 	3.1. Pasa las vinculaciones al cargo destino
	  * 	3.2. inhabilita los cargos que tienen esas denominaciones
	  *     3.3. inhabilita las denominaciones origen, dejando solo activa la de destino
	  * 
	  */
	public void equivalenciasFusionar() {
		boolean error= false;
		String origenes = "";
		long destino = 0;
		for (NomenclaturaDenominacionExt dennomenclatura : lstDenominacionesNomenclatura) {
			if (dennomenclatura.isLbOrigenEquivalencia()) 
				origenes = dennomenclatura.getCodNomenclaturaDenominacion() + ",";
			else 
				destino = dennomenclatura.getCodNomenclaturaDenominacion();
		}
		error = fusionarOrigenesADestino(origenes,destino, lngValorEntidad);
		if (!error) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_EXITO);
			reiniciarCampos();
			nomenclaturaDenominacionDestino = new NomenclaturaDenominacionExt();
			cargarDenominacionesNomenclatura();
			
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_ERROR);
		}
	}
	 
	 
	 /**
	  * Botòn "Cancelar"  no presenta mensaje de confirmacion cuando se accede desde la opcion "Consultar" 
	  */
	 public void abrirDialogoCancelarNomenclatura() {
		 RequestContext.getCurrentInstance().execute("PF('dlgConfirmarCancelacionNomenclatura').show();");
		 return;
	 }
	 
	 public void handleOpcionIncrementoSelection(AjaxBehaviorEvent  e) {
	        
	 }
	 
	 public void handleOpcionOtraAsignacion(AjaxBehaviorEvent  e) {
	        
	 }
	 
	/**
	 * @return the lngValorEntidad
	 */
	public Long getLngValorEntidad() {
		return lngValorEntidad;
	}

	/**
	 * @param lngValorEntidad the lngValorEntidad to set
	 */
	public void setLngValorEntidad(Long lngValorEntidad) {
		this.lngValorEntidad = lngValorEntidad;
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
	 * @return the blnVisibleFormularioNomenclatura
	 */
	public Boolean getBlnVisibleFormularioNomenclatura() {
		return blnVisibleFormularioNomenclatura;
	}

	/**
	 * @param blnVisibleFormularioNomenclatura the blnVisibleFormularioNomenclatura to set
	 */
	public void setBlnVisibleFormularioNomenclatura(Boolean blnVisibleFormularioNomenclatura) {
		this.blnVisibleFormularioNomenclatura = blnVisibleFormularioNomenclatura;
	}
	
	/**
	 * @return the blnVisibleMensajeCrear
	 */
	public Boolean getBlnVisibleMensajeCrear() {
		return blnVisibleMensajeCrear;
	}

	/**
	 * @param blnVisibleMensajeCrear the blnVisibleMensajeCrear to set
	 */
	public void setBlnVisibleMensajeCrear(Boolean blnVisibleMensajeCrear) {
		this.blnVisibleMensajeCrear = blnVisibleMensajeCrear;
	}

	/**
	 * @return the blnVisibleMensajeModificar
	 */
	public Boolean getBlnVisibleMensajeModificar() {
		return blnVisibleMensajeModificar;
	}


	/**
	 * @param blnVisibleMensajeModificar the blnVisibleMensajeModificar to set
	 */
	public void setBlnVisibleMensajeModificar(Boolean blnVisibleMensajeModificar) {
		this.blnVisibleMensajeModificar = blnVisibleMensajeModificar;
	}

	/**
	 * @return the nomenclaturaExt
	 */
	public NomenclaturaExt getNomenclaturaExt() {
		return nomenclaturaExt;
	}

	/**
	 * @param nomenclaturaExt the nomenclaturaExt to set
	 */
	public void setNomenclaturaExt(NomenclaturaExt nomenclaturaExt) {
		this.nomenclaturaExt = nomenclaturaExt;
	}

	/**
	 * @return the blnAccionConsultar
	 */
	public Boolean getBlnAccionConsultar() {
		return blnAccionConsultar;
	}

	/**
	 * @param blnAccionConsultar the blnAccionConsultar to set
	 */
	public void setBlnAccionConsultar(Boolean blnAccionConsultar) {
		this.blnAccionConsultar = blnAccionConsultar;
	}
	
	/**
	 * @return the blnVisibleMensajeConsultar
	 */
	public Boolean getBlnVisibleMensajeConsultar() {
		return blnVisibleMensajeConsultar;
	}

	/**
	 * @param blnVisibleMensajeConsultar the blnVisibleMensajeConsultar to set
	 */
	public void setBlnVisibleMensajeConsultar(Boolean blnVisibleMensajeConsultar) {
		this.blnVisibleMensajeConsultar = blnVisibleMensajeConsultar;
	}
	
	/**
	 * @return the lstDenominacionesNomenclatura
	 */
	public List<NomenclaturaDenominacionExt> getLstDenominacionesNomenclatura() {
		return lstDenominacionesNomenclatura;
	}

	/**
	 * @param lstDenominacionesNomenclatura the lstDenominacionesNomenclatura to set
	 */
	public void setLstDenominacionesNomenclatura(List<NomenclaturaDenominacionExt> lstDenominacionesNomenclatura) {
		this.lstDenominacionesNomenclatura = lstDenominacionesNomenclatura;
	}
	
	/**
	 * @return the denominacion
	 */
	public NomenclaturaDenominacionExt getDenominacion() {
		return denominacion;
	}

	/**
	 * @param denominacion the denominacion to set
	 */
	public void setDenominacion(NomenclaturaDenominacionExt denominacion) {
		this.denominacion = denominacion;
	}

	/**
	 * @return the blnAccionConsultarDenominacion
	 */
	public Boolean getBlnAccionConsultarDenominacion() {
		return blnAccionConsultarDenominacion;
	}

	/**
	 * @param blnAccionConsultarDenominacion the blnAccionConsultarDenominacion to set
	 */
	public void setBlnAccionConsultarDenominacion(Boolean blnAccionConsultarDenominacion) {
		this.blnAccionConsultarDenominacion = blnAccionConsultarDenominacion;
	}
	
	/**
	 * @return the blnVisibleFormularioNomenclaturaBase
	 */
	public Boolean getBlnVisibleFormularioNomenclaturaBase() {
		return blnVisibleFormularioNomenclaturaBase;
	}

	/**
	 * @param blnVisibleFormularioNomenclaturaBase the blnVisibleFormularioNomenclaturaBase to set
	 */
	public void setBlnVisibleFormularioNomenclaturaBase(Boolean blnVisibleFormularioNomenclaturaBase) {
		this.blnVisibleFormularioNomenclaturaBase = blnVisibleFormularioNomenclaturaBase;
	}
	
	/**
	 * @return the lstNomenclaturasBase
	 */
	public List<NomenclaturaExt> getLstNomenclaturasBase() {
		return lstNomenclaturasBase;
	}

	/**
	 * @param lstNomenclaturasBase the lstNomenclaturasBase to set
	 */
	public void setLstNomenclaturasBase(List<NomenclaturaExt> lstNomenclaturasBase) {
		this.lstNomenclaturasBase = lstNomenclaturasBase;
	}
	/**
	 * @return the blnAccionIncrementoSalarial
	 */
	public Boolean getBlnAccionIncrementoSalarial() {
		return blnAccionIncrementoSalarial;
	}

	/**
	 * @param blnAccionIncrementoSalarial the blnAccionIncrementoSalarial to set
	 */
	public void setBlnAccionIncrementoSalarial(Boolean blnAccionIncrementoSalarial) {
		this.blnAccionIncrementoSalarial = blnAccionIncrementoSalarial;
	}
	
	/**
	 * @return the strOpcionIncremento
	 */
	public String getStrOpcionIncremento() {
		return strOpcionIncremento;
	}

	/**
	 * @param strOpcionIncremento the strOpcionIncremento to set
	 */
	public void setStrOpcionIncremento(String strOpcionIncremento) {
		this.strOpcionIncremento = strOpcionIncremento;
	}
	
	/**
	 * @return the normaEscalaSalarial
	 */
	public Norma getNormaEscalaSalarial() {
		return normaEscalaSalarial;
	}

	/**
	 * @param normaEscalaSalarial the normaEscalaSalarial to set
	 */
	public void setNormaEscalaSalarial(Norma normaEscalaSalarial) {
		this.normaEscalaSalarial = normaEscalaSalarial;
	}

	/**
	 * @return the normaEscalaSalarialSeleccionada
	 */
	public Norma getNormaEscalaSalarialSeleccionada() {
		return normaEscalaSalarialSeleccionada;
	}

	/**
	 * @param normaEscalaSalarialSeleccionada the normaEscalaSalarialSeleccionada to set
	 */
	public void setNormaEscalaSalarialSeleccionada(Norma normaEscalaSalarialSeleccionada) {
		this.normaEscalaSalarialSeleccionada = normaEscalaSalarialSeleccionada;
	}

	/**
	 * @return the lbgValorIncremento
	 */
	public BigDecimal getLbgValorIncremento() {
		return lbgValorIncremento;
	}

	/**
	 * @param lbgValorIncremento the lbgValorIncremento to set
	 */
	public void setLbgValorIncremento(BigDecimal lbgValorIncremento) {
		this.lbgValorIncremento = lbgValorIncremento;
	}
	
	/**
	 * @return the blnIncrementoSalarial
	 */
	public Boolean getBlnIncrementoSalarial() {
		return blnIncrementoSalarial;
	}

	/**
	 * @param blnIncrementoSalarial the blnIncrementoSalarial to set
	 */
	public void setBlnIncrementoSalarial(Boolean blnIncrementoSalarial) {
		this.blnIncrementoSalarial = blnIncrementoSalarial;
	}
	
	/**
	 * @return the incrementoSalarial
	 */
	public IncrementoSalarial getIncrementoSalarial() {
		return incrementoSalarial;
	}

	/**
	 * @param incrementoSalarial the incrementoSalarial to set
	 */
	public void setIncrementoSalarial(IncrementoSalarial incrementoSalarial) {
		this.incrementoSalarial = incrementoSalarial;
	}
	
	/**
	 * @return the strMensajeNomenclaturaBase
	 */
	public String getStrMensajeNomenclaturaBase() {
		return strMensajeNomenclaturaBase;
	}

	/**
	 * @param strMensajeNomenclaturaBase the strMensajeNomenclaturaBase to set
	 */
	public void setStrMensajeNomenclaturaBase(String strMensajeNomenclaturaBase) {
		this.strMensajeNomenclaturaBase = strMensajeNomenclaturaBase;
	}

	/**
	 * @return the nomenclaturaRespuesta
	 */
	public Nomenclatura getNomenclaturaRespuesta() {
		return nomenclaturaRespuesta;
	}

	/**
	 * @param nomenclaturaRespuesta the nomenclaturaRespuesta to set
	 */
	public void setNomenclaturaRespuesta(Nomenclatura nomenclaturaRespuesta) {
		this.nomenclaturaRespuesta = nomenclaturaRespuesta;
	}
	
	/**
	 * @return the strOpcionNomenclaturaBase
	 */
	public String getStrOpcionNomenclaturaBase() {
		return strOpcionNomenclaturaBase;
	}

	/**
	 * @param strOpcionNomenclaturaBase the strOpcionNomenclaturaBase to set
	 */
	public void setStrOpcionNomenclaturaBase(String strOpcionNomenclaturaBase) {
		this.strOpcionNomenclaturaBase = strOpcionNomenclaturaBase;
	}
	
	/**
	 * @return the nomenclaturaBaseSelect
	 */
	public NomenclaturaExt getNomenclaturaBaseSelect() {
		return nomenclaturaBaseSelect;
	}

	/**
	 * @param nomenclaturaBaseSelect the nomenclaturaBaseSelect to set
	 */
	public void setNomenclaturaBaseSelect(NomenclaturaExt nomenclaturaBaseSelect) {
		this.nomenclaturaBaseSelect = nomenclaturaBaseSelect;
	}

	/**
	 * @return the listaPlantas
	 */
	public List<EntidadPlantaExt> getListaPlantas() {
		return listaPlantas;
	}

	/**
	 * @param listaPlantas the listaPlantas to set
	 */
	public void setListaPlantas(List<EntidadPlantaExt> listaPlantas) {
		this.listaPlantas = listaPlantas;
	}

	/**
	 * @return the blnNomenclaturaBase
	 */
	public Boolean getBlnNomenclaturaBase() {
		return blnNomenclaturaBase;
	}

	/**
	 * @param blnNomenclaturaBase the blnNomenclaturaBase to set
	 */
	public void setBlnNomenclaturaBase(Boolean blnNomenclaturaBase) {
		this.blnNomenclaturaBase = blnNomenclaturaBase;
	}
	
	/**
	 * @return the blnAccionOtraAsignacion
	 */
	public Boolean getBlnAccionOtraAsignacion() {
		return blnAccionOtraAsignacion;
	}

	/**
	 * @param blnAccionOtraAsignacion the blnAccionOtraAsignacion to set
	 */
	public void setBlnAccionOtraAsignacion(Boolean blnAccionOtraAsignacion) {
		this.blnAccionOtraAsignacion = blnAccionOtraAsignacion;
	}

	/**
	 * @return the lstOtrasAsignacionesSalariales
	 */
	public List<AsignacionSalarialExt> getLstOtrasAsignacionesSalariales() {
		return lstOtrasAsignacionesSalariales;
	}

	/**
	 * @param lstOtrasAsignacionesSalariales the lstOtrasAsignacionesSalariales to set
	 */
	public void setLstOtrasAsignacionesSalariales(List<AsignacionSalarialExt> lstOtrasAsignacionesSalariales) {
		this.lstOtrasAsignacionesSalariales = lstOtrasAsignacionesSalariales;
	}

	/**
	 * @return the asignacionSalarialProcesar
	 */
	public AsignacionSalarialExt getAsignacionSalarialProcesar() {
		return asignacionSalarialProcesar;
	}

	/**
	 * @param asignacionSalarialProcesar the asignacionSalarialProcesar to set
	 */
	public void setAsignacionSalarialProcesar(AsignacionSalarialExt asignacionSalarialProcesar) {
		this.asignacionSalarialProcesar = asignacionSalarialProcesar;
	}
	
	/**
	 * @return the strOpcionOtraAsignacion
	 */
	public String getStrOpcionOtraAsignacion() {
		return strOpcionOtraAsignacion;
	}

	/**
	 * @param strOpcionOtraAsignacion the strOpcionOtraAsignacion to set
	 */
	public void setStrOpcionOtraAsignacion(String strOpcionOtraAsignacion) {
		this.strOpcionOtraAsignacion = strOpcionOtraAsignacion;
	}

	/**
	 * @return the lngValorOtraAsignacion
	 */
	public Long getLngValorOtraAsignacion() {
		return lngValorOtraAsignacion;
	}

	/**
	 * @param lngValorOtraAsignacion the lngValorOtraAsignacion to set
	 */
	public void setLngValorOtraAsignacion(Long lngValorOtraAsignacion) {
		this.lngValorOtraAsignacion = lngValorOtraAsignacion;
	}

	
	
	/**
	 * @return the blnAccionConsultarOtraAsignacion
	 */
	public Boolean getBlnAccionConsultarOtraAsignacion() {
		return blnAccionConsultarOtraAsignacion;
	}

	/**
	 * @param blnAccionConsultarOtraAsignacion the blnAccionConsultarOtraAsignacion to set
	 */
	public void setBlnAccionConsultarOtraAsignacion(Boolean blnAccionConsultarOtraAsignacion) {
		this.blnAccionConsultarOtraAsignacion = blnAccionConsultarOtraAsignacion;
	}

	/**
	 * @return the blnAccionEquivalencia
	 */
	public Boolean getBlnAccionEquivalencia() {
		return blnAccionEquivalencia;
	}

	/**
	 * @param blnAccionEquivalencia the blnAccionEquivalencia to set
	 */
	public void setBlnAccionEquivalencia(Boolean blnAccionEquivalencia) {
		this.blnAccionEquivalencia = blnAccionEquivalencia;
	}

	/**
	 * @return the nomenclaturaDenominacionDestino
	 */
	public NomenclaturaDenominacionExt getNomenclaturaDenominacionDestino() {
		return nomenclaturaDenominacionDestino;
	}

	/**
	 * @param nomenclaturaDenominacionDestino the nomenclaturaDenominacionDestino to set
	 */
	public void setNomenclaturaDenominacionDestino(NomenclaturaDenominacionExt nomenclaturaDenominacionDestino) {
		this.nomenclaturaDenominacionDestino = nomenclaturaDenominacionDestino;
	}
	
	/**
	 * @return the blnPermisoNomenclatura
	 */
	public Boolean getBlnPermisoNomenclatura() {
		return blnPermisoNomenclatura;
	}

	/**
	 * @param blnPermisoNomenclatura the blnPermisoNomenclatura to set
	 */
	public void setBlnPermisoNomenclatura(Boolean blnPermisoNomenclatura) {
		this.blnPermisoNomenclatura = blnPermisoNomenclatura;
	}
	
	/**
	 * @return the lstDenominacionesNomenclaturaBase
	 */
	public final List<NomenclaturaDenominacionExt> getLstDenominacionesNomenclaturaBase() {
		return lstDenominacionesNomenclaturaBase;
	}

	/**
	 * @param lstDenominacionesNomenclaturaBase the lstDenominacionesNomenclaturaBase to set
	 */
	public final void setLstDenominacionesNomenclaturaBase(
			List<NomenclaturaDenominacionExt> lstDenominacionesNomenclaturaBase) {
		this.lstDenominacionesNomenclaturaBase = lstDenominacionesNomenclaturaBase;
	}
	
	/**
	 * @return the nomenclaturaBase
	 */
	public final NomenclaturaExt getNomenclaturaBase() {
		return nomenclaturaBase;
	}

	/**
	 * @param nomenclaturaBase the nomenclaturaBase to set
	 */
	public final void setNomenclaturaBase(NomenclaturaExt nomenclaturaBase) {
		this.nomenclaturaBase = nomenclaturaBase;
	}

	/**
	 * @return the normaNomenclatura
	 */
	public Norma getNormaNomenclatura() {
		return normaNomenclatura;
	}

	/**
	 * @param normaNomenclatura the normaNomenclatura to set
	 */
	public void setNormaNomenclatura(Norma normaNomenclatura) {
		this.normaNomenclatura = normaNomenclatura;
	}

	/**
	 * @return the normaNomenclaturaSeleccionada
	 */
	public Norma getNormaNomenclaturaSeleccionada() {
		return normaNomenclaturaSeleccionada;
	}

	/**
	 * @param normaNomenclaturaSeleccionada the normaNomenclaturaSeleccionada to set
	 */
	public void setNormaNomenclaturaSeleccionada(Norma normaNomenclaturaSeleccionada) {
		this.normaNomenclaturaSeleccionada = normaNomenclaturaSeleccionada;
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
	 * @return the lstVinculacionDenominacion
	 */
	public List<VinculacionExt> getLstVinculacionDenominacion() {
		return lstVinculacionDenominacion;
	}

	/**
	 * @param lstVinculacionDenominacion the lstVinculacionDenominacion to set
	 */
	public void setLstVinculacionDenominacion(List<VinculacionExt> lstVinculacionDenominacion) {
		this.lstVinculacionDenominacion = lstVinculacionDenominacion;
	}
	
	/**
	 * @return the lstPlantaDetalleDenominacion
	 */
	public List<EntidadPlantaDetalleExt> getLstPlantaDetalleDenominacion() {
		return lstPlantaDetalleDenominacion;
	}

	/**
	 * @param lstPlantaDetalleDenominacion the lstPlantaDetalleDenominacion to set
	 */
	public void setLstPlantaDetalleDenominacion(List<EntidadPlantaDetalleExt> lstPlantaDetalleDenominacion) {
		this.lstPlantaDetalleDenominacion = lstPlantaDetalleDenominacion;
	}


	/**
	 * @return the blnPermisoPersonalizacion
	 */
	public Boolean getBlnPermisoPersonalizacion() {
		return blnPermisoPersonalizacion;
	}

	/**
	 * @param blnPermisoPersonalizacion the blnPermisoPersonalizacion to set
	 */
	public void setBlnPermisoPersonalizacion(Boolean blnPermisoPersonalizacion) {
		this.blnPermisoPersonalizacion = blnPermisoPersonalizacion;
	}

	/**
	 * @return the blnOtraAsignacionSalarial
	 */
	public Boolean getBlnOtraAsignacionSalarial() {
		return blnOtraAsignacionSalarial;
	}

	/**
	 * @param blnOtraAsignacionSalarial the blnOtraAsignacionSalarial to set
	 */
	public void setBlnOtraAsignacionSalarial(Boolean blnOtraAsignacionSalarial) {
		this.blnOtraAsignacionSalarial = blnOtraAsignacionSalarial;
	}	

	/**
	 * @return the blnAccionPersonalizarGeneral
	 */
	public Boolean getBlnAccionPersonalizarGeneral() {
		return blnAccionPersonalizarGeneral;
	}

	/**
	 * @param blnAccionPersonalizarGeneral the blnAccionPersonalizarGeneral to set
	 */
	public void setBlnAccionPersonalizarGeneral(Boolean blnAccionPersonalizarGeneral) {
		this.blnAccionPersonalizarGeneral = blnAccionPersonalizarGeneral;
	}
	
	/**
	 * @return the blnPersonalizarTodo
	 */
	public Boolean getBlnPersonalizarTodo() {
		return blnPersonalizarTodo;
	}

	/**
	 * @param blnPersonalizarTodo the blnPersonalizarTodo to set
	 */
	public void setBlnPersonalizarTodo(Boolean blnPersonalizarTodo) {
		this.blnPersonalizarTodo = blnPersonalizarTodo;
	}
	
	/**
	 * @return the codNivelAnt
	 */
	public Long getCodNivelAnt() {
		return codNivelAnt;
	}

	/**
	 * @param codNivelAnt the codNivelAnt to set
	 */
	public void setCodNivelAnt(Long codNivelAnt) {
		this.codNivelAnt = codNivelAnt;
	}

	/**
	 * @return the codDenAnt
	 */
	public Long getCodDenAnt() {
		return codDenAnt;
	}

	/**
	 * @param codDenAnt the codDenAnt to set
	 */
	public void setCodDenAnt(Long codDenAnt) {
		this.codDenAnt = codDenAnt;
	}

	/**
	 * @return the codCodAnt
	 */
	public String getCodCodAnt() {
		return codCodAnt;
	}

	/**
	 * @param codCodAnt the codCodAnt to set
	 */
	public void setCodCodAnt(String codCodAnt) {
		this.codCodAnt = codCodAnt;
	}

	/**
	 * @return the codGradoAnt
	 */
	public Long getCodGradoAnt() {
		return codGradoAnt;
	}

	/**
	 * @param codGradoAnt the codGradoAnt to set
	 */
	public void setCodGradoAnt(Long codGradoAnt) {
		this.codGradoAnt = codGradoAnt;
	}

	/**
	 * @return the txtJustificacion
	 */
	public String getTxtJustificacion() {
		return txtJustificacion;
	}

	/**
	 * @param txtJustificacion the txtJustificacion to set
	 */
	public void setTxtJustificacion(String txtJustificacion) {
		this.txtJustificacion = txtJustificacion;
	}
	
	public Boolean getBlnOrganoAutonomo() {
		return blnOrganoAutonomo;
	}

	public void setBlnOrganoAutonomo(Boolean blnOrganoAutonomo) {
		this.blnOrganoAutonomo = blnOrganoAutonomo;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
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
}