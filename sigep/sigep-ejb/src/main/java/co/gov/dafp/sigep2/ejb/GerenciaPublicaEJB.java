package co.gov.dafp.sigep2.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import co.gov.dafp.sigep2.entity.jpa.seguridad.DatoAdicional;
import co.gov.dafp.sigep2.entity.jpa.seguridad.EvaluacionDesempeno;
import co.gov.dafp.sigep2.entity.jpa.seguridad.LogroRecurso;
import co.gov.dafp.sigep2.entity.jpa.seguridad.ParticipacionInstitucion;
import co.gov.dafp.sigep2.entity.jpa.seguridad.ParticipacionProyecto;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Persona;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Publicacion;
import co.gov.dafp.sigep2.entity.seguridad.DatoAdicionalDTO;
import co.gov.dafp.sigep2.entity.seguridad.EvaluacionDesempenoDTO;
import co.gov.dafp.sigep2.entity.seguridad.FormacionTrabajoDTO;
import co.gov.dafp.sigep2.entity.seguridad.LogroRecursoDTO;
import co.gov.dafp.sigep2.entity.seguridad.ParticipacionInstitucionDTO;
import co.gov.dafp.sigep2.entity.seguridad.ParticipacionProyectoDTO;
import co.gov.dafp.sigep2.entity.seguridad.PublicacionDTO;
import co.gov.dafp.sigep2.entity.seguridad.ReconocimientoDTO;
import co.gov.dafp.sigep2.entity.view.CabezaFamiliaDTO;
import co.gov.dafp.sigep2.entity.view.DesplazamientoDTO;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;
import co.gov.dafp.sigep2.entity.view.ProduccionBibliograficaDTO;
import co.gov.dafp.sigep2.entity.view.TiposLibroInvestigacionDTO;
import co.gov.dafp.sigep2.entity.view.TiposRevistaDTO;
import co.gov.dafp.sigep2.factoria.CabezaFamiliaFactoria;
import co.gov.dafp.sigep2.factoria.DatoAdicionalFactoria;
import co.gov.dafp.sigep2.factoria.DesplazamientoFactoria;
import co.gov.dafp.sigep2.factoria.EvaluacionDesempenoFactoria;
import co.gov.dafp.sigep2.factoria.LogroRecursoFactoria;
import co.gov.dafp.sigep2.factoria.OrientacionSexualFactoria;
import co.gov.dafp.sigep2.factoria.ParticipacionInstitucionFactoria;
import co.gov.dafp.sigep2.factoria.ParticipacionProyectoFactoria;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.PublicacionFactoria;
import co.gov.dafp.sigep2.factoria.TiposLibroInvestigacionFactoria;
import co.gov.dafp.sigep2.factoria.TiposRevistaFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioEntidadFactoria;
import co.gov.dafp.sigep2.interfaces.IGerenciaPublicaRemote;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.factoria.ProduccionBibliograficaFactoria;


@Stateless
public class GerenciaPublicaEJB implements IGerenciaPublicaRemote{
	private static final long serialVersionUID = -3345538355024286776L;
	private static final Logger logger = Logger.getInstance(GerenciaPublicaEJB.class);
	
	@EJB
	EvaluacionDesempenoFactoria evaluacionDesempenoFactoria;
	@EJB
	UsuarioEntidadFactoria usuarioEntidadFactoria;
	@EJB
	DatoAdicionalFactoria datoAdicionalFactoria;
	@EJB
	PersonaFactoria personaFactoria;
	@EJB
	CabezaFamiliaFactoria cabezaFamilia;
	@EJB
	DesplazamientoFactoria desplazamiento;
	@EJB
	OrientacionSexualFactoria orientacionSexual;
	@EJB
	LogroRecursoFactoria logroRecursoFactoria;
	@EJB
	PublicacionFactoria publicacionFactoria;
	@EJB
	TiposRevistaFactoria tiposRevistaFactoria;
	@EJB
	TiposLibroInvestigacionFactoria tiposLibroFactoria;
	@EJB
	ParticipacionProyectoFactoria participacionProyectoFactoria;
	@EJB
	ParticipacionInstitucionFactoria participacionInstitucionFactoria;
	@EJB
	ProduccionBibliograficaFactoria produccionBibliograficaFactoria;



