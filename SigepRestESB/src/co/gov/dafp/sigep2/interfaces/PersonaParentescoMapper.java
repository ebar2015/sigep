package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.PersonaParentesco;
import co.gov.dafp.sigep2.bean.PersonaParentescoExample;
import co.gov.dafp.sigep2.bean.ext.PersonaParentescoExt;

public interface PersonaParentescoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	long countByExample(PersonaParentescoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	int deleteByExample(PersonaParentescoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	int deleteByPrimaryKey(Integer codPersonaParentesco);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	int insert(PersonaParentesco record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	int insertSelective(PersonaParentesco record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	List<PersonaParentescoExt> selectByExample(PersonaParentescoExample example);

	List<PersonaParentescoExt> selectParentescoFiltro(PersonaParentesco PersonaParentescoExt);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	PersonaParentescoExt selectByPrimaryKey(Integer codPersonaParentesco);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	int updateByExampleSelective(@Param("record") PersonaParentesco record,
			@Param("example") PersonaParentescoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	int updateByExample(@Param("record") PersonaParentesco record, @Param("example") PersonaParentescoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	int updateByPrimaryKeySelective(PersonaParentesco record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PERSONA_PARENTESCO
	 * @mbg.generated  Fri Mar 23 10:25:09 COT 2018
	 */
	int updateByPrimaryKey(PersonaParentesco record);
}