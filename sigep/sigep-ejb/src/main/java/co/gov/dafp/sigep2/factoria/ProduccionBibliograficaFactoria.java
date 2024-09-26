package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import co.gov.dafp.sigep2.entity.view.ProduccionBibliograficaDTO;
import co.gov.dafp.sigep2.view.ProduccionBibliografica;

@Stateless
public class ProduccionBibliograficaFactoria extends AbstractFactory<ProduccionBibliografica> {
	private static final long serialVersionUID = -1218090837550198381L;

	public ProduccionBibliograficaFactoria() {
		super(ProduccionBibliografica.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProduccionBibliograficaDTO> obtenerParametricasProduccionBibliografica() {
		try {
			String query = "select * from V_PRODUCCION_BIBLIOGRAFICA";
			return (List<ProduccionBibliograficaDTO>)createNativeQuery(query, ProduccionBibliografica.PRODUCCION_BIBLIOGRAFICA_MAPPING).getResultList();
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	
}
