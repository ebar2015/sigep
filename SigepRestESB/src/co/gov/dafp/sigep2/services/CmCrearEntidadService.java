/**
* 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmCrearEntidad;
import co.gov.dafp.sigep2.bean.CmCrearEntidadExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmCrearEntidadMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la tabla de carga masiva de entidades
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Date 3/10/2018
 */
public class CmCrearEntidadService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5798079313389141323L;
	
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 3/10/2018 - 10:19:41 a. m.
	 * @param cmCrearEntidad
	 * @return
	 */
	public CmCrearEntidad insertCmCrearEntidad (CmCrearEntidad cmCrearEntidad){
		CmCrearEntidad cue = new CmCrearEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearEntidadMapper  mapper = session.getMapper(CmCrearEntidadMapper.class);
			id =  mapper.insert(cmCrearEntidad);
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
	 * @fecha 3/10/2018 - 10:19:37 a. m.
	 * @param cmCrearEntidad
	 * @return
	 */
	public CmCrearEntidad updateCmCrearEntidad(CmCrearEntidad cmCrearEntidad){
		CmCrearEntidad cue = new CmCrearEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearEntidadMapper  mapper = session.getMapper(CmCrearEntidadMapper.class);
			id = mapper.updateByPrimaryKey(cmCrearEntidad);
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
	 * @fecha 3/10/2018 - 10:19:32 a. m.
	 * @param id
	 * @return
	 */
	public CmCrearEntidad getCmCrearEntidadById(long id){
		CmCrearEntidad asoc = new CmCrearEntidad();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearEntidadMapper  mapper = session.getMapper(CmCrearEntidadMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmCrearEntidad();
			}
		}catch(Exception ex){
		
			return new CmCrearEntidad();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 3/10/2018 - 10:19:24 a. m.
	 * @param codProcesoCargaMasiva
	 * @return
	 */
	public List<CmCrearEntidad> getCmCrearEntidadCodProceso(Long codProcesoCargaMasiva){
		List<CmCrearEntidad> asoc = new ArrayList<>();
		CmCrearEntidadExample dtoObject = new CmCrearEntidadExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(codProcesoCargaMasiva);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearEntidadMapper  mapper = session.getMapper(CmCrearEntidadMapper.class);
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
