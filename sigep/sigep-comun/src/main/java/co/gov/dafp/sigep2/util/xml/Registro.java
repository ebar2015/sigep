package co.gov.dafp.sigep2.util.xml;

import java.io.Serializable;

public class Registro extends co.gov.dafp.sigep2.util.xml.elemento.Registro implements Serializable {
	private static final long serialVersionUID = 2272960136062436914L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((tipoRegistro == null) ? 0 : tipoRegistro.hashCode());
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
		Registro other = (Registro) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (tipoRegistro != other.tipoRegistro)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Registro [descripcion=" + descripcion + ", tipoRegistro=" + tipoRegistro + "]";
	}
}
