package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.InstitucionEducativa;
import co.gov.dafp.sigep2.bean.InstitucionEducativaExample;
import co.gov.dafp.sigep2.bean.ext.InstitucionEducativaExt;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstitucionEducativaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    long countByExample(InstitucionEducativaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByExample(InstitucionEducativaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByPrimaryKey(Long codInstitucionEducativa);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insert(InstitucionEducativa record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insertSelective(InstitucionEducativa record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    List<InstitucionEducativa> selectByExample(InstitucionEducativaExample example);
 
    List<InstitucionEducativaExt> selectByFiltro(InstitucionEducativa InstitucionEducativa);
   
    
    List<InstitucionEducativa> selectInstitucionesPro();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    InstitucionEducativa selectByPrimaryKey(Long codInstitucionEducativa);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExampleSelective(@Param("record") InstitucionEducativa record, @Param("example") InstitucionEducativaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExample(@Param("record") InstitucionEducativa record, @Param("example") InstitucionEducativaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKeySelective(InstitucionEducativa record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INSTITUCION_EDUCATIVA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKey(InstitucionEducativa record);
    
    List<InstitucionEducativa> selectInstitucionByPais(InstitucionEducativa InstitucionEducativa);
}