/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.ActividadPrivada;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ActividadPrivadaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ActividadPrivadaExt extends ActividadPrivada {

	private static final long serialVersionUID = 4983601402857953500L;
	
	private String nombreFormaParticipacion;
	private String formaParticipacionDes;
	
	private Integer total;
	
	private Integer limitIni;
	
	private Integer limitFin;

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
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the limitIni
	 */
	public Integer getLimitIni() {
		return limitIni;
	}

	/**
	 * @param limitIni the limitIni to set
	 */
	public void setLimitIni(Integer limitIni) {
		this.limitIni = limitIni;
	}

	/**
	 * @return the limitFin
	 */
	public Integer getLimitFin() {
		return limitFin;
	}

	/**
	 * @param limitFin the limitFin to set
	 */
	public void setLimitFin(Integer limitFin) {
		this.limitFin = limitFin;
	}

	/**
	 * @return the formaParticipacionDes
	 */
	public String getFormaParticipacionDes() {
		return formaParticipacionDes;
	}

	/**
	 * @param formaParticipacionDes the formaParticipacionDes to set
	 */
	public void setFormaParticipacionDes(String formaParticipacionDes) {
		this.formaParticipacionDes = formaParticipacionDes;
	}

	

}
