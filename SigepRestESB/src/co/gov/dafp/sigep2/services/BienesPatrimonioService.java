/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.BienesPatrimonio;
import co.gov.dafp.sigep2.bean.BienesPatrimonioExample;
import co.gov.dafp.sigep2.bean.ext.BienesPatrimonioExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.BienesPatrimonioMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla BienesPatrimonio
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class BienesPatrimonioService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7228699057249810906L;
	
	/**
	 * 
	 * @param BienesPatrimonio
	 * @return
	 */
	public BienesPatrimonio insertBienesPatrimonio (BienesPatrimonio bienesPatrimonio){
		BienesPatrimonio bien = new BienesPatrimonio();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			BienesPatrimonioMapper  mapper = session.getMapper(BienesPatrimonioMapper.class);
			id =  mapper.insert(bienesPatrimonio);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param BienesPatrimonio
	 * @return
	 */
	public BienesPatrimonio updateBienesPatrimonio(BienesPatrimonio bienesPatrimonio){
		BienesPatrimonio bien = new BienesPatrimonio();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			BienesPatrimonioMapper  mapper = session.getMapper(BienesPatrimonioMapper.class);
			id = mapper.updateByPrimaryKey(bienesPatrimonio);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param BienesPatrimonio
	 * @return
	 */
	public BienesPatrimonio delBienesPatrimonio( long codBienPatrimonio){
		BienesPatrimonio bien = new BienesPatrimonio();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			BienesPatrimonioMapper  mapper = session.getMapper(BienesPatrimonioMapper.class);
			id = mapper.deleteByPrimaryKey(codBienPatrimonio);
			if(id > 0){
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_ELIMINACION_CON_EXITO);
				session.commit();
			}else{
				bien.setError(false);
				bien.setMensaje(UtilsConstantes.MSG_NO_ELIMINADO_CON_EXITO);
			}
			return bien;
		}catch (Exception ex) {
			bien.setError(true);
			bien.setMensaje(UtilsConstantes.MSG_EXEPCION);
			bien.setMensajeTecnico(ex.getMessage());
			return bien;
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
	public List<BienesPatrimonioExt> getBienesPatrimonioCodDeclaracion(BienesPatrimonio bienesPatrimonio){
		List<BienesPatrimonioExt> asoc = new ArrayList<>();
		BienesPatrimonioExample dtoObject = new BienesPatrimonioExample();
		if(bienesPatrimonio!=null && bienesPatrimonio.getCodDeclaracion()!=null) {
			dtoObject.createCriteria().andCodDeclaracionEqualTo(bienesPatrimonio.getCodDeclaracion()).
			andFlgActivoEqualTo(bienesPatrimonio.getFlgActivo());
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			BienesPatrimonioMapper  mapper = session.getMapper(BienesPatrimonioMapper.class);
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
	public BienesPatrimonioExt getBienesPatrimonioById(long id){
		BienesPatrimonioExt asoc = new BienesPatrimonioExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			BienesPatrimonioMapper  mapper = session.getMapper(BienesPatrimonioMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new BienesPatrimonioExt();
			}
		}catch(Exception ex){
		
			return new BienesPatrimonioExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	

}
