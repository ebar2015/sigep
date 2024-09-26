/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.HojaVida;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  HojaVidaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class HojaVidaExt extends HojaVida implements Serializable {

	private static final long serialVersionUID = -8859347440924060964L;
	private String justificacionEliminacion;
	private String nombreEntidad;
	private String nombrePersona;
	private String nombreUsuarioAprueba;
	private String nombreModalidad;
	private String nombreCargo;
	private String nombreUtlUan;
	private String imagenHojaVidaUrl;
	private String fechaStringAprobacion;
	private String tipoAprobacion;
	private Integer tiempoAprobacion;
	private Integer diasDeAprobacion;
    private Integer diasPermitidios;
    private Integer totalContratos;
    private Integer totalPlanta;
    private Integer totalSinVerificar;
    private String nombreTipoDocumento;
    private String numeroIdentificacion;
    
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
	 * @return the nombrePersona
	 */
	public String getNombrePersona() {
		return nombrePersona;
	}


	/**
	 * @param nombrePersona the nombrePersona to set
	 */
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}


	/**
	 * @return the nombreUsuarioAprueba
	 */
	public String getNombreUsuarioAprueba() {
		return nombreUsuarioAprueba;
	}


	/**
	 * @param nombreUsuarioAprueba the nombreUsuarioAprueba to set
	 */
	public void setNombreUsuarioAprueba(String nombreUsuarioAprueba) {
		this.nombreUsuarioAprueba = nombreUsuarioAprueba;
	}


	/**
	 * @return the nombreModalidad
	 */
	public String getNombreModalidad() {
		return nombreModalidad;
	}


	/**
	 * @param nombreModalidad the nombreModalidad to set
	 */
	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}


	/**
	 * @return the nombreCargo
	 */
	public String getNombreCargo() {
		return nombreCargo;
	}


	/**
	 * @param nombreCargo the nombreCargo to set
	 */
	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}


	/**
	 * @return the nombreUtlUan
	 */
	public String getNombreUtlUan() {
		return nombreUtlUan;
	}


	/**
	 * @param nombreUtlUan the nombreUtlUan to set
	 */
	public void setNombreUtlUan(String nombreUtlUan) {
		this.nombreUtlUan = nombreUtlUan;
	}


	/**
	 * @return the justificacionEliminacion
	 */
	public String getJustificacionEliminacion() {
		return justificacionEliminacion;
	}


	/**
	 * @param justificacionEliminacion the justificacionEliminacion to set
	 */
	public void setJustificacionEliminacion(String justificacionEliminacion) {
		this.justificacionEliminacion = justificacionEliminacion;
	}


	/**
	 * @return the imagenHojaVidaUrl
	 */
	public String getImagenHojaVidaUrl() {
		return imagenHojaVidaUrl;
	}


	/**
	 * @param imagenHojaVidaUrl the imagenHojaVidaUrl to set
	 */
	public void setImagenHojaVidaUrl(String imagenHojaVidaUrl) {
		this.imagenHojaVidaUrl = imagenHojaVidaUrl;
	}


	/**
	 * @return the fechaStringAprobacion
	 */
	public String getFechaStringAprobacion() {
		return fechaStringAprobacion;
	}


	/**
	 * @param fechaStringAprobacion the fechaStringAprobacion to set
	 */
	public void setFechaStringAprobacion(String fechaStringAprobacion) {
		this.fechaStringAprobacion = fechaStringAprobacion;
	}


	/**
	 * @return the tipoAprobacion
	 */
	public String getTipoAprobacion() {
		return tipoAprobacion;
	}


	/**
	 * @param tipoAprobacion the tipoAprobacion to set
	 */
	public void setTipoAprobacion(String tipoAprobacion) {
		this.tipoAprobacion = tipoAprobacion;
	}


	/**
	 * @return the tiempoAprobacion
	 */
	public Integer getTiempoAprobacion() {
		return tiempoAprobacion;
	}


	/**
	 * @param tiempoAprobacion the tiempoAprobacion to set
	 */
	public void setTiempoAprobacion(Integer tiempoAprobacion) {
		this.tiempoAprobacion = tiempoAprobacion;
	}


	/**
	 * @return the diasDeAprobacion
	 */
	public Integer getDiasDeAprobacion() {
		return diasDeAprobacion;
	}


	/**
	 * @param diasDeAprobacion the diasDeAprobacion to set
	 */
	public void setDiasDeAprobacion(Integer diasDeAprobacion) {
		this.diasDeAprobacion = diasDeAprobacion;
	}


	/**
	 * @return the diasPermitidios
	 */
	public Integer getDiasPermitidios() {
		return diasPermitidios;
	}


	/**
	 * @param diasPermitidios the diasPermitidios to set
	 */
	public void setDiasPermitidios(Integer diasPermitidios) {
		this.diasPermitidios = diasPermitidios;
	}


	/**
	 * @return the totalContratos
	 */
	public Integer getTotalContratos() {
		return totalContratos;
	}


	/**
	 * @param totalContratos the totalContratos to set
	 */
	public void setTotalContratos(Integer totalContratos) {
		this.totalContratos = totalContratos;
	}


	/**
	 * @return the totalPlanta
	 */
	public Integer getTotalPlanta() {
		return totalPlanta;
	}


	/**
	 * @param totalPlanta the totalPlanta to set
	 */
	public void setTotalPlanta(Integer totalPlanta) {
		this.totalPlanta = totalPlanta;
	}


	/**
	 * @return the totalSinVerificar
	 */
	public Integer getTotalSinVerificar() {
		return totalSinVerificar;
	}


	/**
	 * @param totalSinVerificar the totalSinVerificar to set
	 */
	public void setTotalSinVerificar(Integer totalSinVerificar) {
		this.totalSinVerificar = totalSinVerificar;
	}


	/**
	 * @return the nombreTipoDocumento
	 */
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}


	/**
	 * @param nombreTipoDocumento the nombreTipoDocumento to set
	 */
	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
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
	
	
}
