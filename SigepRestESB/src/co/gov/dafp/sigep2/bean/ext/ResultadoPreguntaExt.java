/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.bean.ResultadoPregunta;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ResultadoPreguntaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class ResultadoPreguntaExt extends ResultadoPregunta implements Serializable {

	private static final long serialVersionUID = 7634160163460801017L;
	
	private String nombrePregunta;
	private Date fechaInicioPregunta;
	private Date fechaFinPregunta;
	private String tipoIdentificacion;
	private String numeroIdentificacion;
	private long codRol;
	private int respuestasRol;
	private int totalRoles;
	private String nombreRol;
	
	private long codRangoEdad;
	private long cantidadRango;
	private long rangoTotal;
	private String nombreRango;
	
	private long codNivelJerarquico;
	private String nombreNivel;
	private String respuestaNivel;
	
	private long codSigep;
	private String nombreEntidad;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	
	private String cabezaFamilia;
	private String discapacidad;
	
	

	/**
	 * @return the codNivelJerarquico
	 */
	public long getCodNivelJerarquico() {
		return codNivelJerarquico;
	}
	/**
	 * @param codNivelJerarquico the codNivelJerarquico to set
	 */
	public void setCodNivelJerarquico(long codNivelJerarquico) {
		this.codNivelJerarquico = codNivelJerarquico;
	}
	/**
	 * @return the nombreNivel
	 */
	public String getNombreNivel() {
		return nombreNivel;
	}
	/**
	 * @param nombreNivel the nombreNivel to set
	 */
	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}
	/**
	 * @return the respuestaNivel
	 */
	public String getRespuestaNivel() {
		return respuestaNivel;
	}
	/**
	 * @param respuestaNivel the respuestaNivel to set
	 */
	public void setRespuestaNivel(String respuestaNivel) {
		this.respuestaNivel = respuestaNivel;
	}
	/**
	 * @return the nombrePregunta
	 */
	public String getNombrePregunta() {
		return nombrePregunta;
	}
	/**
	 * @param nombrePregunta the nombrePregunta to set
	 */
	public void setNombrePregunta(String nombrePregunta) {
		this.nombrePregunta = nombrePregunta;
	}
	/**
	 * @return the fechaInicioPregunta
	 */
	public Date getFechaInicioPregunta() {
		return fechaInicioPregunta;
	}
	/**
	 * @param fechaInicioPregunta the fechaInicioPregunta to set
	 */
	public void setFechaInicioPregunta(Date fechaInicioPregunta) {
		this.fechaInicioPregunta = fechaInicioPregunta;
	}
	/**
	 * @return the fechaFinPregunta
	 */
	public Date getFechaFinPregunta() {
		return fechaFinPregunta;
	}
	/**
	 * @param fechaFinPregunta the fechaFinPregunta to set
	 */
	public void setFechaFinPregunta(Date fechaFinPregunta) {
		this.fechaFinPregunta = fechaFinPregunta;
	}
	/**
	 * @return the tipoIdentificacion
	 */
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	/**
	 * @return the codRol
	 */
	public long getCodRol() {
		return codRol;
	}
	/**
	 * @param codRol the codRol to set
	 */
	public void setCodRol(long codRol) {
		this.codRol = codRol;
	}
	/**
	 * @return the respuestasRol
	 */
	public int getRespuestasRol() {
		return respuestasRol;
	}
	/**
	 * @param respuestasRol the respuestasRol to set
	 */
	public void setRespuestasRol(int respuestasRol) {
		this.respuestasRol = respuestasRol;
	}
	/**
	 * @return the totalRoles
	 */
	public int getTotalRoles() {
		return totalRoles;
	}
	/**
	 * @param totalRoles the totalRoles to set
	 */
	public void setTotalRoles(int totalRoles) {
		this.totalRoles = totalRoles;
	}
	/**
	 * @return the nombreRol
	 */
	public String getNombreRol() {
		return nombreRol;
	}
	/**
	 * @param nombreRol the nombreRol to set
	 */
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	/**
	 * @return the codRangoEdad
	 */
	public long getCodRangoEdad() {
		return codRangoEdad;
	}
	/**
	 * @param codRangoEdad the codRangoEdad to set
	 */
	public void setCodRangoEdad(long codRangoEdad) {
		this.codRangoEdad = codRangoEdad;
	}
	/**
	 * @return the cantidadRango
	 */
	public long getCantidadRango() {
		return cantidadRango;
	}
	/**
	 * @param cantidadRango the cantidadRango to set
	 */
	public void setCantidadRango(long cantidadRango) {
		this.cantidadRango = cantidadRango;
	}
	/**
	 * @return the nombreRango
	 */
	public String getNombreRango() {
		return nombreRango;
	}
	/**
	 * @param nombreRango the nombreRango to set
	 */
	public void setNombreRango(String nombreRango) {
		this.nombreRango = nombreRango;
	}
	/**
	 * @return the rangoTotal
	 */
	public long getRangoTotal() {
		return rangoTotal;
	}
	/**
	 * @param rangoTotal the rangoTotal to set
	 */
	public void setRangoTotal(long rangoTotal) {
		this.rangoTotal = rangoTotal;
	}
	/**
	 * @return the codSigep
	 */
	public long getCodSigep() {
		return codSigep;
	}
	/**
	 * @param codSigep the codSigep to set
	 */
	public void setCodSigep(long codSigep) {
		this.codSigep = codSigep;
	}
	/**
	 * @return the nombreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	/**
	 * @param nombreEntidad the nombreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}
	/**
	 * @param primerNombre the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}
	/**
	 * @param segundoNombre the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}
	/**
	 * @param primerApellido the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}
	/**
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	/**
	 * @return the cabezaFamilia
	 */
	public String getCabezaFamilia() {
		return cabezaFamilia;
	}
	/**
	 * @param cabezaFamilia the cabezaFamilia to set
	 */
	public void setCabezaFamilia(String cabezaFamilia) {
		this.cabezaFamilia = cabezaFamilia;
	}
	/**
	 * @return the discapacidad
	 */
	public String getDiscapacidad() {
		return discapacidad;
	}
	/**
	 * @param discapacidad the discapacidad to set
	 */
	public void setDiscapacidad(String discapacidad) {
		this.discapacidad = discapacidad;
	}
	
}
