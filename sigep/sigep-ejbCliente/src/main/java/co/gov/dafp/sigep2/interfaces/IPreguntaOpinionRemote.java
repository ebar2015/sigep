package co.gov.dafp.sigep2.interfaces;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.PreguntaOpinionDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IPreguntaOpinionRemote extends IServiceRemote{
	public void crearPregunta(PreguntaOpinionDTO pregunta) throws SIGEP2SistemaException;
}