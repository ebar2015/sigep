package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.Idioma;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  IdiomaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class IdiomaExt extends Idioma implements Serializable {

	private static final long serialVersionUID = -8935156948680023423L;
	private String nvlConversacion;
	private String nvlRedaccion;
	private String nvlLectura;
	private Short nativo;
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
	 * @return the nativo
	 */
	public Short getNativo() {
		return nativo;
	}
	/**
	 * @param nativo the nativo to set
	 */
	public void setNativo(Short nativo) {
		this.nativo = nativo;
	}

}
