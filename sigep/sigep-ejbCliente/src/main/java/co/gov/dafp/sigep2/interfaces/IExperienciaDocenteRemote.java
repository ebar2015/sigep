package co.gov.dafp.sigep2.interfaces;


import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.ExperienciaDocenteDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IExperienciaDocenteRemote extends IServiceRemote {
	public void actualizarExperienciaDocente(ExperienciaDocenteDTO experienciaDocente, UsuarioDTO usuarioRegistro) throws SIGEP2SistemaException;
	public int obtenerFilasExperienciaDocente(UsuarioDTO cod_Persona);
	public List<ExperienciaDocenteDTO> listarExperienciaDocente(int first, int pageSize, UsuarioDTO cod_persona);
	public ExperienciaDocenteDTO obtenerExperienciaDocenteByCodExpDocente(long codExpDocente);
	boolean eliminarExperienciaDocente(long codExperienciaDocente) throws SIGEP2SistemaException;
}
