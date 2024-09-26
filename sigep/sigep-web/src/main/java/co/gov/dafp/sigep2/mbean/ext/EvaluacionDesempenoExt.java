/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.EvaluacionDesempeno;
import co.gov.dafp.sigep2.util.DateUtils;

/**
 * @author joseviscaya
 *
 */
public class EvaluacionDesempenoExt extends EvaluacionDesempeno implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5311044373786795548L;
    private String nombreEntidad, nombreCargo;
    private String nombreTipEevaluacion;
    private String nombreNivelCumplimiento, fechaInicioString, fechaFinalString;

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
     * @return the nombreTipEevaluacion
     */
    public String getNombreTipEevaluacion() {
	return nombreTipEevaluacion;
    }

    /**
     * @param nombreTipEevaluacion the nombreTipEevaluacion to set
     */
    public void setNombreTipEevaluacion(String nombreTipEevaluacion) {
	this.nombreTipEevaluacion = nombreTipEevaluacion;
    }

    /**
     * @return the nombreNivelCumplimiento
     */
    public String getNombreNivelCumplimiento() {
	return nombreNivelCumplimiento;
    }

    /**
     * @param nombreNivelCumplimiento the nombreNivelCumplimiento to set
     */
    public void setNombreNivelCumplimiento(String nombreNivelCumplimiento) {
	this.nombreNivelCumplimiento = nombreNivelCumplimiento;
    }

    public String getFechaInicioString() {
	fechaInicioString = DateUtils.formatearACadena(getFechaInicioEvaluacion(), "d 'de' MMMM 'del' yyyy");
	return fechaInicioString;
    }

    public void setFechaInicioString(String fechaInicioString) {
	this.fechaInicioString = fechaInicioString;
    }

    public String getFechaFinalString() {
	fechaFinalString = DateUtils.formatearACadena(getFechaFinEvaluacion(), "d 'de' MMMM 'del' yyyy");
	return fechaFinalString;
    }

    public void setFechaFinalString(String fechaFinalString) {
	this.fechaFinalString = fechaFinalString;
    }

	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}
    
    
}