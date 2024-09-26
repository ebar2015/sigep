/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.IdiomaPersona;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  IdiomaPersonaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class IdiomaPersonaExt extends IdiomaPersona implements Serializable {
	

	private static final long serialVersionUID = -1787646808606301778L;
	private String nvlConversacion;
	private String nvlRedaccion;
	private String nvlLectura;
	private String nombreIdioma;
	/**
	 * @return the nvlConversacion
	 */
	public String getNvlConversacion() {
		return nvlConversacion;
	}
	/**
	 * @param nvlConversacion the nvlConversacion to set
	 */
	public void setNvlConversacion(String nvlConversacion) {
		this.nvlConversacion = nvlConversacion;
	}
	/**
	 * @return the nvlRedaccion
	 */
	public String getNvlRedaccion() {
		return nvlRedaccion;
	}
	/**
	 * @param nvlRedaccion the nvlRedaccion to set
	 */
	public void setNvlRedaccion(String nvlRedaccion) {
		this.nvlRedaccion = nvlRedaccion;
	}
	/**
	 * @return the nvlLectura
	 */
	public String getNvlLectura() {
		return nvlLectura;
	}
	/**
	 * @param nvlLectura the nvlLectura to set
	 */
	public void setNvlLectura(String nvlLectura) {
		this.nvlLectura = nvlLectura;
	}
	/**
	 * @return the nombreIdioma
	 */
	public String getNombreIdioma() {
		return nombreIdioma;
	}
	/**
	 * @param nombreIdioma the nombreIdioma to set
	 */
	public void setNombreIdioma(String nombreIdioma) {
		this.nombreIdioma = nombreIdioma;
	}

}
