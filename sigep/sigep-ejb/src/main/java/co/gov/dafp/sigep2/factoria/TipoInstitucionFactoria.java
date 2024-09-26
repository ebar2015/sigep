package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoInstitucionDTO;
import co.gov.dafp.sigep2.view.TipoInstitucion;

@Stateless
public class TipoInstitucionFactoria extends AbstractFactory<TipoInstitucion> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TipoInstitucionFactoria() {
		super(TipoInstitucion.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoInstitucionDTO> obtenerTipoInstitucionEducativa() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_INSTITUCION_EDUCATIVA_SQL);
			return (List<TipoInstitucionDTO>)createNativeQuery(query, TipoInstitucion.TIPO_INSTITUCION_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}	
	}
	
	public TipoInstitucion convertirTipoInstitucionDTO(TipoInstitucionDTO tipoInstitucionDTO) {
		if (tipoInstitucionDTO != null) {
			return find(tipoInstitucionDTO.getId());
		} else {
			return null;
		}
	}
}