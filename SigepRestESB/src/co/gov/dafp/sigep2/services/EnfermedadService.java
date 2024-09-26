/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Enfermedad;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EnfermedadMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Enfermedad
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class EnfermedadService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059060376298916933L;
	
	
	/**
	 * 
	 * @param Enfermedad
	 * @return
	 */
	public Enfermedad insertEnfermedad (Enfermedad enfermedad){
		Enfermedad bien = new Enfermedad();
		bien = getEnfemedadcodCIE(enfermedad.getCodCie10());
		if(bien != null) {
			if(bien.getCodCie10()!= null) {
				bien.setError(true);
				bien.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
				bien.setCodigoEstado(110);
				return bien;	
			}
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EnfermedadMapper  mapper = session.getMapper(EnfermedadMapper.class);
			id =  mapper.insert(enfermedad);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Enfermedad
	 * @return
	 */
	public Enfermedad updateEnfermedad(Enfermedad enfermedad){
		Enfermedad bien = new Enfermedad();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EnfermedadMapper  mapper = session.getMapper(EnfermedadMapper.class);
			id = mapper.updateByPrimaryKey(enfermedad);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;
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
	public List<Enfermedad> getEnfemedadLike(Enfermedad enfermedad){
		List<Enfermedad> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EnfermedadMapper  mapper = session.getMapper(EnfermedadMapper.class);
			asoc =  mapper.selectEnfemedadLike(enfermedad);
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
	public Enfermedad getEnfemedadcodCIE(String codCIE){
		Enfermedad asoc = new Enfermedad();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EnfermedadMapper  mapper = session.getMapper(EnfermedadMapper.class);
			asoc =  mapper.selectByPrimaryKey(codCIE);
			if(asoc != null){
				return asoc;
			}else{
				return new Enfermedad();
			}
		}catch(Exception ex){
			return new Enfermedad();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   6 may. 2019, 17:22:06
	 * @File:   EnfermedadService.java
	 * @return
	 */
	public List<Enfermedad> getEnfemedad(Enfermedad enfermedad){
		List<Enfermedad> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EnfermedadMapper  mapper = session.getMapper(EnfermedadMapper.class);
			asoc =  mapper.selectEnfemedadLikefiltro(enfermedad);
			return asoc;
		}catch(Exception ex){
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
