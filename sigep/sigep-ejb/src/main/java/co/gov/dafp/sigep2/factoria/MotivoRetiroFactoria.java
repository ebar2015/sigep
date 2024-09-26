package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.MotivoRetiroDTO;
import co.gov.dafp.sigep2.view.MotivoRetiro;

@Stateless
public class MotivoRetiroFactoria extends AbstractFactory<MotivoRetiro> {
	private static final long serialVersionUID = -4057588903957975438L;

	public MotivoRetiroFactoria() {
		super(MotivoRetiro.class);
	}

	@SuppressWarnings("unchecked")
	public List<MotivoRetiroDTO> buscarMotivoRetiro() {
		try {
			String query = SQLNames.getSQL(SQLNames.CONSULTAR_MOTIVO_RETIRO_SQL);
			return (List<MotivoRetiroDTO>) createNativeQuery(query, MotivoRetiro.MOTIVO_RETIRO_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public MotivoRetiro convertirMotivoRetiroDTO(MotivoRetiroDTO motivoRetiroDTO) {
		if(motivoRetiroDTO != null) {
			return new MotivoRetiro(motivoRetiroDTO.getId(), motivoRetiroDTO.getSigla(), motivoRetiroDTO.getDescripcion());
		}else {
			return null;
		}
	}

}

