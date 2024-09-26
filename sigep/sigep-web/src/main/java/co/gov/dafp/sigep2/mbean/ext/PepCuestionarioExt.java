package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.PepCuestionario;

public class PepCuestionarioExt extends  PepCuestionario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreCuestionario;

	public String getNombreCuestionario() {
		return nombreCuestionario;
	}

	public void setNombreCuestionario(String nombreCuestionario) {
		this.nombreCuestionario = nombreCuestionario;
	}
	

}
