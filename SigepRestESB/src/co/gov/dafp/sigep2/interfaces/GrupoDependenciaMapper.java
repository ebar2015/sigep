package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.GrupoDependencia;
import co.gov.dafp.sigep2.bean.GrupoDependenciaExample;
import co.gov.dafp.sigep2.bean.ext.GrupoDependenciaExt;

public interface GrupoDependenciaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	long countByExample(GrupoDependenciaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	int deleteByExample(GrupoDependenciaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	int deleteByPrimaryKey(Long codGrupoDependencia);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	int insert(GrupoDependencia record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	int insertSelective(GrupoDependencia record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	List<GrupoDependencia> selectByExample(GrupoDependenciaExample example);
	
	
	List<GrupoDependencia> selectByFiltro(GrupoDependencia example);

	
	List<GrupoDependencia> selectByFiltroEntidad(GrupoDependenciaExt example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	GrupoDependencia selectByPrimaryKey(Long codGrupoDependencia);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	int updateByExampleSelective(@Param("record") GrupoDependencia record,
			@Param("example") GrupoDependenciaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	int updateByExample(@Param("record") GrupoDependencia record, @Param("example") GrupoDependenciaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	int updateByPrimaryKeySelective(GrupoDependencia record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table GRUPO_DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:24:46 COT 2018
	 */
	int updateByPrimaryKey(GrupoDependencia record);
}