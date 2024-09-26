package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.CmCrearEstructura;
import co.gov.dafp.sigep2.bean.CmCrearEstructuraExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmCrearEstructuraMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    long countByExample(CmCrearEstructuraExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int deleteByExample(CmCrearEstructuraExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int deleteByPrimaryKey(Long codCmCrearEstructura);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int insert(CmCrearEstructura record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int insertSelective(CmCrearEstructura record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    List<CmCrearEstructura> selectByExample(CmCrearEstructuraExample example);
   
    List<CmCrearEstructura> selectByFiltro(CmCrearEstructura cmCrearEstructura);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    CmCrearEstructura selectByPrimaryKey(Long codCmCrearEstructura);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int updateByExampleSelective(@Param("record") CmCrearEstructura record, @Param("example") CmCrearEstructuraExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int updateByExample(@Param("record") CmCrearEstructura record, @Param("example") CmCrearEstructuraExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int updateByPrimaryKeySelective(CmCrearEstructura record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_ESTRUCTURA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int updateByPrimaryKey(CmCrearEstructura record);
}