package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class HistoricoSituacionAdminVinculacion extends ErrorMensajes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8954590393376643170L;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_HISTORICO_SITUACION_ADMIN
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private Long codHistoricoSituacionAdmin;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_Entidad_Planta_Detalle_ENCARGO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private Long codEntidadPlantaDetalleEncargo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_INICIO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private Date fechaInicio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_FIN
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private Date fechaFin;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_USUARIO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private BigDecimal audCodUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_ROL
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_ACCION
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private Integer audAccion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_PERSONA
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	private BigDecimal codPersona;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_HISTORICO_SITUACION_ADMIN
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.COD_HISTORICO_SITUACION_ADMIN
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public Long getCodHistoricoSituacionAdmin() {
		return codHistoricoSituacionAdmin;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_HISTORICO_SITUACION_ADMIN
	 * @param codHistoricoSituacionAdmin  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.COD_HISTORICO_SITUACION_ADMIN
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setCodHistoricoSituacionAdmin(Long codHistoricoSituacionAdmin) {
		this.codHistoricoSituacionAdmin = codHistoricoSituacionAdmin;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_Entidad_Planta_Detalle_ENCARGO
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.COD_Entidad_Planta_Detalle_ENCARGO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public Long getCodEntidadPlantaDetalleEncargo() {
		return codEntidadPlantaDetalleEncargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_Entidad_Planta_Detalle_ENCARGO
	 * @param codEntidadPlantaDetalleEncargo  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.COD_Entidad_Planta_Detalle_ENCARGO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setCodEntidadPlantaDetalleEncargo(Long codEntidadPlantaDetalleEncargo) {
		this.codEntidadPlantaDetalleEncargo = codEntidadPlantaDetalleEncargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_INICIO
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_INICIO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_INICIO
	 * @param fechaInicio  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_INICIO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_FIN
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_FIN
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_FIN
	 * @param fechaFin  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.FECHA_FIN
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_FECHA_ACTUALIZACION
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_USUARIO
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_USUARIO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_USUARIO
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_ROL
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_ROL
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_ROL
	 * @param audCodRol  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_COD_ROL
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_ACCION
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_ACCION
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_ACCION
	 * @param audAccion  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.AUD_ACCION
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_PERSONA
	 * @return  the value of HISTORICO_SITUACION_ADMIN_VINCULACION.COD_PERSONA
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public BigDecimal getCodPersona() {
		return codPersona;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column HISTORICO_SITUACION_ADMIN_VINCULACION.COD_PERSONA
	 * @param codPersona  the value for HISTORICO_SITUACION_ADMIN_VINCULACION.COD_PERSONA
	 * @mbg.generated  Mon Jul 23 08:05:45 COT 2018
	 */
	public void setCodPersona(BigDecimal codPersona) {
		this.codPersona = codPersona;
	}
}