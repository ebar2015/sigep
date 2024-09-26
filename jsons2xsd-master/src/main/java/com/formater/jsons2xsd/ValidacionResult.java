/**
 * 
 */
package com.formater.jsons2xsd;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jose Viscaya
 *
 */
public class ValidacionResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6848739019527767200L;
	
	
	private boolean valido;
    private List<Validacion> validaciones;
    private int codeRespuesta;

	/**
	 * @return the valido
	 */
	public boolean isValido() {
		return valido;
	}


	/**
	 * @param valido the valido to set
	 */
	public void setValido(boolean valido) {
		this.valido = valido;
	}


	/**
	 * @return the validaciones
	 */
	public List<Validacion> getValidaciones() {
		return validaciones;
	}


	/**
	 * @param validaciones the validaciones to set
	 */
	public void setValidaciones(List<Validacion> validaciones) {
		this.validaciones = validaciones;
	}


	/**
	 * @return the codeRespuesta
	 */
	public int getCodeRespuesta() {
		return codeRespuesta;
	}


	/**
	 * @param codeRespuesta the codeRespuesta to set
	 */
	public void setCodeRespuesta(int codeRespuesta) {
		this.codeRespuesta = codeRespuesta;
	}


}
