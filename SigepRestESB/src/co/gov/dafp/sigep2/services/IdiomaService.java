/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Idioma;
import co.gov.dafp.sigep2.bean.IdiomaExample;
import co.gov.dafp.sigep2.bean.ext.IdiomaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.IdiomaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla IdiomaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class IdiomaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6768908167714036689L;
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 9, 2019, 7:42:17 AM
	 * @File:   IdiomaService.java
	 * @param idioma
	 * @return
	 */
	public Idioma insertIdioma (Idioma idioma){
		Idioma idio = new Idioma();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaMapper  mapper = session.getMapper(IdiomaMapper.class);
			id =  mapper.insert(idioma);
			if(id > 0){
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return idio;
		}catch (Exception ex) {
			idio.setError(true);
			idio.setMensaje(UtilsConstantes.MSG_EXEPCION);
			idio.setMensajeTecnico(ex.getMessage());
			return idio;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Idioma
	 * @return
	 */
	public Idioma updateIdioma(Idioma idioma){
		Idioma idio = new Idioma();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaMapper  mapper = session.getMapper(IdiomaMapper.class);
			id = mapper.updateByPrimaryKey(idioma);
			session.commit();
			if(id > 0){
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return idio;
		}catch (Exception ex) {
			idio.setError(true);
			idio.setMensaje(UtilsConstantes.MSG_EXEPCION);
			idio.setMensajeTecnico(ex.getMessage());
			return idio;	
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
	public List<Idioma> getIdiomaByAll(){
		List<Idioma> asoc = new ArrayList<>();
		IdiomaExample dtoObject = new IdiomaExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaMapper  mapper = session.getMapper(IdiomaMapper.class);
			dtoObject.setOrderByClause("NOMBRE_IDIOMA");
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			asoc = new ArrayList<>();
			Idioma d = new Idioma();
			d.setError(true);
			d.setMensaje("Error De Sistema");
			d.setMensajeTecnico(ex.getMessage());
			asoc.add(d);
			return asoc;
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
	public Idioma getIdiomaById(int id){
		Idioma asoc = new Idioma();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaMapper  mapper = session.getMapper(IdiomaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new Idioma();
			}
		}catch(Exception ex){
		
			return new Idioma();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param codPersona
	 * @return
	 */
	public IdiomaExt getIdiomaComplPersona(long codPersona){
		IdiomaExt asoc = new IdiomaExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaMapper  mapper = session.getMapper(IdiomaMapper.class);
			asoc =  mapper.selectIdiomaComplPersona(codPersona);
			if(asoc != null){
				return asoc;
			}else{
				return new IdiomaExt();
			}
		}catch(Exception ex){
			
			return new IdiomaExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   May 7, 2019, 8:05:53 AM
	 * @File:   IdiomaService.java
	 * @param idioma
	 * @return
	 */
	public List<Idioma> getIdiomaByfiltro(Idioma idioma){
		List<Idioma> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaMapper  mapper = session.getMapper(IdiomaMapper.class);
			asoc =  mapper.selectByFiltro(idioma);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			asoc = new ArrayList<>();
			Idioma d = new Idioma();
			d.setError(true);
			d.setMensaje("Error De Sistema");
			d.setMensajeTecnico(ex.getMessage());
			asoc.add(d);
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 7:56:53
	 * @File:   IdiomaService.java
	 * @return
	 */
	public List<Idioma> getIdiomaByName(Idioma idioma){
		List<Idioma> asoc = new ArrayList<>();
		IdiomaExample dtoObject = new IdiomaExample();
		dtoObject.createCriteria().andNombreIdiomaEqualTo(idioma.getNombreIdioma());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaMapper  mapper = session.getMapper(IdiomaMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc;
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
	 * @author: Maria Alejandra C 
	 * @fecha 10/06/2019 11:57:26 a. m.
	 * @param Idioma
	 * @return List<Idioma>
	 */
	public List<Idioma> getIdiomaDuplicado(Idioma idioma){
		List<Idioma> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			IdiomaMapper  mapper = session.getMapper(IdiomaMapper.class);
			asoc =  mapper.selectIdiomaDuplicado(idioma);
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
