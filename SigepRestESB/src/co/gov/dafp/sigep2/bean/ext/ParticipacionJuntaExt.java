/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.ParticipacionJunta;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ParticipacionJuntaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class ParticipacionJuntaExt extends ParticipacionJunta implements Serializable {

    private static final long serialVersionUID = 7339658363600339727L;

    private String nombreCalidadMiembro;
    private String nombreTipoVinculacion;
    private String nombreCalidadSocio;
    private String calidadMiembro;
    private String tipoVinculacion;
    private String calidadSocio;
    

    /**
     * @return the nombreCalidadMiembro
     */
    public String getNombreCalidadMiembro() {
	return nombreCalidadMiembro;
    }

    /**
     * @param nombreCalidadMiembro the nombreCalidadMiembro to set
     */
    public void setNombreCalidadMiembro(String nombreCalidadMiembro) {
	this.nombreCalidadMiembro = nombreCalidadMiembro;
    }

    /**
     * @return the nombreTipoVinculacion
     */
    public String getNombreTipoVinculacion() {
	return nombreTipoVinculacion;
    }

    /**
     * @param nombreTipoVinculacion the nombreTipoVinculacion to set
     */
    public void setNombreTipoVinculacion(String nombreTipoVinculacion) {
	this.nombreTipoVinculacion = nombreTipoVinculacion;
    }

    public String getNombreCalidadSocio() {
	return nombreCalidadSocio;
    }

    public void setNombreCalidadSocio(String nombreCalidadSocio) {
	this.nombreCalidadSocio = nombreCalidadSocio;
    }

	/**
	 * @return the calidadMiembro
	 */
	public String getCalidadMiembro() {
		return calidadMiembro;
	}

	/**
	 * @param calidadMiembro the calidadMiembro to set
	 */
	public void setCalidadMiembro(String calidadMiembro) {
		this.calidadMiembro = calidadMiembro;
	}

	/**
	 * @return the tipoVinculacion
	 */
	public String getTipoVinculacion() {
		return tipoVinculacion;
	}

	/**
	 * @param tipoVinculacion the tipoVinculacion to set
	 */
	public void setTipoVinculacion(String tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
	}

	/**
	 * @return the calidadSocio
	 */
	public String getCalidadSocio() {
		return calidadSocio;
	}

	/**
	 * @param calidadSocio the calidadSocio to set
	 */
	public void setCalidadSocio(String calidadSocio) {
		this.calidadSocio = calidadSocio;
	}
}