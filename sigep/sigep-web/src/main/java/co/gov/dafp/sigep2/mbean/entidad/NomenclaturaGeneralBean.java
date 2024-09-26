package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
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
import org.primefaces.event.SelectEvent;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
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
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.AsignacionSalarialExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;

@Named
@ViewScoped
@ManagedBean
public class NomenclaturaGeneralBean  extends BaseBean implements Serializable{

	/**
	 * @Pablo Quintana
	 * 11/12/2018
	 * Clase controladora de todo el proceso de Nomenclatura general
	 */
	private static final long serialVersionUID = 1L;
	private boolean lbVerFrmNomenclatura, lbVerFrmDenominacion, lvVerAsociarEntidades, lbCrearNorma, lbEliminarNomenclatura, lbEliminarDenominacion, 
	                lbCancelar,lbSalir,
	                lbFormularioModificado, lbEsConsultaNomenclatura, lbEsConsultaDenomincacion,
	                lbEsIncrementoSalarial,		lbAplicarIncrementoSalarial,
	        		lbGuardarIncrementoSalarial,lbCancelarIncrementoSalarial,
	        		lbAccionAsociarEntidades, lbAccionCancelarAsociarEntidades;
	private boolean lbConsultaDenominacion = false;
	private NomenclaturaExt buscadorNomenclatura;
	private NomenclaturaDenominacionExt buscardorNomenclaturaDenominaciones;
	private Denominacion buscadorDenominaciones;
	private Nomenclatura nomenclaturaProcesar;
	private List<NomenclaturaExt> lstNomenclaturas;
	private NomenclaturaDenominacionExt nomenclaturaDenominacionProcesar;
	private List<NomenclaturaDenominacionExt> lstDenominacionesNomenclatura;
	Nomenclatura nomenclaturaRespuesta;
	NomenclaturaDenominacionExt nomenclaturaDenominacionRespuesta;
	private List<NomenclaturaExt> listaPlantasNomGeneral; //Lista que contiene las plantas que estan asociadas a la nomenclatura a eliminar
	/* campos de auditoria */
	Integer codAudCodRol,codAudusuario, codAudAccionInsert = 3, codAudAccionUpdate = 2;
	/*Variables de sesión*/
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
	/*Acciones*/
	private String strMsgAcciones, strMsgAccionConfirmar, strMensajeAccion;
	/*Norma*/
	private Norma normaSeleccionada;
	private Norma normaNomenclatura;
	private List<Norma> lstNormas;
	/*Incremento*/
	private String strOpcionIncremento="0";
	BigDecimal lbgValorIncremento;
	private Norma normaSeleccionadaIncremento, normaIncremento;
	IncrementoSalarial incrementoSalarial;
	/*Asociar nomenclatura a entidades*/
	private Entidad entFiltro;
	private List<Entidad> lstEntidadesDisponibles;
	private List<Entidad> lstEntidadesSeleccionadas;
	private boolean lbVerFrmAsociar;
	/*otros*/
	List<SelectItem> lstSIDenominaciones= null;
	/*Otras asignaciones Salariales*/
	List<AsignacionSalarialExt> lstOtrasAsignaciones;
	AsignacionSalarialExt asignacionSalarialProcesar;
	private boolean lbEsEditarOtraAsignacion, lbEsConsultarOtraAsignacion, lbAccionEliminarOtraAsignacion;
	private String strOpcionesAsignacion;
	private Long llValorAsignacion;
	/*Modificando*/
	private boolean lbEsModificando, lbEsmodificandoDenominacion, lbEsModificandoOtrasAsignaciones;
	/*Equivalencias*/
	private boolean lbEsEquivalencias,lbCancelarEquivalencias, lbAccionEquivalenciasFusionar;
	private NomenclaturaDenominacionExt nomenclaturaDenominacionDestino;
	private List<EntidadPlantaDetalleExt> lstPlantaDetalleDenominacion; // Lista que trae las plantas y vinculaciones a las cuales esta asociada una denominacion

	private Boolean lblVerDenominaciones;
	private Boolean lblverBotones;
	private boolean blDenominacionNueva, blDenominacionModificar;
	
	/*Variables para validacion de denominacionExistente*/
	private Long 	codNivelAnt;
	private Long 	codDenAnt;
	private String 	codCodAnt;
	private Long 	codGradoAnt;
	
