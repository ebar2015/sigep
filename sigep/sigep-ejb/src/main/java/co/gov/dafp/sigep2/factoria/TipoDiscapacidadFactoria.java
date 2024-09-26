package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoDiscapacidadDTO;
import co.gov.dafp.sigep2.view.TipoDiscapacidad;

@Stateless
public class TipoDiscapacidadFactoria extends AbstractFactory<TipoDiscapacidad> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TipoDiscapacidadFactoria() {
		super(TipoDiscapacidad.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoDiscapacidadDTO> findTipoDiscapacidad() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_DISCAPACIDAD_SQL);
			return (List<TipoDiscapacidadDTO>)createNativeQuery(query, TipoDiscapacidad.TIPO_DISCAPACIDAD_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}
	
	public TipoDiscapacidad convertirTipoDiscapacidadDTO(TipoDiscapacidadDTO tipoDiscapacidadDTO) {
		if (tipoDiscapacidadDTO != null) {
			return new TipoDiscapacidad(tipoDiscapacidadDTO.getId(), tipoDiscapacidadDTO.getSigla(), tipoDiscapacidadDTO.getDescripcion());
		} else {
			return null;
		}		
	}
}