package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.entity.jpa.seguridad.ParticipacionInstitucion;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class ParticipacionInstitucionFactoria extends AbstractFactory<ParticipacionInstitucion>{
	private static final long serialVersionUID = 8633963329914798217L;
	transient Logger logger = Logger.getInstance(ParticipacionInstitucionFactoria.class);
	
	public ParticipacionInstitucionFactoria(){
		super(ParticipacionInstitucion.class);
	}
	
	public ParticipacionInstitucion obtenerParticipacionInstitucionPorCodPersona(long codPersona) {
		try {
			String query = "select * from PARTICIPACION_INSTITUCION da where da.cod_persona =:cod_persona";
			return (ParticipacionInstitucion) createNativeQuery(query, ParticipacionInstitucion.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - ParticipacionInstitucion obtenerParticipacionInstitucionPorCodPersona(Long codPersona)", e);
			return null;
		}

	}	
	
}