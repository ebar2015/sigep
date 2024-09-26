/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.impl.LogKitLogger;
import org.apache.ibatis.session.SqlSession;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import co.gov.dafp.sigep2.bean.Municipio;
import co.gov.dafp.sigep2.bean.MunicipioExample;
import co.gov.dafp.sigep2.bean.ext.MunicipioExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.MunicipioMapper;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla MunicipioService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class MunicipioService implements Serializable {

	private static final long serialVersionUID = 3541820360614411727L;
	
	/**
	 * @param Municipio
	 * @return
	 */
	public Municipio insertMunicipio (Municipio municipio){
		Municipio mun = new Municipio();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
			id =  mapper.insert(municipio);
			if(id > 0){
				mun.setError(false);
				mun.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				mun.setError(false);
				mun.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return mun;	
		}catch (Exception ex) {
			mun.setError(true);
			mun.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			mun.setMensajeTecnico(ex.getMessage());
			return mun;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Municipio
	 * @return
	 */
	public Municipio updateMunicipio(Municipio municipio){
		Municipio mun = new Municipio();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
			id = mapper.updateByPrimaryKey(municipio);
			if(id > 0){
				mun.setError(false);
				mun.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				mun.setError(false);
				mun.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return mun;	
		}catch (Exception ex) {
			mun.setError(true);
			mun.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			mun.setMensajeTecnico(ex.getMessage());
			return mun;	
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
	public List<Municipio> getMunicipioByAll(int limitIni, int limitEnd){
		List<Municipio> asoc = new ArrayList<>();
		MunicipioExample dtoObject = new MunicipioExample();
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
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
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
	public List<Municipio> getMunicipioByIdDpto(Short id){
		List<Municipio> asoc = new ArrayList<>();
		MunicipioExample dtoObject = new MunicipioExample();
		dtoObject.createCriteria().andCodDepartamentoEqualTo(id);
		dtoObject.setOrderByClause("NOMBRE_MUNICIPIO");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
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
	 * @fecha 27/07/2018 2:31:51 p. m.
	 * @param codMunicipio
	 * @return
	 */
	public Municipio getMunicipioById(int codMunicipio){
		Municipio asoc = new Municipio();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
			asoc =  mapper.selectByPrimaryKey(codMunicipio);
			if(asoc!= null){
				return asoc;
			}else{
				return new Municipio();
			}
		}catch(Exception ex){
		
			return new Municipio();
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
	 * MunicipioService.java
	 * @param codMunicipio
	 * @return
	 */
	public Municipio getMunicipioByDane(int codMunicipioDane){
		Municipio asoc = new Municipio();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
			asoc =  mapper.selectByDane(codMunicipioDane);
			if(asoc!= null){
				return asoc;
			}else{
				return new Municipio();
			}
		}catch(Exception ex){
			return new Municipio();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 7:07:49 AM
	 * @File:   MunicipioService.java
	 * @param municipio
	 * @return
	 */
	public List<Municipio> getMunicipioByFiltro(Municipio municipio){
		List<Municipio> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
			asoc =  mapper.selectByFiltro(municipio);
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
	 * @param Municipio
	 * @return List<Municipio>
	 */
	public List<Municipio> getMunicipioDuplicado(Municipio municipio){
		List<Municipio> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
			asoc =  mapper.selectMunicipioDuplicado(municipio);
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
	
	public List<MunicipioExt> getMunicipioPorDepartamento(Municipio municipio){
		List<MunicipioExt> objectList = new ArrayList<>();
		MunicipioExt muni = new MunicipioExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			MunicipioMapper  mapper = session.getMapper(MunicipioMapper.class);
			objectList =  mapper.selectMunicipioPorDepartamento(municipio);
			
			if (objectList !=null) {
				return objectList;
			}else {
					return new ArrayList<>();
			}
		} catch (Exception e) {
			e.getMessage();
			muni.setError(true);
			muni.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			muni.setMensajeTecnico(e.getMessage());
			return objectList;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}
}
