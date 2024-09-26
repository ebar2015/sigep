package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class FormacionTrabajoDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long id;
	private long codPersona;
	private long codTipoFormacion;
	private long intensidadHoras;
	private String nombreInstitucion;
	private long codPais;
	private long codDepartamento;
	private long codMunicipio;
	private Date fechaInicio;
	private Date fechaFin;
	
	public FormacionTrabajoDTO(){
		super();
	}
	
	public FormacionTrabajoDTO(long codFormacionTrabajo, long codPersona, long codTipoFormacion, long intensidadHoras, String nombreInstitucion, long codPais, long codDepartamento, long codMunicipio, Date fechaInicio, Date fechaFin){
		super();
		this.id = codFormacionTrabajo;
		this.codPersona = codPersona;
		this.codTipoFormacion = codTipoFormacion;
		this.intensidadHoras = intensidadHoras;
		this.nombreInstitucion = nombreInstitucion;
		this.codPais = codPais ;
		this.codDepartamento = codDepartamento;
		this.codMunicipio = codMunicipio;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	public Long getCodTipoFormacion() {
		return codTipoFormacion;
	}

	public void setCodTipoFormacion(Long codTipoFormacion) {
		this.codTipoFormacion = codTipoFormacion;
	}

	public Long getIntensidadHoras() {
		return intensidadHoras;
	}

	public void setIntensidadHoras(Long intensidadHoras) {
		this.intensidadHoras = intensidadHoras;
	}
	
	public void setNombreInstitucion(String nombreInstitucion ){
		this.nombreInstitucion = nombreInstitucion;
	}
	
	public String getNombreInstitucion(){
		return nombreInstitucion;
	}

	public void setCodPais(long codPais) {
		this.codPais = codPais;
	}

	public Long getCodPais() {
		return codPais;
	}

	public Long getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(Long codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public void setCodMunicipio(Long codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public Long getCodMunicipio() {
		return codMunicipio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public Date getFechaTerminacion() {
		return fechaFin;
	}

	public void setFechaTerminacion(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String toString() {
		return id + ',' + codPersona + ',' + codTipoFormacion + ',' + nombreInstitucion + ',' + intensidadHoras + ',' + codPais + ',' + codDepartamento + ',' + codMunicipio + ',' + fechaInicio +',' + fechaFin;
	}
}