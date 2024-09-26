package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.extensions.component.gchart.model.GChartModelBuilder;
import org.primefaces.extensions.component.gchart.model.GChartType;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.GrupoDependencia;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EstructuraExt;
import co.gov.dafp.sigep2.mbean.ext.GrupoDependenciaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
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
 * @author Andrés Jaramillo
 * @Proyect SIGEPII
 * @Company ADA SA
 * @Module Entidades
 * @Fecha 04-09-2018
 */
@Named
@ManagedBean
@ViewScoped
public class GruposTrabajoBean extends BaseBean implements Serializable {
	
    private static final long serialVersionUID = 7154680339675219230L;
    private Long codTipoNorma;
    private Long codNorma;
    private List<GrupoDependenciaExt> gruposEntidad;
    private List<GrupoDependenciaExt> gruposSeleccionados;
    private Entidad entidadProceso;
    private Norma normaFiltro;
    private Norma normaSeleccionada;
    private Map<String, Long> menuNormas;
    private List<Norma> listaNormas;
    private String justificacion;
    private GrupoDependenciaExt grupoFiltro; 
    private Map<String, Long> listaDependencias;
    private Map<String, Long> listaGrupos;
    private short guardadoParcial;
    private Long indice;
    ExternalContext externalContext;
    private EstructuraExt estructuraEntidad;
    private boolean estadoPanelDireccion;
    private int tabindex;
    private GrupoDependenciaExt grupoEliminar;
    private boolean estadoPnlDepEspecial;
    private GrupoDependencia grupoEnGestion;
    private boolean estadoCreGru;
    private EditarDireccionDTO edicionDireccion;
    private GChartModel standardModel = null;  
    private GChartModel treeModel = null;  
     private String banderaPais;
    private DefaultMapModel modeloMapa;
    private String centroMapa;
    private String centerGeoMap;
    private boolean lbverOrganigrama;
  
    public GChartModel getStandardModel(){  
        return standardModel;  
    }  
  
    public GChartModel getTreeModel() {  
        return treeModel;  
    }  
    
    public EditarDireccionDTO getEdicionDireccion() {
		return edicionDireccion;
	}

	public void setEdicionDireccion(EditarDireccionDTO edicionDireccion) {
		this.edicionDireccion = edicionDireccion;
	}

	public boolean isEstadoCreGru() {
		return estadoCreGru;
	}

	public void setEstadoCreGru(boolean estadoCreGru) {
		this.estadoCreGru = estadoCreGru;
	}
	
	public Long getCodTipoNorma() {
		return codTipoNorma;
	}

	public void setCodTipoNorma(Long codTipoNorma) {
		this.codTipoNorma = codTipoNorma;
	}

	public Long getCodNorma() {
		return codNorma;
	}

	public void setCodNorma(Long codNorma) {
		this.codNorma = codNorma;
	}

	public List<GrupoDependenciaExt> getGruposEntidad() {
		return gruposEntidad;
	}

	public void setGruposEntidad(List<GrupoDependenciaExt> gruposEntidad) {
		this.gruposEntidad = gruposEntidad;
	}

	public List<GrupoDependenciaExt> getGruposSeleccionados() {
		return gruposSeleccionados;
	}

	public void setGruposSeleccionados(List<GrupoDependenciaExt> gruposSeleccionados) {
		this.gruposSeleccionados = gruposSeleccionados;
	}

	public Entidad getEntidadProceso() {
		return entidadProceso;
	}

	public void setEntidadProceso(Entidad entidadProceso) {
		this.entidadProceso = entidadProceso;
	}

	public List<Norma> getListaNormas() {
		return listaNormas;
	}

	public void setListaNormas(List<Norma> listaNormas) {
		this.listaNormas = listaNormas;
	}

	public Map<String, Long> getMenuNormas() {
		return menuNormas;
	}

