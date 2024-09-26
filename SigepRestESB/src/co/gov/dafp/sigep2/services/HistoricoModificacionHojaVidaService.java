/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.bean.HistoricoModificacionHojaVidaExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.HistoricoModificacionHojaVidaMapper;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla HistoricoModificacionHojaVida
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class HistoricoModificacionHojaVidaService implements Serializable{

	private static final long serialVersionUID = -3845741959646005960L;
	
	public boolean insertHistoricoModificacionHojaVida (HistoricoModificacionHojaVida historicoModificacionHojaVida){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoModificacionHojaVidaMapper  mapper = session.getMapper(HistoricoModificacionHojaVidaMapper.class);
			id =  mapper.insert(historicoModificacionHojaVida);
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
	 * @param HistoricoModificacionHojaVida
	 * @return
	 */
	public boolean updateHistoricoModificacionHojaVida(HistoricoModificacionHojaVida historicoModificacionHojaVida){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoModificacionHojaVidaMapper  mapper = session.getMapper(HistoricoModificacionHojaVidaMapper.class);
			id = mapper.updateByPrimaryKey(historicoModificacionHojaVida);
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
	public List<HistoricoModificacionHojaVida> getHistoricoModificacionHojaVidaByAll(){
		List<HistoricoModificacionHojaVida> asoc = new ArrayList<>();
		HistoricoModificacionHojaVidaExample dtoObject = new HistoricoModificacionHojaVidaExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoModificacionHojaVidaMapper  mapper = session.getMapper(HistoricoModificacionHojaVidaMapper.class);
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
	public List<HistoricoModificacionHojaVida> getHistoricoModificacionHojaVidaPersona(BigDecimal id){
		List<HistoricoModificacionHojaVida> asoc = new ArrayList<>();
		HistoricoModificacionHojaVidaExample dtoObject = new HistoricoModificacionHojaVidaExample();
		dtoObject.createCriteria().andCodHojaVidaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoModificacionHojaVidaMapper  mapper = session.getMapper(HistoricoModificacionHojaVidaMapper.class);
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
	 * Servicio que retorna lista de HistoricoModificacionHojaVida de acuerdo a filtro enviado
	 */
	public List<HistoricoModificacionHojaVida> getHistoricoModificacionHojaVidaByFiltro(HistoricoModificacionHojaVida historicoModificacionHojaVida){
		List<HistoricoModificacionHojaVida> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoModificacionHojaVidaMapper  mapper = session.getMapper(HistoricoModificacionHojaVidaMapper.class);
			asoc =  mapper.selectByFiltro(historicoModificacionHojaVida);
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
