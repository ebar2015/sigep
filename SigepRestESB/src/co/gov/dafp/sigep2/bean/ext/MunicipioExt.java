package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.Municipio;

/**
 * 
 * @author jgonzalez
 * @version 1.0
 * @Class MunicipioExt
 * @project Sigep-2
 *
 */

public class MunicipioExt extends Municipio implements Serializable{
	private static final long serialVersionUID = -594422575657832964L;

	private String nombreDepartamento;

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
}
