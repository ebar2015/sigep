package co.gov.dafp.sigep2.entity;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class DepartamentoDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long id; 
	
	private long codPais;
	
	private String nombreDepartamento;

	private boolean flgActivo;	

	public DepartamentoDTO(){
		super();
	}
	
	public DepartamentoDTO( long id, long codPais, String nombreDepartamento, boolean flgActivo){
		super();
		this.id = id;
		this.codPais = codPais;
		this.nombreDepartamento = nombreDepartamento;
		this.flgActivo = flgActivo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodPais() {
		return codPais;
	}

	public void setCodPais(long codPais) {
		this.codPais = codPais;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public boolean isFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	@Override
	public String toString() {
		return "DepartamentoDTO [id=" + this.getId() + " codPais=" + this.getCodPais() + " nombreDepartamento=" + this.getNombreDepartamento() + " flgEstado=" + this.isFlgActivo() + "]";
	}
}
