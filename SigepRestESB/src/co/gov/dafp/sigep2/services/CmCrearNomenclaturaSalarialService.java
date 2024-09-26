/**
* 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmCrearNomenclaturaSalarial;
import co.gov.dafp.sigep2.bean.CmCrearNomenclaturaSalarialExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmCrearNomenclaturaSalarialMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar 
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Date 9/10/2018
 */
public class CmCrearNomenclaturaSalarialService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5981192188331976414L;
	
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 9/10/2018 - 2:41:47 p. m.
	 * @param CmCrearNomenclaturaSalarial
	 * @return
	 */
	public CmCrearNomenclaturaSalarial insertCmCrearNomenclaturaSalarial (CmCrearNomenclaturaSalarial cmCrearNomenclaturaSalarial){
		CmCrearNomenclaturaSalarial cue = new CmCrearNomenclaturaSalarial();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearNomenclaturaSalarialMapper  mapper = session.getMapper(CmCrearNomenclaturaSalarialMapper.class);
			id =  mapper.insert(cmCrearNomenclaturaSalarial);
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
	 * @author: Jose Viscaya 
	 * @fecha 9/10/2018 - 2:42:08 p. m.
	 * @param cmCrearNomenclaturaSalarial
	 * @return
	 */
	public CmCrearNomenclaturaSalarial updateCmCrearNomenclaturaSalarial(CmCrearNomenclaturaSalarial cmCrearNomenclaturaSalarial){
		CmCrearNomenclaturaSalarial cue = new CmCrearNomenclaturaSalarial();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearNomenclaturaSalarialMapper  mapper = session.getMapper(CmCrearNomenclaturaSalarialMapper.class);
			id = mapper.updateByPrimaryKey(cmCrearNomenclaturaSalarial);
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
	 * @author: Jose Viscaya 
	 * @fecha 9/10/2018 - 2:42:14 p. m.
	 * @param id
	 * @return
	 */
	public CmCrearNomenclaturaSalarial getCmCrearNomenclaturaSalarialById(long id){
		CmCrearNomenclaturaSalarial asoc = new CmCrearNomenclaturaSalarial();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearNomenclaturaSalarialMapper  mapper = session.getMapper(CmCrearNomenclaturaSalarialMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmCrearNomenclaturaSalarial();
			}
		}catch(Exception ex){
		
			return new CmCrearNomenclaturaSalarial();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 9/10/2018 - 2:42:21 p. m.
	 * @param codProcesoCargaMasiva
	 * @return
	 */
	public List<CmCrearNomenclaturaSalarial> getCmCrearNomenclaturaSalarialCodProceso(Long codProcesoCargaMasiva){
		List<CmCrearNomenclaturaSalarial> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearNomenclaturaSalarialMapper  mapper = session.getMapper(CmCrearNomenclaturaSalarialMapper.class);
			asoc =  mapper.selectByProceso(codProcesoCargaMasiva);
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
