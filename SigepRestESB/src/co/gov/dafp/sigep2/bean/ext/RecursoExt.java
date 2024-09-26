/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.lang.reflect.Array;
import java.util.List;

import co.gov.dafp.sigep2.bean.Recurso;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Que se encarga de recibir las peticiones de los usuarios de movil
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 17/08/2018 11:11:52 a.m.
*/
public class RecursoExt extends Recurso {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4842172006150473338L;
	
	
	private Integer[] codRolList;
	
	private Integer codRol;

	

	/**
	 * @return the codRol
	 */
	public Integer getCodRol() {
		return codRol;
	}

	/**
	 * @param codRol the codRol to set
	 */
	public void setCodRol(Integer codRol) {
		this.codRol = codRol;
	}

	/**
	 * @return the codRolList
	 */
	public Integer[] getCodRolList() {
		return codRolList;
	}

	/**
	 * @param codRolList the codRolList to set
	 */
	public void setCodRolList(Integer[] codRolList) {
		this.codRolList = codRolList;
	}

}
