package co.gov.dafp.sigep2.entity.seguridad;

import java.util.Date;
import java.util.List;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.util.DateUtils;

public class MailDTO extends EntidadBaseDTO {
	private static final long serialVersionUID = -684106671011505085L;

	private long id;

	private String asunto;

	private String cuerpo;

	private String desde = ConfigurationBundleConstants.mailSystem();

	private Date fechaEnvio;

	private Date fechaEnviado;

	private Boolean enviado = Boolean.FALSE;

	private Boolean estado = Boolean.TRUE;

	private List<MailAdjuntoDTO> adjuntos;

	private List<MailDestinatarioDTO> destinatarios;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
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

	public List<MailDestinatarioDTO> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<MailDestinatarioDTO> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public List<MailAdjuntoDTO> getAdjuntos() {
		return adjuntos;
	}

	public void setAdjuntos(List<MailAdjuntoDTO> adjuntos) {
		this.adjuntos = adjuntos;
	}

	@Override
	public String toString() {
		return "Mail [id=" + id + ", asunto=" + asunto + ", cuerpo=" + cuerpo + ", desde=" + desde + ", fechaEnvio="
				+ DateUtils.formatearACadena(fechaEnvio, DateUtils.FECHA_HORA_FORMATO_VO) + ", fechaEnviado="
				+ DateUtils.formatearACadena(fechaEnviado, DateUtils.FECHA_HORA_FORMATO_VO) + ", enviado=" + enviado
				+ ", estado=" + estado + ", adjuntos=" + adjuntos + ", destinatarios="
				+ (destinatarios != null ? destinatarios.size() : 0) + "]";
	}
}
