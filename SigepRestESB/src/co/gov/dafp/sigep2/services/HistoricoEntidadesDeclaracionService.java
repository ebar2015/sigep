/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.HistoricoEntidadesDeclaracion;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.HistoricoEntidadesDeclaracionMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Maria Alejandra
* @version 1.0
* @Class Clase que se encarga de gestionar el almacenamiento y recuperacion de datos de la tabla HISTORICO_ENTIDADES_DECLARACION
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class HistoricoEntidadesDeclaracionService implements Serializable {

	private static final long serialVersionUID = 2026144320537518768L;

	/**
	 * Inserta nuevo registro en maestro HISTORICO_ENTIDADES_DECLARACION
	 * @param historicoEntidadesDeclaracion
	 * @return HistoricoEntidadesDeclaracion
	 */
	public HistoricoEntidadesDeclaracion insertHistoricoEntidadesDeclaracion (HistoricoEntidadesDeclaracion historicoEntidadesDeclaracion){
		HistoricoEntidadesDeclaracion historicoResult = new HistoricoEntidadesDeclaracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoEntidadesDeclaracionMapper  mapper = session.getMapper(HistoricoEntidadesDeclaracionMapper.class);
			id =  mapper.insert(historicoEntidadesDeclaracion);
			if(id > 0){
				historicoResult.setError(false);
				historicoResult.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				historicoResult.setError(false);
				historicoResult.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return historicoResult;
		}catch (Exception ex) {
			historicoResult.setError(true);
			historicoResult.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			historicoResult.setMensajeTecnico(ex.getMessage());
			return historicoResult;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Modifica un historicoEntidadesDeclaracion en maestro HISTORICO_ENTIDADES_DECLARACION por llave primaria
	 * @param historicoEntidadesDeclaracion
	 * @return HistoricoEntidadesDeclaracion
	 */
	public HistoricoEntidadesDeclaracion updateHistoricoEntidadesDeclaracion(HistoricoEntidadesDeclaracion historicoEntidadesDeclaracion){
		HistoricoEntidadesDeclaracion resultHistoricoEntidadesDec = new HistoricoEntidadesDeclaracion();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoEntidadesDeclaracionMapper  mapper = session.getMapper(HistoricoEntidadesDeclaracionMapper.class);
			id = mapper.updateByPrimaryKey(historicoEntidadesDeclaracion);
			if(id > 0){
				resultHistoricoEntidadesDec.setError(false);
				resultHistoricoEntidadesDec.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				resultHistoricoEntidadesDec.setError(false);
				resultHistoricoEntidadesDec.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return resultHistoricoEntidadesDec;
		}catch (Exception ex) {
			resultHistoricoEntidadesDec.setError(true);
			resultHistoricoEntidadesDec.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			resultHistoricoEntidadesDec.setMensajeTecnico(ex.getMessage());
			return resultHistoricoEntidadesDec;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * Servicio que retorna lista del maestro HISTORICO_ENTIDADES_DECLARACION dependiendo del filtro enviado
	 * @param historicoEntidadesDeclaracion
	 * @return List<HistoricoEntidadesDeclaracion>
	 */ 
	public List<HistoricoEntidadesDeclaracion> geHistoricoEntidadesDeclaracionFiltro(HistoricoEntidadesDeclaracion historicoEntidadesDeclaracion){
		List<HistoricoEntidadesDeclaracion> result = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoEntidadesDeclaracionMapper  mapper = session.getMapper(HistoricoEntidadesDeclaracionMapper.class);
			result =  mapper.selectByfiltro(historicoEntidadesDeclaracion);
			if(!result.isEmpty()){
				return result;
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
	 * Consulta un cargo del maestro HISTORICO_ENTIDADES_DECLARACION por su llame primaria
	 * @param historicoEntidadesDeclaracion
	 * @return HistoricoEntidadesDeclaracion
	 */
	public HistoricoEntidadesDeclaracion HistoricoEntidadesDeclaracionById(HistoricoEntidadesDeclaracion historicoEntidadesDeclaracion){
		HistoricoEntidadesDeclaracion asoc = new HistoricoEntidadesDeclaracion();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HistoricoEntidadesDeclaracionMapper  mapper = session.getMapper(HistoricoEntidadesDeclaracionMapper.class);
			asoc =  mapper.selectByPrimaryKey(historicoEntidadesDeclaracion.getCodDeclaracionEntidad());
			if(asoc !=null){
				return asoc;
			}else{
				return new HistoricoEntidadesDeclaracion();
			}
		}catch(Exception ex){
			return new HistoricoEntidadesDeclaracion();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}	
}
