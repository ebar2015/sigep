/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de represntar los datos disponibles por Tipo de contratacion
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: MArtes 26 de Junio de 2018
*/
public class TipoContratoFiltro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2490420068008906187L;
	
	// Variable pra almacenar el id del tipo de contrato
	private Long codTipoContrato;
	// Variable pra almacenar el nombre del tipo de contrato
	private String nombre;
	// Variable pra almacenar el total de registros encontrados en la consulta
	private Integer totalRegistros;
	
	private Long cod;



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
	 * @return the codTipoContrato
	 */
	public Long getCodTipoContrato() {
		return codTipoContrato;
	}

	/**
	 * @param codTipoContrato the codTipoContrato to set
	 */
	public void setCodTipoContrato(Long codTipoContrato) {
		this.cod = codTipoContrato;
		this.codTipoContrato = codTipoContrato;
	}

	/**
	 * @return the cod
	 */
	public Long getCod() {
		return cod;
	}

	/**
	 * @param cod the cod to set
	 */
	public void setCod(Long cod) {
		this.cod = cod;
	}

}
