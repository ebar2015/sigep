package co.gov.dafp.sigep2.entity.seguridad;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class MailDestinatarioDTO extends EntidadBaseDTO {
	private static final long serialVersionUID = -6798167350402197841L;

	private long id;

	private MailDTO mailId;

	private String tipoBandeja;

	private String cuentaCorreo;

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

	@Override
	public String toString() {
		return "MailDestinatario [id=" + id + ", mailId=" + mailId.getId() + ", tipoBandeja=" + tipoBandeja
				+ ", cuentaCorreo=" + cuentaCorreo + ", estado=" + estado + "]";
	}
}
