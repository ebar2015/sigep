/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.Cargo;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  CargoExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class CargoExt extends Cargo implements Serializable {

	private static final long serialVersionUID = -3627109266305071680L;
	
	private String descNivelCargo;
	private String descGradoCargo;
	private int total;
	private String gradoNombre;
	
	/**
	 * @return the descNivelCargo
	 */
	public String getDescNivelCargo() {
		return descNivelCargo;
	}
	/**
	 * @param descNivelCargo the descNivelCargo to set
	 */
	public void setDescNivelCargo(String descNivelCargo) {
		this.descNivelCargo = descNivelCargo;
	}
	/**
	 * @return the descGradoCargo
	 */
	public String getDescGradoCargo() {
		return descGradoCargo;
	}
	/**
	 * @param descGradoCargo the descGradoCargo to set
	 */
	public void setDescGradoCargo(String descGradoCargo) {
		this.descGradoCargo = descGradoCargo;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the gradoNombre
	 */
	public String getGradoNombre() {
		return gradoNombre;
	}
	/**
	 * @param gradoNombre the gradoNombre to set
	 */
	public void setGradoNombre(String gradoNombre) {
		this.gradoNombre = gradoNombre;
	}
	

}
