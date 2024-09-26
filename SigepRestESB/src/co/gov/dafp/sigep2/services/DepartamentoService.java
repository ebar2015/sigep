/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Departamento;
import co.gov.dafp.sigep2.bean.DepartamentoExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DepartamentoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Departamento
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class DepartamentoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5670770362185076773L;
	
	/**
	 * 
	 * @param Departamento
	 * @return
	 */
	public Departamento insertDepartamento (Departamento departamento){
		Departamento depto = new Departamento();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DepartamentoMapper  mapper = session.getMapper(DepartamentoMapper.class);
			id =  mapper.insert(departamento);
			if(id > 0){
				depto.setError(false);
				depto.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				depto.setError(false);
				depto.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return depto;
		}catch (Exception ex) {
			depto.setError(true);
			depto.setMensaje(UtilsConstantes.MSG_EXEPCION);
			depto.setMensajeTecnico(ex.getMessage());
			return depto;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Departamento
	 * @return
	 */
	public Departamento updateDepartamento(Departamento departamento){
		Departamento depto = new Departamento();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DepartamentoMapper  mapper = session.getMapper(DepartamentoMapper.class);
			id = mapper.updateByPrimaryKey(departamento);
			session.commit();
			if(id > 0){
				depto.setError(false);
				depto.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				depto.setError(false);
				depto.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return depto;
		}catch (Exception ex) {
			depto.setError(true);
			depto.setMensaje(UtilsConstantes.MSG_EXEPCION);
			depto.setMensajeTecnico(ex.getMessage());
			return depto;	
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
	public List<Departamento> getDepartamentoByAll(int limitIni, int limitEnd){
		List<Departamento> asoc = new ArrayList<>();
		DepartamentoExample dtoObject = new DepartamentoExample();
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
			DepartamentoMapper  mapper = session.getMapper(DepartamentoMapper.class);
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
	public List<Departamento> getDepartamentoByIdPais(BigDecimal id){
		List<Departamento> asoc = new ArrayList<>();
		DepartamentoExample dtoObject = new DepartamentoExample();
		dtoObject.createCriteria().andCodPaisEqualTo(id);
		dtoObject.setOrderByClause("NOMBRE_DEPARTAMENTO");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DepartamentoMapper  mapper = session.getMapper(DepartamentoMapper.class);
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
	 * @param id
	 * @return
	 */
	public Departamento getDepartamentoById(BigDecimal id){
	    Departamento asoc ;
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DepartamentoMapper  mapper = session.getMapper(DepartamentoMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new Departamento();
			}
		}catch(Exception ex){
			
			return new Departamento();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 8:50:24 AM
	 * @File:   DepartamentoService.java
	 * @param departamento
	 * @return
	 */
	public List<Departamento> getDepartamentoByFiltro(Departamento departamento){
		List<Departamento> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DepartamentoMapper  mapper = session.getMapper(DepartamentoMapper.class);
			asoc =  mapper.selectByFiltro(departamento);
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
	 * @param Departamento
	 * @return List<Departamento>
	 */
	public List<Departamento> getDepartamentoDuplicado(Departamento departamento){
		List<Departamento> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DepartamentoMapper  mapper = session.getMapper(DepartamentoMapper.class);
			asoc =  mapper.selectDepartamentoDuplicado(departamento);
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
