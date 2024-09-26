/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.AsignacionSalarial;
import co.gov.dafp.sigep2.bean.AsignacionSalarialExample;
import co.gov.dafp.sigep2.bean.ext.AsignacionSalarialExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.AsignacionSalarialMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla AsignacionSalarial
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 27 de Julio de 2018
*/
public class AsignacionSalarialService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -672729305482854559L;
	
	/**
	 * 
	 * @param AsignacionSalarial
	 * @return
	 */
	public AsignacionSalarial insertAsignacionSalarial (AsignacionSalarial asignacionSalarial){
		AsignacionSalarial acre = new AsignacionSalarial();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AsignacionSalarialMapper  mapper = session.getMapper(AsignacionSalarialMapper.class);
			id =  mapper.insert(asignacionSalarial);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 5:40:28 p. m.
	 * @param AsignacionSalarial
	 * @return AsignacionSalarial
	 */
	public AsignacionSalarial updateAsignacionSalarial(AsignacionSalarial asignacionSalarial){
		AsignacionSalarial acre = new AsignacionSalarial();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AsignacionSalarialMapper  mapper = session.getMapper(AsignacionSalarialMapper.class);
			id = mapper.updateByPrimaryKey(asignacionSalarial);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;	
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
	public List<AsignacionSalarial> getAsignacionSalarialall(){
		List<AsignacionSalarial> asoc = new ArrayList<>();
		AsignacionSalarialExample dtoObject = new AsignacionSalarialExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AsignacionSalarialMapper  mapper = session.getMapper(AsignacionSalarialMapper.class);
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
	public AsignacionSalarial getAsignacionSalarialById(long id){
		AsignacionSalarial asoc = new AsignacionSalarial();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AsignacionSalarialMapper  mapper = session.getMapper(AsignacionSalarialMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new AsignacionSalarial();
			}
		}catch(Exception ex){
		
			return new AsignacionSalarial();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author Maria Alejandra Colorado R�os 
	 * Metodo que retorna las vinculaciones por Planta de personal
	 * @param vinculacionExt
	 * @return
	 */
	public List<AsignacionSalarialExt> getVinculacionPorPlanta(AsignacionSalarialExt asignacionSalarialExt){
		List<AsignacionSalarialExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AsignacionSalarialMapper  mapper = session.getMapper(AsignacionSalarialMapper.class);
			asoc =  mapper.selectAsignacionSalarialFiltro(asignacionSalarialExt);
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
