package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PepPreguntasCuestionarioDetalle;
import co.gov.dafp.sigep2.bean.PepPreguntasCuestionarioDetalleExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PepPreguntasCuestionarioDetalleMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PepPreguntasCuestionarioDetalleService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PepPreguntasCuestionarioDetalleService implements Serializable {
	private static final long serialVersionUID = 7464386240687960861L;
	
	/**
	 * 
	 * @param PepPreguntasCuestionarioDetalle
	 * @return
	 */
	public PepPreguntasCuestionarioDetalle insertPepPreguntasCuestionarioDetalle (PepPreguntasCuestionarioDetalle PepPreguntasCuestionarioDetalle){
		PepPreguntasCuestionarioDetalle acre = new PepPreguntasCuestionarioDetalle();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioDetalleMapper  mapper = session.getMapper(PepPreguntasCuestionarioDetalleMapper.class);
			id =  mapper.insert(PepPreguntasCuestionarioDetalle);
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
	 * @param PepPreguntasCuestionarioDetalle
	 * @return
	 */
	public PepPreguntasCuestionarioDetalle updatePepPreguntasCuestionarioDetalle(PepPreguntasCuestionarioDetalle PepPreguntasCuestionarioDetalle){
		PepPreguntasCuestionarioDetalle acre = new PepPreguntasCuestionarioDetalle();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioDetalleMapper  mapper = session.getMapper(PepPreguntasCuestionarioDetalleMapper.class);
			id = mapper.updateByPrimaryKey(PepPreguntasCuestionarioDetalle);
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
	public List<PepPreguntasCuestionarioDetalle> getPepPreguntasCuestionarioDetalleAll(PepPreguntasCuestionarioDetalle pepPreguntasCuestionarioDetalle){
		List<PepPreguntasCuestionarioDetalle> asoc = new ArrayList<>();
		PepPreguntasCuestionarioDetalleExample dtoObject = new PepPreguntasCuestionarioDetalleExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioDetalleMapper  mapper = session.getMapper(PepPreguntasCuestionarioDetalleMapper.class);
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
	public PepPreguntasCuestionarioDetalle getPepPreguntasCuestionarioDetalleById(long id){
		PepPreguntasCuestionarioDetalle asoc = new PepPreguntasCuestionarioDetalle();
		PepPreguntasCuestionarioDetalleExample dtoObject = new PepPreguntasCuestionarioDetalleExample();
		dtoObject.createCriteria().andIdPreguntaCuestionarioDetalleEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioDetalleMapper  mapper = session.getMapper(PepPreguntasCuestionarioDetalleMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new PepPreguntasCuestionarioDetalle();
			}
		}catch(Exception ex){
		
			return new PepPreguntasCuestionarioDetalle();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 1/08/2018 3:38:03 p. m.
	 * @param pepPreguntasCuestionarioDetalle
	 * @return
	 */
	public List<PepPreguntasCuestionarioDetalle> getPepPreguntasCuestionarioDetalleFiltro(PepPreguntasCuestionarioDetalle pepPreguntasCuestionarioDetalle){
		List<PepPreguntasCuestionarioDetalle> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepPreguntasCuestionarioDetalleMapper  mapper = session.getMapper(PepPreguntasCuestionarioDetalleMapper.class);
			asoc =  mapper.selectByFiltro(pepPreguntasCuestionarioDetalle);
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
