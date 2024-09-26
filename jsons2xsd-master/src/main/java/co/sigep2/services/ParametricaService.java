/**
 * 
 */
package co.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.sigep2.bean.Parametrica;
import co.sigep2.bean.ParametricaExample;
import co.sigep2.factory.SessionFactory;
import co.sigep2.interfaces.ParametricaMapper;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ParametricaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ParametricaService implements Serializable {

	private static final long serialVersionUID = -433951773416940762L;
	
	
	/**
	 * 
	 * @param Parametrica
	 * @return
	 */
	public String deleteParametrica(BigDecimal codParametrica){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			id =  mapper.deleteByPrimaryKey(codParametrica);
			if(id > 0){
				session.commit();
				return "true";
			}else{
				return "false";
			}
		}catch (Exception ex) {
			return "false";
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * 
	 * @param parametrica
	 * @return
	 */
	public List<Parametrica> getParametricatByNameLike(Parametrica parametrica){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			if(parametrica != null) {
				if(parametrica.getNombreParametro()!= null && !parametrica.getNombreParametro().equals("null")) {
					parametrica.setNombreParametro(parametrica.getNombreParametro().toUpperCase());
				}else {
					parametrica.setNombreParametro("");
				}
				if(parametrica.getLimitEnd() == 0) {
					parametrica.setLimitEnd(10);
				}
			}
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByNameLike(parametrica);
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
	 * @param id
	 * @return
	 */
	public Parametrica getPrametricaById(BigDecimal id){
		Parametrica asoc = new Parametrica();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new Parametrica();
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return new Parametrica();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Parametrica>  getPrametricaByPadreiId(Parametrica user){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectById(user);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Parametrica>  getPrametricaByPadreMovil(BigDecimal id){
		List<Parametrica> asoc = new ArrayList<>();
		ParametricaExample dtoObject = new ParametricaExample();
		dtoObject.createCriteria().andCodPadreParametricaEqualTo(id).andFlgEstadoEqualTo((short) 1);
		dtoObject.setOrderByClause("COD_TABLA_PARAMETRICA");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectPadreMovil(id);
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
	 * @param id
	 * @return
	 */
	public List<Parametrica>  getPrametricaByPadre(Parametrica parametrica){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectPadreTodo(parametrica);
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
	* Elaborado por Jose Viscaya 
	* @fecha 28/08/2018 2:22:01 p.m.
	* @param id
	* @return
	*
	 */
	public Parametrica getPrametricaByValue(String value, BigDecimal padreId){
		List<Parametrica> asoc = new ArrayList<>();
		Parametrica pa = new Parametrica();
		pa.setCodPadreParametrica(padreId);
		pa.setValorParametro(value);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByParamValue(pa);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new Parametrica();
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return  new Parametrica();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
}
