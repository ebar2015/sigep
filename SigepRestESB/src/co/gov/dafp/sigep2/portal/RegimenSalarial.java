/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de almacenar los valores de Regimen Salarial de una entidad
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 28 de Junio de 2018
*/
public class RegimenSalarial implements Serializable {

	private static final long serialVersionUID = 1967093301428121360L;

	private String escalaSalarial;
	private BigDecimal codEscalaSalarial;
	/**
	 * @return the escalaSalarial
	 */
	public String getEscalaSalarial() {
		return escalaSalarial;
	}
	/**
	 * @param escalaSalarial the escalaSalarial to set
	 */
	public void setEscalaSalarial(String escalaSalarial) {
		this.escalaSalarial = escalaSalarial;
	}
	/**
	 * @return the codEscalaSalarial
	 */
	public BigDecimal getCodEscalaSalarial() {
		return codEscalaSalarial;
	}
	/**
	 * @param codEscalaSalarial the codEscalaSalarial to set
	 */
	public void setCodEscalaSalarial(BigDecimal codEscalaSalarial) {
		this.codEscalaSalarial = codEscalaSalarial;
	}
}
