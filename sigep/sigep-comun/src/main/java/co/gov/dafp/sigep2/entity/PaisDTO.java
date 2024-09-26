package co.gov.dafp.sigep2.entity;

import java.io.Serializable;

import co.gov.dafp.sigep2.converter.CapitalCaseConverter;

public class PaisDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = -5791364205407419573L;

	private long id;

	private String nombrePais;

	private boolean flgActivo;

	public PaisDTO() {
		super();
	}

	public PaisDTO(long id, String nombrePais, boolean flgActivo) {
		super();
		this.id = id;
		this.nombrePais = nombrePais;
		this.flgActivo = flgActivo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombrePais() {
		return CapitalCaseConverter.convert(nombrePais);
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	public boolean isFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	@Override
	public String toString() {
		return "PaisDTO [id=" + this.getId() + " nombrePais=" + this.getNombrePais() + " flgEstado="
				+ this.isFlgActivo() + "]";
	}
}
