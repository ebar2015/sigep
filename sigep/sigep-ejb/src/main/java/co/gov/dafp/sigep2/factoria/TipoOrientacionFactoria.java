package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoOrientacionDTO;
import co.gov.dafp.sigep2.view.TipoOrientacion;

@Stateless
public class TipoOrientacionFactoria extends AbstractFactory<TipoOrientacion> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TipoOrientacionFactoria() {
		super(TipoOrientacion.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoOrientacionDTO> buscarTipoOrientacion() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_ORIENTACION_SQL);
			return (List<TipoOrientacionDTO>)createNativeQuery(query, TipoOrientacion.TIPO_ORIENTACION_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}
}
