/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import co.gov.dafp.sigep2.bean.ext.ContratoExt;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de enviar al portal los detalles de la hoja de vida del servidor publico
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: MArtes 26 de Junio de 2018
*/
public class HojaVidaPortal extends ErrorMensajes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8075890938157705789L;
	
	private BigDecimal codPersona;
	// variable para almacenar el nombre del funcionario
	private String nombreFuncionario;
	private String nombres;
	private String apellidos;
	// variable para almaenar la url de la imagen del funcionario
	private String fotoUrl;
	// variable para almacenar la denominacion del cargo que ocupa
	private String denominacionCargo;
	// variable para almacenar el nombre de ka entidad 
	private String nombreEntidad;
	// variable pra almacenar el correo electronico del funcionaario
	private String correoElectronico;
	// variable para almacenar el telefono de la oficina
	private String numeroTelefonoOfcina;
	// variable para almacenar el numero de fax de la oficina
	private String numeroFax;
	// variable para almacenar el nombre del pais donde nacio el funcionario
	private String nombrePaisNacimiento;
	// variable para almacenar el nombre del departamento donde nacio el funcionario
	private String nombreDepartamentoNacimiento;
	// variable para almacenar el nombre del municipio donde nacio el funcionario
	private String nombreMunicipioNacimiento;
	// variable para guardar el tipo de vinculacion
	private String nombreTipoVinculacion;
	// variable para el almacenamiento del nombre de la planta
	private String nombrePlanta;
	// variable para almacenar el listado de su experiencia laboral
	private List<ExperianciaLaboralPortal> experenciaLaboral;
	// variable para almacenar el listado de su educaicon
	private List<FormacionProfesionalPortal> educacionFormal;
	// variable para almacenar el listado de las escalas salariales de la entidad
	private List<RangoSalariosPortal> escalaSalarial;
	// variable para almacenar los contratos que el funcioanrio tiene activos con el esatdo
	private List<ContratoExt> contratos;
	
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
	 * @return the denominacionCargo
	 */
	public String getDenominacionCargo() {
		return denominacionCargo;
	}
	/**
	 * @param denominacionCargo the denominacionCargo to set
	 */
	public void setDenominacionCargo(String denominacionCargo) {
		this.denominacionCargo = denominacionCargo;
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
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	/**
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	/**
	 * @return the numeroTelefonoOfcina
	 */
	public String getNumeroTelefonoOfcina() {
		return numeroTelefonoOfcina;
	}
	/**
	 * @param numeroTelefonoOfcina the numeroTelefonoOfcina to set
	 */
	public void setNumeroTelefonoOfcina(String numeroTelefonoOfcina) {
		this.numeroTelefonoOfcina = numeroTelefonoOfcina;
	}
	/**
	 * @return the numeroFax
	 */
	public String getNumeroFax() {
		return numeroFax;
	}
	/**
	 * @param numeroFax the numeroFax to set
	 */
	public void setNumeroFax(String numeroFax) {
		this.numeroFax = numeroFax;
	}
	/**
	 * @return the nombrePaisNacimienti
	 */
	public String getNombrePaisNacimiento() {
		return nombrePaisNacimiento;
	}
	/**
	 * @param nombrePaisNacimienti the nombrePaisNacimienti to set
	 */
	public void setNombrePaisNacimiento(String nombrePaisNacimiento) {
		this.nombrePaisNacimiento = nombrePaisNacimiento;
	}
	/**
	 * @return the nombreDepartamentoNacimiento
	 */
	public String getNombreDepartamentoNacimiento() {
		return nombreDepartamentoNacimiento;
	}
	/**
	 * @param nombreDepartamentoNacimiento the nombreDepartamentoNacimiento to set
	 */
	public void setNombreDepartamentoNacimiento(String nombreDepartamentoNacimiento) {
		this.nombreDepartamentoNacimiento = nombreDepartamentoNacimiento;
	}
	/**
	 * @return the nombreMunicipioNacimiento
	 */
	public String getNombreMunicipioNacimiento() {
		return nombreMunicipioNacimiento;
	}
	/**
	 * @param nombreMunicipioNacimiento the nombreMunicipioNacimiento to set
	 */
	public void setNombreMunicipioNacimiento(String nombreMunicipioNacimiento) {
		this.nombreMunicipioNacimiento = nombreMunicipioNacimiento;
	}
	/**
	 * @return the experenciaLaborl
	 */
	public List<ExperianciaLaboralPortal> getExperenciaLaboral() {
		return experenciaLaboral;
	}
	/**
	 * @param experenciaLaborl the experenciaLaborl to set
	 */
	public void setExperenciaLaboral(List<ExperianciaLaboralPortal> experenciaLaboral) {
		this.experenciaLaboral = experenciaLaboral;
	}
	/**
	 * @return the educacionFormal
	 */
	public List<FormacionProfesionalPortal> getEducacionFormal() {
		return educacionFormal;
	}
	/**
	 * @param educacionFormal the educacionFormal to set
	 */
	public void setEducacionFormal(List<FormacionProfesionalPortal> educacionFormal) {
		this.educacionFormal = educacionFormal;
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
	 * @return the contratos
	 */
	public List<ContratoExt> getContratos() {
		return contratos;
	}
	/**
	 * @param contratos the contratos to set
	 */
	public void setContratos(List<ContratoExt> contratos) {
		this.contratos = contratos;
	}
	/**
	 * @return the nombreTipovinculacion
	 */
	public String getNombreTipoVinculacion() {
		return nombreTipoVinculacion;
	}
	/**
	 * @param nombreTipovinculacion the nombreTipovinculacion to set
	 */
	public void setNombreTipoVinculacion(String nombreTipoVinculacion) {
		this.nombreTipoVinculacion = nombreTipoVinculacion;
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
	 * @return the escalaSalarial
	 */
	public List<RangoSalariosPortal> getEscalaSalarial() {
		return escalaSalarial;
	}
	/**
	 * @param escalaSalarial the escalaSalarial to set
	 */
	public void setEscalaSalarial(List<RangoSalariosPortal> escalaSalarial) {
		this.escalaSalarial = escalaSalarial;
	}
}
