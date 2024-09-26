package co.gov.dafp.sigep2.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumeroUtil {
	public static final String SIMBOLO_MONEDA = "$";
	public static final String SIMBOLO_PORCENTAJE = "%";

	public static final String SEPARADOR_DECIMAL = ".";
	public static final String SEPARADOR_MILES = "\\u002C";

	public static final String FORMATO_MONEDA = NumeroUtil.SIMBOLO_MONEDA + " #,##0.00";
	public static final String FORMATO_DECIMAL = "#,##0.00";
	public static final String FORMATO_ENTERO = "#,##0";
	public static final String FORMATO_PORCENTAJE = "#,##0.00 " + SIMBOLO_PORCENTAJE;

	public NumeroUtil() {
		super();
	}

	public static String formatoMoneda(BigDecimal valor) {
		BigDecimal valorTemp = valor;
		if (valorTemp == null) {
			valorTemp = BigDecimal.ZERO;
		}
		DecimalFormat nf = new DecimalFormat(FORMATO_MONEDA);
		return nf.format(valorTemp.doubleValue());
	}

	public static String formatoPorcentaje(Double valor) {
		Double valorTemp = valor;
		if (valorTemp == null) {
			valorTemp = 0.0;
		}
		DecimalFormat nf = new DecimalFormat(FORMATO_PORCENTAJE);
		return nf.format(valorTemp);
	}

	public static String formatoNumerico(BigInteger valor) {
		BigInteger valorTemp = valor;
		if (valorTemp == null) {
			valorTemp = BigInteger.ZERO;
		}
		DecimalFormat nf = new DecimalFormat(FORMATO_ENTERO);
		return nf.format(valorTemp);
	}

	public static String formatoMoneda(BigDecimal valor, String formato) {
		BigDecimal valorTemp = valor;
		if (valorTemp == null) {
			valorTemp = BigDecimal.ZERO;
		}
		DecimalFormat nf = new DecimalFormat(formato);
		return nf.format(valorTemp.doubleValue());
	}

	public static BigDecimal redondearAMil(BigDecimal valor) {
		BigDecimal valorTemp = valor;
		if (valorTemp == null) {
			valorTemp = BigDecimal.ZERO;
		}
		BigDecimal mil = new BigDecimal(1000L);
		BigDecimal cien = new BigDecimal(100L);
		BigDecimal quinientos = new BigDecimal(500L);
		if (valorTemp != null && valorTemp.compareTo(mil) > 0) {
			BigDecimal aRedondear = valorTemp.divide(mil).multiply(BigDecimal.valueOf(-1L))
					.add(BigDecimal.valueOf(valorTemp.divide(mil).longValue())).multiply(BigDecimal.valueOf(-1L));
			aRedondear = aRedondear.multiply(mil).setScale(0, RoundingMode.UP);
			if (aRedondear.compareTo(quinientos) <= 0) {
				BigDecimal redondeado = BigDecimal.valueOf(valorTemp.divide(cien).longValue()).multiply(cien);
				valorTemp = redondeado;
			} else {
				valorTemp = valorTemp.setScale(-3, RoundingMode.UP);
			}
		}

		return valorTemp;
	}

	public static BigDecimal redondearADecenasMil(BigDecimal valor) {
		BigDecimal valorTemp = valor;
		if (valorTemp == null) {
			valorTemp = BigDecimal.ZERO;
		}
		BigDecimal millon = new BigDecimal(1000000L);
		BigDecimal cienMil = new BigDecimal(100000L);
		BigDecimal quinientosMil = new BigDecimal(500000L);
		if (valorTemp != null && valorTemp.compareTo(millon) >= 0) {
			BigDecimal aRedondear = valorTemp.divide(millon).multiply(BigDecimal.valueOf(-1L))
					.add(BigDecimal.valueOf(valorTemp.divide(millon).longValue())).multiply(BigDecimal.valueOf(-1L));
			aRedondear = aRedondear.multiply(millon).setScale(0, RoundingMode.UP);
			if (aRedondear.compareTo(quinientosMil) <= 0) {
				BigDecimal redondeado = BigDecimal.valueOf(valorTemp.divide(cienMil).longValue()).multiply(cienMil);
				valorTemp = redondeado;
			} else {
				valorTemp = valorTemp.setScale(-5, RoundingMode.UP);
			}
		} else {
			valorTemp = BigDecimal.ZERO;
		}

		return valorTemp;
	}

	public static String getMonedaFormato() {
		return FORMATO_MONEDA;
	}

	public static String getDecimalFormato() {
		return FORMATO_DECIMAL;
	}

	public static String getPorcentajeFormato() {
		return FORMATO_PORCENTAJE;
	}

	public static String getEnteroFormato() {
		return FORMATO_ENTERO;
	}

	public static String getSimboloMoneda() {
		return SIMBOLO_MONEDA;
	}

	public static String getSimboloPorcentaje() {
		return SIMBOLO_PORCENTAJE;
	}

	public static Object sumatoria(Object acumuladoP, Object value) {
		Object acumulado = acumuladoP;
		Object suma = acumulado;
		if (value != null) {
			if (value instanceof BigDecimal) {
				if (!(acumulado instanceof BigDecimal) && ((Integer) acumulado).equals(0)) {
					acumulado = BigDecimal.ZERO;
				}
				suma = ((BigDecimal) acumulado).add((BigDecimal) value);
			} else if (value instanceof Long) {
				if (!(acumulado instanceof Long) && ((Integer) acumulado).equals(0)) {
					acumulado = 0l;
				}
				suma = ((Long) acumulado) + ((Long) value);
			} else if (value instanceof Integer) {
				suma = ((Integer) acumulado) + ((Integer) value);
			} else if (value instanceof Double) {
				if (!(acumulado instanceof Double) && ((Integer) acumulado).equals(0)) {
					acumulado = 0.0;
				}
				suma = ((Double) acumulado) + ((Double) value);
			}
		}
		return suma;
	}
}
