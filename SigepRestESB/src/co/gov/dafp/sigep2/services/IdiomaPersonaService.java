/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.IdiomaPersona;
import co.gov.dafp.sigep2.bean.IdiomaPersonaExample;
import co.gov.dafp.sigep2.bean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.IdiomaPersonaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla IdiomaPersonaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @fecha 27/07/2018 2:28:43 p. m.
 */
public class IdiomaPersonaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5747991743092812535L;
	
	/**
	 * 
	 * @param IdiomaPersona
	 * @return
	 */
	public IdiomaPersona insertIdiomaPersona (IdiomaPersona idiomaPersona){
		IdiomaPersona idio = new IdiomaPersona();
		SqlSession session = null;
		int id = 0;
		idiomaPersona.setAudFechaActualizacion(new Date());
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaPersonaMapper  mapper = session.getMapper(IdiomaPersonaMapper.class);
			id =  mapper.insert(idiomaPersona);
			if(id > 0){
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return idio;
		}catch (Exception ex) {
			idio.setError(true);
			idio.setMensaje(UtilsConstantes.MSG_EXEPCION);
			idio.setMensajeTecnico(ex.getMessage());
			return idio;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param IdiomaPersona
	 * @return
	 */
	public IdiomaPersona updateIdiomaPersona(IdiomaPersona idiomaPersona){
		IdiomaPersona idio = new IdiomaPersona();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaPersonaMapper  mapper = session.getMapper(IdiomaPersonaMapper.class);
			id = mapper.updateByPrimaryKey(idiomaPersona);
			session.commit();
			if(id > 0){
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return idio;
		}catch (Exception ex) {
			idio.setError(true);
			idio.setMensaje(UtilsConstantes.MSG_EXEPCION);
			idio.setMensajeTecnico(ex.getMessage());
			return idio;		
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
	public List<IdiomaPersona> getIdiomaPersonaByAll(){
		List<IdiomaPersona> asoc = new ArrayList<IdiomaPersona>();
		IdiomaPersonaExample dtoObject = new IdiomaPersonaExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaPersonaMapper  mapper = session.getMapper(IdiomaPersonaMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<IdiomaPersona>();
			}
		}catch(Exception ex){
			return new ArrayList<IdiomaPersona>();
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
	public IdiomaPersonaExt getIdiomaPersonaById(BigDecimal id){
		IdiomaPersonaExt asoc = new IdiomaPersonaExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaPersonaMapper  mapper = session.getMapper(IdiomaPersonaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new IdiomaPersonaExt();
			}
		}catch(Exception ex){
			return new IdiomaPersonaExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param codPersona
	 * @return
	 */
	public List<IdiomaPersonaExt> getIdiomaPersonaByPersona(IdiomaPersona idiomaPersona){
		List<IdiomaPersonaExt> asoc = new ArrayList<IdiomaPersonaExt>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaPersonaMapper  mapper = session.getMapper(IdiomaPersonaMapper.class);
			asoc =  mapper.selectporCodPersona(idiomaPersona);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<IdiomaPersonaExt>();
			}
		}catch(Exception ex){
			return new ArrayList<IdiomaPersonaExt>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
