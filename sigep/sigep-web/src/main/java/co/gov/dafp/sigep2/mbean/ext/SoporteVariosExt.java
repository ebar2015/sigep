/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author joseviscaya
 *
 */
public class SoporteVariosExt implements Serializable {
	
	private static final long serialVersionUID = 3051881734700324049L;
	private BigDecimal id;
	private String tipoSoporte;
	private String detalle;
	private Boolean verificacion;
	private String url;
	private String tipoDato;
	
	public SoporteVariosExt() {
		super();
	}

	public SoporteVariosExt(BigDecimal id, String tipoSoporte, String detalle, Boolean verificacion, String url, String tipoDato) {
		super();
		this.id = id;
		this.tipoSoporte = tipoSoporte;
		this.detalle = detalle;
		this.verificacion = verificacion;
		this.url = url;
		this.tipoDato = tipoDato;
	}
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getTipoSoporte() {
		return tipoSoporte;
	}
	public void setTipoSoporte(String tipoSoporte) {
		this.tipoSoporte = tipoSoporte;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Boolean getVerificacion() {
		return verificacion;
	}
	public void setVerificacion(Boolean verificacion) {
		this.verificacion = verificacion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
}