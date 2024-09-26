/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.AsignacionSalarial;

/**
* @author Maria Alejandra Colorado Ríos
* @version 1.0
* @Class Clase  AsignacionSalarialExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class AsignacionSalarialExt extends AsignacionSalarial {

	private static final long serialVersionUID = -2518151078148929724L;

	private Integer total;
	
	private Integer limitIni;
	
	private Integer limitFin;
	
	private String nombreNivelJerarquico;
	
	private String nombreGrado;
	

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
	 * @return the nombreNivelJerarquico
	 */
	public String getNombreNivelJerarquico() {
		return nombreNivelJerarquico;
	}

	/**
	 * @param nombreNivelJerarquico the nombreNivelJerarquico to set
	 */
	public void setNombreNivelJerarquico(String nombreNivelJerarquico) {
		this.nombreNivelJerarquico = nombreNivelJerarquico;
	}

	/**
	 * @return the nombreGrado
	 */
	public String getNombreGrado() {
		return nombreGrado;
	}

	/**
	 * @param nombreGrado the nombreGrado to set
	 */
	public void setNombreGrado(String nombreGrado) {
		this.nombreGrado = nombreGrado;
	}
}
