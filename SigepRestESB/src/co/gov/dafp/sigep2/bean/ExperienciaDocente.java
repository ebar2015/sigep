package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class ExperienciaDocente extends ErrorMensajes{
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_EXPERIENCIA_DOCENTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Long codExperienciaDocente;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_PERSONA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Long codPersona;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_PAIS
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Integer codPais;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_DEPARTAMENTO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Integer codDepartamento;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_CIUDAD
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Integer codCiudad;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_NIVEL_EDUCATIVO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Integer codNivelEducativo;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_AREA_CONOCIMIENTO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Long codAreaConocimiento;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_INSTITUCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Integer codInstitucion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.FECHA_INGRESO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Date fechaIngreso;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.FLG_ACTUALMENTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Short flgActualmente;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.FECHA_FINALIZACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Date fechaFinalizacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.DIRECCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private String direccion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.TELEFONO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private String telefono;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.HORAS_SEMANA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Short horasSemana;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.MATERIA_IMPARTIDA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private String materiaImpartida;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.URL_DOCUMENTO_SOPORTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private String urlDocumentoSoporte;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.FLG_VERIFICADO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Short flgVerificado;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_USUARIO_VERIFICA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private BigDecimal codUsuarioVerifica;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.FECHA_VERIFICACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Date fechaVerificacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Date audFechaActualizacion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.AUD_COD_USUARIO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private BigDecimal audCodUsuario;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.AUD_COD_ROL
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Integer audCodRol;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.AUD_ACCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Integer audAccion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.FLG_ENTIDAD_PUBLICA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Short flgEntidadPublica;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.NOMBRE_INSTITUCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private String nombreInstitucion;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.INDICATIVO_TELEFONO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private String indicativoTelefono;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.TIEMPO_EXPERIENCIA_DOCENTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.FLG_ACTIVO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Short flgActivo;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column EXPERIENCIA_DOCENTE.COD_TIPO_ZONA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	private Integer codTipoZona;

	private Integer codJornadaLaboral;
	
	private Integer codMotivoRetiro;
	
	private Integer horasPromedioMes;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_EXPERIENCIA_DOCENTE
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_EXPERIENCIA_DOCENTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Long getCodExperienciaDocente() {
		return codExperienciaDocente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_EXPERIENCIA_DOCENTE
	 * @param codExperienciaDocente  the value for EXPERIENCIA_DOCENTE.COD_EXPERIENCIA_DOCENTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodExperienciaDocente(Long codExperienciaDocente) {
		this.codExperienciaDocente = codExperienciaDocente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_PERSONA
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_PERSONA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Long getCodPersona() {
		return codPersona;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_PERSONA
	 * @param codPersona  the value for EXPERIENCIA_DOCENTE.COD_PERSONA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_PAIS
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_PAIS
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Integer getCodPais() {
		return codPais;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_PAIS
	 * @param codPais  the value for EXPERIENCIA_DOCENTE.COD_PAIS
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodPais(Integer codPais) {
		this.codPais = codPais;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_DEPARTAMENTO
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_DEPARTAMENTO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Integer getCodDepartamento() {
		return codDepartamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_DEPARTAMENTO
	 * @param codDepartamento  the value for EXPERIENCIA_DOCENTE.COD_DEPARTAMENTO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodDepartamento(Integer codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_CIUDAD
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_CIUDAD
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Integer getCodCiudad() {
		return codCiudad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_CIUDAD
	 * @param codCiudad  the value for EXPERIENCIA_DOCENTE.COD_CIUDAD
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodCiudad(Integer codCiudad) {
		this.codCiudad = codCiudad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_NIVEL_EDUCATIVO
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_NIVEL_EDUCATIVO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Integer getCodNivelEducativo() {
		return codNivelEducativo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_NIVEL_EDUCATIVO
	 * @param codNivelEducativo  the value for EXPERIENCIA_DOCENTE.COD_NIVEL_EDUCATIVO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodNivelEducativo(Integer codNivelEducativo) {
		this.codNivelEducativo = codNivelEducativo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_AREA_CONOCIMIENTO
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_AREA_CONOCIMIENTO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Long getCodAreaConocimiento() {
		return codAreaConocimiento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_AREA_CONOCIMIENTO
	 * @param codAreaConocimiento  the value for EXPERIENCIA_DOCENTE.COD_AREA_CONOCIMIENTO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodAreaConocimiento(Long codAreaConocimiento) {
		this.codAreaConocimiento = codAreaConocimiento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_INSTITUCION
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_INSTITUCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Integer getCodInstitucion() {
		return codInstitucion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_INSTITUCION
	 * @param codInstitucion  the value for EXPERIENCIA_DOCENTE.COD_INSTITUCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodInstitucion(Integer codInstitucion) {
		this.codInstitucion = codInstitucion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.FECHA_INGRESO
	 * @return  the value of EXPERIENCIA_DOCENTE.FECHA_INGRESO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.FECHA_INGRESO
	 * @param fechaIngreso  the value for EXPERIENCIA_DOCENTE.FECHA_INGRESO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.FLG_ACTUALMENTE
	 * @return  the value of EXPERIENCIA_DOCENTE.FLG_ACTUALMENTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Short getFlgActualmente() {
		return flgActualmente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.FLG_ACTUALMENTE
	 * @param flgActualmente  the value for EXPERIENCIA_DOCENTE.FLG_ACTUALMENTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setFlgActualmente(Short flgActualmente) {
		this.flgActualmente = flgActualmente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.FECHA_FINALIZACION
	 * @return  the value of EXPERIENCIA_DOCENTE.FECHA_FINALIZACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.FECHA_FINALIZACION
	 * @param fechaFinalizacion  the value for EXPERIENCIA_DOCENTE.FECHA_FINALIZACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.DIRECCION
	 * @return  the value of EXPERIENCIA_DOCENTE.DIRECCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.DIRECCION
	 * @param direccion  the value for EXPERIENCIA_DOCENTE.DIRECCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.TELEFONO
	 * @return  the value of EXPERIENCIA_DOCENTE.TELEFONO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.TELEFONO
	 * @param telefono  the value for EXPERIENCIA_DOCENTE.TELEFONO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.HORAS_SEMANA
	 * @return  the value of EXPERIENCIA_DOCENTE.HORAS_SEMANA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Short getHorasSemana() {
		return horasSemana;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.HORAS_SEMANA
	 * @param horasSemana  the value for EXPERIENCIA_DOCENTE.HORAS_SEMANA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setHorasSemana(Short horasSemana) {
		this.horasSemana = horasSemana;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.MATERIA_IMPARTIDA
	 * @return  the value of EXPERIENCIA_DOCENTE.MATERIA_IMPARTIDA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public String getMateriaImpartida() {
		return materiaImpartida;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.MATERIA_IMPARTIDA
	 * @param materiaImpartida  the value for EXPERIENCIA_DOCENTE.MATERIA_IMPARTIDA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setMateriaImpartida(String materiaImpartida) {
		this.materiaImpartida = materiaImpartida;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.URL_DOCUMENTO_SOPORTE
	 * @return  the value of EXPERIENCIA_DOCENTE.URL_DOCUMENTO_SOPORTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public String getUrlDocumentoSoporte() {
		return urlDocumentoSoporte;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.URL_DOCUMENTO_SOPORTE
	 * @param urlDocumentoSoporte  the value for EXPERIENCIA_DOCENTE.URL_DOCUMENTO_SOPORTE
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setUrlDocumentoSoporte(String urlDocumentoSoporte) {
		this.urlDocumentoSoporte = urlDocumentoSoporte;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.FLG_VERIFICADO
	 * @return  the value of EXPERIENCIA_DOCENTE.FLG_VERIFICADO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Short getFlgVerificado() {
		return flgVerificado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.FLG_VERIFICADO
	 * @param flgVerificado  the value for EXPERIENCIA_DOCENTE.FLG_VERIFICADO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setFlgVerificado(Short flgVerificado) {
		this.flgVerificado = flgVerificado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_USUARIO_VERIFICA
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_USUARIO_VERIFICA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public BigDecimal getCodUsuarioVerifica() {
		return codUsuarioVerifica;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_USUARIO_VERIFICA
	 * @param codUsuarioVerifica  the value for EXPERIENCIA_DOCENTE.COD_USUARIO_VERIFICA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodUsuarioVerifica(BigDecimal codUsuarioVerifica) {
		this.codUsuarioVerifica = codUsuarioVerifica;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.FECHA_VERIFICACION
	 * @return  the value of EXPERIENCIA_DOCENTE.FECHA_VERIFICACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Date getFechaVerificacion() {
		return fechaVerificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.FECHA_VERIFICACION
	 * @param fechaVerificacion  the value for EXPERIENCIA_DOCENTE.FECHA_VERIFICACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setFechaVerificacion(Date fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.AUD_FECHA_ACTUALIZACION
	 * @return  the value of EXPERIENCIA_DOCENTE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for EXPERIENCIA_DOCENTE.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.AUD_COD_USUARIO
	 * @return  the value of EXPERIENCIA_DOCENTE.AUD_COD_USUARIO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for EXPERIENCIA_DOCENTE.AUD_COD_USUARIO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.AUD_COD_ROL
	 * @return  the value of EXPERIENCIA_DOCENTE.AUD_COD_ROL
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.AUD_COD_ROL
	 * @param audCodRol  the value for EXPERIENCIA_DOCENTE.AUD_COD_ROL
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.AUD_ACCION
	 * @return  the value of EXPERIENCIA_DOCENTE.AUD_ACCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.AUD_ACCION
	 * @param audAccion  the value for EXPERIENCIA_DOCENTE.AUD_ACCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.FLG_ENTIDAD_PUBLICA
	 * @return  the value of EXPERIENCIA_DOCENTE.FLG_ENTIDAD_PUBLICA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Short getFlgEntidadPublica() {
		return flgEntidadPublica;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.FLG_ENTIDAD_PUBLICA
	 * @param flgEntidadPublica  the value for EXPERIENCIA_DOCENTE.FLG_ENTIDAD_PUBLICA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setFlgEntidadPublica(Short flgEntidadPublica) {
		this.flgEntidadPublica = flgEntidadPublica;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.NOMBRE_INSTITUCION
	 * @return  the value of EXPERIENCIA_DOCENTE.NOMBRE_INSTITUCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.NOMBRE_INSTITUCION
	 * @param nombreInstitucion  the value for EXPERIENCIA_DOCENTE.NOMBRE_INSTITUCION
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.INDICATIVO_TELEFONO
	 * @return  the value of EXPERIENCIA_DOCENTE.INDICATIVO_TELEFONO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public String getIndicativoTelefono() {
		return indicativoTelefono;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.INDICATIVO_TELEFONO
	 * @param indicativoTelefono  the value for EXPERIENCIA_DOCENTE.INDICATIVO_TELEFONO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setIndicativoTelefono(String indicativoTelefono) {
		this.indicativoTelefono = indicativoTelefono;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.FLG_ACTIVO
	 * @return  the value of EXPERIENCIA_DOCENTE.FLG_ACTIVO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.FLG_ACTIVO
	 * @param flgActivo  the value for EXPERIENCIA_DOCENTE.FLG_ACTIVO
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column EXPERIENCIA_DOCENTE.COD_TIPO_ZONA
	 * @return  the value of EXPERIENCIA_DOCENTE.COD_TIPO_ZONA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public Integer getCodTipoZona() {
		return codTipoZona;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column EXPERIENCIA_DOCENTE.COD_TIPO_ZONA
	 * @param codTipoZona  the value for EXPERIENCIA_DOCENTE.COD_TIPO_ZONA
	 * @mbg.generated  Thu Feb 22 17:21:01 COT 2018
	 */
	public void setCodTipoZona(Integer codTipoZona) {
		this.codTipoZona = codTipoZona;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6726833127065279850L;


	private Integer limitInit;
    
    
    private Integer limitEnd;
    
    
    
    /**
	 * @return the limitInit
	 */
	public Integer getLimitInit() {
		return limitInit;
	}

	/**
	 * @param limitInit the limitInit to set
	 */
	public void setLimitInit(Integer limitInit) {
		this.limitInit = limitInit;
	}
    
    
	/**
	 * @return the limitEnd
	 */
	public Integer getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd the limitEnd to set
	 */
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}

	public Integer getCodJornadaLaboral() {
		return codJornadaLaboral;
	}

	public void setCodJornadaLaboral(Integer codJornadaLaboral) {
		this.codJornadaLaboral = codJornadaLaboral;
	}

	public Integer getCodMotivoRetiro() {
		return codMotivoRetiro;
	}

	public void setCodMotivoRetiro(Integer codMotivoRetiro) {
		this.codMotivoRetiro = codMotivoRetiro;
	}

	public Integer getHorasPromedioMes() {
		return horasPromedioMes;
	}

	public void setHorasPromedioMes(Integer horasPromedioMes) {
		this.horasPromedioMes = horasPromedioMes;
	}
	
}