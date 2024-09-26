package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class PepRespuestaCuestionarioDetalle extends ErrorMensajes{
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private Long idRespuestaCuestionarioDetalle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_PREGUNTA_CUESTIONARIO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private Long idRespuestaPreguntaCuestionario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private String dsRespuestaDetalle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private BigDecimal audCodUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private Integer audAccion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private Long idPreguntaCuestionarioDetalle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.FLG_ACTIVO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private Short flgActivo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	private String dsRespuestaDetalleDetalle;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_CUESTIONARIO_DETALLE
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public Long getIdRespuestaCuestionarioDetalle() {
		return idRespuestaCuestionarioDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_CUESTIONARIO_DETALLE
	 * @param idRespuestaCuestionarioDetalle  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setIdRespuestaCuestionarioDetalle(Long idRespuestaCuestionarioDetalle) {
		this.idRespuestaCuestionarioDetalle = idRespuestaCuestionarioDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_PREGUNTA_CUESTIONARIO
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_PREGUNTA_CUESTIONARIO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public Long getIdRespuestaPreguntaCuestionario() {
		return idRespuestaPreguntaCuestionario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_PREGUNTA_CUESTIONARIO
	 * @param idRespuestaPreguntaCuestionario  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_RESPUESTA_PREGUNTA_CUESTIONARIO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setIdRespuestaPreguntaCuestionario(Long idRespuestaPreguntaCuestionario) {
		this.idRespuestaPreguntaCuestionario = idRespuestaPreguntaCuestionario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public String getDsRespuestaDetalle() {
		return dsRespuestaDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE
	 * @param dsRespuestaDetalle  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setDsRespuestaDetalle(String dsRespuestaDetalle) {
		this.dsRespuestaDetalle = dsRespuestaDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @param audCodRol  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @param audAccion  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public Long getIdPreguntaCuestionarioDetalle() {
		return idPreguntaCuestionarioDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @param idPreguntaCuestionarioDetalle  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setIdPreguntaCuestionarioDetalle(Long idPreguntaCuestionarioDetalle) {
		this.idPreguntaCuestionarioDetalle = idPreguntaCuestionarioDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.FLG_ACTIVO
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.FLG_ACTIVO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.FLG_ACTIVO
	 * @param flgActivo  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.FLG_ACTIVO
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE_DETALLE
	 * @return  the value of PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public String getDsRespuestaDetalleDetalle() {
		return dsRespuestaDetalleDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE_DETALLE
	 * @param dsRespuestaDetalleDetalle  the value for PEP_RESPUESTA_CUESTIONARIO_DETALLE.DS_RESPUESTA_DETALLE_DETALLE
	 * @mbg.generated  Tue Dec 04 14:58:33 COT 2018
	 */
	public void setDsRespuestaDetalleDetalle(String dsRespuestaDetalleDetalle) {
		this.dsRespuestaDetalleDetalle = dsRespuestaDetalleDetalle;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4838179698326029978L;
}