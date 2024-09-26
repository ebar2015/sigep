/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

/**
 * @author Andrés Felipe Jaramillo Arenas
 *
 */
public class InstitucionEducativaExt extends co.gov.dafp.sigep2.entities.InstitucionEducativa {
	private static final long serialVersionUID = 4983601402857953500L;
	private String tipoInstitucion;
	private String institucionMen;
	

	public String getTipoInstitucion() {
		return tipoInstitucion;
	}

	public void setTipoInstitucion(String tipoInstitucion) {
		this.tipoInstitucion = tipoInstitucion;
	}
	
	public InstitucionEducativaExt() {
		
	}

	public String getInstitucionMen() {
		return institucionMen;
	}

	public void setInstitucionMen(String institucionMen) {
		this.institucionMen = institucionMen;
	}
	
}
