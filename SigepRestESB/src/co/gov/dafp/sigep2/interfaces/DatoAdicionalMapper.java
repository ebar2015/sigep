package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.DatoAdicional;
import co.gov.dafp.sigep2.bean.DatoAdicionalExample;
import co.gov.dafp.sigep2.bean.ext.DatoAdicionalExt;

public interface DatoAdicionalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    long countByExample(DatoAdicionalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByExample(DatoAdicionalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByPrimaryKey(Long codDatoAdicional);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insert(DatoAdicional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insertSelective(DatoAdicional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    List<DatoAdicional> selectByExample(DatoAdicionalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    DatoAdicional selectByPrimaryKey(Long codDatoAdicional);
   
    
    DatoAdicionalExt selectByCodPersona(Long codPersona);
   
    DatoAdicionalExt selectDatoAdicionalOP(Long codPersona);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExampleSelective(@Param("record") DatoAdicional record, @Param("example") DatoAdicionalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExample(@Param("record") DatoAdicional record, @Param("example") DatoAdicionalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKeySelective(DatoAdicional record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DATO_ADICIONAL
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKey(DatoAdicional record);
}