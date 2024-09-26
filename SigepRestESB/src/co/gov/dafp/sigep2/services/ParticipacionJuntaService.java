/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ParticipacionJunta;
import co.gov.dafp.sigep2.bean.ParticipacionJuntaExample;
import co.gov.dafp.sigep2.bean.ext.ParticipacionJuntaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ParticipacionJuntaMapper;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ParticipacionJuntaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ParticipacionJuntaService implements Serializable {

	private static final long serialVersionUID = -7329823019578808898L;
	/**
	 * 
	 * @param ParticipacionJunta
	 * @return
	 */
	public boolean insertParticipacionJunta (ParticipacionJunta participacionJunta){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionJuntaMapper  mapper = session.getMapper(ParticipacionJuntaMapper.class);
			id =  mapper.insert(participacionJunta);
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
	 * @param ParticipacionJunta
	 * @return
	 */
	public boolean updateParticipacionJunta(ParticipacionJunta participacionJunta){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionJuntaMapper  mapper = session.getMapper(ParticipacionJuntaMapper.class);
			id = mapper.updateByPrimaryKey(participacionJunta);
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
	public List<ParticipacionJuntaExt> getParticipacionJuntaCodDeclaracion(ParticipacionJunta participacionJunta){
		List<ParticipacionJuntaExt> asoc = new ArrayList<>();
		ParticipacionJuntaExample dtoObject = new ParticipacionJuntaExample();
		if(participacionJunta!=null && participacionJunta.getCodDeclaracion()!=null) {
			dtoObject.createCriteria().andCodDeclaracionEqualTo(participacionJunta.getCodDeclaracion()).
			andFlgActivoEqualTo(participacionJunta.getFlgActivo());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionJuntaMapper  mapper = session.getMapper(ParticipacionJuntaMapper.class);
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
	public ParticipacionJuntaExt getParticipacionJuntaById(long id){
		ParticipacionJuntaExt asoc = new ParticipacionJuntaExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParticipacionJuntaMapper  mapper = session.getMapper(ParticipacionJuntaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new ParticipacionJuntaExt();
			}
		}catch(Exception ex){
		
			return new ParticipacionJuntaExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
