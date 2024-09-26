package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase que implementa los registros de Acuerdos de gestión, Bienestar e
 * Incentivos, Capacitación, Horarios flexibles, Teletrabajo, Bilingüismo de la
 * Tabla de Resultados
 */
public class EstrategiaTalentoHumano extends ErrorMensajes {
	private static final long serialVersionUID = 3650867427259442236L;

	private long codCifrasEmpleoPublico;

	private String etiqueta;

	// Acuerdos de gestión, Bienestar e Incentivos, Capacitación, Horarios
	// flexibles, Teletrabajo, Bilingüismo
	private BigInteger debeReportar;
	private BigInteger reporto;
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

	public BigInteger getDebeReportar() {
		return debeReportar;
	}

	public void setDebeReportar(BigInteger debeReportar) {
		this.debeReportar = debeReportar;
	}

	public BigInteger getReporto() {
		return reporto;
	}

	public void setReporto(BigInteger reporto) {
		this.reporto = reporto;
	}

	public BigDecimal getPorcentaje() {
		if (debeReportar != null && debeReportar.compareTo(BigInteger.ZERO) != 0) {
			if (reporto == null) {
				reporto = BigInteger.ZERO;
			}
			setPorcentaje(BigDecimal.valueOf(reporto.doubleValue() / debeReportar.doubleValue()));
		}
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
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
		return "EstrategiaTalentoHumano [codCifrasEmpleoPublico=" + codCifrasEmpleoPublico + ", etiqueta=" + etiqueta
				+ ", debeReportar=" + debeReportar + ", reporto=" + reporto + ", porcentaje=" + porcentaje
				+ ", fechaCorte=" + fechaCorte + ", valor=" + valor + "]";
	}
}
