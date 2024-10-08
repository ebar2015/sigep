package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.CmHvEducacionFormal;
import co.gov.dafp.sigep2.bean.CmHvEducacionFormalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmHvEducacionFormalMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:12 COT 2018
	 */
	long countByExample(CmHvEducacionFormalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:12 COT 2018
	 */
	int deleteByExample(CmHvEducacionFormalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:12 COT 2018
	 */
	int deleteByPrimaryKey(Long codCmHvEducacionFormal);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:12 COT 2018
	 */
	int insert(CmHvEducacionFormal record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:12 COT 2018
	 */
	int insertSelective(CmHvEducacionFormal record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	List<CmHvEducacionFormal> selectByExample(CmHvEducacionFormalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	CmHvEducacionFormal selectByPrimaryKey(Long codCmHvEducacionFormal);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByExampleSelective(@Param("record") CmHvEducacionFormal record,
			@Param("example") CmHvEducacionFormalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByExample(@Param("record") CmHvEducacionFormal record,
			@Param("example") CmHvEducacionFormalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByPrimaryKeySelective(CmHvEducacionFormal record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CM_HV_EDUCACION_FORMAL
	 * @mbg.generated  Thu Aug 16 07:59:13 COT 2018
	 */
	int updateByPrimaryKey(CmHvEducacionFormal record);
}