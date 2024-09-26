package co.gov.dafp.sigep2.entity;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

public abstract class VistaBase implements Serializable {
	private static final long serialVersionUID = -7550071446357289861L;

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract String toString();

	public abstract VistaBaseDTO getDTO();

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VistaBase other = (VistaBase) obj;
		if (getId() != other.getId())
			return false;
		return true;
	}
}
