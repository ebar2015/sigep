/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Cargo;
import co.gov.dafp.sigep2.bean.CargoExample;
import co.gov.dafp.sigep2.bean.Entidad;
import co.gov.dafp.sigep2.bean.EntidadPlantaDetalle;
import co.gov.dafp.sigep2.bean.ext.CargoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CargoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Cargo
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CargoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6097682976274841051L;
	
	/**
	 * 
	 * @param Cargo
	 * @return
	 */
	public Cargo insertCargo (Cargo cargo){
		Cargo carg = new Cargo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			id =  mapper.insert(cargo);
			if(id > 0){
				carg.setError(false);
				carg.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				carg.setError(false);
				carg.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return carg;
		}catch (Exception ex) {
			carg.setError(true);
			carg.setMensaje(UtilsConstantes.MSG_EXEPCION);
			carg.setMensajeTecnico(ex.getMessage());
			return carg;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Cargo
	 * @return
	 */
	public Cargo updateCargo(Cargo cargo){
		Cargo carg = new Cargo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			id = mapper.updateByPrimaryKey(cargo);
			session.commit();
			if(id > 0){
				carg.setError(false);
				carg.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				carg.setError(false);
				carg.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return carg;
		}catch (Exception ex) {
			carg.setError(true);
			carg.setMensaje(UtilsConstantes.MSG_EXEPCION);
			carg.setMensajeTecnico(ex.getMessage());
			return carg;	
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
	public List<Cargo> getCargoByAll(){
		List<Cargo> asoc = new ArrayList<>();
		CargoExample dtoObject = new CargoExample();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
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
	* @author: Jose Viscaya 
	* @fecha 3/09/2018 4:25:54 p.m.
	* @return
	*
	 */
	public List<Cargo> getcargosEntidad(Entidad entidad){
		List<Cargo> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			asoc =  mapper.cargosEntidad(entidad);
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
	* Elaborado por NESTOR RIASCO 
	* @fecha 25/10/2018 4:25:54 p.m.
	* @return
	*
	 */
	public Cargo getCargo(long codCargo){
		Cargo asoc = new Cargo();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			asoc =  mapper.selectByPrimaryKey(codCargo);
			if(asoc != null){
				return asoc;
			}else{
				return new Cargo();
			}
		}catch(Exception ex){
			return new Cargo();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param cargo
	 * @return
	 */
	public List<CargoExt> getCargoTodos(Cargo cargo){
		List<CargoExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			asoc =  mapper.selectTodos(cargo);
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
	 * @param cargo
	 * @return
	 */
	public List<CargoExt> getCargoFilter(Cargo cargo){
		List<CargoExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			asoc =  mapper.selectFiltro(cargo);
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
	public List<CargoExt> getCargoBycodEntidad(long codTipoEntidad){
		List<CargoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			asoc =  mapper.selectByCodTipoentidad(codTipoEntidad);
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
	public List<Cargo> getCargosEntidad(long codEntidad){
		List<Cargo> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			asoc =  mapper.selectCargosEntidad(codEntidad);
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
	* Elaborado por Nestor Riasco 
	* @fecha 2/10/2018 9:48:54 a.m.
	* @return Cargo
	*
	 */
	public Cargo getcargoEntidadPlantaDetalle(EntidadPlantaDetalle EntidadPlantaDetalle){
		Cargo asoc = new Cargo();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CargoMapper  mapper = session.getMapper(CargoMapper.class);
			asoc =  mapper.cargoEntidadPlantaDetalle(EntidadPlantaDetalle);
			if(asoc != null){
				return asoc;
			}else{
				return new Cargo();
			}
		}catch(Exception ex){
			return new Cargo();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
