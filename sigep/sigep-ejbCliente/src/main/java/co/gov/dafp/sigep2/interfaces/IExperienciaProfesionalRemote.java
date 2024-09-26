package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.CargoDTO;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaProfesionalDTO;
import co.gov.dafp.sigep2.entity.view.JornadaLaboralDTO;
import co.gov.dafp.sigep2.entity.view.MotivoRetiroDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Remote
public interface IExperienciaProfesionalRemote extends IServiceRemote {

	public List<ExperienciaProfesionalDTO> listarExperienciaProfesional(int first, int pageSize, long codPersona)
			throws SIGEP2SistemaException;

	public int filasExperienciaProfesionalPorPersona(long codPersona) throws SIGEP2SistemaException;

	public void crearExperienciaProfesional(ExperienciaProfesionalDTO experienciaProfesional, long usuarioRegistro)
			throws SIGEP2SistemaException;

	public List<CargoDTO> buscarCargoPorEntidad() throws SIGEP2SistemaException;
	
	public List<CargoDTO> buscarCargoPorEntidad(Long entidad) throws SIGEP2SistemaException;

	public List<MotivoRetiroDTO> buscarMotivoRetiro() throws SIGEP2SistemaException;

	public List<JornadaLaboralDTO> buscarJornadaLaboral() throws SIGEP2SistemaException;
	
	public boolean eliminarExperienciaProfesional(long codExperienciaProfesional) throws SIGEP2SistemaException;
	
	public ExperienciaProfesionalDTO buscarExperienciaProfesional(long codExperienciaProfesional) throws SIGEP2SistemaException;
}
