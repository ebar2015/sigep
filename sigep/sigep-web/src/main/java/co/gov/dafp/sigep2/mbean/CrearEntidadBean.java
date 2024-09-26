package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;

@Named
@ViewScoped
public class CrearEntidadBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 3695472896249884567L;
	
	private EntidadDTO entidad;
	
	public EntidadDTO getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadDTO entidad) {
		this.entidad = entidad;
	}
	
	@PostConstruct
	public void init() {
		entidad = new EntidadDTO();		
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
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
	}
}