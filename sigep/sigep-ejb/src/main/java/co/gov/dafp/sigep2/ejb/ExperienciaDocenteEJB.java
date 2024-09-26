package co.gov.dafp.sigep2.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.view.AreaConocimiento;
import co.gov.dafp.sigep2.view.NivelEducativo;
import co.gov.dafp.sigep2.entity.jpa.comun.Departamento;
import co.gov.dafp.sigep2.entity.jpa.comun.Municipio;
import co.gov.dafp.sigep2.entity.jpa.comun.Pais;
import co.gov.dafp.sigep2.entity.jpa.seguridad.ExperienciaDocente;
import co.gov.dafp.sigep2.entity.jpa.seguridad.InstitucionEducativa;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaDocenteDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.factoria.AreaConocimientoFactoria;
import co.gov.dafp.sigep2.factoria.DepartamentoFactoria;
import co.gov.dafp.sigep2.factoria.ExperienciaDocenteFactoria;
import co.gov.dafp.sigep2.factoria.InstitucionFactoria;
import co.gov.dafp.sigep2.factoria.MunicipioFactoria;
import co.gov.dafp.sigep2.factoria.NivelEducativoFactoria;
import co.gov.dafp.sigep2.factoria.PaisFactoria;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.interfaces.IExperienciaDocenteRemote;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Stateless
public class ExperienciaDocenteEJB implements IExperienciaDocenteRemote {
	
	private static final long serialVersionUID = 1L;
	private  ExperienciaDocente experienciaDocente;
	private static final Logger logger = Logger.getInstance(DatosPersonalesEJB.class);
	private long cod_persona_buscar;

	@EJB
	ExperienciaDocenteFactoria experienciaDocenteFactoria;
	
	@EJB
	PersonaFactoria personaFactoria;
	
	@EJB
	PaisFactoria paisFactoria;
	
	@EJB
	DepartamentoFactoria departamentoFactoria;
	
	@EJB
	MunicipioFactoria municipioFactoria;
	
	@EJB
	NivelEducativoFactoria nivelEducativoFactoria;
	
	@EJB
	AreaConocimientoFactoria areaConocimientoFactoria;
	
	@EJB
	InstitucionFactoria institucionFactoria;
	
	@Override
	public void actualizarExperienciaDocente(ExperienciaDocenteDTO experienciaDocenteDTO, UsuarioDTO usuarioRegistro) throws SIGEP2SistemaException {
		System.out.println("LLegue hasta el EJB"+experienciaDocenteDTO);
		if(experienciaDocenteDTO.getId() != 0) {
			System.out.println("Voy a Actualizar una experiencia Docente");
			experienciaDocente = experienciaDocenteFactoria.find(experienciaDocenteDTO.getId());
					
		}else {
			System.out.println("Voy a crear una nueva experiencia");
			experienciaDocente = new ExperienciaDocente();
			
			System.out.println("Valor del codigo Persona " + usuarioRegistro.getCodPersona());
		}
		
		Pais pais = paisFactoria.convertirPaisDTO(experienciaDocenteDTO.getCodPais());
		Departamento departamento = departamentoFactoria.convertirDepartamentoDTO(experienciaDocenteDTO.getCodDepartamento());
		Municipio municipio = municipioFactoria.convertirMunicipioDTO(experienciaDocenteDTO.getCodCiudad());
		NivelEducativo nivelEducativo = nivelEducativoFactoria.convertirNivelEducativoDTO(experienciaDocenteDTO.getNivelEducativo());
		AreaConocimiento areaConocimiento = areaConocimientoFactoria.convertirAreaConocimientoDTO(experienciaDocenteDTO.getAreaConocimiento());
		InstitucionEducativa institucion =  institucionFactoria.convertirInstitucionEducativaDTO(experienciaDocenteDTO.getCodInstitucion());
		
		experienciaDocente.setCodPersona(usuarioRegistro.getCodPersona());
		experienciaDocente.setCodPais(pais);
		experienciaDocente.setCodDepartamento(departamento);
		experienciaDocente.setCodCiudad(municipio);
		experienciaDocente.setNivelEducativo(nivelEducativo);
		experienciaDocente.setAreaConocimiento(areaConocimiento);
		experienciaDocente.setCodInstitucion(institucion);
		experienciaDocente.setNombreInstitucion(experienciaDocenteDTO.getNombreInstitucion());
		experienciaDocente.setFechaIngreso(experienciaDocenteDTO.getFechaIngreso());
		experienciaDocente.setIsActivaActualmente(experienciaDocenteDTO.getFlgActualmente());
		experienciaDocente.setFechaFinalizacion(experienciaDocenteDTO.getFechaFinalizacion());
		experienciaDocente.setDireccion(experienciaDocenteDTO.getDireccion());
		experienciaDocente.setTelefono(experienciaDocenteDTO.getTelefono());
		experienciaDocente.setHorasSemana(experienciaDocenteDTO.getHorasSemana());
		experienciaDocente.setMateriaImpartida(experienciaDocenteDTO.getMateriaImpartida());
		experienciaDocente.setUrlDocumentoSoporte(experienciaDocenteDTO.getUrlDocumentoSoporte());
		experienciaDocente.setFlgVerificado(experienciaDocenteDTO.getFlgVerificado());
		experienciaDocente.setAudCodUsuario(usuarioRegistro.getId());
		experienciaDocente.setAudCodRol(1l);		
		experienciaDocente.setAudAccion(1l);
		experienciaDocente.setAudFechaActualizacion(DateUtils.getFechaSistema());
		
		try {
			if(experienciaDocenteDTO.getId()!= 0){
				experienciaDocenteFactoria.merge(experienciaDocente, null);
			}else {
				experienciaDocenteFactoria.persist(experienciaDocente, null);
			}
			
		} catch (SIGEP2SistemaException e) {
			logger.log().error("EJB - crearExperienciaDocente(ExperienciaDocenteDTO experienciaDocenteDTO)");
			throw new SIGEP2SistemaException(e.getMessage());
		}	
	}


	@Override
	public int obtenerFilasExperienciaDocente(UsuarioDTO cod_Persona) {
		cod_persona_buscar = cod_Persona.getCodPersona();
		return experienciaDocenteFactoria.obtenerFilasExperienciaDocente(cod_persona_buscar);
	}


	@Override
	public List<ExperienciaDocenteDTO> listarExperienciaDocente(int first, int pageSize, UsuarioDTO cod_persona) {
		cod_persona_buscar = cod_persona.getCodPersona();
		return (List<ExperienciaDocenteDTO>) experienciaDocenteFactoria.listarExperienciaDocente(first, pageSize,cod_persona_buscar);
	}


	@Override
	public ExperienciaDocenteDTO obtenerExperienciaDocenteByCodExpDocente(long codExpDocente) {
		return experienciaDocenteFactoria.obtenerExperienciaDocenteByCodExpDocente(codExpDocente);
	}
	
	@Override
	public boolean eliminarExperienciaDocente(long codExperienciaDocente) throws SIGEP2SistemaException {
		boolean experienciaEliminada=false;
		try {
			experienciaEliminada = experienciaDocenteFactoria.eliminarExperienciaDocente(codExperienciaDocente);
			return experienciaEliminada;
		}catch(	Exception e){
			logger.log().error("EJB - eliminarExperienciaDocente(long codExperienciaDocente)");
			experienciaEliminada = false;
		}return false;
	}


}
