package co.gov.dafp.sigep2.deledago;

import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.seguridad.CargoDTO;
import co.gov.dafp.sigep2.entity.seguridad.DatoAdicionalDTO;
import co.gov.dafp.sigep2.entity.seguridad.EvaluacionDesempenoDTO;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaDocenteDTO;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaProfesionalDTO;
import co.gov.dafp.sigep2.entity.seguridad.FormacionTrabajoDTO;
import co.gov.dafp.sigep2.entity.seguridad.InstitucionEducativaDTO;
import co.gov.dafp.sigep2.entity.seguridad.LogroRecursoDTO;
import co.gov.dafp.sigep2.entity.seguridad.ParticipacionInstitucionDTO;
import co.gov.dafp.sigep2.entity.seguridad.ParticipacionProyectoDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.PublicacionDTO;
import co.gov.dafp.sigep2.entity.seguridad.ReconocimientoDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.CabezaFamiliaDTO;
import co.gov.dafp.sigep2.entity.view.DesplazamientoDTO;
import co.gov.dafp.sigep2.entity.view.HojaDeVidaDTO;
import co.gov.dafp.sigep2.entity.view.JornadaLaboralDTO;
import co.gov.dafp.sigep2.entity.view.MotivoRetiroDTO;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;
import co.gov.dafp.sigep2.entity.view.ProduccionBibliograficaDTO;
import co.gov.dafp.sigep2.entity.view.TipoDiscapacidadDTO;
import co.gov.dafp.sigep2.entity.view.TipoInstitucionDTO;
import co.gov.dafp.sigep2.entity.view.TipoLetraDTO;
import co.gov.dafp.sigep2.entity.view.TipoOrientacionDTO;
import co.gov.dafp.sigep2.entity.view.TipoViaDTO;
import co.gov.dafp.sigep2.entity.view.TiposLibroInvestigacionDTO;
import co.gov.dafp.sigep2.entity.view.TiposRevistaDTO;
import co.gov.dafp.sigep2.interfaces.IDatosPersonalesRemote;
import co.gov.dafp.sigep2.interfaces.IEvaluacionDesempeno;
import co.gov.dafp.sigep2.interfaces.IExperienciaDocenteRemote;
import co.gov.dafp.sigep2.interfaces.IExperienciaProfesionalRemote;
import co.gov.dafp.sigep2.interfaces.IGerenciaPublicaRemote;
import co.gov.dafp.sigep2.interfaces.IHojaDeVidaRemote;
import co.gov.dafp.sigep2.interfaces.IServiceRemote;
import co.gov.dafp.sigep2.sistema.locator.ServiceLocator;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

public class HojaDeVidaDelegate {

	private static final Logger logger = Logger.getInstance(HojaDeVidaDelegate.class);

	private static final String DATOS_PERSONALES_EJB = "DatosPersonalesEJB";
	private static final String HOJA_VIDA_EJB = "HojaDeVidaEJB";
	private static final String EXPERIENCIA_PROFESIONAL_EJB = "ExperienciaProfesionalEJB";
	private static final String EXPERIENCIA_DOCENTE_EJB = "ExperienciaDocenteEJB";
	private static final String GERENCIA_PUBLICA_EJB = "GerenciaPublicaEJB";
	private static final String EVALUACION_DESEMPENO_EJB = "EvaluacionDesempenoEJB";

