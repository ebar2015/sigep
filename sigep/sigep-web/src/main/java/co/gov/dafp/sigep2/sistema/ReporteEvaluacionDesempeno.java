package co.gov.dafp.sigep2.sistema;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.EvaluacionDesempeno;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;


public class ReporteEvaluacionDesempeno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private EvaluacionDesempeno evaluacionDesempeno;
	private String nombreEntidad;
	private PersonaDTO persona;
	
	public ReporteEvaluacionDesempeno() {
	}
	
	public EvaluacionDesempeno getEvaluacionDesempeno() {
		return evaluacionDesempeno;
	}
	public void setEvaluacionDesempeno(EvaluacionDesempeno eval) {
		this.evaluacionDesempeno = eval;
	}
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	public PersonaDTO getPersona() {
		return persona;
	}
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}	
}