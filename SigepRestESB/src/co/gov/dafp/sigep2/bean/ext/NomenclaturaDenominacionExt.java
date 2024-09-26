/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.NomenclaturaDenominacion;

/**
 * @author Maria Alejandra Colorado
 *
 */
public class NomenclaturaDenominacionExt extends NomenclaturaDenominacion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1065788074608719472L;
	
	
	private Long 	codEntidad;
	private String 	nombreCargo;
	private String 	nivelCargo;
	private String 	gradoCargo;
	private Integer limitIni;
	private Integer limitFin;
	private Integer total;
	private String 	codigoCargo;
	private Long 	codNomenclaturaAsociada;
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
	 * @return the limitIni
	 */
	public Integer getLimitIni() {
		return limitIni;
	}
	/**
	 * @param limitIni the limitIni to set
	 */
	public void setLimitIni(Integer limitIni) {
		this.limitIni = limitIni;
	}
	/**
	 * @return the limitFin
	 */
	public Integer getLimitFin() {
		return limitFin;
	}
	/**
	 * @param limitFin the limitFin to set
	 */
	public void setLimitFin(Integer limitFin) {
		this.limitFin = limitFin;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
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
	 * @return the codNomenclaturaAsociada
	 */
	public Long getCodNomenclaturaAsociada() {
		return codNomenclaturaAsociada;
	}
	/**
	 * @param codNomenclaturaAsociada the codNomenclaturaAsociada to set
	 */
	public void setCodNomenclaturaAsociada(Long codNomenclaturaAsociada) {
		this.codNomenclaturaAsociada = codNomenclaturaAsociada;
	}
}
