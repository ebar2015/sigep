package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.SequenciasSigep;
import co.gov.dafp.sigep2.bean.SequenciasSigepExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.SequenciasSigepMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla SequenciasSigepService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class SequenciasSigepService implements Serializable {

	private static final long serialVersionUID = -1571746186311999755L;
	
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:54:00 p. m.
	 * @param sequenciasSigep
	 * @return
	 */
	public SequenciasSigep insertSequenciasSigep (SequenciasSigep sequenciasSigep){
		SequenciasSigep seqsigep = new SequenciasSigep();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SequenciasSigepMapper  mapper = session.getMapper(SequenciasSigepMapper.class);
			id =  mapper.insert(sequenciasSigep);
			if(id > 0){
				seqsigep.setError(false);
				seqsigep.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				seqsigep.setError(false);
				seqsigep.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return seqsigep;
		}catch (Exception ex) {
			seqsigep.setError(true);
			seqsigep.setMensaje(UtilsConstantes.MSG_EXEPCION);
			seqsigep.setMensajeTecnico(ex.getMessage());
			return seqsigep;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:54:22 p. m.
	 * @param sequenciasSigep
	 * @return
	 */
	public SequenciasSigep updateSequenciasSigep(SequenciasSigep sequenciasSigep){
		SequenciasSigep seqsigep = new SequenciasSigep();
		SequenciasSigepExample dtoObject = new SequenciasSigepExample();
		if(sequenciasSigep!=null ) {
			dtoObject.createCriteria().andTablaNombreEqualTo(sequenciasSigep.getTablaNombre());
		}
		SqlSession session = null;
		sequenciasSigep.setFechaSq(new Date());
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SequenciasSigepMapper  mapper = session.getMapper(SequenciasSigepMapper.class);
			id = mapper.updateByExampleSelective(sequenciasSigep, dtoObject);
			session.commit();
			if(id > 0){
				seqsigep.setError(false);
				seqsigep.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				seqsigep.setError(false);
				seqsigep.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return seqsigep;
		}catch (Exception ex) {
			seqsigep.setError(true);
			seqsigep.setMensaje(UtilsConstantes.MSG_EXEPCION);
			seqsigep.setMensajeTecnico(ex.getMessage());
			return seqsigep;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:54:33 p. m.
	 * @param nombre
	 * @return
	 */
	public SequenciasSigep getSequenciasSigep(String nombre){
		List<SequenciasSigep> seqsigep = new ArrayList<>();
		SequenciasSigepExample dtoObject = new SequenciasSigepExample();
		dtoObject.createCriteria().andTablaNombreEqualTo(nombre);
		SqlSession session = null;
		SequenciasSigep eror = new SequenciasSigep();
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SequenciasSigepMapper  mapper = session.getMapper(SequenciasSigepMapper.class);
			seqsigep =  mapper.selectByExample(dtoObject);
			if(!seqsigep.isEmpty()){
				SequenciasSigep upda = new SequenciasSigep();
				upda.setTablaNombre(seqsigep.get(0).getTablaNombre());
				upda.setSecuencia(seqsigep.get(0).getSecuencia() + 1);
				if(seqsigep.get(0).getTipoRetorno() > 0) {
					upda.setSequenciaS(getFormato(upda.getSecuencia(),seqsigep.get(0).getTipoRetorno()));
				}
				dtoObject = new SequenciasSigepExample();
				dtoObject.createCriteria().andTablaNombreEqualTo(upda.getTablaNombre());
				int id = mapper.updateByExampleSelective(upda, dtoObject);
				session.commit();
				if(id > 0){
					upda.setError(false);
					upda.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				}else{
					upda.setError(true);
					upda.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
				}
				return upda;
			}else{
				return new SequenciasSigep();
			}
		}catch(Exception ex){
			eror.setError(true);
			eror.setMensaje(UtilsConstantes.MSG_EXEPCION);
			eror.setMensajeTecnico(ex.getMessage());
			return eror;	
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:54:33 p. m.
	 * @param nombre
	 * @return
	 */
	public SequenciasSigep setSequenciasSigepCodigoEntidad(String nombre){
		List<SequenciasSigep> seqsigep = new ArrayList<>();
		SequenciasSigepExample dtoObject = new SequenciasSigepExample();
		dtoObject.createCriteria().andTablaNombreEqualTo(nombre);
		SqlSession session = null;
		SequenciasSigep eror = new SequenciasSigep();
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			SequenciasSigepMapper  mapper = session.getMapper(SequenciasSigepMapper.class);
			seqsigep =  mapper.selectByExample(dtoObject);
			if(!seqsigep.isEmpty()){
				SequenciasSigep upda = new SequenciasSigep();
				upda.setTablaNombre(seqsigep.get(0).getTablaNombre());
				upda.setSecuencia(seqsigep.get(0).getSecuencia() - 1);
				if(seqsigep.get(0).getTipoRetorno() > 0) {
					upda.setSequenciaS(getFormato(upda.getSecuencia(),seqsigep.get(0).getTipoRetorno()));
				}
				dtoObject = new SequenciasSigepExample();
				dtoObject.createCriteria().andTablaNombreEqualTo(upda.getTablaNombre());
				int id = mapper.updateByExampleSelective(upda, dtoObject);
				session.commit();
				if(id > 0){
					upda.setError(false);
					upda.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				}else{
					upda.setError(true);
					upda.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
				}
				return upda;
			}else{
				return new SequenciasSigep();
			}
		}catch(Exception ex){
			eror.setError(true);
			eror.setMensaje(UtilsConstantes.MSG_EXEPCION);
			eror.setMensajeTecnico(ex.getMessage());
			return eror;	
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
  /**
   * 
   * @param value
   * @param formt
   * @return
   */
	public String getFormato(long value, int formt) {
		String salida="";
		StringBuilder bld = new StringBuilder();
		if(formt > 0) {
			for (int i = String.valueOf(value).length(); i < formt; i++) {
				bld.append("0");
			}
		}
		salida=bld.toString()+""+value;
		return salida;
	}

}
