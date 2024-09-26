/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Usuario;
import co.gov.dafp.sigep2.entities.UsuarioEntidad;
import co.gov.dafp.sigep2.entities.UsuarioRolEntidad;



/**
 * @author joseviscaya
 *
 */
public class UsuarioExt extends Usuario implements Serializable {

	private static final long serialVersionUID = 6371661626212843291L;
	
	private List<UsuarioEntidad> usuarioEntidad;
	private List<UsuarioRolEntidad> usuarioRolEntidad;
	private List<Entidad> entidadList;
	
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String tipoAsociacion;
	private String nombreEntidad;
	private BigDecimal codRol;
	private BigDecimal codEntidad;
	private BigDecimal codTipoIdentificacionPersona;
	private String numeroIdentificacionPersona;
	private Short flgActivo;
	private BigDecimal codTipoVinculacion;
	private int cantidadRoles;
	
	/**
	 * @return the usuarioEntidad
	 */
	public List<UsuarioEntidad> getUsuarioEntidad() {
		return usuarioEntidad;
	}
	/**
	 * @param usuarioEntidad the usuarioEntidad to set
	 */
	public void setUsuarioEntidad(List<UsuarioEntidad> usuarioEntidad) {
		this.usuarioEntidad = usuarioEntidad;
	}
	/**
	 * @return the usuarioRolEntidad
	 */
	public List<UsuarioRolEntidad> getUsuarioRolEntidad() {
		return usuarioRolEntidad;
	}
	/**
	 * @param usuarioRolEntidad the usuarioRolEntidad to set
	 */
	public void setUsuarioRolEntidad(List<UsuarioRolEntidad> usuarioRolEntidad) {
		this.usuarioRolEntidad = usuarioRolEntidad;
	}
	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}
	/**
	 * @param primerNombre the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}
	/**
	 * @param segundoNombre the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}
	/**
	 * @param primerApellido the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}
	/**
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	/**
	 * @return the entidadList
	 */
	public List<Entidad> getEntidadList() {
		return entidadList;
	}
	/**
	 * @param entidadList the entidadList to set
	 */
	public void setEntidadList(List<Entidad> entidadList) {
		this.entidadList = entidadList;
	}
	/**
	 * @return the tipoAsociacion
	 */
	public String getTipoAsociacion() {
		return tipoAsociacion;
	}
	/**
	 * @param tipoAsociacion the tipoAsociacion to set
	 */
	public void setTipoAsociacion(String tipoAsociacion) {
		this.tipoAsociacion = tipoAsociacion;
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
	 * @return the codRol
	 */
	public BigDecimal getCodRol() {
		return codRol;
	}
	/**
	 * @param codRol the codRol to set
	 */
	public void setCodRol(BigDecimal codRol) {
		this.codRol = codRol;
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
	 * @return the numeroIdentificacionPersona
	 */
	public String getNumeroIdentificacionPersona() {
		return numeroIdentificacionPersona;
	}
	/**
	 * @param numeroIdentificacionPersona the numeroIdentificacionPersona to set
	 */
	public void setNumeroIdentificacionPersona(String numeroIdentificacionPersona) {
		this.numeroIdentificacionPersona = numeroIdentificacionPersona;
	}
	/**
	 * @return the codTipoIdentificacionPersona
	 */
	public BigDecimal getCodTipoIdentificacionPersona() {
		return codTipoIdentificacionPersona;
	}
	/**
	 * @param codTipoIdentificacionPersona the codTipoIdentificacionPersona to set
	 */
	public void setCodTipoIdentificacionPersona(BigDecimal codTipoIdentificacionPersona) {
		this.codTipoIdentificacionPersona = codTipoIdentificacionPersona;
	}
	/**
	 * @return the flgActivo
	 */
	public Short getFlgActivo() {
		return flgActivo;
	}
	/**
	 * @param flgActivo the flgActivo to set
	 */
	public void setFlgActivo(Short flgActivo) {
		this.flgActivo = flgActivo;
	}
	/**
	 * @return the codTipoVinculacion
	 */
	public BigDecimal getCodTipoVinculacion() {
		return codTipoVinculacion;
	}
	/**
	 * @param codTipoVinculacion the codTipoVinculacion to set
	 */
	public void setCodTipoVinculacion(BigDecimal codTipoVinculacion) {
		this.codTipoVinculacion = codTipoVinculacion;
	}
	public int getCantidadRoles() {
		return cantidadRoles;
	}
	public void setCantidadRoles(int cantidadRoles) {
		this.cantidadRoles = cantidadRoles;
	}
}
