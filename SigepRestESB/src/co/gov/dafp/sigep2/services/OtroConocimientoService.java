/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.OtroConocimiento;
import co.gov.dafp.sigep2.bean.OtroConocimientoExample;
import co.gov.dafp.sigep2.bean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.OtroConocimientoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla OtroConocimientoService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class OtroConocimientoService implements Serializable {

	private static final long serialVersionUID = 3637656260602418020L;
	
	/**
	 * 
	 * @param OtroConocimiento
	 * @return
	 */
	public boolean insertOtroConocimiento (OtroConocimiento otroConocimiento){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtroConocimientoMapper  mapper = session.getMapper(OtroConocimientoMapper.class);
			id =  mapper.insert(otroConocimiento);
			if(id > 0){
				session.commit();
				return true;
			}else{
				return false;
			}
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
	 * @param OtroConocimiento
	 * @return
	 */
	public boolean updateOtroConocimiento(OtroConocimiento otroConocimiento){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtroConocimientoMapper  mapper = session.getMapper(OtroConocimientoMapper.class);
			id = mapper.updateByPrimaryKey(otroConocimiento);
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
	public List<OtroConocimiento> getOtroConocimientoByAll(int limitIni, int limitEnd){
		List<OtroConocimiento> asoc = new ArrayList<>();
		OtroConocimientoExample dtoObject = new OtroConocimientoExample();
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
			OtroConocimientoMapper  mapper = session.getMapper(OtroConocimientoMapper.class);
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
	public List<OtroConocimientoExt> getOtroConocimientoPorPersona(OtroConocimiento otroConocimiento){
		List<OtroConocimientoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtroConocimientoMapper  mapper = session.getMapper(OtroConocimientoMapper.class);
			asoc =  mapper.selectByPersona(otroConocimiento);
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
	public OtroConocimientoExt getOtroConocimientoPorId (BigDecimal codOtroConocimiento){
		OtroConocimientoExt asoc = new OtroConocimientoExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtroConocimientoMapper  mapper = session.getMapper(OtroConocimientoMapper.class);
			asoc =  mapper.selectByPrimaryKey(codOtroConocimiento);
			if(asoc!=null){
				return asoc;
			}else{
				return new OtroConocimientoExt();
			}
		}catch(Exception ex){
			return new OtroConocimientoExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}


}
