package co.gov.dafp.sigep2.deledago;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entity.ReporteDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.interfaces.IReporteRemote;
import co.gov.dafp.sigep2.interfaces.IServiceRemote;
import co.gov.dafp.sigep2.sistema.locator.ServiceLocator;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.Registro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;

public class TransversalDelegate {

	private static final Logger logger = Logger.getInstance(TransversalDelegate.class);

	private static final String REPORTE_EJB = "ReporteEJB";

	private static IReporteRemote getReporteServiceService() throws SIGEP2SistemaException {
		IReporteRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = IReporteRemote.class.getName();
			commonService = (IReporteRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + REPORTE_EJB + "!" + pakageClassName);
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

	public static List<Registro> exec(final co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistro,
			final XmlReporte proceso, final UsuarioDTO usuarioReporte, final List<Parametro> parametros,
			final Date fechaReporte, final String rutaArchivoSalida, int first, int pageSize, boolean generarParaCorreo)
			throws SIGEP2NegocioException {
		String msg = "static List<Registro> exec(final co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistro, final XmlReporte proceso, final UsuarioDTO usuarioReporte, final List<Parametro> parametros, final Date fechaReporte, final String rutaArchivoSalida, int first, int pageSize, boolean generarParaCorreo)";
		try {
			IReporteRemote service = getReporteServiceService();
			return service.exec(tipoRegistro, proceso, usuarioReporte, parametros, fechaReporte, rutaArchivoSalida,
					first, pageSize, generarParaCorreo);
		} catch (NullPointerException e) {
			logger.error(msg, e);
			throw new SIGEP2NegocioException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2NegocioException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

	public static String validarReporte(ReporteDTO proceso, List<Parametro> parametros) {
		return null;
	}

	public static ReporteDTO getReporte(Long id) throws SIGEP2SistemaException {
		String msg = "static ReporteDTO getReporte(Long id)";
		try {
			IReporteRemote service = getReporteServiceService();
			return service.getReporte(id);
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

	public static ReporteDTO getReporte(String descripcion) throws SIGEP2SistemaException {
		String msg = "static ReporteDTO getReporte(String descripcion)";
		try {
			IReporteRemote service = getReporteServiceService();
			return service.getReporte(descripcion);
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

	public static int getRowsCount() throws SIGEP2SistemaException {
		String msg = "static int getRowsCount()";
		try {
			IReporteRemote service = getReporteServiceService();
			return service.getRowsCount();
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

	public static RolDTO getRol(String nombre) throws SIGEP2SistemaException {
		return null;
	}

	public static RolDTO getRol(long id) throws SIGEP2SistemaException {
		return null;
	}

	public static List<Object[]> ejecutarQuery(String sqlString, Map<Object, Object> parameters, int maxResult)
			throws SIGEP2SistemaException {
		String msg = "static List<Object[]> ejecutarQuery(String sqlString, Map<Object, Object> parameters, int maxResult)";
		try {
			IReporteRemote service = getReporteServiceService();
			return service.ejecutarQuery(sqlString, parameters, maxResult);
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
