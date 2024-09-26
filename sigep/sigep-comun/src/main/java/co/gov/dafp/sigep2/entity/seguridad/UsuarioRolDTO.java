package co.gov.dafp.sigep2.entity.seguridad;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

/**
 * Entity implementation class for Entity: EntidadBaseDTO
 *
 */
public class UsuarioRolDTO extends EntidadBaseDTO {
	private static final long serialVersionUID = 6303486733839716776L;

	protected long id;

	private Long usuarioId;

	private Long rolId;

	public UsuarioRolDTO() {
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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getRolId() {
		return rolId;
	}

	public void setRolId(Long rolId) {
		this.rolId = rolId;
	}

	@Override
	public String toString() {
		return "UsuarioRolDTO [id=" + id + ", usuarioId=" + usuarioId + ", rolId=" + rolId + ", audFechaActualizacion="
				+ audFechaActualizacion + ", audCodUsuario=" + audCodUsuario + ", audCodRol=" + audCodRol
				+ ", audAccion=" + audAccion + "]";
	}

}
