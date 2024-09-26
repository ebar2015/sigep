package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.PermisoRolAccion;

/**
 * Realiza el mapeo para {@link PermisoRolAccion}
 * 
 * @author JDavila
 */
public interface PermisoRolAccionMapper {

	/**
	 * Devuelve el listado de {@link PermisoRolAccion} teniendo en cuenta si los
	 * valores para {@link PermisoRolAccion#getCodPermisoRolAcciones()},
	 * {@link PermisoRolAccion#isFlgActivo()},
	 * {@link PermisoRolAccion#getCodPermisoRol()} y
	 * {@link PermisoRolAccion#getCodRecursoAccion()} son diferentes a null. Son
	 * excluyentes entre ellos
	 * 
	 * @param permisoRolAccion
	 *            {@link PermisoRolAccion} con los valores seteados para la
	 *            busqueda
	 * 
	 * @return {@link List} de {@link PermisoRolAccion} que coninciden con los
	 *         valores de {@link PermisoRolAccion} seteados segun lo
	 *         especificado
	 */
	List<PermisoRolAccion> selectPermisosRolAccion(@Param("permisoRolAccion") PermisoRolAccion permisoRolAccion);

	List<PermisoRolAccion> selectByPermisoRol(@Param("permisoRolAccion") PermisoRolAccion permisoRolAccion);

	List<PermisoRolAccion> deleteByPermisoRol(@Param("permisoRolAccion") PermisoRolAccion permisoRolAccion);

	/**
	 * Devuelve el {@link PermisoRolAccion} teniendo en cuenta el valor para
	 * {@link PermisoRolAccion#getCodPermisoRolAcciones()}
	 * 
	 * @param permisoRolAccion
	 *            {@link PermisoRolAccion} con
	 *            {@link PermisoRolAccion#getCodPermisoRolAcciones()} seteado
	 *            para la busqueda
	 * 
	 * @return {@link PermisoRolAccion} que conincide
	 *         {@link PermisoRolAccion#getCodPermisoRolAcciones()} especificado
	 */
	PermisoRolAccion selectPermisoRolAccion(@Param("permisoRolAccion") PermisoRolAccion permisoRolAccion);

	/**
	 * Crea un registro para {@link PermisoRolAccion}
	 * 
	 * @param permisoRolAccion
	 *            Registro a crear
	 */
	int asignarPermiso(@Param("permisoRolAccion") PermisoRolAccion permisoRolAccion);

	/**
	 * Setea el valor de {@link PermisoRolAccion#isFlgActivo()} en
	 * <code>0</code> para el registro según
	 * {@link PermisoRolAccion#getCodPermisoRolAcciones()}
	 * 
	 * @param permisoRolAccion
	 *            Registro a modificar
	 */
	int revocarPermiso(@Param("permisoRolAccion") PermisoRolAccion permisoRolAccion);

	/**
	 * Setea el valor de {@link PermisoRolAccion#isFlgActivo()} en
	 * <code>1</code> para el registro según
	 * {@link PermisoRolAccion#getCodPermisoRolAcciones()}
	 * 
	 * @param permisoRolAccion
	 *            Registro a modificar
	 */
	int reasignarPermiso(@Param("permisoRolAccion") PermisoRolAccion permisoRolAccion);
}