package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.SituacionAdministrativa;
import co.gov.dafp.sigep2.bean.ext.SituacionAdministrativaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.SituacionAdministrativaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla SituacionAdministrativaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class SituacionAdministrativaService implements Serializable {

	private static final long serialVersionUID = -5391310248835664659L;
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:55:48 p. m.
	 * @param situacionAdministrativa
	 * @return
	 */
	public SituacionAdministrativa insertSituacionAdministrativa (SituacionAdministrativa situacionAdministrativa){
		SituacionAdministrativa bien = new SituacionAdministrativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			id =  mapper.insert(situacionAdministrativa);
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
	 * @param SituacionAdministrativa
	 * @return
	 */
	public SituacionAdministrativa insertSituacionAdministrativaParticular(SituacionAdministrativaExt situacionAdministrativa){
		SituacionAdministrativa bien = new SituacionAdministrativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			id =  mapper.nuevasituacionParticular(situacionAdministrativa);
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
	 * @param SituacionAdministrativa
	 * @return
	 */
	public SituacionAdministrativa updateSituacionAdministrativa(SituacionAdministrativa situacionAdministrativa){
		SituacionAdministrativa bien = new SituacionAdministrativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			id = mapper.updateByPrimaryKey(situacionAdministrativa);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
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
	 * @param SituacionAdministrativa
	 * @return
	 */
	public SituacionAdministrativa delSituacionAdministrativa( long codBienPatrimonio){
		SituacionAdministrativa bien = new SituacionAdministrativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			id = mapper.deleteByPrimaryKey(codBienPatrimonio);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_ELIMINACION_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_ELIMINADO_CON_EXITO);
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
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:56:02 p. m.
	 * @param situacionAdministrativa
	 * @return
	 */
	public List<SituacionAdministrativa> getSituacionAdministrativaFiltro(SituacionAdministrativa situacionAdministrativa){
		List<SituacionAdministrativa> asoc = new ArrayList<>();
		SqlSession session = null;
		SituacionAdministrativa st = new SituacionAdministrativa();
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			asoc =  mapper.selectByfilter(situacionAdministrativa);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			st.setError(true);
			st.setMensaje(UtilsConstantes.MSG_EXEPCION);
			st.setMensajeTecnico(ex.getMessage());
			asoc.add(st);
			return asoc;
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
	public SituacionAdministrativa getSituacionAdministrativaById(long id){
		SituacionAdministrativa asoc = new SituacionAdministrativa();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new SituacionAdministrativa();
			}
		}catch(Exception ex){
		
			return new SituacionAdministrativa();
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
	public SituacionAdministrativa getSituacionAdministrativaPadre(long id){
		SituacionAdministrativa asoc = new SituacionAdministrativa();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			asoc =  mapper.selectSituacionPadre(id);
			if(asoc != null){
				return asoc;
			}else{
				return new SituacionAdministrativa();
			}
		}catch(Exception ex){
			
			return new SituacionAdministrativa();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param situacionAdministrativa
	 * @return List<SituacionAdministrativaExt>
	 */
	public List<SituacionAdministrativaExt> getSituacionParticularesPorEntidad(SituacionAdministrativaExt situacionAdministrativa){
		List<SituacionAdministrativaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		SituacionAdministrativaExt st = new SituacionAdministrativaExt();
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			asoc =  mapper.selectsituacionparticularesporentidad(situacionAdministrativa);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			st.setError(true);
			st.setMensaje(UtilsConstantes.MSG_EXEPCION);
			st.setMensajeTecnico(ex.getMessage());
			asoc.add(st);
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	public List<SituacionAdministrativaExt> getSituacionParticularAsignada(SituacionAdministrativaExt situacionAdministrativa){
		List<SituacionAdministrativaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		SituacionAdministrativaExt st = new SituacionAdministrativaExt();
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			asoc =  mapper.selectsituacionasignada(situacionAdministrativa);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			st.setError(true);
			st.setMensaje(UtilsConstantes.MSG_EXEPCION);
			st.setMensajeTecnico(ex.getMessage());
			asoc.add(st);
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	public List<SituacionAdministrativa> getNombreSituacion(SituacionAdministrativaExt situacionAdministrativa){
		List<SituacionAdministrativa> asoc = new ArrayList<>();
		SqlSession session = null;
		SituacionAdministrativa st = new SituacionAdministrativa();
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			asoc =  mapper.selectNombreSituacion(situacionAdministrativa);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			st.setError(true);
			st.setMensaje(UtilsConstantes.MSG_EXEPCION);
			st.setMensajeTecnico(ex.getMessage());
			asoc.add(st);
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   9 may. 2019, 9:07:26
	 * @File:   SituacionAdministrativaService.java
	 * @param situacionAdministrativa
	 * @return
	 */
	public List<SituacionAdministrativa> getNombreSituacionUnq(SituacionAdministrativa situacionAdministrativa){
		List<SituacionAdministrativa> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SituacionAdministrativaMapper  mapper = session.getMapper(SituacionAdministrativaMapper.class);
			asoc =  mapper.selectNombreSituacionUniq(situacionAdministrativa);
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
	
}