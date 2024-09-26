/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Recurso;
import co.gov.dafp.sigep2.bean.RecursoExample;
import co.gov.dafp.sigep2.bean.ext.RecursoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.RecursoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla RecursoService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class RecursoService implements Serializable {

	private static final long serialVersionUID = 4881022370494255707L;
	
	/**
	 * @param Recurso
	 * @return
	 */
	public int insertRecurso (Recurso recurso){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RecursoMapper  mapper = session.getMapper(RecursoMapper.class);
			id =  mapper.insert(recurso);
			if(id > 0){
				session.commit();
				return id;
			}else{
				return 0;
			}
		}catch (Exception ex) {
			return 0;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Recurso
	 * @return
	 */
	public boolean updateRecurso(Recurso recurso){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RecursoMapper  mapper = session.getMapper(RecursoMapper.class);
			id = mapper.updateByPrimaryKey(recurso);
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
	 * @fecha 27/07/2018 2:52:01 p. m.
	 * @param limitIni
	 * @param limitEnd
	 * @return
	 */
	public List<Recurso> getRecursoByAll(int limitIni, int limitEnd){
		List<Recurso> asoc = new ArrayList<>();
		RecursoExample dtoObject = new RecursoExample();
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
			RecursoMapper  mapper = session.getMapper(RecursoMapper.class);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:52:11 p. m.
	 * @param id
	 * @return
	 */
	public List<Recurso> getRecursoById(BigDecimal id){
		List<Recurso> asoc = new ArrayList<>();
		RecursoExample dtoObject = new RecursoExample();
		dtoObject.createCriteria().andCodRecursoEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RecursoMapper  mapper = session.getMapper(RecursoMapper.class);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:52:17 p. m.
	 * @param codVentana
	 * @return
	 */
	public Recurso getRecursoPorCodVentana(String codVentana){
		List<Recurso> asoc = new ArrayList<>();
		RecursoExample dtoObject = new RecursoExample();
		dtoObject.createCriteria().andCodigoVentanaEqualTo(codVentana);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RecursoMapper  mapper = session.getMapper(RecursoMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new Recurso();
			}
		}catch(Exception ex){
		
			return new Recurso();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 17/08/2018 11:29:55 a.m.
	* @param id
	* @return
	*
	*/
	public List<RecursoExt> getRecursoList(RecursoExt id){
		List<RecursoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		if(id.getCodRolList() != null) {
			if(id.getCodRolList().length == 0) {
				id.setCodRolList(null);
			}
		}
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RecursoMapper  mapper = session.getMapper(RecursoMapper.class);
			asoc =  mapper.selectRolesList(id);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
		  System.err.println(ex.getMessage());
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
