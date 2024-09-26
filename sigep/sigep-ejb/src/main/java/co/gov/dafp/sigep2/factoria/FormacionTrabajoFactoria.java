package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import co.gov.dafp.sigep2.entity.jpa.seguridad.FormacionTrabajo;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class FormacionTrabajoFactoria extends AbstractFactory<FormacionTrabajo>{
	private static final long serialVersionUID = 8633963329914798217L;
	transient Logger logger = Logger.getInstance(FormacionTrabajoFactoria.class);
	
	public FormacionTrabajoFactoria(){
		super(FormacionTrabajo.class);
	}
	
}