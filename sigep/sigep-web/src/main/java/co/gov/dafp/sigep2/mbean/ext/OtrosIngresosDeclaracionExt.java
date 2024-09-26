/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.OtrosIngresosDeclaracion;

/**
 * @author joseviscaya
 *
 */
public class OtrosIngresosDeclaracionExt extends OtrosIngresosDeclaracion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5163025912890852644L;
	
	private String nombreActividad;
	private String nombreBeneficio;
	/**
	 * @return the nombreActividad
	 */
	public String getNombreActividad() {
		return nombreActividad;
	}
	/**
	 * @param nombreActividad the nombreActividad to set
	 */
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}
	/**
	 * @return the nombreBeneficio
	 */
	public String getNombreBeneficio() {
		return nombreBeneficio;
	}
	/**
	 * @param nombreBeneficio the nombreBeneficio to set
	 */
	public void setNombreBeneficio(String nombreBeneficio) {
		this.nombreBeneficio = nombreBeneficio;
	}

}
