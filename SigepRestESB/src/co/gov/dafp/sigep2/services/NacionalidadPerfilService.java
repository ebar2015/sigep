/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.NacionalidadPerfil;
import co.gov.dafp.sigep2.bean.NacionalidadPerfilExample;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.NacionalidadPerfilMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla NacionalidadPerfilService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class NacionalidadPerfilService implements Serializable {

	private static final long serialVersionUID = -3138621470789390205L;
	
	/**
	 * 
	 * @param NacionalidadPerfilExt
	 * @return
	 */
	public boolean insertNacionalidadPerfil (NacionalidadPerfil nacionalidadPerfil){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NacionalidadPerfilMapper  mapper = session.getMapper(NacionalidadPerfilMapper.class);
			id =  mapper.insert(nacionalidadPerfil);
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
	 * @param NacionalidadPerfilExt
	 * @return
	 */
	public boolean updateNacionalidadPerfil(NacionalidadPerfil nacionalidadPerfil){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NacionalidadPerfilMapper  mapper = session.getMapper(NacionalidadPerfilMapper.class);
			id = mapper.updateByPrimaryKey(nacionalidadPerfil);
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
	public List<NacionalidadPerfil> getNacionalidadPerfilByAll(int limitIni, int limitEnd){
		List<NacionalidadPerfil> asoc = new ArrayList<>();
		NacionalidadPerfilExample dtoObject = new NacionalidadPerfilExample();
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
			NacionalidadPerfilMapper  mapper = session.getMapper(NacionalidadPerfilMapper.class);
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
	public List<NacionalidadPerfil> getNacionalidadPerfilById(long id){
		List<NacionalidadPerfil> asoc = new ArrayList<>();
		NacionalidadPerfilExample dtoObject = new NacionalidadPerfilExample();
		dtoObject.createCriteria().andCodNacionalidadPerfilEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NacionalidadPerfilMapper  mapper = session.getMapper(NacionalidadPerfilMapper.class);
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
	 * @param persona
	 * @return
	 */
	public List<NacionalidadPerfil> getNacionalidadPerfilPersona(Persona persona){
		List<NacionalidadPerfil> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NacionalidadPerfilMapper  mapper = session.getMapper(NacionalidadPerfilMapper.class);
			asoc =  mapper.selectBypersona(persona);
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
	 * @param NacionalidadPerfil
	 * @return
	 */
	public NacionalidadPerfil getNacionalidadPerfilUnico(NacionalidadPerfil user){
		NacionalidadPerfil asoc = new NacionalidadPerfil();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NacionalidadPerfilMapper  mapper = session.getMapper(NacionalidadPerfilMapper.class);
			asoc =  mapper.selectNacionalidadUnica(user);
			if(asoc.getCodNacionalidadPerfil() != null){
				return asoc;
			}else{
				return new NacionalidadPerfil();
			}
		}catch(Exception ex){
		
			return new NacionalidadPerfil();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
