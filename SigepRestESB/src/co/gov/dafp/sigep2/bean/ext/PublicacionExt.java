package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.Publicacion;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  PublicacionExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class PublicacionExt extends Publicacion {

	private static final long serialVersionUID = -3509455133671608512L;
	
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
