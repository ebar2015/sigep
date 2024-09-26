package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.CmCrearPlanta;
import co.gov.dafp.sigep2.bean.CmCrearPlantaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmCrearPlantaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    long countByExample(CmCrearPlantaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int deleteByExample(CmCrearPlantaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int deleteByPrimaryKey(Long codCmCrearPlanta);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int insert(CmCrearPlanta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int insertSelective(CmCrearPlanta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    List<CmCrearPlanta> selectByExample(CmCrearPlantaExample example);
   
    List<CmCrearPlanta> selectByFiltro(CmCrearPlanta cmCrearPlanta);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    CmCrearPlanta selectByPrimaryKey(Long codCmCrearPlanta);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int updateByExampleSelective(@Param("record") CmCrearPlanta record, @Param("example") CmCrearPlantaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int updateByExample(@Param("record") CmCrearPlanta record, @Param("example") CmCrearPlantaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int updateByPrimaryKeySelective(CmCrearPlanta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CM_CREAR_PLANTA
     *
     * @mbg.generated Wed Jan 09 08:39:01 COT 2019
     */
    int updateByPrimaryKey(CmCrearPlanta record);
}