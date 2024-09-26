/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EntidadSistemaRegimen;
import co.gov.dafp.sigep2.bean.ext.EntidadSistemaRegimenExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadSistemaRegimenMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 *
 * @author Sergio
 */
public class EntidadSistemaRegimenService implements Serializable {

    private static final long serialVersionUID = -1805309103206251815L;

    /**
     * M�todo para insertar un registro <code>EntidadSistemaRegimen</code>
     *
     * @param entidadSistemaRegimen En registro a insertar
     * @return El registro con respuesta
     */
    public EntidadSistemaRegimen insertEntidadSelective(EntidadSistemaRegimen entidadSistemaRegimen) {
        SqlSession session = null;
        EntidadSistemaRegimen esr = new EntidadSistemaRegimen();
        int id = 0;
        try {
            session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
            EntidadSistemaRegimenMapper mapper = session.getMapper(EntidadSistemaRegimenMapper.class);
            id = mapper.insertSelective(entidadSistemaRegimen);
            if (id > 0) {
                esr.setError(false);
                esr.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
                session.commit();
            } else {
                esr.setError(false);
                esr.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
            }
            return esr;
        } catch (Exception ex) {
            esr.setError(true);
            esr.setMensaje(UtilsConstantes.MSG_EXEPCION);
            esr.setMensajeTecnico(ex.getMessage());
            return esr;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * M�todo para actualizar un registro <code>EntidadSistemaRegimen</code>
     *
     * @param entidadSistemaRegimen En registro a insertar
     * @return El registro con respuesta
     */
    public EntidadSistemaRegimen updateEntidadSelective(EntidadSistemaRegimen entidadSistemaRegimen) {
        SqlSession session = null;
        EntidadSistemaRegimen esr = new EntidadSistemaRegimen();
        int id = 0;
        try {
            session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
            EntidadSistemaRegimenMapper mapper = session.getMapper(EntidadSistemaRegimenMapper.class);
            id = mapper.updateByPrimaryKey(entidadSistemaRegimen);
            if (id > 0) {
                esr.setError(false);
                esr.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
                session.commit();
            } else {
                esr.setError(false);
                esr.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
            }
            return esr;
        } catch (Exception ex) {
            esr.setError(true);
            esr.setMensaje(UtilsConstantes.MSG_EXEPCION);
            esr.setMensajeTecnico(ex.getMessage());
            return esr;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * M�todo que trae el listado de <code>EntidadSistemaRegimen</code> de una
     * entidad a partir del id
     *
     * @param idEntidad El id de entidad de la cual se desean traer los
     * registros
     * @return
     */
    public List<EntidadSistemaRegimenExt> encontrarPorIdEntidad(BigDecimal idEntidad) {
        SqlSession session = null;
        EntidadSistemaRegimen esr = new EntidadSistemaRegimen();
        try {
            session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
            EntidadSistemaRegimenMapper mapper = session.getMapper(EntidadSistemaRegimenMapper.class);
            List<EntidadSistemaRegimenExt> esrList = mapper.selectByCodEntidad(idEntidad);
            return esrList;
        } catch (Exception ex) {
            esr.setError(true);
            esr.setMensaje(UtilsConstantes.MSG_EXEPCION);
            esr.setMensajeTecnico(ex.getMessage());
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    /**
     * 
     * @Elaborado_Por: Jose Viscaya
     * @param idEntidad
     * @return
     * @Fecha :Mar 13, 2019
     * EntidadSistemaRegimenService.java
     */
    public List<EntidadSistemaRegimenExt> getEntidadSistemaRegimenFiltro(EntidadSistemaRegimen entidadSistemaRegimen) {
    	SqlSession session = null;
    	EntidadSistemaRegimen esr = new EntidadSistemaRegimen();
    	try {
    		session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
    		EntidadSistemaRegimenMapper mapper = session.getMapper(EntidadSistemaRegimenMapper.class);
    		List<EntidadSistemaRegimenExt> esrList = mapper.selectByFiltro(entidadSistemaRegimen);
    		return esrList;
    	} catch (Exception ex) {
    		esr.setError(true);
    		esr.setMensaje(UtilsConstantes.MSG_EXEPCION);
    		esr.setMensajeTecnico(ex.getMessage());
    		return null;
    	} finally {
    		if (session != null) {
    			session.close();
    		}
    	}
    }

}
