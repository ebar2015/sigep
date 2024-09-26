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
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;

@Entity(name = "Municipio")
@Table(name = "MUNICIPIO")
@SqlResultSetMappings({ @SqlResultSetMapping(name = Municipio.MUNICIPIO_MAPPING, classes = {
		@ConstructorResult(targetClass = MunicipioDTO.class, columns = {
				@ColumnResult(name = "COD_MUNICIPIO", type = Long.class),
				@ColumnResult(name = "COD_DEPARTAMENTO", type = Long.class),
				@ColumnResult(name = "COD_MUNICIPIO_DANE", type = Long.class),
				@ColumnResult(name = "NOMBRE_MUNICIPIO", type = String.class),
				@ColumnResult(name = "FLG_ACTIVO", type = Boolean.class) }) }) })
public class Municipio extends EntidadBase {
	
	private static final long serialVersionUID = 7764534595239575268L;
	
	public static final String MUNICIPIO_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.Municipio";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_MUNICIPIO", insertable = false, updatable = false, unique = true, nullable = false)
	protected Long codMunicipio;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Departamento.class)
	@JoinColumn(name = "COD_DEPARTAMENTO")
	protected Departamento codDepartamento;
	
	@Column(name = "COD_MUNICIPIO_DANE", length = 126)
	protected Long codMunicipioDane;
	
	@Column(name = "NOMBRE_MUNICIPIO", length = 126)
	protected String nombreMunicipio;
	
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
		return codMunicipio;
	}

	@Override
	public void setId(Long id) {
		this.codMunicipio = id;
	}

	public Long getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(Long codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public Departamento getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(Departamento codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public Long getCodMunicipioDane() {
		return codMunicipioDane;
	}

	public void setCodMunicipioDane(Long codMunicipioDane) {
		this.codMunicipioDane = codMunicipioDane;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
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
		return "Municipio [codMunicipio=" + codMunicipio + ", codDepartamento=" + codDepartamento +  ", codMunicipioDane=" + codMunicipioDane + ", nombreMunicipio=" + nombreMunicipio + ", flgActivo=" + flgActivo + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new MunicipioDTO(codMunicipio, codDepartamento.getId(), codMunicipioDane, nombreMunicipio, flgActivo);
	}
}