/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.entities.DatoContacto;



/**
 * @author joseviscaya
 *
 */
public class DatoContactoExt extends DatoContacto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3682834321894412894L;
	
	private String nombrePais;
	
	private String nombreDepartamento;
	
	private String nombreMunicipio;
	
	private String nombreTipoZona;
	
	private String indicativoString;
	
	private Short definitivo;
	
	private BigDecimal codDeclaracion;
	
	private String indicativoOficinaString;

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

	public String getIndicativoString() {		
		return indicativoString;
	}

	public void setIndicativoString(String indicativoString) {
		this.indicativoString = indicativoString;
	}

	/**
	 * @return the definitivo
	 */
	public Short getDefinitivo() {
		return definitivo;
	}

	/**
	 * @param definitivo the definitivo to set
	 */
	public void setDefinitivo(Short definitivo) {
		this.definitivo = definitivo;
	}

	/**
	 * @return the codDeclaracion
	 */
	public BigDecimal getCodDeclaracion() {
		return codDeclaracion;
	}

	/**
	 * @param codDeclaracion the codDeclaracion to set
	 */
	public void setCodDeclaracion(BigDecimal codDeclaracion) {
		this.codDeclaracion = codDeclaracion;
	}

	public String getIndicativoOficinaString() {
		return indicativoOficinaString;
	}

	public void setIndicativoOficinaString(String indicativoOficinaString) {
		this.indicativoOficinaString = indicativoOficinaString;
	}
	
	

}
