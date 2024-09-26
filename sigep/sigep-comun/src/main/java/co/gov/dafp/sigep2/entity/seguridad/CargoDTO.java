package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class CargoDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = -5454488610684152186L;

	private Long id;

	private Long codDafp;

	private Long grado;

	private String nombreCargo;

	private Long codEntidad;

	private Long asignacionSalarial;

	public CargoDTO() {
		super();
	}

	public CargoDTO(Long id, Long codDafp, Long grado, String nombreCargo, Long codEntidad, Long asignacionSalarial) {
		super();
		this.id = id;
		this.codDafp = codDafp;
		this.grado = grado;
		this.nombreCargo = nombreCargo;
		this.codEntidad = codEntidad;
		this.asignacionSalarial = asignacionSalarial;
	}

	public CargoDTO(Long id, Long codDafp, Long grado, String nombreCargo, Long asignacionSalarial) {
		super();
		this.id = id;
		this.codDafp = codDafp;
		this.grado = grado;
		this.nombreCargo = nombreCargo;
		this.asignacionSalarial = asignacionSalarial;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getcodDafp() {
		return codDafp;
	}

	public void setcodDafp(Long codDafp) {
		this.codDafp = codDafp;
	}

	public Long getGrado() {
		return grado;
	}

	public void setGrado(Long grado) {
		this.grado = grado;
	}

	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public Long getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	public Long getAsignacionSalarial() {
		return asignacionSalarial;
	}

	public void setAsignacionSalarial(Long asignacionSalarial) {
		this.asignacionSalarial = asignacionSalarial;
	}

	@Override
	public String toString() {
		return "CargoDTO [id=" + id + ", codigoDafp=" + codDafp + ", grado=" + grado + ", nombreCargo=" + nombreCargo
				+ ", codEntidad=" + codEntidad + ", asignacionSalarial=" + asignacionSalarial + "]";
	}

}
