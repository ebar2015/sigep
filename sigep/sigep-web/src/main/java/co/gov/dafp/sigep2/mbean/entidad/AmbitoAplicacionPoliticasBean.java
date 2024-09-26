/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import com.ibm.icu.util.Calendar;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadPoliticaPublica;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.UsuarioSession;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.EntidadPoliticaPublicaExt;
import co.gov.dafp.sigep2.mbean.ext.PoliticaPublicaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 *
 * @author Sergio
 */
@Named(value = "ambitoAplicacionPoliticasBean")
@ViewScoped
public class AmbitoAplicacionPoliticasBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 9112093691832483906L;
	
    @Inject
    private GestionEntidadBean gestionEntidadBean;
    private List<Entidad> entidadesDisponibles;
    private List<Parametrica> politicasList;
    private List<Parametrica> politicasSeleccionadas; // Variable que obtiene todas las politicas publicas seleccionadas.
    private List<Entidad> entidadesSeleccionadas;
    private List<Long> listCodPoliticas;
    private Parametrica politicaDetalle;
    private PoliticaPublicaExt nuevaPolitica;
    private Entidad entidadDetalle;
    private List<EntidadPoliticaPublicaExt> politicasEntidad; //Variable que obtiene las politicas publicas que tiene asociada una Entidad
    private Boolean blnEsmodificado; //Vriable que permite saber si la se ingreso a Gestionar politicas desde tipo planta.
    private Boolean blnGuardarCambios; //Variable que permite saber si se guardaron los cambios de asignacion de politicas
    private Boolean blnRenderCrearEntidad; //Variable que determina si se renderiza o no la opcion de crear entidad
    private Boolean blnDeshacerCambios; //Variable que determina si se deshace o no los cambios realizados para una politica
    ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	CreacionEntidadBean mBCrearEntidadBean = (CreacionEntidadBean) elContext.getELResolver().getValue(elContext,null, "creacionEntidadBean");
	

    @PostConstruct
    @Override
    public void init() {
    	registrarSessionFincion();
    	
    	boolean lbesAmbito = true;
    	blnRenderCrearEntidad = true;
    	blnDeshacerCambios = false;
    	ExternalContext externalContext= FacesContext.getCurrentInstance().getExternalContext();
    	if(externalContext!=null && externalContext.getSessionMap()!=null && externalContext.getSessionMap().get("lbEsAmbitoPoliticasEntidad")!=null){
    		lbesAmbito = (boolean) externalContext.getSessionMap().get("lbEsAmbitoPoliticasEntidad");
    		if(lbesAmbito) {
    			 blnRenderCrearEntidad = false;
    		}
    	}
    	if(!validarPermisoRol() && lbesAmbito ){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"window.location.assign('gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;

    	}
    	blnEsmodificado 	= false;
    	blnGuardarCambios 	= false;
    	verificarModificar();
        entidadesDisponibles 	= new ArrayList<>();
        entidadesSeleccionadas 	= new ArrayList<>();
        politicasSeleccionadas 	= new ArrayList<>();
        nuevaPolitica 			= new PoliticaPublicaExt();
        if (gestionEntidadBean.getEntidadesSeleccionadas() != null && !gestionEntidadBean.getEntidadesSeleccionadas().isEmpty()) {
            entidadesDisponibles.addAll(gestionEntidadBean.getEntidadesSeleccionadas());
        } else if (gestionEntidadBean.getEntFiltro() != null) {
            entidadesDisponibles.add(gestionEntidadBean.getEntFiltro());
        }
        llenarListaPoliticas();
        reiniciarCampos();
    }
    
    /**
     * Metodo que trae las politicas desde la tabla parametrica.
     */
    public void llenarListaPoliticas() {
    	Parametrica obje = new Parametrica();
    	obje.setCodPadreParametrica(new BigDecimal(TipoParametro.POLITICAS_PUBLICAS.getValue()));
    	obje.setFlgEstado((short)1);
    	politicasList  = ComunicacionServiciosEnt.getPoliticasParametrica(obje);
    }
    
    /**
     * Metodo que permite identificar si la accion que se esta realizando es la de modificar.
     */
    public void verificarModificar() {
    	if(gestionEntidadBean.isLbEsModificarEntidad()) {
    		blnEsmodificado = true;
    	}
    }
    
    public void reiniciarCampos() {
        entidadesSeleccionadas 	= null;
        politicasSeleccionadas 	= null;
        entidadDetalle	 		= null;
        politicaDetalle 		= null;
    }

    public void btDialogoConfirmaAsignacionListener() {
		if (!validarSessionFuncion()) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ENTIDAD_AMBITO_POL_SESSION);
		} else {
			if (entidadesSeleccionadas != null && !entidadesSeleccionadas.isEmpty() && politicasSeleccionadas != null
					&& !politicasSeleccionadas.isEmpty()) {
				if (validarAsocacionPoliticas()) {
					RequestContext.getCurrentInstance().execute("PF('dlgExisteAsociacion').show()");
				} else {
					btConfirmarAsociacionesListener();
				}
			}
		}
    }

    /**
     * Metodo  guarda las asociaciones de las politicas.
     */
    public void btConfirmarAsociacionesListener() {
        try {
        	guardarAsociacionesPoliticas();
            mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_ASOCIACION_EXITOSA);
            reiniciarCampos();
        } catch (Exception ex) {
            Logger.getLogger(AmbitoAplicacionPoliticasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public String btCancelarAction() {
        return "/entidad/gestionarEntidad?faces-redirect=true";
    }


    
    /**
     * Metodo que valida si para cualquier politica seleccionada y entidad, existe alguna asociacion
     * @return
     */
    public boolean validarAsocacionPoliticas() {
    	for (Parametrica pp : politicasSeleccionadas) {
    		for (Entidad ent : entidadesSeleccionadas) {
                EntidadPoliticaPublica epp = new EntidadPoliticaPublica();
                epp.setCodEntidad(ent.getCodEntidad().longValue());
                epp.setCodPoliticaPublica(pp.getCodTablaParametrica().longValue());
                epp.setLimitEnd(0);
                List<EntidadPoliticaPublica> eppList = ComunicacionServiciosEnt.getEntidadPoliticasPublicas(epp);
                if (eppList != null && !eppList.isEmpty()) {
                    return true;
                }
            }
    	}
    	return false;
    }
    
    /**
     * Metodo que permite guardar las asociaciones de varias politicas a las entidades seleccionadas.
     */
	public void guardarAsociacionesPoliticas() throws Exception {
		  listCodPoliticas = new ArrayList<>();
		  if(!validarSessionFuncion()) {
	    		mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_AMBITO_POL_SESSION);
	      }else {
	    	  
	    		  for (Parametrica pp : politicasSeleccionadas) {
	  				for (Entidad ent : entidadesSeleccionadas) {
	  					EntidadPoliticaPublica epp = new EntidadPoliticaPublica();
	  					epp.setCodEntidad(ent.getCodEntidad().longValue());
	  					epp.setCodPoliticaPublica(pp.getCodTablaParametrica().longValue());
	  					epp.setLimitEnd(0);
	  					List<EntidadPoliticaPublica> eppList = ComunicacionServiciosEnt.getEntidadPoliticasPublicas(epp);
	  					if (eppList == null || eppList.isEmpty()) {
	  						epp.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
	  						epp.setAudCodRol((int) (long)this.getRolAuditoria().getId());
	  						epp.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
	  						epp.setAudFechaActualizacion(Calendar.getInstance().getTime());
	  						ComunicacionServiciosEnt.setEntidadPoliticaPublica(epp);
	  						List<EntidadPoliticaPublica> etp = ComunicacionServiciosEnt.getEntidadPoliticaPublicaFiltro(epp);
	  						if(etp != null) {
	  							if(!etp.isEmpty()) {
	  								listCodPoliticas.add(etp.get(0).getCodEntidadPoliticaPublica());
	  								blnDeshacerCambios = true;
	  							}
	  						}
	  						blnGuardarCambios = true;
	  					}
	  				}
	  			}
	  			politicasSeleccionadas = new ArrayList<>();
	  	     	entidadesSeleccionadas = new ArrayList<>();
	    	 
	      }
	}
	/**
	 * Metodo que deshace las politicas que fueron asociadas a las entidades.
	 */
	public void deshacerGuardado() {
		boolean exito = false;
		if(listCodPoliticas != null) {
			if(!listCodPoliticas.isEmpty()) {
				for (Long  id : listCodPoliticas) {
					ComunicacionServiciosEnt.delEntidadPoliticaPublica(id);
					exito = true;
				}
				if(exito) {
					  mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_DESHACER_EXITO);
				}else {
					  mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_DESHACER_FALLO);
				}
			}
		}
		if(!exito) {
			 mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_DESHACER_NO);
		}
		blnDeshacerCambios = false;
		
	}

    /**
     * Metodo que permite obtener las politicas con las que cuenta actualmente una entidad.
     */
    public void consultarPoliticasEntidad(Entidad entidad) {
    	entidadDetalle = entidad;
    	if(entidadDetalle !=null) {
    		EntidadPoliticaPublicaExt objFilter = new EntidadPoliticaPublicaExt();
    		objFilter.setCodEntidad(entidadDetalle.getCodEntidad().longValue());
    		politicasEntidad = ComunicacionServiciosEnt.getPoliticasPublicasEntidad(objFilter);
    		RequestContext.getCurrentInstance().execute("PF('verDetalleEntidad').show();");
    	}
    }
    
    /**
     * Metodo que permite guardar una nueva politica publica.
     */
    public void guardarNuevaPoliticaPublica() {
    	if(!validarSessionFuncion()) {
    		mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_AMBITO_POL_SESSION);
    	}else {
	    	nuevaPolitica.setAudFechaActualizacion(DateUtils.getFechaSistema());
	    	nuevaPolitica.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
	    	nuevaPolitica.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
	    	nuevaPolitica.setAudCodRol((int)this.getRolAuditoria().getId());
	    	nuevaPolitica.setFlgActivo((short)1);
	    	
	    	boolean valid = ComunicacionServiciosEnt.setNuevaPoliticaPublica(nuevaPolitica);
	    	if(valid) {
	    		mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_POLITICA_EXITOSA);
	    		llenarListaPoliticas();
	    	}else{
	    		mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_POLITICA_ERROR);
	    	}
	    	RequestContext.getCurrentInstance().execute("PF('wdgtNuevaPolitica').hide();");
	    	nuevaPolitica = new PoliticaPublicaExt();
    	}
    }
    
    /**
     * Metodo que permite limpiar el formulario de creación de nueva politica
     */
    public void cancelarNuevaPolitica() {
    	nuevaPolitica = new PoliticaPublicaExt();
    }
    
    
    public void asignarNuevaPolitica() {
    	if(entidadesSeleccionadas != null && !entidadesSeleccionadas.isEmpty() && politicasSeleccionadas != null && !politicasSeleccionadas.isEmpty()) {
    		mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.MSG_ENTIDAD_POLITICA_CONF_ASIGNAR);
    	}else {
    		mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_POLITICA_VALD);
    	}
    }
    
    /**
     * Metodo que permite validar la seleccion de politicas y entidades.
     */
    public void validarSeleccion() {
    	if(entidadesSeleccionadas != null && !entidadesSeleccionadas.isEmpty()
                && politicasSeleccionadas != null && !politicasSeleccionadas.isEmpty()) {
    		RequestContext.getCurrentInstance().execute("PF('confirmDlg').show();");
    	}else {
    		mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_POLITICA_VALD);
    	}
    }
    
    /**
	 * Metodo que redirecciona a crear Entidad
	 */
	public void redireccionarCrearEntidad(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("crearEntidad.xhtml?isMenu=1");
			mBCrearEntidadBean.setLbEsCrearEntidad(true);
		} catch (IOException e) {
			logger.error("El metodo no redirecciono - redireccionarCrearEntidad()", e);
		}
	}
	
	/**
	 * Metodo que reinicia las variables de selecciion de politicas y entidades.
	 */
	public void cancelarAsinacionPoliticas() {
		politicasSeleccionadas = new ArrayList<>();
		entidadesSeleccionadas = new ArrayList<>();
	}
    
    
    public List<Entidad> getEntidadesDisponibles() {
        return entidadesDisponibles;
    }

    /**
	 * @return the politicaDetalle
	 */
	public Parametrica getPoliticaDetalle() {
		return politicaDetalle;
	}

	/**
	 * @param politicaDetalle the politicaDetalle to set
	 */
	public void setPoliticaDetalle(Parametrica politicaDetalle) {
		this.politicaDetalle = politicaDetalle;
	}

	public Entidad getEntidadDetalle() {
        return entidadDetalle;
    }

    public void setEntidadDetalle(Entidad entidadDetalle) {
        this.entidadDetalle = entidadDetalle;
    }

    public List<Entidad> getEntidadesSeleccionadas() {
        return entidadesSeleccionadas;
    }

    public void setEntidadesSeleccionadas(List<Entidad> entidadesSeleccionadas) {
        this.entidadesSeleccionadas = entidadesSeleccionadas;
    }

    public void setEntidadesDisponibles(List<Entidad> entidadesDisponibles) {
        this.entidadesDisponibles = entidadesDisponibles;
    }
    
    /**
	 * @return the politicasEntidad
	 */
	public List<EntidadPoliticaPublicaExt> getPoliticasEntidad() {
		return politicasEntidad;
	}

	/**
	 * @param politicasEntidad the politicasEntidad to set
	 */
	public void setPoliticasEntidad(List<EntidadPoliticaPublicaExt> politicasEntidad) {
		this.politicasEntidad = politicasEntidad;
	}

	/**
	 * @return the nuevaPolitica
	 */
	public PoliticaPublicaExt getNuevaPolitica() {
		return nuevaPolitica;
	}

	/**
	 * @param nuevaPolitica the nuevaPolitica to set
	 */
	public void setNuevaPolitica(PoliticaPublicaExt nuevaPolitica) {
		this.nuevaPolitica = nuevaPolitica;
	}

	/**
	 * @return the blnEsmodificado
	 */
	public Boolean getBlnEsmodificado() {
		return blnEsmodificado;
	}
	/**
	 * @param blnEsmodificado the blnEsmodificado to set
	 */
	public void setBlnEsmodificado(Boolean blnEsmodificado) {
		this.blnEsmodificado = blnEsmodificado;
	}

	/**
	 * @return the blnGuardarCambios
	 */
	public Boolean getBlnGuardarCambios() {
		return blnGuardarCambios;
	}
	/**
	 * @param blnGuardarCambios the blnGuardarCambios to set
	 */
	public void setBlnGuardarCambios(Boolean blnGuardarCambios) {
		this.blnGuardarCambios = blnGuardarCambios;
	}
	
	
	/**
	 * @return the politicasSeleccionadas
	 */
	public List<Parametrica> getPoliticasSeleccionadas() {
		return politicasSeleccionadas;
	}

	/**
	 * @param politicasSeleccionadas the politicasSeleccionadas to set
	 */
	public void setPoliticasSeleccionadas(List<Parametrica> politicasSeleccionadas) {
		this.politicasSeleccionadas = politicasSeleccionadas;
	}

	/**
	 * @return the blnRenderCrearEntidad
	 */
	public Boolean getBlnRenderCrearEntidad() {
		return blnRenderCrearEntidad;
	}
	/**
	 * @param blnRenderCrearEntidad the blnRenderCrearEntidad to set
	 */
	public void setBlnRenderCrearEntidad(Boolean blnRenderCrearEntidad) {
		this.blnRenderCrearEntidad = blnRenderCrearEntidad;
	}

	/**
	 * @return the blnDeshacerCambios
	 */
	public Boolean getBlnDeshacerCambios() {
		return blnDeshacerCambios;
	}
	/**
	 * @param blnDeshacerCambios the blnDeshacerCambios to set
	 */
	public void setBlnDeshacerCambios(Boolean blnDeshacerCambios) {
		this.blnDeshacerCambios = blnDeshacerCambios;
	}
	/**
	 * @return the politicasList
	 */
	public List<Parametrica> getPoliticasList() {
		return politicasList;
	}

	/**
	 * @param politicasList the politicasList to set
	 */
	public void setPoliticasList(List<Parametrica> politicasList) {
		this.politicasList = politicasList;
	}

	@Override
    public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String persist() throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void retrieve() throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String update() throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete() throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	private boolean validarPermisoRol(){
		try {
			if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_POLITICAS,RolDTO.ADMINISTRADOR_FUNCIONAL))
				return true;
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		return false;
	}	
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya Jan 14, 2019
	 */
	private void registrarSessionFincion() {
		UsuarioSession usuarioSession = new UsuarioSession();
    	usuarioSession.setNombreFuncion(AMBITO_POLITICAS);
    	usuarioSession.setFlgActivo((short) 1);
    	
    	List<UsuarioSession> ltsUsr = ComunicacionServiciosSis.getUsuarioSession(usuarioSession);
    	if(ltsUsr.isEmpty()) {
    		usuarioSession.setCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
    		usuarioSession.setFlgActivo((short) 0);
    		ltsUsr = ComunicacionServiciosSis.getUsuarioSession(usuarioSession);
    		if(ltsUsr.isEmpty()) {
    			BigDecimal codRol  = null;
            	if(!getRolesUsuarioSesion().isEmpty()) {
            		codRol= getRolesUsuarioSesion().get(0).getCodRol();
            		if(codRol == null) {
            			codRol = new BigDecimal(getRolesUsuarioSesion().get(0).getId());
            		}
            	}
            	usuarioSession.setAudAccion(785);
            	usuarioSession.setAudCodRol(codRol.intValue());
            	usuarioSession.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
            	usuarioSession.setAudFechaActualizacion(new Date());
            	usuarioSession.setFechaInicioSession(new Date());
            	usuarioSession.setFlgActivo((short) 1);
            	ComunicacionServiciosSis.setUsuarioSession(usuarioSession);
    		}else {
    			ltsUsr.get(0).setAudAccion(63);
    			ltsUsr.get(0).setAudFechaActualizacion(new Date());
    			ltsUsr.get(0).setFechaInicioSession(new Date());
    			ltsUsr.get(0).setFechaFinSession(null);
    			ltsUsr.get(0).setFlgActivo((short) 1);
				ComunicacionServiciosSis.setUsuarioSession(ltsUsr.get(0));
    		}
    	}
	}
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya Jan 14, 2019
	 * @return
	 */
	private boolean validarSessionFuncion() {
		/*UsuarioSession usuarioSession = new UsuarioSession();
    	usuarioSession.setNombreFuncion(AMBITO_POLITICAS);
    	usuarioSession.setFlgActivo((short) 1);
    	List<UsuarioSession> ltsUsr = ComunicacionServiciosSis.getUsuarioSession(usuarioSession);
    	if(ltsUsr.isEmpty()) {
    		registrarSessionFincion();
    		return true;
    	}else {
    		for (UsuarioSession usSession : ltsUsr) {
    			if(usSession.getCodUsuario().intValue() == getUsuarioSesion().getId()) {
    				usSession.setAudAccion(63);
    				usSession.setAudFechaActualizacion(new Date());
    				usSession.setFechaInicioSession(new Date());
    				usSession.setFechaFinSession(null);
    				ComunicacionServiciosSis.setUsuarioSession(usSession);
    				return true;
    			}
			}
    		return false;
    	}*/
		return true;
	}
	
	
}
