package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.ParticipacionJunta;
import co.gov.dafp.sigep2.bean.ParticipacionJuntaExample;
import co.gov.dafp.sigep2.bean.ext.ParticipacionJuntaExt;

public interface ParticipacionJuntaMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    long countByExample(ParticipacionJuntaExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    int deleteByExample(ParticipacionJuntaExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    int deleteByPrimaryKey(Long codParticipacionJunta);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    int insert(ParticipacionJunta record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    int insertSelective(ParticipacionJunta record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    List<ParticipacionJuntaExt> selectByExample(ParticipacionJuntaExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    ParticipacionJuntaExt selectByPrimaryKey(Long codParticipacionJunta);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    int updateByExampleSelective(@Param("record") ParticipacionJunta record,
	    @Param("example") ParticipacionJuntaExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    int updateByExample(@Param("record") ParticipacionJunta record,
	    @Param("example") ParticipacionJuntaExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    int updateByPrimaryKeySelective(ParticipacionJunta record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table PARTICIPACION_JUNTA
     * 
     * @mbg.generated Fri Mar 23 14:00:39 COT 2018
     */
    int updateByPrimaryKey(ParticipacionJunta record);
}