/**
 * @Author Jose Viscaya
 * PlantaPersonaUtlUanExt.java
 * 15 feb. 2019
 */
package co.gov.dafp.sigep2.bean.ext;

import java.math.BigDecimal;

import co.gov.dafp.sigep2.bean.PlantaPersonaUtlUan;

/**
 * @author Jose Viscaya
 * PlantaPersonaUtlUanExt.java
 * 15 feb. 2019
 * SigepRestESB
 */
public class PlantaPersonaUtlUanExt extends PlantaPersonaUtlUan {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2641801780406544908L;
	
	private BigDecimal 	codEntidad;
	private String 		nombreResponsable;
	private BigDecimal 	salarioGastado;
	private BigDecimal 	montoMaximo;
	private int 		cargosOcupados;
	private int			cantidadColaboradoresUTL;
	private BigDecimal  cantidadSalariosUTL;
	private BigDecimal 	codPersonaVinculada;
	private BigDecimal 	codPersonaResponsable;
	private Short		flgGuardadoParcialPlanta;
	
	/**
	 * @return the codEntidad
	 */
	public BigDecimal getCodEntidad() {
		return codEntidad;
	}

	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(BigDecimal codEntidad) {
		this.codEntidad = codEntidad;
	}

	/**
	 * @return the nombreResponsable
	 */
	public String getNombreResponsable() {
		return nombreResponsable;
	}

	/**
	 * @param nombreResponsable the nombreResponsable to set
	 */
	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}

	/**
	 * @return the salarioGastado
	 */
	public BigDecimal getSalarioGastado() {
		return salarioGastado;
	}

	/**
	 * @param salarioGastado the salarioGastado to set
	 */
	public void setSalarioGastado(BigDecimal salarioGastado) {
		this.salarioGastado = salarioGastado;
	}

	/**
	 * @return the cargosOcupados
	 */
	public int getCargosOcupados() {
		return cargosOcupados;
	}

	/**
	 * @param cargosOcupados the cargosOcupados to set
	 */
	public void setCargosOcupados(int cargosOcupados) {
		this.cargosOcupados = cargosOcupados;
	}

	/**
	 * @return the montoMaximo
	 */
	public BigDecimal getMontoMaximo() {
		return montoMaximo;
	}

	/**
	 * @param montoMaximo the montoMaximo to set
	 */
	public void setMontoMaximo(BigDecimal montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	/**
	 * @return the cantidadColaboradoresUTL
	 */
	public int getCantidadColaboradoresUTL() {
		return cantidadColaboradoresUTL;
	}

	/**
	 * @param cantidadColaboradoresUTL the cantidadColaboradoresUTL to set
	 */
	public void setCantidadColaboradoresUTL(int cantidadColaboradoresUTL) {
		this.cantidadColaboradoresUTL = cantidadColaboradoresUTL;
	}

	/**
	 * @return the codPersonaVinculada
	 */
	public BigDecimal getCodPersonaVinculada() {
		return codPersonaVinculada;
	}

	/**
	 * @param codPersonaVinculada the codPersonaVinculada to set
	 */
	public void setCodPersonaVinculada(BigDecimal codPersonaVinculada) {
		this.codPersonaVinculada = codPersonaVinculada;
	}

	/**
	 * @return the codPersonaResponsable
	 */
	public BigDecimal getCodPersonaResponsable() {
		return codPersonaResponsable;
	}

	/**
	 * @param codPersonaResponsable the codPersonaResponsable to set
	 */
	public void setCodPersonaResponsable(BigDecimal codPersonaResponsable) {
		this.codPersonaResponsable = codPersonaResponsable;
	}

	/**
	 * @return the flgGuardadoParcialPlanta
	 */
	public Short getFlgGuardadoParcialPlanta() {
		return flgGuardadoParcialPlanta;
	}

	/**
	 * @param flgGuardadoParcialPlanta the flgGuardadoParcialPlanta to set
	 */
	public void setFlgGuardadoParcialPlanta(Short flgGuardadoParcialPlanta) {
		this.flgGuardadoParcialPlanta = flgGuardadoParcialPlanta;
	}

	public BigDecimal getCantidadSalariosUTL() {
		return cantidadSalariosUTL;
	}

	public void setCantidadSalariosUTL(BigDecimal cantidadSalariosUTL) {
		this.cantidadSalariosUTL = cantidadSalariosUTL;
	}
	
	
}
