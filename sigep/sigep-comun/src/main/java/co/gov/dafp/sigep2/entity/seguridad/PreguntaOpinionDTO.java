package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class PreguntaOpinionDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long id;
	
	private String pregunta;

	private Date fechaInicio;

	private Date fechaFin;
	
	

	public PreguntaOpinionDTO(){
		super();
	}
	
	public PreguntaOpinionDTO( long id, String pregunta, Date fechaInicio, Date fechaFin){
		super();
		this.id = id;
		this.pregunta = pregunta;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
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

	@Override
	public String toString() {
		return "PreguntaOpinioDTO [id=" + this.getId() + "]";
	}
}
