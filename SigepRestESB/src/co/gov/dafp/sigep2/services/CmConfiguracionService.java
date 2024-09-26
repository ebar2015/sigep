/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmConfiguracion;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmConfiguracionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmConfiguracion
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CmConfiguracionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5486431215645806631L;
	
	/**
	 * 
	 * @param CmConfiguracion
	 * @return
	 */
	public CmConfiguracion insertCmConfiguracion (CmConfiguracion cmConfiguracion){
		CmConfiguracion cue = new CmConfiguracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmConfiguracionMapper  mapper = session.getMapper(CmConfiguracionMapper.class);
			id =  mapper.insert(cmConfiguracion);
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
	 * @param CmConfiguracion
	 * @return
	 */
	public CmConfiguracion updateCmConfiguracion(CmConfiguracion cmConfiguracion){
		CmConfiguracion cue = new CmConfiguracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmConfiguracionMapper  mapper = session.getMapper(CmConfiguracionMapper.class);
			id = mapper.updateByPrimaryKey(cmConfiguracion);
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
	 * @return
	 */
	public List<CmConfiguracion> getCmConfiguracionFiltro(CmConfiguracion cmConfiguracion){
		List<CmConfiguracion> asoc = new ArrayList<>();
		SqlSession session = null;
		String msg = "CodProcesoCargue es obligatorio";
		if(cmConfiguracion.getCodProcesoCargue() == null) {
			CmConfiguracion err = new CmConfiguracion();
			err.setError(true);
			err.setMensaje(msg);
			asoc.add(err);
			return asoc;
		}
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmConfiguracionMapper  mapper = session.getMapper(CmConfiguracionMapper.class);
			asoc =  mapper.selectByfiltro(cmConfiguracion);
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
	public CmConfiguracion getCmConfiguracionById(long id){
		CmConfiguracion asoc = new CmConfiguracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmConfiguracionMapper  mapper = session.getMapper(CmConfiguracionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmConfiguracion();
			}
		}catch(Exception ex){
		
			return new CmConfiguracion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
