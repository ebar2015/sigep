package co.gov.dafp.sigep2.entity.seguridad;

import java.math.BigInteger;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.RecursoDTO;

/**
 * Representa la vista V_RECURSO_ACCION con la configuracion basica en la
 * gestion de permisos
 * 
 * @author JDavila
 * @version 1.0
 */
public class RecursoAccionDTO extends EntidadBaseDTO implements Cloneable {
	private static final long serialVersionUID = 3819655272255546298L;

	/**
	 * PK del recurso al que se le gestiona el permiso
	 */
	private long id;

	/**
	 * {@link RecursoDTO#getDescripcion()} del componente padre de
	 * <code>recurso</code>
	 */
	private String padre;

	/**
	 * PK del registro en RECURSO_ACCION del recurso a configurar
	 */
	private BigInteger codRecursoAccion;

	/**
	 * PK del PERMISO_ROL asociado a <code>codRecursoAccion</code>
	 */
	private BigInteger codPermisoRol;

	/**
	 * PK del PERMISO_ROL_ACCION asociado a <code>codRecursoAccion</code> y
	 * <code>codPermisoRol</code>
	 */
	private BigInteger codPermisoRolAcciones;

	/**
	 * {@link RecursoDTO#getDescripcion()} de la funcionalidad padre de
	 * <code>seccion</code>
	 */
	private String recurso;

	/**
	 * {@link RecursoDTO#getDescripcion()} del recurso o cnotrol a configurar
	 */
	private String seccion;

	/**
	 * PK en ROL del rol al que se le esta configurando acceso desde
	 * <code>recurso</code>
	 */
	private BigInteger codRol;

	/**
	 * Nombre del registro asociado a <code>codRol</code>
	 */
	private String nombreRol;

	/**
	 * Descripcion del registro asociado a <code>codRol</code>
	 */
	private String descripcionRol;

	/**
	 * Indica el estado de la configuración <code>true</code> indica activa y
	 * <code>false</code> inactiva
	 */
	private boolean flgEstado;

	/**
	 * Indica el origen de la configuración <code>true</code> indica que el
	 * recurso en configuracion es un control dentro de una funcionalidad,
	 * <code>false</code> que proviene de una funcionalidad en el menu
	 */
	private boolean flgAccion;

	/**
	 * De manejo en el procesamiento de las consultas, no infiere en el estado
	 * del registro en DDBB. Indica el total de registros que coincidieron en el
	 * resultado sin tener en cuenta la paginacion
	 */
	private BigInteger total;

	/**
	 * De manejo en el procesamiento de las consultas, no infiere en el estado
	 * del registro en DDBB. Indica donde comienza la paginacion
	 */

	private int limitIni;
	/**
	 * De manejo en el procesamiento de las consultas, no infiere en el estado
	 * del registro en DDBB. Indica el tamaño de la pagina
	 */
	private int limitFin;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPadre() {
		return padre;
	}

	public void setPadre(String padre) {
		this.padre = padre;
	}

	public BigInteger getCodRecursoAccion() {
		return codRecursoAccion;
	}

	public void setCodRecursoAccion(BigInteger codRecursoAccion) {
		this.codRecursoAccion = codRecursoAccion;
	}

	public BigInteger getCodPermisoRol() {
		return codPermisoRol;
	}

	public void setCodPermisoRol(BigInteger codPermisoRol) {
		this.codPermisoRol = codPermisoRol;
	}

	public BigInteger getCodPermisoRolAcciones() {
		return codPermisoRolAcciones;
	}

	public void setCodPermisoRolAcciones(BigInteger codPermisoRolAcciones) {
		this.codPermisoRolAcciones = codPermisoRolAcciones;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

	public BigInteger getCodRol() {
		return codRol;
	}

	public void setCodRol(BigInteger codRol) {
		this.codRol = codRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public boolean isFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	public boolean isFlgAccion() {
		return flgAccion;
	}

	public void setFlgAccion(boolean flgAccion) {
		this.flgAccion = flgAccion;
	}

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}

	public int getLimitIni() {
		return limitIni;
	}

	public void setLimitIni(int limitIni) {
		this.limitIni = limitIni;
	}

	public int getLimitFin() {
		return limitFin;
	}

	public void setLimitFin(int limitFin) {
		this.limitFin = limitFin;
	}

	@Override
	public String toString() {
		return "RecursoAccionDTO [id=" + id + ", padre=" + padre + ", codRecursoAccion=" + codRecursoAccion
				+ ", codPermisoRol=" + codPermisoRol + ", codPermisoRolAcciones=" + codPermisoRolAcciones + ", recurso="
				+ recurso + ", seccion=" + seccion + ", codRol=" + codRol + ", nombreRol=" + nombreRol
				+ ", descripcionRol=" + descripcionRol + ", flgEstado=" + flgEstado + ", flgAccion=" + flgAccion
				+ ", total=" + total + ", limitIni=" + limitIni + ", limitFin=" + limitFin + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecursoAccionDTO other = (RecursoAccionDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * Clone de la instancia actual
	 * 
	 * @throws CloneNotSupportedException
	 * 
	 * @retur {@link RecursoAccionDTO} Instancia clonada de <code>this</code>
	 */
	@Override
	public RecursoAccionDTO clone() throws CloneNotSupportedException {

		RecursoAccionDTO clone = new RecursoAccionDTO();

		clone.id = id;
		clone.padre = padre;
		clone.codRecursoAccion = codRecursoAccion;
		clone.codPermisoRol = codPermisoRol;
		clone.codPermisoRolAcciones = codPermisoRolAcciones;
		clone.recurso = recurso;
		clone.seccion = seccion;
		clone.codRol = codRol;
		clone.nombreRol = nombreRol;
		clone.descripcionRol = descripcionRol;
		clone.flgEstado = flgEstado;
		clone.flgAccion = flgAccion;
		clone.total = total;
		clone.limitIni = limitIni;
		clone.limitFin = limitFin;
		return clone;
	}

}
