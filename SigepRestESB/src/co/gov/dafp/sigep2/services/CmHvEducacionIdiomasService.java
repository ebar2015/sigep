/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmHvEducacionIdiomas;
import co.gov.dafp.sigep2.bean.CmHvEducacionIdiomasExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmHvEducacionIdiomasMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmHvEducacionIdiomas
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 26 de Julio de 2018
*/
public class CmHvEducacionIdiomasService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7165665303476026590L;
	
	/**
	 * 
	 * @param CmHvEducacionIdiomas
	 * @return
	 */
	public CmHvEducacionIdiomas insertCmHvEducacionIdiomas (CmHvEducacionIdiomas cmHvEducacionIdiomas){
		CmHvEducacionIdiomas acre = new CmHvEducacionIdiomas();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionIdiomasMapper  mapper = session.getMapper(CmHvEducacionIdiomasMapper.class);
			id =  mapper.insert(cmHvEducacionIdiomas);
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
	 * @param CmHvEducacionIdiomas
	 * @return
	 */
	public CmHvEducacionIdiomas updateCmHvEducacionIdiomas(CmHvEducacionIdiomas cmHvEducacionIdiomas){
		CmHvEducacionIdiomas acre = new CmHvEducacionIdiomas();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionIdiomasMapper  mapper = session.getMapper(CmHvEducacionIdiomasMapper.class);
			id = mapper.updateByPrimaryKey(cmHvEducacionIdiomas);
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
	public List<CmHvEducacionIdiomas> getCmHvEducacionIdiomasAll(){
		List<CmHvEducacionIdiomas> asoc = new ArrayList<>();
		CmHvEducacionIdiomasExample dtoObject = new CmHvEducacionIdiomasExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionIdiomasMapper  mapper = session.getMapper(CmHvEducacionIdiomasMapper.class);
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
	public CmHvEducacionIdiomas getCmHvEducacionIdiomasById(long id){
		CmHvEducacionIdiomas asoc = new CmHvEducacionIdiomas();
		CmHvEducacionIdiomasExample dtoObject = new CmHvEducacionIdiomasExample();
		dtoObject.createCriteria().andCodCmHvEducacionIdiomasEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionIdiomasMapper  mapper = session.getMapper(CmHvEducacionIdiomasMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmHvEducacionIdiomas();
			}
		}catch(Exception ex){
		
			return new CmHvEducacionIdiomas();
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
	public List<CmHvEducacionIdiomas> getCmHvEducacionIdiomasProceso(Short id){
		List<CmHvEducacionIdiomas> asoc = new ArrayList<>();
		CmHvEducacionIdiomasExample dtoObject = new CmHvEducacionIdiomasExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEducacionIdiomasMapper  mapper = session.getMapper(CmHvEducacionIdiomasMapper.class);
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
