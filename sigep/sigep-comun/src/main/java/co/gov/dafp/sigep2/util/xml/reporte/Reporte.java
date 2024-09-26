package co.gov.dafp.sigep2.util.xml.reporte;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reporte", namespace = "http://reporte.xml.util.sigep2.dafp.gov.co/config")
public class Reporte extends co.gov.dafp.sigep2.util.xml.reporte.config.Reporte implements Serializable {
	private static final long serialVersionUID = -2774232505068365989L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Reporte other = (Reporte) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reporte [nombre=" + nombre + "]";
	}
}
