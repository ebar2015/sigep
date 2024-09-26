/**
 * 
 */
package com.formater.jsons2xsd;

import java.io.Serializable;

/**
 * @author Jose Viscaya
 *
 */
public class Validacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8916780658594496555L;
	
	private String key;
	private String value;
	private String validacionName;
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the validacionName
	 */
	public String getValidacionName() {
		return validacionName;
	}
	/**
	 * @param validacionName the validacionName to set
	 */
	public void setValidacionName(String validacionName) {
		this.validacionName = validacionName;
	}
	
	

}
