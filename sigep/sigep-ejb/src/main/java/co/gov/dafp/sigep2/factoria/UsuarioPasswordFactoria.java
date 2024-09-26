/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioPassword;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class UsuarioPasswordFactoria extends AbstractFactory<UsuarioPassword> {
	private static final long serialVersionUID = 3599355031276962146L;

	public UsuarioPasswordFactoria() {
		super(UsuarioPassword.class);
	}

	public UsuarioPasswordDTO getUsuarioByLogin(String login, Long tipoDocumento) throws SIGEP2SistemaException {
		try {
			String sql = SQLNames.getSQL(SQLNames.USUARIO_PASS_SQL)
					+ SQLNames.getSQL(SQLNames.USUARIO_FIND_BY_LOGIN_FULL_SQL);
			return (UsuarioPasswordDTO) createNativeQuery(sql, UsuarioPassword.USUARIO_MAPPING)
					.setParameter("nombre_usuario", login).setParameter("cod_tipo_identificacion", tipoDocumento)
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			logger.error("UsuarioPasswordDTO getUsuarioByLogin(String login, Long tipoDocumento)", e);
			return null;
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	public UsuarioPasswordDTO getUsuarioByLoginOrMail(String login, Long tipoDocumento) throws SIGEP2SistemaException {
		try {
			String sql = SQLNames.getSQL(SQLNames.USUARIO_PASS_SQL)
					+ SQLNames.getSQL(SQLNames.USUARIO_FIND_BY_LOGIN_FULL_SQL);
			return (UsuarioPasswordDTO) createNativeQuery(sql, UsuarioPassword.USUARIO_MAPPING)
					.setParameter("nombre_usuario", login).setParameter("cod_tipo_identificacion", tipoDocumento)
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			logger.info("UsuarioPasswordDTO getUsuarioByLoginOrMail(String login, Long tipoDocumento)", e);
			return null;
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	public UsuarioPasswordDTO getUsuarioByTicket(String ticket) throws SIGEP2SistemaException {
		try {
			String sql = SQLNames.getSQL(SQLNames.USUARIO_PASS_SQL)
					+ SQLNames.getSQL(SQLNames.USUARIO_FIND_BY_ESTADO_SQL)
					+ SQLNames.getSQL(SQLNames.USUARIO_FIND_BY_TICKET_SQL).replace(SQLNames.WHERE, SQLNames.AND);
			return (UsuarioPasswordDTO) createNativeQuery(sql, UsuarioPassword.USUARIO_MAPPING)
					.setParameter("ticket", ticket).setParameter("flg_estado", true).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			logger.error("UsuarioPasswordDTO getUsuarioByTicket(String ticket)", e);
			return null;
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

}
