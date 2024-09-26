package co.gov.dafp.sigep2.entity;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBase;

public abstract class EntidadBase implements Serializable {
	
	private static final long serialVersionUID = -4294390193776168428L;

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract Date getAudFechaActualizacion();

	public abstract void setAudFechaActualizacion(Date audFechaActualizacion);

	public abstract Long getAudCodUsuario();

	public abstract void setAudCodUsuario(Long audCodUsuario);

	public abstract Long getAudCodRol();

	public abstract void setAudCodRol(Long audCodRol);

	public abstract Long getAudAccion();

	public abstract void setAudAccion(Long audAccion);

	public abstract String toString();

	public abstract EntidadBaseDTO getDTO();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
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
		EntidadBase other = (EntidadBase) obj;
		if (Long.compare(getId(), other.getId()) !=0)
			return false;
		return true;
	}
}
