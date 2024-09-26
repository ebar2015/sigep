package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoEntidadDTO;
import co.gov.dafp.sigep2.view.TipoEntidad;

@Stateless
public class TipoEntidadFactoria extends AbstractFactory<TipoEntidad> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TipoEntidadFactoria() {
		super(TipoEntidad.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoEntidadDTO> findTipoEntidad() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_ENTIDAD_SQL);
			return (List<TipoEntidadDTO>)createNativeQuery(query, TipoEntidad.TIPO_ENTIDAD_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public TipoEntidad converterTipoEntidadDTO(TipoEntidadDTO tipoEntidadDTO) {
		if(tipoEntidadDTO != null) {
			return new TipoEntidad(tipoEntidadDTO.getId(),tipoEntidadDTO.getSigla(), tipoEntidadDTO.getDescripcion());
		}else {
			return null;
		}
	}
}
