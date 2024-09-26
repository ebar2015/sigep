/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.DatoAdicional;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  DatoAdicionalExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class DatoAdicionalExt extends DatoAdicional implements Serializable {

	private static final long serialVersionUID = -638861106243344336L;
	
	private String nombreDiscapacidad;
	private String nombreOrientacionSexual;
	private String nombreCabezaHogar;
	
	private String nombreVictimaDesplazamiento;
	private String cabezaHogar;
	private String tipoDiscapacidad;
	private String orientacionSexual;
	private String victimaDesplazamiento;
	
	private String codIsoPais;
	
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
	/**
	 * @return the cabezaHogar
	 */
	public String getCabezaHogar() {
		return cabezaHogar;
	}
	/**
	 * @param cabezaHogar the cabezaHogar to set
	 */
	public void setCabezaHogar(String cabezaHogar) {
		this.cabezaHogar = cabezaHogar;
	}
	/**
	 * @return the tipoDiscapacidad
	 */
	public String getTipoDiscapacidad() {
		return tipoDiscapacidad;
	}
	/**
	 * @param tipoDiscapacidad the tipoDiscapacidad to set
	 */
	public void setTipoDiscapacidad(String tipoDiscapacidad) {
		this.tipoDiscapacidad = tipoDiscapacidad;
	}
	/**
	 * @return the orientacionSexual
	 */
	public String getOrientacionSexual() {
		return orientacionSexual;
	}
	/**
	 * @param orientacionSexual the orientacionSexual to set
	 */
	public void setOrientacionSexual(String orientacionSexual) {
		this.orientacionSexual = orientacionSexual;
	}
	/**
	 * @return the victimaDesplazamiento
	 */
	public String getVictimaDesplazamiento() {
		return victimaDesplazamiento;
	}
	/**
	 * @param victimaDesplazamiento the victimaDesplazamiento to set
	 */
	public void setVictimaDesplazamiento(String victimaDesplazamiento) {
		this.victimaDesplazamiento = victimaDesplazamiento;
	}
	/**
	 * @return the codIsoPais
	 */
	public String getCodIsoPais() {
		return codIsoPais;
	}
	/**
	 * @param codIsoPais the codIsoPais to set
	 */
	public void setCodIsoPais(String codIsoPais) {
		this.codIsoPais = codIsoPais;
	}

}
