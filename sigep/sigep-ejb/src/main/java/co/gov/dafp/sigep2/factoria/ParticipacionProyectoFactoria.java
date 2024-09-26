package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.entity.jpa.seguridad.ParticipacionProyecto;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class ParticipacionProyectoFactoria extends AbstractFactory<ParticipacionProyecto>{
	private static final long serialVersionUID = 8633963329914798217L;
	transient Logger logger = Logger.getInstance(ParticipacionProyectoFactoria.class);
	
	public ParticipacionProyectoFactoria(){
		super(ParticipacionProyecto.class);
	}
	
	
	public ParticipacionProyecto obtenerParticipacionProyectoPorPersona(long codPersona) {
		try {
			String query = "select * from PARTICIPACION_PROYECTO da where da.cod_persona =:cod_persona";
			return (ParticipacionProyecto) createNativeQuery(query, ParticipacionProyecto.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - ParticipacionProyecto obtenerParticipacionProyectoPorPersona(Long codPersona)", e);
			return null;
		}

	}	
	
	
}