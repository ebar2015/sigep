/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EducacionFormal;
import co.gov.dafp.sigep2.bean.EducacionFormalExample;
import co.gov.dafp.sigep2.bean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EducacionFormalMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla EducacionFormal
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class EducacionFormalService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3435577184595833246L;
	
	/**
	 * 
	 * @param EducacionFormal
	 * @return
	 */
	public EducacionFormal insertEducacionFormal (EducacionFormal educacionFormal){
		EducacionFormal edu = new EducacionFormal();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EducacionFormalMapper  mapper = session.getMapper(EducacionFormalMapper.class);
			id =  mapper.insert(educacionFormal);
			if(id > 0){
				edu.setError(false);
				edu.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				edu.setError(false);
				edu.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return edu;
		}catch (Exception ex) {
			edu.setError(true);
			edu.setMensaje(UtilsConstantes.MSG_EXEPCION);
			edu.setMensajeTecnico(ex.getMessage());
			return edu;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param EducacionFormal
	 * @return
	 */
	public EducacionFormal updateEducacionFormal(EducacionFormal educacionFormal){
		EducacionFormal edu = new EducacionFormal();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EducacionFormalMapper  mapper = session.getMapper(EducacionFormalMapper.class);
			id = mapper.updateByPrimaryKey(educacionFormal);
			session.commit();
			if(id > 0){
				edu.setError(false);
				edu.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				edu.setError(false);
				edu.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return edu;
		}catch (Exception ex) {
			edu.setError(true);
			edu.setMensaje(UtilsConstantes.MSG_EXEPCION);
			edu.setMensajeTecnico(ex.getMessage());
			return edu;	
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
	public List<EducacionFormal> getEducacionFormalByAll(int limitIni, int limitEnd){
		List<EducacionFormal> asoc = new ArrayList<>();
		EducacionFormalExample dtoObject = new EducacionFormalExample();
		if(limitEnd > 1 ) {
			dtoObject.setLimitInit(limitIni);
			dtoObject.setLimitEnd(limitEnd);
		}else {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EducacionFormalMapper  mapper = session.getMapper(EducacionFormalMapper.class);
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
	public EducacionFormalExt getEducacionFormalById(EducacionFormal educacionFormal){
		EducacionFormalExt asoc = new EducacionFormalExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EducacionFormalMapper  mapper = session.getMapper(EducacionFormalMapper.class);
			asoc =  mapper.selectById(educacionFormal);
			if(asoc != null){
				return asoc;
			}else{
				return new EducacionFormalExt();
			}
		}catch(Exception ex){
			return new EducacionFormalExt();
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
	public List<EducacionFormalExt> selectByCodPersona001(EducacionFormal educacionFormal){
		List<EducacionFormalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EducacionFormalMapper  mapper = session.getMapper(EducacionFormalMapper.class);
			asoc =  mapper.selectByCodPersona001(educacionFormal);
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
	 * @fecha 11:46:48 a.m. 2018
	 * @param educacionFormal
	 * @return
	 */
	public EducacionFormal updateEducacionFormalSelective(EducacionFormal educacionFormal){
		EducacionFormal edu = new EducacionFormal();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EducacionFormalMapper  mapper = session.getMapper(EducacionFormalMapper.class);
			id = mapper.updateByPrimaryKeySelective(educacionFormal);
			session.commit();
			if(id > 0){
				edu.setError(false);
				edu.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				edu.setError(false);
				edu.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return edu;
		}catch (Exception ex) {
			edu.setError(true);
			edu.setMensaje(UtilsConstantes.MSG_EXEPCION);
			edu.setMensajeTecnico(ex.getMessage());
			return edu;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
