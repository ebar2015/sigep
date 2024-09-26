package co.gov.dafp.sigep2.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.interfaces.ICarguesMasivosRemote;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class CarguesMasivosEJB implements ICarguesMasivosRemote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getInstance(CarguesMasivosEJB.class);
	
	@EJB
    private IEnvioCorreoLocal mailService;

	@Override
	public void emailResultadoCargueMasivo(List<String> email, List<String> ccEmail, String resultado, String proceso)
			throws SIGEP2SistemaException {
	try {
	    String asunto = "Resultado Proceso Cargue Masivo - " + proceso;
	    String body = HTMLUtil.abreParrafo + resultado;
	    body += HTMLUtil.espacioEnBlanco;
	    body += HTMLUtil.cierraParrafo;
	    mailService.enviarMail(asunto, body, null, email, ccEmail);
	} catch (Exception e) {
	    e.getStackTrace();
	    logger.log().error("EJB - emailResultadoCargueMasivo(List<String> email, List<String> ccEmail, String resultado)", e);
	    throw new SIGEP2SistemaException(e.getMessage());
	}
    }
}
