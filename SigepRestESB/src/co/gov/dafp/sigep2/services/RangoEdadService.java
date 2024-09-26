/**
 * @Author: Jose Viscaya
 * @Date  : May 7, 2019, 3:51:22 PM
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PreguntaOpinion;
import co.gov.dafp.sigep2.bean.PreguntaOpinionExample;
import co.gov.dafp.sigep2.bean.RangoEdad;
import co.gov.dafp.sigep2.bean.RangoEdadExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PreguntaOpinionMapper;
import co.gov.dafp.sigep2.interfaces.RangoEdadMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author: Jose Viscaya
 * @Date  : May 7, 2019, 3:51:22 PM
 */
public class RangoEdadService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8099713903121990345L;
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 3:53:04 PM
	 * @File:   RangoEdadService.java
	 * @param RangoEdad
	 * @return
	 */
	public RangoEdad insertRangoEdad (RangoEdad RangoEdad){
		RangoEdad fest = new RangoEdad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RangoEdadMapper  mapper = session.getMapper(RangoEdadMapper.class);
			id =  mapper.insert(RangoEdad);
			if(id > 0){
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return fest;
		}catch (Exception ex) {
			fest.setError(true);
			fest.setMensaje(UtilsConstantes.MSG_EXEPCION);
			fest.setMensajeTecnico(ex.getMessage());
			return fest;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 3:53:09 PM
	 * @File:   RangoEdadService.java
	 * @param RangoEdad
	 * @return
	 */
	public RangoEdad updateRangoEdad(RangoEdad RangoEdad){
		RangoEdad fest = new RangoEdad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RangoEdadMapper  mapper = session.getMapper(RangoEdadMapper.class);
			id = mapper.updateByPrimaryKey(RangoEdad);
			if(id > 0){
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return fest;
		}catch (Exception ex) {
			fest.setError(true);
			fest.setMensaje(UtilsConstantes.MSG_EXEPCION);
			fest.setMensajeTecnico(ex.getMessage());
			return fest;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 9:20:30 AM
	 * @File:   RangoEdadService.java
	 * @param RangoEdad
	 * @return
	 */
	public List<RangoEdad> getRangoEdadByFiltro(RangoEdad rangoEdad){
		List<RangoEdad> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RangoEdadMapper  mapper = session.getMapper(RangoEdadMapper.class);
			asoc =  mapper.selectByFiltro(rangoEdad);
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
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 3:45:24 PM
	 * @File:   RangoEdadService.java
	 * @param RangoEdad
	 * @return
	 */
	public RangoEdad getRangoEdadByID(RangoEdad rangoEdad){
		RangoEdad asoc = new RangoEdad();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RangoEdadMapper  mapper = session.getMapper(RangoEdadMapper.class);
			asoc =  mapper.selectByPrimaryKey(rangoEdad.getCodRangoEdad());
			return asoc;
		}catch(Exception ex){
			return new RangoEdad();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Verifica si ya existen un rango con esas especificaciones.
	 * @param rangoEdad
	 * @return
	 */
	
	public List<RangoEdad> getRangoEdadDuplicado(RangoEdad rangoEdad){
		List<RangoEdad> asoc = new ArrayList<>();
		RangoEdadExample dtoObject = new RangoEdadExample();
		dtoObject.createCriteria().andNombreRangoEqualTo(rangoEdad.getNombreRango()).
		andLimiteInferiorEqualTo(rangoEdad.getLimiteInferior()).andLimiteSuperiorEqualTo(rangoEdad.getLimiteSuperior());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RangoEdadMapper  mapper = session.getMapper(RangoEdadMapper.class);
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
	
}
