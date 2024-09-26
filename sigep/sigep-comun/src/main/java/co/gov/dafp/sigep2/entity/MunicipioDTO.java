package co.gov.dafp.sigep2.entity;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class MunicipioDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long id;
	
	private long codDepartamento;
	
	private long codMunicipioDane;
	
	private String nombreMunicipio;

	private boolean flgActivo;	

	public MunicipioDTO(){
		super();
	}
	
	public MunicipioDTO( long id, long codDepartamento, long codMunicipioDane,  String nombreMunicipio, boolean flgActivo){
		super();
		this.id = id;
		this.codDepartamento = codDepartamento;
		this.codMunicipioDane = codMunicipioDane;
		this.nombreMunicipio = nombreMunicipio;
		this.flgActivo = flgActivo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(long codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public long getCodMunicipioDane() {
		return codMunicipioDane;
	}

	public void setCodMunicipioDane(long codMunicipioDane) {
		this.codMunicipioDane = codMunicipioDane;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public boolean isFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	@Override
	public String toString() {
		return "DepartamentoDTO [id=" + this.getId() + " codDepartamento=" + this.getCodDepartamento() + " codMunicipioDane=" + this.getCodMunicipioDane() + " nombreMunicipio=" + this.getNombreMunicipio() + " flgEstado=" + this.isFlgActivo() + "]";
	}
}