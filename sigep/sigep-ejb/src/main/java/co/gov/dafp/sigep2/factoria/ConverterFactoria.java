/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.EntidadBase;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class ConverterFactoria extends AbstractFactory<EntidadBase> {
	private static final long serialVersionUID = -8648492613174323479L;

	public ConverterFactoria() {
		super(EntidadBase.class);
	}

}
