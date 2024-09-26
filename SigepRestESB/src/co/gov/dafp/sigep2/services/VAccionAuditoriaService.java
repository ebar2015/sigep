package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.VAccionAuditoria;
import co.gov.dafp.sigep2.bean.VAccionAuditoriaExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.VAccionAuditoriaMapper;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla VAccionAuditoriaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class VAccionAuditoriaService implements Serializable {
	private static final long serialVersionUID = -1124917520434040176L;
	
	
	/**
	 * 
	 * @return List<VAccionAuditoria>
	 */
	public List<VAccionAuditoria> getVAccionAuditoria(){
		List<VAccionAuditoria> asoc = new ArrayList<>();
		VAccionAuditoriaExample dtoObject = new VAccionAuditoriaExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VAccionAuditoriaMapper  mapper = session.getMapper(VAccionAuditoriaMapper.class);
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
	public VAccionAuditoria getVAccionAuditoriaSigla(BigDecimal sigla){
		List<VAccionAuditoria> asoc = new ArrayList<>();
		VAccionAuditoriaExample dtoObject = new VAccionAuditoriaExample();
		if(sigla!=null) {
			dtoObject.createCriteria().andAccionAuditoriaIdEqualTo(sigla);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VAccionAuditoriaMapper  mapper = session.getMapper(VAccionAuditoriaMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new VAccionAuditoria();
			}
		}catch(Exception ex){
		
			return new VAccionAuditoria();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
