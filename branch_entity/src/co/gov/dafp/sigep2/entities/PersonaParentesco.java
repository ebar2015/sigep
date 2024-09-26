package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class PersonaParentesco extends ErrorMensajes {
    /**
     * 
     */
    private static final long serialVersionUID = -8207213673250550538L;
    /**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.COD_PERSONA_PARENTESCO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Integer codPersonaParentesco;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.COD_DECLARACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Long codDeclaracion;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.COD_PERSONA
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private BigDecimal codPersona;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.COD_TIPO_PARENTESCO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Integer codTipoParentesco;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.COD_TIPO_IDENTIFICACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Integer codTipoIdentificacion;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.NUM_IDENTIFICACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private String numIdentificacion;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.PRIMER_NOMBRE
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private String primerNombre;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.SEGUNDO_NOMBRE
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private String segundoNombre;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.PRIMER_APELLIDO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private String primerApellido;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.SEGUNDO_APELLIDO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private String segundoApellido;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.FECHA_NACIMIENTO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Date fechaNacimiento;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.COD_GENERO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Integer codGenero;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.AUD_FECHA_ACTUALIZACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Date audFechaActualizacion;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.AUD_COD_USUARIO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private BigDecimal audCodUsuario;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.AUD_COD_ROL
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Integer audCodRol;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.AUD_ACCION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Integer audAccion;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.FLG_ACTIVO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Short flgActivo;
   	/**
   	 * This field was generated by MyBatis Generator. This field corresponds to the database column PERSONA_PARENTESCO.FLG_SOCIEDAD_CONYUGAL_ACTIVA
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	private Short flgSociedadConyugalActiva;

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.COD_PERSONA_PARENTESCO
   	 * @return  the value of PERSONA_PARENTESCO.COD_PERSONA_PARENTESCO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Integer getCodPersonaParentesco() {
   		return codPersonaParentesco;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.COD_PERSONA_PARENTESCO
   	 * @param codPersonaParentesco  the value for PERSONA_PARENTESCO.COD_PERSONA_PARENTESCO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setCodPersonaParentesco(Integer codPersonaParentesco) {
   		this.codPersonaParentesco = codPersonaParentesco;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.COD_DECLARACION
   	 * @return  the value of PERSONA_PARENTESCO.COD_DECLARACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Long getCodDeclaracion() {
   		return codDeclaracion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.COD_DECLARACION
   	 * @param codDeclaracion  the value for PERSONA_PARENTESCO.COD_DECLARACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setCodDeclaracion(Long codDeclaracion) {
   		this.codDeclaracion = codDeclaracion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.COD_PERSONA
   	 * @return  the value of PERSONA_PARENTESCO.COD_PERSONA
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public BigDecimal getCodPersona() {
   		return codPersona;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.COD_PERSONA
   	 * @param codPersona  the value for PERSONA_PARENTESCO.COD_PERSONA
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setCodPersona(BigDecimal codPersona) {
   		this.codPersona = codPersona;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.COD_TIPO_PARENTESCO
   	 * @return  the value of PERSONA_PARENTESCO.COD_TIPO_PARENTESCO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Integer getCodTipoParentesco() {
   		return codTipoParentesco;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.COD_TIPO_PARENTESCO
   	 * @param codTipoParentesco  the value for PERSONA_PARENTESCO.COD_TIPO_PARENTESCO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setCodTipoParentesco(Integer codTipoParentesco) {
   		this.codTipoParentesco = codTipoParentesco;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.COD_TIPO_IDENTIFICACION
   	 * @return  the value of PERSONA_PARENTESCO.COD_TIPO_IDENTIFICACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Integer getCodTipoIdentificacion() {
   		return codTipoIdentificacion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.COD_TIPO_IDENTIFICACION
   	 * @param codTipoIdentificacion  the value for PERSONA_PARENTESCO.COD_TIPO_IDENTIFICACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setCodTipoIdentificacion(Integer codTipoIdentificacion) {
   		this.codTipoIdentificacion = codTipoIdentificacion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.NUM_IDENTIFICACION
   	 * @return  the value of PERSONA_PARENTESCO.NUM_IDENTIFICACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public String getNumIdentificacion() {
   		return numIdentificacion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.NUM_IDENTIFICACION
   	 * @param numIdentificacion  the value for PERSONA_PARENTESCO.NUM_IDENTIFICACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setNumIdentificacion(String numIdentificacion) {
   		this.numIdentificacion = numIdentificacion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.PRIMER_NOMBRE
   	 * @return  the value of PERSONA_PARENTESCO.PRIMER_NOMBRE
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public String getPrimerNombre() {
   		return primerNombre;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.PRIMER_NOMBRE
   	 * @param primerNombre  the value for PERSONA_PARENTESCO.PRIMER_NOMBRE
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setPrimerNombre(String primerNombre) {
   		this.primerNombre = primerNombre;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.SEGUNDO_NOMBRE
   	 * @return  the value of PERSONA_PARENTESCO.SEGUNDO_NOMBRE
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public String getSegundoNombre() {
   		return segundoNombre;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.SEGUNDO_NOMBRE
   	 * @param segundoNombre  the value for PERSONA_PARENTESCO.SEGUNDO_NOMBRE
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setSegundoNombre(String segundoNombre) {
   		this.segundoNombre = segundoNombre;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.PRIMER_APELLIDO
   	 * @return  the value of PERSONA_PARENTESCO.PRIMER_APELLIDO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public String getPrimerApellido() {
   		return primerApellido;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.PRIMER_APELLIDO
   	 * @param primerApellido  the value for PERSONA_PARENTESCO.PRIMER_APELLIDO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setPrimerApellido(String primerApellido) {
   		this.primerApellido = primerApellido;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.SEGUNDO_APELLIDO
   	 * @return  the value of PERSONA_PARENTESCO.SEGUNDO_APELLIDO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public String getSegundoApellido() {
   		return segundoApellido;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.SEGUNDO_APELLIDO
   	 * @param segundoApellido  the value for PERSONA_PARENTESCO.SEGUNDO_APELLIDO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setSegundoApellido(String segundoApellido) {
   		this.segundoApellido = segundoApellido;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.FECHA_NACIMIENTO
   	 * @return  the value of PERSONA_PARENTESCO.FECHA_NACIMIENTO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Date getFechaNacimiento() {
   		return fechaNacimiento;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.FECHA_NACIMIENTO
   	 * @param fechaNacimiento  the value for PERSONA_PARENTESCO.FECHA_NACIMIENTO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setFechaNacimiento(Date fechaNacimiento) {
   		this.fechaNacimiento = fechaNacimiento;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.COD_GENERO
   	 * @return  the value of PERSONA_PARENTESCO.COD_GENERO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Integer getCodGenero() {
   		return codGenero;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.COD_GENERO
   	 * @param codGenero  the value for PERSONA_PARENTESCO.COD_GENERO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setCodGenero(Integer codGenero) {
   		this.codGenero = codGenero;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.AUD_FECHA_ACTUALIZACION
   	 * @return  the value of PERSONA_PARENTESCO.AUD_FECHA_ACTUALIZACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Date getAudFechaActualizacion() {
   		return audFechaActualizacion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.AUD_FECHA_ACTUALIZACION
   	 * @param audFechaActualizacion  the value for PERSONA_PARENTESCO.AUD_FECHA_ACTUALIZACION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setAudFechaActualizacion(Date audFechaActualizacion) {
   		this.audFechaActualizacion = audFechaActualizacion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.AUD_COD_USUARIO
   	 * @return  the value of PERSONA_PARENTESCO.AUD_COD_USUARIO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public BigDecimal getAudCodUsuario() {
   		return audCodUsuario;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.AUD_COD_USUARIO
   	 * @param audCodUsuario  the value for PERSONA_PARENTESCO.AUD_COD_USUARIO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setAudCodUsuario(BigDecimal audCodUsuario) {
   		this.audCodUsuario = audCodUsuario;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.AUD_COD_ROL
   	 * @return  the value of PERSONA_PARENTESCO.AUD_COD_ROL
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Integer getAudCodRol() {
   		return audCodRol;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.AUD_COD_ROL
   	 * @param audCodRol  the value for PERSONA_PARENTESCO.AUD_COD_ROL
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setAudCodRol(Integer audCodRol) {
   		this.audCodRol = audCodRol;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.AUD_ACCION
   	 * @return  the value of PERSONA_PARENTESCO.AUD_ACCION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Integer getAudAccion() {
   		return audAccion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.AUD_ACCION
   	 * @param audAccion  the value for PERSONA_PARENTESCO.AUD_ACCION
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setAudAccion(Integer audAccion) {
   		this.audAccion = audAccion;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.FLG_ACTIVO
   	 * @return  the value of PERSONA_PARENTESCO.FLG_ACTIVO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Short getFlgActivo() {
   		return flgActivo;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.FLG_ACTIVO
   	 * @param flgActivo  the value for PERSONA_PARENTESCO.FLG_ACTIVO
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setFlgActivo(Short flgActivo) {
   		this.flgActivo = flgActivo;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method returns the value of the database column PERSONA_PARENTESCO.FLG_SOCIEDAD_CONYUGAL_ACTIVA
   	 * @return  the value of PERSONA_PARENTESCO.FLG_SOCIEDAD_CONYUGAL_ACTIVA
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public Short getFlgSociedadConyugalActiva() {
   		return flgSociedadConyugalActiva;
   	}

   	/**
   	 * This method was generated by MyBatis Generator. This method sets the value of the database column PERSONA_PARENTESCO.FLG_SOCIEDAD_CONYUGAL_ACTIVA
   	 * @param flgSociedadConyugalActiva  the value for PERSONA_PARENTESCO.FLG_SOCIEDAD_CONYUGAL_ACTIVA
   	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
   	 */
   	public void setFlgSociedadConyugalActiva(Short flgSociedadConyugalActiva) {
   		this.flgSociedadConyugalActiva = flgSociedadConyugalActiva;
   	}

   	/**
        * 
        */
       private int limitIni;
       /**
        * 
        */
       private int limitFin;

       /**
        * @return the limitIni
        */
       public int getLimitIni() {
   	return limitIni;
       }

       /**
        * @param limitIni the limitIni to set
        */
       public void setLimitIni(int limitIni) {
   	this.limitIni = limitIni;
       }

       /**
        * @return the limitFin
        */
       public int getLimitFin() {
   	return limitFin;
       }

       /**
        * @param limitFin the limitFin to set
        */
       public void setLimitFin(int limitFin) {
   	this.limitFin = limitFin;
       }

}