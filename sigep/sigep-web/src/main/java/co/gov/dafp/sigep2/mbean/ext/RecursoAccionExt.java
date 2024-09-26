package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.entities.RecursoAccion;

public class RecursoAccionExt extends RecursoAccion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal codUsuario;

	public BigDecimal getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(BigDecimal codUsuario) {
		this.codUsuario = codUsuario;
	}
	
	
	

}
