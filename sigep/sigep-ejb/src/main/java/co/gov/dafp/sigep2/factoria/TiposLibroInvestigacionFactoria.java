package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import co.gov.dafp.sigep2.entity.view.TiposLibroInvestigacionDTO;
import co.gov.dafp.sigep2.view.TiposLibroInvestigacion;

@Stateless
public class TiposLibroInvestigacionFactoria extends AbstractFactory<TiposLibroInvestigacion> {
	private static final long serialVersionUID = -1218090837550198381L;

	public TiposLibroInvestigacionFactoria() {
		super(TiposLibroInvestigacion.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TiposLibroInvestigacionDTO> obtenerParametricasTiposlibroInvestigacion() {
		try {
			String query = "select * from V_TIPOS_LIBRO_INVESTIGACION";
			return (List<TiposLibroInvestigacionDTO>)createNativeQuery(query, TiposLibroInvestigacion.TIPOS_LIBRO_INVESTIGACION_MAPPING).getResultList();
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	
}
