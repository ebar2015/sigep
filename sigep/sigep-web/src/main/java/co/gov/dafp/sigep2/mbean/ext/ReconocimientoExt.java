/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.Reconocimiento;
import co.gov.dafp.sigep2.util.DateUtils;

/**
 * @author joseviscaya
 *
 */
public class ReconocimientoExt extends Reconocimiento implements Serializable {

    private static final long serialVersionUID = -1025524769312911270L;

    private String nombrePais, fechaReconocimientoString;
    private String nombreDepartamento;
    private String nombreMunicipio;
    private boolean banderaEsPremio;

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

    public String getFechaReconocimientoString() {
	fechaReconocimientoString = DateUtils.formatearACadena(getFechaReconocimiento(), "d 'de' MMMM 'del' yyyy");
	return fechaReconocimientoString;
    }

    public void setFechaReconocimientoString(String fechaReconocimientoString) {
	this.fechaReconocimientoString = fechaReconocimientoString;
    }

	public boolean isBanderaEsPremio() {
		return banderaEsPremio;
	}

	public void setBanderaEsPremio(boolean banderaEsPremio) {
		this.banderaEsPremio = banderaEsPremio;
	}
	
	
	
	
    
    
}