/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Norma;
import co.gov.dafp.sigep2.bean.NormaExample;
import co.gov.dafp.sigep2.bean.SequenciasSigep;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.NormaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla Norma
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Jueves 26 de Julio de 2018
*/

public class NormaService implements Serializable {

	private static final long serialVersionUID = -1633738885929602110L;
	
	/**
	 * 
	 * @param Norma
	 * @return
	 */
	public Norma insertNorma (Norma norma){
		Norma acre = new Norma();
		SqlSession session = null;
		SequenciasSigepService service = new SequenciasSigepService();
		SequenciasSigep sequ = service.getSequenciasSigep("NORMA");
		norma.setCodNorma(sequ.getSecuencia());
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NormaMapper  mapper = session.getMapper(NormaMapper.class);
			id =  mapper.insert(norma);
			session.commit();
			if(id > 0){
				acre.setError(false);
				List<Norma> rest = getNormaFiltro(norma);
				if(rest.size() > 0) {
					acre.setCodNorma(rest.get(0).getCodNorma());
				}
				acre.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
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
	 * @param Norma
	 * @return
	 */
	public Norma updateNorma(Norma Norma){
		Norma acre = new Norma();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NormaMapper  mapper = session.getMapper(NormaMapper.class);
			id = mapper.updateByPrimaryKey(Norma);
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
	public List<Norma> getNormaAll(){
		List<Norma> asoc = new ArrayList<>();
		NormaExample dtoObject = new NormaExample();
		dtoObject.setOrderByClause("NUMERO_NORMA ASC");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NormaMapper  mapper = session.getMapper(NormaMapper.class);
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
	* @author: Jose Viscaya 
	* @fecha 17/08/2018 9:36:38 a.m.
	* @param codTipoNorma
	* @return
	*
	 */
	public List<Norma> getNormaTipoNorma(Integer codTipoNorma){
		List<Norma> asoc = new ArrayList<>();
		NormaExample dtoObject = new NormaExample();
		dtoObject.createCriteria().andCodTipoNormaEqualTo(codTipoNorma);
		dtoObject.setOrderByClause("NUMERO_NORMA ASC");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NormaMapper  mapper = session.getMapper(NormaMapper.class);
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
	public Norma getNormaById(long id){
		Norma asoc = new Norma();
		NormaExample dtoObject = new NormaExample();
		dtoObject.createCriteria().andCodNormaEqualTo(id);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NormaMapper  mapper = session.getMapper(NormaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc!=null){
				return asoc;
			}else{
				return new Norma();
			}
		}catch(Exception ex){
		
			return new Norma();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 23 ago. 2018 10:09:51
	 * @return
	 */
	public List<Norma> getNormaFiltro(Norma norma){
		List<Norma> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NormaMapper  mapper = session.getMapper(NormaMapper.class);
			asoc =  mapper.selectByfiltro(norma);
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
	 * @author: Jose Viscaya 
	 * @fecha 15 nov. 2018 07:20 am
	 * @return
	 */
	public List<Norma> getNormaLike(Norma norma){
		if(norma != null) {
			if(norma.getNumeroNorma() == null) {
				norma.setNumeroNorma("000000000000");
			}
		}
		List<Norma> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			NormaMapper  mapper = session.getMapper(NormaMapper.class);
			asoc =  mapper.selectByLike(norma);
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