	public Boolean guardarDatosAdicionales(long idPersona, DatoAdicionalDTO dato){
		Persona persona = personaFactoria.find(idPersona);
		DatoAdicional datoAdicional = datoAdicionalFactoria.consultarDatoAdicionalByCodPersona(idPersona);
		if(datoAdicional == null) {
			datoAdicional = new DatoAdicional();
			datoAdicional.setCodPersona(persona);
		}
		datoAdicional.setAudAccion(1l);
		datoAdicional.setAudCodRol(1l);
		datoAdicional.setAudFechaActualizacion(new Date());
		datoAdicional.setAudCodUsuario(1l);
		datoAdicional.setCodOrientacionSexual(datoAdicional.getCodOrientacionSexual());
		datoAdicional.setOtraOrientacionSexual(dato.getOtraOrientacionSexual());
		//datoAdicional.setCodCabezaHogar(dato.getCodCabezaHogar());;
		datoAdicional.setFlgVictimaConflicto(dato.getFlgVictimaConflicto());
		try {
			if(datoAdicional.getId()!= 0)		
				datoAdicionalFactoria.merge(datoAdicional, null);
			else 
				datoAdicionalFactoria.persist(datoAdicional, null);			
		} catch (SIGEP2SistemaException e) {
			logger.log().error("EJB - public void actualizarDatosContacto(PersonaDTO personaDTO, DatosContactoDTO datosContactoDTO, UsuarioDTO usuarioRegistro)");	
			return false;
		}
		return true;
	}	
	
	@Override
	public Boolean guardarRedesSociales(long idPersona, DatoAdicionalDTO datoDTO){
		Persona persona = personaFactoria.find(idPersona);
		DatoAdicional datoAdicional = datoAdicionalFactoria.consultarDatoAdicionalByCodPersona(idPersona);
		if(datoAdicional == null) {
			datoAdicional = new DatoAdicional();
			datoAdicional.setCodPersona(persona);
		}
		datoAdicional.setAudAccion(1L);
		datoAdicional.setAudCodRol(1l);
		datoAdicional.setAudFechaActualizacion(new Date());
		datoAdicional.setAudCodUsuario(1l);
		datoAdicional.setUrlFacebook(datoDTO.getUrlFacebook());
		datoAdicional.setUrlInstagram(datoDTO.getUrlInstagram());
		datoAdicional.setUrlLinkedin(datoDTO.getUrlLinkedin());
		datoAdicional.setUrlOtraRedSocial(datoDTO.getUrlOtraRedSocial());
		datoAdicional.setUrlPaginaWeb(datoDTO.getUrlPaginaWeb());
		datoAdicional.setUsuarioSkype(datoDTO.getUsuarioSkype());
		datoAdicional.setUrlSnapchat(datoDTO.getUrlSnapchat());
		datoAdicional.setUrlTwitter(datoDTO.getUrlTwitter());
		try {
			if(datoAdicional.getId()!= 0)		
				datoAdicionalFactoria.merge(datoAdicional, null);
			else 
				datoAdicionalFactoria.persist(datoAdicional, null);			
		} catch (SIGEP2SistemaException e) {
			logger.log().error("EJB - public void actualizarDatosContacto(PersonaDTO personaDTO, DatosContactoDTO datosContactoDTO, UsuarioDTO usuarioRegistro)");	
			return false;
		}
		return true;
	}
	
	public Boolean guardarReconocimiento(long idPersona, ReconocimientoDTO dato) {
		return null;
	}
	
	public Boolean guardarLogroRecurso(long idPersona, LogroRecursoDTO logro){
		Persona persona = personaFactoria.find(idPersona);
		LogroRecurso logroRecurso = logroRecursoFactoria.obtenerLogroRecursoPorCodPersona(idPersona);
		if(logroRecurso == null) {
			logroRecurso = new LogroRecurso();
			logroRecurso.setCodPersona(idPersona);
		}
		logroRecurso.setAudAccion(1l);
		logroRecurso.setAudCodRol(1l);
		logroRecurso.setAudFechaActualizacion(new Date());
		logroRecurso.setAudCodUsuario(1l);
		logroRecurso.setflgAdministraRecursos(logro.getFlgRecursoEconomico());
		logroRecurso.setFlgPersonasCargo(logro.getFlgPersonasCargo());
		logroRecurso.setNumPersonasCargo(logro.getNumPersonasCargo());
		logroRecurso.setNumEmpleados(logro.getNumEmpleados());
		logroRecurso.setvalorRecursoEconomico(logro.getValorRecursoEconomico());
		logroRecurso.setFlgPersonasCargo(logro.getFlgPersonasCargo());
		logroRecurso.setNombreEntidad(logro.getNombreEntidad());
		logroRecurso.setLogroSobresaliente(logro.getLogroSobresaliente());		
		try {
			if(logroRecurso.getId()!= 0)		
				logroRecursoFactoria.merge(logroRecurso, null);
			else 
				logroRecursoFactoria.persist(logroRecurso, null);			
		} catch (SIGEP2SistemaException e) {
			logger.log().error("EJB - public void actualizarDatosContacto(PersonaDTO personaDTO, DatosContactoDTO datosContactoDTO, UsuarioDTO usuarioRegistro)");	
			return false;
		}
		return true;
	}
	
