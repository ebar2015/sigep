package co.gov.dafp.sigep2.sistema;

import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.sistema.ProcesoCargueArchivo;

public class HCargueArchivo extends Thread {
	private Logger logger = Logger.getInstance(HCargueArchivo.class);

	private ProcesoCargueArchivo procesoCargueArchivo;

	public HCargueArchivo(ProcesoCargueArchivo cargueArchivoService) {
		this.procesoCargueArchivo = cargueArchivoService;
	}

	@Override
	public void run() {
		String msg = "run()";
		try {
			logger.log().info(msg, procesoCargueArchivo.getClass());
			this.procesoCargueArchivo.cargarConfiguracionXML();
			logger.log().info(msg, "Lanzar Cargue");
			this.procesoCargueArchivo.lanzarCargue();
			logger.log().info(msg, "El cargue del archivo ha finalizado. Validar log para confirmar el resultado.");
		} catch (Exception e) {
			logger.log().error(msg, e);
		}
	}

}
