/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import javax.ejb.Local;

import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 *
 * @author JDavila
 */
@Local
public interface ICambioContraseniaLocal extends IServiceLocal {
	public String solicitarRestablecerPassword(Long tipoDocumento, String login, String asunto, String cuerpo, int minutosVigencia, int horasVigencia) throws SIGEP2SistemaException;

	public void restablecerPassword(Long usuarioId, String password, String ticket, String asunto, String cuerpo, int diasVigenciaClave, int diasVigenciaTicket, int horaVigenciaTicket)
			throws SIGEP2SistemaException;

	public UsuarioPasswordDTO getUsuarioByLogin(Long tipoDocumento, String login) throws SIGEP2SistemaException;

	public UsuarioPasswordDTO getUsuarioTicket(String ticket) throws SIGEP2SistemaException;

	public void actualizarUsuario(UsuarioPasswordDTO usuario, UsuarioDTO usuarioSesion) throws SIGEP2SistemaException;
}
