/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;

/**
 * @author Jose Viscaya
 * Dec 18, 2018
 */
public class DireccionEntidad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8223125104741241932L;
	
	private String codTipoVia;
	private String numero;
	private String codPrimerLetra;
	private String bis;
	private String codOrientacion;
	private String codSegundaLetra;
	private String segundoNumero;
	private String tercerNumero;
	private String complemento;
	/**
	 * @return the codTipoVia
	 */
	public String getCodTipoVia() {
		return codTipoVia;
	}
	/**
	 * @param codTipoVia the codTipoVia to set
	 */
	public void setCodTipoVia(String codTipoVia) {
		this.codTipoVia = codTipoVia;
	}
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
	 * @return the codPrimerLetra
	 */
	public String getCodPrimerLetra() {
		return codPrimerLetra;
	}
	/**
	 * @param codPrimerLetra the codPrimerLetra to set
	 */
	public void setCodPrimerLetra(String codPrimerLetra) {
		this.codPrimerLetra = codPrimerLetra;
	}
	/**
	 * @return the bis
	 */
	public String getBis() {
		return bis;
	}
	/**
	 * @param bis the bis to set
	 */
	public void setBis(String bis) {
		this.bis = bis;
	}
	/**
	 * @return the codOrientacion
	 */
	public String getCodOrientacion() {
		return codOrientacion;
	}
	/**
	 * @param codOrientacion the codOrientacion to set
	 */
	public void setCodOrientacion(String codOrientacion) {
		this.codOrientacion = codOrientacion;
	}
	/**
	 * @return the codSegundaLetra
	 */
	public String getCodSegundaLetra() {
		return codSegundaLetra;
	}
	/**
	 * @param codSegundaLetra the codSegundaLetra to set
	 */
	public void setCodSegundaLetra(String codSegundaLetra) {
		this.codSegundaLetra = codSegundaLetra;
	}
	/**
	 * @return the segundoNumero
	 */
	public String getSegundoNumero() {
		return segundoNumero;
	}
	/**
	 * @param segundoNumero the segundoNumero to set
	 */
	public void setSegundoNumero(String segundoNumero) {
		this.segundoNumero = segundoNumero;
	}
	/**
	 * @return the tercerNumero
	 */
	public String getTercerNumero() {
		return tercerNumero;
	}
	/**
	 * @param tercerNumero the tercerNumero to set
	 */
	public void setTercerNumero(String tercerNumero) {
		this.tercerNumero = tercerNumero;
	}
	
	
	public String toString() {
		String data = codTipoVia;
		if(!numero.equals("null")) {
			data +=" "+numero;
		}
		if(!codPrimerLetra.equals("null")) {
			data +=" "+codPrimerLetra;
		}
		if(!bis.equals("false")) {
			data +=" "+bis;
		}
		if(!codOrientacion.equals("null")) {
			data +=" "+codOrientacion;
		}
		if(!segundoNumero.equals("null")) {
			data +=" #"+segundoNumero;
		}
		if(!tercerNumero.equals("null")) {
			data +=" - "+tercerNumero;
		}
		if(!complemento.equals("null")) {
			data +=" "+complemento;
		}
		return data;
	}
	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}
	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	


}
