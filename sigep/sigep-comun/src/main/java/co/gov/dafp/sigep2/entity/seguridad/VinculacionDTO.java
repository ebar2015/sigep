package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class VinculacionDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = -3683966681850437221L;
	
	private Long id;
	
	private Long codPersona;
	
	private Long codPersonaTitular;
	
	private Long codEntidadPlanta;
	
	private Long codDependenciaEntidad;
	
	private Long codGrupoDependencia;
	
	private Date fechaPosesion;
	
	private Date fechaFinalizacion;
	
	private Long codCausalDesvinculacion;
	
	private Long codTipoActoAdminDesvinculacion;
	
	private String numActoAdminDesvinculacion;
	
	private Date fechaActoAdminDesvinculacion;
	
	private Boolean isMedicoDocente;
	
	public VinculacionDTO() {
	}

	public VinculacionDTO(Long id, Long codPersona, Long codPersonaTitular, Long codEntidadPlanta,
			Long codDependenciaEntidad, Long codGrupoDependencia, Date fechaPosesion, Date fechaFinalizacion,
			Long codCausalDesvinculacion, Long codTipoActoAdminDesvinculacion, String numActoAdminDesvinculacion,
			Date fechaActoAdminDesvinculacion, Boolean isMedicoDocente) {
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.codPersonaTitular = codPersonaTitular;
		this.codEntidadPlanta = codEntidadPlanta;
		this.codDependenciaEntidad = codDependenciaEntidad;
		this.codGrupoDependencia = codGrupoDependencia;
		this.fechaPosesion = fechaPosesion;
		this.fechaFinalizacion = fechaFinalizacion;
		this.codCausalDesvinculacion = codCausalDesvinculacion;
		this.codTipoActoAdminDesvinculacion = codTipoActoAdminDesvinculacion;
		this.numActoAdminDesvinculacion = numActoAdminDesvinculacion;
		this.fechaActoAdminDesvinculacion = fechaActoAdminDesvinculacion;
		this.isMedicoDocente = isMedicoDocente;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public Long getCodPersonaTitular() {
		return codPersonaTitular;
	}

	public void setCodPersonaTitular(Long codPersonaTitular) {
		this.codPersonaTitular = codPersonaTitular;
	}

	public Long getCodEntidadPlanta() {
		return codEntidadPlanta;
	}

	public void setCodEntidadPlanta(Long codEntidadPlanta) {
		this.codEntidadPlanta = codEntidadPlanta;
	}

	public Long getCodDependenciaEntidad() {
		return codDependenciaEntidad;
	}

	public void setCodDependenciaEntidad(Long codDependenciaEntidad) {
		this.codDependenciaEntidad = codDependenciaEntidad;
	}

	public Long getCodGrupoDependencia() {
		return codGrupoDependencia;
	}

	public void setCodGrupoDependencia(Long codGrupoDependencia) {
		this.codGrupoDependencia = codGrupoDependencia;
	}

	public Date getFechaPosesion() {
		return fechaPosesion;
	}

	public void setFechaPosesion(Date fechaPosesion) {
		this.fechaPosesion = fechaPosesion;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public Long getCodCausalDesvinculacion() {
		return codCausalDesvinculacion;
	}

	public void setCodCausalDesvinculacion(Long codCausalDesvinculacion) {
		this.codCausalDesvinculacion = codCausalDesvinculacion;
	}

	public Long getCodTipoActoAdminDesvinculacion() {
		return codTipoActoAdminDesvinculacion;
	}

	public void setCodTipoActoAdminDesvinculacion(Long codTipoActoAdminDesvinculacion) {
		this.codTipoActoAdminDesvinculacion = codTipoActoAdminDesvinculacion;
	}

	public String getNumActoAdminDesvinculacion() {
		return numActoAdminDesvinculacion;
	}

	public void setNumActoAdminDesvinculacion(String numActoAdminDesvinculacion) {
		this.numActoAdminDesvinculacion = numActoAdminDesvinculacion;
	}

	public Date fechaActoAdminDesvinculacion() {
		return fechaActoAdminDesvinculacion;
	}

	public void setfechaActoAdminDesvinculacion(Date fechaActoAdminDesvinculacion) {
		this.fechaActoAdminDesvinculacion = fechaActoAdminDesvinculacion;
	}

	public Boolean getIsMedicoDocente() {
		return isMedicoDocente;
	}

	public void setIsMedicoDocente(Boolean isMedicoDocente) {
		this.isMedicoDocente = isMedicoDocente;
	}

	@Override
	public String toString() {
		return "VinculacionDTO [id=" + id + ", codPersona=" + codPersona + ", codPersonaTitular=" + codPersonaTitular
				+ ", codEntidadPlanta=" + codEntidadPlanta + ", codDependenciaEntidad="
				+ codDependenciaEntidad + ", codGrupoDependencia=" + codGrupoDependencia + ", fechaPosesion="
				+ fechaPosesion + ", fechaFinalizacion=" + fechaFinalizacion + ", codCausalDesvinculacion="
				+ codCausalDesvinculacion + ", codTipoActoAdminDesvinculacion=" + codTipoActoAdminDesvinculacion
				+ ", numActoAdminDesvinculacion=" + numActoAdminDesvinculacion + ", fechaActoAdminDesvinculacion="
				+ fechaActoAdminDesvinculacion + ", isMedicoDocente=" + isMedicoDocente + "]";
	}
}
