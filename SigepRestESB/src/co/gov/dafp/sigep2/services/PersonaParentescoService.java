package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PersonaParentesco;
import co.gov.dafp.sigep2.bean.ext.PersonaParentescoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PersonaParentescoMapper;
/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PersonaParentescoService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PersonaParentescoService implements Serializable {
	private static final long serialVersionUID = 1468521375197359433L;

	/**
	 * @param PersonaParentesco
	 * @return
	 */
	public boolean insertPersonaParentesco(PersonaParentesco personaParentesco) {
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaParentescoMapper mapper = session.getMapper(PersonaParentescoMapper.class);
			id = mapper.insert(personaParentesco);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:46:57 p. m.
	 * @param personaParentesco
	 * @return
	 */
	public boolean insertPersonaParentescoS(PersonaParentesco personaParentesco) {
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaParentescoMapper mapper = session.getMapper(PersonaParentescoMapper.class);
			id = mapper.insertSelective(personaParentesco);
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
	 * @param PersonaParentesco
	 * @return
	 */
	public boolean updatePersonaParentesco(PersonaParentesco personaParentesco) {
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaParentescoMapper mapper = session.getMapper(PersonaParentescoMapper.class);
			id = mapper.updateByPrimaryKeySelective(personaParentesco);
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
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:47:09 p. m.
	 * @param personaParentesco
	 * @return
	 */
	public List<PersonaParentescoExt> getPersonaParentescoPersona(PersonaParentesco personaParentesco) {
		List<PersonaParentescoExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaParentescoMapper mapper = session.getMapper(PersonaParentescoMapper.class);
			asoc = mapper.selectParentescoFiltro(personaParentesco);
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
	 * @fecha 27/07/2018 2:47:19 p. m.
	 * @param id
	 * @return
	 */
	public PersonaParentescoExt getPersonaParentescoById(int id) {
		PersonaParentescoExt asoc = new PersonaParentescoExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaParentescoMapper mapper = session.getMapper(PersonaParentescoMapper.class);
			asoc = mapper.selectByPrimaryKey(id);
			if (asoc != null) {
				return asoc;
			} else {
				return new PersonaParentescoExt();
			}
		} catch (Exception ex) {
			return new PersonaParentescoExt();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
