package co.gov.dafp.sigep2.util.xml;

import java.io.Serializable;

public class Notificacion extends co.gov.dafp.sigep2.util.xml.elemento.Notificacion implements Serializable {
	private static final long serialVersionUID = -7875153605285868491L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desde == null) ? 0 : desde.hashCode());
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
		Notificacion other = (Notificacion) obj;
		if (desde == null) {
			if (other.desde != null)
				return false;
		} else if (!desde.equals(other.desde))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notificacion [desde=" + desde + "]";
	}

}
