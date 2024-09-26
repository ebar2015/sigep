package co.gov.dafp.sigep2.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.converter.CapitalCaseConverter;
import co.gov.dafp.sigep2.util.xml.reporte.config.CalificadorComparacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDato;

public class Parametro implements Serializable, Cloneable {
	private static final long serialVersionUID = -211419560422645618L;
	private Object key;
	private Object value;
	private String label;
	private String type;
	private String source;
	private boolean required;
	private boolean rendered = true;

	transient ConfiguracionParametro configuracionParametro;

	public Parametro(Parametro parametro) {
		this.key = parametro.key;
		if (this.key != null && !this.key.toString().isEmpty() && this.key instanceof String) {
			this.key = StringUtil.reemplazarAcentos(this.key.toString());
		}
		this.value = parametro.value;
		this.label = parametro.label;
		this.type = parametro.type;
		this.source = parametro.source;
		this.required = parametro.required;
		this.rendered = parametro.rendered;

		this.configuracionParametro = parametro.configuracionParametro;
	}

	public Parametro(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	public Parametro(Object key, String type) {
		this.key = key;
		this.type = type;
	}

	@Override
	public String toString() {
		return value == null ? null : "" + CapitalCaseConverter.convert(value);
	}

	public Object getKey() {
		return key;
	}

	public Object getValue() {
		if (this.value instanceof List<?>) {
			return (List<?>) this.value;
		}
		if (this.type != null) {
			if (this.type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_BOOLEAN.value()) && this.value == null) {
				this.value = Boolean.FALSE;
			} else if (this.type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE.value()) && this.value != null) {
				this.value = this.getDateValue();
			} else if (this.type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_STRING.value())) {
				this.value = this.getStringValue();
			} else if (this.type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_QUERY.value())) {
				this.value = this.toString();
			}
		}
		return value;
	}

	public String getLabel() {
		return CapitalCaseConverter.convert(label);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public BigDecimal getBigDecimalValue() {
		return BigDecimal.valueOf(getDoubleValue());
	}

	public void setBigDecimalValue(BigDecimal bigDecimalValue) {
		value = bigDecimalValue;
	}

	public String getCurrencyValue() {
		return NumeroUtil.formatoMoneda(BigDecimal.valueOf(getDoubleValue())).replace(NumeroUtil.SEPARADOR_MILES, ",")
				.replace(NumeroUtil.SEPARADOR_DECIMAL, ".");
	}

	public String getNumericValue() {
		return NumeroUtil.formatoNumerico(BigInteger.valueOf(getLongValue())).replace(NumeroUtil.SEPARADOR_MILES, ",")
				.replace(NumeroUtil.SEPARADOR_DECIMAL, ".");
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Double getDoubleValue() {
		if (this.value == null) {
			return 0.0;
		}
		try {
			return new Double(toString());
		} catch (NumberFormatException e) {
		}
		return null;
	}

	public void setDoubleValue(Double doubleValue) {
		value = doubleValue;
	}

	public String getPercentValue() {
		return NumeroUtil.formatoPorcentaje(getDoubleValue()).replace(NumeroUtil.SEPARADOR_MILES, ",")
				.replace(NumeroUtil.SEPARADOR_DECIMAL, ".");
	}

	public void getPercentValue(Double percentValue) {
		value = percentValue;
	}

	public Date getDateValue() {
		if (this.value == null) {
			return null;
		}
		if (this.type != null && (this.type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE.value())
				|| this.type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE_STRING.value()))) {
			SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_FORMATO);
			try {
				return format.parse(toString());
			} catch (Exception e) {
			}
		}
		return (Date) this.value;
	}

	public String getMonthValue() {
		if (this.value == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.MES_FORMATO);
		try {
			return format.format(getDateValue());
		} catch (Exception e) {
		}
		return null;
	}

	public String getPeriodValue() {
		if (this.value == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.PERIODO_FORMATO);
		try {
			return format.format(getDateValue());
		} catch (Exception e) {
		}
		return null;
	}

	public String getMonthLargeValue() {
		if (this.value == null) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.MES_LARGO_FORMATO);
		try {
			return format.format(getDateValue());
		} catch (Exception e) {
		}
		return null;
	}

	public String getStringValue() {
		if (this.value != null && !this.value.toString().isEmpty()) {
			
			/*if (!this.getDecryptValue().isEmpty()) {
				return this.getDecryptValue();
			}
			*/

			if (this.type == null) {
				return toString();
			}

			try {
				if (!TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_STRING.value().equals(this.type)) {
					BigDecimal.valueOf(new Double(toString()));
					return null;
				}
			} catch (NumberFormatException e) {
				try {
					if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE_STRING.value().equals(this.type)) {
						return DateUtils.formatearACadena((Date) value);
					}
				} catch (Exception e2) {
				}
			}

			return toString();
		} else {
			return null;
		}
	}

	public String getDecryptValue() {
		try {
			String separator = ConfigurationBundleConstants
					.getString(ConfigurationBundleConstants.SEPARADOR_ENCRIPTADO);
			if (this.value != null && toString().toLowerCase().contains(separator.toLowerCase())) {
				String[] strings = toString().split(separator);
				String stringReturn = "";
				for (String string : strings) {
					stringReturn = stringReturn + StringEncrypt.decrypt(ConfigurationBundleConstants.key(),
							ConfigurationBundleConstants.iv(), string) + " ";
				}
				return stringReturn.trim();
			} else {
				return StringEncrypt.decrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(),
						toString());
			}
		} catch (Exception e) {
			return "";
		}
	}

	public Long getLongValue() {
		if (this.value == null) {
			return null;
		}

		try {
			return Long.valueOf(toString());
		} catch (NumberFormatException e) {
		}

		return null;
	}

	public String getValorFormateado() {
		try {
			if (value != null && type != null) {
				if (type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DECIMAL.value())) {
					return getCurrencyValue();
				} else if (type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_INTEGER.value())) {
					return getNumericValue();
				} else if (type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_PERCENT.value())) {
					return getPercentValue();
				}
				return value.toString();
			}
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * Da formato a un valor teniendo en cuenta el tipo de dato
	 * 
	 * @param type Tipo de dato al que se va a dar formato
	 * @param mask mascara o formato que revibira el valor en procesamiento
	 * 
	 * @return {@link String} cadena con el valor formateado de acuerdo a
	 *         <code>mask</code>
	 */
	public String getValorFormateado(String type, String mask) {
		try {
			if (value != null) {
				if (type == null || mask == null) {
					return value.toString();
				} else if (type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DECIMAL.value())
						|| type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_INTEGER.value())
						|| type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_PERCENT.value())) {
					return NumeroUtil.formatoMoneda(getBigDecimalValue(), mask);
				} else if (type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE.value())) {
					return DateUtils.formatearACadena(getDateValue(), mask);
				} else if (type.equals(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_STRING.value()) && !".*".equals(mask)) {
					return String.format(mask, toString());
				} else {
					return value.toString();
				}
			}
		} catch (Exception e) {
			return value.toString();
		}
		return "";
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Parametro))
			return false;
		Parametro other = (Parametro) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	public String getCapitalCase() {
		return StringUtil.UppercaseFirstLetters(this.getStringValue());
	}

	public Parametro clone() {
		return new Parametro(this);
	}

	public ConfiguracionParametro getConfiguracionParametro() {
		return configuracionParametro;
	}

	public void setConfiguracionParametro(ConfiguracionParametro configuracionParametro) {
		this.configuracionParametro = configuracionParametro;
	}

	public class ConfiguracionParametro {
		private TipoDato tipoDato;
		private String prefijoTabla;
		private String nombreColumna;
		private String valorParametro;
		private CalificadorComparacion calificadorComparacion;

		private String comparador;

		public ConfiguracionParametro(TipoDato tipoDato, String prefijoTabla, String nombreColumna,
				String valorParametro, CalificadorComparacion calificadorComparacion) {
			super();
			this.tipoDato = tipoDato;
			this.prefijoTabla = prefijoTabla;
			this.nombreColumna = nombreColumna;
			this.valorParametro = valorParametro;
			this.calificadorComparacion = calificadorComparacion;
		}

		public TipoDato getTipoDato() {
			return tipoDato;
		}

		public void setTipoDato(TipoDato tipoDato) {
			this.tipoDato = tipoDato;
		}

		public String getPrefijoTabla() {
			return prefijoTabla;
		}

		public void setPrefijoTabla(String prefijoTabla) {
			this.prefijoTabla = prefijoTabla;
		}

		public String getNombreColumna() {
			return nombreColumna;
		}

		public void setNombreColumna(String nombreColumna) {
			this.nombreColumna = nombreColumna;
		}

		public String getValorParametro() {
			return valorParametro;
		}

		public void setValorParametro(String valorParametro) {
			this.valorParametro = valorParametro;
		}

		public CalificadorComparacion getCalificadorComparacion() {
			return calificadorComparacion;
		}

		public void setCalificadorComparacion(CalificadorComparacion calificadorComparacion) {
			this.calificadorComparacion = calificadorComparacion;
		}

		public String getComparador() {
			if (CalificadorComparacion.TTL_REPORTES_IGNORAR.equals(calificadorComparacion)
					|| CalificadorComparacion.TTL_REPORTES_IGUAL.equals(calificadorComparacion)) {
				comparador = "=";
			} else if (CalificadorComparacion.TTL_REPORTES_DIFERENTE_A.equals(calificadorComparacion)) {
				comparador = "!=";
			} else if (CalificadorComparacion.TTL_REPORTES_MAYOR_IGUAL_QUE.equals(calificadorComparacion)) {
				comparador = ">=";
			} else if (CalificadorComparacion.TTL_REPORTES_MAYOR_QUE.equals(calificadorComparacion)) {
				comparador = ">";
			} else if (CalificadorComparacion.TTL_REPORTES_MENOR_IGUAL_QUE.equals(calificadorComparacion)) {
				comparador = "<=";
			} else if (CalificadorComparacion.TTL_REPORTES_MENOR_QUE.equals(calificadorComparacion)) {
				comparador = "<";
			}
			return comparador;
		}

		public void setComparador(String comparador) {
			this.comparador = comparador;
		}
	}
}