	public void setMenuNormas(Map<String, Long> menuNormas) {
		this.menuNormas = menuNormas;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public GrupoDependenciaExt getGrupoFiltro() {
		return grupoFiltro;
	}

	public void setGrupoFiltro(GrupoDependenciaExt grupoFiltro) {
		this.grupoFiltro = grupoFiltro;
	}

	public Map<String, Long> getListaDependencias() {
		return listaDependencias;
	}

	public void setListaDependencias(Map<String, Long> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public Map<String, Long> getListaGrupos() {
		return listaGrupos;
	}
	
	public void setListaGrupos(Map<String, Long> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}
	
	public boolean isEstadoPanelDireccion() {
		return estadoPanelDireccion;
	}

	public void setEstadoPanelDireccion(boolean estadoPanelDireccion) {
		this.estadoPanelDireccion = estadoPanelDireccion;
	}

	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
	}
	
	public Norma getNormaFiltro() {
		if(normaFiltro == null) normaFiltro= new Norma();
		return normaFiltro;
	}

	public void setNormaFiltro(Norma normaFiltro) {
		this.normaFiltro = normaFiltro;
	}

	public boolean isEstadoPnlDepEspecial() {
		return estadoPnlDepEspecial;
	}

	public void setEstadoPnlDepEspecial(boolean estadoPnlDepEspecial) {
		this.estadoPnlDepEspecial = estadoPnlDepEspecial;
	}
	
	public GrupoDependencia getGrupoEnGestion() {
		return grupoEnGestion;
	}

	public void setGrupoEnGestion(GrupoDependencia grupoEnGestion) {
		this.grupoEnGestion = grupoEnGestion;
	}
	
	public short getGuardadoParcial() {
		return guardadoParcial;
	}

	public void setGuardadoParcial(short guardadoParcial) {
		this.guardadoParcial = guardadoParcial;
	}
	
	public Long getIndice() {
		return indice;
	}

	public void setIndice(Long indice) {
		this.indice = indice;
	}
	
	public Norma getNormaSeleccionada() {
		return normaSeleccionada;
	}

	public void setNormaSeleccionada(Norma normaSeleccionada) {
		this.normaSeleccionada = normaSeleccionada;
	}
	
	public String getBanderaPais() {
		return banderaPais;
	}

	public void setBanderaPais(String banderaPais) {
		this.banderaPais = banderaPais;
	} 
	
	public GruposTrabajoBean() {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		this.centerGeoMap = "41.850033, -87.6500523";
    	try {
    		externalContext = FacesContext.getCurrentInstance().getExternalContext();
    		this.entidadProceso = ComunicacionServiciosEnt.getEntidadPorId(((BigDecimal) externalContext.getSessionMap().get("codEntidad")).longValue());
    		this.obtenerEstructura();
    		this.edicionDireccion = new EditarDireccionDTO();
	    	this.gruposSeleccionados = new ArrayList<>();
	    	this.gruposEntidad = new ArrayList<>();
	    	GrupoDependenciaExt grupoFil = new GrupoDependenciaExt();
	    	grupoFil.setFlgActivo((short) 1);
	    	grupoFil.setCodEntidad(this.entidadProceso.getCodEntidad().longValue());
	    	this.gruposSeleccionados = ComunicacionServiciosEnt.getGrupoDependenciasFiltroEntidad(grupoFil);
	    	this.grupoFiltro = new GrupoDependenciaExt();
	    	this.generarMaps();
	    	this.generateModel();
	    	this.indice = -1L;
	    	this.estadoCreGru = true;
	    	this.grupoEnGestion = new GrupoDependencia();
	    	this.banderaPais = "/resources/images/banderas/co.png";
	    	lbverOrganigrama = false;
    	}
    	catch(Exception ex){
    		this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha generado un error al cargar la página.");
    	}
    }
    
    public void generarMaps(){
    	this.listaGrupos = new HashMap<>();
    	GrupoDependenciaExt gruFil = new GrupoDependenciaExt();
    	gruFil.setFlgActivo((short) 1);
    	gruFil.setCodEntidad(this.entidadProceso.getCodEntidad().longValue());
    	List<GrupoDependenciaExt> grupos = ComunicacionServiciosEnt.getGrupoDependenciasFiltroEntidad(gruFil);
    	for(GrupoDependenciaExt g : grupos)
    		this.listaGrupos.put(g.getNombreGrupoDependencia(), g.getCodGrupoDependencia());
    }
    
    public void setValorRegionalSeccional(GrupoDependenciaExt grupo) {
		try {
			grupo.setDesRegionalSeccionalZonal(grupo.getFlgRegionalSeccionalZonal() != null && grupo.getFlgRegionalSeccionalZonal() == 1 ? "Si" : "No");
		}
		catch(Exception ex) {
			logger.error("Error setValorObjeto() GestionarPlantaBean()", ex);
		}
	}
    
    public void celdaEditada(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if(newValue != null && !newValue.equals(oldValue)) {
        }
    }
    
    public void obtenerNormas() {
    	try {
	    	this.listaNormas = ComunicacionServiciosEnt.getFiltroNorma(this.normaFiltro);
	    	this.menuNormas.clear();
	    	for(Norma n : this.listaNormas)
	    		this.menuNormas.put(n.getNumeroNorma(), n.getCodNorma());
    	}catch(Exception ex) {
    		this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Faltan datos", "Error obteniendo normas");
    	}
    }
    
    public void verificarDatosNorma() {
    	if(this.normaFiltro.getCodTipoNorma() == null && this.normaFiltro.getNumeroNorma() == null && this.normaFiltro.getFechaNorma() == null && (this.justificacion == null || this.justificacion.isEmpty())){
    		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Faltan datos", "No ha diligenciado los datos relacionados con la norma o la justificación");
    	}
    }
    
    public void buscarGruposPorNombre() {
    	try {
	    	this.grupoFiltro.setCodEntidad(this.entidadProceso.getCodEntidad().longValue());
	    	this.gruposSeleccionados = ComunicacionServiciosEnt.getGrupoDependenciasFiltroEntidad(this.grupoFiltro);
	    	this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "La búsqueda ha finalizado");
    	}catch(Exception ex) {
    		this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha generado un error al realizar la busqeuda");
    	}
    }
    
