package co.gov.dafp.sigep2.entity.seguridad;

import java.util.List;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class UsuarioEntidadDTO extends EntidadBaseDTO {
	private static final long serialVersionUID = 2135133058923948734L;

	private long id;

	private Long entidad;

	private Long tipoAsociacion;

	private Long usuario;

	private boolean flgEstado;

	private List<UsuarioRolEntidadDTO> usuarioRolEntidades;

	@Override
	public long getId() {
		return id;
	}

	public UsuarioEntidadDTO(long id, Long entidad, Long tipoAsociacion, Long usuario, boolean flgActivo) {
		super();
		this.id = id;
		this.entidad = entidad;
		this.tipoAsociacion = tipoAsociacion;
		this.usuario = usuario;
		this.flgEstado = flgActivo;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}

	public Long getTipoAsociacion() {
		return tipoAsociacion;
	}

	public void setTipoAsociacion(Long tipoAsociacion) {
		this.tipoAsociacion = tipoAsociacion;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public List<UsuarioRolEntidadDTO> getUsuarioRolEntidades() {
		return usuarioRolEntidades;
	}

	public void setUsuarioRolEntidades(List<UsuarioRolEntidadDTO> usuarioRolEntidades) {
		this.usuarioRolEntidades = usuarioRolEntidades;
	}

	public boolean isFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	@Override
	public String toString() {
		return "UsuarioEntidadDTO [id=" + id + ", entidad=" + entidad + ", usuario=" + usuario
				+ ", usuarioRolEntidades=" + usuarioRolEntidades + ", audFechaActualizacion=" + audFechaActualizacion
				+ ", audCodUsuario=" + audCodUsuario + ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}

}
