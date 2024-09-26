package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoZonaDTO;
import co.gov.dafp.sigep2.view.TipoZona;

@Stateless
public class TipoZonaFactoria extends AbstractFactory<TipoZona> {

	private static final long serialVersionUID = 8244232190235496559L;

	public TipoZonaFactoria() {
		super(TipoZona.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoZonaDTO> findTipoZona() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_ZONA_SQL);
			return (List<TipoZonaDTO>)createNativeQuery(query, TipoZona.TIPO_ZONA_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public TipoZona convertirTipoZonaDTO(TipoZonaDTO tipoZonaDTO) {
		if (tipoZonaDTO != null) {
			return new TipoZona(tipoZonaDTO.getId(), tipoZonaDTO.getSigla(), tipoZonaDTO.getDescripcion());
		} else {
			return null;
		}
	}
}