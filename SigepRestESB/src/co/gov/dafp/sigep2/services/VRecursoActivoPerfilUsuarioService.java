
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.VRecursoActivoPerfilUsuario;
import co.gov.dafp.sigep2.bean.VRecursoActivoPerfilUsuarioExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.VRecursoActivoPerfilUsuarioMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla VRecursoActivoPerfilUsuarioService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @fecha 27/07/2018 2:59:20 p. m.
 */
public class VRecursoActivoPerfilUsuarioService implements Serializable {

	private static final long serialVersionUID = 4542009639134036586L;
	
	/**
	 * @author: Jose Viscaya 
	 * @fecha 27/07/2018 2:59:32 p. m.
	 * @param limitIni
	 * @param limitEnd
	 * @return
	 */
	public List<VRecursoActivoPerfilUsuario> getVRecursoActivoPerfilUsuario(int limitIni, int limitEnd){
		List<VRecursoActivoPerfilUsuario> asoc = new ArrayList<>();
		VRecursoActivoPerfilUsuarioExample dtoObject = new VRecursoActivoPerfilUsuarioExample();
		if(limitEnd > 1 ) {
			dtoObject.setLimitInit(limitIni);
			dtoObject.setLimitEnd(limitEnd);
		}else {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VRecursoActivoPerfilUsuarioMapper  mapper = session.getMapper(VRecursoActivoPerfilUsuarioMapper.class);
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
	public List<VRecursoActivoPerfilUsuario> getVRecursoActivoPerfilUsuarioPorId(BigDecimal id){
		List<VRecursoActivoPerfilUsuario> asoc = new ArrayList<>();
		VRecursoActivoPerfilUsuarioExample dtoObject = new VRecursoActivoPerfilUsuarioExample();
		if(id!=null ) {
			dtoObject.createCriteria().andCodRecursoEqualTo(id);	
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			VRecursoActivoPerfilUsuarioMapper  mapper = session.getMapper(VRecursoActivoPerfilUsuarioMapper.class);
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

}
