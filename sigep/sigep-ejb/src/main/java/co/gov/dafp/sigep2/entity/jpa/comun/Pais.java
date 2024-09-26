package co.gov.dafp.sigep2.entity.jpa.comun;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;

@Entity(name = "Pais")
@Table(name = "PAIS")
@SqlResultSetMappings({ @SqlResultSetMapping(name = Pais.PAIS_MAPPING, classes = {
		@ConstructorResult(targetClass = PaisDTO.class, columns = {
				@ColumnResult(name = "COD_PAIS", type = Long.class),
				@ColumnResult(name = "NOMBRE_PAIS", type = String.class),
				@ColumnResult(name = "FLG_ACTIVO", type = Boolean.class) }) }) })
public class Pais extends EntidadBase {
	
	private static final long serialVersionUID = 7764534595239575268L;
	
	public static final String PAIS_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.Pais";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_PAIS", insertable = false, updatable = false, unique = true, nullable = false)
	protected Long codPais;
	
	@Column(name = "NOMBRE_PAIS", length = 126)
	protected String nombrePais;
	
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
	
	public Pais() {}
	
	public Pais( long codPais, String nombrePais, boolean flgActivo){
		super();
		this.codPais = codPais;
		this.nombrePais = nombrePais;
		this.flgActivo = flgActivo;
	}
	
	@Override
	public Long getId() {
		return codPais;
	}

	@Override
	public void setId(Long id) {
		this.codPais = id;
	}
	
	public Long getCodPais() {
		return codPais;
	}

	public void setCodPais(Long codPais) {
		this.codPais = codPais;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
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
		return "Pais [codPais=" + codPais + ", nombrePais=" + nombrePais + ", flgActivo=" + flgActivo + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new PaisDTO(codPais, nombrePais, flgActivo);
	}
}