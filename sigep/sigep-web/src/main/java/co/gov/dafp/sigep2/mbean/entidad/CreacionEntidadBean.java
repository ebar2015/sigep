package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.ibm.icu.util.Calendar;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Autonomia;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadSistemaRegimen;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.SequenciasSigep;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.DireccionBean;
import co.gov.dafp.sigep2.mbean.ext.AutonomiaExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.DigitoVerificacion;
import co.gov.dafp.sigep2.sistema.Direccion;
import co.gov.dafp.sigep2.sistema.SecuenciasEnum;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.produces.EntidadProduces;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ManagedBean
@ViewScoped
public class CreacionEntidadBean extends BaseBean implements Serializable {

    private static final long serialVersionUID = 5887926694320033471L;
    private boolean disponibleDatosBasicos;
    private boolean disponibleAmbitoAplicacion;
    private boolean disponibleCaracterizacion;
    private boolean tabCaracterizacion =  true;
    private boolean adminfun;
    private boolean rolAdmitido;
    private boolean estadoEntidad;
    private boolean estadoEntidadRol = true;
    private boolean estadoSubOrden;
    private boolean estadoCategoria;
    private boolean noAplicaSistemaCarrera 	= false;
    private boolean cehckSistemaSCarrera  	= false;
    private boolean mostrarJustificacion 	= false;
    protected Entidad entidadN;
    private int tabIndex;
    private int norma;
    private boolean adscrito;
    private static final int NORMA_SI = 1;
    private static final int NORMA_NO = 2;
    private boolean zonaUrbana;
    private boolean editandoDireccion;
    
    private String centroMapa;
    private DefaultMapModel modeloMapa;
	private HashMap<String, String> hmCenterZoneDane;
	private final static String ZONA_MAPA_DEFAULT ="4.6097102, -74.081749";    
    
    private Entidad entidadAdscritaSeleccionada;
    private boolean estadoPanelDireccion;
    private BigDecimal codMunicipio;
    private List<String> autonomia = new ArrayList<>();
    private List<String> ltsAutonomia = new ArrayList<>();

	private String indicativoPais;
    private List<String> codigosEscalaSalarialSeleccionados;
    private List<String> codigosSistemaCarreraSeleccionados;
    private List<String> codigosNaturalezaEmpleoSeleccionados;
    private List<String> codsParaEliminar;
    private final int CODIGO_ESCALA 	= 1;
    private final int CODIGO_SISTEMA 	= 2;
    private final int CODIGO_NATURALEZA = 3;
    private BigDecimal codigoDNP;
    private String	strCodigoDNP;
    private boolean lbEsCrearEntidad, lbEsCrearEntidadMenu, lbEsConsultaEntidad;
    private boolean blnEntidadPrivFuncPublic; 
    private String strClasificacionOrganicaPriv;
    private boolean mostrarOcultarEntidadPostconflicto;
    
    private String objetoNorma;
    private String msg2;
    
    private final int msg1Esp = 2189;
    private final int msg1Eng = 2190;
    private final int msg2Esp = 2191;
    private final int msg2Eng = 2192;
    
    private boolean btGuardar = false, lbPermiteModificarNombreEntidad = true;
    
    protected Norma tipoNorma = new Norma();
    protected List<Norma> normasT = new ArrayList<>();
    private String msg1;
    private Integer codigotiponorma;
    private Integer codOrden;
    private Integer codSectorAdministrativo;
    private Date fechaNorma;
    private EditarDireccionDTO editarDireccion;
    private String direccionFisica;
    private boolean requerido = false;
    private static final String CNT_ERROR = "Error ";
    private int codigoJ;
    private boolean caracterizador;
    
    @Inject
	private EntidadProduces entidadProduces;
    
    /**
	 * @return the editarDireccion
	 */
	public EditarDireccionDTO getEditarDireccion() {
		return editarDireccion;
	}

	/**
	 * @param editarDireccion the editarDireccion to set
	 */
	public void setEditarDireccion(EditarDireccionDTO editarDireccion) {
		this.editarDireccion = editarDireccion;
	}
	
	public boolean isEstadoCategoria() {
		return estadoCategoria;
	}

	public void setEstadoCategoria(boolean estadoCategoria) {
		this.estadoCategoria = estadoCategoria;
	}

	@Inject
    protected DireccionBean direccionBean;

