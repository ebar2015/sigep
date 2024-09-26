/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CalidadDeDatos;
import co.gov.dafp.sigep2.bean.CalidadDeDatosNotificacion;
import co.gov.dafp.sigep2.bean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CalidadDeDatosMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author joseviscaya
 *
 */
public class CalidadDeDatosService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1824754519007661568L;
	
	public CalidadDeDatos insertCalidadDeDatos (CalidadDeDatos calidadDeDatos){
		CalidadDeDatos acre = new CalidadDeDatos();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CalidadDeDatosMapper  mapper = session.getMapper(CalidadDeDatosMapper.class);
			id =  mapper.insert(calidadDeDatos);
			if(id > 0){
				acre.setError(false);
				session.commit();
			}else{
				acre.setError(false);
			}
			return acre;
		}catch (Exception ex) {
			acre.setError(true);
			acre.setMensajeTecnico(ex.getMessage());
			return acre;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	public List<CalidadDeDatos> getCalidadDeDatosFiltro(CalidadDeDatos calidadDeDatos){
		List<CalidadDeDatos> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CalidadDeDatosMapper  mapper = session.getMapper(CalidadDeDatosMapper.class);
			asoc =  mapper.selectFiltro(calidadDeDatos);
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
	
	
	public void aplicarCalidadDatos(CalidadDeDatosNotificacion calidadNotificacion){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CalidadDeDatosMapper  mapper = session.getMapper(CalidadDeDatosMapper.class);
			id =  mapper.aplicarCalidadDatos(calidadNotificacion);
			if(id > 0){
				session.commit();
			}else{
				session.commit();
			}
		}catch (Exception ex) {
			session.commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}	
	

}
