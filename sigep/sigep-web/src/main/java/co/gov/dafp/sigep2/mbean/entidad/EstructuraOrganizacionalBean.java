package co.gov.dafp.sigep2.mbean.entidad;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

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
public class EstructuraOrganizacionalBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 7154680339675219230L;

	
	private String indexTab;
	private int tabindex;

	
    @Inject
    protected GestionEntidadBean gestionEntidadBean;



	public String getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(String indexTab) {
		this.indexTab = indexTab;
	}

	public EstructuraOrganizacionalBean() {
		super();
		indexTab = "";
	}

	@Override
	@PostConstruct
	public void init() {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		if(!validarPermisoRolEstructuraOrganizacional()){
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
					"window.location.assign('entidad/../../../gestionarEntidad.xhtml?faces-redirect=true')"); 
    		return;
		}
	}
	


	public String persist() {
		return null;
	}

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
	
    private boolean validarPermisoRolEstructuraOrganizacional(){
    	try {
			if(usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, 
                    				   RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.JEFE_TALENTO_HUMANO)){
				return true;
			}else{
				return false;
			}
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
    	return false;
    }

	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
	}	

}