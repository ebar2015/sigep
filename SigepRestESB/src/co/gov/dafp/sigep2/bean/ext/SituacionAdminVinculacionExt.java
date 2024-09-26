/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.SituacionAdminVinculacion;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  SituacionAdminVinculacionExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class SituacionAdminVinculacionExt extends SituacionAdminVinculacion implements Serializable {

	private static final long serialVersionUID = -6231514809790189894L;

	private String nombreSituacion;
	private Integer codTipoIdentificacion;
    private String numeroIdentificacion;
    private Integer codEntidad;
    private String token;
    private String tipoDocumento;
    private String codigoSigep;
    private String codigoSigepComision;
    private String tipoActoAdministrativo;
	/**
	 * @return the nombreSituacion
	 */
	public String getNombreSituacion() {
		return nombreSituacion;
	}
	/**
	 * @param nombreSituacion the nombreSituacion to set
	 */
	public void setNombreSituacion(String nombreSituacion) {
		this.nombreSituacion = nombreSituacion;
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
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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
	 * @return the codigoSigep
	 */
	public String getCodigoSigep() {
		return codigoSigep;
	}
	/**
	 * @param codigoSigep the codigoSigep to set
	 */
	public void setCodigoSigep(String codigoSigep) {
		this.codigoSigep = codigoSigep;
	}
	/**
	 * @return the codigoSigepComision
	 */
	public String getCodigoSigepComision() {
		return codigoSigepComision;
	}
	/**
	 * @param codigoSigepComision the codigoSigepComision to set
	 */
	public void setCodigoSigepComision(String codigoSigepComision) {
		this.codigoSigepComision = codigoSigepComision;
	}
	/**
	 * @return the tipoActoAdministrativo
	 */
	public String getTipoActoAdministrativo() {
		return tipoActoAdministrativo;
	}
	/**
	 * @param tipoActoAdministrativo the tipoActoAdministrativo to set
	 */
	public void setTipoActoAdministrativo(String tipoActoAdministrativo) {
		this.tipoActoAdministrativo = tipoActoAdministrativo;
	}
	
}
