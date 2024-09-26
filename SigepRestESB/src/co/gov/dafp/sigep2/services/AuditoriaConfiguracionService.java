/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.AuditoriaConfiguracion;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.AuditoriaConfiguracionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla AuditoriaConfiguracion
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class AuditoriaConfiguracionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8346652761605903507L;
	
	
	/**
	 * 
	 * @param Auditoria
	 * @return
	 */
	public AuditoriaConfiguracion insertAuditoriaConfiguracion (AuditoriaConfiguracion auditoria){
		AuditoriaConfiguracion uuconf = new AuditoriaConfiguracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaConfiguracionMapper  mapper = session.getMapper(AuditoriaConfiguracionMapper.class);
			id =  mapper.insert(auditoria);
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
	 * @param AuditoriaConfiguracion
	 * @return
	 */
	public AuditoriaConfiguracion updateAuditoriaConfiguracion(AuditoriaConfiguracion auditoria){
		AuditoriaConfiguracion uuconf = new AuditoriaConfiguracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaConfiguracionMapper  mapper = session.getMapper(AuditoriaConfiguracionMapper.class);
			id = mapper.updateByPrimaryKey(auditoria);
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
	public List<AuditoriaConfiguracion> getAuditoriaConfiguracionByAll(AuditoriaConfiguracion auditoriaConfiguracion){
		List<AuditoriaConfiguracion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaConfiguracionMapper  mapper = session.getMapper(AuditoriaConfiguracionMapper.class);
			asoc =  mapper.selectall(auditoriaConfiguracion);
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
	 * Jose Viscaya 11 ene. 2019
	 * @param auditoriaConfiguracion
	 * @return
	 */
	public List<AuditoriaConfiguracion> getAuditoriaConfiguracionByFiltro(AuditoriaConfiguracion auditoriaConfiguracion){
		List<AuditoriaConfiguracion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaConfiguracionMapper  mapper = session.getMapper(AuditoriaConfiguracionMapper.class);
			asoc =  mapper.selectByFiltro(auditoriaConfiguracion);
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
	 * @param auditoriaConfiguracion
	 * @return
	 */
	public List<AuditoriaConfiguracion> getAuditoriaConfiguracionByName(AuditoriaConfiguracion auditoriaConfiguracion){
		List<AuditoriaConfiguracion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaConfiguracionMapper  mapper = session.getMapper(AuditoriaConfiguracionMapper.class);
			asoc =  mapper.selectLikeName(auditoriaConfiguracion);
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
	public AuditoriaConfiguracion getAuditoriaConfiguracionById(int id){
		AuditoriaConfiguracion asoc = new AuditoriaConfiguracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaConfiguracionMapper  mapper = session.getMapper(AuditoriaConfiguracionMapper.class);
			asoc =  mapper.selectByPrimaryKey((short) id);
			if(asoc != null){
				return asoc;
			}else{
				return new AuditoriaConfiguracion();
			}
		}catch(Exception ex){
			return new AuditoriaConfiguracion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
