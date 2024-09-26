package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class UsuarioRolEntidad extends ErrorMensajes{
    /**
	 * 
	 */
	private static final long serialVersionUID = -532992310993053072L;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.COD_USUARIO_ROL_ENTIDAD
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal codUsuarioRolEntidad;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.COD_USUARIO_ENTIDAD
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal codUsuarioEntidad;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal codRol;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.FLG_ACTIVO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private Short flgActivo;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private Date audFechaActualizacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal audCodUsuario;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.AUD_COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal audCodRol;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.AUD_ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal audAccion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column USUARIO_ROL_ENTIDAD.FLG_ESTADO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal flgEstado;



	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.COD_USUARIO_ROL_ENTIDAD
	 * @return  the value of USUARIO_ROL_ENTIDAD.COD_USUARIO_ROL_ENTIDAD
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getCodUsuarioRolEntidad() {
		return codUsuarioRolEntidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.COD_USUARIO_ROL_ENTIDAD
	 * @param codUsuarioRolEntidad  the value for USUARIO_ROL_ENTIDAD.COD_USUARIO_ROL_ENTIDAD
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodUsuarioRolEntidad(BigDecimal codUsuarioRolEntidad) {
		this.codUsuarioRolEntidad = codUsuarioRolEntidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.COD_USUARIO_ENTIDAD
	 * @return  the value of USUARIO_ROL_ENTIDAD.COD_USUARIO_ENTIDAD
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getCodUsuarioEntidad() {
		return codUsuarioEntidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.COD_USUARIO_ENTIDAD
	 * @param codUsuarioEntidad  the value for USUARIO_ROL_ENTIDAD.COD_USUARIO_ENTIDAD
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodUsuarioEntidad(BigDecimal codUsuarioEntidad) {
		this.codUsuarioEntidad = codUsuarioEntidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.COD_ROL
	 * @return  the value of USUARIO_ROL_ENTIDAD.COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getCodRol() {
		return codRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.COD_ROL
	 * @param codRol  the value for USUARIO_ROL_ENTIDAD.COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodRol(BigDecimal codRol) {
		this.codRol = codRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.FLG_ACTIVO
	 * @return  the value of USUARIO_ROL_ENTIDAD.FLG_ACTIVO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.FLG_ACTIVO
	 * @param flgActivo  the value for USUARIO_ROL_ENTIDAD.FLG_ACTIVO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.AUD_FECHA_ACTUALIZACION
	 * @return  the value of USUARIO_ROL_ENTIDAD.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for USUARIO_ROL_ENTIDAD.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.AUD_COD_USUARIO
	 * @return  the value of USUARIO_ROL_ENTIDAD.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for USUARIO_ROL_ENTIDAD.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.AUD_COD_ROL
	 * @return  the value of USUARIO_ROL_ENTIDAD.AUD_COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.AUD_COD_ROL
	 * @param audCodRol  the value for USUARIO_ROL_ENTIDAD.AUD_COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudCodRol(BigDecimal audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.AUD_ACCION
	 * @return  the value of USUARIO_ROL_ENTIDAD.AUD_ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.AUD_ACCION
	 * @param audAccion  the value for USUARIO_ROL_ENTIDAD.AUD_ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudAccion(BigDecimal audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column USUARIO_ROL_ENTIDAD.FLG_ESTADO
	 * @return  the value of USUARIO_ROL_ENTIDAD.FLG_ESTADO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getFlgEstado() {
		return flgEstado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column USUARIO_ROL_ENTIDAD.FLG_ESTADO
	 * @param flgEstado  the value for USUARIO_ROL_ENTIDAD.FLG_ESTADO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setFlgEstado(BigDecimal flgEstado) {
		this.flgEstado = flgEstado;
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