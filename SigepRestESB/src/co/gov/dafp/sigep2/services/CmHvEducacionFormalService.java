/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmHvEducacionFormal;
import co.gov.dafp.sigep2.bean.CmHvEducacionFormalExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmHvEducacionFormalMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmHvEducacionFormal
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CmHvEducacionFormalService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -539893254777415754L;
	

	/**
	 * 
	 * @param CmHvEducacionFormal
	 * @return
	 */
	public CmHvEducacionFormal insertCmHvEducacionFormal (CmHvEducacionFormal cmHvEducacionFormal){
		CmHvEducacionFormal cue = new CmHvEducacionFormal();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionFormalMapper  mapper = session.getMapper(CmHvEducacionFormalMapper.class);
			id =  mapper.insert(cmHvEducacionFormal);
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
	 * @param CmHvEducacionFormal
	 * @return
	 */
	public CmHvEducacionFormal updateCmHvEducacionFormal(CmHvEducacionFormal cmHvEducacionFormal){
		CmHvEducacionFormal cue = new CmHvEducacionFormal();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionFormalMapper  mapper = session.getMapper(CmHvEducacionFormalMapper.class);
			id = mapper.updateByPrimaryKey(cmHvEducacionFormal);
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
	public CmHvEducacionFormal getCmHvEducacionFormalById(long id){
		CmHvEducacionFormal asoc = new CmHvEducacionFormal();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionFormalMapper  mapper = session.getMapper(CmHvEducacionFormalMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmHvEducacionFormal();
			}
		}catch(Exception ex){
		
			return new CmHvEducacionFormal();
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
	public List<CmHvEducacionFormal> getCmHvEducacionFormalByProceso(CmHvEducacionFormal cmHvEducacionFormal){
		List<CmHvEducacionFormal> asoc = new ArrayList<>();
		CmHvEducacionFormalExample dtoObject = new CmHvEducacionFormalExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(cmHvEducacionFormal.getCodProcesoCargaMasiva());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionFormalMapper  mapper = session.getMapper(CmHvEducacionFormalMapper.class);
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
