/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EntidadCargo;
import co.gov.dafp.sigep2.bean.EntidadCargoExample;
import co.gov.dafp.sigep2.bean.ext.EntidadCargoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadCargoMapper;
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
public class EntidadCargoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1823055362509018768L;
	
	/**
	 * Metdodo apra insertar dats a ala tabla EntidadCargo
	 * @param EntidadCargoPortal
	 * @return
	 */
	public EntidadCargo insertEntidadCargoSelective(EntidadCargo entidadCargo) {
		SqlSession session = null;
		EntidadCargo ent = new EntidadCargo();
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadCargoMapper mapper = session.getMapper(EntidadCargoMapper.class);
			id = mapper.insertSelective(entidadCargo);
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
	 * Metdodo apra actualizar datos a ala tabla EntidadCargo
	 * @param EntidadCargoPortal
	 * @return
	 */
	public EntidadCargo updateEntidadCargo(EntidadCargo entidadCargo) {
		EntidadCargo ent = new EntidadCargo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadCargoMapper mapper = session.getMapper(EntidadCargoMapper.class);
			id = mapper.updateByPrimaryKey(entidadCargo);
			session.commit();
			if (id > 0) {
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
	 * Metdodo para consultar dats de tabla EntidadCargo
	 * @return List<EntidadCargo>
	 */
	public List<EntidadCargo> getEntidadCargoByAll() {
		List<EntidadCargo> asoc = new ArrayList<>();
		EntidadCargoExample dtoObject = new EntidadCargoExample();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadCargoMapper mapper = session.getMapper(EntidadCargoMapper.class);
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
	 * Metdodo para consultar dats de tabla EntidadCargo por id
	 * @param codCargoEntidad
	 * @return EntidadCargo
	 */
	public EntidadCargo getEntidadCargoporById(long codCargoEntidad) {
		EntidadCargo asoc = new EntidadCargo();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadCargoMapper mapper = session.getMapper(EntidadCargoMapper.class);
			asoc = mapper.selectByPrimaryKey(codCargoEntidad);
			if (asoc!= null) {
				return asoc;
			} else {
				return new EntidadCargo();
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
	 * Metdodo para consultar datos de tabla EntidadCargo por filtro
	 * @return List<EntidadCargo>
	 */
	public List<EntidadCargoExt> getEntidadCargoByFilter(EntidadCargoExt entidadCargo) {
		List<EntidadCargoExt> asoc = new ArrayList<>();
		if(entidadCargo.getLimitEnd() == 0) {
			entidadCargo.setLimitInit(0);
			entidadCargo.setLimitEnd(100);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadCargoMapper mapper = session.getMapper(EntidadCargoMapper.class);
			asoc = mapper.selectEntidadCargoFilter(entidadCargo);
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
}
