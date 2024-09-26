/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.bean.Auditoria;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  AuditoriaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class AuditoriaExt extends Auditoria implements Serializable {
	private static final long serialVersionUID = -2765195723935484221L;
	private String nombreParametro;
	private long codTablaParametrica;
	private long codTablaAuditoria;
	private Date fechaIni;
	private Date fechaFin;
	private int total;
	private String nombrePersona;
	private String nombreRol;
	private String nombreTipoIdentificacion;
	private String numeroIdentificacion;

	/**
	 * @return the nombreParametro
	 */
	public String getNombreParametro() {
		return nombreParametro;
	}

	/**
	 * @param nombreParametro the nombreParametro to set
	 */
	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	/**
	 * @return the codTablaParametrica
	 */
	public long getCodTablaParametrica() {
		return codTablaParametrica;
	}

	/**
	 * @param codTablaParametrica the codTablaParametrica to set
	 */
	public void setCodTablaParametrica(long codTablaParametrica) {
		this.codTablaParametrica = codTablaParametrica;
	}

	/**
	 * @return the fechaIni
	 */
	public Date getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni the fechaIni to set
	 */
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the codTablaAuditoria
	 */
	public long getCodTablaAuditoria() {
		return codTablaAuditoria;
	}

	/**
	 * @param codTablaAuditoria the codTablaAuditoria to set
	 */
	public void setCodTablaAuditoria(long codTablaAuditoria) {
		this.codTablaAuditoria = codTablaAuditoria;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getNombreTipoIdentificacion() {
		return nombreTipoIdentificacion;
	}

	public void setNombreTipoIdentificacion(String nombreTipoIdentificacion) {
		this.nombreTipoIdentificacion = nombreTipoIdentificacion;
	}
	
	
	
	
	
}