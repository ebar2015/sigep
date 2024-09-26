package co.gov.dafp.sigep2.deledago;

import java.util.List;

import javax.naming.NamingException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.interfaces.ICarguesMasivosRemote;
import co.gov.dafp.sigep2.interfaces.IServiceRemote;
import co.gov.dafp.sigep2.sistema.locator.ServiceLocator;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

public class CarguesMasivosDelegate {
	
	private static final Logger logger = Logger.getInstance(CarguesMasivosDelegate.class);
	private static final String CARGUES_MASIVOS_EJB = "CarguesMasivosEJB";
	
	public static void emailResultadoCargueMasivo(List<String> email, List<String> ccEmail, String resultado, String proceso) throws SIGEP2SistemaException {
		String msg = "emailResultadoCargueMasivo(List<String> email, List<String> ccEmail, String resultado)";
		ICarguesMasivosRemote service = getCarguesMasivosService();
		try {
			service.emailResultadoCargueMasivo(email, ccEmail, resultado, proceso);
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2SistemaException(e.getMessage());
		}
	}
	
	private static ICarguesMasivosRemote getCarguesMasivosService() throws SIGEP2SistemaException {
		ICarguesMasivosRemote commonService = null;
		try {
			ServiceLocator<IServiceRemote> serviceLocator = ServiceLocator.getInstance();
			String pakageClassName = ICarguesMasivosRemote.class.getName();
			commonService = (ICarguesMasivosRemote) serviceLocator.getService(
					ConfigurationBundleConstants.getEJBAppName() + "/" + ConfigurationBundleConstants.getEJBModuleName()
							+ "/" + CARGUES_MASIVOS_EJB + "!" + pakageClassName);
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
}
