/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.OtrosIngresosDeclaracion;
import co.gov.dafp.sigep2.bean.OtrosIngresosDeclaracionExample;
import co.gov.dafp.sigep2.bean.ext.OtrosIngresosDeclaracionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.OtrosIngresosDeclaracionMapper;
/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla OtrosIngresosDeclaracionService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class OtrosIngresosDeclaracionService implements Serializable {
	private static final long serialVersionUID = 7437098611195253494L;
	/**
	 * 
	 * @param OtrosIngresosDeclaracion
	 * @return
	 */
	public boolean insertOtrosIngresosDeclaracion (OtrosIngresosDeclaracion otrosIngresosDeclaracion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtrosIngresosDeclaracionMapper  mapper = session.getMapper(OtrosIngresosDeclaracionMapper.class);
			id =  mapper.insert(otrosIngresosDeclaracion);
			if(id > 0){
				session.commit();
				return true;
			}else{
				return false;
			}
		}catch (Exception ex) {
			return false;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param OtrosIngresosDeclaracion
	 * @return
	 */
	public boolean updateOtrosIngresosDeclaracion(OtrosIngresosDeclaracion otrosIngresosDeclaracion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtrosIngresosDeclaracionMapper  mapper = session.getMapper(OtrosIngresosDeclaracionMapper.class);
			id = mapper.updateByPrimaryKey(otrosIngresosDeclaracion);
			session.commit();
			return (id==1)?true:false;
		}catch (Exception ex) {
			return false;
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
	public List<OtrosIngresosDeclaracionExt> getOtrosIngresosDeclaracion(OtrosIngresosDeclaracion otrosIngresosDeclaracion){
		List<OtrosIngresosDeclaracionExt> asoc = new ArrayList<>();
		OtrosIngresosDeclaracionExample dtoObject = new OtrosIngresosDeclaracionExample();
		if(otrosIngresosDeclaracion!=null && otrosIngresosDeclaracion.getCodDeclaracion()!=null) {
			dtoObject.createCriteria().andCodDeclaracionEqualTo(otrosIngresosDeclaracion.getCodDeclaracion()).
			andFlgActivoEqualTo(otrosIngresosDeclaracion.getFlgActivo());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtrosIngresosDeclaracionMapper  mapper = session.getMapper(OtrosIngresosDeclaracionMapper.class);
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
	public OtrosIngresosDeclaracion getOtrosIngresosDeclaracionById(BigDecimal id){
		OtrosIngresosDeclaracion asoc = new OtrosIngresosDeclaracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtrosIngresosDeclaracionMapper  mapper = session.getMapper(OtrosIngresosDeclaracionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new OtrosIngresosDeclaracion();
			}
		}catch(Exception ex){
		
			return new OtrosIngresosDeclaracion();
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
	public OtrosIngresosDeclaracion getSumaOtrosIngresos(BigDecimal id){
		OtrosIngresosDeclaracion asoc = new OtrosIngresosDeclaracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			OtrosIngresosDeclaracionMapper  mapper = session.getMapper(OtrosIngresosDeclaracionMapper.class);
			asoc =  mapper.selectOtrosIngresoSuma(id);
			if(asoc != null){
				if(asoc.getValor() == null) {
					asoc.setValor(new BigDecimal(0));
				}
				return asoc;
			}else{
				asoc = new OtrosIngresosDeclaracion();
				asoc.setValor(new BigDecimal(0));
				return asoc;
			}
		}catch(Exception ex){
			asoc = new OtrosIngresosDeclaracion();
			asoc.setValor(new BigDecimal(0));
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
