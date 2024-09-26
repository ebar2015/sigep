package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase que implementa los registros de Ley de cuotas de la Tabla de Resultados
 */
public class LeyCuotas extends ErrorMensajes {
	private static final long serialVersionUID = 8346794124714608341L;

	private long codCifrasEmpleoPublico;

	private String etiqueta;

	// Ley de Cuotas
	private BigInteger debeReportarLC;
	private BigInteger reportoLC;
	private BigDecimal porcentaje;
	private Date fechaCorte;
	private BigInteger nivelDecisorioTotal;
	private BigInteger nivelDecisorioVacantes;
	private BigInteger nivelDecisorioProvistos;
	private BigInteger nivelDecisorioMujeres;
	private BigInteger nivelDecisorioHombres;
	private BigDecimal nivelDecisorioPMujeres;
	private BigDecimal nivelDecisorioPHombres;

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

	public BigInteger getDebeReportarLC() {
		return debeReportarLC;
	}

	public void setDebeReportarLC(BigInteger debeReportarLC) {
		this.debeReportarLC = debeReportarLC;
	}

	public BigInteger getReportoLC() {
		return reportoLC;
	}

	public void setReportoLC(BigInteger reportoLC) {
		this.reportoLC = reportoLC;
	}

	public BigDecimal getPorcentaje() {
		if (debeReportarLC != null && debeReportarLC.compareTo(BigInteger.ZERO) != 0) {
			if (reportoLC == null) {
				reportoLC = BigInteger.ZERO;
			}
			setPorcentaje(BigDecimal.valueOf(reportoLC.doubleValue() / debeReportarLC.doubleValue()));
		}
		return porcentaje;
	}

	private void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public BigInteger getNivelDecisorioTotal() {
		return nivelDecisorioTotal;
	}

	public void setNivelDecisorioTotal(BigInteger nivelDecisorioTotal) {
		this.nivelDecisorioTotal = nivelDecisorioTotal;
	}

	public BigInteger getNivelDecisorioVacantes() {
		return nivelDecisorioVacantes;
	}

	public void setNivelDecisorioVacantes(BigInteger nivelDecisorioVacantes) {
		this.nivelDecisorioVacantes = nivelDecisorioVacantes;
	}

	public BigInteger getNivelDecisorioProvistos() {
		return nivelDecisorioProvistos;
	}

	public void setNivelDecisorioProvistos(BigInteger nivelDecisorioProvistos) {
		this.nivelDecisorioProvistos = nivelDecisorioProvistos;
	}

	public BigInteger getNivelDecisorioMujeres() {
		return nivelDecisorioMujeres;
	}

	public void setNivelDecisorioMujeres(BigInteger nivelDecisorioMujeres) {
		this.nivelDecisorioMujeres = nivelDecisorioMujeres;
	}

	public BigInteger getNivelDecisorioHombres() {
		return nivelDecisorioHombres;
	}

	public void setNivelDecisorioHombres(BigInteger nivelDecisorioHombres) {
		this.nivelDecisorioHombres = nivelDecisorioHombres;
	}

	public BigDecimal getNivelDecisorioPMujeres() {
		return nivelDecisorioPMujeres;
	}

	public void setNivelDecisorioPMujeres(BigDecimal nivelDecisorioPMujeres) {
		this.nivelDecisorioPMujeres = nivelDecisorioPMujeres;
	}

	public BigDecimal getNivelDecisorioPHombres() {
		return nivelDecisorioPHombres;
	}

	public void setNivelDecisorioPHombres(BigDecimal nivelDecisorioPHombres) {
		this.nivelDecisorioPHombres = nivelDecisorioPHombres;
	}

	@Override
	public String toString() {
		return "LeyCuotas [codCifrasEmpleoPublico=" + codCifrasEmpleoPublico + ", etiqueta=" + etiqueta
				+ ", debeReportarLC=" + debeReportarLC + ", reportoLC=" + reportoLC + ", porcentaje=" + porcentaje
				+ ", fechaCorte=" + fechaCorte + ", nivelDecisorioTotal=" + nivelDecisorioTotal
				+ ", nivelDecisorioVacantes=" + nivelDecisorioVacantes + ", nivelDecisorioProvistos="
				+ nivelDecisorioProvistos + ", nivelDecisorioMujeres=" + nivelDecisorioMujeres
				+ ", nivelDecisorioHombres=" + nivelDecisorioHombres + ", nivelDecisorioPMujeres="
				+ nivelDecisorioPMujeres + ", nivelDecisorioPHombres=" + nivelDecisorioPHombres + "]";
	}
}
