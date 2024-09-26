package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Reconocimiento;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class ReconocimientoFactoria extends AbstractFactory<Reconocimiento>{
	private static final long serialVersionUID = 8633963329914798217L;
	transient Logger logger = Logger.getInstance(ReconocimientoFactoria.class);
	
	public ReconocimientoFactoria(){
		super(Reconocimiento.class);
	}
	
	
	
}