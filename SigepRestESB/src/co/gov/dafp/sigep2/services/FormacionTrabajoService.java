/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.FormacionTrabajo;
import co.gov.dafp.sigep2.bean.FormacionTrabajoExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.FormacionTrabajoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla FormacionTrabajoService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class FormacionTrabajoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2703381530104619435L;
	
	/**
	 * 
	 * @param FormacionTrabajo
	 * @return
	 */
	public FormacionTrabajo insertFormacionTrabajo (FormacionTrabajo formacionTrabajo){
		FormacionTrabajo form = new FormacionTrabajo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FormacionTrabajoMapper  mapper = session.getMapper(FormacionTrabajoMapper.class);
			id =  mapper.insert(formacionTrabajo);
			if(id > 0){
				form.setError(false);
				form.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				form.setError(false);
				form.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return form;
		}catch (Exception ex) {
			form.setError(true);
			form.setMensaje(UtilsConstantes.MSG_EXEPCION);
			form.setMensajeTecnico(ex.getMessage());
			return form;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param FormacionTrabajo
	 * @return
	 */
	public FormacionTrabajo updateFormacionTrabajo(FormacionTrabajo formacionTrabajo){
		FormacionTrabajo form = new FormacionTrabajo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FormacionTrabajoMapper  mapper = session.getMapper(FormacionTrabajoMapper.class);
			id = mapper.updateByPrimaryKey(formacionTrabajo);
			session.commit();
			if(id > 0){
				form.setError(false);
				form.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				form.setError(false);
				form.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return form;
		}catch (Exception ex) {
			form.setError(true);
			form.setMensaje(UtilsConstantes.MSG_EXEPCION);
			form.setMensajeTecnico(ex.getMessage());
			return form;	
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
	public List<FormacionTrabajo> getFormacionTrabajoByAll(int limitIni, int limitEnd){
		List<FormacionTrabajo> asoc = new ArrayList<>();
		FormacionTrabajoExample dtoObject = new FormacionTrabajoExample();
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
			FormacionTrabajoMapper  mapper = session.getMapper(FormacionTrabajoMapper.class);
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
	public List<FormacionTrabajo> getFormacionTrabajoById(long id){
		List<FormacionTrabajo> asoc = new ArrayList<>();
		FormacionTrabajoExample dtoObject = new FormacionTrabajoExample();
		dtoObject.createCriteria().andCodFormacionTrabajoEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FormacionTrabajoMapper  mapper = session.getMapper(FormacionTrabajoMapper.class);
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
