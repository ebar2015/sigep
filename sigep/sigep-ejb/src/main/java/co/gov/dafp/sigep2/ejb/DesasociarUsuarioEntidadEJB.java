package co.gov.dafp.sigep2.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.comun.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.factoria.ParametricaFactoria;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioEntidadFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.interfaces.IDesasociarUsuarioEntidadRemote;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class DesasociarUsuarioEntidadEJB implements IDesasociarUsuarioEntidadRemote {
	private static final long serialVersionUID = 4781401191917767846L;

	transient Logger logger = Logger.getInstance(DesasociarUsuarioEntidadEJB.class);

	@EJB
	private IEnvioCorreoLocal mailService;

	@EJB
	private PersonaFactoria personaFactoria;

	@EJB
	private UsuarioEntidadFactoria usuarioEntidadFactoria;

	@EJB
	private UsuarioFactoria usuarioFactoria;

	@EJB
	private ParametricaFactoria parametricaFactoria;

	@Override
	public PersonaDTO consultarPersonaByNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion)
			throws SIGEP2SistemaException {
		return personaFactoria.findPersonaByTipoDocumentoAndNumeroIdentificacion(tipoDocumento, numeroIdentificacion);
	}

	@Override
	public PersonaDTO consultarPersonaByNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion,
			Long codEntidad) throws SIGEP2SistemaException {
		return personaFactoria.findPersonaByTipoDocumentoAndNumeroIdentificacion(tipoDocumento, numeroIdentificacion,
				codEntidad);
	}

	@Override
	public boolean desasociarUsuarioEntidad(Long codPersona, Long codEntidad, Long tipoDocumento,
			String numeroIdentificacion, UsuarioDTO usuarioAud, String strNotaPrivacidad, String asunto, String cuerpoCorreo) throws SIGEP2SistemaException {

		boolean usuarioDesasociado = false;
		try {
			usuarioDesasociado = usuarioEntidadFactoria.desasociarUsuarioEntidad(codPersona, codEntidad, usuarioAud);

			if (usuarioDesasociado == true) {
				PersonaDTO personaDTO = consultarPersonaByNumeroIdentificacion(tipoDocumento, numeroIdentificacion,
						codEntidad);

				List<String> toList = new ArrayList<>();
				toList.add(personaDTO.getCorreoElectronico());
				Parametrica parametro = parametricaFactoria.findByDescripcion(Parametrica.PAR_DIAS_DESCARGAR_INFO);

				mailService.enviarMail(asunto, // asunto 
						cuerpoCorreo, // body
						null, // from
						toList, // to
						null // cc
						, strNotaPrivacidad
				);

				return usuarioDesasociado;
			}
			return false;

		} catch (Exception e) {
			e.getStackTrace();
			logger.log().error("EJB - desasociarUsuarioEntidad(Long codPersona, Long codEntidad)", e);
			throw new SIGEP2SistemaException(e.getMessage());
		}

	}

}
