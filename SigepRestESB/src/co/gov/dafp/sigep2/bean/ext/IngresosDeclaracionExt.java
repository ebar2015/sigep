/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.IngresosDeclaracion;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  IngresosDeclaracionExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class IngresosDeclaracionExt extends IngresosDeclaracion implements Serializable {

	private static final long serialVersionUID = 482826846383417707L;
	private String nombreTipoIngreso;
	private String tipoIngreso;

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

	/**
	 * @return the tipoIngreso
	 */
	public String getTipoIngreso() {
		return tipoIngreso;
	}

	/**
	 * @param tipoIngreso the tipoIngreso to set
	 */
	public void setTipoIngreso(String tipoIngreso) {
		this.tipoIngreso = tipoIngreso;
	}

}
