/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EntidadPlanta;
import co.gov.dafp.sigep2.bean.EntidadPlantaExample;
import co.gov.dafp.sigep2.bean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadPlantaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla EntidadPlanta
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class EntidadPlantaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -162217977688960327L;
	
	/**
	 * 
	 * @param EntidadPlanta
	 * @return
	 */
	public EntidadPlanta insertEntidadPlanta (EntidadPlanta entidadPlanta){
		EntidadPlanta ent = new EntidadPlanta();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper  mapper = session.getMapper(EntidadPlantaMapper.class);
			id =  mapper.insert(entidadPlanta);
			if(id > 0){
				session.commit();
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				entidadPlanta.setAudFechaActualizacion(null);
				entidadPlanta.setFechaFin(null);
				entidadPlanta.setFechaInicio(null);
				if(entidadPlanta.getJustificacion()!=null) {
					if(entidadPlanta.getJustificacion().isEmpty()) {
						entidadPlanta.setJustificacion(null);
					}
				}
				entidadPlanta = getEntidadPlantaFilterId(entidadPlanta);
				ent.setCodEntidadPlanta(entidadPlanta.getCodEntidadPlanta());
				return ent;
			}else{
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
				return ent;
			}
		}catch (Exception ex) {
			ent.setError(true);
			ent.setMensaje(UtilsConstantes.MSG_EXEPCION);
			ent.setMensajeTecnico(ex.getMessage());
			return ent;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param EntidadPlanta
	 * @return
	 */
	public EntidadPlanta updateEntidadPlanta(EntidadPlanta EntidadPlanta){
		EntidadPlanta ent = new EntidadPlanta();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper  mapper = session.getMapper(EntidadPlantaMapper.class);
			id = mapper.updateByPrimaryKey(EntidadPlanta);
			session.commit();
			if(id > 0){
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
				return ent;
			}else{
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
				return ent;
			}
		}catch (Exception ex) {
			ent.setError(true);
			ent.setMensaje(UtilsConstantes.MSG_EXEPCION);
			ent.setMensajeTecnico(ex.getMessage());
			return ent;
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
	public List<EntidadPlanta> getEntidadPlantaByAll(){
		List<EntidadPlanta> asoc = new ArrayList<>();
		EntidadPlantaExample dtoObject = new EntidadPlantaExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper  mapper = session.getMapper(EntidadPlantaMapper.class);
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
	 * @param EntidadPlanta
	 * @return
	 */
	public List<EntidadPlanta> getEntidadPlantaentidad(EntidadPlantaExt EntidadPlanta){
		List<EntidadPlanta> asoc = new ArrayList<>();
		if(EntidadPlanta.getLimitEnd() == 0) {
			EntidadPlanta.setLimitInit(0);
			EntidadPlanta.setLimitEnd(10);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper  mapper = session.getMapper(EntidadPlantaMapper.class);
			asoc =  mapper.selectporentidad(EntidadPlanta);
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
	 * @Elaborado_Por: Jose Viscaya
	 * @param codEntidad
	 * @return
	 * @Fecha :Feb 22, 2019
	 * EntidadPlantaService.java
	 */
	public List<EntidadPlanta> getEntidadPlantaEntidad(long codEntidad){
		List<EntidadPlanta> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper  mapper = session.getMapper(EntidadPlantaMapper.class);
			asoc =  mapper.selectExistPlantas(codEntidad);
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
	public EntidadPlanta getEntidadPlantaById(long id){
		EntidadPlanta asoc = new EntidadPlanta();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper  mapper = session.getMapper(EntidadPlantaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc !=null){
				return asoc;
			}else{
				return new EntidadPlanta();
			}
		}catch(Exception ex){
		
			return new EntidadPlanta();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param EntidadPlanta
	 * @return
	 */
	public List<EntidadPlantaExt> getClasificacionPlantaByEntidad(EntidadPlantaExt EntidadPlanta){
		List<EntidadPlantaExt> asoc = new ArrayList<>();
		if(EntidadPlanta.getLimitEnd() == 0) {
			EntidadPlanta.setLimitInit(0);
			EntidadPlanta.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper  mapper = session.getMapper(EntidadPlantaMapper.class);
			asoc =  mapper.selectClasificacionPlantaByEntidad(EntidadPlanta);
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
	 * @param EntidadPlanta
	 * @return
	 */
	public List<EntidadPlantaExt> getClasePlantaByEntidad(EntidadPlantaExt EntidadPlanta){
		List<EntidadPlantaExt> asoc = new ArrayList<>();
		if(EntidadPlanta.getLimitEnd() == 0) {
			EntidadPlanta.setLimitInit(0);
			EntidadPlanta.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper  mapper = session.getMapper(EntidadPlantaMapper.class);
			asoc =  mapper.selectClasePlantaByEntidad(EntidadPlanta);
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
	 * Metodo para retornar una lista de datos de EntidadPlanta por filtro
	 * @return List<EntidadPlantaExt>
	 */
	public List<EntidadPlantaExt> getEntidadPlantaFilter(EntidadPlantaExt EntidadPlantaExt) {
		List<EntidadPlantaExt> asoc = new ArrayList<>();
		if(EntidadPlantaExt.getLimitEnd() == 0) {
			EntidadPlantaExt.setLimitInit(0);
			EntidadPlantaExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper mapper = session.getMapper(EntidadPlantaMapper.class);
			asoc = mapper.selectEntidadPlantaFilter(EntidadPlantaExt);
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
	/**
	 * 
	 * @param entidadPlanta
	 * @return
	 */
	public EntidadPlanta getEntidadPlantaFilterId(EntidadPlanta entidadPlanta) {
		EntidadPlanta asoc = new EntidadPlanta();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper mapper = session.getMapper(EntidadPlantaMapper.class);
			asoc = mapper.selectEntidadPlantaFilterId(entidadPlanta);
			if (asoc != null) {
				return asoc;
			} else {
				return  new EntidadPlanta();
			}
		} catch (Exception ex) {
			return  new EntidadPlanta();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metodo que retorna las planta existententes para una entidad, exceptuando aquellas que son de tipo UTL
	 * @return List<EntidadPlantaExt>
	 */
	public List<EntidadPlantaExt> getselectEntidadPlantaExceptoUTL(EntidadPlantaExt EntidadPlantaExt) {
		List<EntidadPlantaExt> asoc = new ArrayList<>();
		if(EntidadPlantaExt.getLimitEnd() == 0) {
			EntidadPlantaExt.setLimitInit(0);
			EntidadPlantaExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaMapper mapper = session.getMapper(EntidadPlantaMapper.class);
			asoc = mapper.selectEntidadPlantaExceptoUTL(EntidadPlantaExt);
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
	
}
