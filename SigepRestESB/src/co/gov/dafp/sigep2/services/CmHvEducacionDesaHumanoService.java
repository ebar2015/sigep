/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmHvEducacionDesaHumano;
import co.gov.dafp.sigep2.bean.CmHvEducacionDesaHumanoExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmHvEducacionDesaHumanoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmCrearUsuarios
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CmHvEducacionDesaHumanoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1704309789247213487L;
	
	/**
	 * 
	 * @param CmHvEducacionDesaHumano
	 * @return
	 */
	public CmHvEducacionDesaHumano insertCmHvEducacionDesaHumano (CmHvEducacionDesaHumano cmHvEducacionDesaHumano){
		CmHvEducacionDesaHumano cue = new CmHvEducacionDesaHumano();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionDesaHumanoMapper  mapper = session.getMapper(CmHvEducacionDesaHumanoMapper.class);
			id =  mapper.insert(cmHvEducacionDesaHumano);
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
	 * @param CmHvEducacionDesaHumano
	 * @return
	 */
	public CmHvEducacionDesaHumano updateCmHvEducacionDesaHumano(CmHvEducacionDesaHumano cmHvEducacionDesaHumano){
		CmHvEducacionDesaHumano cue = new CmHvEducacionDesaHumano();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionDesaHumanoMapper  mapper = session.getMapper(CmHvEducacionDesaHumanoMapper.class);
			id = mapper.updateByPrimaryKey(cmHvEducacionDesaHumano);
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
	public CmHvEducacionDesaHumano getCmHvEducacionDesaHumanoById(long id){
		CmHvEducacionDesaHumano asoc = new CmHvEducacionDesaHumano();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionDesaHumanoMapper  mapper = session.getMapper(CmHvEducacionDesaHumanoMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmHvEducacionDesaHumano();
			}
		}catch(Exception ex){
		
			return new CmHvEducacionDesaHumano();
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
	public List<CmHvEducacionDesaHumano> getCmHvEducacionDesaHumanoByProceso(CmHvEducacionDesaHumano cmHvEducacionDesaHumano){
		List<CmHvEducacionDesaHumano> asoc = new ArrayList<>();
		CmHvEducacionDesaHumanoExample dtoObject = new CmHvEducacionDesaHumanoExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(cmHvEducacionDesaHumano.getCodProcesoCargaMasiva());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionDesaHumanoMapper  mapper = session.getMapper(CmHvEducacionDesaHumanoMapper.class);
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
