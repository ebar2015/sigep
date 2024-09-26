/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.mbean.entidad;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Contrato;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadSistemaRegimen;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.ContratoExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosCO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.Direccion;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 *
 * @author Developer
 */
@Named(value = "EliminarEntidadBean")
@ViewScoped
@ManagedBean

public class EliminarEntidadBean extends ConsultaEntidadBean {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<VinculacionExt> vinculacionesConsultar;
    private List<VinculacionExt> vinculacionesEliminar;
    private VinculacionExt vinculacionExt;
    private boolean eliminarVinc;
    private int flgConfirmado=0;

    private List<ContratoExt> contratosEntidad;
    private List<ContratoExt> contratosEliminar;
    private Contrato contrato;
    private Contrato contratoEliminado;
    private String nombreUsuario;
    String headerMsg;

    @Override
    @PostConstruct
    public void init() {
    	cerrarSessionFuncion(AMBITO_POLITICAS);
        try {
        	setEntidadN(gestionEntidadBean.getEntFiltro());
        	if(!validarPermisoRolEliminarEntidad()){
	    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
						TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
						"window.location.assign('gestionarEntidad.xhtml?faces-redirect=true')"); 
	    		return;
        	}
            super.init();
            inicializacionConsulta();
        } catch (Exception ex) {
            Logger.getLogger(ModificacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void inicializacionConsulta() throws Exception {
        setDisponibleDatosBasicos(true);
        setDisponibleCaracterizacion(false);
        setDisponibleAmbitoAplicacion(false);
        
        if(entidadN.getAudCodUsuario() != null) {
        	Usuario usuario = ComunicacionServiciosSis.getUsuarioPorId(entidadN.getAudCodUsuario());
        	if(usuario != null){
        		Persona persona = ComunicacionServiciosHV.getPersonaPorId(usuario.getCodPersona().longValue());
            	if(persona != null) {
            		nombreUsuario = persona.getPrimerNombre() + " " + persona.getPrimerApellido();
            	}
        	}
        }
        
        setAdscrito(getEntidadN().getCodAdscritaVinculada() != null);
        if (isAdscrito()) {
            setEntidadAdscritaSeleccionada(ComunicacionServiciosEnt.getEntidadPorId(getEntidadN().getCodAdscritaVinculada().longValue()));
        }
        somCategoriaEntidadListener();
        if (getEntidadN().getJustificacion() == null || getEntidadN().getJustificacion().isEmpty()) {
            setNorma(getNORMA_SI());
        } else {
            setNorma(getNORMA_NO());
        }
        if (getEntidadN().getDireccion() != null && !getEntidadN().getDireccion().isEmpty()) {
            Direccion dir = new Direccion();
            dir.llenarDatosDesdeJson(getEntidadN().getDireccion());
            direccionBean.setDireccionSeleccionada(dir);
            direccionBean.mostrarDireccion();
            getEntidadN().setDireccion(direccionBean.getDireccionGenerada());
        }
        List<EntidadSistemaRegimen> esrList = ComunicacionServiciosEnt.getEntidadesSistemaRegimenPorIdEntidad(getEntidadN().getCodEntidad());
        if (esrList != null && !esrList.isEmpty()) {
            setCodigosEscalaSalarialSeleccionados(new ArrayList<>());
            setCodigosSistemaCarreraSeleccionados(new ArrayList<>());
            esrList.stream().map((esr) -> {
                if (esr.getCodEscalaSalarial() != null) {
                    getCodigosEscalaSalarialSeleccionados().add("" + esr.getCodEscalaSalarial().intValue());
                }
                return esr;
            }).filter((esr) -> (esr.getCodSistemaCarrera() != null)).forEach((esr) -> {
                getCodigosSistemaCarreraSeleccionados().add("" + esr.getCodSistemaCarrera().intValue());
            });
        }
        headerMsg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES, getLocale());
    }

    @Override
    public String btRegresarAction() {
        gestionEntidadBean.endConversation();
        return "/entidad/gestionarEntidad?faces-redirect=true";
    }

    public void btEliminarEntidadListener(Long flgConfirmado) {  
    	vinculacionExt = new VinculacionExt();
        contrato = new Contrato();
        vinculacionExt.setCodEntidad(entidadN.getCodEntidad().longValue());
        vinculacionExt.setFlgActivo((short)1);      
        contrato.setCodEntidad(entidadN.getCodEntidad().longValue());
        contrato.setFlgActivo((short) 1); 
        contrato.setLimitInit(0);
        contrato.setLimitEnd(100);
        boolean tieneVinculaciones = validarVinculacionesEntidad(vinculacionExt);
        boolean tieneContratos = validarContratosEntidad(contrato);        
        if (!tieneVinculaciones && !tieneContratos)
        	operarEliminacionEntidad();
        else if((tieneVinculaciones || tieneContratos) && flgConfirmado==0)
        	btConfirmarEliminarEntidadListener();
        else if((tieneVinculaciones && tieneContratos) && flgConfirmado==1){
            if(!eliminarVinculacionesEntidad(vinculacionExt))
            	return;
            if(!eliminarContratosEntidad(contrato))
            	return;
            operarEliminacionEntidad();
        } 
        else if ((tieneVinculaciones && !tieneContratos) && flgConfirmado==1){
            if(!eliminarVinculacionesEntidad(vinculacionExt))
            	return;
            operarEliminacionEntidad();
        } 
        else if ((!tieneVinculaciones && tieneContratos) && flgConfirmado==1){
            if(!eliminarContratosEntidad(contrato))
            	return;
            operarEliminacionEntidad();
        }    
    
    }    

    public void operarEliminacionEntidad() {
        try {
        	this.entidadN.setFlgActivo((short) 0);
        	this.entidadN.setCodEstadoEntidad(BigDecimal.valueOf(TipoParametro.ENTIDAD_INACTIVA.getValue()));
        	this.entidadN.setCodSubestadoEntidad(1490);
        	this.entidadN.setAudFechaActualizacion(new Date());
        	this.entidadN.setAudCodRol(this.getUsuarioSesion().getAudCodRol());
        	this.entidadN.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
        	this.entidadN.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
        	Entidad ent = ComunicacionServiciosEnt.setEntidade(this.entidadN);
        	if(ent.isError()) {
        		this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error al eliminar la entidad, error: " + ent.getMensajeTecnico());
        		return;
        	}
        	//guardarEntidad(true);
            String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ELIMINADA, getLocale());
            mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, msg,TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR, getLocale()), "window.location.assign('gestionarEntidad.xhtml')");
        } catch (Exception ex) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "ERROR", ex.getLocalizedMessage());
            Logger.getLogger(ModificacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método valida si una entidad tiene servidores públicos activos
     * asociados y retorna true o false según el resultado de la validación
     *
     * @param vinculacionExt
     * @return boolean
     */
    public boolean validarVinculacionesEntidad(VinculacionExt filtroBusqueda) {
        vinculacionesConsultar = ComunicacionServiciosVin.getMostrarVinculaciones(filtroBusqueda);
        if (vinculacionesConsultar.isEmpty() || vinculacionesConsultar == null) {
            return false;
        } else {
            return true;
        }
    }

    
    /**
     * Este método permite realizar un borrado lógico de las vinculaciones asociadas a una entidad
     *
     * @param VinculacionExt
     * @return
     */
    
    public boolean eliminarVinculacionesEntidad(VinculacionExt filtroBusqueda) {
    	boolean lbisErroreliminando=false;
    	vinculacionesEliminar = ComunicacionServiciosVin.getMostrarVinculaciones(filtroBusqueda);
        for (VinculacionExt vinc : vinculacionesEliminar) {
        	vinc.setFlgActivo((short) 0);
            vinc.setFechaFinalizacion(new Date());
            vinc.setCodCausalDesvinculacion(BigDecimal.valueOf(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue()));
            vinc.setAudFechaActualizacion(DateUtils.getFechaSistema());
            vinc.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
            vinc.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
            vinc.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
            eliminarVinc = ComunicacionServiciosVin.setVinculacion(vinc);
            if(eliminarVinc){
            	lbisErroreliminando = true;
            }
        }
        if(!lbisErroreliminando){
            String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ERROR_ELIMINANDO_VINCULACIONES, getLocale());
            mostrarMensaje(FacesMessage.SEVERITY_INFO, headerMsg, msg); 
            return false;
        }
        return true;
    }

    /**
     * Este método valida si una entidad tiene contratos activos asociados y
     * retorna true o false según el resultado de la validación
     */
     public boolean validarContratosEntidad(Contrato contrato) {
     contratosEntidad = new ArrayList<>();    
     contratosEntidad= ComunicacionServiciosCO.getContratoFiltro(contrato);
     if (contratosEntidad.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


    public boolean eliminarContratosEntidad(Contrato contrato){
    	String strErrorEliminarContrato="";
    	contratosEliminar = new ArrayList<>();
    	contratoEliminado = new Contrato();
    	contratosEliminar = ComunicacionServiciosCO.getContratoFiltro(contrato);
    	for(ContratoExt con: contratosEliminar){
    		con.setFlgActivo((short) 0);
    		contratoEliminado = ComunicacionServiciosCO.setContrato(con);
    		if (contratoEliminado.isError()) {
    			strErrorEliminarContrato = strErrorEliminarContrato + contratoEliminado.toString();
    		}
        }
    	if(!"".equals(strErrorEliminarContrato)){
            String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_ERROR_ELIMINANDO_CONTRATOS, getLocale());
            mostrarMensaje(FacesMessage.SEVERITY_INFO, headerMsg, msg);    		
    		return false;
    	}
    	return true;
    }


    public void btConfirmarEliminarEntidadListener() {
        flgConfirmado=1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('confirmDlg2').show();");
    }
    
    private boolean validarPermisoRolEliminarEntidad(){
    	try {
			if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)){
				return true;
			}else{
				return false;
			}
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
    	return false;
    }

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

}
