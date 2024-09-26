/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.bean.Rol;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  RolExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class RolExt extends Rol implements Serializable {

	private static final long serialVersionUID = -535074687530692938L;
	
	private Long codUsuario;
	private Long codPersona;
	private Long codEntidad;
	private Long codUsuarioRolEntidad;
	private Long codUsuarioRolDependencia;
	private BigDecimal codDependenciaEntidad;
	private String tipoIdentificacion;
	private String numeroIdentificacion;
	private String nombreEntidad;
	private String nombreDependencia;
	private Date fechaInicio;
	private Date fechaFin;
	private String codigoSigep;
	private String ocultarRolBase;
	private String fieldName;
	private String ascDesc;
	
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	/**
	 * @return the nombreDependencia
	 */
	public String getNombreDependencia() {
		return nombreDependencia;
	}
	/**
	 * @param nombreDependencia the nombreDependencia to set
	 */
	public void setNombreDependencia(String nombreDependencia) {
		this.nombreDependencia = nombreDependencia;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the codUsuario
	 */
	public Long getCodUsuario() {
		return codUsuario;
	}
	/**
	 * @param codUsuario the codUsuario to set
	 */
	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}
	/**
	 * @return the codPersona
	 */
	public Long getCodPersona() {
		return codPersona;
	}
	/**
	 * @param codPersona the codPersona to set
	 */
	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}
	/**
	 * @return the codEntidad
	 */
	public Long getCodEntidad() {
		return codEntidad;
	}
	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}
	/**
	 * @return the codUsuarioRolEntidad
	 */
	public Long getCodUsuarioRolEntidad() {
		return codUsuarioRolEntidad;
	}
	/**
	 * @param codUsuarioRolEntidad the codUsuarioRolEntidad to set
	 */
	public void setCodUsuarioRolEntidad(Long codUsuarioRolEntidad) {
		this.codUsuarioRolEntidad = codUsuarioRolEntidad;
	}
	/**
	 * @return the codUsuarioRolDependencia
	 */
	public Long getCodUsuarioRolDependencia() {
		return codUsuarioRolDependencia;
	}
	/**
	 * @param codUsuarioRolDependencia the codUsuarioRolDependencia to set
	 */
	public void setCodUsuarioRolDependencia(Long codUsuarioRolDependencia) {
		this.codUsuarioRolDependencia = codUsuarioRolDependencia;
	}
	/**
	 * @return the codDependenciaEntidad
	 */
	public BigDecimal getCodDependenciaEntidad() {
		return codDependenciaEntidad;
	}
	/**
	 * @param codDependenciaEntidad the codDependenciaEntidad to set
	 */
	public void setCodDependenciaEntidad(BigDecimal codDependenciaEntidad) {
		this.codDependenciaEntidad = codDependenciaEntidad;
	}
	/**
	 * @return the codigoSigep
	 */
	public String getCodigoSigep() {
		return codigoSigep;
	}
	/**
	 * @param codigoSigep the codigoSigep to set
	 */
	public void setCodigoSigep(String codigoSigep) {
		this.codigoSigep = codigoSigep;
	}
	public String getOcultarRolBase() {
		return ocultarRolBase;
	}
	public void setOcultarRolBase(String ocultarRolBase) {
		this.ocultarRolBase = ocultarRolBase;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the ascDesc
	 */
	public String getAscDesc() {
		return ascDesc;
	}
	/**
	 * @param ascDesc the ascDesc to set
	 */
	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}
}
