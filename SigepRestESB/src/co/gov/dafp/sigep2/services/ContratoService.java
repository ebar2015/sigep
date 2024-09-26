/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Contrato;
import co.gov.dafp.sigep2.bean.ContratoExample;
import co.gov.dafp.sigep2.bean.ext.ContratoExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ContratoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Contrato
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class ContratoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4381854500942197011L;
	
	/**
	 * 
	 * @param Contrato
	 * @return
	 */
	public Contrato insertContrato (Contrato contrato){
		Contrato con = new Contrato();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
			id =  mapper.insert(contrato);
			if(id > 0){
				con.setError(false);
				con.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				con.setError(false);
				con.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return con;
		}catch (Exception ex) {
			con.setError(true);
			con.setMensaje(UtilsConstantes.MSG_EXEPCION);
			con.setMensajeTecnico(ex.getMessage());
			return con;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Contrato
	 * @return
	 */
	public Contrato updateContrato(Contrato contrato){
		Contrato con = new Contrato();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
			id = mapper.updateByPrimaryKey(contrato);
			if(id > 0){
				con.setError(false);
				con.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				con.setError(false);
				con.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return con;
		}catch (Exception ex) {
			con.setError(true);
			con.setMensaje(UtilsConstantes.MSG_EXEPCION);
			con.setMensajeTecnico(ex.getMessage());
			return con;	
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
	public List<ContratoExt> getContratoByAll(int limitIni, int limitEnd){
		List<ContratoExt> asoc = new ArrayList<>();
		ContratoExample dtoObject = new ContratoExample();
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
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
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
	 * @param contrato
	 * @return
	 */
	public List<ContratoExt> getContratoPersona(Contrato contrato){
		List<ContratoExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
			asoc =  mapper.selectCodPersona(contrato);
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
	 * @param contrato
	 * @return
	 */
	public List<ContratoExt> getContratoPersonaFecha(Contrato contrato){
		List<ContratoExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
			asoc =  mapper.selectCodPersonaFecha(contrato);
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
	 * @param contrato
	 * @return
	 */
	public List<ContratoExt> getContratoEntreDias(ContratoExt contrato){
		List<ContratoExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
			asoc =  mapper.selectdiasentre(contrato);
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
	 * @param contrato
	 * @return
	 */
	public List<ContratoExt> getContratoFiltro(Contrato contrato){
		List<ContratoExt> asoc = new ArrayList<>();
		if(contrato.getLimitEnd() < 1) {
			contrato.setLimitInit(0);
			contrato.setLimitEnd(10);
		}
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
			asoc =  mapper.selectContratoFiltro(contrato);
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
	 * Elaborado Por: Jose Viscaya
	 * Feb 19, 2019
	 * ContratoService.java
	 * @param contrato
	 * @return
	 */
	public List<ContratoExt> getContratoPersona(PersonaExt personaExt){
		List<ContratoExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
			asoc =  mapper.selectPersona(personaExt);
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
	public List<ContratoExt> ContratoById(long id){
		List<ContratoExt> asoc = new ArrayList<ContratoExt>();
		ContratoExample dtoObject = new ContratoExample();
		dtoObject.createCriteria().andCodContratoEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ContratoMapper  mapper = session.getMapper(ContratoMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<ContratoExt>();
			}
		}catch(Exception ex){
		
			return new ArrayList<ContratoExt>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
