/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.NomenclaturaDenominacion;
import co.gov.dafp.sigep2.bean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.NomenclaturaDenominacionMapper;
import co.gov.dafp.sigep2.portal.NivelJerarquico;
import co.gov.dafp.sigep2.portal.NomenclaturaPortal;
import co.gov.dafp.sigep2.portal.RangoSalariosPortal;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author joseviscaya
 *
 */
public class NomenclaturaDenominacionService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6827382962831080374L;
	
	
	/**
	 * 
	 * @param NomenclaturaDenominacion
	 * @return
	 */
	public NomenclaturaDenominacion insertNomenclaturaDenominacion (NomenclaturaDenominacion nomenclaturaDenominacion){
		NomenclaturaDenominacion acre = new NomenclaturaDenominacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			id =  mapper.insert(nomenclaturaDenominacion);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param NomenclaturaDenominacion
	 * @return
	 */
	public NomenclaturaDenominacion updateNomenclaturaDenominacion(NomenclaturaDenominacion nomenclaturaDenominacion){
		NomenclaturaDenominacion acre = new NomenclaturaDenominacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			id = mapper.updateByPrimaryKey(nomenclaturaDenominacion);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param NomenclaturaDenominacion
	 * @return
	 */
	public NomenclaturaDenominacion updateNoenclatura(NomenclaturaDenominacion nomenclaturaDenominacion){
		NomenclaturaDenominacion acre = new NomenclaturaDenominacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			id = mapper.updateNoenclatura(nomenclaturaDenominacion);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;	
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
	public NomenclaturaDenominacion getNomenclaturaDenominacionId(NomenclaturaDenominacion nomenclaturaDenominacion){
		NomenclaturaDenominacion asoc = new NomenclaturaDenominacion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc =  mapper.selectByPrimaryKey(nomenclaturaDenominacion.getCodNomenclaturaDenominacion());
			if(asoc != null){
				return asoc;
			}else{
				return new NomenclaturaDenominacion();
			}
		}catch(Exception ex){
			return new NomenclaturaDenominacion();
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
	public List<NomenclaturaDenominacionExt> getNomenclaturaDenominacionByFiltro(NomenclaturaDenominacion nomenclaturaDenominacion){
		List<NomenclaturaDenominacionExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc =  mapper.selectByFiltro(nomenclaturaDenominacion);
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
	 * Metdodo para consultar cargos relacinados a una entidad de la tabla EntidadPlantaDetalle
	 * @return List<EntidadPlantaDetalle>
	 */
	public List<NomenclaturaDenominacionExt> getCargoAprobarHojaVida(NomenclaturaDenominacionExt nomenclaturaDenominacionExt) {
		List<NomenclaturaDenominacionExt> asoc = new ArrayList<>();
		if(nomenclaturaDenominacionExt.getLimitIni() == null) {
			nomenclaturaDenominacionExt.setLimitIni(0);
		}
		if(nomenclaturaDenominacionExt.getLimitFin() == null) {
			nomenclaturaDenominacionExt.setLimitFin(10000);
		}
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc = mapper.selectDenominacionesEntidad(nomenclaturaDenominacionExt);
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
	 * @author Maria Alejandra
	 * @fecha 25/02/2019
	 * @return NomenclaturaDenominacionExt
	 */
	public NomenclaturaDenominacionExt getCaracteristicaDenominacion(NomenclaturaDenominacionExt nomenclaturaDenominacionExt){
		NomenclaturaDenominacionExt asoc = new NomenclaturaDenominacionExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc =  mapper.selectCaracteristicaDenominacion(nomenclaturaDenominacionExt);
			return asoc;
		}catch(Exception ex){
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return asoc;	
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
	 * @fecha:  Mar 27, 2019, 7:46:53 AM
	 * @param codEntidad
	 * @return
	 */
	public List<RangoSalariosPortal> getRangoSalarios(Long codEntidad){
		List<RangoSalariosPortal> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc =  mapper.selectEscalaSalarial(codEntidad);
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
	 * @author: Maria Alejandra
	 * @fecha:  03/08/2020
	 * @param codEntidad
	 * @return
	 */
	public List<NomenclaturaPortal> getNomenclaturaEscala(Long codEntidad){
		List<NomenclaturaPortal> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc =  mapper.selectNomenclaturaEscala(codEntidad);
			if(asoc.isEmpty()) {
				return new ArrayList<>();
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
	 * @author: Maria Alejandra
	 * @fecha:  03/08/2020
	 * @param codEntidad
	 * @return
	 */
	public List<NivelJerarquico> getNivelJerarquicoNomenclaturaPortal(NomenclaturaPortal nomenclatura){
		List<NivelJerarquico> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc =  mapper.selectNivelJerarquicoNomenclatura(nomenclatura);
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
	 * @author: Maria Alejandra
	 * @fecha:  03/08/2020
	 * @param codEntidad
	 * @return
	 */
	public List<NivelJerarquico> getNivelJerarquicoDetallePortal(NomenclaturaDenominacionExt nomenclatura){
		List<NivelJerarquico> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc =  mapper.selectNivelJerarquicoDetalle(nomenclatura);
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
	 * @param NomenclaturaDenominacion
	 * @return
	 */
	public NomenclaturaDenominacion updateNomenclaturaDenominacionAsociada(NomenclaturaDenominacion nomenclaturaDenominacion){
		NomenclaturaDenominacion acre = new NomenclaturaDenominacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper  mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			id = mapper.updateNomenclaturaDenominacionAsociada(nomenclaturaDenominacion);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensaje(UtilsConstantes.MSG_EXEPCION);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metdodo para consultar el nivel jerarquico de una denominacion por nomenclatura
	 * @return List<NomenclaturaDenominacionExt>
	 */
	public List<NomenclaturaDenominacionExt> getNivelJerarquicoPorNomenclatura(NomenclaturaDenominacionExt nomenclaturaDenominacionExt) {
		List<NomenclaturaDenominacionExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc = mapper.selectNivelJerarquicoPorNomenclatura(nomenclaturaDenominacionExt);
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
	 * Metdodo para consultar los grados de una nomenclatura y nivel especifico
	 * @return List<NomenclaturaDenominacionExt>
	 */
	public List<NomenclaturaDenominacionExt> getGradoPorDenominacionNivel(NomenclaturaDenominacionExt nomenclaturaDenominacionExt) {
		List<NomenclaturaDenominacionExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc = mapper.selectGradoPorDenominacionNivel(nomenclaturaDenominacionExt);
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
	 * Metdodo para consultar los codigo de una cargo por su denominacion y nivel
	 * @return List<NomenclaturaDenominacionExt>
	 */
	public List<NomenclaturaDenominacionExt> getCodigoPorDenominacionNivel(NomenclaturaDenominacionExt nomenclaturaDenominacionExt) {
		List<NomenclaturaDenominacionExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc = mapper.selectCodigoPorDenominacionNivel(nomenclaturaDenominacionExt);
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
	 * Metdodo para consultar el nivel jerarquico de una denominacion por el cod_entidad
	 * @return List<NomenclaturaDenominacionExt>
	 */
	public List<NomenclaturaDenominacionExt> getNivelJerarquicoXEntidad(NomenclaturaDenominacionExt nomenclaturaDenominacionExt) {
		List<NomenclaturaDenominacionExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc = mapper.selectNivelJerarquicoXEntidad(nomenclaturaDenominacionExt);
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
	 * Metdodo para consultar los codigo de una cargo por entidad
	 * @return List<NomenclaturaDenominacionExt>
	 */
	public List<NomenclaturaDenominacionExt> getCodigoDenominacionXEntidad(NomenclaturaDenominacionExt nomenclaturaDenominacionExt) {
		List<NomenclaturaDenominacionExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc = mapper.selectCodigoDenominacionXEntidad(nomenclaturaDenominacionExt);
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
	 * Metdodo para consultar los codigo de una cargo por entidad
	 * @return List<NomenclaturaDenominacionExt>
	 */
	public List<NomenclaturaDenominacionExt> getGradoDenominacionXEntidad(NomenclaturaDenominacionExt nomenclaturaDenominacionExt) {
		List<NomenclaturaDenominacionExt> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaDenominacionMapper mapper = session.getMapper(NomenclaturaDenominacionMapper.class);
			asoc = mapper.selectGradoDenominacionXEntidad(nomenclaturaDenominacionExt);
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
