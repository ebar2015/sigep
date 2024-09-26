package co.gov.dafp.sigep2.util.xml;

import java.io.Serializable;

public class SQL extends co.gov.dafp.sigep2.util.xml.elemento.SQL implements Serializable {
	private static final long serialVersionUID = -1526325965612687294L;

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
		SQL other = (SQL) obj;
		if (sentencia == null) {
			if (other.sentencia != null)
				return false;
		} else if (!sentencia.equals(other.sentencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SQL [sentencia=" + sentencia + "]";
	}
}
