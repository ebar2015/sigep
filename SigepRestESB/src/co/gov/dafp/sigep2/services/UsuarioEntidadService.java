package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.UsuarioEntidad;
import co.gov.dafp.sigep2.bean.UsuarioEntidadExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.UsuarioEntidadMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla UsuarioEntidadService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class UsuarioEntidadService implements Serializable {
	private static final long serialVersionUID = -8071546305969638196L;
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:57:28 p. m.
	 * @param usuarioEntidad
	 * @return
	 */
	public int insertUsuarioEntidad (UsuarioEntidad usuarioEntidad){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper  mapper = session.getMapper(UsuarioEntidadMapper.class);
			id =  mapper.insert(usuarioEntidad);
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
	 * @param UsuarioEntidad
	 * @return
	 */
	public boolean updateUsuarioEntidad(UsuarioEntidad usuarioEntidad){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper  mapper = session.getMapper(UsuarioEntidadMapper.class);
			id = mapper.updateByPrimaryKey(usuarioEntidad);
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
	public List<UsuarioEntidad> getUsuarioEntidadPorAll(int limitIni, int limitEnd){
		List<UsuarioEntidad> asoc = new ArrayList<>();
		UsuarioEntidadExample dtoObject = new UsuarioEntidadExample();
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
			UsuarioEntidadMapper  mapper = session.getMapper(UsuarioEntidadMapper.class);
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
	 * @param usuarioEntidad
	 * @return
	 */
	public UsuarioEntidad getUsuarioEntidadPorcodUsCodent(UsuarioEntidad usuarioEntidad){
		List<UsuarioEntidad> asoc = new ArrayList<>();
		UsuarioEntidadExample dtoObject = new UsuarioEntidadExample();
		if(usuarioEntidad!=null && usuarioEntidad.getCodUsuario()!=null) {
			dtoObject.createCriteria().andCodUsuarioEqualTo(usuarioEntidad.getCodUsuario()).
			andCodEntidadEqualTo(usuarioEntidad.getCodEntidad()).
			andFlgActivoEqualTo(usuarioEntidad.getFlgActivo());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper  mapper = session.getMapper(UsuarioEntidadMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new UsuarioEntidad();
			}
		}catch(Exception ex){
			return new UsuarioEntidad();
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
	public List<UsuarioEntidad> usuarioEntidadPorUsuarioId(BigDecimal id){
		List<UsuarioEntidad> asoc = new ArrayList<>();
		UsuarioEntidadExample dtoObject = new UsuarioEntidadExample();
		if(id!=null && id.intValue() > 0) {
			dtoObject.createCriteria().andCodUsuarioEqualTo(id);
		}
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper  mapper = session.getMapper(UsuarioEntidadMapper.class);
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
	 * @param uarioEntidad
	 * @return
	 */
	public BigDecimal usuarioEntidadByIdenditadId(UsuarioEntidad uarioEntidad){
		List<UsuarioEntidad> asoc = new ArrayList<>();
		UsuarioEntidadExample dtoObject = new UsuarioEntidadExample();
		if(uarioEntidad!=null && uarioEntidad.getAudCodUsuario()!=null) {
			dtoObject.createCriteria().andCodUsuarioEntidadEqualTo(uarioEntidad.getAudCodUsuario());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper  mapper = session.getMapper(UsuarioEntidadMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0).getCodEntidad();
			}else{
				return new BigDecimal(0);
			}
		}catch(Exception ex){
			return new BigDecimal(0);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	public UsuarioEntidad updateUsuarioEntidadSelective(UsuarioEntidad usuarioEntidad) {
		UsuarioEntidad ent = new UsuarioEntidad();
		UsuarioEntidadExample dtoObject = new UsuarioEntidadExample();
		if(usuarioEntidad!=null && usuarioEntidad.getCodUsuarioEntidad()!=null) {
			dtoObject.createCriteria().andCodUsuarioEntidadEqualTo(usuarioEntidad.getCodUsuarioEntidad());
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper mapper = session.getMapper(UsuarioEntidadMapper.class);
			id = mapper.updateByPrimaryKeySelective(usuarioEntidad);
			
			session.commit();
			if (id > 0) {
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			} else {
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return ent;
		} catch (Exception ex) {
			ent.setError(true);
			ent.setMensaje(UtilsConstantes.MSG_EXEPCION);
			ent.setMensajeTecnico(ex.getMessage());
			return ent;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public UsuarioEntidad insertUsuarioEntidadSelective(UsuarioEntidad entidad) {
		SqlSession session = null;
		UsuarioEntidad ent = new UsuarioEntidad();
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper mapper = session.getMapper(UsuarioEntidadMapper.class);
			id = mapper.insertSelective(entidad);
			if (id > 0) {
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			} else {
				ent.setError(true);
				ent.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
				session.rollback();
			}
			return ent;
		} catch (Exception ex) {
			ent.setError(true);
			ent.setMensaje(UtilsConstantes.MSG_EXEPCION);
			ent.setMensajeTecnico(ex.getMessage());
			return ent;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author Desarolladora Junior
	 * @param 
	 * @return
	 */
	public UsuarioEntidad updatePorUsuario(UsuarioEntidad usuarioEntidad){
		UsuarioEntidad param = new UsuarioEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper  mapper = session.getMapper(UsuarioEntidadMapper.class);
			id = mapper.updatePorUsuario(usuarioEntidad);
			if(id > 0){
				param.setError(false);
				param.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				param.setError(true);
				param.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return param;
		}catch (Exception ex) {
			param.setError(true);
			param.setMensaje(UtilsConstantes.MSG_EXEPCION);
			param.setMensajeTecnico(ex.getMessage());
			return param;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metodo que obtiene informacion de usuarioEntidad.
	 * @param usuarioEntidad. Debe de recibir el código del usuario y la entidad
	 * @return Usuario
	 */
	public UsuarioEntidad getUsuarioEntidad(UsuarioEntidad usuarioEntidad) {
		UsuarioEntidad asoc = new UsuarioEntidad();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioEntidadMapper mapper = session.getMapper(UsuarioEntidadMapper.class);
			asoc = mapper.selectUsuarioEntidad(usuarioEntidad);
			if (asoc == null) {
				asoc = new UsuarioEntidad();
				asoc.setError(false);
				asoc.setMensaje(UtilsConstantes.MSG_SIN_INFORMACION_USUARIO);
			}else{
				asoc.setError(false);
			}
			return asoc;
		} catch (Exception ex) {
			asoc = new UsuarioEntidad();
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return asoc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
