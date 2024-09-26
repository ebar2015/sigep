package co.gov.dafp.sigep2.factoria;


import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.ClaseLibretaMilitarDTO;
import co.gov.dafp.sigep2.view.ClaseLibretaMilitar;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
public class ClaseLibretaMilitarFactoria extends AbstractFactory<ClaseLibretaMilitar> {
	private static final long serialVersionUID = -1218090837550198381L;

	public ClaseLibretaMilitarFactoria() {
		super(ClaseLibretaMilitar.class);
	}

	@SuppressWarnings("unchecked")
	public List<ClaseLibretaMilitarDTO> findClaseLibretaMilitar() {
		try {
			String query = SQLNames.getSQL(SQLNames.CLASE_LIBRETA_MILITAR_SQL);
			return (List<ClaseLibretaMilitarDTO>)createNativeQuery(query, ClaseLibretaMilitar.CLASE_LIBRETA_MILITAR_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public ClaseLibretaMilitar convertirClaseLibretaMilitarDTO(ClaseLibretaMilitarDTO claseLibretaMilitarDTO) {
		if(claseLibretaMilitarDTO != null) {
			return new ClaseLibretaMilitar(claseLibretaMilitarDTO.getId(), claseLibretaMilitarDTO.getSigla(), claseLibretaMilitarDTO.getDescripcion());
		}else {
			return null;
		}
	}
}
