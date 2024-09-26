/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EntidadEscision;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadEscisionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla EntidadEscision
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Martes 3 de Julio de 2018
*/
public class EntidadEscisionService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7363292456155636499L;
	
	/**
	 * 
	 * @param EntidadEscision
	 * @return
	 */
	public EntidadEscision insertEntidadEscision (EntidadEscision entidadEscision){
		EntidadEscision actp = new EntidadEscision();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadEscisionMapper  mapper = session.getMapper(EntidadEscisionMapper.class);
			id =  mapper.insert(entidadEscision);
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
	 * @param EntidadEscision
	 * @return
	 */
	public EntidadEscision updateEntidadEscision(EntidadEscision entidadEscision){
		EntidadEscision actp = new EntidadEscision();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadEscisionMapper  mapper = session.getMapper(EntidadEscisionMapper.class);
			id = mapper.updateByPrimaryKey(entidadEscision);
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
	public EntidadEscision getEntidadEscisionById(BigDecimal id){
		EntidadEscision asoc = new EntidadEscision();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadEscisionMapper  mapper = session.getMapper(EntidadEscisionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new EntidadEscision();
			}
		}catch(Exception ex){
			asoc = new EntidadEscision();
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
	public List<EntidadEscision> getEntidadEscisionFiltro(EntidadEscision entidadEscision){
		List<EntidadEscision>  asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadEscisionMapper  mapper = session.getMapper(EntidadEscisionMapper.class);
			asoc =  mapper.selectFiltro(entidadEscision);
			if(asoc != null){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			EntidadEscision en = new EntidadEscision();
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
