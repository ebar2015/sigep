package co.gov.dafp.sigep2.factoria;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.EvaluacionDesempeno;
import co.gov.dafp.sigep2.entity.seguridad.EvaluacionDesempenoDTO;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class EvaluacionDesempenoFactoria extends AbstractFactory<EvaluacionDesempeno>{
	private static final long serialVersionUID = 8633963329914798217L;
	transient Logger logger = Logger.getInstance(EvaluacionDesempenoFactoria.class);
	
	public EvaluacionDesempenoFactoria(){
		super(EvaluacionDesempeno.class);
	}
	
	public EvaluacionDesempenoDTO consultarEvaluacionDesempeno(long idPersona, long idEntidad) {
		try {			
			String query = SQLNames.getSQL(SQLNames.CONSULTAR_EVALUACION_DESEMPENO);
			System.out.println("consultarEvaluacionDesempeno SQL:" + query);
			return (EvaluacionDesempenoDTO)createNativeQuery(query, EvaluacionDesempeno.EVALUACION_DESEMPENO_MAPPING)
					.setParameter("cod_persona", idPersona)
					.setParameter("cod_entidad", idEntidad)
					.setMaxResults(1).getSingleResult();
		}
		catch (NoResultException e){
			logger.log().debug("No se encontraron Datos", e);
			return null;
		}
		catch(Exception ex){
			logger.log().error("se encontro un error", ex);
			return null;
		}
	}

	public EvaluacionDesempeno obtenerPublicacionPorCodPersonaPeriodo(long codPersona, long codEntidad, Date PeriodoInicio, Date PeriodoFin) {
		try {
			String query = "select * from EVALUACION_DESEMPENO ed where ed.cod_persona =:cod_persona";
			return (EvaluacionDesempeno) createNativeQuery(query, EvaluacionDesempeno.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - EvaluacionDesempeño obtenerPublicacionPorCodPersonaPeriodo(Long codPersona)", e);
			return null;
		}
	}	
	
	public EvaluacionDesempenoDTO consultarEvaluacionDesempeno(long idPersona) {
		try {
			return (EvaluacionDesempenoDTO)find(idPersona).getDTO();
		} 
		catch (NoResultException e){
			return null;
		}
	}
	
}