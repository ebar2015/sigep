package co.gov.dafp.sigep2.vo;

import java.io.Serializable;

public abstract class EntidadVO implements Serializable {
	private static final long serialVersionUID = 3323900228355981760L;

	private long id;
	private Boolean estado = Boolean.TRUE;

	public EntidadVO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		EntidadVO other = (EntidadVO) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
