/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioEntidad;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class UsuarioEntidadFactoria extends AbstractFactory<UsuarioEntidad> {
	private static final long serialVersionUID = -7803228311046869240L;
	transient Logger logger = Logger.getInstance(UsuarioEntidadFactoria.class);

	@EJB
	private UsuarioFactoria usuarioFactoria;

	public UsuarioEntidadFactoria() {
		super(UsuarioEntidad.class);
	}

	public boolean desasociarUsuarioEntidad(Long codPersona, Long codEntidad, UsuarioDTO usuarioAud) {
		try {
			Usuario usuario = usuarioFactoria.consultarUsuarioEntityByCodPersona(codPersona);
			UsuarioEntidad ue = consultarUsuarioEntidad(usuario.getId(), codEntidad);
			Long desasociar = validarDesasociacion(usuario.getId(), codEntidad).longValue();
			if (ue != null && desasociar == 1) {
				String msg = "boolean desasociarUsuarioEntidad(Long codPersona, Long codEntidad, UsuarioDTO usuarioAud)";
				StoredProcedureQuery storedProcedure = this
						.createNamedStoredProcedureQuery(UsuarioEntidad.SP_DESASOCIAR_USUARIO);
				try {
					storedProcedure.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
					storedProcedure.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
					storedProcedure.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
					storedProcedure.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);

					storedProcedure.setParameter(1, codPersona);
					storedProcedure.setParameter(2, codEntidad);
					storedProcedure.setParameter(3, usuarioAud.getId());
					storedProcedure.setParameter(4, usuarioAud.getCodRol());

					storedProcedure.execute();
				} catch (Exception e) {
					logger.log().error(msg, e);
					throw new SIGEP2SistemaException(e);
				}
				return true;
			} else if (ue != null && desasociar == 0) {
				logger.info("Usuario con contrato vigente");
				return false;
			}
		} catch (Exception e) {
			logger.error("factoria desasociarUsuarioEntidad(Long codPersona, Long codEntidad)", e);
			return false;
		}
		return false;
	}

	public UsuarioEntidad consultarUsuarioEntidad(Long codUsuario, Long codEntidad) {
		try {
			String query = SQLNames.getSQL(SQLNames.USUARIO_ENTIDAD_CONTRATO_VINCULACION_SQL)
					+ SQLNames.getSQL(SQLNames.ACTIVO_USUARIO_ENTIDAD_FIND_BY_CODUSUARIO_CODENTIDAD);
			return (UsuarioEntidad) createNativeQuery(query, UsuarioEntidad.class)
					.setParameter("cod_usuario", codUsuario).setParameter("cod_entidad", codEntidad).setMaxResults(1)
					.getSingleResult();

		} catch (NoResultException e) {
			logger.error("factoria consultarUsuarioEntidad(Long codUsuario, Long codEntidad)", e);
			return null;
		}
	}

	private BigDecimal validarDesasociacion(Long codUsuario, Long codEntidad) {
		try {
			String query = SQLNames.getSQL(SQLNames.USUARIO_ROL_ENTIDAD_VALIDAR_DESASOCIACION_SQL)
					+ SQLNames.getSQL(SQLNames.USUARIO_ROL_ENTIDAD_VALIDAR_DESASOCIACION_BY_USUARIO_ENTIDAD);
			return (BigDecimal) createNativeQuery(query).setParameter("cod_usuario", codUsuario)
					.setParameter("cod_entidad", codEntidad).getSingleResult();
		} catch (NoResultException e) {
			logger.error("BigDecimal validarDesasociacion(Long codUsuario, Long codEntidad)", +codUsuario, codEntidad,
					e);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}