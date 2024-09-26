package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class ParticipacionProyectoDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long id;
	private long codPersona;
	private String nombreEntidad;
	private String nombreProyecto;
	private String rolLaborado;
	private long codPais;
	private long codDepartamento;
	private long codMunicipio;
	private Date fechaInicio;
	private Date fechaTerminacion;
	
	public ParticipacionProyectoDTO(){
		super();
	}
	
	public ParticipacionProyectoDTO(long id, long codPersona, String nombreEntidad, String nombreProyecto, String rolLaborado, long codPais, long codDepartamento, long codMunicipio, Date fechaInicio, Date fechaTerminacion){
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.nombreEntidad = nombreEntidad;
		this.nombreProyecto = nombreProyecto;
		this.rolLaborado = rolLaborado;
		this.codPais = codPais ;
		this.codDepartamento = codDepartamento;
		this.codMunicipio = codMunicipio;
		this.fechaInicio = fechaInicio;
		this.fechaTerminacion = fechaTerminacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCodParticipacionProyecto() {
		return id;
	}

	public void setCodParticipacionProyecto(Long id) {
		this.id = id;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}
	
	public String getRolLaborado(){
		return rolLaborado;
	}

	public void setRolLaborado(String rolLaborado) {
		this.rolLaborado = rolLaborado;
	}

	public Long getCodPais() {
		return codPais;
	}

	public void setCodPais(Long codPais) {
		this.codPais = codPais;
	}

	public Long getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(Long codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public Long getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(Long codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Date getFechaTerminacion() {
		return fechaTerminacion;
	}
	
	public void setFechaTerminacion(Date fechaTerminacion) {
		this.fechaTerminacion = fechaTerminacion;
	}

	@Override
	public String toString() {
		return id + ',' + codPersona + ',' + nombreEntidad + ',' + nombreProyecto + ',' + rolLaborado + ',' + codPais + ',' + codDepartamento + ',' + codMunicipio +',' + fechaInicio + ',' + fechaTerminacion ;
	}
}