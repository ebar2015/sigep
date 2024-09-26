/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

/**
 * @author Andrés Felipe Jaramillo Arenas
 *
 */
public class NormaExt extends co.gov.dafp.sigep2.entities.Norma {
	private static final long serialVersionUID = 4983601402857953500L;
	private String tipoNorma;

	public String getTipoNorma() {
		return tipoNorma;
	}

	public void setTipoNorma(String tipoNorma) {
		this.tipoNorma = tipoNorma;
	}
	
	public NormaExt() {
		
	}
	
}