	public Boolean guardarParticipacionInstitucion(long idPersona, ParticipacionInstitucionDTO participacion){
		Persona persona = personaFactoria.find(idPersona);
		ParticipacionInstitucion institucion = participacionInstitucionFactoria.obtenerParticipacionInstitucionPorCodPersona(idPersona);
		if(institucion == null) {
			institucion = new ParticipacionInstitucion();
			institucion.setCodPersona(idPersona);
		}
		institucion.setAudAccion(1l);
		institucion.setAudCodRol(1l);
		institucion.setAudFechaActualizacion(new Date());
		institucion.setAudCodUsuario(1l);
		institucion.setNombreInstitucion(participacion.getNombreInstitucion());
		institucion.setNombreRazonSocialInstitucion(participacion.getNombreRazonSocialInstitucion());
		institucion.setNombreEntidadOrganizacion(participacion.getNombreEntidadOrganizacion());
		
		try {
			if(institucion.getId()!= 0)		
				participacionInstitucionFactoria.merge(institucion, null);
			else 
				participacionInstitucionFactoria.persist(institucion, null);			
		} catch (SIGEP2SistemaException e) {
			logger.log().error("EJB - public void GuardarEvaluacionDesempeno(PersonaDTO personaDTO, DatosContactoDTO datosContactoDTO, UsuarioDTO usuarioRegistro)");	
			return false;
		}
		return true;
	}
	
	public Boolean guardarParticipacionProyecto(long idPersona, ParticipacionProyectoDTO participacion){
		Persona persona = personaFactoria.find(idPersona);
		ParticipacionProyecto proyecto = participacionProyectoFactoria.obtenerParticipacionProyectoPorPersona(idPersona);
		if(proyecto == null) {
			proyecto = new ParticipacionProyecto();
			proyecto.setCodPersona(idPersona);
		}
		proyecto.setAudAccion(1l);
		proyecto.setAudCodRol(1l);
		proyecto.setAudFechaActualizacion(new Date());
		proyecto.setAudCodUsuario(1l);
		proyecto.setNombreEntidad(participacion.getNombreEntidad());
		proyecto.setNombreProyecto(participacion.getNombreProyecto());
		proyecto.setRolLaborado(participacion.getRolLaborado());
		proyecto.setCodPais(participacion.getCodPais());
		proyecto.setCodDepartamento(participacion.getCodDepartamento());
		proyecto.setCodMunicipio(participacion.getCodMunicipio());
		proyecto.setFechaInicio(participacion.getFechaTerminacion());
		proyecto.setFechaTerminacion(participacion.getFechaTerminacion());
		try {
			if(proyecto.getId()!= 0)		
				participacionProyectoFactoria.merge(proyecto, null);
			else 
				participacionProyectoFactoria.persist(proyecto, null);			
		} catch (SIGEP2SistemaException e) {
			logger.log().error("EJB - public void guardarParticipacionProyecto(PersonaDTO personaDTO, DatosContactoDTO datosContactoDTO, UsuarioDTO usuarioRegistro)");	
			return false;
		}
		return true;
	}
	
	public Boolean guardarPublicacion(long idPersona, PublicacionDTO publicacion){
		Persona persona = personaFactoria.find(idPersona);
		Publicacion publica = publicacionFactoria.obtenerPublicacionPorCodPersona(idPersona);
		if(publica == null) {
			publica = new Publicacion();
			publica.setCodPersona(idPersona);
		}
		publica.setAudAccion(1l);
		publica.setAudCodRol(1l);
		publica.setAudFechaActualizacion(new Date());
		publica.setAudCodUsuario(1l);
		publica.setNombreArticulo(publicacion.getNombreArticulo());
		publica.setNombreLibro(publicacion.getNombreLibro());
		publica.setNombrePublicacion(publicacion.getNombrePublicacion());
		publica.setCodOtroTipoPublicacion(publicacion.getCodOtroTipoPublicacion());
		publica.setCodTipoArticulo(publicacion.getCodTipoArticulo());
		publica.setCodTipoPublicacion(publicacion.getCodTipoPublicacion());
		try {
			if(publica.getId()!= 0)		
				publicacionFactoria.merge(publica, null);
			else 
				publicacionFactoria.persist(publica, null);			
		} catch (SIGEP2SistemaException e) {
			logger.log().error("EJB - public void actualizarDatosContacto(PersonaDTO personaDTO, DatosContactoDTO datosContactoDTO, UsuarioDTO usuarioRegistro)");	
			return false;
		}
		return true;
	}
	
	public Boolean guardarFormacionTrabajo(long idPersona, FormacionTrabajoDTO formacion){
		return null;
	}
	
