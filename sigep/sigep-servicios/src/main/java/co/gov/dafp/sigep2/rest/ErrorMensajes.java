/**
 * 
 */
package co.gov.dafp.sigep2.rest;

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
	
	private boolean error;
	private String mensaje;
	private String mensajeTecnico;
	private int codigo;
	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
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
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return getMensaje() + (getMensajeTecnico()!=null?getMensajeTecnico():"");
	}
	
	
	

}
