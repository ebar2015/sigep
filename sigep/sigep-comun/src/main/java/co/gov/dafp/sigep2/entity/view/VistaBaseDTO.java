package co.gov.dafp.sigep2.entity.view;

import java.io.Serializable;

public abstract class VistaBaseDTO implements Serializable {
	private static final long serialVersionUID = -4377837428587397479L;

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract String toString();

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
		VistaBaseDTO other = (VistaBaseDTO) obj;
		if (Long.compare(getId(), other.getId()) != 0 )
			return false;
		return true;
	}
}
