/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmCrearUsuarios;
import co.gov.dafp.sigep2.bean.CmCrearUsuariosExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmCrearUsuariosMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmCrearUsuarios
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CmCrearUsuariosService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4771234967994227161L;
	
	/**
	 * 
	 * @param CmCrearUsuarios
	 * @return
	 */
	public CmCrearUsuarios insertCmCrearUsuarios (CmCrearUsuarios cmCrearUsuarios){
		CmCrearUsuarios cue = new CmCrearUsuarios();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearUsuariosMapper  mapper = session.getMapper(CmCrearUsuariosMapper.class);
			id =  mapper.insert(cmCrearUsuarios);
			if(id > 0){
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
				return cue;
			}else{
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
				return cue;
			}
		}catch (Exception ex) {
			cue.setError(true);
			cue.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cue.setMensajeTecnico(ex.getMessage());
			return cue;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param CmCrearUsuarios
	 * @return
	 */
	public CmCrearUsuarios updateCmCrearUsuarios(CmCrearUsuarios CmCrearUsuarios){
		CmCrearUsuarios cue = new CmCrearUsuarios();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearUsuariosMapper  mapper = session.getMapper(CmCrearUsuariosMapper.class);
			id = mapper.updateByPrimaryKey(CmCrearUsuarios);
			if(id > 0){
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return cue;
		}catch (Exception ex) {
			cue.setError(true);
			cue.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cue.setMensajeTecnico(ex.getMessage());
			return cue;	
		} finally {
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
	public CmCrearUsuarios getCmCrearUsuariosById(long id){
		CmCrearUsuarios asoc = new CmCrearUsuarios();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearUsuariosMapper  mapper = session.getMapper(CmCrearUsuariosMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmCrearUsuarios();
			}
		}catch(Exception ex){
		
			return new CmCrearUsuarios();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param codProcesoCargaMasiva
	 * @return  List<CmCrearUsuarios>
	 */
	public List<CmCrearUsuarios> getCmCrearUsuariosCodProceso(Short codProcesoCargaMasiva){
		List<CmCrearUsuarios> asoc = new ArrayList<>();
		CmCrearUsuariosExample dtoObject = new CmCrearUsuariosExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(codProcesoCargaMasiva);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearUsuariosMapper  mapper = session.getMapper(CmCrearUsuariosMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(asoc!=null){
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
