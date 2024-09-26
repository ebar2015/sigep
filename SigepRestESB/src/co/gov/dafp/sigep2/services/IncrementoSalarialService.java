/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.IncrementoSalarial;
import co.gov.dafp.sigep2.bean.IncrementoSalarialExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.IncrementoSalarialMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya
* @version 1.0
* @Class Que se encarga de realizar comunicacion con la bd
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 5/09/2018 4:34:05 p.m.
*/
public class IncrementoSalarialService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2395854266703143931L;
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 5/09/2018 4:39:50 p.m.
	* @param incrementoSalarial
	* @return
	*
	 */
	public IncrementoSalarial insertIncrementoSalarial (IncrementoSalarial incrementoSalarial){
		IncrementoSalarial acre = new IncrementoSalarial();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IncrementoSalarialMapper  mapper = session.getMapper(IncrementoSalarialMapper.class);
			id =  mapper.insert(incrementoSalarial);
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
	* @author: Jose Viscaya 
	* @fecha 5/09/2018 4:39:43 p.m.
	* @param incrementoSalarial
	* @return
	*
	 */
	public IncrementoSalarial updateIncrementoSalarial(IncrementoSalarial incrementoSalarial){
		IncrementoSalarial acre = new IncrementoSalarial();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IncrementoSalarialMapper  mapper = session.getMapper(IncrementoSalarialMapper.class);
			id = mapper.updateByPrimaryKey(incrementoSalarial);
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
	* @author: Jose Viscaya 
	* @fecha 5/09/2018 4:39:35 p.m.
	* @return
	*
	 */
	public List<IncrementoSalarial> getIncrementoSalarialall(){
		List<IncrementoSalarial> asoc = new ArrayList<>();
		IncrementoSalarialExample dtoObject = new IncrementoSalarialExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IncrementoSalarialMapper  mapper = session.getMapper(IncrementoSalarialMapper.class);
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
	* @fecha 5/09/2018 4:39:30 p.m.
	* @param id
	* @return
	*
	 */
	public IncrementoSalarial getIncrementoSalarialById(long id){
		IncrementoSalarial asoc = new IncrementoSalarial();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IncrementoSalarialMapper  mapper = session.getMapper(IncrementoSalarialMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new IncrementoSalarial();
			}
		}catch(Exception ex){
		
			return new IncrementoSalarial();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 5/09/2018 4:39:20 p.m.
	* @param incrementoSalarial
	* @return
	*
	 */
	public List<IncrementoSalarial> getIncrementoSalarialFilter(IncrementoSalarial incrementoSalarial){
		List<IncrementoSalarial> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IncrementoSalarialMapper  mapper = session.getMapper(IncrementoSalarialMapper.class);
			asoc =  mapper.selectByFilter(incrementoSalarial);
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

}
