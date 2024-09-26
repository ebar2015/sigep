package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class ParticipacionJunta extends ErrorMensajes {
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.COD_PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private Long codParticipacionJunta;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.COD_DECLARACION
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private BigDecimal codDeclaracion;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.ENTIDAD
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private String entidad;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.COD_CALIDAD_MIEMBRO
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private Integer codCalidadMiembro;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.COD_TIPO_VINCULACION
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private Integer codTipoVinculacion;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.AUD_FECHA_ACTUALIZACION
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private Date audFechaActualizacion;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.AUD_COD_USUARIO
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private BigDecimal audCodUsuario;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.AUD_COD_ROL
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private Integer audCodRol;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.AUD_ACCION
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private Integer audAccion;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.FLG_ACTIVO
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private Short flgActivo;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database column PARTICIPACION_JUNTA.COD_CALIDAD_SOCIO
     * 
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    private Integer codCalidadSocio;

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.COD_PARTICIPACION_JUNTA
     * 
     * @return the value of PARTICIPACION_JUNTA.COD_PARTICIPACION_JUNTA
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public Long getCodParticipacionJunta() {
	return codParticipacionJunta;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.COD_PARTICIPACION_JUNTA
     * 
     * @param codParticipacionJunta the value for
     *                              PARTICIPACION_JUNTA.COD_PARTICIPACION_JUNTA
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setCodParticipacionJunta(Long codParticipacionJunta) {
	this.codParticipacionJunta = codParticipacionJunta;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.COD_DECLARACION
     * 
     * @return the value of PARTICIPACION_JUNTA.COD_DECLARACION
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public BigDecimal getCodDeclaracion() {
	return codDeclaracion;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.COD_DECLARACION
     * 
     * @param codDeclaracion the value for PARTICIPACION_JUNTA.COD_DECLARACION
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setCodDeclaracion(BigDecimal codDeclaracion) {
	this.codDeclaracion = codDeclaracion;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.ENTIDAD
     * 
     * @return the value of PARTICIPACION_JUNTA.ENTIDAD
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public String getEntidad() {
	return entidad;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.ENTIDAD
     * 
     * @param entidad the value for PARTICIPACION_JUNTA.ENTIDAD
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setEntidad(String entidad) {
	this.entidad = entidad;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.COD_CALIDAD_MIEMBRO
     * 
     * @return the value of PARTICIPACION_JUNTA.COD_CALIDAD_MIEMBRO
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public Integer getCodCalidadMiembro() {
	return codCalidadMiembro;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.COD_CALIDAD_MIEMBRO
     * 
     * @param codCalidadMiembro the value for
     *                          PARTICIPACION_JUNTA.COD_CALIDAD_MIEMBRO
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setCodCalidadMiembro(Integer codCalidadMiembro) {
	this.codCalidadMiembro = codCalidadMiembro;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.COD_TIPO_VINCULACION
     * 
     * @return the value of PARTICIPACION_JUNTA.COD_TIPO_VINCULACION
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public Integer getCodTipoVinculacion() {
	return codTipoVinculacion;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.COD_TIPO_VINCULACION
     * 
     * @param codTipoVinculacion the value for
     *                           PARTICIPACION_JUNTA.COD_TIPO_VINCULACION
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setCodTipoVinculacion(Integer codTipoVinculacion) {
	this.codTipoVinculacion = codTipoVinculacion;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.AUD_FECHA_ACTUALIZACION
     * 
     * @return the value of PARTICIPACION_JUNTA.AUD_FECHA_ACTUALIZACION
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public Date getAudFechaActualizacion() {
	return audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.AUD_FECHA_ACTUALIZACION
     * 
     * @param audFechaActualizacion the value for
     *                              PARTICIPACION_JUNTA.AUD_FECHA_ACTUALIZACION
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setAudFechaActualizacion(Date audFechaActualizacion) {
	this.audFechaActualizacion = audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.AUD_COD_USUARIO
     * 
     * @return the value of PARTICIPACION_JUNTA.AUD_COD_USUARIO
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public BigDecimal getAudCodUsuario() {
	return audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.AUD_COD_USUARIO
     * 
     * @param audCodUsuario the value for PARTICIPACION_JUNTA.AUD_COD_USUARIO
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setAudCodUsuario(BigDecimal audCodUsuario) {
	this.audCodUsuario = audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.AUD_COD_ROL
     * 
     * @return the value of PARTICIPACION_JUNTA.AUD_COD_ROL
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public Integer getAudCodRol() {
	return audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.AUD_COD_ROL
     * 
     * @param audCodRol the value for PARTICIPACION_JUNTA.AUD_COD_ROL
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setAudCodRol(Integer audCodRol) {
	this.audCodRol = audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.AUD_ACCION
     * 
     * @return the value of PARTICIPACION_JUNTA.AUD_ACCION
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public Integer getAudAccion() {
	return audAccion;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.AUD_ACCION
     * 
     * @param audAccion the value for PARTICIPACION_JUNTA.AUD_ACCION
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setAudAccion(Integer audAccion) {
	this.audAccion = audAccion;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.FLG_ACTIVO
     * 
     * @return the value of PARTICIPACION_JUNTA.FLG_ACTIVO
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public Short getFlgActivo() {
	return flgActivo;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.FLG_ACTIVO
     * 
     * @param flgActivo the value for PARTICIPACION_JUNTA.FLG_ACTIVO
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setFlgActivo(Short flgActivo) {
	this.flgActivo = flgActivo;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value
     * of the database column PARTICIPACION_JUNTA.COD_CALIDAD_SOCIO
     * 
     * @return the value of PARTICIPACION_JUNTA.COD_CALIDAD_SOCIO
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public Integer getCodCalidadSocio() {
	return codCalidadSocio;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of
     * the database column PARTICIPACION_JUNTA.COD_CALIDAD_SOCIO
     * 
     * @param codCalidadSocio the value for PARTICIPACION_JUNTA.COD_CALIDAD_SOCIO
     * @mbg.generated Fri Mar 23 16:35:44 COT 2018
     */
    public void setCodCalidadSocio(Integer codCalidadSocio) {
	this.codCalidadSocio = codCalidadSocio;
    }

    /**
     * 
     */
    private Integer limitIni;
    /**
     * 
     */
    private Integer limitFin;

    /**
     * @return the limitFin
     */
    public Integer getLimitFin() {
	return limitFin;
    }

    /**
     * @param limitFin the limitFin to set
     */
    public void setLimitFin(Integer limitFin) {
	this.limitFin = limitFin;
    }

    /**
     * @return the limitIni
     */
    public Integer getLimitIni() {
	return limitIni;
    }

    /**
     * @param limitIni the limitIni to set
     */
    public void setLimitIni(Integer limitIni) {
	this.limitIni = limitIni;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -5911419867545987484L;
}