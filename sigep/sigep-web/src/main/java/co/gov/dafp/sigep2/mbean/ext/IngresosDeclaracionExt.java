/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.IngresosDeclaracion;

/**
 * @author joseviscaya
 *
 */
public class IngresosDeclaracionExt extends IngresosDeclaracion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 482826846383417707L;
	
	private String nombreTipoIngreso;

	/**
	 * @return the nombreTipoIngreso
	 */
	public String getNombreTipoIngreso() {
		return nombreTipoIngreso;
	}

	/**
	 * @param nombreTipoIngreso the nombreTipoIngreso to set
	 */
	public void setNombreTipoIngreso(String nombreTipoIngreso) {
		this.nombreTipoIngreso = nombreTipoIngreso;
	}

}
