package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class Rol extends ErrorMensajes {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7425121699235799662L;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.COD_ROL
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal codRol;

	private int total;

	private int limitInit;
    
    
    private int limitEnd;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.NOMBRE
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private String nombre;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.DESCRIPCION_ROL
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private String descripcionRol;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.COD_ROL_PADRE
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal codRolPadre;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.FLG_ACTIVO
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Short flgActivo;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.FECHA_MODIFICACION
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Date fechaModificacion;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.FLG_ESTADO
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal flgEstado;

	private boolean flgRolBase;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.AUD_FECHA_ACTUALIZACION
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Date audFechaActualizacion;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.AUD_COD_USUARIO
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal audCodUsuario;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.AUD_COD_ROL
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Integer audCodRol;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column ROL.AUD_ACCION
	 * 
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	private Integer audAccion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.COD_ROL
	 * 
	 * @return the value of ROL.COD_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getCodRol() {
		return codRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.COD_ROL
	 * 
	 * @param codRol
	 *            the value for ROL.COD_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodRol(BigDecimal codRol) {
		this.codRol = codRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.NOMBRE
	 * 
	 * @return the value of ROL.NOMBRE
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.NOMBRE
	 * 
	 * @param nombre
	 *            the value for ROL.NOMBRE
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.DESCRIPCION_ROL
	 * 
	 * @return the value of ROL.DESCRIPCION_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public String getDescripcionRol() {
		return descripcionRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.DESCRIPCION_ROL
	 * 
	 * @param descripcionRol
	 *            the value for ROL.DESCRIPCION_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.COD_ROL_PADRE
	 * 
	 * @return the value of ROL.COD_ROL_PADRE
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getCodRolPadre() {
		return codRolPadre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.COD_ROL_PADRE
	 * 
	 * @param codRolPadre
	 *            the value for ROL.COD_ROL_PADRE
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodRolPadre(BigDecimal codRolPadre) {
		this.codRolPadre = codRolPadre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.FLG_ACTIVO
	 * 
	 * @return the value of ROL.FLG_ACTIVO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.FLG_ACTIVO
	 * 
	 * @param flgActivo
	 *            the value for ROL.FLG_ACTIVO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.FECHA_MODIFICACION
	 * 
	 * @return the value of ROL.FECHA_MODIFICACION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.FECHA_MODIFICACION
	 * 
	 * @param fechaModificacion
	 *            the value for ROL.FECHA_MODIFICACION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.FLG_ESTADO
	 * 
	 * @return the value of ROL.FLG_ESTADO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getFlgEstado() {
		return flgEstado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.FLG_ESTADO
	 * 
	 * @param flgEstado
	 *            the value for ROL.FLG_ESTADO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setFlgEstado(BigDecimal flgEstado) {
		this.flgEstado = flgEstado;
	}

	public boolean isFlgRolBase() {
		return flgRolBase;
	}

	public void setFlgRolBase(boolean flgRolBase) {
		this.flgRolBase = flgRolBase;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.AUD_FECHA_ACTUALIZACION
	 * 
	 * @return the value of ROL.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.AUD_FECHA_ACTUALIZACION
	 * 
	 * @param audFechaActualizacion
	 *            the value for ROL.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.AUD_COD_USUARIO
	 * 
	 * @return the value of ROL.AUD_COD_USUARIO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.AUD_COD_USUARIO
	 * 
	 * @param audCodUsuario
	 *            the value for ROL.AUD_COD_USUARIO
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.AUD_COD_ROL
	 * 
	 * @return the value of ROL.AUD_COD_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.AUD_COD_ROL
	 * 
	 * @param audCodRol
	 *            the value for ROL.AUD_COD_ROL
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ROL.AUD_ACCION
	 * 
	 * @return the value of ROL.AUD_ACCION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ROL.AUD_ACCION
	 * 
	 * @param audAccion
	 *            the value for ROL.AUD_ACCION
	 * @mbg.generated Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	public int getLimitInit() {
		return limitInit;
	}

	public void setLimitInit(int limitInit) {
		this.limitInit = limitInit;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}


}