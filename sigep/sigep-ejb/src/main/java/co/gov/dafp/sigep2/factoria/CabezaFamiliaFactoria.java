package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.view.CabezaFamiliaDTO;
import co.gov.dafp.sigep2.view.CabezaFamilia;

@Stateless
public class CabezaFamiliaFactoria extends AbstractFactory<CabezaFamilia> {
	private static final long serialVersionUID = -1218090837550198381L;

	public CabezaFamiliaFactoria() {
		super(CabezaFamilia.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CabezaFamiliaDTO> obtenerParametricasCabezaFamilia() {
		try {
			String query = "select * from V_CABEZA_FAMILIA";
			return (List<CabezaFamiliaDTO>)createNativeQuery(query, CabezaFamilia.CABEZA_FAMILIA_MAPPING).getResultList();
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	public CabezaFamilia convertirCabezaFamiliaDTO(CabezaFamiliaDTO cabezaFamiliaDTO) {
		if (cabezaFamiliaDTO != null) {
			return new CabezaFamilia(cabezaFamiliaDTO.getId(), cabezaFamiliaDTO.getSigla(), cabezaFamiliaDTO.getDescripcion());
		} else {
			return null;
		}		
	}	
}
