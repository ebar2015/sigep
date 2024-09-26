package co.gov.dafp.sigep2.util.enums;

public enum TipoAccionEnum {
	DELETE(62, "D", "DELETE"),
	UPDATE(63, "U", "UPDATE"),
	INSERT(785, "I", "INSERT");
	
	private int idAccion;
	private String sigla;
	private String descripcion;
	
	private TipoAccionEnum(int idAccion, String sigla, String descripcion) {
		this.idAccion = idAccion;
		this.sigla = sigla;
		this.descripcion = descripcion;
	}
	
	public int getIdAccion() {
		return idAccion;
	}
	public void setIdAccion(int idAccion) {
		this.idAccion = idAccion;
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
