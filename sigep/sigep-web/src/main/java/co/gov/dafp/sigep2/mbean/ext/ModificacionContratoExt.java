/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.ModificacionContrato;

/**
 * @author joseviscaya
 *
 */
public class ModificacionContratoExt extends ModificacionContrato implements Serializable , Comparable<ModificacionContrato>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3400411047630205821L;
	
	
	private String nombreTipoModificacionContrato;
	private String nombreContratistaInicial;
	private String nombreNuevoContratista;
	private String nombreIdentificacionInicial;
	private String numeroIdentificacionInicial;
	private String nombreIdentificacionNuevo;
	private String numeroIdentificacionNuevo;


	/**
	 * @return the nombreTipoModificacionContrato
	 */
	public String getNombreTipoModificacionContrato() {
		return nombreTipoModificacionContrato;
	}


	/**
	 * @param nombreTipoModificacionContrato the nombreTipoModificacionContrato to set
	 */
	public void setNombreTipoModificacionContrato(String nombreTipoModificacionContrato) {
		this.nombreTipoModificacionContrato = nombreTipoModificacionContrato;
	}


	/**
	 * @return the nombreContratistaInicial
	 */
	public String getNombreContratistaInicial() {
		return nombreContratistaInicial;
	}


	/**
	 * @param nombreContratistaInicial the nombreContratistaInicial to set
	 */
	public void setNombreContratistaInicial(String nombreContratistaInicial) {
		this.nombreContratistaInicial = nombreContratistaInicial;
	}


	/**
	 * @return the nombreNuevoContratista
	 */
	public String getNombreNuevoContratista() {
		return nombreNuevoContratista;
	}


	/**
	 * @param nombreNuevoContratista the nombreNuevoContratista to set
	 */
	public void setNombreNuevoContratista(String nombreNuevoContratista) {
		this.nombreNuevoContratista = nombreNuevoContratista;
	}


	/**
	 * @return the nombreIdentificacionInicial
	 */
	public String getNombreIdentificacionInicial() {
		return nombreIdentificacionInicial;
	}


	/**
	 * @param nombreIdentificacionInicial the nombreIdentificacionInicial to set
	 */
	public void setNombreIdentificacionInicial(String nombreIdentificacionInicial) {
		this.nombreIdentificacionInicial = nombreIdentificacionInicial;
	}


	/**
	 * @return the numeroIdentificacionInicial
	 */
	public String getNumeroIdentificacionInicial() {
		return numeroIdentificacionInicial;
	}


	/**
	 * @param numeroIdentificacionInicial the numeroIdentificacionInicial to set
	 */
	public void setNumeroIdentificacionInicial(String numeroIdentificacionInicial) {
		this.numeroIdentificacionInicial = numeroIdentificacionInicial;
	}


	/**
	 * @return the nombreIdentificacionNuevo
	 */
	public String getNombreIdentificacionNuevo() {
		return nombreIdentificacionNuevo;
	}


	/**
	 * @param nombreIdentificacionNuevo the nombreIdentificacionNuevo to set
	 */
	public void setNombreIdentificacionNuevo(String nombreIdentificacionNuevo) {
		this.nombreIdentificacionNuevo = nombreIdentificacionNuevo;
	}


	/**
	 * @return the numeroIdentificacionNuevo
	 */
	public String getNumeroIdentificacionNuevo() {
		return numeroIdentificacionNuevo;
	}


	/**
	 * @param numeroIdentificacionNuevo the numeroIdentificacionNuevo to set
	 */
	public void setNumeroIdentificacionNuevo(String numeroIdentificacionNuevo) {
		this.numeroIdentificacionNuevo = numeroIdentificacionNuevo;
	}
	
	@Override
	public int compareTo(ModificacionContrato o) {
        if (this.getCodModificacionContrato() < o.getCodModificacionContrato()) {
            return 1;
        }
        if (this.getCodModificacionContrato()> o.getCodModificacionContrato()) {
            return -1;
        }
        return 0;
	}

}
