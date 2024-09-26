package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.ParticipacionProyecto;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ParticipacionProyectoExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class ParticipacionProyectoExt extends ParticipacionProyecto {
	
    private static final long serialVersionUID = -1025524769312911270L;
	
	private String nombrePais;
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

}
