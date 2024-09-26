/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Autonomia;
import co.gov.dafp.sigep2.bean.AutonomiaExample;
import co.gov.dafp.sigep2.bean.ext.AutonomiaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.AutonomiaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya
 * 4 ene. 2019
 */
public class AutonomiaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9083690916105824543L;
	
	/**
	 * 
	 * @param Autonomia
	 * @return
	 */
	public Autonomia insertAutonomia (Autonomia Autonomia){
		Autonomia acre = new Autonomia();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AutonomiaMapper  mapper = session.getMapper(AutonomiaMapper.class);
			id =  mapper.insert(Autonomia);
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
	 * @param Autonomia
	 * @return
	 */
	public Autonomia updateAutonomia(Autonomia autonomia){
		Autonomia acre = new Autonomia();
		AutonomiaExample dtoObject = new AutonomiaExample();
		if(autonomia!=null && autonomia.getCodEntidad()!=null && autonomia.getCodTipoAutonomia()!=null) {
			dtoObject.createCriteria().andCodEntidadEqualTo(autonomia.getCodEntidad()).
			andCodTipoAutonomiaEqualTo(autonomia.getCodTipoAutonomia());
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AutonomiaMapper  mapper = session.getMapper(AutonomiaMapper.class);
			id = mapper.updateByExample(autonomia, dtoObject);
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
	 * Elaborado por:
	 * Jose Viscaya 4 ene. 2019
	 * @param Autonomia
	 * @return
	 */
	public List<AutonomiaExt> getAutonomiaCodEntidadFlg(Autonomia autonomia){
		List<AutonomiaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AutonomiaMapper  mapper = session.getMapper(AutonomiaMapper.class);
			asoc =  mapper.selectByfiltro(autonomia);
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
	
	public AutonomiaExt getAutonomiaCodEntidad(Autonomia autonomia){
		List<AutonomiaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AutonomiaMapper  mapper = session.getMapper(AutonomiaMapper.class);
			asoc =  mapper.selectByfiltro(autonomia);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return null;
			}
		}catch(Exception ex){
			return null;
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
	public Autonomia getAutonomiaById(BigDecimal id){
		Autonomia asoc = new Autonomia();
		AutonomiaExample dtoObject = new AutonomiaExample();
		dtoObject.createCriteria().andCodAutonomiaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AutonomiaMapper  mapper = session.getMapper(AutonomiaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new Autonomia();
			}
		}catch(Exception ex){
		
			return new Autonomia();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