	private static IDatosPersonalesRemote getDatosPersonalesService() throws SIGEP2SistemaException {
		IDatosPersonalesRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IDatosPersonalesRemote.class.getName();
			commonService = (IDatosPersonalesRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + DATOS_PERSONALES_EJB + "!" + pakageClassName);
			if (commonService == null) {
				throw new SIGEP2SistemaException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO));
			}
		} catch (NamingException e) {
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO), e);
		}
		return commonService;
	}

	private static IHojaDeVidaRemote getHojaDeVidaService() throws SIGEP2SistemaException {
		IHojaDeVidaRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IHojaDeVidaRemote.class.getName();
			commonService = (IHojaDeVidaRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + HOJA_VIDA_EJB + "!" + pakageClassName);
			if (commonService == null) {
				throw new SIGEP2SistemaException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO));
			}
		} catch (NamingException e) {
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO), e);
		}
		return commonService;
	}

	private static IExperienciaProfesionalRemote getExperienciaProfesionalService() throws SIGEP2SistemaException {
		IExperienciaProfesionalRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IExperienciaProfesionalRemote.class.getName();
			commonService = (IExperienciaProfesionalRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + EXPERIENCIA_PROFESIONAL_EJB + "!" + pakageClassName);
			if (commonService == null) {
				throw new SIGEP2SistemaException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO));
			}
		} catch (NamingException e) {
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO), e);
		}
		return commonService;
	}

	private static IExperienciaDocenteRemote getExperienciaDocenteService() throws SIGEP2SistemaException {
		IExperienciaDocenteRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IExperienciaDocenteRemote.class.getName();
			commonService = (IExperienciaDocenteRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + EXPERIENCIA_DOCENTE_EJB + "!" + pakageClassName);
			if (commonService == null) {
				throw new SIGEP2SistemaException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO));
			}
		} catch (NamingException e) {
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO), e);
		}
		return commonService;
	}

	public static PersonaDTO findPersonaId(long id) throws SIGEP2SistemaException {
		String msg = "static PersonaDTO findPersonaId()";
		IDatosPersonalesRemote service = getDatosPersonalesService();
		try {
			return service.findPersonaId(id);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<PaisDTO> findNacionalidadByCodPersonaId(long codPersona) throws SIGEP2SistemaException {
		String msg = "static List<PaisDTO> findNacionalidadByCodPersonaId(long codPersona)";
		IDatosPersonalesRemote service = getDatosPersonalesService();
		try {
			return service.findNacionalidadByCodPersonaId(codPersona);
		} catch (Exception e) {
			logger.warn(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static void actualizarDatosDemograficos(long codPersona, List<PaisDTO> nacionalidad,
			UsuarioDTO usuarioRegistro, long rolUsuario) throws SIGEP2SistemaException {
		String msg = "static void actualizarDatosDemograficos(PersonaDTO personaDTO, List<PaisDTO> nacionalidad, UsuarioDTO usuarioRegistro)";
		IDatosPersonalesRemote service = getDatosPersonalesService();
		try {
			service.actualizarDatosDemograficos(codPersona, nacionalidad, usuarioRegistro, rolUsuario);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<PersonaDTO> listarPersonasConHojaDeVida(int first, int pageSize, PersonaDTO persona,
			UsuarioDTO usuario, long codEntidadSesion, String codTipoAsociacion, String sortField, String sortOrder) {
		IHojaDeVidaRemote service;
		try {
			service = getHojaDeVidaService();
			return service.listarPersonasConHojaDeVida(first, pageSize, persona, usuario, codEntidadSesion,codTipoAsociacion,sortField,sortOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int filasPersonasConHV(PersonaDTO persona, UsuarioDTO usuario, long codEntidadSesion,
			String codTipoAsociacion) {
		IHojaDeVidaRemote service;
		try {
			service = getHojaDeVidaService();
			return service.filasPersonasConHV(persona, usuario, codEntidadSesion, codTipoAsociacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static List<HojaDeVidaDTO> detallesHojaDeVida(String personaId) throws SIGEP2SistemaException {
		String msg = "static List<HojaDeVidaDTO> detallesHojaDeVida(String personaId)";
		IHojaDeVidaRemote service = getHojaDeVidaService();
		try {
			return service.detallesHojaDeVida(personaId);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e.getMessage() + msg);
		}
	}

	public static List<TipoDiscapacidadDTO> findTipoDiscapacidad() throws SIGEP2SistemaException {
		String msg = "static List<TipoDiscapacidadDTO> findTipoDiscapacidad()";
		IDatosPersonalesRemote service = getDatosPersonalesService();
		try {
			return service.findTipoDiscapacidad();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static void actualizarExperienciaDocente(ExperienciaDocenteDTO experienciaDocenteDTO,
			UsuarioDTO usuarioRegistro) throws SIGEP2SistemaException {
		String msg = "Delegate: actualizarExperienciaDocente(ExperienciaDocenteDTO experienciaDocenteDTO, UsuarioDTO usuarioRegistro)";
		IExperienciaDocenteRemote service = getExperienciaDocenteService();
		try {
			service.actualizarExperienciaDocente(experienciaDocenteDTO, usuarioRegistro);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static void obtenerExperienciaDocenteByCodExpDocente(long codExpDocente) throws SIGEP2SistemaException {
		String msg = "Delegate: obtenerExperienciaDocenteByCodExpDocente(long codExpDocente) throws SIGEP2SistemaException";
		IExperienciaDocenteRemote service = getExperienciaDocenteService();
		try {
			service.obtenerExperienciaDocenteByCodExpDocente(codExpDocente);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static int obtenerFilasExperienciaDocente(UsuarioDTO cod_Persona) throws SIGEP2SistemaException {

		String msg = "Llegue hasta Delegate: obtenerFilasExperienciaDocente";
		IExperienciaDocenteRemote service = getExperienciaDocenteService();
		try {
			return service.obtenerFilasExperienciaDocente(cod_Persona);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e + " " + msg);
		}
	}

	public static void crearExperienciaProfesional(ExperienciaProfesionalDTO experienciaProfesionalDTO,
			long usuarioRegistro) throws SIGEP2SistemaException {
		String msg = "static void crearExperienciaProfesional(ExperienciaProfesionalDTO experienciaProfesionalDTO, UsuarioDTO usuarioRegistro)";
		IExperienciaProfesionalRemote service = getExperienciaProfesionalService();
		try {
			service.crearExperienciaProfesional(experienciaProfesionalDTO, usuarioRegistro);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<ExperienciaDocenteDTO> listarExperienciaDocente(int first, int pageSize, UsuarioDTO cod_Persona)
			throws SIGEP2SistemaException {
		IExperienciaDocenteRemote service = getExperienciaDocenteService();
		try {
			return service.listarExperienciaDocente(first, pageSize, cod_Persona);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<ExperienciaProfesionalDTO> listarExperienciaProfesional(int first, int pageSize, long codPersona)
			throws SIGEP2SistemaException {
		// List<ExperienciaProfesionalDTO> listaExperienciaLaboral;

		String msg = "List<ExperienciaProfesionalDTO> listarExperienciaProfesional(int first, int pageSize,UsuarioDTO codPersona)";
		IExperienciaProfesionalRemote service = getExperienciaProfesionalService();
		try {
			// listaExperienciaLaboral = service.listarExperienciaProfesional(first,
			// pageSize, codPersona);
			return service.listarExperienciaProfesional(first, pageSize, codPersona);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e + " " + msg);
		}
	}

	public static int filasExperienciaProfesionalPorPersona(long codPersona) throws SIGEP2SistemaException {
		String msg = "static int filasExperienciaProfesionalPorPersona(UsuarioDTO codPersona)";
		IExperienciaProfesionalRemote service = getExperienciaProfesionalService();
		try {
			return service.filasExperienciaProfesionalPorPersona(codPersona);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e + " " + msg);
		}
	}

	public static List<PersonaDTO> obtenerUsuariosFiltros(long tipoDoc, String doc, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido, Boolean busquedaDocumento)
			throws SIGEP2SistemaException {
		IDatosPersonalesRemote servicio = getDatosPersonalesService();
		try {

			logger.info("dentro del Hoja de vida Delegate");
			return servicio.obtenerUsuariosFiltros(tipoDoc, doc, primerNombre, segundoNombre, primerApellido,
					segundoApellido, busquedaDocumento);
		} catch (Exception ex) {

			logger.info("Eror dentro de Hoja de vida Delegate");
		}
		return null;
	}

	public static List<TipoViaDTO> buscarTipoVia() throws SIGEP2SistemaException {
		String msg = "static List<ViasDTO> buscarTipoVia()";
		IHojaDeVidaRemote service = getHojaDeVidaService();
		try {
			return service.buscarTipoVia();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<MotivoRetiroDTO> buscarMotivoRetiro() throws SIGEP2SistemaException {
		String msg = "static List<MotivoRetiroDTO> buscarMotivoRetiro()";
		IExperienciaProfesionalRemote service = getExperienciaProfesionalService();
		try {
			return service.buscarMotivoRetiro();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<JornadaLaboralDTO> buscarJornadaLaboral() throws SIGEP2SistemaException {
		String msg = "static List<JornadaLaboralDTO> busarJornadaLaboral()";
		IExperienciaProfesionalRemote service = getExperienciaProfesionalService();
		try {
			return service.buscarJornadaLaboral();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<TipoOrientacionDTO> buscarTipoOrientacion() throws SIGEP2SistemaException {
		String msg = "static List<ViasDTO> buscarTipoOrientacion()";
		IHojaDeVidaRemote service = getHojaDeVidaService();
		try {
			return service.buscarTipoOrientacion();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<TipoLetraDTO> buscarTipoLetra() throws SIGEP2SistemaException {
		String msg = "static List<ViasDTO> buscarTipoLetra()";
		IHojaDeVidaRemote service = getHojaDeVidaService();
		try {
			return service.buscarTipoLetra();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<CargoDTO> buscarCargoPorEntidad() throws SIGEP2SistemaException {
		String msg = "static List<CargoDTO> buscarCargoPorEntidad()";
		IExperienciaProfesionalRemote service = getExperienciaProfesionalService();
		try {
			return service.buscarCargoPorEntidad();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	private static IGerenciaPublicaRemote getFormularioGerenciaPublicaService() throws SIGEP2SistemaException {
		IGerenciaPublicaRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IGerenciaPublicaRemote.class.getName();
			commonService = (IGerenciaPublicaRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + GERENCIA_PUBLICA_EJB + "!" + pakageClassName);
			if (commonService == null) {
				throw new SIGEP2SistemaException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO));
			}
		} catch (NamingException e) {
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO), e);
		}
		return commonService;
	}

	public static Boolean guardarRedeSociales(long idPersona, DatoAdicionalDTO dato) throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.guardarRedesSociales(idPersona, dato);
		} catch (Exception ex) {
			return null;
		}
	}

	public static List<HojaDeVidaDTO> datosEducacionHojaDeVida(String id) throws SIGEP2SistemaException {
		String msg = "static List<HojaDeVidaDTO> datosDeEducacionHojaDeVida(String id)";
		IHojaDeVidaRemote service = getHojaDeVidaService();
		try {
			return service.datosDeEducacionHojaDeVida(id);
		} catch (Exception e) {
			throw new SIGEP2SistemaException(e.getMessage() + msg);
		}
	}

	public static Boolean guardarReconocimiento(long idPersona, ReconocimientoDTO premio)
			throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			service.guardarReconocimiento(idPersona, premio);
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static Boolean guardarLogroRecurso(long idPersona, LogroRecursoDTO logro) throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			service.guardarLogroRecurso(idPersona, logro);
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static Boolean guardarParticipacionInstitucion(long idPersona, ParticipacionInstitucionDTO dato)
			throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			service.guardarParticipacionInstitucion(idPersona, dato);
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static Boolean guardarParticipacionProyecto(long idPersona, ParticipacionProyectoDTO dato)
			throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			service.guardarParticipacionProyecto(idPersona, dato);
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static Boolean guardarPublicacion(long idPersona, PublicacionDTO dato) throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			service.guardarPublicacion(idPersona, dato);
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static Boolean guardarDatosAdicionales(long idPersona, DatoAdicionalDTO dato) throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			service.guardarDatosAdicionales(idPersona, dato);
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static Boolean guardarFormacionTrabajo(long idPersona, FormacionTrabajoDTO dato)
			throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			service.guardarFormacionTrabajo(idPersona, dato);
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static Boolean guardarEvaluacionDesempeno(long idPersona, EvaluacionDesempenoDTO dato, long idEntidad)
			throws SIGEP2SistemaException {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			service.guardarEvaluacionDesempeno(idPersona, dato, idEntidad);
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

	public static List<CabezaFamiliaDTO> obtenerParametricasCabezaFamilia() {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerParametricasCabezaFamilia();
		} catch (Exception ex) {
			return null;
		}
	}

	public static List<DesplazamientoDTO> obtenerParametricasDesplazamiento() {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerParametricasDesplazamiento();
		} catch (Exception ex) {
			return null;
		}
	}

	public static List<OrientacionSexualDTO> obtenerParametricasOrientacionSexual() {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerParametricasOrientacionSexual();
		} catch (Exception ex) {
			return null;
		}
	}

	public static boolean eliminarExperienciaProfesional(long codExperienciaProfesional) throws SIGEP2SistemaException {
		String msg = "static boolean eliminarExperienciaProfesional(long codExperienciaProfesional)";
		IExperienciaProfesionalRemote service = getExperienciaProfesionalService();
		try {
			return service.eliminarExperienciaProfesional(codExperienciaProfesional);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static boolean eliminarExperienciaDocente(long codExperienciaDocente) throws SIGEP2SistemaException {
		String msg = "eliminarExperienciaDocente(long codExpetienciaProfesional)";
		IExperienciaDocenteRemote service = getExperienciaDocenteService();
		try {
			return service.eliminarExperienciaDocente(codExperienciaDocente);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static PersonaDTO detallePersona(String idPersona) throws SIGEP2SistemaException {
		IHojaDeVidaRemote service = getHojaDeVidaService();
		return service.detallePersona(idPersona);
	}

	public static List<TipoInstitucionDTO> findTipoInstitucion() throws SIGEP2SistemaException {
		String msg = "List<TipoInstitucionDTO> findTipoInstitucion()";
		IHojaDeVidaRemote service = getHojaDeVidaService();
		try {
			return service.obtenerTipoInstitucion();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<InstitucionEducativaDTO> findInstitucion() throws SIGEP2SistemaException {
		String msg = "List<InstitucionEducativaDTO> findInstitucion()";
		IHojaDeVidaRemote service = getHojaDeVidaService();
		try {
			return service.obtenerInstitucion();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static ExperienciaProfesionalDTO buscarExperienciaProfesional(Long codExperienciaProfesional)
			throws SIGEP2SistemaException {
		String msg = "static ExperienciaProfesionalDTO buscarExperienciaProfesional(Long codExperienciaProfesional)";
		IExperienciaProfesionalRemote service = getExperienciaProfesionalService();
		try {
			return service.buscarExperienciaProfesional(codExperienciaProfesional);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static LogroRecursoDTO obtenerLogroRecursoPorCodPersona(long codigoPersona) {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerLogroRecursoPorCodPersona(codigoPersona);
		} catch (Exception ex) {
			return null;
		}
	}

	public static PublicacionDTO obtenerArticulosPorPersona(long codigoPersona) {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerArticulosPorPersona(codigoPersona);
		} catch (Exception ex) {
			return null;
		}
	}

	public static EvaluacionDesempenoDTO obtenerEvaluacionPorPersona(long idPersona) {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerEvaluacionPorPersona(idPersona);
		} catch (Exception ex) {
			return null;
		}
	}

	public static ParticipacionInstitucionDTO obtenerParticipacionInstitucionPersona(long idPersona) {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerParticipacionInstitucionPersona(idPersona);
		} catch (Exception ex) {
			return null;
		}
	}

	private static IEvaluacionDesempeno getEvaluacionInterface() throws SIGEP2SistemaException {
		IEvaluacionDesempeno commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IEvaluacionDesempeno.class.getName();
			commonService = (IEvaluacionDesempeno) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + EVALUACION_DESEMPENO_EJB + "!" + pakageClassName);
			if (commonService == null) {
				throw new SIGEP2SistemaException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO));
			}
		} catch (NamingException e) {
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_RECURSO_BLOQUEADO), e);
		}
		return commonService;
	}

	public static EvaluacionDesempenoDTO obtenerEvaluacionActual(long idPersonaConsultada, long idEntidad,
			long idPersonaConsultora) throws SIGEP2SistemaException {
		String msg = "EvaluacionDesempenoDelegate";
		IEvaluacionDesempeno service = HojaDeVidaDelegate.getEvaluacionInterface();
		try {
			return service.obtenerEvaluacionActual(idPersonaConsultada, idEntidad, idPersonaConsultora);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<TiposRevistaDTO> obtenerParametricasTiposRevista() {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerParametricasTiposRevista();
		} catch (Exception ex) {
			return null;
		}
	}

	public static List<TiposLibroInvestigacionDTO> obtenerParametricasLibrosInvestigacion() {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerParametricasLibrosInvestigacion();
		} catch (Exception ex) {
			return null;
		}
	}

	public static List<ProduccionBibliograficaDTO> obtenerParametricasProduccionBibliografica() {
		try {
			IGerenciaPublicaRemote service = HojaDeVidaDelegate.getFormularioGerenciaPublicaService();
			return service.obtenerParametricasProduccionBibliografica();
		} catch (Exception e) {
			return null;
		}
	}

	public static void emailActualizacionDatosPersonaDC(String usuarioModificado, Date fechaModificacion,
			String usuarioModificador, List<String> email, List<String> ccEmail, List<String> camposEditados) throws SIGEP2SistemaException {
		String msg = "emailActualizacionDatosPersona(String usuario, String email)";
		IDatosPersonalesRemote service = getDatosPersonalesService();
		try {
			service.emailActualizacionDatosPersonaDC(usuarioModificado, fechaModificacion, usuarioModificador, email,
					ccEmail,camposEditados);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static void emailActualizacionDatosPersona(String usuarioModificado, String email)
			throws SIGEP2SistemaException {
		String msg = "emailActualizacionDatosPersona(String usuario, String email)";
		IDatosPersonalesRemote service = getDatosPersonalesService();
		try {
			service.emailActualizacionDatosPersona(usuarioModificado, email);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}
	
	/**
	 * Metodo para enviar correos a los roles superiores de un usuario que se esta desvinculando
	 * @param asunto
	 * @param mensaje
	 * @param correo
	 * @throws SIGEP2SistemaException
	 */
	public static void enviarEmailPersonasDesvincular(String asunto, String mensaje, String correo) throws SIGEP2SistemaException {
		IDatosPersonalesRemote service = getDatosPersonalesService();
		try {
			service.enviarEmailPersonasDesvincular(asunto, mensaje, correo);
		} catch (Exception e) {
			logger.error("enviarEmailPersonasDesvincular(String asunto, String mensaje, String correo)", e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}
}