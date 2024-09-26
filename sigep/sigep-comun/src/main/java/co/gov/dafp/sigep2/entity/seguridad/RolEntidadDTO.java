package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;

import javax.persistence.*;

import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

/**
 * Entity implementation class for Entity: Rol
 *
 */
public class RolEntidadDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = -7273141132693729603L;

	protected long id;

	private RolDTO rolId;

	private EntidadBaseDTO entidadId;

	@Convert(converter = UpperCaseConverter.class)
	private String descripcion;

	public RolEntidadDTO() {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public RolDTO getRolId() {
		return rolId;
	}

	public void setRolId(RolDTO rolId) {
		this.rolId = rolId;
	}

	public EntidadBaseDTO getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(EntidadBaseDTO entidadId) {
		this.entidadId = entidadId;
	}

	@Override
	public String toString() {
		return "RolEntidadDTO [id=" + id + ", rolId=" + rolId + ", entidadId=" + entidadId + ", descripcion="
				+ descripcion + ", audFechaActualizacion=" + audFechaActualizacion + ", audCodUsuario=" + audCodUsuario
				+ ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}

}
