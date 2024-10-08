package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class Nomenclatura extends ErrorMensajes{

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.COD_NOMENCLATURA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Long codNomenclatura;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.COD_ENTIDAD
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private BigDecimal codEntidad;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.NOMBRE_NOMENCLATURA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private String nombreNomenclatura;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.COD_RAMA_ORGANIZACIONAL
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Long codRamaOrganizacional;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.COD_SISTEMA_CARRERA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Long codSistemaCarrera;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.COD_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Long codNorma;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.NUM_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private String numNorma;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.FECHA_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Date fechaNorma;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.JUSTIFICACION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private String justificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.FLG_ACTIVO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Short flgActivo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.FLG_GENERICA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Short flgGenerica;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.FLG_DEFINITIVO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Short flgDefinitivo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.COD_NOMENCLATURA_ASOCIADA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Long codNomenclaturaAsociada;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.AUD_COD_USUARIO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private BigDecimal audCodUsuario;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.AUD_COD_ROL
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.AUD_ACCION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private Integer audAccion;
	
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column NOMENCLATURA.CODIGO_NOMENCLATURA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	private String codigoNomenclatura;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.COD_NOMENCLATURA
	 * @return  the value of NOMENCLATURA.COD_NOMENCLATURA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Long getCodNomenclatura() {
		return codNomenclatura;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.COD_NOMENCLATURA
	 * @param codNomenclatura  the value for NOMENCLATURA.COD_NOMENCLATURA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setCodNomenclatura(Long codNomenclatura) {
		this.codNomenclatura = codNomenclatura;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.COD_ENTIDAD
	 * @return  the value of NOMENCLATURA.COD_ENTIDAD
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public BigDecimal getCodEntidad() {
		return codEntidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.COD_ENTIDAD
	 * @param codEntidad  the value for NOMENCLATURA.COD_ENTIDAD
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setCodEntidad(BigDecimal codEntidad) {
		this.codEntidad = codEntidad;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.NOMBRE_NOMENCLATURA
	 * @return  the value of NOMENCLATURA.NOMBRE_NOMENCLATURA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public String getNombreNomenclatura() {
		return nombreNomenclatura;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.NOMBRE_NOMENCLATURA
	 * @param nombreNomenclatura  the value for NOMENCLATURA.NOMBRE_NOMENCLATURA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setNombreNomenclatura(String nombreNomenclatura) {
		this.nombreNomenclatura = nombreNomenclatura;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.COD_RAMA_ORGANIZACIONAL
	 * @return  the value of NOMENCLATURA.COD_RAMA_ORGANIZACIONAL
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Long getCodRamaOrganizacional() {
		return codRamaOrganizacional;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.COD_RAMA_ORGANIZACIONAL
	 * @param codRamaOrganizacional  the value for NOMENCLATURA.COD_RAMA_ORGANIZACIONAL
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setCodRamaOrganizacional(Long codRamaOrganizacional) {
		this.codRamaOrganizacional = codRamaOrganizacional;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.COD_SISTEMA_CARRERA
	 * @return  the value of NOMENCLATURA.COD_SISTEMA_CARRERA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Long getCodSistemaCarrera() {
		return codSistemaCarrera;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.COD_SISTEMA_CARRERA
	 * @param codSistemaCarrera  the value for NOMENCLATURA.COD_SISTEMA_CARRERA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setCodSistemaCarrera(Long codSistemaCarrera) {
		this.codSistemaCarrera = codSistemaCarrera;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.COD_NORMA
	 * @return  the value of NOMENCLATURA.COD_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Long getCodNorma() {
		return codNorma;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.COD_NORMA
	 * @param codNorma  the value for NOMENCLATURA.COD_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setCodNorma(Long codNorma) {
		this.codNorma = codNorma;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.NUM_NORMA
	 * @return  the value of NOMENCLATURA.NUM_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public String getNumNorma() {
		return numNorma;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.NUM_NORMA
	 * @param numNorma  the value for NOMENCLATURA.NUM_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setNumNorma(String numNorma) {
		this.numNorma = numNorma;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.FECHA_NORMA
	 * @return  the value of NOMENCLATURA.FECHA_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Date getFechaNorma() {
		return fechaNorma;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.FECHA_NORMA
	 * @param fechaNorma  the value for NOMENCLATURA.FECHA_NORMA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setFechaNorma(Date fechaNorma) {
		this.fechaNorma = fechaNorma;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.JUSTIFICACION
	 * @return  the value of NOMENCLATURA.JUSTIFICACION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.JUSTIFICACION
	 * @param justificacion  the value for NOMENCLATURA.JUSTIFICACION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.FLG_ACTIVO
	 * @return  the value of NOMENCLATURA.FLG_ACTIVO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.FLG_ACTIVO
	 * @param flgActivo  the value for NOMENCLATURA.FLG_ACTIVO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.FLG_GENERICA
	 * @return  the value of NOMENCLATURA.FLG_GENERICA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Short getFlgGenerica() {
		return flgGenerica;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.FLG_GENERICA
	 * @param flgGenerica  the value for NOMENCLATURA.FLG_GENERICA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setFlgGenerica(Short flgGenerica) {
		this.flgGenerica = flgGenerica;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.FLG_DEFINITIVO
	 * @return  the value of NOMENCLATURA.FLG_DEFINITIVO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Short getFlgDefinitivo() {
		return flgDefinitivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.FLG_DEFINITIVO
	 * @param flgDefinitivo  the value for NOMENCLATURA.FLG_DEFINITIVO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setFlgDefinitivo(Short flgDefinitivo) {
		this.flgDefinitivo = flgDefinitivo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.COD_NOMENCLATURA_ASOCIADA
	 * @return  the value of NOMENCLATURA.COD_NOMENCLATURA_ASOCIADA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Long getCodNomenclaturaAsociada() {
		return codNomenclaturaAsociada;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.COD_NOMENCLATURA_ASOCIADA
	 * @param codNomenclaturaAsociada  the value for NOMENCLATURA.COD_NOMENCLATURA_ASOCIADA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setCodNomenclaturaAsociada(Long codNomenclaturaAsociada) {
		this.codNomenclaturaAsociada = codNomenclaturaAsociada;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.AUD_FECHA_ACTUALIZACION
	 * @return  the value of NOMENCLATURA.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for NOMENCLATURA.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.AUD_COD_USUARIO
	 * @return  the value of NOMENCLATURA.AUD_COD_USUARIO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public BigDecimal getAudCodUsuario() {
		return audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.AUD_COD_USUARIO
	 * @param audCodUsuario  the value for NOMENCLATURA.AUD_COD_USUARIO
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setAudCodUsuario(BigDecimal audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.AUD_COD_ROL
	 * @return  the value of NOMENCLATURA.AUD_COD_ROL
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.AUD_COD_ROL
	 * @param audCodRol  the value for NOMENCLATURA.AUD_COD_ROL
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.AUD_ACCION
	 * @return  the value of NOMENCLATURA.AUD_ACCION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.AUD_ACCION
	 * @param audAccion  the value for NOMENCLATURA.AUD_ACCION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column NOMENCLATURA.CODIGO_NOMENCLATURA
	 * @return  the value of NOMENCLATURA.AUD_ACCION
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */

	public final String getCodigoNomenclatura() {
		return codigoNomenclatura;
	}
	
	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column NOMENCLATURA.CODIGO_NOMENCLATURA
	 * @param audAccion  the value for NOMENCLATURA.CODIGO_NOMENCLATURA
	 * @mbg.generated  Mon Dec 10 09:44:09 COT 2018
	 */
	
	public final void setCodigoNomenclatura(String codigoNomenclatura) {
		this.codigoNomenclatura = codigoNomenclatura;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = -1384120381089792839L;
}