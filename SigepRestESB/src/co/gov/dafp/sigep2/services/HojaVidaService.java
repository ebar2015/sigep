/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.HojaVida;
import co.gov.dafp.sigep2.bean.HojaVidaExample;
import co.gov.dafp.sigep2.bean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.VinculacionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.HojaVidaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla HojaVidaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class HojaVidaService implements Serializable {

	private static final long serialVersionUID = -8313810497290307533L;
	
	/**
	 * 
	 * @param HojaVida
	 * @return
	 */
	public HojaVida insertHojaVida (HojaVida hojaVida){
		SqlSession session = null;
		HojaVida res = new HojaVida();
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			id =  mapper.insert(hojaVida);
			if(id > 0){
				session.commit();
				res.setError(false);
				res.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
			}else{
				res.setError(false);
				res.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return res;
		}catch (Exception ex) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(ex.getMessage());
			return res;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param Hoja de Vida
	 * @return
	 */
	public HojaVida updateHojaVida(HojaVida auditoria){
		SqlSession session = null;
		HojaVida res = new HojaVida();
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			id = mapper.updateByPrimaryKey(auditoria);
			session.commit();
			if(id > 0){
				session.commit();
				res.setError(false);
				res.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			}else{
				res.setError(false);
				res.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return res;
		}catch (Exception ex) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(ex.getMessage());
			return res;	
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
	public List<HojaVida> getHojaVidaByAll(int limitIni, int limitEnd){
		List<HojaVida> asoc = new ArrayList<>();
		HojaVidaExample dtoObject = new HojaVidaExample();
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
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
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
	 * @return
	 */
	public List<HojaVidaExt> getHojaVidafiltro(HojaVida hojaVida){
		List<HojaVidaExt> asoc = new ArrayList<>();
		if(hojaVida.getLimitEnd() == null) {
			hojaVida.setLimitInit(0);
			hojaVida.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.selectHojaFiltro(hojaVida);
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
	public List<HojaVida> getHojaVidaById(long id){
		List<HojaVida> asoc = new ArrayList<>();
		HojaVidaExample dtoObject = new HojaVidaExample();
		dtoObject.createCriteria().andCodHojaVidaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
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
	 * @param persona
	 * @return
	 */
	public boolean eliminarHojaDeVida(HojaVidaExt hv){
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			int asoc =  mapper.eliminarHojaVida(hv);
			session.commit();
			return (asoc == -1)?true:false;
		}catch(Exception ex){
			return false;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * Metodo para validar si la persona tiene aprobada la hoja de vida y no se pasa del tiempo parametrisable
	 * @param hojaVida
	 * @return List<HojaVidaExt>
	 */
	public List<HojaVidaExt> getPersonaHojaVidaAprobada(HojaVidaExt hojaVida){
		List<HojaVidaExt> asoc = new ArrayList<>();
		if(hojaVida.getLimitEnd() == null || hojaVida.getLimitEnd() == 0) {
			hojaVida.setLimitInit(0);
			hojaVida.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.selectPersonaHojaVidaAprobada(hojaVida);
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
	 * @author: Jose Viscaya 
	 * @fecha 17:12:23 2018
	 * @param HojaVidaExt
	 * @return
	 */
	public HojaVidaExt getValidacionHv(VinculacionExt vinculacionExt){
		SqlSession session = null;
		HojaVidaExt res = new HojaVidaExt();
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			res = mapper.validarUsuarioHV(vinculacionExt);
			if(res != null){
				res.setError(false);
			}
			return res;
		}catch (Exception ex) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(ex.getMessage());
			return res;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	

	/** 
	 * Elaborado por Maria Alejandra C 
	 * @fecha 4/12/2018
	 * @param HojaVidaExt
	 * @return
	 */
	public HojaVidaExt getTotalAprobados(HojaVidaExt hojaVidaExt){
		SqlSession session = null;
		HojaVidaExt res = new HojaVidaExt();
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			res = mapper.getTotalAprobados(hojaVidaExt);
			if(res != null){
				res.setError(false);
			}
			return res;
		}catch (Exception ex) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(ex.getMessage());
			return res;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/** 
	 * Elaborado por Maria Alejandra C 
	 * @fecha 13/02/2019
	 * @param HojaVidaExt
	 * @return
	 */
	public List<HojaVidaExt> getHVPersonaConUTL(HojaVidaExt hojaVidaExt){
		List<HojaVidaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.selectHVPersonaConUTL(hojaVidaExt);
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
	 * @Elaborado_Por: Jose Viscaya
	 * @param vinculacionExt
	 * @return List<HojaVidaExt>
	 * @Fecha :Feb 22, 2019
	 * HojaVidaService.java
	 */
	public List<HojaVidaExt> getHVPersona(VinculacionExt vinculacionExt){
		List<HojaVidaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.selectHVPorCodPersona(vinculacionExt);
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
	 * @Elaborado_Por: Jose Viscaya
	 * @param hojaVida
	 * @return
	 * @Fecha :Feb 22, 2019
	 * HojaVidaService.java
	 */
	public List<HojaVidaExt> getHVPersonaRoles(HojaVida hojaVida){
		List<HojaVidaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.selectPersonaRoles(hojaVida);
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
	 * Elaborado por Maria Alejandra C 
	 * @fecha 10/01/2020
	 * @param HojaVidaExt
	 * @return List<HojaVidaExt>
	 */
	public List<HojaVidaExt> getHVPersonaAsociadaUTL(HojaVidaExt hojaVidaExt){
		List<HojaVidaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.selectHVPersonaAsociadaUTL(hojaVidaExt);
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
	 * Elaborado por Maria Alejandra
	 * @fecha 15/07/2022
	 * @param PersonaExt
	 * @return
	 */
	public PersonaExt getHojaVidaPersona(PersonaExt personaExt){
		SqlSession session = null;
		PersonaExt res = new PersonaExt();
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			res = mapper.getHojaVidaPersona(personaExt);
			if(res != null){
				res.setError(false);
			}
			return res;
		}catch (Exception ex) {
			res.setError(true);
			res.setMensaje(UtilsConstantes.MSG_EXEPCION);
			res.setMensajeTecnico(ex.getMessage());
			return res;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
