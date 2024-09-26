package co.gov.dafp.sigep2.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

/**
 * Entity implementation class for View: ProcesoArchivo
 *
 */
@Entity(name = "OrientacionSexual")
@Table(name = "V_ORIENTACION_SEXUAL")
@SqlResultSetMappings({ @SqlResultSetMapping(name = OrientacionSexual.ORIENTACION_SEXUAL_MAPPING, classes = {
		@ConstructorResult(targetClass = OrientacionSexualDTO.class, columns = {
				@ColumnResult(name = "ID_TIPO_ORIENTACION", type = Long.class),
				@ColumnResult(name = "PADRE", type = Long.class),
				@ColumnResult(name = "NOMBRE", type = String.class),
				@ColumnResult(name = "VALOR", type = String.class) }) }) })
public class OrientacionSexual extends VistaBase implements Serializable {
	
	private static final long serialVersionUID = 8202378210619611171L;

	public static final String ORIENTACION_SEXUAL_MAPPING = "co.gov.dafp.sigep2.view.mapping.OrientacionSexual";

	@Id
	@Column(name = "ID_TIPO_ORIENTACION", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	protected Long id;

	@Column(name = "PADRE", insertable = false, updatable = false)
	private Long padre;

	@Column(name = "NOMBRE", insertable = false, updatable = false)
	private String nombre;

	@Column(name = "VALOR", insertable = false, updatable = false)
	private String valor;
	
	public OrientacionSexual(){
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
	
	public Long getPadre() {
		return this.padre;
	}

	public void setPadre(Long padre) {
		this.padre = padre;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	

	@Override
	public String toString() {
		return "TipoDocumento [id=" + id + ", nombre=" + nombre + ", padre=" + padre + "]";
	}

	@Override
	public VistaBaseDTO getDTO() {
		return new OrientacionSexualDTO(id, padre, nombre, valor);
	}

}
