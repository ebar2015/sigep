/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;

/**
 *
 * @author JDavila
 */
@Remote
public interface IRecursosMenuRemote extends IServiceRemote {
	public RecursoActivoPerfilUsuarioDTO findByCodigoVentana(String codigoVentana, Long usuarioId);

	public List<RecursoActivoPerfilUsuarioDTO> obtenerRecursosActivosPorUsuario(Long usuarioId, Long entidadId);

	public List<PermisoUsuarioRolDTO> obtenerPermisosPorUsuario(long usuario);

	public List<PermisoUsuarioRolDTO> obtenerPermisosPorCodigoVentana(String codigoVentana, Long usuarioId, List<RolDTO> roles);

	public List<PermisoUsuarioRolDTO> obtenerPermisosPorAccion(String accion);

	public List<RolDTO> obtenerRolesPorUsuario(Long usuarioId, Long entidadId);
}
