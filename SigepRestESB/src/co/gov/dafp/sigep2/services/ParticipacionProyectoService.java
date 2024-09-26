package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ParticipacionProyecto;
import co.gov.dafp.sigep2.bean.ParticipacionProyectoExample;
import co.gov.dafp.sigep2.bean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ParticipacionProyectoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ParticipacionProyectoService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @fecha 27/07/2018 2:44:11 p. m.
 */
public class ParticipacionProyectoService implements Serializable {

	private static final long serialVersionUID = -2735727328272633539L;
	
	/**
	 * @param ParticipacionProyecto
	 * @return
	 */
	public boolean insertParticipacionProyecto (ParticipacionProyecto participacionProyecto){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionProyectoMapper  mapper = session.getMapper(ParticipacionProyectoMapper.class);
			id =  mapper.insert(participacionProyecto);
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
	 * @param ParticipacionProyecto
	 * @return
	 */
	public boolean updateParticipacionProyecto(ParticipacionProyecto participacionProyecto){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionProyectoMapper  mapper = session.getMapper(ParticipacionProyectoMapper.class);
			id = mapper.updateByPrimaryKey(participacionProyecto);
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
	public List<ParticipacionProyecto> getParticipacionProyectoByAll(int limitIni, int limitEnd){
		List<ParticipacionProyecto> asoc = new ArrayList<>();
		ParticipacionProyectoExample dtoObject = new ParticipacionProyectoExample();
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
			ParticipacionProyectoMapper  mapper = session.getMapper(ParticipacionProyectoMapper.class);
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
	public ParticipacionProyectoExt getParticipacionProyectoByPersona(BigDecimal id){
		List<ParticipacionProyectoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionProyectoMapper  mapper = session.getMapper(ParticipacionProyectoMapper.class);
			asoc =  mapper.selectParticipacionProyectoByPersona(id.longValue());
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new ParticipacionProyectoExt();
			}
		}catch(Exception ex){
			return new ParticipacionProyectoExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya 28 dic. 2018
	 * @param participacionProyecto
	 * @return
	 */
	public List<ParticipacionProyectoExt> getParticipacionProyectoByFiltro(ParticipacionProyecto participacionProyecto){
		List<ParticipacionProyectoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionProyectoMapper  mapper = session.getMapper(ParticipacionProyectoMapper.class);
			asoc =  mapper.selectByFiltro(participacionProyecto);
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
