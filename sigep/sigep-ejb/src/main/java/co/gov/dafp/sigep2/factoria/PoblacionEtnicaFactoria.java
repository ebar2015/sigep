package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.PoblacionEtnicaDTO;
import co.gov.dafp.sigep2.view.PoblacionEtnica;

@Stateless
public class PoblacionEtnicaFactoria extends AbstractFactory<PoblacionEtnica> {
	private static final long serialVersionUID = -1218090837550198381L;

	public PoblacionEtnicaFactoria() {
		super(PoblacionEtnica.class);
	}

	@SuppressWarnings("unchecked")
	public List<PoblacionEtnicaDTO> findPoblacionEtnica() {
		try {
			String query = SQLNames.getSQL(SQLNames.POBLACION_ETNICA_SQL);
			return (List<PoblacionEtnicaDTO>)createNativeQuery(query, PoblacionEtnica.POBLACION_ETNICA_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public PoblacionEtnica convertirPoblacionEtnicaDTO(PoblacionEtnicaDTO poblacionEtnicaDTO) {
		if(poblacionEtnicaDTO != null) {
			return new PoblacionEtnica(poblacionEtnicaDTO.getId(), poblacionEtnicaDTO.getSigla(), poblacionEtnicaDTO.getDescripcion());
		}else {
			return null;
		}
	}
}
