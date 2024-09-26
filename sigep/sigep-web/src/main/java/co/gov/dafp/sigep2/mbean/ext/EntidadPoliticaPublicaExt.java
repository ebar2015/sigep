package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import co.gov.dafp.sigep2.entities.EntidadPoliticaPublica;


/**
* @author Maria Alejandra Colorado
* @version 1.0
* @Class Clase  EntidadPoliticaPublicaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module Gestion de Ambito de politicas
 */

public class EntidadPoliticaPublicaExt extends EntidadPoliticaPublica implements Serializable {
	
	private static final long serialVersionUID = 2967139754484740619L;
	
	private String nombrePolitica;
	private String descripcionPolitica;
	private Long codTipoPolitica;
	private String nombreTipoPolitica;
	/**
	 * @return the nombrePolitica
	 */
	public String getNombrePolitica() {
		return nombrePolitica;
	}
	/**
	 * @param nombrePolitica the nombrePolitica to set
	 */
	public void setNombrePolitica(String nombrePolitica) {
		this.nombrePolitica = nombrePolitica;
	}
	/**
	 * @return the descripcionPolitica
	 */
	public String getDescripcionPolitica() {
		return descripcionPolitica;
	}
	/**
	 * @param descripcionPolitica the descripcionPolitica to set
	 */
	public void setDescripcionPolitica(String descripcionPolitica) {
		this.descripcionPolitica = descripcionPolitica;
	}
	/**
	 * @return the codTipoPolitica
	 */
	public Long getCodTipoPolitica() {
		return codTipoPolitica;
	}
	/**
	 * @param codTipoPolitica the codTipoPolitica to set
	 */
	public void setCodTipoPolitica(Long codTipoPolitica) {
		this.codTipoPolitica = codTipoPolitica;
	}
	/**
	 * @return the nombreTipoPolitica
	 */
	public String getNombreTipoPolitica() {
		return nombreTipoPolitica;
	}
	/**
	 * @param nombreTipoPolitica the nombreTipoPolitica to set
	 */
	public void setNombreTipoPolitica(String nombreTipoPolitica) {
		this.nombreTipoPolitica = nombreTipoPolitica;
	}
}