/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.BienesPatrimonio;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  BienesPatrimonioExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class BienesPatrimonioExt extends BienesPatrimonio implements Serializable {

	private static final long serialVersionUID = 7885197280070577399L;
	
	private String nombreTipoBien;
	private String tipoBien;


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


	/**
	 * @return the tipoBien
	 */
	public String getTipoBien() {
		return tipoBien;
	}


	/**
	 * @param tipoBien the tipoBien to set
	 */
	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}

}
