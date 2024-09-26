/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.EntidadSistemaRegimen;

/**
 * @author joseviscaya
 *
 */
public class EntidadSistemaRegimenExt extends EntidadSistemaRegimen{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5536514580785702554L;

	private String nombreSistemaCarrera;
	
    private String nombreEscalaSalarial;
	
	private String nombreNaturalezaEmpleo;
	
	/**
	 * @return the nombreSistemaCarrera
	 */
	public String getNombreSistemaCarrera() {
		return nombreSistemaCarrera;
	}

	/**
	 * @param nombreSistemaCarrera the nombreSistemaCarrera to set
	 */
	public void setNombreSistemaCarrera(String nombreSistemaCarrera) {
		this.nombreSistemaCarrera = nombreSistemaCarrera;
	}

	/**
	 * @return the nombreEscalaSalarial
	 */
	public String getNombreEscalaSalarial() {
		return nombreEscalaSalarial;
	}

	/**
	 * @param nombreEscalaSalarial the nombreEscalaSalarial to set
	 */
	public void setNombreEscalaSalarial(String nombreEscalaSalarial) {
		this.nombreEscalaSalarial = nombreEscalaSalarial;
	}

	/**
	 * @return the nombreNaturalezaEmpleo
	 */
	public String getNombreNaturalezaEmpleo() {
		return nombreNaturalezaEmpleo;
	}

	/**
	 * @param nombreNaturalezaEmpleo the nombreNaturalezaEmpleo to set
	 */
	public void setNombreNaturalezaEmpleo(String nombreNaturalezaEmpleo) {
		this.nombreNaturalezaEmpleo = nombreNaturalezaEmpleo;
	}

	

}
