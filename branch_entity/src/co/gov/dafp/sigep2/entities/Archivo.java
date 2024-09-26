/**
 * 
 */
package co.gov.dafp.sigep2.entities;

import java.io.Serializable;

/**
 * @author joseviscaya
 *
 */
public class Archivo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2431504113093702389L;
	
	private String archivodata;
	private String nombreArchivo;
	/**
	 * @return the archivodata
	 */
	public String getArchivodata() {
		return archivodata;
	}
	/**
	 * @param archivodata the archivodata to set
	 */
	public void setArchivodata(String archivodata) {
		this.archivodata = archivodata;
	}
	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	/**
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	

}
