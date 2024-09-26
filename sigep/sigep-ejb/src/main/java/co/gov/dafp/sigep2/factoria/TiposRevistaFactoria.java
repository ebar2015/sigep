package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import co.gov.dafp.sigep2.entity.view.TiposRevistaDTO;
import co.gov.dafp.sigep2.view.TiposRevista;

@Stateless
public class TiposRevistaFactoria extends AbstractFactory<TiposRevista> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TiposRevistaFactoria() {
		super(TiposRevista.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TiposRevistaDTO> obtenerParametricasTiposRevista() {
		try {
			String query = "select * from V_TIPOS_REVISTA";
			return (List<TiposRevistaDTO>)createNativeQuery(query, TiposRevista.TIPOS_REVISTA_MAPPING).getResultList();
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	
}
