package co.gov.dafp.sigep2.deledago;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.naming.NamingException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.ArchivoCargueDTO;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioEntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioRolEntidadDTO;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.interfaces.IAsociarUsuarioEntidadRemote;
import co.gov.dafp.sigep2.interfaces.IAutenticacionSistemaRemote;
import co.gov.dafp.sigep2.interfaces.ICambioContraseniaRemote;
import co.gov.dafp.sigep2.interfaces.ICargueArchivoRemote;
import co.gov.dafp.sigep2.interfaces.IDesactivarUsuarioMasivo;
import co.gov.dafp.sigep2.interfaces.IDesasociarUsuarioEntidadRemote;
import co.gov.dafp.sigep2.interfaces.IInicioAplicacionRemote;
import co.gov.dafp.sigep2.interfaces.IRecursosMenuRemote;
import co.gov.dafp.sigep2.interfaces.IServiceRemote;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.locator.ServiceLocator;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.elemento.Archivo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IngresoSistemaDelegate {

	private static final Logger logger = Logger.getInstance(IngresoSistemaDelegate.class);

	private static final String RECURSOS_MENU_EJB = "RecursosMenuEJB";
	private static final String INICIO_APLICACION_EJB = "InicioAplicacionEJB";
	private static final String CARGUE_ARCHIVO_EJB = "CargueArchivoEJB";
	private static final String CAMBIO_CONTRASENIA_EJB = "CambioContraseniaEJB";
	private static final String AUTENTICACION_SISTEMA_EJB = "AutenticacionSistemaEJB";
	private static final String ASOCIAR_USUARIO_ENTIDAD_EJB = "AsociarUsuarioEntidadEJB";
	private static final String DESASOCIAR_USUARIO_ENTIDAD_EJB = "DesasociarUsuarioEntidadEJB";
	private static final String DESACTIVAR_USUARIOS_MASIVO_EJB = "DesactivarUsuarioMasivoEJB";
	private static final String MAXIMO_INTENTOS_FALLIDOS_LOGIN = "MAXIMO_INTENTOS_FALLIDOS_LOGIN";

	private static IDesasociarUsuarioEntidadRemote getDesasociarUsuarioEntidadService() throws SIGEP2SistemaException {
		IDesasociarUsuarioEntidadRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IDesasociarUsuarioEntidadRemote.class.getName();
			commonService = (IDesasociarUsuarioEntidadRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + DESASOCIAR_USUARIO_ENTIDAD_EJB + "!" + pakageClassName);
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

	private static IInicioAplicacionRemote getInicioAplicacionService() throws SIGEP2SistemaException {
		IInicioAplicacionRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IInicioAplicacionRemote.class.getName();
			commonService = (IInicioAplicacionRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + INICIO_APLICACION_EJB + "!" + pakageClassName);
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

	private static IAsociarUsuarioEntidadRemote getPersonaService() throws SIGEP2SistemaException {
		IAsociarUsuarioEntidadRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IAsociarUsuarioEntidadRemote.class.getName();
			commonService = (IAsociarUsuarioEntidadRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + ASOCIAR_USUARIO_ENTIDAD_EJB + "!" + pakageClassName);
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

	private static IAutenticacionSistemaRemote getAutenticacionSistemaService() throws SIGEP2SistemaException {
		IAutenticacionSistemaRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IAutenticacionSistemaRemote.class.getName();
			commonService = (IAutenticacionSistemaRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + AUTENTICACION_SISTEMA_EJB + "!" + pakageClassName);
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

	private static ICambioContraseniaRemote getCambioContraseniaService() throws SIGEP2SistemaException {
		ICambioContraseniaRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = ICambioContraseniaRemote.class.getName();
			commonService = (ICambioContraseniaRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + CAMBIO_CONTRASENIA_EJB + "!" + pakageClassName);
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

	private static ICargueArchivoRemote getCargueArchivoService() throws SIGEP2SistemaException {
		ICargueArchivoRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = ICargueArchivoRemote.class.getName();
			commonService = (ICargueArchivoRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + CARGUE_ARCHIVO_EJB + "!" + pakageClassName);
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

	private static IRecursosMenuRemote getRecursosMenuService() throws SIGEP2SistemaException {
		IRecursosMenuRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IRecursosMenuRemote.class.getName();
			commonService = (IRecursosMenuRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + RECURSOS_MENU_EJB + "!" + pakageClassName);
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

	private static IDesactivarUsuarioMasivo getDesactivarUsuarioMasivoService() throws SIGEP2SistemaException {
		IDesactivarUsuarioMasivo commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IDesactivarUsuarioMasivo.class.getName();
			commonService = (IDesactivarUsuarioMasivo) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + DESACTIVAR_USUARIOS_MASIVO_EJB + "!" + pakageClassName);
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

	public static void init(final ProcesoArchivoDTO proceso, Archivo plantillaArchivo, final long entidadCargue,
			final UsuarioDTO usuarioArchivoCargue, final long rolCargueId, final List<File> archivoCague,
			final Date fechaCargue, final String rutaArchivoEnCargue, final boolean sincronico)
			throws SIGEP2SistemaException, InterruptedException {
		String msg = "static void init(final ProcesoArchivoDTO proceso, final long entidadCargue,"
				+ "final UsuarioDTO usuarioArchivoCargue, final List<File> archivoCague, final Date fechaCargue,"
				+ "final String rutaArchivoEnCargue)";
		ICargueArchivoRemote service = getCargueArchivoService();
		try {
			service.init(proceso, plantillaArchivo, entidadCargue, usuarioArchivoCargue, rolCargueId, archivoCague,
					fechaCargue, rutaArchivoEnCargue, sincronico);
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

	public static PersonaDTO findByTipoDocumentoAndNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion)
			throws SIGEP2SistemaException {
		String msg = "static PersonaDTO findByTipoDocumentoAndNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion)";
		IDesasociarUsuarioEntidadRemote service = getDesasociarUsuarioEntidadService();
		try {
			return service.consultarPersonaByNumeroIdentificacion(tipoDocumento, numeroIdentificacion.trim());
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static PersonaDTO findByTipoDocumentoAndNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion,
			Long codEntidad) throws SIGEP2SistemaException {
		String msg = "static PersonaDTO findByTipoDocumentoAndNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion, Long codEntidad)";
		IDesasociarUsuarioEntidadRemote service = getDesasociarUsuarioEntidadService();
		try {
			return service.consultarPersonaByNumeroIdentificacion(tipoDocumento, numeroIdentificacion.trim(), codEntidad);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static PersonaDTO buscarPersonaAsociadaEntidad(Long tipoDocumento, String numeroIdentificacion,
			Long codEntidad) throws SIGEP2SistemaException {
		String msg = "static PersonaDTO buscarPersonaAsociadaEntidad(Long tipoDocumento, String numeroIdentificacion, Long codEntidad)";
		IAsociarUsuarioEntidadRemote service = getPersonaService();
		try {
			return service.buscarPersonaAsociadaEntidad(tipoDocumento, numeroIdentificacion, codEntidad);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static boolean desasociarUsuarioEntidad(Long codPersona, Long codEntidad, Long tipoDocumento,
			String numeroIdentificacion, UsuarioDTO usuarioAud) throws SIGEP2SistemaException {
		String msg = "static boolean desasociarUsuarioEntidad(Long codPersona, Long codEntidad)";
		IDesasociarUsuarioEntidadRemote service = getDesasociarUsuarioEntidadService();
		String strNotaPrivacidad;
		
		/*NotaPrivacidad*/
		Parametrica paramNotaPrivacidad = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.NOTA_PRIVACIDAD_EMAIL_RESTABLECER_PASSWORD.getValue()));
		if(paramNotaPrivacidad!=null && paramNotaPrivacidad.getValorParametro()!=null){
			strNotaPrivacidad = paramNotaPrivacidad.getValorParametro();
		}else{
			strNotaPrivacidad=null;
		}
		
		/*Consulta el asunto en la tabla parametrica, si no existe el parametro asigna uno por defecto*/
		String asunto = "";
		Integer codParamAsunto =TipoParametro.MAIL_DESASOCIAR_USUARIO_ENTIDAD_ASUNTO.getValue();
		Parametrica paramAsunto = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(codParamAsunto));
		asunto = paramAsunto != null && paramAsunto.getValorParametro() != null ? paramAsunto.getValorParametro():"Desasociar Usuario de Entidad en SIGEP II";
		
		/*Consulta los dias en la tabla parametrica, sino existe el parametro se asigna uno por defecto*/
		
		String dias = "";
		Integer codParamDiasCorreo =TipoParametro.MAIL_DESASOCIAR_USUARIO_ENTIDAD_DIAS.getValue();
		Parametrica ParamDiasCorreo = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(codParamDiasCorreo));
		dias = ParamDiasCorreo != null && ParamDiasCorreo.getValorParametro()!= null ? ParamDiasCorreo.getValorParametro() : "1";
		
		/*Consulta de la entidad donde va a ser desasociado*/
		String nombreEntidad = "";
		Entidad entidad = ComunicacionServiciosEnt.getEntidadPorId(codEntidad);
		nombreEntidad = entidad != null && entidad.getNombreEntidad()!= null ? entidad.getNombreEntidad() : "";
		
		/*Consulta el cuerpo del correo en la tabla parametrica, si no existe el parametro asigna uno por defecto*/
		String cuerpoCorreo = "";
		Integer codParamCuerpoCorreo =TipoParametro.MAIL_DESASOCIAR_USUARIO_ENTIDAD_CUERPO.getValue();
		Parametrica ParamCuerpoCorreo = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(codParamCuerpoCorreo));
		if (ParamCuerpoCorreo != null && ParamCuerpoCorreo.getValorParametro() != null) {
			try {
				cuerpoCorreo = ParamCuerpoCorreo.getValorParametro().replace("DIA", dias).replace("ENTIDAD", nombreEntidad) ;
			} catch (Exception e) {
				cuerpoCorreo = "Error generando el cuerpo del correo";
			}
			
		}
		
		try {
			return service.desasociarUsuarioEntidad(codPersona, codEntidad, tipoDocumento, numeroIdentificacion,
					usuarioAud,strNotaPrivacidad, asunto, cuerpoCorreo);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static ArchivoCargueDTO findByNombreArchivoCargue(String nombreArchivo) throws SIGEP2SistemaException {
		String msg = "static ArchivoCargueDTO findByNombreArchivoCargue(String nombreArchivo)";
		ICargueArchivoRemote service = getCargueArchivoService();
		try {
			return service.findByNombreArchivoCargue(nombreArchivo);
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

	public static List<ProcesoArchivoDTO> getProcesosArchivoPlantillas() throws SIGEP2SistemaException {
		String msg = "static List<ProcesoArchivoDTO> getProcesosArchivoPlantillas()";
		ICargueArchivoRemote service = getCargueArchivoService();
		try {
			return service.getProcesosArchivoPlantillas();
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

	public static List<ProcesoArchivoDTO> getProcesosArchivo() throws SIGEP2SistemaException {
		String msg = "static List<ProcesoArchivoDTO> getProcesosArchivo()";
		ICargueArchivoRemote service = getCargueArchivoService();
		try {
			return service.getProcesosArchivo();
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

	public static List<ProcesoArchivoDTO> getPlantillas() throws SIGEP2SistemaException {
		String msg = "static List<ProcesoArchivoDTO> getPlantillas()";
		ICargueArchivoRemote service = getCargueArchivoService();
		try {
			return service.getPlantillas();
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

	public static List<ProcesoArchivoDTO> getProcesosArchivo(List<String> nombresPlantilla)
			throws SIGEP2SistemaException {
		String msg = "static List<ProcesoArchivoDTO> getProcesosArchivo(List<String> nombresPlantilla)";
		ICargueArchivoRemote service = getCargueArchivoService();
		try {
			return service.getProcesosArchivo(nombresPlantilla);
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

	
	public static void main(String[] args) {
		String password = "*!";
		for (int i=0; i<password.length();i++) {
	        
			
			System.out.println("numero"+i+"imprimir letras "+password.charAt(i));

		}
	}
	
	
	public static RecursoActivoPerfilUsuarioDTO findByCodigoVentana(String codigoVentana, Long usuarioId)
			throws SIGEP2SistemaException {	
		
		String msg = "static RecursoDTO findByCodigoVentana(String codigoVentana)";
		IRecursosMenuRemote service = getRecursosMenuService();
		try {
			return service.findByCodigoVentana(codigoVentana, usuarioId);
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

	public static List<RecursoActivoPerfilUsuarioDTO> obtenerRecursosActivosPorUsuario(Long usuarioId, Long entidadId)
			throws SIGEP2SistemaException {
		String msg = "static List<RecursoDTO> obtenerRecursosActivosPorUsuario(Long usuarioId)";
		IRecursosMenuRemote service = getRecursosMenuService();
		try {
			return service.obtenerRecursosActivosPorUsuario(usuarioId, entidadId);
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

	public static List<RolDTO> obtenerRolesPorUsuario(Long usuarioId, Long entidadId) throws SIGEP2SistemaException {
		String msg = "static List<RecursoDTO> obtenerRolesPorUsuario(Long usuarioId)";
		IRecursosMenuRemote service = getRecursosMenuService();
		try {
			return service.obtenerRolesPorUsuario(usuarioId, entidadId);
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

	public static UsuarioDTO login(Long tipoDocumento, String login, String password, String mac, String ipAddress)
			throws SIGEP2SistemaException {
		String msg = "static UsuarioDTO login(String tipoDocumento, String login, String password, String mac, String ipAddress)";
		IAutenticacionSistemaRemote service = getAutenticacionSistemaService();
		try {
			Parametrica pIntentosFallidos = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(TipoParametro.MAXIMO_INTENTOS_FALLIDOS_LOGIN.getValue()));
			Long maxIntentosFallidos = (long) 4;
			if (pIntentosFallidos != null && pIntentosFallidos.getValorParametro() != null) {
				maxIntentosFallidos = Long.parseLong(pIntentosFallidos.getValorParametro());
			}
          return service.login(tipoDocumento, login, password.trim(), mac, ipAddress, maxIntentosFallidos);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (SIGEP2SistemaException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

	public static void aceptaHabeasData(UsuarioDTO usuario) throws SIGEP2SistemaException {
		String msg = "static void aceptaHabeasData(UsuarioDTO usuario)";
		IAutenticacionSistemaRemote service = getAutenticacionSistemaService();
		try {
			service.aceptaHabeasData(usuario);
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

	public static List<EntidadDTO> obtenerEntidadesUsuario(UsuarioDTO usuario) throws SIGEP2SistemaException {
		String msg = "static List<EntidadDTO> obtenerEntidadesUsuario(UsuarioDTO usuario)";
		IAutenticacionSistemaRemote service = getAutenticacionSistemaService();
		try {
			return service.obtenerEntidadesUsuario(usuario);
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

	public static List<UsuarioDTO> obtenerEntidadesUsuarioAsociadas(Long usuario, Long entidad)
			throws SIGEP2SistemaException {
		String msg = "static List<EntidadDTO> obtenerEntidadesUsuarioAsociadas(UsuarioDTO usuario, EntidadDTO entidad)";
		IAutenticacionSistemaRemote service = getAutenticacionSistemaService();
		try {
			return service.obtenerEntidadesUsuarioAsociadas(usuario, entidad);
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

	public static List<EntidadDTO> obtenerEntidades() throws SIGEP2SistemaException {
		String msg = "static List<EntidadDTO> obtenerEntidades()";
		IAutenticacionSistemaRemote service = getAutenticacionSistemaService();
		try {
			return service.obtenerEntidades();
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

	public static void desactivarUsuariosMasivo(Long codEntidad) throws SIGEP2SistemaException {
		String msg = "static void desactivarUsuariosMasivo(Long entidad)";
		IDesactivarUsuarioMasivo service = getDesactivarUsuarioMasivoService();
		try {
			service.desactivarUsuarios(codEntidad);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static String solicitarRestablecerPassword(Long tipoDocumento, String login, Locale locale)
			throws SIGEP2SistemaException {
		String msg = "static void solicitarRestablecerPassword(Long tipoDocumento, String login)";
		String strNotaPrivacidad;
		ICambioContraseniaRemote service = getCambioContraseniaService();
		
		/*NotaPrivacidad*/
		Parametrica paramNotaPrivacidad = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.NOTA_PRIVACIDAD_EMAIL_RESTABLECER_PASSWORD.getValue()));
		if(paramNotaPrivacidad!=null && paramNotaPrivacidad.getValorParametro()!=null){
			strNotaPrivacidad = paramNotaPrivacidad.getValorParametro();
		}else{
			strNotaPrivacidad=null;
		}
		

		// Consulta del parametro Asunto en la Tabla paramÃ©trica (Si no existe
		// parÃ¡metro, se asigna el del archivo de configuracion)
		Integer codParamAsunto =TipoParametro.MAIL_RESTABLECER_PASSWORD_ASUNTO_CODIGO_PARAM.getValue();
		Parametrica paramAsunto = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(codParamAsunto));
		String asunto = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_RESTABLECER_PASSWORD_ASUNTO);
		if (paramAsunto != null) {
			try {
				asunto = paramAsunto.getValorParametro();
			} catch (Exception e) {
				asunto = "Restablecer contraseÃ±a en SIGEP II";
			}
			
		}

		// Consulta del parametro Cuerpo en la Tabla paramÃ©trica (Si no existe
		// parÃ¡metro, se asigna el del archivo de configuracion)
		Integer codParamCuerpo = TipoParametro.MAIL_RESTABLECER_PASSWORD_CUERPO_CODIGO_PARAM.getValue();
		Parametrica paramCuerpo = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(codParamCuerpo));
		String cuerpo = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_RESTABLECER_PASSWORD_CUERPO);
		if (paramCuerpo != null) {
			try {
				cuerpo = paramCuerpo.getValorParametro();
			} catch (Exception e) {
				cuerpo = "<br><b>Mensaje para: </b>USUARIO_RESTABLECE<hr><p style=\"text-align:justify\">Usted ha solicitado el restablecimiento de su contrase&#241;a en SIGEP II. Se ha generado autorizaci&oacute;n con c&oacute;digo <span style=\"font-weight:bold\">ticket</span>. Para ingresar una nueva contrase&#241;a haga click <a href=\"link\" onmouseover=\"window.status='Restablecer contrase&#241;a';return true\" target=\"_blank\" style=\"color:#2a89b4\"/>aqu&iacute;</a>.</p><p>Recuerde que:</p><ul style=\"text-align:justify\"><li>La contrase&#241;a debe contener al menos una letra may&uacute;scula, una letra min&uacute;scula, un n&uacute;mero y un caracter especial tales como <b>PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS</b>, sin espacios.</li><li>Esta no debe superar la longitud de PASS_VALIDATE_MAXIMO caracteres y debe tener una longitud m&iacute;nima de PASS_VALIDATE_MINIMO.</li><li>En enlace tiene vigencia hasta vence y puede ser utilizado solo una vez. Si dentro de este tiempo no realiza el procedimiento debe solicitar de nuevo en enlace de restablecimiento de contrase&#241;a.</li></ul>";
			}

		}

		// Consulta del tiempo de vigencia del link para reestablecer la
		// contraseÃ±a
		// Tiempo en dias
		String codParamDiasSumar = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_DIAS_CODIGO_PARAM);
		Parametrica paramDiasSumar = ComunicacionServiciosSis.getParametricaIntetos(codParamDiasSumar);
		int diasVigencia = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_DIAS);
		if (paramDiasSumar != null) {
			try {
				diasVigencia = Integer.parseInt(paramDiasSumar.getValorParametro());
			} catch (Exception e) {
				diasVigencia = 1;
			}
		}

		// Tiempo en horas
		String codParamHorasSumar = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_HORAS_CODIGO_PARAM);
		Parametrica paramHorasSumar = ComunicacionServiciosSis.getParametricaIntetos(codParamHorasSumar);
		int horasVigencia = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_HORAS);
		
		if (paramHorasSumar != null) {
			try {
				horasVigencia = Integer.parseInt(paramHorasSumar.getValorParametro());
			} catch (Exception e) {
				horasVigencia = 4;
			}
			
			logger.log().info(msg, "PRUEBA DAFP: Horas vigencia ticket BD: " + horasVigencia);
		}

		try {
			return service.solicitarRestablecerPassword(tipoDocumento, login, asunto, cuerpo, diasVigencia,
					horasVigencia, locale,strNotaPrivacidad);
		} catch (NullPointerException e) {
			logger.log().error(msg, e);
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, locale));
		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, locale));
		}
	}

	public static void restablecerPassword(Long usuarioId, String password, String ticket, Locale locale)
			throws SIGEP2SistemaException {
		String msg = "static void restablecerPassword(String tipoDocumento, String login, String password, String ticket)";
		ICambioContraseniaRemote service = getCambioContraseniaService();
		password = password.trim();
		
		/*NotaPrivacidad*/
		String strNotaPrivacidad;
		Parametrica paramNotaPrivacidad = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.NOTA_PRIVACIDAD_EMAIL_RESTABLECER_PASSWORD.getValue()));
		if(paramNotaPrivacidad!=null && paramNotaPrivacidad.getValorParametro()!=null){
			strNotaPrivacidad = paramNotaPrivacidad.getValorParametro();
		}else{
			strNotaPrivacidad=null;
		}
		
		// Consulta del parametro Asunto en la Tabla paramÃ©trica (Si no existe
		// parÃ¡metro, se asigna el del archivo de configuracion)
		Integer codParamAsunto = TipoParametro.MAIL_PASSWORD_RESTABLECIDO_ASUNTO_CODIGO_PARAM.getValue();
		Parametrica paramAsunto = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(codParamAsunto));
		String asunto = "";

		if (paramAsunto != null) {
			try {
				asunto = paramAsunto.getValorParametro();
			} catch (Exception e) {
				asunto = ConfigurationBundleConstants
						.getString(ConfigurationBundleConstants.MAIL_PASSWORD_RESTABLECIDO_ASUNTO);
			}
			
		}
		if(asunto==null)
			asunto = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_PASSWORD_RESTABLECIDO_ASUNTO);			

		// Consulta del parametro Cuerpo en la Tabla paramÃ©trica (Si no existe
		// parÃ¡metro, se asigna el del archivo de configuracion)
		Integer codParamCuerpo = TipoParametro.MAIL_PASSWORD_RESTABLECIDO_CUERPO_CODIGO_PARAM.getValue();
		Parametrica paramCuerpo = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(codParamCuerpo));
		
		String cuerpo = "";
		
		if (paramCuerpo != null) {
			try {
				cuerpo = paramCuerpo.getValorParametro();
			} catch (Exception e) {
				cuerpo  = ConfigurationBundleConstants
						.getString(ConfigurationBundleConstants.MAIL_PASSWORD_RESTABLECIDO_CUERPO);
			}
			
		}
		if(cuerpo==null)
			cuerpo  = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.MAIL_PASSWORD_RESTABLECIDO_CUERPO);

		// Consulta del tiempo de vigencia de la clave
		String codParamDiasVenceClave = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.CNS_DIAS_VENCE_CLAVE_USUARIO_CODIGO_PARAM);
		Parametrica paramDiasVenceClave = ComunicacionServiciosSis.getParametricaIntetos(codParamDiasVenceClave);
		int diasVigenciaClave = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.CNS_DIAS_VENCE_CLAVE_USUARIO);
		
		if (paramDiasVenceClave != null) {
			try {
				diasVigenciaClave = Integer.parseInt(paramDiasVenceClave.getValorParametro());
			} catch (Exception e) {
				diasVigenciaClave = 360;
			}
		}
		// Consulta del tiempo de vigencia del link para reestablecer la
		// contraseÃ±a
		// Tiempo en dias
		String codParamDiasSumar = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_DIAS_CODIGO_PARAM);
		Parametrica paramDiasSumar = ComunicacionServiciosSis.getParametricaIntetos(codParamDiasSumar);
		int diasVigenciaTicket = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_DIAS);
		
		if (paramDiasSumar != null) {
			try {
				diasVigenciaTicket = Integer.parseInt(paramDiasSumar.getValorParametro());
			} catch (Exception e) {
				diasVigenciaTicket = 1;
			}
		}

		// Tiempo en horas
		String codParamHorasSumar = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_HORAS_CODIGO_PARAM);
		Parametrica paramHorasSumar = ComunicacionServiciosSis.getParametricaIntetos(codParamHorasSumar);
		int horasVigenciaTicket = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_HORAS);
		if (paramHorasSumar != null) {
			try {
				horasVigenciaTicket = Integer.parseInt(paramHorasSumar.getValorParametro());
			} catch (Exception e) {
				horasVigenciaTicket = 4;
			}
		}

		try {
			service.restablecerPassword(usuarioId, password, ticket, asunto, cuerpo, diasVigenciaClave,
					diasVigenciaTicket, horasVigenciaTicket, locale, strNotaPrivacidad);
			
		} catch (NullPointerException e) {
			logger.error(msg, e);
			e.printStackTrace();
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, locale));
		} catch (Exception e) {
			logger.error(msg, e);
			e.printStackTrace();
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, locale));
		}
	}

	public static void validarTicket(Long usuarioId, String ticket, Locale locale) throws SIGEP2SistemaException {
		String msg = "public static void validarTicket(Long usuarioId, String ticket)";
		ICambioContraseniaRemote service = getCambioContraseniaService();

		// Consulta del tiempo de vigencia del link para reestablecer la
		// contraseÃ±a
		// Tiempo en dias
		String codParamDiasSumar = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_DIAS_CODIGO_PARAM);
		Parametrica paramDiasSumar = ComunicacionServiciosSis.getParametricaIntetos(codParamDiasSumar);
		int diasVigencia = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_DIAS);
		if (paramDiasSumar != null && paramDiasSumar.getValorParametro() != null) {
			try {
				diasVigencia = Integer.parseInt(paramDiasSumar.getValorParametro());
			} catch (Exception e) {
				diasVigencia = 1;
			}
			
		}

		// Tiempo en horas
		String codParamHorasSumar = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_HORAS_CODIGO_PARAM);
		Parametrica paramHorasSumar = ComunicacionServiciosSis.getParametricaIntetos(codParamHorasSumar);
		int horasVigencia = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.TIEMPO_VIGENCIA_TICKET_HORAS);
		if (paramHorasSumar != null && paramHorasSumar.getValorParametro() != null) {
			try {
				horasVigencia = Integer.parseInt(paramHorasSumar.getValorParametro());
			} catch (Exception e) {
				horasVigencia = 4;
			}
			
		}

		try {
			service.validarTicket(usuarioId, ticket, diasVigencia, horasVigencia, locale);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, locale));
		} catch (SIGEP2SistemaException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO, locale));
		}
	}

	public static UsuarioPasswordDTO getUsuarioByLogin(Long tipoDocumento, String login) throws SIGEP2SistemaException {
		String msg = "static UsuarioPasswordDTO getUsuarioByLogin(String tipoDocumento, String login)";
		ICambioContraseniaRemote service = getCambioContraseniaService();
		try {
			return service.getUsuarioByLogin(tipoDocumento, login);
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

	public static UsuarioPasswordDTO getUsuarioTicket(String ticket) throws SIGEP2SistemaException {
		String msg = "static UsuarioPasswordDTO getUsuarioTicket(String ticket)";
		ICambioContraseniaRemote service = getCambioContraseniaService();
		try {
			return service.getUsuarioTicket(ticket);
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

	public static UsuarioDTO getUsuario(long id) throws SIGEP2SistemaException {
		String msg = "static UsuarioDTO getUsuario(long id)";
		ICambioContraseniaRemote service = getCambioContraseniaService();
		try {
			return service.getUsuario(id);
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

	public static void anularTicket(UsuarioPasswordDTO usuario, UsuarioDTO usuarioSesion)
			throws SIGEP2SistemaException {
		String msg = "static void actualizarUsuario(UsuarioPasswordDTO usuario, UsuarioDTO usuarioSesion)";
		ICambioContraseniaRemote service = getCambioContraseniaService();
		try {
			service.anularTicket(usuario, usuarioSesion);
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

	public static void asociarEntidad(PersonaDTO persona, TipoAsociacionDTO tipoAsociaion, UsuarioDTO usuarioRegistro,
			EntidadDTO entidadRegistro) throws SIGEP2SistemaException {
		String msg = "static void asociarEntidad(PersonaDTO persona, TipoAsociacionDTO tipoAsociaion, UsuarioDTO usuarioRegistro, EntidadDTO entidadRegistro)";
		IAsociarUsuarioEntidadRemote service = getPersonaService();
		if(persona.getPrimerNombre() != null)
			persona.setPrimerNombre(persona.getPrimerNombre().trim());
		if(persona.getSegundoNombre() != null)
			persona.setSegundoNombre(persona.getSegundoNombre().trim());
		if(persona.getPrimerApellido() != null)
			persona.setPrimerApellido(persona.getPrimerApellido());
		if(persona.getSegundoApellido() != null)
			persona.setSegundoApellido(persona.getSegundoApellido());
		if(persona.getCorreoElectronico() != null) 
			persona.setCorreoElectronico(persona.getCorreoElectronico().trim());
		if(persona.getCorreoAlternativo() != null)
			persona.setCorreoAlternativo(persona.getCorreoAlternativo().trim ());
		try {
			service.asociarEntidad(persona, tipoAsociaion, usuarioRegistro, entidadRegistro);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static void crearUsuario(PersonaDTO persona, TipoAsociacionDTO tipoAsociacion, UsuarioDTO usuarioRegistro,
			EntidadDTO entidadRegistro) throws SIGEP2SistemaException {
		String msg = "static void crearUsuario(PersonaDTO persona, TipoAsociacionDTO tipoAsociaion, UsuarioDTO usuarioRegistro, EntidadDTO entidadRegistro)";
		IAsociarUsuarioEntidadRemote service = getPersonaService();
		if(persona.getPrimerNombre() != null)
				persona.setPrimerNombre(persona.getPrimerNombre().trim());
		persona.setSegundoNombre(persona.getSegundoNombre().trim());
		persona.setPrimerApellido(persona.getPrimerApellido());
		persona.setSegundoApellido(persona.getSegundoApellido());
		persona.setCorreoElectronico(persona.getCorreoElectronico().trim());
		persona.setCorreoAlternativo(persona.getCorreoAlternativo().trim ());
		try {
			service.crearUsuario(persona, tipoAsociacion, usuarioRegistro, entidadRegistro);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public void startUpApp() throws SIGEP2SistemaException {
		String msg = "void startUpApp()";
		IInicioAplicacionRemote servicio = getInicioAplicacionService();
		try {
			servicio.startup();
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

	public static List<PermisoUsuarioRolDTO> obtenerPermisosPorUsuario(long usuario) throws SIGEP2SistemaException {
		String msg = "static List<PermisoUsuarioRolDTO> obtenerPermisosPorUsuario(long usuario)";
		IRecursosMenuRemote service = getRecursosMenuService();
		try {
			return service.obtenerPermisosPorUsuario(usuario);
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

	public static List<PermisoUsuarioRolDTO> obtenerPermisosPorCodigoVentana(String codigoVentana, Long usuarioId,
			List<RolDTO> roles) throws SIGEP2SistemaException {
		String msg = "static PermisoUsuarioRolDTO obtenerPermisosPorCodigoVentana(String codigoVentana)";
		IRecursosMenuRemote service = getRecursosMenuService();
		try {
			return service.obtenerPermisosPorCodigoVentana(codigoVentana, usuarioId, roles);
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

	public static List<PermisoUsuarioRolDTO> obtenerPermisosPorAccion(String accion) throws SIGEP2SistemaException {
		String msg = "static List<PermisoUsuarioRolDTO> obtenerPermisosPorAccion(String accion)";
		IRecursosMenuRemote service = getRecursosMenuService();
		try {
			return service.obtenerPermisosPorAccion(accion);
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

	public static void guardarPersona(PersonaDTO persona, Long tipoAsociacion, Long idUsuarioSesion, Long idEntidad) {
		IAsociarUsuarioEntidadRemote service;
		try {
			service = getPersonaService();
			service.guardarPersona(persona, tipoAsociacion, idUsuarioSesion, idEntidad);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
	}

	public static UsuarioRolEntidadDTO buscarUsuarioRolEntidad(Long codUsuarioEntidad) throws SIGEP2SistemaException {
		String msg = "static UsuarioRolEntidadDTO buscarUsuarioRolEntidad(Long codUsuarioEntidad)";
		IAutenticacionSistemaRemote service = getAutenticacionSistemaService();
		try {
			return service.buscarUsuarioRolEntidad(codUsuarioEntidad);
		} catch (Exception e) {
			logger.warn(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}

	public static boolean usuarioExisteyActivo(PersonaDTO persona, long idEntidad, long tipoAsociacion) {
		IAsociarUsuarioEntidadRemote service;
		try {
			service = getPersonaService();
			return service.usuarioExisteyActivo(persona, idEntidad, tipoAsociacion);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean usuarioExisteyActivo(PersonaDTO persona, long idEntidad) {
		IAsociarUsuarioEntidadRemote service;
		try {
			service = getPersonaService();
			return service.usuarioExisteyActivo(persona, idEntidad);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static UsuarioEntidadDTO consultarUsuarioEntidad(Long codUsuario, Long codEntidad)
			throws SIGEP2SistemaException {
		String msg = "static UsuarioEntidadDTO consultarUsuarioEntidad(Long codUsuario, Long codEntidad)";
		IAutenticacionSistemaRemote service = getAutenticacionSistemaService();
		try {
			return service.consultarUsuarioEntidad(codUsuario, codEntidad);
		} catch (Exception e) {
			logger.warn(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}
}