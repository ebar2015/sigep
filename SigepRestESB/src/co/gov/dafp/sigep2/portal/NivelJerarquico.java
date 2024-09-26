/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Maria Alejandra.
* @version 1.0
* @Class Clase que se encarga de presentar los registros por Nivel Jerarquico
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: 23 de Julio 2020
*/
public class NivelJerarquico implements Serializable{

	private static final long serialVersionUID = 4183728278389504242L;
	
	
	// Variable pra almacenar el id del nivel jerarquico
	private Integer codNivelJerarquico;
	// Variable pra almacenar la caracteristica del nivel jerarquico
	private String nombreNivelJerarquico;
	// variable para almacenar la denominacion del cargo
	private String nombreDenominacion;
	// variable para almacenar el codigo del cargo
	private String nombreCodigo;
	//variable para almacenar el grado del cargo
	private String nombreGrado;
	// variable para almacenar el valor debemado por ese cargo
	private BigDecimal asignacionSalarial;
	private Long 	codNomenclaturaAsociada;
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
	 * @return the nombreDenominacion
	 */
	public String getNombreDenominacion() {
		return nombreDenominacion;
	}
	/**
	 * @param nombreDenominacion the nombreDenominacion to set
	 */
	public void setNombreDenominacion(String nombreDenominacion) {
		this.nombreDenominacion = nombreDenominacion;
	}
	/**
	 * @return the nombreCodigo
	 */
	public String getNombreCodigo() {
		return nombreCodigo;
	}
	/**
	 * @param nombreCodigo the nombreCodigo to set
	 */
	public void setNombreCodigo(String nombreCodigo) {
		this.nombreCodigo = nombreCodigo;
	}
	/**
	 * @return the nombreGrado
	 */
	public String getNombreGrado() {
		return nombreGrado;
	}
	/**
	 * @param nombreGrado the nombreGrado to set
	 */
	public void setNombreGrado(String nombreGrado) {
		this.nombreGrado = nombreGrado;
	}
	/**
	 * @return the asignacionSalarial
	 */
	public BigDecimal getAsignacionSalarial() {
		return asignacionSalarial;
	}
	/**
	 * @param asignacionSalarial the asignacionSalarial to set
	 */
	public void setAsignacionSalarial(BigDecimal asignacionSalarial) {
		this.asignacionSalarial = asignacionSalarial;
	}
	/**
	 * @return the codNomenclaturaAsociada
	 */
	public Long getCodNomenclaturaAsociada() {
		return codNomenclaturaAsociada;
	}
	/**
	 * @param codNomenclaturaAsociada the codNomenclaturaAsociada to set
	 */
	public void setCodNomenclaturaAsociada(Long codNomenclaturaAsociada) {
		this.codNomenclaturaAsociada = codNomenclaturaAsociada;
	}
}
