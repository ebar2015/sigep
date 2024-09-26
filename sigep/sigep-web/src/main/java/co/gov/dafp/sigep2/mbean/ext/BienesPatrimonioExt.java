/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.BienesPatrimonio;

/**
 * @author joseviscaya
 *
 */
public class BienesPatrimonioExt extends BienesPatrimonio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7885197280070577399L;
	
	
	private String nombreTipoBien;


	/**
	 * @return the nombreTipoBien
	 */
	public String getNombreTipoBien() {
		return nombreTipoBien;
	}


	/**
	 * @param nombreTipoBien the nombreTipoBien to set
	 */
	public void setNombreTipoBien(String nombreTipoBien) {
		this.nombreTipoBien = nombreTipoBien;
	}

}
