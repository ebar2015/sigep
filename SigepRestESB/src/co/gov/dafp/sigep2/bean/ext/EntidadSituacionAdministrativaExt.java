/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.EntidadSituacionAdministrativa;

/**
 * @author joseviscaya
 *
 */
public class EntidadSituacionAdministrativaExt extends EntidadSituacionAdministrativa {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4640808369360169493L;
	private String nombreSituacion;
	
	private Integer numAsignadas;

	/**
	 * @return the nombreSituacion
	 */
	public String getNombreSituacion() {
		return nombreSituacion;
	}

	/**
	 * @param nombreSituacion the nombreSituacion to set
	 */
	public void setNombreSituacion(String nombreSituacion) {
		this.nombreSituacion = nombreSituacion;
	}

	/**
	 * @return the numAsignadas
	 */
	public Integer getNumAsignadas() {
		return numAsignadas;
	}

	/**
	 * @param numAsignadas the numAsignadas to set
	 */
	public void setNumAsignadas(Integer numAsignadas) {
		this.numAsignadas = numAsignadas;
	}

}
