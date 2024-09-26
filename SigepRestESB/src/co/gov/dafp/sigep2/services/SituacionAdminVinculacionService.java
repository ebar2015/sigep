package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.SituacionAdminVinculacion;
import co.gov.dafp.sigep2.bean.SituacionAdminVinculacionExample;
import co.gov.dafp.sigep2.bean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.SituacionAdminVinculacionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla SituacionAdminVinculacionService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @fecha 27/07/2018 2:56:17 p. m.
 */
public class SituacionAdminVinculacionService implements Serializable {

	private static final long serialVersionUID = -3619171587052090392L;
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:56:33 p. m.
	 * @param situacionAdminVinculacion
	 * @return
	 */
	public SituacionAdminVinculacion insertSituacionAdminVinculacion (SituacionAdminVinculacion situacionAdminVinculacion){
		SituacionAdminVinculacion respSA = new SituacionAdminVinculacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			id =  mapper.insert(situacionAdminVinculacion);
			if(id > 0){
				session.commit();
				respSA.setError(false);
				respSA.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				situacionAdminVinculacion = getSituacionAdminVinculacion(situacionAdminVinculacion); 
				respSA.setCodSituacionAdminRelacionLaboral(situacionAdminVinculacion.getCodSituacionAdminRelacionLaboral());
			}else{
				respSA.setError(true);
				respSA.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return respSA;
		}catch (Exception ex) {
			respSA.setError(true);
			respSA.setMensaje(UtilsConstantes.MSG_EXEPCION);
			respSA.setMensajeTecnico(ex.getMessage());
			return respSA;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param SituacionAdminVinculacion
	 * @return
	 */
	public SituacionAdminVinculacion updateSituacionAdminVinculacion(SituacionAdminVinculacion situacionAdminVinculacion){
		SituacionAdminVinculacion bien = new SituacionAdminVinculacion();
		SituacionAdminVinculacionExample dtoObject = new SituacionAdminVinculacionExample();
		if(situacionAdminVinculacion!=null && situacionAdminVinculacion.getCodSituacionAdminRelacionLaboral()!=null) {
			dtoObject.createCriteria().andCodSituacionAdminRelacionLaboralEqualTo(situacionAdminVinculacion.getCodSituacionAdminRelacionLaboral());
		}
		
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			id = mapper.updateByExampleSelective(situacionAdminVinculacion, dtoObject);
			if(id > 0){
				if(situacionAdminVinculacion.getFlgActivo()!= null &&  situacionAdminVinculacion.getFlgActivo()==0) {
					updateSituacionOcupaTemporal(situacionAdminVinculacion);
				}
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
				bien.setCodSituacionAdminRelacionLaboral(situacionAdminVinculacion.getCodSituacionAdminRelacionLaboral());
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param SituacionAdminVinculacion
	 * @return
	 */
	public SituacionAdminVinculacion delSituacionAdminVinculacion( long codBienPatrimonio){
		SituacionAdminVinculacion bien = new SituacionAdminVinculacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			id = mapper.deleteByPrimaryKey(codBienPatrimonio);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_ELIMINACION_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_ELIMINADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;
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
	public SituacionAdminVinculacion getSituacionAdminVinculacionById(long id){
		SituacionAdminVinculacion asoc = new SituacionAdminVinculacion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new SituacionAdminVinculacion();
			}
		}catch(Exception ex){
		
			return new SituacionAdminVinculacion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param situacionAdminVinculacion
	 * @return
	 */
	public List<SituacionAdminVinculacionExt> getSituacionAdminVinculacion(SituacionAdminVinculacionExt situacionAdminVinculacion){
		List<SituacionAdminVinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			asoc =  mapper.selectSituacionVinculacion(situacionAdminVinculacion);
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
	 * Servicio que retorna lista de situaciones administrativas en encargo a las cuales esta asociada un cargo.
	 * @param situacionAdminVinculacion
	 * @return
	 */
	public List<SituacionAdminVinculacionExt> getCargoEnEncargo(SituacionAdminVinculacionExt situacionAdminVinculacion){
		List<SituacionAdminVinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			asoc =  mapper.getCargoEnEncargo(situacionAdminVinculacion);
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
	 * Servicio que desactiva todas las situaciones administrativas asociadas a una vinculacion especifica
	 * @author Maria Alejandra Colorado
	 * @param SituacionAdminVinculacionExt
	 * @return SituacionAdminVinculacionExt
	 */
	public SituacionAdminVinculacionExt updateSituacionPorVinculacion(SituacionAdminVinculacionExt situacion){
		SituacionAdminVinculacionExt param = new SituacionAdminVinculacionExt();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			id = mapper.updateSituacionPorVinculacion(situacion);
			if(id > 0){
				param.setError(false);
				param.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				param.setError(true);
				param.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return param;
		}catch (Exception ex) {
			param.setError(true);
			param.setMensaje(UtilsConstantes.MSG_EXEPCION);
			param.setMensajeTecnico(ex.getMessage());
			return param;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Servicio que devulve el registro de SituacionAdminVinculacion dependiendo de lo enviado
	 * @param entidadPlanta
	 * @return
	 */
	public SituacionAdminVinculacion getSituacionAdminVinculacion(SituacionAdminVinculacion situacionAdminVinculacion) {
		SituacionAdminVinculacion asoc = new SituacionAdminVinculacion();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			asoc = mapper.selectSituacionAdminVinculacion(situacionAdminVinculacion);
			if (asoc != null) {
				return asoc;
			} else {
				return  new SituacionAdminVinculacion();
			}
		} catch (Exception ex) {
			return  new SituacionAdminVinculacion();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Servicio borra la informacion de la columna cod_sa_ocupa_temporal de aquellas situaciones administrativas que poseen la SA eliminada
	 * @author Maria Alejandra Colorado
	 * @param SituacionAdminVinculacion
	 * @return SituacionAdminVinculacion
	 */
	public SituacionAdminVinculacion updateSituacionOcupaTemporal(SituacionAdminVinculacion situacion){
		SituacionAdminVinculacion param = new SituacionAdminVinculacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			id = mapper.updateSituacionOcupaTemporal(situacion);
			if(id > 0){
				param.setError(false);
				param.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				param.setError(true);
				param.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return param;
		}catch (Exception ex) {
			param.setError(true);
			param.setMensaje(UtilsConstantes.MSG_EXEPCION);
			param.setMensajeTecnico(ex.getMessage());
			return param;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Servicio que retorna objeto SituacionAdminVinculacion por codSAOcupaTemporal
	 * @param SituacionAdminVinculacion
	 * @return SituacionAdminVinculacion
	 */
	public SituacionAdminVinculacion getSituacionAdminVinculacionByCodSAOcupaTemporal(SituacionAdminVinculacion situacionAdminVinculacion){
		SituacionAdminVinculacion asoc = new SituacionAdminVinculacion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdminVinculacionMapper  mapper = session.getMapper(SituacionAdminVinculacionMapper.class);
			asoc =  mapper.SelectSituacionAdminVinculacionByCodSAOcupaTemporal(situacionAdminVinculacion.getCodSAOcupaTemporal().longValue());
			if(asoc != null){
				return asoc;
			}else{
				return new SituacionAdminVinculacion();
			}
		}catch(Exception ex){
		
			return new SituacionAdminVinculacion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
