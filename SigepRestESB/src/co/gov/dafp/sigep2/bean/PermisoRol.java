package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class PermisoRol extends ErrorMensajes {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3946136601109530925L;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column PERMISO_ROL.COD_PERMISO_ROL
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal codPermisoRol;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column PERMISO_ROL.COD_RECURSO
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal codRecurso;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column PERMISO_ROL.COD_ROL
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal codRol;

	private boolean flgEstado;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column PERMISO_ROL.AUD_FECHA_ACTUALIZACION
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Date audFechaActualizacion;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column PERMISO_ROL.AUD_COD_USUARIO
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Long audCodUsuario;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column PERMISO_ROL.AUD_COD_ROL
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Integer audCodRol;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column PERMISO_ROL.AUD_ACCION
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Integer audAccion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column PERMISO_ROL.COD_PERMISO_ROL
	 * 
	 * @return the value of PERMISO_ROL.COD_PERMISO_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getCodPermisoRol() {
		return codPermisoRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column PERMISO_ROL.COD_PERMISO_ROL
	 * 
	 * @param codPermisoRol
	 *            the value for PERMISO_ROL.COD_PERMISO_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodPermisoRol(BigDecimal codPermisoRol) {
		this.codPermisoRol = codPermisoRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column PERMISO_ROL.COD_RECURSO
	 * 
	 * @return the value of PERMISO_ROL.COD_RECURSO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getCodRecurso() {
		return codRecurso;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column PERMISO_ROL.COD_RECURSO
	 * 
	 * @param codRecurso
	 *            the value for PERMISO_ROL.COD_RECURSO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodRecurso(BigDecimal codRecurso) {
		this.codRecurso = codRecurso;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column PERMISO_ROL.COD_ROL
	 * 
	 * @return the value of PERMISO_ROL.COD_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getCodRol() {
		return codRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column PERMISO_ROL.COD_ROL
	 * 
	 * @param codRol
	 *            the value for PERMISO_ROL.COD_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodRol(BigDecimal codRol) {
		this.codRol = codRol;
	}

	public boolean isFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column PERMISO_ROL.AUD_FECHA_ACTUALIZACION
	 * 
	 * @return the value of PERMISO_ROL.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column PERMISO_ROL.AUD_FECHA_ACTUALIZACION
	 * 
	 * @param audFechaActualizacion
	 *            the value for PERMISO_ROL.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column PERMISO_ROL.AUD_COD_USUARIO
	 * 
	 * @return the value of PERMISO_ROL.AUD_COD_USUARIO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Long getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column PERMISO_ROL.AUD_COD_USUARIO
	 * 
	 * @param audCodUsuario
	 *            the value for PERMISO_ROL.AUD_COD_USUARIO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudCodUsuario(Long audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column PERMISO_ROL.AUD_COD_ROL
	 * 
	 * @return the value of PERMISO_ROL.AUD_COD_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column PERMISO_ROL.AUD_COD_ROL
	 * 
	 * @param audCodRol
	 *            the value for PERMISO_ROL.AUD_COD_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column PERMISO_ROL.AUD_ACCION
	 * 
	 * @return the value of PERMISO_ROL.AUD_ACCION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column PERMISO_ROL.AUD_ACCION
	 * 
	 * @param audAccion
	 *            the value for PERMISO_ROL.AUD_ACCION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
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
	 * @param limitInit
	 *            the limitInit to set
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
	 * @param limitEnd
	 *            the limitEnd to set
	 */
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
}