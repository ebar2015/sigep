package co.gov.dafp.sigep2.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;

import co.gov.dafp.sigep2.entity.seguridad.EvaluacionDesempenoDTO;
import co.gov.dafp.sigep2.factoria.EvaluacionDesempenoFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioEntidadFactoria;
import co.gov.dafp.sigep2.interfaces.IEvaluacionDesempeno;


@Stateless
public class EvaluacionDesempenoEJB implements IEvaluacionDesempeno{
	private static final long serialVersionUID = -3345538355024286776L;
	
	@EJB
	EvaluacionDesempenoFactoria evaluacionDesempenoFactoria;
	@EJB
	UsuarioEntidadFactoria usuarioEntidadFactoria;

	
	public EvaluacionDesempenoDTO obtenerEvaluacionActual(long idPersonaConsultada, long idEntidad, long idUsuarioConsultor) throws MessagingException{
		EvaluacionDesempenoDTO evaluacion = evaluacionDesempenoFactoria.consultarEvaluacionDesempeno(idPersonaConsultada, idEntidad);
		return evaluacion;
	}
}