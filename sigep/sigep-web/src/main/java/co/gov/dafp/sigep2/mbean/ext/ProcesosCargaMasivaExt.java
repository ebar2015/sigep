package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.entities.ProcesosCargaMasiva;

public class ProcesosCargaMasivaExt extends ProcesosCargaMasiva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String 	nombreEntidad;
	private String 	nombreTipoCargue;
	private Long 	codTipoIdentificacion;
	private String 	tipoDocumento;
	private String 	numeroIdentificacion;
	private String 	nombreCompleto;
	private String 	primerNombre;
	private String 	segundoNombre;
	private String 	primerApellido;
	private String 	segundoApellido;
	private String 	nombreEstado;
	private BigDecimal total;
	private String fieldName;
	private String ascDesc;
	
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	public String getNombreTipoCargue() {
		return nombreTipoCargue;
	}
	public void setNombreTipoCargue(String nombreTipoCargue) {
		this.nombreTipoCargue = nombreTipoCargue;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	public String getNombreEstado() {
		return nombreEstado;
	}
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	public Long getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}
	public void setCodTipoIdentificacion(Long codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
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
