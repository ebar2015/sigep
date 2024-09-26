/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.HistoricoSituacionAdminVinculacion;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.HistoricoSituacionAdminVinculacionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla HistoricoSituacionAdminVinculacionService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class HistoricoSituacionAdminVinculacionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3834147842429086434L;
	
	
	/**
	 * 
	 * @param HistoricoSituacionAdminVinculacion
	 * @return
	 */
	public HistoricoSituacionAdminVinculacion insertHistoricoSituacionAdminVinculacion (HistoricoSituacionAdminVinculacion historicoSituacionAdminVinculacion){
		HistoricoSituacionAdminVinculacion actp = new HistoricoSituacionAdminVinculacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoSituacionAdminVinculacionMapper  mapper = session.getMapper(HistoricoSituacionAdminVinculacionMapper.class);
			id =  mapper.insert(historicoSituacionAdminVinculacion);
			if(id > 0){
				actp.setError(false);
				actp.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				actp.setError(false);
				actp.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return actp;
		}catch (Exception ex) {
			actp.setError(true);
			actp.setMensaje(UtilsConstantes.MSG_EXEPCION);
			actp.setMensajeTecnico(ex.getMessage());
			return actp;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param HistoricoSituacionAdminVinculacion
	 * @return
	 */
	public HistoricoSituacionAdminVinculacion updateHistoricoSituacionAdminVinculacion(HistoricoSituacionAdminVinculacion historicoSituacionAdminVinculacion){
		HistoricoSituacionAdminVinculacion actp = new HistoricoSituacionAdminVinculacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoSituacionAdminVinculacionMapper  mapper = session.getMapper(HistoricoSituacionAdminVinculacionMapper.class);
			id = mapper.updateByPrimaryKey(historicoSituacionAdminVinculacion);
			session.commit();
			if(id > 0){
				actp.setError(false);
				actp.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				actp.setError(false);
				actp.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return actp;
		}catch (Exception ex) {
			actp.setError(true);
			actp.setMensaje(UtilsConstantes.MSG_EXEPCION);
			actp.setMensajeTecnico(ex.getMessage());
			return actp;		
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
	public HistoricoSituacionAdminVinculacion getHistoricoSituacionAdminVinculacionById(long id){
		HistoricoSituacionAdminVinculacion asoc = new HistoricoSituacionAdminVinculacion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoSituacionAdminVinculacionMapper  mapper = session.getMapper(HistoricoSituacionAdminVinculacionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new HistoricoSituacionAdminVinculacion();
			}
		}catch(Exception ex){
			asoc = new HistoricoSituacionAdminVinculacion();
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
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
	public List<HistoricoSituacionAdminVinculacion> getHistoricoSituacionAdminVinculacionFiltro(HistoricoSituacionAdminVinculacion historicoSituacionAdminVinculacion){
		List<HistoricoSituacionAdminVinculacion>  asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoSituacionAdminVinculacionMapper  mapper = session.getMapper(HistoricoSituacionAdminVinculacionMapper.class);
			asoc =  mapper.selectByFiltro(historicoSituacionAdminVinculacion);
			if(asoc != null){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			HistoricoSituacionAdminVinculacion en = new HistoricoSituacionAdminVinculacion();
			en.setError(true);
			en.setMensaje(UtilsConstantes.MSG_EXEPCION);
			en.setMensajeTecnico(ex.getMessage());
			asoc .add(en);
			return asoc;		
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
