package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.bean.DependenciaHojaVida;


public class DependenciaHojaVidaExt extends DependenciaHojaVida implements Serializable {

	private static final long serialVersionUID = 7889412621957456319L;
	
	private BigDecimal total;
	private String fieldName;
	private String ascDesc;
	private int limitInit;
    private int limitEnd;
	
	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the ascDesc
	 */
	public String getAscDesc() {
		return ascDesc;
	}
	/**
	 * @param ascDesc the ascDesc to set
	 */
	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}
	/**
	 * @return the limitInit
	 */
	public int getLimitInit() {
		return limitInit;
	}
	/**
	 * @param limitInit the limitInit to set
	 */
	public void setLimitInit(int limitInit) {
		this.limitInit = limitInit;
	}
	/**
	 * @return the limitEnd
	 */
	public int getLimitEnd() {
		return limitEnd;
	}
	/**
	 * @param limitEnd the limitEnd to set
	 */
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
}
