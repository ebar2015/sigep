package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PepPreguntasCuestionario;
import co.gov.dafp.sigep2.bean.PepPreguntasCuestionarioExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PepPreguntasCuestionarioMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PepPreguntasCuestionarioService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PepPreguntasCuestionarioService implements Serializable {

	private static final long serialVersionUID = -1763561575192481509L;
	
	/**
	 * @param PepPreguntasCuestionario
	 * @return
	 */
	public PepPreguntasCuestionario insertPepPreguntasCuestionario (PepPreguntasCuestionario pepPreguntasCuestionario){
		PepPreguntasCuestionario acre = new PepPreguntasCuestionario();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioMapper  mapper = session.getMapper(PepPreguntasCuestionarioMapper.class);
			id =  mapper.insert(pepPreguntasCuestionario);
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
	 * @param PepPreguntasCuestionario
	 * @return
	 */
	public PepPreguntasCuestionario updatePepPreguntasCuestionario(PepPreguntasCuestionario pepPreguntasCuestionario){
		PepPreguntasCuestionario acre = new PepPreguntasCuestionario();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioMapper  mapper = session.getMapper(PepPreguntasCuestionarioMapper.class);
			id = mapper.updateByPrimaryKey(pepPreguntasCuestionario);
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
	public List<PepPreguntasCuestionario> getPepPreguntasCuestionarioAll(PepPreguntasCuestionario pepPreguntasCuestionario){
		List<PepPreguntasCuestionario> asoc = new ArrayList<>();
		PepPreguntasCuestionarioExample dtoObject = new PepPreguntasCuestionarioExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioMapper  mapper = session.getMapper(PepPreguntasCuestionarioMapper.class);
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
	public PepPreguntasCuestionario getPepPreguntasCuestionarioById(long id){
		PepPreguntasCuestionario asoc = new PepPreguntasCuestionario();
		PepPreguntasCuestionarioExample dtoObject = new PepPreguntasCuestionarioExample();
		dtoObject.createCriteria().andIdCuestionarioEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioMapper  mapper = session.getMapper(PepPreguntasCuestionarioMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new PepPreguntasCuestionario();
			}
		}catch(Exception ex){
		
			return new PepPreguntasCuestionario();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 1/08/2018 3:38:59 p. m.
	 * @param pepPreguntasCuestionario
	 * @return
	 */
	public List<PepPreguntasCuestionario> getPepPreguntasCuestionarioFiltro(PepPreguntasCuestionario pepPreguntasCuestionario){
		List<PepPreguntasCuestionario> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioMapper  mapper = session.getMapper(PepPreguntasCuestionarioMapper.class);
			asoc =  mapper.selectByFiltro(pepPreguntasCuestionario);
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
