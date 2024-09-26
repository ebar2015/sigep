/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.GrupoDependencia;

/**
* @author Jose Viscaya
* @version 1.0
* @Class Que se encarga de extender de la clase GrupoDependencia
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 12/09/2018 2:26:26 p.m.
*/
public class GrupoDependenciaExt extends GrupoDependencia {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3793039027183459977L;
	
	
	private Long codEntidad;


	/**
	 * @return the codEntidad
	 */
	public Long getCodEntidad() {
		return codEntidad;
	}


	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

}
