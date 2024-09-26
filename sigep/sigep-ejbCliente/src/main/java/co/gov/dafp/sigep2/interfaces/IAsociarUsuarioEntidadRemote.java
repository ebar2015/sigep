package co.gov.dafp.sigep2.interfaces;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IAsociarUsuarioEntidadRemote extends IServiceRemote {
	public void asociarEntidad(PersonaDTO persona, TipoAsociacionDTO tipoAsociaion, UsuarioDTO usuarioRegistro,
			EntidadDTO entidadRegistro) throws SIGEP2SistemaException;

	public void crearUsuario(PersonaDTO persona, TipoAsociacionDTO tipoAsociacion, UsuarioDTO usuarioRegistro,
			EntidadDTO entidadRegistro) throws SIGEP2SistemaException;

	public PersonaDTO buscarPersonaAsociadaEntidad(Long tipoDocumento, String numeroIdentificacion, Long codEntidad)
			throws SIGEP2SistemaException;

	public void guardarPersona(PersonaDTO persona, Long tipoAsociacion, Long idUsuarioSesion, Long idEntidad)
			throws SIGEP2SistemaException;

	public boolean usuarioExisteyActivo(PersonaDTO persona, long idEntidad, long tipoAsociacion);

	public boolean usuarioExisteyActivo(PersonaDTO persona, long idEntidad);
}