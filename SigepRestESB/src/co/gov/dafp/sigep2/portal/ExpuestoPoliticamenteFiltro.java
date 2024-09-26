/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;

/**
* @author Maria Alejandra.
* @version 1.0
* @Class Clase que se encarga de presentar los registros por flg_expuesto_politicamente
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: 27 Mayo 2020
*/
public class ExpuestoPoliticamenteFiltro implements Serializable{

	private static final long serialVersionUID = -561302833144573805L;
	
	// Variable pra almacenar el id del departamento
	private Integer expuestoPoliticamente;
	// Variable pra almacenar la caracteristica de expuesto
	private String descripcionExpuesto;
	// Variable pra almacenar el total de registros encontrados en la consulta
	private Integer totalRegistros;

	
	/**
	 * @return the expuestoPoliticamente
	 */
	public Integer getExpuestoPoliticamente() {
		return expuestoPoliticamente;
	}
	/**
	 * @param expuestoPoliticamente the expuestoPoliticamente to set
	 */
	public void setExpuestoPoliticamente(Integer expuestoPoliticamente) {
		this.expuestoPoliticamente = expuestoPoliticamente;
	}
	/**
	 * @return the descripcionExpuesto
	 */
	public String getDescripcionExpuesto() {
		return descripcionExpuesto;
	}
	/**
	 * @param descripcionExpuesto the descripcionExpuesto to set
	 */
	public void setDescripcionExpuesto(String descripcionExpuesto) {
		this.descripcionExpuesto = descripcionExpuesto;
	}
	/**
	 * @return the totalRegistros
	 */
	public Integer getTotalRegistros() {
		return totalRegistros;
	}
	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
}
