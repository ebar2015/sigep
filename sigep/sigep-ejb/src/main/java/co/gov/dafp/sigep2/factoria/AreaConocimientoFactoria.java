package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.AreaConocimientoDTO;
import co.gov.dafp.sigep2.view.AreaConocimiento;

@Stateless
public class AreaConocimientoFactoria extends AbstractFactory<AreaConocimiento> {
	private static final long serialVersionUID = -1218090837550198381L;

	public AreaConocimientoFactoria() {
		super(AreaConocimiento.class);
	}

	@SuppressWarnings("unchecked")
	public List<AreaConocimientoDTO> findAreaConocimiento() {
		try {
			String query = SQLNames.getSQL(SQLNames.AREA_CONOCIMIENTO_SQL);
			return (List<AreaConocimientoDTO>)createNativeQuery(query, AreaConocimiento.AREA_CONOCIMIENTO_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}
	
	public AreaConocimiento convertirAreaConocimientoDTO(AreaConocimientoDTO areaConocimientoDTO) {
		if (areaConocimientoDTO != null) {
			return find(areaConocimientoDTO.getId());
		} else {
			return null;
		}
	}
	
}
