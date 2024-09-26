/**
 * 
 */

package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.ParticipacionJunta;

/**
 * @author joseviscaya
 *
 */
public class ParticipacionJuntaExt extends ParticipacionJunta implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7339658363600339727L;

    private String nombreCalidadMiembro;
    private String nombreTipoVinculacion;
    private String nombreCalidadSocio;

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
}