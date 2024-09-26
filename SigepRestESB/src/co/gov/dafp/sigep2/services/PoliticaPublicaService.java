/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.services;

import co.gov.dafp.sigep2.bean.PoliticaPublica;
import co.gov.dafp.sigep2.bean.PoliticaPublicaExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PoliticaPublicaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author Sergio
 */
public class PoliticaPublicaService implements Serializable {

    private static final long serialVersionUID = -7039249456100529621L;

    /**
     * TRa
     *
     * @param politica
     * @return
     */
    public List<PoliticaPublica> getPoliticaByAll(PoliticaPublica politica) {
        List<PoliticaPublica> asoc = new ArrayList<>();
        PoliticaPublicaExample dtoObject = new PoliticaPublicaExample();
        try {
            if (politica.getLimitEnd() > 1) {
                dtoObject.setLimitInit(politica.getLimitInit());
                dtoObject.setLimitEnd(politica.getLimitEnd());
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
            PoliticaPublicaMapper mapper = session.getMapper(PoliticaPublicaMapper.class);
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
     * Inserta una política pública en la base de datos
     *
     * @param politica
     * @return
     */
    public PoliticaPublica insertPoliticaPublica(PoliticaPublica politica) {
        SqlSession session = null;
        PoliticaPublica pol = new PoliticaPublica();
        int id = 0;
        try {
            session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
            PoliticaPublicaMapper mapper = session.getMapper(PoliticaPublicaMapper.class);
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
     * Actualiza un registro de política pública en la base de datos
     *
     * @param politica El objeto <code>PoliticaPublica</code> a actualizar
     * @return
     */
    public PoliticaPublica updatePoliticaPublica(PoliticaPublica politica) {
        PoliticaPublica pol = new PoliticaPublica();
        PoliticaPublicaExample dtoObject = new PoliticaPublicaExample();
        dtoObject.createCriteria().andCodPoliticaPublicaEqualTo(politica.getCodPoliticaPublica());
        SqlSession session = null;
        int id = 0;
        try {
            session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
            PoliticaPublicaMapper mapper = session.getMapper(PoliticaPublicaMapper.class);
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

}
