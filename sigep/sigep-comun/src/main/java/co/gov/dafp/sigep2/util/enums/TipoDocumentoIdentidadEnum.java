package co.gov.dafp.sigep2.util.enums;

public enum TipoDocumentoIdentidadEnum {
	NIT("NI", "Número de Identificación Tributaria","37"),
	CC("CC", "Cédula de Ciudadanía","38"),
	CE("CE", "Cédula de Extranjería","39"),
	TI("TI", "Tarjeta de Identidad","40"),
	RC("RC", "Registro Civil","41"),
	PA("PA", "Pasaporte","42");	
	
	private String sigla;
	private String descripcion;
	private String codigo;
	
	private TipoDocumentoIdentidadEnum(String sigla, String descripcion, String codigo){
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.codigo = codigo;
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

	public final String getCodigo() {
		return codigo;
	}

	public final void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
