package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.bean.RecursoAccion;

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
