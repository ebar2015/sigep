/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import java.util.Locale;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 *
 * @author JDavila
 */
@Remote
public interface ICambioContraseniaRemote extends IServiceRemote {
	public String solicitarRestablecerPassword(Long tipoDocumento, String login, String asunto, String cuerpo, int diasVigencia, int horasVigencia, Locale locale,String notaProvacidad) throws SIGEP2SistemaException;

	public void restablecerPassword(Long usuarioId, String password, String ticket, String asunto, String cuerpo, int diasVigenciaClave, int diasVigenciaTicket, int horasVigenciaTicket, Locale locale, String strNotaPrivacidad)
			throws SIGEP2SistemaException;

	public UsuarioPasswordDTO getUsuarioByLogin(Long tipoDocumento, String login) throws SIGEP2SistemaException;

	public UsuarioPasswordDTO getUsuarioTicket(String ticket) throws SIGEP2SistemaException;
	
	public UsuarioDTO getUsuario(long id) throws SIGEP2SistemaException;

	public void anularTicket(UsuarioPasswordDTO usuario, UsuarioDTO usuarioSesion) throws SIGEP2SistemaException;
	
	public void validarTicket(Long usuarioId, String ticket, int diasVigencia, int horasVigencia, Locale locale) throws SIGEP2SistemaException;
}
