/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.DocumentosAdicionalesHv;

/**
 * @author joseviscaya
 *
 */
public class DocumentosAdicionalesHvExt extends DocumentosAdicionalesHv {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7679076645166346365L;

	
	private String descripciontipo;
	private String nombretipo;


	/**
	 * @return the descripciontipo
	 */
	public String getDescripciontipo() {
		return descripciontipo;
	}


	/**
	 * @param descripciontipo the descripciontipo to set
	 */
	public void setDescripciontipo(String descripciontipo) {
		this.descripciontipo = descripciontipo;
	}


	/**
	 * @return the nombretipo
	 */
	public String getNombretipo() {
		return nombretipo;
	}


	/**
	 * @param nombretipo the nombretipo to set
	 */
	public void setNombretipo(String nombretipo) {
		this.nombretipo = nombretipo;
	}


}
