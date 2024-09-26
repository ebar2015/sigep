/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EscalaSalarial;
import co.gov.dafp.sigep2.bean.EscalaSalarialExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EscalaSalarialMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Que se encarga de realizar la comunicacion con la base de datos
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios fuse 
* @Fecha: 29/08/2018 7:39:34 a.m.
*/
public class EscalaSalarialService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5004251808239320763L;
	
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 29/08/2018 7:43:02 a.m.
	* @param escalaSalarial
	* @return
	*
	 */
	public EscalaSalarial insertEscalaSalarial (EscalaSalarial escalaSalarial){
		EscalaSalarial actp = new EscalaSalarial();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EscalaSalarialMapper  mapper = session.getMapper(EscalaSalarialMapper.class);
			id =  mapper.insert(escalaSalarial);
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
	* @author: Jose Viscaya 
	* @fecha 29/08/2018 7:42:58 a.m.
	* @param escalaSalarial
	* @return
	*
	 */
	public EscalaSalarial updateEscalaSalarial(EscalaSalarial escalaSalarial){
		EscalaSalarial actp = new EscalaSalarial();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EscalaSalarialMapper  mapper = session.getMapper(EscalaSalarialMapper.class);
			id = mapper.updateByPrimaryKey(escalaSalarial);
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
	* @author: Jose Viscaya 
	* @fecha 29/08/2018 7:42:51 a.m.
	* @return
	*
	 */
	public List<EscalaSalarial> getEscalaSalarialAll(){
		List<EscalaSalarial> asoc = new ArrayList<>();
		EscalaSalarialExample dtoObject = new EscalaSalarialExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EscalaSalarialMapper  mapper = session.getMapper(EscalaSalarialMapper.class);
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
	* @fecha 5/09/2018 11:33:36 a.m.
	* @return
	*
	 */
	public List<EscalaSalarial> getEscalaSalarialFiltro(EscalaSalarial escalaSalarial){
		List<EscalaSalarial> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EscalaSalarialMapper  mapper = session.getMapper(EscalaSalarialMapper.class);
			asoc =  mapper.selectByFiltro(escalaSalarial);
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
	* @fecha 29/08/2018 7:43:12 a.m.
	* @param id
	* @return
	*
	 */
	public EscalaSalarial getEscalaSalarialById(long id){
		EscalaSalarial asoc = new EscalaSalarial();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EscalaSalarialMapper  mapper = session.getMapper(EscalaSalarialMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new EscalaSalarial();
			}
		}catch(Exception ex){
			asoc = new EscalaSalarial();
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


}
