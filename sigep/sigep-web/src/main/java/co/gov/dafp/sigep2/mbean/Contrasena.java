/**
 * 
 */
package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author joseviscaya
 *
 */
@ViewScoped
@ManagedBean
public class Contrasena implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8176207350800633287L;

	private boolean habilitarRestablecerContrasenia;

	public Contrasena() {
		habilitarRestablecerContrasenia = false;
	}

	/**
	 * @return the habilitarRestablecerContrasenia
	 */
	public boolean isHabilitarRestablecerContrasenia() {
		return habilitarRestablecerContrasenia;
	}

	/**
	 * @param habilitarRestablecerContrasenia
	 *            the habilitarRestablecerContrasenia to set
	 */
	public void setHabilitarRestablecerContrasenia(boolean habilitarRestablecerContrasenia) {
		this.habilitarRestablecerContrasenia = habilitarRestablecerContrasenia;
	}

	public void habilitarRestablecerContraseniaF() {
		this.habilitarRestablecerContrasenia = !this.habilitarRestablecerContrasenia;

		if (this.habilitarRestablecerContrasenia) {
			SesionBean.setInthabilitarRestablecerContrasenia(1);
		} else {
			SesionBean.setInthabilitarRestablecerContrasenia(0);
		}
	}

	public void restablecerPassword() {
		this.habilitarRestablecerContrasenia = !this.habilitarRestablecerContrasenia;
	}

}
