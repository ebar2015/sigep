package co.gov.dafp.sigep2.util.xml.reporte;

import java.io.Serializable;

/**
 * Clase para el manejo de la sentencia de ordenamiento SQL de query de la
 * plantilla
 */
public class Ordenamiento implements Serializable {
	private static final long serialVersionUID = -8812134271497337427L;

	private static final String ASC = "asc";
	private static final String DESC = "desc";

	private static final String ASCENDING = "ASCENDING";
	private static final String DESCENDING = "DESCENDING";

	private StringBuilder sentencia = new StringBuilder();

	Ordenamiento() {
		super();
	}

	public Ordenamiento addOrdenamiento(String nombreColumna, String criterio) {
		String separador = ", ";

		String criterioVal;
		String criterioValInv;

		if (criterio != null && !DESCENDING.equals(criterio) && !ASCENDING.equals(criterio)) {
			throw new IllegalArgumentException();
		}
		if (nombreColumna == null) {
			throw new IllegalArgumentException();
		}

		if (criterio != null && DESCENDING.equals(criterio)) {
			criterioVal = DESC;
			criterioValInv = ASC;
		} else {
			criterioVal = ASC;
			criterioValInv = DESC;
		}

		String sentenciaTemp = nombreColumna.toLowerCase() + " " + criterioVal;
		String sentenciaTempInv = nombreColumna.toLowerCase() + " " + criterioValInv;

		if (this.sentencia.toString().contains(sentenciaTemp)) {
			return this;
		} else {
			this.sentencia = new StringBuilder(this.sentencia.toString().replace(sentenciaTempInv + separador, ""));
			this.sentencia = new StringBuilder(this.sentencia.toString().replace(sentenciaTempInv, ""));
		}

		if (!this.sentencia.toString().trim().isEmpty() && !this.sentencia.toString().endsWith(separador)) {
			this.sentencia.append(separador);
		}
		this.sentencia.append(sentenciaTemp);

		return this;
	}

	private String getSQL() {
		if (this.sentencia == null || this.sentencia.toString().trim().isEmpty()) {
			return "";
		}
		return " order by " + this.sentencia.toString().trim();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sentencia == null) ? 0 : sentencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ordenamiento other = (Ordenamiento) obj;
		if (sentencia == null) {
			if (other.sentencia != null)
				return false;
		} else if (!sentencia.equals(other.sentencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getSQL();
	}

}
