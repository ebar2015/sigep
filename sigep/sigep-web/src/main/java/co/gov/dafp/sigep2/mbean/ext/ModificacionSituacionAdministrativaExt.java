/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import co.gov.dafp.sigep2.entities.ModificacionSituacionAdministrativa;
import co.gov.dafp.sigep2.entities.SituacionAdminVinculacion;

/**
 * @author Nestor.Riasco
 *
 */
public class ModificacionSituacionAdministrativaExt extends ModificacionSituacionAdministrativa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6231514809790189894L;

	
	private String fechaInicial;
	private String fechaFinal;
	
	public String getFechaInicial() {
		if(this.getFechaInicialProrroga() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.fechaInicial = sdf.format(this.getFechaInicialProrroga());
		}
		return fechaInicial;
	}
	
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	
	public String getFechaFinal() {
		if(this.getFechaFinProrroga() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.fechaFinal = sdf.format(this.getFechaFinProrroga());
		}
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	
}
