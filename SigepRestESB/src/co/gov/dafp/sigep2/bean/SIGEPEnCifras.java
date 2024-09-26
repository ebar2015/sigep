package co.gov.dafp.sigep2.bean;

import java.math.BigInteger;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase para el manejo de reportes publicados en el portal
 */
public class SIGEPEnCifras extends ErrorMensajes {
	private static final long serialVersionUID = 3974001843924532558L;

	private BigInteger id;

	private String nombre;

	private String acceso;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SIGEPEnCifras other = (SIGEPEnCifras) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sbUrlSigep =new StringBuilder("{\"id\":").append(id)
				.append(",\"nombre\":\"").append(nombre).append("\",\"acceso\":\"").append(acceso)
				.append("\"}");	
		return sbUrlSigep.toString(); 
		//return "SIGEPEnCifras [id=" + id + ", nombre=" + nombre + ", acceso=" + acceso + "]";
	}
}
