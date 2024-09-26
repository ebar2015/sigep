package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.seguridad.VinculacionDTO;

@Entity(name = "Vinculacion")
@Table(name = "VINCULACION")

public class Vinculacion extends EntidadBase implements Serializable{
	private static final long serialVersionUID = -6537987811413310522L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_VINCULACION", insertable = false, updatable = false, unique = true, nullable = false)
	private long codVinculacion;
	
	@Column(name = "COD_PERSONA", nullable = false, precision = 22, scale = 0)
	protected Long codPersona;
	
	@Column(name = "COD_PERSONA_TITULAR", nullable = false, precision = 22, scale = 0)
	protected Long codPersonaTitular;
	
	@Column(name = "COD_ENTIDAD_PLANTA", nullable = false, precision = 22, scale = 0)
	protected Long codEntidadPlanta;
	
	@Column(name = "COD_DEPENDENCIA_ENTIDAD", nullable = false, precision = 22, scale = 0)
	protected Long codDependenciaEntidad;
	
	@Column(name = "COD_GRUPO_DEPENDENCIA", nullable = false, precision = 22, scale = 0)
	protected Long codGrupoDependencia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_POSESION", nullable = false, length = 7)
	private Date fechaPosesion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FINALIZACION", nullable = false, length = 7)
	private Date fechaFinalizacion;
	
	@Column(name = "COD_CAUSAL_DESVINCULACION", nullable = false, precision = 22, scale = 0)
	protected Long codCausalDesvinculacion;
	
	@Column(name = "COD_TIPO_ACTO_ADMIN_DESVINCULACION", nullable = false, precision = 22, scale = 0)
	protected Long codTipoActoAdminDesvinculacion;
	
	@Column(name = "NUM_ACTO_ADMIN_DESVINCULACION", nullable = false, length = 255)
	private String numActoAdminDesvinculacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ACTO_ADMIN_DESVINCULACION", nullable = false, length = 7)
	private Date fechaActoAdminDesvinculacion;
	
	@Column(name = "FLG_MEDICO_DOCENTE", precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean isMedicoDocente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false,  precision = 22, scale = 0)
	protected Long  audAccion;
	
	public Vinculacion() {
	}
	
	public Long getId() {
		return this.codVinculacion;
	}

	public void setId(Long id) {
		this.codVinculacion = id;
	}


	public long getCodVinculacion() {
		return codVinculacion;
	}

	public void setCodVinculacion(long codVinculacion) {
		this.codVinculacion = codVinculacion;
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

	public Date getFechaActoAdminDesvinculacion() {
		return fechaActoAdminDesvinculacion;
	}

	public void setFechaActoAdminDesvinculacion(Date fechaActoAdminDesvinculacion) {
		this.fechaActoAdminDesvinculacion = fechaActoAdminDesvinculacion;
	}

	public Boolean getIsMedicoDocente() {
		return isMedicoDocente;
	}

	public void setIsMedicoDocente(Boolean isMedicoDocente) {
		this.isMedicoDocente = isMedicoDocente;
	}

	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	public Long getAudCodUsuario() {
		return audCodUsuario;
	}

	public void setAudCodUsuario(Long audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	public Long getAudCodRol() {
		return audCodRol;
	}

	public void setAudCodRol(Long audCodRol) {
		this.audCodRol = audCodRol;
	}

	public Long getAudAccion() {
		return audAccion;
	}

	public void setAudAccion(Long audAccion) {
		this.audAccion = audAccion;
	}

	@Override
	public String toString() {
		return "Vinculacion [codVinculacion=" + this.getCodVinculacion() + ", codPersona=" + this.getCodPersona() + ", codPersonaTitular="
				+ this.getCodPersonaTitular() + ", codEntidadPlanta=" + this.getCodEntidadPlanta()
				+ ", codDependenciaEntidad=" + this.getCodDependenciaEntidad() + ", codGrupoDependencia=" + this.getCodGrupoDependencia()
				+ ", fechaPosesion=" + this.getFechaPosesion() + ", fechaFinalizacion=" + this.getFechaFinalizacion()
				+ ", codCausalDesvinculacion=" + this.getCodCausalDesvinculacion() + ", codTipoActoAdminDesvinculacion=" + this.getCodTipoActoAdminDesvinculacion()
				+ ", numActoAdminDesvinculacion=" + this.getNumActoAdminDesvinculacion() + ", fechaActoAdminDesvinculacion="
				+ this.getNumActoAdminDesvinculacion() + ", isMedicoDocente=" + this.getIsMedicoDocente() + "]";
	}
	
	public EntidadBaseDTO getDTO() {
		return new VinculacionDTO(codVinculacion, codPersona, codPersonaTitular, codEntidadPlanta, codDependenciaEntidad, codGrupoDependencia, fechaPosesion, fechaFinalizacion, codCausalDesvinculacion, codTipoActoAdminDesvinculacion, numActoAdminDesvinculacion, fechaActoAdminDesvinculacion, isMedicoDocente);
	}
	
	
	
	
}
