/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.util.List;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de responder los filtros disponibles de busquedas de Servidores Publicos desde el Portal
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: MArtes 26 de Junio de 2018
*/
public class Filtros implements Serializable {

	private static final long serialVersionUID = -8716363102993520004L;
	
	// Variable para almacenar los datos disponibles por departamento
	private List<DepartamentoFiltro> departamento;
	// Variable para almacenar los datos disponibles por municipio
	private List<MunicipioFiltro> municipios;
	// Variable para almacenar los datos disponibles por entidad
	private List<InstitucionFiltro> entidad;
	// Variable para almacenar los datos disponibles por tipo de contratacion
	private List<TipoContratoFiltro> tipoContrato;
	// Variable para almacenar los datos disponibles por flg Expuesto politicament
	private List<ExpuestoPoliticamenteFiltro> expuestoP;
	// Variable para almacenar los datos disponibles por Nivel Jerarquico
	private List<NivelJerarquicoFiltro> nivelJerarquico;
	// Variable para almacenar los datos disponibles por Cargo de referencia
		private List<CargoReferenciaFiltro> cargoReferencia;
		
	/**
	 * @return the departamento
	 */
	public List<DepartamentoFiltro> getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(List<DepartamentoFiltro> departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the entidad
	 */
	public List<InstitucionFiltro> getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad the entidad to set
	 */
	public void setEntidad(List<InstitucionFiltro> entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the tipoContrato
	 */
	public List<TipoContratoFiltro> getTipoContrato() {
		return tipoContrato;
	}

	/**
	 * @param tipoContrato the tipoContrato to set
	 */
	public void setTipoContrato(List<TipoContratoFiltro> tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	/**
	 * @return the municipios
	 */
	public List<MunicipioFiltro> getMunicipios() {
		return municipios;
	}

	/**
	 * @param municipios the municipios to set
	 */
	public void setMunicipios(List<MunicipioFiltro> municipios) {
		this.municipios = municipios;
	}

	/**
	 * @return the expuestoP
	 */
	public List<ExpuestoPoliticamenteFiltro> getExpuestoP() {
		return expuestoP;
	}

	/**
	 * @param expuestoP the expuestoP to set
	 */
	public void setExpuestoP(List<ExpuestoPoliticamenteFiltro> expuestoP) {
		this.expuestoP = expuestoP;
	}

	/**
	 * @return the nivelJerarquico
	 */
	public List<NivelJerarquicoFiltro> getNivelJerarquico() {
		return nivelJerarquico;
	}

	/**
	 * @param nivelJerarquico the nivelJerarquico to set
	 */
	public void setNivelJerarquico(List<NivelJerarquicoFiltro> nivelJerarquico) {
		this.nivelJerarquico = nivelJerarquico;
	}

	/**
	 * @return the cargoReferencia
	 */
	public List<CargoReferenciaFiltro> getCargoReferencia() {
		return cargoReferencia;
	}

	/**
	 * @param cargoReferencia the cargoReferencia to set
	 */
	public void setCargoReferencia(List<CargoReferenciaFiltro> cargoReferencia) {
		this.cargoReferencia = cargoReferencia;
	}
}
