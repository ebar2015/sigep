/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.PersonaParentesco;

/**
 * @author joseviscaya
 *
 */
public class PersonaParentescoExt extends PersonaParentesco{

	private static final long serialVersionUID = -7055799966484978612L;

    private String nombreGenero;
    private String nombreTipoDocuento;
    private String nombreParentesco;
    private String nombreCompleto;

    /**
     * @return the nombreGenero
     */
    public String getNombreGenero()
    {
	return nombreGenero;
    }

    /**
     * @param nombreGenero the nombreGenero to set
     */
    public void setNombreGenero(String nombreGenero)
    {
	this.nombreGenero = nombreGenero;
    }

    /**
     * @return the nombreTipoDocuento
     */
    public String getNombreTipoDocuento()
    {
	return nombreTipoDocuento;
    }

    /**
     * @param nombreTipoDocuento the nombreTipoDocuento to set
     */
    public void setNombreTipoDocuento(String nombreTipoDocuento)
    {
	this.nombreTipoDocuento = nombreTipoDocuento;
    }

    /**
     * @return the nombreParentesco
     */
    public String getNombreParentesco()
    {
	return nombreParentesco;
    }

    /**
     * @param nombreParentesco the nombreParentesco to set
     */
    public void setNombreParentesco(String nombreParentesco)
    {
	this.nombreParentesco = nombreParentesco;
    }

    public String getNombreCompleto()
    {
	if ((getSegundoNombre() == null || getSegundoNombre() == "") && getSegundoApellido() != null) {
	    nombreCompleto = getPrimerNombre() + " " + getPrimerApellido() + " " + getSegundoApellido();
	} else if (getSegundoApellido() == null || getSegundoApellido() == "") {
	    nombreCompleto = getPrimerNombre() + " " + getPrimerApellido();
	} else {
	    nombreCompleto = getPrimerNombre() + " " + getSegundoNombre() + " " + getPrimerApellido() + " " + getSegundoApellido();
	}
	return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto)
    {
	this.nombreCompleto = nombreCompleto;
    }
}