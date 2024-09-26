/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de responder los Resultados de busquedas de Servidores Publicos desde el Portal
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: MArtes 26 de Junio de 2018
*/
public class Resultados implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7711847193186145193L;
	
	// Variable pra almacenar el id de la persona
	private BigDecimal codPersona;
	// Variable pra almacenar la url de la imagen de la persona
	private String fotoUrl;
	// Variable pra almacenar el id de la Entidad
	private BigDecimal codEntidad;
	// Variable pra almacenar el id del departamento
	private BigDecimal codDepartamento;
	// Variable pra almacenar el id del municipio
	private BigDecimal codMunicipio;
	// Variable pra almacenar el id del tipo de contrato
	private BigDecimal codTipoContrato;
	private BigDecimal codDenominacion;
	private BigDecimal codNivelJerarquico;
	// Variable pra almacenar el nombre de la persona
	private String nombreFuncionario;
	private String nombres;
	private String apellidos;
	// Variable pra almacenar el nombre de la entidad
	private String nombreEntidad;
	// Variable pra almacenar el nombre del Departamento
	private String nombreDepartamento;
	// Variable pra almacenar el nombre del municipio
	private String nombreMunicipio;
	// Variable pra almacenar el nombre el tipo de contrato
	private String nombreTipoContrato;
	// Variable pra almacenar el email del cunadionario
	private String emailFuncionario;
	// Variable pra almacenar el telefono del funcionario
	private String telefonoFuncionario;
	// Variable  para almacenar el totoal de registros temporalmente
	private Integer totalRegistros;
	// Variable  para almacenar el genero de la persona
	private String genero;
	private Short expuestoPoliticamente;
	private String cargo;
	private String nombreNivelJerarquico;
	private String nombrePlanta;
	private String numeroContrato;
	private BigDecimal codCargoReferencia;
	private String nombreCargoReferencia;

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
	 * @return the nombreFuncionario
	 */
	public String getNombreFuncionario() {
		return nombreFuncionario;
	}

	/**
	 * @param nombreFuncionario the nombreFuncionario to set
	 */
	public void setNombreFuncionario(String nombreFuncionario) {
		this.nombreFuncionario = nombreFuncionario;
	}

	/**
	 * @return the nombreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}

	/**
	 * @param nombreEntidad the nombreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	/**
	 * @return the nombreDepartamento
	 */
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	/**
	 * @param nombreDepartamento the nombreDepartamento to set
	 */
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	/**
	 * @return the nombreMunicipio
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	/**
	 * @param nombreMunicipio the nombreMunicipio to set
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	/**
	 * @return the nombreTipoContrato
	 */
	public String getNombreTipoContrato() {
		return nombreTipoContrato;
	}

	/**
	 * @param nombreTipoContrato the nombreTipoContrato to set
	 */
	public void setNombreTipoContrato(String nombreTipoContrato) {
		this.nombreTipoContrato = nombreTipoContrato;
	}

	/**
	 * @return the emailFuncionario
	 */
	public String getEmailFuncionario() {
		return emailFuncionario;
	}

	/**
	 * @param emailFuncionario the emailFuncionario to set
	 */
	public void setEmailFuncionario(String emailFuncionario) {
		this.emailFuncionario = emailFuncionario;
	}

	/**
	 * @return the telefonoFuncionario
	 */
	public String getTelefonoFuncionario() {
		return telefonoFuncionario;
	}

	/**
	 * @param telefonoFuncionario the telefonoFuncionario to set
	 */
	public void setTelefonoFuncionario(String telefonoFuncionario) {
		this.telefonoFuncionario = telefonoFuncionario;
	}

	/**
	 * @return the totalRegistros
	 */
	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
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
	 * @return the codTipoContrado
	 */
	public BigDecimal getCodTipoContrato() {
		return codTipoContrato;
	}

	/**
	 * @param codTipoContrado the codTipoContrado to set
	 */
	public void setCodTipoContrato(BigDecimal codTipoContrato) {
		this.codTipoContrato = codTipoContrato;
	}

	/**
	 * @return the fotoUrl
	 */
	public String getFotoUrl() {
		return fotoUrl;
	}

	/**
	 * @param fotoUrl the fotoUrl to set
	 */
	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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
	 * @return the nombreNivelJerarquico
	 */
	public String getNombreNivelJerarquico() {
		return nombreNivelJerarquico;
	}

	/**
	 * @param nombreNivelJerarquico the nombreNivelJerarquico to set
	 */
	public void setNombreNivelJerarquico(String nombreNivelJerarquico) {
		this.nombreNivelJerarquico = nombreNivelJerarquico;
	}

	/**
	 * @return the cargo
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * @param cargo the cargo to set
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the nombrePlanta
	 */
	public String getNombrePlanta() {
		return nombrePlanta;
	}

	/**
	 * @param nombrePlanta the nombrePlanta to set
	 */
	public void setNombrePlanta(String nombrePlanta) {
		this.nombrePlanta = nombrePlanta;
	}

	/**
	 * @return the numeroContrato
	 */
	public String getNumeroContrato() {
		return numeroContrato;
	}

	/**
	 * @param numeroContrato the numeroContrato to set
	 */
	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
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

	/**
	 * @return the nombreCargoReferencia
	 */
	public String getNombreCargoReferencia() {
		return nombreCargoReferencia;
	}

	/**
	 * @param nombreCargoReferencia the nombreCargoReferencia to set
	 */
	public void setNombreCargoReferencia(String nombreCargoReferencia) {
		this.nombreCargoReferencia = nombreCargoReferencia;
	}
	
}
