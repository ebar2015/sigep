package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PepRespuestaCuestionario;
import co.gov.dafp.sigep2.bean.PepRespuestaCuestionarioExample;
import co.gov.dafp.sigep2.bean.SequenciasSigep;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PepRespuestaCuestionarioMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PepRespuestaCuestionarioService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PepRespuestaCuestionarioService implements Serializable {
	private static final long serialVersionUID = -3509118117525844453L;
	
	/**
	 * @param PepRespuestaCuestionario
	 * @return
	 */
	public PepRespuestaCuestionario insertPepRespuestaCuestionario (PepRespuestaCuestionario pepRespuestaCuestionario){
		PepRespuestaCuestionario acre = new PepRespuestaCuestionario();
		if(pepRespuestaCuestionario.getIdRespuestaCuestionario() == null) {
			SequenciasSigepService service = new SequenciasSigepService();
			SequenciasSigep sequ = service.getSequenciasSigep("SQ_PEP_RESPUESTA_CUESTIONARIO");
			pepRespuestaCuestionario.setIdRespuestaCuestionario(sequ.getSecuencia());
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestaCuestionarioMapper  mapper = session.getMapper(PepRespuestaCuestionarioMapper.class);
			id =  mapper.insert(pepRespuestaCuestionario);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				acre.setIdRespuestaCuestionario(pepRespuestaCuestionario.getIdRespuestaCuestionario());
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
	 * @param PepRespuestaCuestionario
	 * @return
	 */
	public PepRespuestaCuestionario updatePepRespuestaCuestionario(PepRespuestaCuestionario pepRespuestaCuestionario){
		PepRespuestaCuestionario acre = new PepRespuestaCuestionario();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestaCuestionarioMapper  mapper = session.getMapper(PepRespuestaCuestionarioMapper.class);
			id = mapper.updateByPrimaryKey(pepRespuestaCuestionario);
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
	public List<PepRespuestaCuestionario> getPepRespuestaCuestionarioAll(PepRespuestaCuestionario pepRespuestaCuestionario){
		List<PepRespuestaCuestionario> asoc = new ArrayList<>();
		PepRespuestaCuestionarioExample dtoObject = new PepRespuestaCuestionarioExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestaCuestionarioMapper  mapper = session.getMapper(PepRespuestaCuestionarioMapper.class);
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
	public PepRespuestaCuestionario getPepRespuestaCuestionarioById(long id){
		PepRespuestaCuestionario asoc = new PepRespuestaCuestionario();
		PepRespuestaCuestionarioExample dtoObject = new PepRespuestaCuestionarioExample();
		dtoObject.createCriteria().andIdRespuestaCuestionarioEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestaCuestionarioMapper  mapper = session.getMapper(PepRespuestaCuestionarioMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new PepRespuestaCuestionario();
			}
		}catch(Exception ex){
		
			return new PepRespuestaCuestionario();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
