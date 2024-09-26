package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.ResultadoPregunta;
import co.gov.dafp.sigep2.bean.ResultadoPreguntaExample;
import co.gov.dafp.sigep2.bean.ext.ResultadoPreguntaExt;

public interface ResultadoPreguntaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    long countByExample(ResultadoPreguntaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByExample(ResultadoPreguntaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByPrimaryKey(Long codResultadoPregunta);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insert(ResultadoPregunta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insertSelective(ResultadoPregunta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    List<ResultadoPregunta> selectByExample(ResultadoPreguntaExample example);
  
    List<ResultadoPregunta> selectRespuestasPregunta(ResultadoPregunta resultadoPregunta);
   
    List<ResultadoPreguntaExt> selectRespuestasEstadistica(ResultadoPregunta resultadoPregunta);
  
    List<ResultadoPreguntaExt> selectGraficoEdades(ResultadoPregunta resultadoPregunta);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    ResultadoPregunta selectByPrimaryKey(Long codResultadoPregunta);
   
    ResultadoPregunta selectTotalRespuestas(Long codPreguntaOpinion);
   
    List<ResultadoPregunta> selectGrafico(Integer codPreguntaOpinion);
    
    List<ResultadoPreguntaExt> selectRespuestaPreguntaPersona(Integer codPreguntaOpinion);

    
    ResultadoPregunta selectRespuestasPuntagePregunta(ResultadoPregunta resultadoPregunta);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExampleSelective(@Param("record") ResultadoPregunta record, @Param("example") ResultadoPreguntaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExample(@Param("record") ResultadoPregunta record, @Param("example") ResultadoPreguntaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKeySelective(ResultadoPregunta record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKey(ResultadoPregunta record);
    
    List<ResultadoPreguntaExt> selectGraficoNivelJerarquico(ResultadoPregunta resultadoPregunta);
    List<ResultadoPreguntaExt> selectInformacionExportar(ResultadoPregunta resultadoPregunta);
    List<ResultadoPreguntaExt> selectGraficoRetenSocial(ResultadoPregunta resultadoPregunta);
}