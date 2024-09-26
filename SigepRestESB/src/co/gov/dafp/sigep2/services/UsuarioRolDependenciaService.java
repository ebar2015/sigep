/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.UsuarioRolDependencia;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.UsuarioRolDependenciaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya
 * 9 ene. 2019
 */
public class UsuarioRolDependenciaService implements Serializable {

	private static final long serialVersionUID = 7882018741991532969L;
	
	/**
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param usuarioRolDependencia
	 * @return
	 */
	public UsuarioRolDependencia insertUsuarioRolDependencia (UsuarioRolDependencia usuarioRolDependencia){
		UsuarioRolDependencia acre = new UsuarioRolDependencia();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolDependenciaMapper  mapper = session.getMapper(UsuarioRolDependenciaMapper.class);
			id =  mapper.insert(usuarioRolDependencia);
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
	 * Jose Viscaya 9 ene. 2019
	 * @param usuarioRolDependencia
	 * @return
	 */
	public UsuarioRolDependencia updateUsuarioRolDependencia(UsuarioRolDependencia usuarioRolDependencia){
		UsuarioRolDependencia acre = new UsuarioRolDependencia();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolDependenciaMapper  mapper = session.getMapper(UsuarioRolDependenciaMapper.class);
			id = mapper.updateByPrimaryKey(usuarioRolDependencia);
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
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param usuarioRolDependencia
	 * @return
	 */
	public List<UsuarioRolDependencia> getUsuarioRolDependenciaFiltro(UsuarioRolDependencia usuarioRolDependencia){
		List<UsuarioRolDependencia> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolDependenciaMapper  mapper = session.getMapper(UsuarioRolDependenciaMapper.class);
			asoc =  mapper.selectByFiltro(usuarioRolDependencia);
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
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param id
	 * @return
	 */
	public UsuarioRolDependencia getUsuarioRolDependenciaById(BigDecimal id){
		UsuarioRolDependencia asoc = new UsuarioRolDependencia();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioRolDependenciaMapper  mapper = session.getMapper(UsuarioRolDependenciaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new UsuarioRolDependencia();
			}
		}catch(Exception ex){
		
			return new UsuarioRolDependencia();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
