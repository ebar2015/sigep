package co.gov.dafp.sigep2.bean.ext;

import java.math.BigDecimal;

import co.gov.dafp.sigep2.bean.Denominacion;

public class DenominacionExt extends Denominacion{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5885718191794048042L;

	private Long codNivelJerarquico;
	
	private BigDecimal codNomenclatura;
	
	private Long codEntidad;
	
	private String nivelCargo;
	
	public Long getCodNivelJerarquico() {
		return codNivelJerarquico;
	}

	public void setCodNivelJerarquico(Long codNivelJerarquico) {
		this.codNivelJerarquico = codNivelJerarquico;
	}

	/**
	 * @return the codNomenclatura
	 */
	public BigDecimal getCodNomenclatura() {
		return codNomenclatura;
	}

	/**
	 * @param codNomenclatura the codNomenclatura to set
	 */
	public void setCodNomenclatura(BigDecimal codNomenclatura) {
		this.codNomenclatura = codNomenclatura;
	}

	public Long getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	public String getNivelCargo() {
		return nivelCargo;
	}

	public void setNivelCargo(String nivelCargo) {
		this.nivelCargo = nivelCargo;
	}
	
}
