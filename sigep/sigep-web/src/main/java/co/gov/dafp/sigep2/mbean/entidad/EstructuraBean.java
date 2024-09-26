package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.extensions.component.gchart.model.GChartModelBuilder;
import org.primefaces.extensions.component.gchart.model.GChartType;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Contrato;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.DependenciaEntidad;
import co.gov.dafp.sigep2.entities.DependenciaHojaVida;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Estructura;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.ContratoExt;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EstructuraExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosCO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Gestiona la creacion y administracion de las estructuras organizacionales y
 * los grupo de trabajo. CU04 07 ESTRUCTURA ORGANIZACIONAL
 *
 * @author jesus.torres
 * @Proyect SIGEPII
 * @Company ADA SA
 * @Module Entidades
 * @Fecha 04-09-2018
 */
@Named
@ManagedBean
@ViewScoped

public class EstructuraBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 8706482330998693469L;
	private static final Logger logger = Logger.getInstance(EstructuraBean.class);
	private Entidad entidadActual;
	private boolean verFormularioAgregarDependencia;
	private EstructuraExt estructuraEntidad;
	private List<DependenciaEntidadExt> lstDependenciasEntidad;
	Integer codAudCodRol, codAudAccionInsert = 3, codAudAccionUpdate = 2, codAudAccionDelete = 1;
	private UsuarioDTO usuarioSesion = new UsuarioDTO();
	ExternalContext externalContext;
	private String strMensajeDialog;
	private boolean tieneestructura = false;
	private String strNombreDependenciaBuscar, strNumJerarquiaBuscar, strTipoDependenciaBuscar;
	private GChartModel arbolOrganizacional = null;
	TreeNode rootTreeNode = new DefaultTreeNode();
	TreeNode childTreeNode;
	DependenciaEntidadExt depNivelI;
	private boolean lbverOrganigrama;
	private Norma normaSeleccionada;
	private Norma normaEstructura;
	private MapModel geoModel;
	private EditarDireccionDTO editarDireccion;
	private boolean estadoPanelDireccion;
	private boolean tabDepenEspecial;
	private boolean estadoPnlDepEspecial;
	private boolean estadoPnlMap;
	private boolean estadoPnlform;
	private boolean estadoBtnImpre;
	private boolean estadoCambios;
	private String strMsgNoPermisos;
	private String strMsgGuardarDefError; 
	private String strMsgGuardarParcialError; 
	private String strMsgGuardarDefExito;
	private String strMsgGuardarParcialExito; 
	private String strMsgEntidadInactiva;
	
	private String strMsgValidaJerarquiaPadre; 
	private String strMsgEliminaDependenciaExito; 
	private String strMsgEliminaDependenciaError;
	private String strMsgOrganigramaExito; 
	private String strMsgOrganigramaError; 
	private String strMsgAdvUsuario;
	private String strMsgEntidadNoEstructura;
	private String strMsgErrorObtenerEntidad,strMsgValidarJerarquiaNumerica,strMsgValidarJerarquiaRepetida,strMsgValidarJerarquiaNombre,
				   strMsgValidarJerarquiaNoPadre;
	
	private String centerGeoMap;
	private String mensajeToolOrga;
	private String banderaPais;
    private ExcelOptions excelOpt;   
    private DependenciaEntidadExt dependenciaEnGestion;
    private DefaultMapModel modeloMapa;
    private String centroMapa;
    private DependenciaEntidadExt dependenciaEliminar;
    @Inject
    protected GestionEntidadBean gestionEntidadBean;
    
    private List<SelectItem> siDependenciaspadre;
    
    	
	@Override
	public void validateForm(ComponentSystemEvent event) {
	}

	@Override
	public void retrieve() {
	}

	@Override
	public String update() {
		return null;
	}

	@Override
	public void delete() {
	}

	@Override
	public String persist() {
		return null;
	}
	
	public EstructuraBean() {
		this.cerrarSessionFuncion(AMBITO_POLITICAS);
		this.centerGeoMap = "41.850033, -87.6500523";
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		this.verFormularioAgregarDependencia = false;
		this.normaEstructura = new Norma();
		this.estructuraEntidad = new EstructuraExt();
		this.lstDependenciasEntidad = new ArrayList<>();
		this.codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		this.codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		this.codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());	
		this.usuarioSesion = (UsuarioDTO) externalContext.getSessionMap().get("usuarioSesion");
		this.codAudCodRol = (int) this.getRolAuditoria().getId();
		this.lbverOrganigrama = false;
		this.entidadActual = (Entidad) externalContext.getSessionMap().get("estructuraEntidad");
		this.entidadActual = ComunicacionServiciosEnt.getEntidadPorId(((BigDecimal) externalContext.getSessionMap().get("codEntidad")).longValue());
		this.incializaMsgTitlesProperties();
		this.dependenciaEnGestion = new DependenciaEntidadExt();
		this.geoModel = new DefaultMapModel();
		this.editarDireccion = new EditarDireccionDTO();
		this.estadoPnlform = true;		
		this.tabDepenEspecial = true;
		this.banderaPais = "/resources/images/banderas/co.png";	   
		siDependenciaspadre = new ArrayList<>();
	
	}

	@Override
	@PostConstruct
	public void init() {
		if(!validarPermisoRolEstructuraOrganizacional()){
    		this.mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()), "window.location.assign('entidad/../../../gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;
		}
		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		if(this.entidadActual == null) {
			this.setStrMensajeDialog(strMsgErrorObtenerEntidad);
			RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacionalRetornaPrincipal').show();");
			return;
		}
		if(this.entidadActual != null && entidadActual.getCodEstadoEntidad() != null && entidadActual.getCodEstadoEntidad().intValue() != TipoParametro.ENTIDAD_ACTIVA.getValue()){
				this.setStrMensajeDialog(strMsgEntidadInactiva);
				RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacionalRetornaPrincipal').show();");
				return;
		}			
		this.obtenerEstructura();
		if(this.estructuraEntidad == null || this.estructuraEntidad.getCodEstructura()==null)
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, strMsgAdvUsuario, strMsgEntidadNoEstructura);
		DependenciaEntidadExt depBuscar = new DependenciaEntidadExt();
		depBuscar.setCodEntidad(entidadActual.getCodEntidad().longValue());
		depBuscar.setFlgActivo((short) 1);
		depBuscar.setLimitInit(0);
		depBuscar.setLimitEnd(2000);
		depBuscar.setOrdenDependencias("1");
		this.lstDependenciasEntidad = ComunicacionServiciosVin.getDependenciaEntidadFilter(depBuscar);
		externalContext.getSessionMap().remove("estructuraEntidad");
		try {
			if (!usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.JEFE_TALENTO_HUMANO) && entidadActual.getCodEstadoEntidad().intValue() != 1480) {
				this.setStrMensajeDialog(strMsgNoPermisos);
				RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacionalRetornaPrincipal').show();");
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
		this.configurarCampoDepEspecial();
		this.estadoBtnImpre = true;
		llenaritemsDependenciapadre();
	}

	public void agregarDependencia() {
		if (this.lstDependenciasEntidad == null)
			this.lstDependenciasEntidad = new ArrayList<>();
		DependenciaEntidadExt depNew = new DependenciaEntidadExt();
		depNew.setFlgEspecial((short) 0);
		this.lstDependenciasEntidad.add(0, depNew);
		this.estadoCambios = true;
	
	}
	
	private boolean validarCamposNorma() {
		if(this.estructuraEntidad != null && this.estructuraEntidad.getJustificacion() != null && !this.estructuraEntidad.getJustificacion().isEmpty()) {
			if(this.normaEstructura == null) 
				this.normaEstructura = new Norma();
			return true;
		}
		else if(this.normaEstructura != null && this.normaEstructura.getCodTipoNorma() != null && this.normaEstructura.getFechaNorma() != null && this.normaEstructura.getNumeroNorma() != null)
			return true;
		else {
			this.normaEstructura = new Norma();
			return false;
		}
	}
	
	/**
	 * Guarda la información ingresada en el formulacion de estrutura organizicional. recibe un parametro de tipo texto con el valor del guardado que desea realizarse "1" para realizar un guardado parcial
	 * "0" para guardado definitivo. Este método guarda la información de la tabla de dependencias y la estructura, que son los datos de la norma. 
	 * @param tipo
	 */
	public void guardar(String tipo){
		for(DependenciaEntidadExt de : this.lstDependenciasEntidad) {
			if(!this.validarDuplicidadNombre(de))
				return;
		}
		if(!this.validarCamposNorma()) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_CAMPO_JUSTIFICACION_NORMA);
			return;
		}
		if(!validarInformacionDependencias())
			return;
		Short flgGuardadoParcial;
		String  strMensajeGuardarExito, strMensajeGuardarError;
		if("1".equals(tipo)){
			flgGuardadoParcial=1;
			strMensajeGuardarExito = strMsgGuardarParcialExito;
			strMensajeGuardarError = strMsgGuardarParcialError;
		}
		else{
			flgGuardadoParcial=0;
			strMensajeGuardarExito = strMsgGuardarDefExito;
			strMensajeGuardarError = strMsgGuardarDefError;
		}
		Estructura resEstructura = null;
		DependenciaEntidad resDependencia;
		boolean lblErrorDependencias = false;
		if(this.estructuraEntidad.getCodEstructura()==null)
			this.estructuraEntidad.setAudAccion(codAudAccionInsert);
		else
			this.estructuraEntidad.setAudAccion(codAudAccionUpdate);
		this.estructuraEntidad.setAudCodRol(codAudCodRol);
		this.estructuraEntidad.setAudFechaActualizacion(new Date());
		this.estructuraEntidad.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
		this.estructuraEntidad.setFlgActivo((short) 1);
		this.estructuraEntidad.setFlgGuardadoParcial(flgGuardadoParcial);
		this.estructuraEntidad.setCodEntidad(entidadActual.getCodEntidad());
		this.estructuraEntidad.setFlgDependencia((short) 1);
		if(!tieneestructura && this.normaEstructura != null && this.normaEstructura != null 
				&& this.normaEstructura.getCodTipoNorma() != null 
				&& this.normaEstructura.getFechaNorma() != null 
				&& this.normaEstructura.getNumeroNorma() != null) {
			Norma norma = new Norma();
			norma.setNumeroNorma(normaEstructura.getNumeroNorma());
			norma.setCodTipoNorma(normaEstructura.getCodTipoNorma());
			norma.setFechaNorma(normaEstructura.getFechaNorma());
			List<Norma> listNorma= ComunicacionServiciosEnt.getFiltroNorma(norma);
			if(listNorma != null) {
				if(listNorma.isEmpty()) {
					norma.setAudAccion(785);
					norma.setAudCodRol((int) getUsuarioSesion().getCodRol());
					norma.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
					norma.setAudFechaActualizacion(new Date());
					norma = ComunicacionServiciosEnt.setNorma(norma);
					if(!norma.isError()) 
						this.estructuraEntidad.setCodNorma(norma.getCodNorma());
					else{
						this.mostrarMensaje(FacesMessage.SEVERITY_WARN, strMsgAdvUsuario, norma.getMensaje());
						return;
					}
				}
				else
					this.estructuraEntidad.setCodNorma(listNorma.get(0).getCodNorma());
			}
			resEstructura = ComunicacionServiciosEnt.setEstructura(estructuraEntidad);
			if(resEstructura.isError()){
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, strMsgAdvUsuario, norma.getMensaje());
				return;
			}
		}else
			resEstructura = ComunicacionServiciosEnt.setEstructura(estructuraEntidad);
		if(lstDependenciasEntidad != null && lstDependenciasEntidad.size() > 0){
			for(DependenciaEntidadExt dependencia: lstDependenciasEntidad){
				if(dependencia.getCodDependenciaEntidad()==null)
					dependencia.setAudAccion(codAudAccionInsert);
				else
					dependencia.setAudAccion(codAudAccionUpdate);
				dependencia.setAudCodRol(codAudCodRol);
				dependencia.setAudFechaActualizacion(new Date());
				dependencia.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
				dependencia.setCodEntidad(entidadActual.getCodEntidad().longValue());
				dependencia.setFlgActivo((short) 1);
				dependencia.setFlgEspecial(dependencia.getFlgEspecial() == null? 0 : dependencia.getFlgEspecial());
				resDependencia = ComunicacionServiciosEnt.setDependenciaEntidad(dependencia);
				if(resDependencia.isError()){
					strMensajeGuardarError = strMensajeGuardarError + "-" + resDependencia.getMensajeTecnico();
					lblErrorDependencias = true;
				}
				//Valida que solo se guarden las dependencias definitivas
				if("0".equals(tipo)){
					almacenarMaestroDependenciaHV(dependencia);	
				}
			}				
		}
		if(resEstructura!= null) {
			if(resEstructura.isError() || lblErrorDependencias)
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMensajeGuardarError);
			else{
				if(1 == flgGuardadoParcial){
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMensajeGuardarExito);
					this.obtenerDependencias();	
					this.obtenerEstructura();
				}
				else{
					this.setStrMensajeDialog(strMensajeGuardarExito);
					RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacionalRetornaPrincipal').show();");
				}
				this.estadoCambios = false;
			}
		}
		else {
			if(lblErrorDependencias)
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMensajeGuardarError);
			else{
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMensajeGuardarExito);
				if(0 == flgGuardadoParcial)
					this.regresar();
				else{
					this.obtenerDependencias();	
					this.obtenerEstructura();
				}
			}
		}
	}
	
	private boolean validarInformacionDependencias(){
		if(lstDependenciasEntidad!=null && !lstDependenciasEntidad.isEmpty()){
			String strestadojerarquia=validaVariosRoots();
			if("1".equals(strestadojerarquia)){
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_SIN_RAIZ);
				return false;
			}else if("2".equals(strestadojerarquia)){
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_PADRE_VARIAS);
				return false;
			}
			
			for(DependenciaEntidadExt dependencia: lstDependenciasEntidad){
				if(dependencia.getNombreDependencia()==null || "".equals(dependencia.getNombreDependencia())){
					this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Por favor indique el nombre de la dependencia");
					return false;
				}else if(dependencia.getJerarquia()==null || "".equals(dependencia.getJerarquia())){
					this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,"Por favor indique la jerarquía numérica de: "+ dependencia.getNombreDependencia());
					return false;					
				}else if(dependencia.getJerarquia()!= null && dependencia.getJerarquiaPadre() !=  null
						&& dependencia.getJerarquia().equals(dependencia.getJerarquiaPadre())){
					this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMsgValidarJerarquiaNoPadre);
						return false;
		    	}
			}
		}
		else {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, "No hay dependencias para procesar, por favor ingrese al menos una dependencia en la tabla e intentelo de nuevo");
			return false;
		}
		
	
		
		
		
		return true;
	}
	
	public void celdaEditada(CellEditEvent event) {
		if((event.getOldValue()) instanceof String)
			this.estadoCambios = !((String) event.getOldValue()).equals((String) event.getNewValue());
		else{
			if(event.getOldValue() != null)
				this.estadoCambios = !((Short) event.getOldValue()).equals((Short) event.getNewValue());
		}
    }
    
    
	/*Validaciones en Jerarquia padre*/
    /*valida la jerarquia si tiene predecesoras*/
    public void validarJerarquiaPadre(DependenciaEntidadExt depPadreValidar){
    	if(depPadreValidar != null) {
    		if(depPadreValidar.getJerarquia()!=null && depPadreValidar.getJerarquiaPadre()!= null && depPadreValidar.getJerarquia().
    				equals(depPadreValidar.getJerarquiaPadre())){
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMsgValidarJerarquiaNoPadre);
				depPadreValidar.setJerarquiaPadre(null);
				return;
    		}
    	}
    }
	
	/*Validaciones en Jerarquia*/
    public void validarJerarquia(DependenciaEntidadExt depValidar){
    	if(depValidar != null) {
    		
    		if(depValidar.getJerarquia()!=null && depValidar.getJerarquiaPadre()!= null && depValidar.getJerarquia().equals(depValidar.getJerarquiaPadre())){
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMsgValidarJerarquiaNoPadre);
				depValidar.setJerarquia(null);
				return;
    		}
    		
    		if(depValidar.getJerarquia()!=null){
    			if(depValidar.getJerarquia().contains("..")){
    				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMsgValidarJerarquiaNumerica);
    				depValidar.setJerarquia(null);
    				return;
    			}
    		}
    		for(char i : depValidar.getJerarquia().toCharArray()) {
    			if(i != 46 &&  !(i >= 48 && i <= 57)) {
    				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMsgValidarJerarquiaNumerica);
    				depValidar.setJerarquia(null);
    				return;
    			}
    		}
    		for(DependenciaEntidadExt iter : this.lstDependenciasEntidad) {
    			if(iter.getJerarquia()!=null && iter.getJerarquia().equalsIgnoreCase(depValidar.getJerarquia()) && iter != depValidar) {
    				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMsgValidarJerarquiaRepetida);
    				depValidar.setJerarquia(null);
    				return;
    			}
    		}
    		if(depValidar.getJerarquia().charAt(depValidar.getJerarquia().length() - 1) == '.') {
    			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, strMsgValidarJerarquiaNombre);
    			depValidar.setJerarquia(null);
    			return;
    		}
    	}
    	if(depValidar!=null && depValidar.getJerarquia()!=null && !"".equals(depValidar.getJerarquia()) && depValidar.getJerarquia().split("[.]").length>1){
    		if(!validaHijoPadre(depValidar)){
    	 		depValidar.setJerarquia(null);
        		mostrarMensaje(FacesMessage.SEVERITY_WARN, strMsgAdvUsuario, strMsgValidaJerarquiaPadre);
    		}
    	}
    	agregaritemDependenciapadre(depValidar.getJerarquia());
    }
    
    /**
     * valida que un hijo tenga padre
     * @param depHijo
     * @return
     */
    private boolean validaHijoPadre(DependenciaEntidadExt depHijo){
    	
    	if(depHijo.getJerarquiaPadre()!=null){
    		for(DependenciaEntidadExt depPosiblePadre:lstDependenciasEntidad){
    			if(depPosiblePadre.getJerarquia().equals(depHijo.getJerarquiaPadre())){
						depHijo.setCodPredecesor(depPosiblePadre.getCodDependenciaEntidad());
						return true;
    			}
    		}
    		
    	}else{
    		depHijo.setCodPredecesor(null);
    	}
    	return false;
    }
    
    /*valida si una jerarquia tiene hijos*/
    private boolean validarPadreHijos(DependenciaEntidadExt dependencia){
		boolean tienehijos=false;
    	if(lstDependenciasEntidad.size()>1){
			for(DependenciaEntidadExt iter : this.lstDependenciasEntidad) {
				if(iter.getJerarquia()!=null && dependencia.getJerarquia() != null  && iter != dependencia) {
					if(iter.getJerarquia().length() > dependencia.getJerarquia().length()){
						if(iter.getJerarquia().substring(0, dependencia.getJerarquia().length()).equals(dependencia.getJerarquia())){
							tienehijos = true;
							break;	
						}
					}
				}
			}
    	}
    	return tienehijos;
    }
    
    
    /*Valida si la dependencia tiene vinculaciones activas*/
    public boolean validarVinculacionesActivas(DependenciaEntidadExt dependencia) {
    	boolean blnTieneVinculacion = false;
    	if(dependenciaEliminar!= null && dependenciaEliminar.getCodDependenciaEntidad()!= null) {
    		VinculacionExt objVinc= new VinculacionExt();
        	objVinc.setCodEntidad(entidadActual.getCodEntidad().longValue());
        	objVinc.setCodDependenciaEntidad(BigDecimal.valueOf(dependenciaEliminar.getCodDependenciaEntidad()));
        	objVinc.setFlgActivo((short)1);
        	List<VinculacionExt> vinculacion = ComunicacionServiciosVin.getVinculacionFilter(objVinc);
        	if(!vinculacion.isEmpty()) {
        		blnTieneVinculacion = true;
        	}
    	}
    	
    	return blnTieneVinculacion;
    }
    
    /*Valida si la dependencia a eliminar tiene conratos activos*/
    public boolean validarContratosActivos(DependenciaEntidadExt dependencia) {
    	boolean blnTieneContratos = false;
    	Contrato objContrato = new Contrato();
    	objContrato.setCodEntidad(entidadActual.getCodEntidad().longValue());
    	objContrato.setFlgActivo((short) 1);
    	objContrato.setLimitInit(0);
    	objContrato.setLimitEnd(10);
    	objContrato.setCodDependenciaEntidad(dependenciaEliminar.getCodDependenciaEntidad());
    	List<ContratoExt> contrato = ComunicacionServiciosCO.getContratoFiltro(objContrato);
    	if(!contrato.isEmpty()) {
    		blnTieneContratos = true;
    	}
    	return blnTieneContratos;
    }
    
    /*Metodo que elimina una dependencia de la estructura*/
	public void eliminarDependenciaEstructura(){
		if(validarVinculacionesActivas(dependenciaEliminar)|| validarContratosActivos(dependenciaEliminar)) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ADVERTENCIA_VINCULACION);	
			return;
		}
		
		
		/*validar que la dependencia no tenga hijos asociados*/
		if(validarPadreHijos(dependenciaEliminar)){
			this.setStrMensajeDialog("No puede eliminar la dependencia porque tiene hijos asociados");
			RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacional').show();");	
			return;
		}
	
		DependenciaEntidad resDependencia;
		dependenciaEliminar.setAudAccion(codAudAccionUpdate);
		dependenciaEliminar.setAudCodRol(codAudCodRol);
		dependenciaEliminar.setAudFechaActualizacion(new Date());
		dependenciaEliminar.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
		dependenciaEliminar.setCodEntidad(entidadActual.getCodEntidad().longValue());
		dependenciaEliminar.setFlgActivo((short) 0);
		resDependencia = ComunicacionServiciosEnt.setDependenciaEntidad(dependenciaEliminar);
		if(!resDependencia.isError()){
			this.setStrMensajeDialog(strMsgEliminaDependenciaExito);
			RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacional').show();");
			this.lstDependenciasEntidad.remove(dependenciaEliminar);
			this.lbverOrganigrama = false;
			this.estadoCambios = true;
		}
		else {
			this.setStrMensajeDialog(strMsgEliminaDependenciaError);
			RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacional').show();");			
		}
	}
	
	public void regresar(){
		try {
			finalizarConversacion("/entidad/gestionarEntidad.xhtml", null, null);
		}
		catch (IOException ex) {
			logger.error("public void regresar() EstructuraBean.java error: " + ex.getMessage(), ex);
		}
	}
	
	public void generarEstructura(){
		if(this.estadoCambios) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ADVERTENCIA_CAMBIOS, this.getLocale()));
			return;
		}
		String strestadojerarquia=validaVariosRoots();
		if("1".equals(strestadojerarquia)){
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_SIN_RAIZ);
			return;
		}else if("2".equals(strestadojerarquia)){
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_PADRE_VARIAS);
			return;
		}
		
		if(this.estructuraEntidad!=null){
			this.obtenerDependencias();
			if(this.lstDependenciasEntidad == null || (this.lstDependenciasEntidad != null && this.lstDependenciasEntidad.isEmpty())) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_NO_DEPENDENCIAS);
				return;
			}
			for(DependenciaEntidadExt dependencia : this.lstDependenciasEntidad){
				this.validaHijoPadre(dependencia);
				dependencia.setAudAccion(codAudAccionUpdate);
				dependencia.setAudCodRol(codAudCodRol);
				dependencia.setAudFechaActualizacion(new Date());
				dependencia.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
				ComunicacionServiciosEnt.setDependenciaEntidad(dependencia);			
			}
			this.lbverOrganigrama = true;
			this.generarArbol();
			this.estadoBtnImpre = false;
		}
		else
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, strMsgAdvUsuario, MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ENTIDAD_NO_ESTRUCTURA);
		
	}
	/*Método que valida los padres para la jeraqui
	 * lstDependenciasEntidad
	 * return 0 OK
	 * return 1 si no hay padre
	 * return 2 si hay más de un padre
	 * */
	private String validaVariosRoots(){
		int contPadre=0;
		for(DependenciaEntidadExt dep:lstDependenciasEntidad){
			if(dep.getJerarquiaPadre()==null)
				contPadre++;
		}
		if (contPadre==0)
			return "1";
		if(contPadre>1)
			return "2";
		return "0";
	}
	
	public void generarEstructuraStaff(DependenciaEntidadExt dep){
		this.lbverOrganigrama = true;
		rootTreeNode = new DefaultTreeNode(dep.getNombreDependencia());
		generarArbolNodo(dep.getCodDependenciaEntidad(), rootTreeNode,null );
		arbolOrganizacional = new GChartModelBuilder().setChartType(GChartType.ORGANIZATIONAL).addColumns("Name", "Manager").importTreeNode(rootTreeNode).addOption("size", "large").build();
		this.setStrMensajeDialog(strMsgOrganigramaExito);
		RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacional').show();");	
	}
	
	public void obtenerDependencias(){
		this.lstDependenciasEntidad = null;
		DependenciaEntidadExt depBuscar = new DependenciaEntidadExt();
		depBuscar.setCodEntidad(entidadActual.getCodEntidad().longValue());
		depBuscar.setFlgActivo((short) 1);
		depBuscar.setLimitInit(0);
		depBuscar.setLimitEnd(2000);
		depBuscar.setOrdenDependencias("1");
		this.lstDependenciasEntidad = ComunicacionServiciosVin.getDependenciaEntidadFilter(depBuscar);	
	}
	public void obtenerDependenciasJerarquia(){
		this.lstDependenciasEntidad = null;
		DependenciaEntidadExt depBuscar = new DependenciaEntidadExt();
		depBuscar.setCodEntidad(entidadActual.getCodEntidad().longValue());
		depBuscar.setFlgActivo((short) 1);
		depBuscar.setOrdenDependencias("1");
		depBuscar.setLimitInit(0);
		depBuscar.setLimitEnd(2000);		
		this.lstDependenciasEntidad = ComunicacionServiciosVin.getDependenciaEntidadFilter(depBuscar);	
	}	
	
	private void obtenerEstructura(){
		EstructuraExt fil = new EstructuraExt();
		fil.setCodEntidad(this.entidadActual.getCodEntidad());
		fil.setFlgDependencia((short) 1);
		List<EstructuraExt> lista = ComunicacionServiciosEnt.obtenerEstructuraFiltro(fil);
		if(lista != null && !lista.isEmpty()) {
			this.estructuraEntidad = lista.get(0);
			if(estructuraEntidad.getCodNorma() != null) {
				this.normaEstructura = ComunicacionServiciosEnt.getNormaPorId(estructuraEntidad.getCodNorma());
				this.tieneestructura = true;
				if(normaEstructura.getCodNorma() == null){
					normaEstructura = new Norma();
					this.normaSeleccionada = new Norma();
				}
				else 
					this.normaSeleccionada = this.normaEstructura;
			}else {
				normaEstructura = new Norma();
				this.normaSeleccionada = new Norma();
			}
		}
	}
	
	private void generarArbol() {
		if(generarArbolNivelI()){
			this.setStrMensajeDialog(strMsgOrganigramaExito);
			RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacional').show();");	
		}else{
			this.setStrMensajeDialog(strMsgOrganigramaError);
			RequestContext.getCurrentInstance().execute("PF('dlgEstructuraOrganizacional').show();");				
		}

		arbolOrganizacional = new GChartModelBuilder().setChartType(GChartType.ORGANIZATIONAL).addColumns("Name", "Manager").importTreeNode(rootTreeNode).addOption("size", "large").build();
	}	
	
	private boolean generarArbolNivelI(){
		List<DependenciaEntidadExt> lstDependenciasRoot;
		DependenciaEntidadExt depBuscar = new DependenciaEntidadExt();
		depBuscar.setFlgActivo((short) 1);
		depBuscar.setCodEntidad(entidadActual.getCodEntidad().longValue());
		depBuscar.setCodPredecesorNull(0L);
		lstDependenciasRoot = ComunicacionServiciosVin.getDependenciaEntidadFilter(depBuscar);
		if(lstDependenciasRoot!=null && !lstDependenciasRoot.isEmpty()){
			for (DependenciaEntidadExt dependencia : lstDependenciasRoot) {
				rootTreeNode = new DefaultTreeNode(dependencia.getNombreDependencia());
				generarArbolNodo(dependencia.getCodDependenciaEntidad(), rootTreeNode,"1" );
			}
		}
		return true;
	}
	
	private boolean generarArbolNodo(Long codDependenciaEntidad, TreeNode parent,String dependenciaspadre){
		List<DependenciaEntidadExt> lstDependenciasHijo;
		DependenciaEntidadExt depBuscar = new DependenciaEntidadExt();
		depBuscar.setFlgActivo((short) 1);
		depBuscar.setCodEntidad(entidadActual.getCodEntidad().longValue());
		depBuscar.setCodPredecesor(codDependenciaEntidad);
		depBuscar.setOrdenDependencias("1");
		depBuscar.setLimitInit(0);
		depBuscar.setLimitEnd(2000);
		depBuscar.setDependenciasPadre(dependenciaspadre);
		if(dependenciaspadre==null)
			depBuscar.setFlgDependenciaHijo((short) 1);
		lstDependenciasHijo = ComunicacionServiciosVin.getDependenciaEntidadFilter(depBuscar);
		if(lstDependenciasHijo!=null && !lstDependenciasHijo.isEmpty()){
			for (DependenciaEntidadExt dependencia : lstDependenciasHijo) {
				childTreeNode = childTreeNode = new DefaultTreeNode(dependencia.getNombreDependencia(), rootTreeNode);
				parent.getChildren().add(childTreeNode );
				this.generarArbolNodo(dependencia.getCodDependenciaEntidad(),childTreeNode,dependenciaspadre );
			}
		}
		return true;
	}
	
	public void buscarDependencias(){
		if(!this.estadoCambios){
			DependenciaEntidadExt depBuscar = new DependenciaEntidadExt();
			if(this.getStrNumJerarquiaBuscar()!=null && !"".equals(this.getStrNumJerarquiaBuscar()))
				depBuscar.setJerarquia(this.getStrNumJerarquiaBuscar());
			if(this.getStrNombreDependenciaBuscar()!=null && !"".equals(this.getStrNombreDependenciaBuscar()))
				depBuscar.setNombreDependencia(this.getStrNombreDependenciaBuscar());
			if(this.getStrTipoDependenciaBuscar()!=null && !"".equals(this.getStrTipoDependenciaBuscar()))
				depBuscar.setCodTipDependenciaEspecial(BigDecimal.valueOf(Integer.parseInt(this.getStrTipoDependenciaBuscar())) );
			depBuscar.setCodEntidad(entidadActual.getCodEntidad().longValue());
			depBuscar.setFlgActivo((short) 1);
			depBuscar.setLimitInit(0);
			depBuscar.setLimitEnd(2000);
			depBuscar.setOrdenDependencias("1");
			this.lstDependenciasEntidad=ComunicacionServiciosVin.getDependenciaEntidadFilter(depBuscar);
			if(this.lstDependenciasEntidad.isEmpty()) 
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_MENSAJE_BUSQUEDA);
			else 
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_MENSAJE_BUSQUEDA_EXITOSA);
		}else{
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, 
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ADVERTENCIA_CAMBIOS, this.getLocale()));
		}
	}
	
	private void incializaMsgTitlesProperties(){
		try {
			strMsgNoPermisos = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_NO_PERMISO, getLocale());
			
			strMsgGuardarDefError = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_GUARDADO_DEFINITIVO_ERROR, getLocale());
			
			strMsgGuardarParcialError = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_GUARDADO_PARCIAL_ERROR, getLocale());
			
			strMsgGuardarDefExito = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_GUARDADO_DEFINITIVO_EXITO, getLocale());
			
			strMsgGuardarParcialExito = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_GUARDADO_PARCIAL_EXITO, getLocale());
			
			strMsgEntidadInactiva = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ENTIDAD_NO_ACTIVA, getLocale());
			

			
			strMsgValidaJerarquiaPadre = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_PADRE, getLocale());
			
			strMsgEliminaDependenciaExito = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_DEPENDENCIA_ELIMINADA_EXITO, getLocale());
			
			strMsgEliminaDependenciaError = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_DEPENDENCIA_ELIMINADA_ERROR, getLocale());
		
			strMsgOrganigramaExito = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ORGANIGRAMA_EXITO, getLocale());
			
			strMsgOrganigramaError = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ORGANIGRAMA_ERROR, getLocale());
			
			strMsgAdvUsuario = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ADV_USUARIO, getLocale());
			
			strMsgEntidadNoEstructura = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_ENTIDAD_NO_ESTRUCTURA, getLocale());
			
			strMsgErrorObtenerEntidad = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_OBTENER_ENTIDAD_ERROR, getLocale());
			
			strMsgValidarJerarquiaNumerica =  MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_NUMERICA, getLocale());
			
			strMsgValidarJerarquiaRepetida =  MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_REPETIDA, getLocale());	
			
			strMsgValidarJerarquiaNombre =  MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_NOMBRE, getLocale());	
			
			strMsgValidarJerarquiaNoPadre =  MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ESTRUCTURA_VALIDA_JERARQUIA_NOPADRE, getLocale());				
			

			
		} catch (Exception ex) {
			logger.error("public void incializaMsgTitlesProperties() EstructuraBean.java. Error: " + ex.getMessage(), ex);
		}
	}
	
	public void exportarPdf(){
	
	}
	public void exportarExcel(){
		
	}	
	
	public void retornarPrincipal(){
		String parametros = "recursoId=GestionarEntidadSubMenu"+"&msgAdvertencia="+strMsgEntidadInactiva; /*si se quiere mostrar el mensaje desde el gestionar entidad*/
		parametros = null;
		try {
			finalizarConversacion("/entidad/gestionarEntidad.xhtml", null, parametros);
		} catch (IOException ex) {
			logger.error("public void retornarPrincipal() EstructuraBean.java. Error: " + ex.getMessage(), ex);
		}
	}
	
	public void retornarIndex(){
		try {
			finalizarConversacion(true,null, null);
		} catch (IOException ex) {
			logger.error("public void retornarIndex() EstructuraBean.java. Error: " + ex.getMessage(), ex);
		}
	}
	
	/**
	 * Metodo que inserta la dependencia diligenciada en la tabla maestra dependencia_hoja_vida, esto con el fin
	 * de que las nuevas dependencias puedan ser visualizadas desde el componente de Experiencias en HV.
	 * Este metodo llama un servicio que verifica que la dependencia no exista en la tabla dependencia_hoja_vida.
	 * @param dependencia
	 */
	public void almacenarMaestroDependenciaHV(DependenciaEntidadExt dependencia) {
		DependenciaHojaVida infoDependenciaHV = new DependenciaHojaVida();
		infoDependenciaHV.setAudCodRol(new BigDecimal(codAudCodRol));
		infoDependenciaHV.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
		infoDependenciaHV.setAudFechaActualizacion(new Date());
		infoDependenciaHV.setAudAccion(new BigDecimal(codAudAccionInsert));		
		infoDependenciaHV.setFlgActivo((short)1);
		infoDependenciaHV.setNombreDependencia(dependencia.getNombreDependencia());
		DependenciaHojaVida DependenciaHVGuardado = ComunicacionServiciosSis.setDependenciaHojaVidaDesdeEstructura(infoDependenciaHV);
	}
	
	public void postProcessXLS(Object document) {
		excelOpt = new ExcelOptions();
	    excelOpt.setFacetBgColor("#F88017");
	    excelOpt.setFacetFontSize("10");
	    excelOpt.setFacetFontColor("#0000ff");
	    excelOpt.setFacetFontStyle("BOLD");
	    excelOpt.setCellFontColor("#00ff00");
	    excelOpt.setCellFontSize("8");
		HSSFWorkbook wb = (HSSFWorkbook) document;
	    HSSFSheet sheet = wb.getSheetAt(0);
	    HSSFRow header = sheet.getRow(0);
	    HSSFCellStyle cellStyle = wb.createCellStyle();  
	    cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
	    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
        }
	}
	
	/**
	 * 
	 * @return
	 */
    private boolean validarPermisoRolEstructuraOrganizacional(){
    	try {
    		return (usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.JEFE_TALENTO_HUMANO));
		} catch (SIGEP2SistemaException ex) {
			logger.error("public void validarPermisoRolEstructuraOrganizacional() EstructuraBean.java. Error: " + ex.getMessage(), ex);
			return false;
		}
    }
    
	/**
	 * 
	 * @param evt
	 */
	 public void acNombreNormaSelectListener(ValueChangeEvent  estructuraExt) {
		 Norma estr = (Norma) estructuraExt.getNewValue();
	        if (estr != null && estr.getNumeroNorma() != null)
	        	this.normaEstructura = estr;
	 }
	 /**
	  * 
	  * @param estructuraExt
	  */
	 public void acNormaSelectListener(SelectEvent evt) {
		 if(normaSeleccionada != null && this.normaSeleccionada.getNumeroNorma()!=null) 
			 normaEstructura.setNumeroNorma(normaSeleccionada.getNumeroNorma());
	 }
	 /**
	  * @param estructuraExt
	  * @return
	  */
	 public List<Norma> acNombreNormaListener(String  estructuraExt) {
		 Norma norm = new Norma();
		 norm.setCodTipoNorma(normaEstructura.getCodTipoNorma());
		 norm.setFechaNorma(normaEstructura.getFechaNorma());
		 norm.setNumeroNorma(estructuraExt);
		 List<Norma> normas =  ComunicacionServiciosEnt.getFiltroNormaLike(norm);
	     if(normas == null) 
	    	 return new ArrayList<>();
	     return normas;
	 }
	
	public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
         
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerGeoMap = center.getLat() + "," + center.getLng();
             
            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
        }
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
		segundaDirecion = segundaDirecion == "" ? "" : " -" + segundaDirecion;
		this.editarDireccion.setDireccionGenerada(primerDirecion + segundaDirecion);
	}
	
	public void agergarDireccion() {
		this.dependenciaEnGestion.setDireccionFisica(this.editarDireccion.getDireccionGenerada());
		this.estadoPanelDireccion = false;
		this.geolocalizarDireccion();
	}

	public void configurarDependenciaEspecial(DependenciaEntidadExt depen){
		this.dependenciaEnGestion = depen;
		if(depen.getFlgEspecial() == 1) {
			this.cambiarEstadoVentanas(false);
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "LEY 489 DE 1998, ARTÍCULO 54, LITERAL j: Se podrán fusionar, suprimir o crear dependencias internas en cada entidad u organismo administrativo, y podrá otorgárseles autonomía administrativa y financiera sin personería jurídica");
			RequestContext.getCurrentInstance().execute("ocultarTabs();");
			this.cambiarBanderaPais();
			this.agregarIndicativoDepartamento();
			this.centroMapa = "4.5990577, -74.0756889";
			this.modeloMapa = new DefaultMapModel();
		}else{
			depen.setCodigoDane(null);
			depen.setCodDepartamento(null);
			depen.setCodMunicipio(null);
			depen.setCodTipDependenciaEspecial(null);
			depen.setDireccionFisica(null);
			depen.setPaginaWeb(null);
			depen.setCorreoInstitucional(null);
			depen.setFax(null);
			depen.setIndicador(null);
			depen.setSigla(null);
			depen.setTelefono(null);
			depen.setUbicacionGeo(null);
		}
	}
	
	public void cambiarEstadoVentanas(boolean b) {
		this.estadoPnlDepEspecial = !b;
		this.estadoPnlform = b;
		RequestContext.getCurrentInstance().execute("mostrarTabs();");
	}
	
	public String obtenerRecursoCodificacion(String clave) {
		try {
			return new String((TitlesBundleConstants.getStringMessagesBundle(clave, this.getLocale()).getBytes(Charset.defaultCharset())), "unicode");
		}
		catch(Exception ex) {
			return clave;
		}
	}
	
	public boolean validarDuplicidadNombre(DependenciaEntidadExt dep){
		if(!this.lstDependenciasEntidad.isEmpty()) {
			for(DependenciaEntidadExt d : this.lstDependenciasEntidad){
				try {
					if(d != dep && d.getNombreDependencia().equalsIgnoreCase(dep.getNombreDependencia())) {
						this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_DEPENDENCIA_EXISTE_MENSAJE);
						dep.setNombreDependencia("");
						return false;
					}
				} catch (NullPointerException e) {
					this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_INGRESAR_INFO_DEPENDENCIA);
					return false;
				}
			}	
		}
		return true;
	}
	
	public boolean validarVacios() {
		for(DependenciaEntidadExt d : this.lstDependenciasEntidad){
			if(d.getJerarquia() == null || d.getJerarquia().isEmpty() || d.getNombreDependencia() == null || d.getNombreDependencia().isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Existen registros con jerarquia o nombre de dependencia vacios, por favor diligencie estos campos.");
				return false;
			}
		}
		return true;
	}

	public boolean isEstadoCambios() {
		return estadoCambios;
	}
	
	public void cambiarBanderaPais() {
		Pais pais = null;
		if (this.entidadActual.getCodPais() != null) 
			pais = ComunicacionServiciosSis.getpisporid(entidadActual.getCodPais().longValue());
		else {
			pais = ComunicacionServiciosSis.getpisporid(169l);
			this.entidadActual.setCodPais(new BigDecimal(169l));	
		}
		if (pais.getNombrePais()!=null&& !pais.getNombrePais().equalsIgnoreCase("COLOMBIA")) {
			this.dependenciaEnGestion.setCodDepartamento(null);
			this.dependenciaEnGestion.setCodMunicipio(null);
		} 
		if (pais.getIndicativoPais() != null)
			this.dependenciaEnGestion.setIndicador(pais.getIndicativoPais().toString());
		if (pais.getBanderaUrl() != null)
			this.setBanderaPais(pais.getBanderaUrl().toLowerCase());
		else
			this.setBanderaPais("/resources/images/banderas/default.png");
	}
	
	public void agregarIndicativoDepartamento() {
		if(this.dependenciaEnGestion.getCodDepartamento() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(entidadActual.getCodPais().longValue());
			Departamento departamento = ComunicacionServiciosSis.getdeptoporid(this.dependenciaEnGestion.getCodDepartamento().longValue());
			String indicativo = pais.getIndicativoPais() + departamento.getIndicativo();
			this.dependenciaEnGestion.setIndicador(indicativo);
			this.geolocalizarDireccion();
		}
		
	}
	
	public void configurarCampoDepEspecial() {
		for(DependenciaEntidadExt dep : this.lstDependenciasEntidad) 
			dep.setFlgDependenciaEspecial(dep.getFlgEspecial() != null && dep.getFlgEspecial() == 1);
	}
	
	/**
     * Este método se ejecuta al realizar la geolocalización; el llamado lo hace
     * el <code>ajax</code> del componente <code>GMap</code>
     *
     * @param evt
     */
    public void geolocalizacionListener(GeocodeEvent evt) {
        this.modeloMapa = new DefaultMapModel();
        List<GeocodeResult> results = evt.getResults();
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centroMapa = center.getLat() + "," + center.getLng();
            this.dependenciaEnGestion.setUbicacionGeo(BigDecimal.valueOf(center.getLat()) + "*" + BigDecimal.valueOf(center.getLng()));
            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                getModeloMapa().addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
        } else 
            this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_EXISTE, getLocale()));
    }
    
    public void geolocalizarDireccion() {
        StringBuilder sbScript = new StringBuilder();
        if (this.dependenciaEnGestion.getCodDepartamento() != null || this.dependenciaEnGestion.getCodMunicipio() != null || (this.dependenciaEnGestion.getDireccionFisica() != null && !this.dependenciaEnGestion.getDireccionFisica().isEmpty())) {
            sbScript.append("PF('wvMapaEntidad').geocode('");
            if (this.dependenciaEnGestion.getDireccionFisica() != null && !this.dependenciaEnGestion.getDireccionFisica().isEmpty())
                sbScript.append(this.dependenciaEnGestion.getDireccionFisica()).append(", ");
            if(this.dependenciaEnGestion.getCodMunicipio() != null) {
                Municipio mun = ComunicacionServiciosSis.getMunicipiosid(this.dependenciaEnGestion.getCodMunicipio().longValue());
                if (mun != null) 
                    sbScript.append(mun.getNombreMunicipio()).append(", ");
            }
            if(this.dependenciaEnGestion.getCodDepartamento() != null) {
                Departamento depto = ComunicacionServiciosSis.getdeptoporid(this.dependenciaEnGestion.getCodDepartamento().longValue());
                if (depto != null) 
                    sbScript.append(depto.getNombreDepartamento());
            }
            if(this.dependenciaEnGestion.getCodDepartamento() == null || this.dependenciaEnGestion.getCodMunicipio() == null)
            	this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Debe seleccionar el dapartamento y la ciudad");
            sbScript.append("')");
        }
        if (sbScript.length() > 0) {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute(sbScript.toString());
        }
    }

    /*Get y Set*/
    
	/**
	 * @return the dependenciaEliminar
	 */
	public DependenciaEntidadExt getDependenciaEliminar() {
		return dependenciaEliminar;
	}

	/**
	 * @param dependenciaEliminar the dependenciaEliminar to set
	 */
	public void setDependenciaEliminar(DependenciaEntidadExt dependenciaEliminar) {
		this.dependenciaEliminar = dependenciaEliminar;
	}
	public DefaultMapModel getModeloMapa() {
		return modeloMapa;
	}

	public void setModeloMapa(DefaultMapModel modeloMapa) {
		this.modeloMapa = modeloMapa;
	}

	public String getCentroMapa() {
		return centroMapa;
	}

	public void setCentroMapa(String centroMapa) {
		this.centroMapa = centroMapa;
	}
    
    public String getBanderaPais() {
		return banderaPais;
	}
	
	public void setBanderaPais(String banderaPais) {
		this.banderaPais = banderaPais;
	}
    
	public List<DependenciaEntidadExt> getLstDependenciasEntidad() {
		return lstDependenciasEntidad;
	}

	public void setLstDependenciasEntidad(List<DependenciaEntidadExt> lstDependenciasEntidad) {
		this.lstDependenciasEntidad = lstDependenciasEntidad;
	}

	public boolean isVerFormularioAgregarDependencia() {
		return verFormularioAgregarDependencia;
	}

	public void setVerFormularioAgregarDependencia(boolean verFormularioAgregarDependencia) {
		this.verFormularioAgregarDependencia = verFormularioAgregarDependencia;
	}

	public static Logger getLogger() {
		return logger;
	}

	public EstructuraExt getEstructuraEntidad() {
		return estructuraEntidad;
	}
	
	public void mostrarPanelEditarDireccion(boolean value) {
		this.estadoPanelDireccion = value;
	}

	public boolean isEstadoPanelDireccion() {
		return estadoPanelDireccion;
	}

	public void setEstadoPanelDireccion(boolean estadoPanelDireccion) {
		this.estadoPanelDireccion = estadoPanelDireccion;
	}

	public void setEstructuraEntidad(EstructuraExt estructuraEntidad) {
		this.estructuraEntidad = estructuraEntidad;
	}

	public Entidad getEntidadActual() {
		return entidadActual;
	}

	public void setEntidadActual(Entidad entidadActual) {
		this.entidadActual = entidadActual;
	}

	public GChartModel getArbolOrganizacional() {
		return arbolOrganizacional;
	}

	public void setArbolOrganizacional(GChartModel arbolOrganizacional) {
		this.arbolOrganizacional = arbolOrganizacional;
	}
	
	public String getStrTipoDependenciaBuscar() {
		return strTipoDependenciaBuscar;
	}

	public void setStrTipoDependenciaBuscar(String strTipoDependenciaBuscar) {
		this.strTipoDependenciaBuscar = strTipoDependenciaBuscar;
	}
	
	public String getStrMensajeDialog() {
		return strMensajeDialog;
	}

	public void setStrMensajeDialog(String strMensajeDialog) {
		this.strMensajeDialog = strMensajeDialog;
	}
	
	public boolean isEstadoPnlform() {
		return estadoPnlform;
	}

	public void setEstadoPnlform(boolean estadoPnlform) {
		this.estadoPnlform = estadoPnlform;
	}
	
	public void mostrarMapa() {
		this.estadoPnlMap = !this.estadoPnlMap;
	}

	public boolean isTabDepenEspecial() {
		return tabDepenEspecial;
	}

	public void setTabDepenEspecial(boolean tabDepenEspecial) {
		this.tabDepenEspecial = tabDepenEspecial;
	}

	public boolean isLbverOrganigrama() {
		return lbverOrganigrama;
	}

	public void setLbverOrganigrama(boolean lbverOrganigrama) {
		this.lbverOrganigrama = lbverOrganigrama;
	}
	
	public EditarDireccionDTO getEditarDireccion() {
		return editarDireccion;
	}

	public void setEditarDireccion(EditarDireccionDTO editarDireccion) {
		this.editarDireccion = editarDireccion;
	}
	
	public boolean isEstadoPnlMap() {
		return estadoPnlMap;
	}

	public void setEstadoPnlMap(boolean estadoPnlMap) {
		this.estadoPnlMap = estadoPnlMap;
	}
	
	public boolean isEstadoPnlDepEspecial() {
		return estadoPnlDepEspecial;
	}

	public void setEstadoPnlDepEspecial(boolean estadoPnlDepEspecial) {
		this.estadoPnlDepEspecial = estadoPnlDepEspecial;
	}
	
	public void setEstadoCambios(boolean estadoCambios) {
		this.estadoCambios = estadoCambios;
	}

	public boolean isEstadoBtnImpre() {
		return estadoBtnImpre;
	}

	public void setEstadoBtnImpre(boolean estadoBtnimpre) {
		this.estadoBtnImpre = estadoBtnimpre;
	}

	public String getMensajeToolOrga() {
		return mensajeToolOrga;
	}

	public void setMensajeToolOrga(String mensajeToolOrga) {
		this.mensajeToolOrga = mensajeToolOrga;
	}
	 
	public DependenciaEntidadExt getDependenciaEnGestion() {
		return dependenciaEnGestion;
	}

	public void setDependenciaEnGestion(DependenciaEntidadExt dependenciaEnGestion) {
		this.dependenciaEnGestion = dependenciaEnGestion;
	}

	public MapModel getGeoModel() {
		return geoModel;
	}

	public void setGeoModel(MapModel geoModel) {
		this.geoModel = geoModel;
	}
	
	public String getCenterGeoMap() {
        return centerGeoMap;
    }
	
	public void getCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }
	
	public String getStrNombreDependenciaBuscar() {
		return strNombreDependenciaBuscar;
	}

	public void setStrNombreDependenciaBuscar(String strNombreDependenciaBuscar) {
		this.strNombreDependenciaBuscar = strNombreDependenciaBuscar;
	}

	public String getStrNumJerarquiaBuscar() {
		return strNumJerarquiaBuscar;
	}

	public void setStrNumJerarquiaBuscar(String strNumJerarquiaBuscar) {
		this.strNumJerarquiaBuscar = strNumJerarquiaBuscar;
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
	 * @return the tieneestructura
	 */
	public boolean isTieneestructura() {
		return tieneestructura;
	}

	/**
	 * @param tieneestructura the tieneestructura to set
	 */
	public void setTieneestructura(boolean tieneestructura) {
		this.tieneestructura = tieneestructura;
	}

	/**
	 * @return the normaEstructura
	 */
	public Norma getNormaEstructura() {
		if(normaEstructura==null)
			return new Norma();		
		return normaEstructura;
	}

	/**
	 * @param normaEstructura the normaEstructura to set
	 */
	public void setNormaEstructura(Norma normaEstructura) {
		this.normaEstructura = normaEstructura;
	}

	public List<SelectItem> getSiDependenciaspadre() {
		return siDependenciaspadre;
	}

	public void setSiDependenciaspadre(List<SelectItem> siDependenciaspadre) {
		this.siDependenciaspadre = siDependenciaspadre;
	}
	
	private void llenaritemsDependenciapadre(){
		for(DependenciaEntidadExt dep:lstDependenciasEntidad){
			siDependenciaspadre.add(new SelectItem(dep.getJerarquia(), dep.getJerarquia()));
		}
	}
	private void agregaritemDependenciapadre(String jerarquia){
		if (!siDependenciaspadre.contains(jerarquia))
				siDependenciaspadre.add(new SelectItem(jerarquia, jerarquia));
	}
	
	public void imprimirDependenciasPdf(){
		InitialContext initialContext;
		DataSource dataSource = null;
		HttpServletResponse response ;
		 ServletOutputStream stream;
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:/SIGEP2DS");
			String jasperPath = "/resources/jasper/entidades/estructuraentidad/dependencias/reporteDependenciasEntidad.jrxml";
			String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
			String subReportDir = "/resources/jasper/entidades/estructuraentidad/dependencias/";
			subReportDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath(subReportDir);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("P_CODIGO_ENTIDAD", String.valueOf((this.entidadActual.getCodEntidad())));
			parameters.put("P_SUBREPORT_DIR", subReportDir+"/");

			JasperReport report = JasperCompileManager.compileReport(relativePath);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());
			/* Download */
			response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse(); 
			response.addHeader("Content-disposition", "attachment; filename=" +"DEPENDENCIAS" + ".pdf");
			try {
				stream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(print, stream); 
				stream.flush(); 
				stream.close(); 
				FacesContext.getCurrentInstance().renderResponse();
				FacesContext.getCurrentInstance().responseComplete();
				dataSource.getConnection().close(); 
			}catch (IOException e1) { 
				  e1.printStackTrace(); 
			}
			 

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
}