package co.gov.dafp.sigep2.util.sistema;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;

public class ReporteDefault extends ProcesoReporte {

	@Override
	public String validarFiltrosReporte() {
		String msg = "String validarFiltrosReporte()";
		String validacion = "";
		Parametro fechaInicial = new Parametro("1_fecha_inicial", null);
		Parametro fechaFinal = new Parametro("2_fecha_final", null);
		try {
			fechaInicial = this.parametros.get(this.parametros.indexOf(fechaInicial));
			fechaFinal = this.parametros.get(this.parametros.indexOf(fechaFinal));

			if ((fechaInicial.getValue() == null && fechaFinal.getValue() != null)
					|| (fechaFinal.getValue() == null && fechaInicial.getValue() != null)) {
				throw new SIGEP2NegocioException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_WARN_RANGO_FECHAS_INCOMPLETO));
			}

			if (fechaInicial != null && fechaFinal != null && fechaInicial.getValue() != null
					&& fechaFinal.getValue() != null && fechaInicial.getDateValue().after(fechaFinal.getDateValue())) {
				throw new SIGEP2NegocioException(
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_WARN_RANGO_FECHAS));
			}
		} catch (IndexOutOfBoundsException e) {
			logger.info(msg, e);
		} catch (SIGEP2NegocioException e) {
			logger.info(msg, e);
			validacion = e.getMessage();
		}
		return validacion;
	}
}
