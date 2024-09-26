package co.gov.dafp.sigep2.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.comun.Departamento;
import co.gov.dafp.sigep2.entity.jpa.comun.Municipio;
import co.gov.dafp.sigep2.entity.jpa.comun.Pais;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Cargo;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Entidad;
import co.gov.dafp.sigep2.entity.jpa.seguridad.ExperienciaProfesional;
import co.gov.dafp.sigep2.entity.seguridad.CargoDTO;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaProfesionalDTO;
import co.gov.dafp.sigep2.entity.view.JornadaLaboralDTO;
import co.gov.dafp.sigep2.entity.view.MotivoRetiroDTO;
import co.gov.dafp.sigep2.factoria.CargoFactoria;
import co.gov.dafp.sigep2.factoria.DepartamentoFactoria;
import co.gov.dafp.sigep2.factoria.EntidadFactoria;
import co.gov.dafp.sigep2.factoria.ExperienciaProfesionalFactoria;
import co.gov.dafp.sigep2.factoria.JornadaLaboralFactoria;
import co.gov.dafp.sigep2.factoria.MotivoRetiroFactoria;
import co.gov.dafp.sigep2.factoria.MunicipioFactoria;
import co.gov.dafp.sigep2.factoria.PaisFactoria;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.TipoEntidadFactoria;
import co.gov.dafp.sigep2.interfaces.IExperienciaProfesionalRemote;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.view.JornadaLaboral;
import co.gov.dafp.sigep2.view.MotivoRetiro;
import co.gov.dafp.sigep2.view.TipoEntidad;

@Stateless
public class ExperienciaProfesionalEJB implements IExperienciaProfesionalRemote {

	private static final long serialVersionUID = 2103522692436337390L;
	private static final Logger logger = Logger.getInstance(ExperienciaProfesionalEJB.class);

	@EJB
	ExperienciaProfesionalFactoria experienciaProfesionalFactoria;

	@EJB
	TipoEntidadFactoria tipoEntidadFactoria;

	@EJB
	PersonaFactoria personaFactoria;

	@EJB
	EntidadFactoria entidadFactoria;

	@EJB
	PaisFactoria paisFactoria;

	@EJB
	DepartamentoFactoria departamentoFactoria;

	@EJB
	MunicipioFactoria municipioFactoria;

	@EJB
	CargoFactoria cargoFactoria;

	@EJB
	MotivoRetiroFactoria motivoRetiroFactoria;

	@EJB
	JornadaLaboralFactoria jornadaLaboralFactoria;

	@EJB
	CargoFactoria dependenciaFactoria;

	private ExperienciaProfesional experienciaProfesional;

