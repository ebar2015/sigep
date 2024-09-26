/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ArchivoCargueLog;
import co.gov.dafp.sigep2.bean.ArchivoCargueLogExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ArchivoCargueLogMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ArchivoCargueLog
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class ArchivoCargueLogService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8308851926494960528L;
	
	/**
	 * 
	 * @param ArchivoCargueLog
	 * @return
	 */
	public ArchivoCargueLog insertArchivoCargueLog (ArchivoCargueLog archivoCargueLog){
		ArchivoCargueLog arch =  new ArchivoCargueLog();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ArchivoCargueLogMapper  mapper = session.getMapper(ArchivoCargueLogMapper.class);
			id =  mapper.insert(archivoCargueLog);
			if(id > 0){
				arch.setError(false);
				arch.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				arch.setError(false);
				arch.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return arch;
		}catch (Exception ex) {
			arch.setError(true);
			arch.setMensaje(UtilsConstantes.MSG_EXEPCION);
			arch.setMensajeTecnico(ex.getMessage());
			return arch;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param ArchivoCargueLog
	 * @return
	 */
	public ArchivoCargueLog updateArchivoCargueLog(ArchivoCargueLog archivoCargueLog){
		ArchivoCargueLog arch =  new ArchivoCargueLog();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ArchivoCargueLogMapper  mapper = session.getMapper(ArchivoCargueLogMapper.class);
			id = mapper.updateByPrimaryKey(archivoCargueLog);
			if(id > 0){
				arch.setError(false);
				arch.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				arch.setError(false);
				arch.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return arch;
		}catch (Exception ex) {
			arch.setError(true);
			arch.setMensaje(UtilsConstantes.MSG_EXEPCION);
			arch.setMensajeTecnico(ex.getMessage());
			return arch;	
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
	public List<ArchivoCargueLog> getArchivoCargueLogByAll(){
		List<ArchivoCargueLog> asoc = new ArrayList<>();
		ArchivoCargueLogExample dtoObject = new ArchivoCargueLogExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ArchivoCargueLogMapper  mapper = session.getMapper(ArchivoCargueLogMapper.class);
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
	public List<ArchivoCargueLog> getArchivoCargueLogById(BigDecimal id){
		List<ArchivoCargueLog> asoc = new ArrayList<>();
		ArchivoCargueLogExample dtoObject = new ArchivoCargueLogExample();
		if(id!=null) {
			dtoObject.createCriteria().andCodArchivoCargueEqualTo(id);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ArchivoCargueLogMapper  mapper = session.getMapper(ArchivoCargueLogMapper.class);
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

}
