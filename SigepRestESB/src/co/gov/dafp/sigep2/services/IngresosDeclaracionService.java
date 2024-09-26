/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.IngresosDeclaracion;
import co.gov.dafp.sigep2.bean.IngresosDeclaracionExample;
import co.gov.dafp.sigep2.bean.ext.IngresosDeclaracionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.IngresosDeclaracionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla IngresosDeclaracionService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class IngresosDeclaracionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5970163658320104835L;
	
	public IngresosDeclaracion insertIngresosDeclaracion (IngresosDeclaracion ingresosDeclaracion){
		IngresosDeclaracion ing = new IngresosDeclaracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IngresosDeclaracionMapper  mapper = session.getMapper(IngresosDeclaracionMapper.class);
			id =  mapper.insert(ingresosDeclaracion);
			if(id > 0){
				ing.setError(false);
				ing.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				ing.setError(false);
				ing.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return ing;
		}catch (Exception ex) {
			ing.setError(true);
			ing.setMensaje(UtilsConstantes.MSG_EXEPCION);
			ing.setMensajeTecnico(ex.getMessage());
			return ing;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param IngresosDeclaracion
	 * @return
	 */
	public IngresosDeclaracion updateIngresosDeclaracion(IngresosDeclaracion ingresosDeclaracion){
		IngresosDeclaracion ing = new IngresosDeclaracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IngresosDeclaracionMapper  mapper = session.getMapper(IngresosDeclaracionMapper.class);
			id = mapper.updateByPrimaryKey(ingresosDeclaracion);
			if(id > 0){
				ing.setError(false);
				ing.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				ing.setError(false);
				ing.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return ing;
		}catch (Exception ex) {
			ing.setError(true);
			ing.setMensaje(UtilsConstantes.MSG_EXEPCION);
			ing.setMensajeTecnico(ex.getMessage());
			return ing;
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
	public List<IngresosDeclaracionExt> getIngresosDeclaracionCodDeclaracion(IngresosDeclaracion ingresosDeclaracion){
		List<IngresosDeclaracionExt> asoc = new ArrayList<>();
		IngresosDeclaracionExample dtoObject = new IngresosDeclaracionExample();
		if(ingresosDeclaracion!=null && ingresosDeclaracion.getCodDeclaracion()!=null) {
			dtoObject.createCriteria().andCodDeclaracionEqualTo(ingresosDeclaracion.getCodDeclaracion()).
			andFlgActivoEqualTo(ingresosDeclaracion.getFlgActivo());
		}
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IngresosDeclaracionMapper  mapper = session.getMapper(IngresosDeclaracionMapper.class);
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
	public IngresosDeclaracionExt getIngresosDeclaracionById(long id){
		IngresosDeclaracionExt asoc = new IngresosDeclaracionExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IngresosDeclaracionMapper  mapper = session.getMapper(IngresosDeclaracionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new IngresosDeclaracionExt();
			}
		}catch(Exception ex){
		
			return new IngresosDeclaracionExt();
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
	public IngresosDeclaracionExt getSumaIngresos(IngresosDeclaracion ingresosDeclaracion){
		IngresosDeclaracionExt asoc = new IngresosDeclaracionExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IngresosDeclaracionMapper  mapper = session.getMapper(IngresosDeclaracionMapper.class);
			asoc =  mapper.selectIngresoSuma(ingresosDeclaracion);
			if(asoc != null){
				return asoc;
			}else{
				return new IngresosDeclaracionExt();
			}
		}catch(Exception ex){
			IngresosDeclaracionExt user = new IngresosDeclaracionExt();
			user.setError(true);
			user.setMensajeTecnico(ex.getMessage());
			return user;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * Creado Por: Jose viscaya
	 * @param ingresosDeclaracion
	 * @return
	 * 21 oct. 2019 14:37:07
	 * IngresosDeclaracionService.java
	 */
	public IngresosDeclaracionExt gettotalIngresos(IngresosDeclaracion ingresosDeclaracion){
		IngresosDeclaracionExt asoc = new IngresosDeclaracionExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IngresosDeclaracionMapper  mapper = session.getMapper(IngresosDeclaracionMapper.class);
			asoc =  mapper.totalIngresos(ingresosDeclaracion);
			if(asoc != null){
				return asoc;
			}else{
				return new IngresosDeclaracionExt();
			}
		}catch(Exception ex){
			IngresosDeclaracionExt user = new IngresosDeclaracionExt();
			user.setError(true);
			user.setMensajeTecnico(ex.getMessage());
			return user;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
