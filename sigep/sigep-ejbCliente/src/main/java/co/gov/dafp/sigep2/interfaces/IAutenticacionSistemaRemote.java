/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioEntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioRolEntidadDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 *
 * @author JDavila
 */
@Remote
public interface IAutenticacionSistemaRemote extends IServiceRemote {
	public UsuarioDTO login(Long tipoDocumento, String login, String password, String mac, String ipAddress, long maxIntentos)
			throws SIGEP2SistemaException;

	public void aceptaHabeasData(UsuarioDTO usuario) throws SIGEP2SistemaException;

	public List<EntidadDTO> obtenerEntidadesUsuario(UsuarioDTO usuario) throws SIGEP2SistemaException;

	public List<EntidadDTO> obtenerEntidades() throws SIGEP2SistemaException;

	List<UsuarioDTO> obtenerEntidadesUsuarioAsociadas(Long usuario, Long entidad) throws SIGEP2SistemaException;

	public UsuarioRolEntidadDTO buscarUsuarioRolEntidad(Long codUsuarioEntidad)throws SIGEP2SistemaException;
	
	public UsuarioEntidadDTO consultarUsuarioEntidad(Long codUsuario, Long codEntidad) throws SIGEP2SistemaException;
}
