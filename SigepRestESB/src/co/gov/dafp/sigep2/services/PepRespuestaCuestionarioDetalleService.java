package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.PepRespuestaCuestionarioDetalle;
import co.gov.dafp.sigep2.bean.PepRespuestaCuestionarioDetalleExample;
import co.gov.dafp.sigep2.bean.SequenciasSigep;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.PepRespuestaCuestionarioDetalleMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla PepRespuestaCuestionarioDetalleService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class PepRespuestaCuestionarioDetalleService implements Serializable {

	private static final long serialVersionUID = -3499653947627686447L;

	/**
	 * @param PepRespuestaCuestionarioDetalle
	 * @return
	 */
	public PepRespuestaCuestionarioDetalle insertPepRespuestaCuestionarioDetalle (PepRespuestaCuestionarioDetalle pepRespuestaCuestionarioDetalle){
		PepRespuestaCuestionarioDetalle acre = new PepRespuestaCuestionarioDetalle();
		if(pepRespuestaCuestionarioDetalle.getIdRespuestaCuestionarioDetalle() == null) {
			SequenciasSigepService service = new SequenciasSigepService();
			SequenciasSigep sequ = service.getSequenciasSigep("SQ_PEP_RESPUESTA_CUESTIONARIO_DETALLE");
			pepRespuestaCuestionarioDetalle.setIdRespuestaCuestionarioDetalle(sequ.getSecuencia());
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestaCuestionarioDetalleMapper  mapper = session.getMapper(PepRespuestaCuestionarioDetalleMapper.class);
			id =  mapper.insert(pepRespuestaCuestionarioDetalle);
			if(id > 0){
				acre.setIdRespuestaCuestionarioDetalle(pepRespuestaCuestionarioDetalle.getIdRespuestaCuestionarioDetalle());
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
	 * @param PepRespuestaCuestionarioDetalle
	 * @return
	 */
	public PepRespuestaCuestionarioDetalle updatePepRespuestaCuestionarioDetalle(PepRespuestaCuestionarioDetalle PepRespuestaCuestionarioDetalle){
		PepRespuestaCuestionarioDetalle acre = new PepRespuestaCuestionarioDetalle();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestaCuestionarioDetalleMapper  mapper = session.getMapper(PepRespuestaCuestionarioDetalleMapper.class);
			id = mapper.updateByPrimaryKey(PepRespuestaCuestionarioDetalle);
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
	public List<PepRespuestaCuestionarioDetalle> getPepRespuestaCuestionarioDetalleAll(PepRespuestaCuestionarioDetalle PepRespuestaCuestionarioDetalle){
		List<PepRespuestaCuestionarioDetalle> asoc = new ArrayList<>();
		PepRespuestaCuestionarioDetalleExample dtoObject = new PepRespuestaCuestionarioDetalleExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestaCuestionarioDetalleMapper  mapper = session.getMapper(PepRespuestaCuestionarioDetalleMapper.class);
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
	public PepRespuestaCuestionarioDetalle getPepRespuestaCuestionarioDetalleById(long id){
		PepRespuestaCuestionarioDetalle asoc = new PepRespuestaCuestionarioDetalle();
		PepRespuestaCuestionarioDetalleExample dtoObject = new PepRespuestaCuestionarioDetalleExample();
		dtoObject.createCriteria().andIdRespuestaCuestionarioDetalleEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PepRespuestaCuestionarioDetalleMapper  mapper = session.getMapper(PepRespuestaCuestionarioDetalleMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new PepRespuestaCuestionarioDetalle();
			}
		}catch(Exception ex){
		
			return new PepRespuestaCuestionarioDetalle();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
}
