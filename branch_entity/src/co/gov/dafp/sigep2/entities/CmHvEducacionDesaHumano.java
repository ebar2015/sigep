package co.gov.dafp.sigep2.entities;

import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class CmHvEducacionDesaHumano extends ErrorMensajes{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7798558001050946874L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.COD_CM_HV_EDUCACION_DESA_HUMANO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Long codCmHvEducacionDesaHumano;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.COD_TIPO_IDENTIFICACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String codTipoIdentificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.NUMERO_IDENTIFICACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String numeroIdentificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.NOMBRE_INSTITUCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String nombreInstitucion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.CURSO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String curso;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.MODALIDAD
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String modalidad;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.FECHA_FIN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String fechaFin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.DOCUMENTO_SOPORTE_ESTUDIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String documentoSoporteEstudio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Date audFechaActualizacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_USUARIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Long audCodUsuario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_ROL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Integer audCodRol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_ACCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Integer audAccion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.COD_PROCESO_CARGA_MASIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private Short codProcesoCargaMasiva;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column CM_HV_EDUCACION_DESA_HUMANO.RESULTADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    private String resultado;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.COD_CM_HV_EDUCACION_DESA_HUMANO
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.COD_CM_HV_EDUCACION_DESA_HUMANO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Long getCodCmHvEducacionDesaHumano() {
        return codCmHvEducacionDesaHumano;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.COD_CM_HV_EDUCACION_DESA_HUMANO
     *
     * @param codCmHvEducacionDesaHumano the value for CM_HV_EDUCACION_DESA_HUMANO.COD_CM_HV_EDUCACION_DESA_HUMANO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setCodCmHvEducacionDesaHumano(Long codCmHvEducacionDesaHumano) {
        this.codCmHvEducacionDesaHumano = codCmHvEducacionDesaHumano;
    }

  
    /**
	 * @return the codTipoIdentificacion
	 */
	public String getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	/**
	 * @param codTipoIdentificacion the codTipoIdentificacion to set
	 */
	public void setCodTipoIdentificacion(String codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.NUMERO_IDENTIFICACION
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.NUMERO_IDENTIFICACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.NUMERO_IDENTIFICACION
     *
     * @param numeroIdentificacion the value for CM_HV_EDUCACION_DESA_HUMANO.NUMERO_IDENTIFICACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.NOMBRE_INSTITUCION
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.NOMBRE_INSTITUCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.NOMBRE_INSTITUCION
     *
     * @param nombreInstitucion the value for CM_HV_EDUCACION_DESA_HUMANO.NOMBRE_INSTITUCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.CURSO
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.CURSO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getCurso() {
        return curso;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.CURSO
     *
     * @param curso the value for CM_HV_EDUCACION_DESA_HUMANO.CURSO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.MODALIDAD
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.MODALIDAD
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.MODALIDAD
     *
     * @param modalidad the value for CM_HV_EDUCACION_DESA_HUMANO.MODALIDAD
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.FECHA_FIN
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.FECHA_FIN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.FECHA_FIN
     *
     * @param fechaFin the value for CM_HV_EDUCACION_DESA_HUMANO.FECHA_FIN
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.DOCUMENTO_SOPORTE_ESTUDIO
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.DOCUMENTO_SOPORTE_ESTUDIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getDocumentoSoporteEstudio() {
        return documentoSoporteEstudio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.DOCUMENTO_SOPORTE_ESTUDIO
     *
     * @param documentoSoporteEstudio the value for CM_HV_EDUCACION_DESA_HUMANO.DOCUMENTO_SOPORTE_ESTUDIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setDocumentoSoporteEstudio(String documentoSoporteEstudio) {
        this.documentoSoporteEstudio = documentoSoporteEstudio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_FECHA_ACTUALIZACION
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Date getAudFechaActualizacion() {
        return audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_FECHA_ACTUALIZACION
     *
     * @param audFechaActualizacion the value for CM_HV_EDUCACION_DESA_HUMANO.AUD_FECHA_ACTUALIZACION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAudFechaActualizacion(Date audFechaActualizacion) {
        this.audFechaActualizacion = audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_USUARIO
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_USUARIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Long getAudCodUsuario() {
        return audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_USUARIO
     *
     * @param audCodUsuario the value for CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_USUARIO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAudCodUsuario(Long audCodUsuario) {
        this.audCodUsuario = audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_ROL
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_ROL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Integer getAudCodRol() {
        return audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_ROL
     *
     * @param audCodRol the value for CM_HV_EDUCACION_DESA_HUMANO.AUD_COD_ROL
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAudCodRol(Integer audCodRol) {
        this.audCodRol = audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_ACCION
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.AUD_ACCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Integer getAudAccion() {
        return audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.AUD_ACCION
     *
     * @param audAccion the value for CM_HV_EDUCACION_DESA_HUMANO.AUD_ACCION
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setAudAccion(Integer audAccion) {
        this.audAccion = audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.COD_PROCESO_CARGA_MASIVA
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.COD_PROCESO_CARGA_MASIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public Short getCodProcesoCargaMasiva() {
        return codProcesoCargaMasiva;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.COD_PROCESO_CARGA_MASIVA
     *
     * @param codProcesoCargaMasiva the value for CM_HV_EDUCACION_DESA_HUMANO.COD_PROCESO_CARGA_MASIVA
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setCodProcesoCargaMasiva(Short codProcesoCargaMasiva) {
        this.codProcesoCargaMasiva = codProcesoCargaMasiva;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column CM_HV_EDUCACION_DESA_HUMANO.RESULTADO
     *
     * @return the value of CM_HV_EDUCACION_DESA_HUMANO.RESULTADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column CM_HV_EDUCACION_DESA_HUMANO.RESULTADO
     *
     * @param resultado the value for CM_HV_EDUCACION_DESA_HUMANO.RESULTADO
     *
     * @mbg.generated Thu Jun 07 11:19:44 COT 2018
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}