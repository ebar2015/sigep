/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmSituacionesAdministrativas;
import co.gov.dafp.sigep2.bean.CmSituacionesAdministrativasExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmSituacionesAdministrativasMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class CmSituacionesAdministrativasService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha 9 ago. 2018 7:26:17
 */
public class CmSituacionesAdministrativasService implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 404342096578257448L;
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 9 ago. 2018 7:29:08
	 * @param cmSituacionesAdministrativas
	 * @return
	 */
	public CmSituacionesAdministrativas insertCmSituacionesAdministrativas (CmSituacionesAdministrativas cmSituacionesAdministrativas){
		CmSituacionesAdministrativas cue = new CmSituacionesAdministrativas();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmSituacionesAdministrativasMapper  mapper = session.getMapper(CmSituacionesAdministrativasMapper.class);
			id =  mapper.insertSelective(cmSituacionesAdministrativas);
			if(id > 0){
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
				return cue;
			}else{
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
				return cue;
			}
		}catch (Exception ex) {
			cue.setError(true);
			cue.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cue.setMensajeTecnico(ex.getMessage());
			return cue;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 9 ago. 2018 7:29:28
	 * @param cmSituacionesAdministrativas
	 * @return
	 */
	public CmSituacionesAdministrativas updateCmSituacionesAdministrativas(CmSituacionesAdministrativas cmSituacionesAdministrativas){
		CmSituacionesAdministrativas cue = new CmSituacionesAdministrativas();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmSituacionesAdministrativasMapper  mapper = session.getMapper(CmSituacionesAdministrativasMapper.class);
			id = mapper.updateByPrimaryKey(cmSituacionesAdministrativas);
			if(id > 0){
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return cue;
		}catch (Exception ex) {
			cue.setError(true);
			cue.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cue.setMensajeTecnico(ex.getMessage());
			return cue;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 9 ago. 2018 7:29:45
	 * @param cmSituacionesAdministrativas
	 * @return
	 */
	public List<CmSituacionesAdministrativas> getCmSituacionesAdministrativasByProceso(CmSituacionesAdministrativas cmSituacionesAdministrativas){
		List<CmSituacionesAdministrativas> asoc = new ArrayList<>();
		CmSituacionesAdministrativasExample dtoObject = new CmSituacionesAdministrativasExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(cmSituacionesAdministrativas.getCodProcesoCargaMasiva());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmSituacionesAdministrativasMapper  mapper = session.getMapper(CmSituacionesAdministrativasMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
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
	 * @author: Jose Viscaya 
	 * @fecha 9 ago. 2018 7:29:55
	 * @param id
	 * @return
	 */
	public CmSituacionesAdministrativas getCmSituacionesAdministrativasById(long id){
		CmSituacionesAdministrativas asoc = new CmSituacionesAdministrativas();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmSituacionesAdministrativasMapper  mapper = session.getMapper(CmSituacionesAdministrativasMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmSituacionesAdministrativas();
			}
		}catch(Exception ex){
		
			return new CmSituacionesAdministrativas();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
