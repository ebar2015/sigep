package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.JornadaLaboralDTO;
import co.gov.dafp.sigep2.view.JornadaLaboral;

@Stateless
public class JornadaLaboralFactoria extends AbstractFactory<JornadaLaboral> {
	private static final long serialVersionUID = -9188170076249531734L;

	public JornadaLaboralFactoria() {
		super(JornadaLaboral.class);
	}

	@SuppressWarnings("unchecked")
	public List<JornadaLaboralDTO> buscarJornadaLaboral() {
		try {
			String query = SQLNames.getSQL(SQLNames.CONSULTAR_JORNADA_LABORAL_SQL);
			return (List<JornadaLaboralDTO>)createNativeQuery(query, JornadaLaboral.JORNADA_LABORAL_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public JornadaLaboral convertirJornadaLaboralDTO(JornadaLaboralDTO jornadaLaboralDTO) {
		if(jornadaLaboralDTO != null) {
			return new JornadaLaboral(jornadaLaboralDTO.getId(), jornadaLaboralDTO.getSigla(), jornadaLaboralDTO.getDescripcion());
		}else {
			return null;
		}
	}

}
