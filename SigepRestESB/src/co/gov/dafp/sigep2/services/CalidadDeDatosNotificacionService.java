/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CalidadDeDatosNotificacion;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CalidadDeDatosNotificacionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Maria Alejandra C.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla CALIDAD_DE_DATOS_NOTIFICACION.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class CalidadDeDatosNotificacionService implements Serializable {


	private static final long serialVersionUID = -3547242551384073684L;

	/**
	 * 
	 * @Author: Maria Alejandra C
	 * @Date:   07/12/2021
	 * @File:   CalidadDeDatosNotificacionService.java
	 * @param CalidadDeDatosNotificacion
	 * @return
	 */
	public CalidadDeDatosNotificacion insert (CalidadDeDatosNotificacion calidadDeDatosNotificacion){
		CalidadDeDatosNotificacion calidad = new CalidadDeDatosNotificacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CalidadDeDatosNotificacionMapper  mapper = session.getMapper(CalidadDeDatosNotificacionMapper.class);
			id =  mapper.insert(calidadDeDatosNotificacion);
			if(id > 0){
				calidad.setError(false);
				calidad.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				calidad.setError(false);
				calidad.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return calidad;
		}catch (Exception ex) {
			calidad.setError(true);
			calidad.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			calidad.setMensajeTecnico(ex.getMessage());
			return calidad;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Author: Maria Alejandra C
	 * @Date:   07/12/2021
	 * @File:   CalidadDeDatosNotificacionService.java
	 * @param   CalidadDeDatosNotificacion
	 * @return
	 */
	public CalidadDeDatosNotificacion update(CalidadDeDatosNotificacion calidadDatosNotificacion){
		CalidadDeDatosNotificacion calidad = new CalidadDeDatosNotificacion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CalidadDeDatosNotificacionMapper  mapper = session.getMapper(CalidadDeDatosNotificacionMapper.class);
			id = mapper.updateByPrimaryKey(calidadDatosNotificacion);
			if(id > 0){
				calidad.setError(false);
				calidad.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				calidad.setError(false);
				calidad.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return calidad;
		}catch (Exception ex) {
			calidad.setError(true);
			calidad.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			calidad.setMensajeTecnico(ex.getMessage());
			return calidad;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}


	/**
	 * @return
	 */
	public CalidadDeDatosNotificacion getCalidadDatosNotificacionId(BigDecimal codNotificacion){
		CalidadDeDatosNotificacion asoc = new CalidadDeDatosNotificacion();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CalidadDeDatosNotificacionMapper  mapper = session.getMapper(CalidadDeDatosNotificacionMapper.class);
			asoc =  mapper.selectByPrimaryKey(codNotificacion);
			if(asoc!=null){
				return asoc;
			}else{
				return new CalidadDeDatosNotificacion();
			}
		}catch(Exception ex){
			return new CalidadDeDatosNotificacion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @return
	 */
	public List<CalidadDeDatosNotificacion> getProcesosPorNotificar(){
		List<CalidadDeDatosNotificacion> asoc = new ArrayList<CalidadDeDatosNotificacion>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CalidadDeDatosNotificacionMapper  mapper = session.getMapper(CalidadDeDatosNotificacionMapper.class);
			asoc =  mapper.getProcesosPorNotificar();
			if(asoc!=null){
				return asoc;
			}else{
				return new ArrayList<CalidadDeDatosNotificacion>();
			}
		}catch(Exception ex){
			return new ArrayList<CalidadDeDatosNotificacion>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}	
	
}
