/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.bean.DatoContacto;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  DatoContactoExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class DatoContactoExt extends DatoContacto implements Serializable {

	private static final long serialVersionUID = -3682834321894412894L;
	
	private String nombrePais;
	private String nombreDepartamento;
	private String nombreMunicipio;
	private String nombreTipoZona;
	
	private String tipoZona;
	
	private Short definitivo;
	
	private BigDecimal codDeclaracion;
	
	 private String codIsoPais;
	 private Integer codDaneDepartamento;
	 private Integer codDaneMunicipio;
	

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
	 * @return the nombreTipoZona
	 */
	public String getNombreTipoZona() {
		return nombreTipoZona;
	}

	/**
	 * @param nombreTipoZona the nombreTipoZona to set
	 */
	public void setNombreTipoZona(String nombreTipoZona) {
		this.nombreTipoZona = nombreTipoZona;
	}

	/**
	 * @return the tipoZona
	 */
	public String getTipoZona() {
		return tipoZona;
	}

	/**
	 * @param tipoZona the tipoZona to set
	 */
	public void setTipoZona(String tipoZona) {
		this.tipoZona = tipoZona;
	}

	public Short getDefinitivo() {
		return definitivo;
	}

	public void setDefinitivo(Short definitivo) {
		this.definitivo = definitivo;
	}

	public BigDecimal getCodDeclaracion() {
		return codDeclaracion;
	}

	public void setCodDeclaracion(BigDecimal codDeclaracion) {
		this.codDeclaracion = codDeclaracion;
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


}
