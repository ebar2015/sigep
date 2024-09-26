package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PreguntaOpinion;
import co.gov.dafp.sigep2.bean.PreguntaOpinionExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PreguntaOpinionMapper;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PreguntaOpinionService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PreguntaOpinionService implements Serializable {

	private static final long serialVersionUID = 4446071523686145469L;
	
	/**
	 * 
	 * @param PreguntaOpinion
	 * @return
	 */
	public boolean insertPreguntaOpinion (PreguntaOpinion preguntaOpinion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			id =  mapper.insert(preguntaOpinion);
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
	 * @param PreguntaOpinion
	 * @return
	 */
	public String  deletePreguntaOpinion (int codPreguntaOpinion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			id =  mapper.deleteByPrimaryKey(codPreguntaOpinion);
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
	 * s
	 * @param PreguntaOpinion
	 * @return
	 */
	public boolean updatePreguntaOpinion(PreguntaOpinion preguntaOpinion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			id = mapper.updateByPrimaryKey(preguntaOpinion);
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
	public List<PreguntaOpinion> getPreguntaOpinionByAll(){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		PreguntaOpinionExample dtoObject = new PreguntaOpinionExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
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
	public PreguntaOpinion getPreguntaOpinionById(int id){
		PreguntaOpinion asoc = new PreguntaOpinion();
		PreguntaOpinionExample dtoObject = new PreguntaOpinionExample();
		dtoObject.createCriteria().andCodPreguntaOpinionEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!= null){
				return asoc;
			}else{
				return new PreguntaOpinion();
			}
		}catch(Exception ex){
		
			return new PreguntaOpinion();
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
	public PreguntaOpinion getPreguntaOpinionRDN(){
		PreguntaOpinion asoc = new PreguntaOpinion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			asoc =  mapper.selectrdn();
			if(asoc!= null){
				return asoc;
			}else{
				return new PreguntaOpinion();
			}
		}catch(Exception ex){
			
			return new PreguntaOpinion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param preguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getPreguntaOpinionPorFechaIni(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		PreguntaOpinionExample dtoObject = new PreguntaOpinionExample();
		dtoObject.createCriteria().
		andFechaInicioEqualTo(preguntaOpinion.getFechaInicio()).
		andFlgActivoEqualTo((short) 1);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
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
	 * @param preguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getpreguntaOpinionFechapregunta(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			asoc =  mapper.selectpreguntaOpinionFechapregunta(preguntaOpinion);
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
	 * @param preguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getpreguntaOpinionFecha(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			asoc =  mapper.selectpreguntaOpinionFecha(preguntaOpinion);
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
	 * @param preguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getPreguntaOpinionTodos(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		if(preguntaOpinion.getLimitEnd() == 0) {
			preguntaOpinion.setLimitEnd(10);
		}
		if(preguntaOpinion.getPregunta() != null && !preguntaOpinion.getPregunta().isEmpty()) {
			preguntaOpinion.setPregunta(preguntaOpinion.getPregunta().toUpperCase());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			asoc =  mapper.selectTodos(preguntaOpinion);
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
	 * @param preguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getPreguntaOpinionPorFechaFin(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		PreguntaOpinionExample dtoObject = new PreguntaOpinionExample();
		dtoObject.createCriteria().andFechaFinEqualTo(preguntaOpinion.getFechaFin());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
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
	 * @param preguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getPreguntaOpinionEntreFechaIni(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		PreguntaOpinionExample dtoObject = new PreguntaOpinionExample();
		dtoObject.createCriteria().andFechaInicioBetween(preguntaOpinion.getFechaInicio(), preguntaOpinion.getFechaFin());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
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
	 * @param preguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getPreguntaOpinionEntreFechaFin(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		PreguntaOpinionExample dtoObject = new PreguntaOpinionExample();
		dtoObject.createCriteria().andFechaFinBetween(preguntaOpinion.getFechaInicio(), preguntaOpinion.getFechaFin());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
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
	 * @param preguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getPreguntaOpinionEntreFechaIniFin(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		PreguntaOpinionExample dtoObject = new PreguntaOpinionExample();
		dtoObject.createCriteria().andFechaInicioGreaterThanOrEqualTo(preguntaOpinion.getFechaInicio()).
		andFechaFinLessThanOrEqualTo(preguntaOpinion.getFechaFin());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
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
	 * @author: Maria Alejandra C 
	 * @fecha 10/06/2019 11:57:26 a. m.
	 * @param PreguntaOpinion
	 * @return
	 */
	public List<PreguntaOpinion> getPreguntaPorNombre(PreguntaOpinion preguntaOpinion){
		List<PreguntaOpinion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PreguntaOpinionMapper  mapper = session.getMapper(PreguntaOpinionMapper.class);
			asoc =  mapper.selectPreguntaPorNombre(preguntaOpinion);
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
