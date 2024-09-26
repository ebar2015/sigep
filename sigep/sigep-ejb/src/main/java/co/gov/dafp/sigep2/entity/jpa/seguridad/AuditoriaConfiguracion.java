package co.gov.dafp.sigep2.entity.jpa.seguridad;

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
import co.gov.dafp.sigep2.entity.seguridad.AuditoriaConfiguracionDTO;

@Entity(name = "AuditoriaConfiguracion")
@Table(name="AUDITORIA_CONFIGURACION")
@SqlResultSetMappings({ @SqlResultSetMapping(name = AuditoriaConfiguracion.AUDITORIA_CONFIGURACION_MAPPING, classes = {
		@ConstructorResult(targetClass = AuditoriaConfiguracionDTO.class, columns = {
				@ColumnResult(name = "COD_TABLA_AUDITORIA", type = Long.class),
				@ColumnResult(name = "NOMBRE_TABLA", type = String.class),
				@ColumnResult(name = "DESCRIPCION_TABLA", type = String.class),
				@ColumnResult(name = "FLG_INSERT", type = Boolean.class),
				@ColumnResult(name = "FLG_DELETE", type = Boolean.class),
				@ColumnResult(name = "FLG_UPDATE", type = Boolean.class),
				@ColumnResult(name = "FLG_SELECT", type = Boolean.class),
				@ColumnResult(name = "FECHA_INICIO_AUDITORIA", type = Date.class),
				@ColumnResult(name = "FECHA_FIN_AUDITORIA", type = Date.class)
				 }) }) })
public class AuditoriaConfiguracion extends EntidadBase implements java.io.Serializable {

	private static final long serialVersionUID = -5564243003695207463L;

	public static final String AUDITORIA_CONFIGURACION_MAPPING="co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.AuditoriaConfiguracion";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_TABLA_AUDITORIA", unique = true, nullable = false, precision = 22, scale = 0)
	private long codTablaAuditoria;
	
	@Column(name = "NOMBRE_TABLA", length = 50)
	private String nombreTabla;
	
	@Column(name = "DESCRIPCION_TABLA", length = 2000)
	private String descripcionTabla;

	@Column(name = "FLG_INSERT", nullable = true, precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgInsert = true;

	@Column(name = "FLG_DELETE", nullable = true,precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgDelete = true;

	@Column(name = "FLG_UPDATE", nullable = true, precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgUpdate = true;

	@Column(name = "FLG_SELECT", nullable = true,precision = 1, scale = 0)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean flgSelect = true;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INICIO_AUDITORIA", precision = 22, scale = 0)
	private Date fechaInicioAuditoria;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN_AUDITORIA", precision = 22, scale = 0)
	private Date fechaFinAuditoria;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false,precision = 22, scale = 0)
	protected Long audAccion;

	
	
	
	public AuditoriaConfiguracion() {
		super();
	}

	public AuditoriaConfiguracion(long codTablaAuditoria, String nombreTabla,String descripcionTabla, Boolean flgInsert, Boolean flgDelete,
			Boolean flgUpdate, Boolean flgSelect, Date fechaInicioAuditoria, Date fechaFinAuditoria) {
		super();
		this.codTablaAuditoria = codTablaAuditoria;
		this.nombreTabla = nombreTabla;
		this.descripcionTabla=descripcionTabla;
		this.flgInsert = flgInsert;
		this.flgDelete = flgDelete;
		this.flgUpdate = flgUpdate;
		this.flgSelect = flgSelect;
		this.fechaInicioAuditoria = fechaInicioAuditoria;
		this.fechaFinAuditoria = fechaFinAuditoria;
	}

	public Long getId() {
		return this.codTablaAuditoria;
	}

	public void setId(Long id) {
		this.codTablaAuditoria = id;
	}
	
	public long getCodTablaAuditoria() {
		return codTablaAuditoria;
	}

	public void setCodTablaAuditoria(long codTablaAuditoria) {
		this.codTablaAuditoria = codTablaAuditoria;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}
	
	public String getDescripcionTabla() {
		return descripcionTabla;
	}
	
	public void setDescripcionTabla(String descripcionTabla) {
		this.descripcionTabla = descripcionTabla;
	}

	public Boolean getFlgInsert() {
		return flgInsert;
	}

	public void setFlgInsert(Boolean flgInsert) {
		this.flgInsert = flgInsert;
	}

	public Boolean getFlgDelete() {
		return flgDelete;
	}

	public void setFlgDelete(Boolean flgDelete) {
		this.flgDelete = flgDelete;
	}

	public Boolean getFlgUpdate() {
		return flgUpdate;
	}

	public void setFlgUpdate(Boolean flgUpdate) {
		this.flgUpdate = flgUpdate;
	}

	public Boolean getFlgSelect() {
		return flgSelect;
	}

	public void setFlgSelect(Boolean flgSelect) {
		this.flgSelect = flgSelect;
	}

	public Date getFechaInicioAuditoria() {
		return fechaInicioAuditoria;
	}

	public void setFechaInicioAuditoria(Date fechaInicioAuditoria) {
		this.fechaInicioAuditoria = fechaInicioAuditoria;
	}

	public Date getFechaFinAuditoria() {
		return fechaFinAuditoria;
	}

	public void setFechaFinAuditoria(Date fechaFinAuditoria) {
		this.fechaFinAuditoria = fechaFinAuditoria;
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
		return "AuditoriaConfiguracion [codTablaAuditoria=" + codTablaAuditoria + ", nombreTabla=" + nombreTabla
				+ ", flgInsert=" + flgInsert + ", flgDelete=" + flgDelete + ", flgUpdate=" + flgUpdate + ", flgSelect="
				+ flgSelect + ", fechaInicioAuditoria=" + fechaInicioAuditoria + ", fechaFinAuditoria="
				+ fechaFinAuditoria + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new AuditoriaConfiguracionDTO(this.getCodTablaAuditoria(),this.getNombreTabla(),this.getDescripcionTabla(),this.getFlgInsert(),this.getFlgDelete(),this.getFlgUpdate(),this.getFlgSelect(),this.getFechaInicioAuditoria(),this.getFechaFinAuditoria());
	}
	
	
	
	
	
}
