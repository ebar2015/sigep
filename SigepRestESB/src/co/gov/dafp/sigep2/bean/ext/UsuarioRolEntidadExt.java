/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.math.BigDecimal;

import co.gov.dafp.sigep2.bean.UsuarioRolEntidad;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase extendida  [pra manejo de valores aducionales
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Martes 24 de Julio de 2018
*/
public class UsuarioRolEntidadExt extends UsuarioRolEntidad {

	private static final long serialVersionUID = 1290071002529130126L;
	
	private BigDecimal codUsuario;
	private BigDecimal codEntidad;
	private BigDecimal codPersona;
	private Long resultProcedure;

	/**
	 * @return the codUsuario
	 */
	public BigDecimal getCodUsuario() {
		return codUsuario;
	}

	/**
	 * @param codUsuario the codUsuario to set
	 */
	public void setCodUsuario(BigDecimal codUsuario) {
		this.codUsuario = codUsuario;
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
		this.codEntidad = codEntidad;
	}

	/**
	 * @return the codPersona
	 */
	public BigDecimal getCodPersona() {
		return codPersona;
	}

	/**
	 * @param codPersona the codPersona to set
	 */
	public void setCodPersona(BigDecimal codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * @return the resultProcedure
	 */
	public Long getResultProcedure() {
		return resultProcedure;
	}

	/**
	 * @param resultProcedure the resultProcedure to set
	 */
	public void setResultProcedure(Long resultProcedure) {
		this.resultProcedure = resultProcedure;
	}
}
