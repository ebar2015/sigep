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

import org.hibernate.annotations.Immutable;

import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.view.AccionAuditoriaDTO;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

@Entity(name = "AccionAuditoria")
@Table(name = "V_ACCION_AUDITORIA")
@Immutable
@SqlResultSetMappings({ @SqlResultSetMapping(name = AccionAuditoria.ACCION_AUDITORIA_MAPPING, classes = {
		@ConstructorResult(targetClass = AccionAuditoriaDTO.class, columns = {
				@ColumnResult(name = "ACCION_AUDITORIA_ID", type = Long.class),
				@ColumnResult(name = "SIGLA", type = String.class),
				@ColumnResult(name = "ACCION", type = String.class) }) }) })
public class AccionAuditoria extends VistaBase implements Serializable {

	private static final long serialVersionUID = -2608779179870101625L;

	public static final String ACCION_AUDITORIA_MAPPING = "co.gov.dafp.sigep2.view.mapping.AccionAuditoria";

	@Id
	@Column(name = "ACCION_AUDITORIA_ID", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	protected Long id;

	@Column(insertable = false, updatable = false)
	@Convert(converter = UpperCaseConverter.class)
	private String sigla;

	@Column(name = "ACCION", insertable = false, updatable = false)
	private String accion;

	public AccionAuditoria() {
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
	
	public String getAccion() {
		return accion;
	}

	public void setgetAccion(String accion) {
		this.accion = accion;
	}

	@Override
	public String toString() {
		return "AccionAuditoria [id=" + id + ", sigla=" + sigla + ", accion=" + accion + "]";
	}

	@Override
	public VistaBaseDTO getDTO() {
		return new AccionAuditoriaDTO(id, sigla, accion);
	}
}
