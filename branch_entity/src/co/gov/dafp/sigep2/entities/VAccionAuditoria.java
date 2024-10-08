package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class VAccionAuditoria extends ErrorMensajes{
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column V_ACCION_AUDITORIA.ACCION_AUDITORIA_ID
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal accionAuditoriaId;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column V_ACCION_AUDITORIA.SIGLA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private String sigla;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column V_ACCION_AUDITORIA.ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private String accion;



	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column V_ACCION_AUDITORIA.ACCION_AUDITORIA_ID
	 * @return  the value of V_ACCION_AUDITORIA.ACCION_AUDITORIA_ID
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getAccionAuditoriaId() {
		return accionAuditoriaId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column V_ACCION_AUDITORIA.ACCION_AUDITORIA_ID
	 * @param accionAuditoriaId  the value for V_ACCION_AUDITORIA.ACCION_AUDITORIA_ID
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAccionAuditoriaId(BigDecimal accionAuditoriaId) {
		this.accionAuditoriaId = accionAuditoriaId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column V_ACCION_AUDITORIA.SIGLA
	 * @return  the value of V_ACCION_AUDITORIA.SIGLA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column V_ACCION_AUDITORIA.SIGLA
	 * @param sigla  the value for V_ACCION_AUDITORIA.SIGLA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column V_ACCION_AUDITORIA.ACCION
	 * @return  the value of V_ACCION_AUDITORIA.ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column V_ACCION_AUDITORIA.ACCION
	 * @param accion  the value for V_ACCION_AUDITORIA.ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	private int limitInit;
    
    
    private int limitEnd;
    
    
    
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