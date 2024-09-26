/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Entidad;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class EntidadFactoria extends AbstractFactory<Entidad> {
	private static final long serialVersionUID = -7803228311046869240L;

	public EntidadFactoria() {
		super(Entidad.class);
	}

	public Entidad convertirEntidadDTO(EntidadDTO entidadDTO) {
		if (entidadDTO != null) {
			return find(entidadDTO.getId());
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<EntidadDTO> obtenerDependenciasEntidades(Long entidad) {
		String query = SQLNames.getSQL(SQLNames.ENTIDAD_USUARIO_BASICA_SQL).replace(SQLNames.INNER, SQLNames.LEFT)
				+ SQLNames.getSQL(SQLNames.ENTIDAD_USUARIO_FIND_BY_PREDECESORA_SQL);
		return createNativeQuery(query, Entidad.ENTIDAD_BASICO_SIGEP_MAPPING).setParameter("entidadPredecesora", entidad)
				.getResultList();
	}

	public EntidadDTO obtenerEntidadPorNombre(String nombreEntidad) {
		String query = SQLNames.getSQL(SQLNames.ENTIDAD_USUARIO_BASICA_SQL).replace(SQLNames.INNER, SQLNames.LEFT)
				+ SQLNames.getSQL(SQLNames.ENTIDAD_USUARIO_FIND_BY_NOMBRE_SQL);
		return (EntidadDTO) createNativeQuery(query, Entidad.ENTIDAD_BASICO_MAPPING)
				.setParameter("nombreEntidad", nombreEntidad).setMaxResults(1).getSingleResult();
	}

}