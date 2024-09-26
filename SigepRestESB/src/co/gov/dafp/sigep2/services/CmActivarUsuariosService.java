/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmActivarUsuarios;
import co.gov.dafp.sigep2.bean.CmActivarUsuariosExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmActivarUsuariosMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmActivarUsuarios
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CmActivarUsuariosService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6150886499246895613L;
	
	/**
	 * 
	 * @param Auditoria
	 * @return
	 */
	public CmActivarUsuarios insertCmActivarUsuarios (CmActivarUsuarios cmActivarUsuarios){
		CmActivarUsuarios uuconf = new CmActivarUsuarios();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmActivarUsuariosMapper  mapper = session.getMapper(CmActivarUsuariosMapper.class);
			id =  mapper.insert(cmActivarUsuarios);
			if(id > 0){
				uuconf.setError(false);
				uuconf.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				uuconf.setError(false);
				uuconf.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return uuconf;
		}catch (Exception ex) {
			uuconf.setError(true);
			uuconf.setMensaje(UtilsConstantes.MSG_EXEPCION);
			uuconf.setMensajeTecnico(ex.getMessage());
			return uuconf;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param CmActivarUsuarios
	 * @return
	 */
	public CmActivarUsuarios updateCmActivarUsuarios(CmActivarUsuarios cmActivarUsuarios){
		CmActivarUsuarios uuconf = new CmActivarUsuarios();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmActivarUsuariosMapper  mapper = session.getMapper(CmActivarUsuariosMapper.class);
			id = mapper.updateByPrimaryKey(cmActivarUsuarios);
			session.commit();
			if(id > 0){
				uuconf.setError(false);
				uuconf.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				uuconf.setError(false);
				uuconf.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return uuconf;
		}catch (Exception ex) {
			uuconf.setError(true);
			uuconf.setMensaje(UtilsConstantes.MSG_EXEPCION);
			uuconf.setMensajeTecnico(ex.getMessage());
			return uuconf;	
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
	public List<CmActivarUsuarios> getCmActivarUsuariosByAll(){
		List<CmActivarUsuarios> asoc = new ArrayList<>();
		CmActivarUsuariosExample dtoObject = new CmActivarUsuariosExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmActivarUsuariosMapper  mapper = session.getMapper(CmActivarUsuariosMapper.class);
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
	public List<CmActivarUsuarios> getCmActivarUsuariosCodProceso(Short codProcesoCargaMasiva){
		List<CmActivarUsuarios> asoc = new ArrayList<>();
		CmActivarUsuariosExample dtoObject = new CmActivarUsuariosExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(codProcesoCargaMasiva);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmActivarUsuariosMapper  mapper = session.getMapper(CmActivarUsuariosMapper.class);
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
	public CmActivarUsuarios getCmActivarUsuariosById(long id){
		CmActivarUsuarios asoc = new CmActivarUsuarios();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmActivarUsuariosMapper  mapper = session.getMapper(CmActivarUsuariosMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new CmActivarUsuarios();
			}
		}catch(Exception ex){
			return new CmActivarUsuarios();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
