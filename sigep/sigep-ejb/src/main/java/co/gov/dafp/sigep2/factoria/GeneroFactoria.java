package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.GeneroDTO;
import co.gov.dafp.sigep2.view.Genero;

@Stateless
public class GeneroFactoria extends AbstractFactory<Genero> {
	private static final long serialVersionUID = -1218090837550198381L;

	public GeneroFactoria() {
		super(Genero.class);
	}

	@SuppressWarnings("unchecked")
	public List<GeneroDTO> findGenero() {
		try {
			String query = SQLNames.getSQL(SQLNames.GENERO_SQL);
			return (List<GeneroDTO>)createNativeQuery(query, Genero.GENERO_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}
}
