package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ViewScoped
public class DesactivarUsuarioMasivoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 3695472896249884567L;

	private boolean entidadSeleccionada=false;
	
	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();		
	}

	@Inject
	private SesionBean sesionBean;
	
	public boolean isEntidadSeleccionada() {
		return entidadSeleccionada;
	}
	
	public void setEntidadSeleccionada(boolean entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}
	
	@PostConstruct
	public void init() {
		if(sesionBean!=null && sesionBean.getEntidadUsuario()!=null){
			entidadSeleccionada=true;
		}
	}

	public String persist() throws NotSupportedException{	
		try{
			IngresoSistemaDelegate.desactivarUsuariosMasivo(sesionBean.getEntidadUsuario().getId());
			addMessage("Proceso exitoso: se han actualizado los datos.");
		}catch (SIGEP2SistemaException e) {
			addMessage("Error: no se pudo desactivamar los usuarios de la entidad.");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			if (this.conversation.isTransient()) {
				this.conversation.begin();
				this.conversation.setTimeout(timeOut);
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_URL_INVALID);
		}
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}
	
	private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}