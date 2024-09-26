package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.CmHvEducacionIdiomas;
import co.gov.dafp.sigep2.bean.CmHvEducacionIdiomasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmHvEducacionIdiomasMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	long countByExample(CmHvEducacionIdiomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int deleteByExample(CmHvEducacionIdiomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int deleteByPrimaryKey(Long codCmHvEducacionIdiomas);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int insert(CmHvEducacionIdiomas record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int insertSelective(CmHvEducacionIdiomas record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	List<CmHvEducacionIdiomas> selectByExample(CmHvEducacionIdiomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	CmHvEducacionIdiomas selectByPrimaryKey(Long codCmHvEducacionIdiomas);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByExampleSelective(@Param("record") CmHvEducacionIdiomas record,
			@Param("example") CmHvEducacionIdiomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByExample(@Param("record") CmHvEducacionIdiomas record,
			@Param("example") CmHvEducacionIdiomasExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByPrimaryKeySelective(CmHvEducacionIdiomas record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_IDIOMAS
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByPrimaryKey(CmHvEducacionIdiomas record);
}