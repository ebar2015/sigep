package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PepRespuestasPreguntaCuestionario;
import co.gov.dafp.sigep2.bean.PepRespuestasPreguntaCuestionarioExample;
import co.gov.dafp.sigep2.bean.SequenciasSigep;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PepRespuestasPreguntaCuestionarioMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PepRespuestasPreguntaCuestionarioService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PepRespuestasPreguntaCuestionarioService implements Serializable {

	private static final long serialVersionUID = -4514218895174577011L;
	
	/**
	 * @param PepRespuestasPreguntaCuestionario
	 * @return
	 */
	public PepRespuestasPreguntaCuestionario insertPepRespuestasPreguntaCuestionario (PepRespuestasPreguntaCuestionario pepRespuestasPreguntaCuestionario){
		PepRespuestasPreguntaCuestionario acre = new PepRespuestasPreguntaCuestionario();
		if(pepRespuestasPreguntaCuestionario.getIdRespuestaPreguntaCuestionario() == null) {
			SequenciasSigepService service = new SequenciasSigepService();
			SequenciasSigep sequ = service.getSequenciasSigep("SQ_PEP_RESPUESTAS_PREGUNTA_CUESTIONARIO");
			pepRespuestasPreguntaCuestionario.setIdRespuestaPreguntaCuestionario(sequ.getSecuencia());
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestasPreguntaCuestionarioMapper  mapper = session.getMapper(PepRespuestasPreguntaCuestionarioMapper.class);
			id =  mapper.insert(pepRespuestasPreguntaCuestionario);
			if(id > 0){
				acre.setError(false);
				acre.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				acre.setIdRespuestaPreguntaCuestionario(pepRespuestasPreguntaCuestionario.getIdRespuestaPreguntaCuestionario());
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
	 * @param PepRespuestasPreguntaCuestionario
	 * @return
	 */
	public PepRespuestasPreguntaCuestionario updatePepRespuestasPreguntaCuestionario(PepRespuestasPreguntaCuestionario pepRespuestasPreguntaCuestionario){
		PepRespuestasPreguntaCuestionario acre = new PepRespuestasPreguntaCuestionario();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestasPreguntaCuestionarioMapper  mapper = session.getMapper(PepRespuestasPreguntaCuestionarioMapper.class);
			id = mapper.updateByPrimaryKey(pepRespuestasPreguntaCuestionario);
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
	public List<PepRespuestasPreguntaCuestionario> getPepRespuestasPreguntaCuestionarioAll(PepRespuestasPreguntaCuestionario pepRespuestasPreguntaCuestionario){
		List<PepRespuestasPreguntaCuestionario> asoc = new ArrayList<>();
		PepRespuestasPreguntaCuestionarioExample dtoObject = new PepRespuestasPreguntaCuestionarioExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestasPreguntaCuestionarioMapper  mapper = session.getMapper(PepRespuestasPreguntaCuestionarioMapper.class);
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
	public PepRespuestasPreguntaCuestionario getPepRespuestasPreguntaCuestionarioById(long id){
		PepRespuestasPreguntaCuestionario asoc = new PepRespuestasPreguntaCuestionario();
		PepRespuestasPreguntaCuestionarioExample dtoObject = new PepRespuestasPreguntaCuestionarioExample();
		dtoObject.createCriteria().andIdPreguntaCuestionarioEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestasPreguntaCuestionarioMapper  mapper = session.getMapper(PepRespuestasPreguntaCuestionarioMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new PepRespuestasPreguntaCuestionario();
			}
		}catch(Exception ex){
		
			return new PepRespuestasPreguntaCuestionario();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
