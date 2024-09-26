package co.gov.dafp.sigep2.entity.jpa.comun;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

@Entity(name = "MailAdjunto")
// @Table(name = "MAIL_ADJUNTO")
public class MailAdjunto extends EntidadBase {
	private static final long serialVersionUID = -5692244943573315662L;

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JoinColumn(name = "MAIL_ID", referencedColumnName = "ID", nullable = false, updatable = false)
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Mail.class)
	private Mail mailId;

	@Column(name = "RUTA_ADJUNTO", nullable = false, updatable = false)
	private String rutaAdjunto;

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

	public String getRutaAdjunto() {
		return rutaAdjunto;
	}

	public void setRutaAdjunto(String rutaAdjunto) {
		this.rutaAdjunto = rutaAdjunto;
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
		return "MailAdjunto [id=" + id + ", mailId=" + mailId.getId() + ", rutaAdjunto=" + rutaAdjunto
				+ ", fechaCreacion=" + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}
}
