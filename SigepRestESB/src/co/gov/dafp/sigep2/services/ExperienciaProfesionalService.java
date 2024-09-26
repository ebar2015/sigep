/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ExperienciaProfesional;
import co.gov.dafp.sigep2.bean.ExperienciaProfesionalExample;
import co.gov.dafp.sigep2.bean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ExperienciaProfesionalMapper;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya.
 * @version 1.0
 * @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion
 *        de datos de la tabla ExperienciaProfesional
 * @Proyect SIGEPII
 * @Company ADA S.A
 * @Module exposicion de servicios Rest
 */
public class ExperienciaProfesionalService implements Serializable {

	private static final long serialVersionUID = -4243852210104234753L;

	/** 
	 * @param ExperienciaProfesional
	 * @return
	 */
	public ExperienciaProfesional insertExperienciaProfesional(ExperienciaProfesional experienciaProfesional) {
		ExperienciaProfesional exp = new ExperienciaProfesional();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			id = mapper.insert(experienciaProfesional);
			if (id > 0) {
				exp.setError(false);
				exp.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			} else {
				exp.setError(false);
				exp.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return exp;
		} catch (Exception ex) {
			exp.setError(true);
			exp.setMensaje(UtilsConstantes.MSG_EXEPCION);
			exp.setMensajeTecnico(ex.getMessage());
			LoggerSigep.getInstance().error(ex.getMessage(), ExperienciaProfesionalMapper.class);
			return exp;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * @param ExperienciaProfesional
	 * @return ExperienciaProfesional
	 */
	public ExperienciaProfesional updateExperienciaProfesional(ExperienciaProfesional experienciaProfesional) {
		ExperienciaProfesional exp = new ExperienciaProfesional();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			id = mapper.updateByPrimaryKey(experienciaProfesional);
			session.commit();
			if (id > 0) {
				exp.setError(false);
				exp.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			} else {
				exp.setError(false);
				exp.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return exp;
		} catch (Exception ex) {
			exp.setError(true);
			exp.setMensaje(UtilsConstantes.MSG_EXEPCION);
			exp.setMensajeTecnico(ex.getMessage());
			LoggerSigep.getInstance().error(ex.getMessage(), ExperienciaProfesionalMapper.class);
			return exp;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param ExperienciaProfesional
	 * @return
	 */
	public ExperienciaProfesional updatemasivo(ExperienciaProfesional experienciaProfesional) {
		SqlSession session = null;
		ExperienciaProfesional exp = new ExperienciaProfesional();
		int id = 0;
		experienciaProfesional.setFechaActualizacion(new Date());
		if (experienciaProfesional.getAudCodRol() == null) {
			exp.setError(true);
			exp.setMensaje("Falta AudCodRol()");
			return exp;
		}
		if (experienciaProfesional.getAudCodUsuario() == null) {
			exp.setError(true);
			exp.setMensaje("Falta AudCodUsuario()");
			return exp;
		}
		if (experienciaProfesional.getCodPersona() == null) {
			exp.setError(true);
			exp.setMensaje("Falta CodPersona()");
			return exp;
		}
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			id = mapper.updateExperienciaP(experienciaProfesional);
			id = mapper.updateExperienciaD(experienciaProfesional);
			id = mapper.updateEducacionF(experienciaProfesional);
			session.commit();
			if (id > 1) {
				exp.setError(false);
				exp.setMensaje("Actualizacion Exitosa");
				return exp;
			} else {
				exp.setError(true);
				exp.setMensaje("Actualizacion Fallida");
				return exp;
			}
		} catch (Exception ex) {
			exp.setError(true);
			exp.setMensajeTecnico(ex.getMessage());
			return exp;
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
	public List<ExperienciaProfesional> getExperienciaProfesionalByAll(int limitIni, int limitEnd) {
		List<ExperienciaProfesional> asoc = new ArrayList<>();
		ExperienciaProfesionalExample dtoObject = new ExperienciaProfesionalExample();
		if (limitEnd > 1) {
			dtoObject.setLimitInit(limitIni);
			dtoObject.setLimitEnd(limitEnd);
		} else {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
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
	 * @param Id
	 *            Search
	 * @return
	 */
	public ExperienciaProfesional experienciaProfesionalByKey(long id) {
		ExperienciaProfesional asoc = new ExperienciaProfesional();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectByPrimaryKey(id);
			if (asoc != null) {
				return asoc;
			} else {
				return new ExperienciaProfesional();
			}
		} catch (Exception ex) {

			return new ExperienciaProfesional();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param codPesona
	 * @return
	 */
	public ExperienciaProfesionalExt getDifDias(BigDecimal codPesona) {
		ExperienciaProfesionalExt asoc = new ExperienciaProfesionalExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectByPersona(codPesona);
			if (asoc != null) {
				return asoc;
			} else {
				return new ExperienciaProfesionalExt();
			}
		} catch (Exception ex) {

			return new ExperienciaProfesionalExt();
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
	public ExperienciaProfesionalExt experienciaProfesionalById(long id) {
		ExperienciaProfesionalExt asoc = new ExperienciaProfesionalExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectExperiaciaById(id);
			if (asoc != null) {
				return asoc;
			} else {
				return new ExperienciaProfesionalExt();
			}
		} catch (Exception ex) {

			return new ExperienciaProfesionalExt();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param codPersona
	 * @return
	 */
	public List<ExperienciaProfesional> expProfesionalPorcodigoPer(ExperienciaProfesional experienciaProfesional) {
		List<ExperienciaProfesional> asoc = new ArrayList<>();
		ExperienciaProfesionalExample dtoObject = new ExperienciaProfesionalExample();
		if (experienciaProfesional.getFlgActivoEntidad() != null) {
			dtoObject.createCriteria().andCodPersonaEqualTo(experienciaProfesional.getCodPersona())
					.andFlgActivoEntidadEqualTo(experienciaProfesional.getFlgActivoEntidad())
					.andFlgActivoEqualTo(experienciaProfesional.getFlgActivo());
		} else {
			dtoObject.createCriteria().andCodPersonaEqualTo(experienciaProfesional.getCodPersona())
					.andFlgActivoEqualTo(experienciaProfesional.getFlgActivo());
		}

		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (asoc != null) {
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
	 * @param codTipoentidad
	 * @return
	 */
	public List<ExperienciaProfesional> getExperienciaTipoentidad(int codTipoentidad) {
		List<ExperienciaProfesional> asoc = new ArrayList<>();
		ExperienciaProfesionalExample dtoObject = new ExperienciaProfesionalExample();
		dtoObject.createCriteria().andCodTipoEntidadEqualTo(codTipoentidad);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (asoc != null) {
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
	 * @param experienciaProfesional
	 * @return
	 */
	public List<ExperienciaProfesionalExt> getCargoEntidadPersona(ExperienciaProfesional experienciaProfesional) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectCargoEntidadPersona(experienciaProfesional);
			if (asoc != null) {
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
	 * @param experienciaProfesional
	 * @return
	 */
	public List<ExperienciaProfesionalExt> getExperienCiacalculo(ExperienciaProfesional experienciaProfesional) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectExperienciaCalculo(experienciaProfesional);
			if (asoc != null) {
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
	 * 14 feb. 2019
	 * ExperienciaProfesionalService.java
	 * @param experienciaProfesional
	 * @return
	 */
	public List<ExperienciaProfesionalExt> getExperienciaProfesionalFiltro(ExperienciaProfesional experienciaProfesional) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectbyFilter(experienciaProfesional);
			if (asoc != null) {
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
	 * @param codTipoentidad
	 * @return
	 */
	public List<ExperienciaProfesionalExt> getCargos(ExperienciaProfesional experienciaProfesional) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectCargo(experienciaProfesional);
			if (asoc != null) {
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
	 * @return
	 */
	public List<ExperienciaProfesional> getCargosPublicos() {
		List<ExperienciaProfesional> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectCargoPublic();
			if (asoc != null) {
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
	 * @param codPersona
	 * @return
	 */
	public List<ExperienciaProfesionalExt> getexpProfesionalPorcodigoPer(
			ExperienciaProfesional experienciaProfesional) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectExperiaciaByPersona(experienciaProfesional);
			if (asoc != null) {
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
	 * @param codPersona
	 * @return
	 */
	public List<ExperienciaProfesionalExt> getCargoExprofePersona(ExperienciaProfesional experienciaProfesional) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectCargoExprofePersona(experienciaProfesional);
			if (asoc != null) {
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
	 * 
	 * @fecha 11:22:19 a.m. 2018
	 * @param experienciaProfesional
	 * @return
	 */
	public ExperienciaProfesional updateExperienciaProfesionalSelective(ExperienciaProfesional experienciaProfesional) {
		ExperienciaProfesional exp = new ExperienciaProfesional();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			id = mapper.updateByPrimaryKeySelective(experienciaProfesional);
			session.commit();
			if (id > 0) {
				exp.setError(false);
				exp.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			} else {
				exp.setError(false);
				exp.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return exp;
		} catch (Exception ex) {
			exp.setError(true);
			exp.setMensaje(UtilsConstantes.MSG_EXEPCION);
			exp.setMensajeTecnico(ex.getMessage());
			return exp;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public List<ExperienciaProfesionalExt> obtenerEmpleoActual(ExperienciaProfesionalExt experienciaProfesional) {
		List<ExperienciaProfesionalExt> experiencia = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			experiencia = mapper.selectEmpleoActual(experienciaProfesional);
			if (experiencia != null) {
				return experiencia;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception e) {
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public List<ExperienciaProfesionalExt> obtenerEmpleosAnteriores(ExperienciaProfesional experienciaProfesional) {
		List<ExperienciaProfesionalExt> experiencia = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			experiencia = mapper.selectEmpleosAnteriores(experienciaProfesional);
			if (experiencia != null) {
				return experiencia;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception e) {
			LoggerSigep.getInstance().error(e.getMessage(), ExperienciaProfesionalService.class);
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * @author Maria Alejandra C
	 * @return  List<ExperienciaProfesionalExt
	 * Lista todas las entidades que son diferentes a las entidades publicas.
	 */
	public List<ExperienciaProfesionalExt> getEntidadesPrivadas(ExperienciaProfesionalExt experienciaProfesionalExt) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectEntidadPriv(experienciaProfesionalExt);
			if (asoc != null) {
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
	 * @return  List<ExperienciaProfesionalExt
	 * Lista todas las dependencias que contengan el filtro de busqueda enviado
	 */
	public List<ExperienciaProfesionalExt> getDependenciasFilterLike(ExperienciaProfesionalExt experienciaProfesionalExt) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectDependenciasFilterLike(experienciaProfesionalExt);
			if (asoc != null) {
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
	 * @return  List<ExperienciaProfesionalExt
	 * Lista todas los cargos de entidad publicas que contengan el filtro de busqueda enviado
	 */
	public List<ExperienciaProfesionalExt> getCargosFilterLikeEntidadPublica(ExperienciaProfesionalExt experienciaProfesionalExt) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectCargosFilterLikeEntidadPublica(experienciaProfesionalExt);
			if (asoc != null) {
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
	 * @return  List<ExperienciaProfesionalExt
	 * Lista todas los cargos de entidad publicas que contengan el filtro de busqueda enviado
	 */
	public List<ExperienciaProfesionalExt> getCargosFilterLikeEntidadPrivada(ExperienciaProfesionalExt experienciaProfesionalExt) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectCargosFilterLikeEntidadPrivada(experienciaProfesionalExt);
			if (asoc != null) {
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
	 * @return  List<ExperienciaProfesionalExt>
	 * Lista todas las experiencias profesionales y docentes de la persona enviada. 
	 * Retorna la siguiente información: cod_jornada_laboral, cod_tipo_entidad, fecha_ingreso, fecha_retiro y flg_contratista.
	 */
	public List<ExperienciaProfesionalExt> getExperienciaProfesionalYDocente(ExperienciaProfesionalExt experienciaProfesionalExt) {
		List<ExperienciaProfesionalExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ExperienciaProfesionalMapper mapper = session.getMapper(ExperienciaProfesionalMapper.class);
			asoc = mapper.selectExperienciaProfesionalYDocente(experienciaProfesionalExt);
			if (asoc != null) {
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