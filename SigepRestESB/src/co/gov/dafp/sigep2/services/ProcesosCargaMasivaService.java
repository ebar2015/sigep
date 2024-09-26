package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ProcesosCargaMasiva;
import co.gov.dafp.sigep2.bean.ProcesosCargaMasivaExample;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.ProcesosCargaMasivaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ProcesosCargaMasivaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ProcesosCargaMasivaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ProcesosCargaMasivaService implements Serializable {
	private static final long serialVersionUID = -6592895087714270205L;
	
	/**
	 * 
	 * @param ProcesosCargaMasiva
	 * @return
	 */
	public ProcesosCargaMasiva insertProcesosCargaMasiva (ProcesosCargaMasiva procesosCargaMasiva){
		ProcesosCargaMasiva arch =  new ProcesosCargaMasiva();
		SqlSession session = null;
		int id = 0;
		
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProcesosCargaMasivaMapper  mapper = session.getMapper(ProcesosCargaMasivaMapper.class);
			id =  mapper.insert(procesosCargaMasiva);
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
	 * 
	 * @param ProcesosCargaMasiva
	 * @return
	 */
	public ProcesosCargaMasiva updateProcesosCargaMasiva(ProcesosCargaMasiva procesosCargaMasiva){
		ProcesosCargaMasiva arch =  new ProcesosCargaMasiva();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProcesosCargaMasivaMapper  mapper = session.getMapper(ProcesosCargaMasivaMapper.class);
			id = mapper.updateByPrimaryKey(procesosCargaMasiva);
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
	 * @return
	 */
	public List<ProcesosCargaMasiva> getProcesosCargaMasivaByAll(){
		List<ProcesosCargaMasiva> asoc = new ArrayList<>();
		ProcesosCargaMasivaExample dtoObject = new ProcesosCargaMasivaExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProcesosCargaMasivaMapper  mapper = session.getMapper(ProcesosCargaMasivaMapper.class);
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
	public List<ProcesosCargaMasivaExt> cargaMasivafiltro(PersonaExt persona){
		List<ProcesosCargaMasivaExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProcesosCargaMasivaMapper  mapper = session.getMapper(ProcesosCargaMasivaMapper.class);
			asoc =  mapper.cargaMasivafiltro(persona);
			
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
	public ProcesosCargaMasivaExt getProcesosCargaMasivaById(long codProcesoCargaMasiva){
		ProcesosCargaMasivaExt asoc = new ProcesosCargaMasivaExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ProcesosCargaMasivaMapper  mapper = session.getMapper(ProcesosCargaMasivaMapper.class);
			asoc =  mapper.selectByPrimaryKey(codProcesoCargaMasiva);
			if(asoc != null){
				return asoc;
			}else{
				return new ProcesosCargaMasivaExt();
			}
		}catch(Exception ex){
		
			return new ProcesosCargaMasivaExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
