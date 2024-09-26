package co.gov.dafp.sigep2.util.xml;

import java.io.Serializable;

public class Parametro extends co.gov.dafp.sigep2.util.xml.elemento.Parametro implements Serializable {
	private static final long serialVersionUID = -569786226517256342L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Parametro other = (Parametro) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Parametro [nombre=" + nombre + "]";
	}

}
