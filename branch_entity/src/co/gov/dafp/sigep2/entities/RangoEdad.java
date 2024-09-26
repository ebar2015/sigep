package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class RangoEdad extends ErrorMensajes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2707080235979965337L;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.COD_RANGO_EDAD
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private Long codRangoEdad;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.NOMBRE_RANGO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private String nombreRango;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.LIMITE_INFERIOR
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private Short limiteInferior;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.LIMITE_SUPERIOR
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private Short limiteSuperior;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.FLG_ACTIVO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private Short flgActivo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.AUD_COD_USUARIO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private BigDecimal audCodUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.AUD_COD_ROL
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column RANGO_EDAD.AUD_ACCION
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	private Integer audAccion;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.COD_RANGO_EDAD
	 * @return  the value of RANGO_EDAD.COD_RANGO_EDAD
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public Long getCodRangoEdad() {
		return codRangoEdad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.COD_RANGO_EDAD
	 * @param codRangoEdad  the value for RANGO_EDAD.COD_RANGO_EDAD
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setCodRangoEdad(Long codRangoEdad) {
		this.codRangoEdad = codRangoEdad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.NOMBRE_RANGO
	 * @return  the value of RANGO_EDAD.NOMBRE_RANGO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public String getNombreRango() {
		return nombreRango;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.NOMBRE_RANGO
	 * @param nombreRango  the value for RANGO_EDAD.NOMBRE_RANGO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setNombreRango(String nombreRango) {
		this.nombreRango = nombreRango;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.LIMITE_INFERIOR
	 * @return  the value of RANGO_EDAD.LIMITE_INFERIOR
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public Short getLimiteInferior() {
		return limiteInferior;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.LIMITE_INFERIOR
	 * @param limiteInferior  the value for RANGO_EDAD.LIMITE_INFERIOR
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setLimiteInferior(Short limiteInferior) {
		this.limiteInferior = limiteInferior;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.LIMITE_SUPERIOR
	 * @return  the value of RANGO_EDAD.LIMITE_SUPERIOR
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public Short getLimiteSuperior() {
		return limiteSuperior;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.LIMITE_SUPERIOR
	 * @param limiteSuperior  the value for RANGO_EDAD.LIMITE_SUPERIOR
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setLimiteSuperior(Short limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.FLG_ACTIVO
	 * @return  the value of RANGO_EDAD.FLG_ACTIVO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.FLG_ACTIVO
	 * @param flgActivo  the value for RANGO_EDAD.FLG_ACTIVO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.AUD_FECHA_ACTUALIZACION
	 * @return  the value of RANGO_EDAD.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for RANGO_EDAD.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.AUD_COD_USUARIO
	 * @return  the value of RANGO_EDAD.AUD_COD_USUARIO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for RANGO_EDAD.AUD_COD_USUARIO
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.AUD_COD_ROL
	 * @return  the value of RANGO_EDAD.AUD_COD_ROL
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.AUD_COD_ROL
	 * @param audCodRol  the value for RANGO_EDAD.AUD_COD_ROL
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column RANGO_EDAD.AUD_ACCION
	 * @return  the value of RANGO_EDAD.AUD_ACCION
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column RANGO_EDAD.AUD_ACCION
	 * @param audAccion  the value for RANGO_EDAD.AUD_ACCION
	 * @mbg.generated  Tue May 07 15:49:48 COT 2019
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}
}