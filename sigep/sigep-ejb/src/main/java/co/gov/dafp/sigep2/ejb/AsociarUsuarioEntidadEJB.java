package co.gov.dafp.sigep2.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioRolEntidadFactoria;
import co.gov.dafp.sigep2.interfaces.IAsociarUsuarioEntidadRemote;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class AsociarUsuarioEntidadEJB implements IAsociarUsuarioEntidadRemote {
	private static final long serialVersionUID = 1352471373248439462L;

	transient Logger logger = Logger.getInstance(AsociarUsuarioEntidadEJB.class);

	@EJB
	private PersonaFactoria personaFactoria;

	@EJB
	private UsuarioRolEntidadFactoria usuarioRolEntidadFactoria;

	public void asociarEntidad(PersonaDTO persona, TipoAsociacionDTO tipoAsociacion, UsuarioDTO usuarioRegistro,
			EntidadDTO entidad) throws SIGEP2SistemaException {
		String msg = "asociarEntidad(PersonaDTO persona, TipoAsociacionDTO tipoAsociacion, UsuarioDTO usuarioRegistro, EntidadDTO entidad)";
		try {
			usuarioRolEntidadFactoria.asociarUsuario(persona, entidad, tipoAsociacion, usuarioRegistro);
		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new SIGEP2SistemaException(e);
		}
	}

	public void crearUsuario(PersonaDTO persona, TipoAsociacionDTO tipoAsociacion, UsuarioDTO usuarioRegistro,
			EntidadDTO entidad) throws SIGEP2SistemaException {
		String msg = "crearUsuario(PersonaDTO persona, TipoAsociacionDTO tipoAsociacion, UsuarioDTO usuarioRegistro, EntidadDTO entidad)";
		try {
			usuarioRolEntidadFactoria.crearUsuario(persona, entidad, tipoAsociacion, usuarioRegistro);
		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new SIGEP2SistemaException(e);
		}
	}

	public PersonaDTO buscarPersonaAsociadaEntidad(Long tipoDocumento, String numeroIdentificacion, Long codEntidad)
			throws SIGEP2SistemaException {
		return personaFactoria.buscarPersonaAsociadaEntidad(tipoDocumento, numeroIdentificacion, codEntidad);
	}

	@Override
	public void guardarPersona(PersonaDTO persona, Long tipoAsociacion, Long idUsuarioSesion, Long idEntidad) {
		usuarioRolEntidadFactoria.crearPersona(persona, tipoAsociacion, idUsuarioSesion, idEntidad);
	}

	@Override
	public boolean usuarioExisteyActivo(PersonaDTO persona, long idEntidad, long tipoAsociacion) {
		return usuarioRolEntidadFactoria.usuarioExisteyActivo(persona, idEntidad, tipoAsociacion);
	}

	@Override
	public boolean usuarioExisteyActivo(PersonaDTO persona, long idEntidad) {
		return usuarioRolEntidadFactoria.usuarioExisteyActivo(persona, idEntidad);
	}
}