    public void adicionarGrupo() {
    	if(!this.gruposSeleccionados.isEmpty()){
    		GrupoDependencia grupo = this.gruposSeleccionados.get(this.gruposSeleccionados.size() - 1);
	    	if(grupo.getCodDependenciaEntidad() == null) {
	    		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "No ha diligenciado la dependencia del grupo");
	    		return; 
	    	}
	    	if(grupo.getNombreGrupoDependencia() == null){
	    		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "No ha diligenciado el nombre del grupo");
	    		return; 
	    	}
    	}
    	GrupoDependenciaExt grup = new GrupoDependenciaExt();
    	grup.setCodGrupoDependencia(indice);
    	this.indice--;
    	this.gruposSeleccionados.add(0, grup);
    }
    
    public boolean verificarDuplicidadNombre() {
    	for(GrupoDependenciaExt grup: this.gruposSeleccionados) {
	    	for(GrupoDependenciaExt grupo: this.gruposSeleccionados) {
	    		if(grup.getNombreGrupoDependencia().equalsIgnoreCase(grupo.getNombreGrupoDependencia()) && (grup != grupo)) {
	    			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, "Estimado usuario", "El nombre de un grupo ingresado ya existe por favor ingrese un nuevo nombre para este grupo.");
	    			return true;
	    		}
	    	}
    	}
    	return false;
    }
    
    public void verificarDuplicidadNombre(GrupoDependenciaExt grupo) {
    	if(grupo.getNombreGrupoDependencia().equalsIgnoreCase("")) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, "Estimado usuario", "No ha ingresado ningún nombre para el grupo.");
			grupo.setNombreGrupoDependencia("");
			return;
		}
    	for(GrupoDependenciaExt grup: this.gruposSeleccionados) {
    		if(grup.getNombreGrupoDependencia().equalsIgnoreCase(grupo.getNombreGrupoDependencia()) && (grup != grupo)) {
    			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, "Estimado usuario", "Un nombre de grupo ingresado ya existe por favor ingrese un nuevo nombre para este grupo.");
    			grupo.setNombreGrupoDependencia("");
    			return;
    		}
    	}
    }
    
    public String obtenerNombreDependencia(Long id) {
    	try {
	    	if(id == null)
	    		return "Seleccionar";
	    	DependenciaEntidadExt dependenciaFiltro = new DependenciaEntidadExt();
	    	dependenciaFiltro.setFlgActivo((short) 1);
	    	dependenciaFiltro.setCodDependenciaEntidad(id);
	    	List<DependenciaEntidadExt> depen = ComunicacionServiciosVin.getDependenciaEntidadFilter(dependenciaFiltro);
	    	if(depen.isEmpty())
	    		return "Dato no configurado";
	    	else
	    		return depen.get(0).getNombreDependencia();
    	}catch(Exception ex) {
    		logger.info("Error obtenerNombreDependencia(Long id)", ex);
    		return "Error recuperando dato";
    	}
    }
    
    public String obtenerNombreGrupo(Long id){
    	try {
	    	if(id == null)
	    		return "Seleccionar";
	    	GrupoDependenciaExt gruDep = ComunicacionServiciosEnt.getGrupoDependenciaPorId(id);
	    	if(gruDep == null)
	    		return "Dato no configurado";
	    	else
	    		return gruDep.getNombreGrupoDependencia();
    	}catch(Exception ex) {
    		logger.info("Error obtenerNombreGrupo(Long id)", ex);
    		return "Error recuperando dato";
    	}
    }
    
    public void eliminarGrupoTrabajo(GrupoDependenciaExt g) {
    	grupoEliminar = g;
		RequestContext.getCurrentInstance().execute("$('#dlgEliminarGrupo').modal({backdrop: 'static', keyboard: false});");    	
    }
    
    public void eliminarGrupoTrabajoConfirmar(){
    	try {
			if(grupoEliminar.getCodGrupoDependencia() < 0) 
				this.gruposSeleccionados.remove(grupoEliminar);
			else{
				grupoEliminar.setFlgActivo((short) 0);
				grupoEliminar.setAudAccion(63);
				grupoEliminar.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
				grupoEliminar.setAudFechaActualizacion(new Date());
				grupoEliminar.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
				if(!ComunicacionServiciosEnt.setGrupoDependencia(grupoEliminar)){
					this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al guardar la información", "Se ha geneado un error cuando se intentaba guardar la infomracion de los grupos, por favor contacte con una administrador");
					return; 
				}
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Se ha eliminado el registro");
		    	GrupoDependenciaExt grupoFil = new GrupoDependenciaExt();
		    	grupoFil.setFlgActivo((short) 1);
		    	grupoFil.setCodEntidad(this.entidadProceso.getCodEntidad().longValue());
		    	this.gruposSeleccionados = ComunicacionServiciosEnt.getGrupoDependenciasFiltroEntidad(grupoFil);
		    	RequestContext.getCurrentInstance().execute("$('#dlgEliminarGrupo').modal('hide');");
			}
    	}catch(Exception ex) {
    		this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error al guardar la información", "Se ha geneado un error cuando se intentaba guardar la informacion de los grupos.");
    		RequestContext.getCurrentInstance().execute("$('#dlgEliminarGrupo').modal('hide');");
    	}
    }
    
    public void guardadoParcial() {
    	if(this.actualizar((byte) 1))
    		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Guardado parcial exitoso");
    }
    
    
    public void actualizarGrupos() {
    	if(this.actualizar((byte) 0))
    		this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Actualización exitosa");
    }

    public boolean actualizar(byte guardadoPar) {
    	try {
    		if(!this.validarCampoNorma())
    			return false;
    		this.guardarNorma();
    		this.guardarEstructura();
    		boolean lbRespuestaGuardar;
    		for(GrupoDependenciaExt g : this.gruposSeleccionados){
    			
    			Short otros =  g.getFlgOtros();    			
    			
    			if(this.verificarDuplicidadNombre())
    				return false;
    			if(g.getCodGrupoDependencia()==null || g.getCodGrupoDependencia() < 0) {
    				g.setCodGrupoDependencia(null);
    				g.setAudAccion(785);
    			}
    			else
    				g.setAudAccion(63);
    			g.setFlgActivo((short) 1);
    			g.setGuardadoParcial((short) guardadoPar);
    			g.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
    			g.setAudFechaActualizacion(new Date());
    			g.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
    			if(g.getFlgRegionalSeccionalZonal() == null)
    				g.setFlgRegionalSeccionalZonal((short) 0);
    			lbRespuestaGuardar = ComunicacionServiciosEnt.setGrupoDependencia(g);
    			if(!lbRespuestaGuardar)
    				return false;
    		}
	    	GrupoDependenciaExt grupoFil = new GrupoDependenciaExt();
	    	grupoFil.setFlgActivo((short) 1);
	    	grupoFil.setCodEntidad(this.entidadProceso.getCodEntidad().longValue());
	    	this.gruposSeleccionados = ComunicacionServiciosEnt.getGrupoDependenciasFiltroEntidad(grupoFil);    		
    		return true;
    	}catch(Exception ex) {
    		this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha generado un error cuando se intentaba guardar la información de los grupos, por favor contacte con una administrador");
    		return false;
    	}
    }

	@Override
	@PostConstruct
	public void init() {
		if(!validarPermisoRolEstructuraOrganizacional()){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()), "window.location.assign('entidad/../../../gestionarEntidad.xhtml?faces-redirect=true')"); 
		}
	}
	
	public boolean validarCampoNorma() {
		if(this.justificacion != null && !this.justificacion.isEmpty()) {
			if(this.normaFiltro == null) 
				this.normaFiltro = new Norma();
			return true;
		}
		else if(this.normaFiltro != null && this.normaFiltro.getCodTipoNorma() != null && this.normaFiltro.getFechaNorma() != null && this.normaFiltro.getNumeroNorma() != null && !this.normaFiltro.getNumeroNorma().isEmpty())
			return true;
		else {
			this.normaFiltro = new Norma();
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Debe diligenciar el campo justificación o los campos de la norma");
			return false;
		}
			
	}
  
    public void generateModel() {  
    	GChartModelBuilder grafico = new GChartModelBuilder();
    	grafico.setChartType(GChartType.ORGANIZATIONAL);
    	grafico.addColumns("Name","Manager");
    	for(GrupoDependenciaExt gru : this.gruposSeleccionados) 
    		grafico.addRow(gru.getNombreGrupoDependencia(), gru.getCodPredecesor() == null ? "" : ComunicacionServiciosEnt.getGrupoDependenciaPorId(gru.getCodPredecesor()).getNombreGrupoDependencia());
    	grafico.addOption("size", "large");
    	standardModel  = grafico.build();
    }
	
    private boolean validarPermisoRolEstructuraOrganizacional(){
    	try {
			return (usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,  RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.JEFE_TALENTO_HUMANO));
		} catch (SIGEP2SistemaException ex) {
			logger.error("Error private boolean validarPermisoRolEstructuraOrganizacional() GruposTrabajoBean: " + ex.getMessage(), ex);
			return false;
		}
    }

    /**
     * @param estructuraExt
     * @return
     */
    public List<Norma> acNombreNormaListener(String numNorma) {
		 Norma norm = new Norma();
		 norm.setCodTipoNorma(this.normaFiltro.getCodTipoNorma());
		 norm.setFechaNorma(this.normaFiltro.getFechaNorma());
		 norm.setNumeroNorma(numNorma);
		 List<Norma> normas =  ComunicacionServiciosEnt.getFiltroNormaLike(norm);
	     if(normas == null)
	    	 return new ArrayList<>();
	     return normas;
	}
    
    public List<Norma> buscarNorma(String numNorma) {
    	try {
	    	 Norma norm = new Norma();
			 norm.setCodTipoNorma(normaFiltro.getCodTipoNorma());
			 norm.setFechaNorma(normaFiltro.getFechaNorma());
			 norm.setNumeroNorma(numNorma);
			 List<Norma> normas =  ComunicacionServiciosEnt.getFiltroNorma(norm);
		     if(normas == null)
		    	 return new ArrayList<>();
		     return normas;
    	}
    	catch(Exception ex) {
    		logger.error("public List<Norma> buscarNorma(String numNorma) GruposTrabajoBean: " + ex.getMessage(), ex);
    		return null;
    	}
    }
    
	public void acNombreNormaSelectListener(ValueChangeEvent  estructuraExt) {
		 Norma estr = (Norma) estructuraExt.getNewValue();
	        if (estr!=null && estr.getNumeroNorma() != null) {
	        	this.normaFiltro.setNumeroNorma(estr.getNumeroNorma());
	        	this.normaFiltro = estr;
	        	this.normaSeleccionada = this.normaFiltro;
	        }
	}
	 
	public void acNormaSelectListener(SelectEvent evt) {
		
	}
	
	public void guardarNorma() {
		try { 
			if(this.normaSeleccionada != null && this.normaSeleccionada.getNumeroNorma() != null) {
				List<Norma> lista = this.buscarNorma(this.normaSeleccionada.getNumeroNorma());
				 if(lista.isEmpty()) {
					 this.normaSeleccionada.setAudAccion(785);
					 this.normaSeleccionada.setAudFechaActualizacion(new Date());
					 this.normaSeleccionada.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
					 this.normaSeleccionada.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
					 this.normaSeleccionada = ComunicacionServiciosEnt.setNorma(this.normaSeleccionada);
				 }
				 else
					 this.normaSeleccionada.setCodNorma(lista.get(0).getCodNorma());
			 }
		}
		catch(Exception ex){
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Estimado usuario", "Error al procesar la norma");
		}
	}
 
	private void obtenerEstructura(){
		EstructuraExt fil = new EstructuraExt();
		fil.setCodEntidad(this.entidadProceso.getCodEntidad());
		fil.setFlgDependencia((short) 0);
		List<EstructuraExt> resultado = ComunicacionServiciosEnt.obtenerEstructuraFiltro(fil);
		if(resultado != null && !resultado.isEmpty()) {
			this.estructuraEntidad = resultado.get(0);
			if(this.estructuraEntidad!=null && this.estructuraEntidad.getCodNorma()!=null)
				this.normaFiltro = ComunicacionServiciosEnt.getNormaPorId(this.estructuraEntidad.getCodNorma());
			this.justificacion = estructuraEntidad.getJustificacion();
			if(this.normaFiltro==null || this.normaFiltro.getCodNorma() == null)
				this.normaFiltro = new Norma();
		}
		else { 
			this.normaFiltro = new Norma();
			this.estructuraEntidad = new EstructuraExt();
		}	
	}
	
	public void configurarGrupoEspecial(GrupoDependenciaExt depen){ 
		
		this.grupoEnGestion = depen;
		if(depen.getFlgRegionalSeccionalZonal()!=null && depen.getFlgRegionalSeccionalZonal() == 1) {
			estadoPnlDepEspecial = true;
			this.cambiarEstadoVentanas(false);
			if(depen.getFlgOtros() != null && depen.getFlgOtros() == 1)
				depen.setFlgOtros((short) 0);
			this.cambiarBanderaPais();
			this.agregarIndicativoDepartamento();
			this.centroMapa = "4.5990577, -74.0756889";
		}else{
			this.configurarCampos(depen);
			estadoPnlDepEspecial = false;
		}
	}
	
	public void configurarGrupoOtro(GrupoDependenciaExt depen){ 
		this.grupoEnGestion = depen;
		if(depen.getFlgOtros()!=null &&  depen.getFlgOtros() == 1) {
			this.cambiarEstadoVentanas(false);
			if(depen.getFlgRegionalSeccionalZonal() == 1)
				depen.setFlgRegionalSeccionalZonal((short) 0);
			this.cambiarBanderaPais();
			this.agregarIndicativoDepartamento();
		}
		else
			this.configurarCampos(depen);
	}
	
	public void cambiarEstadoVentanas(boolean b) {
		this.estadoPnlDepEspecial = !b;
		this.estadoCreGru = b;
		System.out.println("Grupo "+ this.grupoEnGestion);
	}
	
	public void agergarDireccion() {
		try {
			this.grupoEnGestion.setDireccion(edicionDireccion.getDireccionGenerada());
			this.setEstadoPanelDireccion(false);
			this.geolocalizarDireccion();
		}
		catch(Exception ex) {
			GruposTrabajoBean.logger.error("Error public void agergarDireccion() GruposTrabajoBean.java", ex.getMessage());
		}
	}
	
	public boolean guardarEstructura() {
		this.estructuraEntidad.setCodEntidad(this.entidadProceso.getCodEntidad());
		this.estructuraEntidad.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
		this.estructuraEntidad.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
		this.estructuraEntidad.setAudFechaActualizacion(new Date());
		if(this.normaSeleccionada != null)
			this.estructuraEntidad.setCodNorma(this.normaSeleccionada.getCodNorma());
		this.estructuraEntidad.setJustificacion(this.justificacion);
		if(this.estructuraEntidad.getCodEstructura() != null)
			this.estructuraEntidad.setAudAccion(63);
		else
			this.estructuraEntidad.setAudAccion(785);
		this.estructuraEntidad.setFlgDependencia((short) 0);
		this.estructuraEntidad = ComunicacionServiciosEnt.setEstructura(this.estructuraEntidad);
		return this.estructuraEntidad.isError();
	}
	
	
	public void generarDireccion() {
		String primerDirecion = "";
		String segundaDirecion = "";
		if (edicionDireccion.getTipoVia() != null) 
			primerDirecion = primerDirecion + " " + edicionDireccion.getTipoVia().getSigla();
		if (edicionDireccion.getSegundoNumero() != null && !edicionDireccion.getSegundoNumero().isEmpty())
			segundaDirecion = segundaDirecion + " " + edicionDireccion.getSegundoNumero();
		if (edicionDireccion.getNumero() != null && !edicionDireccion.getNumero().isEmpty()) 
			primerDirecion = primerDirecion + " " + edicionDireccion.getNumero();
		if (edicionDireccion.getTercerLetra() != null) 
			segundaDirecion = segundaDirecion + edicionDireccion.getTercerLetra().getSigla();
		if (edicionDireccion.getPrimerLetra() != null) 
			primerDirecion = primerDirecion + edicionDireccion.getPrimerLetra().getSigla();
		if (edicionDireccion.isBis()) 
			primerDirecion = primerDirecion + " BIS";
		if (edicionDireccion.getSegundaLetra() != null) 
			primerDirecion = primerDirecion + " " + edicionDireccion.getSegundaLetra().getSigla();
		if (edicionDireccion.getOrientacion() != null) 
			primerDirecion = primerDirecion + " " + edicionDireccion.getOrientacion().getSigla();
		if (edicionDireccion.getTercerNumero() != null && !edicionDireccion.getTercerNumero().isEmpty()) 
			segundaDirecion = segundaDirecion + " " + edicionDireccion.getTercerNumero();
		if (edicionDireccion.getSegundaOrientacion() != null) 
			segundaDirecion = segundaDirecion + " " + edicionDireccion.getSegundaOrientacion().getSigla();
		if (edicionDireccion.getComplemento() != null && !edicionDireccion.getComplemento().isEmpty()) 
			segundaDirecion = segundaDirecion + " " + edicionDireccion.getComplemento();
		segundaDirecion = segundaDirecion == "" ? "" : " -" + segundaDirecion;
		edicionDireccion.setDireccionGenerada(primerDirecion + segundaDirecion);
	}
	
	public void cambiarBanderaPais() {
		Pais pais = null;
		if (this.entidadProceso.getCodPais() != null) 
			pais = ComunicacionServiciosSis.getpisporid(this.entidadProceso.getCodPais().longValue());
		else {
			pais = ComunicacionServiciosSis.getpisporid(169l);
			this.entidadProceso.setCodPais(new BigDecimal(169l));	
		}
		if (!pais.getNombrePais().equalsIgnoreCase("COLOMBIA")) {
			this.grupoEnGestion.setCodDepartamento(null);
			this.grupoEnGestion.setCodMunicipio(null);
		} 
		if (pais.getIndicativoPais() != null)
			this.grupoEnGestion.setIndicativo(pais.getIndicativoPais().toString());
		if (pais.getBanderaUrl() != null)
			this.setBanderaPais(pais.getBanderaUrl().toLowerCase());
		else
			this.setBanderaPais("/resources/images/banderas/default.png");
	}
	
	public void agregarIndicativoDepartamento() {
		if(this.grupoEnGestion.getCodDepartamento() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(this.entidadProceso.getCodPais().longValue());
			Departamento departamento = ComunicacionServiciosSis.getdeptoporid(this.grupoEnGestion.getCodDepartamento().longValue());
			String indicativo = pais.getIndicativoPais() + departamento.getIndicativo();
			this.grupoEnGestion.setIndicativo(indicativo);
			this.geolocalizarDireccion();
		}
	}
	
	public void configurarCampos(GrupoDependenciaExt depen) {
		depen.setCodDane(null);
		depen.setCodDepartamento(null);
		depen.setCodMunicipio(null);
		depen.setCodTipDependenciaEspecial(null);
		depen.setDireccion(null);
		depen.setPaginaWeb(null);
		depen.setEmail(null);
		depen.setFax(null);
		depen.setIndicativo(null);
		depen.setSigla(null);
		depen.setTelefono(null);
		depen.setUbicacionGeo(null);
	}
	
	public void geolocalizacionListener(GeocodeEvent evt) {
        this.modeloMapa = new DefaultMapModel();
        List<GeocodeResult> results = evt.getResults();
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centroMapa = center.getLat() + "," + center.getLng();
            this.grupoEnGestion.setUbicacionGeo(BigDecimal.valueOf(center.getLat()) + "*" + BigDecimal.valueOf(center.getLng()));
            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                modeloMapa.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
        } else 
            this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_EXISTE, getLocale()));
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

	public void geolocalizarDireccion() {
        StringBuilder sbScript = new StringBuilder();
        if (this.grupoEnGestion.getCodDepartamento() != null || this.grupoEnGestion.getCodMunicipio() != null || (this.grupoEnGestion.getDireccion() != null && !this.grupoEnGestion.getDireccion().isEmpty())) {
            sbScript.append("PF('wvMapaGrup').geocode('");
            if (this.grupoEnGestion.getDireccion() != null && !this.grupoEnGestion.getDireccion().isEmpty())
                sbScript.append(this.grupoEnGestion.getDireccion()).append(", ");
            if(this.grupoEnGestion.getCodMunicipio() != null) {
                Municipio mun = ComunicacionServiciosSis.getMunicipiosid(this.grupoEnGestion.getCodMunicipio().longValue());
                if (mun != null) 
                    sbScript.append(mun.getNombreMunicipio()).append(", ");
            }
            if(this.grupoEnGestion.getCodDepartamento() != null) {
                Departamento depto = ComunicacionServiciosSis.getdeptoporid(this.grupoEnGestion.getCodDepartamento().longValue());
                if (depto != null) 
                    sbScript.append(depto.getNombreDepartamento());
            }
            if(this.grupoEnGestion.getCodDepartamento() == null || this.grupoEnGestion.getCodMunicipio() == null)
            	this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Debe seleccionar el dapartamento y la ciudad");
            sbScript.append("')");
        }
        if (sbScript.length() > 0) {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute(sbScript.toString());
        }
    }
	
	public void imprimirGruposTrabajoPdf(){
		InitialContext initialContext;
		DataSource dataSource = null;
		HttpServletResponse response ;
		 ServletOutputStream stream;
		try {
			initialContext = new InitialContext();
			dataSource = (DataSource) initialContext.lookup("java:/SIGEP2DS");
			String jasperPath = "/resources/jasper/entidades/estructuraentidad/gruposTrabajo/reporteGrupoTrabajoEntidad.jrxml";
			String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
			String subReportDir = "/resources/jasper/entidades/estructuraentidad/gruposTrabajo/";
			subReportDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath(subReportDir);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("P_CODIGO_ENTIDAD", String.valueOf((this.entidadProceso.getCodEntidad())));
			parameters.put("P_SUBREPORT_DIR", subReportDir+"/");

			JasperReport report = JasperCompileManager.compileReport(relativePath);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());
			/* Download */
			response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse(); 
			response.addHeader("Content-disposition", "attachment; filename=" +"GRUPOS" + ".pdf");
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

	public String getCenterGeoMap() {
		return centerGeoMap;
	}

	public void setCenterGeoMap(String centerGeoMap) {
		this.centerGeoMap = centerGeoMap;
	}

	public boolean isLbverOrganigrama() {
		return lbverOrganigrama;
	}

	public void setLbverOrganigrama(boolean lbverOrganigrama) {
		this.lbverOrganigrama = lbverOrganigrama;
	}
	
	public void verOrganigrama(){
		lbverOrganigrama=true;
	}
	
	
	
	
 
}