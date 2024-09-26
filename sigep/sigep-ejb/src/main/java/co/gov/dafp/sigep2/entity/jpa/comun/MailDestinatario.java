package co.gov.dafp.sigep2.entity.jpa.comun;

import java.util.Date;

import javax.persistence.*;

import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

@Entity(name = "MailDestinatario")
// @Table(name = "MAIL_DESTINATARIO")
// @NamedQueries({
// @NamedQuery(name = MailDestinatario.FIND_ALL, query = "SELECT m FROM
// MailDestinatario m WHERE m.estado = :estado ORDER BY m.tipoBandeja") })

public class MailDestinatario extends EntidadBase {
	private static final long serialVersionUID = -6798167350402197841L;

	public static final String FIND_ALL = "co.gov.dafp.sigep2.entity.MailDestinatario.findAll";

	@Id
	// @Column(name = "ID", unique = true, nullable = false, precision = 38,
	// insertable = false, updatable = false)
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// @JoinColumn(name = "MAIL_ID", referencedColumnName = "ID", nullable =
	// false, updatable = false)
	// @ManyToOne(fetch = FetchType.EAGER, targetEntity = Mail.class)
	@Transient
	private Mail mailId;

	// @Column(name = "TIPO_BANDEJA", nullable = false, updatable = false)
	// @Convert(converter = UpperCaseConverter.class)
	@Transient
	private String tipoBandeja;

	// @Column(name = "CUENTA_CORREO", updatable = false)
	// @Convert(converter = LowerCaseConverter.class)
	@Transient
	private String cuentaCorreo;

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

	public Mail getMailId() {
		return mailId;
	}

	public void setMailId(Mail mailId) {
		this.mailId = mailId;
	}

	public String getTipoBandeja() {
		return tipoBandeja;
	}

	public void setTipoBandeja(String tipoBandeja) {
		this.tipoBandeja = tipoBandeja;
	}

	public String getCuentaCorreo() {
		return cuentaCorreo;
	}

	public void setCuentaCorreo(String cuentaCorreo) {
		this.cuentaCorreo = cuentaCorreo;
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
		return "MailDestinatario [id=" + id + ", mailId=" + mailId + ", tipoBandeja=" + tipoBandeja + ", cuentaCorreo="
				+ cuentaCorreo + ", fechaCreacion=" + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}
}
