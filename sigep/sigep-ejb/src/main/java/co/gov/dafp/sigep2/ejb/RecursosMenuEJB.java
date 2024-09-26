package co.gov.dafp.sigep2.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.factoria.PermisoUsuarioRolFactoria;
import co.gov.dafp.sigep2.factoria.RecursoFactoria;
import co.gov.dafp.sigep2.interfaces.IRecursosMenuLocal;
import co.gov.dafp.sigep2.interfaces.IRecursosMenuRemote;

@Stateless
public class RecursosMenuEJB implements IRecursosMenuLocal, IRecursosMenuRemote {
	private static final long serialVersionUID = 6049249583974889186L;

	@EJB
	private RecursoFactoria recursoFactoria;

	@EJB
	private PermisoUsuarioRolFactoria permisoUsuarioRolFactoria;

	@Override
	public RecursoActivoPerfilUsuarioDTO findByCodigoVentana(String codigoVentana, Long usuarioId) {
		return recursoFactoria.findByCodigoVentanaDTO(codigoVentana, usuarioId);
	}

	@Override
	public List<RecursoActivoPerfilUsuarioDTO> obtenerRecursosActivosPorUsuario(Long usuarioId, Long entidadId) {
		return recursoFactoria.obtenerRecursosActivosPorUsuario(usuarioId, entidadId);
	}

	@Override
	public List<RolDTO> obtenerRolesPorUsuario(Long usuarioId, Long entidadId) {
		return recursoFactoria.obtenerRolesPorUsuario(usuarioId, entidadId);
	}

	@Override
	public List<PermisoUsuarioRolDTO> obtenerPermisosPorUsuario(long usuario) {
		return permisoUsuarioRolFactoria.obtenerPermisosPorUsuario(usuario);
	}

	@Override
	public List<PermisoUsuarioRolDTO> obtenerPermisosPorCodigoVentana(String codigoVentana, Long usuarioId,
			List<RolDTO> roles) {
		return permisoUsuarioRolFactoria.obtenerPermisosPorCodigoVentana(codigoVentana, usuarioId, roles);
	}

	@Override
	public List<PermisoUsuarioRolDTO> obtenerPermisosPorAccion(String accion) {
		return permisoUsuarioRolFactoria.obtenerPermisosPorAccion(accion);
	}

}
