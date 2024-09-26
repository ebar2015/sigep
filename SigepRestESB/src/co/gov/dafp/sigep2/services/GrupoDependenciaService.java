/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.GrupoDependencia;
import co.gov.dafp.sigep2.bean.GrupoDependenciaExample;
import co.gov.dafp.sigep2.bean.ext.GrupoDependenciaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.GrupoDependenciaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya
* @version 1.0
* @Class Que se encarga de procesar la comunicacion con la base de datos
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 10/09/2018 3:25:58 p.m.
*/
public class GrupoDependenciaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2909807285917665641L;
	
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 10/09/2018 3:28:25 p.m.
	* @param grupoDependencia
	* @return
	*
	 */
	public GrupoDependencia insertGrupoDependencia (GrupoDependencia grupoDependencia){
		GrupoDependencia cue = new GrupoDependencia();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			GrupoDependenciaMapper  mapper = session.getMapper(GrupoDependenciaMapper.class);
			id =  mapper.insert(grupoDependencia);
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
	* @fecha 10/09/2018 3:28:31 p.m.
	* @param grupoDependencia
	* @return
	*
	 */
	public GrupoDependencia updateGrupoDependencia(GrupoDependencia grupoDependencia){
		GrupoDependencia cue = new GrupoDependencia();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			GrupoDependenciaMapper  mapper = session.getMapper(GrupoDependenciaMapper.class);
			id = mapper.updateByPrimaryKey(grupoDependencia);
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
	* @fecha 10/09/2018 3:28:36 p.m.
	* @return
	*
	 */
	public List<GrupoDependencia> getGrupoDependenciaByall(){
		List<GrupoDependencia> asoc = new ArrayList<>();
		GrupoDependenciaExample dtoObject = new GrupoDependenciaExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			GrupoDependenciaMapper  mapper = session.getMapper(GrupoDependenciaMapper.class);
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
	* @fecha 10/09/2018 3:36:12 p.m.
	* @param grupoDependencia
	* @return
	*
	 */
	public List<GrupoDependencia> getGrupoDependenciaByFiltro(GrupoDependencia grupoDependencia){
		List<GrupoDependencia> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			GrupoDependenciaMapper  mapper = session.getMapper(GrupoDependenciaMapper.class);
			asoc =  mapper.selectByFiltro(grupoDependencia);
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
	* @fecha 10/09/2018 3:28:42 p.m.
	* @param id
	* @return
	*
	 */
	public GrupoDependencia getIncrementoSalarialById(long id){
		GrupoDependencia asoc = new GrupoDependencia();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			GrupoDependenciaMapper  mapper = session.getMapper(GrupoDependenciaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new GrupoDependencia();
			}
		}catch(Exception ex){
		
			return new GrupoDependencia();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 2:30:18 p.m.
	* @param grupoDependencia
	* @return
	*
	 */
	public List<GrupoDependencia> getGrupoDependenciaByFiltroEntidad(GrupoDependenciaExt grupoDependencia){
		List<GrupoDependencia> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			GrupoDependenciaMapper  mapper = session.getMapper(GrupoDependenciaMapper.class);
			asoc =  mapper.selectByFiltroEntidad(grupoDependencia);
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
