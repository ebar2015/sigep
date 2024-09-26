/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ExperienciaDocente;
import co.gov.dafp.sigep2.bean.ExperienciaDocenteExample;
import co.gov.dafp.sigep2.bean.ExperienciaProfesional;
import co.gov.dafp.sigep2.bean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.bean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ExperienciaDocenteMapper;
import co.gov.dafp.sigep2.interfaces.ExperienciaProfesionalMapper;
import co.gov.dafp.sigep2.utils.ErrorMensajes;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ExperienciaDocente
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class ExperienciaDocenteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6415789501753787159L;
	
	/**
	 * 
	 * @param ExperienciaDocente
	 * @return
	 */
	public ExperienciaDocente insertExperienciaDocente (ExperienciaDocente experienciaDocente){
		ExperienciaDocente expd = new ExperienciaDocente();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper  mapper = session.getMapper(ExperienciaDocenteMapper.class);
			id =  mapper.insert(experienciaDocente);
			if(id > 0){
				expd.setError(false);
				expd.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				expd.setError(true);
				expd.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return expd;
		}catch (Exception ex) {
			expd.setError(true);
			expd.setMensaje(UtilsConstantes.MSG_EXEPCION);
			expd.setMensajeTecnico(ex.getMessage());
			return expd;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param ExperienciaDocente
	 * @return
	 */
	public ExperienciaDocente updateExperienciaDocente(ExperienciaDocente experienciaDocente){
		ExperienciaDocente expd = new ExperienciaDocente();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper  mapper = session.getMapper(ExperienciaDocenteMapper.class);
			id = mapper.updateByPrimaryKey(experienciaDocente);
			session.commit();
			if(id > 0){
				expd.setError(false);
				expd.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				expd.setError(false);
				expd.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return expd;
		}catch (Exception ex) {
			expd.setError(true);
			expd.setMensaje(UtilsConstantes.MSG_EXEPCION);
			expd.setMensajeTecnico(ex.getMessage());
			return expd;	
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
	public List<ExperienciaDocente> getExperienciaDocenteByAll(int limitIni, int limitEnd){
		List<ExperienciaDocente> asoc = new ArrayList<>();
		ExperienciaDocenteExample dtoObject = new ExperienciaDocenteExample();
		if(limitEnd > 1 ) {
			dtoObject.setLimitInit(limitIni);
			dtoObject.setLimitEnd(limitEnd);
		}else {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper  mapper = session.getMapper(ExperienciaDocenteMapper.class);
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
	public List<ExperienciaDocente> getExperienciaDocenteById(long id){
		List<ExperienciaDocente> asoc = new ArrayList<>();
		ExperienciaDocenteExample dtoObject = new ExperienciaDocenteExample();
		dtoObject.createCriteria().andCodExperienciaDocenteEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper  mapper = session.getMapper(ExperienciaDocenteMapper.class);
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
	public ExperienciaDocenteExt getExpericiado(long id){
		ExperienciaDocenteExt asoc = new ExperienciaDocenteExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper  mapper = session.getMapper(ExperienciaDocenteMapper.class);
			asoc =  mapper.selectByExpericiadoc(id);
			if(asoc != null){
				return asoc;
			}else{
				return new ExperienciaDocenteExt();
			}
		}catch(Exception ex){
			return new ExperienciaDocenteExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param codPersona
	 * @return
	 */
	public int getDifdias(long codPersona){
		ExperienciaDocenteExt asoc = new ExperienciaDocenteExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper  mapper = session.getMapper(ExperienciaDocenteMapper.class);
			asoc =  mapper.difDias(codPersona);
			return asoc.getTotal();
		}catch(Exception ex){
			return 0;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param experienciaDocente
	 * @return
	 */
	public List<ExperienciaDocenteExt> getExperienCiaDocenteCalculo(ExperienciaDocente experienciaDocente) {
		List<ExperienciaDocenteExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper mapper = session.getMapper(ExperienciaDocenteMapper.class);
			asoc = mapper.selectExperienciaDocenteCalculo(experienciaDocente);
			if (asoc != null) {
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
	
	public List<ExperienciaDocenteExt> geExperienciaDocenteBycodPer(ExperienciaDocente experienciaDocente){
		List<ExperienciaDocenteExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper  mapper = session.getMapper(ExperienciaDocenteMapper.class);
			asoc =  mapper.selectByExpericiadocporpersona(experienciaDocente);
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
	 * @fecha 10:38:22 a.m. 2018
	 * @param experienciaDocente
	 * @return
	 */
	public ErrorMensajes updateExperienciaDocenteSelective(ExperienciaDocente experienciaDocente){
		ErrorMensajes expd = new ErrorMensajes();
		ExperienciaDocenteExample dtoObject = new ExperienciaDocenteExample();
		dtoObject.createCriteria().andCodExperienciaDocenteEqualTo(experienciaDocente.getCodExperienciaDocente());
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaDocenteMapper  mapper = session.getMapper(ExperienciaDocenteMapper.class);
			id = mapper.updateByPrimaryKeySelective(experienciaDocente);
			session.commit();
			if(id > 0){
				expd.setError(false);
				expd.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				expd.setError(false);
				expd.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return expd;
		}catch (Exception ex) {
			expd.setError(true);
			expd.setMensaje(UtilsConstantes.MSG_EXEPCION);
			expd.setMensajeTecnico(ex.getMessage());
			return expd;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
