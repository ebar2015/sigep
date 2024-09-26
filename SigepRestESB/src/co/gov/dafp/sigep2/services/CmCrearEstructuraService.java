/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmCrearEstructura;
import co.gov.dafp.sigep2.bean.CmCrearEstructuraExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmCrearEstructuraMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya
 * 9 ene. 2019
 */
public class CmCrearEstructuraService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1391810045473998886L;
	
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param cmCrearEstructura
	 * @return
	 */
	public CmCrearEstructura insertCmCrearEstructura (CmCrearEstructura cmCrearEstructura){
		CmCrearEstructura acre = new CmCrearEstructura();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearEstructuraMapper  mapper = session.getMapper(CmCrearEstructuraMapper.class);
			id =  mapper.insert(cmCrearEstructura);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param cmCrearEstructura
	 * @return
	 */
	public CmCrearEstructura updateCmCrearEstructura(CmCrearEstructura cmCrearEstructura){
		CmCrearEstructura acre = new CmCrearEstructura();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearEstructuraMapper  mapper = session.getMapper(CmCrearEstructuraMapper.class);
			id = mapper.updateByPrimaryKey(cmCrearEstructura);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @return
	 */
	public List<CmCrearEstructura> getCmCrearEstructuraFiltro(CmCrearEstructura cmCrearEstructura){
		List<CmCrearEstructura> asoc = new ArrayList<>();
		SqlSession session = null;
		String msg = "CodProcesoCargaMasiva es obligatorio";
		if(cmCrearEstructura.getCodProcesoCargaMasiva() == null) {
			CmCrearEstructura err = new CmCrearEstructura();
			err.setError(true);
			err.setMensaje(msg);
			asoc.add(err);
			return asoc;
		}
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearEstructuraMapper  mapper = session.getMapper(CmCrearEstructuraMapper.class);
			asoc =  mapper.selectByFiltro(cmCrearEstructura);
			if(!asoc.isEmpty()){
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
	 * @param Id Search
	 * @return
	 */
	public CmCrearEstructura getCmCrearEstructuraById(long id){
		CmCrearEstructura asoc = new CmCrearEstructura();
		CmCrearEstructuraExample dtoObject = new CmCrearEstructuraExample();
		dtoObject.createCriteria().andCodCmCrearEstructuraEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearEstructuraMapper  mapper = session.getMapper(CmCrearEstructuraMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmCrearEstructura();
			}
		}catch(Exception ex){
		
			return new CmCrearEstructura();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
