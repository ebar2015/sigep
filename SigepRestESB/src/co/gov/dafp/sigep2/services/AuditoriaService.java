/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Auditoria;
import co.gov.dafp.sigep2.bean.AuditoriaExample;
import co.gov.dafp.sigep2.bean.ext.AuditoriaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.AuditoriaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Auditoria
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class AuditoriaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7677533068543990261L;
	
	/**
	 * 
	 * @param Auditoria
	 * @return
	 */
	public Auditoria insertAuditoria (Auditoria auditoria){
		Auditoria audi = new Auditoria();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaMapper  mapper = session.getMapper(AuditoriaMapper.class);
			id =  mapper.insert(auditoria);
			if(id > 0){
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return audi;
		}catch (Exception ex) {
			audi.setError(true);
			audi.setMensaje(UtilsConstantes.MSG_EXEPCION);
			audi.setMensajeTecnico(ex.getMessage());
			return audi;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Auditoria
	 * @return
	 */
	public Auditoria updateAuditoria(Auditoria auditoria){
		Auditoria audi = new Auditoria();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaMapper  mapper = session.getMapper(AuditoriaMapper.class);
			id = mapper.updateByPrimaryKey(auditoria);
			session.commit();
			if(id > 0){
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return audi;
		}catch (Exception ex) {
			audi.setError(true);
			audi.setMensaje(UtilsConstantes.MSG_EXEPCION);
			audi.setMensajeTecnico(ex.getMessage());
			return audi;	
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
	public List<Auditoria> getAuditoriaByAll(int limitIni, int limitEnd){
		List<Auditoria> asoc = new ArrayList<>();
		AuditoriaExample dtoObject = new AuditoriaExample();
		if(limitEnd > 1 ) {
			dtoObject.setLimitInit(limitIni);
			dtoObject.setLimitEnd(limitEnd);
		}else {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaMapper  mapper = session.getMapper(AuditoriaMapper.class);
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
	public List<Auditoria> getAuditoriaById(long id){
		List<Auditoria> asoc = new ArrayList<>();
		AuditoriaExample dtoObject = new AuditoriaExample();
		dtoObject.createCriteria().andCodAuditoriaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaMapper  mapper = session.getMapper(AuditoriaMapper.class);
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
	public List<AuditoriaExt> getAuditoriaParametro(AuditoriaExt auditoriaExt){
		List<AuditoriaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaMapper  mapper = session.getMapper(AuditoriaMapper.class);
			asoc =  mapper.selectParametro(auditoriaExt);
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
	public List<AuditoriaExt> getAuditoriaFechas(AuditoriaExt auditoriaExt){
		List<AuditoriaExt> asoc = new ArrayList<>();
		if(auditoriaExt.getLimitEnd() == 0) {
			auditoriaExt.setLimitEnd(10);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaMapper  mapper = session.getMapper(AuditoriaMapper.class);
			asoc =  mapper.selectFechas(auditoriaExt);
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
	 * Servicio que consulta por cualquier campo de la tabla AUDITORIA
	 * @param auditoriaExt
	 * @return List<AuditoriaExt>
	 */
	public List<AuditoriaExt> getAuditoriaByFilter(AuditoriaExt auditoriaExt){
		List<AuditoriaExt> asoc = new ArrayList<>();
		if(auditoriaExt.getLimitEnd() == 0) {
			auditoriaExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AuditoriaMapper  mapper = session.getMapper(AuditoriaMapper.class);
			asoc =  mapper.selectAuditoriaByFilter(auditoriaExt);
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
	
	public final static void  insertarAuditoriaConsulta(String nombreTabla, BigDecimal codUsuario, BigDecimal codRol ){
		SqlSession session = null;
		session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
		try{
			AuditoriaMapper  mapper = session.getMapper(AuditoriaMapper.class);
			Auditoria aud = new Auditoria();
			aud.setNombreTabla(nombreTabla);
			aud.setAudCodUsuario(codUsuario);
			aud.setAudCodRol(codRol);
			mapper.insertAuditoriaConsulta(aud);
		}catch(Exception ex){
			System.out.println();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
