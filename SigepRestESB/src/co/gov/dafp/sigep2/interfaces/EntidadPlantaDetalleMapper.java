package co.gov.dafp.sigep2.interfaces;

import co.gov.dafp.sigep2.bean.EntidadPlantaDetalle;
import co.gov.dafp.sigep2.bean.EntidadPlantaDetalleExample;
import co.gov.dafp.sigep2.bean.ext.EntidadPlantaDetalleExt;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EntidadPlantaDetalleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    long countByExample(EntidadPlantaDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    int deleteByExample(EntidadPlantaDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    int deleteByPrimaryKey(Long codEntidadPlantaDetalle);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    int insert(EntidadPlantaDetalle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    int insertSelective(EntidadPlantaDetalle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    List<EntidadPlantaDetalle> selectByExample(EntidadPlantaDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    EntidadPlantaDetalleExt selectByPrimaryKey(Long codEntidadPlantaDetalle);

 
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    int updateByExampleSelective(@Param("record") EntidadPlantaDetalle record, @Param("example") EntidadPlantaDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    int updateByExample(@Param("record") EntidadPlantaDetalle record, @Param("example") EntidadPlantaDetalleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    int updateByPrimaryKeySelective(EntidadPlantaDetalle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Entidad_Planta_Detalle
     *
     * @mbg.generated Tue Jul 03 10:55:08 COT 2018
     */
    int updateByPrimaryKey(EntidadPlantaDetalle record);
    
    List<EntidadPlantaDetalleExt> selectEntidadPlantaDetalleFilter(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    List<EntidadPlantaDetalleExt> selectCargoPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    
    List<EntidadPlantaDetalleExt> selectCargosPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    List<EntidadPlantaDetalleExt> selectGetNaturalezaEmpleo(EntidadPlantaDetalleExt EntidadcodEntidad);
    List<EntidadPlantaDetalleExt> selectCodigosCargos(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    
    List<EntidadPlantaDetalleExt> selectEntiddCargoPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    int updateCargosDePlantaAutomatico(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    int updateDenominacionDestino(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    EntidadPlantaDetalleExt selectCantidadCargosDirectivos(EntidadPlantaDetalleExt EntidadPlantaDetalleExt) ;
    List<EntidadPlantaDetalleExt> selectVinculacion(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    List<EntidadPlantaDetalleExt> selectVinculacionDenominacionGeneral(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    List<EntidadPlantaDetalleExt> selectPlantaDenominacionGeneral(EntidadPlantaDetalleExt EntidadPlantaDetalleExt);
    Long obtenerVacanciaPorCargo(EntidadPlantaDetalleExt entidadPlantaDetalleExt);
}