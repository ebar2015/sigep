package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.entity.jpa.seguridad.LogroRecurso;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Publicacion;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class PublicacionFactoria extends AbstractFactory<Publicacion>{
	private static final long serialVersionUID = 8633963329914798217L;
	transient Logger logger = Logger.getInstance(PublicacionFactoria.class);
	
	public PublicacionFactoria(){
		super(Publicacion.class);
	}
	
	public Publicacion obtenerPublicacionPorCodPersona(long codPersona) {
		try {
			String query = "select * from PUBLICACION da where da.cod_persona =:cod_persona";
			return (Publicacion) createNativeQuery(query, LogroRecurso.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - LogroRecurso obtenerDatoAdicionalPorCodPersona(Long codPersona)", e);
			return null;
		}

	}	
	
	
	
		
}
	
	
	
