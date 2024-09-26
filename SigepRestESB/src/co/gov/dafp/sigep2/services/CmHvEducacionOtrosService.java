/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmHvEducacionOtros;
import co.gov.dafp.sigep2.bean.CmHvEducacionOtrosExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmHvEducacionOtrosMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmHvEducacionOtros
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 26 de Julio de 2018
*/
public class CmHvEducacionOtrosService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5940468075407984921L;
	
	/**
	 * 
	 * @param CmHvEducacionOtros
	 * @return
	 */
	public CmHvEducacionOtros insertCmHvEducacionOtros (CmHvEducacionOtros cmHvEducacionOtros){
		CmHvEducacionOtros acre = new CmHvEducacionOtros();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionOtrosMapper  mapper = session.getMapper(CmHvEducacionOtrosMapper.class);
			id =  mapper.insert(cmHvEducacionOtros);
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
	 * @param CmHvEducacionOtros
	 * @return
	 */
	public CmHvEducacionOtros updateCmHvEducacionOtros(CmHvEducacionOtros cmHvEducacionOtros){
		CmHvEducacionOtros acre = new CmHvEducacionOtros();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionOtrosMapper  mapper = session.getMapper(CmHvEducacionOtrosMapper.class);
			id = mapper.updateByPrimaryKey(cmHvEducacionOtros);
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
	public List<CmHvEducacionOtros> getCmHvEducacionOtrosAll(){
		List<CmHvEducacionOtros> asoc = new ArrayList<>();
		CmHvEducacionOtrosExample dtoObject = new CmHvEducacionOtrosExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionOtrosMapper  mapper = session.getMapper(CmHvEducacionOtrosMapper.class);
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
	public CmHvEducacionOtros getCmHvEducacionOtrosById(long id){
		CmHvEducacionOtros asoc = new CmHvEducacionOtros();
		CmHvEducacionOtrosExample dtoObject = new CmHvEducacionOtrosExample();
		dtoObject.createCriteria().andCodCmHvEducacionOtrosEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionOtrosMapper  mapper = session.getMapper(CmHvEducacionOtrosMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmHvEducacionOtros();
			}
		}catch(Exception ex){
		
			return new CmHvEducacionOtros();
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
	public List<CmHvEducacionOtros> getCmHvEducacionOtrosProceso(Short id){
		List<CmHvEducacionOtros> asoc = new ArrayList<>();
		CmHvEducacionOtrosExample dtoObject = new CmHvEducacionOtrosExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionOtrosMapper  mapper = session.getMapper(CmHvEducacionOtrosMapper.class);
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
