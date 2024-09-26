package co.gov.dafp.sigep2.entities;

import java.util.List;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase para el manejo de tablas disponibles para gestión de la información
 */
public class TablaGestionInformacion extends ErrorMensajes {
	private static final long serialVersionUID = -2629741958526429791L;

	private String codTabla;

	private String nombre;

	private String tipo;

	private List<ColumnaTablaGestionInformacion> listaColumnasTablaGestionInformacion;

	public String getCodTabla() {
		return codTabla;
	}

	public void setCodTabla(String codTabla) {
		this.codTabla = codTabla;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<ColumnaTablaGestionInformacion> getListaColumnasTablaGestionInformacion() {
		return listaColumnasTablaGestionInformacion;
	}

	public void setListaColumnasTablaGestionInformacion(
			List<ColumnaTablaGestionInformacion> listaColumnasTablaGestionInformacion) {
		this.listaColumnasTablaGestionInformacion = listaColumnasTablaGestionInformacion;
	}

	@Override
	public String toString() {
		return "TablaGestionInformacion [codTabla=" + codTabla + ", nombre=" + nombre
				+ ", listaColumnasTablaGestionInformacion=" + listaColumnasTablaGestionInformacion + "]";
	}
}
