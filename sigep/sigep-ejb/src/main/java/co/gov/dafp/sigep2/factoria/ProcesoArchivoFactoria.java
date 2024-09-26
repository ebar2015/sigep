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
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.view.ProcesoArchivo;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class ProcesoArchivoFactoria extends AbstractFactory<ProcesoArchivo> {
	private static final long serialVersionUID = -872183095620813135L;

	public ProcesoArchivoFactoria() {
		super(ProcesoArchivo.class);
	}

	public List<ProcesoArchivo> getReportes() {
		List<ProcesoArchivo> procesosArchivo = allActives();
		logger.log().info("List<ProcesoArchivo> getReportes()", "Lista reportes " + procesosArchivo.size());
		return procesosArchivo;
	}

	public List<ProcesoArchivo> getReportesUsuario(Usuario usuarioSesion) {
		List<ProcesoArchivo> procesosArchivo = allActives();
		logger.log().info("List<ProcesoArchivo> getReportesUsuario(Usuario usuarioSesion)",
				"Lista reportes " + procesosArchivo.size());
		return procesosArchivo;
	}

	@SuppressWarnings("unchecked")
	public List<ProcesoArchivoDTO> getReportesByPlantilla(List<String> nombresPlantilla) {
		String sql = SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_SQL)
				+ SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_FIND_BY_PLANTILLAS_SQL);
		List<ProcesoArchivoDTO> procesosArchivo = createNativeQuery(sql, ProcesoArchivo.PROCESO_ARCHIVO_MAPPING)
				.setParameter("nombresPlantilla", nombresPlantilla).getResultList();
		logger.log().info(" List<ProcesoArchivoDTO> getReportesByPlantilla(List<String> nombresPlantilla)",
				"Lista reportes " + procesosArchivo.size());
		return procesosArchivo;
	}

	@SuppressWarnings("unchecked")
	public List<ProcesoArchivoDTO> getProcesosArchivoPlantillas() {
		String sql = SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_SQL);
		List<ProcesoArchivoDTO> procesosArchivo = createNativeQuery(sql, ProcesoArchivo.PROCESO_ARCHIVO_MAPPING)
				.getResultList();
		logger.log().info("List<ProcesoArchivoDTO> getProcesosArchivoPlantillas()",
				"Lista tipos de archivos " + procesosArchivo.size());
		return procesosArchivo;
	}

	@SuppressWarnings("unchecked")
	public List<ProcesoArchivoDTO> getProcesosArchivo() {
		String sql = SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_SQL);
		List<ProcesoArchivoDTO> procesosArchivo = createNativeQuery(sql, ProcesoArchivo.PROCESO_ARCHIVO_MAPPING)
				.getResultList();
		logger.log().info("List<ProcesoArchivoDTO> getProcesosArchivo()",
				"Lista tipos de archivos " + procesosArchivo.size());
		return procesosArchivo;
	}

	@SuppressWarnings("unchecked")
	public List<ProcesoArchivoDTO> getPlantillas() {
		String sql = SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_SQL);
		List<ProcesoArchivoDTO> procesosArchivo = createNativeQuery(sql, ProcesoArchivo.PROCESO_ARCHIVO_MAPPING)
				.getResultList();
		sql = SQLNames.getSQL(SQLNames.PROCESO_BACKGROUND_SQL);
		procesosArchivo.addAll(createNativeQuery(sql, ProcesoArchivo.PROCESO_BACKGROUND_MAPPING).getResultList());
		logger.log().info("List<ProcesoArchivoDTO> getPlantillas()",
				"Lista tipos de archivos " + procesosArchivo.size());
		return procesosArchivo;
	}

	public ProcesoArchivoDTO getReporteByPlantilla(String nombrePlantilla, String recursoMenu, String modulo) {
		try {
			String sql = SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_SQL)
					+ SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_FIND_BY_PLANTILLA_SQL)
					+ SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_FIND_BY_MENU_SQL).replace(SQLNames.WHERE, SQLNames.AND)
					+ SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_FIND_BY_MODULO_SQL).replace(SQLNames.WHERE,
							SQLNames.AND);
			return (ProcesoArchivoDTO) createNativeQuery(sql, ProcesoArchivo.PROCESO_ARCHIVO_MAPPING)
					.setParameter("nombrePlantilla", nombrePlantilla).setParameter("recursoMenu", recursoMenu)
					.setParameter("modulo", modulo + "%").getSingleResult();
		} catch (NoResultException e) {
			logger.log().info(
					"ProcesoArchivoDTO getReporteByPlantilla(String nombrePlantilla, String recursoMenu, String modulo)",
					"Sin resultados");
		}
		return null;
	}

	public ProcesoArchivoDTO findReporteByDescripcion(String descripcion) {
		try {
			String sql = SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_SQL)
					+ SQLNames.getSQL(SQLNames.PROCESO_ARCHIVO_FIND_BY_DESCRIPCION_SQL);
			return (ProcesoArchivoDTO) createNativeQuery(sql, ProcesoArchivo.PROCESO_ARCHIVO_MAPPING)
					.setParameter("descripcion", descripcion).getSingleResult();
		} catch (NoResultException e) {
		}
		return null;
	}

	public List<Object[]> ejecutarQuery(String sqlString, Map<Object, Object> parameters, int first, int pageSize) {
		return executeNativeQuery(sqlString, parameters, first, pageSize);
	}

	public synchronized void exectBatch(String nombreArchivo, String userName, String password, String server,
			String nameDDBB) throws Exception {
		executeBatchFromFile(nombreArchivo, userName, password, server, nameDDBB);
	}
}
