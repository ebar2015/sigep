/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de represntar los datos disponibles por departamento
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: MArtes 26 de Junio de 2018
*/
public class DepartamentoFiltro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 671089043571530168L;
	
	// Variable pra almacenar el id del departamento
	private BigDecimal codDepartamento;
	// Variable pra almacenar el nombre del departamento
	private String nombre;
	// Variable pra almacenar el total de registros encontrados en la consulta
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
	 * @return the codDepartamento
	 */
	public BigDecimal getCodDepartamento() {
		return codDepartamento;
	}

	/**
	 * @param codDepartamento the codDepartamento to set
	 */
	public void setCodDepartamento(BigDecimal codDepartamento) {
		this.cod = codDepartamento;
		this.codDepartamento = codDepartamento;
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
