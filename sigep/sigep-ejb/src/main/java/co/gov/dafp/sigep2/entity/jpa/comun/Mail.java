package co.gov.dafp.sigep2.entity.jpa.comun;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.util.DateUtils;

@Entity(name = "Mail")
// @Table(name = "MAIL")

public class Mail extends EntidadBase {
	private static final long serialVersionUID = -684106671011505085L;

	@Id
	// @Column(name = "ID", unique = true, nullable = false, precision = 38,
	// insertable = false, updatable = false)
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// @Column(nullable = false, updatable = false)
	@Transient
	private String asunto;

	// @Column(nullable = false, updatable = false, precision = 1600)
	@Transient
	private String cuerpo;

	// @Column(name = "CUENTA_CORREO_DESDE", nullable = false, updatable =
	// false)
	// @Convert(converter = LowerCaseConverter.class)
	@Transient
	private String desde = ConfigurationBundleConstants.mailSystem();

	// @Temporal(TemporalType.TIMESTAMP)
	// @Column(name = "FECHA_ENVIO", nullable = false, updatable = false)
	@Transient
	private Date fechaEnvio;

	// @Temporal(TemporalType.TIMESTAMP)
	// @Column(name = "FECHA_ENVIADO", nullable = false, insertable = false)
	@Transient
	private Date fechaEnviado;

	// @Convert(converter = BooleanToNumberConverter.class)
	@Transient
	private Boolean enviado = Boolean.FALSE;

	// @OneToMany(targetEntity = MailAdjunto.class, mappedBy = "mailId", cascade
	// = CascadeType.ALL, fetch = FetchType.EAGER)
	@Transient
	private List<MailAdjunto> adjuntos;

	// @OneToMany(targetEntity = MailDestinatario.class, mappedBy = "mailId",
	// cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Transient
	private List<MailDestinatario> destinatarios;

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

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Date getFechaEnviado() {
		return fechaEnviado;
	}

	public void setFechaEnviado(Date fechaEnviado) {
		this.fechaEnviado = fechaEnviado;
	}

	public Boolean getEnviado() {
		return enviado;
	}

	public void setEnviado(Boolean enviado) {
		this.enviado = enviado;
	}

	public List<MailAdjunto> getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(List<MailAdjunto> adjuntos) {
		this.adjuntos = adjuntos;
	}

	public List<MailDestinatario> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<MailDestinatario> destinatarios) {
		this.destinatarios = destinatarios;
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
		return "Mail [id=" + id + ", asunto=" + asunto + ", cuerpo=" + cuerpo + ", desde=" + desde + ", fechaEnvio="
				+ DateUtils.formatearACadenaLarga(fechaEnvio) + ", fechaEnviado="
				+ DateUtils.formatearACadenaLarga(fechaEnviado) + ", enviado=" + enviado + ", adjuntos=" + adjuntos
				+ ", destinatarios=" + (destinatarios != null ? destinatarios.size() : 0) + ", fechaCreacion=" + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}
}
