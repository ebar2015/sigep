/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import co.gov.dafp.sigep2.entity.jpa.seguridad.LogAuditoria;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class LogFactoria extends AbstractFactory<LogAuditoria> {
	private static final long serialVersionUID = 2448624168335456716L;

	public LogFactoria() {
		super(LogAuditoria.class);
	}

	public List<String> getEntidades() {
		logger.log().info("List<String> getEntidades()");
		return this.createNamedQuery(LogAuditoria.FIND_ALL_ENTIDADES, String.class).getResultList();
	}

	public List<LogAuditoria> getLog(String entidad) {
		logger.log().info("List<LogAuditoria> getLog(String entidad)");
		return this.createNamedQuery(LogAuditoria.FIND_BY_ENTIDAD, LogAuditoria.class)
				.setParameter("entidad", entidad).getResultList();
	}

	public List<LogAuditoria> getLog(Date fechaInicial, Date fechaFinal) {
		logger.log().info("List<LogAuditoria> getLog(Date fechaInicial, Date fechaFinal)");
		return this.createNamedQuery(LogAuditoria.FIND_BY_FECHA, LogAuditoria.class)
				.setParameter("fechaInicial", fechaInicial).setParameter("fechaFinal", fechaFinal).getResultList();
	}

	public List<LogAuditoria> getLog(Usuario usuarioId) {
		logger.log().info("List<LogAuditoria> getLog(Usuario usuario)");
		return this.createNamedQuery(LogAuditoria.FIND_BY_USUARIO, LogAuditoria.class)
				.setParameter("usuarioId", usuarioId).getResultList();
	}

	public List<LogAuditoria> getLog(String entidad, Date fechaInicial, Date fechaFinal, Usuario usuarioId) {
		logger.log().info(
				"List<LogAuditoria> getLog(String entidad, Date fechaInicial, Date fechaFinal, Usuario usuarioId)");
		StringBuffer hql = new StringBuffer();
		hql.append(LogAuditoria.SELECT_FIND_ALL);
		if (entidad != null && !entidad.isEmpty()) {
			hql.append(LogAuditoria.AND_FIND_BY_ENTIDAD);
		}
		if (fechaInicial != null && fechaFinal != null) {
			hql.append(LogAuditoria.AND_FIND_BY_FECHA);
		}
		if (usuarioId != null) {
			hql.append(LogAuditoria.AND_FIND_BY_USUARIO);
		}
		hql.append(LogAuditoria.ORDER_BY_DESC_FECHA_REGISTRO);
		TypedQuery<LogAuditoria> q2 = this.createQuery(hql.toString(), LogAuditoria.class);

		if (entidad != null && !entidad.isEmpty()) {
			q2.setParameter("entidad", entidad);
		}
		if (fechaInicial != null && fechaFinal != null) {
			q2.setParameter("fechaInicial", fechaInicial).setParameter("fechaFinal", fechaFinal);
		}
		if (usuarioId != null) {
			q2.setParameter("usuarioId", usuarioId);
		}
		List<LogAuditoria> logAuditoria = q2.getResultList();
		logger.log().info(
				"List<LogAuditoria> getLog(String entidad, Date fechaInicial, Date fechaFinal, Usuario usuarioId)",
				"Registros devueltos " + logAuditoria.size());
		return logAuditoria;
	}

}
