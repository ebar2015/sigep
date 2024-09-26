package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.entities.PersonaParentesco;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosBR;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;

@Named
@ViewScoped
@ManagedBean
public class ParientesConyuguesBean extends BaseBean implements Serializable {
	
	private PersonaParentesco persona;
	private PersonaParentesco parienteSeleccionado;
	private List<PersonaParentesco> parientesLista;
	private boolean estadoPanelParientes;
	private boolean sinParientes;
	private boolean estadoTabParientes;
	private boolean formRegisHabilitado;
	private static final long serialVersionUID = 1L;
	
	public boolean isEstadoTabParientes() {
		return estadoTabParientes;
	}

	public void setEstadoTabParientes(boolean estadoTabParientes) {
		this.estadoTabParientes = estadoTabParientes;
	}
	
	public boolean isFormRegisHabilitado() {
		return formRegisHabilitado;
	}

	public void setFormRegisHabilitado(boolean formRegisHabilitado) {
		this.formRegisHabilitado = formRegisHabilitado;
	}

	public boolean isEstadoPanelParientes() {
		ComunicacionServiciosSis.getParametricaIntetos("BR_MSG_ELIMINACION_FALlIDO");
		return estadoPanelParientes;
	}

	public void setEstadoPanelParientes(boolean estadoPanelParientes) {
		this.estadoPanelParientes = estadoPanelParientes;
	}
	
	public boolean isSinParientes() {
		return sinParientes;
	}

	public void setSinParientes(boolean sinParientes) {
		this.sinParientes = sinParientes;
	}
	
	public PersonaParentesco getParienteSeleccionado() {
		return parienteSeleccionado;
	}

	public void setParienteSeleccionado(PersonaParentesco parienteSeleccionado) {
		this.parienteSeleccionado = parienteSeleccionado;
	}
	
	public List<PersonaParentesco> getParientesLista() {
		return parientesLista;
	}

	public void setParientesLista(List<PersonaParentesco> parientesLista) {
		this.parientesLista = parientesLista;
	}

	public PersonaParentesco getPersona() {
		return persona;
	}

	public void setPersona(PersonaParentesco persona) {
		this.persona = persona;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}
	
	@Override
	public String persist() throws NotSupportedException {
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
	
	}

	public ParientesConyuguesBean() {
		System.out.println("En Parientes Andres");
	}
	
	@PostConstruct
	public void init(){
		persona = new PersonaParentesco();
		persona.setPrimerNombre("Andres");
		persona.setPrimerApellido("Jaramillo");
		persona.setSegundoNombre("Felipe");
		persona.setSegundoApellido("Jaramillo");
		persona.setCodTipoIdentificacion(1);
		persona.setNumIdentificacion("1152434406");
		parienteSeleccionado = new PersonaParentesco();
		parientesLista = new ArrayList<PersonaParentesco>();
		parientesLista.add(persona);
		//reemplazar
		sinParientes = false;
		estadoPanelParientes = true;
	}
	
	public void guardarPariente() {
		ComunicacionServiciosBR.setPersonaParentescoPersona(parienteSeleccionado);
	}
	
	public void declararSinParientes() {
		parientesLista = new ArrayList<PersonaParentesco>();
		
	}
	
	public void activarFormularioRegistro() {
		this.formRegisHabilitado = true;
	}
	

}




