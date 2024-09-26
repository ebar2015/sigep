package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.PersonaExample;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.VinculacionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PersonaMapper;
import co.gov.dafp.sigep2.utils.ErrorMensajes;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PersonaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PersonaService implements Serializable {

	private static final long serialVersionUID = -5527166708554932621L;
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:48:00 p. m.
	 * @param persona
	 * @return
	 */
	public boolean insertProcedure (PersonaExt persona){
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			int id =  mapper.insertPerson(persona);
			if(id == -1) {
				session.commit();
				return true;
			}else {
				return false;
			}
		}catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:48:10 p. m.
	 * @param persona
	 * @return
	 */
	public boolean insertPersona (Persona persona){
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			int id =  mapper.insert(persona);
			if(id > 0) {
				session.commit();
				return true;
			}else {
				return false;
			}
		}catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * @param Persona
	 * @return
	 */
	public boolean updatePersona(Persona persona){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			id = mapper.updateByPrimaryKeySelective(persona);
			session.commit();
			return (id==1)?true:false;
		}catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:48:20 p. m.
	 * @param limitIni
	 * @param limitEnd
	 * @return
	 */
	public List<PersonaExt> getPersonaByAll(int limitIni, int limitEnd){
		List<PersonaExt> asoc = new ArrayList<>();
		PersonaExample dtoObject = new PersonaExample();
		if(limitEnd > 1 ) {
			dtoObject.setLimitInit(limitIni);
			dtoObject.setLimitEnd(limitEnd);
		}else {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
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
	 * @param limitIni
	 * @param limitEnd
	 * @return
	 */
	public List<PersonaExt> getPersonapoapellido(Persona persona){
		List<PersonaExt> asoc = new ArrayList<>();
		PersonaExample dtoObject = new PersonaExample();
		dtoObject.createCriteria().andPrimerApellidoEqualTo(persona.getPrimerApellido());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
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
	 * @param limitIni
	 * @param limitEnd
	 * @return
	 */
	public List<PersonaExt> getPersonaControlInterno(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaControlInterno(persona);
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
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonaCargosFecha(Persona persona){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaCargosFecha(persona);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:48:35 p. m.
	 * @param persona
	 * @return
	 */
	public List<VinculacionExt> getPersonaCargosPosesion(Persona persona){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaCargosPosesion(persona);
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
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonaFiltro(Persona persona){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectByFilter(persona);
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
	 * @param persona
	 * @return
	 */
	public Persona personaById(Persona persona){
		Persona asoc = new Persona();
		PersonaExample dtoObject = new PersonaExample();
		dtoObject.createCriteria().andCodPersonaEqualTo(persona.getCodPersona());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectByPrimaryKey(persona.getCodPersona());
			if(asoc !=null){
				return asoc;
			}else{
				return new Persona();
			}
		}catch(Exception ex){
			return new Persona();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param persona
	 * @return
	 */
	public Persona personaByCodUsuario(Usuario usuairo){
		Persona asoc = new Persona();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectByCodUsuario(usuairo);
			mapper.updateTiket(usuairo);
			if(asoc !=null){
				return asoc;
			}else{
				return new Persona();
			}
		}catch(Exception ex){
			return new Persona();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metodo que retorna una Persona extendida con los nombres de los Id Relacionados
	 * @param persona
	 * @return
	 */
	public PersonaExt personaByIdext(Persona persona){
		PersonaExt asoc = new PersonaExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectByPrimaryKeyExt(persona.getCodPersona());
			if(asoc !=null){
				return asoc;
			}else{
				return new PersonaExt();
			}
		}catch(Exception ex){
			PersonaExt aut = new PersonaExt();
			aut.setError(true);
			aut.setMensaje(UtilsConstantes.MSG_EXEPCION);
			aut.setMensajeTecnico(ex.getMessage());
			return  aut;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * Metodo que retorna informacion de contacto de la persona
	 * @param persona
	 * @return
	 */
	public PersonaExt getPersonaContacto(BigDecimal codPersona){
		PersonaExt asoc = new PersonaExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaContacto(codPersona);
			if(asoc !=null){
				return asoc;
			}else{
				return new PersonaExt();
			}
		}catch(Exception ex){
			PersonaExt aut = new PersonaExt();
			aut.setError(true);
			aut.setMensaje(UtilsConstantes.MSG_EXEPCION);
			aut.setMensajeTecnico(ex.getMessage());
			return  aut;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metodo que retorna informacion de una perdona consultada por tipo de Id y numero de Identificacion
	 * @param persona (tipoDocuemnto, Numero documento)
	 * @return
	 */
	public PersonaExt personaByTipoIdNumId(Persona persona){
		List<PersonaExt> asoc = new ArrayList<>();
		PersonaExample dtoObject = new PersonaExample();
		if(persona!=null && persona.getCodTipoIdentificacion()!=null) {
			dtoObject.createCriteria().andCodTipoIdentificacionEqualTo(persona.getCodTipoIdentificacion()).
			andNumeroIdentificacionEqualTo(persona.getNumeroIdentificacion());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new PersonaExt();
			}
		}catch(Exception ex){
			PersonaExt aut = new PersonaExt();
			aut.setError(true);
			aut.setMensaje(UtilsConstantes.MSG_EXEPCION);
			aut.setMensajeTecnico(ex.getMessage());
			return  aut;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	 /**
     * @author: Jose Viscaya 
     * @fecha 24 Juli 2018
     * @param codPersona
     * @return Este Servicio devuelve una persona extendida
     */
	public PersonaExt personaBycodPersona(BigDecimal codPersona){
		PersonaExt asoc = new PersonaExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectByPrimaryKeyExt(codPersona);
			if(asoc != null){
				return asoc;
			}else{
				return new PersonaExt();
			}
		}catch(Exception ex){
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return  asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:48:52 p. m.
	 * @param persona
	 * @return
	 */
	public Persona personaByTipoIdNumIdCodEntidad(PersonaExt persona){
		Persona asoc = new Persona();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectByEntidad(persona);
			if(asoc!=null){
				return asoc;
			}else{
				return new Persona();
			}
		}catch(Exception ex){
			Persona aut = new Persona();
			aut.setError(true);
			aut.setMensaje(UtilsConstantes.MSG_EXEPCION);
			aut.setMensajeTecnico(ex.getMessage());
			return  aut;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:48:58 p. m.
	 * @param persona
	 * @return
	 */
	public PersonaExt getPersonaEntidadPorCodPeCodEnt(PersonaExt persona){
		PersonaExt asoc = new PersonaExt();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaEnt(persona);
			if(asoc!=null){
				return asoc;
			}else{
				return new PersonaExt();
			}
		}catch(Exception ex){
			PersonaExt aut = new PersonaExt();
			aut.setError(true);
			aut.setMensaje(UtilsConstantes.MSG_EXEPCION);
			aut.setMensajeTecnico(ex.getMessage());
			return  aut;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:49:03 p. m.
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonaEntidadPorCodPeCodEntFiltro(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		
		if(persona.getLimitInit() == null) {
			persona.setLimitInit(0);
		}
		if(persona.getLimitEnd() == null || persona.getLimitEnd() == 0) {
			persona.setLimitEnd(10);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaEntFiltro(persona);
			if(asoc!= null){
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:49:09 p. m.
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonaRolEntidad(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaRolEntidad(persona);
			if(asoc!= null){
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:49:19 p. m.
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonaEntidadPorCodPeCodEntFiltrof(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		
		if(persona.getLimitInit() == null) {
			persona.setLimitInit(0);
		}
		if(persona.getLimitEnd() == null || persona.getLimitEnd() == 0) {
			persona.setLimitEnd(10);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaEntFiltroF(persona);
			if(asoc!= null){
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
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonaHVFiltro(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		
		if(persona.getLimitInit() == null) {
			persona.setLimitInit(0);
		}
		if(persona.getLimitEnd() == null || persona.getLimitEnd() == 0) {
			persona.setLimitEnd(10);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaHVFiltro(persona);
			if(asoc!= null){
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
	 * Metodo para validar si la persona tiene un rol activo en la entidad
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonaRolActivoEntidad(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		if(persona.getLimitEnd() == null || persona.getLimitEnd() == 0) {
			persona.setLimitInit(0);
			persona.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaRolActivoEntidad(persona);
			if(asoc!= null){
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:49:31 p. m.
	 * @param personaExt
	 * @return
	 */
	public List<PersonaExt> getEmailPersonasDesvincular(PersonaExt personaExt){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectEmailPersonasDesvincular(personaExt);
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
	 * @fecha 9:53:45 a.m. 2018
	 * @param persona
	 * @return
	 */
	public ErrorMensajes updatePersonaSelective(Persona persona){
		ErrorMensajes err = new ErrorMensajes();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			id = mapper.updateByPrimaryKeySelective(persona);
			session.commit();
			if(id==1){
				err.setError(false);
				err.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			}else{
				err.setError(true);
				err.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return err;
		}catch (Exception ex) {
			err = new ErrorMensajes();
			err.setError(true);
			err.setMensaje(UtilsConstantes.MSG_EXEPCION);
			err.setMensajeTecnico(ex.getMessage());
			return err;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * 
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getpersonavinculacionfiltro(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		
		if(persona.getLimitInit() == null) {
			persona.setLimitInit(0);
		}
		if(persona.getLimitEnd() == null || persona.getLimitEnd() == 0) {
			persona.setLimitEnd(1000);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaVinculacionFiltro(persona);
			if(asoc!= null){
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
	 * @fecha 23 ago. 2018 14:06:48
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getpersonaActivas(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectByActivas(persona);
			if(asoc!= null){
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
	* @fecha 27/08/2018 3:55:46 p.m.
	* @param persona
	* @return
	*
	 */
	public List<PersonaExt> getpersonaActivasEntidad(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectAactivaEntidad(persona);
			if(asoc!= null){
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
	 * @param personaExt
	 * @return
	 */
	public List<PersonaExt> getPersonasPorRoles(PersonaExt personaExt){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonasPorRoles(personaExt);
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
	 * @author Maria Alejandra Colorado
	 * @param persona
	 * @return
	 * Metodo utilizado para el componente de asignar responsable entidad.
	 * Trae una consulta con las personas con rol gestionEntidad (Responsables) y 
	 * sus el total de asociaciones que tiene para las entidades en general, por 
	 * orden territorial y por orrden nacional
	 */
	public List<PersonaExt> getPersonaResponsableEntidad(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaResponsableEntidad(persona);
			if(asoc!= null){
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
	* @fecha 17/12/2018 07:55:46 am.m.
	* @param PersonaExt
	* @return
	*
	 */
	public List<PersonaExt> getPersonaEntidadPlanta(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaEntidadPlanta(persona);
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
	 * Elaborado Por: Jose Viscaya
	 * 30 ene. 2019
	 * PersonaService.java
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonasVinculadas(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonaVinculdaOP(persona);
			if(asoc!= null){
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
	 * Elaborado Por: Maria C
	 * 30 ene. 2019
	 * PersonaService.java
	 * @param persona
	 * @return
	 */
	public List<PersonaExt> getPersonasVinculadasPorEntidad(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonasVinculadas(persona);
			if(asoc!= null){
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
	
	public List<PersonaExt> selectPersonasRolesNotificaGI(PersonaExt persona){
		List<PersonaExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PersonaMapper  mapper = session.getMapper(PersonaMapper.class);
			asoc =  mapper.selectPersonasRolesNotificaGI(persona);
			if(asoc!= null){
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
}
