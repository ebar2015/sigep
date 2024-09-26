package co.gov.dafp.sigep2.entity.view;

import java.io.Serializable;

/**
 * Entity implementation class for Entity: ProcesoArchivoDTO
 *
 */

public class ProcesoArchivoDTO extends VistaBaseDTO implements Serializable {
	private static final long serialVersionUID = -2418909152174324767L;

	public static final String CARGUE_CLASE = "co.gov.dafp.sigep2.util.sistema.CargueArchivoDefault";
	public static final String CARGUE_CREAR_USUARIOS = "CARGUE CREAR USUARIOS";
	public static final String CARGUE_CREAR_USUARIOS_PLANTILLA = "cargueCrearUsuario.xml";
	public static final String CARGUE_ACTIVACION_USUARIOS = "CARGUE ACTIVACION USUARIOS";
	public static final String CARGUE_ACTIVACION_USUARIOS_PLANTILLA = "cargueActivacionUsuario.xml";

	protected long id;

	private String descripcion;

	private String nombrePlantilla;

	private String interfazProceso;

	private String componenteMenu;

	public ProcesoArchivoDTO() {
		super();
	}

	public ProcesoArchivoDTO(long id, String descripcion, String nombrePlantilla, String interfazProceso,
			String componenteMenu) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.nombrePlantilla = nombrePlantilla;
		this.interfazProceso = interfazProceso;
		this.componenteMenu = componenteMenu;
	}

	public ProcesoArchivoDTO(Long id, String descripcion, String nombrePlantilla, String interfazProceso) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.nombrePlantilla = nombrePlantilla;
		this.interfazProceso = interfazProceso;
	}

	public Long getId() {
		return id;
	}

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

	public String getComponenteMenu() {
		return componenteMenu;
	}

	public void setComponenteMenu(String componenteMenu) {
		this.componenteMenu = componenteMenu;
	}

	@Override
	public String toString() {
		return "ProcesoArchivoDTO [id=" + id + ", descripcion=" + descripcion + ", nombrePlantilla=" + nombrePlantilla
				+ ", interfazProceso=" + interfazProceso + ", componenteMenu=" + componenteMenu + "]";
	}

}
