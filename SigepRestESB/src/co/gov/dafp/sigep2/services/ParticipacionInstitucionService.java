/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ParticipacionInstitucion;
import co.gov.dafp.sigep2.bean.ParticipacionInstitucionExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ParticipacionInstitucionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ParticipacionInstitucionService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ParticipacionInstitucionService implements Serializable {

	private static final long serialVersionUID = 8794898835635328496L;
	
	/**
	 * 
	 * @param ParticipacionInstitucion
	 * @return
	 */
	public boolean insertParticipacionInstitucion (ParticipacionInstitucion participacionInstitucion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionInstitucionMapper  mapper = session.getMapper(ParticipacionInstitucionMapper.class);
			id =  mapper.insert(participacionInstitucion);
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
	 * @param ParticipacionInstitucion
	 * @return
	 */
	public boolean updateParticipacionInstitucion(ParticipacionInstitucion participacionInstitucion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionInstitucionMapper  mapper = session.getMapper(ParticipacionInstitucionMapper.class);
			id = mapper.updateByPrimaryKey(participacionInstitucion);
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
	public List<ParticipacionInstitucion> getParticipacionInstitucionByAll(int limitIni, int limitEnd){
		List<ParticipacionInstitucion> asoc = new ArrayList<>();
		ParticipacionInstitucionExample dtoObject = new ParticipacionInstitucionExample();
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
			ParticipacionInstitucionMapper  mapper = session.getMapper(ParticipacionInstitucionMapper.class);
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
	 * Jose Viscaya 28 dic. 2018
	 * @param participacionInstitucion
	 * @return
	 */
	public List<ParticipacionInstitucion> getParticipacionInstitucionByFiltro(ParticipacionInstitucion participacionInstitucion){
		List<ParticipacionInstitucion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionInstitucionMapper  mapper = session.getMapper(ParticipacionInstitucionMapper.class);
			asoc =  mapper.selectByFiltro(participacionInstitucion);
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
	public ParticipacionInstitucion getParticipacionInstitucionByPersona(BigDecimal id){
		List<ParticipacionInstitucion> asoc = new ArrayList<>();
		ParticipacionInstitucionExample dtoObject = new ParticipacionInstitucionExample();
		dtoObject.createCriteria().andCodPersonaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionInstitucionMapper  mapper = session.getMapper(ParticipacionInstitucionMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new ParticipacionInstitucion();
			}
		}catch(Exception ex){
		
			return new ParticipacionInstitucion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
