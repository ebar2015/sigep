package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Publicacion;
import co.gov.dafp.sigep2.bean.PublicacionExample;
import co.gov.dafp.sigep2.bean.ext.PublicacionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PublicacionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PublicacionService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @fecha 27/07/2018 2:50:37 p. m.
 */
public class PublicacionService implements Serializable {

	private static final long serialVersionUID = -7237543933368916899L;
	
	/**
	 * 
	 * @param Publicacion
	 * @return
	 */
	public boolean insertPublicacion (Publicacion publicacion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PublicacionMapper  mapper = session.getMapper(PublicacionMapper.class);
			id =  mapper.insert(publicacion);
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
	 * @param Publicacion
	 * @return
	 */
	public boolean updatePublicacion(Publicacion publicacion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PublicacionMapper  mapper = session.getMapper(PublicacionMapper.class);
			id = mapper.updateByPrimaryKey(publicacion);
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
	public List<Publicacion> getPublicacionByAll(int limitIni, int limitEnd){
		List<Publicacion> asoc = new ArrayList<>();
		PublicacionExample dtoObject = new PublicacionExample();
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
			PublicacionMapper  mapper = session.getMapper(PublicacionMapper.class);
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
	 * Elaborado por:
	 * Jose Viscaya Dec 27, 2018
	 * @param publicacion
	 * @return
	 */
	public List<PublicacionExt> getPublicacionFiltro(Publicacion publicacion){
		List<PublicacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PublicacionMapper  mapper = session.getMapper(PublicacionMapper.class);
			asoc =  mapper.selectByFiltro(publicacion);
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
	public PublicacionExt getPublicacionByPersona(BigDecimal id){
		List<PublicacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PublicacionMapper  mapper = session.getMapper(PublicacionMapper.class);
			asoc =  mapper.selectPublicacionByPersona(id.longValue());
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new PublicacionExt();
			}
		}catch(Exception ex){
			return new PublicacionExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
