package co.gov.dafp.sigep2.util.enums;

public enum TipoParametricaEnum {
	
	PARAM_TABLA("T", "Tablas parametricas"),
	PARAM_PARAMETRO("P", "Valores de las tablas parametricas"),
	PARAM_SISTEMA("S", "Para almacenar valores de constantes, mensajes, configuraciones");
	
	private String tipo;
	private String descripcion;
	
	private TipoParametricaEnum(String tipo, String descripcion) {
		this.tipo = tipo;
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


}
