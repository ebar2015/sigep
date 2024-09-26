/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmCrearGrupo;
import co.gov.dafp.sigep2.bean.CmCrearGrupoExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmCrearGrupoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya
 * 9 ene. 2019
 */
public class CmCrearGrupoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5710629094223365137L;
	
	/**
	 * 
	 * Elaborado por:
	 * Jose Viscaya 9 ene. 2019
	 * @param CmCrearGrupo
	 * @return
	 */
	public CmCrearGrupo insertCmCrearGrupo (CmCrearGrupo cmCrearGrupo){
		CmCrearGrupo acre = new CmCrearGrupo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearGrupoMapper  mapper = session.getMapper(CmCrearGrupoMapper.class);
			id =  mapper.insert(cmCrearGrupo);
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
	 * @param CmCrearGrupo
	 * @return
	 */
	public CmCrearGrupo updateCmCrearGrupo(CmCrearGrupo cmCrearGrupo){
		CmCrearGrupo acre = new CmCrearGrupo();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearGrupoMapper  mapper = session.getMapper(CmCrearGrupoMapper.class);
			id = mapper.updateByPrimaryKey(cmCrearGrupo);
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
	 * @param CmCrearGrupo
	 * @return
	 */
	public List<CmCrearGrupo> getCmCrearGrupoFiltro(CmCrearGrupo CmCrearGrupo){
		List<CmCrearGrupo> asoc = new ArrayList<>();
		SqlSession session = null;
		String msg = "CodProcesoCargaMasiva es obligatorio";
		if(CmCrearGrupo.getCodProcesoCargaMasiva() == null) {
			CmCrearGrupo err = new CmCrearGrupo();
			err.setError(true);
			err.setMensaje(msg);
			asoc.add(err);
			return asoc;
		}
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearGrupoMapper  mapper = session.getMapper(CmCrearGrupoMapper.class);
			asoc =  mapper.selectByFiltro(CmCrearGrupo);
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
	public CmCrearGrupo getCmCrearGrupoById(long id){
		CmCrearGrupo asoc = new CmCrearGrupo();
		CmCrearGrupoExample dtoObject = new CmCrearGrupoExample();
		dtoObject.createCriteria().andCodCmCrearGrupoEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmCrearGrupoMapper  mapper = session.getMapper(CmCrearGrupoMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmCrearGrupo();
			}
		}catch(Exception ex){
		
			return new CmCrearGrupo();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
