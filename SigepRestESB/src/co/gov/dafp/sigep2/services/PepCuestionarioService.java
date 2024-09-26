package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PepCuestionario;
import co.gov.dafp.sigep2.bean.PepCuestionarioExample;
import co.gov.dafp.sigep2.bean.ext.PepCuestionarioExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PepCuestionarioMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PepCuestionarioService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @fecha 27/07/2018 2:44:46 p. m.
 */
public class PepCuestionarioService implements Serializable{
	private static final long serialVersionUID = -8071342759755608245L;
	/**
	 * 
	 * @param PepCuestionario
	 * @return
	 */
	public PepCuestionario insertPepCuestionario (PepCuestionario pepCuestionario){
		PepCuestionario acre = new PepCuestionario();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepCuestionarioMapper  mapper = session.getMapper(PepCuestionarioMapper.class);
			id =  mapper.insert(pepCuestionario);
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
	 * @param PepCuestionario
	 * @return
	 */
	public PepCuestionario updatePepCuestionario(PepCuestionario pepCuestionario){
		PepCuestionario acre = new PepCuestionario();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepCuestionarioMapper  mapper = session.getMapper(PepCuestionarioMapper.class);
			id = mapper.updateByPrimaryKey(pepCuestionario);
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
	public List<PepCuestionario> getPepCuestionarioAll(PepCuestionario pepCuestionario){
		List<PepCuestionario> asoc = new ArrayList<>();
		PepCuestionarioExample dtoObject = new PepCuestionarioExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepCuestionarioMapper  mapper = session.getMapper(PepCuestionarioMapper.class);
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
	public PepCuestionario getPepCuestionarioById(long id){
		PepCuestionario asoc = new PepCuestionario();
		PepCuestionarioExample dtoObject = new PepCuestionarioExample();
		dtoObject.createCriteria().andIdCuestionarioEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepCuestionarioMapper  mapper = session.getMapper(PepCuestionarioMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new PepCuestionario();
			}
		}catch(Exception ex){
		
			return new PepCuestionario();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	public List<PepCuestionarioExt> getPepCuestionarioExt(){
		List<PepCuestionarioExt>asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepCuestionarioMapper  mapper = session.getMapper(PepCuestionarioMapper.class);
			asoc =  mapper.selectCuestionarioNombre();
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
