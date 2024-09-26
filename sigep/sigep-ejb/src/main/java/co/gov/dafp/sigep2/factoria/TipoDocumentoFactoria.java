package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.view.TipoDocumento;

@Stateless
public class TipoDocumentoFactoria extends AbstractFactory<TipoDocumento> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TipoDocumentoFactoria() {
		super(TipoDocumento.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoDocumentoDTO> findTipoDocumento() {
		try {
			String query = SQLNames.getSQL(SQLNames.TIPO_DOCUMENTO_SQL);
			return (List<TipoDocumentoDTO>)createNativeQuery(query, TipoDocumento.TIPO_DOCUMENTO_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
