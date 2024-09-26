/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.UsuarioSessionExample;
import co.gov.dafp.sigep2.bean.UsuarioSession;
import co.gov.dafp.sigep2.bean.UsuarioSessionExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.UsuarioSessionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya
 * Jan 14, 2019
 */
public class UsuarioSessionService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3349609966974236150L;
	
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya Jan 14, 2019
	 * @param usuarioSession
	 * @return
	 */
	public UsuarioSession insertUsuarioSession (UsuarioSession usuarioSession){
		UsuarioSession acre = new UsuarioSession();
		SqlSession session = null;
		usuarioSession.setFechaInicioSession(new Date());
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioSessionMapper  mapper = session.getMapper(UsuarioSessionMapper.class);
			id =  mapper.insert(usuarioSession);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya Jan 14, 2019
	 * @param usuarioSession
	 * @return
	 */
	public UsuarioSession updateUsuarioSession(UsuarioSession usuarioSession){
		UsuarioSession acre = new UsuarioSession();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioSessionMapper  mapper = session.getMapper(UsuarioSessionMapper.class);
			id = mapper.updateByPrimaryKey(usuarioSession);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya Jan 14, 2019
	 * @param usuarioSession
	 * @return
	 */
	public List<UsuarioSession> getUsuarioSession(UsuarioSession usuarioSession){
		List<UsuarioSession> asoc = new ArrayList<>();
		SqlSession session = null;
		int toC = 0;
		 if(usuarioSession.getCodUsuario() == null)
			 toC++;
		 if(usuarioSession.getNombreFuncion() == null)
			 toC++;
		 if(usuarioSession.getFechaInicioSession() == null)
			 toC++; 
		 if(usuarioSession.getFechaFinSession() == null)
			 toC++;  
		 if(usuarioSession.getAudFechaActualizacion() == null)
			 toC++; 
		 if(usuarioSession.getAudAccion() == null)
			 toC++; 
		 if(toC > 5 ) {
			 return new ArrayList<>();
		 }
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioSessionMapper  mapper = session.getMapper(UsuarioSessionMapper.class);
			asoc =  mapper.selectByFiltro(usuarioSession);
			if(!asoc.isEmpty()){
				if(asoc.size() > UtilsConstantes.MAX_REG) {
					System.out.println(this.getClass().getName());
					asoc = null;
					return new ArrayList<>();
				}
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
	 * Creado Por: Jose viscaya
	 * @return
	 * 5 ago. 2019 8:18:46
	 * UsuarioSessionService.java
	 */
	public long getUsuarioSessionTotal(){
		UsuarioSessionExample dtoObject = new UsuarioSessionExample();
		dtoObject.createCriteria().andFlgActivoEqualTo((short) 1);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioSessionMapper  mapper = session.getMapper(UsuarioSessionMapper.class);
			long t =  mapper.countByExample(dtoObject);
			return t;
		}catch(Exception ex){
			return 0;
		}finally{
			if (session != null) {
				session.close();
			}
		}
		
	}
	
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya Jan 14, 2019
	 * @param id
	 * @return
	 */
	public UsuarioSession getUsuarioSessionById(BigDecimal id){
		UsuarioSession asoc = new UsuarioSession();
		UsuarioSessionExample dtoObject = new UsuarioSessionExample();
		dtoObject.createCriteria().andCodUsuarioSessionEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioSessionMapper  mapper = session.getMapper(UsuarioSessionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new UsuarioSession();
			}
		}catch(Exception ex){
		
			return new UsuarioSession();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
