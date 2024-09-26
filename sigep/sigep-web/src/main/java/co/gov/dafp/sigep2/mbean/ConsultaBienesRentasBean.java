/**
 * 
 */
package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.datamodel.DeclaracionLazyDataModel;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.HistoricoEntidadesDeclaracion;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.bienesrentas.DeclaracionRentaBeanPrint;
import co.gov.dafp.sigep2.mbean.bienesrentas.Utilidad;
import co.gov.dafp.sigep2.mbean.ext.BienesPatrimonioExt;
import co.gov.dafp.sigep2.mbean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.mbean.ext.DeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.IngresosDeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionJuntaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaParentescoExt;
import co.gov.dafp.sigep2.mbean.ext.RecursoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosBR;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.FuncionExtraBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.UtilidadesFaces;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@ViewScoped
@ManagedBean
@Named
public class ConsultaBienesRentasBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 306164432451298493L;
	protected static Logger logger = Logger.getInstance(ConsultaBienesRentasBean.class);

	/**
	 * Validacion seguridad de los botones
	 */
	private boolean btnBuscarBRServidorPublicoDeshabilitado;
	private boolean btnVerDetalleBRDeshabilitado;
	private boolean btnExportarDetalleBRDeshabilitado;
	private boolean btnImprimirDetalleBRDeshabilitado;
	private boolean btnNuevaBusquedaBRDeshabilitado;

	/**
	 * Mensajes de Validacion seguridad de roles
	 */
	private String btnBuscarBRServidorPublicoMensaje;
	private String btnVerDetalleBRMensaje;
	private String btnExportarDetalleBRMensaje;
	private String btnImprimirDetalleBRMensaje;
	private String btnNuevaBusquedaBRMensaje;

	private LazyDataModel<DeclaracionExt> listDecM;
	private LazyDataModel<DeclaracionExt> listDecP;
	private DeclaracionExt consultar 				= new DeclaracionExt();
	private DeclaracionExt consultarCP 				= new DeclaracionExt();
	private DeclaracionExt declaracionSeleccionada 	= new DeclaracionExt();
	private List<DeclaracionExt> contar 			= new ArrayList<DeclaracionExt>();
	private boolean activos;
	private boolean extemporanea;
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	private ExternalContext contexto;
	private String PAGE_CANCEL = "../persona/informacionPersonal.xhtml?recursoId=HojaDeVidaSubMenu";
	private EntidadDTO entidadUsuario = new EntidadDTO();
	private String mensaje;
	/*private boolean rolaprovado = false;*/
	private boolean modificada = false;
	private int totalDeclaraciones;
	private final static Long COD_PARAM_SALARIOS_LABORAL 		= Long.parseLong("749");
	private final static Long COD_PARAM_OTROS_INGRESOS 			= Long.parseLong("1218");
	private final static Long COD_PARAM_CESANTIAS 				= Long.parseLong("750");
	private final static Long COD_PARAM_GASTOS_REPRESENTACION 	= Long.parseLong("751");
	private final static Long COD_PARAM_ARRIENDOS 				= Long.parseLong("752");
	private final static Long COD_PARAM_HONORARIOS 				= Long.parseLong("753");
	
	private DeclaracionRentaBeanPrint declaracionParaImprimir;
	private List<DeclaracionRentaBeanPrint> listaDeclaracionParaImprimir;
	private List<Entidad> entidades;
	private int entidadSelect;
	private boolean variasEntidades;
	private List<SelectItem> lisEntida = new ArrayList<SelectItem>();
	private List<SelectItem> lisEntidadesHistorico = new ArrayList<SelectItem>();
	private boolean visibilidadConsulta = false;
	private String rutaPdf;
	private String tituloVentana;
	private boolean habilitadoTxtEntidad, lbEsAdminFunc;
	/*private boolean entidadesVarias;*/

	public ConsultaBienesRentasBean() {
		try {
			contexto = FacesContext.getCurrentInstance().getExternalContext();
			usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
			entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
			entidades = ComunicacionServiciosSis.getEntidadCodPersona(usuarioSesion.getId());
			variasEntidades=entidades.size()>1?true:false;
			cargarListaEntidades();		
			
			try {
				habilitadoTxtEntidad = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES,RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.AUDITOR);
				lbEsAdminFunc        = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL);
			} catch (SIGEP2SistemaException e1) {
				e1.printStackTrace();
			}

			setBtnBuscarBRServidorPublicoDeshabilitado(this.validarFuncionalidadDeshabilitada("btnBuscarBRServidorPublico"));
			setBtnBuscarBRServidorPublicoMensaje(isBtnBuscarBRServidorPublicoDeshabilitado() ? "Deshabilitado por seguridad Roles" : "");
			
			setBtnVerDetalleBRDeshabilitado(this.validarFuncionalidadDeshabilitada("btnVerDetalleBR"));
			setBtnVerDetalleBRMensaje(isBtnVerDetalleBRDeshabilitado() ? "Deshabilitado por seguridad Roles" : "Ver");
			
			setBtnExportarDetalleBRDeshabilitado(this.validarFuncionalidadDeshabilitada("btnExportarDetalleBR"));
			setBtnExportarDetalleBRMensaje(isBtnExportarDetalleBRDeshabilitado() ? "Deshabilitado por seguridad Roles" : "Exportar");
			
			setBtnImprimirDetalleBRDeshabilitado(this.validarFuncionalidadDeshabilitada("btnImprimirDetalleBR"));
			setBtnImprimirDetalleBRMensaje(isBtnImprimirDetalleBRDeshabilitado() ? "Deshabilitado por seguridad Roles" : "Imprimir");
			
			setBtnNuevaBusquedaBRDeshabilitado(this.validarFuncionalidadDeshabilitada("btnNuevaBusquedaBR"));
			setBtnNuevaBusquedaBRMensaje(isBtnNuevaBusquedaBRDeshabilitado() ? "Deshabilitado por seguridad Roles" : "");
			
			@SuppressWarnings("unchecked")
			List<RolDTO> roles = (List<RolDTO>) contexto.getSessionMap().get("rolesUsuarioSesion");
			DeclaracionExt consultP1 = new DeclaracionExt();
			consultP1.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
			consultP1.setConfirmacion((short) 1);
			consultP1.setLimitIni(0);
			consultP1.setLimitFin(200);
			contar = ComunicacionServiciosBR.getDeclaracionFiltro(consultP1);
			totalDeclaraciones = contar.size();

			DeclaracionExt consultP = new DeclaracionExt();
			consultP.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
			consultP.setConfirmacion((short) 1);
			listDecP = new DeclaracionLazyDataModel(consultP);
			this.visibilidadConsulta = false;
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_BYR);
		} catch (Exception e) {
			logger.error("context", e);
		}
	}

	/**
	 * @author Jhon Garcia
	 * Método que verifica si un botón está habilitado para el usuario de acuerdo al rol y a la funcionalidad
	 */
	@SuppressWarnings("unchecked")
	protected boolean validarFuncionalidadDeshabilitada(String idBoton) {
		/*Consultamos los componentes del módulo con un servicio a bd()*/
		FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String recursoId = paramMap.get("recursoId");
		
		List<RecursoExt> idCompoments= new ArrayList<RecursoExt>();
		List<RolDTO> rolesUsuario = (List<RolDTO>) contexto.getSessionMap().get("rolesUsuarioSesion");
		int [] idRoles = new int[rolesUsuario.size()];
		
		for(int i = 0; i < rolesUsuario.size(); i++) {
			idRoles[i] = (int) rolesUsuario.get(i).getId();
		}
		
		RecursoExt recurso = new RecursoExt();
		recurso.setCodigoVentana(recursoId);
		recurso.setCodRolList(idRoles);
		idCompoments = ComunicacionServiciosSis.getRecursoList(recurso);
		
		for(RecursoExt componentId:idCompoments){
			if(idBoton.equals(componentId.getIdBoton())) {
				if(componentId.getFlgEstado() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void cancelar() {
		UtilidadesFaces.redirect(PAGE_CANCEL);
	}

	/**
	 * :)
	 */
	public void getDeclaracionseConsulta() {
		consultar = limpiarBean(consultar);
		if (consultar.getFechaFniD() == null && consultar.getFechaIniD() == null && consultar.getFechaInicio() == null
				&& consultar.getFechaFin() == null && (consultar.getNumeroIdentificacion() == null || consultar.getNumeroIdentificacion().isEmpty())
				&& consultar.getEntidadReceptora() == null && consultar.getApellidos() == null
				&& consultar.getNombres() == null && consultar.getCodTipoDeclaracion() == null
				&& consultar.getCodTipoDocumento() == null && !activos && !modificada && !extemporanea
				&& consultar.getPeriodoInicial()==null && consultar.getPeriodoFinal()==null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_FILTRO_CONSULTA_HISTORICO_CM);
			return;
		}
		
		if(consultar!=null && consultar.getPeriodoInicial()!=null && consultar.getPeriodoFinal()!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(consultar.getPeriodoInicial());
			int periodoinicial = calendar.get(Calendar.YEAR);
			calendar.setTime(consultar.getPeriodoFinal());
			int periodoFinal = calendar.get(Calendar.YEAR);
			if(periodoinicial > periodoFinal){
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_CONSULTAR_PERIODS_INVALIDOS);
				return;				
			}
		}
		
		if(consultar.getNumeroIdentificacion() != null && consultar.getNumeroIdentificacion().isEmpty())
			consultar.setNumeroIdentificacion(null);
		Utilidad u = new Utilidad();
		if(consultar.getFechaIniD() != null && consultar.getFechaFniD() != null && consultar.getFechaIniD().compareTo(consultar.getFechaFniD()) == 1) 
			u.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_CONSULTAR_FECHAS_DECLARACION_INVALIDAS);
		if(consultar.getFechaInicio() != null && consultar.getFechaFin() != null && consultar.getFechaInicio().compareTo(consultar.getFechaFin()) == 1) 
			u.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.BR_CONSULTAR_FECHAS_DECLARACION_INVALIDAS);
		if (activos || !lbEsAdminFunc ) {
			consultar.setCodEntidad(new BigDecimal(getEntidadUsuario().getId()));
			consultar.setFlgAdm(0);
		}else {
			consultar.setFlgAdm(1);
		}
		
		if (extemporanea) {
			consultar.setFlgExtemporanea((short) 1);
		}else {
			consultar.setFlgExtemporanea((short) 0);
		}
		if (modificada) {
			consultar.setFlgModificado((short) 1);
		}else {
			consultar.setFlgModificado((short) 0);
		}
		consultar.setConfirmacion((short) 1);
		consultar.setCodUsuario(usuarioSesion.getId());
		consultar.setCodRol(this.usuarioSesion.getCodRol());
		
	
		
		listDecM = new DeclaracionLazyDataModel(consultar);
		contexto.getSessionMap().put("decCnsulta", consultar);
		this.visibilidadConsulta = true;
	}

	/**
	 * @param declaracionExt
	 * @return
	 */
	private DeclaracionExt limpiarBean(DeclaracionExt declaracionExt) {
		try {
			if (declaracionExt.getNombres().isEmpty()) {
				declaracionExt.setNombres(null);
			}
			if (declaracionExt.getApellidos().isEmpty()) {
				declaracionExt.setApellidos(null);
			}
			if (declaracionExt.getEntidadReceptora().isEmpty()) {
				declaracionExt.setEntidadReceptora(null);
			}
			if (declaracionExt.getNumeroIdentificacion().isEmpty()) {
				declaracionExt.setNumeroIdentificacion(null);
			}
			return declaracionExt;
		} catch (NullPointerException e) {
			return declaracionExt;
		}
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

	public void construirModeloJasper(DeclaracionExt dec, byte b) {
		try {
			contexto 								= FacesContext.getCurrentInstance().getExternalContext();
			declaracionParaImprimir 				= new DeclaracionRentaBeanPrint();
			listaDeclaracionParaImprimir 			= new ArrayList<>();
			PersonaParentescoExt parentesco 		= new PersonaParentescoExt();
			IngresosDeclaracionExt ingresoLaboral 	= new IngresosDeclaracionExt();
			BienesPatrimonioExt bienPatrimonio 		= new BienesPatrimonioExt();
			declaracionParaImprimir 				= new DeclaracionRentaBeanPrint();
			listaDeclaracionParaImprimir 			= new ArrayList<>();
			long codigoPersonaBuscada = 0;
			codigoPersonaBuscada = (b == 0) ? dec.getCodPersona().longValue() : usuarioSesion.getCodPersona();
			
			DatoContactoExt objDatoContacto = new DatoContactoExt();
			objDatoContacto.setCodDeclaracion(dec.getCodDeclaracion());
			objDatoContacto.setCodPersona(dec.getCodPersona());
			DatoContactoExt datosContacto = ComunicacionServiciosBR.getdDatoContactoExt(objDatoContacto);
			
			PersonaExt datosPersonales = ComunicacionServiciosHV.getpersonacontacto(codigoPersonaBuscada);	
			datosPersonales.setDireccionResidencia(getDireccionResidencia(datosPersonales));
			datosPersonales.setNumCelular(getNumeroTelefonicoP(datosPersonales));
			
			if(datosContacto!=null) {
				if (datosContacto.getCodPaisResidencia()!=null ) {
					datosPersonales.setNombrePaisResidencia(datosContacto.getNombrePais());
				}
				if (datosContacto.getCodDepartamentoResidencia()!=null) {
					datosPersonales.setNombreDepartamentoResidencia(datosContacto.getNombreDepartamento());
				}
				if (datosContacto.getCodMunicipioResidencia()!=null) {
					datosPersonales.setNombreMunicipioResidencia(datosContacto.getNombreMunicipio());
				}	
				if(datosContacto.getNumCelular()!=null ) {
					datosPersonales.setNumCelular(getNumeroTelefonico(datosContacto));
				}
				if(datosContacto.getDireccionResidencia()!=null) {
					datosPersonales.setDireccionResidencia(getDireccionResidenciaGenerada(datosContacto));
				}
			}
			declaracionParaImprimir.setDatosPersonales(datosPersonales);
			
			declaracionParaImprimir.setDetalleDeclaracion(ComunicacionServiciosBR.getdeclaracionid(codigoPersonaBuscada));
			parentesco.setFlgActivo(Short.parseShort("1"));
			parentesco.setCodDeclaracion(dec.getCodDeclaracion().longValue());
			parentesco.setCodPersona(new BigDecimal(codigoPersonaBuscada));
			parentesco.setCodDeclaracion(dec.getCodDeclaracion().longValue());
			parentesco.setCodTipoParentesco(TipoParametro.HIJO.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
				declaracionParaImprimir.getListaParentesco().add(pariente);
			parentesco.setCodTipoParentesco(TipoParametro.MADRE.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
				declaracionParaImprimir.getListaParentesco().add(pariente);
			parentesco.setCodTipoParentesco(TipoParametro.HERMANO.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
				declaracionParaImprimir.getListaParentesco().add(pariente);
			parentesco.setCodTipoParentesco(TipoParametro.PADRE.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
				declaracionParaImprimir.getListaParentesco().add(pariente);
			parentesco.setCodTipoParentesco(TipoParametro.COMPANERO_PERMANENTE.getValue());
			for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
				declaracionParaImprimir.getListaParentesco().add(pariente);			
			parentesco.setCodTipoParentesco(TipoParametro.CONYUGUE.getValue());
			for (PersonaParentescoExt conyugue : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
				declaracionParaImprimir.setConyugue(conyugue);
			if(this.declaracionParaImprimir.getConyugue() == null || this.declaracionParaImprimir.getConyugue().getCodPersona() == null) {
				parentesco.setCodTipoParentesco(TipoParametro.COMPANERO_PERMANENTE.getValue());
				for (PersonaParentescoExt conyugue : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
					this.declaracionParaImprimir.setConyugue(conyugue);
			}
			ingresoLaboral.setCodDeclaracion(dec.getCodDeclaracion().longValue());
			ingresoLaboral.setFlgActivo(1);
			declaracionParaImprimir.setListaIngresosLaborales(ComunicacionServiciosBR.getingresosdeclaracion(ingresoLaboral));
			declaracionParaImprimir.setListaOtrosIngresos(ComunicacionServiciosBR.getotrosingresosdeclaracion(dec.getCodDeclaracion().longValue(), 1));
			declaracionParaImprimir.setListaCuentasDeclaracion(ComunicacionServiciosBR.getcuentasdeclaracion(dec.getCodDeclaracion().longValue(), 1));
			BigDecimal totalIngresos = ComunicacionServiciosBR.getSumaIngresos(dec.getCodDeclaracion().longValue()).getValor();
			declaracionParaImprimir.setTotalIngresoLaboral(totalIngresos == null ?  new BigDecimal(0) : totalIngresos);
			BigDecimal otrosIngresos = ComunicacionServiciosBR.getSumaOtrosIngresos(dec.getCodDeclaracion().longValue()).getValor();
			declaracionParaImprimir.setTotalOtrosIngresos(otrosIngresos == null ? new BigDecimal(0) : otrosIngresos);
			BigDecimal totalCesantias = new BigDecimal(0);
			declaracionParaImprimir.setTotalCesantias(totalCesantias == null ? new BigDecimal(0) : totalCesantias);
			BigDecimal gastosRepresentacion = new BigDecimal(0);
			declaracionParaImprimir.setTotalGastosRepresentacion(gastosRepresentacion == null ? new BigDecimal(0) : gastosRepresentacion);
			BigDecimal totalArriendos = new BigDecimal(0);
			declaracionParaImprimir.setTotalArriendos(totalArriendos == null ? new BigDecimal(0) : totalArriendos);
			BigDecimal sumaIngresos = new BigDecimal(0);
			declaracionParaImprimir.setTotalHonorarios(sumaIngresos == null ? new BigDecimal(0) : sumaIngresos);
			declaracionParaImprimir.sumarTotales();
			declaracionParaImprimir.setListaCuentasDeclaracion(ComunicacionServiciosBR.getcuentasdeclaracion(dec.getCodDeclaracion().longValue(), 1));
			declaracionParaImprimir.setListaAcreenciasObligaciones(ComunicacionServiciosBR.getacreenciaobligacion(dec.getCodDeclaracion().longValue(), 1));
			bienPatrimonio.setFlgActivo((short) 1);
			bienPatrimonio.setCodDeclaracion(new BigDecimal(dec.getCodDeclaracion().longValue()));
			declaracionParaImprimir.setListaBienesPatrimoniales(ComunicacionServiciosBR.getBienesPatrimonio(bienPatrimonio));
			declaracionParaImprimir.setListaActividadesEconomicas(ComunicacionServiciosBR.getactividadprivada(dec.getCodDeclaracion().longValue(), 1));
			declaracionParaImprimir.setListaParticipacionesJuntas(ComunicacionServiciosBR.getparticipacionjunta(dec.getCodDeclaracion().longValue(), 1));
			declaracionParaImprimir.setListaParticipacionesSocio(ComunicacionServiciosBR.getparticipacionjunta(dec.getCodDeclaracion().longValue(), 1));
			listaDeclaracionParaImprimir.add(declaracionParaImprimir);
		} catch (Exception ex) {
			logger.error(ex.getMessage() + " construirModeloJasper() ConsultaBienesRentasBean" , ex);
		}
	}
	
	private String getNumeroTelefonico(DatoContactoExt datoContacto)
	{
		if( datoContacto != null & datoContacto.getNumCelular() == null ) {
			return "";
		}else if( datoContacto != null & datoContacto.getNumCelular()!=null && datoContacto.getTelefonoResidencia()!=null) 
		{
			if(datoContacto != null & datoContacto.getNumCelular().equals(datoContacto.getTelefonoResidencia())) {
				return datoContacto.getNumCelular();
			}else {
				return datoContacto.getNumCelular() + " / " + datoContacto.getTelefonoResidencia();
			}
			
		}else if(datoContacto.getTelefonoResidencia()== null) {
			return datoContacto.getNumCelular();
		}else {
			return datoContacto.getTelefonoResidencia();
		}
	}
	
	
	private String getNumeroTelefonicoP(PersonaExt datosPersonales)
	{
		if( datosPersonales != null & datosPersonales.getNumCelular() == null ) {
			return "";
		}else if( datosPersonales != null & datosPersonales.getNumCelular()!=null && datosPersonales.getTelefonoResidencia()!=null) 
		{
			if(datosPersonales != null & datosPersonales.getNumCelular().equals(datosPersonales.getTelefonoResidencia())) {
				return datosPersonales.getNumCelular();
			}else {
				return datosPersonales.getNumCelular() + " / " + datosPersonales.getTelefonoResidencia();
			}
			
		}else if(datosPersonales.getTelefonoResidencia()== null) {
			return datosPersonales.getNumCelular();
		}else {
			return datosPersonales.getTelefonoResidencia();
		}
	}
	
	/**Validación residencia*/
	private String getDireccionResidencia(PersonaExt datosPersonales) {
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
	
	
	private String getDireccionResidenciaGenerada(DatoContactoExt datoContacto) {
		String direccionGenerada = "";
		if(datoContacto.getDireccionResidencia() != null) {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(datoContacto.getDireccionResidencia(), EditarDireccionDTO.class);
				if (direccion != null) {
					direccionGenerada = direccion.getDireccionGenerada();
				}
			} catch (JsonSyntaxException e) {
	
			}
		}
		return direccionGenerada;
	}
	
	
	public DeclaracionRentaBeanPrint construirModeloJasperOLD(DeclaracionExt dec, byte b) {
		contexto = FacesContext.getCurrentInstance().getExternalContext();
		declaracionParaImprimir = new DeclaracionRentaBeanPrint();
		listaDeclaracionParaImprimir = new ArrayList<>();
		PersonaParentescoExt parentesco = new PersonaParentescoExt();
		IngresosDeclaracionExt ingresoLaboral = new IngresosDeclaracionExt();
		BienesPatrimonioExt bienPatrimonio = new BienesPatrimonioExt();
		declaracionParaImprimir = new DeclaracionRentaBeanPrint();
		listaDeclaracionParaImprimir = new ArrayList<>();
		long codigoPersonaBuscada = 0;
		codigoPersonaBuscada = (b == 0) ? dec.getCodPersona().longValue() : usuarioSesion.getCodPersona();
		declaracionParaImprimir.setDatosPersonales(ComunicacionServiciosHV.getpersonacontacto(codigoPersonaBuscada));
		declaracionParaImprimir.setDetalleDeclaracion(ComunicacionServiciosBR.getdeclaracionid(codigoPersonaBuscada));
		parentesco.setFlgActivo(Short.parseShort("1"));
		parentesco.setCodDeclaracion(dec.getCodDeclaracion().longValue());
		parentesco.setCodPersona(new BigDecimal(codigoPersonaBuscada));
		parentesco.setCodDeclaracion(dec.getCodDeclaracion().longValue());
		parentesco.setCodTipoParentesco(1196);
		for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
			declaracionParaImprimir.getListaParentesco().add(pariente);
		parentesco.setCodTipoParentesco(1197);
		for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
			declaracionParaImprimir.getListaParentesco().add(pariente);
		parentesco.setCodTipoParentesco(1198);
		for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco)) 
			declaracionParaImprimir.getListaParentesco().add(pariente);
		parentesco.setCodTipoParentesco(1199);
		for (PersonaParentescoExt pariente : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
			declaracionParaImprimir.getListaParentesco().add(pariente);
		parentesco.setCodTipoParentesco(707);
		for (PersonaParentescoExt conyugue : ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco))
			declaracionParaImprimir.setConyugue(conyugue);
		ingresoLaboral.setCodDeclaracion(dec.getCodDeclaracion().longValue());
		ingresoLaboral.setFlgActivo(1);
		declaracionParaImprimir.setListaIngresosLaborales(ComunicacionServiciosBR.getingresosdeclaracion(ingresoLaboral));
		declaracionParaImprimir.setListaOtrosIngresos(ComunicacionServiciosBR.getotrosingresosdeclaracion(dec.getCodDeclaracion().longValue(), 1));
		declaracionParaImprimir.setListaCuentasDeclaracion(ComunicacionServiciosBR.getcuentasdeclaracion(dec.getCodDeclaracion().longValue(), 1));
		BigDecimal totalIngresosLaborales = ComunicacionServiciosBR.getSumaIngresos(dec.getCodDeclaracion().longValue()).getValor();
		declaracionParaImprimir.setTotalIngresoLaboral(totalIngresosLaborales == null ? new BigDecimal(0) : totalIngresosLaborales);
		BigDecimal totalOtrosIngresos = ComunicacionServiciosBR.getSumaOtrosIngresos(dec.getCodDeclaracion().longValue()).getValor();
		declaracionParaImprimir.setTotalOtrosIngresos(totalOtrosIngresos == null ? new BigDecimal(0) : totalOtrosIngresos);
		BigDecimal totaCesantias = new BigDecimal(0);//ComunicacionServiciosBR.getSumaIngresos(dec.getCodDeclaracion().longValue(), COD_PARAM_CESANTIAS).getValor();
		declaracionParaImprimir.setTotalCesantias(totaCesantias == null ? new BigDecimal(0) : totaCesantias);
		BigDecimal totalRepresentacion = new BigDecimal(0);//ComunicacionServiciosBR.getSumaIngresos(dec.getCodDeclaracion().longValue(), COD_PARAM_GASTOS_REPRESENTACION).getValor();
		declaracionParaImprimir.setTotalGastosRepresentacion(totalRepresentacion == null ? new BigDecimal(0) : totalRepresentacion);
		BigDecimal sumaIngresos = new BigDecimal(0);//ComunicacionServiciosBR.getSumaIngresos(dec.getCodDeclaracion().longValue(), COD_PARAM_ARRIENDOS).getValor();
		declaracionParaImprimir.setTotalArriendos(sumaIngresos == null ? new BigDecimal(0) : sumaIngresos);
		BigDecimal totalHonorarios = new BigDecimal(0);//ComunicacionServiciosBR.getSumaIngresos(dec.getCodDeclaracion().longValue(), COD_PARAM_HONORARIOS).getValor();
		declaracionParaImprimir.setTotalHonorarios(totalHonorarios == null ? new BigDecimal(0) : totalHonorarios);
		declaracionParaImprimir.sumarTotales();
		declaracionParaImprimir.setListaCuentasDeclaracion(ComunicacionServiciosBR.getcuentasdeclaracion(dec.getCodDeclaracion().longValue(), 1));
		declaracionParaImprimir.setListaAcreenciasObligaciones(ComunicacionServiciosBR.getacreenciaobligacion(dec.getCodDeclaracion().longValue(), 1));
		bienPatrimonio.setFlgActivo((short) 1);
		bienPatrimonio.setCodDeclaracion(new BigDecimal(dec.getCodDeclaracion().longValue()));
		declaracionParaImprimir.setListaBienesPatrimoniales(ComunicacionServiciosBR.getBienesPatrimonio(bienPatrimonio));
		declaracionParaImprimir.setListaActividadesEconomicas(ComunicacionServiciosBR.getactividadprivada(dec.getCodDeclaracion().longValue(), 1));
		
		List<ParticipacionJuntaExt> participacionCorporaciones = ComunicacionServiciosBR.getparticipacionjunta(dec.getCodDeclaracion().longValue(), 1);
		
		declaracionParaImprimir.setListaParticipacionesJuntas(participacionCorporaciones);
		declaracionParaImprimir.setListaParticipacionesSocio(participacionCorporaciones);
		declaracionParaImprimir.setDetalleDeclaracion(dec);
		return declaracionParaImprimir;
	}

	public void validarEntidadesVisualizarEImprimir(DeclaracionExt dec, byte b) {
		if(dec.isTipoDecImpVariasEntidades()  && b==1 && entidades.size()>1) {
			/*822 ingreso 741 periodica*/
			declaracionSeleccionada = dec;
			
			HistoricoEntidadesDeclaracion buscador = new HistoricoEntidadesDeclaracion();
			buscador.setCodDeclaracion(declaracionSeleccionada.getCodDeclaracion());
			List<HistoricoEntidadesDeclaracion> lista = ComunicacionServiciosBR.geHistoricoEntidadesDeclaracionFiltro(buscador);
			lisEntidadesHistorico  = new ArrayList<SelectItem>();
			if(!lista.isEmpty()){
				for (HistoricoEntidadesDeclaracion aux : lista) {
					lisEntidadesHistorico.add(new SelectItem(aux.getCodEntidad().toString(), aux.getNombreEntidad()));
				}
			}			
			RequestContext.getCurrentInstance().execute("$('#pdEntidadesUsuario').modal('show');");
		}else {
			visualizarEimprimir(dec,b);
		}
	}
	
	public void seleccionarEntidadImpresion(int codentidad) {
		for(Entidad ent:entidades) {
			if (entidadSelect == ent.getCodEntidad().intValue()){
				declaracionSeleccionada.setEntidadReceptora(ent.getNombreEntidad());
			}
		}
	}
	
	public void visualizarEImprimirxEntidad() {

		visualizarEimprimir(declaracionSeleccionada,(byte) 1);
	}
	
	public void visualizarEimprimir(DeclaracionExt dec, byte b) {
		construirModeloJasper(dec, b);
		listaDeclaracionParaImprimir.get(0).setDetalleDeclaracion(dec);
		Boolean valid = FuncionExtraBean.verJasperPDF("bienesrentas/FormatoBienesRentas.jasper", contexto, listaDeclaracionParaImprimir, false, "DeclaraciónBienesYRentas" + UtilidadesFaces.eliminarCaracteresEspeciales(dec.getNombreTipoDeclaracion()) + dec.getCodDeclaracion()+ DateUtils.formatearACadena(dec.getFechaPresentacion(), "dd-MM-yyyy"));
		if (valid) {
			logger.log().info("Generado", "Visualizar PDF");
		} 
		else {
			logger.log().error("No se pudo completar la solicitud", "Visualizar PDF");
		}
	}

	public void exportarPDF(DeclaracionExt dec, byte b) {
		construirModeloJasper(dec, b);
		String das = "DeclaraciónBienesYRentas_" + UtilidadesFaces.eliminarCaracteresEspeciales(
				listaDeclaracionParaImprimir.get(0).getDetalleDeclaracion().getNombreTipoDeclaracion()) + "";
		das += listaDeclaracionParaImprimir.get(0).getDetalleDeclaracion().getAnnoDeclaracion();
		String url = FuncionExtraBean.mostrarJasperPDFName(contexto, listaDeclaracionParaImprimir, false, das);
		url = url.replace("/bienesrentas/", "");
		url = "location.href='" + url + "';";
		RequestContext.getCurrentInstance().execute(url);
	}

	public void exportarDeclaracion(DeclaracionExt dec, byte b) {
		construirModeloJasper(dec, b);
		String nombreArchivo = "DeclaracionBienesYRentas"
				+ UtilidadesFaces.eliminarCaracteresEspeciales(declaracionSeleccionada.getNombreTipoDeclaracion())
				+ declaracionSeleccionada.getCodDeclaracion()
				+ DateUtils.formatearACadena(dec.getFechaPresentacion(), "dd-MM-yyyy");
		listaDeclaracionParaImprimir.get(0).setDetalleDeclaracion(dec);
		FuncionExtraBean.descargarJasperPDF("bienesrentas/FormatoBienesRentas.jasper", contexto,
				listaDeclaracionParaImprimir, false, nombreArchivo);
	}

	public List<DeclaracionRentaBeanPrint> coinstruirModeloJasperTodas(ExternalContext contexto) {
		declaracionParaImprimir = new DeclaracionRentaBeanPrint();
		List<DeclaracionRentaBeanPrint> listaDeclaracionParaImprimir = new ArrayList<>();
		PersonaParentescoExt parentesco = new PersonaParentescoExt();
		IngresosDeclaracionExt ingresoLaboral = new IngresosDeclaracionExt();
		BienesPatrimonioExt bienPatrimonio = new BienesPatrimonioExt();
		DeclaracionExt filtro = new DeclaracionExt();
		filtro.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
		filtro.setConfirmacion((short) 1);
		filtro.setLimitIni(0);
		filtro.setLimitFin(200);
		List<DeclaracionExt> listDecP = ComunicacionServiciosBR.getDeclaracionFiltro(filtro);
		for (DeclaracionExt declaracionExt : listDecP) {
			declaracionSeleccionada = declaracionExt;
			declaracionParaImprimir
					.setDatosPersonales(ComunicacionServiciosHV.getpersonacontacto(usuarioSesion.getCodPersona()));

			declaracionParaImprimir.setDetalleDeclaracion(
					ComunicacionServiciosBR.getdeclaracionid(declaracionSeleccionada.getCodDeclaracion().longValue()));

			parentesco.setFlgActivo(Short.parseShort("1"));
			parentesco.setCodDeclaracion(declaracionSeleccionada.getCodDeclaracion().longValue());
			declaracionParaImprimir.setListaParentesco(ComunicacionServiciosBR.getPersonaParentescoPersona(parentesco));

			ingresoLaboral.setCodDeclaracion(declaracionSeleccionada.getCodDeclaracion().longValue());
			ingresoLaboral.setFlgActivo(1);
			declaracionParaImprimir
					.setListaIngresosLaborales(ComunicacionServiciosBR.getingresosdeclaracion(ingresoLaboral));

			declaracionParaImprimir.setListaOtrosIngresos(ComunicacionServiciosBR
					.getotrosingresosdeclaracion(declaracionSeleccionada.getCodDeclaracion().longValue(), 1));
			declaracionParaImprimir.setListaCuentasDeclaracion(ComunicacionServiciosBR
					.getcuentasdeclaracion(declaracionSeleccionada.getCodDeclaracion().longValue(), 1));

			declaracionParaImprimir.setTotalIngresoLaboral( ComunicacionServiciosBR.getSumaIngresos(declaracionSeleccionada.getCodDeclaracion().longValue()).getValor() );
			declaracionParaImprimir.setTotalOtrosIngresos(new BigDecimal(0));
			declaracionParaImprimir.setTotalCesantias(new BigDecimal(0));
			declaracionParaImprimir.setTotalGastosRepresentacion(new BigDecimal(0));
			declaracionParaImprimir.setTotalArriendos(new BigDecimal(0));
			declaracionParaImprimir.setTotalHonorarios(new BigDecimal(0));

			declaracionParaImprimir.sumarTotales();

			declaracionParaImprimir.setListaCuentasDeclaracion(ComunicacionServiciosBR
					.getcuentasdeclaracion(declaracionSeleccionada.getCodDeclaracion().longValue(), 1));
			declaracionParaImprimir.setListaAcreenciasObligaciones(ComunicacionServiciosBR
					.getacreenciaobligacion(declaracionSeleccionada.getCodDeclaracion().longValue(), 1));

			bienPatrimonio.setFlgActivo((short) 1);
			bienPatrimonio.setCodDeclaracion(new BigDecimal(declaracionSeleccionada.getCodDeclaracion().longValue()));
			declaracionParaImprimir
					.setListaBienesPatrimoniales(ComunicacionServiciosBR.getBienesPatrimonio(bienPatrimonio));

			declaracionParaImprimir.setListaActividadesEconomicas(ComunicacionServiciosBR
					.getactividadprivada(declaracionSeleccionada.getCodDeclaracion().longValue(), 1));

			declaracionParaImprimir.setListaParticipacionesJuntas(ComunicacionServiciosBR
					.getparticipacionjunta(declaracionSeleccionada.getCodDeclaracion().longValue(), 1));

			declaracionParaImprimir.setListaParticipacionesSocio(ComunicacionServiciosBR
					.getparticipacionjunta(declaracionSeleccionada.getCodDeclaracion().longValue(), 1));

			listaDeclaracionParaImprimir.add(declaracionParaImprimir);
		}
		return listaDeclaracionParaImprimir;
	}

	public void exportarDeclaracionesTodas() {
		List<DeclaracionRentaBeanPrint> listaDeclaracionParaImprimirc = new ArrayList<>();
		DeclaracionExt filtro = new DeclaracionExt();
		filtro.setCodPersona(new BigDecimal(usuarioSesion.getCodPersona()));
		filtro.setConfirmacion((short) 1);
		filtro.setLimitIni(0);
		filtro.setLimitFin(200);
		List<DeclaracionExt> listDecP = ComunicacionServiciosBR.getDeclaracionFiltro(filtro);
		for (DeclaracionExt declaracionExt : listDecP) {
			listaDeclaracionParaImprimirc.add(construirModeloJasperOLD(declaracionExt, (byte) 1));
		}
		FuncionExtraBean.descargarJasperPDF("bienesrentas/FormatoBienesRentas.jasper", contexto,
				listaDeclaracionParaImprimirc, false, "DeclaracionesDeBienesYRentas_"
						+ ComunicacionServiciosHV.getpersonacontacto(usuarioSesion.getCodPersona()).getNombreUsuario());
	}

	public void exportarDeclaracionesTodasOLD() {
		coinstruirModeloJasperTodas(FacesContext.getCurrentInstance().getExternalContext());
		FuncionExtraBean.descargarJasperPDF("bienesrentas/FormatoBienesRentas.jasper", contexto,
				listaDeclaracionParaImprimir, false, "DeclaracionesDeBienesYRentas_"
						+ ComunicacionServiciosHV.getpersonacontacto(usuarioSesion.getCodPersona()).getNombreUsuario());
	}

	public DeclaracionExt getDeclaracionSeleccionada() {
		return declaracionSeleccionada;
	}

	public void setDeclaracionSeleccionada(DeclaracionExt declaracionSeleccionada) {
		this.declaracionSeleccionada = declaracionSeleccionada;
	}

	/**
	 * @return the consultar
	 */
	public DeclaracionExt getConsultar() {
		return consultar;
	}

	/**
	 * @param consultar
	 *            the consultar to set
	 */
	public void setConsultar(DeclaracionExt consultar) {
		this.consultar = consultar;
	}

	/**
	 * @return the activos
	 */
	public boolean isActivos() {
		return activos;
	}

	/**
	 * @param activos
	 *            the activos to set
	 */
	public void setActivos(boolean activos) {
		this.activos = activos;
	}

	/**
	 * @return the extemporanea
	 */
	public boolean isExtemporanea() {
		return extemporanea;
	}

	/**
	 * @param extemporanea
	 *            the extemporanea to set
	 */
	public void setExtemporanea(boolean extemporanea) {
		this.extemporanea = extemporanea;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the listDecM
	 */
	public LazyDataModel<DeclaracionExt> getListDecM() {
		return listDecM;
	}

	/**
	 * @param listDecM
	 *            the listDecM to set
	 */
	public void setListDecM(LazyDataModel<DeclaracionExt> listDecM) {
		this.listDecM = listDecM;
	}

	/**
	 * @return the listDecP
	 */
	public LazyDataModel<DeclaracionExt> getListDecP() {
		return listDecP;
	}

	/**
	 * @param listDecP
	 *            the listDecP to set
	 */
	public void setListDecP(LazyDataModel<DeclaracionExt> listDecP) {
		this.listDecP = listDecP;
	}

	/**
	 * @return the totalDeclaraciones
	 */
	public int getTotalDeclaraciones() {
		return totalDeclaraciones;
	}

	/**
	 * @param totalDeclaraciones
	 *            the totalDeclaraciones to set
	 */
	public void setTotalDeclaraciones(int totalDeclaraciones) {
		this.totalDeclaraciones = totalDeclaraciones;
	}

	/**
	 * @return the lisEntida
	 */
	public List<SelectItem> getLisEntida() {
		return lisEntida;
	}

	/**
	 * @param lisEntida
	 *            the lisEntida to set
	 */
	public void setLisEntida(List<SelectItem> lisEntida) {
		this.lisEntida = lisEntida;
	}
	
	
	

	public List<SelectItem> getLisEntidadesHistorico() {
		return lisEntidadesHistorico;
	}

	public void setLisEntidadesHistorico(List<SelectItem> lisEntidadesHistorico) {
		this.lisEntidadesHistorico = lisEntidadesHistorico;
	}

	public boolean isVisibilidadConsulta() {
		return this.visibilidadConsulta;
	}

	public void setVisibilidadConsulta(boolean visibilidadConsulta) {
		this.visibilidadConsulta = visibilidadConsulta;
	}

	public void prepararPDF(DeclaracionExt dec, byte b) {
		rutaPdf = "";
		try {
			construirModeloJasper(dec, b);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dec.getFechaInicio());
			if(!listaDeclaracionParaImprimir.isEmpty()) {
				listaDeclaracionParaImprimir.get(0).setDetalleDeclaracion(dec);	
			}
			
			this.tituloVentana = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_VER_DECLARACION_01, getLocale()) + cal.get(Calendar.YEAR)
					+ " "+MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.BR_VER_DECLARACION_02, getLocale())
					+ dec.getEntidadReceptora();
			rutaPdf = FuncionExtraBean.mostrarJasperPDF(FacesContext.getCurrentInstance().getExternalContext(),
					listaDeclaracionParaImprimir, false);
		} catch (Exception e) {
			logger.error("context", "Eror consultaBienesRentas- opción prepararPDF()");
		}
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<String> completeText(String query) {
        List<String> results = new ArrayList<>();
        Entidad objentidad = new Entidad();
        objentidad.setNit(null);
        objentidad.setSigla(null);
        objentidad.setCodigoSigep(null);
        objentidad.setCodigoDane(null);
        objentidad.setCodigoCuin(null);
        objentidad.setFlgActivo((short) 1);
        objentidad.setNombreEntidad(query);
        List<Entidad> enti = ComunicacionServiciosEnt.getEntidadesFiltro(objentidad);
        if(!enti.isEmpty()) {
        	for (Entidad entidad : enti) {
            	results.add(entidad.getNombreEntidad());
    		}
        }
        return results;
    }

	public String obtenerRutaPdf() {
		return rutaPdf;
	}

	public String getTituloVentana() {
		return tituloVentana;
	}

	public void setTituloVentana(String tituloVentana) {
		this.tituloVentana = tituloVentana;
	}

	public boolean isBtnBuscarBRServidorPublicoDeshabilitado() {
		return btnBuscarBRServidorPublicoDeshabilitado;
	}

	public void setBtnBuscarBRServidorPublicoDeshabilitado(boolean btnBuscarBRServidorPublicoDeshabilitado) {
		this.btnBuscarBRServidorPublicoDeshabilitado = btnBuscarBRServidorPublicoDeshabilitado;
	}

	public boolean isBtnVerDetalleBRDeshabilitado() {
		return btnVerDetalleBRDeshabilitado;
	}

	public void setBtnVerDetalleBRDeshabilitado(boolean btnVerDetalleBRDeshabilitado) {
		this.btnVerDetalleBRDeshabilitado = btnVerDetalleBRDeshabilitado;
	}

	public boolean isBtnExportarDetalleBRDeshabilitado() {
		return btnExportarDetalleBRDeshabilitado;
	}

	public void setBtnExportarDetalleBRDeshabilitado(boolean btnExportarDetalleBRDeshabilitado) {
		this.btnExportarDetalleBRDeshabilitado = btnExportarDetalleBRDeshabilitado;
	}

	public boolean isBtnImprimirDetalleBRDeshabilitado() {
		return btnImprimirDetalleBRDeshabilitado;
	}

	public void setBtnImprimirDetalleBRDeshabilitado(boolean btnImprimirDetalleBRDeshabilitado) {
		this.btnImprimirDetalleBRDeshabilitado = btnImprimirDetalleBRDeshabilitado;
	}

	public boolean isBtnNuevaBusquedaBRDeshabilitado() {
		return btnNuevaBusquedaBRDeshabilitado;
	}

	public void setBtnNuevaBusquedaBRDeshabilitado(boolean btnNuevaBusquedaBRDeshabilitado) {
		this.btnNuevaBusquedaBRDeshabilitado = btnNuevaBusquedaBRDeshabilitado;
	}

	public String getBtnBuscarBRServidorPublicoMensaje() {
		return btnBuscarBRServidorPublicoMensaje;
	}

	public void setBtnBuscarBRServidorPublicoMensaje(String btnBuscarBRServidorPublicoMensaje) {
		this.btnBuscarBRServidorPublicoMensaje = btnBuscarBRServidorPublicoMensaje;
	}

	public String getBtnVerDetalleBRMensaje() {
		return btnVerDetalleBRMensaje;
	}

	public void setBtnVerDetalleBRMensaje(String btnVerDetalleBRMensaje) {
		this.btnVerDetalleBRMensaje = btnVerDetalleBRMensaje;
	}

	public String getBtnExportarDetalleBRMensaje() {
		return btnExportarDetalleBRMensaje;
	}

	public void setBtnExportarDetalleBRMensaje(String btnExportarDetalleBRMensaje) {
		this.btnExportarDetalleBRMensaje = btnExportarDetalleBRMensaje;
	}

	public String getBtnImprimirDetalleBRMensaje() {
		return btnImprimirDetalleBRMensaje;
	}

	public void setBtnImprimirDetalleBRMensaje(String btnImprimirDetalleBRMensaje) {
		this.btnImprimirDetalleBRMensaje = btnImprimirDetalleBRMensaje;
	}

	public String getBtnNuevaBusquedaBRMensaje() {
		return btnNuevaBusquedaBRMensaje;
	}

	public void setBtnNuevaBusquedaBRMensaje(String btnNuevaBusquedaBRMensaje) {
		this.btnNuevaBusquedaBRMensaje = btnNuevaBusquedaBRMensaje;
	}

	public boolean isHabilitadoTxtEntidad() {
		return habilitadoTxtEntidad;
	}

	public void setHabilitadoTxtEntidad(boolean habilitadoTxtEntidad) {
		this.habilitadoTxtEntidad = habilitadoTxtEntidad;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws NotSupportedException, SIGEP2SistemaException {
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

	/**
	 * @return the modificada
	 */
	public boolean isModificada() {
		return modificada;
	}

	/**
	 * @param modificada the modificada to set
	 */
	public void setModificada(boolean modificada) {
		this.modificada = modificada;
	}
	
	public void cargarListaEntidades() {
		if(entidades.size() > 1) {
			for (Entidad aux : entidades) {
				lisEntida.add(new SelectItem(aux.getCodEntidad().toString(), aux.getNombreEntidad()));
			}
		}
	}
	
	public int getEntidadSelect() {
		return entidadSelect;
	}
	public void setEntidadSelect(int entidadSelect) {
		this.entidadSelect = entidadSelect;
	}

	public boolean isVariasEntidades() {
		return variasEntidades;
	}

	public void setVariasEntidades(boolean variasEntidades) {
		this.variasEntidades = variasEntidades;
	}

	public boolean isLbEsAdminFunc() {
		return lbEsAdminFunc;
	}

	public void setLbEsAdminFunc(boolean lbEsAdminFunc) {
		this.lbEsAdminFunc = lbEsAdminFunc;
	}
	
	
}