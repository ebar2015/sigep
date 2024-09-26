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

import co.gov.dafp.sigep2.bean.Declaracion;
import co.gov.dafp.sigep2.bean.DeclaracionExample;
import co.gov.dafp.sigep2.bean.ext.DeclaracionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DeclaracionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Declaracion
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class DeclaracionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4437322836870087812L;
	/**
	 * 
	 * @param Declaracion
	 * @return
	 */
	public Declaracion insertDeclaracion (Declaracion declaracion){
		Declaracion dec = new Declaracion();
		SqlSession session = null;
		int id = 0;
		declaracion.setAudFechaActualizacion(new Date());
		declaracion.setConfirmacion((short) 0);
		declaracion.setFlgAcreenciaObligacion((short) 0);
		declaracion.setFlgActividadEconomicaPrivada((short) 0);
		declaracion.setFlgSinBienesPatrimoniales((short) 0);
		declaracion.setFlgSinCuentasAhorro((short) 0);
		declaracion.setFlgSinParientesConyugues((short) 0);
		declaracion.setFlgSinParticipacionJuntas((short) 0);
		declaracion.setFlgTengoIngresosLaborales((short) 0);
		declaracion.setFlgTengoOtrosIngresos((short) 0);
		declaracion.setConfirmacion((short) 0);
		declaracion.setTotalIngresos(new BigDecimal(0));
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			id =  mapper.insert(declaracion);
			if(id > 0){
				dec.setError(false);
				dec.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
				Declaracion dec2 = new Declaracion();
				dec2.setCodPersona(declaracion.getCodPersona());
				dec2.setAnnoDeclaracion(declaracion.getAnnoDeclaracion());
				dec2.setCodTipoDeclaracion(declaracion.getCodTipoDeclaracion());
				dec2.setAudFechaActualizacion(declaracion.getAudFechaActualizacion());
				dec.setMensajeTecnico(getIdDeclaracion(dec2)+"");
			}else{
				dec.setError(false);
				dec.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return dec;
		}catch (Exception ex) {
			dec.setError(true);
			dec.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dec.setMensajeTecnico(ex.getMessage());
			return dec;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Declaracion
	 * @return
	 */
	public Declaracion updateDeclaracion(Declaracion declaracion){
		Declaracion dec = new Declaracion();
		SqlSession session = null;
		int id = 0;
		if(declaracion.getConfirmacion() == 1) {
			Declaracion decla = getTotalDeclaracion(declaracion.getCodDeclaracion());
			declaracion.setTotalIngresos(decla.getTotalIngresos());
		}
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			id = mapper.updateByPrimaryKey(declaracion);
			if(id > 0){
				dec.setError(false);
				dec.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				dec.setError(false);
				dec.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return dec;
		}catch (Exception ex) {
			dec.setError(true);
			dec.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dec.setMensajeTecnico(ex.getMessage());
			return dec;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Declaracion
	 * @return
	 */
	public Declaracion updateDeclaracionSelective(Declaracion declaracion){
		DeclaracionExample dtoObject = new DeclaracionExample();
		if(declaracion!=null && declaracion.getCodDeclaracion()!=null) {
			dtoObject.createCriteria().andCodDeclaracionEqualTo(declaracion.getCodDeclaracion());	
		}
		declaracion.setCodDeclaracion(null);
		Declaracion dec = new Declaracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			id = mapper.updateByExampleSelective(declaracion, dtoObject);
			if(id > 0){
				dec.setError(false);
				dec.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				dec.setError(false);
				dec.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return dec;
		}catch (Exception ex) {
			dec.setError(true);
			dec.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dec.setMensajeTecnico(ex.getMessage());
			return dec;
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
	public List<Declaracion> getDeclaracionPersona(Declaracion declaracion){
		List<Declaracion> asoc = new ArrayList<>();
		DeclaracionExample dtoObject = new DeclaracionExample();
		if(declaracion.getCodPersona()!= null && declaracion.getCodPersona().longValue() > 0
			&& declaracion.getConfirmacion() != null) {
			dtoObject.createCriteria().andCodPersonaEqualTo(declaracion.getCodPersona()).
			andConfirmacionEqualTo(declaracion.getConfirmacion());
		}
		if(declaracion.getCodPersona()!= null && declaracion.getCodPersona().longValue() > 0
				&& declaracion.getConfirmacion() != null && declaracion.getCodTipoDeclaracion() != null) {
				dtoObject.createCriteria().andCodPersonaEqualTo(declaracion.getCodPersona()).
				andConfirmacionEqualTo(declaracion.getConfirmacion()).
				andCodTipoDeclaracionEqualTo(declaracion.getCodTipoDeclaracion());
		}
		if(declaracion.getCodPersona()!= null && declaracion.getCodPersona().longValue() > 0
				&& declaracion.getConfirmacion() != null && declaracion.getCodTipoDeclaracion() != null
				&& declaracion.getAnnoDeclaracion() !=null) {
				dtoObject.createCriteria().andCodPersonaEqualTo(declaracion.getCodPersona()).
				andConfirmacionEqualTo(declaracion.getConfirmacion()).
				andCodTipoDeclaracionEqualTo(declaracion.getCodTipoDeclaracion()).
				andAnnoDeclaracionEqualTo(declaracion.getAnnoDeclaracion());
		}
		
		if(declaracion.getCodPersona()!= null && declaracion.getCodPersona().longValue() > 0
				&& declaracion.getConfirmacion() != null
				&& declaracion.getAnnoDeclaracion() !=null) {
				dtoObject.createCriteria().andCodPersonaEqualTo(declaracion.getCodPersona()).
				andConfirmacionEqualTo(declaracion.getConfirmacion()).
				andAnnoDeclaracionEqualTo(declaracion.getAnnoDeclaracion());
		}
		
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
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
	 * @return
	 */
	public List<Declaracion> getDeclaracionPersonaTipo(Declaracion declaracion){
		List<Declaracion> asoc = new ArrayList<>();
		DeclaracionExample dtoObject = new DeclaracionExample();
		dtoObject.createCriteria().andCodPersonaEqualTo(declaracion.getCodPersona()).
		andCodTipoDeclaracionEqualTo(declaracion.getCodTipoDeclaracion());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
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
	 * @return
	 */
	public List<Declaracion> getDeclaracionCreacionn(Declaracion declaracion){
		List<Declaracion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			asoc =  mapper.selectDeclaracionCreacion(declaracion);
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
	 * @return
	 */
	public List<DeclaracionExt> getDeclaracionfiltro(DeclaracionExt declaracion){
		List<DeclaracionExt> asoc = new ArrayList<>();
		if(declaracion.getFlgExtemporanea() != null) {
			if(declaracion.getFlgExtemporanea() == 0) {
				declaracion.setFlgExtemporanea(null);
			}
		}
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);

				if(declaracion.getFlgAdm() ==1){
					asoc =  mapper.selectDeclaracionFiltro(declaracion);/*con filtro entidad inner join a usuario entidad*/
				}else {
					asoc =  mapper.selectDeclaracionFiltroRol(declaracion);
				}
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
	 * @param Id Search
	 * @return
	 */
	public DeclaracionExt getDeclaracionById(BigDecimal id){
		DeclaracionExt asoc = new DeclaracionExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc !=null){
				return asoc;
			}else{
				return new DeclaracionExt();
			}
		}catch(Exception ex){
		
			return new DeclaracionExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public Declaracion getDeclaracioDate(Declaracion declaracion){
		Declaracion asoc = new Declaracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			asoc =  mapper.selectDate(declaracion);
			if(asoc !=null){
				return asoc;
			}else{
				return new Declaracion();
			}
		}catch(Exception ex){
			return new Declaracion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public Declaracion getDeclaracionUltima(BigDecimal codPersona){
		Declaracion asoc = new Declaracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			asoc =  mapper.selectUltima(codPersona);
			if(asoc !=null){
				return asoc;
			}else{
				return new Declaracion();
			}
		}catch(Exception ex){
			
			return new Declaracion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public List<DeclaracionExt> getDeclaracionesParaExt(BigDecimal codPersona){
		DeclaracionExt dec;
		List<DeclaracionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			dec =  mapper.select822(codPersona);
			if(dec != null) {
				asoc.add(dec);
			}
			dec =  mapper.select743(codPersona);
			if(dec != null) {
				asoc.add(dec);
			}
			dec =  mapper.select741(codPersona);
			if(dec != null) {
				asoc.add(dec);
			}
			dec =  mapper.selectModificadas(codPersona);
			if(dec != null) {
				asoc.add(dec);
			}
			return asoc;
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
	 * @param Id Search
	 * @return
	 */
	public Declaracion getDeclaracionCarga(BigDecimal codPersona){
		Declaracion asoc = new Declaracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			asoc =  mapper.selectCarga(codPersona);
			if(asoc !=null){
				return asoc;
			}else{
				return new Declaracion();
			}
		}catch(Exception ex){
			
			return new Declaracion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public DeclaracionExt getTotalDeclaracion(BigDecimal codDeclaracion){
		DeclaracionExt asoc = new DeclaracionExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			asoc =  mapper.selectTotalDeclaracion(codDeclaracion);
			if(asoc !=null){
				return asoc;
			}else{
				return new DeclaracionExt();
			}
		}catch(Exception ex){
			return new DeclaracionExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param declaracion
	 * @return
	 */
	public Long getIdDeclaracion(Declaracion declaracion){
		Declaracion asoc = new Declaracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DeclaracionMapper  mapper = session.getMapper(DeclaracionMapper.class);
			asoc =  mapper.selectIdDeclaracion(declaracion);
			if(asoc.getCodDeclaracion() !=null){
				return asoc.getCodDeclaracion().longValue();
			}else{
				return new Long(0);
			}
		}catch(Exception ex){
			return new Long(0);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
}
