package co.gov.dafp.sigep2.view;

import java.io.Serializable;

import javax.persistence.*;

import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

/**
 * Entity implementation class for View: ProcesoArchivo
 *
 */
@Entity(name = "ProcesoArchivo")
@Table(name = "V_PROCESO_ARCHIVO")
@SqlResultSetMappings({
		@SqlResultSetMapping(name = ProcesoArchivo.PROCESO_ARCHIVO_MAPPING, classes = {
				@ConstructorResult(targetClass = ProcesoArchivoDTO.class, columns = {
						@ColumnResult(name = "COD_PROCESO_CARGUE", type = Long.class),
						@ColumnResult(name = "DESCRIPCION", type = String.class),
						@ColumnResult(name = "PLANTILLA_XML", type = String.class),
						@ColumnResult(name = "CLASE_JAVA", type = String.class),
						@ColumnResult(name = "MENU", type = String.class) }) }),
		@SqlResultSetMapping(name = ProcesoArchivo.PROCESO_BACKGROUND_MAPPING, classes = {
				@ConstructorResult(targetClass = ProcesoArchivoDTO.class, columns = {
						@ColumnResult(name = "COD_PROCESO_BACK", type = Long.class),
						@ColumnResult(name = "DESCRIPCION", type = String.class),
						@ColumnResult(name = "PLANTILLA_XML", type = String.class),
						@ColumnResult(name = "CLASE_JAVA", type = String.class) }) }) })

public class ProcesoArchivo extends VistaBase implements Serializable {
	private static final long serialVersionUID = 8202378210619611171L;

	public static final String PROCESO_ARCHIVO_MAPPING = "co.gov.dafp.sigep2.view.mapping.ProcesoArchivo";
	public static final String PROCESO_BACKGROUND_MAPPING = "co.gov.dafp.sigep2.view.mapping.ProcesoBackground2";

	@Id
	@Column(name = "COD_PROCESO_CARGUE", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	protected Long id;

	@Column(insertable = false, updatable = false)
	@Convert(converter = UpperCaseConverter.class)
	private String descripcion;

	@Column(name = "PLANTILLA_XML", insertable = false, updatable = false)
	private String nombrePlantilla;

	@Column(name = "CLASE_JAVA", insertable = false, updatable = false)
	private String interfazProceso;

	@Column(name = "MENU", insertable = false, updatable = false)
	private String componenteMenu;

	public ProcesoArchivo() {
		super();
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

	public String getRecursoMenu() {
		return componenteMenu;
	}

	public void setRecursoMenu(String componenteMenu) {
		this.componenteMenu = componenteMenu;
	}

	@Override
	public String toString() {
		return "ProcesoArchivo [id=" + id + ", descripcion=" + descripcion + ", nombrePlantilla=" + nombrePlantilla
				+ ", interfazProceso=" + interfazProceso + ", componenteMenu=" + componenteMenu + "]";
	}

	@Override
	public VistaBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
