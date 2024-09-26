/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Pais;
import co.gov.dafp.sigep2.bean.PaisExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PaisMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PaisService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PaisService implements Serializable {

	private static final long serialVersionUID = 2372798337280214244L;
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 7:35:21 AM
	 * @File:   PaisService.java
	 * @param pais
	 * @return
	 */
	public Pais insertPais (Pais pais){
		Pais paisr = new Pais();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PaisMapper  mapper = session.getMapper(PaisMapper.class);
			id =  mapper.insert(pais);
			if(id > 0){
				paisr.setError(false);
				paisr.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				paisr.setError(false);
				paisr.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return paisr;
		}catch (Exception ex) {
			paisr.setError(true);
			paisr.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			paisr.setMensajeTecnico(ex.getMessage());
			return paisr;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 7:35:46 AM
	 * @File:   PaisService.java
	 * @param pais
	 * @return
	 */
	public Pais updatePais(Pais pais){
		Pais paisr = new Pais();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PaisMapper  mapper = session.getMapper(PaisMapper.class);
			id = mapper.updateByPrimaryKey(pais);
			if(id > 0){
				paisr.setError(false);
				paisr.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				paisr.setError(false);
				paisr.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return paisr;
		}catch (Exception ex) {
			paisr.setError(true);
			paisr.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			paisr.setMensajeTecnico(ex.getMessage());
			return paisr;
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
	public List<Pais> getPaisByAll(){
		List<Pais> asoc = new ArrayList<>();
		PaisExample dtoObject = new PaisExample();
		dtoObject.setOrderByClause("NOMBRE_PAIS");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PaisMapper  mapper = session.getMapper(PaisMapper.class);
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
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 7:43:27 AM
	 * @File:   PaisService.java
	 * @param pais
	 * @return
	 */
	public List<Pais> getPaisByFiltro(Pais pais){
		List<Pais> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PaisMapper  mapper = session.getMapper(PaisMapper.class);
			asoc =  mapper.selectByfiltro(pais);
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
	 * @return
	 */
	public Pais getPaisbyId(BigDecimal codPais){
		Pais asoc = new Pais();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PaisMapper  mapper = session.getMapper(PaisMapper.class);
			asoc =  mapper.selectByPrimaryKey(codPais);
			if(asoc!=null){
				return asoc;
			}else{
				return new Pais();
			}
		}catch(Exception ex){
			return new Pais();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * Feb 21, 2019
	 * PaisService.java
	 * @param pais
	 * @return
	 */
	public Pais getPaisbyIso(Pais pais){
		Pais asoc = new Pais();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PaisMapper  mapper = session.getMapper(PaisMapper.class);
			asoc =  mapper.selectByIso(pais);
			if(asoc!=null){
				return asoc;
			}else{
				return new Pais();
			}
		}catch(Exception ex){
			return new Pais();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * Elimina fisicamente un pais.
	 * @param Pais
	 * @return String
	 */
	public String deletePais(BigDecimal codPais){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PaisMapper  mapper = session.getMapper(PaisMapper.class);
			id =  mapper.deleteByPrimaryKey(codPais);
			if(id > 0){
				session.commit();
				return "true";
			}else{
				return "false";
			}
		}catch (Exception ex) {
			return "false";
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * @author: Maria Alejandra C 
	 * @fecha 10/06/2019 11:57:26 a. m.
	 * @param Pais
	 * @return List<Pais>
	 */
	public List<Pais> getPaisDuplicado(Pais pais){
		List<Pais> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PaisMapper  mapper = session.getMapper(PaisMapper.class);
			asoc =  mapper.selectPaisDuplicado(pais);
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
