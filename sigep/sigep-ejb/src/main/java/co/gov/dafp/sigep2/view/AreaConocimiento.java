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
import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.view.AreaConocimientoDTO;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

@Entity(name = "AreaConocimiento")
//FALTA CREAR LA VISTA EN BD
@Table(name = "V_AREA_CONOCIMIENTO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = AreaConocimiento.AREA_CONOCIMIENTO_MAPPING, classes = {
		@ConstructorResult(targetClass = AreaConocimientoDTO.class, columns = {
				@ColumnResult(name = "AREA_CONOCIMIENTO_ID", type = Long.class),
				@ColumnResult(name = "SIGLA", type = String.class),
				@ColumnResult(name = "DESCRIPCION", type = String.class) }) }) })

public class AreaConocimiento extends VistaBase implements Serializable {
	private static final long serialVersionUID = -73982710533091665L;

	public static final String AREA_CONOCIMIENTO_MAPPING = "co.gov.dafp.sigep2.view.mapping.AreaConocimiento";

	@Id
	@Column(name = "AREA_CONOCIMIENTO_ID", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	protected Long id;

	@Column(insertable = false, updatable = false)
	@Convert(converter = UpperCaseConverter.class)
	private String sigla;

	@Column(name = "DESCRIPCION", insertable = false, updatable = false)
	private String descripcion;

	public AreaConocimiento() {
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "AreaConocimiento [id=" + id + ", sigla=" + sigla + ", descripcion=" + descripcion + "]";
	}

	@Override
	public VistaBaseDTO getDTO() {
		return new AreaConocimientoDTO(id, sigla, descripcion);
	}
}

