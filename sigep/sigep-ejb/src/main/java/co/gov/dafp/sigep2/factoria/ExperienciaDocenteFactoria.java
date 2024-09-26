package co.gov.dafp.sigep2.factoria;


import javax.ejb.Stateless;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.ExperienciaDocente;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaDocenteDTO;

import java.math.BigDecimal;
import java.util.List;

@Stateless
public class ExperienciaDocenteFactoria extends AbstractFactory<ExperienciaDocente> {
	private static final long serialVersionUID = -6858325799546649078L;
	
	public ExperienciaDocenteFactoria() {
		super(ExperienciaDocente.class);
	}
	
	public ExperienciaDocenteDTO obtenerExperienciaDocenteByCodExpDocente(long codExpDocente) {
		try {
			
			String query = SQLNames.getSQL(SQLNames.CONSULTAR_EXPERIENCIA_DOCENTE_SQL)
						  + SQLNames.getSQL(SQLNames.EXPERIENCIA_DOCENTE_BY_CODIGOEXPDOCENTE);
			
			return (ExperienciaDocenteDTO) createNativeQuery(query, ExperienciaDocente.EXPERIENCIA_DOCENTE_MAPPING).setParameter("codExpDocente", codExpDocente).getSingleResult();
			
		}catch (Exception e) {
			System.out.println("FACTORIA,obtenerExperienciaDocenteByCodExpDocente(long codExpDocente)" + e);
			return null;
		}	
	}
	
	public ExperienciaDocente findExperienciaDocente(long codPersona) {
		try {
			String query = SQLNames.getSQL(SQLNames.CONSULTAR_EXPERIENCIA_DOCENTE_SQL)
						   + SQLNames.getSQL(SQLNames.EXPERIENCIA_DOCENTE_BY_PERSONA);
			
			return (ExperienciaDocente) createNativeQuery(query, ExperienciaDocente.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
			
			
		} catch (Exception e) {
			System.out.println("FACTORIA, ExperienciaDocente findExperienciaDocente(long codPersona)" + e);
			return null;
		}
	}
	
	public int obtenerFilasExperienciaDocente(Long cod_persona) {
		try {
			String query =  SQLNames.getSQL(SQLNames.EXPERIENCIA_DOCENTE_NUMERO_FILAS_BY_PERSONA);
			return ((BigDecimal) createNativeQuery(query).setParameter("cod_persona", cod_persona).getSingleResult()).intValue();
		} catch (Exception e) {
			System.out.println("FACTORIA, obtenerFilasExperienciaDocente(long codPersona) + problemaaa!" + e);
		}
		
		return 0;
	}
	
	public boolean eliminarExperienciaDocente(long codExperienciaDocente) {
		try {
			ExperienciaDocente ex = this.find(codExperienciaDocente);
			this.remove(ex, null);
			return true;
		}catch (Exception e) {
			logger.log().error("factoria - eliminarExperienciaProfesional(long codExpetienciaProfesional)", e);
		}
		return false;
	}


	@SuppressWarnings("unchecked")
	public List<ExperienciaDocenteDTO> listarExperienciaDocente(int first, int pageSize, long cod_Persona) {
		System.out.println("listarExperienciaDocente(int first, int pageSize, long cod_Persona) Codigo persona: " + cod_Persona);
		try {
			String query = SQLNames.getSQL(SQLNames.LISTAR_EXPERIENCIA_DOCENTE_SQL)
					+ SQLNames.getSQL(SQLNames.OBTENER_NOMBRES_EXPERIENCIA_DOCENTE_SQL)
					+ SQLNames.getSQL(SQLNames.EXPERIENCIA_DOCENTE_BY_PERSONA );
			return (List<ExperienciaDocenteDTO>) createNativeQuery(query, ExperienciaDocente.DATOS_TABLA_EXPERIENCIA_DOCENTE_MAPPING).setParameter("cod_persona", cod_Persona)
									.getResultList();	
			
		} catch (Exception e) {
			System.out.println("Hola mundo FACTORIA listar experiencia Docente, no entre en el TRY y esta es mi razon: " + e);
			return null;
		}
		
	}
}
