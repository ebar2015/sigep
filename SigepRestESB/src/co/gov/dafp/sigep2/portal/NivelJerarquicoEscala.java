/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.util.List;

/**
* @author Maria Alejandra.
* @version 1.0
* @Class Clase que se encarga de presentar los registros por Nivel Jerarquico
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: 23 de Julio 2020
*/
public class NivelJerarquicoEscala implements Serializable{

	private static final long serialVersionUID = 4183728278389504242L;
	
	
	// Variable pra almacenar el id del nivel jerarquico
	private Integer codNivelJerarquico;
	// Variable pra almacenar la caracteristica del nivel jerarquico
	private String nombreNivelJerarquico;
	// Variable que contiene el detalle de los niveles jerarquicos.
	private List<NivelJerarquico> nivelJerarquico;
	
	/**
	 * @return the codNivelJerarquico
	 */
	public Integer getCodNivelJerarquico() {
		return codNivelJerarquico;
	}
	/**
	 * @param codNivelJerarquico the codNivelJerarquico to set
	 */
	public void setCodNivelJerarquico(Integer codNivelJerarquico) {
		this.codNivelJerarquico = codNivelJerarquico;
	}
	/**
	 * @return the nombreNivelJerarquico
	 */
	public String getNombreNivelJerarquico() {
		return nombreNivelJerarquico;
	}
	/**
	 * @param nombreNivelJerarquico the nombreNivelJerarquico to set
	 */
	public void setNombreNivelJerarquico(String nombreNivelJerarquico) {
		this.nombreNivelJerarquico = nombreNivelJerarquico;
	}
	/**
	 * @return the nivelJerarquico
	 */
	public List<NivelJerarquico> getNivelJerarquico() {
		return nivelJerarquico;
	}
	/**
	 * @param nivelJerarquico the nivelJerarquico to set
	 */
	public void setNivelJerarquico(List<NivelJerarquico> nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}
}
