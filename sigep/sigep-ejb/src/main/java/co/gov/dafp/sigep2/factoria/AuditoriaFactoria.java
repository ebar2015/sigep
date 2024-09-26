package co.gov.dafp.sigep2.factoria;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.AuditoriaConfiguracion;
import co.gov.dafp.sigep2.entity.seguridad.AuditoriaConfiguracionDTO;

@Stateless
public class AuditoriaFactoria extends AbstractFactory<AuditoriaConfiguracion> {

	private static final long serialVersionUID = 3764358271608294534L;

	public AuditoriaFactoria() {
		super(AuditoriaConfiguracion.class);
	}

	@SuppressWarnings("unchecked")
	public List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion() {
		try {
			String query = SQLNames.getSQL(SQLNames.AUDITORIA_CONFIGURACION_SQL);
			return (List<AuditoriaConfiguracionDTO>)createNativeQuery(query, AuditoriaConfiguracion.AUDITORIA_CONFIGURACION_MAPPING).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion(int first, int pageSize) {
		try {
			String query = "SELECT * FROM AUDITORIA_CONFIGURACION ORDER BY NOMBRE_TABLA OFFSET :first ROWS FETCH NEXT :pageSize ROWS ONLY";
			return (List<AuditoriaConfiguracionDTO>)createNativeQuery(query, AuditoriaConfiguracion.AUDITORIA_CONFIGURACION_MAPPING)
					.setParameter("first", first)
					.setParameter("pageSize", pageSize)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public int findAuditoriaConfiguracionTotalRows() {
		try {
			String query = "SELECT count(*) FROM AUDITORIA_CONFIGURACION";
			return ((BigDecimal)createNativeQuery(query).getSingleResult()).intValue();
		} catch (NoResultException e) {
			return 0;
		}
	}
	
}
