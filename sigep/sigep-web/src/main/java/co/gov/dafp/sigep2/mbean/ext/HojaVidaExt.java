/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import co.gov.dafp.sigep2.entities.HojaVida;

/**
 * @author joseviscaya
 *
 */
public class HojaVidaExt extends HojaVida implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8859347440924060964L;
	
	private String justificacionEliminacion;
	private String nombreEntidad;
	private String nombrePersona;
	private String nombreUsuarioAprueba;
	private String nombreModalidad;
	private String nombreCargo;
	private String nombreUtlUan;
	private String fechaStringAprobacion;
	private String tipoAprobacion;
	private Integer tiempoAprobacion;
	private Integer totalSinVerificar;
	private String nombreTipoDocumento;
    private String numeroIdentificacion;
	    
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
	
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	
	public String getNombrePersona() {
		return nombrePersona;
	}
	
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}


	public String getNombreUsuarioAprueba() {
		return nombreUsuarioAprueba;
	}


	public void setNombreUsuarioAprueba(String nombreUsuarioAprueba) {
		this.nombreUsuarioAprueba = nombreUsuarioAprueba;
	}


	public String getNombreModalidad() {
		return nombreModalidad;
	}


	public void setNombreModalidad(String nombreModalidad) {
		this.nombreModalidad = nombreModalidad;
	}


	public String getNombreCargo() {
		return nombreCargo;
	}


	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}


	public String getNombreUtlUan() {
		return nombreUtlUan;
	}


	public void setNombreUtlUan(String nombreUtlUan) {
		this.nombreUtlUan = nombreUtlUan;
	}


	public String getFechaStringAprobacion() {
		if(this.getFechaAprobacion() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.fechaStringAprobacion = sdf.format(this.getFechaAprobacion());
		}
		
		return fechaStringAprobacion;
	}

	public String getTipoAprobacion() {
		
		if(getCodCargo() != null) {
			tipoAprobacion = getNombreCargo();
		}
		
		if(getCodUtlUan() != null) {
			tipoAprobacion = getNombreUtlUan();
		}
		
		if(getDetallesContrato() != null && !getDetallesContrato().equals("")) {
			tipoAprobacion = getDetallesContrato();
		}
		
		return tipoAprobacion;
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
	 * @param fechaStringAprobacion the fechaStringAprobacion to set
	 */
	public void setFechaStringAprobacion(String fechaStringAprobacion) {
		this.fechaStringAprobacion = fechaStringAprobacion;
	}


	/**
	 * @param tipoAprobacion the tipoAprobacion to set
	 */
	public void setTipoAprobacion(String tipoAprobacion) {
		this.tipoAprobacion = tipoAprobacion;
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
