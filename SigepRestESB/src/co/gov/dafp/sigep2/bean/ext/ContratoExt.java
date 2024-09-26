/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.Contrato;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ContratoExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class ContratoExt extends Contrato implements Serializable {

	private static final long serialVersionUID = -150416998711763798L;
	
	private String primerNombre;
	private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String nombreMunicipio;
    private String nombreMoneda;
    private String nombreEntidad;
    private int diasIni;
    private int diasFin;
    private String contratosActivos;
    private Short flgEstadoSuspendido;
    private Short flgEstadoProrroga;
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
	 * @return the nombreMunicipio
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}
	/**
	 * @param nombreMunicipio the nombreMunicipio to set
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	/**
	 * @return the nombreMoneda
	 */
	public String getNombreMoneda() {
		return nombreMoneda;
	}
	/**
	 * @param nombreMoneda the nombreMoneda to set
	 */
	public void setNombreMoneda(String nombreMoneda) {
		this.nombreMoneda = nombreMoneda;
	}

	public int getDiasIni() {
		return diasIni;
	}
	public void setDiasIni(int diasIni) {
		this.diasIni = diasIni;
	}
	public int getDiasFin() {
		return diasFin;
	}
	public void setDiasFin(int diasFin) {
		this.diasFin = diasFin;
	}
	public String getContratosActivos() {
		return contratosActivos;
	}
	public void setContratosActivos(String contratosActivos) {
		this.contratosActivos = contratosActivos;
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
	 * 
	 * Elaborado Por: Jose Viscaya
	 * Feb 19, 2019
	 * ContratoExt.java
	 * @return
	 */
	public String getNombreUsuario() {
		String nom ="";
		if(primerNombre != null) {
			nom+=primerNombre;
		}
		if(segundoNombre != null) {
			nom+=" "+segundoNombre;
		}
		if(primerApellido != null) {
			nom+=" "+primerApellido;
		}
		if(segundoApellido != null) {
			nom+=" "+segundoApellido;
		}
		return nom;
	}
	public Short getFlgEstadoSuspendido() {
		return flgEstadoSuspendido;
	}
	public void setFlgEstadoSuspendido(Short flgEstadoSuspendido) {
		this.flgEstadoSuspendido = flgEstadoSuspendido;
	}
	public Short getFlgEstadoProrroga() {
		return flgEstadoProrroga;
	}
	public void setFlgEstadoProrroga(Short flgEstadoProrroga) {
		this.flgEstadoProrroga = flgEstadoProrroga;
	}
	
	
	
	
	




}
