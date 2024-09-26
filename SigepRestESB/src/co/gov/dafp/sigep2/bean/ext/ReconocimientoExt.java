package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.Reconocimiento;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ReconocimientoExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class ReconocimientoExt extends Reconocimiento {

	private static final long serialVersionUID = 4423380311775448585L;
	
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
