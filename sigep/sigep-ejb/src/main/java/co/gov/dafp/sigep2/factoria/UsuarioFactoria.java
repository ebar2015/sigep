/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Entidad;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioEntidad;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 * @author jhon.deavila
 */
@Stateless
public class UsuarioFactoria extends AbstractFactory<Usuario> {
	private static final long serialVersionUID = -7559670952660299740L;

	@EJB
	private UsuarioRolEntidadFactoria usuarioPerfilDao;

	@EJB
	private RolFactoria rolFactoria;

	private static Usuario usuarioSistema;

	public UsuarioFactoria() {
		super(Usuario.class);
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> obtenerUsuariosPorRol(String rol) {
		String query = SQLNames.getSQL(SQLNames.USUARIO_SQL)
				+ SQLNames.getSQL(SQLNames.USUARIO_ROL_FIND_BY_USUARIO_X_ROL_SQL)
				+ SQLNames.getSQL(SQLNames.USUARIO_FIND_BY_ESTADO_SQL)
				+ SQLNames.getSQL(SQLNames.USUARIO_ROL_FIND_BY_ESTADO_SQL.replace(SQLNames.WHERE, SQLNames.AND))
				+ SQLNames.getSQL(SQLNames.ROL_FIND_BY_NOMBRE_SQL.replace(SQLNames.WHERE, SQLNames.AND));
		return createNativeQuery(query, Usuario.USUARIO_MAPPING).setParameter("nombre", rol)
				.setParameter("bloqueado", false).setParameter("estado", true).getResultList();

	}

	public UsuarioDTO consultarUsuarioByCodPersona(Long codPersona) {
		String query = SQLNames.getSQL(SQLNames.USUARIO_SQL) + SQLNames.getSQL(SQLNames.USUARIO_FIND_BY_COD_PERSONA);
		return (UsuarioDTO) createNativeQuery(query, Usuario.USUARIO_MAPPING).setParameter("cod_persona", codPersona)
				.getSingleResult();
	}

	public Usuario consultarUsuarioEntityByCodPersona(Long codPersona) {
		try {
			String query = SQLNames.getSQL(SQLNames.USUARIO_ENTITY_SQL) + SQLNames.getSQL(SQLNames.USUARIO_BY_PERSONA);
			return (Usuario) createNativeQuery(query, Usuario.class).setParameter("cod_persona", codPersona)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		}
	}

	public Usuario bloquearUsuario(long id, Usuario usuarioSesion) throws SIGEP2SistemaException {
		try {
			Usuario usuario = find(id);
			usuario.setFlgBloqueado(true);
			usuario = merge(usuario, usuarioSesion);
			logger.log().info("bloquearUsuario(String login)", usuario.toString());
			return usuario;
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	public UsuarioDTO getUsuarioByLogin(String login, Long tipoDocumento) throws SIGEP2SistemaException {
		try {
			String query = SQLNames.getSQL(SQLNames.USUARIO_SQL)
					+ SQLNames.getSQL(SQLNames.USUARIO_FIND_BY_LOGIN_FULL_SQL);
			return (UsuarioDTO) createNativeQuery(query, Usuario.USUARIO_MAPPING).setParameter("nombre_usuario", login)
					.setParameter("cod_tipo_identificacion", tipoDocumento).getSingleResult();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	private UsuarioDTO getUsuarioByLogin(String login) throws SIGEP2SistemaException {
		try {
			String query = SQLNames.getSQL(SQLNames.USUARIO_SQL) + SQLNames.getSQL(SQLNames.USUARIO_FIND_BY_LOGIN_SQL);
			return (UsuarioDTO) createNativeQuery(query, Usuario.USUARIO_MAPPING).setParameter("nombre_usuario", login)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.error("", e);
			return null;
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e);
		}
	}

	public Usuario registrarIngreso(final long id, String lugarUltimoIngreso) throws SIGEP2SistemaException {
		Usuario usuario = find(id);
		usuario.setFechaUltimoIngreso(DateUtils.getFechaSistema());
		usuario.setNumeroReintentos(0l);
		//logger.log().info("Usuario registrarIngreso(final long id, String lugarUltimoIngreso)", usuario.toString());
		return merge(usuario, usuario);
	}

	public Usuario getUsuarioSistema() throws SIGEP2SistemaException {
		if (UsuarioFactoria.usuarioSistema == null) {
			UsuarioDTO usuarioSistemaDTO = this.getUsuarioByLogin(UsuarioDTO.USUARIO_SISTEMA);
			if (usuarioSistemaDTO != null) {
				Usuario usuarioSistema = find(usuarioSistemaDTO.getId());
				Rol rolSistema = rolFactoria.obtenerRolPorDescripcion(RolDTO.SISTEMA);
				usuarioSistema.setCodRol(rolSistema.getId());
				setUsuarioSistema(usuarioSistema);
			} else {
				throw new SIGEP2SistemaException(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE);
			}
		}
		return UsuarioFactoria.usuarioSistema;
	}

	private static void setUsuarioSistema(Usuario usuarioSistema) {
		UsuarioFactoria.usuarioSistema = usuarioSistema;
	}

	public void aceptaHabeasData(UsuarioDTO usuario) throws SIGEP2SistemaException {
		Usuario acepto = find(usuario.getId());
		acepto.setFechaAceptoHabeas(DateUtils.getFechaSistema());
		acepto.setFlgAceptoHabeasData(true);
		merge(acepto, null);
	}

	@SuppressWarnings("unchecked")
	public List<EntidadDTO> obtenerEntidadesUsuario(UsuarioDTO usuario) {
		String query = SQLNames.getSQL(SQLNames.ENTIDAD_USUARIO_BASICA_SQL)
				+ SQLNames.getSQL(SQLNames.ENTIDAD_USUARIO_FIND_BY_USUARIO_SQL);
		return createNativeQuery(query, Entidad.ENTIDAD_BASICO_SIGEP_MAPPING).setParameter("usuario", usuario.getId())
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> obtenerEntidadesUsuarioAsociadas(Long usuario, Long entidad) {
		String query = SQLNames.getSQL(SQLNames.USUARIO_SQL) + SQLNames.getSQL(SQLNames.USUARIOENTIDAD_USUARIO)
				+ SQLNames.getSQL(SQLNames.USUARIO_ENTIDAD_ASOCIACION);
		return createNativeQuery(query, Usuario.USUARIO_MAPPING).setParameter("usuario_id", usuario)
				.setParameter("entidad_id", entidad).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<EntidadDTO> obtenerEntidades() {
		String query = SQLNames.getSQL(SQLNames.ENTIDAD_USUARIO_BASICA_SQL);
		return createNativeQuery(query, Entidad.ENTIDAD_BASICO_MAPPING).getResultList();
	}

	public void desactivarUsuariosProcedure(Long entidad) throws Exception {
		String msg = "void desactivarUsuariosProcedure(EntidadDTO entidad)";
		StoredProcedureQuery storedProcedure = this.createNamedStoredProcedureQuery(Usuario.USP_BLOQUEA_USUARIO_MASIVO);
		try {
			storedProcedure.registerStoredProcedureParameter(0, String.class, ParameterMode.IN);
			if (entidad == null) {
				storedProcedure.setParameter(0, "-1");
			} else {
				storedProcedure.setParameter(0, entidad.toString());
			}

			storedProcedure.execute();

		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new Exception(e);
		}
	}

	public void actualizarEstadoUsuario(UsuarioDTO usuario) {
		String query = SQLNames.getSQL(SQLNames.UPDATE_ESTADO_USUARIO);
		createNativeQuery(query, Usuario.USUARIO_MAPPING).setParameter("idusuario", usuario.getId());
	}

	public void actualizarActivoUsuarioEntidad(UsuarioDTO usuario) {
		String query = SQLNames.getSQL(SQLNames.UPDATE_ACTIVO_USUARIO_ENTIDAD);
		createNativeQuery(query, UsuarioEntidad.USUARIO_ENTIDAD_MAPPING).setParameter("idusuario", usuario.getId());
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> obtenerUsuariosEntidad(EntidadDTO entidad) {
		String query = SQLNames.getSQL(SQLNames.USUARIO_BY_ENTIDAD_SQL)
				+ SQLNames.getSQL(SQLNames.FIND_USUARIOS_BY_ENTIDAD);
		return createNativeQuery(query, Usuario.USUARIO_MAPPING).setParameter("entidad", entidad.getId())
				.getResultList();
	}

	public Usuario desbloquearUsuario(final long id) throws SIGEP2SistemaException {
		Usuario usuario = find(id);
		usuario.setNumeroReintentos(0l);
		usuario.setFlgBloqueado(false);
		return merge(usuario, usuario);
	}

	/**
	 * Devuelve el usuario que va a trazar la auditoria en una transaccion
	 * 
	 * @param usuarioAuditoria
	 * @return {@link Usuario}
	 * @throws SIGEP2SistemaException
	 */
	public Usuario getUsuarioParaAuditoria(UsuarioDTO usuarioAuditoria) throws SIGEP2SistemaException {
		Usuario usuario = find(usuarioAuditoria.getId());
		if (usuario != null) {
			Rol rol = rolFactoria.find(usuarioAuditoria.getCodRol());
			if (rol != null) {
				usuario.setCodRol(rol.getId());
				return usuario;
			}
			throw new SIGEP2SistemaException(MessagesBundleConstants.DLG_CONFIGURATION_INVALID);
		}
		throw new SIGEP2SistemaException(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_EXISTE);
	}

}
