package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Municipio;

@Stateless
public class MunicipioFactoria extends AbstractFactory<Municipio> {

	private static final long serialVersionUID = -966017217713570315L;

	public MunicipioFactoria() {
		super(Municipio.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<MunicipioDTO> findMunicipio() {
		try {
			String query = SQLNames.getSQL(SQLNames.MUNICIPIO_SQL);
			return (List<MunicipioDTO>)createNativeQuery(query, Municipio.MUNICIPIO_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Municipio convertirMunicipioDTO(MunicipioDTO municipioDTO) {
		if (municipioDTO != null) {
			return find(municipioDTO.getId());
		} else {
			return null;
		}
	}
}