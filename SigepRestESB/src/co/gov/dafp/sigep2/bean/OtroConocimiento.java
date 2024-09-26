package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

public class OtroConocimiento{
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.COD_OTRO_CONOCIMIENTO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private BigDecimal codOtroConocimiento;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.COD_PERSONA
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private BigDecimal codPersona;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.COD_MODALIDAD
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Integer codModalidad;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.COD_MEDIO_CAPACITACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Integer codMedioCapacitacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.COD_PAIS
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Integer codPais;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.INSTITUCION_FORMACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private String institucionFormacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.NOMBRE_CURSO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private String nombreCurso;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.FECHA_FINALIZACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Date fechaFinalizacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.TOT_HORAS
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Integer totHoras;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.URL_DOCUMENTO_SOPORTE
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private String urlDocumentoSoporte;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Date audFechaActualizacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.AUD_COD_USUARIO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private BigDecimal audCodUsuario;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.AUD_COD_ROL
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Integer audCodRol;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.AUD_ACCION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Integer audAccion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column OTRO_CONOCIMIENTO.FLG_ACTIVO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	private Short flgActivo;



	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.COD_OTRO_CONOCIMIENTO
	 * @return  the value of OTRO_CONOCIMIENTO.COD_OTRO_CONOCIMIENTO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public BigDecimal getCodOtroConocimiento() {
		return codOtroConocimiento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.COD_OTRO_CONOCIMIENTO
	 * @param codOtroConocimiento  the value for OTRO_CONOCIMIENTO.COD_OTRO_CONOCIMIENTO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setCodOtroConocimiento(BigDecimal codOtroConocimiento) {
		this.codOtroConocimiento = codOtroConocimiento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.COD_PERSONA
	 * @return  the value of OTRO_CONOCIMIENTO.COD_PERSONA
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public BigDecimal getCodPersona() {
		return codPersona;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.COD_PERSONA
	 * @param codPersona  the value for OTRO_CONOCIMIENTO.COD_PERSONA
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setCodPersona(BigDecimal codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.COD_MODALIDAD
	 * @return  the value of OTRO_CONOCIMIENTO.COD_MODALIDAD
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Integer getCodModalidad() {
		return codModalidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.COD_MODALIDAD
	 * @param codModalidad  the value for OTRO_CONOCIMIENTO.COD_MODALIDAD
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setCodModalidad(Integer codModalidad) {
		this.codModalidad = codModalidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.COD_MEDIO_CAPACITACION
	 * @return  the value of OTRO_CONOCIMIENTO.COD_MEDIO_CAPACITACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Integer getCodMedioCapacitacion() {
		return codMedioCapacitacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.COD_MEDIO_CAPACITACION
	 * @param codMedioCapacitacion  the value for OTRO_CONOCIMIENTO.COD_MEDIO_CAPACITACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setCodMedioCapacitacion(Integer codMedioCapacitacion) {
		this.codMedioCapacitacion = codMedioCapacitacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.COD_PAIS
	 * @return  the value of OTRO_CONOCIMIENTO.COD_PAIS
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Integer getCodPais() {
		return codPais;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.COD_PAIS
	 * @param codPais  the value for OTRO_CONOCIMIENTO.COD_PAIS
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setCodPais(Integer codPais) {
		this.codPais = codPais;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.INSTITUCION_FORMACION
	 * @return  the value of OTRO_CONOCIMIENTO.INSTITUCION_FORMACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public String getInstitucionFormacion() {
		return institucionFormacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.INSTITUCION_FORMACION
	 * @param institucionFormacion  the value for OTRO_CONOCIMIENTO.INSTITUCION_FORMACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setInstitucionFormacion(String institucionFormacion) {
		this.institucionFormacion = institucionFormacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.NOMBRE_CURSO
	 * @return  the value of OTRO_CONOCIMIENTO.NOMBRE_CURSO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public String getNombreCurso() {
		return nombreCurso;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.NOMBRE_CURSO
	 * @param nombreCurso  the value for OTRO_CONOCIMIENTO.NOMBRE_CURSO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.FECHA_FINALIZACION
	 * @return  the value of OTRO_CONOCIMIENTO.FECHA_FINALIZACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.FECHA_FINALIZACION
	 * @param fechaFinalizacion  the value for OTRO_CONOCIMIENTO.FECHA_FINALIZACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.TOT_HORAS
	 * @return  the value of OTRO_CONOCIMIENTO.TOT_HORAS
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Integer getTotHoras() {
		return totHoras;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.TOT_HORAS
	 * @param totHoras  the value for OTRO_CONOCIMIENTO.TOT_HORAS
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setTotHoras(Integer totHoras) {
		this.totHoras = totHoras;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.URL_DOCUMENTO_SOPORTE
	 * @return  the value of OTRO_CONOCIMIENTO.URL_DOCUMENTO_SOPORTE
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public String getUrlDocumentoSoporte() {
		return urlDocumentoSoporte;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.URL_DOCUMENTO_SOPORTE
	 * @param urlDocumentoSoporte  the value for OTRO_CONOCIMIENTO.URL_DOCUMENTO_SOPORTE
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setUrlDocumentoSoporte(String urlDocumentoSoporte) {
		this.urlDocumentoSoporte = urlDocumentoSoporte;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.AUD_FECHA_ACTUALIZACION
	 * @return  the value of OTRO_CONOCIMIENTO.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for OTRO_CONOCIMIENTO.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.AUD_COD_USUARIO
	 * @return  the value of OTRO_CONOCIMIENTO.AUD_COD_USUARIO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for OTRO_CONOCIMIENTO.AUD_COD_USUARIO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.AUD_COD_ROL
	 * @return  the value of OTRO_CONOCIMIENTO.AUD_COD_ROL
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.AUD_COD_ROL
	 * @param audCodRol  the value for OTRO_CONOCIMIENTO.AUD_COD_ROL
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.AUD_ACCION
	 * @return  the value of OTRO_CONOCIMIENTO.AUD_ACCION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.AUD_ACCION
	 * @param audAccion  the value for OTRO_CONOCIMIENTO.AUD_ACCION
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column OTRO_CONOCIMIENTO.FLG_ACTIVO
	 * @return  the value of OTRO_CONOCIMIENTO.FLG_ACTIVO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column OTRO_CONOCIMIENTO.FLG_ACTIVO
	 * @param flgActivo  the value for OTRO_CONOCIMIENTO.FLG_ACTIVO
	 * @mbg.generated  Wed Mar 07 11:07:04 COT 2018
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6013665894577976687L;


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
}