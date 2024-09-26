/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de almacenar los valores de Tipos de servidor que contiene una entidad
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 28 de Junio de 2018
*/
public class TipoServidorEntidad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 934341856113660208L;
	// variable para indicar que esta seleccinado el tipo empleado publico
	private boolean empleadoPublico;
	// variable para indicar que esta seleccinado el tipo trbajador oficial
	private boolean trabajadorOficial;
	// variable para indicar que esta seleccinado el tipo rama judicial
	private boolean ramaJudicial;
	// variable para indicar que esta seleccinado el tipo contratista
	private boolean contratista;
	// variable para indicar que esta seleccinado el tipo docente
	private boolean docente;
	// variable para indicar que esta seleccinado el tipo instructor
	private boolean instructor;
	// variable para indicar que esta seleccinado el tipo utl
	private boolean utl;
	// variable para indicar que esta seleccinado el tipo uan
	private boolean uan;
	// variable para indicar que esta seleccinado el tipo super numerario
	private boolean supernumerario;
	// variable para indicar que esta seleccinado el tipo regimen privado
	private boolean regimenPrivado;
	/**
	 * @return the empleadoPublico
	 */
	public boolean isEmpleadoPublico() {
		return empleadoPublico;
	}
	/**
	 * @param empleadoPublico the empleadoPublico to set
	 */
	public void setEmpleadoPublico(boolean empleadoPublico) {
		this.empleadoPublico = empleadoPublico;
	}
	/**
	 * @return the trabajadorOficial
	 */
	public boolean isTrabajadorOficial() {
		return trabajadorOficial;
	}
	/**
	 * @param trabajadorOficial the trabajadorOficial to set
	 */
	public void setTrabajadorOficial(boolean trabajadorOficial) {
		this.trabajadorOficial = trabajadorOficial;
	}
	/**
	 * @return the ramaJudicial
	 */
	public boolean isRamaJudicial() {
		return ramaJudicial;
	}
	/**
	 * @param ramaJudicial the ramaJudicial to set
	 */
	public void setRamaJudicial(boolean ramaJudicial) {
		this.ramaJudicial = ramaJudicial;
	}
	/**
	 * @return the contratista
	 */
	public boolean isContratista() {
		return contratista;
	}
	/**
	 * @param contratista the contratista to set
	 */
	public void setContratista(boolean contratista) {
		this.contratista = contratista;
	}
	/**
	 * @return the docente
	 */
	public boolean isDocente() {
		return docente;
	}
	/**
	 * @param docente the docente to set
	 */
	public void setDocente(boolean docente) {
		this.docente = docente;
	}
	/**
	 * @return the instructor
	 */
	public boolean isInstructor() {
		return instructor;
	}
	/**
	 * @param instructor the instructor to set
	 */
	public void setInstructor(boolean instructor) {
		this.instructor = instructor;
	}
	/**
	 * @return the utl
	 */
	public boolean isUtl() {
		return utl;
	}
	/**
	 * @param utl the utl to set
	 */
	public void setUtl(boolean utl) {
		this.utl = utl;
	}
	/**
	 * @return the uan
	 */
	public boolean isUan() {
		return uan;
	}
	/**
	 * @param uan the uan to set
	 */
	public void setUan(boolean uan) {
		this.uan = uan;
	}
	/**
	 * @return the supernumerario
	 */
	public boolean isSupernumerario() {
		return supernumerario;
	}
	/**
	 * @param supernumerario the supernumerario to set
	 */
	public void setSupernumerario(boolean supernumerario) {
		this.supernumerario = supernumerario;
	}
	/**
	 * @return the regimenPrivado
	 */
	public boolean isRegimenPrivado() {
		return regimenPrivado;
	}
	/**
	 * @param regimenPrivado the regimenPrivado to set
	 */
	public void setRegimenPrivado(boolean regimenPrivado) {
		this.regimenPrivado = regimenPrivado;
	}

}
