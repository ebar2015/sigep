package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class HistoricoEntidadesDeclaracion extends ErrorMensajes{
	private static final long serialVersionUID = -2645681710017170945L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    private Long codDeclaracionEntidad;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_ENTIDADES_DECLARACION.COD_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    private BigDecimal codEntidad;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    private BigDecimal codDeclaracion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_ENTIDADES_DECLARACION.NOMBRE_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    private String nombreEntidad;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_ENTIDADES_DECLARACION.AUD_FECHA_MODIFICACION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    private Date audFechaModificacion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_ENTIDADES_DECLARACION.AUD_COD_USUARIO
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    private BigDecimal audCodUsuario;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_ENTIDADES_DECLARACION.AUD_COD_ROL
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    private Integer audCodRol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HISTORICO_ENTIDADES_DECLARACION.AUD_ACCION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    private Integer audAccion;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION_ENTIDAD
     *
     * @return the value of HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public Long getCodDeclaracionEntidad() {
        return codDeclaracionEntidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION_ENTIDAD
     *
     * @param codDeclaracionEntidad the value for HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public void setCodDeclaracionEntidad(Long codDeclaracionEntidad) {
        this.codDeclaracionEntidad = codDeclaracionEntidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_ENTIDADES_DECLARACION.COD_ENTIDAD
     *
     * @return the value of HISTORICO_ENTIDADES_DECLARACION.COD_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public BigDecimal getCodEntidad() {
        return codEntidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_ENTIDADES_DECLARACION.COD_ENTIDAD
     *
     * @param codEntidad the value for HISTORICO_ENTIDADES_DECLARACION.COD_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public void setCodEntidad(BigDecimal codEntidad) {
        this.codEntidad = codEntidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION
     *
     * @return the value of HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public BigDecimal getCodDeclaracion() {
        return codDeclaracion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION
     *
     * @param codDeclaracion the value for HISTORICO_ENTIDADES_DECLARACION.COD_DECLARACION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public void setCodDeclaracion(BigDecimal codDeclaracion) {
        this.codDeclaracion = codDeclaracion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_ENTIDADES_DECLARACION.NOMBRE_ENTIDAD
     *
     * @return the value of HISTORICO_ENTIDADES_DECLARACION.NOMBRE_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_ENTIDADES_DECLARACION.NOMBRE_ENTIDAD
     *
     * @param nombreEntidad the value for HISTORICO_ENTIDADES_DECLARACION.NOMBRE_ENTIDAD
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_ENTIDADES_DECLARACION.AUD_FECHA_MODIFICACION
     *
     * @return the value of HISTORICO_ENTIDADES_DECLARACION.AUD_FECHA_MODIFICACION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public Date getAudFechaModificacion() {
        return audFechaModificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_ENTIDADES_DECLARACION.AUD_FECHA_MODIFICACION
     *
     * @param audFechaModificacion the value for HISTORICO_ENTIDADES_DECLARACION.AUD_FECHA_MODIFICACION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public void setAudFechaModificacion(Date audFechaModificacion) {
        this.audFechaModificacion = audFechaModificacion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_ENTIDADES_DECLARACION.AUD_COD_USUARIO
     *
     * @return the value of HISTORICO_ENTIDADES_DECLARACION.AUD_COD_USUARIO
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public BigDecimal getAudCodUsuario() {
        return audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_ENTIDADES_DECLARACION.AUD_COD_USUARIO
     *
     * @param audCodUsuario the value for HISTORICO_ENTIDADES_DECLARACION.AUD_COD_USUARIO
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public void setAudCodUsuario(BigDecimal audCodUsuario) {
        this.audCodUsuario = audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_ENTIDADES_DECLARACION.AUD_COD_ROL
     *
     * @return the value of HISTORICO_ENTIDADES_DECLARACION.AUD_COD_ROL
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public Integer getAudCodRol() {
        return audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_ENTIDADES_DECLARACION.AUD_COD_ROL
     *
     * @param audCodRol the value for HISTORICO_ENTIDADES_DECLARACION.AUD_COD_ROL
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public void setAudCodRol(Integer audCodRol) {
        this.audCodRol = audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HISTORICO_ENTIDADES_DECLARACION.AUD_ACCION
     *
     * @return the value of HISTORICO_ENTIDADES_DECLARACION.AUD_ACCION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public Integer getAudAccion() {
        return audAccion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HISTORICO_ENTIDADES_DECLARACION.AUD_ACCION
     *
     * @param audAccion the value for HISTORICO_ENTIDADES_DECLARACION.AUD_ACCION
     *
     * @mbggenerated Tue Sep 08 07:17:06 COT 2020
     */
    public void setAudAccion(Integer audAccion) {
        this.audAccion = audAccion;
    }
}