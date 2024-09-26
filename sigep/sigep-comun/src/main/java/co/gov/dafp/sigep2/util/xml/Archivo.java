package co.gov.dafp.sigep2.util.xml;

import java.io.Serializable;

public class Archivo extends co.gov.dafp.sigep2.util.xml.elemento.Archivo implements Serializable {
	private static final long serialVersionUID = -5233710079376880314L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((mascara == null) ? 0 : mascara.hashCode());
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
		Archivo other = (Archivo) obj;
		if (extension != other.extension)
			return false;
		if (mascara == null) {
			if (other.mascara != null)
				return false;
		} else if (!mascara.equals(other.mascara))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Archivo [nombre=" + nombre + ", mascara=" + mascara + ", extension=" + extension + "]";
	}

}