    @PostConstruct
    @Override    
    public void init() {
    	caracterizador = true;
    	cerrarSessionFuncion(AMBITO_POLITICAS);
    	try {
			if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL) || usuarioTieneRolAsignadoSinJerarquia(RolDTO.CARACTERIZADOR_ENTIDAD)) {
				estadoEntidadRol = false;
			}
			if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.CARACTERIZADOR_ENTIDAD) 
				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL) 
				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR)
				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES)
				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)) {
				caracterizador = false;
				 setDisponibleCaracterizacion(false);
	        	 tabCaracterizacion = false;
			}
		} catch (SIGEP2SistemaException e1) {
			estadoEntidadRol = true;
		}
    	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    	this.editarDireccion = new EditarDireccionDTO();
    	adscrito = false;
    	Parametrica mostrarOcultarEntidadPostconflictoParam = ComunicacionServiciosSis.
	    		getParametricaporId(new BigDecimal(TipoParametro.MOSTRAR_OCULTAR_ENTIDAD_POSTCONFLICTO.getValue()));
    	if(mostrarOcultarEntidadPostconflictoParam != null && mostrarOcultarEntidadPostconflictoParam.getValorParametro() != null) {
    		if(mostrarOcultarEntidadPostconflictoParam.getValorParametro().trim().equals("0")) {
    			mostrarOcultarEntidadPostconflicto = false;
    		}else {
    			mostrarOcultarEntidadPostconflicto = true;
    		}
    	}else {
    		mostrarOcultarEntidadPostconflicto = true;
    	}
    	
    	try {
    		if(!lbEsCrearEntidad)
    			this.entidadN = (Entidad) externalContext.getSessionMap().get("entidadModificar");
    			if (entidadN.getNit() != null ) {
    				entidadN.setDigitoVerificacion(Short.parseShort(DigitoVerificacion.calcularDigitoVerificacion(entidadN.getNit())));
    			}
    			
			if(this.entidadN != null) {	
				if(this.entidadN.getJustificacion() != null) {
    				mostrarJustificacion = true;
    			}else {
    				mostrarJustificacion = false;
    			}
    		}
			direccionFisica = (String) externalContext.getSessionMap().get("direccion");
    		if(this.entidadN != null) {
    			if(this.entidadN.getCodMunicipio() != null) {
    				codMunicipio = this.entidadN.getCodMunicipio();
    			}
    		}

            FacesContext facesContext = FacesContext.getCurrentInstance();
            Map<String, String> paramMap = facesContext.getExternalContext().getRequestParameterMap();
            String strIsLinkMenu = paramMap.get("isMenu");
            if(strIsLinkMenu != null && !"".equals(strIsLinkMenu) && "1".equals(strIsLinkMenu)){
            	lbEsCrearEntidad = true;
            }
            
    		if(this.entidadN != null && !lbEsCrearEntidad) {
    			if(entidadN.getCodEstadoEntidad().intValue() == 1480) {
    				setEstadoEntidad(true);
    			}
    			if(this.entidadN.getCodNorma() != null) {
    				tipoNorma = ComunicacionServiciosEnt.getNormaPorId(this.entidadN.getCodNorma().longValue());
    				objetoNorma = tipoNorma.getObjetoNorma();
    			}
    			
    			externalContext.getSessionMap().remove("entidadModificar");
    			List<EntidadSistemaRegimen> esrList = ComunicacionServiciosEnt.getEntidadesSistemaRegimenPorIdEntidad(this.entidadN.getCodEntidad());
    			esrList = this.removerInactivos(esrList);
    			Autonomia aut = new Autonomia();
    			aut.setCodEntidad(this.entidadN.getCodEntidad());
    			aut.setFlgActivo((short) 1);
    			List<AutonomiaExt> lstExt = ComunicacionServiciosEnt.getAutonomia(aut);
    			if(!lstExt.isEmpty()) {
    				for (AutonomiaExt autonomiaExt : lstExt) 
    					autonomia.add(String.valueOf(autonomiaExt.getCodTipoAutonomia()));
    			}
    			if(!autonomia.isEmpty()) 
    				setLtsAutonomia(autonomia);
    	       if(esrList != null) {
    	    	   if(!esrList.isEmpty()) {
    	    		   codigosNaturalezaEmpleoSeleccionados = new ArrayList<>();
    	    		   codigosSistemaCarreraSeleccionados = new ArrayList<>();
    	    		   codigosEscalaSalarialSeleccionados  = new ArrayList<>();
    	    		   for (EntidadSistemaRegimen entidadSistemaRegimenExt : esrList) {
			   				if(entidadSistemaRegimenExt.getCodNaturalezaEmpleo()!= null) 
			   					codigosNaturalezaEmpleoSeleccionados.add(String.valueOf(entidadSistemaRegimenExt.getCodNaturalezaEmpleo()));
			   				if(entidadSistemaRegimenExt.getCodSistemaCarrera()!= null) {
			   					if(entidadSistemaRegimenExt.getCodSistemaCarrera().intValue() == 1459) 
			   						noAplicaSistemaCarrera = true;
			   					else 
			   						codigosSistemaCarreraSeleccionados.add(String.valueOf(entidadSistemaRegimenExt.getCodSistemaCarrera()));
			   				}
			   				if(entidadSistemaRegimenExt.getCodEscalaSalarial()!= null) 
			   					codigosEscalaSalarialSeleccionados.add(String.valueOf(entidadSistemaRegimenExt.getCodEscalaSalarial()));
    	    		   }
    	    	   }
    	    	   if(this.entidadN.getDireccion() == null) 
    	    		   this.entidadN.setDireccion(direccionFisica);
    			}
    	       externalContext.getSessionMap().put("entidadModificar",this.entidadN);
    	       EditarDireccionDTO direccion=null;
    	       if(this.entidadN.getDireccion() != null && !this.entidadN.getDireccion().isEmpty()) {
    	    	   Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    	    	   try {
    	    		   direccion = gson.fromJson(this.entidadN.getDireccion(), EditarDireccionDTO.class);
    	    		   if(direccion != null) {
    	    			   this.editarDireccion = direccion;
    	    		   }
	    	       	} catch(JsonSyntaxException e) {
	    	       		
	    	       	}
    	       }
    	       somCategoriaListener();
    	    	if(this.getEntidadN().getTipoZona()!=null && this.getEntidadN().getTipoZona()==TipoParametro.TIPO_ZONA_URBANA.getValue()){
    	    		zonaUrbana=true;
    	    		mostrarDireccionGenerada();
    	    	}else{
    	    		zonaUrbana=false;
    	    		// Issue 7085
    	    		this.editarDireccion.setComplemento(mostrarDireccionGenerada());
    	    	}
    	        if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.CARACTERIZADOR_ENTIDAD)
    	        	|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL)
    	        	||usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)
    	        	||usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES)
    	        	||usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO)
    	        		) {
    	        	 setDisponibleCaracterizacion(false);
    	        	 tabCaracterizacion =  false;
    	        }else {
    	        	tabCaracterizacion = true;
    	        	 setDisponibleCaracterizacion(true);
    	        }
    	        /*Mapa*/
    	        try {
    	        	if(entidadN.getLongitud()!=null && entidadN.getLatitud()!=null){
        				String dir="";
        				if(direccion!=null && direccion.getDireccionGenerada()!=null)
        					dir = direccion.getDireccionGenerada();
        				modeloMapa = new DefaultMapModel();
        				centroMapa = entidadN.getLatitud().toString().concat(",").concat(entidadN.getLongitud().toString());
        				Marker marker = new Marker(new LatLng(entidadN.getLatitud().doubleValue(), entidadN.getLongitud().doubleValue()), dir);
        				getModeloMapa().addOverlay(marker);	 
        			}else{
        				if(entidadN.getCodMunicipio()!=null)
        					centroMapa = hmCenterZoneDane.get(entidadN.getCodMunicipio());
        				if(centroMapa==null || "".equals(centroMapa))
        					centroMapa = ZONA_MAPA_DEFAULT;
        			}    	
    	        	
    	        	
				} catch (Exception e) {
					centroMapa = ZONA_MAPA_DEFAULT;
				}
    	        
    			        
    		}else {
    			this.entidadN = new Entidad();
    			if (!reiniciarCampos())
    				return;
    		}
    		inicializaCenterZoneByCodDANE();
    		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ENTIDADES);
		}catch (NullPointerException e) {
			if (!reiniciarCampos())
				return;
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
    	this.codsParaEliminar = new ArrayList<>();
    }

    /**
     * Mediante este método se reinician los campos de la interfaz
     */
    public boolean reiniciarCampos() {
        rolAdmitido = false;
        adscrito = false;
        disponibleDatosBasicos = false;
        disponibleCaracterizacion = true;
        tabCaracterizacion = true;
        disponibleAmbitoAplicacion = false;
        estadoEntidad = true;
        estadoSubOrden = false;
        adscrito = false;
        norma = getNORMA_SI();
        tipoNorma = new Norma();
        editandoDireccion = false;
        modeloMapa = new DefaultMapModel();
        lbEsCrearEntidadMenu = false;
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String strIsLinkMenu = paramMap.get("isMenu");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if(strIsLinkMenu != null && !"".equals(strIsLinkMenu) && "1".equals(strIsLinkMenu)){
        	lbEsCrearEntidadMenu = true;
            externalContext.getSessionMap().put("lbEsAmbitoPoliticasEntidad", false);
        }
        externalContext.getSessionMap().put("lbEsCrearEntidad", lbEsCrearEntidadMenu);
        try {
        	if(lbEsCrearEntidadMenu && !validarRolesCreacionEntidad()){
        		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
    					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
    					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
    					"window.location.assign('../index.xhtml?faces-redirect=true')"); 
    			return false;
        	}else if(!validarRolesCreacionEntidad() && lbEsCrearEntidad){
        		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
    					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
    					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
    					"window.location.assign('../index.xhtml?faces-redirect=true')"); 
    			return false;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        entidadN = new EntidadExt();
        indicativoPais = "" + obtenerPaisColombia().getIndicativoPais();
        codigosEscalaSalarialSeleccionados = new ArrayList<>();
        codigosNaturalezaEmpleoSeleccionados = new ArrayList<>();
        codigosSistemaCarreraSeleccionados = new ArrayList<>();
        SequenciasSigep seq = ComunicacionServiciosSis.getSequenciasSigep(SecuenciasEnum.ENTIDAD_CODIGO_SIGEP.name());
        BigDecimal codEntidad =  obtenerCodEntidadPorCodigoSigep(seq.getSequenciaS());
        if(codEntidad != null) {
        	seq = ComunicacionServiciosSis.getSequenciasSigep(SecuenciasEnum.ENTIDAD_CODIGO_SIGEP.name());
        }
        if (seq != null) {
            entidadN.setCodigoSigep(seq.getSequenciaS());
        }
        try {
            adminfun = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL);
            if (!adminfun) 
                entidadN.setCodTipoEntidad(TipoParametro.ENTIDAD_PRIVADA.getValue());
            
        } catch (SIGEP2SistemaException e) {
            adminfun = false;
        }
        lbEsCrearEntidad = true;
        return true;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Acciones de componentes">
    /**
     * Se ejecuta con el evento <i>blur</i> del <code>p:inputText</code> de
     * nombre de entidad
     */
    public void itNombreEntidadListener() {
        entidadN.setNombreEntidad(entidadN.getNombreEntidad().toUpperCase().trim());
        try {
            validarEntidadExistente();
            btGuardar = false;
        } catch (SIGEP2SistemaException ex) {
        	btGuardar = true;
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "", ex.getLocalizedMessage());
        }
    }
    
    public void itDireccionGenerada() {
    	String primerDirecion = "";
		String segundaDirecion = "";
		if (editarDireccion.getTipoVia() != null) 
			primerDirecion = primerDirecion + " " + editarDireccion.getTipoVia().getSigla();
		if (editarDireccion.getSegundoNumero() != null && !editarDireccion.getSegundoNumero().isEmpty()) 
			segundaDirecion = segundaDirecion + " " + editarDireccion.getSegundoNumero();
		if (editarDireccion.getNumero() != null && !editarDireccion.getNumero().isEmpty()) 
			primerDirecion = primerDirecion + " " + editarDireccion.getNumero();
		if (editarDireccion.getTercerLetra() != null) 
			segundaDirecion = segundaDirecion + editarDireccion.getTercerLetra().getSigla();
		if (editarDireccion.getPrimerLetra() != null) 
			primerDirecion = primerDirecion + editarDireccion.getPrimerLetra().getSigla();
		if (editarDireccion.isBis()) 
			primerDirecion = primerDirecion + " BIS";
		if (editarDireccion.getSegundaLetra() != null) 
			primerDirecion = primerDirecion + " " + editarDireccion.getSegundaLetra().getSigla();
		if (editarDireccion.getOrientacion() != null) 
			primerDirecion = primerDirecion + " " + editarDireccion.getOrientacion().getSigla();
		if (editarDireccion.getTercerNumero() != null && !editarDireccion.getTercerNumero().isEmpty()) 
			segundaDirecion = segundaDirecion + " " + editarDireccion.getTercerNumero();
		if (editarDireccion.getSegundaOrientacion() != null) 
			segundaDirecion = segundaDirecion + " " + editarDireccion.getSegundaOrientacion().getSigla();
		if (editarDireccion.getComplemento() != null && !editarDireccion.getComplemento().isEmpty()) 
			segundaDirecion = segundaDirecion + " " + editarDireccion.getComplemento();
		segundaDirecion = segundaDirecion == "" ? "" : " -" + segundaDirecion;
		editarDireccion.setDireccionGenerada(primerDirecion + segundaDirecion);
    }
    
    

    /**
     * Se ejecuta con el evento <i>change</i> de los
     * <code>p:selectOneMenu</code> de departamento y municipio
     */
    public void somDepartamentoMunicipioListener() {
        try {
            validarEntidadExistente();
            btGuardar = false;
			if(entidadN.getCodMunicipio()!=null)
				centroMapa = hmCenterZoneDane.get(entidadN.getCodMunicipio());
			if(centroMapa==null || "".equals(centroMapa))
				centroMapa = ZONA_MAPA_DEFAULT;			
        } catch (SIGEP2SistemaException ex) {
        	btGuardar = true;
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "", ex.getLocalizedMessage());
        }
    }

    /**
     * Se ejecuta con el evento <i>change</i> del <code>p:selectOneRacio</code>
     * de datos de norma
     */
    public void sorNormaListener() {
        switch (norma) {
            case NORMA_SI:
                entidadN.setJustificacion(null);
                break;
        }
    }

    /**
     * Se ejecuta al cambiar el valor del <code>p:selectOneMenu</code> de orden
     */
    public void somOrdenListener() {
    	adscrito = false;
        if (entidadN.getCodOrden() != null && entidadN.getCodOrden().intValue() == TipoParametro.ORDEN_TERRITORIAL.getValue()) {
            setEstadoSubOrden(true);
        } else {
            setEstadoSubOrden(false);
            entidadN.setCodSuborden(null);
            entidadN.setCodCategoria(null);
            setCodigoDNP(null);
        }
        
        //checkEjecutivaAndNacional();
    }
    
    /**
     * Se ejecuta al cambiar el valor de clasificación Orgánica, Orden ó  Naturaleza Jurídica
     */
    public void somCategoriaListener() {
    	estadoCategoria = false;
    	if (entidadN.getCodOrden() != null
                && entidadN.getCodOrden().intValue() == TipoParametro.ORDEN_TERRITORIAL.getValue()) {
            if(entidadN.getCodClasificacionOrganica() != null 
            		&& entidadN.getCodClasificacionOrganica().intValue() == TipoParametro.CLASIF_ORGANICA_RAMA_EJECUTIVA.getValue()) {
            	if(entidadN.getCodNaturaleza() != null 
            			&& (entidadN.getCodNaturaleza().intValue() == TipoParametro.NAT_JURIDICA_ALCALDIA_CAPITAL.getValue() 
            			|| entidadN.getCodNaturaleza().intValue() == TipoParametro.NAT_JURIDICA_ALCALDIA.getValue()
            			|| entidadN.getCodNaturaleza().intValue() == TipoParametro.NAT_JURIDICA_GOBERNACION.getValue()
            			|| entidadN.getCodNaturaleza().intValue() == TipoParametro.NAT_JURIDICA_DISTRITO_ESPECIAL.getValue())) {
            		estadoCategoria = true;
            	}
            }
        }
    }
    
    
    public void somSectorListener() {
        if (codSectorAdministrativo != null) {
        	if (codSectorAdministrativo > 0) {
        		entidadN.setCodSectorAdministrativo(new BigDecimal(codSectorAdministrativo));
        	}
        }
    }

    /**
     * Método que se ejecuta al cambiar valor el en <code>p:selectOneMenu</code>
     * de categoría de entidad
     */
    public void somCategoriaEntidadListener() {
        if (entidadN.getCodCategoria() != null) {
            Parametrica sel = ComunicacionServiciosSis.getParametricaporId(entidadN.getCodCategoria());
            codigoDNP = null;
            if (sel != null) {
                List<Parametrica> dnps = ComunicacionServiciosSis.getParametricaPorIdPadre(TipoParametro.CATEGORIA_DNP.getValue());
                if (dnps != null && !dnps.isEmpty()) {
                    for (Parametrica pr : dnps) {
                        //if (pr.getValorParametro().equals(sel.getValorParametro())) {
                    	if (pr.getNombreParametro().equals(sel.getValorParametro())) {
                            codigoDNP = pr.getCodTablaParametrica();
                            strCodigoDNP = pr.getValorParametro();
                            return;
                        }
                    }
                }
            }
        }
    }

    public void sbbEntidadAdscritaListener() {
    	//NO esta adscrita
        if (entidadN.getCodTipoAdscripcion().intValue() == 2240) {
        	entidadN.setPersoneriaJuridica((short) 1);
            adscrito = false;
        }else {
        	entidadN.setPersoneriaJuridica((short) 0);
        	adscrito = true;
        }
    }

    /**
     * Método que se ejecuta al realizar una consulta por nombre en el
     * <code>p:autocomplete</code> del nombre de entidad adscrita
     *
     * @param query El nombre de la entidad
     * @return El <code>List</code> con las entidades encontradas por el nombre
     * enviado, o <i>null</i> si no encuentra ninguna
     */
    public List<Entidad> acNombreAdscritaListener(String query) {
        if (query != null && !query.isEmpty()) {
            List<Entidad> entidades = ComunicacionServiciosEnt.getEntidadesPorLike(query.toUpperCase());
            return entidades;
        }
        return null;
    }

    /**
     * Se ejecuta al seleccionar un item del autocompletar de entidad vinculada
     *
     * @param evt
     */
    public void acNombreAdscritaSelectListener(SelectEvent evt) {
        if (entidadAdscritaSeleccionada != null) {
            entidadN.setCodEntidadAdscrita(entidadAdscritaSeleccionada.getCodEntidad().intValue());
        }
    }

    /**
     * Se ejecuta al deseleccionar un item del autocompletar de entidad
     * vinculada
     *
     * @param evt
     */
    public void acNombreAdscritaUnselectListener(UnselectEvent evt) {
        setEntidadAdscritaSeleccionada(null);
        entidadN.setCodAdscritaVinculada(null);
    }

    /**
     * Método que se ejecuta al desenfocar el <code>p:inputText</code> de nit,
     * mediante el cual se calcula el dígito de verificación
     */
    public void itNitListener() {
        if (entidadN.getNit() != null) {
            try {
            	Entidad conslta = new Entidad();
            	conslta.setNit(entidadN.getNit());
            	conslta.setFlgActivo((short)1);
            	 List<Entidad> resEntidad = ComunicacionServiciosEnt.getEntidadesFiltro(conslta);
            	 if(resEntidad != null) {
            		 if(!resEntidad.isEmpty()) {
            			 for (Entidad entidad : resEntidad) {
							if(entidadN.getCodEntidad().equals(entidad.getCodEntidad())) {
								entidadN.setNit(entidad.getNit());
								return;
							}
						}
            			 String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_NIT_EXISTE, getLocale());
            			 mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale()), msg);
            			 entidadN.setNit(null);
            			 entidadN.setDigitoVerificacion(null);
            			 return;
            		 }
            	 }
                entidadN.setDigitoVerificacion(Short.parseShort(DigitoVerificacion.calcularDigitoVerificacion(entidadN.getNit())));
            } catch (SIGEP2SistemaException ex) {
                Logger.getLogger(CreacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            entidadN.setDigitoVerificacion(null);
        }
    }

    /**
     * Se ejecuta al accionar el botón de editar dirección
     */
    public void btMostrarEditarDireccionListener() {
        editandoDireccion = true;
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('wvBuiEditar').show()");
        rc.execute("PF('wvOpEditarDireccion').show()");
    }

    /**
     * Se ejecuta al accionar el botón de agregar dirección, actualiza el campo
     * de dirección y cierra el diálogo, además, hace un llamado al método de
     * georreferenciación
     */
    public void btAgregarDireccionListener() {
        entidadN.setDireccion(direccionBean.getDireccionGenerada());
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('wvBuiEditar').hide()");
        rc.execute("PF('wvOpEditarDireccion').hide()");
        geolocalizarDireccion();
        editandoDireccion = false;
    }

    /**
     * Se ejecuta al accionar el botón cancelar del diálogo de dirección, cierra
     * el diálogo y descarta los cambios
     */
    public void btCancelarEditarDireccionListener() {
        direccionBean.setDireccionGenerada(null);
        direccionBean.setDireccionSeleccionada(new Direccion());
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('wvBuiEditar').hide()");
        rc.execute("PF('wvOpEditarDireccion').hide()");
        editandoDireccion = false;
    }


    /**
     * Se ejecuta al hacer clic en el botón de crear entidad
     */
    public void btCrearEntidadListener() {
        try {
        	entidadN.setFlgActivo((short) 1);
            guardarEntidad(false);
            gestionarDisponibilidadPestanas();
            String header = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale());
            String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_CREACION_ENTIDAD,getLocale());
            msg = msg.replace("#codigo", entidadN.getCodigoSigep());
            if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)) 
            	this.entidadN.setCodTipoEntidad(10010);
            mostrarMensaje(FacesMessage.SEVERITY_INFO, header, msg);
            entidadProduces.getEntidadesUsuariorRefresca();
        } catch (SIGEP2SistemaException | ParseException ex) {
            Logger.getLogger(CreacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, CNT_ERROR, ex.getLocalizedMessage());
        }
    }

    /**
     * Se ejecuta al hacer clic en el botón de crear entidad
     */
    public void btGuardarCaracterizacionListener() {
        try {
        	entidadN.setFlgActivo((short) 1);
            guardarEntidad(true);
            gestionarDisponibilidadPestanas();
            String header = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale());
            String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_CARACTERIZACION_GUARDADA, getLocale());
            mostrarMensaje(FacesMessage.SEVERITY_INFO, header, msg);
        } catch (SIGEP2SistemaException | ParseException ex) {
            Logger.getLogger(CreacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, CNT_ERROR, ex.getLocalizedMessage());
        }
    }

    /**
     * Acción del botón regresar
     *
     * @return
     */
    public String btRegresarAction() {
    	if(lbEsCrearEntidad && entidadN!= null && entidadN.getCodigoSigep()!=null) {
    		BigDecimal codEntidad =  obtenerCodEntidadPorCodigoSigep(entidadN.getCodigoSigep());
    		if (codEntidad == null) {
    			SequenciasSigep seq = ComunicacionServiciosSis.setSequenciasSigepCodigoSIGEP(SecuenciasEnum.ENTIDAD_CODIGO_SIGEP.name());
    		}
    	}
        return "/index?faces-redirect=true";
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Lógica">
    /**
     * Mediante este método se valida si el nombre de entidad para el
     * departamento y municipio seleccionados existe
     *
     * @throws SIGEP2SistemaException Cuando la entidad existe
     */
    public void validarEntidadExistente() throws SIGEP2SistemaException {
        if (entidadN != null
                && entidadN.getCodDepartamento() != null
                && entidadN.getCodMunicipio() != null
                && entidadN.getNombreEntidad() != null) {
            Entidad ent = new Entidad();
            ent.setCodDepartamento(entidadN.getCodDepartamento());
            ent.setCodMunicipio(entidadN.getCodMunicipio());
            ent.setNombreEntidad(entidadN.getNombreEntidad());
            Entidad entidades = ComunicacionServiciosEnt.getEntidadDepMun(ent);
            if (entidades != null) {
            	if(entidades.getCodEntidad() != null) {
            		throw new SIGEP2SistemaException(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_EXISTE, getLocale()));
            	}
            }
        }
    }

    private boolean validarRolesCreacionEntidad() throws IOException {
        tabIndex = 0;
        try {
            rolAdmitido = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL,
                    RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS, RolDTO.ADMINISTRADOR_POLITICAS,
                    RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.CARACTERIZADOR_ENTIDAD);
            if (!rolAdmitido) {
    			return false;            	
            } else if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)) {
                setDisponibleDatosBasicos(true);
            }
        } catch (SIGEP2SistemaException e) {
            Logger.getLogger(CreacionEntidadBean.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return true;
    }
    
    public boolean validarRolesConsultaEntidad() throws IOException {
       try {
			if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR, RolDTO.AUDITOR,
																			    RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
																			    RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS, RolDTO.OPERADOR_ENTIDADES,
																			    RolDTO.JEFE_CONTRATOS,  RolDTO.JEFE_CONTROL_INTERNO,
																			    RolDTO.JEFE_TALENTO_HUMANO,  RolDTO.OPERADOR_CONTRATOS,
																			    RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.CARACTERIZADOR_ENTIDAD
																			    ))
				   return true;
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
       return false;
    }    
    

    /**
     * Mediante este método se guardan los datos de la entidad en la base de
     * datos, ya sea para creación o para actualización
     *
     * @param editando Flag que indica si está en edición o en creación
     * @throws co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException Cuando
     * el mensaje técnico contiene la parabra "Exception"
     * @throws java.text.ParseException
     */
    public void guardarEntidad(boolean editando) throws SIGEP2SistemaException, ParseException {
        // Estado Entidad
        if (!editando) {
            entidadN.setCodEstadoEntidad(new BigDecimal(1480));
            entidadN.setCodSubestadoEntidad(1483);
            entidadN.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
            entidadN.setFechaInicio(Calendar.getInstance().getTime());
            entidadN.setAudFechaActualizacion(Calendar.getInstance().getTime());
            
        } else {
            entidadN.setCodEntidad(obtenerCodEntidadPorCodigoSigep(entidadN.getCodigoSigep()));
            entidadN.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
            if(entidadN.getEntidadPostconfilcto() == null) {
            	entidadN.setEntidadPostconfilcto(new BigDecimal(0));
            }
            if(entidadN.getPersoneriaJuridica()==null)
            	entidadN.setPersoneriaJuridica((short) 0);
        }
        entidadN.setAudCodUsuario(BigDecimal.valueOf(getUsuarioSesion().getId()));
        entidadN.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
        try {
        	if (tipoNorma.getFechaNorma() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaString = sdf.format(tipoNorma.getFechaNorma());
                tipoNorma.setFechaNorma(sdf.parse(fechaString));
            }else {
            	entidadN.setCodTipoNorma(null);
            }
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
        
        if(normasT != null) {
        	if(!normasT.isEmpty()) {
        		if(tipoNorma.getNumeroNorma() != null) {
        			for (Norma norma : normasT) {
                		if(tipoNorma.getNumeroNorma().equals(norma.getNumeroNorma())) {
                    		tipoNorma = norma;
                    	}
            		}
        		}
        	}
        }
        int cont = 0;
        if(tipoNorma == null) {
        	tipoNorma = new Norma();
        }
        if(tipoNorma.getCodNorma() == null) {
        	if(tipoNorma.getCodTipoNorma() == null) {
        		if(codigotiponorma != null) {
        			if(codigotiponorma.intValue() > 0) {
        				tipoNorma.setCodTipoNorma(codigotiponorma);
        			}else {
            			cont++;
            		}
        		}else {
        			cont++;
        		}
        	}
        	if(tipoNorma.getFechaNorma() == null) {
        		if(fechaNorma != null) {
        			tipoNorma.setFechaNorma(fechaNorma);
        		}else {
        			cont++;
        		}
        	}
        	if(cont > 0) {
        		tipoNorma =  new Norma();
        	}
        	if(tipoNorma.getNumeroNorma() != null && tipoNorma.getCodTipoNorma()!=null && tipoNorma.getFechaNorma()!=null ) {
        		tipoNorma.setAudAccion(785);
            	tipoNorma.setAudCodRol((int) getUsuarioSesion().getCodRol());
            	tipoNorma.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
            	tipoNorma.setAudFechaActualizacion(new Date());
            	tipoNorma.setObjetoNorma(objetoNorma);
            	Norma tipoNormaGuardada = ComunicacionServiciosEnt.setNorma(tipoNorma);
            	tipoNorma.setCodNorma(tipoNormaGuardada.getCodNorma());
            	if(!tipoNormaGuardada.isError()) {
            		Norma normaFiltro = new Norma();
            		normaFiltro.setNumeroNorma(tipoNorma.getNumeroNorma());
            		normaFiltro.setCodTipoNorma(tipoNorma.getCodTipoNorma());
            		normaFiltro.setFechaNorma(tipoNorma.getFechaNorma());
            		if(!objetoNorma.equals(""))
            			normaFiltro.setObjetoNorma(objetoNorma);
            		List<Norma> listNorma = ComunicacionServiciosEnt.getFiltroNorma(normaFiltro);
            		if(listNorma != null && !listNorma.isEmpty()) {
            			entidadN.setCodNorma(new BigDecimal(listNorma.get(0).getCodNorma()));
            		}
    			}else {
    				String strMsgAdvUsuario ="Estimado Usuario.... Error Del Sistema";
    				mostrarMensaje(FacesMessage.SEVERITY_ERROR, strMsgAdvUsuario, tipoNorma.getMensaje());
    				return;
    			}
        	}else {
        		entidadN.setCodNorma(null);
        	}
        	
        }else {
        	this.tipoNorma.setAudAccion(63);
        	//Norma tipoNormaGuardada = ComunicacionServiciosEnt.setNorma(tipoNorma);
        	entidadN.setCodNorma(new BigDecimal(tipoNorma.getCodNorma()));
        }
        entidadN.setCodPais(new BigDecimal(169));
        if(tipoNorma.getNumeroNorma() !=null) {
        	if(tipoNorma.getNumeroNorma().isEmpty()) {
        		 entidadN.setCodNorma(null);
        	}
        }
        
        long usuarioSesion = this.getUsuarioSesion().getId();
        long rol = this.getRolAuditoria().getId();
        long tipoVin = TipoParametro.TIPO_VINCULACION_SERV_PUBLICO.getValue();
        
        if(entidadN.getCodMunicipio().intValue()== 0) {
        	entidadN.setCodMunicipio(codMunicipio);
        }
        
    	if(zonaUrbana){
    		entidadN.setTipoZona(TipoParametro.TIPO_ZONA_URBANA.getValue());
    	}else{
    		entidadN.setTipoZona(TipoParametro.TIPO_ZONA_RURAL.getValue());
    		entidadN.setDireccion(null);
    		entidadN.setDireccion(editarDireccion.getComplemento());
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			String jsonDireccion = gson.toJson(editarDireccion);
			entidadN.setDireccion(jsonDireccion);
    	}
    		
        
        
        Entidad ent = ComunicacionServiciosEnt.setEntidadExt(entidadN,tipoVin,usuarioSesion,rol);
        entidadN.setCodEntidad(obtenerCodEntidadPorCodigoSigep(entidadN.getCodigoSigep()));
        List<Parametrica> listP = ComunicacionServiciosHV.getParametrica(TipoParametro.AUTONOMIA.getValue());
        Autonomia svAutonomia =  new Autonomia();
		svAutonomia.setCodEntidad(entidadN.getCodEntidad());
		
		svAutonomia.setAudFechaActualizacion(new Date());
		svAutonomia.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		svAutonomia.setAudCodRol(entidadN.getAudCodRol().intValue());
		svAutonomia.setAudCodUsuario(entidadN.getAudCodUsuario());
        if(!listP.isEmpty()) {
        		for (Parametrica parametrica : listP) {
        			if(!ltsAutonomia.isEmpty()) {
        				for (String  auto : ltsAutonomia) {
                			svAutonomia.setCodTipoAutonomia(parametrica.getCodTablaParametrica());
                			if(parametrica.getCodTablaParametrica().intValueExact() ==
                					Integer.parseInt(auto)) {
                				svAutonomia.setFlgActivo((short) 1);
                				ltsAutonomia.remove(auto);
                				break;
                			}else {
                				svAutonomia.setFlgActivo((short) 0);
                			}
                		}
        			}else {
        				svAutonomia.setCodTipoAutonomia(parametrica.getCodTablaParametrica());
        				svAutonomia.setFlgActivo((short) 0);
        			}
            		ComunicacionServiciosEnt.setAutonomia(svAutonomia);
    			}
        }
        /**
         * Manda correo electronico a los roles descritos.
         */
        
        PersonaExt personaRoles = new PersonaExt();
        Integer[] roles = new Integer[2];
         roles[0] = 12;
         roles[1] = 19;
         
         personaRoles.setCodEntidad(BigDecimal.valueOf(this.getEntidadUsuario().getId()));
         personaRoles.setCodRolList(roles);
        
    	List<PersonaExt> listPersonas = ComunicacionServiciosEnt.getPersonasPorRoles(personaRoles);
    	if(!listPersonas.isEmpty()) {
    		String asunto = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_ASUNTO_CREACION_ENTIDAD, getLocale());
			String mensaje = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_MENSAJE_CREACION_ENTIDAD, getLocale())
								.replace("%nombreEntidad%", entidadN.getNombreEntidad());
    		for (PersonaExt personaExt : listPersonas) {
    			personaExt.getCorreoElectronico();
    			ConnectionHttp.sendEmail(personaExt.getCorreoElectronico(), mensaje, asunto);
			}
    	}
 
        String mensajeTecnico = ent.getMensajeTecnico();
        if (mensajeTecnico != null) {
            throw new SIGEP2SistemaException(ent.getMensaje() + " - " + mensajeTecnico);
        }
        this.eliminarRegistrosRegimen();
        List<EntidadSistemaRegimen> esrListSistema = crearRegistrosSeleccioMultiple(codigosSistemaCarreraSeleccionados, CODIGO_SISTEMA);
        validarMensajesTecnicosSeleccionMultiple(esrListSistema);
        List<EntidadSistemaRegimen> esrListEscala = crearRegistrosSeleccioMultiple(codigosEscalaSalarialSeleccionados, CODIGO_ESCALA);
        validarMensajesTecnicosSeleccionMultiple(esrListEscala);
        List<EntidadSistemaRegimen> esrListNaturaleza = crearRegistrosSeleccioMultiple(codigosNaturalezaEmpleoSeleccionados, CODIGO_NATURALEZA);
        validarMensajesTecnicosSeleccionMultiple(esrListNaturaleza);
    }
    
  

    /**
     * 
     * 
     */
    public void inactivarUsuarioRolEntidad() {
    	
    }
    

    /**
     * Obtiene el <code>codEntidad</code> de una entidad dado su código sigep
     *
     * @param codigoSigep
     * @return
     */
    public BigDecimal obtenerCodEntidadPorCodigoSigep(String codigoSigep) {
        Entidad ent = new Entidad();
        ent.setCodigoSigep(codigoSigep);
        ent.setLimitInit(0);
        ent.setLimitEnd(1000);
        List<Entidad> ents = ComunicacionServiciosEnt.getEntidadesFiltro(ent);
        if (ents != null && !ents.isEmpty()) {
            return ents.get(0).getCodEntidad();
        }
        return null;
    }

    /**
     * Inserta registros en la tabla <code>ENTIDAD_SISTEMA_REGIMEN</code> según
     * el tipo de registro
     *
     * @param registros Los registros a insertar
     * @param codigo El tipo de registro, el cual se encuentra como constante
     * @return
     * @throws SIGEP2SistemaException
     */
    public List<EntidadSistemaRegimen> crearRegistrosSeleccioMultiple(List<String> registros, int codigo) throws SIGEP2SistemaException {
        List<EntidadSistemaRegimen> esrList = new ArrayList<>();
        BigDecimal audCodUsuario = BigDecimal.valueOf(getUsuarioSesion().getId());
        Integer audAccion = TipoAccionEnum.INSERT.getIdAccion();
        Short activo = 1;
        if (registros != null) {
            for (int i = 0; i < registros.size(); i++) {
                BigDecimal reg = BigDecimal.valueOf(Long.parseLong(registros.get(i)));
                EntidadSistemaRegimen esr = new EntidadSistemaRegimen();
                esr.setCodEntidad(obtenerCodEntidadPorCodigoSigep(entidadN.getCodigoSigep()));
                esr.setFlgActivo((short) 1);
                switch (codigo) {
                    case CODIGO_ESCALA:
                        esr.setCodEscalaSalarial(reg);
                        break;
                    case CODIGO_SISTEMA:
                        esr.setCodSistemaCarrera(reg);
                        break;
                    case CODIGO_NATURALEZA:
                        esr.setCodNaturalezaEmpleo(reg);
                        break;
                }
                List<EntidadSistemaRegimen> listReg = ComunicacionServiciosEnt.getEntidadesSistemaRegimenPorId(esr);
                if(listReg != null && !listReg.isEmpty()) 
                	esr = listReg.get(0);
                esr.setAudAccion(audAccion);
                if(esr.getCodEntidadSistemaRegimen() != null)
                	esr.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
                esr.setAudCodRol((int) this.getRolAuditoria().getId());
                esr.setAudCodUsuario(audCodUsuario);
                esr.setFlgActivo(activo);
                EntidadSistemaRegimen esrNuevo = ComunicacionServiciosEnt.setEntidadSistemaRegimen(esr);
                esrList.add(esrNuevo);
            }
        }
        return esrList;
    }

    /**
     * Valida en todos los registros <code>EntidadSistemaRegimen</code> de un
     * listado si tienen mensaje de error
     *
     * @param esrList El listado de elementos a validar
     * @throws SIGEP2SistemaException Cuando algún elemento contiene mensaje de
     * error
     */
    public void validarMensajesTecnicosSeleccionMultiple(List<EntidadSistemaRegimen> esrList) throws SIGEP2SistemaException {
        String mensajeTecnico;
        for (EntidadSistemaRegimen esr : esrList) {
            mensajeTecnico = esr.getMensajeTecnico();
            if (mensajeTecnico != null) {
                throw new SIGEP2SistemaException(esr.getMensaje() + " - " + mensajeTecnico);
            }
        }
    }

    /**
     * Gestiona la habilitación y deshabilitación de pestañas según el caso
     *
     * @throws SIGEP2SistemaException
     */
    public void gestionarDisponibilidadPestanas() throws SIGEP2SistemaException {
    	 tabIndex = 2;
        if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_POLITICAS)) {
            setDisponibleAmbitoAplicacion(true);
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			AmbitoAplicacionPoliticasBean mBAmbito = (AmbitoAplicacionPoliticasBean) elContext.getELResolver().getValue(elContext,null, "ambitoAplicacionPoliticasBean");
			List<Entidad> lstEntidaddes = new ArrayList<>();
			lstEntidaddes.add(entidadN);
			mBAmbito.setEntidadesDisponibles(lstEntidaddes);            
        }
        if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.CARACTERIZADOR_ENTIDAD) 
				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL) 
				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR)
				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES)
				||usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)){
        	 setDisponibleCaracterizacion(false);
        	 tabCaracterizacion = false;
        	 tabIndex = 1;
        }else {
        	 tabIndex = 2;
        	 setDisponibleCaracterizacion(true);
        	 tabCaracterizacion = true;
        }
        setDisponibleDatosBasicos(false);
    }

    /**
     * Obtiene el texto de un registro de la tabla paramétrica
     *
     * @param codigo el código del valor a obtener
     * @return
     */
    public String getTextoParametrica(BigDecimal codigo) {
        if (codigo != null) {
            Parametrica param = ComunicacionServiciosSis.getParametricaporId(codigo);
            if (param != null && param.getCodTablaParametrica() != null) {
                return param.getValorParametro();
            }
        }
        return null;
    }

    /**
     * Método mediante el cual se geolocaliza la dirección
     */
    public void geolocalizarDireccion() {
        StringBuilder sbScript = new StringBuilder();
        if (entidadN.getCodDepartamento() != null || entidadN.getCodMunicipio() != null
                || (entidadN.getDireccion() != null && !entidadN.getDireccion().isEmpty())) {
            sbScript.append("PF('wvMapaEntidad').geocode('");
            if (entidadN.getDireccion() != null && !entidadN.getDireccion().isEmpty()) {
                sbScript.append(entidadN.getDireccion()).append(", ");
            }
            if (entidadN.getCodMunicipio() != null) {
                Municipio mun = ComunicacionServiciosSis.getMunicipiosid(entidadN.getCodMunicipio().longValue());
                if (mun != null) {
                    sbScript.append(mun.getNombreMunicipio()).append(", ");
                }
            }
            if (entidadN.getCodDepartamento() != null) {
                Departamento depto = ComunicacionServiciosSis.getdeptoporid(entidadN.getCodDepartamento().longValue());
                if (depto != null) {
                    sbScript.append(depto.getNombreDepartamento());
                }
            }
            sbScript.append("')");
        }
        if (sbScript.length() > 0) {
            RequestContext rc = RequestContext.getCurrentInstance();
            rc.execute(sbScript.toString());
        }
    }

    /**
     * Este método se ejecuta al realizar la geolocalización; el llamado lo hace
     * el <code>ajax</code> del componente <code>GMap</code>
     *
     * @param evt
     */
    public void geolocalizacionListener(GeocodeEvent evt) {
        modeloMapa = new DefaultMapModel();
        List<GeocodeResult> results = evt.getResults();
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centroMapa = center.getLat() + "," + center.getLng();
            entidadN.setLatitud( BigDecimal.valueOf(center.getLat()));
            entidadN.setLongitud(BigDecimal.valueOf(center.getLng()));
            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                getModeloMapa().addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
          
        } else {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, CNT_ERROR, MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_EXISTE, getLocale()));
        }
    }
    
    private void addMarkerGeoLocalizacion(){
    	String direccion = mostrarDireccionGenerada();
		Marker marker = new Marker(new LatLng(entidadN.getLatitud().doubleValue(), entidadN.getLongitud().doubleValue()), direccion);
		getModeloMapa().addOverlay(marker);	 
    }

    /**
     * Metodo que verifica el tipo de entidad y dependiendo de este habilita o no
     * el campo clasificacion organica
     */
    public void verificarClasificacionOrganica() {
    	if (entidadN.getCodClasificacionOrganica() != null && entidadN.getCodClasificacionOrganica().intValue() == TipoParametro.CLASIFICACION_PRIV_FUNC_PUBLICA.getValue()) {
    		setBlnEntidadPrivFuncPublic(true);
    		
    		entidadN.setCodTipoEntidad(TipoParametro.ENTIDAD_PRIV_FUNC_PUBLICAS.getValue());
    		
    		
    		Parametrica clasificacionOrganica =  ComunicacionServiciosVin.getParametricaporId(new BigDecimal(TipoParametro.CLASIFICACION_PRIV_FUNC_PUBLICA.getValue()));
    		if(clasificacionOrganica!= null) {
    			strClasificacionOrganicaPriv = clasificacionOrganica.getNombreParametro() ;
    		}
    	}else {
			if (entidadN.getCodClasificacionOrganica() != null && entidadN.getCodClasificacionOrganica().intValue() != TipoParametro.RAMA_EJECUTIVA.getValue()) {
				entidadN.setCodAdscritaVinculada(new BigDecimal(2240));
				adscrito = false;
			}

			setBlnEntidadPrivFuncPublic(false);
    	}  
    	
    }
    
    public void checkEjecutivaAndNacional()
    {
    	if(entidadN.getCodClasificacionOrganica() == null || entidadN.getCodOrden() == null) return;
    	//if(entidadN.getCodDepartamento().intValue()==11 && entidadN.getCodMunicipio().intValue()==1618) return;
    	
    	if(entidadN.getCodClasificacionOrganica().intValue() == TipoParametro.RAMA_EJECUTIVA.getValue() && entidadN.getCodOrden().intValue() == TipoParametro.ORDEN_NACIONAL.getValue() ) 
    	{
    		String msg = "La Clasificación Orgánica es Rama Ejecutiva del Orden Nacional, por lo tanto se Cambiará el Departamento y la Ciudad a Bogotá";
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale()), msg);
			
    		// se setea bogota
    		entidadN.setCodDepartamento(new BigDecimal(11));
    		entidadN.setCodMunicipio(new BigDecimal(1618));  
    	}
    }
    
    //</editor-fold>
    @Override
    public void validateForm(ComponentSystemEvent event) {
    }

    @Override
    public String persist() {
        return null;
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

    //<editor-fold defaultstate="collapsed" desc="Accesores y mutadores">
    public Entidad getEntidadN() {
        return entidadN;
    }

    public void setEntidadN(Entidad entidadN) {
        this.entidadN = entidadN;
    }

    public BigDecimal getCodigoDNP() {
        return codigoDNP;
    }

    public void setCodigoDNP(BigDecimal codigoDNP) {
        this.codigoDNP = codigoDNP;
    }

    public List<String> getCodigosEscalaSalarialSeleccionados() {
        return codigosEscalaSalarialSeleccionados;
    }

    public void setCodigosEscalaSalarialSeleccionados(List<String> codigosEscalaSalarialSeleccionados) {
        this.codigosEscalaSalarialSeleccionados = codigosEscalaSalarialSeleccionados;
    }

    public List<String> getCodigosSistemaCarreraSeleccionados() {
        return codigosSistemaCarreraSeleccionados;
    }

    public void setCodigosSistemaCarreraSeleccionados(List<String> codigosSistemaCarreraSeleccionados) {
        this.codigosSistemaCarreraSeleccionados = codigosSistemaCarreraSeleccionados;
    }

    public List<String> getCodigosNaturalezaEmpleoSeleccionados() {
        return codigosNaturalezaEmpleoSeleccionados;
    }

    public void setCodigosNaturalezaEmpleoSeleccionados(List<String> codigosNaturalezaEmpleoSeleccionados) {
        this.codigosNaturalezaEmpleoSeleccionados = codigosNaturalezaEmpleoSeleccionados;
    }

    public String getIndicativoPais() {
        return indicativoPais;
    }

    public void setIndicativoPais(String indicativoPais) {
        this.indicativoPais = indicativoPais;
    }

    public Entidad getEntidadAdscritaSeleccionada() {
        return entidadAdscritaSeleccionada;
    }

    public void setEntidadAdscritaSeleccionada(Entidad entidadAdscritaSeleccionada) {
        this.entidadAdscritaSeleccionada = entidadAdscritaSeleccionada;
    }


    public String getCentroMapa() {
        return centroMapa;
    }

    public void setCentroMapa(String centroMapa) {
        this.centroMapa = centroMapa;
    }

    public DefaultMapModel getModeloMapa() {
        return modeloMapa;
    }

    public void setModeloMapa(DefaultMapModel modeloMapa) {
        this.modeloMapa = modeloMapa;
    }

    public boolean isEditandoDireccion() {
        return editandoDireccion;
    }

    public void setEditandoDireccion(boolean editandoDireccion) {
        this.editandoDireccion = editandoDireccion;
    }

    public boolean isZonaUrbana() {
        return zonaUrbana;
    }

    public void setZonaUrbana(boolean zonaUrbana) {
        this.zonaUrbana = zonaUrbana;
    }

    public boolean isAdscrito() {
        return adscrito;
    }

    public void setAdscrito(boolean adscrito) {
        this.adscrito = adscrito;
    }

    public static int getNORMA_SI() {
        return NORMA_SI;
    }

    public static int getNORMA_NO() {
        return NORMA_NO;
    }

    /**
     * @return the adminfun
     */
    public boolean isAdminfun() {
        return adminfun;
    }

    /**
     * @param adminfun the adminfun to set
     */
    public void setAdminfun(boolean adminfun) {
        this.adminfun = adminfun;
    }

    /**
     * @return the norma
     */
    public int getNorma() {
        return norma;
    }

    /**
     * @param norma the norma to set
     */
    public void setNorma(int norma) {
        this.norma = norma;
    }

    /**
     * @return the estadoEntidad
     */
    public boolean isEstadoEntidad() {
        return estadoEntidad;
    }

    /**
     * @param estadoEntidad the estadoEntidad to set
     */
    public void setEstadoEntidad(boolean estadoEntidad) {
        this.estadoEntidad = estadoEntidad;
    }

    public boolean isDisponibleDatosBasicos() {
        return disponibleDatosBasicos;
    }

    public void setDisponibleDatosBasicos(boolean disponibleDatosBasicos) {
        this.disponibleDatosBasicos = disponibleDatosBasicos;
    }

    public boolean isDisponibleCaracterizacion() {
        return disponibleCaracterizacion;
    }

    public void setDisponibleCaracterizacion(boolean disponibleCaracterizacion) {
        this.disponibleCaracterizacion = disponibleCaracterizacion;
    }

    public boolean isDisponibleAmbitoAplicacion() {
        return disponibleAmbitoAplicacion;
    }

    public void setDisponibleAmbitoAplicacion(boolean disponibleAmbitoAplicacion) {
        this.disponibleAmbitoAplicacion = disponibleAmbitoAplicacion;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public boolean isEstadoSubOrden() {
        return estadoSubOrden;
    }

    public void setEstadoSubOrden(boolean estadoSubOrden) {
        this.estadoSubOrden = estadoSubOrden;
    }

	public boolean isLbEsCrearEntidad() {
		return lbEsCrearEntidad;
	}

	public void setLbEsCrearEntidad(boolean lbEsCrearEntidad) {
		this.lbEsCrearEntidad = lbEsCrearEntidad;
	}

	/**
	 * @return the blnEntidadPrivFuncPublic
	 */
	public boolean isBlnEntidadPrivFuncPublic() {
		return blnEntidadPrivFuncPublic;
	}

	/**
	 * @param blnEntidadPrivFuncPublic the blnEntidadPrivFuncPublic to set
	 */
	public void setBlnEntidadPrivFuncPublic(boolean blnEntidadPrivFuncPublic) {
		this.blnEntidadPrivFuncPublic = blnEntidadPrivFuncPublic;
	}

	/**
	 * @return the strClasificacionOrganicaPriv
	 */
	public String getStrClasificacionOrganicaPriv() {
		return strClasificacionOrganicaPriv;
	}

	/**
	 * @param strClasificacionOrganicaPriv the strClasificacionOrganicaPriv to set
	 */
	public void setStrClasificacionOrganicaPriv(String strClasificacionOrganicaPriv) {
		this.strClasificacionOrganicaPriv = strClasificacionOrganicaPriv;
	}

	public boolean isLbEsConsultaEntidad() {
		return lbEsConsultaEntidad;
	}

	public void setLbEsConsultaEntidad(boolean lbEsConsultaEntidad) {
		this.lbEsConsultaEntidad = lbEsConsultaEntidad;
	}

	/**
	 * @return the tipoNorma
	 */
	public Norma getTipoNorma() {
		return tipoNorma;
	}

	/**
	 * @param tipoNorma the tipoNorma to set
	 */
	public void setTipoNorma(Norma tipoNorma) {
		this.tipoNorma = tipoNorma;
	}
	
	public List<String> getNumeroNorma(Integer codTipoNorma, Date fechaNorma) {
		List<String> lista = new ArrayList<String>();

		Norma ax = new Norma();
		ax.setCodTipoNorma(codTipoNorma);
		ax.setFechaNorma(fechaNorma);
		List<Norma> listN = ComunicacionServiciosEnt.getFiltroNorma(ax);

		try {
			if (!listN.isEmpty()) {
				for (Norma aux : listN) {
					if (aux.getNumeroNorma()!=null) {
						lista.add(aux.getNumeroNorma());
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
			return new ArrayList<>();
		}

		return lista;
	}
	
	public List<Norma> acNombreNormaListener(String  estructuraExt) {
		if(tipoNorma == null) {
			tipoNorma = new Norma();
			 return new ArrayList<>();
		}
		 Norma norm = new Norma();
		 norm.setCodTipoNorma(tipoNorma.getCodTipoNorma());
		 norm.setFechaNorma(tipoNorma.getFechaNorma());
		 norm.setNumeroNorma(estructuraExt);
		 normasT =  ComunicacionServiciosEnt.getFiltroNormaLike(norm);
	     if(normasT.isEmpty()) {
	    	 codigotiponorma = tipoNorma.getCodTipoNorma();
	    	 fechaNorma = tipoNorma.getFechaNorma();
	    	 return new ArrayList<>();
	     }
	     return normasT;
	 }
	
	public void acNombreNormaSelectListener(ValueChangeEvent  estructuraExt) {
		try {
			if(estructuraExt.getNewValue() == null) {
				if(tipoNorma == null) 
					tipoNorma = new Norma();
				return;
			}
			Norma estr = (Norma) estructuraExt.getNewValue();
			tipoNorma = estr;
	        if (estr.getNumeroNorma() != null) {
	        	if(normasT == null || normasT.isEmpty())
	        		acNombreNormaListener(estr.getNumeroNorma());
	        	for (Norma norma : normasT) {
	        		if(estr.getNumeroNorma().equals(norma.getNumeroNorma())) {
		        		tipoNorma = norma;
		        		objetoNorma = norma.getObjetoNorma();
		        		break;
		        	}
				}
	        }
		} catch (Exception e) {
			if(tipoNorma == null) {
				tipoNorma = new Norma();
			}
		}
		
	 }
	
	

	/**
	 * @return the msg1
	 */
	public String getMsg1() {
		
		Locale local = getLocale();
		if(local.getLanguage().equals("en")) {
			msg1 = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(msg1Eng)).getValorParametro();
		}else {
			msg1 = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(msg1Esp)).getValorParametro();
		}
		
		return msg1;
	}

	/**
	 * @param msg1 the msg1 to set
	 */
	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}

	/**
	 * @return the msg2
	 */
	public String getMsg2() {
		Locale local = getLocale();
		if(local.getLanguage().equals("en")) {
			msg2 = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(msg2Eng)).getValorParametro();
		}else {
			msg2 = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(msg2Esp)).getValorParametro();
		}
		return msg2;
	}

	/**
	 * @param msg2 the msg2 to set
	 */
	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}

	/**
	 * @return the btGuardar
	 */
	public boolean isBtGuardar() {
		return btGuardar;
	}

	/**
	 * @param btGuardar the btGuardar to set
	 */
	public void setBtGuardar(boolean btGuardar) {
		this.btGuardar = btGuardar;
	}
	
	/**
	 * 
	 */
	public void acListenerNorma() {
		Norma ax = new Norma();
		 ax.setCodTipoNorma(tipoNorma.getCodTipoNorma());
		 ax.setFechaNorma(tipoNorma.getFechaNorma());
		 ax.setNumeroNorma(tipoNorma.getNumeroNorma());
		 List<Norma> listN = ComunicacionServiciosEnt.getFiltroNorma(ax);
		 if(listN != null) {
			 if(!listN.isEmpty()) {
				 tipoNorma.setCodNorma(listN.get(0).getCodNorma());
			 }else {
				 tipoNorma.setCodNorma(null);
			 }
		 }else {
			 tipoNorma.setCodNorma(null);
		 }
	}

	public boolean isMostrarOcultarEntidadPostconflicto() {
		return mostrarOcultarEntidadPostconflicto;
	}

	public void setMostrarOcultarEntidadPostconflicto(boolean mostrarOcultarEntidadPostconflicto) {
		this.mostrarOcultarEntidadPostconflicto = mostrarOcultarEntidadPostconflicto;
	}
	
	public void iraTab(int index) {
		tabIndex = index;
		 RequestContext rc = RequestContext.getCurrentInstance();
	        rc.update("tabViewCrearEntidad");
	}

	/**
	 * @return the noAplicaSistemaCarrera
	 */
	public boolean isNoAplicaSistemaCarrera() {
		return noAplicaSistemaCarrera;
	}

	/**
	 * @param noAplicaSistemaCarrera the noAplicaSistemaCarrera to set
	 */
	public void setNoAplicaSistemaCarrera(boolean noAplicaSistemaCarrera) {
		this.noAplicaSistemaCarrera = noAplicaSistemaCarrera;
	}
	
	public void deshabilitarSistemaSCarrera() {
		if(noAplicaSistemaCarrera) {
			codigosSistemaCarreraSeleccionados = new ArrayList<>();
			codigosSistemaCarreraSeleccionados.add("1459");
			cehckSistemaSCarrera = true;
		}else {
			codigosSistemaCarreraSeleccionados = new ArrayList<>();
			cehckSistemaSCarrera = false;
		}
		
	}

	/**
	 * @return the cehckSistemaSCarrera
	 */
	public boolean isCehckSistemaSCarrera() {
		return cehckSistemaSCarrera;
	}

	/**
	 * @param cehckSistemaSCarrera the cehckSistemaSCarrera to set
	 */
	public void setCehckSistemaSCarrera(boolean cehckSistemaSCarrera) {
		this.cehckSistemaSCarrera = cehckSistemaSCarrera;
	}

	public boolean isLbPermiteModificarNombreEntidad() {
		return lbPermiteModificarNombreEntidad;
	}

	public void setLbPermiteModificarNombreEntidad(boolean lbPermiteModificarNombreEntidad) {
		this.lbPermiteModificarNombreEntidad = lbPermiteModificarNombreEntidad;
	}

	/**
	 * @return the objetoNorma
	 */
	public String getObjetoNorma() {
		return objetoNorma;
	}

	/**
	 * @param objetoNorma the objetoNorma to set
	 */
	public void setObjetoNorma(String objetoNorma) {
		this.objetoNorma = objetoNorma;
	}

	/**
	 * @return the codOrden
	 */
	public Integer getCodOrden() {
		return codOrden;
	}

	/**
	 * @param codOrden the codOrden to set
	 */
	public void setCodOrden(Integer codOrden) {
		this.codOrden = codOrden;
	}

	/**
	 * @return the codSectorAdministrativo
	 */
	public Integer getCodSectorAdministrativo() {
		return codSectorAdministrativo;
	}

	/**
	 * @param codSectorAdministrativo the codSectorAdministrativo to set
	 */
	public void setCodSectorAdministrativo(Integer codSectorAdministrativo) {
		this.codSectorAdministrativo = codSectorAdministrativo;
	}
	
	

	/**
	 */
	public void direccionGeneradaFisica() {
		editarDireccion.setDireccionGenerada(null);
		editarDireccion.setDireccionGenerada(editarDireccion.getComplemento());
	}
	
	public void agergarDireccion() {
		
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String jsonDireccion = gson.toJson(editarDireccion);
		entidadN.setDireccion(jsonDireccion);
		this.estadoPanelDireccion = false;
		RequestContext rc = RequestContext.getCurrentInstance();
        rc.execute("PF('dlg2').hide()");
	}
	
	/**
	 * Método para convertir la dirección
	 */
	public String mostrarDireccionGenerada() {
		String direccion = "";
		try {	
			if (entidadN.getDireccion() != null && !entidadN.getDireccion().isEmpty()) {
				Direccion dir = new Direccion();
				dir.llenarDatosDesdeJson(entidadN.getDireccion());
				direccionBean.setDireccionSeleccionada(dir);
	            direccionBean.mostrarDireccion();
	            direccion = direccionBean.getDireccionGenerada();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
        return direccion;
	}
	
	public void tipoEntidadListener() {
		// ENTIDAD PUBLICA, ES REQUERIDO ADSCRITAS/VINCULADAS
		if (entidadN.getCodTipoEntidad() != null
				&& entidadN.getCodTipoEntidad().intValue() == TipoParametro.ENTIDAD_PUBLICA.getValue()) {
			requerido = true;
		} else {
			requerido = false;
		}
	}

	
	public void mostrarPanelEditarDireccion(boolean value) {
		this.estadoPanelDireccion = value;
	}


	/**
	 * @return the estadoPanelDireccion
	 */
	public boolean isEstadoPanelDireccion() {
		return estadoPanelDireccion;
	}

	/**
	 * @param estadoPanelDireccion the estadoPanelDireccion to set
	 */
	public void setEstadoPanelDireccion(boolean estadoPanelDireccion) {
		RequestContext rc = RequestContext.getCurrentInstance();
		cargarDireccionPanelEditarDireccion();
        rc.execute("PF('dlg2').show()");
		this.estadoPanelDireccion = estadoPanelDireccion;
	}
	/**
	 * Metodo que carga la direccion en el panel, cuando esta ya existe
	 */
	public void cargarDireccionPanelEditarDireccion() {
		if (this.entidadN.getDireccion() != null && !this.entidadN.getDireccion().isEmpty()) {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(this.entidadN.getDireccion(), EditarDireccionDTO.class);
				if (direccion != null) {
					this.editarDireccion = direccion;
				}
			} catch (JsonSyntaxException e) {

			}
		}
	}

	/**
	 * @return the autonomia
	 */
	public List<String> getAutonomia() {
		return autonomia;
	}

	/**
	 * @param autonomia the autonomia to set
	 */
	public void setAutonomia(List<String> autonomia) {
		this.autonomia = autonomia;
	}
	
	public void autonomiaListener(String data) {
		if(ltsAutonomia.isEmpty()) {
			System.out.println("1");
		}else {
			System.out.println("2");
		}
	}

	/**
	 * @return the ltsAutonomia
	 */
	public List<String> getLtsAutonomia() {
		return ltsAutonomia;
	}

	/**
	 * @param ltsAutonomia the ltsAutonomia to set
	 */
	public void setLtsAutonomia(List<String> ltsAutonomia) {
		this.ltsAutonomia = ltsAutonomia;
	}
	
	public void restablecerTipoNorma() {
		Integer codNorma = this.tipoNorma.getCodTipoNorma();
		this.tipoNorma = new Norma();
		this.tipoNorma.setCodTipoNorma(codNorma);
	}
	
	public void restablecerNorma() {
		Date fecha = this.tipoNorma.getFechaNorma();
		Integer codNorma = this.tipoNorma.getCodTipoNorma();
		this.tipoNorma = new Norma();
		this.tipoNorma.setFechaNorma(fecha);
		this.tipoNorma.setCodTipoNorma(codNorma);
	}
	
	@SuppressWarnings("unchecked")
	public void actualizarSistemaRegimenEliminado(ValueChangeEvent event) {
		if(event.getNewValue() != null)
		if(event.getOldValue() != null)
		try{
			if(event.getOldValue() == null)
				return;
			if(((ArrayList<String>) event.getNewValue()).size() < ((ArrayList<String>) event.getOldValue()).size())
				this.buscarElementosEliminados(event);
			else {
				String arg = this.eliminarElementoEliminado(event);
				if(arg != null)
					this.codsParaEliminar.remove(arg);
			}
		}
		catch(Exception ex) {
			CreacionEntidadBean.logger.error("Error: public void actualizarSistemaRegimenEliminado(ValueChangeEvent event) CreacionEntidadBean.java " + ex.getMessage(), new Object());
		}
	}

	
	/**
	 * 
	 * Método que compara las listas de selección de naturaleza de empleo, regimen escala salarial y 
	 * sistema de carrera y determina cuales elementos fueron eliminados.
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	public void buscarElementosEliminados(ValueChangeEvent event) {
		List<String> arrayV = (ArrayList<String>) event.getOldValue();
		List<String> arrayN = (ArrayList<String>) event.getNewValue();
		short indNue = 0;
		for(short itev = 0; itev < arrayV.size(); itev++){
			for(short iten = 0; iten < arrayN.size(); iten++) {
				if(arrayV.get(itev).equalsIgnoreCase(arrayN.get(iten)))
					break;
				else
					indNue++;	
			}
			if(indNue == arrayN.size())
				this.codsParaEliminar.add(new String(arrayV.get(itev)));
			indNue = 0;
		}

	}
	
	@SuppressWarnings("unchecked")
	public String eliminarElementoEliminado(ValueChangeEvent event) {
		if(((ArrayList<String>) event.getNewValue()).size() == 1 || this.codsParaEliminar.isEmpty())
			return null;
		List<String> arrayN = (ArrayList<String>) event.getNewValue();
		for(short s = 0; s < arrayN.size(); s++){
			for(short iter = 0; iter < arrayN.size(); iter++) {
				if(arrayN.get(iter).equalsIgnoreCase(this.codsParaEliminar.get(iter)))
					return this.codsParaEliminar.get(iter);
			}
		}
		return null;
	}
	
	public void eliminarRegistrosRegimen() {
		try {
			for(String str : this.codsParaEliminar) {
				EntidadSistemaRegimen esr = new EntidadSistemaRegimen();
				BigDecimal reg = BigDecimal.valueOf(Long.parseLong(str));
				esr.setCodEntidad(obtenerCodEntidadPorCodigoSigep(entidadN.getCodigoSigep()));
				esr.setFlgActivo((short) 1);
				esr.setCodNaturalezaEmpleo(reg);
				if(!this.eliminacionRegistroRegimen(esr)) {
					esr.setCodNaturalezaEmpleo(null);
					esr.setCodEscalaSalarial(reg);
					if(!this.eliminacionRegistroRegimen(esr)) {
						esr.setCodEscalaSalarial(null);
						esr.setCodSistemaCarrera(reg);
						this.eliminacionRegistroRegimen(esr);
					}	
				}
			}
		}
		catch(Exception ex) {
			
		}
	}
	
	public boolean eliminacionRegistroRegimen(EntidadSistemaRegimen esr) {	
		try{
			List<EntidadSistemaRegimen> listRe = ComunicacionServiciosEnt.getEntidadesSistemaRegimenPorId(esr);
			if(listRe!= null && !listRe.isEmpty()) {
				for(short s = 0; s < listRe.size(); s++) {
					esr = listRe.get(s);
					esr = this.configurarRegsEntSisReg(esr);	
					EntidadSistemaRegimen esrNuevo = ComunicacionServiciosEnt.setEntidadSistemaRegimen(esr);
					if(esrNuevo.isError())
						CreacionEntidadBean.logger.error("Error public void eliminarRegistrosRegimen() CreacionEntidadBean " + esrNuevo.getMensajeTecnico());
				}
				return true;
			}
			else
				return false;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
	public EntidadSistemaRegimen configurarRegsEntSisReg(EntidadSistemaRegimen esr) {
		//Integer audAccion = TipoAccionEnum.INSERT.getIdAccion();
		esr.setAudAccion(63);
		esr.setAudCodRol((int) this.getRolAuditoria().getId());
        esr.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
        esr.setFlgActivo((short) 0);
        return esr;
	}
	
	public List<EntidadSistemaRegimen> removerInactivos(List<EntidadSistemaRegimen> esrList) {
		List<EntidadSistemaRegimen> list = new ArrayList<>();
		if(esrList == null)
			return new ArrayList<>();
		for(EntidadSistemaRegimen e : esrList){
			if(e != null && e.getFlgActivo() != null && e.getFlgActivo() == 1)
				list.add(e);
		}
		return list;
	}
	
	public boolean isMostrarJustificacion() {
		return mostrarJustificacion;
	}

	public void setMostrarJustificacion(boolean mostrarJustificacion) {
		this.mostrarJustificacion = mostrarJustificacion;
	}
	
	/**
	 * @return el requerido
	 */
	public boolean isRequerido() {
		return requerido;
	}

	/**
	 * @param requerido
	 *            el requerido a establecer
	 */
	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}

	/**
	 * @return the estadoEntidadRol
	 */
	public boolean isEstadoEntidadRol() {
		return estadoEntidadRol;
	}

	/**
	 * @param estadoEntidadRol the estadoEntidadRol to set
	 */
	public void setEstadoEntidadRol(boolean estadoEntidadRol) {
		this.estadoEntidadRol = estadoEntidadRol;
	}

	/**
	 * @return the codigoJ
	 */
	public int getCodigoJ() {
		return codigoJ;
	}

	/**
	 * @param codigoJ the codigoJ to set
	 */
	public void setCodigoJ(int codigoJ) {
		this.codigoJ = codigoJ;
	}

	public List<String> getCodsParaEliminar() {
		return codsParaEliminar;
	}

	public void setCodsParaEliminar(List<String> codsParaEliminar) {
		this.codsParaEliminar = codsParaEliminar;
	}

	/**
	 * @return the caracterizador
	 */
	public boolean isCaracterizador() {
		return caracterizador;
	}

	/**
	 * @param caracterizador the caracterizador to set
	 */
	public void setCaracterizador(boolean caracterizador) {
		this.caracterizador = caracterizador;
	}

	/**
	 * @return the strCodigoDNP
	 */
	public String getStrCodigoDNP() {
		return strCodigoDNP;
	}

	/**
	 * @param strCodigoDNP the strCodigoDNP to set
	 */
	public void setStrCodigoDNP(String strCodigoDNP) {
		this.strCodigoDNP = strCodigoDNP;
	}

	/**
	 * @return the tabCaracterizacion
	 */
	public boolean isTabCaracterizacion() {
		return tabCaracterizacion;
	}

	/**
	 * @param tabCaracterizacion the tabCaracterizacion to set
	 */
	public void setTabCaracterizacion(boolean tabCaracterizacion) {
		this.tabCaracterizacion = tabCaracterizacion;
	}	
	
	//https://www.geodatos.net/coordenadas/colombia
	void inicializaCenterZoneByCodPostal(){
		
	}

	void inicializaCenterZoneByCodDANE(){
		hmCenterZoneDane = new HashMap<>();
		hmCenterZoneDane.put("1629", "6.2518401, -75.563591");/*Medellin*/
		hmCenterZoneDane.put("1618", "4.6097102, -74.081749");/*Bogota*/
		hmCenterZoneDane.put("2094", "6.1846099, -75.5991287");/*Itagui*/
		
		
	}

}
