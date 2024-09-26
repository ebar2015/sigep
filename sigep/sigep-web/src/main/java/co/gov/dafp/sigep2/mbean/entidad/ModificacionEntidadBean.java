/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.mbean.entidad;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;

/**
 *
 * @author Sergio
 */
@Named(value = "modificacionEntidadBean")
@ViewScoped
public class ModificacionEntidadBean extends ConsultaEntidadBean {
	
	public boolean isAdminfun() {
		return adminfun;
	}


	public void setAdminfun(boolean adminfun) {
		this.adminfun = adminfun;
	}

	private boolean adminfun;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1888444689283290570L;

	@Override
	@PostConstruct
	public void init() {
		cerrarSessionFuncion(AMBITO_POLITICAS);

	        try {
	            super.init();
	            setEntidadN(gestionEntidadBean.getEntFiltro());
	            verificarClasificacionOrganica();
	            inicializacionConsulta();
	        	if(!validarPermisoRol()){
	        		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
	    					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
	    					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
	    					"window.location.assign('gestionarEntidad.xhtml?faces-redirect=true')"); 
	        		return;
	        	}
	        	if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_POLITICAS)) {
				    setDisponibleCaracterizacion(true);
				    setTabIndex(0);
	        	}
	        	
	        	  if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.CARACTERIZADOR_ENTIDAD) 
	      				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL) 
	      				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR)
	      				|| usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES)
	      				||usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)){
	              	 setDisponibleCaracterizacion(false);
	              	 setTabCaracterizacion(false);
	              }
	        	
	        	
	        	this.adminfun = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL);
	        } catch (Exception ex) {
	            Logger.getLogger(ModificacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
	        }
	}
	
	
    /**
     * Sobreescribe el método para el botón regresar
     *
     * @return
     */
    @Override
    public String btRegresarAction() {
        gestionEntidadBean.endConversation();
        return "/entidad/gestionarEntidad?faces-redirect=true";
    }

    /**
     * Método que se ejecuta al accionar el botón "guardar" de la pestaña de
     * datos básicos
     */
    @Override
    public void btCrearEntidadListener() {
        try {
        	if(tipoNorma == null) {
    			tipoNorma = new Norma();
    		}
        	
        	this.tipoNorma.setObjetoNorma(this.getObjetoNorma());
        	entidadN.setFlgActivo((short) 1);
            guardarEntidad(true);
            String header = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,getLocale());
            String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_DATOS_BASICOS_MODIFICADOS,getLocale());
            mostrarMensaje(FacesMessage.SEVERITY_INFO, header, msg);
        } catch (SIGEP2SistemaException ex) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "ERROR", ex.getLocalizedMessage());
        } catch (ParseException ex) {
            Logger.getLogger(ModificacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que se ejecuta al accionar el botón "guardar" de la pestaña de
     * caracterización
     */
    @Override
    public void btGuardarCaracterizacionListener() {
        try {
        	entidadN.setFlgActivo((short) 1);
            guardarEntidad(true);
            String header = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_HEADER_MENSAJES,getLocale());
            String msg = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ENTIDAD_DATOS_CARACTERIZACION_MODIFICADOS,getLocale());
            mostrarMensaje(FacesMessage.SEVERITY_INFO, header, msg);
        } catch (SIGEP2SistemaException | ParseException ex) {
            Logger.getLogger(CreacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "ERROR", ex.getLocalizedMessage());
        }
    }
    
    private boolean validarPermisoRol(){
		try {
			/*Se valida si puede cambiar el nombre*/
			if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL,  RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)){
				setLbPermiteModificarNombreEntidad(true);
			}else{
				setLbPermiteModificarNombreEntidad(false);
			}
			
			
			if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL,RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.CARACTERIZADOR_ENTIDAD,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_POLITICAS)){
				
				if(!this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL)
						&& !this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES)
						&& !this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO)
						&& !this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.OPERADOR_TALENTO_HUMANO)){
					setDisponibleDatosBasicos(false);
					setTabIndex(1);
				}
				
				return true;
			}
				
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		/*si no es de los roles permitidos se mira si administrador de entidades privadas y si la entidad es de tipo privada*/
		try {
			if(this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES_PRIVADAS)){
				if(getEntidadN().getCodTipoEntidad()!=null && getEntidadN().getCodTipoEntidad() == TipoParametro.ENTIDAD_PRIVADA.getValue())
					return true;
			}
		} catch (SIGEP2SistemaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }

}