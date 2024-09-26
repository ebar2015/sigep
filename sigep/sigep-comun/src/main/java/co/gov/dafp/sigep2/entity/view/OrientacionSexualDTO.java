package co.gov.dafp.sigep2.entity.view;

import java.io.Serializable;


public class OrientacionSexualDTO extends VistaBaseDTO implements Serializable {
	private static final long serialVersionUID = -2378173881386599264L;

	private long id;
	private long padre;
	private String nombre;
	private String valor;

	public OrientacionSexualDTO() {
		super();
	}

	public OrientacionSexualDTO(long id, long padre, String nombre, String valor) {
		super();
		this.id = id;
		this.padre = padre;
		this.nombre = nombre;
		this.valor = valor;
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
		return "TipoDocumentoDTO [id=" + id + ", padre=" + padre + ", nombre=" + nombre + ", valor=" + valor +"]";
	}

	public Long getPadre() {
		return this.padre;
	}

	public void setPadre(Long padre) {
		this.padre = padre;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}