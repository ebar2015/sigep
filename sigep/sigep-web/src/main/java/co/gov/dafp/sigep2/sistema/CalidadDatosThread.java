package co.gov.dafp.sigep2.sistema;

import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;

public class CalidadDatosThread extends Thread {
	
	
	private Persona infoUsuario; 
	

	public CalidadDatosThread(Persona pinfoUsuario) {
		this.infoUsuario = pinfoUsuario;
	}	
	
	/**
	 * Ejecución del hilo Calidad Datos
	 */
	@Override
	public void run() {
		ComunicacionServiciosAdmin.aplicarCalidadDatos(infoUsuario.getCorreoElectronico());
	}

	
}
