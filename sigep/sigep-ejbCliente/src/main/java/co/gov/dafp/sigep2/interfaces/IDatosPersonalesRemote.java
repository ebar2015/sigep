package co.gov.dafp.sigep2.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoDiscapacidadDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IDatosPersonalesRemote extends IServiceRemote {
	public PersonaDTO findPersonaId(long id);

	public List<TipoDiscapacidadDTO> findTipoDiscapacidad();

	public List<PersonaDTO> obtenerUsuariosFiltros(long tipoDoc, String doc, String primerNombre, String segundoNombre,
			String primerApellido, String segundoAPellido, Boolean busquedaDocumento) throws SIGEP2SistemaException;

	public void actualizarDatosDemograficos(long codPersona, List<PaisDTO> nacionalidad, UsuarioDTO usuarioRegistro,
			long rolUsuario) throws SIGEP2SistemaException;

	List<PaisDTO> findNacionalidadByCodPersonaId(long codPersona) throws SIGEP2SistemaException;

	public void emailActualizacionDatosPersona(String usuarioModificado, String email)
			throws SIGEP2SistemaException;
	
	public void emailActualizacionDatosPersonaDC(String usuarioModificado, Date fechaModificacion, String usuarioModificador, List<String> email, List<String> ccEmail, List<String> camposEditados)
			throws SIGEP2SistemaException;
	
	public void enviarEmailPersonasDesvincular(String asunto, String mensaje, String correo)
			throws SIGEP2SistemaException;
	

	public void emailActualizacionDatosPersona(String usuarioTH, String cuerpo, String email)throws SIGEP2SistemaException;
}