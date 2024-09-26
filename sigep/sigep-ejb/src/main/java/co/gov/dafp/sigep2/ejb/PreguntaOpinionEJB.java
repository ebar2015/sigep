package co.gov.dafp.sigep2.ejb;


import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.seguridad.PreguntaOpinion;
import co.gov.dafp.sigep2.entity.seguridad.PreguntaOpinionDTO;
import co.gov.dafp.sigep2.factoria.PreguntaOpinionFactoria;
import co.gov.dafp.sigep2.interfaces.IPreguntaOpinionRemote;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class PreguntaOpinionEJB implements IPreguntaOpinionRemote {
	private static final long serialVersionUID = -5847831892849124578L;

	transient Logger logger = Logger.getInstance(PreguntaOpinionEJB.class);
	
	@EJB
	private PreguntaOpinionFactoria preguntaOpinionFactoria;
	
	private PreguntaOpinion pregunta;
	
	public void crearPregunta(PreguntaOpinionDTO preguntaDTO) throws SIGEP2SistemaException{
		if(pregunta == null){
			pregunta = new PreguntaOpinion();
			pregunta.setPregunta(preguntaDTO.getPregunta());
			pregunta.setFechaInicio(preguntaDTO.getFechaInicio());
			pregunta.setFechaFin(preguntaDTO.getFechaFin());
			pregunta.setAudFechaActualizacion(DateUtils.getFechaSistema());
			pregunta.setAudCodRol(1l);
			pregunta.setAudCodUsuario(1l);
			//pregunta.setAudAccion("ok");
			
			try {
				preguntaOpinionFactoria.persist(pregunta, null);
			} catch (SIGEP2SistemaException e) {
				logger.log().error("EJB - crearPregunta(PreguntaDTO preguntaDTO)", e);
				throw new SIGEP2SistemaException(e);
			}
		}
	}
}


	

	
	