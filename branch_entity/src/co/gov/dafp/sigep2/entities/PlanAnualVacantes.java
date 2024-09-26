package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase que implementa los registros del Plan Anual de Vacante de la Tabla de
 * Resultados
 */
public class PlanAnualVacantes extends ErrorMensajes {
	private static final long serialVersionUID = 1101399304326673680L;

	private long codCifrasEmpleoPublico;

	private String etiqueta;

	// Plan Anual de Vacantes
	private BigInteger debeReportarPAV;
	private BigInteger reportoPAV;
	private BigDecimal porcentaje;
	private Date fechaCorte;
	private BigInteger asesor;
	private BigInteger profesional;
	private BigInteger tecnico;
	private BigInteger asistencial;
	private BigInteger total;

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

	public BigInteger getDebeReportarPAV() {
		return debeReportarPAV;
	}

	public void setDebeReportarPAV(BigInteger debeReportarPAV) {
		this.debeReportarPAV = debeReportarPAV;
	}

	public BigInteger getReportoPAV() {
		return reportoPAV;
	}

	public void setReportoPAV(BigInteger reportoPAV) {
		this.reportoPAV = reportoPAV;
	}

	public BigDecimal getPorcentaje() {
		if (debeReportarPAV != null && debeReportarPAV.compareTo(BigInteger.ZERO) != 0) {
			if (reportoPAV == null) {
				reportoPAV = BigInteger.ZERO;
			}
			setPorcentaje(BigDecimal.valueOf(reportoPAV.doubleValue() / debeReportarPAV.doubleValue()));
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

	public BigInteger getAsesor() {
		return asesor;
	}

	public void setAsesor(BigInteger asesor) {
		this.asesor = asesor;
	}

	public BigInteger getProfesional() {
		return profesional;
	}

	public void setProfesional(BigInteger profesional) {
		this.profesional = profesional;
	}

	public BigInteger getTecnico() {
		return tecnico;
	}

	public void setTecnico(BigInteger tecnico) {
		this.tecnico = tecnico;
	}

	public BigInteger getAsistencial() {
		return asistencial;
	}

	public void setAsistencial(BigInteger asistencial) {
		this.asistencial = asistencial;
	}

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PlanAnualVacantes [codCifrasEmpleoPublico=" + codCifrasEmpleoPublico + ", etiqueta=" + etiqueta
				+ ", debeReportarPAV=" + debeReportarPAV + ", reportoPAV=" + reportoPAV + ", porcentaje=" + porcentaje
				+ ", fechaCorte=" + fechaCorte + ", asesor=" + asesor + ", profesional=" + profesional + ", tecnico="
				+ tecnico + ", asistencial=" + asistencial + ", total=" + total + "]";
	}
}
