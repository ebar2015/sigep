package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ModificacionContrato;
import co.gov.dafp.sigep2.bean.ModificacionContratoExample;
import co.gov.dafp.sigep2.bean.UsuarioSessionExample;
import co.gov.dafp.sigep2.bean.ext.ModificacionContratoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ModificacionContratoMapper;
import co.gov.dafp.sigep2.interfaces.UsuarioSessionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ModificacionContratoService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ModificacionContratoService implements Serializable {

	private static final long serialVersionUID = -2488309245865864473L;
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:30:33 p. m.
	 * @param modificacionContrato
	 * @return
	 */
	public ModificacionContrato insertModificacionContrato (ModificacionContrato modificacionContrato){
		ModificacionContrato mod = new ModificacionContrato();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionContratoMapper  mapper = session.getMapper(ModificacionContratoMapper.class);
			id =  mapper.insert(modificacionContrato);
			if(id > 0){
				mod.setError(false);
				mod.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				mod.setError(false);
				mod.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return mod;
		}catch (Exception ex) {
			mod.setError(true);
			mod.setMensaje(UtilsConstantes.MSG_EXEPCION);
			mod.setMensajeTecnico(ex.getMessage());
			return mod;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param ModificacionContrato
	 * @return
	 */
	public ModificacionContrato updateModificacionContrato(ModificacionContrato modificacionContrato){
		ModificacionContrato mod = new ModificacionContrato();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionContratoMapper  mapper = session.getMapper(ModificacionContratoMapper.class);
			id = mapper.updateByPrimaryKey(modificacionContrato);
			if(id > 0){
				mod.setError(false);
				mod.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				mod.setError(false);
				mod.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return mod;
		}catch (Exception ex) {
			mod.setError(true);
			mod.setMensaje(UtilsConstantes.MSG_EXEPCION);
			mod.setMensajeTecnico(ex.getMessage());
			return mod;	
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
	public List<ModificacionContratoExt> getModificacionContrato(long codContrato){
		List<ModificacionContratoExt> asoc = new ArrayList<>();
		ModificacionContratoExample dtoObject = new ModificacionContratoExample();
		dtoObject.createCriteria().andCodContratoEqualTo(codContrato);
		dtoObject.setOrderByClause("FECHA_AUTORIZACION_MODIFICACION");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionContratoMapper  mapper = session.getMapper(ModificacionContratoMapper.class);
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
	 * Creado Por: Adrian Andrade
	 * @return
	 * 27 nov. 2023 9:13:46	 
	 */
	public List<ModificacionContratoExt> selectMontoContratoInicial(long codNuevoContratista){		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionContratoMapper  mapper = session.getMapper(ModificacionContratoMapper.class);
			List<ModificacionContratoExt> mod =   mapper.selectMontoContratoInicial(codNuevoContratista);
			return mod;
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
	 * @return
	 */
	public List<ModificacionContratoExt> getModificacionContratoPorTipoMod(ModificacionContrato modificacionContrato){
		List<ModificacionContratoExt> asoc = new ArrayList<>();
		ModificacionContratoExample dtoObject = new ModificacionContratoExample();
		if(modificacionContrato.getCodTipoModificacionContrato()!=null 
				&& modificacionContrato.getCodTipoModificacionContrato().longValue() > 0
				&& modificacionContrato.getCodContrato() != null 
				&& modificacionContrato.getCodContrato().longValue() > 0){
			dtoObject.createCriteria().andCodContratoEqualTo(modificacionContrato.getCodContrato()).
			andCodTipoModificacionContratoEqualTo(modificacionContrato.getCodTipoModificacionContrato()).
			andFlgActivoEqualTo(modificacionContrato.getFlgActivo());
		}else if(modificacionContrato.getCodContrato() != null 
				&& modificacionContrato.getCodContrato().longValue() > 0) {
			dtoObject.createCriteria().andCodContratoEqualTo(modificacionContrato.getCodContrato()).
			andFlgActivoEqualTo(modificacionContrato.getFlgActivo());
		}else {
			dtoObject.createCriteria().andCodContratoEqualTo((long) 0);
		}
		dtoObject.setOrderByClause("COD_MODIFICACION_CONTRATO DESC");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionContratoMapper  mapper = session.getMapper(ModificacionContratoMapper.class);
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
	 * @param Id Search
	 * @return
	 */
	public ModificacionContratoExt getModificacionContratoById(long id){
		ModificacionContratoExt asoc = new ModificacionContratoExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionContratoMapper  mapper = session.getMapper(ModificacionContratoMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new ModificacionContratoExt();
			}
		}catch(Exception ex){
		
			return new ModificacionContratoExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	

}
