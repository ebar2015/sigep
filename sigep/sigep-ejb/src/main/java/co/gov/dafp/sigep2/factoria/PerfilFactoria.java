/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class PerfilFactoria extends AbstractFactory<Rol> {
	private static final long serialVersionUID = 4480589378599585292L;

	public PerfilFactoria() {
		super(Rol.class);
	}
}
