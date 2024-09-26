/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;

/**
* @author Maria Alejandra.
* @version 1.0
* @Class Clase que se encarga de presentar los registros por Cargo
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: 29 de Julio 2020
*/
public class CargoReferenciaFiltro implements Serializable{

	private static final long serialVersionUID = 4183728278389504242L;
	
	
	// Variable pra almacenar el id del cargo
	private Integer codCargoReferencia;
	// Variable pra almacenar el nombre del cargo
	private String nombreCargoReferencia;
	// Variable pra almacenar el total de registros encontrados en la consulta
	private Integer totalRegistros;
	/**
	 * @return the codCargoReferencia
	 */
	public Integer getCodCargoReferencia() {
		return codCargoReferencia;
	}
	/**
	 * @param codCargoReferencia the codCargoReferencia to set
	 */
	public void setCodCargoReferencia(Integer codCargoReferencia) {
		this.codCargoReferencia = codCargoReferencia;
	}
	/**
	 * @return the nombreCargoReferencia
	 */
	public String getNombreCargoReferencia() {
		return nombreCargoReferencia;
	}
	/**
	 * @param nombreCargoReferencia the nombreCargoReferencia to set
	 */
	public void setNombreCargoReferencia(String nombreCargoReferencia) {
		this.nombreCargoReferencia = nombreCargoReferencia;
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
