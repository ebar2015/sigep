package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class PreguntaOpinion extends ErrorMensajes{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1411643835634918955L;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.COD_PREGUNTA_OPINION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private Integer codPreguntaOpinion;
	
	
	private Integer total;
	
	private Integer totalRespuesta;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private String pregunta;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.FECHA_INICIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private Date fechaInicio;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.FECHA_FIN
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private Date fechaFin;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private Date audFechaActualizacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal audCodUsuario;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.AUD_COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private BigDecimal audCodRol;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.AUD_ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private Integer audAccion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column PREGUNTA_OPINION.FLG_ACTIVO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	private Short flgActivo;



	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.COD_PREGUNTA_OPINION
	 * @return  the value of PREGUNTA_OPINION.COD_PREGUNTA_OPINION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Integer getCodPreguntaOpinion() {
		return codPreguntaOpinion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.COD_PREGUNTA_OPINION
	 * @param codPreguntaOpinion  the value for PREGUNTA_OPINION.COD_PREGUNTA_OPINION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setCodPreguntaOpinion(Integer codPreguntaOpinion) {
		this.codPreguntaOpinion = codPreguntaOpinion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.PREGUNTA
	 * @return  the value of PREGUNTA_OPINION.PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public String getPregunta() {
		return pregunta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.PREGUNTA
	 * @param pregunta  the value for PREGUNTA_OPINION.PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.FECHA_INICIO
	 * @return  the value of PREGUNTA_OPINION.FECHA_INICIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.FECHA_INICIO
	 * @param fechaInicio  the value for PREGUNTA_OPINION.FECHA_INICIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.FECHA_FIN
	 * @return  the value of PREGUNTA_OPINION.FECHA_FIN
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.FECHA_FIN
	 * @param fechaFin  the value for PREGUNTA_OPINION.FECHA_FIN
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.AUD_FECHA_ACTUALIZACION
	 * @return  the value of PREGUNTA_OPINION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for PREGUNTA_OPINION.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.AUD_COD_USUARIO
	 * @return  the value of PREGUNTA_OPINION.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for PREGUNTA_OPINION.AUD_COD_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.AUD_COD_ROL
	 * @return  the value of PREGUNTA_OPINION.AUD_COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public BigDecimal getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.AUD_COD_ROL
	 * @param audCodRol  the value for PREGUNTA_OPINION.AUD_COD_ROL
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudCodRol(BigDecimal audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.AUD_ACCION
	 * @return  the value of PREGUNTA_OPINION.AUD_ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.AUD_ACCION
	 * @param audAccion  the value for PREGUNTA_OPINION.AUD_ACCION
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column PREGUNTA_OPINION.FLG_ACTIVO
	 * @return  the value of PREGUNTA_OPINION.FLG_ACTIVO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column PREGUNTA_OPINION.FLG_ACTIVO
	 * @param flgActivo  the value for PREGUNTA_OPINION.FLG_ACTIVO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
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

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the totalRespuesta
	 */
	public Integer getTotalRespuesta() {
		return totalRespuesta;
	}

	/**
	 * @param totalRespuesta the totalRespuesta to set
	 */
	public void setTotalRespuesta(Integer totalRespuesta) {
		this.totalRespuesta = totalRespuesta;
	}
}