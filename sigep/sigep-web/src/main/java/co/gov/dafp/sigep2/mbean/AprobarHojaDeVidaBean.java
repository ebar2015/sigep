package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.EducacionFormalDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
public class AprobarHojaDeVidaBean extends BaseBean implements Serializable{

	private static final long serialVersionUID = 341840921609697942L;
	
	private long codPersona;
	private PersonaDTO persona;
	private boolean disableButton=true;
	private boolean entidadSeleccionada=false;
	
	private List<EducacionFormalDTO> educacionFormalList;

	public boolean isDisableButton() {
		return disableButton;
	}

	public void setDisableButton(boolean disable) {
		this.disableButton = disable;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	
	public boolean isEntidadSeleccionada() {
		return entidadSeleccionada;
	}
	
	public void setEntidadSeleccionada(boolean entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}
	
	public long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		throw new NotSupportedException();
	}

	@PostConstruct
	public void init() {
		
		persona = new PersonaDTO();
		
		if(getEntidadUsuario()!=null){
			entidadSeleccionada=true;
		}else {
			addMessage("El usuario no tiene entidad asocidad. No puede realizar ninguna desasociaci�n");
		}
	}

	@Override
	public String persist() throws NotSupportedException {
		boolean desasociado=false;
		if(persona!=null){
			try {
				desasociado=IngresoSistemaDelegate.desasociarUsuarioEntidad(persona.getId(),getEntidadUsuario().getId(),persona.getTipoIdentificacionId().getId(), persona.getNumeroIdentificacion(), getUsuarioSesion());
				if(desasociado){
					addMessage("Usuario desasociado exitosamente de la entidad");
					persona=new PersonaDTO();
					this.setDisableButton(true);
				}else{
					addMessage("Error, no se puso desasociar el usuario a la entidad");
					this.setDisableButton(true);
				}
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
				addMessage("Error, no se puso desasociar el usuario a la entidad");
				this.setDisableButton(true);
			}
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
				System.out.println("retrieve.... cod_persona="+getCodPersona());
				if(getCodPersona() != 0L) {
					try {
						persona=HojaDeVidaDelegate.findPersonaId(getCodPersona());
						if(persona!=null) {
							//Buscar personaDTO para mostrar datos basicos
							TipoDocumentoDTO tipoDocumento = AdministracionDelegate.findTipoDocumentoId(persona.getCodTipoIdentificacion());
							persona.setTipoIdentificacionId(tipoDocumento);
							
							//Buscar Educacion Formal
							//educacionFormalList=HojaDeVidaDelegate.findEducacion(getCodPersona());
							System.out.println("Educacion encontrada size="+educacionFormalList.size());
						}
					} catch (SIGEP2SistemaException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_URL_INVALID);
			return;
		}
	}

	@Override
	public String update() throws NotSupportedException {
		PersonaDTO personaDTO=null;
		try {
			personaDTO=IngresoSistemaDelegate.findByTipoDocumentoAndNumeroIdentificacion(persona.getTipoIdentificacionId().getId(), persona.getNumeroIdentificacion(),getEntidadUsuario().getId());
		} catch (SIGEP2SistemaException e) {
			personaDTO=null;
		}
		
		if(personaDTO!=null){
			personaDTO.setTipoIdentificacionId(persona.getTipoIdentificacionId());
			persona = personaDTO;
			this.setDisableButton(false);
		}
		else{
		 addMessage("Registro no encontrado");
		 this.setDisableButton(true);
		}
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}
	
	private void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public List<EducacionFormalDTO> getEducacionFormalList() {
		return educacionFormalList;
	}

	public void setEducacionFormalList(List<EducacionFormalDTO> educacionFormalList) {
		this.educacionFormalList = educacionFormalList;
	}
}