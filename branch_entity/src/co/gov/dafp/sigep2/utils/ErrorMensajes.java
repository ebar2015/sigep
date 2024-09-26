/**
 * 
 */
package co.gov.dafp.sigep2.utils;

import java.io.Serializable;

/**
 * @author joseviscaya
 *
 */
public class ErrorMensajes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5779664234601964114L;
	
	private Boolean error;
	private String mensaje;
	private String mensajeTecnico;
	private String nombreArchivo;
	private String urlArchivo;
	private int codigoEstado;
	
	/**
	 * @return the error
	 */
	public Boolean isError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(Boolean error) {
		this.error = error;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the mensajeTecnico
	 */
	public String getMensajeTecnico() {
		return mensajeTecnico;
	}
	/**
	 * @param mensajeTecnico the mensajeTecnico to set
	 */
	public void setMensajeTecnico(String mensajeTecnico) {
		this.mensajeTecnico = mensajeTecnico;
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
	/**
	 * @return the urlArchivo
	 */
	public String getUrlArchivo() {
		return urlArchivo;
	}
	/**
	 * @param urlArchivo the urlArchivo to set
	 */
	public void setUrlArchivo(String urlArchivo) {
		this.urlArchivo = urlArchivo;
	}
	/**
	 * @return the codigoEstado
	 */
	public int getCodigoEstado() {
		return codigoEstado;
	}
	/**
	 * @param codigoEstado the codigoEstado to set
	 */
	public void setCodigoEstado(int codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	

}
