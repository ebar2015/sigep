package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;


public class AcreenciaObligacion extends ErrorMensajes
{
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.COD_ACREENCIA_OBLIGACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
	
	
	
    private Long codAcreenciaObligacion;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.COD_DECLARACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private BigDecimal codDeclaracion;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.ENTIDAD_PERSONA
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private String entidadPersona;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.SALDO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private BigDecimal saldo;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.AUD_FECHA_ACTUALIZACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private Date audFechaActualizacion;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.AUD_COD_USUARIO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private Long audCodUsuario;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.AUD_COD_ROL
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private Integer audCodRol;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.AUD_ACCION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private Integer audAccion;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.FLG_ACTIVO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private Short flgActivo;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column ACREENCIA_OBLIGACION.CONCEPTO_ACREENCIA
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    private String conceptoAcreencia;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.COD_ACREENCIA_OBLIGACION
     * @return  the value of ACREENCIA_OBLIGACION.COD_ACREENCIA_OBLIGACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public Long getCodAcreenciaObligacion()
    {
	return codAcreenciaObligacion;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.COD_ACREENCIA_OBLIGACION
     * @param codAcreenciaObligacion  the value for ACREENCIA_OBLIGACION.COD_ACREENCIA_OBLIGACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setCodAcreenciaObligacion(Long codAcreenciaObligacion)
    {
	this.codAcreenciaObligacion = codAcreenciaObligacion;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.COD_DECLARACION
     * @return  the value of ACREENCIA_OBLIGACION.COD_DECLARACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public BigDecimal getCodDeclaracion()
    {
	return codDeclaracion;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.COD_DECLARACION
     * @param codDeclaracion  the value for ACREENCIA_OBLIGACION.COD_DECLARACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setCodDeclaracion(BigDecimal codDeclaracion)
    {
	this.codDeclaracion = codDeclaracion;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.ENTIDAD_PERSONA
     * @return  the value of ACREENCIA_OBLIGACION.ENTIDAD_PERSONA
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public String getEntidadPersona()
    {
	return entidadPersona;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.ENTIDAD_PERSONA
     * @param entidadPersona  the value for ACREENCIA_OBLIGACION.ENTIDAD_PERSONA
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setEntidadPersona(String entidadPersona)
    {
	this.entidadPersona = entidadPersona;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.SALDO
     * @return  the value of ACREENCIA_OBLIGACION.SALDO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public BigDecimal getSaldo()
    {
	return saldo;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.SALDO
     * @param saldo  the value for ACREENCIA_OBLIGACION.SALDO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setSaldo(BigDecimal saldo)
    {
	this.saldo = saldo;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.AUD_FECHA_ACTUALIZACION
     * @return  the value of ACREENCIA_OBLIGACION.AUD_FECHA_ACTUALIZACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public Date getAudFechaActualizacion()
    {
	return audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.AUD_FECHA_ACTUALIZACION
     * @param audFechaActualizacion  the value for ACREENCIA_OBLIGACION.AUD_FECHA_ACTUALIZACION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setAudFechaActualizacion(Date audFechaActualizacion)
    {
	this.audFechaActualizacion = audFechaActualizacion;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.AUD_COD_USUARIO
     * @return  the value of ACREENCIA_OBLIGACION.AUD_COD_USUARIO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public Long getAudCodUsuario()
    {
	return audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.AUD_COD_USUARIO
     * @param audCodUsuario  the value for ACREENCIA_OBLIGACION.AUD_COD_USUARIO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setAudCodUsuario(Long audCodUsuario)
    {
	this.audCodUsuario = audCodUsuario;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.AUD_COD_ROL
     * @return  the value of ACREENCIA_OBLIGACION.AUD_COD_ROL
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public Integer getAudCodRol()
    {
	return audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.AUD_COD_ROL
     * @param audCodRol  the value for ACREENCIA_OBLIGACION.AUD_COD_ROL
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setAudCodRol(Integer audCodRol)
    {
	this.audCodRol = audCodRol;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.AUD_ACCION
     * @return  the value of ACREENCIA_OBLIGACION.AUD_ACCION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public Integer getAudAccion()
    {
	return audAccion;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.AUD_ACCION
     * @param audAccion  the value for ACREENCIA_OBLIGACION.AUD_ACCION
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setAudAccion(Integer audAccion)
    {
	this.audAccion = audAccion;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.FLG_ACTIVO
     * @return  the value of ACREENCIA_OBLIGACION.FLG_ACTIVO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public Short getFlgActivo()
    {
	return flgActivo;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.FLG_ACTIVO
     * @param flgActivo  the value for ACREENCIA_OBLIGACION.FLG_ACTIVO
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setFlgActivo(Short flgActivo)
    {
	this.flgActivo = flgActivo;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column ACREENCIA_OBLIGACION.CONCEPTO_ACREENCIA
     * @return  the value of ACREENCIA_OBLIGACION.CONCEPTO_ACREENCIA
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public String getConceptoAcreencia()
    {
	return conceptoAcreencia;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column ACREENCIA_OBLIGACION.CONCEPTO_ACREENCIA
     * @param conceptoAcreencia  the value for ACREENCIA_OBLIGACION.CONCEPTO_ACREENCIA
     * @mbg.generated  Wed Mar 28 06:44:27 COT 2018
     */
    public void setConceptoAcreencia(String conceptoAcreencia)
    {
	this.conceptoAcreencia = conceptoAcreencia;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -3457038895025437680L;
}