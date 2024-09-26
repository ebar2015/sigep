/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CuentasDeclaracion;
import co.gov.dafp.sigep2.bean.CuentasDeclaracionExample;
import co.gov.dafp.sigep2.bean.ext.CuentasDeclaracionExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CuentasDeclaracionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CuentasDeclaracion
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class CuentasDeclaracionService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2774634060047315867L;
	
	/**
	 * 
	 * @param CuentasDeclaracion
	 * @return
	 */
	public CuentasDeclaracion insertCuentasDeclaracion (CuentasDeclaracion cuentasDeclaracion){
		CuentasDeclaracion cue = new CuentasDeclaracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CuentasDeclaracionMapper  mapper = session.getMapper(CuentasDeclaracionMapper.class);
			id =  mapper.insert(cuentasDeclaracion);
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
	 * @param CuentasDeclaracion
	 * @return
	 */
	public CuentasDeclaracion updateCuentasDeclaracion(CuentasDeclaracion cuentasDeclaracion){
		CuentasDeclaracion cue = new CuentasDeclaracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CuentasDeclaracionMapper  mapper = session.getMapper(CuentasDeclaracionMapper.class);
			id = mapper.updateByPrimaryKey(cuentasDeclaracion);
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
	 * @return
	 */
	public List<CuentasDeclaracionExt> getCuentasDeclaracionCodDeclaracion(CuentasDeclaracion cuentasDeclaracion){
		List<CuentasDeclaracionExt> asoc = new ArrayList<>();
		CuentasDeclaracionExample dtoObject = new CuentasDeclaracionExample();
		if(cuentasDeclaracion!=null && cuentasDeclaracion.getCodDeclaracion()!=null) {
			dtoObject.createCriteria().andCodDeclaracionEqualTo(cuentasDeclaracion.getCodDeclaracion()).
			andFlgActivoEqualTo(cuentasDeclaracion.getFlgActivo());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CuentasDeclaracionMapper  mapper = session.getMapper(CuentasDeclaracionMapper.class);
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
	public CuentasDeclaracion getCuentasDeclaracionById(long id){
		CuentasDeclaracion asoc = new CuentasDeclaracion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CuentasDeclaracionMapper  mapper = session.getMapper(CuentasDeclaracionMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new CuentasDeclaracion();
			}
		}catch(Exception ex){
		
			return new CuentasDeclaracion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
