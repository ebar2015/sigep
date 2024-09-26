/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Maria Alejandra
* @version 1.0
* @Class Clase que se encarga de almacenar los valores de sistema de carrera de una entidad.
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: 31/07/2020
*/
public class SistemaCarrera implements Serializable {

	private static final long serialVersionUID = 758236527260320516L;

	private String sistemaCarrera;
	private BigDecimal codSistemaCarrera;
	/**
	 * @return the sistemaCarrera
	 */
	public String getSistemaCarrera() {
		return sistemaCarrera;
	}
	/**
	 * @param sistemaCarrera the sistemaCarrera to set
	 */
	public void setSistemaCarrera(String sistemaCarrera) {
		this.sistemaCarrera = sistemaCarrera;
	}
	/**
	 * @return the codSistemaCarrera
	 */
	public BigDecimal getCodSistemaCarrera() {
		return codSistemaCarrera;
	}
	/**
	 * @param codSistemaCarrera the codSistemaCarrera to set
	 */
	public void setCodSistemaCarrera(BigDecimal codSistemaCarrera) {
		this.codSistemaCarrera = codSistemaCarrera;
	}
}
