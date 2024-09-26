/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.OtrosIngresosDeclaracion;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  OtrosIngresosDeclaracionExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class OtrosIngresosDeclaracionExt extends OtrosIngresosDeclaracion implements Serializable {

	private static final long serialVersionUID = -5163025912890852644L;
	private String nombreActividad;
	private String nombreBeneficio;
	
	private String descripcionActividad;

	private String formaBeneficio;
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
	/**
	 * @return the descripcionActividad
	 */
	public String getDescripcionActividad() {
		return descripcionActividad;
	}
	/**
	 * @param descripcionActividad the descripcionActividad to set
	 */
	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}
	/**
	 * @return the formaBeneficio
	 */
	public String getFormaBeneficio() {
		return formaBeneficio;
	}
	/**
	 * @param formaBeneficio the formaBeneficio to set
	 */
	public void setFormaBeneficio(String formaBeneficio) {
		this.formaBeneficio = formaBeneficio;
	}
	

}
