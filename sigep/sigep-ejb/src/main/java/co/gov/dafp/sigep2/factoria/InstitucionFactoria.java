package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.InstitucionEducativa;
import co.gov.dafp.sigep2.entity.seguridad.InstitucionEducativaDTO;

@Stateless
public class InstitucionFactoria extends AbstractFactory<InstitucionEducativa> {
	private static final long serialVersionUID = -1218090837550198381L;

	public InstitucionFactoria() {
		super(InstitucionEducativa.class);
	}

	@SuppressWarnings("unchecked")
	public List<InstitucionEducativaDTO> obtenerInstitucionEducativa() {
		try {
			String query = SQLNames.getSQL(SQLNames.INSTITUCION_EDUCATIVA_SQL);
			return (List<InstitucionEducativaDTO>)createNativeQuery(query, InstitucionEducativa.INSTITUCION_EDUCATIVA_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}
	
	public InstitucionEducativa convertirInstitucionEducativaDTO(InstitucionEducativaDTO institucionEducativaDTO) {
		if (institucionEducativaDTO != null) {
			return find(institucionEducativaDTO.getId());
		} else {
			return null;
		}
	}
	
}
