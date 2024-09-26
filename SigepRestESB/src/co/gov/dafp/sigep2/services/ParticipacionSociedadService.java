package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ParticipacionSociedad;
import co.gov.dafp.sigep2.bean.ParticipacionSociedadExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ParticipacionSociedadMapper;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ParticipacionSociedadService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ParticipacionSociedadService implements Serializable {
	private static final long serialVersionUID = -1239295931019326479L;
	/**
	 * 
	 * @param ParticipacionSociedad
	 * @return
	 */
	public boolean insertParticipacionSociedad (ParticipacionSociedad participacionSociedad){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionSociedadMapper  mapper = session.getMapper(ParticipacionSociedadMapper.class);
			id =  mapper.insert(participacionSociedad);
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
	 * @param ParticipacionSociedad
	 * @return
	 */
	public boolean updateParticipacionSociedad(ParticipacionSociedad participacionSociedad){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionSociedadMapper  mapper = session.getMapper(ParticipacionSociedadMapper.class);
			id = mapper.updateByPrimaryKey(participacionSociedad);
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
	public List<ParticipacionSociedad> getParticipacionSociedadByAll(){
		List<ParticipacionSociedad> asoc = new ArrayList<>();
		ParticipacionSociedadExample dtoObject = new ParticipacionSociedadExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionSociedadMapper  mapper = session.getMapper(ParticipacionSociedadMapper.class);
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
	public ParticipacionSociedad getParticipacionSociedadById(BigDecimal id){
		ParticipacionSociedad asoc = new ParticipacionSociedad();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionSociedadMapper  mapper = session.getMapper(ParticipacionSociedadMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new ParticipacionSociedad();
			}
		}catch(Exception ex){
		
			return new ParticipacionSociedad();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
