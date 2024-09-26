/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.EntidadPlanta;
import java.util.Date;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  EntidadPlantaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class EntidadPlantaExt extends EntidadPlanta implements Serializable {
	
	private static final long serialVersionUID = 3489147546314479051L;
	
	private String nombreNorma;
	private String nombreClasificacionPlanta;
	private String nombreClasePlanta;
	private String nombreNaturalezaEmpleo;
	private String nombreTipoNorma;
	private String numeroNorma;
	private String objetoNorma;
	private Long codTipoNorma;
	private Date fechaNorma;
	private int limitInit;
    private int limitEnd;
    private Long codTipoPlanta;
    private Long annoNorma;
    
	
    
    /**
	 * @return el annoNorma
	 */
	public Long getAnnoNorma() {
		return annoNorma;
	}

	/**
	 * @param annoNorma el annoNorma a establecer
	 */
	public void setAnnoNorma(Long annoNorma) {
		this.annoNorma = annoNorma;
	}

	/**
	 * @return the nombreNorma
	 */
	public String getNombreNorma() {
		return nombreNorma;
	}

	/**
	 * @param nombreNorma the nombreNorma to set
	 */
	public void setNombreNorma(String nombreNorma) {
		this.nombreNorma = nombreNorma;
	}

	/**
	 * @return the nombreClasificacionPlanta
	 */
	public String getNombreClasificacionPlanta() {
		return nombreClasificacionPlanta;
	}

	/**
	 * @param nombreClasificacionPlanta the nombreClasificacionPlanta to set
	 */
	public void setNombreClasificacionPlanta(String nombreClasificacionPlanta) {
		this.nombreClasificacionPlanta = nombreClasificacionPlanta;
	}

	/**
	 * @return the nombreClasePlanta
	 */
	public String getNombreClasePlanta() {
		return nombreClasePlanta;
	}

	/**
	 * @param nombreClasePlanta the nombreClasePlanta to set
	 */
	public void setNombreClasePlanta(String nombreClasePlanta) {
		this.nombreClasePlanta = nombreClasePlanta;
	}

	/**
	 * @return the nombreNaturalezaEmpleo
	 */
	public String getNombreNaturalezaEmpleo() {
		return nombreNaturalezaEmpleo;
	}

	/**
	 * @param nombreNaturalezaEmpleo the nombreNaturalezaEmpleo to set
	 */
	public void setNombreNaturalezaEmpleo(String nombreNaturalezaEmpleo) {
		this.nombreNaturalezaEmpleo = nombreNaturalezaEmpleo;
	}
	
    /**
	 * @return the nombreTipoNorma
	 */
	public String getNombreTipoNorma() {
		return nombreTipoNorma;
	}

	/**
	 * @param nombreTipoNorma the nombreTipoNorma to set
	 */
	public void setNombreTipoNorma(String nombreTipoNorma) {
		this.nombreTipoNorma = nombreTipoNorma;
	}

	/**
	 * @return the codTipoNorma
	 */
	public Long getCodTipoNorma() {
		return codTipoNorma;
	}

	/**
	 * @param codTipoNorma the codTipoNorma to set
	 */
	public void setCodTipoNorma(Long codTipoNorma) {
		this.codTipoNorma = codTipoNorma;
	}

	/**
	 * @return the fechaNorma
	 */
	public Date getFechaNorma() {
		return fechaNorma;
	}

	/**
	 * @param fechaNorma the fechaNorma to set
	 */
	public void setFechaNorma(Date fechaNorma) {
		this.fechaNorma = fechaNorma;
	}

	/**
	 * @return the objetoNorma
	 */
	public String getObjetoNorma() {
		return objetoNorma;
	}

	/**
	 * @param objetoNorma the objetoNorma to set
	 */
	public void setObjetoNorma(String objetoNorma) {
		this.objetoNorma = objetoNorma;
	}

	/**
	 * @return the limitInit
	 */
	public int getLimitInit() {
		return limitInit;
	}

	/**
	 * @param limitInit the limitInit to set
	 */
	public void setLimitInit(int limitInit) {
		this.limitInit = limitInit;
	}

	/**
	 * @return the limitEnd
	 */
	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd the limitEnd to set
	 */
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public Long getCodTipoPlanta() {
		return codTipoPlanta;
	}

	public void setCodTipoPlanta(Long codTipoPlanta) {
		this.codTipoPlanta = codTipoPlanta;
	}

	/**
	 * @return the numeroNorma
	 */
	public String getNumeroNorma() {
		return numeroNorma;
	}

	/**
	 * @param numeroNorma the numeroNorma to set
	 */
	public void setNumeroNorma(String numeroNorma) {
		this.numeroNorma = numeroNorma;
	}
	
	
}
