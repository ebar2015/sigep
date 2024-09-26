/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Festivo;
import co.gov.dafp.sigep2.bean.FestivoExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.FestivoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Festivo
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class FestivoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8932193937758528155L;

	/**
	 * 
	 * @param festivo
	 * @return
	 */
	public Festivo insertFestivo (Festivo festivo){
		Festivo fest = new Festivo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FestivoMapper  mapper = session.getMapper(FestivoMapper.class);
			id =  mapper.insert(festivo);
			if(id > 0){
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return fest;
		}catch (Exception ex) {
			fest.setError(true);
			fest.setMensaje(UtilsConstantes.MSG_EXEPCION);
			fest.setMensajeTecnico(ex.getMessage());
			return fest;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param festivo
	 * @return
	 */
	public Festivo updateFestivo(Festivo festivo){
		Festivo fest = new Festivo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FestivoMapper  mapper = session.getMapper(FestivoMapper.class);
			id = mapper.updateByPrimaryKey(festivo);
			if(id > 0){
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return fest;
		}catch (Exception ex) {
			fest.setError(true);
			fest.setMensaje(UtilsConstantes.MSG_EXEPCION);
			fest.setMensajeTecnico(ex.getMessage());
			return fest;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param festivo
	 * @return
	 */
	public Festivo delFestivo(int codFeriado){
		Festivo fest = new Festivo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FestivoMapper  mapper = session.getMapper(FestivoMapper.class);
			id = mapper.deleteByPrimaryKey(codFeriado);
			if(id > 0){
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_ELIMINACION_CON_EXITO);
				session.commit();
			}else{
				fest.setError(false);
				fest.setMensaje(UtilsConstantes.MSG_NO_ELIMINADO_CON_EXITO);
			}
			return fest;
		}catch (Exception ex) {
			fest.setError(true);
			fest.setMensaje(UtilsConstantes.MSG_EXEPCION);
			fest.setMensajeTecnico(ex.getMessage());
			return fest;
		} finally {
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
	public int getDifFestivo(Festivo festivo){
		List<Festivo> asoc = new ArrayList<>();
		FestivoExample dtoObject = new FestivoExample();
		dtoObject.createCriteria().andFechaFeriadoBetween(festivo.getFechaFeriado1(), festivo.getFechaFeriado2());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FestivoMapper  mapper = session.getMapper(FestivoMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			return asoc.size() ;
		}catch(Exception ex){
			return 0;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param fechaFeriado
	 * @return
	 */
	public List<Festivo> getDiasFestivo(Festivo festivo){
		List<Festivo> asoc = new ArrayList<>();
		FestivoExample dtoObject = new FestivoExample();
		dtoObject.createCriteria().andFechaFeriadoGreaterThanOrEqualTo(festivo.getFechaFeriado1());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FestivoMapper  mapper = session.getMapper(FestivoMapper.class);
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
	 * @Date:   May 7, 2019, 9:20:30 AM
	 * @File:   FestivoService.java
	 * @param festivo
	 * @return
	 */
	public List<Festivo> getFestivoByFiltro(Festivo festivo){
		List<Festivo> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FestivoMapper  mapper = session.getMapper(FestivoMapper.class);
			asoc =  mapper.selectByFiltro(festivo);
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
	 * @Date:   May 7, 2019, 3:45:24 PM
	 * @File:   FestivoService.java
	 * @param festivo
	 * @return
	 */
	public Festivo getFestivoByID(Festivo festivo){
		Festivo asoc = new Festivo();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			FestivoMapper  mapper = session.getMapper(FestivoMapper.class);
			asoc =  mapper.selectByPrimaryKey(festivo.getCodFeriado());
			return asoc;
		}catch(Exception ex){
			return new Festivo();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
