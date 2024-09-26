package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PermisoRol;
import co.gov.dafp.sigep2.bean.PermisoRolAccion;
import co.gov.dafp.sigep2.bean.Rol;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.RolExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PermisoRolAccionMapper;
import co.gov.dafp.sigep2.interfaces.PermisoRolMapper;
import co.gov.dafp.sigep2.interfaces.RolMapper;

/**
 * @author Jose Viscaya.
 * @version 1.0
 * @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion
 *        de datos de la tabla RolService.java
 * @Proyect SIGEPII
 * @Company ADA S.A
 * @Module exposicion de servicios Rest
 * @fecha 27/07/2018 2:52:54 p. m.
 */
public class RolService implements Serializable {
	private static final long serialVersionUID = -3225371372691454487L;

	/**
	 * @author: Jose Viscaya
	 * @fecha 27/07/2018 2:53:02 p. m.
	 * @param rol
	 * @return
	 */
	public boolean insertRol(Rol rol) {
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			id = mapper.insert(rol);
			if (id > 0) {
				session.commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date: Jun 6, 2019, 8:33:12 AM
	 * @File: RolService.java
	 * @return
	 */
	public boolean desactivarRoles() {
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			mapper.desactivarRoles();
			return false;
		} catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27/07/2018 2:53:09 p. m.
	 * @param rol
	 * @return
	 */
	public boolean updateRol(Rol rol) {
		SqlSession session = null;
		int id = 0;
		rol.setAudFechaActualizacion(new Date());
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			if (!rol.isFlgRolBase() && rol.getCodRolPadre() == null) {
				PermisoRolMapper permisoRolMapper = session.getMapper(PermisoRolMapper.class);
				List<PermisoRol> permisosRol = permisoRolMapper.selectByRol(rol.getCodRol());

				PermisoRolAccionMapper permisoRolAccionMapper = session.getMapper(PermisoRolAccionMapper.class);

				for (PermisoRol permisoRol : permisosRol) {
					PermisoRolAccion permisoRolAccionBuscar = new PermisoRolAccion();
					permisoRolAccionBuscar.setCodPermisoRol(permisoRol.getCodPermisoRol().toBigInteger());
					List<PermisoRolAccion> permisosRolAccion = permisoRolAccionMapper
							.selectByPermisoRol(permisoRolAccionBuscar);
					for (PermisoRolAccion permisoRolAccion : permisosRolAccion) {
						permisoRolAccionMapper.deleteByPermisoRol(permisoRolAccion);
					}
				}
				permisoRolMapper.deleteByRolId(rol.getCodRol());
			}
			id = mapper.updateByPrimaryKey(rol);
			session.commit();
			return (id == 1) ? true : false;
		} catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * @author: Jose Viscaya
	 * @fecha 27/07/2018 2:53:15 p. m.
	 * @param rol
	 * @return
	 */
	public List<Rol> getRolByAll(RolExt rol) {
		List<Rol> asoc = new ArrayList<>();
		try {
			if (rol.getLimitInit() < 0) {
				rol.setLimitInit(0);
			}
			if (rol.getLimitEnd() < 1) {
				rol.setLimitEnd(100);
			}
		} catch (NullPointerException e) {
			rol.setLimitInit(0);
			rol.setLimitEnd(100);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectRolesT(rol);
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
	 * @fecha 27/07/2018 2:53:20 p. m.
	 * @param rol
	 * @return
	 */
	public List<Rol> getRolByLike(RolExt rol) {
		List<Rol> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);

			if (rol.getLimitEnd() == 0) {
				rol.setLimitEnd(10);
			}
			asoc = mapper.selectLike(rol);
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
	 * @fecha 27/07/2018 2:53:26 p. m.
	 * @param rol
	 * @return
	 */
	public List<Rol> gettHijosRol(Rol rol) {
		List<Rol> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectHijosRol(rol);
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
	 * @fecha 27/07/2018 2:53:30 p. m.
	 * @param persona
	 * @return
	 */
	public List<Rol> rolesPersona(PersonaExt persona) {
		List<Rol> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.rolesPersona(persona);
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
	 * @fecha 27/07/2018 2:53:36 p. m.
	 * @param id
	 * @return
	 */
	public Rol getRolById(BigDecimal id) {
		Rol asoc = new Rol();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectByPrimaryKey(id);
			if (asoc != null) {
				return asoc;
			} else {
				return new Rol();
			}
		} catch (Exception ex) {
			return new Rol();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Rol> getRolByPadre(BigDecimal codPadreId) {
		Rol example = new Rol();
		List<Rol> asoc = new ArrayList<>();
		example.setCodRolPadre(codPadreId);
		example.setFlgEstado(BigDecimal.ONE);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectHijosRol(example);
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
	 * 
	 * @param codPadreId
	 * @return
	 */
	public List<RolExt> getRolByPerosna(PersonaExt persona) {
		List<RolExt> asoc = new ArrayList<>();
		SqlSession session = null;

		if (persona.getLimitEnd() == null || persona.getLimitEnd() == 0) {
			persona.setLimitEnd(10);
			persona.setLimitInit(0);
		}
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectByPersona(persona);
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
	 * 
	 * Elaborado por: Jose Viscaya 9 ene. 2019
	 * 
	 * @param rolExt
	 * @return
	 */
	public List<RolExt> getRolesByPerosnaEntidad(RolExt rolExt) {
		List<RolExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectRolesByPerosnaEntidad(rolExt);
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
	 * 
	 * Elaborado por: Jose Viscaya 8 ene. 2019
	 * 
	 * @return
	 */
	public List<Rol> getRolBase() {
		List<Rol> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectRolesBase();
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
	 * @author: Maria Alejandra C
	 * @fecha 29/03/2019 11:57:26 a. m.
	 * @param rol
	 * @return
	 */
	public List<Rol> gettHijosRolJefeTH(Rol rol) {
		List<Rol> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectHijosRolJefeTH(rol);
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
	 * @author: Maria Alejandra C
	 * @fecha 18/05/2021 11:57:26 a. m.
	 * @param rol
	 * @return
	 */
	public List<Rol> getHijosRolJefeTHyContratos(Rol rol) {
		List<Rol> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectHijosRolJefeTHYContratos(rol);
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
	 * @author: Maria Alejandra C
	 * @fecha 29/03/2019 11:57:26 a. m.
	 * @param rol
	 * @return
	 */
	public List<Rol> getRolPorNombre(Rol rol) {
		List<Rol> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			RolMapper mapper = session.getMapper(RolMapper.class);
			asoc = mapper.selectRolPorNombre(rol);
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
