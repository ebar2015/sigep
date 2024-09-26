package co.gov.dafp.sigep2.util.xml;

import java.io.Serializable;

public class Validacion extends co.gov.dafp.sigep2.util.xml.elemento.Validacion implements Serializable {
	private static final long serialVersionUID = -2114949125867691448L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipoValidacion == null) ? 0 : tipoValidacion.hashCode());
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
		Validacion other = (Validacion) obj;
		if (tipoValidacion != other.tipoValidacion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Validacion [tipoValidacion=" + tipoValidacion + "]";
	}

}
