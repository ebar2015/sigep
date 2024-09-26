package co.gov.dafp.sigep2.entity.seguridad;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class MailAdjuntoDTO extends EntidadBaseDTO {
	private static final long serialVersionUID = -5692244943573315662L;

	private long id;

	private MailDTO mailId;

	private String rutaAdjunto;

	private Boolean estado = Boolean.TRUE;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public MailDTO getMailId() {
		return mailId;
	}

	public void setMailId(MailDTO mailId) {
		this.mailId = mailId;
	}

	public String getRutaAdjunto() {
		return rutaAdjunto;
	}

	public void setRutaAdjunto(String rutaAdjunto) {
		this.rutaAdjunto = rutaAdjunto;
	}

	@Override
	public String toString() {
		return "MailAdjunto [id=" + id + ", mailId=" + mailId.getId() + ", rutaAdjunto=" + rutaAdjunto + ", estado="
				+ estado + "]";
	}
}
