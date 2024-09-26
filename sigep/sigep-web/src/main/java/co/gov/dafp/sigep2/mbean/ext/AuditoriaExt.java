/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.gov.dafp.sigep2.entities.Auditoria;

/**
 * @author joseviscaya
 *
 */
public class AuditoriaExt extends Auditoria implements Serializable {

	private static final long serialVersionUID = -2765195723935484221L;

	private String nombreParametro;
	private Long codTablaParametrica;
	private long codTablaAuditoria;
	private Date fechaIni;
	private Date fechaFin;
	private int total;
	private String nombrePersona;
	private String nombreRol;
	private String nombreTipoIdentificacion;
	private String numeroIdentificacion;
	

	private String FechaActualiza;
	
	public String getFechaActualiza() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.FechaActualiza= sdf.format(getAudFechaModificacion());
		return FechaActualiza;
	}

	public void setFechaActualiza(String fechaActualiza) {
		FechaActualiza = fechaActualiza;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the nombreParametro
	 */
	public String getNombreParametro() {
		return nombreParametro;
	}

	/**
	 * @param nombreParametro
	 *            the nombreParametro to set
	 */
	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	/**
	 * @return the codTablaParametrica
	 */
	public Long getCodTablaParametrica() {
		return codTablaParametrica;
	}

	/**
	 * @param codTablaParametrica
	 *            the codTablaParametrica to set
	 */
	public void setCodTablaParametrica(Long codTablaParametrica) {
		this.codTablaParametrica = codTablaParametrica;
	}

	/**
	 * @return the fechaIni
	 */
	public Date getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni
	 *            the fechaIni to set
	 */
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
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

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
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

	public String getNombreTipoIdentificacion() {
		return nombreTipoIdentificacion;
	}

	public void setNombreTipoIdentificacion(String nombreTipoIdentificacion) {
		this.nombreTipoIdentificacion = nombreTipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	
	
}