/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.GrupoDependencia;

/**
 * @author joseviscaya
 *
 */
public class GrupoDependenciaExt extends GrupoDependencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7885197280070577399L;
	private Long codEntidad;
	private String desRegionalSeccionalZonal;
	
	public Long getCodEntidad() {
		return codEntidad;
	}
	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}
	public String getDesRegionalSeccionalZonal() {
		return getFlgRegionalSeccionalZonal()!=null&&getFlgRegionalSeccionalZonal()==1?"Si":"No";
	}
	public void setDesRegionalSeccionalZonal(String desRegionalSeccionalZonal) {
		this.desRegionalSeccionalZonal = desRegionalSeccionalZonal;
	}
	
	
	
	

	
	

}
