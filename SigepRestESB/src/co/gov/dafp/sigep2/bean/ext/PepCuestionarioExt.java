/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.PepCuestionario;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Que se encarga de recibir las peticiones de los usuarios de movil
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 14/08/2018 10:57:06 a.m.
*/
public class PepCuestionarioExt extends PepCuestionario {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2182126951103052349L;
	
	private String nombreCuestionario;

	/**
	 * @return the nombreCuestionario
	 */
	public String getNombreCuestionario() {
		return nombreCuestionario;
	}

	/**
	 * @param nombreCuestionario the nombreCuestionario to set
	 */
	public void setNombreCuestionario(String nombreCuestionario) {
		this.nombreCuestionario = nombreCuestionario;
	}

}
