package co.gov.dafp.sigep2.ejb;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entity.seguridad.MailDTO;
import co.gov.dafp.sigep2.entity.seguridad.MailDestinatarioDTO;
import co.gov.dafp.sigep2.factoria.MailFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoRespaldoLocal;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoRemote;
import co.gov.dafp.sigep2.sistema.HMail;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;

import co.gov.dafp.sigep2.util.logger.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class EnvioCorreoEJB implements IEnvioCorreoLocal, IEnvioCorreoRespaldoLocal, IEnvioCorreoRemote {
	private static final long serialVersionUID = 1883621957422929126L;

	transient Logger logger = Logger.getInstance(EnvioCorreoEJB.class);

	@Resource(mappedName = "java:jboss/mail/sigep2")
	transient Session mailSession;

	private List<String> adjuntos;

	@EJB
	private UsuarioFactoria usuarioFactoria;

	@EJB
	private MailFactoria mailFactoria;

	@Override
	public void enviarMail(String asunto, String cuerpoP, Object desde, List<?> aList, List<?> ccList)
			throws MessagingException {
		HMail hMail = new HMail(usuarioFactoria, mailFactoria, mailSession, adjuntos, asunto, desde, cuerpoP, aList,ccList);
		hMail.start();
	}
	
	@Override
	public void enviarMail(String asunto, String cuerpoP, Object desde, List<?> aList, List<?> ccList, String strNotaPrivacidad)
			throws MessagingException {
		HMail hMail = new HMail(usuarioFactoria, mailFactoria, mailSession, adjuntos, asunto, desde, cuerpoP, aList,ccList, strNotaPrivacidad );
		hMail.start();
	}

	@Override
	public void enviarMail(String asunto, String cuerpo, Object desde, List<?> a, List<String> adjuntos, List<?> cc)
			throws MessagingException {
		this.adjuntos = adjuntos;
		this.enviarMail(asunto, cuerpo, desde, a, cc);
	}
	
	@Override
	public void enviarMail(String asunto, String cuerpoP, Object desde, List<?> aList, List<?> ccList,Long codUsuario, boolean soloSMS,String strNotaPrivacidad)
			throws MessagingException {
		HMail hMail = new HMail(usuarioFactoria, mailFactoria, mailSession, adjuntos, asunto, desde, cuerpoP, aList,ccList,codUsuario,soloSMS,strNotaPrivacidad);
		hMail.start();
	}	

	@Override
	public boolean enviarMail(MailDTO mail) throws MessagingException {
		this.adjuntos = null;
		HMail hMail = null;
		List<MailDestinatarioDTO> destinatarios = mail.getDestinatarios();
		if (destinatarios == null || destinatarios.isEmpty()) {
			hMail = new HMail(usuarioFactoria, mailFactoria, mailSession, adjuntos, mail.getAsunto(), mail.getDesde(),
					mail.getCuerpo(), mail.getAdjuntos(),
					Arrays.asList(ConfigurationBundleConstants.adminCuentaCorreo()), mail.getFechaEnvio());
		} else {
			hMail = new HMail(usuarioFactoria, mailFactoria, mailSession, adjuntos, mail.getAsunto(), mail.getDesde(),
					mail.getCuerpo(), mail.getAdjuntos(), mail.getDestinatarios(), mail.getFechaEnvio());
		}
		hMail.start();
		while (hMail.isAlive()) {
			continue;
		}

		return hMail.isInterrupted();
	}

	public MailFactoria getMailDao() {
		return mailFactoria;
	}

	public void setMailDao(MailFactoria mailDao) {
		this.mailFactoria = mailDao;
	}

}
