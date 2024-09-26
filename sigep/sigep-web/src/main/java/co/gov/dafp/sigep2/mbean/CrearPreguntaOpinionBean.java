package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@SessionScoped
public class CrearPreguntaOpinionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 4406130899850330333L;



	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws NotSupportedException, SIGEP2SistemaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String update() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// TODO Auto-generated method stub
		
	}
}
