/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.ReporteDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.view.Reporte;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class ReporteFactoria extends AbstractFactory<Reporte> {
	private static final long serialVersionUID = -872183095620813135L;

	public ReporteFactoria() {
		super(Reporte.class);
	}

	public List<Reporte> getReportes() {
		return this.allActives();
	}

	public List<Reporte> getReportesUsuario(UsuarioDTO usuarioSesion) {
		return this.allActives();
	}

	@SuppressWarnings("unchecked")
	public List<Reporte> getReportesByPlantilla(List<String> nombrePlantilla) {
		String query = SQLNames.getSQL(SQLNames.REPORTE_SQL) + SQLNames.getSQL(SQLNames.REPORTE_FIND_BY_PLANTILLAS_SQL);
		return createNativeQuery(query, Reporte.REPORTE_MAPPING).setParameter("plantilla_xml", nombrePlantilla)
				.getResultList();
	}

	public ReporteDTO getReporteByPlantilla(String nombrePlantilla, String recursoMenu, String modulo) {
		try {
			String query = SQLNames.getSQL(SQLNames.REPORTE_SQL)
					+ SQLNames.getSQL(SQLNames.REPORTE_FIND_BY_DESCRIPCION_SQL);
			return (ReporteDTO) createNativeQuery(query, Reporte.REPORTE_MAPPING)
					.setParameter("plantilla_xml", nombrePlantilla).setParameter("menu", recursoMenu)
					.setParameter("modulo", modulo).getSingleResult();
		} catch (NoResultException e) {
			logger.log().info(
					"Reporte getReporteByPlantilla(String nombrePlantilla, String recursoMenu, String modulo)",
					"Sin resultados");
		}
		return null;
	}

	public ReporteDTO findReporteByDescripcion(String descripcion) {
		try {
			String query = SQLNames.getSQL(SQLNames.REPORTE_SQL)
					+ SQLNames.getSQL(SQLNames.REPORTE_FIND_BY_DESCRIPCION_SQL);
			return (ReporteDTO) createNativeQuery(query, Reporte.REPORTE_MAPPING)
					.setParameter("descripcion", descripcion).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Object[]> ejecutarQuery(String sqlString, Map<Object, Object> parameters, int first, int pageSize) {
		return executeNativeQuery(sqlString, parameters, first, pageSize);
	}
}
