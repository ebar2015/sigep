package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase que implementa los registros del Seguimiento de la Tabla de Resultados
 */
public class Seguimiento extends ErrorMensajes {
	private static final long serialVersionUID = -5569128544455678962L;

	private long codCifrasEmpleoPublico;

	private String etiqueta;
	private String entidad;

	// Seguimiento
	private BigInteger debeReportarSeg;
	private BigInteger reportoSeg;
	private BigDecimal porcentaje;
	private Date fechaCorte;

	private BigDecimal valor;
	private List<BigDecimal> listaValor;

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

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public BigInteger getDebeReportarSeg() {
		return debeReportarSeg;
	}

	public void setDebeReportarSeg(BigInteger debeReportarSeg) {
		this.debeReportarSeg = debeReportarSeg;
	}

	public BigInteger getReportoSeg() {
		return reportoSeg;
	}

	public void setReportoSeg(BigInteger reportoSeg) {
		this.reportoSeg = reportoSeg;
	}

	public BigDecimal getPorcentaje() {
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

	public BigDecimal getValor() {
		if (valor == null) {
			valor = BigDecimal.ZERO;
		}
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<BigDecimal> getListaValor() {
		if (listaValor == null) {
			listaValor = new LinkedList<>();
		}
		return listaValor;
	}

	public void unsetListaValor() {
		this.listaValor = null;
	}
}
