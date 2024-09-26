/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.AcreenciaObligacion;
import co.gov.dafp.sigep2.bean.AcreenciaObligacionExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.AcreenciaObligacionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;
/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla AcreenciaObligacion
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class AcreenciaObligacionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6287746579705415153L;
	/**
	 * 
	 * @param AcreenciaObligacion
	 * @return
	 */
	public AcreenciaObligacion insertAcreenciaObligacion (AcreenciaObligacion acreenciaObligacion){
		AcreenciaObligacion acre = new AcreenciaObligacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AcreenciaObligacionMapper  mapper = session.getMapper(AcreenciaObligacionMapper.class);
			id =  mapper.insert(acreenciaObligacion);
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
	 * @param AcreenciaObligacion
	 * @return
	 */
	public AcreenciaObligacion updateAcreenciaObligacion(AcreenciaObligacion acreenciaObligacion){
		AcreenciaObligacion acre = new AcreenciaObligacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AcreenciaObligacionMapper  mapper = session.getMapper(AcreenciaObligacionMapper.class);
			id = mapper.updateByPrimaryKey(acreenciaObligacion);
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
	public List<AcreenciaObligacion> getAcreenciaObligacionCodDeclaracion(AcreenciaObligacion acreenciaObligacion){
		List<AcreenciaObligacion> asoc = new ArrayList<>();
		AcreenciaObligacionExample dtoObject = new AcreenciaObligacionExample();
		if(acreenciaObligacion!=null && acreenciaObligacion.getCodDeclaracion()!=null) {
			dtoObject.createCriteria().andCodDeclaracionEqualTo(acreenciaObligacion.getCodDeclaracion()).
			andFlgActivoEqualTo(acreenciaObligacion.getFlgActivo());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AcreenciaObligacionMapper  mapper = session.getMapper(AcreenciaObligacionMapper.class);
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
	 * @param Id Search
	 * @return
	 */
	public AcreenciaObligacion getAcreenciaObligacionById(long id){
		AcreenciaObligacion asoc = new AcreenciaObligacion();
		AcreenciaObligacionExample dtoObject = new AcreenciaObligacionExample();
		dtoObject.createCriteria().andCodAcreenciaObligacionEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			AcreenciaObligacionMapper  mapper = session.getMapper(AcreenciaObligacionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new AcreenciaObligacion();
			}
		}catch(Exception ex){
		
			return new AcreenciaObligacion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
