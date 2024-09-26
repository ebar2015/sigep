package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.LogroRecurso;
import co.gov.dafp.sigep2.bean.LogroRecursoExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.LogroRecursoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla LogroRecursoService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class LogroRecursoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7013152381571690498L;
	
	/**
	 * 
	 * @param LogroRecurso
	 * @return
	 */
	public LogroRecurso insertLogroRecurso (LogroRecurso logroRecurso){
		LogroRecurso logro = new LogroRecurso();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			LogroRecursoMapper  mapper = session.getMapper(LogroRecursoMapper.class);
			id =  mapper.insert(logroRecurso);
			if(id > 0){
				logro.setError(false);
				logro.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				logro.setError(false);
				logro.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return logro;
		}catch (Exception ex) {
			logro.setError(true);
			logro.setMensaje(UtilsConstantes.MSG_EXEPCION);
			logro.setMensajeTecnico(ex.getMessage());
			return logro;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param LogroRecurso
	 * @return
	 */
	public LogroRecurso updateLogroRecurso(LogroRecurso logroRecurso){
		LogroRecurso logro = new LogroRecurso();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			LogroRecursoMapper  mapper = session.getMapper(LogroRecursoMapper.class);
			id = mapper.updateByPrimaryKey(logroRecurso);
			session.commit();
			if(id > 0){
				logro.setError(false);
				logro.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				logro.setError(false);
				logro.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return logro;
		}catch (Exception ex) {
			logro.setError(true);
			logro.setMensaje(UtilsConstantes.MSG_EXEPCION);
			logro.setMensajeTecnico(ex.getMessage());
			return logro;		
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
	public List<LogroRecurso> getLogroRecursoByAll(int limitIni, int limitEnd){
		List<LogroRecurso> asoc = new ArrayList<>();
		LogroRecursoExample dtoObject = new LogroRecursoExample();
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
			LogroRecursoMapper  mapper = session.getMapper(LogroRecursoMapper.class);
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
	 * @param logroRecurso
	 * @return
	 */
	public List<LogroRecurso> getLogroRecursoFiltro(LogroRecurso logroRecurso){
		List<LogroRecurso> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			LogroRecursoMapper  mapper = session.getMapper(LogroRecursoMapper.class);
			asoc =  mapper.selectByFiltro(logroRecurso);
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
	 * @param logroRecurso
	 * @return
	 */
	public List<LogroRecurso> getLogroRecursoFiltroGerencia(LogroRecurso logroRecurso){
		List<LogroRecurso> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			LogroRecursoMapper  mapper = session.getMapper(LogroRecursoMapper.class);
			asoc =  mapper.selectByFiltroGerencia(logroRecurso);
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
	public LogroRecurso getLogroRecursoById(long id){
		LogroRecurso asoc = new LogroRecurso();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			LogroRecursoMapper  mapper = session.getMapper(LogroRecursoMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new LogroRecurso();
			}
		}catch(Exception ex){
		
			return new LogroRecurso();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	public LogroRecurso getLogroRecursoByPersona(BigDecimal id){
		List<LogroRecurso> asoc = new ArrayList<>();
		LogroRecursoExample dtoObject = new LogroRecursoExample();
		dtoObject.createCriteria().andCodPersonaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			LogroRecursoMapper  mapper = session.getMapper(LogroRecursoMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new LogroRecurso();
			}
		}catch(Exception ex){
		
			return new LogroRecurso();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
