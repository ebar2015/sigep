package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.PersonaParentesco;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  PersonaParentescoExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class PersonaParentescoExt extends PersonaParentesco {

	private static final long serialVersionUID = -7055799966484978612L;
	private String nombreGenero;
	private String nombreTipoDocumento;
	private String nombreParentesco;
	private String tipoDocumento;
	private String tipoParentesco;
	/**
	 * @return the nombreGenero
	 */
	public String getNombreGenero() {
		return nombreGenero;
	}
	/**
	 * @param nombreGenero the nombreGenero to set
	 */
	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}
	/**
	 * @return the nombreTipoDocuento
	 */
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}
	/**
	 * @param nombreTipoDocuento the nombreTipoDocuento to set
	 */
	public void setNombreTipoDocuento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}
	/**
	 * @return the nombreParentesco
	 */
	public String getNombreParentesco() {
		return nombreParentesco;
	}
	/**
	 * @param nombreParentesco the nombreParentesco to set
	 */
	public void setNombreParentesco(String nombreParentesco) {
		this.nombreParentesco = nombreParentesco;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the tipoParentesco
	 */
	public String getTipoParentesco() {
		return tipoParentesco;
	}
	/**
	 * @param tipoParentesco the tipoParentesco to set
	 */
	public void setTipoParentesco(String tipoParentesco) {
		this.tipoParentesco = tipoParentesco;
	}


	

}
