package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ResultadoPregunta;
import co.gov.dafp.sigep2.bean.ResultadoPreguntaExample;
import co.gov.dafp.sigep2.bean.ext.ResultadoPreguntaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ResultadoPreguntaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ResultadoPreguntaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @fecha 27/07/2018 2:52:33 p. m.
 */
public class ResultadoPreguntaService implements Serializable {

	private static final long serialVersionUID = 7334941518772975121L;
	
	/**
	 * 
	 * @param ResultadoPregunta
	 * @return
	 */
	public boolean insertResultadoPregunta (ResultadoPregunta resultadoPregunta){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			id =  mapper.insert(resultadoPregunta);
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
	 * @param ResultadoPregunta
	 * @return
	 */
	public boolean updateResultadoPregunta(ResultadoPregunta resultadoPregunta){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			id = mapper.updateByPrimaryKey(resultadoPregunta);
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
	public List<ResultadoPregunta> getResultadoPreguntaByAll(int limitIni, int limitEnd){
		List<ResultadoPregunta> asoc = new ArrayList<>();
		ResultadoPreguntaExample dtoObject = new ResultadoPreguntaExample();
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
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
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
	public List<ResultadoPregunta> getResultadoPreguntaById(long id){
		List<ResultadoPregunta> asoc = new ArrayList<>();
		ResultadoPreguntaExample dtoObject = new ResultadoPreguntaExample();
		dtoObject.createCriteria().andCodResultadoPreguntaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
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
	public List<ResultadoPregunta> getRespuestasPregunta(ResultadoPregunta resultadoPregunta){
		List<ResultadoPregunta> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectRespuestasPregunta(resultadoPregunta);
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
	public List<ResultadoPreguntaExt> getRespuestasEstadistica(ResultadoPregunta resultadoPregunta){
		List<ResultadoPreguntaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectRespuestasEstadistica(resultadoPregunta);
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
	public ResultadoPregunta getTotalRespuestas(long codPreguntaOpinion){
		ResultadoPregunta asoc = new ResultadoPregunta();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectTotalRespuestas(codPreguntaOpinion);
			if(asoc != null){
				return asoc;
			}else{
				return new ResultadoPregunta();
			}
		}catch(Exception ex){
		
			return new ResultadoPregunta();
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
	public List<ResultadoPregunta> getRespuestasGrafico(int codPreguntaOpinion){
		List<ResultadoPregunta> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectGrafico(codPreguntaOpinion);
			if(asoc != null){
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
	public List<ResultadoPreguntaExt> getRespuestasGraficoEdades(ResultadoPregunta resultadoPregunta){
		List<ResultadoPreguntaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectGraficoEdades(resultadoPregunta);
			if(asoc != null){
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
	public List<ResultadoPreguntaExt> getRespuestaPreguntaPersona(int codPreguntaOpinion){
		List<ResultadoPreguntaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectRespuestaPreguntaPersona(codPreguntaOpinion);
			if(asoc != null){
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
	public ResultadoPregunta getRespuestasPuntagePregunta(ResultadoPregunta resultadoPregunta){
		ResultadoPregunta asoc = new ResultadoPregunta();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectRespuestasPuntagePregunta(resultadoPregunta);
			if(asoc != null){
				return asoc;
			}else{
				return new ResultadoPregunta();
			}
		}catch(Exception ex){
			
			return new ResultadoPregunta();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Retorna informacion del nivel jerarquico
	 * @modulo Gestion de Preguntas de opinion 
	 * @param ResultadoPregunta
	 * @return List<ResultadoPreguntaExt>
	 * @autor Maria Alejandra Colorado
	 */
	public List<ResultadoPreguntaExt> getRespuestasNivelJerarquico(ResultadoPregunta resultadoPregunta){
		List<ResultadoPreguntaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectGraficoNivelJerarquico(resultadoPregunta);
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
	 * Retorna informacion sobre las respuestas de una pregunta especifica
	 * @modulo Gestion de Preguntas de opinion 
	 * @param ResultadoPregunta
	 * @return List<ResultadoPreguntaExt>
	 * @autor Maria Alejandra Colorado
	 */
	public List<ResultadoPreguntaExt> getInformacionRespuestasExportar(ResultadoPregunta resultadoPregunta){
		List<ResultadoPreguntaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectInformacionExportar(resultadoPregunta);
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
	 * Retorna informacion del reten social
	 * @modulo Gestion de Preguntas de opinion 
	 * @param ResultadoPregunta
	 * @return List<ResultadoPreguntaExt>
	 * @autor Maria Alejandra Colorado
	 */
	public List<ResultadoPreguntaExt> getRespuestasRetenSocial(ResultadoPregunta resultadoPregunta){
		List<ResultadoPreguntaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ResultadoPreguntaMapper  mapper = session.getMapper(ResultadoPreguntaMapper.class);
			asoc =  mapper.selectGraficoRetenSocial(resultadoPregunta);
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
