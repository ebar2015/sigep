package co.gov.dafp.sigep2.sistema;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.sistema.ProcesoCargueArchivo;

public class SCargueArchivo {
	private Logger logger = Logger.getInstance(SCargueArchivo.class);

	private ProcesoCargueArchivo procesoCargueArchivo;

	public SCargueArchivo(ProcesoCargueArchivo cargueArchivoService) {
		this.procesoCargueArchivo = cargueArchivoService;
	}

	public void run() throws SIGEP2SistemaException {
		String msg = "run()";
		try {
			logger.log().info(msg, procesoCargueArchivo.getClass());
			this.procesoCargueArchivo.cargarConfiguracionXML();
			logger.log().info(msg, "Lanzar Cargue");
			this.procesoCargueArchivo.lanzarCargue();
			logger.log().info(msg, "El cargue del archivo ha finalizado. Validar log para confirmar el resultado.");
		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new SIGEP2SistemaException(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO));
		}
	}

}
