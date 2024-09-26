package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.Recurso;
import co.gov.dafp.sigep2.bean.RecursoAccion;
import co.gov.dafp.sigep2.bean.ext.RecursoAccionExt;

/**
 * Mapeo con la vista V_RECURSO_ACCION
 */
public interface RecursoAccionMapper {

	/**
	 * Devuelve el listado de recursos asociados a los permisos asignados por
	 * roles
	 * 
	 * @param recursoAccion
	 *            Parametro con el nombre del rol a filtrar por mecanismo like
	 * @param recursos
	 *            {@link List} de {@link String} con las keys equivalentes a
	 *            {@link Recurso#getDescripcion()}
	 * 
	 * @return {@link List} de {@link RecursoAccion}
	 */
	List<RecursoAccion> selectByLikeNombreRol(@Param("recursoAccion") RecursoAccion recursoAccion,
			@Param("inList") List<String> recursos);

	RecursoAccion countByLikeNombreRol(@Param("recursoAccion") RecursoAccion recursoAccion,
			@Param("inList") List<String> recursos);
	
	 List<RecursoAccion> selectByfiltro(RecursoAccion recursoAccion);
	 
	 List<RecursoAccion> getVistaRecursoUsuarioAccionFiltro(RecursoAccionExt recursoAccion);
}