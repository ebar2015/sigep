package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.Auditoria;
import co.gov.dafp.sigep2.bean.AuditoriaExample;
import co.gov.dafp.sigep2.bean.ext.AuditoriaExt;

public interface AuditoriaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    long countByExample(AuditoriaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByExample(AuditoriaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int deleteByPrimaryKey(Long codAuditoria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insert(Auditoria record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int insertSelective(Auditoria record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    List<Auditoria> selectByExample(AuditoriaExample example);
    
    
    List<AuditoriaExt> selectParametro(AuditoriaExt auditoriaExt);
  
    List<AuditoriaExt> selectFechas(AuditoriaExt auditoriaExt);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    Auditoria selectByPrimaryKey(Long codAuditoria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExampleSelective(@Param("record") Auditoria record, @Param("example") AuditoriaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByExample(@Param("record") Auditoria record, @Param("example") AuditoriaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKeySelective(Auditoria record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table AUDITORIA
     *
     * @mbg.generated Mon Jan 29 14:57:36 COT 2018
     */
    int updateByPrimaryKey(Auditoria record);
    List<AuditoriaExt> selectAuditoriaByFilter(AuditoriaExt auditoriaExt);
    
    int insertAuditoriaConsulta(Auditoria auditoria);
}