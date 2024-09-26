package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.seguridad.PreguntaOpinion;

@Stateless
public class PreguntaOpinionFactoria extends AbstractFactory<PreguntaOpinion>{
	private static final long serialVersionUID = 8633963329914798217L;
	
	public PreguntaOpinionFactoria() {
		super(PreguntaOpinion.class);
	}
	
	
}