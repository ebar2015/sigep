/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmHvEvaluacionDesempeno;
import co.gov.dafp.sigep2.bean.CmHvEvaluacionDesempenoExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmHvEvaluacionDesempenoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CmHvEvaluacionDesempeno
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CmHvEvaluacionDesempenoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4819197729273849041L;
	
	
	/**
	 * 
	 * @param CmHvEvaluacionDesempeno
	 * @return
	 */
	public CmHvEvaluacionDesempeno insertCmHvEvaluacionDesempeno (CmHvEvaluacionDesempeno CmHvEvaluacionDesempeno){
		CmHvEvaluacionDesempeno cue = new CmHvEvaluacionDesempeno();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEvaluacionDesempenoMapper mapper = session.getMapper(CmHvEvaluacionDesempenoMapper.class);
			id =  mapper.insert(CmHvEvaluacionDesempeno);
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
	 * 
	 * @param CmHvEvaluacionDesempeno
	 * @return
	 */
	public CmHvEvaluacionDesempeno updateCmHvEvaluacionDesempeno(CmHvEvaluacionDesempeno CmHvEvaluacionDesempeno){
		CmHvEvaluacionDesempeno cue = new CmHvEvaluacionDesempeno();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEvaluacionDesempenoMapper  mapper = session.getMapper(CmHvEvaluacionDesempenoMapper.class);
			id = mapper.updateByPrimaryKey(CmHvEvaluacionDesempeno);
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
	 * 
	 * @param Id Search
	 * @return
	 */
	public CmHvEvaluacionDesempeno getCmHvEvaluacionDesempenoById(long id){
		CmHvEvaluacionDesempeno asoc = new CmHvEvaluacionDesempeno();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEvaluacionDesempenoMapper  mapper = session.getMapper(CmHvEvaluacionDesempenoMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmHvEvaluacionDesempeno();
			}
		}catch(Exception ex){
		
			return new CmHvEvaluacionDesempeno();
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
	public List<CmHvEvaluacionDesempeno> getCmHvEvaluacionDesempenoProcarga(Short codPorceso){
		List<CmHvEvaluacionDesempeno> asoc = new ArrayList<>();
		CmHvEvaluacionDesempenoExample dtoObject = new CmHvEvaluacionDesempenoExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(codPorceso);
      	SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmHvEvaluacionDesempenoMapper  mapper = session.getMapper(CmHvEvaluacionDesempenoMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(asoc!=null){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			CmHvEvaluacionDesempeno cm = new CmHvEvaluacionDesempeno();
			cm.setError(true);
			cm.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cm.setMensajeTecnico(ex.getMessage());
			asoc.add(cm);
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
