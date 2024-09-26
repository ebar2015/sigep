package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.DatoAdicional;

@Stateless
public class DatoAdicionalFactoria extends AbstractFactory<DatoAdicional> {
	private static final long serialVersionUID = -1218090837550198381L;

	public DatoAdicionalFactoria() {
		super(DatoAdicional.class);
	}
		
	public DatoAdicional consultarDatoAdicionalByCodPersona(Long codPersona) {
		try {
			String query = SQLNames.getSQL(SQLNames.DATO_ADICIONAL_SQL)
					+ SQLNames.getSQL(SQLNames.DATO_ADICIONAL_BY_PERSONA);
			return (DatoAdicional) createNativeQuery(query, DatoAdicional.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - DatoAdicional consultarDatoAdicionalByCodPersona(Long codPersona)", e);
			return null;
		}
	}
}