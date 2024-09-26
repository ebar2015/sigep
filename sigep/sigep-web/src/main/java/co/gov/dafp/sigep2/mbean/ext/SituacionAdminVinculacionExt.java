/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import co.gov.dafp.sigep2.entities.SituacionAdminVinculacion;

/**
 * @author joseviscaya
 *
 */
public class SituacionAdminVinculacionExt extends SituacionAdminVinculacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6231514809790189894L;

	
	private String nombreSituacion;
	
	private String fechaInicial;
	private String fechaFinal;
	private String codCodigoSigepEntidad;
	
	/**
	 * @return the nombreSituacion
	 */
	public String getNombreSituacion() {
		return nombreSituacion;
	}
	/**
	 * @param nombreSituacion the nombreSituacion to set
	 */
	public void setNombreSituacion(String nombreSituacion) {
		this.nombreSituacion = nombreSituacion;
	}

	public String getFechaInicial() {
		if(this.getFechaInicio() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.fechaInicial = sdf.format(this.getFechaInicio());
		}
		return fechaInicial;
	}
	
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	
	public String getFechaFinal() {
		if(this.getFechaFin() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.fechaFinal = sdf.format(this.getFechaFin());
		}
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	/**
	 * @return the codCodigoSigepEntidad
	 */
	public String getCodCodigoSigepEntidad() {
		return codCodigoSigepEntidad;
	}
	/**
	 * @param codCodigoSigepEntidad the codCodigoSigepEntidad to set
	 */
	public void setCodCodigoSigepEntidad(String codCodigoSigepEntidad) {
		this.codCodigoSigepEntidad = codCodigoSigepEntidad;
	}

	
}
