package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.bean.ColumnaTablaGestionInformacion;
import co.gov.dafp.sigep2.bean.TablaGestionInformacion;

public interface CifrasEmpleoPublicoMapper {
	/**
	 * Devuelve el listado de registros de cifras de empleo publico de acuerdo a
	 * los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros
	 * 
	 * @return {@link List} de {@link CifrasEmpleoPublico} Registros
	 *         seleccionados
	 */
	List<CifrasEmpleoPublico> selectCifras(@Param("cifrasEmpleoPublico") CifrasEmpleoPublico cifrasEmpleoPublico);

	/**
	 * Devuelve el totalizado de los registros de cifras de empleo publico de
	 * acuerdo a los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros para el totalizado
	 * 
	 * @return {@link CifrasEmpleoPublico} Totalizado de los registros
	 *         seleccionados
	 */
	CifrasEmpleoPublico selectTotalCifras(@Param("cifrasEmpleoPublico") CifrasEmpleoPublico cifrasEmpleoPublico);

	/**
	 * Devuelve el listado de registros de tabla de resultados para cifras de
	 * empleo publico de acuerdo a los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros
	 * 
	 * @return {@link List} de {@link CifrasEmpleoPublico} Registros
	 *         seleccionados
	 */
	List<CifrasEmpleoPublico> selectTablaResultados(
			@Param("cifrasEmpleoPublico") CifrasEmpleoPublico cifrasEmpleoPublico);

	List<TablaGestionInformacion> selectTablas();

	List<ColumnaTablaGestionInformacion> selectColumnasTabla(
			@Param("tablaGestionInformacion") TablaGestionInformacion tablaGestionInformacion);

}
