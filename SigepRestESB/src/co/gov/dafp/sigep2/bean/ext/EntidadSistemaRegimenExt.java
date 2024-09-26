/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.EntidadSistemaRegimen;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Que se encarga de recibir las peticiones de los usuarios de movil
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 14/08/2018 3:09:10 p.m.
*/
public class EntidadSistemaRegimenExt extends EntidadSistemaRegimen {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2649184422603035004L;
	
	
	
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
