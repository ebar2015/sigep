/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.ParticipacionProyecto;
import co.gov.dafp.sigep2.util.DateUtils;

/**
 * @author joseviscaya
 *
 */
public class ParticipacionProyectoExt extends ParticipacionProyecto implements Serializable {

    private static final long serialVersionUID = -1025524769312911270L;

    private String nombrePais, fechaInicialString, fechaFinalString;
    private String nombreDepartamento;
    private String nombreMunicipio;

    public String getNombrePais() {
	return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
	this.nombrePais = nombrePais;
    }

    public String getNombreDepartamento() {
	return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
	this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreMunicipio() {
	return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
	this.nombreMunicipio = nombreMunicipio;
    }

    public String getFechaInicialString() {
	fechaInicialString = DateUtils.formatearACadena(getFechaInicio(), "d 'de' MMMM 'del' yyyy");
	return fechaInicialString;
    }

    public void setFechaInicialString(String fechaInicialString) {
	this.fechaInicialString = fechaInicialString;
    }

    public String getFechaFinalString() {
	fechaFinalString = DateUtils.formatearACadena(getFechaTerminacion(), "d 'de' MMMM 'del' yyyy");
	return fechaFinalString;
    }

    public void setFechaFinalString(String fechaFinalString) {
	this.fechaFinalString = fechaFinalString;
    }
}