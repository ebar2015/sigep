package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.EstadoCivilDTO;
import co.gov.dafp.sigep2.view.EstadoCivil;

@Stateless
public class EstadoCivilFactoria extends AbstractFactory<EstadoCivil> {
	private static final long serialVersionUID = -1218090837550198381L;

	public EstadoCivilFactoria() {
		super(EstadoCivil.class);
	}

	@SuppressWarnings("unchecked")
	public List<EstadoCivilDTO> findEstadoCivil() {
		try {
			String query = SQLNames.getSQL(SQLNames.ESTADO_CIVIL_SQL);
			return (List<EstadoCivilDTO>)createNativeQuery(query, EstadoCivil.ESTADO_CIVIL_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public EstadoCivil convertirEstadoCivilDTO(EstadoCivilDTO estadoCivilDTO) {
		if (estadoCivilDTO != null) {
			return new EstadoCivil(estadoCivilDTO.getId(), estadoCivilDTO.getSigla(), estadoCivilDTO.getDescripcion());
		} else {
			return null;
		}
		
	}
}
