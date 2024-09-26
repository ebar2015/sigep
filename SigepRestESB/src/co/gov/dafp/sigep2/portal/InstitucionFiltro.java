/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de represntar los datos disponibles por Entidad
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: MArtes 26 de Junio de 2018
*/
public class InstitucionFiltro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 940863959195386275L;
	
	// Variable pra almacenar el id de la entidad
	private BigDecimal codEntidad;
	// Variable pra almacenar el nombre de la entidad
	private String nombre;
	// Variable pra almacenar el totoal de registros encontrado en la consulta
	private Integer totalRegistros;

	private BigDecimal cod;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	/**
	 * @return the codEntidad
	 */
	public BigDecimal getCodEntidad() {
		return codEntidad;
	}

	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(BigDecimal codEntidad) {
		this.cod = codEntidad;
		this.codEntidad = codEntidad;
	}

	/**
	 * @return the cod
	 */
	public BigDecimal getCod() {
		return cod;
	}

	/**
	 * @param cod the cod to set
	 */
	public void setCod(BigDecimal cod) {
		this.cod = cod;
	}

}
