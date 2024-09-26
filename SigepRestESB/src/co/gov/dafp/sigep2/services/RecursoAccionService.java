package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.jboss.resteasy.spi.NotAcceptableException;

import co.gov.dafp.sigep2.bean.PermisoRol;
import co.gov.dafp.sigep2.bean.PermisoRolAccion;
import co.gov.dafp.sigep2.bean.RecursoAccion;
import co.gov.dafp.sigep2.bean.ext.RecursoAccionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PermisoRolAccionMapper;
import co.gov.dafp.sigep2.interfaces.PermisoRolMapper;
import co.gov.dafp.sigep2.interfaces.RecursoAccionMapper;

/**
 * Implementaci�n y consumo del mapper {@link RecursoAccionMapper}
 * 
 * @author JDavila
 *
 */
public class RecursoAccionService implements Serializable {

	private static final long serialVersionUID = -1608027416043554222L;

	private final static Integer INSERTAR = 785;
	private final static Integer ACTUALIZAR = 63;

	/**
	 * Devuelve el listado de recursos asignados a los roles
	 * 
	 * @param recursoAccion
	 *            Parametro con el nombre del rol a filtrar por mecanismo like
	 * @return {@link List} de {@link RecursoAccion}
	 */
	public List<RecursoAccion> getRecursoAccionByLikeNombreRol(RecursoAccion recursoAccion) {
		List<RecursoAccion> resultado = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			session.flushStatements();
			RecursoAccionMapper mapper = session.getMapper(RecursoAccionMapper.class);

			RecursoAccion count = null;

			List<String> recursos = null;
			if (recursoAccion.getRecurso() != null && !recursoAccion.getRecurso().isEmpty()) {
				recursos = Arrays.asList(recursoAccion.getRecurso().split(","));
			}

			count = mapper.countByLikeNombreRol(recursoAccion, recursos);
			if (recursoAccion.getLimitFin() == 0) {
				recursoAccion.setLimitFin(count.getTotal().intValue());
			}
			resultado = mapper.selectByLikeNombreRol(recursoAccion, recursos);
			if (!resultado.isEmpty()) {
				if (!resultado.isEmpty()) {
					resultado.get(0).setTotal(count.getTotal());
				}
				return resultado;
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
	 * Gestiona el CRUD de las acciones a las que tendra permiso un rol en
	 * determinado momento.<br/>
	 * <br/>
	 * <br/>
	 * C {@link PermisoRolAccion#getCodPermisoRolAcciones()} es igual a
	 * <code>null</code> <br/>
	 * R {@link NotAcceptableException} <br/>
	 * U Si {@link PermisoRolAccion#getFlgActivo()} es igual a <code>1</code> y
	 * {@link PermisoRolAccion#getCodPermisoRolAcciones()} es diferente de
	 * <code>null</code> <br/>
	 * D si {@link PermisoRolAccion#getFlgActivo()} es igual a <code>0</code> y
	 * {@link PermisoRolAccion#getCodPermisoRolAcciones()} es diferente de
	 * <code>null</code>
	 * 
	 * @param permisoRolAccion
	 *            {@link PermisoRolAccion} a gestionar de acuerdo a determinadas
	 *            condiciones
	 * @return {@link PermisoRolAccion} con el resultado de la transaccion
	 * 
	 * @throws NotAcceptableException
	 *             En caso de que alguna de las condiciones no sea satisfecha se
	 *             devolvera la debida excepcion
	 * 
	 */
	public PermisoRolAccion procesarPermiso(PermisoRolAccion permisoRolAccion) {
		PermisoRolAccion resultado = new PermisoRolAccion();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);

			PermisoRolAccionMapper mapper = session.getMapper(PermisoRolAccionMapper.class);
			// Validacion para creacion del persmiso
			if (permisoRolAccion.getCodPermisoRolAcciones() == null
					|| permisoRolAccion.getCodPermisoRolAcciones().equals(BigInteger.ZERO)
					|| permisoRolAccion.getCodPermisoRolAcciones().equals(BigInteger.ONE.negate())) {
				permisoRolAccion.setAudAccion(BigInteger.valueOf(INSERTAR.longValue()));

				List<PermisoRolAccion> permisosRolAccion = mapper.selectByPermisoRol(permisoRolAccion);
				if (!permisosRolAccion.isEmpty()) {
					return permisosRolAccion.get(0);
				}

				int inactivado = mapper.asignarPermiso(permisoRolAccion);
				if (inactivado == 1) {
					session.commit();
					resultado.setError(false);
					resultado.setMensaje("Permiso asignado");
				}
			}
			// Validacion para la actualizacion del permiso
			else if (permisoRolAccion.isFlgActivo() && permisoRolAccion.getCodPermisoRolAcciones() != null) {
				permisoRolAccion.setAudAccion(BigInteger.valueOf(ACTUALIZAR.longValue()));
				int reactivado = mapper.reasignarPermiso(permisoRolAccion);
				if (reactivado == 1) {
					session.commit();
					resultado.setError(false);
					resultado.setMensaje("Permiso reasignado");
				}
			}
			// Validacion para la eliminacion o desactivacion del permiso
			else if (!permisoRolAccion.isFlgActivo() && permisoRolAccion.getCodPermisoRolAcciones() != null) {
				permisoRolAccion.setAudAccion(BigInteger.valueOf(ACTUALIZAR.longValue()));
				int inactivado = mapper.revocarPermiso(permisoRolAccion);
				if (inactivado == 1) {
					session.commit();
					resultado.setError(false);
					resultado.setMensaje("Permiso revocado");
				}
			}
			// Al no cumplirse las anteriores condiciones se devuelve la
			// excepcion de no soportado
			else {

			}
			return resultado;
		} catch (Exception ex) {
			resultado.setError(true);
			resultado.setMensaje("Proceso fallido");
			resultado.setMensajeTecnico(ex.getMessage());
			return resultado;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Gestiona el CRUD de las acciones a las que tendra permiso un rol en
	 * determinado momento.<br/>
	 * <br/>
	 * <br/>
	 * C {@link PermisoRol#getCodPermisoRol()} es igual a <code>null</code>
	 * <br/>
	 * R {@link NotAcceptableException} <br/>
	 * U Si {@link PermisoRol#isFlgEstado()} es igual a <code>1</code> y
	 * {@link PermisoRol#getCodPermisoRol())} es diferente de <code>null</code>
	 * <br/>
	 * D si {@link PermisoRol#isFlgEstado()} es igual a <code>0</code> y
	 * {@link PermisoRol#getCodPermisoRol()} es diferente de <code>null</code>
	 * 
	 * @param permisoRol
	 *            {@link PermisoRol} a gestionar de acuerdo a determinadas
	 *            condiciones
	 * @return {@link PermisoRol} con el resultado de la transaccion
	 * 
	 * @throws NotAcceptableException
	 *             En caso de que alguna de las condiciones no sea satisfecha se
	 *             devolvera la debida excepcion
	 * 
	 */
	public PermisoRol procesarPermiso(PermisoRol permisoRol) {
		PermisoRol resultado = new PermisoRol();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);

			PermisoRolMapper mapper = session.getMapper(PermisoRolMapper.class);

			// Validacion para creacion del persmiso
			if (permisoRol.getCodPermisoRol() == null) {
				List<PermisoRol> permisosRol = mapper.selectByRecursoRol(permisoRol);
				if (!permisosRol.isEmpty()) {
					return permisosRol.get(0);
				}

				permisoRol.setAudAccion(INSERTAR);
				int inactivado = mapper.insert(permisoRol);
				if (inactivado == 1) {
					session.commit();

					resultado = mapper.selectByRecursoRol(permisoRol).get(0);

					resultado.setError(false);
					resultado.setMensaje("Permiso asignado");
				}
			}
			// Validacion para la actualizacion del permiso
			else if (permisoRol.isFlgEstado() && permisoRol.getCodPermisoRol() != null) {
				permisoRol.setAudAccion(ACTUALIZAR);
				int reactivado = mapper.updateByPrimaryKey(permisoRol);
				if (reactivado == 1) {
					session.commit();
					resultado.setError(false);
					resultado.setMensaje("Permiso reasignado");
				}
			}
			// Validacion para la eliminacion o desactivacion del permiso
			else if (!permisoRol.isFlgEstado() && permisoRol.getCodPermisoRol() != null) {
				permisoRol.setAudAccion(ACTUALIZAR);
				int inactivado = mapper.updateByPrimaryKey(permisoRol);
				if (inactivado == 1) {
					session.commit();
					resultado.setError(false);
					resultado.setMensaje("Permiso revocado");
				}
			}
			// Al no cumplirse las anteriores condiciones se devuelve la
			// excepcion de no soportado
			else {

			}
			return resultado;
		} catch (Exception ex) {
			resultado.setError(true);
			resultado.setMensaje("Proceso fallido");
			resultado.setMensajeTecnico(ex.getMessage());
			return resultado;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Servicio que retorna lista la vista v_recurso_accion dependiendo del filtro enviado
	 * @author: Maria Alejandra C 
	 * @fecha 18/09/2020
	 * @param recursoAccion
	 * @return List<RecursoAccion>
	 */ 
	public List<RecursoAccion> getVistaRecursoAccionFiltro(RecursoAccion recursoAccion){
		List<RecursoAccion> result = new ArrayList<>();
		SqlSession session = null;
		try{
			
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RecursoAccionMapper  mapper = session.getMapper(RecursoAccionMapper.class);
			result =  mapper.selectByfiltro(recursoAccion);
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
	 * Servicio que retorna lista la vista v_recurso_accion dependiendo del filtro enviado (usuario - recurso)
	 * @author: 
	 * @fecha 18/09/2020
	 * @param recursoAccion
	 * @return List<RecursoAccion>
	 */ 
	public List<RecursoAccion> getVistaRecursoUsuarioAccionFiltro(RecursoAccionExt recursoAccion){
		List<RecursoAccion> result = new ArrayList<>();
		SqlSession session = null;
		try{
			
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RecursoAccionMapper  mapper = session.getMapper(RecursoAccionMapper.class);
			result =  mapper.getVistaRecursoUsuarioAccionFiltro(recursoAccion);
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
}
