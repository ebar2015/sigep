package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class EvaluacionDesempenoDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long id;
	private long codPersona;
	private long codEntidad;
	private String cargoEntidadPublica;
	private String cargoEntidadPrivada;
	private long calificacionObtenida;
	private long escalaEvaluacion;
	private Date fechaInicioEvaluacion;
	private Date fechaFinEvaluacion;
	
	public EvaluacionDesempenoDTO(){
		super();
	}
	
	public EvaluacionDesempenoDTO(long id, long codPersona, long codEntidad, String cargoEntidadPublica, String cargoEntidadPrivada, long calificacionObtenida, long escalaEvaluacion, Date fechaInicio, Date fechaFin){
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.codEntidad = codEntidad;
		this.cargoEntidadPublica = cargoEntidadPublica;
		this.cargoEntidadPrivada = cargoEntidadPrivada;
		this.calificacionObtenida = calificacionObtenida ;
		this.escalaEvaluacion = escalaEvaluacion;
		this.fechaInicioEvaluacion = fechaInicio;
		this.fechaFinEvaluacion = fechaFin;
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

	public long getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(long codEntidad) {
		this.codEntidad = codEntidad;
	}

	public String getCargoEntidadPublica() {
		return cargoEntidadPublica;
	}
	public void setCargoEntidadPublica(String cargoEntidadPublica) {
		this.cargoEntidadPublica = cargoEntidadPublica;
	}
	public String getCargoEntidadPrivada() {
		return cargoEntidadPrivada;
	}
	public void setCargoEntidadPrivada(String cargoEntidadPrivada) {
		this.cargoEntidadPrivada = cargoEntidadPrivada;
	}

	public long getCalificacionObtenida() {
		return calificacionObtenida;
	}

	public void setResultado(long calificacionObtenida) {
		this.calificacionObtenida = calificacionObtenida;
	}

	public long getEscalaEvaluacion() {
		return escalaEvaluacion;
	}

	public void setEscalaEvaluacion(long escalaEvaluacion) {
		this.escalaEvaluacion = escalaEvaluacion;
	}

	public Date getFechaInicio() {
		return fechaInicioEvaluacion;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicioEvaluacion = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFinEvaluacion;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFinEvaluacion = fechaFin;
	}

	@Override
	public String toString() {
		return id + ',' + codPersona + ',' + codEntidad + ',' + cargoEntidadPublica + ',' + cargoEntidadPrivada + ',' + calificacionObtenida + ',' + escalaEvaluacion + ',' + fechaInicioEvaluacion +',' + fechaFinEvaluacion;
	}
}