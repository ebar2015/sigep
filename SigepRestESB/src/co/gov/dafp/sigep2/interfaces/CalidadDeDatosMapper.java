package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.CalidadDeDatos;
import co.gov.dafp.sigep2.bean.CalidadDeDatosExample;
import co.gov.dafp.sigep2.bean.CalidadDeDatosNotificacion;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CalidadDeDatosMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    long countByExample(CalidadDeDatosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    int deleteByExample(CalidadDeDatosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    int deleteByPrimaryKey(BigDecimal codCalidadDeDatos);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    int insert(CalidadDeDatos record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    int insertSelective(CalidadDeDatos record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    List<CalidadDeDatos> selectByExample(CalidadDeDatosExample example);
 
    List<CalidadDeDatos> selectFiltro(CalidadDeDatos calidadDeDatos);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    CalidadDeDatos selectByPrimaryKey(BigDecimal codCalidadDeDatos);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    int updateByExampleSelective(@Param("record") CalidadDeDatos record, @Param("example") CalidadDeDatosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    int updateByExample(@Param("record") CalidadDeDatos record, @Param("example") CalidadDeDatosExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    int updateByPrimaryKeySelective(CalidadDeDatos record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    int updateByPrimaryKey(CalidadDeDatos record);
    
    
    int aplicarCalidadDatos(CalidadDeDatosNotificacion calidadNotificacion);
}