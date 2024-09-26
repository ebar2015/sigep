package co.gov.dafp.sigep2.entity.jpa.comun;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

@Entity(name = "Departamento")
@Table(name = "DEPARTAMENTO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = Departamento.DEPARTAMENTO_MAPPING, classes = {
		@ConstructorResult(targetClass = DepartamentoDTO.class, columns = {
				@ColumnResult(name = "COD_DEPARTAMENTO", type = Long.class),
				@ColumnResult(name = "COD_PAIS", type = Long.class),
				@ColumnResult(name = "NOMBRE_DEPARTAMENTO", type = String.class),
				@ColumnResult(name = "FLG_ACTIVO", type = Boolean.class) }) }) })
public class Departamento extends EntidadBase {
	
	private static final long serialVersionUID = 7764534595239575268L;
	
	public static final String DEPARTAMENTO_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.Departamento";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_DEPARTAMENTO", insertable = false, updatable = false, unique = true, nullable = false)
	protected Long codDepartamento;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Pais.class)
	@JoinColumn(name = "COD_PAIS")
	protected Pais codPais;
	
	@Column(name = "NOMBRE_DEPARTAMENTO", length = 126)
	protected String nombreDepartamento;
	
	@Column(name = "FLG_ACTIVO", precision = 22, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	protected boolean flgActivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;
	
	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;
	
	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;
	
	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;
	
	@Override
	public Long getId() {
		return codDepartamento;
	}

	@Override
	public void setId(Long id) {
		this.codDepartamento = id;
	}

	public Long getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(Long codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public Pais getCodPais() {
		return codPais;
	}

	public void setCodPais(Pais codPais) {
		this.codPais = codPais;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public boolean isFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	public Long getAudCodUsuario() {
		return audCodUsuario;
	}

	public void setAudCodUsuario(Long audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	public Long getAudCodRol() {
		return audCodRol;
	}

	public void setAudCodRol(Long audCodRol) {
		this.audCodRol = audCodRol;
	}

	public Long getAudAccion() {
		return audAccion;
	}

	public void setAudAccion(Long audAccion) {
		this.audAccion = audAccion;
	}

	@Override
	public String toString() {
		return "Departamento [codDepartamento=" + codDepartamento + ", codPais=" + codPais + ", nombreDepartamento=" + nombreDepartamento + ", flgActivo=" + flgActivo + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new DepartamentoDTO(codDepartamento, codPais.getId(), nombreDepartamento, flgActivo);
	}
}