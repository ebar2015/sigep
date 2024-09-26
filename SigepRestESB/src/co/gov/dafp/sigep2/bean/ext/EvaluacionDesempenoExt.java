/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.EvaluacionDesempeno;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  EvaluacionDesempenoExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class EvaluacionDesempenoExt extends EvaluacionDesempeno implements Serializable{

	private static final long serialVersionUID = 5311044373786795548L;
	private String nombreEntidad;
	private String nombreTipEevaluacion;
	private String nombreNivelCumplimiento;
	private String nombreCargo;
	private String flgEntidadPublica;
	
	/**
	 * @return the nombreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	/**
	 * @param nombreEntidad the nombreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	/**
	 * @return the nombreTipEevaluacion
	 */
	public String getNombreTipEevaluacion() {
		return nombreTipEevaluacion;
	}
	/**
	 * @param nombreTipEevaluacion the nombreTipEevaluacion to set
	 */
	public void setNombreTipEevaluacion(String nombreTipEevaluacion) {
		this.nombreTipEevaluacion = nombreTipEevaluacion;
	}
	/**
	 * @return the nombreNivelCumplimiento
	 */
	public String getNombreNivelCumplimiento() {
		return nombreNivelCumplimiento;
	}
	/**
	 * @param nombreNivelCumplimiento the nombreNivelCumplimiento to set
	 */
	public void setNombreNivelCumplimiento(String nombreNivelCumplimiento) {
		this.nombreNivelCumplimiento = nombreNivelCumplimiento;
	}
	/**
	 * @return the nombreCargo
	 */
	public String getNombreCargo() {
		return nombreCargo;
	}
	/**
	 * @param nombreCargo the nombreCargo to set
	 */
	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}
	/**
	 * @return the flgEntidadPublica
	 */
	public String getFlgEntidadPublica() {
		return flgEntidadPublica;
	}
	/**
	 * @param flgEntidadPublica the flgEntidadPublica to set
	 */
	public void setFlgEntidadPublica(String flgEntidadPublica) {
		this.flgEntidadPublica = flgEntidadPublica;
	}
	

}
