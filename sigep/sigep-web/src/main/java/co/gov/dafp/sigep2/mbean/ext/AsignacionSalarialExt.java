/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;


import co.gov.dafp.sigep2.entities.AsignacionSalarial;

/**
 * @author Maria Alejandra Colorado
 *
 */
public class AsignacionSalarialExt extends AsignacionSalarial {

	private static final long serialVersionUID = 6581931843216521902L;

	private int total;
	private int limitIni;
	private int limitFin;
	private String nombreNivelJerarquico;
	private String nombreGrado;

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
