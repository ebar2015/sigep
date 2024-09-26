package co.gov.dafp.sigep2.entities;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase para el manejo de columnas de tablas disponibles para gestión de la
 * información
 */
public class ColumnaTablaGestionInformacion extends ErrorMensajes {
	private static final long serialVersionUID = 5848089155282679767L;

	private String codColumna;

	private String nombre;

	private String tipoDato;

	public String getCodColumna() {
		return codColumna;
	}

	public void setCodColumna(String codColumna) {
		this.codColumna = codColumna;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	@Override
	public String toString() {
		return "ColumnaTablaGestionInformacion [codColumna=" + codColumna + ", nombre=" + nombre + ", tipoDato="
				+ tipoDato + "]";
	}
}
