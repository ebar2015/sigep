package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.seguridad.ContratoDTO;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;

@Entity(name = "Contrato")
@Table(name = "CONTRATO")
public class Contrato extends EntidadBase implements Serializable {
	private static final long serialVersionUID = 7636279810598931038L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_CONTRATO", insertable = false, updatable = false, unique = true, nullable = false)
	private long CodContrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_ENTIDAD")
	private Entidad codEntidad;

	@Column(name = "COD_PERSONA")
	private Long codPersona;

	@Column(name = "COD_DEPENDENCIA_ENTIDAD")
	private Long codDependenciaEntidad;

	@Column(name = "COD_GRUPO_DEPENDENCIA")
	private Long codGrupoDependencia;

	@Column(name = "COD_MUNICIPIO")
	private Long codMunicipio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIRMA", nullable = false, length = 7)
	private Date fechaFirma;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO", nullable = false, length = 7)
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN", nullable = false, length = 7)
	private Date fechaFin;

	@Column(name = "NUMERO_CONTRATO")
	private String numeroContrato;

	@Column(name = "OBJETO_CONTRATO")
	private String objetoContrato;

	@Column(name = "ALCANCE_CONTRATO")
	private String alcanceContrato;

	@Column(name = "COD_MONEDA_MONTO")
	private Long codMonedaMonto;

	@Column(name = "MONTO")
	private Long monto;

	@Column(name = "COD_MONEDA_HONORARIO")
	private Long codMonedaHonorario;

	@Column(name = "HONORARIOS")
	private Long honorarios;

	@Column(name = "COD_FUENTE_FINANCIACION")
	private Long codFuenteFinanciacion;

	@Column(name = "COD_ADMINISTRACION_RECURSO")
	private Long codAdministracionRecurso;

	@Column(name = "DERECHO_EXCLUSIVIDAD")
	private Long derechoExclusividad;

	@Column(name = "COD_SUPERVISOR")
	private Long codSupervisor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO_SUPERVISION")
	private Date fechaInicioSupervision;

	@Column(name = "DEPENDENCIA_SUPERVISION")
	private String dependenciaSupervision;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	public Contrato() {
	}

	public Long getId() {
		return this.CodContrato;
	}

	public void setId(Long id) {
		this.CodContrato = id;
	}

	public long getCodContrato() {
		return CodContrato;
	}

	public void setCodContrato(long codContrato) {
		CodContrato = codContrato;
	}

	public Entidad getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Entidad codEntidad) {
		this.codEntidad = codEntidad;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
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

	public Long getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(Long codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public Date getFechaFirma() {
		return fechaFirma;
	}

	public void setFechaFirma(Date fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public String getObjetoContrato() {
		return objetoContrato;
	}

	public void setObjetoContrato(String objetoContrato) {
		this.objetoContrato = objetoContrato;
	}

	public String getAlcanceContrato() {
		return alcanceContrato;
	}

	public void setAlcanceContrato(String alcanceContrato) {
		this.alcanceContrato = alcanceContrato;
	}

	public Long getCodMonedaMonto() {
		return codMonedaMonto;
	}

	public void setCodMonedaMonto(Long codMonedaMonto) {
		this.codMonedaMonto = codMonedaMonto;
	}

	public Long getMonto() {
		return monto;
	}

	public void setMonto(Long monto) {
		this.monto = monto;
	}

	public Long getCodMonedaHonorario() {
		return codMonedaHonorario;
	}

	public void setCodMonedaHonorario(Long codMonedaHonorario) {
		this.codMonedaHonorario = codMonedaHonorario;
	}

	public Long getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(Long honorarios) {
		this.honorarios = honorarios;
	}

	public Long getCodFuenteFinanciacion() {
		return codFuenteFinanciacion;
	}

	public void setCodFuenteFinanciacion(Long codFuenteFinanciacion) {
		this.codFuenteFinanciacion = codFuenteFinanciacion;
	}

	public Long getCodAdministracionRecurso() {
		return codAdministracionRecurso;
	}

	public void setCodAdministracionRecurso(Long codAdministracionRecurso) {
		this.codAdministracionRecurso = codAdministracionRecurso;
	}

	public Long getDerechoExclusividad() {
		return derechoExclusividad;
	}

	public void setDerechoExclusividad(Long derechoExclusividad) {
		this.derechoExclusividad = derechoExclusividad;
	}

	public Long getCodSupervisor() {
		return codSupervisor;
	}

	public void setCodSupervisor(Long codSupervisor) {
		this.codSupervisor = codSupervisor;
	}

	public Date getFechaInicioSupervision() {
		return fechaInicioSupervision;
	}

	public void setFechaInicioSupervision(Date fechaInicioSupervision) {
		this.fechaInicioSupervision = fechaInicioSupervision;
	}

	public String getDependenciaSupervision() {
		return dependenciaSupervision;
	}

	public void setDependenciaSupervision(String dependenciaSupervision) {
		this.dependenciaSupervision = dependenciaSupervision;
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
		return "Contrato [CodContrato=" + this.getCodContrato() + ", codEntidad=" + this.getCodEntidad()
				+ ", codPersona=" + getCodPersona() + ", codDependenciaEntidad=" + this.getCodDependenciaEntidad()
				+ ", codGrupoDependencia=" + this.getCodGrupoDependencia() + ", codMunicipio=" + this.getCodMunicipio()
				+ ", fechaFirma=" + this.getFechaFirma() + ", fechaInicio=" + this.getFechaInicio() + ", fechaFin="
				+ this.getFechaFin() + ", numeroContrato=" + this.getNumeroContrato() + ", objetoContrato="
				+ this.getObjetoContrato() + ", alcanceContrato=" + this.getAlcanceContrato() + ", codMonedaMonto="
				+ this.getCodMonedaMonto() + ", monto=" + this.getMonto() + ", codMonedaHonorario="
				+ this.getCodMonedaHonorario() + ", honorarios=" + this.getHonorarios() + ", codFuenteFinanciacion="
				+ this.getCodFuenteFinanciacion() + ", codAdministracionRecurso=" + this.getCodAdministracionRecurso()
				+ ", derechoExclusividad=" + this.getDerechoExclusividad() + ", codSupervisor="
				+ this.getCodSupervisor() + ", fechaInicioSupervision=" + this.getFechaInicioSupervision()
				+ ", dependenciaSupervision=" + this.getDependenciaSupervision() + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new ContratoDTO(CodContrato, (EntidadDTO) codEntidad.getDTO(), codPersona, codDependenciaEntidad,
				codGrupoDependencia, codMunicipio, fechaFirma, fechaInicio, fechaFin, numeroContrato, objetoContrato,
				alcanceContrato, codMonedaMonto, codMonedaMonto, codMonedaHonorario, honorarios, codFuenteFinanciacion,
				codAdministracionRecurso, derechoExclusividad, codSupervisor, fechaInicioSupervision,
				dependenciaSupervision);
	}

}
