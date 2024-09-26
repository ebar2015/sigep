package co.gov.dafp.sigep2.entities;

import java.math.BigInteger;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase que implementa los registros del tablero de Bilinguismo
 */
public class Bilinguismo extends ErrorMensajes {
	private static final long serialVersionUID = 4086061823508484444L;

	private long codCifrasEmpleoPublico;

	private String etiqueta;

	// Bilinguismo
	private BigInteger cantidadPreInsc;
	private BigInteger totalPreInsc;
	private BigInteger cantidadIni;
	private BigInteger totalIni;
	private BigInteger cantidadTer;
	private BigInteger totalTer;
	private BigInteger cantidadApro;
	private BigInteger totalApro;
	public long getCodCifrasEmpleoPublico() {
		return codCifrasEmpleoPublico;
	}
	public void setCodCifrasEmpleoPublico(long codCifrasEmpleoPublico) {
		this.codCifrasEmpleoPublico = codCifrasEmpleoPublico;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public BigInteger getCantidadPreInsc() {
		return cantidadPreInsc;
	}
	public void setCantidadPreInsc(BigInteger cantidadPreInsc) {
		this.cantidadPreInsc = cantidadPreInsc;
	}
	public BigInteger getTotalPreInsc() {
		return totalPreInsc;
	}
	public void setTotalPreInsc(BigInteger totalPreInsc) {
		this.totalPreInsc = totalPreInsc;
	}
	public BigInteger getCantidadIni() {
		return cantidadIni;
	}
	public void setCantidadIni(BigInteger cantidadIni) {
		this.cantidadIni = cantidadIni;
	}
	public BigInteger getTotalIni() {
		return totalIni;
	}
	public void setTotalIni(BigInteger totalIni) {
		this.totalIni = totalIni;
	}
	public BigInteger getCantidadTer() {
		return cantidadTer;
	}
	public void setCantidadTer(BigInteger cantidadTer) {
		this.cantidadTer = cantidadTer;
	}
	public BigInteger getTotalTer() {
		return totalTer;
	}
	public void setTotalTer(BigInteger totalTer) {
		this.totalTer = totalTer;
	}
	public BigInteger getCantidadApro() {
		return cantidadApro;
	}
	public void setCantidadApro(BigInteger cantidadApro) {
		this.cantidadApro = cantidadApro;
	}
	public BigInteger getTotalApro() {
		return totalApro;
	}
	public void setTotalApro(BigInteger totalApro) {
		this.totalApro = totalApro;
	}
}
