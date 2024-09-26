package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Estructura;
import co.gov.dafp.sigep2.bean.EstructuraExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EstructuraMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jesus Torres.
 * @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Estructura
 * @Proyect SIGEPII
 * @Company ADA S.A
 * @Module Exposicion de servicios Rest
 */
public class EstructuraService implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Estructura insertEstructura(Estructura estructura){
		Estructura arch =  new Estructura();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EstructuraMapper  mapper = session.getMapper(EstructuraMapper.class);
			id =  mapper.insert(estructura);
			if(id > 0){
				arch.setError(false);
				arch.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				arch.setError(false);
				arch.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return arch;
		}catch (Exception ex) {
			arch.setError(true);
			arch.setMensaje(UtilsConstantes.MSG_EXEPCION);
			arch.setMensajeTecnico(ex.getMessage());
			return arch;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**

	 */
	public Estructura updateEstructura(Estructura estructura){
		Estructura arch =  new Estructura();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EstructuraMapper  mapper = session.getMapper(EstructuraMapper.class);
			id = mapper.updateByPrimaryKey(estructura);
			if(id > 0){
				arch.setError(false);
				arch.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				arch.setError(false);
				arch.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return arch;
		}catch (Exception ex) {
			arch.setError(true);
			arch.setMensaje(UtilsConstantes.MSG_EXEPCION);
			arch.setMensajeTecnico(ex.getMessage());
			return arch;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}	
	/**
	 * 
	 * @param codEntidad
	 * @return
	 */
	public Estructura getEstructuraByEntidad(BigDecimal codEntidad){
		List<Estructura> arch =  new ArrayList<>();
		EstructuraExample obdj = new EstructuraExample();
		obdj.createCriteria().andCodEntidadEqualTo(codEntidad);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EstructuraMapper  mapper = session.getMapper(EstructuraMapper.class);
			arch = mapper.selectByExample(obdj);
			if(!arch.isEmpty())
			  return arch.get(0);
			else
			 return new Estructura();
		}catch (Exception ex) {
			Estructura arc = new Estructura();
			arc.setError(true);
			arc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			arc.setMensajeTecnico(ex.getMessage());
			return arc;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}	
	/**
	 * 
	 * @param codEstructura
	 * @return
	 */
	public Estructura getEstructuraById(Long codEstructura){
		Estructura arch =  new Estructura();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EstructuraMapper  mapper = session.getMapper(EstructuraMapper.class);
			arch = mapper.selectByPrimaryKey(codEstructura);
			if(arch != null)
			  return arch;
			else
			 return new Estructura();
		}catch (Exception ex) {
			arch.setError(true);
			arch.setMensaje(UtilsConstantes.MSG_EXEPCION);
			arch.setMensajeTecnico(ex.getMessage());
			return arch;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param estructura
	 * @return
	 */
	public List<Estructura> getEstructuraByFiltro(Estructura estructura){
		List<Estructura> arch =  new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EstructuraMapper  mapper = session.getMapper(EstructuraMapper.class);
			arch = mapper.selectByFiltro(estructura);
			if(!arch.isEmpty())
			  return arch;
			else
			 return new ArrayList<>();
		}catch (Exception ex) {
			Estructura arc = new Estructura();
			arc.setError(true);
			arc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			arc.setMensajeTecnico(ex.getMessage());
			arch.add(arc);
			return arch;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}	
}