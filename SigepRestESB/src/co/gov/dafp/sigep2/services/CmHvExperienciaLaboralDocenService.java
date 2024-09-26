/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmHvExperienciaLaboralDocen;
import co.gov.dafp.sigep2.bean.CmHvExperienciaLaboralDocenExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmHvExperienciaLaboralDocenMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmHvExperienciaLaboralDocen
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 26 de Julio de 2018
*/
public class CmHvExperienciaLaboralDocenService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3108866078405114033L;
	
	/**
	* @author: Jose Viscaya 
	* @fecha 10/08/2018 8:16:33 a. m.
	* @param cmHvExperienciaLaboralDocen
	* @return
	 */
	public CmHvExperienciaLaboralDocen insertCmHvExperienciaLaboralDocen (CmHvExperienciaLaboralDocen cmHvExperienciaLaboralDocen){
		CmHvExperienciaLaboralDocen acre = new CmHvExperienciaLaboralDocen();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralDocenMapper  mapper = session.getMapper(CmHvExperienciaLaboralDocenMapper.class);
			id =  mapper.insert(cmHvExperienciaLaboralDocen);
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
	* @fecha 10/08/2018 8:17:04 a. m.
	* @param cmHvExperienciaLaboralDocen
	* @return
	 */
	public CmHvExperienciaLaboralDocen updateCmHvExperienciaLaboralDocen(CmHvExperienciaLaboralDocen cmHvExperienciaLaboralDocen){
		CmHvExperienciaLaboralDocen acre = new CmHvExperienciaLaboralDocen();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralDocenMapper  mapper = session.getMapper(CmHvExperienciaLaboralDocenMapper.class);
			id = mapper.updateByPrimaryKey(cmHvExperienciaLaboralDocen);
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
	* @author: Jose Viscaya 
	* @fecha 10/08/2018 8:17:10 a. m.
	* @return
	 */
	public List<CmHvExperienciaLaboralDocen> getCmHvExperienciaLaboralDocenAll(){
		List<CmHvExperienciaLaboralDocen> asoc = new ArrayList<>();
		CmHvExperienciaLaboralDocenExample dtoObject = new CmHvExperienciaLaboralDocenExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralDocenMapper  mapper = session.getMapper(CmHvExperienciaLaboralDocenMapper.class);
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
	* @fecha 10/08/2018 8:17:14 a. m.
	* @param id
	* @return
	 */
	public CmHvExperienciaLaboralDocen getCmHvExperienciaLaboralDocenById(long id){
		CmHvExperienciaLaboralDocen asoc = new CmHvExperienciaLaboralDocen();
		CmHvExperienciaLaboralDocenExample dtoObject = new CmHvExperienciaLaboralDocenExample();
		dtoObject.createCriteria().andCodCmHvExperienciaLaboralDocenEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralDocenMapper  mapper = session.getMapper(CmHvExperienciaLaboralDocenMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmHvExperienciaLaboralDocen();
			}
		}catch(Exception ex){
		
			return new CmHvExperienciaLaboralDocen();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 10/08/2018 8:17:19 a. m.
	* @param id
	* @return
	 */
	public List<CmHvExperienciaLaboralDocen> getCmHvExperienciaLaboralDocenProceso(Short id){
		List<CmHvExperienciaLaboralDocen> asoc = new ArrayList<>();
		CmHvExperienciaLaboralDocenExample dtoObject = new CmHvExperienciaLaboralDocenExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvExperienciaLaboralDocenMapper  mapper = session.getMapper(CmHvExperienciaLaboralDocenMapper.class);
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
