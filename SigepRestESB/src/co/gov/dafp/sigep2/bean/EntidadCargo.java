package co.gov.dafp.sigep2.bean;

import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class EntidadCargo extends ErrorMensajes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1911510853677316894L;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ENTIDAD_CARGO.COD_ENTIDAD_CARGO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	private Long codEntidadCargo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ENTIDAD_CARGO.COD_ENTIDAD
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	private Long codEntidad;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ENTIDAD_CARGO.COD_CARGO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	private Long codCargo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ENTIDAD_CARGO.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ENTIDAD_CARGO.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	private Integer audCodUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ENTIDAD_CARGO.AUD_COD_ROL
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ENTIDAD_CARGO.AUD_ACCION
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	private Integer audAccion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column ENTIDAD_CARGO.FLG_ACTIVO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	private Short flgActivo;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ENTIDAD_CARGO.COD_ENTIDAD_CARGO
	 * @return  the value of ENTIDAD_CARGO.COD_ENTIDAD_CARGO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public Long getCodEntidadCargo() {
		return codEntidadCargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ENTIDAD_CARGO.COD_ENTIDAD_CARGO
	 * @param codCargoEntidad  the value for ENTIDAD_CARGO.COD_ENTIDAD_CARGO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public void setCodEntidadCargo(Long codEntidadCargo) {
		this.codEntidadCargo = codEntidadCargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ENTIDAD_CARGO.COD_ENTIDAD
	 * @return  the value of ENTIDAD_CARGO.COD_ENTIDAD
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public Long getCodEntidad() {
		return codEntidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ENTIDAD_CARGO.COD_ENTIDAD
	 * @param codEntidad  the value for ENTIDAD_CARGO.COD_ENTIDAD
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ENTIDAD_CARGO.COD_CARGO
	 * @return  the value of ENTIDAD_CARGO.COD_CARGO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public Long getCodCargo() {
		return codCargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ENTIDAD_CARGO.COD_CARGO
	 * @param codCargo  the value for ENTIDAD_CARGO.COD_CARGO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public void setCodCargo(Long codCargo) {
		this.codCargo = codCargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ENTIDAD_CARGO.AUD_FECHA_ACTUALIZACION
	 * @return  the value of ENTIDAD_CARGO.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ENTIDAD_CARGO.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for ENTIDAD_CARGO.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ENTIDAD_CARGO.AUD_COD_USUARIO
	 * @return  the value of ENTIDAD_CARGO.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public Integer getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ENTIDAD_CARGO.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for ENTIDAD_CARGO.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public void setAudCodUsuario(Integer audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ENTIDAD_CARGO.AUD_COD_ROL
	 * @return  the value of ENTIDAD_CARGO.AUD_COD_ROL
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ENTIDAD_CARGO.AUD_COD_ROL
	 * @param audCodRol  the value for ENTIDAD_CARGO.AUD_COD_ROL
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ENTIDAD_CARGO.AUD_ACCION
	 * @return  the value of ENTIDAD_CARGO.AUD_ACCION
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ENTIDAD_CARGO.AUD_ACCION
	 * @param audAccion  the value for ENTIDAD_CARGO.AUD_ACCION
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column ENTIDAD_CARGO.FLG_ACTIVO
	 * @return  the value of ENTIDAD_CARGO.FLG_ACTIVO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column ENTIDAD_CARGO.FLG_ACTIVO
	 * @param flgActivo  the value for ENTIDAD_CARGO.FLG_ACTIVO
	 * @mbg.generated  Tue Jul 03 10:52:54 COT 2018
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}
}