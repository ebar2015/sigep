package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
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

import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.seguridad.CargoDTO;

@Entity(name = "Cargo")
@Table(name = "CARGO")
@SqlResultSetMappings({
		@SqlResultSetMapping(name = Cargo.CARGO_MAPPING, classes = {
				@ConstructorResult(targetClass = CargoDTO.class, columns = {
						@ColumnResult(name = "COD_CARGO", type = Long.class),
						@ColumnResult(name = "CODIGO_DAFP", type = Long.class),
						@ColumnResult(name = "COD_GRADO", type = Long.class),
						@ColumnResult(name = "NOMBRE_CARGO", type = String.class),
						@ColumnResult(name = "COD_ENTIDAD", type = Long.class),
						@ColumnResult(name = "ASIGNACION_SALARIAL", type = Long.class) }) }),
		@SqlResultSetMapping(name = Cargo.CARGO_NO_ENTIDAD_MAPPING, classes = {
				@ConstructorResult(targetClass = CargoDTO.class, columns = {
						@ColumnResult(name = "COD_CARGO", type = Long.class),
						@ColumnResult(name = "CODIGO_DAFP", type = Long.class),
						@ColumnResult(name = "COD_GRADO", type = Long.class),
						@ColumnResult(name = "NOMBRE_CARGO", type = String.class),
						@ColumnResult(name = "ASIGNACION_SALARIAL", type = Long.class) }) }) })

public class Cargo extends EntidadBase implements Serializable {
	private static final long serialVersionUID = -6630522407247554557L;

	public static final String CARGO_MAPPING = "co.gov.dafp.sigep2.view.mapping.Cargo";
	public static final String CARGO_NO_ENTIDAD_MAPPING = "co.gov.dafp.sigep2.view.mapping.CargoNoEntidad";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_CARGO", insertable = false, updatable = false, unique = true, nullable = false)
	private long codCargo;

	@Column(name = "CODIGO_DAFP", precision = 22, scale = 0)
	private Long codDafp;

	@Column(name = "COD_GRADO", precision = 22, scale = 0)
	private Long grado;

	@Column(name = "NOMBRE_CARGO", nullable = false, length = 255)
	private String nombreCargo;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Entidad.class)
	@JoinColumn(name = "COD_ENTIDAD", nullable = true)
	private Entidad codEntidad;

	@Column(name = "ASIGNACION_SALARIAL", precision = 22, scale = 0)
	private Long asignacionSalarial;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	public Cargo() {
		super();
	}

	public Cargo(long codCargo, Long codDafp, Long grado, String nombreCargo, Entidad codEntidad,
			Long asignacionSalarial, Date audFechaActualizacion, Long audCodUsuario, Long audCodRol, String audAccion) {
		super();
		this.codCargo = codCargo;
		this.codDafp = codDafp;
		this.grado = grado;
		this.nombreCargo = nombreCargo;
		this.codEntidad = codEntidad;
		this.asignacionSalarial = asignacionSalarial;
		this.audFechaActualizacion = audFechaActualizacion;
		this.audCodUsuario = audCodUsuario;
		this.audCodRol = audCodRol;
		this.audAccion = Long.valueOf(audAccion);
	}

	public Long getId() {
		return this.codCargo;
	}

	public void setId(Long id) {
		this.codCargo = id;

	}

	public long getCodCargo() {
		return codCargo;
	}

	public void setCodCargo(long codCargo) {
		this.codCargo = codCargo;
	}

	public Long getCodDafp() {
		return codDafp;
	}

	public void setCodDafp(Long codDafp) {
		this.codDafp = codDafp;
	}

	public Long getGrado() {
		return grado;
	}

	public void setGrado(Long grado) {
		this.grado = grado;
	}

	public String getNombreCargo() {
		return nombreCargo;
	}

	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	public Entidad getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Entidad codEntidad) {
		this.codEntidad = codEntidad;
	}

	public Long getAsignacionSalarial() {
		return asignacionSalarial;
	}

	public void setAsignacionSalarial(Long asignacionSalarial) {
		this.asignacionSalarial = asignacionSalarial;
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
		return "Cargo [codCargo=" + codCargo + ", codDafp=" + codDafp + ", grado=" + grado + ", nombreCargo="
				+ nombreCargo + ", codEntidad=" + codEntidad + ", asignacionSalarial=" + asignacionSalarial
				+ ", audFechaActualizacion=" + audFechaActualizacion + ", audCodUsuario=" + audCodUsuario
				+ ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new CargoDTO(codCargo, codDafp, grado, nombreCargo, codEntidad.getId(), asignacionSalarial);
	}

}
