package co.gov.dafp.sigep2.factoria;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Cargo;
import co.gov.dafp.sigep2.entity.seguridad.CargoDTO;

@Stateless
public class CargoFactoria extends AbstractFactory<Cargo> {
	private static final long serialVersionUID = -3279177227435790359L;

	public CargoFactoria() {
		super(Cargo.class);
	}

	@SuppressWarnings("unchecked")
	public List<CargoDTO> buscarCargoPorEntidad() {
		try {
			String query = SQLNames.getSQL(SQLNames.CONSULTAR_CARGOS_SQL);
			return (List<CargoDTO>) createNativeQuery(query, Cargo.CARGO_MAPPING).getResultList();
		} catch (NoResultException e) {
			logger.error("error en cargo Factoria", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CargoDTO> buscarCargoPorEntidad(Long entidad) {
		try {
			String query = SQLNames.getSQL(SQLNames.CONSULTAR_CARGOS_SQL)
					+ SQLNames.getSQL(SQLNames.CONSULTAR_CARGOS_ENTIDAD_SQL);
			return (List<CargoDTO>) createNativeQuery(query, Cargo.CARGO_NO_ENTIDAD_MAPPING)
					.setParameter("cod_entidad", entidad).getResultList();
		} catch (NoResultException e) {
			logger.error("error en cargo Factoria", e);
		}
		return new LinkedList<>();
	}

	public Cargo convertirCargoEntidad(CargoDTO cargodDTO) {
		if (cargodDTO != null) {
			return find(cargodDTO.getId());
		} else {
			return null;
		}
	}
}