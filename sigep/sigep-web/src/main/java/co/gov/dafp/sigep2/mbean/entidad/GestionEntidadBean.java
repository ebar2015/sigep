/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.mbean.entidad;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadSistemaRegimen;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.UtilidadesFaces;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 * Este <code>JSFManagedBean</code> se crea con el fin de manejar la gestión de
 * entidad. Dado que a partir de la interfaz de gestión de entidad se manejan
 * las operaciones de entidad, se ha definido como
 * <code>ConversationScoped</code> con el fin de guardar la entidad seleccionada
 * en la variable <code>entidadSeleccionada</code> y en cada uno de los
 * <code>JSFManagedBean</code> que se requiere, debe llamarse mediante la
 * anotación <i>@Inject</i>
 * <p>
 * <b><i>NOTA IMPORTANTE:</i></b><br/>
 * Se recomienda ALTAMENTE llamar el método <code>endConversation()</code> una
 * vez se haya alimentado la variable de entidad seleccionada en el
 * <code>JSFManagedBean</code> en el que se emplee este
 * <code>JSFManagedBean</code> para evitar que queden datos de la interfaz de
 * gestión accesibles mediante el id de conversación (ver ejemplo del uso en el
 * método <code>init()</code> de <code>ModificacionEntidadBean</code>
 * </p>
 *
 * @author Sergio
 */
@Named(value = "gestionEntidadBean")
@ConversationScoped
public class GestionEntidadBean extends BaseBean implements Serializable {
    private static final long serialVersionUID = 2068190236731463648L;


    @Inject
    private Conversation conversation;

    private Entidad entFiltro;
    private Entidad entSelect;
    private List<Entidad> lstEntidades;// Lista de entidades
    private Boolean verFiltros;// Controla si se muestra o no el panel de filtros
    private Boolean esFiltro;// Indica si se esta ejecutando filtros
    private Long codEntidad = 0L;
    private Long codTipoNorma = 0L;
    private List<Norma> listaNormas;// Lista de entidades
    private Norma tipoNorma;
    private String nombreEntidad;
    List<String> alEntidades = new ArrayList<String>();
    private List<Entidad> entidadesSeleccionadas;
    private Entidad entidadSeleccionada;
    private boolean lbEsModificarEntidad;
    private boolean lbestadoInactivo;
    private boolean lbDependenciaespecialBuscar;
    private boolean flgDependencia;
    private boolean btnHabilitarBotonPoliticas;
    private BigDecimal codigoDNP;
    private final long CARACTERIZADOR_ENTIDAD = 19l;
    private final static int SISTEMA_CARRERA = 1;
    private final static int ESCALA_SALARIAL = 2;
    private final static int NATURALEZA_EMPLEO = 3;
    private final static int ENTIDADES_MINIMAS = 2;
    
    

    public Long getCodTipoNorma() {
        return codTipoNorma;
    }

    public void setCodTipoNorma(Long codTipoNorma) {
        this.codTipoNorma = codTipoNorma;
    }

    public List<Norma> getListaNormas() {
        return listaNormas;
    }

    public void setListaNormas(List<Norma> listaNormas) {
        this.listaNormas = listaNormas;
    }

    public Entidad getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(Entidad entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

    public List<Entidad> getEntidadesSeleccionadas() {
        return entidadesSeleccionadas;
    }

    public void setEntidadesSeleccionadas(List<Entidad> entidadesSeleccionadas) {
        this.entidadesSeleccionadas = entidadesSeleccionadas;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public Long getCodEntidad() {
        return codEntidad;
    }

    public void setCodEntidad(Long codEntidad) {
        this.codEntidad = codEntidad;
    }

    public void setEntidadesSeleccionadas(Entidad entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

    /**
     * @return the lbEsModificarEntidad
     */
    public boolean isLbEsModificarEntidad() {
        return lbEsModificarEntidad;
    }

    /**
     * @param lbEsModificarEntidad the lbEsModificarEntidad to set
     */
    public void setLbEsModificarEntidad(boolean lbEsModificarEntidad) {
        this.lbEsModificarEntidad = lbEsModificarEntidad;
    }

    @Override
    @PostConstruct
    public void init() {
    	cerrarSessionFuncion(AMBITO_POLITICAS);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Object filtroEntidadSesion = context.getSessionMap().get("filtroGestionEntidad");
        if(filtroEntidadSesion != null) {
            entFiltro = (Entidad) filtroEntidadSesion;
        }else {
            entFiltro = new Entidad();
        }
        conversation.begin();
        esFiltro = false;
        consultarEntidades();
        
        //Si ya hay una entidad en sesion es porque paso a otra ventana y volvió
        
        if(filtroEntidadSesion != null) {
            verFiltros = true;
        }else {
            validarCantEntidades();
        }
        tipoNorma = new Norma();
        tipoNorma.setCodTipoNorma(0);
        entSelect = new EntidadExt();
        entidadesSeleccionadas = new ArrayList<>();
        if(context!=null && context.getRequestParameterMap()!=null && context.getRequestParameterMap().get("msgAdvertencia")!=null){
            mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
                    context.getRequestParameterMap().get("msgAdvertencia"));					
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ENTIDADES);
        validarBotonPoliticas();
    }

    public void validarBotonPoliticas() {
    	btnHabilitarBotonPoliticas = false;
    	 if(!entidadesSeleccionadas.isEmpty()&& entidadesSeleccionadas.size() >= ENTIDADES_MINIMAS) {
         	btnHabilitarBotonPoliticas = true;
         }
    }
    
    public String btnAmbitoAplicacion() {
    	if(entidadesSeleccionadas.size() <= 0) {
    		mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
    				MessagesBundleConstants.MSG_ENTIDAD_POLITICA_BOTON);
    		return "";
    		
    	}
    	 if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
             noautorizado();
             return "";
         }else {
        	 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
             externalContext.getSessionMap().put("lbEsModificarEntidad", false);
             externalContext.getSessionMap().put("lbEsAmbitoPoliticasEntidad", true);
             externalContext.getSessionMap().put("lbEsConsultarEntidad", false);	
             externalContext.getSessionMap().put("lbEsCrearEntidad", false);
         }
    	 return "gestionAmbitoAplicacion?faces-redirect=true";
    }
    
    public String btAccionEntidadListener(Entidad entidad, String accion) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getSessionMap().put("entidadModificar", entidad);
        externalContext.getSessionMap().put("direccion", entidad.getDireccion());
        setEntFiltro(entidad);
        if(accion.contains("modificarEntidad")){
        	if(!validarAdminFuncionalEntidadInactiva(entidad)) {
    			return "";
    		}        	
            externalContext.getSessionMap().put("lbEsModificarEntidad", true);
            externalContext.getSessionMap().put("lbEsAmbitoPoliticasEntidad", false);
            externalContext.getSessionMap().put("lbEsConsultarEntidad", false);
            externalContext.getSessionMap().put("lbEsCrearEntidad", false);
            lbEsModificarEntidad = true;  
            
        }else if(accion.contains("consultarDatosEntidad")){
            if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
                noautorizado();
                return "";
            }else {
                externalContext.getSessionMap().put("lbEsModificarEntidad", false);
                externalContext.getSessionMap().put("lbEsAmbitoPoliticasEntidad", false);
                externalContext.getSessionMap().put("lbEsConsultarEntidad", true);
                externalContext.getSessionMap().put("lbEsCrearEntidad", false);
            }
                    
        }else if(accion.contains("gestionAmbitoAplicacion")){
        	if(!validarEntidadActiva(entidad)) {
    			return "";
    		}  
            if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
                noautorizado();
                return "";
            }else {
            	if(btnHabilitarBotonPoliticas) {
            		mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
            				MessagesBundleConstants.DLG_AMBITO_POLITICAS_ADV);
            		return "";
            	}
            	
                externalContext.getSessionMap().put("lbEsModificarEntidad", false);
                externalContext.getSessionMap().put("lbEsAmbitoPoliticasEntidad", true);
                externalContext.getSessionMap().put("lbEsConsultarEntidad", false);	
                externalContext.getSessionMap().put("lbEsCrearEntidad", false);
            }
                    
        }else if(accion.contains("eliminarEntidad")) {
            if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
                noautorizado();
                return "";
            }
            
            try {
            	Boolean validRold = this.usuarioTieneRolAsignadoSinJerarquia(
        				RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS);
            	
            	if(!validRold) {
            		noautorizado();
                    return "";
            	}
            	
    		} catch (Exception e) {
    			logger.error("eliminarEntidad()", e);
    		}    
        }
        return accion;
    }
    
    public void startConversation() {
        if (!conversation.isTransient()){
            conversation.end();
        }
        conversation.begin();
    }
    
    /**
     * Metodo que valida que el admin funcional y el superadministrador sean los unicos que tengan la opcion de modificar la entidad
     * cuando esta esta en estado inactiva
     * */
    
    public boolean validarAdminFuncionalEntidadInactiva(Entidad entidad) {
    	try {
        	Boolean validRold = this.usuarioTieneRolAsignadoSinJerarquia(
    				RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.SUPER_ADMINISTRADOR);
        	
        	if(!validRold) {
        		return validarEntidadActiva(entidad);
        	}
        	
		} catch (Exception e) {
			logger.error("validarAdminFuncionalEntidadInactiva()", e);
		}    
    	return true;
    }

    /**
     * Mediante este método se finaliza la conversación, esto es, se limpia la
     * variable de entidad seleccionada y deja de ser accesible para otras
     * interfaces. Es de alta importancia ejecutar este método una vez se haya
     * alimentado la variable en todo <code>JSFManagedBean</code> en el que se
     * emplee <code>GestionEntidadBean</code>
     */
    public void endConversation() {
        conversation.end();
    }

    @Override
    public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    /**
     * Mediante este método se consultan las entidades a mostrar
     */
    public void consultarEntidades() {
        // Valida si se consultan entidades por rol o por usuario
        if (!validaPorRol()) {
            // LIstado de entidades del usuario
            lstEntidades = new ArrayList<>();
            // Consulta las entidades asociadas al usuari ologeado
            lstEntidades = ComunicacionServiciosSis.getEntidadCodPersona(getUsuarioSesion().getId());
            // Filtra entidades que no esten inactivas y demas filtros 6
            for (int i = lstEntidades.size() - 1; i >= 0; i--) {
                if (esFiltro) {
                    if (!entFiltro.getCodigoSigep().isEmpty()) {
                        if (!entFiltro.getCodigoSigep().equals(lstEntidades.get(i).getCodigoSigep())) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (!entFiltro.getNombreEntidad().isEmpty()) {
                        if (!entFiltro.getNombreEntidad().equals(lstEntidades.get(i).getNombreEntidad())) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (!entFiltro.getSigla().isEmpty()) {
                        if (!entFiltro.getSigla().equals(lstEntidades.get(i).getSigla())) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (!entFiltro.getNit().isEmpty()) {
                        if (!entFiltro.getNit().equals(lstEntidades.get(i).getNit())) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodigoDane() != null) {
                        if (entFiltro.getCodigoDane() != lstEntidades.get(i).getCodigoDane()) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodEstadoEntidad() != null) {
                        if (entFiltro.getCodEstadoEntidad() != lstEntidades.get(i).getCodEstadoEntidad()) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodTipoEntidad() != null) {
                        if (entFiltro.getCodTipoEntidad().equals(lstEntidades.get(i).getCodTipoEntidad())) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodSectorAdministrativo() != null) {
                        if (entFiltro.getCodSectorAdministrativo() != lstEntidades.get(i)
                                .getCodSectorAdministrativo()) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodClasificacionOrganica() != null) {
                        if (entFiltro.getCodClasificacionOrganica() != lstEntidades.get(i)
                                .getCodClasificacionOrganica()) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodNaturaleza() != null) {
                        if (entFiltro.getCodNaturaleza() != lstEntidades.get(i).getCodNaturaleza()) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodOrden() != null) {
                        if (entFiltro.getCodOrden() != lstEntidades.get(i).getCodOrden()) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodSuborden() != null) {
                        if (entFiltro.getCodSuborden() != lstEntidades.get(i).getCodSuborden()) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodDepartamento() != null) {
                        if (entFiltro.getCodDepartamento() != lstEntidades.get(i).getCodDepartamento()) {
                            lstEntidades.remove(i);
                            continue;
                        }
                    }
                    if (entFiltro.getCodMunicipio() != null) {
                        if (entFiltro.getCodMunicipio() != lstEntidades.get(i).getCodMunicipio()) {
                            lstEntidades.remove(i);
                        }
                    }
                    if (entFiltro.getCodCategoria() != null) {
                        if (entFiltro.getCodCategoria() != lstEntidades.get(i).getCodCategoria()) {
                            lstEntidades.remove(i);
                        }
                    }
                    if(lstEntidades.get(i).getFlgActivo() == 0)
                    	lstEntidades.remove(i);
                }
            }
        }
    }

    /**
     * Mediante este método se valida si se consultan entidades por rol
     *
     * @return
     */
    public boolean validaPorRol() {
        Boolean esValidacionRol = false;
        // validar rol de usuario logeado
        try {
            if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL)
                    || usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)
                    || usuarioTieneRolAsignadoSinJerarquia(RolDTO.CARACTERIZADOR_ENTIDAD)) {
                // Filtros comunes
                entFiltro.setLimitEnd(1000);
                // estados condición solo para el init
               
                if("".equals(entFiltro.getCodigoDane())){
                	entFiltro.setCodigoDane(null);
                }
                
                if(!lbDependenciaespecialBuscar) {
                    entFiltro.setFlgDependenciaEspecial((short) 0);
                } else {
                    entFiltro.setFlgDependenciaEspecial((short) 1);
                }
                entFiltro.setLimitInit(0);
                entFiltro.setFlgActivo((short) 1);
             // Tipo condicion solo para el init
                if(!esFiltro && entFiltro.getCodTipoEntidad()==null){
                    /*sí es administrador funcional trae inciialmente las públicas - sino trae inicialmente las privadas*/
                    if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL)) {
                    	lstEntidades = ComunicacionServiciosEnt.getEntidadesFiltro(entFiltro);
                    	
                    	for (int i = lstEntidades.size() - 1; i >= 0; i--) {
                    		Entidad filtro = lstEntidades.get(i); 
                    		if (filtro.getCodEstadoEntidad() != null) {
                                if (filtro.getCodEstadoEntidad().intValue() == TipoParametro.ENTIDAD_INACTIVA.getValue()) {
                                    lstEntidades.remove(filtro);
                                    continue;
                                }
                            }
                    		
                    		if (filtro.getCodTipoEntidad() != null) {
                                if (filtro.getCodTipoEntidad().intValue() == TipoParametro.ENTIDAD_PRIVADA.getValue()) {
                                	lstEntidades.remove(filtro);
                                	continue;
                                }
                                
                            }
                    		if (filtro.getFlgActivo() == 0)
                    			lstEntidades.remove(filtro);
                    	}
                        
                        
                    }else{
                        entFiltro.setCodEstadoEntidad(new BigDecimal(TipoParametro.ENTIDAD_ACTIVA.getValue()));
                        lstEntidades = ComunicacionServiciosEnt.getEntidadesFiltro(entFiltro);
                        entFiltro.setCodEstadoEntidad(new BigDecimal(TipoParametro.ENTIDAD_ACTIVA.getValue()));
                    }
                }
                
                setFlgDependencia(lbDependenciaespecialBuscar);
                esValidacionRol = true;
            }
        } catch (SIGEP2SistemaException e) {
            return false;
        }
        return esValidacionRol;
    }

    /**
     * Mediante este método se valida el tamaño de lista de entidades para mostrar o
     * no los filtros
     */
    public void validarCantEntidades() {
        verFiltros = (lstEntidades.size() > 1);
    }

    /**
     * Mediante este método se ejecutan los filtros para traer entidades especificas
     */
    public void filtrarLista() {
        //Se guarda el filtro de la entidad en session
        
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        contexto.getSessionMap().put("filtroGestionEntidad", entFiltro);
        
        if(codigoDNP != null) {
        	Parametrica paramCodigoDNP = ComunicacionServiciosSis.getParametricaporId(codigoDNP);
        	if(paramCodigoDNP != null) {
        		List<Parametrica> categorias = ComunicacionServiciosSis.getParametricaPorIdPadre(TipoParametro.TIPO_CATEGORIA_ENTIDAD.getValue());
                if (categorias != null && !categorias.isEmpty()) {
                    for (Parametrica categoria : categorias) {
                        if (categoria.getValorParametro().equals(paramCodigoDNP.getValorParametro())) {
                        	entFiltro.setCodCategoria(categoria.getCodTablaParametrica());
                        	break;
                        }
                    }
                }
        	}
        }
        
        esFiltro = true;
        // Lmipia campos texto
        if (entFiltro.getNit().isEmpty()) {
            entFiltro.setNit(null);
        }
        if (entFiltro.getNombreEntidad().isEmpty()) {
            entFiltro.setNombreEntidad(null);
        }
        if (entFiltro.getSigla().isEmpty()) {
            entFiltro.setSigla(null);
        }
        if (entFiltro.getCodigoSigep().isEmpty()) {
            entFiltro.setCodigoSigep(null);
        }
        consultarFiltro();
    }
    
    
    public void consultarFiltro() {
        lstEntidades = new ArrayList<>();
        if(entFiltro.getCodigoDane().isEmpty()) {
        	entFiltro.setCodigoDane(null);
        }
        if(entFiltro.getCodigoCuin().isEmpty()) {
        	entFiltro.setCodigoCuin(null);
        }
        this.entFiltro.setFlgActivo((short) 1);
        lstEntidades = ComunicacionServiciosEnt.getEntidadesFiltro(entFiltro);
        entFiltro = new Entidad();
    }

    /**
     * Mediante este método se cancelan los filtros
     */
    public void cancelarFilros() {
        esFiltro = false;
        entFiltro = new EntidadExt();

        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        contexto.getSessionMap().put("filtroGestionEntidad", entFiltro);
        
        consultarEntidades();
        entFiltro = new EntidadExt();
    }

    public StreamedContent getFile() {
        InputStream stream = this.getClass().getResourceAsStream("yourFile.txt");
        return new DefaultStreamedContent(stream, "text/plain", "download.txt");
    }

    public String getValorParam(BigDecimal codigo) {
        Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(codigo);
        return parametrica.getNombreParametro();
    }

    public List<SelectItem> getTipoNaturalezaJuridica(int cod) {
        return null;
    }
    
    

    public void rowSelectListener(SelectEvent evt) {
        entidadesSeleccionadas.add((Entidad) evt.getObject());
        validarBotonPoliticas();
    }

    public void rowUnselectListener(UnselectEvent evt) {
        entidadesSeleccionadas.remove((Entidad) evt.getObject());
        validarBotonPoliticas();
    }
    
    public void toggleSelected(ToggleSelectEvent tse) {
    	entidadesSeleccionadas = new ArrayList<>();
    	if(tse.isSelected() && lstEntidades .size()>1) {
        	btnHabilitarBotonPoliticas = true;
        	entidadesSeleccionadas.addAll(lstEntidades);
        	
        } else {
        	btnHabilitarBotonPoliticas = false;
        }
    }

    public Entidad getEntFiltro() {
        return entFiltro;
    }

    public void setEntFiltro(Entidad entFiltro) {
        this.entFiltro = entFiltro;
    }

    public List<Entidad> getLstEntidades() {
        return lstEntidades;
    }

    public void setLstEntidades(List<Entidad> lstEntidades) {
        this.lstEntidades = lstEntidades;
    }

    public Boolean getVerFiltros() {
        return verFiltros;
    }

    public void setVerFiltros(Boolean verFiltros) {
        this.verFiltros = verFiltros;
    }

    @Override
    public String persist() throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    @Override
    public void retrieve() throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    @Override
    public String update() throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    @Override
    public void delete() throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    /**
     * 18072018
     *
     * @author Pablo Quintana Redirecciona la entidad seleccionada a gestionar
     *         estados de entidad
     *
     * @param entidadSel
     */
    public void gestionarEstadoEntidad(Entidad entidadSel) {
    	if(!validarEntidadActiva(entidadSel)) {
			return;
		} 
    	
        if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
            noautorizado();
            return;
        }else {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("entidadGestionEstado", entidadSel);
            UtilidadesFaces.redirect("gestionentidad/adminEstadoGestionEntidad.xhtml#no-back-button");
            endConversation();
        }
        
    }

    /**
     * Redirecciona a Gestionar Situaciones Administrativas Particulares CU 0420
     *
     * @author Jesus Torres
     * @param entidad
     *
     */
    public void gestionarSituacionesParticulares(Entidad entidad) {
    	if(!validarEntidadActiva(entidad)) {
    		return;
    	}  
        if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
            noautorizado();
            return;
        }else {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("entidadParticular", entidad);
            UtilidadesFaces.redirect("gestionentidad/situaciones/gestionarSituacionesParticulares.xhtml");
            endConversation();
        }
        
    }

    /**
     * Redirecciona a Gestionar estructura de entidad
     *
     * @author Jesus Torres
     * @param entidad
     *
     */
    public void estructuraEntidad(Entidad entidad) {
    	if(!validarEntidadActiva(entidad)) {
    		return;
    	}  
    	
        if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) 
            noautorizado();
        else {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("codEntidad", entidad.getCodEntidad());
            externalContext.getSessionMap().put("estructuraEntidad", entidad);
            UtilidadesFaces.redirect("gestionentidad/estructura/estructuraOrganizacional.xhtml");
        }
    }	

    /**
     * Redirecciona a Liquidar entidadesCU 0420
     *
     * @author Hugo Quintero
     * @param entidad
     *
     */
    public void liquidarEntidad(Entidad entidad) {
    	if(!validarEntidadActiva(entidad)) {
			return;
		}  	
    	
        if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
            noautorizado();
            return;
        }else {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("entidadLiquidar", entidad);
            UtilidadesFaces.redirect("liquidarentidad/liquidarEntidad.xhtml");
            endConversation();
        }
    }
    

    /**
     * Redirecciona a Fusionar entidadesCU 0420
     *
     * @author Hugo Quintero
     * @param entidad
     *
     */
    public void fusionarEntidad(Entidad entidad) {	
    	if(!validarEntidadActiva(entidad)) {
			return;
		}  
    	
        if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
            noautorizado();
            return;
        }else {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("entidadFusionar", entidad);
            UtilidadesFaces.redirect("fusionarEntidad/fusionarEntidad.xhtml");
            endConversation();
        }
        
    }	

    public Entidad getEntSelect() {
        return entSelect;
    }

    public void setEntSelect(Entidad entidad) {
        this.entSelect = entidad;
    }

    public Norma getTipoNorma() {
        return tipoNorma;
    }

    public void setTipoNorma(Norma tipoNorma) {
        this.tipoNorma = tipoNorma;
    }

    public List<Norma> recuperarNumeroNorma() {
        if (this.codTipoNorma != null)
            this.listaNormas = ComunicacionServiciosEnt.getNorma(this.codTipoNorma);

        return listaNormas;
    }
    

    public List<String> getAlEntidades() {
        return alEntidades;
    }

    public void setAlEntidades(List<String> alEntidades) {
        this.alEntidades = alEntidades;
    }
    
    

    public boolean isLbestadoInactivo() {
        return lbestadoInactivo;
    }

    public void setLbestadoInactivo(boolean lbestadoInactivo) {
        this.lbestadoInactivo = lbestadoInactivo;
    }

    /**
     * Redirecciona a escindir entidad
     *
     * @author Andrés Jaramillo
     * @param entidad
     *
     */
    public void escindirEntidad(Entidad entidad) {
    	if(!validarEntidadActiva(entidad)) {
			return;
		} 	
        if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD)
            noautorizado();
        else {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("codEntidad", entidad.getCodEntidad());
            UtilidadesFaces.redirect("escindirEntidad/escisionEntidad.xhtml");
        }
        
    }

    /**
     * Redirecciona a gestionarPlanta
     *
     * @author Andrés Jaramillo
     * @param entidad
     *
     */
    public void gestionarPlantas(Entidad entidad) {
    	if(!validarEntidadActiva(entidad)) {
			return;
		}  
    	
        if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
            noautorizado();
            return;
        }else {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("codEntidadProceso", entidad);
            UtilidadesFaces.redirect("gestionarTipoPlanta/gestionarTipoPlanta.xhtml");
        }
        
    }

    /**
     * Listado de entidades del usuario
     *
     * @author Hugo Quintero
     * @param entidad
     *
     */

    public List<SelectItem> getEntidadesUsuario() {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Entidad entidadActual = (Entidad) externalContext.getSessionMap().get("entidadFusionar");

        List<SelectItem> list = new ArrayList<>();
        List<Entidad> listN = ComunicacionServiciosSis.getEntidadCodPersona(getUsuarioSesion().getId());
        try {
            if (!listN.isEmpty()) {
                for (Entidad aux : listN) {
                    if (!aux.isError()) {
                        if (!aux.getCodEntidad().equals(entidadActual.getCodEntidad())) {
                            list.add(new SelectItem(aux.getCodEntidad(), aux.getNombreEntidad()));
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            e.getStackTrace();
            return new ArrayList<>();
        }

        return list;
    }
    
    /**
     * Redirecciona a Nomenclatura y escala salarial
     *
     * @author Andrés Jaramillo
     * @param entidad
     *
     */
    public void nomenclaturaEscala(Entidad entidad) {
        if(getUsuarioSesion().getCodRol() == CARACTERIZADOR_ENTIDAD) {
            noautorizado();
            return;
        }else {
        	if(!validarEntidadActiva(entidad)) {
    			return;
    		}    
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.getSessionMap().put("entidadProceso", entidad);
            UtilidadesFaces.redirect("nomenclatura/nomenclaturaEntidad/gestionarNomenclatura.xhtml");
        }
    }
    
	/**
	 * Metodo que valida si la entidad a la que va a gestionar si se encuentra activa
	 */
	private boolean validarEntidadActiva(Entidad entidad) {
		boolean blnActiva = true;
		Entidad objEntidad = new Entidad();
		objEntidad.setCodEntidad(entidad.getCodEntidad());
		objEntidad.setCodEstadoEntidad(new BigDecimal(TipoParametro.ENTIDAD_ACTIVA.getValue()));
		List<Entidad> activa = ComunicacionServiciosEnt.getEntidadesFiltro(objEntidad);
		if (activa.isEmpty()) {
			blnActiva = false;
			String mensajeInactivo = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_ENTIDAD_INACTIVA, getLocale())
					.replace("%entidad%", entidad.getNombreEntidad());
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, mensajeInactivo);
		}
		return blnActiva;
	}


    public boolean isLbDependenciaespecialBuscar() {
        return lbDependenciaespecialBuscar;
    }

    public void setLbDependenciaespecialBuscar(boolean lbDependenciaespecialBuscar) {
        this.lbDependenciaespecialBuscar = lbDependenciaespecialBuscar;
    }
    
    private void noautorizado() {
        mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
                MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
                TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
                "");
    }
    
    public void mostrarMensajeBotonAccion(Severity severidad, String title, String msg, String btnSi, String acccion) {
        String tituloLog = "void mostrarMensajeBotonAccion(Severity severidad, String title, String msg, String accion)";
        try {
            title = MessagesBundleConstants.getStringMessagesBundle(title, getLocale());
            msg = MessagesBundleConstants.getStringMessagesBundle(msg, getLocale());
        } catch (Exception e) {
            logger.warn(tituloLog, e);
            String exception = "Exception:";
            if (msg != null && msg.contains(exception)) {
                msg = msg.substring(msg.lastIndexOf(exception), msg.length()).replace(exception, "").trim();
            }
            try {
                msg = MessagesBundleConstants.getStringMessagesBundle(msg, getLocale());
            } catch (Exception e2) {
            }
        }
        String level = "info";
        if (FacesMessage.SEVERITY_INFO.equals(severidad)) {
            logger.log().info(tituloLog, title, msg);
        } else if (FacesMessage.SEVERITY_WARN.equals(severidad)) {
            level = "warning";
            logger.log().warn(tituloLog, title, msg);
        } else if (FacesMessage.SEVERITY_FATAL.equals(severidad) || FacesMessage.SEVERITY_ERROR.equals(severidad)) {
            level = "error";
            logger.log().error(tituloLog, title, msg);
        }
        String swal = "swal({title: '" + title + "',text: '" + msg + "',type: '" + level
                + "',showCancelButton: false,confirmButtonColor: '#3085d6',cancelButtonColor: '#d33',confirmButtonText: '"
                + btnSi + "'}).then((result) => {" + acccion + ";})";
        RequestContext.getCurrentInstance().execute(swal);
    }
    
    /**
     * @author Jhon Garcia
     * @param codNorma
     * @return
     */
    public Norma getNorma(Long codNorma) {
    	Norma norma = null;
    	if(codNorma != null) {
    		norma = ComunicacionServiciosEnt.getNormaPorId(codNorma);
    	}
    	return norma;
    }
    
    /**
     * Método para cambiar el formato de la norma
     * @param norma
     * @return fecha Norma
     */
    public String getFechaNorma(Norma norma) {
    	String fechaNorma = "";
    	DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	if(norma != null) {
    		if(norma.getFechaNorma() != null) {
    			fechaNorma = format.format(norma.getFechaNorma());
    		}
    	}
    	return fechaNorma;
    	
    }
    
    
    /**
     * @author Jhon Garcia
     * @param codCategoria
     * @return
     */
    public BigDecimal getCategoriaDNP(BigDecimal codCategoria) {
    	BigDecimal codigoDNP = null;
    	if(codCategoria != null) {
    		Parametrica sel = ComunicacionServiciosSis.getParametricaporId(codCategoria);
    		if (sel != null) {
                List<Parametrica> dnps = ComunicacionServiciosSis.getParametricaPorIdPadre(TipoParametro.CATEGORIA_DNP.getValue());
                if (dnps != null && !dnps.isEmpty()) {
                    for (Parametrica pr : dnps) {
                        if (pr.getValorParametro().equals(sel.getValorParametro())) {
                            codigoDNP = pr.getCodTablaParametrica();
                            return codigoDNP;
                        }
                    }
                }
            }
    	}
    	return codigoDNP;
    }
    
    /**
     * @author Jhon Garcia
     * @param codEntidad - 
     * @param opcion : Equivale a la información que desea obtener de la tabla ENTIDAD_SISTEMA_REGIMEN
     * 		1 : Sistemas de carrera
     * 		2 : Naturalezas de empleo
     * 		3 : Escala salarial
     *  
     * @return
     */
    public String getEntidadSistemaRegimen(BigDecimal codEntidad, int opcion) {
    	List<BigDecimal> codigosSistemaCarreraSeleccionados = new ArrayList<>();
    	List<BigDecimal> codigosNaturalezaEmpleoSeleccionados = new ArrayList<>();
    	List<BigDecimal> codigosEscalaSalarialSeleccionados  = new ArrayList<>();
    	
    	String resultado = "";
    	
    	List<EntidadSistemaRegimen> esrList = ComunicacionServiciosEnt.getEntidadesSistemaRegimenPorIdEntidad(codEntidad);
    	if(esrList != null) {
	    	   if(!esrList.isEmpty()) {
	    		   for (EntidadSistemaRegimen entidadSistemaRegimenExt : esrList) {
	    				if (entidadSistemaRegimenExt.getCodNaturalezaEmpleo() != null) {
	    					codigosNaturalezaEmpleoSeleccionados
	    							.add(entidadSistemaRegimenExt.getCodNaturalezaEmpleo());
	    				}
	    				if (entidadSistemaRegimenExt.getCodSistemaCarrera() != null) {
	    					codigosSistemaCarreraSeleccionados
	    								.add(entidadSistemaRegimenExt.getCodSistemaCarrera());
	    				}
	    				if (entidadSistemaRegimenExt.getCodEscalaSalarial() != null) {
	    					codigosEscalaSalarialSeleccionados.add(entidadSistemaRegimenExt.getCodEscalaSalarial());
	    				}
	    			}
	    	   }
    	}
    	
    	//Retorno de la información
    	switch(opcion) {
    		case SISTEMA_CARRERA:
    			resultado = getInfoEntidadSistemaRegimen(eliminarDuplicados(codigosSistemaCarreraSeleccionados));
    			break;
    		case ESCALA_SALARIAL:
    			resultado = getInfoEntidadSistemaRegimen(eliminarDuplicados(codigosEscalaSalarialSeleccionados));
    			break;
    		case NATURALEZA_EMPLEO: 
    			resultado = getInfoEntidadSistemaRegimen(eliminarDuplicados(codigosNaturalezaEmpleoSeleccionados));
    			break;
    	}
    	
    	return resultado;
    	
    	
    }
    
    /**
     * @author Jhon Garcia
     * Se transforma el short en booleano
     * @param flgDependenciaEspecial
     * @return
     */
    public boolean getFlgDependenciaEspecial(Short flgDependenciaEspecial) {
    	if(flgDependenciaEspecial != null) {
    		if(flgDependenciaEspecial == 1) {
    			return true;
    		}else {
    			return false;
    		}
    	}else {
    		return false;
    	}
    		
    }
    
    /**
     * Transforma el valor del codPersoneriaJuridica
     * @param personeriaJuridica
     * @return
     */
    public String getSINO(Short valor) {
	    if(valor != null) {	
    		if(valor == 0) {
	    		return "No";
	    	}else {
	    		return "Si";
	    	}
	    }else {
	    	return "No";
	    }
	}
    
    /**
     * Retorna el indicativo del pais
     * @param codPais
     * @return
     */
    public int getIndicativoPais(BigDecimal codPais) {
    	Integer indicativoPais = null;
    	if(codPais != null) {	
    		Pais pais = ComunicacionServiciosSis.getpisporid(codPais.longValue());
    		if(pais != null) {
    			indicativoPais = pais.getIndicativoPais();
    		}
    	}
    	return indicativoPais;
    }
    
    
    /**
     * Cuando se presentan registros repetidos se eliminan los duplicados
     * @param lista
     * @return
     */
    public List<BigDecimal> eliminarDuplicados(List<BigDecimal> lista){
    	HashSet<BigDecimal> hashSet = new HashSet<BigDecimal>(lista);
    	lista.clear();
    	lista.addAll(hashSet);
    	return lista;
    }
    
    
    /**
     * Retorna los valores de las paramétricas
     * @param listaCodigos
     * @return
     */
    public String getInfoEntidadSistemaRegimen(List<BigDecimal> listaCodigos) {
    	String resultado = "";
    	
    	for(BigDecimal codigo : listaCodigos) {
			Parametrica param = ComunicacionServiciosSis.getParametricaporId(codigo);
			if(param != null) {
				resultado = resultado + param.getNombreParametro() + ", ";
			}
		}
    	
    	if(!"".equals(resultado)) {
    		resultado = resultado.substring(0, resultado.length() - 2);
    	}
    	
    	return resultado;
    	
    }

	public BigDecimal getCodigoDNP() {
		return codigoDNP;
	}

	public void setCodigoDNP(BigDecimal codigoDNP) {
		this.codigoDNP = codigoDNP;
	}

	public boolean isFlgDependencia() {
		return flgDependencia;
	}

	public void setFlgDependencia(boolean flgDependencia) {
		this.flgDependencia = flgDependencia;
	}

	/**
	 * @return the btnHabilitarBotonPoliticas
	 */
	public boolean isBtnHabilitarBotonPoliticas() {
		return btnHabilitarBotonPoliticas;
	}

	/**
	 * @param btnHabilitarBotonPoliticas the btnHabilitarBotonPoliticas to set
	 */
	public void setBtnHabilitarBotonPoliticas(boolean btnHabilitarBotonPoliticas) {
		this.btnHabilitarBotonPoliticas = btnHabilitarBotonPoliticas;
	}
}