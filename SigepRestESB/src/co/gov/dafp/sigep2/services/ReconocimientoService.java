package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Reconocimiento;
import co.gov.dafp.sigep2.bean.ReconocimientoExample;
import co.gov.dafp.sigep2.bean.ext.ReconocimientoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ReconocimientoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ReconocimientoService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ReconocimientoService implements Serializable {

	private static final long serialVersionUID = 9095975474417926028L;
	
	/**
	 * 
	 * @param Reconocimiento
	 * @return
	 */
	public boolean insertReconocimiento (Reconocimiento reconocimiento){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ReconocimientoMapper  mapper = session.getMapper(ReconocimientoMapper.class);
			id =  mapper.insert(reconocimiento);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:51:23 p. m.
	 * @param reconocimiento
	 * @return
	 */
	public boolean updateReconocimiento(Reconocimiento reconocimiento){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ReconocimientoMapper  mapper = session.getMapper(ReconocimientoMapper.class);
			id = mapper.updateByPrimaryKey(reconocimiento);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:51:18 p. m.
	 * @param limitIni
	 * @param limitEnd
	 * @return
	 */
	public List<Reconocimiento> getReconocimientoByAll(int limitIni, int limitEnd){
		List<Reconocimiento> asoc = new ArrayList<>();
		ReconocimientoExample dtoObject = new ReconocimientoExample();
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
			ReconocimientoMapper  mapper = session.getMapper(ReconocimientoMapper.class);
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
	public ReconocimientoExt getReconocimientoByPersona(BigDecimal id){
		List<ReconocimientoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ReconocimientoMapper  mapper = session.getMapper(ReconocimientoMapper.class);
			asoc =  mapper.selectReconocimientoByPersona(id.longValue());
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new ReconocimientoExt();
			}
		}catch(Exception ex){
		
			return new ReconocimientoExt();
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
	 * @param reconocimiento
	 * @return
	 */
	public List<ReconocimientoExt> getReconocimientoByFiltro(Reconocimiento reconocimiento){
		List<ReconocimientoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ReconocimientoMapper  mapper = session.getMapper(ReconocimientoMapper.class);
			asoc =  mapper.selectByFiltro(reconocimiento);
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
