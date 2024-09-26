/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.SituacionAdministrativa;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  SituacionAdministrativaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class SituacionAdministrativaExt extends SituacionAdministrativa{

	private static final long serialVersionUID = 8136739087765372324L;

    private String nombrePadre;
    private Integer codTipoIdentificacion;
    private String numeroIdentificacion;
    private Integer codEntidad;
    private Integer codRolVinculado;
	private String nombrePersona;
	private Integer codEntidadSituacionAdmin;

	/**
	 * @return the nombrePadre
	 */
	public String getNombrePadre() {
		return nombrePadre;
	}

	/**
	 * @param nombrePadre the nombrePadre to set
	 */
	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	/**
	 * @return the codTipoIdentificacion
	 */
	public Integer getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	/**
	 * @param codTipoIdentificacion the codTipoIdentificacion to set
	 */
	public void setCodTipoIdentificacion(Integer codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
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

	public Integer getCodRolVinculado() {
		return codRolVinculado;
	}

	public void setCodRolVinculado(Integer codRolVinculado) {
		this.codRolVinculado = codRolVinculado;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public Integer getCodEntidadSituacionAdmin() {
		return codEntidadSituacionAdmin;
	}

	public void setCodEntidadSituacionAdmin(Integer codEntidadSituacionAdmin) {
		this.codEntidadSituacionAdmin = codEntidadSituacionAdmin;
	}	
}