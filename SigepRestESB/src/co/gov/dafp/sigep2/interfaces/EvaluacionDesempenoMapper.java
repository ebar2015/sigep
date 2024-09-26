package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.EvaluacionDesempeno;
import co.gov.dafp.sigep2.bean.EvaluacionDesempenoExample;
import co.gov.dafp.sigep2.bean.ext.EvaluacionDesempenoExt;

public interface EvaluacionDesempenoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    long countByExample(EvaluacionDesempenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    int deleteByExample(EvaluacionDesempenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    int deleteByPrimaryKey(Long codEvaluacionDesempeno);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    int insert(EvaluacionDesempeno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    int insertSelective(EvaluacionDesempeno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    List<EvaluacionDesempeno> selectByExample(EvaluacionDesempenoExample example);
   
    
  
    List<EvaluacionDesempenoExt> selectByFiltro(EvaluacionDesempeno EvaluacionDesempeno);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    EvaluacionDesempeno selectByPrimaryKey(Long codEvaluacionDesempeno);
    
    EvaluacionDesempenoExt selectByCodPersona(EvaluacionDesempeno evaluacion);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    int updateByExampleSelective(@Param("record") EvaluacionDesempeno record, @Param("example") EvaluacionDesempenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    int updateByExample(@Param("record") EvaluacionDesempeno record, @Param("example") EvaluacionDesempenoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    int updateByPrimaryKeySelective(EvaluacionDesempeno record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table EVALUACION_DESEMPENO
     *
     * @mbg.generated Tue Jun 12 14:50:13 COT 2018
     */
    int updateByPrimaryKey(EvaluacionDesempeno record);
}