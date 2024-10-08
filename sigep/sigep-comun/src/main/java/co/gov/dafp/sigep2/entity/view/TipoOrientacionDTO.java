package co.gov.dafp.sigep2.entity.view;

import java.io.Serializable;

public class TipoOrientacionDTO extends VistaBaseDTO implements Serializable {
	private static final long serialVersionUID = -2378173881386599264L;

	private long id;
	private String sigla;
	private String descripcion;

	public TipoOrientacionDTO() {
		super();
	}

	public TipoOrientacionDTO(long id, String sigla, String descripcion) {
		super();
		this.id = id;
		this.sigla = sigla;
		this.descripcion = descripcion;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "TipoOrientacionDTO [id=" + id + ", sigla=" + sigla + ", descripcion=" + descripcion + "]";
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}