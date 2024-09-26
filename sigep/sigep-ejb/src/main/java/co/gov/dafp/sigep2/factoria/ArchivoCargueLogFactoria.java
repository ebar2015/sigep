/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.comun.ArchivoCargueLog;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class ArchivoCargueLogFactoria extends AbstractFactory<ArchivoCargueLog> {
	private static final long serialVersionUID = -4144975710477714387L;

	public ArchivoCargueLogFactoria() {
		super(ArchivoCargueLog.class);
	}

	public List<ArchivoCargueLog> obtenerArchivoLog(Long archivoCargueId) {
		return this.createNamedQuery(ArchivoCargueLog.FIND_BY_ARCHIVO_CARGUE_ID, ArchivoCargueLog.class)
				.setParameter("archivoCargueId", archivoCargueId).getResultList();
	}
}
