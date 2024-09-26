package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.view.DesplazamientoDTO;
import co.gov.dafp.sigep2.view.Desplazamiento;

@Stateless
public class DesplazamientoFactoria extends AbstractFactory<Desplazamiento> {
	private static final long serialVersionUID = -1218090837550198381L;

	public DesplazamientoFactoria() {
		super(Desplazamiento.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<DesplazamientoDTO> obtenerParametricasDesplazamiento() {
		try {
			String query = "select * from V_Desplazamiento";
			return (List<DesplazamientoDTO>)createNativeQuery(query, Desplazamiento.DESPLAZAMIENTO_MAPPING).getResultList();
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
}
