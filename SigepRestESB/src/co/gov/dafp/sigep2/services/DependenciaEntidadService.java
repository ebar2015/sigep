/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.DependenciaEntidad;
import co.gov.dafp.sigep2.bean.DependenciaEntidadExample;
import co.gov.dafp.sigep2.bean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DependenciaEntidadMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla DependenciaEntidad
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class DependenciaEntidadService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6583129464558841030L;
	
	/**
	 * 
	 * @param DependenciaEntidad
	 * @return
	 */
	public DependenciaEntidad insertDependenciaEntidad (DependenciaEntidad dependenciaEntidad){
		DependenciaEntidad dept = new DependenciaEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper  mapper = session.getMapper(DependenciaEntidadMapper.class);
			id =  mapper.insert(dependenciaEntidad);
			if(id > 0){
				dept.setError(false);
				dept.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				dept.setError(false);
				dept.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return dept;
		}catch (Exception ex) {
			dept.setError(true);
			dept.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dept.setMensajeTecnico(ex.getMessage());
			return dept;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param DependenciaEntidad
	 * @return
	 */
	public DependenciaEntidad updateDependenciaEntidad(DependenciaEntidad dependenciaEntidad){
		DependenciaEntidad dept = new DependenciaEntidad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper  mapper = session.getMapper(DependenciaEntidadMapper.class);
			id = mapper.updateByPrimaryKey(dependenciaEntidad);
			session.commit();
			if(id > 0){
				dept.setError(false);
				dept.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				dept.setError(false);
				dept.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return dept;
		}catch (Exception ex) {
			dept.setError(true);
			dept.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dept.setMensajeTecnico(ex.getMessage());
			return dept;		
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
	public List<DependenciaEntidad> getDependenciaEntidadByAll(){
		List<DependenciaEntidad> asoc = new ArrayList<>();
		DependenciaEntidadExample dtoObject = new DependenciaEntidadExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper  mapper = session.getMapper(DependenciaEntidadMapper.class);
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
	 * @return
	 */
	public List<DependenciaEntidad> getDependenciaEntidadPersona(DependenciaEntidadExt dependenciaEntidad){
		List<DependenciaEntidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper  mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc =  mapper.selectDpendenciaPersona(dependenciaEntidad);
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
	 * @return
	 */
	public List<DependenciaEntidad> getDependenciaEntidadByunion(){
		List<DependenciaEntidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper  mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc =  mapper.selectUnion();
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
	public List<DependenciaEntidad> getDependenciaEntidadById(long codEntidad){
		List<DependenciaEntidad> asoc = new ArrayList<>();
		DependenciaEntidadExample dtoObject = new DependenciaEntidadExample();
		dtoObject.createCriteria().andCodDependenciaEntidadEqualTo(codEntidad);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper  mapper = session.getMapper(DependenciaEntidadMapper.class);
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
	public DependenciaEntidad getDependenciaEntidadId(long codDependenciaEntidad){
		DependenciaEntidad asoc = new DependenciaEntidad();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper  mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc =  mapper.selectByPrimaryKey(codDependenciaEntidad);
			if(asoc != null){
				return asoc;
			}else{
				return new DependenciaEntidad();
			}
		}catch(Exception ex){
			return new DependenciaEntidad();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metodo para retornar una lista de datos de DependenciaEntidad por filtro
	 * @return List<DependenciaEntidadExt>
	 */
	public List<DependenciaEntidadExt> getDependenciaEntidadFilter(DependenciaEntidadExt dependenciaEntidadExt) {
		List<DependenciaEntidadExt> asoc = new ArrayList<>();
		if(dependenciaEntidadExt.getLimitEnd() == 0) {
			dependenciaEntidadExt.setLimitInit(0);
			dependenciaEntidadExt.setLimitEnd(10000);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc = mapper.selectDependenciaEntidadFilter(dependenciaEntidadExt);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public List<DependenciaEntidadExt> obtenerDependenciasEstructura(DependenciaEntidadExt dependenciaEntidadExt) {
		List<DependenciaEntidadExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc = mapper.obtenerDependenciasEstructura(dependenciaEntidadExt);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public DependenciaEntidadExt obtenerDependenciaEstructuraCodPadre(DependenciaEntidadExt dependenciaEntidadExt) {
		DependenciaEntidadExt asoc = new DependenciaEntidadExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc = mapper.obtenerDependenciaEstructuraCodPadre(dependenciaEntidadExt);
			if (asoc != null) {
				return asoc;
			} else {
				return new DependenciaEntidadExt();
			}
		} catch (Exception ex) {
			return new DependenciaEntidadExt();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public DependenciaEntidadExt obtenerDependenciaEstructuraJerarquia(DependenciaEntidadExt dependenciaEntidadExt) {
		DependenciaEntidadExt asoc = new DependenciaEntidadExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc = mapper.obtenerDependenciaEstructuraJerarquia(dependenciaEntidadExt);
			if (asoc != null) {
				return asoc;
			} else {
				return new DependenciaEntidadExt();
			}
		} catch (Exception ex) {
			return new DependenciaEntidadExt();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public List<DependenciaEntidadExt> obtenerDependenciaEstructuraHijos(DependenciaEntidadExt dependenciaEntidadExt) {
		List<DependenciaEntidadExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc = mapper.obtenerDependenciaEstructuraHijos(dependenciaEntidadExt);
			if (asoc != null) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			DependenciaEntidadExt objetoError = new DependenciaEntidadExt();
			asoc = new ArrayList<>();
			objetoError = new DependenciaEntidadExt();
			objetoError.setError(true);
			objetoError.setMensaje("Error en la consulta");
			objetoError.setMensajeTecnico(ex.getMessage());
			asoc.add(objetoError);
			return asoc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public DependenciaEntidadExt obtenerDependenciaPadreByCodPredecesor(DependenciaEntidadExt objeto) {
		DependenciaEntidadExt asoc = new DependenciaEntidadExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaEntidadMapper mapper = session.getMapper(DependenciaEntidadMapper.class);
			asoc = mapper.obtenerDependenciaPadreByCodPredecesor(objeto);
			if (asoc != null) {
				return asoc;
			} else {
				return new DependenciaEntidadExt();
			}
		} catch (Exception ex) {
			return new DependenciaEntidadExt();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}	
}
