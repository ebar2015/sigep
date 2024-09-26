/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import co.gov.dafp.sigep2.entities.Entidad;

/**
 * @author joseviscaya
 *
 */
public class EntidadExt extends Entidad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2583807084422038810L;

	private long codPersona;

	private int total;

	private BigDecimal codUsuarioSesion;
	private String nombreSector;
	private String nombreClasificacion;
	private String nombreNaturaleza;
	private String nombreOrden;
	private Long codUsuario;
	private Long codRol;
	private Long codTipoVinculacion;
	private int flgDependencial;
	private Long codDependenciaEntidad;

	private List<Long> lstCodEntidad;
	private long dependencia;

	private Date fechaI;
	private Date fechaF;

	private String departamento;
	private String asesor;
	private String idDenominacion;
	
	private String sectorAdministrativo;
	private String orden;
	private String clasificacionOrganica;
	private String naturalezaJuridica;

	/**
	 * @return the codPersona
	 */
	public long getCodPersona() {
		return codPersona;
	}

	/**
	 * @param codPersona
	 *            the codPersona to set
	 */
	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the codUsuarioSesion
	 */
	public BigDecimal getCodUsuarioSesion() {
		return codUsuarioSesion;
	}

	/**
	 * @param codUsuarioSesion
	 *            the codUsuarioSesion to set
	 */
	public void setCodUsuarioSesion(BigDecimal codUsuarioSesion) {
		this.codUsuarioSesion = codUsuarioSesion;
	}

	/**
	 * @return the nombreSector
	 */
	public String getNombreSector() {
		return nombreSector;
	}

	/**
	 * @param nombreSector
	 *            the nombreSector to set
	 */
	public void setNombreSector(String nombreSector) {
		this.nombreSector = nombreSector;
	}

	/**
	 * @return the nombreClasificacion
	 */
	public String getNombreClasificacion() {
		return nombreClasificacion;
	}

	/**
	 * @param nombreClasificacion
	 *            the nombreClasificacion to set
	 */
	public void setNombreClasificacion(String nombreClasificacion) {
		this.nombreClasificacion = nombreClasificacion;
	}

	/**
	 * @return the nombreNaturaleza
	 */
	public String getNombreNaturaleza() {
		return nombreNaturaleza;
	}

	/**
	 * @param nombreNaturaleza
	 *            the nombreNaturaleza to set
	 */
	public void setNombreNaturaleza(String nombreNaturaleza) {
		this.nombreNaturaleza = nombreNaturaleza;
	}

	/**
	 * @return the nombreOrden
	 */
	public String getNombreOrden() {
		return nombreOrden;
	}

	/**
	 * @param nombreOrden
	 *            the nombreOrden to set
	 */
	public void setNombreOrden(String nombreOrden) {
		this.nombreOrden = nombreOrden;
	}

	/**
	 * @return the codUsuario
	 */
	public Long getCodUsuario() {
		return codUsuario;
	}

	/**
	 * @param codUsuario
	 *            the codUsuario to set
	 */
	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}

	/**
	 * @return the codRol
	 */
	public Long getCodRol() {
		return codRol;
	}

	/**
	 * @param codRol
	 *            the codRol to set
	 */
	public void setCodRol(Long codRol) {
		this.codRol = codRol;
	}

	/**
	 * @return the codTipoVinculacion
	 */
	public Long getCodTipoVinculacion() {
		return codTipoVinculacion;
	}

	/**
	 * @param codTipoVinculacion
	 *            the codTipoVinculacion to set
	 */
	public void setCodTipoVinculacion(Long codTipoVinculacion) {
		this.codTipoVinculacion = codTipoVinculacion;
	}

	/**
	 * @return the flgDependencial
	 */
	public int getFlgDependencial() {
		return flgDependencial;
	}

	/**
	 * @param flgDependencial
	 *            the flgDependencial to set
	 */
	public void setFlgDependencial(int flgDependencial) {
		this.flgDependencial = flgDependencial;
	}

	/**
	 * @return the lstCodEntidad
	 */
	public List<Long> getLstCodEntidad() {
		return lstCodEntidad;
	}

	/**
	 * @param lstCodEntidad
	 *            the lstCodEntidad to set
	 */
	public void setLstCodEntidad(List<Long> lstCodEntidad) {
		this.lstCodEntidad = lstCodEntidad;
	}

	/**
	 * @return the dependencia
	 */
	public long getDependencia() {
		return dependencia;
	}

	/**
	 * @param dependencia
	 *            the dependencia to set
	 */
	public void setDependencia(long dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * @return the codDependenciaEntidad
	 */
	public Long getCodDependenciaEntidad() {
		return codDependenciaEntidad;
	}

	/**
	 * @param codDependenciaEntidad
	 *            the codDependenciaEntidad to set
	 */
	public void setCodDependenciaEntidad(Long codDependenciaEntidad) {
		this.codDependenciaEntidad = codDependenciaEntidad;
	}

	/**
	 * @return the fechaI
	 */
	public Date getFechaI() {
		return fechaI;
	}

	/**
	 * @param fechaI
	 *            the fechaI to set
	 */
	public void setFechaI(Date fechaI) {
		this.fechaI = fechaI;
	}

	/**
	 * @return the fechaF
	 */
	public Date getFechaF() {
		return fechaF;
	}

	/**
	 * @param fechaF
	 *            the fechaF to set
	 */
	public void setFechaF(Date fechaF) {
		this.fechaF = fechaF;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getAsesor() {
		return asesor;
	}

	public void setAsesor(String asesor) {
		this.asesor = asesor;
	}

	/**
	 * @return the idDenominacion
	 */
	public String getIdDenominacion() {
		return idDenominacion;
	}

	/**
	 * @param idDenominacion the idDenominacion to set
	 */
	public void setIdDenominacion(String idDenominacion) {
		this.idDenominacion = idDenominacion;
	}

	public String getSectorAdministrativo() {
		return sectorAdministrativo;
	}

	public void setSectorAdministrativo(String sectorAdministrativo) {
		this.sectorAdministrativo = sectorAdministrativo;
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
	 * @return the clasificacionOrganica
	 */
	public String getClasificacionOrganica() {
		return clasificacionOrganica;
	}

	/**
	 * @param clasificacionOrganica the clasificacionOrganica to set
	 */
	public void setClasificacionOrganica(String clasificacionOrganica) {
		this.clasificacionOrganica = clasificacionOrganica;
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

}
