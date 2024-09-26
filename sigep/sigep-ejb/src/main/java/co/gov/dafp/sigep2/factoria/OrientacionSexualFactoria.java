package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;
import co.gov.dafp.sigep2.view.OrientacionSexual;

@Stateless
public class OrientacionSexualFactoria extends AbstractFactory<OrientacionSexual> {
	private static final long serialVersionUID = -1218090837550198381L;

	public OrientacionSexualFactoria() {
		super(OrientacionSexual.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<OrientacionSexualDTO> obtenerParametricasOrientacionSexual() {
		try {
			String query = "select * from V_ORIENTACION_SEXUAL";
			return (List<OrientacionSexualDTO>)createNativeQuery(query, OrientacionSexual.ORIENTACION_SEXUAL_MAPPING).getResultList();
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
}
