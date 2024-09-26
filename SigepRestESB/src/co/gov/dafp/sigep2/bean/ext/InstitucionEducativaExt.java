/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.InstitucionEducativa;

/**
* @author María Alejandra C.
* @version 1.0
* @Class Clase  InstitucionEducativaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */

public class InstitucionEducativaExt extends InstitucionEducativa implements Serializable {
	private static final long serialVersionUID = 1063597764251155563L;
	
	
	private String tipoInstitucion;
	private String institucionMen;

	public String getTipoInstitucion() {
		return tipoInstitucion;
	}

	public void setTipoInstitucion(String tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}
	
	public String getInstitucionMen() {
		return institucionMen;
	}

	public void setInstitucionMen(String institucionMen) {
		this.institucionMen = institucionMen;
	}

	public InstitucionEducativaExt() {
		
	}
	
}
