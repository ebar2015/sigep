package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.UsuarioRolEntidad;


/**
* @author Jose Viscaya
* @version 1.0
* @Class Clase extendida  [pra manejo de valores aducionales
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Martes 24 de Julio de 2018
*/
public class UsuarioRolEntidadExt extends UsuarioRolEntidad {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1290071002529130126L;
	
	private Integer codUsuario;
	private Integer codEntidad;
	private Integer codPersona;

	/**
	 * @return the codUsuario
	 */
	public Integer getCodUsuario() {
		return codUsuario;
	}

	/**
	 * @param codUsuario the codUsuario to set
	 */
	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}

	/**
	 * @return the codEntidad
	 */
	public Integer getCodEntidad() {
		return codEntidad;
	}

	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(Integer codEntidad) {
		this.codEntidad = codEntidad;
	}

	public Integer getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Integer codPersona) {
		this.codPersona = codPersona;
	}
}