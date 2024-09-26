package co.gov.dafp.sigep2.interfaces;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IDesasociarUsuarioEntidadRemote extends IServiceRemote  {

	public PersonaDTO consultarPersonaByNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion)
			throws SIGEP2SistemaException;
	
	public PersonaDTO consultarPersonaByNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion, Long codEntidad)
			throws SIGEP2SistemaException;
	
	public boolean desasociarUsuarioEntidad(Long codUsuario, Long codEntidad,Long tipoDocumento, String numeroIdentificacion, UsuarioDTO usuarioAud, String strNotaPrivacidad, String Asunto, String cuerpoCorreo)
			throws SIGEP2SistemaException;
}
