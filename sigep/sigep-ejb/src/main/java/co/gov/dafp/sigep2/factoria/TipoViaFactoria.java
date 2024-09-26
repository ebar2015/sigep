package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoViaDTO;
import co.gov.dafp.sigep2.view.TipoVia;

@Stateless
public class TipoViaFactoria extends AbstractFactory<TipoVia> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TipoViaFactoria() {
		super(TipoVia.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoViaDTO> buscarTipoVia() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_VIA_SQL);
			return (List<TipoViaDTO>)createNativeQuery(query, TipoVia.TIPO_VIA_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}
}
