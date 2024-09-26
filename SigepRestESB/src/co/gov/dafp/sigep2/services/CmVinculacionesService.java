/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmVinculaciones;
import co.gov.dafp.sigep2.bean.CmVinculacionesExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmVinculacionesMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya
* @version 1.0
* @Class Que se encarga de procesar la comunicacion con la base de datos
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 10/09/2018 3:05:08 p.m.
*/
public class CmVinculacionesService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3364846368666168493L;
	
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 10/09/2018 3:28:51 p.m.
	* @param cmVinculaciones
	* @return
	*
	 */
	public CmVinculaciones insertCmVinculaciones (CmVinculaciones cmVinculaciones){
		CmVinculaciones cue = new CmVinculaciones();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmVinculacionesMapper  mapper = session.getMapper(CmVinculacionesMapper.class);
			id =  mapper.insert(cmVinculaciones);
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
	* @author: Jose Viscaya 
	* @fecha 10/09/2018 3:28:56 p.m.
	* @param cmVinculaciones
	* @return
	*
	 */
	public CmVinculaciones updateCmVinculaciones(CmVinculaciones cmVinculaciones){
		CmVinculaciones cue = new CmVinculaciones();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmVinculacionesMapper  mapper = session.getMapper(CmVinculacionesMapper.class);
			id = mapper.updateByPrimaryKey(cmVinculaciones);
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
	* @author: Jose Viscaya 
	* @fecha 10/09/2018 3:29:04 p.m.
	* @param cmVinculaciones
	* @return
	*
	 */
	public List<CmVinculaciones> getCmVinculacionesByProceso(CmVinculaciones cmVinculaciones){
		List<CmVinculaciones> asoc = new ArrayList<>();
		CmVinculacionesExample dtoObject = new CmVinculacionesExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(cmVinculaciones.getCodProcesoCargaMasiva());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmVinculacionesMapper  mapper = session.getMapper(CmVinculacionesMapper.class);
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
	* @author: Jose Viscaya 
	* @fecha 10/09/2018 3:29:15 p.m.
	* @param id
	* @return
	*
	 */
	public CmVinculaciones getCmVinculacionesById(long id){
		CmVinculaciones asoc = new CmVinculaciones();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmVinculacionesMapper  mapper = session.getMapper(CmVinculacionesMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmVinculaciones();
			}
		}catch(Exception ex){
		
			return new CmVinculaciones();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
