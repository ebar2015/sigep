package co.gov.dafp.sigep2.factoria;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.ExperienciaProfesional;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaProfesionalDTO;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class ExperienciaProfesionalFactoria extends AbstractFactory<ExperienciaProfesional> {
	private static final long serialVersionUID = -6858325799546649078L;
	private static final Logger logger = Logger.getInstance(ExperienciaProfesionalFactoria.class);
	
	public ExperienciaProfesionalFactoria() {
		super(ExperienciaProfesional.class);
	}

	public int filasExperienciaProfesionalPorPersona(long codPersona) {
		try {
			String query =  SQLNames.getSQL(SQLNames.CONTAR_EXPERIENCIAS_PROFESIONAL_SQL)
					+ SQLNames.getSQL(SQLNames.CONSULTAR_EXPERIENCIA_PROFESIONAL_BY_PERSONA_SQL);
			return  ((BigDecimal) createNativeQuery(query).setParameter("codPersona", codPersona).getSingleResult()).intValue();
		} catch (Exception e) {
			logger.log().error("factoria - int filasExperienciaProfesionalPorPersona(long codPersona)", e);
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ExperienciaProfesionalDTO> listarExperienciaProfesional(int first, int pageSize, long codPersona) {
		try {
			String query = SQLNames.getSQL(SQLNames.CONSULTAR_EXPERIENCIA_PROFESIONAL_SQL)
					+ SQLNames.getSQL(SQLNames.CONSULTAR_EXPERIENCIA_PROFESIONAL_BY_PERSONA_SQL);
			System.out.println("query"+query);
			System.out.println("codPersona"+codPersona);
			return (List<ExperienciaProfesionalDTO>) createNativeQuery(query, ExperienciaProfesional.TABLA_EXPERIENCIA_PROFESIONAL_MAPPING)
					.setParameter("codPersona", codPersona).getResultList();
		} catch (Exception e) {
			logger.log().error("factoria - List<ExperienciaProfesionalDTO> listarExperienciaProfesional(int first, int pageSize, long codPersona)", e);
			return null;
		}
	}
	
	public boolean eliminarExperienciaProfesional(long codExperienciaProfesional) {
		try {
			ExperienciaProfesional ex = this.find(codExperienciaProfesional);
			this.remove(ex, null);
			return true;
		}catch (Exception e) {
			logger.log().error("factoria - eliminarExperienciaProfesional(long codExpetienciaProfesional)", e);
		}
		return false;
	}
	
	public ExperienciaProfesionalDTO buscarExperienciaProfesional(long codExperienciaProfesional) {
		try {
			String query = "select * from experiencia_profesional where cod_experiencia_profesional = :codExperienciaProfesional";
			ExperienciaProfesionalDTO verExperienciaProfesional = (ExperienciaProfesionalDTO)createNativeQuery(query, ExperienciaProfesional.EXPERIENCIA_PROFESIONAL_MAPPING).setParameter("codExperienciaProfesional", codExperienciaProfesional).getResultList();
			System.out.println(verExperienciaProfesional);
			return verExperienciaProfesional;
		} catch (Exception e) {
			logger.log().error("factoria - ExperienciaProfesionalDTO buscarExperienciaProfesional(long codExperienciaProfesional)" + codExperienciaProfesional, e);
			return null;
		}
	}
	
}
