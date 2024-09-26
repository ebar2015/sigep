/**
* 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EntidadFusion;
import co.gov.dafp.sigep2.bean.EntidadFusionExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadFusionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la persiostencia con la tabla EntidadFusion
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Date 10/10/2018
 */
public class EntidadFusionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8415328757642032899L;
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 10/10/2018 - 5:56:28 p. m.
	 * @param entidadFusion
	 * @return
	 */
	public EntidadFusion insertEntidadFusion(EntidadFusion entidadFusion){
		EntidadFusion arch =  new EntidadFusion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadFusionMapper  mapper = session.getMapper(EntidadFusionMapper.class);
			id =  mapper.insert(entidadFusion);
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
	 * @author: Jose Viscaya 
	 * @fecha 10/10/2018 - 5:56:18 p. m.
	 * @param entidadFusion
	 * @return
	 */
	public EntidadFusion updateEntidadFusion(EntidadFusion entidadFusion){
		EntidadFusion arch =  new EntidadFusion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadFusionMapper  mapper = session.getMapper(EntidadFusionMapper.class);
			id = mapper.updateByPrimaryKey(entidadFusion);
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
	 * @author: Jose Viscaya 
	 * @fecha 10/10/2018 - 5:56:13 p. m.
	 * @return
	 */
	public List<EntidadFusion> getEntidadFusionByAll(){
		List<EntidadFusion> asoc = new ArrayList<>();
		EntidadFusionExample dtoObject = new EntidadFusionExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadFusionMapper  mapper = session.getMapper(EntidadFusionMapper.class);
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
	 * @fecha 10/10/2018 - 5:56:05 p. m.
	 * @param entidadFusion
	 * @return
	 */
	public List<EntidadFusion> getEntidadFusion(EntidadFusion entidadFusion){
		List<EntidadFusion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadFusionMapper  mapper = session.getMapper(EntidadFusionMapper.class);
			asoc =  mapper.selectByFiltro(entidadFusion);
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