	public Boolean guardarEvaluacionDesempeno(long idPersona, EvaluacionDesempenoDTO evaluacion, long idEntidad){
		Persona persona = personaFactoria.find(idPersona);
		EvaluacionDesempeno eval = evaluacionDesempenoFactoria.obtenerPublicacionPorCodPersonaPeriodo(idPersona, idEntidad, evaluacion.getFechaInicio(), evaluacion.getFechaFin());
		if(eval == null) {
			eval = new EvaluacionDesempeno();
			eval.setCodPersona(idPersona);
		}
		eval.setAudAccion(1l);
		eval.setAudCodRol(1l);
		eval.setAudFechaActualizacion(new Date());
		eval.setAudCodUsuario(1l);
		eval.setFechaInicio(evaluacion.getFechaInicio());
		eval.setFechaFin(evaluacion.getFechaFin());
		eval.setCalificacionObtenida(evaluacion.getCalificacionObtenida());
		eval.setCargoEntidadPrivada(evaluacion.getCargoEntidadPrivada());
		eval.setCargoEntidadPublica(evaluacion.getCargoEntidadPublica());
		eval.setEscalaEvaluacion(evaluacion.getEscalaEvaluacion());
		eval.setCodEntidad(evaluacion.getCodEntidad());
		try {
			if(eval.getId()!= 0)		
				evaluacionDesempenoFactoria.merge(eval, null);
			else 
				evaluacionDesempenoFactoria.persist(eval, null);			
		} catch (SIGEP2SistemaException e) {
			logger.log().error("EJB - public void GuardarEvaluacionDesempeno(PersonaDTO personaDTO, DatosContactoDTO datosContactoDTO, UsuarioDTO usuarioRegistro)");	
			return false;
		}
		return true;
	}	
	
	public List<CabezaFamiliaDTO> obtenerParametricasCabezaFamilia() {
		return cabezaFamilia.obtenerParametricasCabezaFamilia();
	}
	
	public List<DesplazamientoDTO> obtenerParametricasDesplazamiento() {
		return desplazamiento.obtenerParametricasDesplazamiento();
	}
	
	public List<OrientacionSexualDTO> obtenerParametricasOrientacionSexual() {
		return orientacionSexual.obtenerParametricasOrientacionSexual();
	}

	public List<TiposRevistaDTO> obtenerParametricasTiposRevista() {
		return tiposRevistaFactoria.obtenerParametricasTiposRevista();
	}
	
	public List<TiposLibroInvestigacionDTO> obtenerParametricasLibrosInvestigacion() {
		return tiposLibroFactoria.obtenerParametricasTiposlibroInvestigacion();
	}
	
	public List<ProduccionBibliograficaDTO> obtenerParametricasProduccionBibliografica() {
		return produccionBibliograficaFactoria.obtenerParametricasProduccionBibliografica();
	}
	
	public LogroRecursoDTO obtenerLogroRecursoPorCodPersona(long idPersona){
		LogroRecursoDTO logroRecursoDTO = null;
		LogroRecurso logroRecurso = logroRecursoFactoria.obtenerLogroRecursoPorCodPersona(idPersona);
		if(logroRecurso != null) {
			logroRecursoDTO = (LogroRecursoDTO) logroRecurso.getDTO();
		}
		return logroRecursoDTO;
	}
	
	
	public PublicacionDTO obtenerArticulosPorPersona(long idPersona){
		PublicacionDTO publicacion = null;
		Publicacion publi = publicacionFactoria.obtenerPublicacionPorCodPersona(idPersona);
		
		if(publi != null) {
			publicacion = (PublicacionDTO) publi.getDTO();
		}
		return publicacion;
	}
	
	public EvaluacionDesempenoDTO obtenerEvaluacionPorPersona(long idPersona, long idEntidad, Date fechaInicio, Date fechaFin) {
		EvaluacionDesempenoDTO eval = null;
		EvaluacionDesempeno evaluacion = evaluacionDesempenoFactoria.obtenerPublicacionPorCodPersonaPeriodo(idPersona, idEntidad, fechaInicio, fechaFin);
		
		if(evaluacion != null) {
			eval = (EvaluacionDesempenoDTO) evaluacion.getDTO();
		}
		return eval;
	}
	
	public ParticipacionInstitucionDTO obtenerParticipacionInstitucionPersona(long codPersona) {
		ParticipacionInstitucionDTO participacionInstitucion = null;
		ParticipacionInstitucion participacion = participacionInstitucionFactoria.obtenerParticipacionInstitucionPorCodPersona(codPersona);
		
		if(participacion != null) {
			participacionInstitucion = (ParticipacionInstitucionDTO) participacion.getDTO();
		}
		return participacionInstitucion;
	}
	
	public EvaluacionDesempenoDTO obtenerEvaluacionPorPersona(long idPersona) {
		return null;
	}
	

}