/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.DatoContacto;
import co.gov.dafp.sigep2.bean.DatoContactoExample;
import co.gov.dafp.sigep2.bean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DatoContactoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla DatoContacto
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class DatoContactoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1446674550985950343L;
	
	/**
	 * 
	 * @param DatoContacto
	 * @return
	 */
	public DatoContacto insertDatoContacto (DatoContacto datoContacto){
		DatoContacto dat =  new DatoContacto();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoMapper  mapper = session.getMapper(DatoContactoMapper.class);
			id =  mapper.insert(datoContacto);
			if(id > 0){
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				dat.setError(true);
				dat.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return dat;
		}catch (Exception ex) {
			dat.setError(true);
			dat.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dat.setMensajeTecnico(ex.getMessage());
			return dat;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param DatoContacto
	 * @return
	 */
	public DatoContacto updateDatoContacto(DatoContacto datoContacto){
		DatoContacto dat =  new DatoContacto();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoMapper  mapper = session.getMapper(DatoContactoMapper.class);
			id = mapper.updateByPrimaryKey(datoContacto);
			session.commit();
			if(id > 0){
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return dat;
		}catch (Exception ex) {
			dat.setError(true);
			dat.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dat.setMensajeTecnico(ex.getMessage());
			return dat;
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
	public List<DatoContacto> getDatoContactoByAll(int limitIni, int limitEnd){
		List<DatoContacto> asoc = new ArrayList<>();
		DatoContactoExample dtoObject = new DatoContactoExample();
		if(limitEnd > 1 ) {
			dtoObject.setLimitInit(limitIni);
			dtoObject.setLimitEnd(limitEnd);
		}else {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoMapper  mapper = session.getMapper(DatoContactoMapper.class);
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
	public DatoContacto datoContactoById(long id){
		DatoContacto asoc = new DatoContacto();
		DatoContactoExample dtoObject = new DatoContactoExample();
		dtoObject.createCriteria().andCodDatosContactoEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoMapper  mapper = session.getMapper(DatoContactoMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new DatoContacto();
			}
		}catch(Exception ex){
		
			return new DatoContacto();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public DatoContactoExt getDatoContactoPorIdPersona(long id){
		DatoContactoExt asoc = new DatoContactoExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoMapper  mapper = session.getMapper(DatoContactoMapper.class);
			asoc =  mapper.selectByDatoContacto(id);
			if(asoc != null){
				return asoc;
			}else{
				return new DatoContactoExt();
			}
		}catch(Exception ex){
			return new DatoContactoExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 2:45:10 p.m. 2018
	 * @param datoContacto
	 * @return
	 */
	public DatoContacto updateDatoContactoSelective(DatoContacto datoContacto){
		DatoContacto dat =  new DatoContacto();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoMapper  mapper = session.getMapper(DatoContactoMapper.class);
			id = mapper.updateByPrimaryKeySelective(datoContacto);
			session.commit();
			if(id > 0){
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return dat;
		}catch (Exception ex) {
			dat.setError(true);
			dat.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dat.setMensajeTecnico(ex.getMessage());
			return dat;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
