/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.Festivo;

/**
 * @author joseviscaya
 *
 */
public class FestivoExt extends Festivo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5881092261331677077L;
	
	/**
	 * 
	 */
	private int diasFeriados;
	
	public int getDiasFeriados() {
		return diasFeriados;
	}
	
	public void setDiasFeriados(int diasFeriados) {
		this.diasFeriados = diasFeriados;
	}
}
