/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de transportar los criterio de la busqueda del servidor publico
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: MArtes 26 de Junio de 2018
*/
public class BusquedaPortal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 514937135413790738L;
	// Variable que almacena el criterio de busqueda
	private String criterioBusqueda;
	private String criterio1;
	private String criterio2;
	private String criterio3;
	private String criterio4;
	private String criterio5;
	// variable que almacena el valor inicial de el envio de registris 
	private int limiteInicial;
	// varable que almacena el total de registros solicitados de la consulta
	private int limiteFinal;
	// Variable para almaenar el id de le entidad
	private BigDecimal codEntidad;
	// variable para almacenar el id de la persona
	private BigDecimal codPersona;
	// Variable para almaenar el id del departamento
	private BigDecimal codDepartamento;
	// Variable para almaenar el id del municipio
	private BigDecimal codMunicipio;
	// Variable para almaenar el id del tipo contrato
	private BigDecimal codTipoContrato;
	// Variable para almaenar el id del tipo contrato
	private BigDecimal codTipoIdentificacion;
	private BigDecimal codNivelJerarquico;
	private BigDecimal codCargoReferencia;
	
	private BigDecimal codDenominacion;
	
	private BigDecimal codPais;
	// variabe para recibir el prametro de numero de identificacion
	private String numeroIdentificacion;
	private String nombreDenominacion;
	// variable para almacenar la opcion de expuesto politicamente
	private Short expuestoPoliticamente;
	
	

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
	 * @return the codDepartamento
	 */
	public BigDecimal getCodDepartamento() {
		return codDepartamento;
	}

	/**
	 * @param codDepartamento the codDepartamento to set
	 */
	public void setCodDepartamento(BigDecimal codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	/**
	 * @return the codMunicipio
	 */
	public BigDecimal getCodMunicipio() {
		return codMunicipio;
	}

	/**
	 * @param codMunicipio the codMunicipio to set
	 */
	public void setCodMunicipio(BigDecimal codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	/**
	 * @return the codTipoContrato
	 */
	public BigDecimal getCodTipoContrato() {
		return codTipoContrato;
	}

	/**
	 * @param codTipoContrato the codTipoContrato to set
	 */
	public void setCodTipoContrato(BigDecimal codTipoContrato) {
		this.codTipoContrato = codTipoContrato;
	}

	/**
	 * @return the criterio
	 */
	public String getCriterioBusqueda() {
		return criterioBusqueda;
	}

	/**
	 * @param criterio the criterio to set
	 */
	public void setCriterioBusqueda(String criterioBusqueda) {
		this.criterioBusqueda = criterioBusqueda;
	}

	/**
	 * @return the limiteInicial
	 */
	public int getLimiteInicial() {
		return limiteInicial;
	}

	/**
	 * @param limiteInicial the limiteInicial to set
	 */
	public void setLimiteInicial(int limiteInicial) {
		this.limiteInicial = limiteInicial;
	}

	/**
	 * @return the limiteFinal
	 */
	public int getLimiteFinal() {
		return limiteFinal;
	}

	/**
	 * @param limiteFinal the limiteFinal to set
	 */
	public void setLimiteFinal(int limiteFinal) {
		this.limiteFinal = limiteFinal;
	}

	/**
	 * @return the codPersona
	 */
	public BigDecimal getCodPersona() {
		return codPersona;
	}

	/**
	 * @param codPersona the codPersona to set
	 */
	public void setCodPersona(BigDecimal codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * @return the codTipoIdentificacion
	 */
	public BigDecimal getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	/**
	 * @param codTipoIdentificacion the codTipoIdentificacion to set
	 */
	public void setCodTipoIdentificacion(BigDecimal codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	/**
	 * @return the numeroIdentificacio
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacio the numeroIdentificacio to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * @return the expuestoPoliticamente
	 */
	public Short getExpuestoPoliticamente() {
		return expuestoPoliticamente;
	}

	/**
	 * @param expuestoPoliticamente the expuestoPoliticamente to set
	 */
	public void setExpuestoPoliticamente(Short expuestoPoliticamente) {
		this.expuestoPoliticamente = expuestoPoliticamente;
	}

	/**
	 * @return the codPais
	 */
	public BigDecimal getCodPais() {
		return codPais;
	}

	/**
	 * @param codPais the codPais to set
	 */
	public void setCodPais(BigDecimal codPais) {
		this.codPais = codPais;
	}

	/**
	 * @return the codDenominacion
	 */
	public BigDecimal getCodDenominacion() {
		return codDenominacion;
	}

	/**
	 * @param codDenominacion the codDenominacion to set
	 */
	public void setCodDenominacion(BigDecimal codDenominacion) {
		this.codDenominacion = codDenominacion;
	}

	/**
	 * @return the nombreDenominacion
	 */
	public String getNombreDenominacion() {
		return nombreDenominacion;
	}

	/**
	 * @param nombreDenominacion the nombreDenominacion to set
	 */
	public void setNombreDenominacion(String nombreDenominacion) {
		this.nombreDenominacion = nombreDenominacion;
	}


	/**
	 * @return the criterio1
	 */
	public String getCriterio1() {
		return criterio1;
	}

	/**
	 * @param criterio1 the criterio1 to set
	 */
	public void setCriterio1(String criterio1) {
		this.criterio1 = criterio1;
	}

	/**
	 * @return the criterio2
	 */
	public String getCriterio2() {
		return criterio2;
	}

	/**
	 * @param criterio2 the criterio2 to set
	 */
	public void setCriterio2(String criterio2) {
		this.criterio2 = criterio2;
	}

	/**
	 * @return the criterio3
	 */
	public String getCriterio3() {
		return criterio3;
	}

	/**
	 * @param criterio3 the criterio3 to set
	 */
	public void setCriterio3(String criterio3) {
		this.criterio3 = criterio3;
	}

	/**
	 * @return the criterio4
	 */
	public String getCriterio4() {
		return criterio4;
	}

	/**
	 * @param criterio4 the criterio4 to set
	 */
	public void setCriterio4(String criterio4) {
		this.criterio4 = criterio4;
	}

	/**
	 * @return the criterio5
	 */
	public String getCriterio5() {
		return criterio5;
	}

	/**
	 * @param criterio5 the criterio5 to set
	 */
	public void setCriterio5(String criterio5) {
		this.criterio5 = criterio5;
	}

	/**
	 * @return the codNivelJerarquico
	 */
	public BigDecimal getCodNivelJerarquico() {
		return codNivelJerarquico;
	}

	/**
	 * @param codNivelJerarquico the codNivelJerarquico to set
	 */
	public void setCodNivelJerarquico(BigDecimal codNivelJerarquico) {
		this.codNivelJerarquico = codNivelJerarquico;
	}

	/**
	 * @return the codCargoReferencia
	 */
	public BigDecimal getCodCargoReferencia() {
		return codCargoReferencia;
	}

	/**
	 * @param codCargoReferencia the codCargoReferencia to set
	 */
	public void setCodCargoReferencia(BigDecimal codCargoReferencia) {
		this.codCargoReferencia = codCargoReferencia;
	}
}
