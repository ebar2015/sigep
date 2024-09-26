package co.gov.dafp.sigep2.deledago;

import java.util.List;

import javax.naming.NamingException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.ParametricaDTO;
import co.gov.dafp.sigep2.entity.seguridad.AuditoriaConfiguracionDTO;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.PreguntaOpinionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioRolEntidadDTO;
import co.gov.dafp.sigep2.entity.view.AccionAuditoriaDTO;
import co.gov.dafp.sigep2.entity.view.AreaConocimientoDTO;
import co.gov.dafp.sigep2.entity.view.ClaseLibretaMilitarDTO;
import co.gov.dafp.sigep2.entity.view.EstadoCivilDTO;
import co.gov.dafp.sigep2.entity.view.GeneroDTO;
import co.gov.dafp.sigep2.entity.view.NivelEducativoDTO;
import co.gov.dafp.sigep2.entity.view.PoblacionEtnicaDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.entity.view.TipoEntidadDTO;
import co.gov.dafp.sigep2.entity.view.TipoZonaDTO;
import co.gov.dafp.sigep2.interfaces.IAdministracionRemote;
import co.gov.dafp.sigep2.interfaces.IParametrica;
import co.gov.dafp.sigep2.interfaces.IPreguntaOpinionRemote;
import co.gov.dafp.sigep2.interfaces.IServiceRemote;
import co.gov.dafp.sigep2.sistema.locator.ServiceLocator;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

public class AdministracionDelegate {

	private static final Logger logger = Logger.getInstance(AdministracionDelegate.class);

	private static final String ADMINISTRACION_EJB = "AdministracionEJB";
	private static final String PARAMETRICA_EJB = "ParametricaEJB";
	private static final String PREGUNTA_OPINION_EJB = "PreguntaOpinionEJB";


