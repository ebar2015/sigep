package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.PepPreguntasCuestionarioDetalle;
import co.gov.dafp.sigep2.bean.PepPreguntasCuestionarioDetalleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PepPreguntasCuestionarioDetalleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    long countByExample(PepPreguntasCuestionarioDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    int deleteByExample(PepPreguntasCuestionarioDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    int deleteByPrimaryKey(Long idPreguntaCuestionarioDetalle);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    int insert(PepPreguntasCuestionarioDetalle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    int insertSelective(PepPreguntasCuestionarioDetalle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    List<PepPreguntasCuestionarioDetalle> selectByExample(PepPreguntasCuestionarioDetalleExample example);
   
    
    List<PepPreguntasCuestionarioDetalle> selectByFiltro(PepPreguntasCuestionarioDetalle pepPreguntasCuestionarioDetalle);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    PepPreguntasCuestionarioDetalle selectByPrimaryKey(Long idPreguntaCuestionarioDetalle);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    int updateByExampleSelective(@Param("record") PepPreguntasCuestionarioDetalle record, @Param("example") PepPreguntasCuestionarioDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    int updateByExample(@Param("record") PepPreguntasCuestionarioDetalle record, @Param("example") PepPreguntasCuestionarioDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    int updateByPrimaryKeySelective(PepPreguntasCuestionarioDetalle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEP_PREGUNTAS_CUESTIONARIO_DETALLE
     *
     * @mbg.generated Tue Jul 24 07:06:08 COT 2018
     */
    int updateByPrimaryKey(PepPreguntasCuestionarioDetalle record);
}