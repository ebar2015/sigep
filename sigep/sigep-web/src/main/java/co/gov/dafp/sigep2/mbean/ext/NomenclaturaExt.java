/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.Nomenclatura;

/**
 * @author joseviscaya
 *
 */
public class NomenclaturaExt extends Nomenclatura{

	
    private static final long serialVersionUID = -7055799966484978612L;
      
    
    String nombreEntidad;
    String codSigep;
    String nombrePlanta;
    String origenes;
    Long denDestino;
    private String nombreRamaOrganizacional ;
	private String nombreSistemaCarrera ;
    
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
	 * @return the codSigep
	 */
	public String getCodSigep() {
		return codSigep;
	}
	/**
	 * @param codSigep the codSigep to set
	 */
	public void setCodSigep(String codSigep) {
		this.codSigep = codSigep;
	}
	/**
	 * @return the nombrePlanta
	 */
	public String getNombrePlanta() {
		return nombrePlanta;
	}
	/**
	 * @param nombrePlanta the nombrePlanta to set
	 */
	public void setNombrePlanta(String nombrePlanta) {
		this.nombrePlanta = nombrePlanta;
	}
	/**
	 * @return the origenes
	 */
	public String getOrigenes() {
		return origenes;
	}
	/**
	 * @param origenes the origenes to set
	 */
	public void setOrigenes(String origenes) {
		this.origenes = origenes;
	}
	/**
	 * @return the denDestino
	 */
	public Long getDenDestino() {
		return denDestino;
	}
	/**
	 * @param denDestino the denDestino to set
	 */
	public void setDenDestino(Long denDestino) {
		this.denDestino = denDestino;
	}
	/**
	 * @return the nombreRamaOrganizacional
	 */
	public String getNombreRamaOrganizacional() {
		return nombreRamaOrganizacional;
	}
	/**
	 * @param nombreRamaOrganizacional the nombreRamaOrganizacional to set
	 */
	public void setNombreRamaOrganizacional(String nombreRamaOrganizacional) {
		this.nombreRamaOrganizacional = nombreRamaOrganizacional;
	}
	/**
	 * @return the nombreSistemaCarrera
	 */
	public String getNombreSistemaCarrera() {
		return nombreSistemaCarrera;
	}
	/**
	 * @param nombreSistemaCarrera the nombreSistemaCarrera to set
	 */
	public void setNombreSistemaCarrera(String nombreSistemaCarrera) {
		this.nombreSistemaCarrera = nombreSistemaCarrera;
	}
}