package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.view.TipoAsociacion;

@Stateless
public class TipoAsociacionFactoria extends AbstractFactory<TipoAsociacion> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TipoAsociacionFactoria() {
		super(TipoAsociacion.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoAsociacionDTO> findTipoAsociacion() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_ASOCIACION_SQL);
			return (List<TipoAsociacionDTO>)createNativeQuery(query, TipoAsociacion.TIPO_ASOCIACION_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
