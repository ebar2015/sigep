/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.ArchivoCargueDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.ArchivoCargue;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2ValidacionInformacionException;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2ValidacionNotificacionException;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class ArchivoCargueFactoria extends AbstractFactory<ArchivoCargue> {
	private static final long serialVersionUID = 8434481123880618015L;

	private static final String FALLIDO = "FALLIDO";

	public ArchivoCargueFactoria() {
		super(ArchivoCargue.class);
	}

	public ArchivoCargueDTO findByNombreArchivoCargue(String nombreArchivoCargue) {
		String msg = "ArchivoCargueDTO findByNombreArchivoCargue(String nombreArchivoCargue)";
		try {
			String sql = SQLNames.getSQL(SQLNames.ARCHIVO_CARGUE_SQL)
					+ SQLNames.getSQL(SQLNames.ARCHIVO_CARGUE_FIND_BY_ESTADO_SQL)
					+ SQLNames.getSQL(SQLNames.ARCHIVO_CARGUE_FIND_BY_NOMBRE_ARCHIVO_SQL).replace(SQLNames.WHERE,
							SQLNames.AND);
			return (ArchivoCargueDTO) this.createNativeQuery(sql, ArchivoCargue.ARCHIVO_CARGUE_MAPPING)
					.setParameter("nombre_archivo_cargue", nombreArchivoCargue).setParameter("flg_estadp", true)
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			logger.warn(msg, "El archivo en consulta no existe");
			return null;
		}
	}

	public ArchivoCargueDTO findByNombreArchivoCargue(String nombreArchivoCargue, boolean estado) {
		String msg = "ArchivoCargueDTO findByNombreArchivoCargue(String nombreArchivoCargue, boolean estado)";
		try {
			String sql = SQLNames.getSQL(SQLNames.ARCHIVO_CARGUE_SQL)
					+ SQLNames.getSQL(SQLNames.ARCHIVO_CARGUE_FIND_BY_ESTADO_SQL)
					+ SQLNames.getSQL(SQLNames.ARCHIVO_CARGUE_FIND_BY_NOMBRE_ARCHIVO_SQL).replace(SQLNames.WHERE,
							SQLNames.AND);
			return (ArchivoCargueDTO) this.createNativeQuery(sql, ArchivoCargue.ARCHIVO_CARGUE_MAPPING)
					.setParameter("nombre_archivo_cargue", nombreArchivoCargue).setParameter("flg_estadp", estado)
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			logger.warn(msg, "El archivo en consulta no existe");
			return null;
		}
	}

	public void ejecutarCargue(Long archivoCargueId, Long audUsuarioId, Long audRolId)
			throws Exception {
		String msg = "void ejecutarCargue(Long archivoCargueId, Long audUsuarioId, Long audRolId)";
		StoredProcedureQuery storedProcedure = this.createNamedStoredProcedureQuery(ArchivoCargue.SP_EJECUTAR_CARGUE);
		String resultado = "";
		String validacion = "";
		try {
			storedProcedure.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT);
			storedProcedure.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT);

			storedProcedure.setParameter(1, archivoCargueId);
			storedProcedure.setParameter(2, audUsuarioId);
			storedProcedure.setParameter(3, audRolId);

			storedProcedure.execute();
			resultado = (String) storedProcedure.getOutputParameterValue(4);
			validacion = (String) storedProcedure.getOutputParameterValue(5);
			if (resultado.toUpperCase().contains(FALLIDO)) {
				throw new SIGEP2ValidacionInformacionException(validacion.trim());
			} else {
				throw new SIGEP2ValidacionNotificacionException(validacion.trim());
			}
		} catch (SIGEP2ValidacionInformacionException e) {
			logger.error(msg, e);
			logger.log().error(msg, validacion);
		} catch (SIGEP2ValidacionNotificacionException e) {
			logger.info(msg, e);
			logger.log().error(msg, validacion);
		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new Exception(e);
		} finally {
			if (!resultado.isEmpty()) {
				logger.log().warn(resultado);
				logger.log().warn(msg, resultado);
			}
		}
	}
}
