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
import co.gov.dafp.sigep2.entity.view.TipoViaDTO;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

/**
 * Entity implementation class for View: ProcesoArchivo
 *
 */
@Entity(name = "TipoVia")
@Table(name = "V_TIPO_VIA")
@SqlResultSetMappings({ @SqlResultSetMapping(name = TipoVia.TIPO_VIA_MAPPING, classes = {
		@ConstructorResult(targetClass = TipoViaDTO.class, columns = {
				@ColumnResult(name = "TIPO_VIA_ID", type = Long.class),
				@ColumnResult(name = "SIGLA", type = String.class),
				@ColumnResult(name = "DESCRIPCION", type = String.class) }) }) })
public class TipoVia extends VistaBase implements Serializable {
	private static final long serialVersionUID = 8202378210619611171L;

	public static final String TIPO_VIA_MAPPING = "co.gov.dafp.sigep2.view.mapping.TipoVia";

	@Id
	@Column(name = "TIPO_VIA_ID", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	protected Long id;

	@Column(insertable = false, updatable = false)
	@Convert(converter = UpperCaseConverter.class)
	private String sigla;

	@Column(name = "DESCRIPCION", insertable = false, updatable = false)
	private String descripcion;

	public TipoVia() {
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
		return "TipoVia [id=" + id + ", sigla=" + sigla + ", descripcion=" + descripcion + "]";
	}

	@Override
	public VistaBaseDTO getDTO() {
		return new TipoViaDTO(id, sigla, descripcion);
	}

}
