package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.DatoContacto;

@Stateless
public class DatoContactoFactoria extends AbstractFactory<DatoContacto> {
	private static final long serialVersionUID = -1218090837550198381L;

	public DatoContactoFactoria() {
		super(DatoContacto.class);
	}
		
	public DatoContacto consultarDatoContactoByCodPersona(Long codPersona) {
		try {
			String query = SQLNames.getSQL(SQLNames.DATO_CONTACTO_SQL)
					+ SQLNames.getSQL(SQLNames.DATO_CONTACTO_BY_PERSONA);
			return (DatoContacto) createNativeQuery(query, DatoContacto.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - DatoContacto consultarDatoContactoByCodPersona(Long codPersona)", e);
			return null;
		}
	}
}