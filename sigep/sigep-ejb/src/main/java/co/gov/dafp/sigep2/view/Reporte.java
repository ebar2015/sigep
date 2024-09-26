package co.gov.dafp.sigep2.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.ReporteDTO;
import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

/**
 * Entity implementation class for Entity: DetalleMaestro
 *
 */
@Entity
@Table(name = "V_REPORTE")
@SqlResultSetMappings({ @SqlResultSetMapping(name = Reporte.REPORTE_MAPPING, classes = {
		@ConstructorResult(targetClass = ReporteDTO.class, columns = {
				@ColumnResult(name = "COD_REPORTE", type = Long.class),
				@ColumnResult(name = "DESCRIPCION", type = String.class),
				@ColumnResult(name = "PLANTILLA_XML", type = String.class),
				@ColumnResult(name = "CLASE_JAVA", type = String.class),
				@ColumnResult(name = "MENU", type = String.class) }) }) })
public class Reporte extends VistaBase implements Serializable {
	private static final long serialVersionUID = -8197702153161490028L;

	public static final String REPORTE_MAPPING = "co.gov.dafp.sigep2.view.mapping.Reporte";

	@Id
	@Column(name = "COD_REPORTE", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	protected long id;

	@Column(insertable = false, updatable = false)
	@Convert(converter = UpperCaseConverter.class)
	private String descripcion;

	@Column(name = "PLANTILLA_XML", insertable = false, updatable = false)
	private String plantillaXML;

	@Column(name = "CLASE_JAVA", insertable = false, updatable = false)
	private String claseJava;

	@Column(name = "MENU", insertable = false, updatable = false)
	private String menu;

	public Reporte() {
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

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Reporte [id=" + id + ", descripcion=" + descripcion + ", plantillaXML=" + plantillaXML + ", claseJava="
				+ claseJava + ", menu=" + menu + "]";
	}

	@Override
	public VistaBaseDTO getDTO() {
		return new ReporteDTO(id, descripcion, plantillaXML, claseJava, menu);
	}

}
