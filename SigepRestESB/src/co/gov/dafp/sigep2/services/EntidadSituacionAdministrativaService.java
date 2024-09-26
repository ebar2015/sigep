/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EntidadSituacionAdministrativa;
import co.gov.dafp.sigep2.bean.ext.EntidadSituacionAdministrativaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadSituacionAdministrativaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author joseviscaya
 *
 */
public class EntidadSituacionAdministrativaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6001976743425099151L;
	
	
	
	/**
	 * 
	 * @param EntidadSituacionAdministrativa
	 * @return
	 */
	public EntidadSituacionAdministrativa insertEntidadSituacionAdministrativa (EntidadSituacionAdministrativa EntidadSituacionAdministrativa){
		EntidadSituacionAdministrativa depto = new EntidadSituacionAdministrativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadSituacionAdministrativaMapper  mapper = session.getMapper(EntidadSituacionAdministrativaMapper.class);
			id =  mapper.insert(EntidadSituacionAdministrativa);
			if(id > 0){
				depto.setError(false);
				depto.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				depto.setError(false);
				depto.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return depto;
		}catch (Exception ex) {
			depto.setError(true);
			depto.setMensaje(UtilsConstantes.MSG_EXEPCION);
			depto.setMensajeTecnico(ex.getMessage());
			return depto;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param EntidadSituacionAdministrativa
	 * @return
	 */
	public EntidadSituacionAdministrativa updateEntidadSituacionAdministrativa(EntidadSituacionAdministrativa EntidadSituacionAdministrativa){
		EntidadSituacionAdministrativa depto = new EntidadSituacionAdministrativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadSituacionAdministrativaMapper  mapper = session.getMapper(EntidadSituacionAdministrativaMapper.class);
			id = mapper.updateByPrimaryKey(EntidadSituacionAdministrativa);
			session.commit();
			if(id > 0){
				depto.setError(false);
				depto.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				depto.setError(false);
				depto.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return depto;
		}catch (Exception ex) {
			depto.setError(true);
			depto.setMensaje(UtilsConstantes.MSG_EXEPCION);
			depto.setMensajeTecnico(ex.getMessage());
			return depto;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public EntidadSituacionAdministrativa getEntidadSituacionAdministrativaById(Long id){
	    EntidadSituacionAdministrativa asoc ;
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadSituacionAdministrativaMapper  mapper = session.getMapper(EntidadSituacionAdministrativaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new EntidadSituacionAdministrativa();
			}
		}catch(Exception ex){
			
			return new EntidadSituacionAdministrativa();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param situacion
	 * @return
	 */
	public EntidadSituacionAdministrativa nombreExiste(EntidadSituacionAdministrativaExt situacion){
	    EntidadSituacionAdministrativa asoc ;
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadSituacionAdministrativaMapper  mapper = session.getMapper(EntidadSituacionAdministrativaMapper.class);
			asoc =  mapper.selectNombreSituacion(situacion);
			if(asoc != null){
				return asoc;
			}else{
				return new EntidadSituacionAdministrativa();
			}
		}catch(Exception ex){
			return new EntidadSituacionAdministrativa();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param situacion
	 * @return
	 */
	public List<EntidadSituacionAdministrativaExt> getSituacionesAplicadas(EntidadSituacionAdministrativa situacion){
		List<EntidadSituacionAdministrativaExt> asoc = new ArrayList<>() ;
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadSituacionAdministrativaMapper  mapper = session.getMapper(EntidadSituacionAdministrativaMapper.class);
			asoc =  mapper.selectSituacionesAplicadas(situacion);
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
