/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Maria Alejandra
* @version 1.0
* @Class Clase que se encarga de almacenar los valores de naturaleza del empleo de una entidad.
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: 31/07/2020
*/
public class NaturalezaEmpleo implements Serializable {

	private static final long serialVersionUID = 758236527260320516L;

	private String naturalezaEmpleo;
	private BigDecimal codNaturalezaEmpleo;
	/**
	 * @return the naturalezaEmpleo
	 */
	public String getNaturalezaEmpleo() {
		return naturalezaEmpleo;
	}
	/**
	 * @param naturalezaEmpleo the naturalezaEmpleo to set
	 */
	public void setNaturalezaEmpleo(String naturalezaEmpleo) {
		this.naturalezaEmpleo = naturalezaEmpleo;
	}
	/**
	 * @return the codNaturalezaEmpleo
	 */
	public BigDecimal getCodNaturalezaEmpleo() {
		return codNaturalezaEmpleo;
	}
	/**
	 * @param codNaturalezaEmpleo the codNaturalezaEmpleo to set
	 */
	public void setCodNaturalezaEmpleo(BigDecimal codNaturalezaEmpleo) {
		this.codNaturalezaEmpleo = codNaturalezaEmpleo;
	}

}
