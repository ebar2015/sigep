package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.CmHvEvaluacionDesempeno;
import co.gov.dafp.sigep2.bean.CmHvEvaluacionDesempenoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmHvEvaluacionDesempenoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	long countByExample(CmHvEvaluacionDesempenoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int deleteByExample(CmHvEvaluacionDesempenoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int deleteByPrimaryKey(Long codCmHvEvaluacionDesempeno);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int insert(CmHvEvaluacionDesempeno record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int insertSelective(CmHvEvaluacionDesempeno record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	List<CmHvEvaluacionDesempeno> selectByExample(CmHvEvaluacionDesempenoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	CmHvEvaluacionDesempeno selectByPrimaryKey(Long codCmHvEvaluacionDesempeno);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByExampleSelective(@Param("record") CmHvEvaluacionDesempeno record,
			@Param("example") CmHvEvaluacionDesempenoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByExample(@Param("record") CmHvEvaluacionDesempeno record,
			@Param("example") CmHvEvaluacionDesempenoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByPrimaryKeySelective(CmHvEvaluacionDesempeno record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EVALUACION_DESEMPENO
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByPrimaryKey(CmHvEvaluacionDesempeno record);
}