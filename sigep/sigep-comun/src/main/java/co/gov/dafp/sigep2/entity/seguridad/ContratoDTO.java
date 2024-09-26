package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

/**
 * The persistent class for the CONTRATO database table.
 * 
 */

public class ContratoDTO extends EntidadBaseDTO implements Serializable {

	private static final long serialVersionUID = 6170216458198709906L;

	private long id;

	private EntidadDTO codEntidad;

	private Long codPersona;

	private Long CodDependenciaEntidad;

	private Long codGrupoDependencia;

	private Long codMunicipio;
	
	private Date fechaFirma;

	private Date fechaInicio;

	private Date fechaFin;
	
	private String numeroContrato;
	
	private String objetoContrato;

	private String alcanceContrato;

	private Long codMonedaMonto;
	
	private Long monto;

	private Long codMonedaHonorario;
	
	private Long honorarios;

	private Long codFuenteFinanciacion;
	
	private Long codAdministracionRecurso;
	
	private Long derechoExclusividad;
	
	private Long codSupervisor;
	
	private Date fechaInicioSupervision;

	private String dependenciaSupervision;
	
	public ContratoDTO() {
	}

	public ContratoDTO(long id, EntidadDTO codEntidad, Long codPersona, Long codDependenciaEntidad,
			Long codGrupoDependencia, Long codMunicipio, Date fechaFirma, Date fechaInicio, Date fechaFin,
			String numeroContrato, String objetoContrato, String alcanceContrato, Long codMonedaMonto, Long monto,
			Long codMonedaHonorario, Long honorarios, Long codFuenteFinanciacion, Long codAdministracionRecurso,
			Long derechoExclusividad, Long codSupervisor, Date fechaInicioSupervision, String dependenciaSupervision) {
		super();
		this.id = id;
		this.codEntidad = codEntidad;
		this.codPersona = codPersona;
		this.CodDependenciaEntidad = codDependenciaEntidad;
		this.codGrupoDependencia = codGrupoDependencia;
		this.codMunicipio = codMunicipio;
		this.fechaFirma = fechaFirma;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.numeroContrato = numeroContrato;
		this.objetoContrato = objetoContrato;
		this.alcanceContrato = alcanceContrato;
		this.codMonedaMonto = codMonedaMonto;
		this.monto = monto;
		this.codMonedaHonorario = codMonedaHonorario;
		this.honorarios = honorarios;
		this.codFuenteFinanciacion = codFuenteFinanciacion;
		this.codAdministracionRecurso = codAdministracionRecurso;
		this.derechoExclusividad = derechoExclusividad;
		this.codSupervisor = codSupervisor;
		this.fechaInicioSupervision = fechaInicioSupervision;
		this.dependenciaSupervision = dependenciaSupervision;
		
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public EntidadDTO getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(EntidadDTO codEntidad) {
		this.codEntidad = codEntidad;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public Long getCodDependenciaEntidad() {
		return CodDependenciaEntidad;
	}

	public void setCodDependenciaEntidad(Long codDependenciaEntidad) {
		CodDependenciaEntidad = codDependenciaEntidad;
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

	public Long getcodMonedaMonto() {
		return codMonedaMonto;
	}

	public void setcodMonedaMonto(Long codMonedaMonto) {
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

	public Long getcodFuenteFinanciacion() {
		return codFuenteFinanciacion;
	}

	public void setcodFuenteFinanciacion(Long codFuenteFinanciacion) {
		this.codFuenteFinanciacion = codFuenteFinanciacion;
	}

	public Long getCodAdministracionRecurso() {
		return codAdministracionRecurso;
	}

	public void setCodAdministracionRecurso(Long codAdministracionRecurso) {
		this.codAdministracionRecurso = codAdministracionRecurso;
	}

	public Long getderechoExclusividad() {
		return derechoExclusividad;
	}

	public void setderechoExclusividad(Long derechoExclusividad) {
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

	@Override
	public String toString() {
		return "ContratoDTO [id=" + id + ", codEntidad=" + codEntidad.getId() + ", codPersona=" + codPersona
				+ ", CodDependenciaEntidad=" + CodDependenciaEntidad + ", codGrupoDependencia=" + codGrupoDependencia
				+ ", codMunicipio=" + codMunicipio + ", fechaFirma=" + fechaFirma + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", numeroContrato=" + numeroContrato + ", objetoContrato=" + objetoContrato
				+ ", alcanceContrato=" + alcanceContrato + ", codMonedaMontos=" + codMonedaMonto + ", monto=" + monto
				+ ", codMonedaHonorario=" + codMonedaHonorario + ", honorarios=" + honorarios + ", codFuenteFinanciacion="
				+ codFuenteFinanciacion + ", codAdministracionRecurso=" + codAdministracionRecurso
				+ ", derechoExclusividad=" + derechoExclusividad + ", codSupervisor=" + codSupervisor
				+ ", fechaInicioSupervision=" + fechaInicioSupervision + ", dependenciaSupervision="
				+ dependenciaSupervision + "]";
	}
}



