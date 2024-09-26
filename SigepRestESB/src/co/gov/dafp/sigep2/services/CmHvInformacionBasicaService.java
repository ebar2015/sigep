/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmHvInformacionBasica;
import co.gov.dafp.sigep2.bean.CmHvInformacionBasicaExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmHvInformacionBasicaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmHvInformacionBasica
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CmHvInformacionBasicaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3623764084958022315L;
	
	/**
	 * 
	 * @param CmHvInformacionBasica
	 * @return
	 */
	public CmHvInformacionBasica insertCmHvInformacionBasica (CmHvInformacionBasica cmHvInformacionBasica){
		CmHvInformacionBasica cue = new CmHvInformacionBasica();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvInformacionBasicaMapper  mapper = session.getMapper(CmHvInformacionBasicaMapper.class);
			id =  mapper.insert(cmHvInformacionBasica);
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
	 * @param CmHvInformacionBasica
	 * @return
	 */
	public CmHvInformacionBasica updateCmHvInformacionBasica(CmHvInformacionBasica cmHvInformacionBasica){
		CmHvInformacionBasica cue = new CmHvInformacionBasica();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvInformacionBasicaMapper  mapper = session.getMapper(CmHvInformacionBasicaMapper.class);
			id = mapper.updateByPrimaryKey(cmHvInformacionBasica);
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
	public List<CmHvInformacionBasica> getCmHvInformacionBasicaByProceso(CmHvInformacionBasica cmHvInformacionBasica){
		List<CmHvInformacionBasica> asoc = new ArrayList<>();
		CmHvInformacionBasicaExample dtoObject = new CmHvInformacionBasicaExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(cmHvInformacionBasica.getCodProcesoCargaMasiva());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvInformacionBasicaMapper  mapper = session.getMapper(CmHvInformacionBasicaMapper.class);
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
	public CmHvInformacionBasica getCmHvInformacionBasicaById(long id){
		CmHvInformacionBasica asoc = new CmHvInformacionBasica();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvInformacionBasicaMapper  mapper = session.getMapper(CmHvInformacionBasicaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmHvInformacionBasica();
			}
		}catch(Exception ex){
		
			return new CmHvInformacionBasica();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
