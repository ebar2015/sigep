package co.gov.dafp.sigep2.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public abstract class EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = -4294390193776168428L;

	protected Date audFechaActualizacion;

	protected BigDecimal audCodUsuario;

	protected BigDecimal audCodRol;

	protected String audAccion;

	public abstract long getId();

	public abstract void setId(long id);

	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	public BigDecimal getAudCodRol() {
		return audCodRol;
	}

	public void setAudCodRol(BigDecimal audCodRol) {
		this.audCodRol = audCodRol;
	}

	public String getAudAccion() {
		return audAccion;
	}

	public void setAudAccion(String audAccion) {
		this.audAccion = audAccion;
	}

	public abstract String toString();

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
		EntidadBaseDTO other = (EntidadBaseDTO) obj;
		if (Long.compare(getId(), other.getId()) !=0)
			return false;
		return true;
	}
}
