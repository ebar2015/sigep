package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class UsuarioSession extends ErrorMensajes{
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.COD_USUARIO_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private BigDecimal codUsuarioSession;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.COD_USUARIO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private BigDecimal codUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.NOMBRE_FUNCION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private String nombreFuncion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.FECHA_INICIO_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private Date fechaInicioSession;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.FECHA_FIN_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private Date fechaFinSession;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.FLG_ACTIVO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private Short flgActivo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.AUD_COD_USUARIO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private BigDecimal audCodUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.AUD_COD_ROL
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_SESSION.AUD_ACCION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	private Integer audAccion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.COD_USUARIO_SESSION
	 * @return  the value of USUARIO_SESSION.COD_USUARIO_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public BigDecimal getCodUsuarioSession() {
		return codUsuarioSession;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.COD_USUARIO_SESSION
	 * @param codUsuarioSession  the value for USUARIO_SESSION.COD_USUARIO_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setCodUsuarioSession(BigDecimal codUsuarioSession) {
		this.codUsuarioSession = codUsuarioSession;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.COD_USUARIO
	 * @return  the value of USUARIO_SESSION.COD_USUARIO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public BigDecimal getCodUsuario() {
		return codUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.COD_USUARIO
	 * @param codUsuario  the value for USUARIO_SESSION.COD_USUARIO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setCodUsuario(BigDecimal codUsuario) {
		this.codUsuario = codUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.NOMBRE_FUNCION
	 * @return  the value of USUARIO_SESSION.NOMBRE_FUNCION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public String getNombreFuncion() {
		return nombreFuncion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.NOMBRE_FUNCION
	 * @param nombreFuncion  the value for USUARIO_SESSION.NOMBRE_FUNCION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.FECHA_INICIO_SESSION
	 * @return  the value of USUARIO_SESSION.FECHA_INICIO_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public Date getFechaInicioSession() {
		return fechaInicioSession;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.FECHA_INICIO_SESSION
	 * @param fechaInicioSession  the value for USUARIO_SESSION.FECHA_INICIO_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setFechaInicioSession(Date fechaInicioSession) {
		this.fechaInicioSession = fechaInicioSession;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.FECHA_FIN_SESSION
	 * @return  the value of USUARIO_SESSION.FECHA_FIN_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public Date getFechaFinSession() {
		return fechaFinSession;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.FECHA_FIN_SESSION
	 * @param fechaFinSession  the value for USUARIO_SESSION.FECHA_FIN_SESSION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setFechaFinSession(Date fechaFinSession) {
		this.fechaFinSession = fechaFinSession;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.FLG_ACTIVO
	 * @return  the value of USUARIO_SESSION.FLG_ACTIVO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.FLG_ACTIVO
	 * @param flgActivo  the value for USUARIO_SESSION.FLG_ACTIVO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.AUD_FECHA_ACTUALIZACION
	 * @return  the value of USUARIO_SESSION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for USUARIO_SESSION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.AUD_COD_USUARIO
	 * @return  the value of USUARIO_SESSION.AUD_COD_USUARIO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for USUARIO_SESSION.AUD_COD_USUARIO
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.AUD_COD_ROL
	 * @return  the value of USUARIO_SESSION.AUD_COD_ROL
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.AUD_COD_ROL
	 * @param audCodRol  the value for USUARIO_SESSION.AUD_COD_ROL
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_SESSION.AUD_ACCION
	 * @return  the value of USUARIO_SESSION.AUD_ACCION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_SESSION.AUD_ACCION
	 * @param audAccion  the value for USUARIO_SESSION.AUD_ACCION
	 * @mbg.generated  Mon Jan 14 08:32:00 COT 2019
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6729303321077576807L;
}