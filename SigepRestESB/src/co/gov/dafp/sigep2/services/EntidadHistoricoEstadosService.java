/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EntidadHistoricoEstados;
import co.gov.dafp.sigep2.bean.ext.EntidadHistoricoEstadosExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadHistoricoEstadosMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla EntidadHistoricoEstados
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class EntidadHistoricoEstadosService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7622618066053387113L;
	
	/**
	 * 
	 * @param EntidadHistoricoEstados
	 * @return
	 */
	public EntidadHistoricoEstados insertEntidadHistoricoEstados (EntidadHistoricoEstados EntidadHistoricoEstados){
		EntidadHistoricoEstados actp = new EntidadHistoricoEstados();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadHistoricoEstadosMapper  mapper = session.getMapper(EntidadHistoricoEstadosMapper.class);
			id =  mapper.insert(EntidadHistoricoEstados);
			if(id > 0){
				actp.setError(false);
				actp.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				actp.setError(false);
				actp.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return actp;
		}catch (Exception ex) {
			actp.setError(true);
			actp.setMensaje(UtilsConstantes.MSG_EXEPCION);
			actp.setMensajeTecnico(ex.getMessage());
			return actp;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param EntidadHistoricoEstados
	 * @return
	 */
	public EntidadHistoricoEstados updateEntidadHistoricoEstados(EntidadHistoricoEstados EntidadHistoricoEstados){
		EntidadHistoricoEstados actp = new EntidadHistoricoEstados();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadHistoricoEstadosMapper  mapper = session.getMapper(EntidadHistoricoEstadosMapper.class);
			id = mapper.updateByPrimaryKey(EntidadHistoricoEstados);
			session.commit();
			if(id > 0){
				actp.setError(false);
				actp.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				actp.setError(false);
				actp.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return actp;
		}catch (Exception ex) {
			actp.setError(true);
			actp.setMensaje(UtilsConstantes.MSG_EXEPCION);
			actp.setMensajeTecnico(ex.getMessage());
			return actp;		
		} finally {
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
	public EntidadHistoricoEstados getEntidadHistoricoEstadosById(long id){
		EntidadHistoricoEstados asoc = new EntidadHistoricoEstados();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadHistoricoEstadosMapper  mapper = session.getMapper(EntidadHistoricoEstadosMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new EntidadHistoricoEstados();
			}
		}catch(Exception ex){
			asoc = new EntidadHistoricoEstados();
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
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
	public List<EntidadHistoricoEstadosExt> getEntidadHistoricoEstadosFiltro(EntidadHistoricoEstados entidadHistoricoEstados){
		List<EntidadHistoricoEstadosExt>  asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadHistoricoEstadosMapper  mapper = session.getMapper(EntidadHistoricoEstadosMapper.class);
			asoc =  mapper.selectByFiltro(entidadHistoricoEstados);
			if(asoc != null){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			EntidadHistoricoEstadosExt en = new EntidadHistoricoEstadosExt();
			en.setError(true);
			en.setMensaje(UtilsConstantes.MSG_EXEPCION);
			en.setMensajeTecnico(ex.getMessage());
			asoc .add(en);
			return asoc;		
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
