/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.NomenclaturaEntidad;
import co.gov.dafp.sigep2.bean.NomenclaturaEntidadExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.NomenclaturaEntidadMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla NomenclaturaEntidad
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 27 de Julio de 2018
*/
public class NomenclaturaEntidadService implements Serializable {

	private static final long serialVersionUID = -8104913986571491286L;
	/**
	 * 
	 * @param NomenclaturaEntidad
	 * @return
	 */
	public NomenclaturaEntidad insertNomenclaturaEntidad (NomenclaturaEntidad nomenclaturaEntidad){
		NomenclaturaEntidad acre = new NomenclaturaEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaEntidadMapper  mapper = session.getMapper(NomenclaturaEntidadMapper.class);
			id =  mapper.insert(nomenclaturaEntidad);
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
	 * @param NomenclaturaEntidad
	 * @return
	 */
	public NomenclaturaEntidad updateNomenclaturaEntidad(NomenclaturaEntidad nomenclaturaEntidad){
		NomenclaturaEntidad acre = new NomenclaturaEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaEntidadMapper  mapper = session.getMapper(NomenclaturaEntidadMapper.class);
			id = mapper.updateByPrimaryKey(nomenclaturaEntidad);
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
	public List<NomenclaturaEntidad> getNomenclaturaEntidadall(){
		List<NomenclaturaEntidad> asoc = new ArrayList<>();
		NomenclaturaEntidadExample dtoObject = new NomenclaturaEntidadExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaEntidadMapper  mapper = session.getMapper(NomenclaturaEntidadMapper.class);
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
	 * @param nomenclaturaEntidad
	 * @return
	 */
	public List<NomenclaturaEntidad> getNomenclaturaEntidadFiltro(NomenclaturaEntidad nomenclaturaEntidad){
		List<NomenclaturaEntidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaEntidadMapper  mapper = session.getMapper(NomenclaturaEntidadMapper.class);
			asoc =  mapper.selectByfilter(nomenclaturaEntidad);
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
	public NomenclaturaEntidad getNomenclaturaEntidadById(long id){
		NomenclaturaEntidad asoc = new NomenclaturaEntidad();
		NomenclaturaEntidadExample dtoObject = new NomenclaturaEntidadExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaEntidadMapper  mapper = session.getMapper(NomenclaturaEntidadMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new NomenclaturaEntidad();
			}
		}catch(Exception ex){
		
			return new NomenclaturaEntidad();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
