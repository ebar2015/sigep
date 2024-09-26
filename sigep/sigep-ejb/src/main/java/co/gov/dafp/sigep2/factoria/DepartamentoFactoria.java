package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Departamento;

@Stateless
public class DepartamentoFactoria extends AbstractFactory<Departamento> {

	private static final long serialVersionUID = -966017217713570315L;

	public DepartamentoFactoria() {
		super(Departamento.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<DepartamentoDTO> findDepartamento() {
		try {
			String query = SQLNames.getSQL(SQLNames.DEPARTAMENTO_SQL);
			return (List<DepartamentoDTO>)createNativeQuery(query, Departamento.DEPARTAMENTO_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Departamento convertirDepartamentoDTO(DepartamentoDTO departamentoDTO) {
		if (departamentoDTO != null) {
			return find(departamentoDTO.getId());
		} else {
			return null;
		}
	}
}