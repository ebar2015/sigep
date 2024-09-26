package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.PersonaMig;
import co.gov.dafp.sigep2.bean.PersonaMigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonaMigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    long countByExample(PersonaMigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByExample(PersonaMigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByPrimaryKey(String stdIdPerson);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insert(PersonaMig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insertSelective(PersonaMig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    List<PersonaMig> selectByExample(PersonaMigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    PersonaMig selectByPrimaryKey(String stdIdPerson);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExampleSelective(@Param("record") PersonaMig record, @Param("example") PersonaMigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExample(@Param("record") PersonaMig record, @Param("example") PersonaMigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKeySelective(PersonaMig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSONA_MIG
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKey(PersonaMig record);
}