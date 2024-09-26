package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.ModificacionSituacionAdministrativa;
import co.gov.dafp.sigep2.bean.ModificacionSituacionAdministrativaExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ModificacionSituacionAdministrativaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;
/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ModificacionSituacionAdministrativaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ModificacionSituacionAdministrativaService implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:31:08 p. m.
	 * @param modificacionSituacionAdministrativa
	 * @return
	 */
	public ModificacionSituacionAdministrativa insertModificacionSituacionAdministrativa (ModificacionSituacionAdministrativa modificacionSituacionAdministrativa){
		ModificacionSituacionAdministrativa idio = new ModificacionSituacionAdministrativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionSituacionAdministrativaMapper  mapper = session.getMapper(ModificacionSituacionAdministrativaMapper.class);
			id =  mapper.insert(modificacionSituacionAdministrativa);
			if(id > 0){
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return idio;
		}catch (Exception ex) {
			idio.setError(true);
			idio.setMensaje(UtilsConstantes.MSG_EXEPCION);
			idio.setMensajeTecnico(ex.getMessage());
			return idio;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:31:16 p. m.
	 * @param modificacionSituacionAdministrativa
	 * @return
	 */
	public ModificacionSituacionAdministrativa updateModificacionSituacionAdministrativa(ModificacionSituacionAdministrativa modificacionSituacionAdministrativa){
		ModificacionSituacionAdministrativa idio = new ModificacionSituacionAdministrativa();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionSituacionAdministrativaMapper  mapper = session.getMapper(ModificacionSituacionAdministrativaMapper.class);
			id = mapper.updateByPrimaryKey(modificacionSituacionAdministrativa);
			session.commit();
			if(id > 0){
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				idio.setError(false);
				idio.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return idio;
		}catch (Exception ex) {
			idio.setError(true);
			idio.setMensaje(UtilsConstantes.MSG_EXEPCION);
			idio.setMensajeTecnico(ex.getMessage());
			return idio;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:31:24 p. m.
	 * @param codSituacion
	 * @return
	 */
	public List<ModificacionSituacionAdministrativa> getModificacionesPorSA(long codSituacion){
		List<ModificacionSituacionAdministrativa> asoc = new ArrayList<>();
		ModificacionSituacionAdministrativa idio = new ModificacionSituacionAdministrativa();
		ModificacionSituacionAdministrativaExample dtoObject = new ModificacionSituacionAdministrativaExample();
		dtoObject.createCriteria().andCodSituacionAdministrativaVinculacionEqualTo(codSituacion);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ModificacionSituacionAdministrativaMapper  mapper = session.getMapper(ModificacionSituacionAdministrativaMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
			
		}catch (Exception ex) {
			idio.setError(true);
			idio.setMensaje(UtilsConstantes.MSG_EXEPCION);
			idio.setMensajeTecnico(ex.getMessage());
			return new ArrayList<>();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
