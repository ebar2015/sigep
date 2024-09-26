/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.util.Date;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga integrar los datos basicos de la esperiencia laboral del servidor publico
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: MArtes 26 de Junio de 2018
*/
public class ExperianciaLaboralPortal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6870879532679577128L;
	// Variable para almacenar el cargo que ocupo el funcionario
	private String cargo;
	// variable para lamacenar el nombre de la entdad donde laboro o labora el funcionario
	private String entidad;
	// variable para almacena la fecha en que ingreso a laborar
	private Date fechaInicio;
	// variable para almacenar la fecha en que se retiro de la entidad
	private Date fechaFin;
	// variable para almacenar el estado laboral en la entidad
	private String estado;
	
	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}
	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	/**
	 * @return the entidad
	 */
	public String getEntidad() {
		return entidad;
	}
	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	

}
