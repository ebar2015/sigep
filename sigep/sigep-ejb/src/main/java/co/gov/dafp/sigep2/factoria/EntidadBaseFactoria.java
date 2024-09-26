/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tthisplate file, choose Tools | Tthisplates
 * and open the tthisplate in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import javax.ejb.Stateless;

/**
 *
 * @author Jhon De Avila
 *
 * @param <T>
 */
@Stateless
public class EntidadBaseFactoria extends AbstractFactory<EntidadBaseFactoria> {
	private static final long serialVersionUID = 3353953462106517880L;

	public EntidadBaseFactoria() {
		super(EntidadBaseFactoria.class);
	}

}
