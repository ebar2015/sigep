package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class PepPreguntasCuestionarioDetalle extends ErrorMensajes{
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private Long idPreguntaCuestionarioDetalle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private Long idPreguntaCuestionario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.DS_DETALLE_PREGUNTA
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private String dsDetallePregunta;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private BigDecimal audCodUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private Integer audAccion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.GENERA_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private String generaDetalle;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_TIPO_PREGUNTA_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	private Short idTipoPreguntaDetalle;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public Long getIdPreguntaCuestionarioDetalle() {
		return idPreguntaCuestionarioDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @param idPreguntaCuestionarioDetalle  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setIdPreguntaCuestionarioDetalle(Long idPreguntaCuestionarioDetalle) {
		this.idPreguntaCuestionarioDetalle = idPreguntaCuestionarioDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public Long getIdPreguntaCuestionario() {
		return idPreguntaCuestionario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO
	 * @param idPreguntaCuestionario  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_PREGUNTA_CUESTIONARIO
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setIdPreguntaCuestionario(Long idPreguntaCuestionario) {
		this.idPreguntaCuestionario = idPreguntaCuestionario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.DS_DETALLE_PREGUNTA
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.DS_DETALLE_PREGUNTA
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public String getDsDetallePregunta() {
		return dsDetallePregunta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.DS_DETALLE_PREGUNTA
	 * @param dsDetallePregunta  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.DS_DETALLE_PREGUNTA
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setDsDetallePregunta(String dsDetallePregunta) {
		this.dsDetallePregunta = dsDetallePregunta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_USUARIO
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @param audCodRol  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_COD_ROL
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @param audAccion  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.AUD_ACCION
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.GENERA_DETALLE
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.GENERA_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public String getGeneraDetalle() {
		return generaDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.GENERA_DETALLE
	 * @param generaDetalle  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.GENERA_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setGeneraDetalle(String generaDetalle) {
		this.generaDetalle = generaDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_TIPO_PREGUNTA_DETALLE
	 * @return  the value of PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_TIPO_PREGUNTA_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public Short getIdTipoPreguntaDetalle() {
		return idTipoPreguntaDetalle;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_TIPO_PREGUNTA_DETALLE
	 * @param idTipoPreguntaDetalle  the value for PEP_PREGUNTAS_CUESTIONARIO_DETALLE.ID_TIPO_PREGUNTA_DETALLE
	 * @mbg.generated  Fri Aug 10 10:51:01 COT 2018
	 */
	public void setIdTipoPreguntaDetalle(Short idTipoPreguntaDetalle) {
		this.idTipoPreguntaDetalle = idTipoPreguntaDetalle;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4487105550727865060L;
}