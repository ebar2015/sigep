//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.09.15 a las 11:42:19 AM COT 
//

package co.gov.dafp.sigep2.util.xml;

import java.io.Serializable;

public class Columna extends co.gov.dafp.sigep2.util.xml.elemento.Columna implements Serializable {
	private static final long serialVersionUID = -2507428971647015251L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
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
		Columna other = (Columna) obj;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Columna [orden=" + orden + "]";
	}

}
