package co.gov.dafp.sigep2.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.gov.dafp.sigep2.bean.UsuarioRolEntidad;
import co.gov.dafp.sigep2.bean.UsuarioRolEntidadExample;
import co.gov.dafp.sigep2.bean.ext.EntidadExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioRolEntidadExt;

public interface UsuarioRolEntidadMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    long countByExample(UsuarioRolEntidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    int deleteByExample(UsuarioRolEntidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    int deleteByPrimaryKey(BigDecimal codUsuarioRolEntidad);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    int insert(UsuarioRolEntidad record);
   
    int desactivarRolUsuario(UsuarioRolEntidad usuarioRolEntidad);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    int insertSelective(UsuarioRolEntidad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    List<UsuarioRolEntidad> selectByExample(UsuarioRolEntidadExample example);

    List<UsuarioRolEntidadExt> selectByEntidadRolPersona(BigDecimal codPersona);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    UsuarioRolEntidad selectByPrimaryKey(BigDecimal codUsuarioRolEntidad);
  
    UsuarioRolEntidad selectByUsuarioRol(UsuarioExt usuarioExt);
    
    UsuarioRolEntidad selectByUsuarioRolEntidad(UsuarioRolEntidadExt usuarioRolEntidadExt);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    int updateByExampleSelective(@Param("record") UsuarioRolEntidad record, @Param("example") UsuarioRolEntidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    int updateByExample(@Param("record") UsuarioRolEntidad record, @Param("example") UsuarioRolEntidadExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    int updateByPrimaryKeySelective(UsuarioRolEntidad record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table USUARIO_ROL_ENTIDAD
     *
     * @mbg.generated Thu Jan 25 12:04:19 COT 2018
     */
    int updateByPrimaryKey(UsuarioRolEntidad record);
    
    int updateQuitarRol(UsuarioRolEntidadExt usuarioentidad);
    
    int updateByDesactivar(UsuarioRolEntidadExt usuarioentidad);
    
    int asociarRolATodasEntidades(UsuarioRolEntidad usuarioRolEntidad);
    Long asociarRolAUsuario(UsuarioRolEntidadExt usuarioRolEntidadExt);
    
    int eliminarUsuarioEntidad(UsuarioRolEntidadExt usuario);
}