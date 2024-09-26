package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.view.AccionAuditoriaDTO;
import co.gov.dafp.sigep2.view.AccionAuditoria;

@Stateless
public class AccionAuditoriaFactoria extends AbstractFactory<AccionAuditoria> {

	private static final long serialVersionUID = -6350301882931227531L;

	public AccionAuditoriaFactoria() {
		super(AccionAuditoria.class);
	}

	@SuppressWarnings("unchecked")
	public List<AccionAuditoriaDTO> findAccionAuditoria() {
		try {
			String query = SQLNames.getSQL(SQLNames.ACCION_AUDITORIA_SQL);
			return (List<AccionAuditoriaDTO>)createNativeQuery(query, AccionAuditoria.ACCION_AUDITORIA_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
