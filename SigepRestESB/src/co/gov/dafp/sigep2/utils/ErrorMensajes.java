/**
 * 
 */
package co.gov.dafp.sigep2.utils;

import java.io.Serializable;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ErrorMensajes.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class ErrorMensajes implements Serializable {

	private static final long serialVersionUID = 5779664234601964114L;
	
	private Boolean error;
	private String mensaje;
	private String mensajeTecnico;
	private Integer codigoEstado;
	private String textoCertificado;
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
	 * @return the codigoEstado
	 */
	public Integer getCodigoEstado() {
		return codigoEstado;
	}
	/**
	 * @param codigoEstado the codigoEstado to set
	 */
	public void setCodigoEstado(Integer codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	/**
	 * @return the textoCertificado
	 */
	public String getTextoCertificado() {
		return textoCertificado;
	}
	/**
	 * @param textoCertificado the textoCertificado to set
	 */
	public void setTextoCertificado(String textoCertificado) {
		this.textoCertificado = textoCertificado;
	}
	

}
