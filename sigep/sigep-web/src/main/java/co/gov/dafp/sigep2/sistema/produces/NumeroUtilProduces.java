package co.gov.dafp.sigep2.sistema.produces;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import co.gov.dafp.sigep2.util.NumeroUtil;

@RequestScoped
public class NumeroUtilProduces extends NumeroUtil {
	@Named
	@Produces
	public static String getSimboloMoneda() {
		return NumeroUtil.getSimboloMoneda();
	}

	@Named
	@Produces
	public static String getSimboloPorcentaje() {
		return NumeroUtil.getSimboloPorcentaje();
	}

	@Named
	@Produces
	public static String getMonedaFormato() {
		return NumeroUtil.getMonedaFormato();
	}

	@Named
	@Produces
	public static String getDecimalFormato() {
		return NumeroUtil.getDecimalFormato();
	}

	@Named
	@Produces
	public static String getPorcentajeFormato() {
		return NumeroUtil.getPorcentajeFormato();
	}

	@Named
	@Produces
	public static String getEnteroFormato() {
		return NumeroUtil.getEnteroFormato();
	}

}
