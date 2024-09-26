/**
 * 
 */
package co.gov.dafp.sigep2.movile;

import co.gov.dafp.sigep2.bean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.bean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de integrar datos adicionales y Datos cotacto del udsuario
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Martes 25 de Julio de 2018
*/
public class DatosAdicionales extends ErrorMensajes{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4560912073798084909L;
	// Varable para almacenar datos de contacto
	private DatoContactoExt datoContacto;
	// Varable para almacenar datos adicoinales
	private DatoAdicionalExt datosDemograficos;
	/**
	 * @return the datoContacto
	 */
	public DatoContactoExt getDatoContacto() {
		return datoContacto;
	}
	/**
	 * @param datoContacto the datoContacto to set
	 */
	public void setDatoContacto(DatoContactoExt datoContacto) {
		this.datoContacto = datoContacto;
	}
	/**
	 * @return the datosDemograficos
	 */
	public DatoAdicionalExt getDatosDemograficos() {
		return datosDemograficos;
	}
	/**
	 * @param datosDemograficos the datosDemograficos to set
	 */
	public void setDatosDemograficos(DatoAdicionalExt datosDemograficos) {
		this.datosDemograficos = datosDemograficos;
	}

}
