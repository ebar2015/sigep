/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.DocumentosAdicionalesHv;

/**
 * 
 * @author: Jose Viscaya
 * @Date  : Mar 29, 2019, 10:34:25 AM
 */
public class DocumentosAdicionalesHvExt extends DocumentosAdicionalesHv {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7719730119196643750L;
	
	private String descripciontipo;
	
	private String nombretipo;
	
	private boolean verificacion;

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
	
	/**
	 * @return the verificacion
	 */
	public boolean isVerificacion() {
		 if(this.getFlgValidado() != null) {
			 if(this.getFlgValidado().intValue() == 1) {
				 this.verificacion = true;
			 }else {
				 this.verificacion = false;
			 }
		 }else {
			 this.verificacion = false;
		 }
		return verificacion;
	}


	/**
	 * @param verificacion the verificacion to set
	 */
	public void setVerificacion(boolean verificacion) {
		 if(verificacion) {
			 this.setFlgValidado((short) 1);
		 }else {
			 this.setFlgValidado((short) 0);
		 }
		this.verificacion = verificacion;
	}

}
