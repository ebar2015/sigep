package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.SequenciasSigep;
import co.gov.dafp.sigep2.bean.SequenciasSigepExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SequenciasSigepMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SEQUENCIAS_SIGEP
     *
     * @mbg.generated Thu May 24 16:44:25 COT 2018
     */
    long countByExample(SequenciasSigepExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SEQUENCIAS_SIGEP
     *
     * @mbg.generated Thu May 24 16:44:25 COT 2018
     */
    int deleteByExample(SequenciasSigepExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SEQUENCIAS_SIGEP
     *
     * @mbg.generated Thu May 24 16:44:25 COT 2018
     */
    int insert(SequenciasSigep record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SEQUENCIAS_SIGEP
     *
     * @mbg.generated Thu May 24 16:44:25 COT 2018
     */
    int insertSelective(SequenciasSigep record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SEQUENCIAS_SIGEP
     *
     * @mbg.generated Thu May 24 16:44:25 COT 2018
     */
    List<SequenciasSigep> selectByExample(SequenciasSigepExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SEQUENCIAS_SIGEP
     *
     * @mbg.generated Thu May 24 16:44:25 COT 2018
     */
    int updateByExampleSelective(@Param("record") SequenciasSigep record, @Param("example") SequenciasSigepExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SEQUENCIAS_SIGEP
     *
     * @mbg.generated Thu May 24 16:44:25 COT 2018
     */
    int updateByExample(@Param("record") SequenciasSigep record, @Param("example") SequenciasSigepExample example);
}