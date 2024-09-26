package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.SituacionAdministrativa;

/**
 * @author jesus.torres
 *
 */
public class SituacionAdministrativaExt extends SituacionAdministrativa{
	private static final long serialVersionUID = -5843111078958175428L;
	private String nombrePadre;
    private Integer codTipoIdentificacion;
	private String numeroIdentificacion;
	private Integer codEntidad;
	private Integer codRolVinculado;
	private String nombrePersona;
	private Integer codEntidadSituacion;
	private Integer codEntidadSituacionAdmin;
	private Integer numAsignadas;
	private String estadoAsignacion;

	/**
	 * @return the nombrePadre
	 */
	public String getNombrePadre() {
		return nombrePadre;
	}

	/**
	 * @param nombrePadre the nombrePadre to set
	 */
	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	public Integer getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	public void setCodTipoIdentificacion(Integer codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public Integer getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Integer codEntidad) {
		this.codEntidad = codEntidad;
	}

	public Integer getCodRolVinculado() {
		return codRolVinculado;
	}

	public void setCodRolVinculado(Integer codRolVinculado) {
		this.codRolVinculado = codRolVinculado;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public Integer getCodEntidadSituacion() {
		return codEntidadSituacion;
	}

	public void setCodEntidadSituacion(Integer codEntidadSituacion) {
		this.codEntidadSituacion = codEntidadSituacion;
	}

	public Integer getCodEntidadSituacionAdmin() {
		return codEntidadSituacionAdmin;
	}

	public void setCodEntidadSituacionAdmin(Integer codEntidadSituacionAdmin) {
		this.codEntidadSituacionAdmin = codEntidadSituacionAdmin;
	}

	/**
	 * @return the numAsignadas
	 */
	public Integer getNumAsignadas() {
		return numAsignadas;
	}

	/**
	 * @param numAsignadas the numAsignadas to set
	 */
	public void setNumAsignadas(Integer numAsignadas) {
		this.numAsignadas = numAsignadas;
	}

	/**
	 * @return the estadoAsignacion
	 */
	public String getEstadoAsignacion() {
		if(numAsignadas != null) {
			if(numAsignadas > 0) {
				estadoAsignacion =  "SI";
			}else {
				estadoAsignacion =  "NO";
			}
		}else {
			estadoAsignacion =  "NO";
		}
		return estadoAsignacion;
	}

	/**
	 * @param estadoAsignacion the estadoAsignacion to set
	 */
	public void setEstadoAsignacion(String estadoAsignacion) {
		this.estadoAsignacion = estadoAsignacion;
	}
}
