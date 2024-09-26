/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PermisoRol;
import co.gov.dafp.sigep2.bean.PermisoRolExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PermisoRolMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PermisoRolServicio.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PermisoRolServicio implements Serializable {

	private static final long serialVersionUID = 3768328674748535007L;
	
	/**
	 * @param PermisoRol
	 * @return
	 */
	public int insertPermisoRol (PermisoRol permisoRol){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PermisoRolMapper  mapper = session.getMapper(PermisoRolMapper.class);
			id =  mapper.insert(permisoRol);
			if(id > 0){
				session.commit();
				return id;
			}else{
				return 0;
			}
		}catch (Exception ex) {
			return 0;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param PermisoRol
	 * @return
	 */
	public boolean updatePermisoRol(PermisoRol permisoRol){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PermisoRolMapper  mapper = session.getMapper(PermisoRolMapper.class);
			id = mapper.updateByPrimaryKey(permisoRol);
			session.commit();
			return (id==1)?true:false;
		}catch (Exception ex) {
			return false;
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
	public List<PermisoRol> getPermisoRolByAll(int limitIni, int limitEnd){
		List<PermisoRol> asoc = new ArrayList<>();
		PermisoRolExample dtoObject = new PermisoRolExample();
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
			PermisoRolMapper  mapper = session.getMapper(PermisoRolMapper.class);
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
	public List<PermisoRol> getPermisoRolById(BigDecimal id){
		List<PermisoRol> asoc = new ArrayList<>();
		PermisoRolExample dtoObject = new PermisoRolExample();
		dtoObject.createCriteria().andCodPermisoRolEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PermisoRolMapper  mapper = session.getMapper(PermisoRolMapper.class);
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

}