	private Boolean flgIsGnrlTerritorial;
	
	
	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
	}

	@PostConstruct
	public void init()  {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		if(!seguridadNomenclaturaGeneral()){
			return;
		}
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		inicializarVariables();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ENTIDADES);		
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
	/***
	 *   Inicia Variables 
	 */
	private void inicializarVariables(){
		entFiltro=new Entidad();
		lstEntidadesSeleccionadas = new ArrayList<>();
		normaNomenclatura = new Norma();
		nomenclaturaProcesar = new Nomenclatura();
		nomenclaturaDenominacionProcesar  = new NomenclaturaDenominacionExt();
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		codAudCodRol = (int) this.getRolAuditoria().getId();
		codAudusuario = (int) usuarioSesion.getId();
		lbVerFrmNomenclatura = false;
		lblVerDenominaciones = false;
		lblverBotones = false;
		blDenominacionNueva = false;
		blDenominacionModificar=false;
		getListaDenominaciones();
		inicializarFormulario();
	}
	/**
	 * Controla la seguridad por roles
	 * @return
	 */
	private boolean seguridadNomenclaturaGeneral(){
		return true;
	}

	public void gestionarEquivalencias(){
		
	}
	/**
	 * Agrega una nomenclatura nueva
	 */
	public void agregarNomenclatura(){
		if(!esNomenclaturaEnProceso()){
			nomenclaturaDenominacionProcesar = new NomenclaturaDenominacionExt();
			nomenclaturaProcesar = new Nomenclatura();
			nomenclaturaProcesar.setAudAccion(codAudAccionInsert);
			nomenclaturaProcesar.setAudCodRol(codAudCodRol);
			nomenclaturaProcesar.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
			nomenclaturaProcesar.setAudFechaActualizacion(new Date());
			nomenclaturaProcesar.setFlgActivo((short) 1);
			nomenclaturaProcesar.setFlgGenerica((short) 1);
			lbVerFrmNomenclatura = true;
			lblVerDenominaciones = true;
			lblverBotones = true;
			lbEsModificando = true;
			lbEsIncrementoSalarial = false;
			normaNomenclatura = new Norma();
			this.setStrMsgAcciones(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_NOMENCLATURA_CREAR, getLocale()));
			 RequestContext.getCurrentInstance().scrollTo("frNomenclaturaGeneral:PnlFrmNomenclatura");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EN_PROCESO);
		}
	}
	/**
	 * Guarda la nomenclatura
	 * @param strTipoGuardado 0:Parcial 1:Definitivo
	 */
	public void guardarNomenclatura(String strTipoGuardado){
		if(nomenclaturaProcesar==null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_GUARDAR_VACIO);
			return;
		}
		
		if( (nomenclaturaProcesar.getJustificacion()==null || "".equals(nomenclaturaProcesar.getJustificacion())) && 
				(normaNomenclatura.getNumeroNorma()==null||normaNomenclatura.getFechaNorma()==null || normaNomenclatura.getCodTipoNorma()==null)){
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INGRESAR_NORMA_JUSTIFICACION);
			return;
		}
		if(nomenclaturaProcesar.getCodNomenclatura() !=null) {
			nomenclaturaProcesar.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}else {
			nomenclaturaProcesar.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}
		validarNormaNomenclatura();
		
		nomenclaturaProcesar.setFlgDefinitivo(Short.valueOf(strTipoGuardado));
		nomenclaturaRespuesta = ComunicacionServiciosEnt.setNomenclaturaRespuesta(nomenclaturaProcesar);
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
		    	replicarNomenclaturaHeredada();
		    }
		}
		inicializarFormulario();
	}
	

	/**
	 * Metodo que replica la información almacenada en la nomenclatura general en las nomenclaturas asociadas.
	 */
	public boolean replicarNomenclaturaHeredada() {
		NomenclaturaExt objNomenclatura = new NomenclaturaExt();
		if(nomenclaturaProcesar.getCodNomenclatura() != null) {
			objNomenclatura.setCodNomenclatura(nomenclaturaProcesar.getCodNomenclatura());
			objNomenclatura.setAudAccion(nomenclaturaProcesar.getAudAccion());
			NomenclaturaExt resNomenclatura = ComunicacionServiciosEnt.setNomenclaturasHeredadas(objNomenclatura);
			if(resNomenclatura !=null && resNomenclatura.getMensajeTecnico()!=null) {
				return false;
			}
		}
		return true;
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
				nomenclaturaProcesar.setCodNorma(lstNormas.get(0).getCodNorma().longValue());
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
					nomenclaturaProcesar.setCodNorma(norma.getCodNorma());
				}else{
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, norma.getMensaje());
					return;
				}
			}
		}
	}
	
	
	/**
	 * Consulta las nomenclaturas genericas
	 */
	private void cargarNomenclaturas(){
		buscadorNomenclatura = new NomenclaturaExt();
		buscadorNomenclatura.setFlgActivo((short) 1);
		buscadorNomenclatura.setFlgGenerica((short) 1);
		lstNomenclaturas = ComunicacionServiciosEnt.getNomenclaturaFiltro(buscadorNomenclatura);
		lbVerFrmNomenclatura = false;
	}
	/**
	 * Guardar las denomincaciones
	 * Es llamado si guardarNomenclatura se ejecuta correctamente
	 */
	private boolean guardarDenominaciones(){
		if(lstDenominacionesNomenclatura!=null && lstDenominacionesNomenclatura.size()>0){
			if(nomenclaturaProcesar.getCodNomenclatura()==null)
				nomenclaturaProcesar.setCodNomenclatura(nomenclaturaRespuesta.getCodNomenclatura());
			for(NomenclaturaDenominacion n:lstDenominacionesNomenclatura){
					if(n.getCodNomenclaturaDenominacion()!=null)
						n.setAudAccion(codAudAccionUpdate);
					n.setAudCodRol(codAudCodRol);
					n.setFlgActivoParticular((short)0);
					n.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
					n.setAudFechaActualizacion(new Date());
					n.setCodNomenclatura(BigDecimal.valueOf(nomenclaturaProcesar.getCodNomenclatura()));
					NomenclaturaDenominacion res =  ComunicacionServiciosEnt.setNomenclaturaDenominacion(n);
					if(res.isError()){
						mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_GUARDAR_ERROR);
						return false;
					}
			}
		}
		return true;
	}
	
	private boolean guardarOtrasAsignaciones(){
		boolean guardadoAsignaciones = true;
		if(lstOtrasAsignaciones!=null && lstOtrasAsignaciones.size()>0){
			AsignacionSalarial respuesta;
			for(AsignacionSalarial asignacion:lstOtrasAsignaciones){
				if(asignacion.getCodAsignacionSalarial()==null)
					asignacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
				else
					asignacion.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
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
	 * Agrega una denomincación
	 */
	public void agregarDenominacion(){
		if(nomenclaturaProcesar!=null){
			nomenclaturaDenominacionProcesar = new NomenclaturaDenominacionExt();
			nomenclaturaDenominacionProcesar.setAudAccion(codAudAccionInsert);
			nomenclaturaDenominacionProcesar.setAudCodRol(codAudCodRol);
			nomenclaturaDenominacionProcesar.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
			nomenclaturaDenominacionProcesar.setAudFechaActualizacion(new Date());
			nomenclaturaDenominacionProcesar.setFlgActivo((short) 1);
			lbVerFrmDenominacion = true;
			blDenominacionNueva = true;
			blDenominacionModificar=false;
			
			RequestContext.getCurrentInstance().execute("PF('dlgNuevaDenominacion').show();");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_SELECCIONAR_NOMENCLATURA);
		}
	}
	/**
	 * Agrega la denomincación procesa sea nueva o editada a la lista de denominaciones; el guardar se efectuta en guardarNomenclatura
	 */
	public void guardarDenominacion(){
		if(!flgIsGnrlTerritorial && validarLimiteSalarial()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ASIGNACION_SALARIAL_MAYOR);
		}
		
		if(lstDenominacionesNomenclatura==null) {
			lstDenominacionesNomenclatura = new ArrayList<NomenclaturaDenominacionExt>();
		}
		
		if(validarDuplicidadDenominacion()) {
			return;
		}
		
		if(lstDenominacionesNomenclatura.contains(nomenclaturaDenominacionProcesar)){
			lstDenominacionesNomenclatura.remove(nomenclaturaDenominacionProcesar);
			lstDenominacionesNomenclatura.add(nomenclaturaDenominacionProcesar);
		}else{
			lstDenominacionesNomenclatura.add(0, nomenclaturaDenominacionProcesar);	
		}
		RequestContext.getCurrentInstance().execute("PF('dlgNuevaDenominacion').hide();");
		lbVerFrmDenominacion = false;
		
	}
	
	public boolean validarLimiteSalarial() {
		try {
			Parametrica nivelJerarquico = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(nomenclaturaDenominacionProcesar.getCodNivelJerarquico()));
			Parametrica limiteSalarial = ComunicacionServiciosSis.getParametricaByname(TipoParametro.COD_LIMITES_SALARIALES.getValue(), nivelJerarquico.getValorParametro());
			Double limiteSalarialValor = Double.parseDouble(limiteSalarial.getValorParametro());
			Double asignacionSalarial = nomenclaturaDenominacionProcesar.getAsignacionSalarial().doubleValue();
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
					if(!flgIsGnrlTerritorial
							&& nomenclaturaDenominacionProcesar.getCodNivelJerarquico().equals(den.getCodNivelJerarquico())
							&& nomenclaturaDenominacionProcesar.getCodDenominacion().equals(den.getCodDenominacion())
							&& nomenclaturaDenominacionProcesar.getCodCodigo().equals(den.getCodCodigo())
							&& nomenclaturaDenominacionProcesar.getCodGrado().equals(den.getCodGrado())) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_REPETIDA);
						return true;
					} else if(flgIsGnrlTerritorial
								&& nomenclaturaDenominacionProcesar.getCodNivelJerarquico().equals(den.getCodNivelJerarquico())
								&& nomenclaturaDenominacionProcesar.getCodDenominacion().equals(den.getCodDenominacion())
								&& nomenclaturaDenominacionProcesar.getCodCodigo().equals(den.getCodCodigo())) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_REPETIDA);
						return true;
					}
				}
			}else {
				if(!flgIsGnrlTerritorial &&
						(!nomenclaturaDenominacionProcesar.getCodNivelJerarquico().equals(codNivelAnt)
							|| !nomenclaturaDenominacionProcesar.getCodDenominacion().equals(codDenAnt)
							|| !nomenclaturaDenominacionProcesar.getCodCodigo().equals(codCodAnt)
							|| !nomenclaturaDenominacionProcesar.getCodGrado().equals(codGradoAnt))) {
					lstDenominacionesNomenclatura.remove(nomenclaturaDenominacionProcesar);
					for(NomenclaturaDenominacionExt den : lstDenominacionesNomenclatura  ) {
						if(nomenclaturaDenominacionProcesar.getCodNivelJerarquico().equals(den.getCodNivelJerarquico())
								&& nomenclaturaDenominacionProcesar.getCodDenominacion().equals(den.getCodDenominacion())
								&& nomenclaturaDenominacionProcesar.getCodCodigo().equals(den.getCodCodigo())
								&& nomenclaturaDenominacionProcesar.getCodGrado().equals(den.getCodGrado())) {
							mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_REPETIDA);
							if(blDenominacionModificar) {
								nomenclaturaDenominacionProcesar.setCodNivelJerarquico(codNivelAnt);
								nomenclaturaDenominacionProcesar.setCodDenominacion(codDenAnt);
								nomenclaturaDenominacionProcesar.setCodCodigo(codCodAnt);
								nomenclaturaDenominacionProcesar.setCodGrado(codGradoAnt);
								lstDenominacionesNomenclatura.add(nomenclaturaDenominacionProcesar);
							}
							return true;
						}
					}
				}else if (flgIsGnrlTerritorial &&
						  	(!nomenclaturaDenominacionProcesar.getCodNivelJerarquico().equals(codNivelAnt)
						  		|| !nomenclaturaDenominacionProcesar.getCodDenominacion().equals(codDenAnt)
						  		|| !nomenclaturaDenominacionProcesar.getCodCodigo().equals(codCodAnt))) {
					lstDenominacionesNomenclatura.remove(nomenclaturaDenominacionProcesar);
					for(NomenclaturaDenominacionExt den : lstDenominacionesNomenclatura  ) {
						if(nomenclaturaDenominacionProcesar.getCodNivelJerarquico().equals(den.getCodNivelJerarquico())
								&& nomenclaturaDenominacionProcesar.getCodDenominacion().equals(den.getCodDenominacion())
								&& nomenclaturaDenominacionProcesar.getCodCodigo().equals(den.getCodCodigo())) {
							mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_REPETIDA);
							if(blDenominacionModificar) {
								nomenclaturaDenominacionProcesar.setCodNivelJerarquico(codNivelAnt);
								nomenclaturaDenominacionProcesar.setCodDenominacion(codDenAnt);
								nomenclaturaDenominacionProcesar.setCodCodigo(codCodAnt);
								lstDenominacionesNomenclatura.add(nomenclaturaDenominacionProcesar);
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
	 * Solicita Confirmación para cancelar la acción actual
	 */
	public void cancelar(){
		accionesFalse();
		lbCancelar = true;
		if(!lbEsConsultaNomenclatura) {
			this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_DIALOGO_CANCELAR, getLocale()));
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");
		}else {
			inicializarFormulario();
		}
	}
	/**
	 * Solicita confirmación para salir de la pantalla y retornar al index
	 */
	public void regresar(){
		regresarIndex();
		accionesFalse();
		lbSalir= true;
	}
	
	/**
	 * Modifica una nomenclatura existente
	 * @param nomenclaturasel
	 */
	public void modificarNomenclatura(NomenclaturaExt nomenclaturasel){
		if(!esNomenclaturaEnProceso()){
			nomenclaturaProcesar = nomenclaturasel;
			lbVerFrmNomenclatura = true;
			lblVerDenominaciones = true;
			cargarDenomincacionesNomenclatura();
			buscarNormaNomenclatura();
			lbEsConsultaNomenclatura = false;
			lbVerFrmDenominacion = false;
			lbEsIncrementoSalarial = false;
			lbVerFrmAsociar=false;
			lbEsModificando = true;
			lblverBotones = true;
			flgIsGnrlTerritorial = nomenclaturasel.getCodNomenclatura().equals((long) TipoParametro.COD_GENERAL_TERRITORIAL.getValue()) ? true: false;
			this.setStrMsgAcciones(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ENTIDADES_NOMENCLATURA_MODIFICAR, getLocale()));
			RequestContext.getCurrentInstance().update("frNomenclaturaGeneral");
			RequestContext.getCurrentInstance().scrollTo("frNomenclaturaGeneral:PnlFrmNomenclatura");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EN_PROCESO);
		}			
	}
	/**
	 * Permite a visualizavión en modo consulta de una nomenclatura existente
	 * @param nomenclaturasel
	 */
	public void consultarNomenclatura(NomenclaturaExt nomenclaturasel){			
		nomenclaturaProcesar = nomenclaturasel;
		lbVerFrmNomenclatura = true;
		lblVerDenominaciones = true;
		cargarDenomincacionesNomenclatura();
		buscarNormaNomenclatura();
		lbEsConsultaNomenclatura = true;
		lbVerFrmDenominacion = false;
		lbEsIncrementoSalarial = false;
		lbVerFrmAsociar = false;
		lbEsModificando = true;
		lblverBotones = true;
		flgIsGnrlTerritorial = nomenclaturasel.getCodNomenclatura().equals((long) 23) ? true: false;
		this.setStrMsgAcciones(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ENTIDADES_NOMENCLATURA_CONSULTAR, getLocale()));
		RequestContext.getCurrentInstance().scrollTo("frNomenclaturaGeneral:PnlFrmNomenclatura");	
	}
	
	/**
	 * Metodo que busca las normas de las nomenclaturas.
	 */
	public void buscarNormaNomenclatura() {
		normaNomenclatura = new Norma();
		normaSeleccionada = new Norma();
		if(nomenclaturaProcesar != null && nomenclaturaProcesar.getCodNorma()!=null) {
			Norma norma= ComunicacionServiciosEnt.getNormaPorId(nomenclaturaProcesar.getCodNorma().longValue());
			if(norma != null) {
				normaNomenclatura = norma;
				this.normaSeleccionada= norma;
			}
		}
	}
	
	/**
	 * Modifica una denominación existente
	 * @param nomenclaturaDenominacion
	 */
	public void modificarDenominacion(NomenclaturaDenominacionExt nomenclaturaDenominacion){
		nomenclaturaDenominacionProcesar = nomenclaturaDenominacion;
		codNivelAnt = nomenclaturaDenominacion.getCodNivelJerarquico();
		codDenAnt 	= nomenclaturaDenominacion.getCodDenominacion();
		codCodAnt 	= nomenclaturaDenominacion.getCodCodigo();
		codGradoAnt = nomenclaturaDenominacion.getCodGrado();
		
		lbVerFrmDenominacion = true;
		lbEsConsultaDenomincacion = false;
		lbVerFrmAsociar = false;
		lbConsultaDenominacion = false;
		blDenominacionNueva = false;
		blDenominacionModificar=true;
		 
		RequestContext.getCurrentInstance().execute("PF('dlgNuevaDenominacion').show();");
	}
	/**
	 * Permite la visualización en modo consulta de una denominación existente
	 * @param nomenclaturaDenominacion
	 */
	public void consultarDenominacion(NomenclaturaDenominacionExt nomenclaturaDenominacion){
		lbConsultaDenominacion = true;
		nomenclaturaDenominacionProcesar = nomenclaturaDenominacion;
		lbVerFrmDenominacion = true;
		lbEsConsultaDenomincacion = true;
		lbVerFrmAsociar = false;
		RequestContext.getCurrentInstance().execute("PF('dlgNuevaDenominacion').show();");
	}	
	/**
	 * Solicita confirmación para eliminar nomenclatura
	 * @param nomenclaturasel
	 */
	public void eliminarNomenclatura(NomenclaturaExt nomenclaturasel){
		if(!esNomenclaturaEnProceso()){
			nomenclaturaProcesar = nomenclaturasel;
			accionesFalse();
			lbEliminarNomenclatura = true;
			this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PREGUNTA_ELIMINAR_REGISTRO, getLocale()));
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EN_PROCESO);
		}				
	}
	/**
	 * Proceso las acciones de confirmación en caso de que la respuesta sea "SI"
	 * lbEliminarNomenclatura Elimina nomenclatura
	 * eliminarDenominacion   Elimina denominación
	 * lbCancelar             Cancela la edición actual y reestablece el formulario
	 * lbSalir                Retorna al index
	 */
	public void procesarRespuestaConfirmacion(){
		if(lbEliminarNomenclatura){
			eliminarNomenclatura();
		}else if(lbEliminarDenominacion){
			eliminarDenominacion();
		}else if(lbCancelar){
			inicializarFormulario();
		}else if(lbSalir){
			regresarIndex();
		}else if(lbAplicarIncrementoSalarial){
			aplicarIncrementoEscalaDenominaciones();
		}else if(lbCancelarIncrementoSalarial){
			inicializarFormulario();
		}else if(lbGuardarIncrementoSalarial){
			guardarIncrementoSalarial();
		}else if(lbAccionAsociarEntidades){
			asociarNomenclaturaEntidades();
		}else if(lbAccionCancelarAsociarEntidades){
			inicializarFormulario();
		}else if(lbAccionEliminarOtraAsignacion){
			eliminaAsignacionDenominacion();
		}else if(lbCancelarEquivalencias){
			inicializarFormulario();
		}else if(lbAccionEquivalenciasFusionar){
			equivalenciasFusionar();
		}
	}
	private void eliminarNomenclatura(){
		validarPlantasAsociadas();
		if(listaPlantasNomGeneral.isEmpty()) {
			nomenclaturaProcesar.setAudAccion(codAudAccionUpdate);
			nomenclaturaProcesar.setAudCodRol(codAudCodRol);
			nomenclaturaProcesar.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
			nomenclaturaProcesar.setAudFechaActualizacion(new Date());
			nomenclaturaProcesar.setFlgActivo((short) 0);
			Nomenclatura nomenclaturaRespuesta;
			nomenclaturaRespuesta = ComunicacionServiciosEnt.setNomenclaturaRespuesta(nomenclaturaProcesar);
			if(nomenclaturaRespuesta.isError()){
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_ELIMINAR_ERROR);	
			}else{
				replicarNomenclaturaHeredada();
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_ELIMINAR_EXITO);
			}
			
			cargarNomenclaturas();	
		}else {
			RequestContext.getCurrentInstance().execute("PF('wdgtDetallePlantasAsociadasNomenclaturaGeneral').show();");
		}	
	}
	
	/**
	  * Metodo que permite validar si una nomenclatura ya esta asociada a una planta
	  */
	 public void validarPlantasAsociadas() {
		 NomenclaturaExt entFiltro = new NomenclaturaExt();
		 entFiltro.setCodNomenclaturaAsociada(nomenclaturaProcesar.getCodNomenclatura());
		 entFiltro.setFlgActivo((short)1);
		 listaPlantasNomGeneral = ComunicacionServiciosEnt.getPlantaAsociadaNomGeneral(entFiltro);
	 }
	/**
	 * Solicita confirmacion para eliminar denomincacion
	 * @param nomenclaturaDenomincacion
	 */
	public void eliminarDenominacion(NomenclaturaDenominacionExt nomenclaturaDenomincacion){
		nomenclaturaDenominacionProcesar = nomenclaturaDenomincacion;
		lbEliminarNomenclatura = false;
		lbEliminarDenominacion = true;
		lbCancelar = false;
		lbSalir= false;
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PREGUNTA_ELIMINAR_REGISTRO, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");
	}

	/**
	 * Metodo que elimina una denominacion
	 */
	private void eliminarDenominacion(){
		if(nomenclaturaDenominacionProcesar.getCodNomenclaturaDenominacion()!=null){
			boolean valid = false; 
			valid = this.validarVinculacionDenominacionGeneral();
			if(valid) 
				return;
			valid = this.validarPlantaDenominacionGeneral();
			if(valid) 
				return;
			nomenclaturaDenominacionProcesar.setAudAccion(codAudAccionUpdate);
			nomenclaturaDenominacionProcesar.setAudCodRol(codAudCodRol);
			nomenclaturaDenominacionProcesar.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
			nomenclaturaDenominacionProcesar.setAudFechaActualizacion(new Date());
			nomenclaturaDenominacionProcesar.setFlgActivo((short) 0);
			NomenclaturaDenominacion res = ComunicacionServiciosEnt.setNomenclaturaDenominacion(nomenclaturaDenominacionProcesar);
			if(res.isError()){
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_ELIMINAR_ERROR);	
			}else{
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_ELIMINAR_EXITO);
				actualizarDenominacionesAsociadas();
			}
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_ELIMINAR_EXITO);
		}
		lstDenominacionesNomenclatura.remove(nomenclaturaDenominacionProcesar);
	}
	
	/**
	 * Metodo que actualiza las denominaciones asociadas
	 */
	public void actualizarDenominacionesAsociadas() {
		NomenclaturaDenominacion denominacionAsociada = new NomenclaturaDenominacion();
		denominacionAsociada.setAudAccion(codAudAccionUpdate);
		denominacionAsociada.setAudCodRol(codAudCodRol);
		denominacionAsociada.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
		denominacionAsociada.setAudFechaActualizacion(new Date());
		denominacionAsociada.setFlgActivo((short) 0);
		denominacionAsociada.setCodDenominacionAsociacion(new BigDecimal(nomenclaturaDenominacionProcesar.getCodNomenclaturaDenominacion()));
		NomenclaturaDenominacion res = ComunicacionServiciosEnt.setNomenclaturaDenominacionAsociadas(denominacionAsociada);
		
	}
	
	/**
	 * Metodo que valida si una denominacion general cuenta con vinculaciones hijas asociadas.
	 * @return
	 */
	public boolean validarVinculacionDenominacionGeneral() {
		boolean blnVinculacion = false;
		if(nomenclaturaDenominacionProcesar.getCodNomenclaturaDenominacion()!=null) {
			EntidadPlantaDetalleExt entidadPlantaDetalle = new EntidadPlantaDetalleExt();
			entidadPlantaDetalle.setCodNomenclaturaDenominacion(nomenclaturaDenominacionProcesar.getCodNomenclaturaDenominacion());
			entidadPlantaDetalle.setFlgActivo((short)1);
			lstPlantaDetalleDenominacion  = ComunicacionServiciosEnt.getVinculacionDenominacionGeneral(entidadPlantaDetalle);
			 if(!lstPlantaDetalleDenominacion.isEmpty()) {
				 blnVinculacion = true;
				 RequestContext.getCurrentInstance().execute("PF('wdgtDetalleVinculacionPlantaAsociada').show();");
			 }
		}
		return blnVinculacion;
	}
	
	/**
	 * Metodo que valida si una denominacion general cuenta con plantas hijas asociadas.
	 * @return
	 */
	public boolean validarPlantaDenominacionGeneral() {
		boolean blnPlanta = false;
		if(nomenclaturaDenominacionProcesar.getCodNomenclaturaDenominacion()!=null) {
			EntidadPlantaDetalleExt entidadPlantaDetalle = new EntidadPlantaDetalleExt();
			entidadPlantaDetalle.setCodNomenclaturaDenominacion(nomenclaturaDenominacionProcesar.getCodNomenclaturaDenominacion());
			entidadPlantaDetalle.setFlgActivo((short)1);
			lstPlantaDetalleDenominacion  = ComunicacionServiciosEnt.getPlantaDenominacionGeneral(entidadPlantaDetalle);
			 if(!lstPlantaDetalleDenominacion.isEmpty()) {
				 blnPlanta = true;
				 RequestContext.getCurrentInstance().execute("PF('wdgtDetalleVinculacionPlantaAsociada').show();");
			 }
		}
		return blnPlanta;
		
	}
	
	
	/**
	 * Reetorna la lista de denominaciones del maestro denominacion
	 * @return
	 */
	private void getListaDenominaciones(){
		buscadorDenominaciones = new Denominacion();
		List<Denominacion>lstDenomincaciones = ComunicacionServiciosEnt.getDenomincacionesFiltro(buscadorDenominaciones);
		if(lstDenomincaciones!=null && lstDenomincaciones.size()>0){
			lstSIDenominaciones = new ArrayList<SelectItem>();
			for(Denominacion d:lstDenomincaciones){
				lstSIDenominaciones.add(new SelectItem(d.getCodDenominacion(),d.getNombreDenominacion().toUpperCase()));
			}
		}
	}
	
	/**
	 * carga la lista de denominaciones de una nomenclatura
	 */
	private void cargarDenomincacionesNomenclatura(){
		buscardorNomenclaturaDenominaciones = new NomenclaturaDenominacionExt();
		buscardorNomenclaturaDenominaciones.setFlgActivo((short) 1);
		buscardorNomenclaturaDenominaciones.setCodNomenclatura(BigDecimal.valueOf(nomenclaturaProcesar.getCodNomenclatura()));
		lstDenominacionesNomenclatura = ComunicacionServiciosEnt.getDenomincacionesNomenclaturaExtFiltro(buscardorNomenclaturaDenominaciones);
	}
	
	/**
	 * Función de autocompletar a norma
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
	 public void vclNorma(ValueChangeEvent  norma) {
		 Norma estr = (Norma) norma.getNewValue();
	        if (estr != null && estr.getNumeroNorma() != null) {
	        	normaNomenclatura.setNumeroNorma(estr.getNumeroNorma());
	        }
	 }
	 
		public List<Norma> completeMethodNormaIncremento(String  numeroNorma) {
			 Norma norm = new Norma();
			 norm.setNumeroNorma(numeroNorma);
			 norm.setFechaNorma(normaIncremento.getFechaNorma());
			 if(normaIncremento.getCodTipoNorma()!=null)
				 norm.setCodTipoNorma(normaIncremento.getCodTipoNorma().intValue());
			 List<Norma> normas =  ComunicacionServiciosEnt.getFiltroNormaLike(norm);
		     if(normas == null) {
		    	 return new ArrayList<>();
		     }
		     return normas;
		 }		 
	 public void vclNormaIncremento(ValueChangeEvent  estructuraExt) {
		 Norma estr = (Norma) estructuraExt.getNewValue();
	        if (estr != null && estr.getNumeroNorma() != null) {
	        	normaIncremento.setNumeroNorma(estr.getNumeroNorma());
	        }
	 }
	 
	 /**
	  * Restablece los valores iniciales del formulario
	  */
	private void inicializarFormulario(){
		/*se inciia nomenclaturaProcesar*/
		nomenclaturaProcesar = null;
		/*se limpia lista de denomincaciones*/
		lstDenominacionesNomenclatura = null;
		/*flg de formulario modificado a false*/
		lbFormularioModificado = false;
		/*flgs de consltas a false*/
		lbEsConsultaNomenclatura=false;
		lbEsConsultaDenomincacion = false;
		/*se carga lista inicial de nomenclaturas*/
		cargarNomenclaturas();
		/*flgs ver formularios a false*/
		lbVerFrmNomenclatura = false;
		lbVerFrmDenominacion = false;
		lblVerDenominaciones = false;
		/*flgs Incremento*/
		lbEsIncrementoSalarial= false;
		/*flg asociar*/
		lbVerFrmAsociar = false;
		lstEntidadesDisponibles=null;
		/*Otras Asignaciones*/
		lbEsEditarOtraAsignacion = false;
		lbEsModificandoOtrasAsignaciones = false;
		/*Es modificando*/
		lbEsModificando = false;
		/*Equivalencias*/
		lbEsEquivalencias = false;
		lbCancelarEquivalencias = false;
		lblverBotones = false;
		nomenclaturaDenominacionDestino=null;
		normaNomenclatura = null;
		normaSeleccionada = null;
	}	
	

	/**
	 * Redirecciona al index cuando la accion ha sido confirmada
	 */
	private void regresarIndex(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../../persona/informacionPersonal.xhtml?");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * INCREMENTO Y ESCALA SALARIAL
	 */
	
	/**
	 * Accion de nomenclatura, selecciona la nomenclatura  a aplicar incremente
	 * @param nomenclaturasel
	 */
	public void seleccionarNomenclaturaIncrementarescala(NomenclaturaExt nomenclaturasel){
		if(!esNomenclaturaEnProceso()){
			lbEsIncrementoSalarial 	= true;
			lbgValorIncremento 		= null;
			normaSeleccionadaIncremento = new Norma();
			nomenclaturaProcesar = nomenclaturasel;
			nomenclaturaDenominacionProcesar = new NomenclaturaDenominacionExt();/*pa que el dialog no saque null*/
			lbVerFrmNomenclatura = true;
			lblVerDenominaciones = true;
			lbEsConsultaNomenclatura = true;
			lbVerFrmAsociar = false;
			normaIncremento = new Norma();
			buscarNormaNomenclatura();
			cargarDenomincacionesNomenclatura();
			lbEsModificando = true;
			this.setStrMsgAcciones(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_INCREMENTAR_ESCALA_SALARIAL, getLocale()));
			RequestContext.getCurrentInstance().scrollTo("frNomenclaturaGeneral:PnlFrmNomenclatura");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EN_PROCESO);
		}				
	}
	public void solicitaConfirmacionaplicarIncrementoEscalaDenominaciones(){
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
		/**
		 * @todo validar incremento por valor
		 */
		accionesFalse();
		lbAplicarIncrementoSalarial = true;
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(
				MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_DENOMINACION_APLICAR_OPCION_INCREMENTO, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");		
	}
	/**
	 * Aplica la opción de incremente salarial a las denominaciones:
	 * 0:Porcenteaje
	 * 1:Valor fijo
	 * 2:Individual
	 */
	public void aplicarIncrementoEscalaDenominaciones(){
		if(lstDenominacionesNomenclatura!=null && lstDenominacionesNomenclatura.size()>0){
			if("0".equals(strOpcionIncremento)){
				for(NomenclaturaDenominacionExt n:lstDenominacionesNomenclatura){
					n.setIncPorcentaje(lbgValorIncremento);
					if(n.getAsignacionSalarial() != null) {
						n.setNuevaAsignacionSalarial(n.getAsignacionSalarial().add( (n.getAsignacionSalarial().multiply(lbgValorIncremento)).divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING));
					}
					n.setIncValor(null);
				}
			}else if("1".equals(strOpcionIncremento)){
				for(NomenclaturaDenominacionExt n:lstDenominacionesNomenclatura){
					n.setIncValor(lbgValorIncremento);
					if(n.getAsignacionSalarial() != null) {
						n.setNuevaAsignacionSalarial(n.getAsignacionSalarial()!= null ? n.getAsignacionSalarial().add(lbgValorIncremento).setScale(0, BigDecimal.ROUND_CEILING): lbgValorIncremento);
					}
					n.setIncPorcentaje(null);
				}	
			}	
		}
	}
	/**
	 * Solicita Confirmacion para guardar el incremento salarial
	 */
	public void solicitaConfirmacionGuardarIncrementoSalarial(){
		accionesFalse();
		lbGuardarIncrementoSalarial =  true;
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_GUARDAR_INCREMENTO, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");		
	}
	/**
	 * Guarda el incremente salarial cuando la acción haya sido confirmada
	 */
	public void guardarIncrementoSalarial(){
		if(lstDenominacionesNomenclatura!=null && lstDenominacionesNomenclatura.size()>0){
			for(NomenclaturaDenominacionExt n:lstDenominacionesNomenclatura){
				if(n.getCodNomenclaturaDenominacion()!=null)
					n.setAudAccion(codAudAccionUpdate);
				n.setAudCodRol(codAudCodRol);
				n.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
				n.setAudFechaActualizacion(new Date());
				n.setCodNomenclatura(BigDecimal.valueOf(nomenclaturaProcesar.getCodNomenclatura()));
				if(n.getNuevaAsignacionSalarial()!=null)
					n.setAsignacionSalarial(n.getNuevaAsignacionSalarial());
				n.setIncFecha(new Date());
				NomenclaturaDenominacion res  =  ComunicacionServiciosEnt.setNomenclaturaDenominacion(n);
				if(res.isError()){
					mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_ERROR);
					return;
				}
			}
			/*GuardarNorma*/
			incrementoSalarial = new IncrementoSalarial();
			incrementoSalarial.setAudAccion(codAudAccionInsert);
			incrementoSalarial.setAudCodRol(codAudCodRol);
			incrementoSalarial.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
			incrementoSalarial.setAudFechaActualizacion(new Date());
			Norma normaBuscador = new Norma();
			normaBuscador.setNumeroNorma(normaIncremento.getNumeroNorma());
			normaBuscador.setFechaNorma(normaIncremento.getFechaNorma());
			 if(normaIncremento.getCodTipoNorma()!=null)
				 normaBuscador.setCodTipoNorma(normaIncremento.getCodTipoNorma().intValue());
			 List<Norma> normas =  ComunicacionServiciosEnt.getFiltroNormaLike(normaBuscador);
			 if(normas!=null && normas.size()>0)
				 incrementoSalarial.setCodNorma(normas.get(0).getCodNorma());
			 incrementoSalarial.setCodNomenclatura(nomenclaturaProcesar.getCodNomenclatura());
			 
			 IncrementoSalarial res = ComunicacionServiciosEnt.guardarIncremento(incrementoSalarial);
			 if(res.isError()){
				 mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_ERROR);
				 inicializarFormulario();
				 return;
			 }else{
				 if(!replicarNomenclaturaHeredada()){
					 mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_ERROR);
					 inicializarFormulario();
					 return;					 
				 }
				 mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_EXITO);
				 inicializarFormulario();
				 return; 
			 }
			 
		}else{
			 mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_GUARDAR_ERROR);
			 inicializarFormulario();
			 return;			
		}
		
	}
	/**
	 * Solicita confirmacion para cancelar el incremento de escala salarial
	 */
	public void solicitaConfirmacionCancelarIncrementoEscala(){
		accionesFalse();
		lbCancelarIncrementoSalarial =  true;	
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_CANCELAR_INCREMENTO, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");	
	}
	
	public void aplicarIncrementoDenomincacionIndividual( NomenclaturaDenominacionExt denomincaciionsel, String strOpcion){
		nomenclaturaDenominacionProcesar = denomincaciionsel;
		if("0".equals(strOpcion)){
			if(nomenclaturaDenominacionProcesar.getAsignacionSalarial() != null) {
				nomenclaturaDenominacionProcesar.setNuevaAsignacionSalarial(
						nomenclaturaDenominacionProcesar.getAsignacionSalarial().add( 
								(nomenclaturaDenominacionProcesar.getAsignacionSalarial().multiply(denomincaciionsel.getIncPorcentaje())).divide(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_CEILING));
			}
			
			nomenclaturaDenominacionProcesar.setIncValor(null);
			
		}else if("1".equals(strOpcion)){
			if(nomenclaturaDenominacionProcesar.getAsignacionSalarial() != null) {
				nomenclaturaDenominacionProcesar.setNuevaAsignacionSalarial(nomenclaturaDenominacionProcesar.getAsignacionSalarial().add(denomincaciionsel.getIncValor()).setScale(0, BigDecimal.ROUND_CEILING));
			}
			nomenclaturaDenominacionProcesar.setIncPorcentaje(null);
		}
	}
	
	/**
	 * Lleva todas las acciones de confirmacion a false
	 */
	private void accionesFalse(){
		lbEliminarNomenclatura = false;
		lbEliminarDenominacion = false;
		lbCancelar = false;
		lbSalir = false;
		lbAplicarIncrementoSalarial = false;
		lbGuardarIncrementoSalarial =  false;
		lbCancelarIncrementoSalarial =  false;
		lbAccionAsociarEntidades=false;
		lbAccionCancelarAsociarEntidades=false;
		lbAccionEliminarOtraAsignacion = false;
		lbAccionEquivalenciasFusionar = false;
		lbCancelarEquivalencias = false;
	}
	
	 public void handleOpcionIncrementoSelection(AjaxBehaviorEvent  e) {
	        
	 }	
	 
	 /**
	  * ASOCIAR NOMENCLATURA A ENTIDADES
	  */
	 public void seleccionaNomenclaturaAsociar(NomenclaturaExt nomenclaturasel){
		if (!esNomenclaturaEnProceso()) {
			if (nomenclaturasel.getFlgDefinitivo() != null && nomenclaturasel.getFlgDefinitivo() == 1) {
				lbVerFrmAsociar = true;
				lbEsIncrementoSalarial = false;
				nomenclaturaProcesar = nomenclaturasel;
				lbEsModificando = true;
				this.setStrMsgAcciones(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ENTIDADES_NOMENCLATURA_ASOCIAR_NOMENCLATURA, getLocale()));
				RequestContext.getCurrentInstance().scrollTo("frNomenclaturaGeneral:pnlFrmAsociarEntidades");
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_GUARDADO_PARCIAL_ASOCIACION);
			}

		} else {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EN_PROCESO);
		}		 
	 }
	 public void buscarEntidadesAsociar(){
		 entFiltro.setFlgActivo((short) 1);
		 lstEntidadesDisponibles = ComunicacionServiciosEnt.getEntidadesFiltro(entFiltro);
	 }
	 public void solicitaConfirmacionAsociarNomenclaturaEntidades(){
		accionesFalse();
		lbAccionAsociarEntidades = true;	
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_ASOCIAR_ENTIDADES, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");	
	 }
	 public void solicitaConfirmacionCancelarAsociarNomenclaturaEntidades(){
		accionesFalse();
		lbAccionCancelarAsociarEntidades=true;	
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_CANCELAR_ASOCIAR_ENTIDADES, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");		 
	 }
	 
	 /*Metodo que se encarga de asociar una nomenclatura general a una entidad
	  * Este metodo llama servicio que se encarga de conectar con la función F_ASOCIAR_NOMENCLATURA_ENTIDAD */
	 public void asociarNomenclaturaEntidades() {
		 for (Entidad entidadAsociar : lstEntidadesSeleccionadas) {
			 if (!asociadaNomenclatura(entidadAsociar)) {
				 NomenclaturaExt objNomenclatura = new NomenclaturaExt();
				 objNomenclatura.setAudAccion(codAudAccionInsert);
				 objNomenclatura.setAudCodRol(codAudCodRol);
				 objNomenclatura.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
				 objNomenclatura.setCodNomenclaturaAsociada(nomenclaturaProcesar.getCodNomenclatura());
				 objNomenclatura.setCodEntidad(entidadAsociar.getCodEntidad());
				 Nomenclatura result = ComunicacionServiciosEnt.setAsociarNomenclaturAEntidad(objNomenclatura);
				 if(result != null && !result.isError()) {
					 mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_ASOCIAR_EXITO);
				}else {

					 mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_ASOCIAR_ERROR);
				}
			 }else {
				String strMensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EXISTENCIA_ASOCIACION, getLocale())
						.replace("%entidad%", entidadAsociar.getNombreEntidad());
				
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,strMensaje);
			}
		 }
		 
	 }
	 
	 /**
	  * Metodo que verifica si la entidad ya posee asociada la nomenclatura a asociar
	  */
	public boolean asociadaNomenclatura(Entidad entidad) {
		boolean blnAsociada = false;
		NomenclaturaExt objeNomenclatura = new NomenclaturaExt();
		objeNomenclatura.setCodEntidad(entidad.getCodEntidad());
		objeNomenclatura.setCodNomenclaturaAsociada(nomenclaturaProcesar.getCodNomenclatura());
		objeNomenclatura.setFlgActivo((short)1);
		List<NomenclaturaExt> nomenclaturaAsociada = ComunicacionServiciosEnt.getNomenclaturaFiltro(objeNomenclatura);
		 if(!nomenclaturaAsociada.isEmpty()) {
			 blnAsociada = true;
		 }
		 return blnAsociada;
	 }
	 
	/**
	  * OTRAS ASIGNACINES 
	  */
	/*
	 * toma una denominación para procesar y asigar/modificar otras asignaciones
	 */
	public void accionOtraAsignacionDenominacion(NomenclaturaDenominacionExt nomenclaturaDenominacion){
		if(!esOtrasAsignacionesEnProceso()){
			nomenclaturaDenominacionProcesar = nomenclaturaDenominacion;
			lbEsEditarOtraAsignacion = true;
			lbEsModificandoOtrasAsignaciones = true;
			AsignacionSalarialExt buscador = new AsignacionSalarialExt();
			buscador.setFlgActivo((short) 1);
			buscador.setCodNomenclaturaDenominacion(nomenclaturaDenominacionProcesar.getCodNomenclaturaDenominacion());
			lstOtrasAsignaciones = ComunicacionServiciosEnt.getASignacionSalarialFitro(buscador);
			RequestContext.getCurrentInstance().scrollTo("frNomenclaturaGeneral:pnlFrmOtrasAsignaciones");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EN_PROCESO);
		}
		
	}
	 /*
	 * Abre la ventana emergenete para agregar otra asignacion a la denominación
	 */
	public void agregarOtraAsignacionDenominacion(){
		asignacionSalarialProcesar = new AsignacionSalarialExt();
		asignacionSalarialProcesar.setAudAccion(codAudAccionInsert);
		asignacionSalarialProcesar.setAudCodRol(codAudCodRol);
		asignacionSalarialProcesar.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
		asignacionSalarialProcesar.setAudFechaActualizacion(new Date());
		asignacionSalarialProcesar.setCodNomenclaturaDenominacion(nomenclaturaDenominacionProcesar.getCodNomenclaturaDenominacion());
		asignacionSalarialProcesar.setFlgActivo((short) 1);
		llValorAsignacion = null;
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacion').show();");
	}
	/*Seleccionar una OtraAsignacion para su edición*/
	public void editarAsignacionDenominacion(AsignacionSalarial asignacion){
		asignacionSalarialProcesar = (AsignacionSalarialExt) asignacion;
		lbEsEditarOtraAsignacion = true;
		lbEsConsultarOtraAsignacion = false;
		llValorAsignacion = asignacionSalarialProcesar.getPorcentajeAsignacion()!=null?asignacionSalarialProcesar.getPorcentajeAsignacion():
			asignacionSalarialProcesar.getValorAsignacion();
		if(asignacionSalarialProcesar.getPorcentajeAsignacion()!=null)
			strOpcionesAsignacion="0";
		else
			strOpcionesAsignacion="1";
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacion').show();");
	}
	/*Seleccionar una OtraAsignacion para su visualizacion*/
	public void verAsignacionDenominacion(AsignacionSalarial asignacion){
		asignacionSalarialProcesar = (AsignacionSalarialExt) asignacion;
		lbEsConsultarOtraAsignacion = true;
		llValorAsignacion = asignacionSalarialProcesar.getPorcentajeAsignacion()!=null?asignacionSalarialProcesar.getPorcentajeAsignacion():
			asignacionSalarialProcesar.getValorAsignacion();
		if(asignacionSalarialProcesar.getPorcentajeAsignacion()!=null)
			strOpcionesAsignacion="0";
		else
			strOpcionesAsignacion="1";
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacion').show();");
	}	
	/*Solicita confirmacion para elimianr OtraAsignacion*/
	public void solicitaConfirmacionEliminarOtraAsignacion(AsignacionSalarial asignacion){
		asignacionSalarialProcesar = (AsignacionSalarialExt) asignacion;
		accionesFalse();
		lbAccionEliminarOtraAsignacion =  true;
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(
				MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_INCREMENTO_SAL_CONFIRMA_ELIMINAR_OTRA_ASIG, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");	
	}
	/*Elimina OtraAsignacionDenominacion una vez confirmada la acción*/
	public void eliminaAsignacionDenominacion(){
		if(asignacionSalarialProcesar.getCodAsignacionSalarial()==null){
			lstOtrasAsignaciones.remove(asignacionSalarialProcesar);
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_ELIMINAR_EXITO);			
		}else{
			asignacionSalarialProcesar.setAudAccion(codAudAccionUpdate);
			asignacionSalarialProcesar.setAudCodRol(codAudCodRol);
			asignacionSalarialProcesar.setAudCodUsuario(BigDecimal.valueOf(codAudusuario));
			asignacionSalarialProcesar.setAudFechaActualizacion(new Date());
			asignacionSalarialProcesar.setFlgActivo((short) 0);
			AsignacionSalarial respuesta = ComunicacionServiciosEnt.setAsignacionSalarial(asignacionSalarialProcesar);
			if(respuesta.isError()){
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_ELIMINAR_ERROR);
				
			}else{
				lstOtrasAsignaciones.remove(asignacionSalarialProcesar);
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
						MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_ELIMINAR_EXITO);
			}
		}
	}
	/*Agrega otra asignacion a la lista OtrasAsignaciones*/
	public void agregarOtraAsignacionLista(){
		if(lstOtrasAsignaciones==null)
			lstOtrasAsignaciones = new ArrayList<AsignacionSalarialExt>();
		if(lstOtrasAsignaciones.contains(asignacionSalarialProcesar)){
			lstOtrasAsignaciones.remove(asignacionSalarialProcesar);
			if("0".equals(strOpcionesAsignacion))
				asignacionSalarialProcesar.setPorcentajeAsignacion(llValorAsignacion);
			else
				asignacionSalarialProcesar.setPorcentajeAsignacion(null);
			lstOtrasAsignaciones.add(asignacionSalarialProcesar);
		}else{
			lstOtrasAsignaciones.add(0, asignacionSalarialProcesar);	
		}
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacion').hide();");
	}
	public void cerrarConsultarAsignacionSalarial (){
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleOtraAsignacion').hide();");
	}
			
	/*Actualiza Otras Asignaciones*/
	public void actualizarOtrasAsignaciones(){
		
	}
	
	public void cacularOtraAsignacion(){
		if(strOpcionesAsignacion!=null){
			if(llValorAsignacion==null){
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_OPCION_INVALIDA);
			}else{
				if("0".equals(strOpcionesAsignacion)){
					asignacionSalarialProcesar.setValorAsignacion( (nomenclaturaDenominacionProcesar.getAsignacionSalarial().multiply(new BigDecimal(llValorAsignacion))).divide(new BigDecimal(100))  .longValue());
				}else if("1".equals(strOpcionesAsignacion)){
					asignacionSalarialProcesar.setValorAsignacion(llValorAsignacion);
				}
			}
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_OA_SELECCIONAR_OPCION);
		}
	}
	
	 public void handleOpcionOtraAsignacion(AjaxBehaviorEvent  e) {
	        
	 }
	 
	public boolean pintarBotonOtrasAsignaciones(NomenclaturaDenominacion denominacion){
		if(denominacion.getCodNomenclaturaDenominacion()==null)
			return false;
		return true;
	}
	
	
	/**
	 * EQUIVALENCIAS
	 */
	
	public void accionSeleccionarNomenclaturaEquivalencias(NomenclaturaExt nomenclaturasel){// line 310
		if(!esNomenclaturaEnProceso()){
			nomenclaturaProcesar = nomenclaturasel;
			this.buscarNormaNomenclatura();
			lbEsEquivalencias = true;
			lbEsModificando = true;
			lblVerDenominaciones = true;
			lbVerFrmNomenclatura = true;
			lbEsConsultaNomenclatura = true;
			cargarDenomincacionesNomenclatura();
			this.setStrMsgAcciones(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ENTIDADES_NOMENCLATURA_EQUIVALENCIAS, getLocale()));
			RequestContext.getCurrentInstance().scrollTo("frNomenclaturaGeneral:PnlFrmNomenclatura");
		}else{
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_EN_PROCESO);
		}	
		
	}
	public void solicitarConfirmacionEquivalenciasFusionar(){
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
		
		accionesFalse();
		lbAccionEquivalenciasFusionar = true;
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(
				MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_CONFIRMAR, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");			
		/*Ningun origen debe tener salario mayor al destino*/
	}
	/**
	 * Solicita confirmacion para cancelar el incremento de escala salarial
	 */
	public void solicitaConfirmacionCancelarEquivalenciasNomenclatura(){
		accionesFalse();
		lbCancelarEquivalencias =  true;	
		this.setStrMsgAccionConfirmar(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_CANCELAR, getLocale()));
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAccion').show();");	
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
	public void equivalenciasFusionar(){
		boolean error = false;
		String origenes = "";
		long destino = 0;
		for (NomenclaturaDenominacionExt dennomenclatura : lstDenominacionesNomenclatura) {
			if (dennomenclatura.isLbOrigenEquivalencia()) 
				origenes = dennomenclatura.getCodNomenclaturaDenominacion() + ",";
			else 
				destino = dennomenclatura.getCodNomenclaturaDenominacion();
		}
		error = fusionarOrigenesADestino(origenes,destino);
		if (!error) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_EXITO);
			inicializarFormulario();
			nomenclaturaDenominacionDestino = new NomenclaturaDenominacionExt();
			
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ENTIDAD_NOMENCLATURA_FUSIONAR_ERROR);
		}
	}
	
	
	 /** Metodo que inhabilita las denominaciones origen seleccionadas para el proceso de equivalencias*/
		public boolean fusionarOrigenesADestino(String origenes, long destino) {
			boolean error = false;
			NomenclaturaExt objNomenclatura = new NomenclaturaExt();
			if(origenes != null)
				objNomenclatura.setOrigenes(origenes.substring(0, origenes.length()-1));
			objNomenclatura.setDenDestino(destino);
			NomenclaturaExt res = ComunicacionServiciosEnt.setNomenclaturaEquivalenciaGeneral(objNomenclatura);
			if (res.isError())
				error = true;
			return error;
		}
	
	
	public void onRowSelect(SelectEvent event) { 
	     logger.error("pepe","asd");

	    } 	
	
	
	/*Accion de modificar nomenclatura
	 * Utilizada para pntar el panel pnlBotonesGuardar y las acciones de las denominaciones*/
	public boolean esAccionModificarNomenclatura(){
		if(lbEsIncrementoSalarial || lbEsEquivalencias)
			return false;
		return true;
	}
	
	/**
	 * Getters y Setters
	 */
	public boolean isLvVerAsociarEntidades() {
		return lvVerAsociarEntidades;
	}

	public void setLvVerAsociarEntidades(boolean lvVerAsociarEntidades) {
		this.lvVerAsociarEntidades = lvVerAsociarEntidades;
	}

	public Nomenclatura getNomenclaturaProcesar() {
		return nomenclaturaProcesar;
	}

	public void setNomenclaturaProcesar(Nomenclatura nomenclaturaProcesar) {
		this.nomenclaturaProcesar = nomenclaturaProcesar;
	}

	public List<NomenclaturaExt> getLstNomenclaturas() {
		return lstNomenclaturas;
	}

	public void setLstNomenclaturas(List<NomenclaturaExt> lstNomenclaturas) {
		this.lstNomenclaturas = lstNomenclaturas;
	}

	public List<NomenclaturaDenominacionExt> getLstDenominacionesNomenclatura() {
		return lstDenominacionesNomenclatura;
	}

	public void setLstDenominacionesNomenclatura(List<NomenclaturaDenominacionExt> lstDenominacionesNomenclatura) {
		this.lstDenominacionesNomenclatura = lstDenominacionesNomenclatura;
	}

	public boolean isLbVerFrmNomenclatura() {
		return lbVerFrmNomenclatura;
	}

	public void setLbVerFrmNomenclatura(boolean lbVerFrmNomenclatura) {
		this.lbVerFrmNomenclatura = lbVerFrmNomenclatura;
	}

	public boolean isLbVerFrmDenominacion() {
		return lbVerFrmDenominacion;
	}

	public void setLbVerFrmDenominacion(boolean lbVerFrmDenominacion) {
		this.lbVerFrmDenominacion = lbVerFrmDenominacion;
	}

	public boolean isLbCrearNorma() {
		return lbCrearNorma;
	}

	public void setLbCrearNorma(boolean lbCrearNorma) {
		this.lbCrearNorma = lbCrearNorma;
	}

	public String getStrMsgAcciones() {
		return strMsgAcciones;
	}

	public void setStrMsgAcciones(String strMsgAcciones) {
		this.strMsgAcciones = strMsgAcciones;
	}

	public NomenclaturaDenominacion getNomenclaturaDenominacionProcesar() {
		return nomenclaturaDenominacionProcesar;
	}

	public void setNomenclaturaDenominacionProcesar(NomenclaturaDenominacionExt nomenclaturaDenominacionProcesar) {
		this.nomenclaturaDenominacionProcesar = nomenclaturaDenominacionProcesar;
	}

	public Norma getNormaSeleccionada() {
		return normaSeleccionada;
	}

	public void setNormaSeleccionada(Norma normaSeleccionada) {
		this.normaSeleccionada = normaSeleccionada;
	}
	
	public boolean isLbFormularioModificado() {
		return lbFormularioModificado;
	}

	public void setLbFormularioModificado(boolean lbFormularioModificado) {
		this.lbFormularioModificado = lbFormularioModificado;
	}
	
	public boolean isLbEsConsultaNomenclatura() {
		return lbEsConsultaNomenclatura;
	}

	public void setLbEsConsultaNomenclatura(boolean lbEsConsultaNomenclatura) {
		this.lbEsConsultaNomenclatura = lbEsConsultaNomenclatura;
	}

	public boolean isLbEsConsultaDenomincacion() {
		return lbEsConsultaDenomincacion;
	}

	public void setLbEsConsultaDenomincacion(boolean lbEsConsultaDenomincacion) {
		this.lbEsConsultaDenomincacion = lbEsConsultaDenomincacion;
	}
	
	public String getStrMsgAccionConfirmar() {
		return strMsgAccionConfirmar;
	}

	public void setStrMsgAccionConfirmar(String strMsgAccionConfirmar) {
		this.strMsgAccionConfirmar = strMsgAccionConfirmar;
	}

	public boolean isLbSalir() {
		return lbSalir;
	}

	public void setLbSalir(boolean lbSalir) {
		this.lbSalir = lbSalir;
	}

	public boolean isLbEsIncrementoSalarial() {
		return lbEsIncrementoSalarial;
	}

	public void setLbEsIncrementoSalarial(boolean lbEsIncrementoSalarial) {
		this.lbEsIncrementoSalarial = lbEsIncrementoSalarial;
	}

	public String getStrOpcionIncremento() {
		return strOpcionIncremento;
	}

	public void setStrOpcionIncremento(String strOpcionIncremento) {
		this.strOpcionIncremento = strOpcionIncremento;
	}

	public BigDecimal getLbgValorIncremento() {
		return lbgValorIncremento;
	}

	public void setLbgValorIncremento(BigDecimal lbgValorIncremento) {
		this.lbgValorIncremento = lbgValorIncremento;
	}

	public List<Entidad> getLstEntidadesDisponibles() {
		return lstEntidadesDisponibles;
	}

	public void setLstEntidadesDisponibles(List<Entidad> lstEntidadesDisponibles) {
		this.lstEntidadesDisponibles = lstEntidadesDisponibles;
	}

	public List<Entidad> getLstEntidadesSeleccionadas() {
		return lstEntidadesSeleccionadas;
	}

	public void setLstEntidadesSeleccionadas(List<Entidad> lstEntidadesSeleccionadas) {
		this.lstEntidadesSeleccionadas = lstEntidadesSeleccionadas;
	}

	public boolean isLbVerFrmAsociar() {
		return lbVerFrmAsociar;
	}

	public void setLbVerFrmAsociar(boolean lbVerFrmAsociar) {
		this.lbVerFrmAsociar = lbVerFrmAsociar;
	}

	public Entidad getEntFiltro() {
		return entFiltro;
	}

	public void setEntFiltro(Entidad entFiltro) {
		this.entFiltro = entFiltro;
	}

	public List<SelectItem> getLstSIDenominaciones() {
		return lstSIDenominaciones;
	}

	public void setLstSIDenominaciones(List<SelectItem> lstSIDenominaciones) {
		this.lstSIDenominaciones = lstSIDenominaciones;
	}

	public Norma getNormaSeleccionadaIncremento() {
		return normaSeleccionadaIncremento;
	}

	public void setNormaSeleccionadaIncremento(Norma normaSeleccionadaIncremento) {
		this.normaSeleccionadaIncremento = normaSeleccionadaIncremento;
	}

	public Norma getNormaIncremento() {
		return normaIncremento;
	}

	public void setNormaIncremento(Norma normaIncremento) {
		this.normaIncremento = normaIncremento;
	}

	public List<AsignacionSalarialExt> getLstOtrasAsignaciones() {
		return lstOtrasAsignaciones;
	}

	public void setLstOtrasAsignaciones(List<AsignacionSalarialExt> lstOtrasAsignaciones) {
		this.lstOtrasAsignaciones = lstOtrasAsignaciones;
	}

	public AsignacionSalarialExt getAsignacionSalarialProcesar() {
		return asignacionSalarialProcesar;
	}

	public void setAsignacionSalarialProcesar(AsignacionSalarialExt asignacionSalarialProcesar) {
		this.asignacionSalarialProcesar = asignacionSalarialProcesar;
	}

	public boolean isLbEsEditarOtraAsignacion() {
		return lbEsEditarOtraAsignacion;
	}

	public void setLbEsEditarOtraAsignacion(boolean lbEsEditarOtraAsignacion) {
		this.lbEsEditarOtraAsignacion = lbEsEditarOtraAsignacion;
	}

	public boolean isLbEsConsultarOtraAsignacion() {
		return lbEsConsultarOtraAsignacion;
	}

	public void setLbEsConsultarOtraAsignacion(boolean lbEsConsultarOtraAsignacion) {
		this.lbEsConsultarOtraAsignacion = lbEsConsultarOtraAsignacion;
	}

	public boolean isLbAccionEliminarOtraAsignacion() {
		return lbAccionEliminarOtraAsignacion;
	}

	public void setLbAccionEliminarOtraAsignacion(boolean lbAccionEliminarOtraAsignacion) {
		this.lbAccionEliminarOtraAsignacion = lbAccionEliminarOtraAsignacion;
	}

	public String getStrOpcionesAsignacion() {
		return strOpcionesAsignacion;
	}

	public void setStrOpcionesAsignacion(String strOpcionesAsignacion) {
		this.strOpcionesAsignacion = strOpcionesAsignacion;
	}

	public Long getLlValorAsignacion() {
		return llValorAsignacion;
	}

	public void setLlValorAsignacion(Long llValorAsignacion) {
		this.llValorAsignacion = llValorAsignacion;
	}

	public boolean isLbEsModificando() {
		return lbEsModificando;
	}

	public void setLbEsModificando(boolean lbEsModificando) {
		this.lbEsModificando = lbEsModificando;
	}
	
	private boolean esNomenclaturaEnProceso(){
		return lbEsModificando;
	}
	private boolean esDenominacionEnProceso(){
		return lbEsmodificandoDenominacion;
	}
	private boolean esOtrasAsignacionesEnProceso(){
		return lbEsModificandoOtrasAsignaciones;
	}

	public boolean isLbEsEquivalencias() {
		return lbEsEquivalencias;
	}

	public void setLbEsEquivalencias(boolean lbEsEquivalencias) {
		this.lbEsEquivalencias = lbEsEquivalencias;
	}

	public NomenclaturaDenominacionExt getNomenclaturaDenominacionDestino() {
		return nomenclaturaDenominacionDestino;
	}

	public void setNomenclaturaDenominacionDestino(NomenclaturaDenominacionExt nomenclaturaDenominacionDestino) {
		this.nomenclaturaDenominacionDestino = nomenclaturaDenominacionDestino;
	}

	public boolean isLbConsultaDenominacion() {
		return lbConsultaDenominacion;
	}

	public void setLbConsultaDenominacion(boolean lbConsultaDenominacion) {
		this.lbConsultaDenominacion = lbConsultaDenominacion;
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
	 * @return the listaPlantasNomGeneral
	 */
	public List<NomenclaturaExt> getListaPlantasNomGeneral() {
		return listaPlantasNomGeneral;
	}

	/**
	 * @param listaPlantasNomGeneral the listaPlantasNomGeneral to set
	 */
	public void setListaPlantasNomGeneral(List<NomenclaturaExt> listaPlantasNomGeneral) {
		this.listaPlantasNomGeneral = listaPlantasNomGeneral;
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
	 * @return the lblVerDenominaciones
	 */
	public Boolean getLblVerDenominaciones() {
		return lblVerDenominaciones;
	}

	/**
	 * @param lblVerDenominaciones the lblVerDenominaciones to set
	 */
	public void setLblVerDenominaciones(Boolean lblVerDenominaciones) {
		this.lblVerDenominaciones = lblVerDenominaciones;
	}

	/**
	 * @return the lblverBotones
	 */
	public Boolean getLblverBotones() {
		return lblverBotones;
	}

	/**
	 * @param lblverBotones the lblverBotones to set
	 */
	public void setLblverBotones(Boolean lblverBotones) {
		this.lblverBotones = lblverBotones;
	}

	/**
	 * @return the strMensajeAccion
	 */
	public String getStrMensajeAccion() {
		return strMensajeAccion;
	}

	/**
	 * @param strMensajeAccion the strMensajeAccion to set
	 */
	public void setStrMensajeAccion(String strMensajeAccion) {
		this.strMensajeAccion = strMensajeAccion;
	}

	public Long getCodNivelAnt() {
		return codNivelAnt;
	}

	public void setCodNivelAnt(Long codNivelAnt) {
		this.codNivelAnt = codNivelAnt;
	}

	public Long getCodDenAnt() {
		return codDenAnt;
	}

	public void setCodDenAnt(Long codDenAnt) {
		this.codDenAnt = codDenAnt;
	}


	public Long getCodGradoAnt() {
		return codGradoAnt;
	}

	public void setCodGradoAnt(Long codGradoAnt) {
		this.codGradoAnt = codGradoAnt;
	}

	public String getCodCodAnt() {
		return codCodAnt;
	}

	public void setCodCodAnt(String codCodAnt) {
		this.codCodAnt = codCodAnt;
	}

	public Boolean getFlgIsGnrlTerritorial() {
		return flgIsGnrlTerritorial;
	}

	public void setFlgIsGnrlTerritorial(Boolean flgIsGnrlTerritorial) {
		this.flgIsGnrlTerritorial = flgIsGnrlTerritorial;
	}
	
}
