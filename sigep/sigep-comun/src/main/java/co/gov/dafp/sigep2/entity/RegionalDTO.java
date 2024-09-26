package co.gov.dafp.sigep2.entity;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MaestroDTO;

/**
 * Entity implementation class for Entity: Regional
 *
 */
public class RegionalDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 3261358980384049494L;

	protected long id;

	private MaestroDTO maestroId;

	private String descripcion;

	protected Boolean estado = Boolean.TRUE;

	public RegionalDTO() {
		super();
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public MaestroDTO getMaestroId() {
		return maestroId;
	}

	public void setMaestroId(MaestroDTO maestroId) {
		this.maestroId = maestroId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Regional [id=" + id + ", maestroId=" + maestroId.getId() + ", descripcion=" + descripcion + ", estado="
				+ estado + "]";
	}

}
