package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;


public class LogroRecursoDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;
	
	private long codLogroRecurso;
	
	private long codPersona;
	
	private boolean flgAdministraRecursos;
	
	private String nombreEntidad;
	
	private long numEmpleados;
	
	private boolean flgPersonasCargo;
	
	private long numPersonasCargo;
	
	private boolean flgRecursoEconomico;
	
	private double valorRecursoEconomico;
	
	private String logroSobresaliente;
	
	public LogroRecursoDTO() {
		
	}
	
	public LogroRecursoDTO(long codLogroRecurso, long codPersona, boolean flgAdministraRecursos, String nombreEntidad, long numEmpleados, boolean flgPersonasCargo, long numPersonasCargo, boolean flgRecursoEconomico, double valorRecursoEconomico, String logroSobresaliente) {
		super();
		this.codLogroRecurso = codLogroRecurso;
		this.codPersona = codPersona;
		this.flgAdministraRecursos=flgAdministraRecursos;
		this.nombreEntidad=nombreEntidad;
		this.numEmpleados=numEmpleados;
		this.flgPersonasCargo=flgPersonasCargo;
		this.numPersonasCargo=numPersonasCargo;
		this.flgRecursoEconomico=flgRecursoEconomico;
		this.valorRecursoEconomico=valorRecursoEconomico;
		this.logroSobresaliente=logroSobresaliente;
	}

	public long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	public long getId() {
		return this.codLogroRecurso;
	}

	@Override
	public void setId(long codLogroRecurso) {	
		this.codLogroRecurso = codLogroRecurso;
	}

	public Boolean getflgAdministraRecursos() {
		return flgAdministraRecursos;
	}

	public void setflgAdministraRecursos(boolean flgAdministraRecursos) {
		this.flgAdministraRecursos = flgAdministraRecursos;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public Long getNumEmpleados() {
		return numEmpleados;
	}

	public void seNumEmpleados(Long numEmpleados) {
		this.numEmpleados = numEmpleados;
	}

	public Boolean getFlgPersonasCargo() {
		return flgPersonasCargo;
	}

	public void setFlgPersonasCargo(Boolean flgPersonasCargo) {
		this.flgPersonasCargo = flgPersonasCargo;
	}

	public Long getNumPersonasCargo() {
		return numPersonasCargo;
	}

	public void setNumPersonasCargo(Long numPersonasCargo) {
		this.numPersonasCargo = numPersonasCargo;
	}

	public Boolean getFlgRecursoEconomico() {
		return flgRecursoEconomico;
	}

	public void setFlgRecursoEconomico(Boolean flgRecursoEconomico) {
		this.flgRecursoEconomico = flgRecursoEconomico;
	}

	public Double getValorRecursoEconomico() {
		return valorRecursoEconomico;
	}

	public void setvalorRecursoEconomico(Double valorRecursoEconomico) {
		this.valorRecursoEconomico = valorRecursoEconomico;
	}

	public String getLogroSobresaliente() {
		return logroSobresaliente;
	}

	public void setLogroSobresaliente(String logroSobresaliente) {
		this.logroSobresaliente = logroSobresaliente;
	}
	
	@Override
	public String toString() {
		return "Logro recursoDTO [id=" + this.getId() + "codPersona=" + this.getCodPersona() + "]";
	}
}