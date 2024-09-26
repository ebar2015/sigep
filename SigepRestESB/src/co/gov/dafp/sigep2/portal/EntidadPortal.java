/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de almacenar informacion de la Entidad Publicas seleccionada
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 28 de Junio de 2018
*/
public class EntidadPortal extends ErrorMensajes implements Serializable{
	private static final long serialVersionUID = -9187994616771625518L;
	
	// variable para almacenar el id de la entidad
	private BigDecimal codEntidad;
	// variable para guardar el nombe de la entidad
	private String nombreEntidad;
	// variable para guardar el correo de la entidad
	private String correoInstitucional;
	// variable para guardar la pagina web
	private String paginaWeb;
	private String codigoSigep;
	// variable para guardar la direccion de la entidad
	private String direccionFisica;
	// variable para guardar el codigo postal
	private String codigoPostal;
	// variable para guardar el indicativo de los telefonos
	private String indicativo;
	// variable para guardar e numero de telefono fijo
	private String telefono;
	// variable para guardar el nuemro de celular
	private String celular;
	// variable para guardar la personeria juridica
	private String personeriaJuridica;
	// variable para guardar el nombre del representante legal
	private String representanteLegal;
	// variable para guardar el orden de la entidad
	private String orden;
	// variable para guardar el sub Orden de la Entidad
	private String subOrden;
	// variable para guardar la clasificacion organica
	private String clasificacionOrganica;
	// variable para guardar el sector administrativo
	private String sectorAdministrativo;
	// variable para guardar si o no es entidad post conflicto
	private String entidadPostConflicto;
	// variable para guardar la naturaleza juridica
	private String naturalezaJuridica;
	// variable para guardar el nivel administrativo
	private String nivelAdministrativo;
	// variable para guardar el departamento de la entidad
	private String departamento;
	// variable para guardar el municipio de la entidad
	private String municipio;
	// variable para guardar la categoria de la entidad
	private String categoria;
	// variable para guardar la categoria DNP
	private String categoriaDnp;
	// variable para guardar el tipo de vinculacion
	private String tipoVinculacion;
	// variable para guardar la entidad ascrita 
	private String entidadAdscrita;
	// variable para guardar el sistema de carrera
	private String sistemaCarrera;
	// variable para guardar los regimenes salariales que posee
	private RegimenSalarial regimenSalarial;
	// variable para guardar los tipos de servidores que contiene
	private TipoServidorEntidad tipoServidorEntidad;
	// variable para guardar el nit
	private String nit;
	// variable para guardar el digito de verificacion
	private String digitoVerificacion;
	// variable para guardar el codigo DANE
	private String codigoDane;
	// variable para guardar el codigo CIUN
	private String codigoCuin;
	// variable para guardar la latitud de ubicacion georeferenciacion
	private String latitud;
	// variable para guardar la longitud de ubicacion georeferenciacion
	private String longitud;
	private String estadoEntidad;
	private String subEstadoEntidad;
	private String tipoEntidad;
	private List<RegimenSalarial> regimenEscalaSalarial;
	// variable para guardar las naturalezas del empleo de una entidad
	private List<NaturalezaEmpleo> naturalezaEmpleo;
	// variable para guardar los sistemas de carrera de una entidad
	private List<SistemaCarrera> sistemasCarrera;
	// variable para mostrar la totalidad de registros
	private Integer totalRegistros;
	
	private String tipoZona; /// OJO VALIDAR CAMPO PARA ENVIAR NO ESTA AUN 
	
	private String patrimonioPropio;

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
	 * @return the correoInstitucional
	 */
	public String getCorreoInstitucional() {
		return correoInstitucional;
	}

	/**
	 * @param correoInstitucional the correoInstitucional to set
	 */
	public void setCorreoInstitucional(String correoInstitucional) {
		this.correoInstitucional = correoInstitucional;
	}

	/**
	 * @return the paginaWeb
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * @param paginaWeb the paginaWeb to set
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	/**
	 * @return the direccionFisica
	 */
	public String getDireccionFisica() {
		return direccionFisica;
	}

