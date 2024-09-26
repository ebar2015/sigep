/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PlantaPersonaUtlUan;
import co.gov.dafp.sigep2.bean.PlantaPersonaUtlUanExample;
import co.gov.dafp.sigep2.bean.ext.PlantaPersonaUtlUanExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PlantaPersonaUtlUanMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author joseviscaya
 *
 */
public class PlantaPersonaUtlUanService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3540133838133219034L;
	
	
	/**
	 * 
	 * @param PlantaPersonaUtlUan
	 * @return
	 */
	public PlantaPersonaUtlUan insertPlantaPersonaUtlUan (PlantaPersonaUtlUan plantaPersonaUtlUan){
		PlantaPersonaUtlUan acre = new PlantaPersonaUtlUan();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PlantaPersonaUtlUanMapper  mapper = session.getMapper(PlantaPersonaUtlUanMapper.class);
			id =  mapper.insert(plantaPersonaUtlUan);
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
	 * @param PlantaPersonaUtlUan
	 * @return
	 */
	public PlantaPersonaUtlUan updatePlantaPersonaUtlUan(PlantaPersonaUtlUan plantaPersonaUtlUan){
		PlantaPersonaUtlUan acre = new PlantaPersonaUtlUan();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PlantaPersonaUtlUanMapper  mapper = session.getMapper(PlantaPersonaUtlUanMapper.class);
			id = mapper.updateByPrimaryKey(plantaPersonaUtlUan);
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
	public List<PlantaPersonaUtlUanExt> getPlantaPersonaUtlUanFiltro(PlantaPersonaUtlUanExt plantaPersonaUtlUan){
		List<PlantaPersonaUtlUanExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PlantaPersonaUtlUanMapper  mapper = session.getMapper(PlantaPersonaUtlUanMapper.class);
			asoc =  mapper.selectByFiltro(plantaPersonaUtlUan);
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
	public PlantaPersonaUtlUan getPlantaPersonaUtlUanById(long id){
		PlantaPersonaUtlUan asoc = new PlantaPersonaUtlUan();
		PlantaPersonaUtlUanExample dtoObject = new PlantaPersonaUtlUanExample();
		dtoObject.createCriteria().andCodPlantaPersonaUtlUanEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PlantaPersonaUtlUanMapper  mapper = session.getMapper(PlantaPersonaUtlUanMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new PlantaPersonaUtlUan();
			}
		}catch(Exception ex){
		
			return new PlantaPersonaUtlUan();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author Desarolladora Junior
	 * @return List<PlantaPersonaUtlUanExt>
	 */
	public List<PlantaPersonaUtlUanExt> getExcedenteBolsaUTL(PlantaPersonaUtlUanExt plantaPersonaUtlUan){
		List<PlantaPersonaUtlUanExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PlantaPersonaUtlUanMapper  mapper = session.getMapper(PlantaPersonaUtlUanMapper.class);
			asoc =  mapper.selectExcedenteBolsaUTL(plantaPersonaUtlUan);
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
	 * @author Desarolladora Junior
	 * @return List<PlantaPersonaUtlUanExt>
	 */
	public List<PlantaPersonaUtlUanExt> getPersonasVinculadasUTL(PlantaPersonaUtlUanExt plantaPersonaUtlUan){
		List<PlantaPersonaUtlUanExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PlantaPersonaUtlUanMapper  mapper = session.getMapper(PlantaPersonaUtlUanMapper.class);
			asoc =  mapper.selectPersonasVinculadasUTL(plantaPersonaUtlUan);
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
