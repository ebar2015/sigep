/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ActividadPrivada;
import co.gov.dafp.sigep2.bean.ext.ActividadPrivadaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ActividadPrivadaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ActividadPrivada
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class ActividadPrivadaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3122718762461168985L;
	
	/**
	 * 
	 * @param ActividadPrivada
	 * @return
	 */
	public ActividadPrivada insertActividadPrivada (ActividadPrivada actividadPrivada){
		ActividadPrivada actp = new ActividadPrivada();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ActividadPrivadaMapper  mapper = session.getMapper(ActividadPrivadaMapper.class);
			id =  mapper.insert(actividadPrivada);
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
	 * @param ActividadPrivada
	 * @return
	 */
	public ActividadPrivada updateActividadPrivada(ActividadPrivada actividadPrivada){
		ActividadPrivada actp = new ActividadPrivada();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ActividadPrivadaMapper  mapper = session.getMapper(ActividadPrivadaMapper.class);
			id = mapper.updateByPrimaryKey(actividadPrivada);
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
	 * @return
	 */
	public List<ActividadPrivadaExt> getActividadPrivadaCodDeclaracion(ActividadPrivadaExt   actividadPrivada){
		List<ActividadPrivadaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ActividadPrivadaMapper  mapper = session.getMapper(ActividadPrivadaMapper.class);
			asoc =  mapper.selectActividadPorDeclaracion(actividadPrivada);
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
	public ActividadPrivada getActividadPrivadaById(long id){
		ActividadPrivada asoc = new ActividadPrivada();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ActividadPrivadaMapper  mapper = session.getMapper(ActividadPrivadaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new ActividadPrivada();
			}
		}catch(Exception ex){
			asoc = new ActividadPrivada();
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

}
