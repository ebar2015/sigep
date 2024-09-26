package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase que implementa los registros de Empleo de la Tabla de Resultados
 */
public class Empleo extends ErrorMensajes {
	private static final long serialVersionUID = 2339708920617046832L;

	private long codCifrasEmpleoPublico;

	private String etiqueta;

	// Empleos
	private BigInteger debeReportarEmp;
	private BigInteger reportoEmp;
	private BigDecimal porcentaje;
	private Date fechaCorte;
	private BigInteger valor;

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

	public BigInteger getDebeReportarEmp() {
		return debeReportarEmp;
	}

	public void setDebeReportarEmp(BigInteger debeReportarEmp) {
		this.debeReportarEmp = debeReportarEmp;
	}

	public BigInteger getReportoEmp() {
		return reportoEmp;
	}

	public void setReportoEmp(BigInteger reportoEmp) {
		this.reportoEmp = reportoEmp;
	}

	public BigDecimal getPorcentaje() {
		if (debeReportarEmp != null && debeReportarEmp.compareTo(BigInteger.ZERO) != 0) {
			if (reportoEmp == null) {
				reportoEmp = BigInteger.ZERO;
			}
			setPorcentaje(BigDecimal.valueOf(reportoEmp.doubleValue() / debeReportarEmp.doubleValue()));
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

	public BigInteger getValor() {
		return valor;
	}

	public void setValor(BigInteger valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Empleo [codCifrasEmpleoPublico=" + codCifrasEmpleoPublico + ", etiqueta=" + etiqueta
				+ ", debeReportarEmp=" + debeReportarEmp + ", reportoEmp=" + reportoEmp + ", porcentaje=" + porcentaje
				+ ", fechaCorte=" + fechaCorte + ", valor=" + valor + "]";
	}
}
