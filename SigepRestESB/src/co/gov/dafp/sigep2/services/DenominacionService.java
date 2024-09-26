/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Denominacion;
import co.gov.dafp.sigep2.bean.ext.DenominacionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DenominacionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author joseviscaya
 *
 */
public class DenominacionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5824803813018910678L;
	
	/**
	 * 
	 * @param Denominacion
	 * @return
	 */
	public Denominacion insertDenominacion (Denominacion denominacion){
		Denominacion acre = new Denominacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DenominacionMapper  mapper = session.getMapper(DenominacionMapper.class);
			id =  mapper.insert(denominacion);
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
	 * @param Denominacion
	 * @return
	 */
	public Denominacion updateDenominacion(Denominacion denominacion){
		Denominacion acre = new Denominacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DenominacionMapper  mapper = session.getMapper(DenominacionMapper.class);
			id = mapper.updateByPrimaryKey(denominacion);
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
	public List<Denominacion> getDenominacionFiltro(Denominacion denominacion){
		List<Denominacion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DenominacionMapper  mapper = session.getMapper(DenominacionMapper.class);
			asoc =  mapper.selectByFiltro(denominacion);
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
	public Denominacion getDenominacionById(BigDecimal id){
		Denominacion asoc = new Denominacion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DenominacionMapper  mapper = session.getMapper(DenominacionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new Denominacion();
			}
		}catch(Exception ex){
		
			return new Denominacion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author: Maria Alejandra C 
	 * @fecha 10/06/2019 11:57:26 a. m.
	 * @param Denominacion
	 * @return List<Denominacion>
	 */
	public List<Denominacion> getDenominacionDuplicado(Denominacion denominacion){
		List<Denominacion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DenominacionMapper  mapper = session.getMapper(DenominacionMapper.class);
			asoc =  mapper.selectDenominacionDuplicado(denominacion);
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
	 * Metdodo para consultar las denominaciones segun la nomenclatura y nivel enviado
	 * @return List<EntidadPlantaDetalle>
	 */
	public List<DenominacionExt> getDenominacionPorNivelNomenclatura(DenominacionExt DenominacionExt) {
		List<DenominacionExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DenominacionMapper mapper = session.getMapper(DenominacionMapper.class);
			asoc = mapper.selectDenominacionPorNivelNomenclatura(DenominacionExt);
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
	 * Metdodo para consultar las denominaciones segun la entidad enviada
	 * @return List<EntidadPlantaDetalle>
	 */
	public List<DenominacionExt> getDenominacionXEntidad(DenominacionExt DenominacionExt) {
		List<DenominacionExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DenominacionMapper mapper = session.getMapper(DenominacionMapper.class);
			asoc = mapper.selectDenominacionNomenclaturaXEntidad(DenominacionExt);
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
