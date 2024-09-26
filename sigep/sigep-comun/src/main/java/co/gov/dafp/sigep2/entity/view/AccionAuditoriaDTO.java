package co.gov.dafp.sigep2.entity.view;

import java.io.Serializable;

public class AccionAuditoriaDTO extends VistaBaseDTO implements Serializable {

	private static final long serialVersionUID = -1499488953791340131L;
	
	private Long id;
	private String sigla;
	private String accion;

	public AccionAuditoriaDTO() {
		super();
	}

	public AccionAuditoriaDTO(long id, String sigla, String descripcion) {
		super();
		this.id = id;
		this.sigla = sigla;
		this.accion = descripcion;
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
		return "AccionAuditoriaDTO [id=" + id + ", sigla=" + sigla + ", accion=" + accion + "]";
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
}
