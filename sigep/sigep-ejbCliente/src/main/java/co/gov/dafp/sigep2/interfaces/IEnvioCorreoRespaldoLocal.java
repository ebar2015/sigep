/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import javax.ejb.Local;
import javax.mail.MessagingException;

import co.gov.dafp.sigep2.entity.seguridad.MailDTO;

/**
 *
 * @author JDavila
 */
@Local
public interface IEnvioCorreoRespaldoLocal extends IServiceLocal {
	public boolean enviarMail(MailDTO mail) throws MessagingException;
}
