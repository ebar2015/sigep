/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.CuentasDeclaracion;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  CuentasDeclaracionExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class CuentasDeclaracionExt extends CuentasDeclaracion {

	private static final long serialVersionUID = 46375689483793492L;
	
	
	private String nombreTipoCuenta;
	
	private String tipoCuenta;

	/**
	 * @return the nombreTipoCuenta
	 */
	public String getNombreTipoCuenta() {
		return nombreTipoCuenta;
	}

	/**
	 * @param nombreTipoCuenta the nombreTipoCuenta to set
	 */
	public void setNombreTipoCuenta(String nombreTipoCuenta) {
		this.nombreTipoCuenta = nombreTipoCuenta;
	}

	/**
	 * @return the tipoCuenta
	 */
	public String getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

}
