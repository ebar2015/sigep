/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.ActividadPrivada;

/**
 * @author joseviscaya
 *
 */
public class ActividadPrivadaExt extends ActividadPrivada {

	private static final long serialVersionUID = 4983601402857953500L;
	
	private String nombreFormaParticipacion;
	private int total;
	private int limitIni;
	private int limitFin;

	/**
	 * @return the nombreFormaParticipacion
	 */
	public String getNombreFormaParticipacion() {
		return nombreFormaParticipacion;
	}

	/**
	 * @param nombreFormaParticipacion the nombreFormaParticipacion to set
	 */
	public void setNombreFormaParticipacion(String nombreFormaParticipacion) {
		this.nombreFormaParticipacion = nombreFormaParticipacion;
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
	 * @return the limitIni
	 */
	public int getLimitIni() {
		return limitIni;
	}

	/**
	 * @param limitIni the limitIni to set
	 */
	public void setLimitIni(int limitIni) {
		this.limitIni = limitIni;
	}

	/**
	 * @return the limitFin
	 */
	public int getLimitFin() {
		return limitFin;
	}

	/**
	 * @param limitFin the limitFin to set
	 */
	public void setLimitFin(int limitFin) {
		this.limitFin = limitFin;
	}
}
