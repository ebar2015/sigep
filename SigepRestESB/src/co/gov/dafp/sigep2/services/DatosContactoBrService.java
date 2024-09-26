/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.DatoContacto;
import co.gov.dafp.sigep2.bean.DatoContactoBr;
import co.gov.dafp.sigep2.bean.DatoContactoBrExample;
import co.gov.dafp.sigep2.bean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DatoContactoBrMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
 * @author Jose Viscaya
 *
 */
public class DatosContactoBrService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9007928241774733988L;
	
	/**
	 * 
	 * @param DatosContactoBr
	 * @return
	 */
	public DatoContactoBr insertDatosContactoBr (DatoContactoExt datoContacto){
		DatoContactoBr dat =  new DatoContactoBr();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoBrMapper  mapper = session.getMapper(DatoContactoBrMapper.class);
			id =  mapper.insert(datoContacto);
			if(id > 0){
				dat.setError(false);
				dat.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				dat.setError(true);
				dat.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return dat;
		}catch (Exception ex) {
			dat.setError(true);
			dat.setMensaje(UtilsConstantes.MSG_EXEPCION);
			dat.setMensajeTecnico(ex.getMessage());
			return dat;		
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param DatosContactoBr
	 * @return
	 */
	public DatoContactoBr updateDatosContactoBr(DatoContactoExt datoContactoExt){
		DatoContactoBr dat =  new DatoContactoBr();
		dat =  datosContactoExist(datoContactoExt);
		if(dat.getCodDatosContactoBr() !=null) {
			dat =  new DatoContactoBr();
			SqlSession session = null;
			int id = 0;
			try {
				session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
				DatoContactoBrMapper  mapper = session.getMapper(DatoContactoBrMapper.class);
				id = mapper.updateByPrimaryKeySelective(datoContactoExt);
				session.commit();
				if(id > 0){
					dat.setError(false);
					dat.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
					session.commit();
				}else{
					dat.setError(false);
					dat.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
				}
				return dat;
			}catch (Exception ex) {
				dat.setError(true);
				dat.setMensaje(UtilsConstantes.MSG_EXEPCION);
				dat.setMensajeTecnico(ex.getMessage());
				return dat;
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}else {
			dat =  new DatoContactoBr();
			dat = insertDatosContactoBr(datoContactoExt);
			return dat;
		}
	}
	/**
	 * 
	 * @return
	 */
	public List<DatoContactoBr> getDatosContactoBrByAll(){
		List<DatoContactoBr> asoc = new ArrayList<>();
		DatoContactoBrExample dtoObject = new DatoContactoBrExample();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoBrMapper  mapper = session.getMapper(DatoContactoBrMapper.class);
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
	public DatoContactoBr datosContactoBrById(long id){
		DatoContactoBr asoc = new DatoContactoBr();
		DatoContactoBrExample dtoObject = new DatoContactoBrExample();
		dtoObject.createCriteria().andCodDatosContactoBrEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoBrMapper  mapper = session.getMapper(DatoContactoBrMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new DatoContactoBr();
			}
		}catch(Exception ex){
		
			return new DatoContactoBr();
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
	public DatoContactoBr datosContactoExist(DatoContactoExt datoContactoBr){
		List<DatoContactoBr> asoc = new ArrayList<>();
		DatoContactoBrExample dtoObject = new DatoContactoBrExample();
		if(datoContactoBr!=null && datoContactoBr.getCodDeclaracion()!=null) {
			dtoObject.createCriteria().andCodDeclaracionEqualTo(datoContactoBr.getCodDeclaracion())
			.andCodPersonaEqualTo(datoContactoBr.getCodPersona());	
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoBrMapper  mapper = session.getMapper(DatoContactoBrMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new DatoContactoBr();
			}
		}catch(Exception ex){
		
			return new DatoContactoBr();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public DatoContactoExt getDatosContactoBrPorIdPersona(DatoContactoExt datoContactoExt){
		DatoContactoExt asoc = new DatoContactoExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoBrMapper  mapper = session.getMapper(DatoContactoBrMapper.class);
			asoc =  mapper.selectByDatoContacto(datoContactoExt);
			if(asoc != null){
				return asoc;
			}else{
				return new DatoContactoExt();
			}
		}catch(Exception ex){
			return new DatoContactoExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	public DatoContactoExt getDatosContactoBrByDeclaracion(DatoContactoExt datoContacto){
		DatoContactoExt asoc = new DatoContactoExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DatoContactoBrMapper  mapper = session.getMapper(DatoContactoBrMapper.class);
			asoc =  mapper.selectByDatoContactoByDeclaracion(datoContacto);
			if(asoc != null){
				return asoc;
			}else{
				return new DatoContactoExt();
			}
		}catch(Exception ex){
			return new DatoContactoExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

}
