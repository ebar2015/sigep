/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.ParametricaDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Parametrica;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class ParametricaFactoria extends AbstractFactory<Parametrica> {
	private static final long serialVersionUID = -3077216276520699516L;

	public ParametricaFactoria() {
		super(Parametrica.class);
	}

	public void crearParametro(String descripcion, Parametrica... detalleParametro) throws SIGEP2SistemaException {
		String msg = "void crearParametro(String descripcion, Parametrica... detalleParametro)";
		Parametrica padreParametro = null;
		if (findByDescripcion(descripcion) == null) {
			padreParametro = new Parametrica();

			padreParametro.setNombreParametro(descripcion);
			padreParametro = this.persist(padreParametro, null);

			logger.log().info(msg, descripcion + " creado");
		}

		if (detalleParametro != null) {
			padreParametro = findByDescripcion(descripcion);
			for (Parametrica parametroItem : detalleParametro) {
				if (findByDescripcion(padreParametro, parametroItem.getNombreParametro()) == null) {
					parametroItem.setCodPadreParametrica(padreParametro);
					persist(parametroItem, null);

					logger.log().info(msg, parametroItem.getNombreParametro() + " creado");
				}
			}
		}
	}

	public Parametrica findByDescripcion(String descripcion) {
		try {
			return this.createNamedQuery(Parametrica.FIND_BY_NOMBRE_PARAMETRO, Parametrica.class)
					.setParameter("nombreParametro", descripcion).setParameter("flgEstado", true).setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Parametrica findByDescripcion(Parametrica maestroId, String descripcion) {
		try {
			return this.createNamedQuery(Parametrica.FIND_BY_REGISTRO_DESCRIPCION_MAESTRO_ID, Parametrica.class)
					.setParameter("registro", descripcion).setParameter("maestroId", maestroId).setMaxResults(1)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ParametricaDTO> listarTablasDeParametrica() {
		String query = SQLNames.getSQL(SQLNames.LISTAR_TABLAS_PARAMETRICA);

		return createNativeQuery(query, Parametrica.PARAMETRICA_MAPPING).getResultList();
	}

}
