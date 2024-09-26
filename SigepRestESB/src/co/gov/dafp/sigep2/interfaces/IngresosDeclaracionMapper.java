package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.IngresosDeclaracion;
import co.gov.dafp.sigep2.bean.IngresosDeclaracionExample;
import co.gov.dafp.sigep2.bean.ext.IngresosDeclaracionExt;

public interface IngresosDeclaracionMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	long countByExample(IngresosDeclaracionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	int deleteByExample(IngresosDeclaracionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	int deleteByPrimaryKey(Long codIngresoDeclaracion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	int insert(IngresosDeclaracion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	int insertSelective(IngresosDeclaracion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	List<IngresosDeclaracionExt> selectByExample(IngresosDeclaracionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	IngresosDeclaracionExt selectByPrimaryKey(Long codIngresoDeclaracion);

	IngresosDeclaracionExt selectIngresoSuma(IngresosDeclaracion ingresosDeclaracion);
	IngresosDeclaracionExt totalIngresos(IngresosDeclaracion ingresosDeclaracion);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	int updateByExampleSelective(@Param("record") IngresosDeclaracion record,
			@Param("example") IngresosDeclaracionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	int updateByExample(@Param("record") IngresosDeclaracion record,
			@Param("example") IngresosDeclaracionExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	int updateByPrimaryKeySelective(IngresosDeclaracion record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table INGRESOS_DECLARACION
	 * @mbg.generated  Tue Mar 13 09:28:51 COT 2018
	 */
	int updateByPrimaryKey(IngresosDeclaracion record);
}