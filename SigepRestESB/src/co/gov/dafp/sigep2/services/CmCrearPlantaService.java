/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmCrearPlanta;
import co.gov.dafp.sigep2.bean.CmCrearPlantaExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmCrearPlantaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya
 * 9 ene. 2019
 */
public class CmCrearPlantaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5760874774951691063L;
	
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param CmCrearPlanta
	 * @return
	 */
	public CmCrearPlanta insertCmCrearPlanta (CmCrearPlanta CmCrearPlanta){
		CmCrearPlanta acre = new CmCrearPlanta();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearPlantaMapper  mapper = session.getMapper(CmCrearPlantaMapper.class);
			id =  mapper.insert(CmCrearPlanta);
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
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param CmCrearPlanta
	 * @return
	 */
	public CmCrearPlanta updateCmCrearPlanta(CmCrearPlanta CmCrearPlanta){
		CmCrearPlanta acre = new CmCrearPlanta();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearPlantaMapper  mapper = session.getMapper(CmCrearPlantaMapper.class);
			id = mapper.updateByPrimaryKey(CmCrearPlanta);
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
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param CmCrearPlanta
	 * @return
	 */
	public List<CmCrearPlanta> getCmCrearPlantaFiltro(CmCrearPlanta CmCrearPlanta){
		List<CmCrearPlanta> asoc = new ArrayList<>();
		SqlSession session = null;
		String msg = "CodProcesoCargaMasiva es obligatorio";
		if(CmCrearPlanta.getCodProcesoCargaMasiva() == null) {
			CmCrearPlanta err = new CmCrearPlanta();
			err.setError(true);
			err.setMensaje(msg);
			asoc.add(err);
			return asoc;
		}
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearPlantaMapper  mapper = session.getMapper(CmCrearPlantaMapper.class);
			asoc =  mapper.selectByFiltro(CmCrearPlanta);
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
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param id
	 * @return
	 */
	public CmCrearPlanta getCmCrearPlantaById(long id){
		CmCrearPlanta asoc = new CmCrearPlanta();
		CmCrearPlantaExample dtoObject = new CmCrearPlantaExample();
		dtoObject.createCriteria().andCodCmCrearPlantaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearPlantaMapper  mapper = session.getMapper(CmCrearPlantaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmCrearPlanta();
			}
		}catch(Exception ex){
		
			return new CmCrearPlanta();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
