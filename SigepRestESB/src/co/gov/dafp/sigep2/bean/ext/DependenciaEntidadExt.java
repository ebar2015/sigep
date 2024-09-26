/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.bean.DependenciaEntidad;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  DependenciaEntidadExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class DependenciaEntidadExt extends DependenciaEntidad implements Serializable {

	private static final long serialVersionUID = -2154389874537009875L;
	
	private String nombreEntidad;
	private String nombreClaseDependencia;
	private BigDecimal codPersona;
	private Long codPredecesorNull;	
	private String ordenDependencias;
	 private String dependenciasPadre;

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
	 * @return the nombreClaseDependencia
	 */
	public String getNombreClaseDependencia() {
		return nombreClaseDependencia;
	}

	/**
	 * @param nombreClaseDependencia the nombreClaseDependencia to set
	 */
	public void setNombreClaseDependencia(String nombreClaseDependencia) {
		this.nombreClaseDependencia = nombreClaseDependencia;
	}

	/**
	 * @return the codPersona
	 */
	public BigDecimal getCodPersona() {
		return codPersona;
	}

	/**
	 * @param codPersona the codPersona to set
	 */
	public void setCodPersona(BigDecimal codPersona) {
		this.codPersona = codPersona;
	}

	public Long getCodPredecesorNull() {
		return codPredecesorNull;
	}

	public void setCodPredecesorNull(Long codPredecesorNull) {
		this.codPredecesorNull = codPredecesorNull;
	}

	public String getOrdenDependencias() {
		return ordenDependencias;
	}

	public void setOrdenDependencias(String ordenDependencias) {
		this.ordenDependencias = ordenDependencias;
	}

	public String getDependenciasPadre() {
		return dependenciasPadre;
	}

	public void setDependenciasPadre(String dependenciasPadre) {
		this.dependenciasPadre = dependenciasPadre;
	}
	
	
	
	
}
