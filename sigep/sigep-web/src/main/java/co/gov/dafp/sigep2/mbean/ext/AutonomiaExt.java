/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.Autonomia;

/**
 * @author Jose Viscaya
 * 4 ene. 2019
 */
public class AutonomiaExt extends Autonomia {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1930266545061740764L;
	private String nombreAutonomia;
	/**
	 * @return the nombreAutonomia
	 */
	public String getNombreAutonomia() {
		return nombreAutonomia;
	}
	/**
	 * @param nombreAutonomia the nombreAutonomia to set
	 */
	public void setNombreAutonomia(String nombreAutonomia) {
		this.nombreAutonomia = nombreAutonomia;
	}

}
