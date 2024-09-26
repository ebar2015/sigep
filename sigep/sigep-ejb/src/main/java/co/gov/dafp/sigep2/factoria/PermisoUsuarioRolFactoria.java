package co.gov.dafp.sigep2.factoria;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.view.PermisoUsuarioRol;

@Stateless
public class PermisoUsuarioRolFactoria extends AbstractFactory<PermisoUsuarioRol> {
	private static final long serialVersionUID = -1218090837550198381L;

	public PermisoUsuarioRolFactoria() {
		super(PermisoUsuarioRol.class);
	}

	@SuppressWarnings("unchecked")
	public List<PermisoUsuarioRolDTO> obtenerPermisosPorUsuario(long usuario) {
		String query = SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_SQL)
				+ SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_FIND_BY_USUARIO_SQL);
		return createNativeQuery(query, PermisoUsuarioRol.PERMISO_USUARIO_ROL_MAPPING)
				.setParameter("cod_usuario", usuario).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PermisoUsuarioRolDTO> obtenerPermisosPorCodigoVentana(String codigoVentana, Long usuarioId,
			List<RolDTO> roles) {
		String query = SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_SQL)
				+ SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_FIND_BY_CONTROL_HTML_SQL)
				+ SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_FIND_BY_USUARIO_SQL).replace(SQLNames.WHERE,
						SQLNames.AND)
				+ SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_FIND_BY_ROL_SQL).replace(SQLNames.WHERE, SQLNames.AND)
				+ SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_ORDER_BY);
		List<Long> rolesIds = new LinkedList<>();
		for (RolDTO rol : roles) {
			rolesIds.add(rol.getId());
		}
		return createNativeQuery(query, PermisoUsuarioRol.PERMISO_USUARIO_ROL_MAPPING)
				.setParameter("control_html_padre", codigoVentana).setParameter("cod_usuario", usuarioId)
				.setParameter("roles", rolesIds).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<PermisoUsuarioRolDTO> obtenerPermisosPorAccion(String accion) {
		String query = SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_SQL)
				+ SQLNames.getSQL(SQLNames.PERMISO_USUARIO_ROL_FIND_BY_USUARIO_SQL);
		return createNativeQuery(query, PermisoUsuarioRol.PERMISO_USUARIO_ROL_MAPPING).setParameter("accion", accion)
				.getResultList();
	}
}
