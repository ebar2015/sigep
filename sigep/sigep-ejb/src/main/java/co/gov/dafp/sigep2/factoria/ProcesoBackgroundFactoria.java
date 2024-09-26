/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.ProcesoBackgrounDTO;
import co.gov.dafp.sigep2.view.ProcesoBackground;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class ProcesoBackgroundFactoria extends AbstractFactory<ProcesoBackground> {
	private static final long serialVersionUID = 2606365736149021326L;

	public ProcesoBackgroundFactoria() {
		super(ProcesoBackground.class);
	}

	@SuppressWarnings("unchecked")
	public List<ProcesoBackgrounDTO> getProcesosBackground() {
		String sql = SQLNames.getSQL(SQLNames.PROCESO_BACKGROUND_SQL);
		return createNativeQuery(sql, ProcesoBackground.PROCESO_BACKGROUND_MAPPING).getResultList();
	}

	public ProcesoBackgrounDTO findProcesoBackgroundByDescripcion(String descripcion) {
		try {
			String sql = SQLNames.getSQL(SQLNames.PROCESO_BACKGROUND_SQL)
					+ SQLNames.getSQL(SQLNames.PROCESO_BACKGROUND_FIND_BY_DESCRIPCION_SQL);
			return (ProcesoBackgrounDTO) createNativeQuery(sql, ProcesoBackground.PROCESO_BACKGROUND_MAPPING)
					.setParameter("descripcion", descripcion).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
