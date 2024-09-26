/**
 * @ Author Jose Viscaya
 * 13 nov. 2021
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CifrasConciliacion;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CifrasConciliacionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya
 *
 */
public class CifrasConciliacionService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1322163357950498509L;
	
	
	public CifrasConciliacion insertCifrasConciliacion (CifrasConciliacion cifrasConciliacion){
		CifrasConciliacion acre = new CifrasConciliacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CifrasConciliacionMapper  mapper = session.getMapper(CifrasConciliacionMapper.class);
			id =  mapper.insert(cifrasConciliacion);
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
	 * @param CifrasConciliacion
	 * @return
	 */
	public CifrasConciliacion updateCifrasConciliacion(CifrasConciliacion cifrasConciliacion){
		CifrasConciliacion acre = new CifrasConciliacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CifrasConciliacionMapper  mapper = session.getMapper(CifrasConciliacionMapper.class);
			id = mapper.updateByPrimaryKey(cifrasConciliacion);
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
	public List<CifrasConciliacion> getCifrasConciliacionFiltro(CifrasConciliacion cifrasConciliacion){
		List<CifrasConciliacion> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CifrasConciliacionMapper  mapper = session.getMapper(CifrasConciliacionMapper.class);
			asoc =  mapper.selectByFiltro(cifrasConciliacion);
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
