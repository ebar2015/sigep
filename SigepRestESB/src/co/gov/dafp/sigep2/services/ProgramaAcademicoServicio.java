package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ProgramaAcademico;
import co.gov.dafp.sigep2.bean.ProgramaAcademicoExample;
import co.gov.dafp.sigep2.bean.ext.ProgramaAcademicoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ProgramaAcademicoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ProgramaAcademicoServicio.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ProgramaAcademicoServicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4962004047938785568L;
	
	/**
	 * 
	 * @param ProgramaAcademico
	 * @return
	 */
	public ProgramaAcademico insertProgramaAcademico (ProgramaAcademico programaAcademico){
		ProgramaAcademico resp = new ProgramaAcademico();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProgramaAcademicoMapper  mapper = session.getMapper(ProgramaAcademicoMapper.class);
			id =  mapper.insert(programaAcademico);
			if(id > 0){
				resp.setError(false);
				resp.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
				return resp;
			}else{
				resp.setError(false);
				resp.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
				session.commit();
				return resp;
			}
		}catch (Exception ex) {
			resp.setError(true);
			resp.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			resp.setMensajeTecnico(ex.getMessage());
			session.commit();
			return resp;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param ProgramaAcademico
	 * @return
	 */
	public ProgramaAcademico updateProgramaAcademico(ProgramaAcademico programaAcademico){
		ProgramaAcademico resp = new ProgramaAcademico();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProgramaAcademicoMapper  mapper = session.getMapper(ProgramaAcademicoMapper.class);
			id = mapper.updateByPrimaryKey(programaAcademico);
			if(id > 0){
				resp.setError(false);
				resp.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
				return resp;
			}else{
				resp.setError(false);
				resp.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
				session.commit();
				return resp;
			}
		}catch (Exception ex) {
			resp.setError(true);
			resp.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			resp.setMensajeTecnico(ex.getMessage());
			session.commit();
			return resp;	
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
	public List<ProgramaAcademico> getProgramaAcademicoByAll(){
		List<ProgramaAcademico> asoc = new ArrayList<>();
		ProgramaAcademicoExample dtoObject = new ProgramaAcademicoExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProgramaAcademicoMapper  mapper = session.getMapper(ProgramaAcademicoMapper.class);
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
	public ProgramaAcademico getProgramaAcademicoById(int codTituloAcademico){
		ProgramaAcademico asoc = new ProgramaAcademico();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProgramaAcademicoMapper  mapper = session.getMapper(ProgramaAcademicoMapper.class);
			asoc =  mapper.selectByPrimaryKey(codTituloAcademico);
			if(asoc != null){
				return asoc;
			}else{
				return new ProgramaAcademico();
			}
		}catch(Exception ex){
			return new ProgramaAcademico();
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
	public List<ProgramaAcademico> getProgramaAcademicoByIdInt(long id){
		List<ProgramaAcademico> asoc = new ArrayList<>();
		ProgramaAcademicoExample dtoObject = new ProgramaAcademicoExample();
		dtoObject.createCriteria().andCodInstitucionEqualTo(id).andFlgActivoEqualTo((short) 1);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProgramaAcademicoMapper  mapper = session.getMapper(ProgramaAcademicoMapper.class);
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
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 7:54:22 AM
	 * @File:   ProgramaAcademicoServicio.java
	 * @param programaAcademico
	 * @return
	 */
	public List<ProgramaAcademicoExt> getProgramaAcademicoByFiltro(ProgramaAcademico programaAcademico){
		List<ProgramaAcademicoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProgramaAcademicoMapper  mapper = session.getMapper(ProgramaAcademicoMapper.class);
			asoc =  mapper.selectByFiltro(programaAcademico);
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
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 7:54:22 AM
	 * @File:   ProgramaAcademicoServicio.java
	 * @param programaAcademico
	 * @return
	 */
	public List<ProgramaAcademico> getProgramaAcademicoDuplicado(ProgramaAcademico programaAcademico){
		List<ProgramaAcademico> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProgramaAcademicoMapper  mapper = session.getMapper(ProgramaAcademicoMapper.class);
			asoc =  mapper.selectProgramaDuplicado(programaAcademico);
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
	public ProgramaAcademico getProgramaAcademicoByCodigoPrograma(int programaAcademico){
		ProgramaAcademico asoc = new ProgramaAcademico();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProgramaAcademicoMapper  mapper = session.getMapper(ProgramaAcademicoMapper.class);
			asoc =  mapper.selectCodProgramaAcademico(programaAcademico);
			if(asoc != null){
				return asoc;
			}else{
				return new ProgramaAcademico();
			}
		}catch(Exception ex){
			return new ProgramaAcademico();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
