package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.io.Serializable;
import javax.persistence.*;

import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.util.DateUtils;

import java.util.Date;

/**
 * The persistent class for the LOG_AUDITORIA database table.
 *
 */
@Entity(name = "LogAuditoria")
@Table(name = "LOG_AUDITORIA")
@NamedQueries({ @NamedQuery(name = LogAuditoria.FIND_ALL, query = "SELECT la FROM LogAuditoria la"),
		@NamedQuery(name = LogAuditoria.FIND_ALL_ENTIDADES, query = "SELECT DISTINCT la.entidad FROM LogAuditoria la ORDER BY la.entidad"),
		@NamedQuery(name = LogAuditoria.FIND_BY_ENTIDAD, query = "SELECT la FROM LogAuditoria la WHERE UPPER(la.entidad) = UPPER(:entidad) ORDER BY la.fechaRegistro DESC"),
		@NamedQuery(name = LogAuditoria.FIND_BY_FECHA, query = "SELECT la FROM LogAuditoria la WHERE la.fechaRegistro BETWEEN :fechaInicial AND :fechaFinal ORDER BY la.fechaRegistro DESC"),
		@NamedQuery(name = LogAuditoria.FIND_BY_USUARIO, query = "SELECT la FROM LogAuditoria la WHERE la.usuarioId = :usuarioId") })
public class LogAuditoria extends EntidadBase implements Serializable {
	private static final long serialVersionUID = -7584842705520055309L;

	public static final String FIND_ALL = "co.gov.dafp.sigep2.entity.LogAuditoria.findAll";
	public static final String FIND_ALL_ENTIDADES = "co.gov.dafp.sigep2.entity.LogAuditoria.findAllEntidades";
	public static final String FIND_BY_FECHA = "co.gov.dafp.sigep2.entity.LogAuditoria.findByFecha";
	public static final String FIND_BY_USUARIO = "co.gov.dafp.sigep2.entity.LogAuditoria.findByUsuario";
	public static final String FIND_BY_ENTIDAD = "co.gov.dafp.sigep2.entity.LogAuditoria.findByEntidad";

	public static final String SELECT_FIND_ALL = "SELECT la FROM LogAuditoria la WHERE 1=1";
	public static final String ORDER_BY_DESC_FECHA_REGISTRO = " ORDER BY la.fechaRegistro DESC";
	public static final String AND_FIND_BY_FECHA = " AND la.fechaRegistro BETWEEN :fechaInicial AND :fechaFinal";
	public static final String AND_FIND_BY_USUARIO = " AND la.usuarioId = :usuarioId";
	public static final String AND_FIND_BY_ENTIDAD = " AND UPPER(la.entidad) = UPPER(:entidad)";

	@Id
	@Column(unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(updatable = false)
	private String entidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_REGISTRO", updatable = false)
	private Date fechaRegistro;

	@ManyToOne(targetEntity = Usuario.class)
	@JoinColumn(updatable = false)
	private Usuario usuarioId;

	@Column(name = "VALOR_ACTUAL", updatable = false)
	private String valorActual;

	@Column(name = "VALOR_ANTERIOR", updatable = false)
	private String valorAnterior;
	
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
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Usuario getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getValorActual() {
		return valorActual;
	}

	public void setValorActual(String valorActual) {
		this.valorActual = valorActual;
	}

	public String getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = valorAnterior;
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
		return "LogAuditoria [id=" + id + ", entidad=" + entidad + ", fechaRegistro="
				+ DateUtils.formatearACadenaLarga(fechaRegistro) + ", usuarioId=" + usuarioId + ", valorActual="
				+ valorActual + ", valorAnterior=" + valorAnterior + ", fechaCreacion="
				+ "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
