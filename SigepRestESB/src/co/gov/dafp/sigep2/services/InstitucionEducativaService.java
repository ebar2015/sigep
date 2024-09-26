package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.InstitucionEducativa;
import co.gov.dafp.sigep2.bean.InstitucionEducativaExample;
import co.gov.dafp.sigep2.bean.ext.InstitucionEducativaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.InstitucionEducativaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla InstitucionEducativaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class InstitucionEducativaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1230517880938237173L;
	
	/**
	 * 
	 * @param InstitucionEducativa
	 * @return
	 */
	public InstitucionEducativa insertInstitucionEducativa (InstitucionEducativa institucionEducativa){
		InstitucionEducativa intedu = new InstitucionEducativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			InstitucionEducativaMapper  mapper = session.getMapper(InstitucionEducativaMapper.class);
			id =  mapper.insert(institucionEducativa);
			if(id > 0){
				intedu.setError(false);
				intedu.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				intedu.setError(false);
				intedu.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return intedu;
		}catch (Exception ex) {
			intedu.setError(true);
			intedu.setMensaje(UtilsConstantes.MSG_EXEPCION);
			intedu.setMensajeTecnico(ex.getMessage());
			return intedu;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param InstitucionEducativa
	 * @return
	 */
	public InstitucionEducativa updateInstitucionEducativa(InstitucionEducativa institucionEducativa){
		InstitucionEducativa intedu = new InstitucionEducativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			InstitucionEducativaMapper  mapper = session.getMapper(InstitucionEducativaMapper.class);
			id = mapper.updateByPrimaryKey(institucionEducativa);
			session.commit();
			if(id > 0){
				intedu.setError(false);
				intedu.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				intedu.setError(false);
				intedu.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return intedu;
		}catch (Exception ex) {
			intedu.setError(true);
			intedu.setMensaje(UtilsConstantes.MSG_EXEPCION);
			intedu.setMensajeTecnico(ex.getMessage());
			return intedu;	
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
	public List<InstitucionEducativa> getInstitucionEducativaByAll(){
		List<InstitucionEducativa> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			InstitucionEducativaMapper  mapper = session.getMapper(InstitucionEducativaMapper.class);
			asoc =  mapper.selectInstitucionesPro();
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			asoc = new ArrayList<>();
			InstitucionEducativa d = new InstitucionEducativa();
			d.setError(true);
			d.setMensaje("Error De Sistema");
			d.setMensajeTecnico(ex.getMessage());
			asoc.add(d);
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @return
	 */
	public List<InstitucionEducativa> getInstitucionEducativaByTipo(int tipo){
		List<InstitucionEducativa> asoc = new ArrayList<>();
		InstitucionEducativaExample dtoObject = new InstitucionEducativaExample();
		dtoObject.createCriteria().andCodTipoInstitucionEqualTo(tipo);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			InstitucionEducativaMapper  mapper = session.getMapper(InstitucionEducativaMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			asoc = new ArrayList<>();
			InstitucionEducativa d = new InstitucionEducativa();
			d.setError(true);
			d.setMensaje("Error De Sistema");
			d.setMensajeTecnico(ex.getMessage());
			asoc.add(d);
			return asoc;
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
	public InstitucionEducativa institucionEducativaById(long id){
		InstitucionEducativa asoc = new InstitucionEducativa();
		InstitucionEducativaExample dtoObject = new InstitucionEducativaExample();
		dtoObject.createCriteria().andCodInstitucionEducativaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			InstitucionEducativaMapper  mapper = session.getMapper(InstitucionEducativaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc  != null){
				return asoc;
			}else{
				return new InstitucionEducativa();
			}
		}catch(Exception ex){
		
			return new InstitucionEducativa();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * Feb 21, 2019
	 * InstitucionEducativaService.java
	 * @param id
	 * @return
	 */
	public InstitucionEducativa institucionEducativaCodMen(long id){
		InstitucionEducativa asoc = new InstitucionEducativa();
		InstitucionEducativaExample dtoObject = new InstitucionEducativaExample();
		dtoObject.createCriteria().andCodInstitucionMenEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			InstitucionEducativaMapper  mapper = session.getMapper(InstitucionEducativaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc  != null){
				return asoc;
			}else{
				return null;
			}
		}catch(Exception ex){
		
			return null;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 7:21:15 AM
	 * @File:   InstitucionEducativaService.java
	 * @param institucionEducativa
	 * @return
	 */
	public List<InstitucionEducativaExt> getInstitucionEducativaByFiltro(InstitucionEducativa institucionEducativa){
		List<InstitucionEducativaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			InstitucionEducativaMapper  mapper = session.getMapper(InstitucionEducativaMapper.class);
			asoc =  mapper.selectByFiltro(institucionEducativa);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			asoc = new ArrayList<>();
			InstitucionEducativaExt d = new InstitucionEducativaExt();
			d.setError(true);
			d.setMensaje("Error De Sistema");
			d.setMensajeTecnico(ex.getMessage());
			asoc.add(d);
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * 
	 * @Author: Maria Alejandra
	 * @Date:   Oct 21, 2019, 7:21:15 AM
	 * @File:   InstitucionEducativaService.java
	 * @param institucionEducativa
	 * @return List<InstitucionEducativa>
	 */
	public List<InstitucionEducativa> getInstitucionByPais(InstitucionEducativa institucionEducativa){
		List<InstitucionEducativa> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			institucionEducativa.setLimitInit(0);
			institucionEducativa.setLimitEnd(1000);
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			InstitucionEducativaMapper  mapper = session.getMapper(InstitucionEducativaMapper.class);
			asoc =  mapper.selectInstitucionByPais(institucionEducativa);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			asoc = new ArrayList<>();
			InstitucionEducativa d = new InstitucionEducativa();
			d.setError(true);
			d.setMensaje("Error De Sistema");
			d.setMensajeTecnico(ex.getMessage());
			asoc.add(d);
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
