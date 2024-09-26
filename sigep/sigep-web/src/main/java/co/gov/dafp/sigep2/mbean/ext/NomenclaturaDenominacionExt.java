package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.entities.NomenclaturaDenominacion;

public class NomenclaturaDenominacionExt extends NomenclaturaDenominacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal nuevaAsignacionSalarial;
	
	private boolean lbOrigenEquivalencia;
	
	private boolean lbDestinoEquivalencia;
	
	private Long codEntidad;
	
	private String nombreCargo;
	
	private String nivelCargo;
	private String gradoCargo;
	private String codigoCargo;
	private boolean blnFlgActivoParticular;
	

	public BigDecimal getNuevaAsignacionSalarial() {
		return nuevaAsignacionSalarial;
	}

	public void setNuevaAsignacionSalarial(BigDecimal nuevaAsignacionSalarial) {
		this.nuevaAsignacionSalarial = nuevaAsignacionSalarial;
	}

	public boolean isLbOrigenEquivalencia() {
		return lbOrigenEquivalencia;
	}

	public void setLbOrigenEquivalencia(boolean lbOrigenEquivalencia) {
		this.lbOrigenEquivalencia = lbOrigenEquivalencia;
	}

	public boolean isLbDestinoEquivalencia() {
		return lbDestinoEquivalencia;
	}

	public void setLbDestinoEquivalencia(boolean lbDestinoEquivalencia) {
		this.lbDestinoEquivalencia = lbDestinoEquivalencia;
	}

	/**
	 * @return the codEntidad
	 */
	public Long getCodEntidad() {
		return codEntidad;
	}

	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	/**
	 * @return the nombreCargo
	 */
	public String getNombreCargo() {
		return nombreCargo;
	}

	/**
	 * @param nombreCargo the nombreCargo to set
	 */
	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	/**
	 * @return the nivelCargo
	 */
	public String getNivelCargo() {
		return nivelCargo;
	}

	/**
	 * @param nivelCargo the nivelCargo to set
	 */
	public void setNivelCargo(String nivelCargo) {
		this.nivelCargo = nivelCargo;
	}

	/**
	 * @return the gradoCargo
	 */
	public String getGradoCargo() {
		return gradoCargo;
	}

	/**
	 * @param gradoCargo the gradoCargo to set
	 */
	public void setGradoCargo(String gradoCargo) {
		this.gradoCargo = gradoCargo;
	}


	/**
	 * @return the blnFlgActivoParticular
	 */
	public boolean isBlnFlgActivoParticular() {
		if(this.getFlgActivoParticular() == 1) 
			blnFlgActivoParticular = true;
		else
			blnFlgActivoParticular = false;
		return blnFlgActivoParticular;
	}

	/**
	 * @return the codigoCargo
	 */
	public String getCodigoCargo() {
		return codigoCargo;
	}

	/**
	 * @param codigoCargo the codigoCargo to set
	 */
	public void setCodigoCargo(String codigoCargo) {
		this.codigoCargo = codigoCargo;
	}

	/**
	 * @param blnFlgActivoParticular the blnFlgActivoParticular to set
	 */
	public void setBlnFlgActivoParticular(boolean blnFlgActivoParticular) {
		
		if(blnFlgActivoParticular)
			this.setFlgActivoParticular((short)1);
		else
			this.setFlgActivoParticular((short)0);
	}	

}
