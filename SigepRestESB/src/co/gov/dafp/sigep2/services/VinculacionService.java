package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Vinculacion;
import co.gov.dafp.sigep2.bean.VinculacionExample;
import co.gov.dafp.sigep2.bean.ext.EntidadExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.SituacionAdminVinculacionExt;
import co.gov.dafp.sigep2.bean.ext.VinculacionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.VinculacionMapper;
import co.gov.dafp.sigep2.portal.BusquedaPortal;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla VinculacionService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class VinculacionService implements Serializable{

	private static final long serialVersionUID = 5014313860505060236L;
	
	/**
	 * @param Vinculacion
	 * @return
	 */
	public Vinculacion insertVinculacion (Vinculacion vinculacion){
		Vinculacion asoc = new Vinculacion();
		SqlSession session = null;
		int id = 0;
		try {
			vinculacion.setAudFechaActualizacion(new Date());
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			id =  mapper.insert(vinculacion);
			if(id > 0){
				asoc.setError(false);
				asoc.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				asoc.setError(false);
				asoc.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return asoc;
		}catch (Exception ex) {
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
	 * 
	 * @param Vinculacion
	 * @return
	 */
	public Vinculacion deleteVinculacion(BigDecimal codVinculacion){
		Vinculacion asoc = new Vinculacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			id =  mapper.deleteByPrimaryKey(codVinculacion);
			if(id > 0){
				session.commit();
				asoc.setError(false);
			}else{
				asoc.setError(true);
				asoc.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return asoc;
		}catch (Exception ex) {
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
	 * 
	 * @param Vinculacion
	 * @return
	 */
	public Vinculacion updateVinculacion(Vinculacion vinculacion){
		Vinculacion param = new Vinculacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			id = mapper.updateByPrimaryKey(vinculacion);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:59:01 p. m.
	 * @return
	 */
	public List<Vinculacion> getVinculacionByAll(){
		List<Vinculacion> asoc = new ArrayList<>();
		VinculacionExample dtoObject = new VinculacionExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:58:56 p. m.
	 * @param persona
	 * @return
	 */
	public Vinculacion getVinculacionPersona(PersonaExt persona){
		Vinculacion asoc = new Vinculacion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectPersona(persona);
			if(asoc != null){
				return asoc;
			}else{
				return new Vinculacion();
			}
		}catch(Exception ex){
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return new Vinculacion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public List<Vinculacion> getVinculacionPersona(Vinculacion vinculacion){
		List<Vinculacion> asoc = new ArrayList<>();
		VinculacionExample dtoObject = new VinculacionExample();
		if(vinculacion!=null && vinculacion.getCodPersona()!=null) {
			dtoObject.createCriteria().andCodPersonaEqualTo(vinculacion.getCodPersona());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
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
	 * @param situacionAdministrativa
	 * @return
	 */
	public Vinculacion getVinculacionPersona(SituacionAdminVinculacionExt situacionAdministrativa){
		Vinculacion asoc = new Vinculacion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.vinculacionPersona(situacionAdministrativa);
			if(asoc != null){
				return asoc;
			}else{
				return new Vinculacion();
			}
		}catch(Exception ex){
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return new Vinculacion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metodo para retornar una lista de datos de Vinculacion por filtro
	 * @param vinculacion
	 * @return List<Vinculacion>
	 */
	public List<VinculacionExt> getVinculacionFilter(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(2000);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionFilter(vinculacionExt);
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
	 * Metodo para retornar una lista de datos de Vinculacion por filtro
	 * @param vinculacion
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> vinculacionPersonafiltro(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.vinculacionPersonafiltro(vinculacionExt);
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
	 * Metodo para retornar una lista de datos de Vinculacion de diferente entidad
	 * @param vinculacion
	 * @return List<Vinculacion>
	 */
	public List<VinculacionExt> getVinculacionDiferenteEntidad(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionDiferenteEntidad(vinculacionExt);
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
	 * Metodo para retornar una lista de datos de Vinculacion por usuario con datos parametricos
	 * @param vinculacion
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> getMostrarVinculaciones(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectMostrarVinculaciones(vinculacionExt);
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
	 * Metodo para mostrar las vinculaciones que tengan una situacion administrativa con comision/encargo
	 * @param vinculacion
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> getVinculacionSituacionAdmin(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionSituacionAdmin(vinculacionExt);
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
	 * Metodo para mostrar las vinculaciones que tengan una situacion administrativa con periodo de prueba
	 * @param vinculacion
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> getVinculacionSituacionAdminPeriodoPrueba(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionSituacionAdminPeriodoPrueba(vinculacionExt);
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
	 * Metodo para retornar una lista de datos de Vinculacion por usuario con datos de situacion administrativa periodo de prueba
	 * @param vinculacion
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> getVinculacionPeriodoDePrueba(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(500);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectMostrarVinculacionPeriodoDePrueba(vinculacionExt);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metodo para retornar los datos de Vinculacion por medio del id
	 * @param codVinculacion
	 * @return EntidadCargo
	 */
	public Vinculacion getVinculacionById(BigDecimal codVinculacion) {
		Vinculacion asoc = new Vinculacion();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper mapper = session.getMapper(VinculacionMapper.class);
			asoc = mapper.selectByPrimaryKey(codVinculacion);
			if (asoc!= null) {
				return asoc;
			} else {
				return new Vinculacion();
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
	 * Metodo para retornar una lista de datos de Vinculacion por usuario con datos de la vinculacion actual
	 * @param vinculacion
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> getVinculacionActual(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(500);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectMostrarVinculacionActual(vinculacionExt);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
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
	* @fecha 13/08/2018 3:01:48 p.m.
	* @param codPersona
	* @return
	*
	 */
	public List<Vinculacion> getVinculacionesActivas(BigDecimal codPersona){
		List<Vinculacion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionesActivas(codPersona);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			System.err.println(ex.getMessage());
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * Metodo para retornar una lista de datos de Vinculacion o desvinculacion con la que cuenta una persona,
	 *  ordenadas por la fecha de actualizacion
	 * @param vinculacion
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> getUltimaVinculacionDesvinculacion(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectUltimaVinDesv(vinculacionExt);
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
	 * @fecha 23 ago. 2018 10:42:50
	 * @param vinculacionExt
	 * @return
	 */
	public List<VinculacionExt> getVacantes(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVacantes(vinculacionExt);
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
	 * @fecha 10 sep. 2018 10:42:50
	 * @param vinculacionExt
	 * @return
	 */
	public List<VinculacionExt> getcargosfuncionario(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectCargosFuncionario(vinculacionExt);
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
	 * @fecha 03 sep. 2018 10:42:50
	 * @param vinculacionExt
	 * @return
	 */
	public List<VinculacionExt> getVacantesTempGlobal(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVacantesTempGlobal(vinculacionExt);
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
	 * @fecha 04 sep. 2018 10:42:50
	 * @param vinculacionExt
	 * @return
	 */
	public List<VinculacionExt> getVacantesTemyPermGlobal(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVacantesTemyPermGlobal(vinculacionExt);
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
	 * @fecha 04 sep. 2018 10:42:50
	 * @param vinculacionExt
	 * @return
	 */
	public List<VinculacionExt> getVacantesGlobal(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVacantesGlobal(vinculacionExt);
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
	
	public Vinculacion getVinculacionByPersonaEntidad(VinculacionExt vinculacionExt) {
		Vinculacion asoc = new Vinculacion();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper mapper = session.getMapper(VinculacionMapper.class);
			asoc = mapper.selectVinculacionDes(vinculacionExt);
			if (asoc == null) {
				asoc = new Vinculacion();
				asoc.setError(true);
				asoc.setMensaje(UtilsConstantes.MSG_SIN_INFORMACION_USUARIO);
			}else{
				asoc.setError(false);
			}
			return asoc;
		} catch (Exception ex) {
			asoc = new Vinculacion();
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
	 * @author Maria Alejandra Colorado
	 * Metodo que retorna las vinculaciones por Planta de personal
	 * @param vinculacionExt
	 * @return
	 */
	public List<VinculacionExt> getVinculacionPorPlanta(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionPorPlantaPersonal(vinculacionExt);
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
	 * Servicio que desactiva pasivamente todas las vinculaciones que tiene actualmente una planta para todos sus cargos
	 * @author Maria Alejandra Colorado
	 * @param Vinculacion
	 * @return
	 */
	public VinculacionExt updateDesvinculacionAutomaticaPorPlanta(VinculacionExt vinculacion){
		VinculacionExt param = new VinculacionExt();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			id = mapper.updateDesvinculacionAutomaticaPlanta(vinculacion);
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
	 * Metodo para retornar una lista de datos de Vinculacion de la misma entidad
	 * @param vinculacion
	 * @return List<Vinculacion>
	 */
	public List<VinculacionExt> getVinculacionMismaEntidad(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(100);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionMismaEntidad(vinculacionExt);
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
	 * Metodo que retorna la lista de vinculaciones que tienen un codigo de cargo especifico
	 * @param vinculacion
	 * @return List<Vinculacion>
	 */
	public List<VinculacionExt> getVinculacionSenadoresCongresistas(EntidadExt entidad){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionSenadoresCongresistas(entidad);
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
	 *
	 * @fecha:  Mar 27, 2019, 7:42:24 AM
	 * @param busquedaPortal
	 * @return
	 */
	public Long getCodEntidadvinculado(BusquedaPortal busquedaPortal){
		Long asoc;
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectPersonaEntidad(busquedaPortal);
			return asoc;
		}catch(Exception ex){
			return null;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * Elaborado por Maria Alejandra C
	 * @fecha 08 sep. 2019 10:42:50
	 * @param vinculacionExt
	 * @return
	 */
	public List<VinculacionExt> getVacantesTemyPermGlobalyEstructural(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVacantesTemyPermGlobalyEstructural(vinculacionExt);
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
	 * Elaborado por Maria Alejandra C
	 * @fecha 10/01/2020 
	 * @param vinculacionExt
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> getVinculacionAlerta(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinDesvAlerta(vinculacionExt);
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
	 * Elaborado por Maria Alejandra C
	 * @fecha 10/01/2020 
	 * @param vinculacionExt
	 * @return List<VinculacionExt>
	 */
	public List<VinculacionExt> getSituacionesAdminAlerta(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectSituacionesAdmAlerta(vinculacionExt);
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
	 * Metodo para retornar una lista de datos de Vinculacion que no poseen SA o que tienen pero la fecha
	 * de inicio es mayor a la fecha de posesion de la vinculacion a realizar o del sysdate
	 * @param vinculacion
	 * @return List<Vinculacion>
	 */
	public List<VinculacionExt> getVinculacionSinSAXProcesar(VinculacionExt vinculacionExt){
		List<VinculacionExt> asoc = new ArrayList<>();
		if(vinculacionExt.getLimitEnd() == null || vinculacionExt.getLimitEnd() == 0) {
			vinculacionExt.setLimitInit(0);
			vinculacionExt.setLimitEnd(2000);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper  mapper = session.getMapper(VinculacionMapper.class);
			asoc =  mapper.selectVinculacionSinSAXProcesar(vinculacionExt);
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
	 * Metodo para retornar el ultimo regisrtro de vinculación asignada a un cargo
	 * @param codVinculacion
	 * @return EntidadCargo
	 */
	public VinculacionExt getultimaVinculacionByCargo(VinculacionExt vinc) {
		VinculacionExt asoc = new VinculacionExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VinculacionMapper mapper = session.getMapper(VinculacionMapper.class);
			asoc = mapper.selectUltimaVinculacionByCargo(vinc);
			if (asoc!= null) {
				return asoc;
			} else {
				return new VinculacionExt();
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
}
