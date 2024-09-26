package co.gov.dafp.sigep2.entities;

import java.io.Serializable;


public class ArchivoCalidadDatos implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1748555385709935904L;

	private String nombreArchivo;
	private String tipoArchivo;
	private String archivo;
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
	
	
	

}
