package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CalidadDeDatosNotificacion;
import co.gov.dafp.sigep2.bean.UsuarioRolEntidad;
import co.gov.dafp.sigep2.bean.UsuarioRolEntidadExample;
import co.gov.dafp.sigep2.bean.ext.UsuarioExt;
import co.gov.dafp.sigep2.bean.ext.UsuarioRolEntidadExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CalidadDeDatosMapper;
import co.gov.dafp.sigep2.interfaces.UsuarioRolEntidadMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla UsuarioRolEntidadService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class UsuarioRolEntidadService implements Serializable {
	private static final long serialVersionUID = 7288973103090767616L;
	/**
	 * 
	 * @param UsuarioRolEntidad
	 * @return
	 */
	public boolean insertUsuarioRolEntidad (UsuarioRolEntidad usuarioRolEntidad){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			id =  mapper.insert(usuarioRolEntidad);
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
	 * @param UsuarioRolEntidad
	 * @return
	 */
	public boolean updateUsuarioRolEntidad(UsuarioRolEntidad usuarioRolEntidad){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			id = mapper.updateByPrimaryKey(usuarioRolEntidad);
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
	 * @param UsuarioRolEntidad
	 * @return
	 */
	public UsuarioRolEntidad updateQuitarRol(UsuarioRolEntidadExt usuarioRolEntidad){	
		UsuarioRolEntidad asc = new UsuarioRolEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			id = mapper.updateQuitarRol(usuarioRolEntidad);
			session.commit();
			if(id == 1) {
				asc.setError(false);
				asc.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			}else {
				asc.setError(false);
				asc.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
		}catch (Exception ex) {
			asc.setError(true);
			asc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asc.setMensajeTecnico(ex.getMessage());
			return asc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return asc;
	}
	
	/**
	 * 
	 * @param UsuarioRolEntidad
	 * @return
	 */
	public UsuarioRolEntidad updateByDesactivar(UsuarioRolEntidadExt usuarioEntidad){
		UsuarioRolEntidad asc = new UsuarioRolEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			id = mapper.updateByDesactivar(usuarioEntidad);
			session.commit();
			if(id == 1) {
				asc.setError(false);
				asc.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			}else {
				asc.setError(false);
				asc.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
		}catch (Exception ex) {
			asc.setError(true);
			asc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asc.setMensajeTecnico(ex.getMessage());
			return asc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param usuarioExt
	 * @return
	 */
	public UsuarioRolEntidad getUsuarioRol(UsuarioExt usuarioExt){
		UsuarioRolEntidad asc = new UsuarioRolEntidad();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			asc = mapper.selectByUsuarioRol(usuarioExt);
			session.commit();
			if(asc != null) {
				asc.setError(false);
				return asc;
			}else {
				asc = new UsuarioRolEntidad();
				asc.setError(false);
				return asc;
			}
		}catch (Exception ex) {
			asc.setError(true);
			asc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asc.setMensajeTecnico(ex.getMessage());
			return asc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param UsuarioRolEntidad
	 * @return
	 */
	public boolean desactivarRolUsuario(UsuarioRolEntidad usuarioRolEntidad){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			id = mapper.desactivarRolUsuario(usuarioRolEntidad);
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
	public List<UsuarioRolEntidad> getUsuarioRolEntidadByAll(int limitIni, int limitEnd){
		List<UsuarioRolEntidad> asoc = new ArrayList<>();
		UsuarioRolEntidadExample dtoObject = new UsuarioRolEntidadExample();
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
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
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
	public List<UsuarioRolEntidad> getUsuarioRolEntidadById(BigDecimal id){
		List<UsuarioRolEntidad> asoc = new ArrayList<>();
		UsuarioRolEntidadExample dtoObject = new UsuarioRolEntidadExample();
		if(id!=null) {
			dtoObject.createCriteria().andCodUsuarioEntidadEqualTo(id);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
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
	 * Elaborado Por: Jose Viscaya
	 * 11 feb. 2019
	 * UsuarioRolEntidadService.java
	 * @param codPersona
	 * @return
	 */
	public List<UsuarioRolEntidadExt> getEntidadesRolPersona(BigDecimal codPersona){
		List<UsuarioRolEntidadExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			asoc =  mapper.selectByEntidadRolPersona(codPersona);
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
	 * @author Maria Alejandra C
	 * @param UsuarioRolEntidad 
	 * @return UsuarioRolEntidad
	 */
	public UsuarioRolEntidad asociarTodasEntidadAF(UsuarioRolEntidad usuarioRolEntidad){
		UsuarioRolEntidad bien = new UsuarioRolEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			id =  mapper.asociarRolATodasEntidades(usuarioRolEntidad);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	

	/**
	 * Servicio que llama procedimiento almacenado SP_ASOCIAR_ROL_USUARIO 
	 * @author Maria Alejandra C
	 * @param UsuarioRolEntidad 
	 * @return UsuarioRolEntidad
	 */
	public UsuarioRolEntidad asociarRolAUsuario(UsuarioRolEntidadExt usuarioRolEntidadExt){
		UsuarioRolEntidad bien = new UsuarioRolEntidad();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			mapper.asociarRolAUsuario(usuarioRolEntidadExt);
			if(usuarioRolEntidadExt!=null && usuarioRolEntidadExt.getResultProcedure()!=null && usuarioRolEntidadExt.getResultProcedure().intValue() >= 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				bien.setCodUsuarioRolEntidad(new BigDecimal(usuarioRolEntidadExt.getResultProcedure()));
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Servicio que consulta si el usuario ya tiene registrado el rol enviado para la entidad enviada
	 * @param usuarioRolEntidadExt
	 * @return UsuarioRolEntidad
	 */
	public UsuarioRolEntidad getByUsuarioRolEntidad(UsuarioRolEntidadExt usuarioRolEntidadExt){
		UsuarioRolEntidad asc = new UsuarioRolEntidad();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			asc = mapper.selectByUsuarioRolEntidad(usuarioRolEntidadExt);
			session.commit();
			if(asc != null) {
				asc.setError(false);
				return asc;
			}else {
				asc = new UsuarioRolEntidad();
				asc.setError(false);
				return asc;
			}
		}catch (Exception ex) {
			asc.setError(true);
			asc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asc.setMensajeTecnico(ex.getMessage());
			return asc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/** servicio que llama procedimiento en Base de datos que se encarga de
	 * inactivar usuario_entidad y usuario_rol_entidad cuando la persona No cuenta con
	 * una vinculación activa, tiene una vinculación con otra entidad y adicional, 
	 * el numero de desactivación del usuario es 0 días 
	 ***/
	public UsuarioRolEntidadExt eliminarUsuarioEntidad(UsuarioRolEntidadExt usuarioRolEntidadExt){
		UsuarioRolEntidadExt asc = new UsuarioRolEntidadExt();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolEntidadMapper  mapper = session.getMapper(UsuarioRolEntidadMapper.class);
			id = mapper.eliminarUsuarioEntidad(usuarioRolEntidadExt);
			session.commit();
			if(asc != null) {
				asc.setError(false);
				return asc;
			}else {
				asc = new UsuarioRolEntidadExt();
				asc.setError(false);
				return asc;
			}
		}catch (Exception ex) {
			asc.setError(true);
			asc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asc.setMensajeTecnico(ex.getMessage());
			return asc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
}
