package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.NacionalidadPerfil;

@Stateless
public class NacionalidadPerfilFactoria extends AbstractFactory<NacionalidadPerfil> {
	private static final long serialVersionUID = -1218090837550198381L;

	public NacionalidadPerfilFactoria() {
		super(NacionalidadPerfil.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<NacionalidadPerfil> findNacionalidadByCodPersonaId(Long codPersona) {
		try {
			String query = SQLNames.getSQL(SQLNames.NACIONALIDAD_PERFIL_SQL)
					+ SQLNames.getSQL(SQLNames.NACIONALIDAD_PERFIL_BY_PERSONA);
			return (List<NacionalidadPerfil>)createNativeQuery(query, NacionalidadPerfil.class)
										.setParameter("cod_persona", codPersona).getResultList();
		} catch (NoResultException e) {
			logger.debug("factoria - List<NacionalidadPerfil> findNacionalidadByCodPersonaId(Long codPersona)", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public NacionalidadPerfil buscarNacionalidadPorPaisYPersona(Long codPersona, Long codPais) {
		try {
			String query = SQLNames.getSQL(SQLNames.NACIONALIDAD_PERFIL_SQL)
								+ SQLNames.getSQL(SQLNames.NACIONALIDAD_PERFIL_BY_PERSONA_AND_PAIS);
			return (NacionalidadPerfil)createNativeQuery(query, NacionalidadPerfil.class)
								.setParameter("cod_persona", codPersona)
								.setParameter("cod_pais", codPais)
								.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - NacionalidadPerfil buscarNacionalidadPorPaisYPersona(Long codPersona, Long codPais)", e);
			return null;
		}
	}
}