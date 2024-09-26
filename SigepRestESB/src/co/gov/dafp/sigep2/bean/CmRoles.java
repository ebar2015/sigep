package co.gov.dafp.sigep2.bean;

import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class CmRoles extends ErrorMensajes{
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.COD_CM_ROLES
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private Long codCmRoles;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.COD_TIPO_IDENTIFICACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String codTipoIdentificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.NUMERO_IDENTIFICACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String numeroIdentificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.PRIMER_NOMBRE
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String primerNombre;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.PRIMER_APELLIDO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String primerApellido;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.ROL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String rol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.ASIGNAR_DESASIGNAR
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String asignarDesasignar;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.AUD_COD_USUARIO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private Long audCodUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.AUD_COD_ROL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.AUD_ACCION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private Integer audAccion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.COD_PROCESO_CARGA_MASIVA
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private Long codProcesoCargaMasiva;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.RESULTADO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String resultado;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.FECHA_INICIAL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String fechaInicial;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_ROLES.FECHA_FINAL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	private String fechaFinal;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.COD_CM_ROLES
	 * @return  the value of CM_ROLES.COD_CM_ROLES
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public Long getCodCmRoles() {
		return codCmRoles;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.COD_CM_ROLES
	 * @param codCmRoles  the value for CM_ROLES.COD_CM_ROLES
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setCodCmRoles(Long codCmRoles) {
		this.codCmRoles = codCmRoles;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.COD_TIPO_IDENTIFICACION
	 * @return  the value of CM_ROLES.COD_TIPO_IDENTIFICACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.COD_TIPO_IDENTIFICACION
	 * @param codTipoIdentificacion  the value for CM_ROLES.COD_TIPO_IDENTIFICACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setCodTipoIdentificacion(String codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.NUMERO_IDENTIFICACION
	 * @return  the value of CM_ROLES.NUMERO_IDENTIFICACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.NUMERO_IDENTIFICACION
	 * @param numeroIdentificacion  the value for CM_ROLES.NUMERO_IDENTIFICACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.PRIMER_NOMBRE
	 * @return  the value of CM_ROLES.PRIMER_NOMBRE
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.PRIMER_NOMBRE
	 * @param primerNombre  the value for CM_ROLES.PRIMER_NOMBRE
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.PRIMER_APELLIDO
	 * @return  the value of CM_ROLES.PRIMER_APELLIDO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.PRIMER_APELLIDO
	 * @param primerApellido  the value for CM_ROLES.PRIMER_APELLIDO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.ROL
	 * @return  the value of CM_ROLES.ROL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.ROL
	 * @param rol  the value for CM_ROLES.ROL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.ASIGNAR_DESASIGNAR
	 * @return  the value of CM_ROLES.ASIGNAR_DESASIGNAR
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getAsignarDesasignar() {
		return asignarDesasignar;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.ASIGNAR_DESASIGNAR
	 * @param asignarDesasignar  the value for CM_ROLES.ASIGNAR_DESASIGNAR
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setAsignarDesasignar(String asignarDesasignar) {
		this.asignarDesasignar = asignarDesasignar;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.AUD_FECHA_ACTUALIZACION
	 * @return  the value of CM_ROLES.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for CM_ROLES.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.AUD_COD_USUARIO
	 * @return  the value of CM_ROLES.AUD_COD_USUARIO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public Long getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for CM_ROLES.AUD_COD_USUARIO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setAudCodUsuario(Long audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.AUD_COD_ROL
	 * @return  the value of CM_ROLES.AUD_COD_ROL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.AUD_COD_ROL
	 * @param audCodRol  the value for CM_ROLES.AUD_COD_ROL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.AUD_ACCION
	 * @return  the value of CM_ROLES.AUD_ACCION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.AUD_ACCION
	 * @param audAccion  the value for CM_ROLES.AUD_ACCION
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.COD_PROCESO_CARGA_MASIVA
	 * @return  the value of CM_ROLES.COD_PROCESO_CARGA_MASIVA
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public Long getCodProcesoCargaMasiva() {
		return codProcesoCargaMasiva;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.COD_PROCESO_CARGA_MASIVA
	 * @param codProcesoCargaMasiva  the value for CM_ROLES.COD_PROCESO_CARGA_MASIVA
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setCodProcesoCargaMasiva(Long codProcesoCargaMasiva) {
		this.codProcesoCargaMasiva = codProcesoCargaMasiva;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.RESULTADO
	 * @return  the value of CM_ROLES.RESULTADO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.RESULTADO
	 * @param resultado  the value for CM_ROLES.RESULTADO
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.FECHA_INICIAL
	 * @return  the value of CM_ROLES.FECHA_INICIAL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.FECHA_INICIAL
	 * @param fechaInicial  the value for CM_ROLES.FECHA_INICIAL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_ROLES.FECHA_FINAL
	 * @return  the value of CM_ROLES.FECHA_FINAL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_ROLES.FECHA_FINAL
	 * @param fechaFinal  the value for CM_ROLES.FECHA_FINAL
	 * @mbg.generated  Tue Apr 09 13:16:37 COT 2019
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7172352954912448411L;
}