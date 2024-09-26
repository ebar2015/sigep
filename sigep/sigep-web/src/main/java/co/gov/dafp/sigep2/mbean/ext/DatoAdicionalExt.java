/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.DatoAdicional;

/**
 * @author joseviscaya
 *
 */
public class DatoAdicionalExt extends DatoAdicional implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -638861106243344336L;
	
	private String nombreDiscapacidad;
	private String nombreOrientacionSexual;
	private String nombreCabezaHogar;
	private String nombreVictimaDesplazamiento;
	
	/**
	 * @return the nombreDiscapacidad
	 */
	public String getNombreDiscapacidad() {
		return nombreDiscapacidad;
	}
	/**
	 * @param nombreDiscapacidad the nombreDiscapacidad to set
	 */
	public void setNombreDiscapacidad(String nombreDiscapacidad) {
		this.nombreDiscapacidad = nombreDiscapacidad;
	}
	/**
	 * @return the nombreOrientacionSexual
	 */
	public String getNombreOrientacionSexual() {
		return nombreOrientacionSexual;
	}
	/**
	 * @param nombreOrientacionSexual the nombreOrientacionSexual to set
	 */
	public void setNombreOrientacionSexual(String nombreOrientacionSexual) {
		this.nombreOrientacionSexual = nombreOrientacionSexual;
	}
	/**
	 * @return the nombreCabezaHogar
	 */
	public String getNombreCabezaHogar() {
		return nombreCabezaHogar;
	}
	/**
	 * @param nombreCabezaHogar the nombreCabezaHogar to set
	 */
	public void setNombreCabezaHogar(String nombreCabezaHogar) {
		this.nombreCabezaHogar = nombreCabezaHogar;
	}
	/**
	 * @return the nombreVictimaDesplazamiento
	 */
	public String getNombreVictimaDesplazamiento() {
		return nombreVictimaDesplazamiento;
	}
	/**
	 * @param nombreVictimaDesplazamiento the nombreVictimaDesplazamiento to set
	 */
	public void setNombreVictimaDesplazamiento(String nombreVictimaDesplazamiento) {
		this.nombreVictimaDesplazamiento = nombreVictimaDesplazamiento;
	}

}
