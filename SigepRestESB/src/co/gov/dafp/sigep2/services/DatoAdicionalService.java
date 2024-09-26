/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.DatoAdicional;
import co.gov.dafp.sigep2.bean.DatoAdicionalExample;
import co.gov.dafp.sigep2.bean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DatoAdicionalMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla DatoAdicional
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class DatoAdicionalService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5252748138595326683L;
	
	/**
	 * 
	 * @param DatoAdicional
	 * @return
	 */
	public DatoAdicional insertDatoAdicional (DatoAdicional datoAdicional){
		DatoAdicional dat = new DatoAdicional();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoAdicionalMapper  mapper = session.getMapper(DatoAdicionalMapper.class);
			id =  mapper.insert(datoAdicional);
			if(id > 0){
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return dat;
		}catch (Exception ex) {
			dat.setError(true);
			dat.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dat.setMensajeTecnico(ex.getMessage());
			return dat;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param DatoAdicional
	 * @return
	 */
	public DatoAdicional updateDatoAdicional(DatoAdicional datoAdicional){
		DatoAdicional dat = new DatoAdicional();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoAdicionalMapper  mapper = session.getMapper(DatoAdicionalMapper.class);
			id = mapper.updateByPrimaryKey(datoAdicional);
			if(id > 0){
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return dat;
		}catch (Exception ex) {
			dat.setError(true);
			dat.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dat.setMensajeTecnico(ex.getMessage());
			return dat;	
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
	public List<DatoAdicional> getDatoAdicionalByAll(){
		List<DatoAdicional> asoc = new ArrayList<>();
		DatoAdicionalExample dtoObject = new DatoAdicionalExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoAdicionalMapper  mapper = session.getMapper(DatoAdicionalMapper.class);
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
	 * @param Id Search
	 * @return
	 */
	public DatoAdicional getDatoAdicionalById(long id){
		DatoAdicional asoc = new DatoAdicional();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoAdicionalMapper  mapper = session.getMapper(DatoAdicionalMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new DatoAdicional();
			}
		}catch(Exception ex){
			return new DatoAdicional();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public DatoAdicionalExt getatoAdicionalByCodPersona(long id){
		DatoAdicionalExt asoc = new DatoAdicionalExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoAdicionalMapper  mapper = session.getMapper(DatoAdicionalMapper.class);
			asoc =  mapper.selectByCodPersona(id);
			if(asoc != null){
				return asoc;
			}else{
				return new DatoAdicionalExt();
			}
		}catch(Exception ex){
		
			return new DatoAdicionalExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 2:27:52 p.m. 2018
	 * @param datoAdicional
	 * @return
	 */
	public DatoAdicional updateDatoAdicionalSelective(DatoAdicional datoAdicional){
		DatoAdicional dat = new DatoAdicional();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoAdicionalMapper  mapper = session.getMapper(DatoAdicionalMapper.class);
			id = mapper.updateByPrimaryKeySelective(datoAdicional);
			if(id > 0){
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return dat;
		}catch (Exception ex) {
			dat.setError(true);
			dat.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dat.setMensajeTecnico(ex.getMessage());
			return dat;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
