/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.services;

import co.gov.dafp.sigep2.bean.EntidadPoliticaPublica;
import co.gov.dafp.sigep2.bean.EntidadPoliticaPublicaExample;
import co.gov.dafp.sigep2.bean.ext.EntidadPoliticaPublicaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadPoliticaPublicaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Sergio
 */
public class EntidadPoliticaPublicaService implements Serializable {

    private static final long serialVersionUID = 4395723857932857923L;

    /**
     * TRa
     *
     * @param entidadPolitica
     * @return
     */
    public List<EntidadPoliticaPublica> getEntidadPoliticaByFilter(EntidadPoliticaPublica entidadPolitica) {
        List<EntidadPoliticaPublica> asoc = new ArrayList<>();
        EntidadPoliticaPublicaExample dtoObject = new EntidadPoliticaPublicaExample();
        try {
            if (entidadPolitica.getLimitEnd() > 1) {
                dtoObject.setLimitInit(entidadPolitica.getLimitInit());
                dtoObject.setLimitEnd(entidadPolitica.getLimitEnd());
            } else {
                dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
                dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
            }
        } catch (NullPointerException e) {
            dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
            dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
        }
        SqlSession session = null;
        try {
            session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
            EntidadPoliticaPublicaMapper mapper = session.getMapper(EntidadPoliticaPublicaMapper.class);
            if (entidadPolitica.getCodEntidad() != null && entidadPolitica.getCodPoliticaPublica() != null) {
                dtoObject.createCriteria().andCodEntidadEqualTo(entidadPolitica.getCodEntidad())
                        .andCodPoliticaPublicaEqualTo(entidadPolitica.getCodPoliticaPublica());
            } else {
                if (entidadPolitica.getCodEntidad() != null) {
                    dtoObject.createCriteria().andCodEntidadEqualTo(entidadPolitica.getCodEntidad());
                }
                if (entidadPolitica.getCodEntidadPoliticaPublica() != null) {
                    dtoObject.createCriteria().andCodEntidadPoliticaPublicaEqualTo(entidadPolitica.getCodEntidadPoliticaPublica());
                }
                if (entidadPolitica.getCodPoliticaPublica() != null) {
                    dtoObject.createCriteria().andCodPoliticaPublicaEqualTo(entidadPolitica.getCodPoliticaPublica());
                }
            }
            asoc = mapper.selectByExample(dtoObject);
            if (!asoc.isEmpty()) {
                return asoc;
            } else {
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Inserta una pol�tica p�blica en la base de datos
     *
     * @param politica
     * @return
     */
    public EntidadPoliticaPublica insertEntidadPoliticaPublica(EntidadPoliticaPublica politica) {
        SqlSession session = null;
        EntidadPoliticaPublica pol = new EntidadPoliticaPublica();
        int id = 0;
        try {
            session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
            EntidadPoliticaPublicaMapper mapper = session.getMapper(EntidadPoliticaPublicaMapper.class);
            id = mapper.insertSelective(politica);
            if (id > 0) {
                pol.setError(false);
                pol.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
                session.commit();
            } else {
                pol.setError(false);
                pol.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
            }
            return pol;
        } catch (Exception ex) {
            pol.setError(true);
            pol.setMensaje(UtilsConstantes.MSG_EXEPCION);
            pol.setMensajeTecnico(ex.getMessage());
            return pol;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Actualiza un registro de pol�tica p�blica en la base de datos
     *
     * @param politica El objeto <code>EntidadPoliticaPublica</code> a
     * actualizar
     * @return
     */
    public EntidadPoliticaPublica updateEntidadPoliticaPublica(EntidadPoliticaPublica politica) {
        EntidadPoliticaPublica pol = new EntidadPoliticaPublica();
        EntidadPoliticaPublicaExample dtoObject = new EntidadPoliticaPublicaExample();
        dtoObject.createCriteria().andCodEntidadPoliticaPublicaEqualTo(politica.getCodEntidadPoliticaPublica());
        SqlSession session = null;
        int id = 0;
        try {
            session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
            EntidadPoliticaPublicaMapper mapper = session.getMapper(EntidadPoliticaPublicaMapper.class);
            id = mapper.updateByExampleSelective(politica, dtoObject);
            session.commit();
            if (id > 0) {
                pol.setError(false);
                pol.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
                session.commit();
            } else {
                pol.setError(false);
                pol.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
            }
            return pol;
        } catch (Exception ex) {
            pol.setError(true);
            pol.setMensaje(UtilsConstantes.MSG_EXEPCION);
            pol.setMensajeTecnico(ex.getMessage());
            return pol;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
	/**
	 * 
	* Elaborado por Maria Alejandra C
	* @fecha 18/10/2018
	* @param persona
	* @return
	*
	 */
	public List<EntidadPoliticaPublicaExt> getPoliticasPublicasEntidad(EntidadPoliticaPublicaExt entidadpPolitica){
		List<EntidadPoliticaPublicaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPoliticaPublicaMapper  mapper = session.getMapper(EntidadPoliticaPublicaMapper.class);
			asoc =  mapper.selectEntidadPublica(entidadpPolitica);
			if(asoc!= null){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param entidadpPolitica
	 * @return
	 */
	
	public List<EntidadPoliticaPublica> getPoliticasPublicasEntidadFiltro(EntidadPoliticaPublica entidadpPolitica){
		List<EntidadPoliticaPublica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPoliticaPublicaMapper  mapper = session.getMapper(EntidadPoliticaPublicaMapper.class);
			asoc =  mapper.selectFiltro(entidadpPolitica);
			if(asoc!= null){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param codEntidadPoliticaPublica
	 * @return
	 */
	public EntidadPoliticaPublica deleteEntidadPoliticaPublica (Long codEntidadPoliticaPublica) {
		EntidadPoliticaPublica error = new EntidadPoliticaPublica();
		SqlSession session = null;
		int id = 0;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPoliticaPublicaMapper  mapper = session.getMapper(EntidadPoliticaPublicaMapper.class);
			id =  mapper.deleteByPrimaryKey(codEntidadPoliticaPublica);
			if(id == 1){
				session.commit();
				error.setError(false);
				error.setMensaje("Registro Eliminado Satifactoriamente");
				return error;
			}else{
				error.setError(true);
				error.setMensaje("Registro No Eliminado Satifactoriamente");
				return error;
			}
		}catch(Exception ex){
			error.setError(true);
			error.setMensaje("Registro No Eliminado Satifactoriamente");
			error.setMensajeTecnico(ex.getMessage());
			return error;
		}finally{
			if (session != null) {
				session.close();
			}
		}
		
	}
    
}
