package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.NivelEducativoDTO;
import co.gov.dafp.sigep2.view.NivelEducativo;

@Stateless
public class NivelEducativoFactoria extends AbstractFactory<NivelEducativo> {
	private static final long serialVersionUID = -1218090837550198381L;

	public NivelEducativoFactoria() {
		super(NivelEducativo.class);
	}

	@SuppressWarnings("unchecked")
	public List<NivelEducativoDTO> findNivelEducativo() {
		try {
			String query = SQLNames.getSQL(SQLNames.NIVEL_EDUCATIVO_SQL);
			return (List<NivelEducativoDTO>)createNativeQuery(query, NivelEducativo.NIVEL_EDUCATIVO_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}
	
	public NivelEducativo convertirNivelEducativoDTO(NivelEducativoDTO nivelEducativoDTO) {
		if (nivelEducativoDTO != null) {
			return find(nivelEducativoDTO.getId());
		} else {
			return null;
		}
	}
}
