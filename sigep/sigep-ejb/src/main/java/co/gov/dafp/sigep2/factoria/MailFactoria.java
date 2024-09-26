package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.comun.Mail;

@Stateless
public class MailFactoria extends AbstractFactory<Mail> {
	private static final long serialVersionUID = -7910159680853508988L;

	public MailFactoria() {
		super(Mail.class);
	}

	public List<Mail> getMails() {
		return null;// this.createNamedQuery(Mail.FIND_ALL,
					// Mail.class).setParameter("estado", true).getResultList();
	}

	public List<Mail> getMailsPendientesEnvio() {
		return null;// this.createNamedQuery(Mail.FIND_BY_ENVIADO,
					// Mail.class).setParameter("enviado",
					// false).setParameter("estado", true).getResultList();
	}

}