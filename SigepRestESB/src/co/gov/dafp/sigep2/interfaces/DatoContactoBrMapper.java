package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.DatoContactoBr;
import co.gov.dafp.sigep2.bean.DatoContactoBrExample;
import co.gov.dafp.sigep2.bean.ext.DatoContactoExt;

public interface DatoContactoBrMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	long countByExample(DatoContactoBrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	int deleteByExample(DatoContactoBrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	int deleteByPrimaryKey(Long codDatosContactoBr);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	int insert(DatoContactoExt record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	int insertSelective(DatoContactoBr record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	List<DatoContactoBr> selectByExample(DatoContactoBrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	DatoContactoBr selectByPrimaryKey(Long codDatosContactoBr);
	
	/**
	 * 
	 * @param datoContactoExt
	 * @return
	 */
	DatoContactoExt selectByDatoContacto(DatoContactoExt datoContactoExt);
	
	

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	int updateByExampleSelective(@Param("record") DatoContactoBr record,
			@Param("example") DatoContactoBrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	int updateByExample(@Param("record") DatoContactoBr record, @Param("example") DatoContactoBrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	int updateByPrimaryKeySelective(DatoContactoExt record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DATO_CONTACTO_BR
	 * @mbg.generated  Wed Sep 26 11:19:52 COT 2018
	 */
	int updateByPrimaryKey(DatoContactoBr record);
	
	DatoContactoExt selectByDatoContactoByDeclaracion(DatoContactoExt datoContacto);
}