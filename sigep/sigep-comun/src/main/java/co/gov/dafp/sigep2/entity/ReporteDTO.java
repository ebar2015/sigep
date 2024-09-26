package co.gov.dafp.sigep2.entity;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

/**
 * Entity implementation class for Entity: DetalleMaestro
 *
 */
public class ReporteDTO extends VistaBaseDTO implements Serializable {
	private static final long serialVersionUID = -1403158862285650742L;

	public static final String CONSULTA_ROL_POR_NOMBRE = "CONSULTA ROL POR NOMBRE";
	public static final String CONSULTA_ROL_POR_NOMBRE_PLANTILLA = "consultaRolPorNombre.xml";

	public static final String CONSULTA_ROL_POR_USUARIO = "CONSULTA ROL POR USUARIO";
	public static final String CONSULTA_ROL_POR_USUARIO_PLANTILLA = "consultaRolPorUsuario.xml";

	protected Long id;

	private String descripcion;

	private String plantillaXML;

	private String claseJava;

	private String menu;

	public ReporteDTO() {
		super();
	}

	public ReporteDTO(Long id, String descripcion, String plantillaXML, String claseJava, String menu) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.plantillaXML = plantillaXML;
		this.claseJava = claseJava;
		this.menu = menu;
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

	public String getPlantillaXML() {
		return plantillaXML;
	}

	public void setPlantillaXML(String plantillaXML) {
		this.plantillaXML = plantillaXML;
	}

	public String getClaseJava() {
		return claseJava;
	}

	public void setClaseJava(String claseJava) {
		this.claseJava = claseJava;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "ReporteDTO [id=" + id + ", descripcion=" + descripcion + ", plantillaXML=" + plantillaXML
				+ ", claseJava=" + claseJava + ", menu=" + menu + "]";
	}

}
