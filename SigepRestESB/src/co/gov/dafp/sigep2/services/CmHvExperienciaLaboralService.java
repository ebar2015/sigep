/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmHvExperienciaLaboral;
import co.gov.dafp.sigep2.bean.CmHvExperienciaLaboralExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmHvExperienciaLaboralMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmHvExperienciaLaboral
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 26 de Julio de 2018
*/
public class CmHvExperienciaLaboralService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8489449490303192386L;
	
	/**
	 * 
	 * @param CmHvExperienciaLaboral
	 * @return
	 */
	public CmHvExperienciaLaboral insertCmHvExperienciaLaboral (CmHvExperienciaLaboral cmHvExperienciaLaboral){
		CmHvExperienciaLaboral acre = new CmHvExperienciaLaboral();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralMapper  mapper = session.getMapper(CmHvExperienciaLaboralMapper.class);
			id =  mapper.insert(cmHvExperienciaLaboral);
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
	 * @param CmHvExperienciaLaboral
	 * @return
	 */
	public CmHvExperienciaLaboral updateCmHvExperienciaLaboral(CmHvExperienciaLaboral cmHvExperienciaLaboral){
		CmHvExperienciaLaboral acre = new CmHvExperienciaLaboral();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralMapper  mapper = session.getMapper(CmHvExperienciaLaboralMapper.class);
			id = mapper.updateByPrimaryKey(cmHvExperienciaLaboral);
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
	public List<CmHvExperienciaLaboral> getCmHvExperienciaLaboralAll(){
		List<CmHvExperienciaLaboral> asoc = new ArrayList<>();
		CmHvExperienciaLaboralExample dtoObject = new CmHvExperienciaLaboralExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralMapper  mapper = session.getMapper(CmHvExperienciaLaboralMapper.class);
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
	public CmHvExperienciaLaboral getCmHvExperienciaLaboralById(long id){
		CmHvExperienciaLaboral asoc = new CmHvExperienciaLaboral();
		CmHvExperienciaLaboralExample dtoObject = new CmHvExperienciaLaboralExample();
		dtoObject.createCriteria().andCodCmHvExperienciaLaboralEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralMapper  mapper = session.getMapper(CmHvExperienciaLaboralMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmHvExperienciaLaboral();
			}
		}catch(Exception ex){
		
			return new CmHvExperienciaLaboral();
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
	public List<CmHvExperienciaLaboral> getCmHvExperienciaLaboralProceso(Short id){
		List<CmHvExperienciaLaboral> asoc = new ArrayList<>();
		CmHvExperienciaLaboralExample dtoObject = new CmHvExperienciaLaboralExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralMapper  mapper = session.getMapper(CmHvExperienciaLaboralMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(asoc!=null){
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
