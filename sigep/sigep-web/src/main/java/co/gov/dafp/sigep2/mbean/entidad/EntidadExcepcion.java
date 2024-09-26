package co.gov.dafp.sigep2.mbean.entidad;

import java.io.Serializable;

public class EntidadExcepcion extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;

	public EntidadExcepcion() {
	}
	
	public EntidadExcepcion(String mensaje) {
		super(mensaje);
	}

}