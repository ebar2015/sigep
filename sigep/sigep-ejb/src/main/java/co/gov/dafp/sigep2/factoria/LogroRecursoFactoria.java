package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.entity.jpa.seguridad.LogroRecurso;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class LogroRecursoFactoria extends AbstractFactory<LogroRecurso>{
	private static final long serialVersionUID = 8633963329914798217L;
	transient Logger logger = Logger.getInstance(LogroRecursoFactoria.class);
	
	public LogroRecursoFactoria(){
		super(LogroRecurso.class);
	}
	
	
	public LogroRecurso obtenerLogroRecursoPorCodPersona(long codPersona) {
		try {
			String query = "select * from Logro_Recurso da where da.cod_persona =:cod_persona";
			return (LogroRecurso) createNativeQuery(query, LogroRecurso.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - LogroRecurso obtenerDatoAdicionalPorCodPersona(Long codPersona)", e);
			return null;
		}
	}
	
}