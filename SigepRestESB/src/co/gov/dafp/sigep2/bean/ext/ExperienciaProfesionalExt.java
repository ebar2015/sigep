/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.ExperienciaProfesional;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ExperienciaProfesionalExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class ExperienciaProfesionalExt extends ExperienciaProfesional implements Serializable{
	
	private static final long serialVersionUID = -8918110819050298377L;
	private String nombrePais;
	private String nombreDepartamento;
	private String nombreMunicipio;
	private String descripcionLab;
	private String descripcionRet;
	private String descripcionEnt;
	private String tipoEntidad;
	private Integer diferenciaDias;
	private Integer total;
	private String cargoNombre;
	private String nombreEntidadExt;
	private String jornadaLaboral;
	private String motivoRetiro;
	private Short codGrado;
	private Integer codNivelCargo;
	private Long codDenominacion;
	
	private String codIsoPais;
	private Integer codDaneDepartamento;
	private Integer codDaneMunicipio;
	private String nombreCargoPriv;
	private String strDependenciaLike;
	private String strCargoLike;
	private String nombreDependencia;
	private String nombreCargo;
	private Short flgContratista;

	/**
	 * @return the nombrePais
	 */
	public String getNombrePais() {
		return nombrePais;
	}
	/**
	 * @param nombrePais the nombrePais to set
	 */
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	/**
	 * @return the nombreDepartamento
	 */
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	/**
	 * @param nombreDepartamento the nombreDepartamento to set
	 */
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	/**
	 * @return the nombreMunicipio
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}
	/**
	 * @param nombreMunicipio the nombreMunicipio to set
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	/**
	 * @return the descripcionLab
	 */
	public String getDescripcionLab() {
		return descripcionLab;
	}
	/**
	 * @param descripcionLab the descripcionLab to set
	 */
	public void setDescripcionLab(String descripcionLab) {
		this.descripcionLab = descripcionLab;
	}
	/**
	 * @return the descripcionRet
	 */
	public String getDescripcionRet() {
		return descripcionRet;
	}
	/**
	 * @param descripcionRet the descripcionRet to set
	 */
	public void setDescripcionRet(String descripcionRet) {
		this.descripcionRet = descripcionRet;
	}
	/**
	 * @return the descripcionEnt
	 */
	public String getDescripcionEnt() {
		return descripcionEnt;
	}
	/**
	 * @param descripcionEnt the descripcionEnt to set
	 */
	public void setDescripcionEnt(String descripcionEnt) {
		this.descripcionEnt = descripcionEnt;
	}
	/**
	 * @return the diferenciaDias
	 */
	public Integer getDiferenciaDias() {
		return diferenciaDias;
	}
	/**
	 * @param diferenciaDias the diferenciaDias to set
	 */
	public void setDiferenciaDias(Integer diferenciaDias) {
		this.diferenciaDias = diferenciaDias;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * @return the cargoNombre
	 */
	public String getCargoNombre() {
		return cargoNombre;
	}
	/**
	 * @param cargoNombre the cargoNombre to set
	 */
	public void setCargoNombre(String cargoNombre) {
		this.cargoNombre = cargoNombre;
	}
	/**
	 * @return the tipoEntidad
	 */
	public String getTipoEntidad() {
		return tipoEntidad;
	}
	/**
	 * @param tipoEntidad the tipoEntidad to set
	 */
	public void setTipoEntidad(String tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}
	/**
	 * @return the nombreEntidadExt
	 */
	public String getNombreEntidadExt() {
		return nombreEntidadExt;
	}
	/**
	 * @param nombreEntidadExt the nombreEntidadExt to set
	 */
	public void setNombreEntidadExt(String nombreEntidadExt) {
		this.nombreEntidadExt = nombreEntidadExt;
	}
	/**
	 * @return the jornadaLaboral
	 */
	public String getJornadaLaboral() {
		return jornadaLaboral;
	}
	/**
	 * @param jornadaLaboral the jornadaLaboral to set
	 */
	public void setJornadaLaboral(String jornadaLaboral) {
		this.jornadaLaboral = jornadaLaboral;
	}
	/**
	 * @return the motivoRetiro
	 */
	public String getMotivoRetiro() {
		return motivoRetiro;
	}
	/**
	 * @param motivoRetiro the motivoRetiro to set
	 */
	public void setMotivoRetiro(String motivoRetiro) {
		this.motivoRetiro = motivoRetiro;
	}
	/**
	 * @return el codGrado
	 */
	public Short getCodGrado() {
		return codGrado;
	}
	/**
	 * @param codGrado el codGrado a establecer
	 */
	public void setCodGrado(Short codGrado) {
		this.codGrado = codGrado;
	}
	/**
	 * @return el codNivelCargo
	 */
	public Integer getCodNivelCargo() {
		return codNivelCargo;
	}
	/**
	 * @param codNivelCargo el codNivelCargo a establecer
	 */
	public void setCodNivelCargo(Integer codNivelCargo) {
		this.codNivelCargo = codNivelCargo;
	}
	/**
	 * @return el codDenominacion
	 */
	public Long getCodDenominacion() {
		return codDenominacion;
	}
	/**
	 * @param codDenominacion el codDenominacion a establecer
	 */
	public void setCodDenominacion(Long codDenominacion) {
		this.codDenominacion = codDenominacion;
	}
	/**
	 * @return the codIsoPais
	 */
	public String getCodIsoPais() {
		return codIsoPais;
	}
	/**
	 * @param codIsoPais the codIsoPais to set
	 */
	public void setCodIsoPais(String codIsoPais) {
		this.codIsoPais = codIsoPais;
	}
	/**
	 * @return the codDaneDepartamento
	 */
	public Integer getCodDaneDepartamento() {
		return codDaneDepartamento;
	}
	/**
	 * @param codDaneDepartamento the codDaneDepartamento to set
	 */
	public void setCodDaneDepartamento(Integer codDaneDepartamento) {
		this.codDaneDepartamento = codDaneDepartamento;
	}
	/**
	 * @return the codDaneMunicipio
	 */
	public Integer getCodDaneMunicipio() {
		return codDaneMunicipio;
	}
	/**
	 * @param codDaneMunicipio the codDaneMunicipio to set
	 */
	public void setCodDaneMunicipio(Integer codDaneMunicipio) {
		this.codDaneMunicipio = codDaneMunicipio;
	}
	/**
	 * @return the nombreCargoPriv
	 */
	public String getNombreCargoPriv() {
		return nombreCargoPriv;
	}
	/**
	 * @param nombreCargoPriv the nombreCargoPriv to set
	 */
	public void setNombreCargoPriv(String nombreCargoPriv) {
		this.nombreCargoPriv = nombreCargoPriv;
	}
	/**
	 * @return the strDependenciaLike
	 */
	public String getStrDependenciaLike() {
		return strDependenciaLike;
	}
	/**
	 * @param strDependenciaLike the strDependenciaLike to set
	 */
	public void setStrDependenciaLike(String strDependenciaLike) {
		this.strDependenciaLike = strDependenciaLike;
	}
	/**
	 * @return the strCargoLike
	 */
	public String getStrCargoLike() {
		return strCargoLike;
	}
	/**
	 * @param strCargoLike the strCargoLike to set
	 */
	public void setStrCargoLike(String strCargoLike) {
		this.strCargoLike = strCargoLike;
	}
	/**
	 * @return the nombreDependencia
	 */
	public String getNombreDependencia() {
		return nombreDependencia;
	}
	/**
	 * @param nombreDependencia the nombreDependencia to set
	 */
	public void setNombreDependencia(String nombreDependencia) {
		this.nombreDependencia = nombreDependencia;
	}
	/**
	 * @return the nombreCargo
	 */
	public String getNombreCargo() {
		return nombreCargo;
	}
	/**
	 * @param nombreCargo the nombreCargo to set
	 */
	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}
	/**
	 * @return the flgContratista
	 */
	public Short getFlgContratista() {
		return flgContratista;
	}
	/**
	 * @param flgContratista the flgContratista to set
	 */
	public void setFlgContratista(Short flgContratista) {
		this.flgContratista = flgContratista;
	}
	
}

