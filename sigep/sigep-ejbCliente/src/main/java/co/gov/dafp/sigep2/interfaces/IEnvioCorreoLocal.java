/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 *
 * @author JDavila
 */
@Local
public interface IEnvioCorreoLocal extends IServiceLocal {
    public void enviarMail(String asunto, String cuerpo,  Object desde, List<?> aList, List<?> ccList) throws MessagingException;
    
    public void enviarMail(String asunto, String cuerpo,  Object desde, List<?> aList, List<?> ccList, String strNotaPrivacidad) throws MessagingException;
    
    public void enviarMail(String asunto, String cuerpo,  Object desde, List<?> aList, List<String> adjuntos, List<?> ccList) throws MessagingException;
    
    public void enviarMail(String asunto, String cuerpo,  Object desde, List<?> aList, List<?> ccList,Long codUsuario, boolean soloSMS,String strNotaPrivacidad) throws MessagingException;
}