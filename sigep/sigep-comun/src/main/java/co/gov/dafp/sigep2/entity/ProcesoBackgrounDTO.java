package co.gov.dafp.sigep2.entity;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

/**
 * Entity implementation class for Entity: ProcesoBackgrounDTO
 *
 */
public class ProcesoBackgrounDTO extends VistaBaseDTO implements Serializable {
	private static final long serialVersionUID = 7311303255577711203L;

	protected Long id;

	private String descripcion;

	private String nombrePlantilla;

	private String interfazProceso;

	public ProcesoBackgrounDTO() {
		super();
	}

	public ProcesoBackgrounDTO(Long id, String descripcion, String nombrePlantilla, String interfazProceso) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.nombrePlantilla = nombrePlantilla;
		this.interfazProceso = interfazProceso;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public String getInterfazProceso() {
		return interfazProceso;
	}

	public void setInterfazProceso(String interfazProceso) {
		this.interfazProceso = interfazProceso;
	}

	@Override
	public String toString() {
		return "ProcesoBackground [id=" + id + ", descripcion=" + descripcion + ", nombrePlantilla=" + nombrePlantilla
				+ ", interfazProceso=" + interfazProceso + ", fechaCreacion=" + "]";
	}

}
