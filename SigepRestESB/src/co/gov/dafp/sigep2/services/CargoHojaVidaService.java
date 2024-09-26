/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CargoHojaVida;
import co.gov.dafp.sigep2.bean.ext.CargoHojaVidaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CargoHojaVidaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Maria Alejandra
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla cargo_hoja_vida
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class CargoHojaVidaService implements Serializable {
	
	private static final long serialVersionUID = -5335045495562611377L;

	/**
	 * Inserta nuevo cargo en maestro cargo_hoja_vida
	 * @param cargoHojaVida
	 * @return CargoHojaVida
	 */
	public CargoHojaVida insertCargoHV (CargoHojaVida cargoHojaVida){
		CargoHojaVida cargoHVResult = new CargoHojaVida();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoHojaVidaMapper  mapper = session.getMapper(CargoHojaVidaMapper.class);
			id =  mapper.insert(cargoHojaVida);
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
	 * Modifica un cargo en maestro cargo_hoja_vida
	 * @param cargoHojaVida
	 * @return CargoHojaVida
	 */
	public CargoHojaVida updateCargoHojaVida(CargoHojaVida cargoHojaVida){
		CargoHojaVida resultCargoHV = new CargoHojaVida();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoHojaVidaMapper  mapper = session.getMapper(CargoHojaVidaMapper.class);
			id = mapper.updateByPrimaryKey(cargoHojaVida);
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
	 * Servicio que retorna lista del maestro cargo_hoja_vida dependiendo del filtro enviado
	 * @param cargoHojaVida
	 * @return List<CargoHojaVida>
	 */ 
	public List<CargoHojaVidaExt> getCargoHVFiltro(CargoHojaVidaExt cargoHojaVida){
		List<CargoHojaVidaExt> result = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoHojaVidaMapper  mapper = session.getMapper(CargoHojaVidaMapper.class);
			result =  mapper.selectByfiltro(cargoHojaVida);
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
	 * Verifica si el cargo enviado ya existe en el maestro cargo_hoja_vida
	 * @author: Maria Alejandra C 
	 * @fecha 29/04/2020
	 * @param CargoHojaVida
	 * @return List<CargoHojaVida>
	 */
	public List<CargoHojaVida> getCargoHVDuplicado(CargoHojaVida cargoHojaVida){
		List<CargoHojaVida> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoHojaVidaMapper  mapper = session.getMapper(CargoHojaVidaMapper.class);
			asoc =  mapper.selectCargoHVDuplicado(cargoHojaVida);
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
	 * Elimina fisicamente un cargo del maestro cargo_hoja_vida.
	 * @param cargoHojaVida
	 * @return String
	 */
	public String deleteCargoHojaVidaFisico(BigDecimal cargoHojaVida){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoHojaVidaMapper  mapper = session.getMapper(CargoHojaVidaMapper.class);
			id =  mapper.deleteByPrimaryKey(cargoHojaVida);
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
	 * Consulta un cargo del maestro cargoHojaVida por su llame primaria
	 * @param cargoHojaVida
	 * @return CargoHojaVida
	 */
	public CargoHojaVida cargoHVById(CargoHojaVida cargoHojaVida){
		CargoHojaVida asoc = new CargoHojaVida();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoHojaVidaMapper  mapper = session.getMapper(CargoHojaVidaMapper.class);
			asoc =  mapper.selectByPrimaryKey(cargoHojaVida.getCodCargoHojaVida());
			if(asoc !=null){
				return asoc;
			}else{
				return new CargoHojaVida();
			}
		}catch(Exception ex){
			return new CargoHojaVida();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}	
}