	public void crearExperienciaProfesional(ExperienciaProfesionalDTO experienciaProfesionalDTO, long usuarioRegistro)
			throws SIGEP2SistemaException {
		if (experienciaProfesionalDTO.getId() != 0) {
			experienciaProfesional = experienciaProfesionalFactoria.find(experienciaProfesionalDTO.getId());
		} else {
			experienciaProfesional = new ExperienciaProfesional();
		}

		TipoEntidad tipoEntidad = tipoEntidadFactoria.converterTipoEntidadDTO(experienciaProfesionalDTO.getCodTipoEntidad());
		Pais paisEntidad = paisFactoria.convertirPaisDTO(experienciaProfesionalDTO.getCodPaisEntidad());
		Departamento departamentoEntidad = departamentoFactoria.convertirDepartamentoDTO(experienciaProfesionalDTO.getCodDepartamentoEntidad());
		Municipio municipioEntidad = municipioFactoria.convertirMunicipioDTO(experienciaProfesionalDTO.getCodMunicipioEntidad());
		Entidad nombreEntidad = entidadFactoria.convertirEntidadDTO(experienciaProfesionalDTO.getnombreEntidad());
		JornadaLaboral jornadaLaboral = jornadaLaboralFactoria.convertirJornadaLaboralDTO(experienciaProfesionalDTO.getCodJornadaLaboral());
		MotivoRetiro motivoRetiro = motivoRetiroFactoria.convertirMotivoRetiroDTO(experienciaProfesionalDTO.getCodMotivoRetiro());
		Cargo cargoEntidad = cargoFactoria.convertirCargoEntidad(experienciaProfesionalDTO.getCargoEntidad());
		Cargo dependenciaEntidad = cargoFactoria.convertirCargoEntidad(experienciaProfesionalDTO.getDependencia());

		experienciaProfesional.setCodPersona(usuarioRegistro);
		experienciaProfesional.setCodTipoEntidad(tipoEntidad.getId());
		experienciaProfesional.setnombreEntidad(nombreEntidad.getNombreEntidad());
		experienciaProfesional.setCodPaisEntidad(paisEntidad);
		experienciaProfesional.setCodDepartamentoEntidad(departamentoEntidad);
		experienciaProfesional.setCodMunicipioEntidad(municipioEntidad);
		experienciaProfesional.setDireccionEntidad(experienciaProfesionalDTO.getDireccionEntidad());
		experienciaProfesional.setIndicativoTelefono(experienciaProfesionalDTO.getIndicativoTelefono());
		experienciaProfesional.setTelefonoEntidad(experienciaProfesionalDTO.getTelefonoEntidad());
		experienciaProfesional.setDependencia(dependenciaEntidad.getNombreCargo());
		experienciaProfesional.setCargoEntidad(cargoEntidad.getNombreCargo());
		experienciaProfesional.setFechaIngreso(experienciaProfesionalDTO.getFechaIngreso());
		experienciaProfesional.setIsActivaEntidad(experienciaProfesionalDTO.getIsActivaEntidad());
		experienciaProfesional.setFechaRetiro(experienciaProfesionalDTO.getFechaRetiro());
		experienciaProfesional.setCodJornadaLaboral(jornadaLaboral);
		experienciaProfesional.setHorasPromedioMes(experienciaProfesionalDTO.getHorasPromedioMes());
		experienciaProfesional.setCodMotivoRetiro(motivoRetiro);
		experienciaProfesional.setAudCodUsuario(usuarioRegistro);
		experienciaProfesional.setAudCodRol(1l);
		experienciaProfesional.setAudAccion(1l);
		experienciaProfesional.setAudFechaActualizacion(DateUtils.getFechaSistema());

		try {
			if (experienciaProfesionalDTO.getId() != 0) {
				experienciaProfesionalFactoria.merge(experienciaProfesional, null);
			} else {
				experienciaProfesionalFactoria.persist(experienciaProfesional, null);
			}
		} catch (Exception e) {
			e.getStackTrace();
			logger.log().error("EJB - crearExperienciaDocente(ExperienciaDocenteDTO experienciaDocenteDTO)");
			throw new SIGEP2SistemaException("error al intentar registrar la experiencia laboral por codigo persona :"
					+ experienciaProfesionalDTO.getCodPersona() + " " + experienciaProfesionalDTO.getAudCodUsuario(),
					e);
		}
	}

	@Override
	public List<CargoDTO> buscarCargoPorEntidad() throws SIGEP2SistemaException {
		return cargoFactoria.buscarCargoPorEntidad();
	}

	@Override
	public List<CargoDTO> buscarCargoPorEntidad(Long entidad) throws SIGEP2SistemaException {
		return cargoFactoria.buscarCargoPorEntidad(entidad);
	}

	@Override
	public List<MotivoRetiroDTO> buscarMotivoRetiro() throws SIGEP2SistemaException {
		return motivoRetiroFactoria.buscarMotivoRetiro();
	}

	@Override
	public List<JornadaLaboralDTO> buscarJornadaLaboral() throws SIGEP2SistemaException {
		return jornadaLaboralFactoria.buscarJornadaLaboral();
	}

	@Override
	public int filasExperienciaProfesionalPorPersona(long codPersona) throws SIGEP2SistemaException {
		return experienciaProfesionalFactoria.filasExperienciaProfesionalPorPersona(codPersona);
	}

	@Override
	public List<ExperienciaProfesionalDTO> listarExperienciaProfesional(int first, int pageSize, long codPersona)
			throws SIGEP2SistemaException {
		return (List<ExperienciaProfesionalDTO>) experienciaProfesionalFactoria.listarExperienciaProfesional(first,
				pageSize, codPersona);
	}

	@Override
	public boolean eliminarExperienciaProfesional(long codExperienciaProfesional) throws SIGEP2SistemaException {
		boolean experienciaEliminada = false;
		try {
			experienciaEliminada = experienciaProfesionalFactoria
					.eliminarExperienciaProfesional(codExperienciaProfesional);
			return experienciaEliminada;
		} catch (Exception e) {
			logger.log().error("EJB - liminarExperienciaProfesional(long codExperienciaProfesional)");
			experienciaEliminada = false;
		}
		return false;
	}

	@Override
	public ExperienciaProfesionalDTO buscarExperienciaProfesional(long codExperienciaProfesional) throws SIGEP2SistemaException {
		System.out.println("Estoy en el EJB " + codExperienciaProfesional);
		return experienciaProfesionalFactoria.buscarExperienciaProfesional(codExperienciaProfesional);
	}
}