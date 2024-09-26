/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Entidad;
import co.gov.dafp.sigep2.bean.EntidadExample;
import co.gov.dafp.sigep2.bean.ext.EntidadExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Entidad
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class EntidadService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3249249455830529660L;

	/**
	 * 
	 * @param EntidadPortal
	 * @return
	 */
	public Entidad insertEntidadSelective(EntidadExt entidad) {
		SqlSession session = null;
		Entidad ent = new Entidad();
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			id = mapper.insertSelective(entidad);
			if (id > 0) {
				ent.setError(false);
				ent.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
				Entidad enti = entidadByCodSigep(entidad.getCodigoSigep());
				if(enti.getCodEntidad() != null) {
					entidad.setCodEntidad(enti.getCodEntidad());
					if(entidad.getCodUsuario() != null) {
						if(entidad.getCodRol() != null) {
							if(entidad.getCodTipoVinculacion()!= null) {
								insertarUsuarioEntidad(entidad);
								session.commit();
								return ent;
							}
						}
					}
				}else {
					session.rollback();
					ent.setError(true);
					ent.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
					return ent;
				}
			} else {
				ent.setError(true);
				ent.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			session.rollback();
			ent.setError(true);
			ent.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
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
	 * 
	 * @param EntidadPortal
	 * @return
	 */
	public Entidad updateEntidadSelective(Entidad entidad) {
		Entidad ent = new Entidad();
		EntidadExample dtoObject = new EntidadExample();
		dtoObject.createCriteria().andCodEntidadEqualTo(entidad.getCodEntidad());
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			if(entidad.getCodNorma() != null) {
				id = mapper.updateByPrimaryKeySelective(entidad);
			}else {
				id = mapper.updateByPrimaryKeySelective2(entidad);
			}
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
	 * 
	 * @return
	 */
	public List<Entidad> getEntidadByAll(Entidad entidad) {
		List<Entidad> asoc = new ArrayList<>();
		EntidadExample dtoObject = new EntidadExample();
		try {
			if (entidad.getLimitEnd() > 1) {
				dtoObject.setLimitInit(entidad.getLimitInit());
				dtoObject.setLimitEnd(entidad.getLimitEnd());
			} else {
				dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
				dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
			}
		} catch (NullPointerException e) {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
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
	 * 
	 * @param codTipoEntidad
	 * @return
	 */
	public List<Entidad> getEntidadporCodTipoEntidad(int codTipoEntidad) {
		List<Entidad> asoc = new ArrayList<>();
		EntidadExample dtoObject = new EntidadExample();
		dtoObject.createCriteria().andCodTipoEntidadEqualTo(codTipoEntidad);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
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

	public List<Entidad> getEntidadporCodUsuario(BigDecimal codUsuario) {
		List<Entidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByUsuario(codUsuario);
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

	public List<Entidad> getFiltro(Entidad entidad) {
		List<Entidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectFiltro(entidad);
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
     * Elaborado Por: Jose Viscaya
     * 11 feb. 2019
     * EntidadService.java
     * @param codPersona
     * @return
     */
	public List<EntidadExt> getEntidadPorPersona(long codPersona) {
		List<EntidadExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectPorPersona(codPersona);
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
	 * @param Id
	 *            Search
	 * @return
	 */
	public Entidad entidadById(BigDecimal id) {
		Entidad asoc = new Entidad();
		EntidadExample dtoObject = new EntidadExample();
		dtoObject.createCriteria().andCodEntidadEqualTo(id);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByPrimaryKey(id);
			if (asoc != null) {
				return asoc;
			} else {
				return new Entidad();
			}
		} catch (Exception ex) {

			return new Entidad();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param entidad
	 * @return
	 */
	public Entidad getEntidadDepMun(Entidad entidad) {
		Entidad asoc = new Entidad();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.entidadDepMun(entidad);
			if (asoc != null) {
				return asoc;
			} else {
				return new Entidad();
			}
		} catch (Exception ex) {
			
			return new Entidad();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param EntidadPortal
	 * @return
	 */
	public boolean desactivarEntidad(Entidad entidad) {
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			id = mapper.desactivarEntidad(entidad);
			session.commit();
			return (id == -1) ? true : false;
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
	 * @param codTipoEntidad
	 * @return
	 */
	public boolean getEntidadNitExist(String nit) {
		List<Entidad> asoc = new ArrayList<>();
		EntidadExample dtoObject = new EntidadExample();
		dtoObject.createCriteria().andNitEqualTo(nit);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			return asoc.isEmpty();
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
	 * @param codTipoEntidad
	 * @return
	 */
	public boolean getEntidadNombreExist(Entidad entidad) {
		List<Entidad> asoc = new ArrayList<>();
		EntidadExample dtoObject = new EntidadExample();
		dtoObject.createCriteria().andNombreEntidadEqualTo(entidad.getNombreEntidad())
				.andCodDepartamentoEqualTo(entidad.getCodDepartamento())
				.andCodMunicipioEqualTo(entidad.getCodMunicipio());
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			return asoc.isEmpty();
		} catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public List<Entidad> getFiltroLike(Entidad entidad) {
		List<Entidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByNameEntityLike(entidad.getNombreEntidad());
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
	* @author: Jose Viscaya 
	* @fecha 14/08/2018 3:40:16 p.m.
	* @param persona
	* @return
	*
	 */
	public List<Entidad> getEntidadPorPersonaE(Integer codUsuario) {
		List<Entidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.entidadesPorPersonaE(codUsuario);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			Entidad err = new Entidad();
			err.setError(true);
			err.setMensaje(UtilsConstantes.MSG_EXEPCION);
			err.setMensajeTecnico(ex.getMessage());
			asoc.add(err);
			return asoc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 28/08/2018 2:28:56 p.m.
	* @param value
	* @return
	*
	 */
	public Entidad entidadByCodSigep(String value) {
		List<Entidad> asoc = new ArrayList<>();
		EntidadExample dtoObject = new EntidadExample();
		dtoObject.createCriteria().andCodigoSigepEqualTo(value);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (!asoc.isEmpty()) {
				return asoc.get(0);
			} else {
				return new Entidad();
			}
		} catch (Exception ex) {

			return new Entidad();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 03/12/2018 07:10:56 a.m.
	 * @param value
	 * @return
	 *
	 */
	public Entidad entidadByBogota(Entidad value) {
	    Entidad asoc = new Entidad();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByBogota(value);
			if (asoc.getCodEntidad() != null) {
				return asoc;
			} else {
				return new Entidad();
			}
		} catch (Exception ex) {
			return new Entidad();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 3/09/2018 3:30:39 p.m.
	* @param codTipoEntidad
	* @return
	*
	 */
	public boolean getCodSigep(String codigoSigep) {
		List<Entidad> asoc = new ArrayList<>();
		EntidadExample dtoObject = new EntidadExample();
		dtoObject.createCriteria().andCodigoSigepEqualTo(codigoSigep);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (!asoc.isEmpty()) {
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
	 * @author Maria Alejandra Colorado
	 * 23/11/2018
	 * Metodo que retorna la lista de entidades asociadas a una persona
	 * @param 
	 * @return List<>
	 */
	public List<EntidadExt> getEntidadesAsociadasPersonaFiltro(EntidadExt entidadExt) {
		List<EntidadExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectEntidadesAsociadasPersonaFiltro(entidadExt);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			EntidadExt err = new EntidadExt();
			err.setError(true);
			err.setMensaje(UtilsConstantes.MSG_EXEPCION);
			err.setMensajeTecnico(ex.getMessage());
			asoc.add(err);
			return asoc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * 11 feb. 2019
	 * EntidadService.java
	 * @param entidadExt
	 * @return
	 */
	public List<EntidadExt> getEntidadesPersonaRol(EntidadExt entidadExt) {
		List<EntidadExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectEntidadByRolPersona(entidadExt);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			EntidadExt err = new EntidadExt();
			err.setError(true);
			err.setMensaje(UtilsConstantes.MSG_EXEPCION);
			err.setMensajeTecnico(ex.getMessage());
			asoc.add(err);
			return asoc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param entidad
	 * @return
	 */
	public int insertarUsuarioEntidad(EntidadExt entidad) {
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			id = mapper.insertarUsaurioEntidad(entidad);
			return id;
		} catch (Exception ex) {
			return 0;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public List<Entidad> getEntidadByCodigoSigep(String codCodigoSigep) {
		List<Entidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectByCodigoSigep(codCodigoSigep);
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

	public List<Entidad> getEntidadesBogotaUsuario(Integer codUsuario) {
		List<Entidad> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper mapper = session.getMapper(EntidadMapper.class);
			asoc = mapper.selectEntidadesBogotaUsuario(codUsuario);
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
