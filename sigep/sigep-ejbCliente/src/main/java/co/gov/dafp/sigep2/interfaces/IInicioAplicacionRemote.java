/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import javax.ejb.Remote;

/**
 *
 * @author JDavila
 */
@Remote
public interface IInicioAplicacionRemote  {
	public void startup();
}
