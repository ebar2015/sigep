/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import javax.ejb.Remote;
import javax.mail.MessagingException;

import co.gov.dafp.sigep2.entity.seguridad.EvaluacionDesempenoDTO;


/**
 *
 * @author Andrés Jaramillo
 */
@Remote
public interface IEvaluacionDesempeno extends IServiceLocal{
	public EvaluacionDesempenoDTO obtenerEvaluacionActual(long idPersona, long idEntidad, long idUsuario) throws MessagingException;
}
