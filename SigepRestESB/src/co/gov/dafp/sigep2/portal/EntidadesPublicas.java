/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.util.List;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de responder las peticiones de busquedas de Entidades Publicas desde el Portal
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 28 de Junio de 2018
*/
public class EntidadesPublicas extends ErrorMensajes {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8201211635254104616L;
	
	// variable para almacenar el total de resultados de la busqueda
	private Integer totalRegistros;
	// variable para almacenar el lsutado de entidades resultado de la busqueda
	private List<EntidadPortal> entidadesPortal;
	// Variable  que hace eco del numero de datos solicitados
	private Integer limiteFinal;
	/**
	 * @return the entidadesPortal
	 */
	public List<EntidadPortal> getEntidadesPortal() {
		return entidadesPortal;
	}
	/**
	 * @param entidadesPortal the entidadesPortal to set
	 */
	public void setEntidadesPortal(List<EntidadPortal> entidadesPortal) {
		this.entidadesPortal = entidadesPortal;
	}
	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}
	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	/**
	 * @return the limiteFinal
	 */
	public Integer getLimiteFinal() {
		return limiteFinal;
	}
	/**
	 * @param limiteFinal the limiteFinal to set
	 */
	public void setLimiteFinal(Integer limiteFinal) {
		this.limiteFinal = limiteFinal;
	}
	
	

}
