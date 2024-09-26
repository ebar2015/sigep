package co.gov.dafp.sigep2.entities;

import java.math.BigInteger;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase que implementa los registros de Resumen Cargos Publicos de la Tabla de
 * Resultados
 */
public class ResumenCargosPublicos extends ErrorMensajes {
	private static final long serialVersionUID = 2339708920617046832L;

	public static final String NIVEL1 = "nivel1";
	public static final String NIVEL1_1 = "nivel1-1";
	public static final String NIVEL1_3 = "nivel1-3";
	public static final String NIVEL1_4 = "nivel1-4";
	public static final String NIVEL1_5 = "nivel1-5";
	public static final String NIVEL1_6 = "nivel1-6";
	public static final String NIVEL2 = "nivel2";
	public static final String NIVEL3 = "nivel3";
	public static final String NIVEL4 = "nivel4";
	public static final String NIVEL5 = "nivel5";

	private long codCifrasEmpleoPublico;

	private String etiqueta;
	private String tabulacion = NIVEL2;

	// Resumen Cargos Publicos
	private Date fechaCorte;
	private BigInteger valorAdministrativosUniformados;
	private BigInteger valorDocentes;
	private BigInteger totalEmpleosPublicos;
	private BigInteger valorTrabajadoresOficiales;
	private BigInteger granTotal;
	private BigInteger valorRegimenPrivado;

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

	public String getTabulacion() {
		return tabulacion;
	}

	public void setTabulacion(String tabulacion) {
		this.tabulacion = tabulacion;
	}

	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public BigInteger getValorAdministrativosUniformados() {
		return valorAdministrativosUniformados;
	}

	public void setValorAdministrativosUniformados(BigInteger valorAdministrativosUniformados) {
		this.valorAdministrativosUniformados = valorAdministrativosUniformados;
	}

	public BigInteger getValorDocentes() {
		return valorDocentes;
	}

	public void setValorDocentes(BigInteger valorDocentes) {
		this.valorDocentes = valorDocentes;
	}

	public BigInteger getTotalEmpleosPublicos() {
		return totalEmpleosPublicos;
	}

	public void setTotalEmpleosPublicos(BigInteger totalEmpleosPublicos) {
		this.totalEmpleosPublicos = totalEmpleosPublicos;
	}

	public BigInteger getValorTrabajadoresOficiales() {
		return valorTrabajadoresOficiales;
	}

	public void setValorTrabajadoresOficiales(BigInteger valorTrabajadoresOficiales) {
		this.valorTrabajadoresOficiales = valorTrabajadoresOficiales;
	}

	public BigInteger getGranTotal() {
		return granTotal;
	}

	public void setGranTotal(BigInteger granTotal) {
		this.granTotal = granTotal;
	}

	public BigInteger getValorRegimenPrivado() {
		return valorRegimenPrivado;
	}

	public void setValorRegimenPrivado(BigInteger valorRegimenPrivado) {
		this.valorRegimenPrivado = valorRegimenPrivado;
	}
}
