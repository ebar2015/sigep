package co.gov.dafp.sigep2.entity.seguridad;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class UsuarioRolEntidadDTO extends EntidadBaseDTO {
	private static final long serialVersionUID = 6861157223333018456L;

	private long id;

	private Long rol;

	private Long usuarioEntidad;

	private boolean flgActivo;

	private boolean flgEstado;
	
	private RolDTO rolDTO;
	
	private UsuarioEntidadDTO usuarioEntidadDTO;
	
	public UsuarioRolEntidadDTO(long id, long rol, long usuarioEntidad, boolean flgActivo, boolean flgEstado){
		this.id = id;
		this.rol = rol;
		this.usuarioEntidad = usuarioEntidad;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
	}
	
	public UsuarioRolEntidadDTO(long id, RolDTO rolDTO, UsuarioEntidadDTO usuarioEntidadDTO, boolean flgActivo, boolean flgEstado){
		this.id = id;
		this.rolDTO = rolDTO;
		this.usuarioEntidadDTO = usuarioEntidadDTO;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getRol() {
		return rol;
	}

	public void setRol(Long rol) {
		this.rol = rol;
	}

	public Long getUsuarioEntidad() {
		return usuarioEntidad;
	}

	public void setUsuarioEntidad(Long usuarioEntidad) {
		this.usuarioEntidad = usuarioEntidad;
	}

	public boolean isFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	public boolean isFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	public RolDTO getRolDTO() {
		return rolDTO;
	}

	public void setRolDTO(RolDTO rolDTO) {
		this.rolDTO = rolDTO;
	}

	public UsuarioEntidadDTO getUsuarioEntidadDTO() {
		return usuarioEntidadDTO;
	}

	public void setUsuarioEntidadDTO(UsuarioEntidadDTO usuarioEntidadDTO) {
		this.usuarioEntidadDTO = usuarioEntidadDTO;
	}

	@Override
	public String toString() {
		return "UsuarioRolEntidadDTO [id=" + id + ", rol=" + rol + ", usuarioEntidad=" + usuarioEntidad + ", flgActivo="
				+ flgActivo + ", flgEstado=" + flgEstado + ", audFechaActualizacion=" + audFechaActualizacion
				+ ", audCodUsuario=" + audCodUsuario + ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}
}
