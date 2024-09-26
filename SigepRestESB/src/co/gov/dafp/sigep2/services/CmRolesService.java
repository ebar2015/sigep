/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CmRoles;
import co.gov.dafp.sigep2.bean.CmRolesExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CmRolesMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya
* @version 1.0
* @Class Que se encarga de gestionar la cimunicacion con la BD
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 12/09/2018 9:10:14 a.m.
*/
public class CmRolesService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7961207490741533887L;
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 9:24:16 a.m.
	* @param cmRoles
	* @return
	*
	 */
	public CmRoles insertCmRoles (CmRoles cmRoles){
		CmRoles cue = new CmRoles();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmRolesMapper  mapper = session.getMapper(CmRolesMapper.class);
			id =  mapper.insert(cmRoles);
			if(id > 0){
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
				return cue;
			}else{
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
				return cue;
			}
		}catch (Exception ex) {
			cue.setError(true);
			cue.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cue.setMensajeTecnico(ex.getMessage());
			return cue;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 9:24:23 a.m.
	* @param cmRoles
	* @return
	*
	 */
	public CmRoles updateCmRoles(CmRoles cmRoles){
		CmRoles cue = new CmRoles();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmRolesMapper  mapper = session.getMapper(CmRolesMapper.class);
			id = mapper.updateByPrimaryKey(cmRoles);
			if(id > 0){
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				cue.setError(false);
				cue.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return cue;
		}catch (Exception ex) {
			cue.setError(true);
			cue.setMensaje(UtilsConstantes.MSG_EXEPCION);
			cue.setMensajeTecnico(ex.getMessage());
			return cue;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 9:24:30 a.m.
	* @param cmRoles
	* @return
	*
	 */
	public List<CmRoles> getCmRolesByProceso(CmRoles cmRoles){
		List<CmRoles> asoc = new ArrayList<>();
		CmRolesExample dtoObject = new CmRolesExample();
		dtoObject.createCriteria().andCodProcesoCargaMasivaEqualTo(cmRoles.getCodProcesoCargaMasiva());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmRolesMapper  mapper = session.getMapper(CmRolesMapper.class);
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
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 9:24:36 a.m.
	* @param id
	* @return
	*
	 */
	public CmRoles getCmRolesById(long id){
		CmRoles asoc = new CmRoles();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CmRolesMapper  mapper = session.getMapper(CmRolesMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CmRoles();
			}
		}catch(Exception ex){
		
			return new CmRoles();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}


}
