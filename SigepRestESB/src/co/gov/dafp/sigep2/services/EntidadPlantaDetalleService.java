/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EntidadPlantaDetalle;
import co.gov.dafp.sigep2.bean.EntidadPlantaDetalleExample;
import co.gov.dafp.sigep2.bean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadPlantaDetalleMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la comunicacion con la base de datos
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Martes 3 de Julio de 2018
*/
public class EntidadPlantaDetalleService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4264889397143820363L;
	
	/**
	 * Metdodo apra insertar dats a ala tabla EntidadPlantaDetalle
	 * @param EntidadPlantaDetallePortal
	 * @return
	 */
	public EntidadPlantaDetalle insertEntidadPlantaDetalleSelective(EntidadPlantaDetalle EntidadPlantaDetalle) {
		SqlSession session = null;
		EntidadPlantaDetalle ent = new EntidadPlantaDetalle();
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			id = mapper.insertSelective(EntidadPlantaDetalle);
			if (id > 0) {
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			} else {
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return ent;
		} catch (Exception ex) {
			ent.setError(true);
			ent.setMensaje(UtilsConstantes.MSG_EXEPCION);
			ent.setMensajeTecnico(ex.getMessage());
			return ent;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Metdodo apra actualizar datos a ala tabla EntidadPlantaDetalle
	 * @param EntidadPlantaDetallePortal
	 * @return
	 */
	public EntidadPlantaDetalle updateEntidadPlantaDetalle(EntidadPlantaDetalle EntidadPlantaDetalle) {
		EntidadPlantaDetalle ent = new EntidadPlantaDetalle();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			id = mapper.updateByPrimaryKey(EntidadPlantaDetalle);
			session.commit();
			if (id > 0) {
				ent = EntidadPlantaDetalle;
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			} else {
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return ent;
		} catch (Exception ex) {
			ent.setError(true);
			ent.setMensaje(UtilsConstantes.MSG_EXEPCION);
			ent.setMensajeTecnico(ex.getMessage());
			return ent;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Metdodo para consultar dats de tabla EntidadPlantaDetalle
	 * @return List<EntidadPlantaDetalle>
	 */
	public List<EntidadPlantaDetalle> getEntidadPlantaDetalleByAll() {
		List<EntidadPlantaDetalle> asoc = new ArrayList<>();
		EntidadPlantaDetalleExample dtoObject = new EntidadPlantaDetalleExample();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Metdodo para consultar dats de tabla EntidadPlantaDetalle por id
	 * @param codCargoEntidad
	 * @return EntidadPlantaDetalle
	 */
	public EntidadPlantaDetalleExt getEntidadPlantaDetalleporById(long codEntidadPlantaDetalle) {
		EntidadPlantaDetalleExt asoc = new EntidadPlantaDetalleExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectByPrimaryKey(codEntidadPlantaDetalle);
			if (asoc!= null) {
				return asoc;
			} else {
				return new EntidadPlantaDetalleExt();
			}
		} catch (Exception ex) {
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return asoc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	
	
	
	/**
	 * Metdodo para consultar datos de tabla EntidadPlantaDetalle
	 * @return List<EntidadPlantaDetalle>
	 */
	public List<EntidadPlantaDetalleExt> getEntidadPlantaDetalleByFilter(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();
		if(EntidadPlantaDetalle.getLimitEnd() == 0) {
			EntidadPlantaDetalle.setLimitInit(0);
			EntidadPlantaDetalle.setLimitEnd(100);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectEntidadPlantaDetalleFilter(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metdodo para consultar cargos relacinados a una entidad de la tabla EntidadPlantaDetalle
	 * @return List<EntidadPlantaDetalle>
	 */
	public List<EntidadPlantaDetalleExt> getCargoPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();
		
		if(EntidadPlantaDetalle.getLimitEnd() == null) {
			EntidadPlantaDetalle.setLimitInit(0);
			EntidadPlantaDetalle.setLimitEnd(100);
		}
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectCargoPlanta(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	 /**
     * @author: Robinson  Correa 
     * @fecha 15 Abril 2024
     * @param EntidadPlantaDetalleExt
     * @return Servicio para retornar el listado de Cargos 
     * por entidad y en estado activo
     */
	public List<EntidadPlantaDetalleExt> getCargosPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();
		
		if(EntidadPlantaDetalle.getLimitEnd() == null) {
			EntidadPlantaDetalle.setLimitInit(0);
			EntidadPlantaDetalle.setLimitEnd(100);
		}
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectCargosPlanta(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * Metdodo para consultar data de tabla EntidadPlantaDetalle por id
	 * @param objJson
	 * @return EntidadPlantaDetalle
	 */
	public List<EntidadPlantaDetalleExt> getNaturalezaEmpleobyEntidad(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();

		if (EntidadPlantaDetalle.getLimitEnd() == null) {
			EntidadPlantaDetalle.setLimitInit(0);
			EntidadPlantaDetalle.setLimitEnd(100);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectGetNaturalezaEmpleo(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	 /**
     * @author: Robinson  Correa 
     * @fecha 15 Abril 2024
     * @param EntidadPlantaDetalleExt
     * @return Servicio para retornar el listado de  codigos 
     * de los Cargos 
     * por entidad y en estado activo
     */
	public List<EntidadPlantaDetalleExt> getCodigosCargos(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();
		
		if(EntidadPlantaDetalle.getLimitEnd() == null) {
			EntidadPlantaDetalle.setLimitInit(0);
			EntidadPlantaDetalle.setLimitEnd(100);
		}
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectCodigosCargos(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	 /**
     * @author: Jose Viscaya 
     * @fecha 25 Juli 2018
     * @param EntidadPlantaDetalleExt
     * @return Este Servicio retorna una lista de EntidadPlantaDetalleExt
     */
	public List<EntidadPlantaDetalleExt> getEntiddCargoPlanta(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectEntiddCargoPlanta(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * Servicio que desactiva masivamente todas los cargos  que tiene actualmente una planta 
	 * @author Maria Alejandra Colorado
	 * @param EntidadPlantaDetalleExt
	 * @return
	 */
	public EntidadPlantaDetalleExt updateCargosDePlantaAutomatico(EntidadPlantaDetalleExt EntidadPlantaDetalleExt){
		EntidadPlantaDetalleExt param = new EntidadPlantaDetalleExt();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper  mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			id = mapper.updateCargosDePlantaAutomatico(EntidadPlantaDetalleExt);
			if(id > 0){
				param.setError(false);
				param.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				param.setError(true);
				param.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return param;
		}catch (Exception ex) {
			param.setError(true);
			param.setMensaje(UtilsConstantes.MSG_EXEPCION);
			param.setMensajeTecnico(ex.getMessage());
			return param;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Servicio que actualiza la denominacion origen a una de destino. (componente de equivalencias)
	 * @author Maria Alejandra Colorado
	 * @param EntidadPlantaDetalleExt
	 * @return
	 */
	public EntidadPlantaDetalleExt updateDenominacionDestino(EntidadPlantaDetalleExt EntidadPlantaDetalleExt){
		EntidadPlantaDetalleExt param = new EntidadPlantaDetalleExt();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper  mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			id = mapper.updateDenominacionDestino(EntidadPlantaDetalleExt);
			if(id >= 0){
				param.setError(false);
				param.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				param.setError(true);
				param.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return param;
		}catch (Exception ex) {
			param.setError(true);
			param.setMensaje(UtilsConstantes.MSG_EXEPCION);
			param.setMensajeTecnico(ex.getMessage());
			return param;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	 /**
     * Elaborado por Mar�a Alejandra Colorado 
     * @fecha 14/01/2019
     * @param EntidadPlantaDetalleExt
     * @return Este Servicio retorna una objeto EntidadPlantaDetalleExt con el total de directivos que tiene una entidad
     */
	public EntidadPlantaDetalleExt getTotalCargosDirectivoEntidad(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		EntidadPlantaDetalleExt asoc = new EntidadPlantaDetalleExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectCantidadCargosDirectivos(EntidadPlantaDetalle);
			if (asoc !=null) {
				return asoc;
			} else {
				return new EntidadPlantaDetalleExt();
			}
		} catch (Exception ex) {
			return new EntidadPlantaDetalleExt();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
     * @author: Maria Alejandra Colorado 
     * @fecha 02 Abril 2019
     * @param EntidadPlantaDetalleExt
     * @return Este Servicio retorna una lista de vinculaciones
     */
	public List<EntidadPlantaDetalleExt> getVinculacion(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectVinculacion(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
     * @author: Maria Alejandra Colorado 
     * @fecha 02 Abril 2019
     * @param EntidadPlantaDetalleExt
     * @return Este Servicio retorna lista de vinculaciones que tiene asignada una denominacion general.
     * Es decir, busca las vinculaciones que tienen las denominaciones hijas de la denominacion que se envia como parametro. 
     */
	public List<EntidadPlantaDetalleExt> getVinculacionDenominacionGeneral(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectVinculacionDenominacionGeneral(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
     * @author: Maria Alejandra Colorado 
     * @fecha 02 Abril 2019
     * @param EntidadPlantaDetalleExt
     * @return Este Servicio retorna lista de plantas que tiene asignada una denominacion general.
     * Es decir, busca las plantas que tienen las denominaciones hijas de la denominacion que se envia como parametro. 
     */
	public List<EntidadPlantaDetalleExt> getPlantasDenominacionGeneral(EntidadPlantaDetalleExt EntidadPlantaDetalle) {
		List<EntidadPlantaDetalleExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			asoc = mapper.selectPlantaDenominacionGeneral(EntidadPlantaDetalle);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author Maria Alejandra C
	 * @param EntidadPlantaDetalleExt 
	 * @return EntidadPlantaDetalleExt
	 */
	public EntidadPlantaDetalleExt obtenerVacanciaCargos(EntidadPlantaDetalleExt entidadPlantaDetalleExt){
		EntidadPlantaDetalleExt bien = new EntidadPlantaDetalleExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadPlantaDetalleMapper  mapper = session.getMapper(EntidadPlantaDetalleMapper.class);
			mapper.obtenerVacanciaPorCargo(entidadPlantaDetalleExt);
			if(entidadPlantaDetalleExt!=null && entidadPlantaDetalleExt.getVacantesCargo()!=null && entidadPlantaDetalleExt.getVacantesCargo() >= 0){
				bien.setError(false);
				session.commit();
				bien.setVacantesCargo(entidadPlantaDetalleExt.getVacantesCargo());
			}else{
				bien.setError(false);
				bien.setVacantesCargo(null);
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
}
