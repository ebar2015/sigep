/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmContratos;
import co.gov.dafp.sigep2.bean.CmContratosExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmContratosMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmContratos
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha 10/08/2018 8:12:02 a. m.
 */
public class CmContratosService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1352838286255904701L;
	
	
	/**
	* @author: Jose Viscaya 
	* @fecha 10/08/2018 8:17:39 a. m.
	* @param cmContratos
	* @return
	 */
	public CmContratos insertCmContratos (CmContratos cmContratos){
		CmContratos cue = new CmContratos();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmContratosMapper  mapper = session.getMapper(CmContratosMapper.class);
			id =  mapper.insert(cmContratos);
			if(id > 0){
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
				return cue;
			}else{
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
				return cue;
			}
		}catch (Exception ex) {
			cue.setError(true);
			cue.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cue.setMensajeTecnico(ex.getMessage());
			return cue;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	* @author: Jose Viscaya 
	* @fecha 10/08/2018 8:17:46 a. m.
	* @param CmContratos
	* @return
	 */
	public CmContratos updateCmContratos(CmContratos CmContratos){
		CmContratos cue = new CmContratos();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmContratosMapper  mapper = session.getMapper(CmContratosMapper.class);
			id = mapper.updateByPrimaryKey(CmContratos);
			if(id > 0){
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return cue;
		}catch (Exception ex) {
			cue.setError(true);
			cue.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cue.setMensajeTecnico(ex.getMessage());
			return cue;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	* @author: Jose Viscaya 
	* @fecha 10/08/2018 8:17:52 a. m.
	* @param id
	* @return
	 */
	public CmContratos getCmContratosById(long id){
		CmContratos asoc = new CmContratos();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmContratosMapper  mapper = session.getMapper(CmContratosMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmContratos();
			}
		}catch(Exception ex){
		
			return new CmContratos();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	* @author: Jose Viscaya 
	* @fecha 10/08/2018 8:17:57 a. m.
	* @param codProcesoCargaMasiva
	* @return
	 */
	public List<CmContratos> getCmContratosCodProceso(Long codProcesoCargaMasiva){
		List<CmContratos> asoc = new ArrayList<>();
		CmContratosExample dtoObject = new CmContratosExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(codProcesoCargaMasiva);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmContratosMapper  mapper = session.getMapper(CmContratosMapper.class);
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
