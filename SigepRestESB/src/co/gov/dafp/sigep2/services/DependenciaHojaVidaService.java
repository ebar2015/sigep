/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.DependenciaHojaVida;
import co.gov.dafp.sigep2.bean.ext.DependenciaHojaVidaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DependenciaHojaVidaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Maria Alejandra
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla dependencia_hoja_vida
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class DependenciaHojaVidaService implements Serializable {
	
	private static final long serialVersionUID = -5335045495562611377L;

	/**
	 * Inserta una nueva dependencia en maestro dependencia_hoja_vida
	 * @param dependenciaHojaVida
	 * @return DependenciaHojaVida
	 */
	public DependenciaHojaVida insertDependenciaHV (DependenciaHojaVida dependenciaHojaVida){
		DependenciaHojaVida cargoHVResult = new DependenciaHojaVida();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaHojaVidaMapper  mapper = session.getMapper(DependenciaHojaVidaMapper.class);
			id =  mapper.insert(dependenciaHojaVida);
			if(id > 0){
				cargoHVResult.setError(false);
				cargoHVResult.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				cargoHVResult.setError(false);
				cargoHVResult.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return cargoHVResult;
		}catch (Exception ex) {
			cargoHVResult.setError(true);
			cargoHVResult.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			cargoHVResult.setMensajeTecnico(ex.getMessage());
			return cargoHVResult;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Modifica una dependencia en maestro dependencia_hoja_vida
	 * @param cargoHojaVida
	 * @return CargoHojaVida
	 */
	public DependenciaHojaVida updateDependenciaHojaVida(DependenciaHojaVida dependenciaHojaVida){
		DependenciaHojaVida resultCargoHV = new DependenciaHojaVida();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaHojaVidaMapper  mapper = session.getMapper(DependenciaHojaVidaMapper.class);
			id = mapper.updateByPrimaryKey(dependenciaHojaVida);
			if(id > 0){
				resultCargoHV.setError(false);
				resultCargoHV.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				resultCargoHV.setError(false);
				resultCargoHV.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return resultCargoHV;
		}catch (Exception ex) {
			resultCargoHV.setError(true);
			resultCargoHV.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			resultCargoHV.setMensajeTecnico(ex.getMessage());
			return resultCargoHV;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	

	/**
	 * Servicio que retorna lista del maestro dependencia_hoja_vida dependiendo del filtro enviado
	 * @param DependenciaHojaVida
	 * @return List<DependenciaHojaVida>
	 */ 
	public List<DependenciaHojaVidaExt> getDependenciaVFiltro(DependenciaHojaVidaExt dependenciaHojaVida){
		List<DependenciaHojaVidaExt> result = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaHojaVidaMapper  mapper = session.getMapper(DependenciaHojaVidaMapper.class);
			result =  mapper.selectByfiltro(dependenciaHojaVida);
			if(!result.isEmpty()){
				return result;
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
	 * Verifica si el nombre de la dependencia enviada ya existe en el maestro dependencia_hoja_vida
	 * @author: Maria Alejandra C 
	 * @fecha 29/04/2020
	 * @param DependenciaHojaVida
	 * @return List<DependenciaHojaVida>
	 */
	public List<DependenciaHojaVida> getDependenciaHVDuplicado(DependenciaHojaVida cargoHojaVida){
		List<DependenciaHojaVida> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaHojaVidaMapper  mapper = session.getMapper(DependenciaHojaVidaMapper.class);
			asoc =  mapper.selectDependenciaHVDuplicado(cargoHojaVida);
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
	 * Elimina fisicamente un cargo del maestro dependencia_hoja_vida.
	 * @param dependenciaHojaVida
	 * @return String
	 */
	public String deleteDependenciaHojaVidaFisico(BigDecimal dependenciaHojaVida){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaHojaVidaMapper  mapper = session.getMapper(DependenciaHojaVidaMapper.class);
			id =  mapper.deleteByPrimaryKey(dependenciaHojaVida);
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
	 * Inserta una nueva dependencia en maestro dependencia_hoja_vida si este no existe ya.
	 * Este servicio es llamdo desde el componente de Estructura.
	 * @param dependenciaHojaVida
	 * @return DependenciaHojaVida
	 */
	public DependenciaHojaVida insertDependenciaHVDesdeEstructura (DependenciaHojaVida dependenciaHojaVida){
		DependenciaHojaVida cargoHVResult = new DependenciaHojaVida();
		List<DependenciaHojaVida> listaDuplicado =  getDependenciaHVDuplicado(dependenciaHojaVida);
		if(!listaDuplicado.isEmpty()) {
			return cargoHVResult;
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaHojaVidaMapper  mapper = session.getMapper(DependenciaHojaVidaMapper.class);
			id =  mapper.insert(dependenciaHojaVida);
			if(id > 0){
				cargoHVResult.setError(false);
				cargoHVResult.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				cargoHVResult.setError(false);
				cargoHVResult.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return cargoHVResult;
		}catch (Exception ex) {
			cargoHVResult.setError(true);
			cargoHVResult.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			cargoHVResult.setMensajeTecnico(ex.getMessage());
			return cargoHVResult;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Consulta una dependencia del maestro dependenciaHojaVida por su llame primaria
	 * @param DependenciaHV
	 * @return DependenciaHojaVida
	 */
	public DependenciaHojaVida dependenciaHVById(DependenciaHojaVida dependenciaHojaVida){
		DependenciaHojaVida asoc = new DependenciaHojaVida();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DependenciaHojaVidaMapper  mapper = session.getMapper(DependenciaHojaVidaMapper.class);
			asoc =  mapper.selectByPrimaryKey(dependenciaHojaVida.getCodDependenciaHojaVida());
			if(asoc !=null){
				return asoc;
			}else{
				return new DependenciaHojaVida();
			}
		}catch(Exception ex){
			return new DependenciaHojaVida();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}	
}
