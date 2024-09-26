/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Nomenclatura;
import co.gov.dafp.sigep2.bean.NomenclaturaExample;
import co.gov.dafp.sigep2.bean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.NomenclaturaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Nomenclatura
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 27 de Julio de 2018
*/
public class NomenclaturaService implements Serializable {

	private static final long serialVersionUID = 2878695549101335370L;
	
	/**
	 * 
	 * @param Nomenclatura
	 * @return
	 */
	public Nomenclatura insertNomenclatura (Nomenclatura nomenclatura){
		Nomenclatura acre = new Nomenclatura();
		nomenclatura.setAudFechaActualizacion(new Date());
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			id =  mapper.insert(nomenclatura);
			if(id > 0){
				session.commit();
				nomenclatura.setAudFechaActualizacion(null);
				if(nomenclatura.getJustificacion() != null) {
					if(nomenclatura.getJustificacion().isEmpty()) {
						nomenclatura.setJustificacion(null);
					}
				}
				if(nomenclatura.getCodigoNomenclatura()!=null) {
					if(nomenclatura.getCodigoNomenclatura().isEmpty()) {
						nomenclatura.setCodigoNomenclatura(null);
					}
				}
				
				
				List<NomenclaturaExt> nome = getNomenclaturaFiltro(nomenclatura);
				if(!nome.isEmpty()) {
					acre.setCodNomenclatura(nome.get(0).getCodNomenclatura());
				}
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
	 * @param Nomenclatura
	 * @return
	 */
	public Nomenclatura updateNomenclatura(Nomenclatura nomenclatura){
		Nomenclatura acre = new Nomenclatura();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			id = mapper.updateByPrimaryKey(nomenclatura);
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
	public List<Nomenclatura> getNomenclaturaAll(){
		List<Nomenclatura> asoc = new ArrayList<>();
		NomenclaturaExample dtoObject = new NomenclaturaExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
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
	 * @param nomenclatura
	 * @return
	 */
	public List<NomenclaturaExt> getNomenclaturaFiltro(Nomenclatura nomenclatura){
		List<NomenclaturaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			asoc =  mapper.selectByfiltro(nomenclatura);
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
	
	public List<Nomenclatura> getNomenclaturaByEntidad(NomenclaturaExt nomenclatura){
		List<Nomenclatura> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			asoc =  mapper.selectByEntidad(nomenclatura);
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
	public Nomenclatura getNomenclaturaById(long id){
		Nomenclatura asoc = new Nomenclatura();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new Nomenclatura();
			}
		}catch(Exception ex){
		
			return new Nomenclatura();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param NomenclaturaExt 
	 * @return
	 */
	public NomenclaturaExt updateNomenclaturasHeredadas(NomenclaturaExt nomenclaturaExt){
		NomenclaturaExt bien = new NomenclaturaExt();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			id =  mapper.updateNomenclaturasHeredadas(nomenclaturaExt);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param NomenclaturaExt 
	 * @return
	 */
	public NomenclaturaExt updateNomenclaturaEquivalencia(NomenclaturaExt nomenclaturaExt){
		NomenclaturaExt bien = new NomenclaturaExt();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			id =  mapper.updateNomenclaturaEquivalencia(nomenclaturaExt);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param nomenclatura
	 * @return
	 */
	public List<NomenclaturaExt> getPlantasAsociadasNomenclatura(NomenclaturaExt nomenclaturaExt){
		List<NomenclaturaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			asoc =  mapper.selectPlantaAsociadaNomGeneral(nomenclaturaExt);
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
	 * @author Maria Alejandra C
	 * @param NomenclaturaExt 
	 * @return NomenclaturaExt
	 */
	public NomenclaturaExt updateNomenclaturaEquivalenciaGeneral(NomenclaturaExt nomenclaturaExt){
		NomenclaturaExt bien = new NomenclaturaExt();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			id =  mapper.updateNomenclaturaEquivalenciaGeneral(nomenclaturaExt);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	

	/**
	 * Servicio que llama procedimiento almacenado SP_ASOCIAR_ROL_USUARIO 
	 * @author Maria Alejandra C
	 * @param UsuarioRolEntidad 
	 * @return UsuarioRolEntidad
	 */
	public Nomenclatura asociarNomenclaturaGeneralAEntidad(NomenclaturaExt nomenclatura){
		Nomenclatura bien = new Nomenclatura();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NomenclaturaMapper  mapper = session.getMapper(NomenclaturaMapper.class);
			mapper.asociarNomenclaturaGeneralAEntidad(nomenclatura);
			if(nomenclatura!=null && nomenclatura.getResultProcedure()!=null && nomenclatura.getResultProcedure().intValue() >= 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
}
