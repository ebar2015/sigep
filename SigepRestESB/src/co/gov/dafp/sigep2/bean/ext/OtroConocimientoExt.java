/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.OtroConocimiento;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  OtroConocimientoExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class OtroConocimientoExt extends OtroConocimiento implements Serializable {

	private static final long serialVersionUID = -6775898920525434399L;
	private String paisNombre;
	private String modalidadNombre;
	private String medioCapacitacionNombre;

	/**
	 * @return the paisNombre
	 */
	public String getPaisNombre() {
		return paisNombre;
	}
	/**
	 * @param paisNombre the paisNombre to set
	 */
	public void setPaisNombre(String paisNombre) {
		this.paisNombre = paisNombre;
	}
	/**
	 * @return the modalidadNombre
	 */
	public String getModalidadNombre() {
		return modalidadNombre;
	}
	/**
	 * @param modalidadNombre the modalidadNombre to set
	 */
	public void setModalidadNombre(String modalidadNombre) {
		this.modalidadNombre = modalidadNombre;
	}
	/**
	 * @return the medioCapacitacionNombre
	 */
	public String getMedioCapacitacionNombre() {
		return medioCapacitacionNombre;
	}
	/**
	 * @param medioCapacitacionNombre the medioCapacitacionNombre to set
	 */
	public void setMedioCapacitacionNombre(String medioCapacitacionNombre) {
		this.medioCapacitacionNombre = medioCapacitacionNombre;
	}
	
}
