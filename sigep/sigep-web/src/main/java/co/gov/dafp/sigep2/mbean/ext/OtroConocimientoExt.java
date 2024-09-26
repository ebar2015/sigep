/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.OtroConocimiento;
import co.gov.dafp.sigep2.util.DateUtils;

/**
 * @author joseviscaya
 *
 */
public class OtroConocimientoExt extends OtroConocimiento implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6775898920525434399L;

    private String paisNombre, modalidadNombre, medioCapacitacionNombre, fechaFinalizacionString;

    /**
     * @return the paisNombre
     */
    public String getPaisNombre() {
	return paisNombre;
    }

    /**
     * @param paisNombre the paisNombre to set
     */
    public void setPaisNombre(String paisNombre) {
	this.paisNombre = paisNombre;
    }

    /**
     * @return the modalidadNombre
     */
    public String getModalidadNombre() {
	return modalidadNombre;
    }

    /**
     * @param modalidadNombre the modalidadNombre to set
     */
    public void setModalidadNombre(String modalidadNombre) {
	this.modalidadNombre = modalidadNombre;
    }

    /**
     * @return the medioCapacitacionNombre
     */
    public String getMedioCapacitacionNombre() {
	return medioCapacitacionNombre;
    }

    /**
     * @param medioCapacitacionNombre the medioCapacitacionNombre to set
     */
    public void setMedioCapacitacionNombre(String medioCapacitacionNombre) {
	this.medioCapacitacionNombre = medioCapacitacionNombre;
    }

    public String getFechaFinalizacionString() {
	fechaFinalizacionString = DateUtils.formatearACadena(getFechaFinalizacion(), "d 'de' MMMM 'del' yyyy");
	return fechaFinalizacionString;
    }

    public void setFechaFinalizacionString(String fechaFinalizacionString) {
	this.fechaFinalizacionString = fechaFinalizacionString;
    }
}