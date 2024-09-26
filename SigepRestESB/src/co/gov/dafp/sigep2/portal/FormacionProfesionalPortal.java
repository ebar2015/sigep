/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de almacenar la informacion de formacion profesiona de la paersona
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: Miercoles 27 de Junio de 2018
*/
public class FormacionProfesionalPortal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5595051721404451051L;
	// variable para almacenar el nivel de foracion del estudio realizado
	private String nivelDeFormacion;
	// variable para almacenar em area de conocimiento del estudio realizado
	private String areaConocimiento;
	// variable para almacenar el estado del estudio realizado
	private String estado;
	//variable para almacenar el nombre del programa academico
	private String programaAcademico;
	//Variable para almacenar el nombre del nivel educativo
	private String nivelEducativo;
	/**
	 * @return the nivelDeFormacion
	 */
	public String getNivelDeFormacion() {
		return nivelDeFormacion;
	}
	/**
	 * @param nivelDeFormacion the nivelDeFormacion to set
	 */
	public void setNivelDeFormacion(String nivelDeFormacion) {
		this.nivelDeFormacion = nivelDeFormacion;
	}
	/**
	 * @return the areaConocimiento
	 */
	public String getAreaConocimiento() {
		return areaConocimiento;
	}
	/**
	 * @param areaConocimiento the areaConocimiento to set
	 */
	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
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
	/**
	 * @return the programaAcademico
	 */
	public String getProgramaAcademico() {
		return programaAcademico;
	}
	/**
	 * @param programaAcademico the programaAcademico to set
	 */
	public void setProgramaAcademico(String programaAcademico) {
		this.programaAcademico = programaAcademico;
	}
	/**
	 * @return the nivelEducativo
	 */
	public String getNivelEducativo() {
		return nivelEducativo;
	}
	/**
	 * @param nivelEducativo the nivelEducativo to set
	 */
	public void setNivelEducativo(String nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}
}