	/**
	 * @param direccionFisica the direccionFisica to set
	 */
	public void setDireccionFisica(String direccionFisica) {
		this.direccionFisica = direccionFisica;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return the indicativo
	 */
	public String getIndicativo() {
		return indicativo;
	}

	/**
	 * @param indicativo the indicativo to set
	 */
	public void setIndicativo(String indicativo) {
		this.indicativo = indicativo;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return the personeriaJuridica
	 */
	public String getPersoneriaJuridica() {
		return personeriaJuridica;
	}

	/**
	 * @param personeriaJuridica the personeriaJuridica to set
	 */
	public void setPersoneriaJuridica(String personeriaJuridica) {
		this.personeriaJuridica = personeriaJuridica;
	}

	/**
	 * @return the representanteLegal
	 */
	public String getRepresentanteLegal() {
		return representanteLegal;
	}

	/**
	 * @param representanteLegal the representanteLegal to set
	 */
	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}

	/**
	 * @return the subOrden
	 */
	public String getSubOrden() {
		return subOrden;
	}

	/**
	 * @param subOrden the subOrden to set
	 */
	public void setSubOrden(String subOrden) {
		this.subOrden = subOrden;
	}

	/**
	 * @return the clasoificacionOrganica
	 */
	public String getClasificacionOrganica() {
		return clasificacionOrganica;
	}

	/**
	 * @param clasoificacionOrganica the clasoificacionOrganica to set
	 */
	public void setClasificacionOrganica(String clasificacionOrganica) {
		this.clasificacionOrganica = clasificacionOrganica;
	}

	/**
	 * @return the sectorAdministrativo
	 */
	public String getSectorAdministrativo() {
		return sectorAdministrativo;
	}

	/**
	 * @param sectorAdministrativo the sectorAdministrativo to set
	 */
	public void setSectorAdministrativo(String sectorAdministrativo) {
		this.sectorAdministrativo = sectorAdministrativo;
	}

	/**
	 * @return the entidadPostConflicto
	 */
	public String getEntidadPostConflicto() {
		return entidadPostConflicto;
	}

	/**
	 * @param entidadPostConflicto the entidadPostConflicto to set
	 */
	public void setEntidadPostConflicto(String entidadPostConflicto) {
		this.entidadPostConflicto = entidadPostConflicto;
	}

	/**
	 * @return the naturalezaJuridica
	 */
	public String getNaturalezaJuridica() {
		return naturalezaJuridica;
	}

	/**
	 * @param naturalezaJuridica the naturalezaJuridica to set
	 */
	public void setNaturalezaJuridica(String naturalezaJuridica) {
		this.naturalezaJuridica = naturalezaJuridica;
	}


	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the categoriaDnp
	 */
	public String getCategoriaDnp() {
		return categoriaDnp;
	}

	/**
	 * @param categoriaDnp the categoriaDnp to set
	 */
	public void setCategoriaDnp(String categoriaDnp) {
		this.categoriaDnp = categoriaDnp;
	}

	/**
	 * @return the tipoVinculacion
	 */
	public String getTipoVinculacion() {
		return tipoVinculacion;
	}

	/**
	 * @param tipoVinculacion the tipoVinculacion to set
	 */
	public void setTipoVinculacion(String tipoVinculacion) {
		this.tipoVinculacion = tipoVinculacion;
	}

	/**
	 * @return the entidadAdscrita
	 */
	public String getEntidadAdscrita() {
		return entidadAdscrita;
	}

	/**
	 * @param entidadAdscrita the entidadAdscrita to set
	 */
	public void setEntidadAdscrita(String entidadAdscrita) {
		this.entidadAdscrita = entidadAdscrita;
	}

	/**
	 * @return the sistemaCarrera
	 */
	public String getSistemaCarrera() {
		return sistemaCarrera;
	}

	/**
	 * @param sistemaCarrera the sistemaCarrera to set
	 */
	public void setSistemaCarrera(String sistemaCarrera) {
		this.sistemaCarrera = sistemaCarrera;
	}

	/**
	 * @return the regimenSalarial
	 */
	public RegimenSalarial getRegimenSalarial() {
		return regimenSalarial;
	}

	/**
	 * @param regimenSalarial the regimenSalarial to set
	 */
	public void setRegimenSalarial(RegimenSalarial regimenSalarial) {
		this.regimenSalarial = regimenSalarial;
	}

	/**
	 * @return the tipoServidorEntidad
	 */
	public TipoServidorEntidad getTipoServidorEntidad() {
		return tipoServidorEntidad;
	}

	/**
	 * @param tipoServidorEntidad the tipoServidorEntidad to set
	 */
	public void setTipoServidorEntidad(TipoServidorEntidad tipoServidorEntidad) {
		this.tipoServidorEntidad = tipoServidorEntidad;
	}

	/**
	 * @return the nit
	 */
	public String getNit() {
		return nit;
	}

	/**
	 * @param nit the nit to set
	 */
	public void setNit(String nit) {
		this.nit = nit;
	}

	/**
	 * @return the digitoVerificacion
	 */
	public String getDigitoVerificacion() {
		return digitoVerificacion;
	}

	/**
	 * @param digitoVerificacion the digitoVerificacion to set
	 */
	public void setDigitoVerificacion(String digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	/**
	 * @return the codigoDane
	 */
	public String getCodigoDane() {
		return codigoDane;
	}

	/**
	 * @param codigoDane the codigoDane to set
	 */
	public void setCodigoDane(String codigoDane) {
		this.codigoDane = codigoDane;
	}

	/**
	 * @return the codigoCuin
	 */
	public String getCodigoCuin() {
		return codigoCuin;
	}

	/**
	 * @param codigoCuin the codigoCuin to set
	 */
	public void setCodigoCuin(String codigoCuin) {
		this.codigoCuin = codigoCuin;
	}

	/**
	 * @return the latitud
	 */
	public String getLatitud() {
		return latitud;
	}

	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	/**
	 * @return the longitud
	 */
	public String getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(String longitud) {
		this.longitud = longitud;
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
	 * @return the estadoEntidad
	 */
	public String getEstadoEntidad() {
		return estadoEntidad;
	}

	/**
	 * @param estadoEntidad the estadoEntidad to set
	 */
	public void setEstadoEntidad(String estadoEntidad) {
		this.estadoEntidad = estadoEntidad;
	}

	/**
	 * @return the subEstadoEntidad
	 */
	public String getSubEstadoEntidad() {
		return subEstadoEntidad;
	}

	/**
	 * @param subEstadoEntidad the subEstadoEntidad to set
	 */
	public void setSubEstadoEntidad(String subEstadoEntidad) {
		this.subEstadoEntidad = subEstadoEntidad;
	}

	/**
	 * @return the tipoEntidad
	 */
	public String getTipoEntidad() {
		return tipoEntidad;
	}

	/**
	 * @param tipoEntidad the tipoEntidad to set
	 */
	public void setTipoEntidad(String tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	/**
	 * @return the codigoSigep
	 */
	public String getCodigoSigep() {
		return codigoSigep;
	}

	/**
	 * @param codigoSigep the codigoSigep to set
	 */
	public void setCodigoSigep(String codigoSigep) {
		this.codigoSigep = codigoSigep;
	}

	/**
	 * @return the nivelAdministrativo
	 */
	public String getNivelAdministrativo() {
		return nivelAdministrativo;
	}

	/**
	 * @param nivelAdministrativo the nivelAdministrativo to set
	 */
	public void setNivelAdministrativo(String nivelAdministrativo) {
		this.nivelAdministrativo = nivelAdministrativo;
	}

	/**
	 * @return the regimenEscalaSalarial
	 */
	public List<RegimenSalarial> getRegimenEscalaSalarial() {
		return regimenEscalaSalarial;
	}

	/**
	 * @param regimenEscalaSalarial the regimenEscalaSalarial to set
	 */
	public void setRegimenEscalaSalarial(List<RegimenSalarial> regimenEscalaSalarial) {
		this.regimenEscalaSalarial = regimenEscalaSalarial;
	}

	/**
	 * @return the tipoZona
	 */
	public String getTipoZona() {
		return tipoZona;
	}

	/**
	 * @param tipoZona the tipoZona to set
	 */
	public void setTipoZona(String tipoZona) {
		this.tipoZona = tipoZona;
	}

	/**
	 * @return the patrimonioPropio
	 */
	public String getPatrimonioPropio() {
		return patrimonioPropio;
	}

	/**
	 * @param patrimonioPropio the patrimonioPropio to set
	 */
	public void setPatrimonioPropio(String patrimonioPropio) {
		this.patrimonioPropio = patrimonioPropio;
	}

	/**
	 * @return the naturalezaEmpleo
	 */
	public List<NaturalezaEmpleo> getNaturalezaEmpleo() {
		return naturalezaEmpleo;
	}

	/**
	 * @param naturalezaEmpleo the naturalezaEmpleo to set
	 */
	public void setNaturalezaEmpleo(List<NaturalezaEmpleo> naturalezaEmpleo) {
		this.naturalezaEmpleo = naturalezaEmpleo;
	}

	/**
	 * @return the sistemasCarrera
	 */
	public List<SistemaCarrera> getSistemasCarrera() {
		return sistemasCarrera;
	}

	/**
	 * @param sistemasCarrera the sistemasCarrera to set
	 */
	public void setSistemasCarrera(List<SistemaCarrera> sistemasCarrera) {
		this.sistemasCarrera = sistemasCarrera;
	}
}
