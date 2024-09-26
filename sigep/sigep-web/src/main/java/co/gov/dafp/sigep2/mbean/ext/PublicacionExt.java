/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.Publicacion;

/**
 * @author joseviscaya
 *
 */
public class PublicacionExt extends Publicacion implements Serializable {

	private static final long serialVersionUID = -1025524769312911270L;
	
	private String nombreTipoArticulo;
	private String nombreTipoPublicacion;
	private String nombreOtroTipoPublicacion;
	
	public String getNombreTipoArticulo() {
		return nombreTipoArticulo;
	}
	public void setNombreTipoArticulo(String nombreTipoArticulo) {
		this.nombreTipoArticulo = nombreTipoArticulo;
	}
	public String getNombreTipoPublicacion() {
		return nombreTipoPublicacion;
	}
	public void setNombreTipoPublicacion(String nombreTipoPublicacion) {
		this.nombreTipoPublicacion = nombreTipoPublicacion;
	}
	public String getNombreOtroTipoPublicacion() {
		return nombreOtroTipoPublicacion;
	}
	public void setNombreOtroTipoPublicacion(String nombreOtroTipoPublicacion) {
		this.nombreOtroTipoPublicacion = nombreOtroTipoPublicacion;
	}
	
}