	private static IAdministracionRemote getAdministracionService() throws SIGEP2SistemaException {
		IAdministracionRemote commonService = null;
        ServiceLocator<IServiceRemote> serviceLocator;
		try {
			serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IAdministracionRemote.class.getName();
			//commonService = (IAdministracionRemote) serviceLocator.getService("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
			commonService = (IAdministracionRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + ADMINISTRACION_EJB + "!" + pakageClassName);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return commonService;
	}

	private static IParametrica getParametricaService() throws SIGEP2SistemaException {
		IParametrica commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IParametrica.class.getName();
			commonService = (IParametrica) serviceLocator.getService(ConfigurationBundleConstants.getEJBAppName() + "/"
					+ ConfigurationBundleConstants.getEJBModuleName() + "/" + PARAMETRICA_EJB + "!" + pakageClassName);
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

	private static IPreguntaOpinionRemote getPreguntaOpinionService() throws SIGEP2SistemaException {
		IPreguntaOpinionRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IPreguntaOpinionRemote.class.getName();
			commonService = (IPreguntaOpinionRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + PREGUNTA_OPINION_EJB + "!" + pakageClassName);
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

	public static List<TipoDocumentoDTO> findTipoDocumento() throws SIGEP2SistemaException {
		String msg = "static List<TipoDocumentoDTO> findTipoDocumento()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findTipoDocumento();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<NivelEducativoDTO> findNivelEducativo() throws SIGEP2SistemaException {
		String msg = "static List<NivelEducativoDTO> findNivelEducativo()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findNivelEducativo();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<AreaConocimientoDTO> findAreaConocimiento() throws SIGEP2SistemaException {
		String msg = "static List<AreaConocimientoDTO> findAreaConocimiento()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findAreaConocimiento();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<ParametricaDTO> listarTablasParametrica() throws SIGEP2SistemaException {
		String msg = "static List<ParametricaDTO> listarTablasParametrica()";
		IParametrica service = getParametricaService();
		try {
			return service.listarTablas();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static void crearParametro(String descripcion, ParametricaDTO... detalleParametro)
			throws SIGEP2SistemaException {
		String msg = "static void crearParametro(String descripcion, Parametrica... detalleParametro)";
		IParametrica service = getParametricaService();
		try {
			service.crearParametro(descripcion, detalleParametro);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<GeneroDTO> findGenero() throws SIGEP2SistemaException {
		String msg = "static List<GeneroDTO> findGenero()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findGenero();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<PoblacionEtnicaDTO> findPoblacionEtnica() throws SIGEP2SistemaException {
		String msg = "static List<PoblacionEtnicaDTO> findPoblacionEtnica()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findPoblacionEtnica();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<EstadoCivilDTO> findEstadoCivil() throws SIGEP2SistemaException {
		String msg = "static List<EstadoCivilDTO> findEstadoCivil()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findEstadoCivil();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<PaisDTO> findPais() throws SIGEP2SistemaException {
		String msg = "static List<PaisDTO> findPais()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findPais();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<DepartamentoDTO> findDepartamento() throws SIGEP2SistemaException {
		String msg = "static List<DepartamentoDTO> findDepartamento()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findDepartamento();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<MunicipioDTO> findMunicipio() throws SIGEP2SistemaException {
		String msg = "static List<MunicipioDTO> findMunicipio()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findMunicipio();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<TipoZonaDTO> findTipoZona() throws SIGEP2SistemaException {
		String msg = "static List<TipoZonaDTO> findTipoZona()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findTipoZona();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<ClaseLibretaMilitarDTO> findClaseLibretaMilitar() throws SIGEP2SistemaException {
		String msg = "static List<ClaseLibretaMilitarDTO> findClaseLibretaMilitar()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findClaseLibretaMilitar();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<TipoAsociacionDTO> findTipoAsociacion() throws SIGEP2SistemaException {
		String msg = "static List<TipoAsociacionDTO> findTipoAsociacion()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findTipoAsociacion();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static TipoAsociacionDTO findTipoAsociacion(long codTipoAsociacion) throws SIGEP2SistemaException {
		String msg = "static TipoAsociacionDTO findTipoAsociacion(long codTipoAsociacion)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findTipoAsociacion(codTipoAsociacion);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<TipoEntidadDTO> findTipoEntidad() throws SIGEP2SistemaException {
		String msg = "static List<TipoEntidadDTO> findTipoEntidad()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findTipoEntidad();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion() throws SIGEP2SistemaException {
		String msg = "static List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findAuditoriaConfiguracion();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static GeneroDTO findGeneroId(long id) throws SIGEP2SistemaException {
		String msg = "static GeneroDTO findGeneroId()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findGeneroId(id);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static ClaseLibretaMilitarDTO findClaseLibretaMilitarId(long id) throws SIGEP2SistemaException {
		String msg = "static ClaseLibretaMilitarDTO findClaseLibretaMilitarId()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findClaseLibretaMilitarId(id);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static TipoDocumentoDTO findTipoDocumentoId(long id) throws SIGEP2SistemaException {
		String msg = "static TipoDocumentoDTO findTipoDocumentoId(long id)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findTipoDocumentoId(id);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static PaisDTO encontrarPaisPorId(long id) throws SIGEP2SistemaException {
		String msg = "static PaisDTO encontrarPaisPorId(long id)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.encontrarPaisPorId(id);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static boolean actualizarAuditoriaConfiguracion(AuditoriaConfiguracionDTO auditoria)
			throws SIGEP2SistemaException {
		String msg = "static void actualizarAuditoriaConfiguracion(AuditoriaConfiguracionDTO auditoria)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.actualizarAuditoriaConfiguracion(auditoria);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static void crearPregunta(PreguntaOpinionDTO pregunta) throws SIGEP2SistemaException {
		String msg = "static void crearPregunta(PreguntaOpinionDTO pregunta)";
		IPreguntaOpinionRemote service = getPreguntaOpinionService();
		try {
			service.crearPregunta(pregunta);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<AccionAuditoriaDTO> findAccionAuditoria() throws SIGEP2SistemaException {
		String msg = "static List<AccionAuditoriaDTO> findAccionAuditoria()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findAccionAuditoria();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion(int first, int pageSize)
			throws SIGEP2SistemaException {
		String msg = "static List<AuditoriaConfiguracionDTO> findAuditoriaConfiguracion(int first, int pageSize)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findAuditoriaConfiguracion(first, pageSize);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static int findAuditoriaConfiguracionTotalRows() throws SIGEP2SistemaException {
		String msg = "static int findAuditoriaConfiguracionTotalRows()";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.findAuditoriaConfiguracionTotalRows();
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static List<EntidadDTO> obtenerDependenciasEntidades(Long entidad) throws SIGEP2SistemaException {
		String msg = "static List<EntidadDTO> obtenerEntidadesUsuario(Long entidad)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.obtenerDependenciasEntidades(entidad);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

	public static EntidadDTO obtenerEntidadPorNombre(String nombreEntidad) throws SIGEP2SistemaException {
		String msg = "static EntidadDTO obtenerEntidadPorNombre(String nombreEntidad)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.obtenerEntidadPorNombre(nombreEntidad);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

	public static List<RolDTO> obtenerRolesPorDescripcion(String... descripcionRol) throws SIGEP2SistemaException {
		String msg = "static List<EntidadDTO> obtenerEntidadesUsuario(Long entidad)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.obtenerRolesPorDescripcion(descripcionRol);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

	public static void asociarRolUsuario(UsuarioRolEntidadDTO usuarioAsociar, UsuarioDTO usuarioAuditoria, String accion)
			throws SIGEP2SistemaException {
		String msg = "static void asociarRolUsuario(UsuarioRolEntidadDTO usuarioAsociar)";
		IAdministracionRemote service = getAdministracionService();
		try {
			service.asociarRolUsuario(usuarioAsociar, usuarioAuditoria, accion);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

	public static void crearRol(UsuarioDTO usuarioAuditoria, RolDTO... roles) throws SIGEP2SistemaException {
		String msg = "static void crearRol(UsuarioDTO usuarioAuditoria, RolDTO... roles)";
		IAdministracionRemote service = getAdministracionService();
		for(RolDTO r : roles) {
			r.setNombre(r.getNombre().trim());
			r.setDescripcionRol(r.getDescripcionRol().trim());
		}
		try {
			service.crearRol(usuarioAuditoria, roles);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

	public static void actualizarRoles(UsuarioDTO usuarioAuditoria, RolDTO reasignarA, RolDTO... roles)
			throws SIGEP2SistemaException {
		String msg = "static void actualizarRoles(UsuarioDTO usuarioAuditoria, RolDTO... roles)";
		IAdministracionRemote service = getAdministracionService();
		try {
			service.actualizarRoles(usuarioAuditoria, reasignarA, roles);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

	public static boolean isRolTieneUsuarioAsociados(Long codRol) throws SIGEP2SistemaException {
		String msg = "static boolean isRolTieneUsuarioAsociados(Long codRol)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.isRolTieneUsuarioAsociados(codRol);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}
	
	public static List<PersonaDTO> buscarPersonas(Long entidadID,Long tipoDocumento, String numeroIdentificacion, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido,int first, int pageSize, String sortField, String sortOrder) throws SIGEP2SistemaException{
		
		String msg = "static List<PersonaDTO> buscarPersonas(...)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.buscarPersonas(entidadID,tipoDocumento, numeroIdentificacion,
					primerNombre, segundoNombre, primerApellido, segundoApellido, first, pageSize, sortField, sortOrder);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}
	
	public static List<RolDTO> obtenerRolesByPersona(long personaId, long entidadId) throws SIGEP2SistemaException{
		String msg = "static List<RolDTO> obtenerRolesByPersona(...)";
		IAdministracionRemote service = getAdministracionService();
		try {
			return service.obtenerRolesByPersona(personaId, entidadId);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
		
	}

}
