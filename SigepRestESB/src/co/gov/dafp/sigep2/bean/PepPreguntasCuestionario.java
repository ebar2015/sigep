package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class PepPreguntasCuestionario extends ErrorMensajes{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4620752822323754631L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO.ID_PREGUNTA_CUESTIONARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    private Long idPreguntaCuestionario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO.ID_CUESTIONARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    private Long idCuestionario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO.DS_PREGUNTA
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    private String dsPregunta;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    private Date audFechaActualizacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO.ID_TIPO_PREGUNTA
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    private Short idTipoPregunta;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_USUARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    private BigDecimal audCodUsuario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_ROL
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    private Integer audCodRol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_ACCION
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    private Integer audAccion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO.ID_PREGUNTA_CUESTIONARIO
     *
     * @return the value of PEP_PREGUNTAS_CUESTIONARIO.ID_PREGUNTA_CUESTIONARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public Long getIdPreguntaCuestionario() {
        return idPreguntaCuestionario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO.ID_PREGUNTA_CUESTIONARIO
     *
     * @param idPreguntaCuestionario the value for PEP_PREGUNTAS_CUESTIONARIO.ID_PREGUNTA_CUESTIONARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public void setIdPreguntaCuestionario(Long idPreguntaCuestionario) {
        this.idPreguntaCuestionario = idPreguntaCuestionario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO.ID_CUESTIONARIO
     *
     * @return the value of PEP_PREGUNTAS_CUESTIONARIO.ID_CUESTIONARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public Long getIdCuestionario() {
        return idCuestionario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO.ID_CUESTIONARIO
     *
     * @param idCuestionario the value for PEP_PREGUNTAS_CUESTIONARIO.ID_CUESTIONARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public void setIdCuestionario(Long idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO.DS_PREGUNTA
     *
     * @return the value of PEP_PREGUNTAS_CUESTIONARIO.DS_PREGUNTA
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public String getDsPregunta() {
        return dsPregunta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO.DS_PREGUNTA
     *
     * @param dsPregunta the value for PEP_PREGUNTAS_CUESTIONARIO.DS_PREGUNTA
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public void setDsPregunta(String dsPregunta) {
        this.dsPregunta = dsPregunta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_FECHA_ACTUALIZACION
     *
     * @return the value of PEP_PREGUNTAS_CUESTIONARIO.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public Date getAudFechaActualizacion() {
        return audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_FECHA_ACTUALIZACION
     *
     * @param audFechaActualizacion the value for PEP_PREGUNTAS_CUESTIONARIO.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public void setAudFechaActualizacion(Date audFechaActualizacion) {
        this.audFechaActualizacion = audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO.ID_TIPO_PREGUNTA
     *
     * @return the value of PEP_PREGUNTAS_CUESTIONARIO.ID_TIPO_PREGUNTA
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public Short getIdTipoPregunta() {
        return idTipoPregunta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO.ID_TIPO_PREGUNTA
     *
     * @param idTipoPregunta the value for PEP_PREGUNTAS_CUESTIONARIO.ID_TIPO_PREGUNTA
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public void setIdTipoPregunta(Short idTipoPregunta) {
        this.idTipoPregunta = idTipoPregunta;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_USUARIO
     *
     * @return the value of PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_USUARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public BigDecimal getAudCodUsuario() {
        return audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_USUARIO
     *
     * @param audCodUsuario the value for PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_USUARIO
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public void setAudCodUsuario(BigDecimal audCodUsuario) {
        this.audCodUsuario = audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_ROL
     *
     * @return the value of PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_ROL
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public Integer getAudCodRol() {
        return audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_ROL
     *
     * @param audCodRol the value for PEP_PREGUNTAS_CUESTIONARIO.AUD_COD_ROL
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public void setAudCodRol(Integer audCodRol) {
        this.audCodRol = audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_ACCION
     *
     * @return the value of PEP_PREGUNTAS_CUESTIONARIO.AUD_ACCION
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public Integer getAudAccion() {
        return audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PEP_PREGUNTAS_CUESTIONARIO.AUD_ACCION
     *
     * @param audAccion the value for PEP_PREGUNTAS_CUESTIONARIO.AUD_ACCION
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    public void setAudAccion(Integer audAccion) {
        this.audAccion = audAccion;
    }
}